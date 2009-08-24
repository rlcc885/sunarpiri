package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.util.FechaUtil;

public class FechaBusquedaBean extends SunarpBean{
	
	
	private String dia_inicio;
	private String mes_inicio;
	private String anno_inicio;
	private String dia_final;
	private String mes_final;
	private String anno_final;

	public void almacenaFechaHoy(){
		String fecha_hoy;  
		fecha_hoy = FechaUtil.getCurrentDateYYYYMMDD();
		setDia_inicio(fecha_hoy.substring(6,8));
		setMes_inicio(fecha_hoy.substring(4,6));
		setAnno_inicio(fecha_hoy.substring(0,4));
		setDia_final(fecha_hoy.substring(6,8));
		setMes_final(fecha_hoy.substring(4,6));
		setAnno_final(fecha_hoy.substring(0,4));			
	}
	
	/**
	 * Gets the dia_inicio
	 * @return Returns a String
	 */
	public String getDia_inicio() {
		return dia_inicio;
	}
	/**
	 * Sets the dia_inicio
	 * @param dia_inicio The dia_inicio to set
	 */
	public void setDia_inicio(String dia_inicio) {
		this.dia_inicio = dia_inicio;
	}

	/**
	 * Gets the mes_inicio
	 * @return Returns a String
	 */
	public String getMes_inicio() {
		return mes_inicio;
	}
	/**
	 * Sets the mes_inicio
	 * @param mes_inicio The mes_inicio to set
	 */
	public void setMes_inicio(String mes_inicio) {
		this.mes_inicio = mes_inicio;
	}

	/**
	 * Gets the anno_inicio
	 * @return Returns a String
	 */
	public String getAnno_inicio() {
		return anno_inicio;
	}
	/**
	 * Sets the anno_inicio
	 * @param anno_inicio The anno_inicio to set
	 */
	public void setAnno_inicio(String anno_inicio) {
		this.anno_inicio = anno_inicio;
	}

	/**
	 * Gets the dia_final
	 * @return Returns a String
	 */
	public String getDia_final() {
		return dia_final;
	}
	/**
	 * Sets the dia_final
	 * @param dia_final The dia_final to set
	 */
	public void setDia_final(String dia_final) {
		this.dia_final = dia_final;
	}

	/**
	 * Gets the mes_final
	 * @return Returns a String
	 */
	public String getMes_final() {
		return mes_final;
	}
	/**
	 * Sets the mes_final
	 * @param mes_final The mes_final to set
	 */
	public void setMes_final(String mes_final) {
		this.mes_final = mes_final;
	}

	/**
	 * Gets the anno_final
	 * @return Returns a String
	 */
	public String getAnno_final() {
		return anno_final;
	}
	/**
	 * Sets the anno_final
	 * @param anno_final The anno_final to set
	 */
	public void setAnno_final(String anno_final) {
		this.anno_final = anno_final;
	}

}


