package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.util.Constantes;
import java.util.Date;

public class ImagenBean extends SunarpBean{
	
	private String numPartida = "-";
	private String numPagina = "-";
	private String totPaginas = "-";
	private String nomOficina = "";
	//copia certificada
	private String tipoImpresion = Constantes.IMPRESION_COPIA_SIMPLE;
	private String titulosPendientes;
	private String horaTitulosPendientes;
	private Date fechaVerificacion;

	/**
	 * Gets the numPartida
	 * @return Returns a String
	 */
	public String getNumPartida() {
		return numPartida;
	}
	/**
	 * Sets the numPartida
	 * @param numPartida The numPartida to set
	 */
	public void setNumPartida(String numPartida) {
		this.numPartida = numPartida;
	}

	/**
	 * Gets the numPagina
	 * @return Returns a String
	 */
	public String getNumPagina() {
		return numPagina;
	}
	/**
	 * Sets the numPagina
	 * @param numPagina The numPagina to set
	 */
	public void setNumPagina(String numPagina) {
		this.numPagina = numPagina;
	}

	/**
	 * Gets the totPaginas
	 * @return Returns a String
	 */
	public String getTotPaginas() {
		return totPaginas;
	}
	/**
	 * Sets the totPaginas
	 * @param totPaginas The totPaginas to set
	 */
	public void setTotPaginas(String totPaginas) {
		this.totPaginas = totPaginas;
	}

	/**
	 * Gets the nomOficina
	 * @return Returns a String
	 */
	public String getNomOficina() {
		return nomOficina;
	}
	/**
	 * Sets the nomOficina
	 * @param nomOficina The nomOficina to set
	 */
	public void setNomOficina(String nomOficina) {
		this.nomOficina = nomOficina;
	}

	/**
	 * Gets the titulosPendientes
	 * @return Returns a String
	 */
	public String getTitulosPendientes() {
		return titulosPendientes;
	}
	/**
	 * Sets the titulosPendientes
	 * @param titulosPendientes The titulosPendientes to set
	 */
	public void setTitulosPendientes(String titulosPendientes) {
		this.titulosPendientes = titulosPendientes;
	}

	/**
	 * Gets the tipoImpresion
	 * @return Returns a String
	 */
	public String getTipoImpresion() {
		return tipoImpresion;
	}
	/**
	 * Sets the tipoImpresion
	 * @param tipoImpresion The tipoImpresion to set
	 */
	public void setTipoImpresion(String tipoImpresion) {
		this.tipoImpresion = tipoImpresion;
	}

	/**
	 * Gets the horaTitulosPendientes
	 * @return Returns a String
	 */
	public String getHoraTitulosPendientes() {
		return horaTitulosPendientes;
	}
	/**
	 * Sets the horaTitulosPendientes
	 * @param horaTitulosPendientes The horaTitulosPendientes to set
	 */
	public void setHoraTitulosPendientes(String horaTitulosPendientes) {
		this.horaTitulosPendientes = horaTitulosPendientes;
	}

	/**
	 * Gets the fechaVerificacion
	 * @return Returns a Date
	 */
	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}
	/**
	 * Sets the fechaVerificacion
	 * @param fechaVerificacion The fechaVerificacion to set
	 */
	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}

}

