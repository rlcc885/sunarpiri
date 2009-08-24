package gob.pe.sunarp.extranet.acceso.controller;


//paquetes del sistema
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import com.jcorporate.expresso.core.controller.*;

import com.jcorporate.expresso.core.db.*;

import java.sql.*;

//paquetes del proyecto
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;

public class ContactoController
	extends ControllerExtension
	implements Constantes {
	private String thisClass = ContactoController.class.getName() + ".";

	public ContactoController() {
		super();
		addState(new State("enviaSugerencia", "enviasugerencia"));
		setInitialState("enviaSugerencia");
	}

	public ControllerResponse runEnviaSugerenciaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);

			//recojer datos
			String nombres = request.getParameter("txtNombre");
			String apellidos = request.getParameter("txtApellido");
			String dni = request.getParameter("txtDNI");
			String email = request.getParameter("txtEmail");
			
			/*
			Tipos de comentario que puede ingresar el usuario:
            	1 Consulta
                2 Queja
            	2 Observaci&oacute;n
			*/
			int nTipo = Integer.parseInt(request.getParameter("txtTipo"));
			String tipo  = "";
			String destinatarios="";
			
			Propiedades propiedades = Propiedades.getInstance();
			
			switch (nTipo)
				{
					case 1:
						destinatarios = propiedades.getContactenosEmailsConsultas();
						tipo = "Consulta";
						break;
					case 2:
						destinatarios = propiedades.getContactenosEmailsQuejas();
						tipo = "Queja";
						break;
					case 3:
						destinatarios = propiedades.getContactenosEmailsObservs();
						tipo = "Observación";
						break;
				}
			
			
			String cuerpo = request.getParameter("txtCuerpo");

			/*
			enviar mail según template
			D:\wsad\workspace\SunarpExtranetWeb\webApplication\WEB-INF\templates\sugerencias.tpl
			
			Nombre Remitente   : [NOMBRES]
			Apellido Remitente : [APELLIDOS]
			DNI                : [DNI]
			EMAIL del Remitente: [CORREO]
			Tipo de mensaje    : [TIPOMENSAJE]
			Mensaje:
			[MENSAJE]
			*/
			
			Hashtable mailTokens = new Hashtable();
			mailTokens.put("NOMBRES", nombres);
			mailTokens.put("APELLIDOS", apellidos);
			mailTokens.put("DNI", dni);
			mailTokens.put("CORREO", email);
			mailTokens.put("TIPOMENSAJE", tipo);
			mailTokens.put("MENSAJE", cuerpo);
			
			gob.pe.sunarp.extranet.common.MailProcessor.getInstance().saveMailTemplate(
				"sugerencias.tpl",
				mailTokens,
				destinatarios,
				conn);


			conn.commit();
		} //try

		catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn,request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn,request);
			response.setStyle("error");
		} finally {
			try{
				pool.release(conn);
			}catch(Throwable tt) {}
			end(request);
		}
		response.setStyle("ruta1");
		return response;
	}

} //fin de clase