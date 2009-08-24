/*
 * Created on 18/01/2007
 * by LSuarez
 * 
 */
package gob.pe.sunarp.extranet.caja.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
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

import gob.pe.sunarp.extranet.caja.bean.DetalleCajaResumenBean;
import gob.pe.sunarp.extranet.caja.bean.DetalleResumenBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.dbobj.DboRegisPublico;
import gob.pe.sunarp.extranet.dbobj.DboTaCaja;
import gob.pe.sunarp.extranet.dbobj.DboVwDiariorecauda;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.ComboBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.Tarea;

/**
 * @author lsuarez
 * 
 * Controller del Reporte Resumen de Ingreso de Tesorero
 * 
 */
public class ResumenIngresoTesoreroController extends ControllerExtension {

	private String thisClass = ResumenIngresoTesoreroController.class.getName() + ".";

	public ResumenIngresoTesoreroController() {
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
		
			List listaEdificios = Tarea.getComboEdificios(dconn, ExpressoHttpSessionBean.getUsuarioBean(request).getRegPublicoId());
			req.setAttribute("listaEdificios", listaEdificios);
			
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

			List listaEdificios = Tarea.getComboEdificios(dconn, ExpressoHttpSessionBean.getUsuarioBean(request).getRegPublicoId());

			//recuperamos los campos del filtro
			String anioInicio = request.getParameter("anioInicio");
			String mesInicio = request.getParameter("mesInicio");
			String diaInicio = request.getParameter("diaInicio");
			String anioFin = request.getParameter("anioFin");
			String mesFin = request.getParameter("mesFin");
			String diaFin = request.getParameter("diaFin");
			String codEdificio = request.getParameter("edificio");
			String fechaInicio = FechaUtil.stringTimeToOracleString(Integer.parseInt(diaInicio), Integer.parseInt(mesInicio), Integer.parseInt(anioInicio), 0, 0, 0);
			String fechaFin = FechaUtil.stringTimeToOracleString(Integer.parseInt(diaFin), Integer.parseInt(mesFin),Integer.parseInt(anioFin), 23, 59, 59);

			req.setAttribute("fechaInicio", diaInicio+"/"+mesInicio+"/"+anioInicio);
			req.setAttribute("fechaFin", diaFin+"/"+mesFin+"/"+anioFin);

			String fechaActual = FechaUtil.getCurrentDate();
			String horaActual = FechaUtil.getCurrentDateTime().substring(11,19);
			req.setAttribute("fechaActual", fechaActual);
			req.setAttribute("horaActual", horaActual);


			//obtenemos la descripcion de la zona
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			String codigoZonaRegistral = usuario.getRegPublicoId();
			String descZonaRegistral = "";
			DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
			dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
			dboRegisPublico.setField(DboRegisPublico.CAMPO_REG_PUB_ID, codigoZonaRegistral);
			if(dboRegisPublico.find() == true){
				descZonaRegistral = dboRegisPublico.getField(DboRegisPublico.CAMPO_NOMBRE);
			}
			/*DboOficRegistral dboOficRegistral = new DboOficRegistral(dconn);
			dboOficRegistral.setFieldsToRetrieve(DboOficRegistral.CAMPO_NOMBRE);
			dboOficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,codigoZonaRegistral);
			if(dboOficRegistral.find()== true){
				descZonaRegistral = dboOficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE); 
			}*/
			req.setAttribute("zonaRegistral", descZonaRegistral);

			//consulta listado de cajas
			DboTaCaja dboCaja = new DboTaCaja (dconn);
			dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_CAJA + "|" + DboTaCaja.CAMPO_CO_EMPL + "|" +
										DboTaCaja.CAMPO_CO_ZONA);
			dboCaja.setField(DboTaCaja.CAMPO_CO_SEDE, codigoZonaRegistral);
			if(!codEdificio.equals("ALL")){
				dboCaja.setField(DboTaCaja.CAMPO_CO_ZONA, codEdificio);
			}
			List lista = dboCaja.searchAndRetrieveList(DboTaCaja.CAMPO_CO_CAJA + "|" + DboTaCaja.CAMPO_CO_EMPL + "|" +
			DboTaCaja.CAMPO_CO_ZONA);
			
			//agrupamos por zona y calculamos totales
			BigDecimal totalGeneral = new BigDecimal(0);
			List listaResumen = new ArrayList();
			Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				DboTaCaja element = (DboTaCaja) iterator.next();
				String codigoEdificio = element.getField(DboTaCaja.CAMPO_CO_ZONA);
				//si no existe creo un resumen nuevo y agregamos la caja				
				DetalleResumenBean resumenBeanAux = existe(codigoEdificio, listaResumen);
				if(resumenBeanAux == null){
					DetalleResumenBean resumenBean = new DetalleResumenBean();
					resumenBean.setCodEdificio(codigoEdificio);
					//obtener descripcion
					resumenBean.setDescEdificio(obtenerDescEdificio(codigoEdificio, listaEdificios));
					DetalleCajaResumenBean cajaBean = new DetalleCajaResumenBean();
					cajaBean.setCodCaja(element.getField(DboTaCaja.CAMPO_CO_CAJA));
					cajaBean.setCuentaId(element.getField(DboTaCaja.CAMPO_CO_EMPL));
					//Obtener descripcion de la caja
					cajaBean.setDescCaja(obtenerDescCaja(element.getField(DboTaCaja.CAMPO_CO_EMPL), dconn));
					//obtener el total de ingreso de la caja
					cajaBean.setTotalIngresoCaja(obtenerTotalIngresoCaja(cajaBean.getDescCaja(), fechaInicio, fechaFin, dconn));
					resumenBean.getListaCajas().add(cajaBean);
					resumenBean.setTotalIngreso(cajaBean.getTotalIngresoCaja());
					totalGeneral = totalGeneral.add(cajaBean.getTotalIngresoCaja());
					listaResumen.add(resumenBean);
				} else {//si existe el resumen lo agrego a la lista de cajas del resumen
					DetalleCajaResumenBean cajaBean = new DetalleCajaResumenBean();
					cajaBean.setCodCaja(element.getField(DboTaCaja.CAMPO_CO_CAJA));
					cajaBean.setCuentaId(element.getField(DboTaCaja.CAMPO_CO_EMPL));
					//Obtener descripcion de la caja
					cajaBean.setDescCaja(obtenerDescCaja(element.getField(DboTaCaja.CAMPO_CO_EMPL), dconn));
					//obtener el total de ingreso de la caja
					cajaBean.setTotalIngresoCaja(obtenerTotalIngresoCaja(cajaBean.getDescCaja(), fechaInicio, fechaFin, dconn));
					resumenBeanAux.setTotalIngreso(resumenBeanAux.getTotalIngreso().add(cajaBean.getTotalIngresoCaja()));
					totalGeneral = totalGeneral.add(cajaBean.getTotalIngresoCaja());
					resumenBeanAux.getListaCajas().add(cajaBean);
				}
			}
			
			req.setAttribute("listaResumen", listaResumen);
			String totalGeneralAsString = "";
			if(totalGeneral == null){
				totalGeneralAsString = "0.00";
			} else {
				totalGeneralAsString = new DecimalFormat("0.00").format(totalGeneral.doubleValue());
			}
			req.setAttribute("totalGeneral", totalGeneralAsString);
			response.setStyle("resumenIngreso");
			
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


	/**
	 * @param string
	 * @return
	 */
	private String obtenerDescCaja(String cuentaId, DBConnection dconn) throws DBException {
		DboCuenta dboCuenta = new DboCuenta(dconn);
		dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
		dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID, cuentaId);
		if(dboCuenta.find()==true){
			return dboCuenta.getField(DboCuenta.CAMPO_USR_ID);
		}
		return "";
	}


	/**
	 * @param string
	 * @param dconn
	 */
	private BigDecimal obtenerTotalIngresoCaja(String userId, String fechaInicio, String fechaFin, DBConnection dconn) throws DBException {
		String estadoNoExtornado = "1";
		DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
		vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, userId);
		vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fechaInicio + " AND " + fechaFin + " AND " + DboVwDiariorecauda.CAMPO_ESTADO + "=" + estadoNoExtornado);
		List listaAbonos = vista.searchAndRetrieveList();
		Iterator iterator = listaAbonos.iterator();
		BigDecimal totalIngreso = new BigDecimal(0);
		while (iterator.hasNext()) {
			DboVwDiariorecauda element = (DboVwDiariorecauda) iterator.next();
			String montoAux = element.getField(DboVwDiariorecauda.CAMPO_MONTO);
			BigDecimal monto = new BigDecimal(montoAux);
			totalIngreso = totalIngreso.add(monto);				
		}
		return totalIngreso;
	}


	/**
	 * @param codigoEdificio
	 * @return
	 */
	private String obtenerDescEdificio(String codigoEdificio, List listaEdificios) {
		
		String descEdificio = "";
		Iterator iterator = listaEdificios.iterator();
		while (iterator.hasNext()) {
			ComboBean element = (ComboBean) iterator.next();
			if(element.getCodigo().equals(codigoEdificio)){
				return
				 element.getDescripcion();
			}
		}

		return descEdificio;
	}


	/**
	 * @param codigoEdificio
	 * @param listaResumen
	 * @return
	 */
	private DetalleResumenBean existe(String codigoEdificio, List listaResumen) {

		Iterator iterator = listaResumen.iterator();
		while (iterator.hasNext()) {
			DetalleResumenBean element = (DetalleResumenBean) iterator.next();
			if(element.getCodEdificio().equals(codigoEdificio)){
				return element;
			}
		}

		return null;
	}
	
}



