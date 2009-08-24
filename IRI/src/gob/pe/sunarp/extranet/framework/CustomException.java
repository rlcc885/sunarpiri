package gob.pe.sunarp.extranet.framework;

/*
*Custom Exception
*@author h
*@vesion 1
*/

public class CustomException extends Exception {
	private String codigoError;
	private String message="";
	private String forward="error";

	public CustomException(String codigoError, String mensaje, String forward) {
		super();
		this.message = mensaje;
		this.codigoError = codigoError;
		this.forward = forward;
	}

	public CustomException(String codigoError, String mensaje) {
		super();
		this.codigoError = codigoError;
		this.message = mensaje;
	}

	public CustomException(String codigoError) {
		super();
		this.codigoError = codigoError;
	}

	/**
	 * Gets the codigoError
	 * @return Returns a String
	 */
	public String getCodigoError() {
		return codigoError;
	}
	/**
	 * Sets the codigoError
	 * @param codigoError The codigoError to set
	 */
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * Gets the mensaje
	 * @return Returns a String
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Sets the mensaje
	 * @param mensaje The mensaje to set
	 */
	public void setMessage(String mensaje) {
		this.message = mensaje;
	}

	/**
	 * Gets the forward
	 * @return Returns a String
	 */
	public String getForward() {
		return forward;
	}
	/**
	 * Sets the forward
	 * @param forward The forward to set
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}

}