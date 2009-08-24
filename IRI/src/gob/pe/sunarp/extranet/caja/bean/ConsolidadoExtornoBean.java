/*
 * Created on 26-ene-07
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
public class ConsolidadoExtornoBean extends SunarpBean{
	    
	    public String nroAbono;
		public String motivo;
		public String monto;
		public String usuarioTesorero;
		public String concepto;
		public String operador;//agregado jbugarin observaciones cajeros
	    


		/**
		 * @return
		 */
		public String getMonto() {
			return monto;
		}

		/**
		 * @return
		 */
		public String getMotivo() {
			return motivo;
		}

		/**
		 * @return
		 */
		public String getNroAbono() {
			return nroAbono;
		}

		/**
		 * @return
		 */
		public String getUsuarioTesorero() {
			return usuarioTesorero;
		}

		/**
		 * @param string
		 */
		public void setMonto(String string) {
			monto = string;
		}

		/**
		 * @param string
		 */
		public void setMotivo(String string) {
			motivo = string;
		}

		/**
		 * @param string
		 */
		public void setNroAbono(String string) {
			nroAbono = string;
		}

		/**
		 * @param string
		 */
		public void setUsuarioTesorero(String string) {
			usuarioTesorero = string;
		}

		/**
		 * @return
		 */
		public String getConcepto() {
			return concepto;
		}

		/**
		 * @param string
		 */
		public void setConcepto(String string) {
			concepto = string;
		}

		/**
		 * @return
		 */
		public String getOperador() {
			return operador;
		}

		/**
		 * @param string
		 */
		public void setOperador(String string) {
			operador = string;
		}

}
