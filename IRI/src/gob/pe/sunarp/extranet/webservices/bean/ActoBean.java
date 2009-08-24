package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class ActoBean implements Serializable
{
	private static final long serialVersionUID = -8984420868159420408L;
	
	private String descripcionActo;
	private String fechaActoConstitutivo;
	
	private String diaplazo;
	private String mesplazo;
	private String anoplazo;
	
	private String montoAfectacion;
	private ParticipanteBean[] participanteBeans;
	private BienBean[] bienBeans;
	private FormaCondicionBean formaCondicionBean;
	private TituloBean tituloBeans;
	private DocumentoBean[] documentoBeans;
	
	public FormaCondicionBean getFormaCondicionBean() {
		return formaCondicionBean;
	}
	public void setFormaCondicionBean(FormaCondicionBean formaCondicionBean) {
		this.formaCondicionBean = formaCondicionBean;
	}
	public BienBean[] getBienBeans() {
		return bienBeans;
	}
	public void setBienBeans(BienBean[] bienBeans) {
		this.bienBeans = bienBeans;
	}
	public String getDescripcionActo() {
		return descripcionActo;
	}
	public void setDescripcionActo(String descripcionActo) {
		this.descripcionActo = descripcionActo;
	}
	public DocumentoBean[] getDocumentoBeans() {
		return documentoBeans;
	}
	public void setDocumentoBeans(DocumentoBean[] documentoBeans) {
		this.documentoBeans = documentoBeans;
	}
	public String getFechaActoConstitutivo() {
		return fechaActoConstitutivo;
	}
	public void setFechaActoConstitutivo(String fechaActoConstitutivo) {
		this.fechaActoConstitutivo = fechaActoConstitutivo;
	}
	public String getMontoAfectacion() {
		return montoAfectacion;
	}
	public void setMontoAfectacion(String montoAfectacion) {
		this.montoAfectacion = montoAfectacion;
	}
	public ParticipanteBean[] getParticipanteBeans() {
		return participanteBeans;
	}
	public void setParticipanteBeans(ParticipanteBean[] participanteBeans) {
		this.participanteBeans = participanteBeans;
	}
	public String getAnoplazo() {
		return anoplazo;
	}
	public void setAnoplazo(String anoplazo) {
		this.anoplazo = anoplazo;
	}
	public String getDiaplazo() {
		return diaplazo;
	}
	public void setDiaplazo(String diaplazo) {
		this.diaplazo = diaplazo;
	}
	public String getMesplazo() {
		return mesplazo;
	}
	public void setMesplazo(String mesplazo) {
		this.mesplazo = mesplazo;
	}
	public TituloBean getTituloBeans() {
		return tituloBeans;
	}
	public void setTituloBeans(TituloBean tituloBeans) {
		this.tituloBeans = tituloBeans;
	}

}
