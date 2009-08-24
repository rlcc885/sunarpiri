package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class BuscaCargaLaboralBean extends SunarpBean{
	
	private String solicitud_id;	
	private String certificado_id;	
	private String nombre_Cert;
	private String tpo_persona;
	private String objeto_certPN;
	private String objeto_certPJ;
	private String ofic_registral;
	private String estado_sol;
	private String estado_ext_sol;
	private String num_partida;
	private String area_reg_id;
	private String rol;
	private String cuenta_rol;
	private String estado_sol_x_carga;
	private String ver_detalle;
	private String accion;
	private String destiNombre;
	private String destiEnvio;
	private String destiDire;
	private String destiDpto;
	private String estado_sol_id;
	private String tpo_pers_solicitante;
	private String solicitante_PN;
	private String solicitante_PJ;
	//Inicio: jascencio:29/05/2007
	//SUNARP-REGMOBCOM:se esta agregando las siguientes variables y sus metodos
	//get y set respectivamente
	private String placa;
	private String nombreBien;
	private String numeroSerie;
	private String numeroMatricula;
	private String tipoParticipante;
	private String siglas;
    private String descripcionObjetoCertificado;
    private String tipoDocumento;
    private String numeroDocumento;
    private String tipoInformacionDominio;
    private String numeroPartida;
    private String tipoCertificado;
	//Fin: jascencio:29/05/2007
	
	//inicio:dbravo:20/08/2007
    private String flagPagoCrem;
    private String tipoRegistro;
    //fin:dbravo:20/08/2007
		
	
	/**
	 * @autor dbravo
	 * @fecha Sep 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the tipoRegistro
	 */
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	/**
	 * @autor dbravo
	 * @fecha Sep 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param tipoRegistro the tipoRegistro to set
	 */
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the tipoCertificado
	 */
	public String getTipoCertificado() {
		return tipoCertificado;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param tipoCertificado the tipoCertificado to set
	 */
	public void setTipoCertificado(String tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}
	/**
	 * Gets the solicitud_id
	 * @return Returns a String
	 */
	public String getSolicitud_id() {
		return solicitud_id;
	}
	/**
	 * Sets the solicitud_id
	 * @param solicitud_id The solicitud_id to set
	 */
	public void setSolicitud_id(String solicitud_id) {
		this.solicitud_id = solicitud_id;
	}

	/**
	 * Gets the certificado_id
	 * @return Returns a String
	 */
	public String getCertificado_id() {
		return certificado_id;
	}
	/**
	 * Sets the certificado_id
	 * @param certificado_id The certificado_id to set
	 */
	public void setCertificado_id(String certificado_id) {
		this.certificado_id = certificado_id;
	}

	/**
	 * Gets the tpo_persona
	 * @return Returns a String
	 */
	public String getTpo_persona() {
		return tpo_persona;
	}
	/**
	 * Sets the tpo_persona
	 * @param tpo_persona The tpo_persona to set
	 */
	public void setTpo_persona(String tpo_persona) {
		this.tpo_persona = tpo_persona;
	}

	
	

	/**
	 * Gets the ofic_registral
	 * @return Returns a String
	 */
	public String getOfic_registral() {
		return ofic_registral;
	}
	/**
	 * Sets the ofic_registral
	 * @param ofic_registral The ofic_registral to set
	 */
	public void setOfic_registral(String ofic_registral) {
		this.ofic_registral = ofic_registral;
	}

	/**
	 * Gets the estado_sol
	 * @return Returns a String
	 */
	public String getEstado_sol() {
		return estado_sol;
	}
	/**
	 * Sets the estado_sol
	 * @param estado_sol The estado_sol to set
	 */
	public void setEstado_sol(String estado_sol) {
		this.estado_sol = estado_sol;
	}

	/**
	 * Gets the ver_detalle
	 * @return Returns a String
	 */
	public String getVer_detalle() {
		return ver_detalle;
	}
	/**
	 * Sets the ver_detalle
	 * @param ver_detalle The ver_detalle to set
	 */
	public void setVer_detalle(String ver_detalle) {
		this.ver_detalle = ver_detalle;
	}

	/**
	 * Gets the accion
	 * @return Returns a String
	 */
	public String getAccion() {
		return accion;
	}
	/**
	 * Sets the accion
	 * @param accion The accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * Gets the num_partida
	 * @return Returns a String
	 */
	public String getNum_partida() {
		return num_partida;
	}
	/**
	 * Sets the num_partida
	 * @param num_partida The num_partida to set
	 */
	public void setNum_partida(String num_partida) {
		this.num_partida = num_partida;
	}

	/**
	 * Gets the area_reg_id
	 * @return Returns a String
	 */
	public String getArea_reg_id() {
		return area_reg_id;
	}
	/**
	 * Sets the area_reg_id
	 * @param area_reg_id The area_reg_id to set
	 */
	public void setArea_reg_id(String area_reg_id) {
		this.area_reg_id = area_reg_id;
	}

	/**
	 * Gets the rol
	 * @return Returns a String
	 */
	public String getRol() {
		return rol;
	}
	/**
	 * Sets the rol
	 * @param rol The rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Gets the cuenta_rol
	 * @return Returns a String
	 */
	public String getCuenta_rol() {
		return cuenta_rol;
	}
	/**
	 * Sets the cuenta_rol
	 * @param cuenta_rol The cuenta_rol to set
	 */
	public void setCuenta_rol(String cuenta_rol) {
		this.cuenta_rol = cuenta_rol;
	}

	/**
	 * Gets the objeto_certPN
	 * @return Returns a String
	 */
	public String getObjeto_certPN() {
		return objeto_certPN;
	}
	/**
	 * Sets the objeto_certPN
	 * @param objeto_certPN The objeto_certPN to set
	 */
	public void setObjeto_certPN(String objeto_certPN) {
		this.objeto_certPN = objeto_certPN;
	}

	/**
	 * Gets the objeto_certPJ
	 * @return Returns a String
	 */
	public String getObjeto_certPJ() {
		return objeto_certPJ;
	}
	/**
	 * Sets the objeto_certPJ
	 * @param objeto_certPJ The objeto_certPJ to set
	 */
	public void setObjeto_certPJ(String objeto_certPJ) {
		this.objeto_certPJ = objeto_certPJ;
	}

	/**
	 * Gets the estado_sol_x_carga
	 * @return Returns a String
	 */
	public String getEstado_sol_x_carga() {
		return estado_sol_x_carga;
	}
	/**
	 * Sets the estado_sol_x_carga
	 * @param estado_sol_x_carga The estado_sol_x_carga to set
	 */
	public void setEstado_sol_x_carga(String estado_sol_x_carga) {
		this.estado_sol_x_carga = estado_sol_x_carga;
	}

	/**
	 * Gets the nombre_Cert
	 * @return Returns a String
	 */
	public String getNombre_Cert() {
		return nombre_Cert;
	}
	/**
	 * Sets the nombre_Cert
	 * @param nombre_Cert The nombre_Cert to set
	 */
	public void setNombre_Cert(String nombre_Cert) {
		this.nombre_Cert = nombre_Cert;
	}

	/**
	 * Gets the destiNombre
	 * @return Returns a String
	 */
	public String getDestiNombre() {
		return destiNombre;
	}
	/**
	 * Sets the destiNombre
	 * @param destiNombre The destiNombre to set
	 */
	public void setDestiNombre(String destiNombre) {
		this.destiNombre = destiNombre;
	}

	/**
	 * Gets the destiEnvio
	 * @return Returns a String
	 */
	public String getDestiEnvio() {
		return destiEnvio;
	}
	/**
	 * Sets the destiEnvio
	 * @param destiEnvio The destiEnvio to set
	 */
	public void setDestiEnvio(String destiEnvio) {
		this.destiEnvio = destiEnvio;
	}

	/**
	 * Gets the destiDire
	 * @return Returns a String
	 */
	public String getDestiDire() {
		return destiDire;
	}
	/**
	 * Sets the destiDire
	 * @param destiDire The destiDire to set
	 */
	public void setDestiDire(String destiDire) {
		this.destiDire = destiDire;
	}

	/**
	 * Gets the destiDpto
	 * @return Returns a String
	 */
	public String getDestiDpto() {
		return destiDpto;
	}
	/**
	 * Sets the destiDpto
	 * @param destiDepto The destiDpto to set
	 */
	public void setDestiDpto(String destiDpto) {
		this.destiDpto = destiDpto;
	}

	/**
	 * Gets the estado_sol_id
	 * @return Returns a String
	 */
	public String getEstado_sol_id() {
		return estado_sol_id;
	}
	/**
	 * Sets the estado_sol_id
	 * @param estado_sol_id The estado_sol_id to set
	 */
	public void setEstado_sol_id(String estado_sol_id) {
		this.estado_sol_id = estado_sol_id;
	}

	/**
	 * Gets the tpo_pers_solicitante
	 * @return Returns a String
	 */
	public String getTpo_pers_solicitante() {
		return tpo_pers_solicitante;
	}
	/**
	 * Sets the tpo_pers_solicitante
	 * @param tpo_pers_solicitante The tpo_pers_solicitante to set
	 */
	public void setTpo_pers_solicitante(String tpo_pers_solicitante) {
		this.tpo_pers_solicitante = tpo_pers_solicitante;
	}

	/**
	 * Gets the solicitante_PN
	 * @return Returns a String
	 */
	public String getSolicitante_PN() {
		return solicitante_PN;
	}
	/**
	 * Sets the solicitante_PN
	 * @param solicitante_PN The solicitante_PN to set
	 */
	public void setSolicitante_PN(String solicitante_PN) {
		this.solicitante_PN = solicitante_PN;
	}

	/**
	 * Gets the solicitante_PJ
	 * @return Returns a String
	 */
	public String getSolicitante_PJ() {
		return solicitante_PJ;
	}
	/**
	 * Sets the solicitante_PJ
	 * @param solicitante_PJ The solicitante_PJ to set
	 */
	public void setSolicitante_PJ(String solicitante_PJ) {
		this.solicitante_PJ = solicitante_PJ;
	}

	/**
	 * Gets the estado_ext_sol
	 * @return Returns a String
	 */
	public String getEstado_ext_sol() {
		return estado_ext_sol;
	}
	/**
	 * Sets the estado_ext_sol
	 * @param estado_ext_sol The estado_ext_sol to set
	 */
	public void setEstado_ext_sol(String estado_ext_sol) {
		this.estado_ext_sol = estado_ext_sol;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the descripcionObjetoCertificado
	 */
	public String getDescripcionObjetoCertificado() {
		return descripcionObjetoCertificado;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param descripcionObjetoCertificado the descripcionObjetoCertificado to set
	 */
	public void setDescripcionObjetoCertificado(String descripcionObjetoCertificado) {
		this.descripcionObjetoCertificado = descripcionObjetoCertificado;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the nombreBien
	 */
	public String getNombreBien() {
		return nombreBien;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param nombreBien the nombreBien to set
	 */
	public void setNombreBien(String nombreBien) {
		this.nombreBien = nombreBien;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroMatricula
	 */
	public String getNumeroMatricula() {
		return numeroMatricula;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroMatricula the numeroMatricula to set
	 */
	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroSerie
	 */
	public String getNumeroSerie() {
		return numeroSerie;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroSerie the numeroSerie to set
	 */
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the placa
	 */
	public String getPlaca() {
		return placa;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param placa the placa to set
	 */
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the siglas
	 */
	public String getSiglas() {
		return siglas;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param siglas the siglas to set
	 */
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the tipoParticipante
	 */
	public String getTipoParticipante() {
		return tipoParticipante;
	}
	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param tipoParticipante the tipoParticipante to set
	 */
	public void setTipoParticipante(String tipoParticipante) {
		this.tipoParticipante = tipoParticipante;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroPartida
	 */
	public String getNumeroPartida() {
		return numeroPartida;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroPartida the numeroPartida to set
	 */
	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the tipoInformacionDominio
	 */
	public String getTipoInformacionDominio() {
		return tipoInformacionDominio;
	}
	/**
	 * @autor jascencio
	 * @fecha 31/05/2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param tipoInformacionDominio the tipoInformacionDominio to set
	 */
	public void setTipoInformacionDominio(String tipoInformacionDominio) {
		this.tipoInformacionDominio = tipoInformacionDominio;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 20, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the flagPagoCrem
	 */
	public String getFlagPagoCrem() {
		return flagPagoCrem;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 20, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param flagPagoCrem the flagPagoCrem to set
	 */
	public void setFlagPagoCrem(String flagPagoCrem) {
		this.flagPagoCrem = flagPagoCrem;
	}


	

}

