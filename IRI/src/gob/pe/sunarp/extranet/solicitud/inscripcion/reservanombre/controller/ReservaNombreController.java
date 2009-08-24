package gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre.controller;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.logica.Constantes;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import java.util.ArrayList;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.*;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;
import javax.servlet.http.*;
import java.sql.*;
import gob.pe.sunarp.extranet.util.*;
import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.framework.session.*;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.PresentacionSolicitudInscripcion;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservaNombreController extends ControllerExtension {

	
	public ReservaNombreController() {
		super();
		addState(new State("obtenerDatosReservaParticipantes", "Datos de Reserva y Lista de Participantes"));
		addState(new State("obtenerNuevoParticipante", "Agregar Participante"));
		addState(new State("agregarParticipante", "Agregar Participante"));
		addState(new State("obtenerProvincia", "Obtiene Provincia"));
		addState(new State("obtenerDistrito", "Obtiene Distrito"));
		addState(new State("obtenerParticipante", "Modificar Participante"));
		addState(new State("borrarParticipante", "Borrar Participante"));
		addState(new State("obtenerResumenAntesPago", "Obtener Resumen Antes del Pago"));
		addState(new State("obtenerDatosPago", "Obtener Datos del Pago"));
		addState(new State("regresarADatosReserva", "Regresar a Datos de Reserva"));
		addState(new State("procesarSolicitud", "Procesar Solicitud"));
		addState(new State("obtenerImpresion2", "Procesar Solicitud"));
		//setInitialState("obtenerDatosReservaParticipantes");
	}



   /*
    * Devuelve la fecha actual en el formato por ejm:
    * Lima, martes 23 de mayo de 2006 12:51:40 PM
    * 
    */
    public static String getFechaActual(){
  	  	     	  	
	    StringBuffer sbFecha=new StringBuffer("Lima, ");	
	    java.util.Date d = new java.util.Date();
        
	    java.util.Calendar cal = java.util.Calendar.getInstance(java.util.Locale.US);	
	    //sbFecha.append(getNombreDia(cal.get(java.util.Calendar.DAY_OF_WEEK))).append(" ");
	    	           
	    SimpleDateFormat dia= new SimpleDateFormat("dd");
	    SimpleDateFormat mes= new SimpleDateFormat("MMMM");
	    SimpleDateFormat anio= new SimpleDateFormat("yyyy");
	    //SimpleDateFormat time= new SimpleDateFormat("hh:mm:ss a");
	    //SimpleDateFormat time= new SimpleDateFormat("hh:mm a");
	
	    //sbFecha.append(dia.format(d)).append(" de ").append(mes.format(d)).append(" del ").append(anio.format(d)).append(" ").append(time.format(d));                
	    sbFecha.append(dia.format(d)).append(" de ").append(mes.format(d).substring(0,1).toUpperCase() + mes.format(d).substring(1, mes.format(d).length())).append(" del ").append(anio.format(d));                
	    return sbFecha.toString(); 
    }

	protected ControllerResponse runObtenerImpresion2State(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
				
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
		
			req.setAttribute("fechaActual", getFechaActual());
				
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("resumenSolicitudImpresion2");
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}

	protected ControllerResponse runProcesarSolicitudState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		
		SolicitudInscripcion solicitudInscripcion = null;
		PresentacionSolicitudInscripcion presentacionSolicitudInscripcion = null;
		DatosPago datosPago = null;
		int respuesta = 0;
		
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
		
		    UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			solicitudInscripcion = (SolicitudInscripcion) session.getAttribute("solicitudInscripcion");
			
			System.out.println("numeroHoja::"+solicitudInscripcion.getNumeroHoja());
			
			if ( (solicitudInscripcion.getNumeroHoja()==null) || (solicitudInscripcion.getNumeroHoja().equals("")) ) {
				/************************************ DATOS PAGO ***********************************************/
				datosPago = new DatosPago();
				/** codigoFormaPago **/
				datosPago.setCodigoFormaPago("03");
				/** descripcionFormaPago **/
				datosPago.setDescripcionFormaPago("A CUENTA DE SALDO");
				/** costoTotalServicio **/
				datosPago.setCostoTotalServicio(new java.math.BigDecimal(request.getParameter("txtDisServ")));
				/** codigoTipoPago **/
				datosPago.setCodigoTipoPago("01");
				/** descripcionTipoPago **/
				datosPago.setDescripcionTipoPago("PRESENTACION");
				/** numeroOperacion **/
				datosPago.setNumeroOperacion(null);
				/** fechaPago **/
				datosPago.setFechaPago(FechaUtil.getCurrentDateYYYYMMDD());
				/** horaPago **/
				datosPago.setHoraPago(FechaUtil.getCurrentDateTime().substring(11,13)+
									  FechaUtil.getCurrentDateTime().substring(14,16)+
									  FechaUtil.getCurrentDateTime().substring(17,19));
	
				solicitudInscripcion.setDatosPago(datosPago);
				System.out.println("DATOS PAGO");
				System.out.println("codigoFormaPago::"+datosPago.getCodigoFormaPago()+"::");
				System.out.println("descripcionFormaPago::"+datosPago.getDescripcionFormaPago()+"::");
				System.out.println("costoTotalServicio::"+datosPago.getCostoTotalServicio()+"::");
				System.out.println("codigoTipoPago::"+datosPago.getCodigoTipoPago()+"::");
				System.out.println("descripcionTipoPago::"+datosPago.getDescripcionTipoPago()+"::");
				System.out.println("numeroOperacion::"+datosPago.getNumeroOperacion()+"::");
				System.out.println("fechaPago::"+datosPago.getFechaPago()+"::");
				System.out.println("horaPago::"+datosPago.getHoraPago()+"::");			
				/***********************************************************************************************/

				//Procesamos la solicitud de inscripcion
				presentacionSolicitudInscripcion = new PresentacionSolicitudInscripcion();
				respuesta = presentacionSolicitudInscripcion.procesarSolicitud(solicitudInscripcion, "", bean);			

				if(respuesta!=0)
					throw new CustomException("Error con codigo: " + respuesta);
			}
															

			System.out.println("Codigo Retorno::"+respuesta);

			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("resumenSolicitudImpresion");
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}
	
	protected ControllerResponse runObtenerDatosReservaParticipantesState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		ArrayList dptoProvTipDocus = null;
		ArrayList actos = null;
		
		SolicitudInscripcion solicitudInscripcion = null;
		Presentante presentante = null;
		PersonaJuridica personaJuridica = null;
		
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
		
		    UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			solicitudInscripcion = (SolicitudInscripcion) session.getAttribute("solicitudInscripcion");
			
			if (solicitudInscripcion == null)
				// Almacenamos los datos generales y del presentante en la estructura de clases
				solicitudInscripcion = new SolicitudInscripcion();
			
			/************************************** DATOS GENERALES ****************************************/
			String acto = request.getParameter("cboActo");
			/** codigoArea **/
			solicitudInscripcion.setCodigoArea(acto.substring(0,5));
			/** descripcionArea **/
			solicitudInscripcion.setDescripcionArea(request.getParameter("hidArea"));
			/** codigoLibro **/
			solicitudInscripcion.setCodigoLibro(acto.substring(6,9));
			/** descripcionLibro **/
			solicitudInscripcion.setDescripcionLibro(request.getParameter("hidLibro"));
			/** codigoActo **/
			solicitudInscripcion.setCodigoActo(acto.substring(10,15));
			/** descripcionActo **/
			solicitudInscripcion.setDescripcionActo(request.getParameter("hidActo"));
			String oficina = request.getParameter("cboOficinas");
			/** codigoZonaRegistral **/
			solicitudInscripcion.setCodigoZonaRegistral(oficina.substring(0,2));
			/** codigoOficinaRegistral **/
			solicitudInscripcion.setCodigoOficinaRegistral(oficina.substring(3,5));
			/** descripcionOficinaRegistral **/
			solicitudInscripcion.setDescripcionOficinaRegistral(((String)request.getParameter("hidOfic")).toUpperCase());
			/** cuo **/
			solicitudInscripcion.setCuo(null);/** por extranet cuo es null ***/
			/** codigoServicio **/
			solicitudInscripcion.setCodigoServicio("170");
			/** descripcionServicio **/
			solicitudInscripcion.setDescripcionServicio("SOLICITUD DE RESERVA DE PREFERENCIA REGISTRAL");
			/** codigoUsuario **/
			solicitudInscripcion.setCodigoUsuario(bean.getCuentaId());
			/** fechaSolicitud **/
			solicitudInscripcion.setFechaSolicitud(FechaUtil.getCurrentDate());
			
			System.out.println("SOLICITUD INSCRIPCION");
			System.out.println("codigoArea::"+solicitudInscripcion.getCodigoArea()+"::");
			System.out.println("descripcionArea::"+solicitudInscripcion.getDescripcionArea()+"::");
			System.out.println("codigoLibro::"+solicitudInscripcion.getCodigoLibro()+"::");
			System.out.println("descripcionLibro::"+solicitudInscripcion.getDescripcionLibro()+"::");
			System.out.println("codigoActo::"+solicitudInscripcion.getCodigoActo()+"::");		
			System.out.println("descripcionActo::"+solicitudInscripcion.getDescripcionActo()+"::");			
			System.out.println("codigoZonaRegistral::"+solicitudInscripcion.getCodigoZonaRegistral()+"::");			
			System.out.println("codigoOficinaRegistral::"+solicitudInscripcion.getCodigoOficinaRegistral()+"::");			
			System.out.println("cuo::"+solicitudInscripcion.getCuo()+"::");
			System.out.println("codigoServicio::"+solicitudInscripcion.getCodigoServicio()+"::");
			System.out.println("descripcionServicio::"+solicitudInscripcion.getDescripcionServicio()+"::");
			System.out.println("codigoUsuario::"+solicitudInscripcion.getCodigoUsuario()+"::");
			System.out.println("fechaSolicitud::"+solicitudInscripcion.getFechaSolicitud()+"::");
			/******************************************************************************************************/
			
			/********************************* DATOS PRESENTANTE **************************************************/
			presentante = new Presentante();	
			/** codigoPresentante **/
			presentante.setCodigoPresentante(request.getParameter("cboPresentante"));
			/** apellidoPaternoPresentante **/
			presentante.setApellidoPaterno(request.getParameter("txtSolApPa"));
			/** apellidoMaternoPresentante **/
			presentante.setApellidoMaterno(request.getParameter("txtSolApMa"));
			/** nombrePresentante **/
			presentante.setNombre(request.getParameter("txtSolNom"));
			/** codigoInstitucion **/
			presentante.setCodigoInstitucion(request.getParameter("txtSolCodInstitucion"));
			/** descripcionInstitucion **/
			presentante.setDescripcionInstitucion(request.getParameter("txtSolNomInstitucion"));
			/** codigoTipoDocPresentante **/
			presentante.setCodigoTipoDocumento(request.getParameter("cboSolTipDoc"));
			/** descripcionTipoDocPresentante **/
			presentante.setDescripcionTipoDocumento(request.getParameter("hidTipDoc"));
			/** numeroDocPresentante **/
			presentante.setNumeroDocumento(request.getParameter("txtSolNumDoc"));
			/** ubigeoPresentante **/
			presentante.setCodigoDepartamento(request.getParameter("cboEnvDpto").substring(3,5));
			presentante.setCodigoProvincia(request.getParameter("cboEnvProv").substring(6,8));
			presentante.setCodigoDistrito(request.getParameter("cboEnvDist").substring(9,11));
			/** descripcionDepartamentoPresentante **/
			presentante.setDescripcionDepartamento(request.getParameter("hidDep"));
			/** descripcionProvinciaPresentante **/			
			presentante.setDescripcionProvincia(request.getParameter("hidProv"));
			/** descripcionDistritoPresentante **/
			presentante.setDescripcionDistrito(request.getParameter("hidDist"));
			/** codigoTipoViaPresentante **/
			presentante.setCodigoTipoVia(request.getParameter("cboEnvTipoVia"));
			/** descripcionTipoViaPresentante **/
			presentante.setDescripcionTipoVia(request.getParameter("hidTipoVia"));
			/** direccionPresentante **/
			presentante.setDireccion(request.getParameter("txtEnvDire"));
			/** codigoPostalPresentante **/
			presentante.setCodigoPostal(request.getParameter("txtEnvCodPost"));
			/** correoElectronicoPresentante **/
			presentante.setCorreoElectronico(request.getParameter("txtEnvCorreoElectronico"));			
			
			solicitudInscripcion.setPresentante(presentante);
			
			System.out.println("PRESENTANTE");			
			System.out.println("codigoPresentante::"+presentante.getCodigoPresentante()+"::");
			System.out.println("apellidoPaterno::"+presentante.getApellidoPaterno()+"::");
			System.out.println("apellidoMaterno::"+presentante.getApellidoMaterno()+"::");
			System.out.println("nombre::"+presentante.getNombre()+"::");
			System.out.println("codigoInstitucion::"+presentante.getCodigoInstitucion()+"::");
			System.out.println("descripcionInstitucion::"+presentante.getDescripcionInstitucion()+"::");
			System.out.println("codigoTipoDocumento::"+presentante.getCodigoTipoDocumento()+"::");
			System.out.println("descripcionTipoDocumento::"+presentante.getDescripcionTipoDocumento()+"::");
			System.out.println("numeroDocumento::"+presentante.getNumeroDocumento()+"::");						
			System.out.println("codigoDepartamento::"+presentante.getCodigoDepartamento()+"::");						
			System.out.println("codigoProvincia::"+presentante.getCodigoProvincia()+"::");						
			System.out.println("codigoDistrito::"+presentante.getCodigoDistrito()+"::");						
			System.out.println("descripcionDepartamento::"+presentante.getDescripcionDepartamento()+"::");						
			System.out.println("descripcionProvincia::"+presentante.getDescripcionProvincia()+"::");
			System.out.println("descripcionDistrito::"+presentante.getDescripcionDistrito()+"::");
			System.out.println("codigoTipoVia::"+presentante.getCodigoTipoVia()+"::");
			System.out.println("descripcionTipoVia::"+presentante.getDescripcionTipoVia()+"::");
			System.out.println("direccion::"+presentante.getDireccion()+"::");
			System.out.println("codigoPostal::"+presentante.getCodigoPostal()+"::");
			System.out.println("correoElectronico::"+presentante.getCorreoElectronico()+"::");						
			/********************************************************************************************************/
						
			/******************************* DATOS DE LA PERSONA JURIDICA A RESERVAR ********************************/
			personaJuridica = solicitudInscripcion.getPersonaJuridica();
			if (personaJuridica==null)
				personaJuridica = new PersonaJuridica();
			
			if (acto.substring(10,15).equals("00821")) {
				session.setAttribute("flagTipoSociedad", "S");
				session.setAttribute("labelTipoSociedad","");
			/** codigoTipoSociedad **/	
				personaJuridica.setCodigoTipoSociedad("009");
			/** descripcionTipoSociedad **/
				personaJuridica.setDescripcionTipoSociedad("SOCIEDADES ANONIMAS");
			}
			else if (acto.substring(10,15).equals("01132")) {
				session.setAttribute("flagTipoSociedad", "N");
				session.setAttribute("labelTipoSociedad","E.I.R.L");
			/** codigoTipoSociedad **/	
				personaJuridica.setCodigoTipoSociedad("015");
			/** descripcionTipoSociedad **/
				personaJuridica.setDescripcionTipoSociedad("EMPRESAS INDIVIDUALES DE RESPONSABILIDAD LIMITADA");	
			}
			else if (acto.substring(10,15).equals("01121")) {
				session.setAttribute("flagTipoSociedad", "N");
				session.setAttribute("labelTipoSociedad","S.C.R.L");
			/** codigoTipoSociedad **/	
				personaJuridica.setCodigoTipoSociedad("010");	
			/** descripcionTipoSociedad **/
				personaJuridica.setDescripcionTipoSociedad("SOCIEDADES COMERCIALES DE RESPONSABILIDAD LIMITADA");		
			}
			else {
				session.setAttribute("flagTipoSociedad", "N");
				session.setAttribute("labelTipoSociedad","");
			/** codigoTipoSociedad **/	
				personaJuridica.setCodigoTipoSociedad("");				
			/** descripcionTipoSociedad **/
				personaJuridica.setDescripcionTipoSociedad("");		
			}
			
			solicitudInscripcion.setPersonaJuridica(personaJuridica);
			
			System.out.println("PERSONA JURIDICA A RESERVAR");
			System.out.println("codigoTipoSociedad::"+personaJuridica.getCodigoTipoSociedad()+"::");
			System.out.println("descripcionTipoSociedad::"+personaJuridica.getDescripcionTipoSociedad()+"::");
			/********************************************************************************************************/
			
			// En el caso de reserva de nombre el presentante se incluira como participante en la lista
			ArrayList listaParticipantesPN = solicitudInscripcion.getParticipantesPersonaNatural();
			if (listaParticipantesPN == null)
				listaParticipantesPN = new ArrayList();
			else 
				listaParticipantesPN.remove(0);
				
			PersonaNatural personaNatural = new PersonaNatural();
			/** codigoTipoDocPN **/
			personaNatural.setCodigoTipoDocumento(presentante.getCodigoTipoDocumento());
			/** descripcionTipoDocPN **/
			personaNatural.setDescripcionTipoDocumento(presentante.getDescripcionTipoDocumento());
			/** numeroDocPN **/
			personaNatural.setNumeroDocumento(presentante.getNumeroDocumento());
			/** codigoEstadoCivil **/
			personaNatural.setCodigoEstadoCivil(presentante.getCodigoEstadoCivil());
			/** descripcionEstadoCivil **/
			personaNatural.setDescripcionEstadoCivil(presentante.getDescripcionEstadoCivil());
			/** apellidoPaternoPN **/
			personaNatural.setApellidoPaterno(presentante.getApellidoPaterno());
			/** apellidoMaternoPN **/
			personaNatural.setApellidoMaterno(presentante.getApellidoMaterno());
			/** nombrePN **/
			personaNatural.setNombre(presentante.getNombre());
			if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("009")) {
			/** codigoTipoParticipantePN **/
				personaNatural.setCodigoTipoParticipante("092");
			/** descripcionTipoParticipantePN **/
				personaNatural.setDescripcionTipoParticipante("SOCIOS");
			}
			else if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("015")) {
			/** codigoTipoParticipantePN **/
				personaNatural.setCodigoTipoParticipante("038");
			/** descripcionTipoParticipantePN **/
				personaNatural.setDescripcionTipoParticipante("TITULAR");				
			}
			else if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("010")) {
			/** codigoTipoParticipantePN **/
				personaNatural.setCodigoTipoParticipante("062");
			/** descripcionTipoParticipantePN **/
				personaNatural.setDescripcionTipoParticipante("SOCIO(S)");				
			}
			else {
			/** codigoTipoParticipantePN **/
				personaNatural.setCodigoTipoParticipante("");
			/** descripcionTipoParticipantePN **/
				personaNatural.setDescripcionTipoParticipante("");								
			}							
			/** codigoNacionalidadPN **/
			personaNatural.setCodigoNacionalidad(presentante.getCodigoNacionalidad());
			/** descripcionNacionalidadPN **/
			personaNatural.setDescripcionNacionalidad(presentante.getDescripcionNacionalidad());
			/** ubigeoPN **/
			personaNatural.setCodigoDepartamento(presentante.getCodigoDepartamento());
			personaNatural.setCodigoProvincia(presentante.getCodigoProvincia());
			personaNatural.setCodigoDistrito(presentante.getCodigoDistrito());
			/** descripcionDepartamentoPN **/
			personaNatural.setDescripcionDepartamento(presentante.getDescripcionDepartamento());
			/** descripcionProvinciaPN **/
			personaNatural.setDescripcionProvincia(presentante.getDescripcionProvincia());
			/** descripcionDistritoPN **/
			personaNatural.setDescripcionDistrito(presentante.getDescripcionDistrito());
			/** codigoTipoViaPN **/
			personaNatural.setCodigoTipoVia(presentante.getCodigoTipoVia());
			/** descripcionTipoViaPN **/
			personaNatural.setDescripcionTipoVia(presentante.getDescripcionTipoVia());
			/** direccionPN **/
			personaNatural.setDireccion(presentante.getDireccion());
			/** codigoPostalPN **/
			personaNatural.setCodigoPostal(presentante.getCodigoPostal());
			/** correoElectronicoPN **/
			personaNatural.setCorreoElectronico(presentante.getCorreoElectronico());
				
			listaParticipantesPN.add(0,personaNatural);
	
			solicitudInscripcion.setParticipantesPersonaNatural(listaParticipantesPN);	
			////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// Seteamos en session el bean de solicitud de inscripcion
			session.setAttribute("solicitudInscripcion", solicitudInscripcion); 	
			
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosReservaParticipantes");
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}


	protected ControllerResponse runRegresarADatosReservaState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		try {

			init(request);
			validarSesion(request);

			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosReservaParticipantes");

		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}

	protected ControllerResponse runObtenerNuevoParticipanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		SolicitudInscripcion solicitudInscripcion = null;
		PersonaJuridica personaJuridica = null;
				
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");

			/******************************* DATOS DE LA PERSONA JURIDICA A RESERVAR  ******************************/
			personaJuridica = solicitudInscripcion.getPersonaJuridica();
			/** razonSocialPersonaJuridica **/
			personaJuridica.setRazonSocial(request.getParameter("txtSolRazonSocial"));
			/** siglas **/
			personaJuridica.setSiglas(request.getParameter("txtSolSiglas"));
			if (request.getParameter("cboTipoSociedad")!=null) {
			/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima(request.getParameter("cboTipoSociedad"));
				if (request.getParameter("cboTipoSociedad").equals("A"))
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
				else
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
			}
			else {
				/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima("");
				personaJuridica.setDescripcionTipoSociedadAnonima("");
			}
			/** codigoTipoVia **/
			personaJuridica.setCodigoTipoVia(request.getParameter("cboEnvTipoVia"));
			/** descripcionTipoVia **/
			personaJuridica.setDescripcionTipoVia(request.getParameter("hidTipoVia"));
			/** direccion **/
			personaJuridica.setDireccion(request.getParameter("txtSolDireccion"));
			
			solicitudInscripcion.setPersonaJuridica(personaJuridica);

			System.out.println("PERSONA JURIDICA A RESERVAR");
			System.out.println("razonSocial::"+personaJuridica.getRazonSocial()+"::");
			System.out.println("siglas::"+personaJuridica.getSiglas()+"::");
			System.out.println("codigoTipoSociedadAnonima::"+personaJuridica.getCodigoTipoSociedadAnonima()+"::");			
			System.out.println("descripcionTipoSociedadAnonima::"+personaJuridica.getDescripcionTipoSociedadAnonima()+"::");			
			System.out.println("codigoTipoVia::"+personaJuridica.getCodigoTipoVia()+"::");			
			System.out.println("descripcionTipoVia::"+personaJuridica.getDescripcionTipoVia()+"::");			
			System.out.println("direccion::"+personaJuridica.getDireccion()+"::");			
			/*********************************************************************************************************/
	
			// La primera vez seteo en session los combos de nacionalidad y estado civil 
			if (session.getAttribute("arrNacionalidad")==null)
				session.setAttribute("arrNacionalidad", Tarea.getComboPaises(dconn));
			if (session.getAttribute("arrEstadoCivil")==null)
				session.setAttribute("arrEstadoCivil", (ArrayList)Tarea.getComboEstadoCiviles(dconn));
			
			// Obtengo los Departamentos
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
	
			// Direccionamos a la pagina de detalle del participante
			response.setStyle("detalleParticipante");
	
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		finally {
			//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);
			end(request);
		}
		
		return response;
	}

	protected ControllerResponse runObtenerResumenAntesPagoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		PersonaJuridica personaJuridica = null;
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
						
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
				
			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");

			/******************************* DATOS DE LA PERSONA JURIDICA A RESERVAR  ******************************/
			personaJuridica = solicitudInscripcion.getPersonaJuridica();
			/** razonSocialPersonaJuridica **/
			personaJuridica.setRazonSocial(request.getParameter("txtSolRazonSocial"));
			/** siglas **/
			personaJuridica.setSiglas(request.getParameter("txtSolSiglas"));
			if (request.getParameter("cboTipoSociedad")!=null) {
			/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima(request.getParameter("cboTipoSociedad"));
				if (request.getParameter("cboTipoSociedad").equals("A"))
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
				else
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
			}
			else {
				/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima("");
				personaJuridica.setDescripcionTipoSociedadAnonima("");
			}
			/** codigoTipoVia **/
			personaJuridica.setCodigoTipoVia(request.getParameter("cboEnvTipoVia"));
			/** descripcionTipoVia **/
			personaJuridica.setDescripcionTipoVia(request.getParameter("hidTipoVia"));			
			/** direccion **/
			personaJuridica.setDireccion(request.getParameter("txtSolDireccion"));
			
			solicitudInscripcion.setPersonaJuridica(personaJuridica);

			System.out.println("PERSONA JURIDICA A RESERVAR");
			System.out.println("razonSocial::"+personaJuridica.getRazonSocial()+"::");
			System.out.println("siglas::"+personaJuridica.getSiglas()+"::");
			System.out.println("codigoTipoSociedadAnonima::"+personaJuridica.getCodigoTipoSociedadAnonima()+"::");			
			System.out.println("descripcionTipoSociedadAnonima::"+personaJuridica.getDescripcionTipoSociedadAnonima()+"::");			
			System.out.println("codigoTipoVia::"+personaJuridica.getCodigoTipoVia()+"::");			
			System.out.println("descripcionTipoVia::"+personaJuridica.getDescripcionTipoVia()+"::");			
			System.out.println("direccion::"+personaJuridica.getDireccion()+"::");			
			/*********************************************************************************************************/
			
			/*********************** CALCULAMOS LA TARIFA *********************************/
			int codServicio= gob.pe.sunarp.extranet.util.Constantes.COD_SERVICIO_RESERVANOMBRE;
			int codGLA= gob.pe.sunarp.extranet.util.Constantes.COD_GLA_SOLINSCR;
			
			DboTarifa tarifa = new DboTarifa(dconn);
			tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicio);
			tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codGLA);
		
			if(!tarifa.find())
				throw new DBException("No existe servicio con codigo '"+ codServicio + "' y GLA '"+ codGLA + "' en tabla TARIFA");
				
		    req.setAttribute("tarifa",tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
			/******************************************************************************/
				
			//Direccionamos a la pagina del resumen antes del pago
			response.setStyle("resumenSolicitudAntesPago");
	
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}
		catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		finally {
			//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);
			end(request);
		}
		
		return response;
	}


	protected ControllerResponse runObtenerParticipanteState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		PersonaNatural personaNatural = null;
		PersonaJuridica personaJuridica = null;
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
						
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
				
			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");

			/******************************* DATOS DE LA PERSONA JURIDICA A RESERVAR  ******************************/
			personaJuridica = solicitudInscripcion.getPersonaJuridica();
			/** razonSocialPersonaJuridica **/
			personaJuridica.setRazonSocial(request.getParameter("txtSolRazonSocial"));
			/** siglas **/
			personaJuridica.setSiglas(request.getParameter("txtSolSiglas"));
			if (request.getParameter("cboTipoSociedad")!=null) {
			/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima(request.getParameter("cboTipoSociedad"));
				if (request.getParameter("cboTipoSociedad").equals("A"))
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
				else
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
			}
			else {
				/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima("");
				personaJuridica.setDescripcionTipoSociedadAnonima("");
			}
			/** codigoTipoVia **/
			personaJuridica.setCodigoTipoVia(request.getParameter("cboEnvTipoVia"));
			/** descripcionTipoVia **/
			personaJuridica.setDescripcionTipoVia(request.getParameter("hidTipoVia"));
			/** direccion **/
			personaJuridica.setDireccion(request.getParameter("txtSolDireccion"));
			
			solicitudInscripcion.setPersonaJuridica(personaJuridica);

			System.out.println("PERSONA JURIDICA A RESERVAR");
			System.out.println("razonSocial::"+personaJuridica.getRazonSocial()+"::");
			System.out.println("siglas::"+personaJuridica.getSiglas()+"::");
			System.out.println("codigoTipoSociedadAnonima::"+personaJuridica.getCodigoTipoSociedadAnonima()+"::");			
			System.out.println("descripcionTipoSociedadAnonima::"+personaJuridica.getDescripcionTipoSociedadAnonima()+"::");			
			System.out.println("codigoTipoVia::"+personaJuridica.getCodigoTipoVia()+"::");			
			System.out.println("descripcionTipoVia::"+personaJuridica.getDescripcionTipoVia()+"::");			
			System.out.println("direccion::"+personaJuridica.getDireccion()+"::");			
			/*********************************************************************************************************/

			// Obtenemos el indice que fue seleccionado para modificacion
			String indice = request.getParameter("hidIndiceMod");
			req.setAttribute("indice",indice);
			
			// Obtenemos el tipo de persona que fue seleccionado
			String tipoPersona = request.getParameter("hidTipoPersona");
			
			if (tipoPersona.equals("PN")) {
				listaParticipantesPN = solicitudInscripcion.getParticipantesPersonaNatural();
				personaNatural = (PersonaNatural)listaParticipantesPN.get(Integer.parseInt(indice));

				req.setAttribute("codTipoPersona",tipoPersona);
				req.setAttribute("codTipoDoc",personaNatural.getCodigoTipoDocumento());
				req.setAttribute("numDoc",personaNatural.getNumeroDocumento());
				req.setAttribute("codEstadoCivil", personaNatural.getCodigoEstadoCivil());
				req.setAttribute("apePat",personaNatural.getApellidoPaterno());
				req.setAttribute("apeMat",personaNatural.getApellidoMaterno());	
				req.setAttribute("nom",personaNatural.getNombre());					
				req.setAttribute("codDepartamento",personaNatural.getCodigoDepartamento());
				req.setAttribute("codProvincia",personaNatural.getCodigoProvincia());
				req.setAttribute("codDistrito",personaNatural.getCodigoDistrito());
				req.setAttribute("tipoVia", personaNatural.getCodigoTipoVia());
				req.setAttribute("direccion",personaNatural.getDireccion());
				req.setAttribute("codPostal",personaNatural.getCodigoPostal());
				req.setAttribute("codNacionalidad",personaNatural.getCodigoNacionalidad());
				req.setAttribute("correoElec",personaNatural.getCorreoElectronico());	

				req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
				req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, personaNatural.getCodigoDepartamento()));
				req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, personaNatural.getCodigoDepartamento(), personaNatural.getCodigoProvincia()));

			}
			else {
				listaParticipantesPJ = solicitudInscripcion.getParticipantesPersonaJuridica();
				personaJuridica = (PersonaJuridica)listaParticipantesPJ.get(Integer.parseInt(indice));			

				req.setAttribute("codTipoPersona",tipoPersona);
				req.setAttribute("razonSocial", personaJuridica.getRazonSocial());
				req.setAttribute("siglas",personaJuridica.getSiglas());
				req.setAttribute("ruc",personaJuridica.getNumeroDocumento());
				req.setAttribute("codZonaOficina",personaJuridica.getCodigoZonaRegistral()+"|"+personaJuridica.getCodigoOficinaRegistral());	
				req.setAttribute("numeroPartida",personaJuridica.getNumeroPartida());					
				req.setAttribute("codDepartamento",personaJuridica.getCodigoDepartamento());
				req.setAttribute("codProvincia",personaJuridica.getCodigoProvincia());
				req.setAttribute("codDistrito",personaJuridica.getCodigoDistrito());
				req.setAttribute("tipoVia", personaJuridica.getCodigoTipoVia());
				req.setAttribute("direccion",personaJuridica.getDireccion());
				req.setAttribute("codPostal",personaJuridica.getCodigoPostal());
				req.setAttribute("codNacionalidad",personaJuridica.getCodigoNacionalidad());

				req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
				req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, personaJuridica.getCodigoDepartamento()));
				req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, personaJuridica.getCodigoDepartamento(), personaJuridica.getCodigoProvincia()));

			}
	
			response.setStyle("detalleParticipante");

		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
			
			e.printStackTrace();
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");

			ex.printStackTrace();
		} 
		finally {
			//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);		
			end(request);
		}
		
		return response;

	}

	
	protected ControllerResponse runAgregarParticipanteState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		PersonaJuridica personaJuridica = null;
		//PersonaJuridica personaJuridica2 = null;
		PersonaNatural personaNatural;
		String tipoPersona = null;						
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		String indice = null;
		int ind = 0;
				
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
	
			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");

			tipoPersona = (String)request.getParameter("cboTipoPersona");
		
			indice = request.getParameter("hidIndiceMod");
				
			/************************ DATOS DEL PARTICIPANTE PERSONA NATURAL*****************************************/
			if (tipoPersona.equals("PN")) {
				listaParticipantesPN = solicitudInscripcion.getParticipantesPersonaNatural();
				if (listaParticipantesPN==null)
					listaParticipantesPN = new ArrayList();
				/********* por si viene de modificacion ********/
				if ( (indice!=null) && (!indice.equals("")) ) {
					System.out.println("modificacion-indice::"+indice);
					ind = Integer.parseInt(indice);
					personaNatural = (PersonaNatural)listaParticipantesPN.get(ind);
				}
				/***********************************************/
				else {
					System.out.println("nuevo participante");
					personaNatural = new PersonaNatural();
				}
				/** codigoTipoDocPN **/
				personaNatural.setCodigoTipoDocumento(request.getParameter("cboSolTipDoc"));
				/** descripcionTipoDocPN **/
				personaNatural.setDescripcionTipoDocumento(request.getParameter("hidTipDoc"));
				/** numeroDocPN **/
				personaNatural.setNumeroDocumento(request.getParameter("txtSolNumDoc"));
				/** codigoEstadoCivil **/
				personaNatural.setCodigoEstadoCivil(request.getParameter("cboSolEstadoCivil"));
				/** descripcionEstadoCivil **/
				personaNatural.setDescripcionEstadoCivil(request.getParameter("hidEstadoCivil"));
				/** apellidoPaternoPN **/
				personaNatural.setApellidoPaterno(request.getParameter("txtSolApPa"));
				/** apellidoMaternoPN **/
				personaNatural.setApellidoMaterno(request.getParameter("txtSolApMa"));
				/** nombrePN **/
				personaNatural.setNombre(request.getParameter("txtSolNom"));
				if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("009")) {
				/** codigoTipoParticipantePN **/
					personaNatural.setCodigoTipoParticipante("092");
				/** descripcionTipoParticipantePN **/
					personaNatural.setDescripcionTipoParticipante("SOCIOS");
				}
				else if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("015")) {
				/** codigoTipoParticipantePN **/
					personaNatural.setCodigoTipoParticipante("038");
				/** descripcionTipoParticipantePN **/
					personaNatural.setDescripcionTipoParticipante("TITULAR");				
				}
				else if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("010")) {
				/** codigoTipoParticipantePN **/
					personaNatural.setCodigoTipoParticipante("062");
				/** descripcionTipoParticipantePN **/
					personaNatural.setDescripcionTipoParticipante("SOCIO(S)");				
				}
				else {
				/** codigoTipoParticipantePN **/
					personaNatural.setCodigoTipoParticipante("");
				/** descripcionTipoParticipantePN **/
					personaNatural.setDescripcionTipoParticipante("");								
				}							
				/** codigoNacionalidadPN **/
				personaNatural.setCodigoNacionalidad(request.getParameter("cboSolNacionalidad"));
				/** descripcionNacionalidadPN **/
				personaNatural.setDescripcionNacionalidad(request.getParameter("hidNacionalidad"));
				/** ubigeoPN **/
				personaNatural.setCodigoDepartamento(request.getParameter("cboEnvDpto").substring(3,5));
				personaNatural.setCodigoProvincia(request.getParameter("cboEnvProv").substring(6,8));
				personaNatural.setCodigoDistrito(request.getParameter("cboEnvDist").substring(9,11));
				/** descripcionDepartamentoPN **/
				personaNatural.setDescripcionDepartamento(request.getParameter("hidDep"));
				/** descripcionProvinciaPN **/
				personaNatural.setDescripcionProvincia(request.getParameter("hidProv"));
				/** descripcionDistritoPN **/
				personaNatural.setDescripcionDistrito(request.getParameter("hidDist"));
				/** codigoTipoViaPN **/
				personaNatural.setCodigoTipoVia(request.getParameter("cboEnvTipoVia"));
				/** descripcionTipoViaPN **/
				personaNatural.setDescripcionTipoVia(request.getParameter("hidTipoVia"));
				/** direccionPN **/
				personaNatural.setDireccion(request.getParameter("txtEnvDire"));
				/** codigoPostalPN **/
				personaNatural.setCodigoPostal(request.getParameter("txtEnvCodPost"));
				/** correoElectronicoPN **/
			    personaNatural.setCorreoElectronico(request.getParameter("txtEnvCorreoElectronico"));
				
				/********* por si viene de modificacion ********/
				if ( (indice!=null) && (indice.equals("")) ) 
					listaParticipantesPN.add(personaNatural);
	
				solicitudInscripcion.setParticipantesPersonaNatural(listaParticipantesPN);	

				System.out.println("PARTICIPANTE PERSONA NATURAL");
				System.out.println("codigoTipoDocumento::"+personaNatural.getCodigoTipoDocumento()+"::");			
				System.out.println("descripcionTipoDocumento::"+personaNatural.getDescripcionTipoDocumento()+"::");							
				System.out.println("numeroDocumento::"+personaNatural.getNumeroDocumento()+"::");							
				System.out.println("codigoEstadoCivil::"+personaNatural.getCodigoEstadoCivil()+"::");							
				System.out.println("descripcionEstadoCivil::"+personaNatural.getDescripcionEstadoCivil()+"::");							
				System.out.println("apellidoPaterno::"+personaNatural.getApellidoPaterno()+"::");							
				System.out.println("apellidoMaterno::"+personaNatural.getApellidoMaterno()+"::");							
				System.out.println("nombre::"+personaNatural.getNombre()+"::");																						
				System.out.println("codigoTipoParticipante::"+personaNatural.getCodigoTipoParticipante()+"::");	
				System.out.println("descripcionTipoParticipante::"+personaNatural.getDescripcionTipoParticipante()+"::");	
				System.out.println("codigoNacionalidad::"+personaNatural.getCodigoNacionalidad()+"::");	
				System.out.println("descripcionNacionalidad::"+personaNatural.getDescripcionNacionalidad()+"::");	
				System.out.println("codigoDepartamento::"+personaNatural.getCodigoDepartamento()+"::");						
				System.out.println("codigoProvincia::"+personaNatural.getCodigoProvincia()+"::");						
				System.out.println("codigoDistrito::"+personaNatural.getCodigoDistrito()+"::");						
				System.out.println("descripcionDepartamento::"+personaNatural.getDescripcionDepartamento()+"::");						
				System.out.println("descripcionProvincia::"+personaNatural.getDescripcionProvincia()+"::");
				System.out.println("descripcionDistrito::"+personaNatural.getDescripcionDistrito()+"::");
				System.out.println("codigoTipoVia::"+personaNatural.getCodigoTipoVia()+"::");
				System.out.println("descripcionTipoVia::"+personaNatural.getDescripcionTipoVia()+"::");
				System.out.println("direccion::"+personaNatural.getDireccion()+"::");
				System.out.println("codigoPostal::"+personaNatural.getCodigoPostal()+"::");
				System.out.println("correoElectronico::"+personaNatural.getCorreoElectronico()+"::");						
			}
			/*********************************************************************************************************/
			/************************************** DATOS DEL PARTICIPANTE PERSONA JURIDICA **************************/
			else {			
				listaParticipantesPJ = solicitudInscripcion.getParticipantesPersonaJuridica();
				if (listaParticipantesPJ==null)
					listaParticipantesPJ = new ArrayList();				
				/********* por si viene de modificacion ********/
				if ( (indice!=null) && (!indice.equals("")) ) {
					System.out.println("modificacion-indice::"+indice);
					ind = Integer.parseInt(indice);
					personaJuridica = (PersonaJuridica)listaParticipantesPJ.get(ind);
				}
				/***********************************************/
				else {
					System.out.println("nuevo participante");
					personaJuridica = new PersonaJuridica();
				}
				/** codigoTipoDocPJ **/
				personaJuridica.setCodigoTipoDocumento("05");
				/** descripcionTipoDocPJ **/
				personaJuridica.setDescripcionTipoDocumento("R.U.C");
				/** numeroDocPJ **/
				personaJuridica.setNumeroDocumento(request.getParameter("txtSolRUC"));
				if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("009")) {
				/** codigoTipoParticipantePJ **/
					personaJuridica.setCodigoTipoParticipante("092");
				/** descripcionTipoParticipantePJ **/
					personaJuridica.setDescripcionTipoParticipante("SOCIOS");
				}
				else if (solicitudInscripcion.getPersonaJuridica().getCodigoTipoSociedad().equals("010")) {
				/** codigoTipoParticipantePJ **/
					personaJuridica.setCodigoTipoParticipante("062");
				/** descripcionTipoParticipantePJ **/
					personaJuridica.setDescripcionTipoParticipante("SOCIO(S)");				
				}
				else {
				/** codigoTipoParticipantePJ **/
					personaJuridica.setCodigoTipoParticipante("");
				/** descripcionTipoParticipantePJ **/
					personaJuridica.setDescripcionTipoParticipante("");								
				}							
				/** codigoNacionalidadPJ **/
				personaJuridica.setCodigoNacionalidad(request.getParameter("cboSolNacionalidad"));
				/** descripcionNacionalidadPJ **/
				personaJuridica.setDescripcionNacionalidad(request.getParameter("hidNacionalidad"));
				/** razonSocialPJ **/
				personaJuridica.setRazonSocial(request.getParameter("txtSolRazonSocial"));
				/** siglasPJ **/
				personaJuridica.setSiglas(request.getParameter("txtSolSiglas"));
				/** numeroPartidaPJ **/
				personaJuridica.setNumeroPartida(request.getParameter("txtSolNumPartida"));
				String oficina = request.getParameter("cboOficinas");
				/** codigoZonaRegistralPJ **/
				personaJuridica.setCodigoZonaRegistral(oficina.substring(0,2));
				/** codigoOficinaRegistralPJ **/
				personaJuridica.setCodigoOficinaRegistral(oficina.substring(3,5));
				/** ubigeoPJ **/
				personaJuridica.setCodigoDepartamento(request.getParameter("cboEnvDpto").substring(3,5));
				personaJuridica.setCodigoProvincia(request.getParameter("cboEnvProv").substring(6,8));
				personaJuridica.setCodigoDistrito(request.getParameter("cboEnvDist").substring(9,11));
				/** descripcionDepartamentoPJ **/
				personaJuridica.setDescripcionDepartamento(request.getParameter("hidDep"));
				/** descripcionProvinciaPJ **/
				personaJuridica.setDescripcionProvincia(request.getParameter("hidProv"));
				/** descripcionDistritoPJ **/
				personaJuridica.setDescripcionDistrito(request.getParameter("hidDist"));
				/** codigoTipoViaPJ **/
				personaJuridica.setCodigoTipoVia(request.getParameter("cboEnvTipoVia"));
				/** descripcionTipoViaPJ **/
				personaJuridica.setDescripcionTipoVia(request.getParameter("hidTipoVia"));
				/** direccionPJ **/
				personaJuridica.setDireccion(request.getParameter("txtEnvDire"));
				/** codigoPostalPJ **/
				personaJuridica.setCodigoPostal(request.getParameter("txtEnvCodPost"));

				/********* por si viene de modificacion ********/
				if ( (indice!=null) && (indice.equals("")) ) 
					listaParticipantesPJ.add(personaJuridica);
				
				solicitudInscripcion.setParticipantesPersonaJuridica(listaParticipantesPJ);	

				System.out.println("PARTICIPANTE PERSONA JURIDICA");
				System.out.println("codigoTipoDocumento::"+personaJuridica.getCodigoTipoDocumento()+"::");			
				System.out.println("descripcionTipoDocumento::"+personaJuridica.getDescripcionTipoDocumento()+"::");							
				System.out.println("numeroDocumento::"+personaJuridica.getNumeroDocumento()+"::");							
				System.out.println("codigoTipoParticipante::"+personaJuridica.getCodigoTipoParticipante()+"::");	
				System.out.println("descripcionTipoParticipante::"+personaJuridica.getDescripcionTipoParticipante()+"::");	
				System.out.println("codigoNacionalidad::"+personaJuridica.getCodigoNacionalidad()+"::");	
				System.out.println("descripcionNacionalidad::"+personaJuridica.getDescripcionNacionalidad()+"::");	
				System.out.println("razonSocial::"+personaJuridica.getRazonSocial()+"::");	
				System.out.println("siglas::"+personaJuridica.getSiglas()+"::");	
				System.out.println("numeroPartida::"+personaJuridica.getNumeroPartida()+"::");	
				System.out.println("codigoZonaRegistral::"+personaJuridica.getCodigoZonaRegistral()+"::");	
				System.out.println("codigoOficinaRegistral::"+personaJuridica.getCodigoOficinaRegistral()+"::");	
				System.out.println("codigoDepartamento::"+personaJuridica.getCodigoDepartamento()+"::");						
				System.out.println("codigoProvincia::"+personaJuridica.getCodigoProvincia()+"::");						
				System.out.println("codigoDistrito::"+personaJuridica.getCodigoDistrito()+"::");						
				System.out.println("descripcionDepartamento::"+personaJuridica.getDescripcionDepartamento()+"::");						
				System.out.println("descripcionProvincia::"+personaJuridica.getDescripcionProvincia()+"::");
				System.out.println("descripcionDistrito::"+personaJuridica.getDescripcionDistrito()+"::");
				System.out.println("codigoTipoVia::"+personaJuridica.getCodigoTipoVia()+"::");
				System.out.println("descripcionTipoVia::"+personaJuridica.getDescripcionTipoVia()+"::");
				System.out.println("direccion::"+personaJuridica.getDireccion()+"::");
				System.out.println("codigoPostal::"+personaJuridica.getCodigoPostal()+"::");
			}			
			/**********************************************************************************************************/
					
			// Direccionamos a la pagina de los Datos de Reserva
			response.setStyle("datosReservaParticipantes");
	
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
			
			e.printStackTrace();
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");

			ex.printStackTrace();
		} 
		finally {
			end(request);
		}
		
		return response;

	}

	protected ControllerResponse runBorrarParticipanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		ArrayList listaParticipantesPN2 = null;
		ArrayList listaParticipantesPJ2 = null;
		SolicitudInscripcion solicitudInscripcion = null;
		int size1 = 0;
		int size2 = 0;
		int sizeLista1 = 0;
		int sizeLista2 = 0;
		PersonaNatural personaNatural = null;
		PersonaJuridica personaJuridica = null;	
				
		try {	
					
			init(request);
			validarSesion(request);

			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");

			/******************************* DATOS DE LA PERSONA JURIDICA A RESERVAR  ******************************/
			personaJuridica = solicitudInscripcion.getPersonaJuridica();
			/** razonSocialPersonaJuridica **/
			personaJuridica.setRazonSocial(request.getParameter("txtSolRazonSocial"));
			/** siglas **/
			personaJuridica.setSiglas(request.getParameter("txtSolSiglas"));
			if (request.getParameter("cboTipoSociedad")!=null) {
			/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima(request.getParameter("cboTipoSociedad"));
				if (request.getParameter("cboTipoSociedad").equals("A"))
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
				else
			/** descripcionTipoSociedadAnonima **/
					personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
			}
			else {
				/** codigoTipoSociedadAonima **/
				personaJuridica.setCodigoTipoSociedadAnonima("");
				personaJuridica.setDescripcionTipoSociedadAnonima("");
			}
			/** codigoTipoVia **/
			personaJuridica.setCodigoTipoVia(request.getParameter("cboEnvTipoVia"));
			/** descripcionTipoVia **/
			personaJuridica.setDescripcionTipoVia(request.getParameter("hidTipoVia"));
			/** direccion **/
			personaJuridica.setDireccion(request.getParameter("txtSolDireccion"));
			
			solicitudInscripcion.setPersonaJuridica(personaJuridica);

			System.out.println("PERSONA JURIDICA A RESERVAR");
			System.out.println("razonSocial::"+personaJuridica.getRazonSocial()+"::");
			System.out.println("siglas::"+personaJuridica.getSiglas()+"::");
			System.out.println("codigoTipoSociedadAnonima::"+personaJuridica.getCodigoTipoSociedadAnonima()+"::");			
			System.out.println("descripcionTipoSociedadAnonima::"+personaJuridica.getDescripcionTipoSociedadAnonima()+"::");			
			System.out.println("codigoTipoVia::"+personaJuridica.getCodigoTipoVia()+"::");			
			System.out.println("descripcionTipoVia::"+personaJuridica.getDescripcionTipoVia()+"::");			
			System.out.println("direccion::"+personaJuridica.getDireccion()+"::");			
			/*********************************************************************************************************/

			// Borrar
			String[] indicesPN = req.getParameterValues("indicesListaPN");
			String[] indicesPJ = req.getParameterValues("indicesListaPJ");
			
			if (indicesPN!=null) 
				size1 = indicesPN.length;
			
			if (indicesPJ!=null)
				size2 = indicesPJ.length;
			
			listaParticipantesPN = solicitudInscripcion.getParticipantesPersonaNatural();
			listaParticipantesPJ = solicitudInscripcion.getParticipantesPersonaJuridica();
			
			if (listaParticipantesPN!=null)
				sizeLista1 = listaParticipantesPN.size();
				

			if (listaParticipantesPJ!=null)
				sizeLista2 = listaParticipantesPJ.size();

			listaParticipantesPN2 = new ArrayList();
			listaParticipantesPJ2 = new ArrayList();
			boolean res = false;

			for (int i=0; i<sizeLista1; i++) {
				res = false;
				personaNatural = (PersonaNatural)listaParticipantesPN.get(i);
				for (int j=0; j<size1; j++) {
					if (i == Integer.parseInt(indicesPN[j]) )
						res = true;
				}
				if (res==false)
					listaParticipantesPN2.add(personaNatural);
				
			}			
			solicitudInscripcion.setParticipantesPersonaNatural(listaParticipantesPN2);
			

			res = false;
			for (int i=0; i<sizeLista2; i++) {
				res = false;
				personaJuridica = (PersonaJuridica)listaParticipantesPJ.get(i);
				for (int j=0; j<size2; j++) {
					if (i == Integer.parseInt(indicesPJ[j]) )
						res = true;
				}
				if (res==false)
					listaParticipantesPJ2.add(personaJuridica);
				
			}			
			solicitudInscripcion.setParticipantesPersonaJuridica(listaParticipantesPJ2);
		
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosReservaParticipantes");
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			//JDBC.getInstance().closeResultSet(rsetGla);
			//JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		
		return response;

	
	}
	
	
	protected ControllerResponse runObtenerProvinciaState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		
		try {	
					
			init(request);
			validarSesion(request);

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
	
			String codDepartamento = ((String)request.getParameter("cboEnvDpto")).substring(3,5);
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, codDepartamento));

			req.setAttribute("codTipoPersona",request.getParameter("cboTipoPersona"));
			req.setAttribute("codTipoDoc",request.getParameter("cboSolTipDoc"));
			if (request.getParameter("txtSolNumDoc")!=null)
				req.setAttribute("numDoc",request.getParameter("txtSolNumDoc"));
			else
				req.setAttribute("numDoc","");
			req.setAttribute("codEstadoCivil",request.getParameter("cboSolEstadoCivil"));
			if (request.getParameter("txtSolApPa")!=null)
				req.setAttribute("apePat",request.getParameter("txtSolApPa"));
			else
				req.setAttribute("apePat","");
			if (request.getParameter("txtSolApMa")!=null)				
				req.setAttribute("apeMat",request.getParameter("txtSolApMa"));	
			else
				req.setAttribute("apePat","");
			if (request.getParameter("txtSolNom")!=null)					
				req.setAttribute("nom",request.getParameter("txtSolNom"));					
			else
				req.setAttribute("nom","");					
			req.setAttribute("codDepartamento",codDepartamento);
			if (request.getParameter("cboEnvTipoVia")!=null)
				req.setAttribute("tipoVia",request.getParameter("cboEnvTipoVia"));
			else
				req.setAttribute("tipoVia","");
			if (request.getParameter("txtEnvDire")!=null)
				req.setAttribute("direccion",request.getParameter("txtEnvDire"));
			else
				req.setAttribute("direccion","");
			req.setAttribute("codPostal",request.getParameter("txtEnvCodPost"));
			req.setAttribute("codNacionalidad",request.getParameter("cboSolNacionalidad"));
			if (request.getParameter("txtEnvCorreoElectronico")!=null)
				req.setAttribute("correoElec",request.getParameter("txtEnvCorreoElectronico"));			
			else
				req.setAttribute("correoElec","");	

			if (request.getParameter("txtSolNumPartida")!=null)
				req.setAttribute("numeroPartida",request.getParameter("txtSolNumPartida"));			
			else
				req.setAttribute("numeroPartida","");	
			if (request.getParameter("txtSolRazonSocial")!=null)
				req.setAttribute("razonSocial",request.getParameter("txtSolRazonSocial"));			
			else
				req.setAttribute("razonSocial","");
			if (request.getParameter("txtSolSiglas")!=null)
				req.setAttribute("siglas",request.getParameter("txtSolSiglas"));			
			else
				req.setAttribute("siglas","");
			if (request.getParameter("txtSolRUC")!=null)
				req.setAttribute("ruc",request.getParameter("txtSolRUC"));			
			else
				req.setAttribute("ruc","");
			req.setAttribute("codZonaOficina",request.getParameter("cboOficinas"));

			// Obtenemos el indice que fue seleccionado para modificacion
			String indice = request.getParameter("hidIndiceMod");
			req.setAttribute("indice",indice);
																							
			// Direccionamos a la pagina de detalle del participante
			response.setStyle("detalleParticipante");
		
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			//JDBC.getInstance().closeResultSet(rsetGla);
			//JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		
		return response;

	
	}


	protected ControllerResponse runObtenerDistritoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		
		try {	
					
			init(request);
			validarSesion(request);

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
	

			String codDepartamento = ((String)request.getParameter("cboEnvDpto")).substring(3,5);
			String codProvincia = ((String)request.getParameter("cboEnvProv")).substring(6,8);
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, codDepartamento));
			if (!codProvincia.equals("  "))
				req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, codDepartamento, codProvincia));
			
			req.setAttribute("codTipoPersona",request.getParameter("cboTipoPersona"));
			req.setAttribute("codTipoDoc",request.getParameter("cboSolTipDoc"));
			if (request.getParameter("txtSolNumDoc")!=null)
				req.setAttribute("numDoc",request.getParameter("txtSolNumDoc"));
			else
				req.setAttribute("numDoc","");
			req.setAttribute("codEstadoCivil",request.getParameter("cboSolEstadoCivil"));
			if (request.getParameter("txtSolApPa")!=null)
				req.setAttribute("apePat",request.getParameter("txtSolApPa"));
			else
				req.setAttribute("apePat","");
			if (request.getParameter("txtSolApMa")!=null)				
				req.setAttribute("apeMat",request.getParameter("txtSolApMa"));	
			else
				req.setAttribute("apePat","");
			if (request.getParameter("txtSolNom")!=null)					
				req.setAttribute("nom",request.getParameter("txtSolNom"));					
			else
				req.setAttribute("nom","");					
			req.setAttribute("codDepartamento",codDepartamento);
			req.setAttribute("codProvincia",codProvincia);
			if (request.getParameter("cboEnvTipoVia")!=null)
				req.setAttribute("tipoVia",request.getParameter("cboEnvTipoVia"));
			else
				req.setAttribute("tipoVia","");
			if (request.getParameter("txtEnvDire")!=null)
				req.setAttribute("direccion",request.getParameter("txtEnvDire"));
			else
				req.setAttribute("direccion","");
			req.setAttribute("codPostal",request.getParameter("txtEnvCodPost"));
			req.setAttribute("codNacionalidad",request.getParameter("cboSolNacionalidad"));
			if (request.getParameter("txtEnvCorreoElectronico")!=null)
				req.setAttribute("correoElec",request.getParameter("txtEnvCorreoElectronico"));			
			else
				req.setAttribute("correoElec","");			

			if (request.getParameter("txtSolNumPartida")!=null)
				req.setAttribute("numeroPartida",request.getParameter("txtSolNumPartida"));			
			else
				req.setAttribute("numeroPartida","");	
			if (request.getParameter("txtSolRazonSocial")!=null)
				req.setAttribute("razonSocial",request.getParameter("txtSolRazonSocial"));			
			else
				req.setAttribute("razonSocial","");
			if (request.getParameter("txtSolSiglas")!=null)
				req.setAttribute("siglas",request.getParameter("txtSolSiglas"));			
			else
				req.setAttribute("siglas","");
			if (request.getParameter("txtSolRUC")!=null)
				req.setAttribute("ruc",request.getParameter("txtSolRUC"));			
			else
				req.setAttribute("ruc","");
			req.setAttribute("codZonaOficina",request.getParameter("cboOficinas"));

			// Obtenemos el indice que fue seleccionado para modificacion
			String indice = request.getParameter("hidIndiceMod");
			req.setAttribute("indice",indice);
			
			// Direccionamos a la pagina de detalle del participante				
			response.setStyle("detalleParticipante");
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			//JDBC.getInstance().closeResultSet(rsetGla);
			//JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		
		return response;

	
	}
	
}

