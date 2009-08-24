package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
 *Bean VehiPropi
 *@version 2
 *Made in KumaX
 */

public class VehiPropi extends SunarpBean{
	
	
	private String propietario = " "; /** ALT + 255 **/
	private String direccion = " ";
	private String expediente = " ";
	private String ubigeo = " ";
			
	//***********************************SETTERS Y GETTERS************************
	
	

	/**
	 * Gets the propietario
	 * @return Returns a String
	 */
	public String getPropietario()
	{
		return propietario;
	}
	/**
	 * Sets the propietario
	 * @param propietario The propietario to set
	 */
	public void setPropietario(String propietario)
	{
		this.propietario = propietario;
	}
	
	/**
	 * Gets the direccion
	 * @return Returns a String
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * Sets the direccion
	 * @param direccion The direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	/**
	 * Gets the expediente
	 * @return Returns a String
	 */
	public String getExpediente() {
		return expediente;
	}
	/**
	 * Sets the expediente
	 * @param expediente The expediente to set
	 */
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	/**
	 * Gets the ubigeo
	 * @return Returns a String
	 */
	public String getUbigeo() {
		return ubigeo;
	}
	/**
	 * Sets the ubigeo
	 * @param ubigeo The ubigeo to set
	 */
	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

}