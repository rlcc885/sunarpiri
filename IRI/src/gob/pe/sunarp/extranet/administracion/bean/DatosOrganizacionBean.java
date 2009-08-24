package gob.pe.sunarp.extranet.administracion.bean;
//-
import gob.pe.sunarp.extranet.common.SunarpBean;

import java.util.List;

public class DatosOrganizacionBean extends SunarpBean{
	
	//Datos de Organizacion	
	private String ruc="";
	private String razonSocial="";
	private String paisIdOrganizacion="";
	private String departamentoIdOrganizacion="";
	private String otroDepartamentoOrganizacion="";
	private String provinciaIdOrganizacion="";
	private String distritoOrganizacion="";
	private String direccionOrganizacion="";
	private String codPostalOrganizacion="";
	private String prefijoCuenta="";
	private String clave="";
	private String confirmacionClave="";
	private String cur="";
	private String jurisdiccionId="";
	private String giroNegocio="";
	private String respuestaSecreta="";
	private String preguntaSecretaId="";
	private String emailOrganizacion=""; //en realidad no tiene, se le asigna el mismo que el administrador
	private String siglas="";

	//-Representante--
	private String apellidoPaternoRepresentante="";
	private String apellidoMaternoRepresentante="";
	private String nombresRepresentante="";
	private String tipoDocumentoRepresentante="";
	private String numeroDocumentoRepresentante="";
	//--Administrador --
	private String apellidoPaternoAdministrador="";
	private String apellidoMaternoAdministrador="";
	private String nombresAdministrador="";
	private String tipoDocumentoAdministrador="";
	private String numeroDocumentoAdministrador="";
	private String emailAdministrador="";
	private String paisAdministrador="";
	private String departamentoAdministrador="";
	private String otroDepartamentoAdministrador="";
	private String provinciaAdministrador="";
	private String distritoAdministrador="";
	private String direccionAdministrador="";
	private String codPostalAdministrador="";
	private String telefonoAdministrador="";
	private String anexoAdministrador="";
	private String faxAdministrador=""; 
	
	//-extra
	private boolean flagOrganizacionInterna=false;
	private boolean flagExonerarPago=false;
	private String  organizacionPeJuriId="";


	//-campos extra para link "editar mis datos"
	private String contrasena1=null;
	private String contrasena2=null;
	private String contrasena3=null;
	
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
	 * Gets the clave
	 * @return Returns a String
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * Sets the clave
	 * @param clave The clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Gets the confirmacionClave
	 * @return Returns a String
	 */
	public String getConfirmacionClave() {
		return confirmacionClave;
	}
	/**
	 * Sets the confirmacionClave
	 * @param confirmacionClave The confirmacionClave to set
	 */
	public void setConfirmacionClave(String confirmacionClave) {
		this.confirmacionClave = confirmacionClave;
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
	 * Gets the preguntaSecretaId
	 * @return Returns a String
	 */
	public String getPreguntaSecretaId() {
		return preguntaSecretaId;
	}
	/**
	 * Sets the preguntaSecretaId
	 * @param preguntaSecretaId The preguntaSecretaId to set
	 */
	public void setPreguntaSecretaId(String preguntaSecretaId) {
		this.preguntaSecretaId = preguntaSecretaId;
	}

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
	 * Gets the giroNegocio
	 * @return Returns a String
	 */
	public String getGiroNegocio() {
		return giroNegocio;
	}
	/**
	 * Sets the giroNegocio
	 * @param giroNegocio The giroNegocio to set
	 */
	public void setGiroNegocio(String giroNegocio) {
		this.giroNegocio = giroNegocio;
	}

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
	 * Gets the tipoDocumentoRepresentante
	 * @return Returns a String
	 */
	public String getTipoDocumentoRepresentante() {
		return tipoDocumentoRepresentante;
	}
	/**
	 * Sets the tipoDocumentoRepresentante
	 * @param tipoDocumentoRepresentante The tipoDocumentoRepresentante to set
	 */
	public void setTipoDocumentoRepresentante(String tipoDocumentoRepresentante) {
		this.tipoDocumentoRepresentante = tipoDocumentoRepresentante;
	}

	/**
	 * Gets the numeroDocumentoRepresentante
	 * @return Returns a String
	 */
	public String getNumeroDocumentoRepresentante() {
		return numeroDocumentoRepresentante;
	}
	/**
	 * Sets the numeroDocumentoRepresentante
	 * @param numeroDocumentoRepresentante The numeroDocumentoRepresentante to set
	 */
	public void setNumeroDocumentoRepresentante(String numeroDocumentoRepresentante) {
		this.numeroDocumentoRepresentante = numeroDocumentoRepresentante;
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
	 * Gets the numeroDocumentoAdministrador
	 * @return Returns a String
	 */
	public String getNumeroDocumentoAdministrador() {
		return numeroDocumentoAdministrador;
	}
	/**
	 * Sets the numeroDocumentoAdministrador
	 * @param numeroDocumentoAdministrador The numeroDocumentoAdministrador to set
	 */
	public void setNumeroDocumentoAdministrador(String numeroDocumentoAdministrador) {
		this.numeroDocumentoAdministrador = numeroDocumentoAdministrador;
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
	 * Gets the otroDepartamentoAdministrador
	 * @return Returns a String
	 */
	public String getOtroDepartamentoAdministrador() {
		return otroDepartamentoAdministrador;
	}
	/**
	 * Sets the otroDepartamentoAdministrador
	 * @param otroDepartamentoAdministrador The otroDepartamentoAdministrador to set
	 */
	public void setOtroDepartamentoAdministrador(String otroDepartamentoAdministrador) {
		this.otroDepartamentoAdministrador = otroDepartamentoAdministrador;
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
	 * Gets the distritoAdministrador
	 * @return Returns a String
	 */
	public String getDistritoAdministrador() {
		return distritoAdministrador;
	}
	/**
	 * Sets the distritoAdministrador
	 * @param distritoAdministrador The distritoAdministrador to set
	 */
	public void setDistritoAdministrador(String distritoAdministrador) {
		this.distritoAdministrador = distritoAdministrador;
	}

	/**
	 * Gets the direccionAdministrador
	 * @return Returns a String
	 */
	public String getDireccionAdministrador() {
		return direccionAdministrador;
	}
	/**
	 * Sets the direccionAdministrador
	 * @param direccionAdministrador The direccionAdministrador to set
	 */
	public void setDireccionAdministrador(String direccionAdministrador) {
		this.direccionAdministrador = direccionAdministrador;
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
	 * Gets the tipoDocumentoAdministrador
	 * @return Returns a String
	 */
	public String getTipoDocumentoAdministrador() {
		return tipoDocumentoAdministrador;
	}
	/**
	 * Sets the tipoDocumentoAdministrador
	 * @param tipoDocumentoAdministrador The tipoDocumentoAdministrador to set
	 */
	public void setTipoDocumentoAdministrador(String tipoDocumentoAdministrador) {
		this.tipoDocumentoAdministrador = tipoDocumentoAdministrador;
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
	 * Gets the distritoOrganizacion
	 * @return Returns a String
	 */
	public String getDistritoOrganizacion() {
		return distritoOrganizacion;
	}
	/**
	 * Sets the distritoOrganizacion
	 * @param distritoOrganizacion The distritoOrganizacion to set
	 */
	public void setDistritoOrganizacion(String distritoOrganizacion) {
		this.distritoOrganizacion = distritoOrganizacion;
	}

	/**
	 * Gets the direccionOrganizacion
	 * @return Returns a String
	 */
	public String getDireccionOrganizacion() {
		return direccionOrganizacion;
	}
	/**
	 * Sets the direccionOrganizacion
	 * @param direccionOrganizacion The direccionOrganizacion to set
	 */
	public void setDireccionOrganizacion(String direccionOrganizacion) {
		this.direccionOrganizacion = direccionOrganizacion;
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
	 * Gets the emailOrganizacion
	 * @return Returns a String
	 */
	public String getEmailOrganizacion() {
		return emailOrganizacion;
	}
	/**
	 * Sets the emailOrganizacion
	 * @param emailOrganizacion The emailOrganizacion to set
	 */
	public void setEmailOrganizacion(String emailOrganizacion) {
		this.emailOrganizacion = emailOrganizacion;
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
	 * Gets the flagOrganizacionInterna
	 * @return Returns a boolean
	 */
	public boolean getFlagOrganizacionInterna() {
		return flagOrganizacionInterna;
	}
	/**
	 * Sets the flagOrganizacionInterna
	 * @param flagOrganizacionInterna The flagOrganizacionInterna to set
	 */
	public void setFlagOrganizacionInterna(boolean flagOrganizacionInterna) {
		this.flagOrganizacionInterna = flagOrganizacionInterna;
	}

	/**
	 * Gets the flagExonerarPago
	 * @return Returns a boolean
	 */
	public boolean getFlagExonerarPago() {
		return flagExonerarPago;
	}
	/**
	 * Sets the flagExonerarPago
	 * @param flagExonerarPago The flagExonerarPago to set
	 */
	public void setFlagExonerarPago(boolean flagExonerarPago) {
		this.flagExonerarPago = flagExonerarPago;
	}


	/**
	 * Gets the organizacionPeJuriId
	 * @return Returns a String
	 */
	public String getOrganizacionPeJuriId() {
		return organizacionPeJuriId;
	}
	/**
	 * Sets the organizacionPeJuriId
	 * @param organizacionPeJuriId The organizacionPeJuriId to set
	 */
	public void setOrganizacionPeJuriId(String organizacionPeJuriId) {
		this.organizacionPeJuriId = organizacionPeJuriId;
	}

	/**
	 * Gets the contrasena1
	 * @return Returns a String
	 */
	public String getContrasena1() {
		return contrasena1;
	}
	/**
	 * Sets the contrasena1
	 * @param contrasena1 The contrasena1 to set
	 */
	public void setContrasena1(String contrasena1) {
		this.contrasena1 = contrasena1;
	}

	/**
	 * Gets the contrasena2
	 * @return Returns a String
	 */
	public String getContrasena2() {
		return contrasena2;
	}
	/**
	 * Sets the contrasena2
	 * @param contrasena2 The contrasena2 to set
	 */
	public void setContrasena2(String contrasena2) {
		this.contrasena2 = contrasena2;
	}

	/**
	 * Gets the contrasena3
	 * @return Returns a String
	 */
	public String getContrasena3() {
		return contrasena3;
	}
	/**
	 * Sets the contrasena3
	 * @param contrasena3 The contrasena3 to set
	 */
	public void setContrasena3(String contrasena3) {
		this.contrasena3 = contrasena3;
	}

	/**
	 * Gets the anexoAdministrador
	 * @return Returns a String
	 */
	public String getAnexoAdministrador() {
		return anexoAdministrador;
	}
	/**
	 * Sets the anexoAdministrador
	 * @param anexoAdministrador The anexoAdministrador to set
	 */
	public void setAnexoAdministrador(String anexoAdministrador) {
		this.anexoAdministrador = anexoAdministrador;
	}

}

