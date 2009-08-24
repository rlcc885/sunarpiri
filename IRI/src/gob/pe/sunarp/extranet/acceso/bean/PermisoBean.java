package gob.pe.sunarp.extranet.acceso.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;



public class PermisoBean extends SunarpBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 01;
	private String desc;
	private String url;

	/**
	 * Gets the desc
	 * @return Returns a String
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * Sets the desc
	 * @param desc The desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Gets the url
	 * @return Returns a String
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Sets the url
	 * @param url The url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}

