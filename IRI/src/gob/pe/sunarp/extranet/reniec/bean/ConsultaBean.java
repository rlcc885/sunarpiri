package gob.pe.sunarp.extranet.reniec.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class ConsultaBean extends SunarpBean{


	private String nu_docu;
	private String ap_pate;
	private String ap_mate;
	private String no_pers;
	
	private String es_val;
	private String res_val;
	
	public String getAp_mate() {
		return ap_mate;
	}
	public void setAp_mate(String ap_mate) {
		this.ap_mate = ap_mate;
	}
	public String getAp_pate() {
		return ap_pate;
	}
	public void setAp_pate(String ap_pate) {
		this.ap_pate = ap_pate;
	}
	public String getEs_val() {
		return es_val;
	}
	public void setEs_val(String es_val) {
		this.es_val = es_val;
	}
	public String getNo_pers() {
		return no_pers;
	}
	public void setNo_pers(String no_pers) {
		this.no_pers = no_pers;
	}
	public String getNu_docu() {
		return nu_docu;
	}
	public void setNu_docu(String nu_docu) {
		this.nu_docu = nu_docu;
	}
	public String getRes_val() {
		return res_val;
	}
	public void setRes_val(String res_val) {
		this.res_val = res_val;
	}
}
