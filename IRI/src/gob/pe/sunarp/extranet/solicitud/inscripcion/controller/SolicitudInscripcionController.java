package gob.pe.sunarp.extranet.solicitud.inscripcion.controller;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.logica.Constantes;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.*;
import gob.pe.sunarp.extranet.administracion.bean.DatosUsuarioBean;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.*;
import java.util.ArrayList;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.util.*;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;

import org.apache.commons.fileupload.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class SolicitudInscripcionController extends ControllerExtension {

	public SolicitudInscripcionController() {
		super();
		addState(new State("datosGenerales", "Datos Generales"));
		addState(new State("obtenerPresentante", "Obtiene Datos del Presentante"));
		addState(new State("obtenerProvincia", "Obtiene Provincia"));
		addState(new State("obtenerDistrito", "Obtiene Distrito"));
		addState(new State("regresarASolicitudInscripcion", "Regresar a Solicitud Inscripcion"));
		addState(new State("regresarASolicitudInscripcion2", "Regresar a Solicitud Inscripcion"));
		addState(new State("regresarASolicitudInscripcion3", "Regresar a Solicitud Inscripcion"));//regreso a pantalla inicial de bloqueo de inmueble
		addState(new State("regresarASolicitudInscripcion4", "Regresar a Solicitud Inscripcion"));//regreso a pantalla inicial de transferencia Vehicular
		setInitialState("datosGenerales");
	}

	protected ControllerResponse runDatosGeneralesState(ControllerRequest request, ControllerResponse response) throws ControllerException {

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

			// Removemos variables de session
			session.removeAttribute("presentante");
			session.removeAttribute("arrRepr");
			session.removeAttribute("arrDocu");
			session.removeAttribute("DATOS_FORMULARIO");
			session.removeAttribute("solicitudInscripcion");						
			
			//Obtenemos datos del usuario que se loguea
			DatosUsuarioBean datosUsuarioBean = Tarea.getDatosUsuario(dconn,bean.getUserId());
			
			// Verificamos si pertenece a una notaria
			String idPersona = datosUsuarioBean.getPersonaId();
			System.out.println("idPersona::"+idPersona);
			String persJuriId = datosUsuarioBean.getPersJuriId();
			
			if (persJuriId==null) {
			
				System.out.println("Persona Juridica igual a null");
				DboTPNtia dboTpNtia = new DboTPNtia(dconn);
				dboTpNtia.setFieldsToRetrieve(DboTPNtia.CAMPO_CO_NTIA + "|" +
											  DboTPNtia.CAMPO_DE_NTIA);									  
				dboTpNtia.setField(DboTPNtia.CAMPO_PERSONA_ID, idPersona);
				String codigoNotaria = null;
				String descripcionNotaria = null;
				if (dboTpNtia.find() == true) {
					codigoNotaria = dboTpNtia.getField(DboTPNtia.CAMPO_CO_NTIA);
					descripcionNotaria = dboTpNtia.getField(DboTPNtia.CAMPO_DE_NTIA);
						
				}
	
				// EL USUARIO LOGUEADO PERTENECE A UNA NOTARIA
				if (codigoNotaria!=null) {
					DboTARepr dboTaRepr = new DboTARepr(dconn);
					dboTaRepr.setFieldsToRetrieve(DboTARepr.CAMPO_AP_MATE_REPR + "|" +
												  DboTARepr.CAMPO_AP_PATE_REPR + "|" +
												  DboTARepr.CAMPO_CO_DEPA + "|" +
												  DboTARepr.CAMPO_CO_DIST + "|" +
												  DboTARepr.CAMPO_CO_PAIS + "|" +
												  DboTARepr.CAMPO_CO_PROV + "|" +
												  DboTARepr.CAMPO_CO_REPR + "|" +
												  DboTARepr.CAMPO_CO_TIPO_DOCU + "|" +
												  DboTARepr.CAMPO_DE_MAIL + "|" +
												  DboTARepr.CAMPO_NO_DIRE + "|" +
												  DboTARepr.CAMPO_NO_MAIL + "|" +
												  DboTARepr.CAMPO_NO_REPR + "|" +
												  DboTARepr.CAMPO_NU_DOCU + "|" +
												  DboTARepr.CAMPO_DE_MAIL);									  
					dboTaRepr.setField(DboTARepr.CAMPO_IN_ESTD, "A");
					dboTaRepr.setField(DboTARepr.CAMPO_CO_NTIA, codigoNotaria);
					Presentante presentante = null;
					ArrayList resultado = new ArrayList();
					java.util.ArrayList arr2 = dboTaRepr.searchAndRetrieveList(DboTARepr.CAMPO_CO_REPR);
	
					for (int i = 0; i < arr2.size(); i++) 
					{
						ComboBean b = new ComboBean();
						DboTARepr d = (DboTARepr) arr2.get(i);
						b.setCodigo(d.getField(DboTARepr.CAMPO_CO_REPR));
						b.setDescripcion(d.getField(DboTARepr.CAMPO_CO_REPR));
						resultado.add(b);
					}
					
					req.setAttribute("codigoNotaria", codigoNotaria);
					req.setAttribute("descripcionNotaria", descripcionNotaria);
					session.setAttribute("arrRepr", (ArrayList)resultado);
						
				}
				// EL USUARIO LOGUEADO NO PERTENECE A UNA NOTARIA
				else {
	
					// Obtiene Tipos de Documento
					session.setAttribute("arrDocu", (ArrayList)Tarea.getComboTiposDocumento(dconn));
					
					req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
					System.out.println("dep::"+datosUsuarioBean.getDepartamento());
					req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, datosUsuarioBean.getDepartamento()));
					System.out.println("prov::"+datosUsuarioBean.getProvincia());
					req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, datosUsuarioBean.getDepartamento(), datosUsuarioBean.getProvincia()));
					System.out.println("dist::"+datosUsuarioBean.getDistrito());
		
					session.setAttribute("DATOS_FORMULARIO", datosUsuarioBean);
					}
			
			}
			
			else {
					//consultando PEJURI
					System.out.println("Persona Juridica diferente a null");
					DboPeJuri dboPeJuri = new DboPeJuri(dconn);
					dboPeJuri.setFieldsToRetrieve(DboPeJuri.CAMPO_PERSONA_ID
												  );									  
					String personaIdPeJuri = null;
					dboPeJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, persJuriId );
					if (dboPeJuri.find() == true) {
						personaIdPeJuri = dboPeJuri.getField(DboPeJuri.CAMPO_PERSONA_ID);
						
					}
					
						// Consultando Notaria TPNTIA
					DboTPNtia dboTpNtia = new DboTPNtia(dconn);
					dboTpNtia.setFieldsToRetrieve(DboTPNtia.CAMPO_CO_NTIA + "|" +
												  DboTPNtia.CAMPO_DE_NTIA);									  
					dboTpNtia.setField(DboTPNtia.CAMPO_PERSONA_ID, personaIdPeJuri);
					String codigoNotaria = null;
					String descripcionNotaria = null;
					
					if (dboTpNtia.find() == true) {
						codigoNotaria = dboTpNtia.getField(DboTPNtia.CAMPO_CO_NTIA);
						descripcionNotaria = dboTpNtia.getField(DboTPNtia.CAMPO_DE_NTIA);
						System.out.println("codigoNotaria::"+codigoNotaria);
					}
		
					// EL USUARIO LOGUEADO PERTENECE A UNA NOTARIA
					if (codigoNotaria!=null) {
						DboTARepr dboTaRepr = new DboTARepr(dconn);
						dboTaRepr.setFieldsToRetrieve(DboTARepr.CAMPO_AP_MATE_REPR + "|" +
													  DboTARepr.CAMPO_AP_PATE_REPR + "|" +
													  DboTARepr.CAMPO_CO_DEPA + "|" +
													  DboTARepr.CAMPO_CO_DIST + "|" +
													  DboTARepr.CAMPO_CO_PAIS + "|" +
													  DboTARepr.CAMPO_CO_PROV + "|" +
													  DboTARepr.CAMPO_CO_REPR + "|" +
													  DboTARepr.CAMPO_CO_TIPO_DOCU + "|" +
													  DboTARepr.CAMPO_DE_MAIL + "|" +
													  DboTARepr.CAMPO_NO_DIRE + "|" +
													  DboTARepr.CAMPO_NO_MAIL + "|" +
													  DboTARepr.CAMPO_NO_REPR + "|" +
													  DboTARepr.CAMPO_NU_DOCU + "|" +
													  DboTARepr.CAMPO_DE_MAIL);									  
						dboTaRepr.setField(DboTARepr.CAMPO_IN_ESTD, "A");
						dboTaRepr.setField(DboTARepr.CAMPO_CO_NTIA, codigoNotaria);
						Presentante presentante = null;
						ArrayList resultado = new ArrayList();
						java.util.ArrayList arr2 = dboTaRepr.searchAndRetrieveList(DboTARepr.CAMPO_CO_REPR);
		
						for (int i = 0; i < arr2.size(); i++) 
						{
							ComboBean b = new ComboBean();
							DboTARepr d = (DboTARepr) arr2.get(i);
							b.setCodigo(d.getField(DboTARepr.CAMPO_CO_REPR));
							b.setDescripcion(d.getField(DboTARepr.CAMPO_CO_REPR));
							resultado.add(b);
						}
						
						req.setAttribute("codigoNotaria", codigoNotaria);
						req.setAttribute("descripcionNotaria", descripcionNotaria);
						session.setAttribute("arrRepr", (ArrayList)resultado);
							
					}
					// EL USUARIO LOGUEADO NO PERTENECE A UNA NOTARIA
					else {
		
						// Obtiene Tipos de Documento
						session.setAttribute("arrDocu", (ArrayList)Tarea.getComboTiposDocumento(dconn));
						
						req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
						System.out.println("dep::"+datosUsuarioBean.getDepartamento());
						req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, datosUsuarioBean.getDepartamento()));
						System.out.println("prov::"+datosUsuarioBean.getProvincia());
						req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, datosUsuarioBean.getDepartamento(), datosUsuarioBean.getProvincia()));
						System.out.println("dist::"+datosUsuarioBean.getDistrito());
			
						session.setAttribute("DATOS_FORMULARIO", datosUsuarioBean);
						}
		
					
			}
								
			// Tipos de Via
			DboTipoVia dboTipoVia = new DboTipoVia();
			dboTipoVia.clearAll();
			dboTipoVia.setField(DboTipoVia.CAMPO_ESTADO, "1");
			java.util.ArrayList arr4 = dboTipoVia.searchAndRetrieveList(DboTipoVia.CAMPO_DESC_CORTA);
			java.util.ArrayList arrTipoVia = new ArrayList();
			
			for (int i = 0; i < arr4.size(); i++) {
				ComboBean b = new ComboBean();
				DboTipoVia d = (DboTipoVia) arr4.get(i);
				b.setCodigo(d.getField(DboTipoVia.CAMPO_TIPO_VIA));
				b.setDescripcion(d.getField(DboTipoVia.CAMPO_DESC_CORTA));
				arrTipoVia.add(b);
			}
			session.setAttribute("arrTipoVia", arrTipoVia);

			
			Zona zona = new Zona();
			zona.setConn(dconn);
			//zona.setUsuario(usuario);
			zona.setPaisId(datosUsuarioBean.getPais());
			zona.setDepartamentoId(datosUsuarioBean.getDepartamento());
			zona.setProvinciaId(datosUsuarioBean.getProvincia());
			zona.calculaZona();
			req.setAttribute("oficRegId", zona.getOficRegId());
			req.setAttribute("regPubId", zona.getRegPubId());
			
			req.setAttribute("acto", "00");
			
								
			response.setStyle("datosGenerales");
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


	protected ControllerResponse runRegresarASolicitudInscripcionState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		DatosUsuarioBean datosUsuarioBean = null;
		Presentante presentante = null;
		SolicitudInscripcion solicitudInscripcion = null;
		PersonaJuridica personaJuridica = null;
				
		try {

			init(request);
			validarSesion(request);

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
			
			req.setAttribute("codigoNotaria", solicitudInscripcion.getPresentante().getCodigoInstitucion());
			req.setAttribute("descripcionNotaria", solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			req.setAttribute("codRepr", solicitudInscripcion.getPresentante().getCodigoPresentante());
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento()));
			req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento(), solicitudInscripcion.getPresentante().getCodigoProvincia()));
			req.setAttribute("oficRegId", solicitudInscripcion.getCodigoOficinaRegistral());
			req.setAttribute("regPubId", solicitudInscripcion.getCodigoZonaRegistral());
			req.setAttribute("acto", solicitudInscripcion.getCodigoArea() + "|" + solicitudInscripcion.getCodigoLibro() + "|" + solicitudInscripcion.getCodigoActo());
			
			response.setStyle("datosGenerales");

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
				
	protected ControllerResponse runRegresarASolicitudInscripcion2State(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		DatosUsuarioBean datosUsuarioBean = null;
		Presentante presentante = null;
		SolicitudInscripcion solicitudInscripcion = null;
		PersonaJuridica personaJuridica = null;
		Capital capital = null;
		InstrumentoPublico instrumentoPublico = null;		
		ReservaMercantil reservaMercantil = null;
		EscrituraPublica escrituraPublica = null;				

		try {

			init(request);
			validarSesion(request);

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
			
			capital = solicitudInscripcion.getCapital();
			if (capital==null)
				capital = new Capital();

			reservaMercantil = solicitudInscripcion.getReservaMercantil();
			if (reservaMercantil==null)
				reservaMercantil = new ReservaMercantil();

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
						personaJuridica.setIndicadorRUC(value);
					}
					/*********************************************************************************************************/
					/********************************** CAPITAL *************************************************************/
					else if (name.equals("cboSolMoneda"))
						/** codigoMoneda **/
						capital.setCodigoMoneda(value);
					else if (name.equals("hidMoneda"))
						/** descripcionMoneda **/
						capital.setDescripcionMoneda(value);
					else if (name.equals("txtSolMonto")) {
						if ( !value.equals("") )
							/** montoCapital **/
							capital.setMontoCapital(new BigDecimal(value));									
					}
					else if (name.equals("txtSolValor")) {
						if ( !value.equals("") )
							/** valor **/
							capital.setValor(new BigDecimal(value));
					}
					else if (name.equals("txtNumeroAccion")) {
						if ( !value.equals("") )
							/** numero **/
							capital.setNumero(Integer.parseInt(value));
					}
					else if (name.equals("rdoSolCancelacion")) {
						/** codigoCancelacionCapital **/
						capital.setCodigoCancelacionCapital(value);
						if (capital.getCodigoCancelacionCapital().equals("01"))
							/** descripcionCancelacioncapital **/
							capital.setDescripcionCancelacionCapital("TOTAL");
						else
							/** descripcionCancelacioncapital **/
							capital.setDescripcionCancelacionCapital("PARCIAL");			

					}
					else if (name.equals("txtPorCancelado")) {
						if ( !value.equals("") )
							/** valor **/
							capital.setPorcentajeCancelado(new BigDecimal(value));
					}
					/*********************************************************************************************************/
					/************************* DATOS DE RESERVA DE DENOMINACION ****************************************/
					else if (name.equals("txtSolAnhoTitulo"))
						/** anhoTitulo **/
						reservaMercantil.setAnhoTitulo(value);
						
					else if (name.equals("txtSolNumeroTitulo"))
						/** numeroTitulo **/
						reservaMercantil.setNumeroTitulo(value);
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
                } else {
                    //InputStream in = item.getInputStream();
                    if ( (item.getName()!=null) && (!item.getName().equals("")) ) {
	                    /** nombreArchivo **/
	                    escrituraPublica.setNombreArchivo(item.getName());
	                    /** documentoEscrituraPublica **/
	                    escrituraPublica.setDocumentoEscrituraPublica(value);
	                    // do something with the file contents
                    }
                    //in.close();
                }
            }

			if (personaJuridica.getIndicadorRUC()==null)
				personaJuridica.setIndicadorRUC("NO");
				
			solicitudInscripcion.setPersonaJuridica(personaJuridica);

			System.out.println("PERSONA JURIDICA A RESERVAR");
			System.out.println("razonSocial::"+personaJuridica.getRazonSocial()+"::");
			System.out.println("siglas::"+personaJuridica.getSiglas()+"::");
			System.out.println("codigoTipoSociedadAnonima::"+personaJuridica.getCodigoTipoSociedadAnonima()+"::");			
			System.out.println("descripcionTipoSociedadAnonima::"+personaJuridica.getDescripcionTipoSociedadAnonima()+"::");			
			System.out.println("indicadorRUC::"+personaJuridica.getIndicadorRUC()+"::");			
			/*********************************************************************************************************/
			solicitudInscripcion.setCapital(capital);
			
			System.out.println("CAPITAL");
			System.out.println("codigoMoneda::"+capital.getCodigoMoneda()+"::");
			System.out.println("descripcionMoneda::"+capital.getDescripcionMoneda()+"::");
			System.out.println("montoCapital::"+capital.getMontoCapital()+"::");			
			System.out.println("valor::"+capital.getValor()+"::");			
			System.out.println("numero::"+capital.getNumero()+"::");			
			System.out.println("codigoCancelacionCapital::"+capital.getCodigoCancelacionCapital()+"::");
			System.out.println("descripcionCancelacioncapital::"+capital.getDescripcionCancelacionCapital()+"::");
			System.out.println("porcentajeCancelado::"+capital.getPorcentajeCancelado()+"::");						
			/********************************************************************************************************/
			solicitudInscripcion.setReservaMercantil(reservaMercantil);
			
			System.out.println("RESERVA MERCANTIL");
			System.out.println("anhoTitulo::"+reservaMercantil.getAnhoTitulo()+"::");
			System.out.println("numeroTitulo::"+reservaMercantil.getNumeroTitulo()+"::");
			/****************************************************************************************************/
		
			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("01");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("ESCRITURA PUBLICA");

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
			
			System.out.println("ESCRITURA PUBLICA");
			System.out.println("documentoEscrituraPublico::"+escrituraPublica.getDocumentoEscrituraPublica()+"::");
			/******************************************************************************************************/			
			
			req.setAttribute("codigoNotaria", solicitudInscripcion.getPresentante().getCodigoInstitucion());
			req.setAttribute("descripcionNotaria", solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			req.setAttribute("codRepr", solicitudInscripcion.getPresentante().getCodigoPresentante());
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento()));
			req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento(), solicitudInscripcion.getPresentante().getCodigoProvincia()));
			req.setAttribute("oficRegId", solicitudInscripcion.getCodigoOficinaRegistral());
			req.setAttribute("regPubId", solicitudInscripcion.getCodigoZonaRegistral());
			req.setAttribute("acto", solicitudInscripcion.getCodigoArea() + "|" + solicitudInscripcion.getCodigoLibro() + "|" + solicitudInscripcion.getCodigoActo());
			
			response.setStyle("datosGenerales");

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

	protected ControllerResponse runRegresarASolicitudInscripcion3State(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		DatosUsuarioBean datosUsuarioBean = null;
		Presentante presentante = null;
		SolicitudInscripcion solicitudInscripcion = null;
		//PersonaJuridica personaJuridica = null;
		//Capital capital = null;
		InstrumentoPublico instrumentoPublico = null;		
		//ReservaMercantil reservaMercantil = null;
		EscrituraPublica escrituraPublica = null;				

		try {

			init(request);
			validarSesion(request);

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
			
			/*capital = solicitudInscripcion.getCapital();
			if (capital==null)
				capital = new Capital();
			*/
			/*reservaMercantil = solicitudInscripcion.getReservaMercantil();
			if (reservaMercantil==null)
				reservaMercantil = new ReservaMercantil();
			*/
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
                    }
                    //in.close();
                }
            }

			
				
			//solicitudInscripcion.setPersonaJuridica(personaJuridica);

			
			//solicitudInscripcion.setCapital(capital);
			
			//solicitudInscripcion.setReservaMercantil(reservaMercantil);
			
			
		
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
			
			req.setAttribute("codigoNotaria", solicitudInscripcion.getPresentante().getCodigoInstitucion());
			req.setAttribute("descripcionNotaria", solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			req.setAttribute("codRepr", solicitudInscripcion.getPresentante().getCodigoPresentante());
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento()));
			req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento(), solicitudInscripcion.getPresentante().getCodigoProvincia()));
			req.setAttribute("oficRegId", solicitudInscripcion.getCodigoOficinaRegistral());
			req.setAttribute("regPubId", solicitudInscripcion.getCodigoZonaRegistral());
			req.setAttribute("acto", solicitudInscripcion.getCodigoArea() + "|" + solicitudInscripcion.getCodigoLibro() + "|" + solicitudInscripcion.getCodigoActo());
			
			response.setStyle("datosGenerales");

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

			
	protected ControllerResponse runRegresarASolicitudInscripcion4State(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		DatosUsuarioBean datosUsuarioBean = null;
		Presentante presentante = null;
		SolicitudInscripcion solicitudInscripcion = null;
		//PersonaJuridica personaJuridica = null;
		//Capital capital = null;
		InstrumentoPublico instrumentoPublico = null;		
		//ReservaMercantil reservaMercantil = null;
		EscrituraPublica escrituraPublica = null;				

		try {

			init(request);
			validarSesion(request);

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
			
			/*capital = solicitudInscripcion.getCapital();
			if (capital==null)
				capital = new Capital();
			*/
			/*reservaMercantil = solicitudInscripcion.getReservaMercantil();
			if (reservaMercantil==null)
				reservaMercantil = new ReservaMercantil();
			*/
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
                    }
                    //in.close();
                }
            }

			
				
			//solicitudInscripcion.setPersonaJuridica(personaJuridica);

			
			//solicitudInscripcion.setCapital(capital);
			
			//solicitudInscripcion.setReservaMercantil(reservaMercantil);
			
			
		
			/** codigoTipoInstrumento **/
			instrumentoPublico.setCodigoTipoInstrumento("021");

			/** descripcionTipoInstrumento **/
			instrumentoPublico.setDescripcionTipoInstrumento("ACTA NOTARIAL DE TRANSFERENCIA VEHICULAR");

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
			
			req.setAttribute("codigoNotaria", solicitudInscripcion.getPresentante().getCodigoInstitucion());
			req.setAttribute("descripcionNotaria", solicitudInscripcion.getPresentante().getDescripcionInstitucion());
			req.setAttribute("codRepr", solicitudInscripcion.getPresentante().getCodigoPresentante());
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento()));
			req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, solicitudInscripcion.getPresentante().getCodigoDepartamento(), solicitudInscripcion.getPresentante().getCodigoProvincia()));
			req.setAttribute("oficRegId", solicitudInscripcion.getCodigoOficinaRegistral());
			req.setAttribute("regPubId", solicitudInscripcion.getCodigoZonaRegistral());
			req.setAttribute("acto", solicitudInscripcion.getCodigoArea() + "|" + solicitudInscripcion.getCodigoLibro() + "|" + solicitudInscripcion.getCodigoActo());
			
			response.setStyle("datosGenerales");

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
	
	
	protected ControllerResponse runObtenerPresentanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
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
	
			// Obtiene Notaria
			String codNotaria = (String)request.getParameter("hidCodNotaria");
			String nomNotaria = (String)request.getParameter("hidNomNotaria");
			String codRepr = (String)request.getParameter("cboPresentante");
			
			req.setAttribute("codigoNotaria", codNotaria);
			req.setAttribute("descripcionNotaria", nomNotaria);
			req.setAttribute("codRepr", codRepr);

			// Obtiene Tipos de Documento
			session.setAttribute("arrDocu", (ArrayList)Tarea.getComboTiposDocumento(dconn));
				
			DboTARepr dboTaRepr = new DboTARepr(dconn);
			dboTaRepr.setFieldsToRetrieve(DboTARepr.CAMPO_AP_MATE_REPR + "|" +
									      DboTARepr.CAMPO_AP_PATE_REPR + "|" +
										  DboTARepr.CAMPO_CO_DEPA + "|" +
										  DboTARepr.CAMPO_CO_DIST + "|" +
										  DboTARepr.CAMPO_CO_PAIS + "|" +
										  DboTARepr.CAMPO_CO_PROV + "|" +
										  DboTARepr.CAMPO_CO_REPR + "|" +
										  DboTARepr.CAMPO_CO_TIPO_DOCU + "|" +
										  DboTARepr.CAMPO_DE_MAIL + "|" +
									      DboTARepr.CAMPO_NO_DIRE + "|" +
										  DboTARepr.CAMPO_NO_MAIL + "|" +
										  DboTARepr.CAMPO_NO_REPR + "|" +
										  DboTARepr.CAMPO_NU_DOCU + "|" +
										  DboTARepr.CAMPO_DE_MAIL);									  
				dboTaRepr.setField(DboTARepr.CAMPO_IN_ESTD, "A");
				dboTaRepr.setField(DboTARepr.CAMPO_CO_NTIA, codNotaria);
				dboTaRepr.setField(DboTARepr.CAMPO_CO_REPR, codRepr);
				Presentante presentante = null;
				ArrayList resultado = new ArrayList();
				java.util.ArrayList arr2 = dboTaRepr.searchAndRetrieveList(DboTARepr.CAMPO_CO_REPR);

				if (dboTaRepr.find() == true) {
					presentante = new Presentante();
					presentante.setCodigoInstitucion(codNotaria);
					presentante.setDescripcionInstitucion(nomNotaria);
					presentante.setApellidoMaterno(dboTaRepr.getField(DboTARepr.CAMPO_AP_MATE_REPR));
					presentante.setApellidoPaterno(dboTaRepr.getField(DboTARepr.CAMPO_AP_PATE_REPR));
					presentante.setCodigoPais(dboTaRepr.getField(DboTARepr.CAMPO_CO_PAIS));
					presentante.setCodigoDepartamento(dboTaRepr.getField(DboTARepr.CAMPO_CO_DEPA));
					presentante.setCodigoProvincia(dboTaRepr.getField(DboTARepr.CAMPO_CO_PROV));
					presentante.setCodigoDistrito(dboTaRepr.getField(DboTARepr.CAMPO_CO_DIST));
					presentante.setCodigoPostal("");//NO EXISTE
					presentante.setCodigoPresentante(dboTaRepr.getField(DboTARepr.CAMPO_CO_REPR));
					presentante.setCodigoTipoDocumento(dboTaRepr.getField(DboTARepr.CAMPO_CO_TIPO_DOCU));
					presentante.setCodigoTipoVia("");//NO EXISTE
					presentante.setCorreoElectronico(dboTaRepr.getField(DboTARepr.CAMPO_DE_MAIL));
					presentante.setDescripcionPais("");
					presentante.setDescripcionDepartamento("");
					presentante.setDescripcionProvincia("");
					presentante.setDescripcionDistrito("");
					presentante.setDescripcionTipoDocumento(dboTaRepr.getField(DboTARepr.CAMPO_CO_TIPO_DOCU));
					presentante.setDescripcionTipoVia("");//NO EXISTE
					presentante.setDireccion(dboTaRepr.getField(DboTARepr.CAMPO_NO_DIRE));
					presentante.setNombre(dboTaRepr.getField(DboTARepr.CAMPO_NO_REPR));
					presentante.setNumeroDocumento(dboTaRepr.getField(DboTARepr.CAMPO_NU_DOCU));
				}
				
				session.setAttribute("presentante",presentante);

				req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
				System.out.println("dep::"+presentante.getCodigoDepartamento());
				req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, presentante.getCodigoDepartamento()));
				System.out.println("prov::"+presentante.getCodigoProvincia());
				req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, presentante.getCodigoDepartamento(), presentante.getCodigoProvincia()));
				System.out.println("dist::"+presentante.getCodigoDistrito());


				Zona zona = new Zona();
				zona.setConn(dconn);
				//zona.setUsuario(usuario);
				zona.setPaisId(presentante.getCodigoPais());
				zona.setDepartamentoId(presentante.getCodigoDepartamento());
				zona.setProvinciaId(presentante.getCodigoProvincia());
				zona.calculaZona();
				req.setAttribute("oficRegId", zona.getOficRegId());
				req.setAttribute("regPubId", zona.getRegPubId());
			
				String codActo = (String)request.getParameter("cboActo");
				req.setAttribute("acto", codActo);
						
				response.setStyle("datosGenerales");
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
		DatosUsuarioBean datosUsuarioBean = null;
		
		try {	
					
			init(request);
			validarSesion(request);

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
	
			// Obtiene Notaria
			String codNotaria = (String)request.getParameter("hidCodNotaria");
			String nomNotaria = (String)request.getParameter("hidNomNotaria");
			String codRepr = (String)request.getParameter("cboPresentante");
			
			req.setAttribute("codigoNotaria", codNotaria);
			req.setAttribute("descripcionNotaria", nomNotaria);
			req.setAttribute("codRepr", codRepr);

			System.out.println("codDepartamento::"+request.getParameter("cboEnvDpto"));
			String codDepartamento = ((String)request.getParameter("cboEnvDpto")).substring(3,5);
			System.out.println("codDepartamento::"+codDepartamento);
			req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, codDepartamento));

			//Obtenemos datos del usuario que se loguea
			//datosUsuarioBean = Tarea.getDatosUsuario(dconn,bean.getUserId());
			datosUsuarioBean = (DatosUsuarioBean)session.getAttribute("DATOS_FORMULARIO");
			datosUsuarioBean.setDepartamento(codDepartamento);
			datosUsuarioBean.setProvincia("");
			datosUsuarioBean.setDistrito("");
			
			//req.setAttribute("DATOS_FORMULARIO", datosUsuarioBean);

			Zona zona = new Zona();
			zona.setConn(dconn);
			//zona.setUsuario(usuario);
			zona.setPaisId(datosUsuarioBean.getPais());
			zona.setDepartamentoId(datosUsuarioBean.getDepartamento());
			zona.setProvinciaId(datosUsuarioBean.getProvincia());
			zona.calculaZona();
			req.setAttribute("oficRegId", zona.getOficRegId());
			req.setAttribute("regPubId", zona.getRegPubId());

			String codActo = (String)request.getParameter("cboActo");
			req.setAttribute("acto", codActo);
		
			response.setStyle("datosGenerales");
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
		DatosUsuarioBean datosUsuarioBean = null;
		
		try {	
					
			init(request);
			validarSesion(request);

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
	
			// Obtiene Notaria
			String codNotaria = (String)request.getParameter("hidCodNotaria");
			String nomNotaria = (String)request.getParameter("hidNomNotaria");
			String codRepr = (String)request.getParameter("cboPresentante");
			
			req.setAttribute("codigoNotaria", codNotaria);
			req.setAttribute("descripcionNotaria", nomNotaria);
			req.setAttribute("codRepr", codRepr);

			String codDepartamento = ((String)request.getParameter("cboEnvDpto")).substring(3,5);
			System.out.println("codDepartamento::"+codDepartamento);
			String codProvincia = ((String)request.getParameter("cboEnvProv")).substring(6,8);
			System.out.println("codProvincia::"+codProvincia);
						req.setAttribute("arr3", Tarea.getComboDepartamentos(dconn));
			req.setAttribute("arr_hijo1", Tarea.getComboProvincias(dconn, codDepartamento));
			if (!codProvincia.equals("  "))
				req.setAttribute("arr_hijo2", Tarea.getComboDistritos(dconn, codDepartamento, codProvincia));
							
			//Obtenemos datos del usuario que se loguea
			//datosUsuarioBean = Tarea.getDatosUsuario(dconn,bean.getUserId());
			datosUsuarioBean = (DatosUsuarioBean)session.getAttribute("DATOS_FORMULARIO");
			datosUsuarioBean.setDepartamento(codDepartamento);
			datosUsuarioBean.setProvincia(codProvincia);
			datosUsuarioBean.setDistrito("");

			//req.setAttribute("DATOS_FORMULARIO", datosUsuarioBean);

			Zona zona = new Zona();
			zona.setConn(dconn);
			//zona.setUsuario(usuario);
			zona.setPaisId(datosUsuarioBean.getPais());
			zona.setDepartamentoId(datosUsuarioBean.getDepartamento());
			zona.setProvinciaId(datosUsuarioBean.getProvincia());
			zona.calculaZona();
			req.setAttribute("oficRegId", zona.getOficRegId());
			req.setAttribute("regPubId", zona.getRegPubId());
					
			response.setStyle("datosGenerales");
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

