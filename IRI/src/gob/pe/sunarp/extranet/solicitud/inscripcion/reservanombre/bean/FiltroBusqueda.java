package gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/** @modelguid {86D652E1-7151-4341-ABD5-55A78B6B0999} */
public class FiltroBusqueda extends SunarpBean{
	

	/** @modelguid {6B26E058-716D-4278-91A9-FC4E98E8366F} */
	private String razonSocial;

	/** @modelguid {53C2B21B-5B4B-4729-97A4-BDAB8D98B337} */
	private String siglas;

	/**
	 * 
	 * @modelguid {07BE9A31-C378-4BC9-8CE3-BC53A53A78D6}
	 */
	private java.util.ArrayList resultadoBusqueda;


	/**
	 * @return
	 * @modelguid {12B4503A-3FE3-428D-BC9D-89A745B772D1}
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @return
	 * @modelguid {B7D5CDA8-EE70-4A8D-B95A-E8D3A2C36B7B}
	 */
	public String getSiglas() {
		return siglas;
	}

	/**
	 * @param string
	 * @modelguid {ABEF181F-7C24-4FF4-A867-D34D208997F7}
	 */
	public void setRazonSocial(String string) {
		razonSocial = string;
	}

	/**
	 * @param string
	 * @modelguid {48CB8F24-0CD7-4445-89E1-CB2B12F0CA4B}
	 */
	public void setSiglas(String string) {
		siglas = string;
	}

	/**
	 * @return
	 */
	public java.util.ArrayList getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	/**
	 * @param list
	 */
	public void setResultadoBusqueda(java.util.ArrayList list) {
		resultadoBusqueda = list;
	}

}