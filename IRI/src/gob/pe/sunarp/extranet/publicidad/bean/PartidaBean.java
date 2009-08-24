package gob.pe.sunarp.extranet.publicidad.bean;

import java.util.ArrayList;

import gob.pe.sunarp.extranet.common.SunarpBean;

/*
*Bean Partida
*@vesion 1
*/

public class PartidaBean extends SunarpBean{
	

	//-
	private String refNumPart = "&nbsp;";
	private String numPartida="&nbsp;";
	//-
	private String codArea="&nbsp;";
	private String areaRegistralDescripcion = "&nbsp;";
	
	private String codLibro="&nbsp;";
	private String libroDescripcion="&nbsp;";
	private String fichaId = "&nbsp;";	
	private String tomoId = "&nbsp;";
	private String fojaId = "&nbsp;";	
	//-	
	private String regPubDescripcion = "&nbsp;";
	private String oficRegDescripcion = "&nbsp;";
	//-
	private String participanteDescripcion = "&nbsp;";
	private String participacionDescripcion = "&nbsp;";
	private String participanteTipoDocumentoDescripcion="&nbsp;";
	private String participanteNumeroDocumento="&nbsp;";
	//-
	private String pj_razonSocial="&nbsp;";
	private String pj_ruc="&nbsp;";
	//-
	private String predioDireccion="&nbsp;";
	private String predioPropietario="&nbsp;";
	//-
	private String mineriaRazonSocial="&nbsp;";
	private String derechoMinero="&nbsp;";
	//-
	private String embarcacionMatricula="&nbsp;";
	private String embarcacionNombre="&nbsp;";
	//-
	private String buqueMatricula="&nbsp;";
	private String buqueNombre="&nbsp;";
	//-
	private String aeronaveMatricula="&nbsp;";
	private String aeronaveTipoTitular="&nbsp;";
	private String aeronaveTitular="&nbsp;";
	
	//-
	private String areaRegistralId="&nbsp;";
	private String regPubId="&nbsp;";
	private String oficRegId="&nbsp;";
	
	//-11oct2002
	//-campo extra para Titulo
	private boolean flagTitulo=false;
	private String  esquelaUrl;
	
	private String estadoInd="&nbsp;";
	
	
	//26Marz2003
	//-campo extra para Reg. Vehicular
	
	private String numeroPlaca = "&nbsp;";
	private String baja = "&nbsp;";
	
	//Número de Motor
	private String numeroMotor = "&nbsp;";
	private String numeroSerie = "&nbsp;";
	private String marca = "&nbsp;";
	private String modelo = "&nbsp;";
	
	// 2003-07-31
	private String estado="&nbsp;"; // estado de la partida
	/**
	 * inicio dbravo 21/06/2007
	 */
	private String numPartidaMigrado="";
	private String refNumPartMigrado = "&nbsp;";
	private String numeroPaginas = "&nbsp;";
	private String libroDescripcionMigrado="&nbsp;";
	private String fechaInscripcionAsiento="&nbsp;";
	/**
	 * fin dbravo 21/06/2007
	 */
	/** inicio: jrosas 16-07-07 */
	private String tipoPersona="&nbsp;";
	
	/**
	 * @return the tipoPersona
	 */
	public String getTipoPersona() {
		return tipoPersona;
	}
	/**
	 * @param tipoPersona the tipoPersona to set
	 */
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	/** fin: jrosas 16-07-07 */
	
	/**
	 * inicio ifigueroa 08/08/2007
	 */
	private ArrayList participantes;
	private String descEjecucion;
	

	public String getDescEjecucion() {
		return descEjecucion;
	}
	public void setDescEjecucion(String descEjecucion) {
		this.descEjecucion = descEjecucion;
	}
	/**
	 * fin ifigueroa 08/08/2007
	 */
	//***********************************SETTERS Y GETTERS************************
	/**
	 * Gets the refNumPart
	 * @return Returns a String
	 */
	public String getRefNumPart() {
		return refNumPart;
	}
	/**
	 * Sets the refNumPart
	 * @param refNumPart The refNumPart to set
	 */
	public void setRefNumPart(String refNumPart) {
		this.refNumPart = refNumPart;
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
	 * Gets the areaRegistralDescripcion
	 * @return Returns a String
	 */
	public String getAreaRegistralDescripcion() {
		return areaRegistralDescripcion;
	}
	/**
	 * Sets the areaRegistralDescripcion
	 * @param areaRegistralDescripcion The areaRegistralDescripcion to set
	 */
	public void setAreaRegistralDescripcion(String areaRegistralDescripcion) {
		this.areaRegistralDescripcion = areaRegistralDescripcion;
	}

	/**
	 * Gets the libroDescripcion
	 * @return Returns a String
	 */
	public String getLibroDescripcion() {
		return libroDescripcion;
	}
	/**
	 * Sets the libroDescripcion
	 * @param libroDescripcion The libroDescripcion to set
	 */
	public void setLibroDescripcion(String libroDescripcion) {
		this.libroDescripcion = libroDescripcion;
	}

	/**
	 * Gets the fichaId
	 * @return Returns a String
	 */
	public String getFichaId() {
		return fichaId;
	}
	/**
	 * Sets the fichaId
	 * @param fichaId The fichaId to set
	 */
	public void setFichaId(String fichaId) {
		this.fichaId = fichaId;
	}

	/**
	 * Gets the tomoId
	 * @return Returns a String
	 */
	public String getTomoId() {
		return tomoId;
	}
	/**
	 * Sets the tomoId
	 * @param tomoId The tomoId to set
	 */
	public void setTomoId(String tomoId) {
		this.tomoId = tomoId;
	}

	/**
	 * Gets the fojaId
	 * @return Returns a String
	 */
	public String getFojaId() {
		return fojaId;
	}
	/**
	 * Sets the fojaId
	 * @param fojaId The fojaId to set
	 */
	public void setFojaId(String fojaId) {
		this.fojaId = fojaId;
	}

	/**
	 * Gets the regPubDescripcion
	 * @return Returns a String
	 */
	public String getRegPubDescripcion() {
		return regPubDescripcion;
	}
	/**
	 * Sets the regPubDescripcion
	 * @param regPubDescripcion The regPubDescripcion to set
	 */
	public void setRegPubDescripcion(String regPubDescripcion) {
		this.regPubDescripcion = regPubDescripcion;
	}

	/**
	 * Gets the oficRegDescripcion
	 * @return Returns a String
	 */
	public String getOficRegDescripcion() {
		return oficRegDescripcion;
	}
	/**
	 * Sets the oficRegDescripcion
	 * @param oficRegDescripcion The oficRegDescripcion to set
	 */
	public void setOficRegDescripcion(String oficRegDescripcion) {
		this.oficRegDescripcion = oficRegDescripcion;
	}

	/**
	 * Gets the participanteDescripcion
	 * @return Returns a String
	 */
	public String getParticipanteDescripcion() {
		return participanteDescripcion;
	}
	/**
	 * Sets the participanteDescripcion
	 * @param participanteDescripcion The participanteDescripcion to set
	 */
	public void setParticipanteDescripcion(String participanteDescripcion) {
		this.participanteDescripcion = participanteDescripcion;
	}

	/**
	 * Gets the participacionDescripcion
	 * @return Returns a String
	 */
	public String getParticipacionDescripcion() {
		return participacionDescripcion;
	}
	/**
	 * Sets the participacionDescripcion
	 * @param participacionDescripcion The participacionDescripcion to set
	 */
	public void setParticipacionDescripcion(String participacionDescripcion) {
		this.participacionDescripcion = participacionDescripcion;
	}

	/**
	 * Gets the participanteTipoDocumentoDescripcion
	 * @return Returns a String
	 */
	public String getParticipanteTipoDocumentoDescripcion() {
		return participanteTipoDocumentoDescripcion;
	}
	/**
	 * Sets the participanteTipoDocumentoDescripcion
	 * @param participanteTipoDocumentoDescripcion The participanteTipoDocumentoDescripcion to set
	 */
	public void setParticipanteTipoDocumentoDescripcion(String participanteTipoDocumentoDescripcion) {
		this.participanteTipoDocumentoDescripcion =
			participanteTipoDocumentoDescripcion;
	}

	/**
	 * Gets the participanteNumeroDocumento
	 * @return Returns a String
	 */
	public String getParticipanteNumeroDocumento() {
		return participanteNumeroDocumento;
	}
	/**
	 * Sets the participanteNumeroDocumento
	 * @param participanteNumeroDocumento The participanteNumeroDocumento to set
	 */
	public void setParticipanteNumeroDocumento(String participanteNumeroDocumento) {
		this.participanteNumeroDocumento = participanteNumeroDocumento;
	}









	/**
	 * Gets the derechoMinero
	 * @return Returns a String
	 */
	public String getDerechoMinero() {
		return derechoMinero;
	}
	/**
	 * Sets the derechoMinero
	 * @param derechoMinero The derechoMinero to set
	 */
	public void setDerechoMinero(String derechoMinero) {
		this.derechoMinero = derechoMinero;
	}

	/**
	 * Gets the embarcacionMatricula
	 * @return Returns a String
	 */
	public String getEmbarcacionMatricula() {
		return embarcacionMatricula;
	}
	/**
	 * Sets the embarcacionMatricula
	 * @param embarcacionMatricula The embarcacionMatricula to set
	 */
	public void setEmbarcacionMatricula(String embarcacionMatricula) {
		this.embarcacionMatricula = embarcacionMatricula;
	}

	/**
	 * Gets the embarcacionNombre
	 * @return Returns a String
	 */
	public String getEmbarcacionNombre() {
		return embarcacionNombre;
	}
	/**
	 * Sets the embarcacionNombre
	 * @param embarcacionNombre The embarcacionNombre to set
	 */
	public void setEmbarcacionNombre(String embarcacionNombre) {
		this.embarcacionNombre = embarcacionNombre;
	}

	/**
	 * Gets the buqueMatricula
	 * @return Returns a String
	 */
	public String getBuqueMatricula() {
		return buqueMatricula;
	}
	/**
	 * Sets the buqueMatricula
	 * @param buqueMatricula The buqueMatricula to set
	 */
	public void setBuqueMatricula(String buqueMatricula) {
		this.buqueMatricula = buqueMatricula;
	}

	/**
	 * Gets the buqueNombre
	 * @return Returns a String
	 */
	public String getBuqueNombre() {
		return buqueNombre;
	}
	/**
	 * Sets the buqueNombre
	 * @param buqueNombre The buqueNombre to set
	 */
	public void setBuqueNombre(String buqueNombre) {
		this.buqueNombre = buqueNombre;
	}

	/**
	 * Gets the aeronaveMatricula
	 * @return Returns a String
	 */
	public String getAeronaveMatricula() {
		return aeronaveMatricula;
	}
	/**
	 * Sets the aeronaveMatricula
	 * @param aeronaveMatricula The aeronaveMatricula to set
	 */
	public void setAeronaveMatricula(String aeronaveMatricula) {
		this.aeronaveMatricula = aeronaveMatricula;
	}

	/**
	 * Gets the aeronaveTipoTitular
	 * @return Returns a String
	 */
	public String getAeronaveTipoTitular() {
		return aeronaveTipoTitular;
	}
	/**
	 * Sets the aeronaveTipoTitular
	 * @param aeronaveTipoTitular The aeronaveTipoTitular to set
	 */
	public void setAeronaveTipoTitular(String aeronaveTipoTitular) {
		this.aeronaveTipoTitular = aeronaveTipoTitular;
	}

	/**
	 * Gets the aeronaveTitular
	 * @return Returns a String
	 */
	public String getAeronaveTitular() {
		return aeronaveTitular;
	}
	/**
	 * Sets the aeronaveTitular
	 * @param aeronaveTitular The aeronaveTitular to set
	 */
	public void setAeronaveTitular(String aeronaveTitular) {
		this.aeronaveTitular = aeronaveTitular;
	}

	/**
	 * Gets the pj_razonSocial
	 * @return Returns a String
	 */
	public String getPj_razonSocial() {
		return pj_razonSocial;
	}
	/**
	 * Sets the pj_razonSocial
	 * @param pj_razonSocial The pj_razonSocial to set
	 */
	public void setPj_razonSocial(String pj_razonSocial) {
		this.pj_razonSocial = pj_razonSocial;
	}

	/**
	 * Gets the pj_ruc
	 * @return Returns a String
	 */
	public String getPj_ruc() {
		return pj_ruc;
	}
	/**
	 * Sets the pj_ruc
	 * @param pj_ruc The pj_ruc to set
	 */
	public void setPj_ruc(String pj_ruc) {
		this.pj_ruc = pj_ruc;
	}

	/**
	 * Gets the predioDireccion
	 * @return Returns a String
	 */
	public String getPredioDireccion() {
		return predioDireccion;
	}
	/**
	 * Sets the predioDireccion
	 * @param predioDireccion The predioDireccion to set
	 */
	public void setPredioDireccion(String predioDireccion) {
		this.predioDireccion = predioDireccion;
	}

	/**
	 * Gets the predioPropietario
	 * @return Returns a String
	 */
	public String getPredioPropietario() {
		return predioPropietario;
	}
	/**
	 * Sets the predioPropietario
	 * @param predioPropietario The predioPropietario to set
	 */
	public void setPredioPropietario(String predioPropietario) {
		this.predioPropietario = predioPropietario;
	}

	/**
	 * Gets the mineriaRazonSocial
	 * @return Returns a String
	 */
	public String getMineriaRazonSocial() {
		return mineriaRazonSocial;
	}
	/**
	 * Sets the mineriaRazonSocial
	 * @param mineriaRazonSocial The mineriaRazonSocial to set
	 */
	public void setMineriaRazonSocial(String mineriaRazonSocial) {
		this.mineriaRazonSocial = mineriaRazonSocial;
	}

	/**
	 * Gets the regPubId
	 * @return Returns a String
	 */
	public String getRegPubId() {
		return regPubId;
	}
	/**
	 * Sets the regPubId
	 * @param regPubId The regPubId to set
	 */
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}

	/**
	 * Gets the oficRegId
	 * @return Returns a String
	 */
	public String getOficRegId() {
		return oficRegId;
	}
	/**
	 * Sets the oficRegId
	 * @param oficRegId The oficRegId to set
	 */
	public void setOficRegId(String oficRegId) {
		this.oficRegId = oficRegId;
	}

	/**
	 * Gets the areaRegistralId
	 * @return Returns a String
	 */
	public String getAreaRegistralId() {
		return areaRegistralId;
	}
	/**
	 * Sets the areaRegistralId
	 * @param areaRegistralId The areaRegistralId to set
	 */
	public void setAreaRegistralId(String areaRegistralId) {
		this.areaRegistralId = areaRegistralId;
	}

	/**
	 * Gets the codArea
	 * @return Returns a String
	 */
	public String getCodArea() {
		return codArea;
	}
	/**
	 * Sets the codArea
	 * @param codArea The codArea to set
	 */
	public void setCodArea(String codArea) {
		this.codArea = codArea;
	}

	/**
	 * Gets the codLibro
	 * @return Returns a String
	 */
	public String getCodLibro() {
		return codLibro;
	}
	/**
	 * Sets the codLibro
	 * @param codLibro The codLibro to set
	 */
	public void setCodLibro(String codLibro) {
		this.codLibro = codLibro;
	}


	/**
	 * Gets the flagTitulo
	 * @return Returns a boolean
	 */
	public boolean getFlagTitulo() {
		return flagTitulo;
	}
	/**
	 * Sets the flagTitulo
	 * @param flagTitulo The flagTitulo to set
	 */
	public void setFlagTitulo(boolean flagTitulo) {
		this.flagTitulo = flagTitulo;
	}

	/**
	 * Gets the esquelaUrl
	 * @return Returns a String
	 */
	public String getEsquelaUrl() {
		return esquelaUrl;
	}
	/**
	 * Sets the esquelaUrl
	 * @param esquelaUrl The esquelaUrl to set
	 */
	public void setEsquelaUrl(String esquelaUrl) {
		this.esquelaUrl = esquelaUrl;
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
	 * Gets the numeroPlaca
	 * @return Returns a String
	 */
	public String getNumeroPlaca() {
		return numeroPlaca;
	}
	/**
	 * Sets the numeroPlaca
	 * @param numeroPlaca The numeroPlaca to set
	 */
	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}


	/**
	 * Gets the baja
	 * @return Returns a String
	 */
	public String getBaja() {
		return baja;
	}
	/**
	 * Sets the baja
	 * @param baja The baja to set
	 */
	public void setBaja(String baja) {
		this.baja = baja;
	}

	/**
	 * Gets the numeroMotor
	 * @return Returns a String
	 */
	public String getNumeroMotor() {
		return numeroMotor;
	}
	/**
	 * Sets the numeroMotor
	 * @param numeroMotor The numeroMotor to set
	 */
	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}

	/**
	 * Gets the numeroSerie
	 * @return Returns a String
	 */
	public String getNumeroSerie() {
		return numeroSerie;
	}
	/**
	 * Sets the numeroSerie
	 * @param numeroSerie The numeroSerie to set
	 */
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	/**
	 * Gets the marca
	 * @return Returns a String
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * Sets the marca
	 * @param marca The marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * Gets the modelo
	 * @return Returns a String
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * Sets the modelo
	 * @param modelo The modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * Gets the estadoInd
	 * @return Returns a String
	 */
	public String getEstadoInd() {
		return estadoInd;
	}
	/**
	 * Sets the estadoInd
	 * @param estadoInd The estadoInd to set
	 */
	public void setEstadoInd(String estadoInd) {
		this.estadoInd = estadoInd;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroPaginas
	 */
	public String getNumeroPaginas() {
		return numeroPaginas;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroPaginas the numeroPaginas to set
	 */
	public void setNumeroPaginas(String numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the refNumPartMigrado
	 */
	public String getRefNumPartMigrado() {
		return refNumPartMigrado;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param refNumPartMigrado the refNumPartMigrado to set
	 */
	public void setRefNumPartMigrado(String refNumPartMigrado) {
		this.refNumPartMigrado = refNumPartMigrado;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the libroDescripcionMigrado
	 */
	public String getLibroDescripcionMigrado() {
		return libroDescripcionMigrado;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param libroDescripcionMigrado the libroDescripcionMigrado to set
	 */
	public void setLibroDescripcionMigrado(String libroDescripcionMigrado) {
		this.libroDescripcionMigrado = libroDescripcionMigrado;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 25, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numPartidaMigrado
	 */
	public String getNumPartidaMigrado() {
		return numPartidaMigrado;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 25, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numPartidaMigrado the numPartidaMigrado to set
	 */
	public void setNumPartidaMigrado(String numPartidaMigrado) {
		this.numPartidaMigrado = numPartidaMigrado;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 6, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaInscripcionAsiento
	 */
	public String getFechaInscripcionAsiento() {
		return fechaInscripcionAsiento;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 6, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaInscripcionAsiento the fechaInscripcionAsiento to set
	 */
	public void setFechaInscripcionAsiento(String fechaInscripcionAsiento) {
		this.fechaInscripcionAsiento = fechaInscripcionAsiento;
	}
	public ArrayList getParticipantes() {
		return participantes;
	}
	public void setParticipantes(ArrayList participantes) {
		this.participantes = participantes;
	}

}