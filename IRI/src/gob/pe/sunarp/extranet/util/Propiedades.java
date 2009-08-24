package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
Clase singleton para guardar las propiedades
generales del sistema

Revisar además: archivo webappResources.xml
*/

public class Propiedades extends SunarpBean{
	
	//instancia única
	private static Propiedades propiedades;
		
	//constructor privado
	private Propiedades() {
		super();
	}
	
	//getInstance
	public static Propiedades getInstance() {
		if (propiedades == null)
			propiedades = new Propiedades();
		return propiedades;
	}	

//flags
private boolean flagGrabaClave=false;
private boolean flagProduccion=false;
private boolean flagTransaccion=true;
//valores
private int     lineasPorPag=10;
private int     sendMailTiempo=5;				// para la WEB
//-envio de mail
private String sendMailSMTPServer=null;
private String sendMailUser=null;
private String sendMailPassword=null;
//RPV
private String sendMailPersonasRPV = null;

private int maxResultadosBusqueda=70;

//valores para contactenos
private String contactenosEmailsConsultas=null;
private String contactenosEmailsQuejas=null;
private String contactenosEmailsObservs=null;

//valores para PreparaImagen
private double imageScaleAsiento = 0.70;
private double imageScaleFicha   = 0.60;
private double imageScaleFolio   = 0.50;


private String rutaWebApplication="";

//hphp:30/10
private String rutaModeloSobre="";

private int     sendMailTiempoNoWeb=15;
private int     mailActivoDias=5;				// para la WEB
private int     mailActivoDiasNoWeb=5;
//*************SETTERS Y GETTERS******************

	/**
	 * Gets the flagGrabaClave
	 * @return Returns a boolean
	 */
	public boolean getFlagGrabaClave() {
		return flagGrabaClave;
	}
	/**
	 * Sets the flagGrabaClave
	 * @param flagGrabaClave The flagGrabaClave to set
	 */
	public void setFlagGrabaClave(boolean flagGrabaClave) {
		this.flagGrabaClave = flagGrabaClave;
	}

	/**
	 * Gets the lineasPorPag
	 * @return Returns a int
	 */
	public int getLineasPorPag() {
		return lineasPorPag;
	}
	/**
	 * Sets the lineasPorPag
	 * @param lineasPorPag The lineasPorPag to set
	 */
	public void setLineasPorPag(int lineasPorPag) {
		this.lineasPorPag = lineasPorPag;
	}

	/**
	 * Gets the sendMailSMTPServer
	 * @return Returns a String
	 */
	public String getSendMailSMTPServer() {
		return sendMailSMTPServer;
	}
	/**
	 * Sets the sendMailSMTPServer
	 * @param sendMailSMTPServer The sendMailSMTPServer to set
	 */
	public void setSendMailSMTPServer(String sendMailSMTPServer) {
		this.sendMailSMTPServer = sendMailSMTPServer;
	}

	/**
	 * Gets the sendMailUser
	 * @return Returns a String
	 */
	public String getSendMailUser() {
		return sendMailUser;
	}
	/**
	 * Sets the sendMailUser
	 * @param sendMailUser The sendMailUser to set
	 */
	public void setSendMailUser(String sendMailUser) {
		this.sendMailUser = sendMailUser;
	}

	/**
	 * Gets the sendMailPassword
	 * @return Returns a String
	 */
	public String getSendMailPassword() {
		return sendMailPassword;
	}
	/**
	 * Sets the sendMailPassword
	 * @param sendMailPassword The sendMailPassword to set
	 */
	public void setSendMailPassword(String sendMailPassword) {
		this.sendMailPassword = sendMailPassword;
	}

	/**
	 * Gets the sendMailTiempo
	 * @return Returns a String
	 */

	/**
	 * Gets the sendMailTiempo
	 * @return Returns a int
	 */
	public int getSendMailTiempo() {
		return sendMailTiempo;
	}
	/**
	 * Sets the sendMailTiempo
	 * @param sendMailTiempo The sendMailTiempo to set
	 */
	public void setSendMailTiempo(int sendMailTiempo) {
		this.sendMailTiempo = sendMailTiempo;
	}

	/**
	 * Gets the maxResultadosBusqueda
	 * @return Returns a int
	 */
	public int getMaxResultadosBusqueda() {
		return maxResultadosBusqueda;
	}
	/**
	 * Sets the maxResultadosBusqueda
	 * @param maxResultadosBusqueda The maxResultadosBusqueda to set
	 */
	public void setMaxResultadosBusqueda(int maxResultadosBusqueda) {
		this.maxResultadosBusqueda = maxResultadosBusqueda;
	}

	/**
	 * Gets the contactenosEmailsConsultas
	 * @return Returns a String[]
	 */
	public String getContactenosEmailsConsultas() {
		return contactenosEmailsConsultas;
	}
	/**
	 * Sets the flagProduccion
	 * @param flagProduccion The flagProduccion to set
	 */
	public void setFlagProduccion(boolean flagProduccion) {
		this.flagProduccion = flagProduccion;
	}

	/**
	 * Gets the flagProduccion
	 * @return Returns a boolean
	 */
	public boolean getFlagProduccion() {
		return flagProduccion;
	}
	/**
	 * Gets the imageScaleAsiento
	 * @return Returns a double
	 */
	public double getImageScaleAsiento() {
		return imageScaleAsiento;
	}
	/**
	 * Sets the imageScaleAsiento
	 * @param imageScaleAsiento The imageScaleAsiento to set
	 */
	public void setImageScaleAsiento(double imageScaleAsiento) {
		this.imageScaleAsiento = imageScaleAsiento;
	}

	/**
	 * Gets the imageScaleFicha
	 * @return Returns a double
	 */
	public double getImageScaleFicha() {
		return imageScaleFicha;
	}
	/**
	 * Sets the imageScaleFicha
	 * @param imageScaleFicha The imageScaleFicha to set
	 */
	public void setImageScaleFicha(double imageScaleFicha) {
		this.imageScaleFicha = imageScaleFicha;
	}

	/**
	 * Gets the imageScaleFolio
	 * @return Returns a double
	 */
	public double getImageScaleFolio() {
		return imageScaleFolio;
	}
	/**
	 * Sets the imageScaleFolio
	 * @param imageScaleFolio The imageScaleFolio to set
	 */
	public void setImageScaleFolio(double imageScaleFolio) {
		this.imageScaleFolio = imageScaleFolio;
	}

	/**
	 * Sets the contactenosEmailsConsultas
	 * @param contactenosEmailsConsultas The contactenosEmailsConsultas to set
	 */
	public void setContactenosEmailsConsultas(String contactenosEmailsConsultas) {
		this.contactenosEmailsConsultas = contactenosEmailsConsultas;
	}

	/**
	 * Gets the contactenosEmailsQuejas
	 * @return Returns a String
	 */
	public String getContactenosEmailsQuejas() {
		return contactenosEmailsQuejas;
	}
	/**
	 * Sets the contactenosEmailsQuejas
	 * @param contactenosEmailsQuejas The contactenosEmailsQuejas to set
	 */
	public void setContactenosEmailsQuejas(String contactenosEmailsQuejas) {
		this.contactenosEmailsQuejas = contactenosEmailsQuejas;
	}

	/**
	 * Gets the contactenosEmailsObservs
	 * @return Returns a String
	 */
	public String getContactenosEmailsObservs() {
		return contactenosEmailsObservs;
	}
	/**
	 * Sets the contactenosEmailsObservs
	 * @param contactenosEmailsObservs The contactenosEmailsObservs to set
	 */
	public void setContactenosEmailsObservs(String contactenosEmailsObservs) {
		this.contactenosEmailsObservs = contactenosEmailsObservs;
	}

	/**
	 * Gets the rutaWebApplication
	 * @return Returns a String
	 */
	public String getRutaWebApplication() {
		return rutaWebApplication;
	}
	/**
	 * Sets the rutaWebApplication
	 * @param rutaWebApplication The rutaWebApplication to set
	 */
	public void setRutaWebApplication(String rutaWebApplication) {
		this.rutaWebApplication = rutaWebApplication;
	}

	/**
	 * Gets the flagTransaccion
	 * @return Returns a boolean
	 */
	public boolean getFlagTransaccion() {
		return flagTransaccion;
	}
	/**
	 * Sets the flagTransaccion
	 * @param flagTransaccion The flagTransaccion to set
	 */
	public void setFlagTransaccion(boolean flagTransaccion) {
		this.flagTransaccion = flagTransaccion;
	}

	/**
	 * Gets the sendMailPersonasRPV
	 * @return Returns a String
	 */
	public String getSendMailPersonasRPV() {
		return sendMailPersonasRPV;
	}
	/**
	 * Sets the sendMailPersonasRPV
	 * @param sendMailPersonasRPV The sendMailPersonasRPV to set
	 */
	public void setSendMailPersonasRPV(String sendMailPersonasRPV) {
		this.sendMailPersonasRPV = sendMailPersonasRPV;
	}

	/**
	 * Gets the mailActivoDias
	 * @return Returns a int
	 */
	public int getMailActivoDias() {
		return mailActivoDias;
	}
	/**
	 * Sets the mailActivoDias
	 * @param mailActivoDias The mailActivoDias to set
	 */
	public void setMailActivoDias(int mailActivoDias) {
		this.mailActivoDias = mailActivoDias;
	}

	/**
	 * Gets the mailActivoDiasNoWeb
	 * @return Returns a int
	 */
	public int getMailActivoDiasNoWeb() {
		return mailActivoDiasNoWeb;
	}
	/**
	 * Sets the mailActivoDiasNoWeb
	 * @param mailActivoDiasNoWeb The mailActivoDiasNoWeb to set
	 */
	public void setMailActivoDiasNoWeb(int mailActivoDiasNoWeb) {
		this.mailActivoDiasNoWeb = mailActivoDiasNoWeb;
	}

	/**
	 * Gets the sendMailTiempoNoWeb
	 * @return Returns a int
	 */
	public int getSendMailTiempoNoWeb() {
		return sendMailTiempoNoWeb;
	}
	/**
	 * Sets the sendMailTiempoNoWeb
	 * @param sendMailTiempoNoWeb The sendMailTiempoNoWeb to set
	 */
	public void setSendMailTiempoNoWeb(int sendMailTiempoNoWeb) {
		this.sendMailTiempoNoWeb = sendMailTiempoNoWeb;
	}

	/**
	 * Gets the rutaModeloSobre
	 * @return Returns a String
	 */
	public String getRutaModeloSobre() {
		return rutaModeloSobre;
	}
	/**
	 * Sets the rutaModeloSobre
	 * @param rutaModeloSobre The rutaModeloSobre to set
	 */
	public void setRutaModeloSobre(String rutaModeloSobre) {
		this.rutaModeloSobre = rutaModeloSobre;
	}

}

