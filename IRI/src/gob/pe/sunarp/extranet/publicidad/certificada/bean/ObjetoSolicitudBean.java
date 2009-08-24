package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import java.sql.Date;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class ObjetoSolicitudBean extends SunarpBean{
	

	private String objeto_sol_id;
	private String solicitud_id;
	private String reg_pub_id;
	private String ofic_reg_id;
	private String ofic_reg_desc;
	private String certificado_id;
	private String tpo_cert;
	private String certificado_desc;
	private String refnum_part;
	private String refnum;
	private String area_reg_desc;
	private String ns_asie;
	private String cod_acto;
	private String aa_titu;
	private String num_titu;
	private String num_pag;
	private String tpo_pers;
	private String ape_pat;
	private String ape_mat;
	private String nombres;
	private String raz_soc;
	private String subTotal;
	private String desc_certi;
	private String desc_tipop;
	private String desc_regis;
	private String libro;
	private String area_reg_id;
	private String servicio_id;
	private String numPartida;
	private String placa;
	private String ns_asie_placa;
	private String ns_asiento;
	private String numpag;	
	private String desc_acto;
	private String ts_veri_manu;
	private String cod_GLA;
//	Inicio:jascencio:29/05/2007
	//SUNARP-REGMOBCOM:se esta agregando las siguientes variables y sus metodos
	//get y set respectivamente
	private String numeroPartida;
	private String nombreBien;
	private String numeroMatricula;
	private String numeroSerie;
	private String tipoParticipante;
	private String siglas;
	private String tipoDocumento;
	private String numeroDocumento;
	private String tipoInformacionDominio;
	private String tipoRegistro;
	private String insAsiento;
	private String flagHistorico;
	private String urlBusqueda;
	private String criterioBusqueda;
    private String fechaInscripcionASientoDesde;
    private String fechaInscripcionASientoHasta;
    /**
     * 31/08/2007:inicio: dbravo
     * descripcion: Se requiere la fecha en formato Date, para lograr realizar la busqueda por rangos
     */
    private Date  fechaInscripcionAsientoDesdeDate;
    private Date  fechaInscripcionAsientoHastaDate;
    /**
     * 31/08/2007:fin: dbravo
     */
    private String desTipoRegistro;
    
	//Fin:jascencio:29/05/2007
	//Inicio:mgarate:23/06/2007
    private String flagmetodo;
    private String tomo;
    private String folio;
    private String ficha;
    private String numeroMotor;
    //Inicio:jascencio:08/08/2007
    private String refNumParAnterior;
    private String numeroPartidaAnterior;
    private String numeroPaginasAnterior;
    //Fin:jascencio
    
    //inicio:dbravo:10/08/2007
    private String flagAceptaCondicion;
    private String flagEnvioDomicilio;
    //fin:dbravo:10/08/2007
    //inicio:ifigueroa 27/08/2007
    private String desc_GLA;
    private String nombreDocumento;
    //FIN:ifigueroa 27/08/2007
    private String nombreParticipante;
    
    /**
	 * @return the nombreParticipante
	 */
	public String getNombreParticipante() {
		return nombreParticipante;
	}
	/**
	 * @param nombreParticipante the nombreParticipante to set
	 */
	public void setNombreParticipante(String nombreParticipante) {
		this.nombreParticipante = nombreParticipante;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 10, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the flagAceptaCondicion
	 */
	public String getFlagAceptaCondicion() {
		return flagAceptaCondicion;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 10, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param flagAceptaCondicion the flagAceptaCondicion to set
	 */
	public void setFlagAceptaCondicion(String flagAceptaCondicion) {
		this.flagAceptaCondicion = flagAceptaCondicion;
	}
    /**
	 * @autor jascencio
	 * @fecha Aug 9, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroPaginasAnterior
	 */
	public String getNumeroPaginasAnterior() {
		return numeroPaginasAnterior;
	}
	/**
	 * @autor jascencio
	 * @fecha Aug 9, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroPaginasAnterior the numeroPaginasAnterior to set
	 */
	public void setNumeroPaginasAnterior(String numeroPaginasAnterior) {
		this.numeroPaginasAnterior = numeroPaginasAnterior;
	}
	/**
	 * @autor jascencio
	 * @fecha Aug 9, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroPartidaAnterior
	 */
	public String getNumeroPartidaAnterior() {
		return numeroPartidaAnterior;
	}
	/**
	 * @autor jascencio
	 * @fecha Aug 9, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroPartidaAnterior the numeroPartidaAnterior to set
	 */
	public void setNumeroPartidaAnterior(String numeroPartidaAnterior) {
		this.numeroPartidaAnterior = numeroPartidaAnterior;
	}
	
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the criterioBusqueda
	 */
	public String getCriterioBusqueda() {
		return criterioBusqueda;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param criterioBusqueda the criterioBusqueda to set
	 */
	public void setCriterioBusqueda(String criterioBusqueda) {
		this.criterioBusqueda = criterioBusqueda;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the desTipoRegistro
	 */
	public String getDesTipoRegistro() {
		return desTipoRegistro;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param desTipoRegistro the desTipoRegistro to set
	 */
	public void setDesTipoRegistro(String desTipoRegistro) {
		this.desTipoRegistro = desTipoRegistro;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaInscripcionASientoDesde
	 */
	public String getFechaInscripcionASientoDesde() {
		return fechaInscripcionASientoDesde;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaInscripcionASientoDesde the fechaInscripcionASientoDesde to set
	 */
	public void setFechaInscripcionASientoDesde(String fechaInscripcionASientoDesde) {
		this.fechaInscripcionASientoDesde = fechaInscripcionASientoDesde;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaInscripcionASientoHasta
	 */
	public String getFechaInscripcionASientoHasta() {
		return fechaInscripcionASientoHasta;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaInscripcionASientoHasta the fechaInscripcionASientoHasta to set
	 */
	public void setFechaInscripcionASientoHasta(String fechaInscripcionASientoHasta) {
		this.fechaInscripcionASientoHasta = fechaInscripcionASientoHasta;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the ficha
	 */
	public String getFicha() {
		return ficha;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param ficha the ficha to set
	 */
	public void setFicha(String ficha) {
		this.ficha = ficha;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the flagHistorico
	 */
	public String getFlagHistorico() {
		return flagHistorico;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param flagHistorico the flagHistorico to set
	 */
	public void setFlagHistorico(String flagHistorico) {
		this.flagHistorico = flagHistorico;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the flagmetodo
	 */
	public String getFlagmetodo() {
		return flagmetodo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param flagmetodo the flagmetodo to set
	 */
	public void setFlagmetodo(String flagmetodo) {
		this.flagmetodo = flagmetodo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the insAsiento
	 */
	public String getInsAsiento() {
		return insAsiento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param insAsiento the insAsiento to set
	 */
	public void setInsAsiento(String insAsiento) {
		this.insAsiento = insAsiento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the nombreBien
	 */
	public String getNombreBien() {
		return nombreBien;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param nombreBien the nombreBien to set
	 */
	public void setNombreBien(String nombreBien) {
		this.nombreBien = nombreBien;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroMatricula
	 */
	public String getNumeroMatricula() {
		return numeroMatricula;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroMatricula the numeroMatricula to set
	 */
	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroMotor
	 */
	public String getNumeroMotor() {
		return numeroMotor;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroMotor the numeroMotor to set
	 */
	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroPartida
	 */
	public String getNumeroPartida() {
		return numeroPartida;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroPartida the numeroPartida to set
	 */
	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroSerie
	 */
	public String getNumeroSerie() {
		return numeroSerie;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroSerie the numeroSerie to set
	 */
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the siglas
	 */
	public String getSiglas() {
		return siglas;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param siglas the siglas to set
	 */
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the tipoInformacionDominio
	 */
	public String getTipoInformacionDominio() {
		return tipoInformacionDominio;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param tipoInformacionDominio the tipoInformacionDominio to set
	 */
	public void setTipoInformacionDominio(String tipoInformacionDominio) {
		this.tipoInformacionDominio = tipoInformacionDominio;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the tipoParticipante
	 */
	public String getTipoParticipante() {
		return tipoParticipante;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param tipoParticipante the tipoParticipante to set
	 */
	public void setTipoParticipante(String tipoParticipante) {
		this.tipoParticipante = tipoParticipante;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the tipoRegistro
	 */
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param tipoRegistro the tipoRegistro to set
	 */
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the tomo
	 */
	public String getTomo() {
		return tomo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param tomo the tomo to set
	 */
	public void setTomo(String tomo) {
		this.tomo = tomo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the urlBusqueda
	 */
	public String getUrlBusqueda() {
		return urlBusqueda;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 11, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param urlBusqueda the urlBusqueda to set
	 */
	public void setUrlBusqueda(String urlBusqueda) {
		this.urlBusqueda = urlBusqueda;
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
	 * Gets the reg_pub_id
	 * @return Returns a String
	 */
	public String getReg_pub_id() {
		return reg_pub_id;
	}
	/**
	 * Sets the reg_pub_id
	 * @param reg_pub_id The reg_pub_id to set
	 */
	public void setReg_pub_id(String reg_pub_id) {
		this.reg_pub_id = reg_pub_id;
	}

	/**
	 * Gets the ofic_reg_id
	 * @return Returns a String
	 */
	public String getOfic_reg_id() {
		return ofic_reg_id;
	}
	/**
	 * Sets the ofic_reg_id
	 * @param ofic_reg_id The ofic_reg_id to set
	 */
	public void setOfic_reg_id(String ofic_reg_id) {
		this.ofic_reg_id = ofic_reg_id;
	}

	/**
	 * Gets the refnum_part
	 * @return Returns a String
	 */
	public String getRefnum_part() {
		return refnum_part;
	}
	/**
	 * Sets the refnum_part
	 * @param refnum_part The refnum_part to set
	 */
	public void setRefnum_part(String refnum_part) {
		this.refnum_part = refnum_part;
	}

	/**
	 * Gets the ns_asie
	 * @return Returns a String
	 */
	public String getNs_asie() {
		return ns_asie;
	}
	/**
	 * Sets the ns_asie
	 * @param ns_asie The ns_asie to set
	 */
	public void setNs_asie(String ns_asie) {
		this.ns_asie = ns_asie;
	}

	/**
	 * Gets the cod_acto
	 * @return Returns a String
	 */
	public String getCod_acto() {
		return cod_acto;
	}
	/**
	 * Sets the cod_acto
	 * @param cod_acto The cod_acto to set
	 */
	public void setCod_acto(String cod_acto) {
		this.cod_acto = cod_acto;
	}

	/**
	 * Gets the aa_titu
	 * @return Returns a String
	 */
	public String getAa_titu() {
		return aa_titu;
	}
	/**
	 * Sets the aa_titu
	 * @param aa_titu The aa_titu to set
	 */
	public void setAa_titu(String aa_titu) {
		this.aa_titu = aa_titu;
	}

	/**
	 * Gets the num_titu
	 * @return Returns a String
	 */
	public String getNum_titu() {
		return num_titu;
	}
	/**
	 * Sets the num_titu
	 * @param num_titu The num_titu to set
	 */
	public void setNum_titu(String num_titu) {
		this.num_titu = num_titu;
	}

	/**
	 * Gets the num_pag
	 * @return Returns a String
	 */
	public String getNum_pag() {
		return num_pag;
	}
	/**
	 * Sets the num_pag
	 * @param num_pag The num_pag to set
	 */
	public void setNum_pag(String num_pag) {
		this.num_pag = num_pag;
	}

	/**
	 * Gets the tpo_pers
	 * @return Returns a String
	 */
	public String getTpo_pers() {
		return tpo_pers;
	}
	/**
	 * Sets the tpo_pers
	 * @param tpo_pers The tpo_pers to set
	 */
	public void setTpo_pers(String tpo_pers) {
		this.tpo_pers = tpo_pers;
	}

	/**
	 * Gets the ape_pat
	 * @return Returns a String
	 */
	public String getApe_pat() {
		return ape_pat;
	}
	/**
	 * Sets the ape_pat
	 * @param ape_pat The ape_pat to set
	 */
	public void setApe_pat(String ape_pat) {
		this.ape_pat = ape_pat;
	}

	/**
	 * Gets the ape_mat
	 * @return Returns a String
	 */
	public String getApe_mat() {
		return ape_mat;
	}
	/**
	 * Sets the ape_mat
	 * @param ape_mat The ape_mat to set
	 */
	public void setApe_mat(String ape_mat) {
		this.ape_mat = ape_mat;
	}

	/**
	 * Gets the nombres
	 * @return Returns a String
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * Sets the nombres
	 * @param nombres The nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * Gets the raz_soc
	 * @return Returns a String
	 */
	public String getRaz_soc() {
		return raz_soc;
	}
	/**
	 * Sets the raz_soc
	 * @param raz_soc The raz_soc to set
	 */
	public void setRaz_soc(String raz_soc) {
		this.raz_soc = raz_soc;
	}

	/**
	 * Gets the objeto_sol_id
	 * @return Returns a String
	 */
	public String getObjeto_sol_id() {
		return objeto_sol_id;
	}
	/**
	 * Sets the objeto_sol_id
	 * @param objeto_sol_id The objeto_sol_id to set
	 */
	public void setObjeto_sol_id(String objeto_sol_id) {
		this.objeto_sol_id = objeto_sol_id;
	}

	/**
	 * Gets the desc_certi
	 * @return Returns a String
	 */
	public String getDesc_certi() {
		return desc_certi;
	}
	/**
	 * Sets the desc_certi
	 * @param desc_certi The desc_certi to set
	 */
	public void setDesc_certi(String desc_certi) {
		this.desc_certi = desc_certi;
	}

	/**
	 * Gets the desc_regis
	 * @return Returns a String
	 */
	public String getDesc_regis() {
		return desc_regis;
	}
	/**
	 * Sets the desc_regis
	 * @param desc_regis The desc_regis to set
	 */
	public void setDesc_regis(String desc_regis) {
		this.desc_regis = desc_regis;
	}

	/**
	 * Gets the desc_tipop
	 * @return Returns a String
	 */
	public String getDesc_tipop() {
		return desc_tipop;
	}
	/**
	 * Sets the desc_tipop
	 * @param desc_tipop The desc_tipop to set
	 */
	public void setDesc_tipop(String desc_tipop) {
		this.desc_tipop = desc_tipop;
	}

	/**
	 * Gets the subTotal
	 * @return Returns a String
	 */
	public String getSubTotal() {
		return subTotal;
	}
	/**
	 * Sets the subTotal
	 * @param subTotal The subTotal to set
	 */
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
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
	 * Gets the libro
	 * @return Returns a String
	 */
	public String getLibro() {
		return libro;
	}
	/**
	 * Sets the libro
	 * @param libro The libro to set
	 */
	public void setLibro(String libro) {
		this.libro = libro;
	}

	/**
	 * Gets the certificado_desc
	 * @return Returns a String
	 */
	public String getCertificado_desc() {
		return certificado_desc;
	}
	/**
	 * Sets the certificado_desc
	 * @param certificado_desc The certificado_desc to set
	 */
	public void setCertificado_desc(String certificado_desc) {
		this.certificado_desc = certificado_desc;
	}




	/**
	 * Gets the ofic_reg_desc
	 * @return Returns a String
	 */
	public String getOfic_reg_desc() {
		return ofic_reg_desc;
	}
	/**
	 * Sets the ofic_reg_desc
	 * @param ofic_reg_desc The ofic_reg_desc to set
	 */
	public void setOfic_reg_desc(String ofic_reg_desc) {
		this.ofic_reg_desc = ofic_reg_desc;
	}

	/**
	 * Gets the area_reg_desc
	 * @return Returns a String
	 */
	public String getArea_reg_desc() {
		return area_reg_desc;
	}
	/**
	 * Sets the area_reg_desc
	 * @param area_reg_desc The area_reg_desc to set
	 */
	public void setArea_reg_desc(String area_reg_desc) {
		this.area_reg_desc = area_reg_desc;
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
	 * Gets the servicio_id
	 * @return Returns a String
	 */
	public String getServicio_id() {
		return servicio_id;
	}
	/**
	 * Sets the servicio_id
	 * @param servicio_id The servicio_id to set
	 */
	public void setServicio_id(String servicio_id) {
		this.servicio_id = servicio_id;
	}

	/**
	 * Gets the numPartida
	 * @return Returns a String
	 */
	public String getNumPartida() {
		return numPartida;
	}
	/**
	 * Sets the numPartida
	 * @param numPartida The numPartida to set
	 */
	public void setNumPartida(String numPartida) {
		this.numPartida = numPartida;
	}

	/**
	 * Gets the tpo_cert
	 * @return Returns a String
	 */
	public String getTpo_cert() {
		return tpo_cert;
	}
	/**
	 * Sets the tpo_cert
	 * @param tpo_cert The tpo_cert to set
	 */
	public void setTpo_cert(String tpo_cert) {
		this.tpo_cert = tpo_cert;
	}

	/**
	 * Gets the refnum
	 * @return Returns a String
	 */
	public String getRefnum() {
		return refnum;
	}
	/**
	 * Sets the refnum
	 * @param refnum The refnum to set
	 */
	public void setRefnum(String refnum) {
		this.refnum = refnum;
	}

	/**
	 * Gets the placa
	 * @return Returns a String
	 */
	public String getPlaca() {
		return placa;
	}
	/**
	 * Sets the placa
	 * @param placa The placa to set
	 */
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	/**
	 * Gets the ns_asie_placa
	 * @return Returns a String
	 */
	public String getNs_asie_placa() {
		return ns_asie_placa;
	}
	/**
	 * Sets the ns_asie_placa
	 * @param ns_asie_placa The ns_asie_placa to set
	 */
	public void setNs_asie_placa(String ns_asie_placa) {
		this.ns_asie_placa = ns_asie_placa;
	}

	/**
	 * Gets the desc_acto
	 * @return Returns a String
	 */
	public String getDesc_acto() {
		return desc_acto;
	}
	/**
	 * Sets the desc_acto
	 * @param desc_acto The desc_acto to set
	 */
	public void setDesc_acto(String desc_acto) {
		this.desc_acto = desc_acto;
	}

	/**
	 * Gets the ns_asiento
	 * @return Returns a String
	 */
	public String getNs_asiento() {
		return ns_asiento;
	}
	/**
	 * Sets the ns_asiento
	 * @param ns_asiento The ns_asiento to set
	 */
	public void setNs_asiento(String ns_asiento) {
		this.ns_asiento = ns_asiento;
	}

	/**
	 * Gets the numpag
	 * @return Returns a String
	 */
	public String getNumpag() {
		return numpag;
	}
	/**
	 * Sets the numpag
	 * @param numpag The numpag to set
	 */
	public void setNumpag(String numpag) {
		this.numpag = numpag;
	}

	/**
	 * Gets the ts_veri_manu
	 * @return Returns a String
	 */
	public String getTs_veri_manu() {
		return ts_veri_manu;
	}
	/**
	 * Sets the ts_veri_manu
	 * @param ts_veri_manu The ts_veri_manu to set
	 */
	public void setTs_veri_manu(String ts_veri_manu) {
		this.ts_veri_manu = ts_veri_manu;
	}

	/**
	 * Gets the cod_GLA
	 * @return Returns a String
	 */
	public String getCod_GLA() {
		return cod_GLA;
	}
	/**
	 * Sets the cod_GLA
	 * @param cod_GLA The cod_GLA to set
	 */
	public void setCod_GLA(String cod_GLA) {
		this.cod_GLA = cod_GLA;
	}
	/**
	 * @autor jascencio
	 * @fecha Aug 8, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the refNumParAnterior
	 */
	public String getRefNumParAnterior() {
		return refNumParAnterior;
	}
	/**
	 * @autor jascencio
	 * @fecha Aug 8, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param refNumParAnterior the refNumParAnterior to set
	 */
	public void setRefNumParAnterior(String refNumParAnterior) {
		this.refNumParAnterior = refNumParAnterior;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 10, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the flagEnvioDomicilio
	 */
	public String getFlagEnvioDomicilio() {
		return flagEnvioDomicilio;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 10, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param flagEnvioDomicilio the flagEnvioDomicilio to set
	 */
	public void setFlagEnvioDomicilio(String flagEnvioDomicilio) {
		this.flagEnvioDomicilio = flagEnvioDomicilio;
	}
	public String getDesc_GLA() {
		return desc_GLA;
	}
	public void setDesc_GLA(String desc_GLA) {
		this.desc_GLA = desc_GLA;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 31, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaInscripcionAsientoDesdeDate
	 */
	public Date getFechaInscripcionAsientoDesdeDate() {
		return fechaInscripcionAsientoDesdeDate;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 31, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaInscripcionAsientoDesdeDate the fechaInscripcionAsientoDesdeDate to set
	 */
	public void setFechaInscripcionAsientoDesdeDate(
			Date fechaInscripcionAsientoDesdeDate) {
		this.fechaInscripcionAsientoDesdeDate = fechaInscripcionAsientoDesdeDate;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 31, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaInscripcionAsientoHastaDate
	 */
	public Date getFechaInscripcionAsientoHastaDate() {
		return fechaInscripcionAsientoHastaDate;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 31, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaInscripcionAsientoHastaDate the fechaInscripcionAsientoHastaDate to set
	 */
	public void setFechaInscripcionAsientoHastaDate(
			Date fechaInscripcionAsientoHastaDate) {
		this.fechaInscripcionAsientoHastaDate = fechaInscripcionAsientoHastaDate;
	}
}


