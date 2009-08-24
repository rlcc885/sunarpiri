package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class MovimientosTotalBean extends SunarpBean{
	
	private String saldoInicial;
	private String abonos;
	private String consumos;
	private String saldoFinal;
	private String lineaPrepago;
	/**
	 * Gets the saldoInicial
	 * @return Returns a String
	 */
	public String getSaldoInicial() {
		return saldoInicial;
	}
	/**
	 * Sets the saldoInicial
	 * @param saldoInicial The saldoInicial to set
	 */
	public void setSaldoInicial(String saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	/**
	 * Gets the abonos
	 * @return Returns a String
	 */
	public String getAbonos() {
		return abonos;
	}
	/**
	 * Sets the abonos
	 * @param abonos The abonos to set
	 */
	public void setAbonos(String abonos) {
		this.abonos = abonos;
	}

	/**
	 * Gets the consumos
	 * @return Returns a String
	 */
	public String getConsumos() {
		return consumos;
	}
	/**
	 * Sets the consumos
	 * @param consumos The consumos to set
	 */
	public void setConsumos(String consumos) {
		this.consumos = consumos;
	}

	/**
	 * Gets the saldoFinal
	 * @return Returns a String
	 */
	public String getSaldoFinal() {
		return saldoFinal;
	}
	/**
	 * Sets the saldoFinal
	 * @param saldoFinal The saldoFinal to set
	 */
	public void setSaldoFinal(String saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	/**
	 * Gets the lineaPrepago
	 * @return Returns a String
	 */
	public String getLineaPrepago() {
		return lineaPrepago;
	}
	/**
	 * Sets the lineaPrepago
	 * @param lineaPrepago The lineaPrepago to set
	 */
	public void setLineaPrepago(String lineaPrepago) {
		this.lineaPrepago = lineaPrepago;
	}

}

