package gob.pe.sunarp.extranet.publicidad.bean;

import java.sql.Timestamp;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class TituloBean extends SunarpBean{


	private String regPub_id;
	private String regPub_nombre;
	private String oficReg_id;
	private String oficReg_nombre;
	private String anno;
	private String titulo;
	private String fecPresent;
	private String horPresent;
	private String estado_id;
	private String estadoDesc;
	private String montoLiquida;
	private String fecVencimiento;
	private String primerPrtcApePat = "";
	private String primerPrtcApeMat = "";
	private String primerPrtcNombres = "";
	
	private String resultado = "";
	private String tipoRegistro;
	private String url = "";
	private String desc_url = "";
	private Timestamp fechaPresentacion;
	
	//Inicio:mgarate:11/07/2007
	private String numeroOrden = "&nbsp;";
    private double montoPagado = 0;
    private String simboloMontoPagado = "&nbsp;";
    private String numeroRecibo = "&nbsp;";
	//Fin:mgarate
    
    //Inicio:dbravo 18/07/2007
    private String refNumTitulo;
    /**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the refNumTitulo
	 */
	public String getRefNumTitulo() {
		return refNumTitulo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param refNumTitulo the refNumTitulo to set
	 */
	public void setRefNumTitulo(String refNumTitulo) {
		this.refNumTitulo = refNumTitulo;
	}
	//Fin:dbravo 18/07/2007
	/**
	 * Gets the regPub_id
	 * @return Returns a String
	 */
	public String getRegPub_id() {
		return regPub_id;
	}
	/**
	 * Sets the regPub_id
	 * @param regPub_id The regPub_id to set
	 */
	public void setRegPub_id(String regPub_id) {
		this.regPub_id = regPub_id;
	}

	/**
	 * Gets the oficReg_id
	 * @return Returns a String
	 */
	public String getOficReg_id() {
		return oficReg_id;
	}
	/**
	 * Sets the oficReg_id
	 * @param oficReg_id The oficReg_id to set
	 */
	public void setOficReg_id(String oficReg_id) {
		this.oficReg_id = oficReg_id;
	}

	/**
	 * Gets the anno
	 * @return Returns a String
	 */
	public String getAnno() {
		return anno;
	}
	/**
	 * Sets the anno
	 * @param anno The anno to set
	 */
	public void setAnno(String anno) {
		this.anno = anno;
	}

	/**
	 * Gets the titulo
	 * @return Returns a String
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Sets the titulo
	 * @param titulo The titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Gets the fecPresent
	 * @return Returns a String
	 */
	public String getFecPresent() {
		return fecPresent;
	}
	/**
	 * Sets the fecPresent
	 * @param fecPresent The fecPresent to set
	 */
	public void setFecPresent(String fecPresent) {
		this.fecPresent = fecPresent;
	}

	/**
	 * Gets the horPresent
	 * @return Returns a String
	 */
	public String getHorPresent() {
		return horPresent;
	}
	/**
	 * Sets the horPresent
	 * @param horPresent The horPresent to set
	 */
	public void setHorPresent(String horPresent) {
		this.horPresent = horPresent;
	}

	/**
	 * Gets the estado_id
	 * @return Returns a String
	 */
	public String getEstado_id() {
		return estado_id;
	}
	/**
	 * Sets the estado_id
	 * @param estado_id The estado_id to set
	 */
	public void setEstado_id(String estado_id) {
		this.estado_id = estado_id;
	}

	/**
	 * Gets the estadoDesc
	 * @return Returns a String
	 */
	public String getEstadoDesc() {
		return estadoDesc;
	}
	/**
	 * Sets the estadoDesc
	 * @param estadoDesc The estadoDesc to set
	 */
	public void setEstadoDesc(String estadoDesc) {
		this.estadoDesc = estadoDesc;
	}

	/**
	 * Gets the montoLiquida
	 * @return Returns a String
	 */
	public String getMontoLiquida() {
		return montoLiquida;
	}
	/**
	 * Sets the montoLiquida
	 * @param montoLiquida The montoLiquida to set
	 */
	public void setMontoLiquida(String montoLiquida) {
		this.montoLiquida = montoLiquida;
	}

	/**
	 * Gets the fecVencimiento
	 * @return Returns a String
	 */
	public String getFecVencimiento() {
		return fecVencimiento;
	}
	/**
	 * Sets the fecVencimiento
	 * @param fecVencimiento The fecVencimiento to set
	 */
	public void setFecVencimiento(String fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

	/**
	 * Gets the primerPrtcApePat
	 * @return Returns a String
	 */
	public String getPrimerPrtcApePat() {
		return primerPrtcApePat;
	}
	/**
	 * Sets the primerPrtcApePat
	 * @param primerPrtcApePat The primerPrtcApePat to set
	 */
	public void setPrimerPrtcApePat(String primerPrtcApePat) {
		this.primerPrtcApePat = primerPrtcApePat;
	}

	/**
	 * Gets the primerPrtcApeMat
	 * @return Returns a String
	 */
	public String getPrimerPrtcApeMat() {
		return primerPrtcApeMat;
	}
	/**
	 * Sets the primerPrtcApeMat
	 * @param primerPrtcApeMat The primerPrtcApeMat to set
	 */
	public void setPrimerPrtcApeMat(String primerPrtcApeMat) {
		this.primerPrtcApeMat = primerPrtcApeMat;
	}

	/**
	 * Gets the primerPrtcNombres
	 * @return Returns a String
	 */
	public String getPrimerPrtcNombres() {
		return primerPrtcNombres;
	}
	/**
	 * Sets the primerPrtcNombres
	 * @param primerPrtcNombres The primerPrtcNombres to set
	 */
	public void setPrimerPrtcNombres(String primerPrtcNombres) {
		this.primerPrtcNombres = primerPrtcNombres;
	}

	/**
	 * Gets the resultado
	 * @return Returns a String
	 */
	public String getResultado() {
		return resultado;
	}
	/**
	 * Sets the resultado
	 * @param resultado The resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * Gets the tipoRegistro
	 * @return Returns a String
	 */
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	/**
	 * Sets the tipoRegistro
	 * @param tipoRegistro The tipoRegistro to set
	 */
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	/**
	 * Gets the url
	 * @return Returns a String
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Sets the url
	 * @param url The url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the desc_url
	 * @return Returns a String
	 */
	public String getDesc_url() {
		return desc_url;
	}
	/**
	 * Sets the desc_url
	 * @param desc_url The desc_url to set
	 */
	public void setDesc_url(String desc_url) {
		this.desc_url = desc_url;
	}

	/**
	 * Gets the regPub_nombre
	 * @return Returns a String
	 */
	public String getRegPub_nombre() {
		return regPub_nombre;
	}
	/**
	 * Sets the regPub_nombre
	 * @param regPub_nombre The regPub_nombre to set
	 */
	public void setRegPub_nombre(String regPub_nombre) {
		this.regPub_nombre = regPub_nombre;
	}

	/**
	 * Gets the oficReg_nombre
	 * @return Returns a String
	 */
	public String getOficReg_nombre() {
		return oficReg_nombre;
	}
	/**
	 * Sets the oficReg_nombre
	 * @param oficReg_nombre The oficReg_nombre to set
	 */
	public void setOficReg_nombre(String oficReg_nombre) {
		this.oficReg_nombre = oficReg_nombre;
	}

	/**
	 * @autor dbravo
	 * @fecha Jul 16, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the montoPagado
	 */
	public double getMontoPagado() {
		return montoPagado;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 16, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param montoPagado the montoPagado to set
	 */
	public void setMontoPagado(double montoPagado) {
		this.montoPagado = montoPagado;
	}
	public String getNumeroOrden() {
		return numeroOrden;
	}
	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}
	public String getNumeroRecibo() {
		return numeroRecibo;
	}
	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 16, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaPresentacion
	 */
	public Timestamp getFechaPresentacion() {
		return fechaPresentacion;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 16, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaPresentacion the fechaPresentacion to set
	 */
	public void setFechaPresentacion(Timestamp fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	/**
	 * @return the simboloMontoPagado
	 */
	public String getSimboloMontoPagado() {
		return simboloMontoPagado;
	}
	/**
	 * @param simboloMontoPagado the simboloMontoPagado to set
	 */
	public void setSimboloMontoPagado(String simboloMontoPagado) {
		this.simboloMontoPagado = simboloMontoPagado;
	}
	
}

