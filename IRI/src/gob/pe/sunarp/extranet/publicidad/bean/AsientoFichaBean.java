package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class AsientoFichaBean extends SunarpBean{
	
	private String numPagRef;
	private String tpoAsiento = "2";
	private String nroFicha;
	private String objectId;
	private int nroPaginas;
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
	 * Gets the nroFicha
	 * @return Returns a String
	 */
	public String getNroFicha() {
		return nroFicha;
	}
	/**
	 * Sets the nroFicha
	 * @param nroFicha The nroFicha to set
	 */
	public void setNroFicha(String nroFicha) {
		this.nroFicha = nroFicha;
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
	 * @return Returns a int
	 */
	public int getNroPaginas() {
		return nroPaginas;
	}
	/**
	 * Sets the nroPaginas
	 * @param nroPaginas The nroPaginas to set
	 */
	public void setNroPaginas(int nroPaginas) {
		this.nroPaginas = nroPaginas;
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

