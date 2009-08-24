package gob.pe.sunarp.extranet.transaction;

public class TransactionException extends Exception {

	private Exception ex;
	private String message;

	public TransactionException(String message) {
		super(message);
		this.message = message;
	}

	public TransactionException(Exception ex) {
		super(ex.toString());
		this.ex = ex;
	}

	public TransactionException(String mensaje, Exception ex) {
		super(ex.toString());
		this.message = mensaje;
		this.ex = ex;
	}
	
	public Exception getEx() {
		return ex;
	}

	public java.lang.String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());
		buffer.append(this.message);
		return buffer.toString();
	}

	public String getMessage() {
		return message;
	}
}
