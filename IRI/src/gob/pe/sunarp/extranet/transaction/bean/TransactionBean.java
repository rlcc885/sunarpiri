package gob.pe.sunarp.extranet.transaction.bean;


import com.jcorporate.expresso.core.controller.ControllerRequest;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;




public class TransactionBean extends SunarpBean{
	
//	public class TransactionBean extends LogAuditoriaCertificadoBean{	

	//Inicio:mgarate:20/11/2007
	private long numeroOperacion;//mgarate:20/11/2007
	private String codigoTipoPago;
	private double costoTotal;
	//Fin:mgarate
	private int codigoServicio;
	//Tarifario
	private int codigoGLA;//Codigo de criterio
	
	private String remoteAddr;
	private UsuarioBean usuarioSession;

	//para controlar q no se cobre cuando falla una partida
	private String flagCobro;
    
	/**
	 * inicio:dbravo:27/07/2007
	 * descripcion: Creado para la busqueda de Publicidad Masiva Relacional y certificados crem's, la variable guarda la cantidad de registros 
	 * 				que se retorno de la busqueda.	
	 */
	private long cantidadRegistros;
	/**
	 * fin:dbravo:27/07/2007	
	 */
	/**
	 * inicio:dbravo:13/08/2007
	 * descripcion: El identificador de la cuenta del solicitante, este se cuando se verifica un certificado CREM	
	 */
	private String cuentaIdSolicitante;
	/**
	 * fin:dbravo:13/08/2007	
	 */
        //Modificado por: Proyecto Filtros de Acceso
        //Fecha: 02/10/2006
        private String idSesion;
        
        /**
         * Gets the idSesion
         * @return Returns a String
         */
        public String getIdSesion() {
                return idSesion;
        }
        /**
         * Sets the idSesion
         * @param idSesion The idSesion to set
         */
        public void setIdSesion(String idSesion) {
                this.idSesion = idSesion;
        }
        
        //Fin Modificación	
	/**
	 * Gets the codigoServicio
	 * @return Returns a int
	 */
	public int getCodigoServicio() {
		return codigoServicio;
	}
	/**
	 * Sets the codigoServicio
	 * @param codigoServicio The codigoServicio to set
	 */
	public void setCodigoServicio(int codigoServicio) {
		this.codigoServicio = codigoServicio;
	}


	/**
	 * Gets the usuarioSession
	 * @return Returns a UsuarioBean
	 */
	public UsuarioBean getUsuarioSession() {
		return usuarioSession;
	}
	/**
	 * Sets the usuarioSession
	 * @param usuarioSession The usuarioSession to set
	 */
	public void setUsuarioSession(UsuarioBean usuarioSession) {
		this.usuarioSession = usuarioSession;
	}


	/**
	 * Gets the remoteAddr
	 * @return Returns a String
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}
	/**
	 * Sets the remoteAddr
	 * @param remoteAddr The remoteAddr to set
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}


	/**
	 * Gets the flagCobro
	 * @return Returns a String
	 */
	public String getFlagCobro() {
		return flagCobro;
	}
	/**
	 * Sets the flagCobro
	 * @param flagCobro The flagCobro to set
	 */
	public void setFlagCobro(String flagCobro) {
		this.flagCobro = flagCobro;
	}


	/**
	 * Gets the codigoGLA
	 * @return Returns a int
	 */
	public int getCodigoGLA() {
		return codigoGLA;
	}
	/**
	 * Sets the codigoGLA
	 * @param codigoGLA The codigoGLA to set
	 */
	public void setCodigoGLA(int codigoGLA) {
		this.codigoGLA = codigoGLA;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the cantidadRegistros
	 */
	public long getCantidadRegistros() {
		return cantidadRegistros;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param cantidadRegistros the cantidadRegistros to set
	 */
	public void setCantidadRegistros(long cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 13, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the cuentaIdSolicitante
	 */
	public String getCuentaIdSolicitante() {
		return cuentaIdSolicitante;
	}
	/**
	 * @autor dbravo
	 * @fecha Aug 13, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param cuentaIdSolicitante the cuentaIdSolicitante to set
	 */
	public void setCuentaIdSolicitante(String cuentaIdSolicitante) {
		this.cuentaIdSolicitante = cuentaIdSolicitante;
	}
	//Inicio:mgarate:20/11/2007
	public String getCodigoTipoPago() {
		return codigoTipoPago;
	}
	public void setCodigoTipoPago(String codigoTipoPago) {
		this.codigoTipoPago = codigoTipoPago;
	}
	public long getNumeroOperacion() {
		return numeroOperacion;
	}
	public void setNumeroOperacion(long numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}
	public double getCostoTotal() {
		return costoTotal;
	}
	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	//Fin:mgarate
}


