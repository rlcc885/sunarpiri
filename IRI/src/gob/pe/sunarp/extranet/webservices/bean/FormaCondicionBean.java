package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class FormaCondicionBean implements Serializable 
{
	private static final long serialVersionUID = -637598036952256723L;
	
	private String descripcionForma;
	private String descripcionCondicion;
	
	public String getDescripcionCondicion() {
		return descripcionCondicion;
	}
	public void setDescripcionCondicion(String descripcionCondicion) {
		this.descripcionCondicion = descripcionCondicion;
	}
	public String getDescripcionForma() {
		return descripcionForma;
	}
	public void setDescripcionForma(String descripcionForma) {
		this.descripcionForma = descripcionForma;
	}

}
