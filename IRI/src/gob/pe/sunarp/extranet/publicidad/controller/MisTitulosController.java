package gob.pe.sunarp.extranet.publicidad.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.EstadoTituloBean;
import gob.pe.sunarp.extranet.publicidad.bean.MisTituloException;
import gob.pe.sunarp.extranet.publicidad.bean.TituloBean;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.util.*;
import java.sql.*;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import gob.pe.sunarp.extranet.pool.*;

public class MisTitulosController extends ControllerExtension {
	private String thisClass = MisTitulosController.class.getName() + ".";

	public MisTitulosController() {
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("buscaRecibo", "Ventana de Busq. x Apellidos y Nombres."));
		setInitialState("muestra");
	}

	public String getTitle() {
		return new String("MisTitulosController");
	}
	
	private void muestraEstados(ControllerRequest request, DBConnection dconn) throws DBException
	
	{
		// Paso 4 - Obtengo valores del Combo			
		EstadoTituloBean e = null;
		java.util.List lista = new java.util.ArrayList();
		
		DboTmEstadoTitulo est = new DboTmEstadoTitulo(dconn);
					
		e = new EstadoTituloBean();
		e.setEstado_id("20|30|40|60");
		e.setDescripcion("En Transito");
		lista.add(e);
		
		e = new EstadoTituloBean();
		e.setEstado_id("50|140");
		e.setDescripcion("TACHADO");
		lista.add(e);

		for(java.util.Iterator ii = est.searchAndRetrieveList().iterator(); ii.hasNext();){
			DboTmEstadoTitulo et = (DboTmEstadoTitulo) ii.next();
			e = new EstadoTituloBean();
			e.setEstado_id(et.getField(DboTmEstadoTitulo.CAMPO_ESTADO_TITULO_ID));
			if( (e.getEstado_id().equals("20")) || (e.getEstado_id().equals("30")) ||
				(e.getEstado_id().equals("40")) || (e.getEstado_id().equals("80")) ||
				(e.getEstado_id().equals("90")) || (e.getEstado_id().equals("100")) ||
				(e.getEstado_id().equals("110")) || (e.getEstado_id().equals("120")) ||
				(e.getEstado_id().equals("130")) ){
					e.setDescripcion(et.getField(DboTmEstadoTitulo.CAMPO_ESTADO));
					lista.add(e);
			}
		}
					
		e = new EstadoTituloBean();
		e.setEstado_id("20|30|40|50|60|80|90|100|110|120|130|140");
		e.setDescripcion("TODOS");
		lista.add(e);
		
		ExpressoHttpSessionBean.getRequest(request).setAttribute("estados", lista);
	}
	
	
	

	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		//java.sql.Connection conne = null;
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pst1 = null;
		java.sql.ResultSet rs1 = null;
		HttpServletRequest req = null;
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			req = ExpressoHttpSessionBean.getRequest(request);
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			//9nov2002
			//validar si puede entrar a esta opcion
			if (user.getCur()==null || 
				user.getCur().trim().length()==0)
				throw new gob.pe.sunarp.extranet.util.ValidacionException("","");
			
			muestraEstados(request, dconn);
					
			boolean busq_normal = false;
			boolean ordenarXEstado = false;
			boolean primera_vez = false;
		
		//Recuperamos variables de Búsqueda
			String orden = request.getParameter("orden");
			String aplica = request.getParameter("aplica");
			
			if(aplica != null && aplica.equalsIgnoreCase("X"))
				busq_normal = true;
		
			if(orden == null || orden.trim().length() <= 0)
				ordenarXEstado = false;
			else if(orden.equalsIgnoreCase("on"))
				ordenarXEstado = true;
			else
				ordenarXEstado = false;
		
		if(ordenarXEstado)
			ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "X");
			
		if(!busq_normal && request.getParameter("botonordenado") == null)
			primera_vez = true;
		
		//Capturo variables de fecha
			String diainicio = null;
			String mesinicio = null;
			String anoinicio = null;
			String diafin = null;
			String mesfin = null;
			String anofin = null;

			//Si es la primera vez que ingreso se muestra los datos de la Fecha de Hoy			
			if(primera_vez){
				String fh = FechaUtil.getCurrentDate();
				diainicio = fh.substring(0,2);
				mesinicio = fh.substring(3,5);
				anoinicio = fh.substring(6,10);
				diafin = fh.substring(0,2);
				mesfin = fh.substring(3,5);
				anofin = fh.substring(6,10);
			}else{
				diainicio = request.getParameter("diainicio");
				mesinicio = request.getParameter("mesinicio");
				anoinicio = request.getParameter("anoinicio");
				diafin = request.getParameter("diafin");
				mesfin = request.getParameter("mesfin");
				anofin = request.getParameter("anofin");
			}
			
			StringBuffer fecha_hoy_i = new StringBuffer(FechaUtil.stringTimeToOracleString(Integer.parseInt(diainicio), 
													Integer.parseInt(mesinicio), 
													Integer.parseInt(anoinicio), 
													0,0,0));
													
			StringBuffer fecha_hoy_f = new StringBuffer(FechaUtil.stringTimeToOracleString(
													Integer.parseInt(diafin), 
													Integer.parseInt(mesfin),
													Integer.parseInt(anofin), 
													23,59,59));
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("diainicio", diainicio);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mesinicio", mesinicio);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("anoinicio", anoinicio);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("diafin", diafin);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mesfin", mesfin);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("anofin", anofin);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("user", ExpressoHttpSessionBean.getUsuarioBean(request).getRazSocial());
			
		//Recuperamos variable de Estado
			String estados = request.getParameter("estado");
			boolean todos = false;
			
			StringTokenizer tok = null;
			if(primera_vez){
				tok = new StringTokenizer("20|30|40|60", "|");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("estado", "10|20|30|40");
			}else {	//Si dio click a Búsqueda o a Ordenar
				if(estados == null || estados.trim().length() <= 0)
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar un Estado", "errorTitulo");
				
				tok = new StringTokenizer(estados, "|");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("estado", estados);
			}
				
			StringBuffer estado = null;
				java.util.Vector v = new java.util.Vector();
				if(tok.hasMoreTokens()){
					estado = new StringBuffer(" IN ('");
					estado.append(tok.nextToken()).append("'");
					while(tok.hasMoreTokens())
						estado.append(", '").append(tok.nextToken()).append("'");

					estado.append(") ");
				}
// Paso 1 - VERIFICO SALDO DISPONIBLE
			Saldo cmd0 = new Saldo();
			boolean vs = cmd0.verificaSaldo(dconn, TipoServicio.CONSULTA_TITULOS, user);
				
			if (vs == false)
				throw new CustomException(Errors.EC_GENERIC_ERROR, "Saldo Insuficiente", "errorTitulo");

			//VERIFICAR COUNT
			long tamano = 0;
			int paginacion = 1;
			if(request.getParameter("pagina") != null)
				paginacion = Integer.parseInt(request.getParameter("pagina"));

			StringBuffer sql = new StringBuffer();
			boolean hacerCount = false;
			
			if(paginacion == 1)
				hacerCount = true;
						
			if(hacerCount){
				sql.append("SELECT count(*) FROM ( ");
				
				sql.append("SELECT /*+ORDERED*/ ");
				sql.append("distinct TITULO.REFNUM_TITU ");
				sql.append("FROM PRESENTANTE, TITULO, DETALLE_TITULO ");
				sql.append("WHERE PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID ");
				sql.append("AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES ");
				sql.append("AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
				sql.append("AND DETALLE_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU	");
				sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = '").append(user.getRegPublicoId()).append("' ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = '").append(user.getOficRegistralId()).append("' ");
				sql.append("AND PRESENTANTE.CUR_PRES = '").append(user.getCur()).append("' ");
				sql.append("AND DETALLE_TITULO.ESTADO_TITULO_ID ").append(estado.toString()).append(" ");
				sql.append("AND TITULO.TS_PRESENT > ").append(fecha_hoy_i.toString()).append(" ");
				
				sql.append("INTERSECT ");
				
				sql.append("SELECT /*+ORDERED*/ ");
				sql.append("distinct TITULO.REFNUM_TITU ");
				sql.append("FROM PRESENTANTE, TITULO, DETALLE_TITULO ");
				sql.append("WHERE PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID ");
				sql.append("AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES ");
				sql.append("AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
				sql.append("AND DETALLE_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU	");
				sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = '").append(user.getRegPublicoId()).append("' ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = '").append(user.getOficRegistralId()).append("' ");
				sql.append("AND PRESENTANTE.CUR_PRES = '").append(user.getCur()).append("' ");
				sql.append("AND DETALLE_TITULO.ESTADO_TITULO_ID ").append(estado.toString()).append(" ");
				sql.append("AND TITULO.TS_PRESENT < ").append(fecha_hoy_f.toString()).append(" ");
				sql.append(") ");
				
				if (isTrace(this)) System.out.println("SQL>>" + sql.toString());
			
				//stmt   = conne.createStatement();
				stmt   = conn.createStatement();
				rs = stmt.executeQuery(sql.toString());
				rs.next();
				tamano = rs.getLong(1);

				if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda())
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
			}else
				tamano = Long.parseLong(request.getParameter("tamano"));		

// Paso 2 - RECUPERANDO LOS TITULOS
				sql = new StringBuffer("SELECT ");
				sql.append("/*+ORDERED INDEX (PRESENTANTE INDX_PRESENT_CUR_OTROS) INDEX (TITULO INDX_TITU_PRES) INDEX(DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
				sql.append("DISTINCT ");
				sql.append("REGIS_PUBLICO.NOMBRE as rp_nombre, ");
				sql.append("TITULO.NUM_TITU as t_num_titu, ");
				sql.append("TITULO.ANO_TITU as t_ano_titu, ");
				sql.append("OFIC_REGISTRAL.NOMBRE as or_nombre, ");
				sql.append("REGIS_PUBLICO.REG_PUB_ID as rp_reg_pub_id, ");
				sql.append("OFIC_REGISTRAL.OFIC_REG_ID as or_ofic_reg_id, ");
				sql.append("TITULO.TS_PRESENT as t_ts_present, ");
				sql.append("TM_ESTADO_TITULO.ESTADO as tet_estado, ");
				sql.append("TITULO.FEC_VENC as t_fec_venc, ");
				sql.append("DETALLE_TITULO.MONTO_LIQ as dt_monto_liq, ");
				sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_titulo_id, ");
				sql.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
				sql.append("TITULO.OFIC_REG_ID as t_ofic_reg_id, ");
				sql.append("TITULO.AREA_REG_ID as t_area_reg_id, ");
				sql.append("TITULO.REFNUM_TITU as t_refnum_titu ");
				sql.append("FROM PRESENTANTE, TITULO, DETALLE_TITULO, TM_ESTADO_TITULO, OFIC_REGISTRAL, REGIS_PUBLICO ");
				sql.append("WHERE TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID ");
				sql.append("AND TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID ");
				sql.append("AND TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES ");
				sql.append("AND TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES ");
				sql.append("AND DETALLE_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU ");
				sql.append("AND TM_ESTADO_TITULO.ESTADO_TITULO_ID = DETALLE_TITULO.ESTADO_TITULO_ID ");
				sql.append("AND OFIC_REGISTRAL.REG_PUB_ID = TITULO.REG_PUB_ID ");
				sql.append("AND OFIC_REGISTRAL.OFIC_REG_ID = TITULO.OFIC_REG_ID ");
				sql.append("AND REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
				sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = '").append(user.getRegPublicoId()).append("' ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = '").append(user.getOficRegistralId()).append("' ");
				sql.append("AND PRESENTANTE.CUR_PRES = '").append(user.getCur()).append("' ");
				sql.append("AND DETALLE_TITULO.ESTADO_TITULO_ID ").append(estado.toString());
				sql.append(" AND TITULO.TS_PRESENT > ").append(fecha_hoy_i.toString()).append(" ");
				
				sql.append("INTERSECT ");
				
				sql.append("SELECT ");
				sql.append("/*+ORDERED INDEX (PRESENTANTE INDX_PRESENT_CUR_OTROS) INDEX (TITULO INDX_TITU_PRES) INDEX(DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
				sql.append("DISTINCT ");
				sql.append("REGIS_PUBLICO.NOMBRE as rp_nombre, ");
				sql.append("TITULO.NUM_TITU as t_num_titu, ");
				sql.append("TITULO.ANO_TITU as t_ano_titu, ");
				sql.append("OFIC_REGISTRAL.NOMBRE as or_nombre, ");
				sql.append("REGIS_PUBLICO.REG_PUB_ID as rp_reg_pub_id, ");
				sql.append("OFIC_REGISTRAL.OFIC_REG_ID as or_ofic_reg_id, ");
				sql.append("TITULO.TS_PRESENT as t_ts_present, ");
				sql.append("TM_ESTADO_TITULO.ESTADO as tet_estado, ");
				sql.append("TITULO.FEC_VENC as t_fec_venc, ");
				sql.append("DETALLE_TITULO.MONTO_LIQ as dt_monto_liq, ");
				sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_titulo_id, ");
				sql.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
				sql.append("TITULO.OFIC_REG_ID as t_ofic_reg_id, ");
				sql.append("TITULO.AREA_REG_ID as t_area_reg_id, ");
				sql.append("TITULO.REFNUM_TITU as t_refnum_titu ");
				sql.append("FROM PRESENTANTE, TITULO, DETALLE_TITULO, TM_ESTADO_TITULO, OFIC_REGISTRAL, REGIS_PUBLICO ");
				sql.append("WHERE TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID ");
				sql.append("AND TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID ");
				sql.append("AND TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES ");
				sql.append("AND TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES ");
				sql.append("AND DETALLE_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU ");
				sql.append("AND TM_ESTADO_TITULO.ESTADO_TITULO_ID = DETALLE_TITULO.ESTADO_TITULO_ID ");
				sql.append("AND OFIC_REGISTRAL.REG_PUB_ID = TITULO.REG_PUB_ID ");
				sql.append("AND OFIC_REGISTRAL.OFIC_REG_ID = TITULO.OFIC_REG_ID ");
				sql.append("AND REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
				sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = '").append(user.getRegPublicoId()).append("' ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = '").append(user.getOficRegistralId()).append("' ");
				sql.append("AND PRESENTANTE.CUR_PRES = '").append(user.getCur()).append("' ");
				sql.append("AND DETALLE_TITULO.ESTADO_TITULO_ID ").append(estado.toString());
				sql.append(" AND TITULO.TS_PRESENT < ").append(fecha_hoy_f.toString()).append(" ");

				if(!ordenarXEstado){
					sql.append("ORDER BY t_ts_present ");
				}else{
					sql.append("ORDER BY tet_estado ");
				}
			
			if (isTrace(this)) System.out.print(">> SQL : " + sql.toString());
			
			
			Propiedades propiedades = Propiedades.getInstance();
		    int conta = 1;
		    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    stmt.setFetchSize(propiedades.getLineasPorPag());
		    rs = stmt.executeQuery(sql.toString());

			if (paginacion > 1)
		   		rs.absolute(propiedades.getLineasPorPag() * (paginacion - 1));
	
	      
	      boolean b = rs.next();
		  java.util.List list = new java.util.ArrayList();
  		  TituloBean detalle = null;
			
	      while (b==true && conta <= propiedades.getLineasPorPag()){
                conta++;			
				detalle = new TituloBean();
					
				detalle.setRegPub_id(rs.getString("rp_reg_pub_id"));
				detalle.setRegPub_nombre(rs.getString("rp_nombre"));
				detalle.setOficReg_id(rs.getString("or_ofic_reg_id"));
				detalle.setOficReg_nombre(rs.getString("or_nombre"));
				detalle.setAnno(rs.getString("t_ano_titu"));
				detalle.setTitulo(rs.getString("t_num_titu"));
				
                java.util.Date date = rs.getDate("t_ts_present");
                if(date != null)
                	detalle.setFecPresent(FechaUtil.dateToString(date));
                else
                	detalle.setFecPresent(" ");
				
				date = rs.getDate("t_ts_present");
				if(date != null)
					detalle.setHorPresent(FechaUtil.dateTimeToString(new java.sql.Timestamp(date.getTime())).substring(11,19));
				else
					detalle.setHorPresent(" ");				
				
				detalle.setEstado_id(rs.getString("tet_estado"));//chequear
				detalle.setEstadoDesc(rs.getString("tet_estado"));//chequear
				detalle.setMontoLiquida(rs.getString("dt_monto_liq"));
				
                date = rs.getDate("t_fec_venc");
                if(date != null)
                	detalle.setFecVencimiento(FechaUtil.dateToString(date));
                else
                	detalle.setFecVencimiento(" ");

				//**PARA ESQUELAS					
					String est = rs.getString("tet_estado");
					String estId = rs.getString("tet_estado_titulo_id");
					
					String refNumTitu = rs.getString("t_refnum_titu");
					String areaRegID = rs.getString("t_area_reg_id");
					
					if(estId.equalsIgnoreCase("50")){
						detalle.setResultado(est);
						String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "T", areaRegID, false);
						detalle.setUrl(paramEsq);
						detalle.setDesc_url("[ VER ESQUELA ]");
					}else if(estId.equalsIgnoreCase("40")){
						detalle.setResultado(est);
						String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID,  false);
						detalle.setUrl(paramEsq);
						detalle.setDesc_url("[ VER ESQUELA ]");
					}else if(estId.equalsIgnoreCase("30")){
						detalle.setResultado(est + " con monto igual " + rs.getString("dt_monto_liq"));
						String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "L", areaRegID,  false);
						detalle.setUrl(paramEsq);
						detalle.setDesc_url("[ VER ESQUELA ]");
					}else if(estId.equalsIgnoreCase("110")){
						detalle.setResultado(est);
						String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "A", areaRegID,  false);//chequear
						detalle.setUrl(paramEsq);
						detalle.setDesc_url("[ VER ESQUELA ]");
					}else if(estId.equalsIgnoreCase("120")){
						detalle.setResultado(est);
						String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID,  false);
						detalle.setUrl(paramEsq);
						detalle.setDesc_url("[ VER ESQUELA ]");
					}else if(estId.equalsIgnoreCase("130")){
						String ssql = "SELECT RAZ_SOC_RES FROM RESERVA_RZ_SOC WHERE REFNUM_TITU = ? ";
						//pst1 = conne.prepareStatement(ssql);
						pst1 = conn.prepareStatement(ssql);
						pst1.setString(1, refNumTitu);
						rs1 = pst1.executeQuery();
								
						if(rs1.next())
							detalle.setResultado(est + " RAZON SOCIAL RESERVADA: " + rs1.getString(1));
						else
							detalle.setResultado(est + " RAZON SOCIAL RESERVADA: -");
									
						String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "R", areaRegID,  false);
						detalle.setUrl(paramEsq);
						detalle.setDesc_url("[ VER ESQUELA ]");
					}else
						detalle.setResultado(est);
				//**PARA ESQUELAS					

			// Para Participantes
				//boolean juri = participantesPN(conne, stmt, rs, detalle);
				boolean juri = participantesPN(conn, stmt, rs, detalle);
				if(juri)
					//participantesPJ(conne, stmt, rs, detalle);
					participantesPJ(conn, stmt, rs, detalle);

				list.add(detalle);
				b = rs.next();
			}
			
			if(list.size() <= 0)
				throw new MisTituloException("muestra");
				
			boolean hayNext = rs.next();			
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("list", list);
			
//*PAGINACION EN EL JSP*//	
  		  int pagSiguiente = 0;
  		  int linXpag = propiedades.getLineasPorPag();
		
			if(paginacion == 1){
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);

				pagSiguiente = (tamano >= linXpag)?linXpag:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Titulos del 1 al ").append(pagSiguiente));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));
			}else{
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
				
				pagSiguiente = (tamano >= linXpag * paginacion)?linXpag * paginacion:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Titulos del ").append(((paginacion - 1) * linXpag) + 1).append(" al ").append(pagSiguiente));
			}
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));

			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeroderegistros", String.valueOf(tamano));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));		
//*PAGINACION EN EL JSP*//

				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
				response.setStyle("muestra");
		}//try
		
		
		catch (ValidacionException ve){
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no existe informacion disponible<br><br><br><br><br>");			
		}catch(MisTituloException mte){
			rollback(conn, request);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);
			response.setStyle(mte.getForward());
		}catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			closeExtra(stmt, rs);
			closeExtra(pst1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	
	
	public boolean participantesPN(Connection conne, Statement stmt1, ResultSet rs1, TituloBean bean) throws SQLException, Exception{
				boolean juri = true;
				StringBuffer sql1 = new StringBuffer("SELECT ");
				sql1.append("IPT.APE_PAT as ipt_ape_pat, ");
				sql1.append("IPT.APE_MAT as ipt_ape_mat, ");
				sql1.append("IPT.NOMBRES as ipt_nombres ");
				sql1.append("FROM TITULO T, IND_PN_PARTIC_TITU IPT ");
				sql1.append("WHERE T.REG_PUB_ID = IPT.COD_REG ");
				sql1.append("AND T.OFIC_REG_ID = IPT.COD_OFIC_REG ");
				sql1.append("AND T.NUM_TITU = IPT.NU_TITU ");
				sql1.append("AND T.ANO_TITU = IPT.AA_TITU ");
				sql1.append("AND T.REG_PUB_ID = '").append(bean.getRegPub_id()).append("' ");
				sql1.append("AND T.ANO_TITU = '").append(bean.getAnno()).append("' ");
				sql1.append("AND T.NUM_TITU = '").append(bean.getTitulo()).append("' ");
				sql1.append("AND T.OFIC_REG_ID = '").append(bean.getOficReg_id()).append("' ");
				sql1.append("AND ROWNUM = 1");
				
				if (isTrace(this)) System.out.print(">> SQL : " + sql1.toString());
			
				stmt1 = conne.createStatement();
				rs1 = stmt1.executeQuery(sql1.toString());
				while(rs1.next()){
					if(rs1.getString("ipt_ape_pat") != null)
						bean.setPrimerPrtcApePat(rs1.getString("ipt_ape_pat"));
					else
						bean.setPrimerPrtcApePat("");
						
					if(rs1.getString("ipt_ape_mat") != null)
						bean.setPrimerPrtcApeMat(rs1.getString("ipt_ape_mat"));
					else
						bean.setPrimerPrtcApeMat("");
						
					if(rs1.getString("ipt_nombres") != null)
						bean.setPrimerPrtcNombres(rs1.getString("ipt_nombres"));
					else
						bean.setPrimerPrtcNombres("");
						
					juri = false;
				}
				return juri;
	}

	public void participantesPJ(Connection conne, Statement stmt1, ResultSet rs1, TituloBean bean) throws SQLException, Exception{
					StringBuffer sql1 = new StringBuffer("SELECT ");
					sql1.append("IPT.RAZ_SOC as ipt_raz_soc ");

					sql1.append("FROM TITULO T, IND_PJ_PARTIC_TITU IPT ");

					sql1.append("WHERE T.REG_PUB_ID = IPT.REG_PUB_ID ");
					sql1.append("AND T.OFIC_REG_ID = IPT.OFIC_REG_ID ");
					sql1.append("AND T.NUM_TITU = IPT.NU_TITU ");
					sql1.append("AND T.ANO_TITU = IPT.AA_TITU ");
					sql1.append("AND T.REG_PUB_ID = '").append(bean.getRegPub_id()).append("' ");
					sql1.append("AND T.ANO_TITU = '").append(bean.getAnno()).append("' ");
					sql1.append("AND T.NUM_TITU = '").append(bean.getTitulo()).append("' ");
					sql1.append("AND T.OFIC_REG_ID = '").append(bean.getOficReg_id()).append("' ");
					sql1.append("AND ROWNUM = 1");
					
					if (isTrace(this)) System.out.print(">> SQL : " + sql1.toString());
					
					stmt1 = conne.createStatement();
					rs1 = stmt1.executeQuery(sql1.toString());

					while(rs1.next()){
						bean.setPrimerPrtcApePat("");
						bean.setPrimerPrtcApeMat("");
						bean.setPrimerPrtcNombres(rs1.getString("ipt_raz_soc"));
					}
	}
	public void manejoPaginacion(Propiedades propiedades, ControllerRequest request, int paginacion, boolean hayNext, long tamano, int num_pagina) throws Exception{
  		  int pagSiguiente = 0;
  		  int linXpag = propiedades.getLineasPorPag();

			if(paginacion == 1){
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
				
				pagSiguiente = (tamano >= linXpag)?linXpag:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Titulos del 1 al ").append(pagSiguiente));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));
			}else{
				
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
				
				pagSiguiente = (tamano >= linXpag * paginacion)?linXpag * paginacion:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Titulos del ").append(((paginacion - 1) * linXpag) + 1).append(" al ").append(pagSiguiente));
			}
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeroderegistros", String.valueOf(tamano));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));		
	}
	public void closeExtra(java.sql.Statement stmt1, java.sql.ResultSet rs1){
		if(rs1 != null)
			try{rs1.close();
			}catch(Exception ex){}
		if(stmt1 != null)
			try{stmt1.close();
			}catch(Exception ex){}
	}	
}
