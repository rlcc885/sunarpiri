package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class PagoTarjetaBean extends SunarpBean{



	private String medioId;
	private String monto;
	private String numItems = "1";
	private String userId;
	private String personaId;
	private String numOrden;
	private String numero;
	private String ano;
	private String mes;
	private String solicitudId;
	//SAUL
	//SE COMENTA CAMPO GLOSA
	//private String glosa;
	private String url;
	
	
	/**
	 * Gets the medioId
	 * @return Returns a String
	 */
	public String getMedioId() {
		return medioId;
	}
	/**
	 * Sets the medioId
	 * @param medioId The medioId to set
	 */
	public void setMedioId(String medioId) {
		this.medioId = medioId;
	}

	/**
	 * Gets the monto
	 * @return Returns a String
	 */
	public String getMonto() {
		return monto;
	}
	/**
	 * Sets the monto
	 * @param monto The monto to set
	 */
	public void setMonto(String monto) {
		this.monto = monto;
	}

	/**
	 * Gets the numItems
	 * @return Returns a String
	 */
	public String getNumItems() {
		return numItems;
	}
	/**
	 * Sets the numItems
	 * @param numItems The numItems to set
	 */
	public void setNumItems(String numItems) {
		this.numItems = numItems;
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
	 * Gets the personaId
	 * @return Returns a String
	 */
	public String getPersonaId() {
		return personaId;
	}
	/**
	 * Sets the personaId
	 * @param personaId The personaId to set
	 */
	public void setPersonaId(String personaId) {
		this.personaId = personaId;
	}

	/**
	 * Gets the numOrden
	 * @return Returns a String
	 */
	public String getNumOrden() {
		return numOrden;
	}
	/**
	 * Sets the numOrden
	 * @param numOrden The numOrden to set
	 */
	public void setNumOrden(String numOrden) {
		this.numOrden = numOrden;
	}

	/**
	 * Gets the numero
	 * @return Returns a String
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * Sets the numero
	 * @param numero The numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Gets the ano
	 * @return Returns a String
	 */
	public String getAno() {
		return ano;
	}
	/**
	 * Sets the ano
	 * @param ano The ano to set
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * Gets the mes
	 * @return Returns a String
	 */
	public String getMes() {
		return mes;
	}
	/**
	 * Sets the mes
	 * @param mes The mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * Gets the solicitudId
	 * @return Returns a String
	 */
	public String getSolicitudId() {
		return solicitudId;
	}
	/**
	 * Sets the solicitudId
	 * @param solicitudId The solicitudId to set
	 */
	public void setSolicitudId(String solicitudId) {
		this.solicitudId = solicitudId;
	}

	/**
	 * Gets the url
	 * @return Returns a String
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Sets the url
	 * @param url The url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}*/

}

