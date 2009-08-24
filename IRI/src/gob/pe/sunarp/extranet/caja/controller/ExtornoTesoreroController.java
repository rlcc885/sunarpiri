/*
 * Created on 24-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.caja.controller;
import com.ibm.jvm.format.Util;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.prepago.bean.*;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.util.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;
/** 
 * @author jbugarin TEAM SUNARP AVATAR!!!
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExtornoTesoreroController extends ControllerExtension {
	
	public ExtornoTesoreroController() 
		{
			super();
			addState(new State("inicial", "Muestra la ventana de Busqueda y Resultados"));
			addState(new State("buscaNroAbono", "Buscar por numero de abono"));
			addState(new State("buscaFecha", "Buscar por fecha"));
			addState(new State("anular", "anular un abono"));
			addState(new State("solicitaExtornarAbono", "anular un abono"));
			addState(new State("extornarAbono", "anular un abono"));
			setInitialState("inicial");
		}

	public String getTitle() 
		{
			return new String("ExtornoTesoreroController");
		}
	protected ControllerResponse runInicialState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		ArrayList listaCajas = new ArrayList();
		
		
		try{
			init(request);
			validarSesion(request);
			req = ExpressoHttpSessionBean.getRequest(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			//String fecha = FechaUtil.getCurrentDate();
			//Capturo variables del formulario extorno de pago
		
			String ano = request.getParameter("ano");
			String mes = request.getParameter("mes");
			String dia = request.getParameter("dia");
			String nroAbono = request.getParameter("txtNroAbono");
			
			String fecha_aux = null;
			String fecha_ = null;
			String fecha = null;
			
			int _d ;
			int _m ;
			int _a ;
										
			//PRIMERA VEZ QUE SE ENTRA A LA VENTANA - FECHA DE HOY
			if(ano == null){
			fecha_aux = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
			dia = fecha_aux.substring(0,2);
					mes = fecha_aux.substring(3,5);
					ano = fecha_aux.substring(6,10);
			}
				
				_d = Integer.parseInt(dia);
				_m = Integer.parseInt(mes);
				_a = Integer.parseInt(ano);
	
				fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
				fecha_ = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);
				if (isTrace(this)) trace("Buscando movimientos del dia " + fecha.substring(0, 10), request);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", ano);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mes", mes);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("dia", dia);
				
			DiarioRecaudaDetalleBean bean = null;
								java.util.List lista = new java.util.ArrayList();

								DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
								//vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, caja);
								vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_);//cb
					

							  //*** MANEJO DE LA PAGINACION
										/*  int num_pagina = Propiedades.getInstance().getLineasPorPag();
										  vista.setMaxRecords(num_pagina);
										  int paginacion = 1;
										  if(request.getParameter("pagina") != null)
											  paginacion = Integer.parseInt(request.getParameter("pagina"));
				
										  //*** FIN DE MANEJO DE PAGINACION						
										  boolean hayNext = false;
										  boolean encontro = false;
			*/
										  //java.util.List ve = vista.searchAndRetrieveListPaginado(paginacion);
								java.util.List ve = vista.searchAndRetrieveList(DboVwDiariorecauda.CAMPO_ABONO_ID+"|"+DboVwDiariorecauda.CAMPO_TS_CREA+"|"+
								DboVwDiariorecauda.CAMPO_NOMBRES+"|"+DboVwDiariorecauda.CAMPO_MONTO+"|"+
								DboVwDiariorecauda.CAMPO_ESTADO);
			
										  //if(vista.getHaySiguiente())
											  //hayNext = true;
			
										  for(Iterator i = ve.iterator(); i.hasNext();){
											//  encontro = true;

											  DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();
											  bean = new DiarioRecaudaDetalleBean();
				
											  bean.setIdCajero(x.getField(DboVwDiariorecauda.CAMPO_USR_CAJA));
				
											  String aux = null;
											  if(x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC) == null || x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC).trim().length() <= 0)
												  aux = "SIN ASIGNAR";
											  else
												  aux = x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
					
											  bean.setNroRecibo(aux);
											  //bean.setNroAbonoExtranet(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
											  bean.setHora(x.getField(DboVwDiariorecauda.CAMPO_TS_CREA));
											  bean.setIdAbono(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
											  bean.setTipoPersona((x.getField(DboVwDiariorecauda.CAMPO_TIPO_USR).trim().equalsIgnoreCase("O"))?"PJ":"PN");
											  bean.setNombre(x.getField(DboVwDiariorecauda.CAMPO_APE_PAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_APE_MAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_NOMBRES));
											  bean.setMonto(x.getField(DboVwDiariorecauda.CAMPO_MONTO));
											  bean.setTipoPago((x.getField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT).trim().equalsIgnoreCase("E"))?"Efectivo":"Cheque");
											  bean.setEsAnulado(x.getField(DboVwDiariorecauda.CAMPO_ESTADO));
											  bean.setTipoAbono(x.getField(DboVwDiariorecauda.CAMPO_TIPO_VENT));
											  //bean.setNumComprobante(x.getField(DboVwDiariorecauda.CAMPO_COMPR));
											  bean.setNumComprobante("");
											  lista.add(bean);
										  }
			
//								*PAGINACION EN EL JSP*//			
									/*	  if(paginacion == 1){
											  if(hayNext)
												  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
											  else
												  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
											  ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
										  }else{
											  if(hayNext)
												  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
											  else
												  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
											  ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
										  }
										  ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
										  ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
							*/
								req.setAttribute("listadoConsulta",lista);
								response.setStyle("inicial");

				

				 
			 response.setStyle("inicial");
			 
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}

	protected ControllerResponse runBuscaNroAbonoState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
		
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = null;
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			
		
		
			try{
				
				init(request);
				validarSesion(request);
				req = ExpressoHttpSessionBean.getRequest(request);
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
		
				UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
                //capturo variables del formulario extorno de pago
				 String ano = request.getParameter("ano");
				 String mes = request.getParameter("mes");
				 String dia = request.getParameter("dia");
				 String nroAbono = request.getParameter("txtNroAbono");		
				 
				 //poniendo en session el criterio de busqueda
				 session.setAttribute("nroAbono", nroAbono);
				 session.removeAttribute("fecha");
				session.removeAttribute("fecha_");
				  
			
		         String fecha_aux = null;
				 String fecha_ = null;
				 String fecha = null;
			
				 int _d ;
				 int _m ;
				 int _a ;
										
				 //PRIMERA VEZ QUE SE ENTRA A LA VENTANA - FECHA DE HOY
				 if(ano == null){
				    fecha_aux = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
				    dia = fecha_aux.substring(0,2);
				    mes = fecha_aux.substring(3,5);
				    ano = fecha_aux.substring(6,10);
				 }
				
				 _d = Integer.parseInt(dia);
				 _m = Integer.parseInt(mes);
				 _a = Integer.parseInt(ano);
	
				 fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
				 fecha_ = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);
				 if (isTrace(this)) trace("Buscando movimientos del dia " + fecha.substring(0, 10), request);
				 ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", ano);
				 ExpressoHttpSessionBean.getRequest(request).setAttribute("mes", mes);
				 ExpressoHttpSessionBean.getRequest(request).setAttribute("dia", dia);
				
			//Empieza el QUERY

				DiarioRecaudaDetalleBean bean = null;

				DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
				vista.setField(DboVwDiariorecauda.CAMPO_ABONO_ID, nroAbono);
					
				
				ArrayList lista = vista.searchAndRetrieveList(DboVwDiariorecauda.CAMPO_ABONO_ID+"|"+DboVwDiariorecauda.CAMPO_TS_CREA+"|"+
													DboVwDiariorecauda.CAMPO_NOMBRES+"|"+DboVwDiariorecauda.CAMPO_MONTO+"|"+
													DboVwDiariorecauda.CAMPO_ESTADO
													);
							
				List listaADevolver = new ArrayList();
				Iterator i = lista.iterator();
				while(i.hasNext()){
					//encontro = true;

					DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();
					bean = new DiarioRecaudaDetalleBean();
				
					bean.setIdCajero(x.getField(DboVwDiariorecauda.CAMPO_USR_CAJA));
					bean.setNroRecibo("");
					//bean.setNroAbonoExtranet(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
					bean.setHora(x.getField(DboVwDiariorecauda.CAMPO_TS_CREA));
					bean.setIdAbono(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
					bean.setTipoPersona((x.getField(DboVwDiariorecauda.CAMPO_TIPO_USR).trim().equalsIgnoreCase("O"))?"PJ":"PN");
					bean.setNombre(x.getField(DboVwDiariorecauda.CAMPO_APE_PAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_APE_MAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_NOMBRES));
					bean.setMonto(x.getField(DboVwDiariorecauda.CAMPO_MONTO));
					bean.setTipoPago((x.getField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT).trim().equalsIgnoreCase("E"))?"Efectivo":"Cheque");
					bean.setEsAnulado(x.getField(DboVwDiariorecauda.CAMPO_ESTADO));
					bean.setTipoAbono(x.getField(DboVwDiariorecauda.CAMPO_TIPO_VENT));
					//bean.setNumComprobante(x.getField(DboVwDiariorecauda.CAMPO_COMPR));
					bean.setNumComprobante("");
					listaADevolver.add(bean);
				}
			
				req.setAttribute("listadoConsulta",listaADevolver);	
				req.setAttribute("numeroAbono",nroAbono);
				response.setStyle("inicial");
				
			 
			} catch (CustomException ce) {
				log(ce.getCodigoError(), ce.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(ce.getForward());
			} catch (DBException dbe) {
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
			return response;
		}

	protected ControllerResponse runBuscaFechaState(
				ControllerRequest request,
				ControllerResponse response)
				throws ControllerException {
		
				DBConnectionFactory pool = DBConnectionFactory.getInstance();
				Connection conn = null;
				HttpServletRequest req = null;
				HttpSession session = ExpressoHttpSessionBean.getSession(request);
				ArrayList listaCajas = new ArrayList();
		
		
				try{
					init(request);
					validarSesion(request);
					req = ExpressoHttpSessionBean.getRequest(request);
					conn = pool.getConnection();
					conn.setAutoCommit(false);
					DBConnection dconn = new DBConnection(conn);
		
					UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
//					capturo variables del formulario extorno de pago
		
					String ano = request.getParameter("ano");
					String mes = request.getParameter("mes");
					String dia = request.getParameter("dia");
				
			
					String fecha_aux = null;
					String fecha_ = null;
					String fecha = null;
			
					int _d ;
					int _m ;
					int _a ;
										
					//PRIMERA VEZ QUE SE ENTRA A LA VENTANA - FECHA DE HOY
					if(ano == null){
					fecha_aux = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
					dia = fecha_aux.substring(0,2);
					mes = fecha_aux.substring(3,5);
					ano = fecha_aux.substring(6,10);
					}
				
					_d = Integer.parseInt(dia);
					_m = Integer.parseInt(mes);
					_a = Integer.parseInt(ano);
	
					fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
					fecha_ = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);
					if (isTrace(this)) trace("Buscando movimientos del dia " + fecha.substring(0, 10), request);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", ano);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("mes", mes);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("dia", dia);

					
					//poniendo en session el criterio de busqueda
					session.setAttribute("fecha", fecha);
					session.setAttribute("fecha_", fecha_);
					session.removeAttribute("nroAbono");
					
					
					DiarioRecaudaDetalleBean bean = null;
					java.util.List lista = new java.util.ArrayList();

					DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
					//vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, caja);
					vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_);//cb
					

                  //*** MANEJO DE LA PAGINACION
							/*  int num_pagina = Propiedades.getInstance().getLineasPorPag();
							  vista.setMaxRecords(num_pagina);
							  int paginacion = 1;
							  if(request.getParameter("pagina") != null)
								  paginacion = Integer.parseInt(request.getParameter("pagina"));
				
							  //*** FIN DE MANEJO DE PAGINACION						
							  boolean hayNext = false;
							  boolean encontro = false;
*/
							  //java.util.List ve = vista.searchAndRetrieveListPaginado(paginacion);
					java.util.List ve = vista.searchAndRetrieveList(DboVwDiariorecauda.CAMPO_ABONO_ID+"|"+DboVwDiariorecauda.CAMPO_TS_CREA+"|"+
					DboVwDiariorecauda.CAMPO_NOMBRES+"|"+DboVwDiariorecauda.CAMPO_MONTO+"|"+
					DboVwDiariorecauda.CAMPO_ESTADO);
			
							  //if(vista.getHaySiguiente())
								  //hayNext = true;
			
							  for(Iterator i = ve.iterator(); i.hasNext();){
								//  encontro = true;

								  DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();
								  bean = new DiarioRecaudaDetalleBean();
				
								  bean.setIdCajero(x.getField(DboVwDiariorecauda.CAMPO_USR_CAJA));
				
								  String aux = null;
								  if(x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC) == null || x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC).trim().length() <= 0)
									  aux = "SIN ASIGNAR";
								  else
									  aux = x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
					
								  bean.setNroRecibo(aux);
								  //bean.setNroAbonoExtranet(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
								  bean.setHora(x.getField(DboVwDiariorecauda.CAMPO_TS_CREA));
								  bean.setIdAbono(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
								  bean.setTipoPersona((x.getField(DboVwDiariorecauda.CAMPO_TIPO_USR).trim().equalsIgnoreCase("O"))?"PJ":"PN");
								  bean.setNombre(x.getField(DboVwDiariorecauda.CAMPO_APE_PAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_APE_MAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_NOMBRES));
								  bean.setMonto(x.getField(DboVwDiariorecauda.CAMPO_MONTO));
								  bean.setTipoPago((x.getField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT).trim().equalsIgnoreCase("E"))?"Efectivo":"Cheque");
								  bean.setEsAnulado(x.getField(DboVwDiariorecauda.CAMPO_ESTADO));
								  bean.setTipoAbono(x.getField(DboVwDiariorecauda.CAMPO_TIPO_VENT));
								  //bean.setNumComprobante(x.getField(DboVwDiariorecauda.CAMPO_COMPR));
								  bean.setNumComprobante("");
								  lista.add(bean);
							  }
			
//					*PAGINACION EN EL JSP*//			
						/*	  if(paginacion == 1){
								  if(hayNext)
									  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
								  else
									  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
								  ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
							  }else{
								  if(hayNext)
									  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
								  else
									  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
								  ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
							  }
							  ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
							  ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
				*/
					req.setAttribute("listadoConsulta",lista);
					response.setStyle("inicial");
			 
				} catch (CustomException ce) {
					log(ce.getCodigoError(), ce.getMessage(), request);
					principal(request);
					rollback(conn, request);
					response.setStyle(ce.getForward());
				} catch (DBException dbe) {
					log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
					rollback(conn, request);
					response.setStyle("error");
				}catch(Throwable ex){
					log(Constantes.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					rollback(conn, request);
					response.setStyle("error");
				}finally{
					pool.release(conn);
					end(request);
				}
				return response;
			}
	protected ControllerResponse runAnularState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
		
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = null;
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			ArrayList listaCajas = new ArrayList();
		
		
			try{
				init(request);
				validarSesion(request);
				req = ExpressoHttpSessionBean.getRequest(request);
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
		
				UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
//				capturo variables del formulario extorno de pago
		
				String ano = request.getParameter("ano");
				String mes = request.getParameter("mes");
				String dia = request.getParameter("dia");
				
			
				String fecha_aux = null;
				String fecha_ = null;
				String fecha = null;
			
				int _d ;
				int _m ;
				int _a ;
										
				//PRIMERA VEZ QUE SE ENTRA A LA VENTANA - FECHA DE HOY
				if(ano == null){
				fecha_aux = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
				dia = fecha_aux.substring(0,2);
				mes = fecha_aux.substring(3,5);
				ano = fecha_aux.substring(6,10);
				}
				
				_d = Integer.parseInt(dia);
				_m = Integer.parseInt(mes);
				_a = Integer.parseInt(ano);
	
				fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
				fecha_ = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);
				if (isTrace(this)) trace("Buscando movimientos del dia " + fecha.substring(0, 10), request);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", ano);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mes", mes);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("dia", dia);

			
			
				//Capturo variables del formulario extorno de pago campo hidden
				String idAbono = request.getParameter("hid1");
				
				DboAbono dboAbono = new DboAbono(dconn);
				dboAbono.setFieldsToUpdate(DboAbono.CAMPO_ESTADO);
				dboAbono.setField(DboAbono.CAMPO_ABONO_ID,idAbono);
				dboAbono.setField(DboAbono.CAMPO_ESTADO,"0");
				dboAbono.update();
				
				dconn.commit();
						
				req.setAttribute("numeroAbono",idAbono);
				req.setAttribute("arrRepr",Tarea.getComboMotivosExtorno(dconn));
				response.setStyle("motivo");
			 
			} catch (CustomException ce) {
				log(ce.getCodigoError(), ce.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(ce.getForward());
			} catch (DBException dbe) {
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
			return response;
		}
	protected ControllerResponse runSolicitaExtornarAbonoState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
		
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
		
			try{
				System.out.println("INICIO::::");
				init(request);
				validarSesion(request);
			
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
		
				UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

				String abonoId = request.getParameter("hid1");
				String tpoAbono = request.getParameter("hid2").substring(0,1);
				String monto = request.getParameter("hid3");
			
			//Paso 1
				DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
				vista.setField(DboVwDiariorecauda.CAMPO_ABONO_ID, abonoId);
			
				if(!vista.find())
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono no existe o ya ha sido extornado", "errorPrepago");
			
				//14 nov
				//pintar en pantalla el monto FORMATEADO
				req.setAttribute("monto_formateado",Tarea.formatoNumero(monto));
			
				if (isTrace(this)) trace("Se procederá a extornar el abono # " + abonoId, request);
			
				StringBuffer problemas = new StringBuffer("Usuario");
				
				String fecfor = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
				fecfor = fecfor.substring(6,10) + "-" + fecfor.substring(3,5) + "-" + fecfor.substring(0,2);
				
				//Paso X : Verifica que el abono no haya sido asociado anteriormente			
				String reciboAsociado = vista.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
			
				if(reciboAsociado != null & reciboAsociado.trim().length() > 0){
					 if (isTrace(this)) trace(problemas.append(user.getUserId()).append(" trato de extornar abono # ").append(abonoId).append(",pero ya esta asociado al recibo # ").append(reciboAsociado).append(". Accion denegada.") .toString(), request);
						 throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono ya está asociado a un recibo", "errorPrepago");
				 }	
			
				/*
			
			
			//Paso 2
				if(!vista.getField(DboVwDiariorecauda.CAMPO_USR_CAJA).equalsIgnoreCase(user.getUserId()))
				{
					if (isTrace(this)) trace(problemas.append(user.getUserId()).append(" trato de extornar abono # ").append(abonoId).append(". Accion denegada: Usuario no autorizado.").toString(), request);
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Usuario no autorizado", "errorPrepago");
				}
			
			//Paso 3
				
			
				

			

			//Se anadio para continiar con los siguientes pasos (4)
				DboAbono abo = new DboAbono(dconn);
				abo.setFieldsToRetrieve(DboAbono.CAMPO_FG_CIERRE);
				abo.setField(DboAbono.CAMPO_ABONO_ID, abonoId);
			
				if(!abo.find())
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No existe abono: " + abonoId, "errorPrepago");
		
			//Paso 4	
				if(abo.getField(DboAbono.CAMPO_FG_CIERRE).equalsIgnoreCase("1"))
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono no disponible para ser extornado", "errorPrepago");
		
			//Paso 5
				if(vista.getField(DboVwDiariorecauda.CAMPO_ESTADO).equalsIgnoreCase("0"))
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono ya ha sido extornado", "errorPrepago");
			
				//ExpressoHttpSessionBean.getRequest(request).setAttribute("nroAbono", abonoId);
				//ExpressoHttpSessionBean.getRequest(request).setAttribute("tpoAbono", tpoAbono);
			
			/*
			//Formando el QUERY
				MultiDBObject multi = new MultiDBObject(dconn);
			
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuentaJuris", "a");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPerfilCuenta", "b");
				
				multi.setForeignKey("a", DboCuentaJuris.CAMPO_CUENTA_ID, "b", DboPerfilCuenta.CAMPO_CUENTA_ID);
				
				multi.setField("a", DboCuentaJuris.CAMPO_REG_PUB_ID, user.getRegPublicoId());
				multi.setField("a", DboCuentaJuris.CAMPO_OFIC_REG_ID, user.getOficRegistralId());
				multi.setField("b", DboPerfilCuenta.CAMPO_PERFIL_ID, "50");
				multi.setField("b", DboPerfilCuenta.CAMPO_ESTADO, "1");
			
				java.util.Vector v = new java.util.Vector();
			
				for(java.util.Iterator list = multi.searchAndRetrieve().iterator();list.hasNext();)
				{
					MultiDBObject oneMulti = (MultiDBObject) list.next();
					v.add(oneMulti.getField("b", DboPerfilCuenta.CAMPO_CUENTA_ID));
				}
			
				if (v.size()==0)
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No existe usuario TESORERO", "errorPrepago");
			
				StringBuffer a = new StringBuffer();
			
				if(v.size() > 1)
				{
					a.append(" IN (").append((String)v.get(0));
				
					for(int x = 1; x < v.size(); x++)
					{
						a.append(",").append((String)v.get(x));
//						if (x< (v.size()-1))
//							a.append(",");
					}
					a.append(") ");
				}
				else
					a.append(" = ").append(  ((String) v.get(0))  );
				
			
				multi = new MultiDBObject(dconn);
			
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "a");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "b");
				
				multi.setForeignKey("a", DboPeNatu.CAMPO_PE_NATU_ID, "b", DboCuenta.CAMPO_PE_NATU_ID);
				multi.setAppendWhereClause(DboCuenta.CAMPO_CUENTA_ID + a.toString());
			
				TesoreroBean bean = null;
				java.util.List listas = new java.util.ArrayList();
						
				for(java.util.Iterator list = multi.searchAndRetrieve().iterator();list.hasNext();)
				{
					MultiDBObject oneMulti = (MultiDBObject) list.next();
					bean = new TesoreroBean();
					bean.setCuenta_id(oneMulti.getField("b", DboCuenta.CAMPO_CUENTA_ID));
					bean.setUsr_id(oneMulti.getField("b", DboCuenta.CAMPO_USR_ID));
					bean.setApe_pat(oneMulti.getField("a", DboPeNatu.CAMPO_APE_PAT));
					bean.setApe_mat(oneMulti.getField("a", DboPeNatu.CAMPO_APE_MAT));
					bean.setNombres(oneMulti.getField("a", DboPeNatu.CAMPO_NOMBRES));
					listas.add(bean);
				}
				*/
				//ExpressoHttpSessionBean.getRequest(request).setAttribute("listadoConsulta", listas);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("monto", monto);
				req.setAttribute("arrRepr",Tarea.getComboMotivosExtorno(dconn));//pinta el combo de motivos de extorno
				req.setAttribute("numeroAbono",abonoId);
				req.setAttribute("tipoAbono",tpoAbono);
				req.setAttribute("montoAbono",monto);
				response.setStyle("motivo");
								
				
			} catch (CustomException ce) {
				log(ce.getCodigoError(), ce.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(ce.getForward());
			} catch (DBException dbe) {
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
			return response;
		}
		
	protected ControllerResponse runExtornarAbonoState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
		
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = null;
			
			try{
				init(request);
				validarSesion(request);
				req = ExpressoHttpSessionBean.getRequest(request);
							
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
			
				if (isTrace(this)) trace("Iniciando el proceso de extorno", request);
				
				UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);

				String abonoId = request.getParameter("nroAbono");
				String tpoAbono = request.getParameter("tpoAbono").substring(0,1);
				String glosa = request.getParameter("glosa");
				String monto = request.getParameter("mtoAbono");
				//String pwCajero = request.getParameter("pwCajero");
				String idTesorero = user.getUserId();
				//String nroAbono = "";
				String fecha = "";
				String fecha_ = "";
				
				
				//String pwTesorero = request.getParameter("pwTesorero");
				
			
				//if(pwCajero == null || pwCajero.trim().length() <= 0)
				//	throw new CustomException(Errors.EC_MISSING_PARAM, "Se olvidó ingresar el password del usuario Cajero", "errorPrepago");
			
				//if(pwTesorero == null || pwTesorero.trim().length() <= 0)
				//	throw new CustomException(Errors.EC_MISSING_PARAM, "Se olvidó ingresar el password del usuario Tesorero", "errorPrepago");

				//	SecAdmin secAdmin = SecAdmin.getInstance();
				//	boolean cajero = true;
				//	if (isTrace(this)) trace("Validando el password del cajero y del tesorero", request);
				//	try {
				//		String usuario = user.getUserId();
				//		String password = request.getParameter("pwCajero");
				/*		secAdmin.validaUsuario(usuario, password);
					
						cajero = false;
						usuario = request.getParameter("idTesorero");
						password = request.getParameter("pwTesorero");
						secAdmin.validaUsuario(usuario, password);
					} catch (CustomException ce) {
						if(cajero)
							throw new CustomException(ce.getCodigoError(), "Password de Cajero es incorrecto", "errorPrepago");
						else
							throw new CustomException(ce.getCodigoError(), "Password de Tesorero es incorrecto", "errorPrepago");
					}
			*/
				DboAbono abono = new DboAbono(dconn);
				abono.setField(DboAbono.CAMPO_ABONO_ID, abonoId);
			
				if(!abono.find())
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se encontro abono", "errorPrepago");
				
				String tipo_vent = abono.getField(abono.CAMPO_TIPO_VENT);
			
				abono.setFieldsToUpdate(DboAbono.CAMPO_ESTADO);
				abono.setField(DboAbono.CAMPO_ESTADO, "0");
				abono.update();
			
				if(tipo_vent.equals(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA))
				{
					DboPagoSolicitud dboPagSol = new DboPagoSolicitud(dconn);
					dboPagSol.setField(dboPagSol.CAMPO_ABONO_ID, abono.getField(abono.CAMPO_ABONO_ID));
					if(!dboPagSol.find())
						throw new CustomException(Errors.EC_GENERIC_ERROR, "No se encontro Pago Solicitud", "errorPrepago");
					/*DboSolicitud dboSol = new DboSolicitud(dconn);
					dboSol.setFieldsToUpdate(dboSol.CAMPO_ESTADO,Constantes.ESTADO_SOL_CANCELADA);
					dboSol.setField(dboSol.CAMPO_SOLICITUD_ID,dboPagSol.getField(dboPagSol.CAMPO_SOLICITUD_ID));
					dboSol.update();
					*/
					Solicitud sol = new Solicitud(dboPagSol.getField(dboPagSol.CAMPO_SOLICITUD_ID),conn);
					if(sol.getEstado().equals(Constantes.ESTADO_SOL_POR_VERIFICAR))
					{
						sol.setUsr_modi(user.getUserId());
						sol.actualizaEstadoSol(Constantes.ESTADO_SOL_CANCELADA);
					}
					else
					{
						throw new CustomException(Errors.EC_GENERIC_ERROR, "No se puede extornar la Solicitud", "errorPrepago");
					}
				}
			
				DboComprobante compro = new DboComprobante(dconn);
				compro.setField(DboComprobante.CAMPO_ABONO_ID, abonoId);
			
				if(!compro.find())
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se encontro comprobante", "errorPrepago");
			
				compro.setFieldsToUpdate(DboComprobante.CAMPO_ESTADO);
				compro.setField(DboComprobante.CAMPO_ESTADO, "1");
				compro.update();
			
				DboExtorno extorno = new DboExtorno(dconn);
			
				extorno.setField(DboExtorno.CAMPO_USR_CAJA, idTesorero);
				extorno.setField(DboExtorno.CAMPO_USR_TESO, idTesorero);
				extorno.setField(DboExtorno.CAMPO_MONTO, monto);
				extorno.setField(DboExtorno.CAMPO_GLOSA, glosa);
				extorno.setField(DboExtorno.CAMPO_ABONO_ID, abonoId);
				extorno.setField(DboExtorno.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				extorno.add();
			
				MultiDBObject m = new MultiDBObject(dconn);

				m.addDBObj("gob.pe.sunarp.extranet.dbobj.DboAbono", "a");
				m.addDBObj("gob.pe.sunarp.extranet.dbobj.DboMovimiento", "b");
					
				m.setForeignKey("a", DboAbono.CAMPO_MOVIMIENTO_ID, "b", DboMovimiento.CAMPO_MOVIMIENTO_ID);
					
				m.setField("a", DboAbono.CAMPO_ABONO_ID, abonoId);
				
				java.util.Vector vectorcillo =  m.searchAndRetrieve();
				
				if(vectorcillo.size() <= 0)
				{
					if (isTrace(this)) trace("No se pudo obtener Linea Prepago del match de las tablas Movimiento y Abono con AbonoId # " + abonoId + " - Registro no existe.", request);
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se pudo obtener Linea Prepago", "errorPrepago");
				}
				
				if(vectorcillo.size() > 1)
				{
					if (isTrace(this)) trace("No se pudo obtener Linea Prepago del match de las tablas Movimiento y Abono con AbonoId # " + abonoId + " - Existe mas de un registro y solo debe haber uno.", request);
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se pudo obtener Linea Prepago. Existe mas de una Linea Prepago", "errorPrepago");
				}
			
				m = (MultiDBObject) vectorcillo.get(0);
				
				String lineacita = m.getField("b", DboMovimiento.CAMPO_LINEA_PREPAGO_ID);
				
				PrepagoBean prepagoBean = new PrepagoBean();
				prepagoBean.setTransacId(-1);
				prepagoBean.setEsExtorno(true);
				prepagoBean.setMontoBruto(Double.parseDouble(monto));
				prepagoBean.setLineaPrepagoId(lineacita);
				
				if(tpoAbono.equalsIgnoreCase("D"))
					prepagoBean.setEsFgDeposito(true);
				else
					prepagoBean.setEsFgDeposito(false);
				
				//Tarifario
				if(!prepagoBean.getLineaPrepagoId().equals(""+Comodin.getInstance().getLineaPrePago()))
				{
					LineaPrepago lineaCmd = new LineaPrepago();
					lineaCmd.reduceSaldo(user, prepagoBean, dconn);
				}
				
				conn.commit();
				
				
				
				if(abonoId!=null){
				
					 //nroAbono =  (String)session.getAttribute("nroAbono");
                //Empieza el QUERY

				  DiarioRecaudaDetalleBean bean = null;
				  DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
				  vista.setField(DboVwDiariorecauda.CAMPO_ABONO_ID, abonoId);
					
				
			  ArrayList lista = vista.searchAndRetrieveList(DboVwDiariorecauda.CAMPO_ABONO_ID+"|"+DboVwDiariorecauda.CAMPO_TS_CREA+"|"+
																	  DboVwDiariorecauda.CAMPO_NOMBRES+"|"+DboVwDiariorecauda.CAMPO_MONTO+"|"+
																	  DboVwDiariorecauda.CAMPO_ESTADO
																	  );
							
			  List listaADevolver = new ArrayList();
			  Iterator i = lista.iterator();
			  while(i.hasNext()){
				  //encontro = true;
			  DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();
			  bean = new DiarioRecaudaDetalleBean();
				
			  bean.setIdCajero(x.getField(DboVwDiariorecauda.CAMPO_USR_CAJA));
			  bean.setNroRecibo("");
			  //bean.setNroAbonoExtranet(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
			  bean.setHora(x.getField(DboVwDiariorecauda.CAMPO_TS_CREA));
			  bean.setIdAbono(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
			  bean.setTipoPersona((x.getField(DboVwDiariorecauda.CAMPO_TIPO_USR).trim().equalsIgnoreCase("O"))?"PJ":"PN");
			  bean.setNombre(x.getField(DboVwDiariorecauda.CAMPO_APE_PAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_APE_MAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_NOMBRES));
			  bean.setMonto(x.getField(DboVwDiariorecauda.CAMPO_MONTO));
			  bean.setTipoPago((x.getField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT).trim().equalsIgnoreCase("E"))?"Efectivo":"Cheque");
			  bean.setEsAnulado(x.getField(DboVwDiariorecauda.CAMPO_ESTADO));
			  bean.setTipoAbono(x.getField(DboVwDiariorecauda.CAMPO_TIPO_VENT));
			  //bean.setNumComprobante(x.getField(DboVwDiariorecauda.CAMPO_COMPR));
			  bean.setNumComprobante("");
			  listaADevolver.add(bean);
								  }
			
			  req.setAttribute("listadoConsulta",listaADevolver);	
			  req.setAttribute("numeroAbono",abonoId);
				
				}
				else {
					fecha = (String)session.getAttribute("fecha");
					fecha_ = (String)session.getAttribute("fecha_");
					if (isTrace(this)) trace("Buscando movimientos del dia " + fecha.substring(0, 10), request);
					
				
					DiarioRecaudaDetalleBean bean = null;
					java.util.List lista = new java.util.ArrayList();

					DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
					//vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, caja);
					vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_);//cb
					

									  //*** MANEJO DE LA PAGINACION
												 // int num_pagina = Propiedades.getInstance().getLineasPorPag();
												 // vista.setMaxRecords(num_pagina);
												  //int paginacion = 1;
												  //if(request.getParameter("pagina") != null)
													//  paginacion = Integer.parseInt(request.getParameter("pagina"));
				
												  //*** FIN DE MANEJO DE PAGINACION						
												  //boolean hayNext = false;
												  //boolean encontro = false;

												  //java.util.List ve = vista.searchAndRetrieveListPaginado(paginacion);
					java.util.List ve = vista.searchAndRetrieveList(DboVwDiariorecauda.CAMPO_ABONO_ID+"|"+DboVwDiariorecauda.CAMPO_TS_CREA+"|"+
					DboVwDiariorecauda.CAMPO_NOMBRES+"|"+DboVwDiariorecauda.CAMPO_MONTO+"|"+
					DboVwDiariorecauda.CAMPO_ESTADO);
			
												  //if(vista.getHaySiguiente())
													 // hayNext = true;
			
												  for(Iterator i = ve.iterator(); i.hasNext();){
													 // encontro = true;

													  DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();
													  bean = new DiarioRecaudaDetalleBean();
				
													  bean.setIdCajero(x.getField(DboVwDiariorecauda.CAMPO_USR_CAJA));
				
													  String aux = null;
													  if(x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC) == null || x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC).trim().length() <= 0)
														  aux = "SIN ASIGNAR";
													  else
														  aux = x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
					
													  bean.setNroRecibo(aux);
													  //bean.setNroAbonoExtranet(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
													  bean.setHora(x.getField(DboVwDiariorecauda.CAMPO_TS_CREA));
													  bean.setIdAbono(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
													  bean.setTipoPersona((x.getField(DboVwDiariorecauda.CAMPO_TIPO_USR).trim().equalsIgnoreCase("O"))?"PJ":"PN");
													  bean.setNombre(x.getField(DboVwDiariorecauda.CAMPO_APE_PAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_APE_MAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_NOMBRES));
													  bean.setMonto(x.getField(DboVwDiariorecauda.CAMPO_MONTO));
													  bean.setTipoPago((x.getField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT).trim().equalsIgnoreCase("E"))?"Efectivo":"Cheque");
													  bean.setEsAnulado(x.getField(DboVwDiariorecauda.CAMPO_ESTADO));
													  bean.setTipoAbono(x.getField(DboVwDiariorecauda.CAMPO_TIPO_VENT));
													  //bean.setNumComprobante(x.getField(DboVwDiariorecauda.CAMPO_COMPR));
													  bean.setNumComprobante("");
													  lista.add(bean);
													  req.setAttribute("listadoConsulta",lista);
												  }
			
//										*PAGINACION EN EL JSP*//			
												  //if(paginacion == 1){
													//  if(hayNext)
													//	  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
													//  else
													//	  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				//
													//  ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
												//  }else{
												//	  if(hayNext)
												//		  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
												//	  else
												//		  ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
											//		  ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
												  }
												//  ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
												//  ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
				
										
			//	}
				
					
				
				transition("inicial", request, response);
				
				
				
				
				
			} catch (CustomException ce) {
				log(ce.getCodigoError(), ce.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(ce.getForward());
			} catch (DBException dbe) {
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
			return response;
		}
}
