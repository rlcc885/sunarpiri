package gob.pe.sunarp.extranet.reportegeneral.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class OficinasBean extends SunarpBean{
	
	private String regPubId;
	private String oficRegId;
	private String nombre;
	/**
	 * Gets the regPubId
	 * @return Returns a String
	 */
	public String getRegPubId() {
		return regPubId;
	}
	/**
	 * Sets the regPubId
	 * @param regPubId The regPubId to set
	 */
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}

	/**
	 * Gets the oficRegId
	 * @return Returns a String
	 */
	public String getOficRegId() {
		return oficRegId;
	}
	/**
	 * Sets the oficRegId
	 * @param oficRegId The oficRegId to set
	 */
	public void setOficRegId(String oficRegId) {
		this.oficRegId = oficRegId;
	}

	/**
	 * Gets the nombre
	 * @return Returns a String
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Sets the nombre
	 * @param nombre The nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

