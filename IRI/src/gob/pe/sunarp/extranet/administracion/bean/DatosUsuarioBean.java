package gob.pe.sunarp.extranet.administracion.bean;
//-
import gob.pe.sunarp.extranet.common.SunarpBean;

import java.util.List;

public class DatosUsuarioBean extends SunarpBean{

	private String apellidoPaterno="";
	private String apellidoMaterno="";
	private String nombres="";
	private String tipoDocumento="";
	private String numDocumento="";
	private String fax="";
	private String telefono="";
	private String anexo="";
	private String pais="";
	private String departamento="";
	private String otroDepartamento="";
	private String provincia="";
	private String distrito="";
	private String direccion="";
	private String codPostal="";
	private String email="";
	private String userId="";
	private String perfilId="";
	private String clave="";
	private String confirmacionClave="";
	private String preguntaSecreta="";
	private String respuestaSecreta="";
	
	//-
	private boolean flagInterno=false;
	private boolean flagExoneradoPago=false;
	private boolean flagActivo=false;
	
	//-campos extra para link "editar mis datos"
	private String contrasena1=null;
	private String contrasena2=null;
	private String contrasena3=null;
	
	private String cuentaId="";
	
	//arreglos para almacenar los permisos EXTRAS del usuario
	private String[] arrPermisoId  =null;
	private String[] arrPermisoDesc=null;
	
	/************* AGERGADO POR GIANCARLO OCHOA **********/
	private String personaId = null;
	private String persJuriId = null;
	/*****************************************************/
	/** DESCAJ 03/01/2007 IFIGUEROA **/
	private int diasCad=0;
	/** DESCAJ 03/01/2007 IFIGUEROA **/
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
	 * Gets the numDocumento
	 * @return Returns a String
	 */
	public String getNumDocumento() {
		return numDocumento;
	}
	/**
	 * Sets the numDocumento
	 * @param numDocumento The numDocumento to set
	 */
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
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
	 * Gets the provincia
	 * @return Returns a String
	 */
	public String getProvincia() {
		return provincia;
	}
	/**
	 * Sets the provincia
	 * @param provincia The provincia to set
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
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
	 * Gets the codPostal
	 * @return Returns a String
	 */
	public String getCodPostal() {
		return codPostal;
	}
	/**
	 * Sets the codPostal
	 * @param codPostal The codPostal to set
	 */
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
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
	 * Gets the perfilId
	 * @return Returns a String
	 */
	public String getPerfilId() {
		return perfilId;
	}
	/**
	 * Sets the perfilId
	 * @param perfilId The perfilId to set
	 */
	public void setPerfilId(String perfilId) {
		this.perfilId = perfilId;
	}


	/**
	 * Gets the otroDepartamento
	 * @return Returns a String
	 */
	public String getOtroDepartamento() {
		return otroDepartamento;
	}
	/**
	 * Sets the otroDepartamento
	 * @param otroDepartamento The otroDepartamento to set
	 */
	public void setOtroDepartamento(String otroDepartamento) {
		this.otroDepartamento = otroDepartamento;
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
	 * Gets the flagExoneradoPago
	 * @return Returns a boolean
	 */
	public boolean getFlagExoneradoPago() {
		return flagExoneradoPago;
	}
	/**
	 * Sets the flagExoneradoPago
	 * @param flagExoneradoPago The flagExoneradoPago to set
	 */
	public void setFlagExoneradoPago(boolean flagExoneradoPago) {
		this.flagExoneradoPago = flagExoneradoPago;
	}

	/**
	 * Gets the flagInterno
	 * @return Returns a boolean
	 */
	public boolean getFlagInterno() {
		return flagInterno;
	}
	/**
	 * Sets the flagInterno
	 * @param flagInterno The flagInterno to set
	 */
	public void setFlagInterno(boolean flagInterno) {
		this.flagInterno = flagInterno;
	}

	/**
	 * Gets the flagActivo
	 * @return Returns a boolean
	 */
	public boolean getFlagActivo() {
		return flagActivo;
	}
	/**
	 * Sets the flagActivo
	 * @param flagActivo The flagActivo to set
	 */
	public void setFlagActivo(boolean flagActivo) {
		this.flagActivo = flagActivo;
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
	 * Gets the arrPermisoId
	 * @return Returns a String[]
	 */
	public String[] getArrPermisoId() {
		return arrPermisoId;
	}
	/**
	 * Sets the arrPermisoId
	 * @param arrPermisoId The arrPermisoId to set
	 */
	public void setArrPermisoId(String[] arrPermisoId) {
		this.arrPermisoId = arrPermisoId;
	}

	/**
	 * Gets the arrPermisoDesc
	 * @return Returns a String[]
	 */
	public String[] getArrPermisoDesc() {
		return arrPermisoDesc;
	}
	/**
	 * Sets the arrPermisoDesc
	 * @param arrPermisoDesc The arrPermisoDesc to set
	 */
	public void setArrPermisoDesc(String[] arrPermisoDesc) {
		this.arrPermisoDesc = arrPermisoDesc;
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
	 * Gets the persJuriId
	 * @return Returns a String
	 */
	public String getPersJuriId() {
		return persJuriId;
	}
	/**
	 * Sets the persJuriId
	 * @param persJuriId The persJuriId to set
	 */
	public void setPersJuriId(String persJuriId) {
		this.persJuriId = persJuriId;
	}

	/**
	 * @return
	 */
	public int getDiasCad() {
		return diasCad;
	}

	/**
	 * @param i
	 */
	public void setDiasCad(int i) {
		diasCad = i;
	}

}

