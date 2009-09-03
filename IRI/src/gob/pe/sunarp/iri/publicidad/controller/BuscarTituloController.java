package gob.pe.sunarp.iri.publicidad.controller;

import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaConsulTituloBean;
import gob.pe.sunarp.extranet.util.*;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public class BuscarTituloController extends ControllerExtension {

	private String thisClass = BuscarTituloController.class.getName() + ".";

	public BuscarTituloController() {
		super();
		addState(new State("selecciona", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("buscarXNroTitulo", "Ventana de Busq. x Nro Titulo."));
		addState(new State("buscarXNroTituloDet", "Ventana de Busq. x Nro Titulo Detalle."));
		addState(new State("verFormulario", "Ventana de Busq. x Usuario-Directa."));
		addState(new State("buscarXNroTituloG", "Estado que se encarga de direccionar al estado respectivo segun criterio de seleccion"));
		addState(new State("buscarXPresentantePNG", "Ventana de Busq. x Documento de Identidad."));
		addState(new State("buscarXPresentantePJG", "Ventana de Busq. x Organizacion."));
		addState(new State("buscarXPresentantePJRucG", "Ventana de Busq. x Organizacion."));
		
		addState(new State("buscarXParticipantePNG", "Ventana de Busq. x Organizacion."));
		addState(new State("buscarXParticipantePJG", "Ventana de Busq. x Organizacion."));
		addState(new State("buscaOrden", "Ventana de Busq. x Organizacion."));
		setInitialState("selecciona");
	}


	public String getTitle() {
		return new String("BuscarTituloController");
	}

	private void muestraDocsId(ControllerRequest request, DBConnection dconn) throws DBException
	{
		DboTmDocIden doc_iden = new DboTmDocIden(dconn);
			
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_TIPO_DOC_ID);
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
			//_
		ArrayList listaDocsIds = doc_iden.searchAndRetrieveList();
				
		java.util.List listaDocsId = new ArrayList();
				
		for(int i = 0; i < listaDocsIds.size(); i++)
		{
			DboTmDocIden docIdent = (DboTmDocIden) listaDocsIds.get(i);
				
			DocIdenBean bean = new DocIdenBean();
			bean.setTipoDoc(docIdent.getField(DboTmDocIden.CAMPO_TIPO_DOC_ID));
			bean.setNomAbre(docIdent.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
			listaDocsId.add(bean);
		}
			
		ExpressoHttpSessionBean.getRequest(request).setAttribute("listaDocsId", listaDocsId);
	}

	protected ControllerResponse runSeleccionaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		try{
			init(request);
			validarSesion(request);
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			int opcion = user.getFgInterno()?2:1;
			
			//String usuarioId = request.getParameter("userId");
			switch(opcion){
				case 1: transition("muestra", request, response); break;
				case 2: transition("verFormulario", request, response); break;
			}
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}

	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			UsuarioBean usuario = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			
			if(usuario.getFgInterno())
				return response;
				
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);				
				
			DboOficRegistral oficreg = new DboOficRegistral(dconn);
			oficreg.setFieldsToRetrieve(DboOficRegistral.CAMPO_REG_PUB_ID + "|" + DboOficRegistral.CAMPO_OFIC_REG_ID + "|" + DboOficRegistral.CAMPO_NOMBRE);
			
			ArrayList lista = oficreg.searchAndRetrieveList(DboOficRegistral.CAMPO_NOMBRE);
			DboOficRegistral oficregis = null;
			OficRegistralesBean bean = null;
			List listaOfic = new ArrayList();
			
			for(int i = 0; i < lista.size(); i++)
			{
				oficregis = (DboOficRegistral) lista.get(i);
				bean = new OficRegistralesBean();
				bean.setRegPubId(oficregis.getField(DboOficRegistral.CAMPO_REG_PUB_ID));
				bean.setOficRegId(oficregis.getField(DboOficRegistral.CAMPO_OFIC_REG_ID));
				bean.setNombre(oficregis.getField(DboOficRegistral.CAMPO_NOMBRE));
				listaOfic.add(bean);
			}
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("listaOficinas", listaOfic);
			
			response.setStyle("muestra");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	

	/**
	 * Consulta de Estado de Títulos >> Resultado de Título  Detalle
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	protected ControllerResponse runBuscarXNroTituloDetState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pst1 = null;
		java.sql.ResultSet rs1 = null;
		//jbugarin anotacion de inscripcion
		String esta = "";
		String ctrl = "";
		String descZona ="";
		
		try{
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);

			String mensaje_adicional = "";
			String area_registral= null;
			ExpressoHttpSessionBean.getRequest(request).setAttribute("odev", request.getParameter("CboOficinas"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("adev", request.getParameter("ano"));
			//Capturando Parametros
			StringTokenizer stk = null;
			String aux[] = new String[3];
			
			try{
				int y = 0;
				stk = new StringTokenizer(request.getParameter("oficinas"), "|");
				while (stk.hasMoreTokens()) 
					aux[y++] = stk.nextToken();
			}catch(Exception e1){
				throw new CustomException(Errors.EC_MISSING_PARAM, "No se pudo obtener oficina registral", "errorTitulo");
			}

			String aux1 = aux[0];
			String aux2 = request.getParameter("ano");
			String aux3 = request.getParameter("numtitu");
			String aux4 = aux[1];
			String aux5 = request.getParameter("areareg");

			if(aux3 == null || aux3.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar un Número de Titulo", "errorTitulo");

			if(aux2 == null || aux2.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar un Ano", "errorTitulo");
			
			DetalleTituloBean detalle = null;
			//Preguntando si Estado de Titulo es 10
			boolean esTituloPresentado = false;
			detalle = Generales.esTituloPresentado(aux3, aux2, aux1, aux4, dconn);

			if(detalle == null)
				esTituloPresentado = false;
			else{
				detalle.setFecha(FechaUtil.stringmmddyyyytoddmmyyyy(detalle.getFecha()));
				esTituloPresentado = true;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("presentado", "X");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", detalle);
				response.setStyle("detalle");
				
				Job003 j = new Job003();

				j.setUsuario(user);
				j.setCodigoServicio(TipoServicio.CONSULTA_TITULOS);
				j.setRegPubId(aux1);
				j.setOficRegId(aux4);
				j.setArea(detalle.getAreaRegistral());
								
				Thread llamador1 = new Thread(j);
				llamador1.start();
				
				//POSIBILIDAD 1 DE GRABAR TRANSACTION
				//log_transaction
				LogAuditoriaConsulTituloBean logbean = new LogAuditoriaConsulTituloBean();
				//_datos genericos
				logbean.setCodigoServicio(TipoServicio.CONSULTA_TITULOS);
				logbean.setUsuarioSession(user);
								//Modificado por: Proyecto Filtros de Acceso
								//Fecha: 02/10/2006
								//logbean.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
								logbean.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
								//Fecha: 08/10/2006             
								logbean.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
								//Fin Modificación
				//_datos especificos
				logbean.setOficRegId(aux4);
				logbean.setRegPubId(aux1);
				logbean.setAnoTitulo(aux2);
				logbean.setNumTitulo(aux3);
				/*
				Job004 j4 = new Job004();
					j4.setBean(logbean);
					Thread llamador2 = new Thread(j4);
					llamador2.start();									
				*/
			  if (Propiedades.getInstance().getFlagTransaccion()==true)
				Transaction.getInstance().registraTransaccion(logbean,conn);
				conn.commit();
			}
			
			if(!esTituloPresentado){
				StringBuffer sql = new StringBuffer("SELECT ");
				sql.append("/*+ORDERED INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
				sql.append("DISTINCT ");
				sql.append("DETALLE_TITULO.ESTADO_TITULO_ID as dt_estado_titulo_id, ");
				/**2004/05/10 Kuma Se agregaron estos dos campos **/
				sql.append("DETALLE_TITULO.PU_CTRL as pu_ctrl, ");
				sql.append("DETALLE_TITULO.ES_TITU_CALI as es_titu_cali, ");
				/**2004/05/10 Kuma Fin **/
				sql.append("TITULO.NUM_TITU as t_num_titu, ");
				sql.append("TITULO.ANO_TITU as t_ano_titu, ");
				sql.append("OFIC_REGISTRAL.NOMBRE as or_nombre, ");
				sql.append("TM_ESTADO_TITULO.MENSAJE as tet_mensaje, ");
				sql.append("TITULO.REFNUM_TITU as t_refnum_titu, ");
				sql.append("TITULO.FEC_VENC as t_fec_venc, ");
				sql.append("TITULO.TS_ULT_SYNC as ts_ult_sync, ");
				sql.append("PRESENTANTE.CUR_PRES as p_cur_pres, ");
				sql.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
				sql.append("TITULO.OFIC_REG_ID as t_ofic_reg_id, ");
				sql.append("PRESENTANTE.APE_PAT as p_ape_pat, ");
				sql.append("PRESENTANTE.APE_MAT as p_ape_mat, ");
				sql.append("PRESENTANTE.NOMBRES as p_nombres, ");
				sql.append("PRESENTANTE.NU_DOC as p_nu_doc, ");
				sql.append("PRESENTANTE.TIPO_PER as tipo_per, ");
				sql.append("PRESENTANTE.NOMBRE_INST as raz_soc, "); 
				sql.append("TITULO.TS_PRESENT as t_ts_present, ");
				sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_titulo_id, ");
				sql.append("TM_ESTADO_TITULO.ESTADO as tet_estado, ");
				sql.append("TITULO.AREA_REG_ID as t_area_reg_id, ");
				sql.append("TM_AREA_REGISTRAL.NOMBRE as tar_nombre, ");
				sql.append("TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
				sql.append("TM_ACTO.DESCRIPCION as ta_descripcion, ");
				sql.append("TM_INST_SIR.NOMBRE_INST as empresa, ");
				sql.append("DETALLE_TITULO.MONTO_LIQ as dt_monto_liq ");
				
				sql.append("FROM TITULO, PRESENTANTE, TM_INST_SIR, DETALLE_TITULO, TM_ESTADO_TITULO, ACTOS_TITULO, TM_ACTO, OFIC_REGISTRAL, TM_AREA_REGISTRAL ");

				sql.append("WHERE PRESENTANTE.PRES_REG_PUB_ID = TITULO.REG_PUB_ID ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.OFIC_REG_ID ");
				sql.append("AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES ");
				sql.append("AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
				sql.append("AND PRESENTANTE.sistema_id = TITULO.sistema_id ");
				sql.append("AND DETALLE_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU ");
				sql.append("AND DETALLE_TITULO.FG_ACTIVO = '1' ");
				sql.append("AND TM_ESTADO_TITULO.ESTADO_TITULO_ID = DETALLE_TITULO.ESTADO_TITULO_ID ");
				sql.append("AND ACTOS_TITULO.REFNUM_TITU(+) = TITULO.REFNUM_TITU ");
				sql.append("AND TM_ACTO.COD_ACTO(+) = ACTOS_TITULO.COD_ACTO ");
				sql.append("AND TITULO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
				sql.append("AND TITULO.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID ");
				sql.append("AND TM_AREA_REGISTRAL.AREA_REG_ID = TITULO.AREA_REG_ID ");
				sql.append("AND TM_AREA_REGISTRAL.ESTADO = '1' ");
				sql.append("AND PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES(+) "); 
				sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID(+) "); 
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID(+) ");
				sql.append("AND TITULO.ANO_TITU = '").append(aux2).append("' ");
				sql.append("AND TITULO.NUM_TITU = '").append(aux3).append("' ");	
				sql.append("AND TITULO.AREA_REG_ID = '").append(aux5).append("' ");
				/*
				 * Inicio:jascencio:12/07/07
				 * CC: REGMOBCON-2006
				 */
				if(aux3!=null){
					if(Double.parseDouble(aux3)<Double.parseDouble(Constantes.NUM_TITULO_RMC)){
						sql.append("AND TITULO.REG_PUB_ID = '").append(aux1).append("' ");
						sql.append("AND TITULO.OFIC_REG_ID = '").append(aux4).append("' ");
						
					}
				}
				/*
				 * Fin:jascencio
				 */
				
								
				sql.append("ORDER BY TITULO.NUM_TITU, TITULO.ANO_TITU ");
				
				if (isTrace(this)) System.out.print(">> SQL : " + sql.toString());
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql.toString());
				
				java.util.List listaActRegistrales = new java.util.ArrayList();
				pst1 = null;
				rs1 = null;
				java.util.Date date = null; 
				boolean primera_vez = true;
				String ssql = null;
				boolean estadoAnteriorTachado=false;
				String estadoAnterior="";
				
				while(rs.next()){
					detalle = new DetalleTituloBean();
					
					if(primera_vez)
					{
						if(!user.getFgInterno())
						{
							/**2004/05/10 Kuma Se comento para que no se vaya en caso el estado sea 70 **/
							/*if(rs.getString("dt_estado_titulo_id") != null && rs.getString("dt_estado_titulo_id").equals("70")){
								ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "Este titulo ya fue recogido");
								transition("muestra", request, response);
								return response;
							}
							*/
							/**2004/05/10 Kuma Fin**/
						}
						else
						{
							if(rs.getString("dt_estado_titulo_id") != null && rs.getString("dt_estado_titulo_id").equals("70"))
							{
								mensaje_adicional = " ";
								ssql = "SELECT MAX(NS_DETALLE) FROM DETALLE_TITULO WHERE REFNUM_TITU = ? AND FG_ACTIVO = '0' ";
								pst1 = conn.prepareStatement(ssql);
								pst1.setLong(1, rs.getLong("t_refnum_titu"));
								rs1 = pst1.executeQuery();
								long auxi1 = rs1.next()?rs1.getLong(1):0;
								
								ssql = "SELECT TET.ESTADO, TET.ESTADO_TITULO_ID FROM DETALLE_TITULO DT, TM_ESTADO_TITULO TET WHERE DT.ESTADO_TITULO_ID = TET.ESTADO_TITULO_ID AND DT.REFNUM_TITU = ? AND DT.NS_DETALLE = ?";
								pst1 = conn.prepareStatement(ssql);
								pst1.setLong(1, rs.getLong("t_refnum_titu"));
								pst1.setLong(2, auxi1);
								rs1 = pst1.executeQuery();
								
								if(rs1.next()){
									estadoAnterior = rs1.getString(1);
									long idEstado = rs1.getLong(2);
									mensaje_adicional = new StringBuffer(" ").append(estadoAnterior).toString();
									if(idEstado == 50)
										estadoAnteriorTachado = true;
								}
							}
						}

						detalle.setNum_titulo(rs.getString("t_num_titu"));
						detalle.setAno_titulo(rs.getString("t_ano_titu"));
						detalle.setOficina(rs.getString("or_nombre"));
						
						if(Double.parseDouble(detalle.getNum_titulo())>Double.parseDouble(Constantes.NUM_TITULO_RMC)){
							detalle.setRmc("1");
						}
						else{
							detalle.setResultado("0");
						}
						
						/**2004/05/10 Kuma Se cambia el metodo de recuperacion del mensaje para vehicular, ahora depende del pu_ctrl y el es_titu_cali**/
						//detalle.setMensaje(rs.getString("tet_mensaje"));
						String areaRegID = rs.getString("t_area_reg_id");
						String esTituCali = rs.getString("es_titu_cali");
					   esta = esTituCali;
					   
						if(esTituCali == null) esTituCali="";
						String puCtrl = rs.getString("pu_ctrl");
						ctrl = puCtrl;
						if(puCtrl == null) puCtrl = "";
						/**
						 * rmc:20071015
						 * Se va recuperar siempre el mansaje de 
						 */
						/*
						if(false)
						{
							detalle.setMensaje(mensajeTitulo(esTituCali,puCtrl));
						}
						else
						{
						*/
						//inicio mgarate
						//detalle.setMensaje(rs.getString("tet_mensaje"));
						if(areaRegID.equals("24000"))
						{
							detalle.setMensaje(mensajeTitulo(esTituCali,puCtrl));
						}
						else
						{
							detalle.setMensaje(rs.getString("tet_mensaje"));
						}
						//fin mgarate
						/*
						}
						*/
						/**2004/05/10 Kuma Fin**/
						
						date = rs.getDate("t_fec_venc");
						if(date != null)
							detalle.setVencimiento(FechaUtil.dateToString(date));
						else
							detalle.setVencimiento(" ");
						
						//Obteniendo el nombre del Presentante
						/*
							StringBuffer lll = new StringBuffer("");
							if(rs.getString("p_cur_pres") != null) {
								ssql = "SELECT NOMBRE_INST FROM TM_INST_SIR WHERE CUR_PRES = ? AND REG_PUB_ID = ? AND OFIC_REG_ID = ? ";
								pst1 = conn.prepareStatement(ssql);
								pst1.setString(1, rs.getString("p_cur_pres"));
								pst1.setString(2, rs.getString("t_reg_pub_id"));
								pst1.setString(3, rs.getString("t_ofic_reg_id"));
								rs1 = pst1.executeQuery();
								if(rs1.next())
									if(rs1.getString(1) != null)
										lll.append(rs1.getString(1)).append("  ").toString();
							}*/
							
						String stipoper = new String (rs.getString("tipo_per")==null?"":rs.getString("tipo_per"));
						StringBuffer lll = new StringBuffer("");
						if (stipoper.equals("N")){
							
							if(rs.getString("p_ape_pat") != null)
								lll.append(rs.getString("p_ape_pat")).append(" ");
							if(rs.getString("p_ape_mat") != null)
								lll.append(rs.getString("p_ape_mat")).append(" ");
							if(rs.getString("p_nombres") !=null)
								lll.append(rs.getString("p_nombres"));
						}		
						else{
							if(rs.getString("p_ape_pat") != null)
								lll.append(rs.getString("p_ape_pat")).append(" ");
							if(rs.getString("p_ape_mat") != null)
								lll.append(rs.getString("p_ape_mat")).append(" ");
							if(rs.getString("p_nombres") !=null)
								lll.append(rs.getString("p_nombres"));
															
							lll.append(" ");	
							if(rs.getString("raz_soc") !=null)
								lll.append(rs.getString("raz_soc"));
							else	
								lll.append(rs.getString("empresa") == null?"":rs.getString("empresa"));
														
						}		
							
							detalle.setPresentante_nom(lll.toString());
							detalle.setPresentante_num_doc(rs.getString("p_nu_doc"));
						/*Fin: Obteniendo el nombre del Presentante*/
						
						//fecha presentacion
						java.sql.Timestamp ts_present = rs.getTimestamp("t_ts_present");
						
						if(ts_present != null)
							detalle.setFecha(FechaUtil.dateTimeToString(ts_present));
						else
							detalle.setFecha(" ");
						
						//fecha ultima sincronización
						java.sql.Timestamp ts_ult_syn = rs.getTimestamp("ts_ult_sync");
						
						if(ts_ult_syn != null)
							detalle.setFecha_ult_sinc(FechaUtil.dateTimeToString(ts_ult_syn));
						else
							detalle.setFecha_ult_sinc(" ");
						
						
						//detalle.setFecha_ult_sinc(FechaUtil.getCurrentDateTime());
						
						String estId = rs.getString("tet_estado_titulo_id");
						String est =  new StringBuffer(rs.getString("tet_estado")).append(mensaje_adicional).toString();
						String refNumTitu = rs.getString("t_refnum_titu");
						
						/*Obteniendo Resultado y Esquela*/
						/**2004/05/11 Kuma El resultado de la calificacion depende ahora del puCtl y el esTituCali para vehicular**/
						if(areaRegID.equals("24000"))
						{
							
							
							
							detalle.setResultado(resultadoTitulo(esTituCali,puCtrl));
							if( puCtrl.equals("06") || puCtrl.equals("07") || puCtrl.equals("08") || puCtrl.equals("09") || puCtrl.equals("14") || puCtrl.equals("15") )
							{
								if(esTituCali.equalsIgnoreCase("31") || puCtrl.equals("14") || puCtrl.equals("15"))
								{
									StringBuffer parts = new StringBuffer();
									ssql = "SELECT P.NUM_PARTIDA FROM PARTIDA P, ASIENTO A WHERE P.REFNUM_PART = A.REFNUM_PART AND P.REG_PUB_ID = ? AND P.OFIC_REG_ID = ? AND A.NUM_TITU = ? AND A.AA_TITU = ? AND P.AREA_REG_ID = ?";
									pst1 = conn.prepareStatement(ssql);
									pst1.setString(1, rs.getString("t_reg_pub_id"));
									pst1.setString(2, rs.getString("t_ofic_reg_id"));
									pst1.setString(3, rs.getString("t_num_titu"));
									pst1.setString(4, rs.getString("t_ano_titu"));
									pst1.setString(5, areaRegID);
									rs1 = pst1.executeQuery();
										
									if(!rs1.next())
										parts.append(" en la partida: ---");
									else
									{
										String auxil = rs1.getString(1);
										if(!rs1.next())
											parts.append(" en la partida: ").append(auxil);
										else
										{
											parts.append(" en las partidas: ").append(auxil).append(" ").append(rs1.getString(1));
											while(rs1.next())
												parts.append(" ").append(rs1.getString(1));
										}
									}
									detalle.setResultado(detalle.getResultado() + parts.toString());
								}
							}
							if(esTituCali.equalsIgnoreCase("32"))
							{
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "T", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}
							if(esTituCali.equalsIgnoreCase("33"))
							{
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}
							if(esTituCali.equalsIgnoreCase("39"))
							{
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}
						}
						else
						{
						/**2004/05/11 Kuma Fin**/
							if(estId.equalsIgnoreCase("60"))
							{
								ssql = "SELECT P.NUM_PARTIDA FROM PARTIDA P, ASIENTO A WHERE P.REFNUM_PART = A.REFNUM_PART AND P.REG_PUB_ID = ? AND P.OFIC_REG_ID = ? AND A.NUM_TITU = ? AND A.AA_TITU = ? AND P.AREA_REG_ID = ?";
								pst1 = conn.prepareStatement(ssql);
								pst1.setString(1, rs.getString("t_reg_pub_id"));
								pst1.setString(2, rs.getString("t_ofic_reg_id"));
								pst1.setString(3, rs.getString("t_num_titu"));
								pst1.setString(4, rs.getString("t_ano_titu"));
								pst1.setString(5, areaRegID);
								rs1 = pst1.executeQuery();
									
									if(!rs1.next())
										detalle.setResultado(est + " en la partida: ---");
									else{
										StringBuffer p = new StringBuffer(est);
										String auxil = rs1.getString(1);
										if(!rs1.next())
											p.append(" en la partida: ").append(auxil);
										else{
											p.append(" en las partidas: ").append(auxil).append(" ").append(rs1.getString(1));
											while(rs1.next())
												p.append(" ").append(rs1.getString(1));
										}
										detalle.setResultado(p.toString());	
									}
							}else if(estId.equalsIgnoreCase("50")){
								detalle.setResultado(est);
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "T", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estId.equalsIgnoreCase("40")){
								detalle.setResultado(est);
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estId.equalsIgnoreCase("30")){
								detalle.setResultado(est + " con monto igual " + rs.getDouble("dt_monto_liq"));
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "L", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estId.equalsIgnoreCase("100")){
								detalle.setResultado(est);
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estId.equalsIgnoreCase("120")){
								detalle.setResultado(est);
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estId.equalsIgnoreCase("90")){
								detalle.setResultado(est + " con monto igual " + rs.getDouble("dt_monto_liq"));
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "L", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estId.equalsIgnoreCase("140")){
								detalle.setResultado(est);
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "T", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estId.equalsIgnoreCase("130")){
								ssql = "SELECT RAZ_SOC_RES FROM RESERVA_RZ_SOC WHERE REFNUM_TITU = ? ";
								pst1 = conn.prepareStatement(ssql);
								pst1.setString(1, refNumTitu);
								rs1 = pst1.executeQuery();
								
								if(rs1.next())
									detalle.setResultado(est + " RAZON SOCIAL RESERVADA: " + rs1.getString(1));
								else
									detalle.setResultado(est + " RAZON SOCIAL RESERVADA: -");
									
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "R", areaRegID, false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else if(estadoAnteriorTachado){
								detalle.setResultado(new StringBuffer(estadoAnterior).append(mensaje_adicional).toString());
								String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "T", areaRegID,  false);
								detalle.setUrl(paramEsq);
								detalle.setDesc_url("[ VER ESQUELA ]");
							}else
								detalle.setResultado(est);
						/**2004/05/11 Kuma Cerrando llave if(areaRegID.equals("24000"))**/
						}
						/**2004/05/11 Kuma Fin**/
								
						/*Obteniendo Resultado y Esquela*/
						detalle.setTipoRegistro(rs.getString("tar_nombre"));
						area_registral = rs.getString("tar_area_reg_id");
						detalle.setAreaRegistral(area_registral);
						//ExpressoHttpSessionBean.getRequest(request).setAttribute("areaReg", area_registral);
						primera_vez = false;
						/*
						 * Inicio:jascencio:12/07/07
						 * CC: REGMOBCON-2006
						 */
						if(Double.parseDouble(aux3) >= Double.parseDouble(Constantes.NUM_TITULO_RMC)){
							List listaPartidasBloq=listadopartidasBloqueadas(conn, stmt, rs, detalle);
							ExpressoHttpSessionBean.getRequest(request).setAttribute("listaPartidasBloqueadas", listaPartidasBloq);
						}	
						/*
						 * Fin:jascencio
						 */
						ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", detalle);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fecha_imp", FechaUtil.getCurrentDateTime());
						
						
						Job003 j = new Job003();

						j.setUsuario(user);
						j.setCodigoServicio(TipoServicio.CONSULTA_TITULOS);
						j.setRegPubId(aux1);
						j.setOficRegId(aux4);
						j.setArea(area_registral);
								
						Thread llamador1 = new Thread(j);
						llamador1.start();
								
						//POSIBILIDAD 2					
						//log_transaction
						LogAuditoriaConsulTituloBean logbean = new LogAuditoriaConsulTituloBean();
						//_datos genericos
						logbean.setCodigoServicio(TipoServicio.CONSULTA_TITULOS);
						logbean.setUsuarioSession(user);
												//Modificado por: Proyecto Filtros de Acceso
												//Fecha: 02/10/2006
												//logbean.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
												logbean.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
												//Fecha: 08/10/2006             
												logbean.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
												//Fin Modificación
						//_datos especificos
						logbean.setOficRegId(aux4);
						logbean.setRegPubId(aux1);
						logbean.setAnoTitulo(aux2);
						logbean.setNumTitulo(aux3);
						/*
						Job004 j4b = new Job004();
					j4b.setBean(logbean);
					Thread llamador2b = new Thread(j4b);
					llamador2b.start();									
						*/
				if (Propiedades.getInstance().getFlagTransaccion()==true)
						Transaction.getInstance().registraTransaccion(logbean,conn);
						conn.commit();
																	
					}//if(primera_vez)
					listaActRegistrales.add(rs.getString("ta_descripcion"));
				}//for(Iterator i = multi.searchAndRetrieve(orde
			
			
			
				if(!primera_vez){
					StringBuffer sqlb = new StringBuffer("SELECT ");
					sqlb.append("IPT.NS_PERS_NATU as ipt_ns_pers_natu, ");
					sqlb.append("IPT.APE_PAT as ipt_ape_pat, ");
					sqlb.append("IPT.APE_MAT as ipt_ape_mat, ");
					sqlb.append("IPT.NOMBRES as ipt_nombres ");
					sqlb.append("FROM TITULO T, IND_PN_PARTIC_TITU IPT ");
					sqlb.append("WHERE T.REG_PUB_ID = IPT.COD_REG ");
					sqlb.append("AND T.OFIC_REG_ID = IPT.COD_OFIC_REG ");
					sqlb.append("AND T.NUM_TITU = IPT.NU_TITU ");
					sqlb.append("AND T.ANO_TITU = IPT.AA_TITU ");
					sqlb.append("AND T.SISTEMA_ID = IPT.SISTEMA_ID ");
					sqlb.append("AND T.AREA_REG_ID = '").append(area_registral).append("' ");
					sqlb.append("AND T.ANO_TITU = '").append(aux2).append("' ");
					sqlb.append("AND T.NUM_TITU = '").append(aux3).append("' ");
					/*
					 * Inicio:jascencio:12/07/07
					 * CC: REGMOBCON-2006
					 */
					if(aux3!=null){
						if(Double.parseDouble(aux3)<Double.parseDouble(Constantes.NUM_TITULO_RMC)){
							sqlb.append("AND T.OFIC_REG_ID = '").append(aux4).append("' ");
							sqlb.append("AND T.REG_PUB_ID = '").append(aux1).append("' ");
							
						}
					}
					/*
					 * Fin:jascencio
					 */
					
					if (isTrace(this)) System.out.print(">> SQL : " + sqlb.toString());
					java.sql.Statement stmt1 = null;
					java.util.List listaActEntidades  = new java.util.ArrayList();
					DetalleTituloEntidadBean dettitent = null;
					
					stmt1 = conn.createStatement();
					rs1 = stmt1.executeQuery(sqlb.toString());
					String nombreCompleto="";
					String nombreAnterior="";
					
					while(rs1.next()){
						dettitent = new DetalleTituloEntidadBean();
						dettitent.setTipo(new StringBuffer(String.valueOf(rs1.getLong("ipt_ns_pers_natu"))).append(" - PN").toString());
						
						StringBuffer nombres = new StringBuffer("");
						
						if(rs1.getString("ipt_ape_pat") != null)
							nombres.append(rs1.getString("ipt_ape_pat")).append(" ");
						if(rs1.getString("ipt_ape_mat") != null)
							nombres.append(rs1.getString("ipt_ape_mat")).append(" ");
						if(rs1.getString("ipt_nombres") != null)
							nombres.append(rs1.getString("ipt_nombres"));
						
						nombreCompleto = nombres.toString();
						dettitent.setEntidad(nombreCompleto);
																			
						if (!nombreAnterior.equals(nombreCompleto)) //esto se hace para que no se muestre el mismo participante en el JSP asi sea de otro acto
							listaActEntidades.add(dettitent);
						nombreAnterior=nombreCompleto;
					}
			
						sqlb = new StringBuffer("SELECT ");
						sqlb.append("IPT.NS_PERS_JURI as ipt_ns_pers_juri, ");
						sqlb.append("IPT.RAZ_SOC as ipt_raz_soc ");
	
						sqlb.append("FROM TITULO T, IND_PJ_PARTIC_TITU IPT ");
	
						sqlb.append("WHERE T.REG_PUB_ID = IPT.REG_PUB_ID ");
						sqlb.append("AND T.OFIC_REG_ID = IPT.OFIC_REG_ID ");
						sqlb.append("AND T.NUM_TITU = IPT.NU_TITU ");
						sqlb.append("AND T.ANO_TITU = IPT.AA_TITU ");
						sqlb.append("AND T.SISTEMA_ID = IPT.SISTEMA_ID ");						
						sqlb.append("AND T.ANO_TITU = '").append(aux2).append("' ");
						sqlb.append("AND T.NUM_TITU = '").append(aux3).append("' ");
						sqlb.append("AND T.AREA_REG_ID = '").append(area_registral).append("' ");
						
						/*
						 * Inicio:jascencio:12/07/07
						 * CC: REGMOBCON
						 */
						
						if(aux3!=null){
							if(Double.parseDouble(aux3)<Double.parseDouble(Constantes.NUM_TITULO_RMC)){
								sqlb.append("AND T.OFIC_REG_ID = '").append(aux4).append("' ");
								sqlb.append("AND T.REG_PUB_ID = '").append(aux1).append("' ");
							}
						}
						/*
						 * Fin:jascencio
						 */
						if (isTrace(this)) System.out.print(">> SQL : " + sqlb.toString());
						
						stmt1 = conn.createStatement();
						rs1 = stmt1.executeQuery(sqlb.toString());
						
									
						String razonSocialAnterior="";
						String razonSocialActual="";
						while(rs1.next()){
							dettitent = new DetalleTituloEntidadBean();
									
							dettitent.setTipo(new StringBuffer(String.valueOf(rs1.getLong("ipt_ns_pers_juri"))).append(" - PJ").toString());
							razonSocialActual = rs1.getString("ipt_raz_soc");
							dettitent.setEntidad(razonSocialActual);
									
							if (!razonSocialActual.equals(razonSocialAnterior)) //esto se hace para que no se muestre el mismo participante en el JSP asi sea de otro acto
								listaActEntidades.add(dettitent);
							razonSocialAnterior=razonSocialActual;
						}
			
						//	Seteando Valores
							ExpressoHttpSessionBean.getRequest(request).setAttribute("listaRegistrales", listaActRegistrales);
							ExpressoHttpSessionBean.getRequest(request).setAttribute("listaEntidades", listaActEntidades);
					/**	jbugarin 23/02/2007 **/
							 DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
							 dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
							 dboRegisPublico.setField(DboRegisPublico.CAMPO_REG_PUB_ID,aux1);
							 if(dboRegisPublico.find()== true){
							 descZona = dboRegisPublico.getField(DboRegisPublico.CAMPO_NOMBRE); 
							 }

							 ExpressoHttpSessionBean.getRequest(request).setAttribute("estado",esta);
							 ExpressoHttpSessionBean.getRequest(request).setAttribute("ctrl",ctrl);
							 ExpressoHttpSessionBean.getRequest(request).setAttribute("zonaRegistral",descZona);
			
					  
							 /** jbugarin fin**/		        
					       
						response.setStyle("detalle");
						conn.commit();
					}//if(!primera_vez)
				else
				{
					ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "Este titulo no existe, por favor verifique el numero de titulo");
					transition("muestra", request, response);
				}//if(!primera_vez)
			}//if(!esTituloPresentado)
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

	/**
	 * Consulta de Estado de Títulos --> Buscar X Nro Título
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	protected ControllerResponse runBuscarXNroTituloState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		java.sql.Statement stmt = null;
		java.sql.Statement stmt1 = null;
		//java.sql.ResultSet rs = null;
		ResultSet rset = null;
		java.sql.PreparedStatement pst1 = null;
		java.sql.ResultSet rs1 = null;
		//jbugarin
		String esta = "";
		String ctr  = "";	

		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

		try {
			init(request);			
			validarSesion(request);
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
					
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			//
			int num_pagina = Propiedades.getInstance().getLineasPorPag();	
			
			int paginacion = 1;

			//if (request.getParameter("pagina")!=null)
			try{
				paginacion=Integer.parseInt(request.getParameter("pagina"));
			} catch (NumberFormatException e) {
			}
			//System.out.println("paginacion:"+paginacion);
			//
			
			String aux[] = new String[3];
			StringTokenizer stk = null;
			String area_registral = null;
			String mensaje_adicional = "";
			String valOrden = request.getParameter("orden")==null?"":request.getParameter("orden");

			if(!valOrden.equalsIgnoreCase("on"))
			{
				try {
					stk = new StringTokenizer(request.getParameter("oficinas"), "|");
					int y = 0;
	
					while (stk.hasMoreTokens()) {
						aux[y++] = stk.nextToken();
					}
				} catch (Exception e1) {
					throw new CustomException(
						Errors.EC_MISSING_PARAM,
						"Error al obtener la oficina registral",
						"errorTitulo");
				}
			}
			
			String aux1 = "";
			if(!valOrden.equalsIgnoreCase("on"))	
				aux1 = aux[0]; //RegPubId
			else
				aux1 = request.getParameter("regPub");	
				
			String aux2 = request.getParameter("ano");
			String aux3 = request.getParameter("numtitu");
			
			String aux4 = "";
			if(!valOrden.equalsIgnoreCase("on"))
				aux4 = aux[1]; //OficRegId
			else
				aux4 = request.getParameter("ordOficina");	



			boolean pase = true;

			if (aux3 == null || aux3.trim().length() <= 0)
					throw new CustomException(
						Errors.EC_MISSING_PARAM,
						"Debe ingresar un Numero de Titulo a buscar",
						"errorTitulo");

			DetalleTituloBean detalle = null;

			//Preguntando si Estado de Titulo es 10
			boolean esTituloPresentado = false;
			detalle = Generales.esTituloPresentado(aux3, aux2, aux1, aux4, dconn);

			if (detalle == null)
					esTituloPresentado = false;
			else {
				esTituloPresentado = true;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("presentado", "X");
				ExpressoHttpSessionBean.getRequest(request).setAttribute(
					"oficina",
					request.getParameter("CboOficinas"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute(
					"ano",
					request.getParameter("ano"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", detalle);
				response.setStyle("detalle");
			}
			
			
		//Recuperamos variable de Ordenamiento			
			String h = request.getParameter("orden");
			boolean ordenarXEstado = false;
			if(h == null || h.trim().length() <= 0)
				ordenarXEstado = false;
			else if(h.equalsIgnoreCase("on"))
				ordenarXEstado = true;
			else
				ordenarXEstado = false;

			//String aux[] = new String[3];
			//String aux1 = aux[0];
			//String aux4 = aux[1];

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "7");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", aux2);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numtitu", aux3);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ordOficina", aux4);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("regPub", aux1);
			
			// Agregado JACR - inicio
			//COUNT		
			long tamano = 0;
			StringBuffer sqlc = new StringBuffer();
			boolean hacerCount = false;
			
			if(paginacion == 1)
				hacerCount = true;

		if(hacerCount){
			sqlc.append("SELECT ");
			sqlc.append("/*+ORDERED INDEX(TITULO INDEX_TITU_REG_ANO_TITU) */ ");
			sqlc.append("count(TITULO.REFNUM_TITU)");
			sqlc.append("FROM TITULO ");
			sqlc.append("WHERE ");
			sqlc.append("TITULO.ANO_TITU='").append(aux2).append("' AND ");
			sqlc.append("TITULO.NUM_TITU='").append(aux3).append("' ");
			
			/*
			 * Inicio:jascencio:12/07/07
			 * CC: REGMOBCON
			 */
			if(aux3!=null){
				if(Integer.parseInt(aux3)<Integer.parseInt(Constantes.NUM_TITULO_RMC)){
					sqlc.append("AND TITULO.OFIC_REG_ID = '").append(aux4).append("' ");
					sqlc.append("AND TITULO.REG_PUB_ID = '").append(aux1).append("' ");
				}
			}
			/*
			 * Fin:jascencio
			 */
			
			if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset   = stmt.executeQuery(sqlc.toString());
			rset.next();
			tamano = rset.getLong(1);
			
			if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda())
				throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
		}else
			tamano = Long.parseLong(request.getParameter("tamano"));	
//	 Agregado JACR - fin

//			if (!esTituloPresentado) {

				StringBuffer sql = new StringBuffer("SELECT ");
				sql.append(
					"/*+ORDERED INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
				//sql.append("DISTINCT ");
				sql.append("DETALLE_TITULO.ESTADO_TITULO_ID as dt_estado_titulo_id, ");
				/**2004/05/11 Kuma Se agregaron estos dos campos **/
				sql.append("DETALLE_TITULO.PU_CTRL as pu_ctrl, ");
				sql.append("DETALLE_TITULO.ES_TITU_CALI as es_titu_cali, ");
				/**2004/05/11 Kuma Fin **/
				sql.append("TITULO.NUM_TITU as t_num_titu, ");
				sql.append("TITULO.ANO_TITU as t_ano_titu, ");
				sql.append("OFIC_REGISTRAL.NOMBRE as or_nombre, ");
				sql.append("TM_ESTADO_TITULO.MENSAJE as tet_mensaje, ");
				sql.append("TITULO.REFNUM_TITU as t_refnum_titu, ");
				sql.append("TITULO.FEC_VENC as t_fec_venc, ");
				sql.append("PRESENTANTE.CUR_PRES as p_cur_pres, ");
				sql.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
				sql.append("TITULO.OFIC_REG_ID as t_ofic_reg_id, ");
				sql.append("PRESENTANTE.APE_PAT as p_ape_pat, ");
				sql.append("PRESENTANTE.APE_MAT as p_ape_mat, ");
				sql.append("PRESENTANTE.NOMBRES as p_nombres, ");
				sql.append("PRESENTANTE.NU_DOC as p_nu_doc, ");
				sql.append("TITULO.TS_PRESENT as t_ts_present, ");
				sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_titulo_id, ");
				sql.append("TM_ESTADO_TITULO.ESTADO as tet_estado, ");
				sql.append("TITULO.AREA_REG_ID as t_area_reg_id, ");
				sql.append("TM_AREA_REGISTRAL.NOMBRE as tar_nombre, ");
				sql.append("TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
				//sql.append("TM_ACTO.DESCRIPCION as ta_descripcion, ");
				//
				sql.append("REGIS_PUBLICO.NOMBRE as rp_nombre, ");
				sql.append("PRESENTANTE.TIPO_PER as tipo_per, ");
				sql.append("PRESENTANTE.NOMBRE_INST as raz_soc, ");
				sql.append("TM_INST_SIR.NOMBRE_INST as empresa, ");
				sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_t_id, ");
				//
				sql.append("DETALLE_TITULO.MONTO_LIQ as dt_monto_liq ");

				sql.append(
				//	"FROM TITULO, PRESENTANTE, DETALLE_TITULO, TM_ESTADO_TITULO, ACTOS_TITULO, TM_ACTO, OFIC_REGISTRAL, TM_AREA_REGISTRAL, REGIS_PUBLICO, TM_INST_SIR ");
					"FROM TITULO, PRESENTANTE, DETALLE_TITULO, TM_ESTADO_TITULO, OFIC_REGISTRAL, TM_AREA_REGISTRAL, REGIS_PUBLICO, TM_INST_SIR ");

				sql.append("WHERE TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID(+) ");
				sql.append("AND TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID(+) ");
				sql.append("AND TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES(+) ");
				sql.append("AND TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES(+) ");
				sql.append("AND DETALLE_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU ");
				sql.append("AND DETALLE_TITULO.FG_ACTIVO = '1' ");
				sql.append("AND TM_ESTADO_TITULO.ESTADO_TITULO_ID = DETALLE_TITULO.ESTADO_TITULO_ID ");
				//sql.append("AND ACTOS_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU ");
				//sql.append("AND TM_ACTO.COD_ACTO = ACTOS_TITULO.COD_ACTO ");
				sql.append("AND TITULO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
				sql.append("AND TITULO.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID ");
				sql.append("AND TM_AREA_REGISTRAL.AREA_REG_ID = TITULO.AREA_REG_ID ");
				sql.append("AND TM_AREA_REGISTRAL.ESTADO = '1' ");
				//
				sql.append("AND REGIS_PUBLICO.REG_PUB_ID = TITULO.REG_PUB_ID ");
				sql.append("AND PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES(+) ");
				sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID(+) ");
				sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID(+) ");	
				//Inicio:jascencio:14/09/2007
				//CC: SUNARP-REGMOBCON-2006
				sql.append("AND PRESENTANTE.sistema_id = TITULO.sistema_id ");
				//Fin:jascencio
				//
				sql.append("AND TITULO.ANO_TITU = '").append(aux2).append("' ");
				sql.append("AND TITULO.NUM_TITU = '").append(aux3).append("' ");

				//sql.append("ORDER BY TITULO.NUM_TITU, TITULO.ANO_TITU ");
				
				/*
				 * Inicio:jascencio:12/07/07
				 * CC: REGMOBCON-2006
				 */
				if(aux3!=null){
					if(Integer.parseInt(aux3)<Integer.parseInt(Constantes.NUM_TITULO_RMC)){
						sql.append("AND TITULO.OFIC_REG_ID = '").append(aux4).append("' ");
						sql.append("AND TITULO.REG_PUB_ID = '").append(aux1).append("' ");
					}
				}
				/*
				 * Fin:jascencio
				 */

				StringBuffer orden = null;
				if(ordenarXEstado){
					orden = new StringBuffer(" ORDER BY TM_ESTADO_TITULO.ESTADO");
					ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
				}else{
					orden = new StringBuffer(" ORDER BY OFIC_REGISTRAL.NOMBRE, TITULO.ANO_TITU DESC, TITULO.NUM_TITU DESC");
					ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);
				}
			
				sql.append(" ").append(orden);



				if (isTrace(this))
						System.out.print(">> SQL : " + sql.toString());
				//stmt = conn.createStatement();
				//rs = stmt.executeQuery(sql.toString());
				
				//
				Propiedades propiedades = Propiedades.getInstance();
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(propiedades.getLineasPorPag());
				//*** MANEJO DE LA PAGINACION
				rset = stmt.executeQuery(sql.toString());

				//java.util.List listaActRegistrales = new java.util.ArrayList();
				pst1 = null;
				rs1 = null;
				//java.util.Date date = null;
				boolean primera_vez = true;
				String ssql = null;
	
		  if (paginacion > 1)
			rset.absolute(propiedades.getLineasPorPag() * (paginacion - 1));
	
		  int conta=1;
		  boolean hayNext = false;
		  boolean encontro = false;
		  boolean b = rset.next();
		  java.util.List lista1 = new java.util.ArrayList();
	      
		  while (b==true && conta <= propiedades.getLineasPorPag()){
			  encontro = true;
			  conta++;			
			
				GeneralTituloBean bean = new GeneralTituloBean();
				bean.setSede(rset.getString("rp_nombre")==null?"---":rset.getString("rp_nombre"));
				bean.setDependencia(rset.getString("or_nombre")==null?"---":rset.getString("or_nombre"));
				bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
				bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
				//bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":rset.getString("tar_nombre"));
				
				/*
				 * Inicio:jascencio:12/07/07
				 * CC: REGMOBCON-2006
				 */
				if(aux3!=null){
					if(Integer.parseInt(aux3)<Integer.parseInt(Constantes.NUM_TITULO_RMC)){
						bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":rset.getString("tar_nombre"));
					}
					else{
						bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":"Registro Mobiliario de Contratos");
					}
				}
				/*
				 * Fin:jascencio
				 */
				
				//date = rset.getDate("t_ts_present");
				//bean.setFec_presentacion(FechaUtil.dateToString(date));
				
				//date = rset.getDate("t_fec_venc");
				//bean.setFec_vencimiento(FechaUtil.dateToString(date));


			//fecha presentacion
			java.sql.Timestamp ts_present = rset.getTimestamp("t_ts_present");
						
			if(ts_present != null)
				bean.setFec_presentacion(FechaUtil.dateTimeToString(ts_present));
			else
				bean.setFec_presentacion("---");

                        
			//fecha vencimiento
			java.util.Date date = rset.getDate("t_fec_venc");
				
			if(date != null)
				bean.setFec_vencimiento(FechaUtil.dateToString(date));
			else
				bean.setFec_vencimiento("---");	


				
			//cjvc77 20021218
				
			//modificado por JACR - inicio
				
				bean.setTipo_Per(rset.getString("tipo_per")== null?"-":rset.getString("tipo_per"));
				bean.setRaz_Soc(rset.getString("raz_soc"));
				bean.setArea_Reg(rset.getString("tar_area_reg_id"));
				/**2004/05/11 Kuma El estado depende de el puCtrl y el esTituCali **/
				String puCtrl = rset.getString("pu_ctrl");
				ctr=puCtrl;
				if(puCtrl == null) puCtrl = "";
				String esTituCali = rset.getString("es_titu_cali");
				esta=esTituCali;
				if(esTituCali == null) esTituCali = "";
				if(bean.getArea_Reg().equals("24000"))
				{
					bean.setEstado(resultadoTitulo(esTituCali,puCtrl));
				}
				else
				{
					bean.setEstado(rset.getString("tet_estado"));
				}
				/**2004/05/11 Kuma Fin **/
				
				bean.setUrl_detalle("");
				
				String stipoper = new String (rset.getString("tipo_per")== null?"":rset.getString("tipo_per"));
			
				StringBuffer xx = new StringBuffer();
				if (stipoper.equals("N")){
			
					if(rset.getString("empresa") != null)
						xx.append(rset.getString("empresa")).append("  ");
										
					if(rset.getString("p_ape_pat") != null)
						xx.append(rset.getString("p_ape_pat")).append(" ");
					if(rset.getString("p_ape_mat") != null)
						xx.append(rset.getString("p_ape_mat")).append(" ");
					if(rset.getString("p_nombres") != null)
						xx.append(rset.getString("p_nombres"));						
				}else{
					if(rset.getString("raz_soc") != null)
						xx.append(rset.getString("raz_soc"));
					else
						xx.append(rset.getString("empresa") == null?"---":rset.getString("empresa"));						
					
				}	
				//modificado por JACR - fin 
				
				bean.setPresentante(xx.toString().trim().equals("")?"---":xx.toString());				
				bean.setReg_pub_id(rset.getString("t_reg_pub_id"));
				bean.setOfic_reg_id(rset.getString("t_ofic_reg_id"));
//**ESQUELAS
				String estId = rset.getString("tet_estado_t_id");
				String refNumTitu = rset.getString("t_refnum_titu");
				String areaRegID = rset.getString("tar_area_reg_id");
				/**2004/05/11 Kuma El link a esquela depende de el esTituCali para vehicular**/
				if(bean.getArea_Reg().equals("24000"))
				{
					if(esTituCali.equals("32"))
					{
						esquela("50", refNumTitu, areaRegID, dconn, bean);
					}
					else if(esTituCali.equals("33"))
					{
						esquela("40", refNumTitu, areaRegID, dconn, bean);
					}
					else if(esTituCali.equals("39"))
					{
						esquela("120", refNumTitu, areaRegID, dconn, bean);
					}
					else
					{
						esquela("", refNumTitu, areaRegID, dconn, bean);
					}
					
				}
				else
				{
					esquela(estId, refNumTitu, areaRegID, dconn, bean);
				}
				/**2004/05/11 Kuma Fin**/
				
//**ESQUELAS
				area_registral = rset.getString("tar_area_reg_id");
				aux1 = rset.getString("t_reg_pub_id");
				aux4 = rset.getString("t_ofic_reg_id");
				
				bean.setArea_Reg(area_registral);

//**PARA PARTIDAS
				String auxiliarpartida = partida(conn, rset);
				bean.setPartida(auxiliarpartida);
//**PARA PARTIDAS
				StringBuffer sql1 = null;				

			// Para Actos Titulos
				actosTitulo(conn, stmt1, rs1, bean);			

			// Para Participantes
				boolean juri = participantesPN(conn, stmt1, rs1, bean);
				if(juri)
					participantesPJ(conn, stmt1, rs1, bean);
				/*
				 * Inicio:jascencio:12/07/07
				 * CC: REGMOBCON-2006
				 */
				List listaActosReg=listadoActosRegistrales(conn, stmt, rset, bean);
				bean.setListadoActos(listaActosReg);
				/*
				 * Fin:jascencio
				 */
				lista1.add(bean);
				b = rset.next();
			}
			String descZona = "";
			if(lista1.size() == 1){
				GeneralTituloBean bean = (GeneralTituloBean) lista1.get(0);
				String aux_ofic = bean.getReg_pub_id() + "|" + bean.getOfic_reg_id();
				request.setParameter("ano", bean.getAno());
				request.setParameter("numtitu", bean.getTitulo());
				request.setParameter("areareg",area_registral);
				request.setParameter("oficinas", aux_ofic);
				transition("buscarXNroTituloDet", request, response);
       
				
				
				
				
				
				return response;
			
			
			
			}

			hayNext = rset.next();			



			if(encontro)
			{
				//2 noviembre
				//log_transaction
				LogAuditoriaConsulTituloBean logbean = new LogAuditoriaConsulTituloBean();
				//_datos genericos
				logbean.setCodigoServicio(TipoServicio.CONSULTA_TITULOS);
				logbean.setUsuarioSession(user);
								//Modificado por: Proyecto Filtros de Acceso
								//Fecha: 02/10/2006
								//logbean.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
								logbean.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
								//Fecha: 08/10/2006             
								logbean.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
								//Fin Modificación
				//_datos especificos
				logbean.setOficRegId(aux4);
				logbean.setRegPubId(aux1);
				logbean.setAnoTitulo(aux2);
				logbean.setNumTitulo(aux3);
				/*
				Job004 j4c = new Job004();
					j4c.setBean(logbean);
					Thread llamador2c = new Thread(j4c);
					llamador2c.start();		
				*/
				if (Propiedades.getInstance().getFlagTransaccion()==true)
				Transaction.getInstance().registraTransaccion(logbean,conn);
				conn.commit();
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
			}
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);



			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.CONSULTA_TITULOS);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			
	//*PAGINACION EN EL JSP*//
			manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);


			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaAct",fechaAct);

						
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista1);
			response.setStyle("listado");
					

		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			closeExtra(stmt1, null);
			closeExtra(stmt, rset);
			closeExtra(pst1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
	}

	public ControllerResponse runVerFormularioState(ControllerRequest request, ControllerResponse response)
		throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		String ruta = "error";

		try {

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
			init(request);
			validarSesion(request);

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			java.util.List listaDocsId = Tarea.getComboTiposDocumento(dconn);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("listaDocsId", listaDocsId);

			java.util.List listaDocsIdPJ = Tarea.getComboTiposDocumentoPJ(dconn);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("listaDocsIdPJ", listaDocsIdPJ);

			//obtener datos para combo box, listas, etc
			ComboBean bean1 = null;

/*
			//registro publico
			DboRegisPublico dboRegisPublico = new DboRegisPublico(conn);
			dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_REG_PUB_ID+"|"+DboRegisPublico.CAMPO_NOMBRE);
			java.util.ArrayList arrx = dboRegisPublico.searchAndRetrieveList(DboRegisPublico.CAMPO_NOMBRE);
				
			java.util.ArrayList arreglo1 = new java.util.ArrayList();
			for (int i = 0; i < arrx.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(((DboRegisPublico) arrx.get(i)).getField(DboRegisPublico.CAMPO_REG_PUB_ID));
				bean1.setDescripcion(((DboRegisPublico) arrx.get(i)).getField(DboRegisPublico.CAMPO_NOMBRE));
				arreglo1.add(bean1);
			}

			//combo area registral
			DboTmAreaRegistral dboAreaRegistral = new DboTmAreaRegistral(conn);
			dboAreaRegistral.setFieldsToRetrieve(DboTmAreaRegistral.CAMPO_AREA_REG_ID+"|"+DboTmAreaRegistral.CAMPO_NOMBRE);
			dboAreaRegistral.setField(DboTmAreaRegistral.CAMPO_ESTADO, "1");
			arrx = dboAreaRegistral.searchAndRetrieveList(DboTmAreaRegistral.CAMPO_NOMBRE);
			java.util.ArrayList arreglo2 = new java.util.ArrayList();
			for (int i = 0; i < arrx.size(); i++) {
				bean1 = new ComboBean();
				bean1.setCodigo(((DboTmAreaRegistral) arrx.get(i)).getField(DboTmAreaRegistral.CAMPO_AREA_REG_ID));
				bean1.setDescripcion(((DboTmAreaRegistral) arrx.get(i)).getField(DboTmAreaRegistral.CAMPO_NOMBRE));
				arreglo2.add(bean1);
			}

*/
			//mandar arreglos al JSP
//			req.setAttribute("arreglo1", arreglo1); // sede-Zona
//			req.setAttribute("arreglo2", arreglo2); // area registral
//			req.setAttribute("arreglo5", resultado2); //  tipo participaciocn

			ruta = "verFormulario";

		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		response.setStyle(ruta);
		return response;
	}

	protected ControllerResponse runBuscarXNroTituloGState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		String area_registral = null;
		//String descZona="";
		//String ctr="";//jbugarin
		//String esta="";	//jbugarin

		//Conexiones de Base de Datos
			java.sql.Statement stmt1 = null;
			java.sql.ResultSet rs1 = null;
			
			
		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			

		try{
			init(request);
			validarSesion(request);
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			int paginacion = Integer.parseInt(request.getParameter("pagina"));
			//Recuperamos variable de Ano y Numero de Titulo
			String aux2 = request.getParameter("ano");
			String aux3 = request.getParameter("numtitu");
			//Recuperamos varible de Zonas
			String param = request.getParameter("hid1");
			StringBuffer subcadena = null;
			StringTokenizer stk;
			int numTokens = 0;
			if(aux3 != null){
				if(Integer.parseInt(aux3) < Integer.parseInt(Constantes.NUM_TITULO_RMC)){
					if(param == null || param.trim().length() <= 0)
						throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar como minimo una zona", "errorTitulo");
						
					stk = new StringTokenizer(param, "|");
					numTokens = stk.countTokens();
					if(numTokens == 1)
						subcadena = new StringBuffer(" = '").append(stk.nextToken()).append("' ");
					else if(numTokens > 1 && numTokens < 13){
						subcadena = new StringBuffer(" IN (");
						if(stk.hasMoreTokens())
							subcadena.append("\'").append(stk.nextToken()).append("\'");
						
						while (stk.hasMoreTokens()) {
							subcadena.append(", \'").append(stk.nextToken()).append("\'");
						}
						subcadena.append(") ");
					}
				}
			}
			
			int y = 0;
		

			if(aux2 == null || aux2.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar un ano", "errorTitulo");

			if(aux3 == null || aux3.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar un Número de Titulo", "errorTitulo");
			
		//Recuperamos variable de Ordenamiento			
			String h = request.getParameter("orden");
			boolean ordenarXEstado = false;
			if(h == null || h.trim().length() <= 0)
				ordenarXEstado = false;
			else if(h.equalsIgnoreCase("on"))
				ordenarXEstado = true;
			else
				ordenarXEstado = false;

			String aux[] = new String[3];
			String aux1 = aux[0];
			String aux4 = aux[1];

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "1");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hid1", param);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", aux2);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numtitu", aux3);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hid1", param);
			
		//COUNT
		long tamano = 0;
		StringBuffer sqlc = new StringBuffer();
		boolean hacerCount = false;
		
		if(paginacion == 1)
			hacerCount = true;

		if(hacerCount){
			sqlc.append("SELECT ");
			if(numTokens == 1)
				sqlc.append("/*+ORDERED INDEX(TITULO INDEX_TITU_REG_ANO_TITU) */ ");
			else if(numTokens > 1 && numTokens < 13)
				sqlc.append("/*+ORDERED INDEX(TITULO INDEX_TITU_REG_ANO_TITU) */ ");
			else
				sqlc.append("/*+ORDERED INDEX(TITULO INDEX_TITU_ANO_NRO) */ ");
			
			sqlc.append("count(TITULO.REFNUM_TITU)");
			sqlc.append("FROM TITULO ");
			sqlc.append("WHERE ");
			sqlc.append("TITULO.ANO_TITU='").append(aux2).append("' AND ");
			sqlc.append("TITULO.NUM_TITU='").append(aux3).append("' ");
			
			if(numTokens != 13)
			{	
				/*
				 *Inicio:jascencio:12/07/07 
				 *CC: REGMOBCON-2006
				 */
				if(aux3!=null){
					if(Integer.parseInt(aux3)<Integer.parseInt(Constantes.NUM_TITULO_RMC)){
						sqlc.append("AND TITULO.REG_PUB_ID").append(subcadena.toString()).append(" ");
//						sqlc.append("AND TITULO.OFIC_REG_ID = '").append(aux4).append("' ");
//						sqlc.append("AND TITULO.REG_PUB_ID = '").append(aux1).append("' ");
					}
				}
				/*
				 * Fin:jascencio
				 */
			}

			if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset   = stmt.executeQuery(sqlc.toString());
			rset.next();
			tamano = rset.getLong(1);
			
			if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda())
				throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
		}else
			tamano = Long.parseLong(request.getParameter("tamano"));
			
		//Formando QUERY			
		StringBuffer sql = new StringBuffer("SELECT ");
		
		if(numTokens == 1)
			sql.append("/*+ORDERED INDEX(TITULO INDEX_TITU_REG_ANO_TITU) INDEX (PRESENTANTE INDX_PRES) INDEX(DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
		else if(numTokens > 1 && numTokens < 13)
			sql.append("/*+ORDERED INDEX(TITULO INDEX_TITU_REG_ANO_TITU) INDEX (PRESENTANTE INDX_PRES) INDEX(DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
		else
			sql.append("/*+ORDERED INDEX(TITULO INDEX_TITU_ANO_NRO) INDEX (PRESENTANTE INDX_PRES) INDEX(DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
			 
		//--------sql.append("DISTINCT ");
		/**2004/05/11 Kuma Se agregaron estos dos campos **/
		sql.append("DETALLE_TITULO.PU_CTRL as pu_ctrl, ");
		sql.append("DETALLE_TITULO.ES_TITU_CALI as es_titu_cali, ");
		/**2004/05/11 Kuma Fin **/
		sql.append("REGIS_PUBLICO.NOMBRE as rp_nombre, ");
		sql.append("OFIC_REGISTRAL.NOMBRE as or_nombre, ");
		sql.append("TITULO.ANO_TITU as t_ano_titu, ");
		sql.append("TITULO.NUM_TITU as t_num_titu, ");
		sql.append("TM_AREA_REGISTRAL.NOMBRE as tar_nombre, ");
		sql.append("TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
		sql.append("TITULO.TS_PRESENT as t_ts_present, ");
		sql.append("TITULO.FEC_VENC as t_fec_venc, ");
		sql.append("PRESENTANTE.CUR_PRES as p_cur_pres , ");
		sql.append("PRESENTANTE.APE_PAT as p_ape_pat, ");
		sql.append("PRESENTANTE.APE_MAT as p_ape_mat, ");
		sql.append("PRESENTANTE.NOMBRES as p_nombres, ");
		sql.append("TM_ESTADO_TITULO.ESTADO as tet_estado, ");
		sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_t_id, ");
		sql.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
		sql.append("TITULO.REFNUM_TITU as t_refnum_titu, ");
		sql.append("TITULO.OFIC_REG_ID  as t_ofic_reg_id, ");
		sql.append("TM_INST_SIR.NOMBRE_INST as empresa, ");
		
		//modificado por JACR - inicio
		sql.append("PRESENTANTE.TIPO_PER as tipo_per, ");
		sql.append("PRESENTANTE.NOMBRE_INST as raz_soc ");/*se cambio el nombre del campo RAZ_SOC X NOMBRE_INST*/
		//modificado por JACR - fin 
		 
		sql.append("FROM TITULO, PRESENTANTE, TM_INST_SIR, DETALLE_TITULO, TM_AREA_REGISTRAL, TM_ESTADO_TITULO, OFIC_REGISTRAL, REGIS_PUBLICO ");
		
		sql.append("WHERE TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID(+) ");
		sql.append("AND TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID(+) ");
		sql.append("AND TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES(+) ");
		sql.append("AND TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES(+) ");
		sql.append("AND TITULO.REFNUM_TITU = DETALLE_TITULO.REFNUM_TITU ");
		sql.append("AND TITULO.sistema_id = PRESENTANTE.sistema_id ");
		sql.append("AND DETALLE_TITULO.FG_ACTIVO = '1' ");
		sql.append("AND DETALLE_TITULO.ESTADO_TITULO_ID=TM_ESTADO_TITULO.ESTADO_TITULO_ID ");
		sql.append("AND TITULO.AREA_REG_ID = TM_AREA_REGISTRAL.AREA_REG_ID ");
		sql.append("AND TITULO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
		sql.append("AND TITULO.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID ");
		sql.append("AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
		sql.append("AND TM_AREA_REGISTRAL.ESTADO = '1' ");
		sql.append("AND PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES(+) ");
		sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID(+) ");
		sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID(+) ");
		
		sql.append("AND ");
		sql.append("TITULO.ANO_TITU='").append(aux2).append("' AND ");
		sql.append("TITULO.NUM_TITU='").append(aux3).append("' ");
		
		if(numTokens != 13)
		{	
			/*
			 *Inicio:jascencio:12/07/07 
			 *CC: REGMOBCON-2006
			 */
			if(aux3!=null){
				if(Integer.parseInt(aux3)<Integer.parseInt(Constantes.NUM_TITULO_RMC)){
					sql.append("AND TITULO.REG_PUB_ID").append(subcadena.toString()).append(" ");
					//sql.append("AND TITULO.OFIC_REG_ID = '").append(aux4).append("' ");
					//sql.append("AND TITULO.REG_PUB_ID = '").append(aux1).append("' ");
				}
			}
			/*
			 * Fin:jascencio
			 */
		}
		
		sql.append("ORDER BY ");
		if(ordenarXEstado){
			sql.append("TM_ESTADO_TITULO.ESTADO");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
		}else{
			sql.append("OFIC_REGISTRAL.NOMBRE, TITULO.ANO_TITU DESC, TITULO.NUM_TITU DESC");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);
		}

		if (isTrace(this)) System.out.println(" >> " + sql.toString());

		//*** MANEJO DE LA PAGINACION
		  Propiedades propiedades = Propiedades.getInstance();
		  stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		  stmt.setFetchSize(propiedades.getLineasPorPag());
		  rset   = stmt.executeQuery(sql.toString());
	
		  if (paginacion > 1)
			rset.absolute(propiedades.getLineasPorPag() * (paginacion - 1));
	
		  int conta=1;
		  boolean hayNext = false;
		  boolean encontro = false;
		  boolean b = rset.next();
		  java.util.List lista1 = new java.util.ArrayList();
	      
		  while (b==true && conta <= propiedades.getLineasPorPag()){
			  encontro = true;
			  conta++;			
			
				
				GeneralTituloBean bean = new GeneralTituloBean();
				
				bean.setSede(rset.getString("rp_nombre")==null?"---":rset.getString("rp_nombre"));
				bean.setDependencia(rset.getString("or_nombre")==null?"---":rset.getString("or_nombre"));
				bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
				bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
				
				//bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":rset.getString("tar_nombre"));
				
				/*
				 * Inicio:jascencio:12/07/07
				 * CC: REGMOBCON-2006
				 */
				if(aux3!=null){
					if(Integer.parseInt(aux3)<Integer.parseInt(Constantes.NUM_TITULO_RMC)){
						bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":rset.getString("tar_nombre"));
					}
					else{
						bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":"Registro Mobiliario de Contratos");
					}
				}
				/*
				 * Fin:jascencio
				 */
				//fecha presentacion
				java.sql.Timestamp ts_present = rset.getTimestamp("t_ts_present");
						
				if(ts_present != null)
					bean.setFec_presentacion(FechaUtil.dateTimeToString(ts_present));
				else
					bean.setFec_presentacion("---");

                        
				//fecha vencimiento
				java.util.Date date = rset.getDate("t_fec_venc");
				
				if(date != null)
					bean.setFec_vencimiento(FechaUtil.dateToString(date));
				else
					bean.setFec_vencimiento("---");	
				
				
			
			//cjvc77 20021218
				
			//modificado por JACR - inicio
				
				
				bean.setTipo_Per(rset.getString("tipo_per") == null?"-":rset.getString("tipo_per"));
				bean.setRaz_Soc(rset.getString("raz_soc"));
				bean.setArea_Reg(rset.getString("tar_area_reg_id"));
				/**2004/05/11 Kuma El estado depende de el puCtrl y el esTituCali **/
				String puCtrl = rset.getString("pu_ctrl");
				//ctr=puCtrl;
				
				if(puCtrl == null) puCtrl = "";
				String esTituCali = rset.getString("es_titu_cali");
				//esta=esTituCali;//jbugarin
				if(esTituCali == null) esTituCali = "";
				if(bean.getArea_Reg().equals("24000"))
				{
					bean.setEstado(resultadoTitulo(esTituCali,puCtrl));
				}
				else
				{
					bean.setEstado(rset.getString("tet_estado")==null?"---":rset.getString("tet_estado"));
				}
				/**2004/05/11 Kuma Fin **/
				bean.setUrl_detalle("");
				
				String stipoper = new String (rset.getString("tipo_per")==null?"":rset.getString("tipo_per"));
			
				StringBuffer xx = new StringBuffer();
				if (stipoper.equals("N")){
					/*
					if(rset.getString("empresa") != null)
						xx.append(rset.getString("empresa")).append("  ");
					*/					
					if(rset.getString("p_ape_pat") != null)
						xx.append(rset.getString("p_ape_pat")).append(" ");
					if(rset.getString("p_ape_mat") != null)
						xx.append(rset.getString("p_ape_mat")).append(" ");
					if(rset.getString("p_nombres") != null)
						xx.append(rset.getString("p_nombres"));					
					xx.append(" ");																		
				}else{

					if(rset.getString("p_ape_pat") != null)
						xx.append(rset.getString("p_ape_pat")).append(" ");
					if(rset.getString("p_ape_mat") != null)
						xx.append(rset.getString("p_ape_mat")).append(" ");
					if(rset.getString("p_nombres") != null)
						xx.append(rset.getString("p_nombres"));
					xx.append(" ");
					if(rset.getString("raz_soc") != null)
						xx.append(rset.getString("raz_soc"));
					else
						xx.append(rset.getString("empresa") == null?"":rset.getString("empresa"));						
				}	
				//modificado por JACR - fin 
				
				//bean.setPresentante(xx.toString());
				bean.setPresentante(xx.toString().trim().equals("")?"---":xx.toString());
				bean.setReg_pub_id(rset.getString("t_reg_pub_id"));
				bean.setOfic_reg_id(rset.getString("t_ofic_reg_id"));
//**ESQUELAS
				String estId = rset.getString("tet_estado_t_id");
				String refNumTitu = rset.getString("t_refnum_titu");
				String areaRegID = rset.getString("tar_area_reg_id");
				/**2004/05/11 Kuma El link a esquela depende de el esTituCali para vehicular**/
				if(bean.getArea_Reg().equals("24000"))
				{
					if(esTituCali.equals("32"))
					{
						esquela("50", refNumTitu, areaRegID, dconn, bean);
					}
					else if(esTituCali.equals("33"))
					{
						esquela("40", refNumTitu, areaRegID, dconn, bean);
					}
					else if(esTituCali.equals("39"))
					{
						esquela("120", refNumTitu, areaRegID, dconn, bean);
					}
					else
					{
						esquela("", refNumTitu, areaRegID, dconn, bean);
					}
					
				}
				else
				{
					esquela(estId, refNumTitu, areaRegID, dconn, bean);
				}
				/**2004/05/11 Kuma Fin**/
				
				
//**ESQUELAS
				area_registral = rset.getString("tar_area_reg_id");
				aux1 = rset.getString("t_reg_pub_id");
				aux4 = rset.getString("t_ofic_reg_id");
				
				bean.setArea_Reg(area_registral);

//**PARA PARTIDAS
				String auxiliarpartida = partida(conn, rset);
				bean.setPartida(auxiliarpartida);
//**PARA PARTIDAS
				StringBuffer sql1 = null;				

			// Para Actos Titulos
				actosTitulo(conn, stmt1, rs1, bean);			

			// Para Participantes
				boolean juri = participantesPN(conn, stmt1, rs1, bean);
				if(juri)
					participantesPJ(conn, stmt1, rs1, bean);
				/*
				 * Inicio:jascencio:12/07/07
				 * CC: REGMOBCON-2006
				 */
				List listaActosReg=listadoActosRegistrales(conn, stmt, rset, bean);
				bean.setListadoActos(listaActosReg);
				/*
				 * Fin:jascencio
				 */
				
				lista1.add(bean);
				b = rset.next();
			}
			
			if(lista1.size() == 1){
				GeneralTituloBean bean = (GeneralTituloBean) lista1.get(0);
				String aux_ofic = bean.getReg_pub_id() + "|" + bean.getOfic_reg_id();
				request.setParameter("ano", bean.getAno());
				request.setParameter("numtitu", bean.getTitulo());
				request.setParameter("areareg",area_registral);
				request.setParameter("oficinas", aux_ofic);
				transition("buscarXNroTituloDet", request, response);
				
				/**	jbugarin 23/02/2007 **/
			/* DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
			 dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
			 dboRegisPublico.setField(DboRegisPublico.CAMPO_REG_PUB_ID,bean.getReg_pub_id());
			 if(dboRegisPublico.find()== true){
			 descZona = dboRegisPublico.getField(DboRegisPublico.CAMPO_NOMBRE); 
			 }

			 ExpressoHttpSessionBean.getRequest(request).setAttribute("estado",esta);
			 ExpressoHttpSessionBean.getRequest(request).setAttribute("ctrl",ctr);
			 ExpressoHttpSessionBean.getRequest(request).setAttribute("zonaRegistral",descZona);
			*/
					  
			 /** jbugarin fin**/
				
				
				
				return response;
			}

			hayNext = rset.next();			

			if(encontro)
			{
				//2 noviembre
				//log_transaction
				LogAuditoriaConsulTituloBean logbean = new LogAuditoriaConsulTituloBean();
				//_datos genericos
				logbean.setCodigoServicio(TipoServicio.CONSULTA_TITULOS);
				logbean.setUsuarioSession(user);
								//Modificado por: Proyecto Filtros de Acceso
								//Fecha: 02/10/2006
								//logbean.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
								logbean.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
								//Fecha: 08/10/2006             
								logbean.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
								//Fin Modificación
				//_datos especificos
				logbean.setOficRegId(aux4);
				logbean.setRegPubId(aux1);
				logbean.setAnoTitulo(aux2);
				logbean.setNumTitulo(aux3);
				/*
				Job004 j4c = new Job004();
					j4c.setBean(logbean);
					Thread llamador2c = new Thread(j4c);
					llamador2c.start();		
				*/
				if (Propiedades.getInstance().getFlagTransaccion()==true)
				Transaction.getInstance().registraTransaccion(logbean,conn);
				conn.commit();
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
			}
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);
			
//*PAGINACION EN EL JSP*//
			manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);
			
			
			
			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.CONSULTA_TITULOS);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaAct",fechaAct);
			
			
						
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista1);
			response.setStyle("listado");
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
			closeExtra(stmt, rset);
			closeExtra(stmt1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	

	
	protected ControllerResponse runBuscarXPresentantePJGState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		
		
		Statement stmt = null;
		ResultSet rset = null;
		//Conexiones de Base de Datos
			java.sql.Connection conne = null;
			java.sql.Statement stmt1 = null;
			java.sql.ResultSet rs1 = null;

		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

		
		try{
			init(request);
			validarSesion(request);
			
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "3");
			
			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			int paginacion = Integer.parseInt(request.getParameter("pagina"));
		//Recuperamos Variable de Búsqueda
			String tipob = request.getParameter("tipob");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipob", tipob);
			
		//Verificamos y redireccionamos si es por tipo de Documento
			if(tipob.equals("T"))
			{
				if(request.getParameter("ruc") != null && request.getParameter("ruc").trim().length() > 0)
				{
					transition("buscarXPresentantePJRucG", request, response);
					return response;
				}
				else
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar Número de Documento", "errorTitulo");
			}
			
		conn = pool.getConnection();
		conn.setAutoCommit(false);
		DBConnection dconn = new DBConnection(conn);

		//Recuperamos variable de Ordenamiento
			String h = request.getParameter("orden");
			boolean ordenarXEstado = false;
			if(h == null || h.trim().length() <= 0)
				ordenarXEstado = false;
			else if(h.equalsIgnoreCase("on"))
				ordenarXEstado = true;
			else
				ordenarXEstado = false;

		//Variable de Zonas
			String param = request.getParameter("hid1");
			if(param == null || param.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar como minimo una zona", "errorTitulo");

			StringBuffer subcadena = null;
			StringTokenizer stk = new StringTokenizer(param, "|");
			
			int numTokens = stk.countTokens();
			if(numTokens == 1)
				subcadena = new StringBuffer(" = '").append(stk.nextToken()).append("' ");
			else if(numTokens > 1 && numTokens < 13){
				subcadena = new StringBuffer(" IN (");
	
				if(stk.hasMoreTokens())
					subcadena.append("\'").append(stk.nextToken()).append("\'");
	
				while (stk.hasMoreTokens()) {
					subcadena.append(", \'").append(stk.nextToken()).append("\'");
				}
				subcadena.append(") ");
			}
			
			String aux[] = new String[3];
			int y = 0;
		//Otras variables
			String aux1 = aux[0];  	// REG_PUB_ID
			String aux4 = aux[1];	// OFIC_REG_ID
			String area_registral = null;			

		//Recuperamos Variables para Razon Social y Siglas
			String aux2 = Tarea.reemplazaApos(request.getParameter("razsoc"));
			String aux3 = Tarea.reemplazaApos(request.getParameter("siglas"));
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("razsoc", aux2);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("siglas", aux3);
			
			boolean pase_siglas = false;
			boolean pase_razsoc = false;

			if(aux2 != null && aux2.trim().length() > 0)
			{
				pase_razsoc = true;
				aux2 = "'" + aux2 + "%'";
			}
			if(!pase_razsoc){
				if(aux3 == null || aux3.trim().length() <= 0)
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar ya sea la Razon Social o las Siglas", "errorTitulo");
				pase_siglas = true;
				aux3 = "'" + aux3 + "%'";
			}

			ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc", request.getParameter("ruc"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hid1", param);

			//VERIFICAR COUNT 1
			long tamano = 0;
			boolean hacerCount = false;
			StringBuffer sqlc = new StringBuffer("SELECT");

			if(paginacion == 1){
				if (pase_razsoc) 
					sqlc.append(" /*+ORDERED  INDEX (PRESENTANTE INDX_PRESENTANTE_NOMBINST) */ ");
				else
					sqlc.append(" /*+ORDERED INDEX (TM_INST_SIR INDX_INST_SIGLAS) */ ");
				
				hacerCount = true;//veri
			}			
			
			if(hacerCount){			
				sqlc.append(" count(*)");
				if(pase_razsoc)// Razon Social
				{
					sqlc.append(" FROM PRESENTANTE");
					sqlc.append(" WHERE ");
					sqlc.append(" PRESENTANTE.nombre_inst like ").append(aux2);
					
				}	
				else
				{
					sqlc.append(" FROM PRESENTANTE, TM_INST_SIR ");
					sqlc.append(" WHERE ");
					sqlc.append(" TM_INST_SIR.siglas like ").append(aux3);
					sqlc.append(" AND TM_INST_SIR.REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID ");
					sqlc.append(" AND TM_INST_SIR.OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID ");
					sqlc.append(" AND TM_INST_SIR.CUR_PRES = PRESENTANTE.CUR_PRES ");					
				}	
				
				if(numTokens != 13)
					sqlc.append(" AND PRESENTANTE.PRES_REG_PUB_ID ").append(subcadena);
				

				if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset   = stmt.executeQuery(sqlc.toString());
				rset.next();
				tamano = rset.getLong(1);
				
				if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda() * 2)
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
			}

			//VERIFICAR COUNT 2
			tamano = 0;
			sqlc = new StringBuffer("SELECT");

			if(paginacion == 1){
				if (pase_razsoc) 
				{	sqlc.append(" /*+ORDERED ");
					sqlc.append(" INDEX (PRESENTANTE INDX_PRESENTANTE_NOMBINST) ");
					sqlc.append(" INDEX (TITULO INDX_TITU_PRES) ");
					sqlc.append(" */  ");
				}
				else
				{	sqlc.append(" /*+ORDERED ");
					sqlc.append(" INDEX (TM_INST_SIR INDX_INST_SIGLAS) ");
					sqlc.append(" INDEX (PRESENTANTE INDX_PRES_CUR) ");
					sqlc.append(" INDEX (TITULO INDX_TITU_PRES) ");
					sqlc.append(" */ 	");
				}
				hacerCount = true;//veri
			}			
			
			if(hacerCount){			
				
				if(pase_razsoc) // Razon Social
				{
				
					sqlc.append(" count(TITULO.REFNUM_TITU)");
					sqlc.append(" FROM PRESENTANTE, TITULO");
					sqlc.append(" WHERE ");
					sqlc.append(" PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID");
					sqlc.append(" AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID");
					sqlc.append(" AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES");
					sqlc.append(" AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
					sqlc.append(" AND PRESENTANTE.SISTEMA_ID = TITULO.SISTEMA_ID ");
				
					sqlc.append(" and PRESENTANTE.nombre_inst like ").append(aux2);
				}	
				else
				{
					sqlc.append(" count(TITULO.REFNUM_TITU)");
					sqlc.append(" FROM PRESENTANTE, TM_INST_SIR, TITULO");
					sqlc.append(" WHERE ");
					
					sqlc.append(" TM_INST_SIR.REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID ");
					sqlc.append(" AND TM_INST_SIR.OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID ");
					sqlc.append(" AND TM_INST_SIR.CUR_PRES = PRESENTANTE.CUR_PRES ");										
					
					sqlc.append(" AND PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID");
					sqlc.append(" AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID");
					sqlc.append(" AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES");
					sqlc.append(" AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
					sqlc.append(" AND PRESENTANTE.SISTEMA_ID = TITULO.SISTEMA_ID ");
										
					sqlc.append(" and TM_INST_SIR.siglas like ").append(aux3);
					
				}	
				
				if(numTokens != 13)
					sqlc.append(" and TITULO.REG_PUB_ID ").append(subcadena);

				if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset   = stmt.executeQuery(sqlc.toString());
				rset.next();
				tamano = rset.getLong(1);
			}else
				tamano = Long.parseLong(request.getParameter("tamano"));

//Formando Query principal
StringBuffer q = new StringBuffer();		
q.append(" SELECT ");
if (pase_razsoc) 
{
	q.append(" /*+ORDERED ");
	//q.append("     INDEX (TM_INST_SIR INDX_INST_RAZ_SOC) ");
	q.append("     INDEX (PRESENTANTE INDX_PRESENTANTE_NOMBRE_INST) ");
	q.append("     INDEX (TITULO INDX_TITU_PRES) ");
	q.append("     INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT)  ");
	q.append(" */  ");
}
else
{
	q.append(" /*+ORDERED ");
	q.append(" INDEX (TM_INST_SIR INDX_INST_SIGLAS) ");
	q.append(" INDEX (PRESENTANTE INDX_PRES_CUR) ");
	q.append(" INDEX (TITULO INDX_TITU_PRES) ");
	q.append(" INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT) ");
	q.append(" */ 	");
}

//---------------q.append(" DISTINCT ");
q.append(" TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
q.append(" TM_AREA_REGISTRAL.NOMBRE      as tm_area_registral_nombre,");
q.append(" REGIS_PUBLICO.NOMBRE          as regis_publico_nombre,  ");        
q.append(" TITULO.REFNUM_TITU            as t_refnum_titu, ");
q.append(" TITULO.NUM_TITU               as t_num_titu, ");
q.append(" TITULO.ANO_TITU               as t_ano_titu, ");
q.append(" TITULO.REG_PUB_ID             as t_reg_pub_id, ");
q.append(" TITULO.OFIC_REG_ID            as t_ofic_reg_id, ");
q.append(" TITULO.TS_PRESENT             as t_ts_present,  ");
q.append(" TITULO.FEC_VENC               as t_fec_venc,    ");
q.append(" PRESENTANTE.NOMBRE_INST       as tm_inst_sir_nombre_inst,  ");
q.append(" OFIC_REGISTRAL.NOMBRE         as ofic_registral_nombre,  ");
q.append(" TM_ESTADO_TITULO.ESTADO_TITULO_ID as campo1,  ");
q.append(" TM_ESTADO_TITULO.ESTADO       as tm_estado_titulo_estado ");

if(pase_razsoc)
{
q.append(" FROM PRESENTANTE, TITULO, DETALLE_TITULO, TM_AREA_REGISTRAL, OFIC_REGISTRAL, REGIS_PUBLICO, TM_ESTADO_TITULO ");
q.append(" WHERE ");
}
else
{
q.append(" FROM TM_INST_SIR, PRESENTANTE, TITULO, DETALLE_TITULO, TM_AREA_REGISTRAL, OFIC_REGISTRAL, REGIS_PUBLICO, TM_ESTADO_TITULO ");
q.append(" WHERE PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID  AND ");
q.append(" PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID  AND  ");
q.append(" PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES  AND  ");

}
q.append(" PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID ");
q.append(" AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID  ");
q.append(" AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES  ");
q.append(" AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES   ");
q.append(" AND PRESENTANTE.sistema_id = TITULO.sistema_id ");
q.append(" AND TITULO.REFNUM_TITU = DETALLE_TITULO.REFNUM_TITU  ");
q.append(" AND DETALLE_TITULO.FG_ACTIVO = '1'  ");
q.append(" AND DETALLE_TITULO.ESTADO_TITULO_ID = TM_ESTADO_TITULO.ESTADO_TITULO_ID  ");
q.append(" AND TITULO.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID  ");
q.append(" AND TITULO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID  ");
q.append(" AND TITULO.AREA_REG_ID = TM_AREA_REGISTRAL.AREA_REG_ID "); 
q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID  ");
q.append(" AND TM_AREA_REGISTRAL.ESTADO = '1'  ");
if(pase_razsoc) // Razon Social
	q.append(" and PRESENTANTE.nombre_inst like ").append(aux2);
else
	q.append(" and TM_INST_SIR.siglas like ").append(aux3);

if(numTokens != 13)
	q.append(" and TITULO.REG_PUB_ID ").append(subcadena);


//order_____			
			if(ordenarXEstado)
			{
				q.append(" order by TM_ESTADO_TITULO.estado");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
			}
			else
			{
				q.append(" order by OFIC_REGISTRAL.nombre, TITULO.ANO_TITU DESC,");
				q.append("          TITULO.NUM_TITU DESC");
			}
			
if (isTrace(this)) System.out.println("___verquery___"+q.toString());			
			
Propiedades propiedades = Propiedades.getInstance();
//Connection myConn = conn.getMyConnection();
stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(propiedades.getLineasPorPag());
rset   = stmt.executeQuery(q.toString());

if (paginacion > 1)
	rset.absolute(propiedades.getLineasPorPag() * (paginacion - 1));

boolean b = rset.next();

int conta=1;
java.util.List lista1 = new java.util.ArrayList();


while (b==true && conta <= propiedades.getLineasPorPag())
{
	conta++;
	GeneralTituloBean bean= new GeneralTituloBean();
	
	//completar detalles
	bean.setSede(rset.getString("regis_publico_nombre")==null?"---":rset.getString("regis_publico_nombre"));
	bean.setDependencia(rset.getString("ofic_registral_nombre")==null?"---":rset.getString("ofic_registral_nombre"));
	bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
	bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
	bean.setTipo_registro(rset.getString("tm_area_registral_nombre")==null?"---":rset.getString("tm_area_registral_nombre"));

	//java.util.Date ts_present = rset.getDate("t_ts_present");
	//bean.setFec_presentacion(FechaUtil.dateToString(ts_present));
	//java.util.Date fv = rset.getDate("t_fec_venc");
	//bean.setFec_vencimiento(FechaUtil.dateToString(fv));


			//fecha presentacion
			java.sql.Timestamp ts_present = rset.getTimestamp("t_ts_present");
						
			if(ts_present != null)
				bean.setFec_presentacion(FechaUtil.dateTimeToString(ts_present));
			else
				bean.setFec_presentacion("---");

                        
			//fecha vencimiento
			java.util.Date date = rset.getDate("t_fec_venc");
				
			if(date != null)
				bean.setFec_vencimiento(FechaUtil.dateToString(date));
			else
				bean.setFec_vencimiento("---");	


	
	if(rset.getString("tm_inst_sir_nombre_inst") != null)
		bean.setPresentante(rset.getString("tm_inst_sir_nombre_inst"));
	else
		bean.setPresentante("");
		
	bean.setEstado(rset.getString("tm_estado_titulo_estado"));
	bean.setUrl_detalle("");
	bean.setReg_pub_id(rset.getString("t_reg_pub_id"));
	bean.setOfic_reg_id(rset.getString("t_ofic_reg_id"));

//__ESQUELAS
	String estId      = rset.getString("campo1");
	String refNumTitu = rset.getString("t_refnum_titu");
	String areaRegID  = rset.getString("tar_area_reg_id");
	esquela(estId, refNumTitu, areaRegID, dconn, bean);
//__ESQUELAS

	area_registral = areaRegID;
	aux1 = rset.getString("t_reg_pub_id");
	aux4 = rset.getString("t_ofic_reg_id");
	bean.setArea_Reg(area_registral);

//**PARA PARTIDAS
				String auxiliarpartida = partida(conn, rset);
				bean.setPartida(auxiliarpartida);
//**FIN PARA PARTIDAS				

			// Para Actos Titulos
				actosTitulo(conn, stmt1, rs1, bean);			

			// Para Participantes
				boolean juri = participantesPN(conn, stmt1, rs1, bean);
				if(juri)
					participantesPJ(conn, stmt1, rs1, bean);	

	//agregar bean a la lista
	lista1.add(bean);														
	b = rset.next();
}//while (b==true && conta <= propiedades.getLineasPorPag())


//si solamente hay uno, hacer transition	
			if(lista1.size() == 1)
			{
				GeneralTituloBean gtb = (GeneralTituloBean) lista1.get(0);
				String aux_ofic = gtb.getReg_pub_id() + "|" + gtb.getOfic_reg_id();
				request.setParameter("ano",gtb.getAno());
				request.setParameter("numtitu",gtb.getTitulo());
				request.setParameter("areareg",area_registral);
				request.setParameter("oficinas", aux_ofic);
				transition("buscarXNroTituloDet", request, response);
				return response;
			}

			boolean hayNext = rset.next();


			if (lista1.size()>=1)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);

//*PAGINACION EN EL JSP*//
			manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);


			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.CONSULTA_TITULOS);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaAct",fechaAct);


			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista1);
			response.setStyle("listado");
		}
		catch(ValidacionException ve){
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1", ve.getMensaje());
		}		
		catch(CustomException ce){
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
			closeExtra(stmt, rset);
			closeExtra(stmt1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	
//------------------------------------------


protected ControllerResponse runBuscarXPresentantePJRucGState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
                
		Statement stmt = null;
		ResultSet rset = null;
		//Conexiones de Base de Datos
			java.sql.Statement stmt1 = null;
			java.sql.ResultSet rs1 = null;
						
		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
		try{
				init(request);
				validarSesion(request);

				UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
                
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

				String area_registral = null;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "4");

				int num_pagina = Propiedades.getInstance().getLineasPorPag();
				int paginacion = Integer.parseInt(request.getParameter("pagina"));
		//Variable de Ordenamiento
				String h = request.getParameter("orden");
				boolean ordenarXEstado = false;

				if(h == null || h.trim().length() <= 0)
						ordenarXEstado = false;
				else if(h.equalsIgnoreCase("on"))
						ordenarXEstado = true;
				else
						ordenarXEstado = false;

		//Variable de Zonas			
				String param = request.getParameter("hid1");

				if(param == null || param.trim().length() <= 0)
						throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar como minimo una zona", "errorTitulo");

				ExpressoHttpSessionBean.getRequest(request).setAttribute("hid1", param);
				
				StringBuffer subcadena = null;
				StringTokenizer stk = new StringTokenizer(param, "|");

				int numTokens = stk.countTokens();
				if(numTokens == 1)
					subcadena = new StringBuffer(" = '").append(stk.nextToken()).append("' ");
				else if(numTokens > 1 && numTokens < 13){
					subcadena = new StringBuffer(" IN (");
					if(stk.hasMoreTokens())
							subcadena.append("\'").append(stk.nextToken()).append("\'");
	
					while (stk.hasMoreTokens()) {
							subcadena.append(", \'").append(stk.nextToken()).append("\'");
					}
					subcadena.append(") ");
				}

				String aux[] = new String[3];
				int y = 0;
		//Recuperamos variable de tipo de Búsqueda
				String tipob = request.getParameter("tipob");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tipob", tipob);

		//Variables	
				String aux1 = aux[0];  	// REG_PUB_ID
				String aux4 = aux[1];	// OFIC_REG_ID

		//Variables de Tipo de Documento
				String aux2 = request.getParameter("ruc");
				String aux7 = request.getParameter("tipdocpj");

				ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc", aux2);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tipdocpj", aux7);

			//VERIFICAR COUNT 1
			long tamano = 0;
			boolean hacerCount = false;//veri
		
			StringBuffer sqlc = new StringBuffer("");

			if(paginacion == 1)
				hacerCount = true;
				
			if(hacerCount){
				sqlc.append("SELECT ");
				sqlc.append("/*+ORDERED INDEX (PERSONA INDX_PERS_TIPONUMDOC)  */  ");
				sqlc.append("count(*) ");
				sqlc.append("FROM PERSONA ");
				sqlc.append("WHERE PERSONA.TIPO_DOC_ID = '").append(aux7).append("' ");
				sqlc.append(" AND PERSONA.NUM_DOC_IDEN = '").append(aux2).append("' ");
		        
				if(numTokens != 13)
					sqlc.append(" AND PERSONA.REG_PUB_ID ").append(subcadena.toString()).append(" ");
	
				if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset   = stmt.executeQuery(sqlc.toString());
				rset.next();
				tamano = rset.getLong(1);
				
				if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda() * 2)
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
			}

			//VERIFICAR COUNT 2
			tamano = 0;
			sqlc = new StringBuffer("");

			if(hacerCount){
				sqlc.append("SELECT ");
				sqlc.append("/*+ORDERED INDEX (PERSONA INDX_PERS_TIPONUMDOC) INDEX (PE_JURI XIF102PE_JURI) INDEX (TM_INST_SIR XIF110TM_INST_SIR) INDEX (TITULO INDX_TITU_PRES) */  ");
				sqlc.append("count(TITULO.REFNUM_TITU) ");
				sqlc.append("FROM PERSONA, PE_JURI, TM_INST_SIR, PRESENTANTE, TITULO ");
				sqlc.append("WHERE PE_JURI.PERSONA_ID = PERSONA.PERSONA_ID ");
				sqlc.append("AND TM_INST_SIR.PE_JURI_ID = PE_JURI.PE_JURI_ID ");
				sqlc.append("AND PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID ");
				sqlc.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID ");
				sqlc.append("AND PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES ");
				sqlc.append("AND TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID ");
				sqlc.append("AND TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID ");
				sqlc.append("AND TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES ");
				sqlc.append("AND TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES ");
				sqlc.append(" AND PERSONA.TIPO_DOC_ID = '").append(aux7).append("' ");
				sqlc.append(" AND PERSONA.NUM_DOC_IDEN = '").append(aux2).append("' ");
		        
				if(numTokens != 13)
					sqlc.append(" AND TITULO.REG_PUB_ID ").append(subcadena.toString()).append(" ");
	
				if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset   = stmt.executeQuery(sqlc.toString());
				rset.next();
				tamano = rset.getLong(1);
			}else
				tamano = Long.parseLong(request.getParameter("tamano"));


		//formando Query principal
		StringBuffer sqlTD = new StringBuffer();
		sqlTD.append("SELECT ");
		sqlTD.append("/*+ORDERED INDEX (PERSONA INDX_PERS_TIPONUMDOC) INDEX (PE_JURI XIF102PE_JURI) INDEX (TM_INST_SIR XIF110TM_INST_SIR) INDEX (TITULO INDX_TITU_PRES) */ ");
		//---------------sqlTD.append("DISTINCT ");
		sqlTD.append("PRESENTANTE.NUM_HOJA_PRES as p_num_hoja_pres, ");
		sqlTD.append("PRESENTANTE.AA_HOJA_PRES as p_aa_hoja_pres, ");
		sqlTD.append("PRESENTANTE.PRES_OFIC_REG_ID as p_pres_ofic_reg_id, ");
		sqlTD.append("PRESENTANTE.PRES_REG_PUB_ID as p_pres_reg_pub_id, ");
		sqlTD.append("PRESENTANTE.AREA_REG_ID as p_area_reg_id, ");
		sqlTD.append("PRESENTANTE.CUR_PRES as p_cur_pres, ");
		sqlTD.append("PRESENTANTE.TIPO_PER as p_tipo_per, ");
		sqlTD.append("PRESENTANTE.NOMBRES as p_nombres, ");
		sqlTD.append("PRESENTANTE.APE_MAT as p_ape_mat, ");
		sqlTD.append("PRESENTANTE.APE_PAT as p_ape_pat, ");
		sqlTD.append("PRESENTANTE.AA_TITU_ANTE as p_aa_titu_ante, ");
		sqlTD.append("PRESENTANTE.NU_TITU_ANTE as p_nu_titu_ante, ");
		sqlTD.append("PRESENTANTE.TI_DOCU as p_ti_docu, ");
		sqlTD.append("PRESENTANTE.NU_DOC as p_nu_doc, ");
		sqlTD.append("PRESENTANTE.TS_PRES as p_ts_pres, ");
		sqlTD.append("TITULO.REFNUM_TITU as t_refnum_titu, ");
		sqlTD.append("TITULO.NUM_TITU as t_num_titu, ");
		sqlTD.append("TITULO.ANO_TITU as t_ano_titu, ");
		sqlTD.append("TITULO.AREA_REG_ID as t_area_reg_id, ");
		sqlTD.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
		sqlTD.append("TITULO.OFIC_REG_ID as t_ofic_reg_id, ");
		sqlTD.append("TITULO.TS_PRESENT as t_ts_present, ");
		sqlTD.append("TITULO.FEC_VENC as t_fec_venc, ");
		sqlTD.append("TITULO.NUM_HOJA_PRES as t_num_hoja_pres, ");
		sqlTD.append("TITULO.AA_HOJA_PRES as t_aa_hoja_pres, ");
		sqlTD.append("TITULO.PRES_OFIC_REG_ID as t_pres_ofic_reg_id, ");
		sqlTD.append("TITULO.PRES_REG_PUB_ID as t_pres_reg_pub_id, ");
		sqlTD.append("DETALLE_TITULO.REFNUM_TITU as dt_refnum_titu, ");
		sqlTD.append("DETALLE_TITULO.NS_DETALLE as dt_ns_detalle, ");
		sqlTD.append("DETALLE_TITULO.FG_ACTIVO as dt_fg_activo, ");
		sqlTD.append("DETALLE_TITULO.MONTO_LIQ as dt_monto_liq, ");
		sqlTD.append("DETALLE_TITULO.ESTADO_TITULO_ID as dt_estado_t_id, ");
		sqlTD.append("DETALLE_TITULO.TS_CREA as dt_ts_crea, ");
		sqlTD.append("DETALLE_TITULO.PU_CTRL as dt_pu_ctrl, ");
		sqlTD.append("DETALLE_TITULO.ES_TITU_CALI as dt_es_titu_cali, ");
		sqlTD.append("TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
		sqlTD.append("TM_AREA_REGISTRAL.NOMBRE as tar_nombre, ");
		sqlTD.append("TM_AREA_REGISTRAL.DESCRIPCION as tar_descripcion, ");
		sqlTD.append("TM_AREA_REGISTRAL.ESTADO as tar_estado, ");
		sqlTD.append("TM_AREA_REGISTRAL.PREFIJO as tar_prefijo, ");
		sqlTD.append("PE_JURI.PE_JURI_ID as pj_pj_id, ");
		sqlTD.append("PE_JURI.RAZ_SOC as pj_raz_soc, ");
		sqlTD.append("PE_JURI.SIGLAS as pj_siglas, ");
		sqlTD.append("PE_JURI.PREF_CTA as pj_pref_cta, ");
		sqlTD.append("PE_JURI.TIPO_ORG as pj_tipo_org, ");
		sqlTD.append("PE_JURI.PERSONA_ID as pj_per_id, ");
		sqlTD.append("PE_JURI.JURIS_ID as pj_juris_id, ");
		sqlTD.append("PE_JURI.REPRES_ID as pj_repres_id, ");
		sqlTD.append("TM_INST_SIR.REG_PUB_ID as tis_reg_pub_id, ");
		sqlTD.append("TM_INST_SIR.OFIC_REG_ID as tis_ofic_reg_id, ");
		sqlTD.append("TM_INST_SIR.CUR_PRES as tis_cur_pres, ");
		sqlTD.append("TM_INST_SIR.NOMBRE_INST as tis_nombre_inst, ");
		sqlTD.append("TM_INST_SIR.SIGLAS as tis_siglas, ");
		sqlTD.append("TM_INST_SIR.PE_JURI_ID as tis_pj_id, ");
		sqlTD.append("REGIS_PUBLICO.REG_PUB_ID as rp_reg_pub_id, ");
		sqlTD.append("REGIS_PUBLICO.NOMBRE as rp_nombre, ");
		sqlTD.append("REGIS_PUBLICO.SIGLAS as rp_siglas, ");
		sqlTD.append("REGIS_PUBLICO.TS_CREA as rp_ts_crea, ");
		sqlTD.append("REGIS_PUBLICO.USR_CREA as rp_usr_crea, ");
		sqlTD.append("PERSONA.PERSONA_ID as per_per_id, ");
		sqlTD.append("PERSONA.NUM_DOC_IDEN as per_num_doc_iden, ");
		sqlTD.append("PERSONA.TPO_PERS as per_tpo_pers, ");
		sqlTD.append("PERSONA.FAX as per_fax, ");
		sqlTD.append("PERSONA.EMAIL as per_email, ");
		sqlTD.append("PERSONA.TELEF as per_telef, ");
		sqlTD.append("PERSONA.ANEXO as per_anexo, ");
		sqlTD.append("PERSONA.TIPO_DOC_ID as per_tipo_doc_id, ");
		sqlTD.append("PERSONA.JURIS_ID as per_juris_id, ");
		sqlTD.append("PERSONA.REG_PUB_ID as per_reg_pub_id, ");
		sqlTD.append("OFIC_REGISTRAL.REG_PUB_ID as or_reg_pub_id, ");
		sqlTD.append("OFIC_REGISTRAL.OFIC_REG_ID as or_ofic_reg_id, ");
		sqlTD.append("OFIC_REGISTRAL.NOMBRE as or_nombre, ");
		sqlTD.append("OFIC_REGISTRAL.JURIS_ID as or_juris_id, ");
		sqlTD.append("OFIC_REGISTRAL.TS_CREA as or_ts_crea, ");
		sqlTD.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as te_t_estado_t_id, ");
		sqlTD.append("TM_ESTADO_TITULO.MENSAJE as te_t_mensaje, ");
		sqlTD.append("TM_ESTADO_TITULO.ESTADO  as te_t_estado ");

		sqlTD.append("FROM PERSONA, PE_JURI, TM_INST_SIR, PRESENTANTE, TITULO, DETALLE_TITULO, TM_ESTADO_TITULO, TM_AREA_REGISTRAL, REGIS_PUBLICO, OFIC_REGISTRAL ");

		sqlTD.append("WHERE PE_JURI.PERSONA_ID = PERSONA.PERSONA_ID ");
		sqlTD.append("AND TM_INST_SIR.PE_JURI_ID = PE_JURI.PE_JURI_ID ");
		sqlTD.append(" AND PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID ");
		sqlTD.append(" AND PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID ");
		sqlTD.append(" AND PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES ");
		sqlTD.append("AND TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID ");
		sqlTD.append("AND TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID ");
		sqlTD.append("AND TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES ");
		sqlTD.append("AND TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES ");
		sqlTD.append("AND DETALLE_TITULO.REFNUM_TITU = TITULO.REFNUM_TITU ");
		sqlTD.append("AND DETALLE_TITULO.FG_ACTIVO = '1' ");
		sqlTD.append("AND TM_ESTADO_TITULO.ESTADO_TITULO_ID = DETALLE_TITULO.ESTADO_TITULO_ID ");
		sqlTD.append("AND OFIC_REGISTRAL.OFIC_REG_ID = TITULO.OFIC_REG_ID ");
		sqlTD.append("AND OFIC_REGISTRAL.REG_PUB_ID = TITULO.REG_PUB_ID ");
		sqlTD.append("AND TM_AREA_REGISTRAL.AREA_REG_ID = TITULO.AREA_REG_ID ");
		sqlTD.append("AND REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
		sqlTD.append("AND TM_AREA_REGISTRAL.ESTADO = '1' ");
		sqlTD.append("AND PERSONA.TIPO_DOC_ID = '").append(aux7).append("' ");
		sqlTD.append("AND PERSONA.NUM_DOC_IDEN = '").append(aux2).append("' ");
        
		if(numTokens != 13)
			sqlTD.append("AND TITULO.REG_PUB_ID ").append(subcadena.toString()).append(" ");
        
		sqlTD.append("ORDER BY ");

		if(ordenarXEstado){
				sqlTD.append("TM_ESTADO_TITULO.ESTADO ");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
		}else{
				sqlTD.append("OFIC_REGISTRAL.NOMBRE, TITULO.ANO_TITU DESC, TITULO.NUM_TITU DESC");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);
		}

		if (isTrace(this)) System.out.println(" >> " + sqlTD.toString());

				//*** MANEJO DE LA PAGINACION
				Propiedades propiedades = Propiedades.getInstance();
				//Connection myConn = conn.getMyConnection();
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(propiedades.getLineasPorPag());
				rset   = stmt.executeQuery(sqlTD.toString());

				if (paginacion > 1)
				  rset.absolute(propiedades.getLineasPorPag() * (paginacion - 1));

				boolean b = rset.next();

				int conta=1;
				java.util.List lista1 = new java.util.ArrayList();

				boolean encontro = false;
				boolean hayNext = false;

				while (b==true && conta <= propiedades.getLineasPorPag()){
						//ExpressoHttpSessionBean.getRequest(request).setAttribute("numeroderegistros", "1");
						encontro = true;

						conta++;


				java.util.List listaActRegistrales = new java.util.ArrayList();


				GeneralTituloBean bean = null;


				bean= new GeneralTituloBean();
				bean.setSede(rset.getString("rp_nombre")==null?"---":rset.getString("rp_nombre"));
				bean.setDependencia(rset.getString("or_nombre")==null?"---":rset.getString("or_nombre"));
				bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
				bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
				bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":rset.getString("tar_nombre"));

				//java.util.Date date = rset.getDate("t_ts_present");
				//bean.setFec_presentacion(FechaUtil.dateToString(date));
                
				//date = rset.getDate("t_fec_venc");
				//bean.setFec_vencimiento(FechaUtil.dateToString(date));


			//fecha presentacion
			java.sql.Timestamp ts_present = rset.getTimestamp("t_ts_present");
						
			if(ts_present != null)
				bean.setFec_presentacion(FechaUtil.dateTimeToString(ts_present));
			else
				bean.setFec_presentacion("---");

                        
			//fecha vencimiento
			java.util.Date date = rset.getDate("t_fec_venc");
				
			if(date != null)
				bean.setFec_vencimiento(FechaUtil.dateToString(date));
			else
				bean.setFec_vencimiento("---");	



				if(rset.getString("tis_nombre_inst") != null)
					bean.setPresentante(rset.getString("tis_nombre_inst"));
				else
					bean.setPresentante("");
                	
				bean.setEstado(rset.getString("te_t_estado"));
				bean.setUrl_detalle("");
				bean.setReg_pub_id(rset.getString("t_reg_pub_id"));
				bean.setOfic_reg_id(rset.getString("t_ofic_reg_id"));
                

//**ESQUELAS
						String estId = rset.getString("te_t_estado_t_id");
						String refNumTitu = rset.getString("t_refnum_titu");
						String areaRegID = rset.getString("tar_area_reg_id");
						esquela(estId, refNumTitu, areaRegID, dconn, bean);
//**ESQUELAS

						area_registral = rset.getString("tar_area_reg_id");
						aux1 = rset.getString("t_reg_pub_id");
						aux4 = rset.getString("t_ofic_reg_id");
                        
						bean.setArea_Reg(area_registral);

//**PARA PARTIDAS
						String auxiliarpartida = partida(conn, rset);
						bean.setPartida(auxiliarpartida);
						
//**PARA PARTIDAS
/*Uso Servicio*/


			// Para Actos Titulos
				actosTitulo(conn, stmt1, rs1, bean);			

				  // Para Participantes
						boolean juri = participantesPN(conn, stmt1, rs1, bean);
						if(juri)
							participantesPJ(conn, stmt1, rs1, bean);

						lista1.add(bean);
						 b = rset.next();
				}

				if(lista1.size() == 1){
						GeneralTituloBean bean = (GeneralTituloBean) lista1.get(0);
						String aux_ofic = bean.getReg_pub_id() + "|" + bean.getOfic_reg_id();
						request.setParameter("ano", bean.getAno());
						request.setParameter("numtitu", bean.getTitulo());
						request.setParameter("areareg",area_registral);
						request.setParameter("oficinas", aux_ofic);
						transition("buscarXNroTituloDet", request, response);
						return response;
				}
				hayNext = rset.next();
				if(encontro)
						ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
				else
						ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);

//*PAGINACION EN EL JSP*//
		  manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);
		  
		  
			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.CONSULTA_TITULOS);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaAct",fechaAct);
		  

				ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista1);
				response.setStyle("listado");
		}
		catch(ValidacionException ve){
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1", ve.getMensaje());
		}		
		catch(CustomException ce){
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
			closeExtra(stmt, rset);
			closeExtra(stmt1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
}




	protected ControllerResponse runBuscarXParticipantePNGState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
				
		Statement stmt = null;
		ResultSet rset = null;
		//Conexiones de Base de Datos
			java.sql.Statement stmt1 = null;
			java.sql.ResultSet rs1 = null;
			
		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
						
		
		try{
			init(request);
			validarSesion(request);
			
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			
			String area_registral = null;
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "5");

			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			int paginacion = Integer.parseInt(request.getParameter("pagina"));
		//Recuperando variable de Ordenamiento
			String h = request.getParameter("orden");
			boolean ordenarXEstado = false;
			if(h == null || h.trim().length() <= 0)
				ordenarXEstado = false;
			else if(h.equalsIgnoreCase("on"))
				ordenarXEstado = true;
			else
				ordenarXEstado = false;
			
		//Recuperando variable de eleecion de zonas			
			String param = request.getParameter("hid1");
			if(param == null || param.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar como minimo una zona", "errorTitulo");

			ExpressoHttpSessionBean.getRequest(request).setAttribute("hid1", param);

			StringBuffer subcadena = null;
			StringTokenizer stk = new StringTokenizer(param, "|");

			int numTokens = stk.countTokens();
			if(numTokens == 1)
				subcadena = new StringBuffer(" = '").append(stk.nextToken()).append("' ");
			else if(numTokens > 1 && numTokens < 13){
				subcadena = new StringBuffer(" IN (");
				if(stk.hasMoreTokens())
					subcadena.append("\'").append(stk.nextToken()).append("\'");
				while (stk.hasMoreTokens()) {
					subcadena.append(", \'").append(stk.nextToken()).append("\'");
				}
				subcadena.append(") ");
			}			

			String aux[] = new String[3];
			int y = 0;
			String aux1 = aux[0];  	// REG_PUB_ID
			String aux4 = aux[1];	// OFIC_REG_ID
		//Recuperando tipo de Búsqueda			
			String tipob = request.getParameter("tipob");
			
		//Recuperando valores de Apellidos y Nombres
			String aux2 = Tarea.reemplazaApos(request.getParameter("nombrep"));
			String aux3 = Tarea.reemplazaApos(request.getParameter("apepatp"));
			String aux5 = Tarea.reemplazaApos(request.getParameter("apematp"));
			
			String aux6 = request.getParameter("tipdocpnp");
			String aux7 = request.getParameter("numdocpnp");
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("nombrep", aux2);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("apepatp", aux3);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("apematp", aux5);			
			
			boolean pase_nomap = true;
			boolean pase_apepat = false;
			boolean pase_apemat = false;
			boolean pase_nombres = false;
			
			if(tipob.equals("N")){
				if( (aux2 == null || aux2.trim().length() <= 0) && (aux3 == null || aux3.trim().length() <= 0))
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar ya sea el Nombre o Apellido Paterno", "errorTitulo");
				else{
					if(aux2 != null && aux2.trim().length() > 0){
						aux2 = aux2 + "%";
						pase_nombres = true;
					}
					if(aux3 != null && aux3.trim().length() > 0){
						aux3 = aux3 + "%";
						pase_apepat = true; 
					}
					if(aux5 != null && aux5.trim().length() > 0){
						aux5 = aux5 + "%";
						pase_apemat = true;
					}
				}	
			}else if(tipob.equals("T")){
				if( (aux7 == null) || (aux7.trim().length() <= 0) )
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar un Número de Documento", "errorTitulo");
				else	
					pase_nomap = false;
			}else throw new CustomException(Errors.EC_MISSING_PARAM, "No se puede determinar su tipo de búsqueda", "errorTitulo");

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipdocpnp", aux6);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numdocpnp", aux7);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipob", tipob);

		//VERIFICAR COUNT 1
		long tamano = 0;
		StringBuffer sqlc = new StringBuffer();
		boolean hacerCount = false;
		
		if(paginacion == 1){
			if(pase_nomap){
				hacerCount = true;//veri
				sqlc.append("SELECT /*+ORDERED INDEX(IND_PN_PARTIC_TITU INDX_APE_NOM_PRTC_TITU) */ ");
			}else{
				hacerCount = true;//veri
				sqlc.append("SELECT /*+INDEX (IND_PN_PARTIC_TITU,INDX_PRTC_TITU_PN_TI_NU_DOC) */ ");
			}
		}
					
		if(hacerCount){
			sqlc.append(" count(*)");//20021217
			sqlc.append(" FROM IND_PN_PARTIC_TITU");
			sqlc.append(" WHERE ");

			if(pase_nomap){
				if(pase_apepat)
					sqlc.append(" IND_PN_PARTIC_TITU.APE_PAT LIKE '").append(aux3).append("' ");
				
				if(pase_apemat)
					sqlc.append(" AND IND_PN_PARTIC_TITU.APE_MAT LIKE '").append(aux5).append("' ");
				
				if(pase_nombres)	
					sqlc.append(" AND IND_PN_PARTIC_TITU.NOMBRES LIKE '").append(aux2).append("' ");
			}else{
				sqlc.append(" IND_PN_PARTIC_TITU.TI_DOC_IDEN = '").append(aux6).append("' AND ");
				sqlc.append(" IND_PN_PARTIC_TITU.NU_DOC_IDEN = '").append(aux7).append("' ");
			}
			
			if(numTokens != 13)
				sqlc.append(" AND IND_PN_PARTIC_TITU.COD_REG ").append(subcadena.toString()).append(" ");

			if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset   = stmt.executeQuery(sqlc.toString());
			rset.next();
			tamano = rset.getLong(1);

			if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda() * 2)
				throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
				
		}

		//VERIFICAR COUNT 2
		tamano = 0;
		sqlc = new StringBuffer();
		
		if(paginacion == 1){
			if(pase_nomap){
				hacerCount = true;//veri
				sqlc.append("SELECT /*+ORDERED INDEX(IND_PN_PARTIC_TITU INDX_APE_NOM_PRTC_TITU) INDEX(TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX(DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
			}else{
				hacerCount = true;//veri
				sqlc.append("SELECT /*+INDEX (IND_PN_PARTIC_TITU,INDX_PRTC_TITU_PN_TI_NU_DOC) INDEX(TITULO,XAK1TITULO) INDEX(DETALLE_TITULO,INDX_REFNUM_FG_ACT) INDEX (PRESENTANTE,INDX_PRES)*/ ");
			}
		}
					
		if(hacerCount)
		{
			sqlc.append(" count(TITULO.REFNUM_TITU)");//20021217
			sqlc.append(" FROM IND_PN_PARTIC_TITU,TITULO");
			sqlc.append(" WHERE IND_PN_PARTIC_TITU.COD_REG=TITULO.REG_PUB_ID AND");
			sqlc.append(" IND_PN_PARTIC_TITU.COD_OFIC_REG=TITULO.OFIC_REG_ID AND");
			sqlc.append(" IND_PN_PARTIC_TITU.AA_TITU=TITULO.ANO_TITU AND");
			sqlc.append(" IND_PN_PARTIC_TITU.NU_TITU=TITULO.NUM_TITU AND ");
			
			/**kuma 2003/09/02**/
			sqlc.append(" IND_PN_PARTIC_TITU.AREA_REG_ID=TITULO.AREA_REG_ID  ");
			/**fin kuma**/
			
			if(pase_nomap){
				if(pase_apepat)
					sqlc.append(" AND IND_PN_PARTIC_TITU.APE_PAT LIKE '").append(aux3).append("' ");
				
				if(pase_apemat)
					sqlc.append(" AND IND_PN_PARTIC_TITU.APE_MAT LIKE '").append(aux5).append("' ");
				
				if(pase_nombres)	
					sqlc.append(" AND IND_PN_PARTIC_TITU.NOMBRES LIKE '").append(aux2).append("' ");
			}else{
				sqlc.append(" AND IND_PN_PARTIC_TITU.TI_DOC_IDEN = '").append(aux6).append("' ");
				sqlc.append(" AND IND_PN_PARTIC_TITU.NU_DOC_IDEN = '").append(aux7).append("' ");
			}
			
			if(numTokens != 13)
				sqlc.append(" AND TITULO.REG_PUB_ID ").append(subcadena.toString()).append(" ");

			if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());

			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset   = stmt.executeQuery(sqlc.toString());
			rset.next();
			tamano = rset.getLong(1);
		}else
			tamano = Long.parseLong(request.getParameter("tamano"));


		//Formando el Query de búsqueda principal

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");
		//--------------------sql.append("DISTINCT ");
		sql.append("REGIS_PUBLICO.NOMBRE as rp_nombre, ");
		sql.append("OFIC_REGISTRAL.NOMBRE as or_nombre, ");
		sql.append("TITULO.ANO_TITU as t_ano_titu, ");
		sql.append("TITULO.NUM_TITU as t_num_titu, ");
		sql.append("TM_AREA_REGISTRAL.NOMBRE as tar_nombre, ");
		sql.append("TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
		sql.append("TITULO.TS_PRESENT as t_ts_present, ");
		sql.append("TITULO.FEC_VENC as t_fec_venc, ");
		sql.append("PRESENTANTE.APE_PAT as p_ape_pat, ");
		sql.append("PRESENTANTE.APE_MAT as p_ape_mat, ");
		sql.append("PRESENTANTE.NOMBRES as p_nombres, ");
		sql.append("PRESENTANTE.CUR_PRES as p_cur_pres, ");
		sql.append("TM_ESTADO_TITULO.ESTADO as tet_estado, ");
		sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_t_id, ");
		sql.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
		sql.append("TITULO.REFNUM_TITU as t_refnum_titu, ");
		sql.append("TITULO.OFIC_REG_ID  as t_ofic_reg_id, ");
		sql.append("TM_INST_SIR.NOMBRE_INST as empresa ");		
		
		sql.append("FROM IND_PN_PARTIC_TITU,TITULO,DETALLE_TITULO,PRESENTANTE,TM_AREA_REGISTRAL,REGIS_PUBLICO,OFIC_REGISTRAL,TM_ESTADO_TITULO, TM_INST_SIR ");
		
		sql.append("WHERE IND_PN_PARTIC_TITU.COD_REG=TITULO.REG_PUB_ID AND ");
		sql.append("IND_PN_PARTIC_TITU.COD_OFIC_REG=TITULO.OFIC_REG_ID AND ");
		sql.append("IND_PN_PARTIC_TITU.AA_TITU=TITULO.ANO_TITU  AND ");
		sql.append("IND_PN_PARTIC_TITU.NU_TITU=TITULO.NUM_TITU AND ");
		sql.append("IND_PN_PARTIC_TITU.SISTEMA_ID = TITULO.SISTEMA_ID AND ");
		sql.append("TITULO.REFNUM_TITU = DETALLE_TITULO.REFNUM_TITU AND ");
		sql.append("DETALLE_TITULO.FG_ACTIVO='1' AND ");
		sql.append("TITULO.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID AND ");
		sql.append("DETALLE_TITULO.ESTADO_TITULO_ID=TM_ESTADO_TITULO.ESTADO_TITULO_ID AND ");
		sql.append("TITULO.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID AND ");
		sql.append("TITULO.AREA_REG_ID = TM_AREA_REGISTRAL.AREA_REG_ID AND ");
		sql.append("TITULO.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID AND ");
		sql.append("TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID AND ");
		sql.append("TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID AND ");
		sql.append("TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES AND ");
		sql.append("TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES ");

		sql.append("AND PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES(+) ");
		sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID(+) ");
		sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID(+) ");
		
		if(pase_nomap){
			if(pase_apepat)
				sql.append("AND IND_PN_PARTIC_TITU.APE_PAT LIKE '").append(aux3).append("' ");
			
			if(pase_apemat)
				sql.append("AND IND_PN_PARTIC_TITU.APE_MAT LIKE '").append(aux5).append("' ");
			
			if(pase_nombres)	
				sql.append("AND IND_PN_PARTIC_TITU.NOMBRES LIKE '").append(aux2).append("' ");
		}else{
			sql.append("AND IND_PN_PARTIC_TITU.TI_DOC_IDEN = '").append(aux6).append("' ");
			sql.append("AND IND_PN_PARTIC_TITU.NU_DOC_IDEN = '").append(aux7).append("' ");
		}
		
		if(numTokens != 13)
			sql.append("AND TITULO.REG_PUB_ID ").append(subcadena.toString()).append(" ");

		sql.append("ORDER BY ");
		if(ordenarXEstado){
			sql.append("TM_ESTADO_TITULO.ESTADO");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
		}else{
			sql.append("OFIC_REGISTRAL.NOMBRE, TITULO.ANO_TITU DESC, TITULO.NUM_TITU DESC");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);
		}

		if (isTrace(this)) System.out.println(" >> " + sql.toString());
			//*** MANEJO DE LA PAGINACION
		  Propiedades propiedades = Propiedades.getInstance();
		  //Connection myConn = conn.getMyConnection();
		  stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		  stmt.setFetchSize(propiedades.getLineasPorPag());
		  rset   = stmt.executeQuery(sql.toString());
	
		  if (paginacion > 1)
			rset.absolute(propiedades.getLineasPorPag() * (paginacion - 1));
	
		  boolean b = rset.next();
	      
		  int conta=1;
		  java.util.List lista1 = new java.util.ArrayList();
	
		  boolean encontro = false;
		  boolean hayNext = false;
	      
		  while (b==true && conta <= propiedades.getLineasPorPag())
		  {
			encontro = true;
			conta++;			
			
			java.util.List listaActRegistrales = new java.util.ArrayList();
			
			GeneralTituloBean bean = null;
			encontro = true;
			bean= new GeneralTituloBean();
				
				bean.setSede(rset.getString("rp_nombre")==null?"---":rset.getString("rp_nombre"));
				bean.setDependencia(rset.getString("or_nombre")==null?"---":rset.getString("or_nombre"));
				bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
				bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
				bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":rset.getString("tar_nombre"));
				
				//java.util.Date date = rset.getDate("t_ts_present");
				//bean.setFec_presentacion(FechaUtil.dateToString(date));
				
				//date = rset.getDate("t_fec_venc");
				//bean.setFec_vencimiento(FechaUtil.dateToString(date));
                
                
			//fecha presentacion
			java.sql.Timestamp ts_present = rset.getTimestamp("t_ts_present");
						
			if(ts_present != null)
				bean.setFec_presentacion(FechaUtil.dateTimeToString(ts_present));
			else
				bean.setFec_presentacion("---");

                        
			//fecha vencimiento
			java.util.Date date = rset.getDate("t_fec_venc");
				
			if(date != null)
				bean.setFec_vencimiento(FechaUtil.dateToString(date));
			else
				bean.setFec_vencimiento("---");	
                
                
				
	//**Presentante Juridico
			//cjvc77 20021218
				StringBuffer xx = new StringBuffer();
				if(rset.getString("empresa") != null)
					xx.append(rset.getString("empresa")).append("  ");
					
				if(rset.getString("p_ape_pat") != null)
					xx.append(rset.getString("p_ape_pat")).append(" ");
				if(rset.getString("p_ape_mat") != null)
					xx.append(rset.getString("p_ape_mat")).append(" ");
				if(rset.getString("p_nombres") != null)
					xx.append(rset.getString("p_nombres"));
				
				bean.setPresentante(xx.toString());

	//**Presentante Juridico
				bean.setEstado(rset.getString("tet_estado"));
				bean.setUrl_detalle("");
				bean.setReg_pub_id(rset.getString("t_reg_pub_id"));
				bean.setOfic_reg_id(rset.getString("t_ofic_reg_id"));
				
//**ESQUELAS
				String estId = rset.getString("tet_estado_t_id");
				String refNumTitu = rset.getString("t_refnum_titu");
				String areaRegID = rset.getString("tar_area_reg_id");
				esquela(estId, refNumTitu, areaRegID, dconn, bean);				
//**ESQUELAS
				
				area_registral = rset.getString("tar_area_reg_id");
				aux1 = rset.getString("t_reg_pub_id");
				aux4 = rset.getString("t_ofic_reg_id");
				
				bean.setArea_Reg(area_registral);

//**PARA PARTIDAS
				String auxiliarpartida = partida(conn, rset);
				bean.setPartida(auxiliarpartida);
//**PARA PARTIDAS
/*Uso Servicio*/		
				

			// Para Actos Titulos
				actosTitulo(conn, stmt1, rs1, bean);			

				// Para Participantes
				boolean juri = participantesPN(conn, stmt1, rs1, bean);
				if(juri)
					participantesPJ(conn, stmt1, rs1, bean);

				lista1.add(bean);
				b = rset.next();
			}
			
			if(lista1.size() == 1){
				GeneralTituloBean bean = (GeneralTituloBean) lista1.get(0);
				String aux_ofic = bean.getReg_pub_id() + "|" + bean.getOfic_reg_id();
				request.setParameter("ano", bean.getAno());
				request.setParameter("numtitu", bean.getTitulo());
				request.setParameter("areareg",area_registral);
				request.setParameter("oficinas", aux_ofic);
				transition("buscarXNroTituloDet", request, response);
				return response;
			}
			hayNext = rset.next();			
			
			if(encontro)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);
			
//*PAGINACION EN EL JSP*//
		  manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);
		  
		  
			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.CONSULTA_TITULOS);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaAct",fechaAct);

		  

			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista1);
			response.setStyle("listado");
		}
		catch(ValidacionException ve){
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1", ve.getMensaje());
		}		
		
		catch(CustomException ce){
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
			closeExtra(stmt, rset);
			closeExtra(stmt1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runBuscarXParticipantePJGState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		Statement stmt = null;
		ResultSet rset = null;
		//Conexiones de Base de Datos
		java.sql.Statement stmt1 = null;
		java.sql.ResultSet rs1 = null;
			
		//obtener usuario de la sesion				
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
		try{
			init(request);
			validarSesion(request);
			
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			
			String area_registral = null;
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			int paginacion = Integer.parseInt(request.getParameter("pagina"));
			//Recupero variables de zonas			
			String param = request.getParameter("hid1");
			if(param == null || param.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar como minimo una zona", "errorTitulo");

			ExpressoHttpSessionBean.getRequest(request).setAttribute("hid1", param);
			StringBuffer subcadena = null;
			StringTokenizer stk = new StringTokenizer(param, "|");

			int numTokens = stk.countTokens();
			if(numTokens == 1)
				subcadena = new StringBuffer(" = '").append(stk.nextToken()).append("' ");
			else if(numTokens > 1 && numTokens < 13){
				subcadena = new StringBuffer(" IN (");
				if(stk.hasMoreTokens())
					subcadena.append("\'").append(stk.nextToken()).append("\'");
				while (stk.hasMoreTokens()) {
					subcadena.append(", \'").append(stk.nextToken()).append("\'");
				}
				subcadena.append(") ");
			}
			//Recupero variable de ordenamiento
			String h = request.getParameter("orden");
			boolean ordenarXEstado = false;
			if(h == null || h.trim().length() <= 0)
				ordenarXEstado = false;
			else if(h.equalsIgnoreCase("on"))
				ordenarXEstado = true;
			else
				ordenarXEstado = false;

		//Recuperando tipo de Búsqueda			
			String tipob = request.getParameter("tipob");

		//Recupero variable RUC, Siglas y Razon Social
			String aux2 = Tarea.reemplazaApos(request.getParameter("razsocp"));
			String aux3 = Tarea.reemplazaApos(request.getParameter("siglasp"));
			String aux10 = request.getParameter("rucp");
			String aux11 = request.getParameter("tipdocpjp");
			boolean paseRuc = false;
			boolean pase_razsoc = false;
			boolean pase_siglas = false;
			
			if(tipob.equals("T")){//Selecciono por tipo de documento
				if(request.getParameter("rucp") != null && request.getParameter("rucp").trim().length() > 0)
					paseRuc = true;
				else
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar un Número de Documento", "errorTitulo");
			}else if(tipob.equals("R")){
				if(aux2 != null && aux2.trim().length() > 0)
					pase_razsoc = true;
				
				if(!pase_razsoc){
					if(aux3 == null || aux3.trim().length() <= 0)
						throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar ya sea la Razon Social o las Siglas", "errorTitulo");
					pase_siglas = true;
				}
			}else	throw new CustomException(Errors.EC_MISSING_PARAM, "No se puede determinar su tipo de búsqueda", "errorTitulo");
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "6");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("rucp", request.getParameter("rucp"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipdocpjp", request.getParameter("tipdocpjp"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("razsocp", request.getParameter("razsocp"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("siglasp", request.getParameter("siglasp"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipob", request.getParameter("tipob"));

			String aux1 = null;
			String aux4 = null;
	
			//VERIFICAR COUNT 1
			long tamano = 0;
			StringBuffer sqlc = new StringBuffer();
			boolean hacerCount = false;
			
			if(paginacion == 1){
				if(paseRuc){
					hacerCount = true;//veri
					sqlc.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDX_PJ_PARTIC_TITU_TIPONUMDOC) */ ");
				}else if(pase_razsoc){ // Razon Social
					hacerCount = true;
					sqlc.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDEX_PJ_PARTIC_TITU_RAZ_SOC) */ ");
				}else if(pase_siglas){		// Siglas
					hacerCount = true;//veri
					sqlc.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDEX_PJ_PARTIC_TITU_SIGLAS) */ ");
				}else 
					throw new CustomException(Errors.EC_MISSING_PARAM, "No se puede determinar su tipo de búsqueda", "errorTitulo");
			}
			
			if(hacerCount){
				sqlc.append(" count(*)");//20021217
				sqlc.append(" FROM IND_PJ_PARTIC_TITU");
				sqlc.append(" WHERE ");
		
				if(paseRuc){
					sqlc.append(" IND_PJ_PARTIC_TITU.TI_DOC_IDEN = '").append(aux11).append("' ");
					sqlc.append(" AND IND_PJ_PARTIC_TITU.NU_DOC_IDEN = '").append(aux10).append("' ");
				}else if(pase_razsoc) // Razon Social
					sqlc.append(" IND_PJ_PARTIC_TITU.RAZ_SOC LIKE '").append(aux2).append("%' ");
				else if(pase_siglas)		// Siglas
					sqlc.append(" IND_PJ_PARTIC_TITU.SIGLAS LIKE '").append(aux3).append("%' ");
		
				if(numTokens != 13)
					sqlc.append(" AND IND_PJ_PARTIC_TITU.REG_PUB_ID").append(subcadena.toString()).append(" ");

				if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset   = stmt.executeQuery(sqlc.toString());
				rset.next();
				tamano = rset.getLong(1);
			
				if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda() * 2)
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
			}
			
			//VERIFICAR COUNT 2
			tamano = 0;
			sqlc = new StringBuffer();
			
			if(paginacion == 1){
				if(paseRuc){
					hacerCount = true;//veri
					sqlc.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDX_PJ_PARTIC_TITU_TIPONUMDOC INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT) */ ");
				}else if(pase_razsoc){ // Razon Social
					hacerCount = true;
					sqlc.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDEX_PJ_PARTIC_TITU_RAZ_SOC) INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
				}else if(pase_siglas){		// Siglas
					hacerCount = true;//veri
					sqlc.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDEX_PJ_PARTIC_TITU_SIGLAS) INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT) */ ");
				}else 
					throw new CustomException(Errors.EC_MISSING_PARAM, "No se puede determinar su tipo de búsqueda", "errorTitulo");
			}
			
			if(hacerCount){
				sqlc.append(" count(TITULO.REFNUM_TITU)");//20021217
				sqlc.append(" FROM IND_PJ_PARTIC_TITU, TITULO");
				sqlc.append(" WHERE IND_PJ_PARTIC_TITU.REG_PUB_ID = TITULO.REG_PUB_ID ");
				sqlc.append(" AND IND_PJ_PARTIC_TITU.OFIC_REG_ID = TITULO.OFIC_REG_ID ");
				sqlc.append(" AND IND_PJ_PARTIC_TITU.AA_TITU = TITULO.ANO_TITU ");
				sqlc.append(" AND IND_PJ_PARTIC_TITU.NU_TITU = TITULO.NUM_TITU ");
				sqlc.append(" AND IND_PJ_PARTIC_TITU.SISTEMA_ID = TITULO.SISTEMA_ID ");
				
				if(paseRuc){
					sqlc.append(" AND IND_PJ_PARTIC_TITU.TI_DOC_IDEN = '").append(aux11).append("' ");
					sqlc.append(" AND IND_PJ_PARTIC_TITU.NU_DOC_IDEN = '").append(aux10).append("' ");
				}else if(pase_razsoc) // Razon Social
					sqlc.append(" AND IND_PJ_PARTIC_TITU.RAZ_SOC LIKE '").append(aux2).append("%' ");
				else if(pase_siglas)		// Siglas
					sqlc.append(" AND IND_PJ_PARTIC_TITU.SIGLAS LIKE '").append(aux3).append("%' ");
				
				if(numTokens != 13)
					sqlc.append(" AND TITULO.REG_PUB_ID").append(subcadena.toString()).append(" ");

				if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
				stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rset   = stmt.executeQuery(sqlc.toString());
				rset.next();
				tamano = rset.getLong(1);
			}else
				tamano = Long.parseLong(request.getParameter("tamano"));
	

			StringBuffer sql = new StringBuffer();
			if(paseRuc)
				sql.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDX_PJ_PARTIC_TITU_TIPONUMDOC INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT) */ ");
			else if(pase_razsoc) // Razon Social
				sql.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDEX_PJ_PARTIC_TITU_RAZ_SOC) INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT)*/ ");
			else if(pase_siglas)		// Siglas
				sql.append("SELECT /*+ORDERED INDEX (IND_PJ_PARTIC_TITU INDEX_PJ_PARTIC_TITU_SIGLAS) INDEX (TITULO XAK1TITULO) INDEX (PRESENTANTE INDX_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT) */ ");
			else 
				throw new CustomException(Errors.EC_MISSING_PARAM, "No se puede determinar su tipo de búsqueda", "errorTitulo");
			
			//---------------sql.append("DISTINCT ");
			sql.append("TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
			sql.append("TM_AREA_REGISTRAL.NOMBRE as area_nombre, ");
			sql.append("TM_AREA_REGISTRAL.DESCRIPCION, ");
			sql.append("TM_AREA_REGISTRAL.ESTADO, ");
			sql.append("TM_AREA_REGISTRAL.PREFIJO, ");
			sql.append("IND_PJ_PARTIC_TITU.OFIC_REG_ID, ");
			sql.append("IND_PJ_PARTIC_TITU.REG_PUB_ID, ");
			sql.append("IND_PJ_PARTIC_TITU.NU_TITU, ");
			sql.append("IND_PJ_PARTIC_TITU.AA_TITU, ");
			sql.append("IND_PJ_PARTIC_TITU.NS_AFEC, ");
			sql.append("IND_PJ_PARTIC_TITU.NS_PERS_JURI, ");
			sql.append("IND_PJ_PARTIC_TITU.COD_ACTO_RGST, ");
			sql.append("IND_PJ_PARTIC_TITU.RAZ_SOC, ");
			sql.append("IND_PJ_PARTIC_TITU.NU_DOC_IDEN, ");
			sql.append("IND_PJ_PARTIC_TITU.TI_DOC_IDEN, ");
			sql.append("IND_PJ_PARTIC_TITU.SIGLAS, ");
			sql.append("IND_PJ_PARTIC_TITU.CUR, ");
			sql.append("IND_PJ_PARTIC_TITU.TI_PERS_JURI, ");
			sql.append("IND_PJ_PARTIC_TITU.TS_ULT_SYNC, ");
			sql.append("IND_PJ_PARTIC_TITU.AGNT_SYNC, ");
			sql.append("REGIS_PUBLICO.REG_PUB_ID, ");
			sql.append("REGIS_PUBLICO.NOMBRE as rp_nombre, ");
			sql.append("REGIS_PUBLICO.SIGLAS, ");
			sql.append("REGIS_PUBLICO.TS_CREA, ");
			sql.append("REGIS_PUBLICO.TS_ULT_MODIF, ");
			sql.append("REGIS_PUBLICO.USR_CREA, ");
			sql.append("REGIS_PUBLICO.USR_ULT_MODIF, ");
			sql.append("TITULO.REFNUM_TITU as t_refnum_titu, ");
			sql.append("TITULO.NUM_TITU as t_num_titu, ");
			sql.append("TITULO.ANO_TITU as t_ano_titu, ");
			sql.append("TITULO.AREA_REG_ID, ");
			sql.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
			sql.append("TITULO.OFIC_REG_ID as t_ofic_reg_id, ");
			sql.append("TITULO.TS_PRESENT as t_ts_present, ");
			sql.append("TITULO.FEC_VENC as t_fec_venc, ");
			sql.append("TITULO.TS_ULT_SYNC, ");
			sql.append("TITULO.AGNT_SYNC, ");
			sql.append("TITULO.NUM_HOJA_PRES, ");
			sql.append("TITULO.AA_HOJA_PRES, ");
			sql.append("TITULO.PRES_OFIC_REG_ID, ");
			sql.append("TITULO.PRES_REG_PUB_ID, ");
			sql.append("DETALLE_TITULO.REFNUM_TITU, ");
			sql.append("DETALLE_TITULO.NS_DETALLE, ");
			sql.append("DETALLE_TITULO.FG_ACTIVO, ");
			sql.append("DETALLE_TITULO.MONTO_LIQ, ");
			sql.append("DETALLE_TITULO.ESTADO_TITULO_ID, ");
			sql.append("DETALLE_TITULO.TS_ULT_SYNC, ");
			sql.append("DETALLE_TITULO.AGNT_SYNC, ");
			sql.append("DETALLE_TITULO.TS_CREA, ");
			sql.append("DETALLE_TITULO.PU_CTRL, ");
			sql.append("DETALLE_TITULO.ES_TITU_CALI, ");
			sql.append("OFIC_REGISTRAL.REG_PUB_ID, ");
			sql.append("OFIC_REGISTRAL.OFIC_REG_ID, ");
			sql.append("OFIC_REGISTRAL.NOMBRE as ofr_nombre, ");
			sql.append("OFIC_REGISTRAL.JURIS_ID, ");
			sql.append("OFIC_REGISTRAL.TS_CREA, ");
			sql.append("OFIC_REGISTRAL.TS_ULT_MODIF, ");
			sql.append("OFIC_REGISTRAL.USR_CREA, ");
			sql.append("OFIC_REGISTRAL.USR_ULT_MODIF, ");
			sql.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tet_estado_titulo_id, ");
			sql.append("TM_ESTADO_TITULO.MENSAJE, ");
			sql.append("TM_ESTADO_TITULO.ESTADO as tet_estado, ");
			sql.append("PRESENTANTE.NUM_HOJA_PRES, ");
			sql.append("PRESENTANTE.AA_HOJA_PRES, ");
			sql.append("PRESENTANTE.PRES_OFIC_REG_ID, ");
			sql.append("PRESENTANTE.PRES_REG_PUB_ID, ");
			sql.append("PRESENTANTE.AREA_REG_ID, ");
			sql.append("PRESENTANTE.CUR_PRES as pr_cur_pres, ");
			sql.append("PRESENTANTE.TIPO_PER, ");
			sql.append("PRESENTANTE.NOMBRES as pr_nombres, ");
			sql.append("PRESENTANTE.APE_MAT as pr_ape_mat, ");
			sql.append("PRESENTANTE.APE_PAT as pr_ape_pat, ");
			sql.append("PRESENTANTE.TI_DOCU, ");
			sql.append("PRESENTANTE.NU_DOC, ");
			sql.append("PRESENTANTE.TS_PRES, ");
			sql.append("TM_INST_SIR.NOMBRE_INST as empresa ");
			
			sql.append("FROM IND_PJ_PARTIC_TITU, TITULO, DETALLE_TITULO, PRESENTANTE, TM_AREA_REGISTRAL, OFIC_REGISTRAL, REGIS_PUBLICO, TM_ESTADO_TITULO, TM_INST_SIR ");

			sql.append("WHERE IND_PJ_PARTIC_TITU.REG_PUB_ID = TITULO.REG_PUB_ID ");
			sql.append("AND IND_PJ_PARTIC_TITU.OFIC_REG_ID = TITULO.OFIC_REG_ID ");
			sql.append("AND IND_PJ_PARTIC_TITU.AA_TITU = TITULO.ANO_TITU ");
			sql.append("AND IND_PJ_PARTIC_TITU.NU_TITU = TITULO.NUM_TITU ");
			sql.append("AND IND_PJ_PARTIC_TITU.SISTEMA_ID = TITULO.SISTEMA_ID ");
			sql.append("AND TITULO.PRES_REG_PUB_ID = PRESENTANTE.PRES_REG_PUB_ID ");
			sql.append("AND TITULO.PRES_OFIC_REG_ID = PRESENTANTE.PRES_OFIC_REG_ID ");
			sql.append("AND TITULO.AA_HOJA_PRES = PRESENTANTE.AA_HOJA_PRES ");
			sql.append("AND TITULO.NUM_HOJA_PRES = PRESENTANTE.NUM_HOJA_PRES ");
			sql.append("AND TITULO.REFNUM_TITU = DETALLE_TITULO.REFNUM_TITU ");
			sql.append("AND DETALLE_TITULO.FG_ACTIVO = '1' ");
			sql.append("AND DETALLE_TITULO.ESTADO_TITULO_ID = TM_ESTADO_TITULO.ESTADO_TITULO_ID ");
			sql.append("AND TITULO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
			sql.append("AND TITULO.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID ");
			sql.append("AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
			sql.append("AND TITULO.AREA_REG_ID = TM_AREA_REGISTRAL.AREA_REG_ID ");

			sql.append("AND PRESENTANTE.CUR_PRES = TM_INST_SIR.CUR_PRES(+) ");
			sql.append("AND PRESENTANTE.PRES_REG_PUB_ID = TM_INST_SIR.REG_PUB_ID(+) ");
			sql.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TM_INST_SIR.OFIC_REG_ID(+) ");
			
			if(paseRuc){
				sql.append("AND IND_PJ_PARTIC_TITU.TI_DOC_IDEN = '").append(aux11).append("' ");
				sql.append("AND IND_PJ_PARTIC_TITU.NU_DOC_IDEN = '").append(aux10).append("' ");
			}else if(pase_razsoc) // Razon Social
				sql.append("AND IND_PJ_PARTIC_TITU.RAZ_SOC LIKE '").append(aux2).append("%' ");
			else if(pase_siglas)		// Siglas
				sql.append("AND IND_PJ_PARTIC_TITU.SIGLAS LIKE '").append(aux3).append("%' ");
			else 
				throw new CustomException(Errors.EC_MISSING_PARAM, "No se puede determinar su tipo de búsqueda", "errorTitulo");
			
			if(numTokens != 13)
				sql.append("AND TITULO.REG_PUB_ID").append(subcadena.toString()).append(" ");
			sql.append("ORDER BY ");

			if(ordenarXEstado){
				sql.append("TM_ESTADO_TITULO.ESTADO");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
			}else{
				sql.append("OFIC_REGISTRAL.NOMBRE, TITULO.ANO_TITU DESC, TITULO.NUM_TITU DESC");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);
			}

			if (isTrace(this)) System.out.println(" >> " + sql.toString());

			//*** MANEJO DE LA PAGINACION
		  Propiedades propiedades = Propiedades.getInstance();
		  //Connection myConn = conn.getMyConnection();
		  stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		  stmt.setFetchSize(propiedades.getLineasPorPag());
		  rset   = stmt.executeQuery(sql.toString());
	
		  if (paginacion > 1)
			rset.absolute(propiedades.getLineasPorPag() * (paginacion - 1));
	
		  boolean b = rset.next();
		
		  int conta=1;
		  java.util.List lista1 = new java.util.ArrayList();
	
		  boolean encontro = false;
		  boolean hayNext = false;
	      
		  while (b==true && conta <= propiedades.getLineasPorPag()){
			encontro = true;
			conta++;			
			
			java.util.List listaActRegistrales = new java.util.ArrayList();
			
			GeneralTituloBean bean = new GeneralTituloBean();
			encontro = true;
			
				bean.setSede(rset.getString("rp_nombre")==null?"---":rset.getString("rp_nombre"));
				bean.setDependencia(rset.getString("ofr_nombre")==null?"---":rset.getString("ofr_nombre"));
				bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
				bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
				bean.setTipo_registro(rset.getString("area_nombre")==null?"---":rset.getString("area_nombre"));

				//java.util.Date date = rset.getDate("t_ts_present");
				//bean.setFec_presentacion(FechaUtil.dateToString(date));
				//date = rset.getDate("t_fec_venc");
				//bean.setFec_vencimiento(FechaUtil.dateToString(date));
				
				
			//fecha presentacion
			java.sql.Timestamp ts_present = rset.getTimestamp("t_ts_present");
						
			if(ts_present != null)
				bean.setFec_presentacion(FechaUtil.dateTimeToString(ts_present));
			else
				bean.setFec_presentacion("---");

                        
			//fecha vencimiento
			java.util.Date date = rset.getDate("t_fec_venc");
				
			if(date != null)
				bean.setFec_vencimiento(FechaUtil.dateToString(date));
			else
				bean.setFec_vencimiento("---");					
				
				
	//**Presentante Juridico
			//cjvc77 20021218
				StringBuffer xx = new StringBuffer();
				if(rset.getString("empresa") != null)
					xx.append(rset.getString("empresa")).append("  ");
					
				if(rset.getString("pr_ape_pat") != null)
					xx.append(rset.getString("pr_ape_pat")).append(" ");
				if(rset.getString("pr_ape_mat") != null)
					xx.append(rset.getString("pr_ape_mat")).append(" ");
				if(rset.getString("pr_nombres") != null)
					xx.append(rset.getString("pr_nombres"));
				
				bean.setPresentante(xx.toString());

	//**Presentante Juridico
				bean.setEstado(rset.getString("tet_estado"));
				bean.setUrl_detalle("");
				bean.setReg_pub_id(rset.getString("t_reg_pub_id"));
				bean.setOfic_reg_id(rset.getString("t_ofic_reg_id"));

//**ESQUELAS
				String estId = rset.getString("tet_estado_titulo_id");
				String refNumTitu = rset.getString("t_refnum_titu");
				String areaRegID = rset.getString("tar_area_reg_id");
				esquela(estId, refNumTitu, areaRegID, dconn, bean);
//**ESQUELAS
				area_registral = areaRegID;
				aux1 = rset.getString("t_reg_pub_id");
				aux4 = rset.getString("t_ofic_reg_id");
				
				bean.setArea_Reg(area_registral);

//**PARA PARTIDAS
				String auxiliarpartida = partida(conn, rset);
				bean.setPartida(auxiliarpartida);
//**PARA PARTIDAS
/*Uso Servicio*/				

			// Para Actos Titulos
				actosTitulo(conn, stmt1, rs1, bean);			
				
				
			// Para Participantes
				boolean juri = participantesPN(conn, stmt1, rs1, bean);
				if(juri)
					participantesPJ(conn, stmt1, rs1, bean);

				lista1.add(bean);
				b = rset.next();
//				hayNext = rset.next();			
			}
			if(lista1.size() == 1){
				GeneralTituloBean bean = (GeneralTituloBean) lista1.get(0);
				String aux_ofic = bean.getReg_pub_id() + "|" + bean.getOfic_reg_id();
				request.setParameter("ano", bean.getAno());
				request.setParameter("numtitu", bean.getTitulo());
				request.setParameter("areareg",area_registral);
				request.setParameter("oficinas", aux_ofic);
				transition("buscarXNroTituloDet", request, response);
				return response;
			}
//	hayNext = rset.next();			

			if(encontro)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);
//*PAGINACION EN EL JSP*//
		  // Verifico que que existen mas de 15 registros	
		  if(tamano > num_pagina && conta > num_pagina)
			 hayNext = true;					  	 
		  	
		  manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);
		  
		  
			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.CONSULTA_TITULOS);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaAct",fechaAct);

		  
						
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista1);
			response.setStyle("listado");
		}
		
		catch(ValidacionException ve){
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1", ve.getMensaje());
		}		
		
		
		catch(CustomException ce){
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
			closeExtra(stmt, rset);
			closeExtra(stmt1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
	}

	protected ControllerResponse runBuscaOrdenState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		try{
			init(request);
			validarSesion(request);
			
			int opcion = Integer.parseInt(request.getParameter("tipo"));

			switch(opcion){
				case 1: transition("buscarXNroTituloG", request, response); break;
				case 2: transition("buscarXPresentantePNG", request, response); break;
				case 3: transition("buscarXPresentantePJG", request, response); break;
				case 4: transition("buscarXPresentantePJRucG", request, response); break;
				case 5: transition("buscarXParticipantePNG", request, response); break;
				case 6: transition("buscarXParticipantePJG", request, response); break;
				case 7: transition("buscarXNroTitulo", request, response); break;
			}
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}
	
	
	
	protected ControllerResponse runBuscarXPresentantePNGState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		Statement stmt = null;
		ResultSet rset = null;
		//Conexiones de Base de Datos
			java.sql.Statement stmt1 = null;
			java.sql.ResultSet rs1 = null;
			
		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);			
		
		try{
			init(request);
			validarSesion(request);
			
			UsuarioBean user = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			int paginacion = Integer.parseInt(request.getParameter("pagina"));
		//Parametro de Ordenamiento
			String h = request.getParameter("orden");
			boolean ordenarXEstado = false;
			
			if(h == null || h.trim().length() <= 0)
				ordenarXEstado = false;
			else if(h.equalsIgnoreCase("on"))
				ordenarXEstado = true;
			else
				ordenarXEstado = false;
					
		//Parametro de Zonas	
			String param = request.getParameter("hid1");
			if(param == null || param.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar como minimo una zona", "errorTitulo");

			StringTokenizer stk = new StringTokenizer(param, "|");
			
			String aux[] = new String[3];
			int y = 0;
			StringBuffer subcadena = null;
			
			int numTokens = stk.countTokens();
			if(numTokens == 1)
				subcadena = new StringBuffer(" = '").append(stk.nextToken()).append("' ");
			else if(numTokens > 1 && numTokens < 13){
				subcadena = new StringBuffer(" IN (");
				if(stk.hasMoreTokens())
					subcadena.append("\'").append(stk.nextToken()).append("\'");
				while (stk.hasMoreTokens()) {
					subcadena.append(", \'").append(stk.nextToken()).append("\'");
				}
				subcadena.append(") ");
			}
		//Recuperando tipo de Búsqueda
			String tipob = request.getParameter("tipob");

		//Recuperando valores de Apellidos y Nombres y Num. de Doc.
			String aux2 = Tarea.reemplazaApos(request.getParameter("nombres"));
			String aux3 = Tarea.reemplazaApos(request.getParameter("apepat"));
			String aux5 = Tarea.reemplazaApos(request.getParameter("apemat"));
			String aux8 = request.getParameter("tipdocpn");
			String aux9 = request.getParameter("numdocpn");
			boolean pase_nombres = true;
			boolean pase_apepat = true;
			boolean pase_apemat = true;
			boolean pase_numdoc = false;
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("nombres", aux2);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("apepat", aux3);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("apemat", aux5);			
			
			if(tipob.equals("N")){
				if( (aux2 == null || aux2.trim().length() <= 0) && (aux3 == null || aux3.trim().length() <= 0))
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar al menos el Apellido Paterno o el Nombre", "errorTitulo");
			
				if(aux2 == null || aux2.trim().length() <= 0)
					pase_nombres = false;
				else
					aux2 = aux2 + "%";
				
				if(aux3 == null || aux3.trim().length() <= 0)
					pase_apepat = false;
				else
					aux3 = aux3 + "%";
	
				if(aux5 == null || aux5.trim().length() <= 0)
					pase_apemat = false;
				else
					aux5 = aux5 + "%";
			}else if(tipob.equals("T")){
				if( (aux9 == null) || (aux9.trim().length() <= 0) )
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar un Número de Documento", "errorTitulo");

				pase_numdoc = true;
			}

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "2");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hid1", param);

			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipdocpn", aux8);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numdocpn", aux9);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipob", tipob);
			
			
		//Parametros varios
			String aux1 = aux[0];  	// REG_PUB_ID
			String aux4 = aux[1];	// OFIC_REG_ID
			String area_registral = null;			

		//VERIFICAR COUNT 1
		StringBuffer sqlc = new StringBuffer();
		boolean hacerCount = false;
		long tamano = 0;
		
		if(paginacion == 1){
			if(tipob.equals("N")){
				hacerCount = true;//veri
				sqlc.append("SELECT /*+ORDERED INDEX(PRESENTANTE INDX_AP_NOM_PRES) */ ");
				sqlc.append(" count(*)");
				sqlc.append(" FROM PRESENTANTE");
				sqlc.append(" WHERE ");
				
				if(pase_apepat){//Obligatorio
					sqlc.append(" PRESENTANTE.APE_PAT LIKE '").append(aux3).append("' ");
				}
				if(pase_apemat){
					sqlc.append("  AND PRESENTANTE.APE_MAT LIKE '").append(aux5).append("' ");
				}
				if(pase_nombres)
					sqlc.append("  AND PRESENTANTE.NOMBRES LIKE '").append(aux2).append("' ");
				
				if(numTokens != 13)
					sqlc.append(" AND PRESENTANTE.PRES_REG_PUB_ID ").append(subcadena.toString());
				
			}else if(pase_numdoc){
				hacerCount = true;//veri
				sqlc.append("SELECT /*+ORDERED INDEX (PRESENTANTE INDEX_PRESENTANTE_NU_DOC) */ ");
				sqlc.append(" count(*)");
				sqlc.append(" FROM PRESENTANTE ");
				sqlc.append(" WHERE PRESENTANTE.NU_DOC = '").append(aux9).append("' ");
				sqlc.append(" AND PRESENTANTE.TI_DOCU = '").append(aux8).append("' ");
				
				if(numTokens != 13)
					sqlc.append(" AND PRESENTANTE.PRES_REG_PUB_ID ").append(subcadena.toString());
			}
		}


		if(hacerCount){
			if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset   = stmt.executeQuery(sqlc.toString());
			rset.next();
			tamano = rset.getLong(1);
			
			if(tamano > Propiedades.getInstance().getMaxResultadosBusqueda() * 2)
				throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda.");
		}
			
		//VERIFICAR COUNT 2
		sqlc = new StringBuffer();
		tamano = 0;
		
		if(paginacion == 1){
			if(tipob.equals("N")){
				hacerCount = true;//veri
				sqlc.append("SELECT /*+ORDERED INDEX(PRESENTANTE INDX_AP_NOM_PRES) INDEX (TITULO INDX_TITU_PRES) */ ");
				sqlc.append(" count(TITULO.REFNUM_TITU)");
				sqlc.append(" FROM PRESENTANTE, TITULO ");
				sqlc.append(" WHERE PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID ");
				sqlc.append(" AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID ");
				sqlc.append(" AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES ");
				sqlc.append(" AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
		
				if(pase_apepat)
					sqlc.append(" AND PRESENTANTE.APE_PAT LIKE '").append(aux3).append("' ");
				if(pase_apemat)
					sqlc.append(" AND PRESENTANTE.APE_MAT LIKE '").append(aux5).append("' ");
				if(pase_nombres)
					sqlc.append(" AND PRESENTANTE.NOMBRES LIKE '").append(aux2).append("' ");
				
				if(numTokens != 13)
					sqlc.append(" AND TITULO.REG_PUB_ID ").append(subcadena.toString());
				
			}else if(pase_numdoc){
				hacerCount = true;//veri
				sqlc.append("SELECT /*+ORDERED INDEX (PRESENTANTE INDEX_PRESENTANTE_NU_DOC) INDEX (TITULO INDX_TITU_PRES) */ ");
				sqlc.append(" count(TITULO.REFNUM_TITU)");
				sqlc.append(" FROM PRESENTANTE, TITULO ");
				sqlc.append(" WHERE PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID ");
				sqlc.append(" AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID ");
				sqlc.append(" AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES ");
				sqlc.append(" AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
				sqlc.append(" AND PRESENTANTE.NU_DOC = '").append(aux9).append("' ");
				sqlc.append(" AND PRESENTANTE.TI_DOCU = '").append(aux8).append("' ");
				
				if(numTokens != 13)
					sqlc.append(" AND TITULO.REG_PUB_ID ").append(subcadena.toString());
			}
		}


		if(hacerCount){
			if (isTrace(this)) System.out.println("SQL>>" + sqlc.toString());
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset   = stmt.executeQuery(sqlc.toString());
			rset.next();
			tamano = rset.getLong(1);
		}else
			tamano = Long.parseLong(request.getParameter("tamano"));


		//Formando el Query principal
		StringBuffer sqlG = new StringBuffer();
		StringBuffer sqlPN = new StringBuffer("SELECT /*+ORDERED INDEX(PRESENTANTE INDX_AP_NOM_PRES) INDEX (TITULO INDX_TITU_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT) */ ");
		StringBuffer sqlTD = new StringBuffer("SELECT /*+ORDERED INDEX (PRESENTANTE INDEX_PRESENTANTE_NU_DOC) INDEX (TITULO INDX_TITU_PRES) INDEX (DETALLE_TITULO INDX_REFNUM_FG_ACT) */ ");
		StringBuffer SQL = new StringBuffer();
		
		//-----------sqlG.append("DISTINCT ");
		sqlG.append("TM_AREA_REGISTRAL.AREA_REG_ID as tar_area_reg_id, ");
		sqlG.append("TM_AREA_REGISTRAL.NOMBRE as regis_nombre, ");
		sqlG.append("TM_AREA_REGISTRAL.DESCRIPCION as regis_descripcion, ");
		sqlG.append("TM_AREA_REGISTRAL.ESTADO as regis_estado, ");
		sqlG.append("TM_AREA_REGISTRAL.PREFIJO as regis_prefijo, ");
		sqlG.append("REGIS_PUBLICO.REG_PUB_ID as publico_reg_pub_id, ");
		sqlG.append("REGIS_PUBLICO.NOMBRE as publico_nombre, ");
		sqlG.append("REGIS_PUBLICO.SIGLAS as publico_siglas, ");
		sqlG.append("TITULO.REFNUM_TITU as t_refnum_titu, ");
		sqlG.append("TITULO.NUM_TITU as t_num_titu, ");
		sqlG.append("TITULO.ANO_TITU as t_ano_titu, ");
		sqlG.append("TITULO.AREA_REG_ID as t_area_reg_id, ");
		sqlG.append("TITULO.REG_PUB_ID as t_reg_pub_id, ");
		sqlG.append("TITULO.OFIC_REG_ID as t_ofic_reg_id, ");
		sqlG.append("TITULO.TS_PRESENT as t_ts_present, ");
		sqlG.append("TITULO.FEC_VENC as t_fec_venc, ");
		sqlG.append("TITULO.NUM_HOJA_PRES as t_num_hoja_pres, ");
		sqlG.append("TITULO.AA_HOJA_PRES as t_aa_hoja_pres, ");
		sqlG.append("TITULO.PRES_OFIC_REG_ID as t_pres_ofic_reg_id, ");
		sqlG.append("TITULO.PRES_REG_PUB_ID as t_pres_reg_pub_id, ");
		sqlG.append("DETALLE_TITULO.REFNUM_TITU as detti_refnum_titu, ");
		sqlG.append("DETALLE_TITULO.NS_DETALLE as detti_ns_detalle, ");
		sqlG.append("DETALLE_TITULO.FG_ACTIVO as detti_fg_activo, ");
		sqlG.append("DETALLE_TITULO.MONTO_LIQ as detti_monto_liq, ");
		sqlG.append("DETALLE_TITULO.ESTADO_TITULO_ID as detti_estado_titulo_id, ");
		sqlG.append("DETALLE_TITULO.PU_CTRL as detti_pu_ctrl, ");
		sqlG.append("DETALLE_TITULO.ES_TITU_CALI as detti_es_titu_cali, ");
		sqlG.append("OFIC_REGISTRAL.REG_PUB_ID as ofic_reg_pub_id, ");
		sqlG.append("OFIC_REGISTRAL.OFIC_REG_ID as ofic_ofic_reg_id, ");
		sqlG.append("OFIC_REGISTRAL.NOMBRE as ofic_nombre, ");
		sqlG.append("OFIC_REGISTRAL.JURIS_ID as ofic_juris_id, ");
		sqlG.append("OFIC_REGISTRAL.USR_ULT_MODIF as ofic_usr_ult_modif, ");
		sqlG.append("TM_ESTADO_TITULO.ESTADO_TITULO_ID as tetitu_estado_titulo_id, ");
		sqlG.append("TM_ESTADO_TITULO.MENSAJE as tetitu_mensaje, ");
		sqlG.append("TM_ESTADO_TITULO.ESTADO as tetitu_estado, ");
		sqlG.append("PRESENTANTE.NUM_HOJA_PRES as pre_num_hoja_pres, ");
		sqlG.append("PRESENTANTE.AA_HOJA_PRES as pre_aa_hoja_pres, ");
		sqlG.append("PRESENTANTE.PRES_OFIC_REG_ID as pre_pres_ofic_reg_id, ");
		sqlG.append("PRESENTANTE.PRES_REG_PUB_ID as pre_pres_reg_pub_id, ");
		sqlG.append("PRESENTANTE.AREA_REG_ID as pre_area_reg_id, ");
		sqlG.append("PRESENTANTE.CUR_PRES as pre_cur_pres, ");
		sqlG.append("PRESENTANTE.TIPO_PER as pre_tipo_per, ");
		sqlG.append("PRESENTANTE.NOMBRES as pre_nombres, ");
		sqlG.append("PRESENTANTE.APE_MAT as pre_ape_mat, ");
		sqlG.append("PRESENTANTE.APE_PAT as pre_ape_pat, ");
		sqlG.append("PRESENTANTE.TI_DOCU as pre_ti_docu, ");
		sqlG.append("PRESENTANTE.NU_DOC as pre_nu_doc, ");
		sqlG.append("PRESENTANTE.TS_PRES as pre_ts_pres ");

		sqlG.append("FROM PRESENTANTE, TITULO, DETALLE_TITULO, TM_AREA_REGISTRAL, OFIC_REGISTRAL, REGIS_PUBLICO, TM_ESTADO_TITULO ");
		sqlG.append("WHERE PRESENTANTE.PRES_REG_PUB_ID = TITULO.PRES_REG_PUB_ID ");
		sqlG.append("AND PRESENTANTE.PRES_OFIC_REG_ID = TITULO.PRES_OFIC_REG_ID ");
		sqlG.append("AND PRESENTANTE.AA_HOJA_PRES = TITULO.AA_HOJA_PRES ");
		sqlG.append("AND PRESENTANTE.NUM_HOJA_PRES = TITULO.NUM_HOJA_PRES ");
		sqlG.append("AND PRESENTANTE.SISTEMA_ID = TITULO.SISTEMA_ID ");
		sqlG.append("AND TITULO.REFNUM_TITU = DETALLE_TITULO.REFNUM_TITU ");		
		sqlG.append("AND DETALLE_TITULO.FG_ACTIVO = '1' ");
		sqlG.append("AND DETALLE_TITULO.ESTADO_TITULO_ID = TM_ESTADO_TITULO.ESTADO_TITULO_ID ");
		sqlG.append("AND TITULO.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID ");
		sqlG.append("AND TITULO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
		sqlG.append("AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
		sqlG.append("AND TITULO.AREA_REG_ID = TM_AREA_REGISTRAL.AREA_REG_ID ");
		sqlG.append("AND TM_AREA_REGISTRAL.ESTADO = '1' ");
		
		
		
	if(tipob.equals("N")){
		sqlPN.append(sqlG.toString());
		
		if(pase_apepat)
			sqlPN.append("AND PRESENTANTE.APE_PAT LIKE '").append(aux3).append("' ");
			
		if(pase_apemat)
			sqlPN.append("AND PRESENTANTE.APE_MAT LIKE '").append(aux5).append("' ");

		if(pase_nombres)
			sqlPN.append("AND PRESENTANTE.NOMBRES LIKE '").append(aux2).append("' ");
		
		if(numTokens != 13)
			sqlPN.append("AND TITULO.REG_PUB_ID ").append(subcadena.toString());
		
		SQL.append(sqlPN.toString());
	}else if(pase_numdoc){
		sqlTD.append(sqlG.toString());
		
		sqlTD.append("AND PRESENTANTE.NU_DOC = '").append(aux9).append("' ");
		sqlTD.append("AND PRESENTANTE.TI_DOCU = '").append(aux8).append("' ");
		
		if(numTokens != 13)
			sqlTD.append("AND TITULO.REG_PUB_ID ").append(subcadena.toString());
		
		SQL.append(sqlTD.toString());
	}

	StringBuffer orden = null;
	if(ordenarXEstado){
		orden = new StringBuffer(" ORDER BY TM_ESTADO_TITULO.ESTADO");
		ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
	}else{
		orden = new StringBuffer(" ORDER BY OFIC_REGISTRAL.NOMBRE, TITULO.ANO_TITU DESC, TITULO.NUM_TITU DESC");
		ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);
	}

	SQL.append(" ").append(orden);


if (isTrace(this)) System.out.println("___verQUERY >> " + SQL.toString());

//*** MANEJO DE LA PAGINACION
Propiedades propiedades = Propiedades.getInstance();

//Connection myConn = conn.getMyConnection();
stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(propiedades.getLineasPorPag());
rset   = stmt.executeQuery(SQL.toString());

if (paginacion > 1)
	rset.absolute(propiedades.getLineasPorPag() * (paginacion - 1));

boolean b = rset.next();

int conta=1;
java.util.List lista1 = new java.util.ArrayList();

boolean encontro = false;
boolean hayNext = false;

while (b==true && conta <= propiedades.getLineasPorPag()){
	encontro = true;
	conta++;
	java.util.List listaActRegistrales = new java.util.ArrayList();
	GeneralTituloBean bean = null;

			//se anadio paginacion
				
				bean= new GeneralTituloBean();
				bean.setSede(rset.getString("publico_nombre")==null?"---":rset.getString("publico_nombre"));
				bean.setDependencia(rset.getString("ofic_nombre")==null?"---":rset.getString("ofic_nombre"));
				bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
				bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
				bean.setTipo_registro(rset.getString("regis_nombre")==null?"---":rset.getString("regis_nombre"));
				//Manuel 02Nov
				//java.util.Date date = rset.getDate("t_ts_present");
				//bean.setFec_presentacion(FechaUtil.dateToString(date));				
				//date = rset.getDate("t_fec_venc");
				//bean.setFec_vencimiento(FechaUtil.dateToString(date));


				//fecha presentacion
				java.sql.Timestamp ts_present = rset.getTimestamp("t_ts_present");
						
				if(ts_present != null)
					bean.setFec_presentacion(FechaUtil.dateTimeToString(ts_present));
				else
					bean.setFec_presentacion("---");

                        
				//fecha vencimiento
				java.util.Date date = rset.getDate("t_fec_venc");
				
				if(date != null)
					bean.setFec_vencimiento(FechaUtil.dateToString(date));
				else
					bean.setFec_vencimiento("---");	


				
				//Manuel 02Nov - Modificado por cjvc77 
				StringBuffer xx = new StringBuffer("");
				if(rset.getString("pre_ape_pat") != null)
					xx.append(rset.getString("pre_ape_pat")).append(" ");
				if(rset.getString("pre_ape_mat") != null)
					xx.append(rset.getString("pre_ape_mat")).append(" ");
				if(rset.getString("pre_nombres") != null)
					xx.append(rset.getString("pre_nombres"));
					
				bean.setPresentante(xx.toString());

				bean.setEstado(rset.getString("tetitu_estado"));
				bean.setUrl_detalle("");
				
				bean.setReg_pub_id(rset.getString("t_reg_pub_id"));
				bean.setOfic_reg_id(rset.getString("t_ofic_reg_id"));

//**ESQUELAS
				String estId = rset.getString("tetitu_estado_titulo_id");
				String refNumTitu = rset.getString("t_refnum_titu");
				String areaRegID = rset.getString("tar_area_reg_id");
				esquela(estId, refNumTitu, areaRegID, dconn, bean);
//**ESQUELAS

				area_registral = areaRegID;
				aux1 = rset.getString("t_reg_pub_id");
				aux4 = rset.getString("t_ofic_reg_id");
				
				bean.setArea_Reg(area_registral);
				
//**PARA PARTIDAS
				String auxiliarpartida = partida(conn, rset);
				bean.setPartida(auxiliarpartida);
//**PARA PARTIDAS
/*Uso Servicio*/	


			// Para Actos Titulos
				actosTitulo(conn, stmt1, rs1, bean);			
				
			// Para Participantes
				boolean juri = participantesPN(conn, stmt1, rs1, bean);
				if(juri)
					participantesPJ(conn, stmt1, rs1, bean);

				lista1.add(bean);
				b = rset.next();
			}
			
			
			if(lista1.size() == 1){
				GeneralTituloBean bean = (GeneralTituloBean) lista1.get(0);
				String aux_ofic = bean.getReg_pub_id() + "|" + bean.getOfic_reg_id();
				request.setParameter("ano", bean.getAno());
				request.setParameter("numtitu", bean.getTitulo());
				request.setParameter("oficinas", aux_ofic);
				request.setParameter("areareg",area_registral);
				transition("buscarXNroTituloDet", request, response);
				return response;
			}
			
			hayNext = rset.next();			
			
			if(encontro)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);

//*PAGINACION EN EL JSP*//
		  manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);
		  
		  
			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.CONSULTA_TITULOS);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaAct",fechaAct);
		  
		  
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista1);
			response.setStyle("listado");
		}
		catch(ValidacionException ve){
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1", ve.getMensaje());
		}		
		catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		}
		catch(DBException dbe){
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
			closeExtra(stmt, rset);
			closeExtra(stmt1, rs1);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	public String partida(Connection conn, java.sql.ResultSet rset) throws SQLException, Exception{
		
				String sql1 = "SELECT DISTINCT P.NUM_PARTIDA FROM PARTIDA P, ASIENTO A WHERE P.REFNUM_PART = A.REFNUM_PART AND P.REG_PUB_ID = ? AND P.OFIC_REG_ID = ? AND A.NUM_TITU = ? AND A.AA_TITU = ? AND P.AREA_REG_ID=?";
				java.sql.PreparedStatement pst = null;
				java.sql.ResultSet rs = null;
				try{
					pst = conn.prepareStatement(sql1);
					pst.setString(1, rset.getString("t_reg_pub_id"));
					pst.setString(2, rset.getString("t_ofic_reg_id"));
					pst.setString(3, rset.getString("t_num_titu"));
					pst.setString(4, rset.getString("t_ano_titu"));
					pst.setString(5, rset.getString("tar_area_reg_id"));
					rs = pst.executeQuery();
					
					if(rs.next())
					{
						StringBuffer cad = new StringBuffer();
						cad.append(rs.getString(1)); 
						while(rs.next())
						{
							cad.append(", ").append(rs.getString(1)); 
						}
						return cad.toString();
					}
					else
						return "---";
				}finally{
					if(rs != null)
						try{rs.close();
						}catch(Exception ex){}
					if(pst != null)
						try{pst.close();
						}catch(Exception ex){}
				}

	}
	public void actosTitulo(Connection conne, Statement stmt1, ResultSet rs1, GeneralTituloBean bean) throws SQLException, Exception{
				/*NO BORRAR: Se descomentará luego*/
				/*
				bean.setTipo_acto("");
				StringBuffer sql1 = new StringBuffer("SELECT ");
				sql1.append("TA.DESCRIPCION ");
				sql1.append("FROM TITULO TI, TM_ACTO TA, ACTOS_TITULO AT ");
				sql1.append("WHERE TI.REFNUM_TITU = AT.REFNUM_TITU ");
				sql1.append("AND AT.COD_ACTO = TA.COD_ACTO ");
				sql1.append("AND TI.ANO_TITU = '").append(bean.getAno()).append("' ");
				sql1.append("AND TI.NUM_TITU = '").append(bean.getTitulo()).append("' ");
				sql1.append("AND TI.REG_PUB_ID = '").append(bean.getReg_pub_id()).append("' ");
				sql1.append("AND TI.OFIC_REG_ID = '").append(bean.getOfic_reg_id()).append("' ");
				sql1.append("AND ROWNUM = 1");
				
				if (isTrace(this)) System.out.print(sql1.toString());
				stmt1 = conne.createStatement();
				rs1 = stmt1.executeQuery(sql1.toString());
				
				if(rs1.next())
					bean.setTipo_acto(rs1.getString(1)!=null?rs1.getString(1):"");
				*/
				return;
	}
	public void closeExtra(Statement stmt1, ResultSet rs1){
		if(rs1 != null)
			try{rs1.close();
			}catch(Exception ex){}
		if(stmt1 != null)
			try{stmt1.close();
			}catch(Exception ex){}
	}
	public boolean participantesPN(Connection conn, Statement stmt1, ResultSet rs1, GeneralTituloBean bean) throws SQLException, Exception{
				/*NO BORRAR: Se descomentará luego
				/*
				boolean juri = true;
				StringBuffer sql1 = new StringBuffer("SELECT ");
				sql1.append("IPT.NS_PERS_NATU as ipt_ns_pers_natu, ");
				sql1.append("IPT.APE_PAT as ipt_ape_pat, ");
				sql1.append("IPT.APE_MAT as ipt_ape_mat, ");
				sql1.append("IPT.NOMBRES as ipt_nombres ");
				sql1.append("FROM TITULO T, IND_PN_PARTIC_TITU IPT ");
				sql1.append("WHERE T.REG_PUB_ID = IPT.COD_REG ");
				sql1.append("AND T.OFIC_REG_ID = IPT.COD_OFIC_REG ");
				sql1.append("AND T.NUM_TITU = IPT.NU_TITU ");
				sql1.append("AND T.ANO_TITU = IPT.AA_TITU ");
				sql1.append("AND T.REG_PUB_ID = '").append(bean.getReg_pub_id()).append("' ");
				//sql1.append("AND T.ANO_TITU = '").append(aux2).append("' ");
				//sql1.append("AND T.NUM_TITU = '").append(aux3).append("' ");
				sql1.append("AND T.ANO_TITU = '").append(bean.getAno()).append("' ");
				sql1.append("AND T.NUM_TITU = '").append(bean.getTitulo()).append("' ");
				sql1.append("AND T.OFIC_REG_ID = '").append(bean.getOfic_reg_id()).append("' ");
				sql1.append("AND ROWNUM = 1");
				
				if (isTrace(this)) System.out.print(">> SQL : " + sql1.toString());
			
				stmt1 = conn.createStatement();
				rs1 = stmt1.executeQuery(sql1.toString());
				while(rs1.next()){
					bean.setNum_sec_participante(String.valueOf(rs1.getLong("ipt_ns_pers_natu")));
					StringBuffer auxi_nombres = new StringBuffer("");
					if(rs1.getString("ipt_ape_pat") != null)
						auxi_nombres.append(rs1.getString("ipt_ape_pat")).append(" ");
					if(rs1.getString("ipt_ape_mat") != null)
						auxi_nombres.append(rs1.getString("ipt_ape_mat")).append(" ");
					if(rs1.getString("ipt_nombres") != null)
						auxi_nombres.append(rs1.getString("ipt_nombres")).append(" ");
						
					bean.setParticipante(auxi_nombres.toString());
					juri = false;
				}
				return juri;
				*/
				return false;
	}
	public void participantesPJ(Connection conn, Statement stmt1, ResultSet rs1, GeneralTituloBean bean) throws SQLException, Exception{
					/*NO BORRAR: Se descomentará luego
					/*
					StringBuffer sql1 = new StringBuffer("SELECT ");
					sql1.append("IPT.NS_PERS_JURI as ipt_ns_pers_juri, ");
					sql1.append("IPT.RAZ_SOC as ipt_raz_soc ");

					sql1.append("FROM TITULO T, IND_PJ_PARTIC_TITU IPT ");

					sql1.append("WHERE T.REG_PUB_ID = IPT.REG_PUB_ID ");
					sql1.append("AND T.OFIC_REG_ID = IPT.OFIC_REG_ID ");
					sql1.append("AND T.NUM_TITU = IPT.NU_TITU ");
					sql1.append("AND T.ANO_TITU = IPT.AA_TITU ");
					sql1.append("AND T.REG_PUB_ID = '").append(bean.getReg_pub_id()).append("' ");
					sql1.append("AND T.ANO_TITU = '").append(bean.getAno()).append("' ");
					sql1.append("AND T.NUM_TITU = '").append(bean.getTitulo()).append("' ");
					sql1.append("AND T.OFIC_REG_ID = '").append(bean.getOfic_reg_id()).append("' ");
					
					if (isTrace(this)) System.out.print(">> SQL : " + sql1.toString());
					
					stmt1 = conn.createStatement();
					rs1 = stmt1.executeQuery(sql1.toString());

					while(rs1.next()){
						bean.setNum_sec_participante(String.valueOf(rs1.getLong("ipt_ns_pers_juri")));
						bean.setParticipante(rs1.getString("ipt_raz_soc")!=null?rs1.getString("ipt_raz_soc"):"");
					}
					*/
					return;
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
	
	
	public void esquela(String estId, String refNumTitu, String areaRegID, DBConnection conn, GeneralTituloBean bean) 
	throws SQLException, Exception
	{
				bean.setUrl_detalle("");
				bean.setUrl_esquela("");
				if(estId.equalsIgnoreCase("50"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "T", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}else if(estId.equalsIgnoreCase("40"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}else if(estId.equalsIgnoreCase("30"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "L", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}else if(estId.equalsIgnoreCase("90"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "L", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}else if(estId.equalsIgnoreCase("100"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}else if(estId.equalsIgnoreCase("120"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "O", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}else if(estId.equalsIgnoreCase("130"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "R", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}else if(estId.equalsIgnoreCase("140"))
				{
					String paramEsq = Generales.formateoUrlEsquela(refNumTitu, "T", areaRegID, false);
					bean.setUrl_detalle(paramEsq);
					bean.setUrl_esquela("[ VER ESQUELA ]");
				}
	}
	
	/**2004/05/10 Kuma Se añadio para tomar estos dos parametros para el mensaje **/
	public String mensajeTitulo(String es_titu_cali, String pu_ctrl)
	{
			if(pu_ctrl.equalsIgnoreCase("01"))
			{
				return "El Título se encuentra: Presentado";
			}
			if(pu_ctrl.equalsIgnoreCase("02"))
			{
				return "El Título se encuentra: En Calificación";
			}
			if(pu_ctrl.equalsIgnoreCase("03"))
			{
				return "El Título se encuentra: En Calificación";
			}
			if(pu_ctrl.equalsIgnoreCase("04"))
			{
				return "El Título se encuentra: En Calificación";
			}
			if(pu_ctrl.equalsIgnoreCase("05"))
			{
				return "El Título se encuentra: En Calificación";
			}
			//mgarate
			if(pu_ctrl.equalsIgnoreCase("12"))
			{
				return "El Título se encuentra: En Calificación";
			}
			if(pu_ctrl.equalsIgnoreCase("13"))
			{
				return "El Título se encuentra: En Calificación";
			}
			if(pu_ctrl.equalsIgnoreCase("14"))
			{
				return "El Título se encuentra: Inscrito";
			}
			if(pu_ctrl.equalsIgnoreCase("15"))
			{
				return "El Título se encuentra: Inscrito";
			}
			//mgarate
			if(pu_ctrl.equalsIgnoreCase("06"))
			{
				if(es_titu_cali.equalsIgnoreCase("31"))
				{
					return "El título se encuentra: Inscrito";
				}
				if(es_titu_cali.equalsIgnoreCase("32"))
				{
					return "El título se encuentra: Tachado";
				}
				if(es_titu_cali.equalsIgnoreCase("33"))
				{
					return "El título se encuentra: Observado";
				}
				if(es_titu_cali.equalsIgnoreCase("34"))
				{
					return "El título se encuentra: Liquidado";
				}
				if(es_titu_cali.equalsIgnoreCase("39"))
				{
					return "El título se encuentra: Observado/Suspendido";
				}
				
			}
			if(pu_ctrl.equalsIgnoreCase("07"))
			{
				if(es_titu_cali.equalsIgnoreCase("31"))
				{
					return "Acerquese a ventanilla a recoger su constancia de inscripcion";
				}
				if(es_titu_cali.equalsIgnoreCase("32"))
				{
					return "Acerquese a ventanilla para informarse sobre los motivos de la tacha";
				}
				if(es_titu_cali.equalsIgnoreCase("33"))
				{
					return "Acerquese a ventanilla a recoger la esquela de observaciones";
				}
				if(es_titu_cali.equalsIgnoreCase("34"))
				{
					return "Debe acercarse a ventanilla a liquidar el monto indicado";
				}
				
				if(es_titu_cali.equalsIgnoreCase("39"))
				{
					return "Puede pasar a recoger el título el cual se encuentra: Observado/Suspendido";
				}
				
			}
			if(pu_ctrl.equalsIgnoreCase("08"))
			{
				return "El título ha sido reingresado";
			}
			if(pu_ctrl.equalsIgnoreCase("09"))
			{
				if(es_titu_cali.equalsIgnoreCase("31"))
				{
					return "El título ya ha sido entregado al usuario y su estado es: Inscrito";
				}
				if(es_titu_cali.equalsIgnoreCase("32"))
				{
					return "El título ya ha sido entregado al usuario y su estado es: Tachado";
				}
				if(es_titu_cali.equalsIgnoreCase("33"))
				{
					return "El título ya ha sido entregado al usuario y su estado es: Observado";
				}
				if(es_titu_cali.equalsIgnoreCase("34"))
				{
					return "El título ya ha sido entregado al usuario y su estado es: Liquidado";
				}
				if(es_titu_cali.equalsIgnoreCase("39"))
				{
					return "El título ya ha sido entregado al usuario y su estado es: Observado/Suspendido";
				}
				
			}
			if(pu_ctrl.equalsIgnoreCase("16"))
			{
				return "El Titulo se encuentra en Apelacion";
			}
			return "";
	}
	/**2004/05/10 Kuma Fin **/
	
	/**2004/05/11 Kuma Se añadio para tomar estos dos parametros para el resultado **/
	public String resultadoTitulo(String es_titu_cali, String pu_ctrl)
	{
			if(pu_ctrl.equalsIgnoreCase("08"))
			{
				return "REINGRESADO";
			}
			if(pu_ctrl.equalsIgnoreCase("16"))
			{
				return "APELADO";
			}
			//Inicio:mgarate
			if(pu_ctrl.equalsIgnoreCase("01"))
			{
				return "PRESENTADO";
			}
			if(pu_ctrl.equalsIgnoreCase("02"))
			{
				return "EN CALIFICACION";
			}
			if(pu_ctrl.equalsIgnoreCase("03"))
			{
				return "EN CALIFICACION";
			}
			if(pu_ctrl.equalsIgnoreCase("04"))
			{
				return "EN CALIFICACION";
			}
			if(pu_ctrl.equalsIgnoreCase("05"))
			{
				return "EN CALIFICACION";
			}
			if(pu_ctrl.equalsIgnoreCase("12"))
			{
				return "EN CALIFICACION";
			}
			if(pu_ctrl.equalsIgnoreCase("13"))
			{
				return "EN CALIFICACION";
			}
			if(pu_ctrl.equalsIgnoreCase("14"))
			{
				return "INSCRITO";
			}
			if(pu_ctrl.equalsIgnoreCase("15"))
			{
				return "INSCRITO";
			}
			//fin:mgarate
			if(es_titu_cali.equalsIgnoreCase("31"))
			{
				return "INSCRITO";
			}
			if(es_titu_cali.equalsIgnoreCase("32"))
			{
				return "TACHADO";
			}
			if(es_titu_cali.equalsIgnoreCase("33"))
			{
				return "OBSERVADO";
			}
			if(es_titu_cali.equalsIgnoreCase("34"))
			{
				return "LIQUIDADO";
			}
			if(es_titu_cali.equalsIgnoreCase("39"))
			{
				return "OBSERVADO/SUSPENDIDO";
			}
			if(es_titu_cali.equalsIgnoreCase(""))
			{
				return "EN CALIFICACION";
			}
			return "";
				
	}
	/**2004/05/11 Kuma Fin **/
	/** JBUGARIN ANOTACION DE INSCRIPCION 23/02/2007 **/
		protected ControllerResponse runAnotacionInscripcionState(
				ControllerRequest request,
				ControllerResponse response)
				throws ControllerException {
					//HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
					DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
				try{
					init(request);
			
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
					String numTitulo = request.getParameter("numTitulo");				
					String anho = request.getParameter("anho");
					String actoRegistral = request.getParameter("actoRegistral");
					String fechaPresentacion = request.getParameter("fechaPresentacion");
					String partida = request.getParameter("partida");
					String oficina = request.getParameter("oficina");
					String zonaRegistral = request.getParameter("zona");
				
					//AGREGADO JBUGARIN ANOTACION DE INSCRIPCION
				String recibo = "";
				String usuarioRegistro = "";
				//
				
				//SAUL - SE COMENTA EL LLAMADO A LOS CAMPOS 
				// - CAMPO_RECIBOS
				// - CAMPO_ID_USUA_CREA
				/** Consultando Tabla Asientos jbugarijn anotacion inscripcion **/
				DboAsiento dboAsiento = new DboAsiento(dconn);
				/*dboAsiento.setFieldsToRetrieve(DboAsiento.CAMPO_RECIBOS + "|" + DboAsiento.CAMPO_ID_USUA_CREA);
				dboAsiento.setField(DboAsiento.CAMPO_NUM_TITU,numTitulo);
				if(dboAsiento.find()==true){
					recibo=dboAsiento.getField(DboAsiento.CAMPO_RECIBOS);
					usuarioRegistro = dboAsiento.getField(DboAsiento.CAMPO_ID_USUA_CREA);
							
				}
				*/				
				/** fin **/
				
					ExpressoHttpSessionBean.getRequest(request).setAttribute("numTitulo",numTitulo);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("anho",anho);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("actoRegistral",actoRegistral);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaPresentacion",fechaPresentacion);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("partida",partida.substring(23,32));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("oficina",oficina);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("asiento",Constantes.ACTO_ANOTACION_INSCRIPCION);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("zonaRegistral",zonaRegistral);
					
					/** agragado jbugarin anotacion inscripcion **/
				ExpressoHttpSessionBean.getRequest(request).setAttribute("recibo",recibo);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("usuarioRegistro",usuarioRegistro);
				/** **/
					
					response.setStyle("anotacionInscripcion");
				
				} catch (Throwable ex) {
					log(Errors.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					response.setStyle("error");
				} finally {
					//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
					pool.release(conn);
					end(request);
				}
				return response;
			}
		
	
		/** FIN **/
		/*
		 * Inicio:jascencio:11/07/07
		 * CC:REGMOBCON
		 */
		public List listadopartidasBloqueadas(Connection conn, Statement stmt, ResultSet rs, DetalleTituloBean detalle) throws SQLException,Exception{ 
			ArrayList lista			= null;
			StringBuffer sql		= null;
			String anioTitulo		= null;
			String numeroTitulo		= null;
			String registroPublico	= null;
			String oficinaRegistral	= null;
			String refNumeroTitulo	= null;
			PreparedStatement pstmt = null;
			ResultSet rset 			= null;
			ArrayList listaPartidas	= null;
			ArrayList listaTitulos	= null;
			
			try {
				sql=new StringBuffer();
				numeroTitulo=detalle.getNum_titulo();
				anioTitulo = detalle.getAno_titulo();
				refNumeroTitulo=rs.getString("t_refnum_titu");
				//int intrefNumTitulo = Integer.parseInt(refNumeroTitulo);
				sql.append( " select t.refnum_titu,t.refnum_orden,t.reg_pub_id,t.ofic_reg_id,t.ano_titu,t.num_titu,o.nombre " +
							" from titulo_orden t, ofic_registral o " +
							" where t.refnum_titu='"+refNumeroTitulo+"' "+
							" and o.reg_pub_id=t.reg_pub_id " +
							" and o.ofic_reg_id=t.ofic_reg_id " );
											
				if (isTrace(this)) System.out.println(sql.toString());

				pstmt=conn.prepareStatement(sql.toString());
				rset=pstmt.executeQuery();
				boolean hayResultado = rset.next();
				listaTitulos=hayResultado?new ArrayList():null;
				Map titulo=null;
				while(hayResultado){
					titulo = new HashMap();
					titulo.put("refNumeroTitulo", rset.getString("refnum_titu"));
					titulo.put("refNumeroOrden",rset.getString("refnum_orden"));
					titulo.put("registro", rset.getString("reg_pub_id"));
					titulo.put("oficina", rset.getString("ofic_reg_id"));
					titulo.put("anio", rset.getString("ano_titu"));
					titulo.put("numeroTitulo", rset.getString("num_titu"));
					titulo.put("nombreOficina", rset.getString("nombre"));
					listaTitulos.add(titulo);
					hayResultado = rset.next();
				}
				boolean hayPartidas = false;
				if(listaTitulos != null && listaTitulos.size() > 0){
					hayPartidas = true;
					for(Iterator itera=listaTitulos.iterator();itera.hasNext();){
						
						Map mapTitulo=(Map) itera.next();
						sql.delete(0,sql.length());
						hayResultado = false;
						
						sql.append("SELECT TB.NUM_PARTIDA as partidabloqueada ");
						sql.append("FROM TA_BLOQ_PARTIDA TB ");
						sql.append("WHERE ");
						sql.append("TB.ANO_TITU ='").append(mapTitulo.get("anio")).append("' ");
						sql.append("AND TB.NUM_TITU ='").append(mapTitulo.get("numeroTitulo")).append("' ");
						sql.append("AND TB.REG_PUB_ID ='").append(mapTitulo.get("registro")).append("' ");
						sql.append("AND TB.OFIC_REG_ID ='").append(mapTitulo.get("oficina")).append("' ");
						sql.append("and TB.Num_Partida <>'").append(Constantes.NUMERO_TITULO_SIN_PARTIDAS).append("'");
						sql.append("AND TB.ESTADO='1' ");
						
						if (isTrace(this)) System.out.println(sql.toString());
						
						pstmt=conn.prepareStatement(sql.toString());

						rset=pstmt.executeQuery();
						
						hayResultado = rset.next();
						listaPartidas = hayResultado?new ArrayList():null;
						
						if(hayResultado && hayPartidas){
							lista = new ArrayList();
							hayPartidas = false;
						}
						
						while(hayResultado){
							listaPartidas.add(rset.getString("partidabloqueada"));
							hayResultado = rset.next();
						}
						if(listaPartidas!=null){
							
							for(Iterator it=listaPartidas.iterator();it.hasNext();){
								boolean hayRegistros = false;
								String auxPartida=(String) it.next();
								sql=new StringBuffer();
								sql.append("SELECT TB.NUM_PARTIDA as partidabloqueada,");
								sql.append("TB.ANO_TITU as ano_titulo,");
								sql.append("TB.NUM_TITU as num_titulo,");
								sql.append("TB.reg_pub_id,");
								sql.append("TB.ofic_reg_id,");
								sql.append("TB.area_reg_id ");
								sql.append("FROM TA_BLOQ_PARTIDA TB ");
								sql.append("WHERE TB.NUM_PARTIDA= '").append(auxPartida).append("' ");
								sql.append("  AND TB.ESTADO='1' ");
								if(numeroTitulo!=null && anioTitulo!=null){
									sql.append("and not(TB.NUM_TITU ='").append(numeroTitulo).append("' ");
									sql.append("AND TB.ANO_TITU ='").append(anioTitulo).append("') ");
								}
								if (isTrace(this)) System.out.println(sql.toString());
								pstmt=conn.prepareStatement(sql.toString());
								rset=pstmt.executeQuery();
								
								PartidaBloqueadaBean partidaBloqBean=null;
								StringBuffer url=null;
								hayRegistros = rset.next();
								
								
								while(hayRegistros){
									partidaBloqBean=new PartidaBloqueadaBean();
									partidaBloqBean.setPartidaBloqueada(auxPartida);
									partidaBloqBean.setAnoTitulo(rset.getString("ano_titulo"));
									partidaBloqBean.setNumeroTitulo(rset.getString("num_titulo"));
									partidaBloqBean.setNombreOficina((String)mapTitulo.get("nombreOficina"));
									partidaBloqBean.setAreaRegistral(rset.getString("area_reg_id"));
									partidaBloqBean.setRegistro(rset.getString("reg_pub_id"));
									partidaBloqBean.setOficina(rset.getString("ofic_reg_id"));
									lista.add(partidaBloqBean);
									hayRegistros = rset.next();
								}
							}
						}						
					}
				}
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			finally{
				closeExtra(pstmt, rset);
			}
			return lista;
		}
		
		public List listadoActosRegistrales (Connection conn, Statement stmt, ResultSet rs, GeneralTituloBean bean) throws SQLException, Exception{
			ArrayList lista=null;
			StringBuffer sql=null;
			String numeroTitulo=null;
			String anioTitulo=null;
			PreparedStatement pstmt=null;
			ResultSet rset=null;
			
			try {
				sql=new StringBuffer();
				numeroTitulo=bean.getTitulo();
				anioTitulo=bean.getAno();
				
				sql.append("SELECT ta.descripcion ")
				   .append("FROM actos_titulo act, TM_ACTO ta, TITULO Tit ")
				   .append("WHERE tit.refnum_titu=act.refnum_titu ")
				   .append("and act.cod_acto=ta.cod_acto ")
				   .append("and tit.num_titu='").append(numeroTitulo).append("' ")
				   .append("and tit.ano_titu='").append(anioTitulo).append("' ")
				   .append("order by act.ns_acto_titu");
				
				if (isTrace(this)) System.out.println("[-->]" + sql.toString());
				
				pstmt=conn.prepareStatement(sql.toString());
				rset=pstmt.executeQuery();
				
				if(rset.next()){
					lista=new ArrayList();
					lista.add(rset.getString("descripcion"));
				}
				
				while(rset.next()){
					
					lista.add(rset.getString("descripcion"));
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				closeExtra(pstmt, rset);
			}
			return lista;
		}
		
		/*
		 * Fin:jascencio
		 */
}

