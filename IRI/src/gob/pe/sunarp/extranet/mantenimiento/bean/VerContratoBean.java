package gob.pe.sunarp.extranet.mantenimiento.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class VerContratoBean extends SunarpBean{
	
	private String verContratoId="";
	private String verContrato="";
	private String fechaHoraCreacion="";
	/**
	 * Gets the verContratoId
	 * @return Returns a String
	 */
	public String getVerContratoId() {
		return verContratoId;
	}
	/**
	 * Sets the verContratoId
	 * @param verContratoId The verContratoId to set
	 */
	public void setVerContratoId(String verContratoId) {
		this.verContratoId = verContratoId;
	}

	/**
	 * Gets the verContrato
	 * @return Returns a String
	 */
	public String getVerContrato() {
		return verContrato;
	}
	/**
	 * Sets the verContrato
	 * @param verContrato The verContrato to set
	 */
	public void setVerContrato(String verContrato) {
		this.verContrato = verContrato;
	}


	/**
	 * Gets the fechaHoraCreacion
	 * @return Returns a String
	 */
	public String getFechaHoraCreacion() {
		return fechaHoraCreacion;
	}
	/**
	 * Sets the fechaHoraCreacion
	 * @param fechaHoraCreacion The fechaHoraCreacion to set
	 */
	public void setFechaHoraCreacion(String fechaHoraCreacion) {
		this.fechaHoraCreacion = fechaHoraCreacion;
	}

}

