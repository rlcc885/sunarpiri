package gob.pe.sunarp.extranet.prepago.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.NonHandleableException;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.administracion.bean.DatosUsuarioBean;
import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.administracion.bean.MantenUsuarioBean;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.prepago.bean.AbonoBean;
import gob.pe.sunarp.extranet.prepago.bean.AbonoVentanillaBean;
import gob.pe.sunarp.extranet.prepago.bean.BancoBean;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.util.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class AbonoVentanillaController extends ControllerExtension implements Constantes{
	private String thisClass = AbonoVentanillaController.class.getName() + ".";



	public AbonoVentanillaController() {
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("mantenimientoUsuario", "Estado que se encarga de direccionar al estado respectivo segun criterio de seleccion"));

		addState(new State("usuarioDirecta", "Ventana de Busq. x Usuario-Directa."));
		addState(new State("usuarioApeNom", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("usuarioDocId", "Ventana de Busq. x Documento de Identidad."));
		addState(new State("usuarioOrg", "Ventana de Busq. x Organizacion."));
		addState(new State("muestraVentanilla", "Ventana de Busq. x Organizacion."));
		addState(new State("resultadoAbonoVentanilla", "Ventana de Busq. x Organizacion."));
		setInitialState("muestra");
	}


	public String getTitle() {
		return new String("AbonoVentanillaController");
	}
	
	private void muestraDocsId(ControllerRequest request,DBConnection dconn) throws DBException
	{
		
		DboTmDocIden doc_iden = new DboTmDocIden(dconn);
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_TIPO_DOC_ID);
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
		
		doc_iden.setField(DboTmDocIden.CAMPO_ESTADO,"1");
			
		ArrayList listaDocsIds = doc_iden.searchAndRetrieveList();
				
		java.util.List listaDocsId = new ArrayList();
				
		for(int i = 0; i < listaDocsIds.size(); i++)
		{
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
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
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
		  if(estInicialCaja.equals("1") && usuario.getPerfilId()==Constantes.PERFIL_CAJERO){
				  req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. Debe aperturar su caja para poder ingresar a la opción.");
				  response.setStyle("ok");
		  }
		  else if(estInicialCaja.equals("0") && usuario.getPerfilId()==Constantes.PERFIL_CAJERO){
				  req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. El tesorero no ha realizado su apertura general.");
				  response.setStyle("ok");
		  }
		  else{
			muestraDocsId(request, dconn);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("prima", "x");
			response.setStyle("muestra");
		  }
			ExpressoHttpSessionBean.getSession(request).setAttribute("destinoPrepago", "Ventanilla.do");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
				pool.release(conn);
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
			switch(opcion){
				case 1: transition("usuarioDirecta", request, response); break;
				case 2: transition("usuarioOrg", request, response); break;
				case 3: transition("usuarioApeNom", request, response); break;
				case 4: transition("usuarioDocId", request, response); break;
			}
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		}catch(NonHandleableException nhe){
			log(Errors.EC_GENERIC_ERROR, "NonHandleableException AbonoVentanillaController, estado: mantenimientoUsuario.", nhe, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
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
HttpServletRequest req = null; //agregado jbugarin descaj 08/01/07
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			String usuarioId = request.getParameter("userId");
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			req = ExpressoHttpSessionBean.getRequest(request);//agregado jbugarin descaj 08/01/07
			
			if (usuarioId == null || usuarioId.trim().length() <= 0)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Debe ingresar una cuenta de usuario", "errorPrepago");
			
			muestraDocsId(request, dconn);
			
			DboCuenta cuenta = new DboCuenta(dconn);
			cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
			cuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);
			
			if (cuenta.find()){
				String cuentaId = cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
				
				AbonoVentanillaBean bean = new AbonoVentanillaBean();
				
				MultiDBObject multi = new MultiDBObject(dconn);
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
	
				multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
				multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
				multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
				multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
				multi.setField("cuenta", DboCuenta.CAMPO_CUENTA_ID, cuentaId);
				multi.setField("cuenta", DboCuenta.CAMPO_ESTADO, "1"); //18Sept
				multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");
				multi.setField("linprepag", DboLineaPrepago.CAMPO_ESTADO, "1");//19Sept
				multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO, "1");
				
				java.util.Vector elementos = multi.searchAndRetrieve();
				
				if(elementos.size() == 1){
				
					MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
					bean.setUsuarioId(usuarioId);
					bean.setNombre(oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
					bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
					bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
					bean.setAfil_organiz(null);
					bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
				
					java.util.List lista = new java.util.ArrayList();
					lista.add(bean);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", lista);
				}else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", null);
			}else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", null);
			
			/***** AGREGADO JBUGARIN 08/01/2007 INICIO DESCAJ *****/
						String sRefrescaAuto = request.getParameter("chkRefrescaAuto");
						req.setAttribute("sRefrescaAuto",sRefrescaAuto);
						req.setAttribute("valorRB",request.getParameter("radio"));
						//para no perder el texto de busqueda del form manteOrg.jsp
						req.setAttribute("buscarUsuarioId",usuarioId);
			/***** JBUGARIN 08/01/2007 FIN DESCAJ *****/	
			response.setStyle("muestra");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
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
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);			
			
			String nom = request.getParameter("nombres");
			String ap = request.getParameter("apepat");
			String am = request.getParameter("apemat");
			
			if( (nom == null || nom.trim().length() <= 0) && (ap == null || ap.trim().length() <= 0) )
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar algún valor ya sea en el campo Nombre o Apellido Paterno", "errorPrepago");
			
			muestraDocsId(request, dconn);
			
			StringBuffer nombres = null;
			StringBuffer apepat = null;
			StringBuffer apemat = null;
			
			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
			
			multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);

			if(ap != null && ap.trim().length() > 0){
				apepat = new StringBuffer("%").append(ap.trim()).append("%");
				multi.setField("penatu", DboPeNatu.CAMPO_APE_PAT, apepat.toString());
			}
			if(nom != null && nom.trim().length() > 0){
				nombres = new StringBuffer("%").append(nom.trim()).append("%");
				multi.setField("penatu", DboPeNatu.CAMPO_NOMBRES, nombres.toString());
			}
			if(am != null && am.trim().length() > 0){
				apemat = new StringBuffer("%").append(am.trim()).append("%");
				multi.setField("penatu", DboPeNatu.CAMPO_APE_MAT, apemat.toString());
			}
			multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");//Cambiado
			multi.setField("cuenta", DboCuenta.CAMPO_ESTADO, "1"); //18Sept
			multi.setField("linprepag", DboLineaPrepago.CAMPO_ESTADO, "1");//19Sept
			multi.setField("tipodoc",DboTmDocIden.CAMPO_ESTADO,"1");
										

			java.util.List lista = new java.util.ArrayList();
			AbonoVentanillaBean bean = null;
			java.util.List l = multi.searchAndRetrieve();
			
			if(l.size() > 0){
				for(Iterator i = l.iterator(); i.hasNext();){
					MultiDBObject oneMulti = (MultiDBObject) i.next();
					
					bean = new AbonoVentanillaBean();
					
					bean.setUsuarioId(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
					bean.setNombre(oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
					bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
					bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
					//bean.setNombre(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
					bean.setAfil_organiz(null);
					bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
					lista.add(bean);
				}
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", lista);
			}else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", null);
			
			/**** AGREGADO JBUGARIN 08/07/2007 INICIO DESCAJ ****/
			String sRefrescaAuto3 = request.getParameter("chkRefrescaAuto");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("sRefrescaAuto",sRefrescaAuto3);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("valorRB",request.getParameter("radio"));
			//para no perder el texto de busqueda del form manteOrg.jsp
			ExpressoHttpSessionBean.getRequest(request).setAttribute("buscarApellidoPaterno",ap);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("buscarApellidoMaterno",am);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("buscarNombre",nom);
			/**** JBUGARIN 08/07/2007	FIN DESCAJ ****/	
			response.setStyle("muestra");
		}catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
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
		
		try{
			init(request);
			validarSesion(request);
			
			String numopt = request.getParameter("numopt");
			String numdoc = request.getParameter("numdoc");

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			muestraDocsId(request, dconn);
			
			if (numdoc == null || numdoc.trim().length() <= 0)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Debe ingresar un número de Documento.", "errorPrepago");
				
			MultiDBObject multi = new MultiDBObject(dconn);

			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");

			multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			
			multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");
			multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, numdoc);
			multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, numopt);//chequear
			multi.setField("persona", DboPersona.CAMPO_TPO_PERS, "N");
			multi.setField("cuenta", DboCuenta.CAMPO_ESTADO, "1"); //18Sept
			multi.setField("linprepag", DboLineaPrepago.CAMPO_ESTADO, "1");//19Sept
			multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO,"1");
				

			java.util.List lista = new java.util.ArrayList();
			
			AbonoVentanillaBean bean = null;
			
			boolean encontro = false;
			
			for(Iterator i = multi.searchAndRetrieve().iterator(); i.hasNext();)
			{
				MultiDBObject oneMulti = (MultiDBObject) i.next();
				
				bean = new AbonoVentanillaBean();

				bean.setUsuarioId(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
				bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
				bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
				bean.setNombre(oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
				bean.setAfil_organiz(null);
				bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
				lista.add(bean);
				encontro = true;
			}
			
			if(!encontro){//propio
			multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");

			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_PE_JURI_ID, "pejuri", DboPeJuri.CAMPO_PE_JURI_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			
			//multi.setField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV, "RUC");
			
			multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, numdoc);
			multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, numopt);

			multi.setField("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "is null");//cb
			multi.setField("pejuri", DboPeJuri.CAMPO_TIPO_ORG, "0");
			multi.setField("persona", DboPersona.CAMPO_TPO_PERS, "J");
			multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO, "1");


			
			for(Iterator i = multi.searchAndRetrieve().iterator(); i.hasNext();)
			{
				MultiDBObject oneMulti = (MultiDBObject) i.next();
				
				bean = new AbonoVentanillaBean();

				//bean.setUsuarioId(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
				bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
				bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
				bean.setNombre(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
				bean.setAfil_organiz(null);
				bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
				lista.add(bean);
				encontro = true;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("organi", "X");
			}
			}			
			if(encontro)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", lista);
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", null);
			
			/**** AGREGADO JBUGARIN 08/01/2007 DESCAJ INICIO ****/
			String sRefrescaAuto5 = request.getParameter("chkRefrescaAuto");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("sRefrescaAuto",sRefrescaAuto5);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("valorRB",request.getParameter("radio"));
			//para no perder el texto de busqueda del form manteUsuarioCA.jsp
			ExpressoHttpSessionBean.getRequest(request).setAttribute("buscarTipoDocu",numopt);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("buscarNuDocu",numdoc);
			/**** JBUGARIN 08/01/2007 DESCAJ FIN ****/
				
			response.setStyle("muestra");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
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
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			String numopt = request.getParameter("numopt");
			String numdoc = request.getParameter("numdoc");
			String razsoc = request.getParameter("razsoc");
			String ruc = request.getParameter("ruc");
			String nombreRuc = request.getParameter("nombreRUC"); //esto es para que el como se llama el campo del RUC sea igual a la base de datos

			muestraDocsId(request, dconn);
			
			boolean paseRuc = true;
			boolean paseRS = true;
			
			if (razsoc == null || razsoc.trim().length() <= 0)
				paseRS = false;

			if(ruc == null || ruc.trim().length() <= 0)
				paseRuc = false;
			
			if (!paseRuc && !paseRS)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Debe ingrear algun valor ya sea en el campo Razon Social o RUC.", "errorPrepago");


			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PE_JURI_ID, "linprepag", DboLineaPrepago.CAMPO_PE_JURI_ID);
			multi.setField("pejuri", DboPeJuri.CAMPO_TIPO_ORG, "0");
			multi.setField("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "is null");//cb
			multi.setField("linprepag", DboLineaPrepago.CAMPO_ESTADO, "1");//19Sept
			multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO, "1");

			if(paseRuc){
				multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, ruc);
				multi.setField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV, nombreRuc);
			}
			
			if(paseRS)
				multi.setAppendWhereClause(" PE_JURI." + DboPeJuri.CAMPO_RAZ_SOC + " LIKE '" + razsoc + "%'");
				//multi.setField("pejuri", DboPeJuri.CAMPO_RAZ_SOC, razsoc);

			java.util.List lista = new java.util.ArrayList();
			AbonoVentanillaBean bean = null;
				
			java.util.List l = multi.searchAndRetrieve();
				
			if(l.size() > 0)
				{
				for(Iterator i = l.iterator(); i.hasNext();){
					MultiDBObject oneMulti = (MultiDBObject) i.next();
						
					bean = new AbonoVentanillaBean();
					bean.setUsuarioId(null);
					bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
					bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
					bean.setNombre(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
					bean.setAfil_organiz(null);
					bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
					lista.add(bean);
				}
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", lista);
			}else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", null);

			ExpressoHttpSessionBean.getRequest(request).setAttribute("organi", "X");
			
			/**** AGREGADO JBUGARIN DESCAJ 08/01/07 ****/
			String sRefrescaAuto2 = request.getParameter("chkRefrescaAuto");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("sRefrescaAuto",sRefrescaAuto2);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("valorRB",request.getParameter("radio"));
			//para no perder el texto de busqueda del form AbonoVentanilla.jsp
			ExpressoHttpSessionBean.getRequest(request).setAttribute("buscarRuc",ruc);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("buscarRazSoc",razsoc);				
		   /**** FIN JBUGARIN DESCAJ 08/01/07 ****/
			
			response.setStyle("muestra");
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
//**********************************************************************************

	protected ControllerResponse runMuestraVentanillaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		UsuarioBean usuario    = ExpressoHttpSessionBean.getUsuarioBean(request);
		ExpressoHttpSessionBean.getSession(request).removeAttribute("comprobante");
				
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			String lin = request.getParameter("lineaPrepago");
			
			if(lin == null || lin.trim().length() <= 0)
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No existe Línea Prepago para dicho Usuario u Organización", "errorPrepago");

			DboLineaPrepago linea = new DboLineaPrepago(dconn);
			linea.setFieldsToRetrieve(DboLineaPrepago.CAMPO_CUENTA_ID + "|" + DboLineaPrepago.CAMPO_PE_JURI_ID + "|" + DboLineaPrepago.CAMPO_ESTADO);
			linea.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, lin);
			if (linea.find()==false)
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Línea Prepago no encontrada", "errorPrepago");
			/** JBUGARIN OBSERVACIONES SUNARP 21/02/2007 **/
			//if(linea.getField(DboLineaPrepago.CAMPO_ESTADO).equals("0"))
			//	throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Línea Prepago inactiva", "errorPrepago");
			/** FIN OBSERVACIONES**/
			String cuentaId = linea.getField(DboLineaPrepago.CAMPO_CUENTA_ID);
			String pejuriId = linea.getField(DboLineaPrepago.CAMPO_PE_JURI_ID);
			
			boolean empresa=false;
			
			DboContrato contrato = new DboContrato(dconn);
			if (pejuriId!=null && pejuriId.trim().length()>0)
				{
				empresa=true;
				contrato.setField(DboContrato.CAMPO_PE_JURI_ID, pejuriId);			
				}
			if (cuentaId!=null && cuentaId.trim().length()>0)
				contrato.setField(DboContrato.CAMPO_CUENTA_ID, cuentaId);	
						
			contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
			contrato.find();
			
			String userId = null;
			String nomEntidad = null;
			String numcontrato = null;

			if(empresa)
			{
				DboPeJuri pejur = new DboPeJuri(dconn);
				pejur.setFieldsToRetrieve(DboPeJuri.CAMPO_PREF_CTA + "|" + DboPeJuri.CAMPO_RAZ_SOC);
				pejur.setField(DboPeJuri.CAMPO_PE_JURI_ID, pejuriId);
				pejur.find();
				userId = pejur.getField(DboPeJuri.CAMPO_PREF_CTA) + "001";
				nomEntidad = pejur.getField(DboPeJuri.CAMPO_RAZ_SOC);
			} 
			else 
			{
				DboCuenta cuenta = new DboCuenta(dconn);
				cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID + "|" + DboCuenta.CAMPO_PE_NATU_ID);
				cuenta.setField(DboCuenta.CAMPO_CUENTA_ID, cuentaId);
				cuenta.find();
				userId = cuenta.getField(DboCuenta.CAMPO_USR_ID);

				DboPeNatu pe = new DboPeNatu(dconn);
				pe.setFieldsToRetrieve(DboPeNatu.CAMPO_NOMBRES + "|" + DboPeNatu.CAMPO_APE_PAT + "|" + DboPeNatu.CAMPO_APE_MAT);
				pe.setField(DboPeNatu.CAMPO_PE_NATU_ID, cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
				pe.find();
				nomEntidad = pe.getField(DboPeNatu.CAMPO_APE_PAT) + " " + pe.getField(DboPeNatu.CAMPO_APE_MAT) + " " + pe.getField(DboPeNatu.CAMPO_NOMBRES);
			}
			
			ComprobanteBean compBean = new ComprobanteBean();
			compBean.setUserId(userId);
			compBean.setCajero(usuario.getUserId());
			//compBean.setOficina("Lima");
			compBean.setNombreEntidad(nomEntidad);
			compBean.setContratoId(contrato.getField(DboContrato.CAMPO_CONTRATO_ID));

			req.setAttribute("comprobante", compBean);
			req.setAttribute("linea", lin);
			
			DboTmBanco bancoI = new DboTmBanco(dconn);
			bancoI.setField(DboTmBanco.CAMPO_ESTADO, "1");
			bancoI.setFieldsToRetrieve(DboTmBanco.CAMPO_BANCO_ID + "|" + DboTmBanco.CAMPO_NOMBRE);
			ArrayList listaBancos = bancoI.searchAndRetrieveList();
			bancoI.clearFieldsToRetrieve();
			
			java.util.List listaBanco = new ArrayList();
					
			for(int i = 0; i < listaBancos.size(); i++)
			{
				DboTmBanco banco = (DboTmBanco) listaBancos.get(i);
					
				BancoBean bean = new BancoBean();
				bean.setId(banco.getField(DboTmBanco.CAMPO_BANCO_ID));
				bean.setDescripcion(banco.getField(DboTmBanco.CAMPO_NOMBRE));
				listaBanco.add(bean);
			}
				
			req.setAttribute("listaBancos", listaBanco);
			
			/**** AGREGADO JBUGARIN DESCAJ 08/01/07 ****/
			DatosUsuarioBean datosUsuarioBean = Tarea.getDatosUsuario(dconn, userId);
			Boolean flagActivo = new Boolean(datosUsuarioBean.getFlagActivo());
			String estadoLineaPrepago = null;
			req.setAttribute("flagActivo",flagActivo);
			req.setAttribute("userId",usuario.getUserId());
			req.setAttribute("usuarioUpdate",userId);
			/** AGREGADO JBUGARIN OBSERVACIONES SUNARP 21/02/2007 **/
			DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(dconn);
			dboLineaPrepago.setFieldsToRetrieve(DboLineaPrepago.CAMPO_ESTADO);
			dboLineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID,datosUsuarioBean.getCuentaId());
			if(dboLineaPrepago.find()==true){
				estadoLineaPrepago=dboLineaPrepago.getField(DboLineaPrepago.CAMPO_ESTADO);
			}
			req.setAttribute("estadoLineaPrepago",estadoLineaPrepago);
			req.setAttribute("cuentaIdActualizar",datosUsuarioBean.getCuentaId());
			/** FIN OBSERVACIONES SUNARP **/
			/**** FIN JBUGARIN DESCAJ 08/01/07 ****/
			response.setStyle("ventanilla");
			
		}catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
				pool.release(conn);
			end(request);
		}
		return response;
	}


    public ControllerResponse runResultadoAbonoVentanillaState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException {    

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			UsuarioBean usuarioBean = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			
			if(ExpressoHttpSessionBean.getSession(request).getAttribute("comprobante") == null)
			{
				/**
				String usuario = request.getParameter("usuario");
				String nombre = request.getParameter("nombre");
				String contratoId = request.getParameter("contratoId");
				String monto_bruto = request.getParameter("monto");
						
				String lineaPrePago = request.getParameter("linea");
				
				boolean paso1 = true;
				StringTokenizer stk = null;
				String tipoPago = null;
				String aux_TipPag = null;
				
				try{
					stk = new StringTokenizer(request.getParameter("tipopago"), "|");
					tipoPago = stk.nextToken();
					paso1 = false;
					aux_TipPag = stk.nextToken();
					paso1 = true;
				}catch(Exception e1){
					if(paso1)
						throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el tipo de pago.", "errorPrepago");
					else
						throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el nombre del tipo de pago.", "errorPrepago");
				}
	
				boolean esCheque = false;
							
				if(tipoPago.equals("C"))
					esCheque = true;
				
				
				String bancoId = null;
				String aux_Banco = null;
					
				if(esCheque){	
					try{			
				        stk = new StringTokenizer(request.getParameter("bancoId"), "|");
				        bancoId = stk.nextToken();
				        paso1 = false;
						aux_Banco = stk.nextToken();
						paso1 = true;
					}catch(Exception e2){
						if(paso1)
							throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el id. del banco.", "errorPrepago");
						else
							throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el nombre del banco.", "errorPrepago");
					}
				}
				
				String tipoCheque = null;
				String aux_TipCheque = null;
					
				if(esCheque){
					try{
						stk = new StringTokenizer(request.getParameter("tipocheque"), "|");
						tipoCheque = stk.nextToken();
						paso1 = false;
						aux_TipCheque = stk.nextToken();
					}catch(Exception e3){
						if(paso1)
							throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener el tipo de cheque.", "errorPrepago");
						else
							throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener la descripcion del cheque.", "errorPrepago");
					}
				}
				
				String numCheque  = null;
				
				if(esCheque){
					if(request.getParameter("numcheque") == null || request.getParameter("numcheque").trim().length() <= 0)
						throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar el número de cheque.", "errorPrepago");
						
					numCheque  = request.getParameter("numcheque");
				}
					PrepagoBean prepagoBean = new PrepagoBean();
					**/
	/*Monto bruto*/	/**prepagoBean.setMontoBruto(Double.parseDouble(monto_bruto)); // Monto ing. poir el cliente
					**/
					/*Medio Id*/	//prepagoBean.setMedioId(medioId);
					
	/*Linea Prepago Id*/
					/**prepagoBean.setLineaPrepagoId(lineaPrePago);
					 **/ 
	/*Ventanilla*/	/**	prepagoBean.setFlag_ventan(true);
					**/
	
					//if(tipoPago.equals("E")){
	/*EFECTIVO*/	//	prepagoBean.setFlag_efectivo(true);
	/*NO VA*/		//	prepagoBean.setBancoId(null);
	/*NO VA*/		//	prepagoBean.setTipoCheque(null);
	/*NO VA*/		//	prepagoBean.setNumCheuqe(null);
					//}else{
	/*CHEQUE*/		//	prepagoBean.setFlag_efectivo(false);
	/*Banco Id*/	//	prepagoBean.setBancoId(bancoId);
	/*Tipo Cheque*/	//	prepagoBean.setTipoCheque(tipoCheque);
	/*Num Cheque*/	//	prepagoBean.setNumCheuqe(numCheque);
					//}
	
	/*Usuario*/		/**prepagoBean.setUsuario(usuario);
	
					// Incrementa Saldo del cliente				
					LineaPrepago lineaCmd = new LineaPrepago();
					ComprobanteBean beancomp = lineaCmd.incrementaSaldo(usuarioBean, prepagoBean, dconn);
					
					beancomp.setContratoId(contratoId);
					beancomp.setTipoPago(aux_TipPag);
					
					beancomp.setCajero(usuarioBean.getUserId());
					
					StringBuffer q = new StringBuffer();
					q.append("SELECT nombre FROM ofic_registral WHERE reg_pub_id= '").append(usuarioBean.getRegPublicoId()).append("' AND ofic_reg_id='").append(usuarioBean.getOficRegistralId()).append("'");
					Statement stmt   = conn.createStatement();
					ResultSet rset   = stmt.executeQuery(q.toString());
					if (isTrace(this)) System.out.println("Oficina Reg QUERY ---> "+q.toString());
					rset.next();
					
					beancomp.setOficina(rset.getString("nombre"));
					rset.close();
					stmt.close();
					
					if(tipoPago.equals("E")){
						beancomp.setTipoCheque(null);
						beancomp.setBanco(null);
						beancomp.setNumcheque(null);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("cheq", null);					
					}else{
						beancomp.setTipoCheque(aux_TipCheque);
						beancomp.setBanco(aux_Banco);
						//beancomp.setNumcheque(aux_TipPag);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("cheq", "x");					
					}
					
					double nuevo_saldo = lineaCmd.getSaldoActual(lineaPrePago, dconn);
					conn.commit();
					//Seteo la variable de Sesion
					usuarioBean.setSaldo(nuevo_saldo);
					
					ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", beancomp);
					//ExpressoHttpSessionBean.getRequest(request).setAttribute("comprobante", beancomp);
					**/
					
					/**** AGREGADO JBUGARIN DESCAJ 09/01/07 ****/
					String flagActivo = (String)request.getParameter("flgActivo");
					String userUpdate = request.getParameter("userUpdate");
					/** JBUGARIN OBSERVACIONES SUNARP 21/02/2007 **/
					String estadoLineaPrepago = request.getParameter("estadoLineaPrepago");
					String cuentaIdActualizar = request.getParameter("cuentaIdActualizar");
					String lineaPrepagoId = null;
					/******/
					if ( ((flagActivo!=null) && (flagActivo.equals("false"))) || ((estadoLineaPrepago!=null) && (estadoLineaPrepago.equals("0")))) {
						DboCuenta dboCuenta = new DboCuenta(dconn);
						
						dboCuenta.setFieldsToUpdate(DboCuenta.CAMPO_ESTADO );
						dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaIdActualizar);
						dboCuenta.setField(DboCuenta.CAMPO_ESTADO,"1");
						dboCuenta.update();
						/** JBUGARIN OBSERVACIONES SUNARP 21/02/2007 **/
						DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(dconn);
						dboLineaPrepago.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
						dboLineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID,cuentaIdActualizar);
					if(dboLineaPrepago.find()==true){
						lineaPrepagoId=dboLineaPrepago.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
					}
					dboLineaPrepago.clear();
					dboLineaPrepago.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
					dboLineaPrepago.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID,lineaPrepagoId);
					dboLineaPrepago.setField(DboLineaPrepago.CAMPO_ESTADO,"1");
					dboLineaPrepago.update();
					/******/
					}
					/**** FIN JBUGARIN DESCAJ 09/01/07 ****/
									
					Abono ab = new Abono();
					AbonoBean bAbo = new AbonoBean();
					bAbo.setUsuario(request.getParameter("usuario"));
					bAbo.setNombre(request.getParameter("nombre"));
					bAbo.setContratoId(request.getParameter("contratoId"));
					bAbo.setMonto_bruto(request.getParameter("monto"));
					bAbo.setLineaPrePago(request.getParameter("linea"));
					bAbo.setTipoPago(request.getParameter("tipopago"));
					bAbo.setBancoId(request.getParameter("bancoId"));
					bAbo.setTipoCheque(request.getParameter("tipocheque"));
					bAbo.setNumCheque(request.getParameter("numCheque"));
					ab.setAbono(bAbo);
					
					ComprobanteBean beancomp = ab.efectuaAbono(conn,usuarioBean);
					LineaPrepago lineaCmd = new LineaPrepago();
					double nuevo_saldo = lineaCmd.getSaldoActual(ab.getAbono().getLineaPrePago(), dconn);
					if(beancomp.getTipoPago().equals("E"))
					{
						ExpressoHttpSessionBean.getRequest(request).setAttribute("cheq", null);
					}
					else
					{
						ExpressoHttpSessionBean.getRequest(request).setAttribute("cheq", "x");
					}
					conn.commit();
					//Seteo la variable de Sesion
					usuarioBean.setSaldo(nuevo_saldo);
		
					ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", beancomp);

				}
				ExpressoHttpSessionBean.getSession(request).setAttribute("Usuario", usuarioBean);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagsiguiente", ExpressoHttpSessionBean.getSession(request).getAttribute("destinoPrepago"));
				response.setStyle("comprobante");
		}catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	
	
}

