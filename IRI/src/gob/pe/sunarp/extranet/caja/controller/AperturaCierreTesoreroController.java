/*
 * Created on 10-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.caja.controller;

import gob.pe.sunarp.extranet.administracion.bean.DatosOrganizacionBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.SecuencialesCajas;
import gob.pe.sunarp.extranet.common.logica.Constantes;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import java.util.ArrayList;
import java.util.Iterator;

import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.*;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;
import javax.servlet.http.*;
import java.sql.*;
import gob.pe.sunarp.extranet.util.*;
import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.framework.session.*;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.*;
import java.math.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.PresentacionSolicitudInscripcion;
import java.text.SimpleDateFormat;
import gob.pe.sunarp.extranet.caja.*;
import gob.pe.sunarp.extranet.caja.bean.DetalleCajaBean;
/**
 * @author jbugarin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AperturaCierreTesoreroController extends ControllerExtension {
	
	public AperturaCierreTesoreroController() {
			super();
			
			addState(new State("aperturaCierreInicio", "me lleva al form de apertura cierre"));
			addState(new State("aperturarCajas", "Aperturar Todas las cajas"));
			addState(new State("cierreInicio", "Cerrar la caja"));
			addState(new State("cierreCaja"," boton de Cerrar la caja"));
			setInitialState("aperturaCierreInicio");
				
		}
	protected ControllerResponse runAperturaCierreInicioState(ControllerRequest request, ControllerResponse response) throws ControllerException {

	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		String descZona = "";
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			//recuperando datos de usuario
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			//recuperando el codigo de zonaRegistral
			String codigoZona = usuario.getRegPublicoId();
			String fecha = FechaUtil.getCurrentDate();
			//String moneda = request.getParameter("CboTipoMoneda");
			
			
			//System.out.print("regpubid::" +codigoZona);
			//Obteniendo la descripcion de Zona Registral
				DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
				dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
				dboRegisPublico.setField(DboRegisPublico.CAMPO_REG_PUB_ID,codigoZona);
			
				if(dboRegisPublico.find()== true){
					descZona = dboRegisPublico.getField(DboRegisPublico.CAMPO_NOMBRE); 
				}
			//String monto = request.getParameter("txtTipoCambio");
			//System.out.println("JLBPFECHA:::"+fecha);
			//mandando datos al jsp
			req.setAttribute("zonaRegistral",descZona);
			req.setAttribute("fecha",fecha);
			
			
			//haciendo consulta si se inserto un monto y un tipo de cambio
			DboTaCamb dboTaCamb = new DboTaCamb(dconn);
			dboTaCamb.setFieldsToRetrieve(DboTaCamb.CAMPO_MONTO+"|"+DboTaCamb.CAMPO_CO_TIPO_MONE);
			dboTaCamb.setField(dboTaCamb.CAMPO_FECHA,"to_date('"+fecha+"','dd-mm-yyyy HH24:MI:SS')");
			//MODIFICADO OBSERVACIONES PRODUCCION SUNARP CAJEROS 22/03/2007
			dboTaCamb.setField(dboTaCamb.CAMPO_CO_REGI,codigoZona);
			//FIN MODIFICADO
			if(dboTaCamb.find()==true){
				req.setAttribute("monto",dboTaCamb.getField(DboTaCamb.CAMPO_MONTO));
				req.setAttribute("moneda",dboTaCamb.getField(DboTaCamb.CAMPO_CO_TIPO_MONE));
			}
			//haciendo consulta del listado de cajas
			DboTaCaja dboCaja = new DboTaCaja (dconn);
			dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_CAJA + "|" + DboTaCaja.CAMPO_CO_EMPL + "|" +
										DboTaCaja.CAMPO_DE_CAJA+"|"+DboTaCaja.CAMPO_ESTA);
			dboCaja.setField(DboTaCaja.CAMPO_CO_SEDE, codigoZona);
			//lista para almacenar las cajas asignadas a un Tesorero
			ArrayList lista = dboCaja.searchAndRetrieveList(DboTaCaja.CAMPO_CO_CAJA);
			
			ArrayList listaDetalleCaja = new ArrayList();
			Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				DetalleCajaBean det = null;
				DboTaCaja element = (DboTaCaja) iterator.next();
				DboCuenta dboCuenta = new DboCuenta(dconn);	
				det = new DetalleCajaBean();
				dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
				dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID,element.getField(DboTaCaja.CAMPO_CO_EMPL)); 
							
				if(dboCuenta.find()==true){
					String userId = dboCuenta.getField(DboCuenta.CAMPO_USR_ID);
					System.out.println("userIDCOn::"+userId);
					det.setCajeroAsignado(userId);
				}
					det.setCaja(element.getField(DboTaCaja.CAMPO_CO_CAJA));
					det.setEstado(element.getField(DboTaCaja.CAMPO_ESTA));
				
				if(det.getEstado().equals(DetalleCajaBean.ESTADO_CERRADO)){
					det.setDesEstado("CERRADO");
				} else if(det.getEstado().equals(DetalleCajaBean.APERTURA_GENERAL)){
					det.setDesEstado("APERTURADO GENERAL");
				} else if(det.getEstado().equals(DetalleCajaBean.APERTURA_CAJERO)){
					det.setDesEstado("APERTURA CAJERO");
				}
			
				listaDetalleCaja.add(det);			
				
				
			}
			

			
			req.setAttribute("listaDetalleCaja",listaDetalleCaja);
			response.setStyle("aperturaCierreInicio");
			
						
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}

		return response;
	}
	
	protected ControllerResponse runAperturarCajasState(ControllerRequest request, ControllerResponse response) throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		String descZona = "";
		
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			try {
				init(request);
				validarSesion(request);
			
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
			
				//recuperando datos de usuario
				UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
				//recuperando el codigo de zonaRegistral
				String codigoZona = usuario.getRegPublicoId();
				String fecha = FechaUtil.getCurrentDateTime();
				String fecha2 = FechaUtil.getCurrentDate();
				//Recuperando datos de Tipo de Cambio y Moneda
				String moneda = request.getParameter("cboTipoMoneda");
				String monto = request.getParameter("txtTipoCambio");
				req.setAttribute("monto", monto);
				req.setAttribute("moneda", moneda);
				System.out.println("monto cambio:::"+monto);
				
				
				//Obteniendo la descripcion de Zona Registral
				DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
				dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
				dboRegisPublico.setField(DboRegisPublico.CAMPO_REG_PUB_ID,codigoZona);
			
				if(dboRegisPublico.find()== true){
					descZona = dboRegisPublico.getField(DboRegisPublico.CAMPO_NOMBRE); 
				}
				//haciendo consulta si se inserto un monto y un tipo de cambio
				DboTaCamb dboTaCambaux = new DboTaCamb(dconn);
				dboTaCambaux.setFieldsToRetrieve(DboTaCamb.CAMPO_MONTO+"|"+DboTaCamb.CAMPO_CO_TIPO_MONE);
				dboTaCambaux.setField(dboTaCambaux.CAMPO_FECHA,"to_date('"+fecha+"','dd-mm-yyyy HH24:MI:SS')");
				if(dboTaCambaux.find()==true){
				req.setAttribute("monto",dboTaCambaux.getField(DboTaCamb.CAMPO_MONTO));
				req.setAttribute("moneda",dboTaCambaux.getField(DboTaCamb.CAMPO_CO_TIPO_MONE));
				}		
				//Insertar en la tabla TA_DIAR_CAJA
				//haciendo consulta del listado de cajas
				DboTaCaja dboCaja = new DboTaCaja (dconn);
				dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_CAJA + "|" + 
											DboTaCaja.CAMPO_CO_EMPL + "|" + 
											DboTaCaja.CAMPO_DE_CAJA + "|" + 
											DboTaCaja.CAMPO_ESTA + "|" + 
											DboTaCaja.CAMPO_CO_SEDE + "|" + 
											DboTaCaja.CAMPO_CO_ZONA + "|" + 
											DboTaCaja.CAMPO_CO_REGI);
				dboCaja.setField(DboTaCaja.CAMPO_CO_SEDE, codigoZona);
				dboCaja.setField(DboTaCaja.CAMPO_ESTA, DetalleCajaBean.ESTADO_CERRADO);
				ArrayList listaCajaAux = dboCaja.searchAndRetrieveList(DboTaCaja.CAMPO_CO_CAJA);
			
				//Insertamos en TA_DIAR_CAJA por cada registro en listaCajaAux y Actualizamos TA_CAJA el ESTA = 1
				DboTaDiarCaja dboTaDiarCaja = null;
				DboTaCaja dboTaCajaAux = null;
				
				
				
				Iterator iterator = listaCajaAux.iterator();
				while (iterator.hasNext()) {
					DboTaCaja element = (DboTaCaja) iterator.next();
					dboTaDiarCaja = new DboTaDiarCaja(dconn);
					String idDiar = SecuencialesCajas.getInstance().getIdDiarCaja(conn).toString();
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_ID_DIAR, idDiar);
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_CAJA, element.getField(DboTaCaja.CAMPO_CO_CAJA));
					String co_caja = element.getField(DboTaCaja.CAMPO_CO_CAJA);
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_EMPL_CAJE, null);
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_EMPL_TESO, usuario.getCuentaId());
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_FH_APER, "to_date('"+fecha+"','dd-mm-yyyy HH24:MI:SS')");
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_FH_APER_CAJE, null);
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_FH_CIER_CAJE, null);
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_REGI, element.getField(DboTaCaja.CAMPO_CO_REGI));
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_ZONA, element.getField(DboTaCaja.CAMPO_CO_ZONA));
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_SEDE, element.getField(DboTaCaja.CAMPO_CO_SEDE));
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_DE_CAJA, element.getField(DboTaCaja.CAMPO_DE_CAJA));										
					dboTaDiarCaja.add();
					//Actusalizando TA_CAJA
							
					dboTaCajaAux = new DboTaCaja(dconn);
					dboTaCajaAux.setFieldsToUpdate(DboTaCaja.CAMPO_CO_CAJA + "|" + DboTaCaja.CAMPO_ID_DIAR + "|" + DboTaCaja.CAMPO_ESTA);
					dboTaCajaAux.setField(DboTaCaja.CAMPO_CO_CAJA, co_caja);
					dboTaCajaAux.setField(DboTaCaja.CAMPO_ID_DIAR, idDiar);
					dboTaCajaAux.setField(DboTaCaja.CAMPO_ESTA, DetalleCajaBean.APERTURA_GENERAL);
					dboTaCajaAux.update();	
					
				
				}
				
				//Insertando en TABLA TA_CAMB
					DboTaCamb aux =  new DboTaCamb(dconn);
							aux.setFieldsToRetrieve(DboTaCamb.CAMPO_CO_REGI+"|"+DboTaCamb.CAMPO_FECHA);
							aux.setField(DboTaCamb.CAMPO_CO_REGI,codigoZona);
							aux.setField(DboTaCamb.CAMPO_FECHA,"to_date('"+fecha2+"','dd-mm-yyyy HH24:MI:SS')");
								
							if(aux.find()==false){
																									  
							  DboTaCamb dboTaCamb = new DboTaCamb(dconn);
							  dboTaCamb.setField(DboTaCamb.CAMPO_CO_REGI,codigoZona);
							  dboTaCamb.setField(DboTaCamb.CAMPO_FECHA, "to_date('"+fecha2+"','dd-mm-yyyy HH24:MI:SS')");
							  dboTaCamb.setField(DboTaCamb.CAMPO_CO_TIPO_MONE,moneda);
							  dboTaCamb.setField(DboTaCamb.CAMPO_MONTO,monto);
							  dboTaCamb.add();
						}
				
				
				
				
				//Recuperamos nuevamente la lista para mostrarla
				DboTaCaja dboCajaForView = new DboTaCaja (dconn);
				dboCajaForView.setFieldsToRetrieve(DboTaCaja.CAMPO_ESTA + "|" + DboTaCaja.CAMPO_CO_EMPL + "|" +
											DboTaCaja.CAMPO_DE_CAJA + "|" + DboTaCaja.CAMPO_CO_CAJA );
				dboCajaForView.setField(DboTaCaja.CAMPO_CO_SEDE, codigoZona);
				ArrayList listaForView = dboCajaForView.searchAndRetrieveList(DboTaCaja.CAMPO_CO_CAJA);
				ArrayList listaDetalleCajaForView = new ArrayList();
			
				DetalleCajaBean detForView = null;
				Iterator iteratorForView = listaForView.iterator();
				while (iteratorForView.hasNext()) {
					DboTaCaja element = (DboTaCaja) iteratorForView.next();
					detForView = new DetalleCajaBean();
					DboCuenta dboCuenta = new DboCuenta(dconn);
					dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
									dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID,element.getField(DboTaCaja.CAMPO_CO_EMPL)); 
							
									if(dboCuenta.find()==true){
										String userId = dboCuenta.getField(DboCuenta.CAMPO_USR_ID);
										System.out.println("userIDCOn::"+userId);
										detForView.setCajeroAsignado(userId);
									}
					
					detForView.setCaja(element.getField(DboTaCaja.CAMPO_CO_CAJA));
					//detForView.setCajeroAsignado(element.getField(DboTaCaja.CAMPO_CO_EMPL));
					detForView.setEstado(element.getField(DboTaCaja.CAMPO_ESTA));
					
					
					
					
					if(detForView.getEstado().equals(DetalleCajaBean.ESTADO_CERRADO)){
						detForView.setDesEstado("CERRADO");
					} else if(detForView.getEstado().equals(DetalleCajaBean.APERTURA_GENERAL)){
						detForView.setDesEstado("APERTURADO GENERAL");
					} else if(detForView.getEstado().equals(DetalleCajaBean.APERTURA_CAJERO)){
						detForView.setDesEstado("APERTURA CAJERO");
					}
					listaDetalleCajaForView.add(detForView);
				}
			
				req.setAttribute("listaDetalleCaja",listaDetalleCajaForView);

				//mandando datos al jsp
				req.setAttribute("zonaRegistral",descZona);
				String fechaView = FechaUtil.getCurrentDate();
				req.setAttribute("fecha",fechaView);
				dconn.commit();
				response.setStyle("aperturaCierreInicio");
			
						
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			} catch (DBException dbe) {
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
			} finally {
				pool.release(conn);
				end(request);
			}

			return response;
		}
	protected ControllerResponse runCierreInicioState(ControllerRequest request, ControllerResponse response) throws ControllerException {

			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
				String descZona = "";
				//String usuarioCaja = "";
				
		
				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

				try {
					
					init(request);
					validarSesion(request);
			
					conn = pool.getConnection();
					conn.setAutoCommit(false);
					DBConnection dconn = new DBConnection(conn);
			
					//recuperando datos de usuario
					UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
					//recuperando el codigo de zonaRegistral
					String codigoZona = usuario.getRegPublicoId();
					//String fecha = FechaUtil.getCurrentDate();
					String fecha_aux = "";
					//String usuarioCaja = usuario.getUserId();
					//String caja = usuario.getCuentaId();
					String caja="";
					String usuarioCaja = request.getParameter("hid1");
					String idDiar = "";
					String fechaAperturaCajero="";
					
					
					DboCuenta dboCuenta = new DboCuenta(dconn);
					dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
					dboCuenta.setField(DboCuenta.CAMPO_USR_ID,usuarioCaja);
					if (dboCuenta.find()==true){
					caja = (dboCuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
					System.out.println("caja__cont"+caja);
					}
					
					
					String dia,mes,ano;
					String fecha="";
					String _fecha="";
					int _d,_a,_m;
					String estadoCaja ="";
				
					//Convirtiendo a formatao de fechas
					fecha_aux  = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
					dia = fecha_aux.substring(0,2);
					mes = fecha_aux.substring(3,5);
					ano = fecha_aux.substring(6,10);
					_d = Integer.parseInt(dia);
					_m = Integer.parseInt(mes);
					_a = Integer.parseInt(ano);
					fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
					_fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);
				   //Consultando el estado de la caja
									  DboTaCaja dboCaja = new DboTaCaja(dconn);
					dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_ESTA + "|" + DboTaCaja.CAMPO_ID_DIAR);
									  dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL,caja);
									  if(dboCaja.find()== true){
									  estadoCaja = dboCaja.getField(DboTaCaja.CAMPO_ESTA);
									  idDiar = dboCaja.getField(DboTaCaja.CAMPO_ID_DIAR);
									  }
					
					if (estadoCaja.equals("0")&& !estadoCaja.equals("")){
					req.setAttribute("mensaje1","La caja "+ usuarioCaja +"se encuentra cerrada.");
					response.setStyle("ok");
					}else {								  
					
					//consultando fecha de Apertura del Cajero
					DboTaDiarCaja dboTaDiarCaja = new DboTaDiarCaja(dconn);
					dboTaDiarCaja.setFieldsToRetrieve(DboTaDiarCaja.CAMPO_FH_APER_CAJE);
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_ID_DIAR,idDiar);
					if(dboTaDiarCaja.find()==true){
					fechaAperturaCajero=dboTaDiarCaja.getField(DboTaDiarCaja.CAMPO_FH_APER_CAJE);
					}
					//Consultando Recaudacion en efectivo
					String estadoSinExtorno = "1";
					DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
					vista.setField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT,"E");
					vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA,usuarioCaja);
					vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + _fecha
					+ " AND " + DboVwDiariorecauda.CAMPO_ESTADO + " = " + estadoSinExtorno);//modificado jbugarin
					double recaudEfectivo = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
					String monto = Double.toString(recaudEfectivo);
					req.setAttribute("efectivo",monto);
					vista.clear();
				
					//consultando Recaudacion cheque
					vista.setField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT,"C");
					vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA,usuarioCaja);
					vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + _fecha
					+ " AND " + DboVwDiariorecauda.CAMPO_ESTADO + " = " + estadoSinExtorno);//modificado jbugarin
					double recaudCheque = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
					String montoCheque = Double.toString(recaudCheque);
					req.setAttribute("cheque",Tarea.formatoNumero(montoCheque));
					req.setAttribute("nombreCaja",usuarioCaja);
					req.setAttribute("estadoCierre",estadoCaja);
					req.setAttribute("usuarioCaja", caja);
					req.setAttribute("fechaAperturaCajero",FechaUtil.expressoDateTimeToUtilDateTime(fechaAperturaCajero));
					response.setStyle("cierreInicio");
					}
					
					//si la caja solo fue aperturada por el tesorero
					/*if (estadoCaja.equals("1")){
						req.setAttribute("mensaje1","La caja "+ usuarioCaja +"se encuentra cerrada.");
						
						response.setStyle("ok");
						
					}*/
			
						
				} catch (CustomException e) {
					log(e.getCodigoError(), e.getMessage(), request);
					principal(request);
					rollback(conn, request);
					response.setStyle(e.getForward());
				} catch (DBException dbe) {
					log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
					principal(request);
					rollback(conn, request);
					response.setStyle("error");
				} catch (Throwable ex) {
					log(Errors.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					rollback(conn, request);
					response.setStyle("error");
				} finally {
					pool.release(conn);
					end(request);
				}

				return response;
			}
	
	protected ControllerResponse runCierreCajaState(ControllerRequest request, ControllerResponse response) throws ControllerException {

				DBConnectionFactory pool = DBConnectionFactory.getInstance();
				Connection conn = null;
					String descZona = "";
					String idDiar = null;
		
					HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

					try {
						
						init(request);
						validarSesion(request);
			
						conn = pool.getConnection();
						conn.setAutoCommit(false);
						DBConnection dconn = new DBConnection(conn);
			
						//recuperando datos de usuario
						UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
						//recuperando el codigo de zonaRegistral
						String codigoZona = usuario.getRegPublicoId();
						String fecha = FechaUtil.getCurrentDateTime();
						String usuarioCaja = request.getParameter("hid1");
					
						
						String codigoCaja = "";
						String estadoCaja = "";
						//haciendo consulta si se inserto un monto y un tipo de cambio
						DboTaCamb dboTaCamb = new DboTaCamb(dconn);
						dboTaCamb.setFieldsToRetrieve(DboTaCamb.CAMPO_MONTO+"|"+DboTaCamb.CAMPO_CO_TIPO_MONE);
						dboTaCamb.setField(dboTaCamb.CAMPO_FECHA,"to_date('"+fecha+"','dd-mm-yyyy HH24:MI:SS')");
						if(dboTaCamb.find()==true){
						req.setAttribute("monto",dboTaCamb.getField(DboTaCamb.CAMPO_MONTO));
						req.setAttribute("moneda",dboTaCamb.getField(DboTaCamb.CAMPO_CO_TIPO_MONE));
						}
				    
						DboTaCaja dboCaja = new DboTaCaja (dconn);
						//consultando el id diar
						dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_ZONA +"|"+DboTaCaja.CAMPO_ID_DIAR+"|"+
						DboTaCaja.CAMPO_CO_CAJA);
						dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL, usuarioCaja);
						if(dboCaja.find()== true){
						idDiar = dboCaja.getField(DboTaCaja.CAMPO_ID_DIAR);
						codigoCaja = dboCaja.getField(DboTaCaja.CAMPO_CO_CAJA);
						}
					
						DboTaBill dboTabill = new DboTaBill(dconn);
						//recuperando los cantidades de billetes y monedas
						String billDosc = request.getParameter("txtDoscientos");
						String montoBillDosc = request.getParameter("txtMontoBillete1");
					
					
						if(billDosc.equals("")){
						//Insertando en TA_BILL datos vacios
										  dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										  dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"14");
										  dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										  dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										  dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										  dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										  dboTabill.add();
										  dboTabill.clear();
					
						}
						else{
						//Insertando en TA_BILL
										  dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										  dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"14");
										  dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										  dboTabill.setField(DboTaBill.CAMPO_CANT,billDosc);
										  dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoBillDosc);
										  dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										  dboTabill.add();
										  dboTabill.clear();
						}
					
						String billCien = request.getParameter("txtCien");
						String montoBillCien = request.getParameter("txtMontoBillete2");
					
						if(billCien.equals("")){
					   //Insertando en TA_BILL
										dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"01");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						} else {				
					
										dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"01");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,billDosc);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoBillCien);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
					
						String billCincuenta = request.getParameter("txtCincuenta");
						String montoBillCincuenta = request.getParameter("txtMontoBillete3");
                   
						if(billCincuenta.equals("")){
							dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"02");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);										dboTabill.add();
										dboTabill.clear();	
						}
                   
					   else {
					   //Insertando en TA_BILL
										dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"02");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,billCincuenta);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoBillCincuenta);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
					   }
					
						String billVeinte = request.getParameter("txtVeinte");
						String montoBillVeinte = request.getParameter("txtMontoBillete4");
					
						if(billVeinte.equals("")){
										dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"15");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {
					
						//Insertando en TA_BILL
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"15");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,billVeinte);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoBillVeinte);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
					
						String billDiez = request.getParameter("txtDiez");
						String montoBillDiez = request.getParameter("txtMontoBillete5");
					
						if(billDiez.equals("")){
										dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"03");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {
					
						//Insertando en TA_BILL
										dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"03");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,billDiez);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoBillDiez);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
					
						String monCinco = request.getParameter("txtCincons");
						String montoMonCinco = request.getParameter("txtMontoMoneda1");
					
						if(monCinco.equals("")){
							dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"13");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}else {
					
						//Insertando en TA_BILL
										dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"13");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,monCinco);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoMonCinco);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
					
						String mondos = request.getParameter("txtDosns");
						String montoMonDos = request.getParameter("txtMontoMoneda2");
					
						if(mondos.equals("")){
							dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"17");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {
					
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"17");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,mondos);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoMonDos);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}				
					
						String monNuevoSol = request.getParameter("txtUnns");				
						String montoMonNuevoSol = request.getParameter("txtMontoMoneda3");
					
						if(monNuevoSol.equals("")){
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"06");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {	
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"06");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,monNuevoSol);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoMonNuevoSol);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
									
						String monCincuentaCentimos = request.getParameter("txtCincuentac");
						String montoMonCincuentaCentimos = request.getParameter("txtMontoMoneda4");
					
						if(monCincuentaCentimos.equals("")){
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);				
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"05");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"05");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,monCincuentaCentimos);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoMonCincuentaCentimos);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}				
					
						String monVeinteCentimos = request.getParameter("txtVeintec");
						String montoMonVeinteCentimos = request.getParameter("txtMontoMoneda5");
					
						if(monVeinteCentimos.equals("")){
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"20");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"20");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,monVeinteCentimos);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoMonVeinteCentimos);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
					
						String monDiezCentimos = request.getParameter("txtDiezc");
						String montoMonDiezCentimos = request.getParameter("txtMontoMoneda6");
					
						if(monDiezCentimos.equals("")){
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);				
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"04");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"04");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,monDiezCentimos);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoMonDiezCentimos);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
					
						String monCincoCentimos = request.getParameter("txtCincoc");
						String montoMonCincoCentimos = request.getParameter("txtMontoMoneda7");
					
						if(monCincoCentimos.equals("")){
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);				
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"21");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,0);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,0);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
						else {
						dboTabill.setField(DboTaBill.CAMPO_ID_DIAR,idDiar);
										dboTabill.setField(DboTaBill.CAMPO_CO_BIMO,"21");
										dboTabill.setField(DboTaBill.CAMPO_CO_OPER,"02");
										dboTabill.setField(DboTaBill.CAMPO_CANT,monCincoCentimos);
										dboTabill.setField(DboTaBill.CAMPO_MO_TOTA,montoMonCincoCentimos);
										dboTabill.setField(DboTaBill.CAMPO_CO_TIPO_MONE,1);
										dboTabill.add();
										dboTabill.clear();
						}
										
						//Haciendo Actualizacion en TA_DIAR_CAJA
						DboTaDiarCaja dboTaDiarCaja = new DboTaDiarCaja (dconn);
						dboTaDiarCaja.setFieldsToUpdate(DboTaDiarCaja.CAMPO_FH_CIER_CAJE);
						dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_ID_DIAR, idDiar);
						dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_FH_CIER_CAJE, "to_date('"+fecha+"','dd-mm-yyyy HH24:MI:SS')");
						dboTaDiarCaja.update();
						dboCaja.clear();				
					
						//Actualizando estado de caja en TA_CAJA
						dboCaja.setFieldsToUpdate(DboTaCaja.CAMPO_ESTA);
						dboCaja.setField(DboTaCaja.CAMPO_CO_CAJA, codigoCaja);
						dboCaja.setField(DboTaCaja.CAMPO_ESTA, "0");
						dboCaja.update();
					
						
						
											
						//recuperando datos de usuario
						//UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
						//recuperando el codigo de zonaRegistral
						//String codigoZona = usuario.getRegPublicoId();
						//String fecha = FechaUtil.getCurrentDate();
			
						System.out.print("regpubid::" +codigoZona);
						//Obteniendo la descripcion de Zona Registral
						DboOficRegistral dboOficRegistral = new DboOficRegistral(dconn);
						dboOficRegistral.setFieldsToRetrieve(DboOficRegistral.CAMPO_NOMBRE);
						dboOficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,codigoZona);
			
						if(dboOficRegistral.find()== true){
							descZona = dboOficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE); 
						}
			
						//mandando datos al jsp
						req.setAttribute("zonaRegistral",descZona);
						req.setAttribute("fecha",fecha);
			
						//haciendo consulta del listado de cajas
						//DboTaCaja dboCaja = new DboTaCaja (dconn);
						dboCaja.clear();
						dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_CAJA + "|" + DboTaCaja.CAMPO_CO_EMPL + "|" +
										DboTaCaja.CAMPO_DE_CAJA+"|"+DboTaCaja.CAMPO_ESTA);
						dboCaja.setField(DboTaCaja.CAMPO_CO_SEDE, codigoZona);
						//lista para almacenar las cajas asignadas a un Tesorero
						ArrayList lista = dboCaja.searchAndRetrieveList(DboTaCaja.CAMPO_CO_CAJA);
			
						ArrayList listaDetalleCaja = new ArrayList();
						Iterator iterator = lista.iterator();
						while (iterator.hasNext()) {
							DetalleCajaBean det = null;
							DboTaCaja element = (DboTaCaja) iterator.next();
							DboCuenta dboCuenta = new DboCuenta(dconn);	
							det = new DetalleCajaBean();
							dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
							dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID,element.getField(DboTaCaja.CAMPO_CO_EMPL)); 
										
							if(dboCuenta.find()==true){
								String userId = dboCuenta.getField(DboCuenta.CAMPO_USR_ID);
								
								det.setCajeroAsignado(userId);
							}
								det.setCaja(element.getField(DboTaCaja.CAMPO_CO_CAJA));
								det.setEstado(element.getField(DboTaCaja.CAMPO_ESTA));
							
							if(det.getEstado().equals(DetalleCajaBean.ESTADO_CERRADO)){
								det.setDesEstado("CERRADO");
							} else if(det.getEstado().equals(DetalleCajaBean.APERTURA_GENERAL)){
								det.setDesEstado("APERTURADO GENERAL");
							} else if(det.getEstado().equals(DetalleCajaBean.APERTURA_CAJERO)){
								det.setDesEstado("APERTURA CAJERO");
							}
						
							listaDetalleCaja.add(det);			
							
							
						}
						
						dconn.commit();
			
						req.setAttribute("listaDetalleCaja",listaDetalleCaja);
						response.setStyle("aperturaCierreInicio");

								
			
						
					} catch (CustomException e) {
						log(e.getCodigoError(), e.getMessage(), request);
						principal(request);
						rollback(conn, request);
						response.setStyle(e.getForward());
					} catch (DBException dbe) {
						log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
					} catch (Throwable ex) {
						log(Errors.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
					} finally {
						pool.release(conn);
						end(request);
					}

					return response;
				}
}
