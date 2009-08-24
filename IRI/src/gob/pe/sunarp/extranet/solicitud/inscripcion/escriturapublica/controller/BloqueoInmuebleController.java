package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.controller;

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
import java.math.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.PresentacionSolicitudInscripcion;
import java.text.SimpleDateFormat;

import org.apache.commons.fileupload.*;
import java.util.*;
import java.io.*;

public class BloqueoInmuebleController extends ControllerExtension {


	public BloqueoInmuebleController() {
		super();
		addState(new State("obtenerDatosBloqueo", "Datos Bloqueo Inmueble y de Participantes"));
		addState(new State("obtenerNuevoParticipante", "Nuevo Participante"));
		addState(new State("obtenerNuevaPartida", "Nueva Partida"));
		addState(new State("borrarParticipante", "Borrar Participante"));
		addState(new State("borrarPartida", "Borrar Participante"));
		addState(new State("agregarParticipante", "Agregar Participante"));
		addState(new State("agregarPartida", "Agregar Participante"));
		addState(new State("obtenerProvincia", "Obtiene Provincia"));
		addState(new State("obtenerDistrito", "Obtiene Distrito"));
		addState(new State("regresarABloqueoInmueble", "Regresar a Datos de Bloqueo de Inmueble"));
		addState(new State("obtenerParticipante", "Modificar Participante"));
		addState(new State("obtenerPartida", "Modificar Partida"));
		addState(new State("obtenerResumenAntesPago", "Obtener Resumen Antes del Pago"));
		addState(new State("procesarSolicitud", "Procesar Solicitud"));
		addState(new State("llenaPartida", "Llena los datos de partida involucrados"));
		addState(new State("llenaPartidaRango", "Llena los datos por Rango de Partidas"));
		setInitialState("obtenerDatosBloqueo");
				
	}

   protected ControllerResponse runObtenerDatosBloqueoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
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
			solicitudInscripcion.setCuo(SecuencialCuo.obtieneSecuenciaCuo());/** POR EXTRANET CUO ES Secuencial **/
			/** codigoServicio **/
			 
			solicitudInscripcion.setCodigoServicio(String.valueOf(gob.pe.sunarp.extranet.util.Constantes.COD_SERVICIO_BLOQUEOINMUEBLE));
			/** descripcionServicio **/
			solicitudInscripcion.setDescripcionServicio("SOLICITUD DE BLOQUEO INMUEBLE");
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
			
			// Seteamos en session el bean de solicitud de inscripcion
			session.setAttribute("solicitudInscripcion", solicitudInscripcion); 	
			
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosBloqueo");
			
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
		Capital capital = null;
		InstrumentoPublico instrumentoPublico = null;		
		ReservaMercantil reservaMercantil = null;
		EscrituraPublica escrituraPublica = null;
		
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

			// Upload
			DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(req);
            Iterator it = items.iterator();
	        FileItem item = null;
	        String name, value;
	        
	        // Inicializamos Beans
	        
			ArrayList instrumentosPublico = solicitudInscripcion.getInstrumentoPublico();
			if ( (instrumentosPublico!=null) && (instrumentosPublico.size()>0) ) {
				instrumentoPublico = (InstrumentoPublico)instrumentosPublico.get(0);
				instrumentosPublico.remove(0);
			}
			else
				instrumentosPublico = new ArrayList();
			if (instrumentoPublico==null)
				instrumentoPublico = new InstrumentoPublico();
									        
	        
	        escrituraPublica = solicitudInscripcion.getEscrituraPublica();
			if (escrituraPublica==null)
				escrituraPublica = new EscrituraPublica();
			
			String indicador = null;		        
			
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* INSTRUMENTO PUBLICO **************************************************/
					if (name.equals("txtSolFecha")) {
						if ( (value!=null) && (!value.equals("")) )
							/** fecha **/
							instrumentoPublico.setFecha(value.substring(6,10)+value.substring(3,5)+value.substring(0,2));
					}
					else if (name.equals("txtSolLugar"))
						/** lugar **/
						instrumentoPublico.setLugar(value);
					else if (name.equals("txtSolOtros"))
						/** otros **/
						instrumentoPublico.setOtros(value);
                	/*********************************************************************************************************/
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
	                    //in.close();
                    }
                }
            }

			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("022"); //codigo de la boleta notarial

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("BOLETA NOTARIAL");

			/** secuencia **/
			instrumentoPublico.setSecuencia(new Long("1"));

			instrumentosPublico.add(instrumentoPublico);
				
			solicitudInscripcion.setInstrumentoPublico(instrumentosPublico);
					
			System.out.println("INSTRUMENTO PUBLICO");
			System.out.println("codigoTipoInstrumento::"+instrumentoPublico.getCodigoTipoInstrumento()+"::");
			System.out.println("descripcionTipoInstrumento::"+instrumentoPublico.getDescripcionTipoInstrumento()+"::");			
			System.out.println("fecha::"+instrumentoPublico.getFecha()+"::");
			System.out.println("lugar::"+instrumentoPublico.getLugar()+"::");			
			System.out.println("otros::"+instrumentoPublico.getOtros()+"::");
			System.out.println("secuencia::"+instrumentoPublico.getSecuencia()+"::");
			/******************************************************************************************************/
			
			solicitudInscripcion.setEscrituraPublica(escrituraPublica);
			
			System.out.println("BOLETA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/
			
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
		//Capital capital = null;
		InstrumentoPublico instrumentoPublico = null;		
		//ReservaMercantil reservaMercantil = null;
		EscrituraPublica escrituraPublica = null;
		
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

			// Upload
			DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(req);
            Iterator it = items.iterator();
	        FileItem item = null;
	        String name, value;
	        
	        // Inicializamos Beans
	        personaJuridica = solicitudInscripcion.getPersonaJuridica();
			
			
			ArrayList instrumentosPublico = solicitudInscripcion.getInstrumentoPublico();
			if ( (instrumentosPublico!=null) && (instrumentosPublico.size()>0) ) {
				instrumentoPublico = (InstrumentoPublico)instrumentosPublico.get(0);
				instrumentosPublico.remove(0);
			}
			else
				instrumentosPublico = new ArrayList();
			if (instrumentoPublico==null)
				instrumentoPublico = new InstrumentoPublico();
									        
	        
	        escrituraPublica = solicitudInscripcion.getEscrituraPublica();
			if (escrituraPublica==null)
				escrituraPublica = new EscrituraPublica();
			

			Vector vIndicesPN = new Vector();
			Vector vIndicesPJ = new Vector();
			
			
			String indicador = null;		        
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* DATOS DE LA PERSONA JURIDICA A CONSTITUIR  ****************************/
                   	if (name.equals("txtSolRazonSocial"))
						/** razonSocialPersonaJuridica **/
						personaJuridica.setRazonSocial(value);
					else if (name.equals("txtSolSiglas"))
						/** siglas **/
						personaJuridica.setSiglas(value);
                   	else if (name.equals("cboTipoSociedad")) {
						/** codigoTipoSociedadAonima **/
						personaJuridica.setCodigoTipoSociedadAnonima(value);
						if (value.equals("A"))
						/** descripcionTipoSociedadAnonima **/
							personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
						else
						/** descripcionTipoSociedadAnonima **/
							personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
                   	}
					else if (name.equals("txtSolRequiereRUC")) {
						/** indicadorRUC **/
						indicador = value;
					}
					
					/******************************* INSTRUMENTO PUBLICO **************************************************/
					else if (name.equals("txtSolFecha")) {
						if ( (value!=null) && (!value.equals("")) )
							/** fecha **/
							instrumentoPublico.setFecha(value.substring(6,10)+value.substring(3,5)+value.substring(0,2));
					}
					else if (name.equals("txtSolLugar"))
						/** lugar **/
						instrumentoPublico.setLugar(value);
					else if (name.equals("txtSolOtros"))
						/** otros **/
						instrumentoPublico.setOtros(value);
                	/*********************************************************************************************************/
                	else if (name.equals("indicesListaPN")) {
                		vIndicesPN.add(value);
                	}
                	else if (name.equals("indicesListaPJ"))
                		vIndicesPJ.add(value);
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
	                    //in.close();
                    }
                }
            }

									
			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("022");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("BOLETA NOTARIAL");

			/** secuencia **/
			instrumentoPublico.setSecuencia(new Long("1"));

			instrumentosPublico.add(instrumentoPublico);
				
			solicitudInscripcion.setInstrumentoPublico(instrumentosPublico);
					
			System.out.println("INSTRUMENTO PUBLICO");
			System.out.println("codigoTipoInstrumento::"+instrumentoPublico.getCodigoTipoInstrumento()+"::");
			System.out.println("descripcionTipoInstrumento::"+instrumentoPublico.getDescripcionTipoInstrumento()+"::");			
			System.out.println("fecha::"+instrumentoPublico.getFecha()+"::");
			System.out.println("lugar::"+instrumentoPublico.getLugar()+"::");			
			System.out.println("otros::"+instrumentoPublico.getOtros()+"::");
			System.out.println("secuencia::"+instrumentoPublico.getSecuencia()+"::");
			/******************************************************************************************************/
			
			solicitudInscripcion.setEscrituraPublica(escrituraPublica);
			
			System.out.println("BOLETA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/


			//Borrar				
			String[] indicesPN = new String[vIndicesPN.size()];
			String[] indicesPJ = new String[vIndicesPJ.size()];
			vIndicesPN.copyInto(indicesPN);
			vIndicesPJ.copyInto(indicesPJ);

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

			// Direccionamos a la pagina de Datos de Bloqueo de Inmueble	
			response.setStyle("datosBloqueo");
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
				/** codigoTipoParticipantePN **/
				personaNatural.setCodigoTipoParticipante("059");
				/** descripcionTipoParticipantePN **/
				personaNatural.setDescripcionTipoParticipante("INTERESADO");						
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
				/** codigoTipoParticipantePJ **/
				personaJuridica.setCodigoTipoParticipante("059");
				/** descripcionTipoParticipantePJ **/
				personaJuridica.setDescripcionTipoParticipante("INTERESADO");
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
			response.setStyle("datosBloqueo");
	
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
			// Si selecciono indicadorRUC
			if ( (request.getParameter("txtSolFechaNacimiento")!=null) && (!request.getParameter("txtSolFechaNacimiento").equals("")) ) {
				String valor1 = request.getParameter("txtSolFechaNacimiento");
				req.setAttribute("fecNacim", valor1.substring(6,10)+valor1.substring(3,5)+valor1.substring(0,2));			
			}
			else
				req.setAttribute("fecNacim","");
			req.setAttribute("codCargo",request.getParameter("cboSolCargo"));
			if ( (request.getParameter("txtSolFechaCargo")!=null) && (!request.getParameter("txtSolFechaCargo").equals("")) ) {
				String valor2 = request.getParameter("txtSolFechaCargo");
				req.setAttribute("fecCargo",valor2.substring(6,10)+valor2.substring(3,5)+valor2.substring(0,2));			
			}
			else
				req.setAttribute("fecCargo","");

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
			// Si selecciono indicadorRUC
			if ( (request.getParameter("txtSolFechaNacimiento")!=null) && (!request.getParameter("txtSolFechaNacimiento").equals("")) ) {
				String valor1 = request.getParameter("txtSolFechaNacimiento");
				req.setAttribute("fecNacim", valor1.substring(6,10)+valor1.substring(3,5)+valor1.substring(0,2));			
			}		
			else
				req.setAttribute("fecNacim","");
			req.setAttribute("codCargo",request.getParameter("cboSolCargo"));
			if ( (request.getParameter("txtSolFechaCargo")!=null) && (!request.getParameter("txtSolFechaCargo").equals("")) ) {
				String valor2 = request.getParameter("txtSolFechaCargo");
				req.setAttribute("fecCargo",valor2.substring(6,10)+valor2.substring(3,5)+valor2.substring(0,2));			
			}
			else
				req.setAttribute("fecCargo","");

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

	protected ControllerResponse runObtenerNuevaPartidaState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		SolicitudInscripcion solicitudInscripcion = null;
		PersonaJuridica personaJuridica = null;
		InstrumentoPublico instrumentoPublico = null;		
		EscrituraPublica escrituraPublica = null;
		
		
		
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

			// Upload
			DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(req);
            Iterator it = items.iterator();
	        FileItem item = null;
	        String name, value;
	        
	        // Inicializamos Beans
	        
			ArrayList instrumentosPublico = solicitudInscripcion.getInstrumentoPublico();
			if ( (instrumentosPublico!=null) && (instrumentosPublico.size()>0) ) {
				instrumentoPublico = (InstrumentoPublico)instrumentosPublico.get(0);
				instrumentosPublico.remove(0);
			}
			else
				instrumentosPublico = new ArrayList();
			if (instrumentoPublico==null)
				instrumentoPublico = new InstrumentoPublico();
									        
	        
	        escrituraPublica = solicitudInscripcion.getEscrituraPublica();
			if (escrituraPublica==null)
				escrituraPublica = new EscrituraPublica();
			
			String indicador = null;		        
			
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* INSTRUMENTO PUBLICO **************************************************/
					if (name.equals("txtSolFecha")) {
						if ( (value!=null) && (!value.equals("")) )
							/** fecha **/
							instrumentoPublico.setFecha(value.substring(6,10)+value.substring(3,5)+value.substring(0,2));
					}
					else if (name.equals("txtSolLugar"))
						/** lugar **/
						instrumentoPublico.setLugar(value);
					else if (name.equals("txtSolOtros"))
						/** otros **/
						instrumentoPublico.setOtros(value);
                	/*********************************************************************************************************/
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
	                    //in.close();
                    }
                }
            }

			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("022"); //codigo de la boleta notarial

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("BOLETA NOTARIAL");

			/** secuencia **/
			instrumentoPublico.setSecuencia(new Long("1"));

			instrumentosPublico.add(instrumentoPublico);
				
			solicitudInscripcion.setInstrumentoPublico(instrumentosPublico);
					
			System.out.println("INSTRUMENTO PUBLICO");
			System.out.println("codigoTipoInstrumento::"+instrumentoPublico.getCodigoTipoInstrumento()+"::");
			System.out.println("descripcionTipoInstrumento::"+instrumentoPublico.getDescripcionTipoInstrumento()+"::");			
			System.out.println("fecha::"+instrumentoPublico.getFecha()+"::");
			System.out.println("lugar::"+instrumentoPublico.getLugar()+"::");			
			System.out.println("otros::"+instrumentoPublico.getOtros()+"::");
			System.out.println("secuencia::"+instrumentoPublico.getSecuencia()+"::");
			/******************************************************************************************************/
			
			solicitudInscripcion.setEscrituraPublica(escrituraPublica);
			
			System.out.println("BOLETA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/
			
			// La primera vez seteo en session los combos de nacionalidad y estado civil 
			if (session.getAttribute("arrNacionalidad")==null)
				session.setAttribute("arrNacionalidad", Tarea.getComboPaises(dconn));
			if (session.getAttribute("arrEstadoCivil")==null)
				session.setAttribute("arrEstadoCivil", (ArrayList)Tarea.getComboEstadoCiviles(dconn));
			
			// Obtengo los Departamentos
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
	
			// Direccionamos a la pagina de detalle de la partida
			response.setStyle("detallePartida");
	
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

	protected ControllerResponse runRegresarABloqueoInmuebleState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		try {

			init(request);
			validarSesion(request);

			// Direccionamos a la pagina de Datos de Bloqueo de Inmueble	
			response.setStyle("datosBloqueo");

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
	
	protected ControllerResponse runAgregarPartidaState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		String tipoPartida = null;						
		Partida partida = null;
		ArrayList listaPartidas = null;
		int ind = 0;
		String indice = null;		
		String oficina = null;
		
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
	
			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");

			tipoPartida = (String)request.getParameter("rbPartida");
		
			indice = request.getParameter("hidIndiceMod2");
			oficina = request.getParameter("hidOfic");
				
			/************************ DATOS DE LA PARTIDA*****************************************/
			if (tipoPartida.equals("numeroPartida")) {
				listaPartidas = solicitudInscripcion.getPartidas();
				if (listaPartidas==null)
					listaPartidas = new ArrayList();
				/********* por si viene de modificacion ********/
				if ( (indice!=null) && (!indice.equals("")) ) {
					System.out.println("modificacion-indice::"+indice);
					ind = Integer.parseInt(indice);
					partida = (Partida)listaPartidas.get(ind);
				}
				/***********************************************/
				else {
					System.out.println("nueva partida");
					partida = new Partida();
				}
				/** Numero Partida **/
				partida.setNumeroPartida(request.getParameter("txtPartida"));
				/** Oficina Registral **/
				partida.setCodigoZonaRegistral(request.getParameter("cboOficinas").substring(0,2));
				partida.setCodigoOficinaRegistral(request.getParameter("cboOficinas").substring(3,5));
				partida.setDescripcionOficinaRegistral(oficina);
				/** Ficha **/
				partida.setFicha(request.getParameter("txtFicha"));
				/** Tomo **/
				partida.setTomo(request.getParameter("txtTomo"));
				/** Foja **/
				partida.setFoja(request.getParameter("txtFoja"));
				/** Distrito **/
				partida.setDescripcionDistritoPartida(request.getParameter("txtDistrito"));
				/** apellidoMaternoPN **/
				
				/********* por si viene de modificacion ********/
				if ( (indice!=null) && (indice.equals("")) ) 
					listaPartidas.add(partida);
	
				solicitudInscripcion.setPartidas(listaPartidas);	

				System.out.println("PARTIDAS");
				System.out.println("numeroPartida::"+partida.getNumeroPartida()+"::");			
				System.out.println("codigoZonaRegistral::"+partida.getCodigoZonaRegistral()+"::");							
				System.out.println("codigoOficinaRegistral::"+partida.getCodigoOficinaRegistral()+"::");							
				System.out.println("descripcionOficinaRegistral::"+partida.getDescripcionOficinaRegistral()+"::");							
				System.out.println("ficha::"+partida.getFicha()+"::");							
				System.out.println("tomo::"+partida.getTomo()+"::");							
				System.out.println("foja::"+partida.getFoja()+"::");							
				System.out.println("distrito::"+partida.getDescripcionDistritoPartida()+"::");							
				
			}
			/*********************************************************************************************************/
							
			// Direccionamos a la pagina de los Datos de Bloqueo de Inmueble
			response.setStyle("datosBloqueo");
	
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
	
	protected ControllerResponse runBorrarPartidaState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		ArrayList listaParticipantesPN2 = null;
		ArrayList listaParticipantesPJ2 = null;
		ArrayList listaPartidas = null;
		ArrayList listaPartidas2 = null;
		SolicitudInscripcion solicitudInscripcion = null;
		InstrumentoPublico instrumentoPublico = null;		
		EscrituraPublica escrituraPublica = null;
		
		int size1 = 0;
		int size2 = 0;
		int size3 = 0;
		int sizeLista1 = 0;
		int sizeLista2 = 0;
		int sizeLista3 = 0;
		
		PersonaNatural personaNatural = null;
		PersonaJuridica personaJuridica = null;	
		Partida partida = null;
				
		try {	
					
			init(request);
			validarSesion(request);

			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");

			// Upload
			DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(req);
            Iterator it = items.iterator();
	        FileItem item = null;
	        String name, value;
	        
	        // Inicializamos Beans
	        personaJuridica = solicitudInscripcion.getPersonaJuridica();
			
			
			ArrayList instrumentosPublico = solicitudInscripcion.getInstrumentoPublico();
			if ( (instrumentosPublico!=null) && (instrumentosPublico.size()>0) ) {
				instrumentoPublico = (InstrumentoPublico)instrumentosPublico.get(0);
				instrumentosPublico.remove(0);
			}
			else
				instrumentosPublico = new ArrayList();
			if (instrumentoPublico==null)
				instrumentoPublico = new InstrumentoPublico();
									        
	        
	        escrituraPublica = solicitudInscripcion.getEscrituraPublica();
			if (escrituraPublica==null)
				escrituraPublica = new EscrituraPublica();
			

			//Vector vIndicesPN = new Vector();
			//Vector vIndicesPJ = new Vector();
			Vector vIndicesPartida = new Vector();
			
			String indicador = null;		        
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* DATOS DE LA PERSONA JURIDICA A CONSTITUIR  ****************************/
                   	if (name.equals("txtSolRazonSocial"))
						/** razonSocialPersonaJuridica **/
						personaJuridica.setRazonSocial(value);
					else if (name.equals("txtSolSiglas"))
						/** siglas **/
						personaJuridica.setSiglas(value);
                   	else if (name.equals("cboTipoSociedad")) {
						/** codigoTipoSociedadAonima **/
						personaJuridica.setCodigoTipoSociedadAnonima(value);
						if (value.equals("A"))
						/** descripcionTipoSociedadAnonima **/
							personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
						else
						/** descripcionTipoSociedadAnonima **/
							personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
                   	}
					else if (name.equals("txtSolRequiereRUC")) {
						/** indicadorRUC **/
						indicador = value;
					}
					
					/******************************* INSTRUMENTO PUBLICO **************************************************/
					else if (name.equals("txtSolFecha")) {
						if ( (value!=null) && (!value.equals("")) )
							/** fecha **/
							instrumentoPublico.setFecha(value.substring(6,10)+value.substring(3,5)+value.substring(0,2));
					}
					else if (name.equals("txtSolLugar"))
						/** lugar **/
						instrumentoPublico.setLugar(value);
					else if (name.equals("txtSolOtros"))
						/** otros **/
						instrumentoPublico.setOtros(value);
                	/*********************************************************************************************************/
                	else if (name.equals("indicesListaPartida")) {
                		vIndicesPartida.add(value);
                	}
                	
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
	                    //in.close();
                    }
                }
            }

									
			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("022");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("BOLETA NOTARIAL");

			/** secuencia **/
			instrumentoPublico.setSecuencia(new Long("1"));

			instrumentosPublico.add(instrumentoPublico);
				
			solicitudInscripcion.setInstrumentoPublico(instrumentosPublico);
					
			System.out.println("INSTRUMENTO PUBLICO");
			System.out.println("codigoTipoInstrumento::"+instrumentoPublico.getCodigoTipoInstrumento()+"::");
			System.out.println("descripcionTipoInstrumento::"+instrumentoPublico.getDescripcionTipoInstrumento()+"::");			
			System.out.println("fecha::"+instrumentoPublico.getFecha()+"::");
			System.out.println("lugar::"+instrumentoPublico.getLugar()+"::");			
			System.out.println("otros::"+instrumentoPublico.getOtros()+"::");
			System.out.println("secuencia::"+instrumentoPublico.getSecuencia()+"::");
			/******************************************************************************************************/
			
			solicitudInscripcion.setEscrituraPublica(escrituraPublica);
			
			System.out.println("BOLETA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/


			//Borrar				
			String[] indicesPartidas = new String[vIndicesPartida.size()];
			vIndicesPartida.copyInto(indicesPartidas);

			if (indicesPartidas!=null) 
				size3 = indicesPartidas.length;
			
			listaPartidas = solicitudInscripcion.getPartidas();
			
			if (listaPartidas!=null)
				sizeLista3 = listaPartidas.size();
				


			listaPartidas2 = new ArrayList();
			boolean res = false;

			for (int i=0; i<sizeLista3; i++) {
				res = false;
				partida = (Partida)listaPartidas.get(i);
				for (int j=0; j<size3; j++) {
					if (i == Integer.parseInt(indicesPartidas[j]) )
						res = true;
				}
				if (res==false)
					listaPartidas2.add(partida);
				
			}			
			solicitudInscripcion.setPartidas(listaPartidas2);
			

			// Direccionamos a la pagina de Datos de Bloqueo de Inmueble	
			response.setStyle("datosBloqueo");
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

	
	protected ControllerResponse runObtenerParticipanteState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		PersonaNatural personaNatural = null;
		PersonaJuridica personaJuridica = null;
		InstrumentoPublico instrumentoPublico = null;		
		EscrituraPublica escrituraPublica = null;
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


			// Upload
			DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(req);
            Iterator it = items.iterator();
	        FileItem item = null;
	        String name, value;
	        
	        // Inicializamos Beans
	        personaJuridica = solicitudInscripcion.getPersonaJuridica();
			
			ArrayList instrumentosPublico = solicitudInscripcion.getInstrumentoPublico();
			if ( (instrumentosPublico!=null) && (instrumentosPublico.size()>0) ) {
				instrumentoPublico = (InstrumentoPublico)instrumentosPublico.get(0);
				instrumentosPublico.remove(0);
			}
			else
				instrumentosPublico = new ArrayList();
			if (instrumentoPublico==null)
				instrumentoPublico = new InstrumentoPublico();
									        
	        
	        escrituraPublica = solicitudInscripcion.getEscrituraPublica();
			if (escrituraPublica==null)
				escrituraPublica = new EscrituraPublica();
			

			String indice = null;
			String tipoPersona = null;
			String indicador = null;
				        
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* DATOS DE LA PERSONA JURIDICA A CONSTITUIR  ****************************/
                   	if (name.equals("txtSolRazonSocial"))
						/** razonSocialPersonaJuridica **/
						personaJuridica.setRazonSocial(value);
					else if (name.equals("txtSolSiglas"))
						/** siglas **/
						personaJuridica.setSiglas(value);
                   	//else if (name.equals("cboTipoSociedad")) {
						/** codigoTipoSociedadAonima **/
						//personaJuridica.setCodigoTipoSociedadAnonima(value);
						//if (value.equals("A"))
						/** descripcionTipoSociedadAnonima **/
							//personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
						//else
						/** descripcionTipoSociedadAnonima **/
							//personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
                   //	}
				//	else if (name.equals("txtSolRequiereRUC")) {
						/** indicadorRUC **/
						//indicador = value;
					//}
					/*********************************************************************************************************/
					
					
					/******************************* INSTRUMENTO PUBLICO **************************************************/
					else if (name.equals("txtSolFecha")) {
						if ( (value!=null) && (!value.equals("")) )
							/** fecha **/
							instrumentoPublico.setFecha(value.substring(6,10)+value.substring(3,5)+value.substring(0,2));
					}
					else if (name.equals("txtSolLugar"))
						/** lugar **/
						instrumentoPublico.setLugar(value);
					else if (name.equals("txtSolOtros"))
						/** otros **/
						instrumentoPublico.setOtros(value);
                	/*********************************************************************************************************/
                	else if (name.equals("hidIndiceMod"))
                		indice = value;
                	else if (name.equals("hidTipoPersona"))	
                		tipoPersona = value;
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
	                    //in.close();
                    }
                }
            }

			//if (indicador==null)
			//	personaJuridica.setIndicadorRUC("NO");
			//else
				//personaJuridica.setIndicadorRUC(indicador);
				
			solicitudInscripcion.setPersonaJuridica(personaJuridica);

					
			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("022");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("BOLETA NOTARIAL");

			/** secuencia **/
			instrumentoPublico.setSecuencia(new Long("1"));

			instrumentosPublico.add(instrumentoPublico);
				
			solicitudInscripcion.setInstrumentoPublico(instrumentosPublico);
					
			System.out.println("INSTRUMENTO PUBLICO");
			System.out.println("codigoTipoInstrumento::"+instrumentoPublico.getCodigoTipoInstrumento()+"::");
			System.out.println("descripcionTipoInstrumento::"+instrumentoPublico.getDescripcionTipoInstrumento()+"::");			
			System.out.println("fecha::"+instrumentoPublico.getFecha()+"::");
			System.out.println("lugar::"+instrumentoPublico.getLugar()+"::");			
			System.out.println("otros::"+instrumentoPublico.getOtros()+"::");
			System.out.println("secuencia::"+instrumentoPublico.getSecuencia()+"::");
			/******************************************************************************************************/
			
			solicitudInscripcion.setEscrituraPublica(escrituraPublica);
			
			System.out.println("BOLETA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/


			System.out.println("indice!!!!::"+indice);
			// Obtenemos el indice que fue seleccionado para modificacion
			req.setAttribute("indice",indice);
			
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
				// Si selecciono indicardorRUC
				req.setAttribute("fecNacim",personaNatural.getFechaNacimiento());
				req.setAttribute("codCargo",personaNatural.getCodigoCargoOcupacion());
				req.setAttribute("fecCargo",personaNatural.getFechaCargo());

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
	
	protected ControllerResponse runObtenerPartidaState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		ArrayList listaPartidas = null;
		ArrayList instrumentosPublico = null;
		Partida partida = null;
		InstrumentoPublico instrumentoPublico = null;		
		EscrituraPublica escrituraPublica = null;
		PersonaJuridica personaJuridica = null;
		PersonaNatural personaNatural = null;
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


			// Upload
			DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(req);
            Iterator it = items.iterator();
	        FileItem item = null;
	        String name, value;
	        
	        // Inicializamos Beans
	        //personaJuridica = solicitudInscripcion.getPersonaJuridica();
						
			
			if ( (instrumentosPublico!=null) && (instrumentosPublico.size()>0) ) {
				instrumentoPublico = (InstrumentoPublico)instrumentosPublico.get(0);
				instrumentosPublico.remove(0);
			}
			else
				instrumentosPublico = new ArrayList();
			if (instrumentoPublico==null)
				instrumentoPublico = new InstrumentoPublico();
									        
	        
	        escrituraPublica = solicitudInscripcion.getEscrituraPublica();
			if (escrituraPublica==null)
				escrituraPublica = new EscrituraPublica();
			

			String indice = null;
			String tipoPartida = null;
			String indicador = null;
				        
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* DATOS DE LA PERSONA JURIDICA A CONSTITUIR  ****************************/
                   	if (name.equals("txtSolRazonSocial"))
						/** razonSocialPersonaJuridica **/
						personaJuridica.setRazonSocial(value);
					else if (name.equals("txtSolSiglas"))
						/** siglas **/
						personaJuridica.setSiglas(value);
                   	//else if (name.equals("cboTipoSociedad")) {
						/** codigoTipoSociedadAonima **/
						//personaJuridica.setCodigoTipoSociedadAnonima(value);
						//if (value.equals("A"))
						/** descripcionTipoSociedadAnonima **/
							//personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
						//else
						/** descripcionTipoSociedadAnonima **/
							//personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
                   //	}
				//	else if (name.equals("txtSolRequiereRUC")) {
						/** indicadorRUC **/
						//indicador = value;
					//}
					/*********************************************************************************************************/
					
					
					/******************************* INSTRUMENTO PUBLICO **************************************************/
					else if (name.equals("txtSolFecha")) {
						if ( (value!=null) && (!value.equals("")) )
							/** fecha **/
							instrumentoPublico.setFecha(value.substring(6,10)+value.substring(3,5)+value.substring(0,2));
					}
					else if (name.equals("txtSolLugar"))
						/** lugar **/
						instrumentoPublico.setLugar(value);
					else if (name.equals("txtSolOtros"))
						/** otros **/
						instrumentoPublico.setOtros(value);
                	/*********************************************************************************************************/
                	else if (name.equals("hidIndiceMod2"))
                		indice = value;
                	
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
	                    //in.close();
                    }
                }
            }

							
			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("022");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("BOLETA NOTARIAL");

			/** secuencia **/
			instrumentoPublico.setSecuencia(new Long("1"));

			instrumentosPublico.add(instrumentoPublico);
				
			solicitudInscripcion.setInstrumentoPublico(instrumentosPublico);
					
			System.out.println("INSTRUMENTO PUBLICO");
			System.out.println("codigoTipoInstrumento::"+instrumentoPublico.getCodigoTipoInstrumento()+"::");
			System.out.println("descripcionTipoInstrumento::"+instrumentoPublico.getDescripcionTipoInstrumento()+"::");			
			System.out.println("fecha::"+instrumentoPublico.getFecha()+"::");
			System.out.println("lugar::"+instrumentoPublico.getLugar()+"::");			
			System.out.println("otros::"+instrumentoPublico.getOtros()+"::");
			System.out.println("secuencia::"+instrumentoPublico.getSecuencia()+"::");
			/******************************************************************************************************/
			
			solicitudInscripcion.setEscrituraPublica(escrituraPublica);
			
			System.out.println("BOLETA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/


			System.out.println("indice!!!!::"+indice);
			// Obtenemos el indice que fue seleccionado para modificacion
			req.setAttribute("indice",indice);
			
			//if (tipoPartida.equals("numeroPartida")) {
				listaPartidas = solicitudInscripcion.getPartidas();
				partida = (Partida)listaPartidas.get(Integer.parseInt(indice));

				//req.setAttribute("metodoPartida",tipoPartida);
				req.setAttribute("numeroPartida",partida.getNumeroPartida());
				req.setAttribute("regPubId",partida.getCodigoZonaRegistral());
				req.setAttribute("oficRegId",partida.getCodigoOficinaRegistral());
				req.setAttribute("oficinaRegistral",partida.getDescripcionOficinaRegistral());
				req.setAttribute("Ficha", partida.getFicha());
				req.setAttribute("Tomo",partida.getTomo());
				req.setAttribute("Foja",partida.getFoja());	
				req.setAttribute("Distrito",partida.getDescripcionDistritoPartida());					
				
				
			//}
			response.setStyle("detallePartida");

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

	protected ControllerResponse runObtenerResumenAntesPagoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		ArrayList listaPartidas = null;
		ArrayList instrumentosPublico = null;
		Partida partida = null;
		InstrumentoPublico instrumentoPublico = null;		
		EscrituraPublica escrituraPublica = null;
		PersonaJuridica personaJuridica = null;
		PersonaNatural personaNatural = null;
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


			// Upload
			DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(req);
            Iterator it = items.iterator();
	        FileItem item = null;
	        String name, value;
	        
	        // Inicializamos Beans
	        //personaJuridica = solicitudInscripcion.getPersonaJuridica();
						
			
			if ( (instrumentosPublico!=null) && (instrumentosPublico.size()>0) ) {
				instrumentoPublico = (InstrumentoPublico)instrumentosPublico.get(0);
				instrumentosPublico.remove(0);
			}
			else
				instrumentosPublico = new ArrayList();
			if (instrumentoPublico==null)
				instrumentoPublico = new InstrumentoPublico();
									        
	        
	        escrituraPublica = solicitudInscripcion.getEscrituraPublica();
			if (escrituraPublica==null)
				escrituraPublica = new EscrituraPublica();
			

			String indice = null;
			String tipoPartida = null;
			String indicador = null;
				        
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* DATOS DE LA PERSONA JURIDICA A CONSTITUIR  ****************************/
                   	if (name.equals("txtSolRazonSocial"))
						/** razonSocialPersonaJuridica **/
						personaJuridica.setRazonSocial(value);
					else if (name.equals("txtSolSiglas"))
						/** siglas **/
						personaJuridica.setSiglas(value);
                   	//else if (name.equals("cboTipoSociedad")) {
						/** codigoTipoSociedadAonima **/
						//personaJuridica.setCodigoTipoSociedadAnonima(value);
						//if (value.equals("A"))
						/** descripcionTipoSociedadAnonima **/
							//personaJuridica.setDescripcionTipoSociedadAnonima("ABIERTA");
						//else
						/** descripcionTipoSociedadAnonima **/
							//personaJuridica.setDescripcionTipoSociedadAnonima("CERRADA");
                   //	}
				//	else if (name.equals("txtSolRequiereRUC")) {
						/** indicadorRUC **/
						//indicador = value;
					//}
					/*********************************************************************************************************/
					
					
					/******************************* INSTRUMENTO PUBLICO **************************************************/
					else if (name.equals("txtSolFecha")) {
						if ( (value!=null) && (!value.equals("")) )
							/** fecha **/
							instrumentoPublico.setFecha(value.substring(6,10)+value.substring(3,5)+value.substring(0,2));
					}
					else if (name.equals("txtSolLugar"))
						/** lugar **/
						instrumentoPublico.setLugar(value);
					else if (name.equals("txtSolOtros"))
						/** otros **/
						instrumentoPublico.setOtros(value);
                	/*********************************************************************************************************/
                	else if (name.equals("hidIndiceMod2"))
                		indice = value;
                	
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
	                    //in.close();
                    }
                }
            }

							
			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("022");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("BOLETA NOTARIAL");

			/** secuencia **/
			instrumentoPublico.setSecuencia(new Long("1"));

			instrumentosPublico.add(instrumentoPublico);
				
			solicitudInscripcion.setInstrumentoPublico(instrumentosPublico);
					
			System.out.println("INSTRUMENTO PUBLICO");
			System.out.println("codigoTipoInstrumento::"+instrumentoPublico.getCodigoTipoInstrumento()+"::");
			System.out.println("descripcionTipoInstrumento::"+instrumentoPublico.getDescripcionTipoInstrumento()+"::");			
			System.out.println("fecha::"+instrumentoPublico.getFecha()+"::");
			System.out.println("lugar::"+instrumentoPublico.getLugar()+"::");			
			System.out.println("otros::"+instrumentoPublico.getOtros()+"::");
			System.out.println("secuencia::"+instrumentoPublico.getSecuencia()+"::");
			/******************************************************************************************************/
			
			solicitudInscripcion.setEscrituraPublica(escrituraPublica);
			
			System.out.println("BOLETA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/
	
			/*********************** CALCULAMOS LA TARIFA *********************************/
			int codServicio= gob.pe.sunarp.extranet.util.Constantes.COD_SERVICIO_BLOQUEOINMUEBLE;
			int codGLA= gob.pe.sunarp.extranet.util.Constantes.COD_GLA_BLOQUEOINMUEBLE;
			
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

			req.setAttribute("fechaActual", getFechaActual());
			
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
protected ControllerResponse runLlenaPartidaState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		SolicitudInscripcion solicitudInscripcion = null;
		Partida partida = null;
		ArrayList listaPartidas = null;
		String indice = null;
		String refnumpart = null;
		
				
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
       
			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
					
			// Obteniendo Zona y Oficina Registral
			System.out.println("Consultando Partida");
			DboPartida dboPartida = new  DboPartida(dconn);
	        dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID + "|" +
										   DboPartida.CAMPO_OFIC_REG_ID + "|" +
										   DboPartida.CAMPO_REFNUM_PART + "|" +
										   DboPartida.CAMPO_NUM_PARTIDA
										   );
			
			dboPartida.setField(DboPartida.CAMPO_NUM_PARTIDA, request.getParameter("txtPartida"));		
			
			if(request.getParameter("txtPartida")!=null){
			
			String zona = null;
			String oficina = null;
			String numPartida = null;	
			String regPubId = null;
			
			if (dboPartida.find() == true) {
				oficina = dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID);
				numPartida = dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA);
				refnumpart = dboPartida.getField(DboPartida.CAMPO_REFNUM_PART);
				regPubId = dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID);
				System.out.println("oficina::"+oficina);
				System.out.println("numPartida::"+numPartida);
				System.out.println("refNumPart::"+refnumpart);
				System.out.println("regPubId::"+regPubId);
				
			//Obteniendo datos de tabla IND_PRTC
			System.out.println("Consultando FICHA"); 
			DboFicha dboFicha = new DboFicha(dconn);
			dboFicha.setFieldsToRetrieve(DboFicha.CAMPO_REFNUM_PART + "|" +
										 DboFicha.CAMPO_FICHA
											);

			dboFicha.setField(DboFicha.CAMPO_REFNUM_PART,refnumpart);
			String ficha = null;
			
			
			if (dboFicha.find() == true) {
				ficha = dboFicha.getField(DboFicha.CAMPO_FICHA);
				System.out.println("ficha::"+ficha);
								
			}
			
			//Obteniendo datos de tabla TOMO_FOLIO
			System.out.println("Consultando TOMO_FOLIO");
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			dboTomoFolio.setFieldsToRetrieve(DboTomoFolio.CAMPO_REFNUM_PART + "|" +
											 DboTomoFolio.CAMPO_NU_FOJA + "|" +
											 DboTomoFolio.CAMPO_NU_TOMO
											);
			dboTomoFolio.setField(DboTomoFolio.CAMPO_REFNUM_PART,refnumpart);
			String tomo = null;
			String foja = null;
			if (dboTomoFolio.find() == true) {
				tomo = dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO);
				foja = dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA);
				System.out.println("tomo::"+tomo);
				System.out.println("foja::"+foja);
								
			}
			
			//Obteniendo datos de tabla REG_PREDIOS
			System.out.println("Consultando REG_PREDIOS");
			DboRegPredios dboRegPredios = new DboRegPredios(dconn);
			dboRegPredios.setFieldsToRetrieve(DboRegPredios.CAMPO_DIST_ID + "|" +
											  DboRegPredios.CAMPO_REFNUM_PART + "|" +
											  DboRegPredios.CAMPO_PAIS_ID + "|" +
											  DboRegPredios.CAMPO_PROV_ID + "|" +
											  DboRegPredios.CAMPO_DPTO_ID   
											  );
			dboRegPredios.setField(DboRegPredios.CAMPO_REFNUM_PART,refnumpart);
			String distritoId = null;
			String provinciaId = null;
			String departamentoId = null;
			String paisId = null;
			
			if (dboRegPredios.find() == true) {
				distritoId = dboRegPredios.getField(DboRegPredios.CAMPO_DIST_ID);
				provinciaId = dboRegPredios.getField(DboRegPredios.CAMPO_PROV_ID);
				departamentoId = dboRegPredios.getField(DboRegPredios.CAMPO_DPTO_ID);
				paisId = dboRegPredios.getField(DboRegPredios.CAMPO_PAIS_ID);
				
				
				System.out.println("distritoId::"+distritoId);
				System.out.println("provinciaId::"+provinciaId);
				System.out.println("departamentoId::"+departamentoId);
				System.out.println("paisId::"+paisId);
								
			}
			
			//Obteniendo datos de tabla TM_DISTRITO
			System.out.println("Consultando TM_DISTRITO");
			DboTmDistrito dboTmDistrito = new DboTmDistrito(dconn);
			dboTmDistrito.setFieldsToRetrieve(DboTmDistrito.CAMPO_NOMBRE
											  );
			dboTmDistrito.setField(DboTmDistrito.CAMPO_PAIS_ID,paisId);
			dboTmDistrito.setField(DboTmDistrito.CAMPO_PROV_ID,provinciaId);
			dboTmDistrito.setField(DboTmDistrito.CAMPO_DPTO_ID,paisId);
			
			
			String nombreDistrito = null;
			
			if (dboTmDistrito.find() == true) {
				nombreDistrito = dboTmDistrito.getField(DboTmDistrito.CAMPO_NOMBRE);
								
				System.out.println("nombreDistrito::"+nombreDistrito);
										
			}
			
			// Obtenemos el bean de solicitud de inscripcion de session
			//solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
			//indice = request.getParameter("hidIndiceMod2");
			//listaPartidas = solicitudInscripcion.getPartidas();	
			
			//colocando los valores recuperado en el bean
			//partida.setNumeroPartida(request.getParameter("txtPartida"));
			//partida.setCodigoOficinaRegistral(oficina);
			//partida.setFicha(ficha);
			//partida.setTomo(tomo);
			//partida.setTomo(foja);
			//partida.setTomo(nombreDistrito);
			
			req.setAttribute("numeroPartida",numPartida);
			req.setAttribute("regPubId",regPubId);
			req.setAttribute("oficRegId",oficina);
			req.setAttribute("Ficha",ficha);
			req.setAttribute("Tomo",tomo);
			req.setAttribute("Foja",foja);
			req.setAttribute("Distrito",nombreDistrito);
			
					}
			else{
			System.out.println("Numero de Partida en blanco");
				}
			}
			
			
			//para direccionar en el xml a la pagina
			response.setStyle("detallePartida");
	
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
protected ControllerResponse runLlenaPartidaRangoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		SolicitudInscripcion solicitudInscripcion = null;
		ArrayList listaPartidas = null;
		ArrayList listaPartidas2 = null;
		String partidaDesde = null;
		String partidaHasta = null;
		String zona = null;
		String oficina = null;
		String numPartida = null;	
		String regPubId = null;
		String refnumpart = null;
		String indice = null;
		String oficina2 = null;
				
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
			listaPartidas = solicitudInscripcion.getPartidas();
			
			if(listaPartidas==null){
			listaPartidas = new ArrayList();
			}
			
			// Obteniendo indices de partidas
			indice = request.getParameter("hidIndiceMod2");
			oficina2 = request.getParameter("hidOfic");
					
			// Obteniendo el rango de partidas desde el formulario
			partidaDesde = request.getParameter("partidaDesde");
			partidaHasta = request.getParameter("partidaHasta");
				
			// Consultando PARTIDA
			System.out.println("Consultando Partida");
			DboPartida dboPartida = new  DboPartida(dconn);
	        dboPartida.setAppendWhereClause("NUM_PARTIDA >= " + "'"+partidaDesde+"'" + " AND NUM_PARTIDA <= " + "'"+partidaHasta+"'");
	        dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID + "|" +
										   DboPartida.CAMPO_OFIC_REG_ID + "|" +
										   DboPartida.CAMPO_REFNUM_PART + "|" +
										   DboPartida.CAMPO_NUM_PARTIDA
										   );
			dboPartida.setField(DboPartida.CAMPO_ESTADO,"1");
			
			listaPartidas2 = dboPartida.searchAndRetrieveList();
			
			
			for (int i = 0; i < listaPartidas2.size(); i++) {
				DboPartida dboPartida2 = (DboPartida)listaPartidas2.get(i);
				
				oficina = dboPartida2.getField(DboPartida.CAMPO_OFIC_REG_ID);
				numPartida = dboPartida2.getField(DboPartida.CAMPO_NUM_PARTIDA);
				refnumpart = dboPartida2.getField(DboPartida.CAMPO_REFNUM_PART);
				regPubId = dboPartida2.getField(DboPartida.CAMPO_REG_PUB_ID);		
				
				//Obteniendo datos de tabla IND_PRTC
				System.out.println("Consultando FICHA"); 
				DboFicha dboFicha = new DboFicha(dconn);
				dboFicha.setFieldsToRetrieve(DboFicha.CAMPO_REFNUM_PART + "|" +
											 DboFicha.CAMPO_FICHA
												);
	
				dboFicha.setField(DboFicha.CAMPO_REFNUM_PART,refnumpart);
				String ficha = null;
				
				
				if (dboFicha.find() == true) {
					ficha = dboFicha.getField(DboFicha.CAMPO_FICHA);
					System.out.println("ficha::"+ficha);
									
				}
				
				//Obteniendo datos de tabla TOMO_FOLIO
				System.out.println("Consultando TOMO_FOLIO");
				DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
				dboTomoFolio.setFieldsToRetrieve(DboTomoFolio.CAMPO_REFNUM_PART + "|" +
												 DboTomoFolio.CAMPO_NU_FOJA + "|" +
												 DboTomoFolio.CAMPO_NU_TOMO
												);
				dboTomoFolio.setField(DboTomoFolio.CAMPO_REFNUM_PART,refnumpart);
				String tomo = null;
				String foja = null;
				if (dboTomoFolio.find() == true) {
					tomo = dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO);
					foja = dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA);
					System.out.println("tomo::"+tomo);
					System.out.println("foja::"+foja);
									
				}
				
				//Obteniendo datos de tabla REG_PREDIOS
				System.out.println("Consultando REG_PREDIOS");
				DboRegPredios dboRegPredios = new DboRegPredios(dconn);
				dboRegPredios.setFieldsToRetrieve(DboRegPredios.CAMPO_DIST_ID + "|" +
												  DboRegPredios.CAMPO_REFNUM_PART + "|" +
												  DboRegPredios.CAMPO_PAIS_ID + "|" +
												  DboRegPredios.CAMPO_PROV_ID + "|" +
												  DboRegPredios.CAMPO_DPTO_ID   
												  );
				dboRegPredios.setField(DboRegPredios.CAMPO_REFNUM_PART,refnumpart);
				String distritoId = null;
				String provinciaId = null;
				String departamentoId = null;
				String paisId = null;
				
				if (dboRegPredios.find() == true) {
					distritoId = dboRegPredios.getField(DboRegPredios.CAMPO_DIST_ID);
					provinciaId = dboRegPredios.getField(DboRegPredios.CAMPO_PROV_ID);
					departamentoId = dboRegPredios.getField(DboRegPredios.CAMPO_DPTO_ID);
					paisId = dboRegPredios.getField(DboRegPredios.CAMPO_PAIS_ID);
					
					
					System.out.println("distritoId::"+distritoId);
					System.out.println("provinciaId::"+provinciaId);
					System.out.println("departamentoId::"+departamentoId);
					System.out.println("paisId::"+paisId);
									
				}
				
				// Obteniendo datos de tabla TM_DISTRITO
				System.out.println("Consultando TM_DISTRITO");
				DboTmDistrito dboTmDistrito = new DboTmDistrito(dconn);
				dboTmDistrito.setFieldsToRetrieve(DboTmDistrito.CAMPO_NOMBRE
												  );
				dboTmDistrito.setField(DboTmDistrito.CAMPO_PAIS_ID,paisId);
				dboTmDistrito.setField(DboTmDistrito.CAMPO_PROV_ID,provinciaId);
				dboTmDistrito.setField(DboTmDistrito.CAMPO_DPTO_ID,paisId);
				
				
				String nombreDistrito = null;
				
				if (dboTmDistrito.find() == true) {
					nombreDistrito = dboTmDistrito.getField(DboTmDistrito.CAMPO_NOMBRE);
									
					System.out.println("nombreDistrito::"+nombreDistrito);
											
				}
				
				// Obteniendo datos de tabla OFIC_REGISTRAL
				System.out.println("Consultando OFIC_REGISTRAL");
				DboOficRegistral dboOficRegistral = new DboOficRegistral(dconn);
				dboOficRegistral.setFieldsToRetrieve(DboOficRegistral.CAMPO_NOMBRE);
				dboOficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficina);	
				String descOficina = null;
				if (dboOficRegistral.find() == true) {
					descOficina = dboOficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE);
					System.out.println("descOficina::"+descOficina);
											
				}								
				
				
						
					// Creando una partida temporal
					System.out.println("Nueva Partida");
					// creando una partida temporal
					Partida partida = new Partida();
					//seteando los valores en la partida
					/** Numero Partida **/
					partida.setNumeroPartida(numPartida);
					/** Oficina Registral **/
					partida.setCodigoZonaRegistral(regPubId);
					partida.setCodigoOficinaRegistral(oficina);
					partida.setDescripcionOficinaRegistral(descOficina);
					/** Ficha **/
					partida.setFicha(ficha);
					/** Tomo **/
					partida.setTomo(tomo);
					/** Foja **/
					partida.setFoja(foja);
					/** Distrito **/
					partida.setDescripcionDistritoPartida(nombreDistrito);
				
					//agregando esta partida a la lista de partidas
					listaPartidas.add(partida);
			
	
			}
			
			solicitudInscripcion.setPartidas(listaPartidas);
					 
			//para direccionar en el xml a la pagina
			response.setStyle("datosBloqueo");
			
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

}





