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
public class AperturaCajeroController extends ControllerExtension  {

	public AperturaCajeroController() {
				super();
			
				addState(new State("aperturaInicio", "me lleva al form de apertura de cajeros"));
				addState(new State("aperturaCaja", "Apertura la caja actual"));
				setInitialState("aperturaInicio");
				
			}
			
	protected ControllerResponse runAperturaInicioState(ControllerRequest request, ControllerResponse response) throws ControllerException {

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
				String cuentaId = usuario.getCuentaId();
				String edificio = "";
				String descripcionEdificio="";
				String caja = usuario.getUserId();
				String estInicialCaja = "";
				DboTaCaja dboCaja = new DboTaCaja (dconn);			
				
				//validando el estado de la caja
				dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_ESTA);
				dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL,cuentaId);
				if(dboCaja.find()==true){
					estInicialCaja=dboCaja.getField(DboTaCaja.CAMPO_ESTA);
					
							
				}
				if(estInicialCaja.equals("2")){
						req.setAttribute("mensaje1","La caja "+ caja +" ya fué aperturada. Para aperturas adicionales primero debe cerrar su caja");
						response.setStyle("ok");
				}
				else if(estInicialCaja.equals("0")){
					
											req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada por el Tesorero");
											response.setStyle("ok");
				}
				else{
				
				dboCaja.clearAll();
				
				//Obteniendo la descripcion de Zona Registral
				DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
				dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
				dboRegisPublico.setField(DboRegisPublico.CAMPO_REG_PUB_ID,codigoZona);
			
				if(dboRegisPublico.find()== true){
					descZona = dboRegisPublico.getField(DboRegisPublico.CAMPO_NOMBRE); 
				}
			
				//Obteniendo el edificio
				
				dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_ZONA);
				dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL, cuentaId);
				if(dboCaja.find()== true){
								 edificio = dboCaja.getField(DboTaCaja.CAMPO_CO_ZONA);
							}
				
				//obteniendo la descripcion del edificio
				DboTATabl dboTATabl = new DboTATabl(dconn);
				dboTATabl.setFieldsToRetrieve(DboTATabl.CAMPO_DE_VALO);
				dboTATabl.setField(DboTATabl.CAMPO_VA_COLU, edificio);
				dboTATabl.setField(DboTATabl.CAMPO_CO_COLU, "CO_EDIF");
				if(dboTATabl.find()== true){
									 descripcionEdificio = dboTATabl.getField(DboTATabl.CAMPO_DE_VALO);
									}
							
				//mandando datos al jsp
				req.setAttribute("nombreZona",descZona);
				req.setAttribute("fecha",fecha);
				req.setAttribute("edificio",descripcionEdificio);
				req.setAttribute("caja",caja);
				response.setStyle("aperturaInicio");	
				}
				dconn.commit();
				
						
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
		
	protected ControllerResponse runAperturaCajaState(ControllerRequest request, ControllerResponse response) throws ControllerException {

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
					String cuentaId = usuario.getCuentaId();
					String edificio = "";
					String idDiar="";
					String estadoCaja = "";
					String caja = usuario.getUserId();
					String coCaja = "";
													
					DboTaCaja dboCaja = new DboTaCaja (dconn);
//					Consultando el estado de la caja
													
					dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_ESTA);
					dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL,cuentaId);
					if(dboCaja.find()== true){
					estadoCaja = dboCaja.getField(DboTaCaja.CAMPO_ESTA);
					dboCaja.clear();
					}
					
					
					if(estadoCaja.equals("0")){
					req.setAttribute("mensaje1","El Tesorero no ha realizado la apertura general de las cajas");
					}
					else {								
					dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_ZONA +"|"+DboTaCaja.CAMPO_ID_DIAR+"|"+DboTaCaja.CAMPO_CO_CAJA);
					dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL, cuentaId);
					if(dboCaja.find()== true){
					idDiar = dboCaja.getField(DboTaCaja.CAMPO_ID_DIAR);
					coCaja = dboCaja.getField(DboTaCaja.CAMPO_CO_CAJA);
							}
					//Realizando la Actualizacion
					
					dboCaja.clear();
					dboCaja.setFieldsToUpdate(DboTaCaja.CAMPO_ESTA);
					dboCaja.setField(DboTaCaja.CAMPO_CO_CAJA, coCaja);
					dboCaja.setField(DboTaCaja.CAMPO_ID_DIAR, idDiar);
					dboCaja.setField(DboTaCaja.CAMPO_ESTA, "2");
					dboCaja.update();
					
					DboTaDiarCaja dboTaDiarCaja = new DboTaDiarCaja (dconn);
					dboTaDiarCaja.setFieldsToUpdate(DboTaDiarCaja.CAMPO_FH_APER_CAJE +"|"+DboTaDiarCaja.CAMPO_CO_EMPL_CAJE);
					
					
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_ID_DIAR, idDiar);
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_FH_APER_CAJE, "to_date('"+fecha+"','dd-mm-yyyy HH24:MI:SS')");
					dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_EMPL_CAJE, cuentaId);
					dboTaDiarCaja.update();
					
					
					dconn.commit();														
					req.setAttribute("caja",caja);
					req.setAttribute("mensaje1","La caja "+caja+" se aperturó correctamente. Para aperturas adicionales primero debe cerrar su caja");
					}
					
					response.setStyle("ok");
								  
						
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
