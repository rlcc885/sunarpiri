package gob.pe.sunarp.iri.acceso.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.*;
import com.jcorporate.expresso.core.controller.*;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoFeriados;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IngresoController
	extends ControllerExtension
	implements Constantes {
	private String thisClass = IngresoController.class.getName() + ".";

	public IngresoController() {
		super();
		addState(new State("validaIngreso", "Realizar proceso de Login del usuario"));
		addState(new State("cambiaPassword","Cambia el password para el usuario que esta en sesion"));
		addState(new State("cabecera", "Cabecera"));
		setInitialState("validaIngreso");
	}

	public String getTitle() {
		return new String("IngresoController");
	}

	protected ControllerResponse runValidaIngresoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			if (isTrace(this)) trace("inicia proceso de Login", request);
			/*
			Uso de flagProduccion:
				false  : se usa el displayLogin.html para pruebas en desarrollo
				         no hay TAM
				true   : configuracion para Produccion, TAM ACTIVADO validando login!
			*/

			String user = null;
			String pwd = null;

			/*
			Variables usadas si se utiliza la pantalla login Dummy
			       /acceso/displayLogin.html
			*/
			if (Propiedades.getInstance().getFlagProduccion() == false) 
			{
				if (isTrace(this)) trace("login sin TAM", request);
				user = request.getParameter("user");
				pwd = request.getParameter("clave");
				
				if (user == null)
				{
					response.setStyle("paginaInicial_desarrollo");
					return response;
				}
			}
			/* 
			El TAM coloca el usuario loggeado en el request, en la variable "iv-user"
			y pone su grupo en la variable "iv-groups"
			*/

			String iv_user = "";
			String iv_groups = "";

			if (Propiedades.getInstance().getFlagProduccion() == true) 
			{
				if (isTrace(this)) trace("recuperar usuario enviado por TAM", request);
				
				Enumeration enu = req.getHeaderNames();
				if (enu.hasMoreElements()) 
					{
					while (enu.hasMoreElements()) 
					{
						String parametroRequest = (String) enu.nextElement();
						if (parametroRequest.equals("iv-user"))
							iv_user = req.getHeader(parametroRequest);
						if (parametroRequest.equals("iv-groups"))
							iv_groups = req.getHeader(parametroRequest);
					}
				}

				if (iv_user.length() == 0)
					{
					if (isTrace(this)) trace("Error E08004", request);
					throw new CustomException(E08004_USUARIO_NO_VALIDADO_EN_TAM);
					}

				if (iv_groups.length() == 0)
					{
					if (isTrace(this)) trace("Error E08005", request);
					throw new CustomException(E08005_USUARIO_SIN_GRUPO);
					}
					
				if (isTrace(this)) trace("iv_user = " + iv_user, request);

				user = iv_user;
			}
			System.out.println("IngresoController-->DDD");
			

			//Crear una sesión.
			//Invalidar una si ya existe.
			HttpSession sx = req.getSession(false);
			if (sx!=null)
				sx.invalidate();
				
			HttpSession session = req.getSession(true);

			//Fin Parte 0.

			conn = pool.getConnection();
			conn.setAutoCommit(false);			
			
			DBConnection dconn = new DBConnection(conn);
			
			DboCuenta cuentaUserI = new DboCuenta(dconn);
			cuentaUserI.setField(DboCuenta.CAMPO_USR_ID, user);
			if (Propiedades.getInstance().getFlagProduccion() == false)
				cuentaUserI.setField(DboCuenta.CAMPO_CLAVE, pwd);
			
			if (isTrace(this)) trace("...buscando cuenta...", request);
			ArrayList listCuentaUser = cuentaUserI.searchAndRetrieveList();

			if(listCuentaUser.size() < 1)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Usuario y Password incorrecto", "errorLogon");//Se puso ese descripcion de error para que no envie Mail
	
			if(listCuentaUser.size() > 1)
				throw new CustomException(Constantes.EC_GENERIC_DB_ERROR_INTEGRIDAD, "Existen mas de dos cuentas con el mismo usuario", "errorLogon");
	
			DboCuenta cuentaUser = (DboCuenta) listCuentaUser.get(0);

			// Estado = 0
			if (!cuentaUser.getField(DboCuenta.CAMPO_ESTADO).equals("1"))
				throw new CustomException(Constantes.CUENTA_DESHABILITADA, "Su cuenta de usuario se encuentra inactiva", "errorLogon");
			
			System.out.println("IngresoController-->EEE");
			// Estado = 1
			String cuentaId = cuentaUser.getField(DboCuenta.CAMPO_CUENTA_ID);
			String usrId = cuentaUser.getField(DboCuenta.CAMPO_USR_ID);
			String fgNewUsrVent = cuentaUser.getField(DboCuenta.CAMPO_FG_NEW_USR_VENT);
			String peNatuId = cuentaUser.getField(DboCuenta.CAMPO_PE_NATU_ID);
			System.out.println("IngresoController-->FFF");
					
			boolean exonPago = cuentaUser.getField(DboCuenta.CAMPO_EXON_PAGO).equals("1")?true:false;
			String tipoUsr = cuentaUser.getField(DboCuenta.CAMPO_TIPO_USR);
			System.out.println("IngresoController-->GGG");
			
                        //------------------------------------------------------------------------------------------
                        //Modificado por: Proyecto Filtro de Acceso
                        //Fecha: 02/10/2006
                        String sesionId = ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request));
                        System.out.println("Id Sesion: " + sesionId);
                        String ipRemota = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
                        boolean existeSesion = ControlAccesoSesion.existeSesion(sesionId, conn);
                        System.out.println("IngresoController-->HHH");
                        if (!existeSesion)
                        {
                            boolean flagActivo = ControlAccesoSesion.indicadorSesionesActivo(user, conn);
                            if (flagActivo)
                            {
                                    //Verificar si tiene sesiones abiertas
                                boolean tieneSesiones = ControlAccesoSesion.tieneSesionesActivas(cuentaId, user, conn);
                                if (tieneSesiones) 
                                {
                                    if (isTrace(this)) 
                                    {
                                            trace("Error E08007", request);
                                        throw new CustomException(E08007_TIENE_SESION_ACTIVA);
                                    }
                                }
                            }
                        }
                        System.out.println("IngresoController-->III");
                        //Obtiene el rango de IP
                        String[] rangos = ControlAccesoIP.obtenerRangoIP(user, conn);
                                    
                        if (rangos != null)
                        {
                            boolean accesoPermitido = ControlAccesoIP.accesoPermitidoIP(ipRemota, rangos);
                            if (accesoPermitido)
                            {
                                    boolean cualquierIP = ControlAccesoIP.cualquierIPPermitida(rangos);
                                    if (!cualquierIP)
                                    {
                                            //Verificar feriados
                                    boolean feriado = ControlAccesoFeriados.verificaAccesoFeriados(new java.util.Date(), conn);
                                    if (feriado)
                                    {
                                            if (isTrace(this)) 
                                        {
                                            trace("Error E08009", request);
                                            throw new CustomException(E08009_ES_FERIADO);
                                        }
                                    }
                                }
                            }
                                else
                                {
                                    if (isTrace(this))
                                    {
                                            trace("Error E08008", request);
                                        throw new CustomException(E08008_DIRECCION_IP_NO_TIENE_ACCESO);
                                    }
                                }                            
                        }
                                    
                        System.out.println("IngresoController-->JJJ");
                        //Fecha: 07/10/2006
                        //Registra la nueva sesión
                        if (sesionId == null)
                            {
                            if (isTrace(this))
                            {
                                trace("Error E08010", request);
                                    throw new CustomException(E08010_NO_EXISTE_SESION_PD);
                            }
                        }
                        
                        System.out.println("IngresoController-->KKK");
                        if (!existeSesion)
                        {
                            ControlAccesoSesion.registrarNuevaSesion(user, sesionId, ipRemota, ipRemota, conn);
                        }
                        session.setAttribute("user-session-id", sesionId);
                                    
                        //Fin Modificacion

			//Parte 3: Obtengo Perfil y Nivel de Usuario
			DboPerfilCuenta perfilCtaI = new DboPerfilCuenta(dconn);
			perfilCtaI.setField(DboPerfilCuenta.CAMPO_CUENTA_ID, cuentaId);
			perfilCtaI.setField(DboPerfilCuenta.CAMPO_ESTADO, "1");
			perfilCtaI.setFieldsToRetrieve(
			DboPerfilCuenta.CAMPO_PERFIL_ID + "|" + DboPerfilCuenta.CAMPO_NIVEL_ACCESO_ID);

			ArrayList listaperfilCta = perfilCtaI.searchAndRetrieveList();
			perfilCtaI.clearFieldsToRetrieve();
			if (listaperfilCta.size() != 1)
				throw new CustomException(Constantes.NO_PERFILCUENTA_USUARIO);

			DboPerfilCuenta perfilCta = (DboPerfilCuenta) listaperfilCta.get(0);

			long perfilId = Long.parseLong(perfilCta.getField(DboPerfilCuenta.CAMPO_PERFIL_ID));

			if (isTrace(this)) trace("...buscando datos adicionales...", request);
			// Capturamos los datos de nombre y apellidos
			String nombres = null;
			String apPat = null;
			String apMat = null;
			DboPeNatu dboPN = new DboPeNatu(dconn);
			dboPN.setFieldsToRetrieve(DboPeNatu.CAMPO_NOMBRES + "|" + DboPeNatu.CAMPO_APE_PAT + "|" + DboPeNatu.CAMPO_APE_MAT);
			dboPN.setField(DboPeNatu.CAMPO_PE_NATU_ID, peNatuId);
			
			if (dboPN.find()) {
				nombres = dboPN.getField(DboPeNatu.CAMPO_NOMBRES);
				apPat = dboPN.getField(DboPeNatu.CAMPO_APE_PAT);
				apMat = dboPN.getField(DboPeNatu.CAMPO_APE_MAT);
			}

			//Fin Parte 3: Obtengo Perfil y Nivel de Usuario

			//Parte 2: Obtengo Saldo de Usuario

			// Usuario Externo con Linea de Prepago
			double saldo_aux = 0.0;
			String lineaPrePago_aux = null;

			if (tipoUsr.substring(0, 1).equals("1")) {
				if (isTrace(this)) trace("Usuario es Externo: Tipo Usuario : 1XXX", request);
				DboLineaPrepago lineaPrePagoI = new DboLineaPrepago(dconn);
				lineaPrePagoI.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuentaId);
				lineaPrePagoI.setFieldsToRetrieve(
					DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID + "|" + DboLineaPrepago.CAMPO_SALDO);

				ArrayList listLineaPrePago = lineaPrePagoI.searchAndRetrieveList();
				lineaPrePagoI.clearFieldsToRetrieve();
				if (listLineaPrePago.size() == 1) {
					DboLineaPrepago lineaPrePago = (DboLineaPrepago) listLineaPrePago.get(0);
					saldo_aux =
						Double.parseDouble(lineaPrePago.getField(DboLineaPrepago.CAMPO_SALDO));
					lineaPrePago_aux =
						lineaPrePago.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
				} else
					throw new CustomException(Constantes.NO_SALDO_DE_LINEA_PREPAGO);
			} else {
				// Usuario Interno
				if (isTrace(this)) trace("Usuario es Interno: Tipo Usuario : 0XXX", request);
				saldo_aux = 9999;
				lineaPrePago_aux = null;
				
				/*DboCargaLaboral dboCarLab = new DboCargaLaboral(dconn);
				dboCarLab.setField(dboCarLab.CAMPO_CUENTA_ID, cuentaId);
				dboCarLab.setField(dboCarLab.CAMPO_ESTADO, "1");
				if(dboCarLab.haySiguiente)
					session.setAttribute("REGIS","SI");
				*/
			}

					//Fin Parte 2: Obtengo Saldo de Usuario

					/* Obteniendo Permisos del Usuario */

					StringBuffer cadenacj = new StringBuffer(DboCuentaJuris.CAMPO_PERSONA_ID);
					cadenacj.append("|").append(DboCuentaJuris.CAMPO_OFIC_REG_ID);
					cadenacj.append("|").append(DboCuentaJuris.CAMPO_REG_PUB_ID);
					cadenacj.append("|").append(DboCuentaJuris.CAMPO_JURIS_ID);

					DboCuentaJuris cuentajuri = new DboCuentaJuris(dconn);
					cuentajuri.setFieldsToRetrieve(cadenacj.toString());
					cuentajuri.setField(DboCuentaJuris.CAMPO_CUENTA_ID, cuentaId);

					if (!cuentajuri.find())
						throw new CustomException(Constantes.NO_REG_CUENTA_JURIS);

					//Parte 4: Guardo Datos en Sesion
					UsuarioBean usuario = new UsuarioBean();
					usuario.setUserId(usrId);
					usuario.setCuentaId(cuentaId);
					usuario.setPeNatuId(peNatuId);
					usuario.setExonPago(exonPago);
					usuario.setTipoUser(tipoUsr);
					if (tipoUsr.substring(0, 1).equals("0")==true)
						usuario.setFgInterno(true);
					else
						usuario.setFgInterno(false);
					
					usuario.setFgIndividual(tipoUsr.substring(1, 2).equals("1"));
					usuario.setFgAdmin(tipoUsr.substring(2, 3).equals("1"));
					usuario.setPerfilId(perfilId);
					usuario.setNivelAccesoId(0);
					usuario.setSaldo(saldo_aux);
					usuario.setLinPrePago(lineaPrePago_aux);
					usuario.setPersonaId(cuentajuri.getField(DboCuentaJuris.CAMPO_PERSONA_ID));
					usuario.setOficRegistralId(
						cuentajuri.getField(DboCuentaJuris.CAMPO_OFIC_REG_ID));
					usuario.setRegPublicoId(cuentajuri.getField(DboCuentaJuris.CAMPO_REG_PUB_ID));
					usuario.setJurisdiccionId(cuentajuri.getField(DboCuentaJuris.CAMPO_JURIS_ID));
					usuario.setApeMat(apMat);
					usuario.setApePat(apPat);
					usuario.setNombres(nombres);

					//Parte 4.1: Es Persona Juridica?

					String codOrg = null;

					if (tipoUsr.substring(1, 2).equals("0")) {
						if (isTrace(this)) trace("Usuario Es Persona Juridica. Tipo Usuario: X0XX", request);
						DboPeNatu peNatu = new DboPeNatu(dconn);
						peNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, peNatuId);
						peNatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);

						if (!peNatu.find())
							throw new CustomException(Constantes.NO_REG_PE_NATU);

						codOrg = peNatu.getField(DboPeNatu.CAMPO_PE_JURI_ID);
						usuario.setCodOrg(codOrg);

						if (isTrace(this)) trace("Codigo Organizacion(PE_JURI_ID) = " + codOrg, request);

						DboPeJuri pejuri = new DboPeJuri(dconn);
						pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_PREF_CTA + "|" + DboPeJuri.CAMPO_RAZ_SOC);
						pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, codOrg);

						if (!pejuri.find())
							throw new CustomException(Constantes.NO_REG_PE_JURI);

						usuario.setUserAdminOrg(pejuri.getField(DboPeJuri.CAMPO_PREF_CTA) + "001");
						usuario.setRazSocial(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));

						if (isTrace(this)) trace("Usuario Administrador de la Organizacion ingresada es: "
								+ usuario.getUserAdminOrg(),
							request);
						
						// 17Sept2002cjvc77 - Es usuario Externo y Juridico 
						if(tipoUsr.substring(0,1).equals("1")){
							DboTmInstSir temp = new DboTmInstSir(dconn);
							temp.setFieldsToRetrieve(DboTmInstSir.CAMPO_CUR_PRES);
							temp.setField(DboTmInstSir.CAMPO_PE_JURI_ID, codOrg);
					
							if(temp.find())
								usuario.setCur(temp.getField(DboTmInstSir.CAMPO_CUR_PRES));
							else
								usuario.setCur(null);
						}else
							usuario.setCur(null);
							
						//Anadido 14 Setiembre modificado el 16
						if (tipoUsr.substring(0, 1).equals("1")) { //Usuario Externo
							DboLineaPrepago lp = new DboLineaPrepago(dconn);
							lp.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
							lp.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, codOrg);
							lp.setField(DboLineaPrepago.CAMPO_CUENTA_ID, null);
							
							if(!lp.find())
								throw new CustomException(Constantes.NO_LINEA_PREPAGO_ORG);
							
							usuario.setLineaPrePagoOrganizacion(lp.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
						}else
							usuario.setLineaPrePagoOrganizacion(null);
					} else {
						if (isTrace(this)) trace("Usuario Es Persona Natural Usuario: X1XX", request);
						usuario.setCodOrg(null);
						usuario.setUserAdminOrg(null);
						//Anadido 14 de Setiembre
						usuario.setLineaPrePagoOrganizacion(null);

					}

					//validar CONTRATO
					//Reglas : -TODO USUARIO EXTERNO TIENE CONTRATO
					//         -LOS  USUARIOS INTERNOS NO TIENEN CONTRATO
					//sep2002_HT

					//_old
					//_old Es Externo Natural o Externo Juridico Administrador
					//_old if (tipoUsr.startsWith("110") || tipoUsr.startsWith("101")) 
					
					//_new
					if (usuario.getFgInterno()==false)
					{
						DboContrato contrato = new DboContrato(dconn);
						// Persona Natural
						if (tipoUsr.substring(1, 2).equals("1")) {
							contrato.setField(DboContrato.CAMPO_CUENTA_ID, cuentaId);
							contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
						} else {
							// Persona Juridica
							contrato.setField(DboContrato.CAMPO_PE_JURI_ID, codOrg);
							contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
						}

						if (!contrato.find())
							throw new CustomException(Constantes.NO_REG_CONTRATO);

						usuario.setNum_contrato(contrato.getField(DboContrato.CAMPO_CONTRATO_ID));
					} else
						usuario.setNum_contrato(null);
						
					session.setAttribute("Usuario", usuario);

					//Fin Parte 4: Guardo Datos en Sesion					

					//Parte 5: Verifico si es primera vez que se Logea el Usuario

					/*DESCAJ 02/01/2007  INICIO  */
					java.util.Date hoy = new java.util.Date();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					boolean bolVencio=false;
					if(cuentaUser.getField(DboCuenta.CAMPO_TS_CAD_CLAVE)!= null && cuentaUser.getField(DboCuenta.CAMPO_TS_CAD_CLAVE).length()>0){
						
						bolVencio=!(df.parse(cuentaUser.getField(DboCuenta.CAMPO_TS_CAD_CLAVE).substring(0,19)).compareTo(hoy)>0);
					}
					/*DESCAJ 02/01/2007  FIN  */
					if (cuentaUser.getField("FG_NEW_USR_VENT").equals("1") || bolVencio )  {
						/*DESCAJ 02/01/2007  INICIO  agregar aqui el codigos*/
						if(bolVencio){
							req.setAttribute("mensaje","vencio");
						}else{
							req.setAttribute("mensaje","primera");
						}
						if(perfilId == PERFIL_INTERNO ||
							perfilId == PERFIL_CAJERO ||
							perfilId == PERFIL_TESORERO){
								req.setAttribute("nperfil","interno");	
								ComboBean bDias=Tarea.getDiasCaducidadClaveInt(dconn);
								req.setAttribute("nDiasCad",bDias.getAtributo1());			
						}
						if(perfilId == PERFIL_INDIVIDUAL_EXTERNO ||
						perfilId == PERFIL_AFILIADO_EXTERNO ||
						perfilId == PERFIL_ADMIN_GENERAL){
							req.setAttribute("nperfil","externo");
							req.setAttribute("arrCaducidad",Tarea.getComboCaducidadClave(dconn));
						}
		
						
						/*DESCAJ 02/01/2007  INICIO  agregar aqui el codigos*/
						info("Mostrando pagina de cambio de password", request);

						DboTmPregSecretas pregSecretasI = new DboTmPregSecretas(dconn);
						pregSecretasI.setField(DboTmPregSecretas.CAMPO_ESTADO, "1");
						pregSecretasI.setFieldsToRetrieve(
							DboTmPregSecretas.CAMPO_PREG_SEC_ID
								+ "|"
								+ DboTmPregSecretas.CAMPO_DESCRIPCION);
						//pregSecretasI.setFieldsToRetrieve("DESCRIPCION");
						ArrayList listaPregSecretas = pregSecretasI.searchAndRetrieveList();
						pregSecretasI.clearFieldsToRetrieve();
						for (int i = 0; i < listaPregSecretas.size(); i++) {
							DboTmPregSecretas lineaPrePago = (DboTmPregSecretas) listaPregSecretas.get(i);
							Output output = new Output();
							output.setName("PregSec" + i);
							output.setAttribute(
								"ID",
								lineaPrePago.getField(DboTmPregSecretas.CAMPO_PREG_SEC_ID));
							output.setAttribute(
								"Desc",
								lineaPrePago.getField(DboTmPregSecretas.CAMPO_DESCRIPCION));
							response.addOutput(output);
						}
						
						response.setStyle("paginaCambiaPassword");						
					} else {
						cuentaUser.clearAll();
						cuentaUser.setConnection(dconn);
						cuentaUser.setField(DboCuenta.CAMPO_CUENTA_ID, cuentaId);

						if (!cuentaUser.find())
							throw new CustomException(Constantes.NO_REG_CUENTA);

						cuentaUser.setField(
							DboCuenta.CAMPO_TS_ULT_ACC,
							FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
						cuentaUser.clearFieldsToUpdate();
						cuentaUser.setFieldsToUpdate(DboCuenta.CAMPO_TS_ULT_ACC);
						cuentaUser.update();
						
						
						
						conn.commit();
						cuentaUser.clearFieldsToUpdate();
						
						/**DESCAJ lsuarez  09/02/2007**/
						//response.setStyle("menuInicial");
						if(usuario.getPerfilId() == Constantes.PERFIL_DEVOLUCIONES){
							response.setStyle("menuInicialDevoluciones");
						} else{
							response.setStyle("menuInicial");
						}
						
					}
					//Fin Parte 5: Verifico si es primera vez que se Logea el Usuario
					System.out.println("HI there " + response.getStyle() );
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			ExpressoHttpSessionBean.getRequest(request).setAttribute(e.getForward(), e.getMessage());
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
			}
			catch (Throwable t){}
			end(request);
		}
		return response;
	}

	protected ControllerResponse runCambiaPasswordState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try {
			init(request);

			String npwd = request.getParameter("newPass");
			String cpwd = request.getParameter("conPass");
			

			/*
			-Se agregan funciones de TAM
			*/

			if (request.getParameter("pregSecret") == null
				|| request.getParameter("pregSecret").equals("0"))
					throw new CustomException(
						Constantes.EC_MISSING_PARAM,
						"Debe seleccionar alguna pregunta.");

			if (request.getParameter("rpta") == null
				|| request.getParameter("rpta").trim().length() <= 0)
					throw new CustomException(
						Constantes.EC_MISSING_PARAM,
						"Debe ingresar repuesta alguna para la pregunta que seleccione.");
			if (!npwd.equals(cpwd))
					throw new CustomException(Constantes.NO_COINCIDEN_PWDS);

			if (!((npwd.length() >= 6) && (npwd.length() <= 10)))
					throw new CustomException(
						Constantes.EC_PARAM_MISSFORMED,
						"El password debe tener como longitud entre 6 y 10 caracteres.");

			int longitud = npwd.length();
			boolean esAlfaNum = true;
			boolean hayNum = false;

			for (int i = 0; i < longitud; i++) {
				char caracter = npwd.charAt(i);
				if (!Character.isLetterOrDigit(caracter)) {
					esAlfaNum = false;
					break;
				} else {
					if (Character.isDigit(caracter))
							hayNum = true;
				}
			}

			if (!esAlfaNum)
					throw new CustomException(
						Constantes.EC_PARAM_MISSFORMED,
						"El password solo permite caracteres alfanumericos.");

			if (!hayNum)
					throw new CustomException(
						Constantes.EC_PARAM_MISSFORMED,
						"El password debe tener por lo menos un caracter numerico");

			//Fin Parte 0: Validaciones JavaScript

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			if(session==null)
				throw new CustomException(Constantes.E70000_NO_EXISTE_SESION);
			UsuarioBean usuario = (UsuarioBean) session.getAttribute("Usuario");
			/** DESCAJ IFIGUEROA 16/01/2006 INICIO**/
			String passActual = request.getParameter("actualPass");
			if(passActual!=null && passActual.length()>0){
				SecAdmin secAdmin = SecAdmin.getInstance();
				if(!secAdmin.validaUsuario(usuario.getUserId(),passActual))
				throw new ValidacionException("El password actual no es correcto","actualPass");/* CustomException(
					Constantes.EC_PARAM_MISSFORMED,
					"El password actual no es correcto");*/
				
			}
			/** DESCAJ IFIGUEROA 16/01/2006 FIN**/


			info("Ingresando a la Ventana Cambio de Password.", request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);

			DboCuenta cuentaUserI = new DboCuenta(dconn);
			cuentaUserI.setDBName(request.getDBName());
			cuentaUserI.setField(DboCuenta.CAMPO_CUENTA_ID, usuario.getCuentaId());

			if (!cuentaUserI.find())
					throw new CustomException(Constantes.USER_NO_EXISTE);
			/*DESCAJ 03/01/2007 IFIGUEROA INICIO*/
			Calendar gCal= Calendar.getInstance();
			long perfilId=usuario.getPerfilId();
			int dias=0;

			if( perfilId == PERFIL_INTERNO ||
				perfilId == PERFIL_CAJERO ||
				perfilId == PERFIL_TESORERO){
				String strDias=(String)request.getParameter("ndias");

				dias=Integer.parseInt(strDias);				
			}
			if( perfilId == PERFIL_INDIVIDUAL_EXTERNO ||
				perfilId == PERFIL_AFILIADO_EXTERNO ||
				perfilId == PERFIL_ADMIN_GENERAL){
				String strDias=(String)request.getParameter("cboCaducidad");
				dias=Integer.parseInt(strDias);				
			}
			gCal.add(Calendar.DAY_OF_MONTH,dias);
			/*DESCAJ 03/01/2007 IFIGUEROA FIN*/
			cuentaUserI.setFieldsToUpdate(
				"PREG_SEC_ID|CLAVE|RESP_SECRETA|TS_ULT_ACC|FG_NEW_USR_VENT|TS_CAD_CLAVE");
			cuentaUserI.setField("PREG_SEC_ID", request.getParameter("pregSecret"));

			cuentaUserI.setField("CLAVE", npwd);
			cuentaUserI.setField("RESP_SECRETA", request.getParameter("rpta"));
			cuentaUserI.setField("FG_NEW_USR_VENT", "0");
			cuentaUserI.setField("TS_ULT_ACC",FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
			if(dias>0)
				cuentaUserI.setField("TS_CAD_CLAVE",FechaUtil.dateTimeToStringToOracle(new Timestamp(gCal.getTime().getTime())));
			else
				cuentaUserI.setField("TS_CAD_CLAVE",null);
			cuentaUserI.update();

			//cambiar password en TAM
			SecAdmin secAdmin = SecAdmin.getInstance();
			secAdmin.cambiaPasswordUsuario(usuario.getUserId(), npwd);
			
			conn.commit();

			/**DESCAJ lsuarez  09/02/2007**/
			//response.setStyle("menuInicial");
			if(usuario.getPerfilId() == Constantes.PERFIL_DEVOLUCIONES){
				response.setStyle("menuInicialDevoluciones");
			} else{
				response.setStyle("menuInicial");
			}
		} 
		
		catch (ValidacionException e) 
		{
			rollback(conn, request);
			req.setAttribute("mensaje1",e.getMensaje());
			req.setAttribute("destino","back");
			response.setStyle("pantallaFinal");
		}		
		catch (CustomException e) 
		{
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}
		 catch (DBException dbe) {
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

	protected ControllerResponse runCabeceraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
		{

		try {
			init(request);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean usuario = (UsuarioBean) session.getAttribute("Usuario");
			
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			req.setAttribute("usuario", usuario.getUserId());
			req.setAttribute("saldo", Double.toString(usuario.getSaldo()));
			
			response.setStyle("cabecera");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}
}