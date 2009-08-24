package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class MediosPagoBean extends SunarpBean{
	
	private String medio;
	private String tipo;
	private String desc;
	private boolean selected = false;

	public String getMedio() {
		return medio;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

