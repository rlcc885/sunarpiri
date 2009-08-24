package gob.pe.sunarp.extranet.common.cm;
public class FolioCM extends ObjetoCM {
	private int secuencial;
	private String numTomo;
	private String numFolio;

	public String toString() {
		return new StringBuffer(super.toString())
				.append(" secuencial=").append(secuencial).append(" numTomo=").append(numTomo)
				.append(" numFolio=").append(numFolio).toString();
	}
	/**
	 * Gets the numTomo
	 * @return Returns a String
	 */
	public String getNumTomo() {
		return numTomo;
	}
	/**
	 * Sets the numTomo
	 * @param numTomo The numTomo to set
	 */
	public void setNumTomo(String numTomo) {
		this.numTomo = numTomo;
	}

	/**
	 * Gets the numFolio
	 * @return Returns a String
	 */
	public String getNumFolio() {
		return numFolio;
	}
	/**
	 * Sets the numFolio
	 * @param numFolio The numFolio to set
	 */
	public void setNumFolio(String numFolio) {
		this.numFolio = numFolio;
	}

	/**
	 * Gets the secuencial
	 * @return Returns a int
	 */
	public int getSecuencial() {
		return secuencial;
	}
	/**
	 * Sets the secuencial
	 * @param secuencial The secuencial to set
	 */
	public void setSecuencial(int secuencial) {
		this.secuencial = secuencial;
	}

}

