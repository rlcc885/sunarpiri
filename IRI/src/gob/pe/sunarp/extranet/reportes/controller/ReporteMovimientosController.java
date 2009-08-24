package gob.pe.sunarp.extranet.reportes.controller;

//paquetes del sistema
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.jcorporate.expresso.core.controller.*;
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
import gob.pe.sunarp.extranet.common.*;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;

import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletResponse;

public class ReporteMovimientosController extends ControllerExtension implements Constantes
{
	public ReporteMovimientosController() 
	{
		super();
		addState(new State("verFormulario", "muestra formulario de búsqueda de transacciones"));
		addState(new State("verReporte", "muestra el resultado de la búsqueda de movimientos"));
		addState(new State("verReporteDetalle", "muestra el resultado de la búsqueda de movimientos para una organización"));
		addState(new State("exportarReporte", "exporta el resultado de la búsqueda de movimientos"));		
	}
	
	public ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException
		{
		try {
			init(request);
			validarSesion(request);			
			
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean userLogged = ExpressoHttpSessionBean.getUsuarioBean(request);

			req.setAttribute("perfilId",""+userLogged.getPerfilId());
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

			req.setAttribute("usuario_logeado",userLogged.getUserId());
			
			response.setStyle("movimientos");

		} 
		catch (CustomException ce) 
		{
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		} 
		catch (Throwable ex) 
		{
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} 
		return response;	
	}

	public ControllerResponse runVerReporteState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
		{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
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

			String radioFiltro = request.getParameter("radio");
			String razonSocial = request.getParameter("razon_social");
			String ruc = request.getParameter("ruc");
			String userId= request.getParameter("userId");

			String str_Criterio_Busqueda="";
			String str_Variable_Busqueda="";
			StringBuffer query = new StringBuffer();

			//hp
			boolean orgUnica = true;
			
			DboPeJuri personaJuridica = new DboPeJuri(dconn);
			//oa
			//DboLineaPrepago lineaPrepago = new DboLineaPrepago(dconn);
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String peJuriId = null;
			String personaId = null;
			String jurisId = null;
			String codOrg = null;
			String lpId = null;
			String cuId = null;			
			
			int int_Total_Abono=0;
			double dbl_Total_Consumo=0;
			double dbl_Total_Abono=0;			
			double dbl_Saldo_Inicial=0;			
			double dbl_Saldo_Final=0;			
			
			MovimientosBean temp_MovimientosBean =null;	
			MovimientosTotalBean temp_MovimientosTotalBean =null;
			java.util.List listMovimientos = new java.util.ArrayList();
			java.util.List listMovimientosTotal = new java.util.ArrayList();									
			

					//sacar de la tabla PR_JURI  JURIS_ID
					personaJuridica.setField(DboPeJuri.CAMPO_PE_JURI_ID, userLogged.getCodOrg());
					personaJuridica.retrieve();
					jurisId = personaJuridica.getField(DboPeJuri.CAMPO_JURIS_ID);	
					peJuriId=personaJuridica.getField(DboPeJuri.CAMPO_PE_JURI_ID);
					
					if (userLogged.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO)
						jurisId =userLogged.getJurisdiccionId();
						
					// oa
					
					if ((userLogged.getPerfilId() == PERFIL_ADMIN_ORG_EXT)||(userLogged.getPerfilId() == PERFIL_AFILIADO_EXTERNO))
					{
						//recuperar el pe_juri_id de la organizacion
						peJuriId = userLogged.getCodOrg();
						query.setLength(0);
						query.append("select persona_id ");
						query.append("from pe_juri  ");
						query.append("where pe_juri_id = ? ");
						String sql = query.toString();
						if (isTrace(this)) System.out.println("___verqueryoa_ " + sql);
						if (isTrace(this)) System.out.println(".." + userLogged.getPersonaId()+ " " + personaId);
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, peJuriId);
						rs = pstmt.executeQuery();
						if(rs.next())
							personaId = rs.getString(1);
						else
							throw new ValidacionException(" No existe un administrador válido asociado a esta organización");
						if (isTrace(this)) System.out.println(".." + userLogged.getPersonaId()+ " " + personaId);
						// = ;
						//recuperar el linea_pre_pago_id de la organizacion
						query.setLength(0);
						query.append(" SELECT LP.LINEA_PREPAGO_ID");
						query.append(" FROM LINEA_PREPAGO LP");
						query.append(" WHERE LP.CUENTA_ID is NULL AND LP.PE_JURI_ID = ?");
						sql = query.toString();
						if (isTrace(this)) System.out.println("___verqueryoa_ " + sql);
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, peJuriId);
						rs = pstmt.executeQuery();
						if(rs.next())
							lpId = rs.getString(1);
						else
							throw new ValidacionException(" No existe una línea prepago asociada a esta organización");
					} else 
					{
					if(radioFiltro.equals("1")){ //Tipo de Usuario de Organizacion

						if(!razonSocial.equals("")){
				    			str_Criterio_Busqueda="Razon Social";
								str_Variable_Busqueda=razonSocial;

								query.setLength(0);
								
								query.append(" SELECT PJ.PE_JURI_ID, PJ.PERSONA_ID, LP. LINEA_PREPAGO_ID, PJ.RAZ_SOC, PE.NUM_DOC_IDEN");
								query.append(" FROM PE_JURI PJ, LINEA_PREPAGO LP, PERSONA PE ");
								query.append(" WHERE PJ.PERSONA_ID=PE.PERSONA_ID ");
								query.append(" AND PJ.RAZ_SOC LIKE ? "); 						

								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){
									query.append(" AND ");
									query.append(" PJ.JURIS_ID = ? ");
								}	
								query.append("AND LP.PE_JURI_ID=PJ.PE_JURI_ID AND LP.CUENTA_ID IS NULL ");
								//hp
								String sql = query.toString();
								if (isTrace(this)) System.out.println("___verquery_ " + sql);
								pstmt = conn.prepareStatement(sql);
								
								//pstmt.setString(1, "%" +razonSocial.toUpperCase() + "%");
								pstmt.setString(1, razonSocial.toUpperCase() + "%");
								
								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){								
									pstmt.setString(2, jurisId);
								}	
								rs = pstmt.executeQuery();
								if(rs.next())
								{
									java.util.List listOrganizaciones = new java.util.ArrayList();
									do{
										OrganizacionBean orgBean = new OrganizacionBean();
										orgBean.setPeJuriId(rs.getString(1).trim());
										orgBean.setPersonaId(rs.getString(2).trim());
										orgBean.setLpId(rs.getString(3).trim());
										orgBean.setRazonSocial(rs.getString(4).trim());
										orgBean.setNroDocumento(rs.getString(5).trim());
										listOrganizaciones.add(orgBean);
									}while(rs.next());
									if(listOrganizaciones.size()>1){
										orgUnica = false;
										req.setAttribute("listOrganizaciones",listOrganizaciones);
									} else {
										OrganizacionBean temp_orgBean = (OrganizacionBean) listOrganizaciones.get(0);
										peJuriId= temp_orgBean.getPeJuriId();
										personaId = temp_orgBean.getPersonaId();
										lpId = temp_orgBean.getLpId();
									}
									/*
									peJuriId = rs.getString(1).trim();
									personaId = rs.getString(2).trim();
									lpId = rs.getString(3).trim();
									*/
								}else
								{
									throw new ValidacionException(" No existen organizaciones registradas de razón social" + razonSocial ,"");
								}
						
						}// si no recupera nada mandar mensaje de error
						//para el ruc
						if(!ruc.equals("")){
								
				    			str_Criterio_Busqueda="RUC";
								str_Variable_Busqueda=ruc;

								query.setLength(0);					
								query.append(" SELECT PJ.PE_JURI_ID, PJ.PERSONA_ID,LP.LINEA_PREPAGO_ID, PJ.RAZ_SOC, PE.NUM_DOC_IDEN ");
								query.append(" FROM PE_JURI PJ, PERSONA PE, LINEA_PREPAGO LP ");
								query.append(" WHERE PJ.PERSONA_ID=PE.PERSONA_ID ");
								query.append(" AND PE.TIPO_DOC_ID='05' ");
								query.append(" AND NUM_DOC_IDEN = ?");
								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){
									query.append(" AND ");
									query.append(" PJ.JURIS_ID = ? ");
								}	
								query.append("AND LP.PE_JURI_ID=PJ.PE_JURI_ID AND LP.CUENTA_ID IS NULL ");
								String sql=query.toString();
								if (isTrace(this)) System.out.println(" ___verquery_ " + sql);
								pstmt = conn.prepareStatement(sql);
								pstmt.setString(1, ruc);
								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){								
									pstmt.setString(2, jurisId);
								}							
								rs = pstmt.executeQuery();
								//puede recuperarse mas de una organizacion
								if(rs.next())
								{
									java.util.List listOrganizaciones = new java.util.ArrayList();
									do{
										OrganizacionBean orgBean = new OrganizacionBean();
										orgBean.setPeJuriId(rs.getString(1).trim());
										orgBean.setPersonaId(rs.getString(2).trim());
										orgBean.setLpId(rs.getString(3).trim());
										orgBean.setRazonSocial(rs.getString(4).trim());
										orgBean.setNroDocumento(rs.getString(5).trim());
										listOrganizaciones.add(orgBean);
									}while(rs.next());
									if(listOrganizaciones.size()>1){
										orgUnica = false;
										req.setAttribute("listOrganizaciones",listOrganizaciones);
									} else {
										peJuriId = rs.getString(1).trim();
										personaId = rs.getString(2).trim();
										lpId = rs.getString(3).trim();
									}
									/*
									peJuriId = rs.getString(1).trim();
									personaId = rs.getString(2).trim();
									lpId = rs.getString(3).trim();									
									*/
								}else
								{
									throw new ValidacionException(" No existen organizaciones registradas con RUC" + ruc ,"");								
								}
						  }
						  
					}
					//SELECT PERSONA_ID FROM PE_JURI WHERE PE_JURI=<<COD ORG SESION>>
					if(radioFiltro.equals("2")){ //Tipo de Usuario INDIVIDUAL
						str_Criterio_Busqueda="userId";
						str_Variable_Busqueda = userId;

						query.setLength(0);
						query.append(" SELECT CJ.JURIS_ID,CJ.PERSONA_ID, CU.CUENTA_ID ");
						query.append(" FROM CUENTA CU, CUENTA_JURIS CJ ");
						query.append(" WHERE "); 
						query.append(" CU.CUENTA_ID=CJ.CUENTA_ID "); 
						query.append(" AND CU.USR_ID= ? "); 
						
						if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION || userLogged.getPerfilId() == PERFIL_ADMIN_ORG_EXT ||  userLogged.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO){
							query.append(" AND ");
							query.append(" CJ.JURIS_ID = ? ");									
						}	
						
						if (isTrace(this)) System.out.println("__verqueryb_ " + query.toString());
						
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, userId.toUpperCase() );
						if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ||  userLogged.getPerfilId() == PERFIL_ADMIN_ORG_EXT ||  userLogged.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO){
							pstmt.setString(2, jurisId);
						}	
						rs = pstmt.executeQuery();
						if(rs.next())
						{
							peJuriId = rs.getString(1).trim();
							personaId = rs.getString(2).trim();
							cuId=rs.getString(3).trim();
						}else{
							throw new ValidacionException(" No existe usuario registrado " + userId ,"");
						}
					}				
						  
					}
					temp_MovimientosTotalBean = new MovimientosTotalBean();
					temp_MovimientosTotalBean.setLineaPrepago(lpId);
					temp_MovimientosTotalBean.setLineaPrepago(cuId);
					
					//Ejecucion del query

				//hp
				if(orgUnica){
					
					query.setLength(0);		

					if(radioFiltro.equals("1")){
							
						query.append(" SELECT T.PERSONA_ID, T.ABONO_ID, to_char(T.FEC_HOR,'dd/mm/yyyy hh24:mi:ss'), T.TOTALMONTO ");
						query.append(" , T.NOMBRE,DECODE(trim(T.TPO_PAG_VENT),'E' ");
						query.append(" ,'EFECTIVO','C','EFECTIVO',T.TPO_PAG_VENT) ");
						query.append(" FROM (SELECT T2.PERSONA_ID, T1.ABONO_ID, "); 
						query.append(" T2.FEC_HOR ");
						query.append(" ,  T1.TOTALMONTO, T2.NOMBRE,T2.TPO_PAG_VENT ");
			      		query.append(" FROM ");
						query.append(" (SELECT V.ABONO_ID, SUM(V.MONTO) TOTALMONTO ");
		            	query.append(" FROM   VW_MOVIMIENTO V ");
			          	query.append(" WHERE V.PERSONA_ID = ? ");
	   			       	query.append(" GROUP BY V.ABONO_ID) T1, ");
						//query.append(" GROUP BY CUBE(V.ABONO_ID)) T1, ");	   			       	
						query.append(" (SELECT V.PERSONA_ID, V.ABONO_ID, V.FEC_HOR ");
						query.append(" , V.TPO_PAG_VENT, V.MONTO, V.NOMBRE ");
		            	query.append(" FROM   ");
						query.append(" VW_MOVIMIENTO V ");
		            	query.append(" WHERE V.PERSONA_ID = ?) T2 ");
			    		query.append(" WHERE T1.ABONO_ID = T2.ABONO_ID(+))T ");
		                query.append(" WHERE T.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss')");  			    		
						//query.append(" WHERE T.FEC_HOR BETWEEN <<FECHA INICIO>> AND <<FECHA FIN>> ");
						
						if (isTrace(this)) System.out.println("___verquerya_ " + query.toString());
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, Integer.parseInt(personaId));
						pstmt.setInt(2, Integer.parseInt(personaId));						
						pstmt.setString(3, date_Inicio_Ora);
						pstmt.setString(4, date_Fin_Ora);
					}						


					if(radioFiltro.equals("2")){
							
						query.append(" SELECT T.PERSONA_ID, T.ABONO_ID, to_char(T.FEC_HOR,'dd/mm/yyyy hh24:mi:ss'), T.TOTALMONTO ");
						query.append(" , T.NOMBRE,DECODE(trim(T.TPO_PAG_VENT),'E' ");
						query.append(" ,'EFECTIVO','C','EFECTIVO',T.TPO_PAG_VENT) ");
						query.append(" FROM (SELECT T2.PERSONA_ID, T1.ABONO_ID, "); 
						query.append(" T2.FEC_HOR ");
						query.append(" ,  T1.TOTALMONTO, T2.NOMBRE,T2.TPO_PAG_VENT ");
			      		query.append(" FROM ");
						query.append(" (SELECT V.ABONO_ID, SUM(V.MONTO) TOTALMONTO ");
		            	query.append(" FROM   VW_MOVIMIENTO V ");
			          	query.append(" WHERE V.PERSONA_ID = ? ");
			          	query.append(" GROUP BY V.ABONO_ID) T1, ");
	   			       	//query.append(" GROUP BY CUBE(V.ABONO_ID)) T1, ");
						query.append(" (SELECT V.PERSONA_ID, V.ABONO_ID, V.FEC_HOR ");
						query.append(" , V.TPO_PAG_VENT, V.MONTO, V.NOMBRE ");
		            	query.append(" FROM   ");
						query.append(" VW_MOVIMIENTO V ");
		            	query.append(" WHERE V.PERSONA_ID = ?) T2 ");
			    		query.append(" WHERE T1.ABONO_ID = T2.ABONO_ID(+))T ");
		                query.append(" WHERE T.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss')");  			    		
						//query.append(" WHERE T.FEC_HOR BETWEEN <<FECHA INICIO>> AND <<FECHA FIN>> ");
						
						if (isTrace(this)) System.out.println("__verqueryq_ "+query.toString());
						
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, Integer.parseInt(personaId));
						pstmt.setInt(2, Integer.parseInt(personaId));						
						pstmt.setString(3, date_Inicio_Ora);
						pstmt.setString(4, date_Fin_Ora);
					}						
						

					rs = pstmt.executeQuery();
									
					while(rs.next())
					{
						temp_MovimientosBean = new MovimientosBean();				
						temp_MovimientosBean.setAbonoId(rs.getString(2));						
						temp_MovimientosBean.setFecha(rs.getString(3).substring(0,10));						
						temp_MovimientosBean.setHora(rs.getString(3).substring(10,19));						
						temp_MovimientosBean.setMonto(rs.getString(4));						
						temp_MovimientosBean.setNombre(rs.getString(5));						
						temp_MovimientosBean.setTipoPagoVentanilla(rs.getString(6));																																				
						listMovimientos.add(temp_MovimientosBean);
						//int_Total_Abono=int_Total_Abono+Integer.parseInt(rs.getString(4));
						dbl_Total_Abono=dbl_Total_Abono+Double.parseDouble(rs.getString(4));
					}	
						
					query.setLength(0);		

					if(radioFiltro.equals("1")){
						query.append(" SELECT NVL(SUM(C.MONTO),0) ");
						query.append(" FROM PE_JURI PJ, LINEA_PREPAGO L, MOVIMIENTO M, CONSUMO C, TRANSACCION T, OFIC_REGISTRAL O ");
						query.append(" WHERE L.PE_JURI_ID = PJ.PE_JURI_ID AND L.CUENTA_ID IS NOT NULL AND M.LINEA_PREPAGO_ID = L.LINEA_PREPAGO_ID");
		            	query.append(" AND M.MOVIMIENTO_ID = C.MOVIMIENTO_ID AND C.TRANS_ID = T.TRANS_ID AND T.OFIC_REG_ID = O.OFIC_REG_ID AND T.REG_PUB_ID  = O.REG_PUB_ID");
		            	query.append(" AND PJ.PE_JURI_ID = ?");								            	
     			       	query.append(" AND M.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
     			       	
						if (isTrace(this)) System.out.println(" ___verquery._ "+ query.toString());
						
						pstmt = conn.prepareStatement(query.toString());
						//pstmt.setInt(1, Integer.parseInt(cuId));
						pstmt.setString(1, peJuriId);
						pstmt.setString(2, date_Inicio_Ora);
						pstmt.setString(3, date_Fin_Ora);
						
						rs = null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Total_Consumo=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setConsumos(String.valueOf(dbl_Total_Consumo));
							//temp_MovimientosTotalBean.setConsumos(rs.getString(1));
						}	
						temp_MovimientosTotalBean.setAbonos(String.valueOf(dbl_Total_Abono));
						//temp_MovimientosTotalBean.setAbonos(int_Total_Abono+"");
					}


					if(radioFiltro.equals("2")){
						query.append(" SELECT NVL(SUM(C.MONTO),0)  ");
						query.append(" FROM CONSUMO C,MOVIMIENTO M, OFIC_REGISTRAL O , LINEA_PREPAGO L, TRANSACCION T ");
						query.append(" WHERE M.MOVIMIENTO_ID=C.MOVIMIENTO_ID AND C.TRANS_ID=T.TRANS_ID  ");
						query.append(" AND L.LINEA_PREPAGO_ID=M.LINEA_PREPAGO_ID AND L.LINEA_PREPAGO_ID IN  "); 
						query.append(" (SELECT LP.LINEA_PREPAGO_ID  ");
			      		query.append(" FROM LINEA_PREPAGO LP ");
						query.append(" WHERE LP.CUENTA_ID=? )  ");
		            	query.append(" AND O.OFIC_REG_ID=T.OFIC_REG_ID  ");
     			       	query.append(" AND O.REG_PUB_ID=T.REG_PUB_ID  ");
     			       	query.append(" AND M.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
     			       	
     			       	if (isTrace(this)) System.out.println(" ___verquery.._ "+ query.toString());
     			       	
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, Integer.parseInt(cuId));
						pstmt.setString(2, date_Inicio_Ora);
						pstmt.setString(3, date_Fin_Ora);
						
						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Total_Consumo=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setConsumos(String.valueOf(dbl_Total_Consumo));							
							//temp_MovimientosTotalBean.setConsumos(rs.getString(1));
						}	
						temp_MovimientosTotalBean.setAbonos(String.valueOf(dbl_Total_Abono));
						//temp_MovimientosTotalBean.setAbonos(int_Total_Abono+"");						
					}
					
					
					query.setLength(0);		

					if(radioFiltro.equals("1")){
						//recuperar todos los linea_prepago_ids de la organizacion
						query.setLength(0);
						query.append("SELECT sum(MONTO_FIN) FROM movimiento ")
							.append("where (FEC_HOR,linea_Prepago_id) in ")
							.append("(select max(FEC_HOR), linea_Prepago_id ")
							.append("from movimiento M ")
							.append("WHERE M.FEC_HOR < to_date(?,'dd/mm/yyyy hh24:mi:ss') ")
							.append("AND linea_prepago_id in (select linea_prepago_id from linea_prepago where pe_juri_id=?) ")
							.append("group by linea_Prepago_id)");
												
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, date_Inicio_Ora);
						pstmt.setString(2, peJuriId);
						rs=null;			
						rs = pstmt.executeQuery();

						
						if(rs.next())
						{
							dbl_Saldo_Inicial=Double.parseDouble(rs.getString(1)==null?"0":rs.getString(1));
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}else{
							dbl_Saldo_Inicial=Double.parseDouble("0");
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}	
					}
					if(radioFiltro.equals("2")){
						query.setLength(0);	
						query.append(" SELECT MONTO_FIN FROM MOVIMIENTO  ");
						query.append(" WHERE LINEA_PREPAGO_ID =? ");
						query.append(" AND FEC_HOR < to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
						query.append(" ORDER BY FEC_HOR DESC "); 
						
						if (isTrace(this)) System.out.println(" ___verquery..._ "+ query.toString());

						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1,lpId );
						pstmt.setString(2, date_Inicio_Ora);

						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Saldo_Inicial=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}else{
							dbl_Saldo_Inicial=Double.parseDouble("0");
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}	
						
					}						
				
					listMovimientosTotal.add(temp_MovimientosTotalBean);
				}
			
			//SELECT monto_fin FROM MOVIMIENTO  WHERE LINEA_PREPAGO_ID =2 AND FEC_HOR < to_date('01/12/2002','dd/mm/yyyy hh24:mi:ss')  ORDER BY  FEC_HOR DESC
			
			req.setAttribute("perfilId",""+userLogged.getPerfilId());
			req.setAttribute("usuario_logeado",userLogged.getUserId());
			
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());
			
			req.setAttribute("selectedIDay",date_Dia_Inicio);
			req.setAttribute("selectedIMonth",date_Mes_Inicio);
			req.setAttribute("selectedIYear",date_Ano_Inicio);
			req.setAttribute("selectedFDay",date_Dia_Fin);
			req.setAttribute("selectedFMonth",date_Mes_Fin);
			req.setAttribute("selectedFYear",date_Ano_Fin);			

			if (listMovimientos.size()>0)
				req.setAttribute("listMovimientos",listMovimientos);
			if (listMovimientosTotal.size()>0)
				req.setAttribute("listMovimientosTotal",listMovimientosTotal);
			
			req.setAttribute("dateInicio",date_Inicio);
			req.setAttribute("dateFin",date_Fin);
			req.setAttribute("str_Criterio_Busqueda",str_Criterio_Busqueda);
			req.setAttribute("str_Variable_Busqueda",str_Variable_Busqueda);

			response.setStyle("movimientos");

			conn.commit();
			
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());			
		}catch (ValidacionException ve) {		
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","ReporteMovimientos.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			

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
	
	public ControllerResponse runVerReporteDetalleState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
		{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try {
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
			
			String date_Dia_Inicio = request.getParameter("diainicio");
			String date_Mes_Inicio = request.getParameter("mesinicio");
			String date_Ano_Inicio = request.getParameter("anoinicio");
			String date_Dia_Fin = request.getParameter("diafin");
			
			
			String date_Mes_Fin = request.getParameter("mesfin");
			String date_Ano_Fin  = request.getParameter("anofin");
			
			//cjvc77 20021230
			String rz_aux = request.getParameter("rz_aux");
			String ndoc_aux = request.getParameter("ndoc_aux");
			req.setAttribute("rz_aux", rz_aux);
			req.setAttribute("ndoc_aux", ndoc_aux);
			//Validar Rango de Fechas						
			String date_Inicio = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio)) ;
			String date_Fin = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin));
			
			String date_Inicio_Ora = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Inicio), Integer.parseInt(date_Mes_Inicio), Integer.parseInt(date_Ano_Inicio)) +" 00:00:00";
			String date_Fin_Ora = FechaUtil.getStringDate(Integer.parseInt(date_Dia_Fin), Integer.parseInt(date_Mes_Fin), Integer.parseInt(date_Ano_Fin)) +" 23:59:59";

			String radioFiltro = request.getParameter("radio");
			String razonSocial = request.getParameter("razon_social");
			String ruc = request.getParameter("ruc");
			String userId= request.getParameter("userId");

			String peJuriId = request.getParameter("peJuriId");
			String personaId = request.getParameter("personaId");
			String lpId = request.getParameter("lpId");


			String str_Criterio_Busqueda="";
			String str_Variable_Busqueda="";
			StringBuffer query = new StringBuffer();
			
			DboPeJuri personaJuridica = new DboPeJuri(dconn);
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			//String peJuriId = null;
			//String personaId = null;
			String jurisId = null;
			String codOrg = null;
			//String lpId = null;
			//String cuId = null;			
			
			int int_Total_Abono=0;
			double dbl_Total_Consumo=0;
			double dbl_Total_Abono=0;			
			double dbl_Saldo_Inicial=0;			
			double dbl_Saldo_Final=0;			
			
			MovimientosBean temp_MovimientosBean =null;	
			MovimientosTotalBean temp_MovimientosTotalBean =null;
			java.util.List listMovimientos = new java.util.ArrayList();
			java.util.List listMovimientosTotal = new java.util.ArrayList();									
			
					
					//sacar de la tabla PR_JURI  JURIS_ID
					personaJuridica.setField(DboPeJuri.CAMPO_PE_JURI_ID, userLogged.getCodOrg());
					personaJuridica.retrieve();
					jurisId = personaJuridica.getField(DboPeJuri.CAMPO_JURIS_ID);	
					peJuriId=personaJuridica.getField(DboPeJuri.CAMPO_PE_JURI_ID);
					
					if (userLogged.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO)
						jurisId =userLogged.getJurisdiccionId();
					

					temp_MovimientosTotalBean = new MovimientosTotalBean();
					temp_MovimientosTotalBean.setLineaPrepago(lpId);
				
					//Ejecucion del query
					
					query.setLength(0);		

					//if(radioFiltro.equals("1")){
							
						query.append(" SELECT T.PERSONA_ID, T.ABONO_ID, to_char(T.FEC_HOR,'dd/mm/yyyy hh24:mi:ss'), T.TOTALMONTO ");
						query.append(" , T.NOMBRE,DECODE(trim(T.TPO_PAG_VENT),'E' ");
						query.append(" ,'EFECTIVO','C','EFECTIVO',T.TPO_PAG_VENT) ");
						query.append(" FROM (SELECT T2.PERSONA_ID, T1.ABONO_ID, "); 
						query.append(" T2.FEC_HOR ");
						query.append(" ,  T1.TOTALMONTO, T2.NOMBRE,T2.TPO_PAG_VENT ");
			      		query.append(" FROM ");
						query.append(" (SELECT V.ABONO_ID, SUM(V.MONTO) TOTALMONTO ");
		            	query.append(" FROM   VW_MOVIMIENTO V ");
			          	query.append(" WHERE V.PERSONA_ID = ? ");
	   			       	query.append(" GROUP BY V.ABONO_ID) T1, ");
						//query.append(" GROUP BY CUBE(V.ABONO_ID)) T1, ");	   			       	
						query.append(" (SELECT V.PERSONA_ID, V.ABONO_ID, V.FEC_HOR ");
						query.append(" , V.TPO_PAG_VENT, V.MONTO, V.NOMBRE ");
		            	query.append(" FROM   ");
						query.append(" VW_MOVIMIENTO V ");
		            	query.append(" WHERE V.PERSONA_ID = ?) T2 ");
			    		query.append(" WHERE T1.ABONO_ID = T2.ABONO_ID(+))T ");
		                query.append(" WHERE T.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss')");  			    		
						//query.append(" WHERE T.FEC_HOR BETWEEN <<FECHA INICIO>> AND <<FECHA FIN>> ");
						
						if (isTrace(this)) System.out.println("___verquerya_ " + query.toString());
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, Integer.parseInt(personaId));
						pstmt.setInt(2, Integer.parseInt(personaId));						
						pstmt.setString(3, date_Inicio_Ora);
						pstmt.setString(4, date_Fin_Ora);
					//}


					rs = pstmt.executeQuery();
									
					while(rs.next())
					{
						temp_MovimientosBean = new MovimientosBean();				
						temp_MovimientosBean.setAbonoId(rs.getString(2));						
						temp_MovimientosBean.setFecha(rs.getString(3).substring(0,10));						
						temp_MovimientosBean.setHora(rs.getString(3).substring(10,19));						
						temp_MovimientosBean.setMonto(rs.getString(4));						
						temp_MovimientosBean.setNombre(rs.getString(5));						
						temp_MovimientosBean.setTipoPagoVentanilla(rs.getString(6));																																				
						listMovimientos.add(temp_MovimientosBean);
						//int_Total_Abono=int_Total_Abono+Integer.parseInt(rs.getString(4));
						dbl_Total_Abono=dbl_Total_Abono+Double.parseDouble(rs.getString(4));
					}	
						
					query.setLength(0);		

					//if(radioFiltro.equals("1")){
						query.append(" SELECT NVL(SUM(C.MONTO),0) ");
						query.append(" FROM PE_JURI PJ, LINEA_PREPAGO L, MOVIMIENTO M, CONSUMO C, TRANSACCION T, OFIC_REGISTRAL O ");
						query.append(" WHERE L.PE_JURI_ID = PJ.PE_JURI_ID AND L.CUENTA_ID IS NOT NULL AND M.LINEA_PREPAGO_ID = L.LINEA_PREPAGO_ID");
		            	query.append(" AND M.MOVIMIENTO_ID = C.MOVIMIENTO_ID AND C.TRANS_ID = T.TRANS_ID AND T.OFIC_REG_ID = O.OFIC_REG_ID AND T.REG_PUB_ID  = O.REG_PUB_ID");
		            	query.append(" AND PJ.PE_JURI_ID = ?");								            	
     			       	query.append(" AND M.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
     			       	
						if (isTrace(this)) System.out.println(" ___verquery._ "+ query.toString());
						
						pstmt = conn.prepareStatement(query.toString());
						//pstmt.setInt(1, Integer.parseInt(cuId));
						pstmt.setString(1, peJuriId);
						pstmt.setString(2, date_Inicio_Ora);
						pstmt.setString(3, date_Fin_Ora);
						
						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Total_Consumo=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setConsumos(String.valueOf(dbl_Total_Consumo));
							//temp_MovimientosTotalBean.setConsumos(rs.getString(1));
						}	
						temp_MovimientosTotalBean.setAbonos(dbl_Total_Abono+"");
						//temp_MovimientosTotalBean.setAbonos(int_Total_Abono+"");
					//}

					
					//query.setLength(0);		

					//if(radioFiltro.equals("1")){
							
						//recuperar todos los linea_prepago_ids de la organizacion
						query.setLength(0);
						query.append("SELECT sum(MONTO_FIN) FROM movimiento ")
							.append("where (FEC_HOR,linea_Prepago_id) in ")
							.append("(select max(FEC_HOR), linea_Prepago_id ")
							.append("from movimiento M ")
							.append("WHERE M.FEC_HOR < to_date(?,'dd/mm/yyyy hh24:mi:ss') ")
							.append("AND linea_prepago_id in (select linea_prepago_id from linea_prepago where pe_juri_id=?) ")
							.append("group by linea_Prepago_id)");
												
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, date_Inicio_Ora);
						pstmt.setString(2, peJuriId);

						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Saldo_Inicial=Double.parseDouble(rs.getString(1)==null?"0":rs.getString(1));
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}else{
							dbl_Saldo_Inicial=Double.parseDouble("0");
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}	
					//}

			listMovimientosTotal.add(temp_MovimientosTotalBean);
			
			//SELECT monto_fin FROM MOVIMIENTO  WHERE LINEA_PREPAGO_ID =2 AND FEC_HOR < to_date('01/12/2002','dd/mm/yyyy hh24:mi:ss')  ORDER BY  FEC_HOR DESC
			
			req.setAttribute("perfilId",""+userLogged.getPerfilId());
			req.setAttribute("usuario_logeado",userLogged.getUserId());
			
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());
			
			req.setAttribute("selectedIDay",date_Dia_Inicio);
			req.setAttribute("selectedIMonth",date_Mes_Inicio);
			req.setAttribute("selectedIYear",date_Ano_Inicio);
			req.setAttribute("selectedFDay",date_Dia_Fin);
			req.setAttribute("selectedFMonth",date_Mes_Fin);
			req.setAttribute("selectedFYear",date_Ano_Fin);			

			if (listMovimientos.size()>0)
				req.setAttribute("listMovimientos",listMovimientos);
			if (listMovimientosTotal.size()>0)
				req.setAttribute("listMovimientosTotal",listMovimientosTotal);
			
			req.setAttribute("dateInicio",date_Inicio);
			req.setAttribute("dateFin",date_Fin);
			req.setAttribute("str_Criterio_Busqueda",str_Criterio_Busqueda);
			req.setAttribute("str_Variable_Busqueda",str_Variable_Busqueda);

			response.setStyle("movimientos");

			conn.commit();
			
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
			/*
		}catch (ValidacionException ve) {		
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","ReporteMovimientos.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			
*/
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
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);				
		

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
				
		try {
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean userLogged = (UsuarioBean) session.getAttribute("Usuario");
			
			//Recepcion de Parametros de formulario JSP (fechas)
			
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

			
			String radioFiltro = request.getParameter("radio");
			String razonSocial = request.getParameter("razon_social");
			String ruc = request.getParameter("ruc");
			String userId= request.getParameter("userId");

			String str_Criterio_Busqueda="";
			String str_Variable_Busqueda="";
			StringBuffer query = new StringBuffer();
			
			DboPeJuri personaJuridica = new DboPeJuri(dconn);
			
			//Definicion de variables de BD
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			//StringBuffer strbfr_sql = new StringBuffer();			
			//java.sql.PreparedStatement prpstmt =null;
			
			String peJuriId = null;
			String personaId = null;
			String jurisId = null;
			String codOrg = null;
			String lpId = null;
			String cuId = null;			
			
			int int_Total_Abono=0;
			double dbl_Total_Consumo=0;
			double dbl_Total_Abono=0;			
			double dbl_Saldo_Inicial=0;			
			double dbl_Saldo_Final=0;			
			
			MovimientosBean temp_MovimientosBean =null;	
			MovimientosTotalBean temp_MovimientosTotalBean =null;
			java.util.List temp_list_Movimientos = new java.util.ArrayList();
			java.util.List temp_list_MovimientosTotal = new java.util.ArrayList();									
			

			
					//sacar de la tabla PR_JURI  JURIS_ID
					personaJuridica.setField(DboPeJuri.CAMPO_PE_JURI_ID, userLogged.getCodOrg());
					personaJuridica.retrieve();
					jurisId = personaJuridica.getField(DboPeJuri.CAMPO_JURIS_ID);	
					peJuriId=personaJuridica.getField(DboPeJuri.CAMPO_PE_JURI_ID);
					
					if (userLogged.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO)
						jurisId =userLogged.getJurisdiccionId();

					if(radioFiltro.equals("1")){ //Tipo de Usuario de Organizacion

						if(!razonSocial.equals("")){
				    			str_Criterio_Busqueda="Razon Social";
								str_Variable_Busqueda=razonSocial;

								query.setLength(0);
								
								query.append(" SELECT PJ.PE_JURI_ID, PJ.PERSONA_ID, LP. LINEA_PREPAGO_ID ");
								query.append(" FROM PE_JURI PJ, LINEA_PREPAGO LP ");
								query.append(" WHERE  ");
								query.append(" PJ.RAZ_SOC LIKE ? "); 						

								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){
									query.append(" AND ");
									query.append(" PJ.JURIS_ID = ? ");
								}	
								query.append("AND LP.PE_JURI_ID=PJ.PE_JURI_ID AND LP.CUENTA_ID IS NULL ");
								pstmt = conn.prepareStatement(query.toString());
								pstmt.setString(1, "%" +razonSocial.toUpperCase() + "%");
								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){								
									pstmt.setString(2, jurisId);
								}	
								rs = pstmt.executeQuery();
								if(rs.next())
								{
									peJuriId = rs.getString(1).trim();
									personaId = rs.getString(2).trim();
									lpId = rs.getString(3).trim();
								}else
								{
									throw new gob.pe.sunarp.extranet.util.ValidacionException(" No existen organizaciones registradas de razón social" + razonSocial ,"");
								}
						
						}// si no recupera nada mandar mensaje de error
						//para el ruc
						if(!ruc.equals("")){
								
				    			str_Criterio_Busqueda="RUC";
								str_Variable_Busqueda=ruc;

								query.setLength(0);					
								query.append(" SELECT PJ.PE_JURI_ID, PJ.PERSONA_ID,LP.LINEA_PREPAGO_ID  ");
								query.append(" FROM PE_JURI PJ, PERSONA PE, LINEA_PREPAGO LP ");
								query.append(" WHERE PJ.PERSONA_ID=PE.PERSONA_ID ");
								query.append(" AND PE.TIPO_DOC_ID='05' ");
								query.append(" AND NUM_DOC_IDEN = ?");
								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){
									query.append(" AND ");
									query.append(" PJ.JURIS_ID = ? ");
								}	
								query.append("AND LP.PE_JURI_ID=PJ.PE_JURI_ID AND LP.CUENTA_ID IS NULL ");
								pstmt = conn.prepareStatement(query.toString());
								pstmt.setString(1, ruc);
								if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ){								
									pstmt.setString(2, jurisId);
								}							
								rs = pstmt.executeQuery();
								if(rs.next())
								{
									peJuriId = rs.getString(1).trim();
									personaId = rs.getString(2).trim();
									lpId = rs.getString(3).trim();									
								}else
								{
									throw new gob.pe.sunarp.extranet.util.ValidacionException(" No existen organizaciones registradas con RUC" + ruc ,"");								
								}
						  }
						  
					}
					//SELECT PERSONA_ID FROM PE_JURI WHERE PE_JURI=<<COD ORG SESION>>
					if(radioFiltro.equals("2")){ //Tipo de Usuario INDIVIDUAL
						str_Criterio_Busqueda="userId";
						str_Variable_Busqueda = userId;

						query.setLength(0);
						query.append(" SELECT CJ.JURIS_ID,CJ.PERSONA_ID, CU.CUENTA_ID ");
						query.append(" FROM CUENTA CU, CUENTA_JURIS CJ ");
						query.append(" WHERE "); 
						query.append(" CU.CUENTA_ID=CJ.CUENTA_ID "); 
						query.append(" AND CU.USR_ID= ? "); 
						
						if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION || userLogged.getPerfilId() == PERFIL_ADMIN_ORG_EXT ||  userLogged.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO){
							query.append(" AND ");
							query.append(" CJ.JURIS_ID = ? ");									
						}	
						if (isTrace(this)) trace("Prepare"+ query.toString(), request);
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, userId.toUpperCase() );
						if( userLogged.getPerfilId() == PERFIL_ADMIN_JURISDICCION ||  userLogged.getPerfilId() == PERFIL_ADMIN_ORG_EXT ||  userLogged.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO){								
							pstmt.setString(2, jurisId);
						}	
						rs = pstmt.executeQuery();
						if(rs.next())
						{
							peJuriId = rs.getString(1).trim();
							personaId = rs.getString(2).trim();
							cuId=rs.getString(3).trim();
						}else{
							throw new ValidacionException(" No existe usuario registrado " + userId ,"");
						}
					}				
						  
					temp_MovimientosTotalBean = new MovimientosTotalBean();
					temp_MovimientosTotalBean.setLineaPrepago(lpId);
					temp_MovimientosTotalBean.setLineaPrepago(cuId);					
					//Ejecucion del query
					
					query.setLength(0);		

					if(radioFiltro.equals("1")){
							
						query.append(" SELECT T.PERSONA_ID, T.ABONO_ID, to_char(T.FEC_HOR,'dd/mm/yyyy hh24:mi:ss'), T.TOTALMONTO ");
						query.append(" , T.NOMBRE,DECODE(trim(T.TPO_PAG_VENT),'E' ");
						query.append(" ,'EFECTIVO','C','EFECTIVO',T.TPO_PAG_VENT) ");
						query.append(" FROM (SELECT T2.PERSONA_ID, T1.ABONO_ID, "); 
						query.append(" T2.FEC_HOR ");
						query.append(" ,  T1.TOTALMONTO, T2.NOMBRE,T2.TPO_PAG_VENT ");
			      		query.append(" FROM ");
						query.append(" (SELECT V.ABONO_ID, SUM(V.MONTO) TOTALMONTO ");
		            	query.append(" FROM   VW_MOVIMIENTO V ");
			          	query.append(" WHERE V.PERSONA_ID = ? ");
	   			       	query.append(" GROUP BY V.ABONO_ID) T1, ");
						//query.append(" GROUP BY CUBE(V.ABONO_ID)) T1, ");	   			       	
						query.append(" (SELECT V.PERSONA_ID, V.ABONO_ID, V.FEC_HOR ");
						query.append(" , V.TPO_PAG_VENT, V.MONTO, V.NOMBRE ");
		            	query.append(" FROM   ");
						query.append(" VW_MOVIMIENTO V ");
		            	query.append(" WHERE V.PERSONA_ID = ?) T2 ");
			    		query.append(" WHERE T1.ABONO_ID = T2.ABONO_ID(+))T ");
		                query.append(" WHERE T.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss')");  			    		
						//query.append(" WHERE T.FEC_HOR BETWEEN <<FECHA INICIO>> AND <<FECHA FIN>> ");
	
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, Integer.parseInt(personaId));
						pstmt.setInt(2, Integer.parseInt(personaId));						
						pstmt.setString(3, date_Inicio_Ora);
						pstmt.setString(4, date_Fin_Ora);
					}						


					if(radioFiltro.equals("2")){
							
						query.append(" SELECT T.PERSONA_ID, T.ABONO_ID, to_char(T.FEC_HOR,'dd/mm/yyyy hh24:mi:ss'), T.TOTALMONTO ");
						query.append(" , T.NOMBRE,DECODE(trim(T.TPO_PAG_VENT),'E' ");
						query.append(" ,'EFECTIVO','C','EFECTIVO',T.TPO_PAG_VENT) ");
						query.append(" FROM (SELECT T2.PERSONA_ID, T1.ABONO_ID, "); 
						query.append(" T2.FEC_HOR ");
						query.append(" ,  T1.TOTALMONTO, T2.NOMBRE,T2.TPO_PAG_VENT ");
			      		query.append(" FROM ");
						query.append(" (SELECT V.ABONO_ID, SUM(V.MONTO) TOTALMONTO ");
		            	query.append(" FROM   VW_MOVIMIENTO V ");
			          	query.append(" WHERE V.PERSONA_ID = ? ");
			          	query.append(" GROUP BY V.ABONO_ID) T1, ");
	   			       	//query.append(" GROUP BY CUBE(V.ABONO_ID)) T1, ");
						query.append(" (SELECT V.PERSONA_ID, V.ABONO_ID, V.FEC_HOR ");
						query.append(" , V.TPO_PAG_VENT, V.MONTO, V.NOMBRE ");
		            	query.append(" FROM   ");
						query.append(" VW_MOVIMIENTO V ");
		            	query.append(" WHERE V.PERSONA_ID = ?) T2 ");
			    		query.append(" WHERE T1.ABONO_ID = T2.ABONO_ID(+))T ");
		                query.append(" WHERE T.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss')");  			    		
						//query.append(" WHERE T.FEC_HOR BETWEEN <<FECHA INICIO>> AND <<FECHA FIN>> ");
						if (isTrace(this)) trace("Prepare"+ query.toString(), request);	
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, Integer.parseInt(personaId));
						pstmt.setInt(2, Integer.parseInt(personaId));						
						pstmt.setString(3, date_Inicio_Ora);
						pstmt.setString(4, date_Fin_Ora);
					}						
						

					rs = pstmt.executeQuery();
									
					while(rs.next())
					{
						temp_MovimientosBean = new MovimientosBean();				
						temp_MovimientosBean.setAbonoId(rs.getString(2));						
						temp_MovimientosBean.setFecha(rs.getString(3).substring(0,10));						
						temp_MovimientosBean.setHora(rs.getString(3).substring(10,19));						
						temp_MovimientosBean.setMonto(rs.getString(4));						
						temp_MovimientosBean.setNombre(rs.getString(5));						
						temp_MovimientosBean.setTipoPagoVentanilla(rs.getString(6));																																				
						temp_list_Movimientos.add(temp_MovimientosBean);
						//int_Total_Abono=int_Total_Abono+Integer.parseInt(rs.getString(4));
						dbl_Total_Abono=dbl_Total_Abono+Double.parseDouble(rs.getString(4));
					}	
						
					query.setLength(0);		

					if(radioFiltro.equals("1")){
						query.append(" SELECT NVL(SUM(C.MONTO),0)  ");
						query.append(" FROM CONSUMO C,MOVIMIENTO M, OFIC_REGISTRAL O , LINEA_PREPAGO L, TRANSACCION T ");
						query.append(" WHERE M.MOVIMIENTO_ID=C.MOVIMIENTO_ID AND C.TRANS_ID=T.TRANS_ID  ");
						query.append(" AND L.LINEA_PREPAGO_ID=M.LINEA_PREPAGO_ID AND L.LINEA_PREPAGO_ID IN  "); 
						query.append(" (SELECT LP.LINEA_PREPAGO_ID  ");
			      		query.append(" FROM LINEA_PREPAGO LP,PE_JURI PJ ");
						//query.append(" WHERE LP.CUENTA_ID=? )  ");
						query.append(" WHERE LP.PE_JURI_ID= PJ.PE_JURI_ID ");
		            	query.append(" AND LP.CUENTA_ID IS NOT NULL  )");
		            	query.append(" AND O.OFIC_REG_ID=T.OFIC_REG_ID  ");								            	
     			       	query.append(" AND O.REG_PUB_ID=T.REG_PUB_ID  ");
     			       	query.append(" AND M.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
						if (isTrace(this)) trace("Prepare"+ query.toString(), request);					
						pstmt = conn.prepareStatement(query.toString());
						//pstmt.setInt(1, Integer.parseInt(cuId));
						pstmt.setString(1, date_Inicio_Ora);
						pstmt.setString(2, date_Fin_Ora);
						
						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Total_Consumo=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setConsumos(dbl_Total_Consumo+"");
							//temp_MovimientosTotalBean.setConsumos(rs.getString(1));
						}	
						temp_MovimientosTotalBean.setAbonos(dbl_Total_Abono+"");
						//temp_MovimientosTotalBean.setAbonos(int_Total_Abono+"");
					}


					if(radioFiltro.equals("2")){
						query.append(" SELECT NVL(SUM(C.MONTO),0)  ");
						query.append(" FROM CONSUMO C,MOVIMIENTO M, OFIC_REGISTRAL O , LINEA_PREPAGO L, TRANSACCION T ");
						query.append(" WHERE M.MOVIMIENTO_ID=C.MOVIMIENTO_ID AND C.TRANS_ID=T.TRANS_ID  ");
						query.append(" AND L.LINEA_PREPAGO_ID=M.LINEA_PREPAGO_ID AND L.LINEA_PREPAGO_ID IN  "); 
						query.append(" (SELECT LP.LINEA_PREPAGO_ID  ");
			      		query.append(" FROM LINEA_PREPAGO LP ");
						query.append(" WHERE LP.CUENTA_ID=? )  ");
		            	query.append(" AND O.OFIC_REG_ID=T.OFIC_REG_ID  ");
     			       	query.append(" AND O.REG_PUB_ID=T.REG_PUB_ID  ");
     			       	query.append(" AND M.FEC_HOR BETWEEN  to_date(?,'dd/mm/yyyy hh24:mi:ss')  and  to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
						if (isTrace(this)) trace("Prepare"+ query.toString(), request);					
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, Integer.parseInt(cuId));
						pstmt.setString(2, date_Inicio_Ora);
						pstmt.setString(3, date_Fin_Ora);
						
						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Total_Consumo=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setConsumos(dbl_Total_Consumo+"");							
							//temp_MovimientosTotalBean.setConsumos(rs.getString(1));
						}	
						temp_MovimientosTotalBean.setAbonos(dbl_Total_Abono+"");
						//temp_MovimientosTotalBean.setAbonos(int_Total_Abono+"");						
					}
					
					
					query.setLength(0);		

					if(radioFiltro.equals("1")){
							
						query.append(" SELECT MONTO_FIN FROM MOVIMIENTO  ");
						query.append(" WHERE LINEA_PREPAGO_ID =? ");
						query.append(" AND FEC_HOR < to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
						query.append(" ORDER BY FEC_HOR DESC "); 
	
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1,lpId );
						pstmt.setString(2, date_Inicio_Ora);

						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Saldo_Inicial=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}else{
							dbl_Saldo_Inicial=Double.parseDouble("0");
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}	
					}
					if(radioFiltro.equals("2")){
							
						query.append(" SELECT MONTO_FIN FROM MOVIMIENTO  ");
						query.append(" WHERE LINEA_PREPAGO_ID =? ");
						query.append(" AND FEC_HOR < to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
						query.append(" ORDER BY FEC_HOR DESC "); 
	
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1,lpId );
						pstmt.setString(2, date_Inicio_Ora);

						rs=null;			
						rs = pstmt.executeQuery();
						
						if(rs.next())
						{
							dbl_Saldo_Inicial=Double.parseDouble(rs.getString(1));
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}else{
							dbl_Saldo_Inicial=Double.parseDouble("0");
							temp_MovimientosTotalBean.setSaldoInicial(dbl_Saldo_Inicial+"");
							dbl_Saldo_Final=dbl_Saldo_Inicial +dbl_Total_Abono-dbl_Total_Consumo;
							temp_MovimientosTotalBean.setSaldoFinal(dbl_Saldo_Final+"");
						}	
						
					}						
					
					temp_list_MovimientosTotal.add(temp_MovimientosTotalBean);

				
			
			//SELECT monto_fin FROM MOVIMIENTO  WHERE LINEA_PREPAGO_ID =2 AND FEC_HOR < to_date('01/12/2002','dd/mm/yyyy hh24:mi:ss')  ORDER BY  FEC_HOR DESC
			
			/*
			
			java.util.List temp_list_Dia = new java.util.ArrayList();		
			for (int i=1;i<32;i++)
			{
				DiasBean diasbean = new DiasBean();
				if(i<10)
				{
					diasbean.setStr_Dia_Inicio("0"+i);
					diasbean.setStr_Dia_Fin("0"+i);
					if(i==Integer.parseInt(date_Dia_Inicio)){
						diasbean.setStr_Dia_Selected_Inicio("selected");					
					}else{
						diasbean.setStr_Dia_Selected_Inicio("");					
					}
					if(i==Integer.parseInt(date_Dia_Fin)){
						diasbean.setStr_Dia_Selected_Fin("selected");										
					}else{
						diasbean.setStr_Dia_Selected_Fin("");														
					}
					
				}else{
					diasbean.setStr_Dia_Inicio(""+i);
					diasbean.setStr_Dia_Fin(""+i);					
					if(i==Integer.parseInt(date_Dia_Inicio)){
						diasbean.setStr_Dia_Selected_Inicio("selected");					
					}else{
						diasbean.setStr_Dia_Selected_Inicio("");					
					}
					if(i==Integer.parseInt(date_Dia_Fin)){
						diasbean.setStr_Dia_Selected_Fin("selected");										
					}else{
						diasbean.setStr_Dia_Selected_Fin("");														
					}

				}
				temp_list_Dia.add(diasbean);
			}

			java.util.List temp_list_Mes = new java.util.ArrayList();		
			for (int i=1;i<13;i++)
			{
				MesesBean mesesbean = new MesesBean();
				if(i<10)
				{
					mesesbean.setStr_Mes_Inicio("0"+i);
					mesesbean.setStr_Mes_Fin("0"+i);					
					if(i==Integer.parseInt(date_Mes_Inicio)){
						mesesbean.setStr_Mes_Selected_Inicio("selected");													
					}else{
						mesesbean.setStr_Mes_Selected_Inicio("");																			
					}
					if(i==Integer.parseInt(date_Mes_Fin)){				
						mesesbean.setStr_Mes_Selected_Fin("selected");										
					}else{					
						mesesbean.setStr_Mes_Selected_Fin("");														
					}

				}else{
					mesesbean.setStr_Mes_Inicio(""+i);					
					mesesbean.setStr_Mes_Fin(""+i);								
					if(i==Integer.parseInt(date_Mes_Inicio)){
						mesesbean.setStr_Mes_Selected_Inicio("selected");														
					}else{
						mesesbean.setStr_Mes_Selected_Inicio("");					
											
					}
					if(i==Integer.parseInt(date_Mes_Fin)){		
						mesesbean.setStr_Mes_Selected_Fin("selected");										
					}else{				
						mesesbean.setStr_Mes_Selected_Fin("");														
					}							
				}
				temp_list_Mes.add(mesesbean);									
			}


			java.sql.Date date_Fecha  = new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime());
					
			String fecha =""+ date_Fecha;
			String str_ano = fecha.substring(0,4);
			int int_ano = Integer.parseInt(str_ano);
			int int_periodo = 4;
			java.util.List temp_list_Ano = new java.util.ArrayList();		
			for (int i=1;i<1+int_periodo;i++)
			{
				AnosBean anosbean = new AnosBean();
				anosbean.setStr_Ano_Inicio(""+(int_ano - int_periodo + i));
				anosbean.setStr_Ano_Fin(""+ (int_ano - int_periodo + i));					
				if(int_ano-int_periodo+i == Integer.parseInt(date_Ano_Inicio)){
					anosbean.setStr_Ano_Selected_Inicio("selected");					
				}else{
					anosbean.setStr_Ano_Selected_Inicio("");					
				}	
				if(int_ano-int_periodo+i == Integer.parseInt(date_Ano_Fin)){
					anosbean.setStr_Ano_Selected_Fin("selected");														
				}else{
					anosbean.setStr_Ano_Selected_Fin("");														
				}	

				temp_list_Ano.add(anosbean);
			}*/
			
	 		String fname = "ReporteMovimientos.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			PrintWriter out_cliente = null;
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			StringBuffer stb = null;
			stb = new StringBuffer();
			stb.append("Numero de Abono").append(",");
			stb.append("Fecha").append(",");
			stb.append("Hora").append(",");
			stb.append("Monto").append(",");
			stb.append("Agencia").append(",");
			stb.append("Forma de Pago");			
					
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());

			for(java.util.Iterator iterator_movimientos = temp_list_Movimientos.iterator(); iterator_movimientos.hasNext();){
				MovimientosBean iterator_movimientosbean = (MovimientosBean) iterator_movimientos.next();

				stb = new StringBuffer();

				stb.append(iterator_movimientosbean.getAbonoId()).append(",");
				stb.append(iterator_movimientosbean.getFecha()).append(",");
				stb.append(iterator_movimientosbean.getHora()).append(",");
				stb.append(iterator_movimientosbean.getMonto()).append(",");
				stb.append(iterator_movimientosbean.getNombre()).append(",");
				stb.append(iterator_movimientosbean.getTipoPagoVentanilla());
				
				out_cliente.println(stb.toString());
			}
			
			//MovimientosTotalBean temp_MovimientosTotalBean = (MovimientosTotalBean) temp_list_MovimientosTotal;
			stb = new StringBuffer();
			stb.append("Saldo Inicial").append(",");
			stb.append("Abonos").append(",");
			stb.append("Consumos").append(",");
			stb.append("Saldo Final");		
			out_cliente.println(stb.toString());
			stb = new StringBuffer();
			stb.append(temp_MovimientosTotalBean.getSaldoInicial()).append(",");
			stb.append(temp_MovimientosTotalBean.getAbonos()).append(",");
			stb.append(temp_MovimientosTotalBean.getConsumos()).append(",");
			stb.append(temp_MovimientosTotalBean.getSaldoFinal());
			out_cliente.println(stb.toString());
			
			conn.commit();
			
			out_cliente.flush();
			out_cliente.close();
			response.setCustomResponse(true);			
		

		} 
		catch (CustomException ce) 
		{
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());			
		}
		catch (ValidacionException ve) 
		{
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","ReporteMovimientos.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			

		} 
		catch (DBException dbe) 
		{
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} 
		catch (Throwable ex) 
		{
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} 
		finally 
		{
			if(pool != null) 
				pool.release(conn);
			end(request);
		}
		
		
	return response;

	}		

}