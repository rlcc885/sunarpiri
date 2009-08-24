package gob.pe.sunarp.extranet.administracion.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.util.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.administracion.bean.*;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailException;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class EditarUsuarioController
	extends ControllerExtension
	implements Constantes {

	public EditarUsuarioController() {
		super();
		addState(new State("verFormulario", "Ver Formulario Dependiendo del perfil user"));
		addState(new State("registraDatos", "Registra Datos de organizaciones"));
		setInitialState("verFormulario");
	}

	public String getTitle() {
		return new String("CrearUsuarioController");
	}

	protected ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			//obtener usuario de la sesión				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			long perfil = usuario.getPerfilId();

			//obtener informacion a pintar en formulario de ingreso de datos
			req.setAttribute("arrPais", Tarea.getComboPaises(dconn));
			req.setAttribute("tiposDocumento", Tarea.getComboTiposDocumento(dconn));

			//departamento y provincia
			FormBean formBean;
			if (perfil == PERFIL_ADMIN_JURISDICCION)
				formBean =Tarea.getDepartamento_ProvinciaPorJurisdiccion(dconn,usuario.getJurisdiccionId());
			else
				formBean = Tarea.getDepartamento_Provincia(dconn);

			req.setAttribute("arrDepartamento", formBean.getArray1());
			req.setAttribute("arrProvincia", formBean.getArray2());
			req.setAttribute("pregSecretas", Tarea.getComboPreguntasSecretas(dconn));

			//perfil AO
			if (perfil == PERFIL_ADMIN_ORG_EXT) {
				//Recupera el prefijo de la cuenta y el numero de usuarios la fecha en esa organizacion
				DboPeJuri userId = new DboPeJuri(dconn);
				userId.setField(DboPeJuri.CAMPO_PE_JURI_ID, usuario.getCodOrg());
				userId.find();
				String lengthNumber =
					String.valueOf(Integer.parseInt(userId.getField(DboPeJuri.CAMPO_NU_USRS) + 1));
				if (lengthNumber.length() < 3) 
					lengthNumber = "0" + lengthNumber;

				String newUserId = userId.getField(DboPeJuri.CAMPO_PREF_CTA) + lengthNumber;
				req.setAttribute("newUserId", newUserId);
			}
		
			//obtener lista de perfiles que puede asignar el perfil del usuario loggeado
			req.setAttribute("arrPerfiles",Tarea.getListaPerfilesAsignablesSegunPerfil(dconn, request, perfil));
							
			//obtener universo de permisos que puede asignar el perfil del usuario loggeado
			req.setAttribute("arrPermisos",Tarea.getListaPermisosAsignablesSegunPerfil(dconn, request, perfil));
			
			
			//de este punto hacia arriba, todo es igual a CrearOrganizacionController
			//de este punto hacia abajo, se buscan los datos originales del usuario
			//a  ser modificado

			/*
			sea param1 = Cuenta.usr_id del usuario a modificar
			*/
			
			DatosUsuarioBean datosUsuarioBean = Tarea.getDatosUsuario(dconn,req.getParameter("param1"));
			/**DESCAJ 03/01/2007 IFIGUEROA INICIO **/
			int perfilIdUsuario=Integer.parseInt(datosUsuarioBean.getPerfilId());
			if(perfilIdUsuario == PERFIL_INTERNO ||
			perfilIdUsuario == PERFIL_CAJERO ||
			perfilIdUsuario == PERFIL_TESORERO){
					req.setAttribute("nperfil","interno");	
					ComboBean bDias=Tarea.getDiasCaducidadClaveInt(dconn);
					req.setAttribute("nDiasCad",bDias.getAtributo1());			
			}
			if(perfilIdUsuario == PERFIL_INDIVIDUAL_EXTERNO ||
			perfilIdUsuario == PERFIL_AFILIADO_EXTERNO ||
			perfilIdUsuario == PERFIL_ADMIN_GENERAL ||
			perfilIdUsuario == PERFIL_ADMIN_ORG_EXT){ //Parchado por GOV
				req.setAttribute("nperfil","externo");
				req.setAttribute("arrCaducidad",Tarea.getComboCaducidadClave(dconn));
			} 
			
			/**DESCAJ 03/01/2007 IFIGUEROA FIN **/
			//-enviar informacion a pantalla
			req.setAttribute("perfilId",""+perfil);
			
			//7nov200, la respuesta secreta NO se debe ver
			datosUsuarioBean.setRespuestaSecreta("");			
			
			req.setAttribute("DATOS_FORMULARIO", datosUsuarioBean);
			req.setAttribute("modo","2");
			response.setStyle("verFormulario");
			
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
			try{
				pool.release(conn);
			}catch(Throwable tt){}
			end(request);
		}

		return response;
	}
	
	
	
	protected ControllerResponse runRegistraDatosState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		DatosUsuarioBean datosUsuarioBean = null;
		
		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			datosUsuarioBean = Tarea.recojeDatosRequestUsuario(req, usuario.getPerfilId());

			//-datos Hidden en web
			datosUsuarioBean.setUserId(req.getParameter("hid4"));
			
			Tarea.actualizaDatosUsuario(dconn, usuario, datosUsuarioBean, 0);

			StringBuffer sb = new StringBuffer();
			sb.append("Usuario [").append(datosUsuarioBean.getUserId());
			sb.append("] actualizado");
			req.setAttribute("mensaje1",sb.toString());
			req.setAttribute("destino","Mantenimiento.do");
			response.setStyle("ok");
			conn.commit();
			
		} 
		catch (ValidacionException e) 
		{
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("mensaje1",e.getMensaje());
			req.setAttribute("destino","back");			
		} 		
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (MailException me) {
			log(Errors.EC_CANNOT_SEND_MAIL, "", me, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			try{
				pool.release(conn);
			}catch(Throwable tt){}
			end(request);
		}

		return response;
	}
}