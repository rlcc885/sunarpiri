package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class BancoBean extends SunarpBean{


	private String id;
	private String descripcion;
	/**
	 * Gets the id
	 * @return Returns a String
	 */
	public String getId() {
		return id;
	}
	/**
	 * Sets the id
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the descripcion
	 * @return Returns a String
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Sets the descripcion
	 * @param descripcion The descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

