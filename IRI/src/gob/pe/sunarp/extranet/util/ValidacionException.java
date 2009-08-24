package gob.pe.sunarp.extranet.util;

public class ValidacionException extends Exception {
	private String mensaje = "";
	private String focus = "";

	public ValidacionException(String mensaje, String focus) {
		super();
		this.mensaje = mensaje;
		this.focus = focus;
	}
	
	public ValidacionException(String mensaje) {
		super();
		this.mensaje = mensaje;
	}	

	/**
	 * Gets the mensaje
	 * @return Returns a String
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * Sets the mensaje
	 * @param mensaje The mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Gets the focus
	 * @return Returns a String
	 */
	public String getFocus() {
		return focus;
	}
	/**
	 * Sets the focus
	 * @param focus The focus to set
	 */
	public void setFocus(String focus) {
		this.focus = focus;
	}

}