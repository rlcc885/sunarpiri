package gob.pe.sunarp.extranet.webservices.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;


public class BuscaPartidaDetallePersonaNaturalBean extends SunarpBean{

	private String zonaRegistral = null;
	private String oficinaRegistral = null;
	private String nroPartida = null;
	private String estado = null;
	private String libro = null;
	private String nombreParticipante = null;
	
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


	/**
	 * Gets the nombreParticipante
	 * @return Returns a String
	 */
	public String getNombreParticipante() {
		return nombreParticipante;
	}
	/**
	 * Sets the nombreParticipante
	 * @param nombreParticipante The nombreParticipante to set
	 */
	public void setNombreParticipante(String nombreParticipante) {
		this.nombreParticipante = nombreParticipante;
	}

}

