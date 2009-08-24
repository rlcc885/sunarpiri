package gob.pe.sunarp.extranet.webservices.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class BuscaPartidaDetallePersonaJuridicaBean extends SunarpBean{

	private String zonaRegistral = null;
	private String oficinaRegistral = null;
	private String nroPartida = null;
	private String estado = null;
	private String ruc = null;
	private String razonSocial = null;
	private String libro = null;
	
	/**
	 * Gets the zonaRegistral
	 * @return Returns a String
	 */
	public String getZonaRegistral() {
		return zonaRegistral;
	}
	/**
	 * Sets the zonaRegistral
	 * @param zonaRegistral The zonaRegistral to set
	 */
	public void setZonaRegistral(String zonaRegistral) {
		this.zonaRegistral = zonaRegistral;
	}

	/**
	 * Gets the ruc
	 * @return Returns a String
	 */
	public String getRuc() {
		return ruc;
	}
	/**
	 * Sets the ruc
	 * @param ruc The ruc to set
	 */
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	/**
	 * Gets the razonSocial
	 * @return Returns a String
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * Sets the razonSocial
	 * @param razonSocial The razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Gets the oficinaRegistral
	 * @return Returns a String
	 */
	public String getOficinaRegistral() {
		return oficinaRegistral;
	}
	/**
	 * Sets the oficinaRegistral
	 * @param oficinaRegistral The oficinaRegistral to set
	 */
	public void setOficinaRegistral(String oficinaRegistral) {
		this.oficinaRegistral = oficinaRegistral;
	}

	/**
	 * Gets the nroPartida
	 * @return Returns a String
	 */
	public String getNroPartida() {
		return nroPartida;
	}
	/**
	 * Sets the nroPartida
	 * @param nroPartida The nroPartida to set
	 */
	public void setNroPartida(String nroPartida) {
		this.nroPartida = nroPartida;
	}

	/**
	 * Gets the libro
	 * @return Returns a String
	 */
	public String getLibro() {
		return libro;
	}
	/**
	 * Sets the libro
	 * @param libro The libro to set
	 */
	public void setLibro(String libro) {
		this.libro = libro;
	}

	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

}

