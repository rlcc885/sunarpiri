package gob.pe.sunarp.extranet.transaction.bean;
import gob.pe.sunarp.extranet.transaction.TipoServicio;

public class LogAuditoriaConsultaPartidaBean extends TransactionBean{
	private String tipoConsPartida; // 1 = Partida, 2 = Ficha, 3 = Folio, 4 = Placa
	private String libTomFol;
	private String numPartFic;	//Identifica el Número de Partida o Número de Ficha o Número de Placa
	private String oficRegId;
	private String regPubId;
	private String tipoBusq;

	/**
	 * Gets the tipoConsPartida
	 * @return Returns a String
	 */
	public String getTipoConsPartida() {
		return tipoConsPartida;
	}
	/**
	 * Sets the tipoConsPartida
	 * @param tipoConsPartida The tipoConsPartida to set
	 */
	public void setTipoConsPartida(String tipoConsPartida) {
		this.tipoConsPartida = tipoConsPartida;
	}

	/**
	 * Gets the libTomFol
	 * @return Returns a String
	 */
	public String getLibTomFol() {
		return libTomFol;
	}
	/**
	 * Sets the libTomFol
	 * @param libTomFol The libTomFol to set
	 */
	public void setLibTomFol(String libTomFol) {
		this.libTomFol = libTomFol;
	}

	/**
	 * Gets the numPartFic
	 * @return Returns a String
	 */
	public String getNumPartFic() {
		return numPartFic;
	}
	/**
	 * Sets the numPartFic
	 * @param numPartFic The numPartFic to set
	 */
	public void setNumPartFic(String numPartFic) {
		this.numPartFic = numPartFic;
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
	 * Gets the tipoBusq
	 * @return Returns a String
	 */
	public String getTipoBusq() {
		return tipoBusq;
	}
	/**
	 * Sets the tipoBusq
	 * @param tipoBusq The tipoBusq to set
	 */
	public void setTipoBusq(String tipoBusq) {
		this.tipoBusq = tipoBusq;
	}

}

