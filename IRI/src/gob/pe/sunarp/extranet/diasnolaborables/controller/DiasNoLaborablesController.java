package gob.pe.sunarp.extranet.diasnolaborables.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;

import gob.pe.sunarp.extranet.caja.bean.ConsolidadoExtornoBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboDiaNoLabo;
import gob.pe.sunarp.extranet.dbobj.DboExtorno;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboTPHojaPres;
import gob.pe.sunarp.extranet.dbobj.DboTaDiarCaja;
import gob.pe.sunarp.extranet.diasnolaborables.bean.DiasNoLaborables;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.Tarea;

/**
 * @author jbugarin
 *
 */
public class DiasNoLaborablesController extends ControllerExtension {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DiasNoLaborablesController() {
		super();
	
		addState(new State("muestraFormulario", "me lleva al form de ingreso de dias no laborables"));
		addState(new State("grabarDia", "Graba el dia ingresado en el formulario"));
		addState(new State("muestraFiltroConsulta", "muestra el filtro para buscar los dias no laborables"));
		addState(new State("buscarDiasNoLabo", "consulta los dias no laborables"));
		setInitialState("muestraFormulario");
		
	}
	
	public ControllerResponse runMuestraFormularioState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException 
		{
			
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
						
			try
			{
				init(request);
				validarSesion(request);
				/** envio de datos para el combo de fechas **/
				req.setAttribute("arrDays", FechaUtil.getReportDays());
				req.setAttribute("arrMonths", FechaUtil.getReportMonths());
				req.setAttribute("arrYears", FechaUtil.getReportYears());
				/** fin envio de datos **/
				//direcciona al jsp a mostrar
				response.setStyle("muestraFormulario");
				
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle(e.getForward());
			} catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				end(request);
			}
			
			return response;
		}

	public ControllerResponse runGrabarDiaState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException, SQLException 
		{
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			Statement stmt   = null;
			ResultSet rset   = null;
			
			try
			{
				init(request);
				validarSesion(request);
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
				/** recupero los datos del Formulario **/
				String dia = request.getParameter("dia");
				String mes = request.getParameter("mes");
				String anio = request.getParameter("anio");
				String fechaInicio = FechaUtil.stringTimeToOracleString(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(anio), 0, 0, 0);
				/** fecha y hora del registro**/
				String fecha_actual = FechaUtil.getCurrentDateTime();
				String fecha_hora = fecha_actual.substring(0,2)+
							 fecha_actual.substring(3,5)+
							 fecha_actual.substring(6,10)+
							 fecha_actual.substring(11,13)+
							 fecha_actual.substring(14,16)+
							 fecha_actual.substring(17,19);
				String dias = fecha_hora.substring(0,2);
				String meses = fecha_hora.substring(2,4);
				String anho = fecha_hora.substring(4,8);
				String hh = fecha_hora.substring(8,10);
				String mm = fecha_hora.substring(10,12);
				String ss = fecha_hora.substring(12,14);
				//tablaTPHojaPres.setField(DboTPHojaPres.CAMPO_TS_HOJA_PRES,FechaUtil.stringTimeToOracleString(dia+"/"+mes+"/"+anho+" "+hh+":"+mm+":"+ss));
				
				String oficina = request.getParameter("cboOficinas");
				String regPubId =oficina.substring(0,2);
				String ofic = oficina.substring(3, 5);
				String estado = request.getParameter("cboEstado");
				String descripcion = request.getParameter("txtDesc");
				String docSustento = request.getParameter("txtDocSust");
				UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
				DboDiaNoLabo diaNoLabo = new DboDiaNoLabo(dconn);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_REG_PUB_ID, regPubId);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_OFIC_REG_ID, ofic);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_DIA,fechaInicio);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_DESCRIPCION, descripcion);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_DOCU_SUST, docSustento);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_ESTADO, estado);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_ID_USUA_CREA, usuario.getUserId());
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_TS_USUA_CREA, FechaUtil.stringTimeToOracleString(dias+"/"+meses+"/"+anho+" "+hh+":"+mm+":"+ss));
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_ID_USUA_MODI, null);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_TS_USUA_MODI, null);
				diaNoLabo.add();
				conn.commit();
				diaNoLabo.clearAll();
				/**  **/
				//direcciona al jsp a mostrar
				response.setStyle("muestraConfirmacion");
				
				
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle(e.getForward());
			} catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeStatement(stmt);
				pool.release(conn);			
				end(request);
			}
			
			return response;
		}
	
	public ControllerResponse runMuestraFiltroConsultaState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException 
		{
			
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
						
			try
			{
				init(request);
				validarSesion(request);
				/** envio de datos para el combo de fechas **/
				req.setAttribute("arrDays", FechaUtil.getReportDays());
				req.setAttribute("arrMonths", FechaUtil.getReportMonths());
				req.setAttribute("arrYears", FechaUtil.getReportYears());
				/** fin envio de datos **/
				//direcciona al jsp a mostrar
				response.setStyle("muestraFiltro");
				
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle(e.getForward());
			} catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				end(request);
			}
			
			return response;
		}
	
	public ControllerResponse runBuscarDiasNoLaboState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException, SQLException 
		{
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			Statement stmt   = null;
			ResultSet rset   = null;
			
			try
			{
				init(request);
				validarSesion(request);
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
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
				String regPubId =oficina.substring(0,2);
				String ofic = oficina.substring(3, 5);
				
				/** REALIZANDO CONSULTA A LA BD Y LO RECUPERO EN ARRAYLIST**/
				ArrayList listaDias = new ArrayList();
				ArrayList<DiasNoLaborables> listaDiasPresentacion = new ArrayList<DiasNoLaborables>();
				DboDiaNoLabo diaNoLabo = new DboDiaNoLabo(dconn);
				diaNoLabo.setFieldsToRetrieve(DboDiaNoLabo.CAMPO_DIA + "|"+ DboDiaNoLabo.CAMPO_DESCRIPCION + "|" + DboDiaNoLabo.CAMPO_DOCU_SUST 
					+ "|" + DboDiaNoLabo.CAMPO_ESTADO);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_REG_PUB_ID, regPubId);
				diaNoLabo.setField(DboDiaNoLabo.CAMPO_OFIC_REG_ID, ofic);
				diaNoLabo.setAppendWhereClause(DboDiaNoLabo.CAMPO_DIA + " BETWEEN " + fechaInicio + " AND " + fechaFin + " ORDER BY " + DboDiaNoLabo.CAMPO_DIA);
				listaDias = diaNoLabo.searchAndRetrieveList();
				
				for(Iterator i = listaDias.iterator(); i.hasNext();){
					DboDiaNoLabo diaNoLaboAux = (DboDiaNoLabo)i.next();
					DiasNoLaborables dias = new DiasNoLaborables();
					dias.setDia(diaNoLaboAux.getField(DboDiaNoLabo.CAMPO_DIA).toString().substring(0, 10));
					dias.setDescripcion(diaNoLaboAux.getField(DboDiaNoLabo.CAMPO_DESCRIPCION));
					dias.setDocuSustento(diaNoLaboAux.getField(DboDiaNoLabo.CAMPO_DOCU_SUST));
					dias.setEstado(diaNoLaboAux.getField(DboDiaNoLabo.CAMPO_ESTADO));
					listaDiasPresentacion.add(dias);
					
				}
				diaNoLabo.clearAll();
				req.setAttribute("listaDias", listaDiasPresentacion);
				//direcciona al jsp a mostrar
				response.setStyle("muestraResultados");
				
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle(e.getForward());
			} catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeStatement(stmt);
				pool.release(conn);			
				end(request);
			}
			
			return response;
		}
	
}
