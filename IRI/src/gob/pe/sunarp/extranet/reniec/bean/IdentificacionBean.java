package gob.pe.sunarp.extranet.reniec.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class IdentificacionBean extends SunarpBean{
	
	
	private String codOper;
	private String codUser;
	private String codTransac;
	private String codEntidad;
	private String codTipoDoc;
	private String sesion;
	private String codExito;
	private String user;
	private String password;
	
	private WebServiceBean wsDataVerification;
	private WebServiceBean wsAuthentication;
				
	public String getCodEntidad() {
		return codEntidad;
	}
	public void setCodEntidad(String codEntidad) {
		this.codEntidad = codEntidad;
	}
	public String getCodOper() {
		return codOper;
	}
	public void setCodOper(String codOper) {
		this.codOper = codOper;
	}
	public String getCodTransac() {
		return codTransac;
	}
	public void setCodTransac(String codTransac) {
		this.codTransac = codTransac;
	}
	public String getCodUser() {
		return codUser;
	}
	public void setCodUser(String codUser) {
		this.codUser = codUser;
	}
	public String getSesion() {
		return sesion;
	}
	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
	public WebServiceBean getWsAuthentication() {
		return wsAuthentication;
	}
	public void setWsAuthentication(WebServiceBean authentication) {
		wsAuthentication = authentication;
	}
	public WebServiceBean getWsDataVerification() {
		return wsDataVerification;
	}
	public void setWsDataVerification(WebServiceBean wsDataVerification) {
		this.wsDataVerification = wsDataVerification;
	}
	public String getCodTipoDoc() {
		return codTipoDoc;
	}
	public void setCodTipoDoc(String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}
	public String getCodExito() {
		return codExito;
	}
	public void setCodExito(String codExito) {
		this.codExito = codExito;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	

}
