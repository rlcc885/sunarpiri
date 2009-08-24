/*
 * Created on 11-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.caja.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/**
 * @author jbugarin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DetalleCajaBean extends SunarpBean{


	public static final String ESTADO_CERRADO = "0";
	public static final String APERTURA_GENERAL = "1";
	public static final String APERTURA_CAJERO = "2";	
	
	public String caja;
	public String cajeroAsignado;
	public String estado;
	public String desEstado;	

	/**
	 * @return
	 */
	public String getCaja() {
		return caja;
	}

	/**
	 * @return
	 */
	public String getCajeroAsignado() {
		return cajeroAsignado;
	}

	/**
	 * @return
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param string
	 */
	public void setCaja(String string) {
		caja = string;
	}

	/**
	 * @param string
	 */
	public void setCajeroAsignado(String string) {
		cajeroAsignado = string;
	}

	/**
	 * @param string
	 */
	public void setEstado(String string) {
		estado = string;
	}

	/**
	 * @return
	 */
	public String getDesEstado() {
		return desEstado;
	}

	/**
	 * @param string
	 */
	public void setDesEstado(String string) {
		desEstado = string;
	}

}
