package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.util.Vector;

public class DetalleTituloBean extends SunarpBean{

	private String num_titulo;
	private String ano_titulo;
	private String oficina;
	private String mensaje;
	private String vencimiento;
	private String presentante_nom;
	private String presentante_num_doc;
	private String fecha;
	private String resultado;
	private String tipoRegistro;
	private String url = "";
	private String desc_url = "";
	private String fecha_ult_sinc;
	//16nov2002
	private String areaRegistral="";
	//Inicio:mgarate:11/07/2007
	private String rmc = "";
	
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	/**
	 * Gets the num_titulo
	 * @return Returns a String
	 */
	public String getNum_titulo() {
		return num_titulo;
	}
	/**
	 * Sets the num_titulo
	 * @param num_titulo The num_titulo to set
	 */
	public void setNum_titulo(String num_titulo) {
		this.num_titulo = num_titulo;
	}

	/**
	 * Gets the ano_titulo
	 * @return Returns a String
	 */
	public String getAno_titulo() {
		return ano_titulo;
	}
	/**
	 * Sets the ano_titulo
	 * @param ano_titulo The ano_titulo to set
	 */
	public void setAno_titulo(String ano_titulo) {
		this.ano_titulo = ano_titulo;
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
	 * Gets the resultado
	 * @return Returns a String
	 */
	public String getResultado() {
		return resultado;
	}
	/**
	 * Sets the resultado
	 * @param resultado The resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * Gets the mensaje
	 * @return Returns a String
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * Sets the mensaje
	 * @param mensaje The mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Gets the fecha
	 * @return Returns a String
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * Sets the fecha
	 * @param fecha The fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the vencimiento
	 * @return Returns a String
	 */
	public String getVencimiento() {
		return vencimiento;
	}
	/**
	 * Sets the vencimiento
	 * @param vencimiento The vencimiento to set
	 */
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}

	/**
	 * Gets the presentante_nom
	 * @return Returns a String
	 */
	public String getPresentante_nom() {
		return presentante_nom;
	}
	/**
	 * Sets the presentante_nom
	 * @param presentante_nom The presentante_nom to set
	 */
	public void setPresentante_nom(String presentante_nom) {
		this.presentante_nom = presentante_nom;
	}

	/**
	 * Gets the presentante_num_doc
	 * @return Returns a String
	 */
	public String getPresentante_num_doc() {
		return presentante_num_doc;
	}
	/**
	 * Sets the presentante_num_doc
	 * @param presentante_num_doc The presentante_num_doc to set
	 */
	public void setPresentante_num_doc(String presentante_num_doc) {
		this.presentante_num_doc = presentante_num_doc;
	}
	/**
	 * Gets the desc_url
	 * @return Returns a String
	 */
	public String getDesc_url() {
		return desc_url;
	}
	/**
	 * Sets the desc_url
	 * @param desc_url The desc_url to set
	 */
	public void setDesc_url(String desc_url) {
		this.desc_url = desc_url;
	}

	/**
	 * Gets the url
	 * @return Returns a String
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Sets the url
	 * @param url The url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the fecha_ult_sinc
	 * @return Returns a String
	 */
	public String getFecha_ult_sinc() {
		return fecha_ult_sinc;
	}
	/**
	 * Sets the fecha_ult_sinc
	 * @param fecha_ult_sinc The fecha_ult_sinc to set
	 */
	public void setFecha_ult_sinc(String fecha_ult_sinc) {
		this.fecha_ult_sinc = fecha_ult_sinc;
	}

	/**
	 * Gets the areaRegistral
	 * @return Returns a String
	 */
	public String getAreaRegistral() {
		return areaRegistral;
	}
	/**
	 * Sets the areaRegistral
	 * @param areaRegistral The areaRegistral to set
	 */
	public void setAreaRegistral(String areaRegistral) {
		this.areaRegistral = areaRegistral;
	}
	public String getRmc() {
		return rmc;
	}
	public void setRmc(String rmc) {
		this.rmc = rmc;
	}

}

