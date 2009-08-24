package gob.pe.sunarp.extranet.transaction.bean;

public class LogAuditoriaBusqPartidaBean extends TransactionBean {

	private String partSeleccionado;// Es PN: Participante Natural, PJ: Participante Jurídico, TND: TipoNumeroDocumento, BI Bienes (placa u otrosDatos)
	private String nomApeRazSocPart;//Para PN: Se guardara el Nombre, para PJ: Se guardara la Razon Social
	private String[] numSedes;
	private String codAreaReg;
	private String tipoParticipacion;
	private String tipoPersPart;
	private String tipoBusq;
	
	/** inicio: jrosas 13-07-07   **/
	private String tipoDocumento;
	private String NumeroDocumento;
	private String numeroPlaca;
	private String otrosDatos;
	/** fin: jrosas 13-07-07   **/
	/*
	 * Inicio:jascencio:18/07/07
	 * CC:REGMOBCON-2006
	 */
	
	private String nombreBien;
	private String numeroMatricula;
	private String numeroSerie;
	
	/*
	 * Fin:jascencio
	 */
	private int tipoBusqPartida;//Se especifica el tipo de búsqueda de partida: EMB, MINERO, PREDIO, RAZSOCPJ, AEREO. Usar interface: TipoBusqPartida
	
	/**
	 * Gets the partSeleccionado
	 * @return Returns a String
	 */
	public String getPartSeleccionado() {
		return partSeleccionado;
	}
	/**
	 * Sets the partSeleccionado
	 * @param partSeleccionado The partSeleccionado to set
	 */
	public void setPartSeleccionado(String partSeleccionado) {
		this.partSeleccionado = partSeleccionado;
	}

	/**
	 * Gets the nomApeRazSocPart
	 * @return Returns a String
	 */
	public String getNomApeRazSocPart() {
		return nomApeRazSocPart;
	}
	/**
	 * Sets the nomApeRazSocPart
	 * @param nomApeRazSocPart The nomApeRazSocPart to set
	 */
	public void setNomApeRazSocPart(String nomApeRazSocPart) {
		this.nomApeRazSocPart = nomApeRazSocPart;
	}

	/**
	 * Gets the numSedes
	 * @return Returns a String[]
	 */
	public String[] getNumSedes() {
		return numSedes;
	}
	/**
	 * Sets the numSedes
	 * @param numSedes The numSedes to set
	 */
	public void setNumSedes(String[] numSedes) {
		this.numSedes = numSedes;
	}

	/**
	 * Gets the codAreaReg
	 * @return Returns a String
	 */
	public String getCodAreaReg() {
		return codAreaReg;
	}
	/**
	 * Sets the codAreaReg
	 * @param codAreaReg The codAreaReg to set
	 */
	public void setCodAreaReg(String codAreaReg) {
		this.codAreaReg = codAreaReg;
	}

	/**
	 * Gets the tipoParticipacion
	 * @return Returns a String
	 */
	public String getTipoParticipacion() {
		return tipoParticipacion;
	}
	/**
	 * Sets the tipoParticipacion
	 * @param tipoParticipacion The tipoParticipacion to set
	 */
	public void setTipoParticipacion(String tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}

	/**
	 * Gets the tipoPersPart
	 * @return Returns a String
	 */
	public String getTipoPersPart() {
		return tipoPersPart;
	}
	/**
	 * Sets the tipoPersPart
	 * @param tipoPersPart The tipoPersPart to set
	 */
	public void setTipoPersPart(String tipoPersPart) {
		this.tipoPersPart = tipoPersPart;
	}

	/**
	 * Gets the tipoBusqPartida
	 * @return Returns a int
	 */
	public int getTipoBusqPartida() {
		return tipoBusqPartida;
	}
	/**
	 * Sets the tipoBusqPartida
	 * @param tipoBusqPartida The tipoBusqPartida to set
	 */
	public void setTipoBusqPartida(int tipoBusqPartida) {
		this.tipoBusqPartida = tipoBusqPartida;
	}
	/**
	 * @return the tipoBusq
	 */
	public String getTipoBusq() {
		return tipoBusq;
	}
	/**
	 * @param tipoBusq the tipoBusq to set
	 */
	public void setTipoBusq(String tipoBusq) {
		this.tipoBusq = tipoBusq;
	}
	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return NumeroDocumento;
	}
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		NumeroDocumento = numeroDocumento;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the numeroPlaca
	 */
	public String getNumeroPlaca() {
		return numeroPlaca;
	}
	/**
	 * @param numeroPlaca the numeroPlaca to set
	 */
	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}
	/**
	 * @return the otrosDatos
	 */
	public String getOtrosDatos() {
		return otrosDatos;
	}
	/**
	 * @param otrosDatos the otrosDatos to set
	 */
	public void setOtrosDatos(String otrosDatos) {
		this.otrosDatos = otrosDatos;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 18, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the nombreBien
	 */
	public String getNombreBien() {
		return nombreBien;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 18, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param nombreBien the nombreBien to set
	 */
	public void setNombreBien(String nombreBien) {
		this.nombreBien = nombreBien;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 18, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroMatricula
	 */
	public String getNumeroMatricula() {
		return numeroMatricula;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 18, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroMatricula the numeroMatricula to set
	 */
	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 18, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the numeroSerie
	 */
	public String getNumeroSerie() {
		return numeroSerie;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 18, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param numeroSerie the numeroSerie to set
	 */
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

}

