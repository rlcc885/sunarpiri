package gob.pe.sunarp.extranet.publicidad.bean;

import java.util.ArrayList;
import java.util.List;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class GeneralTituloBean extends SunarpBean{


	private String sede;
	private String dependencia;
	private String ano;
	private String titulo;
	private String tipo_registro;
	private String tipo_acto;
	private String partida = "";
	private String fec_presentacion;
	private String fec_vencimiento;
	private String presentante = "";
	private String participante = "";
	private String estado = "";
	private String fec_limite;
	private String url_detalle;
	private String url_esquela;
	private String num_sec_participante;
	private String reg_pub_id;
	private String ofic_reg_id;
	private String tipo_per = "";
	private String raz_soc = "";
	private String tar_area_reg_id = "";
	//Inicio:mgarate:11/07/2007
	private List listadoActos;//se cambio de un String[] a un ArrayList para un mejor manejo jascencio.
	//Fin:mgarate
	
	
	/**
	 * Gets the sede
	 * @return Returns a String
	 */
	public String getSede() {
		return sede;
	}
	/**
	 * Sets the sede
	 * @param sede The sede to set
	 */
	public void setSede(String sede) {
		this.sede = sede;
	}

	/**
	 * Gets the dependencia
	 * @return Returns a String
	 */
	public String getDependencia() {
		return dependencia;
	}
	/**
	 * Sets the dependencia
	 * @param dependencia The dependencia to set
	 */
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * Gets the ano
	 * @return Returns a String
	 */
	public String getAno() {
		return ano;
	}
	/**
	 * Sets the ano
	 * @param ano The ano to set
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * Gets the titulo
	 * @return Returns a String
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Sets the titulo
	 * @param titulo The titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Gets the tipo_registro
	 * @return Returns a String
	 */
	public String getTipo_registro() {
		return tipo_registro;
	}
	/**
	 * Sets the tipo_registro
	 * @param tipo_registro The tipo_registro to set
	 */
	public void setTipo_registro(String tipo_registro) {
		this.tipo_registro = tipo_registro;
	}

	/**
	 * Gets the tipo_acto
	 * @return Returns a String
	 */
	public String getTipo_acto() {
		return tipo_acto;
	}
	/**
	 * Sets the tipo_acto
	 * @param tipo_acto The tipo_acto to set
	 */
	public void setTipo_acto(String tipo_acto) {
		this.tipo_acto = tipo_acto;
	}

	/**
	 * Gets the partida
	 * @return Returns a String
	 */
	public String getPartida() {
		return partida;
	}
	/**
	 * Sets the partida
	 * @param partida The partida to set
	 */
	public void setPartida(String partida) {
		this.partida = partida;
	}

	/**
	 * Gets the fec_presentacion
	 * @return Returns a String
	 */
	public String getFec_presentacion() {
		return fec_presentacion;
	}
	/**
	 * Sets the fec_presentacion
	 * @param fec_presentacion The fec_presentacion to set
	 */
	public void setFec_presentacion(String fec_presentacion) {
		this.fec_presentacion = fec_presentacion;
	}

	/**
	 * Gets the fec_vencimiento
	 * @return Returns a String
	 */
	public String getFec_vencimiento() {
		return fec_vencimiento;
	}
	/**
	 * Sets the fec_vencimiento
	 * @param fec_vencimiento The fec_vencimiento to set
	 */
	public void setFec_vencimiento(String fec_vencimiento) {
		this.fec_vencimiento = fec_vencimiento;
	}

	/**
	 * Gets the presentante
	 * @return Returns a String
	 */
	public String getPresentante() {
		return presentante;
	}
	/**
	 * Sets the presentante
	 * @param presentante The presentante to set
	 */
	public void setPresentante(String presentante) {
		this.presentante = presentante;
	}
//modificado por JACR - inicio
	
	public String getTipo_Per() {
		return tipo_per;
	}
	/**
	 * Sets the presentante
	 * @param presentante The presentante to set
	 */
	public void setTipo_Per(String tipo_per) {
		this.tipo_per = tipo_per;
	}	
	
	public String getRaz_Soc() {
		return raz_soc;
	}
	/**
	 * Sets the presentante
	 * @param presentante The presentante to set
	 */
	public void setRaz_Soc(String raz_soc) {
		this.raz_soc = raz_soc;
	}
	
	public String getArea_Reg() {
		return tar_area_reg_id;
	}
	/**
	 * Sets the presentante
	 * @param presentante The presentante to set
	 */
	public void setArea_Reg(String tar_area_reg_id) {
		this.tar_area_reg_id = tar_area_reg_id;
	}
//modificado por JACR - fin
	
	/**
	 * Gets the participante
	 * @return Returns a String
	 */
	public String getParticipante() {
		return participante;
	}
	/**
	 * Sets the participante
	 * @param participante The participante to set
	 */
	public void setParticipante(String participante) {
		this.participante = participante;
	}

	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Gets the fec_limite
	 * @return Returns a String
	 */
	public String getFec_limite() {
		return fec_limite;
	}
	/**
	 * Sets the fec_limite
	 * @param fec_limite The fec_limite to set
	 */
	public void setFec_limite(String fec_limite) {
		this.fec_limite = fec_limite;
	}

	/**
	 * Gets the url_detalle
	 * @return Returns a String
	 */
	public String getUrl_detalle() {
		return url_detalle;
	}
	/**
	 * Sets the url_detalle
	 * @param url_detalle The url_detalle to set
	 */
	public void setUrl_detalle(String url_detalle) {
		this.url_detalle = url_detalle;
	}

	/**
	 * Gets the url_esquela
	 * @return Returns a String
	 */
	public String getUrl_esquela() {
		return url_esquela;
	}
	/**
	 * Sets the url_esquela
	 * @param url_esquela The url_esquela to set
	 */
	public void setUrl_esquela(String url_esquela) {
		this.url_esquela = url_esquela;
	}

	/**
	 * Gets the num_sec_participante
	 * @return Returns a Strin
	 */
	public String getNum_sec_participante() {
		return num_sec_participante;
	}
	/**
	 * Sets the num_sec_participante
	 * @param num_sec_participante The num_sec_participante to set
	 */
	public void setNum_sec_participante(String num_sec_participante) {
		this.num_sec_participante = num_sec_participante;
	}

	/**
	 * Gets the reg_pub_id
	 * @return Returns a String
	 */
	public String getReg_pub_id() {
		return reg_pub_id;
	}
	/**
	 * Sets the reg_pub_id
	 * @param reg_pub_id The reg_pub_id to set
	 */
	public void setReg_pub_id(String reg_pub_id) {
		this.reg_pub_id = reg_pub_id;
	}

	/**
	 * Gets the ofic_reg_id
	 * @return Returns a String
	 */
	public String getOfic_reg_id() {
		return ofic_reg_id;
	}
	/**
	 * Sets the ofic_reg_id
	 * @param ofic_reg_id The ofic_reg_id to set
	 */
	public void setOfic_reg_id(String ofic_reg_id) {
		this.ofic_reg_id = ofic_reg_id;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 13, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the listadoActos
	 */
	public List getListadoActos() {
		return listadoActos;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 13, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param listadoActos the listadoActos to set
	 */
	public void setListadoActos(List listadoActos) {
		this.listadoActos = listadoActos;
	}
	

}

