package gob.pe.sunarp.extranet.publicidad.certificada.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class SolicitanteBean extends SunarpBean{
	
	private String solicitante_id;
	private String solicitud_id;
	private String tpo_pers;
	private String ape_pat;
	private String ape_mat;
	private String nombres;
	private String raz_soc;
	private String tipo_doc_id;
	private String num_doc_iden;
	private String email;
	private String ts_crea;
	/** DESCAJ IFIGUEROA 19/01/2007 **/
	private String direccion;
	private String zonaRegistral;
	private String saldo;
	
	

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
	 * Gets the tipo_doc_id
	 * @return Returns a String
	 */
	public String getTipo_doc_id() {
		return tipo_doc_id;
	}
	/**
	 * Sets the tipo_doc_id
	 * @param tipo_doc_id The tipo_doc_id to set
	 */
	public void setTipo_doc_id(String tipo_doc_id) {
		this.tipo_doc_id = tipo_doc_id;
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
	 * Gets the ts_crea
	 * @return Returns a String
	 */
	public String getTs_crea() {
		return ts_crea;
	}
	/**
	 * Sets the ts_crea
	 * @param ts_crea The ts_crea to set
	 */
	public void setTs_crea(String ts_crea) {
		this.ts_crea = ts_crea;
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
	 * Gets the num_doc_iden
	 * @return Returns a String
	 */
	public String getNum_doc_iden() {
		return num_doc_iden;
	}
	/**
	 * Sets the num_doc_iden
	 * @param num_doc_iden The num_doc_iden to set
	 */
	public void setNum_doc_iden(String num_doc_iden) {
		this.num_doc_iden = num_doc_iden;
	}

	/**
	 * Gets the solicitante_id
	 * @return Returns a String
	 */
	public String getSolicitante_id() {
		return solicitante_id;
	}
	/**
	 * Sets the solicitante_id
	 * @param solicitante_id The solicitante_id to set
	 */
	public void setSolicitante_id(String solicitante_id) {
		this.solicitante_id = solicitante_id;
	}

	/**
	 * @return
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param string
	 */
	public void setDireccion(String string) {
		direccion = string;
	}

	/**
	 * @return
	 */
	public String getZonaRegistral() {
		return zonaRegistral;
	}

	/**
	 * @param string
	 */
	public void setZonaRegistral(String string) {
		zonaRegistral = string;
	}

	/**
	 * @return
	 */
	public String getSaldo() {
		return saldo;
	}

	/**
	 * @param string
	 */
	public void setSaldo(String string) {
		saldo = string;
	}

}

