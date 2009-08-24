package gob.pe.sunarp.extranet.transaction.bean;


public class LogAuditoriaCertificadoBean extends TransactionBean {
//public class LogAuditoriaCertificadoBean{

	private String solicitud_id;
	private String oficRegId;
	private String regPubId;
	private String tipoCertificado;
	private String consumoId;
	private String cantidad = "1";
	
	
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
	 * Gets the oficRegId
	 * @return Returns a String
	 */
	public String getOficRegId() {
		return oficRegId;
	}
	/**
	 * Sets the oficRegId
	 * @param oficRegId The oficRegId to set
	 */
	public void setOficRegId(String oficRegId) {
		this.oficRegId = oficRegId;
	}

	/**
	 * Gets the regPubId
	 * @return Returns a String
	 */
	public String getRegPubId() {
		return regPubId;
	}
	/**
	 * Sets the regPubId
	 * @param regPubId The regPubId to set
	 */
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}

	/**
	 * Gets the tipoCertificado
	 * @return Returns a String
	 */
	public String getTipoCertificado() {
		return tipoCertificado;
	}
	/**
	 * Sets the tipoCertificado
	 * @param tipoCertificado The tipoCertificado to set
	 */
	public void setTipoCertificado(String tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}

	/**
	 * Gets the consumoId
	 * @return Returns a String
	 */
	public String getConsumoId() {
		return consumoId;
	}
	/**
	 * Sets the consumoId
	 * @param consumoId The consumoId to set
	 */
	public void setConsumoId(String consumoId) {
		this.consumoId = consumoId;
	}

	/**
	 * Gets the cantidad
	 * @return Returns a String
	 */
	public String getCantidad() {
		return cantidad;
	}
	/**
	 * Sets the cantidad
	 * @param cantidad The cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

}