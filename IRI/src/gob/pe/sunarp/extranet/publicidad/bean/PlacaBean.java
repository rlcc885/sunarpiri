package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
 *Bean Placa
 *@version 1
 */

public class PlacaBean extends SunarpBean{
	
	
	private String partida = " "; /** ALT + 255 **/
	private String placa = " ";
	private String propietario = " ";
	
			
	//***********************************SETTERS Y GETTERS************************
	
	/**
	 * Gets the partida
	 * @return Returns a String
	 */
	public String getPartida() {
		return partida;
	}
	/**
	 * Sets the partida
	 * @param partida The partida to set
	 */
	public void setPartida(String partida) {
		this.partida = partida;
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


	/**
	 * Gets the propietario
	 * @return Returns a String
	 */
	public String getPropietario() {
		return propietario;
	}
	/**
	 * Sets the propietario
	 * @param propietario The propietario to set
	 */
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
}