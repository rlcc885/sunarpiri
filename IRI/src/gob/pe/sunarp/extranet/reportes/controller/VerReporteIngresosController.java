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

public class VerReporteIngresosController extends ControllerExtension implements Constantes{
	private String thisClass = VerReporteIngresosController.class.getName() + ".";

	public VerReporteIngresosController() {
		super();
		addState(new State("verFormulario", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("verReporte", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("verAbono", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("exportar", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("exportarAbono", "Muestra la ventana de Busqueda y Resultados"));
		setInitialState("verFormulario");
	}

	public String getTitle() {
		return new String("VerReporteIngresosController");
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
			
			req.setAttribute("hayData", null);

			response.setStyle("muestra");
			
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}


	protected ControllerResponse runVerReporteState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			//Capturo variables de Fecha
				String anoinicio = request.getParameter("anoinicio");
				String mesinicio = request.getParameter("mesinicio");
				String diainicio = request.getParameter("diainicio");
				String anofin = request.getParameter("anofin");
				String mesfin = request.getParameter("mesfin");
				String diafin = request.getParameter("diafin");
				
				int _di = Integer.parseInt(diainicio);
				int _mi = Integer.parseInt(mesinicio);
				int _ai = Integer.parseInt(anoinicio);
				int _df = Integer.parseInt(diafin);
				int _mf = Integer.parseInt(mesfin);
				int _af = Integer.parseInt(anofin);
				
				String fechitaI = _di + "/" + _mi + "/" + _ai;
				String fechitaF = _df + "/" + _mf + "/" + _af;
				
				req.setAttribute("fecini", fechitaI);
				req.setAttribute("fecfin", fechitaF);				

			//Construyendo la fecha de inicio y fecha de fin para el Oracle
				String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
				String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);
				
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());
			
			req.setAttribute("selectedIDay",diainicio);
			req.setAttribute("selectedIMonth",mesinicio);
			req.setAttribute("selectedIYear",anoinicio);
			req.setAttribute("selectedFDay",diafin);
			req.setAttribute("selectedFMonth",mesfin);
			req.setAttribute("selectedFYear",anofin);			

			java.sql.Statement stmt = null;
			java.sql.ResultSet rs = null;

			//Query para obtener el MONTO TOTAL
				long total;
				/**
				 * Inicio:mgarate 01/02/2008
				 * ticket # 23664 
				 */
				/* 
				String sql = "SELECT Sum(a.MONTO) AS MONTO" +
							 " FROM ABONO a" +
							 " WHERE a.estado != '0' AND a.TIPO_ABONO in ('L','V')" +
							 " AND a.TS_MODI BETWEEN " + fechaInicio + " AND " + fechaFin;
				*/
				// se evita llamar a la oficina registral web y se obtiene un monto total
				String sql = "SELECT Sum(a.MONTO) AS MONTO" +
							 " FROM ABONO a" +
							 " WHERE a.estado != '0' AND a.TIPO_ABONO in ('L', 'V') " +
							 " AND a.TS_MODI BETWEEN " + fechaInicio + " AND " + fechaFin + " AND " +
							 " a.ofic_reg_id !='00' AND a.reg_pub_id != '00' "; 
									
				stmt = conn.createStatement();
				if (isTrace(this)) System.out.println("__verSQL " + sql);
				rs = stmt.executeQuery(sql);
			
				if(rs.next())
					total = (long) rs.getDouble(1);
				else
					throw new ReporteNoRegistroException("muestra");
			
				// en este query solo se llama al monto de la oficina registral web 
				// y se suman al monto parcial sacado en el query anterior
				sql = "SELECT Sum(a.MONTO) AS MONTO " +
				  	  " FROM ABONO a, PAGO_EN_LINEA p " +
				  	  " WHERE a.estado != '0' AND " +
				  	  " a.movimiento_id = p.movimiento_id AND " + 
				  	  " a.TIPO_ABONO in ('L', 'V') " +
				      " AND a.TS_MODI BETWEEN " +fechaInicio+ " AND " +fechaFin+ " AND"+ 
				      " A.REG_PUB_ID='00' AND A.OFIC_REG_ID = '00' ";
				        	
				stmt = conn.createStatement();
				if (isTrace(this)) System.out.println("__verSQL " + sql);
				rs = stmt.executeQuery(sql);
				
				if(rs.next())
					total = total + (long) rs.getDouble(1);
				else
					throw new ReporteNoRegistroException("muestra");
				
			//Query para el detalle	
				/*
				sql = "SELECT a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR, Count(a.MONTO) AS NRO, Sum(a.MONTO) AS MONTO " +
						"FROM ABONO a, OFIC_REGISTRAL b " + 
						"WHERE b.REG_PUB_ID(+) =a.REG_PUB_ID AND b.OFIC_REG_ID(+) = a.OFIC_REG_ID " +
						"AND a.TS_MODI BETWEEN " + fechaInicio + " AND " + fechaFin +
						" AND a.estado != '0' AND a.TIPO_ABONO in ('L','V')" +
						" GROUP BY a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR" +
						" ORDER BY a.reg_pub_id, a.OFIC_REG_ID, a.TIPO_USR";
					*/
				// aqui en el primer query se evito que tocara la oficina web y con un union se sumo otro query que solo 
				// selecciono la oficina web pero que estaba anidada con la tabla pago en linea 
				sql = "SELECT REG_PUB_ID,OFIC_REG_ID,NOMBRE,TIPO_USR,NRO, MONTO " +
			       	  " FROM (SELECT a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR, Count(a.MONTO) AS NRO, Sum(a.MONTO) AS MONTO " +
			  		  " FROM ABONO a, OFIC_REGISTRAL b " + 
			  		  " WHERE b.REG_PUB_ID(+) =a.REG_PUB_ID AND b.OFIC_REG_ID(+) = a.OFIC_REG_ID " + 
			  		  " AND a.TS_MODI BETWEEN "+fechaInicio+" AND " + fechaFin +  
			  		  " AND a.estado != '0' AND a.TIPO_ABONO in ('L','V') AND " +
			          " A.REG_PUB_ID != '00' AND A.OFIC_REG_ID !='00' " +
			  		  " GROUP BY a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR " +
			   		  " ORDER BY a.reg_pub_id, a.OFIC_REG_ID, a.TIPO_USR) " +
					  " UNION " +
					  " SELECT REG_PUB_ID,OFIC_REG_ID,NOMBRE,TIPO_USR,NRO, MONTO " +
			          " FROM (SELECT a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR, Count(a.MONTO) AS NRO, Sum(a.MONTO) AS MONTO " + 
			          " FROM ABONO a, OFIC_REGISTRAL b, PAGO_EN_LINEA p " +
			  		  " WHERE b.REG_PUB_ID(+) =a.REG_PUB_ID AND b.OFIC_REG_ID(+) = a.OFIC_REG_ID AND " +
			          " a.movimiento_id = p.movimiento_id AND " +
			  		  " a.TS_MODI BETWEEN " +fechaInicio+ " AND " + fechaFin +
			  		  " AND a.estado != '0' AND a.TIPO_ABONO in ('L','V') AND " +
			          " A.REG_PUB_ID = '00' AND A.OFIC_REG_ID ='00' " +
			  		  " GROUP BY a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR " +
			  		  " ORDER BY a.reg_pub_id, a.OFIC_REG_ID, a.TIPO_USR) ";
				
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				if (isTrace(this)) System.out.println("__verSQL " + sql);
				rs = stmt.executeQuery(sql);
				
			//Declarando variables
				boolean nuevo;
				boolean individual = false;
				boolean organizacion = false;

				String regPubId = null;
				String oficRegId = null;
				String tipoUser = null;
				StringBuffer stb = null;
				StringBuffer stb1 = null;
				String aux = "";
				long contadorOrg = 0;
				long contadorIndiv = 0;
				long montoOrg = 0;
				long montoIndiv = 0;

				IngresoBean bean = null;
				java.util.List lista = new java.util.ArrayList();
			
				boolean encontro = false;
			//Recorriendo el resultado del Query y usando logica para ponerlo en el Bean
				while(rs.next()){
					encontro = true;
					regPubId = rs.getString(1);
					oficRegId = rs.getString(2);
					tipoUser = rs.getString(4);
					
					//Si registro NO pertenece a la misma oficina que el registro anterior (quebrar)
						if(!aux.equals(regPubId + oficRegId)){
							//La primera vez no existe registro anterior, por lo que no hay que guardar datos
								if(bean != null){
									//La oficina no presenta abonos para personas individuales
										if(!individual){
											bean.setIndividual(0);
											bean.setMontoIndiv(0);
										}
									//La oficina no presenta abonos para organizaciones									
										if(!organizacion){
											bean.setOrganizacion(0);
											bean.setMontoOrg(0);
										}

									bean.setTotal(bean.getMontoIndiv() + bean.getMontoOrg());
									stb1 = new StringBuffer("Total: ").append(total).append(". Total Oficina: ").append(bean.getTotal());
									
									double porcentaje = (((double) bean.getTotal()) / ((double) total)) * 100;
									stb1.append(". Porcentaje: " + Double.toString(porcentaje));
									porcentaje = Math.round(porcentaje * 100);
									porcentaje = porcentaje / 100;
									bean.setPorcentaje(porcentaje);
									stb1.append(". Procentaje Redondeado: ").append(porcentaje);
									if (isTrace(this)) trace(stb1.toString(), request);
									//Llenando el Bean con data y guardandolo en la Lista
										lista.add(bean);
								}
						//Creando el Bean para almacenar los datos, se anade datos generales
							bean = new IngresoBean();
							bean.setRegPubId(regPubId);	//RegPubId
							bean.setOficRegId(oficRegId);	//OficRegId
							bean.setOficina(rs.getString(3));	//Nombre Oficina
							individual = false;
							organizacion = false;
						}
					
					//Si la oficina presenta Abonos para personas individuales
						if(tipoUser.equals("I")){
							stb = new StringBuffer("RegPubId: ").append(regPubId).append(". OficRegId: ").append(oficRegId).append(". Tipo Usuario: Individual");
							if (isTrace(this)) trace(stb.toString(), request);
							
							bean.setIndividual(rs.getLong(5));
							bean.setMontoIndiv((long) rs.getDouble(6));
							aux = regPubId + oficRegId;
							
							contadorIndiv += bean.getIndividual();
							montoIndiv += bean.getMontoIndiv();
							
							individual = true;	//Activa flag indicando que existe abonos para personas individuales
						}
	
					//Si la oficina presenta Abonos para organizaciones	
						if(tipoUser.equals("O")){
							stb = new StringBuffer("RegPubId: ").append(regPubId).append(". OficRegId: ").append(oficRegId).append(". Tipo Usuario: Organizacion");
							if (isTrace(this)) trace(stb.toString(), request);
							
							bean.setOrganizacion(rs.getLong(5));
							bean.setMontoOrg((long) rs.getDouble(6));
							aux = regPubId + oficRegId;
							
							contadorOrg += bean.getOrganizacion();
							montoOrg += bean.getMontoOrg();
							organizacion = true;	//Activa flag indicando que existe abonos para organizaciones
						}
				}
			if(!encontro)
				throw new ReporteNoRegistroException("muestra");

			//La oficina no presenta abonos para personas individuales
				if(!individual){
					bean.setIndividual(0);
					bean.setMontoIndiv(0);
				}
			//La oficina no presenta abonos para organizaciones									
				if(!organizacion){
					bean.setOrganizacion(0);
					bean.setMontoOrg(0);
				}

			bean.setTotal(bean.getMontoIndiv() + bean.getMontoOrg());
			stb1 = new StringBuffer("Total: ").append(total).append(". Total Oficina: ").append(bean.getTotal());
									
			double porcentaje = (((double) bean.getTotal()) / ((double) total)) * 100;
			stb1.append(". Porcentaje: " + Double.toString(porcentaje));
			porcentaje = Math.round(porcentaje * 100);
			porcentaje = porcentaje / 100;
			bean.setPorcentaje(porcentaje);
			stb1.append(". Procentaje Redondeado: ").append(porcentaje);
			if (isTrace(this)) trace(stb1.toString(), request);
			
			//Llenando el Bean con data y guardandolo en la Lista
				lista.add(bean);
			
			req.setAttribute("hayData", "S");
			req.setAttribute("numeroOrg", Long.toString(contadorOrg));
			req.setAttribute("totalmontoOrg", Long.toString(montoOrg));
			req.setAttribute("numeroIndiv", Long.toString(contadorIndiv));
			req.setAttribute("totalmontoIndiv", Long.toString(montoIndiv));
			req.setAttribute("totalgeneral", Long.toString(total));
			req.setAttribute("lista", lista);
			
			response.setStyle("muestra");
			conn.commit();
		} catch (ReporteNoRegistroException re) {
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "N");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("direccionar", "VerReporteIngresos.do");
			rollback(conn, request);
			response.setStyle(re.getForward());
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
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
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

		
			//Capturo variables de Fecha
				String anoinicio = request.getParameter("anoinicio");
				String mesinicio = request.getParameter("mesinicio");
				String diainicio = request.getParameter("diainicio");
				String anofin = request.getParameter("anofin");
				String mesfin = request.getParameter("mesfin");
				String diafin = request.getParameter("diafin");
				
			req.setAttribute("selectedIDay",diainicio);
			req.setAttribute("selectedIMonth",mesinicio);
			req.setAttribute("selectedIYear",anoinicio);
			req.setAttribute("selectedFDay",diafin);
			req.setAttribute("selectedFMonth",mesfin);
			req.setAttribute("selectedFYear",anofin);					
				
				int _di = Integer.parseInt(diainicio);
				int _mi = Integer.parseInt(mesinicio);
				int _ai = Integer.parseInt(anoinicio);
				int _df = Integer.parseInt(diafin);
				int _mf = Integer.parseInt(mesfin);
				int _af = Integer.parseInt(anofin);

			//Construyendo la fecha de inicio y fecha de fin para el Oracle
				String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
				String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);

			java.sql.Statement stmt = null;
			java.sql.ResultSet rs = null;
			
			//Query para obtener el MONTO TOTAL
				long total;
				/**
				 * Inicio:mgarate 01/02/2008
				 * ticket # 23664 
				 */
				/* 
				String sql = "SELECT Sum(a.MONTO) AS MONTO" +
							 " FROM ABONO a" +
							 " WHERE a.estado != '0' AND a.TIPO_ABONO in ('L','V')" +
							 " AND a.TS_MODI BETWEEN " + fechaInicio + " AND " + fechaFin;
				*/
				// se evita llamar a la oficina registral web y se obtiene un monto total
				String sql = "SELECT Sum(a.MONTO) AS MONTO" +
							 " FROM ABONO a" +
							 " WHERE a.estado != '0' AND a.TIPO_ABONO in ('L', 'V') " +
							 " AND a.TS_MODI BETWEEN " + fechaInicio + " AND " + fechaFin + " AND " +
							 " a.ofic_reg_id !='00' AND a.reg_pub_id != '00' "; 
									
				stmt = conn.createStatement();
				if (isTrace(this)) System.out.println("__verSQL " + sql);
				rs = stmt.executeQuery(sql);
			
				if(rs.next())
					total = (long) rs.getDouble(1);
				else
					throw new ReporteNoRegistroException("muestra");
			
				// en este query solo se llama al monto de la oficina registral web 
				// y se suman al monto parcial sacado en el query anterior
				sql = "SELECT Sum(a.MONTO) AS MONTO " +
				  	  " FROM ABONO a, PAGO_EN_LINEA p " +
				  	  " WHERE a.estado != '0' AND " +
				  	  " a.movimiento_id = p.movimiento_id AND " + 
				  	  " a.TIPO_ABONO in ('L', 'V') " +
				      " AND a.TS_MODI BETWEEN " +fechaInicio+ " AND " +fechaFin+ " AND"+ 
				      " A.REG_PUB_ID='00' AND A.OFIC_REG_ID = '00' ";
				        	
				stmt = conn.createStatement();
				if (isTrace(this)) System.out.println("__verSQL " + sql);
				rs = stmt.executeQuery(sql);
				
				if(rs.next())
					total = total + (long) rs.getDouble(1);
				else
					throw new ReporteNoRegistroException("muestra");
				
			//Query para el detalle	
				/*
				sql = "SELECT a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR, Count(a.MONTO) AS NRO, Sum(a.MONTO) AS MONTO " +
						"FROM ABONO a, OFIC_REGISTRAL b " + 
						"WHERE b.REG_PUB_ID(+) =a.REG_PUB_ID AND b.OFIC_REG_ID(+) = a.OFIC_REG_ID " +
						"AND a.TS_MODI BETWEEN " + fechaInicio + " AND " + fechaFin +
						" AND a.estado != '0' AND a.TIPO_ABONO in ('L','V')" +
						" GROUP BY a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR" +
						" ORDER BY a.reg_pub_id, a.OFIC_REG_ID, a.TIPO_USR";
					*/
				// aqui en el primer query se evito que tocara la oficina web y con un union se sumo otro query que solo 
				// selecciono la oficina web pero que estaba anidada con la tabla pago en linea 
				sql = "SELECT REG_PUB_ID,OFIC_REG_ID,NOMBRE,TIPO_USR,NRO, MONTO " +
			       	  " FROM (SELECT a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR, Count(a.MONTO) AS NRO, Sum(a.MONTO) AS MONTO " +
			  		  " FROM ABONO a, OFIC_REGISTRAL b " + 
			  		  " WHERE b.REG_PUB_ID(+) =a.REG_PUB_ID AND b.OFIC_REG_ID(+) = a.OFIC_REG_ID " + 
			  		  " AND a.TS_MODI BETWEEN "+fechaInicio+" AND " + fechaFin +  
			  		  " AND a.estado != '0' AND a.TIPO_ABONO in ('L','V') AND " +
			          " A.REG_PUB_ID != '00' AND A.OFIC_REG_ID !='00' " +
			  		  " GROUP BY a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR " +
			   		  " ORDER BY a.reg_pub_id, a.OFIC_REG_ID, a.TIPO_USR) " +
					  " UNION " +
					  " SELECT REG_PUB_ID,OFIC_REG_ID,NOMBRE,TIPO_USR,NRO, MONTO " +
			          " FROM (SELECT a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR, Count(a.MONTO) AS NRO, Sum(a.MONTO) AS MONTO " + 
			          " FROM ABONO a, OFIC_REGISTRAL b, PAGO_EN_LINEA p " +
			  		  " WHERE b.REG_PUB_ID(+) =a.REG_PUB_ID AND b.OFIC_REG_ID(+) = a.OFIC_REG_ID AND " +
			          " a.movimiento_id = p.movimiento_id AND " +
			  		  " a.TS_MODI BETWEEN " +fechaInicio+ " AND " + fechaFin +
			  		  " AND a.estado != '0' AND a.TIPO_ABONO in ('L','V') AND " +
			          " A.REG_PUB_ID = '00' AND A.OFIC_REG_ID ='00' " +
			  		  " GROUP BY a.reg_pub_id, a.OFIC_REG_ID, b.NOMBRE, a.TIPO_USR " +
			  		  " ORDER BY a.reg_pub_id, a.OFIC_REG_ID, a.TIPO_USR) ";
				
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				if (isTrace(this)) System.out.println("__verSQL " + sql);
				rs = stmt.executeQuery(sql);
				
			if (isTrace(this)) trace("Datos del reporte de Ingresos...", request);

			//Declarando variables
				boolean nuevo;
				boolean individual = false;
				boolean organizacion = false;

				String regPubId = null;
				String oficRegId = null;
				String tipoUser = null;
				StringBuffer stb = null;
				StringBuffer stb1 = null;
				String aux = "";
				long contadorOrg = 0;
				long contadorIndiv = 0;
				long montoOrg = 0;
				long montoIndiv = 0;
				boolean cabecera = true;

	 			String fname = "ingreso.csv";
				HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
				PrintWriter out_cliente = null;

				IngresoBean bean = null;
				java.util.List lista = new java.util.ArrayList();


			//Recorriendo el resultado del Query y usando logica para ponerlo en el Bean
				while(rs.next()){
					
					//Poniendo la Cabecera			
						if(cabecera){
							res.setContentType("archivo/xxx");
							res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
	
							stb = new StringBuffer();
							stb.append("Ofic Registral").append(",");
							stb.append("Organizaciones").append(",");
							stb.append("Monto").append(",");
							stb.append("Individuales").append(",");
							stb.append("Monto").append(",");
							stb.append("Total").append(",");
							stb.append("Porcentajes(%)");
							
							out_cliente = res.getWriter();
							out_cliente.println(stb.toString());
							cabecera = false;
						}
					
					regPubId = rs.getString(1);
					oficRegId = rs.getString(2);
					tipoUser = rs.getString(4);
					
					//Si registro NO pertenece a la misma oficina que el registro anterior (quebrar)
						if(!aux.equals(regPubId + oficRegId)){
							//La primera vez no existe registro anterior, por lo que no hay que guardar datos
								if(bean != null){
									//La oficina no presenta abonos para personas individuales
										if(!individual){
											bean.setIndividual(0);
											bean.setMontoIndiv(0);
										}
									//La oficina no presenta abonos para organizaciones									
										if(!organizacion){
											bean.setOrganizacion(0);
											bean.setMontoOrg(0);
										}

									bean.setTotal(bean.getMontoIndiv() + bean.getMontoOrg());
									
									double porcentaje = (((double) bean.getTotal()) / ((double) total)) * 100;
									porcentaje = Math.round(porcentaje * 100);
									porcentaje = porcentaje / 100;
									bean.setPorcentaje(porcentaje);
									
									
									//Crear la linea
									StringBuffer stbx = new StringBuffer();
									
									stbx.append(bean.getOficina()).append(",");
									stbx.append(bean.getOrganizacion()).append(",");
									stbx.append(bean.getMontoOrg()).append(",");
									stbx.append(bean.getIndividual()).append(",");
									stbx.append(bean.getMontoIndiv()).append(",");
									stbx.append(bean.getTotal()).append(",");
									stbx.append(bean.getPorcentaje());									
									out_cliente.println(stbx.toString());
									
								}
						//Creando el Bean para almacenar los datos, se anade datos generales
							bean = new IngresoBean();
							bean.setRegPubId(regPubId);	//RegPubId
							bean.setOficRegId(oficRegId);	//OficRegId
							bean.setOficina(rs.getString(3));	//Nombre Oficina
							individual = false;
							organizacion = false;
						}
					
					//Si la oficina presenta Abonos para personas individuales
						if(tipoUser.equals("I")){
							bean.setIndividual(rs.getLong(5));
							bean.setMontoIndiv((long) rs.getDouble(6));
							aux = regPubId + oficRegId;
							
							//Inicio:mgarate:05/02/2008
							contadorIndiv += bean.getIndividual();
							//Fin:mgarate
							montoIndiv += bean.getMontoIndiv();
							
							individual = true;	//Activa flag indicando que existe abonos para personas individuales
						}
	
					//Si la oficina presenta Abonos para organizaciones	
						if(tipoUser.equals("O")){
							bean.setOrganizacion(rs.getLong(5));
							bean.setMontoOrg((long) rs.getDouble(6));
							aux = regPubId + oficRegId;
							//Inicio:mgarate:05/02/2008
							contadorOrg+=bean.getOrganizacion();
							//Fin:mgarate
							montoOrg += bean.getMontoOrg();
							organizacion = true;	//Activa flag indicando que existe abonos para organizaciones
						}
				}
			if(cabecera)
				throw new ValidacionException("No existen datos para exportar");

			//La oficina no presenta abonos para personas individuales
				if(!individual){
					bean.setIndividual(0);
					bean.setMontoIndiv(0);
			}
			//La oficina no presenta abonos para organizaciones									
				if(!organizacion){
					bean.setOrganizacion(0);
					bean.setMontoOrg(0);
				}

			bean.setTotal(bean.getMontoIndiv() + bean.getMontoOrg());
									
			double porcentaje = (((double) bean.getTotal()) / ((double) total)) * 100;
			porcentaje = Math.round(porcentaje * 100);
			porcentaje = porcentaje / 100;
			bean.setPorcentaje(porcentaje);

		//Crear la linea
			StringBuffer stbx = new StringBuffer();
			
			stbx.append(bean.getOficina()).append(",");
			stbx.append(bean.getOrganizacion()).append(",");
			stbx.append(bean.getMontoOrg()).append(",");
			stbx.append(bean.getIndividual()).append(",");
			stbx.append(bean.getMontoIndiv()).append(",");
			stbx.append(bean.getTotal()).append(",");
			stbx.append(bean.getPorcentaje());		
			out_cliente.println(stbx.toString());
			
			stb = new StringBuffer();
			
			stb.append("TOTALES").append(", ");
			stb.append(Long.toString(contadorOrg)).append(", ");
			stb.append(Long.toString(montoOrg)).append(", ");
			stb.append(Long.toString(contadorIndiv)).append(", ");
			stb.append(Long.toString(montoIndiv)).append(", ");
			stb.append(Long.toString(total));

			out_cliente.println(stb.toString());
			/*ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "S");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeroOrg", Long.toString(contadorOrg));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("totalmontoOrg", Long.toString(montoOrg));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeroIndiv", Long.toString(contadorIndiv));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("totalmontoIndiv", Long.toString(montoIndiv));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("totalgeneral", Long.toString(total));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista);
			
			response.setStyle(findForward("muestra", request));
			*/
			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);
			conn.commit();
		} 
		catch (ValidacionException ve) 
		{		
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",ve.getMensaje());			
		}		
		
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}


	protected ControllerResponse runVerAbonoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
			
	HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		
	try{
		init(request);
		validarSesion(request);
			
		conn = pool.getConnection();
		conn.setAutoCommit(false);
		DBConnection dconn = new DBConnection(conn);
		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

		//Capturando Parámetros
		StringTokenizer stk = null;
		String oficRegId = null;
		String regPubId = null;
				
		try{
			stk = new StringTokenizer(request.getParameter("oficina"), "|");
			regPubId = stk.nextToken();
			oficRegId = stk.nextToken();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("nom_oficina", Generales.getNombreOficina(regPubId, oficRegId, dconn));
		}
		catch(Throwable e1)
		{
			throw new ValidacionException("No se pudo obtener oficina registral");
		}
		
		boolean esPagoEnLinea = false;
		if(regPubId.equals("00") && oficRegId.equals("00"))
				esPagoEnLinea = true;
		
			String entidad = request.getParameter("entidad");
			if(entidad == null || entidad.trim().length() <= 0)
				throw new ValidacionException("No se pudo obtener el tipo de búsqueda, ya sea por Organización o por Individuales");

			//Capturo variables de Fecha
				String anoinicio = request.getParameter("anoinicio");
				String mesinicio = request.getParameter("mesinicio");
				String diainicio = request.getParameter("diainicio");
				String anofin = request.getParameter("anofin");
				String mesfin = request.getParameter("mesfin");
				String diafin = request.getParameter("diafin");
				
			req.setAttribute("selectedIDay",diainicio);
			req.setAttribute("selectedIMonth",mesinicio);
			req.setAttribute("selectedIYear",anoinicio);
			req.setAttribute("selectedFDay",diafin);
			req.setAttribute("selectedFMonth",mesfin);
			req.setAttribute("selectedFYear",anofin);									
				
				int _di = Integer.parseInt(diainicio);
				int _mi = Integer.parseInt(mesinicio);
				int _ai = Integer.parseInt(anoinicio);
				int _df = Integer.parseInt(diafin);
				int _mf = Integer.parseInt(mesfin);
				int _af = Integer.parseInt(anofin);


				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", entidad);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("oficina", request.getParameter("oficina"));
				
			//Construyendo la fecha de inicio y fecha de fin para el Oracle
				String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
				String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);
				
			//Retornar Fecha para mostrarse
				String fechitaI = _di + "/" + _mi + "/" + _ai;
				String fechitaF = _df + "/" + _mf + "/" + _af;
				
				req.setAttribute("fecini", fechitaI);
				req.setAttribute("fecfin", fechitaF);
			
			java.sql.Statement stmt = null;
			java.sql.ResultSet rs = null;
			
			StringBuffer query = new StringBuffer();
			String sql = null;
			//Query para obtener el MONTO TOTAL
				long total;

				query.append("SELECT Sum(a.MONTO) AS MONTO FROM ABONO a");
				if(esPagoEnLinea) query.append(", PAGO_EN_LINEA p");
				query.append(" WHERE a.estado = '1' AND a.TS_MODI BETWEEN ");
				query.append(fechaInicio).append(" AND ").append(fechaFin);
				if(esPagoEnLinea) query.append(" AND p.MOVIMIENTO_ID = a.MOVIMIENTO_ID AND a.TIPO_ABONO in ('L','V')");
				query.append(" AND a.reg_pub_id='").append(regPubId).append("' AND a.OFIC_REG_ID='");
				query.append(oficRegId).append("' AND a.TIPO_USR='").append(entidad).append("'");
				sql = query.toString();

				stmt = conn.createStatement();
				if (isTrace(this)) System.out.println("__verSQL " + sql);
				rs = stmt.executeQuery(sql);
			
				if(rs.next())
					total = (long) rs.getDouble(1);
				else
					throw new ReporteNoRegistroException("verAbono");
			
			trace("Monto Total en abonos en la fecha indicada: " + total, request);

			query = new StringBuffer();
			//Query para el detalle
				if(entidad.equals("I")){
					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "I");
					query.append("SELECT  b.usr_id, c.APE_PAT, c.APE_MAT, c.NOMBRES, a.MONTO");
					if(esPagoEnLinea) query.append(", p.PAGO_EN_LINEA_ID");
					query.append(" FROM ABONO a, CUENTA b, PE_NATU c");
					if(esPagoEnLinea) query.append(", PAGO_EN_LINEA p");
					query.append(" WHERE a.reg_pub_id='").append(regPubId).append("' AND a.OFIC_REG_ID='");
					query.append(oficRegId).append("' AND a.persona_id = c.persona_id AND b.PE_NATU_ID=c.PE_NATU_ID");
					if(esPagoEnLinea) query.append(" AND p.MOVIMIENTO_ID = a.MOVIMIENTO_ID AND a.TIPO_ABONO in ('L','V')");
					query.append(" AND a.tipo_usr='I' AND a.estado='1'");
					query.append("AND a.TS_MODI BETWEEN ").append(fechaInicio).append(" AND ").append(fechaFin);
					/**
					 * Inicio:mgarate:27/11/2007:Performance Reporte
					 */
					//if(esPagoEnLinea) query.append(" ORDER BY p.PAGO_EN_LINEA_ID");
					if(esPagoEnLinea) query.append(" ORDER BY p.PAGO_EN_LINEA_ID, A.ABONO_ID");
					/**
					 * Fin:mgarate
					 */
				}
				if(entidad.equals("O")){
					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "O");
					query.append("SELECT  b.usr_id, d.raz_soc, a.MONTO");
					if(esPagoEnLinea) query.append(", p.PAGO_EN_LINEA_ID");
					query.append(" FROM ABONO a, CUENTA b, PE_NATU c, PE_JURI d");
					if(esPagoEnLinea) query.append(", PAGO_EN_LINEA p");
					query.append(" WHERE a.reg_pub_id='").append(regPubId).append("' AND a.OFIC_REG_ID='");
					query.append(oficRegId).append("' AND a.persona_id = d.persona_id AND b.PE_NATU_ID=c.PE_NATU_ID AND d.pe_juri_id = c.pe_juri_id");
					if(esPagoEnLinea) query.append(" AND p.MOVIMIENTO_ID = a.MOVIMIENTO_ID AND a.TIPO_ABONO in ('L','V')");
					query.append(" AND a.tipo_usr='O' AND a.estado='1' AND substr(b.tipo_usr,3,1) = '1'");
					query.append("AND a.TS_MODI BETWEEN ").append(fechaInicio).append(" AND ").append(fechaFin);
					/**
					 * Inicio:mgarate:27/11/2007:Performance Reporte
					 */
					//if(esPagoEnLinea) query.append(" ORDER BY p.PAGO_EN_LINEA_ID");
					if(esPagoEnLinea) query.append(" ORDER BY p.PAGO_EN_LINEA_ID, A.ABONO_ID");
					/**
					 * Fin:mgarate
					 */
				}
	
				stmt = conn.createStatement();
				sql = query.toString();
				if (isTrace(this)) System.out.println("___versql: " + sql);
				rs = stmt.executeQuery(sql);
				
			if (isTrace(this)) trace("Datos del reporte de Ingresos...", request);

			VerAbonoBean bean = null;
			java.util.List lista = new java.util.ArrayList();
			
			boolean encontro = false;

			//Recorriendo el resultado del Query y usando logica para ponerlo en el Bean
				if(entidad.equals("I"))
					while(rs.next()){
						encontro = true;
						String aux1 = rs.getString(2);
						String aux2 = rs.getString(3);
						String aux3 = rs.getString(4);

						aux1 = aux1==null?"":aux1;
						aux2 = aux2==null?"":aux2;
						aux3 = aux3==null?"":aux3;

						bean = new VerAbonoBean();
						bean.setUserId(rs.getString(1)==null?"":rs.getString(1));
						bean.setEntidad(aux1 + " " + aux2 + " " + aux3);
						bean.setMonto((long) rs.getDouble(5));
						if(esPagoEnLinea) 
							bean.setPagoLineaId(rs.getString(6));
						else
							bean.setPagoLineaId("");
						lista.add(bean);
					}
							

				if(entidad.equals("O"))
					while(rs.next()){
							encontro = true;
							bean = new VerAbonoBean();
							bean.setUserId(rs.getString(1)==null?"":rs.getString(1));
							bean.setEntidad(rs.getString(2)==null?"":rs.getString(2));
							bean.setMonto((long) rs.getDouble(3));
							if(esPagoEnLinea) 
								bean.setPagoLineaId(rs.getString(4));
							else
								bean.setPagoLineaId("");
							lista.add(bean);
					}
			
			if(!encontro)
				throw new ReporteNoRegistroException("verAbono");

			ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "S");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("totalgeneral", Long.toString(total));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista);
			
			response.setStyle("verAbono");
			conn.commit();
		}
		catch (ValidacionException ve) 
		{		
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",ve.getMensaje());			
		}		
		 catch (ReporteNoRegistroException re) {
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "N");
			rollback(conn, request);
			response.setStyle(re.getForward());
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runExportarAbonoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
	
			//Capturando Parámetros
				StringTokenizer stk = null;
				String oficRegId = null;
				String regPubId = null;
				
				try{
					stk = new StringTokenizer(request.getParameter("oficina"), "|");
					regPubId = stk.nextToken();
					oficRegId = stk.nextToken();
					ExpressoHttpSessionBean.getRequest(request).setAttribute("nom_oficina", Generales.getNombreOficina(regPubId, oficRegId, dconn));
				}
				catch(Throwable e1)
				{
					throw new ValidacionException("No se pudo obtener oficina registral");
				}
		
			String entidad = request.getParameter("entidad");
			if(entidad == null || entidad.trim().length() <= 0)
				throw new ValidacionException("No se pudo obtener el tipo de búsqueda, ya sea por Organizacion o por Individuales");

			//Capturo variables de Fecha
				String anoinicio = request.getParameter("anoinicio");
				String mesinicio = request.getParameter("mesinicio");
				String diainicio = request.getParameter("diainicio");
				String anofin = request.getParameter("anofin");
				String mesfin = request.getParameter("mesfin");
				String diafin = request.getParameter("diafin");
				
				int _di = Integer.parseInt(diainicio);
				int _mi = Integer.parseInt(mesinicio);
				int _ai = Integer.parseInt(anoinicio);
				int _df = Integer.parseInt(diafin);
				int _mf = Integer.parseInt(mesfin);
				int _af = Integer.parseInt(anofin);
				
			req.setAttribute("selectedIDay",diainicio);
			req.setAttribute("selectedIMonth",mesinicio);
			req.setAttribute("selectedIYear",anoinicio);
			req.setAttribute("selectedFDay",diafin);
			req.setAttribute("selectedFMonth",mesfin);
			req.setAttribute("selectedFYear",anofin);									
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", entidad);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("oficina", request.getParameter("oficina"));
			//Construyendo la fecha de inicio y fecha de fin para el Oracle
				String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
				String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);
				
			java.sql.Statement stmt = null;
			java.sql.ResultSet rs = null;
			
			StringBuffer query = new StringBuffer();
			String sql = null;
			//Query para obtener el MONTO TOTAL
				long total;

				query.append("SELECT Sum(a.MONTO) AS MONTO FROM ABONO a");
				query.append(" WHERE a.estado = '1' AND a.TS_MODI BETWEEN ");
				query.append(fechaInicio).append(" AND ").append(fechaFin);
				query.append(" AND a.reg_pub_id='").append(regPubId).append("' AND a.OFIC_REG_ID='");
				query.append(oficRegId).append("' AND a.TIPO_USR='").append(entidad).append("'");
				sql = query.toString();

				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			
				if(rs.next())
					total = (long) rs.getDouble(1);
				else
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No existen registros", "errorReportes");
			
			if (isTrace(this)) trace("Monto Total en abonos en la fecha indicada: " + total, request);

			query = new StringBuffer();
			//Query para el detalle
				if(entidad.equals("I")){
					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "I");
					query.append("SELECT  b.usr_id, c.APE_PAT, c.APE_MAT, c.NOMBRES, a.MONTO");
					query.append(" FROM ABONO a, CUENTA b, PE_NATU c").append(" WHERE a.reg_pub_id='").append(regPubId).append("' AND a.OFIC_REG_ID='");
					query.append(oficRegId).append("' AND a.persona_id = c.persona_id AND b.PE_NATU_ID=c.PE_NATU_ID");
					query.append(" AND a.tipo_usr='I' AND a.estado='1'");
					query.append("AND a.TS_MODI BETWEEN ").append(fechaInicio).append(" AND ").append(fechaFin);
				}
	
				if(entidad.equals("O")){
					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "O");
					query.append("SELECT  b.usr_id, d.raz_soc, a.MONTO");
					query.append(" FROM ABONO a, CUENTA b, PE_NATU c, PE_JURI d").append(" WHERE a.reg_pub_id='").append(regPubId).append("' AND a.OFIC_REG_ID='");
					query.append(oficRegId).append("' AND a.persona_id = d.persona_id AND b.PE_NATU_ID=c.PE_NATU_ID AND d.pe_juri_id = c.pe_juri_id");
					query.append(" AND a.tipo_usr='O' AND a.estado='1' AND substr(b.tipo_usr,3,1) = '1'");
					query.append("AND a.TS_MODI BETWEEN ").append(fechaInicio).append(" AND ").append(fechaFin);
				}

				stmt = conn.createStatement();
				sql = query.toString();
				if (isTrace(this)) System.out.println("versql: " + sql);
				rs = stmt.executeQuery(sql);
				
			if (isTrace(this)) trace("Datos del reporte de Ingresos...", request);

	 			String fname = "ingresoAbono.csv";
				HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
				PrintWriter out_cliente = null;

				VerAbonoBean bean = bean = new VerAbonoBean();
				java.util.List lista = new java.util.ArrayList();
			
				boolean encontro = false;
				boolean cabecera = true;
				StringBuffer stb = null;
			//Recorriendo el resultado del Query y usando logica para ponerlo en el Bean
				while(rs.next()){
					encontro = true;
	
					//Poniendo la Cabecera			
						if(cabecera){
							res.setContentType("archivo/xxx");
							res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
	
							stb = new StringBuffer();
							stb.append("Usuario").append(",");
							
							if(entidad.equals("I"))
								stb.append("Apellidos y Nombres").append(",");
							
							if(entidad.equals("O"))
								stb.append("Razon Social").append(",");
							
							stb.append("Monto").append(",");
							
							out_cliente = res.getWriter();
							out_cliente.println(stb.toString());
							cabecera = false;
						}
					
					bean.setUserId(rs.getString(1)==null?"":rs.getString(1));
							
					if(entidad.equals("I")){
						String aux1 = rs.getString(2);
						String aux2 = rs.getString(3);
						String aux3 = rs.getString(4);
						
						aux1 = aux1==null?"":aux1;
						aux2 = aux2==null?"":aux2;
						aux3 = aux3==null?"":aux3;
						
						bean.setEntidad(aux1 + " " + aux2 + " " + aux3);
						bean.setMonto((long) rs.getDouble(5));
					}
					if(entidad.equals("O")){
						bean.setEntidad(rs.getString(2)==null?"":rs.getString(2));
						bean.setMonto((long) rs.getDouble(3));
					}
				
					stb = new StringBuffer();
					stb.append(bean.getUserId()).append(",");
					stb.append(bean.getEntidad()).append(",");
					stb.append(bean.getMonto());
					
					out_cliente.println(stb.toString());
				}
							
			if(!encontro)
				throw new ValidacionException("No existen registros");

			stb = new StringBuffer();
			stb.append("TOTAL").append(",");
			stb.append(Long.toString(total));

			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);
			conn.commit();
		} 
		catch (ValidacionException ve) 
		{		
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",ve.getMensaje());			
		}		
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}	
}