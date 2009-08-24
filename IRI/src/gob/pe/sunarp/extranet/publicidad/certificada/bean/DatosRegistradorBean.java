package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class DatosRegistradorBean extends SunarpBean{
	
	private String apellidoPaterno = "";
	private String apellidoMaterno = "";
	private String nombre = "";
	private String cuentaId = "";
	private String correo_electronico = "";
	
	/**
	 * Gets the apellidoPaterno
	 * @return Returns a String
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	/**
	 * Sets the apellidoPaterno
	 * @param apellidoPaterno The apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * Gets the apellidoMaterno
	 * @return Returns a String
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	/**
	 * Sets the apellidoMaterno
	 * @param apellidoMaterno The apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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
	 * Gets the cuentaId
	 * @return Returns a String
	 */
	public String getCuentaId() {
		return cuentaId;
	}
	/**
	 * Sets the cuentaId
	 * @param cuentaId The cuentaId to set
	 */
	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}

	/**
	 * Gets the correo_electronico
	 * @return Returns a String
	 */
	public String getCorreo_electronico() {
		return correo_electronico;
	}
	/**
	 * Sets the correo_electronico
	 * @param correo_electronico The correo_electronico to set
	 */
	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}

}

