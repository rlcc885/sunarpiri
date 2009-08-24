package gob.pe.sunarp.extranet.common.cm;

import java.util.Date;

public class AsientoCM extends ObjetoCM {
	private int numAsiento;
	private int numOrdenAsiento;
	private String anoTitulo;
	private String numTitulo;
	private String codActoOriginal;
	private String codActoGeneral;
	private int repeticion;
	private Date fecha;
	private boolean mineria = false;

	public String toString() {
		return new StringBuffer(super.toString())
				.append(" numAsiento=").append(numAsiento).append(" numOrdenAsiento=").append(numOrdenAsiento)
				.append(" anoTitulo=").append(anoTitulo).append(" numTitulo=").append(numTitulo)
				.append(" codActoOriginal=").append(codActoOriginal).append(" codActoGeneral=").append(codActoGeneral)
				.append(" repeticion=").append(repeticion).append(" fecha=").append(fecha)
				.append(" mineria=").append(mineria).toString();
	}
	/**
	 * Gets the numAsiento
	 * @return Returns a int
	 */
	public int getNumAsiento() {
		return numAsiento;
	}
	/**
	 * Sets the numAsiento
	 * @param numAsiento The numAsiento to set
	 */
	public void setNumAsiento(int numAsiento) {
		this.numAsiento = numAsiento;
	}

	/**
	 * Gets the anoTitulo
	 * @return Returns a String
	 */
	public String getAnoTitulo() {
		if (anoTitulo == null) anoTitulo = "9999";
		return anoTitulo;
	}
	/**
	 * Sets the anoTitulo
	 * @param anoTitulo The anoTitulo to set
	 */
	public void setAnoTitulo(String anoTitulo) {
		this.anoTitulo = anoTitulo;
	}

	/**
	 * Gets the numTitulo
	 * @return Returns a String
	 */
	public String getNumTitulo() {
		if (numTitulo == null) numTitulo = "99999999";
		return numTitulo;
	}
	/**
	 * Sets the numTitulo
	 * @param numTitulo The numTitulo to set
	 */
	public void setNumTitulo(String numTitulo) {
		this.numTitulo = numTitulo;
	}

	/**
	 * Gets the codActoOriginal
	 * @return Returns a String
	 */
	public String getCodActoOriginal() {
		return codActoOriginal;
	}
	/**
	 * Sets the codActoOriginal
	 * @param codActoOriginal The codActoOriginal to set
	 */
	public void setCodActoOriginal(String codActoOriginal) {
		this.codActoOriginal = codActoOriginal;
	}

	/**
	 * Gets the fecha
	 * @return Returns a Date
	 */
	public Date getFecha() {
//		if (fecha == null)
//			fecha = new Date(253402318799000L);
			// Fri Dec 31 23:59:59 GMT-05:00 9999 que es una fecha que no se va a encontrar
		return fecha;
	}
	/**
	 * Sets the fecha
	 * @param fecha The fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the repeticion
	 * @return Returns a int
	 */
	public int getRepeticion() {
		return repeticion;
	}
	/**
	 * Sets the repeticion
	 * @param repeticion The repeticion to set
	 */
	public void setRepeticion(int repeticion) {
		this.repeticion = repeticion;
	}

	/**
	 * Gets the numOrdenAsiento
	 * @return Returns a int
	 */
	public int getNumOrdenAsiento() {
		return numOrdenAsiento;
	}
	/**
	 * Sets the numOrdenAsiento
	 * @param numOrdenAsiento The numOrdenAsiento to set
	 */
	public void setNumOrdenAsiento(int numOrdenAsiento) {
		this.numOrdenAsiento = numOrdenAsiento;
	}

	/**
	 * Gets the mineria
	 * @return Returns a boolean
	 */
	public boolean getMineria() {
		return mineria;
	}
	/**
	 * Sets the mineria
	 * @param mineria The mineria to set
	 */
	public void setMineria(boolean mineria) {
		this.mineria = mineria;
	}

	/**
	 * Gets the codActoGeneral
	 * @return Returns a String
	 */
	public String getCodActoGeneral() {
		return codActoGeneral;
	}
	/**
	 * Sets the codActoGeneral
	 * @param codActoGeneral The codActoGeneral to set
	 */
	public void setCodActoGeneral(String codActoGeneral) {
		this.codActoGeneral = codActoGeneral;
	}

}

