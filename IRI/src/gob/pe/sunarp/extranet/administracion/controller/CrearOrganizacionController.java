package gob.pe.sunarp.extranet.administracion.controller;

import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.administracion.bean.DatosOrganizacionBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;

import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaAfiliacionBean;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;
import java.io.IOException;
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

public class CrearOrganizacionController
	extends ControllerExtension
	implements Constantes {

	public CrearOrganizacionController() {
		super();
		addState(new State("verFormulario","Ver Formulario para ingreso de datos de organizacion"));
		addState(new State("registraDatos", "Registra Datos de organizaciones"));
		setInitialState("verFormulario");
	}

	public String getTitle() {
		return new String("CrearOrganizacionController");
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

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean datosUsuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			//pongo los datos en request para que los pueda manejar el JSP
			req.setAttribute("perfilId",""+datosUsuario.getPerfilId());

			FormBean formBean = null;
			if ((datosUsuario.getPerfilId() == PERFIL_ADMIN_GENERAL)
				|| (datosUsuario.getPerfilId() == PERFIL_CAJERO)) 
				formBean = Tarea.getDepartamento_Provincia(dconn);
			
			if (datosUsuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION) 
				formBean = Tarea.getDepartamento_ProvinciaPorJurisdiccion(dconn, datosUsuario.getJurisdiccionId());

			req.setAttribute("arrDepartamentos", formBean.getArray1());
			req.setAttribute("arrProvincias", formBean.getArray2());

			req.setAttribute("arrGiros", Tarea.getComboGiros(dconn));
			req.setAttribute("arrPaises", Tarea.getComboPaises(dconn));
			req.setAttribute("arrTiposDocumento", Tarea.getComboTiposDocumento(dconn));
			
			if (datosUsuario.getPerfilId() != PERFIL_CAJERO)
			{
				req.setAttribute("arrPreguntas", Tarea.getComboPreguntasSecretas(dconn));
				req.setAttribute("arrJurisdicciones", Tarea.getComboJurisdicciones(dconn));
			}

			response.setStyle("formOrganizacion");
			req.setAttribute("modo","0");

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


	protected ControllerResponse runRegistraDatosState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		DatosOrganizacionBean bean = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);

			UsuarioBean userLogged = ExpressoHttpSessionBean.getUsuarioBean(request);

			//-recoger datos del request
			bean = Tarea.recojeDatosRequestOrganizacion(req,userLogged.getPerfilId());
			
			//-validaciones:
			if (bean.getPrefijoCuenta()==null)
				throw new ValidacionException("Prefijo Cuenta es campo obligatorio","prefijoCuenta");
			if (bean.getPrefijoCuenta().trim().length()==0)
				throw new ValidacionException("Prefijo Cuenta es campo obligatorio","prefijoCuenta");			

			DboPeJuri personaJuri = new DboPeJuri(dconn);
			personaJuri.setField(DboPeJuri.CAMPO_PREF_CTA,bean.getPrefijoCuenta());
			ArrayList existePrefijo = personaJuri.searchAndRetrieveList();

			if (existePrefijo.size() == 1)
				throw new ValidacionException("Prefijo de cuenta se Repite","prefijoCuenta");
			
			//verificar que la clave y verificacion de contrasena
			if (bean.getClave() != null) {
				if (!bean.getClave().equalsIgnoreCase(bean.getConfirmacionClave())) 
					throw new ValidacionException("La clave no es igual a la confirmacion","clave");
			}

//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//PROCESO DE CREACION DE ORGANIZACION
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------

		String contrasena = bean.getClave();
		String usuarioId  = bean.getPrefijoCuenta()+"001";
		
		//condiciones especiales según perfil del
		//usuario que realiza la operacion
		if (userLogged.getPerfilId()==PERFIL_CAJERO)
			{
				bean.setFlagOrganizacionInterna(false);
				bean.setPreguntaSecretaId("0");
				contrasena = GeneraClave.generaNuevaClave(usuarioId);
			}
			
		String perfilOtorgado = "";
		if (userLogged.getPerfilId()==PERFIL_ADMIN_GENERAL)
		{
			if (bean.getFlagOrganizacionInterna()==false) 
				perfilOtorgado = ""+ PERFIL_ADMIN_ORG_EXT;
			else
				perfilOtorgado = ""+ PERFIL_ADMIN_JURISDICCION; 
		}
			
		if (userLogged.getPerfilId()==PERFIL_ADMIN_JURISDICCION)
			bean.setFlagOrganizacionInterna(false);

		if (userLogged.getPerfilId()==PERFIL_CAJERO || 
			userLogged.getPerfilId()==PERFIL_ADMIN_JURISDICCION)
			perfilOtorgado=""+PERFIL_ADMIN_ORG_EXT;


		//8nov2002 - Zona
		Zona zona = new Zona();
		zona.setConn(dconn);
		zona.setUsuario(userLogged);
		zona.setPaisId(bean.getPaisIdOrganizacion());
		zona.setDepartamentoId(bean.getDepartamentoIdOrganizacion());
		zona.setProvinciaId(bean.getProvinciaIdOrganizacion());
		
		zona.calculaZona();
		String jurisId    = zona.getJurisId();
		String regPubId   = zona.getRegPubId();
		String oficRegId  = zona.getOficRegId();
		
		if (bean.getFlagOrganizacionInterna()==true)
			jurisId = bean.getJurisdiccionId();
			
		//************ Actualizo datos de la organizacion ************//
		//tabla Persona
		DboPersona dboPersona = new DboPersona(dconn);
		dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, TIPO_DOCUMENTO_RUC);
		dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN, bean.getRuc());
		dboPersona.setField(DboPersona.CAMPO_TPO_PERS, "J");
		dboPersona.setField(DboPersona.CAMPO_EMAIL, bean.getEmailAdministrador());
		dboPersona.setField(DboPersona.CAMPO_JURIS_ID, jurisId);
		dboPersona.setField(DboPersona.CAMPO_REG_PUB_ID, regPubId);
		dboPersona.add();
		
		String firstPersonId = dboPersona.getField(DboPersona.CAMPO_PERSONA_ID);

		//Tabla Persona Juridica
		DboPeJuri personaJuridica = new DboPeJuri(dconn);
		personaJuridica.setField(DboPeJuri.CAMPO_RAZ_SOC, bean.getRazonSocial());
		personaJuridica.setField(DboPeJuri.CAMPO_PREF_CTA, bean.getPrefijoCuenta());
		personaJuridica.setField(DboPeJuri.CAMPO_SIGLAS, "");
		if (bean.getFlagOrganizacionInterna()==true)
			personaJuridica.setField(DboPeJuri.CAMPO_TIPO_ORG, "1");
		else
			personaJuridica.setField(DboPeJuri.CAMPO_TIPO_ORG, "0");
		personaJuridica.setField(DboPeJuri.CAMPO_REPRES_ID, null);
		personaJuridica.setField(DboPeJuri.CAMPO_PERSONA_ID, firstPersonId);
		personaJuridica.setField(DboPeJuri.CAMPO_NU_USRS, "1");
		personaJuridica.setField(DboPeJuri.CAMPO_GIRO_ID, bean.getGiroNegocio());
		personaJuridica.setField(DboPeJuri.CAMPO_JURIS_ID, jurisId);
		personaJuridica.add();
		
		String organizacionPeJuriId = personaJuridica.getField(DboPeJuri.CAMPO_PE_JURI_ID);

		//Verificar CUR
		if (bean.getFlagOrganizacionInterna()==false)
			{
				String cur = bean.getCur();
				if (cur!=null && cur.trim().length()>0)
				{
					DboTmInstSir dboTmInstSir = new DboTmInstSir(dconn);
					dboTmInstSir.setField(DboTmInstSir.CAMPO_CUR_PRES, cur);
					dboTmInstSir.setField(DboTmInstSir.CAMPO_OFIC_REG_ID, oficRegId);
					dboTmInstSir.setField(DboTmInstSir.CAMPO_REG_PUB_ID, regPubId);
					ArrayList arrc = dboTmInstSir.searchAndRetrieveList();
					if (arrc.size() == 0)
						throw new ValidacionException("Cur no existe","cur");
					dboTmInstSir = (DboTmInstSir) arrc.get(0);
					dboTmInstSir.setConnection(dconn);
					dboTmInstSir.setFieldsToUpdate(DboTmInstSir.CAMPO_PE_JURI_ID);
					dboTmInstSir.setField(DboTmInstSir.CAMPO_PE_JURI_ID, organizacionPeJuriId);
					dboTmInstSir.update();					
				}
			} 
					
		//direccion organizacion
		DboDireccion direccion = new DboDireccion(dconn);
		direccion.setField(DboDireccion.CAMPO_PERSONA_ID, firstPersonId);
		direccion.setField(DboDireccion.CAMPO_PAIS_ID, bean.getPaisIdOrganizacion());
		direccion.setField(DboDireccion.CAMPO_DPTO_ID, bean.getDepartamentoIdOrganizacion());
		direccion.setField(DboDireccion.CAMPO_LUG_EXT, bean.getOtroDepartamentoOrganizacion());
		direccion.setField(DboDireccion.CAMPO_PROV_ID, bean.getProvinciaIdOrganizacion());
		direccion.setField(DboDireccion.CAMPO_NO_DIST, bean.getDistritoOrganizacion());
		direccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA, bean.getDireccionOrganizacion());
		direccion.setField(DboDireccion.CAMPO_COD_POST, bean.getCodPostalOrganizacion());
		direccion.add();
		//************ Fin Actualizo datos de la organizacion ************//
		
		
		
		
		
		
		

		//************ Actualizo Representante Legal************//
		//tabla persona
		dboPersona.clearAll();
		dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, bean.getTipoDocumentoRepresentante());
		dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN, bean.getNumeroDocumentoRepresentante());
		dboPersona.setField(DboPersona.CAMPO_TPO_PERS, "N");
		dboPersona.setField(DboPersona.CAMPO_EMAIL, bean.getEmailAdministrador());
		dboPersona.setField(DboPersona.CAMPO_JURIS_ID, jurisId);
		dboPersona.setField(DboPersona.CAMPO_REG_PUB_ID, regPubId);
		dboPersona.add();
		
		String representantePersonaId = dboPersona.getField(DboPersona.CAMPO_PERSONA_ID);

		//tabla PE_NATU
		DboPeNatu personaNatural = new DboPeNatu(dconn);
		personaNatural.setField(DboPeNatu.CAMPO_APE_PAT, bean.getApellidoPaternoRepresentante());
		personaNatural.setField(DboPeNatu.CAMPO_APE_MAT, bean.getApellidoMaternoRepresentante());
		personaNatural.setField(DboPeNatu.CAMPO_NOMBRES, bean.getNombresRepresentante());
		personaNatural.setField(DboPeNatu.CAMPO_PE_JURI_ID, organizacionPeJuriId);
		personaNatural.setField(DboPeNatu.CAMPO_PERSONA_ID, representantePersonaId);
		personaNatural.add();
		
		String representantePeNatuId = personaNatural.getField(DboPeNatu.CAMPO_PE_NATU_ID);
		
		//actualizar PE_JURI con REPRES_ID = PE_NATU_ID del representante legal
		personaJuridica.clearAll();
		personaJuridica.setField(personaJuridica.CAMPO_PE_JURI_ID,organizacionPeJuriId);
		boolean ff = personaJuridica.find();
		if (ff==true)
		{
			personaJuridica.setFieldsToUpdate(DboPeJuri.CAMPO_REPRES_ID);
			personaJuridica.setField(DboPeJuri.CAMPO_REPRES_ID, representantePeNatuId);		
			personaJuridica.update();
		}
		//************ Fin Actualizo Representante Legal************//		







		//************ Actualizo Administrador ************//
		//tabla persona
		dboPersona.clearAll();
		dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, bean.getTipoDocumentoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN, bean.getNumeroDocumentoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_TPO_PERS, "N");
		dboPersona.setField(DboPersona.CAMPO_EMAIL, bean.getEmailAdministrador());
		dboPersona.setField(DboPersona.CAMPO_TELEF, bean.getTelefonoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_ANEXO, bean.getAnexoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_FAX, bean.getFaxAdministrador());
		dboPersona.setField(DboPersona.CAMPO_JURIS_ID, jurisId);		
		dboPersona.setField(DboPersona.CAMPO_REG_PUB_ID, regPubId);
		dboPersona.add();
		
		String administradorPersonaId = dboPersona.getField(DboPersona.CAMPO_PERSONA_ID);

		//tabla PE_NATU
		personaNatural.clearAll();
		personaNatural.setField(DboPeNatu.CAMPO_PERSONA_ID, administradorPersonaId);
		personaNatural.setField(DboPeNatu.CAMPO_APE_PAT, bean.getApellidoPaternoAdministrador());
		personaNatural.setField(DboPeNatu.CAMPO_APE_MAT, bean.getApellidoMaternoAdministrador());
		personaNatural.setField(DboPeNatu.CAMPO_NOMBRES, bean.getNombresAdministrador());
		personaNatural.setField(DboPeNatu.CAMPO_PE_JURI_ID, organizacionPeJuriId);
		personaNatural.setField(DboPeNatu.CAMPO_PERSONA_ID, administradorPersonaId);
		personaNatural.add();
		
		String administradorPeNatuId = personaNatural.getField(DboPeNatu.CAMPO_PE_NATU_ID);

		//Registro direccion (administrador jurisdiccion)
		direccion.clearAll();
		direccion.setField(DboDireccion.CAMPO_PERSONA_ID, administradorPersonaId);
		direccion.setField(DboDireccion.CAMPO_PAIS_ID, bean.getPaisAdministrador());
		direccion.setField(DboDireccion.CAMPO_DPTO_ID, bean.getDepartamentoAdministrador());
		direccion.setField(DboDireccion.CAMPO_LUG_EXT, bean.getOtroDepartamentoAdministrador());
		direccion.setField(DboDireccion.CAMPO_PROV_ID, bean.getProvinciaAdministrador());
		direccion.setField(DboDireccion.CAMPO_NO_DIST, bean.getDistritoAdministrador());
		direccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA, bean.getDireccionAdministrador());
		direccion.setField(DboDireccion.CAMPO_COD_POST, bean.getCodPostalAdministrador());
		direccion.add();
		//*************************fin insert datos administrador ********************//
		






		//Registro en la tabla cuenta
		DboCuenta cuenta = new DboCuenta(dconn);
		cuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);
		
		if (bean.getFlagOrganizacionInterna()==false) 
			cuenta.setField(DboCuenta.CAMPO_TIPO_USR, "1010");
		else
			cuenta.setField(DboCuenta.CAMPO_TIPO_USR, "0010");
		
		
		//todo usuario interno esta exonerado de pago
		if (bean.getFlagOrganizacionInterna()==true)
			bean.setFlagExonerarPago(true);
		
		if (bean.getFlagExonerarPago()==true)
			cuenta.setField(DboCuenta.CAMPO_EXON_PAGO, "1");
		else
			cuenta.setField(DboCuenta.CAMPO_EXON_PAGO, "0");
		cuenta.setField(DboCuenta.CAMPO_FG_NEW_USR_VENT, "1");
		cuenta.setField(DboCuenta.CAMPO_FG_REC_MAIL, "1");
		cuenta.setField(DboCuenta.CAMPO_RESP_SECRETA, bean.getRespuestaSecreta());
		if (Propiedades.getInstance().getFlagGrabaClave()==false)
			cuenta.setField(DboCuenta.CAMPO_CLAVE, "*");		
		else
			cuenta.setField(DboCuenta.CAMPO_CLAVE, contrasena);
		cuenta.setField(DboCuenta.CAMPO_PREG_SEC_ID, bean.getPreguntaSecretaId());
		cuenta.setField(DboCuenta.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		cuenta.setField(DboCuenta.CAMPO_TS_ULT_ACC, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		cuenta.setField(DboCuenta.CAMPO_USR_CREA, userLogged.getUserId());
		cuenta.setField(DboCuenta.CAMPO_PE_NATU_ID, administradorPeNatuId);
		cuenta.setField(DboCuenta.CAMPO_ESTADO, "1");
		cuenta.setField(DboCuenta.CAMPO_FG_SYNC_TAM, "1");
		cuenta.add();

		//Tabla Cuenta_Juris
		DboCuentaJuris cuentaJuris = new DboCuentaJuris(dconn);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_CUENTA_ID, cuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
		cuentaJuris.setField(DboCuentaJuris.CAMPO_JURIS_ID, jurisId);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_REG_PUB_ID, regPubId);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_OFIC_REG_ID, oficRegId);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_PERSONA_ID, firstPersonId);
		cuentaJuris.add();

		//Tabla Perfil Cuenta
		DboPerfilCuenta perfilCuenta = new DboPerfilCuenta(dconn);
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_CUENTA_ID, cuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_PERFIL_ID, perfilOtorgado);
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_ESTADO, "1");
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_TS_ULT_MODIF, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_USR_CREA, userLogged.getUserId());
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_USR_ULT_MODIF, userLogged.getUserId());
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_NIVEL_ACCESO_ID, "1");
		perfilCuenta.add();

		DboContrato contrato = null;
		if (bean.getFlagOrganizacionInterna()==false)
		{
			//Tabla Contrato
			DboVerContrato verContrato = new DboVerContrato(dconn);
			String verContratoId = verContrato.getMax(DboVerContrato.CAMPO_VER_CONTRATO_ID);
			
			contrato = new DboContrato(dconn);
			contrato.setField(DboContrato.CAMPO_FEC_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			contrato.setField(DboContrato.CAMPO_CUENTA_ID, cuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
			contrato.setField(DboContrato.CAMPO_ESTADO, "1");
			contrato.setField(DboContrato.CAMPO_USR_ULT_MODIF, userLogged.getUserId());
			contrato.setField(DboContrato.CAMPO_USR_CREA, userLogged.getUserId());
			contrato.setField(DboContrato.CAMPO_VER_CONTRATO_ID, verContratoId);
			contrato.setField(DboContrato.CAMPO_TS_ULT_MODIF, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			contrato.setField(DboContrato.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			contrato.setField(DboContrato.CAMPO_PE_JURI_ID, organizacionPeJuriId);
			contrato.add();
		}

		//Datos de la linea prepago
		DboLineaPrepago lineaPrepago = null;
		if (bean.getFlagOrganizacionInterna()==false) 
		{ 
			lineaPrepago = new DboLineaPrepago(dconn);
			lineaPrepago.setField(DboLineaPrepago.CAMPO_SALDO, "0.0");
			lineaPrepago.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
			lineaPrepago.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF, userLogged.getUserId());
			lineaPrepago.setField(DboLineaPrepago.CAMPO_USR_CREA, userLogged.getUserId());
			lineaPrepago.setField(DboLineaPrepago.CAMPO_TS_ULT_MODIF, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			lineaPrepago.setField(DboLineaPrepago.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			lineaPrepago.setField(DboLineaPrepago.CAMPO_FG_DEPOSITO, "0");
			lineaPrepago.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, organizacionPeJuriId);
			lineaPrepago.add();
		}

		//Grabar en Org Cuentas
		DboOrgCtas orgCuentas = new DboOrgCtas(dconn);
		orgCuentas.setField(DboOrgCtas.CAMPO_PE_JURI_ID, organizacionPeJuriId);
		orgCuentas.setField(DboOrgCtas.CAMPO_CUENTA_ID, cuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
		orgCuentas.setField(DboOrgCtas.CAMPO_FG_ADMIN, "1");
		orgCuentas.add();

		if (bean.getFlagOrganizacionInterna()==false)
		{ 
			lineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
			lineaPrepago.add();
		}


			LogAuditoriaAfiliacionBean logbean = new LogAuditoriaAfiliacionBean();
					
			logbean.setCodigoServicio(TipoServicio.AFILIACION_EXTRANET);
                        //Modificado por: Proyecto Filtros de Acceso
                        //Fecha: 02/10/2006
                        //logbean.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
                        logbean.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
                        //Fecha: 08/10/2006             
                        logbean.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
                        //Fin Modificación
			logbean.setUsuarioSession(userLogged);
					
			logbean.setTipoAfil("O");
			
			if (userLogged.getPerfilId()==Constantes.PERFIL_CAJERO &&
			    userLogged.getCodOrg().equals("0"))
			    {
				logbean.setRegPubId(regPubId);
				logbean.setOficRegId(oficRegId);
			    }			
			else
				{
				logbean.setRegPubId(userLogged.getRegPublicoId());
				logbean.setOficRegId(userLogged.getOficRegistralId());
				}
			logbean.setFgWeb("0");
			logbean.setPersonaId(firstPersonId);

			if (contrato != null)
				logbean.setNumCont(contrato.getField(DboContrato.CAMPO_CONTRATO_ID));

			logbean.setUserId(usuarioId);
					
			/*
					Job004 j = new Job004();
					j.setBean(logbean);
					Thread llamador1 = new Thread(j);
					llamador1.start();		
			*/
			//if (Propiedades.getInstance().getFlagProduccion()==false)
			Transaction.getInstance().registraTransaccion(logbean,conn);

		if (userLogged.getPerfilId()==PERFIL_CAJERO)
		{
			MailDataBean mailBean = new MailDataBean();
			mailBean.setTo(bean.getEmailAdministrador());
			mailBean.setSubject("ENVIO DE PASSWORD");
			mailBean.setBody("Su id de usuario es "+usuarioId+" y su nueva contrasena es : "+contrasena);
			MailProcessor.getInstance().saveMail(mailBean, conn);		
			
			//ademas, enviar un mail al administrador de la jurisdiccion a la que
			//pertenece el cajero
			cuenta.clearAll();
			cuenta.setField(cuenta.CAMPO_USR_ID,userLogged.getUserAdminOrg());
			if (cuenta.find())
				{
					String xpeNatuId = cuenta.getField(cuenta.CAMPO_PE_NATU_ID);
					personaNatural.clearAll();
					personaNatural.setField(personaNatural.CAMPO_PE_NATU_ID,xpeNatuId);
					if (personaNatural.find())
						{
							String xpersonaId = personaNatural.getField(personaNatural.CAMPO_PERSONA_ID);
							dboPersona.clearAll();
							dboPersona.setField(dboPersona.CAMPO_PERSONA_ID,xpersonaId);
							if (dboPersona.find())
								{
								mailBean = new MailDataBean();
								mailBean.setTo(dboPersona.getField(dboPersona.CAMPO_EMAIL));
								mailBean.setSubject("CREACION DE ORGANIZACION A TRAVES DE CAJERO");
								mailBean.setBody("El cajero "+userLogged.getUserId()+" ha creado la organizacion: "+bean.getRazonSocial());
								MailProcessor.getInstance().saveMail(mailBean, conn);
								}
						}//if (personaNatural.find())
				}//if (cuenta.find())
		}//if (userLogged.getPerfilId()==PERFIL_CAJERO)


		
		
				
		//insertar usuario en TAM
		SecAdmin secAdmin = SecAdmin.getInstance();
		secAdmin.adicionarUsuario(usuarioId, 
								bean.getNombresAdministrador(), 
								bean.getApellidoPaternoAdministrador(), 
								"usr", 
								contrasena);	
		secAdmin.adicionarUsuarioAGrupo(dconn, usuarioId, perfilOtorgado);
		

		

//------------------------------------------------------------------


			StringBuffer mf = new StringBuffer();
			mf.append("Organizaci&oacute;n ");
			mf.append(bean.getRazonSocial());
			mf.append(" creada con &eacute;xito.<BR>Nuevo usuario = [");
			mf.append(bean.getPrefijoCuenta());
			mf.append("001]");
			req.setAttribute("destino","MantenimientoOrg.do");
			req.setAttribute("mensaje1",mf.toString());
			response.setStyle("ok");

			if (isTrace(this)) trace("Realizando commit a la base de datos", request);
			conn.commit();
		} //try
		catch (ValidacionException e) {
			//esta excepcion la usare para redireccion a la 
			//pagina de creacion con el mensaje respectivo
			rollback(conn, request);
			req.setAttribute("VALIDACION_EXCEPTION",e);
			req.setAttribute("DATOS_ORGANIZACION_BEAN",bean);
			try {
				this.transition("verFormulario", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} finally {
				if (pool != null)
					pool.release(conn);
				end(request);
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
			try{
				pool.release(conn);
			}catch(Throwable tt){}
			end(request);
		}
		return response;
	}
}