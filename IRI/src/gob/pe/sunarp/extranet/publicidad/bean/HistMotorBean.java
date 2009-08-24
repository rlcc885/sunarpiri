package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
 *Bean HistMotor
 *@version 1
 */

public class HistMotorBean extends SunarpBean{
	
	
	private String placa = " "; /** ALT + 255 **/
	private String secuencial = " ";
	private String serie = " ";
	private String motor = " ";
	
			
	//***********************************SETTERS Y GETTERS************************
	
	
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
	 * Gets the secuencial
	 * @return Returns a String
	 */
	public String getSecuencial() {
		return secuencial;
	}
	/**
	 * Sets the secuencial
	 * @param secuencial The secuencial to set
	 */
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	
	/**
	 * Gets the serie
	 * @return Returns a String
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * Sets the serie
	 * @param serie The serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}
	/**
	 * Gets the motor
	 * @return Returns a String
	 */
	public String getMotor() {
		return motor;
	}
	/**
	 * Sets the motor
	 * @param motor The motor to set
	 */
	public void setMotor(String motor) {
		this.motor = motor;
	}

}