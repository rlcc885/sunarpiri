package gob.pe.sunarp.extranet.solicitud.inscripcion;

import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.transaction.bean.*;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.webservices.ServiceProvider;
import gob.pe.sunarp.extranet.framework.*;
import java.sql.*;
import com.jcorporate.expresso.core.db.*;
import gob.pe.sunarp.extranet.dbobj.*;
import java.util.ArrayList;
import gob.pe.sunarp.extranet.reportegeneral.pool.*;
import java.io.*;
import java.util.*;
import javax.naming.*;
import gob.pe.sunarp.extranet.common.*;
import gob.pe.sunarp.extranet.reportegeneral.bean.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.*;

// PROYECTO DE INTEGRACION SUNARP RENIEC
// 07/02/2007 JOSE LLANOS
// llamada de web service para validacion de datos
// ------inicio---------
import gob.pe.sunarp.extranet.reniec.bo.ValidacionIdentidadBo;
// ------fin---------

public class PresentacionSolicitudInscripcion{

	private String fechaActualSolicitudPresentacion;
	private String numeroHojaSolicitudPresentacion;
	private String cuoSolicitudPresentacion;

	public int procesarSolicitud(SolicitudInscripcion solicitudInscripcion, String rtfFile, UsuarioBean usuario){
		int respuesta = 7;//ERROR GENERAL

		double pagoMinimo = 0;
		boolean flag = false;

		try {
			System.out.println("procesarSolicitud.....");
			/**************** 1. VALIDA PERMISO *****************/
			flag = validaPermisoUsuario(usuario);
			if (flag==false) {
				return Errors.WS_USER_NOT_ALLOWED;//USUARIO NO TIENE PERMISO
			}

			/**************** 2. COSTO > PAGO MINIMO ************/
			pagoMinimo = getPagoMinimo();
			System.out.println("pagoMinimo::"+pagoMinimo);
			if (pagoMinimo > solicitudInscripcion.getDatosPago().getCostoTotalServicio().doubleValue()) {
				return Errors.WS_INSUFFICENT_COST;//COSTO SOLICITUD < PAGO MINIMO
			}

			//COMPLETAMOS EL ANHO Y NUMERO DE HOJA
			solicitudInscripcion.setAnho( (FechaUtil.getCurrentDateYYYYMMDD()).substring(0,4) );
			solicitudInscripcion.setNumeroHoja(SecuenciaSolicitud.obtieneSecuencia());

		
			
			/*************** TRANSACCION :3, 4, 5 Y 6 **********/
			respuesta = realizaOperacion(solicitudInscripcion, rtfFile, usuario);

			/**
			 * Se actualizan guardan los datos generados para poder ser enviados como respuesta
			 */
			setCuoSolicitudPresentacion(solicitudInscripcion.getCuo());
			//setFechaActualSolicitudPresentacion(solicitudInscripcion.getFechaSolicitud());
			setFechaActualSolicitudPresentacion(solicitudInscripcion.getAnho());
			setNumeroHojaSolicitudPresentacion(solicitudInscripcion.getNumeroHoja());
			

		} catch (DBException dbe) {
			respuesta = Errors.WS_BD_EXTRANET_ERROR;//ERROR ACCEDIENDO A BD EXTRANET

		} catch (CustomException ce) {
			respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAL

		} catch (Throwable ex) {
			respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAL

		}

		return respuesta;

	}


	private synchronized int realizaOperacion(SolicitudInscripcion solicitudInscripcion,
								  			  String rtfFile,
								  			  UsuarioBean usuario){

		int respuesta = 7;//ERROR GENERAL
		boolean flag2 = false;
		LogAuditoriaSolicitudInscripcionBean bt = null;
		String consumoId = null;
		DatosAdicionales datosAdicionales = null;

		DBConnectionFactory pool = null;
		Connection conn1 = null;
		DBConnection myConn1 = null;

		//DBConnectionFactories pools = null;
		Connection conn2 = null;
		DBConnection myConn2 = null;

		OficinaConection oficDB = null;

		try {


				/***** FLAG DE REPLICACION ********/
				InitialContext ictx = new InitialContext();
				Context myenv = (Context)ictx.lookup("java:comp/env");
				Integer valorReplicacion = (Integer)myenv.lookup("valorReplicacion");
				System.out.println("Valor de Replicacion:-------->"+valorReplicacion);
				/***********************************/
				
				System.out.println("Obteniendo pool de conexiones a la Bodega Central....");
				//HACIA LA BODEGA CENTRAL
				pool = DBConnectionFactory.getInstance();
				conn1 = pool.getConnection();
				conn1.setAutoCommit(false);
				myConn1 = new DBConnection(conn1);
				valorReplicacion = 1;

				//if(valorReplicacion.doubleValue()==1) {
				
				System.out.println("Obteniendo conexion a la oficina.....");
				//HACIA LA OFICINA
				oficDB = (OficinaConection) ConsolaCentral.getInstance().getDbOficinas().get(solicitudInscripcion.getCodigoZonaRegistral()+solicitudInscripcion.getCodigoOficinaRegistral());
				System.out.println("Conexión de la oficina Central::"+oficDB);
				Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn2 = DriverManager.getConnection(oficDB.getUrl(), oficDB.getUser(), oficDB.getPassword());
				conn2.setAutoCommit(false);
				myConn2 = new DBConnection(conn2);
				//}
				
				
				
				/**************** 3. FORMA PAGO *********************/
				if ( (solicitudInscripcion.getDatosPago().getCodigoFormaPago().equals("01")) ||
					 (solicitudInscripcion.getDatosPago().getCodigoFormaPago().equals("02")) ||   //TARJETA DE CREDITO
					 (solicitudInscripcion.getDatosPago().getCodigoFormaPago().equals("03")) )  { //DESCUENTO DE SALDO
					
					System.out.println("Procesando descontar saldo.....");
					bt = new LogAuditoriaSolicitudInscripcionBean();
					bt.setUsuarioSession(usuario);
					bt.setCodigoGLA(Constantes.COD_GLA_SOLINSCR);//PONER CONSTANTE
					bt.setCodigoServicio(Constantes.COD_SERVICIO_SOLINSCR);//PONER CONSTANTE
					bt.setFlagCobro("1");////
					/**
					 * Inicio:mgarate:21/11/2007 - PCM 
					**/
					bt.setRemoteAddr(solicitudInscripcion.getIpRemota());
					bt.setCodigoTipoPago(solicitudInscripcion.getDatosPago().getCodigoFormaPago());
					bt.setCostoTotal(solicitudInscripcion.getDatosPago().getCostoTotalServicio().doubleValue());
					//Fin:mgarate - PCM
					//Descuento de Saldo
					if (Propiedades.getInstance().getFlagTransaccion()==true)
						Transaction.getInstance().registraTransaccion(bt,conn1);
					consumoId = bt.getConsumoId();
					/**
					 * Inicio:mgarate:20/11/2007 - PCM
					**/
					if(solicitudInscripcion.getDatosPago().getCodigoFormaPago().equals("03"))
					{
						solicitudInscripcion.getDatosPago().setNumeroOperacion(""+bt.getNumeroOperacion());
					}
					//Fin:mgarate:20/11/2007 - PCM
					System.out.println("consumoId::"+consumoId);
					System.out.println("transId/numeroOperacion::"+solicitudInscripcion.getDatosPago().getNumeroOperacion());
				
				}
				else {
					return Errors.WS_TYPE_OF_PAYMENT_NOT_AVAILABLE; //FORMA DE PAGO INCORRECTA O NO DISPONIBLE
				}

				
				/**
				 *  PROYECTO DE INTEGRACION SUNARP - RENIEC
				 *  07/02/2007 JOSE LLANOS
				 *  llamada de web service para validacion de datos
				 *  ------inicio---------
				 */
 				String respuestaWS = "";
				ValidacionIdentidadBo validacionIdentidadBo = new ValidacionIdentidadBo(); 
				
				/*try{
					respuestaWS = validacionIdentidadBo.validarIdentidad(solicitudInscripcion);
					System.out.println("RESPUESTA RENIEC: " + respuestaWS);
				} catch (Exception ex) {
					System.out.println(ex);
					return Errors.WS_VALIDACION_RENIEC_ERROR;//VALIDACION_RENIEC
				}
				
				if (!respuestaWS.equals("01")) return Errors.WS_VALIDACION_RENIEC_ERROR;//VALIDACION_RENIEC				
					if (respuestaWS.equals("") || respuestaWS==null) return Errors.WS_VALIDACION_RENIEC_ERROR;//VALIDACION_RENIEC
				//-------fin-------------

				/**************** 4.0 Obtiene Datos adicionales *******/
				datosAdicionales = obtieneDatosAdicionales(consumoId, myConn1);

				/**************** 4.1 REGISTRA EXTRANET *********/
				try {

					registraSolicitud(solicitudInscripcion, datosAdicionales, rtfFile, conn1, myConn1);

				} catch (DBException dbe) {
					dbe.printStackTrace();
					try {
						myConn1.rollback();
						if (myConn2!=null)
							myConn2.rollback();
					}
					catch(DBException dbe1){}
					return Errors.WS_BD_EXTRANET_ERROR;//ERROR ACCEDIENDO A BD EXTRANET

				} catch (Throwable ex) {
					ex.printStackTrace();
					try {
						myConn1.rollback();
						if (myConn2!=null)
							myConn2.rollback();
					}
					catch(DBException dbe1){}
					return Errors.WS_BD_SIR_ERROR;//ERROR GENERAL
				}

				/**************** 4.2 REGISTRA OFICINA *********/
				//if(valorReplicacion.doubleValue()==1) {
				try 
				{
					System.out.println("INGRESANDO EN LA OFICINA DE ZONA IX ");
					registraSolicitud(solicitudInscripcion, datosAdicionales, rtfFile, conn2, myConn2);

				} catch (DBException dbe) {
					dbe.printStackTrace();
					try {
						myConn1.rollback();
						myConn2.rollback();
					}
					catch(DBException dbe1){}
					return Errors.WS_BD_SIR_ERROR;//ERROR ACCEDIENDO A BD SIR

				} catch (Throwable ex) {
					ex.printStackTrace();
					try {
						myConn1.rollback();
						myConn2.rollback();
					}
					catch(DBException dbe1){}
					return Errors.WS_GENERAL_ERROR;//ERROR GENERAL
				}
				//}
				
				/**************** 5. ACTUALIZA ESTADO OPERACION *******/
				flag2 = actualizaOperacion(solicitudInscripcion,conn1,myConn1);
				if (flag2 == false) {
					try {
						myConn1.rollback();
						if (myConn2!=null)
							myConn2.rollback();
					}
					catch(DBException dbe1){}
					respuesta = Errors.WS_BD_EXTRANET_ERROR;//ERROR ACCEDIENDO A BD EXTRANET
				}

				/**************** 6. commit ****************************/
				respuesta = 0;//EXITO

				myConn1.commit();

				if(valorReplicacion.doubleValue()==1) {
					myConn2.commit();
				}


		} catch (SQLException sqe) {
			sqe.printStackTrace();
			try {
				myConn1.rollback();
				myConn2.rollback();
			}
			catch(DBException dbe1){}
			catch(Throwable tr1){}
			respuesta = Errors.WS_BD_SIR_ERROR;//ERROR ACCEDIENDO A BD SIR

		} catch (DBException dbe) {
			dbe.printStackTrace();
			try {
				myConn1.rollback();
				myConn2.rollback();
			}
			catch(DBException dbe1){}
			catch(Throwable tr1){}
			respuesta = Errors.WS_BD_EXTRANET_ERROR;//ERROR ACCEDIENDO A BD EXTRANET

		} catch (CustomException ce) {
			ce.printStackTrace();
			try {
				myConn1.rollback();
				myConn2.rollback();
			}
			catch(DBException dbe1){}
			catch(Throwable tr1){}
			if (ce.getCodigoError().equals(Constantes.E70001_SALDO_INSUFICIENTE))
				respuesta = Errors.WS_INSUFFICENT_COST;//SALDO INSUFICIENTE
			else
				respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAL

		} catch (Throwable ex) {
			ex.printStackTrace();
			try {
				myConn1.rollback();
				myConn2.rollback();
			}
			catch(DBException dbe1){}
			catch(Throwable tr1){}
			respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAL
		}
		finally {
			try{
				if (pool!=null)
					pool.release(conn1);
				if (myConn2!=null) {
					myConn2.disconnect();
				}
			}
			catch (Throwable t){}
		}

		return respuesta;

	}



	private boolean validaPermisoUsuario(UsuarioBean usuario) throws DBException, Throwable {

		DBConnectionFactory pool = null;
		Connection conn = null;
		boolean b = false;
		Statement stmt = null;
		ResultSet rset = null;
		StringBuffer sb = null;

		try {

			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit(false);

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
			sb.append(" AND PERMISO_USR.CUENTA_ID = ").append(usuario.getCuentaId());
			sb.append(" AND PERMISO_USR.PERMISO_ID = TM_PERMISO_EXT.PERMISO_ID	");
			sb.append(" AND PERMISO_USR.PERMISO_ID = ").append(Constantes.SOLINSCRP_PERMISO_ID);

			stmt = conn.createStatement();
			rset = stmt.executeQuery(sb.toString());

			b = rset.next();

		} catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			try{
				pool.release(conn);
			}
			catch (Throwable t)
			{
				throw t;
			}
		}

		return b;
		

	}

	private double getPagoMinimo() throws CustomException, DBException, Throwable	{

		DBConnectionFactory pool = null;
		Connection conn = null;
		DBConnection myConn = null;
		DboTarifa tarifa = null;
		int codServicio, codGLA;

		try {

			pool = DBConnectionFactory.getInstance();

			conn = pool.getConnection();
		   	myConn = new DBConnection(conn);

		    codServicio= Constantes.COD_SERVICIO_SOLINSCR;//revisar codigos
			//Tarifario
			codGLA= Constantes.COD_GLA_SOLINSCR;//revisar codigos

			tarifa = new DboTarifa(myConn);
			tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicio);
			//Tarifario
			tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codGLA);

			if(!tarifa.find())
				throw new CustomException("No existe servicio con codigo '"+ codServicio + "' y GLA '"+ codGLA + "' en tabla TARIFA");

		} catch (DBException dbe) {
			throw dbe;

		} catch (CustomException ce) {
			throw ce;

		} catch (Throwable ex) {
			throw ex;

		} finally {
			try{
				pool.release(conn);
			}
			catch (Throwable t)
			{
				throw t;
			}
		}

		return Double.parseDouble(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));

	}


	/**
	 * @param solicitudInscripcion
	 * @param datosAdicionales
	 * @param rtfFile
	 * @param conn
	 * @param myConn
	 * @throws DBException
	 * @throws Throwable
	 */
	private void registraSolicitud(SolicitudInscripcion solicitudInscripcion,
											    DatosAdicionales datosAdicionales,
											    String rtfFile,
											    Connection conn,
											    DBConnection myConn) throws DBException, Throwable {

		try {

		   	registraSolicitudBD(solicitudInscripcion, datosAdicionales, myConn);
		   	/**** luego quitar comentario ***********/
		   	registraRtfBD(solicitudInscripcion, rtfFile, conn);
		   	/*****************************************/

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			throw new DBException(sqe);

		} catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		}
	}

	private void registraRtfBD(SolicitudInscripcion solicitudInscripcion, String rtfFile, Connection conn) throws SQLException, Throwable {

		File archivo = null;
		String sentenciaSql = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("INICIO RTF...");

			archivo = generaArchivo(rtfFile);

			sentenciaSql = "INSERT INTO TT_DOCU_TRANS(CO_REGI_PRES, CO_OFIC_RGST_PRES, AA_HOJA_PRES, "+
								  					 "NU_HOJA_PRES, AA_TITU, NU_TITU, CO_REGI, CO_OFIC_RGST, "+
								  					 "CN_DOCU,NS_DOCU_TRANS) VALUES(?,?,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sentenciaSql);
			pstmt.setString(1,solicitudInscripcion.getCodigoZonaRegistral());
			pstmt.setString(2,solicitudInscripcion.getCodigoOficinaRegistral());
			pstmt.setString(3,solicitudInscripcion.getAnho());
			pstmt.setString(4,solicitudInscripcion.getNumeroHoja());
			pstmt.setString(5,null);
			pstmt.setString(6,null);
			pstmt.setString(7,null);
			pstmt.setString(8,null);
			pstmt.setBinaryStream(9,new FileInputStream(archivo),(int)archivo.length());
			pstmt.setString(10,"1");

			pstmt.execute(); // Execute the statement

			System.out.println("FIN RTF...");
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			throw sqe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			if (pstmt!=null)
				try{
					pstmt.close();
				}
				catch (Throwable t)
				{}
		}

	}


	private boolean actualizaOperacion(SolicitudInscripcion solicitudInscripcion, Connection conn1, DBConnection myConn) throws DBException, Throwable {

		int sec;
		String sentenciaSql = null;
		PreparedStatement pstmt = null;
		int res;

		try {


			//ACTUALIZAMOS EL ESTADO DE LA OPERACION
			sentenciaSql = "UPDATE OPERACION SET OPERACION.ESTADO = ? "+
						   "WHERE OPERACION.SECUENCIA = ? ";
			pstmt = conn1.prepareStatement(sentenciaSql);
			pstmt.setString(1,"0"); // ESTADO PROCESADO
			pstmt.setString(2,solicitudInscripcion.getSecuencialOperacion());

			res = pstmt.executeUpdate(); // Execute the statement

			if (res < 0)
				return false;

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			throw sqe;

		} catch (Throwable ex) {
			throw ex;

		}

		return true;

	}


	private DatosAdicionales obtieneDatosAdicionales(String consumoId,
	 									 			 DBConnection myConn) throws DBException, Throwable {

		DatosAdicionales datosAdicionales = null;

		try {

			System.out.println("Consultando CONSUMO");
			DboConsumo dboConsumo = new  DboConsumo(myConn);
			dboConsumo.setFieldsToRetrieve(DboConsumo.CAMPO_MONTO + "|" +
										   DboConsumo.CAMPO_MOVIMIENTO_ID);
			dboConsumo.setField(DboConsumo.CAMPO_CONSUMO_ID, consumoId);
			String monto = null;
			String movimientoId = null;
			if (dboConsumo.find() == true) {
				monto = dboConsumo.getField(DboConsumo.CAMPO_MONTO);
				movimientoId = dboConsumo.getField(DboConsumo.CAMPO_MOVIMIENTO_ID);
				System.out.println("monto::"+monto);
				System.out.println("movimientoId::"+movimientoId);
			}

			System.out.println("Consultando MOVIMIENTO");
			DboMovimiento dboMovimiento = new DboMovimiento(myConn);
			dboMovimiento.setFieldsToRetrieve(DboMovimiento.CAMPO_FEC_HOR + "|" +
										      DboMovimiento.CAMPO_LINEA_PREPAGO_ID);
		    dboMovimiento.setField(DboMovimiento.CAMPO_MOVIMIENTO_ID, movimientoId);
		    String fecHor = null;
		    String lineaPrePagoId = null;
		    if (dboMovimiento.find() == true) {
				fecHor = dboMovimiento.getField(DboMovimiento.CAMPO_FEC_HOR);
				lineaPrePagoId = dboMovimiento.getField(DboMovimiento.CAMPO_LINEA_PREPAGO_ID);
				System.out.println("fecHor::"+fecHor);
				System.out.println("lineaPrePagoId::"+lineaPrePagoId);
			}

			System.out.println("Consultando LINEAPREPAGO");
			DboLineaPrepago dboLineaPrePago = new DboLineaPrepago(myConn);
			dboLineaPrePago.setFieldsToRetrieve(DboLineaPrepago.CAMPO_CUENTA_ID);
			dboLineaPrePago.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, lineaPrePagoId);
			String cuentaId = null;
			if (dboLineaPrePago.find() == true) {
				cuentaId = dboLineaPrePago.getField(DboLineaPrepago.CAMPO_CUENTA_ID);
				System.out.println("cuentaId::"+cuentaId);
			}

			datosAdicionales = new DatosAdicionales();
			datosAdicionales.setConsumoId(consumoId);
			datosAdicionales.setCuentaId(cuentaId);
			datosAdicionales.setFecHor(fecHor);
			datosAdicionales.setLineaPrePagoId(lineaPrePagoId);
			datosAdicionales.setMonto(monto);
			datosAdicionales.setMovimientoId(movimientoId);


		} catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		}

		return datosAdicionales;

	}


	private void registraSolicitudBD(SolicitudInscripcion solicitudInscripcion,
									 DatosAdicionales datosAdicionales,
									 DBConnection myConn) throws DBException, Throwable {
	/** JBUGARIN 20/09/06 INICIO**/

		try {

			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_SOLINSCR) {

				registraSolicitudBDConstitucionEmpresa(solicitudInscripcion,datosAdicionales, myConn );
				System.out.println("SERVICIO----CONSTITUCION DE EMPRESA----");

			}
			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_RESERVANOMBRE) {

				registraSolicitudBDReservaNombre(solicitudInscripcion,datosAdicionales, myConn );
				System.out.println("SERVICIO---RESERVA DE NOMBRE---");
			}

			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_BLOQUEOINMUEBLE) {

				registraSolicitudBDBloqueoInmueble(solicitudInscripcion,datosAdicionales, myConn );
				System.out.println("SERVICIO---BLOQUEO DE INMUEBLE---");
			}

			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_TRANSFVEHICULAR) {

				registraSolicitudBDTransfVehicular(solicitudInscripcion,datosAdicionales, myConn );
				System.out.println("SERVICIO---TRANSFERENCIA VEHICULAR---");
			}




		} catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		}

	}

	/** JBUGARIN 20/09/06 FIN **/

	private void registraSolicitudBDConstitucionEmpresaOficina(SolicitudInscripcion solicitudInscripcion,
			DatosAdicionales datosAdicionales,
            Connection myConn) throws DBException, Throwable 
    {
		StringBuffer q = new StringBuffer();
		PreparedStatement pstmt = null;
		try
		{
			
			q.append("");
			pstmt = myConn.prepareStatement(q.toString());
			pstmt.executeQuery();
			
			
			
			
		
		}catch (Exception dbe) {
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		}

		
	}
	
	private void registraSolicitudBDConstitucionEmpresa(SolicitudInscripcion solicitudInscripcion,
														DatosAdicionales datosAdicionales,
									                    DBConnection myConn) throws DBException, Throwable {

		String dia, mes, anho, hh, mm, ss;

		try {
			System.out.println("Insertando en TP_HOJA_PRES");
			//TP_HOJA_PRES
			DboTPHojaPres tablaTPHojaPres = new DboTPHojaPres();
			tablaTPHojaPres.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_AREA, solicitudInscripcion.getCodigoArea());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_AREA, solicitudInscripcion.getDescripcionArea());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUO, solicitudInscripcion.getCuo());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_SERVICIO_ID, solicitudInscripcion.getCodigoServicio());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_SERVICIO, solicitudInscripcion.getDescripcionServicio());
			//viene de otro
			//tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_IN_RUC, solicitudInscripcion.getIndicadorRUC());
			/** presentante*/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUARIO, solicitudInscripcion.getCodigoUsuario());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_PERS_AUTZ_PRES, solicitudInscripcion.getPresentante().getCodigoPresentante());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_PRES, solicitudInscripcion.getPresentante().getApellidoPaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_PRES, solicitudInscripcion.getPresentante().getApellidoMaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_PRES, solicitudInscripcion.getPresentante().getNombre());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUR, solicitudInscripcion.getPresentante().getCodigoInstitucion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_CUR, solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN, solicitudInscripcion.getPresentante().getCodigoTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN, solicitudInscripcion.getPresentante().getDescripcionTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU, solicitudInscripcion.getPresentante().getNumeroDocumento());
			/***jbugarin 13/09/06 ***/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_UB_GEOG_PRES, solicitudInscripcion.getPresentante().getCodigoPais()+solicitudInscripcion.getPresentante().getCodigoDepartamento()+solicitudInscripcion.getPresentante().getCodigoProvincia()+solicitudInscripcion.getPresentante().getCodigoDistrito()); //CONCATENACION VERIFICAR
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_VIA_PRES, solicitudInscripcion.getPresentante().getCodigoTipoVia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_VIA_PRES, solicitudInscripcion.getPresentante().getDescripcionTipoVia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_VIA_PRES, solicitudInscripcion.getPresentante().getDireccion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_MAIL_PRES, solicitudInscripcion.getPresentante().getCorreoElectronico());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DEPARTAMENTO_PRES, solicitudInscripcion.getPresentante().getDescripcionDepartamento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_PROVINCIA_PRES, solicitudInscripcion.getPresentante().getDescripcionProvincia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DISTRITO_PRES, solicitudInscripcion.getPresentante().getDescripcionDistrito());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_POSTAL_PRES, solicitudInscripcion.getPresentante().getCodigoPostal());
			
			/*** MGARATE 25/04/2008 ******/
			
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_USR_VERIF, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_VERIF, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RPTA_VERIF, null);
			
			/*** MGARATE 25/04/2008 ******/
			
			/***jbugarin 13/09/06 ***/

			/***jbugarin 13/09/06 ***/
			/** personaJuridica**/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_IN_RUC, solicitudInscripcion.getPersonaJuridica().getIndicadorRUC());
			/***jbugarin 13/09/06 ***/

			/** interesado ya no existe jbugarin 13/09/06*/
			/*tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_REPR, solicitudInscripcion.getInteresado().getApellidoPaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_REPR, solicitudInscripcion.getInteresado().getApellidoMaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_REPR, solicitudInscripcion.getInteresado().getNombre());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN_REPR, solicitudInscripcion.getInteresado().getCodigoTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN_REPR, solicitudInscripcion.getInteresado().getDescripcionTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU_REPR, solicitudInscripcion.getInteresado().getNumeroDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_ELECT_REPR, solicitudInscripcion.getInteresado().getCorreoElectronico());
			*/

			/** **/
			
			dia = solicitudInscripcion.getDatosPago().getFechaPago().substring(0,2);
			mes = solicitudInscripcion.getDatosPago().getFechaPago().substring(3,5);
			anho = solicitudInscripcion.getDatosPago().getFechaPago().substring(6,10);
			
			System.out.println("Fecha de Pago - dia::"+dia);			
			System.out.println("Fecha de Pago - mes::"+mes);
			System.out.println("Fecha de Pago - anho::"+anho);
			
			hh = solicitudInscripcion.getDatosPago().getHoraPago().substring(0,2);
			mm = solicitudInscripcion.getDatosPago().getHoraPago().substring(3,5);
			ss = solicitudInscripcion.getDatosPago().getHoraPago().substring(6,8);
			
			System.out.println("Hora de Pago::"+solicitudInscripcion.getDatosPago().getHoraPago());
			System.out.println("Hora de Pago - hora::"+hh);			
			System.out.println("Hora de Pago - minutos::"+mm);
			System.out.println("Hora de Pago - segundos::"+ss);			
			
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_HOJA_PRES,FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_DEFI, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_USUA_CREA, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUA_CREA, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_OBSE, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_SITU_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_ANOT, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_FORM_REG, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RZ_SOCL_REPR, null);
			/***jbugarin 13/09/06 ***/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_ELECT_REPR, null);
			/***jbugarin 13/09/06 ***/
			tablaTPHojaPres.add();

			//TA_TITU_ACTO
			System.out.println("Insertando en TA_TITU_ACTO");
			DboTATituActo tablaTATituActo = new DboTATituActo();
			tablaTATituActo.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTATituActo.setField(DboTATituActo.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
			tablaTATituActo.setField(DboTATituActo.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_LIBR, solicitudInscripcion.getCodigoLibro());
			tablaTATituActo.setField(DboTATituActo.CAMPO_DE_LIBR, solicitudInscripcion.getDescripcionLibro());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/***  */
			tablaTATituActo.setField(DboTATituActo.CAMPO_NS_AFEC, 1);////////////////////
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_ESTD, Constantes.CAMPO_IN_ESTD);
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_PART, Constantes.CAMPO_NU_PART);
			tablaTATituActo.setField(DboTATituActo.CAMPO_AA_TITU, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_TITU, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_MO_TOTA_ACTO, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_EXON, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_PO_EXON, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_CREA, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_CREA, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_MODI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_MODI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_RUBR, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_RESE, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_GENE_ASIE, null);
			tablaTATituActo.add();

			//TT_PERS_JURI_TITU
			System.out.println("Insertando en TT_PERS_JURI_TITU");
			DboTTPersJuriTitu tablaTTPersJuriTitu = new DboTTPersJuriTitu();
			tablaTTPersJuriTitu.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** personaJuridica*/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, solicitudInscripcion.getPersonaJuridica().getRazonSocial());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, solicitudInscripcion.getPersonaJuridica().getSiglas());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, "000");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoParticipante());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedad());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_SOCI, solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedadAnonima());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_SOCI, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedadAnonima());
			/*** capital*/ //JBUGARIN 13/09/06
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_MONE, solicitudInscripcion.getCapital().getCodigoMoneda());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_MONE, solicitudInscripcion.getCapital().getDescripcionMoneda());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MO_TOTA, solicitudInscripcion.getCapital().getMontoCapital().toString());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_VA_ACCN, solicitudInscripcion.getCapital().getValor().toString());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_SI_CAPI, solicitudInscripcion.getCapital().getCodigoCancelacionCapital());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SI_CAPI, solicitudInscripcion.getCapital().getDescripcionCancelacionCapital());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC,"N1");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART,solicitudInscripcion.getCodigoLibro());
			if (solicitudInscripcion.getCapital().getPorcentajeCancelado()!=null)
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_PO_PAGO_CANC, solicitudInscripcion.getCapital().getPorcentajeCancelado().toString());
			else
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_PO_PAGO_CANC, null);
			/*** reservaMercantil*/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU_ASOC, solicitudInscripcion.getReservaMercantil().getAnhoTitulo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU_ASOC, solicitudInscripcion.getReservaMercantil().getNumeroTitulo());
			
			/***  */
			
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);///////////////////
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_CIIU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, null);
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, "1");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_ACCI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TS_USUA_CREA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_USUA_CREA, null);
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
			/** JBUGARIN 13/09/06 **/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MAIL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, null);
			/** JBUGARIN 13/09/06 **/
			/** gochoa **/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
			/************/
			tablaTTPersJuriTitu.add();

			ArrayList listaParticipantesPJ = (ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
			PersonaJuridica personaJuridica = null;
			int size = 0;
			if (listaParticipantesPJ!=null) {
				size = listaParticipantesPJ.size();
				for (int i=0; i<size; i++){
					personaJuridica = (PersonaJuridica)listaParticipantesPJ.get(i);
					/*** solicitudInscripcion*/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** participantesPersonaJuridica*/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, personaJuridica.getCodigoTipoDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, personaJuridica.getDescripcionTipoDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, personaJuridica.getNumeroDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, i+2);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, personaJuridica.getCodigoTipoParticipante());
					/**
					 * 
					 * SE AGREGO EL CAMPO CAMPO_TI_PRTC_SUNAT - REQUERIMIENTO DE SUNAT
					 * SAUL VASQUEZ - AVATAR GLOBAL 
					 */
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC_SUNAT, personaJuridica.getCodigoTipoParticipantePJSUNAT());								
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, personaJuridica.getDescripcionTipoParticipante());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, personaJuridica.getCodigoNacionalidad());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, personaJuridica.getDescripcionNacionalidad());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, personaJuridica.getRazonSocial());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, personaJuridica.getSiglas());
					//JBUGARIN 13/09/06
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, personaJuridica.getNumeroPartida());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, personaJuridica.getCodigoZonaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, personaJuridica.getCodigoOficinaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, personaJuridica.getCodigoTipoVia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, personaJuridica.getDescripcionTipoVia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, personaJuridica.getDireccion());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, personaJuridica.getCodigoPostal());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, personaJuridica.getCodigoPais()+personaJuridica.getCodigoDepartamento()+personaJuridica.getCodigoProvincia()+personaJuridica.getCodigoDistrito());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, personaJuridica.getDescripcionDepartamento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, personaJuridica.getDescripcionProvincia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, personaJuridica.getDescripcionDistrito());
					//JBUGARIN 13/09/06

					/***  */
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);//////////////////////////////
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_SOCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_SOCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_MONE, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_MONE, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MO_TOTA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_VA_ACCN, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_SI_CAPI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SI_CAPI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_PO_PAGO_CANC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_CIIU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_ACCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_USUA_CREA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
					/** JBUGARIN 13/09/06 **/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MAIL, null);
					/** JBUGARIN 13/09/06 **/

					/**
					 * SE ADICIONAN LOS CAMPOS: 
					 * 
					 * - indicadorRepresentante, 
					 * - numeroPartidaEmpresa
					 * 
					 * SAUL VASQUEZ - AVATAR GLOBAL
					 */			
					//SAUL
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_IND_RRLL_PARTIC, personaJuridica.getIndicadorRepresentante());
					
					tablaTTPersJuriTitu.add();


				}
			}

			//TT_PERS_NATU_TITU
			System.out.println("Insertando en TT_PERS_NATU_TITU");
			DboTTPersNatuTitu tablaTTPersNatuTitu = new DboTTPersNatuTitu();
			tablaTTPersNatuTitu.setConnection(myConn);
			ArrayList listaParticipantesPN = (ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
			PersonaNatural personaNatural = null;
			if (listaParticipantesPN!=null) {
				size = listaParticipantesPN.size();
				for (int i=0; i<size; i++){
					personaNatural = (PersonaNatural)listaParticipantesPN.get(i);

					/*** solicitudInscripcion*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** participantesPersonaNatural*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_PERS_NATU, i+1);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_DOCU_IDEN, personaNatural.getCodigoTipoDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_DOCU_IDEN, personaNatural.getDescripcionTipoDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_DOCU, personaNatural.getNumeroDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_CIVL, personaNatural.getCodigoEstadoCivil());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ES_CIVL, personaNatural.getDescripcionEstadoCivil());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU, personaNatural.getApellidoPaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU, personaNatural.getApellidoMaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_PERS_NATU, personaNatural.getNombre());
					// MODIFICADO POR G.O.V
					/**
					 * Modificado por SVR - AVATAR SAC - FIN
					 */
					//if (solicitudInscripcion.getPersonaJuridica().getIndicadorRUC().equals(Constantes.INDICADORRUCXML)) {
						//dia = personaNatural.getFechaNacimiento().substring(6,8);
						//mes = personaNatural.getFechaNacimiento().substring(4,6);
						//anho = personaNatural.getFechaNacimiento().substring(0,4);
						//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_NACI, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					//} else {
					
					dia = personaNatural.getFechaNacimiento().substring(0,2);
					mes = personaNatural.getFechaNacimiento().substring(3,5);
					anho = personaNatural.getFechaNacimiento().substring(6,10);
				
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_NACI, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" 00:00:00"));
					//}
					///
					/**
					 * Modificado por SVR - AVATAR SAC - INI
					 */
						
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC, personaNatural.getCodigoTipoParticipante());
					/**
					 * 
					 * SE AGREGO EL CAMPO CAMPO_TI_PRTC_SUNAT - REQUERIMIENTO DE SUNAT
					 * SAUL VASQUEZ - AVATAR GLOBAL 
					 * 
					 */					
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC_SUNAT, personaNatural.getCodigoTipoParticipantePNSUNAT());		
					
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_PRTC, personaNatural.getDescripcionTipoParticipante());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_PAIS, personaNatural.getCodigoNacionalidad());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_NCNL, personaNatural.getDescripcionNacionalidad());
					/** JBUGARIN  13/09/06**/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_UB_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DEPARTAMENTO_PRES, personaNatural.getDescripcionDepartamento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_PROVINCIA_PRES, personaNatural.getDescripcionProvincia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DISTRITO_PRES, personaNatural.getDescripcionDistrito());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_VIA, personaNatural.getCodigoTipoVia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_VIA, personaNatural.getDescripcionTipoVia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_VIA, personaNatural.getDireccion());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_POSTAL, personaNatural.getCodigoPostal());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_MAIL, personaNatural.getCorreoElectronico());
					/** JBUGARIN  13/09/06**/
					// MODIFICADO POR G.O.V
					/**
					 * Modificado por SVR - AVATAR SAC - INI
					 */					
					//if (solicitudInscripcion.getPersonaJuridica().getIndicadorRUC().equals(Constantes.INDICADORRUCXML)) {
						//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OCUP, personaNatural.getCodigoCargoOcupacion());
						//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_OCUP, personaNatural.getDescripcionCargoOcupacion());
						//dia = personaNatural.getFechaCargo().substring(6,8);
						//mes = personaNatural.getFechaCargo().substring(4,6);
						//anho = personaNatural.getFechaCargo().substring(0,4);
						//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_OCUP, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					//}
					//else {
					
					dia = ((InstrumentoPublico)(solicitudInscripcion.getInstrumentoPublico().get(0))).getFecha().substring(6, 8);
					mes = ((InstrumentoPublico)(solicitudInscripcion.getInstrumentoPublico().get(0))).getFecha().substring(4, 6);
					anho = ((InstrumentoPublico)(solicitudInscripcion.getInstrumentoPublico().get(0))).getFecha().substring(0, 4);
					
					
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OCUP, personaNatural.getCodigoCargoOcupacion());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_OCUP, personaNatural.getDescripcionCargoOcupacion());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_OCUP,FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" 00:00:00"));
						
					//}
					/**
					 * Modificado por SVR - AVATAR SAC - FIN
					 */						
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());
					/*** */
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_AFEC, 1);//////////////////
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ASOC, Constantes.CAMPO_NU_PART_ASOC);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CUR, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_ACCI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_USUA_CREA, null);

					/**
					 * SE ADICIONAN LOS CAMPOS: 
					 * 
					 * - indicadorRepresentante, 
					 * - nombreConyuge, 
					 * - valorParticipacion,
					 * - porcentajeParticipacion,
					 * - numeroPartidaEmpresa
					 * 
					 * SAUL VASQUEZ - AVATAR GLOBAL
					 */	
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_IND_RRLL_PARTIC, personaNatural.getIndicadorRepresentante());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_CONY, personaNatural.getNombreConyuge());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_VA_PART, personaNatural.getValorParticipacion());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_PO_PART, personaNatural.getPorcentajeParticipacion());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ACCI, personaNatural.getNumeroPartidaEmpresa());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ACCI, personaNatural.getNumeroPartidaEmpresa());					

					tablaTTPersNatuTitu.add();
				}
			}

			//TP_INTR_TITU
			System.out.println("Insertando en TP_INTR_TITU");
			DboTPIntrTitu tablaTPIntrTitu = new DboTPIntrTitu();
			tablaTPIntrTitu.setConnection(myConn);
			ArrayList listaInstrumentosPublico = (ArrayList)solicitudInscripcion.getInstrumentoPublico();
			InstrumentoPublico instrumentoPublico = null;
			if (listaInstrumentosPublico!=null) {
				size = listaInstrumentosPublico.size();
				for (int i=0; i<size; i++){
					instrumentoPublico = (InstrumentoPublico)listaInstrumentosPublico.get(i);
					/*** solicitudInscripcion*/
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** instrumentoPublico*/
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NS_INTR_TITU, i+1);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_TI_INTR, instrumentoPublico.getCodigoTipoInstrumento());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_DE_TI_INTR, instrumentoPublico.getDescripcionTipoInstrumento());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_LU_INTR_PUBL, instrumentoPublico.getLugar());
					dia = instrumentoPublico.getFecha().substring(6,8);
					mes = instrumentoPublico.getFecha().substring(4,6);
					anho = instrumentoPublico.getFecha().substring(0,4);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_FE_INTR_PUBL, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));

					/*** */
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NS_AFEC, 1);/////////////////////////
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_AA_TITU, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NU_TITU, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_REGI, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_TS_USUA_CREA, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_ID_USUA_CREA, null);
					tablaTPIntrTitu.add();
				}
			}

			//TA_SGMT_TITU_JURI
			System.out.println("Insertando en TA_SGMT_TITU_JURI");
			DboTASgmtTituJuri tablaTASgmtTituJuri = new DboTASgmtTituJuri();
			tablaTASgmtTituJuri.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** instrumentoPublico */
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_DE_OBSE, instrumentoPublico.getOtros());
			/***  */
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_SEQU, Constantes.CAMPO_NU_SEQU);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ES_TITU, Constantes.CAMPO_ES_TITU);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_PU_CTRL, Constantes.CAMPO_PU_CTRL);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_IN_MOST, Constantes.CAMPO_IN_MOST);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_AA_TITU, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_TITU, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_REGI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_OFIC_RGST, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_EMPL, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TI_REIN, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_OPER, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ES_TITU_CALI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_USUA_CREA, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ID_USUA_CREA, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_USUA_MODI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ID_USUA_MODI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TI_SITU_SGMT_TITU_JURI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_OPER_CHAR, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_SECC, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_ZONA, null);
			tablaTASgmtTituJuri.add();


			//TT_PAGO
			System.out.println("Insertando en TT_PAGO");
			DboTTPago tablaTTPago = new DboTTPago();
			tablaTTPago.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPago.setField(DboTTPago.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPago.setField(DboTTPago.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** datosPago*/
			tablaTTPago.setField(DboTTPago.CAMPO_MO_SERV, solicitudInscripcion.getDatosPago().getCostoTotalServicio().toString());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_FR_PAGO, solicitudInscripcion.getDatosPago().getCodigoFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_FR_PAGO, solicitudInscripcion.getDatosPago().getDescripcionFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, solicitudInscripcion.getDatosPago().getNumeroOperacion());
			
			System.out.println("Fecha de Pago::"+solicitudInscripcion.getDatosPago().getFechaPago());
			//"09/08/2007"
			//dia = solicitudInscripcion.getDatosPago().getFechaPago().substring(6,8);
			dia = solicitudInscripcion.getDatosPago().getFechaPago().substring(0,2);
			mes = solicitudInscripcion.getDatosPago().getFechaPago().substring(3,5);
			anho = solicitudInscripcion.getDatosPago().getFechaPago().substring(6,10);
			
			System.out.println("Fecha de Pago - dia::"+dia);			
			System.out.println("Fecha de Pago - mes::"+mes);
			System.out.println("Fecha de Pago - anho::"+anho);
			
			hh = solicitudInscripcion.getDatosPago().getHoraPago().substring(0,2);
			mm = solicitudInscripcion.getDatosPago().getHoraPago().substring(3,5);
			ss = solicitudInscripcion.getDatosPago().getHoraPago().substring(6,8);
			System.out.println("Hora de Pago::"+solicitudInscripcion.getDatosPago().getHoraPago());
			System.out.println("Hora de Pago - hora::"+hh);			
			System.out.println("Hora de Pago - minutos::"+mm);
			System.out.println("Hora de Pago - segundos::"+ss);			
			
			tablaTTPago.setField(DboTTPago.CAMPO_FE_PAGO,  FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
			tablaTTPago.setField(DboTTPago.CAMPO_CO_TIPO_PAGO, solicitudInscripcion.getDatosPago().getCodigoTipoPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_TIPO_PAGO, solicitudInscripcion.getDatosPago().getDescripcionTipoPago());
			/***  */
			tablaTTPago.setField(DboTTPago.CAMPO_AA_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_NU_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST, null);
			tablaTTPago.setField(DboTTPago.CAMPO_IN_SERV, "01"); //agregado por la funcionalidad de reserva de denominacion
			tablaTTPago.add();

			System.out.println("Insertando en TT_PAGO_DSCTO");
			DboTTPagoDscto tablaTTPagoDscto = new DboTTPagoDscto();
			tablaTTPagoDscto.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** datos obtenidos*/
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_MOVIMIENTO_ID, datosAdicionales.getMovimientoId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_FEC_HOR, "to_date('"+datosAdicionales.getFecHor().substring(0,19)+"','yyyy-mm-dd HH24:MI:SS')");
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_LINEA_PREPAGO_ID, datosAdicionales.getLineaPrePagoId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CUENTA_ID, datosAdicionales.getCuentaId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CONSUMO_ID, datosAdicionales.getConsumoId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_MONTO, datosAdicionales.getMonto());
			/***  */
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_AA_TITU, null);
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_NU_TITU, null);
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_REGI, null);
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_OFIC_RGST, null);
			
			
			tablaTTPagoDscto.add();


		} catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		}

	}


	private void registraSolicitudBDReservaNombre(SolicitudInscripcion solicitudInscripcion,
														DatosAdicionales datosAdicionales,
									                    DBConnection myConn) throws DBException, Throwable {

		String dia, mes, anho, hh, mm, ss;

		try {

			System.out.println("Insertando en TP_HOJA_PRES");
			//TP_HOJA_PRES
			DboTPHojaPres tablaTPHojaPres = new DboTPHojaPres();
			tablaTPHojaPres.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_AREA, solicitudInscripcion.getCodigoArea());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_AREA, solicitudInscripcion.getDescripcionArea());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUO, solicitudInscripcion.getCuo());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_SERVICIO_ID, solicitudInscripcion.getCodigoServicio());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_SERVICIO, solicitudInscripcion.getDescripcionServicio());
			//viene de otro
			//tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_IN_RUC, solicitudInscripcion.getIndicadorRUC());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUARIO, solicitudInscripcion.getCodigoUsuario());
			/** presentante*/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_PERS_AUTZ_PRES, solicitudInscripcion.getPresentante().getCodigoPresentante());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_PRES, solicitudInscripcion.getPresentante().getApellidoPaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_PRES, solicitudInscripcion.getPresentante().getApellidoMaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_PRES, solicitudInscripcion.getPresentante().getNombre());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUR, solicitudInscripcion.getPresentante().getCodigoInstitucion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_CUR, solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN, solicitudInscripcion.getPresentante().getCodigoTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN, solicitudInscripcion.getPresentante().getDescripcionTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU, solicitudInscripcion.getPresentante().getNumeroDocumento());
			/***jbugarin 13/09/06 ***/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_UB_GEOG_PRES, solicitudInscripcion.getPresentante().getCodigoPais()+solicitudInscripcion.getPresentante().getCodigoDepartamento()+solicitudInscripcion.getPresentante().getCodigoProvincia()+solicitudInscripcion.getPresentante().getCodigoDistrito()); //CONCATENACION VERIFICAR
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DEPARTAMENTO_PRES, solicitudInscripcion.getPresentante().getDescripcionDepartamento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_PROVINCIA_PRES, solicitudInscripcion.getPresentante().getDescripcionProvincia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DISTRITO_PRES, solicitudInscripcion.getPresentante().getDescripcionDistrito());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_VIA_PRES, solicitudInscripcion.getPresentante().getCodigoTipoVia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_VIA_PRES, solicitudInscripcion.getPresentante().getDescripcionTipoVia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_VIA_PRES, solicitudInscripcion.getPresentante().getDireccion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_POSTAL_PRES, solicitudInscripcion.getPresentante().getCodigoPostal());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_MAIL_PRES, solicitudInscripcion.getPresentante().getCorreoElectronico());
			/***jbugarin 13/09/06 ***/

			/***jbugarin 13/09/06 ***/
			/** Persona Juridica**/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_IN_RUC, solicitudInscripcion.getPersonaJuridica().getIndicadorRUC());
			/***jbugarin 13/09/06 ***/

			/** interesado ya no existe jbugarin 13/09/06*/
			/*tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_REPR, solicitudInscripcion.getInteresado().getApellidoPaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_REPR, solicitudInscripcion.getInteresado().getApellidoMaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_REPR, solicitudInscripcion.getInteresado().getNombre());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN_REPR, solicitudInscripcion.getInteresado().getCodigoTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN_REPR, solicitudInscripcion.getInteresado().getDescripcionTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU_REPR, solicitudInscripcion.getInteresado().getNumeroDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_ELECT_REPR, solicitudInscripcion.getInteresado().getCorreoElectronico());
			*/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_DEFI, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_DEFI, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_USUA_CREA, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUA_CREA, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_OBSE, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_SITU_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_ANOT, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_FORM_REG, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RZ_SOCL_REPR, null);
			/***jbugarin 13/09/06 ***/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_ELECT_REPR, null);
			/***jbugarin 13/09/06 ***/
			tablaTPHojaPres.add();

			//TA_TITU_ACTO
			System.out.println("Insertando en TA_TITU_ACTO");
			DboTATituActo tablaTATituActo = new DboTATituActo();
			tablaTATituActo.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTATituActo.setField(DboTATituActo.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
			tablaTATituActo.setField(DboTATituActo.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_LIBR, solicitudInscripcion.getCodigoLibro());
			tablaTATituActo.setField(DboTATituActo.CAMPO_DE_LIBR, solicitudInscripcion.getDescripcionLibro());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/***  */
			tablaTATituActo.setField(DboTATituActo.CAMPO_NS_AFEC, 1);////////////////////
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_ESTD, Constantes.CAMPO_IN_ESTD);
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_PART, Constantes.CAMPO_NU_PART);
			tablaTATituActo.setField(DboTATituActo.CAMPO_AA_TITU, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_TITU, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_MO_TOTA_ACTO, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_EXON, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_PO_EXON, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_CREA, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_CREA, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_MODI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_MODI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_RUBR, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_RESE, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_GENE_ASIE, null);
			tablaTATituActo.add();

			//TT_PERS_JURI_TITU
			System.out.println("Insertando en TT_PERS_JURI_TITU");
			DboTTPersJuriTitu tablaTTPersJuriTitu = new DboTTPersJuriTitu();
			tablaTTPersJuriTitu.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());
			/*** personaJuridica*/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, solicitudInscripcion.getPersonaJuridica().getRazonSocial());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, solicitudInscripcion.getPersonaJuridica().getSiglas());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, "000");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoParticipante());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedad());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_SOCI, solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedadAnonima());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_SOCI, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedadAnonima());
			/** */
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);///////////////////
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_CIIU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, "1");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_ACCI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TS_USUA_CREA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_USUA_CREA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MAIL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_MONE, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_MONE, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MO_TOTA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_VA_ACCN, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_SI_CAPI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SI_CAPI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_PO_PAGO_CANC, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU_ASOC, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU_ASOC, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, null);
			/** gochoa **/
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, solicitudInscripcion.getPersonaJuridica().getCodigoTipoVia());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, solicitudInscripcion.getPersonaJuridica().getDireccion());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoVia());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
			/************/

			tablaTTPersJuriTitu.add();

			ArrayList listaParticipantesPJ = (ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
			PersonaJuridica personaJuridica = null;
			int size = 0;
			if (listaParticipantesPJ!=null) {
				size = listaParticipantesPJ.size();
				for (int i=0; i<size; i++){
					personaJuridica = (PersonaJuridica)listaParticipantesPJ.get(i);

					/*** solicitudInscripcion*/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** participantesPersonaJuridica*/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, personaJuridica.getCodigoTipoDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, personaJuridica.getDescripcionTipoDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, personaJuridica.getNumeroDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());

					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, i+2);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, personaJuridica.getCodigoTipoParticipante());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, personaJuridica.getDescripcionTipoParticipante());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, personaJuridica.getCodigoNacionalidad());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, personaJuridica.getDescripcionNacionalidad());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, personaJuridica.getRazonSocial());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, personaJuridica.getSiglas());
					//JBUGARIN 13/09/06
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, personaJuridica.getNumeroPartida());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, personaJuridica.getCodigoZonaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, personaJuridica.getCodigoOficinaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, personaJuridica.getCodigoTipoVia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, personaJuridica.getDescripcionTipoVia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, personaJuridica.getDireccion());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, personaJuridica.getCodigoPostal());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, personaJuridica.getCodigoPais()+personaJuridica.getCodigoDepartamento()+personaJuridica.getCodigoProvincia()+personaJuridica.getCodigoDistrito());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, personaJuridica.getDescripcionDepartamento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, personaJuridica.getDescripcionProvincia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, personaJuridica.getDescripcionDistrito());
					//JBUGARIN 13/09/06

					/***  */
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);//////////////////////////////
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_SOCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_SOCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_MONE, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_MONE, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MO_TOTA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_VA_ACCN, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_SI_CAPI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SI_CAPI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_PO_PAGO_CANC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_CIIU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_ACCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_USUA_CREA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
					/** JBUGARIN 13/09/06 **/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MAIL, null);
					/** JBUGARIN 13/09/06 **/

					tablaTTPersJuriTitu.add();


				}
			}

			//TT_PERS_NATU_TITU
			System.out.println("Insertando en TT_PERS_NATU_TITU");
			DboTTPersNatuTitu tablaTTPersNatuTitu = new DboTTPersNatuTitu();
			tablaTTPersNatuTitu.setConnection(myConn);
			ArrayList listaParticipantesPN = (ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
			PersonaNatural personaNatural = null;
			if (listaParticipantesPN!=null) {
				size = listaParticipantesPN.size();
				for (int i=0; i<size; i++){
					personaNatural = (PersonaNatural)listaParticipantesPN.get(i);

					/*** solicitudInscripcion*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** participantesPersonaNatural*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_PERS_NATU, i+1);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_DOCU_IDEN, personaNatural.getCodigoTipoDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_DOCU_IDEN, personaNatural.getDescripcionTipoDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_DOCU, personaNatural.getNumeroDocumento());
					/** JBUGARIN  13/09/06**/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					/** JBUGARIN  13/09/06**/

					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_CIVL, personaNatural.getCodigoEstadoCivil());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ES_CIVL, personaNatural.getDescripcionEstadoCivil());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU, personaNatural.getApellidoPaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU, personaNatural.getApellidoMaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_PERS_NATU, personaNatural.getNombre());
					//dia = personaNatural.getFechaNacimiento().substring(6,8);
					//mes = personaNatural.getFechaNacimiento().substring(4,6);
					//anho = personaNatural.getFechaNacimiento().substring(0,4);
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_NACI, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC, personaNatural.getCodigoTipoParticipante());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_PRTC, personaNatural.getDescripcionTipoParticipante());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_PAIS, personaNatural.getCodigoNacionalidad());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_NCNL, personaNatural.getDescripcionNacionalidad());
					/** JBUGARIN  13/09/06**/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_UB_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DEPARTAMENTO_PRES, personaNatural.getDescripcionDepartamento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_PROVINCIA_PRES, personaNatural.getDescripcionProvincia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DISTRITO_PRES, personaNatural.getDescripcionDistrito());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_VIA, personaNatural.getCodigoTipoVia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_VIA, personaNatural.getDescripcionTipoVia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_VIA, personaNatural.getDireccion());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_POSTAL, personaNatural.getCodigoPostal());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_MAIL, personaNatural.getCorreoElectronico());
					/** JBUGARIN  13/09/06**/
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OCUP, personaNatural.getCodigoCargoOcupacion());
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_OCUP, personaNatural.getDescripcionCargoOcupacion());
					//dia = personaNatural.getFechaCargo().substring(6,8);
					//mes = personaNatural.getFechaCargo().substring(4,6);
					//anho = personaNatural.getFechaCargo().substring(0,4);
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_OCUP, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());
					/*** */
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_AFEC, 1);//////////////////
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ASOC, Constantes.CAMPO_NU_PART_ASOC);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CUR, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_ACCI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_USUA_CREA, null);

					tablaTTPersNatuTitu.add();
				}
			}

			//TP_INTR_TITU
			//System.out.println("Insertando en TP_INTR_TITU");
			//DboTPIntrTitu tablaTPIntrTitu = new DboTPIntrTitu();
			//tablaTPIntrTitu.setConnection(myConn);
			//ArrayList listaInstrumentosPublico = (ArrayList)solicitudInscripcion.getInstrumentoPublico();
			//InstrumentoPublico instrumentoPublico = null;
			//if (listaInstrumentosPublico!=null) {
				//size = listaInstrumentosPublico.size();
				//for (int i=0; i<size; i++){
					//instrumentoPublico = (InstrumentoPublico)listaInstrumentosPublico.get(i);
					///*** solicitudInscripcion*/
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					///*** instrumentoPublico*/
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NS_INTR_TITU, i+1);
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_TI_INTR, instrumentoPublico.getCodigoTipoInstrumento());
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_DE_TI_INTR, instrumentoPublico.getDescripcionTipoInstrumento());
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_LU_INTR_PUBL, instrumentoPublico.getLugar());
					//dia = instrumentoPublico.getFecha().substring(6,8);
					//mes = instrumentoPublico.getFecha().substring(4,6);
					//anho = instrumentoPublico.getFecha().substring(0,4);
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_FE_INTR_PUBL, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));

					/*** */
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NS_AFEC, 1);/////////////////////////
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_AA_TITU, null);
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NU_TITU, null);
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_REGI, null);
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_OFIC_RGST, null);
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_TS_USUA_CREA, null);
					//tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_ID_USUA_CREA, null);
					//tablaTPIntrTitu.add();
				//}
			//}

			//TA_SGMT_TITU_JURI
			System.out.println("Insertando en TA_SGMT_TITU_JURI");
			DboTASgmtTituJuri tablaTASgmtTituJuri = new DboTASgmtTituJuri();
			tablaTASgmtTituJuri.setConnection(myConn);

			/*** solicitudInscripcion*/
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			//tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_DE_OBSE, instrumentoPublico.getOtros());

			/***  */
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_SEQU, Constantes.CAMPO_NU_SEQU);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ES_TITU, Constantes.CAMPO_ES_TITU);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_PU_CTRL, Constantes.CAMPO_PU_CTRL);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_IN_MOST, Constantes.CAMPO_IN_MOST);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_AA_TITU, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_TITU, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_REGI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_OFIC_RGST, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_EMPL, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TI_REIN, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_OPER, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ES_TITU_CALI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_USUA_CREA, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ID_USUA_CREA, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_USUA_MODI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ID_USUA_MODI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TI_SITU_SGMT_TITU_JURI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_OPER_CHAR, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_SECC, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_ZONA, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_DE_OBSE, null);
			tablaTASgmtTituJuri.add();


			//TT_PAGO
			System.out.println("Insertando en TT_PAGO");
			DboTTPago tablaTTPago = new DboTTPago();
			tablaTTPago.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPago.setField(DboTTPago.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPago.setField(DboTTPago.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** datosPago*/
			tablaTTPago.setField(DboTTPago.CAMPO_MO_SERV, solicitudInscripcion.getDatosPago().getCostoTotalServicio().toString());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_FR_PAGO, solicitudInscripcion.getDatosPago().getCodigoFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_FR_PAGO, solicitudInscripcion.getDatosPago().getDescripcionFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, solicitudInscripcion.getDatosPago().getNumeroOperacion());
			dia = solicitudInscripcion.getDatosPago().getFechaPago().substring(6,8);
			mes = solicitudInscripcion.getDatosPago().getFechaPago().substring(4,6);
			anho = solicitudInscripcion.getDatosPago().getFechaPago().substring(0,4);
			hh = solicitudInscripcion.getDatosPago().getHoraPago().substring(0,2);
			mm = solicitudInscripcion.getDatosPago().getHoraPago().substring(2,4);
			ss = solicitudInscripcion.getDatosPago().getHoraPago().substring(4,6);
			tablaTTPago.setField(DboTTPago.CAMPO_FE_PAGO,  FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
			tablaTTPago.setField(DboTTPago.CAMPO_CO_TIPO_PAGO, solicitudInscripcion.getDatosPago().getCodigoTipoPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_TIPO_PAGO, solicitudInscripcion.getDatosPago().getDescripcionTipoPago());
			/***  */
			tablaTTPago.setField(DboTTPago.CAMPO_AA_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_NU_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST, null);
			tablaTTPago.add();

			System.out.println("Insertando en TT_PAGO_DSCTO");
			DboTTPagoDscto tablaTTPagoDscto = new DboTTPagoDscto();
			tablaTTPagoDscto.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** datos obtenidos*/
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_MOVIMIENTO_ID, datosAdicionales.getMovimientoId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_FEC_HOR, "to_date('"+datosAdicionales.getFecHor().substring(0,19)+"','yyyy-mm-dd HH24:MI:SS')");
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_LINEA_PREPAGO_ID, datosAdicionales.getLineaPrePagoId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CUENTA_ID, datosAdicionales.getCuentaId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CONSUMO_ID, datosAdicionales.getConsumoId());
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_MONTO, datosAdicionales.getMonto());
			/***  */
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_AA_TITU, null);
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_NU_TITU, null);
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_REGI, null);
			tablaTTPagoDscto.setField(DboTTPagoDscto.CAMPO_CO_OFIC_RGST, null);
			tablaTTPagoDscto.add();

			System.out.println("TERMINO!!!!!!");


		} catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;

		}

	}

	private void registraSolicitudBDBloqueoInmueble(SolicitudInscripcion solicitudInscripcion,
														DatosAdicionales datosAdicionales,
									                    DBConnection myConn) throws DBException, Throwable {

		String dia, mes, anho, hh, mm, ss;

		try {
			System.out.println("Insertando en TP_HOJA_PRES");
			//TP_HOJA_PRES
			DboTPHojaPres tablaTPHojaPres = new DboTPHojaPres();
			tablaTPHojaPres.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_AREA, solicitudInscripcion.getCodigoArea());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_AREA, solicitudInscripcion.getDescripcionArea());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUO, solicitudInscripcion.getCuo());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_SERVICIO_ID, solicitudInscripcion.getCodigoServicio());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_SERVICIO, solicitudInscripcion.getDescripcionServicio());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUARIO, solicitudInscripcion.getCodigoUsuario());
			/** presentante*/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_PERS_AUTZ_PRES, solicitudInscripcion.getPresentante().getCodigoPresentante());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_PRES, solicitudInscripcion.getPresentante().getApellidoPaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_PRES, solicitudInscripcion.getPresentante().getApellidoMaterno());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_PRES, solicitudInscripcion.getPresentante().getNombre());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUR, solicitudInscripcion.getPresentante().getCodigoInstitucion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_CUR, solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN, solicitudInscripcion.getPresentante().getCodigoTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN, solicitudInscripcion.getPresentante().getDescripcionTipoDocumento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU, solicitudInscripcion.getPresentante().getNumeroDocumento());
			/***jbugarin 13/09/06 ***/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_UB_GEOG_PRES, solicitudInscripcion.getPresentante().getCodigoPais()+solicitudInscripcion.getPresentante().getCodigoDepartamento()+solicitudInscripcion.getPresentante().getCodigoProvincia()+solicitudInscripcion.getPresentante().getCodigoDistrito()); //CONCATENACION VERIFICAR
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DEPARTAMENTO_PRES, solicitudInscripcion.getPresentante().getDescripcionDepartamento());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_PROVINCIA_PRES, solicitudInscripcion.getPresentante().getDescripcionProvincia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DISTRITO_PRES, solicitudInscripcion.getPresentante().getDescripcionDistrito());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_VIA_PRES, solicitudInscripcion.getPresentante().getCodigoTipoVia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_VIA_PRES, solicitudInscripcion.getPresentante().getDescripcionTipoVia());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_VIA_PRES, solicitudInscripcion.getPresentante().getDireccion());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_POSTAL_PRES, solicitudInscripcion.getPresentante().getCodigoPostal());
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_MAIL_PRES, solicitudInscripcion.getPresentante().getCorreoElectronico());
			/** **/
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_IN_RUC, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_DEFI, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_USUA_CREA, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUA_CREA, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_OBSE, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_SITU_HOJA_PRES, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_ANOT, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_FORM_REG, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RZ_SOCL_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU_REPR, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_DEFI, null);
			tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_ELECT_REPR, null);

			tablaTPHojaPres.add();

			//TA_TITU_ACTO
			System.out.println("Insertando en TA_TITU_ACTO");
			DboTATituActo tablaTATituActo = new DboTATituActo();
			tablaTATituActo.setConnection(myConn);

			/**solicitudInscripcion*/
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTATituActo.setField(DboTATituActo.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
			tablaTATituActo.setField(DboTATituActo.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_LIBR, solicitudInscripcion.getCodigoLibro());
			tablaTATituActo.setField(DboTATituActo.CAMPO_DE_LIBR, solicitudInscripcion.getDescripcionLibro());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/***  */
			tablaTATituActo.setField(DboTATituActo.CAMPO_NS_AFEC, 1);////////////////////
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_ESTD, Constantes.CAMPO_IN_ESTD);
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_PART, Constantes.CAMPO_NU_PART);
			tablaTATituActo.setField(DboTATituActo.CAMPO_AA_TITU, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_TITU, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_MO_TOTA_ACTO, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_EXON, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_PO_EXON, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_CREA, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_CREA, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_MODI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_MODI, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_CO_RUBR, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_RESE, null);
			tablaTATituActo.setField(DboTATituActo.CAMPO_IN_GENE_ASIE, null);
			tablaTATituActo.add();

			//TT_PERS_JURI_TITU
			System.out.println("Insertando en TT_PERS_JURI_TITU");
			DboTTPersJuriTitu tablaTTPersJuriTitu = new DboTTPersJuriTitu();
			tablaTTPersJuriTitu.setConnection(myConn);
			// no se considera por que no existe una persona juridica

			// solicitudInscripcion
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());

			/*** Participante personaJuridica*/
			/*tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, solicitudInscripcion.getPersonaJuridica().getRazonSocial());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, solicitudInscripcion.getPersonaJuridica().getSiglas());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, "000");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoParticipante());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedad());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_SOCI, solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedadAnonima());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_SOCI, solicitudInscripcion.getPersonaJuridica().getDescripcionTipoSociedadAnonima());


			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);///////////////////
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_CIIU, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, null);
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, null);
			//tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, "1");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_ACCI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TS_USUA_CREA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_USUA_CREA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
			tablaTTPersJuriTitu.add();
			*/

			ArrayList listaParticipantesPJ = (ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
			PersonaJuridica personaJuridica = null;
			int size = 0;
			if (listaParticipantesPJ!=null) {
				size = listaParticipantesPJ.size();
				for (int i=0; i<size; i++){
					personaJuridica = (PersonaJuridica)listaParticipantesPJ.get(i);
					/*** solicitudInscripcion*/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** participantesPersonaJuridica*/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, personaJuridica.getCodigoTipoDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, personaJuridica.getDescripcionTipoDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, personaJuridica.getNumeroDocumento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, i+1);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, personaJuridica.getCodigoTipoParticipante());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, personaJuridica.getDescripcionTipoParticipante());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, personaJuridica.getCodigoNacionalidad());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, personaJuridica.getDescripcionNacionalidad());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, personaJuridica.getRazonSocial());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, personaJuridica.getSiglas());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, personaJuridica.getCodigoZonaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, personaJuridica.getCodigoOficinaRegistral());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, personaJuridica.getCodigoTipoVia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, personaJuridica.getDescripcionTipoVia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, personaJuridica.getDireccion());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, personaJuridica.getCodigoPostal());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, personaJuridica.getCodigoPais()+personaJuridica.getCodigoDepartamento()+personaJuridica.getCodigoProvincia()+personaJuridica.getCodigoDistrito());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, personaJuridica.getDescripcionDepartamento());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, personaJuridica.getDescripcionProvincia());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, personaJuridica.getDescripcionDistrito());
					/** **/
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);//////////////////////////////
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_SOCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_SOCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_MONE, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_MONE, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MO_TOTA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_VA_ACCN, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_SI_CAPI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SI_CAPI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_PO_PAGO_CANC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_TITU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_TITU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_CIIU, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_ACCI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_USUA_CREA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MAIL, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, null);


					tablaTTPersJuriTitu.add();


				}
			}

			//TT_PERS_NATU_TITU
			System.out.println("Insertando en TT_PERS_NATU_TITU");
			DboTTPersNatuTitu tablaTTPersNatuTitu = new DboTTPersNatuTitu();
			tablaTTPersNatuTitu.setConnection(myConn);
			ArrayList listaParticipantesPN = (ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
			PersonaNatural personaNatural = null;
			if (listaParticipantesPN!=null) {
				size = listaParticipantesPN.size();
				for (int i=0; i<size; i++){
					personaNatural = (PersonaNatural)listaParticipantesPN.get(i);
					/*** solicitudInscripcion*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** participantesPersonaNatural*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_PERS_NATU, i+1);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_DOCU_IDEN, personaNatural.getCodigoTipoDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_DOCU_IDEN, personaNatural.getDescripcionTipoDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_DOCU, personaNatural.getNumeroDocumento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_CIVL, personaNatural.getCodigoEstadoCivil());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ES_CIVL, personaNatural.getDescripcionEstadoCivil());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU, personaNatural.getApellidoPaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU, personaNatural.getApellidoMaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_PERS_NATU, personaNatural.getNombre());
					//dia = personaNatural.getFechaNacimiento().substring(6,8);
					//mes = personaNatural.getFechaNacimiento().substring(4,6);
					//anho = personaNatural.getFechaNacimiento().substring(0,4);
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_NACI, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC, personaNatural.getCodigoTipoParticipante());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_PRTC, personaNatural.getDescripcionTipoParticipante());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_PAIS, personaNatural.getCodigoNacionalidad());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_NCNL, personaNatural.getDescripcionNacionalidad());
					/** JBUGARIN  13/09/06**/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_UB_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DEPARTAMENTO_PRES, personaNatural.getDescripcionDepartamento());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_PROVINCIA_PRES, personaNatural.getDescripcionProvincia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DISTRITO_PRES, personaNatural.getDescripcionDistrito());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_VIA, personaNatural.getCodigoTipoVia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_VIA, personaNatural.getDescripcionTipoVia());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_VIA, personaNatural.getDireccion());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_POSTAL, personaNatural.getCodigoPostal());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_MAIL, personaNatural.getCorreoElectronico());
					/** JBUGARIN  13/09/06**/

					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OCUP, personaNatural.getCodigoCargoOcupacion());
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_OCUP, personaNatural.getDescripcionCargoOcupacion());
					//dia = personaNatural.getFechaCargo().substring(6,8);
					//mes = personaNatural.getFechaCargo().substring(4,6);
					//anho = personaNatural.getFechaCargo().substring(0,4);
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_OCUP, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_LIBR_PART, solicitudInscripcion.getCodigoLibro());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_LIBR_PART, solicitudInscripcion.getDescripcionLibro());
					/*** */
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_AFEC, 1);//////////////////
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ASOC, Constantes.CAMPO_NU_PART_ASOC);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CUR, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_ACCI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_USUA_CREA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_NACI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OCUP, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_OCUP, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_OCUP, null);

					tablaTTPersNatuTitu.add();
				}
			}

			//TP_INTR_TITU
			System.out.println("Insertando en TP_INTR_TITU");
			DboTPIntrTitu tablaTPIntrTitu = new DboTPIntrTitu();
			tablaTPIntrTitu.setConnection(myConn);
			ArrayList listaInstrumentosPublico = (ArrayList)solicitudInscripcion.getInstrumentoPublico();
			InstrumentoPublico instrumentoPublico = null;
			if (listaInstrumentosPublico!=null) {
				size = listaInstrumentosPublico.size();
				for (int i=0; i<size; i++){
					instrumentoPublico = (InstrumentoPublico)listaInstrumentosPublico.get(i);
					/*** solicitudInscripcion*/
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_DE_ACTO_RGST, solicitudInscripcion.getDescripcionActo());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					/*** instrumentoPublico*/
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NS_INTR_TITU, i+1);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_TI_INTR, instrumentoPublico.getCodigoTipoInstrumento());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_DE_TI_INTR, instrumentoPublico.getDescripcionTipoInstrumento());
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_LU_INTR_PUBL, instrumentoPublico.getLugar());
					dia = instrumentoPublico.getFecha().substring(6,8);
					mes = instrumentoPublico.getFecha().substring(4,6);
					anho = instrumentoPublico.getFecha().substring(0,4);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_FE_INTR_PUBL, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					/*** */
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NS_AFEC, 1);/////////////////////////
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_AA_TITU, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_NU_TITU, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_REGI, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_TS_USUA_CREA, null);
					tablaTPIntrTitu.setField(DboTPIntrTitu.CAMPO_ID_USUA_CREA, null);
					tablaTPIntrTitu.add();
				}
			}


			//TA_SGMT_TITU_JURI
			System.out.println("Insertando en TA_SGMT_TITU_JURI");
			DboTASgmtTituJuri tablaTASgmtTituJuri = new DboTASgmtTituJuri();
			tablaTASgmtTituJuri.setConnection(myConn);

			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_DE_OBSE, instrumentoPublico.getOtros());

			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_SEQU, Constantes.CAMPO_NU_SEQU);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ES_TITU, Constantes.CAMPO_ES_TITU);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_PU_CTRL, Constantes.CAMPO_PU_CTRL);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_IN_MOST, Constantes.CAMPO_IN_MOST);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_AA_TITU, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_NU_TITU, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_REGI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_OFIC_RGST, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_EMPL, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TI_REIN, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_OPER, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ES_TITU_CALI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_USUA_CREA, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ID_USUA_CREA, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_USUA_MODI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_ID_USUA_MODI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TI_SITU_SGMT_TITU_JURI, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_TS_OPER_CHAR, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_SECC, null);
			tablaTASgmtTituJuri.setField(DboTASgmtTituJuri.CAMPO_CO_ZONA, null);
			tablaTASgmtTituJuri.add();


			/*System.out.println("Insertando en TA_SGMT_TITU_INMB");
			DboTASgmtTituInmb tablaTASgmtTituInmb = new DboTASgmtTituInmb();
			tablaTASgmtTituInmb.setConnection(myConn);
			//tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			//tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_DE_OBSE, instrumentoPublico.getOtros());
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_PU_CTRL, Constantes.CAMPO_PU_CTRL);
            tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_REGI_ORIG_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTASgmtTituInmb.setField(DboTASgmtTituJuri.CAMPO_NU_SEQU, Constantes.CAMPO_NU_SEQU);
			tablaTASgmtTituInmb.setField(DboTASgmtTituJuri.CAMPO_ES_TITU, Constantes.CAMPO_ES_TITU);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_IN_MOST, Constantes.CAMPO_IN_MOST);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_AA_TITU, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_NU_TITU, null);
			//tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_REGI, null);
			//tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_OFIC_RGST, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_EMPL, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_TI_REIN, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_TS_OPER, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_ES_TITU_CALI, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_TS_USUA_CREA, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_ID_USUA_CREA, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_TS_USUA_MODI, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_ID_USUA_MODI, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_TI_SITU_SGMT_TITU_INMB, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_TS_OPER_CHAR, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_SECC, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_CO_ZONA, null);
			tablaTASgmtTituInmb.setField(DboTASgmtTituInmb.CAMPO_DE_OBSE, null);
			tablaTASgmtTituInmb.add();
			*/

			//TT_PAGO
			System.out.println("Insertando en TT_PAGO");
			DboTTPago tablaTTPago = new DboTTPago();
			tablaTTPago.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPago.setField(DboTTPago.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPago.setField(DboTTPago.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** datosPago*/
			tablaTTPago.setField(DboTTPago.CAMPO_MO_SERV, solicitudInscripcion.getDatosPago().getCostoTotalServicio().toString());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_FR_PAGO, solicitudInscripcion.getDatosPago().getCodigoFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_FR_PAGO, solicitudInscripcion.getDatosPago().getDescripcionFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, solicitudInscripcion.getDatosPago().getNumeroOperacion());
			dia = solicitudInscripcion.getDatosPago().getFechaPago().substring(6,8);
			mes = solicitudInscripcion.getDatosPago().getFechaPago().substring(4,6);
			anho = solicitudInscripcion.getDatosPago().getFechaPago().substring(0,4);
			hh = solicitudInscripcion.getDatosPago().getHoraPago().substring(0,2);
			mm = solicitudInscripcion.getDatosPago().getHoraPago().substring(2,4);
			ss = solicitudInscripcion.getDatosPago().getHoraPago().substring(4,6);
			tablaTTPago.setField(DboTTPago.CAMPO_FE_PAGO,  FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
			tablaTTPago.setField(DboTTPago.CAMPO_CO_TIPO_PAGO, solicitudInscripcion.getDatosPago().getCodigoTipoPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_TIPO_PAGO, solicitudInscripcion.getDatosPago().getDescripcionTipoPago());
			/***  */
			tablaTTPago.setField(DboTTPago.CAMPO_AA_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_NU_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST, null);
			tablaTTPago.add();


			System.out.println("Insertando en TT_INMB_TITU");
			DboTTInmbTitu tablaTTInmbTitu = new DboTTInmbTitu();
			tablaTTInmbTitu.setConnection(myConn);
			ArrayList listaPartidas = (ArrayList)solicitudInscripcion.getPartidas();
			Partida partida = null;
			if (listaPartidas!=null) {
				size = listaPartidas.size();
				for (int i=0; i<size; i++){
					partida = (Partida)listaPartidas.get(i);

					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NS_AFEC, 1);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NS_INMB, i+1);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_REGI_PART, partida.getCodigoZonaRegistral());
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_OFIC_RGST_PART, partida.getCodigoOficinaRegistral());

					/** partida **/
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NU_PART_ASOC, partida.getNumeroPartida());

					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_AA_TITU, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NU_TITU, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_REGI, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_GR_IMPO, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NS_AFEC, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_UB_GEOG, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TI_ZONA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NO_ZONA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NO_ZONA_1, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TI_VIA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NO_VIA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NO_VIA_1, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TI_NMRC, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NU_VIA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NU_VIA_1, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TI_INTE, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NU_INTE, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NU_INTE_1, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_DE_OTRA_REFE, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_DE_OTRA_REFE_1, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_CATA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_IN_BLOQ, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_ID_USUA_CREA, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TS_USUA_MODI, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_ID_USUA_MODI, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TI_SITU_INMB_TITU, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_CO_LIBR_PART, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_NS_ASIE_ASOC, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_IN_MXTO, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_DE_MXTO, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_TI_PROP, null);
					tablaTTInmbTitu.setField(DboTTInmbTitu.CAMPO_DE_INMB, null);


					tablaTTInmbTitu.add();

				}

			}

			//falta definir 22/09/06
			/*System.out.println("Insertando en TA_BLOQ_PART");

			DboTABloqPart tablaTABloqPart = new DboTABloqPart();
			tablaTABloqPart.setConnection(myConn);

			tablaTABloqPart.setField(DboTTInmbTitu.CAMPO_CO_ACTO_RGST, solicitudInscripcion.getCodigoActo());
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_CO_TI_SIST, 2);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_DE_TI_SIST, "SIR");
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_AA_TITU, null);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_NU_TITU, null);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_CO_REGI, null);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_CO_OFIC_RGST, null);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_TS_USUA_CREA, null);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_ID_USUA_CREA, null);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_TS_USUA_MODI, null);
			tablaTABloqPart.setField(DboTABloqPart.CAMPO_ID_USUA_MODI, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_CO_SECC, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_CO_LIBR, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_NU_PART, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_NU_ORIG_PART, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_NU_TOMO, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_NU_FOJA, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_IN_BLOQ_ACTI, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_IN_BLOQ_PASI, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_TS_USUA_ELIM, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_ID_USUA_ELIM, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_FLAG, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_ID_AUX, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_NU_FOJA_ID, null);
			tablaTABloqPart.setField(tablaTABloqPart.CAMPO_TS_USUA_CHAR, null);

			tablaTABloqPart.add();
			*/

			System.out.println("TERMINO!!!!!!");




		} catch (DBException dbe) {
			System.out.println("EXCEPCION2222222222222BloqInm");
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			System.out.println("EXCEPCION1111111111111111111BloqInm");
			ex.printStackTrace();
			throw ex;

		}

	}

	/** TRANSFERENCIA VEHICULAR INICIO 03/10/06**/

	private void registraSolicitudBDTransfVehicular(SolicitudInscripcion solicitudInscripcion,
														DatosAdicionales datosAdicionales,
									                    DBConnection myConn) throws DBException, Throwable {

		String dia, mes, anho, hh, mm, ss;

		try {


		    //TA_TITU
			System.out.println("Insertando en TA_TITU");
			DboTATitu tablaTATitu = new DboTATitu();
			tablaTATitu.setConnection(myConn);
			/** solicitudInscripcion**/
			tablaTATitu.setField(DboTATitu.CAMPO_NO_HOJA,solicitudInscripcion.getNumeroHoja());
			tablaTATitu.setField(DboTATitu.CAMPO_AN_TITU,solicitudInscripcion.getAnho());
			tablaTATitu.setField(DboTATitu.CAMPO_CO_AREA,solicitudInscripcion.getCodigoArea());
			tablaTATitu.setField(DboTATitu.CAMPO_CO_LIBR,solicitudInscripcion.getCodigoLibro());
			tablaTATitu.setField(DboTATitu.CAMPO_CO_ZONA,solicitudInscripcion.getCodigoZonaRegistral());
			tablaTATitu.setField(DboTATitu.CAMPO_CO_SEDE,solicitudInscripcion.getCodigoOficinaRegistral());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_AREA,solicitudInscripcion.getDescripcionArea());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_LIBRO,solicitudInscripcion.getDescripcionLibro());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_AREA,solicitudInscripcion.getDescripcionArea());
			tablaTATitu.setField(DboTATitu.CAMPO_CUO,null);
			tablaTATitu.setField(DboTATitu.CAMPO_SERVICIO_ID,solicitudInscripcion.getCodigoServicio());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_SERVICIO,solicitudInscripcion.getDescripcionServicio());
			tablaTATitu.setField(DboTATitu.CAMPO_ID_USUARIO,solicitudInscripcion.getCodigoUsuario());
			/** presentante**/
			tablaTATitu.setField(DboTATitu.CAMPO_CO_PRES,solicitudInscripcion.getPresentante().getCodigoPresentante());
			tablaTATitu.setField(DboTATitu.CAMPO_AP_PATE,solicitudInscripcion.getPresentante().getApellidoPaterno());
			tablaTATitu.setField(DboTATitu.CAMPO_AP_MATE,solicitudInscripcion.getPresentante().getApellidoMaterno());
			tablaTATitu.setField(DboTATitu.CAMPO_NOMBRE,solicitudInscripcion.getPresentante().getNombre());
			tablaTATitu.setField(DboTATitu.CAMPO_CUR,solicitudInscripcion.getPresentante().getCodigoInstitucion());
			tablaTATitu.setField(DboTATitu.CAMPO_DES_INST,solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			tablaTATitu.setField(DboTATitu.CAMPO_TI_DOCU_PRES,solicitudInscripcion.getPresentante().getCodigoTipoDocumento());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_TIPO_PRES,solicitudInscripcion.getPresentante().getDescripcionTipoDocumento());
			tablaTATitu.setField(DboTATitu.CAMPO_LE_PRES,solicitudInscripcion.getPresentante().getNumeroDocumento());
			tablaTATitu.setField(DboTATitu.CAMPO_UB_GEOG_PRES, solicitudInscripcion.getPresentante().getCodigoPais()+solicitudInscripcion.getPresentante().getCodigoDepartamento()+solicitudInscripcion.getPresentante().getCodigoProvincia()+solicitudInscripcion.getPresentante().getCodigoDistrito());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_DEPARTAMENTO_PRES, solicitudInscripcion.getPresentante().getDescripcionDepartamento());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_PROVINCIA_PRES, solicitudInscripcion.getPresentante().getDescripcionProvincia());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_DISTRITO_PRES, solicitudInscripcion.getPresentante().getDescripcionDistrito());
			tablaTATitu.setField(DboTATitu.CAMPO_TI_VIA_PRES, solicitudInscripcion.getPresentante().getCodigoTipoVia());
			tablaTATitu.setField(DboTATitu.CAMPO_DE_VIA_PRES, solicitudInscripcion.getPresentante().getDescripcionTipoVia());
			tablaTATitu.setField(DboTATitu.CAMPO_ADDR, solicitudInscripcion.getPresentante().getDireccion());
			tablaTATitu.setField(DboTATitu.CAMPO_CO_POSTAL_PRES, solicitudInscripcion.getPresentante().getDireccion());
			tablaTATitu.setField(DboTATitu.CAMPO_MAIL_PRES, solicitudInscripcion.getPresentante().getCorreoElectronico());
			
			tablaTATitu.setField(DboTATitu.CAMPO_NO_TITU,null);
			tablaTATitu.setField(DboTATitu.CAMPO_RZ_SOCL,null);
			tablaTATitu.setField(DboTATitu.CAMPO_ES_TITU,null);
			tablaTATitu.setField(DboTATitu.CAMPO_ES_TITU_CALI,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FH_PRES,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FH_DIGI,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_EMPL_DIGI,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_EMPL_TECN,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_SECC,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_PSTO_TRAB,null);
			tablaTATitu.setField(DboTATitu.CAMPO_PU_CTRL,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_EMPL_REGI,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FE_VENC,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FE_DOCU_LEGA,null);
			tablaTATitu.setField(DboTATitu.CAMPO_OBSV,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_EMPL_DIGI_SECC,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FH_DIGI_SECC,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FH_INSC,null);
			tablaTATitu.setField(DboTATitu.CAMPO_TI_DOCU_LEGA,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_PACT_ESPE,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FG_PROV,null);
			tablaTATitu.setField(DboTATitu.CAMPO_NO_DIAS_PROR,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FG_SOLI_TACH,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_TIPO_RTTE,null);
			tablaTATitu.setField(DboTATitu.CAMPO_TI_PROP,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FH_MICSEG,null);
			tablaTATitu.setField(DboTATitu.CAMPO_CO_NOTA,null);
			tablaTATitu.setField(DboTATitu.CAMPO_TS_USUA_MODI,null);
			tablaTATitu.setField(DboTATitu.CAMPO_NO_DIAS_EXTR,null);
			tablaTATitu.setField(DboTATitu.CAMPO_FG_CANC,null);
			tablaTATitu.setField(DboTATitu.CAMPO_TI_DIAR,null);
			tablaTATitu.setField(DboTATitu.CAMPO_IN_FORM,null);
			
			
			tablaTATitu.add();

			/******************** VEHICULO *************************/
			DboTATituActoVehi tablaTATituActoVehi = new DboTATituActoVehi();
			tablaTATituActoVehi.setConnection(myConn);
			
			DboTATituPlac tablaTATituPlac = new DboTATituPlac();
			tablaTATituPlac.setConnection(myConn);
			
			ArrayList listaVehiculos = (ArrayList)solicitudInscripcion.getVehiculos();
			Vehiculo vehiculo = null;
			if (listaVehiculos!=null) {
				int size = listaVehiculos.size();
				for (int i=0; i<size; i++){

					//TA_TITU_ACTO_VEHI
					System.out.println("Insertando en TA_TITU_ACTO_VEHI");
					

					vehiculo = (Vehiculo)listaVehiculos.get(i);
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_AN_TITU,solicitudInscripcion.getAnho() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_NO_HOJA,solicitudInscripcion.getNumeroHoja() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_CO_ZONA,solicitudInscripcion.getCodigoZonaRegistral() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_CO_SEDE,solicitudInscripcion.getCodigoOficinaRegistral() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_NS_ACTO,i+1 );
					/** vehiculo **/
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_NO_PTDA,vehiculo.getNumeroPartida() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_CO_ACTO,vehiculo.getCodigoSubActo() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_DE_ACTO,vehiculo.getDescripcionSubActo() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_CO_FORM_PAGO,vehiculo.getCodigoFormaPago() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_FORM_PAGO,vehiculo.getDescripcionFormaPago() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_CO_TIPO_MONE,vehiculo.getCodigoMoneda() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_DE_MONE,vehiculo.getDescripcionMoneda() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_SALD,vehiculo.getSaldo().toString() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_OBSV,vehiculo.getObservaciones() );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_MONT,vehiculo.getMonto().toString());
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_MONT_PGDO,vehiculo.getPagado().toString());
					
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_NO_TITU,null );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_NO_SECU_PLAC,null );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_CO_CONC,null );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_IN_OPC1,null );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_IN_OPC2,null );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_IN_OPC3,null );
					tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_IN_PAGO,null );
                    tablaTATituActoVehi.setField(DboTATituActoVehi.CAMPO_NO_PLAC,null );
					
					tablaTATituActoVehi.add();

					//TA_TITU_PLAC
					System.out.println("Insertando en TA_TITU_PLAC");
					
					
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NS_TITU_PLAC, i+1 );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_AN_TITU, solicitudInscripcion.getAnho() );
					
					/** vehiculo **/
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NO_PLAC,vehiculo.getPlaca() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NO_SERI,vehiculo.getSerie() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NO_MOTR,vehiculo.getMotor() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_CO_REGI_DEST, vehiculo.getCodigoZonaRegistral() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_CO_SEDE_DEST, vehiculo.getCodigoOficinaRegistral() );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NO_PTDA, vehiculo.getNumeroPartida() );
					
					
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NO_TITU, null );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_FG_PROV, null );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_TI_SIST, null );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_IN_GENE, null );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_TS_USUA_MODI, null );
					tablaTATituPlac.setField(DboTATituPlac.CAMPO_NO_SECU_PLAC, null );
					
					tablaTATituPlac.add();


				    //String codComprador = vehiculo.getCodigoComprador();
					//String codVendedor = vehiculo.getCodigoVendedor();

					//String numDoc = null;

					DboTTProp tablaTTProp= new DboTTProp();
					tablaTTProp.setConnection(myConn);

					DboTTCtte tablaTTCtte = new DboTTCtte();
					tablaTTCtte.setConnection(myConn);


					/*********************************** PARTICIPANTES PERSONA NATURAL ********************/
					ArrayList listaParticipantesPN = (ArrayList)solicitudInscripcion.getParticipantesPersonaNatural();
					PersonaNatural personaNatural = null;
					if (listaParticipantesPN!=null) {
						int size2 = listaParticipantesPN.size();
						for (int j=0; j<size; j++){
							/** personaNatural**/
							personaNatural = (PersonaNatural)listaParticipantesPN.get(j);

				           // numDoc = personaNatural.getNumeroDocumento();

							//if (codComprador.equals(numDoc)){

								//TT_PROP
								System.out.println("Insertando en TT_PROP");
								
								tablaTTProp.setField(DboTATituPlac.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja() );
								tablaTTProp.setField(DboTATituPlac.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
								tablaTTProp.setField(DboTATituPlac.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
								tablaTTProp.setField(DboTATituPlac.CAMPO_AN_TITU, solicitudInscripcion.getAnho() ); 
								tablaTTProp.setField(DboTTProp.CAMPO_NS_PROP,i+1);
								tablaTTProp.setField(DboTTProp.CAMPO_TI_DOCU, personaNatural.getCodigoTipoDocumento());
								tablaTTProp.setField(DboTTProp.CAMPO_NO_DOCU, personaNatural.getNumeroDocumento());
								tablaTTProp.setField(DboTTProp.CAMPO_CO_ESTA_CIVL, personaNatural.getCodigoEstadoCivil());
								tablaTTProp.setField(DboTTProp.CAMPO_AP_PATE, personaNatural.getApellidoPaterno());
								tablaTTProp.setField(DboTTProp.CAMPO_AP_MATE, personaNatural.getApellidoMaterno());
							    tablaTTProp.setField(DboTTProp.CAMPO_NOMB, personaNatural.getNombre());
							    tablaTTProp.setField(DboTTProp.CAMPO_PAIS_ID, personaNatural.getCodigoNacionalidad());
					            tablaTTProp.setField(DboTTProp.CAMPO_DE_NCL, personaNatural.getDescripcionNacionalidad());
							    tablaTTProp.setField(DboTTProp.CAMPO_UB_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTATitu.CAMPO_DE_DEPARTAMENTO_PRES, personaNatural.getDescripcionDepartamento());
								tablaTTProp.setField(DboTATitu.CAMPO_DE_PROVINCIA_PRES, personaNatural.getDescripcionProvincia());
								tablaTTProp.setField(DboTATitu.CAMPO_DE_DISTRITO_PRES, personaNatural.getDescripcionDistrito());
					            tablaTTProp.setField(DboTTProp.CAMPO_TI_VIA, personaNatural.getCodigoTipoVia());
					            tablaTTProp.setField(DboTTProp.CAMPO_DE_VIA, personaNatural.getDescripcionTipoVia());
					            tablaTTProp.setField(DboTTProp.CAMPO_ADDR, personaNatural.getDireccion());
					            tablaTTProp.setField(DboTTProp.CAMPO_CO_POSTAL,personaNatural.getCodigoPostal());
					            tablaTTProp.setField(DboTTProp.CAMPO_MAIL,personaNatural.getCorreoElectronico());
					            
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_PLAC,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_TITU,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NS_ACTO,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_TI_PROP,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_IMPD,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_TRAN,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_RZ_SOCL,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_IN_PROP,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_EXPE_ANTE,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_FE_EXPE_ANTE,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_TARJ,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_FG_ACTI,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_DE_ESTA_CIVIL,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_DE_TI_DOCU,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_TRAN,null);
					            
					            
					            tablaTTProp.add();
					            
					            /*
								//TT_CTTE
					            System.out.println("Insertando en TT_CTTE");
								
								tablaTTCtte.setField(DboTTCtte.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja() );
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
								tablaTTCtte.setField(DboTTCtte.CAMPO_AN_TITU, solicitudInscripcion.getAnho() ); 
								tablaTTCtte.setField(DboTTCtte.CAMPO_NS_CTTE, i+1 ); 
								
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOCU, personaNatural.getCodigoTipoDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOCU, personaNatural.getNumeroDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_ESTA_CIVL, personaNatural.getCodigoEstadoCivil());
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_PATE, personaNatural.getApellidoPaterno());
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_MATE, personaNatural.getApellidoMaterno());
							    tablaTTCtte.setField(DboTTCtte.CAMPO_NOMB, personaNatural.getNombre());
							    tablaTTCtte.setField(DboTTCtte.CAMPO_CO_UBIC_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTTCtte.CAMPO_UB_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTTCtte.CAMPO_DE_DEPARTAMENTO_PRES, personaNatural.getDescripcionDepartamento());
								tablaTTProp.setField(DboTTCtte.CAMPO_DE_PROVINCIA_PRES, personaNatural.getDescripcionProvincia());
								tablaTTProp.setField(DboTTCtte.CAMPO_DE_DISTRITO_PRES, personaNatural.getDescripcionDistrito());
					            
					            tablaTTCtte.setField(DboTTCtte.CAMPO_ADDR, personaNatural.getDireccion());
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NO_TITU,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_TITU_EXPE,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_RGTR,null);
					       		tablaTTCtte.setField(DboTTCtte.CAMPO_NO_PTDA, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PROP, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_RZ_SOCL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_RTTE, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART_GRAL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_GRAV, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_PART, null);
								tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
								
					            tablaTTCtte.add();
					            */
							//}
							/*else if (codVendedor.equals(numDoc)){
						  		System.out.println("Insertando en TT_CTTE");
								
								tablaTTCtte.setField(DboTTCtte.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja() );
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
								tablaTTCtte.setField(DboTTCtte.CAMPO_AN_TITU, solicitudInscripcion.getAnho() ); 
								tablaTTCtte.setField(DboTTCtte.CAMPO_NS_CTTE, i+1 ); 
								
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOCU, personaNatural.getCodigoTipoDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOCU, personaNatural.getNumeroDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_ESTA_CIVL, personaNatural.getCodigoEstadoCivil());
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_PATE, personaNatural.getApellidoPaterno());
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_MATE, personaNatural.getApellidoMaterno());
							    tablaTTCtte.setField(DboTTCtte.CAMPO_NOMB, personaNatural.getNombre());
							    tablaTTCtte.setField(DboTTCtte.CAMPO_CO_UBIC_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTTCtte.CAMPO_UB_GEOG, personaNatural.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTTCtte.CAMPO_DE_DEPARTAMENTO_PRES, personaNatural.getDescripcionDepartamento());
								tablaTTProp.setField(DboTTCtte.CAMPO_DE_PROVINCIA_PRES, personaNatural.getDescripcionProvincia());
								tablaTTProp.setField(DboTTCtte.CAMPO_DE_DISTRITO_PRES, personaNatural.getDescripcionDistrito());
					            tablaTTCtte.setField(DboTTCtte.CAMPO_ADDR, personaNatural.getDireccion());
					            
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NO_TITU,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_TITU_EXPE,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_RGTR,null);
					       		tablaTTCtte.setField(DboTTCtte.CAMPO_NO_PTDA, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PROP, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_RZ_SOCL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_RTTE, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART_GRAL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_GRAV, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_PART, null);
								tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
								
					            tablaTTCtte.add();
							}
							*/
						
						}// FIN FOR PERSONA NATURALES

					}// FIN IF PERSONA NATURALES


		       		ArrayList listaParticipantesPJ= (ArrayList)solicitudInscripcion.getParticipantesPersonaJuridica();
					PersonaJuridica personaJuridica = null;
					if (listaParticipantesPJ!=null) {
						int size2 = listaParticipantesPJ.size();
						for (int j=0; j<size2; j++){
							/** personaJuridica**/
							personaJuridica = (PersonaJuridica)listaParticipantesPJ.get(j);

				           // numDoc = personaJuridica.getNumeroDocumento();

							//if (codComprador.equals(numDoc)){

								//TT_PROP
								System.out.println("Insertando en TT_PROP");
								
								tablaTTProp.setField(DboTTProp.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja() );
								tablaTTProp.setField(DboTTProp.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
								tablaTTProp.setField(DboTTProp.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
								tablaTTProp.setField(DboTTProp.CAMPO_AN_TITU, solicitudInscripcion.getAnho() ); 
								
								tablaTTProp.setField(DboTTProp.CAMPO_TI_DOCU, personaJuridica.getCodigoTipoDocumento());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_TI_DOCU, personaJuridica.getDescripcionTipoDocumento());
								tablaTTProp.setField(DboTTProp.CAMPO_NO_DOCU, personaJuridica.getNumeroDocumento());
								tablaTTProp.setField(DboTTProp.CAMPO_PAIS_ID, personaJuridica.getCodigoNacionalidad());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_NCL, personaJuridica.getDescripcionNacionalidad());
								tablaTTProp.setField(DboTTProp.CAMPO_RZ_SOCL, personaJuridica.getRazonSocial());
								tablaTTProp.setField(DboTTProp.CAMPO_NU_PART, personaJuridica.getNumeroPartida());
								tablaTTProp.setField(DboTTProp.CAMPO_CO_REGI, personaJuridica.getCodigoZonaRegistral());
								tablaTTProp.setField(DboTTProp.CAMPO_CO_OFIC_RGST, personaJuridica.getCodigoOficinaRegistral());
								tablaTTProp.setField(DboTTProp.CAMPO_UB_GEOG, personaJuridica.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaJuridica.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTTProp.CAMPO_DE_DEPARTAMENTO_PRES, personaJuridica.getDescripcionDepartamento());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_PROVINCIA_PRES, personaJuridica.getDescripcionProvincia());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_DISTRITO_PRES, personaJuridica.getDescripcionDistrito());
								tablaTTProp.setField(DboTTProp.CAMPO_TI_VIA, personaJuridica.getCodigoTipoVia());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_VIA, personaJuridica.getDescripcionTipoVia());
								tablaTTProp.setField(DboTTProp.CAMPO_ADDR, personaJuridica.getDireccion());
								tablaTTProp.setField(DboTTProp.CAMPO_CO_POSTAL, personaJuridica.getCodigoPostal());
								
					            tablaTTProp.setField(DboTTProp.CAMPO_AP_PATE, null);
								tablaTTProp.setField(DboTTProp.CAMPO_AP_MATE, null);
							    tablaTTProp.setField(DboTTProp.CAMPO_NOMB, null);
							    tablaTTProp.setField(DboTTProp.CAMPO_CO_ESTA_CIVL, null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_PLAC,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NS_PROP,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_TITU,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NS_ACTO,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_TI_PROP,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_IMPD,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_TRAN,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_IN_PROP,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_EXPE_ANTE,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_FE_EXPE_ANTE,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_NO_TARJ,null);
					            tablaTTProp.setField(DboTTProp.CAMPO_FG_ACTI,null);
					            
					            tablaTTProp.add();

								/*
								//TT_CTTE
					            System.out.println("Insertando en TT_CTTE");
								
								tablaTTCtte.setField(DboTTProp.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja() );
								tablaTTCtte.setField(DboTTProp.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
								tablaTTCtte.setField(DboTTProp.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
								tablaTTCtte.setField(DboTTProp.CAMPO_AN_TITU, solicitudInscripcion.getAnho() ); 
								
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOCU, personaJuridica.getCodigoTipoDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOCU, personaJuridica.getNumeroDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_UBIC_GEOG, personaJuridica.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTTProp.CAMPO_DE_DEPARTAMENTO_PRES, personaJuridica.getDescripcionDepartamento());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_PROVINCIA_PRES, personaJuridica.getDescripcionProvincia());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_DISTRITO_PRES, personaJuridica.getDescripcionDistrito());
					            tablaTTCtte.setField(DboTTCtte.CAMPO_ADDR, personaJuridica.getDireccion());
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_PATE, null);
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_MATE, null);
							    tablaTTCtte.setField(DboTTCtte.CAMPO_NOMB, null);
							    tablaTTCtte.setField(DboTTCtte.CAMPO_NO_TITU,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_TITU_EXPE,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_CTTE,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_RGTR,null);
					       		tablaTTCtte.setField(DboTTCtte.CAMPO_CO_ESTA_CIVL, null);
					       		tablaTTCtte.setField(DboTTCtte.CAMPO_NO_PTDA, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PROP, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_RZ_SOCL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_RTTE, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART_GRAL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_GRAV, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_PART, null);
								tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
								
					            tablaTTCtte.add();
							}
							else if (codVendedor.equals(numDoc)){
						  		System.out.println("Insertando en TT_CTTE");
								
								tablaTTCtte.setField(DboTTProp.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja() );
								tablaTTCtte.setField(DboTTProp.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
								tablaTTCtte.setField(DboTTProp.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
								tablaTTCtte.setField(DboTTProp.CAMPO_AN_TITU, solicitudInscripcion.getAnho() ); 
								
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOCU, personaJuridica.getCodigoTipoDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOCU, personaJuridica.getNumeroDocumento());
								tablaTTCtte.setField(DboTTCtte.CAMPO_CO_UBIC_GEOG, personaJuridica.getCodigoPais()+personaNatural.getCodigoDepartamento()+personaNatural.getCodigoProvincia()+personaNatural.getCodigoDistrito());
					            tablaTTProp.setField(DboTTProp.CAMPO_DE_DEPARTAMENTO_PRES, personaJuridica.getDescripcionDepartamento());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_PROVINCIA_PRES, personaJuridica.getDescripcionProvincia());
								tablaTTProp.setField(DboTTProp.CAMPO_DE_DISTRITO_PRES, personaJuridica.getDescripcionDistrito());
					            tablaTTCtte.setField(DboTTCtte.CAMPO_ADDR, personaJuridica.getDireccion());
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_PATE, null);
								tablaTTCtte.setField(DboTTCtte.CAMPO_AP_MATE, null);
							    tablaTTCtte.setField(DboTTCtte.CAMPO_NOMB, null);
							    tablaTTCtte.setField(DboTTCtte.CAMPO_NO_TITU,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_TITU_EXPE,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_CTTE,null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_RGTR,null);
					       		tablaTTCtte.setField(DboTTCtte.CAMPO_CO_ESTA_CIVL, null);
					       		tablaTTCtte.setField(DboTTCtte.CAMPO_NO_PTDA, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PROP, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_RZ_SOCL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_FG_RTTE, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_PART_GRAL, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_CO_TIPO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NO_DOC2, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_GRAV, null);
					            tablaTTCtte.setField(DboTTCtte.CAMPO_NS_PART, null);
								tablaTTCtte.setField(DboTTCtte.CAMPO_NS_ACTO, null);
								
					            tablaTTCtte.add();							}
								*/
						}//FIN FOR PERSONA JURIDICA
					}// FIN DEL IF PESONA JURIDICA



				}// FOR VEHICULOS

			}//IF VEHICULOS

			//TA_TITU_DOCU
			System.out.println("Insertando en TA_TITU_DOCU");
			DboTATituDocu tablaTATituDocu = new DboTATituDocu();
			tablaTATituDocu.setConnection(myConn);
			ArrayList listaInstrumentosPublico = (ArrayList)solicitudInscripcion.getInstrumentoPublico();
			InstrumentoPublico instrumentoPublico = null;
			if (listaInstrumentosPublico!=null) {
				int size = listaInstrumentosPublico.size();
				for (int i=0; i<size; i++){
					instrumentoPublico = (InstrumentoPublico)listaInstrumentosPublico.get(i);

					tablaTATituDocu.setField(DboTATituDocu.CAMPO_AN_TITU, solicitudInscripcion.getAnho());
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_NO_HOJA, solicitudInscripcion.getNumeroHoja());
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_CO_SEDE, solicitudInscripcion.getCodigoOficinaRegistral() );
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_CO_ZONA, solicitudInscripcion.getCodigoZonaRegistral() );
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_NS_DOCU, i+1);
					/*** instrumentoPublico*/
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_NS_ACTO,solicitudInscripcion.getDescripcionActo() );
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_CO_TIPO_DOCU_LEGA,instrumentoPublico.getCodigoTipoInstrumento() );
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_DE_TIPO_DOCU_LEGA,instrumentoPublico.getDescripcionTipoInstrumento() );
					dia = instrumentoPublico.getFecha().substring(6,8);
					mes = instrumentoPublico.getFecha().substring(4,6);
					anho = instrumentoPublico.getFecha().substring(0,4);
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_FE_DOCU_LEGA, FechaUtil.stringToOracleString(dia+"/"+mes+"/"+anho));
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_UB_GEOG, instrumentoPublico.getLugar() );
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_DE_OBSE, instrumentoPublico.getOtros() );
					
					/*** */
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_CO_ACTO,null );
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_NO_TITU,null );
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_TI_FUNC, null);
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_EMIS, null);
					tablaTATituDocu.setField(DboTATituDocu.CAMPO_NS_ACTO,null );

					tablaTATituDocu.add();
				}
			}


			//TT_PAGO
			System.out.println("Insertando en TT_PAGO");
			DboTTPago tablaTTPago = new DboTTPago();
			tablaTTPago.setConnection(myConn);
			/*** solicitudInscripcion*/
			tablaTTPago.setField(DboTTPago.CAMPO_NU_HOJA_PRES, solicitudInscripcion.getNumeroHoja());
			tablaTTPago.setField(DboTTPago.CAMPO_AA_HOJA_PRES, solicitudInscripcion.getAnho());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI_PRES, solicitudInscripcion.getCodigoZonaRegistral());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST_PRES, solicitudInscripcion.getCodigoOficinaRegistral());
			/*** datosPago*/
			tablaTTPago.setField(DboTTPago.CAMPO_MO_SERV, solicitudInscripcion.getDatosPago().getCostoTotalServicio().toString());
			tablaTTPago.setField(DboTTPago.CAMPO_CO_FR_PAGO, solicitudInscripcion.getDatosPago().getCodigoFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_FR_PAGO, solicitudInscripcion.getDatosPago().getDescripcionFormaPago());
			tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, solicitudInscripcion.getDatosPago().getNumeroOperacion());
			dia = solicitudInscripcion.getDatosPago().getFechaPago().substring(6,8);
			mes = solicitudInscripcion.getDatosPago().getFechaPago().substring(4,6);
			anho = solicitudInscripcion.getDatosPago().getFechaPago().substring(0,4);
			hh = solicitudInscripcion.getDatosPago().getHoraPago().substring(0,2);
			mm = solicitudInscripcion.getDatosPago().getHoraPago().substring(2,4);
			ss = solicitudInscripcion.getDatosPago().getHoraPago().substring(4,6);
			tablaTTPago.setField(DboTTPago.CAMPO_FE_PAGO,  FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
			tablaTTPago.setField(DboTTPago.CAMPO_CO_TIPO_PAGO, solicitudInscripcion.getDatosPago().getCodigoTipoPago());
			tablaTTPago.setField(DboTTPago.CAMPO_DE_TIPO_PAGO, solicitudInscripcion.getDatosPago().getDescripcionTipoPago());
			/***  */
			tablaTTPago.setField(DboTTPago.CAMPO_AA_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_NU_TITU, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI, null);
			tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST, null);
			tablaTTPago.add();


			} catch (DBException dbe) {
			System.out.println("EXCEPCION transferencia Vehicular");
			dbe.printStackTrace();
			throw dbe;

		} catch (Throwable ex) {
			System.out.println("EXCEPCION1111111111111111111TransfVehicular");
			ex.printStackTrace();
			throw ex;

		}

	}

	/** TRANSFERENCIA VEHICULAR FIN 03/10/06**/

	private File generaArchivo(String rtfFile) {

		File f = null;

		try {
			System.out.println("comienzo de generaArchivo");
			f = new File("/notarios/SolicitudInscripcion.rtf");
			File aux = new File(f.getParentFile().toString());

			if (!aux.exists())
				aux.mkdirs();

			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] arregloTexto = convierteTiraEnArregloValidaVacio(rtfFile, "\n");

			for (int i = 0; i < arregloTexto.length; i++) {
				bw.write(arregloTexto[i]);
				bw.newLine();
			}

			bw.close();
			fw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}

	private String[] convierteTiraEnArregloValidaVacio(String tira, String separador) {
		int idxAnt = 0;
		String tokenDerecha = tira;
		List listaTokens = new ArrayList();

		while(idxAnt>=0){
			int idxOfSeparador = tokenDerecha.indexOf(separador);

			String tokenActual = null;
			if(idxOfSeparador >= 0){
				tokenActual = tokenDerecha.substring(0, idxOfSeparador);
				tokenDerecha = tokenDerecha.substring(idxOfSeparador + separador.length(), tokenDerecha.length() );
			}else{
				tokenActual = tokenDerecha;
				tokenDerecha = null;
			}
			idxAnt = idxOfSeparador;
			listaTokens.add(tokenActual);
		}

		String[] arrTokens = new String[listaTokens.size()];
		for(int i=0; i<listaTokens.size(); i++){
			arrTokens[i] = (String) listaTokens.get(i);
		}

		return arrTokens;
	}


	public String getCuoSolicitudPresentacion() {
		return cuoSolicitudPresentacion;
	}


	public void setCuoSolicitudPresentacion(String cuoSolicitudPresentacion) {
		this.cuoSolicitudPresentacion = cuoSolicitudPresentacion;
	}


	public String getFechaActualSolicitudPresentacion() {
		return fechaActualSolicitudPresentacion;
	}


	public void setFechaActualSolicitudPresentacion(
			String fechaActualSolicitudPresentacion) {
		this.fechaActualSolicitudPresentacion = fechaActualSolicitudPresentacion;
	}


	public String getNumeroHojaSolicitudPresentacion() {
		return numeroHojaSolicitudPresentacion;
	}


	public void setNumeroHojaSolicitudPresentacion(
			String numeroHojaSolicitudPresentacion) {
		this.numeroHojaSolicitudPresentacion = numeroHojaSolicitudPresentacion;
	}


}