package gob.pe.sunarp.extranet.reportes.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.State;
import gob.pe.sunarp.extranet.pool.*;
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

public class VerReporteEvolucionController extends ControllerExtension{
	
	public VerReporteEvolucionController() {
		super();
		addState(new State("verFormulario", "muestra formulario de búsqueda de transacciones"));
		addState(new State("verReporteEvolucion1", "muestra el resultado de la búsqueda del reporte 1"));
		addState(new State("verReporteEvolucion2", "muestra el resultado de la búsqueda del reporte 2"));
		addState(new State("verReporteEvolucion3", "muestra el resultado de la búsqueda del reporte 3"));
		addState(new State("exportarReporteEvolucion1", "exporta el reporte 1 en formato csv"));
		addState(new State("exportarReporteEvolucion2", "exporta el reporte 2 en formato csv"));
		addState(new State("exportarReporteEvolucion3", "exporta el reporte 3 en formato csv"));
		setInitialState("verFormulario");
	}

	public String getTitle() 
	{
		return new String("VerReporteEvolucionController");
	}
	
	public ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
			
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

			EvolucionBean evolucionBean = new EvolucionBean();
			
			evolucionBean.setTipoPersona("2");
			evolucionBean.setIndicador("1");
			evolucionBean.setHayRegistros("1");
			req.setAttribute("evolucionBean", evolucionBean);
			
			
			response.setStyle("verFormulario");
			
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} 
		return response;
	}
	
public ControllerResponse runVerReporteEvolucion1State(
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

			// Recuperar Datos de Session //
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
			// Instancia de Bean de datos del Formulario //
			EvolucionBean evolucionBean = new EvolucionBean();
			
			//Nombre del Reporte : Número de Consultas efectuadas fuera de Zona Registral vs.Total Consultas efectuadas por usuarios de una Zona Registral.
			
			//parametros recibidos
			
			//PARAM1 : TIPO DE PERSONA (0:ORGANIZACIONES, 1:INDIVIDUALES, 2: Ambos) 
			//PARAM2 : FECHA DE INICIO DEL REPORTE
			//PARAM3 : FECHA DE FIN DEL REPORTE
			//PARAM4 : FG_PORC (0: NO, 1 : SI)
			
			String param1 = request.getParameter("tipoPersona");
			
			String date_Dia_Inicio = request.getParameter("diainicio");
			String date_Mes_Inicio = request.getParameter("mesinicio");
			String date_Ano_Inicio = request.getParameter("anoinicio");
			String date_Dia_Fin = request.getParameter("diafin");
			String date_Mes_Fin = request.getParameter("mesfin");
			String date_Ano_Fin  = request.getParameter("anofin");

			//fechas
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
			String date_Inicio = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			/*if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
			{
				String a="1";
			}

			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
			{
				String b="1";					
			}	
			*/
			
			//FG_PORC (0: NO, 1 : SI)
			
			String param4 = request.getParameter("fgPorc");
			
			//Ejecucion del query 
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT REG_PUB_ORIG, COD_REG_PUB, SUM(RPN) RPN, SUM(RPJ) RPJ,SUM(RPI) RPI ");
			query.append(" FROM USO_SERVICIO ");
			query.append(" WHERE REG_PUB_ORIG != '00'" );
			query.append(" AND SERVICIO_ID IN (40,70)" );
			query.append(" AND AAAAMMDD BETWEEN ? AND ? "); 
			if(!param1.equals("2")){
				query.append(" AND TIPO_USR= ? ");
			}
			query.append(" AND FG_INTERNO='0' ");
			query.append(" GROUP BY REG_PUB_ORIG,COD_REG_PUB ");
			query.append(" ORDER BY REG_PUB_ORIG,COD_REG_PUB ");
			
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//pstmt
			pstmt = conn.prepareStatement(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      							ResultSet.CONCUR_UPDATABLE);
			//pstmt.setDate(1 , FechaUtil.transformaASQLDate(date_Inicio));
			//pstmt.setDate(2 , FechaUtil.transformaASQLDate(date_Fin));	
			
			pstmt.setString(1, date_Inicio);
			pstmt.setString(2, date_Fin);
			if(!param1.equals("2")){
				pstmt.setString(3 , param1);
			}	
			
			rs = pstmt.executeQuery();	
			
			int [][] matriz = new int[13][13]; 
			double [][] matrizP = new double[13][13]; 
			int [] totalConsulta = new int[13];
			double [] porcentajes = new double [13];
			int totalTemp = 0;
			
			boolean isNotEmpty = rs.next();
			
			if(isNotEmpty){
			
			int temp = Integer.parseInt(rs.getString(1).trim());
				
			rs.beforeFirst();
			
			while(rs.next()){
				
				//acumulamos de por inicio a fin de matriz[inicio][fin]
				matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1] = rs.getInt(3) + rs.getInt(4) + rs.getInt(5);
				if(temp == Integer.parseInt(rs.getString(1).trim())){	
					totalTemp += matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}else{
					totalConsulta[temp - 1] = totalTemp;
					totalTemp = matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}	 
				temp = Integer.parseInt(rs.getString(1).trim());
				
				
			}
			//para el ultimo quiebre
			totalConsulta[temp - 1] = totalTemp;
			//para el ultimo quiebre
			}//si hay data en el query
			
			//si se pide porcentajes se actualiza la matriz con porcentajes
			
			if(param4.equals("1")){
			
				for(int i = 0 ; i < 13 ; i++){
				
					for(int j = 0 ; j <  13 ; j++){
						if(totalConsulta[i] > 0){
							double div = 100*(double)matriz[i][j]/(double)totalConsulta[i];
							java.math.BigDecimal redondeado = new java.math.BigDecimal(div).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
							matrizP[i][j] = redondeado.doubleValue();
						}else{
							matrizP[i][j] = 0;
						}	
					}
				
				}
			
			}
			
			if(param4.equals("0")){
				for(int i = 0; i < 13 ; i++){
				
					if(totalConsulta[i] > 0){
						double tempPorc = 100 * (double)(totalConsulta[i] - matriz[i][i]) / totalConsulta[i];
						java.math.BigDecimal redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						porcentajes[i] = redondeado.doubleValue() ;
				
					}else{
					
						porcentajes[i] = 0.0;
									
					}	
			
				}
			}
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
		
		
			ArrayList consultasEfectuadasVector = new ArrayList();
			ArrayList consultasEfectuadasVectorPorc = new ArrayList();
		
			if(param4.equals("0")){
				for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
				
			 		ConsultasEfectuadasBean bean = new ConsultasEfectuadasBean();		
			  		int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			  		String zonaRegistral = (String)ordenOficRegis.get(i);
			  		String nombreZona =(String)zonas.get((String)ordenOficRegis.get(i));
			  		bean.setZonaRegistral((String)zonas.get(ordenOficRegis.get(i)));
			  		bean.setDestReg1(String.valueOf(matriz[indiceInicial][4]));  // ZR1
			  		bean.setDestReg2(String.valueOf(matriz[indiceInicial][10])); //ZR2
			  		bean.setDestReg3(String.valueOf(matriz[indiceInicial][11])); //ZR3
			  		bean.setDestReg4(String.valueOf(matriz[indiceInicial][8]));  //ZR4
			  		bean.setDestReg5(String.valueOf(matriz[indiceInicial][7]));  //ZR5
			  		bean.setDestReg6(String.valueOf(matriz[indiceInicial][12])); //ZR6
			  		bean.setDestReg7(String.valueOf(matriz[indiceInicial][3]));  //ZR7
			  		bean.setDestReg8(String.valueOf(matriz[indiceInicial][1]));  //ZR8
			  		bean.setDestReg9(String.valueOf(matriz[indiceInicial][0]));  //ZR9
			  		bean.setDestReg10(String.valueOf(matriz[indiceInicial][5])); //ZR10
			  		bean.setDestReg11(String.valueOf(matriz[indiceInicial][9])); //ZR11
			  		bean.setDestReg12(String.valueOf(matriz[indiceInicial][2])); //ZR12
			  		bean.setDestReg13(String.valueOf(matriz[indiceInicial][6])); //ZR13
			  		bean.setTotalZonaReg(String.valueOf(totalConsulta[indiceInicial]));
			  		bean.setPorcentZonaReg(String.valueOf(porcentajes[indiceInicial]) + "%");		
			  		consultasEfectuadasVector.add(bean);
				}
			
				req.setAttribute("consultasEfectuadasVector", consultasEfectuadasVector);
			
			}else if(param4.equals("1")){
			
				for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
					ConsultasEfectuadasBean bean = new ConsultasEfectuadasBean();		
			 	 	int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			  		String zonaRegistral = (String)ordenOficRegis.get(i);
			  		String nombreZona =(String)zonas.get((String)ordenOficRegis.get(i));
			  		bean.setZonaRegistral((String)zonas.get(ordenOficRegis.get(i)));
			  		bean.setDestReg1(String.valueOf(matrizP[indiceInicial][4]) +"%");  // ZR1
			  		bean.setDestReg2(String.valueOf(matrizP[indiceInicial][10])+"%"); //ZR2
			  		bean.setDestReg3(String.valueOf(matrizP[indiceInicial][11])+"%"); //ZR3
			  		bean.setDestReg4(String.valueOf(matrizP[indiceInicial][8])+"%");  //ZR4
			  		bean.setDestReg5(String.valueOf(matrizP[indiceInicial][7])+"%");  //ZR5
			  		bean.setDestReg6(String.valueOf(matrizP[indiceInicial][12])+"%"); //ZR6
			  		bean.setDestReg7(String.valueOf(matrizP[indiceInicial][3])+"%");  //ZR7
			  		bean.setDestReg8(String.valueOf(matrizP[indiceInicial][1])+"%");  //ZR8
			  		bean.setDestReg9(String.valueOf(matrizP[indiceInicial][0])+"%");  //ZR9
			  		bean.setDestReg10(String.valueOf(matrizP[indiceInicial][5])+"%"); //ZR10
			  		bean.setDestReg11(String.valueOf(matrizP[indiceInicial][9])+"%"); //ZR11
			  		bean.setDestReg12(String.valueOf(matrizP[indiceInicial][2])+"%"); //ZR12
			  		bean.setDestReg13(String.valueOf(matrizP[indiceInicial][6])+"%"); //ZR13
			  		bean.setTotalZonaReg("100 %");
			  		consultasEfectuadasVectorPorc.add(bean);
				}
				
				req.setAttribute("consultasEfectuadasVectorPorc", consultasEfectuadasVectorPorc);
			
			}		

			evolucionBean.setTipoPersona(param1);
			evolucionBean.setIndicador("1");
			evolucionBean.setHayRegistros("1");
			req.setAttribute("evolucionBean", evolucionBean);
			req.setAttribute("tipoPersona", param4);
			
			response.setStyle("verFormulario");
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
	
	
	
	
public ControllerResponse runVerReporteEvolucion2State(
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


			// Recuperar Datos de Session //
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
			// Instancia de Bean de datos del Formulario //
			EvolucionBean evolucionBean = new EvolucionBean();
			
			//Nombre del Reporte : Número de Consultas efectuadas fuera de Zona Registral vs.Total Consultas efectuadas por usuarios de una Zona Registral.
			
			//parametros recibidos
			
			//PARAM1 : TIPO DE PERSONA (0:ORGANIZACIONES, 1:INDIVIDUALES, 2: Ambos) 
			//PARAM2 : FECHA DE INICIO DEL REPORTE
			//PARAM3 : FECHA DE FIN DEL REPORTE
			//PARAM4 : FG_PORC (0: NO, 1 : SI)
			
			String param1 = request.getParameter("tipoPersona");
			
			//Recepcion de Parametros de formulario JSP
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
			String date_Inicio = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			/*if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
			{
				String a="1";
			}


			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
			{
				String b="1";					
			}	
			*/
			//FG_PORC (0: NO, 1 : SI)
			
			String param4 = request.getParameter("fgPorc");
			
			//Ejecucion del query 
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT REG_PUB_ORIG,COD_REG_PUB, VAL_TOTAL ");
			query.append(" FROM USO_SERVICIO ");
			query.append(" WHERE REG_PUB_ORIG != '00'");
			query.append(" AND SERVICIO_ID IN (40,70) ");
			query.append(" AND AAAAMMDD BETWEEN ? AND ? "); 
			if(!param1.equals("2")){
				query.append(" AND TIPO_USR= ? ");
			}
			query.append(" AND FG_INTERNO='0' ");
			query.append(" GROUP BY REG_PUB_ORIG,COD_REG_PUB,VAL_TOTAL ");
			query.append(" ORDER BY REG_PUB_ORIG,COD_REG_PUB ");
			
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			pstmt = conn.prepareStatement(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      							ResultSet.CONCUR_UPDATABLE);
			
			pstmt.setString(1, date_Inicio);
			pstmt.setString(2, date_Fin);
			if(!param1.equals("2")){
				pstmt.setString(3 , param1);
			}
			
			rs = pstmt.executeQuery();	
			
			int [][] matriz = new int[13][13]; 
			double [][] matrizP = new double[13][13]; 
			int [] totalConsulta = new int[13];
			double [] porcentajes = new double [13];
			int totalTemp = 0;
			
			boolean isNotEmpty = rs.next();
			
			if(isNotEmpty){
			int temp = Integer.parseInt(rs.getString(1).trim());
			
			rs.beforeFirst();
			
			while(rs.next()){
				
				//acumulamos de por inicio a fin de matriz[inicio][fin]
				matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1] = rs.getInt(3);
				if(temp == Integer.parseInt(rs.getString(1).trim())){	
					totalTemp += matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}else{
					totalConsulta[temp - 1] = totalTemp;
					totalTemp = matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}	 
				temp = Integer.parseInt(rs.getString(1).trim());
				
				
			}
			//para el ultimo quiebre
			totalConsulta[temp - 1] = totalTemp;
			//para el ultimo quiebre
			}//si hay data en el query
			
			//si se pide porcentajes se actualiza la matriz con porcentajes
			
			if(param4.equals("1")){
			
				for(int i = 0 ; i < 13 ; i++){
				
					for(int j = 0 ; j <  13 ; j++){
						if(totalConsulta[i] > 0){
							double div = 100*(double)matriz[i][j]/(double)totalConsulta[i];
							java.math.BigDecimal redondeado = new java.math.BigDecimal(div).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
							matrizP[i][j] = redondeado.doubleValue();
						}else{
							matrizP[i][j] = 0;
						}	
					}
				
				}
			
			}
			
			if(param4.equals("0")){
				for(int i = 0; i < 13 ; i++){
				
					if(totalConsulta[i] > 0){
						double tempPorc = 100 * (double)(totalConsulta[i] - matriz[i][i]) / totalConsulta[i];
						java.math.BigDecimal redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						porcentajes[i] = redondeado.doubleValue() ;
				
					}else{
					
						porcentajes[i] = 0.0;
									
					}	
			
				}
			}
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
		
		
		ArrayList consultasEfectuadas2Vector = new ArrayList();
		ArrayList consultasEfectuadas2VectorPorc = new ArrayList();
		
		if(param4.equals("0")){
			for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
				
			 	ConsultasEfectuadasBean bean = new ConsultasEfectuadasBean();		
			  	int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			  	String zonaRegistral = (String)ordenOficRegis.get(i);
			  	String nombreZona =(String)zonas.get((String)ordenOficRegis.get(i));
			  	bean.setZonaRegistral((String)zonas.get(ordenOficRegis.get(i)));
			  	bean.setDestReg1(String.valueOf(matriz[indiceInicial][4]));  // ZR1
			  	bean.setDestReg2(String.valueOf(matriz[indiceInicial][10])); //ZR2
			  	bean.setDestReg3(String.valueOf(matriz[indiceInicial][11])); //ZR3
			  	bean.setDestReg4(String.valueOf(matriz[indiceInicial][8]));  //ZR4
			  	bean.setDestReg5(String.valueOf(matriz[indiceInicial][7]));  //ZR5
			  	bean.setDestReg6(String.valueOf(matriz[indiceInicial][12])); //ZR6
			  	bean.setDestReg7(String.valueOf(matriz[indiceInicial][3]));  //ZR7
			  	bean.setDestReg8(String.valueOf(matriz[indiceInicial][1]));  //ZR8
			  	bean.setDestReg9(String.valueOf(matriz[indiceInicial][0]));  //ZR9
			  	bean.setDestReg10(String.valueOf(matriz[indiceInicial][5])); //ZR10
			  	bean.setDestReg11(String.valueOf(matriz[indiceInicial][9])); //ZR11
			  	bean.setDestReg12(String.valueOf(matriz[indiceInicial][2])); //ZR12
			  	bean.setDestReg13(String.valueOf(matriz[indiceInicial][6])); //ZR13
			  	bean.setTotalZonaReg(String.valueOf(totalConsulta[indiceInicial]));
			  	bean.setPorcentZonaReg(String.valueOf(porcentajes[indiceInicial])+"%");		
			  	consultasEfectuadas2Vector.add(bean);
		
			}
			
			req.setAttribute("consultasEfectuadas2Vector", consultasEfectuadas2Vector);
			
		}else if(param4.equals("1")){
			
			for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
				ConsultasEfectuadasBean bean = new ConsultasEfectuadasBean();		
			  	int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			  	String zonaRegistral = (String)ordenOficRegis.get(i);
			  	String nombreZona =(String)zonas.get((String)ordenOficRegis.get(i));
			  	bean.setZonaRegistral((String)zonas.get(ordenOficRegis.get(i)));
			  	bean.setDestReg1(String.valueOf(matrizP[indiceInicial][4]) +"%");  // ZR1
			  	bean.setDestReg2(String.valueOf(matrizP[indiceInicial][10])+"%"); //ZR2
			  	bean.setDestReg3(String.valueOf(matrizP[indiceInicial][11])+"%"); //ZR3
			  	bean.setDestReg4(String.valueOf(matrizP[indiceInicial][8])+"%");  //ZR4
			  	bean.setDestReg5(String.valueOf(matrizP[indiceInicial][7])+"%");  //ZR5
			  	bean.setDestReg6(String.valueOf(matrizP[indiceInicial][12])+"%"); //ZR6
			  	bean.setDestReg7(String.valueOf(matrizP[indiceInicial][3])+"%");  //ZR7
			  	bean.setDestReg8(String.valueOf(matrizP[indiceInicial][1])+"%");  //ZR8
			  	bean.setDestReg9(String.valueOf(matrizP[indiceInicial][0])+"%");  //ZR9
			  	bean.setDestReg10(String.valueOf(matrizP[indiceInicial][5])+"%"); //ZR10
			  	bean.setDestReg11(String.valueOf(matrizP[indiceInicial][9])+"%"); //ZR11
			  	bean.setDestReg12(String.valueOf(matrizP[indiceInicial][2])+"%"); //ZR12
			  	bean.setDestReg13(String.valueOf(matrizP[indiceInicial][6])+"%"); //ZR13
			  	bean.setTotalZonaReg("100 %");
			  	consultasEfectuadas2VectorPorc.add(bean);
			
			}
			req.setAttribute("consultasEfectuadas2VectorPorc", consultasEfectuadas2VectorPorc);
			
		}		


			evolucionBean.setTipoPersona(param1);
			evolucionBean.setIndicador("2");
			evolucionBean.setHayRegistros("1");
			req.setAttribute("evolucionBean", evolucionBean);
			
			response.setStyle("verFormulario");
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
	}	public ControllerResponse runExportarReporteEvolucion1State(
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


			// Recuperar Datos de Session //
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
			// Instancia de Bean de datos del Formulario //
			EvolucionBean evolucionBean = new EvolucionBean();
			
			//Nombre del Reporte : Número de Consultas efectuadas fuera de Zona Registral vs.Total Consultas efectuadas por usuarios de una Zona Registral.
			
			//parametros recibidos
			
			//PARAM1 : TIPO DE PERSONA (0:ORGANIZACIONES, 1:INDIVIDUALES, 2: Ambos) 
			//PARAM2 : FECHA DE INICIO DEL REPORTE
			//PARAM3 : FECHA DE FIN DEL REPORTE
			//PARAM4 : FG_PORC (0: NO, 1 : SI)
			
			String param1 = request.getParameter("tipoPersona");
			
			//Recepcion de Parametros de formulario JSP
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
			String date_Inicio = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			/*if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
			{
				String a="1";
			}


			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
			{
				String b="1";					
			}	
			*/
			
			//FG_PORC (0: NO, 1 : SI)
			
			String param4 = request.getParameter("fgPorc");
			
			//Ejecucion del query 
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT REG_PUB_ORIG, COD_REG_PUB, SUM(RPN) RPN, SUM(RPJ) RPJ,SUM(RPI) RPI ");
			query.append(" FROM USO_SERVICIO ");
			query.append(" WHERE REG_PUB_ORIG != '00'");
			query.append(" AND SERVICIO_ID IN (40,70) ");
			query.append(" AND AAAAMMDD BETWEEN ? AND ? "); 
			if(!param1.equals("2")){
				query.append(" AND TIPO_USR= ? ");
			} 
			query.append(" AND FG_INTERNO='0' ");
			query.append(" GROUP BY REG_PUB_ORIG,COD_REG_PUB ");
			query.append(" ORDER BY REG_PUB_ORIG,COD_REG_PUB ");
			
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			pstmt = conn.prepareStatement(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      							ResultSet.CONCUR_UPDATABLE);
			
			pstmt.setString(1, date_Inicio);
			pstmt.setString(2, date_Fin);
			if(!param1.equals("2")){
				pstmt.setString(3 , param1);
			}
			
			rs = pstmt.executeQuery();	
			
			int [][] matriz = new int[13][13]; 
			double [][] matrizP = new double[13][13]; 
			int [] totalConsulta = new int[13];
			double [] porcentajes = new double [13];
			int totalTemp = 0;
			
			boolean isNotEmpty = rs.next();
			
			if(isNotEmpty){
			
			int temp = Integer.parseInt(rs.getString(1).trim());
				
			
			rs.beforeFirst();
			
			while(rs.next()){
				
				//acumulamos de por inicio a fin de matriz[inicio][fin]
				matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1] = rs.getInt(3) + rs.getInt(4) + rs.getInt(5);
				if(temp == Integer.parseInt(rs.getString(1).trim())){	
					totalTemp += matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}else{
					totalConsulta[temp - 1] = totalTemp;
					totalTemp = matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}	 
				temp = Integer.parseInt(rs.getString(1).trim());
				
				
			}
			//para el ultimo quiebre
			totalConsulta[temp - 1] = totalTemp;
			//para el ultimo quiebre
			} //fin del si hay valores en el resultSet
			
			//si se pide porcentajes se actualiza la matriz con porcentajes
			
			if(param4.equals("1")){
			
				for(int i = 0 ; i < 13 ; i++){
				
					for(int j = 0 ; j <  13 ; j++){
						if(totalConsulta[i] > 0){
							double div = 100*(double)matriz[i][j]/(double)totalConsulta[i];
							java.math.BigDecimal redondeado = new java.math.BigDecimal(div).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
							matrizP[i][j] = redondeado.doubleValue();
						}else{
							matrizP[i][j] = 0;
						}	
					}
				
				}
			
			}
			
			if(param4.equals("0")){
				for(int i = 0; i < 13 ; i++){
				
					if(totalConsulta[i] > 0){
						double tempPorc = 100 * (double)(totalConsulta[i] - matriz[i][i]) / totalConsulta[i];
						java.math.BigDecimal redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						porcentajes[i] = redondeado.doubleValue() ;
				
					}else{
					
						porcentajes[i] = 0.0;
									
					}	
			
				}
			}
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
		
			//Generacion del archivo CSV			
			String fname = "reporteEvolucion1.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;
			StringBuffer stb = new StringBuffer();

			stb.append("Numero de Consultas").append(",");
			stb.append("Zona Registral Destino").append(",");
			for(int i = 0 ; i < 12; i++){
				stb.append("ZR"+ (i +1)).append(",");
			}	
			stb.append("ZR13");
			
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
			
			stb.setLength(0);
			stb.append("Zona Registral Origen").append("  ");
			out_cliente.println(stb.toString());
			
			
			
			//Armando el corazon del reporte
			if(param4.equals("0")){
			for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
				
				stb.setLength(0);
			 	int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			 	stb.append((String)zonas.get(ordenOficRegis.get(i))).append(",");
			 	stb.append("").append(",");
			 	stb.append(String.valueOf(matriz[indiceInicial][4])).append(",");  // ZR1
			  	stb.append(String.valueOf(matriz[indiceInicial][10])).append(","); //ZR2
			  	stb.append(String.valueOf(matriz[indiceInicial][11])).append(","); //ZR3
			  	stb.append(String.valueOf(matriz[indiceInicial][8])).append(",");  //ZR4
			  	stb.append(String.valueOf(matriz[indiceInicial][7])).append(",");  //ZR5
			  	stb.append(String.valueOf(matriz[indiceInicial][12])).append(","); //ZR6
			  	stb.append(String.valueOf(matriz[indiceInicial][3])).append(",");  //ZR7
			  	stb.append(String.valueOf(matriz[indiceInicial][1])).append(",");  //ZR8
			  	stb.append(String.valueOf(matriz[indiceInicial][0])).append(",");  //ZR9
			  	stb.append(String.valueOf(matriz[indiceInicial][5])).append(","); //ZR10
			  	stb.append(String.valueOf(matriz[indiceInicial][9])).append(","); //ZR11
			  	stb.append(String.valueOf(matriz[indiceInicial][2])).append(","); //ZR12
			  	stb.append(String.valueOf(matriz[indiceInicial][6])).append(","); //ZR13
			  	stb.append(String.valueOf(totalConsulta[indiceInicial])).append(",");
			  	stb.append(String.valueOf(porcentajes[indiceInicial])+"%");		
			  	out_cliente.println(stb.toString());
		
			}
			
		}else if(param4.equals("1")){
			
			for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
				stb.setLength(0);
			  	int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			  	stb.append((String)zonas.get(ordenOficRegis.get(i))).append(",");
			  	stb.append("").append(",");
			  	stb.append(String.valueOf(matrizP[indiceInicial][4]) +"%").append(",");  // ZR1
			  	stb.append(String.valueOf(matrizP[indiceInicial][10])+"%").append(","); //ZR2
			  	stb.append(String.valueOf(matrizP[indiceInicial][11])+"%").append(","); //ZR3
			  	stb.append(String.valueOf(matrizP[indiceInicial][8])+"%").append(",");  //ZR4
			  	stb.append(String.valueOf(matrizP[indiceInicial][7])+"%").append(",");  //ZR5
			  	stb.append(String.valueOf(matrizP[indiceInicial][12])+"%").append(","); //ZR6
			  	stb.append(String.valueOf(matrizP[indiceInicial][3])+"%").append(",");  //ZR7
			  	stb.append(String.valueOf(matrizP[indiceInicial][1])+"%").append(",");  //ZR8
			  	stb.append(String.valueOf(matrizP[indiceInicial][0])+"%").append(",");  //ZR9
			  	stb.append(String.valueOf(matrizP[indiceInicial][5])+"%").append(","); //ZR10
			  	stb.append(String.valueOf(matrizP[indiceInicial][9])+"%").append(","); //ZR11
			  	stb.append(String.valueOf(matrizP[indiceInicial][2])+"%").append(","); //ZR12
			  	stb.append(String.valueOf(matrizP[indiceInicial][6])+"%").append(","); //ZR13
			  	stb.append("100 %");
			  	out_cliente.println(stb.toString());
			}
		}		
			
			
			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);
			
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
	public ControllerResponse runExportarReporteEvolucion2State(
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


			// Recuperar Datos de Session //
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
			// Instancia de Bean de datos del Formulario //
			EvolucionBean evolucionBean = new EvolucionBean();
			
			//Nombre del Reporte : Número de Consultas efectuadas fuera de Zona Registral vs.Total Consultas efectuadas por usuarios de una Zona Registral.
			
			//parametros recibidos
			
			//PARAM1 : TIPO DE PERSONA (0:ORGANIZACIONES, 1:INDIVIDUALES, 2: Ambos) 
			//PARAM2 : FECHA DE INICIO DEL REPORTE
			//PARAM3 : FECHA DE FIN DEL REPORTE
			//PARAM4 : FG_PORC (0: NO, 1 : SI)
			
			String param1 = request.getParameter("tipoPersona");
			
			//Recepcion de Parametros de formulario JSP
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
			String date_Inicio = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String date_Fin = FechaUtil.getStringDateAAAAMMDD(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			/*if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
			{
				String a="1";
			}


			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
			{
				String b="1";					
			}	
			*/
			
			//FG_PORC (0: NO, 1 : SI)
			
			String param4 = request.getParameter("fgPorc");
			
			//Ejecucion del query 
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT REG_PUB_ORIG,COD_REG_PUB, VAL_TOTAL ");
			query.append(" FROM USO_SERVICIO ");
			query.append(" WHERE REG_PUB_ORIG != '00'");
			query.append(" AND SERVICIO_ID IN (40,70) ");
			query.append(" AND AAAAMMDD BETWEEN ? AND ? "); 
			if(!param1.equals("2")){
				query.append(" AND TIPO_USR= ? ");
			} 
			query.append(" AND FG_INTERNO='0' ");
			query.append(" GROUP BY REG_PUB_ORIG,COD_REG_PUB,VAL_TOTAL ");
			query.append(" ORDER BY REG_PUB_ORIG,COD_REG_PUB ");
			
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			pstmt = conn.prepareStatement(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      							ResultSet.CONCUR_UPDATABLE);
			
			pstmt.setString(1, date_Inicio);
			pstmt.setString(2, date_Fin);
			if(!param1.equals("2")){
				pstmt.setString(3 , param1);
			}
			rs = pstmt.executeQuery();	
			
			int [][] matriz = new int[13][13]; 
			double [][] matrizP = new double[13][13]; 
			int [] totalConsulta = new int[13];
			double [] porcentajes = new double [13];
			int totalTemp = 0;
			
			boolean isNotEmpty = rs.next();
			
			if(isNotEmpty){
			
			int temp = Integer.parseInt(rs.getString(1).trim());
			
			rs.beforeFirst();
			
			while(rs.next()){
				
				//acumulamos de por inicio a fin de matriz[inicio][fin]
				matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1] = rs.getInt(3);
				if(temp == Integer.parseInt(rs.getString(1).trim())){	
					totalTemp += matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}else{
					totalConsulta[temp - 1] = totalTemp;
					totalTemp = matriz[Integer.parseInt(rs.getString(1).trim()) - 1][Integer.parseInt(rs.getString(2).trim()) - 1];
					
				}	 
				temp = Integer.parseInt(rs.getString(1).trim());
				
				
			}
			//para el ultimo quiebre
			totalConsulta[temp - 1] = totalTemp;
			//para el ultimo quiebre
			}//fin si hay datos en el resultset
			
			//si se pide porcentajes se actualiza la matriz con porcentajes
			
			if(param4.equals("1")){
			
				for(int i = 0 ; i < 13 ; i++){
				
					for(int j = 0 ; j <  13 ; j++){
						if(totalConsulta[i] > 0){
							double div = 100*(double)matriz[i][j]/(double)totalConsulta[i];
							java.math.BigDecimal redondeado = new java.math.BigDecimal(div).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
							matrizP[i][j] = redondeado.doubleValue();
						}else{
							matrizP[i][j] = 0;
						}	
					}
				
				}
			
			}
			
			if(param4.equals("0")){
				for(int i = 0; i < 13 ; i++){
				
					if(totalConsulta[i] > 0){
						double tempPorc = 100 * (double)(totalConsulta[i] - matriz[i][i]) / totalConsulta[i];
						java.math.BigDecimal redondeado = new java.math.BigDecimal(tempPorc).setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
						porcentajes[i] = redondeado.doubleValue() ;
				
					}else{
					
						porcentajes[i] = 0.0;
									
					}	
			
				}
			}
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
		
			//Generacion del archivo CSV			
			String fname = "reporteEvolucion1.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;
			StringBuffer stb = new StringBuffer();

			stb.append("Numero de Consultas").append(",");
			stb.append("Zona Registral Destino").append(",");
			for(int i = 0 ; i < 12; i++){
				stb.append("ZR"+ (i +1)).append(",");
			}	
			stb.append("ZR13");
			
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
			
			stb.setLength(0);
			stb.append("Zona Registral Origen").append("  ");
			out_cliente.println(stb.toString());
			
			
			
			//Armando el corazon del reporte
			if(param4.equals("0")){
			for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
				
				stb.setLength(0);
			 	int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			 	stb.append((String)zonas.get(ordenOficRegis.get(i))).append(",");
			 	stb.append("").append(",");
			 	stb.append(String.valueOf(matriz[indiceInicial][4])).append(",");  // ZR1
			  	stb.append(String.valueOf(matriz[indiceInicial][10])).append(","); //ZR2
			  	stb.append(String.valueOf(matriz[indiceInicial][11])).append(","); //ZR3
			  	stb.append(String.valueOf(matriz[indiceInicial][8])).append(",");  //ZR4
			  	stb.append(String.valueOf(matriz[indiceInicial][7])).append(",");  //ZR5
			  	stb.append(String.valueOf(matriz[indiceInicial][12])).append(","); //ZR6
			  	stb.append(String.valueOf(matriz[indiceInicial][3])).append(",");  //ZR7
			  	stb.append(String.valueOf(matriz[indiceInicial][1])).append(",");  //ZR8
			  	stb.append(String.valueOf(matriz[indiceInicial][0])).append(",");  //ZR9
			  	stb.append(String.valueOf(matriz[indiceInicial][5])).append(","); //ZR10
			  	stb.append(String.valueOf(matriz[indiceInicial][9])).append(","); //ZR11
			  	stb.append(String.valueOf(matriz[indiceInicial][2])).append(","); //ZR12
			  	stb.append(String.valueOf(matriz[indiceInicial][6])).append(","); //ZR13
			  	stb.append(String.valueOf(totalConsulta[indiceInicial])).append(",");
			  	stb.append(String.valueOf(porcentajes[indiceInicial])+ "%");		
			  	out_cliente.println(stb.toString());
		
			}
			
		}else if(param4.equals("1")){
			
			for(int i = 0 ;i < ordenOficRegis.size(); i++ ){
				stb.setLength(0);
			  	int indiceInicial = Integer.parseInt((String)ordenOficRegis.get(i)) - 1;
			  	stb.append((String)zonas.get(ordenOficRegis.get(i))).append(",");
			  	stb.append("").append(",");
			  	stb.append(String.valueOf(matrizP[indiceInicial][4]) +"%").append(",");  // ZR1
			  	stb.append(String.valueOf(matrizP[indiceInicial][10])+"%").append(","); //ZR2
			  	stb.append(String.valueOf(matrizP[indiceInicial][11])+"%").append(","); //ZR3
			  	stb.append(String.valueOf(matrizP[indiceInicial][8])+"%").append(",");  //ZR4
			  	stb.append(String.valueOf(matrizP[indiceInicial][7])+"%").append(",");  //ZR5
			  	stb.append(String.valueOf(matrizP[indiceInicial][12])+"%").append(","); //ZR6
			  	stb.append(String.valueOf(matrizP[indiceInicial][3])+"%").append(",");  //ZR7
			  	stb.append(String.valueOf(matrizP[indiceInicial][1])+"%").append(",");  //ZR8
			  	stb.append(String.valueOf(matrizP[indiceInicial][0])+"%").append(",");  //ZR9
			  	stb.append(String.valueOf(matrizP[indiceInicial][5])+"%").append(","); //ZR10
			  	stb.append(String.valueOf(matrizP[indiceInicial][9])+"%").append(","); //ZR11
			  	stb.append(String.valueOf(matrizP[indiceInicial][2])+"%").append(","); //ZR12
			  	stb.append(String.valueOf(matrizP[indiceInicial][6])+"%").append(","); //ZR13
			  	stb.append("100 %");
			  	out_cliente.println(stb.toString());
			}
		}		
			
			
			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);
			
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
	public ControllerResponse runVerReporteEvolucion3State(
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
			String fechaIni = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String fechaFin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			/*
			if (!FechaUtil.verifyDate(date_Inicio) || !FechaUtil.verifyDate(date_Fin))
			if (FechaUtil.compare(date_Inicio,date_Fin)>0)
			*/

			String tipoPersona = request.getParameter("tipoPersona");
			//  tipoPersona : 1 natural
			//                0 juridica
			//                2 todos
			
			String param4 = request.getParameter("fgPorc");
			//  param4 : 0 reporte de conteo
			//           1 reporte de importes
			
			
			StringBuffer query  = new StringBuffer();
			
			
//********************************************************************************************			
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************

			if (param4.equals("0"))
			{
				String grupoPersona="";
				
				if (tipoPersona.equals("0"))
						grupoPersona="'PJ'";
				if (tipoPersona.equals("1"))
						grupoPersona="'PN'";						
				if (tipoPersona.equals("2"))
						grupoPersona="'PN','PJ'";
				
				query.append("select t1.Nombre_RazonSocial, t1.titulos, t1.partidas, t1.total, t2.ingreso                        ");
				query.append("from (select nvl(t4.persona_id,t3.persona_id) persona_id,                                          ");
				query.append("             decode(t3.pe_juri_id,null, 'PN'||'|'||t3.nombres||' '||t3.ape_pat||' '||t3.ape_mat,   ");
				query.append("                                        'PJ'||'|'||t4.raz_soc)Nombre_RazonSocial,                  ");
				query.append("             count(case when (t1.servicio_id = 40) then 1 else null end) titulos,                  ");
				query.append("             count(case when (t1.servicio_id != 40) then 1 else null end)partidas,                 ");
				query.append("             count(*) total                                                                        ");
				query.append("      from   transaccion t1, cuenta t2,                                                            ");
				query.append("             pe_natu t3, pe_juri t4                                                                ");
				query.append("      where t1.cuenta_id = t2.cuenta_id and                                                        ");
				query.append("            t2.pe_natu_id = t3.pe_natu_id and                                                      ");
				query.append("            t3.pe_juri_id = t4.pe_juri_id(+) and                                                   ");
				query.append("            t1.servicio_id not in (10,50,60) and                                                   ");
				query.append("            t1.fec_hor between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and        ");
				query.append("                               to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')            ");
				query.append("                                                                                                   ");
				query.append("      group by nvl(t4.persona_id,t3.persona_id),                                                   ");
				query.append("               decode(t3.pe_juri_id,null, 'PN'||'|'||t3.nombres||' '||t3.ape_pat||' '||t3.ape_mat, ");
				query.append("                                          'PJ'||'|'||t4.raz_soc)) t1,                              ");              
				query.append("     (select t2.persona_id, sum(t1.monto)ingreso                                                   ");         
				query.append("      from   abono t1, persona t2                                                                  ");         
				//query.append("      where  t1.persona_id = t2.persona_id and                                                     ");         
				query.append("      where  t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and                                                     ");         
				query.append("        t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and            ");
				query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')                ");
				query.append("      group by t2.persona_id)t2                                                                    ");         
				query.append("where t1.persona_id = t2.persona_id(+) and                                                         ");         
				query.append("      substr(t1.nombre_razonsocial,0,2) in ("+grupoPersona+")                                      ");
				query.append("order by 4 desc                                                                                    ");						
			}//if (param4.equals("0"))
			
			
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************			
			
			
			if (param4.equals("1"))
			{
				if (tipoPersona.equals("1"))
						{
							//natural
							
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat NOMBRE");
							query.append(" from abono t1, persona t2, pe_natu t3");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t3.persona_id and");
							query.append("      t3.pe_juri_id is null and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat");
							query.append(" order by abono desc");
						}
				if (tipoPersona.equals("0"))
						{
							//juridico
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t4.raz_soc NOMBRE");
							query.append(" from abono t1, persona t2, pe_juri t4");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t4.persona_id and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t4.raz_soc");
							query.append(" order by abono desc							");
						}
				if (tipoPersona.equals("2"))
						{
							//ambos
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat NOMBRE");
							query.append(" from abono t1, persona t2, pe_natu t3");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t3.persona_id and");
							query.append("      t3.pe_juri_id is null and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat");
							query.append(" union");
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t4.raz_soc NOMBRE");
							query.append(" from abono t1, persona t2, pe_juri t4");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t4.persona_id and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t4.raz_soc");
							query.append(" order by abono desc							");
						}
			}
			
			if (isTrace(this)) System.out.println(query.toString());
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query.toString());
			
			ArrayList arrayConsultasPeNatu = new ArrayList();
			int cont = 1;
			while(rs.next())
			{
				
				RankingUsuariosBean bean = new RankingUsuariosBean();
				bean.setPosicion(String.valueOf(cont));
				
				if(param4.equals("0"))
				{
					String[] str = Tarea.convierteTiraEnArreglo(rs.getString(1),"|");
					
					bean.setNombreRazon(str[1]);
					bean.setNumTitulos(String.valueOf(rs.getInt(2)));
					bean.setNumPartidas(String.valueOf(rs.getInt(3)));
					bean.setTotalConsultas(String.valueOf(rs.getInt(4)));
					double ingreso = rs.getDouble(5);
					bean.setIngreso(""+ingreso);
					bean.setTipoPersona(str[0]);
				}

				if(param4.equals("1"))
				{
					bean.setNombreRazon(rs.getString(4));
					bean.setTotalConsultas(String.valueOf(rs.getDouble(3)));
					bean.setTipoPersona(rs.getString(2));
				}
				
				
				if(cont % 2 == 0)
				   bean.setColorCelda("#FFFFFF");	
				else
				   bean.setColorCelda("#CCCCCC");
				
				arrayConsultasPeNatu.add(bean);
				cont++;
			} // while
			
			req.setAttribute("modo","30");
			
			if(arrayConsultasPeNatu.size() == 0)
			{
				evolucionBean.setHayRegistros("0");
				evolucionBean.setMensajeError("No Existen registros con el criterio de búsqueda seleccionado");
			}
			
			if(arrayConsultasPeNatu.size() > 0)
			{
				evolucionBean.setHayRegistros("1");
			
				if(param4.equals("0"))	
					req.setAttribute("arrayConsultasPeNatu", arrayConsultasPeNatu);
				if(param4.equals("1"))
					req.setAttribute("arrayConsultasTotalAbonos", arrayConsultasPeNatu);
			}			
			
			
			
			evolucionBean.setTipoPersona(tipoPersona);
			evolucionBean.setIndicador("3");
			req.setAttribute("evolucionBean", evolucionBean);
			req.setAttribute("tipoPersona", param4);
			
			response.setStyle("verFormulario");
			//conn.commit();
			
			
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
	public ControllerResponse runExportarReporteEvolucion3State(
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
			String fechaIni = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio));
			String fechaFin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			String tipoPersona = request.getParameter("tipoPersona");
			//  tipoPersona : 1 natural
			//                0 juridica
			//                2 todos
			
			String param4 = request.getParameter("fgPorc");
			//  param4 : 0 reporte de conteo
			//           1 reporte de importes
			
			//Construccion del query 
			StringBuffer query = new StringBuffer();
			
//********************************************************************************************			
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************

			if (param4.equals("0"))
			{
				String grupoPersona="";
				
				if (tipoPersona.equals("0"))
						grupoPersona="'PJ'";
				if (tipoPersona.equals("1"))
						grupoPersona="'PN'";						
				if (tipoPersona.equals("2"))
						grupoPersona="'PN','PJ'";
				
				query.append("select t1.Nombre_RazonSocial, t1.titulos, t1.partidas, t1.total, t2.ingreso                        ");
				query.append("from (select nvl(t4.persona_id,t3.persona_id) persona_id,                                          ");
				query.append("             decode(t3.pe_juri_id,null, 'PN'||'|'||t3.nombres||' '||t3.ape_pat||' '||t3.ape_mat,   ");
				query.append("                                        'PJ'||'|'||t4.raz_soc)Nombre_RazonSocial,                  ");
				query.append("             count(case when (t1.servicio_id = 40) then 1 else null end) titulos,                  ");
				query.append("             count(case when (t1.servicio_id != 40) then 1 else null end)partidas,                 ");
				query.append("             count(*) total                                                                        ");
				query.append("      from   transaccion t1, cuenta t2,                                                            ");
				query.append("             pe_natu t3, pe_juri t4                                                                ");
				query.append("      where t1.cuenta_id = t2.cuenta_id and                                                        ");
				query.append("            t2.pe_natu_id = t3.pe_natu_id and                                                      ");
				query.append("            t3.pe_juri_id = t4.pe_juri_id(+) and                                                   ");
				query.append("            t1.servicio_id not in (10,50,60) and                                                   ");
				query.append("            t1.fec_hor between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and        ");
				query.append("                               to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')            ");
				query.append("                                                                                                   ");
				query.append("      group by nvl(t4.persona_id,t3.persona_id),                                                   ");
				query.append("               decode(t3.pe_juri_id,null, 'PN'||'|'||t3.nombres||' '||t3.ape_pat||' '||t3.ape_mat, ");
				query.append("                                          'PJ'||'|'||t4.raz_soc)) t1,                              ");              
				query.append("     (select t2.persona_id, sum(t1.monto)ingreso                                                   ");         
				query.append("      from   abono t1, persona t2                                                                  ");         
				query.append("      where  t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and                                                     ");         
				query.append("        t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and            ");
				query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')                ");
				query.append("      group by t2.persona_id)t2                                                                    ");         
				query.append("where t1.persona_id = t2.persona_id(+) and                                                         ");         
				query.append("      substr(t1.nombre_razonsocial,0,2) in ("+grupoPersona+")                                      ");
				query.append("order by 4 desc                                                                                    ");						
			}//if (param4.equals("0"))
			
			
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************
//********************************************************************************************			
			
			
			if (param4.equals("1"))
			{
				if (tipoPersona.equals("1"))
						{
							//natural
							
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat NOMBRE");
							query.append(" from abono t1, persona t2, pe_natu t3");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t3.persona_id and");
							query.append("      t3.pe_juri_id is null and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat");
							query.append(" order by abono desc");
						}
				if (tipoPersona.equals("0"))
						{
							//juridico
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t4.raz_soc NOMBRE");
							query.append(" from abono t1, persona t2, pe_juri t4");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t4.persona_id and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t4.raz_soc");
							query.append(" order by abono desc							");
						}
				if (tipoPersona.equals("2"))
						{
							//ambos
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat NOMBRE");
							query.append(" from abono t1, persona t2, pe_natu t3");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t3.persona_id and");
							query.append("      t3.pe_juri_id is null and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t3.nombres ||' '|| t3.ape_pat ||' '|| t3.ape_mat");
							query.append(" union");
							query.append(" select t2.persona_id, t2.tpo_pers, sum(monto) abono, t4.raz_soc NOMBRE");
							query.append(" from abono t1, persona t2, pe_juri t4");
							query.append(" where t1.tipo_abono IN ('L','V') and t1.persona_id = t2.persona_id and");
							query.append("      t2.persona_id = t4.persona_id and");
							query.append("      t1.ts_crea between to_date('"+fechaIni+" 00:00:00','DD/MM/YYYY hh24:mi:ss') and ");
							query.append("                           to_date('"+fechaFin+" 23:59:59','DD/MM/YYYY hh24:mi:ss')");
							query.append(" group by t2.persona_id, t2.tpo_pers, t4.raz_soc");
							query.append(" order by abono desc							");
						}
			}
			
			
			if (isTrace(this)) System.out.println(query.toString());
			
			Statement stmt = conn.createStatement();
			
			//ejecutamos el query de titulos
			ResultSet rs = stmt.executeQuery(query.toString());
				
			//Generacion del archivo CSV			
			String fname = "reporteEvolucion1.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;
			StringBuffer stb = new StringBuffer();
			  
			stb.append("Nro").append(",");
			stb.append("Nombre/Razon Social ").append(",");
			stb.append("Tipo").append(",");
			
			if(param4.equals("0")){		
				stb.append("Títulos ").append(",");
				stb.append("Partidas ").append(",");
			}
			stb.append("Total ");
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
			
			
			ArrayList arrayConsultasPeNatu = new ArrayList();
			int cont = 1;
			while(rs.next())
			{
				
				stb.setLength(0);	
				stb.append(String.valueOf(cont)).append(",");
				
				if(param4.equals("0"))
				{
					String[] str = Tarea.convierteTiraEnArreglo(rs.getString(1),"|");
					
					stb.append(str[1]).append(",");
					stb.append(String.valueOf(rs.getInt(2))).append(",");
					stb.append(String.valueOf(rs.getInt(3))).append(",");
					stb.append(String.valueOf(rs.getInt(4))).append(",");
					double ingreso = rs.getDouble(5);
					stb.append(""+ingreso).append(",");
					stb.append(str[0]);
				}

				if(param4.equals("1"))
				{
					stb.append(rs.getString(4)).append(",");
					stb.append(String.valueOf(rs.getDouble(3))).append(",");
					stb.append(rs.getString(2));
				}
				
				out_cliente.println(stb.toString());
				cont++;
			}
			
						
			
			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);
			
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