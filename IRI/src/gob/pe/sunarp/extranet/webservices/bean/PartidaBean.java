package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class PartidaBean implements Serializable
{
	private static final long serialVersionUID = -4246781768862775097L;
	
	private String descripcionZonaRegistral;
	private String descripcionOficinaRegistral;
	private String numeroPartida;	
	private String numeroFicha;
	private String numeroTomo;
	private String numeroFolio;
	private String descripcionAreaRegistral;
	private String descripcionRegistro;
	private String participante;
	private String documentoIdentidad;
	private String numeroDocumento;

	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the descripcionAreaRegistral
	 */
	public String getDescripcionAreaRegistral() {
		return descripcionAreaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param descripcionAreaRegistral the descripcionAreaRegistral to set
	 */
	public void setDescripcionAreaRegistral(String descripcionAreaRegistral) {
		this.descripcionAreaRegistral = descripcionAreaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the descripcionOficinaRegistral
	 */
	public String getDescripcionOficinaRegistral() {
		return descripcionOficinaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param descripcionOficinaRegistral the descripcionOficinaRegistral to set
	 */
	public void setDescripcionOficinaRegistral(String descripcionOficinaRegistral) {
		this.descripcionOficinaRegistral = descripcionOficinaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the descripcionRegistro
	 */
	public String getDescripcionRegistro() {
		return descripcionRegistro;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param descripcionRegistro the descripcionRegistro to set
	 */
	public void setDescripcionRegistro(String descripcionRegistro) {
		this.descripcionRegistro = descripcionRegistro;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the descripcionZonaRegistral
	 */
	public String getDescripcionZonaRegistral() {
		return descripcionZonaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param descripcionZonaRegistral the descripcionZonaRegistral to set
	 */
	public void setDescripcionZonaRegistral(String descripcionZonaRegistral) {
		this.descripcionZonaRegistral = descripcionZonaRegistral;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroFicha
	 */
	public String getNumeroFicha() {
		return numeroFicha;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroFicha the numeroFicha to set
	 */
	public void setNumeroFicha(String numeroFicha) {
		this.numeroFicha = numeroFicha;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroFolio
	 */
	public String getNumeroFolio() {
		return numeroFolio;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroFolio the numeroFolio to set
	 */
	public void setNumeroFolio(String numeroFolio) {
		this.numeroFolio = numeroFolio;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroPartida
	 */
	public String getNumeroPartida() {
		return numeroPartida;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroPartida the numeroPartida to set
	 */
	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the numeroTomo
	 */
	public String getNumeroTomo() {
		return numeroTomo;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 22, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param numeroTomo the numeroTomo to set
	 */
	public void setNumeroTomo(String numeroTomo) {
		this.numeroTomo = numeroTomo;
	}
	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}
	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getParticipante() {
		return participante;
	}
	public void setParticipante(String participante) {
		this.participante = participante;
	}
	
	
	
}
