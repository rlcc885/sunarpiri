package gob.pe.sunarp.extranet.reportegeneral.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class OficinaConection extends SunarpBean{
	
	private String url;
	private String user;
	private String password;
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

	/**
	 * Gets the user
	 * @return Returns a String
	 */
	public String getUser() {
		return user;
	}
	/**
	 * Sets the user
	 * @param user The user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the password
	 * @return Returns a String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}

