package gob.pe.sunarp.extranet.webservices.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.io.Serializable;

public class BuscaPartidaDetallePropiedadVehicularBean extends SunarpBean{

	private String zonaRegistral = null;
	private String oficinaRegistral = null;
	private String nroPartida = null;
	private String estado = null;
	private String placa = null;
	private String estadoVehiculo = null;
	
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
	 * Gets the estadoVehiculo
	 * @return Returns a String
	 */
	public String getEstadoVehiculo() {
		return estadoVehiculo;
	}
	/**
	 * Sets the estadoVehiculo
	 * @param estadoVehiculo The estadoVehiculo to set
	 */
	public void setEstadoVehiculo(String estadoVehiculo) {
		this.estadoVehiculo = estadoVehiculo;
	}

	/**
	 * Gets the placa
	 * @return Returns a String
	 */
	public String getPlaca() {
		return placa;
	}
	/**
	 * Sets the placa
	 * @param placa The placa to set
	 */
	public void setPlaca(String placa) {
		this.placa = placa;
	}

}

