package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class EvolucionBean extends SunarpBean{
	
	private java.util.ArrayList list_UsuariosRegistrados;
	private java.util.ArrayList list_TotalesUsuariosRegistrados;	
	private String str_Date_Inicio;
	private String str_Date_Fin;
	private String str_Dia_Inicio;
	private String str_Mes_Inicio;
	private String str_Ano_Inicio;
	private String str_Dia_Fin;
	private String str_Mes_Fin;
	private String str_Ano_Fin;
	private String tipoPersona;
	private String tipoPersonaName;
	private String indicador;
	private String hayRegistros;
	private String mensajeError;

	/**
	 * Gets the list_UsuariosRegistrados
	 * @return Returns a java.util.ArrayList
	 */
	public java.util.ArrayList getList_UsuariosRegistrados() {
		return list_UsuariosRegistrados;
	}
	/**
	 * Sets the list_UsuariosRegistrados
	 * @param list_UsuariosRegistrados The list_UsuariosRegistrados to set
	 */
	public void setList_UsuariosRegistrados(
		java.util.ArrayList list_UsuariosRegistrados) {
		this.list_UsuariosRegistrados = list_UsuariosRegistrados;
	}

	/**
	 * Gets the list_TotalesUsuariosRegistrados
	 * @return Returns a java.util.ArrayList
	 */
	public java.util.ArrayList getList_TotalesUsuariosRegistrados() {
		return list_TotalesUsuariosRegistrados;
	}
	/**
	 * Sets the list_TotalesUsuariosRegistrados
	 * @param list_TotalesUsuariosRegistrados The list_TotalesUsuariosRegistrados to set
	 */
	public void setList_TotalesUsuariosRegistrados(
		java.util.ArrayList list_TotalesUsuariosRegistrados) {
		this.list_TotalesUsuariosRegistrados = list_TotalesUsuariosRegistrados;
	}

	/**
	 * Gets the str_Date_Inicio
	 * @return Returns a String
	 */
	public String getStr_Date_Inicio() {
		return str_Date_Inicio;
	}
	/**
	 * Sets the str_Date_Inicio
	 * @param str_Date_Inicio The str_Date_Inicio to set
	 */
	public void setStr_Date_Inicio(String str_Date_Inicio) {
		this.str_Date_Inicio = str_Date_Inicio;
	}

	/**
	 * Gets the str_Date_Fin
	 * @return Returns a String
	 */
	public String getStr_Date_Fin() {
		return str_Date_Fin;
	}
	/**
	 * Sets the str_Date_Fin
	 * @param str_Date_Fin The str_Date_Fin to set
	 */
	public void setStr_Date_Fin(String str_Date_Fin) {
		this.str_Date_Fin = str_Date_Fin;
	}

	/**
	 * Gets the str_Dia_Inicio
	 * @return Returns a String
	 */
	public String getStr_Dia_Inicio() {
		return str_Dia_Inicio;
	}
	/**
	 * Sets the str_Dia_Inicio
	 * @param str_Dia_Inicio The str_Dia_Inicio to set
	 */
	public void setStr_Dia_Inicio(String str_Dia_Inicio) {
		this.str_Dia_Inicio = str_Dia_Inicio;
	}

	/**
	 * Gets the str_Mes_Inicio
	 * @return Returns a String
	 */
	public String getStr_Mes_Inicio() {
		return str_Mes_Inicio;
	}
	/**
	 * Sets the str_Mes_Inicio
	 * @param str_Mes_Inicio The str_Mes_Inicio to set
	 */
	public void setStr_Mes_Inicio(String str_Mes_Inicio) {
		this.str_Mes_Inicio = str_Mes_Inicio;
	}

	/**
	 * Gets the str_Ano_Inicio
	 * @return Returns a String
	 */
	public String getStr_Ano_Inicio() {
		return str_Ano_Inicio;
	}
	/**
	 * Sets the str_Ano_Inicio
	 * @param str_Ano_Inicio The str_Ano_Inicio to set
	 */
	public void setStr_Ano_Inicio(String str_Ano_Inicio) {
		this.str_Ano_Inicio = str_Ano_Inicio;
	}

	/**
	 * Gets the str_Dia_Fin
	 * @return Returns a String
	 */
	public String getStr_Dia_Fin() {
		return str_Dia_Fin;
	}
	/**
	 * Sets the str_Dia_Fin
	 * @param str_Dia_Fin The str_Dia_Fin to set
	 */
	public void setStr_Dia_Fin(String str_Dia_Fin) {
		this.str_Dia_Fin = str_Dia_Fin;
	}

	/**
	 * Gets the str_Mes_Fin
	 * @return Returns a String
	 */
	public String getStr_Mes_Fin() {
		return str_Mes_Fin;
	}
	/**
	 * Sets the str_Mes_Fin
	 * @param str_Mes_Fin The str_Mes_Fin to set
	 */
	public void setStr_Mes_Fin(String str_Mes_Fin) {
		this.str_Mes_Fin = str_Mes_Fin;
	}

	/**
	 * Gets the str_Ano_Fin
	 * @return Returns a String
	 */
	public String getStr_Ano_Fin() {
		return str_Ano_Fin;
	}
	/**
	 * Sets the str_Ano_Fin
	 * @param str_Ano_Fin The str_Ano_Fin to set
	 */
	public void setStr_Ano_Fin(String str_Ano_Fin) {
		this.str_Ano_Fin = str_Ano_Fin;
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
	 * Gets the tipoPersonaName
	 * @return Returns a String
	 */
	public String getTipoPersonaName() {
		return tipoPersonaName;
	}
	/**
	 * Sets the tipoPersonaName
	 * @param tipoPersonaName The tipoPersonaName to set
	 */
	public void setTipoPersonaName(String tipoPersonaName) {
		this.tipoPersonaName = tipoPersonaName;
	}

	/**
	 * Gets the indicador
	 * @return Returns a String
	 */
	public String getIndicador() {
		return indicador;
	}
	/**
	 * Sets the indicador
	 * @param indicador The indicador to set
	 */
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	/**
	 * Gets the hayRegistros
	 * @return Returns a String
	 */
	public String getHayRegistros() {
		return hayRegistros;
	}
	/**
	 * Sets the hayRegistros
	 * @param hayRegistros The hayRegistros to set
	 */
	public void setHayRegistros(String hayRegistros) {
		this.hayRegistros = hayRegistros;
	}

	/**
	 * Gets the mensajeError
	 * @return Returns a String
	 */
	public String getMensajeError() {
		return mensajeError;
	}
	/**
	 * Sets the mensajeError
	 * @param mensajeError The mensajeError to set
	 */
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

}

