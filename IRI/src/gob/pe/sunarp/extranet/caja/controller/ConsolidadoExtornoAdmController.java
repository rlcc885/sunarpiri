/*
 * Created on 26-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.caja.controller;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.caja.bean.ConsolidadoExtornoAdmBean;
import gob.pe.sunarp.extranet.caja.bean.ConsolidadoExtornoBean;
import gob.pe.sunarp.extranet.caja.bean.DetalleCajaResumenBean;
import gob.pe.sunarp.extranet.caja.bean.DetalleResumenBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboAbono;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboExtorno;
import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.dbobj.DboTaCaja;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoVenta;
import gob.pe.sunarp.extranet.dbobj.DboVwDiariorecauda;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.DiarioRecaudaDetalleBean;
import gob.pe.sunarp.extranet.util.ComboBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.Tarea;
/**
 * @author jbugarin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConsolidadoExtornoAdmController extends ControllerExtension {

	private String thisClass = ResumenIngresoTesoreroController.class.getName() + ".";

		public ConsolidadoExtornoAdmController() {
			super();
			addState(new State("muestra", "Muestra la ventana con el Filtro de Busqueda"));
			addState(new State("buscar", "Muestra la busqueda"));
			setInitialState("muestra");
		}
	protected ControllerResponse runMuestraState (
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
				//List listaEdificios = Tarea.getComboEdificios(dconn, ExpressoHttpSessionBean.getUsuarioBean(request).getRegPublicoId());
				//req.setAttribute("listaEdificios", listaEdificios);
			
				req.setAttribute("arrDays", FechaUtil.getReportDays());
				req.setAttribute("arrMonths", FechaUtil.getReportMonths());
				req.setAttribute("arrYears", FechaUtil.getReportYears());
			
				String hoy = FechaUtil.getCurrentDate();
				String hoyDia = hoy.substring(0,2);
				String hoyMes = hoy.substring(3,5);
				String hoyAnio = hoy.substring(6,10);
			
				req.setAttribute("selectedIDay",hoyDia);
				req.setAttribute("selectedIMonth",hoyMes);
				req.setAttribute("selectedIYear",hoyAnio);
				req.setAttribute("selectedFDay",hoyDia);
				req.setAttribute("selectedFMonth",hoyMes);
				req.setAttribute("selectedFYear",hoyAnio);
			
				//devolvemos el filtro de busqueda
				response.setStyle("filtro");
			
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
	protected ControllerResponse runBuscarState (
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
				//List listaEdificios = Tarea.getComboEdificios(dconn, ExpressoHttpSessionBean.getUsuarioBean(request).getRegPublicoId());

				//recuperamos los campos del filtro
				String anioInicio = request.getParameter("anioInicio");
				String mesInicio = request.getParameter("mesInicio");
				String diaInicio = request.getParameter("diaInicio");
				String anioFin = request.getParameter("anioFin");
				String mesFin = request.getParameter("mesFin");
				String diaFin = request.getParameter("diaFin");
				//String codEdificio = request.getParameter("edificio");
				String fechaInicio = FechaUtil.stringTimeToOracleString(Integer.parseInt(diaInicio), Integer.parseInt(mesInicio), Integer.parseInt(anioInicio), 0, 0, 0);
				String fechaFin = FechaUtil.stringTimeToOracleString(Integer.parseInt(diaFin), Integer.parseInt(mesFin),Integer.parseInt(anioFin), 23, 59, 59);

				req.setAttribute("fechaInicio", diaInicio+"/"+mesInicio+"/"+anioInicio);
				req.setAttribute("fechaFin", diaFin+"/"+mesFin+"/"+anioFin);

				String fechaActual = FechaUtil.getCurrentDate();
				String horaActual = FechaUtil.getCurrentDateTime().substring(11,19);
				req.setAttribute("fechaActual", fechaActual);
				req.setAttribute("horaActual", horaActual);
								
				ConsolidadoExtornoBean bean = null;

				String tipoVenta = "";
				String descVenta = "";
				DboAbono dboAbono = new DboAbono(dconn);
				DboTmTipoVenta dboTipoVenta = new DboTmTipoVenta(dconn);
				//obtenenos los usuarios que son tesoreros			
				ResultSet rs = null;
				ArrayList listadoConsulta = new ArrayList();

				//obtenemos la lista de tesoreros
				Statement s = dconn.createStatement();
				rs = s.executeQuery("select t.cuenta_id, t.usr_id from cuenta t, perfil_cuenta d where t.cuenta_id = d.cuenta_id and d.perfil_id = '50' and t.estado='1'");
				List listaTesoreros = new ArrayList();
				while(rs.next()){
					String userId = rs.getString(2);
					
					listaTesoreros.add(userId);
				}

				//obtenemos los extornos de cada tesorero
				ConsolidadoExtornoAdmBean extornoPorTesorero = null;
				List listaExtornoPorTesorero = new ArrayList();
				DboExtorno dboExtorno = new DboExtorno(dconn);
				BigDecimal total = new BigDecimal(0);
				//recorremos la lista de tesoreros para obtener sus extornos
				for(int p = 0; p < listaTesoreros.size(); p++){
					
					extornoPorTesorero = new ConsolidadoExtornoAdmBean();
					
					dboExtorno.setField(DboExtorno.CAMPO_USR_CAJA, (String)listaTesoreros.get(p));
					dboExtorno.setAppendWhereClause(DboExtorno.CAMPO_TS_CREA + " BETWEEN " + fechaInicio + " AND " + fechaFin);//cb
					List listaExtornos = dboExtorno.searchAndRetrieveList();
					
					ConsolidadoExtornoBean extorno = null;
					List listaExtornoAux = new ArrayList();
					BigDecimal subTotal = new BigDecimal(0);
					for(Iterator i = listaExtornos.iterator(); i.hasNext();){
						
						extorno = new ConsolidadoExtornoBean();
						
						DboExtorno dboExtornoAux = (DboExtorno) i.next();
						extorno.setUsuarioTesorero(dboExtornoAux.getField(DboExtorno.CAMPO_USR_CAJA));
						extorno.setNroAbono(dboExtornoAux.getField(DboExtorno.CAMPO_ABONO_ID));
						String montoStringAux = dboExtornoAux.getField(DboExtorno.CAMPO_MONTO);
						if(montoStringAux.equals("")){
							montoStringAux = "0";
						}
						BigDecimal montoAux = new BigDecimal(montoStringAux);
						extorno.setMonto(Tarea.formatoNumero(dboExtornoAux.getField(DboExtorno.CAMPO_MONTO)));
						extorno.setMotivo(dboExtornoAux.getField(DboExtorno.CAMPO_GLOSA));
					    
						dboAbono.setFieldsToRetrieve(DboAbono.CAMPO_ABONO_ID+"|"+DboAbono.CAMPO_TIPO_VENT+"|"+DboAbono.CAMPO_USR_CAJA);//modificado jbugarin observaciones cajas
						dboAbono.setField(DboAbono.CAMPO_ABONO_ID,dboExtornoAux.getField(DboExtorno.CAMPO_ABONO_ID));
						if(dboAbono.find()==true){
							tipoVenta = dboAbono.getField(DboAbono.CAMPO_TIPO_VENT);
							//traigo el cajero que hizo la operacion
							extorno.setOperador(dboAbono.getField(DboAbono.CAMPO_USR_CAJA)); //jbugarin observaciones cajas
						}
					    
						dboTipoVenta.setFieldsToRetrieve(DboTmTipoVenta.CAMPO_TIPO_VENT+"|"+DboTmTipoVenta.CAMPO_DESCRIPCION);
						dboTipoVenta.setField(DboTmTipoVenta.CAMPO_TIPO_VENT,tipoVenta);
						if(dboTipoVenta.find()==true){
							descVenta = dboTipoVenta.getField(DboTmTipoVenta.CAMPO_DESCRIPCION);
						}
						extorno.setConcepto(descVenta);
						dboAbono.clear();
						dboTipoVenta.clear();
						
						listaExtornoAux.add(extorno);	
						
						//actualizamos el subtotal
						subTotal = subTotal.add(montoAux);
						
					}	
						
					extornoPorTesorero.setListaExtornos(listaExtornoAux);
					extornoPorTesorero.setSubTotal(subTotal);
					//para que en el reporte aparezca formateado con decimales
					extornoPorTesorero.setSubTotalFormat(Tarea.formatoNumero(subTotal.doubleValue()));
					
					//actualizamos el total
					total = total.add(subTotal);
						
					//limpiamos el dbo que usamos para obtener los extornos
					dboExtorno.clearAll();

					if(!listaExtornos.isEmpty()){
						listaExtornoPorTesorero.add(extornoPorTesorero);
					}

				}


				req.setAttribute("listaConsulta",listaExtornoPorTesorero);
				req.setAttribute("total",Tarea.formatoNumero(total.doubleValue()));
				response.setStyle("reporteExtornoPrint");
			
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
	
}
