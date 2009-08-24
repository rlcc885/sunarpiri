package gob.pe.sunarp.extranet.afiliacion.controller;
/*
*Controller Afiliacion
*@vesion 1
*/

//paquetes del sistema
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.dbobj.DBObject;
import com.jcorporate.expresso.core.misc.*;
//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.framework.session.*;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaAfiliacionBean;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.afiliacion.bean.*;
import gob.pe.sunarp.extranet.transaction.*;

import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class AfiliacionController
	extends ControllerExtension
	implements Constantes {
	private String thisClass = AfiliacionController.class.getName() + ".";
	public AfiliacionController() {
		super();
		//-afiliacion persona
		addState(new State("solicitarFormulario", "c"));
		addState(new State("mostrarContrato", "c"));
		addState(new State("aceptarContrato", "c"));
		addState(new State("mostrarOpcionPago", "c"));
		addState(new State("regresar", "c"));
		//-afiliacion Organizacion
		addState(new State("solicitarFormularioOrg", "c"));
		addState(new State("mostrarContratoOrg", "c"));
		addState(new State("aceptarContratoOrg", "c"));
		addState(new State("mostrarOpcionPagoOrg", "c"));
		addState(new State("regresarOrg", "c"));
		//-
		setInitialState("solicitarFormulario");
	}

	public ControllerResponse runSolicitarFormularioState(
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
DBConnection dconn = new DBConnection(conn);

			//---ComboBox
			java.util.ArrayList arreglo3 = new java.util.ArrayList(); //departamento
			java.util.ArrayList arreglo4 = new java.util.ArrayList(); //oficina registral

			//departamento
			//-HTV-28agosto-Se elimina combo Oficina Registral
			// y se pone combo Provincia			
			DboTmProvincia dboProvincia = new DboTmProvincia(dconn);

			DboTmDepartamento dbo3 = new DboTmDepartamento(dconn);
			dbo3.setField(DboTmDepartamento.CAMPO_PAIS_ID, "01");
			dbo3.setField(DboTmDepartamento.CAMPO_ESTADO, "1");
			java.util.ArrayList arr3 =	dbo3.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
			java.util.ArrayList arrpro = new java.util.ArrayList();
			
			for (int i = 0; i < arr3.size(); i++) 
			{
				ComboBean b = new ComboBean();
				DboTmDepartamento d = (DboTmDepartamento) arr3.get(i);
				b.setCodigo(d.getField(DboTmDepartamento.CAMPO_DPTO_ID));
				b.setDescripcion(d.getField(DboTmDepartamento.CAMPO_NOMBRE));
				arreglo3.add(b);

				//buscar las provincias del departamento
				dboProvincia.clearAll();
				dboProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, b.getCodigo());
				dboProvincia.setField(DboTmProvincia.CAMPO_ESTADO, "1");
				java.util.ArrayList arrpv =
					dboProvincia.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
				for (int w = 0; w < arrpv.size(); w++) {
					DboTmProvincia dp = (DboTmProvincia) arrpv.get(w);
					Value05Bean b5 = new Value05Bean();
					b5.setValue01(dp.getField(DboTmProvincia.CAMPO_PROV_ID));
					b5.setValue02(dp.getField(DboTmProvincia.CAMPO_NOMBRE));
					b5.setValue03(b.getCodigo());
					arrpro.add(b5);
				}
			}

			req.setAttribute("arr1", Tarea.getComboTiposDocumento(dconn));
			req.setAttribute("arr2", Tarea.getComboPaises(dconn));
			req.setAttribute("arr3", arreglo3);
			req.setAttribute("arr5", Tarea.getComboPreguntasSecretas(dconn));
			//arreglo hijo provincia
			req.setAttribute("arr_hijo1", arrpro);

			response.setStyle("formularioAfiliacion");
		} 
		catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
				pool.release(conn);
			end(request);
		}
		
		return response;
	}
	public ControllerResponse runMostrarContratoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DatosUsuarioBean bean1 = new DatosUsuarioBean();
		
		try {

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			//recojer
			bean1.setApellidoPaterno(req.getParameter("txt1").toUpperCase());
			bean1.setApellidoMaterno(req.getParameter("txt2").toUpperCase());
			bean1.setNombre(req.getParameter("txt3").toUpperCase());
			bean1.setTipoDocumento(req.getParameter("combo1"));
			bean1.setNumeroDocumento(req.getParameter("txt4").toUpperCase());
			bean1.setPais(req.getParameter("combo2"));
			//hphp 200302-03
			bean1.setDepartamento(req.getParameter("combo3"));
			bean1.setProvinciaId(req.getParameter("combo4"));
			//
			
			if(req.getParameter("txt5")!=null)
				bean1.setOtro(req.getParameter("txt5").toUpperCase());

		//8nov2002 - Zona
		Zona zona = new Zona();
		zona.setConn(dconn);
		//NO HAY USUARIO EN SESION zona.setUsuario();
		zona.setPaisId(bean1.getPais());
		zona.setDepartamentoId(bean1.getDepartamento());
		zona.setProvinciaId(bean1.getProvinciaId());
		//zona.setDepartamentoId(req.getParameter("combo3"));
		//zona.setProvinciaId(req.getParameter("combo4"));
		zona.calculaZona();

		bean1.setJuris(zona.getJurisId());
		bean1.setRegPubId(zona.getRegPubId());
		bean1.setOficina(zona.getOficRegId());
		
			bean1.setDistrito(req.getParameter("txt7"));
			bean1.setCalle(req.getParameter("txt8").toUpperCase());
			bean1.setCodigoPostal(req.getParameter("txt9").toUpperCase());
			bean1.setTelefono(req.getParameter("txt10"));
			bean1.setFax(req.getParameter("txt11"));
			bean1.setEmail(req.getParameter("txt12"));
			bean1.setUsuario(req.getParameter("txt13").toUpperCase());
			bean1.setPassword(req.getParameter("txt14").toUpperCase());
			bean1.setConfirmacionPassword(req.getParameter("txt15").toUpperCase());
			bean1.setPreguntaSecreta(req.getParameter("combo5").toUpperCase());
			bean1.setRespuestaSecreta(req.getParameter("txt16").toUpperCase());
			if (req.getParameter("chk1") != null)
				bean1.setRecibirMail(true);

			//-----validar cuenta
			DboCuenta dbo1 = new DboCuenta(dconn);
			dbo1.setField(DboCuenta.CAMPO_USR_ID, bean1.getUsuario());
			if (dbo1.find() == true)
					throw new ValidacionException("Ya existe alguien afiliado con ese Usuario, por favor ingrese uno diferente","txt13");
	
			//tipo documento
			DboTmDocIden dbo3 = new DboTmDocIden(dconn);
			dbo3.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, bean1.getTipoDocumento());
			dbo3.setField(DboTmDocIden.CAMPO_ESTADO, "1");
			if (dbo3.find() == true)
					bean1.setDescripcionTipoDocumento(
						dbo3.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
	
			//version contrato
			DboVerContrato dbo4 = new DboVerContrato(dconn);
			java.util.ArrayList arr4 = dbo4.searchAndRetrieveList(DboVerContrato.CAMPO_VER_CONTRATO + " Desc");
			if ((arr4 != null) && (arr4.size() > 0)) 
			{
				DboVerContrato dvc = (DboVerContrato) arr4.get(0);
				bean1.setContratoVersionId(dvc.getField(DboVerContrato.CAMPO_VER_CONTRATO_ID));
				bean1.setContratoVersion(dvc.getField(DboVerContrato.CAMPO_VER_CONTRATO));
			}			


			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);
			sesion.removeAttribute("DATOS_CONTRATO");
			sesion.setAttribute("DATOS_CONTRATO", bean1);

			//-datos para el pie del contrato
			/*
			req.setAttribute("usr_id", bean1.getUsuario());
			req.setAttribute("usr_nombre",bean1.getApellidoPaterno().trim()
					+ " "
					+ bean1.getApellidoMaterno().trim()
					+ " "
					+ bean1.getNombre());
			req.setAttribute("tipo_id", bean1.getDescripcionTipoDocumento());
			req.setAttribute("num_id", bean1.getNumeroDocumento());
			req.setAttribute("fecha_hora_sistema",gob.pe.sunarp.extranet.util.FechaUtil.getCurrentDateTime());
			req.setAttribute("num_contrato", "&nbsp;");
			req.setAttribute("version_contrato", bean1.getVersionContrato());
			*/

			req.setAttribute("flag", "0");

			response.setStyle("mostrarContrato");
		} 

		catch (ValidacionException e) 
		{
				rollback(conn, request);
				req.setAttribute("VALIDACION_EXCEPTION",e);
				req.setAttribute("DATOS_FORMULARIO", bean1);
				try {
					this.transition("solicitarFormulario", request, response);
				} 
				catch (Throwable ex) 
				{
					log(Errors.EC_GENERIC_ERROR, "", ex, request);
					rollback(conn, request);
					response.setStyle("error");
				}
		} 
		/*
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}*/
		 catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
						pool.release(conn);
			end(request);
		}
		
		return response;
	} //fin mostrar contrato

public ControllerResponse runRegresarState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DatosUsuarioBean bean1 = new DatosUsuarioBean();
		
		try {
			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);
			bean1 = (DatosUsuarioBean) sesion.getAttribute("DATOS_CONTRATO");

			req.setAttribute("DATOS_FORMULARIO", bean1);
			this.transition("solicitarFormulario", request, response);
		}

		catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		
		return response;
	} //fin metodo
	
	
public ControllerResponse runRegresarOrgState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DatosOrganizacionBean bean1 = new DatosOrganizacionBean();
		
		try {
			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);
			bean1 = (DatosOrganizacionBean) sesion.getAttribute("DATOS_CONTRATO_ORG");

			req.setAttribute("DATOS_FORMULARIO", bean1);
			this.transition("solicitarFormularioOrg", request, response);
		} 

		catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		
		return response;
	} //fin metodo	

	public ControllerResponse runAceptarContratoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
		{
			
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);
			DatosUsuarioBean bean1 =
				(DatosUsuarioBean) sesion.getAttribute("DATOS_CONTRATO");

			//----
					//insertar registro en tabla PERSONA
		DboPersona dbo1 = new DboPersona(dconn);
		dbo1.setField(DboPersona.CAMPO_NUM_DOC_IDEN, bean1.getNumeroDocumento());
		dbo1.setField(DboPersona.CAMPO_TPO_PERS, "N");
		dbo1.setField(DboPersona.CAMPO_FAX, bean1.getFax());
		dbo1.setField(DboPersona.CAMPO_EMAIL, bean1.getEmail());
		dbo1.setField(DboPersona.CAMPO_TELEF, bean1.getTelefono());
		dbo1.setField(DboPersona.CAMPO_TIPO_DOC_ID, bean1.getTipoDocumento());
		dbo1.setField(DboPersona.CAMPO_REG_PUB_ID, bean1.getRegPubId());
		if (bean1.getJuris().trim().length() == 0)
			dbo1.setField(DboPersona.CAMPO_JURIS_ID, null);
		else
			dbo1.setField(DboPersona.CAMPO_JURIS_ID, bean1.getJuris());
			
			//dbo1.setField(DboPersona.CAMPO_JURIS_ID, "2");
			
		dbo1.add();
		/**
		 * SVASQUEZ - AVATAR GLOBAL
		 */
		//String personaId = "" + dbo1.max(DboPersona.CAMPO_PERSONA_ID);
		String personaId = "" + dbo1.getMax(DboPersona.CAMPO_PERSONA_ID);

		//insertar registro peNatu
		DboPeNatu dbo2 = new DboPeNatu(dconn);
		dbo2.setField(DboPeNatu.CAMPO_NOMBRES, bean1.getNombre());
		dbo2.setField(DboPeNatu.CAMPO_APE_PAT, bean1.getApellidoPaterno());
		dbo2.setField(DboPeNatu.CAMPO_APE_MAT, bean1.getApellidoMaterno());
		dbo2.setField(DboPeNatu.CAMPO_PE_JURI_ID, null);
		dbo2.setField(DboPeNatu.CAMPO_PERSONA_ID, personaId);
		dbo2.add();
		String peNatuId = "" + dbo2.getMax(DboPeNatu.CAMPO_PE_NATU_ID);

		//insertar registro direccion
		DboDireccion dbo3 = new DboDireccion(dconn);
		dbo3.setField(DboDireccion.CAMPO_PERSONA_ID, personaId);
		if (bean1.getPais().equals("01") == true)
			dbo3.setField(DboDireccion.CAMPO_LUG_EXT, " ");
		else
			dbo3.setField(DboDireccion.CAMPO_LUG_EXT, bean1.getOtro());
		dbo3.setField(DboDireccion.CAMPO_NOM_NUM_VIA, bean1.getCalle());
		dbo3.setField(DboDireccion.CAMPO_COD_POST, bean1.getCodigoPostal());
		if (bean1.getPais().equals("01") == true) {
			dbo3.setField(DboDireccion.CAMPO_NO_DIST, bean1.getDistrito());
			dbo3.setField(DboDireccion.CAMPO_PROV_ID, bean1.getProvinciaId());
			dbo3.setField(DboDireccion.CAMPO_DPTO_ID, bean1.getDepartamento());
		} else {
			dbo3.setField(DboDireccion.CAMPO_NO_DIST, "  ");
			dbo3.setField(DboDireccion.CAMPO_PROV_ID, " ");
			dbo3.setField(DboDireccion.CAMPO_DPTO_ID, " ");
		}
		dbo3.setField(DboDireccion.CAMPO_PAIS_ID, bean1.getPais());
		dbo3.add();

		//insertar cuenta
		DboCuenta dbo4 = new DboCuenta(dconn);
		dbo4.setField(DboCuenta.CAMPO_USR_ID, bean1.getUsuario());
		dbo4.setField(DboCuenta.CAMPO_TIPO_USR, "1100");
		dbo4.setField(DboCuenta.CAMPO_EXON_PAGO, "0");
		dbo4.setField(DboCuenta.CAMPO_FG_NEW_USR_VENT, "0");
		dbo4.setField(DboCuenta.CAMPO_TS_ULT_ACC,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
		if (bean1.getRecibirMail() == true)
			dbo4.setField(DboCuenta.CAMPO_FG_REC_MAIL, "1");
		else
			dbo4.setField(DboCuenta.CAMPO_FG_REC_MAIL, "0");
		dbo4.setField(DboCuenta.CAMPO_RESP_SECRETA, bean1.getRespuestaSecreta());
		/* el password no se graba en base de datos
		dbo3.setField(DboCuenta.CAMPO_CLAVE,bean1.getPassword());
		*/
		if (Propiedades.getInstance().getFlagGrabaClave()==true)
			dbo4.setField(DboCuenta.CAMPO_CLAVE, bean1.getPassword());
		else
			dbo4.setField(DboCuenta.CAMPO_CLAVE, '*');
		dbo4.setField(DboCuenta.CAMPO_TS_CREA,FechaUtil.stringTimeToOracleString(FechaUtil.getCurrentDateTime()));
		dbo4.setField(DboCuenta.CAMPO_PREG_SEC_ID, bean1.getPreguntaSecreta());
		dbo4.setField(DboCuenta.CAMPO_USR_CREA, bean1.getUsuario());
		dbo4.setField(DboCuenta.CAMPO_PE_NATU_ID, peNatuId);
		dbo4.setField(DboCuenta.CAMPO_ESTADO, "1");
		dbo4.add();
		String cuentaId = "" + dbo4.getMax(DboCuenta.CAMPO_CUENTA_ID);
		bean1.setCuentaId(cuentaId);

		//obtener reg_pub_id de la oficina
		/*28agosto-h-el reg pub id se obtuvo de la provincia
		DboOficRegistral dboo = new DboOficRegistral(conn);
		dboo.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,bean1.getOficina());
		dboo.find();
		String reg_pub_id = dboo.getField(DboOficRegistral.CAMPO_REG_PUB_ID);
		*/

		//cuenta_juris
		DboCuentaJuris dbo5 = new DboCuentaJuris(dconn);
		dbo5.setField(DboCuentaJuris.CAMPO_CUENTA_ID, cuentaId);
		dbo5.setField(DboCuentaJuris.CAMPO_JURIS_ID, bean1.getJuris());
		//dbo5.setField(DboCuentaJuris.CAMPO_JURIS_ID, "2");
		dbo5.setField(DboCuentaJuris.CAMPO_OFIC_REG_ID, bean1.getOficina());
		dbo5.setField(DboCuentaJuris.CAMPO_PERSONA_ID, personaId);
		dbo5.setField(DboCuentaJuris.CAMPO_REG_PUB_ID, bean1.getRegPubId());
		dbo5.add();

		//contrato
		DboContrato dbo6 = new DboContrato(dconn);
		dbo6.setField(DboContrato.CAMPO_FEC_CREA,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
		dbo6.setField(DboContrato.CAMPO_CUENTA_ID, cuentaId);
		dbo6.setField(DboContrato.CAMPO_ESTADO, "1");
		dbo6.setField(DboContrato.CAMPO_TS_ULT_MODIF,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
		dbo6.setField(DboContrato.CAMPO_TS_CREA,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
		dbo6.setField(DboContrato.CAMPO_USR_ULT_MODIF, " ");
		dbo6.setField(DboContrato.CAMPO_USR_CREA, " ");
		dbo6.setField(DboContrato.CAMPO_VER_CONTRATO_ID, bean1.getContratoVersionId());
//INSERTA MANUEL
//		dbo6.setField(DboContrato.CAMPO_PE_JURI_ID, bean1.getJuris());
		dbo6.setField(DboContrato.CAMPO_PE_JURI_ID, null);
//INSERTA		

		dbo6.add();
		String contratoId = "" + dbo6.getMax(DboContrato.CAMPO_CONTRATO_ID);
		bean1.setContratoNumero(contratoId);

		//linea prepago
		DboLineaPrepago dbo7 = new DboLineaPrepago(dconn);
		dbo7.setField(DboLineaPrepago.CAMPO_SALDO, 0);
		dbo7.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuentaId);
		dbo7.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
		dbo7.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF, bean1.getUsuario());
		dbo7.setField(DboLineaPrepago.CAMPO_USR_CREA, bean1.getUsuario());
		dbo7.setField(
			DboLineaPrepago.CAMPO_TS_ULT_MODIF,
			FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
		dbo7.setField(
			DboLineaPrepago.CAMPO_TS_CREA,
			FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
//INSERTA MANUEL			
//		dbo7.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, bean1.getJuris());
		dbo7.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, null);		
//INSERTA 		
		dbo7.setField(DboLineaPrepago.CAMPO_FG_DEPOSITO, "0");
		dbo7.add();
		//		addField("LINEA_PREPAGO_ID","auto-inc", 0, false, "null");
		
		
		bean1.setLineaPrePagoId(dbo7.getMax(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));

		//29agost
		//se agrega el perfil 20 por default al usuario recien creado
		DboPerfilCuenta dboPerfil = new DboPerfilCuenta(dconn);
		dboPerfil.setField(DboPerfilCuenta.CAMPO_CUENTA_ID, cuentaId);
		dboPerfil.setField(DboPerfilCuenta.CAMPO_PERFIL_ID, "20");
		dboPerfil.setField(DboPerfilCuenta.CAMPO_ESTADO, "1");
		dboPerfil.setField(DboPerfilCuenta.CAMPO_USR_ULT_MODIF, bean1.getUsuario());
		dboPerfil.setField(DboPerfilCuenta.CAMPO_USR_CREA, bean1.getUsuario());
		dboPerfil.setField(DboPerfilCuenta.CAMPO_TS_ULT_MODIF, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
		dboPerfil.setField(DboPerfilCuenta.CAMPO_TS_CREA, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
		dboPerfil.setField(DboPerfilCuenta.CAMPO_NIVEL_ACCESO_ID, "0");
		dboPerfil.add();
			//----

			//-datos para el pie del contrato
			req.setAttribute("usr_id", bean1.getUsuario());
			req.setAttribute("usr_nombre",
				bean1.getApellidoPaterno().trim()
					+ " "
					+ bean1.getApellidoMaterno().trim()
					+ " "
					+ bean1.getNombre());
			req.setAttribute("tipo_id", bean1.getDescripcionTipoDocumento());
			req.setAttribute("num_id", bean1.getNumeroDocumento());
			req.setAttribute("fecha_hora_sistema",gob.pe.sunarp.extranet.util.FechaUtil.getCurrentDateTime());
			req.setAttribute("num_contrato", bean1.getContratoNumero());
			req.setAttribute("version_contrato", bean1.getContratoVersion());

			req.setAttribute("flag", "1");

			//4sep agregar usuario en TAM
			//insertar usuario en TAM
			SecAdmin secAdmin = SecAdmin.getInstance();
			secAdmin.adicionarUsuario(
				bean1.getUsuario(),
				bean1.getNombre(),
				bean1.getApellidoPaterno(),
				"usr",
				bean1.getPassword());
			secAdmin.adicionarUsuarioAGrupo(dconn, bean1.getUsuario(), "20");


//Creando un UsuarioBean simulado
			UsuarioBean userSimulado = new UsuarioBean();
			userSimulado.setFgInterno(false);
			userSimulado.setUserId(bean1.getUsuario());
			userSimulado.setRegPublicoId(bean1.getRegPubId());
			userSimulado.setOficRegistralId(bean1.getOficina());
			userSimulado.setCuentaId(bean1.getCuentaId());
			userSimulado.setFgAdmin(false);
			userSimulado.setFgIndividual(true);
			userSimulado.setCodOrg(null);
			userSimulado.setExonPago(true);	//Porque es Afiliacion se 
									//pone true, debido a que 
									//no se le puede cobrar 
									//por no estar logeado.
					
					
//Inicio de la Transaccion
		LogAuditoriaAfiliacionBean logbean = new LogAuditoriaAfiliacionBean();
					
		logbean.setCodigoServicio(TipoServicio.AFILIACION_EXTRANET);
		logbean.setUsuarioSession(userSimulado);
                
                //Modificado por: Proyecto Filtros de Acceso
                //Fecha: 02/10/2006
                //logbean.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
                logbean.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
                //Fecha: 08/10/2006             
                logbean.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
                //Fin Modificación
					
		logbean.setTipoAfil("I");
		logbean.setRegPubId(bean1.getRegPubId());
		logbean.setOficRegId(bean1.getOficina());
		logbean.setFgWeb("1");
		logbean.setNumCont(bean1.getContratoNumero());
		logbean.setUserId(bean1.getUsuario());
		logbean.setPersonaId(personaId);
				
				/*
					Job004 j = new Job004();
					j.setBean(logbean);
					Thread llamador1 = new Thread(j);
					llamador1.start();				
			*/
			//if (Propiedades.getInstance().getFlagProduccion()==false)
				Transaction.getInstance().registraTransaccion(logbean,conn);
					
			response.setStyle("mostrarContrato");
			conn.commit();
		} //try

		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
					pool.release(conn);
			end(request);
		}
		
		return response;
	} //fin aceptar contrato

	public ControllerResponse runMostrarOpcionPagoState(
	 	//ya no va a la opcion de pago debido a que no esta logueado todavia, ahora va a la pagina principal
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try {

			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);
			DatosUsuarioBean bean1 =
				(DatosUsuarioBean) sesion.getAttribute("DATOS_CONTRATO");

			req.setAttribute("usr", bean1.getUsuario());
			
			sesion.removeAttribute("DATOS_CONTRATO");
			
			response.setStyle("mostrarConfirmacion");
		} //try

		catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		
		return response;
	} //fin mostrar opcion pago
	
	
	public ControllerResponse runMostrarOpcionPagoOrgState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try {

			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);
			DatosOrganizacionBean bean1 =
				(DatosOrganizacionBean) sesion.getAttribute("DATOS_CONTRATO_ORG");

			req.setAttribute("usuario", bean1.getUsuarioId());
			req.setAttribute("organizacion", bean1.getOrgRazon());
			
			response.setStyle("mostrarConfirmacionOrg");			
		} //try

		catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}
		return response;
	} //fin mostrar opcion pago

	//******************************************************************************************
	//******************************************************************************************
	//******************************************************************************************
	//******************************************************************************************
	//******************************************************************************************

	public ControllerResponse runSolicitarFormularioOrgState(
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
DBConnection dconn = new DBConnection(conn);
			//-datos para combo box

			ComboBean bean1 = null;
			//combo giro
			DboTmGiro dbo0 = new DboTmGiro(dconn);
			dbo0.setField(DboTmGiro.CAMPO_ESTADO, "1");
			java.util.ArrayList arr0 = dbo0.searchAndRetrieveList(DboTmGiro.CAMPO_NOMBRE);
			java.util.ArrayList arreglo0 = new java.util.ArrayList();
			for (int i = 0; i < arr0.size(); i++) {

				bean1 = new ComboBean();
				bean1.setCodigo(((DboTmGiro) arr0.get(i)).getField(DboTmGiro.CAMPO_GIRO_ID));
				bean1.setDescripcion(((DboTmGiro) arr0.get(i)).getField(DboTmGiro.CAMPO_NOMBRE));
				arreglo0.add(bean1);
			}

			//pais
			DboTmPais dbo1 = new DboTmPais(dconn);
			dbo1.setField(DboTmPais.CAMPO_ESTADO, "1");
			java.util.ArrayList arr1 = dbo1.searchAndRetrieveList(DboTmPais.CAMPO_NOMBRE);
			java.util.ArrayList arreglo1 = new java.util.ArrayList();
			for (int i = 0; i < arr1.size(); i++) {
				bean1 = new ComboBean();

				bean1.setCodigo(((DboTmPais) arr1.get(i)).getField(DboTmPais.CAMPO_PAIS_ID));
				bean1.setDescripcion(((DboTmPais) arr1.get(i)).getField(DboTmPais.CAMPO_NOMBRE));
				arreglo1.add(bean1);
			}

			//departamento y provincia
			DboTmProvincia dboProvincia = new DboTmProvincia(dconn);
			DboTmDepartamento dbo3 = new DboTmDepartamento(dconn);
			dbo3.setField(DboTmDepartamento.CAMPO_PAIS_ID, "01");
			dbo3.setField(DboTmDepartamento.CAMPO_ESTADO, "1");
			java.util.ArrayList arr3 = dbo3.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
			java.util.ArrayList arreglo3 = new java.util.ArrayList();
			java.util.ArrayList arrpro = new java.util.ArrayList();
			
			for (int i = 0; i < arr3.size(); i++) 
			{
				bean1 = new ComboBean();
				DboTmDepartamento d = (DboTmDepartamento) arr3.get(i);
				bean1.setCodigo(d.getField(DboTmDepartamento.CAMPO_DPTO_ID));
				bean1.setDescripcion(d.getField(DboTmDepartamento.CAMPO_NOMBRE));
				arreglo3.add(bean1);

				//buscar las provincias del departamento
				dboProvincia.clearAll();
				dboProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, bean1.getCodigo());
				java.util.ArrayList arrpv = dboProvincia.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
				for (int w = 0; w < arrpv.size(); w++) {
					DboTmProvincia dp = (DboTmProvincia) arrpv.get(w);
					Value05Bean b5 = new Value05Bean();
					b5.setValue01(dp.getField(DboTmProvincia.CAMPO_PROV_ID));
					b5.setValue02(dp.getField(DboTmProvincia.CAMPO_NOMBRE));
					b5.setValue03(bean1.getCodigo());
					arrpro.add(b5);
				}
			}

			//tipo documento
			DboTmDocIden dbo4 = new DboTmDocIden(dconn);
			dbo4.setCustomWhereClause(
				DboTmDocIden.CAMPO_ESTADO
					+ "= '1' and("
					+ DboTmDocIden.CAMPO_TIPO_PER
					+ "='N' or "
					+ DboTmDocIden.CAMPO_TIPO_PER
					+ "='A')");
			java.util.ArrayList arr4 = dbo4.searchAndRetrieveList();
			java.util.ArrayList arreglo4 = new java.util.ArrayList();

			for (int i = 0; i < arr4.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(((DboTmDocIden) arr4.get(i)).getField(DboTmDocIden.CAMPO_TIPO_DOC_ID));
				bean1.setDescripcion(((DboTmDocIden) arr4.get(i)).getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
				arreglo4.add(bean1);
			}

			//pregunta secreta
			DboTmPregSecretas dbo5 = new DboTmPregSecretas(dconn);
			dbo5.setField(DboTmPregSecretas.CAMPO_ESTADO, "1");
			java.util.ArrayList arr5 = dbo5.searchAndRetrieveList();
			java.util.ArrayList arreglo5 = new java.util.ArrayList();
			for (int i = 0; i < arr5.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(((DboTmPregSecretas) arr5.get(i)).getField(DboTmPregSecretas.CAMPO_PREG_SEC_ID));
				bean1.setDescripcion(((DboTmPregSecretas) arr5.get(i)).getField(DboTmPregSecretas.CAMPO_DESCRIPCION));
				arreglo5.add(bean1);
			}

			//-enviar arreglos
			req.setAttribute("arr0", arreglo0); //giro
			req.setAttribute("arr1", arreglo1); //pais
			req.setAttribute("arr3", arreglo3); //departamento
			req.setAttribute("arrpro", arrpro); //provincia
			req.setAttribute("arr4", arreglo4); //tipo documento
			req.setAttribute("arr5", arreglo5); //tipo pregunta secreta

			response.setStyle("formularioAfiliacionOrg");
		} //try
		catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
				pool.release(conn);
			end(request);
		}
		
		return response;
	}

	public ControllerResponse runMostrarContratoOrgState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
		
		DatosOrganizacionBean bean1 = new DatosOrganizacionBean();

		try {
			init(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			//-
			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);

			//validar Contrato
			bean1.setAdmPrefijo(req.getParameter("admPrefijo"));
			bean1.setUsuarioId(bean1.getAdmPrefijo() + "001");
			
			//recoger datos organizacion			
			bean1.setOrgRazon(req.getParameter("orgRazon").toUpperCase());
			bean1.setOrgRuc(req.getParameter("orgRuc"));
			bean1.setOrgGiro(req.getParameter("orgGiro"));
			bean1.setOrgPais(req.getParameter("orgPais"));
			if (req.getParameter("orgLug")!=null)
				bean1.setOrgLug(req.getParameter("orgLug"));
			if (req.getParameter("orgDpto")!=null)
				bean1.setOrgDpto(req.getParameter("orgDpto"));
			String xpro = req.getParameter("orgProvincia");
			if (xpro == null)
				bean1.setOrgProvincia(" ");
			else
				bean1.setOrgProvincia(xpro);
			bean1.setOrgDistrito(req.getParameter("orgDistrito"));
			bean1.setOrgVia(req.getParameter("orgVia"));
			bean1.setOrgTelefono(req.getParameter("orgTelefono"));
			bean1.setOrgFax(req.getParameter("orgFax"));
			bean1.setOrgEmail(req.getParameter("orgEmail"));
			//recoger datos representante
			bean1.setRepApePat(req.getParameter("repApePat").toUpperCase());
			bean1.setRepApeMat(req.getParameter("repApeMat").toUpperCase());
			bean1.setRepNombre(req.getParameter("repNombre").toUpperCase());
			bean1.setRepTipoDocumento(req.getParameter("repTipoDocumento"));
			bean1.setRepNumeroDocumento(req.getParameter("repNumeroDocumento"));
			//recoger datos administrador
			bean1.setAdmApePat(req.getParameter("admApePat").toUpperCase());
			bean1.setAdmApeMat(req.getParameter("admApeMat").toUpperCase());
			bean1.setAdmNombre(req.getParameter("admNombre").toUpperCase());
			bean1.setAdmEmail(req.getParameter("admEmail"));
			bean1.setAdmTipoDocumento(req.getParameter("admTipoDocumento"));
			bean1.setAdmNumeroDocumento(req.getParameter("admNumeroDocumento"));
			bean1.setAdmPais(req.getParameter("admPais"));
			if (req.getParameter("admDpto")!=null)
				bean1.setAdmDpto(req.getParameter("admDpto"));
			if (req.getParameter("admOtro")!=null)
				bean1.setAdmOtro(req.getParameter("admOtro"));
			String xpro2 = req.getParameter("admProvincia");
			if (xpro2 == null)
				bean1.setAdmProvincia(" ");
			else
				bean1.setAdmProvincia(xpro2);
			bean1.setAdmDistrito(req.getParameter("admDistrito"));
			bean1.setAdmVia(req.getParameter("admVia"));
			bean1.setAdmCodPostal(req.getParameter("admCodPostal"));
			bean1.setAdmTelefono(req.getParameter("admTelefono"));
			bean1.setAdmFax(req.getParameter("admFax"));
			
			bean1.setAdmPassword(req.getParameter("password"));
			bean1.setAdmConfirmaPassword(req.getParameter("admConfirmaPassword"));
			bean1.setAdmTipoPregunta(req.getParameter("admTipoPregunta"));
			bean1.setAdmRespuesta(req.getParameter("admRespuesta"));
			if (req.getParameter("admQuiero") != null)
				bean1.setAdmQuiero(true);

			//-buscar juris y regpub para ORGANIZACION
			bean1.setOrgJuris("00");
			bean1.setOrgRegPubId("00");
			
		//8nov2002 - Zona
		Zona zona = new Zona();
		zona.setConn(dconn);
		//NO HAY USUARIO EN SESION zona.setUsuario();
		zona.setPaisId(bean1.getOrgPais());
		zona.setDepartamentoId(bean1.getOrgDpto());
		zona.setProvinciaId(bean1.getOrgProvincia());
		zona.calculaZona();
		bean1.setOrgJuris(zona.getJurisId());
		bean1.setOrgRegPubId(zona.getRegPubId());
		bean1.setOrgOficinaId(zona.getOficRegId());			
			
			DboCuenta dbo1 = new DboCuenta(dconn);
			dbo1.setField(DboCuenta.CAMPO_USR_ID, bean1.getUsuarioId());
			if (dbo1.find() == true)
				throw new ValidacionException("Prefijo de Organizacion ya existe, por favor ingrese otro","admPrefijo");
							
			//-buscar juris y regpub para ADMINISTRADOR
			bean1.setAdmJuris(Constantes.ID_JURISDICCION_FUERA_DEL_PERU);
			bean1.setAdmRegPubId(Constantes.ID_REGISTRO_PUBLICO_FUERA_DEL_PERU);
			bean1.setAdmOficinaId(Constantes.ID_OFICINA_FUERA_DEL_PERU);
			
			if (bean1.getAdmPais().equals("01") == true) {
				//peru
				DboTmProvincia dboProvincia = new DboTmProvincia(dconn);
				dboProvincia.setField(DboTmProvincia.CAMPO_PAIS_ID, bean1.getAdmPais());
				dboProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, bean1.getAdmDpto());
				dboProvincia.setField(DboTmProvincia.CAMPO_PROV_ID, bean1.getAdmProvincia());
				if (dboProvincia.find() == true) {
					bean1.setAdmRegPubId(dboProvincia.getField(DboTmProvincia.CAMPO_REG_PUB_ID));
					bean1.setAdmOficinaId(dboProvincia.getField(DboTmProvincia.CAMPO_OFIC_REG_ID));
					DboOficRegistral dboOficinaRegistral = new DboOficRegistral(dconn);
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,bean1.getAdmOficinaId());
					dboOficinaRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,bean1.getAdmRegPubId());
					if (dboOficinaRegistral.find() == true)
						bean1.setAdmJuris(dboOficinaRegistral.getField(DboOficRegistral.CAMPO_JURIS_ID));

				}
			}

			//version contrato
			DboVerContrato dboVerContrato = new DboVerContrato(dconn);
			java.util.ArrayList arrvc = dboVerContrato.searchAndRetrieveList(DboVerContrato.CAMPO_VER_CONTRATO + " Desc");
			if ((arrvc != null) && (arrvc.size() > 0))
				{
				bean1.setContratoVersion(((DboVerContrato) arrvc.get(0)).getField(DboVerContrato.CAMPO_VER_CONTRATO));
				bean1.setContratoVersionId(((DboVerContrato) arrvc.get(0)).getField(DboVerContrato.CAMPO_VER_CONTRATO_ID));
				}

			//descripcion tipo documento REP
			DboTmDocIden dboTipoDocumento = new DboTmDocIden(dconn);
			dboTipoDocumento.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,bean1.getRepTipoDocumento());
			dboTipoDocumento.setField(DboTmDocIden.CAMPO_ESTADO,"1");
			if (dboTipoDocumento.find() == true)
				bean1.setRepDescripcionTipoDocumento(dboTipoDocumento.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));

			//descripcion tipo documento ADM
			dboTipoDocumento.clearAll();
			dboTipoDocumento.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,bean1.getAdmTipoDocumento());
			dboTipoDocumento.setField(DboTmDocIden.CAMPO_ESTADO,"1");
			if (dboTipoDocumento.find() == true)
				bean1.setAdmDescripcionTipoDocumento(dboTipoDocumento.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));

			sesion.setAttribute("DATOS_CONTRATO_ORG", bean1);
			
			//-datos para el pie del contrato
			req.setAttribute("flag", "0");			
			
			response.setStyle("mostrarContratoOrg");
			//conn.commit();
			
		} //try
		
		catch (ValidacionException e) 
		{
				rollback(conn, request);
				req.setAttribute("VALIDACION_EXCEPTION",e);
				req.setAttribute("DATOS_FORMULARIO", bean1);
				try {
					this.transition("solicitarFormularioOrg", request, response);
				} 
				catch (Throwable ex) 
				{
					log(Errors.EC_GENERIC_ERROR, "", ex, request);
					rollback(conn, request);
					response.setStyle("error");
				}
		} 		
		/*
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}*/
		 catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
					pool.release(conn);
			end(request);
		}

		
		return response;
	} //fin mostrarContratoOrg

	public ControllerResponse runAceptarContratoOrgState(
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
			DBConnection dconn = new DBConnection(conn);
			//-
			HttpSession sesion = ExpressoHttpSessionBean.getSession(request);

			DatosOrganizacionBean bean1 =
				(DatosOrganizacionBean) sesion.getAttribute("DATOS_CONTRATO_ORG");
			//-buscar juris y regpub

			//insertar organizacion
			DboPersona dboPersona = new DboPersona(dconn);
			dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN, bean1.getOrgRuc());
			dboPersona.setField(DboPersona.CAMPO_TPO_PERS, "J");
			dboPersona.setField(DboPersona.CAMPO_FAX, bean1.getOrgFax());
			dboPersona.setField(DboPersona.CAMPO_EMAIL, bean1.getOrgEmail());
			dboPersona.setField(DboPersona.CAMPO_TELEF, bean1.getOrgTelefono());
			dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, TIPO_DOCUMENTO_RUC);
			if (bean1.getOrgJuris().trim().length() == 0)
				dboPersona.setField(DboPersona.CAMPO_JURIS_ID, null);
			else
				dboPersona.setField(DboPersona.CAMPO_JURIS_ID, bean1.getOrgJuris());
			dboPersona.setField(DboPersona.CAMPO_REG_PUB_ID, bean1.getOrgRegPubId());
			dboPersona.add();
			//addField("PERSONA_ID","auto-inc", 0, false, "null");
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 */
			//String personaId = "" + dboPersona.max(DboPersona.CAMPO_PERSONA_ID);
			String personaId = "" + dboPersona.getMax(DboPersona.CAMPO_PERSONA_ID);

			//insetar persona juridica
			DboPeJuri dboPeJuri = new DboPeJuri(dconn);
			dboPeJuri.setField(DboPeJuri.CAMPO_RAZ_SOC, bean1.getOrgRazon());
			dboPeJuri.setField(DboPeJuri.CAMPO_SIGLAS, bean1.getOrgRazon().substring(0, 1));
			dboPeJuri.setField(DboPeJuri.CAMPO_PREF_CTA, bean1.getAdmPrefijo());
			dboPeJuri.setField(DboPeJuri.CAMPO_TIPO_ORG, "0");
			dboPeJuri.setField(DboPeJuri.CAMPO_PERSONA_ID, personaId);
			dboPeJuri.setField(DboPeJuri.CAMPO_NU_USRS, "1");
			dboPeJuri.setField(DboPeJuri.CAMPO_JURIS_ID, bean1.getOrgJuris());
			dboPeJuri.setField(DboPeJuri.CAMPO_GIRO_ID, bean1.getOrgGiro());
			dboPeJuri.setField(DboPeJuri.CAMPO_TS_ULT_ACC, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			//3setp2002H - se agrega campo represent
			dboPeJuri.setField(dboPeJuri.CAMPO_REPRES_ID, null);
			//4sep2002-HT- se pone null y se hace update abajo

			dboPeJuri.add();
			//addField("PE_JURI_ID","auto-inc", 0, false, "null");
			//INSERTADO MANUEL
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 */
			//String peJuriId = "" + dboPeJuri.max(DboPeJuri.CAMPO_PE_JURI_ID);
			String peJuriId = "" + dboPeJuri.getMax(DboPeJuri.CAMPO_PE_JURI_ID);			

			//INSERTADO			
			//insertar direccion
			DboDireccion dboDireccion = new DboDireccion(dconn);
			dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, personaId);
			dboDireccion.setField(DboDireccion.CAMPO_PAIS_ID, bean1.getOrgPais());
			dboDireccion.setField(DboDireccion.CAMPO_DPTO_ID, bean1.getOrgDpto());
			dboDireccion.setField(DboDireccion.CAMPO_PROV_ID, bean1.getOrgProvincia());
			dboDireccion.setField(DboDireccion.CAMPO_LUG_EXT, bean1.getOrgLug());
			dboDireccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA, bean1.getOrgVia());
			dboDireccion.setField(DboDireccion.CAMPO_COD_POST, " ");
			dboDireccion.setField(DboDireccion.CAMPO_NO_DIST, bean1.getOrgDistrito());
			dboDireccion.add();

			//insertar datos representante legal
			dboPersona.clearAll();
			dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN,bean1.getRepNumeroDocumento());
			dboPersona.setField(DboPersona.CAMPO_TPO_PERS, "N");
			dboPersona.setField(DboPersona.CAMPO_FAX, bean1.getOrgFax());
			dboPersona.setField(DboPersona.CAMPO_EMAIL, bean1.getOrgEmail());
			dboPersona.setField(DboPersona.CAMPO_TELEF, bean1.getOrgTelefono());
			dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, bean1.getRepTipoDocumento());
			dboPersona.setField(DboPersona.CAMPO_JURIS_ID, bean1.getOrgJuris());
			dboPersona.setField(DboPersona.CAMPO_REG_PUB_ID, bean1.getOrgRegPubId());
			dboPersona.add();
			/**
			 * SVASQUEZ - AVATAR GLOBAL 
			 */
			//String personaRepId = "" + dboPersona.max(DboPersona.CAMPO_PERSONA_ID);
			String personaRepId = "" + dboPersona.getMax(DboPersona.CAMPO_PERSONA_ID);

			//persona natural - representante legal
			DboPeNatu dboPeNatu = new DboPeNatu(dconn);
			dboPeNatu.setField(DboPeNatu.CAMPO_APE_PAT, bean1.getRepApePat());
			dboPeNatu.setField(DboPeNatu.CAMPO_APE_MAT, bean1.getRepApeMat());
			dboPeNatu.setField(DboPeNatu.CAMPO_NOMBRES, bean1.getRepNombre());

			//INSERTA MANUEL			
			//dboPeNatu.setField(DboPeNatu.CAMPO_PE_JURI_ID, bean1.getOrgJuris());
			dboPeNatu.setField(DboPeNatu.CAMPO_PE_JURI_ID, peJuriId);
			//INSERTA			
			dboPeNatu.setField(DboPeNatu.CAMPO_PERSONA_ID, personaRepId);
			dboPeNatu.add();
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 */
			//String personaNatuRepId = "" + dboPeNatu.max(DboPeNatu.CAMPO_PE_NATU_ID);
			String personaNatuRepId = "" + dboPeNatu.getMax(DboPeNatu.CAMPO_PE_NATU_ID);
			//addField("PE_NATU_ID","auto-inc", 0, false, "null");

			//_4sep se actualiza PEJURI con personaNatuRepId
Statement stmt = conn.createStatement();
StringBuffer zb=new StringBuffer();
zb.append("update pe_juri set repres_id=");
zb.append(personaNatuRepId);
zb.append(" where pe_juri_id =").append(peJuriId);
if (isTrace(this)) System.out.println("__sqlupdate__ "+zb.toString());
stmt.execute(zb.toString());
			//_

			//insertar datos del administrador
			dboPersona.clearAll();
			dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN,bean1.getAdmNumeroDocumento());
			dboPersona.setField(DboPersona.CAMPO_TPO_PERS, "N");
			dboPersona.setField(DboPersona.CAMPO_FAX, bean1.getAdmFax());
			dboPersona.setField(DboPersona.CAMPO_EMAIL, bean1.getAdmEmail());
			dboPersona.setField(DboPersona.CAMPO_TELEF, bean1.getAdmTelefono());
			dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, bean1.getAdmTipoDocumento());
			dboPersona.setField(DboPersona.CAMPO_JURIS_ID, bean1.getAdmJuris());
			dboPersona.setField(DboPersona.CAMPO_REG_PUB_ID, bean1.getAdmRegPubId());
			dboPersona.add();
			
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 */
			//String personaAdmId = "" + dboPersona.max(DboPersona.CAMPO_PERSONA_ID);
			String personaAdmId = "" + dboPersona.getMax(DboPersona.CAMPO_PERSONA_ID);

			dboPeNatu.clearAll();
			dboPeNatu.setField(DboPeNatu.CAMPO_APE_PAT, bean1.getAdmApePat());
			dboPeNatu.setField(DboPeNatu.CAMPO_APE_MAT, bean1.getAdmApeMat());
			dboPeNatu.setField(DboPeNatu.CAMPO_NOMBRES, bean1.getAdmNombre());
			//INSERTA MANUEL			
			//			dboPeNatu.setField(DboPeNatu.CAMPO_PE_JURI_ID, bean1.getAdmJuris());
			dboPeNatu.setField(DboPeNatu.CAMPO_PE_JURI_ID, peJuriId);
			//INSERTA			
			dboPeNatu.setField(DboPeNatu.CAMPO_PERSONA_ID, personaAdmId);
			dboPeNatu.add();
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 */
			//String personaNatuAdmId = "" + dboPeNatu.max(DboPeNatu.CAMPO_PE_NATU_ID);
			String personaNatuAdmId = "" + dboPeNatu.getMax(DboPeNatu.CAMPO_PE_NATU_ID);

			dboDireccion.clearAll();
			dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, personaAdmId);
			dboDireccion.setField(DboDireccion.CAMPO_PAIS_ID, bean1.getAdmPais());
			dboDireccion.setField(DboDireccion.CAMPO_DPTO_ID, bean1.getAdmDpto());
			dboDireccion.setField(DboDireccion.CAMPO_PROV_ID, bean1.getAdmProvincia());
			dboDireccion.setField(DboDireccion.CAMPO_LUG_EXT, bean1.getAdmOtro());
			dboDireccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA, bean1.getAdmVia());
			dboDireccion.setField(DboDireccion.CAMPO_COD_POST, bean1.getAdmCodPostal());
			dboDireccion.setField(DboDireccion.CAMPO_NO_DIST, bean1.getAdmDistrito());
			dboDireccion.add();

			DboCuenta dboCuenta = new DboCuenta(dconn);
			dboCuenta.setField(DboCuenta.CAMPO_USR_ID, bean1.getUsuarioId());
			dboCuenta.setField(DboCuenta.CAMPO_TIPO_USR, "1010");
			dboCuenta.setField(DboCuenta.CAMPO_EXON_PAGO, "0");
			dboCuenta.setField(DboCuenta.CAMPO_FG_NEW_USR_VENT, "0");
			dboCuenta.setField(DboCuenta.CAMPO_TS_ULT_ACC,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			if (bean1.getAdmQuiero() == true)
					dboCuenta.setField(DboCuenta.CAMPO_FG_REC_MAIL, "1");
			else
					dboCuenta.setField(DboCuenta.CAMPO_FG_REC_MAIL, "0");
			dboCuenta.setField(DboCuenta.CAMPO_RESP_SECRETA, bean1.getAdmRespuesta());
			dboCuenta.setField(DboCuenta.CAMPO_CLAVE, bean1.getAdmPassword());
			dboCuenta.setField(DboCuenta.CAMPO_TS_CREA,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dboCuenta.setField(DboCuenta.CAMPO_PREG_SEC_ID, bean1.getAdmTipoPregunta());
			dboCuenta.setField(DboCuenta.CAMPO_USR_CREA, bean1.getUsuarioId());
			dboCuenta.setField(DboCuenta.CAMPO_PE_NATU_ID, personaNatuAdmId);
			dboCuenta.setField(DboCuenta.CAMPO_ESTADO, "1");
			dboCuenta.setField(DboCuenta.CAMPO_FG_SYNC_TAM, "1");
			dboCuenta.add();
			//addField("CUENTA_ID","auto-inc", 0, false, "null");
			//String cuentaId = "" + dboCuenta.max(DboCuenta.CAMPO_CUENTA_ID);
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 */
			String cuentaId = dboCuenta.getMax(DboCuenta.CAMPO_CUENTA_ID); 
			System.out.println("DboCuenta - cuentaId::"+cuentaId);
			//se agrega el perfil 40 por default al usuario recien creado
			DboPerfilCuenta dboPerfil = new DboPerfilCuenta(dconn);
			dboPerfil.setField(DboPerfilCuenta.CAMPO_CUENTA_ID, cuentaId);
			dboPerfil.setField(DboPerfilCuenta.CAMPO_PERFIL_ID, "40");
			dboPerfil.setField(DboPerfilCuenta.CAMPO_ESTADO, "1");
			dboPerfil.setField(DboPerfilCuenta.CAMPO_USR_ULT_MODIF, bean1.getUsuarioId());
			dboPerfil.setField(DboPerfilCuenta.CAMPO_USR_CREA, bean1.getUsuarioId());
			dboPerfil.setField(DboPerfilCuenta.CAMPO_TS_ULT_MODIF,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dboPerfil.setField(DboPerfilCuenta.CAMPO_TS_CREA,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dboPerfil.setField(DboPerfilCuenta.CAMPO_NIVEL_ACCESO_ID, "1");
			dboPerfil.add();

			//INSSERTA MANUEL
			//contrato
			DboContrato dbo6 = new DboContrato(dconn);
			dbo6.setField(DboContrato.CAMPO_FEC_CREA,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dbo6.setField(DboContrato.CAMPO_CUENTA_ID, cuentaId);
			dbo6.setField(DboContrato.CAMPO_ESTADO, "1");
			dbo6.setField(				DboContrato.CAMPO_TS_ULT_MODIF,
				FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dbo6.setField(
				DboContrato.CAMPO_TS_CREA,
				FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dbo6.setField(DboContrato.CAMPO_USR_ULT_MODIF, bean1.getUsuarioId());
			dbo6.setField(DboContrato.CAMPO_USR_CREA, bean1.getUsuarioId());
			dbo6.setField(DboContrato.CAMPO_VER_CONTRATO_ID, bean1.getContratoVersionId());
			//INSERTA MANUEL
			//		dbo6.setField(DboContrato.CAMPO_PE_JURI_ID, bean1.getJuris());
			dbo6.setField(DboContrato.CAMPO_PE_JURI_ID, peJuriId);
			//IINSERTA		

			dbo6.add();
			String contratoId = "" + dbo6.getMax(DboContrato.CAMPO_CONTRATO_ID);
			bean1.setContratoNumero(contratoId);

			//INSERTA

			//linea prepago
			DboLineaPrepago dbo7 = new DboLineaPrepago(dconn);
			dbo7.setField(DboLineaPrepago.CAMPO_SALDO, 0);
			dbo7.setField(DboLineaPrepago.CAMPO_CUENTA_ID, null);
			dbo7.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
			dbo7.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF,bean1.getUsuarioId());
			dbo7.setField(DboLineaPrepago.CAMPO_USR_CREA, bean1.getUsuarioId());
			dbo7.setField(DboLineaPrepago.CAMPO_TS_ULT_MODIF,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dbo7.setField(DboLineaPrepago.CAMPO_TS_CREA,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			//INSERTA MANUEL							
			//			dbo7.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, bean1.getAdmJuris());
			dbo7.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, peJuriId);
			//INSERTA						
			dbo7.setField(DboLineaPrepago.CAMPO_FG_DEPOSITO, "0");
			dbo7.add();

			dbo7.clearAll();
			dbo7.setField(DboLineaPrepago.CAMPO_SALDO, 0);
			dbo7.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuentaId);
			dbo7.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
			dbo7.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF, bean1.getUsuarioId());
			dbo7.setField(DboLineaPrepago.CAMPO_USR_CREA, bean1.getUsuarioId());
			dbo7.setField(DboLineaPrepago.CAMPO_TS_ULT_MODIF,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
			dbo7.setField(DboLineaPrepago.CAMPO_TS_CREA,FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
				
			//INSERTA MANUEL											
			//			dbo7.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, bean1.getAdmJuris());
			dbo7.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, peJuriId);
			//INSERTA 			
			dbo7.setField(DboLineaPrepago.CAMPO_FG_DEPOSITO, "0");
			dbo7.add();
			//		addField("LINEA_PREPAGO_ID","auto-inc", 0, false, "null");		
			String lineaPrePagoId = dbo7.getMax(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);			

			//ADEMAS, HACER UN INSERT EN LA TABLA ORG_CTAS, flag ='1'
			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);
			dboOrgCtas.setField(DboOrgCtas.CAMPO_CUENTA_ID, cuentaId);
			dboOrgCtas.setField(DboOrgCtas.CAMPO_FG_ADMIN, "1");
			//INSERTA MANUEL														
			//			dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID, bean1.getAdmJuris());
			dboOrgCtas.setField(DboOrgCtas.CAMPO_PE_JURI_ID, peJuriId);
			//INSERTA 														
			dboOrgCtas.add();

			//1sep2002H-se agrega registro en Cuenta Juris
			DboCuentaJuris dboCuentaJuris = new DboCuentaJuris(dconn);
			dboCuentaJuris.setField(dboCuentaJuris.CAMPO_CUENTA_ID, cuentaId);
			dboCuentaJuris.setField(dboCuentaJuris.CAMPO_REG_PUB_ID,bean1.getAdmRegPubId());			
			dboCuentaJuris.setField(dboCuentaJuris.CAMPO_OFIC_REG_ID,bean1.getAdmOficinaId());
			dboCuentaJuris.setField(dboCuentaJuris.CAMPO_PERSONA_ID, personaId);
			dboCuentaJuris.setField(dboCuentaJuris.CAMPO_JURIS_ID, bean1.getOrgJuris());
			dboCuentaJuris.add();

			/*
			aqui llamar a programas de Log de Carlo
			*/

			//-datos para el pie del contrato
			req.setAttribute("flag", "1");
			req.setAttribute("fecha_hora_sistema",gob.pe.sunarp.extranet.util.FechaUtil.getCurrentDateTime());
			req.setAttribute("usr_id", bean1.getUsuarioId());
			req.setAttribute("num_contrato", bean1.getContratoNumero());
			req.setAttribute("version_contrato", bean1.getContratoVersion());
			req.setAttribute("organizacion", bean1.getOrgRazon());
			req.setAttribute(
				"anrep",
				bean1.getRepApePat()
					+ " "
					+ bean1.getRepApeMat()
					+ ", "
					+ bean1.getRepNombre());
			req.setAttribute(
				"anadm",
				bean1.getAdmApePat()
					+ " "
					+ bean1.getAdmApeMat()
					+ ", "
					+ bean1.getAdmNombre());

			req.setAttribute("tipoDocumentoRep", bean1.getRepDescripcionTipoDocumento());
			req.setAttribute("tipoDocumentoAdm", bean1.getAdmDescripcionTipoDocumento());
			req.setAttribute("numeroDocumentoRep", bean1.getRepNumeroDocumento());
			req.setAttribute("numeroDocumentoAdm", bean1.getAdmNumeroDocumento());
			req.setAttribute("organizacion", bean1.getOrgRazon());

			// agregar usuario en TAM
			//insertar usuario en TAM
			SecAdmin secAdmin = SecAdmin.getInstance();
			secAdmin.adicionarUsuario(
				bean1.getUsuarioId(),
				bean1.getAdmNombre(),
				bean1.getAdmApePat(),
				"usr",
				bean1.getAdmPassword());
			secAdmin.adicionarUsuarioAGrupo(dconn, bean1.getUsuarioId(), "40");


				//Creando un UsuarioBean simulado
					UsuarioBean user = new UsuarioBean();
					user.setFgInterno(false);
					user.setUserId(bean1.getUsuarioId());
					user.setRegPublicoId(bean1.getAdmRegPubId());
					user.setOficRegistralId(bean1.getAdmOficinaId());
					user.setCuentaId(cuentaId);	//Seria mejor colocarlo en el bean1
					user.setFgAdmin(true);
					user.setFgIndividual(false);
					user.setExonPago(true);	//Porque es Afiliacion se pone true, debido a que no se le puede cobrar por no estar logeado.

				//Inicio de la Transaccion
					LogAuditoriaAfiliacionBean logbean = new LogAuditoriaAfiliacionBean();
					
					logbean.setCodigoServicio(TipoServicio.AFILIACION_EXTRANET);
                                        //Modificado por: Proyecto Filtros de Acceso
                                        //Fecha: 02/10/2006
                                        //logbean.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
                                        logbean.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
                                        //Fecha: 08/10/2006             
                                        logbean.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
                                        //Fin Modificación
					logbean.setUsuarioSession(user);
					
					logbean.setTipoAfil("O");
					logbean.setRegPubId(bean1.getAdmRegPubId());
					logbean.setOficRegId(bean1.getAdmOficinaId());
					logbean.setFgWeb("1");
					logbean.setNumCont(bean1.getContratoNumero());
					logbean.setUserId(bean1.getUsuarioId());
					logbean.setPersonaId(personaId);
					/*
					Job004 j = new Job004();
					j.setBean(logbean);
					Thread llamador1 = new Thread(j);
					llamador1.start();					
					*/
			//if (Propiedades.getInstance().getFlagProduccion()==false)
					Transaction.getInstance().registraTransaccion(logbean,conn);

			response.setStyle("mostrarContratoOrg");

			conn.commit();
		} //try

		catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		
		return response;
	} //fin aceptarContratoOrg

} //fin clase AfiliacionController