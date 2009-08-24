package gob.pe.sunarp.extranet.common;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class ObjectPool {
	public static final boolean trace = false;
	public String currentThread;
	public int timeOutOcurred;

	public static final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd.HHmmss.SSS");

	private String now() {
		return new StringBuffer(this.getClass().getName())
			.append(" ")
			.append(Thread.currentThread().getName())
			.append(" ")
			.append(formatter.format(new java.util.Date()))
			.toString();
	}

	private long expirationTime = 10 * 60 * 1000; // default 10 minutos
	private long timeOut = 5 * 60 * 1000; // default 5 minutos
	private int maxSize = 10; // default 10 

	private long waitingThreads = 0;

	private boolean closed = false; // default 10 

	private Hashtable locked = new Hashtable();
	private Hashtable unlocked = new Hashtable();

	protected abstract Object create() throws Exception;
	protected abstract boolean validate(Object o);
	protected abstract void expire(Object o);

	private ObjectPoolMonitor monitor = null;

	public ObjectPool() {
		String className = this.getClass().getName();
		int length = className.length();
		if (length > 30) {
			className = className.substring(length - 30, length);
		}
		monitor = new ObjectPoolMonitor(this);
		monitor.setName("MonitorExpiry ObjectPool " + className);
		monitor.setSeekTime(Math.min(expirationTime, timeOut)/10);
		monitor.start();
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public int getLockedSize() {
		return locked.size();
	}

	public int getUnlockedSize() {
		return unlocked.size();
	}

	public int getSize() {
		return getLockedSize() + getUnlockedSize();
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
		monitor.setSeekTime(Math.min(expirationTime, timeOut)/10);
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
		monitor.setSeekTime(Math.min(expirationTime, timeOut)/10);
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public synchronized void close() {
		currentThread = Thread.currentThread().getName() + "->close()";
		Object o;
		try {
			while ((getLockedSize() > 0) || (waitingThreads > 0)) {
				if (trace)
					System.err.println(now() + " ObjectPool.close(). entrando a wait()");
				wait();
				if (trace)
					System.err.println(now() + " ObjectPool.close(). salio de wait()");
			}
		} catch (InterruptedException e) {
		}
		closed = true;
		if (getUnlockedSize() > 0) {
			Enumeration e = unlocked.keys();
			while (e.hasMoreElements()) {
				o = e.nextElement();
				unlocked.remove(o);
				if (trace)
					System.err.println(now() + " ObjectPool.close(). Expirando objeto");
				expire(o);
				if (trace)
					System.err.println(now() + " ObjectPool.close(). Objeto Expirado");
			}
		}
		if (trace)
			System.err.println(now() + " ObjectPool.close(). Interrumpiendo monitor");
		monitor.interrupt();
		if (trace)
			System.err.println(now() + " ObjectPool.close(). Monitor Interrumpido. TERMINADO TODO");
		currentThread = currentThread + " salio";
	}

	synchronized void checkExpiry() {
		currentThread = Thread.currentThread().getName() + "->checkExpiry()";
		if (trace)
			System.err.println(now() + " ObjectPool.checkExpiry(). Entrando...");
		try {
			long now = System.currentTimeMillis();
			Enumeration e = unlocked.keys();
			Object o;
			if (getUnlockedSize() > 0) {
				if (trace)
					System.err.println(now() + " ObjectPool.checkExpiry(). Revisando Unlockeds");
				while (e.hasMoreElements()) {
					o = e.nextElement();
					if ((now - ((Long) unlocked.get(o)).longValue()) > expirationTime) {
						// object has expired
						unlocked.remove(o);
						if (trace)
							System.err.println(now() + " ObjectPool.checkExpiry(). Expirando objeto");
						expire(o);
						if (trace)
							System.err.println(now() + " ObjectPool.checkExpiry(). Objeto Expirado");
						o = null;
					}
				}
			}
			e = locked.keys();
			if (getLockedSize() > 0) {
				if (trace)
					System.err.println(now() + " ObjectPool.checkExpiry(). Revisando Lockeds");
				while (e.hasMoreElements()) {
					o = e.nextElement();
					if ((now - ((Long) locked.get(o)).longValue()) > timeOut) {
						timeOutOcurred++;
						// object has expired
						/*locked.remove(o);
						if (trace)
							System.err.println(now() + " ObjectPool.checkExpiry(). Expirando objeto");
						expire(o);
						if (trace)
							System.err.println(now() + " ObjectPool.checkExpiry(). Objeto Expirado");
						o = null;*/
					}
				}
			}
		} finally {
			if (trace)
				System.err.println(now() + " ObjectPool.checkExpiry(). Notificando");
			notify();
			currentThread = currentThread + " salio";
		}
	}

	protected synchronized Object checkOut() throws Exception {
		currentThread = Thread.currentThread().getName() + "->checkOut()";
		try {
			if (closed)
				throw new InterruptedException("ObjectPool closed");
			while (true) {
				long now = System.currentTimeMillis();
				Object o;

				if (unlocked.size() > 0) {
					if (trace)
						System.err.println(now() + " ObjectPool.checkOut(). Revisando Unlockeds por una conexion disponible");
					Enumeration e = unlocked.keys();
					while (e.hasMoreElements()) {
						if (trace)
							System.err.println(now() + " ObjectPool.checkOut(). Encontrado. Validandolo");
						o = e.nextElement();
						if (validate(o)) {
							if (trace)
								System.err.println(now() + " ObjectPool.checkOut(). Validado");
							unlocked.remove(o);
							locked.put(o, new Long(now));
							if (trace)
								System.err.println(now() + " ObjectPool.checkOut(). Encontrado. Devolviendo conexion");
							return (o);
						} else {
							// object failed validation
							if (trace)
								System.err.println(now() + " ObjectPool.checkOut(). no validado. Expirando conexion");
							unlocked.remove(o);
							expire(o);
							o = null;
							if (trace)
								System.err.println(now() + " ObjectPool.checkOut(). Encontrado. Conexion espirada");
						}
					}
				}
				// no objects available, check if we create one
				if (canCreateOne()) {
					if (trace)
						System.err.println(now() + " ObjectPool.checkOut(). Se crea nueva conexion");
					o = create();
					locked.put(o, new Long(now));
					if (trace)
						System.err.println(now() + " ObjectPool.checkOut(). Creada");
					return (o);
				} else {
					if (trace)
						System.err.println(now() + " ObjectPool.checkOut(). Entrando a wait()");
					waitingThreads++;
					wait();
					if (trace)
						System.err.println(now() + " ObjectPool.checkOut(). Salio de wait()");
				}
			}
		} finally {
			if (trace)
				System.err.println(now() + " ObjectPool.checkOut(). Notificando");
			waitingThreads--;
			notify();
			currentThread = currentThread + " salio";

		// TODO: Eliminar
		//System.out.println("*1*" + currentThread);		
		}
	}

	private boolean canCreateOne() {
		return getSize() < getMaxSize();
	}

	protected synchronized void checkIn(Object o) {
		currentThread = Thread.currentThread().getName() + "->checkIn()";
		try {
			locked.remove(o);
			unlocked.put(o, new Long(System.currentTimeMillis()));
			if (trace)
				System.err.println(now() + " ObjectPool.checkIn(). Notificando");
		} finally {
			notify();
			currentThread = currentThread + " salio";
		}
	}

	/**
	 * 
	 */
	public void checkStatusConecction(){
		Object o;	
		System.out.println("CM - checkStatusConecction - getUnLockedSize :: "+ getUnlockedSize());
		System.out.println("CM - checkStatusConecction - getSize       :: "+ getSize());				

		if (getUnlockedSize() > 2) {
			Enumeration e1 = unlocked.keys();
			while (e1.hasMoreElements() && getUnlockedSize() > 1) {				
				o = e1.nextElement();
				unlocked.remove(o);				
				System.out.println(now() + "CM - checkStatusConecction(). Expirando objeto DB no activo::"+o.toString());
				expire(o);
				System.out.println(now() + "CM - checkStatusConecction(). Objeto DB no activo Expirado:::"+o.toString());
			}
		}

		if (getLockedSize() > 5) {
			System.out.println("CM - checkStatusConecction - getLockedSize :: "+ getLockedSize());
			System.out.println("CM - checkStatusConecction - getSize       :: "+ getSize());			
			Enumeration e1 = locked.keys();
			while (e1.hasMoreElements() && getLockedSize() > 1) {
				o = e1.nextElement();
				locked.remove(o);				
				System.out.println(now() + "CM - checkStatusConecction(). Expirando objeto::"+o.toString());
				expire(o);
				System.out.println(now() + "CM - checkStatusConecction(). Objeto Expirado:::"+o.toString());
			}
		}
	}
}