package gob.pe.sunarp.extranet.webservices.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class PartidaXPropiedadInmuebleBean extends SunarpBean{

	private BuscaPartidaCabeceraPropiedadInmuebleBean cabecera = null;
	private BuscaPartidaDetallePropiedadInmuebleBean[] detalle = null;

	/**
	 * Gets the cabecera
	 * @return Returns a BuscaPartidaCabeceraPropiedadInmuebleBean
	 */
	public BuscaPartidaCabeceraPropiedadInmuebleBean getCabecera() {
		return cabecera;
	}
	/**
	 * Sets the cabecera
	 * @param cabecera The cabecera to set
	 */
	public void setCabecera(BuscaPartidaCabeceraPropiedadInmuebleBean cabecera) {
		this.cabecera = cabecera;
	}

	/**
	 * Gets the detalle
	 * @return Returns a BuscaPartidaDetallePropiedadInmuebleBean[]
	 */
	public BuscaPartidaDetallePropiedadInmuebleBean[] getDetalle() {
		return detalle;
	}
	/**
	 * Sets the detalle
	 * @param detalle The detalle to set
	 */
	public void setDetalle(BuscaPartidaDetallePropiedadInmuebleBean[] detalle) {
		this.detalle = detalle;
	}

}

