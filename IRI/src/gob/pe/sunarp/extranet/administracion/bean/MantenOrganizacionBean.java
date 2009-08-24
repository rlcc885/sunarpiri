package gob.pe.sunarp.extranet.administracion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
Bean utilizado para mostrar los resultados de la
búsqueda
*/
public class MantenOrganizacionBean extends SunarpBean{

	private String ruc;
	private String usuario_adm;
	private String razsoc;
	private String siglas;
	private String direccion;
	private String num_usu;
	private String cod_org;
	private String tipoOrg;
	
	//---h
	private String usuarioId="";
	private String flagActivo="";
	private String flagExonPago="";	
	
	private String lineaPrepago;
	
	//22oct2002_HT
	private String fechaAfiliacion="";
	private String fechaUltimoAcceso="";
	private String diasDesdeUltimoAcceso="";
	
	private String saldo;
	
//___________SETTERS Y GETTERS_______________


	/**
	 * Gets the ruc
	 * @return Returns a String
	 */
	public String getRuc() {
		return ruc;
	}
	/**
	 * Sets the ruc
	 * @param ruc The ruc to set
	 */
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	/**
	 * Gets the usuario_adm
	 * @return Returns a String
	 */
	public String getUsuario_adm() {
		return usuario_adm;
	}
	/**
	 * Sets the usuario_adm
	 * @param usuario_adm The usuario_adm to set
	 */
	public void setUsuario_adm(String usuario_adm) {
		this.usuario_adm = usuario_adm;
	}

	/**
	 * Gets the razsoc
	 * @return Returns a String
	 */
	public String getRazsoc() {
		return razsoc;
	}
	/**
	 * Sets the razsoc
	 * @param razsoc The razsoc to set
	 */
	public void setRazsoc(String razsoc) {
		this.razsoc = razsoc;
	}

	/**
	 * Gets the siglas
	 * @return Returns a String
	 */
	public String getSiglas() {
		return siglas;
	}
	/**
	 * Sets the siglas
	 * @param siglas The siglas to set
	 */
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	/**
	 * Gets the direccion
	 * @return Returns a String
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * Sets the direccion
	 * @param direccion The direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Gets the num_usu
	 * @return Returns a String
	 */
	public String getNum_usu() {
		return num_usu;
	}
	/**
	 * Sets the num_usu
	 * @param num_usu The num_usu to set
	 */
	public void setNum_usu(String num_usu) {
		this.num_usu = num_usu;
	}

	/**
	 * Gets the cod_org
	 * @return Returns a String
	 */
	public String getCod_org() {
		return cod_org;
	}
	/**
	 * Sets the cod_org
	 * @param cod_org The cod_org to set
	 */
	public void setCod_org(String cod_org) {
		this.cod_org = cod_org;
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
	 * Gets the usuarioId
	 * @return Returns a String
	 */
	public String getUsuarioId() {
		return usuarioId;
	}
	/**
	 * Sets the usuarioId
	 * @param usuarioId The usuarioId to set
	 */
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
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
	 * Gets the tipoOrg
	 * @return Returns a String
	 */
	public String getTipoOrg() {
		return tipoOrg;
	}
	/**
	 * Sets the tipoOrg
	 * @param tipoOrg The tipoOrg to set
	 */
	public void setTipoOrg(String tipoOrg) {
		this.tipoOrg = tipoOrg;
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

}

