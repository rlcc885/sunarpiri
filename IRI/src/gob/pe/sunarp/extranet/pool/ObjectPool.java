
package gob.pe.sunarp.extranet.pool;
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

	public Hashtable locked = new Hashtable();
	public Hashtable unlocked = new Hashtable();

	protected abstract Object create() throws Exception;
	protected abstract boolean validate(Object o);
	protected abstract void expire(Object o);

	/**
	 * SVASQUEZ 
	 * AVATAR - GLOBAL
	 * ACCION: 	
	 * 1. SE COMENTA EL OBJETO: ObjectPoolMonitor
	 * 2. SE CREA UNA INSTANCIA DEL OBJETO MonitorConnectionFactory.java  
	 */
	private ObjectPoolMonitor monitor = null;
	//private MonitorConnectionFactory monitor = null;

	public ObjectPool() {
		String className = this.getClass().getName();
		int length = className.length();
		if (length > 30) {
			className = className.substring(length - 30, length);
		}
		//SAUL
		monitor = new ObjectPoolMonitor(this);
		monitor.setName("MonitorExpiry ObjectPool " + className);
		monitor.setSeekTime(Math.min(expirationTime, timeOut)/10);
		monitor.start();
		System.out.println("getExpirationTime()"+getExpirationTime());
		System.out.println("getTimeOut()::"+getTimeOut());		
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
		//monitor.setTimeSleep(Math.min(expirationTime, timeOut)/10);
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
		//monitor.setTimeSleep(Math.min(expirationTime, timeOut)/10);
		monitor.setSeekTime(Math.min(expirationTime, timeOut)/10);
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public synchronized void close() {
		currentThread = Thread.currentThread().getName() + "->close()";
		Object o;
		try {
			while ((getLockedSize() > 1) || (waitingThreads > 0)) {
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
		/**
		 * SVASQUEZ
		 * AVATAR-GLOBAL
		 * DESCRIPCION: SE COMENTA EL MONITOR INTERRUP
		 */
		monitor.interrupt();
		//monitor.closeListener();
		if (trace)
			System.err.println(now() + " ObjectPool.close(). Monitor Interrumpido. TERMINADO TODO");
		currentThread = currentThread + " salio";
	}


	public synchronized void closePool() {
		currentThread = Thread.currentThread().getName() + "->close()";
		Object o;
		try {
			if (getLockedSize() > 0) {
				Enumeration e1 = locked.keys();
				while (e1.hasMoreElements()) {
					o = e1.nextElement();
					locked.remove(o);
					if (trace)
						System.err.println(now() + " ObjectPool.close(). Expirando objeto");
					expire(o);
					if (trace)
						System.err.println(now() + " ObjectPool.close(). Objeto Expirado");
				}
			}
			
			while ((getLockedSize() > 1) || (waitingThreads > 0)) {
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
		/**
		 * SVASQUEZ
		 * AVATAR-GLOBAL
		 * DESCRIPCION: SE COMENTA EL MONITOR INTERRUP
		 */
		monitor.interrupt();
		//monitor.closeListener();
		if (trace)
			System.err.println(now() + " ObjectPool.close(). Monitor Interrumpido. TERMINADO TODO");
		currentThread = currentThread + " salio";
	}

		
	synchronized void checkExpiry() {
		currentThread = Thread.currentThread().getName() + "->checkExpiry()";
		if (trace)
			System.out.println(now() + " ObjectPool.checkExpiry(). Entrando...");
		try {
			long now = System.currentTimeMillis();
			Enumeration e = unlocked.keys();
			Object o;
			if (getUnlockedSize() > 0) {
				if (trace)
					System.out.println(now() + " ObjectPool.checkExpiry(). Revisando Unlockeds");
				while (e.hasMoreElements()) {
					o = e.nextElement();
					long valor = ((Long) unlocked.get(o)).longValue();
					if ((now - valor) > expirationTime) {
						// object has expired
						unlocked.remove(o);
						if (trace)
							System.out.println(now() + " ObjectPool.checkExpiry(). Expirando objeto");
						expire(o);
						if (trace)
							System.out.println(now() + " ObjectPool.checkExpiry(). Objeto Expirado");
						o = null;
					}
				}
			}
			e = locked.keys();
			if (getLockedSize() > 0) {
				if (trace)
					System.out.println(now() + " ObjectPool.checkExpiry(). Revisando Lockeds");
				while (e.hasMoreElements()) {
					o = e.nextElement();
					long valor = ((Long) locked.get(o)).longValue();
					if ((now - valor) > timeOut) {
						timeOutOcurred++;
						// object has expired
						/**locked.remove(o);
						if (trace)
							System.err.println(now() + " ObjectPool.checkExpiry(). Expirando objeto");
						expire(o);
						if (trace)
							System.err.println(now() + " ObjectPool.checkExpiry(). Objeto Expirado");
						o = null;*/
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
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
					
					// revisamos que este objeto creado no sea igual a los que ya tenemos
					if (locked.containsKey(o)) {
						System.err.println(now() + " FATAL-ERROR: SE CREÓ UN OBJETO REPETIDO! " + this.getClass().getName());
						throw new IllegalStateException("El método create() creó un objeto que ya existía");
					}
					if (unlocked.containsKey(o)) {
						System.err.println(now() + " FATAL-ERROR: SE CREÓ UN OBJETO REPETIDO! " + this.getClass().getName());
						throw new IllegalStateException("El método create() creó un objeto que ya existía");
					}

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
		}catch(Exception e){
			e.printStackTrace();
		} 
		finally {
			notify();
			currentThread = currentThread + " salio";
		}
	}

	public void checkStatusConecction(){
		Object o;			
		System.out.println("checkStatusConecction - getSize       :: "+ getSize());				

		if (getUnlockedSize() > 7) {
			Enumeration e1 = unlocked.keys();
			while (e1.hasMoreElements() && getUnlockedSize() > 1) {				
				o = e1.nextElement();
				expire(o);				
				long valor = ((Long) locked.get(o)).longValue();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(valor);
				unlocked.remove(o);				
				//System.out.println(now() + " checkStatusConecction(). Expirando objeto No Activo  ::"+o.toString());
				expire(o);
				//System.out.println(now() + " checkStatusConecction(). Objeto No Activo Expirado  :::"+o.toString());
				System.out.println(now() + " checkStatusConecction(). Valor del Objeto No activo Expirado::" + sqlTimestamp);				
			}
		}
		/**
		 * - EL POOL DE HILOS DEL WAS MANEJA ENTRE 10 Y 50 HILOS POR SERVIDOR
		 * - SE EXPIRAN LAS CONEXIONES HASTA EL MINIMO SOPORTADO - VERIFICAR
		 * - SE EMITE UNA ALERTA DE POSIBLES HILOS COLGADOS
		 */		
		if (getLockedSize() > 43) {				
			System.out.println("checkStatusConecction - getLockedSize :: "+ getLockedSize());
			checkExpiryTimePoolConecction();			
			Enumeration e1 = locked.keys();
			while (e1.hasMoreElements() && getLockedSize() > 30) {
				o = e1.nextElement();
				long valor = ((Long) locked.get(o)).longValue();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(valor);
				locked.remove(o);				
				System.out.println(now() + " checkStatusConecction(). Expirando objeto Activo  ::"+o.toString());
				expire(o);
				System.out.println(now() + " checkStatusConecction(). Objeto Activo Expirado  :::"+o.toString());
				System.out.println(now() + " checkStatusConecction(). Valor del Objeto Expirado::" + sqlTimestamp);
			}
		}
	}
	
	/**
	 * SVASQUEZ - AVATAR GLOBAL
	 * SE BUSCAN LAS CONEXIONES CON EXCESO DE TIEMPO
	 * POR DEFECTO SE HA PUESTO 5 MINUTOS
	 * SOLO SE VERIFICAN 2 CONEXIONES DE ESTE TIPO YA QUE SE HACE MUY LENTO EL PROCESO 
	 */
	public void checkExpiryTimePoolConecction(){
		Object o;
		Enumeration e = locked.keys();
		long now = System.currentTimeMillis();
		long tiempoVerificacion = 1000*1*3; //Default 3 minutos
		int contador=0;
		while(e.hasMoreElements() && contador < 5){
			o = e.nextElement();	
			long valor = ((Long) locked.get(o)).longValue(); 
			if ((now - valor) > tiempoVerificacion) {
				contador++;				
				o = e.nextElement();
				locked.remove(o);				
				System.out.println(now() + " checkStatusConecction(). Expirando objeto::"+o.toString());
				expire(o);
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(valor);
				System.out.println(now() + " checkExpiryTimePoolConecction(). Objeto Expirado          ::" + o.toString());
				System.out.println(now() + " checkExpiryTimePoolConecction(). Valor del Objeto Expirado::" + sqlTimestamp);
			}
		}
	}	
}