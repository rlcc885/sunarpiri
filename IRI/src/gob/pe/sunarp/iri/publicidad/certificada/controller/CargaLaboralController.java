package gob.pe.sunarp.iri.publicidad.certificada.controller;

import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboObjetoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboTaSoliDevo;
import gob.pe.sunarp.extranet.dbobj.DboTmLibro;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.bean.DetalleRjbBean;
import gob.pe.sunarp.extranet.publicidad.certificada.AdministradorCargaLaboral;
//import gob.pe.sunarp.extranet.publicidad.certificada.Atencion;
import gob.pe.sunarp.extranet.publicidad.certificada.Certificado;
import gob.pe.sunarp.extranet.publicidad.certificada.MonitorCargaLaboral;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.*;
import gob.pe.sunarp.extranet.publicidad.service.VerificaCremCondicionadoService;
import gob.pe.sunarp.extranet.publicidad.service.VerificaCremHistoricoService;
import gob.pe.sunarp.extranet.publicidad.service.VerificaGravamenRmcService;
import gob.pe.sunarp.extranet.publicidad.service.VerificaPositivoRmcService;
import gob.pe.sunarp.extranet.publicidad.service.VerificaRjbService;
import gob.pe.sunarp.extranet.publicidad.service.VerificaVigenciaRmcService;
import gob.pe.sunarp.extranet.publicidad.service.impl.VerificaCremCondicionadoServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.VerificaCremHistoricoServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.VerificaGravamenRmcServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.VerificaPositivoRmcServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.VerificaRjbServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.VerificaVigenciaRmcServiceImpl;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultarPartidaDirectaSQLImpl;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaAtencionSolicitudBean;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaVerificaCremBean;
import gob.pe.sunarp.extranet.util.*;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class CargaLaboralController extends ControllerExtension implements Constantes{
	private String thisClass = CargaLaboralController.class.getName() + ".";

	public CargaLaboralController() {
		super();
		addState(new State("buscaSolicitud", "Muestra la ventana de busqueda de Solicitudes"));
		addState(new State("muestraSolicitud", "Muestra los resultados de la busqueda por numero de solicitud"));
		addState(new State("detalleSolicitud", "Muestra el detalle de solicitud"));
		addState(new State("verCertificadosNegativo", "Muestra el detalle para tipo de certificado verificado"));
		addState(new State("certificadoVerficado", "Guarda los datos del certificado verificado"));
		addState(new State("certificadoExpedido", "Guarda los datos del certificado Expedido"));
		//addState(new State("buscaEstadoSolicitud", "Muestra la ventana de busqueda por Estado de la solicitud"));										
		//addState(new State("muestraEstadoSolicitud", "Muestra los resultados de la busqueda por numero de solicitud"));				
		addState(new State("despachaSolicitud", "Despacha un conjunto de Solicitudes"));
		addState(new State("exportarSolicitud", "Exporta un conjunto de Solicitudes por despachar"));
		
		//inicio:dbravo:18/07/2007
		addState(new State("comprobante", "Muestra el comprobante del saldo faltante - CREM"));
		//fin:dbravo:18/07/2007
		
		setInitialState("buscaSolicitud");
	}


	public String getTitle() {
		return new String("CargaLaboralController");
	}
	
	protected ControllerResponse runBuscaSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);			
			FechaBusquedaBean fechabusqBean = new FechaBusquedaBean();
			fechabusqBean.almacenaFechaHoy();
			
			/****** muestra la ventana busqueda solicitud, por defecto aparecen las solicitudes PENDIENTES ******/
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			//res.setHeader("ETag", etag);
			String stipobusq;
			String sNumSol;
			String sRangoFecha; 
			String sEstadoSol;
			String sRefrescaAuto;
			String tipoEnv;
			StringBuffer desde = new StringBuffer();		
			StringBuffer hasta = new StringBuffer();
			Solicitud solicitud = new Solicitud();			
			ArrayList arrbuscaList = new ArrayList();
			int pagina=1;
			int tamano=1;
			
			PaginacionBean paginacionBean = new PaginacionBean();
			
			//seteo los datos de Paginacion
			paginacionBean.setNum_pagina(Propiedades.getInstance().getLineasPorPag());
			paginacionBean.setPaginacion(pagina);
			paginacionBean.setTamano(tamano);
			
			//Recupero el tipo de busqueda			
			stipobusq = "E";
			sRefrescaAuto = null;
			//Seteo la conexion
			solicitud.setConn(conn);			
			solicitud.setPaginacBean(paginacionBean);			
				
				//recupero el valor del estado, por defecto es 01:Pendientes
				sEstadoSol = "01";								
				sRangoFecha = null;
				tipoEnv = "";
				//busqueda de la solicitud solo por estado					
				arrbuscaList = solicitud.recuperarXEstadoSol(sEstadoSol, userBean.getCuentaId(), null,null,tipoEnv);
 				manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());
				 
				//envio los datos de la busqueda por Estado de la solicitud
				req.setAttribute("sEstadoSol",sEstadoSol);
				req.setAttribute("sRangoFecha",sRangoFecha);
			
			//envio el bean con los  campos rango de fecha 
			req.setAttribute("fechabusqBean",fechabusqBean);
			//encontro registros q mostrar
			req.setAttribute("encontro", paginacionBean.getEncontro());
			//envio datos generales			
			req.setAttribute("stipobusq",stipobusq);
			//if(arrbuscaList.size()>0)
			req.setAttribute("arrResultado", arrbuscaList);
			req.setAttribute("tam",""+arrbuscaList.size());
			req.setAttribute("sRefrescaAuto", sRefrescaAuto);
			req.setAttribute("refreshTime",Tarea.getValorParametro(dconn,Constantes.PARAM_REFRESH+""));
			/***** fin ****/
			
			response.setStyle("buscaSolicitud");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
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
	
	
	
protected ControllerResponse runMuestraSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	HttpServletResponse res = (HttpServletResponse) ((ServletControllerRequest) request).getServletResponse();
	boolean exito = false;
	try{
		init(request);
		validarSesion(request);			
		UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
		conn = pool.getConnection();
		conn.setAutoCommit(false);
		DBConnection dconn = new DBConnection(conn);
		
		//verificar si hubo cambios en la carga laboral
		boolean cache = false;
		String etag = new StringBuffer(userBean.getCuentaId()).append("1").append("100").toString();
		if(!MonitorCargaLaboral.getInstance().getEstadoCargaRegistrador(userBean.getCuentaId())){
			if (isTrace(this)) trace("Revisamos si se puede sacar de cache del browser", request);
	/*		String modifiedSince = req.getHeader("If-Modified-Since");
			trace("Header If-Modified-Since: " + modifiedSince, request);*/
			String noneMatch = req.getHeader("ETag");
			if (isTrace(this)) trace("Header ETag: " + noneMatch, request);
			if ((noneMatch != null) && (noneMatch.equals(etag))) cache = true;
		}
		if(cache){
			if (isTrace(this)) trace("Ya fue visto y se puede sacar de cache", request);
			res.setStatus(res.SC_NOT_MODIFIED);
			exito = true; 
		} else {
			res.setHeader("ETag", etag);
			String stipobusq;
			String sNumSol;
			String sRangoFecha; 
			String sEstadoSol;
			String sRefrescaAuto;
			String tipoEnv;
			StringBuffer desde = new StringBuffer();		
			StringBuffer hasta = new StringBuffer();
			Solicitud solicitud = new Solicitud();			
			ArrayList arrbuscaList = new ArrayList();
			FechaBusquedaBean fechaBusqBean = new FechaBusquedaBean();
			PaginacionBean paginacionBean = new PaginacionBean();
			
			//seteo los datos de Paginacion
			paginacionBean.setNum_pagina(Propiedades.getInstance().getLineasPorPag());
			paginacionBean.setPaginacion(Integer.parseInt(request.getParameter("pagina")));
			paginacionBean.setTamano(Integer.parseInt(request.getParameter("tamano")));
			
			//Recupero el tipo de busqueda			
			stipobusq = request.getParameter("radTipBusq");			
			sRefrescaAuto = request.getParameter("chkRefrescaAuto");
			//Seteo la conexion
			solicitud.setConn(conn);			
			solicitud.setPaginacBean(paginacionBean);
			//seteo la fecha de hoy
			fechaBusqBean.almacenaFechaHoy();
			if (stipobusq.equals("N")){
				//busqueda por numero de solicitud
				sNumSol = request.getParameter("txtnumSol");
				if(sNumSol==null || sNumSol.equals(""))
					throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el Número de Solicitud a buscar");											
				arrbuscaList =  solicitud.recuperarXNroSol(sNumSol, userBean.getCuentaId());						
				req.setAttribute("sNumSol",sNumSol);
				 		
			}else{
				//busqueda por estado de la solicitud
				//recupero el valor del estado, por defecto es 01:Pendientes
				sEstadoSol = request.getParameter("cboEstadoSol");								
				sRangoFecha = request.getParameter("chkRangoFecha");
				if(request.getParameter("cboEnvio")==null)
				{
					tipoEnv = "";
				}
				else
				{
					tipoEnv = request.getParameter("cboEnvio");
					req.setAttribute("cboEnvio",tipoEnv);
				}
				if (sRangoFecha==null){
					//busqueda de la solicitud solo por estado					
					arrbuscaList = solicitud.recuperarXEstadoSol(sEstadoSol, userBean.getCuentaId(), null,null,tipoEnv);
 					manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());
 					//setAttribute("numeroderegistros", String.valueOf(tamano));
				}else{
					//busqueda por rango de fechas					
					desde.append(request.getParameter("cboanoinicio")).append("/").append(request.getParameter("cbomesinicio")).append("/").append(request.getParameter("cbodiainicio"));
					hasta.append(request.getParameter("cboanofin")).append("/").append(request.getParameter("cbomesfin")).append("/").append(request.getParameter("cbodiafin"));
					//busqueda de la solicitud por estado y por rango de fecha
					arrbuscaList = solicitud.recuperarXEstadoSol(sEstadoSol,userBean.getCuentaId(), desde.toString(),hasta.toString(),tipoEnv);
					manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());
					
					fechaBusqBean.setDia_inicio(request.getParameter("cbodiainicio"));
					fechaBusqBean.setMes_inicio(request.getParameter("cbomesinicio"));
					fechaBusqBean.setAnno_inicio(request.getParameter("cboanoinicio"));
					fechaBusqBean.setDia_final(request.getParameter("cbodiafin"));
					fechaBusqBean.setMes_final(request.getParameter("cbomesfin"));
					fechaBusqBean.setAnno_final(request.getParameter("cboanofin"));					
													
				} 
				//envio los datos de la busqueda por Estado de la solicitud
				req.setAttribute("sEstadoSol",sEstadoSol);
				req.setAttribute("sRangoFecha",sRangoFecha);
			}
			//envio el bean con los  campos rango de fecha 
			req.setAttribute("fechabusqBean",fechaBusqBean);
			//encontro registros q mostrar
			req.setAttribute("encontro", paginacionBean.getEncontro());
			//envio datos generales			
			req.setAttribute("stipobusq",stipobusq);
			if(arrbuscaList.size()==0)
				req.setAttribute("mensaje","No se obtuvieron resultados");
			req.setAttribute("arrResultado", arrbuscaList);
			req.setAttribute("tam",""+arrbuscaList.size());
			req.setAttribute("sRefrescaAuto", sRefrescaAuto);
			req.setAttribute("refreshTime",Tarea.getValorParametro(dconn,Constantes.PARAM_REFRESH+""));
			response.setStyle("muestraCargaLaboral");
		}
	
	conn.commit();
			
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
protected ControllerResponse runExportarSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			BuscaCargaLaboralBean buscaBean = null;
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			String tipoEnv;
			String[] solIds;
			
			//hphp:29/10
			//solIds =  req.getParameterValues("solId");
			solIds =  req.getParameterValues("solicitud");
			if(request.getParameter("cboEnvio")==null)
			{
				tipoEnv = "";
			}
			else
			{
				tipoEnv = request.getParameter("cboEnvio");
				
			}
			
			Solicitud solicitud = new Solicitud();			
			ArrayList arrbuscaList = new ArrayList();
			//Seteo la conexion
			solicitud.setConn(conn);
			
			//busqueda por estado de la solicitud
			//recupero el valor del estado, por defecto es 01:Pendientes
			
			arrbuscaList = solicitud.recuperarExportaSol(solIds, userBean.getCuentaId(), tipoEnv);
			//String glosa = Tarea.getValorParametro(dconn,"" + Constantes.PARAM_GLOSA);						
			
			//hphp:30/10
			String fileName=Propiedades.getInstance().getRutaModeloSobre();
			Hashtable values = new Hashtable();
			//
			
			//Envio para download
			String fname = "despacho.txt";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/txt; charset=utf-8");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;
			/*
			StringBuffer stb = new StringBuffer();
			//hphp:29/10
			//stb.append("---------------------------------------------------------------------------------");
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
			*/
			for(int i = 0; i<arrbuscaList.size(); i++)
			{
				//buscaBean = new BuscaCargaLaboralBean();
				buscaBean = (BuscaCargaLaboralBean) arrbuscaList.get(i);
				//hphp:30/10
				values.put("[NO_SOLICITUD]",buscaBean.getSolicitud_id());
				values.put("[DESTINATARIO]",buscaBean.getDestiNombre());
				values.put("[DIRECCION1]",
					buscaBean.getDestiDire().length()<=45?buscaBean.getDestiDire():buscaBean.getDestiDire().substring(0,45)
						
				);
				values.put("[DIRECCION2]",
					buscaBean.getDestiDire().length()>45?buscaBean.getDestiDire().substring(45):""
				);
				values.put("[DIRECCION3]",buscaBean.getDestiDpto());
				//
				/*
				stb = new StringBuffer();
				stb.append("Número de Solicitud : ").append(buscaBean.getSolicitud_id());
				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
				stb = new StringBuffer();
				stb.append("Atención            : ").append(buscaBean.getDestiNombre());
				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
				stb = new StringBuffer();
				stb.append("Dirección           : ").append(buscaBean.getDestiDire());
				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
				stb = new StringBuffer();
				stb.append("                      ").append(buscaBean.getDestiDpto());
				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
				stb = new StringBuffer();
				stb.append("	").append(glosa);
				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
				stb = new StringBuffer();
				//hphp:29/10
				//stb.append("---------------------------------------------------------------------------------");
				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
				*/
				BufferedReader reader= new BufferedReader(new FileReader(fileName));
				String linea= reader.readLine();
				StringBuffer lineaImprimir;
				//while(!linea.equals("EOF")) {
				while(linea!=null) {
					int x,y = -1;
					lineaImprimir = new StringBuffer();
					x = linea.indexOf("[",y);
					while(x!=-1){
						lineaImprimir = lineaImprimir.append(linea.substring(y+1,x));
						y = linea.indexOf("]",x);
						lineaImprimir = lineaImprimir.append((String)values.get(linea.substring(x,y+1)));
						x = linea.indexOf("[",y);
					}
					if(y<linea.length())
						lineaImprimir = lineaImprimir.append(linea.substring(y+1));
					out_cliente = res.getWriter();
					out_cliente.println(lineaImprimir.toString());
					linea= reader.readLine();
				}

			}
			out_cliente = res.getWriter();
			out_cliente.println("");
			out_cliente.flush();
			out_cliente.close();
			
			if(!tipoEnv.equals(""))
				req.setAttribute("cboEnvio",tipoEnv);
			
			req.setAttribute("cboEstadoSol",request.getParameter("cboEstadoSol"));
			req.setAttribute("radTipBusq",request.getParameter("radTipBusq"));
			req.setAttribute("chkRefrescaAuto",request.getParameter("chkRefrescaAuto"));
			req.setAttribute("sEstadoSol",request.getParameter("sEstadoSol"));
			req.setAttribute("sRangoFecha",request.getParameter("sRangoFecha"));
			req.setAttribute("refreshTime",Tarea.getValorParametro(dconn,Constantes.PARAM_REFRESH+""));
			response.setCustomResponse(true);
			
			
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
protected ControllerResponse runMuestraEstadoSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			String stipobusq;
			String sNumSol;
			String sRangoFecha; 
			String sEstadoSol;
			String sRefrescaAuto;				
			StringBuffer desde = new StringBuffer();		
			StringBuffer hasta = new StringBuffer();
			
			Solicitud solicitud = new Solicitud();			
			ArrayList arrbuscaList = new ArrayList();
			
			//Recupero el tipo de busqueda			
			stipobusq = request.getParameter("radTipBusq");			
			//Seteo la conexion
			solicitud.setConn(conn);
			if (stipobusq.equals("NU")){
				//busqueda por numero de solicitud
				sNumSol = request.getParameter("txtnumSol");											
				arrbuscaList =  solicitud.recuperarDBXNroSol(sNumSol, userBean.getCuentaId());
			}	
			if (stipobusq.equals("PN")){
				//busqueda por nombre de la persona natural
				sNumSol = request.getParameter("txtnumSol");
				sNumSol = request.getParameter("txtnumSol");
				sNumSol = request.getParameter("txtnumSol");													
				
			}	 		 		
			if (stipobusq.equals("PJ")){
				//busqueda por nobre de la persona juridica
				sNumSol = request.getParameter("txtnumSol");											
				
			}	 		
			if (stipobusq.equals("RF")){
				//busqueda por rango de fechas
				desde.append(request.getParameter("cboanoinicio")).append("/").append(request.getParameter("cbomesinicio")).append("/").append(request.getParameter("cbodiainicio"));
				hasta.append(request.getParameter("cboanofin")).append("/").append(request.getParameter("cbomesfin")).append("/").append(request.getParameter("cbodiafin"));
				//busqueda de la solicitud por rango de fecha
				arrbuscaList = solicitud.recuperarSolxRangoFechas(userBean.getCuentaId(), desde.toString(),hasta.toString());								
			}	 		
			
			//Refresca el jsp
			//sRefrescaAuto = request.getParameter("chkRefrescaAuto");	
			req.setAttribute("stipobusq",stipobusq);
			req.setAttribute("arrResultado", arrbuscaList);			
			response.setStyle("buscaEstadoSolicitud");

			
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
*/	
	
	
protected ControllerResponse runDetalleSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			String sNumSol;					
			String sRegistrador;
		
			//Inicio:mgarate 06-08-2007
			ArrayList resultado = new ArrayList();
			//Fin:mgarate
			//Recupero numero de solicitud			
			sNumSol = request.getParameter("solicitud");
			//Recupero un valor si es registrador
			sRegistrador= request.getParameter("registrador");
			Solicitud solicitud = new Solicitud(sNumSol, conn);			
			/** DESCAJ IFIGUEROA 18/01/2006 INICIO***/
			if (solicitud.getEstado()!=null){
				if(solicitud.getEstado().equalsIgnoreCase(ESTADO_SOL_IMPROCEDENTE)){
					if(userBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT){
						if(solicitud.validarDevolucion(userBean.getCuentaId(),solicitud.getCuenta_id(),dconn)){
							DboTaSoliDevo solDev= new DboTaSoliDevo();
							solDev.setConnection(dconn);
							solDev.setField(DboTaSoliDevo.CAMPO_SOLICITUD_ID,sNumSol);
							solDev.setFieldsToRetrieve(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO);
							if(solDev.find())
								req.setAttribute("devolucion","false");
							else
								req.setAttribute("devolucion","true");
						}
					}else{
						if (userBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && solicitud.getCuenta_id().equalsIgnoreCase(userBean.getCuentaId())){
							DboTaSoliDevo solDev= new DboTaSoliDevo();
							solDev.setConnection(dconn);
							solDev.setField(DboTaSoliDevo.CAMPO_SOLICITUD_ID,sNumSol);
							solDev.setFieldsToRetrieve(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO);
							if(solDev.find())
								req.setAttribute("devolucion","false");
							else
								req.setAttribute("devolucion","true");
						}
						
					}
					
				}else if(solicitud.getEstado().equalsIgnoreCase(ESTADO_SOL_POR_VERIFICAR)){
					
					if(solicitud.getObjetoSolicitudList()!=null){
						
						ObjetoSolicitudBean objetoSolicitudBean = (ObjetoSolicitudBean)solicitud.getObjetoSolicitudList().get(0);
						
						if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO) && sRegistrador!=null && sRegistrador.equals("REG")){
							/**
							 * inicio:dbravo:03/08/2007
							 * cc:sunarp-regmobcon-2006
							 * descripción: Si el certificado fuera de tipo CREM Condicionado, se obtendra el comentario.
							 */
							VerificaCremCondicionadoService verificaCremCondicionadoService = new VerificaCremCondicionadoServiceImpl(conn, dconn);
							ConstanciaCremBean constanciaCremBean = verificaCremCondicionadoService.comentarioCertificadoCREMCondicionado(objetoSolicitudBean);
							req.setAttribute("constanciaCrem", constanciaCremBean);
							/**
							 * fin:dbravo:03/08/2007
							 * cc:sunarp-regmobcon-2006
							 */
						}
						else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_COPIA_LITERAL_RMC)){
							
							//Inicio:jascencio:08/08/2007
							//CC:REGMOBCON-2006
							
							if(objetoSolicitudBean.getCod_GLA().equals(Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC)){
								ConsultarPartidaDirectaSQL consultarPartidaDirectaSQL;
								consultarPartidaDirectaSQL = new ConsultarPartidaDirectaSQLImpl(conn,dconn);
								String refNumParAnterior = null;
								refNumParAnterior= consultarPartidaDirectaSQL.obtenerRefNumParAntiguo(objetoSolicitudBean.getRefnum());
								if(refNumParAnterior != null && refNumParAnterior.length() > 0){
									List auxiliarLista=null;
									auxiliarLista = new ArrayList();
									objetoSolicitudBean.setRefNumParAnterior(refNumParAnterior);
									auxiliarLista.add(objetoSolicitudBean);
									solicitud.setObjetoSolicitudList(auxiliarLista);
								}
								
							}	
							//Fin:jascencio
						}
						/**** inicio:jrosas 06-08-07 ***/
						else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) && sRegistrador!=null && sRegistrador.equals("REG")){
							VerificaCremHistoricoService verificaCremHistoricoService = new VerificaCremHistoricoServiceImpl(conn, dconn);
							ConstanciaCremBean constanciaCremBean = verificaCremHistoricoService.comentarioCertificadoCREMHistorico(objetoSolicitudBean);
							req.setAttribute("constanciaCrem", constanciaCremBean);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA) && sRegistrador!=null && sRegistrador.equals("REG")){
							VerificaCremHistoricoService verificaCremActosVigentesService = new VerificaCremHistoricoServiceImpl(conn, dconn);
							ConstanciaCremBean constanciaCremBean = verificaCremActosVigentesService.comentarioCertificadoCREMHistorico(objetoSolicitudBean);
							req.setAttribute("constanciaCrem", constanciaCremBean);
						}
						/**** fin:jrosas 06-08-07 ***/

						
						else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_G))
						{
							/**** Inicio:mgarate: 06-08-07 ***/
						    VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
						    resultado = verificaRjb.recuperarGravamenVehicularRJB(objetoSolicitudBean);
						    req.setAttribute("tipoRegistro", Constantes.REGISTRO_VEHICULAR);
						    req.setAttribute("resultado", resultado);
						    
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_EMBARCACIONES_G))
						{
							VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
							resultado = verificaRjb.recuperarGravamenEmbPesqueraRJB(objetoSolicitudBean);
							req.setAttribute("tipoRegistro", Constantes.REGISTRO_EMBARCACIONES_PESQUERAS);
							req.setAttribute("resultado", resultado);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_BUQUES_G))
						{
							VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
							resultado = verificaRjb.recuperarGravamenBusqueRJB(objetoSolicitudBean);
							req.setAttribute("tipoRegistro", Constantes.REGISTRO_BUQUES);
							req.setAttribute("resultado", resultado);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_AERONAVES_G))
						{
							VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
							resultado = verificaRjb.recuperarGravamenAeronaveRJB(objetoSolicitudBean);
							req.setAttribute("tipoRegistro", Constantes.REGISTRO_AERONAVES);
							req.setAttribute("resultado", resultado);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_D))
						{
							VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
							resultado = verificaRjb.recuperarDominialVehicularRJB(objetoSolicitudBean);
							req.setAttribute("tipoRegistro", Constantes.REGISTRO_VEHICULAR);
							req.setAttribute("resultado", resultado);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_EMBARCACIONES_D))
						{
							VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
							resultado = verificaRjb.recuperarDominialEmbPesqueraRJB(objetoSolicitudBean);
							req.setAttribute("tipoRegistro", Constantes.REGISTRO_EMBARCACIONES_PESQUERAS);
							req.setAttribute("resultado", resultado);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_BUQUES_D))
						{
							VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
							resultado = verificaRjb.recuperarDominialBuqueRJB(objetoSolicitudBean);
							req.setAttribute("tipoRegistro", Constantes.REGISTRO_BUQUES);
							req.setAttribute("resultado", resultado);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_AERONAVES_D))
						{
							VerificaRjbService verificaRjb = new VerificaRjbServiceImpl(conn, dconn);
							resultado = verificaRjb.recuperarDominialAeronaveRJB(objetoSolicitudBean);
							req.setAttribute("tipoRegistro", Constantes.REGISTRO_AERONAVES);
							req.setAttribute("resultado", resultado);
							/**** Fin:mgarate ***/
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS)){
							/**** inicio: ifigueroa 09-08-07 ***/
							VerificaPositivoRmcService verificaPositivoRmcService = new VerificaPositivoRmcServiceImpl(conn, dconn);
							ArrayList constanciaRMCBean = verificaPositivoRmcService.constanciaTituloCertificadoPositivoRmc(objetoSolicitudBean);
							req.setAttribute("constanciaRMC", constanciaRMCBean);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_VIGENCIA)){
							
							VerificaVigenciaRmcService verificaVigenciaRmcService = new VerificaVigenciaRmcServiceImpl(conn, dconn);
							ArrayList constanciaRMCBean = verificaVigenciaRmcService.constanciaTituloCertificadoVigenciaRmc(objetoSolicitudBean);
							req.setAttribute("constanciaVigenciaRMC", constanciaRMCBean);
							
						}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_GRAVAMEN)){
							
							VerificaGravamenRmcService verificaGravamenRmcService = new VerificaGravamenRmcServiceImpl(conn, dconn);
							ArrayList constanciaRMCBean = verificaGravamenRmcService.constanciaTituloCertificadoGravamenRmc(objetoSolicitudBean);
							req.setAttribute("constanciaGravamenRMC", constanciaRMCBean);
							/**** fin: ifigueroa 09-08-07 ***/
							
						}
									
							
						
					}
					
				}else if(solicitud.getEstado().equalsIgnoreCase(ESTADO_SOL_POR_DESPACHAR)){
					
					ObjetoSolicitudBean objetoSolicitudBean = (ObjetoSolicitudBean)solicitud.getObjetoSolicitudList().get(0);
					
					if( objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO) ||
						objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
						objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)){
						/**
						 * inicio:dbravo:03/08/2007
						 * cc:sunarp-regmobcon-2006
						 * descripción: Si el certificado fuera de tipo CREM Condicionado, se obtendra el comentario.
						 */
						Certificado certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_EXPEDIR);
						req.setAttribute("certificado", certificado);
						
						/**
						 * fin:dbravo:03/08/2007
						 * cc:sunarp-regmobcon-2006
						 */
					}
					
					
				}else if(solicitud.getEstado().equalsIgnoreCase(ESTADO_SOL_DESPACHADA)){
					
					ObjetoSolicitudBean objetoSolicitudBean = (ObjetoSolicitudBean)solicitud.getObjetoSolicitudList().get(0);
					
					if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)||
							objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
							objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)){
						/**
						 * inicio:dbravo:24/08/2007
						 * cc:sunarp-regmobcon-2006
						 * descripción: Si el certificado fuera de tipo CREM Condicionado, se obtendra el comentario.
						 */
						Certificado certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_EXPEDIR);
						req.setAttribute("certificado", certificado);
						
						/**
						 * fin:dbravo:24/08/2007
						 * cc:sunarp-regmobcon-2006
						 */
					}
					
					
				}
				
			}
			/** DESCAJ IFIGUEROA 18/01/2006 FIN***/			
			req.setAttribute("Solicitud", solicitud);
			req.setAttribute("Registrador", sRegistrador);
			//req.setAttribute("Usuario",userBean);			
			response.setStyle("detallesolicitud");
			
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
	
protected ControllerResponse runVerCertificadosNegativoState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			String sNumSol;					
			String tipo_cert;
			String estado_cert;
			
			//Inicio:mgarate:08-08-2007
				DetalleRjbBean detalle = new DetalleRjbBean();
			//Fin:mgarate
			
			//Recupero numero de solicitud			
			sNumSol = request.getParameter("sol_id");
			//recupero el tipo de certificado Negativo o Positivo
			tipo_cert = request.getParameter("Tipo_Cert");
			//recupero el estado del certificado: Por verficar o Vista preliminar:C
			estado_cert = request.getParameter("EstadoCertificado");
			
			/**
			 * inicio:dbravo:06/08/2007
			 * descripcion: Se invoca a los datos de la solicitud, para validar el id del certificado
			 */
			Solicitud solicitud = new Solicitud(sNumSol, conn);	
			ObjetoSolicitudBean objetoSolicitudBean = (ObjetoSolicitudBean)solicitud.getObjetoSolicitudList().get(0);
			/**
			 * fin:dbravo:06/08/2007
			 */
			
			Certificado certificado = null;
			if (estado_cert.equals(Constantes.ESTADO_SOL_POR_EXPEDIR)){
				certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_EXPEDIR);
				/**** inicio: jrosas 17-08-07 **/
				if (certificado.getConstancia2()!= null){
					StringBuffer constaux= new StringBuffer();
					constaux.append("");
					constaux.append(certificado.getConstancia2().toString());
					certificado.setConstancia2(constaux);// recupera constancia 2 de la base de datos con formato
				}
				/**** fin: jrosas 17-08-07 **/
			}	
			else
			{
				certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_VERIFICAR);
				//hphp:29/10
				certificado.setConstancia(""+request.getParameter("Constancia"));
			}

			certificado.setTpo_certificado(tipo_cert);
			// actualizo el registro que se encuentra en certificado "V"
			/*if (estado_cert.equals(Constantes.ESTADO_SOL_POR_EXPEDIR)){
				certificado.actualizarEstadoCertExpedido();
			}*/
			/********Inicio: ifigueroa 09/08/2007*******/
			if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS) || objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_VIGENCIA) || objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_GRAVAMEN)){
				DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
				dboOficinaRegistral.find();
				certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
			}
			
			/********Fin: ifigueroa 09/08/2007*******/
			req.setAttribute("Certificado", certificado);
			// Inicio:mgarate:05/06/2007
			// para los demas certificados que usen el mismo jsp
			// agregar un else if para sus condicionales
			if(tipo_cert.equals(Constantes.CERTIFICADO_BUSQUEDA))
			{ 
				ArrayList listResultado;
				CriterioBean critBean = new CriterioBean();
				critBean = Tarea.recuperarCriterio(certificado.getUrl_busq());
				String flag = critBean.getFlagmetodo();
				if(flag.equals("1") || flag.equals("2") || flag.equals("3") || flag.equals("4") || flag.equals("41"))
				{
					req.setAttribute("tipoBusqueda","D"); 
				}else
				{
					req.setAttribute("tipoBusqueda","I"); 
				}
				listResultado = Tarea.seleccionarBusqueda(certificado.getUrl_busq(),critBean.getFlagmetodo(), conn);
				//Inicio:mgarate:28/09/2007
				DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
				dboOficinaRegistral.find();
				certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));	
				//Fin:mgarate
				req.setAttribute("flagMetodo",critBean.getFlagmetodo());
				req.setAttribute("arrBusqueda",listResultado); 
				response.setStyle("certificadoBusqueda");
			}
			else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)){
				/*** inicio:dbravo:06/08/2007  * cc:sunarp-regmobcon-2006 */
				DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
				dboOficinaRegistral.find();
				certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
				
				ArrayList desTipoRegistro = new ArrayList();
				if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)){
					StringTokenizer st = new java.util.StringTokenizer(objetoSolicitudBean.getTipoRegistro(), ",", false);	
					String flagHistorico = objetoSolicitudBean.getFlagHistorico();
					String valorHistorico="";
					String segundalinea=""; 
					String unalinea="";
					int i=0;
					
					while (st.hasMoreTokens() == true) {
						String codigoLibro = st.nextToken();
						
						if(codigoLibro.equals(Constantes.TIPO_REGISTRO_MOBILIARIO_CONTRATOS)){
							desTipoRegistro.add("Registro Mobiliario de Contratos");
						}
						else{
							if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_VEHICULAR)){
								desTipoRegistro.add("Propiedad Vehicular");
							}
							else{
								if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_EMBARCACION)){
									desTipoRegistro.add("Propiedad Embarcacion Pesquera");
								}
								else{
									if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_BUQUES)){
										desTipoRegistro.add("Propiedad Buques");
									}
									else{
										if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_AERONAVES)){
											desTipoRegistro.add("Propiedad Aeronaves");
										}
										else{
											if(codigoLibro.equals(Constantes.TIPO_PERSONAS_JURIDICAS)){
												desTipoRegistro.add("Personas Jurídicas (Participaciones)");
											}
										}
									}
								}
							}
						}
						
						i++;
					}
					
					int part_num=0;
					int numTipos = desTipoRegistro.size();
					while (part_num < numTipos)
					{  
						 if (part_num >= 4){
						 	if (part_num == 4){
						 	   segundalinea = (String)desTipoRegistro.get(part_num);
						 	}else{
							   segundalinea += "," + (String)desTipoRegistro.get(part_num);
						 	} 
						 }else{
						    if (part_num == 0){
							   unalinea = (String)desTipoRegistro.get(part_num);
							}else{
							   unalinea += "," + (String)desTipoRegistro.get(part_num);
							} 
						 }
						  part_num = part_num+1;
					}	
					
					if (flagHistorico.equals("1")){
						valorHistorico="Histórico";
					}
					req.setAttribute("numTipos", String.valueOf(numTipos));
					req.setAttribute("unalinea", unalinea);
					req.setAttribute("segundalinea", segundalinea);
					req.setAttribute("descFlagHistorico", valorHistorico);
				}
				
				
				response.setStyle("certificadoCremCondicionado");
				/**** fin dbravo *****/
			}
			/*** inicio:jrosas :07-08-07*/
			else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO)){				
				
				DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
				dboOficinaRegistral.find();
				certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
				response.setStyle("certificadoCremHistorico");
				
			}else if (objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)){
				
				DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
				dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
				dboOficinaRegistral.find();
				certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
				response.setStyle("certificadoCremActosVigentes");
				
			}
			/*** fin:jrosas :07-08-07*/
			/**** Inicio:mgarate: 08-08-07 ***/
			else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_G))
				{
				
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
				
				    VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
				    detalle = detalleRjb.recuperarDetalleVehicularRJB(objetoSolicitudBean);
				    req.setAttribute("detalle", detalle);
				    response.setStyle("certificadoRjb");
				   
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_EMBARCACIONES_G))
				{
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
					
					VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
					detalle = detalleRjb.recuperarDetalleEmbPesqueraRJB(objetoSolicitudBean);
					req.setAttribute("detalle", detalle);
					response.setStyle("certificadoRjb");
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_BUQUES_G))
				{
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
					
					VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
					detalle = detalleRjb.recuperarDetalleBuqueRJB(objetoSolicitudBean);
					req.setAttribute("detalle", detalle);
					response.setStyle("certificadoRjb");
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_AERONAVES_G))
				{
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
					
					VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
					detalle = detalleRjb.recuperarDetalleAeronaveRJB(objetoSolicitudBean);
					req.setAttribute("detalle", detalle);
					response.setStyle("certificadoRjb");
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_D))
				{
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
					
					VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
					detalle = detalleRjb.recuperarDetalleVehicularRJB(objetoSolicitudBean);
					req.setAttribute("detalle", detalle);
					response.setStyle("certificadoRjb");
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_EMBARCACIONES_D))
				{
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
					
					VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
					detalle = detalleRjb.recuperarDetalleEmbPesqueraRJB(objetoSolicitudBean);
					req.setAttribute("detalle", detalle);
					response.setStyle("certificadoRjb");
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_BUQUES_D))
				{
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
					
					VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
					detalle = detalleRjb.recuperarDetalleBuqueRJB(objetoSolicitudBean);
					req.setAttribute("detalle", detalle);
					response.setStyle("certificadoRjb");
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_AERONAVES_D))
				{
					DboOficRegistral dboOficinaRegistral= new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,userBean.getOficRegistralId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,userBean.getRegPublicoId());
					dboOficinaRegistral.find();
					certificado.setOfic_reg_id_verif(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
					
					VerificaRjbService detalleRjb = new VerificaRjbServiceImpl(conn, dconn);
					detalle = detalleRjb.recuperarDetalleAeronaveRJB(objetoSolicitudBean);
					req.setAttribute("detalle", detalle);
					response.setStyle("certificadoRjb");
				}						
				/**** Fin:mgarate ***/
			/********Inicio: ifigueroa 09/08/2007*******/
				else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS)){
					if (tipo_cert!=null && tipo_cert.equalsIgnoreCase(Constantes.CERTIFICADO_NEGATIVO))
						response.setStyle("certificadoNegativoRMC");
					else 
						if (tipo_cert!=null && tipo_cert.equalsIgnoreCase(Constantes.CERTIFICADO_POSITIVO))
							response.setStyle("certificadoPositivoRMC");
					
				}else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_VIGENCIA)){
				
					response.setStyle("certificadoVigenciaRMC");
				}
				else if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_GRAVAMEN)){
					response.setStyle("certificadoGravamenRMC");
				}
			/********Fin: ifigueroa 09/08/2007*******/
			else
			{
				response.setStyle("certificadoNegativo");
			}
			/********Inicio: ifigueroa 15/08/2007*******/
			req.setAttribute("objetoSolicitud", objetoSolicitudBean);
			/********Fin: ifigueroa 15/08/2007*******/
			/*********Inicio jrosas 21-08-07 ***/
			req.setAttribute("solicitud", solicitud);
			/*********fin jrosas 21-08-07 ***/
			// Fin:mgarate:05/06/2007
			/*
			if (tipo_cert.equals("N")){
				response.setStyle("CertificadoNegativo");
			}
			if (tipo_cert.equals("P")){
				response.setStyle("CertificadoNoNegativo");
			}*/
						
			//Solicitud solicitud = new Solicitud(sNumSol, conn);									
			//req.setAttribute("Solicitud", solicitud);
			
			
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
	
	
	
protected ControllerResponse runCertificadoVerficadoState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			String sNumSol;					
			String tipo_cert;
			String comentario;
			//hphp:29/10
			String constancia,titulo=null;
			String usrPass;
			String improcedente;
			String mensaje = "Solicitud Verificada Satisfactoriamente";
			String destino = "CargaLaboral.do"; 
			String validaDatosVerifica;
			
			//Recupero numero de solicitud			
			sNumSol = request.getParameter("sol_id");
			
		  /* inicio:jrosas 05-06-2007
		   SUNARP-REGMOBCOM: Modificacion de los estados del certificado  */
		
		   /* recupero el tipo de certificado respuesta, procedente de la evaluacion del verificador en el valor del radio:
		    * por ejemplo el tipo de cert. de respuesta puede ser(I, N,P) y pertenecer al tipo de certificado N (Grupo de 
		    * Cert. de tipo Positivo/Negativo),respuestas (I,G,V) para tipo_cert Gavamen/vigencia:(R),
		    * respuesta (I,M) para tipo_cert de gravamenes:(G), respuesta (I,D) para tipo_cert Dominial:(D),
		    * respuesta (I,A,H,C) para tipo_cert CREM:(C)  */
			
			
			tipo_cert = request.getParameter("radioValor");
			//recupero el comentario agregado por el verificador
			comentario = request.getParameter("Comentario");
			//hphp:29/10:recupero la constancia agregado por el verificador
			//constancia = request.getParameter("Constancia");
			//recupero el password ingresado por el usuario
			usrPass = request.getParameter("usrPass");
			// ingreso titulos para certificado registro de mobiliario de contrato/vigencia/gravamen
			titulo = request.getParameter("txtTitulos");
						
						
			//recupero el estado del certificado: Verificado: V
			//estado_cert = request.getParameter("EstadoCertificado");			
			Certificado certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_VERIFICAR);
			Solicitud solicitud = new Solicitud(sNumSol, conn);
			
			/******* jrosas: 13-06-2007 */
			ObjetoSolicitudBean obj=(ObjetoSolicitudBean)solicitud.getObjetoSolicitudList(0);
			String tipo_certificado=obj.getTpo_cert();
			String cert_id=obj.getCertificado_id();
			
			/**
			 * inicio:dbravo:10/08/2007
			 * descripcion: Si el tipo de certificado es CREM (Vigencia, historico o condicionado),
			 *              se calcula la tarifa por cantidad de lineas.
			 */
			if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO) ||
			   obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO)    ||
			   obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)		   
			 ){
				
				improcedente = request.getParameter("chkImprocedente");
				if (improcedente!=null){
					//recupero el valor del check para la copia literal 
					tipo_cert = Constantes.ESTADO_SOL_IMPROCEDENTE;
				}
				
				if(tipo_cert.equals(Constantes.ESTADO_SOL_IMPROCEDENTE)==false){
					String idSesion = ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request));
					String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
					ComprobanteBean beancomp = descuentaCREM(conn, dconn, userBean, certificado, obj, solicitud, Long.parseLong(request.getParameter("cantidadLineas")), idSesion, ipOrigen);
					
					if(beancomp!=null){
						beancomp.setTipoPub("X");
						ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", beancomp);
						req.setAttribute("comprobante", "comprobanteCREM");
					}
					
					req.setAttribute("objetoSolicitud", obj);
				}
			}		
			/**
			 * fin:dbravo:13/08/2007
			 */
			
			//	recupero la constancia agregado por el verificador
			if (tipo_certificado.equals("N") || tipo_certificado.equals("L")){
				if (tipo_certificado.equals("N")&& cert_id.equals("18")){ // cert. registro mobiliario
					//se seteara dependiendo el tipo de certificado la constancia o constancia2 
					certificado.setConstancia2(new StringBuffer(request.getParameter("Constancia2")));
				}else{ 
					// resto de cert. negativos y copia literal
					constancia = request.getParameter("Constancia");
					//se seteara dependiendo el tipo de certificado la constancia o constancia2 
					certificado.setConstancia(constancia);
				}
					
			}else{ // otros
				certificado.setConstancia2(new StringBuffer(request.getParameter("Constancia2"))); 
			}
			
			/********jrosas: 13-06-2007 *********/
			
			//Atencion atencion = new Atencion();
			AdministradorCargaLaboral adminCargaLab = new AdministradorCargaLaboral();
			
			//para Copia Literal de partida
			if(tipo_cert.equals(Constantes.COPIA_LITERAL)){
				improcedente = request.getParameter("chkImprocedente");
				if (improcedente!=null){
					//recupero el valor del check para la copia literal 
					tipo_cert = Constantes.ESTADO_SOL_IMPROCEDENTE;
				}
				//Para copia literal, verifico si existen registros en las tablas VERIFICA_ASIENTO, VERIFICA_FICHA, VERIFICA_TITU_PEND, VERIFICA_TOMO_FOJA
				validaDatosVerifica = certificado.recuperaDatosVerificados();
				if(validaDatosVerifica==null || validaDatosVerifica.equals(""))
					throw new CustomException(Errors.EC_NOT_FOUND_DATA, "No se encontraron datos de Asientos, Fichas, Tomo en la extranet");											
				
			}
				certificado.setCertificado_id(cert_id);
				certificado.setPassword(usrPass);
				certificado.setTpo_certificado(tipo_cert);
				//certificado.setEstado_certificado(estado_cert);			
				certificado.setComentario(comentario);
				certificado.setTs_verificacion(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));				
				certificado.setUsr_crea(userBean.getUserId());
				certificado.setUsr_modi(userBean.getUserId());
				/**** Inicio: ifigueroa 20/08/2007*****/
				if (titulo==null) titulo="";
					certificado.setTitulo(titulo);
				/**** Fin: ifigueroa 20/08/2007*****/
				certificado.guardarCertificadoVerificado(userBean.getUserId(),usrPass);
				
				
				//***************guarda los datos del certificado				
								
				//valido la opcion de certificado elegido
				if (tipo_cert.equals(Constantes.ESTADO_SOL_IMPROCEDENTE)){
					//actualizo el estado de la solicitud a "I" e inserto un registro por el cambio de solicitud en sgmt_solicitud
					solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_IMPROCEDENTE);
					//inserto un registro de verificacion en atencion
					//atencion.grabarVerificacion(sNumSol, userBean.getCuentaId(), comentario, conn);
					//actualizo el estado de solicitud x carga
					adminCargaLab.actualizarSolxCargaImprocendI(sNumSol,userBean,conn);
					//Inicio:mgarate:22/06/2007
					if(!tipo_certificado.equals("B"))
					{
						/**
						 * @autor: dbravo
						 * @descripcion: Se invalidara la carga laboral del emisor solo cuando el certificado tenga emision
						 */
						if(
							!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS) &&
							!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_VIGENCIA) &&
							!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_GRAVAMEN) 
						  ){
							if(
								!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_G) &&
								!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_BUQUES_G) &&
								!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_AERONAVES_G) &&
								!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_EMBARCACIONES_G) 
							  ){
								 if(
									!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA) &&
									!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) &&
									!cert_id.equalsIgnoreCase(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)
								  ){
									 if(!cert_id.equalsIgnoreCase(String.valueOf(Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC))){
										 adminCargaLab.actualizarSolxCargaImprocendII(sNumSol,userBean,conn);
									 }
								 }
							}
						}
					}
					//Fin:mgarate:22/06/2007
				}else{
					if (tipo_cert.equals("N")|| tipo_cert.equals("P")|| tipo_cert.equals("L")){
						 if (solicitud.getObjetoSolicitudList(0).getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS)){
							 //Cert. Registro Mobiliario de Contratos
							 solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_DESPACHAR);
						 }else{
							 if(solicitud.getObjetoSolicitudList(0).getCertificado_id().equals(Constantes.COD_CERTIFICADO_COPIA_LITERAL_RMC)){
								 solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_DESPACHAR);
							 }else{
								 solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_EXPEDIR); 
							 }
						 }
					}
					if (tipo_cert.equals("V")|| tipo_cert.equals("G")|| tipo_cert.equals("A")|| tipo_cert.equals("H")|| tipo_cert.equals("C")|| tipo_cert.equals("M") || tipo_cert.equals("B") ){
						 //Cert. Registro de Vigencia
						 solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_DESPACHAR);
					}
					else{
						if(tipo_cert.equals("D")){
							/*** inicio: jrosas 24-08 -07 **/
							if (solicitud.getEstado().equals("C")){
								solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_EXPEDIR);
							}else if (solicitud.getEstado().equals("V")){
								solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_DESPACHAR);
							}
							/*** fin: jrosas 24-08 -07 **/
						}
					}
					/*else{
						//actualizo el estado de la solicitud a "V" e inserto un registro por el cambio de solicitud en sgmt_solicitud
						// para copia literal, cert. dominial
						solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_EXPEDIR);
					}*/
					//inserto un registro de verificacion en atencion
					//atencion.grabarVerificacion(sNumSol, userBean.getCuentaId(), comentario, conn);
					//actualizo el estado de solicitud x carga
					adminCargaLab.actualizarSolxCargaVerficado(sNumSol,userBean,conn);
				}
				
				if(request.getParameter("chkVeriManu")!=null)
				{
					solicitud.actualizaVeriManuSol();
				}
				/*** inicio jrosas 21-08-07 **/
				req.setAttribute("solicitud", solicitud);
				/*** fin jrosas 21-08-07 **/
				
				/********inicio, dbravo: 14-06-2007 *********/
				/**   manejo de auditoria para la atención de solitud **/
				
				LogAuditoriaAtencionSolicitudBean logAuditoriaAtencionSolicitudBean = new LogAuditoriaAtencionSolicitudBean();
				logAuditoriaAtencionSolicitudBean.setEstado(solicitud.getEstado());
				logAuditoriaAtencionSolicitudBean.setComentario(comentario);
				logAuditoriaAtencionSolicitudBean.setCuentaId(userBean.getCuentaId());
				logAuditoriaAtencionSolicitudBean.setSolicitudId(sNumSol);
				
				gob.pe.sunarp.extranet.transaction.Transaction.getInstance().registraLogAuditoriaAtencionSolicitud(logAuditoriaAtencionSolicitudBean, dconn);
				
				/**   manejo de auditoria para la atención de solitud **/
				/********fin, dbravo: 14-06-2007 *********/
				
				//mail
				MailDataBean mailBean = new MailDataBean();
				mailBean.setTo(solicitud.getDatosRegisEmisorBean().getCorreo_electronico());
				mailBean.setSubject("Solicitud de Publicidad Certificada No. " + solicitud.getSolicitud_id());
				mailBean.setBody("Ud ha recibido una nueva solicitud. Ingrese a la extranet y revise la solicitud "+ solicitud.getSolicitud_id());
				MailProcessor.getInstance().saveMail(mailBean, conn);
				
				conn.commit();
				
				// le decimos al singleton que la carga laboral del registrador se ha modificado
				try{
					MonitorCargaLaboral.getInstance().setEstadoCargaRegistrador(solicitud.getDatosRegisVerificadorBean().getCuentaId(), true);
					MonitorCargaLaboral.getInstance().setEstadoCargaRegistrador(solicitud.getDatosRegisEmisorBean().getCuentaId(), true);
					
				}catch (NullPointerException n) {
					if(isTrace(this)) System.out.println("WARNING. No se pudo recuperar el cuenta_id del registrador");
				}
				req.setAttribute("mensaje", mensaje);
				req.setAttribute("destino", destino);	
				
				response.setStyle("verificaSolicitud");
					
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
	
	
	protected ControllerResponse runCertificadoExpedidoState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			String sNumSol;					
			String tipo_cert;
			String comentario;
			String constancia;
			String usrPass;
			String mensaje = "Solcitud Expedida Satisfactoriamente";
			String destino = "CargaLaboral.do";

			//Recupero numero de solicitud			
			sNumSol = request.getParameter("sol_id");						
			//recupero el tipo de certificado, valor del radio: I, N, P
			tipo_cert = request.getParameter("tip_cert");
			comentario = request.getParameter("ComentarioExpedir");
			//hphp:29/10:recupero la constancia agregado por el verificador
			constancia = request.getParameter("ConstanciaExpedir");
			usrPass = request.getParameter("usrPassExp");									
			Certificado certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_EXPEDIR);
			Solicitud solicitud = new Solicitud(sNumSol, conn);
			
			certificado.setPassword(usrPass);
			//actualizo el estado del certificado
			certificado.validaPasswordCertExpedido(userBean.getUserId());
			certificado.setTs_expedicion(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));				
			certificado.setUsr_modi(userBean.getUserId());
			certificado.setConstancia(constancia);
			certificado.actualizarEstadoCertExpedido();
			//actualizo el estado de la solicitud  a "E" e inserto un registro por el cambio de solicitud en sgmt_solicitud
			solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_DESPACHAR);
			//inserto un registro en atencion
			//atencion.grabarEmision(sNumSol, userBean.getCuentaId(), comentario, conn);
			
			//actualizo el estado de solicitud x carga
			//AdministradorCargaLaboral adminCargaLab = new AdministradorCargaLaboral();
			//adminCargaLab.actualizarSolxCargaExpedido(sNumSol, userBean, conn);-- solo en caso de actualizar solicitud_carga al emisor en C como completa
			// si se hace esa correccion entocnes se modificaran las busquedas por numero de solicitud, y estado (carga laboral)
			/******** inicio: jrosas: 24-08-07  se guarda logs para el caso de que pase por certificado porEmitido **/
			/********inicio, dbravo: 14-06-2007 *********/
			/**   manejo de auditoria para la atención de solitud **/
			
			LogAuditoriaAtencionSolicitudBean logAuditoriaAtencionSolicitudBean = new LogAuditoriaAtencionSolicitudBean();
			logAuditoriaAtencionSolicitudBean.setEstado(solicitud.getEstado());
			logAuditoriaAtencionSolicitudBean.setComentario(comentario);
			logAuditoriaAtencionSolicitudBean.setCuentaId(userBean.getCuentaId());
			logAuditoriaAtencionSolicitudBean.setSolicitudId(sNumSol);
			
			gob.pe.sunarp.extranet.transaction.Transaction.getInstance().registraLogAuditoriaAtencionSolicitud(logAuditoriaAtencionSolicitudBean, dconn);
			
			/******** manejo de auditoria para la atención de solitud **/
			/********fin, dbravo: 14-06-2007 *********/
			/*** fin: jrosas: 24-08-07  **/
			
			conn.commit();
			// le decimos al singleton que la carga laboral del usuario se ha modificado
			
			try{
				MonitorCargaLaboral.getInstance().setEstadoCargaRegistrador(solicitud.getDatosRegisEmisorBean().getCuentaId(), true);
			}catch (NullPointerException n) {
				if(isTrace(this)) System.out.println("WARNING. No se pudo recuperar el cuenta_id del registrador");
			}
			
			req.setAttribute("mensaje", mensaje);
			req.setAttribute("destino", destino);			
			response.setStyle("expideSolicitud");
					
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
	
	protected ControllerResponse runDespachaSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try
		{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			String stipobusq;
			String sNumSol;
			String sRangoFecha; 
			String sEstadoSol;
			String sRefrescaAuto;
			String tipoEnv;
			String[] solIds;
			 
			Solicitud solicitud;
			ArrayList arrbuscaList = new ArrayList();
			FechaBusquedaBean fechaBusqBean = new FechaBusquedaBean();
			//Recupero el tipo de busqueda			
			stipobusq = null;
			solIds =  req.getParameterValues("solId");
			for(int i=0; i<solIds.length; i++)
			{
				System.out.println("Actualizando Solicitud " + solIds[i]);
				solicitud = new Solicitud(solIds[i], conn);
				solicitud.actualizaEstadoSol(Constantes.ESTADO_SOL_DESPACHADA);
				// inicio:jrosas 13-06-2007
				if (solicitud.getObjetoSolicitudList(0).getTpo_cert().equals("N") || solicitud.getObjetoSolicitudList(0).getTpo_cert().equals("L") || solicitud.getObjetoSolicitudList(0).getTpo_cert().equals("D")){
					if (!solicitud.getObjetoSolicitudList(0).getCertificado_id().equals("18") || 
						 solicitud.getObjetoSolicitudList(0).getTpo_cert().equals("L")){
						
						AdministradorCargaLaboral admin = new AdministradorCargaLaboral();
						admin.actualizarSolxCargaExpedido(solIds[i], userBean,conn);
					}
				}
					
				// fin:jrosas 13-06-2007
				
			}
			conn.commit();
			// le decimos al singleton que la carga laboral del usuario se ha modificado
			try{
				MonitorCargaLaboral.getInstance().setEstadoCargaRegistrador(userBean.getCuentaId(), true);
			}catch (NullPointerException n) {
				if(isTrace(this)) System.out.println("WARNING. No se pudo recuperar el cuenta_id del registrador");
			}
			
			req.setAttribute("mensaje", "La operacion fue realizada con exito");
			req.setAttribute("destino", "CargaLaboral.do");
			
			response.setStyle("despachaSolicitud");

			
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
private void manejoPaginacion(Propiedades propiedades, ControllerRequest request, int paginacion, boolean hayNext, long tamano, int num_pagina) throws Exception{
  		  int pagSiguiente = 0;
  		  int linXpag = propiedades.getLineasPorPag();

			if(paginacion == 1){
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
				
				pagSiguiente = (tamano >= linXpag)?linXpag:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Solicitudes del 1 al ").append(pagSiguiente));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));
			}else{
				
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
				
				pagSiguiente = (tamano >= linXpag * paginacion)?linXpag * paginacion:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Solicitudes del ").append(((paginacion - 1) * linXpag) + 1).append(" al ").append(pagSiguiente));
			}
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeroderegistros", String.valueOf(tamano));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));		
	}	

	protected ControllerResponse runComprobanteState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			
			init(request);
			validarSesion(request);
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
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
	 
	/**
	 * @author dbravo
	 * @descripcion Se encarga de obtener la descripción para el comprobante a emitir.
	 * @param obj
	 * @param solicitud
	 * @param montoBruto
	 * @param dbConn
	 * @return
	 * @throws DBException
	 */
	private ComprobanteBean descripcionComprobanteCrem(ObjetoSolicitudBean obj, Solicitud solicitud, String montoBruto, DBConnection dbConn) throws DBException{
		
		StringBuffer descripcion = new StringBuffer();
		
		if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)){
			descripcion.append("CERTIFICADO REGISTRAL MOBILIARIO CONDICIONADO ");
		}else if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO)){
			descripcion.append("CERTIFICADO REGISTRAL MOBILIARIO HISTORICO ");
		}else if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)){
			descripcion.append("CERTIFICADO REGISTRAL MOBILIARIO ");
		}
		StringBuffer desTipoRegistro = new StringBuffer();
		/** inicio: jrosas 17-08-07 **/
		if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)){
			StringTokenizer st = new java.util.StringTokenizer(obj.getTipoRegistro(), ",", false);	
			
			int i=0;
			while (st.hasMoreTokens() == true) {
				String codigoLibro = st.nextToken();
				
				if(i!=0){
					desTipoRegistro.append(",");
				}
				if(codigoLibro.equals(Constantes.TIPO_REGISTRO_MOBILIARIO_CONTRATOS)){
					desTipoRegistro.append("Registro Mobiliario de Contratos");
				}
				else{
					if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_VEHICULAR)){
						desTipoRegistro.append("Propiedad Vehicular");
					}
					else{
						if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_EMBARCACION)){
							desTipoRegistro.append("Propiedad Embarcacion Pesquera");
						}
						else{
							if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_BUQUES)){
								desTipoRegistro.append("Propiedad Buques");
							}
							else{
								if(codigoLibro.equals(Constantes.TIPO_PROPIEDAD_AERONAVES)){
									desTipoRegistro.append("Propiedad Aeronaves");
								}
								else{
									if(codigoLibro.equals(Constantes.TIPO_PERSONAS_JURIDICAS)){
										desTipoRegistro.append("Personas Jurídicas (Participaciones)");
									}
								}
							}
						}
					}
				}
				
				i++;
				
			}
		}
		/** fin: jrosas 17-08-07 **/
		
		if(obj.getTpo_pers().equals("N"))
		{
			descripcion.append("<BR>");
			descripcion.append("PARTICIPANTE: ");
			descripcion.append(obj.getApe_pat());
			if(obj.getApe_mat()!=null && !obj.getApe_mat().trim().equals("")){
				descripcion.append(" ").append(obj.getApe_mat());
			}
			descripcion.append(" ").append(obj.getNombres());
		}
		else
		{
			descripcion.append("<BR>");
			descripcion.append("PARTICIPANTE: ");
			descripcion.append(obj.getRaz_soc());
		}
		
		String desTipoParticipante = "";
		if(obj.getTipoParticipante() !=null && obj.getTipoParticipante().equals("1")){
			desTipoParticipante = "Deudor";
		}else if(obj.getTipoParticipante() !=null && obj.getTipoParticipante().equals("2")){
			desTipoParticipante = "Acreedor";
		}else if(obj.getTipoParticipante() !=null && obj.getTipoParticipante().equals("3")){
			desTipoParticipante = "Representante";
		}else if(obj.getTipoParticipante() !=null && obj.getTipoParticipante().equals("4")){
			desTipoParticipante = "Otros tipos de participacion";
		}
		
		if (obj.getTipoParticipante() !=null && !obj.getTipoParticipante().equals("") ){
			descripcion.append("<BR>");
			descripcion.append("TIPO DE PARTICIPACIÓN: ");
			descripcion.append(desTipoParticipante);
		}
		/** inicio: jrosas 17-08-07 **/
		if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)){
			if (obj.getTipoRegistro() !=null && !obj.getTipoRegistro().equals("") ){
				descripcion.append("<BR>");
				descripcion.append("REGISTROS: ");
				descripcion.append(desTipoRegistro.toString());
			}
		
			if (obj.getFlagHistorico() != null && !obj.getFlagHistorico().equals("") ){
				descripcion.append("<BR>");
				descripcion.append("HISTÓRICO: ");
				if (obj.getFlagHistorico().equals("1"))
					descripcion.append("SÍ");
				else
					descripcion.append("NO");
			}
			if (obj.getFechaInscripcionASientoDesde() !=null && !obj.getFechaInscripcionASientoDesde().equals("") ){
				descripcion.append("<BR>");
				descripcion.append("FECHA DE INSCRIPCIÓN DE ASIENTO: ");
				descripcion.append(obj.getFechaInscripcionASientoDesde());
			}
			if (obj.getFechaInscripcionASientoHasta() != null && !obj.getFechaInscripcionASientoHasta().equals("") ){
				descripcion.append(" - ").append(obj.getFechaInscripcionASientoHasta());
			}
		}
		/** fin: jrosas 17-08-07 **/
		
		descripcion.append("<BR>");
		descripcion.append("OFICINA: WEB");
		
		
		ComprobanteBean beancomp = new ComprobanteBean();
		beancomp.setTipoPago("Linea prepago");
		beancomp.setOficina("WEB");
		beancomp.setCajero("WEB");
		beancomp.setFecha_hora(FechaUtil.getCurrentDateTime());
		beancomp.setMonto(montoBruto);
		
		DboCuenta dboCuenta = new DboCuenta(dbConn);
		dboCuenta.clearAll();
		dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID+"|"+DboCuenta.CAMPO_PE_NATU_ID);
		dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID, solicitud.getCuenta_id());
		if(dboCuenta.find()){
			beancomp.setUserId(dboCuenta.getField(DboCuenta.CAMPO_USR_ID));
		}
		
		DboPeNatu dboPN = new DboPeNatu(dbConn);
		dboPN.setFieldsToRetrieve(DboPeNatu.CAMPO_NOMBRES + "|" + DboPeNatu.CAMPO_APE_PAT + "|" + DboPeNatu.CAMPO_APE_MAT);
		dboPN.setField(DboPeNatu.CAMPO_PE_NATU_ID, dboCuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
		
		if (dboPN.find()) {
			beancomp.setNombreEntidad(dboPN.getField(DboPeNatu.CAMPO_APE_PAT) + " " + dboPN.getField(DboPeNatu.CAMPO_APE_MAT) + " " + dboPN.getField(DboPeNatu.CAMPO_NOMBRES));
		}
		
		
		beancomp.setSolicitudId(obj.getSolicitud_id());
		beancomp.setTipoPub("C");
		beancomp.setSolDesc(descripcion.toString());
		
		return beancomp;
	}	
	
	/**
	 * @autor dbravo
	 * @descripcion Se encarga de la logica para el segundo pago del certificado CREM
	 * @param conn
	 * @param dbConn
	 * @param usuario
	 * @param obj
	 * @param solicitud
	 * @param cantidadLineas
	 * @param idSesion
	 * @param ipOrigen
	 * @return
	 * @throws CustomException
	 * @throws DBException
	 * @throws Throwable
	 */
	private ComprobanteBean descuentaCREM(Connection conn, DBConnection dbConn, UsuarioBean usuario, Certificado certificado, ObjetoSolicitudBean obj, Solicitud solicitud, long cantidadLineas, String idSesion, String ipOrigen) throws CustomException, DBException, Throwable{
		
		int codigoCertificado = 0;
		int lineasPorPagina = 0;
		ComprobanteBean beancomp = null;
		
		ResourceBundle bundle = ResourceBundle.getBundle("gob.pe.sunarp.extranet.publicidad.properties.Publicidad");
		
		if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)){
			cantidadLineas += Long.parseLong(bundle.getString("constante.certificado.crem.condicionado.lineas.cabecera"));
			cantidadLineas += Long.parseLong(bundle.getString("constante.certificado.crem.condicionado.lineas.pie"));
			lineasPorPagina = Integer.parseInt(bundle.getString("constante.certificado.crem.condicionado.lineas.pagina"));
			codigoCertificado = Constantes.SERVICIO_CERTIFICADO_CREM_CONDICIONADO_PAGINA;
		}else if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO)){
			cantidadLineas += Long.parseLong(bundle.getString("constante.certificado.crem.historico.lineas.cabecera"));
			cantidadLineas += Long.parseLong(bundle.getString("constante.certificado.crem.historico.lineas.pie"));
			lineasPorPagina = Integer.parseInt(bundle.getString("constante.certificado.crem.historico.lineas.pagina"));
			codigoCertificado = Constantes.SERVICIO_CERTIFICADO_CREM_HISTORICO_PAGINA;
		}else if(obj.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)){
			cantidadLineas += Long.parseLong(bundle.getString("constante.certificado.crem.vigente.lineas.cabecera"));
			cantidadLineas += Long.parseLong(bundle.getString("constante.certificado.crem.vigente.lineas.pie"));
			lineasPorPagina = Integer.parseInt(bundle.getString("constante.certificado.crem.vigente.lineas.pagina"));
			codigoCertificado = Constantes.SERVICIO_CERTIFICADO_CREM_ACTOS_VIGENTE_PAGINA;
		}else{
			throw new NullPointerException("No se ingreso el codigo del certificado CREM.");
		}
		
		long cantidadPaginas   = cantidadLineas/lineasPorPagina;
		long restante          = cantidadLineas%lineasPorPagina;
		
		if(restante>0){
			cantidadPaginas = cantidadPaginas+1;
		}
		
		/**
		 * @autor Daniel L. Bravo Falcón
		 * @descripcion Si la persona acepto el descuento en linea se procede a realiza la operación de descuento.
		 */
		if (obj.getFlagAceptaCondicion()!=null && obj.getFlagAceptaCondicion().equals("1"))
		{
			
			/**
			 * @autor Daniel L. Bravo Falcón
			 * @descripcion Si el total de paginas es una, no se realiza el descuento en linea, 
			 * 				si el total de paginas es mayor que una se descuenta la linea 
			 */
			
			if(cantidadPaginas>1){
				
				LogAuditoriaVerificaCremBean bt = new LogAuditoriaVerificaCremBean();
				bt.setRemoteAddr(ipOrigen);             
				bt.setUsuarioSession(usuario);
				bt.setCantidadRegistros(cantidadLineas);
				bt.setCuentaIdSolicitante(solicitud.getCuenta_id());
				bt.setIdSesion(idSesion);
				
				bt.setCodigoServicio(codigoCertificado);
				
				bt.setCodigoGLA(Integer.parseInt(obj.getCod_GLA()));
				
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					
					/**
					 * @autor Daniel L. Bravo Falcón
					 * @fecha 14/08/2007
					 * @descripcion Se realiza el pago faltante del certificado CREM y se actualiza el objeto solicitud, campo
					 * 				flag_pago_crem con "1"(con pago)
					 * 				Si se produce una excepción de tipo saldo insuficiente, se actualiza el objeto solicitud, campo
					 * 			    flag_pago_crem con "0"(sin pago).
					 */
					try{
						
						PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
						
						certificado.setFlagPagoCrem("1");
						certificado.setPaginasCrem(String.valueOf(cantidadPaginas));
						certificado.setPagoCrem(new BigDecimal(prepagoBean.getMontoBruto()));
						
						beancomp = descripcionComprobanteCrem(obj, solicitud, String.valueOf(prepagoBean.getMontoBruto()),dbConn);
						
						
					} catch (CustomException e) {
						/**
						 * @author Daniel L. Bravo Falcón
						 * @descripcion Si se produce una excepción de tipo saldo insuficiente, se actualiza el objeto solicitud, campo
						 * 			    flag_pago_crem con "0"(sin pago).
						 */
						if(Constantes.E70001_SALDO_INSUFICIENTE.equalsIgnoreCase(e.getCodigoError())){
							
							PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioCREM(codigoCertificado, Integer.parseInt(obj.getCod_GLA()), cantidadLineas, dbConn);
							certificado.setFlagPagoCrem("0");
							certificado.setPaginasCrem(String.valueOf(cantidadPaginas));
							certificado.setPagoCrem(new BigDecimal(prepagoBean.getMontoBruto()));
							
						}else{
							throw e;
						}
						
					}catch(DBException dbe){
						throw dbe;
					}catch(Throwable ex){
						throw ex;
					}
				}
			}else{
				
				certificado.setFlagPagoCrem("1");
				certificado.setPaginasCrem(String.valueOf(cantidadPaginas));
				certificado.setPagoCrem(new BigDecimal(0));
				
			}
		  
		}else{
			
			/**
			 * Si la persona no acepto el descuento
			 */
			if(cantidadPaginas>1){
				
				PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioCREM(codigoCertificado, Integer.parseInt(obj.getCod_GLA()), cantidadLineas, dbConn);
				certificado.setFlagPagoCrem("0");
				certificado.setPaginasCrem(String.valueOf(cantidadPaginas));
				certificado.setPagoCrem(new BigDecimal(prepagoBean.getMontoBruto()));
				
			}else{
				
				certificado.setFlagPagoCrem("1");
				certificado.setPaginasCrem(String.valueOf(cantidadPaginas));
				certificado.setPagoCrem(new BigDecimal(0));
				
			}
			
		}
		
		return beancomp;
	}
	
}

