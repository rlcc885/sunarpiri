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

public class TransferenciaVehicularController extends ControllerExtension {


	public TransferenciaVehicularController() {
		super();
		addState(new State("obtenerDatosTransferencia", "Datos de Transferencia Vehicular"));
		addState(new State("obtenerNuevoVehiculo", "Nuevo Vehiculo"));
		addState(new State("obtenerNuevoParticipante", "Nuevo Participante"));
		addState(new State("borrarParticipante", "Borrar Participante"));
		addState(new State("borrarVehiculo", "Borrar Vehiculo"));
		addState(new State("agregarVehiculo", "Agregar Vehiculo"));
		addState(new State("agregarParticipante", "Agregar Participante"));
		addState(new State("obtenerProvincia", "Obtiene Provincia"));
		addState(new State("obtenerDistrito", "Obtiene Distrito"));
		addState(new State("regresarATransVehicular", "Regresar a Datos de detalle de Vehiculo"));
		addState(new State("regresarATransVehicular2", "Regresar a Datos de TransferenciaVehicular"));
		addState(new State("obtenerParticipante", "Modificar Participante"));
		addState(new State("obtenerVehiculo", "Modificar Vehiculo"));
		addState(new State("obtenerResumenAntesPago", "Obtener Resumen Antes del Pago"));
		addState(new State("procesarSolicitud", "Procesar Solicitud"));
		addState(new State("llenaVehiculo", "Completar Datos de Serie, Motor"));
		setInitialState("obtenerDatosTransferencia");
				
	}

   protected ControllerResponse runObtenerDatosTransferenciaState(ControllerRequest request, ControllerResponse response) throws ControllerException {
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
			 
			solicitudInscripcion.setCodigoServicio(String.valueOf(gob.pe.sunarp.extranet.util.Constantes.COD_GLA_TRANSFERENCIAVEHICULAR));
			/** descripcionServicio **/
			solicitudInscripcion.setDescripcionServicio("SOLICITUD DE TRANSFERENCIA VEHICULAR");
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
			
			// Direccionamos a la pagina de Datos de Transferencia Vehicular	
			response.setStyle("datosTransferencia");
			
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

protected ControllerResponse runObtenerNuevoVehiculoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		SolicitudInscripcion solicitudInscripcion = null;
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
			instrumentoPublico.setCodigoTipoInstrumento("021"); //codigo de acta notarial de transferencia vehicular

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("ACTA NOTARIAL");

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
			
			System.out.println("ACTA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/
			
			//creando un nuevo vehiculo
			Vehiculo vehiculo = null;
			ArrayList listaVehiculos = null;
			
			listaVehiculos = solicitudInscripcion.getVehiculos();
			int sizeLista = 0;
			
			if (listaVehiculos == null)
				listaVehiculos = new ArrayList();
			
			vehiculo = new Vehiculo();
			listaVehiculos.add(vehiculo);
			solicitudInscripcion.setVehiculos(listaVehiculos);
			
			sizeLista = listaVehiculos.size();
			
			if (sizeLista>0)
				req.setAttribute("indiceVehiculo",String.valueOf(sizeLista-1));
			else
				req.setAttribute("indiceVehiculo", "0");	
			
			// Direccionamos a la pagina de detalle del participante
			response.setStyle("detalleVehiculo");
	
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

protected ControllerResponse runObtenerNuevoParticipanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		SolicitudInscripcion solicitudInscripcion = null;
		Vehiculo vehiculo = null;
		PersonaNatural personaNatural = null;
		PersonaJuridica personaJuridica = null;
		ArrayList listaParticipantePN = null;
		ArrayList listaParticipantePJ = null;
		
		
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

			// Inicializamos Beans
	        ArrayList vehiculos = solicitudInscripcion.getVehiculos();
			vehiculo = (Vehiculo)vehiculos.get(Integer.parseInt(req.getParameter("indVehiculo")));
			req.setAttribute("indiceVehiculo",req.getParameter("indVehiculo"));
			//recuperando la lista de vendedores juridicos y naturales
			req.setAttribute("vendedoresNaturales",request.getParameter("vendedoresNaturales"));
			req.setAttribute("vendedoresJuridicos",request.getParameter("vendedoresJuridicos"));
	        /**recupero los datos del formulario de Detalle de Vehiculo
	        y los seteo en el Bean de Vehiculo
	        **/
	        vehiculo.setPlaca(request.getParameter("txtPlaca"));
			vehiculo.setSerie(request.getParameter("txtSerie"));
			vehiculo.setMotor(request.getParameter("txtMotor"));
			vehiculo.setCodigoOficinaRegistral(request.getParameter("cboOficinas"));
			vehiculo.setNumeroPartida(request.getParameter("txtPartida"));
			vehiculo.setCodigoSubActo(request.getParameter("cboSubActo"));
			vehiculo.setCodigoFormaPago(request.getParameter("cboFormaPago"));
			vehiculo.setCodigoMoneda(request.getParameter("cboMoneda"));
			vehiculo.setObservaciones(request.getParameter("txtObservaciones"));
			
			if ( request.getParameter("txtMonto").equals("") )
				vehiculo.setMonto(new java.math.BigDecimal("0"));
			else
				vehiculo.setMonto( new java.math.BigDecimal( request.getParameter("txtMonto") ) );
			
			if ( request.getParameter("txtPagado").equals("") )
				vehiculo.setPagado( new java.math.BigDecimal("0") );
			else
				vehiculo.setPagado( new java.math.BigDecimal( request.getParameter("txtPagado") ) );			
			
			if ( request.getParameter("txtSaldo").equals("") )
				vehiculo.setSaldo( new java.math.BigDecimal("0") );	
			else
				vehiculo.setSaldo( new java.math.BigDecimal( request.getParameter("txtSaldo") ) );
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
		
		Vehiculo vehiculo = null;
		
		
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
			
			ArrayList vehiculos = solicitudInscripcion.getVehiculos();
			vehiculo = (Vehiculo)vehiculos.get(Integer.parseInt(req.getParameter("indVehiculo")));
			req.setAttribute("indiceVehiculo",req.getParameter("indVehiculo"));
			req.setAttribute("indice2",request.getParameter("hidIndiceMod2"));			
												        
	        /**recupero los datos del formulario de Detalle de Vehiculo
	        y los seteo en el Bean de Vehiculo
	        **/
	        vehiculo.setPlaca(req.getParameter("txtPlaca"));
			vehiculo.setSerie(req.getParameter("txtSerie"));
			vehiculo.setMotor(req.getParameter("txtMotor"));
			vehiculo.setCodigoOficinaRegistral(req.getParameter("cboOficinas"));
			vehiculo.setNumeroPartida(req.getParameter("txtPartida"));
			vehiculo.setCodigoSubActo(req.getParameter("cboSubActo"));
			vehiculo.setCodigoFormaPago(req.getParameter("cboFormaPago"));
			vehiculo.setCodigoMoneda(req.getParameter("cboMoneda"));
			vehiculo.setMonto(new java.math.BigDecimal(req.getParameter("txtMonto")));
			vehiculo.setPagado(new java.math.BigDecimal(req.getParameter("txtPagado")));
			vehiculo.setSaldo(new java.math.BigDecimal(req.getParameter("txtSaldo")));
			vehiculo.setObservaciones(req.getParameter("txtObservaciones"));
			               
			//Borrar				
			String[] indicesPN = req.getParameterValues("indicesListaPN");
			String[] indicesPJ = req.getParameterValues("indicesListaPJ");
			
				
			if (indicesPN!=null) 
				size1 = indicesPN.length;
			
			if (indicesPJ!=null)
				size2 = indicesPJ.length;
			
			listaParticipantesPN = vehiculo.getCompradoresPersonaNatural();
			listaParticipantesPJ = vehiculo.getCompradoresPersonaJuridica();
			
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
			vehiculo.setCompradoresPersonaNatural(listaParticipantesPN2);
			

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
			vehiculo.setCompradoresPersonaJuridica(listaParticipantesPJ2);

			// Direccionamos a la pagina de Datos de Bloqueo de Inmueble	
			response.setStyle("detalleVehiculo");
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

	protected ControllerResponse runAgregarVehiculoState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		Vehiculo vehiculo = null;
		ArrayList listaVehiculos = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		String indice = null;
		String indice2 = null;
		int ind = 0;
				
		try {	
				init(request);
				validarSesion(request);

				// Obtenemos el bean de solicitud de inscripcion de session
				solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
				
				indice = request.getParameter("indVehiculo");
				indice2 = request.getParameter("hidIndiceMod2");

				listaVehiculos = solicitudInscripcion.getVehiculos();
				if (listaVehiculos==null)
					listaVehiculos = new ArrayList();
				/********* por si viene de modificacion ********/
				if ( (indice2!=null) && (!indice2.equals("")) ) {
					System.out.println("modificacion-indice::"+indice2);
					ind = Integer.parseInt(indice2);
					vehiculo = (Vehiculo)listaVehiculos.get(ind);
				}
				/***********************************************/
				
				if ( (indice!=null) && (!indice.equals("")) ) {
					System.out.println("nuevo-indice::"+indice);
					ind = Integer.parseInt(indice);
					vehiculo = (Vehiculo)listaVehiculos.get(ind);
				}
				
				/** Placa **/
				vehiculo.setPlaca(request.getParameter("txtPlaca"));
				/** Serie **/
				vehiculo.setSerie(request.getParameter("txtSerie"));
				/** Motor **/
				vehiculo.setMotor(request.getParameter("txtMotor"));
				/** Oficina Registral **/
				vehiculo.setCodigoOficinaRegistral(request.getParameter("cboOficinas"));
				vehiculo.setDescripcionOficinaRegistral(request.getParameter("hidOfic"));
				/** Numero Partida **/
				vehiculo.setNumeroPartida(request.getParameter("txtPartida"));
				/** SubActo **/
				vehiculo.setCodigoSubActo(request.getParameter("cboSubActo"));
				/** Forma de Pago**/
				vehiculo.setCodigoFormaPago(request.getParameter("cboFormaPago"));
				/** Moneda **/
				vehiculo.setCodigoMoneda(request.getParameter("cboMoneda"));
				/** Monto **/
				vehiculo.setMonto(new java.math.BigDecimal (request.getParameter("txtMonto")));
				/** Pagado **/
				vehiculo.setPagado(new java.math.BigDecimal (request.getParameter("txtPagado")));						
				/** Saldo **/
				vehiculo.setSaldo(new java.math.BigDecimal (request.getParameter("txtSaldo")));
				/** Observaciones **/
				vehiculo.setObservaciones(request.getParameter("txtObservaciones"));
				
				
				/********* por si viene de modificacion ********/
				if ( (indice2!=null) && (indice2.equals("")) ) 
					//listaVehiculos.add(vehiculo); si lo pongo se duplica
					
				solicitudInscripcion.setVehiculos(listaVehiculos);	

				System.out.println("VEHICULO");
				System.out.println("Placa::"+vehiculo.getPlaca()+"::");			
				System.out.println("Serie::"+vehiculo.getSerie()+"::");							
				System.out.println("Motor::"+vehiculo.getMotor()+"::");							
				System.out.println("OficinaRegistral::"+vehiculo.getCodigoOficinaRegistral()+"::");							
				System.out.println("numeroPartida::"+vehiculo.getNumeroPartida()+"::");							
				System.out.println("subActo::"+vehiculo.getCodigoSubActo()+"::");							
				System.out.println("formaPago::"+vehiculo.getCodigoFormaPago()+"::");							
				System.out.println("Moneda::"+vehiculo.getCodigoMoneda()+"::");																						
				System.out.println("Monto::"+vehiculo.getMonto()+"::");	
				System.out.println("Pagado::"+vehiculo.getPagado()+"::");	
				System.out.println("Saldo::"+vehiculo.getSaldo()+"::");	
				System.out.println("Observaciones::"+vehiculo.getObservaciones()+"::");	
				
				System.out.println("Lista Vehiculos::"+listaVehiculos.size());	
			
			/*********************************************************************************************************/
								
			// Direccionamos a la pagina de los Datos de Reserva
			response.setStyle("datosTransferencia");
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
			//recuperando el indice de vehiculo para que se pueda pintar
			req.setAttribute("indiceVehiculo",request.getParameter("indVehiculo"));
			
			req.setAttribute("indice2",request.getParameter("hidIndiceMod2"));
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
			//recuperando el indice de vehiculo para que se pueda pintar
			req.setAttribute("indiceVehiculo",request.getParameter("indVehiculo"));
			req.setAttribute("indice2",request.getParameter("hidIndiceMod2"));			
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
			instrumentoPublico.setCodigoTipoInstrumento("021"); //codigo del acta notarial

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("ACTA NOTARIAL");

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
			
			System.out.println("ACTA NOTARIAL");
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

	protected ControllerResponse runRegresarATransVehicularState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
	
		try {

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
			
			//recuperando el indice de vehiculo para que se pueda pintar
			req.setAttribute("indiceVehiculo",request.getParameter("indVehiculo"));
			req.setAttribute("indice2",request.getParameter("hidIndiceMod2"));			
			req.setAttribute("indiceVehiculo2",request.getParameter("hidIndiceMod2"));//agregando			
			// Direccionamos a la pagina de Datos de transferencia Vehicular	
			response.setStyle("detalleVehiculo");

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

protected ControllerResponse runRegresarATransVehicular2State(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		String indice = null;	
		String indice2 = null;	
		SolicitudInscripcion solicitudInscripcion = null;
		ArrayList listaVehiculos = null;
		ArrayList listaVehiculos2 = null;
		try {

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
			
			indice = request.getParameter("hidIndiceMod2");
			indice2 = request.getParameter("indVehiculo");
			System.out.println("Indice en regresar a datos transferencia:" + indice);
			//indice = request.getParameter("hidIndiceMod2");
			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
			//recuperando la lista de vendedores juridicos y naturales
			req.setAttribute("vendedoresNaturales",request.getParameter("vendedoresNaturales"));
			req.setAttribute("vendedoresJuridicos",request.getParameter("vendedoresJuridicos"));
			listaVehiculos = solicitudInscripcion.getVehiculos();		
			System.out.println("vendedirersd::" + request.getParameter("vendedoresJuridicos") );
			
			if ( (indice!=null) && (indice.equals("")) ) 
				listaVehiculos.remove(Integer.parseInt(indice2));
			
			
			//recuperando el indice de vehiculo para que se pueda pintar
			req.setAttribute("indiceVehiculo",indice);
			req.setAttribute("indiceVehiculo2",request.getParameter("hidIndiceMod2"));//agregando			
			// Direccionamos a la pagina de Datos de transferencia Vehicular	
			response.setStyle("datosTransferencia");
			
			
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
	

	protected ControllerResponse runAgregarParticipanteState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		
		SolicitudInscripcion solicitudInscripcion = null;
		PersonaJuridica personaJuridica = null;
		PersonaNatural personaNatural = null;
		ArrayList listaParticipantesPN = null;
		ArrayList listaParticipantesPJ = null;
		String tipoPersona = null;						
		String indice = null;
		int ind = 0;
		ArrayList listaVehiculos = null;
		Vehiculo vehiculo = null;
						
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
	
			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
			listaVehiculos = solicitudInscripcion.getVehiculos();			
			vehiculo = (Vehiculo)listaVehiculos.get(Integer.parseInt(request.getParameter("indVehiculo")));
			req.setAttribute("indiceVehiculo", request.getParameter("indVehiculo"));
			tipoPersona = (String)request.getParameter("cboTipoPersona");
		
			indice = request.getParameter("hidIndiceMod");
			//recuperando la lista de vendedores juridicos y naturales
			req.setAttribute("vendedoresNaturales",request.getParameter("vendedoresNaturales"));
			req.setAttribute("vendedoresJuridicos",request.getParameter("vendedoresJuridicos"));
				
			/************************ DATOS DEL PARTICIPANTE PERSONA NATURAL*****************************************/
			if (tipoPersona.equals("PN")) {
				listaParticipantesPN = vehiculo.getCompradoresPersonaNatural();
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
	
				vehiculo.setCompradoresPersonaNatural(listaParticipantesPN);	

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
				listaParticipantesPJ = vehiculo.getCompradoresPersonaJuridica();
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
				
				vehiculo.setCompradoresPersonaJuridica(listaParticipantesPJ);	

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
			
			//req.getParameter("indVehiculo");
			req.setAttribute("indiceVehiculo",req.getAttribute("indiceVehiculo"));
			req.setAttribute("indiceVehiculo2",request.getParameter("hidIndiceMod2"));		
			// Direccionamos a la pagina de los Detalles de Vehiculo
			response.setStyle("detalleVehiculo");
	
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
	
	protected ControllerResponse runBorrarVehiculoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		
		Vehiculo vehiculo = null;
		ArrayList listaVehiculos = null;
		ArrayList listaVehiculos2 = null;
		SolicitudInscripcion solicitudInscripcion = null;
		InstrumentoPublico instrumentoPublico = null;		
		EscrituraPublica escrituraPublica = null;
		
		int size3 = 0;
		int sizeLista3 = 0;
		
						
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
			
			
			Vector vIndicesVehiculo = new Vector();
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
                	else if (name.equals("indicesListaVehiculo")) {
                		vIndicesVehiculo.add(value);
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
			instrumentoPublico.setCodigoTipoInstrumento("021");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("ACTA NOTARIAL");

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
			
			System.out.println("ACTA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/


			//Borrar				
			String[] indicesVehiculos = new String[vIndicesVehiculo.size()];
			vIndicesVehiculo.copyInto(indicesVehiculos);

			if (indicesVehiculos!=null) 
				size3 = indicesVehiculos.length;
			
			listaVehiculos = solicitudInscripcion.getVehiculos();
			
			if (listaVehiculos!=null)
				sizeLista3 = listaVehiculos.size();
				


			listaVehiculos2 = new ArrayList();
			boolean res = false;

			for (int i=0; i<sizeLista3; i++) {
				res = false;
				vehiculo = (Vehiculo)listaVehiculos.get(i);
				for (int j=0; j<size3; j++) {
					if (i == Integer.parseInt(indicesVehiculos[j]) )
						res = true;
				}
				if (res==false)
					listaVehiculos2.add(vehiculo);
				
			}			
			
			solicitudInscripcion.setVehiculos(listaVehiculos2);
			

			// Direccionamos a la pagina de Datos de Bloqueo de Inmueble	
			response.setStyle("datosTransferencia");
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
		ArrayList listaVehiculos = null;
		PersonaNatural personaNatural = null;
		PersonaJuridica personaJuridica = null;
		Vehiculo vehiculo = null;
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
			
			listaVehiculos = solicitudInscripcion.getVehiculos();
			vehiculo = (Vehiculo)listaVehiculos.get(Integer.parseInt((String)request.getParameter("indVehiculo")));
										
			String indice = null;
			String tipoPersona = null;
			
			vehiculo.setPlaca(request.getParameter("txtPlaca"));
			vehiculo.setSerie(request.getParameter("txtSerie"));
			vehiculo.setMotor(request.getParameter("txtMotor"));
			vehiculo.setCodigoOficinaRegistral(request.getParameter("cboOficinas"));
			vehiculo.setNumeroPartida(request.getParameter("txtPartida"));
			vehiculo.setCodigoSubActo(request.getParameter("cboSubActo"));
			vehiculo.setCodigoFormaPago(request.getParameter("cboFormaPago"));
			vehiculo.setCodigoMoneda(request.getParameter("cboMoneda"));
			vehiculo.setMonto(new java.math.BigDecimal(request.getParameter("txtMonto")));
			vehiculo.setPagado(new java.math.BigDecimal(request.getParameter("txtPagado")));
			vehiculo.setSaldo(new java.math.BigDecimal(request.getParameter("txtSaldo")));
			vehiculo.setObservaciones(request.getParameter("txtObservaciones"));	        
	       	
	       	indice = request.getParameter("hidIndiceMod");
	       	
	       	tipoPersona = request.getParameter("hidTipoPersona");
	       		       			
			System.out.println("indice!!!!::"+indice);
			// Obtenemos el indice que fue seleccionado para modificacion
			req.setAttribute("indice",indice);
			req.setAttribute("indice2",request.getParameter("hidIndiceMod2"));
			//agregado error despyues de modificar participante
			req.setAttribute("indiceVehiculo",request.getParameter("indVehiculo"));
			
			if (tipoPersona.equals("PN")) {
				listaParticipantesPN = vehiculo.getCompradoresPersonaNatural();
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
				listaParticipantesPJ = vehiculo.getCompradoresPersonaJuridica();
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
	
	protected ControllerResponse runObtenerVehiculoState (ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		SolicitudInscripcion solicitudInscripcion = null;
		
		ArrayList instrumentosPublico = null;
		ArrayList listaVehiculos = null;
		Vehiculo vehiculo = null;
		InstrumentoPublico instrumentoPublico = null;		
		EscrituraPublica escrituraPublica = null;
		String indice = null;
		
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
			

							        
	        // Obtenemos los datos
	        while(it.hasNext()) {
                item = (FileItem)it.next();
                name=item.getFieldName();
                value=item.getString();
                if(item.isFormField()) {
					/******************************* DATOS DE LA PERSONA JURIDICA A CONSTITUIR  ****************************/
                   	//if (name.equals("txtSolRazonSocial"))
						/** razonSocialPersonaJuridica **/
					//	personaJuridica.setRazonSocial(value);
					//else if (name.equals("txtSolSiglas"))
						/** siglas **/
					//	personaJuridica.setSiglas(value);
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
                	else if (name.equals("hidIndiceMod2"))//lo traigo del formulario de carag de archivo de transf vehi
                		indice = value;
                	
                }
                else {
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
			instrumentoPublico.setCodigoTipoInstrumento("021");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("ACTA NOTARIAL");

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
			
			System.out.println("ACTA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/


			System.out.println("indice!!!!::"+indice);
			
			// Obtenemos el indice que fue seleccionado para modificacion
						
			req.setAttribute("indiceVehiculo",indice); 
			req.setAttribute("indiceVehiculo2",indice); 
			
			
			listaVehiculos = solicitudInscripcion.getVehiculos();
			vehiculo = (Vehiculo)listaVehiculos.get(Integer.parseInt(indice));
						
			response.setStyle("detalleVehiculo");

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
			instrumentoPublico.setCodigoTipoInstrumento("021");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("ACTA NOTARIAL");

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
			
			System.out.println("ACTA NOTARIAL");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/
	
			/*********************** CALCULAMOS LA TARIFA *********************************/
			int codServicio= gob.pe.sunarp.extranet.util.Constantes.COD_SERVICIO_TRANSFVEHICULAR;
			int codGLA= gob.pe.sunarp.extranet.util.Constantes.COD_GLA_TRANSFERENCIAVEHICULAR;
			
			DboTarifa tarifa = new DboTarifa(dconn);
			tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicio);
			tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codGLA);
		
			if(!tarifa.find())
				throw new DBException("No existe servicio con codigo '"+ codServicio + "' y GLA '"+ codGLA + "' en tabla TARIFA");
				
		    req.setAttribute("tarifa",tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
			/******************************************************************************/
			
			req.setAttribute("indice2",request.getParameter("hidIndiceMod2"));
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
protected ControllerResponse runLlenaVehiculoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		Vehiculo vehiculo = null;
		SolicitudInscripcion solicitudInscripcion = null;
		ArrayList listaVehiculos = null;
		String indice = null;
		String flag1 = null;
				
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
       
			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			// Obteniendo placa serie motor
			System.out.println("Consultando Vehiculo");
			DboVehiculo dboVehiculo = new  DboVehiculo(dconn);
	        dboVehiculo.setFieldsToRetrieve(DboVehiculo.CAMPO_NUM_PLACA + "|" +
										   DboVehiculo.CAMPO_NUM_SERIE + "|" + 
										   DboVehiculo.CAMPO_NUM_MOTOR + "|" +
										   DboVehiculo.CAMPO_REFNUM_PART
										   );
										   
			String placa = request.getParameter("txtPlaca");
			dboVehiculo.setField(DboVehiculo.CAMPO_NUM_PLACA, placa);	
			String serie = null;
			String motor = null;
			String refnumpart = null;
			if (dboVehiculo.find() == true) {
				serie = dboVehiculo.getField(DboVehiculo.CAMPO_NUM_SERIE);
				motor = dboVehiculo.getField(DboVehiculo.CAMPO_NUM_MOTOR);
				refnumpart = dboVehiculo.getField(DboVehiculo.CAMPO_REFNUM_PART);
				System.out.println("Placa::"+placa);
				System.out.println("Serie::"+serie);
				System.out.println("Motor::"+motor);
				System.out.println("Ref Num Part::"+refnumpart);
				
			}
			
			
			// Obteniendo Zona y Oficina Registral
			System.out.println("Consultando Partida");
			DboPartida dboPartida = new  DboPartida(dconn);
	        dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID + "|" +
										   DboPartida.CAMPO_OFIC_REG_ID + "|" + 
										   DboPartida.CAMPO_NUM_PARTIDA 
										   );
			dboPartida.setField(DboPartida.CAMPO_REFNUM_PART, refnumpart);		
			dboPartida.setField(DboPartida.CAMPO_ESTADO, "1");		
			String zona = null;
			String oficina = null;
			String numPartida = null;	
			if (dboPartida.find() == true) {
				zona = dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID);
				oficina = dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID);
				numPartida = dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA);
				System.out.println("zona::"+zona);
				System.out.println("oficina::"+oficina);
				System.out.println("numPartida::"+numPartida);
				
				
			}
			
			//Obteniendo datos de tabla IND_PRTC
			System.out.println("Consultando IND_PRTC"); 
			DboIndPrtc dboIndPrtc = new DboIndPrtc(dconn);
			dboIndPrtc.setFieldsToRetrieve(DboIndPrtc.CAMPO_CUR_PRTC + "|" + 
											DboIndPrtc.CAMPO_TIPO_PERS + "|" + 
											DboIndPrtc.CAMPO_COD_PARTIC
											);
			dboIndPrtc.setField(DboIndPrtc.CAMPO_ESTADO,"1");
			dboIndPrtc.setField(DboIndPrtc.CAMPO_REFNUM_PART,refnumpart);
			dboIndPrtc.setField(DboIndPrtc.CAMPO_TIPO_PERS,"N");
			String tipoPersona = null;
			String curPrtc = null;
			String codParticipante = null;
			PersonaNatural personaNatural = null;
			PersonaJuridica personaJuridica = null;
			java.util.ArrayList listaPersonaJuridica = new ArrayList(); 
			java.util.ArrayList listaPersonaNatural = new ArrayList(); 
			java.util.ArrayList listaIndPtrcNat = dboIndPrtc.searchAndRetrieveList();
			
				for (int i = 0; i < listaIndPtrcNat.size(); i++) 
					{
						
						
						DboIndPrtc lista = (DboIndPrtc) listaIndPtrcNat.get(i);
						curPrtc =lista.getField(DboIndPrtc.CAMPO_CUR_PRTC);
						
						//Obteniendo datos de PRTC_NATU
						System.out.println("Consultando PRTC_NATU");
						DboPrtcNat dboPrtcNat = new DboPrtcNat(dconn);
							dboPrtcNat.setFieldsToRetrieve(DboPrtcNat.CAMPO_TI_DOC_IDEN + "|" + 
															DboPrtcNat.CAMPO_NU_DOC_IDEN + "|" +
														    DboPrtcNat.CAMPO_APE_PAT + "|" +
														    DboPrtcNat.CAMPO_APE_MAT + "|" +
														    DboPrtcNat.CAMPO_NOMBRES 
														    );
							dboPrtcNat.setField(DboPrtcNat.CAMPO_CUR_PRTC,curPrtc);
							String descDocu = null;
							
						if (dboPrtcNat.find() == true) {
							personaNatural = new PersonaNatural();
							personaNatural.setCodigoTipoDocumento(dboPrtcNat.getField(dboPrtcNat.CAMPO_TI_DOC_IDEN));
							personaNatural.setNumeroDocumento(dboPrtcNat.getField(dboPrtcNat.CAMPO_NU_DOC_IDEN));
							personaNatural.setApellidoPaterno(dboPrtcNat.getField(dboPrtcNat.CAMPO_APE_PAT));
							personaNatural.setApellidoMaterno(dboPrtcNat.getField(dboPrtcNat.CAMPO_APE_MAT));
							personaNatural.setNombre(dboPrtcNat.getField(dboPrtcNat.CAMPO_NOMBRES));
							descDocu = dboPrtcNat.getField(dboPrtcNat.CAMPO_TI_DOC_IDEN);
							System.out.println("*******llego");
							System.out.println(descDocu);
						}
						
							listaPersonaNatural.add(personaNatural);	
					}
					
						/*System.out.println("Consultando TA_TABL"); 
							DboTATabl dboTaTabl = new DboTATabl(dconn);
							dboTaTabl.setFieldsToRetrieve(DboTATabl.CAMPO_DE_VALO  
															);
							dboTaTabl.setField(DboTATabl.CAMPO_VA_COLU,descDocu);
							
							if(dboTaTabl.find() == true){
							personaNatural.setDescripcionTipoDocumento(dboTaTabl.getField(dboTaTabl.CAMPO_DE_VALO))
							System.out.println(dboPrtcNat.CAMPO_TI_DOC_IDEN); 
							}	
						*/						
						
						//Obteniendo datos de tabla IND_PRTC
						System.out.println("Consultando IND_PRTC"); 
						//DboIndPrtc dboIndPrtc = new DboIndPrtc(dconn);
						dboIndPrtc.clearAll();
						dboIndPrtc.setFieldsToRetrieve(DboIndPrtc.CAMPO_CUR_PRTC + "|" + 
											DboIndPrtc.CAMPO_TIPO_PERS + "|" + 
											DboIndPrtc.CAMPO_COD_PARTIC
											);
						dboIndPrtc.setField(DboIndPrtc.CAMPO_ESTADO,"1");
						dboIndPrtc.setField(DboIndPrtc.CAMPO_REFNUM_PART,refnumpart);
						dboIndPrtc.setField(DboIndPrtc.CAMPO_TIPO_PERS,"J");
			
			
			java.util.ArrayList listaIndPtrcJur = dboIndPrtc.searchAndRetrieveList();
			
				for (int i = 0; i < listaIndPtrcJur.size(); i++) 
					{
						
						
						DboIndPrtc lista = (DboIndPrtc) listaIndPtrcNat.get(i);
						curPrtc =lista.getField(DboIndPrtc.CAMPO_CUR_PRTC);
						
						//Obteniendo datos de PRTC_JURI
						System.out.println("Consultando PRTC_JURI");
						DboPrtcJur dboPrtcJur = new DboPrtcJur(dconn);
						dboPrtcJur.setFieldsToRetrieve(DboPrtcJur.CAMPO_TI_DOC +"|" +
													   DboPrtcJur.CAMPO_NU_DOC +"|" +
													   DboPrtcJur.CAMPO_RAZON_SOCIAL 													   												  
													  );
						dboPrtcJur.setField(DboPrtcJur.CAMPO_CUR_PRTC,curPrtc);
						
						if (dboPrtcJur.find() == true) {
							personaJuridica = new PersonaJuridica();
							personaJuridica.setCodigoTipoDocumento(dboPrtcJur.getField(dboPrtcJur.CAMPO_TI_DOC));
							personaJuridica.setNumeroDocumento(dboPrtcJur.getField(dboPrtcJur.CAMPO_NU_DOC));
							personaJuridica.setRazonSocial(dboPrtcJur.getField(dboPrtcJur.CAMPO_RAZON_SOCIAL));
											
						}
						
						/*System.out.println("Consultando TA_TABL"); 
							DboTATabl dboTaTabl = new DboTATabl(dconn);
							dboTaTabl.setFieldsToRetrieve(DboTATabl.CAMPO_DE_VALO  
															);
							dboTaTabl.setField(DboTATabl.CAMPO_VA_COLU,dboPrtcJur.getField(dboPrtcJur.CAMPO_NU_DOC));
							
							if(dboTaTabl.find() == true){
							personaJuridica.setDescripcionTipoDocumento(dboTaTabl.getField(dboTaTabl.CAMPO_DE_VALO))
							}
						
						*/
						listaPersonaJuridica.add(personaJuridica);

					}


			// Obtenemos el bean de solicitud de inscripcion de session
			solicitudInscripcion = (SolicitudInscripcion)session.getAttribute("solicitudInscripcion");
						
			indice = request.getParameter("indVehiculo");
			
			req.setAttribute("indiceVehiculo",indice); 
			req.setAttribute("indiceVehiculo2",request.getParameter("hidIndiceMod2"));
			//req.setAttribute("indiceVehiculo2",indice); 

			//Recupero los Vehiculos
			listaVehiculos = solicitudInscripcion.getVehiculos();
			vehiculo = (Vehiculo)listaVehiculos.get(Integer.parseInt(indice));
			
			vehiculo.setPlaca(placa);
			vehiculo.setSerie(serie);
			vehiculo.setMotor(motor);
			vehiculo.setCodigoOficinaRegistral(zona);
			vehiculo.setCodigoZonaRegistral(oficina);
			vehiculo.setNumeroPartida(numPartida);
			//vehiculo.setVendedorNatural();
			//vehiculo.setVendedorJuridica();
			
			// Haciendo request para recuperar a los vendedores del Vehiculo Natural
			req.setAttribute("vendedoresNaturales",listaPersonaNatural); 			
			
			// Haciendo request para recuperar a los vendedores del Vehiculo Juridica
			req.setAttribute("vendedoresJuridicas",listaPersonaJuridica); 			

			//para direccionar en el xml a la pagina
			response.setStyle("detalleVehiculo");
	
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

