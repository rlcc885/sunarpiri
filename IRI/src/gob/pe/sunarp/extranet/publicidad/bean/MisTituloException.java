package gob.pe.sunarp.extranet.publicidad.bean;
public class MisTituloException extends Exception {

	private String forward;
	
	public MisTituloException(String newForward){
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

