package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
Bean usado por UsoServicio.java
*/
public class UsoServicioBean extends SunarpBean{
	
	private String regPubId;
	private String oficRegId;
	private String areaRegId;
	/**
	 * Gets the regPubId
	 * @return Returns a String
	 */
	public String getRegPubId() {
		return regPubId;
	}
	/**
	 * Sets the regPubId
	 * @param regPubId The regPubId to set
	 */
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}

	/**
	 * Gets the oficRegId
	 * @return Returns a String
	 */
	public String getOficRegId() {
		return oficRegId;
	}
	/**
	 * Sets the oficRegId
	 * @param oficRegId The oficRegId to set
	 */
	public void setOficRegId(String oficRegId) {
		this.oficRegId = oficRegId;
	}

	/**
	 * Gets the areaRegId
	 * @return Returns a String
	 */
	public String getAreaRegId() {
		return areaRegId;
	}
	/**
	 * Sets the areaRegId
	 * @param areaRegId The areaRegId to set
	 */
	public void setAreaRegId(String areaRegId) {
		this.areaRegId = areaRegId;
	}

}

