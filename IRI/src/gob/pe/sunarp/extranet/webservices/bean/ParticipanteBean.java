package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class ParticipanteBean implements Serializable
{
	private static final long serialVersionUID = -1282477469503641426L;
	
	private String nombreParticipante;
	private String apellidoPaternoParticipante;
	private String apellidoMaternoParticipante;
	private String descripcionAbreviadaTipoDocumento;
	private String numeroDocumento;
	private String descripcionTipoParticipacion;
	private String domicilio;
	private String razonSocial;
	
	/**
	 * @autor jascencio
	 * @fecha Sep 3, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @autor jascencio
	 * @fecha Sep 3, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getDescripcionAbreviadaTipoDocumento() {
		return descripcionAbreviadaTipoDocumento;
	}
	public void setDescripcionAbreviadaTipoDocumento(
			String descripcionAbreviadaTipoDocumento) {
		this.descripcionAbreviadaTipoDocumento = descripcionAbreviadaTipoDocumento;
	}
	public String getDescripcionTipoParticipacion() {
		return descripcionTipoParticipacion;
	}
	public void setDescripcionTipoParticipacion(String descripcionTipoParticipacion) {
		this.descripcionTipoParticipacion = descripcionTipoParticipacion;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getNombreParticipante() {
		return nombreParticipante;
	}
	public void setNombreParticipante(String nombreParticipante) {
		this.nombreParticipante = nombreParticipante;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getApellidoMaternoParticipante() {
		return apellidoMaternoParticipante;
	}
	public void setApellidoMaternoParticipante(String apellidoMaternoParticipante) {
		this.apellidoMaternoParticipante = apellidoMaternoParticipante;
	}
	public String getApellidoPaternoParticipante() {
		return apellidoPaternoParticipante;
	}
	public void setApellidoPaternoParticipante(String apellidoPaternoParticipante) {
		this.apellidoPaternoParticipante = apellidoPaternoParticipante;
	}
}
