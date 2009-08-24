package gob.pe.sunarp.extranet.acceso.controller;

import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.Loggy;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.Controller;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;

public class ReestablecerContrasenaController extends ControllerExtension {
	private String thisClass = ReestablecerContrasenaController.class.getName() + ".";
		
    public ReestablecerContrasenaController(){
		super();
		addState(new State("solicitaFormulario", "Muestra la ventana de Reestablecimiento de Password"));
		addState(new State("identificacionCuenta", "Ventana de Verificación de Cuenta"));
		addState(new State("reestablecePassword", "Ventana de Verificación de Usuario"));
		setInitialState("solicitaFormulario");
	}

	public String getTitle() {
		return new String("ReestablecerContrasenaController");
	}
    
    public ControllerResponse runSolicitaFormularioState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException {    

		try{
			init(request);
			response.setStyle("ventanaOlvidoPwd");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
    }


    public ControllerResponse runIdentificacionCuentaState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException {    

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
						
			String userId = request.getParameter("user");
			String pregSecId = null;
			
			
			DBConnection dconn = new DBConnection(conn);
			
			DboCuenta cuentaI = new DboCuenta(dconn);

			StringBuffer cadena = new StringBuffer();
			cadena.append(DboCuenta.CAMPO_CUENTA_ID).append("|").append(DboCuenta.CAMPO_PREG_SEC_ID).append("|").append(DboCuenta.CAMPO_RESP_SECRETA).append("|");
			cadena.append(DboCuenta.CAMPO_PE_NATU_ID);

			cuentaI.setFieldsToRetrieve(cadena.toString());
			cuentaI.setField(DboCuenta.CAMPO_USR_ID, userId);
			
			ArrayList listaCuenta = cuentaI.searchAndRetrieveList();
			
			if(listaCuenta.size() != 1)
				throw new ValidacionException("El usuario ingresado no se encuentra registrado.");

			DboCuenta cuenta = (DboCuenta) listaCuenta.get(0);

			String personaNatuId = cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID);
			pregSecId = cuenta.getField(DboCuenta.CAMPO_PREG_SEC_ID);
				
			// descripción de Pregunta Secreta
			DboTmPregSecretas pregSecI = new DboTmPregSecretas(dconn);
			pregSecI.setField(DboTmPregSecretas.CAMPO_PREG_SEC_ID, pregSecId);
			pregSecI.retrieve();
			String pregSecDescripcion = pregSecI.getField(DboTmPregSecretas.CAMPO_DESCRIPCION);
			
			//persona
			DboPeNatu peNatuI = new DboPeNatu(dconn);
			cadena.delete(0,cadena.length());
			cadena.append(DboPeNatu.CAMPO_APE_PAT).append("|").append(DboPeNatu.CAMPO_APE_MAT).append("|");
			cadena.append(DboPeNatu.CAMPO_NOMBRES).append("|").append(DboPeNatu.CAMPO_PERSONA_ID);
			peNatuI.setFieldsToRetrieve(cadena.toString());
			peNatuI.setField(DboPeNatu.CAMPO_PE_NATU_ID, personaNatuId);
			boolean b = peNatuI.find();
				
			String apem = request.getParameter("ApeMat");
			if(apem != null && apem.trim().length() > 0)
				if(!peNatuI.getField(DboPeNatu.CAMPO_APE_MAT).equalsIgnoreCase(apem))
					throw new CustomException(Constantes.NO_COINCIDEN_PWDS, "Los datos ingresados no coinciden, por favor intentar nuevamente.");
				
			if( !(peNatuI.getField(DboPeNatu.CAMPO_APE_PAT).equalsIgnoreCase(request.getParameter("ApePat")) &&
					  peNatuI.getField(DboPeNatu.CAMPO_NOMBRES).equalsIgnoreCase(request.getParameter("Nombres"))) )
					  	throw new CustomException(Constantes.NO_COINCIDEN_PWDS, "Los datos ingresados no coinciden, por favor intentar nuevamente.");

				String personaId = peNatuI.getField(DboPeNatu.CAMPO_PERSONA_ID);
					
					
				DboPersona personaI = new DboPersona(dconn);
				StringBuffer cadena2 = new StringBuffer();
				cadena2.append(DboPersona.CAMPO_EMAIL).append("|").append(DboPersona.CAMPO_NUM_DOC_IDEN);
				personaI.setField(DboPersona.CAMPO_PERSONA_ID, personaId);
				personaI.retrieve();
				
				if(!(personaI.getField(DboPersona.CAMPO_EMAIL).equals(request.getParameter("email")) &&
						 personaI.getField(DboPersona.CAMPO_NUM_DOC_IDEN).equals(request.getParameter("DNI"))) )
						throw new CustomException(Constantes.NO_COINCIDEN_PWDS, "Los datos ingresados no coinciden, por favor intentar nuevamente.");

				response.setStyle("identificaCuenta");
				
				req.setAttribute("pregSecreta", pregSecDescripcion);
				req.setAttribute("userId", userId);

		} 
		catch (ValidacionException ve) 
		{
			principal(request);
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",ve.getMensaje());
			response.setStyle("pantallaFinal");
		}		
		catch (CustomException ce) 
		{
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("errorLogon", ce.getMessage());
			response.setStyle("errorLogon");
		}
		 catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, dbe.getMessage(), request);
			principal(request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
			end(request);
			try
			{
				pool.release(conn);
			}
			catch (Throwable tee)
			{
			}
		}
		return response;
    }

	public ControllerResponse runReestablecePasswordState(
					ControllerRequest request,
					ControllerResponse response)
					throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		try {
			String nuevoPwd    = request.getParameter("npass");
			String confirmaPwd = request.getParameter("cpass");
			String userId      = request.getParameter("userId");

			if (!nuevoPwd.equals(confirmaPwd))
				throw new CustomException(Constantes.NO_COINCIDEN_PWDS, "No coinciden passwords.");
						
			String a = request.getParameter("respuesta");

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
DBConnection dconn = new DBConnection(conn);			

			DboCuenta cuentaUserI = new DboCuenta(dconn);
			cuentaUserI.setField(DboCuenta.CAMPO_USR_ID, userId);

			if (!cuentaUserI.find())
				throw new ValidacionException("El usuario ingresado no se encuentra registrado.");
			
			String respuestaSecreta = cuentaUserI.getField(DboCuenta.CAMPO_RESP_SECRETA);
			
			if (!a.equalsIgnoreCase(respuestaSecreta))
				throw new CustomException(Constantes.NO_COINCIDEN_PWDS, "La respuesta secreta no coincide.");
			
			//cambiar password en TAM
			SecAdmin secAdmin = SecAdmin.getInstance();
			secAdmin.cambiaPasswordUsuario(userId, nuevoPwd);
			
			cuentaUserI.setFieldsToUpdate(DboCuenta.CAMPO_CLAVE + "|" + DboCuenta.CAMPO_TS_ULT_ACC);

			if (Propiedades.getInstance().getFlagGrabaClave()==false)
				cuentaUserI.setField("CLAVE", "*/*");
			else
				cuentaUserI.setField("CLAVE", nuevoPwd);
						
			cuentaUserI.setField("TS_ULT_ACC",FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
			cuentaUserI.update();
			conn.commit();

			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "Su contraseña fue correctamente cambiada");
			response.setStyle("cambio");
		} 
		catch (ValidacionException ve) 
		{
			principal(request);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino","back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1",ve.getMensaje());
			response.setStyle("pantallaFinal");
		}				
		catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("errorLogon", ce.getMessage());
			response.setStyle("errorLogon");
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
			if (pool != null)
				pool.release(conn);
			end(request);
		}
		return response;
	}
}
