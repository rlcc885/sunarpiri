package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class ResultadoDetallePartidaRmcBean implements Serializable
{	
	private static final long serialVersionUID = -6570678658601569499L;
	
	private String codigoError;
	private String montoPagado;
	private String fechaOperacion;
	private PartidaRmcBean partidaRmcBeans;
	
	public String getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	public String getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public String getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(String montoPagado) {
		this.montoPagado = montoPagado;
	}
	public PartidaRmcBean getPartidaRmcBeans() {
		return partidaRmcBeans;
	}
	public void setPartidaRmcBeans(PartidaRmcBean partidaRmcBeans) {
		this.partidaRmcBeans = partidaRmcBeans;
	}
}
