package gob.pe.sunarp.extranet.administracion.controller;

import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.administracion.bean.DatosOrganizacionBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.util.*;
import java.io.IOException;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class EditarOrganizacionController extends ControllerExtension implements Constantes {
	
	public EditarOrganizacionController() 
	{
		super();
		addState(new State("verFormulario", "Ver Formulario General para Organizaciones)"));
		addState(new State("registraDatos", "Actualiza Datos de organizaciones"));
		setInitialState("verFormulario");	
	}

	public String getTitle() {
		return new String("EditarOrganizacionController");
	}
	
	protected ControllerResponse runVerFormularioState(ControllerRequest request, ControllerResponse response) throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);			

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean datosUsuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			req.setAttribute("perfilId",""+datosUsuario.getPerfilId());

			DatosOrganizacionBean d2 = (DatosOrganizacionBean) req.getAttribute("DATOS_ORGANIZACION_BEAN");
			
			//Buscar datos de la organización a ser editada
			DatosOrganizacionBean datosOrganizacionBean=null;
			if (d2==null)
				datosOrganizacionBean = Tarea.getDatosOrganizacion(dconn, req.getParameter("codOrg"));
			else
				datosOrganizacionBean = Tarea.getDatosOrganizacion(dconn, d2.getOrganizacionPeJuriId());

			req.setAttribute("arrPaises", Tarea.getComboPaises(dconn));
			req.setAttribute("arrTiposDocumento", Tarea.getComboTiposDocumento(dconn));
			req.setAttribute("arrJurisdicciones", Tarea.getComboJurisdicciones(dconn));
			req.setAttribute("arrGiros", Tarea.getComboGiros(dconn));
			req.setAttribute("arrPreguntas", Tarea.getComboPreguntasSecretas(dconn));
			
			//departamento y provincia
			FormBean formBean;
			if (datosUsuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
				formBean =Tarea.getDepartamento_ProvinciaPorJurisdiccion(dconn,datosUsuario.getJurisdiccionId());
			else
				formBean = Tarea.getDepartamento_Provincia(dconn);

			req.setAttribute("arrDepartamentos", formBean.getArray1());
			req.setAttribute("arrProvincias", formBean.getArray2());

			req.setAttribute("modo","2");
			
			
			if (d2==null)
			{
				//7nov200, la respuesta secreta NO se debe ver
				datosOrganizacionBean.setRespuestaSecreta("");
				req.setAttribute("DATOS_ORGANIZACION_BEAN",datosOrganizacionBean);
			}
			else
			{
				d2.setRuc(datosOrganizacionBean.getRuc());
				d2.setPrefijoCuenta(datosOrganizacionBean.getPrefijoCuenta());
				d2.setJurisdiccionId(datosOrganizacionBean.getJurisdiccionId());
				req.setAttribute("DATOS_ORGANIZACION_BEAN",d2);
			}
				
			response.setStyle("formOrganizacion");
			
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
		protected ControllerResponse runRegistraDatosState(ControllerRequest request, ControllerResponse response) throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
			
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);			
			DatosOrganizacionBean datosOrganizacionBean = new DatosOrganizacionBean();				
			
			try {
				init(request);
				validarSesion(request);

				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);

				UsuarioBean userLogged = ExpressoHttpSessionBean.getUsuarioBean(request);

				//capturar parametros de ingreso
				datosOrganizacionBean = Tarea.recojeDatosRequestOrganizacion(req, userLogged.getPerfilId());
				datosOrganizacionBean.setOrganizacionPeJuriId(req.getParameter("hid1"));
	
				//verificar que la clave y verificacion de contrasena
				if (datosOrganizacionBean.getClave() != null && datosOrganizacionBean.getClave().trim().length()>0) 
					{
						if (!datosOrganizacionBean.getClave().trim().equals(datosOrganizacionBean.getConfirmacionClave().trim())) 
									throw new ValidacionException("La contrasena no está confirmada","clave");
					}
		
				//actualizar
				Tarea.actualizaDatosOrganizacion(dconn, userLogged, datosOrganizacionBean, 0);
				
				conn.commit();

			String mensaje = "Organización "+datosOrganizacionBean.getRazonSocial()+" ha sido actualizada.";
			req.setAttribute("destino","MantenimientoOrg.do");
			req.setAttribute("mensaje1",mensaje);
			response.setStyle("ok");	

			} //try
			
			catch (ValidacionException e) {
				//esta excepcion la usare para redireccion a la 
				//pagina de creacion con el mensaje respectivo
				rollback(conn, request);
				req.setAttribute("VALIDACION_EXCEPTION",e);
				req.setAttribute("DATOS_ORGANIZACION_BEAN", datosOrganizacionBean);
				//request.setObjectParameter("obj1",e);
				//request.setObjectParameter("obj2",datosOrganizacionBean);
				try {
					this.transition("verFormulario", request, response);
				} catch (Throwable ex) {
					log(Errors.EC_GENERIC_ERROR, "", ex, request);
					rollback(conn, request);
					response.setStyle("error");
				} finally {
					try{
						pool.release(conn);
					}catch(Throwable tt){}
					end(request);
				}
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