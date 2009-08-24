package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
 *Bean HistPropietario
 *@version 1
 */

public class HistPropietarioBean extends SunarpBean{
	
	
	private String nombre = " "; /** ALT + 255 **/
	private String documentos = " ";
	private String direcciones = " ";
	private String fechaInsc = " ";
	
			
	//***********************************SETTERS Y GETTERS************************
	
	
	
	/**
	 * Gets the nombre
	 * @return Returns a String
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Sets the nombre
	 * @param nombre The nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the documentos
	 * @return Returns a String
	 */
	public String getDocumentos() {
		return documentos;
	}
	/**
	 * Sets the documentos
	 * @param documentos The documentos to set
	 */
	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	/**
	 * Gets the direcciones
	 * @return Returns a String
	 */
	public String getDirecciones() {
		return direcciones;
	}
	/**
	 * Sets the direcciones
	 * @param direcciones The direcciones to set
	 */
	public void setDirecciones(String direcciones) {
		this.direcciones = direcciones;
	}

	/**
	 * Gets the fechaInsc
	 * @return Returns a String
	 */
	public String getFechaInsc() {
		return fechaInsc;
	}
	/**
	 * Sets the fechaInsc
	 * @param fechaInsc The fechaInsc to set
	 */
	public void setFechaInsc(String fechaInsc) {
		this.fechaInsc = fechaInsc;
	}

}