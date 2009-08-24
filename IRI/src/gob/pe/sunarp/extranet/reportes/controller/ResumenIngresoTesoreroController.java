/*
 * Created on 11-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.reportes.controller;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.reportes.beans.IngresoBean;
import gob.pe.sunarp.extranet.reportes.beans.ReporteNoRegistroException;
import gob.pe.sunarp.extranet.reportes.beans.VerAbonoBean;
import gob.pe.sunarp.extranet.util.*;
import java.io.PrintWriter;
import java.sql.*;
import java.util.StringTokenizer;
import gob.pe.sunarp.extranet.pool.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jbugarin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ResumenIngresoTesoreroController extends ControllerExtension {

	public ResumenIngresoTesoreroController() {
			super();
			addState(new State("verFormulario", "Muestra la ventana de Busqueda por fechas y Resultados"));
			addState(new State("buscaMuestraResultados", "Muestra Los Resultados"));
			setInitialState("verFormulario");
		}
	
	protected ControllerResponse runVerFormularioState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
			try{
				init(request);
				validarSesion(request);

				req.setAttribute("arrDays", FechaUtil.getReportDays());
				req.setAttribute("arrMonths", FechaUtil.getReportMonths());
				req.setAttribute("arrYears", FechaUtil.getReportYears());
			
				String hoy = FechaUtil.getCurrentDate();
				String hoy_dia = hoy.substring(0,2);
				String hoy_mes = hoy.substring(3,5);
				String hoy_ano = hoy.substring(6,10);
			
				req.setAttribute("selectedIDay",hoy_dia);
				req.setAttribute("selectedIMonth",hoy_mes);
				req.setAttribute("selectedIYear",hoy_ano);
				req.setAttribute("selectedFDay",hoy_dia);
				req.setAttribute("selectedFMonth",hoy_mes);
				req.setAttribute("selectedFYear",hoy_ano);			
				
				//req.setAttribute("hayData", null);

				response.setStyle("verFormulario");
			
			}catch(Throwable ex){
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			}finally{
				end(request);
			}
			return response;
		}
		
	protected ControllerResponse runbuscaMuestraResultadosState(
				ControllerRequest request,
				ControllerResponse response)
				throws ControllerException {

				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
				try{
					init(request);
					validarSesion(request);

					req.setAttribute("arrDays", FechaUtil.getReportDays());
					req.setAttribute("arrMonths", FechaUtil.getReportMonths());
					req.setAttribute("arrYears", FechaUtil.getReportYears());
			
					String hoy = FechaUtil.getCurrentDate();
					String hoy_dia = hoy.substring(0,2);
					String hoy_mes = hoy.substring(3,5);
					String hoy_ano = hoy.substring(6,10);
			
					req.setAttribute("selectedIDay",hoy_dia);
					req.setAttribute("selectedIMonth",hoy_mes);
					req.setAttribute("selectedIYear",hoy_ano);
					req.setAttribute("selectedFDay",hoy_dia);
					req.setAttribute("selectedFMonth",hoy_mes);
					req.setAttribute("selectedFYear",hoy_ano);			
				
					String date_Dia_Inicio = request.getParameter("diainicio");
					String date_Mes_Inicio = request.getParameter("mesinicio");
					String date_Ano_Inicio = request.getParameter("anoinicio");
					String date_Dia_Fin = request.getParameter("diafin");
					String date_Mes_Fin = request.getParameter("mesfin");
					String date_Ano_Fin  = request.getParameter("anofin");

					//Validar Rango de Fechas						
					String date_Inicio = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio)) ;
					String date_Fin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
		
					String date_Inicio_Ora = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio)) +" 00:00:00";
					String date_Fin_Ora = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin)) +" 23:59:59";


					response.setStyle("muestraResumen");
			
				}catch(Throwable ex){
					log(Constantes.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					response.setStyle("error");
				}finally{
					end(request);
				}
				return response;
			}

}
