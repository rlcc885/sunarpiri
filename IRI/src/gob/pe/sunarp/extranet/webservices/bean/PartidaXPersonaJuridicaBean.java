package gob.pe.sunarp.extranet.webservices.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;


public class PartidaXPersonaJuridicaBean extends SunarpBean{

	private BuscaPartidaCabeceraPersonaJuridicaBean cabecera = null;
	private BuscaPartidaDetallePersonaJuridicaBean[] detalle = null;

	/**
	 * Gets the cabecera
	 * @return Returns a BuscaPartidaCabeceraBean
	 */
	public BuscaPartidaCabeceraPersonaJuridicaBean getCabecera() {
		return cabecera;
	}
	/**
	 * Sets the cabecera
	 * @param cabecera The cabecera to set
	 */
	public void setCabecera(BuscaPartidaCabeceraPersonaJuridicaBean cabecera) {
		this.cabecera = cabecera;
	}

	/**
	 * Gets the detalle
	 * @return Returns a BuscaPartidaDetalleBean[]
	 */
	public BuscaPartidaDetallePersonaJuridicaBean[] getDetalle() {
		return detalle;
	}
	/**
	 * Sets the detalle
	 * @param detalle The detalle to set
	 */
	public void setDetalle(BuscaPartidaDetallePersonaJuridicaBean[] detalle) {
		this.detalle = detalle;
	}

}

