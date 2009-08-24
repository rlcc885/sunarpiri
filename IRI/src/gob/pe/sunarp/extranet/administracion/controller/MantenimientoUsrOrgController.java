package gob.pe.sunarp.extranet.administracion.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.NonHandleableException;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.administracion.bean.MantenUsuarioBean;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.util.ValidacionException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;


public class MantenimientoUsrOrgController extends ControllerExtension implements Constantes{
	private String thisClass = MantenimientoUsrOrgController.class.getName() + ".";


	public MantenimientoUsrOrgController() {
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("mantenimientoUsuario", "Estado que se encarga de direccionar al estado respectivo segun criterio de seleccion"));

		addState(new State("usuarioDirecta", "Ventana de Busq. x Usuario-Directa."));
		addState(new State("usuarioApeNom", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("usuarioApeNomXorg", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("usuarioDocId", "Ventana de Busq. x Documento de Identidad."));
		addState(new State("usuarioOrg", "Ventana de Busq. x Organizacion."));
		addState(new State("usuarioTiempoInac", "Ventana de Busq. x Tiempo de Inactividad."));
		
		//8sep2002HT
		addState(new State("activacion", "activacion/desactivacion de usuarios"));
		addState(new State("exoneracion", "exoneracion de pago"));		
		addState(new State("cambioClave", "cambiar clave"));		
		
		setInitialState("muestra");
	}

	public String getTitle() {
		return new String("MantenimientoUsrOrgController");
	}
	
	private void muestraDocsId(ControllerRequest request, DBConnection dconn) throws DBException
	{
		DboTmDocIden doc_iden = new DboTmDocIden(dconn);
			
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_TIPO_DOC_ID);
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
		doc_iden.setField(DboTmDocIden.CAMPO_ESTADO,"1");
			
		ArrayList listaDocsIds = doc_iden.searchAndRetrieveList();
				
		java.util.List listaDocsId = new ArrayList();
				
		for(int i = 0; i < listaDocsIds.size(); i++){
			DboTmDocIden docIdent = (DboTmDocIden) listaDocsIds.get(i);
				
			DocIdenBean bean = new DocIdenBean();
			bean.setTipoDoc(docIdent.getField(DboTmDocIden.CAMPO_TIPO_DOC_ID));
			bean.setNomAbre(docIdent.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
			listaDocsId.add(bean);
		}
			
		ExpressoHttpSessionBean.getRequest(request).setAttribute("listaDocsId", listaDocsId);
	}
	
	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;

		try {
			init(request);
			validarSesion(request);
			
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
                        muestraDocsId(request,dconn);


			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			String cuentaId = usuario.getCuentaId();
			String estInicialCaja = "";
			String caja = usuario.getUserId();
			DboTaCaja dboCaja = new DboTaCaja (dconn);
			dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_ESTA);
			dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL,cuentaId);
			if(dboCaja.find()==true){
			estInicialCaja=dboCaja.getField(DboTaCaja.CAMPO_ESTA);
			}
			if(estInicialCaja.equals("1") && usuario.getPerfilId()==Constantes.PERFIL_CAJERO ){
					req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. Debe aperturar su caja para poder ingresar a la opción.");
					response.setStyle("ok");
			}
			else if(estInicialCaja.equals("0") && usuario.getPerfilId()==Constantes.PERFIL_CAJERO ){
					req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. El tesorero no ha realizado su apertura general.");
					response.setStyle("ok");
			}
			else{
			/** FIN OBSERVACIONES SUNARP **/
			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
			response.setStyle("muestraAO");
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					response.setStyle("muestraAJ");
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
					response.setStyle("muestraAG");
			if (usuario.getPerfilId() == PERFIL_CAJERO)
					response.setStyle("muestraCA");
			//cjvc 30Sept
			ExpressoHttpSessionBean.getSession(request).setAttribute("destinoPrepago", "Mantenimiento.do");
			}
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
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
	
	
protected ControllerResponse runActivacionState(
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

				//activar o desactivar un usuario
				//dependiendo de lo solicitado en param1

				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

				String param1 = req.getParameter("param1");
				String param2 = req.getParameter("param2");
				DboCuenta dboCuenta = new DboCuenta(dconn);
				dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
				dboCuenta.setField(DboCuenta.CAMPO_USR_ID, param2);

				java.util.ArrayList arr1 = dboCuenta.searchAndRetrieveList();
				DboCuenta cuenta = (DboCuenta) arr1.get(0);
				cuenta.setConnection(dconn);
				cuenta.setFieldsToUpdate(DboCuenta.CAMPO_ESTADO);

				if (param1.equals("0")) {
					//desactivar
					cuenta.setField(DboCuenta.CAMPO_ESTADO, "0");
					
					//se debe cambiar tambien el estado de su linea
					DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(dconn);
					dboLineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID,cuenta.getField(DboCuenta.CAMPO_USR_ID));
					if (dboLineaPrepago.find()==true)
					{
						dboLineaPrepago.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
						dboLineaPrepago.setField(DboLineaPrepago.CAMPO_ESTADO,"0");
						dboLineaPrepago.update();
					}
					
					req.setAttribute("mensaje1","Se desactivo exitosamente al usuario " + param2);
				} else {
					//activar
					cuenta.setField(DboCuenta.CAMPO_ESTADO, "1");
					
					//se debe cambiar tambien el estado de su linea
					DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(dconn);
					dboLineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID,cuenta.getField(DboCuenta.CAMPO_USR_ID));
					if (dboLineaPrepago.find()==true)
					{
						dboLineaPrepago.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
						dboLineaPrepago.setField(DboLineaPrepago.CAMPO_ESTADO,"1");
						dboLineaPrepago.update();
					}
					
					req.setAttribute("mensaje1","Se activo exitosamente al usuario " + param2);
				}
				cuenta.update();
				conn.commit();
				req.setAttribute("destino","Mantenimiento.do");
				response.setStyle("ok");
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
					}catch(Throwable ttt){}
				end(request);
			}
			return response;
		}
		
protected ControllerResponse runExoneracionState(
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
		//exonerar pago
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		String param1 = req.getParameter("param1");
		String param2 = req.getParameter("param2");

		DboCuenta dboCuenta = new DboCuenta(dconn);
		dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
		dboCuenta.setField(DboCuenta.CAMPO_USR_ID, param2);
		java.util.ArrayList arr1 = dboCuenta.searchAndRetrieveList();
		DboCuenta cuenta = (DboCuenta) arr1.get(0);
		cuenta.setConnection(dconn);
		cuenta.setFieldsToUpdate(DboCuenta.CAMPO_EXON_PAGO);

		if (param1.equals("0")) {
			//quitar exoneracion
			req.setAttribute("mensaje1","Se activo pago al usuario " + param2);
			cuenta.setField(DboCuenta.CAMPO_EXON_PAGO, "0");
		} else {
			//exonerar
			req.setAttribute("mensaje1","Se exonero exitosamente al usuario " + param2);			
			cuenta.setField(DboCuenta.CAMPO_EXON_PAGO, "1");
		}
		cuenta.update();
		conn.commit();
		response.setStyle("ok");
		
		req.setAttribute("destino","Mantenimiento.do");
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
					}catch(Throwable ttt){}
		end(request);
	}
	return response;
}



protected ControllerResponse runCambioClaveState(
	ControllerRequest request,
	ControllerResponse response)
	throws ControllerException {
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
	
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);	

	try {
		init(request);
		validarSesion(request);
		
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
		String usuarioId = req.getParameter("param1");
		//calcula nuevo password
		String nuevaClave = GeneraClave.generaNuevaClave(usuarioId);
		//Cambiar el password del usuario en TAM
		SecAdmin secAdmin = SecAdmin.getInstance();
		secAdmin.cambiaPasswordUsuario(usuarioId, nuevaClave);
		DboCuenta dboCuenta = new DboCuenta(dconn);
		dboCuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);
		dboCuenta.find();
		
		String peNatuId = dboCuenta.getField(DboCuenta.CAMPO_PE_NATU_ID);
		dboCuenta.setFieldsToUpdate(DboCuenta.CAMPO_CLAVE);
		dboCuenta.setField(DboCuenta.CAMPO_CLAVE, nuevaClave);
		dboCuenta.update();
		
		DboPeNatu dboPeNatu = new DboPeNatu(dconn);
		dboPeNatu.setFieldsToRetrieve(dboPeNatu.CAMPO_PERSONA_ID);
		dboPeNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,peNatuId);

		//enviar un email al usuario con su nueva clave		
		String email="";
		if (dboPeNatu.find()==true)
		{
			String personaId = dboPeNatu.getField(DboPeNatu.CAMPO_PERSONA_ID);
			DboPersona dboPersona = new DboPersona(dconn);
			dboPersona.setFieldsToRetrieve(DboPersona.CAMPO_EMAIL);
			dboPersona.setField(DboPersona.CAMPO_PERSONA_ID,personaId);
			if (dboPersona.find()==true)
			{
				email = dboPersona.getField(DboPersona.CAMPO_EMAIL);
				//Envio de Mail
				MailDataBean mailBean = new MailDataBean();
				mailBean.setTo(email);
				mailBean.setSubject("SOLICITUD DE CAMBIO DE PASSWORD");
				mailBean.setBody("Su id de usuario es "+usuarioId+" y su nueva contrasena es : "+nuevaClave);
				MailProcessor.getInstance().saveMail(mailBean, conn);
			}
		}
		conn.commit();		
		String mensaje = "Contraseña ha sido cambiada.";
		if (email.length()>0)
		 	mensaje = mensaje +" Un email con la nueva contraseña ha sido enviado a " + email;
		req.setAttribute("mensaje1",mensaje);		 
		req.setAttribute("destino","Mantenimiento.do");
		response.setStyle("ok");
		
	} 
		catch (ValidacionException e) 
		{
			log(Constantes.E08002_ERROR_TAM, e.getMensaje(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}	
		catch (CustomException e) 
		{
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}	
	catch (DBException dbe) 
	{
		log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
		principal(request);
		rollback(conn, request);
		response.setStyle("error");
	}
	 catch (Throwable ex) {
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		rollback(conn, request);
		response.setStyle("error");
	} finally {
					try{
					pool.release(conn);
					}catch(Throwable ttt){}
		end(request);
	}
	return response;
}
	
	protected ControllerResponse runMantenimientoUsuarioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		try{
			init(request);
			validarSesion(request);
			
			int opcion = Integer.parseInt(request.getParameter("radio"));
			String usuarioId = request.getParameter("userId");
			switch(opcion){
				case 1: 
					transition("usuarioDirecta", request, response); 
					break;
				case 2: 
					transition("usuarioApeNom", request, response); 
					break;
				case 3: 
					transition("usuarioDocId", request, response); 
					break;
				case 4: 
					transition("usuarioOrg", request, response); 
					break;
				case 5: 
					transition("usuarioTiempoInac", request, response); 
					break;
				case 6: 
					transition("usuarioApeNomXorg", request, response); 
					break;	 
			}
		} 
		catch (CustomException e) 
		{
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) 
		{
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}
		 finally {
			end(request);
		}
		return response;
	}
			
	protected ControllerResponse runUsuarioDirectaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);			
			
			String usuarioId = request.getParameter("userId");
			String pagina = request.getParameter("pagina");

			if ((usuarioId == null) || (usuarioId.trim().length() <= 0))
				throw new CustomException(Constantes.EC_MISSING_PARAM);
						
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			muestraDocsId(request,dconn);
			
			//el usuario cajero no puede buscarse a si mismo
			if (usuario.getPerfilId()==PERFIL_CAJERO)
				{
					if (usuarioId.equals(usuario.getUserId()))
						throw new ValidacionException("No se han encontrando resultados para su busqueda");
				}
							
			DboCuenta cuenta = new DboCuenta(dconn);
			cuenta.clearAll();
			cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
			cuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);
			
			java.util.List listaMantUsuario = new java.util.ArrayList();
			
			boolean transito=false;
			
			if (cuenta.find()==false)
				throw new ValidacionException("No se han encontrando resultados para su búsqueda");
				String cuentaId = cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
				
				MultiDBObject multi = new MultiDBObject(dconn);
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
				multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
				multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
				multi.setField("cuenta", DboCuenta.CAMPO_CUENTA_ID, cuentaId);

				if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION) 
					multi.setField("persona", DboPersona.CAMPO_JURIS_ID, usuario.getJurisdiccionId());
				
				java.util.Vector elementos = multi.searchAndRetrieve();
				
				if(elementos.size() != 1)
					throw new CustomException(Constantes.EC_GENERIC_DB_ERROR);
				
				MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
				
				MantenUsuarioBean manten_usuario = new MantenUsuarioBean();
				manten_usuario.setUsuario(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
				
				String tipo_usuario = null;
				
				//String a = multi.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
				if(oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).startsWith("1"))
					tipo_usuario = "EXTERNO";
				else
					tipo_usuario = "INTERNO";
					
				manten_usuario.setTipo(tipo_usuario);
				
				manten_usuario.setApe_Nom(oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + ", " + oneMulti.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
		
				if(oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).substring(2,3).equals("1"))
					tipo_usuario = "SI";
				else
					tipo_usuario = "--";
				
				manten_usuario.setAdmin_Org(tipo_usuario);
				
				String juriId = oneMulti.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID);
				
				if((juriId != null) && (juriId.trim().length() > 0)){
					DboPeJuri pejuri = new DboPeJuri();
					pejuri.setConnection(dconn);
					pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
					pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, DboPeNatu.CAMPO_PE_JURI_ID);
					pejuri.find();
					manten_usuario.setOrg_afiliada(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
				}else manten_usuario.setOrg_afiliada("--");
			
				
				
				//8sep2002HT
				manten_usuario.setFlagActivo(oneMulti.getField("cuenta", DboCuenta.CAMPO_ESTADO));
				manten_usuario.setFlagExonPago(oneMulti.getField("cuenta", DboCuenta.CAMPO_EXON_PAGO));
				
				String tipoUsr = oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);

				String pe_juri_id = oneMulti.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID);

				
				//obtener tipo y numero documento de la persona
				String tipoDocId = "";
				String tipoDocIdDescripcion="";
				String numeroDocumento="";		
				
				String personaId = oneMulti.getField("penatu", DboPeNatu.CAMPO_PERSONA_ID);
				DboPersona dboPersona = new DboPersona(dconn);
				dboPersona.setFieldsToRetrieve(DboPersona.CAMPO_TIPO_DOC_ID+"|"+DboPersona.CAMPO_NUM_DOC_IDEN);
				dboPersona.setField(DboPersona.CAMPO_PERSONA_ID,personaId);
				if (dboPersona.find()==true)
				{
					tipoDocId = dboPersona.getField(DboPersona.CAMPO_TIPO_DOC_ID);
					numeroDocumento = dboPersona.getField(DboPersona.CAMPO_NUM_DOC_IDEN);
				}
				DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
				dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
				dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,tipoDocId);
				dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO,"1");
				if (dboTmDocIden.find()==true)
					tipoDocIdDescripcion=dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					
				manten_usuario.setTipoDocumentoDesc(tipoDocIdDescripcion);
				manten_usuario.setNumeroDocumento(numeroDocumento);
				
				//*cjvc				
					DboLineaPrepago lin = new DboLineaPrepago(dconn);
					lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
					if(tipo_usuario.equals("SI"))
					{
						lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, " IS NULL ");
						lin.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, pe_juri_id);
					} else
						lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuentaId);
						
					if(lin.find())
						{
						manten_usuario.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
						manten_usuario.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
						}
				//*cjvc

				//Validacion: la cuenta es visible o no 
				//dependiendo del perfil del usuario logeado
				boolean cuentaVisible = false;
				if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
				{
					cuentaVisible=true;
					/*
					DboOrgCtas dboOrgCtas = new DboOrgCtas(conn);
					dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID,usuario.getCodOrg());
					dboOrgCtas.setField(DboOrgCtas.CAMPO_CUENTA_ID,manten_usuario.getUsuario());
					cuentaVisible = dboOrgCtas.find();
					*/
				}

				if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
					usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
				{
					if (    !(tipoUsr.startsWith("10"))    )
						cuentaVisible=true;
				}

				if (usuario.getPerfilId() == PERFIL_CAJERO)				
				{
					if (     tipoUsr.startsWith("11")      )
						{
						cuentaVisible=true;
						}
					else
						{
						//28dic2002
						//buscar el RUC de la empresa a la que pertenece el usuario
						DboPeJuri dboPeJuri = new DboPeJuri(dconn);
						dboPeJuri.setField(dboPeJuri.CAMPO_PE_JURI_ID,pe_juri_id);
						dboPeJuri.find();
						String persona_id = dboPeJuri.getField(dboPeJuri.CAMPO_PERSONA_ID);
						DboPersona dbop = new DboPersona(dconn);
						dbop.setField(dbop.CAMPO_PERSONA_ID,persona_id);
						dbop.find();
						String ruc = dbop.getField(dbop.CAMPO_NUM_DOC_IDEN);
						request.setAttribute("ruc",ruc);
						request.setParameter("ruc",ruc);
						
						request.setAttribute("conadm","1");
						request.setParameter("conadm","1");						
						
						//ademas se debe enviar el pe_juri_id
						request.setAttribute("persona_id",persona_id);
						request.setParameter("persona_id",persona_id);						
						transito=true;
						
						}
				}
			String sRefrescaAuto = null;			
			if (transito==false)
			{
				//8 nov - fechas de acceso y ultimo acceso
				String fechaCrea = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
				fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
				String fechaUlt  = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
				fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
				int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
				manten_usuario.setFechaAfiliacion(fechaCrea);
				manten_usuario.setFechaUltimoAcceso(fechaUlt);
				manten_usuario.setDiasDesdeUltimoAcceso(""+du);
								
				if (cuentaVisible==true)
					listaMantUsuario.add(manten_usuario);
					
			if (listaMantUsuario.size()==0)		
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");					
					
			if (listaMantUsuario!=null && listaMantUsuario.size()>0)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMantUsuario", listaMantUsuario);

			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					response.setStyle("muestraAO");
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					response.setStyle("muestraAJ");
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
					response.setStyle("muestraAG");
			if (usuario.getPerfilId() == PERFIL_CAJERO)			
					response.setStyle("muestraCA");
					
			}
			else
			{
			transition("usuarioOrg", request, response);	
			}
			
			/***** AGREGADO JBUGARIN 05/01/2007 INICIO DESCAJ *****/
					sRefrescaAuto = request.getParameter("chkRefrescaAuto");
					req.setAttribute("sRefrescaAuto",sRefrescaAuto);
					req.setAttribute("valorRB",request.getParameter("radio"));
					//para no perder el texto de busqueda del form manteOrg.jsp
					req.setAttribute("buscarUsuarioId",usuarioId);
			/***** JBUGARIN 05/01/2007 FIN DESCAJ *****/
			
		}//try
		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION", e);
			try {
				this.transition("muestra", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} 
		}
		
		 catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
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
					}catch(Throwable ttt){}
			end(request);
		}
		return response;
	}

	protected ControllerResponse runUsuarioApeNomState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		String sRefrescaAuto2 = null; //agregado jbugarin descaj
		
		try{
			init(request);
			validarSesion(request);
			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);

			String nom = request.getParameter("nombres");
			String ap = request.getParameter("apepat");
			String am = request.getParameter("apemat"); 
			
			
			muestraDocsId(request,dconn);
			
			StringBuffer nombres = null;
			StringBuffer apepat = null;
			StringBuffer apemat = null;
			boolean ape_materno = true;
			
			if (am == null || am.trim().length() <= 0)
				ape_materno = false;

			nombres = new StringBuffer(nom.trim()).append("%");
			apepat = new StringBuffer(ap.trim()).append("%");
			
			if(ape_materno)
				apemat = new StringBuffer(am.trim()).append("%");

			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PE_NATU_ID, "cuenta", DboCuenta.CAMPO_PE_NATU_ID);
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
							
			multi.setField("penatu", DboPeNatu.CAMPO_APE_PAT, apepat.toString());
			multi.setField("penatu", DboPeNatu.CAMPO_NOMBRES, nombres.toString());
			
			if(ape_materno)
				multi.setField("penatu", DboPeNatu.CAMPO_APE_MAT, apemat.toString());
			
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION) 
				multi.setField("persona", DboPersona.CAMPO_JURIS_ID, usuario.getJurisdiccionId());

			//el usuario cajero no puede buscarse a si mismo
			if (usuario.getPerfilId()==PERFIL_CAJERO)
				multi.setAppendWhereClause(" CUENTA.USR_ID != '" + usuario.getUserId() +"'");			
			
			MultiDBObject multis = new MultiDBObject(dconn);
			String tipo_usuario = null;
			
			java.util.List listaMantUsuario = new java.util.ArrayList();
				
			java.util.List l = multi.searchAndRetrieve(
							DboPeNatu.CAMPO_APE_PAT+"|"+
							DboPeNatu.CAMPO_APE_MAT+"|"+
							DboPeNatu.CAMPO_NOMBRES							
							);
			
			if (l.size()==0)
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");
			
			for(Iterator i = l.iterator(); i.hasNext();){
					multis = (MultiDBObject) i.next();
					
					MantenUsuarioBean manten_usuario = new MantenUsuarioBean();
					manten_usuario.setUsuario(multis.getField("cuenta", DboCuenta.CAMPO_USR_ID));
	
				//*cjvc				
					DboLineaPrepago lin = new DboLineaPrepago(dconn);
					lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
					lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, multis.getField("cuenta", DboCuenta.CAMPO_CUENTA_ID));
						
					if(lin.find())
						{
						manten_usuario.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
						manten_usuario.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
						}


					if(multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).startsWith("1"))
						tipo_usuario = "EXTERNO";
					else
						tipo_usuario = "INTERNO";
						
					manten_usuario.setTipo(tipo_usuario);
					
					manten_usuario.setApe_Nom(multis.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + multis.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + ", " + multis.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
					
					if(multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).substring(2,3).equals("1"))
						tipo_usuario = "SI";
					else
						tipo_usuario = "--";
					
					manten_usuario.setAdmin_Org(tipo_usuario);
	
					String juriId = multis.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID);
	
					if((juriId != null) && (juriId.trim().length() > 0)){
						DboPeJuri pejuri = new DboPeJuri();
						pejuri.setConnection(dconn);
						pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
						pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, multis.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID));
						pejuri.find();
						manten_usuario.setOrg_afiliada(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
					}else manten_usuario.setOrg_afiliada("--");
					
				//8sep2002HT
				manten_usuario.setFlagActivo(multis.getField("cuenta", DboCuenta.CAMPO_ESTADO));
				manten_usuario.setFlagExonPago(multis.getField("cuenta", DboCuenta.CAMPO_EXON_PAGO));
				String tipoUsr = multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
				
				
				
					//tipo de documento y su numero				
					String tipoDocId       = multis.getField("persona", DboPersona.CAMPO_TIPO_DOC_ID);
					String numeroDocumento = multis.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN);

					String tipoDocIdDescripcion="";
					DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
					dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,tipoDocId);
					if (dboTmDocIden.find()==true)
						tipoDocIdDescripcion=dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);

				manten_usuario.setNumeroDocumento(numeroDocumento);
				manten_usuario.setTipoDocumentoDesc(tipoDocIdDescripcion);
										
				//Validacion: la cuenta es visible o no 
				//dependiendo del perfil del usuario logeado
				boolean cuentaVisible = false;
				if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
				{	
					cuentaVisible=true;
					/*
					dboOrgCtas.clearAll();
					dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID,usuario.getCodOrg());
					dboOrgCtas.setField(DboOrgCtas.CAMPO_CUENTA_ID,manten_usuario.getUsuario());
					cuentaVisible = dboOrgCtas.find();
					*/
				}
				if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
					usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
				{
					if (    !(tipoUsr.startsWith("10"))    )
						cuentaVisible=true;
				}

				if (usuario.getPerfilId() == PERFIL_CAJERO)				
				{
					if (     tipoUsr.startsWith("11")      )
						cuentaVisible=true;
				}


				//8 nov - fechas de acceso y ultimo acceso
				String fechaCrea = multis.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
				fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
				String fechaUlt  = multis.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
				fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
				int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
				manten_usuario.setFechaAfiliacion(fechaCrea);
				manten_usuario.setFechaUltimoAcceso(fechaUlt);
				manten_usuario.setDiasDesdeUltimoAcceso(""+du);
				
				if (cuentaVisible)
					listaMantUsuario.add(manten_usuario);
			}//for
				
			if (listaMantUsuario.size()==0)		
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");					
							
			if (listaMantUsuario!=null && listaMantUsuario.size()>0)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMantUsuario", listaMantUsuario);
			
			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					response.setStyle("muestraAO");			
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					response.setStyle("muestraAJ");
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
					response.setStyle("muestraAG");
			if (usuario.getPerfilId() == PERFIL_CAJERO)
					response.setStyle("muestraCA");
			
			/**** AGREGADO JBUGARIN 05/07/2007 INICIO DESCAJ ****/
						sRefrescaAuto2 = request.getParameter("chkRefrescaAuto");
						req.setAttribute("sRefrescaAuto",sRefrescaAuto2);
						req.setAttribute("valorRB",request.getParameter("radio"));
						//para no perder el texto de busqueda del form manteOrg.jsp
						req.setAttribute("buscarApellidoPaterno",ap);
						req.setAttribute("buscarApellidoMaterno",am);
						req.setAttribute("buscarNombre",nom);
			/**** JBUGARIN 05/07/2007	FIN DESCAJ ****/
		} 		
		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION", e);
			try {
				this.transition("muestra", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} 
		}
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
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
					}catch(Throwable ttt){}
			end(request);
		}
		return response;	
	}


	protected ControllerResponse runUsuarioApeNomXorgState(ControllerRequest request, ControllerResponse response) throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			validarSesion(request);

			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);
			String nom = request.getParameter("nombres");
			String ap = request.getParameter("apepat");
			String am = request.getParameter("apemat");

			muestraDocsId(request,dconn);

			StringBuffer nombres = null;
			StringBuffer apepat = null;
			StringBuffer apemat = null;
			
			boolean nombre = true;
			boolean ape_paterno = true;			
			boolean ape_materno = true;
			if (nom == null || nom.trim().length() <= 0)
				nombre = false;

			if (ap == null || ap.trim().length() <= 0)
				ape_paterno = false;

			if (am == null || am.trim().length() <= 0)
					ape_materno = false;
			//nombres = new StringBuffer(nom.trim()).append("%");
			//apepat = new StringBuffer(ap.trim()).append("%");
			if (nombre)
				nombres = new StringBuffer(nom.trim()).append("%");

			if (ape_paterno)
				apepat = new StringBuffer(ap.trim()).append("%");			
			
			if (ape_materno)
					apemat = new StringBuffer(am.trim()).append("%");

			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "lineaprepago");
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PE_NATU_ID, "cuenta", DboCuenta.CAMPO_PE_NATU_ID);
			multi.setForeignKey("lineaprepago", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);

						if (ape_paterno)
				multi.setField("penatu", DboPeNatu.CAMPO_APE_PAT, apepat.toString());
			if (nombre)
				multi.setField("penatu", DboPeNatu.CAMPO_NOMBRES, nombres.toString());

			multi.setField("penatu", DboPeNatu.CAMPO_PE_JURI_ID, usuario.getCodOrg());
			if (ape_materno)
					multi.setField("penatu", DboPeNatu.CAMPO_APE_MAT, apemat.toString());

			//el usuario cajero no puede buscarse a si mismo
			if (usuario.getPerfilId()==PERFIL_CAJERO)
				multi.setAppendWhereClause(" CUENTA.USR_ID != '" + usuario.getUserId()+"'");			
							
			MultiDBObject multis = new MultiDBObject(dconn);
			String tipo_usuario = null;
			java.util.List listaMantUsuario = new java.util.ArrayList();

			java.util.List l = multi.searchAndRetrieve(
							DboPeNatu.CAMPO_APE_PAT+"|"+
							DboPeNatu.CAMPO_APE_MAT+"|"+
							DboPeNatu.CAMPO_NOMBRES
							);
			
			if (l.size()==0)
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");


			for (Iterator i = l.iterator(); i.hasNext();) {
					multis = (MultiDBObject) i.next();

					MantenUsuarioBean manten_usuario = new MantenUsuarioBean();
					manten_usuario.setUsuario(multis.getField("cuenta", DboCuenta.CAMPO_USR_ID));
					if (multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).startsWith("1"))
							tipo_usuario = "EXTERNO";
					else
							tipo_usuario = "INTERNO";
					manten_usuario.setTipo(tipo_usuario);
					manten_usuario.setApe_Nom(multis.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + multis.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + ", " + multis.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
					String fh = FechaUtil.expressoDateTimeToUtilDateTime(multis.getField("cuenta", DboCuenta.CAMPO_TS_CREA));
					manten_usuario.setFechaHoraRegistro(fh);
					manten_usuario.setSaldo(multis.getField("lineaprepago", DboLineaPrepago.CAMPO_SALDO));
					if ((multis.getField("cuenta", DboCuenta.CAMPO_ESTADO)).equalsIgnoreCase("0"))
						manten_usuario.setEstado("INACTIVO");
					else
						manten_usuario.setEstado("ACTIVO");
					//--16Sept2002cjvc77
					manten_usuario.setLineaPrepago(multis.getField("lineaprepago", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
					manten_usuario.setSaldo(multis.getField("lineaprepago", DboLineaPrepago.CAMPO_SALDO));
					
					if (multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).substring(2, 3).equals("1"))
							tipo_usuario = "SI";
					else
							tipo_usuario = "--";

					manten_usuario.setAdmin_Org(tipo_usuario);
					String juriId = multis.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID);

					if ((juriId != null) && (juriId.trim().length() > 0)) {
						DboPeJuri pejuri = new DboPeJuri();
						pejuri.setConnection(dconn);
						pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
						pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, multis.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID));
						pejuri.find();
						manten_usuario.setOrg_afiliada(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
					} else
							manten_usuario.setOrg_afiliada("--");

					//8sep2002HT
					manten_usuario.setFlagActivo(multis.getField("cuenta", DboCuenta.CAMPO_ESTADO));
					manten_usuario.setFlagExonPago(multis.getField("cuenta", DboCuenta.CAMPO_EXON_PAGO));
					String tipoUsr = multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);


					//tipo de documento y su numero				
					DboPersona dboPersona = new DboPersona(dconn);
					dboPersona.setField(DboPersona.CAMPO_PERSONA_ID,multis.getField("penatu", DboPeNatu.CAMPO_PERSONA_ID));
					if (dboPersona.find())
					{
						String tipoDocId       = dboPersona.getField(DboPersona.CAMPO_TIPO_DOC_ID);
						String numeroDocumento = dboPersona.getField(DboPersona.CAMPO_NUM_DOC_IDEN);
	
						String tipoDocIdDescripcion="";
						DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
						dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
						dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,tipoDocId);
						if (dboTmDocIden.find()==true)
							tipoDocIdDescripcion=dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);
	
						manten_usuario.setNumeroDocumento(numeroDocumento);
						manten_usuario.setTipoDocumentoDesc(tipoDocIdDescripcion);						
					}
					
					//Validacion: la cuenta es visible o no 
					//dependiendo del perfil del usuario logeado
					boolean cuentaVisible = false;
					if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
					{	
						cuentaVisible=true;
						/*
						2nov2002
						dboOrgCtas.clearAll();
						dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID,usuario.getCodOrg());
						dboOrgCtas.setField(DboOrgCtas.CAMPO_CUENTA_ID,multis.getField("cuenta", DboCuenta.CAMPO_CUENTA_ID));
						cuentaVisible = dboOrgCtas.find();
						*/
					}
					
					if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
						usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					{
						if (    !(tipoUsr.startsWith("10"))    )
							cuentaVisible=true;
					}
	
					if (usuario.getPerfilId() == PERFIL_CAJERO)				
					{
						if (     tipoUsr.startsWith("11")      )
							cuentaVisible=true;
					}
					

					//8 nov - fechas de acceso y ultimo acceso
					String fechaCrea = multis.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
					fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
					String fechaUlt  = multis.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
					fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
					int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
					manten_usuario.setFechaAfiliacion(fechaCrea);
					manten_usuario.setFechaUltimoAcceso(fechaUlt);
					manten_usuario.setDiasDesdeUltimoAcceso(""+du);					
										
					if (cuentaVisible == true)
						listaMantUsuario.add(manten_usuario);

			} //for
			
			if (listaMantUsuario.size()==0)		
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");								
			
			if (listaMantUsuario!=null && listaMantUsuario.size()>0)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMantUsuario", listaMantUsuario);

			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					response.setStyle("muestraAO");
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					response.setStyle("muestraAJ");
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
					response.setStyle("muestraAG");
			if (usuario.getPerfilId() == PERFIL_CAJERO)
					response.setStyle("muestraCA");
					
		}
		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION", e);
			try {
				this.transition("muestra", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} 
		}		
		 catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
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


	protected ControllerResponse runUsuarioDocIdState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			 
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			validarSesion(request);

			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);

			String numopt = request.getParameter("numopt");
			String numdoc = request.getParameter("numdoc");

			muestraDocsId(request,dconn);
			
			if (numdoc == null || numdoc.trim().length() <= 0)
				throw new CustomException(Constantes.EC_MISSING_PARAM);

			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.setForeignKey("persona", DboPersona.CAMPO_PERSONA_ID, "penatu", DboPeNatu.CAMPO_PERSONA_ID);
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PE_NATU_ID, "cuenta", DboCuenta.CAMPO_PE_NATU_ID);
			
			multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, numopt);
			multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, numdoc);

			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION) 
				multi.setField("persona", DboPersona.CAMPO_JURIS_ID, usuario.getJurisdiccionId());
			

			//el usuario cajero no puede buscarse a si mismo
			if (usuario.getPerfilId()==PERFIL_CAJERO)
				multi.setAppendWhereClause(" CUENTA.USR_ID != '" + usuario.getUserId()+"'");

			java.util.List listaMantUsuario = new java.util.ArrayList();
			java.util.List l = multi.searchAndRetrieve();
			
			if (l.size()==0)
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");
				
			for(Iterator i = l.iterator(); i.hasNext();){
					MultiDBObject oneMulti = (MultiDBObject) i.next();
					
					MantenUsuarioBean manten_usuario = new MantenUsuarioBean();
					manten_usuario.setUsuario(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
					
					String tipo_usuario = null;
					
					//String a = multi.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
					if(oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).startsWith("1"))
						tipo_usuario = "EXTERNO";
					else
						tipo_usuario = "INTERNO";
						
					manten_usuario.setTipo(tipo_usuario);
					
					manten_usuario.setApe_Nom(oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + ", " + oneMulti.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
			
					if(oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).substring(2,3).equals("1"))
						tipo_usuario = "SI";
					else
						tipo_usuario = "--";
					
					manten_usuario.setAdmin_Org(tipo_usuario);
					
					String juriId = oneMulti.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID);
	
					if((juriId != null) && (juriId.trim().length() > 0)){
						DboPeJuri pejuri = new DboPeJuri();
						pejuri.setConnection(dconn);
						pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
						pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, DboPeNatu.CAMPO_PE_JURI_ID);
						pejuri.find();
						manten_usuario.setOrg_afiliada(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
					}else manten_usuario.setOrg_afiliada("--");
	
				//*cjvc				
					DboLineaPrepago lin = new DboLineaPrepago(dconn);
					lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
					lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, oneMulti.getField("cuenta", DboCuenta.CAMPO_CUENTA_ID));
						
					if(lin.find())
						{
						manten_usuario.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
						manten_usuario.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
						}
				//*cjvc
	
					//8sep2002HT
					manten_usuario.setFlagActivo(oneMulti.getField("cuenta", DboCuenta.CAMPO_ESTADO));
					manten_usuario.setFlagExonPago(oneMulti.getField("cuenta", DboCuenta.CAMPO_EXON_PAGO));
					String tipoUsr = oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
				
					//Validacion: la cuenta es visible o no 
					//dependiendo del perfil del usuario logeado
					boolean cuentaVisible = false;
					if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
					{	
						cuentaVisible=false;
						/*
						dboOrgCtas.clearAll();
						dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID,usuario.getCodOrg());
						dboOrgCtas.setField(DboOrgCtas.CAMPO_CUENTA_ID,manten_usuario.getUsuario());
						cuentaVisible = dboOrgCtas.find();
						*/
					}
					if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
						usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					{
						if (    !(tipoUsr.startsWith("10"))    )
							cuentaVisible=true;
					}
	
					if (usuario.getPerfilId() == PERFIL_CAJERO)				
					{
						if (     tipoUsr.startsWith("11")      )
							cuentaVisible=true;
					}
				/**** AGREGADO JBUGARIN 05/01/2007 DESCAJ INICIO ****/
								String sRefrescaAuto5 = request.getParameter("chkRefrescaAuto");
								req.setAttribute("sRefrescaAuto",sRefrescaAuto5);
								req.setAttribute("valorRB",request.getParameter("radio"));
								//para no perder el texto de busqueda del form manteUsuarioCA.jsp
								req.setAttribute("buscarTipoDocu",numopt);
								req.setAttribute("buscarNuDocu",numdoc);
				/**** JBUGARIN 05/01/2007 DESCAJ FIN ****/	
					
					//8 nov - fechas de acceso y ultimo acceso
					String fechaCrea = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
					fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
					String fechaUlt  = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
					fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
					int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
					manten_usuario.setFechaAfiliacion(fechaCrea);
					manten_usuario.setFechaUltimoAcceso(fechaUlt);
					manten_usuario.setDiasDesdeUltimoAcceso(""+du);			
					
	
					//tipo de documento y su numero				
					String tipoDocId       = oneMulti.getField("persona", DboPersona.CAMPO_TIPO_DOC_ID);
					String numeroDocumento = oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN);

					String tipoDocIdDescripcion="";
					DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
					dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,tipoDocId);
					if (dboTmDocIden.find()==true)
						tipoDocIdDescripcion=dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);
						
					manten_usuario.setTipoDocumentoDesc(tipoDocIdDescripcion);
					manten_usuario.setNumeroDocumento(numeroDocumento);								
					
					if (cuentaVisible==true)				
						listaMantUsuario.add(manten_usuario);						
				}
				
			if (listaMantUsuario.size()==0)		
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");									
			
			if (listaMantUsuario!=null && listaMantUsuario.size()>0)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMantUsuario", listaMantUsuario);
	
			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					response.setStyle("muestraAO");	
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					response.setStyle("muestraAJ");
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
					response.setStyle("muestraAG");
			if (usuario.getPerfilId() == PERFIL_CAJERO)
					response.setStyle("muestraCA");
					
		} 
		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION", e);
			try {
				this.transition("muestra", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} 
		}		
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
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

	protected ControllerResponse runUsuarioOrgState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			validarSesion(request);
			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);
			String tipoUsr = null;

			muestraDocsId(request,dconn);
			
			String razsoc = request.getParameter("razsoc");
			String ruc = request.getParameter("ruc");
			
			boolean paseRuc = true;
			boolean paseRS = true;
			
			if (razsoc == null || razsoc.trim().length() <= 0)
				paseRS = false;

			if(ruc == null || ruc.trim().length() <= 0)
				paseRuc = false;
			
			if (!paseRuc && !paseRS)
				throw new CustomException(Constantes.EC_MISSING_PARAM);
				
			java.util.List listaMantOrg = new java.util.ArrayList();
				
			if(paseRuc)
			{
				MultiDBObject multi = new MultiDBObject(dconn);
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
				//multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboOrgCtas", "orgctas");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
				
				multi.setForeignKey("persona", DboPersona.CAMPO_PERSONA_ID, "pejuri", DboPeJuri.CAMPO_PERSONA_ID);
				multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PE_JURI_ID, "penatu", DboPeNatu.CAMPO_PE_JURI_ID);
				multi.setForeignKey("penatu", DboPeNatu.CAMPO_PE_NATU_ID, "cuenta", DboCuenta.CAMPO_PE_NATU_ID);
	
				multi.setField("persona", DboPersona.CAMPO_TPO_PERS, "J");
				multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, ruc);
				
				String pi =request.getParameter("persona_id");
				if (pi!=null)
					multi.setField("persona", DboPersona.CAMPO_PERSONA_ID, pi);	
	
				if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION) 
					multi.setField("persona", DboPersona.CAMPO_JURIS_ID, usuario.getJurisdiccionId());
				
			//el usuario cajero no puede buscarse a si mismo
			if (usuario.getPerfilId()==PERFIL_CAJERO)
				multi.setAppendWhereClause(" CUENTA.USR_ID != '" + usuario.getUserId()+"'");	
	
				java.util.List l = multi.searchAndRetrieve();
				
				if (l.size()==0)
					throw new ValidacionException("No se han encontrando resultados para su busqueda","");
				
				if(l.size() > 0){
			
					for(Iterator i = l.iterator(); i.hasNext();){
						MultiDBObject oneMulti = (MultiDBObject) i.next();
						
						MantenUsuarioBean manten_usuario = new MantenUsuarioBean();
						manten_usuario.setUsuario(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
						tipoUsr = oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
						
						String tipo_usuario = null;
						
					//*cjvc				
						DboLineaPrepago lin = new DboLineaPrepago(dconn);
						lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
						lin.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, oneMulti.getField("pejuri", DboPeJuri.CAMPO_PE_JURI_ID));
						lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, " IS NULL ");
							
						if(lin.find())
							{
							manten_usuario.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
							manten_usuario.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
							}
					//*cjvc
						
						
						//String a = multi.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
						if(oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).startsWith("1"))
							tipo_usuario = "EXTERNO";
						else
							tipo_usuario = "INTERNO";
							
						manten_usuario.setTipo(tipo_usuario);
						
						manten_usuario.setApe_Nom(oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + ", " + oneMulti.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
				
						if(oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).substring(2,3).equals("1"))
							tipo_usuario = "SI";
						else
							tipo_usuario = "--";
						
						manten_usuario.setAdmin_Org(tipo_usuario);
						manten_usuario.setOrg_afiliada(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
		
		
					//tipo de documento y su numero				
					String tipoDocId       = oneMulti.getField("persona", DboPersona.CAMPO_TIPO_DOC_ID);
					String numeroDocumento = oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN);

					String tipoDocIdDescripcion="";
					DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
					dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,tipoDocId);
					if (dboTmDocIden.find()==true)
						tipoDocIdDescripcion=dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);

				manten_usuario.setNumeroDocumento(numeroDocumento);
				manten_usuario.setTipoDocumentoDesc(tipoDocIdDescripcion);
						
						//Validacion: la cuenta es visible o no 
						//dependiendo del perfil del usuario logeado
						boolean cuentaVisible = false;
						if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
						{	
							dboOrgCtas.clearAll();
							dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID,usuario.getCodOrg());
							dboOrgCtas.setField(DboOrgCtas.CAMPO_CUENTA_ID,manten_usuario.getUsuario());
							cuentaVisible = dboOrgCtas.find();
						}
						if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
							usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
						{
							if (    !(tipoUsr.startsWith("10"))    )
								cuentaVisible=true;
						}
		
						if (usuario.getPerfilId() == PERFIL_CAJERO)				
						{
							if (     tipoUsr.startsWith("101")      )
								cuentaVisible=true;
						}
						
							//8 nov - fechas de acceso y ultimo acceso
							String fechaCrea = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
							fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
							String fechaUlt  = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
							fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
							int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
							manten_usuario.setFechaAfiliacion(fechaCrea);
							manten_usuario.setFechaUltimoAcceso(fechaUlt);
							manten_usuario.setDiasDesdeUltimoAcceso(""+du);							
						
						if (cuentaVisible==true)
							listaMantOrg.add(manten_usuario);
					}//for
				}
				
			}

















			
if(paseRS)
{
StringBuffer sb = new StringBuffer();
sb.append(DboPeJuri.CAMPO_RAZ_SOC);
sb.append(" like '").append(razsoc.trim()).append("%'");
			
if (usuario.getPerfilId()==PERFIL_ADMIN_JURISDICCION)
		{
			sb.append(" and ").append(DboPeJuri.CAMPO_JURIS_ID);
			sb.append(" = ").append(usuario.getJurisdiccionId());
		}
			
DboPeJuri dbo1 = new DboPeJuri(dconn);
dbo1.setCustomWhereClause(sb.toString());
java.util.ArrayList arri = dbo1.searchAndRetrieveList(DboPeJuri.CAMPO_RAZ_SOC);			
				
if (arri.size()==0)
	throw new ValidacionException("No se han encontrando resultados para su búsqueda");


DboPersona dboPersona = new DboPersona(dconn);
DboCuenta  dboCuenta  = new DboCuenta(dconn);
DboPeNatu  dboPeNatu  = new DboPeNatu(dconn);
DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
DboLineaPrepago lin = new DboLineaPrepago(dconn);
				
for (int i=0; i < arri.size(); i++)
{
	DboPeJuri dboPeJuri = (DboPeJuri) arri.get(i);
	MantenUsuarioBean manten_usuario = new MantenUsuarioBean();
	String personaId = dboPeJuri.getField(DboPeJuri.CAMPO_PERSONA_ID);
	String peJuriId= dboPeJuri.getField(DboPeJuri.CAMPO_PE_JURI_ID);
	
	manten_usuario.setOrg_afiliada(dboPeJuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
	
	//persona
	dboPersona.clearAll();
	dboPersona.setFieldsToRetrieve(dboPersona.CAMPO_NUM_DOC_IDEN);
	dboPersona.setField(dboPersona.CAMPO_PERSONA_ID,personaId);
	dboPersona.find();
	
	//tipo de documento y su numero				
	String tipoDocId       = dboPersona.getField(DboPersona.CAMPO_TIPO_DOC_ID);
	String numeroDocumento = dboPersona.getField(DboPersona.CAMPO_NUM_DOC_IDEN);

	String tipoDocIdDescripcion="";
	dboTmDocIden.clearAll();
	dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
	dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,tipoDocId);
	if (dboTmDocIden.find()==true)
			tipoDocIdDescripcion=dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);

	manten_usuario.setNumeroDocumento(numeroDocumento);
	manten_usuario.setTipoDocumentoDesc(tipoDocIdDescripcion);	


	//buscar el usuario administrador de la empresa
	dboPeNatu.clearAll();
	dboPeNatu.setField(dboPeNatu.CAMPO_PE_JURI_ID, peJuriId);
	java.util.ArrayList arrj = dboPeNatu.searchAndRetrieveList();
	tipoUsr="";
	for (int j = 0; j < arrj.size(); j++)
		{
		dboCuenta.clearAll();
		DboPeNatu dbo2 = (DboPeNatu) arrj.get(j);
		String peNatuId= dbo2.getField(dboPeNatu.CAMPO_PE_NATU_ID);
		manten_usuario.setApe_Nom(
		dbo2.getField(DboPeNatu.CAMPO_APE_PAT)
		+ " " + 
		dbo2.getField(DboPeNatu.CAMPO_APE_MAT)
		+ ", " + 
		dbo2.getField(DboPeNatu.CAMPO_NOMBRES)); 		
		dboCuenta.setField(dboCuenta.CAMPO_PE_NATU_ID, peNatuId);
		boolean b = dboCuenta.find();
		if (b==true)
			{
			tipoUsr  = dboCuenta.getField(dboCuenta.CAMPO_TIPO_USR);
			String xf = tipoUsr.substring(2,3);
			if (xf.equals("1"))
				break; //usuario administrador encontrado
			}
		}

		
	//linea prepago	
	lin.clearAll();
	lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
	lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, " is null ");
	lin.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, peJuriId);
	

	if(lin.find())
		{
		manten_usuario.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
		manten_usuario.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
		}

	//verificar si es usuario externo o interno
	if(tipoUsr.startsWith("1"))
		manten_usuario.setTipo("EXTERNO");
	else
		manten_usuario.setTipo("INTERNO");
		


	if(tipoUsr.substring(2,3).equals("1"))
		manten_usuario.setAdmin_Org("SI");	
	else
		manten_usuario.setAdmin_Org("--");	


	manten_usuario.setFlagActivo(dboCuenta.getField(DboCuenta.CAMPO_ESTADO));
	manten_usuario.setFlagExonPago(dboCuenta.getField(DboCuenta.CAMPO_EXON_PAGO));
	manten_usuario.setUsuario(dboCuenta.getField(DboCuenta.CAMPO_USR_ID));
;


	//Validacion: la cuenta es visible o no 
	//dependiendo del perfil del usuario logeado
	boolean cuentaVisible = false;
	if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
			cuentaVisible=true;
	
	if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
		usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
		{
						//no interesa el primer flag, pero
						//los dos siguientes deben ser "01" 
						String f = tipoUsr.substring(1,3);
						if (   f.equals("01")    )
								cuentaVisible=true;	
		}
		
	if (usuario.getPerfilId() == PERFIL_CAJERO)				
		{
		if (     tipoUsr.startsWith("10")      )
				cuentaVisible=true;
		}


		String fechaCrea = dboCuenta.getField(DboCuenta.CAMPO_TS_CREA);
		fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
		String fechaUlt  = dboCuenta.getField(DboCuenta.CAMPO_TS_ULT_ACC);
		fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
		int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
		manten_usuario.setFechaAfiliacion(fechaCrea);
		manten_usuario.setFechaUltimoAcceso(fechaUlt);
		manten_usuario.setDiasDesdeUltimoAcceso(String.valueOf(du));	

		if (cuentaVisible==true)				
			listaMantOrg.add(manten_usuario);
							
}//for arri

				
						
											
			
}//if razSoc
			
































			if (listaMantOrg.size()==0)		
				throw new ValidacionException("No se han encontrando resultados para su búsqueda");
							
			if (listaMantOrg!=null && listaMantOrg.size()>0)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMantUsuario", listaMantOrg);			

			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					response.setStyle("muestraAO");
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					response.setStyle("muestraAJ");
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
					response.setStyle("muestraAG");
			if (usuario.getPerfilId() == PERFIL_CAJERO)
					response.setStyle("muestraCA");
					
		}
		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION", e);
			try {
				this.transition("muestra", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} 
		}
		 catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
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

	protected ControllerResponse runUsuarioTiempoInacState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			validarSesion(request);

			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);

			muestraDocsId(request,dconn);

			String tiempo = request.getParameter("tiempo");
			
			if (tiempo == null || tiempo.trim().length() <= 0)
				throw new CustomException(Constantes.EC_MISSING_PARAM);
				
			int dias;
			try{
				dias = Integer.parseInt(tiempo);
			}catch(NumberFormatException nfe){
				throw new CustomException(Constantes.EC_PARAM_MISSFORMED);
			}
			
			MultiDBObject multi = new MultiDBObject(dconn);
			
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			
			String fecha_limite = FechaUtil.add(FechaUtil.dateToString(new java.util.Date()), -dias);
			String fecha = " < " + FechaUtil.stringTimeToOracleString(fecha_limite + " 00:00:00");
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION) {
				multi.setField("persona", DboPersona.CAMPO_JURIS_ID, usuario.getJurisdiccionId());
			}
			
			//el usuario cajero no puede buscarse a si mismo
			if (usuario.getPerfilId()==PERFIL_CAJERO)
				multi.setAppendWhereClause(" CUENTA.USR_ID != '" + usuario.getUserId()+"'");						

			//multi.setField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC, fecha);cb
			multi.setAppendWhereClause(" CUENTA." + DboCuenta.CAMPO_TS_ULT_ACC + fecha);//cb

			MultiDBObject multis = new MultiDBObject(dconn);
			String tipo_usuario = null;
			java.util.List listaMantLimFecha = new java.util.ArrayList();
									
			java.util.List l = multi.searchAndRetrieve();
			
			if (l.size()==0)
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");
				
			if(l.size() > 0){
									
				for(Iterator i = l.iterator(); i.hasNext();){
					multis = (MultiDBObject) i.next();
					
					MantenUsuarioBean manten_usuario = new MantenUsuarioBean();
					manten_usuario.setUsuario(multis.getField("cuenta", DboCuenta.CAMPO_USR_ID));
	
					if(multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).startsWith("1"))
						tipo_usuario = "EXTERNO";
					else
						tipo_usuario = "INTERNO";
						
					manten_usuario.setTipo(tipo_usuario);
					
					manten_usuario.setApe_Nom(multis.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + multis.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + ", " + multis.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
					
					if(multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR).substring(2,3).equals("1"))
						tipo_usuario = "SI";
					else
						tipo_usuario = "--";
					
					manten_usuario.setAdmin_Org(tipo_usuario);
	
					String juriId = multis.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID);
					
					if((juriId != null) && (juriId.trim().length() > 0)){
						DboPeJuri pejuri = new DboPeJuri(dconn);
						pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
						pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, multis.getField("penatu", DboPeNatu.CAMPO_PE_JURI_ID));
						pejuri.find();
						manten_usuario.setOrg_afiliada(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
					}else manten_usuario.setOrg_afiliada("--");
					
						manten_usuario.setFlagActivo(multis.getField("cuenta", DboCuenta.CAMPO_ESTADO));
						manten_usuario.setFlagExonPago(multis.getField("cuenta", DboCuenta.CAMPO_EXON_PAGO));
						String tipoUsr = multis.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
				
						//Validacion: la cuenta es visible o no 
						//dependiendo del perfil del usuario logeado
						boolean cuentaVisible = false;
						if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
						{	
							cuentaVisible=true;
							/*
							dboOrgCtas.clearAll();
							dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID,usuario.getCodOrg());
							dboOrgCtas.setField(DboOrgCtas.CAMPO_CUENTA_ID,manten_usuario.getUsuario());
							cuentaVisible = dboOrgCtas.find();
							*/
						}
						if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
							usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
						{
							if (    !(tipoUsr.startsWith("10"))    )
								cuentaVisible=true;
						}
		
						if (usuario.getPerfilId() == PERFIL_CAJERO)				
						{
							if (     tipoUsr.startsWith("11")      )
								cuentaVisible=true;
						}
						
						
						//8 nov - fechas de acceso y ultimo acceso
						String fechaCrea = multis.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
						fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
						String fechaUlt  = multis.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
						fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
						int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
						manten_usuario.setFechaAfiliacion(fechaCrea);
						manten_usuario.setFechaUltimoAcceso(fechaUlt);
						manten_usuario.setDiasDesdeUltimoAcceso(""+du);							
						
					//tipo de documento y su numero				
					String tipoDocId       = multis.getField("persona", DboPersona.CAMPO_TIPO_DOC_ID);
					String numeroDocumento = multis.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN);

					String tipoDocIdDescripcion="";
					DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
					dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,tipoDocId);
					if (dboTmDocIden.find()==true)
						tipoDocIdDescripcion=dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);

				manten_usuario.setNumeroDocumento(numeroDocumento);
				manten_usuario.setTipoDocumentoDesc(tipoDocIdDescripcion);
										
						if (cuentaVisible==true)		
							listaMantLimFecha.add(manten_usuario);
				}
			}

			if (listaMantLimFecha.size()==0)		
				throw new ValidacionException("No se han encontrando resultados para su busqueda","");								
			
			if (listaMantLimFecha!=null && listaMantLimFecha.size()>0)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMantUsuario", listaMantLimFecha);			

			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					response.setStyle("muestraAO");
			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					response.setStyle("muestraAJ");
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
					response.setStyle("muestraAG");
			if (usuario.getPerfilId() == PERFIL_CAJERO)
					response.setStyle("muestraCA");
			
			/**** AGREGADO JBUGARIN 05/01/2007 DESCAJ INICIO ****/
				String sRefrescaAuto3 = request.getParameter("chkRefrescaAuto");
				req.setAttribute("sRefrescaAuto",sRefrescaAuto3);
				
				req.setAttribute("valorRB",request.getParameter("radio"));
				//para no perder el texto de busqueda del form manteOrg.jsp
				req.setAttribute("buscarTiempo",tiempo);
		    /**** JBUGARIN 05/01/2007 DESCAJ FIN ****/	
			
			
		}
		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION", e);
			try {
				this.transition("muestra", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} 
		}		
		 catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
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
