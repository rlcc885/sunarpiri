package gob.pe.sunarp.extranet.pool;

public class ObjectPoolMonitor extends Thread {
	
	private long seekTime = 1*1000*5; // default 1 seg - Se aumenta a 5 segundos
	private ObjectPool objectPool;
	
	public void setSeekTime(long seekTime) {
		this.seekTime = seekTime;
	}

	public long getSeekTime() {
		return seekTime;
	}

	public ObjectPoolMonitor(ObjectPool objectPool) {
		this.objectPool = objectPool;
	}
	
	public ObjectPoolMonitor() {	
	}
	
	public void init(long seekTime){
		setName("PoolMonitor.");
		setSeekTime(seekTime);
	}	
	
	public void run() {
		try {
			while (true) {
				sleep(seekTime);
				objectPool.checkExpiry();
			}
		} catch (InterruptedException e) {
			System.out.println("Se interrumpio el Hilo ObjectPoolMonitor");
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			interrupt();	
		} catch (Exception e) {
			System.out.println("Se interummpio el Hilo: Pool Monitor");
			e.printStackTrace();
		}
	}
}
