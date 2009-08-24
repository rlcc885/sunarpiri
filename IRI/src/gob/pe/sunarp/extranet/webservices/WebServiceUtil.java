package gob.pe.sunarp.extranet.webservices;

import gob.pe.sunarp.extranet.common.Serializacion;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboContrato;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboCuentaJuris;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboPerfilCuenta;
import gob.pe.sunarp.extranet.dbobj.DboTmInstSir;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.BLOB;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;


public class WebServiceUtil {

	public static boolean validaOperacion(Connection conn, String cuo, String codServicio, String codUsuario, String codInstitucion, String descInstitucion) throws DBException, Throwable {
		String sentenciaSql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean res = false;
		String secuencial = null;
		String fechActual = null;
		int reg = 0;
			
		try { 
			System.out.println("validaOperacion....");
			
			//HACIA LA BODEGA CENTRAL
			sentenciaSql = "SELECT COUNT(*) AS TMP FROM OPERACION WHERE OPERACION.CUO = ? AND OPERACION.ESTADO = ? AND OPERACION.SERVICIO_ID = ?";
			pstmt = conn.prepareStatement(sentenciaSql);
			
			pstmt.setString(1, cuo);
			pstmt.setString(2, "0");//ESTADO PROCESADO
			pstmt.setString(3, codServicio);
			
			rs = pstmt.executeQuery(); // Execute the statement				  					 
			rs.next();
			reg = rs.getInt("TMP");

			if (reg>0)
				return false;
			
			sentenciaSql = "SELECT id_sec.nextval AS SECUENCIA FROM DUAL";
			pstmt = conn.prepareStatement(sentenciaSql);
			rs = pstmt.executeQuery();
			
			rs.next();
			secuencial = rs.getString("SECUENCIA");
			System.out.println("secuencial::"+secuencial);
			//solicitudInscripcion.setSecuencialOperacion(secuencial);
			
			fechActual = FechaUtil.getCurrentDateTime();
			
			System.out.println("Insertando en OPERACION");
			sentenciaSql = "INSERT INTO OPERACION (SECUENCIA, CUO, SERVICIO_ID, ID_USUARIO, CUR, NO_CUR, FECHA, ESTADO) VALUES (?,?,?,?,?,?,TO_DATE(?,'dd/MM/yyyy HH24:MI:SS'),?)";
			
			pstmt = conn.prepareStatement(sentenciaSql);
			
			pstmt.setInt(1, Integer.parseInt(secuencial));
			pstmt.setString(2, cuo);
			pstmt.setString(3, codServicio);
			pstmt.setString(4, codUsuario);
			pstmt.setString(5, codInstitucion);
			pstmt.setString(6, descInstitucion);
			pstmt.setString(7, fechActual);
			pstmt.setString(8, "1");//ESTADO PENDIENTE
			
			pstmt.execute(); // Execute the statement
			
			conn.commit();
			
			res = true;

		} 
		catch (Throwable ex) {
			ex.printStackTrace();
			conn.rollback();
			throw ex;
		} 
		finally {
			try{
				if (pstmt !=null)
					pstmt.close();
			}
			catch (Throwable t) {
				throw t;
			}
		}
		
		return res;
		
	}

	public static boolean validaPermisoUsuario(Connection conn, String cuentaID, String permisoID) throws DBException, Throwable {
		boolean b = false;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer sb = null;

		try {
			System.out.println("validaPermisoUsuario...");
			sb = new StringBuffer();
			sb.delete(0,sb.length());
			sb.append(" SELECT ");
			sb.append(" TM_PERMISO_EXT.PERMISO_ID as permisoId, ");
			sb.append(" TM_PERMISO_EXT.STRING_URL as stringUrl,");
			sb.append(" TM_PERMISO_EXT.NOMBRE as nombre,");
			sb.append(" TM_PERMISO_EXT.METODO as metodo");
			sb.append(" FROM TM_PERMISO_EXT,PERMISO_USR ");
			sb.append(" WHERE TM_PERMISO_EXT.ESTADO = '1' ");
			sb.append(" AND PERMISO_USR.CUENTA_ID = ").append(cuentaID);
			sb.append(" AND PERMISO_USR.PERMISO_ID = TM_PERMISO_EXT.PERMISO_ID	");
			sb.append(" AND PERMISO_USR.PERMISO_ID = ").append(permisoID);
			
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sb.toString());

			b = rset.next();
		} 
		catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;
		} 

		return b;
	}

	public static UsuarioBean getUserBean(Connection conn, String user) throws CustomException, DBException, Throwable {
		UsuarioBean usuario = null;
		DBConnection dconn = null;
		
		try {
			dconn = new DBConnection(conn);

			System.out.println("getUserBean....");
			DboCuenta cuentaUserI = new DboCuenta(dconn);
			cuentaUserI.setField(DboCuenta.CAMPO_USR_ID, user);

			ArrayList listCuentaUser = cuentaUserI.searchAndRetrieveList();

			//************** CAMBIAR********
			if(listCuentaUser.size() < 1)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Usuario y Password incorrecto", "errorLogon");//Se puso ese descripcion de error para que no envie Mail
	
			if(listCuentaUser.size() > 1)
				throw new CustomException(Constantes.EC_GENERIC_DB_ERROR_INTEGRIDAD, "Existen mas de dos cuentas con el mismo usuario", "errorLogon");
	
			DboCuenta cuentaUser = (DboCuenta) listCuentaUser.get(0);

			// Estado = 0
			if (!cuentaUser.getField(DboCuenta.CAMPO_ESTADO).equals("1"))
				throw new CustomException(Constantes.CUENTA_DESHABILITADA, "Su cuenta de usuario se encuentra inactiva", "errorLogon");
			/************************/
			
			// Estado = 1
			String cuentaId = cuentaUser.getField(DboCuenta.CAMPO_CUENTA_ID);
			String usrId = cuentaUser.getField(DboCuenta.CAMPO_USR_ID);
			String fgNewUsrVent = cuentaUser.getField(DboCuenta.CAMPO_FG_NEW_USR_VENT);
			String peNatuId = cuentaUser.getField(DboCuenta.CAMPO_PE_NATU_ID);
					
			boolean exonPago = cuentaUser.getField(DboCuenta.CAMPO_EXON_PAGO).equals("1")?true:false;
			String tipoUsr = cuentaUser.getField(DboCuenta.CAMPO_TIPO_USR);

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

			if (tipoUsr.substring(0, 1).equals("1")) {
				//if (isTrace(this)) trace("Usuario es Externo: Tipo Usuario : 1XXX", request);
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
				} 
				else
					throw new CustomException(Constantes.NO_SALDO_DE_LINEA_PREPAGO);
			} 
			else {
				// Usuario Interno
				//if (isTrace(this)) trace("Usuario es Interno: Tipo Usuario : 0XXX", request);
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

			if (tipoUsr.substring(1, 2).equals("0")) {
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
				}
				else
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
				}
				else
					usuario.setLineaPrePagoOrganizacion(null);
			} 
			else {
				//if (isTrace(this)) trace("Usuario Es Persona Natural Usuario: X1XX", request);
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
			if (usuario.getFgInterno()==false) {
				DboContrato contrato = new DboContrato(dconn);
				// Persona Natural
				if (tipoUsr.substring(1, 2).equals("1")) {
					contrato.setField(DboContrato.CAMPO_CUENTA_ID, cuentaId);
					contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
				}
				else {
					// Persona Juridica
					contrato.setField(DboContrato.CAMPO_PE_JURI_ID, codOrg);
					contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
				}
				
				if (!contrato.find())
					throw new CustomException(Constantes.NO_REG_CONTRATO);
					
				usuario.setNum_contrato(contrato.getField(DboContrato.CAMPO_CONTRATO_ID));
			} 
			else
				usuario.setNum_contrato(null);
			
			//session.setAttribute("Usuario", usuario);
			//Fin Parte 4: Guardo Datos en Sesion					
		} 
		catch (CustomException e) {
			e.printStackTrace();
			throw e;
		
		} 
		catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;
		
		} 
		catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;
			
		} 

		return usuario;
	}
	
	public static String getSedesSQL (String sedes) {
		StringTokenizer st = null;
		StringBuffer sb = null;
		String elemento = null;
		String[] tokens = null;
		String[] tokens2 = null;
		int cuenta = 0;
		int x = 0;
		
		st = new StringTokenizer(sedes, "*", false);
		sb = new StringBuffer();
		
		sb.append("(");
		cuenta = st.countTokens(); //numero de sedes elegidas
				
		tokens = new String[cuenta];
		tokens2 = new String[cuenta];
		int z=0;
		
		while (st.hasMoreTokens() == true) {
			elemento = st.nextToken();
					
			tokens2[z]=elemento;
					
			//poner en tira SQL
			sb.append("'");
			sb.append(elemento);
			sb.append("'");

			if (x <= (cuenta - 2))
				sb.append(",");
				
			x++;
					
			//poner en arreglo
			tokens[z]=elemento;
					
			z++;
		}
		
		sb.append(")");
		
		return sb.toString();
	}

	public static Serializable getConsultaAnterior (Connection conn, String cuo, String tipoDato) throws Exception {
		BLOB campoBlob = null;
		ResultSet rs = null;
		Statement stmt = null;
		StringBuffer sql = new StringBuffer();
		Serializable lista = null;

		sql.append("SELECT VALOR FROM DATA_SESSION WHERE ");
		sql.append("SESSION_ID = '").append(cuo).append("' ");
		sql.append("AND NOMBRE = '").append(tipoDato).append("'");
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql.toString());
		
		if (rs.next()) {
			campoBlob = ((OracleResultSet)rs).getBLOB("VALOR");
			lista = (Serializable)Serializacion.getInstance().deserializar(campoBlob.binaryStreamValue());
		}

		JDBC.getInstance().closeResultSet(rs);
		JDBC.getInstance().closeStatement(stmt);
		
		return lista;
	}
	
	public static void eliminaConsultaAnterior (Connection conn, String cuo, String tipoDato) throws Exception  {
		BLOB campoBlob = null;
		ResultSet rs = null;
		Statement stmt = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append("DELETE FROM DATA_SESSION WHERE ");
		sql.append("SESSION_ID = '").append(cuo).append("' AND ");
		sql.append("NOMBRE = '").append(tipoDato).append("'");

		stmt = conn.createStatement();

		stmt.executeUpdate(sql.toString());
		conn.commit();
	}
	
	public static void insertaConsulta (String cuo, String tipoDato, Serializable obj) throws Exception {
		DBConnectionFactory pool = null;
		Connection conn = null;
		BLOB campoBlob = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = null;

		pool = DBConnectionFactory.getInstance();
		conn = pool.getConnection();
		conn.setAutoCommit(false);
		
		eliminaConsultaAnterior(conn, cuo, tipoDato);
		
		System.out.println("---> CUO: " + cuo);
		System.out.println("---> NOMBRE: " + tipoDato);
		
		sql = new StringBuffer();
		sql.append("INSERT INTO DATA_SESSION(SESSION_ID, NOMBRE, TS_CREACION, VALOR) VALUES('").append(cuo).append("', '").append(tipoDato).append("', SYSDATE, empty_blob())");
		stmt = conn.createStatement();
		
		stmt.executeUpdate(sql.toString());
		conn.commit();

		sql = new StringBuffer();
		sql.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(cuo).append("' AND NOMBRE ='").append(tipoDato).append("' FOR UPDATE");
		stmt = conn.createStatement();
		
		rs = stmt.executeQuery(sql.toString());

		rs.next();		
		campoBlob = ((oracle.jdbc.driver.OracleResultSet)rs).getBLOB("VALOR");
		
		OutputStream outstream = campoBlob.getBinaryOutputStream();
		int j = -1;
		int bSize = campoBlob.getBufferSize(); // buffer del campo blob

		byte buffer[] = new byte[bSize];

		InputStream instream = Serializacion.getInstance().serializarAStream(obj);
		j = instream.read(buffer);

		while (j != -1) {
			outstream.write(buffer, 0, j);
			j = instream.read(buffer);
		}

		outstream.close();
		instream.close();
		conn.commit();
	}
}

