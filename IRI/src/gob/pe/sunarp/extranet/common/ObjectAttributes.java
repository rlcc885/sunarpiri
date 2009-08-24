package gob.pe.sunarp.extranet.common;
public class ObjectAttributes extends SunarpBean{
	private long creationTime;
	private Thread creationThread;
	
	public ObjectAttributes() {
		creationTime = System.currentTimeMillis();
		creationThread = Thread.currentThread();
	}
	
	public long getCreationTime() {
		return creationTime;
	}

	public Thread getCreationThread() {
		return creationThread;
	}
	
	protected void finalize() {
		creationThread = null;
	}
}

