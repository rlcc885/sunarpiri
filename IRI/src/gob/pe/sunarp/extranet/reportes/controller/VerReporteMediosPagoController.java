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
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.reportes.beans.*;
import gob.pe.sunarp.extranet.util.*;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VerReporteMediosPagoController extends ControllerExtension implements Constantes{
	
	public VerReporteMediosPagoController() {
		super();
		addState(new State("verFormulario", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("verReporte", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("exportar", "Exporta el Reporte a Excel"));
		setInitialState("verFormulario");
	}
	public String getTitle() {
		return new String("VerReporteMediosPagoController");
	}

	protected ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

				
		try{
			init(request);
			validarSesion(request);
			
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			EvolucionBean evolucionBean = new EvolucionBean();
			
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
			
			evolucionBean.setTipoPersona("2");
			evolucionBean.setIndicador("1");
			evolucionBean.setHayRegistros("1");
			req.setAttribute("evolucionBean", evolucionBean);
			response.setStyle("verFormulario");
			
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		}  catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} 
		return response;
	}


	protected ControllerResponse runVerReporteState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		
			// Recuperar Datos de Session //
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
			
			// Instancia de Bean de datos del Formulario //
			EvolucionBean evolucionBean = new EvolucionBean();
			
			//Recepcion de Parametros de formulario JSP
			
			String date_Dia_Inicio = request.getParameter("diainicio");
			String date_Mes_Inicio = request.getParameter("mesinicio");
			String date_Ano_Inicio = request.getParameter("anoinicio");
			String date_Dia_Fin = request.getParameter("diafin");
			String date_Mes_Fin = request.getParameter("mesfin");
			String date_Ano_Fin  = request.getParameter("anofin");
			
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());
			
			req.setAttribute("selectedIDay",date_Dia_Inicio);
			req.setAttribute("selectedIMonth",date_Mes_Inicio);
			req.setAttribute("selectedIYear",date_Ano_Inicio);
			req.setAttribute("selectedFDay",date_Dia_Fin);
			req.setAttribute("selectedFMonth",date_Mes_Fin);
			req.setAttribute("selectedFYear",date_Ano_Fin);	
			//Validar Rango de Fechas						
			String date_Inicio = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));

/*			
			if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
				String a="1";

			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
				String b="1";					
*/			
			//FG_PORC (0: NO, 1 : SI)
			String param1 = request.getParameter("tipoPersona");
			String param4 = request.getParameter("fgPorc");
			
			//Construccion del query 
			StringBuffer query = new StringBuffer();
			
			
			//query para los totales
			
			query.append(" select count(t.montoefectivo) numEfectivo, "); 
       		query.append(" count(t.montocredito) numCredito, ");
       		query.append(" count(t.montodebito) numDebito, ");
       		query.append(" count(*) numTotal, ");
       		query.append(" sum(nvl(t.montoEfectivo,0)) efectivo, "); 
       		query.append(" sum(nvl(t.montocredito,0)) credito, "); 
       		query.append(" sum(nvl(t.montoDebito,0)) debito, ");
       		query.append(" sum(nvl(t.montoCredito,0) + nvl(t.montoDebito,0) + nvl(t.montoEfectivo,0)) montoTotal ");
			query.append(" from (SELECT decode(b.medio_id,1,b.monto) montoCredito, ");
            query.append(" 		decode(b.medio_id,2,b.monto) montoDebito, ");
            query.append(" 		decode(b.tipo_abono, 'V', b.monto) montoEfectivo ");
      		query.append(" 		FROM REGIS_PUBLICO  a, ABONO  b, persona c ");
      		query.append(" 		WHERE b.PERSONA_ID=c.PERSONA_ID and "); 
            query.append(" 		c.REG_PUB_ID=a.REG_PUB_ID and "); 
            query.append(" 		b.TIPO_ABONO in ('L','V') and ");
            if(!param1.equals("2")){
            	query.append(" 	 c.tpo_pers = ? and ");
            }
            query.append(" 		b.ts_crea  BETWEEN  TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')  AND TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')) t ");
            
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println(query.toString());
			pstmt = conn.prepareStatement(query.toString());
			
			if(!param1.equals("2")){
				if(param1.equals("0")){ //J
					pstmt.setString(1 , "J");
				}else if(param1.equals("1")){ //N
					pstmt.setString(1 , "N");
				}
				pstmt.setString(2, date_Inicio + "00:00:00");
				pstmt.setString(3, date_Fin +"23:59:59");
			}else{
			
				pstmt.setString(1, date_Inicio + "00:00:00");
				pstmt.setString(2, date_Fin +"23:59:59");	
			
			}	
				
			//Guardar los totales en un bean es un soloRegistro
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			TotalMediosPagoBean totalMediosBean = new TotalMediosPagoBean();
			long m1 =rs.getLong(1);
			long m2 =rs.getLong(2);
			long m3 = rs.getLong(3);
			////////long m4 = rs.getLong(4)
			long m4 = m1+m2+m3;
			totalMediosBean.setTotalEfectivo(m1);
			totalMediosBean.setTotalCredito(m2);
			totalMediosBean.setTotalDebito(m3);
			totalMediosBean.setTotalTotal1(m4);
			
			totalMediosBean.setTotalPorcentaje("100 %");
			
			totalMediosBean.setTotalEfectivoSoles(rs.getLong(5));
			totalMediosBean.setTotalCreditoSoles(rs.getLong(6));
			totalMediosBean.setTotalDebitoSoles(rs.getLong(7));
			totalMediosBean.setTotalTotal2(rs.getLong(8));
			
			totalMediosBean.setTotalPorcentajeSoles("100 %");
			
			
			req.setAttribute("totalMediosPago", totalMediosBean);
			
			query.setLength(0);
			//query para el detalle
			query.append(" select t.siglas, t.reg_pub_id, ");		 
       		query.append(" count(t.montocredito) numCredito, "); 
       		query.append(" count(t.montodebito) numDebito, ");
       		query.append(" count(t.montoefectivo) numEfectivo, ");
       		query.append(" count(*) numTotal, ");
       		query.append(" sum(nvl(t.montocredito,0)) credito, ");
       		query.append(" sum(nvl(t.montoDebito,0)) debito, "); 
       		query.append(" sum(nvl(t.montoEfectivo,0)) efectivo, ");
       		query.append(" sum(nvl(t.montoCredito,0) + nvl(t.montoDebito,0) + nvl(t.montoEfectivo,0)) montoTotal ");
			query.append(" from (SELECT A.SIGLAS, a.REG_PUB_ID, "); 
            query.append(" 		decode(b.medio_id,1,b.monto) montoCredito, ");
            query.append(" 		decode(b.medio_id,2,b.monto) montoDebito, ");
            query.append(" 		decode(b.tipo_abono, 'V', b.monto) montoEfectivo ");
      		query.append(" 		FROM REGIS_PUBLICO  a, ABONO  b, PERSONA  c ");
      		query.append(" 		WHERE b.PERSONA_ID=c.PERSONA_ID and "); 
            query.append(" 		c.REG_PUB_ID=a.REG_PUB_ID and "); 
            query.append(" 		b.TIPO_ABONO in ('L','V') and ");
            if(!param1.equals("2")){
            	query.append("    c.tpo_pers = ? and ");		//parametro 1
            }
            query.append("		b.ts_crea  BETWEEN  TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')  AND TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')) t "); //parametro 2 y 3
            
			query.append(" 	group by t.siglas, t.reg_pub_id ");  
			query.append(" 	ORDER BY to_number(substr(t.siglas,3, 2)) ");
			
			
			pstmt = null;
			rs = null;
			System.out.println(query.toString());
			pstmt = conn.prepareStatement(query.toString());
			
			if(!param1.equals("2")){
				if(param1.equals("0")){ //J
					pstmt.setString(1 , "J");
				}else if(param1.equals("1")){ //N
					pstmt.setString(1 , "N");
				}
				pstmt.setString(2, date_Inicio + " 00:00:00");
				pstmt.setString(3, date_Fin +" 23:59:59");
			}else{
			
				pstmt.setString(1, date_Inicio + " 00:00:00");
				pstmt.setString(2, date_Fin +" 23:59:59");	
			
			}
				
			rs = pstmt.executeQuery();	
			
			ArrayList arrayMediosPago = new ArrayList();
			
			Map zonas = new HashMap();
			zonas.put("05","ZR1");
			zonas.put("11","ZR2");
			zonas.put("12","ZR3");
			zonas.put("09","ZR4");
			zonas.put("08","ZR5");
			zonas.put("13","ZR6");
			zonas.put("04","ZR7");
			zonas.put("02","ZR8");
			zonas.put("01","ZR9");
			zonas.put("06","ZR10");
			zonas.put("10","ZR11");
			zonas.put("03","ZR12");
			zonas.put("07","ZR13");	
			
			ArrayList ordenOficRegis = new ArrayList();
			ordenOficRegis.add("05");
			ordenOficRegis.add("11");
			ordenOficRegis.add("12");
			ordenOficRegis.add("09");
			ordenOficRegis.add("08");
			ordenOficRegis.add("13");
			ordenOficRegis.add("04");
			ordenOficRegis.add("02");
			ordenOficRegis.add("01");
			ordenOficRegis.add("06");
			ordenOficRegis.add("10");
			ordenOficRegis.add("03");
			ordenOficRegis.add("07");
			
			for(int i = 0 ; i < ordenOficRegis.size(); i++){
			
				MediosPagoBean bean = new MediosPagoBean();
				bean.setZona((String)zonas.get(ordenOficRegis.get(i)));
				bean.setEfectivo("0");
				bean.setCredito("0");
				bean.setDebito("0");
				bean.setTotal("0");
				bean.setPorcentaje("0 %");
				bean.setEfectivoSoles("0");
				bean.setCreditoSoles("0");
				bean.setDebitoSoles("0");
				bean.setTotalSoles("0");
				bean.setPorcentajeSoles("0 %");
				arrayMediosPago.add(bean);
			
			}
			
			//Llenar el bean con los resultados a mostrar
			while(rs.next()){
				
				for(int i = 0 ; i < ordenOficRegis.size() ; i++){
					
					MediosPagoBean medioPagoBean = (MediosPagoBean)arrayMediosPago.get(i);
					if(medioPagoBean.getZona().equals(rs.getString(1).trim())){
						
						
						int r5 = rs.getInt(5);
						int r3 = rs.getInt(3);
						int r4 = rs.getInt(4);
						////////int r6 = rs.getInt(6);
						int r6 = r3+r4+r5;
						medioPagoBean.setEfectivo(String.valueOf(r5));
						medioPagoBean.setCredito(String.valueOf(r3));
						medioPagoBean.setDebito(String.valueOf(r4));
						
						medioPagoBean.setTotal(String.valueOf(r6)); // total1 para porcentaje 1
						
						double tempPorc = 0;
						if (r6!=0)
							tempPorc = 100 * (double)(r6) / (double)totalMediosBean.getTotalTotal1(); 
						
						if (isTrace(this)) System.out.println("temp_Porc_a:" + tempPorc);
						java.math.BigDecimal redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						
						medioPagoBean.setPorcentaje(String.valueOf(redondeado) + "%");
						
						int r9 = rs.getInt(9);
						int r7 = rs.getInt(7);
						int r8 = rs.getInt(8);
						///////int r10 = rs.getInt(10)
						int r10 = r7+r8+r9;
						medioPagoBean.setEfectivoSoles(String.valueOf(r9));
						medioPagoBean.setCreditoSoles(String.valueOf(r7));
						medioPagoBean.setDebitoSoles(String.valueOf(r8));
						
						medioPagoBean.setTotalSoles(String.valueOf(r10));// total2 para porcentaje 2						
						
						tempPorc=0;
						if (r10!=0)
							tempPorc = 100 * ((double) r10) / (double)totalMediosBean.getTotalTotal2(); 
						
						if (isTrace(this)) System.out.println("temp_Porc_b :" + tempPorc);
						redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						medioPagoBean.setPorcentajeSoles(String.valueOf(redondeado) + "%");
						
					}	
				}
			}	
			
			
			req.setAttribute("mediosPago", arrayMediosPago);
			
			evolucionBean.setStr_Date_Inicio(date_Inicio);
			evolucionBean.setStr_Date_Fin(date_Fin);
			evolucionBean.setTipoPersona(param1);
			evolucionBean.setIndicador("1");
			evolucionBean.setHayRegistros("1");
			
			if(param1.equals("0")){
				evolucionBean.setTipoPersonaName("PJ");
			}else if(param1.equals("1")){ 
				evolucionBean.setTipoPersonaName("PN");
			}else if(param1.equals("2")){ 	
				evolucionBean.setTipoPersonaName("PN y PJ");
			}
			req.setAttribute("evolucionBean", evolucionBean);
			req.setAttribute("tipoPersona", param4);	
			response.setStyle("verFormulario");
			pstmt.close();
			rs.close();
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
	protected ControllerResponse runExportarState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			// Recuperar Datos de Session //
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			
			// Instancia de Bean de datos del Formulario //
			EvolucionBean evolucionBean = new EvolucionBean();
			
			//Recepcion de Parametros de formulario JSP
			
			String date_Dia_Inicio = request.getParameter("diainicio");
			String date_Mes_Inicio = request.getParameter("mesinicio");
			String date_Ano_Inicio = request.getParameter("anoinicio");
			String date_Dia_Fin = request.getParameter("diafin");
			String date_Mes_Fin = request.getParameter("mesfin");
			String date_Ano_Fin  = request.getParameter("anofin");
			
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());
			
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
			
			//FG_PORC (0: NO, 1 : SI)
			String param1 = request.getParameter("tipoPersona");
			String param4 = request.getParameter("fgPorc");
			
			//Construccion del query 
			StringBuffer query = new StringBuffer();
			
			
			//query para los totales
			
			query.append(" select count(t.montoefectivo) numEfectivo, "); 
       		query.append(" count(t.montocredito) numCredito, ");
       		query.append(" count(t.montodebito) numDebito, ");
       		query.append(" count(*) numTotal, ");
       		query.append(" sum(nvl(t.montoEfectivo,0)) efectivo, "); 
       		query.append(" sum(nvl(t.montocredito,0)) credito, "); 
       		query.append(" sum(nvl(t.montoDebito,0)) debito, ");
       		query.append(" sum(nvl(t.montoCredito,0) + nvl(t.montoDebito,0) + nvl(t.montoEfectivo,0)) montoTotal ");
			query.append(" from (SELECT decode(b.medio_id,1,b.monto) montoCredito, ");
            query.append(" 		decode(b.medio_id,2,b.monto) montoDebito, ");
            query.append(" 		decode(b.tipo_abono, 'V', b.monto) montoEfectivo ");
      		query.append(" 		FROM REGIS_PUBLICO  a, ABONO  b, persona c ");
      		query.append(" 		WHERE b.PERSONA_ID=c.PERSONA_ID and "); 
            query.append(" 		c.REG_PUB_ID=a.REG_PUB_ID and "); 
            query.append(" 		b.TIPO_ABONO in ('L','V') and ");
            if(!param1.equals("2")){
            	query.append(" 	 c.tpo_pers = ? and ");
            }
            query.append(" 		b.ts_crea  BETWEEN  TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')  AND TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')) t ");
            
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			pstmt = conn.prepareStatement(query.toString());
			
			if(!param1.equals("2")){
				if(param1.equals("0")){ //J
					pstmt.setString(1 , "J");
				}else if(param1.equals("1")){ //N
					pstmt.setString(1 , "N");
				}
				pstmt.setString(2, date_Inicio + "00:00:00");
				pstmt.setString(3, date_Fin +"23:59:59");
			}else{
			
				pstmt.setString(1, date_Inicio + "00:00:00");
				pstmt.setString(2, date_Fin +"23:59:59");	
			
			}	
				
			//Guardar los totales en un bean es un soloRegistro
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			TotalMediosPagoBean totalMediosBean = new TotalMediosPagoBean();
			totalMediosBean.setTotalEfectivo(rs.getLong(1));
			totalMediosBean.setTotalCredito(rs.getLong(2));
			totalMediosBean.setTotalDebito(rs.getLong(3));
			totalMediosBean.setTotalTotal1(rs.getLong(4));
			totalMediosBean.setTotalPorcentaje("100 %");
			totalMediosBean.setTotalEfectivoSoles(rs.getLong(5));
			totalMediosBean.setTotalCreditoSoles(rs.getLong(6));
			totalMediosBean.setTotalDebitoSoles(rs.getLong(7));
			totalMediosBean.setTotalTotal2(rs.getLong(8));
			totalMediosBean.setTotalPorcentajeSoles("100 %");
			
			
			
			
			query.setLength(0);
			//query para el detalle
			query.append(" select t.siglas, t.reg_pub_id, ");		 
       		query.append(" count(t.montocredito) numCredito, "); 
       		query.append(" count(t.montodebito) numDebito, ");
       		query.append(" count(t.montoefectivo) numEfectivo, ");
       		query.append(" count(*) numTotal, ");
       		query.append(" sum(nvl(t.montocredito,0)) credito, ");
       		query.append(" sum(nvl(t.montoDebito,0)) debito, "); 
       		query.append(" sum(nvl(t.montoEfectivo,0)) efectivo, ");
       		query.append(" sum(nvl(t.montoCredito,0) + nvl(t.montoDebito,0) + nvl(t.montoEfectivo,0)) montoTotal ");
			query.append(" from (SELECT A.SIGLAS, a.REG_PUB_ID, "); 
            query.append(" 		decode(b.medio_id,1,b.monto) montoCredito, ");
            query.append(" 		decode(b.medio_id,2,b.monto) montoDebito, ");
            query.append(" 		decode(b.tipo_abono, 'V', b.monto) montoEfectivo ");
      		query.append(" 		FROM REGIS_PUBLICO  a, ABONO  b, PERSONA  c ");
      		query.append(" 		WHERE b.PERSONA_ID=c.PERSONA_ID and "); 
            query.append(" 		c.REG_PUB_ID=a.REG_PUB_ID and "); 
            query.append(" 		b.TIPO_ABONO in ('L','V') and ");
            if(!param1.equals("2")){
            	query.append("    c.tpo_pers = ? and ");		//parametro 1
            }
            query.append("		b.ts_crea  BETWEEN  TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')  AND TO_DATE(?,'DD/MM/YYYY hh24:mi:ss')) t "); //parametro 2 y 3
            
			query.append(" 	group by t.siglas, t.reg_pub_id ");  
			query.append(" 	ORDER BY to_number(substr(t.siglas,3, 2)) ");
			
			
			pstmt = null;
			rs = null;
			
			pstmt = conn.prepareStatement(query.toString());
			
			if(!param1.equals("2")){
				if(param1.equals("0")){ //J
					pstmt.setString(1 , "J");
				}else if(param1.equals("1")){ //N
					pstmt.setString(1 , "N");
				}
				pstmt.setString(2, date_Inicio + "00:00:00");
				pstmt.setString(3, date_Fin +"23:59:59");
			}else{
			
				pstmt.setString(1, date_Inicio + "00:00:00");
				pstmt.setString(2, date_Fin +"23:59:59");	
			
			}
				
			rs = pstmt.executeQuery();	
			
			ArrayList arrayMediosPago = new ArrayList();
			
			Map zonas = new HashMap();
			zonas.put("05","ZR1");
			zonas.put("11","ZR2");
			zonas.put("12","ZR3");
			zonas.put("09","ZR4");
			zonas.put("08","ZR5");
			zonas.put("13","ZR6");
			zonas.put("04","ZR7");
			zonas.put("02","ZR8");
			zonas.put("01","ZR9");
			zonas.put("06","ZR10");
			zonas.put("10","ZR11");
			zonas.put("03","ZR12");
			zonas.put("07","ZR13");	
			
			ArrayList ordenOficRegis = new ArrayList();
			ordenOficRegis.add("05");
			ordenOficRegis.add("11");
			ordenOficRegis.add("12");
			ordenOficRegis.add("09");
			ordenOficRegis.add("08");
			ordenOficRegis.add("13");
			ordenOficRegis.add("04");
			ordenOficRegis.add("02");
			ordenOficRegis.add("01");
			ordenOficRegis.add("06");
			ordenOficRegis.add("10");
			ordenOficRegis.add("03");
			ordenOficRegis.add("07");
			
			for(int i = 0 ; i < ordenOficRegis.size(); i++){
			
				MediosPagoBean bean = new MediosPagoBean();
				bean.setZona((String)zonas.get(ordenOficRegis.get(i)));
				bean.setEfectivo("0");
				bean.setCredito("0");
				bean.setDebito("0");
				bean.setTotal("0");
				bean.setPorcentaje("0 %");
				bean.setEfectivoSoles("0");
				bean.setCreditoSoles("0");
				bean.setDebitoSoles("0");
				bean.setTotalSoles("0");
				bean.setPorcentajeSoles("0 %");
				arrayMediosPago.add(bean);
			
			}
			
			//Llenar el bean con los resultados a mostrar
			while(rs.next()){
				
				for(int i = 0 ; i < ordenOficRegis.size() ; i++){
					
					MediosPagoBean medioPagoBean = (MediosPagoBean)arrayMediosPago.get(i);
					if(medioPagoBean.getZona().equals(rs.getString(1).trim())){
						
						medioPagoBean.setEfectivo(String.valueOf(rs.getInt(5)));
						medioPagoBean.setCredito(String.valueOf(rs.getInt(3)));
						medioPagoBean.setDebito(String.valueOf(rs.getInt(4)));
						medioPagoBean.setTotal(String.valueOf(rs.getInt(6))); // total1 para porcentaje 1
						
						double tempPorc = 100 * (double)(rs.getInt(6)) / (double)totalMediosBean.getTotalTotal1(); 
						
						java.math.BigDecimal redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						
						medioPagoBean.setPorcentaje(String.valueOf(redondeado) + "%");
						medioPagoBean.setEfectivoSoles(String.valueOf(rs.getInt(9)));
						medioPagoBean.setCreditoSoles(String.valueOf(rs.getInt(7)));
						medioPagoBean.setDebitoSoles(String.valueOf(rs.getInt(8)));
						medioPagoBean.setTotalSoles(String.valueOf(rs.getInt(10)));// total2 para porcentaje 2						
						
						tempPorc = 100 * (double)rs.getInt(10) / (double)totalMediosBean.getTotalTotal2(); 
						redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						medioPagoBean.setPorcentajeSoles(String.valueOf(redondeado) + "%");
						
					}	
				}
			}	
			
			//Generacion del archivo CSV			
			String fname = "reporteMediosPago.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;
			
			StringBuffer stb = new StringBuffer();
			stb.append("").append(",");
			stb.append("Efectivo").append(",");
			stb.append("Credito ").append(",");
			stb.append("Debito").append(",");
			stb.append("Total").append(",");
			stb.append("Porcentajes").append(",");
			stb.append("Efectivo S/.").append(",");
			stb.append("Debito S/.").append(",");
			stb.append("Credito S/.").append(",");
			stb.append("Total.").append(",");
			stb.append("Porcentajes ");
			
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
			
			for(Iterator it = arrayMediosPago.iterator(); it.hasNext();){
				stb.setLength(0);
				MediosPagoBean bean = (MediosPagoBean) it.next();
				stb.append(bean.getZona()).append(",");		
				stb.append(bean.getEfectivo()).append(",");		
				stb.append(bean.getCredito()).append(",");
				stb.append(bean.getDebito()).append(",");		
				stb.append(bean.getTotal()).append(",");
				stb.append(bean.getPorcentaje()).append(",");
				stb.append(bean.getEfectivoSoles()).append(",");		
				stb.append(bean.getCreditoSoles()).append(",");		
				stb.append(bean.getDebitoSoles()).append(",");		
				stb.append(bean.getTotalSoles()).append(",");		
				stb.append(bean.getPorcentajeSoles()).append(",");				
				out_cliente.println(stb.toString());	
			}	
			//los totales
			stb.setLength(0);
			stb.append("TOTAL").append(",");
			stb.append(totalMediosBean.getTotalEfectivo()).append(",");
			stb.append(totalMediosBean.getTotalCredito()).append(",");
			stb.append(totalMediosBean.getTotalDebito()).append(",");
			stb.append(totalMediosBean.getTotalTotal1()).append(",");
			stb.append(totalMediosBean.getTotalPorcentaje()).append(",");
			stb.append(totalMediosBean.getTotalEfectivoSoles()).append(",");
			stb.append(totalMediosBean.getTotalCreditoSoles()).append(",");
			stb.append(totalMediosBean.getTotalDebitoSoles()).append(",");
			stb.append(totalMediosBean.getTotalTotal2()).append(",");
			stb.append(totalMediosBean.getTotalPorcentajeSoles());
			
			out_cliente.println(stb.toString());	
			
			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);
			
			pstmt.close();
			rs.close();
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
	}}
