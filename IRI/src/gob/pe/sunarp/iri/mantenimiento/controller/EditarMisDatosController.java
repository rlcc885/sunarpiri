package gob.pe.sunarp.iri.mantenimiento.controller;

/*
Programa invocado por el link "Editar mis Datos"

Este programa es semejante a:
	EditarOrganizacionController y
	EditarUsuarioController
*/
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.administracion.bean.DatosOrganizacionBean;
import gob.pe.sunarp.extranet.administracion.bean.DatosUsuarioBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.*;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.pool.*;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class EditarMisDatosController extends ControllerExtension implements Constantes {
	
	public EditarMisDatosController() {
		super();
		addState(new State("verFormulario", "ver formulario para cambiar datos"));
		addState(new State("actualizaDatosOrganizacion", "Actualizar Datos"));
		addState(new State("actualizaDatosPersona", "Actualizar Datos"));
		setInitialState("verFormulario");	
	}

	public String getTitle() {
		return new String("EditarMisDatosController");
	}
	
	protected ControllerResponse runVerFormularioState(ControllerRequest request, ControllerResponse response) throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			long perfilId = usuario.getPerfilId();
			req.setAttribute("perfilId",String.valueOf(perfilId));
			
			//Recojer informacion de la base de datos, según perfil
			// y de la persona u Organizacion
			boolean persona=false;
			
			if (perfilId == PERFIL_ADMIN_GENERAL ||
				perfilId == PERFIL_ADMIN_JURISDICCION ||
				perfilId == PERFIL_ADMIN_ORG_EXT)
				{
					DatosOrganizacionBean bean = Tarea.getDatosOrganizacion(dconn, usuario.getCodOrg());
					
					//7nov2002
					//respuesta secreta no debe verse
					bean.setRespuestaSecreta("");
					req.setAttribute("DATOS_FORMULARIO", bean);
					response.setStyle("verFormularioOrganizacion");
				}
			/*
			if (perfilId == PERFIL_INDIVIDUAL_EXTERNO ||
				perfilId == PERFIL_AFILIADO_EXTERNO)
				{
					DatosUsuarioBean bean = Tarea.getDatosUsuario(dconn, usuario.getUserId());
					//7nov2002
					//respuesta secreta no debe verse
					bean.setRespuestaSecreta("");
					req.setAttribute("DATOS_FORMULARIO", bean);
					response.setStyle("verFormularioPersona");
					persona=true;
				}
			*/
			if (perfilId == PERFIL_INDIVIDUAL_EXTERNO ||
				perfilId == PERFIL_AFILIADO_EXTERNO ||
				perfilId == PERFIL_INTERNO ||
				perfilId == PERFIL_CAJERO ||
				perfilId == PERFIL_TESORERO)
				{
					DatosUsuarioBean bean = Tarea.getDatosUsuario(dconn, usuario.getUserId());
					//7nov2002
					//respuesta secreta no debe verse
					bean.setRespuestaSecreta("");
					req.setAttribute("DATOS_FORMULARIO", bean);
					response.setStyle("verFormularioPersona");
					/**DESCAJ 03/01/2007 IFIGUEROA INICIO **/
					if( perfilId == PERFIL_INTERNO ||
						perfilId == PERFIL_CAJERO ||
						perfilId == PERFIL_TESORERO){
							req.setAttribute("nperfil","interno");	
							ComboBean bDias=Tarea.getDiasCaducidadClaveInt(dconn);
							req.setAttribute("nDiasCad",bDias.getAtributo1());			
					}
					if( perfilId == PERFIL_INDIVIDUAL_EXTERNO ||
						perfilId == PERFIL_AFILIADO_EXTERNO ||
						perfilId == PERFIL_ADMIN_GENERAL){
						req.setAttribute("nperfil","externo");
						req.setAttribute("arrCaducidad",Tarea.getComboCaducidadClave(dconn));
					} 
					
					/**DESCAJ 03/01/2007 IFIGUEROA FIN **/
					persona=true;
				}
				

			//Recojer datos comunes
			req.setAttribute("arrPaises", Tarea.getComboPaises(dconn));
			req.setAttribute("arrTiposDocumento", Tarea.getComboTiposDocumento(dconn));
			req.setAttribute("arrJurisdicciones", Tarea.getComboJurisdicciones(dconn));
			req.setAttribute("arrPreguntas", Tarea.getComboPreguntasSecretas(dconn));
			
			//departamento y provincia
			FormBean formBean;
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
				formBean =Tarea.getDepartamento_ProvinciaPorJurisdiccion(dconn,usuario.getJurisdiccionId());
			else
				formBean = Tarea.getDepartamento_Provincia(dconn);

			req.setAttribute("arrDepartamentos", formBean.getArray1());
			req.setAttribute("arrProvincias", formBean.getArray2());
			
			//datos a mostrar solo si es organizacion
			if (persona==false)
				req.setAttribute("arrGiros", Tarea.getComboGiros(dconn));
			
			
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
	
	
	
	
	
		protected ControllerResponse runActualizaDatosOrganizacionState(ControllerRequest request, ControllerResponse response) throws ControllerException 
		{

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

				HttpSession session = ExpressoHttpSessionBean.getSession(request);
				UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");

				//capturar parametros de ingreso
				datosOrganizacionBean = Tarea.recojeDatosRequestOrganizacion(req, userLogged.getPerfilId());
				datosOrganizacionBean.setOrganizacionPeJuriId(req.getParameter("hid1"));
	
				//verificar que la clave y verificacion de contrasena
				if (datosOrganizacionBean.getClave() != null && datosOrganizacionBean.getClave().trim().length()>0) 
					{
						if (!datosOrganizacionBean.getClave().trim().equals(datosOrganizacionBean.getConfirmacionClave().trim())) 
									throw new ValidacionException("La contrasena no esta confirmada","clave");
					}
		
				//validaciones
				String contrasenaActual = datosOrganizacionBean.getContrasena1();
				String contrasenaNueva = datosOrganizacionBean.getContrasena2();
				String contrasenaConfirmada = datosOrganizacionBean.getContrasena3();
				
				if (contrasenaActual!=null)
				{
					if (contrasenaNueva.equals(contrasenaConfirmada)==false)
						throw new ValidacionException("Nueva contrasena no confirmada", "contrasena2");
				}
				//actualizar
				Tarea.actualizaDatosOrganizacion(dconn, userLogged, datosOrganizacionBean, 1);
				
				conn.commit();

			String mensaje = "Organizacion "+datosOrganizacionBean.getRazonSocial()+" ha sido actualizada.<br><br>";
			req.setAttribute("mensaje1",mensaje);
			//si no se pone "destino" no se muestra boton para "regresar"
			//req.setAttribute("destino","Mantenimiento.do");
			response.setStyle("ok");	

			} //try
			
			catch (ValidacionException e) {
				//esta excepcion la usare para redireccion a la 
				//pagina de creacion con el mensaje respectivo
				rollback(conn, request);
				req.setAttribute("VALIDACION_EXCEPTION",e);
				req.setAttribute("DATOS_ORGANIZACION_BEAN", datosOrganizacionBean);
				try {
					this.transition("verFormulario", request, response);
				} catch (Throwable ex) {
					log(Errors.EC_GENERIC_ERROR, "", ex, request);
					rollback(conn, request);
					response.setStyle("error");
				} finally {
						pool.release(conn);
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
				pool.release(conn);
				end(request);
			}

			return response;
		}
		
	protected ControllerResponse runActualizaDatosPersonaState(
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
			
			//validaciones
			String contrasenaActual = datosUsuarioBean.getContrasena1();
			String contrasenaNueva = datosUsuarioBean.getContrasena2();
			String contrasenaConfirmada = datosUsuarioBean.getContrasena3();
			
			if (contrasenaActual!=null)
			{
				if (contrasenaNueva.equals(contrasenaConfirmada)==false)
					throw new ValidacionException("Nueva contrasena no confirmada", "contrasena2");
			}
			
			Tarea.actualizaDatosUsuario(dconn, usuario, datosUsuarioBean, 1);

			StringBuffer sb = new StringBuffer();
			//sb.append("<br><br><br><br><br><br>");
			sb.append("Usuario [").append(datosUsuarioBean.getUserId());
			sb.append("] actualizado");
			//sb.append("<br><br><br><br><br><br>");
			req.setAttribute("mensaje1",sb.toString());
			//si no se pone "destino" no se muestra boton para "regresar"
			//req.setAttribute("destino","Mantenimiento.do");
			response.setStyle("ok");
			conn.commit();
			
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
				pool.release(conn);
			end(request);
		}

		return response;
	}		

}