package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class TituloPendienteBean implements Serializable
{
	private static final long serialVersionUID = -4855486414554535080L;
	
	private String descripcionRegistroPublico;
	private String numeroTitulo;
	private String fechaPresentacion;
	private String fechaVencimiento;
	private String descripcionActo;
	private String descripcionEstado;
	
	public String getDescripcionActo() {
		return descripcionActo;
	}
	public void setDescripcionActo(String descripcionActo) {
		this.descripcionActo = descripcionActo;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public String getDescripcionRegistroPublico() {
		return descripcionRegistroPublico;
	}
	public void setDescripcionRegistroPublico(String descripcionRegistroPublico) {
		this.descripcionRegistroPublico = descripcionRegistroPublico;
	}
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getNumeroTitulo() {
		return numeroTitulo;
	}
	public void setNumeroTitulo(String numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}
	
}
