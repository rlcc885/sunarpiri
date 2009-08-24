package gob.pe.sunarp.extranet.common.cm;


public class FichaCM extends ObjetoCM {
	private String numFicha;
	private String numFichaBis;
	private String[] fichasOIDMineria;
	private int totalfichasOIDMineria;


	public String toString() {
		return new StringBuffer(super.toString())
				.append(" numFicha=").append(numFicha).append(" numFichaBis=").append(numFichaBis)
				.append(" fichasOIDMineria=").append(fichasOIDMineria).append(" totalfichasOIDMineria=").append(totalfichasOIDMineria).toString();
	}

	/**
	 * Gets the numFicha
	 * @return Returns a String
	 */
	public String getNumFicha() {
		return numFicha;
	}
	/**
	 * Sets the numFicha
	 * @param numFicha The numFicha to set
	 */
	public void setNumFicha(String numFicha) {
		this.numFicha = numFicha;
	}

	/**
	 * Gets the numFichaBis
	 * @return Returns a String
	 */
	public String getNumFichaBis() {
		return numFichaBis;
	}
	/**
	 * Sets the numFichaBis
	 * @param numFichaBis The numFichaBis to set
	 */
	public void setNumFichaBis(String numFichaBis) {
		this.numFichaBis = numFichaBis;
	}

	/**
	 * Gets the fichasOIDMineria
	 * @return Returns a PartidaMineriaFichasSOutput[]
	 */
	public String[] getFichasOIDMineria() {
		return fichasOIDMineria;
	}
	/**
	 * Sets the fichasOIDMineria
	 * @param fichasOIDMineria The fichasOIDMineria to set
	 */
	public void setFichasOIDMineria(String[] fichasOIDMineria) {
		this.fichasOIDMineria = fichasOIDMineria;
	}

	/**
	 * Gets the totalfichasOIDMineria
	 * @return Returns a int
	 */
	public int getTotalfichasOIDMineria() {
		return totalfichasOIDMineria;
	}
	/**
	 * Sets the totalfichasOIDMineria
	 * @param totalfichasOIDMineria The totalfichasOIDMineria to set
	 */
	public void setTotalfichasOIDMineria(int totalfichasOIDMineria) {
		this.totalfichasOIDMineria = totalfichasOIDMineria;
	}

}

