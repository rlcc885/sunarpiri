package gob.pe.sunarp.iri.publicidad.controller;

//paquetes del sistema
import java.util.ArrayList;
import java.util.Vector;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import com.jcorporate.expresso.core.misc.*;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;

//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.framework.session.*;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.publicidad.service.ConsultaIndicePartidasPersonaJurídicaService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultaIndicePartidasPersonaNaturalService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultaIndicePartidasxBienesService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultaIndicePartidasxTipoNumeroDocumentoService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasPersonaJuridicaService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasPersonaNaturalService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasxBienesService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasxTipoNumeroDocumentoService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarPartidaDirectaService;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultaIndicePartidasPersonaJurídicaServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultaIndicePartidasxBienesServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultaIndicePartidasxTipoNumeroDocumentoServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarIndicePartidasPersonaNaturalServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasPersonaJuridicaServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasPersonaNaturalServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasxBienesServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasxTipoNumeroDocumentoServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarPartidaDirectaServiceImpl;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.transaction.bean.*;

public class BuscarXIndiceController extends ControllerExtension implements Constantes {
	private String thisClass = BuscarXIndiceController.class.getName() + ".";

	public BuscarXIndiceController() {
		super();
		addState(new State("verFormulario", "muestra formulario de busqueda"));
		addState(new State("buscarPersonaNatural", "buscar por persona natural"));
		addState(new State("buscarPersonaJuridica", "buscar por persona juridica"));
		addState(new State("buscarRazonSocial", "b"));
		addState(new State("buscarPredio", "b"));
		addState(new State("buscarMineria", "b"));
		addState(new State("buscarEmbarcacion", "b"));
		addState(new State("buscarBuque", "b"));
		addState(new State("buscarAeronaveXMatricula", "b"));
		addState(new State("buscarAeronaveXNombre", "b"));
		addState(new State("buscarAeronaveXRazonSocial", "b"));
		addState(new State("buscarXNombreVehicular", "b"));
		addState(new State("buscarXPlaca", "b"));
		addState(new State("buscarXNombreVehicularJuri", "b"));
		addState(new State("buscarXNombreVehicularMotor", "b"));
		addState(new State("buscarXNombreVehicularChasis", "b"));
		addState(new State("buscarPersonaNaturalRmc", "buscar por persona natural Rmc"));
		addState(new State("buscarPersonaJuridicaRmc", "buscar por persona juridica Rmc"));
	    addState(new State("buscarTipoNumeroDocumentoRmc", "buscar por tipo y número de documento Rmc"));
		addState(new State("buscarBienesRmc", "buscar por bien Rmc"));
		addState(new State("buscarPersonaNaturalSigc", "buscar por persona natural"));
		addState(new State("buscarPersonaJuridicaSigc", "buscar por persona juridica"));
		addState(new State("buscarTipoNumeroDocumentoSigc", "buscar por tipo y número de documento"));
		addState(new State("buscarBienesSigc", "buscar por bien"));
		setInitialState("verFormulario");

	}


	public ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


		try {
			init(request);
			validarSesion(request);


			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			StringBuffer sb = new StringBuffer();
			ComboBean bean1 = null;


			//	El TIPO DE PARTICIPANTE es fijo: NATURAL = N o JURIDICO = J
			java.util.ArrayList arrx = new java.util.ArrayList();
			java.util.ArrayList arry = new java.util.ArrayList();
			bean1 = new ComboBean();
			bean1.setCodigo("N");
			bean1.setDescripcion("Natural");
			arrx.add(bean1);
			bean1 = new ComboBean();
			bean1.setCodigo("J");
			bean1.setDescripcion("Juridico");
			arrx.add(bean1);
			req.setAttribute("arreglo3", arrx); //  tipo participante

			//departamentos
			DboTmDepartamento dboTmDepartamento = new DboTmDepartamento(dconn);
			dboTmDepartamento.setFieldsToRetrieve(DboTmDepartamento.CAMPO_DPTO_ID+"|"+
			                                      DboTmDepartamento.CAMPO_NOMBRE);
			dboTmDepartamento.setField(DboTmDepartamento.CAMPO_ESTADO,"1");
			dboTmDepartamento.setField(DboTmDepartamento.CAMPO_PAIS_ID,"01");
			arrx = new java.util.ArrayList();
			arry = new java.util.ArrayList();
			arrx = dboTmDepartamento.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
			for (int x =0; x < arrx.size(); x++)
			{
				Value05Bean bean = new Value05Bean();
				DboTmDepartamento dbo = (DboTmDepartamento) arrx.get(x);
				bean.setValue01(dbo.getField(dbo.CAMPO_DPTO_ID));
				bean.setValue02(dbo.getField(dbo.CAMPO_NOMBRE));
				arry.add(bean);
			}
			req.setAttribute("arrDepartamentos",arry);

			//provincias
			DboTmProvincia dboTmProvincia = new DboTmProvincia(dconn);
			dboTmProvincia.setFieldsToRetrieve(DboTmProvincia.CAMPO_DPTO_ID+"|"+
											   DboTmProvincia.CAMPO_PROV_ID+"|"+
											   DboTmProvincia.CAMPO_NOMBRE);
			dboTmProvincia.setField(DboTmProvincia.CAMPO_ESTADO,"1");
			dboTmProvincia.setField(DboTmProvincia.CAMPO_PAIS_ID,"01");
			arrx = new java.util.ArrayList();
			arry = new java.util.ArrayList();
			arrx = dboTmProvincia.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
			for (int x =0; x < arrx.size(); x++)
			{
				Value05Bean bean = new Value05Bean();
				DboTmProvincia dbo = (DboTmProvincia) arrx.get(x);
				bean.setValue01(dbo.getField(dbo.CAMPO_DPTO_ID));
				bean.setValue02(dbo.getField(dbo.CAMPO_PROV_ID));
				bean.setValue03(dbo.getField(dbo.CAMPO_NOMBRE));
				arry.add(bean);
			}
			req.setAttribute("arrProvincias",arry);

			arry = new java.util.ArrayList();
			String pidedis = req.getParameter("pidedis");

			//distritos
			DboTmDistrito dboTmDistrito = new DboTmDistrito(dconn);
			dboTmDistrito.setFieldsToRetrieve(DboTmDistrito.CAMPO_DPTO_ID+"|"+
			                                  DboTmDistrito.CAMPO_PROV_ID+"|"+
			                                  DboTmDistrito.CAMPO_DIST_ID+"|"+
			                                  DboTmDistrito.CAMPO_NOMBRE);
			dboTmDistrito.setField(DboTmDistrito.CAMPO_ESTADO,"1");
			dboTmDistrito.setField(DboTmDistrito.CAMPO_PAIS_ID,"01");
			if (pidedis==null)
			{
				//es la primera vez que se carga la pantalla
				//y solamente deben mostrarse distritos de Lima
				dboTmDistrito.setField(DboTmDistrito.CAMPO_DPTO_ID,"15");
				dboTmDistrito.setField(DboTmDistrito.CAMPO_PROV_ID,"01");
				req.setAttribute("depLima","15");
				req.setAttribute("proLima","01");
			}
			else
			{
				InputBusqIndirectaBean beanb = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, 0);
				req.setAttribute("beanb",beanb);
			}
				arrx = new java.util.ArrayList();

				arrx = dboTmDistrito.searchAndRetrieveList(DboTmDistrito.CAMPO_NOMBRE);
				for (int x = 0; x < arrx.size(); x++)
				{
					Value05Bean bean = new Value05Bean();
					DboTmDistrito dbo = (DboTmDistrito) arrx.get(x);
					bean.setValue01(dbo.getField(dbo.CAMPO_DPTO_ID));
					bean.setValue02(dbo.getField(dbo.CAMPO_PROV_ID));
					bean.setValue03(dbo.getField(dbo.CAMPO_DIST_ID));
					bean.setValue04(dbo.getField(dbo.CAMPO_NOMBRE));
					arry.add(bean);
				}
			req.setAttribute("arrDistritos",arry);

			//combo tipo zona
			DboTipoZona dboTipoZona = new DboTipoZona(dconn);


			dboTipoZona.setFieldsToRetrieve(DboTipoZona.CAMPO_TIPO_ZONA + "|" + DboTipoZona.CAMPO_DESC_CORTA);


			dboTipoZona.setField(DboTipoZona.CAMPO_ESTADO, "1");
			java.util.ArrayList arreglo8 = new java.util.ArrayList();
			arrx = dboTipoZona.searchAndRetrieveList(DboTipoZona.CAMPO_DESC_CORTA);
			for (int i = 0; i < arrx.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(
					((DboTipoZona) arrx.get(i)).getField(DboTipoZona.CAMPO_TIPO_ZONA));
				bean1.setDescripcion(
					((DboTipoZona) arrx.get(i)).getField(DboTipoZona.CAMPO_DESC_CORTA));
				arreglo8.add(bean1);
			}


			//combo tipo via


			DboTipoVia dboTipoVia = new DboTipoVia(dconn);


			dboTipoVia.setFieldsToRetrieve(
				DboTipoVia.CAMPO_TIPO_VIA + "|" + DboTipoVia.CAMPO_DESC_CORTA);


			dboTipoVia.setField(DboTipoVia.CAMPO_ESTADO, "1");
			java.util.ArrayList arreglo9 = new java.util.ArrayList();
			arrx = dboTipoVia.searchAndRetrieveList(DboTipoVia.CAMPO_DESC_CORTA);
			for (int i = 0; i < arrx.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(((DboTipoVia) arrx.get(i)).getField(DboTipoVia.CAMPO_TIPO_VIA));
				bean1.setDescripcion(
					((DboTipoVia) arrx.get(i)).getField(DboTipoVia.CAMPO_DESC_CORTA));
				arreglo9.add(bean1);
			}


			//combo tipo numeracion
			DboTipoNumeracion dboTipoNumeracion = new DboTipoNumeracion(dconn);


			dboTipoNumeracion.setFieldsToRetrieve(
				DboTipoNumeracion.CAMPO_TIPO_NUMER + "|" + DboTipoNumeracion.CAMPO_DESC_CORTA);


			dboTipoNumeracion.setField(DboTipoNumeracion.CAMPO_ESTADO, "1");
			java.util.ArrayList arreglo10 = new java.util.ArrayList();


			arrx =
				dboTipoNumeracion.searchAndRetrieveList(DboTipoNumeracion.CAMPO_DESC_CORTA);


			for (int i = 0; i < arrx.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(
					((DboTipoNumeracion) arrx.get(i)).getField(DboTipoNumeracion.CAMPO_TIPO_NUMER));
				bean1.setDescripcion(
					((DboTipoNumeracion) arrx.get(i)).getField(DboTipoNumeracion.CAMPO_DESC_CORTA));
				arreglo10.add(bean1);
			}


			//combo tipo interior
			DboTipoInterior dboTipoInterior = new DboTipoInterior(dconn);


			dboTipoInterior.setFieldsToRetrieve(
				DboTipoInterior.CAMPO_TIPO_INTER + "|" + DboTipoInterior.CAMPO_DESC_CORTA);


			dboTipoInterior.setField(DboTipoInterior.CAMPO_ESTADO, "1");
			java.util.ArrayList arreglo11 = new java.util.ArrayList();
			arrx = dboTipoInterior.searchAndRetrieveList(DboTipoInterior.CAMPO_DESC_CORTA);
			for (int i = 0; i < arrx.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(
					((DboTipoInterior) arrx.get(i)).getField(DboTipoInterior.CAMPO_TIPO_INTER));
				bean1.setDescripcion(
					((DboTipoInterior) arrx.get(i)).getField(DboTipoInterior.CAMPO_DESC_CORTA));
				arreglo11.add(bean1);
			}


			//Ademas, enviar el costo de la busqueda, segun tarifario
			DboTarifa dboTarifa = new DboTarifa(dconn);
			sb.delete(0, sb.length());
			sb.append(DboTarifa.CAMPO_SERVICIO_ID);
			sb.append(">= 20 AND ");
			sb.append(DboTarifa.CAMPO_SERVICIO_ID);
			/******* inicio: jrosas 12-07-07 ***/
			sb.append(" <= 35");//se cambio de 33 a 35. jascencio:16/07/07
			/******* fin: jrosas 12-07-07 ***/


			//dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			//Tarifario
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC+"|"+dboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA);

			dboTarifa.setCustomWhereClause(sb.toString());
			arrx = dboTarifa.searchAndRetrieveList(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA +"|"+DboTarifa.CAMPO_SERVICIO_ID);
			java.util.ArrayList arreglo12 = new java.util.ArrayList();
			/*
			//Tarifario
			Value05Bean bean5 = new Value05Bean();
			bean5.setValue01("0");
			bean5.setValue02("0");
			bean5.setValue03(((DboTarifa) arrx.get(i)).getField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA));
			arreglo12.add(bean5);
			*/
			int valor1 = 0;
			Value05Bean bean5 = null;
			for (int i = 0; i < arrx.size(); i++)
			{
				bean5 = new Value05Bean();
				if(valor1==0){
					bean5.setValue01(""+valor1);
					bean5.setValue02("0");
					bean5.setValue03(((DboTarifa) arrx.get(i)).getField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA));
					arreglo12.add(bean5);
					bean5 = new Value05Bean();
				}
				valor1+=1;
				bean5.setValue01("" + valor1);
				bean5.setValue02(((DboTarifa) arrx.get(i)).getField(DboTarifa.CAMPO_PREC_OFIC));
				//Tarifario
				bean5.setValue03(((DboTarifa) arrx.get(i)).getField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA));
				arreglo12.add(bean5);
				/*** inicio: jrosas 12-07-07 ***/
				if(valor1==13)//se cambio de 14 a 15. jascencio:16/07/07
					valor1 = 0;
				/*** fin: jrosas 12-07-07 ***/
			}

			//req.setAttribute("arreglo4", arreglo4); //  tipo documento
			//req.setAttribute("arreglo5", resultado2); //  tipo participaciocn


			req.setAttribute("arreglo8", arreglo8); //  tipo zonz
			req.setAttribute("arreglo9", arreglo9); //  tipo via
			req.setAttribute("arreglo10", arreglo10); //  tipo numeracion
			req.setAttribute("arreglo11", arreglo11); //  tipo interior
			req.setAttribute("arreglo12", arreglo12); //  tarifa-costo

			req.setAttribute("arrAreaRegistral", Tarea.getComboAreasRegistrales(dconn));
			/**** inicio: jrosas 12-07-07 ***/
			req.setAttribute("arrDocu",Tarea.getComboTiposDocumento(dconn));
			//req.setAttribute("arrAreaLibro", Tarea.getComboAreaLibro(conn,Constantes.BUSQ_SEDE_20,Constantes.SERVICIO_BUSQ_INDICE_PARTIDA_RMC));
			//Inicio:jascencio:16/07/07
			req.setAttribute("arrAreaLibro",Tarea.getComboAreaLibro (conn,Constantes.BUSQ_SEDE_20, Constantes. SERVICIO_BUSQ_INDICE_PARTIDA_RMC, Constantes. SERVICIO_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC));		
			//Fin:jascencio
			/**** fin: jrosas 12-07-07 ***/
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			if (usuario.getFgInterno()==true)
				req.setAttribute("flagInterno","1");
			else
				req.setAttribute("flagInterno","0");

			response.setStyle("verFormulario");

		} //try
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		return response;
	}
	//**************************************************************************

	public static final boolean trace = false;
	public static final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
	private String now() {
		return new StringBuffer(this.getClass().getName())
			.append(" ")
			.append(Thread.currentThread().getName())
			.append(" ")
			.append(formatter.format(new java.util.Date()))
			.toString();
	}
    
	/**** inicio: jrosas: 12-07-07 ******/
	public ControllerResponse runBuscarPersonaNaturalRmcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);
		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null;		
		
		try {
			init(request);
			validarSesion(request);

			inputBusqIndirectaBean = Tarea.recojeDatosRequestBusqIndirectaPartidaRMC(req);

			ConsultaIndicePartidasPersonaNaturalService consultarIndicePartidasPersonaNaturalService = new ConsultarIndicePartidasPersonaNaturalServiceImpl();
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			String amid_session = ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request));
			String session_id= sesion.getId();
			
			/** En caso que no exista, lo inserto, para que elimine temporales al finalizar sesion**/
			if(sesion.getAttribute("monitor")==null){
			
				MonitorDeSesion monitor = new MonitorDeSesion();
				sesion.setAttribute("monitor",monitor);
			}
			
			FormOutputBuscarPartida output = consultarIndicePartidasPersonaNaturalService.busquedaIndicePersonaNaturalRMC(ConsultaIndicePartidasPersonaNaturalService.MEDIO_CONTROLLER, inputBusqIndirectaBean, usuario, ipOrigen, session_id, amid_session);
			
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNaturalRmc&tipo=N");
			
			req.setAttribute("outnumber",inputBusqIndirectaBean.getCantidad());	
			req.setAttribute("rmc","rmc");		
			req.setAttribute("flagCertBusq", inputBusqIndirectaBean.getCodGrupoLibroArea());
			req.setAttribute("tarifa",""+output.getTarifa());				
			req.setAttribute("usuaEtiq",usuario.getUserId());				
			req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output", output);

			/*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", inputBusqIndirectaBean.getVerifica());
			/*fin:dbravo:14/09/2007*/ 
			
		    response.setStyle("resultadoPersonaNatural");
			
		}catch (ValidacionException e) {
			
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
			
		} catch (CustomException e) {
			
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					response.setStyle("pantallaFinal");
					req.setAttribute("destino","back");
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}


		return response;
}
		
	/**** fin: jrosas: 12-07-07 ******/

	
	/**
	 * Búsqueda Partidas X Indices 
	 * Buscar por Número de Partida
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarPersonaNaturalState(ControllerRequest request, ControllerResponse response)
		throws ControllerException
	{
		if (trace) System.err.println(now() + " Inicio.");

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmt   = null;
		ResultSet rset   = null;
		Statement stmtc   = null;
		ResultSet rsetc   = null;

		try
		{
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			ArrayList resultado = new ArrayList();
			Propiedades propiedades = Propiedades.getInstance();
			int conteo=0;
			boolean haySiguiente = false;

			// Inicio:mgarate:31/05/2007
			String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=9";;
			// Fin:mgarate:31/05/2007
			
			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_PERSONA_NATURAL);

			String apellidoPaterno = "";
			String apellidoMaterno = "";
			String nombre = "";
			String tipoParticipacion = "";

			StringBuffer sb = new StringBuffer();

			if (bean1.getHid2().equals("1"))
			{
				apellidoPaterno = bean1.getArea1ApePat().trim();
				//apellidoPaterno = Tarea.reemplazaApos(apellidoPaterno);
				apellidoMaterno = bean1.getArea1ApeMat().trim();
				//apellidoMaterno = Tarea.reemplazaApos(apellidoMaterno);
				nombre = bean1.getArea1Nombre().trim();
				//nombre = Tarea.reemplazaApos(nombre);
				tipoParticipacion = bean1.getArea1TipoParticipacion();
			}

			if (bean1.getHid2().equals("2"))
			{
				apellidoPaterno = bean1.getArea2ApePat().trim();
				//apellidoPaterno = Tarea.reemplazaApos(apellidoPaterno);
				apellidoMaterno = bean1.getArea2ApeMat().trim();
				//apellidoMaterno = Tarea.reemplazaApos(apellidoMaterno);
				nombre = bean1.getArea2Nombre().trim();
				//nombre = Tarea.reemplazaApos(nombre);
				tipoParticipacion = bean1.getArea2TipoParticipacion();
			}

			if (bean1.getHid2().equals("3"))
			{
				apellidoPaterno = bean1.getArea3ParticipanteApePat().trim();
				//apellidoPaterno = Tarea.reemplazaApos(apellidoPaterno);
				apellidoMaterno = bean1.getArea3ParticipanteApeMat().trim();
				//apellidoMaterno = Tarea.reemplazaApos(apellidoMaterno);
				nombre = bean1.getArea3ParticipanteNombre().trim();
				//nombre = Tarea.reemplazaApos(nombre);
				tipoParticipacion = bean1.getArea3TipoParticipacion();
			}

			/** Si existe parametro salto, significa que viene de mostrar resultados, es decir, no esta entrando por 1ra vez **/
			/* inicio, dbravo: 15/06/2007 */
			StringBuffer q  = null;
			StringBuffer q2 = null;
			StringBuffer qca = null;
			StringBuffer qcb = null;
			/* fin, dbravo: 15/06/2007 */
			if(req.getParameter("salto")==null)
			{
				/* inicio, dbravo: 15/06/2007 */	
				//_empieza busqueda
				q  = new StringBuffer();
				q2 = new StringBuffer();
				qca = new StringBuffer();
				qcb = new StringBuffer();
				/* fin, dbravo: 15/06/2007 */
				if (apellidoMaterno.length()>0)
				{
					q.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					q2.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					qca.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					qcb.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
				}
				else
				{
					q.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					q2.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					qca.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					qcb.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
				}


				// 2003-07-31 enviar el estado al JSP
				q.append(" PARTIDA.ESTADO as estado, ");
				q.append(" partida.refnum_part,  partida.cod_libro,     partida.num_partida,");
				q.append(" regis_publico.siglas, ofic_registral.nombre, prtc_nat.ape_pat,");
				q.append(" prtc_nat.ape_mat,     prtc_nat.nombres,      prtc_nat.nu_doc_iden,");
				q.append(" prtc_nat.ti_doc_iden, ind_prtc.cod_partic,   ind_prtc.estado AS estadoPartic");
				q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
				qca.append(" count(prtc_nat.cur_prtc) ");
				qcb.append(" count(partida.refnum_part) ");

				q.append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla, grupo_libro_area_det glad ");
				q.append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ");
				q.append(" AND ind_prtc.refnum_part = partida.refnum_part ");
				q.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
				q.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
				q.append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ");
				q.append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
				q.append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
				q.append(" and ape_pat like '").append(apellidoPaterno).append("%' ");
				appendCondicionEstadoPartida(q);

				q2.append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla, grupo_libro_area_det glad ");
				q2.append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ");
				q2.append(" AND ind_prtc.refnum_part = partida.refnum_part ");
				q2.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
				q2.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
				q2.append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ");
				q2.append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
				q2.append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
				q2.append(" and ape_pat like '").append(apellidoPaterno).append("%' ");
				appendCondicionEstadoPartida(q2);


				qca.append(" FROM prtc_nat");
				qca.append(" WHERE ");
				qca.append(" ape_pat like '").append(apellidoPaterno).append("%'");

				qcb.append(" FROM prtc_nat, ind_prtc, partida , grupo_libro_area gla, grupo_libro_area_det glad ");
				qcb.append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ");
				qcb.append(" AND ind_prtc.refnum_part = partida.refnum_part ");
				qcb.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
				qcb.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
				qcb.append(" and ape_pat like '").append(apellidoPaterno).append("%' ");
//				qcb.append(" and nombres like '").append(nombre).append("%' ");///made by Leo
				appendCondicionEstadoPartida(qcb);

				if (apellidoMaterno.length()>0)
					{
						q.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
						q2.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
						qca.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
						qcb.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
					}
				if (nombre.length()>0)
					{
						q.append(" and nombres like '").append(nombre).append("%'");
						q2.append(" and nombres like '").append(nombre).append("%'");
						qca.append(" and nombres like '").append(nombre).append("%'");
						qcb.append(" and nombres like '").append(nombre).append("%'");
					}

				StringBuffer sbregpub = new StringBuffer();
				if (bean1.getSedesElegidas().length==1)
					{
						sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
						qca.append(" AND prtc_nat.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
					}
				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					{
						sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
						qca.append(" AND prtc_nat.reg_pub_id IN ").append(bean1.getSedesSQLString());
					}
				q.append(sbregpub.toString());
				q2.append(sbregpub.toString());
				qcb.append(sbregpub.toString());

				//q.append(" AND area_reg_id = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
				q.append(" AND partida.cod_libro = glad.cod_libro  ");
				q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
				q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
				//q2.append(" AND area_reg_id = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
				q2.append(" AND partida.cod_libro = glad.cod_libro  ");
				q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
				q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
				//qcb.append(" AND area_reg_id = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
				qcb.append(" AND partida.cod_libro = glad.cod_libro  ");
				qcb.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
				qcb.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");

				if(bean1.getFlagIncluirInactivos()==false)
				{
					q.append(" AND IND_PRTC.ESTADO = '1'");
					q2.append(" AND IND_PRTC.ESTADO = '1'");
					qcb.append(" AND IND_PRTC.ESTADO = '1'");
				}

				q.append(" ORDER BY prtc_nat.ape_pat, prtc_nat.ape_mat, prtc_nat.nombres, ind_prtc.estado DESC, PARTIDA.REG_PUB_ID, partida.ofic_reg_id, partida.num_partida  ");

				//contar
				if (bean1.getFlagPagineo()==false)
				{
					//1er query ()
					if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
					stmtc   = conn.createStatement();
					rsetc   = stmtc.executeQuery(qca.toString());
					boolean bc = rsetc.next();
					conteo = rsetc.getInt(1);
					if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
						throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
					if (conteo==0)
						throw new ValidacionException("No se encontraron resultados para su búsqueda");

					//2er query ()
					if (isTrace(this)) System.out.println("___verqueryCOUNT_B__"+qcb.toString());
					rsetc   = stmtc.executeQuery(qcb.toString());
					bc = rsetc.next();
					conteo = rsetc.getInt(1);
					if (conteo > propiedades.getMaxResultadosBusqueda() )
						throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
					if (conteo==0)
						throw new ValidacionException("No se encontraron resultados para su búsqueda");

				}

				if (isTrace(this)) System.out.println("___verquery___"+q.toString());
				//UsoServicio_________________________________
							
				//descripcion area registral
				DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
				dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
				dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
				dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
				String descripcionAreaRegistral="";
				if (dboTmAreaRegistral.find() == true)
					descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

				//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();


				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(propiedades.getLineasPorPag());
				rset   = stmt.executeQuery(q.toString());
				/** Importante! Para pagineo.
					if (bean1.getSalto()>1)
						rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
				**/

				boolean b = rset.next();


				DboFicha dboFicha = new DboFicha(dconn);
				DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
				DboTmLibro dboTmLibro = new DboTmLibro(dconn);
				DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
				DboParticLibro dboParticLibro = new DboParticLibro(dconn);


				int conta=0;


				boolean nuevo = true;

				String antRefNumPart = "";
				String antRegPubDescripcion = "";
				String antnombre = "";
				String antEstadoAct = "";

				StringBuffer cadenaPart = new StringBuffer();

				PartidaBean partidaBean = new PartidaBean();

				while (b==true)
				{

					//_completar los detalles de la partida encontrada
					String refNumPart = rset.getString("refnum_part");
					String oficRegDesc = rset.getString("nombre");
					String codLibro   = rset.getString("cod_libro");
					String estadoAct = "";
					//if (rset.getString("estadoPartic").startsWith("1"))
					if ((rset.getString("estadoPartic")!=null)&&(rset.getString("estadoPartic").startsWith("1")))
					{
						estadoAct = "Activo";
					}
					else
					{
						estadoAct = "Inactivo";
					}

					sb.delete(0,sb.length());
					/*
					String ape_mat = rset.getString("ape_mat").trim();
					if (ape_mat==null)
						ape_mat="";
					String nombres = rset.getString("nombres").trim();
					if (nombres==null)
						nombres="";
					sb.append(rset.getString("ape_pat").trim()).append(" ");
					sb.append(ape_mat).append(", ");
					sb.append(nombres);
					*/
					String ape_mat = rset.getString("ape_mat")==null?"":rset.getString("ape_mat").trim();
					String nombres = rset.getString("nombres")==null?"":rset.getString("nombres").trim();
					sb.append(rset.getString("ape_pat")==null?"":rset.getString("ape_pat").trim()).append(" ");
					sb.append(ape_mat).append(", ");
					sb.append(nombres);
					String nombreAct = sb.toString();

					//hp
					//if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)))
					if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)) && (antnombre.equals(nombreAct)))
					{
						nuevo = false;
						/*
						if(!antnombre.equals(nombreAct))
						{
							cadenaPart.append(" ; ").append(nombreAct);
							antnombre = nombreAct;
						}
						*/
					}
					else
					{
						if(!antRefNumPart.equals(""))
						{
							partidaBean.setParticipanteDescripcion(cadenaPart.toString());
							resultado.add(partidaBean);
							conta++;
							partidaBean = new PartidaBean();

						}

						antRefNumPart = refNumPart;
						antRegPubDescripcion = oficRegDesc;
						antnombre = nombreAct;
						antEstadoAct = estadoAct;

						cadenaPart.delete(0,cadenaPart.length());
						cadenaPart.append(nombreAct);
						nuevo = true;
					}

					if(nuevo)
					{

						partidaBean.setRefNumPart(refNumPart);
						partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
						partidaBean.setNumPartida(rset.getString("num_partida"));
						partidaBean.setRegPubDescripcion(rset.getString("siglas"));
						partidaBean.setOficRegDescripcion(oficRegDesc);
	// 2003-07-31 enviar el estado al JSP
						partidaBean.setEstado(rset.getString("estado"));

						partidaBean.setEstadoInd(estadoAct);



						//ficha
						dboFicha.clearAll();
						sb.delete(0, sb.length());
						sb.append(dboFicha.CAMPO_FICHA).append("|");
						sb.append(dboFicha.CAMPO_FICHA_BIS);
						dboFicha.setFieldsToRetrieve(sb.toString());
						dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
						if (dboFicha.find() == true)
								{
									partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
									String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
									int nbis = 0;
									try {
										nbis = Integer.parseInt(bis);
									}
									catch (NumberFormatException n)
									{
										nbis =0;
									}
									if (nbis>=1)
										partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
								}

						//obtener tomo y foja
						dboTomoFolio.clearAll();
						sb.delete(0, sb.length());
						sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
						sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
						sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
						sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
						dboTomoFolio.setFieldsToRetrieve(sb.toString());
						dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
						if (dboTomoFolio.find() == true)
								{
									partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
									partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

									String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
									String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

									if (bist.trim().length() > 0)
											partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);

									if (bisf.trim().length() > 0)
											partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

									//28dic2002
									//quitar el caracter "9" del inicio del tomoid
										if (partidaBean.getTomoId().length()>0)
										{
											if (partidaBean.getTomoId().startsWith("9"))
												{
													String ntomo = partidaBean.getTomoId().substring(1);
													partidaBean.setTomoId(ntomo);
												}
										}
								}

						//descripcion libro
						dboTmLibro.clearAll();
						dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
						dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
						if (dboTmLibro.find() == true)
							partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));


						//participante y su número de documento
						String tipoDocumento="";
						String codPartic="";

						String nuDocIden = rset.getString("nu_doc_iden");
						if ((nuDocIden==null) || (nuDocIden.trim().length()==0))
							partidaBean.setParticipanteNumeroDocumento("&nbsp;");
						else
							partidaBean.setParticipanteNumeroDocumento(nuDocIden);


						tipoDocumento = rset.getString("ti_doc_iden");
						codPartic     = rset.getString("cod_partic");

						//descripción de documento
						if (tipoDocumento!=null)
						{
							if (tipoDocumento.trim().length()>0)
							{
								dboTmDocIden.clearAll();
								dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
								dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
								dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO, "1");
								if (dboTmDocIden.find() == true)
									partidaBean.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
							}
						}

						//descripción del tipo de participación
						dboParticLibro.clearAll();
						dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
						dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
						dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
						dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
							if (dboParticLibro.find()==true)
								partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));


					}

					b = rset.next();
				/**Importante para pagineo
					if (conta==propiedades.getLineasPorPag())
					{
						if(b==true)
							haySiguiente = true;

						break;
					}
				**/

				}//while (b==true)

				partidaBean.setParticipanteDescripcion(cadenaPart.toString());
				resultado.add(partidaBean);
				conta++;

				if (resultado.size()==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

				//sesion.setAttribute("resultado",resultado);

				/** En caso que no exista, lo inserto, para que elimine temporales al finalizar sesion**/
				if(sesion.getAttribute("monitor")==null)
				{
					MonitorDeSesion monitor = new MonitorDeSesion();
					sesion.setAttribute("monitor",monitor);
				}

				/** Borro temporal ya que esta puede no ser primera consulta **/
				sb.delete(0,sb.length());
				sb.append("DELETE FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
				stmt   = conn.createStatement();
				stmt.executeUpdate(sb.toString());

				/** Inserto blob vacio **/
				sb.delete(0,sb.length());
				sb.append("INSERT INTO DATA_SESSION(SESSION_ID, NOMBRE, TS_CREACION, VALOR) VALUES('").append(sesion.getId()).append("','").append("BusqIndirectaPersNat").append("', SYSDATE, empty_blob())");
				stmt   = conn.createStatement();
				rset   = stmt.executeQuery(sb.toString());

				/** Selecciono el blob como referencia, para actualizacion **/
				sb.delete(0,sb.length());
				sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("' FOR UPDATE");
				stmt   = conn.createStatement();
				rset   = stmt.executeQuery(sb.toString());

				oracle.sql.BLOB campoBlob = null;

				if(rset.next())
				{
					campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

				}
				else
				{
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
				}


				/** Referencio el blob vacio hacia el outputStream, y luego calculo el tamaño del buffer que me servira para llenar el blob **/
				//java.io.ObjectInputStream ioStr = null;
				java.io.OutputStream outstream = campoBlob.getBinaryOutputStream();
				int j = -1;
				int bSize = campoBlob.getBufferSize(); // buffer del campo blob

				byte buffer[] = new byte[bSize];

				/** El Arraylist lo serializo, para introducirlo en el inputStream **/
				java.io.InputStream instream = gob.pe.sunarp.extranet.common.Serializacion.getInstance().serializarAStream(resultado);

				/** El inputStream lo introduzco de a pocos en el outputStream, que tiene la referencia hacia el blob, asi que lo llena. **/
				j = instream.read(buffer);
				while (j != -1) {
					outstream.write(buffer, 0, j);
					j = instream.read(buffer);
				}

				/** Cierro los streams **/
				outstream.close();
				instream.close();



			}  // fin if(req.getParameter("salto")==null)
			else
			{

				/** Caso contrario, consulta ya fue hecha, debo recuperar de tabla temporal **/

				//resultado = (java.util.ArrayList)sesion.getAttribute("resultado");

				sb.delete(0,sb.length());
				sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
				stmt   = conn.createStatement();
				rset   = stmt.executeQuery(sb.toString());
				if(rset.next())
				{
					/** Recupero el Blob, serializado **/
					oracle.sql.BLOB campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

					/** Para poder usarlo, debo deserializarlo **/
					resultado = (ArrayList) gob.pe.sunarp.extranet.common.Serializacion.getInstance().deserializar(campoBlob.binaryStreamValue());
				}
				else
				{
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
				}
			}


			/** Importante! Para pagineo.
			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
			**/
			/**Importante para pagineo
				if (conta==propiedades.getLineasPorPag())
				{
					if(b==true)
						haySiguiente = true;

					break;
				}
			**/

			java.util.ArrayList resultado2 = new java.util.ArrayList();
			int pagina = 0;


			if(req.getParameter("salto")!=null)
				pagina = Integer.parseInt((String)req.getParameter("salto"));

			int i = pagina * propiedades.getLineasPorPag();
			conteo = resultado.size();
			while ( (i < ((pagina+1) * propiedades.getLineasPorPag())) && (i<conteo))
			{
				resultado2.add(resultado.get(i));
				i++;
			}
			if(i<conteo)
				haySiguiente = true;

			FormOutputBuscarPartida output = new FormOutputBuscarPartida();
			if (bean1.getFlagIncluirInactivos()==true)
				output.setFlagEstado(true);
			output.setResultado(resultado2);
			if (bean1.getFlagPagineo()==false)
				output.setCantidadRegistros(String.valueOf(conteo));
			else
				output.setCantidadRegistros(bean1.getCantidad());

			//calcular numero para boton "retroceder pagina"
			/*if (bean1.getSalto()==1)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(bean1.getSalto()-1);
			*/
			if (pagina==0)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(pagina-1);

			//calcular numero para boton "avanzar pagina"
			/*if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(bean1.getSalto()+1);
			*/
			if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(pagina+1);

			//calcular regs del x al y
			int del = (pagina*propiedades.getLineasPorPag())+1;
			int al  = del+resultado2.size()-1;
			output.setNdel(del);
			output.setNal(al);


			//ETIQUETA

			// recuperamos el costo de la visualizacion
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());


			if (dboTarifa.find())
			{
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}
			req.setAttribute("tarifa",""+tarifa);
			// recuperamos el usuario
			String usuaEtiq = usuario.getUserId();
			req.setAttribute("usuaEtiq",usuaEtiq);
			// recuperamos la fecha Actual
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			req.setAttribute("fechaAct",fechaAct);



			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNatural");
			req.setAttribute("output", output);

			// Inicio:mgarate:31/05/2007
			   req.setAttribute("criterioBusqueda",criterioBusqueda);
			   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
			// Fin:mgarate:31/05/2007
			
		    /*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", bean1.getVerifica());
			/*fin:dbravo:14/09/2007*/ 
			   
			response.setStyle("resultadoPersonaNatural");

			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();

				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart(apellidoPaterno + " " + apellidoMaterno + " " + nombre);
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(tipoParticipacion);
				bt.setTipoPersPart("N");
				/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
				*/
				
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					/**
					 *  inicio, dbravo: 15/06/2007
					 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					 *  			   inicialmente el Job002.
					 */
					if(q2!=null){
					
						if (bean1.getFlagPagineo()==false)
						{
							/*
							validar que el usuario NO sea de zona WEB
							*/
							if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
							{
								//estamos en la primera llamada
								//enviamos TODOS los registros encontrados
								//a otro Thread para que registre el UsoServicio
									Job004 j = new Job004();
									j.setQuery(q2.toString());
									j.setUsuario(usuario);
									j.setCostoServicio(prepagoBean.getMontoBruto());
									j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
									Thread llamador1 = new Thread(j);
									llamador1.start();
							}
						}
					}
					/**
					 *  fin, dbravo: 15/06/2007
					 */
				}
			}//if flagPagine

			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}


		} //try
		catch (ValidacionException e)
		{
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try
					{
						rollback(conn, request);
					}
					catch (Throwable ex)
					{
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
					}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		}
		catch (DBException dbe)
		{
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		catch (Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally
		{
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeStatement(stmt);
				JDBC.getInstance().closeResultSet(rsetc);
				JDBC.getInstance().closeStatement(stmtc);
				pool.release(conn);
				end(request);
			if (trace)
				System.err.println(now() + " Fin.");
		}

		return response;
	} //fin metodo buscar persona natural

	/**** inicio: jrosas: 13-07-07 ******/
	
	public ControllerResponse runBuscarPersonaJuridicaRmcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null;		
		
		try {
			init(request);
			validarSesion(request);

			inputBusqIndirectaBean = Tarea.recojeDatosRequestBusqIndirectaPartidaRMC(req);

			ConsultaIndicePartidasPersonaJurídicaService consultarIndicePartidasPersonaJuridicaService = new ConsultaIndicePartidasPersonaJurídicaServiceImpl();
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			String amid_session=ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request));
			
			FormOutputBuscarPartida output = consultarIndicePartidasPersonaJuridicaService.busquedaIndicePersonaJuridicaRMC(ConsultaIndicePartidasPersonaJurídicaService.MEDIO_CONTROLLER, inputBusqIndirectaBean, usuario, ipOrigen, amid_session);
			
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridicaRmc&tipo=J");
			
			req.setAttribute("outnumber",inputBusqIndirectaBean.getCantidad());	
			req.setAttribute("rmc","rmc");	
			req.setAttribute("flagCertBusq", inputBusqIndirectaBean.getCodGrupoLibroArea());
			req.setAttribute("tarifa",""+output.getTarifa());				
			req.setAttribute("usuaEtiq",usuario.getUserId());				
			req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output", output);

			/*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", inputBusqIndirectaBean.getVerifica());
			/*fin:dbravo:14/09/2007*/ 
			
		    response.setStyle("resultadoPersonaJuridica");
			
		}catch (ValidacionException e) {
			
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
			
		}  catch (CustomException e) {
			
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					response.setStyle("pantallaFinal");
					req.setAttribute("destino","back");
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}


		return response;
}
		
	/**** fin: jrosas: 13-07-07 ******/
	
    /**** inicio: jrosas: 13-07-07 ******/
	
	public ControllerResponse runBuscarTipoNumeroDocumentoRmcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null;		
		
		try {
			init(request);
			validarSesion(request);

			inputBusqIndirectaBean = Tarea.recojeDatosRequestBusqIndirectaPartidaRMC(req);

			ConsultaIndicePartidasxTipoNumeroDocumentoService consultarIndicePartidasTipoNumeroService = new ConsultaIndicePartidasxTipoNumeroDocumentoServiceImpl();
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			String amid_session=ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request));
			
			FormOutputBuscarPartida output = consultarIndicePartidasTipoNumeroService.busquedaIndiceTipoNumeroDocumentoRMC(ConsultaIndicePartidasxTipoNumeroDocumentoService.MEDIO_CONTROLLER, inputBusqIndirectaBean, usuario, ipOrigen, amid_session);
			
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarTipoNumeroDocumentoRmc&tipo=D");
			
			req.setAttribute("outnumber",inputBusqIndirectaBean.getCantidad());	
			req.setAttribute("rmc","rmc");	
			req.setAttribute("flagCertBusq", inputBusqIndirectaBean.getCodGrupoLibroArea());
			req.setAttribute("tarifa",""+output.getTarifa());				
			req.setAttribute("usuaEtiq",usuario.getUserId());				
			req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output", output);
			//Inicio:jascencio:09/08/2007
			req.setAttribute("flagCertBusq", inputBusqIndirectaBean.getCodGrupoLibroArea());
			//Fin:jascencio
		    response.setStyle("resultadoTipoDocBienes"); // se repetira la misma etiqueta ya que el jsp sera el mismo para todos
			
		}catch (ValidacionException e) {
			
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
			
		} catch (CustomException e) {
			
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					response.setStyle("pantallaFinal");
					req.setAttribute("destino","back");
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}


		return response;
}
		
	/**** fin: jrosas: 13-07-07 ******/
	
 /**** inicio: jrosas: 13-07-07 ******/
	
	public ControllerResponse runBuscarBienesRmcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null;		
		
		try {
			init(request);
			validarSesion(request);

			inputBusqIndirectaBean = Tarea.recojeDatosRequestBusqIndirectaPartidaRMC(req);

			ConsultaIndicePartidasxBienesService consultarIndicePartidasBienesService = new ConsultaIndicePartidasxBienesServiceImpl();
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			String amid_session=ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request));
			
			FormOutputBuscarPartida output = consultarIndicePartidasBienesService.busquedaIndiceBienesRMC(ConsultaIndicePartidasxBienesService.MEDIO_CONTROLLER, inputBusqIndirectaBean, usuario, ipOrigen, amid_session);
			
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarBienesRmc&tipo=B");
			
			req.setAttribute("outnumber",inputBusqIndirectaBean.getCantidad());	
			req.setAttribute("rmc","rmc");	
			req.setAttribute("flagCertBusq", inputBusqIndirectaBean.getCodGrupoLibroArea());
			req.setAttribute("tarifa",""+output.getTarifa());				
			req.setAttribute("usuaEtiq",usuario.getUserId());				
			req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output", output);
			//Inicio:jascencio:09/08/2007
			req.setAttribute("flagCertBusq", inputBusqIndirectaBean.getCodGrupoLibroArea());
			//Fin:jascencio
		    response.setStyle("resultadoTipoDocBienes"); // se repetira la misma etiqueta ya que el jsp sera el mismo para todos
			
		}catch (ValidacionException e) {
			
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
			
		} catch (CustomException e) {
			
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					response.setStyle("pantallaFinal");
					req.setAttribute("destino","back");
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}


		return response;
}
		
	/**
	 * Búsqueda Partidas X Indices 
	 * Buscar por Persona Jurídica 
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */	
	public ControllerResponse runBuscarPersonaJuridicaState(
				ControllerRequest request,
				ControllerResponse response)
				throws ControllerException {


				DBConnectionFactory pool = DBConnectionFactory.getInstance();
				Connection conn = null;


				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


				//obtener usuario de la sesion
				UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
					Statement stmtc   = null;
					Statement stmt   = null;
					ResultSet rsetc   = null;
					ResultSet rset   = null;
				try {
					init(request);
					validarSesion(request);


					conn = pool.getConnection();
					conn.setAutoCommit(false);
					DBConnection dconn = new DBConnection(conn);
					
					System.out.println("runBuscarPersonaJuridicaState--------------------------------->1");
					
					// Inicio:mgarate:31/05/2007
					   String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=10";
					// Fin:mgarate:31/05/2007

					InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_PERSONA_JURIDICA);

					String razonSocial = "";
					String siglas = "";
					String tipoParticipacion = "";

					if (bean1.getHid2().equals("1"))
							{
								razonSocial = bean1.getArea1Razon().trim();
								siglas = bean1.getArea1Siglas().trim();
								tipoParticipacion = bean1.getArea1TipoParticipacion();
							}

					if (bean1.getHid2().equals("2"))
							{
								razonSocial = bean1.getArea2Razon1().trim();
								siglas = bean1.getArea2Siglas().trim();
								tipoParticipacion = bean1.getArea2TipoParticipacion();
							}
					if (bean1.getHid2().equals("3"))
							{
								razonSocial = bean1.getArea3ParticipanteRazon().trim();
								siglas = bean1.getArea3Siglas().trim();
								tipoParticipacion = bean1.getArea3TipoParticipacion();
							}

					//razonSocial = Tarea.reemplazaApos(razonSocial);
					//siglas = Tarea.reemplazaApos(siglas);

					//-empieza busqueda
					StringBuffer q  = new StringBuffer();
					StringBuffer q2 = new StringBuffer();
					StringBuffer qca = new StringBuffer();
					StringBuffer qcb = new StringBuffer();

					//elegir indice
					if (razonSocial.length()>0 && siglas.length()>0)
					{
						q.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
						q2.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
						qca.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
						qcb.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					}
					else
					{
						if (razonSocial.length()>0)
							{
							q.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							q2.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							qca.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							qcb.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							}
						else
							{
							q.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_SIGLAS) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							q2.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_SIGLAS) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							qca.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_SIGLAS) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							qcb.append(" SELECT /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_SIGLAS) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
							}
					}

					// 2003-07-31 enviar el estado al JSP
					q.append(" PARTIDA.ESTADO as estado, ");
					q.append(" REGIS_PUBLICO.SIGLAS, ");
					q.append(" OFIC_REGISTRAL.NOMBRE, ");
					q.append(" IND_PRTC.COD_PARTIC, ");
					q.append(" PRTC_JUR.RAZON_SOCIAL, PRTC_JUR.TI_DOC, PRTC_JUR.NU_DOC, ");
					q.append(" PARTIDA.REFNUM_PART, PARTIDA.NUM_PARTIDA, ");
					q.append(" PARTIDA.COD_LIBRO,IND_PRTC.ESTADO ");
					q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
					qca.append(" count(prtc_jur.cur_prtc) ");
					qcb.append(" count(partida.refnum_part) ");
				
					q.append(" FROM PRTC_JUR,IND_PRTC,PARTIDA,OFIC_REGISTRAL,REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad ");
					q.append(" WHERE ");
					q2.append(" FROM PRTC_JUR,IND_PRTC,PARTIDA,OFIC_REGISTRAL,REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad ");
					q2.append(" WHERE ");
					qca.append(" FROM PRTC_JUR");
					qca.append(" WHERE ");
					qcb.append(" FROM PRTC_JUR,IND_PRTC,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
					qcb.append(" WHERE ");

					q.append(" PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC ");
					q.append(" AND IND_PRTC.REFNUM_PART=PARTIDA.REFNUM_PART ");
					q.append(" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id  ");
					q.append(" AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID  ");
					q.append(" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID ");
					q.append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
					q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
					if (razonSocial.length()>0)
						q.append(" AND prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%' ");
					if (siglas.length()>0)
						q.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%' ");
					appendCondicionEstadoPartida(q);

					q2.append(" PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC ");
					q2.append(" AND IND_PRTC.REFNUM_PART=PARTIDA.REFNUM_PART ");
					q2.append(" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id  ");
					q2.append(" AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID  ");
					q2.append(" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID ");
					q2.append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
					q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
					if (razonSocial.length()>0)
						q2.append(" AND prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%' ");
					if (siglas.length()>0)
						q2.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%' ");
					appendCondicionEstadoPartida(q2);

					if (razonSocial.length()>0 && siglas.length()>0)
						{
							qca.append(" prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%' ");
							qca.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%'");
						}
					else
					{
						if (razonSocial.length()>0)
							qca.append(" prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%' ");
						if (siglas.length()>0)
							qca.append(" PRTC_JUR.SIGLAS like '").append(siglas).append("%'");
					}

					qcb.append(" PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC ");
					qcb.append(" AND IND_PRTC.REFNUM_PART=PARTIDA.REFNUM_PART ");
					qcb.append(" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id  ");
					qcb.append(" AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID  ");
					if (razonSocial.length()>0)
						qcb.append(" AND prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%' ");
					if (siglas.length()>0)
						qcb.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%' ");
					appendCondicionEstadoPartida(qcb);


					StringBuffer sbregpub = new StringBuffer();
					if (bean1.getSedesElegidas().length==1)
						{
							sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
							qca.append(" AND prtc_jur.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
						}
					if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
						{
							sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
							qca.append(" AND prtc_jur.reg_pub_id IN ").append(bean1.getSedesSQLString());
						}
					q.append(sbregpub.toString());
					q2.append(sbregpub.toString());
					qcb.append(sbregpub.toString());

					//q.append(" AND partida.area_reg_id = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
					q.append(" AND partida.cod_libro = glad.cod_libro  ");
					q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
					q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
					//q2.append(" AND partida.area_reg_id = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
					q2.append(" AND partida.cod_libro = glad.cod_libro  ");
					q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
					q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
					//qcb.append(" AND partida.area_reg_id = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
					qcb.append(" AND partida.cod_libro = glad.cod_libro  ");
					qcb.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
					qcb.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
					
					if(bean1.getFlagIncluirInactivos()==false)
					{
						q.append(" AND IND_PRTC.ESTADO = '1'");
						q2.append(" AND IND_PRTC.ESTADO = '1'");
						qcb.append(" AND IND_PRTC.ESTADO = '1'");
					}
					
					q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");
					//contar
					Propiedades propiedades = Propiedades.getInstance();
					int conteo=0;
					if (bean1.getFlagPagineo()==false)
					{
						if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
						stmtc   = conn.createStatement();
						rsetc   = stmtc.executeQuery(qca.toString());
						boolean bc = rsetc.next();
						conteo = rsetc.getInt(1);
					
						if (conteo > (propiedades.getMaxResultadosBusqueda()*2) )
							throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
						if (conteo==0)
							throw new ValidacionException("No se encontraron resultados para su búsqueda");
					
						if (isTrace(this)) System.out.println("___verqueryCOUNT_B__"+qcb.toString());
						rsetc   = stmtc.executeQuery(qcb.toString());
						bc = rsetc.next();
						conteo = rsetc.getInt(1);
						if (conteo > propiedades.getMaxResultadosBusqueda() )
							throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
						if (conteo==0)
							throw new ValidacionException("No se encontraron resultados para su búsqueda");
					}
					if (isTrace(this)) System.out.println("___verquery___"+q.toString());

					//UsoServicio_________________________________
						
					//descripcion area registral
					DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
					dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
					dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
					dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
					String descripcionAreaRegistral="";
					if (dboTmAreaRegistral.find() == true)
						descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

					//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

					stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(propiedades.getLineasPorPag());
					rset   = stmt.executeQuery(q.toString());
					if (bean1.getSalto()>1)
						rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));

					boolean b = rset.next();
					ArrayList resultado = new ArrayList();

					
					DboFicha dboFicha = new DboFicha(dconn);
					DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
					DboTmLibro dboTmLibro = new DboTmLibro(dconn);
					DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
					DboParticLibro dboParticLibro = new DboParticLibro(dconn);
					
					
					StringBuffer sb = new StringBuffer();
					
					int conta=0;
					boolean haySiguiente = false;
					while (b==true)
					{
						PartidaBean partidaBean = new PartidaBean();
					
						//_completar los detalles de la partida encontrada
						String refNumPart = rset.getString("refnum_part");
						String codLibro   = rset.getString("cod_libro");
						partidaBean.setRefNumPart(refNumPart);
						partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
						partidaBean.setNumPartida(rset.getString("num_partida"));
						partidaBean.setRegPubDescripcion(rset.getString("siglas"));
						partidaBean.setOficRegDescripcion(rset.getString("nombre"));
						// 2003-07-31 enviar el estado al JSP
						partidaBean.setEstado(rset.getString("estado"));
					
						partidaBean.setEstadoInd("Inactivo");
						if (rset.getString("estado").startsWith("1"))
							partidaBean.setEstadoInd("Activo");
					
						//ficha
						dboFicha.clearAll();
						sb.delete(0, sb.length());
						sb.append(dboFicha.CAMPO_FICHA).append("|");
						sb.append(dboFicha.CAMPO_FICHA_BIS);
						dboFicha.setFieldsToRetrieve(sb.toString());
						dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
						if (dboFicha.find() == true)
								{
									partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
									String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
									int nbis = 0;
									try {
										nbis = Integer.parseInt(bis);
									}
									catch (NumberFormatException n)
									{
										nbis =0;
									}
									if (nbis>=1)
										partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
								}
					
						//obtener tomo y foja
						dboTomoFolio.clearAll();
						sb.delete(0, sb.length());
						sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
						sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
						sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
						sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
						dboTomoFolio.setFieldsToRetrieve(sb.toString());
						dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
						if (dboTomoFolio.find() == true)
								{
									partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
									partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
					
					
									String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
									String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);
					
					
									if (bist.trim().length() > 0)
											partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
					
					
									if (bisf.trim().length() > 0)
											partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
					
									//28dic2002
									//quitar el caracter "9" del inicio del tomoid
										if (partidaBean.getTomoId().length()>0)
										{
											if (partidaBean.getTomoId().startsWith("9"))
												{
													String ntomo = partidaBean.getTomoId().substring(1);
													partidaBean.setTomoId(ntomo);
												}
										}
								}
					
					
						//descripcion libro
						dboTmLibro.clearAll();
						dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
						dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
						if (dboTmLibro.find() == true)
							partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
					
					
					
					
						//participante y su número de documento
						String tipoDocumento="";
						String codPartic="";
					
					
					
						partidaBean.setParticipanteDescripcion(rset.getString("RAZON_SOCIAL"));
					
						String nuDocIden = rset.getString("nu_doc");
						if (nuDocIden==null || nuDocIden.trim().length()==0)
							partidaBean.setParticipanteNumeroDocumento("&nbsp;");
						else
							partidaBean.setParticipanteNumeroDocumento(nuDocIden);
					
					
						tipoDocumento = rset.getString("ti_doc");
						codPartic     = rset.getString("cod_partic");
					
					
						//descripción de documento
						if (tipoDocumento!=null)
						{
							if (tipoDocumento.trim().length()>0)
							{
								dboTmDocIden.clearAll();
								dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
								dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
								dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO, "1");
								if (dboTmDocIden.find() == true)
										partidaBean.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
							}
						}
					
					
						//descripción del tipo de participación
						dboParticLibro.clearAll();
						dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
						dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
						dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
						dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
							if (dboParticLibro.find()==true)
								partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
					
						resultado.add(partidaBean);
						conta++;
					
						b = rset.next();
						if (conta==propiedades.getLineasPorPag())
						{
							if(b==true)
								haySiguiente = true;
					
							break;
						}
					}//while (b==true)
					
					
					System.out.println("runBuscarPersonaJuridicaState--------------------------------->5");

					if (resultado.size()==0)
						throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
					
					FormOutputBuscarPartida output = new FormOutputBuscarPartida();
					if (bean1.getFlagIncluirInactivos()==true)
						output.setFlagEstado(true);
					output.setResultado(resultado);
					if (bean1.getFlagPagineo()==false)
						output.setCantidadRegistros(String.valueOf(conteo));
					else
						output.setCantidadRegistros(bean1.getCantidad());


					//calcular numero para boton "retroceder pagina"
					if (bean1.getSalto()==1)
						output.setPagAnterior(-1);
					else
						output.setPagAnterior(bean1.getSalto()-1);
					
					//calcular numero para boton "avanzar pagina"
					if (haySiguiente==false)
						output.setPagSiguiente(-1);
					else
						output.setPagSiguiente(bean1.getSalto()+1);
					
					
					//calcular regs del x al y
					int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
					int al  = del+resultado.size()-1;
					output.setNdel(del);
					output.setNal(al);


					//ETIQUETA
			
					// recuperamos el costo de la visualizacion
					double tarifa = 0;
					DboTarifa dboTarifa = new DboTarifa(dconn);
					dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
					dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
					dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());
			
					if (dboTarifa.find())
					{
						String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
						tarifa = Double.parseDouble(sTarifa);
					}
					req.setAttribute("tarifa",""+tarifa);
					// recuperamos el usuario
					String usuaEtiq = usuario.getUserId();
					req.setAttribute("usuaEtiq",usuaEtiq);
					// recuperamos la fecha Actual
					String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
					req.setAttribute("fechaAct",fechaAct);

					output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridica");
					req.setAttribute("output", output);

					// Inicio:mgarate:31/05/2007
				    req.setAttribute("criterioBusqueda",criterioBusqueda);
				    req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
				    // Fin:mgarate:31/05/2007
				   
				    /*inicio:dbravo:14/09/2007*/
					req.setAttribute("flagVerifica", bean1.getVerifica());
					/*fin:dbravo:14/09/2007*/ 
				
					response.setStyle("resultadoPersonaJuridica");


					if (bean1.getFlagPagineo()==false)
					{
						//llamar a "Transaccion"
						LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();

						//Datos generales
						 //Modificado por: Proyecto Filtros de Acceso
						 //Fecha: 02/10/2006
						 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
						 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
						 //Fecha: 08/10/2006             
						 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
						 //Fin Modificación
						bt.setUsuarioSession(usuario);
						bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
						//Tarifario
						bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
						//datos particulares de esta transaccion
						bt.setPartSeleccionado("PJ");
						bt.setNumSedes(bean1.getSedesElegidas());
						bt.setNomApeRazSocPart(razonSocial);
						bt.setCodAreaReg(bean1.getComboArea());
						bt.setTipoParticipacion(tipoParticipacion);
						bt.setTipoPersPart("J");
						/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
					*/
						if (Propiedades.getInstance().getFlagTransaccion()==true){
							PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
							
							/**
							  *  inicio, dbravo: 15/06/2007
							  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
							  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
							  *  			   inicialmente el Job002.
							  */
							if (bean1.getFlagPagineo()==false)
							{
								/*
								validar que el usuario NO sea de zona WEB
								*/
								if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
								{
									//estamos en la primera llamada
									//enviamos TODOS los registros encontrados
									//a otro Thread para que registre el UsoServicio
										Job004 j = new Job004();
										j.setQuery(q2.toString());
										j.setUsuario(usuario);
										j.setCostoServicio(prepagoBean.getMontoBruto());
										j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
										Thread llamador1 = new Thread(j);
										llamador1.start();
								}
							}
							/**
							  * fin, dbravo: 15/06/2007
							  */
						}

					}//if flagpagine


				conn.commit();


				if (usuario.getFgInterno()==false)
				{
					LineaPrepago lineaCmd = new LineaPrepago();
					double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
					usuario.setSaldo(nuevoSaldo);
				}
				System.out.println("runBuscarPersonaJuridicaState--------------------------------->"+response.getStyle());

		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
					log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
					principal(request);
					rollback(conn, request);
					response.setStyle("error");
				} catch (Throwable ex) {
					log(Constantes.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					rollback(conn, request);
					response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	} // fin metodo buscar persona juridica


	public ControllerResponse runBuscarRazonSocialState(ControllerRequest request, ControllerResponse response)	throws ControllerException {
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

        //Construir los selects
        StringBuffer sbSelect1a = new StringBuffer();
        StringBuffer sbSelect1b = new StringBuffer();
        StringBuffer sbFrom1 = new StringBuffer();
        StringBuffer sbWhere1a = new StringBuffer();
        StringBuffer sbWhere1b = new StringBuffer();
        StringBuffer sbWhere1c = new StringBuffer();
        StringBuffer sbSelect2a = new StringBuffer();
        StringBuffer sbSelect2b = new StringBuffer();
        StringBuffer sbFrom2 = new StringBuffer();
        StringBuffer sbWhere2a = new StringBuffer();
        StringBuffer sbWhere2b = new StringBuffer();
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

        sbSelect1a.append("SELECT  /*+ORDERED INDEX(PARTIDA SYS_C005644) */ 'PART' AS TIPO,PARTIDA.REFNUM_PART,REGIS_PUBLICO.REG_PUB_ID, REGIS_PUBLICO.NOMBRE, REGIS_PUBLICO.SIGLAS, ");
        sbSelect1a.append("REGIS_PUBLICO.USR_CREA, REGIS_PUBLICO.USR_ULT_MODIF, ");
        sbSelect1a.append("OFIC_REGISTRAL.REG_PUB_ID AS OFRREGPUB, OFIC_REGISTRAL.OFIC_REG_ID, OFIC_REGISTRAL.NOMBRE AS OFRNOMBRE, ");
        sbSelect1a.append("OFIC_REGISTRAL.USR_CREA AS OFRUSRCREA, OFIC_REGISTRAL.USR_ULT_MODIF AS USRULTMOD, RAZ_SOC_PJ.REFNUM_PART AS REFNUPART, ");
        sbSelect1a.append("RAZ_SOC_PJ.NS_NOMBRE, RAZ_SOC_PJ.RAZON_SOC, RAZ_SOC_PJ.ESTADO as estadoInd, RAZ_SOC_PJ.SIGLAS AS RZSIGL, ");
        sbSelect1a.append("RAZ_SOC_PJ.NU_FOJA, RAZ_SOC_PJ.NU_TOMO, RAZ_SOC_PJ.NU_ORIG_PART, ");
        sbSelect1a.append("RAZ_SOC_PJ.AGNT_SYNC , PARTIDA.NUM_PARTIDA, PARTIDA.REG_PUB_ID AS PARTREGPUB, ");

        // 2003-07-31 enviar el estado al JSP
        sbSelect1a.append("PARTIDA.OFIC_REG_ID AS PARTOFRID, PARTIDA.COD_LIBRO, PARTIDA.AREA_REG_ID,PARTIDA.ESTADO AS estado, ");
        sbSelect1a.append("PARTIDA.AGNT_SYNC AS PARTAGNSYN, PARTIDA.CO_LIBR_ORIG ");

        sbSelect1b.append("SELECT /*+ORDERED INDEX(PARTIDA SYS_C005644)*/ PARTIDA.REG_PUB_ID, PARTIDA.OFIC_REG_ID, PARTIDA.AREA_REG_ID ");

        sbFrom1.append("FROM RAZ_SOC_PJ, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad ");

        sbWhere1a.append("WHERE RAZ_SOC_PJ.ESTADO = '1' AND ");

        sbWhere1b.append("PARTIDA.REFNUM_PART = RAZ_SOC_PJ.REFNUM_PART ");
        appendCondicionEstadoPartida(sbWhere1b);
        sbWhere1b.append(" AND ");

        sbWhere1c.append("OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID AND ");
        sbWhere1c.append("OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID AND ");
        sbWhere1c.append("REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");

        sbSelect2a.append("SELECT /*+ORDERED INDEX(TI SYS_C001712) INDEX(RES INDEX_RES_RZ_SOC) INDEX(DT INDX_REFNUM_FG_ACT)*/ ");
        sbSelect2a.append("'TITU' AS TIPO,RES.REFNUM_TITU,RP.REG_PUB_ID,RP.NOMBRE,RP.SIGLAS,null,TI.ANO_TITU,TI.NUM_TITU,null,OFR.NOMBRE, ");
        sbSelect2a.append("null,null,0,0,RES.RAZ_SOC_RES,'1',null,null,null,null,null,null,TI.REG_PUB_ID,TI.OFIC_REG_ID,null,'22000',null,null,null ");

        sbSelect2b.append("SELECT /*+ORDERED */ TI.REG_PUB_ID, TI.OFIC_REG_ID,'22000'");

        sbFrom2.append("FROM RESERVA_RZ_SOC RES,TITULO TI, DETALLE_TITULO DT,OFIC_REGISTRAL OFR, REGIS_PUBLICO RP ");

        sbWhere2a.append("WHERE ");

        sbWhere2b.append("TI.REFNUM_TITU = RES.REFNUM_TITU AND ");
        sbWhere2b.append("DT.REFNUM_TITU = TI.REFNUM_TITU AND ");
        sbWhere2b.append("DT.FG_ACTIVO = '1' AND ");
        sbWhere2b.append("DT.ESTADO_TITULO_ID = 130 AND ");
        sbWhere2b.append("ofr.reg_pub_id = TI.REG_PUB_ID AND ");
        sbWhere2b.append("ofr.ofic_reg_id = TI.OFIC_REG_ID AND ");
        sbWhere2b.append("rp.reg_pub_id = ofr.reg_pub_id ");

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_RAZON_SOCIAL);

			String tipoParticipacion = bean1.getArea2TipoParticipacion();

			boolean br=false;
			boolean bs=false;

			if (bean1.getArea2Razon2().length()>0)
				br=true;

			if (bean1.getArea2SiglasB().length()>0)
				bs=true;

			if (br==false && bs==false)
				throw new ValidacionException("Por favor indique Razón Social y/o Siglas para la búsqueda");

			//***************************************************
			//***************************************************
			//  CREACION DE QUERY PARA CONTAR
			//***************************************************
			//***************************************************
			StringBuffer qc = new StringBuffer();

			//posibilidad 1, usuario ingresa Razón Social, SIN siglas
			if (br==true && bs==false) {
				qc.append(" select count(*) from ( ");
				qc.append(" SELECT  /*+ORDERED INDEX(RAZ_SOC_PJ IND_RAZ_SOC) INDEX(PARTIDA SYS_C005644) */");
				qc.append("         PARTIDA.REFNUM_PART");
				qc.append(" FROM    RAZ_SOC_PJ,");
				qc.append("         PARTIDA");
				qc.append(" WHERE   RAZ_SOC_PJ.ESTADO = '1' AND");
				qc.append("         RAZ_SOC_PJ.REFNUM_PART = PARTIDA.REFNUM_PART AND");
				qc.append("         PARTIDA.AREA_REG_ID='22000' and");
				qc.append(" 		RAZON_SOC like '").append(bean1.getArea2Razon2()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");


				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());

				appendCondicionEstadoPartida(qc);

				qc.append(" union all ");
				qc.append(" SELECT  /*+ORDERED INDEX(RAZ_SOC_PJ IND_SIGLAS) INDEX(PARTIDA SYS_C005644) */");
				qc.append("         PARTIDA.REFNUM_PART");
				qc.append(" FROM    RAZ_SOC_PJ,");
				qc.append("         PARTIDA");
				qc.append(" WHERE   RAZ_SOC_PJ.ESTADO = '1' AND");
				qc.append("         RAZ_SOC_PJ.REFNUM_PART = PARTIDA.REFNUM_PART AND");
				qc.append("         PARTIDA.AREA_REG_ID='22000' and");
				qc.append(" 		siglas like '").append(bean1.getArea2Razon2()).append("%'");
				qc.append("         and razon_soc not like '").append(bean1.getArea2Razon2()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());

				appendCondicionEstadoPartida(qc);

				qc.append(" union all ");

				//hp
				//qc.append(" SELECT  /*+ORDERED INDEX(T1 IDX_DETTIT_FGACT_EST) INDEX(T2 INDEX_RES_RZ_SOC) INDEX(T3 SYS_C005698)*/ ");
				qc.append(" SELECT  /*+ORDERED INDEX(TI SYS_C001712) INDEX(RES INDEX_RES_RZ_SOC) INDEX(DT INDX_REFNUM_FG_ACT)*/ ");
				qc.append("         RES.REFNUM_TITU");
				qc.append(" FROM    RESERVA_RZ_SOC RES,");
				qc.append("         TITULO TI,");
				qc.append("         DETALLE_TITULO DT");
				qc.append(" WHERE   DT.REFNUM_TITU = TI.REFNUM_TITU AND   ");
				qc.append("         DT.FG_ACTIVO='1' AND   ");
				qc.append("         TI.REFNUM_TITU=RES.REFNUM_TITU AND       ");
				qc.append("         DT.ESTADO_TITULO_ID = 130 				");
				qc.append("         and res.raz_soc_res like '").append(bean1.getArea2Razon2()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND ti.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND ti.reg_pub_id IN ").append(bean1.getSedesSQLString());

				qc.append("   )  ");
			}

			//posibilidad 2, usuario ingresa Razón social y siglas
			if (br==true && bs==true) {
				qc.append(" select count(*) from ( ");
				qc.append(" SELECT  /*+ORDERED INDEX(RAZ_SOC_PJ IND_RAZ_SOC) INDEX(PARTIDA SYS_C005644) */");
				qc.append("         PARTIDA.REFNUM_PART");
				qc.append(" FROM    RAZ_SOC_PJ,");
				qc.append("         PARTIDA");
				qc.append(" WHERE   RAZ_SOC_PJ.ESTADO = '1' AND");
				qc.append("         RAZ_SOC_PJ.REFNUM_PART = PARTIDA.REFNUM_PART AND");
				qc.append("         PARTIDA.AREA_REG_ID='22000' and");
				qc.append(" 		RAZON_SOC like '").append(bean1.getArea2Razon2()).append("%'");
				qc.append("         and siglas like '").append(bean1.getArea2SiglasB()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());

				appendCondicionEstadoPartida(qc);

				qc.append(" union all ");

				qc.append(" SELECT  /*+ORDERED INDEX(TITULO SYS_C001712) INDEX (RESERVA_RZ_SOC INDEX_RES_RZ_SOC) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
				qc.append("         RES.REFNUM_TITU");
				qc.append(" FROM    RESERVA_RZ_SOC RES,");
				qc.append("         TITULO TI,");
				qc.append("         DETALLE_TITULO DT");
				qc.append(" WHERE   DT.REFNUM_TITU = TI.REFNUM_TITU AND   ");
				qc.append("         DT.FG_ACTIVO='1' AND   ");
				qc.append("         TI.REFNUM_TITU=RES.REFNUM_TITU AND       ");
				qc.append("         DT.ESTADO_TITULO_ID = 130 				");
				qc.append("         and res.raz_soc_res like '").append(bean1.getArea2Razon2()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND ti.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND ti.reg_pub_id IN ").append(bean1.getSedesSQLString());

				qc.append("   )  ");
			}

			//posibilidad 3, usuario ingresa SIGLAS solamente
			if (br==false && bs==true) {
				qc.append(" select count(*) from ( ");
				qc.append(" SELECT  /*+ORDERED INDEX(RAZ_SOC_PJ IND_RAZ_SOC) INDEX(PARTIDA SYS_C005644) */");
				qc.append("         PARTIDA.REFNUM_PART");
				qc.append(" FROM    RAZ_SOC_PJ,");
				qc.append("         PARTIDA");
				qc.append(" WHERE   RAZ_SOC_PJ.ESTADO = '1' AND");
				qc.append("         RAZ_SOC_PJ.REFNUM_PART = PARTIDA.REFNUM_PART AND");
				qc.append("         PARTIDA.AREA_REG_ID='22000' and");
				qc.append(" 		RAZON_SOC like '").append(bean1.getArea2SiglasB()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());

				appendCondicionEstadoPartida(qc);

				qc.append(" union all ");

				qc.append(" SELECT  /*+ORDERED INDEX(RAZ_SOC_PJ IND_SIGLAS) INDEX(PARTIDA SYS_C005644) */");
				qc.append("         PARTIDA.REFNUM_PART");
				qc.append(" FROM    RAZ_SOC_PJ,");
				qc.append("         PARTIDA");
				qc.append(" WHERE   RAZ_SOC_PJ.ESTADO = '1' AND");
				qc.append("         RAZ_SOC_PJ.REFNUM_PART = PARTIDA.REFNUM_PART AND");
				qc.append("         PARTIDA.AREA_REG_ID='22000' and");
				qc.append(" 		siglas like '").append(bean1.getArea2SiglasB()).append("%'");
				qc.append("         and razon_soc not like '").append(bean1.getArea2SiglasB()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());

				appendCondicionEstadoPartida(qc);

				qc.append(" union all ");

				qc.append(" SELECT  /*+ORDERED INDEX(TITULO SYS_C001712) INDEX (RESERVA_RZ_SOC INDEX_RES_RZ_SOC) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
				qc.append("         RES.REFNUM_TITU");
				qc.append(" FROM    RESERVA_RZ_SOC RES,");
				qc.append("         TITULO TI,");
				qc.append("         DETALLE_TITULO DT");
				qc.append(" WHERE   DT.REFNUM_TITU = TI.REFNUM_TITU AND   ");
				qc.append("         DT.FG_ACTIVO='1' AND   ");
				qc.append("         TI.REFNUM_TITU=RES.REFNUM_TITU AND       ");
				qc.append("         DT.ESTADO_TITULO_ID = 130 				");
				qc.append("         and res.raz_soc_res like '").append(bean1.getArea2SiglasB()).append("%'");

				if (bean1.getSedesElegidas().length==1)
					qc.append(" AND ti.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					qc.append(" AND ti.reg_pub_id IN ").append(bean1.getSedesSQLString());

				qc.append("   )  ");
			}

			Propiedades propiedades = Propiedades.getInstance();

			int conteo = 0;

			if (bean1.getFlagPagineo()==false) {
				if (isTrace(this))
					System.out.println("___verqueryCOUNT_A__"+qc.toString());

				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qc.toString());
				boolean bc = rsetc.next();

				conteo = rsetc.getInt(1);

				if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");

				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");
			}

			StringBuffer condicionextra = new StringBuffer();

			condicionextra.append(" partida.cod_libro = glad.cod_libro and ");
			condicionextra.append(" gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' AND ");
			condicionextra.append(" gla.cod_grupo_libro_area = glad.cod_grupo_libro_area AND ");

			StringBuffer condicionextra2a = new StringBuffer();
			StringBuffer condicionextra2b = new StringBuffer();

			if (bean1.getSedesElegidas().length==1) {
				condicionextra2a.append(" PARTIDA.REG_PUB_ID = '").append(bean1.getSedesElegidas()[0]).append("' AND ");
				condicionextra2b.append(" TI.REG_PUB_ID = '").append(bean1.getSedesElegidas()[0]).append("' AND ");
			}

			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12) {
				condicionextra2a.append(" PARTIDA.REG_PUB_ID IN ").append(bean1.getSedesSQLString()).append(" AND ");
				condicionextra2b.append(" TI.REG_PUB_ID IN ").append(bean1.getSedesSQLString()).append(" AND ");
			}

			//***************************************************
			//***************************************************
			//  CREACION DE QUERY PARA LLAMAR A USO SERVICIO
			//***************************************************
			//***************************************************
			StringBuffer q2  = new StringBuffer();

			//posibilidad 1, usuario ingresa Razón Social, SIN siglas
			if (br==true && bs==false) {
				q2.append(sbSelect1b);
				q2.append(sbFrom1);
				q2.append(sbWhere1a);
				q2.append(" RAZ_SOC_PJ.RAZON_SOC LIKE '").append(bean1.getArea2Razon2()).append("%' AND ");
				q2.append(sbWhere1b);
				q2.append(condicionextra.toString());
				q2.append(condicionextra2a.toString());
				q2.append(sbWhere1c);
				q2.append(" UNION ");
				q2.append(sbSelect1b);
				q2.append(sbFrom1);
				q2.append(sbWhere1a);
				q2.append(" RAZ_SOC_PJ.siglas like '").append(bean1.getArea2Razon2()).append("%' AND ");
				q2.append(sbWhere1b);
				q2.append(condicionextra.toString());
				q2.append(condicionextra2a.toString());
				q2.append(sbWhere1c);
				q2.append(" UNION ");
				q2.append(sbSelect2b);
				q2.append(sbFrom2);
			        /**
			         * SVASQUEZ - AVATAR GLOBAL
			        */
			        q2.append(", PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");			
				q2.append(sbWhere2a);
				q2.append(" RES.RAZ_SOC_RES LIKE '").append(bean1.getArea2Razon2()).append("%' AND ");
				q2.append(condicionextra.toString());
				q2.append(condicionextra2b.toString());
				q2.append(sbWhere2b);
			
			System.out.println("SAUL 1 - q2.toString()"+q2.toString());			
			}

			//posibilidad 2, usuario ingresa Razón social y siglas
			if (br==true && bs==true) {
				q2.append(sbSelect1b);
				q2.append(sbFrom1);
				q2.append(sbWhere1a);
				q2.append(" RAZ_SOC_PJ.RAZON_SOC like '").append(bean1.getArea2Razon2()).append("%' AND ");
				q2.append(" RAZ_SOC_PJ.SIGLAS like '").append(bean1.getArea2SiglasB()).append("%' AND ");
				q2.append(sbWhere1b);
				q2.append(condicionextra.toString());
				q2.append(condicionextra2a.toString());
				q2.append(sbWhere1c);
				q2.append(" UNION ");
				q2.append(sbSelect2b);
				q2.append(sbFrom2);
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 */
			q2.append(", PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
				q2.append(sbWhere2a);
				q2.append(" RES.RAZ_SOC_RES LIKE '").append(bean1.getArea2Razon2()).append("%' AND ");
				q2.append(condicionextra.toString());
				q2.append(condicionextra2b.toString());
				q2.append(sbWhere2b);
			System.out.println("SAUL 2 - q2.toString()"+q2.toString());		
			}

			//posibilidad 3, usuario ingresa SIGLAS solamente
			if (br==false && bs==true) {
				q2.append(sbSelect1b);
				q2.append(sbFrom1);
				q2.append(sbWhere1a);
				q2.append(" RAZ_SOC_PJ.siglas like '").append(bean1.getArea2SiglasB()).append("%' AND ");
				q2.append(sbWhere1b);
				q2.append(condicionextra.toString());
				q2.append(condicionextra2a.toString());
				q2.append(sbWhere1c);
				q2.append(" UNION ");
				q2.append(sbSelect1b);
				q2.append(sbFrom1);
				q2.append(sbWhere1a);
				q2.append(" RAZ_SOC_PJ.razon_soc like '").append(bean1.getArea2SiglasB()).append("%' AND ");
				q2.append(sbWhere1b);
				q2.append(condicionextra.toString());
				q2.append(condicionextra2a.toString());
				q2.append(sbWhere1c);
			}
			
			/**
			 * inicio, dbravo: 15/06/2007
			 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
			 *
			//UsoServicio_________________________________
			if (bean1.getFlagPagineo()==false) {
				/*
				validar que el usuario NO sea de zona WEB
				/
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB) == false) {
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
					Job002 j = new Job002();

				//if (isTrace(this))
				/**
				 * SE COMENTA EL TRACE LOG
				 *
						System.out.print(">> SQLq2 : " + q2.toString());

					j.setQuery(q2.toString());
					j.setUsuario(usuario);
					j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));

					Thread llamador1 = new Thread(j);
					llamador1.start();
				}
			}
			*
			* fin, dbravo: 15/06/2007
			**/

			//***************************************************
			//***************************************************
			//  CREACION DE QUERY PARA RECOGER LOS REGISTROS DE LA BASE DE DATOS
			//***************************************************
			//***************************************************

			StringBuffer q  = new StringBuffer();

			//posibilidad 1, usuario ingresa Razón Social, SIN siglas
			if (br==true && bs==false) {
				q.append(sbSelect1a);
				q.append(sbFrom1);
				q.append(sbWhere1a);
				q.append(" RAZ_SOC_PJ.RAZON_SOC LIKE '").append(bean1.getArea2Razon2()).append("%' AND ");
				q.append(sbWhere1b);
				q.append(condicionextra.toString());
				q.append(condicionextra2a.toString());
				q.append(sbWhere1c);
				q.append(" UNION ");
				q.append(sbSelect1a);
				q.append(sbFrom1);
				q.append(sbWhere1a);
				q.append(" RAZ_SOC_PJ.siglas like '").append(bean1.getArea2Razon2()).append("%' AND ");
				q.append(sbWhere1b);
				q.append(condicionextra.toString());
				q.append(condicionextra2a.toString());
				q.append(sbWhere1c);
				q.append(" UNION ");
				q.append(sbSelect2a);
				q.append(sbFrom2);
				q.append(sbWhere2a);
				q.append(" RES.RAZ_SOC_RES LIKE '").append(bean1.getArea2Razon2()).append("%' AND ");
				q.append(condicionextra2b.toString());
				q.append(sbWhere2b);
			}

			//posibilidad 2, usuario ingresa Razón social y siglas
			if (br==true && bs==true) {
				q.append(sbSelect1a);
				q.append(sbFrom1);
				q.append(sbWhere1a);
				q.append(" RAZ_SOC_PJ.RAZON_SOC like '").append(bean1.getArea2Razon2()).append("%' AND ");
				q.append(" RAZ_SOC_PJ.SIGLAS like '").append(bean1.getArea2SiglasB()).append("%' AND ");
				q.append(sbWhere1b);
				q.append(condicionextra.toString());
				q.append(condicionextra2a.toString());
				q.append(sbWhere1c);
				q.append(" UNION ");
				q.append(sbSelect2a);
				q.append(sbFrom2);
				q.append(sbWhere2a);
				q.append(" RES.RAZ_SOC_RES LIKE '").append(bean1.getArea2Razon2()).append("%' AND ");
				q.append(condicionextra.toString());
				q.append(condicionextra2b.toString());
				q.append(sbWhere2b);
			}

			//posibilidad 3, usuario ingresa SIGLAS solamente
			if (br==false && bs==true) {
				q.append(sbSelect1a);
				q.append(sbFrom1);
				q.append(sbWhere1a);
				q.append(" RAZ_SOC_PJ.siglas like '").append(bean1.getArea2SiglasB()).append("%' AND ");
				q.append(sbWhere1b);
				q.append(condicionextra.toString());
				q.append(condicionextra2a.toString());
				q.append(sbWhere1c);
				q.append(" UNION ");
				q.append(sbSelect1a);
				q.append(sbFrom1);
				q.append(sbWhere1a);
				q.append(" RAZ_SOC_PJ.razon_soc like '").append(bean1.getArea2SiglasB()).append("%' AND ");
				q.append(sbWhere1b);
				q.append(condicionextra.toString());
				q.append(condicionextra2a.toString());
				q.append(sbWhere1c);
			}

			if (isTrace(this))
				System.out.println("___verquery___"+q.toString());

			//descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);

			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");

			String descripcionAreaRegistral="";

			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset = stmt.executeQuery(q.toString());

			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));

			boolean b = rset.next();

			ArrayList resultado = new ArrayList();

			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboPrtcJur dboPrtcJur = new DboPrtcJur(dconn);
			DboIndPrtc dboIndPrtc = new DboIndPrtc(dconn);

			StringBuffer sb = new StringBuffer();

			boolean haySiguiente = false;
			int conta=0;

			PartidaBean partidaBean = null;

			while (b==true) {
				partidaBean = new PartidaBean();

				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setRegPubDescripcion(rset.getString("SIGLAS"));
				partidaBean.setOficRegDescripcion(rset.getString("OFRNOMBRE"));

				// 2003-07-31 enviar el estado al JSP
				partidaBean.setEstado(rset.getString("estado"));

				/*
				la VISTA es SQL UNION, tiene titulos y partidas
				hay que diferenciar entre ambas
				*/
				String tipo = rset.getString("TIPO");

				if (tipo.startsWith("T")) {
					//es un TITULO
					partidaBean.setFlagTitulo(true);

					//FORMATO : "T-"+ ANO +"-"+ NUMERO
					sb.delete(0, sb.length());

					sb.append("T-");
					sb.append(rset.getString("USR_ULT_MODIF"));
					sb.append("-");
					sb.append(rset.getString("OFRREGPUB"));

					partidaBean.setNumPartida(sb.toString());
					partidaBean.setPj_razonSocial(rset.getString("RAZON_SOC"));
					partidaBean.setRefNumPart(rset.getString("REFNUM_PART"));

					//-agregar EsquelaURl
					String esquelaUrl = Generales.formateoUrlEsquela(partidaBean.getRefNumPart(),"R", bean1.getComboArea(),  false);
					partidaBean.setEsquelaUrl(esquelaUrl);

					resultado.add(partidaBean);
					conta++;

					b = rset.next();

					if (conta==propiedades.getLineasPorPag()) {
						if(b==true)
							haySiguiente = true;

						break;
					}

					continue;
				}

				//de lo contrario es una partida
				String refNumPart = rset.getString("REFNUM_PART");
				String regPubId   = rset.getString("REG_PUB_ID");
				String oficRegId  = rset.getString("OFIC_REG_ID");
				String codLibro   = rset.getString("COD_LIBRO");

				//numero partida
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setNumPartida(rset.getString("NUM_PARTIDA"));

				//ficha
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(dboFicha.CAMPO_FICHA).append("|");
				sb.append(dboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);

				if (dboFicha.find() == true) {
					partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
					String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
					int nbis = 0;

				try {
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException n)
				{
					nbis =0;
				}
				if (nbis>=1)
					partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
			}

			//obtener tomo y foja
			dboTomoFolio.clearAll();

			sb.delete(0, sb.length());

			sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
			sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
			sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
			sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);

			dboTomoFolio.setFieldsToRetrieve(sb.toString());
			dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);

			if (dboTomoFolio.find() == true) {
				partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

				if (bist.trim().length() > 0)
					partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);

				if (bisf.trim().length() > 0)
					partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

				//28dic2002
				//quitar el caracter "9" del inicio del tomoid
				if (partidaBean.getTomoId().length()>0) {
					if (partidaBean.getTomoId().startsWith("9"))
							{
								String ntomo = partidaBean.getTomoId().substring(1);
								partidaBean.setTomoId(ntomo);
							}
					}
			}


			//razon social
			partidaBean.setPj_razonSocial(rset.getString("RAZON_SOC"));

			dboIndPrtc.clearAll();
			dboIndPrtc.setField(dboIndPrtc.CAMPO_REFNUM_PART,refNumPart);
			dboIndPrtc.setField(dboIndPrtc.CAMPO_TIPO_PERS,"J");

			if (dboIndPrtc.find()==true) {
				String curPrtc     = dboIndPrtc.getField(dboIndPrtc.CAMPO_CUR_PRTC);

				//persona juridica
				dboPrtcJur.clearAll();
				dboPrtcJur.setFieldsToRetrieve(DboPrtcJur.CAMPO_NU_DOC);
				dboPrtcJur.setField(dboPrtcJur.CAMPO_CUR_PRTC, curPrtc);
				dboPrtcJur.setField(DboPrtcJur.CAMPO_REG_PUB_ID, regPubId);
				dboPrtcJur.setField(DboPrtcJur.CAMPO_OFIC_REG_ID, oficRegId);

				if (dboPrtcJur.find() == true)
					partidaBean.setPj_ruc(dboPrtcJur.getField(DboPrtcJur.CAMPO_NU_DOC));
			}

			resultado.add(partidaBean);
			conta++;
			b = rset.next();

			if (conta==propiedades.getLineasPorPag()) {
				if(b==true)
					haySiguiente = true;

				break;
			}
		}//while (b==true)

		if (resultado.size()==0)
			throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		output.setResultado(resultado);

		if (bean1.getFlagPagineo()==false)
			output.setCantidadRegistros(String.valueOf(conteo));
		else
			output.setCantidadRegistros(bean1.getCantidad());

		//calcular numero para boton "retroceder pagina"
		if (bean1.getSalto()==1)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(bean1.getSalto()-1);

		//calcular numero para boton "avanzar pagina"
		if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(bean1.getSalto()+1);

		//calcular regs del x al y
		int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
		int al  = del+resultado.size()-1;
		output.setNdel(del);
		output.setNal(al);

		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find()) {
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}

		req.setAttribute("tarifa",""+tarifa);

		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);

		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);

		output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarRazonSocial");
		req.setAttribute("output", output);

		response.setStyle("resultadoRazonSocial");

		if (bean1.getFlagPagineo()==false) {
			//llamar a "Transaccion"
			LogAuditoriaBusqRazSocPJBean bt = new LogAuditoriaBusqRazSocPJBean();

			//Datos generales
			 //Modificado por: Proyecto Filtros de Acceso
			 //Fecha: 02/10/2006
			 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
			 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
			 //Fecha: 08/10/2006             
			 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
			 //Fin Modificación
			bt.setUsuarioSession(usuario);
			bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
			bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));

			bt.setTipoBusqPartida(Constantes.REG_RAZ_SOC_PJ);
			//datos particulares de esta transaccion
			bt.setPartSeleccionado("PJ");
			bt.setNumSedes(bean1.getSedesElegidas());

			if(br && bs) {
				bt.setNomApeRazSocPart(bean1.getArea2Razon2() + " " + bean1.getArea2SiglasB());
				bt.setRazSocPJ(bean1.getArea2Razon2() + " " + bean1.getArea2SiglasB());
			}
			else if(br) {
				bt.setNomApeRazSocPart(bean1.getArea2Razon2());
				bt.setRazSocPJ(bean1.getArea2Razon2());
			}
			else if(bs) {
				bt.setNomApeRazSocPart(bean1.getArea2SiglasB());
				bt.setRazSocPJ(bean1.getArea2SiglasB());
			}

			bt.setCodAreaReg(bean1.getComboArea());
			bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
			bt.setTipoPersPart("J");

			
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					/**
					  *  inicio, dbravo: 15/06/2007
					  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					  *  			   inicialmente el Job002.
					  */
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (bean1.getFlagPagineo()==false) {
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB) == false) {
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();

							if (isTrace(this))
								System.out.print(">> SQLq2 : " + q2.toString());
								
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));

							Thread llamador1 = new Thread(j);
							llamador1.start();
						}
					}
					/**
					 *  fin, dbravo: 15/06/2007
					 */
					
					
				}
			}//if flagpagineo

			conn.commit();
			if (usuario.getFgInterno()==false) {
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e) {
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA)) {
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");

				try{
					rollback(conn, request);
				}
				catch (Throwable ex) {
					log(Constantes.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					rollback(conn, request);
					response.setStyle("error");
				}
			}
			else {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
			}

		}
		catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}

		return response;
	} // fin metodo buscar razon social	//**************************************************************************	//**************************************************************************


public ControllerResponse runBuscarPredioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException
{


		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_PREDIO);


if (bean1.getArea3PredioNombreVia().length()==0)
	throw new ValidacionException("Por favor ingrese el dato Nombre VIA","");


//-empieza busqueda
StringBuffer q  = new StringBuffer();
StringBuffer q2 = new StringBuffer();
StringBuffer qca = new StringBuffer();


q.append(" SELECT ");
q2.append(" SELECT ");
qca.append(" SELECT ");

	// 2003-07-31 enviar el estado al JSP
	q.append(" PARTIDA.ESTADO as estado, ");


q.append(" REGIS_PUBLICO.SIGLAS  as regis_publico_siglas, ");
q.append(" OFIC_REGISTRAL.NOMBRE as ofic_registral_nombre, ");
q.append(" IND_PRTC.CUR_PRTC     as ind_prtc_cur_prtc, ");
q.append(" IND_PRTC.TIPO_PERS    as ind_prtc_tipo_pers, ");
q.append(" PARTIDA.REFNUM_PART   as partida_refnum_part, ");
q.append(" PARTIDA.NUM_PARTIDA   as partida_num_partida, ");
q.append(" PARTIDA.REG_PUB_ID    as partida_reg_pub_id, ");
q.append(" PARTIDA.OFIC_REG_ID   as partida_ofic_reg_id, ");
q.append(" REG_PREDIOS.PROV_ID   as reg_predios_prov_id, ");
q.append(" REG_PREDIOS.NO_ZONA   as reg_predios_no_zona, ");
q.append(" REG_PREDIOS.NU_INMB   as reg_predios_nu_inmb, ");
q.append(" REG_PREDIOS.NUM_INTER as reg_predios_num_inter, ");
q.append(" REG_PREDIOS.NO_VIA    as reg_predios_no_via, ");
q.append(" REG_PREDIOS.DPTO_ID   as reg_predios_dpto_id, ");
q.append(" REG_PREDIOS.DIST_ID   as reg_predios_dist_id ");
q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
qca.append(" count(partida.refnum_part) ");


q.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,IND_PRTC,PARTIDA,REG_PREDIOS, grupo_libro_area gla, grupo_libro_area_det glad ");
q.append(" WHERE REG_PREDIOS.ESTADO = '1' ");
q.append(" and COD_PARTIC in ('072','073','074') ");
q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q.append(" AND REG_PREDIOS.REFNUM_PART = PARTIDA.REFNUM_PART ");
q.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
appendCondicionEstadoPartida(q);
//q.append(" and partida.estado='1'");


q2.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,IND_PRTC,PARTIDA,REG_PREDIOS, grupo_libro_area gla, grupo_libro_area_det glad ");
q2.append(" WHERE REG_PREDIOS.ESTADO = '1' ");
q2.append(" and COD_PARTIC in ('072','073','074') ");
q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q2.append(" AND REG_PREDIOS.REFNUM_PART = PARTIDA.REFNUM_PART ");
q2.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
appendCondicionEstadoPartida(q2);
//q2.append(" and partida.estado='1'");



qca.append(" FROM IND_PRTC,PARTIDA,REG_PREDIOS, grupo_libro_area gla, grupo_libro_area_det glad ");
qca.append(" WHERE REG_PREDIOS.ESTADO = '1' ");
qca.append(" and COD_PARTIC in ('072','073','074') ");


//qca.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
//qca.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
//qca.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");


qca.append(" AND REG_PREDIOS.REFNUM_PART = PARTIDA.REFNUM_PART ");
qca.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
appendCondicionEstadoPartida(qca);
//qca.append(" and partida.estado='1'");

q.append(" and DPTO_ID='").append(bean1.getArea3PredioDepartamento()).append("'");
q.append(" AND PROV_ID='").append(bean1.getArea3PredioProvincia()).append("'");
q.append(" AND DIST_ID='").append(bean1.getArea3PredioDistrito()).append("'");
q.append(" AND NO_VIA like '").append(bean1.getArea3PredioNombreVia()).append("%'");
q.append(" and TIPO_VIA ='").append(bean1.getArea3PredioTipoVia()).append("'");


q2.append(" and DPTO_ID='").append(bean1.getArea3PredioDepartamento()).append("'");
q2.append(" AND PROV_ID='").append(bean1.getArea3PredioProvincia()).append("'");
q2.append(" AND DIST_ID='").append(bean1.getArea3PredioDistrito()).append("'");
q2.append(" AND NO_VIA like '").append(bean1.getArea3PredioNombreVia()).append("%'");
q2.append(" and TIPO_VIA ='").append(bean1.getArea3PredioTipoVia()).append("'");


qca.append(" and DPTO_ID='").append(bean1.getArea3PredioDepartamento()).append("'");
qca.append(" AND PROV_ID='").append(bean1.getArea3PredioProvincia()).append("'");
qca.append(" AND DIST_ID='").append(bean1.getArea3PredioDistrito()).append("'");
qca.append(" AND NO_VIA like '").append(bean1.getArea3PredioNombreVia()).append("%'");
qca.append(" and TIPO_VIA ='").append(bean1.getArea3PredioTipoVia()).append("'");


if (bean1.getArea3PredioNombreZona().length()>0)
	{
		q.append(" AND TIPO_ZONA = '").append(bean1.getArea3PredioTipoZona()).append("'");
		q.append(" AND NO_ZONA like '").append(bean1.getArea3PredioNombreZona()).append("%'");
		q2.append(" AND TIPO_ZONA = '").append(bean1.getArea3PredioTipoZona()).append("'");
		q2.append(" AND NO_ZONA like '").append(bean1.getArea3PredioNombreZona()).append("%'");
		qca.append(" AND TIPO_ZONA = '").append(bean1.getArea3PredioTipoZona()).append("'");
		qca.append(" AND NO_ZONA like '").append(bean1.getArea3PredioNombreZona()).append("%'");
	}

if (bean1.getArea3PredioNumero().length()>0)
	{
		q.append(" and TIPO_NUMER = '").append(bean1.getArea3PredioTipoNumerac()).append("'");
		q.append(" and NU_INMB = '").append(bean1.getArea3PredioNumero()).append("'");
		q2.append(" and TIPO_NUMER = '").append(bean1.getArea3PredioTipoNumerac()).append("'");
		q2.append(" and NU_INMB = '").append(bean1.getArea3PredioNumero()).append("'");
		qca.append(" and TIPO_NUMER = '").append(bean1.getArea3PredioTipoNumerac()).append("'");
		qca.append(" and NU_INMB = '").append(bean1.getArea3PredioNumero()).append("'");
	}

if (bean1.getArea3PredioInteriorNro().length()>0)
	{
		q.append(" and TIPO_INTER = '").append(bean1.getArea3PredioTipoInterior()).append("'");
		q.append(" and NUM_INTER = '").append(bean1.getArea3PredioInteriorNro()).append("'");
		q2.append(" and TIPO_INTER = '").append(bean1.getArea3PredioTipoInterior()).append("'");
		q2.append(" and NUM_INTER = '").append(bean1.getArea3PredioInteriorNro()).append("'");
		qca.append(" and TIPO_INTER = '").append(bean1.getArea3PredioTipoInterior()).append("'");
		qca.append(" and NUM_INTER = '").append(bean1.getArea3PredioInteriorNro()).append("'");
	}


StringBuffer sbregpub = new StringBuffer();
if (bean1.getSedesElegidas().length==1)
		sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
		sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
q.append(sbregpub.toString());
q2.append(sbregpub.toString());
qca.append(sbregpub.toString());


//q.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q.append(" and partida.cod_libro = glad.cod_libro  ");
q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q2.append(" and partida.cod_libro = glad.cod_libro  ");
q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//qca.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
qca.append(" and partida.cod_libro = glad.cod_libro  ");
qca.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
qca.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");

q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");

//contar
int conteo=0;
Propiedades propiedades = Propiedades.getInstance();
if (bean1.getFlagPagineo()==false)
{
	if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
	stmtc   = conn.createStatement();
	rsetc   = stmtc.executeQuery(qca.toString());
	boolean bc = rsetc.next();
	conteo = rsetc.getInt(1);

	if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
		throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
	if (conteo==0)
		throw new ValidacionException("No se encontraron resultados para su búsqueda");
}
if (isTrace(this)) System.out.println("___verquery___"+q.toString());

			/**
			 * inicio, dbravo: 15/06/2007
			 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
			//UsoServicio_________________________________
			if (bean1.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				/
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			/**
			  * fin, dbravo: 15/06/2007
			  */


stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(propiedades.getLineasPorPag());
rset   = stmt.executeQuery(q.toString());
if (bean1.getSalto()>1)
	rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));

boolean b = rset.next();


ArrayList resultado = new ArrayList();


StringBuffer sb = new StringBuffer();


DboTmDepartamento dboTmDepartamento = new DboTmDepartamento(dconn);
DboTmProvincia dboTmProvincia = new DboTmProvincia(dconn);
DboTmDistrito dboTmDistrito = new DboTmDistrito(dconn);
DboPrtcNat dboPrtcNat = new DboPrtcNat(dconn);
DboPrtcJur dboPrtcJur = new DboPrtcJur(dconn);


int conta=0;
boolean haySiguiente = false;
while (b==true)
{
	PartidaBean partidaBean = new PartidaBean();

	//_completar los detalles de la partida encontrada
	String refNumPart = rset.getString("partida_refnum_part");
	partidaBean.setRefNumPart(refNumPart);
	partidaBean.setNumPartida(rset.getString("partida_num_partida"));
	partidaBean.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
	partidaBean.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
	// 2003-07-31 enviar el estado al JSP
	partidaBean.setEstado(rset.getString("estado"));

	String regPubId   = rset.getString("partida_reg_pub_id");
	String oficRegId  = rset.getString("partida_ofic_reg_id");

		//direccion = Dpto, Provincia, Distrito, Via, Zona, Numeracion e Interior
		sb.delete(0,sb.length());
		dboTmDepartamento.clearAll();
		dboTmDepartamento.setFieldsToRetrieve(DboTmDepartamento.CAMPO_NOMBRE);
		dboTmDepartamento.setField(DboTmDepartamento.CAMPO_DPTO_ID,rset.getString("reg_predios_dpto_id"));
		dboTmDepartamento.setField(DboTmDepartamento.CAMPO_ESTADO,"1");
		if (dboTmDepartamento.find()==true)
			sb.append(dboTmDepartamento.getField(DboTmDepartamento.CAMPO_NOMBRE).trim());
		sb.append(" ");
		//-
		dboTmProvincia.clearAll();
		dboTmProvincia.setFieldsToRetrieve(DboTmProvincia.CAMPO_NOMBRE);
		dboTmProvincia.setField(dboTmProvincia.CAMPO_DPTO_ID,rset.getString("reg_predios_dpto_id"));
		dboTmProvincia.setField(dboTmProvincia.CAMPO_PROV_ID,rset.getString("reg_predios_prov_id"));
		dboTmProvincia.setField(dboTmProvincia.CAMPO_ESTADO,"1");
		if (dboTmProvincia.find()==true)
			sb.append(dboTmProvincia.getField(dboTmProvincia.CAMPO_NOMBRE).trim());
		sb.append(" ");
		//-
		dboTmDistrito.clearAll();
		dboTmDistrito.setFieldsToRetrieve(DboTmDistrito.CAMPO_NOMBRE);
		dboTmDistrito.setField(DboTmDistrito.CAMPO_DPTO_ID,rset.getString("reg_predios_dpto_id"));
		dboTmDistrito.setField(DboTmDistrito.CAMPO_PROV_ID,rset.getString("reg_predios_prov_id"));
		dboTmDistrito.setField(DboTmDistrito.CAMPO_DIST_ID,rset.getString("reg_predios_dist_id"));
		dboTmDistrito.setField(DboTmDistrito.CAMPO_ESTADO,"1");
		if (dboTmDistrito.find()==true)
			sb.append(dboTmDistrito.getField(dboTmDistrito.CAMPO_NOMBRE).trim());
		sb.append(" ");
		//-

		String reg_predios_no_via  = rset.getString("reg_predios_no_via");
		if (reg_predios_no_via==null)
			reg_predios_no_via="";
		String reg_predios_no_zona = rset.getString("reg_predios_no_zona");
		if (reg_predios_no_zona==null)
			reg_predios_no_zona="";
		String reg_predios_nu_inmb = rset.getString("reg_predios_nu_inmb");
		if (reg_predios_nu_inmb==null)
			reg_predios_nu_inmb="";
		String reg_predios_num_inter =	rset.getString("reg_predios_num_inter");
		if (reg_predios_num_inter==null)
			reg_predios_num_inter="";

		sb.append(reg_predios_no_via.trim()).append(" ");
		sb.append(reg_predios_no_zona.trim()).append(" ");
		sb.append(reg_predios_nu_inmb.trim()).append(" ");
		sb.append(reg_predios_num_inter.trim());
		partidaBean.setPredioDireccion(sb.toString());

		//propietario del inmueble
		String tipoPersona = rset.getString("ind_prtc_tipo_pers");
		String curPrtc    = rset.getString("ind_prtc_cur_prtc");


		if (tipoPersona.equals("N") == true)
				{
					//persona natural
					dboPrtcNat.clearAll();
					sb.delete(0,sb.length());
					sb.append(DboPrtcNat.CAMPO_APE_PAT).append("|");
					sb.append(DboPrtcNat.CAMPO_APE_MAT).append("|");
					sb.append(DboPrtcNat.CAMPO_NOMBRES);
					dboPrtcNat.setFieldsToRetrieve(sb.toString());


					dboPrtcNat.setField(DboPrtcNat.CAMPO_CUR_PRTC, curPrtc);
					dboPrtcNat.setField(DboPrtcNat.CAMPO_REG_PUB_ID, regPubId);
					dboPrtcNat.setField(DboPrtcNat.CAMPO_OFIC_REG_ID, oficRegId);
					if (dboPrtcNat.find() == true)
						{
								sb.delete(0, sb.length());
								sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_PAT));
								sb.append(" ");
								sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_MAT));
								sb.append(", ");
								sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_NOMBRES));
							partidaBean.setPredioPropietario(sb.toString());
						}
				}
			else
				{
					//persona juridica
					dboPrtcJur.clearAll();
					dboPrtcJur.setFieldsToRetrieve(DboPrtcJur.CAMPO_RAZON_SOCIAL);
					dboPrtcJur.setField(dboPrtcJur.CAMPO_CUR_PRTC, curPrtc);
					dboPrtcJur.setField(DboPrtcJur.CAMPO_REG_PUB_ID, regPubId);
					dboPrtcJur.setField(DboPrtcJur.CAMPO_OFIC_REG_ID, oficRegId);
					if (dboPrtcJur.find() == true)
							partidaBean.setPredioPropietario(dboPrtcJur.getField(DboPrtcJur.CAMPO_RAZON_SOCIAL));
				} //if (tipoPersona.equals("N")

	resultado.add(partidaBean);
	conta++;
	b = rset.next();

		if (conta==propiedades.getLineasPorPag())
		{
			if(b==true)
				haySiguiente = true;

			break;
		}
}//while (b==true)

if (resultado.size()==0)
	throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

FormOutputBuscarPartida output = new FormOutputBuscarPartida();
output.setResultado(resultado);
if (bean1.getFlagPagineo()==false)
	output.setCantidadRegistros(String.valueOf(conteo));
else
	output.setCantidadRegistros(bean1.getCantidad());


//calcular numero para boton "retroceder pagina"
if (bean1.getSalto()==1)
	output.setPagAnterior(-1);
else
	output.setPagAnterior(bean1.getSalto()-1);

//calcular numero para boton "avanzar pagina"
if (haySiguiente==false)
	output.setPagSiguiente(-1);
else
	output.setPagSiguiente(bean1.getSalto()+1);


//calcular regs del x al y
int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
int al  = del+resultado.size()-1;
output.setNdel(del);
output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);

			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarPredio");
			req.setAttribute("output", output);
			response.setStyle("resultadoPredio");

			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBuscaPartidaRegPrediosBean bt = new LogAuditoriaBuscaPartidaRegPrediosBean();

				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
				bt.setTipoBusqPartida(Constantes.REG_PREDIO);
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart("*");
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
				bt.setTipoPersPart("*");
				//_
				bt.setDpto(bean1.getArea3PredioDepartamento());
				bt.setProv(bean1.getArea3PredioProvincia());
				bt.setDist(bean1.getArea3PredioDistrito());
				bt.setNomZona(bean1.getArea3PredioNombreZona());
				bt.setNomVia(bean1.getArea3PredioNombreVia());
				bt.setPais("01");
				bt.setTpoInt(bean1.getArea3PredioTipoInterior());
				bt.setTpoNum(bean1.getArea3PredioTipoNumerac());
				bt.setNumInt(bean1.getArea3PredioInteriorNro());
				bt.setTpoVia(bean1.getArea3PredioTipoVia());
				bt.setNumInmb(bean1.getArea3PredioNumero());
				bt.setTpoZona(bean1.getArea3PredioTipoZona());
				/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
					*/
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					/**
					  *  inicio, dbravo: 15/06/2007
					  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					  *  			   inicialmente el Job002.
					  */
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (bean1.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
								Job004 j = new Job004();
								j.setQuery(q2.toString());
								j.setUsuario(usuario);
								j.setCostoServicio(prepagoBean.getMontoBruto());
								j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
								Thread llamador1 = new Thread(j);
								llamador1.start();
						}
					}
					/**
					 * fin, dbravo: 15/06/2007
					 */
					
				}
			}//if flagPagine

			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}


		return response;
	} // fin metodo buscar Predio

	public ControllerResponse runBuscarMineriaState(
	ControllerRequest request, ControllerResponse response)
		throws ControllerException
	{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;


		try {
			init(request);
			validarSesion(request);


			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_MINERIAxSOCIEDAD);


			String derecho = bean1.getArea3MineriaDerechoMinero().trim();
			String sociedad = bean1.getArea3MineriaSociedad().trim();

			//validar que tenga por lo menos uno de los dos valores
			if (derecho.length()==0 && sociedad.length()==0)
				throw new ValidacionException("Faltan datos para la búsqueda","");

			//-empieza busqueda
			StringBuffer q  = new StringBuffer();
			StringBuffer q2 = new StringBuffer();
			StringBuffer qca = new StringBuffer();

			q.append(" SELECT ");
			q2.append(" SELECT ");
			qca.append(" SELECT ");

			// 2003-07-31 enviar el estado al JSP
			q.append(" PARTIDA.ESTADO as estado, ");
			q.append(" PARTIDA.NUM_PARTIDA   as partida_num_partida, ");
			q.append(" PARTIDA.REFNUM_PART   as partida_refnum_part, ");
			q.append(" REGIS_PUBLICO.SIGLAS  as regis_publico_siglas, ");
			q.append(" OFIC_REGISTRAL.NOMBRE as ofic_registral_nombre, ");
			if (derecho.length()>0)
				q.append(" DERE_MINE.NO_DERE as dere_mine_no_dere");
			else
				q.append(" SOCI_MINE.NO_SOCI as soci_mine_no_soci");
			q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
			qca.append(" count(partida.refnum_part) ");

			q.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
			q2.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
			qca.append(" FROM PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");

			if (derecho.length()>0)
			{
				q.append(",DERE_MINE");
				q2.append(",DERE_MINE");
				qca.append(",DERE_MINE");
			}
			else
			{
				q.append(",SOCI_MINE");
				q2.append(",SOCI_MINE");
				qca.append(",SOCI_MINE");
			}

			q.append(" WHERE REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			appendCondicionEstadoPartida(q);
			//q.append(" and partida.estado='1'");

			q2.append(" WHERE REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			appendCondicionEstadoPartida(q2);
			//q2.append(" and partida.estado='1'");
			
			qca.append(" where ");
			//qca.append(" WHERE REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			//qca.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			//qca.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			
			
			if (derecho.length()>0)
			{
				q.append(" AND DERE_MINE.REFNUM_PART = PARTIDA.REFNUM_PART ");
				q.append(" AND DERE_MINE.ESTADO = '1' ");
				q.append(" AND DERE_MINE.NO_DERE LIKE '").append(derecho).append("%'");
				response.setStyle("resultadoMineriaxDerecho");
				q2.append(" AND DERE_MINE.REFNUM_PART = PARTIDA.REFNUM_PART ");
				q2.append(" AND DERE_MINE.ESTADO = '1' ");
				q2.append(" AND DERE_MINE.NO_DERE LIKE '").append(derecho).append("%'");
			
				qca.append(" DERE_MINE.REFNUM_PART = PARTIDA.REFNUM_PART ");
				qca.append(" AND DERE_MINE.ESTADO = '1' ");
				qca.append(" AND DERE_MINE.NO_DERE LIKE '").append(derecho).append("%'");
			}
			else
			{
				q.append(" AND SOCI_MINE.REFNUM_PART = PARTIDA.REFNUM_PART ");
				q.append(" AND SOCI_MINE.ESTADO ='1'");
				q.append(" AND SOCI_MINE.NO_SOCI LIKE '").append(sociedad).append("%'");
				response.setStyle("resultadoMineriaxSociedad");
				q2.append(" AND SOCI_MINE.REFNUM_PART = PARTIDA.REFNUM_PART ");
				q2.append(" AND SOCI_MINE.ESTADO ='1'");
				q2.append(" AND SOCI_MINE.NO_SOCI LIKE '").append(sociedad).append("%'");
			
			
				qca.append(" SOCI_MINE.REFNUM_PART = PARTIDA.REFNUM_PART ");
				qca.append(" AND SOCI_MINE.ESTADO ='1'");
				qca.append(" AND SOCI_MINE.NO_SOCI LIKE '").append(sociedad).append("%' ");
			}
			appendCondicionEstadoPartida(qca);
			//qca.append(" and partida.estado='1'");


			StringBuffer sbregpub = new StringBuffer();
			if (bean1.getSedesElegidas().length==1)
					sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
			q.append(sbregpub.toString());
			q2.append(sbregpub.toString());
			qca.append(sbregpub.toString());
			
			
			//q.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			q.append(" and partida.cod_libro = glad.cod_libro  ");
			q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
			//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
			//qca.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			qca.append(" and partida.cod_libro = glad.cod_libro  ");
			qca.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			qca.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
			
			
			q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");
			
			//contar
			Propiedades propiedades = Propiedades.getInstance();
			
			
			int conteo=0;
			if (bean1.getFlagPagineo()==false)
			{
				if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qca.toString());
				boolean bc = rsetc.next();
				conteo = rsetc.getInt(1);
			
				if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");
			}
			if (isTrace(this)) System.out.println("___verquery___"+q.toString());
						
			
						/**
						 * inicio, dbravo: 15/06/2007
						 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
			
						//UsoServicio_________________________________
						if (bean1.getFlagPagineo()==false)
						{
							/*
							validar que el usuario NO sea de zona WEB
							/
							if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
							{
								//estamos en la primera llamada
								//enviamos TODOS los registros encontrados
								//a otro Thread para que registre el UsoServicio
									Job002 j = new Job002();
									j.setQuery(q2.toString());
									j.setUsuario(usuario);
									j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
									Thread llamador1 = new Thread(j);
									llamador1.start();
							}
						}
						
						/**
						  * fin, dbravo: 15/06/2007
						  */
			
			
			
			//descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
			
			//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();
			
			
			
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
			
			boolean b = rset.next();
			ArrayList resultado = new ArrayList();
			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);
			
			
			StringBuffer sb = new StringBuffer();
			
			
			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
			
				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("partida_refnum_part");
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setNumPartida(rset.getString("partida_num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
				// 2003-07-31 enviar el estado al JSP
				partidaBean.setEstado(rset.getString("estado"));
			
				//ficha
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(dboFicha.CAMPO_FICHA).append("|");
				sb.append(dboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
				if (dboFicha.find() == true)
						{
							partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
							String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
							int nbis = 0;
							try {
								nbis = Integer.parseInt(bis);
							}
							catch (NumberFormatException n)
							{
								nbis =0;
							}
							if (nbis>=1)
								partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
						}
			
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
				if (dboTomoFolio.find() == true)
						{
							partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
							partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
			
			
							String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
							String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);
			
			
							if (bist.trim().length() > 0)
									partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
			
			
							if (bisf.trim().length() > 0)
									partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
							//28dic2002
							//quitar el caracter "9" del inicio del tomoid
								if (partidaBean.getTomoId().length()>0)
								{
									if (partidaBean.getTomoId().startsWith("9"))
										{
											String ntomo = partidaBean.getTomoId().substring(1);
											partidaBean.setTomoId(ntomo);
										}
								}
						}
			
			
				if (derecho.length()>0)
					partidaBean.setDerechoMinero(rset.getString("dere_mine_no_dere"));
				else
					partidaBean.setMineriaRazonSocial(rset.getString("soci_mine_no_soci"));
			
				resultado.add(partidaBean);
				conta++;
			
				b = rset.next();
					if (conta==propiedades.getLineasPorPag())
					{
						if(b==true)
							haySiguiente = true;
			
						break;
					}
			}//while (b==true)
			
			if (resultado.size()==0)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			
			FormOutputBuscarPartida output = new FormOutputBuscarPartida();
			output.setResultado(resultado);
			if (bean1.getFlagPagineo()==false)
				output.setCantidadRegistros(String.valueOf(conteo));
			else
				output.setCantidadRegistros(bean1.getCantidad());
			
			
			//calcular numero para boton "retroceder pagina"
			if (bean1.getSalto()==1)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(bean1.getSalto()-1);
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(bean1.getSalto()+1);
			
			
			//calcular regs del x al y
			int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);



		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);

			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarMineria");
			req.setAttribute("output", output);

			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBuscaPartidaRegMinBean bt = new LogAuditoriaBuscaPartidaRegMinBean();

				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
				bt.setTipoBusqPartida(Constantes.REG_MINERO);
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart("*");
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
				bt.setTipoPersPart("*");
				//_
					if (derecho.length()>0) {
						bt.setTipoParam("D");
						bt.setValor(derecho);
					} else {
						bt.setTipoParam("R");
						bt.setValor(sociedad);
					}
					/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
					*/
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					/**
					  *  inicio, dbravo: 15/06/2007
					  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					  *  			   inicialmente el Job002.
					  */
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					//	UsoServicio_________________________________
					if (bean1.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
								Job004 j = new Job004();
								j.setQuery(q2.toString());
								j.setUsuario(usuario);
								j.setCostoServicio(prepagoBean.getMontoBruto());
								j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
								Thread llamador1 = new Thread(j);
								llamador1.start();
						}
					}
					/**
					  * fin, dbravo: 15/06/2007
					  */
				}
			}//if flagpagine


			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}


		return response;
	} // fin metodo buscar Mineria
	//**************************************************************************
	//**************************************************************************


public ControllerResponse runBuscarEmbarcacionState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException
{


		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			// Inicio:mgarate:31/05/2007
			   String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=11";
			// Fin:mgarate:31/05/2007
			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, Constantes.BUSQUEDA_INDIRECTA_EMBARCACION);

			String matricula = bean1.getArea3EmbarcacionNumeroMatricula().trim();
			String nombre = bean1.getArea3EmbarcacionNombre().trim();

			boolean m = false;
			boolean n = false;

			if (matricula.length() > 0)
							m = true;

					//5sep2002-solamente debe buscar por uno de los dos
			if (m == false) {
						if (nombre.length() > 0)
								n = true;
					}

			if (m == false && n == false)
				throw new ValidacionException("Faltan datos para la búsqueda","");



//-empieza busqueda
StringBuffer q  = new StringBuffer();
StringBuffer q2 = new StringBuffer();
StringBuffer qca = new StringBuffer();


q.append(" SELECT /*+ordered */ ");
q2.append(" SELECT /*+ordered */ ");
qca.append(" SELECT /*+ordered */ ");



	// 2003-07-31 enviar el estado al JSP
	q.append(" PARTIDA.ESTADO as estado, ");
q.append(" PARTIDA.REFNUM_PART        as partida_refnum_part, ");
q.append(" PARTIDA.COD_LIBRO          as partida_cod_libro, ");
q.append(" PARTIDA.NUM_PARTIDA        as partida_num_partida, ");
q.append(" REGIS_PUBLICO.SIGLAS       as regis_publico_siglas, ");
q.append(" OFIC_REGISTRAL.NOMBRE      as ofic_registral_nombre, ");
q.append(" REG_EMB_PESQ.NUM_MATRICULA as reg_emb_pesq_num_matricula, ");
q.append(" REG_EMB_PESQ.NOMBRE_EMB    as reg_emb_pesq_nombre_emb ");


q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
qca.append(" count(partida.refnum_part) ");


q.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,REG_EMB_PESQ,PARTIDA, grupo_libro_area_det glad, grupo_libro_area gla  ");
q.append(" WHERE REG_EMB_PESQ.ESTADO = '1' ");
q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q.append(" AND REG_EMB_PESQ.REFNUM_PART = PARTIDA.REFNUM_PART ");

q2.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,REG_EMB_PESQ,PARTIDA, grupo_libro_area_det glad, grupo_libro_area gla  ");
q2.append(" WHERE REG_EMB_PESQ.ESTADO = '1' ");
q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q2.append(" AND REG_EMB_PESQ.REFNUM_PART = PARTIDA.REFNUM_PART ");

qca.append(" FROM REG_EMB_PESQ,PARTIDA, grupo_libro_area_det glad, grupo_libro_area gla ");
qca.append(" WHERE REG_EMB_PESQ.ESTADO = '1' ");
qca.append(" AND REG_EMB_PESQ.REFNUM_PART = PARTIDA.REFNUM_PART ");

if (m == true && n == true)
{
	q.append(" and REG_EMB_PESQ.NUM_MATRICULA like '" + matricula + "%' AND ");
	q.append(" REG_EMB_PESQ.NOMBRE_EMB like '" + nombre + "%' ");
	q2.append(" and REG_EMB_PESQ.NUM_MATRICULA like '" + matricula + "%' AND ");
	q2.append(" REG_EMB_PESQ.NOMBRE_EMB like '" + nombre + "%' ");
	qca.append(" and REG_EMB_PESQ.NUM_MATRICULA like '" + matricula + "%' AND ");
	qca.append(" REG_EMB_PESQ.NOMBRE_EMB like '" + nombre + "%' ");
}
if (m == true)
	{
	q.append(" and REG_EMB_PESQ.NUM_MATRICULA like '" + matricula + "%' ");
	q2.append(" and REG_EMB_PESQ.NUM_MATRICULA like '" + matricula + "%' ");
	qca.append(" and REG_EMB_PESQ.NUM_MATRICULA like '" + matricula + "%' ");
	}
if (n == true)
	{
	q.append(" and REG_EMB_PESQ.NOMBRE_EMB like '" + nombre + "%' ");
	q2.append(" and REG_EMB_PESQ.NOMBRE_EMB like '" + nombre + "%' ");
	qca.append(" and REG_EMB_PESQ.NOMBRE_EMB like '" + nombre + "%' ");
	}

StringBuffer sbregpub = new StringBuffer();
if (bean1.getSedesElegidas().length==1)
		sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
		sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
q.append(sbregpub.toString());
q2.append(sbregpub.toString());
qca.append(sbregpub.toString());


//q.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q.append(" and partida.cod_libro = glad.cod_libro  ");
q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q2.append(" and partida.cod_libro = glad.cod_libro  ");
q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//qca.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
qca.append(" and partida.cod_libro = glad.cod_libro  ");
qca.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
qca.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");

appendCondicionEstadoPartida(q);
appendCondicionEstadoPartida(q2);
appendCondicionEstadoPartida(qca);
/*
q.append(" and partida.estado='1'");
q2.append(" and partida.estado='1'");
qca.append(" and partida.estado='1'");
*/

q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");

//contar
Propiedades propiedades = Propiedades.getInstance();
int conteo=0;
if (bean1.getFlagPagineo()==false)
{
	if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
	stmtc   = conn.createStatement();
	rsetc   = stmtc.executeQuery(qca.toString());
	boolean bc = rsetc.next();
	conteo = rsetc.getInt(1);

	if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
		throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
	if (conteo==0)
		throw new ValidacionException("No se encontraron resultados para su búsqueda");
}
if (isTrace(this)) System.out.println("___verquery___"+q.toString());
//UsoServicio_________________________________
			/**
			 * inicio, dbravo: 15/06/2007
			 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion	
			if (bean1.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				/
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			*/




//descripcion area registral
DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
String descripcionAreaRegistral="";
if (dboTmAreaRegistral.find() == true)
	descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(propiedades.getLineasPorPag());
rset   = stmt.executeQuery(q.toString());
if (bean1.getSalto()>1)
	rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));

boolean b = rset.next();
ArrayList resultado = new ArrayList();
DboFicha dboFicha = new DboFicha(dconn);
DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
DboTmLibro dboTmLibro = new DboTmLibro(dconn);
DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
DboParticLibro dboParticLibro = new DboParticLibro(dconn);

StringBuffer sb = new StringBuffer();
int conta=0;
boolean haySiguiente = false;
while (b==true)
{
	PartidaBean partidaBean = new PartidaBean();

	//_completar los detalles de la partida encontrada
	String refNumPart = rset.getString("partida_refnum_part");
	String codLibro   = rset.getString("partida_cod_libro");
	partidaBean.setRefNumPart(refNumPart);
	partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
	partidaBean.setNumPartida(rset.getString("partida_num_partida"));
	partidaBean.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
	partidaBean.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
	// 2003-07-31 enviar el estado al JSP
	partidaBean.setEstado(rset.getString("estado"));

	//ficha
	dboFicha.clearAll();
	sb.delete(0, sb.length());
	sb.append(dboFicha.CAMPO_FICHA).append("|");
	sb.append(dboFicha.CAMPO_FICHA_BIS);
	dboFicha.setFieldsToRetrieve(sb.toString());
	dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
	if (dboFicha.find() == true)
			{
				partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
				String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
				int nbis = 0;
				try {
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException nfe)
				{
					nbis =0;
				}
				if (nbis>=1)
					partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");

			}

	//obtener tomo y foja
	dboTomoFolio.clearAll();
	sb.delete(0, sb.length());
	sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
	sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
	sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
	sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
	dboTomoFolio.setFieldsToRetrieve(sb.toString());
	dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
	if (dboTomoFolio.find() == true)
			{
				partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


				if (bist.trim().length() > 0)
						partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);


				if (bisf.trim().length() > 0)
						partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
				//28dic2002
				//quitar el caracter "9" del inicio del tomoid
					if (partidaBean.getTomoId().length()>0)
					{
						if (partidaBean.getTomoId().startsWith("9"))
							{
								String ntomo = partidaBean.getTomoId().substring(1);
								partidaBean.setTomoId(ntomo);
							}
					}
			}

	partidaBean.setEmbarcacionMatricula(rset.getString("reg_emb_pesq_num_matricula"));
	if (partidaBean.getEmbarcacionMatricula()==null)
		partidaBean.setEmbarcacionMatricula("");
	partidaBean.setEmbarcacionNombre(rset.getString("reg_emb_pesq_nombre_emb"));

	resultado.add(partidaBean);
	conta++;
	b = rset.next();
		if (conta==propiedades.getLineasPorPag())
		{
			if(b==true)
				haySiguiente = true;

			break;
		}
}//while (b==true)

if (resultado.size()==0)
	throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

FormOutputBuscarPartida output = new FormOutputBuscarPartida();
output.setResultado(resultado);
if (bean1.getFlagPagineo()==false)
	output.setCantidadRegistros(String.valueOf(conteo));
else
	output.setCantidadRegistros(bean1.getCantidad());


//calcular numero para boton "retroceder pagina"
if (bean1.getSalto()==1)
	output.setPagAnterior(-1);
else
	output.setPagAnterior(bean1.getSalto()-1);

//calcular numero para boton "avanzar pagina"
if (haySiguiente==false)
	output.setPagSiguiente(-1);
else
	output.setPagSiguiente(bean1.getSalto()+1);


//calcular regs del x al y
int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
int al  = del+resultado.size()-1;
output.setNdel(del);
output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);



			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarEmbarcacion");
			req.setAttribute("output", output);

			// Inicio:mgarate:31/05/2007
			   req.setAttribute("criterioBusqueda",criterioBusqueda);
			   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
			// Fin:mgarate:31/05/2007
			
		    /*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", bean1.getVerifica());
			/*fin:dbravo:14/09/2007*/    
			   
			response.setStyle("resultadoEmbarcacion");


			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBuscaPartidaRegEmb bt = new LogAuditoriaBuscaPartidaRegEmb();

				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
				bt.setTipoBusqPartida(Constantes.REG_EMB);
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart("*");
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
				bt.setTipoPersPart("*");
				//_
				bt.setTipoEmb("P");
				if (m == true)
					{
						bt.setTipoParam("M");
						bt.setValor(matricula);
					}
				else
					{
						bt.setTipoParam("N");
						bt.setValor(nombre);
					}
					/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
					*/
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					/**
					  *  inicio, dbravo: 15/06/2007
					  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					  *  			   inicialmente el Job002.
					  */
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (bean1.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
								Job004 j = new Job004();
								j.setQuery(q2.toString());
								j.setUsuario(usuario);
								j.setCostoServicio(prepagoBean.getMontoBruto());
								j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
								Thread llamador1 = new Thread(j);
								llamador1.start();
						}
					}
					/**
					  * fin, dbravo: 15/06/2007
					  */
				}
			}//flag pagineo

			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try{
						rollback(conn, request);


					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	} // fin metodo buscar embarcacion


	/**
	 * Propiedad Immueble No Predial -->Búsqueda Registro Buques-->Búsqueda Registro Buques  
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarBuqueState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException
		{


		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


		// Inicio:mgarate:18/06/2007
		   String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=15";
		// Fin:mgarate:18/06/2007

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

		try {
			init(request);
			validarSesion(request);


			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, Constantes.BUSQUEDA_INDIRECTA_BUQUE);


			String matricula = bean1.getArea3BuqueNumeroMatricula().trim();
			String nombre = bean1.getArea3BuqueNombre().trim();

			boolean m = false;
			boolean n = false;

			if (matricula.length() > 0)
							m = true;

					//5sep2002-solamente debe buscar por uno de los dos
			if (m == false) {
						if (nombre.length() > 0)
								n = true;
					}

			if (m == false && n == false)
				throw new ValidacionException("Faltan datos para la búsqueda","");

			//-empieza busqueda
			StringBuffer q  = new StringBuffer();
			StringBuffer q2 = new StringBuffer();
			StringBuffer qca = new StringBuffer();

			q.append(" SELECT ");
			q2.append(" SELECT ");
			qca.append(" SELECT ");

			// 2003-07-31 enviar el estado al JSP
			q.append(" PARTIDA.ESTADO as estado, ");
			q.append(" PARTIDA.REFNUM_PART      as partida_refnum_part,");
			q.append(" PARTIDA.NUM_PARTIDA      as partida_num_partida,");
			q.append(" REGIS_PUBLICO.SIGLAS     as regis_publico_siglas,");
			q.append(" OFIC_REGISTRAL.NOMBRE    as ofic_registral_nombre,");
			q.append(" REG_BUQUES.NOMBRE        as reg_buques_nombre,");
			q.append(" REG_BUQUES.NUM_MATRICULA as reg_buques_num_matricula");
			q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
			qca.append(" count(partida.refnum_part) ");

			StringBuffer sbregpub = new StringBuffer();
			if (bean1.getSedesElegidas().length==1)
					sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());

			q.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,REG_BUQUES,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
			q.append(" WHERE REG_BUQUES.ESTADO = '1' ");
			q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q.append(" and  reg_buques.refnum_part = partida.refnum_part ");
			q.append(sbregpub.toString());
			//q.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			q.append(" and partida.cod_libro = glad.cod_libro  ");
			q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
			
			
			q2.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,REG_BUQUES,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
			q2.append(" WHERE REG_BUQUES.ESTADO = '1' ");
			q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q2.append(" and  reg_buques.refnum_part = partida.refnum_part ");
			q2.append(sbregpub.toString());
			//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
			
			
			qca.append(" FROM REG_BUQUES,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
			qca.append(" WHERE REG_BUQUES.ESTADO = '1' ");
			//qca.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			//qca.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			//qca.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			qca.append(" and  reg_buques.refnum_part = partida.refnum_part ");
			qca.append(sbregpub.toString());
			//qca.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			qca.append(" and partida.cod_libro = glad.cod_libro  ");
			qca.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			qca.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");

			if (m == true && n == true)
			{
				q.append(" and REG_BUQUES.NUM_MATRICULA like '" + matricula + "%' AND ");
				q.append("REG_BUQUES.NOMBRE like '" + nombre + "%' ");
				q2.append(" and REG_BUQUES.NUM_MATRICULA like '" + matricula + "%' AND ");
				q2.append("REG_BUQUES.NOMBRE like '" + nombre + "%' ");
				qca.append(" and REG_BUQUES.NUM_MATRICULA like '" + matricula + "%' AND ");
				qca.append("REG_BUQUES.NOMBRE like '" + nombre + "%' ");
			}
			if (m == true && n == false)
				{
				q.append(" and REG_BUQUES.NUM_MATRICULA like '" + matricula + "%' ");
				q2.append(" and REG_BUQUES.NUM_MATRICULA like '" + matricula + "%' ");
				qca.append(" and REG_BUQUES.NUM_MATRICULA like '" + matricula + "%' ");
				}
			if (m == false && n == true)
				{
				q.append(" and REG_BUQUES.NOMBRE like '" + nombre + "%' ");
				q2.append(" and REG_BUQUES.NOMBRE like '" + nombre + "%' ");
				qca.append(" and REG_BUQUES.NOMBRE like '" + nombre + "%' ");
				}

				appendCondicionEstadoPartida(q);
				appendCondicionEstadoPartida(q2);
				appendCondicionEstadoPartida(qca);
				q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");
				
				//contar
				int conteo=0;
				Propiedades propiedades = Propiedades.getInstance();
				if (bean1.getFlagPagineo()==false)
				{
					if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
					stmtc   = conn.createStatement();
					rsetc   = stmtc.executeQuery(qca.toString());
					boolean bc = rsetc.next();
					conteo = rsetc.getInt(1);
				
					if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
						throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
					if (conteo==0)
						throw new ValidacionException("No se encontraron resultados para su búsqueda");
				}
				if (isTrace(this)) System.out.println("___verquery___"+q.toString());


				//UsoServicio_________________________________
						/**
						 * inicio, dbravo: 15/06/2007
						 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
						if (bean1.getFlagPagineo()==false)
						{
							/*
							validar que el usuario NO sea de zona WEB
							/
							if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
							{
								//estamos en la primera llamada
								//enviamos TODOS los registros encontrados
								//a otro Thread para que registre el UsoServicio
									Job002 j = new Job002();
									j.setQuery(q2.toString());
									j.setUsuario(usuario);
									j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
									Thread llamador1 = new Thread(j);
									llamador1.start();
							}
						}
						*/
			
			
			
			//descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
			
			boolean b = rset.next();
			ArrayList resultado = new ArrayList();
			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);
			
			StringBuffer sb = new StringBuffer();
			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
			
				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("partida_refnum_part");
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setNumPartida(rset.getString("partida_num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
				// 2003-07-31 enviar el estado al JSP
				partidaBean.setEstado(rset.getString("estado"));
			
				//ficha
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(dboFicha.CAMPO_FICHA).append("|");
				sb.append(dboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
				if (dboFicha.find() == true)
						{
							partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
							String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
							int nbis = 0;
							try {
								nbis = Integer.parseInt(bis);
							}
							catch (NumberFormatException nuf)
							{
								nbis =0;
							}
							if (nbis>=1)
								partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
						}
			
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
				if (dboTomoFolio.find() == true)
						{
							partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
							partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
			
			
							String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
							String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);
			
			
							if (bist.trim().length() > 0)
									partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
			
			
							if (bisf.trim().length() > 0)
									partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
							//28dic2002
							//quitar el caracter "9" del inicio del tomoid
								if (partidaBean.getTomoId().length()>0)
								{
									if (partidaBean.getTomoId().startsWith("9"))
										{
											String ntomo = partidaBean.getTomoId().substring(1);
											partidaBean.setTomoId(ntomo);
										}
								}
						}
			
				partidaBean.setBuqueNombre(rset.getString("reg_buques_nombre"));
				partidaBean.setBuqueMatricula(rset.getString("reg_buques_num_matricula"));
			
				resultado.add(partidaBean);
				conta++;
				b = rset.next();
					if (conta==propiedades.getLineasPorPag())
					{
						if(b==true)
							haySiguiente = true;
			
						break;
					}
			}//while (b==true)

			if (resultado.size()==0)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			
			FormOutputBuscarPartida output = new FormOutputBuscarPartida();
			output.setResultado(resultado);
			if (bean1.getFlagPagineo()==false)
				output.setCantidadRegistros(String.valueOf(conteo));
			else
				output.setCantidadRegistros(bean1.getCantidad());
			//calcular numero para boton "retroceder pagina"
			if (bean1.getSalto()==1)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(bean1.getSalto()-1);
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(bean1.getSalto()+1);


			//calcular regs del x al y
			int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);

		   // Inicio:mgarate:31/05/2007
		   	  req.setAttribute("criterioBusqueda",criterioBusqueda);
		   	  req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
		   // Fin:mgarate:31/05/2007

		   	/*inicio:dbravo:14/09/2007*/
		   	req.setAttribute("flagVerifica", bean1.getVerifica());
		   	/*fin:dbravo:14/09/2007*/
		   	  
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarBuque");
			req.setAttribute("output", output);

			response.setStyle("resultadoBuque");


			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBuscaPartidaRegEmb bt = new LogAuditoriaBuscaPartidaRegEmb();

				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
				bt.setTipoBusqPartida(Constantes.REG_EMB);
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart("*");
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
				bt.setTipoPersPart("*");
				//_
				bt.setTipoEmb("B");
				if (m == true)
					{
						bt.setTipoParam("M");
						bt.setValor(matricula);
					}
				else
					{
						bt.setTipoParam("N");
						bt.setValor(nombre);
					}
					/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
					*/
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					/**
					  *  inicio, dbravo: 15/06/2007
					  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					  *  			   inicialmente el Job002.
					  */
					if (bean1.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
								Job004 j = new Job004();
								j.setQuery(q2.toString());
								j.setUsuario(usuario);
								j.setCostoServicio(prepagoBean.getMontoBruto());
								j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
								Thread llamador1 = new Thread(j);
								llamador1.start();
						}
					}
					/**
					  * fin, dbravo: 15/06/2007
					  */
				}
			}//if flagPagieno


			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	} // fin metodo buscar Buque

public ControllerResponse runBuscarAeronaveXMatriculaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException
{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			// Inicio:mgarate:31/05/2007
			   String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=12";
			// Fin:mgarate:31/05/2007
			
			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_AERONAVEXMATRICULA);


//-empieza busqueda
StringBuffer q  = new StringBuffer();
StringBuffer q2 = new StringBuffer();
StringBuffer qca = new StringBuffer();


q.append(" SELECT ");
q2.append(" SELECT ");
qca.append(" SELECT ");


	// 2003-07-31 enviar el estado al JSP
	q.append(" PARTIDA.ESTADO as estado, ");
q.append(" PARTIDA.REFNUM_PART         as partida_refnum_part,");
q.append(" PARTIDA.NUM_PARTIDA         as partida_num_partida,");
q.append(" REGIS_PUBLICO.SIGLAS        as regis_publico_siglas,");
q.append(" OFIC_REGISTRAL.NOMBRE       as ofic_registral_nombre,");
q.append(" REG_AERONAVES.NUM_MATRICULA as reg_aeronaves_num_matricula,");
q.append(" IND_PRTC.TIPO_PERS          as ind_prtc_tipo_pers,");
q.append(" IND_PRTC.CUR_PRTC           as ind_prtc_cur_prtc,");
q.append(" PARTIDA.REG_PUB_ID          as partida_reg_pub_id,");
q.append(" PARTIDA.OFIC_REG_ID         as partida_ofic_reg_id");


q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");

qca.append(" count(partida.refnum_part) ");

StringBuffer sbregpub = new StringBuffer();
if (bean1.getSedesElegidas().length==1)
		sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
		sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());

q.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
q.append(" WHERE  ");
q.append(" REG_AERONAVES.ESTADO = '1' ");
q.append(" AND IND_PRTC.COD_PARTIC = '003' ");
q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q.append(" AND REG_AERONAVES.REFNUM_PART = PARTIDA.REFNUM_PART ");
q.append(" AND PARTIDA.REFNUM_PART = IND_PRTC.REFNUM_PART ");
q.append(sbregpub.toString());
//q.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q.append(" and partida.cod_libro = glad.cod_libro  ");
q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
q.append(" and REG_AERONAVES.NUM_MATRICULA = '").append(bean1.getArea3AeronaveNumeroMatricula()).append("'");


q2.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
q2.append(" WHERE  ");
q2.append(" REG_AERONAVES.ESTADO = '1' ");
q2.append(" AND IND_PRTC.COD_PARTIC = '003' ");
q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q2.append(" AND REG_AERONAVES.REFNUM_PART = PARTIDA.REFNUM_PART ");
q2.append(" AND PARTIDA.REFNUM_PART = IND_PRTC.REFNUM_PART ");
q2.append(sbregpub.toString());
//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q2.append(" and partida.cod_libro = glad.cod_libro  ");
q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
q2.append(" and REG_AERONAVES.NUM_MATRICULA = '").append(bean1.getArea3AeronaveNumeroMatricula()).append("'");


qca.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
qca.append(" WHERE  ");
qca.append(" REG_AERONAVES.ESTADO = '1' ");
qca.append(" AND IND_PRTC.COD_PARTIC = '003' ");
qca.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
qca.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
qca.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
qca.append(" AND REG_AERONAVES.REFNUM_PART = PARTIDA.REFNUM_PART ");
qca.append(" AND PARTIDA.REFNUM_PART = IND_PRTC.REFNUM_PART ");
qca.append(sbregpub.toString());
//qca.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
qca.append(" and partida.cod_libro = glad.cod_libro  ");
qca.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
qca.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
qca.append(" and REG_AERONAVES.NUM_MATRICULA = '").append(bean1.getArea3AeronaveNumeroMatricula()).append("'");

appendCondicionEstadoPartida(q);
appendCondicionEstadoPartida(q2);
appendCondicionEstadoPartida(qca);
/*
q.append(" and partida.estado='1'");
q2.append(" and partida.estado='1'");
qca.append(" and partida.estado='1'");
*/
q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");

//contar
Propiedades propiedades = Propiedades.getInstance();
int conteo=0;
if (bean1.getFlagPagineo()==false)
{
	if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
	stmtc   = conn.createStatement();
	rsetc   = stmtc.executeQuery(qca.toString());
	boolean bc = rsetc.next();
	conteo = rsetc.getInt(1);

	if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
		throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
	if (conteo==0)
		throw new ValidacionException("No se encontraron resultados para su búsqueda");
}
if (isTrace(this)) System.out.println("___verquery___"+q.toString());
//UsoServicio_________________________________
			/**
			 * inicio, dbravo: 15/06/2007
			 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
			if (bean1.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				/
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			/**
			  * fin, dbravo: 15/06/2007
			  */



//descripcion area registral
DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
String descripcionAreaRegistral="";
if (dboTmAreaRegistral.find() == true)
	descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();


stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(propiedades.getLineasPorPag());
rset   = stmt.executeQuery(q.toString());
if (bean1.getSalto()>1)
	rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));


boolean b = rset.next();


ArrayList resultado = new ArrayList();


DboFicha dboFicha = new DboFicha(dconn);
DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
//DboTmLibro dboTmLibro = new DboTmLibro(conn);
//DboTmDocIden dboTmDocIden = new DboTmDocIden(conn);
DboPrtcNat dboPrtcNat = new DboPrtcNat(dconn);
DboPrtcJur dboPrtcJur = new DboPrtcJur(dconn);


StringBuffer sb = new StringBuffer();


int conta=0;
boolean haySiguiente = false;
while (b==true)
{
	PartidaBean partidaBean = new PartidaBean();

	//_completar los detalles de la partida encontrada
	String refNumPart = rset.getString("partida_refnum_part");
	partidaBean.setRefNumPart(refNumPart);
	partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
	partidaBean.setNumPartida(rset.getString("partida_num_partida"));
	partidaBean.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
	partidaBean.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
	// 2003-07-31 enviar el estado al JSP
	partidaBean.setEstado(rset.getString("estado"));

	String regPubId   = rset.getString("partida_reg_pub_id");
	String oficRegId  = rset.getString("partida_ofic_reg_id");

	//ficha
	dboFicha.clearAll();
	sb.delete(0, sb.length());
	sb.append(dboFicha.CAMPO_FICHA).append("|");
	sb.append(dboFicha.CAMPO_FICHA_BIS);
	dboFicha.setFieldsToRetrieve(sb.toString());
	dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
	if (dboFicha.find() == true)
			{
				partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
				String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
				int nbis = 0;
				try {
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException n)
				{
					nbis =0;
				}
				if (nbis>=1)
					partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
			}

	//obtener tomo y foja
	dboTomoFolio.clearAll();
	sb.delete(0, sb.length());
	sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
	sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
	sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
	sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
	dboTomoFolio.setFieldsToRetrieve(sb.toString());
	dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
	if (dboTomoFolio.find() == true)
			{
				partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


				if (bist.trim().length() > 0)
						partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);


				if (bisf.trim().length() > 0)
						partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
				//28dic2002
				//quitar el caracter "9" del inicio del tomoid
					if (partidaBean.getTomoId().length()>0)
					{
						if (partidaBean.getTomoId().startsWith("9"))
							{
								String ntomo = partidaBean.getTomoId().substring(1);
								partidaBean.setTomoId(ntomo);
							}
					}
			}


	partidaBean.setAeronaveMatricula(rset.getString("reg_aeronaves_num_matricula"));

	//propietario de la aeronave
		String tipoPersona = rset.getString("ind_prtc_tipo_pers");
		String curPrtc     = rset.getString("ind_prtc_cur_prtc");
		if (tipoPersona.equals("N") == true)
				{
					partidaBean.setAeronaveTipoTitular("PN");
					//persona natural
					dboPrtcNat.clearAll();
					sb.delete(0,sb.length());
					sb.append(DboPrtcNat.CAMPO_APE_PAT).append("|");
					sb.append(DboPrtcNat.CAMPO_APE_MAT).append("|");
					sb.append(DboPrtcNat.CAMPO_NOMBRES);
					dboPrtcNat.setFieldsToRetrieve(sb.toString());


					dboPrtcNat.setField(DboPrtcNat.CAMPO_CUR_PRTC, curPrtc);
					dboPrtcNat.setField(DboPrtcNat.CAMPO_REG_PUB_ID, regPubId);
					dboPrtcNat.setField(DboPrtcNat.CAMPO_OFIC_REG_ID, oficRegId);
					if (dboPrtcNat.find() == true)
						{
							sb.delete(0, sb.length());
							sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_PAT));
							sb.append(" ");
							sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_MAT));
							sb.append(", ");
							sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_NOMBRES));
							partidaBean.setAeronaveTipoTitular(sb.toString());
						}
				}
			else
				{
					partidaBean.setAeronaveTipoTitular("PJ");
					//persona juridica
					dboPrtcJur.clearAll();
					dboPrtcJur.setFieldsToRetrieve(DboPrtcJur.CAMPO_RAZON_SOCIAL);
					dboPrtcJur.setField(dboPrtcJur.CAMPO_CUR_PRTC, curPrtc);
					dboPrtcJur.setField(DboPrtcJur.CAMPO_REG_PUB_ID, regPubId);
					dboPrtcJur.setField(DboPrtcJur.CAMPO_OFIC_REG_ID, oficRegId);
					if (dboPrtcJur.find() == true)
							partidaBean.setAeronaveTipoTitular(dboPrtcJur.getField(DboPrtcJur.CAMPO_RAZON_SOCIAL));
				} //if (tipoPersona.equals("N")

	resultado.add(partidaBean);
	conta++;
	b = rset.next();
		if (conta==propiedades.getLineasPorPag())
		{
			if(b==true)
				haySiguiente = true;

			break;
		}
}//while (b==true)

if (resultado.size()==0)
	throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

FormOutputBuscarPartida output = new FormOutputBuscarPartida();
output.setResultado(resultado);
if (bean1.getFlagPagineo()==false)
	output.setCantidadRegistros(String.valueOf(conteo));
else
	output.setCantidadRegistros(bean1.getCantidad());


//calcular numero para boton "retroceder pagina"
if (bean1.getSalto()==1)
	output.setPagAnterior(-1);
else
	output.setPagAnterior(bean1.getSalto()-1);

//calcular numero para boton "avanzar pagina"
if (haySiguiente==false)
	output.setPagSiguiente(-1);
else
	output.setPagSiguiente(bean1.getSalto()+1);


//calcular regs del x al y
int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
int al  = del+resultado.size()-1;
output.setNdel(del);
output.setNal(al);

		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);


			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarAeronaveXMatricula");
			req.setAttribute("output", output);
			// Inicio:mgarate:31/05/2007
			   req.setAttribute("criterioBusqueda",criterioBusqueda);
			   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
			// Fin:mgarate:31/05/2007
			   
		   /*inicio:dbravo:14/09/2007*/
		   req.setAttribute("flagVerifica", bean1.getVerifica());
		   /*fin:dbravo:14/09/2007*/
		   
			response.setStyle("resultadoAeronave");



			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBuscaPartidaRegAereoBean bt = new LogAuditoriaBuscaPartidaRegAereoBean();

				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
				bt.setTipoBusqPartida(Constantes.REG_AEREO);
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart("*");
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
				bt.setTipoPersPart("*");
				//_
				bt.setTipoParam("M");
				bt.setValor(bean1.getArea3AeronaveNumeroMatricula());
				bt.setTipoTitular("");


					/*

					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
					*/
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					/**
					  *  inicio, dbravo: 15/06/2007
					  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					  *  			   inicialmente el Job002.
					  */
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (bean1.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
								Job004 j = new Job004();
								j.setQuery(q2.toString());
								j.setUsuario(usuario);
								j.setCostoServicio(prepagoBean.getMontoBruto());
								j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
								Thread llamador1 = new Thread(j);
								llamador1.start();
						}
					}
					/**
					  * fin, dbravo: 15/06/2007
					  */	
				}
			}//if flagpagie



			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	} // fin metodo buscar aeronaveXMatricula


public ControllerResponse runBuscarAeronaveXNombreState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {


		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			// Inicio:mgarate:31/05/2007
			   String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=13";
			// Fin:mgarate:31/05/2007
			InputBusqIndirectaBean bean1 =Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, Constantes.BUSQUEDA_INDIRECTA_AERONAVEXNOMBRE);


if (bean1.getArea3AeronaveApePat().trim().length()==0)
	throw new ValidacionException("Campo minimo requerido para la búsqueda: apellido paterno","");

//-empieza busqueda
StringBuffer q  = new StringBuffer();
StringBuffer q2 = new StringBuffer();
StringBuffer qca = new StringBuffer();

q.append(" SELECT ");
q2.append(" SELECT ");
qca.append(" SELECT ");

	// 2003-07-31 enviar el estado al JSP
	q.append(" PARTIDA.ESTADO as estado, ");
q.append(" PARTIDA.REFNUM_PART         as partida_refnum_part,");
q.append(" PARTIDA.NUM_PARTIDA         as partida_num_partida,");
q.append(" REGIS_PUBLICO.SIGLAS        as regis_publico_siglas,");
q.append(" OFIC_REGISTRAL.NOMBRE       as ofic_registral_nombre,");
q.append(" REG_AERONAVES.NUM_MATRICULA as reg_aeronaves_num_matricula,");
q.append(" PRTC_NAT.APE_PAT            as prtc_nat_ape_pat,");
q.append(" PRTC_NAT.APE_PAT            as prtc_nat_ape_mat,");
q.append(" PRTC_NAT.NOMBRES            as prtc_nat_nombres ");
q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
qca.append(" count(partida.refnum_part) ");

q.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PRTC_NAT,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
q.append(" WHERE ");
q.append(" REG_AERONAVES.ESTADO = '1' ");
q.append(" AND IND_PRTC.COD_PARTIC = '003' ");
q.append(" AND IND_PRTC.TIPO_PERS = 'N' ");
q.append(" AND PARTIDA.COD_LIBRO = '040' ");
q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q.append(" AND PRTC_NAT.CUR_PRTC = IND_PRTC.CUR_PRTC ");
q.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
q.append(" AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");


q2.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PRTC_NAT,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
q2.append(" WHERE ");
q2.append(" REG_AERONAVES.ESTADO = '1' ");
q2.append(" AND IND_PRTC.COD_PARTIC = '003' ");
q2.append(" AND IND_PRTC.TIPO_PERS = 'N' ");
q2.append(" AND PARTIDA.COD_LIBRO = '040' ");
q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q2.append(" AND PRTC_NAT.CUR_PRTC = IND_PRTC.CUR_PRTC ");
q2.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
q2.append(" AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");


qca.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PRTC_NAT,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
qca.append(" WHERE ");
qca.append(" REG_AERONAVES.ESTADO = '1' ");
qca.append(" AND IND_PRTC.COD_PARTIC = '003' ");
qca.append(" AND IND_PRTC.TIPO_PERS = 'N' ");
qca.append(" AND PARTIDA.COD_LIBRO = '040' ");
qca.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
qca.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
qca.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
qca.append(" AND PRTC_NAT.CUR_PRTC = IND_PRTC.CUR_PRTC ");
qca.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
qca.append(" AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");


q.append(" and APE_PAT like '").append(bean1.getArea3AeronaveApePat().trim()).append("%' ");
q2.append(" and APE_PAT like '").append(bean1.getArea3AeronaveApePat().trim()).append("%' ");
qca.append(" and APE_PAT like '").append(bean1.getArea3AeronaveApePat().trim()).append("%' ");

if (bean1.getArea3AeronaveApeMat().trim().length()>0)
{
	q.append(" and APE_MAT like '").append(bean1.getArea3AeronaveApeMat().trim()).append("%'");
	q2.append(" and APE_MAT like '").append(bean1.getArea3AeronaveApeMat().trim()).append("%'");
	qca.append(" and APE_MAT like '").append(bean1.getArea3AeronaveApeMat().trim()).append("%'");
}
if (bean1.getArea1Nombre().trim().length()>0)
{
	q.append(" and NOMBRES like '").append(bean1.getArea1Nombre().trim()).append("%'");
	q2.append(" and NOMBRES like '").append(bean1.getArea1Nombre().trim()).append("%'");
	qca.append(" and NOMBRES like '").append(bean1.getArea1Nombre().trim()).append("%'");
}

StringBuffer sbregpub = new StringBuffer();
if (bean1.getSedesElegidas().length==1)
	{
		sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
		sbregpub.append(" and PRTC_NAT.REG_PUB_ID = '").append(bean1.getSedesElegidas()[0]).append("'");
	}
if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
	{
		sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
		sbregpub.append(" and PRTC_NAT.REG_PUB_ID IN ").append(bean1.getSedesSQLString());
	}
q.append(sbregpub.toString());
q2.append(sbregpub.toString());
qca.append(sbregpub.toString());

//q.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q.append(" and partida.cod_libro = glad.cod_libro  ");
q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q2.append(" and partida.cod_libro = glad.cod_libro  ");
q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//qca.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
qca.append(" and partida.cod_libro = glad.cod_libro  ");
qca.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
qca.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");

appendCondicionEstadoPartida(q);
appendCondicionEstadoPartida(q2);
appendCondicionEstadoPartida(qca);
/*
q.append(" and partida.estado='1'");
q2.append(" and partida.estado='1'");
qca.append(" and partida.estado='1'");
*/
q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");

//contar
Propiedades propiedades = Propiedades.getInstance();


int conteo=0;
if (bean1.getFlagPagineo()==false)
{
	if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
	stmtc   = conn.createStatement();
	rsetc   = stmtc.executeQuery(qca.toString());
	boolean bc = rsetc.next();
	conteo = rsetc.getInt(1);

	if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
		throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
	if (conteo==0)
		throw new ValidacionException("No se encontraron resultados para su búsqueda");
}
if (isTrace(this)) System.out.println("___verquery___"+q.toString());
//UsoServicio_________________________________
			/**
			 * inicio, dbravo: 15/06/2007
			 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
			if (bean1.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				/
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			/**
			  * fin, dbravo: 15/06/2007
			  */


//descripcion area registral
DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
String descripcionAreaRegistral="";
if (dboTmAreaRegistral.find() == true)
	descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(propiedades.getLineasPorPag());
rset   = stmt.executeQuery(q.toString());
if (bean1.getSalto()>1)
	rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));

boolean b = rset.next();
ArrayList resultado = new ArrayList();

DboFicha dboFicha = new DboFicha(dconn);
DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
DboTmLibro dboTmLibro = new DboTmLibro(dconn);
DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
DboParticLibro dboParticLibro = new DboParticLibro(dconn);


StringBuffer sb = new StringBuffer();


int conta=0;
boolean haySiguiente = false;
while (b==true)
{
	PartidaBean partidaBean = new PartidaBean();

	//_completar los detalles de la partida encontrada
	String refNumPart = rset.getString("partida_refnum_part");
	partidaBean.setRefNumPart(refNumPart);
	partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
	partidaBean.setNumPartida(rset.getString("partida_num_partida"));
	partidaBean.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
	partidaBean.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
	// 2003-07-31 enviar el estado al JSP
	partidaBean.setEstado(rset.getString("estado"));

	//ficha
	dboFicha.clearAll();
	sb.delete(0, sb.length());
	sb.append(dboFicha.CAMPO_FICHA).append("|");
	sb.append(dboFicha.CAMPO_FICHA_BIS);
	dboFicha.setFieldsToRetrieve(sb.toString());
	dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
	if (dboFicha.find() == true)
			{
				partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
				String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
				int nbis = 0;
				try {
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException n)
				{
					nbis =0;
				}
				if (nbis>=1)
					partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
			}

	//obtener tomo y foja
	dboTomoFolio.clearAll();
	sb.delete(0, sb.length());
	sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
	sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
	sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
	sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
	dboTomoFolio.setFieldsToRetrieve(sb.toString());
	dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
	if (dboTomoFolio.find() == true)
			{
				partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


				if (bist.trim().length() > 0)
						partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);


				if (bisf.trim().length() > 0)
						partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
				//28dic2002
				//quitar el caracter "9" del inicio del tomoid
					if (partidaBean.getTomoId().length()>0)
					{
						if (partidaBean.getTomoId().startsWith("9"))
							{
								String ntomo = partidaBean.getTomoId().substring(1);
								partidaBean.setTomoId(ntomo);
							}
					}
			}

	partidaBean.setAeronaveMatricula(rset.getString("reg_aeronaves_num_matricula"));
	partidaBean.setAeronaveTipoTitular("PN");

	//propietario de la aeronave
	sb.delete(0, sb.length());
	String prtc_nat_ape_pat = rset.getString("prtc_nat_ape_pat");
	String prtc_nat_ape_mat = rset.getString("prtc_nat_ape_mat");
	String prtc_nat_nombres = rset.getString("prtc_nat_nombres");
	sb.append(prtc_nat_ape_pat);
	sb.append(" ");
	sb.append(prtc_nat_ape_mat);
	sb.append(", ");
	sb.append(prtc_nat_nombres);
	partidaBean.setAeronaveTipoTitular(sb.toString());

	resultado.add(partidaBean);
		conta++;
	b = rset.next();
		if (conta==propiedades.getLineasPorPag())
		{
			if(b==true)
				haySiguiente = true;

			break;
		}
}//while (b==true)

if (resultado.size()==0)
	throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

FormOutputBuscarPartida output = new FormOutputBuscarPartida();
output.setResultado(resultado);
if (bean1.getFlagPagineo()==false)
	output.setCantidadRegistros(String.valueOf(conteo));
else
	output.setCantidadRegistros(bean1.getCantidad());


//calcular numero para boton "retroceder pagina"
if (bean1.getSalto()==1)
	output.setPagAnterior(-1);
else
	output.setPagAnterior(bean1.getSalto()-1);

//calcular numero para boton "avanzar pagina"
if (haySiguiente==false)
	output.setPagSiguiente(-1);
else
	output.setPagSiguiente(bean1.getSalto()+1);

//calcular regs del x al y
int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
int al  = del+resultado.size()-1;
output.setNdel(del);
output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);



			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarAeronaveXNombre");
			req.setAttribute("output", output);


			// Inicio:mgarate:31/05/2007
			   req.setAttribute("criterioBusqueda",criterioBusqueda);
			   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
			// Fin:mgarate:31/05/2007
			
		    /*inicio:dbravo:14/09/2007*/
		    req.setAttribute("flagVerifica", bean1.getVerifica());
		    /*fin:dbravo:14/09/2007*/   
			   
			response.setStyle("resultadoAeronave");


			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBuscaPartidaRegAereoBean bt = new LogAuditoriaBuscaPartidaRegAereoBean();

				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				bt.setTipoBusqPartida(Constantes.REG_AEREO);
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart(bean1.getArea3AeronaveApePat()
							+ " "
							+ bean1.getArea3AeronaveApeMat()
							+ " "
							+ bean1.getArea3AeronaveNombre()
							);
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
				bt.setTipoPersPart("N");
				//_
				bt.setTipoParam("T");
				bt.setValor(bean1.getArea3AeronaveApePat()
							+ " "
							+ bean1.getArea3AeronaveApeMat()
							+ " "
							+ bean1.getArea3AeronaveNombre()
							);
				bt.setTipoTitular("N");



					/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
					*/
			  if (Propiedades.getInstance().getFlagTransaccion()==true){

				/**
				  *  inicio, dbravo: 15/06/2007
				  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				  *  			   inicialmente el Job002.
				  */
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if (bean1.getFlagPagineo()==false)
				{
					/*
					validar que el usuario NO sea de zona WEB
					*/
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						//estamos en la primera llamada
						//enviamos TODOS los registros encontrados
						//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
							Thread llamador1 = new Thread(j);
							llamador1.start();
					}
				}
				/**
				  * fin, dbravo: 15/06/2007
				  */
				
			  }
			}//if flagpagine


			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	} // fin metodo buscar aeronaveXNombre


	//**************************************************************************
	//**************************************************************************
	//**************************************************************************


	public ControllerResponse runBuscarAeronaveXRazonSocialState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {


		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			//Inicio:mgarate:31/05/2007
			String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=14";
			//Fin:mgarate:31/05/2007
			
			InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, Constantes.BUSQUEDA_INDIRECTA_AERONAVEXRAZONSOCIAL);


			if (bean1.getArea3AeronaveRazon().trim().length()==0)
				throw new ValidacionException("Campo Razon Social es requerido para la busqueda","");


//-empieza busqueda
StringBuffer q  = new StringBuffer();
StringBuffer q2 = new StringBuffer();
StringBuffer qca = new StringBuffer();


q.append(" SELECT ");
q2.append(" SELECT ");
qca.append(" SELECT ");

	// 2003-07-31 enviar el estado al JSP
	q.append(" PARTIDA.ESTADO as estado, ");
q.append(" PARTIDA.REFNUM_PART as partida_refnum_part,");
q.append(" PARTIDA.NUM_PARTIDA as partida_num_partida,");
q.append(" REGIS_PUBLICO.SIGLAS as regis_publico_siglas,");
q.append(" OFIC_REGISTRAL.NOMBRE as ofic_registral_nombre,");
q.append(" REG_AERONAVES.NUM_MATRICULA as reg_aeronaves_num_matricula,");
q.append(" PRTC_JUR.RAZON_SOCIAL as prtc_jur_razon_social");


q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
qca.append(" count(partida.refnum_part) ");


q.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PRTC_JUR,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
q.append(" WHERE REG_AERONAVES.ESTADO = '1' ");
q.append(" AND IND_PRTC.COD_PARTIC = '003' ");
q.append(" AND IND_PRTC.TIPO_PERS = 'J' ");
q.append(" AND PARTIDA.COD_LIBRO = '040' ");
q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q.append(" AND PRTC_JUR.CUR_PRTC = IND_PRTC.CUR_PRTC ");
q.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
q.append(" AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");


q2.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PRTC_JUR,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
q2.append(" WHERE REG_AERONAVES.ESTADO = '1' ");
q2.append(" AND IND_PRTC.COD_PARTIC = '003' ");
q2.append(" AND IND_PRTC.TIPO_PERS = 'J' ");
q2.append(" AND PARTIDA.COD_LIBRO = '040' ");
q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
q2.append(" AND PRTC_JUR.CUR_PRTC = IND_PRTC.CUR_PRTC ");
q2.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
q2.append(" AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");


qca.append(" FROM REGIS_PUBLICO,REG_AERONAVES,OFIC_REGISTRAL,IND_PRTC,PRTC_JUR,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad  ");
qca.append(" WHERE REG_AERONAVES.ESTADO = '1' ");
qca.append(" AND IND_PRTC.COD_PARTIC = '003' ");
qca.append(" AND IND_PRTC.TIPO_PERS = 'J' ");
qca.append(" AND PARTIDA.COD_LIBRO = '040' ");
qca.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
qca.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
qca.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
qca.append(" AND PRTC_JUR.CUR_PRTC = IND_PRTC.CUR_PRTC ");
qca.append(" AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
qca.append(" AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");


q.append(" and PRTC_JUR.RAZON_SOCIAL LIKE '").append(bean1.getArea3AeronaveRazon().trim()).append("%'");
q2.append(" and PRTC_JUR.RAZON_SOCIAL LIKE '").append(bean1.getArea3AeronaveRazon().trim()).append("%'");
qca.append(" and PRTC_JUR.RAZON_SOCIAL LIKE '").append(bean1.getArea3AeronaveRazon().trim()).append("%'");

if (bean1.getArea3SiglasB().trim().length()>0)
	{
		q.append(" and PRTC_JUR.SIGLAS like '").append(bean1.getArea3SiglasB().trim()).append("%'");
		q2.append(" and PRTC_JUR.SIGLAS like '").append(bean1.getArea3SiglasB().trim()).append("%'");
		qca.append(" and PRTC_JUR.SIGLAS like '").append(bean1.getArea3SiglasB().trim()).append("%'");
	}

StringBuffer sbregpub = new StringBuffer();
if (bean1.getSedesElegidas().length==1)
	{
		sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
		sbregpub.append(" and PRTC_JUR.REG_PUB_ID = '").append(bean1.getSedesElegidas()[0]).append("'");
	}
if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
	{
		sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
		sbregpub.append(" and PRTC_JUR.REG_PUB_ID IN ").append(bean1.getSedesSQLString());
	}
q.append(sbregpub.toString());
q2.append(sbregpub.toString());
qca.append(sbregpub.toString());

//q.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q.append(" and partida.cod_libro = glad.cod_libro  ");
q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
q2.append(" and partida.cod_libro = glad.cod_libro  ");
q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
q2.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");
//qca.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
qca.append(" and partida.cod_libro = glad.cod_libro  ");
qca.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
qca.append(" and gla.cod_grupo_libro_area ='").append(bean1.getCodGrupoLibroArea()).append("' ");

appendCondicionEstadoPartida(q);
appendCondicionEstadoPartida(q2);
appendCondicionEstadoPartida(qca);
/*
q.append(" and partida.estado='1'");
q2.append(" and partida.estado='1'");
qca.append(" and partida.estado='1'");
*/
q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");


//contar
Propiedades propiedades = Propiedades.getInstance();


int conteo=0;
if (bean1.getFlagPagineo()==false)
{
	if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
	stmtc   = conn.createStatement();
	rsetc   = stmtc.executeQuery(qca.toString());
	boolean bc = rsetc.next();
	conteo = rsetc.getInt(1);

	if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
		throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
	if (conteo==0)
		throw new ValidacionException("No se encontraron resultados para su búsqueda");
}
if (isTrace(this)) System.out.println("___verquery___"+q.toString());
//UsoServicio_________________________________
			/**
			 * inicio, dbravo: 15/06/2007
			 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
			if (bean1.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				/
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			/**
			  * fin, dbravo: 15/06/2007
			  */



//descripcion area registral
DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
String descripcionAreaRegistral="";
if (dboTmAreaRegistral.find() == true)
	descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(propiedades.getLineasPorPag());
rset   = stmt.executeQuery(q.toString());
if (bean1.getSalto()>1)
	rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));

boolean b = rset.next();
ArrayList resultado = new ArrayList();
DboFicha dboFicha = new DboFicha(dconn);
//DboTomoFolio dboTomoFolio = new DboTomoFolio(conn);
DboTmLibro dboTmLibro = new DboTmLibro(dconn);
DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
DboParticLibro dboParticLibro = new DboParticLibro(dconn);


StringBuffer sb = new StringBuffer();


int conta=0;
boolean haySiguiente = false;
while (b==true)
{
	PartidaBean partidaBean = new PartidaBean();

	//_completar los detalles de la partida encontrada
	String refNumPart = rset.getString("partida_refnum_part");
	partidaBean.setRefNumPart(refNumPart);
	partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
	partidaBean.setNumPartida(rset.getString("partida_num_partida"));
	partidaBean.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
	partidaBean.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
	// 2003-07-31 enviar el estado al JSP
	partidaBean.setEstado(rset.getString("estado"));

	partidaBean.setAeronaveMatricula(rset.getString("reg_aeronaves_num_matricula"));
	partidaBean.setAeronaveTipoTitular("PJ");
	partidaBean.setAeronaveTitular(rset.getString("prtc_jur_razon_social"));

	resultado.add(partidaBean);
	conta++;
	b = rset.next();
			if (conta==propiedades.getLineasPorPag())
		{
			if(b==true)
				haySiguiente = true;

			break;
		}
}//while (b==true)

if (resultado.size()==0)
	throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

FormOutputBuscarPartida output = new FormOutputBuscarPartida();
output.setResultado(resultado);
if (bean1.getFlagPagineo()==false)
	output.setCantidadRegistros(String.valueOf(conteo));
else
	output.setCantidadRegistros(bean1.getCantidad());


//calcular numero para boton "retroceder pagina"
if (bean1.getSalto()==1)
	output.setPagAnterior(-1);
else
	output.setPagAnterior(bean1.getSalto()-1);

//calcular numero para boton "avanzar pagina"
if (haySiguiente==false)
	output.setPagSiguiente(-1);
else
	output.setPagSiguiente(bean1.getSalto()+1);


//calcular regs del x al y
int del = ((bean1.getSalto()-1)*propiedades.getLineasPorPag())+1;
int al  = del+resultado.size()-1;
output.setNdel(del);
output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);


			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarRazonSocial");
			req.setAttribute("output", output);

			// Inicio:mgarate:31/05/2007
			   req.setAttribute("criterioBusqueda",criterioBusqueda);
			   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
			// Fin:mgarate:31/05/2007
			
		    /*inicio:dbravo:14/09/2007*/
		    req.setAttribute("flagVerifica", bean1.getVerifica());
		    /*fin:dbravo:14/09/2007*/   
			   
			response.setStyle("resultadoAeronave");

			if (bean1.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaBuscaPartidaRegAereoBean bt = new LogAuditoriaBuscaPartidaRegAereoBean();

				//b generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
				bt.setTipoBusqPartida(Constantes.REG_AEREO);
				//datos particulares de esta transaccion
				bt.setPartSeleccionado("PN");
				bt.setNumSedes(bean1.getSedesElegidas());
				bt.setNomApeRazSocPart(bean1.getArea3AeronaveRazon());
				bt.setCodAreaReg(bean1.getComboArea());
				bt.setTipoParticipacion(bean1.getArea3TipoParticipacion());
				bt.setTipoPersPart("J");
				//_
				bt.setTipoParam("T");
				bt.setValor(bean1.getArea3AeronaveRazon());
				bt.setTipoTitular("J");
				/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();
				*/
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
				/**
				  *  inicio, dbravo: 15/06/2007
				  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				  *  			   inicialmente el Job002.
				  */
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if (bean1.getFlagPagineo()==false)
				{
					/*
					validar que el usuario NO sea de zona WEB
					*/
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						//estamos en la primera llamada
						//enviamos TODOS los registros encontrados
						//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
							Thread llamador1 = new Thread(j);
							llamador1.start();
					}
				}
				/**
				  * fin, dbravo: 15/06/2007
				  */
				
			  }
			}//if flagpagi

			conn.commit();
			if (usuario.getFgInterno()==false)
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}
		} //try
		catch (ValidacionException e) {
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontró la partida buscada");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);

			pool.release(conn);
			end(request);
		}
		return response;
	} // fin metodo buscar Aernoave x razon social

public ControllerResponse runBuscarXNombreVehicularState(ControllerRequest request,ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

	try
		{
		init(request);
		validarSesion(request);

		conn = pool.getConnection();
		conn.setAutoCommit(false);
		DBConnection dconn = new DBConnection(conn);

		ArrayList resultado = new ArrayList();
		Propiedades propiedades = Propiedades.getInstance();
		int conteo=0;
		boolean haySiguiente = false;

		// Inicio:mgarate:30/05/2007
		   String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=5";;
		// Fin:mgarate:30/05/2007
		
		InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_PERSONA_NATURAL);

		String apellidoPaterno = "";
		String apellidoMaterno = "";
		String nombre = "";
		String tipoParticipacion = "";
		String tipoBusqVehi = "";
		String razonSoci = "";

		StringBuffer sb = new StringBuffer();


		if (bean1.getHid2().equals("4"))
		{
				apellidoPaterno = bean1.getArea4ApePat().trim();
				apellidoMaterno = bean1.getArea4ApeMat().trim();
				nombre = bean1.getArea4Nombre().trim();
				razonSoci = bean1.getArea4Razon().trim();
				tipoParticipacion = bean1.getArea4TipoParticipacion();
				tipoBusqVehi = bean1.getArea4Tipo();
		}

	StringBuffer q  = null;
	StringBuffer q2 = null;
	StringBuffer qca = null;
	StringBuffer qcb = null;
	
	if(req.getParameter("salto")==null)
	{
				//_empieza busqueda
				q  = new StringBuffer();
				q2 = new StringBuffer();
				qca = new StringBuffer();
				qcb = new StringBuffer();

				if (apellidoMaterno.length()>0)
				{
					q.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) INDEX (VEHICULO INDEX_VEHICULO_REFNUM_PART)*/ ");
					q2.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) INDEX (VEHICULO INDEX_VEHICULO_REFNUM_PART)*/ ");
					qca.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					qcb.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATMAT) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
				}
				else
				{
					q.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) INDEX (VEHICULO INDEX_VEHICULO_REFNUM_PART)*/ ");
					q2.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) INDEX (VEHICULO INDEX_VEHICULO_REFNUM_PART)*/ ");
					qca.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
					qcb.append(" SELECT  /*+ORDERED INDEX(PRTC_NAT INDEX_PRTC_NAT_APEPATNOM) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C005644) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) */ ");
				}

				q.append(" partida.refnum_part,  partida.cod_libro,     partida.num_partida,");
				/**kuma 2003/09/02 **/
				q.append(" partida.reg_pub_id,  partida.ofic_reg_id, ");
				/**fin kuma**/
				//hphp
				q.append(" partida.estado, ");

				q.append(" regis_publico.siglas, ofic_registral.nombre, prtc_nat.ape_pat,");
				q.append(" prtc_nat.ape_mat,     prtc_nat.nombres,      prtc_nat.nu_doc_iden,");
				q.append(" prtc_nat.ti_doc_iden, ind_prtc.cod_partic,   ind_prtc.estado as estadoInd,");
				q.append(" vehiculo.num_placa, vehiculo.fg_baja");
				q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
				qca.append(" count(prtc_nat.cur_prtc) ");
				qcb.append(" count(partida.refnum_part) ");

				q.append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico ,VEHICULO");
				q.append(" WHERE ape_pat like '").append(apellidoPaterno).append("%'");

				q2.append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico,VEHICULO ");
			q2.append(" WHERE 0 = 0 and ape_pat like '").append(apellidoPaterno).append("%'");

				qca.append(" FROM prtc_nat");
				qca.append(" WHERE ");
				qca.append(" ape_pat like '").append(apellidoPaterno).append("%'");

				qcb.append(" FROM prtc_nat, ind_prtc, partida,VEHICULO ");
				qcb.append(" WHERE ape_pat like '").append(apellidoPaterno).append("%'");


			if (apellidoMaterno.length()>0)
				{
					q.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
					q2.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
					qca.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
					qcb.append(" and ape_mat like '").append(apellidoMaterno).append("%'");
				}
			if (nombre.length()>0)
				{
					q.append(" and nombres like '").append(nombre).append("%'");
					q2.append(" and nombres like '").append(nombre).append("%'");
					qca.append(" and nombres like '").append(nombre).append("%'");
					qcb.append(" and nombres like '").append(nombre).append("%'");
				}

				q.append(" AND ind_prtc.cur_prtc = prtc_nat.cur_prtc ");
				q2.append(" AND ind_prtc.cur_prtc = prtc_nat.cur_prtc ");
				qcb.append(" AND ind_prtc.cur_prtc = prtc_nat.cur_prtc ");


				q.append(" AND ind_prtc.tipo_pers = 'N' ");
				q2.append(" AND ind_prtc.tipo_pers = 'N' ");
				qcb.append(" AND ind_prtc.tipo_pers = 'N' ");

			if(bean1.getFlagIncluirInactivos()==false)
			{
				q.append(" AND IND_PRTC.ESTADO = '1'");
				q2.append(" AND IND_PRTC.ESTADO = '1'");
				qcb.append(" AND IND_PRTC.ESTADO = '1'");
			}


				q.append(" AND partida.refnum_part = ind_prtc.refnum_part ");
				appendCondicionEstadoPartida(q);
				q2.append(" AND partida.refnum_part = ind_prtc.refnum_part ");
				appendCondicionEstadoPartida(q2);
				qcb.append(" AND partida.refnum_part = ind_prtc.refnum_part ");
				appendCondicionEstadoPartida(qcb);

			StringBuffer sbregpub = new StringBuffer();
			if (bean1.getSedesElegidas().length==1)
				{
					sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
					qca.append(" AND prtc_nat.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
				}

			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
				{
					sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
					qca.append(" AND prtc_nat.reg_pub_id IN ").append(bean1.getSedesSQLString());
				}
			q.append(sbregpub.toString());
			q2.append(sbregpub.toString());
			qcb.append(sbregpub.toString());

			q.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			q2.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			qcb.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");


			q.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
			q2.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
			qcb.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");

			q.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
			q2.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
			qcb.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");

			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");

			q.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");
			q2.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");

			q.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");
			q2.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");

			q.append(" AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");
			q2.append(" AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");
			qcb.append(" AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");

			q.append(" AND ind_prtc.cod_partic = " + Constantes.PROPIETARIO_VEHI);
			q2.append(" AND ind_prtc.cod_partic = " + Constantes.PROPIETARIO_VEHI);
			qcb.append(" AND ind_prtc.cod_partic = " + Constantes.PROPIETARIO_VEHI);

			//**añadido por Leo**//
			if(usuario.getFgInterno()==false)
			{
				 q.append(" AND VEHICULO.FG_BAJA = '0' ");
				 q2.append(" AND VEHICULO.FG_BAJA = '0' ");
	 			qcb.append(" AND VEHICULO.FG_BAJA = '0' ");
			}
			if(usuario.getFgInterno()==true && bean1.getFlagIncluirInactivos() == false)
			{
				 q.append(" AND  VEHICULO.FG_BAJA = '0' ");
				 q2.append(" AND  VEHICULO.FG_BAJA = '0' ");
	 			qcb.append(" AND VEHICULO.FG_BAJA = '0' ");
			}
			//**//
			q.append(" ORDER BY PARTIDA.REG_PUB_ID, partida.ofic_reg_id, partida.num_partida, ind_prtc.estado DESC, prtc_nat.ape_pat, prtc_nat.ape_mat, prtc_nat.nombres");


			//contar
			if (bean1.getFlagPagineo()==false)
			{


				if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qca.toString());
				boolean bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");


				if (isTrace(this)) System.out.println("___verqueryCOUNT_B__"+qcb.toString());
				rsetc   = stmtc.executeQuery(qcb.toString());
				bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo > propiedades.getMaxResultadosBusqueda() )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");



			}

			if (isTrace(this)) System.out.println("___verquery___"+q.toString());
			//UsoServicio_________________________________
			/**
			 * inicio, dbravo: 15/06/2007
			 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
						if (bean1.getFlagPagineo()==false)
						{
							/*
							validar que el usuario NO sea de zona WEB
							/
							if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
							{
								//estamos en la primera llamada
								//enviamos TODOS los registros encontrados
								//a otro Thread para que registre el UsoServicio
									Job002 j = new Job002();
									j.setQuery(q2.toString());
									j.setUsuario(usuario);
									j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
									Thread llamador1 = new Thread(j);
									llamador1.start();
							}
						}
			/**
			  * fin, dbravo: 15/06/2007
			  */

			//descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

			//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
		/** Importante! Para pagineo.
			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
		**/

			boolean b = rset.next();


			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);

			int conta=0;

			boolean nuevo = true;

			String antRefNumPart = "";
			String antRegPubDescripcion = "";
			String antnombre = "";
			String antEstadoAct = "";

			StringBuffer cadenaPart = new StringBuffer();

			PartidaBean partidaBean = new PartidaBean();

			while (b==true)
			{

				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("refnum_part");
				String oficRegDesc = rset.getString("nombre");
				String codLibro   = rset.getString("cod_libro");
				String estadoAct = "";
				if (rset.getString("estadoInd").startsWith("1"))
				{
					estadoAct = "Activo";
				}
				else
				{
					estadoAct = "Inactivo";
				}

				sb.delete(0,sb.length());
				String ape_mat = rset.getString("ape_mat").trim();
				if (ape_mat==null)
					ape_mat="";
				String nombres = rset.getString("nombres").trim();
				if (nombres==null)
					nombres="";
				sb.append(rset.getString("ape_pat").trim()).append(" ");
				sb.append(ape_mat).append(", ");
				sb.append(nombres);
				String nombreAct = sb.toString();

				if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)))
				{
					nuevo = false;
					if(!antnombre.equals(nombreAct))
					{
						cadenaPart.append(" ; ").append(nombreAct);
						antnombre = nombreAct;
					}

				}
				else
				{

					if(!antRefNumPart.equals(""))
					{
						partidaBean.setParticipanteDescripcion(cadenaPart.toString());
						resultado.add(partidaBean);
						conta++;
						partidaBean = new PartidaBean();

					}

					antRefNumPart = refNumPart;
					antRegPubDescripcion = oficRegDesc;
					antnombre = nombreAct;
					antEstadoAct = estadoAct;

					cadenaPart.delete(0,cadenaPart.length());
					cadenaPart.append(nombreAct);
					nuevo = true;
				}

				if(nuevo)
				{

					partidaBean.setRefNumPart(refNumPart);
					partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setRegPubDescripcion(rset.getString("siglas"));
					partidaBean.setOficRegDescripcion(oficRegDesc);
					partidaBean.setEstadoInd(estadoAct);
					//hphp
					partidaBean.setEstado(rset.getString("estado"));

					/**kuma 2003/09/02**/
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					/**fin kuma**/

					//*vehicular*//añadido por Leo

					partidaBean.setNumeroPlaca(rset.getString("num_placa"));

					String Baja = rset.getString("fg_baja");
					if(Baja.equals("0"))
						partidaBean.setBaja("En circulación");
					else
						partidaBean.setBaja("Fuera de circulación");

					//**//


					//ficha
					dboFicha.clearAll();
					sb.delete(0, sb.length());
					sb.append(dboFicha.CAMPO_FICHA).append("|");
					sb.append(dboFicha.CAMPO_FICHA_BIS);
					dboFicha.setFieldsToRetrieve(sb.toString());
					dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
					if (dboFicha.find() == true)
							{
								partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
								String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
								int nbis = 0;
								try {
									nbis = Integer.parseInt(bis);
								}
								catch (NumberFormatException n)
								{
									nbis =0;
								}
								if (nbis>=1)
									partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
							}

					//obtener tomo y foja
					dboTomoFolio.clearAll();
					sb.delete(0, sb.length());
					sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
					sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
					sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
					sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
					dboTomoFolio.setFieldsToRetrieve(sb.toString());
					dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
					if (dboTomoFolio.find() == true)
							{
								partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
								partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


								String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
								String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


								if (bist.trim().length() > 0)
										partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);


								if (bisf.trim().length() > 0)
										partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

								//28dic2002
								//quitar el caracter "9" del inicio del tomoid
									if (partidaBean.getTomoId().length()>0)
									{
										if (partidaBean.getTomoId().startsWith("9"))
											{
												String ntomo = partidaBean.getTomoId().substring(1);
												partidaBean.setTomoId(ntomo);
											}
									}
							}


					//descripcion libro
					dboTmLibro.clearAll();
					dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
					dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
					if (dboTmLibro.find() == true)
						partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));


					//participante y su número de documento
					String tipoDocumento="";
					String codPartic="";

					String nuDocIden = rset.getString("nu_doc_iden");
					if (nuDocIden==null || nuDocIden.trim().length()==0)
						partidaBean.setParticipanteNumeroDocumento("&nbsp;");
					else
						partidaBean.setParticipanteNumeroDocumento(nuDocIden);


					tipoDocumento = rset.getString("ti_doc_iden");
					codPartic     = rset.getString("cod_partic");


					//descripción de documento
					if (tipoDocumento!=null)
					{
						if (tipoDocumento.trim().length()>0)
						{
							dboTmDocIden.clearAll();
							dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
							dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
							dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO, "1");
							if (dboTmDocIden.find() == true)
								partidaBean.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
						}
					}


					//descripción del tipo de participación
					dboParticLibro.clearAll();
					dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
					dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
						if (dboParticLibro.find()==true)
							partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));

				}


				b = rset.next();
			/**Importante para pagineo
				if (conta==propiedades.getLineasPorPag())
				{
					if(b==true)
						haySiguiente = true;

					break;
				}
			**/

			}//while (b==true)

			partidaBean.setParticipanteDescripcion(cadenaPart.toString());
			resultado.add(partidaBean);
			conta++;

			if (resultado.size()==0)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

			//sesion.setAttribute("resultado",resultado);

			/** En caso que no exista, lo inserto, para que elimine temporales al finalizar sesion**/
			if(sesion.getAttribute("monitor")==null)
			{
				MonitorDeSesion monitor = new MonitorDeSesion();
				sesion.setAttribute("monitor",monitor);
			}

			/** Borro temporal ya que esta puede no ser primera consulta **/
			sb.delete(0,sb.length());
			sb.append("DELETE FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			stmt.executeUpdate(sb.toString());

			/** Inserto blob vacio **/
			sb.delete(0,sb.length());
			sb.append("INSERT INTO DATA_SESSION(SESSION_ID, NOMBRE, TS_CREACION, VALOR) VALUES('").append(sesion.getId()).append("','").append("BusqIndirectaPersNat").append("', SYSDATE, empty_blob())");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			/** Selecciono el blob como referencia, para actualizacion **/
			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("' FOR UPDATE");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			oracle.sql.BLOB campoBlob = null;

			if(rset.next())
			{
				campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


			/** Referencio el blob vacio hacia el outputStream, y luego calculo el tamaño del buffer que me servira para llenar el blob **/
			//java.io.ObjectInputStream ioStr = null;
			java.io.OutputStream outstream = campoBlob.getBinaryOutputStream();
			int j = -1;
			int bSize = campoBlob.getBufferSize(); // buffer del campo blob

			byte buffer[] = new byte[bSize];

			/** El Arraylist lo serializo, para introducirlo en el inputStream **/
			java.io.InputStream instream = gob.pe.sunarp.extranet.common.Serializacion.getInstance().serializarAStream(resultado);

			/** El inputStream lo introduzco de a pocos en el outputStream, que tiene la referencia hacia el blob, asi que lo llena. **/
			j = instream.read(buffer);
			while (j != -1) {
				outstream.write(buffer, 0, j);
				j = instream.read(buffer);
			}

			/** Cierro los streams **/
			outstream.close();
			instream.close();


	}///if(salto)
	else
	{

			/** Caso contrario, consulta ya fue hecha, debo recuperar de tabla temporal **/

			//resultado = (java.util.ArrayList)sesion.getAttribute("resultado");

			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());
			if(rset.next())
			{
				/** Recupero el Blob, serializado **/
				oracle.sql.BLOB campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

				/** Para poder usarlo, debo deserializarlo **/
				resultado = (ArrayList) gob.pe.sunarp.extranet.common.Serializacion.getInstance().deserializar(campoBlob.binaryStreamValue());
			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


	}

		java.util.ArrayList resultado2 = new java.util.ArrayList();
		int pagina = 0;


		if(req.getParameter("salto")!=null)
			pagina = Integer.parseInt((String)req.getParameter("salto"));

		int i = pagina * propiedades.getLineasPorPag();
		conteo = resultado.size();
		while ( (i < ((pagina+1) * propiedades.getLineasPorPag())) && (i<conteo))
		{
			resultado2.add(resultado.get(i));
			i++;
		}
		if(i<conteo)
			haySiguiente = true;

		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		if (bean1.getFlagIncluirInactivos()==true)
			output.setFlagEstado(true);
		output.setResultado(resultado2);
		if (bean1.getFlagPagineo()==false)
			output.setCantidadRegistros(String.valueOf(conteo));
		else
			output.setCantidadRegistros(bean1.getCantidad());


		//calcular numero para boton "retroceder pagina"
		/*if (bean1.getSalto()==1)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(bean1.getSalto()-1);
		*/
		if (pagina==0)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(pagina-1);

		//calcular numero para boton "avanzar pagina"
		/*if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(bean1.getSalto()+1);
		*/
		if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(pagina+1);

		//calcular regs del x al y
		int del = (pagina*propiedades.getLineasPorPag())+1;
		int al  = del+resultado2.size()-1;
		output.setNdel(del);
		output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);
		//////

		/**01/10/2003 Se corrigio problema de campos diferentes**/
		//output.setAction("/iri/BuscaPartidasXIndices.do?state=buscarPersonaNatural");
		output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicular");
		req.setAttribute("output", output);
		
		// Inicio:mgarate:30/05/2007
		   req.setAttribute("criterioBusqueda",criterioBusqueda);
		   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
		// Fin:mgarate:30/05/2007
		   
	    /*inicio:dbravo:14/09/2007*/
	    req.setAttribute("flagVerifica", bean1.getVerifica());
	    /*fin:dbravo:14/09/2007*/
	   
		response.setStyle("listaNombre");////resultadoPersonaNatural

		if (bean1.getFlagPagineo()==false)
		{
			//llamar a "Transaccion"
			LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();

			//Datos generales
			 //Modificado por: Proyecto Filtros de Acceso
			 //Fecha: 02/10/2006
			 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
			 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
			 //Fecha: 08/10/2006             
			 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
			 //Fin Modificación
			bt.setUsuarioSession(usuario);
			bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
			//Tarifario
			bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));

			//datos particulares de esta transaccion
			bt.setPartSeleccionado("PN");
			bt.setNumSedes(bean1.getSedesElegidas());
			bt.setNomApeRazSocPart(apellidoPaterno + " " + apellidoMaterno + " " + nombre);
			bt.setCodAreaReg(bean1.getComboArea());
			bt.setTipoParticipacion(tipoParticipacion);
			bt.setTipoPersPart("N");
			/*
				Job004 j = new Job004();
				j.setBean(bt);
				Thread llamador1 = new Thread(j);
				llamador1.start();
			*/
			if (Propiedades.getInstance().getFlagTransaccion()==true){
				/**
				  *  inicio, dbravo: 15/06/2007
				  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				  *  			   inicialmente el Job002.
				  */
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if(q2!=null){
					if (bean1.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
								Job004 j = new Job004();
								j.setQuery(q2.toString());
								j.setUsuario(usuario);
								j.setCostoServicio(prepagoBean.getMontoBruto());
								j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
								Thread llamador1 = new Thread(j);
								llamador1.start();
						}
					}
				}
				/**
				  * fin, dbravo: 15/06/2007
				  */
			}
		}//if flagPagine

		conn.commit();
		if (usuario.getFgInterno()==false)
		{
			LineaPrepago lineaCmd = new LineaPrepago();
			double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
			usuario.setSaldo(nuevoSaldo);
		}

	 }

		catch (ValidacionException e)
		{
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}

		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try
					{
						rollback(conn, request);
					}
					catch (Throwable ex)
					{
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
					}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		}
		catch (DBException dbe)
		{
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}

		catch (Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally
		{
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeResultSet(rsetc);
				JDBC.getInstance().closeStatement(stmt);
				JDBC.getInstance().closeStatement(stmtc);
				pool.release(conn);
				end(request);
		}
		return response;
	}


	/////*** REGISTRO VEHICULAR JURIDICO  ***////



public ControllerResponse runBuscarXNombreVehicularJuriState(ControllerRequest request,ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

	try
		{
		init(request);
		validarSesion(request);

		conn = pool.getConnection();
		conn.setAutoCommit(false);
		DBConnection dconn = new DBConnection(conn);

		ArrayList resultado = new ArrayList();
		Propiedades propiedades = Propiedades.getInstance();
		int conteo=0;
		boolean haySiguiente = false;

		// Inicio:mgarate:31/05/2007
		   String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=6";;
		// Fin:mgarate:31/05/2007
		
		InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_PERSONA_JURIDICA);

		String tipoParticipacion = "";
		String tipoBusqVehi = "";
		String razonSoci = "";

		StringBuffer sb = new StringBuffer();

		if (bean1.getHid2().equals("4"))
		{
				razonSoci = bean1.getArea4Razon().trim();
				tipoParticipacion = bean1.getArea4TipoParticipacion();
				tipoBusqVehi = bean1.getArea4Tipo();
		}
		
	StringBuffer q  = null;
	StringBuffer q2 = null;
	StringBuffer qca = null;
	StringBuffer qcb = null;
	
	if(req.getParameter("salto")==null)
	{

				//_empieza busqueda
				q  = new StringBuffer();
				q2 = new StringBuffer();
				qca = new StringBuffer();
				qcb = new StringBuffer();


				q.append(" SELECT  /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C001421) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) INDEX (VEHICULO INDEX_VEHICULO_REFNUM_PART) */ ");
				q2.append(" SELECT  /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C001421) INDEX (OFIC_REGISTRAL SYS_C005712) INDEX(REGIS_PUBLICO SYS_C005759) INDEX (VEHICULO INDEX_VEHICULO_REFNUM_PART) */ ");
				qca.append(" SELECT  /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C001421)  */ ");
				qcb.append(" SELECT  /*+ORDERED INDEX(PRTC_JUR INDEX_PRTC_JUR_RAZON_SOCIAL) INDEX(IND_PRTC INDEX_IND_PRTC_CUR_PRTC) INDEX (PARTIDA SYS_C001421)  */ ");

				q.append(" partida.refnum_part,  partida.cod_libro,     partida.num_partida,");
				//hphp
				q.append(" partida.estado, partida.reg_pub_id, partida.ofic_reg_id, ");

				q.append(" regis_publico.siglas, ofic_registral.nombre, ");
				q.append(" PRTC_JUR.RAZON_SOCIAL,      PRTC_JUR.NU_DOC, PRTC_JUR.TI_DOC,  ");
				q.append("  ind_prtc.cod_partic,   ind_prtc.estado as estadoInd,");
				q.append(" vehiculo.num_placa, vehiculo.fg_baja");
				q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
				qca.append(" count(PRTC_JUR.cur_prtc) ");
				qcb.append(" count(partida.refnum_part) ");

				q.append(" FROM PRTC_JUR, ind_prtc, partida, ofic_registral, regis_publico ,VEHICULO");
				q.append(" WHERE RAZON_SOCIAL like '").append(razonSoci).append("%'");

				q2.append(" FROM PRTC_JUR, ind_prtc, partida, ofic_registral, regis_publico ");
				q2.append(" WHERE RAZON_SOCIAL like '").append(razonSoci).append("%'");

				qca.append(" FROM PRTC_JUR");
				qca.append(" WHERE ");
				qca.append(" RAZON_SOCIAL like '").append(razonSoci).append("%'");

				qcb.append(" FROM PRTC_JUR, ind_prtc, partida, vehiculo ");
				qcb.append(" WHERE RAZON_SOCIAL like '").append(razonSoci).append("%'");

				q.append(" AND ind_prtc.cur_prtc = PRTC_JUR.cur_prtc ");
				q2.append(" AND ind_prtc.cur_prtc = PRTC_JUR.cur_prtc ");
				qcb.append(" AND ind_prtc.cur_prtc = PRTC_JUR.cur_prtc ");

				q.append(" AND ind_prtc.tipo_pers = 'J' ");
				q2.append(" AND ind_prtc.tipo_pers = 'J' ");
				qcb.append(" AND ind_prtc.tipo_pers = 'J' ");

				q.append(" AND ind_prtc.cod_partic = " + Constantes.PROPIETARIO_VEHI);
				q2.append(" AND ind_prtc.cod_partic = " + Constantes.PROPIETARIO_VEHI);
				qcb.append(" AND ind_prtc.cod_partic = " + Constantes.PROPIETARIO_VEHI);

			if(bean1.getFlagIncluirInactivos()==false)
			{
				q.append(" AND IND_PRTC.ESTADO = '1'");
				q2.append(" AND IND_PRTC.ESTADO = '1'");
				qcb.append(" AND IND_PRTC.ESTADO = '1'");
			}


				q.append(" AND partida.refnum_part = ind_prtc.refnum_part ");
				appendCondicionEstadoPartida(q);
				q2.append(" AND partida.refnum_part = ind_prtc.refnum_part ");
				appendCondicionEstadoPartida(q2);
				qcb.append(" AND partida.refnum_part = ind_prtc.refnum_part ");
				appendCondicionEstadoPartida(qcb);

			StringBuffer sbregpub = new StringBuffer();
			if (bean1.getSedesElegidas().length==1)
				{

					sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
					qca.append(" AND PRTC_JUR.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
				}

			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
				{
					sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
					qca.append(" AND PRTC_JUR.reg_pub_id IN ").append(bean1.getSedesSQLString());
				}
			q.append(sbregpub.toString());
			q2.append(sbregpub.toString());
			qcb.append(sbregpub.toString());


			q.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			q2.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			qcb.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");


			q.append(" AND PARTIDA.REG_PUB_ID  = PRTC_JUR.REG_PUB_ID ");
			q2.append(" AND PARTIDA.REG_PUB_ID  = PRTC_JUR.REG_PUB_ID ");
			qcb.append(" AND PARTIDA.REG_PUB_ID  = PRTC_JUR.REG_PUB_ID ");

			q.append(" AND partida.ofic_reg_id = PRTC_JUR.ofic_reg_id  ");
			q2.append(" AND partida.ofic_reg_id = PRTC_JUR.ofic_reg_id  ");
			qcb.append(" AND partida.ofic_reg_id = PRTC_JUR.ofic_reg_id  ");

			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");

			q.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");
			q2.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");

			q.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");
			q2.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");

			q.append(" AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");
			q2.append(" AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");
			qcb.append(" AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");


			 if(usuario.getFgInterno()==false)
			 {
			 	q.append(" AND VEHICULO.FG_BAJA = '0' ");
    			q2.append(" AND VEHICULO.FG_BAJA = '0' ");
	 			qcb.append(" AND VEHICULO.FG_BAJA = '0' ");


			 }
			 if(usuario.getFgInterno()==true && bean1.getFlagIncluirInactivos() == false)
			 {
				 q.append(" AND  VEHICULO.FG_BAJA = '0' ");
 				 q2.append(" AND VEHICULO.FG_BAJA = '0' ");
      			 qcb.append(" AND VEHICULO.FG_BAJA = '0' ");

			 }

			q.append(" ORDER BY PARTIDA.REG_PUB_ID, partida.ofic_reg_id, partida.num_partida, ind_prtc.estado DESC, PRTC_JUR.RAZON_SOCIAL");


			//contar
			if (bean1.getFlagPagineo()==false)
			{


				if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qca.toString());
				boolean bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");


				if (isTrace(this)) System.out.println("___verqueryCOUNT_B__"+qcb.toString());
				rsetc   = stmtc.executeQuery(qcb.toString());
				bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo > propiedades.getMaxResultadosBusqueda() )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");

			}


			if (isTrace(this)) System.out.println("___verquery___"+q.toString());
			//UsoServicio_________________________________
						/**
						 * inicio, dbravo: 15/06/2007
						 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion

						if (bean1.getFlagPagineo()==false)
						{
							/*
							validar que el usuario NO sea de zona WEB
							/
							if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
							{
								//estamos en la primera llamada
								//enviamos TODOS los registros encontrados
								//a otro Thread para que registre el UsoServicio
									Job002 j = new Job002();
									j.setQuery(q2.toString());
									j.setUsuario(usuario);
									j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
									Thread llamador1 = new Thread(j);
									llamador1.start();
							}
						}
						/*
						 * 
						 */


			//descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

			//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
		/** Importante! Para pagineo.
			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
		**/

			boolean b = rset.next();

			//ficha
			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);

			int conta=0;

			boolean nuevo = true;

			String antRefNumPart = "";
			String antRegPubDescripcion = "";
			String antnombre = "";
			String antEstadoAct = "";

			StringBuffer cadenaPart = new StringBuffer();

			PartidaBean partidaBean = new PartidaBean();

			while (b==true)
			{
				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("refnum_part");
				String oficRegDesc = rset.getString("nombre");
				String codLibro   = rset.getString("cod_libro");
				String estadoAct = "";
				if (rset.getString("estadoInd").startsWith("1"))
				{
					estadoAct = "Activo";
				}
				else
				{
					estadoAct = "Inactivo";
				}

				String nombRazonSoc = rset.getString("RAZON_SOCIAL").trim();/////by leo


				if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)))
				{
					nuevo = false;

					if(!antnombre.equals(nombRazonSoc))
					{
						cadenaPart.append(" ; ").append(nombRazonSoc);
						antnombre = nombRazonSoc;
					}

				}
				else
				{

					if(!antRefNumPart.equals(""))
					{
						partidaBean.setParticipanteDescripcion(cadenaPart.toString());
						resultado.add(partidaBean);
						conta++;
						partidaBean = new PartidaBean();

					}

					antRefNumPart = refNumPart;
					antRegPubDescripcion = oficRegDesc;
					antnombre = nombRazonSoc;
					antEstadoAct = estadoAct;

					cadenaPart.delete(0,cadenaPart.length());
					cadenaPart.append(nombRazonSoc);
					nuevo = true;
				}

				if(nuevo)
				{

					partidaBean.setRefNumPart(refNumPart);
					partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setRegPubDescripcion(rset.getString("siglas"));
					partidaBean.setOficRegDescripcion(oficRegDesc);
					partidaBean.setEstadoInd(estadoAct);
					//hphp
					partidaBean.setEstado(rset.getString("estado"));

					/**kuma 2003/09/02**/
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					/**fin kuma**/
					//*vehicular*//añadido por Leo

					partidaBean.setNumeroPlaca(rset.getString("num_placa"));

					String Baja = rset.getString("fg_baja");
					if(Baja.equals("0"))
						partidaBean.setBaja("En circulación");
					else
						partidaBean.setBaja("Fuera de circulación");

					//**//


					//ficha
					dboFicha.clearAll();
					sb.delete(0, sb.length());
					sb.append(dboFicha.CAMPO_FICHA).append("|");
					sb.append(dboFicha.CAMPO_FICHA_BIS);
					dboFicha.setFieldsToRetrieve(sb.toString());
					dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
					if (dboFicha.find() == true)
							{
								partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
								String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
								int nbis = 0;
								try {
									nbis = Integer.parseInt(bis);
								}
								catch (NumberFormatException n)
								{
									nbis =0;
								}
								if (nbis>=1)
									partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
							}

					//obtener tomo y foja
					dboTomoFolio.clearAll();
					sb.delete(0, sb.length());
					sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
					sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
					sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
					sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
					dboTomoFolio.setFieldsToRetrieve(sb.toString());
					dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
					if (dboTomoFolio.find() == true)
							{
								partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
								partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


								String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
								String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


								if (bist.trim().length() > 0)
										partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);


								if (bisf.trim().length() > 0)
										partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

								//28dic2002
								//quitar el caracter "9" del inicio del tomoid
									if (partidaBean.getTomoId().length()>0)
									{
										if (partidaBean.getTomoId().startsWith("9"))
											{
												String ntomo = partidaBean.getTomoId().substring(1);
												partidaBean.setTomoId(ntomo);
											}
									}
							}


					//descripcion libro
					dboTmLibro.clearAll();
					dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
					dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
					if (dboTmLibro.find() == true)
						partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));



					//participante y su número de documento
					String tipoDocumento="";
					String codPartic="";
					codPartic     = rset.getString("cod_partic");

					//descripción del tipo de participación
					dboParticLibro.clearAll();
					dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
					dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
						if (dboParticLibro.find()==true)
							partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));


				}



				b = rset.next();
			/**Importante para pagineo
				if (conta==propiedades.getLineasPorPag())
				{
					if(b==true)
						haySiguiente = true;

					break;
				}
			**/

			}//while (b==true)

			partidaBean.setParticipanteDescripcion(cadenaPart.toString());
			resultado.add(partidaBean);
			conta++;

			if (resultado.size()==0)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

			//sesion.setAttribute("resultado",resultado);

			/** En caso que no exista, lo inserto, para que elimine temporales al finalizar sesion**/
			if(sesion.getAttribute("monitor")==null)
			{
				MonitorDeSesion monitor = new MonitorDeSesion();
				sesion.setAttribute("monitor",monitor);
			}

			/** Borro temporal ya que esta puede no ser primera consulta **/
			sb.delete(0,sb.length());
			sb.append("DELETE FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			stmt.executeUpdate(sb.toString());

			/** Inserto blob vacio **/
			sb.delete(0,sb.length());
			sb.append("INSERT INTO DATA_SESSION(SESSION_ID, NOMBRE, TS_CREACION, VALOR) VALUES('").append(sesion.getId()).append("','").append("BusqIndirectaPersNat").append("', SYSDATE, empty_blob())");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			/** Selecciono el blob como referencia, para actualizacion **/
			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("' FOR UPDATE");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			oracle.sql.BLOB campoBlob = null;

			if(rset.next())
			{
				campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


			/** Referencio el blob vacio hacia el outputStream, y luego calculo el tamaño del buffer que me servira para llenar el blob **/
			//java.io.ObjectInputStream ioStr = null;
			java.io.OutputStream outstream = campoBlob.getBinaryOutputStream();
			int j = -1;
			int bSize = campoBlob.getBufferSize(); // buffer del campo blob

			byte buffer[] = new byte[bSize];

			/** El Arraylist lo serializo, para introducirlo en el inputStream **/
			java.io.InputStream instream = gob.pe.sunarp.extranet.common.Serializacion.getInstance().serializarAStream(resultado);

			/** El inputStream lo introduzco de a pocos en el outputStream, que tiene la referencia hacia el blob, asi que lo llena. **/
			j = instream.read(buffer);
			while (j != -1) {
				outstream.write(buffer, 0, j);
				j = instream.read(buffer);
			}

			/** Cierro los streams **/
			outstream.close();
			instream.close();


	}///if(salto)
	else
	{

			/** Caso contrario, consulta ya fue hecha, debo recuperar de tabla temporal **/

			//resultado = (java.util.ArrayList)sesion.getAttribute("resultado");

			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());
			if(rset.next())
			{
				/** Recupero el Blob, serializado **/
				oracle.sql.BLOB campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

				/** Para poder usarlo, debo deserializarlo **/
				resultado = (ArrayList) gob.pe.sunarp.extranet.common.Serializacion.getInstance().deserializar(campoBlob.binaryStreamValue());
			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


	}



		java.util.ArrayList resultado2 = new java.util.ArrayList();
		int pagina = 0;


		if(req.getParameter("salto")!=null)
			pagina = Integer.parseInt((String)req.getParameter("salto"));

		int i = pagina * propiedades.getLineasPorPag();
		conteo = resultado.size();
		while ( (i < ((pagina+1) * propiedades.getLineasPorPag())) && (i<conteo))
		{
			resultado2.add(resultado.get(i));
			i++;
		}
		if(i<conteo)
			haySiguiente = true;

		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		if (bean1.getFlagIncluirInactivos()==true)
			output.setFlagEstado(true);
		output.setResultado(resultado2);
		if (bean1.getFlagPagineo()==false)
			output.setCantidadRegistros(String.valueOf(conteo));
		else
			output.setCantidadRegistros(bean1.getCantidad());


		//calcular numero para boton "retroceder pagina"
		/*if (bean1.getSalto()==1)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(bean1.getSalto()-1);
		*/
		if (pagina==0)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(pagina-1);

		//calcular numero para boton "avanzar pagina"
		/*if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(bean1.getSalto()+1);
		*/
		if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(pagina+1);

		//calcular regs del x al y
		int del = (pagina*propiedades.getLineasPorPag())+1;
		int al  = del+resultado2.size()-1;
		output.setNdel(del);
		output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);
		//////


		output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicularJuri");
		req.setAttribute("output", output);
		// Inicio:mgarate:31/05/2007
		   req.setAttribute("criterioBusqueda",criterioBusqueda);
		   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
		// Fin:mgarate:31/05/2007
		
	    /*inicio:dbravo:14/09/2007*/
	    req.setAttribute("flagVerifica", bean1.getVerifica());
	    /*fin:dbravo:14/09/2007*/
	    
		response.setStyle("listaNombre");////resultadoPersonaNatural

		if (bean1.getFlagPagineo()==false)
		{
			//llamar a "Transaccion"
			LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();

			//Datos generales
			 //Modificado por: Proyecto Filtros de Acceso
			 //Fecha: 02/10/2006
			 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
			 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
			 //Fecha: 08/10/2006             
			 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
			 //Fin Modificación
			bt.setUsuarioSession(usuario);
			bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
			//Tarifario
			bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
			//datos particulares de esta transaccion
			bt.setPartSeleccionado("PJ");
			bt.setNumSedes(bean1.getSedesElegidas());
			//bt.setNomApeRazSocPart(apellidoPaterno + " " + apellidoMaterno + " " + nombre);
			bt.setNomApeRazSocPart(bean1.getArea4Razon());
			bt.setCodAreaReg(bean1.getComboArea());
			bt.setTipoParticipacion(tipoParticipacion);
			bt.setTipoPersPart("N");
			/*
				Job004 j = new Job004();
				j.setBean(bt);
				Thread llamador1 = new Thread(j);
				llamador1.start();
			*/
			if (Propiedades.getInstance().getFlagTransaccion()==true){
				/**
				  *  inicio, dbravo: 15/06/2007
				  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				  *  			   inicialmente el Job002.
				  */
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if(q2!=null){	
					if (bean1.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
								Job004 j = new Job004();
								j.setQuery(q2.toString());
								j.setUsuario(usuario);
								j.setCostoServicio(prepagoBean.getMontoBruto());
								j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
								Thread llamador1 = new Thread(j);
								llamador1.start();
						}
					}
				}
				/**
				  * fin, dbravo: 15/06/2007
				  */	
			}
		}//if flagPagine

		conn.commit();
		if (usuario.getFgInterno()==false)
		{
			LineaPrepago lineaCmd = new LineaPrepago();
			double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
			usuario.setSaldo(nuevoSaldo);
		}




	 }

		catch (ValidacionException e)
		{
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}

		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try
					{
						rollback(conn, request);
					}
					catch (Throwable ex)
					{
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
					}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		}
		catch (DBException dbe)
		{
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}

		catch (Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally
		{
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeResultSet(rsetc);
				JDBC.getInstance().closeStatement(stmt);
				JDBC.getInstance().closeStatement(stmtc);
				pool.release(conn);
				end(request);
		}
		return response;
	}

///BUSQUEDA X NUMERO DE MOTOR
public ControllerResponse runBuscarXNombreVehicularMotorState(ControllerRequest request,ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmt   = null;
		ResultSet rset   = null;
		Statement stmtc   = null;
		ResultSet rsetc   = null;

	try
		{
		init(request);
		validarSesion(request);

		conn = pool.getConnection();
		conn.setAutoCommit(false);
		DBConnection dconn = new DBConnection(conn);

		ArrayList resultado = new ArrayList();
		Propiedades propiedades = Propiedades.getInstance();
		int conteo=0;
		boolean haySiguiente = false;

		// Inicio:mgarate:31/05/2007
		   String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=7";;		
		// Fin:mgarate:31/05/2007
		
		InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_NUMERO_MOTOR);

		String tipoParticipacion = "";
		String tipoBusqVehi = "";
		String numeroMotor = "";

		StringBuffer sb = new StringBuffer();

		if (bean1.getHid2().equals("4"))
		{
				numeroMotor = bean1.getArea4NumMotor().trim();
				tipoParticipacion = bean1.getArea4TipoParticipacion();
				tipoBusqVehi = bean1.getArea4Tipo();
		}

		StringBuffer q  = new StringBuffer();
		StringBuffer q2 = new StringBuffer();
		StringBuffer qca = new StringBuffer();
		StringBuffer qcb = new StringBuffer();

	if(req.getParameter("salto")==null)
	{

				q.append(" SELECT /*+ORDERED  INDEX(VEHICULO INDEX_VEHICULO_NUM_MOTOR)  INDEX(TM_MARCA_vehi PK_MARCA_VEHI) */");
				q2.append(" SELECT /*+ORDERED  INDEX(VEHICULO INDEX_VEHICULO_NUM_MOTOR)  INDEX(TM_MARCA_vehi PK_MARCA_VEHI) */");
				qca.append(" SELECT ");
				qcb.append(" SELECT ");

				q.append(" partida.REG_PUB_ID as REG_PUB_ID , partida.refnum_part,  partida.cod_libro, ");
				//hphp
				q.append(" partida.estado, ");

				q.append(" partida.ofic_reg_id  as ofic_reg_id,   partida.num_partida as num_partida,");
				q.append(" regis_publico.siglas, ofic_registral.nombre, ");
				q.append(" vehiculo.num_placa ,  vehiculo.fg_baja ,");
				q.append(" vehiculo.NUM_MOTOR as NUM_MOTOR, vehiculo.NUM_SERIE, ");
				q.append(" vehiculo.COD_MARCA, vehiculo.COD_MODELO, ");
				q.append(" TM_MODELO_VEHI.DESCRIPCION as descrip1  , TM_MARCA_VEHI.DESCRIPCION as descrip2");
				q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
				qca.append(" count(vehiculo.NUM_MOTOR) ");
				qcb.append(" count(partida.refnum_part) ");

				q.append(" FROM  VEHICULO, partida, ofic_registral, regis_publico, TM_MARCA_VEHI, TM_MODELO_VEHI ");
				q.append(" WHERE VEHICULO.NUM_MOTOR like '").append(numeroMotor).append("%'");

			 	if(usuario.getFgInterno()==false)
				 q.append(" AND VEHICULO.FG_BAJA = '0' ");

			 	if(usuario.getFgInterno()==true && bean1.getFlagIncluirInactivos() == false)
				 q.append(" AND  VEHICULO.FG_BAJA = '0' ");

				q2.append(" FROM  VEHICULO, partida, ofic_registral, regis_publico, TM_MARCA_VEHI, TM_MODELO_VEHI ");
				q2.append(" WHERE VEHICULO.NUM_MOTOR like '").append(numeroMotor).append("%'");


				qca.append(" FROM VEHICULO");
				qca.append(" WHERE ");
				qca.append(" NUM_MOTOR like '").append(numeroMotor).append("%'");

				qcb.append(" FROM VEHICULO,partida ");
				qcb.append(" WHERE VEHICULO.NUM_MOTOR like '").append(numeroMotor).append("%'");
				qcb.append(" AND PARTIDA.REFNUM_PART  = VEHICULO.REFNUM_PART ");

				appendCondicionEstadoPartida(qcb);


				q.append(" AND PARTIDA.REFNUM_PART = VEHICULO.REFNUM_PART ");
				q2.append(" AND PARTIDA.REFNUM_PART = VEHICULO.REFNUM_PART ");


	 			 if(usuario.getFgInterno()==false)
				 {
				 	q.append(" AND VEHICULO.FG_BAJA = '0' ");
	    			q2.append(" AND VEHICULO.FG_BAJA = '0' ");
		 			qcb.append(" AND VEHICULO.FG_BAJA = '0' ");


				 }
				 if(usuario.getFgInterno()==true && bean1.getFlagIncluirInactivos() == false)
				 {
					 q.append(" AND  VEHICULO.FG_BAJA = '0' ");
	 				 q2.append(" AND VEHICULO.FG_BAJA = '0' ");
	      			 qcb.append(" AND VEHICULO.FG_BAJA = '0' ");

				 }

			StringBuffer sbregpub = new StringBuffer();
			if (bean1.getSedesElegidas().length==1)
				{

					sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				}

			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
				{
					sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
				}
			q.append(sbregpub.toString());
			q2.append(sbregpub.toString());
			qcb.append(sbregpub.toString());

			q.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			q2.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			qcb.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("' ");


				appendCondicionEstadoPartida(q);
				appendCondicionEstadoPartida(q2);

				q.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");
				q2.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");

				q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
				q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");

				q.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");
				q2.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");

				q.append(" AND TM_MARCA_VEHI.COD_MARCA = vehiculo.COD_MARCA ");
				q2.append(" AND TM_MARCA_VEHI.COD_MARCA = vehiculo.COD_MARCA ");
				q.append(" AND TM_MODELO_VEHI.COD_MODELO = vehiculo.COD_MODELO ");
				q2.append(" AND TM_MODELO_VEHI.COD_MODELO = vehiculo.COD_MODELO ");


			if(bean1.getFlagIncluirInactivos()==false)
			{
				q.append(" ORDER BY PARTIDA.REG_PUB_ID, partida.ofic_reg_id, partida.num_partida DESC, vehiculo.NUM_MOTOR");
			}
			else
			{

				q.append(" UNION ALL ");
				q.append(" SELECT /*+ORDERED  INDEX(VEHICULO_HIST INDEX_VEHICULO_HIST_NUM_MOTOR) */");

				q.append(" partida.REG_PUB_ID , partida.refnum_part,  partida.cod_libro, ");
				//10/10/2003 Falto hphp
				q.append(" partida.estado, ");
				q.append(" partida.ofic_reg_id ,   partida.num_partida ,");
				q.append(" regis_publico.siglas, ofic_registral.nombre, ");
				q.append(" vehiculo_hist.num_placa ,  '-' ,");
				q.append(" vehiculo_hist.NUM_MOTOR as NUM_MOTOR, vehiculo_hist.NUM_SERIE, ");
				q.append(" '-', '-', ");
				q.append(" '-'   , '-'");


				q.append(" FROM  vehiculo_hist, partida, ofic_registral, regis_publico ");
				q.append(" WHERE vehiculo_hist.NUM_MOTOR like '").append(numeroMotor).append("%'");
				q.append(" AND PARTIDA.REFNUM_PART = vehiculo_hist.REFNUM_PART ");

			sbregpub = new StringBuffer();
			if (bean1.getSedesElegidas().length==1)
				{
					sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
				}

			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
				{
					sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
				}
			q.append(sbregpub.toString());
			q.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("' ");
			appendCondicionEstadoPartida(q);


			q.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id  ");
			q.append(" ORDER BY REG_PUB_ID, ofic_reg_id, num_partida, NUM_MOTOR");

			}//fin if flagInactivo


			//contar
			if (bean1.getFlagPagineo()==false)
			{

				if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qca.toString());
				boolean bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");


				if (isTrace(this)) System.out.println("___verqueryCOUNT_B__"+qcb.toString());
				rsetc   = stmtc.executeQuery(qcb.toString());
				bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo > propiedades.getMaxResultadosBusqueda() )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");

			}


			if (isTrace(this)) System.out.println("___verquery___"+q.toString());
			//UsoServicio_________________________________
						
						/**
						 * inicio, dbravo: 15/06/2007
						 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion
						if (bean1.getFlagPagineo()==false)
						{
							/*
							validar que el usuario NO sea de zona WEB
							/
							if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
							{
								//estamos en la primera llamada
								//enviamos TODOS los registros encontrados
								//a otro Thread para que registre el UsoServicio
									Job002 j = new Job002();
									j.setQuery(q2.toString());
									j.setUsuario(usuario);
									j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
									Thread llamador1 = new Thread(j);
									llamador1.start();
							}
						}
						/**
						  * fin, dbravo: 15/06/2007
						  */


			//descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

			//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
		/** Importante! Para pagineo.
			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
		**/

			boolean b = rset.next();

			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);

			int conta=0;
			boolean nuevo = true;
			String antRefNumPart = "";
			String antRegPubDescripcion = "";
			String antnombre = "";
			//String antEstadoAct = "";

			StringBuffer cadenaPart = new StringBuffer();

			PartidaBean partidaBean = new PartidaBean();

			while (b==true)
			{

				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("refnum_part");
				String oficRegDesc = rset.getString("nombre");
				String codLibro   = rset.getString("cod_libro");

				String numerMotor = rset.getString("NUM_MOTOR").trim();/////by leo


				if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)))
				{
					nuevo = false;

					if(!antnombre.equals(numerMotor))
					{
						cadenaPart.append(" ; ").append(numerMotor);
						antnombre = numerMotor;
					}

				}
				else
				{

					if(!antRefNumPart.equals(""))
					{
						partidaBean.setParticipanteDescripcion(cadenaPart.toString());
						resultado.add(partidaBean);
						conta++;
						partidaBean = new PartidaBean();

					}

					antRefNumPart = refNumPart;
					antRegPubDescripcion = oficRegDesc;
					antnombre = numerMotor;


					cadenaPart.delete(0,cadenaPart.length());
					cadenaPart.append(numerMotor);
					nuevo = true;
				}

				if(nuevo)
				{

					partidaBean.setRefNumPart(refNumPart);
					partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setRegPubDescripcion(rset.getString("siglas"));
					partidaBean.setOficRegDescripcion(oficRegDesc);
					partidaBean.setEstado(rset.getString("estado"));

					/**kuma 2003/09/02**/
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					/**fin kuma**/

					//*vehicular*//añadido por Leo

					partidaBean.setNumeroPlaca(rset.getString("num_placa"));
					partidaBean.setNumeroMotor(rset.getString("NUM_MOTOR"));
					partidaBean.setNumeroSerie(rset.getString("NUM_SERIE"));

					//TM_MODELO_VEHI.DESCRIPCION   , TM_MARCA_VEHI.DESCRIPCION"
					partidaBean.setMarca(rset.getString("descrip2"));
					partidaBean.setModelo(rset.getString("descrip1"));


					String Baja = rset.getString("fg_baja");
					if(Baja.equals("0"))
						partidaBean.setBaja("En circulación");
					else
						partidaBean.setBaja("Fuera de circulación");

					//**//


					//ficha
					dboFicha.clearAll();
					sb.delete(0, sb.length());
					sb.append(dboFicha.CAMPO_FICHA).append("|");
					sb.append(dboFicha.CAMPO_FICHA_BIS);
					dboFicha.setFieldsToRetrieve(sb.toString());
					dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
					if (dboFicha.find() == true)
							{
								partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
								String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
								int nbis = 0;
								try {
									nbis = Integer.parseInt(bis);
								}
								catch (NumberFormatException n)
								{
									nbis =0;
								}
								if (nbis>=1)
									partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
							}

					//obtener tomo y foja
					dboTomoFolio.clearAll();
					sb.delete(0, sb.length());
					sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
					sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
					sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
					sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
					dboTomoFolio.setFieldsToRetrieve(sb.toString());
					dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
					if (dboTomoFolio.find() == true)
							{
								partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
								partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


								String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
								String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


								if (bist.trim().length() > 0)
										partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);


								if (bisf.trim().length() > 0)
										partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

								//28dic2002
								//quitar el caracter "9" del inicio del tomoid
									if (partidaBean.getTomoId().length()>0)
									{
										if (partidaBean.getTomoId().startsWith("9"))
											{
												String ntomo = partidaBean.getTomoId().substring(1);
												partidaBean.setTomoId(ntomo);
											}
									}
							}


					//descripcion libro
					dboTmLibro.clearAll();
					dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
					dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
					if (dboTmLibro.find() == true)
						partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));




					//participante y su número de documento
					String tipoDocumento="";
					String codPartic="";


					//descripción del tipo de participación
					dboParticLibro.clearAll();
					dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
					//dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
					dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
						if (dboParticLibro.find()==true)
							partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));


				}



				b = rset.next();
			/**Importante para pagineo
				if (conta==propiedades.getLineasPorPag())
				{
					if(b==true)
						haySiguiente = true;

					break;
				}
			**/

			}//while (b==true)

			partidaBean.setParticipanteDescripcion(cadenaPart.toString());
			resultado.add(partidaBean);
			conta++;

			if (resultado.size()==0)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

			//sesion.setAttribute("resultado",resultado);

			/** En caso que no exista, lo inserto, para que elimine temporales al finalizar sesion**/
			if(sesion.getAttribute("monitor")==null)
			{
				MonitorDeSesion monitor = new MonitorDeSesion();
				sesion.setAttribute("monitor",monitor);
			}

			/** Borro temporal ya que esta puede no ser primera consulta **/
			sb.delete(0,sb.length());
			sb.append("DELETE FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			stmt.executeUpdate(sb.toString());

			/** Inserto blob vacio **/
			sb.delete(0,sb.length());
			sb.append("INSERT INTO DATA_SESSION(SESSION_ID, NOMBRE, TS_CREACION, VALOR) VALUES('").append(sesion.getId()).append("','").append("BusqIndirectaPersNat").append("', SYSDATE, empty_blob())");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			/** Selecciono el blob como referencia, para actualizacion **/
			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("' FOR UPDATE");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			oracle.sql.BLOB campoBlob = null;

			if(rset.next())
			{
				campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


			/** Referencio el blob vacio hacia el outputStream, y luego calculo el tamaño del buffer que me servira para llenar el blob **/
			//java.io.ObjectInputStream ioStr = null;
			java.io.OutputStream outstream = campoBlob.getBinaryOutputStream();
			int j = -1;
			int bSize = campoBlob.getBufferSize(); // buffer del campo blob

			byte buffer[] = new byte[bSize];

			/** El Arraylist lo serializo, para introducirlo en el inputStream **/
			java.io.InputStream instream = gob.pe.sunarp.extranet.common.Serializacion.getInstance().serializarAStream(resultado);

			/** El inputStream lo introduzco de a pocos en el outputStream, que tiene la referencia hacia el blob, asi que lo llena. **/
			j = instream.read(buffer);
			while (j != -1) {
				outstream.write(buffer, 0, j);
				j = instream.read(buffer);
			}

			/** Cierro los streams **/
			outstream.close();
			instream.close();


	}///if(salto)
	else
	{

			/** Caso contrario, consulta ya fue hecha, debo recuperar de tabla temporal **/

			//resultado = (java.util.ArrayList)sesion.getAttribute("resultado");

			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());
			if(rset.next())
			{
				/** Recupero el Blob, serializado **/
				oracle.sql.BLOB campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

				/** Para poder usarlo, debo deserializarlo **/
				resultado = (ArrayList) gob.pe.sunarp.extranet.common.Serializacion.getInstance().deserializar(campoBlob.binaryStreamValue());
			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


	}



		java.util.ArrayList resultado2 = new java.util.ArrayList();
		int pagina = 0;


		if(req.getParameter("salto")!=null)
			pagina = Integer.parseInt((String)req.getParameter("salto"));

		int i = pagina * propiedades.getLineasPorPag();
		conteo = resultado.size();
		while ( (i < ((pagina+1) * propiedades.getLineasPorPag())) && (i<conteo))
		{
			resultado2.add(resultado.get(i));
			i++;
		}
		if(i<conteo)
			haySiguiente = true;

		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		if (bean1.getFlagIncluirInactivos()==true)
			output.setFlagEstado(true);
		output.setResultado(resultado2);
		if (bean1.getFlagPagineo()==false)
			output.setCantidadRegistros(String.valueOf(conteo));
		else
			output.setCantidadRegistros(bean1.getCantidad());


		//calcular numero para boton "retroceder pagina"
		/*if (bean1.getSalto()==1)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(bean1.getSalto()-1);
		*/
		if (pagina==0)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(pagina-1);

		//calcular numero para boton "avanzar pagina"
		/*if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(bean1.getSalto()+1);
		*/
		if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(pagina+1);

		//calcular regs del x al y
		int del = (pagina*propiedades.getLineasPorPag())+1;
		int al  = del+resultado2.size()-1;
		output.setNdel(del);
		output.setNal(al);

		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);
		//////


		output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicularMotor");
		req.setAttribute("output", output);
		
		// Inicio:mgarate:31/05/2007
		   req.setAttribute("criterioBusqueda",criterioBusqueda);
		   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
		// Fin:mgarate:31/05/2007
		
	    /*inicio:dbravo:14/09/2007*/
	    req.setAttribute("flagVerifica", bean1.getVerifica());
	    /*fin:dbravo:14/09/2007*/   
		   
		response.setStyle("resultadoMotor");////resultadoPersonaNatural

		if (bean1.getFlagPagineo()==false)
		{
			//llamar a "Transaccion"
			LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();

			//Datos generales
			 //Modificado por: Proyecto Filtros de Acceso
			 //Fecha: 02/10/2006
			 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
			 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
			 //Fecha: 08/10/2006             
			 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
			 //Fin Modificación
			bt.setUsuarioSession(usuario);
			bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
			//Tarifario
			bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
			//datos particulares de esta transaccion
			bt.setTipoBusqPartida(Constantes.REG_VEH_MOTOR);
			bt.setPartSeleccionado("NM");
			bt.setNumSedes(bean1.getSedesElegidas());
			//bt.setNomApeRazSocPart(apellidoPaterno + " " + apellidoMaterno + " " + nombre);
			bt.setNomApeRazSocPart(numeroMotor);
			bt.setCodAreaReg(bean1.getComboArea());
			//hp
			/*
			bt.setTipoParticipacion(tipoParticipacion);
			bt.setTipoPersPart("N");
			*/
			/*
				Job004 j = new Job004();
				j.setBean(bt);
				Thread llamador1 = new Thread(j);
				llamador1.start();
			*/
			if (Propiedades.getInstance().getFlagTransaccion()==true){
				/**
				  *  inicio, dbravo: 15/06/2007
				  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				  *  			   inicialmente el Job002.
				  */
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if (bean1.getFlagPagineo()==false)
				{
					/*
					validar que el usuario NO sea de zona WEB
					*/
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						//estamos en la primera llamada
						//enviamos TODOS los registros encontrados
						//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
							Thread llamador1 = new Thread(j);
							llamador1.start();
					}
				}
				/**
				  * fin, dbravo: 15/06/2007
				  */
			}
		}//if flagPagine

		conn.commit();
		if (usuario.getFgInterno()==false)
		{
			LineaPrepago lineaCmd = new LineaPrepago();
			double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
			usuario.setSaldo(nuevoSaldo);
		}




	 }

		catch (ValidacionException e)
		{
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}

		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try
					{
						rollback(conn, request);
					}
					catch (Throwable ex)
					{
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
					}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		}
		catch (DBException dbe)
		{
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}

		catch (Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally
		{
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeResultSet(rsetc);
				JDBC.getInstance().closeStatement(stmt);
				JDBC.getInstance().closeStatement(stmtc);
				pool.release(conn);
				end(request);
		}


		return response;
	}


///CHASIS


public ControllerResponse runBuscarXNombreVehicularChasisState(ControllerRequest request,ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);

		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		Statement stmt   = null;
		ResultSet rset   = null;
		Statement stmtc   = null;
		ResultSet rsetc   = null;

	try
		{
		init(request);
		validarSesion(request);

		conn = pool.getConnection();
		conn.setAutoCommit(false);
		DBConnection dconn = new DBConnection(conn);

		ArrayList resultado = new ArrayList();
		Propiedades propiedades = Propiedades.getInstance();
		int conteo=0;
		boolean haySiguiente = false;

		// Inicio:mgarate:31/05/2007
		   String criterioBusqueda = req.getParameter("criterio")+"/flagmetodo=8";
		// Fin:mgarate:31/05/2007
		
		InputBusqIndirectaBean bean1 = Tarea.recojeDatosRequestBusqIndirectaPartida(dconn, req, BUSQUEDA_INDIRECTA_NUMERO_CHASIS);

		String tipoParticipacion = "";
		String tipoBusqVehi = "";
		String numeroChasis = "";

		StringBuffer sb = new StringBuffer();

		if (bean1.getHid2().equals("4"))
		{
				numeroChasis = bean1.getArea4NumChasis().trim();
				tipoParticipacion = bean1.getArea4TipoParticipacion();
				tipoBusqVehi = bean1.getArea4Tipo();
		}

		StringBuffer q  = new StringBuffer();
		StringBuffer q2 = new StringBuffer();
		StringBuffer qca = new StringBuffer();
		StringBuffer qcb = new StringBuffer();

	if(req.getParameter("salto")==null)
	{

				q.append(" SELECT /*+ORDERED  INDEX(VEHICULO INDEX_VEHICULO_NUM_SERIE)  INDEX(TM_MARCA_vehi PK_MARCA_VEHI) */");
				q2.append(" SELECT /*+ORDERED  INDEX(VEHICULO INDEX_VEHICULO_NUM_SERIE)  INDEX(TM_MARCA_vehi PK_MARCA_VEHI) */");
				qca.append(" SELECT ");
				qcb.append(" SELECT ");

				q.append(" partida.REG_PUB_ID as REG_PUB_ID , partida.refnum_part,  partida.cod_libro, ");
				//hphp
				q.append(" partida.estado,");

				q.append(" partida.ofic_reg_id  as ofic_reg_id,   partida.num_partida as num_partida,");
				q.append(" regis_publico.siglas, ofic_registral.nombre, ");
				q.append(" vehiculo.num_placa ,  vehiculo.fg_baja ,");
				q.append(" vehiculo.NUM_MOTOR as NUM_MOTOR, vehiculo.NUM_SERIE, ");
				q.append(" vehiculo.COD_MARCA, vehiculo.COD_MODELO, ");
				q.append(" TM_MODELO_VEHI.DESCRIPCION as descrip1  , TM_MARCA_VEHI.DESCRIPCION as descrip2");
				q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
				qca.append(" count(vehiculo.NUM_SERIE) ");
				qcb.append(" count(partida.refnum_part) ");


				q.append(" FROM  VEHICULO, partida, ofic_registral, regis_publico, TM_MARCA_VEHI, TM_MODELO_VEHI ");
				q.append(" WHERE VEHICULO.NUM_SERIE like '").append(numeroChasis).append("%'");

			 	if(usuario.getFgInterno()==false)
				 q.append(" AND VEHICULO.FG_BAJA = '0' ");

			 	if(usuario.getFgInterno()==true && bean1.getFlagIncluirInactivos() == false)
				 q.append(" AND  VEHICULO.FG_BAJA = '0' ");

				q2.append(" FROM  VEHICULO, partida, ofic_registral, regis_publico, TM_MARCA_VEHI, TM_MODELO_VEHI ");
				q2.append(" WHERE VEHICULO.NUM_SERIE like '").append(numeroChasis).append("%'");


				qca.append(" FROM VEHICULO");
				qca.append(" WHERE ");
				qca.append(" NUM_SERIE like '").append(numeroChasis).append("%'");

				qcb.append(" FROM VEHICULO,partida ");
				qcb.append(" WHERE VEHICULO.NUM_SERIE like '").append(numeroChasis).append("%'");
				qcb.append(" AND PARTIDA.REFNUM_PART  = VEHICULO.REFNUM_PART ");

				appendCondicionEstadoPartida(qcb);


				q.append(" AND PARTIDA.REFNUM_PART = VEHICULO.REFNUM_PART ");
				q2.append(" AND PARTIDA.REFNUM_PART = VEHICULO.REFNUM_PART ");

	 			 if(usuario.getFgInterno()==false)
				 {
				 	q.append(" AND VEHICULO.FG_BAJA = '0' ");
	    			q2.append(" AND VEHICULO.FG_BAJA = '0' ");
		 			qcb.append(" AND VEHICULO.FG_BAJA = '0' ");


				 }
				 if(usuario.getFgInterno()==true && bean1.getFlagIncluirInactivos() == false)
				 {
					 q.append(" AND  VEHICULO.FG_BAJA = '0' ");
	 				 q2.append(" AND VEHICULO.FG_BAJA = '0' ");
	      			 qcb.append(" AND VEHICULO.FG_BAJA = '0' ");

				 }

			StringBuffer sbregpub = new StringBuffer();
			if (bean1.getSedesElegidas().length==1)
				{

					sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");

				}

			if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
				{
					sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
				}
			q.append(sbregpub.toString());
			q2.append(sbregpub.toString());
			qcb.append(sbregpub.toString());

			q.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			q2.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("'");
			qcb.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("' ");


				appendCondicionEstadoPartida(q);
				appendCondicionEstadoPartida(q2);

				q.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");
				q2.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");

				q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
				q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");

				q.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");
				q2.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id ");

				q.append(" AND TM_MARCA_VEHI.COD_MARCA = vehiculo.COD_MARCA ");
				q2.append(" AND TM_MARCA_VEHI.COD_MARCA = vehiculo.COD_MARCA ");
				q.append(" AND TM_MODELO_VEHI.COD_MODELO = vehiculo.COD_MODELO ");
				q2.append(" AND TM_MODELO_VEHI.COD_MODELO = vehiculo.COD_MODELO ");

			if(bean1.getFlagIncluirInactivos()==false)
			{
				q.append(" ORDER BY PARTIDA.REG_PUB_ID, partida.ofic_reg_id, partida.num_partida DESC, vehiculo.NUM_SERIE");
			}
			else
			{

				q.append(" UNION ALL ");
				q.append(" SELECT /*+ORDERED  INDEX(VEHICULO_HIST INDEX_VEHICULO_HIST_NUM_SERIE) */");

				q.append(" partida.REG_PUB_ID , partida.refnum_part,  partida.cod_libro, ");
				//Se olvido hphp 10/10/2003
				q.append(" partida.estado,");
				q.append(" partida.ofic_reg_id ,   partida.num_partida ,");
				q.append(" regis_publico.siglas, ofic_registral.nombre, ");
				q.append(" vehiculo_hist.num_placa ,  '-' ,");
				q.append(" vehiculo_hist.NUM_MOTOR as NUM_MOTOR, vehiculo_hist.NUM_SERIE as NUM_SERIE, ");
				q.append(" '-', '-', ");
				q.append(" '-', '-'");

				q.append(" FROM  vehiculo_hist, partida, ofic_registral, regis_publico ");
				q.append(" WHERE vehiculo_hist.NUM_SERIE like '").append(numeroChasis).append("%'");
				q.append(" AND PARTIDA.REFNUM_PART = vehiculo_hist.REFNUM_PART ");

				sbregpub = new StringBuffer();
				if (bean1.getSedesElegidas().length==1)
					{
						sbregpub.append(" AND partida.reg_pub_id = '").append(bean1.getSedesElegidas()[0]).append("'");
					}

				if (bean1.getSedesElegidas().length>=2 && bean1.getSedesElegidas().length<=12)
					{
						sbregpub.append(" AND partida.reg_pub_id IN ").append(bean1.getSedesSQLString());
					}
				q.append(sbregpub.toString());
				q.append(" AND area_reg_id = '").append(bean1.getComboArea()).append("' ");
				appendCondicionEstadoPartida(q);


				q.append(" AND ofic_registral.ofic_reg_id = partida.ofic_reg_id ");
				q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
				q.append(" AND regis_publico.reg_pub_id = ofic_registral.reg_pub_id  ");
				q.append(" ORDER BY REG_PUB_ID, ofic_reg_id, num_partida, NUM_SERIE");

			}//fin if flagInactivo



			//contar
			if (bean1.getFlagPagineo()==false)
			{

				if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qca.toString());
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qca.toString());
				boolean bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo> (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");


				if (isTrace(this)) System.out.println("___verqueryCOUNT_B__"+qcb.toString());
				rsetc   = stmtc.executeQuery(qcb.toString());
				bc = rsetc.next();
				conteo = rsetc.getInt(1);
				if (conteo > propiedades.getMaxResultadosBusqueda() )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if (conteo==0)
					throw new ValidacionException("No se encontraron resultados para su búsqueda");

			}


			if (isTrace(this)) System.out.println("___verquery___"+q.toString());
			//UsoServicio_________________________________
						/**
						 * inicio, dbravo: 15/06/2007
						 * descripcion: Se retira el Job002, se ingresa el job004, el job004 se ingresa despues de realizar la transaccion

						if (bean1.getFlagPagineo()==false)
						{
							/*
							validar que el usuario NO sea de zona WEB
							/
							if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
							{
								//estamos en la primera llamada
								//enviamos TODOS los registros encontrados
								//a otro Thread para que registre el UsoServicio
									Job002 j = new Job002();
									j.setQuery(q2.toString());
									j.setUsuario(usuario);
									j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
									Thread llamador1 = new Thread(j);
									llamador1.start();
							}
						}
						/**
						  * fin, dbravo: 15/06/2007
						  */

			//descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,bean1.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

			//String descripcionAreaRegistral = bean1.getDescGrupoLibroArea();

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
		/** Importante! Para pagineo.
			if (bean1.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (bean1.getSalto() - 1));
		**/

			boolean b = rset.next();




			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);




			int conta=0;


			boolean nuevo = true;

			String antRefNumPart = "";
			String antRegPubDescripcion = "";
			String antnombre = "";
			//String antEstadoAct = "";

			StringBuffer cadenaPart = new StringBuffer();

			PartidaBean partidaBean = new PartidaBean();

			while (b==true)
			{

				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("refnum_part");
				String oficRegDesc = rset.getString("nombre");
				String codLibro   = rset.getString("cod_libro");


				//String numerMotor = rset.getString("NUM_MOTOR").trim();/////by leo
				String numerChasis = rset.getString("NUM_SERIE").trim();/////by leo

				if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)))
				{
					nuevo = false;
//					if(!antnombre.equals(nombreAct))
//					{
//						cadenaPart.append(" ; ").append(nombreAct);
//						antnombre = nombreAct;
//					}

					if(!antnombre.equals(numerChasis))
					{
						cadenaPart.append(" ; ").append(numerChasis);
						antnombre = numerChasis;
					}

				}
				else
				{

					if(!antRefNumPart.equals(""))
					{
						partidaBean.setParticipanteDescripcion(cadenaPart.toString());
						resultado.add(partidaBean);
						conta++;
						partidaBean = new PartidaBean();

					}

					antRefNumPart = refNumPart;
					antRegPubDescripcion = oficRegDesc;
					antnombre = numerChasis;
					//antEstadoAct = estadoAct;

					cadenaPart.delete(0,cadenaPart.length());
					cadenaPart.append(numerChasis);
					nuevo = true;
				}

				if(nuevo)
				{

					partidaBean.setRefNumPart(refNumPart);
					partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setRegPubDescripcion(rset.getString("siglas"));
					partidaBean.setOficRegDescripcion(oficRegDesc);

					/**kuma 2003/09/02**/
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					/**fin kuma**/

					//*vehicular*//añadido por Leo

					partidaBean.setNumeroPlaca(rset.getString("num_placa"));
					partidaBean.setNumeroMotor(rset.getString("NUM_MOTOR"));
					partidaBean.setNumeroSerie(rset.getString("NUM_SERIE"));

					//TM_MODELO_VEHI.DESCRIPCION   , TM_MARCA_VEHI.DESCRIPCION"
					partidaBean.setMarca(rset.getString("descrip2"));
					partidaBean.setModelo(rset.getString("descrip1"));

					partidaBean.setEstado(rset.getString("estado"));

					String Baja = rset.getString("fg_baja");
					if(Baja.equals("0"))
						partidaBean.setBaja("En circulación");
					else
						partidaBean.setBaja("Fuera de circulación");

					//**//


					//ficha
					dboFicha.clearAll();
					sb.delete(0, sb.length());
					sb.append(dboFicha.CAMPO_FICHA).append("|");
					sb.append(dboFicha.CAMPO_FICHA_BIS);
					dboFicha.setFieldsToRetrieve(sb.toString());
					dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
					if (dboFicha.find() == true)
							{
								partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
								String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
								int nbis = 0;
								try {
									nbis = Integer.parseInt(bis);
								}
								catch (NumberFormatException n)
								{
									nbis =0;
								}
								if (nbis>=1)
									partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
							}

					//obtener tomo y foja
					dboTomoFolio.clearAll();
					sb.delete(0, sb.length());
					sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
					sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
					sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
					sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
					dboTomoFolio.setFieldsToRetrieve(sb.toString());
					dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
					if (dboTomoFolio.find() == true)
							{
								partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
								partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


								String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
								String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


								if (bist.trim().length() > 0)
										partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);


								if (bisf.trim().length() > 0)
										partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

								//28dic2002
								//quitar el caracter "9" del inicio del tomoid
									if (partidaBean.getTomoId().length()>0)
									{
										if (partidaBean.getTomoId().startsWith("9"))
											{
												String ntomo = partidaBean.getTomoId().substring(1);
												partidaBean.setTomoId(ntomo);
											}
									}
							}


					//descripcion libro
					dboTmLibro.clearAll();
					dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
					dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
					if (dboTmLibro.find() == true)
						partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));




					//participante y su número de documento
					String tipoDocumento="";
					String codPartic="";


					//descripción del tipo de participación
					dboParticLibro.clearAll();
					dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
					//dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
					dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
						if (dboParticLibro.find()==true)
							partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));


				}



				b = rset.next();
			/**Importante para pagineo
				if (conta==propiedades.getLineasPorPag())
				{
					if(b==true)
						haySiguiente = true;

					break;
				}
			**/

			}//while (b==true)

			partidaBean.setParticipanteDescripcion(cadenaPart.toString());
			resultado.add(partidaBean);
			conta++;

			if (resultado.size()==0)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);

			//sesion.setAttribute("resultado",resultado);

			/** En caso que no exista, lo inserto, para que elimine temporales al finalizar sesion**/
			if(sesion.getAttribute("monitor")==null)
			{
				MonitorDeSesion monitor = new MonitorDeSesion();
				sesion.setAttribute("monitor",monitor);
			}

			/** Borro temporal ya que esta puede no ser primera consulta **/
			sb.delete(0,sb.length());
			sb.append("DELETE FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			stmt.executeUpdate(sb.toString());

			/** Inserto blob vacio **/
			sb.delete(0,sb.length());
			sb.append("INSERT INTO DATA_SESSION(SESSION_ID, NOMBRE, TS_CREACION, VALOR) VALUES('").append(sesion.getId()).append("','").append("BusqIndirectaPersNat").append("', SYSDATE, empty_blob())");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			/** Selecciono el blob como referencia, para actualizacion **/
			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("' FOR UPDATE");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());

			oracle.sql.BLOB campoBlob = null;

			if(rset.next())
			{
				campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


			/** Referencio el blob vacio hacia el outputStream, y luego calculo el tamaño del buffer que me servira para llenar el blob **/
			//java.io.ObjectInputStream ioStr = null;
			java.io.OutputStream outstream = campoBlob.getBinaryOutputStream();
			int j = -1;
			int bSize = campoBlob.getBufferSize(); // buffer del campo blob

			byte buffer[] = new byte[bSize];

			/** El Arraylist lo serializo, para introducirlo en el inputStream **/
			java.io.InputStream instream = gob.pe.sunarp.extranet.common.Serializacion.getInstance().serializarAStream(resultado);

			/** El inputStream lo introduzco de a pocos en el outputStream, que tiene la referencia hacia el blob, asi que lo llena. **/
			j = instream.read(buffer);
			while (j != -1) {
				outstream.write(buffer, 0, j);
				j = instream.read(buffer);
			}

			/** Cierro los streams **/
			outstream.close();
			instream.close();


	}///if(salto)
	else
	{

			/** Caso contrario, consulta ya fue hecha, debo recuperar de tabla temporal **/

			//resultado = (java.util.ArrayList)sesion.getAttribute("resultado");

			sb.delete(0,sb.length());
			sb.append("SELECT VALOR FROM DATA_SESSION WHERE SESSION_ID = '").append(sesion.getId()).append("' AND NOMBRE ='").append("BusqIndirectaPersNat").append("'");
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(sb.toString());
			if(rset.next())
			{
				/** Recupero el Blob, serializado **/
				oracle.sql.BLOB campoBlob = ((oracle.jdbc.driver.OracleResultSet) rset).getBLOB("VALOR");

				/** Para poder usarlo, debo deserializarlo **/
				resultado = (ArrayList) gob.pe.sunarp.extranet.common.Serializacion.getInstance().deserializar(campoBlob.binaryStreamValue());
			}
			else
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


	}



		java.util.ArrayList resultado2 = new java.util.ArrayList();
		int pagina = 0;


		if(req.getParameter("salto")!=null)
			pagina = Integer.parseInt((String)req.getParameter("salto"));

		int i = pagina * propiedades.getLineasPorPag();
		conteo = resultado.size();
		while ( (i < ((pagina+1) * propiedades.getLineasPorPag())) && (i<conteo))
		{
			resultado2.add(resultado.get(i));
			i++;
		}
		if(i<conteo)
			haySiguiente = true;

		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		if (bean1.getFlagIncluirInactivos()==true)
			output.setFlagEstado(true);
		output.setResultado(resultado2);
		if (bean1.getFlagPagineo()==false)
			output.setCantidadRegistros(String.valueOf(conteo));
		else
			output.setCantidadRegistros(bean1.getCantidad());


		//calcular numero para boton "retroceder pagina"
		/*if (bean1.getSalto()==1)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(bean1.getSalto()-1);
		*/
		if (pagina==0)
			output.setPagAnterior(-1);
		else
			output.setPagAnterior(pagina-1);

		//calcular numero para boton "avanzar pagina"
		/*if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(bean1.getSalto()+1);
		*/
		if (haySiguiente==false)
			output.setPagSiguiente(-1);
		else
			output.setPagSiguiente(pagina+1);

		//calcular regs del x al y
		int del = (pagina*propiedades.getLineasPorPag())+1;
		int al  = del+resultado2.size()-1;
		output.setNdel(del);
		output.setNal(al);


		//ETIQUETA

		// recuperamos el costo de la visualizacion
		double tarifa = 0;
		DboTarifa dboTarifa = new DboTarifa(dconn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
		dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, bean1.getCodGrupoLibroArea());

		if (dboTarifa.find())
		{
			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			tarifa = Double.parseDouble(sTarifa);
		}
		req.setAttribute("tarifa",""+tarifa);
		// recuperamos el usuario
		String usuaEtiq = usuario.getUserId();
		req.setAttribute("usuaEtiq",usuaEtiq);
		// recuperamos la fecha Actual
		String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
		req.setAttribute("fechaAct",fechaAct);
		//////


		output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicularChasis");
		req.setAttribute("output", output);


		// Inicio:mgarate:31/05/2007
		   req.setAttribute("criterioBusqueda",criterioBusqueda);
		   req.setAttribute("flagCertBusq",bean1.getCodGrupoLibroArea());
		// Fin:mgarate:31/05/2007
		
	    /*inicio:dbravo:14/09/2007*/
	    req.setAttribute("flagVerifica", bean1.getVerifica());
	    /*fin:dbravo:14/09/2007*/
		   
		response.setStyle("resultadoChasis");////resultadoPersonaNatural

		if (bean1.getFlagPagineo()==false)
		{
			//llamar a "Transaccion"
			LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();

			//Datos generales
			 //Modificado por: Proyecto Filtros de Acceso
			 //Fecha: 02/10/2006
			 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
			 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
			 //Fecha: 08/10/2006             
			 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
			 //Fin Modificación
			bt.setUsuarioSession(usuario);
			bt.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
			//Tarifario
			bt.setCodigoGLA(Integer.parseInt(bean1.getCodGrupoLibroArea()));
			//datos particulares de esta transaccion
			bt.setTipoBusqPartida(Constantes.REG_VEH_SERIE);
			bt.setPartSeleccionado("NS");
			bt.setNumSedes(bean1.getSedesElegidas());
			//bt.setNomApeRazSocPart(apellidoPaterno + " " + apellidoMaterno + " " + nombre);
			bt.setNomApeRazSocPart(numeroChasis);
			bt.setCodAreaReg(bean1.getComboArea());
			//Inicio:mgarate:12/09/2007
			//la transaccion necesita estos dos valores
			bt.setTipoParticipacion(tipoParticipacion);
			bt.setTipoPersPart("N");
			//Fin:mgarate
			/*
				Job004 j = new Job004();
				j.setBean(bt);
				Thread llamador1 = new Thread(j);
				llamador1.start();
			*/
			if (Propiedades.getInstance().getFlagTransaccion()==true){
				/**
				  *  inicio, dbravo: 15/06/2007
				  *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				  *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				  *  			   inicialmente el Job002.
				  */
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if (bean1.getFlagPagineo()==false)
				{
					/*
					validar que el usuario NO sea de zona WEB
					*/
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						//estamos en la primera llamada
						//enviamos TODOS los registros encontrados
						//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							j.setCodigoServicio(Tarea.getServicioIdPorNumeroSedes(bean1.getSedesElegidas().length));
							Thread llamador1 = new Thread(j);
							llamador1.start();
					}
				}
				/**
				  * fin, dbravo: 15/06/2007
				  */
			}
		}//if flagPagine

		conn.commit();
		if (usuario.getFgInterno()==false)
		{
			LineaPrepago lineaCmd = new LineaPrepago();
			double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
			usuario.setSaldo(nuevoSaldo);
		}

	 }
		catch (ValidacionException e)
		{
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}

		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","Lo sentimos, no se encontro la partida buscada");
					try
					{
						rollback(conn, request);
					}
					catch (Throwable ex)
					{
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
					}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}

		}
		catch (DBException dbe)
		{
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}

		catch (Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally
		{
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeResultSet(rsetc);
				JDBC.getInstance().closeStatement(stmt);
				JDBC.getInstance().closeStatement(stmtc);
				pool.release(conn);
				end(request);
		}


		return response;
	}







	private void appendCondicionEstadoPartida(StringBuffer q) {
		q.append(" and PARTIDA.ESTADO != '2' ");
	}

	boolean validaNulo(String cadena)
	{
		if (cadena == null)
		{
			return false;
		}
		else
		{

			if (cadena.trim().equals(""))
			{
				return false;
			}
			else
			{
				return true;
			}

		}
	}

	String reemplazaApos(String cadena)
	{
		if (cadena == null)
		{
			return cadena;
		}
		else
		{
			if (cadena.indexOf("'")<0)
			{
				return cadena;
			}
			else
			{
				int indice = 0;
				StringBuffer nuevo = new StringBuffer();
				while ( (cadena.indexOf("'", indice)) >= 0 ) {
					nuevo.append(cadena.substring(indice,cadena.indexOf("'", indice)+1));
					nuevo.append("'");
					indice = cadena.indexOf("'", indice)+1;
				}

				if(indice<=cadena.length())
					nuevo.append(cadena.substring(indice,cadena.length()));

				return nuevo.toString();
			}

		}
	}
	
	/*
	 * Inicio:jascencio:16/07/07
	 * CC:REGMOBCON-2006
	 */
	public ControllerResponse runBuscarPersonaNaturalSigcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null; 
		
		
		try
		{
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			
			StringBuffer criterioBusqueda=new StringBuffer();
			criterioBusqueda.append(req.getParameter("criterio"))
							.append("/flagmetodo=3");

			InputBusqIndirectaBean bean = Tarea.recojeDatosRequestBusqIndirectaPartidaSIGC(req);
			
			ConsultarNacionalIndicePartidasPersonaNaturalService consultarNacionalIndicePartidasPersonaNaturalService; 
			consultarNacionalIndicePartidasPersonaNaturalService=new ConsultarNacionalIndicePartidasPersonaNaturalServiceImpl(); 
			String ipOrigen=ControlAccesoIP.obtenerIPRemota(req);
			String idSession=sesion.getId();
			FormOutputBuscarPartida output;
			output=consultarNacionalIndicePartidasPersonaNaturalService.busquedaIndicePersonaNaturalSIGC(ConsultarNacionalIndicePartidasPersonaNaturalService.MEDIO_CONTROLLER, bean, usuario, ipOrigen, idSession);
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNaturalSigc&tipo=N");
			req.setAttribute("outnumber",bean.getCantidad());
			req.setAttribute("tarifa",output.getTarifa());
			req.setAttribute("usuaEtiq", usuario.getUserId());
			req.setAttribute("fechaAct", FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output",output);
			req.setAttribute("criterioBusqueda", criterioBusqueda.toString());
			req.setAttribute("flagCertBusq", bean.getCodGrupoLibroArea());
			req.setAttribute("flagVerifica", bean.getVerifica());
			req.setAttribute("rmc", "sigc");
			
			response.setStyle("resultadoPersonaNatural");
				


		}catch (ValidacionException e){
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e){
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
			{
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
			}
			else
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
			}
		}catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}
	
	public ControllerResponse runBuscarPersonaJuridicaSigcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null; 
		
		

		try
		{
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			
			StringBuffer criterioBusqueda=new StringBuffer();
			criterioBusqueda.append(req.getParameter("criterio"))
							.append("/flagmetodo=10");

			
			InputBusqIndirectaBean bean =Tarea.recojeDatosRequestBusqIndirectaPartidaSIGC(req);
			ConsultarNacionalIndicePartidasPersonaJuridicaService consultarNacionalIndicePartidasPersonaJurídicaService;
			consultarNacionalIndicePartidasPersonaJurídicaService=new ConsultarNacionalIndicePartidasPersonaJuridicaServiceImpl(); 
			
			String ipOrigen=ControlAccesoIP.obtenerIPRemota(req);
			String idSession=sesion.getId();
			FormOutputBuscarPartida output;
			output=consultarNacionalIndicePartidasPersonaJurídicaService.busquedaIndicePersonaJuridicaSIGC(ConsultarNacionalIndicePartidasPersonaJuridicaService.MEDIO_CONTROLLER, bean, usuario, ipOrigen, idSession);
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridicaSigc&tipo=J");
			req.setAttribute("outnumber",bean.getCantidad());
			req.setAttribute("tarifa",output.getTarifa());
			req.setAttribute("usuaEtiq", usuario.getUserId());
			req.setAttribute("fechaAct", FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output",output);
			req.setAttribute("criterioBusqueda", criterioBusqueda.toString());
			req.setAttribute("flagCertBusq", bean.getCodGrupoLibroArea());
			req.setAttribute("flagVerifica", bean.getVerifica());
			req.setAttribute("rmc", "sigc");
			response.setStyle("resultadoPersonaJuridica");
				


		}catch (ValidacionException e){
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e){
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
			{
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
			}
			else
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
			}
		}catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}
	
	public ControllerResponse runBuscarTipoNumeroDocumentoSigcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null; 
		
		
		try
		{
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			
			
			StringBuffer criterioBusqueda=new StringBuffer();
			criterioBusqueda.append(req.getParameter("criterio"))
							.append("/flagmetodo=10");

			InputBusqIndirectaBean bean =Tarea.recojeDatosRequestBusqIndirectaPartidaSIGC(req);
			ConsultarNacionalIndicePartidasxTipoNumeroDocumentoService  consultarNacionalIndicePartidasxTipoNumeroDocumentoService;
			consultarNacionalIndicePartidasxTipoNumeroDocumentoService=new ConsultarNacionalIndicePartidasxTipoNumeroDocumentoServiceImpl(); 
			
			String ipOrigen=ControlAccesoIP.obtenerIPRemota(req);
			String idSession=sesion.getId();
			FormOutputBuscarPartida output;
			output=consultarNacionalIndicePartidasxTipoNumeroDocumentoService.busquedaIndiceTipoNumeroDocumentoSIGC(ConsultarNacionalIndicePartidasxTipoNumeroDocumentoService.MEDIO_CONTROLLER, bean, usuario, ipOrigen, idSession);
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarTipoNumeroDocumentoSigc&tipo=D");
			req.setAttribute("outnumber",bean.getCantidad());
			req.setAttribute("tarifa",output.getTarifa());
			req.setAttribute("usuaEtiq", usuario.getUserId());
			req.setAttribute("fechaAct", FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output",output);
			req.setAttribute("criterioBusqueda", criterioBusqueda.toString());
			req.setAttribute("flagCertBusq", bean.getCodGrupoLibroArea());
			req.setAttribute("rmc", "sigc");
			response.setStyle("resultadoPersonaJuridica");
				


		}catch (ValidacionException e){
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e){
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
			{
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
			}
			else
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
			}
		}catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}
	public ControllerResponse runBuscarBienesSigcState(ControllerRequest request,
			ControllerResponse response)
		throws ControllerException
	{

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		HttpSession sesion = ExpressoHttpSessionBean.getSession(request);


		//obtener usuario de la sesion
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		InputBusqIndirectaBean inputBusqIndirectaBean=null; 
		
		
		try
		{
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			
			
			StringBuffer criterioBusqueda=new StringBuffer();
			criterioBusqueda.append(req.getParameter("criterio"))
							.append("/flagmetodo=10");

			
			InputBusqIndirectaBean bean =Tarea.recojeDatosRequestBusqIndirectaPartidaSIGC(req);
			ConsultarNacionalIndicePartidasxBienesService  consultarNacionalIndicePartidasxBienesService; 
			consultarNacionalIndicePartidasxBienesService=new ConsultarNacionalIndicePartidasxBienesServiceImpl(); 
			
			String ipOrigen=ControlAccesoIP.obtenerIPRemota(req);
			String idSession=sesion.getId();
			FormOutputBuscarPartida output;
			output=consultarNacionalIndicePartidasxBienesService.busquedaIndiceBienesSIGC(ConsultarNacionalIndicePartidasxBienesService.MEDIO_CONTROLLER ,bean, usuario, ipOrigen, idSession);
			output.setAction("/iri/BuscaPartidasXIndicesIRI.do?state=buscarBienesSigc&tipo=B");
			req.setAttribute("outnumber",bean.getCantidad());
			req.setAttribute("tarifa",output.getTarifa());
			req.setAttribute("usuaEtiq", usuario.getUserId());
			req.setAttribute("fechaAct", FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output",output);
			req.setAttribute("criterioBusqueda", criterioBusqueda.toString());
			req.setAttribute("flagCertBusq", bean.getCodGrupoLibroArea());
			req.setAttribute("rmc", "sigc");
			
			/*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", bean.getVerifica());
			/*fin:dbravo:14/09/2007*/
			
			response.setStyle("resultadoPersonaJuridica");
		
		}catch (ValidacionException e){
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}
		catch (CustomException e){
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
			{
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
			}
			else
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
			}
		}catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}
	/*
	 * Fin:jascencio
	 */
	
} //fin de clase