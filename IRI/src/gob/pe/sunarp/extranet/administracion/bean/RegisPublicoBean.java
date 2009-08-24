package gob.pe.sunarp.extranet.administracion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class RegisPublicoBean extends SunarpBean{

	private String regPubID;
	private String nombre;
	private String siglas;
	/**
	 * Gets the regPubID
	 * @return Returns a String
	 */
	public String getRegPubID() {
		return regPubID;
	}
	/**
	 * Sets the regPubID
	 * @param regPubID The regPubID to set
	 */
	public void setRegPubID(String regPubID) {
		this.regPubID = regPubID;
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

	/**
	 * Gets the siglas
	 * @return Returns a String
	 */
	public String getSiglas() {
		return siglas;
	}
	/**
	 * Sets the siglas
	 * @param siglas The siglas to set
	 */
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

}

