package gob.pe.sunarp.extranet.webservices.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class PartidaXPropiedadVehicularBean extends SunarpBean{

	private BuscaPartidaCabeceraPropiedadVehicularBean cabecera = null;
	private BuscaPartidaDetallePropiedadVehicularBean[] detalle = null;

	/**
	 * Gets the cabecera
	 * @return Returns a BuscaPartidaCabeceraPropiedadVehicularBean
	 */
	public BuscaPartidaCabeceraPropiedadVehicularBean getCabecera() {
		return cabecera;
	}
	/**
	 * Sets the cabecera
	 * @param cabecera The cabecera to set
	 */
	public void setCabecera(BuscaPartidaCabeceraPropiedadVehicularBean cabecera) {
		this.cabecera = cabecera;
	}

	/**
	 * Gets the detalle
	 * @return Returns a BuscaPartidaDetallePropiedadVehicularBean[]
	 */
	public BuscaPartidaDetallePropiedadVehicularBean[] getDetalle() {
		return detalle;
	}
	/**
	 * Sets the detalle
	 * @param detalle The detalle to set
	 */
	public void setDetalle(BuscaPartidaDetallePropiedadVehicularBean[] detalle) {
		this.detalle = detalle;
	}

}

