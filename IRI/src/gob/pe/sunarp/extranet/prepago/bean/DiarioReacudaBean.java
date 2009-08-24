package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class DiarioReacudaBean extends SunarpBean{


	private String dia;
	private String mes;
	private String ano;
	
	private String totalAbonos;
	private String totalExtornos;
	private String totalRecaudado;
	/**
	 * Gets the dia
	 * @return Returns a String
	 */
	public String getDia() {
		return dia;
	}
	/**
	 * Sets the dia
	 * @param dia The dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}

	/**
	 * Gets the mes
	 * @return Returns a String
	 */
	public String getMes() {
		return mes;
	}
	/**
	 * Sets the mes
	 * @param mes The mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * Gets the ano
	 * @return Returns a String
	 */
	public String getAno() {
		return ano;
	}
	/**
	 * Sets the ano
	 * @param ano The ano to set
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * Gets the totalAbonos
	 * @return Returns a String
	 */
	public String getTotalAbonos() {
		return totalAbonos;
	}
	/**
	 * Sets the totalAbonos
	 * @param totalAbonos The totalAbonos to set
	 */
	public void setTotalAbonos(String totalAbonos) {
		this.totalAbonos = totalAbonos;
	}

	/**
	 * Gets the totalExtornos
	 * @return Returns a String
	 */
	public String getTotalExtornos() {
		return totalExtornos;
	}
	/**
	 * Sets the totalExtornos
	 * @param totalExtornos The totalExtornos to set
	 */
	public void setTotalExtornos(String totalExtornos) {
		this.totalExtornos = totalExtornos;
	}

	/**
	 * Gets the totalRecaudado
	 * @return Returns a String
	 */
	public String getTotalRecaudado() {
		return totalRecaudado;
	}
	/**
	 * Sets the totalRecaudado
	 * @param totalRecaudado The totalRecaudado to set
	 */
	public void setTotalRecaudado(String totalRecaudado) {
		this.totalRecaudado = totalRecaudado;
	}

}

