package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class AsientoFojaBean extends SunarpBean{
	
	private String numRef;
	private String numPagRef;
	private String tpoAsiento = "3";
	private String nroTomo;
	private String nroFolio;
	private String objectId;
	private String nroPaginas;
	private String fechaUltimaReplica;
	private String horaUltimaReplica;
	/**
	 * Gets the numRef
	 * @return Returns a String
	 */
	public String getNumRef() {
		return numRef;
	}
	/**
	 * Sets the numRef
	 * @param numRef The numRef to set
	 */
	public void setNumRef(String numRef) {
		this.numRef = numRef;
	}

	/**
	 * Gets the tpoAsiento
	 * @return Returns a String
	 */
	public String getTpoAsiento() {
		return tpoAsiento;
	}
	/**
	 * Sets the tpoAsiento
	 * @param tpoAsiento The tpoAsiento to set
	 */
	public void setTpoAsiento(String tpoAsiento) {
		this.tpoAsiento = tpoAsiento;
	}

	/**
	 * Gets the nroTomo
	 * @return Returns a String
	 */
	public String getNroTomo() {
		return nroTomo;
	}
	/**
	 * Sets the nroTomo
	 * @param nroTomo The nroTomo to set
	 */
	public void setNroTomo(String nroTomo) {
		this.nroTomo = nroTomo;
	}

	/**
	 * Gets the nroFolio
	 * @return Returns a String
	 */
	public String getNroFolio() {
		return nroFolio;
	}
	/**
	 * Sets the nroFolio
	 * @param nroFolio The nroFolio to set
	 */
	public void setNroFolio(String nroFolio) {
		this.nroFolio = nroFolio;
	}

	/**
	 * Gets the objectId
	 * @return Returns a String
	 */
	public String getObjectId() {
		return objectId;
	}
	/**
	 * Sets the objectId
	 * @param objectId The objectId to set
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * Gets the nroPaginas
	 * @return Returns a String
	 */
	public String getNroPaginas() {
		return nroPaginas;
	}
	/**
	 * Sets the nroPaginas
	 * @param nroPaginas The nroPaginas to set
	 */
	public void setNroPaginas(String nroPaginas) {
		this.nroPaginas = nroPaginas;
	}

	/**
	 * Gets the fechaUltimaReplica
	 * @return Returns a String
	 */
	public String getFechaUltimaReplica() {
		return fechaUltimaReplica;
	}
	/**
	 * Sets the fechaUltimaReplica
	 * @param fechaUltimaReplica The fechaUltimaReplica to set
	 */
	public void setFechaUltimaReplica(String fechaUltimaReplica) {
		this.fechaUltimaReplica = fechaUltimaReplica;
	}

	/**
	 * Gets the horaUltimaReplica
	 * @return Returns a String
	 */
	public String getHoraUltimaReplica() {
		return horaUltimaReplica;
	}
	/**
	 * Sets the horaUltimaReplica
	 * @param horaUltimaReplica The horaUltimaReplica to set
	 */
	public void setHoraUltimaReplica(String horaUltimaReplica) {
		this.horaUltimaReplica = horaUltimaReplica;
	}

	/**
	 * Gets the numPagRef
	 * @return Returns a String
	 */
	public String getNumPagRef() {
		return numPagRef;
	}
	/**
	 * Sets the numPagRef
	 * @param numPagRef The numPagRef to set
	 */
	public void setNumPagRef(String numPagRef) {
		this.numPagRef = numPagRef;
	}

}

