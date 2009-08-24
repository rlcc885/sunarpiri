/*
 * Created on 19/01/2007
 *
 */
package gob.pe.sunarp.extranet.caja.controller;

import gob.pe.sunarp.extranet.caja.bean.DetalleBilleteBean;
import gob.pe.sunarp.extranet.caja.bean.DetalleMonedaBean;
import gob.pe.sunarp.extranet.common.logica.Errors;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboTaBill;
import gob.pe.sunarp.extranet.dbobj.DboTaCaja;
import gob.pe.sunarp.extranet.dbobj.DboTaDiarCaja;
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

/**
 * @author lsuarez
 *
 */
public class BalanceCajaCajeroController extends ControllerExtension {

	private String thisClass = BalanceCajaCajeroController.class.getName() + ".";

	public BalanceCajaCajeroController() {
		super();
		addState(new State("muestra", "Muestra la ventana con el Filtro de Busqueda"));
		addState(new State("refresh", "Refresca el filtro de busqueda Filtro de Busqueda"));
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
		
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());
			
			String hoy = FechaUtil.getCurrentDate();
			String hoyDia = hoy.substring(0,2);
			String hoyMes = hoy.substring(3,5);
			String hoyAnio = hoy.substring(6,10);
			
			req.setAttribute("selectedDay",hoyDia);
			req.setAttribute("selectedMonth",hoyMes);
			req.setAttribute("selectedYear",hoyAnio);
			
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

			//recuperamos la fecha (REVISAR)
			String dia = request.getParameter("dia");
			String mes = request.getParameter("mes");
			String anio = request.getParameter("anio");
			String fechaInicio = FechaUtil.stringTimeToOracleString(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(anio), 0, 0, 0);
			String fechaFin = FechaUtil.stringTimeToOracleString(Integer.parseInt(dia), Integer.parseInt(mes),Integer.parseInt(anio), 23, 59, 59);
			
			//obtenemos estos 2 parametros
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			String codigoEdificio = obtenerCodigoEdificio(dconn, usuario.getCuentaId());
			String codigoCaja = obtenerCodigoCaja(dconn, usuario.getCuentaId());

			List listaEdificios = Tarea.getComboEdificios(dconn, usuario.getRegPublicoId());
			String descEdificio = obtenerDescEdificio(codigoEdificio, listaEdificios);
			List listaCajas = Tarea.getComboCajasPorEdificio(dconn, usuario.getRegPublicoId(), codigoEdificio);
			String userIdCajero = obtenerUserIdCajero(dconn, codigoCaja, listaCajas);	
			
			/** MODIFICADO JBUGARIN OBSERVACIONES CAJEROS 07/03/2007 **/
			//consultando en pe_natu_id de la tabla cuenta para traer el nombre y apellido del cajero
			String peNatuId = "";
			DboCuenta dboCuenta = new DboCuenta(dconn);
			dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_PE_NATU_ID);
			dboCuenta.setField(DboCuenta.CAMPO_USR_ID,userIdCajero);
			if (dboCuenta.find()==true){
			   peNatuId = dboCuenta.getField(DboCuenta.CAMPO_PE_NATU_ID);
			}
			//consultando la tabal PE_NATU para traer el nombre y apellido del cajero
			String nombre = "";
			String apPaterno = "";
			String apMaterno = "";
			DboPeNatu dboPeNatu = new DboPeNatu(dconn);
			dboPeNatu.setFieldsToRetrieve(DboPeNatu.CAMPO_NOMBRES + "|"+ DboPeNatu.CAMPO_APE_PAT + "|" + DboPeNatu.CAMPO_APE_MAT);
			dboPeNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,peNatuId);
			if (dboPeNatu.find()==true){
				nombre = dboPeNatu.getField(DboPeNatu.CAMPO_NOMBRES);
				apPaterno = dboPeNatu.getField(DboPeNatu.CAMPO_APE_PAT);
				apMaterno = dboPeNatu.getField(DboPeNatu.CAMPO_APE_MAT);
				
			}
			/** FIN MODIFICACIONES 07/03/2007 **/
			
			//validacion - la caja no esta cerrada
			if(userIdCajero.equals("")){
				req.setAttribute("mensajeError", "La caja asociada no esta cerrada");
				return runMuestraState(request, response);
			}
			
			//Obtener fecha de apertura y fecha de cierre
			String fechaAperturaCaja = "";
			String fechaCierreCaja = "";
			String codigoCajaDiaria = "";
			DboTaDiarCaja dboTaDiarCaja = new DboTaDiarCaja(dconn);
			dboTaDiarCaja.setFieldsToRetrieve(DboTaDiarCaja.CAMPO_FH_APER_CAJE +"|"+ DboTaDiarCaja.CAMPO_FH_CIER_CAJE +"|"+ DboTaDiarCaja.CAMPO_ID_DIAR);
			dboTaDiarCaja.setField(DboTaDiarCaja.CAMPO_CO_CAJA, codigoCaja);
			dboTaDiarCaja.setAppendWhereClause(DboTaDiarCaja.CAMPO_FH_CIER_CAJE + " BETWEEN " + fechaInicio + " AND " + fechaFin  + " ORDER BY " + DboTaDiarCaja.CAMPO_ID_DIAR + " DESC");
			List listaAux = dboTaDiarCaja.searchAndRetrieveList();
			if(listaAux.size() > 0){
				DboTaDiarCaja dboTaDiarCajaAux = (DboTaDiarCaja)listaAux.get(0);
				fechaAperturaCaja = dboTaDiarCajaAux.getField(DboTaDiarCaja.CAMPO_FH_APER_CAJE);
				fechaCierreCaja = dboTaDiarCajaAux.getField(DboTaDiarCaja.CAMPO_FH_CIER_CAJE);
				codigoCajaDiaria = dboTaDiarCajaAux.getField(DboTaDiarCaja.CAMPO_ID_DIAR);
				System.out.println("codigo caja Diaria:==>"  + codigoCajaDiaria);
			}

					
			req.setAttribute("descEdificio", descEdificio);
			req.setAttribute("userIdCajero", userIdCajero);
			req.setAttribute("fechaAperturaCaja", fechaAperturaCaja);
			req.setAttribute("fechaCierreCaja", fechaCierreCaja);

			//obtenemos el billetaje
			DboTaBill dboTaBill = new DboTaBill(dconn);
			dboTaBill.setFieldsToRetrieve(DboTaBill.CAMPO_CO_BIMO +"|"+ 
											DboTaBill.CAMPO_CANT +"|"+ 
											DboTaBill.CAMPO_MO_TOTA);
			dboTaBill.setField(DboTaBill.CAMPO_ID_DIAR, codigoCajaDiaria);
			List listaBilletaje = dboTaBill.searchAndRetrieveList(DboTaBill.CAMPO_CO_BIMO);
			List listaBilletes = obtenerListaBilletes(listaBilletaje);
			List listaMonedas = obtenerListaMonedas(listaBilletaje);
			req.setAttribute("listaBilletes", listaBilletes);
			req.setAttribute("listaMonedas", listaMonedas);
			req.setAttribute("montoTotalBilletaje", obtenerMontoTotalBilletaje(listaMonedas, listaBilletes));
			

			//obtenemos el total de abonos
			String totalEfectivo = obtenerTotalIngresoCajaEfectivo(userIdCajero, fechaInicio, fechaFin, dconn);
			String totalCheque = obtenerTotalIngresoCajaCheque(userIdCajero, fechaInicio, fechaFin, dconn);
			BigDecimal totalAbonos = new BigDecimal(totalEfectivo).add(new BigDecimal(totalCheque));
			req.setAttribute("totalEfectivo", totalEfectivo);
			req.setAttribute("totalCheque", totalCheque);
			req.setAttribute("totalAbonos", new DecimalFormat("0.00").format(totalAbonos.doubleValue()));

			String fechaActual = FechaUtil.getCurrentDate();
			String horaActual = FechaUtil.getCurrentDateTime().substring(11,19);
			req.setAttribute("fechaActual", fechaActual);
			req.setAttribute("horaActual", horaActual);
			
			/** MODIFICADO JBUGARIN OBSERVACIONES CAJAS 07/03/2007 **/
			//enviando el nombre y apellidos del cajero
			req.setAttribute("nombres",nombre);
			req.setAttribute("apPaterno",apPaterno);
			req.setAttribute("apMaterno",apMaterno);
			/** FIN MODIFICACIONES**/
			
			//devolvemos el filtro de busqueda
			response.setStyle("balanceCaja");
			
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
	 * @param dconn
	 * @param string
	 * @return
	 */
	private String obtenerCodigoCaja(DBConnection dconn, String userId) throws DBException {
		String codigoCaja = "";
		DboTaCaja dboCaja = new DboTaCaja (dconn);
		dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_CAJA);
		dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL, userId);
		if(dboCaja.find()==true){
			codigoCaja = dboCaja.getField(DboTaCaja.CAMPO_CO_CAJA);
		}
		return codigoCaja;
	}


	/**
	 * @param dconn
	 * @param string
	 * @return
	 */
	private String obtenerCodigoEdificio(DBConnection dconn, String userId) throws DBException {
		String codigoEdificio = "";
		DboTaCaja dboCaja = new DboTaCaja (dconn);
		dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_ZONA);
		dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL, userId);
		if(dboCaja.find()==true){
			codigoEdificio = dboCaja.getField(DboTaCaja.CAMPO_CO_ZONA);
		}
		return codigoEdificio;
	}


	/**
	 * @param listaMonedas
	 * @param listaBilletes
	 * @return
	 */
	private String obtenerMontoTotalBilletaje(List listaMonedas, List listaBilletes) {
		
		BigDecimal montoTotal = new BigDecimal(0);
		Iterator iterMoneda = listaMonedas.iterator();
		while (iterMoneda.hasNext()) {
			DetalleMonedaBean element = (DetalleMonedaBean) iterMoneda.next();
			montoTotal = montoTotal.add(element.getMonto());
		}
		Iterator iterBillete = listaBilletes.iterator();
		while (iterBillete.hasNext()) {
			DetalleBilleteBean element = (DetalleBilleteBean) iterBillete.next();
			montoTotal = montoTotal.add(element.getMonto());
		}

		return new DecimalFormat("0.00").format(montoTotal.doubleValue());
	}


	/**
	 * @param listaBilletaje
	 * @return
	 */
	private List obtenerListaMonedas(List listaBilletaje) throws DBException {
		
		List listaMoneda = new ArrayList();

		DetalleMonedaBean detalleMonedaBean = null;
		Iterator iterator = listaBilletaje.iterator();
		while (iterator.hasNext()) {
			detalleMonedaBean = new DetalleMonedaBean();
			DboTaBill element = (DboTaBill) iterator.next();
			detalleMonedaBean.setCodigo(element.getField(DboTaBill.CAMPO_CO_BIMO));
			detalleMonedaBean.setUnidades(new Long(element.getField(DboTaBill.CAMPO_CANT)));
			detalleMonedaBean.setMonto(new BigDecimal(element.getField(DboTaBill.CAMPO_MO_TOTA)));
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleMonedaBean.COD_MONEDA_CINCO_SOLES)) { 
				detalleMonedaBean.setDescripcion(DetalleMonedaBean.DESC_MONEDA_CINCO_SOLES);
				listaMoneda.add(detalleMonedaBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleMonedaBean.COD_MONEDA_DOS_SOLES)) {
				detalleMonedaBean.setDescripcion(DetalleMonedaBean.DESC_MONEDA_DOS_SOLES);
				listaMoneda.add(detalleMonedaBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleMonedaBean.COD_MONEDA_UN_SOL)) {
				detalleMonedaBean.setDescripcion(DetalleMonedaBean.DESC_MONEDA_UN_SOL);
				listaMoneda.add(detalleMonedaBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleMonedaBean.COD_MONEDA_CINCUENTA_CENTIMOS)) { 
				detalleMonedaBean.setDescripcion(DetalleMonedaBean.DESC_MONEDA_CINCUENTA_CENTIMOS);
				listaMoneda.add(detalleMonedaBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleMonedaBean.COD_MONEDA_VEINTE_CENTIMOS)) { 
				detalleMonedaBean.setDescripcion(DetalleMonedaBean.DESC_MONEDA_VEINTE_CENTIMOS);
				listaMoneda.add(detalleMonedaBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleMonedaBean.COD_MONEDA_DIEZ_CENTIMOS)) { 
				detalleMonedaBean.setDescripcion(DetalleMonedaBean.DESC_MONEDA_DIEZ_CENTIMOS);
				listaMoneda.add(detalleMonedaBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleMonedaBean.COD_MONEDA_CINCO_CENTIMOS)){
				detalleMonedaBean.setDescripcion(DetalleMonedaBean.DESC_MONEDA_CINCO_CENTIMOS);
				listaMoneda.add(detalleMonedaBean);
			}
		}
		return listaMoneda;
	}


	/**
	 * @param listaBilletaje
	 * @return
	 */
	private List obtenerListaBilletes(List listaBilletaje) throws DBException {

		List listaBillete = new ArrayList();

		DetalleBilleteBean detalleBilleteBean = null;
		Iterator iterator = listaBilletaje.iterator();
		while (iterator.hasNext()) {
			detalleBilleteBean = new DetalleBilleteBean();
			DboTaBill element = (DboTaBill) iterator.next();
			detalleBilleteBean.setCodigo(element.getField(DboTaBill.CAMPO_CO_BIMO));
			detalleBilleteBean.setUnidades(new Long(element.getField(DboTaBill.CAMPO_CANT)));
			detalleBilleteBean.setMonto(new BigDecimal(element.getField(DboTaBill.CAMPO_MO_TOTA)));
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleBilleteBean.COD_BILLETE_DOSCIENTOS_SOLES)) { 
				detalleBilleteBean.setDescripcion(DetalleBilleteBean.DESC_BILLETE_DOSCIENTOS_SOLES);
				listaBillete.add(detalleBilleteBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleBilleteBean.COD_BILLETE_CIEN_SOLES)) {
				detalleBilleteBean.setDescripcion(DetalleBilleteBean.DESC_BILLETE_CIEN_SOLES);
				listaBillete.add(detalleBilleteBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleBilleteBean.COD_BILLETE_CINCUENTA_SOLES)) { 
				detalleBilleteBean.setDescripcion(DetalleBilleteBean.DESC_BILLETE_CINCUENTA_SOLES);
				listaBillete.add(detalleBilleteBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleBilleteBean.COD_BILLETE_VEINTE_SOLES)) { 
				detalleBilleteBean.setDescripcion(DetalleBilleteBean.DESC_BILLETE_VEINTE_SOLES);
				listaBillete.add(detalleBilleteBean);
			}
			if(element.getField(DboTaBill.CAMPO_CO_BIMO).equals(DetalleBilleteBean.COD_BILLETE_DIEZ_SOLES)) { 
				detalleBilleteBean.setDescripcion(DetalleBilleteBean.DESC_BILLETE_DIEZ_SOLES);
				listaBillete.add(detalleBilleteBean);
			}
		}
		return listaBillete;
	}


	/**
	 * @param dconn
	 * @param codigoCajaDiaria
	 * @param listaCajas
	 * @return
	 */
	private String obtenerUserIdCajero(DBConnection dconn, String codigoCajaDiaria, List listaCajas) {
		String descCaja = "";
		Iterator iterator = listaCajas.iterator();
		while (iterator.hasNext()) {
			ComboBean element = (ComboBean) iterator.next();
			if(element.getCodigo().equals(codigoCajaDiaria)){
				return
				 element.getDescripcion();
			}
		}

		return descCaja;
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


	private String obtenerUserId(DBConnection dconn, String cuentaId) throws DBException {
		
		String userId = "";
		DboCuenta dboCuenta = new DboCuenta(dconn);
		dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
		dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID, cuentaId);
		if(dboCuenta.find()==true){
			userId = dboCuenta.getField(DboCuenta.CAMPO_USR_ID);
		}

		return userId;
	}

	/**
	 * @param string
	 * @param dconn
	 */
	private String obtenerTotalIngresoCajaEfectivo(String userId, String fechaInicio, String fechaFin, DBConnection dconn) throws DBException {
		
		String estadoNoExtornado = "1";
		DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
		vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, userId);
		vista.setField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT, "E");
		/** modificado jbugarin para q no considere extoronos en el reporte**/
		vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fechaInicio + " AND " + fechaFin + " AND " + DboVwDiariorecauda.CAMPO_ESTADO + "=" + estadoNoExtornado);
		/** fin jbugarin modificado**/
		List listaAbonos = vista.searchAndRetrieveList();
		Iterator iterator = listaAbonos.iterator();
		BigDecimal totalIngreso = new BigDecimal(0);
		while (iterator.hasNext()) {
			DboVwDiariorecauda element = (DboVwDiariorecauda) iterator.next();
			String montoAux = element.getField(DboVwDiariorecauda.CAMPO_MONTO);
			BigDecimal monto = new BigDecimal(montoAux);
			totalIngreso = totalIngreso.add(monto);				
		}
		return new DecimalFormat("0.00").format(totalIngreso.doubleValue());
	}

	/**
	 * @param string
	 * @param dconn
	 */
	private String obtenerTotalIngresoCajaCheque(String userId, String fechaInicio, String fechaFin, DBConnection dconn) throws DBException {
		String estadoNoExtornado = "1";
		DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
		vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, userId);
		vista.setField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT, "C");
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
		return new DecimalFormat("0.00").format(totalIngreso.doubleValue());
	}


}
