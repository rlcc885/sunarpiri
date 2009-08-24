package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class DocumentoBean implements Serializable
{
	private static final long serialVersionUID = -6014237734937726189L;
	
	private String documento;
	private String funcionario;
	private String fecha;
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

}
