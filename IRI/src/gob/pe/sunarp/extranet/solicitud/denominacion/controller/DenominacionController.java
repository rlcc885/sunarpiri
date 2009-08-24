/**
 * 
 */
package gob.pe.sunarp.extranet.solicitud.denominacion.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.net.nt.ConnStrategy;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.dbobj.DboActosTitulo;
import gob.pe.sunarp.extranet.dbobj.DboDetalleTitulo;
import gob.pe.sunarp.extranet.dbobj.DboExEntidadDist;
import gob.pe.sunarp.extranet.dbobj.DboMensaje;
import gob.pe.sunarp.extranet.dbobj.DboMensajeMail;
import gob.pe.sunarp.extranet.dbobj.DboParticLibro;
import gob.pe.sunarp.extranet.dbobj.DboReciboTitulo;
import gob.pe.sunarp.extranet.dbobj.DboTATabl;
import gob.pe.sunarp.extranet.dbobj.DboTATituActo;
import gob.pe.sunarp.extranet.dbobj.DboTPHojaPres;
import gob.pe.sunarp.extranet.dbobj.DboTTBloqPartida;
import gob.pe.sunarp.extranet.dbobj.DboTTPago;
import gob.pe.sunarp.extranet.dbobj.DboTTPersJuriTitu;
import gob.pe.sunarp.extranet.dbobj.DboTTPersNatuTitu;
import gob.pe.sunarp.extranet.dbobj.DboTaCaja;
import gob.pe.sunarp.extranet.dbobj.DboTarifa;
import gob.pe.sunarp.extranet.dbobj.DboTitulo;
import gob.pe.sunarp.extranet.dbobj.DboTmActo;
import gob.pe.sunarp.extranet.dbobj.DboTmAreaRegistral;
import gob.pe.sunarp.extranet.dbobj.DboTmDepartamento;
import gob.pe.sunarp.extranet.dbobj.DboTmEstadoTitulo;
import gob.pe.sunarp.extranet.dbobj.DboTmLibro;
import gob.pe.sunarp.extranet.dbobj.DboTmProvincia;
import gob.pe.sunarp.extranet.dbobj.DboTmServicio;
import gob.pe.sunarp.extranet.dbobj.DboTransaccion;
import gob.pe.sunarp.extranet.dbobj.DboVwDiariorecauda;
import gob.pe.sunarp.extranet.dbobj.DboVwHojasPresentadas;
import gob.pe.sunarp.extranet.dbobj.DboVwHojasTrabajadas;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.reportegeneral.bean.ConsolaCentral;
import gob.pe.sunarp.extranet.reportegeneral.bean.OficinaConection;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.Juridica;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.Participantes;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.Presentante;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.RazonSocial;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.ReportesBean;
import gob.pe.sunarp.extranet.solicitud.inscripcion.SecuenciaSolicitud;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaCertificadoBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Tarea;
/**
 * @author jbugarin
 *
 */
public class DenominacionController extends ControllerExtension {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DenominacionController() {
		// TODO Auto-generated constructor stub
		super();
		addState(new State("muestraForm", "Muestra el form inicial para el ingreso de datos"));
		addState(new State("muestraForm2", "Muestra el 2do form inicial para el ingreso de datos"));
		addState(new State("comprobante", "Muestra el comprobante de pago para usuarios registrados"));
		addState(new State("resumen", "Muestra el resumen de todo el proceso realizado usuarios registrados"));
		addState(new State("nuevoParticipante", "Muestra el formulario de ingreso de datos de participantes"));
		addState(new State("borrarParticipante", "Borra al participante ingresado"));
		addState(new State("grabarParticipante", "Graba el participante"));
		addState(new State("cancelarParticipante", "Borra al participante"));
		addState(new State("regresarMuestraForm", "regresa al form principal de denominaciones"));
		addState(new State("consultaResumen", "url invocada desde la oficina para la consulta del resumen"));
		addState(new State("muestraFiltroReporte", " muestra el jsp con el filtro para generar una hoja de resultados"));
		addState(new State("verReportes", " metodo para construir el reporte de hojas presentadas y trabajadas"));
		addState(new State("consultaEstadoSolic", "metodo que busca por año y hoja de presentacion mostrando detalles del titulo"));
		
		setInitialState("muestraForm");
	}
	
	protected ControllerResponse runMuestraFormState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		try{
			session.removeAttribute("solicitudDenominacion");
			Denominacion deno = new Denominacion();
			
			if(request.getParameter("publ")!=null){
				if ("p".equals(request.getParameter("publ"))){//INDICA QUE NO ES USUARIO REGISTRADO
					deno.setIndicadorRegistrado("N");
					deno.setCodigoUsuario("<ANONIMO>");
				}
					
				
			}else{
				deno.setIndicadorRegistrado("S");
			}
			
			session.setAttribute("solicitudDenominacion", deno);
			response.setStyle("muestraForm");
		}
		/*catch (CustomException e) {
			//log(e.getCodigoError(), e.getMessage(), request);
			//principal(request);
			//rollback(conn, request);
			//response.setStyle("error");
		} */
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			//JDBC.getInstance().closeResultSet(rsetGla);
			//JDBC.getInstance().closeStatement(pstmt);
			//pool.release(conn);
			end(request);
		}
		return response;
		
	}
	
protected ControllerResponse runMuestraForm2State(ControllerRequest request, ControllerResponse response) throws ControllerException {
		
	//HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	HttpSession session = ExpressoHttpSessionBean.getSession(request);
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
	
	try{
			
		Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion");	
		if (deno.getIndicadorRegistrado().equals("S")){//usuario registrado
			init(request);
			validarSesion(request);
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			deno.setCodigoUsuario(usuario.getUserId());
			deno.setUsuario(usuario);
		}	
		
			conn = pool.getConnection();
			DBConnection dconn = new DBConnection(conn);
			//Denominacion deno = new Denominacion();
			
			Juridica juri = new Juridica();
			ArrayList<RazonSocial> listaDenominaciones = new ArrayList<RazonSocial>();
			
			deno.setServicio(Constantes.SERVICIORESERVADENOMINACION); // el valor del servicio siempre es 197 para reservas de denominacion
			//consultando la descripcion del servicio
			DboTmServicio tmServicio = new DboTmServicio(dconn);
			tmServicio.setFieldsToRetrieve(DboTmServicio.CAMPO_NOMBRE);
			tmServicio.setField(DboTmServicio.CAMPO_SERVICIO_ID, deno.getServicio());
			if(tmServicio.find()==true){
				deno.setDescServicio(tmServicio.getField(DboTmServicio.CAMPO_NOMBRE));
			}
			if("01".equals(request.getParameter("constitucion")) ){
				deno.setIndicadorSeleccion(request.getParameter("constitucion"));
				deno.setDescSeleccion("CONSTITUCION");
				//01 selecciono constitucion
			}
			if ("02".equals(request.getParameter("modificacion"))){
				deno.setIndicadorSeleccion(request.getParameter("modificacion"));
				deno.setDescSeleccion("MODIFICACION");
				//02 selecciono modificacion
			}
			if("01".equals(request.getParameter("denominacion") )){
				deno.setInidcadorDenominacion(request.getParameter("denominacion"));
				//01 selecciono denominacion y tambien se habilitan los campos de denominacion abrev.
				for (int i = 1;i<=5;i++){
					RazonSocial raz = new RazonSocial();
					String parametro = "deno" + String.valueOf(i);
					String param = "denoAbre" + String.valueOf(i);
					raz.setDenominacion(request.getParameter(parametro) );
					raz.setDenoAbrev(request.getParameter(param) );
					raz.setOrden(""+i);
					listaDenominaciones.add(raz);
					
				}
			}
			if ("02".equals(request.getParameter("razonSoc"))){
				deno.setInidcadorDenominacion(request.getParameter("razonSoc"));
				//02 selecciono razon social solo se leen los de modificacion
				for (int i = 1;i<=5;i++){
					RazonSocial raz = new RazonSocial();
					String parametro = "Raz" + String.valueOf(i);
					raz.setDenominacion(request.getParameter(parametro) );
					raz.setDenoAbrev("");
					listaDenominaciones.add(raz);
					
				}
			}
			
			deno.setListaDenominaciones(listaDenominaciones);
			juri.setRazonSocial(request.getParameter("nomModif"));
			if (!request.getParameter("partida").equals("") && request.getParameter("partida")!=null)
			juri.setPartida(request.getParameter("partida"));
			else
				juri.setPartida("N1");
			juri.setFicha(request.getParameter("ficha"));
			juri.setTipo(request.getParameter("cboTipoSociedad"));
			juri.setDepartamento(request.getParameter("cboDepartamento"));
			juri.setProvincia(request.getParameter("cboProvincia").substring(2, 4));
			//consultar a tm_provincia
			DboTmProvincia dboProv = new DboTmProvincia(dconn);
			dboProv.setFieldsToRetrieve(DboTmProvincia.CAMPO_REG_PUB_ID +"|"+ DboTmProvincia.CAMPO_OFIC_REG_ID
					+"|"+ DboTmProvincia.CAMPO_NOMBRE);
			dboProv.setField(DboTmProvincia.CAMPO_DPTO_ID, juri.getDepartamento());
			dboProv.setField(DboTmProvincia.CAMPO_PROV_ID, juri.getProvincia());
			if(dboProv.find()==true){
				deno.setCoRegiPres(dboProv.getField(DboTmProvincia.CAMPO_REG_PUB_ID));//relacionar de donde vienen
				deno.setCoOficRegiPres(dboProv.getField(DboTmProvincia.CAMPO_OFIC_REG_ID));
				juri.setDescProv(dboProv.getField(DboTmProvincia.CAMPO_NOMBRE));
			}else{
				throw new Exception("NO ENCONTRADO EN TM_PROVINCIA");
			}
			//consultando el nombre del departamento
			DboTmDepartamento dboDpto = new DboTmDepartamento(dconn);
			dboDpto.setFieldsToRetrieve(DboTmDepartamento.CAMPO_NOMBRE);
			dboDpto.setField(DboTmDepartamento.CAMPO_DPTO_ID, juri.getDepartamento());
			if(dboDpto.find()==true){
				juri.setDescDepto(dboDpto.getField(DboTmDepartamento.CAMPO_NOMBRE));				
			}
			else{
				throw new Exception("NO ENCONTRADO EN TM_DEPARTAMENTO");
			}
			deno.setPersonaJuridica(juri);
			deno.setCodigoArea(Constantes.AREA_PERSONA_JURIDICA);
			//consultando el area 
			DboTmAreaRegistral tmArea = new DboTmAreaRegistral(dconn);
			tmArea.setFieldsToRetrieve(DboTmAreaRegistral.CAMPO_NOMBRE);
			tmArea.setField(DboTmAreaRegistral.CAMPO_AREA_REG_ID, deno.getCodigoArea());
			if(tmArea.find()==true){
			deno.setDescripcionArea(tmArea.getField(DboTmAreaRegistral.CAMPO_NOMBRE));
			}
			
			deno.setCodigoActo(juri.getTipo().substring(0, 5));
			//consultando la descripcion del acto
			DboTmActo tmActo = new DboTmActo(dconn);
			tmActo.setFieldsToRetrieve(DboTmActo.CAMPO_DESCRIPCION);
			tmActo.setField(DboTmActo.CAMPO_COD_ACTO, deno.getCodigoActo());
			if (tmActo.find()==true){
				deno.setDescActo(tmActo.getField(DboTmActo.CAMPO_DESCRIPCION));
			}
			deno.setCodigoLibro(juri.getTipo().substring(5, 8));
			//consultando la descripcion del libro
			DboTmLibro tmLibro = new DboTmLibro(dconn);
			tmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
			tmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO, deno.getCodigoLibro());
			if(tmLibro.find()==true){
				deno.setDescLibro(tmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));
			}
			//consultar el costo por el tramite
			DboTarifa tarifa = new DboTarifa(dconn);
			tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, deno.getServicio());
			if (tarifa.find()==true){
			deno.setMonto(Double.valueOf(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC)));
			}
			juri.setDescTipo(deno.getDescLibro());
			
			//pongo en session la denominacion
			session.setAttribute("solicitudDenominacion", deno);
			response.setStyle("muestraForm2");
		}
		/*catch (CustomException e) {
			//log(e.getCodigoError(), e.getMessage(), request);
			//principal(request);
			//rollback(conn, request);
			//response.setStyle("error");
		} */
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			//JDBC.getInstance().closeResultSet(rsetGla);
			//JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		return response;
		
	}

protected ControllerResponse runComprobanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
	HttpSession session = ExpressoHttpSessionBean.getSession(request);
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request); 
	DBConnectionFactory pool = null;
	Connection conn = null;
	Connection conn2 = null;
	DBConnection myConn = null;
	DBConnection myConn2 = null;
	OficinaConection oficDB = null;
	
	try{
		pool = DBConnectionFactory.getInstance();
		conn = pool.getConnection();
	   	myConn = new DBConnection(conn);
	   	myConn.setAutoCommit(false);
		Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion");
		Presentante pres = new Presentante();
		pres.setNombre(request.getParameter("nombres"));
		pres.setApePaterno(request.getParameter("apePaterno"));
		pres.setApeMaterno(request.getParameter("apeMaterno"));
		pres.setTipoDocu(request.getParameter("cboTipoDocu"));
		//consultando los tipos de documento activos
		DboTATabl taTabl = new DboTATabl(myConn);
		taTabl.setFieldsToRetrieve(DboTATabl.CAMPO_DE_VALO);
		taTabl.setField(DboTATabl.CAMPO_VA_COLU, pres.getTipoDocu());
		taTabl.setField(DboTATabl.CAMPO_CO_COLU, "TI_DOCU_IDEN");
		taTabl.setField(DboTATabl.CAMPO_IN_ESTD, "A");
		if(taTabl.find()==true){
			pres.setDescDocu(taTabl.getField(DboTATabl.CAMPO_DE_VALO));
		}
		pres.setNumDocu(request.getParameter("numDocu"));
		pres.setParticipacion(request.getParameter("cboParticipacion"));
		
		pres.setDescParticipacion(tipoParticipantes(pres.getParticipacion()));
		
		pres.setEmail(request.getParameter("email"));
		if (request.getParameter("direccion")!=null && !request.getParameter("direccion").equals(""))
		pres.setDireccion(request.getParameter("direccion"));
		else
			pres.setDireccion("");
		deno.setPresentante(pres);
		deno.setNumeroHoja(SecuenciaSolicitud.obtieneSecuencia());
		deno.setCuo(deno.getNumeroHoja());
		deno.setAnio((FechaUtil.getCurrentDateYYYYMMDD()).substring(0,4));
		String fecha_actual = FechaUtil.getCurrentDateTime();
		String fecha_hora = fecha_actual.substring(0,2)+
					 fecha_actual.substring(3,5)+
					 fecha_actual.substring(6,10)+
					 fecha_actual.substring(11,13)+
					 fecha_actual.substring(14,16)+
					 fecha_actual.substring(17,19);
		deno.setFechaProceso(fecha_hora);
		DatosPago datosPago = new DatosPago();
		datosPago = new DatosPago();
		//si es anonimo debe de ser 04
		if(deno.getIndicadorRegistrado().equals("S")){
		datosPago.setCodigoFormaPago(Constantes.FRMPAGOUSUARIO);
		datosPago.setDescripcionFormaPago(Constantes.DESCFRMPAGOUSUARIO);
		}else{
			datosPago.setCodigoFormaPago(Constantes.FRMPAGOANONIMO);
		datosPago.setDescripcionFormaPago(Constantes.DESCFRMPAGOANONIMO);
		}
		datosPago.setCostoTotalServicio(new java.math.BigDecimal(deno.getMonto()));
		datosPago.setCodigoTipoPago(Constantes.CODIGOTIPOPAGODENOMINACION);
		datosPago.setDescripcionTipoPago(Constantes.DESCTIPOPAGODENOMINACION);
		datosPago.setNumeroOperacion(null);
		datosPago.setFechaPago(FechaUtil.getCurrentDateYYYYMMDD());
		datosPago.setHoraPago(FechaUtil.getCurrentDateTime().substring(11,13)+
							  FechaUtil.getCurrentDateTime().substring(14,16)+
							  FechaUtil.getCurrentDateTime().substring(17,19));
		deno.setDatosPago(datosPago);
		session.setAttribute("solicitudDenominacion", deno);
		req.setAttribute("denominacion", deno);
		req.setAttribute("datosPago", datosPago);
		req.setAttribute("presentante", deno.getPresentante());
		//registrando en extranet
		String nuOperacion="";
		if (deno.getIndicadorRegistrado().equals("S")){
			PrepagoBean pre = new PrepagoBean();
			System.out.println("INICIANDO EL DESCUENTO DE SALDO");
			nuOperacion = descuentoSaldo(myConn, pre, deno);
			System.out.println("FINALIZADO EL DESCUENTO DE SALDO");
			registraSolicitudDenominacion(deno, myConn,nuOperacion);
			
		}else{
			registraSolicitudDenominacion(deno, myConn,nuOperacion);
		}
		//HACIA LA OFICINA
		
		/*oficDB = (OficinaConection) ConsolaCentral.getDbOficinas().get("01"+"01");
		System.out.println("Conexión de la oficina Central::"+oficDB);
		Class.forName("oracle.jdbc.driver.OracleDriver");
        conn2 = DriverManager.getConnection(oficDB.getUrl(), oficDB.getUser(), oficDB.getPassword());
		conn2.setAutoCommit(false);
		myConn2 = new DBConnection(conn2);
		//registrando en oficina de lima
		//registraSolicitudDenominacionOficina(deno, myConn2, nuOperacion);*/
		String url="";
		if (deno.getIndicadorRegistrado().equals("S")){
			url="comprobante";
		}
		if (deno.getIndicadorRegistrado().equals("N")){
			
			req.setAttribute("denominacion", deno);
			req.setAttribute("denoRazSoc", deno.getListaDenominaciones());
			req.setAttribute("presentante", deno.getPresentante());
			req.setAttribute("juridica", deno.getPersonaJuridica());
			
			if (deno.getListaParticipantes()!=null){
				req.setAttribute("participantes", deno.getListaParticipantes());
			}else{
			Participantes part = new Participantes();
			ArrayList<Participantes> arreglo = new ArrayList<Participantes>();
			part.setApeMaterno("");
			part.setApePaterno("");
			part.setNombre("");
			part.setNumDocu("");
			part.setRazonSocial("");
			part.setTipoDocu("");
			part.setDescDocu("");
			arreglo.add(part);
			deno.setListaParticipantes(arreglo);
			req.setAttribute("participantes", deno.getListaParticipantes());
			}	
			req.setAttribute("indicadorTitulo", "no");
			url="resumenImpresion";
		}
		response.setStyle(url);
	}
	/*catch (CustomException e) {
		//log(e.getCodigoError(), e.getMessage(), request);
		//principal(request);
		//rollback(conn, request);
		//response.setStyle("error");
	} */
	catch (Throwable ex) {
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		rollback(conn, request);
		rollback(conn2, request);
		response.setStyle("error");
	}finally{
		//JDBC.getInstance().closeResultSet(rsetGla);
		//JDBC.getInstance().closeStatement(pstmt);
		pool.release(conn);
		pool.release(conn2);
		end(request);
	}
	return response;
	
}

protected ControllerResponse runResumenState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
	HttpSession session = ExpressoHttpSessionBean.getSession(request);
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	try{
		//System.out.println("JLBP::" + request.getParameter("constitucion"));
		Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion");
		req.setAttribute("denominacion", deno);
		req.setAttribute("denoRazSoc", deno.getListaDenominaciones());
		req.setAttribute("presentante", deno.getPresentante());
		req.setAttribute("juridica", deno.getPersonaJuridica());
		
		if (deno.getListaParticipantes()!=null){
			req.setAttribute("participantes", deno.getListaParticipantes());
		}else{
		Participantes part = new Participantes();
		ArrayList<Participantes> arreglo = new ArrayList<Participantes>();
		part.setApeMaterno("");
		part.setApePaterno("");
		part.setNombre("");
		part.setNumDocu("");
		part.setRazonSocial("");
		part.setTipoDocu("");
		part.setDescDocu("");
		arreglo.add(part);
		deno.setListaParticipantes(arreglo);
			
		req.setAttribute("participantes", deno.getListaParticipantes());
		}
		req.setAttribute("indicadorTitulo", "no");
		response.setStyle("resumenImpresion");
		
	}
	/*catch (CustomException e) {
		//log(e.getCodigoError(), e.getMessage(), request);
		//principal(request);
		//rollback(conn, request);
		//response.setStyle("error");
	} */
	catch (Throwable ex) {
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		//rollback(conn, request);
		response.setStyle("error");
	}finally{
		//JDBC.getInstance().closeResultSet(rsetGla);
		//JDBC.getInstance().closeStatement(pstmt);
		//pool.release(conn);
		end(request);
	}
	return response;
	
}

protected ControllerResponse runNuevoParticipanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
	try{
		
		response.setStyle("nuevoParticipante");
	}
	/*catch (CustomException e) {
		//log(e.getCodigoError(), e.getMessage(), request);
		//principal(request);
		//rollback(conn, request);
		//response.setStyle("error");
	} */
	catch (Throwable ex) {
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		//rollback(conn, request);
		response.setStyle("error");
	}finally{
		//JDBC.getInstance().closeResultSet(rsetGla);
		//JDBC.getInstance().closeStatement(pstmt);
		//pool.release(conn);
		end(request);
	}
	return response;
	
}



protected ControllerResponse runCancelarParticipanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	HttpSession session = ExpressoHttpSessionBean.getSession(request);
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	try{
		req.setAttribute("indicaParticipantes", "si");
		response.setStyle("muestraForm2");
	}
	/*catch (CustomException e) {
		//log(e.getCodigoError(), e.getMessage(), request);
		//principal(request);
		//rollback(conn, request);
		//response.setStyle("error");
	} */
	catch (Throwable ex) {
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		//rollback(conn, request);
		response.setStyle("error");
	}finally{
		//JDBC.getInstance().closeResultSet(rsetGla);
		//JDBC.getInstance().closeStatement(pstmt);
		//pool.release(conn);
		end(request);
	}
	return response;
	
}

protected ControllerResponse runGrabarParticipanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
	HttpSession session = ExpressoHttpSessionBean.getSession(request);
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	try{
		
		//ArrayList arreglo = new ArrayList();
		Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion");
		ArrayList<Participantes> listaParticipantes = deno.getListaParticipantes();
		
		if (listaParticipantes==null){
			listaParticipantes = new ArrayList<Participantes>();
			Participantes participante = new Participantes();
			//validando si selecciono dni o ruc
			if ("dni".equals(request.getParameter("dni"))){
				participante.setNumDocu(request.getParameter("numDocu"));
				String tmp = request.getParameter("cboTipoDocu");
				participante.setTipoDocu(tmp.substring(0,2));
				participante.setDescDocu(tmp.substring(2));
				participante.setNombre(request.getParameter("nombres"));
				participante.setApePaterno(request.getParameter("apePaterno"));
				participante.setApeMaterno(request.getParameter("apeMaterno"));
			}
			if ("ruc".equals(request.getParameter("ruc"))){
				participante.setTipoDocu(request.getParameter("ruc").toUpperCase());
				participante.setDescDocu("RUC");
				participante.setNumDocu(request.getParameter("txtRuc"));
				participante.setRazonSocial(request.getParameter("razonSoc"));
			}
			listaParticipantes.add(participante);
			deno.setListaParticipantes(listaParticipantes);
		}else{
			listaParticipantes = new ArrayList<Participantes>();
			for (int i = 0;i<deno.getListaParticipantes().size();i++){
				Participantes part = new Participantes();
				part = (Participantes)deno.getListaParticipantes().get(i);
			listaParticipantes.add(part);
			}
			if ("dni".equals(request.getParameter("dni"))){
				Participantes participante = new Participantes();
				String tmp = request.getParameter("cboTipoDocu");
				participante.setTipoDocu(tmp.substring(0,2));
				participante.setDescDocu(tmp.substring(2));
				participante.setNumDocu(request.getParameter("numDocu"));
				participante.setNombre(request.getParameter("nombres"));
				participante.setApePaterno(request.getParameter("apePaterno"));
				participante.setApeMaterno(request.getParameter("apeMaterno"));
				listaParticipantes.add(participante);
			}
			if ("ruc".equals(request.getParameter("ruc"))){
				Participantes participante = new Participantes();
				participante.setDescDocu(request.getParameter("ruc").toUpperCase());
				participante.setTipoDocu(request.getParameter("ruc").toUpperCase());
				participante.setNumDocu(request.getParameter("txtRuc"));
				participante.setRazonSocial(request.getParameter("razonSoc"));
				listaParticipantes.add(participante);
			}
			deno.setListaParticipantes(listaParticipantes);
		}
		session.setAttribute("solicitudDenominacion", deno);				
		req.setAttribute("indicaParticipantes", "si");
		response.setStyle("muestraForm2");
	}
	/*catch (CustomException e) {
		//log(e.getCodigoError(), e.getMessage(), request);
		//principal(request);
		//rollback(conn, request);
		//response.setStyle("error");
	} */
	catch (Throwable ex) {
		System.out.println("eeee");
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		//rollback(conn, request);
		response.setStyle("error");
	}finally{
		//JDBC.getInstance().closeResultSet(rsetGla);
		//JDBC.getInstance().closeStatement(pstmt);
		//pool.release(conn);
		end(request);
	}
	return response;
	
}

protected ControllerResponse runBorrarParticipanteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
	HttpSession session = ExpressoHttpSessionBean.getSession(request);
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	
	try{
		Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion");
		
		
		ArrayList arr = new ArrayList();
		ArrayList<Participantes> listaParticipantes = new ArrayList<Participantes>();
		//elimino el seleccionado
		//if(deno.getListaParticipantes().size()>0){
			String indice = request.getParameter("indicesListaParticipante");
			arr= deno.getListaParticipantes();
			//arr.remove(Integer.parseInt(indice)-1);
			for (int i = 0;i<arr.size();i++){
				if(i==(Integer.parseInt(indice)-1)){
					
				}else{ 
					//int j = 0;
					Participantes part = new Participantes();
					part = (Participantes)arr.get(i);
					listaParticipantes.add(part);
				}
			}
			//deno.setListaParticipantes(arr);
		//}
		deno.setListaParticipantes(listaParticipantes);
		session.setAttribute("solicitudDenominacion", deno);
		req.setAttribute("indicaParticipantes", "si");
		response.setStyle("muestraForm2");
	}
	/*catch (CustomException e) {
		//log(e.getCodigoError(), e.getMessage(), request);
		//principal(request);
		//rollback(conn, request);
		//response.setStyle("error");
	} */
	catch (Throwable ex) {
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		//rollback(conn, request);
		response.setStyle("error");
	}finally{
		//JDBC.getInstance().closeResultSet(rsetGla);
		//JDBC.getInstance().closeStatement(pstmt);
		//pool.release(conn);
		end(request);
	}
	return response;
	
}

protected ControllerResponse runRegresarMuestraFormState(ControllerRequest request, ControllerResponse response) throws ControllerException {
	
	HttpSession session = ExpressoHttpSessionBean.getSession(request);
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	
	try{
		Denominacion deno = (Denominacion)session.getAttribute("solicitudDenominacion");
		Juridica juri = deno.getPersonaJuridica();
		if (juri.getPartida().equals("N1"))
			juri.setPartida("");
		
		deno.setPersonaJuridica(juri);
		session.setAttribute("solicitudDenominacion", deno);
		req.setAttribute("regresa", "si");
		response.setStyle("muestraForm");
	}
	/*catch (CustomException e) {
		//log(e.getCodigoError(), e.getMessage(), request);
		//principal(request);
		//rollback(conn, request);
		//response.setStyle("error");
	} */
	catch (Throwable ex) {
		log(Errors.EC_GENERIC_ERROR, "", ex, request);
		principal(request);
		//rollback(conn, request);
		response.setStyle("error");
	}finally{
		//JDBC.getInstance().closeResultSet(rsetGla);
		//JDBC.getInstance().closeStatement(pstmt);
		//pool.release(conn);
		end(request);
	}
	return response;
	
}

private void registraSolicitudDenominacion(Denominacion deno,DBConnection myConn, String nuOperacion) throws DBException, NumberFormatException, SQLException, CustomException{
		String dia, mes, anho, hh, mm, ss;
		int totalPersNatu = 0;
		int totalPersJuri = 0;
		try{
		//	TP_HOJA_PRES
		DboTPHojaPres tablaTPHojaPres = new DboTPHojaPres();
		tablaTPHojaPres.setConnection(myConn);
		System.out.println("Insertando en BODEGA CENTRAL");
		System.out.println("Insertando en TP_HOJA_PRES");
		/*** solicitudInscripcion*/
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_PRES, deno.getAnio());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_AREA, deno.getCodigoArea());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_AREA, deno.getDescripcionArea());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUO, deno.getNumeroHoja());//requerimiento de mauricio en email
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_SERVICIO_ID, deno.getServicio()); //solo extranet segun mauricio
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_SERVICIO, deno.getDescServicio());//solo extranet segun mauricio
		/** presentante*/
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUARIO, deno.getCodigoUsuario());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_PERS_AUTZ_PRES, Constantes.PERZAUTZPRES);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_PRES, deno.getPresentante().getApePaterno());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_PRES, deno.getPresentante().getApeMaterno());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_PRES, deno.getPresentante().getNombre());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUR, "");
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_CUR, "");
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN, deno.getPresentante().getTipoDocu());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN, deno.getPresentante().getDescDocu());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU, deno.getPresentante().getNumDocu());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_UB_GEOG_PRES, deno.getPersonaJuridica().getDepartamento()+ deno.getPersonaJuridica().getProvincia()); 
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_VIA_PRES, "");
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_VIA_PRES, "");
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_VIA_PRES, deno.getPresentante().getDireccion());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_MAIL_PRES, deno.getPresentante().getEmail());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DEPARTAMENTO_PRES,"" );
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_PROVINCIA_PRES,"" );
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DISTRITO_PRES, "");
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_POSTAL_PRES, "");
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_USR_VERIF, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_VERIF, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RPTA_VERIF, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_IN_RUC, "1");
		dia = deno.getFechaProceso().substring(0,2);
		mes = deno.getFechaProceso().substring(2,4);
		anho = deno.getFechaProceso().substring(4,8);
		hh = deno.getFechaProceso().substring(8,10);
		mm = deno.getFechaProceso().substring(10,12);
		ss = deno.getFechaProceso().substring(12,14);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_HOJA_PRES,FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_HOJA_PRES, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_DEFI, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_USUA_CREA, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUA_CREA, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_OBSE, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_SITU_HOJA_PRES, deno.getPresentante().getParticipacion());
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_ANOT, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_FORM_REG, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RZ_SOCL_REPR, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_REPR, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_REPR, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_REPR, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN_REPR, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN_REPR, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU_REPR, null);
		tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_ELECT_REPR, null);
		tablaTPHojaPres.add();
		//TA_TITU_ACTO
		System.out.println("Insertando en TA_TITU_ACTO");
		DboTATituActo tablaTATituActo = new DboTATituActo();
		tablaTATituActo.setConnection(myConn);
		/*** solicitudInscripcion*/
		tablaTATituActo.setField(DboTATituActo.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
		tablaTATituActo.setField(DboTATituActo.CAMPO_AA_HOJA_PRES, deno.getAnio());
		tablaTATituActo.setField(DboTATituActo.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
		tablaTATituActo.setField(DboTATituActo.CAMPO_DE_ACTO_RGST, deno.getDescActo());
		tablaTATituActo.setField(DboTATituActo.CAMPO_CO_LIBR, deno.getCodigoLibro());
		tablaTATituActo.setField(DboTATituActo.CAMPO_DE_LIBR, deno.getDescLibro());
		tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
		tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
		tablaTATituActo.setField(DboTATituActo.CAMPO_NS_AFEC, 1);////////////////////
		tablaTATituActo.setField(DboTATituActo.CAMPO_IN_ESTD, Constantes.CAMPO_IN_ESTD);
		if (deno.getIndicadorSeleccion().equals("01")){
		tablaTATituActo.setField(DboTATituActo.CAMPO_NU_PART, Constantes.CAMPO_NU_PART);
		}else{
			tablaTATituActo.setField(DboTATituActo.CAMPO_NU_PART, deno.getPersonaJuridica().getPartida());
		}
		tablaTATituActo.setField(DboTATituActo.CAMPO_AA_TITU, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_NU_TITU, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_MO_TOTA_ACTO, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_IN_EXON, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_PO_EXON, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_CREA, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
		tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_CREA, deno.getCodigoUsuario().substring(0, 5));
		tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_MODI, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
		tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_MODI, deno.getCodigoUsuario().substring(0, 5));
		tablaTATituActo.setField(DboTATituActo.CAMPO_CO_RUBR, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_IN_RESE, null);
		tablaTATituActo.setField(DboTATituActo.CAMPO_IN_GENE_ASIE, null);
		tablaTATituActo.add();
		
		//TT_PERS_JURI_TITU -- solo razones sociales - llena 5 registros
		System.out.println("Insertando en TT_PERS_JURI_TITU");
		DboTTPersJuriTitu tablaTTPersJuriTitu = new DboTTPersJuriTitu();
		tablaTTPersJuriTitu.setConnection(myConn);
		int size = 0;
		if(deno.getListaDenominaciones()!=null){
			size = deno.getListaDenominaciones().size();
			for (int i=0; i<size; i++){
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());	
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, i+1);
				RazonSocial raz = (RazonSocial) deno.getListaDenominaciones().get(i);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, raz.getDenominacion());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, raz.getDenoAbrev());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, deno.getCodigoLibro().substring(1));
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, deno.getDescLibro());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, "000");//solo raz sociales en el rango de las 5 reservas
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
				if (deno.getIndicadorSeleccion().equals("01")){
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, Constantes.CAMPO_NU_PART);
				}else{
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, deno.getPersonaJuridica().getPartida());
				}
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC_SUNAT, null);								
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, "");
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
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
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MAIL, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_IND_RRLL_PARTIC, null);
				tablaTTPersJuriTitu.add();
				totalPersJuri++; //contador para guardar en ExEntidadDist - Distribuidor
			}
		}
		//TT_PERS_JURI_TITU -- solo modificaciones
		if ("02".equals(deno.getIndicadorSeleccion())){
			int j=5;
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());	
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, ++j);//
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, deno.getPersonaJuridica().getRazonSocial());//NOMBRE ACTUAL
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, "");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, deno.getCodigoLibro().substring(1));
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, deno.getDescLibro());
			
			DboParticLibro dbo = new DboParticLibro(myConn);
			dbo.setFieldsToRetrieve(DboParticLibro.CAMPO_COD_PARTIC);
			dbo.setField(DboParticLibro.CAMPO_COD_LIBRO, deno.getCodigoLibro());
			dbo.setField(DboParticLibro.CAMPO_NOMBRE,"ADOPTANTE");
			if (dbo.find()==true){
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, dbo.getField(DboParticLibro.CAMPO_COD_PARTIC));//solo raz sociales en el rango de las 5 reservas
				
			}
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART_ASOC, deno.getPersonaJuridica().getPartida());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC_SUNAT, null);								
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, "ADOPTANTE");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
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
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_EMPRE, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_MAIL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_IND_RRLL_PARTIC, null);
			tablaTTPersJuriTitu.add();
			totalPersJuri++;
		}
		
		//TT_PERS_JURI_TITU/TT_PERS_NATU_TITU -- solo para participantes
		if(deno.getListaParticipantes()!=null){
			size = deno.getListaParticipantes().size();
			for (int i=0; i<size; i++){
				
			//	RazonSocial raz = (RazonSocial) deno.getListaDenominaciones().get(i);
			Participantes part = (Participantes)deno.getListaParticipantes().get(i);
			if(part!=null){
				if (!"RUC".equals(part.getTipoDocu()) ){//tipo de docu para ruc es 05
					DboTTPersNatuTitu tablaTTPersNatuTitu = new DboTTPersNatuTitu();
					tablaTTPersNatuTitu.setConnection(myConn);
					//int j = 0;
					/*** solicitudInscripcion*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
					/*** participantesPersonaNatural*/
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_PERS_NATU, 5+i+1);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_DOCU_IDEN, part.getTipoDocu().substring(0,2));
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_DOCU_IDEN, part.getDescDocu());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_DOCU, part.getNumDocu());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_CIVL, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ES_CIVL, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU, part.getApePaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU, part.getApeMaterno());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_PERS_NATU, part.getNombre());
					
					DboParticLibro dbo = new DboParticLibro(myConn);
					dbo.setFieldsToRetrieve(DboParticLibro.CAMPO_COD_PARTIC);
					dbo.setField(DboParticLibro.CAMPO_COD_LIBRO, deno.getCodigoLibro());
					dbo.setField(DboParticLibro.CAMPO_NOMBRE,"INTERESADO");
					if (dbo.find()==true){
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC, dbo.getField(DboParticLibro.CAMPO_COD_PARTIC));
					
					}
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_PRTC, "INTERESADO");
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_NACI, null);
					//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC_SUNAT, null);		
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_PAIS, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_NCNL, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_UB_GEOG, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_PROVINCIA_PRES, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DISTRITO_PRES, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_VIA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_VIA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_VIA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_POSTAL, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_MAIL, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OCUP, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_OCUP, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_OCUP, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_AFEC, 1);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ASOC, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_TITU, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CUR, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_ACCI, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TS_USUA_CREA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_USUA_CREA, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_IND_RRLL_PARTIC, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_CONY, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_VA_PART, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_PO_PART, null);
					tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ACCI, null);
					tablaTTPersNatuTitu.add();
					totalPersNatu++;
				}
				if ("RUC".equals(part.getTipoDocu())){
					//int j = 0;
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());	
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, 5+i+1);
					RazonSocial raz = (RazonSocial) deno.getListaDenominaciones().get(i);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, part.getRazonSocial());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, "");
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, null);
					
					DboParticLibro dbo = new DboParticLibro(myConn);
					dbo.setFieldsToRetrieve(DboParticLibro.CAMPO_COD_PARTIC);
					dbo.setField(DboParticLibro.CAMPO_COD_LIBRO, deno.getCodigoLibro());
					dbo.setField(DboParticLibro.CAMPO_NOMBRE,"INTERESADO");
					if (dbo.find()==true){
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, dbo.getField(DboParticLibro.CAMPO_COD_PARTIC));//solo raz sociales en el rango de las 5 reservas
					
					}
										
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, deno.getPersonaJuridica().getPartida());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, "05");
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, "RUC");
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, part.getNumDocu());
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC_SUNAT, null);								
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, "INTERESADO");
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
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
					tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_IND_RRLL_PARTIC, null);
					tablaTTPersJuriTitu.add();
					totalPersJuri++;
				}
			}
			}
			
			
		}
		//TT_PAGO
		System.out.println("Insertando en TT_PAGO");
		DboTTPago tablaTTPago = new DboTTPago();
		tablaTTPago.setConnection(myConn);
		/*** solicitudInscripcion*/
		tablaTTPago.setField(DboTTPago.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
		tablaTTPago.setField(DboTTPago.CAMPO_AA_HOJA_PRES, deno.getAnio());
		tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
		tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
		tablaTTPago.setField(DboTTPago.CAMPO_MO_SERV, deno.getMonto().toString());
		tablaTTPago.setField(DboTTPago.CAMPO_CO_FR_PAGO, deno.getDatosPago().getCodigoFormaPago());
		tablaTTPago.setField(DboTTPago.CAMPO_DE_FR_PAGO, deno.getDatosPago().getDescripcionFormaPago());
		if (deno.getIndicadorRegistrado().equals("S"))
		tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, nuOperacion);		
		else
			tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, null);
		
		tablaTTPago.setField(DboTTPago.CAMPO_FE_PAGO,  FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
		tablaTTPago.setField(DboTTPago.CAMPO_CO_TIPO_PAGO, deno.getDatosPago().getCodigoTipoPago());
		tablaTTPago.setField(DboTTPago.CAMPO_DE_TIPO_PAGO, deno.getDatosPago().getDescripcionTipoPago());
		tablaTTPago.setField(DboTTPago.CAMPO_AA_TITU, null);
		tablaTTPago.setField(DboTTPago.CAMPO_NU_TITU, null);
		tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI, null);
		tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST, null);
		if (deno.getIndicadorSeleccion().equals("01"))
		tablaTTPago.setField(DboTTPago.CAMPO_IN_SERV, "02"); //constitucion
		else
			tablaTTPago.setField(DboTTPago.CAMPO_IN_SERV, "02"); //modificacion
		tablaTTPago.add();
		
		//TT_BLOQ_PART
		if (deno.getIndicadorSeleccion().equals("02")){
			System.out.println("Insertando en TT_BLOQ_PART");
			DboTTBloqPartida bloqPart = new DboTTBloqPartida();
			bloqPart.setConnection(myConn);
			bloqPart.setField(DboTTBloqPartida.CAMPO_REG_PUB_ID, deno.getCoRegiPres());
			bloqPart.setField(DboTTBloqPartida.CAMPO_OFIC_REG_ID, deno.getCoOficRegiPres());
			
			if (deno.getIndicadorSeleccion().equals("02"))
			bloqPart.setField(DboTTBloqPartida.CAMPO_NUM_PARTIDA, deno.getPersonaJuridica().getPartida());
			else
				bloqPart.setField(DboTTBloqPartida.CAMPO_NUM_PARTIDA,Constantes.CAMPO_NU_PART );
			
			bloqPart.setField(DboTTBloqPartida.CAMPO_COD_LIBRO, deno.getCodigoLibro());
			bloqPart.setField(DboTTBloqPartida.CAMPO_AA_HOJA_PRES, deno.getAnio());
			bloqPart.setField(DboTTBloqPartida.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			bloqPart.setField(DboTTBloqPartida.CAMPO_NU_FOJA, "");
			bloqPart.setField(DboTTBloqPartida.CAMPO_NU_TOMO, "");
			bloqPart.setField(DboTTBloqPartida.CAMPO_FICHA, deno.getPersonaJuridica().getFicha());
			bloqPart.setField(DboTTBloqPartida.CAMPO_RZ_SOC_MODI, deno.getPersonaJuridica().getRazonSocial());
			bloqPart.setField(DboTTBloqPartida.CAMPO_CO_ACTO, deno.getCodigoActo());
			bloqPart.setField(DboTTBloqPartida.CAMPO_ESTADO, "B");
			bloqPart.setField(DboTTBloqPartida.CAMPO_TS_USUA_CREA, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
			bloqPart.setField(DboTTBloqPartida.CAMPO_ID_USUA_CREA, deno.getCodigoUsuario());
			bloqPart.add();
		}
		
		//EX_ENTIDAD_DIST
		System.out.println("Insertando en EXT_ENTIDAD_DIST");
		DboExEntidadDist dboExEntDist = new DboExEntidadDist(myConn);
		dboExEntDist.setField(DboExEntidadDist.CAMPO_ENT_REFNUM,Secuenciales.getInstance().getExEntDistRefNum(myConn));
		dboExEntDist.setField(DboExEntidadDist.CAMPO_AA_HOJA_PRES, deno.getAnio() );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja() );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_REG_PUB_ID, deno.getCoRegiPres() );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_OFIC_REG_ID, deno.getCoOficRegiPres() );
		if (deno.getIndicadorSeleccion().equals("02"))
			dboExEntDist.setField(DboExEntidadDist.CAMPO_TBP_NUM_PARTIDA, deno.getPersonaJuridica().getPartida());
		else
			dboExEntDist.setField(DboExEntidadDist.CAMPO_TBP_NUM_PARTIDA,Constantes.CAMPO_NU_PART );
		
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TTA_CO_ACTO, deno.getCodigoActo() );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TTA_NS_AFEC, 1 );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TPNT_CO_ACTO_RGST, deno.getCodigoActo() );
		
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TPNT_NS_AFEC, 1 );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TPNT_NS_PERS_NATU, totalPersNatu );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TPJT_CO_ACTO_RGST, deno.getCodigoActo() );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TPJT_NS_AFEC, 1);
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TPJT_NS_PERS_NATU, totalPersJuri );
		dboExEntDist.setField(DboExEntidadDist.CAMPO_ESTADO, "0"); //0 para transmitir
		dboExEntDist.setField(DboExEntidadDist.CAMPO_TMSTMP_DIST, null);
		dboExEntDist.setField(DboExEntidadDist.CAMPO_ERROR_CODIGO, "" );
		
		dboExEntDist.add();
		myConn.commit();
		System.out.println("FINALIZANDO INSERTS en BODEGA CENTRAL");
		
		}catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;
			
		}finally{
			myConn.rollback();
			
		}
	
	}

private void registraSolicitudDenominacionOficina(Denominacion deno,DBConnection myConn, String nuOperacion) throws Exception{
	String dia, mes, anho, hh, mm, ss;
	try{
	//	TP_HOJA_PRES
	DboTPHojaPres tablaTPHojaPres = new DboTPHojaPres();
	tablaTPHojaPres.setConnection(myConn);
	System.out.println("Insertando en TP_HOJA_PRES");
	/*** solicitudInscripcion*/
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_PRES, deno.getAnio());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_AREA, deno.getCodigoArea());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_AREA, deno.getDescripcionArea());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUO, deno.getNumeroHoja());//requerimiento de mauricio en email
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_SERVICIO_ID, deno.getServicio()); //solo extranet segun mauricio
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_SERVICIO, deno.getDescServicio());//solo extranet segun mauricio
	/** presentante*/
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUARIO, deno.getCodigoUsuario());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_PERS_AUTZ_PRES, Constantes.PERZAUTZPRES);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_PRES, deno.getPresentante().getApePaterno());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_PRES, deno.getPresentante().getApeMaterno());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_PRES, deno.getPresentante().getNombre());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CUR, "");
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_CUR, "");
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN, deno.getPresentante().getTipoDocu());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN, deno.getPresentante().getDescDocu());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU, deno.getPresentante().getNumDocu());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_UB_GEOG_PRES, deno.getPersonaJuridica().getDepartamento()+ deno.getPersonaJuridica().getProvincia()); 
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_VIA_PRES, "");
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_VIA_PRES, "");
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_VIA_PRES, deno.getPresentante().getDireccion());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_MAIL_PRES, deno.getPresentante().getEmail());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DEPARTAMENTO_PRES,"" );
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_PROVINCIA_PRES,"" );
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_DISTRITO_PRES, "");
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_POSTAL_PRES, "");
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_USR_VERIF, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_VERIF, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RPTA_VERIF, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_IN_RUC, "1");
	dia = deno.getFechaProceso().substring(0,2);
	mes = deno.getFechaProceso().substring(2,4);
	anho = deno.getFechaProceso().substring(4,8);
	hh = deno.getFechaProceso().substring(8,10);
	mm = deno.getFechaProceso().substring(10,12);
	ss = deno.getFechaProceso().substring(12,14);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_HOJA_PRES,FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_HOJA_PRES, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_DEFI, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_USUA_CREA, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_ID_USUA_CREA, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_OBSE, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_SITU_HOJA_PRES, deno.getPresentante().getParticipacion());
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_ANOT, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_FORM_REG, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_RZ_SOCL_REPR, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_PATE_REPR, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_AP_MATE_REPR, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NO_REPR, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN_REPR, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN_REPR, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_NU_DOCU_REPR, null);
	tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_CO_ELECT_REPR, null);
	tablaTPHojaPres.add();
	//TA_TITU_ACTO
	System.out.println("Insertando en TA_TITU_ACTO");
	DboTATituActo tablaTATituActo = new DboTATituActo();
	tablaTATituActo.setConnection(myConn);
	/*** solicitudInscripcion*/
	tablaTATituActo.setField(DboTATituActo.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
	tablaTATituActo.setField(DboTATituActo.CAMPO_AA_HOJA_PRES, deno.getAnio());
	tablaTATituActo.setField(DboTATituActo.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
	tablaTATituActo.setField(DboTATituActo.CAMPO_DE_ACTO_RGST, deno.getDescActo());
	tablaTATituActo.setField(DboTATituActo.CAMPO_CO_LIBR, deno.getCodigoLibro());
	tablaTATituActo.setField(DboTATituActo.CAMPO_DE_LIBR, deno.getDescLibro());
	tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
	tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
	tablaTATituActo.setField(DboTATituActo.CAMPO_NS_AFEC, 1);////////////////////
	tablaTATituActo.setField(DboTATituActo.CAMPO_IN_ESTD, Constantes.CAMPO_IN_ESTD);
	if (deno.getIndicadorSeleccion().equals("01")){
	tablaTATituActo.setField(DboTATituActo.CAMPO_NU_PART, Constantes.CAMPO_NU_PART);
	}else{
		tablaTATituActo.setField(DboTATituActo.CAMPO_NU_PART, deno.getPersonaJuridica().getPartida());
	}
	tablaTATituActo.setField(DboTATituActo.CAMPO_AA_TITU, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_NU_TITU, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_CO_REGI, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_CO_OFIC_RGST, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_MO_TOTA_ACTO, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_IN_EXON, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_PO_EXON, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_CREA, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
	tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_CREA, deno.getCodigoUsuario().substring(0, 5));
	tablaTATituActo.setField(DboTATituActo.CAMPO_TS_USUA_MODI, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
	tablaTATituActo.setField(DboTATituActo.CAMPO_ID_USUA_MODI, deno.getCodigoUsuario().substring(0, 5));
	tablaTATituActo.setField(DboTATituActo.CAMPO_CO_RUBR, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_IN_RESE, null);
	tablaTATituActo.setField(DboTATituActo.CAMPO_IN_GENE_ASIE, null);
	tablaTATituActo.add();
	
	//TT_PERS_JURI_TITU -- solo razones sociales - llena 5 registros
	System.out.println("Insertando en TT_PERS_JURI_TITU");
	DboTTPersJuriTitu tablaTTPersJuriTitu = new DboTTPersJuriTitu();
	tablaTTPersJuriTitu.setConnection(myConn);
	int size = 0;
	if(deno.getListaDenominaciones()!=null){
		size = deno.getListaDenominaciones().size();
		for (int i=0; i<size; i++){
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());	
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, i+1);
			RazonSocial raz = (RazonSocial) deno.getListaDenominaciones().get(i);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, raz.getDenominacion());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, raz.getDenoAbrev());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, deno.getCodigoLibro().substring(1));
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, deno.getDescLibro());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, "000");//solo raz sociales en el rango de las 5 reservas
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
			if (deno.getIndicadorSeleccion().equals("01")){
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, Constantes.CAMPO_NU_PART);
			}else{
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, deno.getPersonaJuridica().getPartida());
			}
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC_SUNAT, null);								
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, "");
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
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
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_IND_RRLL_PARTIC, null);
			tablaTTPersJuriTitu.add();
		
		}
	}
	//TT_PERS_JURI_TITU -- solo modificaciones
	if ("02".equals(deno.getIndicadorSeleccion())){
		int j=5;
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());	
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, ++j);//
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, deno.getPersonaJuridica().getRazonSocial());//NOMBRE ACTUAL
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, "");
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, deno.getCodigoLibro().substring(1));
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, deno.getDescLibro());
		
		/*DboParticLibro dbo = new DboParticLibro(myConn);
		dbo.setFieldsToRetrieve(DboParticLibro.CAMPO_COD_PARTIC);
		dbo.setField(DboParticLibro.CAMPO_COD_LIBRO, deno.getCodigoLibro());
		dbo.setField(DboParticLibro.CAMPO_NOMBRE,"ADOPTANTE");*/
		//if (dbo.find()==true){
			tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, obtenerTipoParticipante("ADOPTANTE", deno.getCodigoLibro()));//solo raz sociales en el rango de las 5 reservas
		//}
		
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, deno.getPersonaJuridica().getPartida());
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC_SUNAT, null);								
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, "ADOPTANTE");
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
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
		tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_IND_RRLL_PARTIC, null);
		tablaTTPersJuriTitu.add();
	}
	
	//TT_PERS_JURI_TITU/TT_PERS_NATU_TITU -- solo para participantes
	if(deno.getListaParticipantes()!=null){
		size = deno.getListaParticipantes().size();
		for (int i=0; i<size; i++){
			
		//	RazonSocial raz = (RazonSocial) deno.getListaDenominaciones().get(i);
		Participantes part = (Participantes)deno.getListaParticipantes().get(i);
		if(part!=null){
			if ("DNI".equals(part.getTipoDocu()) ){
				DboTTPersNatuTitu tablaTTPersNatuTitu = new DboTTPersNatuTitu();
				tablaTTPersNatuTitu.setConnection(myConn);
				//int j = 0;
				/*** solicitudInscripcion*/
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
				/*** participantesPersonaNatural*/
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_PERS_NATU, 5+i+1);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_DOCU_IDEN, "09");
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_DOCU_IDEN, "DNI");
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_DOCU, part.getNumDocu());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_CIVL, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_ES_CIVL, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU, part.getApePaterno());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU, part.getApeMaterno());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_PERS_NATU, part.getNombre());
				
				
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC, obtenerTipoParticipante("INTERESADO",deno.getCodigoLibro()));
				
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_TI_PRTC, "INTERESADO");
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_NACI, null);
				//tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_PRTC_SUNAT, null);		
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_PAIS, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_NCNL, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_UB_GEOG, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_PROVINCIA_PRES, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_DISTRITO_PRES, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TI_VIA, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_VIA, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_VIA, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_POSTAL, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_MAIL, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OCUP, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_OCUP, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_FE_OCUP, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NS_AFEC, 1);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ASOC, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_AA_TITU, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_TITU, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_REGI, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CO_OFIC_RGST, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_CUR, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_ACCI, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_TS_USUA_CREA, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_ID_USUA_CREA, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_IND_RRLL_PARTIC, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NO_CONY, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_VA_PART, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_PO_PART, null);
				tablaTTPersNatuTitu.setField(DboTTPersNatuTitu.CAMPO_NU_PART_ACCI, null);
				tablaTTPersNatuTitu.add();
			}
			if ("RUC".equals(part.getTipoDocu())){
				//int j = 0;
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_ACTO_RGST, deno.getCodigoActo());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_ACTO_RGST, deno.getDescActo());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());	
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_AFEC, 1);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NS_PERS_JURI, 5+i+1);
				RazonSocial raz = (RazonSocial) deno.getListaDenominaciones().get(i);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_RZ_SOCL, part.getRazonSocial());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_SIGL, "");
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PERS_JURI, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PERS_JURI, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, obtenerTipoParticipante("INTERESADO", deno.getCodigoLibro()));//solo raz sociales en el rango de las 5 reservas
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_LIBR_PART, deno.getCodigoLibro());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_LIBR_PART, deno.getDescLibro());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_PART, deno.getPersonaJuridica().getPartida());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ES_PRTC, Constantes.CAMPO_ES_PRTC);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN, "05");
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN, "RUC");
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NU_DOCU, part.getNumDocu());
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC_SUNAT, null);								
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, "INTERESADO");
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_ID_PAIS, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_NCNL, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_REGI, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_TI_VIA, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_VIA, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_NO_VIA, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_CO_POSTAL, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_UB_GEOG, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DEPARTAMENTO_PRES, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_PROVINCIA_PRES, null);
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_DE_DISTRITO_PRES, null);
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
				tablaTTPersJuriTitu.setField(DboTTPersJuriTitu.CAMPO_IND_RRLL_PARTIC, null);
				tablaTTPersJuriTitu.add();
			}
		}
		}
		
		
	}
	//TT_PAGO
	System.out.println("Insertando en TT_PAGO");
	DboTTPago tablaTTPago = new DboTTPago();
	tablaTTPago.setConnection(myConn);
	/*** solicitudInscripcion*/
	tablaTTPago.setField(DboTTPago.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
	tablaTTPago.setField(DboTTPago.CAMPO_AA_HOJA_PRES, deno.getAnio());
	tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
	tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
	tablaTTPago.setField(DboTTPago.CAMPO_MO_SERV, deno.getMonto().toString());
	tablaTTPago.setField(DboTTPago.CAMPO_CO_FR_PAGO, deno.getDatosPago().getCodigoFormaPago());
	tablaTTPago.setField(DboTTPago.CAMPO_DE_FR_PAGO, deno.getDatosPago().getDescripcionFormaPago());
	if (deno.getIndicadorRegistrado().equals("S"))
		tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, nuOperacion);		
		else
			tablaTTPago.setField(DboTTPago.CAMPO_NU_OPER, null);		
	
	tablaTTPago.setField(DboTTPago.CAMPO_FE_PAGO,  FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
	tablaTTPago.setField(DboTTPago.CAMPO_CO_TIPO_PAGO, deno.getDatosPago().getCodigoTipoPago());
	tablaTTPago.setField(DboTTPago.CAMPO_DE_TIPO_PAGO, deno.getDatosPago().getDescripcionTipoPago());
	tablaTTPago.setField(DboTTPago.CAMPO_AA_TITU, null);
	tablaTTPago.setField(DboTTPago.CAMPO_NU_TITU, null);
	tablaTTPago.setField(DboTTPago.CAMPO_CO_REGI, null);
	tablaTTPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST, null);
	if (deno.getIndicadorSeleccion().equals("01"))
	tablaTTPago.setField(DboTTPago.CAMPO_IN_SERV, "02"); //constitucion
	else
		tablaTTPago.setField(DboTTPago.CAMPO_IN_SERV, "02"); //modificacion
	tablaTTPago.add();
	
	//TT_BLOQ_PART
	if (deno.getIndicadorSeleccion().equals("02")){
		System.out.println("Insertando en TT_BLOQ_PART");
		DboTTBloqPartida bloqPart = new DboTTBloqPartida();
		bloqPart.setConnection(myConn);
		bloqPart.setField(DboTTBloqPartida.CAMPO_REG_PUB_ID, deno.getCoRegiPres());
		bloqPart.setField(DboTTBloqPartida.CAMPO_OFIC_REG_ID, deno.getCoOficRegiPres());
		
		if (deno.getIndicadorSeleccion().equals("02"))
		bloqPart.setField(DboTTBloqPartida.CAMPO_NUM_PARTIDA, deno.getPersonaJuridica().getPartida());
		else
			bloqPart.setField(DboTTBloqPartida.CAMPO_NUM_PARTIDA,Constantes.CAMPO_NU_PART );
		
		bloqPart.setField(DboTTBloqPartida.CAMPO_COD_LIBRO, deno.getCodigoLibro());
		bloqPart.setField(DboTTBloqPartida.CAMPO_AA_HOJA_PRES, deno.getAnio());
		bloqPart.setField(DboTTBloqPartida.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
		bloqPart.setField(DboTTBloqPartida.CAMPO_NU_FOJA, "");
		bloqPart.setField(DboTTBloqPartida.CAMPO_NU_TOMO, "");
		bloqPart.setField(DboTTBloqPartida.CAMPO_FICHA, deno.getPersonaJuridica().getFicha());
		bloqPart.setField(DboTTBloqPartida.CAMPO_RZ_SOC_MODI, deno.getPersonaJuridica().getRazonSocial());
		bloqPart.setField(DboTTBloqPartida.CAMPO_CO_ACTO, deno.getCodigoActo());
		bloqPart.setField(DboTTBloqPartida.CAMPO_ESTADO, "B");
		bloqPart.setField(DboTTBloqPartida.CAMPO_TS_USUA_CREA, FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
		bloqPart.setField(DboTTBloqPartida.CAMPO_ID_USUA_CREA, deno.getCodigoUsuario());
		bloqPart.add();
	}
	
	myConn.commit();
	
	}catch (DBException dbe) {
		dbe.printStackTrace();
		throw dbe;

	}

}

	private String  obtenerTipoParticipante(String descParticipante, String codigoLibro) throws Exception{
		//conexion a la extranet
		String respuesta = "";
		DBConnectionFactory pool = null;
		Connection conn = null;
		DBConnection myConn = null;
		pool = DBConnectionFactory.getInstance();
		conn = pool.getConnection();
	   	myConn = new DBConnection(conn);
	   	DboParticLibro dbo = new DboParticLibro(myConn);
		dbo.setFieldsToRetrieve(DboParticLibro.CAMPO_COD_PARTIC);
		dbo.setField(DboParticLibro.CAMPO_COD_LIBRO, codigoLibro);
		dbo.setField(DboParticLibro.CAMPO_NOMBRE,descParticipante);
		if (dbo.find()==true){
			respuesta = dbo.getField(DboParticLibro.CAMPO_COD_PARTIC);
		}
		conn.close();
		
		return respuesta;
	}

	private String  descuentoSaldo(DBConnection myConn, PrepagoBean pre, Denominacion deno) throws DBException, NumberFormatException, SQLException, CustomException{
		
		int transId = Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(myConn)));
		try{ 
		DboTransaccion transac = new DboTransaccion();
		transac.setConnection(myConn);
		
		//transac.setField(transac.CAMPO_TRANS_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(myConn))));		
		transac.setField(transac.CAMPO_TRANS_ID, transId);
		transac.setField(transac.CAMPO_SERVICIO_ID, deno.getServicio());
		transac.setField(transac.CAMPO_COD_GRUPO_LIBRO_AREA, deno.getCodigoLibro());
		transac.setField(transac.CAMPO_CUENTA_ID, deno.getUsuario().getCuentaId());
		transac.setField(transac.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		Double costo_servicio = Double.parseDouble(deno.getMonto().toString());
		transac.setField(transac.CAMPO_COSTO, Double.toString(costo_servicio));
		transac.setField(transac.CAMPO_IP, "LOCALHOST");
		transac.setField(transac.CAMPO_SESION_ID, "");
		transac.setField(transac.CAMPO_TIPO_USR, "1");
		transac.setField(transac.CAMPO_STR_BUSQ, "RESERVA DENOMINACION");
		transac.setField(transac.CAMPO_REG_PUB_ID, deno.getUsuario().getRegPublicoId());
		transac.setField(transac.CAMPO_OFIC_REG_ID, deno.getUsuario().getOficRegistralId());
		transac.add();
		
		PrepagoBean prep = new PrepagoBean();
		prep.setTransacId(transId);
		prep.setUsuario(deno.getUsuario().getUserId());
		prep.setLineaPrepagoId(deno.getUsuario().getLinPrePago());
		prep.setMontoBruto(costo_servicio);
		
		LineaPrepago lineaCmd = new LineaPrepago();
		
		lineaCmd.reduceSaldo(deno.getUsuario(), prep, myConn);
		
		myConn.commit();
		}catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		}finally{
			myConn.rollback();
		}
		return String.valueOf(transId);
		}
	protected ControllerResponse runConsultaResumenState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		
		//HttpSession session = ExpressoHttpSessionBean.getSession(request);
		HttpServletRequest req;
        DBConnectionFactory pool;
        Connection conn2;
        req = ExpressoHttpSessionBean.getRequest(request);
        pool = null;
        //DBConnection myConn = null;
        conn2 = null;
        try
        {
            pool = DBConnectionFactory.getInstance();
            //conn2 = pool.getConnection();
            conn2 = DriverManager.getConnection("jdbc:oracle:thin:@172.18.1.55:1521:dbsp", "user1", "oraclerac");
            DBConnection myConn = new DBConnection(conn2);
            Denominacion deno = new Denominacion();
            Presentante pres = new Presentante();
            Juridica juri = new Juridica();
            ResultSet rs = null;
            Statement stmt = null;
            stmt = conn2.createStatement();
            deno.setNumeroHoja(request.getParameter("nuHoja"));
            deno.setAnio(request.getParameter("anio"));
            deno.setCoRegiPres(request.getParameter("coRegi"));
            deno.setCoOficRegiPres(request.getParameter("ofic"));
            deno.setIndicadorSeleccion("01");
            deno.setDescSeleccion("CONSTITUCION");
            StringBuilder query = new StringBuilder();
            query.append("SELECT REFNUM_TITU FROM USER1.MENSAJE_MAIL WHERE");
            query.append((new StringBuilder(" AA_HOJA_PRES='")).append(deno.getAnio()).append("' ").toString());
            query.append((new StringBuilder(" AND NU_HOJA_PRES='")).append(deno.getNumeroHoja()).append("' ").toString());
            query.append((new StringBuilder("AND OFIC_REG_ID='")).append(deno.getCoOficRegiPres()).append("' ").toString());
            query.append((new StringBuilder(" AND REG_PUB_ID='")).append(deno.getCoRegiPres()).append("' ").toString());
            System.out.println("-->CONSULTANDO REFNUMTITU EN MENSAJE MAIL...");
            System.out.println((new StringBuilder("-->")).append(query.toString()).toString());
            for(rs = stmt.executeQuery(query.toString()); rs.next(); deno.setRefNumTitu(String.valueOf(rs.getLong(1))));
            System.out.println((new StringBuilder("-->REFNUM_TITU RECUPERADO::")).append(deno.getRefNumTitu()).toString());
			/**** parche ***/
			if (deno.getRefNumTitu()==null){
				deno.setNumeroTitulo("NO ENCONTRADO");
				deno.setAnioTitu("NO ENCONTRADO");
				deno.setFechaPresTitu("NO ENCONTRADO");
			}else{
				//CONSULTANDO EL NUMERO DE TITULO //RECUPERAR LA HORA DEL TITULO GENERADO
				System.out.println("-->CONSULTANDO NUMTITU EN TITULO...");
				DboTitulo dbTitulo = new DboTitulo(myConn);
				dbTitulo.setFieldsToRetrieve(DboTitulo.CAMPO_NUM_TITU +"|"+ DboTitulo.CAMPO_ANO_TITU +"|"+ DboTitulo.CAMPO_TS_PRESENT);
				dbTitulo.setField(DboMensajeMail.CAMPO_REFNUM_TITU, deno.getRefNumTitu());
				if (dbTitulo.find()==true){
					deno.setNumeroTitulo(dbTitulo.getField(DboTitulo.CAMPO_NUM_TITU));
					System.out.println("-->TITULO RECUPERADO::" + deno.getNumeroTitulo());
					deno.setAnioTitu(dbTitulo.getField(DboTitulo.CAMPO_ANO_TITU));
					deno.setFechaPresTitu(dbTitulo.getField(DboTitulo.CAMPO_TS_PRESENT));
				}	
			}
			/*** parche 28/01/2009**/
			
			DboTPHojaPres tpHoja = new DboTPHojaPres(myConn);
			tpHoja.setFieldsToRetrieve(DboTPHojaPres.CAMPO_CUO + "|"+ DboTPHojaPres.CAMPO_CO_AREA + "|"+ DboTPHojaPres.CAMPO_DE_AREA
			+ "|"+ DboTPHojaPres.CAMPO_ID_USUARIO + "|"+ DboTPHojaPres.CAMPO_PERS_AUTZ_PRES	+ "|"+	DboTPHojaPres.CAMPO_AP_PATE_PRES
			+ "|"+ DboTPHojaPres.CAMPO_AP_MATE_PRES + "|"+ DboTPHojaPres.CAMPO_NO_PRES + "|"+ DboTPHojaPres.CAMPO_TI_DOCU_IDEN
			+ "|"+ DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN + "|"+ DboTPHojaPres.CAMPO_NU_DOCU + "|"+ DboTPHojaPres.CAMPO_SERVICIO_ID
			+ "|"+ DboTPHojaPres.CAMPO_DE_SERVICIO + "|"+ DboTPHojaPres.CAMPO_UB_GEOG_PRES + "|"+ DboTPHojaPres.CAMPO_NO_VIA_PRES
			+ "|"+ DboTPHojaPres.CAMPO_MAIL_PRES + "|"+ DboTPHojaPres.CAMPO_TI_SITU_HOJA_PRES
			);
			tpHoja.setField(DboTPHojaPres.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			tpHoja.setField(DboTPHojaPres.CAMPO_AA_HOJA_PRES, deno.getAnio());
			tpHoja.setField(DboTPHojaPres.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			tpHoja.setField(DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
			
			if(tpHoja.find()==true){
				//deno.setDescServicio(tpHoja.getField(DboTPHojaPres.CAMPO_ID_USUARIO));
				deno.setServicio(tpHoja.getField(DboTPHojaPres.CAMPO_SERVICIO_ID));
				deno.setDescServicio(tpHoja.getField(DboTPHojaPres.CAMPO_DE_SERVICIO));
				deno.setCodigoArea(tpHoja.getField(DboTPHojaPres.CAMPO_CO_AREA));
				deno.setCodigoUsuario(tpHoja.getField(DboTPHojaPres.CAMPO_ID_USUARIO));
				pres.setApePaterno(tpHoja.getField(DboTPHojaPres.CAMPO_AP_PATE_PRES));
				pres.setApeMaterno(tpHoja.getField(DboTPHojaPres.CAMPO_AP_MATE_PRES));
				pres.setNombre(tpHoja.getField(DboTPHojaPres.CAMPO_NO_PRES));
				pres.setNumDocu(tpHoja.getField(DboTPHojaPres.CAMPO_NU_DOCU));
				pres.setTipoDocu(tpHoja.getField(DboTPHojaPres.CAMPO_TI_DOCU_IDEN));
				pres.setDescDocu(tpHoja.getField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN));
				pres.setDireccion(tpHoja.getField(DboTPHojaPres.CAMPO_NO_VIA_PRES));
				pres.setEmail(tpHoja.getField(DboTPHojaPres.CAMPO_MAIL_PRES));
				pres.setDescParticipacion(tipoParticipantes(tpHoja.getField(DboTPHojaPres.CAMPO_TI_SITU_HOJA_PRES)));
				
				juri.setDepartamento(tpHoja.getField(DboTPHojaPres.CAMPO_UB_GEOG_PRES).substring(0, 2));
				juri.setProvincia(tpHoja.getField(DboTPHojaPres.CAMPO_UB_GEOG_PRES).substring(2, 4));
				deno.setPresentante(pres);
			}
			//consultando provincia y departamento
			DboTmProvincia dboProv = new DboTmProvincia(myConn);
			dboProv.setFieldsToRetrieve(DboTmProvincia.CAMPO_REG_PUB_ID +"|"+ DboTmProvincia.CAMPO_OFIC_REG_ID
					+"|"+ DboTmProvincia.CAMPO_NOMBRE);
			dboProv.setField(DboTmProvincia.CAMPO_DPTO_ID, juri.getDepartamento());
			dboProv.setField(DboTmProvincia.CAMPO_PROV_ID, juri.getProvincia());
			if(dboProv.find()==true){
				deno.setCoRegiPres(dboProv.getField(DboTmProvincia.CAMPO_REG_PUB_ID));//relacionar de donde vienen
				deno.setCoOficRegiPres(dboProv.getField(DboTmProvincia.CAMPO_OFIC_REG_ID));
				juri.setDescProv(dboProv.getField(DboTmProvincia.CAMPO_NOMBRE));
			}else{
				throw new Exception("NO ENCONTRADO EN TM_PROVINCIA");
			}
			//consultando el nombre del departamento
			DboTmDepartamento dboDpto = new DboTmDepartamento(myConn);
			dboDpto.setFieldsToRetrieve(DboTmDepartamento.CAMPO_NOMBRE);
			dboDpto.setField(DboTmDepartamento.CAMPO_DPTO_ID, juri.getDepartamento());
			if(dboDpto.find()==true){
				juri.setDescDepto(dboDpto.getField(DboTmDepartamento.CAMPO_NOMBRE));				
			}
			else{
				throw new Exception("NO ENCONTRADO EN TM_DEPARTAMENTO");
			}
			
			//TA_TITU_ACTO
			DboTATituActo dboTatitu = new DboTATituActo(myConn);
			dboTatitu.setFieldsToRetrieve(DboTATituActo.CAMPO_CO_ACTO_RGST + "|"+ DboTATituActo.CAMPO_DE_ACTO_RGST + "|"+ 
			DboTATituActo.CAMPO_CO_LIBR + "|"+	DboTATituActo.CAMPO_DE_LIBR);
			dboTatitu.setField(DboTATituActo.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			dboTatitu.setField(DboTATituActo.CAMPO_AA_HOJA_PRES, deno.getAnio());
			dboTatitu.setField(DboTATituActo.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			dboTatitu.setField(DboTATituActo.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
			if(dboTatitu.find()==true){
				deno.setCodigoLibro(dboTatitu.getField(DboTATituActo.CAMPO_CO_LIBR));
				deno.setDescLibro(dboTatitu.getField(DboTATituActo.CAMPO_DE_LIBR));
				deno.setCodigoActo(dboTatitu.getField(DboTATituActo.CAMPO_CO_ACTO_RGST));
				deno.setDescActo(dboTatitu.getField(DboTATituActo.CAMPO_DE_ACTO_RGST));
			}
			juri.setDescTipo(deno.getDescLibro());
			// RECUPERANDO LAS RAZONES SOCIALES (RANGO DE 5)
			DboTTPersJuriTitu dboJuri = new DboTTPersJuriTitu(myConn);
			dboJuri.setFieldsToRetrieve(DboTTPersJuriTitu.CAMPO_RZ_SOCL + "|"+ DboTTPersJuriTitu.CAMPO_DE_SIGL);
			dboJuri.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, "000");
			dboJuri.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
			dboJuri.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			dboJuri.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			dboJuri.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
			ArrayList arr = dboJuri.searchAndRetrieveList();
			ArrayList<RazonSocial> listaDenominaciones = new ArrayList<RazonSocial>();
			Iterator iterator = arr.iterator();
			
			while (iterator.hasNext()) {
				RazonSocial raz = new RazonSocial();
				DboTTPersJuriTitu element = (DboTTPersJuriTitu) iterator.next();
				raz.setDenominacion(element.getField(DboTTPersJuriTitu.CAMPO_RZ_SOCL));
				raz.setDenoAbrev(element.getField(DboTTPersJuriTitu.CAMPO_DE_SIGL));
				listaDenominaciones.add(raz);
			}
			
			deno.setListaDenominaciones(listaDenominaciones);
			DboTTPersJuriTitu dboJuri2 = new DboTTPersJuriTitu(myConn);
			//RECUPERANDO ADOPTANTE EN CASO DE MODIFICACION
			dboJuri2.setFieldsToRetrieve(DboTTPersJuriTitu.CAMPO_RZ_SOCL + "|"+ DboTTPersJuriTitu.CAMPO_NU_PART);
			dboJuri2.setField(DboTTPersJuriTitu.CAMPO_DE_TI_PRTC, "ADOPTANTE");
			dboJuri2.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
			dboJuri2.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			dboJuri2.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			dboJuri2.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
			if (dboJuri2.find()==true){
				juri.setPartida(dboJuri2.getField(DboTTPersJuriTitu.CAMPO_NU_PART));
				juri.setRazonSocial(dboJuri2.getField(DboTTPersJuriTitu.CAMPO_RZ_SOCL));
			}
			
			//RECUPERANDO FICHA EN CASO DE MODIFICACION
			DboTTBloqPartida dboTtbloq = new DboTTBloqPartida(myConn);
			dboTtbloq.setFieldsToRetrieve(DboTTBloqPartida.CAMPO_FICHA);
			dboTtbloq.setField(dboTtbloq.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			dboTtbloq.setField(dboTtbloq.CAMPO_AA_HOJA_PRES, deno.getAnio());
			dboTtbloq.setField(dboTtbloq.CAMPO_REG_PUB_ID, deno.getCoRegiPres());
			dboTtbloq.setField(dboTtbloq.CAMPO_OFIC_REG_ID, deno.getCoOficRegiPres());
			if (dboTtbloq.find()==true){
				juri.setFicha(dboTtbloq.getField(DboTTBloqPartida.CAMPO_FICHA));
				deno.setIndicadorSeleccion("02");
				deno.setDescSeleccion("MODIFICACION");
			}else{
				juri.setFicha("");
			}
			
			//RECUPERANDO PARTICIPANTES PERSONA NATURAL
			DboTTPersNatuTitu dboTtPersNatu = new DboTTPersNatuTitu(myConn);
			dboTtPersNatu.setFieldsToRetrieve(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU +"|"+ DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU +"|"+
			DboTTPersNatuTitu.CAMPO_NO_PERS_NATU +"|"+ 	DboTTPersNatuTitu.CAMPO_DE_TI_DOCU_IDEN +"|"+	DboTTPersNatuTitu.CAMPO_TI_DOCU_IDEN
			+"|"+ DboTTPersNatuTitu.CAMPO_NU_DOCU +"|"+ DboTTPersNatuTitu.CAMPO_TI_PRTC
			);
			dboTtPersNatu.setField(dboTtPersNatu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			dboTtPersNatu.setField(dboTtPersNatu.CAMPO_AA_HOJA_PRES, deno.getAnio());
			dboTtPersNatu.setField(dboTtPersNatu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			dboTtPersNatu.setField(dboTtPersNatu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
			ArrayList arr2 = dboTtPersNatu.searchAndRetrieveList();
			ArrayList<Participantes> listaParticipantes = new ArrayList<Participantes>();
			Iterator iterator2 = arr2.iterator();
			while (iterator2.hasNext()) {
				Participantes part = new Participantes();
				DboTTPersNatuTitu element = (DboTTPersNatuTitu) iterator2.next();
				part.setNombre(element.getField(DboTTPersNatuTitu.CAMPO_NO_PERS_NATU));
				part.setApePaterno(element.getField(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU));
				part.setApeMaterno(element.getField(DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU));
				part.setNumDocu(element.getField(DboTTPersNatuTitu.CAMPO_NU_DOCU));
				part.setTipoDocu("DNI");
				part.setDescDocu(element.getField(DboTTPersNatuTitu.CAMPO_DE_TI_DOCU_IDEN));
				part.setTipoParticipante(element.getField(DboTTPersNatuTitu.CAMPO_TI_PRTC));
				listaParticipantes.add(part);
			}
			//RECUPERANDO PARTICIPANTE PERSONA JURIDICA
			DboTTPersJuriTitu dboJuri3 = new DboTTPersJuriTitu(myConn);
			//dboJuri.clearAll();
			dboJuri3.setFieldsToRetrieve(DboTTPersJuriTitu.CAMPO_RZ_SOCL + "|"+ DboTTPersJuriTitu.CAMPO_TI_DOCU_IDEN
					+ "|"+ 	DboTTPersJuriTitu.CAMPO_DE_TI_DOCU_IDEN + "|"+ DboTTPersJuriTitu.CAMPO_NU_DOCU
			);
			//dboJuri.setField(DboTTPersJuriTitu.CAMPO_TI_PRTC, "000");
			dboJuri3.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
			dboJuri3.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			dboJuri3.setField(DboTTPersJuriTitu.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			dboJuri3.setField(DboTTPersJuriTitu.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
			dboJuri3.setAppendWhereClause(DboTTPersJuriTitu.CAMPO_TI_PRTC + " <> '000' " );
			ArrayList arr3 = dboJuri3.searchAndRetrieveList();
			//ArrayList<Participantes> listaParticipantesJuri = new ArrayList<Participantes>();
			Iterator iterator3 = arr3.iterator();
			while (iterator3.hasNext()) {
				Participantes part = new Participantes();
				DboTTPersJuriTitu element = (DboTTPersJuriTitu) iterator3.next();
				part.setNumDocu(element.getField(DboTTPersJuriTitu.CAMPO_NU_DOCU));
				part.setTipoDocu("RUC");
				part.setDescDocu("RUC");
				//part.setTipoParticipante(element.getField(DboTTPersJuriTitu.CAMPO_TI_PRTC));
				part.setRazonSocial(element.getField(DboTTPersJuriTitu.CAMPO_RZ_SOCL));
				listaParticipantes.add(part);
			}

			//CONSULTANDO LA TABLA DE TT_PAGO
			System.out.println("-->CONSULTANDO FORMA_PAGO EN TTPAGO...");
			DboTTPago dboPago = new DboTTPago(myConn);
			DatosPago pago = new DatosPago();
			dboPago.setFieldsToRetrieve(DboTTPago.CAMPO_CO_FR_PAGO +"|"+ DboTTPago.CAMPO_MO_SERV +"|"+ DboTTPago.CAMPO_NU_OPER);
			dboPago.setField(DboTTPago.CAMPO_AA_HOJA_PRES, deno.getAnio());
			dboPago.setField(DboTTPago.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
			dboPago.setField(DboTTPago.CAMPO_CO_REGI_PRES, deno.getCoRegiPres());
			dboPago.setField(DboTTPago.CAMPO_CO_OFIC_RGST_PRES, deno.getCoOficRegiPres());
			if (dboPago.find()==true){
				pago.setCodigoFormaPago(dboPago.getField(DboTTPago.CAMPO_CO_FR_PAGO));
				if("03".equals(pago.getCodigoFormaPago())){
					pago.setDescripcionFormaPago(Constantes.PAGOWEB);
					pago.setCostoTotalServicio(new java.math.BigDecimal(dboPago.getField(DboTTPago.CAMPO_MO_SERV)));
					pago.setNumeroOperacion(dboPago.getField(DboTTPago.CAMPO_NU_OPER));
				}
				if("04".equals(pago.getCodigoFormaPago())){
					DboReciboTitulo dboRec = new DboReciboTitulo(myConn);
					dboRec.setFieldsToRetrieve(DboReciboTitulo.CAMPO_CO_CAJA +"|"+ DboReciboTitulo.CAMPO_NUM_TICK +"|"+ DboReciboTitulo.CAMPO_MONTO_COBR);
					dboRec.setField(DboReciboTitulo.CAMPO_REFNUM_TITU, deno.getRefNumTitu());
					
					if (deno.getRefNumTitu()!=null){
					
						if (dboRec.find()==true){
							pago.setDescripcionFormaPago(dboRec.getField(DboReciboTitulo.CAMPO_CO_CAJA));
							pago.setCostoTotalServicio(new java.math.BigDecimal(dboRec.getField(DboReciboTitulo.CAMPO_MONTO_COBR)));
							pago.setNumeroOperacion(dboRec.getField(DboReciboTitulo.CAMPO_NUM_TICK));
						}
					
					}else{
						pago.setDescripcionFormaPago("NO PRESENTADO");
						pago.setNumeroOperacion("NO PRESENTADO");
						pago.setCostoTotalServicio(new java.math.BigDecimal("0"));
					}
					
				}
			}
			// evaluar para recuperar los datos segun el tipo de pago
			deno.setListaParticipantes(listaParticipantes);
			deno.setPersonaJuridica(juri);
			req.setAttribute("denominacion", deno);
			req.setAttribute("denoRazSoc", deno.getListaDenominaciones());
			req.setAttribute("presentante", deno.getPresentante());
			req.setAttribute("juridica", deno.getPersonaJuridica());
			req.setAttribute("participantes", deno.getListaParticipantes());
			req.setAttribute("pago", pago);
			req.setAttribute("indicadorTitulo", "si");
			response.setStyle("resumenImpresion");
		}
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn2, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn2, request);
			response.setStyle("error");
		}finally{
			pool.release(conn2);
			end(request);
		}
		return response;
		
	}
	
	protected ControllerResponse runMuestraFiltroReporteState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		
		System.out.println("--->JLBP<---");
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = null;
		Connection conn = null;
		DBConnection myConn = null;
		
		try
		{
			init(request);
			validarSesion(request);
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			pool = DBConnectionFactory.getInstance();
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.18.1.55:1521:dbsp", "user1", "oraclerac");
			myConn = new DBConnection(conn);
			/** envio de datos para el combo de fechas y Oficinas **/
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());
			req.setAttribute("listaOficinas",Tarea.getComboOficinasRegistrales(myConn));
			/** fin envio de datos **/
			//direcciona al jsp a mostrar
			response.setStyle("filtroReporte");
			System.out.println(response.getStyle());
				
		} catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		
		return response;
	}
		
	
	
	protected ControllerResponse runVerReportesState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = null;
		Connection conn = null;
		DBConnection myConn = null;
		
		
		try{
			init(request);
			
			pool = DBConnectionFactory.getInstance();
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.18.1.55:1521:dbsp", "user1", "oraclerac");
			myConn = new DBConnection(conn);
		   	ArrayList<ReportesBean> listaDetalleHojas = new ArrayList<ReportesBean>();
			/** recupero los datos del Formulario **/
			String diaIni = request.getParameter("diaInicio");
			String mesIni = request.getParameter("mesInicio");
			String anioIni = request.getParameter("anioInicio");
			String fechaInicio = FechaUtil.stringTimeToOracleString(Integer.parseInt(diaIni), Integer.parseInt(mesIni), Integer.parseInt(anioIni), 0, 0, 0);
			String diaFin = request.getParameter("diaFin");
			String mesFin = request.getParameter("mesFin");
			String anioFin = request.getParameter("anioFin");
			String fechaFin = FechaUtil.stringTimeToOracleString(Integer.parseInt(diaFin), Integer.parseInt(mesFin),Integer.parseInt(anioFin), 23, 59, 59);
			String oficina = request.getParameter("cboOficinas");
			String presentadas = request.getParameter("tipoPresentadas");
			String trabajadas =  request.getParameter("tipoTrabajadas");
			/****/
			ArrayList listaHojas = new ArrayList();
			DboVwHojasPresentadas dbo = new DboVwHojasPresentadas(myConn);
			//DboVwHojasTrabajadas dboTra = new DboVwHojasTrabajadas();
			
			/** reporte para presentadas **/
			if ("01".equals(presentadas)){
				if(!"01".equals(oficina) && "01".equals(presentadas)){//reporte para todas
					dbo.setField(DboVwHojasPresentadas.CAMPO_OFICINA, oficina);
				}
				
				dbo.setFieldsToRetrieve(DboVwHojasPresentadas.CAMPO_AA_HOJA_PRES +"|"+ DboVwHojasPresentadas.CAMPO_NU_HOJA_PRES +"|"+
						DboVwHojasPresentadas.CAMPO_SERVICIO +"|"+ DboVwHojasPresentadas.CAMPO_OFICINA +"|"+ DboVwHojasPresentadas.CAMPO_PRESENTANTE
						+"|"+ DboVwHojasPresentadas.CAMPO_ID_USUARIO + "|"+  DboVwHojasPresentadas.CAMPO_TS_PRESENTACION + "|"  +
						DboVwHojasPresentadas.CAMPO_MAIL_PRES
				);
				dbo.setAppendWhereClause(" ts_presentacion "+" >= "+ 
						fechaInicio
						+"and"+   " ts_presentacion "+" <" +fechaFin
						+"and" +  " servicio like 'Solicitud de Reserva de Denominacion%'" + " order by nu_hoja_pres");
				listaHojas = dbo.searchAndRetrieveList();
				
				if (listaHojas.size()>0){
					for(int i =0; i<listaHojas.size(); i++){
						DboVwHojasPresentadas view = (DboVwHojasPresentadas)listaHojas.get(i);
						ReportesBean rpt = new ReportesBean();
						rpt.setAniooHoja(view.getField(DboVwHojasPresentadas.CAMPO_AA_HOJA_PRES));
						rpt.setNumHoja(view.getField(DboVwHojasPresentadas.CAMPO_NU_HOJA_PRES));
						rpt.setServicio(view.getField(DboVwHojasPresentadas.CAMPO_SERVICIO));
						rpt.setOficina(view.getField(DboVwHojasPresentadas.CAMPO_OFICINA));
						rpt.setPresentante(view.getField(DboVwHojasPresentadas.CAMPO_PRESENTANTE));
						rpt.setUsuario(view.getField(DboVwHojasPresentadas.CAMPO_ID_USUARIO));
						rpt.setMail(view.getField(DboVwHojasPresentadas.CAMPO_MAIL_PRES));
						rpt.setFecha(view.getField(DboVwHojasPresentadas.CAMPO_TS_PRESENTACION));
						listaDetalleHojas.add(rpt);
					}
				}
				req.setAttribute("tipoReporte", presentadas);
			}
			/** reporte para presentadas **/
			/** reporte para trabajajadas**/
			if("02".equals(trabajadas)){
				ResultSet rs = null;
				Statement stm = null;
				StringBuilder str = new StringBuilder();
				str.append("select aa_hoja_pres, num_hoja_pres, oficina, ano_titu, num_titu,servicio, ns_detalle,estado, ts_presentacion, trunc(plazo,2)");
				str.append(" from user1.v_hojas_trabajadas t");
				str.append(" where  ns_detalle = (select max(ns_detalle )");
				str.append(" from user1.v_hojas_trabajadas h");
				str.append(" where h.aa_hoja_pres = t.aa_hoja_pres");
				str.append(" and   h.num_hoja_pres = t.num_hoja_pres");
				str.append(" and   h.oficina = t.oficina)");
				str.append(" and t.servicio like 'Solicitud de Reserva de Denominacion%'");
				str.append(" and ts_presentacion "+" >= "+ 
						fechaInicio
						+"and"+   " ts_presentacion "+" <" +fechaFin);
				if(!"01".equals(oficina) && "02".equals(trabajadas)){//reporte para todas
					str.append("and t.oficina =" + "'"+ oficina +"'");
				}
				
				System.out.println(str);
				stm = conn.createStatement();
	            rs = stm.executeQuery(str.toString());
	            while (rs.next()) {
	            	ReportesBean rpt = new ReportesBean();
	            	rpt.setAniooHoja(rs.getString(1));
	            	rpt.setNumHoja(rs.getString(2));
	            	rpt.setOficina(rs.getString(3));
	            	rpt.setAnioTitu(rs.getString(4));
	            	rpt.setNumTitu(rs.getString(5));
	            	rpt.setServicio(rs.getString(6));
	            	rpt.setNsDetalle(String.valueOf(rs.getInt(7)));
	            	rpt.setEstado(rs.getString(8));
	            	rpt.setFecha(rs.getString(9));
	            	rpt.setPlazo(String.valueOf(rs.getInt(10)));
	            	listaDetalleHojas.add(rpt);
	            }
	            req.setAttribute("tipoReporte", trabajadas);
			}
			/** reporte para trabajajadas**/
			req.setAttribute("arrHojas", listaDetalleHojas);
			response.setStyle("muestraResultadoReporte");
			System.out.println(response.getStyle());
			dbo.clearAll();
			listaHojas = null;
			listaDetalleHojas=null;
			
		}catch(Exception ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
			ex.printStackTrace();
		}finally{
			pool.release(conn);
			end(request);
		}
		
		return response;
	}
	
	private String tipoParticipantes(String valor){
		
		String tipo;
		
		switch ( Integer.parseInt(valor) ) {
	      case 1:
	    	  tipo="Titular";
	           break;
	      case 2:
	    	  tipo="Socio";
	           break;
	      case 3:
	    	  tipo="Abogado";
	           break;
	      case 4:
	    	  tipo="Notario";
	           break;
	      default:
	          tipo=""; 
	    	  System.out.println("error al asignar tipo de presentante..." );
	           break;
	      }
	
		return tipo;

		
	}
	
protected ControllerResponse runConsultaEstadoSolicState(ControllerRequest request, ControllerResponse response) throws Exception {
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = null;
		Connection conn = null;
		DBConnection myConn = null;
		ArrayList lista = null;
		Denominacion deno = new Denominacion();
		String url = null;
		String mensaje = null;
		String estadoTituloId = null;
				
		try
		{
			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@172.18.1.55:1521:dbsp", "user1", "oraclerac");
			myConn = new DBConnection(conn);
			String anioHoja = req.getParameter("cboAnio");
			String numHoja = req.getParameter("numSolic");
			DboMensajeMail dboMen = new DboMensajeMail(myConn);
			dboMen.setFieldsToRetrieve(DboMensajeMail.CAMPO_REFNUM_TITU);
			dboMen.setField(DboMensajeMail.CAMPO_AA_HOJA_PRES, anioHoja);
			dboMen.setField(DboMensajeMail.CAMPO_NU_HOJA_PRES, numHoja);
			lista = dboMen.searchAndRetrieveList();
			if (lista.size()>0){ //recupera resultados
				deno.setNumeroHoja(numHoja);
				deno.setAnio(anioHoja);
				DboMensajeMail dbo = (DboMensajeMail)lista.get(0);
				deno.setRefNumTitu(dbo.getField(DboMensajeMail.CAMPO_REFNUM_TITU));
				DboTitulo titu = new DboTitulo(myConn);
				titu.setFieldsToRetrieve(DboTitulo.CAMPO_NUM_TITU + "|" + DboTitulo.CAMPO_ANO_TITU + "|" + DboTitulo.CAMPO_TS_PRESENT);
				titu.setField(DboTitulo.CAMPO_REFNUM_TITU, deno.getRefNumTitu());
				if (titu.find()){
					deno.setNumeroTitulo(titu.getField(DboTitulo.CAMPO_NUM_TITU));
					deno.setAnioTitu(titu.getField(DboTitulo.CAMPO_ANO_TITU));
					deno.setFechaPresTitu(titu.getField(DboTitulo.CAMPO_TS_PRESENT));
				}
				DboActosTitulo dboActo = new DboActosTitulo(myConn);
				dboActo.setFieldsToRetrieve(DboActosTitulo.CAMPO_COD_ACTO);
				dboActo.setField(DboActosTitulo.CAMPO_REFNUM_TITU, deno.getRefNumTitu());
				if (dboActo.find())
					deno.setCodigoActo(dboActo.getField(DboActosTitulo.CAMPO_COD_ACTO));
				DboTmActo tmActo = new DboTmActo(myConn);
				tmActo.setFieldsToRetrieve(DboTmActo.CAMPO_DESCRIPCION + "|" + DboTmActo.CAMPO_COD_LIBRO);
				tmActo.setField(DboTmActo.CAMPO_COD_ACTO, deno.getCodigoActo());
				if (tmActo.find()){
					deno.setDescActo(tmActo.getField(DboTmActo.CAMPO_DESCRIPCION));
					deno.setCodigoLibro(tmActo.getField(DboTmActo.CAMPO_COD_LIBRO));
				}
				DboTmLibro dboLibro = new DboTmLibro(myConn);
				dboLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
				dboLibro.setField(DboTmLibro.CAMPO_COD_LIBRO, deno.getCodigoLibro());//
				if (dboLibro.find()==true)
					deno.setDescLibro(dboLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));
					
				DboDetalleTitulo dboDetalle = new DboDetalleTitulo(myConn);
				dboDetalle.setFieldsToRetrieve(DboDetalleTitulo.CAMPO_ESTADO_TITULO_ID);
				dboDetalle.setField(DboDetalleTitulo.CAMPO_REFNUM_TITU, deno.getRefNumTitu());
				dboDetalle.setField(DboDetalleTitulo.CAMPO_FG_ACTIVO, "1");
				if (dboDetalle.find())
					estadoTituloId = dboDetalle.getField(DboDetalleTitulo.CAMPO_ESTADO_TITULO_ID);
				DboTmEstadoTitulo dboEstado = new DboTmEstadoTitulo(myConn);
				dboEstado.setFieldsToRetrieve(DboTmEstadoTitulo.CAMPO_MENSAJE + "|" + DboTmEstadoTitulo.CAMPO_ESTADO);
				dboEstado.setField(DboTmEstadoTitulo.CAMPO_ESTADO_TITULO_ID, estadoTituloId);
				if (dboEstado.find()){
					deno.setMensaje(dboEstado.getField(DboTmEstadoTitulo.CAMPO_MENSAJE));
					deno.setEstado(dboEstado.getField(DboTmEstadoTitulo.CAMPO_ESTADO));
				}
				//recuperando datos del presente
				DboTPHojaPres dboTpHojaPres = new DboTPHojaPres(myConn);
				dboTpHojaPres.setFieldsToRetrieve(DboTPHojaPres.CAMPO_AP_PATE_PRES + "|" + DboTPHojaPres.CAMPO_AP_MATE_PRES
						+ "|" + DboTPHojaPres.CAMPO_NO_PRES + "|" + DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN + "|" + DboTPHojaPres.CAMPO_NU_DOCU
						+ "|" + DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES + "|" + DboTPHojaPres.CAMPO_CO_REGI_PRES
				);
				dboTpHojaPres.setField(DboTPHojaPres.CAMPO_AA_HOJA_PRES, deno.getAnio());
				dboTpHojaPres.setField(DboTPHojaPres.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
				if (dboTpHojaPres.find()==true){
					Presentante pres = new Presentante();
					pres.setApeMaterno(dboTpHojaPres.getField(DboTPHojaPres.CAMPO_AP_MATE_PRES));
					pres.setApePaterno(dboTpHojaPres.getField(DboTPHojaPres.CAMPO_AP_PATE_PRES));
					pres.setNombre(dboTpHojaPres.getField(DboTPHojaPres.CAMPO_NO_PRES));
					pres.setDescDocu(dboTpHojaPres.getField(DboTPHojaPres.CAMPO_DE_TI_DOCU_IDEN));
					pres.setNumDocu(dboTpHojaPres.getField(DboTPHojaPres.CAMPO_NU_DOCU));
					deno.setCoOficRegiPres(dboTpHojaPres.getField(DboTPHojaPres.CAMPO_CO_OFIC_RGST_PRES));
					deno.setCoRegiPres(dboTpHojaPres.getField(DboTPHojaPres.CAMPO_CO_REGI_PRES));
					//deno.setPresentante(pres);
					req.setAttribute("presentante", pres);
				}
				DboVwHojasTrabajadas dboTrab = new DboVwHojasTrabajadas(myConn);
				dboTrab.setFieldsToRetrieve(DboVwHojasTrabajadas.CAMPO_OFICINA);
				dboTrab.setField(DboVwHojasTrabajadas.CAMPO_NUM_HOJA_PRES, deno.getNumeroHoja());
				dboTrab.setField(DboVwHojasTrabajadas.CAMPO_AA_HOJA_PRES, deno.getAnio());
				ArrayList listaOfic = dboTrab.searchAndRetrieveList();
				if (listaOfic.size()>0){
					DboVwHojasTrabajadas dboTrb = (DboVwHojasTrabajadas)listaOfic.get(0);
					deno.setDescOficina(dboTrb.getField(DboVwHojasTrabajadas.CAMPO_OFICINA));
				}
				//RECUPERANDO PARTICIPANTES PERSONA NATURAL
				DboTTPersNatuTitu dboTtPersNatu = new DboTTPersNatuTitu(myConn);
				dboTtPersNatu.setFieldsToRetrieve(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU +"|"+ DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU +"|"+
				DboTTPersNatuTitu.CAMPO_NO_PERS_NATU );
				dboTtPersNatu.setField(dboTtPersNatu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
				dboTtPersNatu.setField(dboTtPersNatu.CAMPO_AA_HOJA_PRES, deno.getAnio());
				ArrayList arr2 = dboTtPersNatu.searchAndRetrieveList();
				ArrayList<Participantes> listaParticipantes = new ArrayList<Participantes>();
				Iterator iterator2 = arr2.iterator();
				while (iterator2.hasNext()) {
					Participantes part = new Participantes();
					DboTTPersNatuTitu element = (DboTTPersNatuTitu) iterator2.next();
					part.setNombre(element.getField(DboTTPersNatuTitu.CAMPO_NO_PERS_NATU));
					part.setApePaterno(element.getField(DboTTPersNatuTitu.CAMPO_AP_PATE_PERS_NATU));
					part.setApeMaterno(element.getField(DboTTPersNatuTitu.CAMPO_AP_MATE_PERS_NATU));
					part.setTipoParticipante("PN");
					listaParticipantes.add(part);
				}
				DboTTPersJuriTitu dboJuri3 = new DboTTPersJuriTitu(myConn);
				dboJuri3.setFieldsToRetrieve(DboTTPersJuriTitu.CAMPO_RZ_SOCL);
				dboJuri3.setField(DboTTPersJuriTitu.CAMPO_AA_HOJA_PRES, deno.getAnio());
				dboJuri3.setField(DboTTPersJuriTitu.CAMPO_NU_HOJA_PRES, deno.getNumeroHoja());
				dboJuri3.setAppendWhereClause(DboTTPersJuriTitu.CAMPO_TI_PRTC + " <> '000' " );
				ArrayList arr3 = dboJuri3.searchAndRetrieveList();
				Iterator iterator3 = arr3.iterator();
				ArrayList listaParticipantesJuri = new ArrayList();
				while (iterator3.hasNext()) {
					Participantes part = new Participantes();
					DboTTPersJuriTitu element = (DboTTPersJuriTitu) iterator3.next();
					part.setRazonSocial(element.getField(DboTTPersJuriTitu.CAMPO_RZ_SOCL));
					part.setTipoParticipante("PJ");
					listaParticipantesJuri.add(part);
				}
				req.setAttribute("denominacion", deno);
				req.setAttribute("muestraResultados", "1");
				req.setAttribute("participantesNaturales", listaParticipantes);
				req.setAttribute("participantesJuridicos", listaParticipantesJuri);
				url = "resulEstadoSolic";
			}
			else{//mensaje de que no se encuentran los datos del titulo
				mensaje = "No se encontraron resultados para la hoja: " + numHoja + " presentada el año: " + anioHoja; 
				req.setAttribute("mensaje", mensaje);
				req.setAttribute("muestraResultados", "0");//o muestra mensaje - 1 muestra resultados
				url = "resulEstadoSolic";
			}
			response.setStyle(url);
		} 
		catch(Exception ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
			ex.printStackTrace();
		}
		finally {
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
}