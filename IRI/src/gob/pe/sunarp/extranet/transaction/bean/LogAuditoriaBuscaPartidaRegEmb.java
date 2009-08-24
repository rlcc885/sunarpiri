package gob.pe.sunarp.extranet.transaction.bean;

public class LogAuditoriaBuscaPartidaRegEmb
	extends LogAuditoriaBusqPartidaBean {

	private String tipoParam;
	private String tipoEmb;
	private String valor;
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
	 * Gets the tipoEmb
	 * @return Returns a String
	 */
	public String getTipoEmb() {
		return tipoEmb;
	}
	/**
	 * Sets the tipoEmb
	 * @param tipoEmb The tipoEmb to set
	 */
	public void setTipoEmb(String tipoEmb) {
		this.tipoEmb = tipoEmb;
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
}

