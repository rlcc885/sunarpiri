package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class EstadoTituloBean extends SunarpBean{


	private String estado_id;
	private String descripcion;
	/**
	 * Gets the estado_id
	 * @return Returns a String
	 */
	public String getEstado_id() {
		return estado_id;
	}
	/**
	 * Sets the estado_id
	 * @param estado_id The estado_id to set
	 */
	public void setEstado_id(String estado_id) {
		this.estado_id = estado_id;
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

