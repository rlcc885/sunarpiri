package gob.pe.sunarp.extranet.publicidad.bean;

import java.util.ArrayList;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class ConstanciaRjbBean extends SunarpBean
{
	private String descripciónActo;
	private String  fechaAfectacion;
	private String  montoGravamen;
	private ArrayList listaParticipante;
	private ArrayList listaDocumento;
	/** inicio: jrosas: 15-10-07  **/
	private ArrayList  nombrePropietario;
	/** fin: jrosas: 15-10-07  **/
	private String  numeroTitulo;
	private String anoTitulo;
	private String  fechaInscripcion;
	
	public String getAnoTitulo() {
		return anoTitulo;
	}
	public void setAnoTitulo(String anoTitulo) {
		this.anoTitulo = anoTitulo;
	}
	public String getDescripciónActo() {
		return descripciónActo;
	}
	public void setDescripciónActo(String descripciónActo) {
		this.descripciónActo = descripciónActo;
	}
	public String getFechaAfectacion() {
		return fechaAfectacion;
	}
	public void setFechaAfectacion(String fechaAfectacion) {
		this.fechaAfectacion = fechaAfectacion;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public ArrayList getListaDocumento() {
		return listaDocumento;
	}
	public void setListaDocumento(ArrayList listaDocumento) {
		this.listaDocumento = listaDocumento;
	}
	public ArrayList getListaParticipante() {
		return listaParticipante;
	}
	public void setListaParticipante(ArrayList listaParticipante) {
		this.listaParticipante = listaParticipante;
	}
	public String getMontoGravamen() {
		return montoGravamen;
	}
	public void setMontoGravamen(String montoGravamen) {
		this.montoGravamen = montoGravamen;
	}
	public String getNumeroTitulo() {
		return numeroTitulo;
	}
	public void setNumeroTitulo(String numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}
	/**
	 * @return the nombrePropietario
	 */
	public ArrayList getNombrePropietario() {
		return nombrePropietario;
	}
	/**
	 * @param nombrePropietario the nombrePropietario to set
	 */
	public void setNombrePropietario(ArrayList nombrePropietario) {
		this.nombrePropietario = nombrePropietario;
	}
}
