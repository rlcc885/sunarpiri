package gob.pe.sunarp.extranet.administracion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class MantenUsuarioBean extends SunarpBean{

	private String usuario;
	private String tipo;
	private String ape_Nom;
	private String org_afiliada;
	private String admin_Org;
	
	//---8sep2002h
	private String flagActivo="";
	private String flagExonPago="";
	private String tipoDocumentoDesc="";
	private String numeroDocumento="";
	//---
	//---9sep2002LSJ
	private String fechaHoraRegistro;
	private String saldo;
	private String estado;
	
	//--16Sept2002cjvc77
	private String lineaPrepago;
	
	//
	private String fechaAfiliacion="";
	private String fechaUltimoAcceso="";
	private String diasDesdeUltimoAcceso="";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Gets the usuario
	 * @return Returns a String
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * Sets the usuario
	 * @param usuario The usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Gets the tipo
	 * @return Returns a String
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Sets the tipo
	 * @param tipo The tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the ape_Nom
	 * @return Returns a String
	 */
	public String getApe_Nom() {
		return ape_Nom;
	}
	/**
	 * Sets the ape_Nom
	 * @param ape_Nom The ape_Nom to set
	 */
	public void setApe_Nom(String ape_Nom) {
		this.ape_Nom = ape_Nom;
	}

	/**
	 * Gets the org_afiliada
	 * @return Returns a String
	 */
	public String getOrg_afiliada() {
		return org_afiliada;
	}
	/**
	 * Sets the org_afiliada
	 * @param org_afiliada The org_afiliada to set
	 */
	public void setOrg_afiliada(String org_afiliada) {
		this.org_afiliada = org_afiliada;
	}

	/**
	 * Gets the admin_Org
	 * @return Returns a string
	 */
	public String getAdmin_Org() {
		return admin_Org;
	}
	/**
	 * Sets the admin_Org
	 * @param admin_Org The admin_Org to set
	 */
	public void setAdmin_Org(String admin_Org) {
		this.admin_Org = admin_Org;
	}

	/**
	 * Gets the flagActivo
	 * @return Returns a String
	 */
	public String getFlagActivo() {
		return flagActivo;
	}
	/**
	 * Sets the flagActivo
	 * @param flagActivo The flagActivo to set
	 */
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	/**
	 * Gets the flagExonPago
	 * @return Returns a String
	 */
	public String getFlagExonPago() {
		return flagExonPago;
	}
	/**
	 * Sets the flagExonPago
	 * @param flagExonPago The flagExonPago to set
	 */
	public void setFlagExonPago(String flagExonPago) {
		this.flagExonPago = flagExonPago;
	}

	/**
	 * Gets the tipoDocumentoDesc
	 * @return Returns a String
	 */
	public String getTipoDocumentoDesc() {
		return tipoDocumentoDesc;
	}
	/**
	 * Sets the tipoDocumentoDesc
	 * @param tipoDocumentoDesc The tipoDocumentoDesc to set
	 */
	public void setTipoDocumentoDesc(String tipoDocumentoDesc) {
		this.tipoDocumentoDesc = tipoDocumentoDesc;
	}

	/**
	 * Gets the numeroDocumento
	 * @return Returns a String
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * Sets the numeroDocumento
	 * @param numeroDocumento The numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * Gets the fechaHoraRegistro
	 * @return Returns a String
	 */
	public String getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}
	/**
	 * Sets the fechaHoraRegistro
	 * @param fechaHoraRegistro The fechaHoraRegistro to set
	 */
	public void setFechaHoraRegistro(String fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}

	/**
	 * Gets the saldo
	 * @return Returns a String
	 */
	public String getSaldo() {
		return saldo;
	}
	/**
	 * Sets the saldo
	 * @param saldo The saldo to set
	 */
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Gets the lineaPrepago
	 * @return Returns a String
	 */
	public String getLineaPrepago() {
		return lineaPrepago;
	}
	/**
	 * Sets the lineaPrepago
	 * @param lineaPrepago The lineaPrepago to set
	 */
	public void setLineaPrepago(String lineaPrepago) {
		this.lineaPrepago = lineaPrepago;
	}

	/**
	 * Gets the fechaAfiliacion
	 * @return Returns a String
	 */
	public String getFechaAfiliacion() {
		return fechaAfiliacion;
	}
	/**
	 * Sets the fechaAfiliacion
	 * @param fechaAfiliacion The fechaAfiliacion to set
	 */
	public void setFechaAfiliacion(String fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	/**
	 * Gets the fechaUltimoAcceso
	 * @return Returns a String
	 */
	public String getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}
	/**
	 * Sets the fechaUltimoAcceso
	 * @param fechaUltimoAcceso The fechaUltimoAcceso to set
	 */
	public void setFechaUltimoAcceso(String fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	/**
	 * Gets the diasDesdeUltimoAcceso
	 * @return Returns a String
	 */
	public String getDiasDesdeUltimoAcceso() {
		return diasDesdeUltimoAcceso;
	}
	/**
	 * Sets the diasDesdeUltimoAcceso
	 * @param diasDesdeUltimoAcceso The diasDesdeUltimoAcceso to set
	 */
	public void setDiasDesdeUltimoAcceso(String diasDesdeUltimoAcceso) {
		this.diasDesdeUltimoAcceso = diasDesdeUltimoAcceso;
	}

}

