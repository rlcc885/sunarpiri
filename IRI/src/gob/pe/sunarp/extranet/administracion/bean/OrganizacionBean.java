package gob.pe.sunarp.extranet.administracion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class OrganizacionBean extends SunarpBean{
	
	private String personaIdOrganizacion;
	private String pe_juri_id;
	private String cur;
	private String razonSocial;
	private String ruc;
	private String siglas;
	private String prefijoCuenta;
	private String giroId;
	private String paisIdOrganizacion;
	private String departamentoIdOrganizacion;
	private String provinciaIdOrganizacion;
	private String otroDepartamentoOrganizacion;
	private String nomNumViaOrganizacion;
	private String codPostalOrganizacion;
	private String distOrganizacion;
	private String jurisID;	
	private String tipoOrg;
	private String exonPago;
	//datos del representante
	private String repres_id;
	private String peNatuIdRepresentante;
	private String personaIdRepresentante;
	private String nombresRepresentante;
	private String apellidoPaternoRepresentante;
	private String apellidoMaternoRepresentante;
	private String tipoDocRepresentante;
	private String numDocRepresentante;
	//datos del administrador
	private String peNatuIdAdministrador;
	private String personaIdAdministrador;
	private String cuentaAdministrador;
	private String tipoDocAdministrador;
	private String numDocIdenAdministrador;
	private String nombresAdministrador;
	private String apellidoPaternoAdministrador;
	private String apellidoMaternoAdministrador;
	private String paisAdministrador;
	private String departamentoAdministrador;
	private String provinciaAdministrador;
	private String lugarExtAdministrador;
	private String nomNumViaAdministrador;
	private String codPostalAdministrador;
	private String noDistAdministrador;
	private String emailAdministrador;
	private String faxAdministrador;
	private String telefonoAdministrador;
	private String prefijoAdministrador;
	private String claveAdministrador;
	private String respuestaSecretaAdministrador;
	private String preguntaSecretaAdministrador;

	/**
	 * Gets the razonSocial
	 * @return Returns a String
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * Sets the razonSocial
	 * @param razonSocial The razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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
	 * Gets the prefijoCuenta
	 * @return Returns a String
	 */
	public String getPrefijoCuenta() {
		return prefijoCuenta;
	}
	/**
	 * Sets the prefijoCuenta
	 * @param prefijoCuenta The prefijoCuenta to set
	 */
	public void setPrefijoCuenta(String prefijoCuenta) {
		this.prefijoCuenta = prefijoCuenta;
	}

	/**
	 * Gets the giroId
	 * @return Returns a String
	 */
	public String getGiroId() {
		return giroId;
	}
	/**
	 * Sets the giroId
	 * @param giroId The giroId to set
	 */
	public void setGiroId(String giroId) {
		this.giroId = giroId;
	}

	/**
	 * Gets the tipoDocRepresentante
	 * @return Returns a String
	 */
	public String getTipoDocRepresentante() {
		return tipoDocRepresentante;
	}
	/**
	 * Sets the tipoDocRepresentante
	 * @param tipoDocRepresentante The tipoDocRepresentante to set
	 */
	public void setTipoDocRepresentante(String tipoDocRepresentante) {
		this.tipoDocRepresentante = tipoDocRepresentante;
	}

	/**
	 * Gets the numDocRepresentante
	 * @return Returns a String
	 */
	public String getNumDocRepresentante() {
		return numDocRepresentante;
	}
	/**
	 * Sets the numDocRepresentante
	 * @param numDocRepresentante The numDocRepresentante to set
	 */
	public void setNumDocRepresentante(String numDocRepresentante) {
		this.numDocRepresentante = numDocRepresentante;
	}

	/**
	 * Gets the cuentaAdministrador
	 * @return Returns a String
	 */
	public String getCuentaAdministrador() {
		return cuentaAdministrador;
	}
	/**
	 * Sets the cuentaAdministrador
	 * @param cuentaAdministrador The cuentaAdministrador to set
	 */
	public void setCuentaAdministrador(String cuentaAdministrador) {
		this.cuentaAdministrador = cuentaAdministrador;
	}

	/**
	 * Gets the tipoDocAdministrador
	 * @return Returns a String
	 */
	public String getTipoDocAdministrador() {
		return tipoDocAdministrador;
	}
	/**
	 * Sets the tipoDocAdministrador
	 * @param tipoDocAdministrador The tipoDocAdministrador to set
	 */
	public void setTipoDocAdministrador(String tipoDocAdministrador) {
		this.tipoDocAdministrador = tipoDocAdministrador;
	}

	/**
	 * Gets the numDocIdenAdministrador
	 * @return Returns a String
	 */
	public String getNumDocIdenAdministrador() {
		return numDocIdenAdministrador;
	}
	/**
	 * Sets the numDocIdenAdministrador
	 * @param numDocIdenAdministrador The numDocIdenAdministrador to set
	 */
	public void setNumDocIdenAdministrador(String numDocIdenAdministrador) {
		this.numDocIdenAdministrador = numDocIdenAdministrador;
	}

	/**
	 * Gets the nombresAdministrador
	 * @return Returns a String
	 */
	public String getNombresAdministrador() {
		return nombresAdministrador;
	}
	/**
	 * Sets the nombresAdministrador
	 * @param nombresAdministrador The nombresAdministrador to set
	 */
	public void setNombresAdministrador(String nombresAdministrador) {
		this.nombresAdministrador = nombresAdministrador;
	}

	/**
	 * Gets the apellidoPaternoAdministrador
	 * @return Returns a String
	 */
	public String getApellidoPaternoAdministrador() {
		return apellidoPaternoAdministrador;
	}
	/**
	 * Sets the apellidoPaternoAdministrador
	 * @param apellidoPaternoAdministrador The apellidoPaternoAdministrador to set
	 */
	public void setApellidoPaternoAdministrador(String apellidoPaternoAdministrador) {
		this.apellidoPaternoAdministrador = apellidoPaternoAdministrador;
	}

	/**
	 * Gets the apellidoMaternoAdministrador
	 * @return Returns a String
	 */
	public String getApellidoMaternoAdministrador() {
		return apellidoMaternoAdministrador;
	}
	/**
	 * Sets the apellidoMaternoAdministrador
	 * @param apellidoMaternoAdministrador The apellidoMaternoAdministrador to set
	 */
	public void setApellidoMaternoAdministrador(String apellidoMaternoAdministrador) {
		this.apellidoMaternoAdministrador = apellidoMaternoAdministrador;
	}

	/**
	 * Gets the paisAdministrador
	 * @return Returns a String
	 */
	public String getPaisAdministrador() {
		return paisAdministrador;
	}
	/**
	 * Sets the paisAdministrador
	 * @param paisAdministrador The paisAdministrador to set
	 */
	public void setPaisAdministrador(String paisAdministrador) {
		this.paisAdministrador = paisAdministrador;
	}

	/**
	 * Gets the departamentoAdministrador
	 * @return Returns a String
	 */
	public String getDepartamentoAdministrador() {
		return departamentoAdministrador;
	}
	/**
	 * Sets the departamentoAdministrador
	 * @param departamentoAdministrador The departamentoAdministrador to set
	 */
	public void setDepartamentoAdministrador(String departamentoAdministrador) {
		this.departamentoAdministrador = departamentoAdministrador;
	}

	/**
	 * Gets the provinciaAdministrador
	 * @return Returns a String
	 */
	public String getProvinciaAdministrador() {
		return provinciaAdministrador;
	}
	/**
	 * Sets the provinciaAdministrador
	 * @param provinciaAdministrador The provinciaAdministrador to set
	 */
	public void setProvinciaAdministrador(String provinciaAdministrador) {
		this.provinciaAdministrador = provinciaAdministrador;
	}

	/**
	 * Gets the lugarExtAdministrador
	 * @return Returns a String
	 */
	public String getLugarExtAdministrador() {
		return lugarExtAdministrador;
	}
	/**
	 * Sets the lugarExtAdministrador
	 * @param lugarExtAdministrador The lugarExtAdministrador to set
	 */
	public void setLugarExtAdministrador(String lugarExtAdministrador) {
		this.lugarExtAdministrador = lugarExtAdministrador;
	}

	/**
	 * Gets the codPostalAdministrador
	 * @return Returns a String
	 */
	public String getCodPostalAdministrador() {
		return codPostalAdministrador;
	}
	/**
	 * Sets the codPostalAdministrador
	 * @param codPostalAdministrador The codPostalAdministrador to set
	 */
	public void setCodPostalAdministrador(String codPostalAdministrador) {
		this.codPostalAdministrador = codPostalAdministrador;
	}

	/**
	 * Gets the noDistAdministrador
	 * @return Returns a String
	 */
	public String getNoDistAdministrador() {
		return noDistAdministrador;
	}
	/**
	 * Sets the noDistAdministrador
	 * @param noDistAdministrador The noDistAdministrador to set
	 */
	public void setNoDistAdministrador(String noDistAdministrador) {
		this.noDistAdministrador = noDistAdministrador;
	}

	/**
	 * Gets the emailAdministrador
	 * @return Returns a String
	 */
	public String getEmailAdministrador() {
		return emailAdministrador;
	}
	/**
	 * Sets the emailAdministrador
	 * @param emailAdministrador The emailAdministrador to set
	 */
	public void setEmailAdministrador(String emailAdministrador) {
		this.emailAdministrador = emailAdministrador;
	}

	/**
	 * Gets the faxAdministrador
	 * @return Returns a String
	 */
	public String getFaxAdministrador() {
		return faxAdministrador;
	}
	/**
	 * Sets the faxAdministrador
	 * @param faxAdministrador The faxAdministrador to set
	 */
	public void setFaxAdministrador(String faxAdministrador) {
		this.faxAdministrador = faxAdministrador;
	}

	/**
	 * Gets the telefonoAdministrador
	 * @return Returns a String
	 */
	public String getTelefonoAdministrador() {
		return telefonoAdministrador;
	}
	/**
	 * Sets the telefonoAdministrador
	 * @param telefonoAdministrador The telefonoAdministrador to set
	 */
	public void setTelefonoAdministrador(String telefonoAdministrador) {
		this.telefonoAdministrador = telefonoAdministrador;
	}

	/**
	 * Gets the prefijoAdministrador
	 * @return Returns a String
	 */
	public String getPrefijoAdministrador() {
		return prefijoAdministrador;
	}
	/**
	 * Sets the prefijoAdministrador
	 * @param prefijoAdministrador The prefijoAdministrador to set
	 */
	public void setPrefijoAdministrador(String prefijoAdministrador) {
		this.prefijoAdministrador = prefijoAdministrador;
	}

	/**
	 * Gets the claveAdministrador
	 * @return Returns a String
	 */
	public String getClaveAdministrador() {
		return claveAdministrador;
	}
	/**
	 * Sets the claveAdministrador
	 * @param claveAdministrador The claveAdministrador to set
	 */
	public void setClaveAdministrador(String claveAdministrador) {
		this.claveAdministrador = claveAdministrador;
	}

	/**
	 * Gets the respuestaSecretaAdministrador
	 * @return Returns a String
	 */
	public String getRespuestaSecretaAdministrador() {
		return respuestaSecretaAdministrador;
	}
	/**
	 * Sets the respuestaSecretaAdministrador
	 * @param respuestaSecretaAdministrador The respuestaSecretaAdministrador to set
	 */
	public void setRespuestaSecretaAdministrador(String respuestaSecretaAdministrador) {
		this.respuestaSecretaAdministrador = respuestaSecretaAdministrador;
	}

	/**
	 * Gets the preguntaSecretaAdministrador
	 * @return Returns a String
	 */
	public String getPreguntaSecretaAdministrador() {
		return preguntaSecretaAdministrador;
	}
	/**
	 * Sets the preguntaSecretaAdministrador
	 * @param preguntaSecretaAdministrador The preguntaSecretaAdministrador to set
	 */
	public void setPreguntaSecretaAdministrador(String preguntaSecretaAdministrador) {
		this.preguntaSecretaAdministrador = preguntaSecretaAdministrador;
	}

	/**
	 * Gets the jurisID
	 * @return Returns a String
	 */
	public String getJurisID() {
		return jurisID;
	}
	/**
	 * Sets the jurisID
	 * @param jurisID The jurisID to set
	 */
	public void setJurisID(String jurisID) {
		this.jurisID = jurisID;
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
	 * Gets the nomNumViaAdministrador
	 * @return Returns a String
	 */
	public String getNomNumViaAdministrador() {
		return nomNumViaAdministrador;
	}
	/**
	 * Sets the nomNumViaAdministrador
	 * @param nomNumViaAdministrador The nomNumViaAdministrador to set
	 */
	public void setNomNumViaAdministrador(String nomNumViaAdministrador) {
		this.nomNumViaAdministrador = nomNumViaAdministrador;
	}

	/**
	 * Gets the pe_juri_id
	 * @return Returns a String
	 */
	public String getPe_juri_id() {
		return pe_juri_id;
	}
	/**
	 * Sets the pe_juri_id
	 * @param pe_juri_id The pe_juri_id to set
	 */
	public void setPe_juri_id(String pe_juri_id) {
		this.pe_juri_id = pe_juri_id;
	}

	/**
	 * Gets the cur
	 * @return Returns a String
	 */
	public String getCur() {
		return cur;
	}
	/**
	 * Sets the cur
	 * @param cur The cur to set
	 */
	public void setCur(String cur) {
		this.cur = cur;
	}

	/**
	 * Gets the exonPago
	 * @return Returns a String
	 */
	public String getExonPago() {
		return exonPago;
	}
	/**
	 * Sets the exonPago
	 * @param exonPago The exonPago to set
	 */
	public void setExonPago(String exonPago) {
		this.exonPago = exonPago;
	}

	/**
	 * Gets the nombresRepresentante
	 * @return Returns a String
	 */
	public String getNombresRepresentante() {
		return nombresRepresentante;
	}
	/**
	 * Sets the nombresRepresentante
	 * @param nombresRepresentante The nombresRepresentante to set
	 */
	public void setNombresRepresentante(String nombresRepresentante) {
		this.nombresRepresentante = nombresRepresentante;
	}

	/**
	 * Gets the apellidoPaternoRepresentante
	 * @return Returns a String
	 */
	public String getApellidoPaternoRepresentante() {
		return apellidoPaternoRepresentante;
	}
	/**
	 * Sets the apellidoPaternoRepresentante
	 * @param apellidoPaternoRepresentante The apellidoPaternoRepresentante to set
	 */
	public void setApellidoPaternoRepresentante(String apellidoPaternoRepresentante) {
		this.apellidoPaternoRepresentante = apellidoPaternoRepresentante;
	}

	/**
	 * Gets the apellidoMaternoRepresentante
	 * @return Returns a String
	 */
	public String getApellidoMaternoRepresentante() {
		return apellidoMaternoRepresentante;
	}
	/**
	 * Sets the apellidoMaternoRepresentante
	 * @param apellidoMaternoRepresentante The apellidoMaternoRepresentante to set
	 */
	public void setApellidoMaternoRepresentante(String apellidoMaternoRepresentante) {
		this.apellidoMaternoRepresentante = apellidoMaternoRepresentante;
	}

	/**
	 * Gets the repres_id
	 * @return Returns a String
	 */
	public String getRepres_id() {
		return repres_id;
	}

	/**
	 * Sets the repres_id
	 * @param repres_id The repres_id to set
	 */
	public void setRepres_id(String repres_id) {
		this.repres_id = repres_id;
	}
	/**
	 * Gets the peNatuIdRepresentante
	 * @return Returns a String
	 */
	public String getPeNatuIdRepresentante() {
		return peNatuIdRepresentante;
	}
	/**
	 * Sets the peNatuIdRepresentante
	 * @param peNatuIdRepresentante The peNatuIdRepresentante to set
	 */
	public void setPeNatuIdRepresentante(String peNatuIdRepresentante) {
		this.peNatuIdRepresentante = peNatuIdRepresentante;
	}

	/**
	 * Gets the personaIdRepresentante
	 * @return Returns a String
	 */
	public String getPersonaIdRepresentante() {
		return personaIdRepresentante;
	}
	/**
	 * Sets the personaIdRepresentante
	 * @param personaIdRepresentante The personaIdRepresentante to set
	 */
	public void setPersonaIdRepresentante(String personaIdRepresentante) {
		this.personaIdRepresentante = personaIdRepresentante;
	}

	/**
	 * Gets the peNatuIdAdministrador
	 * @return Returns a String
	 */
	public String getPeNatuIdAdministrador() {
		return peNatuIdAdministrador;
	}
	/**
	 * Sets the peNatuIdAdministrador
	 * @param peNatuIdAdministrador The peNatuIdAdministrador to set
	 */
	public void setPeNatuIdAdministrador(String peNatuIdAdministrador) {
		this.peNatuIdAdministrador = peNatuIdAdministrador;
	}

	/**
	 * Gets the personaIdAdministrador
	 * @return Returns a String
	 */
	public String getPersonaIdAdministrador() {
		return personaIdAdministrador;
	}
	/**
	 * Sets the personaIdAdministrador
	 * @param personaIdAdministrador The personaIdAdministrador to set
	 */
	public void setPersonaIdAdministrador(String personaIdAdministrador) {
		this.personaIdAdministrador = personaIdAdministrador;
	}

	/**
	 * Gets the personaIdOrganizacion
	 * @return Returns a String
	 */
	public String getPersonaIdOrganizacion() {
		return personaIdOrganizacion;
	}
	/**
	 * Sets the personaIdOrganizacion
	 * @param personaIdOrganizacion The personaIdOrganizacion to set
	 */
	public void setPersonaIdOrganizacion(String personaIdOrganizacion) {
		this.personaIdOrganizacion = personaIdOrganizacion;
	}

	/**
	 * Gets the paisIdOrganizacion
	 * @return Returns a String
	 */
	public String getPaisIdOrganizacion() {
		return paisIdOrganizacion;
	}
	/**
	 * Sets the paisIdOrganizacion
	 * @param paisIdOrganizacion The paisIdOrganizacion to set
	 */
	public void setPaisIdOrganizacion(String paisIdOrganizacion) {
		this.paisIdOrganizacion = paisIdOrganizacion;
	}

	/**
	 * Gets the departamentoIdOrganizacion
	 * @return Returns a String
	 */
	public String getDepartamentoIdOrganizacion() {
		return departamentoIdOrganizacion;
	}
	/**
	 * Sets the departamentoIdOrganizacion
	 * @param departamentoIdOrganizacion The departamentoIdOrganizacion to set
	 */
	public void setDepartamentoIdOrganizacion(String departamentoIdOrganizacion) {
		this.departamentoIdOrganizacion = departamentoIdOrganizacion;
	}

	/**
	 * Gets the provinciaIdOrganizacion
	 * @return Returns a String
	 */
	public String getProvinciaIdOrganizacion() {
		return provinciaIdOrganizacion;
	}
	/**
	 * Sets the provinciaIdOrganizacion
	 * @param provinciaIdOrganizacion The provinciaIdOrganizacion to set
	 */
	public void setProvinciaIdOrganizacion(String provinciaIdOrganizacion) {
		this.provinciaIdOrganizacion = provinciaIdOrganizacion;
	}
	/**
	 * Gets the nomNumViaOrganizacion
	 * @return Returns a String
	 */
	public String getNomNumViaOrganizacion() {
		return nomNumViaOrganizacion;
	}
	/**
	 * Sets the nomNumViaOrganizacion
	 * @param nomNumViaOrganizacion The nomNumViaOrganizacion to set
	 */
	public void setNomNumViaOrganizacion(String nomNumViaOrganizacion) {
		this.nomNumViaOrganizacion = nomNumViaOrganizacion;
	}

	/**
	 * Gets the codPostalOrganizacion
	 * @return Returns a String
	 */
	public String getCodPostalOrganizacion() {
		return codPostalOrganizacion;
	}
	/**
	 * Sets the codPostalOrganizacion
	 * @param codPostalOrganizacion The codPostalOrganizacion to set
	 */
	public void setCodPostalOrganizacion(String codPostalOrganizacion) {
		this.codPostalOrganizacion = codPostalOrganizacion;
	}

	/**
	 * Gets the distOrganizacion
	 * @return Returns a String
	 */

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
	 * Gets the distOrganizacion
	 * @return Returns a String
	 */
	public String getDistOrganizacion() {
		return distOrganizacion;
	}
	/**
	 * Sets the distOrganizacion
	 * @param distOrganizacion The distOrganizacion to set
	 */
	public void setDistOrganizacion(String distOrganizacion) {
		this.distOrganizacion = distOrganizacion;
	}

	/**
	 * Gets the otroDepartamentoOrganizacion
	 * @return Returns a String
	 */
	public String getOtroDepartamentoOrganizacion() {
		return otroDepartamentoOrganizacion;
	}
	/**
	 * Sets the otroDepartamentoOrganizacion
	 * @param otroDepartamentoOrganizacion The otroDepartamentoOrganizacion to set
	 */
	public void setOtroDepartamentoOrganizacion(String otroDepartamentoOrganizacion) {
		this.otroDepartamentoOrganizacion = otroDepartamentoOrganizacion;
	}

}

