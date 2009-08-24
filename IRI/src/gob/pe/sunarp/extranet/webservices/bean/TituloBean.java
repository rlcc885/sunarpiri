package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class TituloBean implements Serializable
{
	private static final long serialVersionUID = -1427449870919935608L;
	
	private String numeroTitulo;
	private String numeroOrden;
	private String fechaPresentacion;
	private String derechosPagados;
	private String numeroRecibos;
	private String fechaInscripcion;
	private String descripcionOficinaRegistral;
	
	public String getDerechosPagados() {
		return derechosPagados;
	}
	public void setDerechosPagados(String derechosPagados) {
		this.derechosPagados = derechosPagados;
	}
	public String getDescripcionOficinaRegistral() {
		return descripcionOficinaRegistral;
	}
	public void setDescripcionOficinaRegistral(String descripcionOficinaRegistral) {
		this.descripcionOficinaRegistral = descripcionOficinaRegistral;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public String getNumeroOrden() {
		return numeroOrden;
	}
	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}
	public String getNumeroRecibos() {
		return numeroRecibos;
	}
	public void setNumeroRecibos(String numeroRecibos) {
		this.numeroRecibos = numeroRecibos;
	}
	public String getNumeroTitulo() {
		return numeroTitulo;
	}
	public void setNumeroTitulo(String numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}

}
