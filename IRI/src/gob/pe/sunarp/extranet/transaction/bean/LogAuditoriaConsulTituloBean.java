package gob.pe.sunarp.extranet.transaction.bean;

public class LogAuditoriaConsulTituloBean extends TransactionBean {

	private String AnoTitulo;
	private String NumTitulo;
	private String OficRegId;
	private String RegPubId;

	/**
	 * Gets the anoTitulo
	 * @return Returns a String
	 */
	public String getAnoTitulo() {
		return AnoTitulo;
	}
	/**
	 * Sets the anoTitulo
	 * @param anoTitulo The anoTitulo to set
	 */
	public void setAnoTitulo(String anoTitulo) {
		AnoTitulo = anoTitulo;
	}

	/**
	 * Gets the numTitulo
	 * @return Returns a String
	 */
	public String getNumTitulo() {
		return NumTitulo;
	}
	/**
	 * Sets the numTitulo
	 * @param numTitulo The numTitulo to set
	 */
	public void setNumTitulo(String numTitulo) {
		NumTitulo = numTitulo;
	}

	/**
	 * Gets the oficRegId
	 * @return Returns a String
	 */
	public String getOficRegId() {
		return OficRegId;
	}
	/**
	 * Sets the oficRegId
	 * @param oficRegId The oficRegId to set
	 */
	public void setOficRegId(String oficRegId) {
		OficRegId = oficRegId;
	}

	/**
	 * Gets the regPubId
	 * @return Returns a String
	 */
	public String getRegPubId() {
		return RegPubId;
	}
	/**
	 * Sets the regPubId
	 * @param regPubId The regPubId to set
	 */
	public void setRegPubId(String regPubId) {
		RegPubId = regPubId;
	}

}

