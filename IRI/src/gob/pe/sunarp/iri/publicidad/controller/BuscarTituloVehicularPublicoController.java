package gob.pe.sunarp.iri.publicidad.controller;


import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.util.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.sql.ResultSet;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gob.pe.sunarp.extranet.util.*;


public class BuscarTituloVehicularPublicoController extends ControllerExtension {


	private String thisClass = BuscarTituloVehicularPublicoController.class.getName() + ".";


	public BuscarTituloVehicularPublicoController() {
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("buscarXNroTitulo", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("mostrarEsquela", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("verEsquela", "Ventana de Busq. x Apellidos y Nombres."));
		setInitialState("muestra");
	}




	public String getTitle() {
		return new String("BuscarTituloVehicularPublicoController");
	}


	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		try{
			//init(request);
			if(request.getParameter("detalle") != null && request.getParameter("detalle").equals("SI)"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", null);
								
			ExpressoHttpSessionBean.getRequest(request).setAttribute("odev", request.getParameter("CboOficinas"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("adev", request.getParameter("ano"));
			response.setStyle("muestra");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runBuscarXNroTituloState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
		DBConnectionFactoryV pool = DBConnectionFactoryV.getInstance();
		Connection conn = null;

		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pst1 = null;
		java.sql.ResultSet rs1 = null;			


		try{
			init(request);
			conn = pool.getConnection();
			//conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			//connLocal = poolLocal.getConnection();
			//connLocal.setAutoCommit(false);
			//DBConnection dconnLocal = new DBConnection(connLocal);
			
			
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);			
			String aux[] = new String[3];
			StringTokenizer stk = null;
			String area_registral= null;
			String mensaje_adicional = "";
	
			try{
				stk = new StringTokenizer(request.getParameter("oficinas"), "|");
				int y = 0;
					  
				while (stk.hasMoreTokens()) {
					aux[y++] = stk.nextToken();
				}
			}catch(Exception e1){
				throw new CustomException(Errors.EC_MISSING_PARAM, "Error al obtener la oficina registral", "errorTitulo");
			}
			
			String aux1 = aux[0];							//RegPubId
			String aux2 = request.getParameter("ano");
			String aux3 = request.getParameter("numtitu");
			String aux4 = aux[1];							//OficRegId
			
			boolean pase = true;
			
			if(aux3 == null || aux3.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar un Numero de Titulo a buscar", "errorTitulo");
			
			DetalleTituloBean detalle = null;

//**********inicio

			StringBuffer sql = new StringBuffer("SELECT ");
			sql.append(" RPV.TA_TITU.AN_TITU");
			sql.append(",RPV.TA_TITU.NO_TITU");			
			sql.append(",RPV.TA_TITU.RZ_SOCL");
			sql.append(",RPV.TA_TITU.CO_SECC");
			sql.append(",to_char(RPV.TA_TITU.FH_PRES, 'dd/mm/yyyy hh:mm:ss') AS FE_PRES");
			sql.append(",RPV.TA_TITU.ES_TITU");
			//sql.append(",RPV.TA_TITU.ES_TITU_CALI");
			sql.append(",RPV.TA_TITU.PU_CTRL");
			sql.append(",RPV.TA_TITU.FE_VENC");
			sql.append(",RPV.TA_TITU.TI_DOCU_PRES");
			sql.append(",RPV.TA_TITU.LE_PRES");
			//sql.append(",DECODE(RPV.TA_TITU.ES_TITU,'01','PRESENTADO','02','REVISADO','03','CALIFICADO','04','DESPACHADO','05','REINGRESADO','06','PASE')");
			sql.append(",DECODE(RPV.TA_TITU.ES_TITU_CALI,'31','INSCRITO','32','TACHADO','33','OBSERVADO','34','LIQUIDADO') AS ES_TITU_CALI");
			sql.append(",RPV.CO_ESTA_TITU.CO_ESTA_TITU");
			sql.append(",RPV.CO_ESTA_TITU.DE_ESTA_TITU");
			sql.append(",RPV.CO_PTOS_CTRL.DE_PU_CTRL");
			sql.append(",RPV.CO_SECC.DE_SECC");
			sql.append(",RPV.CO_TIPO_DOCU.DE_TIPO_DOCU");
			sql.append(",RPV.TA_TITU.CO_EMPL_TECN");
			sql.append(",RPV.TA_TITU.NO_DIAS_PROR ");
			sql.append(",RPV.SF_DIAS_LABO_RETRO(RPV.TA_TITU.FE_VENC,5) AS FE_SUBS");
			sql.append(",RPV.SF_ZONA_TITU(RPV.TA_TITU.AN_TITU,RPV.TA_TITU.NO_TITU,'1') AS ZONA ");
			sql.append(",RPV.TA_TITU_PLAC.NO_PLAC");
			sql.append(",RPV.CO_ACTO.DE_ACTO");
			sql.append(" FROM ");
			sql.append(" RPV.TA_TITU ");
			sql.append(" ,RPV.CO_ESTA_TITU ");
			sql.append(" ,RPV.CO_PTOS_CTRL ");
			sql.append(" ,RPV.CO_SECC ");
			sql.append(" ,RPV.CO_TIPO_DOCU ");
			sql.append(" ,RPV.TA_TITU_PLAC ");
			sql.append(" ,RPV.TA_TITU_ACTO ");
			sql.append(" ,RPV.CO_ACTO ");
			sql.append(" WHERE ");
			sql.append(" (RPV.TA_TITU.ES_TITU=CO_ESTA_TITU.CO_ESTA_TITU(+)) ");
			sql.append(" AND (RPV.TA_TITU.CO_SECC=CO_SECC.CO_SECC(+)) ");
			sql.append(" AND (RPV.TA_TITU.PU_CTRL=CO_PTOS_CTRL.CO_PU_CTRL(+)) ");
			sql.append(" AND (RPV.TA_TITU.TI_DOCU_PRES=CO_TIPO_DOCU.CO_TIPO_DOCU(+)) ");
			sql.append(" AND (RPV.TA_TITU_PLAC.AN_TITU=RPV.TA_TITU.AN_TITU (+)) ");  
			sql.append(" AND (RPV.TA_TITU_PLAC.NO_TITU=RPV.TA_TITU.NO_TITU (+)) "); 
			sql.append(" AND (RPV.TA_TITU_PLAC.AN_TITU =RPV.TA_TITU_ACTO.AN_TITU(+)) ");  
			sql.append(" AND (RPV.TA_TITU_PLAC.NO_TITU =RPV.TA_TITU_ACTO.NO_TITU(+)) "); 
			sql.append(" AND (RPV.TA_TITU_PLAC.NO_SECU_PLAC =RPV.TA_TITU_ACTO.NO_SECU_PLAC(+)) "); 
			sql.append(" AND (RPV.TA_TITU_ACTO.CO_ACTO=RPV.CO_ACTO.CO_ACTO(+)) ");
			sql.append(" AND (RPV.TA_TITU.AN_TITU='").append(aux2).append("') ");
			sql.append(" AND (RPV.TA_TITU.NO_TITU='").append(aux3).append("') ");
			 
			if (isTrace(this)) System.out.print(">> SQL : " + sql.toString());
			
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql.toString());
				
				java.util.List listaActRegistrales = new java.util.ArrayList();
				java.util.List listaPlacas = new java.util.ArrayList();
				pst1 = null;
				rs1 = null;
				java.util.Date date = null; 
				String str_date=null;
				boolean primera_vez = true;
				String ssql = null;
				detalle = new DetalleTituloBean();
				int int_flag=0;
				while(rs.next()){
					int_flag=1;


					if(primera_vez){
					/*		if(rs.getString("dt_estado_titulo_id") != null && rs.getString("dt_estado_titulo_id").equals("70")){
								ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "Este titulo ya fue recogido");
								transition("muestra", request, response);
								return response;
							}
					*/

						//detalle.setNum_titulo(rs.getString("t_num_titu"));
						detalle.setNum_titulo(aux3);
						//detalle.setAno_titulo(rs.getString("t_ano_titu"));
						detalle.setAno_titulo(aux2);
						detalle.setOficina(request.getParameter("txt_oficinas"));
						//detalle.setMensaje(rs.getString("tet_mensaje"));


						date = rs.getDate("FE_VENC");
						if(date != null)
							detalle.setVencimiento(FechaUtil.dateToString(date));
						else
							detalle.setVencimiento(" ");


					
						/*Obteniendo el nombre del Presentante
						
						
						
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
							}
							
							if(rs.getString("p_ape_pat") != null)
								lll.append(rs.getString("p_ape_pat")).append(" ");
							if(rs.getString("p_ape_mat") != null)
								lll.append(rs.getString("p_ape_mat")).append(" ");
							if(rs.getString("p_nombres") !=null)
								lll.append(rs.getString("p_nombres"));
							
							detalle.setPresentante_nom(lll.toString());
							detalle.setPresentante_num_doc(rs.getString("p_nu_doc"));
							RPV.CO_TIPO_DOCU.DE_TIPO_DOCU
						Fin: Obteniendo el nombre del Presentante*/
						//java.util.List listaActRegistrales = new java.util.ArrayList();
						listaActRegistrales.add(rs.getString("DE_ACTO"));
						
						//java.util.List listaPlacas = new java.util.ArrayList();
						//listaPlacas.add(rs.getString("NO_PLAC"));

						detalle.setPresentante_nom(rs.getString("RZ_SOCL"));
						detalle.setPresentante_nom(rs.getString("RZ_SOCL"));
						detalle.setPresentante_num_doc(rs.getString("DE_TIPO_DOCU") + " - " +rs.getString("LE_PRES")  );
						
						str_date = rs.getString("FE_PRES");
						
						if(str_date != null)
							//detalle.setFecha(FechaUtil.dateTimeToString(new java.sql.Timestamp(date.getTime())));
							detalle.setFecha(str_date);
						else
							detalle.setFecha(" ");
						
						detalle.setFecha_ult_sinc(FechaUtil.getCurrentDateTime());
						
						String estId = rs.getString("ES_TITU");
						//String est =  new StringBuffer(rs.getString("DE_ESTA_TITU")).append(mensaje_adicional).toString();
						String est =  new StringBuffer(rs.getString("ES_TITU_CALI")).append(mensaje_adicional).toString();
						detalle.setResultado(est);
						String men = rs.getString("CO_ESTA_TITU");
						String msj = new String("");
						if(!(men==null))
						{
						if(men.equals("01"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("02"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("03"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("04"))
							msj = "Titulo Despachado";
						if(men.equals("05"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("06"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("07"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("08"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("09"))
							msj = "El Titulo se encuentra en Apelacion";
						if(men.equals("10"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("11"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("31"))
							msj = "Acerquese a ventanilla a recoger su constancia de inscripcion";
						if(men.equals("32"))
							msj = "Acerquese a ventanilla para informarse sobre los motivos de la tacha";
						if(men.equals("33"))
							msj = "Acerquese a ventanilla a recoger la esquela de observaciones";
						if(men.equals("34"))
							msj = "Debe acercarse a ventanilla a liquidar el monto indicado";
						if(men.equals("35"))
							msj = "Su Titulo se encuentra Prorrogado";
						if(men.equals("36"))
							msj = "Su Titulo se encuentra en estado Reservado";
						if(men.equals("37"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("38"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("39"))
							msj = "Su Titulo esta en Proceso de Calificacion";
						if(men.equals("99"))
							msj = "Por favor comuniquese con la Zona IX";
						}
						detalle.setMensaje(msj);

						primera_vez = false;
						//ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", detalle);
					}
					//listaActRegistrales.add(rs.getString("ta_descripcion"));
					listaPlacas.add(rs.getString("NO_PLAC"));

				}
				if(int_flag==1){
				//listaActRegistrales.add(rs.getString("ta_descripcion"));
				//listaPlacas.add(rs.getString("NO_PLAC"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", detalle);
					java.util.List listaActEntidades  = new java.util.ArrayList();
		//	Seteando Valores
					ExpressoHttpSessionBean.getRequest(request).setAttribute("listaRegistrales", listaActRegistrales);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("listaPlacas", listaPlacas);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("listaEntidades", listaActEntidades);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("oficina", request.getParameter("oficinas"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", request.getParameter("ano"));
					
					response.setStyle("detalle");
					conn.commit();
				}else{
					ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "Este titulo no existe, por favor verifique el número de titulo y/o los datos del mismo");
					transition("muestra", request, response);
				}

		}catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
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




	public void closeExtra(java.sql.Statement stmt1, java.sql.ResultSet rs1){
		if(rs1 != null)
			try{rs1.close();
			}catch(Exception ex){}
		if(stmt1 != null)
			try{stmt1.close();
			}catch(Exception ex){}
	}		
}


