package gob.pe.sunarp.extranet.transaction.bean;

public class LogAuditoriaBuscaPartidaRegAereoBean
	extends LogAuditoriaBusqPartidaBean {

	private String tipoParam;
	private String valor;
	private String tipoTitular;
	//private String audBusqRegAeroId;
	/**
	 * Gets the tipoParam
	 * @return Returns a String
	 */
	public String getTipoParam() {
		return tipoParam;
	}
	/**
	 * Sets the tipoParam
	 * @param tipoParam The tipoParam to set
	 */
	public void setTipoParam(String tipoParam) {
		this.tipoParam = tipoParam;
	}

	/**
	 * Gets the valor
	 * @return Returns a String
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * Sets the valor
	 * @param valor The valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * Gets the tipoTitular
	 * @return Returns a String
	 */
	public String getTipoTitular() {
		return tipoTitular;
	}
	/**
	 * Sets the tipoTitular
	 * @param tipoTitular The tipoTitular to set
	 */
	public void setTipoTitular(String tipoTitular) {
		this.tipoTitular = tipoTitular;
	}

}

