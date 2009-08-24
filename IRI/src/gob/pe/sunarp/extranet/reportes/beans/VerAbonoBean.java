package gob.pe.sunarp.extranet.reportes.beans;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class VerAbonoBean extends SunarpBean{
	

	private String userId;
	private String entidad;
	private long monto;
	private String pagoLineaId;
	/**
	 * Gets the userId
	 * @return Returns a String
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Sets the userId
	 * @param userId The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the entidad
	 * @return Returns a String
	 */
	public String getEntidad() {
		return entidad;
	}
	/**
	 * Sets the entidad
	 * @param entidad The entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * Gets the monto
	 * @return Returns a long
	 */
	public long getMonto() {
		return monto;
	}
	/**
	 * Sets the monto
	 * @param monto The monto to set
	 */
	public void setMonto(long monto) {
		this.monto = monto;
	}

	/**
	 * Gets the pagoLineaId
	 * @return Returns a String
	 */
	public String getPagoLineaId() {
		return pagoLineaId;
	}
	/**
	 * Sets the pagoLineaId
	 * @param pagoLineaId The pagoLineaId to set
	 */
	public void setPagoLineaId(String pagoLineaId) {
		this.pagoLineaId = pagoLineaId;
	}

}

