package gob.pe.sunarp.extranet.common;
public class SynchronizedInteger {
	
	private int i;
	
	public SynchronizedInteger(int i) {
		this.i = i;
	}
	
	public synchronized void increase() {
		i++;
	}

	public synchronized void decrease() {
		i--;
	}
	
	public String toString() {
		return new Integer(i).toString();
	}
}

