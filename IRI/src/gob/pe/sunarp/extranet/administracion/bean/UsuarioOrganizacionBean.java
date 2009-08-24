package gob.pe.sunarp.extranet.administracion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class UsuarioOrganizacionBean extends SunarpBean{
	private String userID;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String apellidosYnombres;
	private String tsRegistro;
	private String saldo;
	private String estado;
	private String admOrg;
	/**
	 * Gets the userID
	 * @return Returns a String
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * Sets the userID
	 * @param userID The userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

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
	 * Gets the tsRegistro
	 * @return Returns a String
	 */
	public String getTsRegistro() {
		return tsRegistro;
	}
	/**
	 * Sets the tsRegistro
	 * @param tsRegistro The tsRegistro to set
	 */
	public void setTsRegistro(String tsRegistro) {
		this.tsRegistro = tsRegistro;
	}

	/**
	 * Gets the saldo
	 * @return Returns a String
	 */
	public String getSaldo() {
		return saldo;
	}
	/**
	 * Sets the saldo
	 * @param saldo The saldo to set
	 */
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		if (estado.equalsIgnoreCase("1"))
			return "ACTIVO";
		else
			return "INACTIVO";
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Gets the admOrg
	 * @return Returns a String
	 */
	public String getAdmOrg() {
		//si el tercer caracter es 1 entonces quiere decir que es el administrador de la organizacion
		if (admOrg.charAt(2) == '1')			
			return "SI";
		else
			return " ";
	}
	/**
	 * Sets the admOrg
	 * @param admOrg The admOrg to set
	 */
	public void setAdmOrg(String admOrg) {
		this.admOrg = admOrg;
	}

	/**
	 * Gets the apellidosYnombres
	 * @return Returns a String
	 */
	public String getApellidosYnombres() {
		StringBuffer aux = new StringBuffer();
		aux.append(this.getApellidoPaterno());
		aux.append(" ");
		aux.append(this.getApellidoMaterno());
		aux.append(" ");
		aux.append(this.getNombres());
				
		return aux.toString();
	}
	/**
	 * Sets the apellidosYnombres
	 * @param apellidosYnombres The apellidosYnombres to set
	 */
	public void setApellidosYnombres(String apellidosYnombres) {
		this.apellidosYnombres = apellidosYnombres;
	}

}

