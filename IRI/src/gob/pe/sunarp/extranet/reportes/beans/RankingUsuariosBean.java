package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class RankingUsuariosBean extends SunarpBean{
	
	
	private String posicion;
	private String nombreRazon;
	private String numTitulos;
	private String numPartidas;
	private String totalConsultas;
	private String colorCelda;
	private String tipoPersona;
	//_
	private String ingreso;

	/**
	 * Gets the posicion
	 * @return Returns a String
	 */
	public String getPosicion() {
		return posicion;
	}
	/**
	 * Sets the posicion
	 * @param posicion The posicion to set
	 */
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	/**
	 * Gets the nombreRazon
	 * @return Returns a String
	 */
	public String getNombreRazon() {
		return nombreRazon;
	}
	/**
	 * Sets the nombreRazon
	 * @param nombreRazon The nombreRazon to set
	 */
	public void setNombreRazon(String nombreRazon) {
		this.nombreRazon = nombreRazon;
	}

	/**
	 * Gets the numTitulos
	 * @return Returns a String
	 */
	public String getNumTitulos() {
		return numTitulos;
	}
	/**
	 * Sets the numTitulos
	 * @param numTitulos The numTitulos to set
	 */
	public void setNumTitulos(String numTitulos) {
		this.numTitulos = numTitulos;
	}

	/**
	 * Gets the numPartidas
	 * @return Returns a String
	 */
	public String getNumPartidas() {
		return numPartidas;
	}
	/**
	 * Sets the numPartidas
	 * @param numPartidas The numPartidas to set
	 */
	public void setNumPartidas(String numPartidas) {
		this.numPartidas = numPartidas;
	}

	/**
	 * Gets the totalConsultas
	 * @return Returns a String
	 */
	public String getTotalConsultas() {
		return totalConsultas;
	}
	/**
	 * Sets the totalConsultas
	 * @param totalConsultas The totalConsultas to set
	 */
	public void setTotalConsultas(String totalConsultas) {
		this.totalConsultas = totalConsultas;
	}

	/**
	 * Gets the colorCelda
	 * @return Returns a String
	 */
	public String getColorCelda() {
		return colorCelda;
	}
	/**
	 * Sets the colorCelda
	 * @param colorCelda The colorCelda to set
	 */
	public void setColorCelda(String colorCelda) {
		this.colorCelda = colorCelda;
	}

	/**
	 * Gets the tipoPersona
	 * @return Returns a String
	 */
	public String getTipoPersona() {
		return tipoPersona;
	}
	/**
	 * Sets the tipoPersona
	 * @param tipoPersona The tipoPersona to set
	 */
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	/**
	 * Gets the ingreso
	 * @return Returns a String
	 */
	public String getIngreso() {
		return ingreso;
	}
	/**
	 * Sets the ingreso
	 * @param ingreso The ingreso to set
	 */
	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

}

