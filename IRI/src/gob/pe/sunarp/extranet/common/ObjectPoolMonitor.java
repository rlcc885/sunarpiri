package gob.pe.sunarp.extranet.common;
public class ObjectPoolMonitor extends Thread {
	
	private long seekTime = 1*1000; // default 1 seg
	private ObjectPool objectPool = null;
	
	public void setSeekTime(long seekTime) {
		this.seekTime = seekTime;
	}

	public long getSeekTime() {
		return seekTime;
	}

	public ObjectPoolMonitor(ObjectPool objectPool) {
		this.objectPool = objectPool;
	}
	
	public void run() {
		try {
			while (true) {
				sleep(seekTime);
				objectPool.checkExpiry();
			}
		} catch (InterruptedException e) {
		}
	}
}

