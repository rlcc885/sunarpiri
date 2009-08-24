package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class DetalleTituloEntidadBean extends SunarpBean{


	private String tipo;
	private String entidad;
	/**
	 * Gets the tipo
	 * @return Returns a String
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Sets the tipo
	 * @param tipo The tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the entidad
	 * @return Returns a String
	 */
	public String getEntidad() {
		return entidad;
	}
	/**
	 * Sets the entidad
	 * @param entidad The entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

}

