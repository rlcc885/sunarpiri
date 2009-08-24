package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class SolicitudXCargaBean extends SunarpBean{
	
	
	private String solicitud_id;
	private String cuenta_id;
	private String rol;
	private String cta_id_reg;
	private String prioridad;
	private String estado;
	private String ts_crea;
	private String ts_modi;
	private String usr_crea;
	private String usr_modi;	

	/**
	 * Gets the solicitud_id
	 * @return Returns a String
	 */
	public String getSolicitud_id() {
		return solicitud_id;
	}
	/**
	 * Sets the solicitud_id
	 * @param solicitud_id The solicitud_id to set
	 */
	public void setSolicitud_id(String solicitud_id) {
		this.solicitud_id = solicitud_id;
	}

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
	 * Gets the rol
	 * @return Returns a String
	 */
	public String getRol() {
		return rol;
	}
	/**
	 * Sets the rol
	 * @param rol The rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Gets the cta_id_reg
	 * @return Returns a String
	 */
	public String getCta_id_reg() {
		return cta_id_reg;
	}
	/**
	 * Sets the cta_id_reg
	 * @param cta_id_reg The cta_id_reg to set
	 */
	public void setCta_id_reg(String cta_id_reg) {
		this.cta_id_reg = cta_id_reg;
	}

	/**
	 * Gets the prioridad
	 * @return Returns a String
	 */
	public String getPrioridad() {
		return prioridad;
	}
	/**
	 * Sets the prioridad
	 * @param prioridad The prioridad to set
	 */
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	/**
	 * Gets the ts_crea
	 * @return Returns a String
	 */
	public String getTs_crea() {
		return ts_crea;
	}
	/**
	 * Sets the ts_crea
	 * @param ts_crea The ts_crea to set
	 */
	public void setTs_crea(String ts_crea) {
		this.ts_crea = ts_crea;
	}

	/**
	 * Gets the ts_modi
	 * @return Returns a String
	 */
	public String getTs_modi() {
		return ts_modi;
	}
	/**
	 * Sets the ts_modi
	 * @param ts_modi The ts_modi to set
	 */
	public void setTs_modi(String ts_modi) {
		this.ts_modi = ts_modi;
	}

	/**
	 * Gets the usr_crea
	 * @return Returns a String
	 */
	public String getUsr_crea() {
		return usr_crea;
	}
	/**
	 * Sets the usr_crea
	 * @param usr_crea The usr_crea to set
	 */
	public void setUsr_crea(String usr_crea) {
		this.usr_crea = usr_crea;
	}

	/**
	 * Gets the usr_modi
	 * @return Returns a String
	 */
	public String getUsr_modi() {
		return usr_modi;
	}
	/**
	 * Sets the usr_modi
	 * @param usr_modi The usr_modi to set
	 */
	public void setUsr_modi(String usr_modi) {
		this.usr_modi = usr_modi;
	}

	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

}

