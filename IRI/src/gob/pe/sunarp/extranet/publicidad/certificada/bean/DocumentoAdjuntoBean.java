package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class DocumentoAdjuntoBean extends SunarpBean{
	
	
	private String documento_id;
	private String atencion_id;
	private String nombre;
	private String tamano;
	private String ts_crea;

	/**
	 * Gets the documento_id
	 * @return Returns a String
	 */
	public String getDocumento_id() {
		return documento_id;
	}
	/**
	 * Sets the documento_id
	 * @param documento_id The documento_id to set
	 */
	public void setDocumento_id(String documento_id) {
		this.documento_id = documento_id;
	}

	/**
	 * Gets the atencion_id
	 * @return Returns a String
	 */
	public String getAtencion_id() {
		return atencion_id;
	}
	/**
	 * Sets the atencion_id
	 * @param atencion_id The atencion_id to set
	 */
	public void setAtencion_id(String atencion_id) {
		this.atencion_id = atencion_id;
	}

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
	 * Gets the tamano
	 * @return Returns a String
	 */
	public String getTamano() {
		return tamano;
	}
	/**
	 * Sets the tamano
	 * @param tamano The tamano to set
	 */
	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	/**
	 * Gets the ts_crea
	 * @return Returns a String
	 */
	public String getTs_crea() {
		return ts_crea;
	}
	/**
	 * Sets the ts_crea
	 * @param ts_crea The ts_crea to set
	 */
	public void setTs_crea(String ts_crea) {
		this.ts_crea = ts_crea;
	}

}

