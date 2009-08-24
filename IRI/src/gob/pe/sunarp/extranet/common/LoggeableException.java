package gob.pe.sunarp.extranet.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggeableException extends Exception {
	
	private String errorCode;
	private String otroMensaje;

	/**
	 * Constructor for LoggeableException
	 */
	public LoggeableException(String errorCode) {
		super(findText(errorCode));
		this.errorCode = errorCode;
	}

	/**
	 * Constructor for LoggeableException
	 */
	public LoggeableException(String errorCode, String otroMensaje) {
		super(findText(errorCode) + ". " + otroMensaje);
		this.otroMensaje = otroMensaje;
		this.errorCode = errorCode;
	}

	/**
	 * Constructor for LoggeableException
	 */
	public LoggeableException(String errorCode, Throwable e) {
		super(findText(errorCode) + "\r\nNestedException\r\n" + getStackTrace(e));
		this.otroMensaje = otroMensaje;
		this.errorCode = errorCode;
	}

	private static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}


	private static String findText(String errorCode) {
		RegisteredError error = SystemResources.getInstance().getRegisteredError(errorCode);
		if (error == null) {
			return "Error code not Found: " + errorCode;
		}
		return error.toString();
	}
	/**
	 * Gets the otroMensaje
	 * @return Returns a String
	 */
	public String getOtroMensaje() {
		return otroMensaje;
	}
	/**
	 * Sets the otroMensaje
	 * @param otroMensaje The otroMensaje to set
	 */
	public void setOtroMensaje(String otroMensaje) {
		this.otroMensaje = otroMensaje;
	}

	/**
	 * Gets the errorCode
	 * @return Returns a String
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * Sets the errorCode
	 * @param errorCode The errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}

