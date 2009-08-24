package gob.pe.sunarp.extranet.webservices;

import gob.pe.sunarp.extranet.common.Errors;
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
import gob.pe.sunarp.extranet.framework.xml.XWebappResources;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.solicitud.inscripcion.PresentacionSolicitudInscripcion;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.InstrumentoPublico;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Partida;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaNatural;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.Presentante;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.InstrumentoPublico;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Partida;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Vehiculo;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPIReqBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPJReqBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPNReqBean;
import gob.pe.sunarp.extranet.webservices.bean.BuscaPartidaXPVReqBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPersonaJuridicaBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPersonaNaturalBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPropiedadInmuebleBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaXPropiedadVehicularBean;
import gob.pe.sunarp.extranet.webservices.logic.PersonaJuridicaLogic;
import gob.pe.sunarp.extranet.webservices.logic.PersonaNaturalLogic;
import gob.pe.sunarp.extranet.webservices.logic.PropiedadInmuebleLogic;
import gob.pe.sunarp.extranet.webservices.logic.PropiedadVehicularLogic;
import gob.pe.sunarp.extranet.webservices.util.Errores;
import gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre.bean.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import gob.pe.sunarp.extranet.solicitud.inscripcion.* ;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import pe.com.avatar.xml.ParserXML;
import pe.com.avatar.xml.XMLManager;
import sun.misc.BASE64Encoder;

import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
public class SunarpServiceProvider extends ServiceProvider 
{
	/**
	 * @param xmlForm
	 * @param xmlHash
	 * @param rtfFile
	 * @return
	 */
	public String setNewRegisterRequest (String xmlForm, String xmlHash, String rtfFile) 
	{
		String xmlRespuesta = "";
		String ipRemota="";
		int respuesta = Errors.WS_GENERAL_ERROR ;//ERROR GENERAL
		String hashXMLObtenido = generaHashMD5(xmlForm);
		
		System.out.println("********** XMLFrom = " + xmlForm);
		System.out.println("********** XMLHash = " + xmlHash);
		System.out.println("********** HASHXMLOBTENIDO = " + hashXMLObtenido);
		
		try
		{
			ipRemota = obtenerIPRemota(getServletRequest());
		}catch (Exception e) 
		{	
			e.printStackTrace();	
		}
	
		
		SolicitudInscripcion solicitudInscripcion = null;
		UsuarioBean usuario = null;
		PresentacionSolicitudInscripcion presentacionSolicitudInscripcion = null;
	
		if (!hashXMLObtenido.equals(xmlHash)){
			respuesta = Errors.WS_DIFFERENT_HASH;//ERROR HASH
			xmlRespuesta = generarTramaEnvioRespuesta(presentacionSolicitudInscripcion, respuesta,detalleRespuesta(respuesta) );

			System.out.println("Trama que se envia como respuesta    ::" + xmlRespuesta);
			System.out.println("Codigo de que se envia como respuesta::" + respuesta);			
			return xmlRespuesta;			
		}
		
		
		boolean res = false;
		
		System.out.println("XML recibido::"+xmlForm);
    	System.out.println("Archivo rtf recibido::::::::"+rtfFile);
		
		try{	
			//Cargamos la estrutura xml en el bean de solicitud de inscripcion
			solicitudInscripcion = loadXMLtoBean(xmlForm);
		}
		catch(Exception e){
			respuesta = Errors.WS_PARSER_ERROR;//ERROR DE PARSEO
			xmlRespuesta = generarTramaEnvioRespuesta(presentacionSolicitudInscripcion, respuesta,detalleRespuesta(respuesta) );
			
			System.out.println("Trama que se envia como respuesta    ::" + xmlRespuesta);
			System.out.println("Codigo de que se envia como respuesta::" + respuesta);			
			return xmlRespuesta;
		}
		solicitudInscripcion.setIpRemota(ipRemota);	
		try{
			
			//Validamos si la operacion ya fue ejecutada
			res = validaOperacion(solicitudInscripcion, xmlForm, rtfFile);
			if (res==false){
				respuesta = Errors.WS_TRANSACTION_ALREADY_PROCESSED;//TRANSACCION YA FUE PROCESADA
				xmlRespuesta = generarTramaEnvioRespuesta(presentacionSolicitudInscripcion, respuesta,detalleRespuesta(respuesta) );

				System.out.println("Trama que se envia como respuesta    ::" + xmlRespuesta);
				System.out.println("Codigo de que se envia como respuesta::" + respuesta);				
				return xmlRespuesta;
			}

			//Obtenemos los datos del usuario
			usuario = getUserBean(solicitudInscripcion.getCodigoUsuario());
			
			//Procesamos la solicitud de inscripcion
			presentacionSolicitudInscripcion = new PresentacionSolicitudInscripcion();
			respuesta = presentacionSolicitudInscripcion.procesarSolicitud(solicitudInscripcion, rtfFile, usuario);			
			xmlRespuesta = generarTramaEnvioRespuesta(presentacionSolicitudInscripcion, respuesta, detalleRespuesta(respuesta) );			
		} 
		catch (CustomException e) {
			respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAl
			xmlRespuesta=generarTramaEnvioRespuesta(presentacionSolicitudInscripcion, respuesta, detalleRespuesta(respuesta) );
		}
		catch (DBException dbe) {
			respuesta = Errors.WS_BD_EXTRANET_ERROR;//ERROR ACCEDIENDO A BD EXTRANET
			xmlRespuesta=generarTramaEnvioRespuesta(presentacionSolicitudInscripcion, respuesta, detalleRespuesta(respuesta) );
		} 
		catch(Throwable t){
			respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAl
			xmlRespuesta=generarTramaEnvioRespuesta(presentacionSolicitudInscripcion, respuesta, detalleRespuesta(respuesta) );
		}

		System.out.println("Trama que se envia como respuesta    ::" + xmlRespuesta);
		System.out.println("Codigo de que se envia como respuesta::" + respuesta);			

		return xmlRespuesta;
	}

	private String generarTramaEnvioRespuesta(PresentacionSolicitudInscripcion presentacionSolicitudInscripcion, int codigoRespuesta, String detalleRespuesta){
		String respuesta;
		StringBuffer sb = null;
		String anhoHojaPresentacion="";
		String numeroHojaPresentacion="";
		String cuo="";
		
		if(presentacionSolicitudInscripcion!=null){
			anhoHojaPresentacion = presentacionSolicitudInscripcion.getFechaActualSolicitudPresentacion();
			numeroHojaPresentacion = presentacionSolicitudInscripcion.getNumeroHojaSolicitudPresentacion();
			cuo = presentacionSolicitudInscripcion.getCuoSolicitudPresentacion();
		}
			
		
		sb = new StringBuffer();
		sb.append("<?xml version="+"\"" + "1.0" +"\"" + " encoding="+"\""+"UTF-8"+"\""+ "?>");
		//sb.append("<escrituraPublica xmlns="+"\""+ "http://gob.pe.sunarp.extranet/servicios/escriturapublica" +"\""+ ">");		
		sb.append("<resultadoSolicitudInscripcion>");
		sb.append("<anhoHojaPresentacion>").append(anhoHojaPresentacion).append("</anhoHojaPresentacion>");
		sb.append("<numeroHojaPresentacion>").append(numeroHojaPresentacion).append("</numeroHojaPresentacion>");		
		sb.append("<cuo>").append(cuo).append("</cuo>");
		sb.append("<codigoResultado>").append(String.valueOf(codigoRespuesta)).append("</codigoResultado>");		
		sb.append("<descripcionResultado>").append(detalleRespuesta).append("</descripcionResultado>");
		sb.append("</resultadoSolicitudInscripcion>");
		//sb.append("</escrituraPublica>");		
		respuesta = sb.toString();		
		return respuesta;
	}
	private String detalleRespuesta(int respuesta){
		String strRespuesta;
 
		/**
		 * Constantes error webservices
		 * 
		 * WS_EXITO = 0
		 * PARSER_ERROR = 1;
		 * USER_NOT_ALLOWED = 2;
		 * INSUFFICENT_COST = 3;
		 * TRANSACTION_ALREADY_PROCESSED = 4;
		 * INSUFFICENT_FOUNDS = 6;
		 * GENERAL_ERROR = 7;
		 * BD_EXTRANET_ERROR = 8;
		 * BD_SIR_ERROR = 9;
		 * TYPE_OF_PAYMENT_NOT_AVAILABLE = 10;
		 * DIFFERENT_HASH = 11;
		 * VALIDACION_RENIEC_ERROR = 12;	
		 * 	
		*/
		switch (respuesta) {
		case Constantes.WS_EXITO :
			strRespuesta ="LA SOLICITUD HA SIDO REGISTRADA CON EXITO";
			break;
		
		case Constantes.WS_PARSER_ERROR :
			strRespuesta ="ERROR AL PARSEAR LA TRAMA DE ENVIO";
			break;
		case Constantes.WS_USER_NOT_ALLOWED :
			strRespuesta = "USUARIO NO PERMITIDO";
			break;
		case Constantes.WS_INSUFFICENT_COST :
			strRespuesta = "SALDO INSUFICIENTE";
			break;
		case Constantes.WS_TRANSACTION_ALREADY_PROCESSED :
			strRespuesta = "LA TRANSACCION YA FUE PROCESADA";
			break;
		case Constantes.WS_INSUFFICENT_FOUNDS :
			strRespuesta = "DATOS INSUFIENCIENTES";
			break;						
		case Constantes.WS_GENERAL_ERROR :
			strRespuesta = "ERROR GENERAL";
			break;						
		case Constantes.WS_BD_EXTRANET_ERROR :
			strRespuesta = "ERROR DE BASE DE DATOS";
			break;						
		case Constantes.WS_BD_SIR_ERROR :
			strRespuesta = "ERROR DE BASE DE DATOS SIR";
			break;						
		case Constantes.WS_TYPE_OF_PAYMENT_NOT_AVAILABLE :
			strRespuesta = "TYPE OF PAYMENT NOT AVAILABLE";
			break;						
		case Constantes.WS_DIFFERENT_HASH :
			strRespuesta = "DIFFERENT HASH";
			break;
		case Constantes.WS_VALIDACION_RENIEC_ERROR :
			strRespuesta = "ERROR VALIDANDO EL DOCUMENTO CON RENIEC";
			break;

		default:
			strRespuesta = "ERROR GENERAL";
			break;
		}
		return strRespuesta;
	} 	

	/*************************** METODO PARA LA SOLICITUD DE BUSQUEDA INICIO  ***************************************/
	
	/*public int setNewRegisterRequest2 (String xmlForm, String xmlHash) {
		String hashXMLObtenido = generaHashMD5(xmlForm);
		
		System.out.println("********** XMLHash = " + xmlHash);
		System.out.println("********** HASHXMLOBTENIDO = " + hashXMLObtenido);
		
		//if (!hashXMLObtenido.equals(xmlHash))
			//return Errors.WS_DIFFERENT_HASH;//ERROR HASH
		
		//SolicitudInscripcion solicitudInscripcion = null;
		UsuarioBean usuario = null;
		PresentacionSolicitudBusqueda presentacionSolicitudBusqueda = null;
		SolicitudBusqueda solicitudBusqueda = null;
		int respuesta = Errors.WS_GENERAL_ERROR ;//ERROR GENERAL
		boolean res = false;
		
		System.out.println("XML recibido::"+xmlForm);
    	//System.out.println("Archivo rtf recibido::::::::"+rtfFile);
		
		try{	
			//Cargamos la estrutura xml en el bean de solicitud de inscripcion
			solicitudBusqueda = loadXMLtoBeanBusquedaJuridica(xmlForm);
		}
		catch(Exception e){
			respuesta = Errors.WS_PARSER_ERROR;//ERROR DE PARSEO
		}
			
		try{
			
			//Validamos si la operacion ya fue ejecutada
			res = validaOperacion2(solicitudBusqueda, xmlForm);
			if (res==false)
				return Errors.WS_TRANSACTION_ALREADY_PROCESSED;//TRANSACCION YA FUE PROCESADA

			//Obtenemos los datos del usuario
			usuario = getUserBean(solicitudBusqueda.getCodigoUsuario());
			
			//Procesamos la solicitud de inscripcion
			presentacionSolicitudBusqueda = new gob.pe.sunarp.extranet.solicitud.inscripcion.PresentacionSolicitudBusqueda();
			respuesta = presentacionSolicitudBusqueda.procesarSolicitud(solicitudBusqueda,usuario);			
		
		} 
		catch (CustomException e) {
			respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAl
		}
		catch (DBException dbe) {
			respuesta = Errors.WS_BD_EXTRANET_ERROR;//ERROR ACCEDIENDO A BD EXTRANET
		} 
		catch(Throwable t){
			respuesta = Errors.WS_GENERAL_ERROR;//ERROR GENERAl
		}

		System.out.println("Codigo Retorno::"+respuesta);
		return respuesta;
	}
	*/
	/*************************** METODO PARA LA SOLICITUD DE BUSQUEDA FIN  ***************************************/

	/**
	 * @param solicitudInscripcion
	 * @param xmlForm
	 * @param rtfFile
	 * @return
	 * @throws DBException
	 * @throws Throwable
	 */
	private boolean validaOperacion(SolicitudInscripcion solicitudInscripcion, String xmlForm, String rtfFile) throws DBException, Throwable {
		
		DBConnectionFactory pool = null;
		Connection conn1 = null;
		
		File archivo1 = null;
		File archivo2 = null;
		String sentenciaSql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean res = false;
		String secuencial = null;
		String reg = "";
		String ruta_xml, ruta_rtf, fecha_hora, fecha_actual;
			
		try { 
			
			System.out.println("validaOperacion....");
			
			//HACIA LA BODEGA CENTRAL
			pool = DBConnectionFactory.getInstance();
			conn1 = pool.getConnection();
			conn1.setAutoCommit(false);
			
			StringBuffer sentenciaSql2 = new StringBuffer();
			
			sentenciaSql2.append("SELECT COUNT(*) AS TMP FROM OPERACION ");
			sentenciaSql2.append("WHERE OPERACION.CUO = ").append(solicitudInscripcion.getCuo()).append(" AND ");
			sentenciaSql2.append("OPERACION.ESTADO =").append(0).append(" AND ");
			sentenciaSql2.append("OPERACION.SERVICIO_ID =").append(solicitudInscripcion.getCodigoServicio()).append(" ");
			pstmt = conn1.prepareStatement(sentenciaSql2.toString());
		
			System.out.println("query validacion - "+ sentenciaSql2.toString());
			
			rs = pstmt.executeQuery(); // Execute the statement				  					 
			if(rs.next())
			{
				reg = rs.getString(1);
			}
			if (Integer.parseInt(reg)>0)
				return false;
			
			sentenciaSql = "SELECT id_sec.nextval AS SECUENCIA FROM DUAL";
			pstmt = conn1.prepareStatement(sentenciaSql);
			rs = pstmt.executeQuery();
			
			rs.next();
			secuencial = rs.getString("SECUENCIA");
			
			solicitudInscripcion.setSecuencialOperacion(secuencial);
			
			java.util.Calendar cal = java.util.Calendar.getInstance();
			
			fecha_actual = FechaUtil.getCurrentDateTime();
			fecha_hora = fecha_actual.substring(0,2)+
						 fecha_actual.substring(3,5)+
						 fecha_actual.substring(6,10)+
						 fecha_actual.substring(11,13)+
						 fecha_actual.substring(14,16)+
						 fecha_actual.substring(17,19);
			
			//SAUL CREAR UN ARCHIVO PROPERTIES PARA GENERAR LA RUTA			
			ResourceBundle rutaProperties = ResourceBundle.getBundle("gob.pe.sunarp.extranet.common.properties.wsPCM");			
			ruta_xml = rutaProperties.getString("ruta_xml") + solicitudInscripcion.getCodigoServicio()+"_"+secuencial+"_"+fecha_hora+".xml";
			ruta_rtf = rutaProperties.getString("ruta_rtf") + solicitudInscripcion.getCodigoServicio()+"_"+secuencial+"_"+fecha_hora+".rtf";			
			String Separador = System.getProperty("file.separator");
			
			archivo1 = generaArchivo(xmlForm, ruta_xml);
			archivo2 = generaArchivo(rtfFile, ruta_rtf);
			System.out.println("archivo1 ruta_xml::"+archivo1);
			System.out.println("archivo1 ruta_rtf::"+archivo2);				
			System.out.println("Insertando en OPERACION");
			sentenciaSql = "INSERT INTO OPERACION( SECUENCIA, " +
												  "CUO, " +
												  "SERVICIO_ID, " +
												  "ID_USUARIO, "+
								  				  "CUR, " +
								  				  "NO_CUR, " +
								  				  "FECHA, " +
								  				  "ESTADO, " +
								  				  "RUTA_XML, " +
								  				  "RUTA_RTF)"+
								  				 "VALUES(?,?,?,?,?,?,TO_DATE(?,'dd/MM/yyyy HH24:MI:SS'),?,?,?)";
			
			pstmt = conn1.prepareStatement(sentenciaSql);
			pstmt.setInt(1, Integer.parseInt(secuencial));
			pstmt.setString(2,solicitudInscripcion.getCuo());
			pstmt.setString(3,solicitudInscripcion.getCodigoServicio());
			pstmt.setString(4,solicitudInscripcion.getCodigoUsuario());
			pstmt.setString(5,solicitudInscripcion.getPresentante().getCodigoInstitucion());
			pstmt.setString(6,solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			pstmt.setString(7, fecha_actual);
			pstmt.setString(8,"1");//ESTADO PENDIENTE
			pstmt.setString(9, ruta_xml);
			pstmt.setString(10, ruta_rtf);
			
			pstmt.execute(); // Execute the statement
			
			conn1.commit();
			
			res = true;

		} catch (DBException dbe) {
			dbe.printStackTrace();
			conn1.rollback();
			throw dbe;
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			conn1.rollback();
			throw ex;

		} finally {
			try{
				if (pstmt !=null)
					pstmt.close();
			}
			catch (Throwable t)
			{
				throw t;
			}
		}
		
		return res;
		
	}
	
	/**************************** para validar la solicitud de busqueda inicio ***********************************/
/*
	private boolean validaOperacion2(SolicitudBusqueda solicitudBusqueda, String xmlForm) throws DBException, Throwable {
		
		DBConnectionFactory pool = null;
		Connection conn1 = null;
		
		File archivo1 = null;
		String sentenciaSql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean res = false;
		String secuencial = null;
		int reg = 0;
		String ruta_xml, fecha_hora, fecha_actual;
			
		try { 
			
			System.out.println("validaOperacion....");
			
			//HACIA LA BODEGA CENTRAL
			pool = DBConnectionFactory.getInstance();
			conn1 = pool.getConnection();
			conn1.setAutoCommit(false);
			
			sentenciaSql = "SELECT COUNT(*) AS TMP FROM OPERACION "+
						   "WHERE OPERACION.CUO = ? AND OPERACION.ESTADO = ? AND OPERACION.SERVICIO_ID = ?";
			pstmt = conn1.prepareStatement(sentenciaSql);
			pstmt.setString(1,solicitudBusqueda.getCuo());
			pstmt.setString(2,"0");//ESTADO PROCESADO
			pstmt.setString(3,solicitudBusqueda.getCodigoServicio());
			rs = pstmt.executeQuery(); // Execute the statement				  					 
			rs.next();
			reg = rs.getInt("TMP");
			if (reg>0)
				return false;
			
			sentenciaSql = "SELECT id_sec.nextval AS SECUENCIA FROM DUAL";
			pstmt = conn1.prepareStatement(sentenciaSql);
			rs = pstmt.executeQuery();
			
			rs.next();
			secuencial = rs.getString("SECUENCIA");
			System.out.println("secuencial::"+secuencial);
			solicitudBusqueda.setSecuencialOperacion(secuencial);
			
			java.util.Calendar cal = java.util.Calendar.getInstance();
			
			fecha_actual = FechaUtil.getCurrentDateTime();
			fecha_hora = fecha_actual.substring(0,2)+
						 fecha_actual.substring(3,5)+
						 fecha_actual.substring(6,10)+
						 fecha_actual.substring(11,13)+
						 fecha_actual.substring(14,16)+
						 fecha_actual.substring(17,19);
			ruta_xml = "C:\\TEMP\\"+solicitudBusqueda.getCodigoServicio()+"_"+secuencial+"_"+fecha_hora+".xml";
			archivo1 = generaArchivo(xmlForm, ruta_xml);
			
			System.out.println("Insertando en OPERACION");
			sentenciaSql = "INSERT INTO OPERACION(SECUENCIA, CUO, SERVICIO_ID, ID_USUARIO, "+
								  				 "CUR, NO_CUR, FECHA, ESTADO, RUTA_XML)"+
								  				 "VALUES(?,?,?,?,?,?,TO_DATE(?,'dd/MM/yyyy HH24:MI:SS'),?,?,?)";
			
			pstmt = conn1.prepareStatement(sentenciaSql);
			pstmt.setInt(1, Integer.parseInt(secuencial));
			pstmt.setString(2,solicitudBusqueda.getCuo());
			pstmt.setString(3,solicitudBusqueda.getCodigoServicio());
			pstmt.setString(4,solicitudBusqueda.getCodigoUsuario());
			pstmt.setString(5,solicitudBusqueda.getCodigoInstitucion());
			pstmt.setString(6,solicitudBusqueda.getDescripcionInstitucion());
			pstmt.setString(7, fecha_actual);
			pstmt.setString(8,"1");//ESTADO PENDIENTE
			pstmt.setString(9, ruta_xml);
						
			pstmt.execute(); // Execute the statement
			
			conn1.commit();
			
			res = true;

		} catch (DBException dbe) {
			dbe.printStackTrace();
			conn1.rollback();
			throw dbe;
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			conn1.rollback();
			throw ex;

		} finally {
			try{
				if (pstmt !=null)
					pstmt.close();
			}
			catch (Throwable t)
			{
				throw t;
			}
		}
		
		return res;
		
	}
	*/
	/************************************ para validar la solicitud de busqueda fin ********************************/
	
	/**
	 * @param datosXML
	 * @return
	 * @throws Exception
	 */
	public SolicitudInscripcion loadXMLtoBean(String datosXML) throws Exception{ //carga la cabecera
		
		ParserXML objeto = new ParserXML();
		NodeList nodeList = null;
		int size = 0;

		SolicitudInscripcion solicitudInscripcion = null;
		Presentante presentante = null;
		PersonaJuridica personaJuridica = null;
		Capital capital = null;
		ReservaMercantil reservaMercantil = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		PersonaNatural personaNatural = null;
		ArrayList listaInstrumentos = null;	
		InstrumentoPublico instrumentoPublico = null;
		DatosPago datosPago = null;	
		
				
		try {

			Document doc = objeto.getDocument(datosXML);
					
			solicitudInscripcion = new SolicitudInscripcion();
			System.out.println("SOLICITUD INCRIPCION");	
			solicitudInscripcion.setCodigoArea(XMLManager.getXMLValue(datosXML, Constantes.CODIGOAREA));
			solicitudInscripcion.setDescripcionArea(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONAREA));
			solicitudInscripcion.setCodigoActo(XMLManager.getXMLValue(datosXML, Constantes.CODIGOACTO));
			solicitudInscripcion.setDescripcionActo(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONACTO));
			solicitudInscripcion.setCodigoLibro(XMLManager.getXMLValue(datosXML, Constantes.CODIGOLIBRO));
			solicitudInscripcion.setDescripcionLibro(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONLIBRO));
			solicitudInscripcion.setCodigoZonaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRAL));
			solicitudInscripcion.setCodigoOficinaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRAL));
			solicitudInscripcion.setCuo(XMLManager.getXMLValue(datosXML, Constantes.CUO));
			//######### modificado jbugarin 12/09/06 #########/
			solicitudInscripcion.setCodigoUsuario(XMLManager.getXMLValue(datosXML, Constantes.CODIGOUSUARIO));
			//######### modificado jbugarin 12/09/06 #########/
			solicitudInscripcion.setCodigoServicio(XMLManager.getXMLValue(datosXML, Constantes.CODIGOSERVICIO));
			solicitudInscripcion.setDescripcionServicio(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONSERVICIO));
			
			/*
			System.out.println(Constantes.CODIGOAREA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOAREA));
			System.out.println(Constantes.DESCRIPCIONAREA+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONAREA));
			System.out.println(Constantes.CODIGOACTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOACTO));
			System.out.println(Constantes.DESCRIPCIONACTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONACTO));	
			System.out.println(Constantes.CODIGOLIBRO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOLIBRO));
			System.out.println(Constantes.DESCRIPCIONLIBRO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONLIBRO));
			System.out.println(Constantes.CODIGOZONAREGISTRAL+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRAL));
			System.out.println(Constantes.CODIGOOFICINAREGISTRAL+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRAL));
			System.out.println(Constantes.CUO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CUO));
			//######### modificado jbugarin 12/09/06 #######/
			System.out.println(Constantes.CODIGOUSUARIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOUSUARIO));
			//--######## modificado jbugarin 12/09/06 --#######			
			System.out.println(Constantes.CODIGOSERVICIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOSERVICIO));
			System.out.println(Constantes.DESCRIPCIONSERVICIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONSERVICIO));
			*/
			//--## modificado JBUGARIN 19/09/06 DIRECCIONA DE ACUERDO AL CODIGO DE SERVICIO --##
			
			/**
			 * VALIDA SI LA SOLICITUD: CONSTITUCION DE EMPRESA
			 **/
			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_SOLINSCR) {
								
				solicitudInscripcion = loadXMLtoBeanConstitucionEmpresa(solicitudInscripcion, datosXML, doc);		
			
			}
			/**
			 * VALIDA SI LA SOLICITUD: RESERVA NOMBRE
			 **/			
			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_RESERVANOMBRE) { 
								
				solicitudInscripcion = loadXMLtoBeanReservaNombre(solicitudInscripcion, datosXML, doc);		
			
			}
			
			/**
			 * VALIDA SI LA SOLICITUD: BLOQUEO INMUEBLE
			 **/
			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_BLOQUEOINMUEBLE) { 
								
				solicitudInscripcion = loadXMLtoBeanBloqueoInmueble(solicitudInscripcion, datosXML, doc);		
			
			}
			
			/**
			 * VALIDA SI LA SOLICITUD: TRANSFERENCIA VEHICULAR
			 **/			
			if (Integer.parseInt(solicitudInscripcion.getCodigoServicio()) == Constantes.COD_SERVICIO_TRANSFVEHICULAR) { 
								
				solicitudInscripcion = loadXMLtoBeanTransferenciaVehicular(solicitudInscripcion, datosXML, doc);		
			
			}
			/** modificado JBUGARIN 19/09/06 **/
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return solicitudInscripcion;
	
	} 
	
			
	
	/**
	 * @param solicitudInscripcion
	 * @param datosXML
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public SolicitudInscripcion loadXMLtoBeanConstitucionEmpresa(SolicitudInscripcion solicitudInscripcion,
	 														     String datosXML,
	 														     Document doc) throws Exception{	
	
		Presentante presentante = null;
		NodeList nodeList = null;
		int size = 0;
		PersonaJuridica personaJuridica = null;
		Capital capital = null;
		ReservaMercantil reservaMercantil = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		PersonaNatural personaNatural = null;
		ArrayList listaInstrumentos = null;	
		InstrumentoPublico instrumentoPublico = null;
		DatosPago datosPago = new DatosPago();	

		try {

			System.out.println("PRESENTANTE");
			presentante = new Presentante();	
			//presentante.setCodigoUsuario(XMLManager.getXMLValue(datosXML, Constantes.CODIGOUSUARIO));
			presentante.setCodigoPresentante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			presentante.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			presentante.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			presentante.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			presentante.setCodigoInstitucion(XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			presentante.setDescripcionInstitucion(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			presentante.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));
			presentante.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));
			presentante.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));
			/********modificado jbugarin 12/09/06*******/
			String ubigeoPresentante = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE);
			if(!(ubigeoPresentante.equals(null) || ubigeoPresentante.equals(""))){
			presentante.setCodigoDepartamento(ubigeoPresentante.substring(2,4));
			presentante.setCodigoProvincia(ubigeoPresentante.substring(4,6));
     		presentante.setCodigoDistrito(ubigeoPresentante.substring(6,8));
			}
			
			presentante.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			presentante.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));
			presentante.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			presentante.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));
			presentante.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			presentante.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));
			presentante.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));
			//correo agregado						//correo agregado
			presentante.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));
			/********modificado jbugarin 12/09/06*******/
			
			solicitudInscripcion.setPresentante(presentante);
			
			//System.out.println(Constantes.CODIGOUSUARIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOUSUARIO));
			/*
			System.out.println(Constantes.CODIGOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			System.out.println(Constantes.APELLIDOPATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			System.out.println(Constantes.APELLIDOMATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			System.out.println(Constantes.NOMBREPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			System.out.println(Constantes.CODIGOINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			System.out.println(Constantes.DESCRIPCIONINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			System.out.println(Constantes.CODIGOTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));										
			System.out.println(Constantes.NUMERODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));										
			System.out.println(Constantes.UBIGEOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			System.out.println(Constantes.DESCRIPCIONPROVINCIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDISTRITOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			System.out.println(Constantes.DESCRIPCIONTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			System.out.println(Constantes.DIRECCIONPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));						
			System.out.println(Constantes.CODIGOPOSTALPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));						
			//AGREGADO CODIGOTIPOVIA Y CORREOELECTR
			System.out.println(Constantes.CODIGOTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));						
			System.out.println(Constantes.CORREOELECTRONICOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));						
			*/
			
			System.out.println("PERSONA JURIDICA");
			personaJuridica = new PersonaJuridica();
			
			personaJuridica.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPERSONAJURIDICA));
			personaJuridica.setSiglas(XMLManager.getXMLValue(datosXML, Constantes.SIGLASPERSONAJURIDICA));
			
			//personaJuridica.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPERSONAJURIDICA));
			personaJuridica.setCodigoTipoSociedad(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDAD));
			personaJuridica.setDescripcionTipoSociedad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDAD));
			personaJuridica.setCodigoTipoSociedadAnonima(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDADANONIMA));
			personaJuridica.setDescripcionTipoSociedadAnonima(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDADANONIMA));
			/***JBUGARIN 12/09/06 ***/
			personaJuridica.setIndicadorRUC(XMLManager.getXMLValue(datosXML, Constantes.INDICADORRUC));
			/***JBUGARIN 12/09/06 ***/
			solicitudInscripcion.setPersonaJuridica(personaJuridica);
			
			/*			
			System.out.println(Constantes.RAZONSOCIALPERSONAJURIDICA+"::"+XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPERSONAJURIDICA));
			System.out.println(Constantes.SIGLASPERSONAJURIDICA+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPERSONAJURIDICA));
			System.out.println(Constantes.CODIGOTIPOSOCIEDAD+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDAD));
			System.out.println(Constantes.DESCRIPCIONTIPOSOCIEDAD+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDAD));
			System.out.println(Constantes.CODIGOTIPOSOCIEDADANONIMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDADANONIMA));
			System.out.println(Constantes.DESCRIPCIONTIPOSOCIEDADANONIMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDADANONIMA));
			*/
			/** JBUGARIN 12/09/06 **/
			//System.out.println(Constantes.INDICADORRUC+"::"+XMLManager.getXMLValue(datosXML, Constantes.INDICADORRUC));
			/** JBUGARIN 12/09/06 **/
			
			System.out.println("CAPITAL");
			capital = new Capital();
			capital.setCodigoMoneda(XMLManager.getXMLValue(datosXML, Constantes.CODIGOMONEDA));
			capital.setDescripcionMoneda(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONMONEDA));
			capital.setMontoCapital(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.MONTOCAPITAL)));
			capital.setValor(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.VALOR)));
			capital.setCodigoCancelacionCapital(XMLManager.getXMLValue(datosXML, Constantes.CODIGOCANCELACIONCAPITAL));
			capital.setDescripcionCancelacionCapital(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONCANCELACIONCAPITAL));
			capital.setPorcentajeCancelado(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.PORCENTAJECANCELADO)));
			solicitudInscripcion.setCapital(capital);	
			/*
			System.out.println(Constantes.CODIGOMONEDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOMONEDA));
			System.out.println(Constantes.DESCRIPCIONMONEDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONMONEDA));
			System.out.println(Constantes.MONTOCAPITAL+"::"+XMLManager.getXMLValue(datosXML, Constantes.MONTOCAPITAL));
			System.out.println(Constantes.VALOR+"::"+XMLManager.getXMLValue(datosXML, Constantes.VALOR));
			System.out.println(Constantes.CODIGOCANCELACIONCAPITAL+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOCANCELACIONCAPITAL));
			System.out.println(Constantes.DESCRIPCIONCANCELACIONCAPITAL+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONCANCELACIONCAPITAL));
			System.out.println(Constantes.PORCENTAJECANCELADO+"::"+XMLManager.getXMLValue(datosXML, Constantes.PORCENTAJECANCELADO));
			*/

	
			System.out.println("RESERVA MERCANTIL");
			reservaMercantil = new ReservaMercantil();	
			reservaMercantil.setAnhoTitulo(XMLManager.getXMLValue(datosXML, Constantes.ANHOTITULO));
			reservaMercantil.setNumeroTitulo(XMLManager.getXMLValue(datosXML, Constantes.NUMEROTITULO));
			solicitudInscripcion.setReservaMercantil(reservaMercantil);
			//System.out.println(Constantes.ANHOTITULO+"::"+XMLManager.getXMLValue(datosXML, Constantes.ANHOTITULO));
			//System.out.println(Constantes.NUMEROTITULO+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMEROTITULO));
			
			nodeList = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONANATURAL);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("PARTICIPANTES(PERSONAS NATURALES)--->"+size);
				listaParticipantesPN = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("PARTICIPANTE::"+i);
					personaNatural = new PersonaNatural();
					personaNatural.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,i));
					personaNatural.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,i));
					personaNatural.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,i));
					personaNatural.setCodigoEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,i));
					personaNatural.setDescripcionEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,i));
					personaNatural.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,i));
					personaNatural.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,i));
					personaNatural.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,i));
					/***** JUBUGARIN 12/09/06 ***/
					String ubigeoPN = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN);
					if(!(ubigeoPN.equals(null) || ubigeoPN.equals("")) ){
					personaNatural.setCodigoDepartamento(ubigeoPN.substring(2,4));
					personaNatural.setCodigoProvincia(ubigeoPN.substring(4,6));
					personaNatural.setCodigoDistrito(ubigeoPN.substring(6,8));
					}
					personaNatural.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,i));
					personaNatural.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,i));
					personaNatural.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,i));
					personaNatural.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,i));
					personaNatural.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,i));
					personaNatural.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,i));
					personaNatural.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,i));
					personaNatural.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,i));
					/***** JUBUGARIN 12/09/06 ***/
					personaNatural.setFechaNacimiento(XMLManager.getXMLValue(datosXML, Constantes.FECHANACIMIENTO,i));
					personaNatural.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,i));
					personaNatural.setCodigoTipoParticipantePNSUNAT(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPNSUNAT,i));
					personaNatural.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,i));
					personaNatural.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,i));
					personaNatural.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,i));
					personaNatural.setCodigoCargoOcupacion(XMLManager.getXMLValue(datosXML, Constantes.CODIGOCARGOOCUPACION,i));
					personaNatural.setDescripcionCargoOcupacion(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONCARGOOCUPACION,i));
					personaNatural.setFechaCargo(XMLManager.getXMLValue(datosXML, Constantes.FECHACARGO,i));
					//SAUL 
					personaNatural.setIndicadorRepresentante(XMLManager.getXMLValue(datosXML, Constantes.INDICADORREPRESENTANTEPN,i));
					personaNatural.setNombreConyuge(XMLManager.getXMLValue(datosXML, Constantes.NOMBRECONYUGEPN,i));
					personaNatural.setValorParticipacion(XMLManager.getXMLValue(datosXML, Constantes.VALORPARTICIPACIONPN,i));
					personaNatural.setPorcentajeParticipacion(XMLManager.getXMLValue(datosXML, Constantes.PORCENTAJEPARTICIPACIONPN,i));
					personaNatural.setNumeroPartidaEmpresa(XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDAEMPRESAPN,i));
					personaNatural.setDescripcionProfesional(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROFESIONPN,i));
					
					
					listaParticipantesPN.add(personaNatural);
					/*
					System.out.println(Constantes.CODIGOTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,i));						
					System.out.println(Constantes.DESCRIPCIONTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,i));						
					System.out.println(Constantes.NUMERODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,i));						
					System.out.println(Constantes.CODIGOESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,i));						
					System.out.println(Constantes.DESCRIPCIONESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,i));						
					System.out.println(Constantes.APELLIDOPATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,i));						
					System.out.println(Constantes.APELLIDOMATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,i));						
					System.out.println(Constantes.NOMBREPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,i));						
					System.out.println(Constantes.FECHANACIMIENTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHANACIMIENTO,i));						
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,i));						
					System.out.println(Constantes.CODIGONACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,i));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,i));						
					System.out.println(Constantes.CODIGOCARGOOCUPACION+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOCARGOOCUPACION,i));						
					System.out.println(Constantes.DESCRIPCIONCARGOOCUPACION+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONCARGOOCUPACION,i));						
					System.out.println(Constantes.FECHACARGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHACARGO,i));
					System.out.println(Constantes.UBIGEOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN,i));
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,i));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,i));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,i));	
					System.out.println(Constantes.CODIGOTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,i));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,i));
					System.out.println(Constantes.DIRECCIONPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,i));
					System.out.println(Constantes.CODIGOPOSTALPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,i));
					System.out.println(Constantes.CORREOELECTRONICOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,i));
					*/
				
				}
				solicitudInscripcion.setParticipantesPersonaNatural(listaParticipantesPN);	
			}
			
	
			nodeList = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONAJURIDICA);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("PARTICIPANTES(PERSONAS JURIDICAS)--->"+size);
				listaParticipantesPJ = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("PARTICIPANTE::"+i);
					personaJuridica = new PersonaJuridica();
					personaJuridica.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,i));
					personaJuridica.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,i));
					personaJuridica.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,i));
					personaJuridica.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,i));
					personaJuridica.setCodigoTipoParticipantePJSUNAT(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJSUNAT,i));					
					personaJuridica.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,i));
					personaJuridica.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,i));
					personaJuridica.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,i));
					personaJuridica.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,i));
					personaJuridica.setSiglas(XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));
					/***** JUBUGARIN 12/09/06 ***/
					personaJuridica.setCodigoZonaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPJ,i));
					personaJuridica.setCodigoOficinaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPJ,i));
					personaJuridica.setNumeroPartida(XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDAPJ,i));
					String ubigeoPJ = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ);
					if(!(ubigeoPJ.equals(null) ||ubigeoPJ.equals("")) ){
					personaJuridica.setCodigoDepartamento(ubigeoPJ.substring(2,4));
					personaJuridica.setCodigoProvincia(ubigeoPJ.substring(4,6));
					personaJuridica.setCodigoDistrito(ubigeoPJ.substring(6,8));
					}
					personaJuridica.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,i));
					personaJuridica.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,i));
					personaJuridica.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,i));
					personaJuridica.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,i));
					personaJuridica.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,i));
					personaJuridica.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,i));
					personaJuridica.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,i));
					/***** JUBUGARIN 12/09/06 ***/
					//SAUL
					personaJuridica.setIndicadorRepresentante(XMLManager.getXMLValue(datosXML, Constantes.INDICADORREPRESENTANTEPJ,i));
					
					listaParticipantesPJ.add(personaJuridica);
					
					/*System.out.println(Constantes.CODIGOTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,i));						
					System.out.println(Constantes.DESCRIPCIONTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,i));						
					System.out.println(Constantes.NUMERODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,i));						
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,i));						
					System.out.println(Constantes.CODIGONACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,i));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,i));						
					System.out.println(Constantes.RAZONSOCIALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,i));						
					System.out.println(Constantes.SIGLASPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.UBIGEOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ,i));						
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,i));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,i));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,i));	
					System.out.println(Constantes.CODIGOTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,i));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,i));
					System.out.println(Constantes.DIRECCIONPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,i));
					System.out.println(Constantes.CODIGOPOSTALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,i));
					*/					
				}
				solicitudInscripcion.setParticipantesPersonaJuridica(listaParticipantesPJ);	
			}
		    
			nodeList = doc.getElementsByTagName(Constantes.INSTRUMENTOPUBLICO);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("INSTRUMENTOS PUBLICOS--->"+size);
				listaInstrumentos = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("INSTRUMENTO PUBLICO::"+i);
					instrumentoPublico = new InstrumentoPublico();
					instrumentoPublico.setSecuencia(new Long(XMLManager.getXMLValue(datosXML, Constantes.SECUENCIA,i)));
					instrumentoPublico.setCodigoTipoInstrumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOINSTRUMENTO,i));
					instrumentoPublico.setDescripcionTipoInstrumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOINSTRUMENTO,i));
					instrumentoPublico.setLugar(XMLManager.getXMLValue(datosXML, Constantes.LUGAR,i));
					instrumentoPublico.setFecha(XMLManager.getXMLValue(datosXML, Constantes.FECHA,i));
					instrumentoPublico.setOtros(XMLManager.getXMLValue(datosXML, Constantes.OTROS,i));
					
					listaInstrumentos.add(instrumentoPublico);
					//System.out.println(Constantes.SECUENCIA+"::"+XMLManager.getXMLValue(datosXML, Constantes.SECUENCIA,i));						
					//System.out.println(Constantes.CODIGOTIPOINSTRUMENTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOINSTRUMENTO,i));						
					//System.out.println(Constantes.DESCRIPCIONTIPOINSTRUMENTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOINSTRUMENTO,i));						
					//System.out.println(Constantes.LUGAR+"::"+XMLManager.getXMLValue(datosXML, Constantes.LUGAR,i));						
					//System.out.println(Constantes.FECHA+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHA,i));
					//System.out.println(Constantes.OTROS+"::"+XMLManager.getXMLValue(datosXML, Constantes.OTROS,i));								
				}
				solicitudInscripcion.setInstrumentoPublico(listaInstrumentos);
			}	
			
			System.out.println("DATOS PAGO");	
			datosPago = new DatosPago();
			datosPago.setCostoTotalServicio(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO)));
			datosPago.setCodigoFormaPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			datosPago.setDescripcionFormaPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			datosPago.setNumeroOperacion(XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			datosPago.setFechaPago(XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			datosPago.setHoraPago(XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			datosPago.setCodigoTipoPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			datosPago.setDescripcionTipoPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));
			
			solicitudInscripcion.setDatosPago(datosPago);
			/*
			System.out.println(Constantes.COSTOTOTALSERVICIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO));
			System.out.println(Constantes.CODIGOFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			System.out.println(Constantes.DESCRIPCIONFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			System.out.println(Constantes.NUMEROOPERACION+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			System.out.println(Constantes.FECHAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			System.out.println(Constantes.HORAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			System.out.println(Constantes.CODIGOTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			System.out.println(Constantes.DESCRIPCIONTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));
			*/

		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return solicitudInscripcion;

	
	} 
	
	
	
	/** JBUGARIN LOADXML2  RESERVA DE NOMBRE inicio 18/09/06**/
	
	public SolicitudInscripcion loadXMLtoBeanReservaNombre(SolicitudInscripcion solicitudInscripcion,
	 														     String datosXML,
	 														     Document doc) throws Exception{
		
		ParserXML objeto = new ParserXML();
		NodeList nodeList = null;
		int size = 0;

		Presentante presentante = null;
		PersonaJuridica personaJuridica = null;
		PersonaNatural personaNatural = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		Partida partida = null;
		//ArrayList partidas= = null;
		
		DatosPago datosPago = new DatosPago();	
		
		try {
	
			System.out.println("PRESENTANTE");
			presentante = new Presentante();	
			presentante.setCodigoPresentante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			presentante.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			presentante.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			presentante.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			presentante.setCodigoInstitucion(XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			presentante.setDescripcionInstitucion(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			presentante.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));
			presentante.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));
			presentante.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));
			String ubigeoPresentante = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE);
			presentante.setCodigoDepartamento(ubigeoPresentante.substring(2,4));
			presentante.setCodigoProvincia(ubigeoPresentante.substring(4,6));
     		presentante.setCodigoDistrito(ubigeoPresentante.substring(6,8));
			presentante.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			presentante.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));
			presentante.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			presentante.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));
			presentante.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			presentante.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));
			presentante.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));
			presentante.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));
			
			solicitudInscripcion.setPresentante(presentante);
			
			System.out.println(Constantes.CODIGOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			System.out.println(Constantes.APELLIDOPATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			System.out.println(Constantes.APELLIDOMATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			System.out.println(Constantes.NOMBREPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			System.out.println(Constantes.CODIGOINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			System.out.println(Constantes.DESCRIPCIONINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			System.out.println(Constantes.CODIGOTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));										
			System.out.println(Constantes.NUMERODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));										
			System.out.println(Constantes.UBIGEOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			System.out.println(Constantes.DESCRIPCIONPROVINCIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDISTRITOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			System.out.println(Constantes.DESCRIPCIONTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			System.out.println(Constantes.DIRECCIONPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));						
			System.out.println(Constantes.CODIGOPOSTALPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));						
			System.out.println(Constantes.CODIGOTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));						
			System.out.println(Constantes.CORREOELECTRONICOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));						
			
			System.out.println("PERSONA JURIDICA");
			personaJuridica = new PersonaJuridica();
			personaJuridica.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPERSONAJURIDICA));
			personaJuridica.setSiglas(XMLManager.getXMLValue(datosXML, Constantes.SIGLASPERSONAJURIDICA));
			personaJuridica.setCodigoTipoSociedad(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDAD));
			personaJuridica.setDescripcionTipoSociedad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDAD));
			personaJuridica.setCodigoTipoSociedadAnonima(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDADANONIMA));
			personaJuridica.setDescripcionTipoSociedadAnonima(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDADANONIMA));
			/*** gochoa ***/
			personaJuridica.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIA));
			personaJuridica.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIA));
			/**************/
			personaJuridica.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCION));
					
			solicitudInscripcion.setPersonaJuridica(personaJuridica);
			
			System.out.println(Constantes.RAZONSOCIALPERSONAJURIDICA+"::"+XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPERSONAJURIDICA));
			System.out.println(Constantes.SIGLASPERSONAJURIDICA+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPERSONAJURIDICA));
			System.out.println(Constantes.CODIGOTIPOSOCIEDAD+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDAD));
			System.out.println(Constantes.DESCRIPCIONTIPOSOCIEDAD+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDAD));
			System.out.println(Constantes.CODIGOTIPOSOCIEDADANONIMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOSOCIEDADANONIMA));
			System.out.println(Constantes.DESCRIPCIONTIPOSOCIEDADANONIMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOSOCIEDADANONIMA));
		    System.out.println(Constantes.DIRECCION+"::"+XMLManager.getXMLValue(datosXML,Constantes.DIRECCION));
			
			
			nodeList = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONANATURAL);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("PARTICIPANTES(PERSONAS NATURALES)--->"+size);
				listaParticipantesPN = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("PARTICIPANTE::"+i);
					personaNatural = new PersonaNatural();
					personaNatural.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,i));
					personaNatural.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,i));
					personaNatural.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,i));
					personaNatural.setCodigoEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,i));
					personaNatural.setDescripcionEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,i));
					personaNatural.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,i));
					personaNatural.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,i));
					personaNatural.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,i));
					String ubigeoPN = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN);
					personaNatural.setCodigoDepartamento(ubigeoPN.substring(2,4));
					personaNatural.setCodigoProvincia(ubigeoPN.substring(4,6));
					personaNatural.setCodigoDistrito(ubigeoPN.substring(6,8));
					personaNatural.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,i));
					personaNatural.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,i));
					personaNatural.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,i));
					personaNatural.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,i));
					personaNatural.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,i));
					personaNatural.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,i));
					personaNatural.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,i));
					personaNatural.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,i));
					personaNatural.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,i));
					personaNatural.setCodigoTipoParticipantePNSUNAT(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPNSUNAT,i));					
					personaNatural.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,i));
					personaNatural.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,i));
					personaNatural.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,i));
					
					listaParticipantesPN.add(personaNatural);
					
					System.out.println(Constantes.CODIGOTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,i));						
					System.out.println(Constantes.DESCRIPCIONTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,i));						
					System.out.println(Constantes.NUMERODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,i));						
					System.out.println(Constantes.CODIGOESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,i));						
					System.out.println(Constantes.DESCRIPCIONESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,i));						
					System.out.println(Constantes.APELLIDOPATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,i));						
					System.out.println(Constantes.APELLIDOMATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,i));						
					System.out.println(Constantes.NOMBREPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,i));						
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,i));						
					System.out.println(Constantes.CODIGONACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,i));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,i));						
					System.out.println(Constantes.UBIGEOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN,i));
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,i));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,i));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,i));	
					System.out.println(Constantes.CODIGOTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,i));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,i));
					System.out.println(Constantes.DIRECCIONPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,i));
					System.out.println(Constantes.CODIGOPOSTALPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,i));
					System.out.println(Constantes.CORREOELECTRONICOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,i));
				
				}
				solicitudInscripcion.setParticipantesPersonaNatural(listaParticipantesPN);	
			}
			
	
			nodeList = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONAJURIDICA);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("PARTICIPANTES(PERSONAS JURIDICAS)--->"+size);
				listaParticipantesPJ = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("PARTICIPANTE::"+i);
					personaJuridica = new PersonaJuridica();
					personaJuridica.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,i));
					personaJuridica.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,i));
					personaJuridica.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,i));
					personaJuridica.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,i));
					personaJuridica.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,i));
					personaJuridica.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,i));
					personaJuridica.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,i));
					personaJuridica.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,i));
					personaJuridica.setSiglas(XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));
					personaJuridica.setCodigoZonaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPJ,i));
					personaJuridica.setCodigoOficinaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPJ,i));
					personaJuridica.setNumeroPartida(XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDAPJ,i));
					String ubigeoPJ = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ);
					if(ubigeoPJ!=null){
					personaJuridica.setCodigoDepartamento(ubigeoPJ.substring(2,4));
					personaJuridica.setCodigoProvincia(ubigeoPJ.substring(4,6));
					personaJuridica.setCodigoDistrito(ubigeoPJ.substring(6,8));
					}
					personaJuridica.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,i));
					personaJuridica.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,i));
					personaJuridica.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,i));
					personaJuridica.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,i));
					personaJuridica.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,i));
					personaJuridica.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,i));
					personaJuridica.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,i));
					
					listaParticipantesPJ.add(personaJuridica);

					System.out.println(Constantes.CODIGOTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,i));						
					System.out.println(Constantes.DESCRIPCIONTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,i));						
					System.out.println(Constantes.NUMERODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,i));						
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,i));						
					System.out.println(Constantes.CODIGONACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,i));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,i));						
					System.out.println(Constantes.RAZONSOCIALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,i));						
					System.out.println(Constantes.SIGLASPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.CODIGOZONAREGISTRALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.CODIGOOFICINAREGISTRALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.NUMEROPARTIDAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.UBIGEOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ,i));						
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,i));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,i));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,i));	
					System.out.println(Constantes.CODIGOTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,i));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,i));
					System.out.println(Constantes.DIRECCIONPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,i));
					System.out.println(Constantes.CODIGOPOSTALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,i));
					
				}

				solicitudInscripcion.setParticipantesPersonaJuridica(listaParticipantesPJ);	
			}
		    
			
			System.out.println("DATOS PAGO");	
			datosPago = new DatosPago();
			datosPago.setCostoTotalServicio(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO)));
			datosPago.setCodigoFormaPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			datosPago.setDescripcionFormaPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			datosPago.setNumeroOperacion(XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			datosPago.setFechaPago(XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			datosPago.setHoraPago(XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			datosPago.setCodigoTipoPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			datosPago.setDescripcionTipoPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));
			
			solicitudInscripcion.setDatosPago(datosPago);
			System.out.println(Constantes.COSTOTOTALSERVICIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO));
			System.out.println(Constantes.CODIGOFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			System.out.println(Constantes.DESCRIPCIONFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			System.out.println(Constantes.NUMEROOPERACION+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			System.out.println(Constantes.FECHAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			System.out.println(Constantes.HORAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			System.out.println(Constantes.CODIGOTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			System.out.println(Constantes.DESCRIPCIONTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));
		
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return solicitudInscripcion;
	
	} 
	
	/** JBUGARIN LOADXML2 RESERVA DE NOMBRE fin 18/09/06**/
	
	
	/** JBUGARIN LOADXML2 BLOQUEO DE INMUEBLE inicio 21/09/06**/
	
	public SolicitudInscripcion loadXMLtoBeanBloqueoInmueble(SolicitudInscripcion solicitudInscripcion,
	 														     String datosXML,
	 														     Document doc) throws Exception{	
	
		Presentante presentante = null;
		NodeList nodeList = null;
		int size = 0;
		PersonaJuridica personaJuridica = null;
		Capital capital = null;
		ReservaMercantil reservaMercantil = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		PersonaNatural personaNatural = null;
		ArrayList listaInstrumentos = null;	
		InstrumentoPublico instrumentoPublico = null;
		DatosPago datosPago = new DatosPago();	
		Partida partida = new Partida();
		ArrayList listaPartidas = null;
		
				
		try {
			
			System.out.println("PRESENTANTE");
			presentante = new Presentante();	
			presentante.setCodigoPresentante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			presentante.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			presentante.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			presentante.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			presentante.setCodigoInstitucion(XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			presentante.setDescripcionInstitucion(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			presentante.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));
			presentante.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));
			presentante.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));
			String ubigeoPresentante = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE);
			presentante.setCodigoDepartamento(ubigeoPresentante.substring(2,4));
			presentante.setCodigoProvincia(ubigeoPresentante.substring(4,6));
     		presentante.setCodigoDistrito(ubigeoPresentante.substring(6,8));
			presentante.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			presentante.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));
			presentante.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			presentante.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));
			presentante.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			presentante.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));
			presentante.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));
			presentante.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));
			
			solicitudInscripcion.setPresentante(presentante);
			
			System.out.println(Constantes.CODIGOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			System.out.println(Constantes.APELLIDOPATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			System.out.println(Constantes.APELLIDOMATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			System.out.println(Constantes.NOMBREPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			System.out.println(Constantes.CODIGOINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			System.out.println(Constantes.DESCRIPCIONINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			System.out.println(Constantes.CODIGOTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));										
			System.out.println(Constantes.NUMERODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));										
			System.out.println(Constantes.UBIGEOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			System.out.println(Constantes.DESCRIPCIONPROVINCIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDISTRITOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			System.out.println(Constantes.CODIGOTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));						
			System.out.println(Constantes.DESCRIPCIONTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			System.out.println(Constantes.DIRECCIONPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));						
			System.out.println(Constantes.CODIGOPOSTALPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));						
			System.out.println(Constantes.CORREOELECTRONICOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));						
			
			nodeList = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONANATURAL);	
			
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("PARTICIPANTES(PERSONAS NATURALES)--->"+size);
				listaParticipantesPN = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("PARTICIPANTE::"+i);
					personaNatural = new PersonaNatural();
					personaNatural.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,i));
					personaNatural.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,i));
					personaNatural.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,i));
					personaNatural.setCodigoEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,i));
					personaNatural.setDescripcionEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,i));
					personaNatural.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,i));
					personaNatural.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,i));
					personaNatural.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,i));
					String ubigeoPN = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN);
					personaNatural.setCodigoDepartamento(ubigeoPN.substring(2,4));
					personaNatural.setCodigoProvincia(ubigeoPN.substring(4,6));
					personaNatural.setCodigoDistrito(ubigeoPN.substring(6,8));
					personaNatural.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,i));
					personaNatural.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,i));
					personaNatural.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,i));
					personaNatural.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,i));
					personaNatural.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,i));
					personaNatural.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,i));
					personaNatural.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,i));
					personaNatural.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,i));
					personaNatural.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,i));
					personaNatural.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,i));
					personaNatural.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,i));
					personaNatural.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,i));
										
					listaParticipantesPN.add(personaNatural);
					
					System.out.println(Constantes.CODIGOTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,i));						
					System.out.println(Constantes.DESCRIPCIONTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,i));						
					System.out.println(Constantes.NUMERODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,i));						
					System.out.println(Constantes.CODIGOESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,i));						
					System.out.println(Constantes.DESCRIPCIONESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,i));						
					System.out.println(Constantes.APELLIDOPATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,i));						
					System.out.println(Constantes.APELLIDOMATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,i));						
					System.out.println(Constantes.NOMBREPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,i));						
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,i));						
					System.out.println(Constantes.CODIGONACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,i));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,i));						
					System.out.println(Constantes.UBIGEOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN,i));
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,i));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,i));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,i));	
					System.out.println(Constantes.CODIGOTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,i));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,i));
					System.out.println(Constantes.DIRECCIONPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,i));
					System.out.println(Constantes.CODIGOPOSTALPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,i));
					System.out.println(Constantes.CORREOELECTRONICOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,i));
				
				
				}
				solicitudInscripcion.setParticipantesPersonaNatural(listaParticipantesPN);	
			}
			
	
			nodeList = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONAJURIDICA);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("PARTICIPANTES(PERSONAS JURIDICAS)--->"+size);
				listaParticipantesPJ = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("PARTICIPANTE::"+i);
					personaJuridica = new PersonaJuridica();
					personaJuridica.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,i));
					personaJuridica.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,i));
					personaJuridica.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,i));
					personaJuridica.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,i));
					personaJuridica.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,i));
					personaJuridica.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,i));
					personaJuridica.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,i));
					personaJuridica.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,i));
					personaJuridica.setSiglas(XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));
					personaJuridica.setCodigoZonaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPJ,i));
					personaJuridica.setCodigoOficinaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPJ,i));
					personaJuridica.setNumeroPartida(XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDAPJ,i));
					String ubigeoPJ = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ);
					personaJuridica.setCodigoDepartamento(ubigeoPJ.substring(2,4));
					personaJuridica.setCodigoProvincia(ubigeoPJ.substring(4,6));
					personaJuridica.setCodigoDistrito(ubigeoPJ.substring(6,8));
					personaJuridica.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,i));
					personaJuridica.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,i));
					personaJuridica.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,i));
					personaJuridica.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,i));
					personaJuridica.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,i));
					personaJuridica.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,i));
					personaJuridica.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,i));
					
					listaParticipantesPJ.add(personaJuridica);
					
					System.out.println(Constantes.CODIGOTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,i));						
					System.out.println(Constantes.DESCRIPCIONTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,i));						
					System.out.println(Constantes.NUMERODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,i));						
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,i));						
					System.out.println(Constantes.CODIGONACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,i));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,i));						
					System.out.println(Constantes.RAZONSOCIALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,i));						
					System.out.println(Constantes.SIGLASPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.CODIGOZONAREGISTRALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.CODIGOOFICINAREGISTRALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.NUMEROPARTIDAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,i));						
					System.out.println(Constantes.UBIGEOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ,i));						
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,i));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,i));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,i));	
					System.out.println(Constantes.CODIGOTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,i));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,i));
					System.out.println(Constantes.DIRECCIONPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,i));
					System.out.println(Constantes.CODIGOPOSTALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,i));
					
					
				}
				solicitudInscripcion.setParticipantesPersonaJuridica(listaParticipantesPJ);	
			}

			nodeList = doc.getElementsByTagName(Constantes.INSTRUMENTOPUBLICO);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("INSTRUMENTOS PUBLICOS--->"+size);
				listaInstrumentos = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("INSTRUMENTO PUBLICO::"+i);
					instrumentoPublico = new InstrumentoPublico();
					instrumentoPublico.setSecuencia(new Long(XMLManager.getXMLValue(datosXML, Constantes.SECUENCIA,i)));
					instrumentoPublico.setCodigoTipoInstrumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOINSTRUMENTO,i));
					instrumentoPublico.setDescripcionTipoInstrumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOINSTRUMENTO,i));
					instrumentoPublico.setLugar(XMLManager.getXMLValue(datosXML, Constantes.LUGAR,i));
					instrumentoPublico.setFecha(XMLManager.getXMLValue(datosXML, Constantes.FECHA,i));
					instrumentoPublico.setOtros(XMLManager.getXMLValue(datosXML, Constantes.OTROS,i));
					
					listaInstrumentos.add(instrumentoPublico);
					System.out.println(Constantes.SECUENCIA+"::"+XMLManager.getXMLValue(datosXML, Constantes.SECUENCIA,i));						
					System.out.println(Constantes.CODIGOTIPOINSTRUMENTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOINSTRUMENTO,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOINSTRUMENTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOINSTRUMENTO,i));						
					System.out.println(Constantes.LUGAR+"::"+XMLManager.getXMLValue(datosXML, Constantes.LUGAR,i));						
					System.out.println(Constantes.FECHA+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHA,i));
					System.out.println(Constantes.OTROS+"::"+XMLManager.getXMLValue(datosXML, Constantes.OTROS,i));								
				}
				solicitudInscripcion.setInstrumentoPublico(listaInstrumentos);
			}	

		    
			System.out.println("DATOS PAGO");	
			datosPago = new DatosPago();
			datosPago.setCostoTotalServicio(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO)));
			datosPago.setCodigoFormaPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			datosPago.setDescripcionFormaPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			datosPago.setNumeroOperacion(XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			datosPago.setFechaPago(XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			datosPago.setHoraPago(XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			datosPago.setCodigoTipoPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			datosPago.setDescripcionTipoPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));
			
			solicitudInscripcion.setDatosPago(datosPago);
			System.out.println(Constantes.COSTOTOTALSERVICIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO));
			System.out.println(Constantes.CODIGOFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			System.out.println(Constantes.DESCRIPCIONFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			System.out.println(Constantes.NUMEROOPERACION+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			System.out.println(Constantes.FECHAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			System.out.println(Constantes.HORAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			System.out.println(Constantes.CODIGOTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			System.out.println(Constantes.DESCRIPCIONTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));

			/****************** PARTIDA!!!!**********************/
							
			nodeList = doc.getElementsByTagName(Constantes.PARTIDA);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("PARTIDAS--->"+size);
				listaPartidas = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("PARTIDA::"+i);
					partida = new Partida();
					partida.setCodigoZonaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPARTIDA));
					partida.setCodigoOficinaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPARTIDA));
					partida.setNumeroPartida(XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDA));
					partida.setCodigoTipoSistema(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODESISTEMA));
					partida.setDescripcionTipoSistema(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODESISTEMA));
					
					listaPartidas.add(partida);
					System.out.println(Constantes.CODIGOZONAREGISTRALPARTIDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPARTIDA,i));						
					System.out.println(Constantes.CODIGOOFICINAREGISTRALPARTIDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPARTIDA,i));						
					System.out.println(Constantes.NUMEROPARTIDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDA,i));						
					System.out.println(Constantes.CODIGOTIPODESISTEMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODESISTEMA,i));						
					System.out.println(Constantes.DESCRIPCIONTIPODESISTEMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODESISTEMA,i));
							
				}
				solicitudInscripcion.setPartidas(listaPartidas);
			}	
			
			
			
			
			
			/**********************************************************/			
			
		
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return solicitudInscripcion;
	
	} 
	
	/** JBUGARIN LOADXML2 BLOQUEO DE INMUEBLE fin 21/09/06**/
	
	/** JBUGARIN LOADXML2 TRANSFERENCIA VEHICULAR INICIO 21/09/06**/
	
	
	public SolicitudInscripcion loadXMLtoBeanTransferenciaVehicular(SolicitudInscripcion solicitudInscripcion,
	 														     String datosXML,
	   								     Document doc) throws Exception{	
	
		Presentante presentante = null;
		NodeList nodeList = null;
		NodeList nodeList2 = null;
		NodeList nodeList3 = null;
		int size = 0;
		int size2 = 0;
		int size3 = 0;
		PersonaJuridica personaJuridica = null;
		Capital capital = null;
		ReservaMercantil reservaMercantil = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		PersonaNatural personaNatural = null;
		ArrayList listaInstrumentos = null;	
		InstrumentoPublico instrumentoPublico = null;
		Vehiculo vehiculo = null;
		ArrayList listaVehiculos = null;
		DatosPago datosPago = new DatosPago();	
		
		
		
				
		try {
							
			System.out.println("PRESENTANTE");
			presentante = new Presentante();	
			presentante.setCodigoPresentante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			presentante.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			presentante.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			presentante.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			presentante.setCodigoInstitucion(XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			presentante.setDescripcionInstitucion(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			presentante.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));
			presentante.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));
			presentante.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));
			String ubigeoPresentante = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE);
			presentante.setCodigoDepartamento(ubigeoPresentante.substring(2,4));
			presentante.setCodigoProvincia(ubigeoPresentante.substring(4,6));
     		presentante.setCodigoDistrito(ubigeoPresentante.substring(6,8));
			presentante.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			presentante.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));
			presentante.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			presentante.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));
			presentante.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			presentante.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));
			presentante.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));
			presentante.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));
			
			solicitudInscripcion.setPresentante(presentante);
			
			System.out.println(Constantes.CODIGOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPRESENTANTE));
			System.out.println(Constantes.APELLIDOPATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPRESENTANTE));
			System.out.println(Constantes.APELLIDOMATERNOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPRESENTANTE));
			System.out.println(Constantes.NOMBREPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPRESENTANTE));
			System.out.println(Constantes.CODIGOINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOINSTITUCION));
			System.out.println(Constantes.DESCRIPCIONINSTITUCION+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONINSTITUCION));
			System.out.println(Constantes.CODIGOTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONTIPODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPRESENTANTE));										
			System.out.println(Constantes.NUMERODOCPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPRESENTANTE));										
			System.out.println(Constantes.UBIGEOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPRESENTANTE));
			System.out.println(Constantes.DESCRIPCIONPROVINCIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPRESENTANTE));										
			System.out.println(Constantes.DESCRIPCIONDISTRITOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPRESENTANTE));
			System.out.println(Constantes.CODIGOTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPRESENTANTE));						
			System.out.println(Constantes.DESCRIPCIONTIPOVIAPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPRESENTANTE));
			System.out.println(Constantes.DIRECCIONPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPRESENTANTE));						
			System.out.println(Constantes.CODIGOPOSTALPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPRESENTANTE));						
			System.out.println(Constantes.CORREOELECTRONICOPRESENTANTE+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPRESENTANTE));						
			
			nodeList = doc.getElementsByTagName(Constantes.INSTRUMENTOPUBLICO);	
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("INSTRUMENTOS PUBLICOS--->"+size);
				listaInstrumentos = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("INSTRUMENTO PUBLICO::"+i);
					instrumentoPublico = new InstrumentoPublico();
					instrumentoPublico.setSecuencia(new Long(XMLManager.getXMLValue(datosXML, Constantes.SECUENCIA,i)));
					instrumentoPublico.setCodigoTipoInstrumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOINSTRUMENTO,i));
					instrumentoPublico.setDescripcionTipoInstrumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOINSTRUMENTO,i));
					instrumentoPublico.setLugar(XMLManager.getXMLValue(datosXML, Constantes.LUGAR,i));
					instrumentoPublico.setFecha(XMLManager.getXMLValue(datosXML, Constantes.FECHA,i));
					instrumentoPublico.setOtros(XMLManager.getXMLValue(datosXML, Constantes.OTROS,i));
					
					listaInstrumentos.add(instrumentoPublico);
					System.out.println(Constantes.SECUENCIA+"::"+XMLManager.getXMLValue(datosXML, Constantes.SECUENCIA,i));						
					System.out.println(Constantes.CODIGOTIPOINSTRUMENTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOINSTRUMENTO,i));						
					System.out.println(Constantes.DESCRIPCIONTIPOINSTRUMENTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOINSTRUMENTO,i));						
					System.out.println(Constantes.LUGAR+"::"+XMLManager.getXMLValue(datosXML, Constantes.LUGAR,i));						
					System.out.println(Constantes.FECHA+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHA,i));
					System.out.println(Constantes.OTROS+"::"+XMLManager.getXMLValue(datosXML, Constantes.OTROS,i));								
				}
				solicitudInscripcion.setInstrumentoPublico(listaInstrumentos);
			}	

		   
		    nodeList = doc.getElementsByTagName(Constantes.VEHICULO); 	
			
			
			
			if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("VEHICULOS--->"+size);
				listaVehiculos = new ArrayList();
				for (int i = 0; i < size; i++) {
					System.out.println("VEHICULO::"+i);
					vehiculo = new Vehiculo();
					vehiculo.setPlaca(XMLManager.getXMLValue(datosXML, Constantes.PLACA,i));
					vehiculo.setSerie(XMLManager.getXMLValue(datosXML, Constantes.SERIE,i));
					vehiculo.setMotor(XMLManager.getXMLValue(datosXML, Constantes.MOTOR,i));
					vehiculo.setCodigoZonaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPARTIDA,i));
					vehiculo.setCodigoOficinaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPARTIDA,i));
					vehiculo.setNumeroPartida(XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDA,i));
					vehiculo.setCodigoSubActo(XMLManager.getXMLValue(datosXML, Constantes.CODIGOSUBACTO,i));
					vehiculo.setDescripcionSubActo(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONSUBACTO,i));
					vehiculo.setCodigoFormaPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO,i));
					vehiculo.setDescripcionFormaPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO,i));
					vehiculo.setCodigoMoneda(XMLManager.getXMLValue(datosXML, Constantes.CODIGOMONEDA,i));
					vehiculo.setDescripcionMoneda(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONMONEDA,i));
					vehiculo.setMonto(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.MONTO,i)));
					vehiculo.setPagado(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.PAGADO,i)));
					vehiculo.setSaldo(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.SALDO,i)));
					vehiculo.setObservaciones(XMLManager.getXMLValue(datosXML, Constantes.OBSERVACIONES,i));
					vehiculo.setCodigoVendedor(XMLManager.getXMLValue(datosXML, Constantes.CODIGOVENDEDOR,i));
					vehiculo.setDescripcionVendedor(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONVENDEDOR,i));
					vehiculo.setCodigoComprador(XMLManager.getXMLValue(datosXML, Constantes.CODIGOCOMPRADOR,i));
					vehiculo.setDescripcionComprador(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONCOMPRADOR,i));
					vehiculo.setCodigoTipoSistema(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODESISTEMA,i));
					vehiculo.setDescripcionTipoSistema(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODESISTEMA,i));
					
					
					listaVehiculos.add(vehiculo);
					System.out.println(Constantes.PLACA+"::"+XMLManager.getXMLValue(datosXML, Constantes.PLACA,i));						
					System.out.println(Constantes.SERIE+"::"+XMLManager.getXMLValue(datosXML, Constantes.SERIE,i));						
					System.out.println(Constantes.MOTOR+"::"+XMLManager.getXMLValue(datosXML, Constantes.MOTOR,i));						
					System.out.println(Constantes.CODIGOZONAREGISTRALPARTIDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPARTIDA,i));						
					System.out.println(Constantes.CODIGOOFICINAREGISTRALPARTIDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPARTIDA,i));
					System.out.println(Constantes.NUMEROPARTIDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDA,i));
					System.out.println(Constantes.CODIGOSUBACTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOSUBACTO,i));								
					System.out.println(Constantes.DESCRIPCIONSUBACTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONSUBACTO,i));								
					System.out.println(Constantes.CODIGOFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO,i));								
					System.out.println(Constantes.DESCRIPCIONFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO,i));								
					System.out.println(Constantes.CODIGOMONEDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOMONEDA,i));								
					System.out.println(Constantes.DESCRIPCIONMONEDA+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONMONEDA,i));								
					System.out.println(Constantes.MONTO+"::"+XMLManager.getXMLValue(datosXML, Constantes.MONTO,i));								
					System.out.println(Constantes.PAGADO+"::"+XMLManager.getXMLValue(datosXML, Constantes.PAGADO,i));								
					System.out.println(Constantes.SALDO+"::"+XMLManager.getXMLValue(datosXML, Constantes.SALDO,i));					
					System.out.println(Constantes.OBSERVACIONES+"::"+XMLManager.getXMLValue(datosXML, Constantes.OBSERVACIONES,i));
					System.out.println(Constantes.CODIGOVENDEDOR+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOVENDEDOR,i));
					System.out.println(Constantes.DESCRIPCIONVENDEDOR+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONVENDEDOR,i));
					System.out.println(Constantes.CODIGOCOMPRADOR+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOCOMPRADOR,i));
					System.out.println(Constantes.DESCRIPCIONCOMPRADOR+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONCOMPRADOR,i));
					System.out.println(Constantes.CODIGOTIPODESISTEMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODESISTEMA,i));					
					System.out.println(Constantes.DESCRIPCIONTIPODESISTEMA+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODESISTEMA,i));					
					
				nodeList2 = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONANATURAL);	
				if (nodeList2 != null && nodeList2.getLength() > 0) {
				size2 = nodeList2.getLength();
				System.out.println("COMPRADORES(PERSONAS NATURALES)--->"+size2);
				listaParticipantesPN = new ArrayList();
				for (int j = 0; j< size2; j++) {
					System.out.println("COMPRADOR::"+j);
					personaNatural = new PersonaNatural();
					personaNatural.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,j));
					personaNatural.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,j));
					personaNatural.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,j));
					personaNatural.setCodigoEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,j));
					personaNatural.setDescripcionEstadoCivil(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,j));
					personaNatural.setApellidoPaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,j));
					personaNatural.setApellidoMaterno(XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,j));
					personaNatural.setNombre(XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,j));
					String ubigeoPN = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN);
					personaNatural.setCodigoDepartamento(ubigeoPN.substring(2,4));
					personaNatural.setCodigoProvincia(ubigeoPN.substring(4,6));
					personaNatural.setCodigoDistrito(ubigeoPN.substring(6,8));
					personaNatural.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,j));
					personaNatural.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,j));
					personaNatural.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,j));
					personaNatural.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,j));
					personaNatural.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,j));
					personaNatural.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,j));
					personaNatural.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,j));
					personaNatural.setCorreoElectronico(XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,j));
					personaNatural.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,j));
					personaNatural.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,j));
					personaNatural.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,j));
					personaNatural.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,j));
										
					listaParticipantesPN.add(personaNatural);
					
					System.out.println(Constantes.CODIGOTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPN,j));					
					System.out.println(Constantes.DESCRIPCIONTIPODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPN,j));					
					System.out.println(Constantes.NUMERODOCPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPN,j));					
					System.out.println(Constantes.CODIGOESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOESTADOCIVIL,j));					
					System.out.println(Constantes.DESCRIPCIONESTADOCIVIL+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONESTADOCIVIL,j));					
					System.out.println(Constantes.APELLIDOPATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOPATERNOPN,j));					
					System.out.println(Constantes.APELLIDOMATERNOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.APELLIDOMATERNOPN,j));				
					System.out.println(Constantes.NOMBREPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.NOMBREPN,j));					
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPN,j));					
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPN,j));						
					System.out.println(Constantes.CODIGONACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPN,j));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPN,j));						
					System.out.println(Constantes.UBIGEOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPN,j));
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPN,j));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPN,j));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPN,j));	
					System.out.println(Constantes.CODIGOTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPN,j));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPN,j));
					System.out.println(Constantes.DIRECCIONPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPN,j));
					System.out.println(Constantes.CODIGOPOSTALPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPN,j));
					System.out.println(Constantes.CORREOELECTRONICOPN+"::"+XMLManager.getXMLValue(datosXML, Constantes.CORREOELECTRONICOPN,j));
				
				
				}
				//
				vehiculo.setCompradoresPersonaNatural(listaParticipantesPN);	
			}
			
				nodeList3 = doc.getElementsByTagName(Constantes.PARTICIPANTEPERSONAJURIDICA);	
				if (nodeList3 != null && nodeList3.getLength() > 0) {
				size3 = nodeList3.getLength();
				System.out.println("COMPRADORES(PERSONAS JURIDICAS)--->"+size3);
				listaParticipantesPJ = new ArrayList();
				for (int k = 0; k < size3; k++) {
					System.out.println("COMPRADOR::"+k);
					personaJuridica = new PersonaJuridica();
					personaJuridica.setCodigoTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,k));
					personaJuridica.setDescripcionTipoDocumento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,k));
					personaJuridica.setNumeroDocumento(XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,k));
					personaJuridica.setCodigoTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,k));
					personaJuridica.setDescripcionTipoParticipante(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,k));
					personaJuridica.setCodigoNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,k));
					personaJuridica.setDescripcionNacionalidad(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,k));
					personaJuridica.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,k));
					personaJuridica.setSiglas(XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,k));
					personaJuridica.setCodigoZonaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOZONAREGISTRALPJ,k));
					personaJuridica.setCodigoOficinaRegistral(XMLManager.getXMLValue(datosXML, Constantes.CODIGOOFICINAREGISTRALPJ,k));
					personaJuridica.setNumeroPartida(XMLManager.getXMLValue(datosXML, Constantes.NUMEROPARTIDAPJ,k));
					String ubigeoPJ = XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ);
					personaJuridica.setCodigoDepartamento(ubigeoPJ.substring(2,4));
					personaJuridica.setCodigoProvincia(ubigeoPJ.substring(4,6));
					personaJuridica.setCodigoDistrito(ubigeoPJ.substring(6,8));
					personaJuridica.setDescripcionDepartamento(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,k));
					personaJuridica.setDescripcionProvincia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,k));
					personaJuridica.setDescripcionDistrito(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,k));
					personaJuridica.setCodigoTipoVia(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,k));
					personaJuridica.setDescripcionTipoVia(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,k));
					personaJuridica.setDireccion(XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,k));
					personaJuridica.setCodigoPostal(XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,k));
					
					listaParticipantesPJ.add(personaJuridica);
					
					System.out.println(Constantes.CODIGOTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPODOCPJ,k));						
					System.out.println(Constantes.DESCRIPCIONTIPODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPODOCPJ,k));						
					System.out.println(Constantes.NUMERODOCPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMERODOCPJ,k));						
					System.out.println(Constantes.CODIGOTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPARTICIPANTEPJ,k));						
					System.out.println(Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPARTICIPANTEPJ,k));						
					System.out.println(Constantes.CODIGONACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGONACIONALIDADPJ,k));						
					System.out.println(Constantes.DESCRIPCIONNACIONALIDADPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONNACIONALIDADPJ,k));						
					System.out.println(Constantes.RAZONSOCIALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIALPJ,k));						
					System.out.println(Constantes.SIGLASPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,k));						
					System.out.println(Constantes.CODIGOZONAREGISTRALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,k));						
					System.out.println(Constantes.CODIGOOFICINAREGISTRALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,k));						
					System.out.println(Constantes.NUMEROPARTIDAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.SIGLASPJ,k));						
					System.out.println(Constantes.UBIGEOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.UBIGEOPJ,k));						
					System.out.println(Constantes.DESCRIPCIONDEPARTAMENTOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDEPARTAMENTOPJ,k));												
					System.out.println(Constantes.DESCRIPCIONPROVINCIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONPROVINCIAPJ,k));												
					System.out.println(Constantes.DESCRIPCIONDISTRITOPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONDISTRITOPJ,k));	
					System.out.println(Constantes.CODIGOTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOVIAPJ,k));
					System.out.println(Constantes.DESCRIPCIONTIPOVIAPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOVIAPJ,k));
					System.out.println(Constantes.DIRECCIONPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.DIRECCIONPJ,k));
					System.out.println(Constantes.CODIGOPOSTALPJ+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOPOSTALPJ,k));
					
					
				}
				vehiculo.setCompradoresPersonaJuridica(listaParticipantesPJ);	
			}


						
				}
				solicitudInscripcion.setVehiculos(listaVehiculos);
			}	

		    		    		    
			System.out.println("DATOS PAGO");	
			datosPago = new DatosPago();
			datosPago.setCostoTotalServicio(new BigDecimal(XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO)));
			datosPago.setCodigoFormaPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			datosPago.setDescripcionFormaPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			datosPago.setNumeroOperacion(XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			datosPago.setFechaPago(XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			datosPago.setHoraPago(XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			datosPago.setCodigoTipoPago(XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			datosPago.setDescripcionTipoPago(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));
			
			solicitudInscripcion.setDatosPago(datosPago);
			System.out.println(Constantes.COSTOTOTALSERVICIO+"::"+XMLManager.getXMLValue(datosXML, Constantes.COSTOTOTALSERVICIO));
			System.out.println(Constantes.CODIGOFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOFORMAPAGO));
			System.out.println(Constantes.DESCRIPCIONFORMAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONFORMAPAGO));
			System.out.println(Constantes.NUMEROOPERACION+"::"+XMLManager.getXMLValue(datosXML, Constantes.NUMEROOPERACION));
			System.out.println(Constantes.FECHAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.FECHAPAGO));
			System.out.println(Constantes.HORAPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.HORAPAGO));
			System.out.println(Constantes.CODIGOTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.CODIGOTIPOPAGO));
			System.out.println(Constantes.DESCRIPCIONTIPOPAGO+"::"+XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONTIPOPAGO));

			
			}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return solicitudInscripcion;
	
	} 
	
	
	/** JBUGARIN LOADXML2 TRANSFERENCIA VEHICULAR FIN 21/09/06**/
	
	/** JBUGARIN LOADXML2 BUSQUEDA POR PERSONA JURIDICA INICIO 06/12/06**/
	
	public SolicitudBusqueda loadXMLtoBeanBusquedaJuridica(String datosXML
	   								                   ) throws Exception{	
	
		ParserXML objeto = new ParserXML();
		NodeList nodeList = null;
		int size = 0;
		ArrayList filtroBusquedaList = null;      
		FiltroBusqueda filtroBusqueda = null;
		SolicitudBusqueda solicitudBusqueda = null;
								
		try {
				Document doc = objeto.getDocument(datosXML);
				System.out.println("Busqueda por Persona Juridica");			
				solicitudBusqueda = new SolicitudBusqueda();
				
				solicitudBusqueda.setCodigoUsuario(XMLManager.getXMLValue(datosXML, Constantes.CODIGOUSUARIO));
				solicitudBusqueda.setCodigoServicio(XMLManager.getXMLValue(datosXML, Constantes.CODIGOSERVICIO));
				solicitudBusqueda.setDescripcionServicio(XMLManager.getXMLValue(datosXML, Constantes.DESCRIPCIONSERVICIO));

				nodeList = doc.getElementsByTagName(Constantes.VEHICULO); 	
				if (nodeList != null && nodeList.getLength() > 0) {
				size = nodeList.getLength();
				System.out.println("Busqueda::"+size);
				filtroBusquedaList = new ArrayList();
					for (int j = 0; j < size; j++) {
					System.out.println("Filtro"+j);
					filtroBusqueda.setRazonSocial(XMLManager.getXMLValue(datosXML, Constantes.RAZONSOCIAL,j))	;
					filtroBusqueda.setSiglas(XMLManager.getXMLValue(datosXML, Constantes.SIGLAS,j))	;
						
					}
				solicitudBusqueda.setFiltroBusqueda(filtroBusquedaList);
				}
			
		
			}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return solicitudBusqueda;
	
	} 
	
	
	/** JBUGARIN LOADXML2 BUSQUEDA POR PERSONA JURIDICA FIN 06/12/06**/
		
	private UsuarioBean getUserBean(String user) throws CustomException, DBException, Throwable {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		UsuarioBean usuario = null;
		
		try {

			conn = pool.getConnection();
			conn.setAutoCommit(false);			
			
			DBConnection dconn = new DBConnection(conn);

			System.out.println("getUserBean....");
			DboCuenta cuentaUserI = new DboCuenta(dconn);
			cuentaUserI.setField(DboCuenta.CAMPO_USR_ID, user);
			/*if (Propiedades.getInstance().getFlagProduccion() == false)
				cuentaUserI.setField(DboCuenta.CAMPO_CLAVE, pwd);*/

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
				} else
					throw new CustomException(Constantes.NO_SALDO_DE_LINEA_PREPAGO);
			} else {
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
			} else {
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
			
			//session.setAttribute("Usuario", usuario);
			//Fin Parte 4: Guardo Datos en Sesion					
		} catch (CustomException e) {
			e.printStackTrace();
			throw e;
		
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
			catch (Throwable t){}
		}

		return usuario;
	}
	
	
	private File generaArchivo(String rtfFile, String rutaFile) {
		
		File f = null;
	
		try {
			f = new File(rutaFile);
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

	public String generaHashMD5(String strByteAux) {
        String hashRpta = null;
        MessageDigest pruebas = null;
        //byte raw[] = null;
        /*
        try {
            pruebas = MessageDigest.getInstance("MD5");
            pruebas.update(strByteAux.getBytes()); // Generación de resumen de mensaje
            //pruebas.update(strByteAux.getBytes("UTF8"));//PRUEBAS DE INTELINUX
            raw = pruebas.digest(); // Obtención del resumen de mensaje
            //hashRpta = (new BASE64Encoder()).encode(raw); // Traducción a BASE64
            StringBuffer buffer  =  new StringBuffer();
            for(int i=0;i< raw.length; i++)
            {
            	String s = Integer.toHexString(raw[i]);
            	int tamano = s.length();
            	if(tamano>=2)
            	{
            		buffer.append(s.substring(tamano-2, tamano));
            	}else
            	{
            		buffer.append("0");
            		buffer.append(s);
            	}  	
            }
            hashRpta = buffer.toString();
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }*/
 
        MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new IllegalStateException(e.getMessage());
    }
		try
		{
			md.update(strByteAux.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			throw new IllegalStateException(e.getMessage());
		}
		byte raw[] = md.digest(); // Obtención del resumen de mensaje
		int size = raw.length; 

		StringBuffer h = new StringBuffer(size); 
		for (int i = 0; i < size; i++)
		{ 
			int u = raw[i] & 255; // unsigned conversion 

			if (u < 16)
			{
				h.append("0" + Integer.toHexString(u)); //$NON-NLS-1$ 
			}
			else
			{
				h.append(Integer.toHexString(u)); 
			} 
		}
		hashRpta = h.toString();
 		return hashRpta;       
    }

	// Añadido por CCS
	/*public PartidaXPersonaJuridicaBean buscaRazonSocial (BuscaPartidaXPJReqBean req) {
		return PersonaJuridicaLogic.buscaRazonSocial(req);
	}*/

	// Añadido por CCS
	/*public PartidaXPersonaNaturalBean buscaPersonaNatural (BuscaPartidaXPNReqBean req) {
		return PersonaNaturalLogic.buscaPersonaNatural(req);
	}*/

	// Añadido por CCS
	/*public PartidaXPropiedadInmuebleBean buscaPropiedadInmueble (BuscaPartidaXPIReqBean req) {
		return PropiedadInmuebleLogic.buscaPropiedadInmueble(req);
	}*/

	// Añadido por CCS
	/*public PartidaXPropiedadVehicularBean buscaPropiedadVehicular (BuscaPartidaXPVReqBean req) {
		return PropiedadVehicularLogic.buscaPropiedadVehicular(req);
	}*/
}

