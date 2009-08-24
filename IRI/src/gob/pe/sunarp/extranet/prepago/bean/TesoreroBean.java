package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class TesoreroBean extends SunarpBean{


	private String cuenta_id;
	private String usr_id;
	private String ape_pat;
	private String ape_mat;
	private String nombres;
	/**
	 * Gets the cuenta_id
	 * @return Returns a String
	 */
	public String getCuenta_id() {
		return cuenta_id;
	}
	/**
	 * Sets the cuenta_id
	 * @param cuenta_id The cuenta_id to set
	 */
	public void setCuenta_id(String cuenta_id) {
		this.cuenta_id = cuenta_id;
	}

	/**
	 * Gets the usr_id
	 * @return Returns a String
	 */
	public String getUsr_id() {
		return usr_id;
	}
	/**
	 * Sets the usr_id
	 * @param usr_id The usr_id to set
	 */
	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}

	/**
	 * Gets the ape_pat
	 * @return Returns a String
	 */
	public String getApe_pat() {
		return ape_pat;
	}
	/**
	 * Sets the ape_pat
	 * @param ape_pat The ape_pat to set
	 */
	public void setApe_pat(String ape_pat) {
		this.ape_pat = ape_pat;
	}

	/**
	 * Gets the ape_mat
	 * @return Returns a String
	 */
	public String getApe_mat() {
		return ape_mat;
	}
	/**
	 * Sets the ape_mat
	 * @param ape_mat The ape_mat to set
	 */
	public void setApe_mat(String ape_mat) {
		this.ape_mat = ape_mat;
	}

	/**
	 * Gets the nombres
	 * @return Returns a String
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * Sets the nombres
	 * @param nombres The nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

}


