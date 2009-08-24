package gob.pe.sunarp.extranet.reportes.controller;

//paquetes del sistema
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.misc.*;

//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.reportes.beans.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;

import java.io.PrintWriter;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletResponse;

public class ReporteUsuariosRegistradosController extends ControllerExtension
{

	public ReporteUsuariosRegistradosController() 
	{
		super();
		addState(new State("verFormulario", "muestra formulario de búsqueda de usuarios"));
		addState(new State("verReporte", "muestra el resultado de la búsqueda de usuarios"));
		addState(new State("verDetalle", "muestra el resultado de la búsqueda detallada de usuarios"));		
		addState(new State("exportarReporte", "exporta el resultado de la búsqueda detallada de usuarios"));		
	}
	
	public ControllerResponse runVerFormularioState
	(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
		{
		try {	
			init(request);
			validarSesion(request);
			
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			//fechas
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

			FormUsuariosRegistradosBean temp_FormUsuariosRegistradosBean = new FormUsuariosRegistradosBean();
			
			req.setAttribute("formusuariosregistradosbean", temp_FormUsuariosRegistradosBean);
			
			response.setStyle("usuariosregistrados");
		}catch(Throwable ex)
		{
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}			

	public ControllerResponse runVerReporteState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");

			FormUsuariosRegistradosBean temp_FormUsuariosRegistradosBean = new FormUsuariosRegistradosBean();
							
			//Recepcion de Parametros de formulario JSP
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());			
			
			String date_Dia_Inicio = request.getParameter("diainicio");
			String date_Mes_Inicio = request.getParameter("mesinicio");
			String date_Ano_Inicio = request.getParameter("anoinicio");
			String date_Dia_Fin = request.getParameter("diafin");
			String date_Mes_Fin = request.getParameter("mesfin");
			String date_Ano_Fin  = request.getParameter("anofin");
			
			req.setAttribute("selectedIDay",date_Dia_Inicio);
			req.setAttribute("selectedIMonth",date_Mes_Inicio);
			req.setAttribute("selectedIYear",date_Ano_Inicio);
			req.setAttribute("selectedFDay",date_Dia_Fin);
			req.setAttribute("selectedFMonth",date_Mes_Fin);
			req.setAttribute("selectedFYear",date_Ano_Fin);

			//Validar Rango de Fechas						
			String date_Inicio = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));

			String date_Inicio_Ora = date_Inicio +" 00:00:00";
			String date_Fin_Ora = date_Fin +" 23:59:59";

			String str_sql = null;
			java.sql.ResultSet rs = null;

			StringBuffer strbfr_sql = new StringBuffer();			
						
			java.util.List temp_list_usuariosregistrados = new java.util.ArrayList();
			java.util.List temp_list_totalesusuariosregistrados = new java.util.ArrayList();			
			int totalind = 0;
			int totalorg = 0;
			int totalgen = 0;
			double porcentaje = 0;			
			String str_porcentaje="";
			
			//Generar SQL
			int temp_total_individual = 0;
			int temp_total_organizacion = 0;			
			int temp_total_general = 0;			
			UsuariosRegistradosBean temp_UsuariosRegistradosBean =null;
			
			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB TOTAL
			
			temp_UsuariosRegistradosBean = new UsuariosRegistradosBean();
			
			
			
			strbfr_sql.append("SELECT 'WEB' NOMBRE ,decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I) SUMAI, ");
			strbfr_sql.append(" decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) SIMAO  , ");
			strbfr_sql.append(" decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I)+decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) TOTAL");
			strbfr_sql.append(" from  (select count(*) NUM_TRANS_I ");
			strbfr_sql.append(" from  aud_afiliacion t2, transaccion t3 ");
			strbfr_sql.append(" where ");
			strbfr_sql.append(" t2.trans_id = t3.trans_id ");
			strbfr_sql.append(" and t2.tipo_afil='I' ");
			strbfr_sql.append(" and t2.fg_web = 1 ");
			strbfr_sql.append(" and t3.fec_hor between to_date('").append(date_Inicio_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS') and to_date('").append(date_Fin_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS') ) t4 ");
			strbfr_sql.append(", (select count(*) NUM_TRANS_O  ");
			strbfr_sql.append(" from  aud_afiliacion t2, transaccion t3 ");
			strbfr_sql.append(" where");
			strbfr_sql.append(" t2.trans_id = t3.trans_id");
			strbfr_sql.append(" and t2.tipo_afil='O' ");
			strbfr_sql.append(" and t2.fg_web = 1 ");
			strbfr_sql.append(" and t3.fec_hor between to_date('").append(date_Inicio_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS') and to_date('").append(date_Fin_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS')  ) t5 ");			
																		
			
			Statement stmt = conn.createStatement();
			if (isTrace(this)) System.out.println("___verQuery_ "+strbfr_sql.toString());
			rs = stmt.executeQuery(strbfr_sql.toString());
			
			if(rs.next())
			{
				totalind = rs.getInt(2);
				totalorg = rs.getInt(3);
				totalgen = rs.getInt(4);
				temp_UsuariosRegistradosBean.setCodigoRegistroPublico("00");
				temp_UsuariosRegistradosBean.setCodigoOficinaRegistral("00");
				temp_UsuariosRegistradosBean.setNombreOficinaRegistral("WEB");	
				temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+totalind);					
				temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+totalorg);					
				temp_total_individual= temp_total_individual+ totalind;
				temp_total_organizacion= temp_total_organizacion + totalorg;
			}
			else
			{
				totalind = 0;
				totalorg = 0;
				totalgen = 0;				
				temp_UsuariosRegistradosBean.setNombreOficinaRegistral("WEB");	
				temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+totalind);									
				temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+totalorg);								
				temp_total_individual= temp_total_individual+ totalind;
				temp_total_organizacion= temp_total_organizacion + totalorg;
			}	
			
			//totalgen=totalgen+totalind+totalorg;
			temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +totalgen);									
			temp_total_general = temp_total_general + totalgen;
			temp_UsuariosRegistradosBean.setPorcentajesOficinaRegistral(""+ 0);
			temp_list_usuariosregistrados.add(temp_UsuariosRegistradosBean);

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//OFICINAS REGISTRALES
			strbfr_sql.setLength(0);
			strbfr_sql.append("SELECT t1.reg_pub_id,t1.ofic_reg_id,t1.nombre  ");
			strbfr_sql.append(",decode(t7.NUM_TRANS_O,null,0,t7.NUM_TRANS_O) SUMAO ");
			strbfr_sql.append(",decode(t8.NUM_TRANS_I,null,0,t8.NUM_TRANS_I) SUMAI ");
			strbfr_sql.append(",decode(t8.NUM_TRANS_I,null,0,t8.NUM_TRANS_I) + decode(t7.NUM_TRANS_O,null,0,t7.NUM_TRANS_O) TOTAL ");			
			strbfr_sql.append("FROM ");
			strbfr_sql.append("ofic_registral t1, ");
			strbfr_sql.append("(  ");
			strbfr_sql.append("select   ");
			strbfr_sql.append("t4.ofic_reg_id, t4.reg_pub_id,count(*) NUM_TRANS_O  ");
			strbfr_sql.append("from  ");
			strbfr_sql.append("aud_afiliacion t2  ");
			strbfr_sql.append(",transaccion t3  ");
			strbfr_sql.append(",cuenta_juris t4  ");
			strbfr_sql.append(",cuenta t5  ");
			strbfr_sql.append(",persona t6  ");
			strbfr_sql.append("where   ");
			strbfr_sql.append("t4.cuenta_id = t5.cuenta_id  ");
			strbfr_sql.append("and t4.persona_id = t6.persona_id  ");
			strbfr_sql.append("and t2.trans_id = t3.trans_id  ");
			strbfr_sql.append("and t2.persona_id = t4.persona_id   ");
			strbfr_sql.append("and t2.fg_web = 0   ");			
			strbfr_sql.append("and t5.tipo_usr like '101%' ");
			strbfr_sql.append("and t3.fec_hor between to_date('").append(date_Inicio_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS') and to_date('").append(date_Fin_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS') ");
			strbfr_sql.append("group by   ");
			strbfr_sql.append("t4.ofic_reg_id, t4.reg_pub_id  ");
			strbfr_sql.append(") ");
			strbfr_sql.append("t7 ");
			strbfr_sql.append(", ");
			strbfr_sql.append("( ");
			strbfr_sql.append("select t4.ofic_reg_id, t4.reg_pub_id,count(*) NUM_TRANS_I   ");
			strbfr_sql.append("from  ");
			strbfr_sql.append("aud_afiliacion t2  ");
			strbfr_sql.append(",transaccion t3  ");
			strbfr_sql.append(",cuenta_juris t4  ");
			strbfr_sql.append(",cuenta t5  ");
			strbfr_sql.append(",persona t6  ");
			strbfr_sql.append("where   ");
			strbfr_sql.append("t4.cuenta_id = t5.cuenta_id  ");
			strbfr_sql.append("and t4.persona_id = t6.persona_id  ");
			strbfr_sql.append("and t2.trans_id = t3.trans_id  ");
			strbfr_sql.append("and t2.persona_id = t4.persona_id   ");
			strbfr_sql.append("and t2.fg_web = 0   ");			
			strbfr_sql.append("and t5.tipo_usr like '110%' ");
			strbfr_sql.append("and t3.fec_hor between to_date('").append(date_Inicio_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS') and to_date('").append(date_Fin_Ora).append("' ,'DD/MM/YYYY HH24:MI:SS') ");		
			strbfr_sql.append("group by   ");
			strbfr_sql.append("t4.ofic_reg_id, t4.reg_pub_id  ");
			strbfr_sql.append(") ");
			strbfr_sql.append("t8 ");
			strbfr_sql.append("WHERE  ");
			strbfr_sql.append("t1.reg_pub_id = t7.reg_pub_id (+)   ");
			strbfr_sql.append("and t1.ofic_reg_id = t7.ofic_reg_id (+)   ");
			strbfr_sql.append("and t1.reg_pub_id = t8.reg_pub_id (+)  ");
			strbfr_sql.append("and t1.ofic_reg_id = t8.ofic_reg_id (+)   ");
			strbfr_sql.append("and t1.reg_pub_id <>'00'   ");
			strbfr_sql.append("GROUP BY t1.reg_pub_id,t1.ofic_reg_id,t1.nombre,t7.NUM_TRANS_O,t8.NUM_TRANS_I	  ");
			strbfr_sql.append("ORDER BY t1.nombre  ");

			if (isTrace(this)) System.out.println("__verQuery_ "+ strbfr_sql.toString());
			
			rs = stmt.executeQuery(strbfr_sql.toString());

			while(rs.next())
			{
				temp_UsuariosRegistradosBean = new UsuariosRegistradosBean();				
				totalorg = rs.getInt(4);				
				totalind = rs.getInt(5);
				totalgen = rs.getInt(6);
				temp_UsuariosRegistradosBean.setCodigoRegistroPublico(rs.getString(1));
				temp_UsuariosRegistradosBean.setCodigoOficinaRegistral(rs.getString(2));
				temp_UsuariosRegistradosBean.setNombreOficinaRegistral(rs.getString(3));
				temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+totalind);
				temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+totalorg);
				temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +totalgen);
				temp_total_individual= temp_total_individual+ totalind;
				temp_total_organizacion= temp_total_organizacion + totalorg;
				temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +totalgen);
				temp_total_general = temp_total_general + totalgen;				
				temp_UsuariosRegistradosBean.setPorcentajesOficinaRegistral(""+ 0);
				temp_list_usuariosregistrados.add(temp_UsuariosRegistradosBean);
			}	

			//Calculo de Porcentajes
			for(java.util.Iterator iterator_usuariosregistrados = temp_list_usuariosregistrados.iterator(); iterator_usuariosregistrados.hasNext();)
			{
				UsuariosRegistradosBean iteratorusuariosregistradosbean = (UsuariosRegistradosBean) iterator_usuariosregistrados.next();
				if(Integer.parseInt(iteratorusuariosregistradosbean.getTotalGeneralOficinaRegistral())!=0)
				{				
					porcentaje= Double.parseDouble(iteratorusuariosregistradosbean.getTotalGeneralOficinaRegistral());
					porcentaje = (porcentaje/((double)temp_total_general)) * 100;
					porcentaje = Math.round(porcentaje*100);
					porcentaje = porcentaje / 100;	
					iteratorusuariosregistradosbean.setPorcentajesOficinaRegistral(Double.toString(porcentaje));
				}
			}
			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//TOTAL GENERAL++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			temp_UsuariosRegistradosBean = new UsuariosRegistradosBean();			
			temp_UsuariosRegistradosBean.setNombreOficinaRegistral("Total");
			temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+temp_total_individual);
			temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+temp_total_organizacion);
			temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +temp_total_general);									
			temp_list_totalesusuariosregistrados.add(temp_UsuariosRegistradosBean);	

			temp_FormUsuariosRegistradosBean .setList_UsuariosRegistrados(temp_list_usuariosregistrados);
			temp_FormUsuariosRegistradosBean .setList_TotalesUsuariosRegistrados(temp_list_totalesusuariosregistrados);			
			
			temp_FormUsuariosRegistradosBean .setStr_Date_Inicio(date_Inicio);
			temp_FormUsuariosRegistradosBean .setStr_Date_Fin(date_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Ano_Fin(date_Ano_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Ano_Inicio(date_Ano_Inicio);
			temp_FormUsuariosRegistradosBean .setStr_Mes_Fin(date_Mes_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Mes_Inicio(date_Mes_Inicio);
			
			req.setAttribute("formusuariosregistradosbean", temp_FormUsuariosRegistradosBean );
			
			response.setStyle("usuariosregistrados");
			
			conn.commit();
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
			
		}
		return response;
	}


	public ControllerResponse runExportarReporteState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");

			FormUsuariosRegistradosBean temp_FormUsuariosRegistradosBean = new FormUsuariosRegistradosBean();
							
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());			
			
			String date_Dia_Inicio = request.getParameter("diainicio");
			String date_Mes_Inicio = request.getParameter("mesinicio");
			String date_Ano_Inicio = request.getParameter("anoinicio");
			String date_Dia_Fin = request.getParameter("diafin");
			String date_Mes_Fin = request.getParameter("mesfin");
			String date_Ano_Fin  = request.getParameter("anofin");
			
			req.setAttribute("selectedIDay",date_Dia_Inicio);
			req.setAttribute("selectedIMonth",date_Mes_Inicio);
			req.setAttribute("selectedIYear",date_Ano_Inicio);
			req.setAttribute("selectedFDay",date_Dia_Fin);
			req.setAttribute("selectedFMonth",date_Mes_Fin);
			req.setAttribute("selectedFYear",date_Ano_Fin);

			//Validar Rango de Fechas						
			String date_Inicio = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
			{
				String a="1";
			}

			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
			{
				String b="1";					
			}	

			String date_Inicio_Ora = date_Inicio +" 00:00:00";
			String date_Fin_Ora = date_Fin +" 23:59:59";


			String str_sql = null;
			java.sql.Statement stmt = null;
			java.sql.ResultSet rs = null;

			StringBuffer strbfr_sql = new StringBuffer();			
			java.sql.PreparedStatement prpstmt =null;
						
			java.util.List temp_list_usuariosregistrados = new java.util.ArrayList();
			java.util.List temp_list_totalesusuariosregistrados = new java.util.ArrayList();			
			int totalind = 0;
			int totalorg = 0;
			int totalgen = 0;
			double porcentaje = 0;			
			String str_porcentaje="";
			//Generar SQL
			int temp_total_individual = 0;
			int temp_total_organizacion = 0;			
			int temp_total_general = 0;			
			UsuariosRegistradosBean temp_UsuariosRegistradosBean =null;

	 		String fname = "UsuariosRegistrados.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			PrintWriter out_cliente = null;
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			StringBuffer stb = null;
			stb = new StringBuffer();
			stb.append("Ofic Registral").append(",");
			stb.append("Organizaciones").append(",");
			stb.append("Individuales").append(",");
			stb.append("Total").append(",");
			stb.append("Porcentajes(%)");
					
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
				

			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB TOTAL
			temp_UsuariosRegistradosBean = new UsuariosRegistradosBean();
			strbfr_sql.append("SELECT 'WEB' NOMBRE ,decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I) SUMAI, ");
			strbfr_sql.append(" decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) SIMAO  , ");
			strbfr_sql.append(" decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I)+decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) TOTAL");
			strbfr_sql.append(" from  (select count(*) NUM_TRANS_I ");
			strbfr_sql.append(" from  aud_afiliacion t2, transaccion t3 ");
			strbfr_sql.append(" where ");
			strbfr_sql.append(" t2.trans_id = t3.trans_id ");
			strbfr_sql.append(" and t2.tipo_afil='I' ");
			strbfr_sql.append(" and t2.fg_web = 1 ");
			strbfr_sql.append(" and t3.fec_hor between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS') ) t4 ");
			strbfr_sql.append(", (select count(*) NUM_TRANS_O  ");
			strbfr_sql.append(" from  aud_afiliacion t2, transaccion t3 ");
			strbfr_sql.append(" where");
			strbfr_sql.append(" t2.trans_id = t3.trans_id");
			strbfr_sql.append(" and t2.tipo_afil='O' ");
			strbfr_sql.append(" and t2.fg_web = 1 ");
			strbfr_sql.append(" and t3.fec_hor between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS')  ) t5 ");			
																		
			if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
			prpstmt = conn.prepareStatement(strbfr_sql.toString());				
			prpstmt.setString(1, date_Inicio_Ora );
			prpstmt.setString(2, date_Fin_Ora );
			prpstmt.setString(3, date_Inicio_Ora );
			prpstmt.setString(4, date_Fin_Ora );				
			if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
			rs = null;				
			conn.setAutoCommit(false);			
			rs = prpstmt.executeQuery();			

			/*			
			str_sql = "Select 'WEB' NOMBRE ,decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I) SUMAI, "+
			" decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) SIMAO  ," +
			" decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I)+decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) TOTAL " +
			" from  (select count(*) NUM_TRANS_I "+
						 "from  aud_afiliacion t2, transaccion t3 " +
						 "where " +
						 "t2.trans_id = t3.trans_id "+
						 "and t2.tipo_afil='I' " +
						 "and t2.fg_web = 1 " +
						 "and t3.fec_hor between " + date_Inicio_Ora + "  and " + date_Fin_Ora + " ) t4 " +
					", (select count(*) NUM_TRANS_O " +
	 					 "from  aud_afiliacion t2, transaccion t3 "+
						 "where "+
						 "t2.trans_id = t3.trans_id "+
						 "and t2.tipo_afil='O' "+
						 "and t2.fg_web = 1	" +
						 "and t3.fec_hor between " + date_Inicio_Ora + " and " + date_Fin_Ora + " ) t5 " ;
						 
			//str_sql = "SELECT SUBSTR(TRAN.FEC_HOR,0,10),COUNT(TRAN.TRANS_ID) " +
			//		"FROM AUD_AFILIACION AFIL,TRANSACCION TRAN " +
			//		"WHERE AFIL.TRANS_ID=TRAN.TRANS_ID AND AFIL.FG_WEB='1' AND TRAN.TIPO_USR='1' AND AFIL.TIPO_AFIL='I' AND TRAN.FEC_HOR " +
			//		"BETWEEN " + date_Inicio_Ora + " AND " + date_Fin_Ora + " GROUP BY SUBSTR(TRAN.FEC_HOR,0,10) ";
			rs = null;	
			stmt = conn.getMyConnection().createStatement();
			conn.setAutoCommit(false);
			trace("SELECT a base de datos" + str_sql.toString(),request);
			trace("SELECT a base de datos" + date_Inicio_Ora,request);		
			trace("SELECT a base de datos" + date_Fin_Ora,request);	
			trace("SELECT a base de datos" + date_Fin_Ora,request);				
			rs = stmt.executeQuery(str_sql);			
			*/
			if(rs.next())
			{
				totalind = rs.getInt(2);
				totalorg = rs.getInt(3);
				totalgen = rs.getInt(4);
				temp_UsuariosRegistradosBean.setCodigoRegistroPublico("00");
				temp_UsuariosRegistradosBean.setCodigoOficinaRegistral("00");
				temp_UsuariosRegistradosBean.setNombreOficinaRegistral("WEB");	
				temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+totalind);					
				temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+totalorg);					
				temp_total_individual= temp_total_individual+ totalind;
				temp_total_organizacion= temp_total_organizacion + totalorg;
			}else{
				totalind = 0;
				totalorg = 0;
				totalgen = 0;				
				temp_UsuariosRegistradosBean.setNombreOficinaRegistral("WEB");	
				temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+totalind);									
				temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+totalorg);								
				temp_total_individual= temp_total_individual+ totalind;
				temp_total_organizacion= temp_total_organizacion + totalorg;
			}	
			//totalgen=totalgen+totalind+totalorg;
			temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +totalgen);									
			temp_total_general = temp_total_general + totalgen;
			temp_UsuariosRegistradosBean.setPorcentajesOficinaRegistral(""+ 0);
			temp_list_usuariosregistrados.add(temp_UsuariosRegistradosBean);

			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//OFICINAS REGISTRALES
			strbfr_sql.setLength(0);
			strbfr_sql.append("SELECT ");
			strbfr_sql.append("t1.reg_pub_id  ");
			strbfr_sql.append(",t1.ofic_reg_id  ");
			strbfr_sql.append(",t1.nombre  ");
			strbfr_sql.append(",decode(t7.NUM_TRANS_O,null,0,t7.NUM_TRANS_O) SUMAO ");
			strbfr_sql.append(",decode(t8.NUM_TRANS_I,null,0,t8.NUM_TRANS_I) SUMAI ");
			strbfr_sql.append(",decode(t8.NUM_TRANS_I,null,0,t8.NUM_TRANS_I) + decode(t7.NUM_TRANS_O,null,0,t7.NUM_TRANS_O) TOTAL ");			
			strbfr_sql.append("FROM ");
			strbfr_sql.append("ofic_registral t1, ");
			strbfr_sql.append("(  ");
			strbfr_sql.append("select   ");
			strbfr_sql.append("t4.ofic_reg_id, t4.reg_pub_id,count(*) NUM_TRANS_O  ");
			strbfr_sql.append("from  ");
			strbfr_sql.append("aud_afiliacion t2  ");
			strbfr_sql.append(",transaccion t3  ");
			strbfr_sql.append(",cuenta_juris t4  ");
			strbfr_sql.append(",cuenta t5  ");
			strbfr_sql.append(",persona t6  ");
			strbfr_sql.append("where   ");
			strbfr_sql.append("t4.cuenta_id = t5.cuenta_id  ");
			strbfr_sql.append("and t4.persona_id = t6.persona_id  ");
			strbfr_sql.append("and t2.trans_id = t3.trans_id  ");
			strbfr_sql.append("and t2.persona_id = t4.persona_id   ");
			strbfr_sql.append("and t2.fg_web = 0   ");			
			strbfr_sql.append("and t5.tipo_usr like '101%' ");
			strbfr_sql.append("and t3.fec_hor between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS') ");
			strbfr_sql.append("group by   ");
			strbfr_sql.append("t4.ofic_reg_id, t4.reg_pub_id  ");
			strbfr_sql.append(") ");
			strbfr_sql.append("t7 ");
			strbfr_sql.append(", ");
			strbfr_sql.append("( ");
			strbfr_sql.append("select t4.ofic_reg_id, t4.reg_pub_id,count(*) NUM_TRANS_I   ");
			strbfr_sql.append("from  ");
			strbfr_sql.append("aud_afiliacion t2  ");
			strbfr_sql.append(",transaccion t3  ");
			strbfr_sql.append(",cuenta_juris t4  ");
			strbfr_sql.append(",cuenta t5  ");
			strbfr_sql.append(",persona t6  ");
			strbfr_sql.append("where   ");
			strbfr_sql.append("t4.cuenta_id = t5.cuenta_id  ");
			strbfr_sql.append("and t4.persona_id = t6.persona_id  ");
			strbfr_sql.append("and t2.trans_id = t3.trans_id  ");
			strbfr_sql.append("and t2.persona_id = t4.persona_id   ");
			strbfr_sql.append("and t2.fg_web = 0   ");			
			strbfr_sql.append("and t5.tipo_usr like '110%' ");
			strbfr_sql.append("and t3.fec_hor between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS') ");		
			strbfr_sql.append("group by   ");
			strbfr_sql.append("t4.ofic_reg_id, t4.reg_pub_id  ");
			strbfr_sql.append(") ");
			strbfr_sql.append("t8 ");
			strbfr_sql.append("WHERE  ");
			strbfr_sql.append("t1.reg_pub_id = t7.reg_pub_id (+)   ");
			strbfr_sql.append("and t1.ofic_reg_id = t7.ofic_reg_id (+)   ");
			strbfr_sql.append("and t1.reg_pub_id = t8.reg_pub_id (+)  ");
			strbfr_sql.append("and t1.ofic_reg_id = t8.ofic_reg_id (+)   ");
			strbfr_sql.append("and t1.reg_pub_id <>'00'   ");
			strbfr_sql.append("GROUP BY t1.reg_pub_id,t1.ofic_reg_id,t1.nombre,t7.NUM_TRANS_O,t8.NUM_TRANS_I	  ");
			strbfr_sql.append("ORDER BY t1.nombre  ");

			
			if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
			prpstmt = conn.prepareStatement(strbfr_sql.toString());				
			prpstmt.setString(1, date_Inicio_Ora );
			prpstmt.setString(2, date_Fin_Ora );
			prpstmt.setString(3, date_Inicio_Ora );
			prpstmt.setString(4, date_Fin_Ora );				
			if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
			rs = null;				
			rs = prpstmt.executeQuery();

			/*
			str_sql = "Select t1.reg_pub_id, t1.ofic_reg_id, t1.nombre, " +
			" decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I) SUMAI, " +
			" decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) SUMAO , " +
			" decode(t4.NUM_TRANS_I,null,0,t4.NUM_TRANS_I) + decode(t5.NUM_TRANS_O,null,0,t5.NUM_TRANS_O) TOTAL " +
			" from ofic_registral t1, " +
					  "(select t2.ofic_reg_id, t2.reg_pub_id,count(*) NUM_TRANS_I " +
	 					 "from  aud_afiliacion t2, transaccion t3 " +
						 "where " +
						 "t2.trans_id = t3.trans_id " +
 						 "and t3.tipo_usr =  1 "+
						 "and t2.tipo_afil='I' " +
						 "and t2.fg_web = 0 " +
						 "and t3.fec_hor between " + date_Inicio_Ora + " and " + date_Fin_Ora + 						 "group by t2.ofic_reg_id, t2.reg_pub_id, t2.tipo_afil) t4 " +
					  ", (select t2.ofic_reg_id, t2.reg_pub_id, count(*) NUM_TRANS_O " +
	 					 "from  aud_afiliacion t2, transaccion t3 "+
						 "where " +
						 "t2.trans_id = t3.trans_id " +
						 "and t3.tipo_usr =  1 "+
						 "and t2.tipo_afil='O' " +
						 "and t2.fg_web = 0 " +
						 "and t3.fec_hor between " + date_Inicio_Ora + " and " + date_Fin_Ora + 
						 "group by t2.ofic_reg_id, t2.reg_pub_id,t2.tipo_afil) t5 " +
			" where " +
				"t1.reg_pub_id = t4.reg_pub_id (+) and "+
				"t1.ofic_reg_id = t4.ofic_reg_id (+) and " +
				"t1.reg_pub_id = t5.reg_pub_id (+)and " +
				"t1.ofic_reg_id = t5.ofic_reg_id (+) " +
				"and t1.reg_pub_id <>'00' " +
			"group by t1.reg_pub_id,t1.ofic_reg_id,t1.nombre,t4.NUM_TRANS_I,NUM_TRANS_O	"+
			"ORDER BY t1.nombre ";	 
									 
			//str_sql = "SELECT SUBSTR(TRAN.FEC_HOR,0,10),COUNT(TRAN.TRANS_ID) " +
			//		"FROM AUD_AFILIACION AFIL,TRANSACCION TRAN " +
			//		"WHERE AFIL.TRANS_ID=TRAN.TRANS_ID AND AFIL.FG_WEB='1' AND TRAN.TIPO_USR='1' AND AFIL.TIPO_AFIL='I' AND TRAN.FEC_HOR " +
			//		"BETWEEN " + date_Inicio_Ora + " AND " + date_Fin_Ora + " GROUP BY SUBSTR(TRAN.FEC_HOR,0,10) ";
			rs = null;	
			stmt = conn.getMyConnection().createStatement();
			conn.setAutoCommit(false);
			trace("SELECT a base de datos" + str_sql.toString(),request);
			trace("SELECT a base de datos" + date_Inicio_Ora,request);		
			trace("SELECT a base de datos" + date_Fin_Ora,request);	
			trace("SELECT a base de datos" + date_Fin_Ora,request);				
			rs = stmt.executeQuery(str_sql);			
			*/
			while(rs.next())
			{
				temp_UsuariosRegistradosBean = new UsuariosRegistradosBean();				
				totalorg = rs.getInt(4);				
				totalind = rs.getInt(5);
				totalgen = rs.getInt(6);
				temp_UsuariosRegistradosBean.setCodigoRegistroPublico(rs.getString(1));
				temp_UsuariosRegistradosBean.setCodigoOficinaRegistral(rs.getString(2));
				temp_UsuariosRegistradosBean.setNombreOficinaRegistral(rs.getString(3));
				temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+totalind);
				temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+totalorg);
				temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +totalgen);
				temp_total_individual= temp_total_individual+ totalind;
				temp_total_organizacion= temp_total_organizacion + totalorg;
				temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +totalgen);
				temp_total_general = temp_total_general + totalgen;				
				temp_UsuariosRegistradosBean.setPorcentajesOficinaRegistral(""+ 0);
				temp_list_usuariosregistrados.add(temp_UsuariosRegistradosBean);

			}	
	
			//totalgen=totalgen+totalind+totalorg;

			//Calculo de Porcentajes
			for(java.util.Iterator iterator_usuariosregistrados = temp_list_usuariosregistrados.iterator(); iterator_usuariosregistrados.hasNext();){
				UsuariosRegistradosBean iteratorusuariosregistradosbean = (UsuariosRegistradosBean) iterator_usuariosregistrados.next();
				if(Integer.parseInt(iteratorusuariosregistradosbean.getTotalGeneralOficinaRegistral())!=0){				
					porcentaje= Double.parseDouble(iteratorusuariosregistradosbean.getTotalGeneralOficinaRegistral());
					porcentaje = (porcentaje/((double)temp_total_general)) * 100;
					porcentaje = Math.round(porcentaje*100);

					//porcentaje= Double.parseDouble(iteratorusuariosregistradosbean.getTotalGeneralOficinaRegistral());					
					//double exp = Math.pow(10,(double)2);
					//double ris = Math.round(porcentaje*exp);
					//porcentaje= ris/exp;
					
					porcentaje = porcentaje / 100;	
					//str_porcentaje=""+porcentaje;
					//str_porcentaje=str_porcentaje.substring(0,str_porcentaje.indexOf(".")+2);
					iteratorusuariosregistradosbean.setPorcentajesOficinaRegistral(Double.toString(porcentaje));
				}
			}

			for(java.util.Iterator iterator_usuariosregistrados = temp_list_usuariosregistrados.iterator(); iterator_usuariosregistrados.hasNext();){
				UsuariosRegistradosBean iteratorusuariosregistradosbean = (UsuariosRegistradosBean) iterator_usuariosregistrados.next();

				stb = new StringBuffer();

				stb.append(iteratorusuariosregistradosbean.getNombreOficinaRegistral()).append(",");
				stb.append(iteratorusuariosregistradosbean.getTotalOrganizacionOficinaRegistral()).append(",");
				stb.append(iteratorusuariosregistradosbean.getTotalIndividualOficinaRegistral()).append(",");
				stb.append(iteratorusuariosregistradosbean.getTotalGeneralOficinaRegistral()).append(",");
				stb.append(iteratorusuariosregistradosbean.getPorcentajesOficinaRegistral());
				
				out_cliente.println(stb.toString());
			}	
			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//TOTAL GENERAL++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			temp_UsuariosRegistradosBean = new UsuariosRegistradosBean();			
			temp_UsuariosRegistradosBean.setNombreOficinaRegistral("Total");
			temp_UsuariosRegistradosBean.setTotalIndividualOficinaRegistral(""+temp_total_individual);
			temp_UsuariosRegistradosBean.setTotalOrganizacionOficinaRegistral(""+temp_total_organizacion);
			temp_UsuariosRegistradosBean.setTotalGeneralOficinaRegistral("" +temp_total_general);									
			temp_list_totalesusuariosregistrados.add(temp_UsuariosRegistradosBean);	

			//totalesusuariobean.setTotalGeneralOficinaRegistral("" + (totalwebind+totalweborg));			
			//temp_list_usuariosregistrados.add(temp_UsuariosRegistradosbean);

			stb = new StringBuffer();
			
			stb.append("TOTALES").append(", ");
			stb.append(temp_UsuariosRegistradosBean.getTotalOrganizacionOficinaRegistral()).append(", ");
			stb.append(temp_UsuariosRegistradosBean.getTotalIndividualOficinaRegistral()).append(", ");
			stb.append(temp_UsuariosRegistradosBean.getTotalGeneralOficinaRegistral());

			out_cliente.println(stb.toString());			
			
			temp_FormUsuariosRegistradosBean .setList_UsuariosRegistrados(temp_list_usuariosregistrados);
			temp_FormUsuariosRegistradosBean .setList_TotalesUsuariosRegistrados(temp_list_totalesusuariosregistrados);			
			
			temp_FormUsuariosRegistradosBean .setStr_Date_Inicio(date_Inicio);
			temp_FormUsuariosRegistradosBean .setStr_Date_Fin(date_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Ano_Fin(date_Ano_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Ano_Inicio(date_Ano_Inicio);
			temp_FormUsuariosRegistradosBean .setStr_Mes_Fin(date_Mes_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Mes_Inicio(date_Mes_Inicio);

			conn.commit();
			
			out_cliente.flush();
			out_cliente.close();
			response.setCustomResponse(true);
			
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
			
		}
		return response;
	}


	public ControllerResponse runVerDetalleState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");

			FormUsuariosRegistradosBean temp_FormUsuariosRegistradosBean = new FormUsuariosRegistradosBean();
							
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());			
			
			String date_Dia_Inicio = request.getParameter("diainicio");
			String date_Mes_Inicio = request.getParameter("mesinicio");
			String date_Ano_Inicio = request.getParameter("anoinicio");
			String date_Dia_Fin = request.getParameter("diafin");
			String date_Mes_Fin = request.getParameter("mesfin");
			String date_Ano_Fin  = request.getParameter("anofin");
			
			req.setAttribute("selectedIDay",date_Dia_Inicio);
			req.setAttribute("selectedIMonth",date_Mes_Inicio);
			req.setAttribute("selectedIYear",date_Ano_Inicio);
			req.setAttribute("selectedFDay",date_Dia_Fin);
			req.setAttribute("selectedFMonth",date_Mes_Fin);
			req.setAttribute("selectedFYear",date_Ano_Fin);			
			
			
			String str_CodRegPub  = request.getParameter("codregpub");
			String str_CodOfiReg  = request.getParameter("codofireg");
			String str_CodTipo = request.getParameter("codtipo");						
			String str_Pagina = request.getParameter("pagina");
			String str_NomOfiReg = request.getParameter("nomofireg");
			String str_fecha_formato="";
			int int_Pagina;
			//Validaciones de Parametros//
			if ((str_Pagina==null) || (str_Pagina.equals("")))
			{
				int_Pagina= 1;
			}else{
				int_Pagina= Integer.parseInt(str_Pagina);
			}
			//Validar Rango de Fechas						
			String date_Inicio = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
			{
				String a="1";
			}

			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
			{
				String b="1";					
			}	
			//String date_Inicio_Ora=FechaUtil.stringTimeToOracleString(date_Inicio +" 00:00:00");
			//String date_Fin_Ora= FechaUtil.stringTimeToOracleString(date_Fin +" 23:59:59");
			
			//String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
			//String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);
			
			String date_Inicio_Ora = date_Inicio +" 00:00:00";
			String date_Fin_Ora = date_Fin +" 23:59:59";

			//Variables de Acceso a Datos
			String str_sql = null;
			String str_sql_count = null;						
			java.sql.Statement stmt = null;
			java.sql.ResultSet rs = null;

			StringBuffer strbfr_sql = new StringBuffer();			
			java.sql.PreparedStatement prpstmt =null;
			
			java.util.List temp_list_usuariosregistradosdetalle = new java.util.ArrayList();

			//Generar SQL
			UsuariosRegistradosIndividualesBean temp_UsuariosRegistradosIndividualesBean =null;
			UsuariosRegistradosOrganizacionBean temp_UsuariosRegistradosOrganizacionBean =null;
			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB TOTAL
			//TO_CHAR(SYSDATE, 'MM-DD-YYYY HH24:MI:SS') 
			if(str_CodTipo.equals("org"))
			{
				temp_UsuariosRegistradosOrganizacionBean = new UsuariosRegistradosOrganizacionBean();
				if(str_CodRegPub.equals("00") && str_CodOfiReg.equals("00")){
					/*str_sql = "SELECT PJ.RAZ_SOC, TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'), PE.NUM_DOC_IDEN, PJ.NU_USRS, PN.APE_PAT, PN.APE_MAT, PN.NOMBRES, TO_CHAR(PJ.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') " +
							  "FROM PE_JURI PJ, ORG_CTAS OC, PE_NATU PN, PERSONA PE, CUENTA CU, AUD_AFILIACION AUD, TRANSACCION TRAN " +
							  "WHERE PJ.PE_JURI_ID=OC.PE_JURI_ID AND OC.FG_ADMIN='1' AND TRAN.CUENTA_ID=CU.CUENTA_ID "+
							  "AND CU.PE_NATU_ID=PN.PE_NATU_ID AND PN.PE_JURI_ID=PJ.PE_JURI_ID AND PJ.PERSONA_ID=PE.PERSONA_ID "+
							  "AND TRAN.TRANS_ID=AUD.TRANS_ID AND OC.CUENTA_ID=CU.CUENTA_ID AND AUD.FG_WEB='1' "+
							  "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora; */
					/*str_sql = "SELECT DISTINCT CU.USR_ID,PJ.RAZ_SOC, TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'), PE.NUM_DOC_IDEN, PJ.NU_USRS,PN.APE_PAT,PN.APE_MAT,PN.NOMBRES "+
							  ", TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') "+
							  "FROM PERSONA PE, PE_JURI PJ,AUD_AFILIACION AUD, TRANSACCION TRAN, PE_NATU PN, CUENTA CU " +
							  "WHERE " +
							  "PE.PERSONA_ID=PJ.PERSONA_ID AND PJ.PERSONA_ID=AUD.PERSONA_ID AND PE.PERSONA_ID=AUD.PERSONA_ID " +
							  "AND TRAN.TRANS_ID=AUD.TRANS_ID  AND AUD.FG_WEB='1' "+
							  "AND PN.PE_JURI_ID=PJ.PE_JURI_ID AND PN.PE_NATU_ID=CU.PE_NATU_ID " + 
							  "AND CU.TIPO_USR LIKE '101%' " +
							  "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora; 
					*/		  
					  strbfr_sql.append("SELECT ");
					  strbfr_sql.append("CU.USR_ID ");
					  strbfr_sql.append(",PJ.RAZ_SOC ");
					  strbfr_sql.append(", TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append(",PE.NUM_DOC_IDEN ");
					  strbfr_sql.append(",PJ.NU_USRS ");
					  strbfr_sql.append(",PEN.APE_PAT ");			
					  strbfr_sql.append(",PEN.APE_MAT  ");
					  strbfr_sql.append(",PEN.NOMBRES  ");
					  strbfr_sql.append(",TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append("FROM ");
					  strbfr_sql.append("  AUD_AFILIACION AUD ");
					  strbfr_sql.append(", TRANSACCION TRAN");					  					  
					  strbfr_sql.append(", PE_JURI PJ");					  
					  strbfr_sql.append(", PERSONA PE");					  
					  strbfr_sql.append(", CUENTA_JURIS CUJ");					  
					  strbfr_sql.append(", CUENTA CU");					  
					  strbfr_sql.append(", PE_NATU PEN  ");		
					  strbfr_sql.append("WHERE ");					  					  			  
					  strbfr_sql.append("  AUD.TRANS_ID=TRAN.TRANS_ID");					  
					  strbfr_sql.append("  AND AUD.PERSONA_ID=PJ.PERSONA_ID ");
					  strbfr_sql.append("  AND PJ.PERSONA_ID =PE.PERSONA_ID ");			
					  strbfr_sql.append("  AND PE.PERSONA_ID=CUJ.PERSONA_ID ");			
					  strbfr_sql.append("  AND CUJ.CUENTA_ID=CU.CUENTA_ID ");			
					  strbfr_sql.append("  AND CU.PE_NATU_ID=PEN.PE_NATU_ID  ");										  
					  strbfr_sql.append("  AND CU.TIPO_USR LIKE '101%' ");										  
					  strbfr_sql.append("  AND AUD.FG_WEB = 1 ");										  
					  //strbfr_sql.append("  AND AUD.REG_PUB_ID=? AND AUD.OFIC_REG_ID=? ");
					  strbfr_sql.append("  AND TRAN.FEC_HOR between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS') ");

							  
				}else{	
					/*str_sql = "SELECT PJ.RAZ_SOC, TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'), PE.NUM_DOC_IDEN, PJ.NU_USRS, PN.APE_PAT, PN.APE_MAT, PN.NOMBRES, TO_CHAR(PJ.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') " +
							  "FROM PE_JURI PJ, ORG_CTAS OC, PE_NATU PN, PERSONA PE, CUENTA CU, AUD_AFILIACION AUD, TRANSACCION TRAN " +
							  "WHERE PJ.PE_JURI_ID=OC.PE_JURI_ID AND OC.FG_ADMIN='1' AND TRAN.CUENTA_ID=CU.CUENTA_ID " +
							  "AND CU.PE_NATU_ID=PN.PE_NATU_ID AND PN.PE_JURI_ID=PJ.PE_JURI_ID AND PJ.PERSONA_ID=PE.PERSONA_ID " +
							  "AND TRAN.TRANS_ID=AUD.TRANS_ID AND OC.CUENTA_ID=CU.CUENTA_ID AND AUD.FG_WEB='0' AND TRAN.REG_PUB_ID='"+ str_CodRegPub + "' " +
							  "AND AUD.OFIC_REG_ID='" + str_CodOfiReg + "' " + //; limit 1
							  "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora;
					*/	
					/*  str_sql="SELECT DISTINCT CU.USR_ID,PJ.RAZ_SOC, TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'), PE.NUM_DOC_IDEN, PJ.NU_USRS,PN.APE_PAT,PN.APE_MAT,PN.NOMBRES " +
							  ", TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') "+
							  "FROM PERSONA PE, PE_JURI PJ,AUD_AFILIACION AUD, TRANSACCION TRAN, PE_NATU PN, CUENTA CU " +
							  "WHERE " +
							  "PE.PERSONA_ID=PJ.PERSONA_ID AND PJ.PERSONA_ID=AUD.PERSONA_ID AND PE.PERSONA_ID=AUD.PERSONA_ID " +
							  "AND TRAN.TRANS_ID=AUD.TRANS_ID  AND AUD.FG_WEB='0' " +
							  "AND PN.PE_JURI_ID=PJ.PE_JURI_ID AND PN.PE_NATU_ID=CU.PE_NATU_ID " +
							  "AND CU.TIPO_USR LIKE '101%' " +
							  //"AND AUD.REG_PUB_ID='"+ str_CodRegPub + "' AND AUD.OFIC_REG_ID='" + str_CodOfiReg + "' " +
							  "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora;
					 */
						  
					  strbfr_sql.append("SELECT ");
					  strbfr_sql.append("CU.USR_ID ");
					  strbfr_sql.append(",PJ.RAZ_SOC ");
					  strbfr_sql.append(", TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append(",PE.NUM_DOC_IDEN ");
					  strbfr_sql.append(",PJ.NU_USRS ");
					  strbfr_sql.append(",PEN.APE_PAT ");			
					  strbfr_sql.append(",PEN.APE_MAT  ");
					  strbfr_sql.append(",PEN.NOMBRES  ");
					  strbfr_sql.append(",TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append("FROM ");
					  strbfr_sql.append("  AUD_AFILIACION AUD ");
					  strbfr_sql.append(", TRANSACCION TRAN");					  					  
					  strbfr_sql.append(", PE_JURI PJ");					  
					  strbfr_sql.append(", PERSONA PE");					  
					  strbfr_sql.append(", CUENTA_JURIS CUJ");					  
					  strbfr_sql.append(", CUENTA CU");					  
					  strbfr_sql.append(", PE_NATU PEN  ");		
					  strbfr_sql.append("WHERE ");					  					  			  
					  strbfr_sql.append("  AUD.TRANS_ID=TRAN.TRANS_ID");					  
					  strbfr_sql.append("  AND AUD.PERSONA_ID=PJ.PERSONA_ID ");
					  strbfr_sql.append("  AND PJ.PERSONA_ID =PE.PERSONA_ID ");			
					  strbfr_sql.append("  AND PE.PERSONA_ID=CUJ.PERSONA_ID ");			
					  strbfr_sql.append("  AND CUJ.CUENTA_ID=CU.CUENTA_ID ");			
					  strbfr_sql.append("  AND CU.PE_NATU_ID=PEN.PE_NATU_ID  ");										  
					  strbfr_sql.append("  AND CU.TIPO_USR LIKE '101%' ");										  
					  strbfr_sql.append("  AND AUD.FG_WEB = 0 ");										  
					  strbfr_sql.append("  AND AUD.REG_PUB_ID=? AND AUD.OFIC_REG_ID=? ");
					  strbfr_sql.append("  AND TRAN.FEC_HOR between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS') ");
				}
			  
			}else{
				temp_UsuariosRegistradosIndividualesBean = new UsuariosRegistradosIndividualesBean();
				if(str_CodRegPub.equals("00") && str_CodOfiReg.equals("00")){
					/*str_sql = "SELECT CU.USR_ID,TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'),TDOC.NOMBRE_ABREV,PE.NUM_DOC_IDEN, PN.APE_PAT, PN.APE_MAT,PN.NOMBRES,TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') " +
							  "FROM CUENTA CU,TM_DOC_IDEN TDOC,PE_NATU PN,PERSONA PE,AUD_AFILIACION AUD, TRANSACCION TRAN " +
							  "WHERE CU.PE_NATU_ID=PN.PE_NATU_ID AND PN.PERSONA_ID=PE.PERSONA_ID AND TRAN.TRANS_ID=AUD.TRANS_ID " +
							  "AND PE.TIPO_DOC_ID=TDOC.TIPO_DOC_ID AND AUD.FG_WEB='1'AND CU.CUENTA_ID=TRAN.CUENTA_ID AND CU.TIPO_USR LIKE '11%' " +
							  "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora;*/
					/*str_sql = "SELECT CU.USR_ID,TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'),TDOC.NOMBRE_ABREV,PE.NUM_DOC_IDEN, PN.APE_PAT, PN.APE_MAT,PN.NOMBRES,TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') " + 
							  "FROM CUENTA CU,TM_DOC_IDEN TDOC,PE_NATU PN,PERSONA PE,AUD_AFILIACION AUD, TRANSACCION TRAN " +
  							  "WHERE CU.PE_NATU_ID=PN.PE_NATU_ID AND PN.PERSONA_ID=PE.PERSONA_ID AND TRAN.TRANS_ID=AUD.TRANS_ID " +
							  "AND PE.TIPO_DOC_ID=TDOC.TIPO_DOC_ID AND AUD.FG_WEB='1'AND PE.PERSONA_ID=AUD.PERSONA_ID AND CU.TIPO_USR LIKE '11%' " +
							  "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora;
					*/		  
					  strbfr_sql.append("SELECT ");
					  strbfr_sql.append("CU.USR_ID ");
					  strbfr_sql.append(", TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append(",TDOC.NOMBRE_ABREV ");					  
					  strbfr_sql.append(",PE.NUM_DOC_IDEN ");
					  strbfr_sql.append(",PEN.APE_PAT ");			
					  strbfr_sql.append(",PEN.APE_MAT  ");
					  strbfr_sql.append(",PEN.NOMBRES  ");
					  strbfr_sql.append(",TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append("FROM ");
					  strbfr_sql.append("  AUD_AFILIACION AUD ");
					  strbfr_sql.append(", TRANSACCION TRAN");					  					  
					  strbfr_sql.append(", CUENTA_JURIS CUJ");					  
					  strbfr_sql.append(", CUENTA CU");					  
					  strbfr_sql.append(", PE_NATU PEN  ");		
					  strbfr_sql.append(", PERSONA PE");		
					  strbfr_sql.append(", TM_DOC_IDEN TDOC ");
					  strbfr_sql.append("WHERE ");					  					  			  
					  strbfr_sql.append("  AUD.TRANS_ID=TRAN.TRANS_ID");					  
					  strbfr_sql.append("  AND AUD.PERSONA_ID=CUJ.PERSONA_ID ");			
					  strbfr_sql.append("  AND CUJ.CUENTA_ID=CU.CUENTA_ID ");			
					  strbfr_sql.append("  AND CU.PE_NATU_ID=PEN.PE_NATU_ID  ");
					  strbfr_sql.append("  AND PEN.PERSONA_ID=PE.PERSONA_ID  ");
					  strbfr_sql.append("  AND PE.TIPO_DOC_ID=TDOC.TIPO_DOC_ID  ");					  					  
					  strbfr_sql.append("  AND CU.TIPO_USR LIKE '11%' ");										  
					  strbfr_sql.append("  AND AUD.FG_WEB = 1 ");										  
					  //strbfr_sql.append("  AND AUD.REG_PUB_ID=? AND AUD.OFIC_REG_ID=? ");
					  strbfr_sql.append("  AND TRAN.FEC_HOR between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS') ");
							  
				}else{						
					/*str_sql = "SELECT CU.USR_ID,TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'),TDOC.NOMBRE_ABREV,PE.NUM_DOC_IDEN, PN.APE_PAT, PN.APE_MAT,PN.NOMBRES,TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') " +
							  "FROM CUENTA CU,TM_DOC_IDEN TDOC,PE_NATU PN,PERSONA PE,AUD_AFILIACION AUD, TRANSACCION TRAN " +
					  		  "WHERE CU.PE_NATU_ID=PN.PE_NATU_ID AND PN.PERSONA_ID=PE.PERSONA_ID AND TRAN.TRANS_ID=AUD.TRANS_ID " +
							  "AND PE.TIPO_DOC_ID=TDOC.TIPO_DOC_ID AND AUD.FG_WEB='0' AND TRAN.REG_PUB_ID='"+ str_CodRegPub + "' AND CU.CUENTA_ID=TRAN.CUENTA_ID " +
							  "AND AUD.OFIC_REG_ID='" + str_CodOfiReg + "' AND CU.TIPO_USR LIKE '11%' " +
							  "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora;
					*/
					/*str_sql ="SELECT CU.USR_ID,TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS'),TDOC.NOMBRE_ABREV,PE.NUM_DOC_IDEN,PN.APE_PAT,PN.APE_MAT,PN.NOMBRES,TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS')  "+
							 "FROM CUENTA CU,TM_DOC_IDEN TDOC,PE_NATU PN,PERSONA PE,AUD_AFILIACION AUD, TRANSACCION TRAN "+
							 "WHERE CU.PE_NATU_ID=PN.PE_NATU_ID AND PN.PERSONA_ID=PE.PERSONA_ID AND TRAN.TRANS_ID=AUD.TRANS_ID "+
							 "AND PE.TIPO_DOC_ID=TDOC.TIPO_DOC_ID AND AUD.FG_WEB='0'AND PE.PERSONA_ID=AUD.PERSONA_ID "+
							 "AND CU.TIPO_USR LIKE '11%' " +
							 //"AND AUD.REG_PUB_ID='"+ str_CodRegPub + "' AND  AUD.OFIC_REG_ID='" + str_CodOfiReg + "' "+
							 "AND TRAN.FEC_HOR between " + date_Inicio_Ora + " and " + date_Fin_Ora;
					*/
					  strbfr_sql.append("SELECT ");
					  strbfr_sql.append("CU.USR_ID ");
					  strbfr_sql.append(", TO_CHAR(CU.TS_CREA, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append(",TDOC.NOMBRE_ABREV ");					  
					  strbfr_sql.append(",PE.NUM_DOC_IDEN ");
					  strbfr_sql.append(",PEN.APE_PAT ");			
					  strbfr_sql.append(",PEN.APE_MAT  ");
					  strbfr_sql.append(",PEN.NOMBRES  ");
					  strbfr_sql.append(",TO_CHAR(CU.TS_ULT_ACC, 'DD/MM/YYYY HH24:MI:SS') ");
					  strbfr_sql.append("FROM ");
					  strbfr_sql.append("  AUD_AFILIACION AUD ");
					  strbfr_sql.append(", TRANSACCION TRAN");					  					  
					  strbfr_sql.append(", CUENTA_JURIS CUJ");					  
					  strbfr_sql.append(", CUENTA CU");					  
					  strbfr_sql.append(", PE_NATU PEN  ");		
					  strbfr_sql.append(", PERSONA PE");		
					  strbfr_sql.append(", TM_DOC_IDEN TDOC ");
					  strbfr_sql.append("WHERE ");					  					  			  
					  strbfr_sql.append("  AUD.TRANS_ID=TRAN.TRANS_ID");					  
					  strbfr_sql.append("  AND AUD.PERSONA_ID=CUJ.PERSONA_ID ");			
					  strbfr_sql.append("  AND CUJ.CUENTA_ID=CU.CUENTA_ID ");			
					  strbfr_sql.append("  AND CU.PE_NATU_ID=PEN.PE_NATU_ID  ");
					  strbfr_sql.append("  AND PEN.PERSONA_ID=PE.PERSONA_ID  ");
					  strbfr_sql.append("  AND PE.TIPO_DOC_ID=TDOC.TIPO_DOC_ID  ");					  					  
					  strbfr_sql.append("  AND CU.TIPO_USR LIKE '11%' ");										  
					  strbfr_sql.append("  AND AUD.FG_WEB = 0 ");										  
					  strbfr_sql.append("  AND AUD.REG_PUB_ID=? AND AUD.OFIC_REG_ID=? ");
					  strbfr_sql.append("  AND TRAN.FEC_HOR between to_date(? ,'DD/MM/YYYY HH24:MI:SS') and to_date(? ,'DD/MM/YYYY HH24:MI:SS') ");

				}			  
			}


			int regPorPagina= Propiedades.getInstance().getLineasPorPag();
			
			if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
			prpstmt = conn.prepareStatement(strbfr_sql.toString());				
			if(str_CodRegPub.equals("00") && str_CodOfiReg.equals("00"))
			{
				prpstmt.setString(1, date_Inicio_Ora );
				prpstmt.setString(2, date_Fin_Ora );							
			}else{
				prpstmt.setString(1, str_CodRegPub );
				prpstmt.setString(2, str_CodOfiReg );
				prpstmt.setString(3, date_Inicio_Ora );
				prpstmt.setString(4, date_Fin_Ora );			
			}				
			if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
			rs = null;				

			prpstmt.setFetchSize(regPorPagina);
			conn.setAutoCommit(false);
			rs = prpstmt.executeQuery();
			/*
			rs = null;	
			//stmt = conn.getMyConnection().createStatement();
			stmt = conn.getMyConnection().createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
			//stmnt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int regPorPagina= Propiedades.getInstance().getLineasPorPag();
			//regPorPagina= 2;
			//stmt.setFetchSize( regPorPagina + 1);
			stmt.setFetchSize(regPorPagina);
			conn.setAutoCommit(false);
			trace("SELECT a base de datos" + str_sql.toString(),request);
			trace("SELECT a base de datos" + date_Inicio_Ora,request);		
			trace("SELECT a base de datos" + date_Fin_Ora,request);	
			trace("SELECT a base de datos" + date_Fin_Ora,request);				
			rs = stmt.executeQuery(str_sql);			
			*/
			//rs.setFetchSize(1);
			if(int_Pagina != 1){
				//myResultSet.absolute((regPorPagina * (numPagina - 1)) - 1);
				//rs.absolute(regPorPagina * (int_Pagina - 1));
				rs.absolute(regPorPagina * (int_Pagina - 1));
				//rs.setFetchSize(1);
				//myResultSet.setFetchSize(regPorPagina + 1);
			}
			int numeroregistro=0;
			while (rs.next())
			{
				numeroregistro=numeroregistro+1;
				if(numeroregistro == regPorPagina+1){
					
					break;
				}	
					if(str_CodTipo.equals("org"))
					{
/*						temp_UsuariosRegistradosOrganizacionBean = new UsuariosRegistradosOrganizacionBean();
						temp_UsuariosRegistradosOrganizacionBean.setStr_RazonSocial(rs.getString(1));									
						str_fecha_formato=rs.getString(2);
						trace("FECHA"+ str_fecha_formato,request);	
						temp_UsuariosRegistradosOrganizacionBean.setStr_FechaRegistro(str_fecha_formato.substring(0,10));
						temp_UsuariosRegistradosOrganizacionBean.setStr_HoraRegistro(str_fecha_formato.substring(10,19));					
						temp_UsuariosRegistradosOrganizacionBean.setStr_Documento(rs.getString(3));
						temp_UsuariosRegistradosOrganizacionBean.setStr_NumeroUsuarios(rs.getString(4));	
						if(rs.getString(6)==null){
							temp_UsuariosRegistradosOrganizacionBean.setStr_Apellidos(rs.getString(5));
						}else{
							temp_UsuariosRegistradosOrganizacionBean.setStr_Apellidos(rs.getString(5) + " " + rs.getString(6) );							
						}		
						
						temp_UsuariosRegistradosOrganizacionBean.setStr_Nombres(rs.getString(7));
						str_fecha_formato=rs.getString(8);					
						trace("FECHA" + str_fecha_formato,request);							
						temp_UsuariosRegistradosOrganizacionBean.setStr_FechaUltimoAcceso(str_fecha_formato.substring(0,10));
						temp_UsuariosRegistradosOrganizacionBean.setStr_HoraUltimoAcceso(str_fecha_formato.substring(10,19));
*/						
						temp_UsuariosRegistradosOrganizacionBean = new UsuariosRegistradosOrganizacionBean();
						temp_UsuariosRegistradosOrganizacionBean.setStr_RazonSocial(rs.getString(2));									
						str_fecha_formato=rs.getString(3);
						if (isTrace(this)) trace("FECHA"+ str_fecha_formato,request);	
						temp_UsuariosRegistradosOrganizacionBean.setStr_FechaRegistro(str_fecha_formato.substring(0,10));
						temp_UsuariosRegistradosOrganizacionBean.setStr_HoraRegistro(str_fecha_formato.substring(10,19));					
						temp_UsuariosRegistradosOrganizacionBean.setStr_Documento(rs.getString(4));
						temp_UsuariosRegistradosOrganizacionBean.setStr_NumeroUsuarios(rs.getString(5));	
						if(rs.getString(7)==null){
							temp_UsuariosRegistradosOrganizacionBean.setStr_Apellidos(rs.getString(6));
						}else{
							temp_UsuariosRegistradosOrganizacionBean.setStr_Apellidos(rs.getString(6) + " " + rs.getString(7) );							
						}		
						
						temp_UsuariosRegistradosOrganizacionBean.setStr_Nombres(rs.getString(8));
						str_fecha_formato=rs.getString(9);					
						if (isTrace(this)) trace("FECHA" + str_fecha_formato,request);							
						temp_UsuariosRegistradosOrganizacionBean.setStr_FechaUltimoAcceso(str_fecha_formato.substring(0,10));
						temp_UsuariosRegistradosOrganizacionBean.setStr_HoraUltimoAcceso(str_fecha_formato.substring(10,19));
						

					}else{
						temp_UsuariosRegistradosIndividualesBean = new UsuariosRegistradosIndividualesBean();					
						temp_UsuariosRegistradosIndividualesBean.setStr_UsuarioIndividual(rs.getString(1));
						//str_fecha_formato=FechaUtil.toPaginado(rs.getString(2));
						str_fecha_formato=rs.getString(2);
						
						temp_UsuariosRegistradosIndividualesBean.setStr_FechaRegistro(str_fecha_formato.substring(0,10));
						temp_UsuariosRegistradosIndividualesBean.setStr_HoraRegistro(str_fecha_formato.substring(10,19));
						temp_UsuariosRegistradosIndividualesBean.setStr_TipoDocumento(rs.getString(3));
						temp_UsuariosRegistradosIndividualesBean.setStr_Documento(rs.getString(4));	
						if(rs.getString(6)==null){
							temp_UsuariosRegistradosIndividualesBean.setStr_Apellidos(rs.getString(5) );
						}else{
							temp_UsuariosRegistradosIndividualesBean.setStr_Apellidos(rs.getString(5) + " " + rs.getString(6));
						}								
						
						temp_UsuariosRegistradosIndividualesBean.setStr_Nombres(rs.getString(7));
						str_fecha_formato=rs.getString(8);					
						temp_UsuariosRegistradosIndividualesBean.setStr_FechaUltimoAcceso(str_fecha_formato.substring(0,10));
						temp_UsuariosRegistradosIndividualesBean.setStr_HoraUltimoAcceso(str_fecha_formato.substring(10,19));
					}						
		
					//totalgen=totalgen+totalind+totalorg;
					if(str_CodTipo.equals("org"))
					{
						temp_list_usuariosregistradosdetalle.add(temp_UsuariosRegistradosOrganizacionBean);
					}else{
						temp_list_usuariosregistradosdetalle.add(temp_UsuariosRegistradosIndividualesBean);				
					}
			}

			if(str_CodTipo.equals("org"))
			{
				temp_FormUsuariosRegistradosBean.setList_UsuariosRegistradosOrganizacion(temp_list_usuariosregistradosdetalle);
				temp_FormUsuariosRegistradosBean.setStr_NombreTipo("ORGANIZACION");
			}else{
				temp_FormUsuariosRegistradosBean.setList_UsuariosRegistradosIndividuales(temp_list_usuariosregistradosdetalle);
				temp_FormUsuariosRegistradosBean.setStr_NombreTipo("INDIVIDUAL");
			}

			temp_FormUsuariosRegistradosBean .setStr_NombreOfiReg(str_NomOfiReg);
			temp_FormUsuariosRegistradosBean .setStr_Date_Inicio(date_Inicio);
			temp_FormUsuariosRegistradosBean .setStr_Date_Fin(date_Fin);


			temp_FormUsuariosRegistradosBean.setStr_Ano_Fin(date_Ano_Fin);
			temp_FormUsuariosRegistradosBean.setStr_Ano_Inicio(date_Ano_Inicio);
			temp_FormUsuariosRegistradosBean.setStr_Mes_Fin(date_Mes_Fin);
			temp_FormUsuariosRegistradosBean.setStr_Mes_Inicio(date_Mes_Inicio);
			temp_FormUsuariosRegistradosBean.setStr_Dia_Fin(date_Dia_Fin);
			temp_FormUsuariosRegistradosBean.setStr_Dia_Inicio(date_Dia_Inicio);
			
			if(int_Pagina==1){
				temp_FormUsuariosRegistradosBean.setStr_Pagina_Anterior("");
			}else{		
				temp_FormUsuariosRegistradosBean.setStr_Pagina_Anterior(""+(int_Pagina-1));
			}
			if(numeroregistro==regPorPagina+1){
				temp_FormUsuariosRegistradosBean.setStr_Pagina_Siguiente(""+(int_Pagina+1));
			}else{		
				temp_FormUsuariosRegistradosBean.setStr_Pagina_Siguiente("");
			}				
			temp_FormUsuariosRegistradosBean.setStr_Pagina_Fin(""+int_Pagina);
			temp_FormUsuariosRegistradosBean.setStr_CodRegPub(str_CodRegPub);
			temp_FormUsuariosRegistradosBean.setStr_CodOfiReg(str_CodOfiReg);
			temp_FormUsuariosRegistradosBean.setStr_CodTipo(str_CodTipo);			
			
			
			req.setAttribute("formusuariosregistradosbean", temp_FormUsuariosRegistradosBean );
			
			response.setStyle("usuariosregistradosdetalle");
			conn.commit();
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
			
		}
		return response;
	}

}