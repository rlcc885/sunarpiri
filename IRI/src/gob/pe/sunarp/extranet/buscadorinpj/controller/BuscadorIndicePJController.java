package gob.pe.sunarp.extranet.buscadorinpj.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringTokenizer;

import gob.pe.sunarp.extranet.buscadorinpj.beans.ResulBuscadorPJBean;
import gob.pe.sunarp.extranet.buscadorinpj.motor.MotorBusqueda;
import gob.pe.sunarp.extranet.buscadorinpj.util.UtilBuscador;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.dbobj.DboTransaccion;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Saldo;
import gob.pe.sunarp.extranet.util.Tarea;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tools.ant.taskdefs.Sync.MyCopy;

import com.jcorporate.expresso.core.controller.Controller;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class BuscadorIndicePJController extends ControllerExtension {

	public static int longitud;

	// MotorBusqueda motor = new MotorBusqueda();
	public static int paginacion = 15;

	public enum tipoBusqueda {
		razon_soc, deno;
	}

	public BuscadorIndicePJController() {
		super();
		addState(new State("buscar",
				"Recibe la cadena de busqueda para procesarla"));
		addState(new State("formBusqueda",
				"Recibe la cadena de busqueda para procesarla"));
		addState(new State("paginandoAdelante",
				"Recibe parametros de paginacion"));
		addState(new State("paginandoAtras", "Recibe parametros de paginacion"));

		setInitialState("formBusqueda");
	}

	protected ControllerResponse runFormBusquedaState(
			ControllerRequest request, ControllerResponse response)
			throws ControllerException {

		try {
			init(request);
			validarSesion(request);
			response.setStyle("formBusqueda");

		} catch (CustomException e) {
			// log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			// rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			// rollback(conn, request);
			response.setStyle("error");
		} finally {
			// JDBC.getInstance().closeResultSet(rsetGla);
			// JDBC.getInstance().closeStatement(pstmt);
			// pool.release(conn);
			end(request);
		}
		return response;

	}

	protected ControllerResponse runBuscarState(ControllerRequest request,
			ControllerResponse response) throws Exception {
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		Connection conn = null;
		DBConnectionFactory pool = null;
		MotorBusqueda.setMotorBusqueda(null);
		MotorBusqueda motor = MotorBusqueda.getInstance();
		try {
			init(request);
			validarSesion(request);
			
			ArrayList lista = new ArrayList();
			ArrayList listaPaginada = new ArrayList();
			String cs = null;
			String cadena = null;
			UsuarioBean usuario = ExpressoHttpSessionBean
					.getUsuarioBean(request);
			String varSiguiente = null;
			String varAnterior = null;
			String varTemp = request.getParameter("rb");
			if (("r").equalsIgnoreCase(varTemp)) { 
				cs = req.getParameter("txtRazSoc").toLowerCase();
				String[] splitCadena = UtilBuscador.armaCadenaPharseQuery(cs);
				motor.setCriterioBusqueda(cs);
				motor.setListaResultadoTotal(lista);
				motor.setTipoBusqueda("R");
				motor.setCadenaBusqueda(splitCadena);
				lista = motor.buscaLuceneArchivo();
				if (motor.getTotalResultadosLucene() > 20) {
					motor.setFinPaginacion(20);
					motor.setInicioPaginacion(0);
					listaPaginada = motor.runQueryAndDisplayResultsPag(motor.getDirectorio(), motor.getB());
					varSiguiente = "si";
					varAnterior = "no";
					req.setAttribute("recuperados", listaPaginada);
					req.setAttribute("inicio", String.valueOf(motor
							.getFinPaginacion()));
				} else {
					varSiguiente = "no";
					varAnterior = "no";
					req.setAttribute("recuperados", lista);
				}

			} else { 
				cs = req.getParameter("txtSiglas").toLowerCase();
				motor.setCriterioBusqueda(cs);
				String[] splitCadena = UtilBuscador.armaCadenaPharseQuery(cs); 
				motor.setCriterioBusqueda(cs);
				motor.setListaResultadoTotal(lista);
				motor.setTipoBusqueda("S");
				motor.setCadenaBusqueda(splitCadena);
				lista = motor.buscaLuceneArchivo();
				if (motor.getTotalResultadosLucene() > 20) {
					motor.setFinPaginacion(20);
					motor.setInicioPaginacion(0);
					listaPaginada = motor.runQueryAndDisplayResultsPag(motor.getDirectorio(), motor.getB());
					varSiguiente = "si";
					varAnterior = "no";
					req.setAttribute("recuperados", listaPaginada);
					req.setAttribute("inicio", String.valueOf(motor
							.getFinPaginacion()));// String.valueOf(motor.getFin())

				} else {
					varSiguiente = "no";
					varAnterior = "no";
					req.setAttribute("recuperados", lista);
				}
			}
			req.setAttribute("total", String.valueOf(motor
					.getTotalResultadosLucene()));
			req.setAttribute("siguiente", varSiguiente);
			req.setAttribute("anterior", varAnterior);
			req.setAttribute("criterio", motor.getCriterioBusqueda());
			if ("S".equals(motor.getTipoBusqueda()))
				req.setAttribute("tipoBusqueda", "Por Siglas");
			else
				req.setAttribute("tipoBusqueda", "Por Razon Social");
			
			req.setAttribute("refresca", "si");
			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();
			DBConnection myConn = new DBConnection(conn);
			myConn.setAutoCommit(false);
			int tarifa =Tarea.getTarifa(myConn, 198);
			PrepagoBean prep= new PrepagoBean();
			String tmp =registrTransaccionDsctoSaldo(myConn, usuario, prep,motor.getCriterioBusqueda());
			if(usuario.getExonPago())
			motor.setCostoServicio("0");
			else
				motor.setCostoServicio(tmp);
			
			req.setAttribute("costo", motor.getCostoServicio());
			response.setStyle("resulBusqueda");

		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			// rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			ex.getMessage();
			ex.printStackTrace();
			response.setStyle("error");
		} finally {
			// JDBC.getInstance().closeResultSet(rsetGla);
			// JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		return response;

	}

	public  String registrTransaccionDsctoSaldo(DBConnection myConn,
			UsuarioBean user, PrepagoBean prep, String cadBusqueda) throws DBException,
			CustomException {

		Double costo_servicio = null;
		try {
			int transId = 0;
			transId = Integer.valueOf(String.valueOf(Secuenciales.getInstance()
					.getIDTransaccion(myConn)));

			DboTransaccion transac = new DboTransaccion();
			transac.setConnection(myConn);
			transac.setField(transac.CAMPO_TRANS_ID, transId);
			transac.setField(transac.CAMPO_SERVICIO_ID, 198);
			transac.setField(transac.CAMPO_COD_GRUPO_LIBRO_AREA, 10);
			transac.setField(transac.CAMPO_CUENTA_ID, user.getCuentaId());
			transac.setField(transac.CAMPO_FEC_HOR, FechaUtil
					.dateTimeToStringToOracle(new Timestamp(System
							.currentTimeMillis())));
			// Double costo_servicio =
			// Double.parseDouble(deno.getMonto().toString());
			costo_servicio = Double.parseDouble(Tarea.getTarifa(myConn,
					198).toString());
			if (!user.getExonPago())
			transac.setField(transac.CAMPO_COSTO, Double
					.toString(costo_servicio));
			else
				transac.setField(transac.CAMPO_COSTO, Double
						.toString(0));
			transac.setField(transac.CAMPO_IP, "LOCALHOST");
			transac.setField(transac.CAMPO_SESION_ID, "");
			transac.setField(transac.CAMPO_TIPO_USR, "1");
			transac.setField(transac.CAMPO_STR_BUSQ, cadBusqueda);
			transac.setField(transac.CAMPO_REG_PUB_ID, user.getRegPublicoId());
			transac.setField(transac.CAMPO_OFIC_REG_ID, user
					.getOficRegistralId());
			transac.add();
			
			if (!user.getExonPago()){
				prep.setTransacId(transId);
				prep.setMontoBruto(costo_servicio);
				prep.setUsuario(user.getUserId());
				prep.setLineaPrepagoId(user.getLinPrePago());
				LineaPrepago lineaCmd = new LineaPrepago();
				lineaCmd.reduceSaldo(user, prep, myConn);
			}
			myConn.commit();
			
			
		} catch (NumberFormatException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		return costo_servicio.toString();
	}

	protected ControllerResponse runPaginandoAdelanteState(
			ControllerRequest request, ControllerResponse response)
			throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			//init(request);
			validarSesion(request);
			ArrayList lista = new ArrayList();
			int valor = Integer.parseInt(req.getParameter("inicio"));
			MotorBusqueda motorcito = MotorBusqueda.getInstance();
			motorcito.setInicioPaginacion(valor);
			motorcito.setFinPaginacion(valor + 20);
			String varSig = null;
			String varAnt = null;
			//lista = motorcito.paginacion();
			lista = motorcito.runQueryAndDisplayResultsPag(motorcito.getDirectorio(), motorcito.getB());
			int longTotal = motorcito.getTotalResultadosLucene();

			if (longTotal > 20 && motorcito.getFinPaginacion() < longTotal)
				varSig = "si";
			else
				varSig = "no";
			if (valor >= 20 && valor <= longTotal)
				varAnt = "si";
			else
				varAnt = "no";
			
			//motorcito.runQueryAndDisplayResults(indexStore, q);
			req.setAttribute("recuperados", lista);
			req.setAttribute("total", motorcito.getTotalResultadosLucene());
			req.setAttribute("inicio", String.valueOf(motorcito
					.getFinPaginacion()));
			req.setAttribute("fin", String
					.valueOf(motorcito.getFinPaginacion() - 20));
			req.setAttribute("anterior", varAnt);
			req.setAttribute("siguiente", varSig);
			req.setAttribute("criterio", motorcito.getCriterioBusqueda());
			if ("S".equals(motorcito.getTipoBusqueda()))
				req.setAttribute("tipoBusqueda", "Por Siglas");
			else
				req.setAttribute("tipoBusqueda", "Por Razon Social");
			
			req.setAttribute("refresca", "no");
			req.setAttribute("costo", motorcito.getCostoServicio());
			response.setStyle("resulBusqueda");
		} catch (CustomException e) {
			// log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			// rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			// rollback(conn, request);
			response.setStyle("error");
		} finally {
			// JDBC.getInstance().closeResultSet(rsetGla);
			// JDBC.getInstance().closeStatement(pstmt);
			// pool.release(conn);
			end(request);
		}
		return response;

	}

	protected ControllerResponse runPaginandoAtrasState(
			ControllerRequest request, ControllerResponse response)
			throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			String varSig = null;
			String varAnt = null;
			//init(request);
			validarSesion(request);
			ArrayList lista = new ArrayList();
			MotorBusqueda motorcito = MotorBusqueda.getInstance();
			int valor = Integer.parseInt(req.getParameter("inicio"));
			int resto;
			int numValida;
			resto = valor % 20;
			if (resto != 0) {
				numValida = valor / 20;
				motorcito.setFinPaginacion((numValida + 1) * 20);
				motorcito.setInicioPaginacion(((numValida + 1) * 20) - 20);
			} else {
				motorcito.setFinPaginacion(valor);
				motorcito.setInicioPaginacion(valor - 20);
			}
			if (motorcito.getInicioPaginacion() == 0) {
				varAnt = "no";
				varSig = "si";
			} else {
				varAnt = "si";
				varSig = "si";

			}

			//lista = motorcito.paginacion();
			lista = motorcito.runQueryAndDisplayResultsPag(motorcito.getDirectorio(), motorcito.getB());
			req.setAttribute("recuperados", lista);
			req.setAttribute("total", motorcito.getTotalResultadosLucene());
			req.setAttribute("inicio", String.valueOf(motorcito
					.getInicioPaginacion()));
			req.setAttribute("fin", String.valueOf(motorcito
					.getInicioPaginacion() - 20));
			req.setAttribute("anterior", varAnt);
			req.setAttribute("siguiente", varSig);
			req.setAttribute("criterio", motorcito.getCriterioBusqueda());
			if ("S".equals(motorcito.getTipoBusqueda()))
				req.setAttribute("tipoBusqueda", "Por Siglas");
			else
				req.setAttribute("tipoBusqueda", "Por Razon Social");

			req.setAttribute("refresca", "no");
			req.setAttribute("costo", motorcito.getCostoServicio());
			response.setStyle("resulBusqueda");
		} catch (CustomException e) {
			// log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			// rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			// rollback(conn, request);
			response.setStyle("error");
		} finally {
			// JDBC.getInstance().closeResultSet(rsetGla);
			// JDBC.getInstance().closeStatement(pstmt);
			// pool.release(conn);
			end(request);
		}
		return response;

	}
}
