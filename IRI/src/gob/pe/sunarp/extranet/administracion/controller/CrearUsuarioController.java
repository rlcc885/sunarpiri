package gob.pe.sunarp.extranet.administracion.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaAfiliacionBean;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.util.GeneraClave;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.administracion.bean.*;
import gob.pe.sunarp.extranet.administracion.util.ControlDiaHoraAcceso;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailException;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.dbobj.*;

import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class CrearUsuarioController
	extends ControllerExtension
	implements Constantes {

	public CrearUsuarioController() {
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
			/* RECUPERAMOS CONEXION A BASE DE DATOS E INICIAMOS TRANSACCION */
			if (isTrace(this)) trace("Recuperamos conexion a base de datos", request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			//obtener usuario de la sesion				
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
				if (lengthNumber.length() < 3) {
					lengthNumber = "0" + lengthNumber;
				}

				String newUserId = userId.getField(DboPeJuri.CAMPO_PREF_CTA) + lengthNumber;
				req.setAttribute("newUserId", newUserId);
			}

			// el cajero no puede asignar perfiles ni permisos extras
			
			//obtener lista de perfiles que puede asignar el usuario
			if (perfil != PERFIL_CAJERO)
				req.setAttribute("arrPerfiles",Tarea.getListaPerfilesAsignablesSegunPerfil(dconn, request, perfil));
							
			//obtener universo de permisos que puede asignar el usuario
			if (perfil != PERFIL_CAJERO)
				req.setAttribute("arrPermisos",Tarea.getListaPermisosAsignablesSegunPerfil(dconn, request, perfil));

			req.setAttribute("perfilId",""+perfil);
			
			//obtener el siguiente userId autogenerado
			if (perfil == PERFIL_ADMIN_GENERAL ||
				perfil == PERFIL_ADMIN_JURISDICCION ||
				perfil == PERFIL_ADMIN_ORG_EXT)
				{
					String peJuriId = usuario.getCodOrg();
					DboPeJuri dboPeJuri = new DboPeJuri(dconn);
					dboPeJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,peJuriId);
					if (dboPeJuri.find()==false)
						throw new IllegalArgumentException("No se encontró organización asociada");
						
					String prefijo   = dboPeJuri.getField(DboPeJuri.CAMPO_PREF_CTA);
					String personaId = dboPeJuri.getField(DboPeJuri.CAMPO_PERSONA_ID);
					int nu      = Integer.parseInt(dboPeJuri.getField(DboPeJuri.CAMPO_NU_USRS));
					nu++;
					
					String nextId = prefijo + Tarea.rellenaIzq(""+nu,"0",3);
					
					req.setAttribute("NEXT_ID", nextId);	
					
					
					//caso especial si es AO, el departamento y la provincia
					//deben ser los del usuario loggeado
					if (perfil == PERFIL_ADMIN_ORG_EXT)
						{
							DboDireccion dboDireccion = new DboDireccion(dconn);
							dboDireccion.setField(dboDireccion.CAMPO_PERSONA_ID,personaId);
							if (dboDireccion.find())
							{
								String paisId = dboDireccion.getField(dboDireccion.CAMPO_PAIS_ID);
								if (paisId.equals("01"))
									{
										req.setAttribute("casoAOd",dboDireccion.getField(dboDireccion.CAMPO_DPTO_ID));
										req.setAttribute("casoAOp",dboDireccion.getField(dboDireccion.CAMPO_PROV_ID));
									}
							}
						}
				}
			else
			{
				req.setAttribute("NEXT_ID","");	
			}
			
			response.setStyle("verFormulario");

			/* TERMINAMOS TRANSACCION CON EXITO */
			if (isTrace(this)) trace("Realizando commit a la base de datos", request);
			req.setAttribute("modo","0");

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
			try {
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
		DatosUsuarioBean bean = null;
		
		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);
			
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			//cargar los datos del request en un bean
			bean = Tarea.recojeDatosRequestUsuario(req, usuario.getPerfilId());

			//Validar que no exista un usuario repetido
			DboCuenta cuenta = new DboCuenta(dconn);
			
			if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
				usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION ||
				usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					bean.setUserId(req.getParameter("hidNEXT_ID"));
			
			cuenta.setField(DboCuenta.CAMPO_USR_ID, bean.getUserId());
			if (cuenta.find()==true) 
				throw new ValidacionException("Seleccione otro <USUARIO ID>, ya existe el ingresado","userId");


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//PROCESO DE CREACION DEL USUARIO
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

		
		//condiciones especiales según perfil del
		//usuario que realiza la operacion
		if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
			bean.setFlagInterno(false);

		//generar password aleatorio y enviarlo por email
		if (usuario.getPerfilId()==PERFIL_CAJERO)		
		{
			bean.setPreguntaSecreta("0");
			bean.setClave(GeneraClave.generaNuevaClave(bean.getUserId()));
			MailDataBean mailBean = new MailDataBean();
			mailBean.setTo(bean.getEmail());
			mailBean.setSubject("ENVIO DE PASSWORD");
			mailBean.setBody("Su id de usuario es "+bean.getUserId()+" y su nueva contrasena es : "+bean.getClave());
			MailProcessor.getInstance().saveMail(mailBean, conn);		
		}
		
		//8nov2002 - Zona
		Zona zona = new Zona();
		zona.setConn(dconn);
		zona.setUsuario(usuario);
		zona.setPaisId(bean.getPais());
		zona.setDepartamentoId(bean.getDepartamento());
		zona.setProvinciaId(bean.getProvincia());
		zona.calculaZona();
		String jurisId    = zona.getJurisId();
		String regPubId   = zona.getRegPubId();
		String oficRegId  = zona.getOficRegId();
			
		//Tabla Persona
		DboPersona newPersona = new DboPersona(dconn);
		newPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID,bean.getTipoDocumento());
		newPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN,bean.getNumDocumento());
		newPersona.setField(DboPersona.CAMPO_TPO_PERS,"N");
		newPersona.setField(DboPersona.CAMPO_FAX,bean.getFax());
		newPersona.setField(DboPersona.CAMPO_EMAIL,bean.getEmail());
		newPersona.setField(DboPersona.CAMPO_TELEF,bean.getTelefono());
		newPersona.setField(DboPersona.CAMPO_ANEXO,bean.getAnexo());
		newPersona.setField(DboPersona.CAMPO_JURIS_ID,jurisId);
		newPersona.setField(DboPersona.CAMPO_REG_PUB_ID,regPubId);
		newPersona.add();
		
		String personaId = newPersona.getField(DboPersona.CAMPO_PERSONA_ID);
		
		//tabla Pe_natu
		DboPeNatu personaNatural = new DboPeNatu(dconn);
		personaNatural.setField(DboPeNatu.CAMPO_APE_PAT,bean.getApellidoPaterno());
		personaNatural.setField(DboPeNatu.CAMPO_APE_MAT,bean.getApellidoMaterno());
		personaNatural.setField(DboPeNatu.CAMPO_NOMBRES,bean.getNombres());
		
		if (usuario.getPerfilId()==PERFIL_CAJERO || bean.getFlagInterno()==false)	
			personaNatural.setField(DboPeNatu.CAMPO_PE_JURI_ID,null);
		else
			personaNatural.setField(DboPeNatu.CAMPO_PE_JURI_ID,usuario.getCodOrg());
		
		//2nov2002
		if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
			personaNatural.setField(DboPeNatu.CAMPO_PE_JURI_ID,usuario.getCodOrg()); 
			
		personaNatural.setField(DboPeNatu.CAMPO_PERSONA_ID,personaId);
		personaNatural.add();
		
		if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT){
			//actualizar la tabla pejuri, incrementando el número de 
			//usuarios que tiene la organizacion
			DboPeJuri dboPeJuri = new DboPeJuri(dconn);
			dboPeJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,usuario.getCodOrg());
			if (dboPeJuri.find()==true)
			{
				dboPeJuri.clearFieldsToUpdate(); 
				dboPeJuri.setFieldsToUpdate(DboPeJuri.CAMPO_NU_USRS);
				
				int nu = Integer.parseInt(dboPeJuri.getField(DboPeJuri.CAMPO_NU_USRS));
				nu++;
					if (nu>=1000)
						throw new ValidacionException("Lo sentimos; pero no puede exceder de 999 usuarios por Organización");		
				dboPeJuri.setField(DboPeJuri.CAMPO_NU_USRS,String.valueOf(nu));
				dboPeJuri.update();
			}
		}
		
		if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
			usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION )
			{
				if (bean.getFlagInterno()==true){
					//actualizar la tabla pejuri, incrementando el número de 
					//usuarios que tiene la organizacion
					DboPeJuri dboPeJuri = new DboPeJuri(dconn);
					dboPeJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,usuario.getCodOrg());
					if (dboPeJuri.find()==true)
					{
						dboPeJuri.clearFieldsToUpdate(); 
						dboPeJuri.setFieldsToUpdate(DboPeJuri.CAMPO_NU_USRS);
						
						int nu = Integer.parseInt(dboPeJuri.getField(DboPeJuri.CAMPO_NU_USRS));
						nu++;
							if (nu>=1000)
								throw new ValidacionException("Lo sentimos; pero no puede exceder de 999 usuarios por Organización");		
						dboPeJuri.setField(DboPeJuri.CAMPO_NU_USRS,String.valueOf(nu));
						dboPeJuri.update();
					}
				}
			}
		/*
		if (bean.getFlagInterno()==true){
			if (usuario.getPerfilId()!=PERFIL_CAJERO)
			{
				//actualizar la tabla pejuri, incrementando el número de 
				//usuarios que tiene la organizacion
				DboPeJuri dboPeJuri = new DboPeJuri(dconn);
				dboPeJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,usuario.getCodOrg());
				if (dboPeJuri.find()==true)
				{
					dboPeJuri.clearFieldsToUpdate(); 
					dboPeJuri.setFieldsToUpdate(DboPeJuri.CAMPO_NU_USRS);
					
					int nu = Integer.parseInt(dboPeJuri.getField(DboPeJuri.CAMPO_NU_USRS));
					nu++;
					if (nu>=1000)
						throw new ValidacionException("Lo sentimos; pero no puede exceder de 999 usuarios por Organización");		
					dboPeJuri.setField(DboPeJuri.CAMPO_NU_USRS,String.valueOf(nu));
					dboPeJuri.update();
				}
			}
		}
		*/
		// Tabla Direccion 
		if (bean.getDistrito().trim().length()==0) 
				throw new ValidacionException("Por favor ingrese un distrito","distrito");		
		if (bean.getDireccion().trim().length()==0) 
				throw new ValidacionException("Por favor ingrese la direccion","direccion");						
		//if (bean.getCodPostal().trim().length()==0) 
		//		throw new ValidacionException("Por favor ingrese el codigo postal","codPostal");
		DboDireccion direccion = new DboDireccion(dconn);
		direccion.setField(DboDireccion.CAMPO_PERSONA_ID,personaId);
		direccion.setField(DboDireccion.CAMPO_PAIS_ID,bean.getPais());
		direccion.setField(DboDireccion.CAMPO_DPTO_ID,bean.getDepartamento());
		direccion.setField(DboDireccion.CAMPO_LUG_EXT,bean.getOtroDepartamento());
		direccion.setField(DboDireccion.CAMPO_PROV_ID,bean.getProvincia());
		direccion.setField(DboDireccion.CAMPO_NO_DIST,bean.getDistrito());
		direccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA,bean.getDireccion());
		direccion.setField(DboDireccion.CAMPO_COD_POST,bean.getCodPostal());
		direccion.add();

		//Registro en la tabla cuenta
		cuenta.clearAll();
		cuenta.setField(DboCuenta.CAMPO_USR_ID,bean.getUserId());
		
		String tipoUsr="";
		if (bean.getFlagInterno()==true)
			{
				tipoUsr="0000";
				if (bean.getPerfilId().equals(""+PERFIL_ADMIN_JURISDICCION))
					tipoUsr="0010";
			}
		else
			tipoUsr="1100";
			
		if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
			tipoUsr="1000";
		if (usuario.getPerfilId()==PERFIL_CAJERO)
			tipoUsr="1100";
			
		cuenta.setField(DboCuenta.CAMPO_TIPO_USR,tipoUsr);
		
		//todo usuario interno esta exonerado de pago
		if (bean.getFlagInterno()==true)
			bean.setFlagExoneradoPago(true);
			
		if (bean.getFlagExoneradoPago()==true)
			cuenta.setField(DboCuenta.CAMPO_EXON_PAGO,"1");
		else
			cuenta.setField(DboCuenta.CAMPO_EXON_PAGO,"0");
		cuenta.setField(DboCuenta.CAMPO_FG_NEW_USR_VENT,"1");
		cuenta.setField(DboCuenta.CAMPO_FG_REC_MAIL,"0");
		if (Propiedades.getInstance().getFlagGrabaClave()==true)
				cuenta.setField(DboCuenta.CAMPO_CLAVE,bean.getClave());
		else
				cuenta.setField(DboCuenta.CAMPO_CLAVE,"*");

		cuenta.setField(DboCuenta.CAMPO_RESP_SECRETA,bean.getRespuestaSecreta());
		cuenta.setField(DboCuenta.CAMPO_PREG_SEC_ID,"0");
		cuenta.setField(DboCuenta.CAMPO_TS_CREA,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		cuenta.setField(DboCuenta.CAMPO_TS_ULT_ACC,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		cuenta.setField(DboCuenta.CAMPO_USR_CREA,usuario.getUserId());
		cuenta.setField(DboCuenta.CAMPO_PE_NATU_ID,personaNatural.getField(DboPeNatu.CAMPO_PE_NATU_ID));
		if(bean.getFlagActivo()==true)
			cuenta.setField(DboCuenta.CAMPO_ESTADO,"1");
		else
			cuenta.setField(DboCuenta.CAMPO_ESTADO,"0");

		cuenta.setField(DboCuenta.CAMPO_FG_SYNC_TAM,"1");
		
		/*** MODIFICADO GIANCARLO OCHOA*/
		cuenta.setField(DboCuenta.CAMPO_TS_CAD_CLAVE, null);
		/********/
		cuenta.add();
		
		String cuentaId = cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
		
		//Tabla Perfil Cuenta
		DboPerfilCuenta perfilCuenta = new DboPerfilCuenta(dconn);
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_CUENTA_ID,cuentaId);
		
		if (usuario.getPerfilId()==PERFIL_ADMIN_GENERAL ||
			usuario.getPerfilId()==PERFIL_ADMIN_JURISDICCION)
			perfilCuenta.setField(DboPerfilCuenta.CAMPO_PERFIL_ID,bean.getPerfilId());
		if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
			{
			bean.setPerfilId(""+PERFIL_AFILIADO_EXTERNO);
			perfilCuenta.setField(DboPerfilCuenta.CAMPO_PERFIL_ID,""+PERFIL_AFILIADO_EXTERNO);
			}
		if (usuario.getPerfilId()==PERFIL_CAJERO)
		{
			bean.setPerfilId(""+PERFIL_INDIVIDUAL_EXTERNO);
			perfilCuenta.setField(DboPerfilCuenta.CAMPO_PERFIL_ID,""+PERFIL_INDIVIDUAL_EXTERNO);
		}
						
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_ESTADO,"1");
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_USR_CREA,usuario.getUserId());
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_USR_ULT_MODIF,usuario.getUserId());
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_TS_CREA,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_TS_ULT_MODIF,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		perfilCuenta.setField(DboPerfilCuenta.CAMPO_NIVEL_ACCESO_ID,"0");
		perfilCuenta.add();
		
		
		//Tabla Cuenta_juris
		DboCuentaJuris cuentaJuris = new DboCuentaJuris(dconn);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_CUENTA_ID,cuentaId);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_REG_PUB_ID,regPubId);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_OFIC_REG_ID,oficRegId);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_PERSONA_ID,personaId);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_JURIS_ID, jurisId);
		cuentaJuris.add();
		
		//hphp: Falta Tabla Org_Ctas
		if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
		{
			if (isTrace(this)) System.out.println("administrador de organizacion registrando un usuario interno");
			DboOrgCtas orgCuentas = new DboOrgCtas(dconn);
			orgCuentas.setField(DboOrgCtas.CAMPO_PE_JURI_ID, usuario.getCodOrg());
			orgCuentas.setField(DboOrgCtas.CAMPO_CUENTA_ID, cuentaId);
			orgCuentas.setField(DboOrgCtas.CAMPO_FG_ADMIN, "0");
			orgCuentas.add();
		} else if (((usuario.getPerfilId()==PERFIL_ADMIN_JURISDICCION)||(usuario.getPerfilId()==PERFIL_ADMIN_GENERAL))&&(bean.getFlagInterno()==true))
		{
			if (isTrace(this)) System.out.println("administrador general o de jurisdiccion registrando un usuario interno");
			DboOrgCtas orgCuentas = new DboOrgCtas(dconn);
			orgCuentas.setField(DboOrgCtas.CAMPO_PE_JURI_ID, usuario.getCodOrg());
			orgCuentas.setField(DboOrgCtas.CAMPO_CUENTA_ID, cuentaId);
			orgCuentas.setField(DboOrgCtas.CAMPO_FG_ADMIN, "0");
			orgCuentas.add();
		} if (usuario.getPerfilId()!=PERFIL_CAJERO)
		{
			if (isTrace(this)) System.out.println("cajero registrando un usuario");
		}
		
		//Tabla Contrato
		DboContrato contrato = null; 
		if(bean.getFlagInterno()==false)
		{
			DboVerContrato verContrato = new DboVerContrato(dconn);
			String verContratoId = verContrato.getMax(DboVerContrato.CAMPO_VER_CONTRATO_ID); 

			contrato = new DboContrato(dconn);
			contrato.setField(DboContrato.CAMPO_FEC_CREA,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			contrato.setField(DboContrato.CAMPO_CUENTA_ID,cuentaId);
			contrato.setField(DboContrato.CAMPO_ESTADO, "1");
			contrato.setField(DboContrato.CAMPO_USR_ULT_MODIF, usuario.getUserId());
			contrato.setField(DboContrato.CAMPO_USR_CREA, usuario.getUserId());
			contrato.setField(DboContrato.CAMPO_VER_CONTRATO_ID, verContratoId);
			contrato.setField(DboContrato.CAMPO_TS_ULT_MODIF, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			contrato.setField(DboContrato.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			contrato.add();
		}
		
		//Tabla linea prepago
		DboLineaPrepago lineaPrepago=null;
		if (bean.getFlagInterno()==false)
		{
			lineaPrepago = new DboLineaPrepago(dconn);
			lineaPrepago.setField(DboLineaPrepago.CAMPO_SALDO, "0.0");
			lineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuentaId);
			lineaPrepago.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
			lineaPrepago.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF, usuario.getUserId());
			lineaPrepago.setField(DboLineaPrepago.CAMPO_USR_CREA, usuario.getUserId());
			lineaPrepago.setField(DboLineaPrepago.CAMPO_TS_ULT_MODIF, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			lineaPrepago.setField(DboLineaPrepago.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			lineaPrepago.setField(DboLineaPrepago.CAMPO_FG_DEPOSITO,"0");
			lineaPrepago.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, null); 
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
		logbean.setUsuarioSession(usuario);
					
		logbean.setTipoAfil("I"); //I=individual
								  //O=organizacion
		                          
		                          
		if (usuario.getPerfilId()==Constantes.PERFIL_CAJERO &&
			usuario.getCodOrg().equals("0"))
			{
			logbean.setRegPubId(regPubId);
			logbean.setOficRegId(oficRegId);
			}
			else
			{
			logbean.setRegPubId(usuario.getRegPublicoId());
			logbean.setOficRegId(usuario.getOficRegistralId());
			}
		
		
		
		logbean.setFgWeb("0");
		
		logbean.setPersonaId(personaId);

		if(contrato != null)	
			logbean.setNumCont(contrato.getField(DboContrato.CAMPO_CONTRATO_ID));

		logbean.setUserId(bean.getUserId());
					
				/*
					Job004 j = new Job004();
					j.setBean(logbean);
					Thread llamador1 = new Thread(j);
					llamador1.start();		
				*/
			//if (Propiedades.getInstance().getFlagProduccion()==false)
				Transaction.getInstance().registraTransaccion(logbean,conn);

		/*
		sep2002_h
		
		SEGURIDAD, consideraciones:
		
		1.- Inscribir permisos extras en la tabla permiso_usr
		2.- Los permisos normales del usuario no se graban aquí porque
			se pueden obtener de la tabla permiso_perfil
		3.- Crear usuario en TAM
		4.- Los permisos extras también se deben inscribir en TAM
		5.- Los procesos en TAM deben ser ejecutarse AL FINAL y DESPUES
			de todos los demas procesos
		*/
		
		if (usuario.getPerfilId()!=PERFIL_CAJERO)
		{
		//Tabla Permiso_Usr
		DboPermisoUsr dboPermisoUsr = new DboPermisoUsr(dconn);		
		String[] permisosExtras = bean.getArrPermisoId();
		
		for(int i = 0 ; i < permisosExtras.length; i++)
		{
			dboPermisoUsr.clearAll();
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_CUENTA_ID, cuentaId);
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_PERMISO_ID,permisosExtras[i]);
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_ESTADO,"1");
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_USR_CREA,usuario.getUserId());
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_USR_ULT_MODIF,usuario.getUserId());
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_TS_CREA,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_TS_ULT_MODIF,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_FG_SYNC_TAM,"1");
			dboPermisoUsr.add();
		}
		}
		
		//insertar usuario en TAM
		SecAdmin secAdmin = SecAdmin.getInstance();
		secAdmin.adicionarUsuario(bean.getUserId(),bean.getNombres(),bean.getApellidoPaterno(),"usr", bean.getClave());
		secAdmin.adicionarUsuarioAGrupo(dconn,bean.getUserId(), bean.getPerfilId());
		
		
		if (usuario.getPerfilId()!=PERFIL_CAJERO)
		{
		String[] permisosExtras = bean.getArrPermisoId();
		//agregar permisos extras en TAM
		for(int i = 0 ; i < permisosExtras.length; i++)
		{		
			secAdmin.asignarACLaUsuario(dconn, permisosExtras[i], bean.getUserId());
		}
		}

				//Modificado por: Proyecto Filtros de Acceso
				//Fecha: 02/10/2006
				if (Propiedades.getInstance().getFlagProduccion() == true) 
				{
						if ((usuario.getPerfilId() == PERFIL_CAJERO) || (usuario.getPerfilId() == PERFIL_INTERNO) || (usuario.getPerfilId() == PERFIL_TESORERO) || (usuario.getExonPago() == true))
					{
						ControlDiaHoraAcceso.configurarFechaHora(usuario.getUserId());  
					}
				}
				//Fin Modificacion                
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------





			//mostrar la pagina de confirmacion de nuevo Usuario creado
			StringBuffer mf = new StringBuffer();
			mf.append("USUARIO : ");
			mf.append(bean.getUserId());
			mf.append(" con el nombre : ");
			mf.append(bean.getNombres());
			mf.append(" ");
			mf.append(bean.getApellidoPaterno());
			mf.append(" ");
			mf.append(bean.getApellidoMaterno());
			mf.append(" registrado Satisfactoriamente.");
			
			req.setAttribute("mensaje1",mf.toString());
			req.setAttribute("destino","Mantenimiento.do");
			response.setStyle("ok");

			/* TERMINAMOS TRANSACCION CON EXITO */
			if (isTrace(this)) trace("Realizando commit a la base de datos", request);
			conn.commit();
			
		} 

		catch (ValidacionException e) {
				rollback(conn, request);
				req.setAttribute("VALIDACION_EXCEPTION",e);
				req.setAttribute("DATOS_FORMULARIO", bean);
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