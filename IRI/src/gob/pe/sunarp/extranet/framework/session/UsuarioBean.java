package gob.pe.sunarp.extranet.framework.session;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class UsuarioBean extends SunarpBean{

	private String userId;
	private String cuentaId;
	private String peNatuId;
	
	//private String exonPago;
	private boolean exonPago;
	private String tipoUser;
	//private String fgInterno;
	private boolean fgInterno;
	//private String fgIndividual;
	private boolean fgIndividual;
	//private String fgAdmin;
	private boolean fgAdmin;
	private long perfilId;
	private long nivelAccesoId;
	private String codOrg;
	private String linPrePago;
	private double saldo;

	private String personaId; 		// CUENTA_JURIS(cuentaId) : PERSONA_ID
	private String oficRegistralId; // CUENTA_JURIS(cuentaId) : OFIC_REG_ID
	private String regPublicoId;    // CUENTA_JURIS(cuentaId) : REG_PUB_ID
	private String jurisdiccionId;  // CUENTA_JURIS(cuentaId) : JURIS_ID
	private String num_contrato; 	// CONTRATO(cuentaId) : CONTRATO_ID
	private String userAdminOrg;	// Contiene la desripcion de la organizacion.Ej BCRE para Banco Credito
	
	private String lineaPrePagoOrganizacion; //anadido 14 setiembre
	private String cur;

	private String nombres;
	private String apePat;
	private String apeMat;
	private String razSocial;
	
	public String toString()
	{
		return new StringBuffer("user:").append(userId).append(" org:").append(codOrg).append(" ").append(regPublicoId).append(oficRegistralId).toString();
	}

	/**
	 * Gets the userId
	 * @return Returns a String
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Sets the userId
	 * @param userId The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * Gets the peNatuId
	 * @return Returns a String
	 */
	public String getPeNatuId() {
		return peNatuId;
	}
	/**
	 * Sets the peNatuId
	 * @param peNatuId The peNatuId to set
	 */
	public void setPeNatuId(String peNatuId) {
		this.peNatuId = peNatuId;
	}

	/**
	 * Gets the exonPago
	 * @return Returns a String
	 */
	public boolean getExonPago() {
		return exonPago;
	}
	/**
	 * Sets the exonPago
	 * @param exonPago The exonPago to set
	 */
	public void setExonPago(boolean exonPago) {
		this.exonPago = exonPago;
	}

	/**
	 * Gets the tipoUser
	 * @return Returns a String
	 */
	public String getTipoUser() {
		return tipoUser;
	}
	/**
	 * Sets the tipoUser
	 * @param tipoUser The tipoUser to set
	 */
	public void setTipoUser(String tipoUser) {
		this.tipoUser = tipoUser;
	}

	/**
	 * Gets the fgInterno
	 * @return Returns a String
	 */
	public boolean getFgInterno() {
		return fgInterno;
	}
	/**
	 * Sets the fgInterno
	 * @param fgInterno The fgInterno to set
	 */
	public void setFgInterno(boolean fgInterno) {
		this.fgInterno = fgInterno;
	}

	/**
	 * Gets the fgIndividual
	 * @return Returns a String
	 */
	public boolean getFgIndividual() {
		return fgIndividual;
	}
	/**
	 * Sets the fgIndividual
	 * @param fgIndividual The fgIndividual to set
	 */
	public void setFgIndividual(boolean fgIndividual) {
		this.fgIndividual = fgIndividual;
	}

	/**
	 * Gets the fgAdmin
	 * @return Returns a String
	 */
	public boolean getFgAdmin() {
		return fgAdmin;
	}
	/**
	 * Sets the fgAdmin
	 * @param fgAdmin The fgAdmin to set
	 */
	public void setFgAdmin(boolean fgAdmin) {
		this.fgAdmin = fgAdmin;
	}

	/**
	 * Gets the perfilId
	 * @return Returns a String
	 */
	public long getPerfilId() {
		return perfilId;
	}
	/**
	 * Sets the perfilId
	 * @param perfilId The perfilId to set
	 */
	public void setPerfilId(long perfilId) {
		this.perfilId = perfilId;
	}

	/**
	 * Gets the nivelAccesoId
	 * @return Returns a String
	 */
	public long getNivelAccesoId() {
		return nivelAccesoId;
	}
	/**
	 * Sets the nivelAccesoId
	 * @param nivelAccesoId The nivelAccesoId to set
	 */
	public void setNivelAccesoId(long nivelAccesoId) {
		this.nivelAccesoId = nivelAccesoId;
	}

	/**
	 * Gets the codOrg
	 * @return Returns a String
	 */
	public String getCodOrg() {
		return codOrg;
	}
	/**
	 * Sets the codOrg
	 * @param codOrg The codOrg to set
	 */
	public void setCodOrg(String codOrg) {
		this.codOrg = codOrg;
	}

	/**
	 * Gets the linPrePago
	 * @return Returns a String
	 */
	public String getLinPrePago() {
		return linPrePago;
	}
	/**
	 * Sets the linPrePago
	 * @param linPrePago The linPrePago to set
	 */
	public void setLinPrePago(String linPrePago) {
		this.linPrePago = linPrePago;
	}

	/**
	 * Gets the saldo
	 * @return Returns a double
	 */
	public double getSaldo() {
		return saldo;
	}
	/**
	 * Sets the saldo
	 * @param saldo The saldo to set
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	/**
	 * Gets the oficRegistralId
	 * @return Returns a String
	 */
	public String getOficRegistralId() {
		return oficRegistralId;
	}
	/**
	 * Sets the oficRegistralId
	 * @param oficRegistralId The oficRegistralId to set
	 */
	public void setOficRegistralId(String oficRegistralId) {
		this.oficRegistralId = oficRegistralId;
	}

	/**
	 * Gets the regPublicoId
	 * @return Returns a String
	 */
	public String getRegPublicoId() {
		return regPublicoId;
	}
	/**
	 * Sets the regPublicoId
	 * @param regPublicoId The regPublicoId to set
	 */
	public void setRegPublicoId(String regPublicoId) {
		this.regPublicoId = regPublicoId;
	}

	/**
	 * Gets the jurisdiccionId
	 * @return Returns a String
	 */
	public String getJurisdiccionId() {
		return jurisdiccionId;
	}
	/**
	 * Sets the jurisdiccionId
	 * @param jurisdiccionId The jurisdiccionId to set
	 */
	public void setJurisdiccionId(String jurisdiccionId) {
		this.jurisdiccionId = jurisdiccionId;
	}

	/**
	 * Gets the personaId
	 * @return Returns a String
	 */
	public String getPersonaId() {
		return personaId;
	}
	/**
	 * Sets the personaId
	 * @param personaId The personaId to set
	 */
	public void setPersonaId(String personaId) {
		this.personaId = personaId;
	}

	/**
	 * Gets the num_contrato
	 * @return Returns a String
	 */
	public String getNum_contrato() {
		return num_contrato;
	}
	/**
	 * Sets the num_contrato
	 * @param num_contrato The num_contrato to set
	 */
	public void setNum_contrato(String num_contrato) {
		this.num_contrato = num_contrato;
	}

	/**
	 * Gets the userAdminOrg
	 * @return Returns a String
	 */
	public String getUserAdminOrg() {
		return userAdminOrg;
	}
	/**
	 * Sets the userAdminOrg
	 * @param userAdminOrg The userAdminOrg to set
	 */
	public void setUserAdminOrg(String userAdminOrg) {
		this.userAdminOrg = userAdminOrg;
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
	 * Gets the lineaPrePagoOrganizacion
	 * @return Returns a String
	 */
	public String getLineaPrePagoOrganizacion() {
		return lineaPrePagoOrganizacion;
	}
	/**
	 * Sets the lineaPrePagoOrganizacion
	 * @param lineaPrePagoOrganizacion The lineaPrePagoOrganizacion to set
	 */
	public void setLineaPrePagoOrganizacion(String lineaPrePagoOrganizacion) {
		this.lineaPrePagoOrganizacion = lineaPrePagoOrganizacion;
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
	 * Gets the apePat
	 * @return Returns a String
	 */
	public String getApePat() {
		return apePat;
	}
	/**
	 * Sets the apePat
	 * @param apePat The apePat to set
	 */
	public void setApePat(String apePat) {
		this.apePat = apePat;
	}

	/**
	 * Gets the apeMat
	 * @return Returns a String
	 */
	public String getApeMat() {
		return apeMat;
	}
	/**
	 * Sets the apeMat
	 * @param apeMat The apeMat to set
	 */
	public void setApeMat(String apeMat) {
		this.apeMat = apeMat;
	}

	/**
	 * Gets the razSocial
	 * @return Returns a String
	 */
	public String getRazSocial() {
		return razSocial;
	}
	/**
	 * Sets the razSocial
	 * @param razSocial The razSocial to set
	 */
	public void setRazSocial(String razSocial) {
		this.razSocial = razSocial;
	}

}

