package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class TransaccionBean extends SunarpBean{
	

	private String razonSocial;
	private String userId;
	private String fecha;
	private String hora;
	private String nomOficinaRegistral;
	private String nomServicio;
	private String costo;
	private String idDocumento;

	/**
	 * Gets the razonSocial
	 * @return Returns a String
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * Sets the razonSocial
	 * @param razonSocial The razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Gets the userId
	 * @return Returns a String
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Sets the userId
	 * @param userId The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the fecha
	 * @return Returns a String
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * Sets the fecha
	 * @param fecha The fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the hora
	 * @return Returns a String
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * Sets the hora
	 * @param hora The hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * Gets the nomOficinaRegistral
	 * @return Returns a String
	 */
	public String getNomOficinaRegistral() {
		return nomOficinaRegistral;
	}
	/**
	 * Sets the nomOficinaRegistral
	 * @param nomOficinaRegistral The nomOficinaRegistral to set
	 */
	public void setNomOficinaRegistral(String nomOficinaRegistral) {
		this.nomOficinaRegistral = nomOficinaRegistral;
	}

	/**
	 * Gets the nomServicio
	 * @return Returns a String
	 */
	public String getNomServicio() {
		return nomServicio;
	}
	/**
	 * Sets the nomServicio
	 * @param nomServicio The nomServicio to set
	 */
	public void setNomServicio(String nomServicio) {
		this.nomServicio = nomServicio;
	}

	/**
	 * Gets the costo
	 * @return Returns a String
	 */
	public String getCosto() {
		return costo;
	}
	/**
	 * Sets the costo
	 * @param costo The costo to set
	 */
	public void setCosto(String costo) {
		this.costo = costo;
	}

	/**
	 * Gets the idDocumento
	 * @return Returns a String
	 */
	public String getIdDocumento() {
		return idDocumento;
	}
	/**
	 * Sets the idDocumento
	 * @param idDocumento The idDocumento to set
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

}

