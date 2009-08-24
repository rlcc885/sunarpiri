/**
 * 
 */
package gob.pe.sunarp.extranet.solicitud.denominacion.beans;

import java.io.Serializable;

/**
 * @author jbugarin
 *
 */
public class ReportesBean implements Serializable {
	
	private String aniooHoja;
	private String numHoja;
	private String oficina;
	private String anioTitu;
	private String numTitu;
	private String servicio;
	private String usuario;
	private String presentante;
	private String mail;
	private String fecha;
	private String estado;
	private String plazo;
	private String nsDetalle;
	public String getAniooHoja() {
		return aniooHoja;
	}
	public void setAniooHoja(String aniooHoja) {
		this.aniooHoja = aniooHoja;
	}
	public String getAnioTitu() {
		return anioTitu;
	}
	public void setAnioTitu(String anioTitu) {
		this.anioTitu = anioTitu;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNsDetalle() {
		return nsDetalle;
	}
	public void setNsDetalle(String nsDetalle) {
		this.nsDetalle = nsDetalle;
	}
	public String getNumHoja() {
		return numHoja;
	}
	public void setNumHoja(String numHoja) {
		this.numHoja = numHoja;
	}
	public String getNumTitu() {
		return numTitu;
	}
	public void setNumTitu(String numTitu) {
		this.numTitu = numTitu;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	public String getPlazo() {
		return plazo;
	}
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}
	public String getPresentante() {
		return presentante;
	}
	public void setPresentante(String presentante) {
		this.presentante = presentante;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	

}
