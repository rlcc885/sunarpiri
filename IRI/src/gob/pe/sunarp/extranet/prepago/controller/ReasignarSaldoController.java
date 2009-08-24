package gob.pe.sunarp.extranet.prepago.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.NonHandleableException;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import javax.servlet.http.HttpServletRequest;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class ReasignarSaldoController extends ControllerExtension {

	private String thisClass = ReasignarSaldoController.class.getName() + ".";

	public ReasignarSaldoController() {
		super();
		addState(new State("verFormulario", "Muestra la ventana principal"));
		addState(new State("decide", "Sirve de medio para direccionar el objetivo a realizar"));
		addState(new State("incrementaSaldo", "Realiza el incremento"));
		addState(new State("reduceSaldo", "Realiza la reduccion"));
		setInitialState("verFormulario");
	}

	public String getTitle() {
		return new String("ReasignarSaldoController");
	}

	protected ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			
			String linea_Usuario = req.getParameter("linea");
			String linea_Organizacion = user.getLineaPrePagoOrganizacion();
			
			LineaPrepago lineaCmd = new LineaPrepago();
			
			double saldo_Org = lineaCmd.getSaldoActual(linea_Organizacion, dconn);
			double saldo_Usr = lineaCmd.getSaldoActual(linea_Usuario, dconn);
			
			req.setAttribute("saldo_Org", String.valueOf(saldo_Org));
			req.setAttribute("saldo_Usr", String.valueOf(saldo_Usr));
			req.setAttribute("lineaUsuario", linea_Usuario);
			
			response.setStyle("verFormulario");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	protected ControllerResponse runDecideState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		try{
			init(request);
			validarSesion(request);
			
			int opcion = Integer.parseInt(request.getParameter("tipo"));
			switch(opcion){
				case 1: transition("incrementaSaldo", request, response); break;
				case 2: transition("reduceSaldo", request, response); break;
			}
			
		}catch(NonHandleableException nhe){
			log(Errors.EC_GENERIC_ERROR, "", nhe, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}


	protected ControllerResponse runIncrementaSaldoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			
			double monto = 0;
			
			if(req.getParameter("monto") == null || req.getParameter("monto").trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM);
			try{
				monto = Double.parseDouble(req.getParameter("monto"));
			}catch(NumberFormatException nfe){
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Debe ingresar valores numericos.");
			}
			
			String saldo_disponible = req.getParameter("saldo_disponible");
			String saldo_inicial = req.getParameter("saldo_inicial");
			String nuevo_saldo = req.getParameter("nuevo_saldo");
			
			String linea_Organizacion = user.getLineaPrePagoOrganizacion();
			String linea_Usuario = req.getParameter("lineaUsuario");
			
			PrepagoBean prepagoBean = new PrepagoBean();
			prepagoBean.setFlag_transferencia(true);
			prepagoBean.setLineaPrepagoId(linea_Usuario);
			prepagoBean.setMontoBruto(monto);
			
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.incrementaSaldo(user, prepagoBean, dconn);
			
			if (user.getLinPrePago().equalsIgnoreCase(linea_Usuario)) { //si entra aca quiere decir que el mismo administrador se esta reasignando saldo por lo cual deberia refrescar el frame superior 
				user.setSaldo(lineaCmd.getSaldoActual(linea_Usuario,dconn));
				req.setAttribute("cambioSaldoAdm","true");
			}
				
			prepagoBean = new PrepagoBean();
			prepagoBean.setFlag_transferencia(true);
			prepagoBean.setLineaPrepagoId(linea_Organizacion);
			prepagoBean.setMontoBruto(monto);
			prepagoBean.setTransacId(-1);					// -1 : Significa que en Consumo se pondra nulo (null).
			
			lineaCmd.reduceSaldo(user, prepagoBean, dconn);
			
			//leer nuevo saldo
			if (user.getFgInterno()==false)			
			{				
				double nuevoSaldo = lineaCmd.getSaldoActual(user.getLinPrePago(),dconn);
				user.setSaldo(nuevoSaldo);			
			}
						
			conn.commit();
			req.setAttribute("rptatransferencia", "Su transferencia se realizo con exito.");
			response.setStyle("transferenciaOK");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "No se pudo realizar la transaccion.", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "No se pudo realizar la transaccion.", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		return response;
	}

	protected ControllerResponse runReduceSaldoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			
			double monto = 0;
			
			if(req.getParameter("monto") == null || req.getParameter("monto").trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM);
			try{
				monto = Double.parseDouble(req.getParameter("monto"));
			}catch(NumberFormatException nfe){
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Debe ingresar valores numericos.");
			}
			
			String saldo_disponible = req.getParameter("saldo_disponible");
			String saldo_inicial = req.getParameter("saldo_inicial");
			String nuevo_saldo = req.getParameter("nuevo_saldo");

			String linea_Organizacion = user.getLineaPrePagoOrganizacion();
			String linea_Usuario = req.getParameter("lineaUsuario");
			
			PrepagoBean prepagoBean = new PrepagoBean();
			prepagoBean.setFlag_transferencia(true);
			prepagoBean.setLineaPrepagoId(linea_Usuario);
			prepagoBean.setMontoBruto(monto);
			prepagoBean.setTransacId(-1);					// -1 : Significa que en Consumo se pondra nulo (null).
			
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(user, prepagoBean, dconn);

			if (user.getLinPrePago().equalsIgnoreCase(linea_Usuario)) { //si entra aca quiere decir que el mismo administrador se esta reasignando saldo por lo cual deberia refrescar el frame superior 
				user.setSaldo(lineaCmd.getSaldoActual(linea_Usuario,dconn));
				req.setAttribute("cambioSaldoAdm","true");
			}
			
			prepagoBean = new PrepagoBean();
			prepagoBean.setFlag_transferencia(true);
			prepagoBean.setLineaPrepagoId(linea_Organizacion);
			prepagoBean.setMontoBruto(monto);
			
			lineaCmd.incrementaSaldo(user, prepagoBean, dconn);
			
			//leer nuevo saldo
			if (user.getFgInterno()==false)			
			{				
				double nuevoSaldo = lineaCmd.getSaldoActual(user.getLinPrePago(),dconn);
				user.setSaldo(nuevoSaldo);			
			}
			conn.commit();
			
			req.setAttribute("rptatransferencia", "Su transferencia se realizo con exito.");
			response.setStyle("transferenciaOK");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "No se pudo realizar la transaccion.", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "No se pudo realizar la transaccion.", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		return response;
	}
}

