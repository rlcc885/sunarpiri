/**
 * 
 */
package gob.pe.sunarp.extranet.solicitud.denominacion.beans;

import java.io.Serializable;

/**
 * @author jbugarin
 *
 */
public class RazonSocial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String denominacion;
	private String denoAbrev;
	private String orden;
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	/**
	 * 
	 */
	public RazonSocial() {
		// TODO Auto-generated constructor stub
	}
	public String getDenoAbrev() {
		return denoAbrev;
	}
	public void setDenoAbrev(String denoAbrev) {
		this.denoAbrev = denoAbrev;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

}
