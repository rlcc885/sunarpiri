package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class RegistroPublicoBean extends SunarpBean{
	
	private String codigoRegistroPublico;
	private String nombreRegistroPublico;
	private String codigoRegistroPublicoSelected;
	/**
	 * Gets the codigoRegistroPublico
	 * @return Returns a String
	 */
	public String getCodigoRegistroPublico() {
		return codigoRegistroPublico;
	}
	/**
	 * Sets the codigoRegistroPublico
	 * @param codigoRegistroPublico The codigoRegistroPublico to set
	 */
	public void setCodigoRegistroPublico(String codigoRegistroPublico) {
		this.codigoRegistroPublico = codigoRegistroPublico;
	}

	/**
	 * Gets the nombreRegistroPublico
	 * @return Returns a String
	 */
	public String getNombreRegistroPublico() {
		return nombreRegistroPublico;
	}
	/**
	 * Sets the nombreRegistroPublico
	 * @param nombreRegistroPublico The nombreRegistroPublico to set
	 */
	public void setNombreRegistroPublico(String nombreRegistroPublico) {
		this.nombreRegistroPublico = nombreRegistroPublico;
	}

	/**
	 * Gets the codigoRegistroPublicoSelected
	 * @return Returns a String
	 */
	public String getCodigoRegistroPublicoSelected() {
		return codigoRegistroPublicoSelected;
	}
	/**
	 * Sets the codigoRegistroPublicoSelected
	 * @param codigoRegistroPublicoSelected The codigoRegistroPublicoSelected to set
	 */
	public void setCodigoRegistroPublicoSelected(String codigoRegistroPublicoSelected) {
		this.codigoRegistroPublicoSelected = codigoRegistroPublicoSelected;
	}

}

