package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class AbonoVentanillaBean extends SunarpBean{


	private String usuarioId;
	private String nombre;
	private String tipo_doc;
	private String num_doc;
	private String afil_organiz;
	private String lineaPrepago;
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
	 * Gets the tipo_doc
	 * @return Returns a String
	 */
	public String getTipo_doc() {
		return tipo_doc;
	}
	/**
	 * Sets the tipo_doc
	 * @param tipo_doc The tipo_doc to set
	 */
	public void setTipo_doc(String tipo_doc) {
		this.tipo_doc = tipo_doc;
	}

	/**
	 * Gets the num_doc
	 * @return Returns a String
	 */
	public String getNum_doc() {
		return num_doc;
	}
	/**
	 * Sets the num_doc
	 * @param num_doc The num_doc to set
	 */
	public void setNum_doc(String num_doc) {
		this.num_doc = num_doc;
	}

	/**
	 * Gets the afil_organiz
	 * @return Returns a String
	 */
	public String getAfil_organiz() {
		return afil_organiz;
	}
	/**
	 * Sets the afil_organiz
	 * @param afil_organiz The afil_organiz to set
	 */
	public void setAfil_organiz(String afil_organiz) {
		this.afil_organiz = afil_organiz;
	}

	/**
	 * Gets the lineaPrepago
	 * @return Returns a String
	 */
	public String getLineaPrepago() {
		return lineaPrepago;
	}
	/**
	 * Sets the lineaPrepago
	 * @param lineaPrepago The lineaPrepago to set
	 */
	public void setLineaPrepago(String lineaPrepago) {
		this.lineaPrepago = lineaPrepago;
	}

}

