package gob.pe.sunarp.extranet.webservices.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class PartidaXPersonaNaturalBean extends SunarpBean{

	private BuscaPartidaCabeceraPersonaNaturalBean cabecera = null;
	private BuscaPartidaDetallePersonaNaturalBean[] detalle = null;

	/**
	 * Gets the cabecera
	 * @return Returns a BuscaPartidaCabeceraPersonaNaturalBean
	 */
	public BuscaPartidaCabeceraPersonaNaturalBean getCabecera() {
		return cabecera;
	}
	/**
	 * Sets the cabecera
	 * @param cabecera The cabecera to set
	 */
	public void setCabecera(BuscaPartidaCabeceraPersonaNaturalBean cabecera) {
		this.cabecera = cabecera;
	}

	/**
	 * Gets the detalle
	 * @return Returns a BuscaPartidaDetallePersonaNaturalBean[]
	 */
	public BuscaPartidaDetallePersonaNaturalBean[] getDetalle() {
		return detalle;
	}
	/**
	 * Sets the detalle
	 * @param detalle The detalle to set
	 */
	public void setDetalle(BuscaPartidaDetallePersonaNaturalBean[] detalle) {
		this.detalle = detalle;
	}

}

