package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
 *Bean VehiGrava
 *@version 1
 *Xelehull
 */

public class VehiParticipaGrava extends SunarpBean{
	
	
	private String nombres = " "; /** ALT + 255 **/
	private String descripcion = " ";


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

