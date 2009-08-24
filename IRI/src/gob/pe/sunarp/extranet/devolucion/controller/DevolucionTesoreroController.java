/*
 * Created on Jan 19, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.devolucion.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboConsumo;
import gob.pe.sunarp.extranet.dbobj.DboConsumoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboDireccion;
import gob.pe.sunarp.extranet.dbobj.DboObjetoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboRegisPublico;
import gob.pe.sunarp.extranet.dbobj.DboSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboTaSoliDevo;
import gob.pe.sunarp.extranet.devolucion.SolicitudDevolucion;
import gob.pe.sunarp.extranet.devolucion.bean.SolicitudDevolucionBean;
import gob.pe.sunarp.extranet.devolucion.util.HelperPagarDevolucion;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.publicidad.certificada.ObjetoSolicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.PagoBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.SolicitanteBean;
import gob.pe.sunarp.extranet.util.Constantes;
//import gob.pe.sunarp.extranet.util.DTEGeneratorHelper;
import gob.pe.sunarp.extranet.util.FechaUtil;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;


/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DevolucionTesoreroController extends ControllerExtension implements Constantes{
	private String thisClass = DevolucionController.class.getName() + ".";

		public DevolucionTesoreroController() {
			super();
			addState(new State("buscaSolicitudDev", "Busca Solicitud devolucion"));
			addState(new State("muestraFormularioBusquedaDev", "Busca Solicitud devolucion"));
			addState(new State("pagar", "Paga una solicitud de devolucion"));
			setInitialState("muestraFormularioBusquedaDev");
		}
	public String getTitle() {
			return new String("DevolucionController");
		}

		
	
	/*
	 * LSD - realiza la busqueda
	 */
	protected ControllerResponse runBuscaSolicitudDevState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
		
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			String estado = req.getParameter("estado");
			StringBuffer feci = new StringBuffer(request.getParameter("diainicio"));
			feci.append("/").append(request.getParameter("mesinicio")).append("/").append(request.getParameter("anoinicio")).append(" 00:00:00");
			String fechaInicio = FechaUtil.stringTimeToOracleString(feci.toString());

			StringBuffer fecf = new StringBuffer(request.getParameter("diafin"));
			fecf.append("/").append(request.getParameter("mesfin")).append("/").append(request.getParameter("anofin")).append(" 23:59:59");
			String fechaFin = FechaUtil.stringTimeToOracleString(fecf.toString());		
			
			DboTaSoliDevo dboSolDev= new DboTaSoliDevo();
			dboSolDev.setConnection(dconn);
			if(!estado.equals("T")){
				dboSolDev.setField(DboTaSoliDevo.CAMPO_ESTA,estado);
			}
			
			//dboSolDev.setField(DboTaSoliDevo.CAMPO_OFIC_REG_ID, usuario.getOficRegistralId());
			dboSolDev.setField(DboTaSoliDevo.CAMPO_REG_PUB_ID, usuario.getRegPublicoId());
			dboSolDev.setAppendWhereClause(DboTaSoliDevo.CAMPO_FE_SOLI + " BETWEEN " + fechaInicio + " AND " + fechaFin + " AND " + DboTaSoliDevo.CAMPO_ESTA + "<> '0'");
			List listaConsulta = dboSolDev.searchAndRetrieveList(DboTaSoliDevo.CAMPO_FE_SOLI);
			List listaSolicitud = new ArrayList();
			DboTaSoliDevo solDevolucion = null;
			SolicitudDevolucionBean solicitud = null;
			Iterator iterator = listaConsulta.iterator();
			while(iterator.hasNext()) {
				solDevolucion = (DboTaSoliDevo) iterator.next();
				solicitud = new SolicitudDevolucionBean();
				//codigos de la solicitud de devolucion
				solicitud.setIdSolicitudDev(solDevolucion.getField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO));
				solicitud.setSolicitudId(solDevolucion.getField(DboTaSoliDevo.CAMPO_SOLICITUD_ID));
				//estado
				String strEstado = solDevolucion.getField(DboTaSoliDevo.CAMPO_ESTA);
				if(!strEstado.trim().equals(""))
				switch(strEstado.charAt(0)) {
					case '0': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_REGISTRADO); break;
					case '1': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_CON_INFORME); break;
					case '2': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_CON_RESOLUCION); break;
					case '3': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_PAGADO); break;
				}
				//fecha de solicitud
				solicitud.setFechaSolicitud(FechaUtil.expressoDateToUtilDate(solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_SOLI)));
				
				//anio de la hoja de tramite, numero de la hoja de tramite y fecha de tramite
				//pueden ser vacios
				String anioTramite = solDevolucion.getField(DboTaSoliDevo.CAMPO_AA_TRAM);
				String nroHojaTramite = solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_TRAM);
				String fechaTramite = solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_TRAM);
				String nroInforme = solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_INFO);
				String fechaInforme = solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_INFO);
				String nroResolucion = solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_RESO);
				String cuentaId = solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID);
				String cuentaIdDev = solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
				solicitud.setAnoHojaTramite(anioTramite.equals("") ? "----" : anioTramite);
				solicitud.setNumHojaTramite(nroHojaTramite.equals("") ? null : nroHojaTramite);
				solicitud.setFechaTramite(fechaTramite.equals("") ? "----" : FechaUtil.expressoDateToUtilDate(fechaTramite).replace('/','.'));
				solicitud.setNumInforme(nroInforme.equals("") ? null : nroInforme);
				solicitud.setFechaInforme(fechaInforme.equals("") ? "---" : FechaUtil.expressoDateToUtilDate(fechaInforme));
				solicitud.setNumResolucion(nroResolucion.equals("") ? null : nroResolucion);
				solicitud.setCuentaId(cuentaId.equals("") ? null : cuentaId);
				
				//seteamos el nombre
				if(!cuentaId.equals("")){
					DboCuenta dboCuenta = new DboCuenta();
					dboCuenta.setConnection(dconn);
					dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID, cuentaId);
					dboCuenta.find();
					DboPeNatu penatu= new DboPeNatu();
					penatu.setConnection(dconn);
					penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, dboCuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
					if(penatu.find())
						solicitud.setNombre(penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+ penatu.getField(DboPeNatu.CAMPO_APE_MAT)+" "+penatu.getField(DboPeNatu.CAMPO_NOMBRES));
				}
				if(!cuentaIdDev.equals("")){
					DboCuenta dboCuenta = new DboCuenta();
					dboCuenta.setConnection(dconn);
					dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID, cuentaIdDev);
					dboCuenta.find();
					DboPeNatu penatu= new DboPeNatu();
					penatu.setConnection(dconn);
					penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, dboCuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
					if(penatu.find())
						solicitud.setNombre(penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+ penatu.getField(DboPeNatu.CAMPO_APE_MAT)+" "+penatu.getField(DboPeNatu.CAMPO_NOMBRES));
				}

				listaSolicitud.add(solicitud);
			}
			
			req.setAttribute("lstSolDevolucion",listaSolicitud);
			if(listaSolicitud.size()>0){
				req.setAttribute("contador1","x");
			} else {
				req.setAttribute("contador0","x");
			}
			/** Mantener parametros de busqueda**/
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", req.getParameter("diainicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", req.getParameter("mesinicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", req.getParameter("anoinicio"));

			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1",  req.getParameter("diafin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1",  req.getParameter("mesfin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1",   req.getParameter("anofin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("estado",   req.getParameter("estado"));
		
			response.setStyle("buscaSolDevolucion");

		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}

		return response;

	}
	
	/*
	 * Muestra el filtro
	 */
	protected ControllerResponse runMuestraFormularioBusquedaDevState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		try{
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			init(request);
			validarSesion(request);
		
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);			
			Calendar cal=Calendar.getInstance();
	
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", String.valueOf(cal.get(Calendar.MONTH)+1));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1",  String.valueOf(cal.get(Calendar.YEAR)));

			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", String.valueOf(cal.get(Calendar.MONTH)+1));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", String.valueOf(cal.get(Calendar.YEAR)));
		
			response.setStyle("muestraBuscarSolDevolucion");

		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
		
			response.setStyle(e.getForward());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
			//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);						
			end(request);
		}
		return response;
	}

	
	/*
	 * LSD - Pagar
	 */
	protected ControllerResponse runPagarState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
	
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			String solicitudId = req.getParameter("solicitudId");//Puede ser vacio
			String idSolicitudDevolucion = req.getParameter("hidNumSolDev");//PK de la tabla
			
			//si existe un idSolicitud recuperamos al solicitud, caso contrario es un usario que se da de baja
			String montoAbono = "";
			String nroSolicitud = "";
			String fechaSolicitud = "";
			if(!solicitudId.equals("")){
				Solicitud sol = new Solicitud(solicitudId, conn);
				montoAbono = sol.getTotal();
				nroSolicitud = sol.getSolicitud_id();
				fechaSolicitud = sol.getTs_crea();
			}
			
			//recuperamos el CUENTA_ID_DEV 
			String cuentaIdDevolucion = null;
			DboTaSoliDevo dboTaSoliDevo = new DboTaSoliDevo(dconn);
			dboTaSoliDevo.setFieldsToRetrieve(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
			dboTaSoliDevo.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO, idSolicitudDevolucion);
			if(dboTaSoliDevo.find()){
				cuentaIdDevolucion = dboTaSoliDevo.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
			} 
			
			String idSession=req.getSession().getId();
			String ipRemota=req.getRemoteAddr();
			HelperPagarDevolucion.pagarDevolucion(idSolicitudDevolucion, 
												ipRemota, 
												idSession,
												montoAbono, 
												nroSolicitud,
												fechaSolicitud,
												dconn);
			
			dconn.commit();
			req.setAttribute("mensaje", "Se realizo el pago de la devolucion exitosamente");
			transition("buscaSolicitudDev", request, response);
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}			
	
}
