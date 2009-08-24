package gob.pe.sunarp.extranet.prepago.bean;
public class ConstanciaPagoException extends Exception {

	private String forward;
	
	public ConstanciaPagoException(String newForward){
		this.forward = newForward;
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

