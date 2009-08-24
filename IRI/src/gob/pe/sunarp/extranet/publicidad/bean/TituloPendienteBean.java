package gob.pe.sunarp.extranet.publicidad.bean;

import java.sql.Date;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class TituloPendienteBean extends SunarpBean{
	
	private String numRef;
	private String aaTitulo;
	private String nroTitulo;
	private String acto;
	private String zonaReg;
	private String oficReg;
	private String estado_id;
	// Inicio:mgarate:11/07/2007
	private Date fechaPresentacion;
    private Date fechaVencimiento;
    private String actoDescripcion;
    private String estadoDescripcion;
    //Inicio:dbravo:18/07/2007
    private String descripcionOficinaRegistral;
    private String oficinas;
	private String area_registral;
	private String sede;
    
    
    public String getActoDescripcion() {
		return actoDescripcion;
	}
	public void setActoDescripcion(String actoDescripcion) {
		this.actoDescripcion = actoDescripcion;
	}
	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}
	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}
	
	//  Fin mgarate
	/**
	 * Gets the numRef
	 * @return Returns a String
	 */
	public String getNumRef() {
		return numRef;
	}
	/**
	 * Sets the numRef
	 * @param numRef The numRef to set
	 */
	public void setNumRef(String numRef) {
		this.numRef = numRef;
	}

	/**
	 * Gets the aaTitulo
	 * @return Returns a String
	 */
	public String getAaTitulo() {
		return aaTitulo;
	}
	/**
	 * Sets the aaTitulo
	 * @param aaTitulo The aaTitulo to set
	 */
	public void setAaTitulo(String aaTitulo) {
		this.aaTitulo = aaTitulo;
	}

	/**
	 * Gets the nroTitulo
	 * @return Returns a String
	 */
	public String getNroTitulo() {
		return nroTitulo;
	}
	/**
	 * Sets the nroTitulo
	 * @param nroTitulo The nroTitulo to set
	 */
	public void setNroTitulo(String nroTitulo) {
		this.nroTitulo = nroTitulo;
	}

	/**
	 * Gets the acto
	 * @return Returns a String
	 */
	public String getActo() {
		return acto;
	}
	/**
	 * Sets the acto
	 * @param acto The acto to set
	 */
	public void setActo(String acto) {
		this.acto = acto;
	}

	/**
	 * Gets the zonaReg
	 * @return Returns a String
	 */
	public String getZonaReg() {
		return zonaReg;
	}
	/**
	 * Sets the zonaReg
	 * @param zonaReg The zonaReg to set
	 */
	public void setZonaReg(String zonaReg) {
		this.zonaReg = zonaReg;
	}

	/**
	 * Gets the oficReg
	 * @return Returns a String
	 */
	public String getOficReg() {
		return oficReg;
	}
	/**
	 * Sets the oficReg
	 * @param oficReg The oficReg to set
	 */
	public void setOficReg(String oficReg) {
		this.oficReg = oficReg;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the descripcionOficinaRegistral
	 */
	public String getDescripcionOficinaRegistral() {
		return descripcionOficinaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param descripcionOficinaRegistral the descripcionOficinaRegistral to set
	 */
	public void setDescripcionOficinaRegistral(String descripcionOficinaRegistral) {
		this.descripcionOficinaRegistral = descripcionOficinaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaPresentacion
	 */
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaPresentacion the fechaPresentacion to set
	 */
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaVencimiento
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	/**
	 * @return the estado_id
	 */
	public String getEstado_id() {
		return estado_id;
	}
	/**
	 * @param estado_id the estado_id to set
	 */
	public void setEstado_id(String estado_id) {
		this.estado_id = estado_id;
	}
	/**
	 * @return the area_registral
	 */
	public String getArea_registral() {
		return area_registral;
	}
	/**
	 * @param area_registral the area_registral to set
	 */
	public void setArea_registral(String area_registral) {
		this.area_registral = area_registral;
	}
	/**
	 * @return the oficinas
	 */
	public String getOficinas() {
		return oficinas;
	}
	/**
	 * @param oficinas the oficinas to set
	 */
	public void setOficinas(String oficinas) {
		this.oficinas = oficinas;
	}
	/**
	 * @return the sede
	 */
	public String getSede() {
		return sede;
	}
	/**
	 * @param sede the sede to set
	 */
	public void setSede(String sede) {
		this.sede = sede;
	}
	

}

