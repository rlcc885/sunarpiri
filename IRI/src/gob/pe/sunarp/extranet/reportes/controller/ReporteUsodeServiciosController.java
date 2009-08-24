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
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
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

public class ReporteUsodeServiciosController extends ControllerExtension
{

	public ReporteUsodeServiciosController() {
		super();
		addState(new State("verFormulario", "muestra formulario de búsqueda de usuarios"));
		addState(new State("verReporte", "muestra el resultado de la búsqueda de usuarios"));
		addState(new State("verDetalle", "muestra el resultado de la búsqueda detallada de usuarios"));		
		addState(new State("exportarReporte", "exporta el resultado de la búsqueda detallada de usuarios"));		
	}


	public ControllerResponse runVerFormularioState(
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

			
			FormUsodeServiciosBean temp_FormUsodeServiciosBean = new FormUsodeServiciosBean();

					
			StringBuffer strbfr_sql = new StringBuffer();
			String str_sql = null;
			Statement stmt = null;
			PreparedStatement prpstmt =null;
			ResultSet rs = null;

			RegistroPublicoBean temp_RegistroPublicoBean =null;
			OficinaRegistralBean temp_OficinaRegistralBean =null;
			TipoUsuarioBean temp_TipoUsuarioBean=null;
			java.util.List temp_list_RegistroPublico = new java.util.ArrayList();
			java.util.List temp_list_OficinaRegistral = new java.util.ArrayList();
			java.util.List temp_list_TipoUsuario = new java.util.ArrayList();						
						
			strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
			strbfr_sql.append("FROM REGIS_PUBLICO RP ");
			strbfr_sql.append("WHERE RP.REG_PUB_ID <> '00' ");
			strbfr_sql.append("ORDER BY RP.NOMBRE" );
	
			prpstmt = conn.prepareStatement(strbfr_sql.toString());
						
			rs = prpstmt.executeQuery();
			int j=0;	
			if (j==0)
			{
				temp_RegistroPublicoBean = new RegistroPublicoBean();								
				temp_RegistroPublicoBean.setCodigoRegistroPublico("TODAS");
				temp_RegistroPublicoBean.setNombreRegistroPublico("TODAS");					
				temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("selected");
				temp_list_RegistroPublico.add(temp_RegistroPublicoBean);				
			}			
			while(rs.next())
			{
				temp_RegistroPublicoBean = new RegistroPublicoBean();			
				temp_RegistroPublicoBean.setCodigoRegistroPublico(rs.getString(1));
				temp_RegistroPublicoBean.setNombreRegistroPublico(rs.getString(2));					
				temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("");				
				j=j+1;
				temp_list_RegistroPublico.add(temp_RegistroPublicoBean);
			}	
			
			strbfr_sql.setLength(0);
			strbfr_sql.append("SELECT OFR.REG_PUB_ID,OFR.OFIC_REG_ID, OFR.NOMBRE  ");
			strbfr_sql.append("FROM OFIC_REGISTRAL OFR ");
			//strbfr_sql.append("WHERE RP.REG_PUB_ID<>'00' ");
			strbfr_sql.append("ORDER BY OFR.NOMBRE" );
	
			//Iniciar Transaccion
			prpstmt = conn.prepareStatement(strbfr_sql.toString());
						
			//prepare
			rs=null;
			rs = prpstmt.executeQuery();
			int k=0;	
			if (k==0)
			{
				temp_OficinaRegistralBean = new OficinaRegistralBean();								
				temp_OficinaRegistralBean.setCodigoRegistroPublico("TODAS");				
				temp_OficinaRegistralBean.setCodigoOficinaRegistral("TODAS");
				temp_OficinaRegistralBean.setNombreOficinaRegistral("TODAS");					
				temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("selected");
				temp_list_OficinaRegistral.add(temp_OficinaRegistralBean);
			}			
			while(rs.next())
			{
				temp_OficinaRegistralBean = new OficinaRegistralBean();								
				temp_OficinaRegistralBean.setCodigoRegistroPublico(rs.getString(1));
				temp_OficinaRegistralBean.setCodigoOficinaRegistral(rs.getString(2));
				temp_OficinaRegistralBean.setNombreOficinaRegistral(rs.getString(3));					
				temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("");
				k=k+1;
				temp_list_OficinaRegistral.add(temp_OficinaRegistralBean);
			}	

			temp_TipoUsuarioBean = new TipoUsuarioBean();
			temp_TipoUsuarioBean.setCodigoTipoUsuario("1");
			temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("selected");
			temp_TipoUsuarioBean.setNombreTipoUsuario("Individual");	
			temp_list_TipoUsuario.add(temp_TipoUsuarioBean);
			
			temp_TipoUsuarioBean = new TipoUsuarioBean();				
			temp_TipoUsuarioBean.setCodigoTipoUsuario("0");
			temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("");
			temp_TipoUsuarioBean.setNombreTipoUsuario("Organizacion");
			temp_list_TipoUsuario.add(temp_TipoUsuarioBean);			

			temp_FormUsodeServiciosBean.setList_RegistroPublico(temp_list_RegistroPublico);
			temp_FormUsodeServiciosBean.setList_OficinaRegistral(temp_list_OficinaRegistral);
			temp_FormUsodeServiciosBean.setList_TipoUsuario(temp_list_TipoUsuario);
			req.setAttribute("formusodeserviciosbean", temp_FormUsodeServiciosBean);
			
			//session.setAttribute("usuariosregistradosbean", usuariosregistradosbean);

			/*if(secadmin.grupoDelUsuario(userLogged.getUserId()) == 1 || secadmin.grupoDelUsuario(userLogged.getUserId()) == 7){
				//mostrar 3 filtros de búsqueda
				flagAdminInter = true; 
			}
			if(secadmin.grupoDelUsuario(userLogged.getUserId()) == 4){
				//mostrar 2 filtros de búsqueda
				flagAdminExtn = true; 
			}*/
			
			response.setStyle("usodeservicios");
			
		}catch(Throwable ex)
		{
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally
		{
			pool.release(conn);
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
					
		try 
		{
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");

			// Instancia de Bean de datos del Formulario //
			FormUsodeServiciosBean temp_FormUsodeServiciosBean = new FormUsodeServiciosBean();
							
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
			
			String str_RegistroPublico  = request.getParameter("sel_RegistroPublico");
			String str_OficinaRegistral  = request.getParameter("sel_OficinaRegistral");			
			String str_TipoUsuario  = request.getParameter("sel_TipoUsuario");			

			String str_nombre_registro_publico="";	
			String str_nombre_oficina_registral="";

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




			String date_Inicio_Ora=FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Inicio),Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio) );//+" 00:00:00");
			String date_Fin_Ora= FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Fin),Integer.parseInt(date_Mes_Fin),Integer.parseInt(date_Ano_Fin) );// +" 23:59:59");

			
			if((str_RegistroPublico==null || !str_RegistroPublico.equals("")) || (str_RegistroPublico==null || str_RegistroPublico.equals(""))){
			}	

			//String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
			//String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);
			
			//String date_Inicio_Ora = date_Inicio +" 00:00:00";
			//String date_Fin_Ora = date_Fin +" 23:59:59";
			StringBuffer strbfr_sql = new StringBuffer();
			java.sql.PreparedStatement prpstmt =null;			
			java.sql.ResultSet rs = null;			
			java.sql.Statement stm = null;

			java.util.List temp_list_usodeservicios = new java.util.ArrayList();
			java.util.List temp_list_totalusodeservicios = new java.util.ArrayList();	
/*

			int tipo_strbfr_sql=1;

			int totalind = 0;
			int totalorg = 0;
			int totalgen = 0;
			double porcentaje = 0;			
			String str_porcentaje="";
			//Generar SQL
			int temp_total_individual = 0;
			int temp_total_organizacion = 0;			
			int temp_total_general = 0;			
*/			
			int int_contador_linea =0;
			
			UsodeServiciosBean temp_UsodeServiciosBean =null;
			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB TOTAL

			if(str_RegistroPublico.equals("TODAS") && str_OficinaRegistral.equals("TODAS")){
				//strbfr_sql.append(" select decode(t.servicio_id,null,'TOTAL', initcap(t.regpub)) regpub,  ");
				strbfr_sql.setLength(0);
				strbfr_sql.append(" select decode(t.regpub,null,'TOTAL', t.regpub) regpub,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 111,'06. Copia Literal Certificada', 112,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
//				Inicio:jascencio:31/08/2007
				//CC: SUNARP-REGMOBOCN.-2006
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales') as tipo,");
				//Fin:jascencio

				strbfr_sql.append(" sum(nvl(t.rpn,0)) rpn, sum(nvl(t.rpj,0)) rpj, sum(nvl(t.rpi,0)) rpi, sum(nvl(t.rbm,0)) rbm, sum(nvl(t.total,0)) sub_total ");	
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porSUBT,0)), '990.99') porSubTotal ");
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porTOT,0)), '990.99') porTotal ");
				strbfr_sql.append(" from (select a.reg_pub_id,a.regpub,a.servicio_id,a.nombre, b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT");
				strbfr_sql.append(" from (select t2.nombre regpub, t2.reg_pub_id, t1.servicio_id, t1.nombre");
				strbfr_sql.append(" from   tm_servicio t1, regis_publico t2");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140)) a,");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140)) a,");
				//Inicio:jascencio:31/08/2007
				//CC:SUNARP-REGMOBCON-2006
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196)) a,");
				//Fin:jascencio

				strbfr_sql.append(" (select t1.cod_reg_pub, t1.servicio_id, ");
				strbfr_sql.append(" sum(t1.rpn) rpn , sum(t1.rpj) rpj, sum(t1.rpi) rpi, sum(t1.rbm) rbm, ");
				strbfr_sql.append(" sum(t1.rpn + t1.rpj + t1.rpi + t1.rbm) total");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(partition by t1.cod_reg_pub),2), '990.99') as porSUBT ");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(),2), '990.99') as porTOT ");
				strbfr_sql.append(" from   uso_servicio t1");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140) ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112120,121,122,123,124,125,140) ");
				//Inicio:jascencio:31/08/2007
				//CC: SUNARP-REGMOBCON-2006
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196) ");
				//Fin:jascencio

				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70) and");				
				strbfr_sql.append(" and t1.tipo_usr = ").append(str_TipoUsuario).append(" ");				
				strbfr_sql.append(" and to_date(t1.aaaammdd,'yyyy/mm/dd') between to_date(").append(date_Inicio_Ora).append(",'yyyy/mm/dd') and ");
				strbfr_sql.append(" to_date(").append(date_Fin_Ora).append(",'yyyy/mm/dd')");
				strbfr_sql.append(" group by t1.cod_reg_pub, t1.servicio_id)b");
				strbfr_sql.append(" where b.cod_reg_pub(+) = a.reg_pub_id and");
				strbfr_sql.append(" b.servicio_id(+) = a.servicio_id");
				strbfr_sql.append(" group by a.reg_pub_id,a.regpub,a.servicio_id, a.nombre,b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT) t");
				//strbfr_sql.append(" group by cube (t.regpub, decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales'))");
				strbfr_sql.append(" group by cube (t.regpub,");
				//Inicio:jascencio:31/08/2007
				//CC:SUNARP-REGMOBCON-2006
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales'))");
				//Fin:jascencio
				if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
				//prpstmt = conn.prepareStatement(strbfr_sql.toString());
				stm = conn.createStatement();
				/*prpstmt.setString(1, str_TipoUsuario);
				prpstmt.setString(2, date_Inicio_Ora);
				prpstmt.setString(3, date_Fin_Ora);*/
				
				//prpstmt.setString(1, date_Inicio_Ora);
				//prpstmt.setString(2, date_Fin_Ora);
				
				if (isTrace(this)) trace("Prepare"+ stm.toString(), request);
				if (isTrace(this)) System.out.println("query - " + strbfr_sql.toString());
				rs = stm.executeQuery(strbfr_sql.toString());
				
				str_nombre_registro_publico="";

				while(rs.next())
				{
					if (isTrace(this)) trace("str_nombre_registro_publico"+ rs.getString(1).toString(), request);

					if(!str_nombre_registro_publico.equals(rs.getString(1))){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreRegistroPublico(rs.getString(1));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("##e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						int_contador_linea=int_contador_linea+1;
	
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);
						
						str_nombre_registro_publico=rs.getString(1).toString();
						if (isTrace(this)) trace("str_nombre_registro_publico"+ str_nombre_registro_publico, request);
					}					
					if(str_nombre_registro_publico.equals("TOTAL")){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						//temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);					
					}else{
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);											
					}	
				
				}	
				
			}
			if(!str_RegistroPublico.equals("TODAS") && str_OficinaRegistral.equals("TODAS")){
			
				
				strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
				strbfr_sql.append("FROM REGIS_PUBLICO RP ");
				strbfr_sql.append("WHERE RP.REG_PUB_ID=? ");
				strbfr_sql.append("ORDER BY RP.NOMBRE" );

				//Iniciar Transaccion
				prpstmt = conn.prepareStatement(strbfr_sql.toString());
				prpstmt.setString(1, str_RegistroPublico);						
				//prepare
				rs = prpstmt.executeQuery();	
				while(rs.next())
				{
					str_nombre_registro_publico=rs.getString(2);	
				}	
				
				strbfr_sql.setLength(0);
				strbfr_sql.append(" select decode(t.regpub,null,'TOTAL', t.regpub) regpub,  " );
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 111,'06. Copia Literal Certificada', 112,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
//				Inicio:jascencio:31/08/2007
				//CC: SUNARP-REGMOBOCN.-2006
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales') as tipo,");
				//Fin:jascencio

				strbfr_sql.append(" sum(nvl(t.rpn,0)) rpn, sum(nvl(t.rpj,0)) rpj, sum(nvl(t.rpi,0)) rpi, sum(nvl(t.rbm,0)) rbm, sum(nvl(t.total,0)) sub_total ");	
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porSUBT,0)), '990.99') porSubTotal ");						
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porTOT,0)), '990.99') porTotal ");										
				strbfr_sql.append(" from (select a.reg_pub_id,a.regpub,a.servicio_id,a.nombre, b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT");
				strbfr_sql.append(" from (select t2.nombre regpub, t2.reg_pub_id, t1.servicio_id, t1.nombre");						
				strbfr_sql.append(" from   tm_servicio t1, regis_publico t2");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140)) a,");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140)) a,");
//				Inicio:jascencio:31/08/2007
				//CC:SUNARP-REGMOBCON-2006
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196)) a,");
				//Fin:jascencio

				strbfr_sql.append(" (select t1.cod_reg_pub, t1.servicio_id, ");
				strbfr_sql.append(" sum(t1.rpn) rpn , sum(t1.rpj) rpj, sum(t1.rpi) rpi, sum(t1.rbm) rbm, ");
				strbfr_sql.append(" sum(t1.rpn + t1.rpj + t1.rpi + t1.rbm) total");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(partition by t1.cod_reg_pub),2), '990.99') as porSUBT ");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(),2), '990.99') as porTOT ");
				strbfr_sql.append(" from   uso_servicio t1");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140) ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140) ");
//				Inicio:jascencio:31/08/2007
				//CC: SUNARP-REGMOBCON-2006
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196) ");
				//Fin:jascencio	
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70) and");				
				strbfr_sql.append(" and t1.tipo_usr=? ");				
				strbfr_sql.append(" and to_date(t1.aaaammdd,'yyyy/mm/dd') between to_date(?,'yyyy/mm/dd') and ");
				strbfr_sql.append(" to_date(?,'yyyy/mm/dd')");
				strbfr_sql.append(" group by t1.cod_reg_pub, t1.servicio_id)b");
				strbfr_sql.append(" where b.cod_reg_pub(+) = a.reg_pub_id and");
				strbfr_sql.append(" b.servicio_id(+) = a.servicio_id");
				strbfr_sql.append(" group by a.reg_pub_id,a.regpub,a.servicio_id, a.nombre,b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT) t");
				//strbfr_sql.append(" group by cube (t.regpub, decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales'))");
				strbfr_sql.append(" group by cube (t.regpub,");
				//Inicio:jascencio:31/08/2007
				//CC:SUNARP-REGMOBCON-2006
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales'))");
				//Fin:jascencio

				if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
				prpstmt = conn.prepareStatement(strbfr_sql.toString());				
				prpstmt.setString(1, str_TipoUsuario);
				prpstmt.setString(2, date_Inicio_Ora);
				prpstmt.setString(3, date_Fin_Ora);
				
				//prpstmt.setString(1, date_Inicio_Ora);
				//prpstmt.setString(2, date_Fin_Ora);
				
				if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
				rs=null;
				rs = prpstmt.executeQuery();

			//Iniciar Transaccion
			//prepare
			//str_nombre_registro_publico="";
				while(rs.next())
				{
					if (isTrace(this)) trace("str_nombre_registro_publico"+ rs.getString(1).toString(), request);
					if((str_nombre_registro_publico.equals(rs.getString(1).toString()) && int_contador_linea==0 )|| (rs.getString(1).toString().equals("TOTAL") && !str_nombre_registro_publico.equals("TOTAL")) ){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreRegistroPublico(rs.getString(1));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("##e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						int_contador_linea=int_contador_linea+1;
	
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);
						
						str_nombre_registro_publico=rs.getString(1).toString();
						if (isTrace(this)) trace("str_nombre_registro_publico"+ str_nombre_registro_publico, request);
					}					
					if(str_nombre_registro_publico.equals("TOTAL")){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);					
					}else{
						if(str_nombre_registro_publico.equals(rs.getString(1).toString())){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));					
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);											
						}
					}
				
				}	
			}	
			
			
			
			if(!str_RegistroPublico.equals("TODAS") && !str_OficinaRegistral.equals("TODAS")){
			
/*				
				strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
				strbfr_sql.append("FROM REGIS_PUBLICO RP ");
				strbfr_sql.append("WHERE RP.REG_PUB_ID=? ");
				strbfr_sql.append("ORDER BY RP.NOMBRE" );
*/				
				strbfr_sql.append("SELECT OFR.REG_PUB_ID,OFR.OFIC_REG_ID, OFR.NOMBRE  ");
				strbfr_sql.append("FROM OFIC_REGISTRAL OFR ");
				strbfr_sql.append("WHERE OFR.REG_PUB_ID= ?");
				strbfr_sql.append("AND OFR.OFIC_REG_ID= ?");
				strbfr_sql.append("ORDER BY OFR.NOMBRE" );
/*				
				strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
				strbfr_sql.append("FROM REGIS_PUBLICO RP ");
				strbfr_sql.append("WHERE RP.REG_PUB_ID=? ");
				strbfr_sql.append("ORDER BY RP.NOMBRE" );
*/
				//Iniciar Transaccion
				prpstmt = conn.prepareStatement(strbfr_sql.toString());
				prpstmt.setString(1, str_RegistroPublico);						
				prpstmt.setString(2, str_OficinaRegistral);				
				//prepare
				rs = prpstmt.executeQuery();	
				java.util.List temp_sel_list_OficinaRegistral = new java.util.ArrayList();
				while(rs.next())
				{
					str_nombre_oficina_registral=rs.getString(3).toString();	
					//temp_sel_list_OficinaRegistral.add(str_nombre_oficina_registral);	
					//int a= temp_sel_list_OficinaRegistral.indexOf(str_nombre_oficina_registral);
				}	
				
				strbfr_sql.setLength(0);
				strbfr_sql.append(" select decode(t.oficina,NULL,'TOTAL',t.oficina) oficina,  " );
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 111,'06. Copia Literal Certificada', 112,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
//				Inicio:jascencio:31/08/2007
				//CC: SUNARP-REGMOBOCN.-2006
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales') as tipo,");
				//Fin:jascencio

				strbfr_sql.append(" sum(nvl(t.rpn,0)) rpn, sum(nvl(t.rpj,0)) rpj, sum(nvl(t.rpi,0)) rpi, sum(nvl(t.rbm,0)) rbm, sum(nvl(t.total,0)) sub_total ");	
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porSUBT,0)), '990.99') porSubTotal ");						
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porTOT,0)), '990.99') porTotal ");										
                strbfr_sql.append(" from (select a.reg_pub_id,a.oficina,a.servicio_id,a.nombre, b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT ");
				strbfr_sql.append(" from (select t2.nombre oficina, t2.reg_pub_id, t2.ofic_reg_id, t1.servicio_id, t1.nombre ");			
				strbfr_sql.append(" from   tm_servicio t1, ofic_registral t2 ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140)) a,");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140)) a,");
//				Inicio:jascencio:31/08/2007
				//CC:SUNARP-REGMOBCON-2006
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196)) a,");
				//Fin:jascencio

				strbfr_sql.append("	(select  t1.cod_reg_pub, t1.cod_ofic_reg, t1.servicio_id, ");
				strbfr_sql.append(" sum(t1.rpn) rpn , sum(t1.rpj) rpj, sum(t1.rpi) rpi, sum(t1.rbm) rbm, ");
				strbfr_sql.append(" sum(t1.rpn + t1.rpj + t1.rpi + t1.rbm) total");
				
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(partition by t1.cod_reg_pub, t1.cod_ofic_reg),2), '990.99') as porSUBT ");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(),2), '990.99') as porTOT ");
				strbfr_sql.append(" from   uso_servicio t1");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140) ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140) ");
//				Inicio:jascencio:31/08/2007
				//CC: SUNARP-REGMOBCON-2006
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196) ");
				//Fin:jascencio

				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70) and");				
				strbfr_sql.append(" and t1.tipo_usr=? ");				
				strbfr_sql.append(" and to_date(t1.aaaammdd,'yyyy/mm/dd') between to_date(?,'yyyy/mm/dd') and ");
				strbfr_sql.append(" to_date(?,'yyyy/mm/dd')");
				strbfr_sql.append(" group by t1.cod_reg_pub, t1.cod_ofic_reg, t1.servicio_id)b");
				strbfr_sql.append(" where b.cod_reg_pub(+) = a.reg_pub_id ");
				strbfr_sql.append(" and b.cod_ofic_reg(+) = a.ofic_reg_id ");
				strbfr_sql.append(" and b.servicio_id(+) = a.servicio_id");
				strbfr_sql.append(" group by a.reg_pub_id,a.oficina,a.oficina,a.servicio_id, a.nombre,b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT) t ");
				//strbfr_sql.append(" group by cube (t.oficina, decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales'))");
				strbfr_sql.append(" group by cube (t.oficina,");
				//Inicio:jascencio:31/08/2007
				//CC:SUNARP-REGMOBCON-2006
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales'))");
				//Fin:jascencio

				if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
				prpstmt = conn.prepareStatement(strbfr_sql.toString());				
				prpstmt.setString(1, str_TipoUsuario);
				prpstmt.setString(2, date_Inicio_Ora);
				prpstmt.setString(3, date_Fin_Ora);
				
				//prpstmt.setString(1, date_Inicio_Ora);
				//prpstmt.setString(2, date_Fin_Ora);
				
				if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
				rs=null;
				rs = prpstmt.executeQuery();

			//Iniciar Transaccion
			//prepare
			//str_nombre_registro_publico="";
				while(rs.next())
				{
					if (isTrace(this)) trace("str_nombre_oficina_registral"+ rs.getString(1).toString(), request);
					if((str_nombre_oficina_registral.equals(rs.getString(1).toString()) && int_contador_linea==0 )|| (rs.getString(1).toString().equals("TOTAL") && !str_nombre_oficina_registral.equals("TOTAL")) ){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreRegistroPublico(rs.getString(1));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("##e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						int_contador_linea=int_contador_linea+1;
	
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);
						
						str_nombre_oficina_registral=rs.getString(1).toString();
						if (isTrace(this)) trace("str_nombre_oficina_registral"+ str_nombre_oficina_registral, request);
					}					
					if(str_nombre_oficina_registral.equals("TOTAL")){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);					
					}else{
						if(str_nombre_oficina_registral.equals(rs.getString(1).toString())){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);											
						}
					}
				
				}	
			}			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++						
			//StringBuffer strbfr_sql = new StringBuffer();
			strbfr_sql.setLength(0);			
			rs = null;

			RegistroPublicoBean temp_RegistroPublicoBean =null;
			OficinaRegistralBean temp_OficinaRegistralBean =null;
			TipoUsuarioBean temp_TipoUsuarioBean=null;
			java.util.List temp_list_RegistroPublico = new java.util.ArrayList();
			java.util.List temp_list_OficinaRegistral = new java.util.ArrayList();
			java.util.List temp_list_TipoUsuario = new java.util.ArrayList();						
						
			strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
			strbfr_sql.append("FROM REGIS_PUBLICO RP ");
			strbfr_sql.append("WHERE RP.REG_PUB_ID<>'00' ");
			strbfr_sql.append("ORDER BY RP.NOMBRE" );
	
			//Iniciar Transaccion
			prpstmt = conn.prepareStatement(strbfr_sql.toString());
						
			//prepare
			rs = prpstmt.executeQuery();
			int j=0;	
			if (j==0)
			{
				temp_RegistroPublicoBean = new RegistroPublicoBean();								
				temp_RegistroPublicoBean.setCodigoRegistroPublico("TODAS");
				temp_RegistroPublicoBean.setNombreRegistroPublico("TODAS");		
				if(str_RegistroPublico.equals("TODAS")){
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("selected");									
				}else{
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("");				
				}							

				temp_list_RegistroPublico.add(temp_RegistroPublicoBean);				
			}			
			while(rs.next())
			{
				temp_RegistroPublicoBean = new RegistroPublicoBean();			
				temp_RegistroPublicoBean.setCodigoRegistroPublico(rs.getString(1));
				temp_RegistroPublicoBean.setNombreRegistroPublico(rs.getString(2));					
				if(str_RegistroPublico.equals(rs.getString(1))){
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("selected");									
				}else{
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("");				
				}				
				j=j+1;
				temp_list_RegistroPublico.add(temp_RegistroPublicoBean);
			}	
			strbfr_sql.setLength(0);
			strbfr_sql.append("SELECT OFR.REG_PUB_ID,OFR.OFIC_REG_ID, OFR.NOMBRE  ");
			strbfr_sql.append("FROM OFIC_REGISTRAL OFR ");
			//strbfr_sql.append("WHERE RP.REG_PUB_ID<>'00' ");
			strbfr_sql.append("ORDER BY OFR.NOMBRE" );
	
			//Iniciar Transaccion
			prpstmt = conn.prepareStatement(strbfr_sql.toString());
						
			//prepare
			rs=null;
			rs = prpstmt.executeQuery();
			int k=0;	
			if (k==0)
			{
				temp_OficinaRegistralBean = new OficinaRegistralBean();								
				temp_OficinaRegistralBean.setCodigoRegistroPublico("TODAS");				
				temp_OficinaRegistralBean.setCodigoOficinaRegistral("TODAS");
				temp_OficinaRegistralBean.setNombreOficinaRegistral("TODAS");					
				if(str_OficinaRegistral.equals("TODAS")){
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("selected");
				}else{
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("");					
				}
				temp_list_OficinaRegistral.add(temp_OficinaRegistralBean);
			}			
			while(rs.next())
			{
				temp_OficinaRegistralBean = new OficinaRegistralBean();								
				temp_OficinaRegistralBean.setCodigoRegistroPublico(rs.getString(1));
				temp_OficinaRegistralBean.setCodigoOficinaRegistral(rs.getString(2));
				temp_OficinaRegistralBean.setNombreOficinaRegistral(rs.getString(3));					
				if(str_RegistroPublico.equals(rs.getString(1)) && str_OficinaRegistral.equals(rs.getString(2))){
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("selected");
				}else{
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("");
				}

				k=k+1;
				temp_list_OficinaRegistral.add(temp_OficinaRegistralBean);
			}	

			if(str_TipoUsuario.equals("1")){
				temp_TipoUsuarioBean = new TipoUsuarioBean();
				temp_TipoUsuarioBean.setCodigoTipoUsuario("1");
				temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("selected");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Individual");	
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);		
				temp_TipoUsuarioBean = new TipoUsuarioBean();				
				temp_TipoUsuarioBean.setCodigoTipoUsuario("0");
				temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Organizacion");
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);			
			}else{
				temp_TipoUsuarioBean = new TipoUsuarioBean();
				temp_TipoUsuarioBean.setCodigoTipoUsuario("1");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Individual");	
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);		
				temp_TipoUsuarioBean = new TipoUsuarioBean();				
				temp_TipoUsuarioBean.setCodigoTipoUsuario("0");
				temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("selected");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Organizacion");
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);							
			}

			temp_FormUsodeServiciosBean.setList_RegistroPublico(temp_list_RegistroPublico);
			temp_FormUsodeServiciosBean.setList_OficinaRegistral(temp_list_OficinaRegistral);
			temp_FormUsodeServiciosBean.setList_TipoUsuario(temp_list_TipoUsuario);
			temp_FormUsodeServiciosBean.setStr_RegistroPublico(str_RegistroPublico);
			temp_FormUsodeServiciosBean.setStr_OficinaRegistral(str_OficinaRegistral);			
			temp_FormUsodeServiciosBean.setStr_TipoUsuario(str_TipoUsuario);
			temp_FormUsodeServiciosBean.setList_UsodeServicios(temp_list_usodeservicios);
			temp_FormUsodeServiciosBean.setStr_Date_Inicio(date_Inicio);
			temp_FormUsodeServiciosBean.setStr_Date_Fin(date_Fin);

			req.setAttribute("formusoserviciosbean", temp_FormUsodeServiciosBean);
			
/*			
			temp_FormUsuariosRegistradosBean .setList_Dias(temp_list_Dia);
			temp_FormUsuariosRegistradosBean .setList_Meses(temp_list_Mes);			
			temp_FormUsuariosRegistradosBean .setList_Anos(temp_list_Ano);
			temp_FormUsuariosRegistradosBean .setList_UsuariosRegistrados(temp_list_usuariosregistrados);
			temp_FormUsuariosRegistradosBean .setList_TotalesUsuariosRegistrados(temp_list_totalesusuariosregistrados);			
			
			temp_FormUsuariosRegistradosBean .setStr_Date_Inicio(date_Inicio);
			temp_FormUsuariosRegistradosBean .setStr_Date_Fin(date_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Ano_Fin(date_Ano_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Ano_Inicio(date_Ano_Inicio);
			temp_FormUsuariosRegistradosBean .setStr_Mes_Fin(date_Mes_Fin);
			temp_FormUsuariosRegistradosBean .setStr_Mes_Inicio(date_Mes_Inicio);


			//session.setAttribute("usuariosregistradosbean",usuariosregistradosbean);
	
*/						
			req.setAttribute("formusodeserviciosbean", temp_FormUsodeServiciosBean );
			//Retornando el valor para próximas búsquedas: Por Fechas
			//ExpressoHttpSessionBean.getRequest(request).setAttribute("fecini", date_inicio);
			//ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfin", date_fin);

			//ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", "asdasd");
			//ExpressoHttpSessionBean.getRequest(request).setAttribute("tipousuario", "asdasd");

			response.setStyle("usodeservicios");
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
			
			
			// Validar Sesion //				
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			// Recuperar Datos de Session //
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");

			// Instancia de Bean de datos del Formulario //
			FormUsodeServiciosBean temp_FormUsodeServiciosBean = new FormUsodeServiciosBean();
							
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
			
			String str_RegistroPublico  = request.getParameter("sel_RegistroPublico");
			String str_OficinaRegistral  = request.getParameter("sel_OficinaRegistral");			
			String str_TipoUsuario  = request.getParameter("sel_TipoUsuario");			

			String str_nombre_registro_publico="";	
			String str_nombre_oficina_registral="";

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




			String date_Inicio_Ora=FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Inicio),Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio) );//+" 00:00:00");
			String date_Fin_Ora= FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Fin),Integer.parseInt(date_Mes_Fin),Integer.parseInt(date_Ano_Fin) );// +" 23:59:59");

			
			if((str_RegistroPublico==null || !str_RegistroPublico.equals("")) || (str_RegistroPublico==null || str_RegistroPublico.equals(""))){
			}	

			//String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
			//String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);
			
			//String date_Inicio_Ora = date_Inicio +" 00:00:00";
			//String date_Fin_Ora = date_Fin +" 23:59:59";
			StringBuffer strbfr_sql = new StringBuffer();
			java.sql.PreparedStatement prpstmt =null;			
			java.sql.ResultSet rs = null;			

			java.util.List temp_list_usodeservicios = new java.util.ArrayList();
			java.util.List temp_list_totalusodeservicios = new java.util.ArrayList();	
/*

			int tipo_strbfr_sql=1;

			int totalind = 0;
			int totalorg = 0;
			int totalgen = 0;
			double porcentaje = 0;			
			String str_porcentaje="";
			//Generar SQL
			int temp_total_individual = 0;
			int temp_total_organizacion = 0;			
			int temp_total_general = 0;			
*/			
			int int_contador_linea =0;
			
			UsodeServiciosBean temp_UsodeServiciosBean =null;
			///++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//WEB TOTAL

			if(str_RegistroPublico.equals("TODAS") && str_OficinaRegistral.equals("TODAS")){
				//strbfr_sql.append(" select decode(t.servicio_id,null,'TOTAL', initcap(t.regpub)) regpub,  ");
				strbfr_sql.setLength(0);
				strbfr_sql.append(" select decode(t.regpub,null,'TOTAL', t.regpub) regpub,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 111,'06. Copia Literal Certificada', 112,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales') as tipo,");
				//Fin:mgarate

				strbfr_sql.append(" sum(nvl(t.rpn,0)) rpn, sum(nvl(t.rpj,0)) rpj, sum(nvl(t.rpi,0)) rpi, sum(nvl(t.rbm,0)) rbm, sum(nvl(t.total,0)) sub_total ");	
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porSUBT,0)), '990.99') porSubTotal ");
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porTOT,0)), '990.99') porTotal ");
				strbfr_sql.append(" from (select a.reg_pub_id,a.regpub,a.servicio_id,a.nombre, b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT");
				strbfr_sql.append(" from (select t2.nombre regpub, t2.reg_pub_id, t1.servicio_id, t1.nombre");
				strbfr_sql.append(" from   tm_servicio t1, regis_publico t2");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140)) a,");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140)) a,");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196)) a,");
				//Fin:mgarate

				strbfr_sql.append(" (select t1.cod_reg_pub, t1.servicio_id, ");
				strbfr_sql.append(" sum(t1.rpn) rpn , sum(t1.rpj) rpj, sum(t1.rpi) rpi, sum(t1.rbm) rbm, ");
				strbfr_sql.append(" sum(t1.rpn + t1.rpj + t1.rpi + t1.rbm) total");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(partition by t1.cod_reg_pub),2), '990.99') as porSUBT ");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(),2), '990.99') as porTOT ");
				strbfr_sql.append(" from   uso_servicio t1");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140) ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112120,121,122,123,124,125,140) ");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196) ");
				//Fin:mgarate

				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70) and");				
				strbfr_sql.append(" and t1.tipo_usr=? ");				
				strbfr_sql.append(" and to_date(t1.aaaammdd,'yyyy/mm/dd') between to_date(?,'yyyy/mm/dd') and ");
				strbfr_sql.append(" to_date(?,'yyyy/mm/dd')");
				strbfr_sql.append(" group by t1.cod_reg_pub, t1.servicio_id)b");
				strbfr_sql.append(" where b.cod_reg_pub(+) = a.reg_pub_id and");
				strbfr_sql.append(" b.servicio_id(+) = a.servicio_id");
				strbfr_sql.append(" group by a.reg_pub_id,a.regpub,a.servicio_id, a.nombre,b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT) t");
				//strbfr_sql.append(" group by cube (t.regpub, decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales'))");
				strbfr_sql.append(" group by cube (t.regpub,");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales'))");
				//Fin:mgarate
				if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
				prpstmt = conn.prepareStatement(strbfr_sql.toString());				
				prpstmt.setString(1, str_TipoUsuario);
				prpstmt.setString(2, date_Inicio_Ora);
				prpstmt.setString(3, date_Fin_Ora);
				
				//prpstmt.setString(1, date_Inicio_Ora);
				//prpstmt.setString(2, date_Fin_Ora);
				
				if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
				if (isTrace(this)) System.out.println("query - " + strbfr_sql.toString());
				rs = prpstmt.executeQuery();
				str_nombre_registro_publico="";

				while(rs.next())
				{
					if (isTrace(this)) trace("str_nombre_registro_publico"+ rs.getString(1).toString(), request);

					if(!str_nombre_registro_publico.equals(rs.getString(1))){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreRegistroPublico(rs.getString(1));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("##e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						int_contador_linea=int_contador_linea+1;
	
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);
						
						str_nombre_registro_publico=rs.getString(1).toString();
						if (isTrace(this)) trace("str_nombre_registro_publico"+ str_nombre_registro_publico, request);
					}					
					if(str_nombre_registro_publico.equals("TOTAL")){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);					
					}else{
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);											
					}	
				
				}	
				
			}
			if(!str_RegistroPublico.equals("TODAS") && str_OficinaRegistral.equals("TODAS")){
			
				
				strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
				strbfr_sql.append("FROM REGIS_PUBLICO RP ");
				strbfr_sql.append("WHERE RP.REG_PUB_ID=? ");
				strbfr_sql.append("ORDER BY RP.NOMBRE" );

				//Iniciar Transaccion
				prpstmt = conn.prepareStatement(strbfr_sql.toString());
				prpstmt.setString(1, str_RegistroPublico);						
				//prepare
				rs = prpstmt.executeQuery();	
				while(rs.next())
				{
					str_nombre_registro_publico=rs.getString(2);	
				}	
				
				strbfr_sql.setLength(0);
				strbfr_sql.append(" select decode(t.regpub,null,'TOTAL', t.regpub) regpub,  " );
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 111,'06. Copia Literal Certificada', 112,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales') as tipo,");
				//Fin:mgarate

				strbfr_sql.append(" sum(nvl(t.rpn,0)) rpn, sum(nvl(t.rpj,0)) rpj, sum(nvl(t.rpi,0)) rpi, sum(nvl(t.rbm,0)) rbm, sum(nvl(t.total,0)) sub_total ");	
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porSUBT,0)), '990.99') porSubTotal ");						
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porTOT,0)), '990.99') porTotal ");										
				strbfr_sql.append(" from (select a.reg_pub_id,a.regpub,a.servicio_id,a.nombre, b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT");
				strbfr_sql.append(" from (select t2.nombre regpub, t2.reg_pub_id, t1.servicio_id, t1.nombre");						
				strbfr_sql.append(" from   tm_servicio t1, regis_publico t2");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140)) a,");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140)) a,");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196)) a,");
				//Fin:mgarate

				strbfr_sql.append(" (select t1.cod_reg_pub, t1.servicio_id, ");
				strbfr_sql.append(" sum(t1.rpn) rpn , sum(t1.rpj) rpj, sum(t1.rpi) rpi, sum(t1.rbm) rbm, ");
				strbfr_sql.append(" sum(t1.rpn + t1.rpj + t1.rpi + t1.rbm) total");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(partition by t1.cod_reg_pub),2), '990.99') as porSUBT ");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(),2), '990.99') as porTOT ");
				strbfr_sql.append(" from   uso_servicio t1");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140) ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140) ");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196) ");
				//Fin:mgarate	
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70) and");				
				strbfr_sql.append(" and t1.tipo_usr=? ");				
				strbfr_sql.append(" and to_date(t1.aaaammdd,'yyyy/mm/dd') between to_date(?,'yyyy/mm/dd') and ");
				strbfr_sql.append(" to_date(?,'yyyy/mm/dd')");
				strbfr_sql.append(" group by t1.cod_reg_pub, t1.servicio_id)b");
				strbfr_sql.append(" where b.cod_reg_pub(+) = a.reg_pub_id and");
				strbfr_sql.append(" b.servicio_id(+) = a.servicio_id");
				strbfr_sql.append(" group by a.reg_pub_id,a.regpub,a.servicio_id, a.nombre,b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT) t");
				//strbfr_sql.append(" group by cube (t.regpub, decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales'))");
				strbfr_sql.append(" group by cube (t.regpub,");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales'))");
				//Fin:mgarate

				if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
				prpstmt = conn.prepareStatement(strbfr_sql.toString());				
				prpstmt.setString(1, str_TipoUsuario);
				prpstmt.setString(2, date_Inicio_Ora);
				prpstmt.setString(3, date_Fin_Ora);
				
				//prpstmt.setString(1, date_Inicio_Ora);
				//prpstmt.setString(2, date_Fin_Ora);
				
				if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
				rs=null;
				rs = prpstmt.executeQuery();

			//Iniciar Transaccion
			//prepare
			//str_nombre_registro_publico="";
				while(rs.next())
				{
					if (isTrace(this)) trace("str_nombre_registro_publico"+ rs.getString(1).toString(), request);
					if((str_nombre_registro_publico.equals(rs.getString(1).toString()) && int_contador_linea==0 )|| (rs.getString(1).toString().equals("TOTAL") && !str_nombre_registro_publico.equals("TOTAL")) ){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreRegistroPublico(rs.getString(1));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("##e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						int_contador_linea=int_contador_linea+1;
	
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);
						
						str_nombre_registro_publico=rs.getString(1).toString();
						if (isTrace(this)) trace("str_nombre_registro_publico"+ str_nombre_registro_publico, request);
					}					
					if(str_nombre_registro_publico.equals("TOTAL")){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);					
					}else{
						if(str_nombre_registro_publico.equals(rs.getString(1).toString())){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2)==null?"Totales":rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalRBM(rs.getString(6));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(8));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(9));					
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);											
						}
					}
				
				}	
			}	
			
			
			
			if(!str_RegistroPublico.equals("TODAS") && !str_OficinaRegistral.equals("TODAS")){
			
/*				
				strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
				strbfr_sql.append("FROM REGIS_PUBLICO RP ");
				strbfr_sql.append("WHERE RP.REG_PUB_ID=? ");
				strbfr_sql.append("ORDER BY RP.NOMBRE" );
*/				
				strbfr_sql.append("SELECT OFR.REG_PUB_ID,OFR.OFIC_REG_ID, OFR.NOMBRE  ");
				strbfr_sql.append("FROM OFIC_REGISTRAL OFR ");
				strbfr_sql.append("WHERE OFR.REG_PUB_ID= ?");
				strbfr_sql.append("AND OFR.OFIC_REG_ID= ?");
				strbfr_sql.append("ORDER BY OFR.NOMBRE" );
/*				
				strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
				strbfr_sql.append("FROM REGIS_PUBLICO RP ");
				strbfr_sql.append("WHERE RP.REG_PUB_ID=? ");
				strbfr_sql.append("ORDER BY RP.NOMBRE" );
*/
				//Iniciar Transaccion
				prpstmt = conn.prepareStatement(strbfr_sql.toString());
				prpstmt.setString(1, str_RegistroPublico);						
				prpstmt.setString(2, str_OficinaRegistral);				
				//prepare
				rs = prpstmt.executeQuery();	
				java.util.List temp_sel_list_OficinaRegistral = new java.util.ArrayList();
				while(rs.next())
				{
					str_nombre_oficina_registral=rs.getString(3).toString();	
					//temp_sel_list_OficinaRegistral.add(str_nombre_oficina_registral);	
					//int a= temp_sel_list_OficinaRegistral.indexOf(str_nombre_oficina_registral);
				}	
				
				strbfr_sql.setLength(0);
				strbfr_sql.append(" select decode(t.oficina,NULL,'TOTAL',t.oficina) oficina,  " );
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//strbfr_sql.append(" decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 111,'06. Copia Literal Certificada', 112,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales')tipo,  ");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales') as tipo,");
				//Fin:mgarate

				strbfr_sql.append(" sum(nvl(t.rpn,0)) rpn, sum(nvl(t.rpj,0)) rpj, sum(nvl(t.rpi,0)) rpi, sum(nvl(t.rbm,0)) rbm, sum(nvl(t.total,0)) sub_total ");	
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porSUBT,0)), '990.99') porSubTotal ");						
				strbfr_sql.append(" ,TO_CHAR(sum(nvl(t.porTOT,0)), '990.99') porTotal ");										
                strbfr_sql.append(" from (select a.reg_pub_id,a.oficina,a.servicio_id,a.nombre, b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT ");
				strbfr_sql.append(" from (select t2.nombre oficina, t2.reg_pub_id, t2.ofic_reg_id, t1.servicio_id, t1.nombre ");			
				strbfr_sql.append(" from   tm_servicio t1, ofic_registral t2 ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140)) a,");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140)) a,");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196)) a,");
				//Fin:mgarate

				strbfr_sql.append("	(select  t1.cod_reg_pub, t1.cod_ofic_reg, t1.servicio_id, ");
				strbfr_sql.append(" sum(t1.rpn) rpn , sum(t1.rpj) rpj, sum(t1.rpi) rpi, sum(t1.rbm) rbm, ");
				strbfr_sql.append(" sum(t1.rpn + t1.rpj + t1.rpi + t1.rbm) total");
				
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(partition by t1.cod_reg_pub, t1.cod_ofic_reg),2), '990.99') as porSUBT ");
				strbfr_sql.append(" ,TO_CHAR(round(100*ratio_to_report(sum(nvl(t1.rpn,0) + nvl(t1.rpj,0) + nvl(t1.rpi,0) + nvl(t1.rbm,0))) over(),2), '990.99') as porTOT ");
				strbfr_sql.append(" from   uso_servicio t1");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,120,121,122,123,124,125,140) ");
				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70,80,90,110,111,112,120,121,122,123,124,125,140) ");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" where  t1.servicio_id in (10, 33, 35, 40, 41, 70, 80, 90, 110, 111, 112,120, 121, 122, 123, 124, 125, 126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,150,180,181,182,183,184,185,186,187,196) ");
				//Fin:mgarate

				//strbfr_sql.append(" where  t1.servicio_id in (10,40,70) and");				
				strbfr_sql.append(" and t1.tipo_usr=? ");				
				strbfr_sql.append(" and to_date(t1.aaaammdd,'yyyy/mm/dd') between to_date(?,'yyyy/mm/dd') and ");
				strbfr_sql.append(" to_date(?,'yyyy/mm/dd')");
				strbfr_sql.append(" group by t1.cod_reg_pub, t1.cod_ofic_reg, t1.servicio_id)b");
				strbfr_sql.append(" where b.cod_reg_pub(+) = a.reg_pub_id ");
				strbfr_sql.append(" and b.cod_ofic_reg(+) = a.ofic_reg_id ");
				strbfr_sql.append(" and b.servicio_id(+) = a.servicio_id");
				strbfr_sql.append(" group by a.reg_pub_id,a.oficina,a.oficina,a.servicio_id, a.nombre,b.rpn, b.rpj, b.rpi, b.rbm, b.total,porTOT,porSUBT) t ");
				//strbfr_sql.append(" group by cube (t.oficina, decode(t.servicio_id, 10,'01. Consulta Partidas',  40,'02. Consulta Titulos', 70,'03. Visualización de Partidas', 80,'04. Consulta Partidas',  90,'05. Consulta Titulos', 110,'06. Copia Literal Certificada', 120,'07. Certificado de Testamento', 121,'08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal', 123,'10. Certificado de Persona Juridica', 124,'11. Certificado de Propiedad Inmueble', 125,'12. Certificado de Propiedad Vehicular', 140,'13. Envio a Domicilio', null,'Totales'))");
				strbfr_sql.append(" group by cube (t.oficina,");
				//inicio:mgarate:ticket 24665
				strbfr_sql.append(" decode(t.servicio_id, 10, '01. Consulta Partidas', 40, '02. Consulta Titulos',70, '03. Visualización de Partidas', 80, '04. Consulta Partidas', 90, '05. Consulta Titulos', 110, '06. Copia Literal Certificada', 111, '06. Copia Literal Certificada', 112, '06. Copia Literal Certificada', 120, '07. Certificado de Testamento', 121, '08. Certificado de Sucesion Intestada', 122,'09. Certificado de Registro de Personal',");
				strbfr_sql.append(" 123, '10. Certificado de Persona Juridica', 124, '11. Certificado de Propiedad Inmueble', 125, '12. Certificado de Propiedad Vehicular', 140, '13. Envio a Domicilio', 126, '14. Certificado Registro Mobiliario Actos Vigentes', 127, '15. Certificado Registro Mobiliario Historico', 128, '16. Certificado Registro Mobiliario Condicionado', 129, '17. Certificado Antecedentes Dominiales Vehicular',");
				strbfr_sql.append(" 130, '18. Certificado Antecedentes Dominiales Buques', 131, '19. Registro Mobiliario de Contratos',132, '20. Certificado de Gravamen', 133, '21. Certificado de Vigencia', 134,'22. Certificado Busqueda Directa Vehicular', 135, '23. Certificado Antecedentes Dominiales Aeronaves', 136, '24. Certificado Antecedentes Dominiales Aeronaves', 137, '25. Certificado Historico Gravamen Vehicular',");
				strbfr_sql.append(" 138, '26. Certificado Historico Gravamen Busques', 139, '27. Certificado Historico Gravamen Busques', 141, '28. Certificado Historico Gravamen Embarcacion Pesquera', 142, '29. Certificado Busqueda Directa Embarcacion Pesquera', 143, '30. Certificado Busqueda Directa Aeronaves', 144, '31. Certificado Busqueda Directa Buques', 145, '32. Certificado Busqueda Indirecta Vehicular',");
				strbfr_sql.append(" 146, '33. Certificado Busqueda Indirecta Embarcaciones Pesqueras',147, '34. Certificado Busqueda Indirecta Aeronaves', 148, '35. Certificado Busqueda Indirecta Buquess', 150, '36. Copia Literal Certificada RMC', 180, '37. Publicidad Masiva Relacional(0-19)', 181, '38. Publicidad Masiva Relacional(20-100)', 182, '39. Publicidad Masiva Relacional(101-500)',");
				strbfr_sql.append(" 183,'40. Publicidad Masiva Relacional(501-1000)', 184, '41. Publicidad Masiva Relacional(1001-10000)', 185, '42. Publicidad Masiva Relacional(10001 a  50000)', 186, '43. Publicidad Masiva Relacional(50001 a 100000)', 187, '44. Publicidad Masiva Relacional(100001 a mas)', 196, '45. Visualizacion Partida RMC',"); 
				strbfr_sql.append(" 33,'46. Busqueda Indice Partida RMC', 35,'47. Busqueda Nacional de Indice Partida SIGC', 41,'48. Busqueda Directa Partida RMC', null,'Totales'))");
				//Fin:mgarate
				
				if (isTrace(this)) trace("Prepare"+ strbfr_sql.toString(), request);
				prpstmt = conn.prepareStatement(strbfr_sql.toString());				
				prpstmt.setString(1, str_TipoUsuario);
				prpstmt.setString(2, date_Inicio_Ora);
				prpstmt.setString(3, date_Fin_Ora);
				
				//prpstmt.setString(1, date_Inicio_Ora);
				//prpstmt.setString(2, date_Fin_Ora);
				
				if (isTrace(this)) trace("Prepare"+ prpstmt.toString(), request);
				rs=null;
				rs = prpstmt.executeQuery();

			//Iniciar Transaccion
			//prepare
			//str_nombre_registro_publico="";
				while(rs.next())
				{
					if (isTrace(this)) trace("str_nombre_oficina_registral"+ rs.getString(1).toString(), request);
					if((str_nombre_oficina_registral.equals(rs.getString(1).toString()) && int_contador_linea==0 )|| (rs.getString(1).toString().equals("TOTAL") && !str_nombre_oficina_registral.equals("TOTAL")) ){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreRegistroPublico(rs.getString(1));
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("##e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						int_contador_linea=int_contador_linea+1;
	
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);
						
						str_nombre_oficina_registral=rs.getString(1).toString();
						if (isTrace(this)) trace("str_nombre_oficina_registral"+ str_nombre_oficina_registral, request);
					}					
					if(str_nombre_oficina_registral.equals("TOTAL")){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(6));
						//temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(8));					
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);					
					}else{
						if(str_nombre_oficina_registral.equals(rs.getString(1).toString())){
						temp_UsodeServiciosBean = new UsodeServiciosBean();				
						temp_UsodeServiciosBean.setNombreServicio(rs.getString(2));
						temp_UsodeServiciosBean.setTotalRPN(rs.getString(3));
						temp_UsodeServiciosBean.setTotalRPJ(rs.getString(4));
						temp_UsodeServiciosBean.setTotalRPI(rs.getString(5));
						temp_UsodeServiciosBean.setTotalGeneralRegistroPublico(rs.getString(6));
						temp_UsodeServiciosBean.setPorcentajeRegistroPublico(rs.getString(7));
						temp_UsodeServiciosBean.setPorcentajeNacionalRegistroPublico(rs.getString(8));					
						if(int_contador_linea % 2 == 0)
						{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#e2e2e2");	
						}else{
						   temp_UsodeServiciosBean.setCelda_bgcolor("#ffffff");
						}   
						temp_UsodeServiciosBean.setCelda_titulo_bgcolor("#ffffff");
						int_contador_linea=int_contador_linea+1;
						
						temp_list_usodeservicios.add(temp_UsodeServiciosBean);											
						}
					}
				
				}	
			}			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++						
			
			//StringBuffer strbfr_sql = new StringBuffer();
			strbfr_sql.setLength(0);			
			rs = null;

			RegistroPublicoBean temp_RegistroPublicoBean =null;
			OficinaRegistralBean temp_OficinaRegistralBean =null;
			TipoUsuarioBean temp_TipoUsuarioBean=null;
			java.util.List temp_list_RegistroPublico = new java.util.ArrayList();
			java.util.List temp_list_OficinaRegistral = new java.util.ArrayList();
			java.util.List temp_list_TipoUsuario = new java.util.ArrayList();						
						
			strbfr_sql.append("SELECT RP.REG_PUB_ID ,RP.NOMBRE ");
			strbfr_sql.append("FROM REGIS_PUBLICO RP ");
			strbfr_sql.append("WHERE RP.REG_PUB_ID<>'00' ");
			strbfr_sql.append("ORDER BY RP.NOMBRE" );
	
			//Iniciar Transaccion
			prpstmt = conn.prepareStatement(strbfr_sql.toString());
						
			//prepare
			rs = prpstmt.executeQuery();
			int j=0;	
			if (j==0)
			{
				temp_RegistroPublicoBean = new RegistroPublicoBean();								
				temp_RegistroPublicoBean.setCodigoRegistroPublico("TODAS");
				temp_RegistroPublicoBean.setNombreRegistroPublico("TODAS");		
				if(str_RegistroPublico.equals("TODAS")){
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("selected");									
				}else{
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("");				
				}							

				temp_list_RegistroPublico.add(temp_RegistroPublicoBean);				
			}			
			while(rs.next())
			{
				temp_RegistroPublicoBean = new RegistroPublicoBean();			
				temp_RegistroPublicoBean.setCodigoRegistroPublico(rs.getString(1));
				temp_RegistroPublicoBean.setNombreRegistroPublico(rs.getString(2));					
				if(str_RegistroPublico.equals(rs.getString(1))){
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("selected");									
				}else{
					temp_RegistroPublicoBean.setCodigoRegistroPublicoSelected("");				
				}				
				j=j+1;
				temp_list_RegistroPublico.add(temp_RegistroPublicoBean);
			}	
			strbfr_sql.setLength(0);
			strbfr_sql.append("SELECT OFR.REG_PUB_ID,OFR.OFIC_REG_ID, OFR.NOMBRE  ");
			strbfr_sql.append("FROM OFIC_REGISTRAL OFR ");
			//strbfr_sql.append("WHERE RP.REG_PUB_ID<>'00' ");
			strbfr_sql.append("ORDER BY OFR.NOMBRE" );
	
			//Iniciar Transaccion
			prpstmt = conn.prepareStatement(strbfr_sql.toString());
						
			//prepare
			rs=null;
			rs = prpstmt.executeQuery();
			int k=0;	
			if (k==0)
			{
				temp_OficinaRegistralBean = new OficinaRegistralBean();								
				temp_OficinaRegistralBean.setCodigoRegistroPublico("TODAS");				
				temp_OficinaRegistralBean.setCodigoOficinaRegistral("TODAS");
				temp_OficinaRegistralBean.setNombreOficinaRegistral("TODAS");					
				if(str_OficinaRegistral.equals("TODAS")){
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("selected");
				}else{
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("");					
				}
				temp_list_OficinaRegistral.add(temp_OficinaRegistralBean);
			}			
			while(rs.next())
			{
				temp_OficinaRegistralBean = new OficinaRegistralBean();								
				temp_OficinaRegistralBean.setCodigoRegistroPublico(rs.getString(1));
				temp_OficinaRegistralBean.setCodigoOficinaRegistral(rs.getString(2));
				temp_OficinaRegistralBean.setNombreOficinaRegistral(rs.getString(3));					
				if(str_RegistroPublico.equals(rs.getString(1)) && str_OficinaRegistral.equals(rs.getString(2))){
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("selected");
				}else{
					temp_OficinaRegistralBean.setCodigoOficinaRegistralSelected("");
				}

				k=k+1;
				temp_list_OficinaRegistral.add(temp_OficinaRegistralBean);
			}	

			if(str_TipoUsuario.equals("1")){
				temp_TipoUsuarioBean = new TipoUsuarioBean();
				temp_TipoUsuarioBean.setCodigoTipoUsuario("1");
				temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("selected");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Individual");	
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);		
				temp_TipoUsuarioBean = new TipoUsuarioBean();				
				temp_TipoUsuarioBean.setCodigoTipoUsuario("0");
				temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Organizacion");
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);			
			}else{
				temp_TipoUsuarioBean = new TipoUsuarioBean();
				temp_TipoUsuarioBean.setCodigoTipoUsuario("1");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Individual");	
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);		
				temp_TipoUsuarioBean = new TipoUsuarioBean();				
				temp_TipoUsuarioBean.setCodigoTipoUsuario("0");
				temp_TipoUsuarioBean.setCodigoTipoUsuarioSelected("selected");
				temp_TipoUsuarioBean.setNombreTipoUsuario("Organizacion");
				temp_list_TipoUsuario.add(temp_TipoUsuarioBean);							
			}

			temp_FormUsodeServiciosBean.setList_RegistroPublico(temp_list_RegistroPublico);
			temp_FormUsodeServiciosBean.setList_OficinaRegistral(temp_list_OficinaRegistral);
			temp_FormUsodeServiciosBean.setList_TipoUsuario(temp_list_TipoUsuario);
			temp_FormUsodeServiciosBean.setStr_RegistroPublico(str_RegistroPublico);
			temp_FormUsodeServiciosBean.setStr_OficinaRegistral(str_OficinaRegistral);			
			temp_FormUsodeServiciosBean.setStr_TipoUsuario(str_TipoUsuario);
			temp_FormUsodeServiciosBean.setList_UsodeServicios(temp_list_usodeservicios);
			temp_FormUsodeServiciosBean.setStr_Date_Inicio(date_Inicio);
			temp_FormUsodeServiciosBean.setStr_Date_Fin(date_Fin);

			//req.setAttribute("formusoserviciosbean", temp_FormUsodeServiciosBean);
			//req.setAttribute("formusodeserviciosbean", temp_FormUsodeServiciosBean );
			//response.setStyle(findForward("usodeservicios", request));
			
	 		String fname = "ReporteUsodeServicios.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			PrintWriter out_cliente = null;
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			StringBuffer stb = null;
			stb = new StringBuffer();
			stb.append("").append(",");
			stb.append("RPN").append(",");
			stb.append("RPJ").append(",");
			stb.append("RPI").append(",");
			//inicio:mgarate:ticket 24665
			stb.append("RBM").append(",");
			//fin:mgarate
			stb.append("Total").append(",");
			stb.append("Porcentajes(%)").append(",");
			stb.append("(%) Porcentajes Nivel Nacional");			
			
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
			String temp=null;
			String temp_Servicio=null;
			for(java.util.Iterator iterator_usodeservicios = temp_list_usodeservicios.iterator(); iterator_usodeservicios.hasNext();){
				UsodeServiciosBean iterator_usodeserviciosbean = (UsodeServiciosBean) iterator_usodeservicios.next();
				temp=iterator_usodeserviciosbean.getNombreRegistroPublico();
				temp_Servicio=iterator_usodeserviciosbean.getNombreServicio();
				stb = new StringBuffer();
				if(temp==null && temp_Servicio!=null){
					if(iterator_usodeserviciosbean.getNombreRegistroPublico()==null){
						stb.append(iterator_usodeserviciosbean.getNombreServicio()).append(",");
					}else{
						stb.append(iterator_usodeserviciosbean.getNombreServicio()).append(",");
					}
					if(iterator_usodeserviciosbean.getTotalRPN()==null){
						stb.append("").append(",");
					}else{				
						stb.append(iterator_usodeserviciosbean.getTotalRPN()).append(",");
					}
					if(iterator_usodeserviciosbean.getTotalRPI()==null){
						stb.append("").append(",");
					}else{				
						stb.append(iterator_usodeserviciosbean.getTotalRPJ()).append(",");
					}
					if(iterator_usodeserviciosbean.getTotalRPJ()==null){
						stb.append("").append(",");
					}else{				
						stb.append(iterator_usodeserviciosbean.getTotalRPI()).append(",");
					}
					//inicio:mgarate:ticket 24665 
					if(iterator_usodeserviciosbean.getTotalRBM()==null)
					{
						stb.append("").append(",");
					}else
					{
						stb.append(iterator_usodeserviciosbean.getTotalRBM()).append(",");
					}
					//fin:mgarate
					//cjvc77
					if(iterator_usodeserviciosbean.getTotalGeneralRegistroPublico()==null){
						stb.append("").append(",");
					}else{				
						stb.append(iterator_usodeserviciosbean.getTotalGeneralRegistroPublico()).append(",");
					}
					//
					if(iterator_usodeserviciosbean.getPorcentajeRegistroPublico()==null){
						stb.append("").append(",");
					}else{				
						stb.append(iterator_usodeserviciosbean.getPorcentajeRegistroPublico()).append(",");
					}
					if(iterator_usodeserviciosbean.getPorcentajeNacionalRegistroPublico()==null){
						stb.append("").append(",");
					}else{				
						stb.append(iterator_usodeserviciosbean.getPorcentajeNacionalRegistroPublico());
					}
				}else{
					stb.append(iterator_usodeserviciosbean.getNombreRegistroPublico()).append(",");
				}
				
				
				out_cliente.println(stb.toString());
			}

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
	
}