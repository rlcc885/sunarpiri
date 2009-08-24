package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class OrganizacionBean extends SunarpBean{
	
	private String peJuriId;
	private String personaId;
	private String lpId;
	private String razonSocial;
	private String nroDocumento;
	/**
	 * Gets the lpId
	 * @return Returns a String
	 */
	public String getLpId() {
		return lpId;
	}
	/**
	 * Sets the lpId
	 * @param lpId The lpId to set
	 */
	public void setLpId(String lpId) {
		this.lpId = lpId;
	}

	/**
	 * Gets the peJuriId
	 * @return Returns a String
	 */
	public String getPeJuriId() {
		return peJuriId;
	}
	/**
	 * Sets the peJuriId
	 * @param peJuriId The peJuriId to set
	 */
	public void setPeJuriId(String peJuriId) {
		this.peJuriId = peJuriId;
	}

	/**
	 * Gets the personaId
	 * @return Returns a String
	 */
	public String getPersonaId() {
		return personaId;
	}
	/**
	 * Sets the personaId
	 * @param personaId The personaId to set
	 */
	public void setPersonaId(String personaId) {
		this.personaId = personaId;
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
	 * Gets the nroDocumento
	 * @return Returns a String
	 */
	public String getNroDocumento() {
		return nroDocumento;
	}
	/**
	 * Sets the nroDocumento
	 * @param nroDocumento The nroDocumento to set
	 */
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

}

