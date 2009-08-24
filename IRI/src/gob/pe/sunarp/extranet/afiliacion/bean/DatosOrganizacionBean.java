package gob.pe.sunarp.extranet.afiliacion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class DatosOrganizacionBean extends SunarpBean{
	
	//datos organizacion
private String orgRazon="";
private String orgRuc="";
private String orgGiro="";
private String orgPais="";
private String orgLug="";
private String orgDpto="";
private String orgProvincia="";
private String orgDistrito="";
private String orgVia="";
private String orgTelefono="";
private String orgFax="";
private String orgEmail="";
//-extra
private String orgOficinaId="";
private String orgJuris="";
private String orgRegPubId="";

//datos representante de la organizacion
private String repApePat="";
private String repApeMat="";
private String repNombre="";
private String repTipoDocumento="";
private String repNumeroDocumento="";
private String repDescripcionTipoDocumento="";


//datos administrador 
private String admApePat="";
private String admApeMat="";
private String admNombre="";
private String admEmail="";
private String admTipoDocumento="";
private String admNumeroDocumento="";
private String admPais="";
private String admDpto="";
private String admOtro="";
private String admProvincia="";
private String admDistrito="";
private String admVia="";
private String admCodPostal="";
private String admTelefono="";
private String admAnexo="";
private String admFax="";
private String admPrefijo="";
private String admPassword="";
private String admConfirmaPassword="";
private String admTipoPregunta="";
private String admRespuesta="";
private boolean admQuiero=false;

//-extra
private String usuarioId="";
private String admOficinaId="";
private String admJuris="";
private String admRegPubId="";
private String admDescripcionTipoDocumento="";

//-adicional
	private String contratoNumero="";
	private String contratoVersion="";
	private String contratoVersionId="";
	
//INSERTAR

	/**
	 * Gets the orgRazon
	 * @return Returns a String
	 */
	public String getOrgRazon() {
		return orgRazon;
	}
	/**
	 * Sets the orgRazon
	 * @param orgRazon The orgRazon to set
	 */
	public void setOrgRazon(String orgRazon) {
		this.orgRazon = orgRazon;
	}

	/**
	 * Gets the orgRuc
	 * @return Returns a String
	 */
	public String getOrgRuc() {
		return orgRuc;
	}
	/**
	 * Sets the orgRuc
	 * @param orgRuc The orgRuc to set
	 */
	public void setOrgRuc(String orgRuc) {
		this.orgRuc = orgRuc;
	}

	/**
	 * Gets the orgGiro
	 * @return Returns a String
	 */
	public String getOrgGiro() {
		return orgGiro;
	}
	/**
	 * Sets the orgGiro
	 * @param orgGiro The orgGiro to set
	 */
	public void setOrgGiro(String orgGiro) {
		this.orgGiro = orgGiro;
	}

	/**
	 * Gets the orgPais
	 * @return Returns a String
	 */
	public String getOrgPais() {
		return orgPais;
	}
	/**
	 * Sets the orgPais
	 * @param orgPais The orgPais to set
	 */
	public void setOrgPais(String orgPais) {
		this.orgPais = orgPais;
	}

	/**
	 * Gets the orgLug
	 * @return Returns a String
	 */
	public String getOrgLug() {
		return orgLug;
	}
	/**
	 * Sets the orgLug
	 * @param orgLug The orgLug to set
	 */
	public void setOrgLug(String orgLug) {
		this.orgLug = orgLug;
	}

	/**
	 * Gets the orgDpto
	 * @return Returns a String
	 */
	public String getOrgDpto() {
		return orgDpto;
	}
	/**
	 * Sets the orgDpto
	 * @param orgDpto The orgDpto to set
	 */
	public void setOrgDpto(String orgDpto) {
		this.orgDpto = orgDpto;
	}

	/**
	 * Gets the orgProvincia
	 * @return Returns a String
	 */
	public String getOrgProvincia() {
		return orgProvincia;
	}
	/**
	 * Sets the orgProvincia
	 * @param orgProvincia The orgProvincia to set
	 */
	public void setOrgProvincia(String orgProvincia) {
		this.orgProvincia = orgProvincia;
	}

	/**
	 * Gets the orgDistrito
	 * @return Returns a String
	 */
	public String getOrgDistrito() {
		return orgDistrito;
	}
	/**
	 * Sets the orgDistrito
	 * @param orgDistrito The orgDistrito to set
	 */
	public void setOrgDistrito(String orgDistrito) {
		this.orgDistrito = orgDistrito;
	}

	/**
	 * Gets the orgVia
	 * @return Returns a String
	 */
	public String getOrgVia() {
		return orgVia;
	}
	/**
	 * Sets the orgVia
	 * @param orgVia The orgVia to set
	 */
	public void setOrgVia(String orgVia) {
		this.orgVia = orgVia;
	}

	/**
	 * Gets the orgTelefono
	 * @return Returns a String
	 */
	public String getOrgTelefono() {
		return orgTelefono;
	}
	/**
	 * Sets the orgTelefono
	 * @param orgTelefono The orgTelefono to set
	 */
	public void setOrgTelefono(String orgTelefono) {
		this.orgTelefono = orgTelefono;
	}

	/**
	 * Gets the orgFax
	 * @return Returns a String
	 */
	public String getOrgFax() {
		return orgFax;
	}
	/**
	 * Sets the orgFax
	 * @param orgFax The orgFax to set
	 */
	public void setOrgFax(String orgFax) {
		this.orgFax = orgFax;
	}

	/**
	 * Gets the orgEmail
	 * @return Returns a String
	 */
	public String getOrgEmail() {
		return orgEmail;
	}
	/**
	 * Sets the orgEmail
	 * @param orgEmail The orgEmail to set
	 */
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	/**
	 * Gets the repApePat
	 * @return Returns a String
	 */
	public String getRepApePat() {
		return repApePat;
	}
	/**
	 * Sets the repApePat
	 * @param repApePat The repApePat to set
	 */
	public void setRepApePat(String repApePat) {
		this.repApePat = repApePat;
	}

	/**
	 * Gets the repApeMat
	 * @return Returns a String
	 */
	public String getRepApeMat() {
		return repApeMat;
	}
	/**
	 * Sets the repApeMat
	 * @param repApeMat The repApeMat to set
	 */
	public void setRepApeMat(String repApeMat) {
		this.repApeMat = repApeMat;
	}

	/**
	 * Gets the repNombre
	 * @return Returns a String
	 */
	public String getRepNombre() {
		return repNombre;
	}
	/**
	 * Sets the repNombre
	 * @param repNombre The repNombre to set
	 */
	public void setRepNombre(String repNombre) {
		this.repNombre = repNombre;
	}

	/**
	 * Gets the repTipoDocumento
	 * @return Returns a String
	 */
	public String getRepTipoDocumento() {
		return repTipoDocumento;
	}
	/**
	 * Sets the repTipoDocumento
	 * @param repTipoDocumento The repTipoDocumento to set
	 */
	public void setRepTipoDocumento(String repTipoDocumento) {
		this.repTipoDocumento = repTipoDocumento;
	}

	/**
	 * Gets the repNumeroDocumento
	 * @return Returns a String
	 */
	public String getRepNumeroDocumento() {
		return repNumeroDocumento;
	}
	/**
	 * Sets the repNumeroDocumento
	 * @param repNumeroDocumento The repNumeroDocumento to set
	 */
	public void setRepNumeroDocumento(String repNumeroDocumento) {
		this.repNumeroDocumento = repNumeroDocumento;
	}

	/**
	 * Gets the admApePat
	 * @return Returns a String
	 */
	public String getAdmApePat() {
		return admApePat;
	}
	/**
	 * Sets the admApePat
	 * @param admApePat The admApePat to set
	 */
	public void setAdmApePat(String admApePat) {
		this.admApePat = admApePat;
	}

	/**
	 * Gets the admApeMat
	 * @return Returns a String
	 */
	public String getAdmApeMat() {
		return admApeMat;
	}
	/**
	 * Sets the admApeMat
	 * @param admApeMat The admApeMat to set
	 */
	public void setAdmApeMat(String admApeMat) {
		this.admApeMat = admApeMat;
	}

	/**
	 * Gets the admNombre
	 * @return Returns a String
	 */
	public String getAdmNombre() {
		return admNombre;
	}
	/**
	 * Sets the admNombre
	 * @param admNombre The admNombre to set
	 */
	public void setAdmNombre(String admNombre) {
		this.admNombre = admNombre;
	}

	/**
	 * Gets the admEmail
	 * @return Returns a String
	 */
	public String getAdmEmail() {
		return admEmail;
	}
	/**
	 * Sets the admEmail
	 * @param admEmail The admEmail to set
	 */
	public void setAdmEmail(String admEmail) {
		this.admEmail = admEmail;
	}

	/**
	 * Gets the admTipoDocumento
	 * @return Returns a String
	 */
	public String getAdmTipoDocumento() {
		return admTipoDocumento;
	}
	/**
	 * Sets the admTipoDocumento
	 * @param admTipoDocumento The admTipoDocumento to set
	 */
	public void setAdmTipoDocumento(String admTipoDocumento) {
		this.admTipoDocumento = admTipoDocumento;
	}

	/**
	 * Gets the admNumeroDocumento
	 * @return Returns a String
	 */
	public String getAdmNumeroDocumento() {
		return admNumeroDocumento;
	}
	/**
	 * Sets the admNumeroDocumento
	 * @param admNumeroDocumento The admNumeroDocumento to set
	 */
	public void setAdmNumeroDocumento(String admNumeroDocumento) {
		this.admNumeroDocumento = admNumeroDocumento;
	}

	/**
	 * Gets the admPais
	 * @return Returns a String
	 */
	public String getAdmPais() {
		return admPais;
	}
	/**
	 * Sets the admPais
	 * @param admPais The admPais to set
	 */
	public void setAdmPais(String admPais) {
		this.admPais = admPais;
	}

	/**
	 * Gets the admDpto
	 * @return Returns a String
	 */
	public String getAdmDpto() {
		return admDpto;
	}
	/**
	 * Sets the admDpto
	 * @param admDpto The admDpto to set
	 */
	public void setAdmDpto(String admDpto) {
		this.admDpto = admDpto;
	}

	/**
	 * Gets the admOtro
	 * @return Returns a String
	 */
	public String getAdmOtro() {
		return admOtro;
	}
	/**
	 * Sets the admOtro
	 * @param admOtro The admOtro to set
	 */
	public void setAdmOtro(String admOtro) {
		this.admOtro = admOtro;
	}

	/**
	 * Gets the admProvincia
	 * @return Returns a String
	 */
	public String getAdmProvincia() {
		return admProvincia;
	}
	/**
	 * Sets the admProvincia
	 * @param admProvincia The admProvincia to set
	 */
	public void setAdmProvincia(String admProvincia) {
		this.admProvincia = admProvincia;
	}

	/**
	 * Gets the admDistrito
	 * @return Returns a String
	 */
	public String getAdmDistrito() {
		return admDistrito;
	}
	/**
	 * Sets the admDistrito
	 * @param admDistrito The admDistrito to set
	 */
	public void setAdmDistrito(String admDistrito) {
		this.admDistrito = admDistrito;
	}

	/**
	 * Gets the admVia
	 * @return Returns a String
	 */
	public String getAdmVia() {
		return admVia;
	}
	/**
	 * Sets the admVia
	 * @param admVia The admVia to set
	 */
	public void setAdmVia(String admVia) {
		this.admVia = admVia;
	}

	/**
	 * Gets the admCodPostal
	 * @return Returns a String
	 */
	public String getAdmCodPostal() {
		return admCodPostal;
	}
	/**
	 * Sets the admCodPostal
	 * @param admCodPostal The admCodPostal to set
	 */
	public void setAdmCodPostal(String admCodPostal) {
		this.admCodPostal = admCodPostal;
	}

	/**
	 * Gets the admTelefono
	 * @return Returns a String
	 */
	public String getAdmTelefono() {
		return admTelefono;
	}
	/**
	 * Sets the admTelefono
	 * @param admTelefono The admTelefono to set
	 */
	public void setAdmTelefono(String admTelefono) {
		this.admTelefono = admTelefono;
	}

	/**
	 * Gets the admFax
	 * @return Returns a String
	 */
	public String getAdmFax() {
		return admFax;
	}
	/**
	 * Sets the admFax
	 * @param admFax The admFax to set
	 */
	public void setAdmFax(String admFax) {
		this.admFax = admFax;
	}

	/**
	 * Gets the admPrefijo
	 * @return Returns a String
	 */
	public String getAdmPrefijo() {
		return admPrefijo;
	}
	/**
	 * Sets the admPrefijo
	 * @param admPrefijo The admPrefijo to set
	 */
	public void setAdmPrefijo(String admPrefijo) {
		this.admPrefijo = admPrefijo;
	}

	/**
	 * Gets the admPassword
	 * @return Returns a String
	 */
	public String getAdmPassword() {
		return admPassword;
	}
	/**
	 * Sets the admPassword
	 * @param admPassword The admPassword to set
	 */
	public void setAdmPassword(String admPassword) {
		this.admPassword = admPassword;
	}

	/**
	 * Gets the admConfirmaPassword
	 * @return Returns a String
	 */
	public String getAdmConfirmaPassword() {
		return admConfirmaPassword;
	}
	/**
	 * Sets the admConfirmaPassword
	 * @param admConfirmaPassword The admConfirmaPassword to set
	 */
	public void setAdmConfirmaPassword(String admConfirmaPassword) {
		this.admConfirmaPassword = admConfirmaPassword;
	}

	/**
	 * Gets the admTipoPregunta
	 * @return Returns a String
	 */
	public String getAdmTipoPregunta() {
		return admTipoPregunta;
	}
	/**
	 * Sets the admTipoPregunta
	 * @param admTipoPregunta The admTipoPregunta to set
	 */
	public void setAdmTipoPregunta(String admTipoPregunta) {
		this.admTipoPregunta = admTipoPregunta;
	}

	/**
	 * Gets the admRespuesta
	 * @return Returns a String
	 */
	public String getAdmRespuesta() {
		return admRespuesta;
	}
	/**
	 * Sets the admRespuesta
	 * @param admRespuesta The admRespuesta to set
	 */
	public void setAdmRespuesta(String admRespuesta) {
		this.admRespuesta = admRespuesta;
	}




	/**
	 * Gets the admQuiero
	 * @return Returns a boolean
	 */
	public boolean getAdmQuiero() {
		return admQuiero;
	}
	/**
	 * Sets the admQuiero
	 * @param admQuiero The admQuiero to set
	 */
	public void setAdmQuiero(boolean admQuiero) {
		this.admQuiero = admQuiero;
	}

	/**
	 * Gets the orgJuris
	 * @return Returns a String
	 */
	public String getOrgJuris() {
		return orgJuris;
	}
	/**
	 * Sets the orgJuris
	 * @param orgJuris The orgJuris to set
	 */
	public void setOrgJuris(String orgJuris) {
		this.orgJuris = orgJuris;
	}

	/**
	 * Gets the orgRegPubId
	 * @return Returns a String
	 */
	public String getOrgRegPubId() {
		return orgRegPubId;
	}
	/**
	 * Sets the orgRegPubId
	 * @param orgRegPubId The orgRegPubId to set
	 */
	public void setOrgRegPubId(String orgRegPubId) {
		this.orgRegPubId = orgRegPubId;
	}

	/**
	 * Gets the admJuris
	 * @return Returns a String
	 */
	public String getAdmJuris() {
		return admJuris;
	}
	/**
	 * Sets the admJuris
	 * @param admJuris The admJuris to set
	 */
	public void setAdmJuris(String admJuris) {
		this.admJuris = admJuris;
	}

	/**
	 * Gets the admRegPubId
	 * @return Returns a String
	 */
	public String getAdmRegPubId() {
		return admRegPubId;
	}
	/**
	 * Sets the admRegPubId
	 * @param admRegPubId The admRegPubId to set
	 */
	public void setAdmRegPubId(String admRegPubId) {
		this.admRegPubId = admRegPubId;
	}

	/**
	 * Gets the admOficinaId
	 * @return Returns a String
	 */
	public String getAdmOficinaId() {
		return admOficinaId;
	}
	/**
	 * Sets the admOficinaId
	 * @param admOficinaId The admOficinaId to set
	 */
	public void setAdmOficinaId(String admOficinaId) {
		this.admOficinaId = admOficinaId;
	}

	/**
	 * Gets the orgOficinaId
	 * @return Returns a String
	 */
	public String getOrgOficinaId() {
		return orgOficinaId;
	}
	/**
	 * Sets the orgOficinaId
	 * @param orgOficinaId The orgOficinaId to set
	 */
	public void setOrgOficinaId(String orgOficinaId) {
		this.orgOficinaId = orgOficinaId;
	}


	/**
	 * Gets the admDescripcionTipoDocumento
	 * @return Returns a String
	 */
	public String getAdmDescripcionTipoDocumento() {
		return admDescripcionTipoDocumento;
	}
	/**
	 * Sets the admDescripcionTipoDocumento
	 * @param admDescripcionTipoDocumento The admDescripcionTipoDocumento to set
	 */
	public void setAdmDescripcionTipoDocumento(String admDescripcionTipoDocumento) {
		this.admDescripcionTipoDocumento = admDescripcionTipoDocumento;
	}

	/**
	 * Gets the repDescripcionTipoDocumento
	 * @return Returns a String
	 */
	public String getRepDescripcionTipoDocumento() {
		return repDescripcionTipoDocumento;
	}
	/**
	 * Sets the repDescripcionTipoDocumento
	 * @param repDescripcionTipoDocumento The repDescripcionTipoDocumento to set
	 */
	public void setRepDescripcionTipoDocumento(String repDescripcionTipoDocumento) {
		this.repDescripcionTipoDocumento = repDescripcionTipoDocumento;
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
	 * Gets the admAnexo
	 * @return Returns a String
	 */
	public String getAdmAnexo() {
		return admAnexo;
	}
	/**
	 * Sets the admAnexo
	 * @param admAnexo The admAnexo to set
	 */
	public void setAdmAnexo(String admAnexo) {
		this.admAnexo = admAnexo;
	}

}

