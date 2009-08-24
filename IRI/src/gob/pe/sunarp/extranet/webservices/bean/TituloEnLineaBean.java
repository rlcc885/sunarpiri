/**
 * 
 */
package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

import gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion;

/**
 * @author jbugarin
 *
 */
public class TituloEnLineaBean implements Serializable{

	private Denominacion deno;

	public Denominacion getDeno() {
		return deno;
	}

	public void setDeno(Denominacion deno) {
		this.deno = deno;
	}
}
