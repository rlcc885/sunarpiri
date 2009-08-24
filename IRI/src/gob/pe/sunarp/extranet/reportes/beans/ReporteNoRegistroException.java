package gob.pe.sunarp.extranet.reportes.beans;

public class ReporteNoRegistroException extends Exception {
	
	private String forward;
	
	public ReporteNoRegistroException(String forward){
		this.forward = forward;
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

