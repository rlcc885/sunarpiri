package gob.pe.sunarp.extranet.afiliacion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class DatosUsuarioBean extends SunarpBean{
	private String apellidoPaterno = "";
	private String apellidoMaterno = "";
	private String nombre = "";
	private String tipoDocumento = "";
	private String numeroDocumento = "";
	private String pais = "";
	private String departamento = "";
	private String oficina = "";
	private String distrito = "";
	private String calle = "";
	private String codigoPostal = "";
	private String telefono = "";
	private String anexo = "";
	private String fax = "";
	private String email = "";
	private String usuario = "";
	private String password = "";
	private String confirmacionPassword = "";
	private String preguntaSecreta = "";
	private String respuestaSecreta = "";
	private boolean recibirMail = false;
	private String juris="";
	private String otro="";
	private String descripcionTipoDocumento="";

//-contrato	
	private String contratoNumero="";
	private String contratoVersion="";
	private String contratoVersionId="";
	
	private String provinciaId="";
	private String regPubId="";
//INSERTADO MANUEL	
	private String peJuriId="";
//INSERTADO	

	private String cuentaId="";
	private String lineaPrePagoId="";
		/**
	 * Gets the apellidoPaterno
	 * @return Returns a String
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	/**
	 * Sets the apellidoPaterno
	 * @param apellidoPaterno The apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * Gets the apellidoMaterno
	 * @return Returns a String
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	/**
	 * Sets the apellidoMaterno
	 * @param apellidoMaterno The apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * Gets the nombre
	 * @return Returns a String
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Sets the nombre
	 * @param nombre The nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the tipoDocumento
	 * @return Returns a String
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * Sets the tipoDocumento
	 * @param tipoDocumento The tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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
	 * Gets the pais
	 * @return Returns a String
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * Sets the pais
	 * @param pais The pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Gets the departamento
	 * @return Returns a String
	 */
	public String getDepartamento() {
		return departamento;
	}
	/**
	 * Sets the departamento
	 * @param departamento The departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * Gets the oficina
	 * @return Returns a String
	 */
	public String getOficina() {
		return oficina;
	}
	/**
	 * Sets the oficina
	 * @param oficina The oficina to set
	 */
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	/**
	 * Gets the distrito
	 * @return Returns a String
	 */
	public String getDistrito() {
		return distrito;
	}
	/**
	 * Sets the distrito
	 * @param distrito The distrito to set
	 */
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	/**
	 * Gets the calle
	 * @return Returns a String
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * Sets the calle
	 * @param calle The calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * Gets the codigoPostal
	 * @return Returns a String
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * Sets the codigoPostal
	 * @param codigoPostal The codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * Gets the telefono
	 * @return Returns a String
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * Sets the telefono
	 * @param telefono The telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Gets the fax
	 * @return Returns a String
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * Sets the fax
	 * @param fax The fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Gets the email
	 * @return Returns a String
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the email
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

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
	 * Gets the password
	 * @return Returns a String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the confirmacionPassword
	 * @return Returns a String
	 */
	public String getConfirmacionPassword() {
		return confirmacionPassword;
	}
	/**
	 * Sets the confirmacionPassword
	 * @param confirmacionPassword The confirmacionPassword to set
	 */
	public void setConfirmacionPassword(String confirmacionPassword) {
		this.confirmacionPassword = confirmacionPassword;
	}

	/**
	 * Gets the preguntaSecreta
	 * @return Returns a String
	 */
	public String getPreguntaSecreta() {
		return preguntaSecreta;
	}
	/**
	 * Sets the preguntaSecreta
	 * @param preguntaSecreta The preguntaSecreta to set
	 */
	public void setPreguntaSecreta(String preguntaSecreta) {
		this.preguntaSecreta = preguntaSecreta;
	}

	/**
	 * Gets the respuestaSecreta
	 * @return Returns a String
	 */
	public String getRespuestaSecreta() {
		return respuestaSecreta;
	}
	/**
	 * Sets the respuestaSecreta
	 * @param respuestaSecreta The respuestaSecreta to set
	 */
	public void setRespuestaSecreta(String respuestaSecreta) {
		this.respuestaSecreta = respuestaSecreta;
	}

	/**
	 * Gets the recibirMail
	 * @return Returns a boolean
	 */
	public boolean getRecibirMail() {
		return recibirMail;
	}
	/**
	 * Sets the recibirMail
	 * @param recibirMail The recibirMail to set
	 */
	public void setRecibirMail(boolean recibirMail) {
		this.recibirMail = recibirMail;
	}

	/**
	 * Gets the juris
	 * @return Returns a String
	 */
	public String getJuris() {
		return juris;
	}
	/**
	 * Sets the juris
	 * @param juris The juris to set
	 */
	public void setJuris(String juris) {
		this.juris = juris;
	}

	/**
	 * Gets the otro
	 * @return Returns a String
	 */
	public String getOtro() {
		return otro;
	}
	/**
	 * Sets the otro
	 * @param otro The otro to set
	 */
	public void setOtro(String otro) {
		this.otro = otro;
	}

	/**
	 * Gets the descripcionTipoDocumento
	 * @return Returns a String
	 */
	public String getDescripcionTipoDocumento() {
		return descripcionTipoDocumento;
	}
	/**
	 * Sets the descripcionTipoDocumento
	 * @param descripcionTipoDocumento The descripcionTipoDocumento to set
	 */
	public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
		this.descripcionTipoDocumento = descripcionTipoDocumento;
	}

	/**
	 * Gets the provinciaId
	 * @return Returns a String
	 */
	public String getProvinciaId() {
		return provinciaId;
	}
	/**
	 * Sets the provinciaId
	 * @param provinciaId The provinciaId to set
	 */
	public void setProvinciaId(String provinciaId) {
		this.provinciaId = provinciaId;
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
	 * Gets the peJuriId
	 * @return Returns a String
	 */
	public String getPeJuriId() {
		return peJuriId;
	}
	/**
	 * Sets the peJuriId
	 * @param peJuriId The peJuriId to set
	 */
	public void setPeJuriId(String peJuriId) {
		this.peJuriId = peJuriId;
	}

	/**
	 * Gets the contratoNumero
	 * @return Returns a String
	 */
	public String getContratoNumero() {
		return contratoNumero;
	}
	/**
	 * Sets the contratoNumero
	 * @param contratoNumero The contratoNumero to set
	 */
	public void setContratoNumero(String contratoNumero) {
		this.contratoNumero = contratoNumero;
	}

	/**
	 * Gets the contratoVersion
	 * @return Returns a String
	 */
	public String getContratoVersion() {
		return contratoVersion;
	}
	/**
	 * Sets the contratoVersion
	 * @param contratoVersion The contratoVersion to set
	 */
	public void setContratoVersion(String contratoVersion) {
		this.contratoVersion = contratoVersion;
	}

	/**
	 * Gets the contratoVersionId
	 * @return Returns a String
	 */
	public String getContratoVersionId() {
		return contratoVersionId;
	}
	/**
	 * Sets the contratoVersionId
	 * @param contratoVersionId The contratoVersionId to set
	 */
	public void setContratoVersionId(String contratoVersionId) {
		this.contratoVersionId = contratoVersionId;
	}

	/**
	 * Gets the lineaPrePagoId
	 * @return Returns a String
	 */
	public String getLineaPrePagoId() {
		return lineaPrePagoId;
	}
	/**
	 * Sets the lineaPrePagoId
	 * @param lineaPrePagoId The lineaPrePagoId to set
	 */
	public void setLineaPrePagoId(String lineaPrePagoId) {
		this.lineaPrePagoId = lineaPrePagoId;
	}

	/**
	 * Gets the cuentaId
	 * @return Returns a String
	 */
	public String getCuentaId() {
		return cuentaId;
	}
	/**
	 * Sets the cuentaId
	 * @param cuentaId The cuentaId to set
	 */
	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}

	/**
	 * Gets the anexo
	 * @return Returns a String
	 */
	public String getAnexo() {
		return anexo;
	}
	/**
	 * Sets the anexo
	 * @param anexo The anexo to set
	 */
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

}