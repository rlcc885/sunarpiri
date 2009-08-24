package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.util.List;


/*
 *Bean VehiGrava
 *@version 1
 *Kuma
 */

public class VehiGrava extends SunarpBean{
	
	
	private String estado = " "; /** ALT + 255 **/
	private String afecta = " ";
	private String fecAfe = " ";
	private String noDocu = " ";
	private String titulo = " ";
	private String juzgad = " ";
	private String cauAfe = " ";
	private String juez = " ";
	private String secre = " ";
	private String modifi = " ";
	private String juezDs = " ";
	private String secrDs = " ";
	private String fecDes = " ";
	private String noXpDs = " ";
	
	private List listbeanPartGrava = new java.util.ArrayList();
	

	
			
	//***********************************SETTERS Y GETTERS************************
	
	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * Gets the afectacion
	 * @return Returns a String
	 */
	public String getAfecta() {
		return afecta;
	}
	/**
	 * Sets the afectacion
	 * @param afecta The afecta to set
	 */
	public void setAfecta(String afecta) {
		this.afecta = afecta;
	}


	/**
	 * Gets the fechaDeAfectacion
	 * @return Returns a String
	 */
	public String getFecAfe() {
		return fecAfe;
	}
	/**
	 * Sets the fechaDeAfectacion
	 * @param fecAfe The fecAfe to set
	 */
	public void setFecAfe(String fecAfe) {
		this.fecAfe = fecAfe;
	}
	
	/**
	 * Gets the numeroDeDocumento
	 * @return Returns a String
	 */
	public String getNoDocu() {
		return noDocu;
	}
	/**
	 * Sets the numeroDeDocumento
	 * @param noDocu The noDocu to set
	 */
	public void setNoDocu(String noDocu) {
		this.noDocu = noDocu;
	}
	
	/**
	 * Gets the titulo
	 * @return Returns a String
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Sets the titulo
	 * @param titulo The titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * Gets the juzgado
	 * @return Returns a String
	 */
	public String getJuzgad() {
		return juzgad;
	}
	/**
	 * Sets the juzgado
	 * @param juzgad The juzgad to set
	 */
	public void setJuzgad(String juzgad) {
		this.juzgad = juzgad;
	}
	
	/**
	 * Gets the causaDeAfecatacion
	 * @return Returns a String
	 */
	public String getCauAfe() {
		return cauAfe;
	}
	/**
	 * Sets the causaDeAfecatacion
	 * @param cauAfe The cauAfe to set
	 */
	public void setCauAfe(String cauAfe) {
		this.cauAfe = cauAfe;
	}
	
	/**
	 * Gets the juez
	 * @return Returns a String
	 */
	public String getJuez() {
		return juez;
	}
	/**
	 * Sets the juez
	 * @param juez The juez to set
	 */
	public void setJuez(String juez) {
		this.juez = juez;
	}
	
	/**
	 * Gets the secretario
	 * @return Returns a String
	 */
	public String getSecre() {
		return secre;
	}
	/**
	 * Sets the secretario
	 * @param secre The secre to set
	 */
	public void setSecre(String secre) {
		this.secre = secre;
	}
	
	/**
	 * Gets the modificacion
	 * @return Returns a String
	 */
	public String getModifi() {
		return modifi;
	}
	/**
	 * Sets the modificacion
	 * @param modifi The modifi to set
	 */
	public void setModifi(String modifi) {
		this.modifi = modifi;
	}
	
	/**
	 * Gets the juezDescargo
	 * @return Returns a String
	 */
	public String getJuezDs() {
		return juezDs;
	}
	/**
	 * Sets the juezDescargo
	 * @param juezDs The juezDs to set
	 */
	public void setJuezDs(String juezDs) {
		this.juezDs = juezDs;
	}
	
	/**
	 * Gets the secretarioDescargo
	 * @return Returns a String
	 */
	public String getSecrDs() {
		return secrDs;
	}
	/**
	 * Sets the secretarioDescargo
	 * @param secrDs The secrDs to set
	 */
	public void setSecrDs(String secrDs) {
		this.secrDs = secrDs;
	}
	public String getFecDes() {
		return fecDes;
	}
	public void setFecDes(String fecDes) {
		this.fecDes = fecDes;
	}
	/**
	 * Gets the numeroDeExpedienteDescargo
	 * @return Returns a String
	 */
	public String getNoXpDs() {
		return noXpDs;
	}
	/**
	 * Sets the numeroDeExpedienteDescargo
	 * @param noXpDs The noXpDs to set
	 */
	public void setNoXpDs(String noXpDs) {
		this.noXpDs = noXpDs;
	}
	

	/**
	 * Gets the listbeanPartGrava
	 * @return Returns a List
	 */
	public List getListbeanPartGrava() {
		return listbeanPartGrava;
	}
	/**
	 * Sets the listbeanPartGrava
	 * @param listbeanPartGrava The listbeanPartGrava to set
	 */
	public void setListbeanPartGrava(List listbeanPartGrava) {
		this.listbeanPartGrava = listbeanPartGrava;
	}

}