/**
 * 
 */
package gob.pe.sunarp.extranet.publicidad.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jcorporate.expresso.core.controller.Output;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.acceso.util.ControlAccesoFeriados;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboContrato;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboCuentaJuris;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboPerfilCuenta;
import gob.pe.sunarp.extranet.dbobj.DboTmInstSir;
import gob.pe.sunarp.extranet.dbobj.DboTmPregSecretas;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.publicidad.service.AccesoService;
import gob.pe.sunarp.extranet.util.ComboBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.Tarea;

/**
 * @author dbravo
 *
 */
public class AccesoServiceImpl extends ServiceImpl implements AccesoService, Constantes{

	private Connection conn;
	private DBConnection dbConn;
	private DBConnectionFactory pool;
	
	public AccesoServiceImpl() throws Exception{
		this.pool = DBConnectionFactory.getInstance();
		this.conn = pool.getConnection();
		this.dbConn = new DBConnection(conn);
	}
	
	public AccesoServiceImpl(DBConnectionFactory pool, Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
		this.pool = pool;
	}
	
	//Inicio:mgarate:28/07/2007
	public UsuarioBean validarIngreso(String username, String clave, String ipRemota, String session_id) throws CustomException, DBException, Throwable
	{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		UsuarioBean usuario;
		
		try
		{
			String user = username;
			String pwd = clave;
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);			
			
			DBConnection dconn = new DBConnection(conn);

			DboCuenta cuentaUserI = new DboCuenta(dconn);
			cuentaUserI.setField(DboCuenta.CAMPO_USR_ID, user);
			
			if (Propiedades.getInstance().getFlagProduccion() == false)
				cuentaUserI.setField(DboCuenta.CAMPO_CLAVE, pwd);

			ArrayList listCuentaUser = cuentaUserI.searchAndRetrieveList();

			if(listCuentaUser.size() < 1)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Usuario y Password incorrecto", "errorLogon");//Se puso ese descripcion de error para que no envie Mail
	
			if(listCuentaUser.size() > 1)
				throw new CustomException(Constantes.EC_GENERIC_DB_ERROR_INTEGRIDAD, "Existen mas de dos cuentas con el mismo usuario", "errorLogon");
	
			DboCuenta cuentaUser = (DboCuenta) listCuentaUser.get(0);

			// Estado = 0
			if (!cuentaUser.getField(DboCuenta.CAMPO_ESTADO).equals("1"))
				throw new CustomException(Constantes.CUENTA_DESHABILITADA, "Su cuenta de usuario se encuentra inactiva", "errorLogon");
			
			// Estado = 1
			String cuentaId = cuentaUser.getField(DboCuenta.CAMPO_CUENTA_ID);
			String usrId = cuentaUser.getField(DboCuenta.CAMPO_USR_ID);
			String fgNewUsrVent = cuentaUser.getField(DboCuenta.CAMPO_FG_NEW_USR_VENT);
			String peNatuId = cuentaUser.getField(DboCuenta.CAMPO_PE_NATU_ID);
					
			boolean exonPago = cuentaUser.getField(DboCuenta.CAMPO_EXON_PAGO).equals("1")?true:false;
			String tipoUsr = cuentaUser.getField(DboCuenta.CAMPO_TIPO_USR);
                        
            boolean existeSesion = ControlAccesoSesion.existeSesion(session_id, conn);
                                    
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
	                        throw new CustomException(E08007_TIENE_SESION_ACTIVA);
	                    }
	                }
	            }
            }
                                    
            //Obtiene el rango de IP
            String[] rangos = ControlAccesoIP.obtenerRangoIP(user, conn);
                                    
            if (rangos != null)
            {
                boolean accesoPermitido = ControlAccesoIP.accesoPermitidoIP(ipRemota, rangos);
                if (accesoPermitido)
                {
                    boolean cualquierIP = ControlAccesoIP.cualquierIPPermitida(rangos);
                    if (!cualquierIP)
                    {        //Verificar feriados
                    	boolean feriado = ControlAccesoFeriados.verificaAccesoFeriados(new java.util.Date(), conn);
                        if (feriado)
                        {
                                if (isTrace(this)) 
                            {
                                throw new CustomException(E08009_ES_FERIADO);
                            }
                        }
                    }
                }
                else
                {
                    if (isTrace(this))
                    {
                        throw new CustomException(E08008_DIRECCION_IP_NO_TIENE_ACCESO);
                    }
                }                            
            }
                                    
            //Fecha: 07/10/2006
            //Registra la nueva sesión
            if (session_id == null)
            {
	            if (isTrace(this))
	            {
	               throw new CustomException(E08010_NO_EXISTE_SESION_PD);
	            }
            }
                        
            if (!existeSesion)
            {
                ControlAccesoSesion.registrarNuevaSesion(user, session_id, ipRemota, ipRemota, conn);
            }
                        
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

			if (tipoUsr.substring(0, 1).equals("1")) 
			{
				DboLineaPrepago lineaPrePagoI = new DboLineaPrepago(dconn);
				lineaPrePagoI.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuentaId);
				lineaPrePagoI.setFieldsToRetrieve(
					DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID + "|" + DboLineaPrepago.CAMPO_SALDO);

				ArrayList listLineaPrePago = lineaPrePagoI.searchAndRetrieveList();
				lineaPrePagoI.clearFieldsToRetrieve();
				if (listLineaPrePago.size() == 1) 
				{
					DboLineaPrepago lineaPrePago = (DboLineaPrepago) listLineaPrePago.get(0);
					saldo_aux =
						Double.parseDouble(lineaPrePago.getField(DboLineaPrepago.CAMPO_SALDO));
					lineaPrePago_aux =
						lineaPrePago.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
				} else
					throw new CustomException(Constantes.NO_SALDO_DE_LINEA_PREPAGO);
			} else 
			{
				// Usuario Interno
				saldo_aux = 9999;
				lineaPrePago_aux = null;
				
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
			usuario = new UsuarioBean();
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
			usuario.setOficRegistralId(cuentajuri.getField(DboCuentaJuris.CAMPO_OFIC_REG_ID));
			usuario.setRegPublicoId(cuentajuri.getField(DboCuentaJuris.CAMPO_REG_PUB_ID));
			usuario.setJurisdiccionId(cuentajuri.getField(DboCuentaJuris.CAMPO_JURIS_ID));
			usuario.setApeMat(apMat);
			usuario.setApePat(apPat);
			usuario.setNombres(nombres);

			//Parte 4.1: Es Persona Juridica?

			String codOrg = null;

			if (tipoUsr.substring(1, 2).equals("0")) 
			{
				DboPeNatu peNatu = new DboPeNatu(dconn);
				peNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, peNatuId);
				peNatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);

				if (!peNatu.find())
					throw new CustomException(Constantes.NO_REG_PE_NATU);

				codOrg = peNatu.getField(DboPeNatu.CAMPO_PE_JURI_ID);
				usuario.setCodOrg(codOrg);

				DboPeJuri pejuri = new DboPeJuri(dconn);
				pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_PREF_CTA + "|" + DboPeJuri.CAMPO_RAZ_SOC);
				pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, codOrg);

				if (!pejuri.find())
					throw new CustomException(Constantes.NO_REG_PE_JURI);

				usuario.setUserAdminOrg(pejuri.getField(DboPeJuri.CAMPO_PREF_CTA) + "001");
				usuario.setRazSocial(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
				
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
			} else 
			{
				usuario.setCodOrg(null);
				usuario.setUserAdminOrg(null);
				//Anadido 14 de Setiembre
				usuario.setLineaPrePagoOrganizacion(null);

			}
			
			int count=0;
			StringBuffer q = new StringBuffer();
			q.append("SELECT COUNT(TM_PERMISO_EXT.PERMISO_ID) ");
			q.append("FROM TM_PERMISO_EXT, PERMISO_USR ");
			q.append("WHERE TM_PERMISO_EXT.ESTADO = '1' ");
			q.append("AND PERMISO_USR.CUENTA_ID = '").append(usuario.getCuentaId()).append("' ");
			q.append("AND TM_PERMISO_EXT.PERMISO_ID = '77' ");
			q.append("AND PERMISO_USR.PERMISO_ID = TM_PERMISO_EXT.PERMISO_ID ");
			
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(q.toString());
			if(rset.next())
			{
				count = rset.getInt(1);
			}
			
			if(count==0)
			{
				throw new CustomException(Constantes.NO_PERFILCUENTA_USUARIO);
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
				

			//Fin Parte 4: Guardo Datos en Sesion					

			//Parte 5: Verifico si es primera vez que se Logea el Usuario

			/*DESCAJ 02/01/2007  INICIO  */
				
		}catch (CustomException e){
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			
			throw e;
		} catch (DBException dbe) {
			rollback(conn);
			throw dbe;
		} catch (Throwable ex) {
			rollback(conn);
			throw ex;
		} finally {
			pool.release(conn);
		}
		
		return usuario;
	}
	//Fin:mgarate
}
