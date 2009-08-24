package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class IngresoBean extends SunarpBean{
	

	private String oficina;
	private long organizacion;
	private long montoOrg;
	private long individual;
	private long montoIndiv;
	private long total;
	private double porcentaje;
	
	private String regPubId;
	private String oficRegId;
	
	/**
	 * Gets the oficina
	 * @return Returns a String
	 */
	public String getOficina() {
		return oficina;
	}
	/**
	 * Sets the oficina
	 * @param oficina The oficina to set
	 */
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	/**
	 * Gets the organizacion
	 * @return Returns a String
	 */
	public long getOrganizacion() {
		return organizacion;
	}
	/**
	 * Sets the organizacion
	 * @param organizacion The organizacion to set
	 */
	public void setOrganizacion(long organizacion) {
		this.organizacion = organizacion;
	}

	/**
	 * Gets the montoOrg
	 * @return Returns a long
	 */
	public long getMontoOrg() {
		return montoOrg;
	}
	/**
	 * Sets the montoOrg
	 * @param montoOrg The montoOrg to set
	 */
	public void setMontoOrg(long montoOrg) {
		this.montoOrg = montoOrg;
	}

	/**
	 * Gets the individual
	 * @return Returns a String
	 */
	public long getIndividual() {
		return individual;
	}
	/**
	 * Sets the individual
	 * @param individual The individual to set
	 */
	public void setIndividual(long individual) {
		this.individual = individual;
	}

	/**
	 * Gets the montoIndiv
	 * @return Returns a long
	 */
	public long getMontoIndiv() {
		return montoIndiv;
	}
	/**
	 * Sets the montoIndiv
	 * @param montoIndiv The montoIndiv to set
	 */
	public void setMontoIndiv(long montoIndiv) {
		this.montoIndiv = montoIndiv;
	}

	/**
	 * Gets the total
	 * @return Returns a long
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * Sets the total
	 * @param total The total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * Gets the porcentaje
	 * @return Returns a short
	 */
	public double getPorcentaje() {
		return porcentaje;
	}
	/**
	 * Sets the porcentaje
	 * @param porcentaje The porcentaje to set
	 */
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
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

}

