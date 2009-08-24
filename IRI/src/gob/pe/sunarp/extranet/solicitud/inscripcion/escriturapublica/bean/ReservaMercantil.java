package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/** @modelguid {30D9ED73-45C4-4CC7-87AC-286A69120947} */
public class ReservaMercantil extends SunarpBean{
	
	/** @modelguid {80BDD4CF-28ED-46CA-A253-90F9DB9875A0} */
	private String anhoTitulo;

	/** @modelguid {EFF30758-74E7-405C-ACFC-A05884602F85} */
	private String numeroTitulo;



	/**
	 * @return
	 * @modelguid {9FB8BCAA-ADD1-49B8-A9C9-C0FA0F9A774D}
	 */
	public String getAnhoTitulo() {
		return anhoTitulo;
	}

	/**
	 * @return
	 * @modelguid {E670DB7B-3C30-411B-A88F-9CC71897B304}
	 */
	public String getNumeroTitulo() {
		return numeroTitulo;
	}

	/**
	 * @param string
	 * @modelguid {4D806228-E6D9-42FB-935B-516BB1977B81}
	 */
	public void setAnhoTitulo(String string) {
		anhoTitulo = string;
	}

	/**
	 * @param string
	 * @modelguid {3DE9E361-E7F6-4DF3-A5B9-0129B16E58E0}
	 */
	public void setNumeroTitulo(String string) {
		numeroTitulo = string;
	}

}

