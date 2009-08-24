package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class RptTransaccionBean extends SunarpBean{
	
	String razonSocial;
	String numDoc;
	String peJuriID;
	String a;
	/**
	 * Gets the peJuriID
	 * @return Returns a String
	 */
	public String getPeJuriID() {
		return peJuriID;
	}
	/**
	 * Sets the peJuriID
	 * @param peJuriID The peJuriID to set
	 */
	public void setPeJuriID(String peJuriID) {
		this.peJuriID = peJuriID;
	}

	/**
	 * Gets the razonSocial
	 * @return Returns a String
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * Sets the razonSocial
	 * @param razonSocial The razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Gets the numDoc
	 * @return Returns a String
	 */
	public String getNumDoc() {
		return numDoc;
	}
	/**
	 * Sets the numDoc
	 * @param numDoc The numDoc to set
	 */
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

}

