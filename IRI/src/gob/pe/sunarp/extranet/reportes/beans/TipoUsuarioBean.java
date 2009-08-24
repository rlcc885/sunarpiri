package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class TipoUsuarioBean extends SunarpBean{
	
	private String codigoTipoUsuario;
	private String nombreTipoUsuario;
	private String codigoTipoUsuarioSelected;
	/**
	 * Gets the codigoTipoUsuario
	 * @return Returns a String
	 */
	public String getCodigoTipoUsuario() {
		return codigoTipoUsuario;
	}
	/**
	 * Sets the codigoTipoUsuario
	 * @param codigoTipoUsuario The codigoTipoUsuario to set
	 */
	public void setCodigoTipoUsuario(String codigoTipoUsuario) {
		this.codigoTipoUsuario = codigoTipoUsuario;
	}

	/**
	 * Gets the nombreTipoUsuario
	 * @return Returns a String
	 */
	public String getNombreTipoUsuario() {
		return nombreTipoUsuario;
	}
	/**
	 * Sets the nombreTipoUsuario
	 * @param nombreTipoUsuario The nombreTipoUsuario to set
	 */
	public void setNombreTipoUsuario(String nombreTipoUsuario) {
		this.nombreTipoUsuario = nombreTipoUsuario;
	}

	/**
	 * Gets the codigoTipoUsuarioSelected
	 * @return Returns a String
	 */
	public String getCodigoTipoUsuarioSelected() {
		return codigoTipoUsuarioSelected;
	}
	/**
	 * Sets the codigoTipoUsuarioSelected
	 * @param codigoTipoUsuarioSelected The codigoTipoUsuarioSelected to set
	 */
	public void setCodigoTipoUsuarioSelected(String codigoTipoUsuarioSelected) {
		this.codigoTipoUsuarioSelected = codigoTipoUsuarioSelected;
	}

}

