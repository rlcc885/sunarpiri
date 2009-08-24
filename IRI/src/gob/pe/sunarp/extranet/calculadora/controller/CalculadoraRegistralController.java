package gob.pe.sunarp.extranet.calculadora.controller;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;
import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.calculadora.bean.*;

public class CalculadoraRegistralController extends ControllerExtension {


	public CalculadoraRegistralController() {
		super();
		addState(new State("mostrarDatosInicio", "Datos Inicio"));
		addState(new State("mostrarDatosCalculadora", "Datos Calculadora"));
		addState(new State("obtenerLibro", "Obtener Libro"));
		addState(new State("obtenerActos", "Obtener Actos"));
		addState(new State("mostrarCalculo", "Mostrar Calculo"));
		addState(new State("aceptarCalculo", "Aceptar Calculo"));
		addState(new State("aceptarCalculoPrincipal", "Aceptar Calculo Principal"));
		setInitialState("mostrarDatosInicio");
	}

	protected ControllerResponse runAceptarCalculoPrincipalState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);

		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			String codArea = request.getParameter("cboArea");
			String codLibro = request.getParameter("cboLibro");
					
			ArrayList listaActos = (ArrayList)session.getAttribute("listaActos");

			String[] listaIndiceSeleccionados = req.getParameterValues("chkActo");
			int size2=0;
			if (listaIndiceSeleccionados!=null)
				size2 = listaIndiceSeleccionados.length;
			String indice = null;
			ActoCalculadoraRegistral acto = null;
			double totalPresentacion = 0;
			double totalInscripcion = 0;
			double totalDerechoPagar = 0;
			for (int i=0; i<size2; i++){
				indice = (String)listaIndiceSeleccionados[i];
				acto = (ActoCalculadoraRegistral)listaActos.get(Integer.parseInt(indice));
				acto.setSeleccionado(true);
				totalPresentacion = Math.round( (totalPresentacion + Double.parseDouble(acto.getMontoPresentacion()))*Double.parseDouble("100"))/Double.parseDouble("100");
				totalInscripcion = Math.round( (totalInscripcion + Double.parseDouble(acto.getMontoInscripcion()))*Double.parseDouble("100"))/Double.parseDouble("100");
				totalDerechoPagar = Math.round((totalPresentacion + totalInscripcion)*Double.parseDouble(acto.getNroVeces())*Double.parseDouble("100"))/Double.parseDouble("100");
			}

			req.setAttribute("totalPresentacion", String.valueOf(totalPresentacion));
			req.setAttribute("totalInscripcion", String.valueOf(totalInscripcion));
			req.setAttribute("totalDerechoPagar", String.valueOf(totalDerechoPagar));
			
			req.setAttribute("codArea",codArea);
			req.setAttribute("codLibro",codLibro);
			
			response.setStyle("datosCalculadora");
		
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}

	protected ControllerResponse runAceptarCalculoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);

		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			String codArea = request.getParameter("hidCodArea");
			String codLibro = request.getParameter("hidCodLibro");
					
			ArrayList listaActos = (ArrayList)session.getAttribute("listaActos");
			String indice = request.getParameter("hidIndice");
			
			
			ActoCalculadoraRegistral acto = (ActoCalculadoraRegistral)listaActos.get(Integer.parseInt(indice));
			double total; 
			double montoInscripcion;
			double derechoInscripcion;
			double div;
			if (acto.getInFrml().equals("S")) {
			
				session.setAttribute("tipoCambioDia", request.getAttribute("tipoCambio"));
				acto.setNroVeces(request.getParameter("txtNroVeces"));
				derechoInscripcion = Double.parseDouble((String)request.getParameter("txtDerechoInscripcion"));
				div = derechoInscripcion/Double.parseDouble(acto.getNroVeces());
				montoInscripcion = Math.round( div * Double.parseDouble("100"))/Double.parseDouble("100");
				acto.setMontoInscripcion(String.valueOf(montoInscripcion));
				total = Math.round((Double.parseDouble(acto.getMontoPresentacion()) + Double.parseDouble(acto.getMontoInscripcion()) ) * Double.parseDouble(acto.getNroVeces()) * Double.parseDouble("100"))/Double.parseDouble("100");
				acto.setTotal(String.valueOf(total));
				acto.setCodigoMoneda(request.getParameter("cboMoneda"));
				acto.setDescripcionMoneda(request.getParameter("hidMoneda"));
				acto.setTransaccionSoles(request.getParameter("txtTranSoles"));
				acto.setTransaccionDolares(request.getParameter("txtTranDolar"));
			}
			else {
				acto.setNroVeces(request.getParameter("txtNroVeces"));
				total = Math.round( ( Double.parseDouble(acto.getMontoPresentacion()) + Double.parseDouble(acto.getMontoInscripcion()) ) * Double.parseDouble(acto.getNroVeces()) * Double.parseDouble("100"))/Double.parseDouble("100");
				acto.setTotal(String.valueOf(total));
			}
			
			req.setAttribute("codArea",codArea);
			req.setAttribute("codLibro",codLibro);
			
			response.setStyle("datosCalculadora");
		
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}
	
	protected ControllerResponse runMostrarCalculoState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);

		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			String codArea = request.getParameter("cboArea");
			String codLibro = request.getParameter("cboLibro");
					
			ArrayList listaActos = (ArrayList)session.getAttribute("listaActos");
			String indice = request.getParameter("hidIndice");
			req.setAttribute("indice",indice);
			
			ActoCalculadoraRegistral acto = (ActoCalculadoraRegistral)listaActos.get(Integer.parseInt(indice));

			if (acto.getInFrml().equals("S")) {
			
				req.setAttribute("tasa",acto.getFrml());
				req.setAttribute("moneda",acto.getCodigoMoneda());
				req.setAttribute("tipoCambio", (String)session.getAttribute("tipoCambioDia"));
				req.setAttribute("uit", (String)session.getAttribute("montoUIT"));
				req.setAttribute("nroveces", acto.getNroVeces());
				req.setAttribute("valorMinimo", acto.getValorMinimo());
				req.setAttribute("montoSoles", acto.getTransaccionSoles());
				req.setAttribute("montoDolar", acto.getTransaccionDolares());
				req.setAttribute("montoDerecho", acto.getMontoInscripcion());
				
				response.setStyle("datosCalculoVariable");
			
			}
			else {
				req.setAttribute("nroveces", acto.getNroVeces());
				response.setStyle("datosCalculoFijo");
				
			}
			
			req.setAttribute("codArea",codArea);
			req.setAttribute("codLibro",codLibro);
			
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}
	
	protected ControllerResponse runMostrarDatosInicioState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);

		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);
		
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosInicio");
		
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		finally {
			end(request);
		}
		
		return response;
	}

	protected ControllerResponse runMostrarDatosCalculadoraState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			session.removeAttribute("arrLibro");
			session.removeAttribute("listaActos");

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			String tipoCambio = request.getParameter("txtTipoCambioDia");
			String uit = request.getParameter("txtMontoUIT");			
			
			double tipoCambioDouble = Math.round(Double.parseDouble(tipoCambio)*100)/100;
			double uitDouble = Math.round(Double.parseDouble(uit)*100)/100;
			
			session.setAttribute("tipoCambioDia", String.valueOf(tipoCambio));
			session.setAttribute("montoUIT", String.valueOf(uit));
			
			// Areas
			ArrayList resultadoAreas = new ArrayList();
			DboTmAreaRegistralCal dboTmAreaRegistralCal = new DboTmAreaRegistralCal(dconn);
			dboTmAreaRegistralCal.setFieldsToRetrieve(DboTmAreaRegistralCal.CAMPO_AREA_REG_ID + "|" + DboTmAreaRegistralCal.CAMPO_NOMBRE);
			dboTmAreaRegistralCal.setField(DboTmAreaRegistralCal.CAMPO_ESTADO, "1");
			ArrayList arrx = dboTmAreaRegistralCal.searchAndRetrieveList(DboTmAreaRegistralCal.CAMPO_NOMBRE);
			for (int i = 0; i < arrx.size(); i++) 
			{
				ComboBean bean1 = new ComboBean();
				bean1.setCodigo(((DboTmAreaRegistralCal) arrx.get(i)).getField(DboTmAreaRegistralCal.CAMPO_AREA_REG_ID));
				bean1.setDescripcion(((DboTmAreaRegistralCal) arrx.get(i)).getField(DboTmAreaRegistralCal.CAMPO_NOMBRE));
				resultadoAreas.add(bean1);
			}
			
			session.setAttribute("arrArea",resultadoAreas);
			
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosCalculadora");
		
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		finally {
			//SE AGREGA EL CIERRE DE LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);			
			end(request);
		}		
		return response;
	}

	protected ControllerResponse runObtenerLibroState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			session.removeAttribute("listaActos");
			
			String codArea = request.getParameter("cboArea");

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
		
			// Libros
			ArrayList resultadoLibros = new ArrayList();
			DboTmLibroCal dboTmLibroCal = new DboTmLibroCal(dconn);
			dboTmLibroCal.setFieldsToRetrieve(DboTmLibroCal.CAMPO_COD_LIBRO + "|" + DboTmLibroCal.CAMPO_DESCRIPCION);
			dboTmLibroCal.setField(DboTmLibroCal.CAMPO_AREA_REG_ID, codArea);
			dboTmLibroCal.setField(DboTmLibroCal.CAMPO_ESTADO, "1");
			ArrayList arrx = dboTmLibroCal.searchAndRetrieveList(DboTmLibroCal.CAMPO_DESCRIPCION);
			for (int i = 0; i < arrx.size(); i++) 
			{
				ComboBean bean1 = new ComboBean();
				bean1.setCodigo(((DboTmLibroCal) arrx.get(i)).getField(DboTmLibroCal.CAMPO_COD_LIBRO));
				bean1.setDescripcion(((DboTmLibroCal) arrx.get(i)).getField(DboTmLibroCal.CAMPO_DESCRIPCION));
				resultadoLibros.add(bean1);
			}

			session.setAttribute("arrLibro",resultadoLibros);
			req.setAttribute("codArea",codArea);
			
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosCalculadora");
		
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		finally {
			//SE AGREGA EL CIERRE DE LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);			
			end(request);
		}
		
		return response;
	}

	protected ControllerResponse runObtenerActosState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		try {			

			init(request);
			validarSesion(request);

			req = ExpressoHttpSessionBean.getRequest(request);

			String codArea = request.getParameter("cboArea");
			String codLibro = request.getParameter("cboLibro");

			// Obtiene conexion del pool
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			ActoCalculadoraRegistral bean1 = null;
		
			
			ArrayList resultadoActos = new ArrayList();
			DboTPAranCal dboTPAranCal = new DboTPAranCal(dconn);
			DboTAAranCal dboTAAranCal = new DboTAAranCal(dconn);

			// UIT
			dboTPAranCal.setFieldsToRetrieve(DboTPAranCal.CAMPO_ID_ARAN + "|" + DboTPAranCal.CAMPO_VA_UIT);	
			dboTPAranCal.setField(DboTPAranCal.CAMPO_IN_ESTD, "A");
			ArrayList arrx1 = dboTPAranCal.searchAndRetrieveList(DboTPAranCal.CAMPO_ID_ARAN);
			String valorUIT = ((DboTPAranCal) arrx1.get(0)).getField(DboTPAranCal.CAMPO_VA_UIT);
			String idAran = ((DboTPAranCal) arrx1.get(0)).getField(DboTPAranCal.CAMPO_ID_ARAN);
			
			
			// Actos
			DboTPActoRgstCal dboTPActoRgstCal = new DboTPActoRgstCal(dconn);
			dboTPActoRgstCal.setFieldsToRetrieve(DboTPActoRgstCal.CAMPO_CO_ACTO_GRAL + "|" + DboTPActoRgstCal.CAMPO_DE_ACTO_RGST + "|" + DboTPActoRgstCal.CAMPO_CO_TASA);
			dboTPActoRgstCal.setField(DboTPActoRgstCal.CAMPO_CO_AREA, codArea);
			dboTPActoRgstCal.setField(DboTPActoRgstCal.CAMPO_CO_LIBR, codLibro);			
			dboTPActoRgstCal.setField(DboTPActoRgstCal.CAMPO_IN_ESTD, "A");
			ArrayList arrx2 = dboTPActoRgstCal.searchAndRetrieveList(DboTPActoRgstCal.CAMPO_DE_ACTO_RGST);
			for (int i = 0; i < arrx2.size(); i++) 
			{
				bean1 = new ActoCalculadoraRegistral();
				bean1.setCodigoActo(((DboTPActoRgstCal) arrx2.get(i)).getField(DboTPActoRgstCal.CAMPO_CO_ACTO_GRAL));
				bean1.setDescripcionActo(((DboTPActoRgstCal) arrx2.get(i)).getField(DboTPActoRgstCal.CAMPO_DE_ACTO_RGST));
				bean1.setCodigoTasa(((DboTPActoRgstCal) arrx2.get(i)).getField(DboTPActoRgstCal.CAMPO_CO_TASA));

				bean1.setValorUIT(valorUIT);
				bean1.setIdAran(idAran);

				// Actos - monto Presentacion
				dboTAAranCal.clearAll();
				dboTAAranCal.setFieldsToRetrieve(DboTAAranCal.CAMPO_MO_CPTO);
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_REGI, "01");
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_OFIC_RGST, "01");
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_TASA, bean1.getCodigoTasa());
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_CPTO, "01"); /*----*/
				dboTAAranCal.setField(DboTAAranCal.CAMPO_ID_ARAN, bean1.getIdAran());
				dboTAAranCal.setField(DboTAAranCal.CAMPO_IN_ESTD, "A");
				ArrayList arrx3 = dboTAAranCal.searchAndRetrieveList();
				bean1.setMontoPresentacion(((DboTAAranCal) arrx3.get(0)).getField(DboTAAranCal.CAMPO_MO_CPTO));

				// Actos - monto Inscripcion
				dboTAAranCal.clearAll();
				dboTAAranCal.setFieldsToRetrieve(DboTAAranCal.CAMPO_MO_CPTO + "|" + DboTAAranCal.CAMPO_VALOR_MIN + "|" + DboTAAranCal.CAMPO_IN_FRML + "|" + DboTAAranCal.CAMPO_FRML);
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_REGI, "01");
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_OFIC_RGST, "01");
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_TASA, bean1.getCodigoTasa());
				dboTAAranCal.setField(DboTAAranCal.CAMPO_CO_CPTO, "02"); /*----*/
				dboTAAranCal.setField(DboTAAranCal.CAMPO_ID_ARAN, bean1.getIdAran());
				dboTAAranCal.setField(DboTAAranCal.CAMPO_IN_ESTD, "A");
				ArrayList arrx4 = dboTAAranCal.searchAndRetrieveList();
				bean1.setMontoInscripcion(((DboTAAranCal) arrx4.get(0)).getField(DboTAAranCal.CAMPO_MO_CPTO));
				bean1.setValorMinimo(((DboTAAranCal) arrx4.get(0)).getField(DboTAAranCal.CAMPO_VALOR_MIN));
				bean1.setInFrml(((DboTAAranCal) arrx4.get(0)).getField(DboTAAranCal.CAMPO_IN_FRML));
				bean1.setFrml(((DboTAAranCal) arrx4.get(0)).getField(DboTAAranCal.CAMPO_FRML));
			
				if (bean1.getInFrml().equals("S"))
					bean1.setMontoInscripcion("0.00");
		
				bean1.setNroVeces("1");
				bean1.setTotal(String.valueOf(Double.parseDouble(bean1.getMontoPresentacion()) + Double.parseDouble(bean1.getMontoInscripcion())));
				resultadoActos.add(bean1);
			}	
			
			req.setAttribute("codArea",codArea);
			req.setAttribute("codLibro",codLibro);
			session.setAttribute("listaActos",resultadoActos);
			
			// Direccionamos a la pagina de Datos de Reserva	
			response.setStyle("datosCalculadora");
		
		} 
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} 
		finally {
			//SE AGREGA EL CIERRE DE LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);			
			end(request);
		}
		
		return response;
	}
		
}

