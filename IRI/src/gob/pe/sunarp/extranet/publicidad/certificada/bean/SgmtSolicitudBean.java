package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class SgmtSolicitudBean extends SunarpBean{
	
	
private String sgmt_solicitud_id;
private String solicitud_id;
private String estado_inicial;
private String estado_final;
private String ts_movimiento;
private String usr_movimiento;


	/**
	 * Gets the sgmt_solicitud_id
	 * @return Returns a String
	 */
	public String getSgmt_solicitud_id() {
		return sgmt_solicitud_id;
	}
	/**
	 * Sets the sgmt_solicitud_id
	 * @param sgmt_solicitud_id The sgmt_solicitud_id to set
	 */
	public void setSgmt_solicitud_id(String sgmt_solicitud_id) {
		this.sgmt_solicitud_id = sgmt_solicitud_id;
	}

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
	 * Gets the estado_inicial
	 * @return Returns a String
	 */
	public String getEstado_inicial() {
		return estado_inicial;
	}
	/**
	 * Sets the estado_inicial
	 * @param estado_inicial The estado_inicial to set
	 */
	public void setEstado_inicial(String estado_inicial) {
		this.estado_inicial = estado_inicial;
	}

	/**
	 * Gets the estado_final
	 * @return Returns a String
	 */
	public String getEstado_final() {
		return estado_final;
	}
	/**
	 * Sets the estado_final
	 * @param estado_final The estado_final to set
	 */
	public void setEstado_final(String estado_final) {
		this.estado_final = estado_final;
	}

	/**
	 * Gets the ts_movimiento
	 * @return Returns a String
	 */
	public String getTs_movimiento() {
		return ts_movimiento;
	}
	/**
	 * Sets the ts_movimiento
	 * @param ts_movimiento The ts_movimiento to set
	 */
	public void setTs_movimiento(String ts_movimiento) {
		this.ts_movimiento = ts_movimiento;
	}

	/**
	 * Gets the usr_movimiento
	 * @return Returns a String
	 */
	public String getUsr_movimiento() {
		return usr_movimiento;
	}
	/**
	 * Sets the usr_movimiento
	 * @param usr_movimiento The usr_movimiento to set
	 */
	public void setUsr_movimiento(String usr_movimiento) {
		this.usr_movimiento = usr_movimiento;
	}

}

