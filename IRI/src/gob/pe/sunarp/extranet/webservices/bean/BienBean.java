package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;



public class BienBean implements Serializable 
{
	private static final long serialVersionUID = 7713715817992184202L;
	
	private String descripcionBien;
	private String valorizacion;
	
	public String getDescripcionBien() {
		return descripcionBien;
	}
	public void setDescripcionBien(String descripcionBien) {
		this.descripcionBien = descripcionBien;
	}
	public String getValorizacion() {
		return valorizacion;
	}
	public void setValorizacion(String valorizacion) {
		this.valorizacion = valorizacion;
	}
}
