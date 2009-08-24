package gob.pe.sunarp.extranet.publicidad.controller;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;
import org.apache.soap.util.Bean;

import gob.pe.sunarp.extranet.util.*;

public class BuscarTituloPublicoController extends ControllerExtension {

	private String thisClass = BuscarTituloPublicoController.class.getName() + ".";

	public BuscarTituloPublicoController() {
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("buscarXNroTitulo", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("buscarXNroTituloDet", "Ventana detalle de Busq. x Apellidos y Nombres."));
		/** JBUGARIN ANOTACION INSCRIPCION 23/02/2007 **/
		addState(new State("anotacionInscripcion", "Ventana que muestra la anotacion de inscripcion del titulo"));
		/** FIN**/
		//addState(new State("mostrarEsquela", "Ventana de Busq. x Apellidos y Nombres."));
		//addState(new State("verEsquela", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("buscaOrden", "Ventana de Busq. x Organizacion."));
		setInitialState("muestra");
	}

	public String getTitle() {
		return new String("BuscarTituloPublicoController");
	}

	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		try{
			init(request);
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

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		java.sql.Statement stmt = null;
		java.sql.Statement stmt1 = null;
		//java.sql.ResultSet rs = null;
		ResultSet rset = null;
		java.sql.PreparedStatement pst1 = null;
		java.sql.ResultSet rs1 = null;
//			long tamano = 0;
		//jbugarin anotacion de inscripcion
		String esta = "";
		String ctrl = "";
		try {
			init(request);
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
			
		String h = request.getParameter("orden");
		boolean ordenarXEstado = false;
		if(h == null || h.trim().length() <= 0)
			ordenarXEstado = false;
		else if(h.equalsIgnoreCase("on"))
			ordenarXEstado = true;
		else
			ordenarXEstado = false;
		
		ExpressoHttpSessionBean.getRequest(request).setAttribute("tipo", "7");
		ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", aux2);
		ExpressoHttpSessionBean.getRequest(request).setAttribute("numtitu", aux3);
		ExpressoHttpSessionBean.getRequest(request).setAttribute("ordOficina", aux4);
		ExpressoHttpSessionBean.getRequest(request).setAttribute("regPub", aux1);
		
//	 Agregado JACR - inicio
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
//		 Agregado JACR - fin

//				if (!esTituloPresentado) {

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
				
				if(ordenarXEstado){
					sql.append(" ORDER BY TM_ESTADO_TITULO.ESTADO");
					ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", "ON");
				}else{
					sql.append(" ORDER BY OFIC_REGISTRAL.NOMBRE, TITULO.ANO_TITU DESC, TITULO.NUM_TITU DESC");
					ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);
				}
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("orden", null);

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
				java.util.Date date = null;
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
			
				java.util.List listaActRegistrales = new java.util.ArrayList();
				GeneralTituloBean bean = new GeneralTituloBean();
				
				bean.setSede(rset.getString("rp_nombre")==null?"---":rset.getString("rp_nombre"));
				bean.setDependencia(rset.getString("or_nombre")==null?"---":rset.getString("or_nombre"));
				bean.setAno(rset.getString("t_ano_titu")==null?"---":rset.getString("t_ano_titu"));
				bean.setTitulo(rset.getString("t_num_titu")==null?"---":rset.getString("t_num_titu"));
				bean.setTipo_registro(rset.getString("tar_nombre")==null?"---":rset.getString("tar_nombre"));
				
				//date = rset.getDate("t_ts_present");
				//bean.setFec_presentacion(FechaUtil.dateToString(date));
				
				//date = rset.getDate("t_fec_venc");
				//bean.setFec_vencimiento(FechaUtil.dateToString(date));

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
				date = rset.getDate("t_fec_venc");
					
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
				ctrl = puCtrl;
				if(puCtrl == null) puCtrl = "";
				String esTituCali = rset.getString("es_titu_cali");
				esta = esTituCali;
				
			
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
//	**ESQUELAS
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
				
//	**ESQUELAS
				area_registral = rset.getString("tar_area_reg_id");
				aux1 = rset.getString("t_reg_pub_id");
				aux4 = rset.getString("t_ofic_reg_id");
				
				bean.setArea_Reg(areaRegID);

//	**PARA PARTIDAS
				String auxiliarpartida = partida(conn, rset);
				bean.setPartida(auxiliarpartida);
//	**PARA PARTIDAS
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
				BuscarTituloController buscarTituloController = new BuscarTituloController();
				List listaActosReg=buscarTituloController.listadoActosRegistrales(conn, stmt, rset, bean);
				bean.setListadoActos(listaActosReg);
				/*
				 * Fin:jascencio
				 */
				lista1.add(bean);
				b = rset.next();
			}
			/** obteniendo la descripcion de la zona registral jbugarin anotacion de inscripcion **/
			String descZona = "";
			
			
			if(lista1.size() == 1){
				GeneralTituloBean bean = (GeneralTituloBean) lista1.get(0);
				String aux_ofic = bean.getReg_pub_id() + "|" + bean.getOfic_reg_id();
				request.setParameter("ano", bean.getAno());
				request.setParameter("numtitu", bean.getTitulo());
				request.setParameter("areareg",area_registral);
				request.setParameter("oficinas", aux_ofic);
				transition("buscarXNroTituloDet", request, response);
				//jbugarin 23/02/2007
				DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
											dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
											dboRegisPublico.setField(DboRegisPublico.CAMPO_REG_PUB_ID,bean.getReg_pub_id());
											if(dboRegisPublico.find()== true){
											descZona = dboRegisPublico.getField(DboRegisPublico.CAMPO_NOMBRE); 
											}
               	
				ExpressoHttpSessionBean.getRequest(request).setAttribute("estado",esta);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ctrl",ctrl);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("zonaRegistral",descZona);
				
			/** FIN **/	
				
				return response;
			}

			hayNext = rset.next();			

				if(encontro)
				{
				//2 noviembre

					ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", "SI");
				}
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("encontro", null);
					
										
			
	//*PAGINACION EN EL JSP*//
			manejoPaginacion(propiedades, request, paginacion, hayNext, tamano, num_pagina);
			
			//ETIQUETA		
																
			double tarifa = 0;
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = "Invitado";
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
// Agregado por JACR - inicio
	
   protected ControllerResponse runBuscarXNroTituloDetState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
				
			// Fecha: 2003-07-31
			// Autor: HP
			// Modificación: Corregir error al momento de buscar el No. Partida al que fue inscrito el título
			
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			
			java.sql.Statement stmt = null;
			java.sql.ResultSet rs = null;
			java.sql.PreparedStatement pst1 = null;
			java.sql.ResultSet rs1 = null;
			
			String refNumTituPcm = "";  //mgarate:27/10/2006 PCM
			
			try{
				init(request);
			//	validarSesion(request);

				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);

			//	UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);

				String mensaje_adicional = "";
				String area_registral= null;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("odev", request.getParameter("CboOficinas"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("oficina", request.getParameter("CboOficinas"));
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
//					esTituloPresentado = true;
					esTituloPresentado = false;
					ExpressoHttpSessionBean.getRequest(request).setAttribute("presentado", "X");
					ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", detalle);
					System.out.println(ExpressoHttpSessionBean.getRequest(request).getAttribute("presentado"));
					response.setStyle("detalle");
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
							
							//if(!user.getFgInterno()){
							/**2004/05/10 Kuma Se comento para que no se vaya en caso el estado sea 70 **/
							/*	if(rs.getString("dt_estado_titulo_id") != null && rs.getString("dt_estado_titulo_id").equals("70")){
									ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "Este titulo ya fue recogido");
									transition("muestra", request, response);
									return response;
								}
							*/	
							/**2004/05/10 Kuma Fin**/
							//}
							
							/*
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
							}*/

							detalle.setNum_titulo(rs.getString("t_num_titu"));
							detalle.setAno_titulo(rs.getString("t_ano_titu"));
							detalle.setOficina(rs.getString("or_nombre"));
							/**2004/05/10 Kuma Se cambia el metodo de recuperacion del mensaje para vehicular, ahora depende del pu_ctrl y el es_titu_cali**/
							//detalle.setMensaje(rs.getString("tet_mensaje"));
							String areaRegID = rs.getString("t_area_reg_id");
							String esTituCali = rs.getString("es_titu_cali");
							if(esTituCali == null) esTituCali="";
							String puCtrl = rs.getString("pu_ctrl");
							if(puCtrl == null) puCtrl = "";
							if(areaRegID.equals("24000"))
							{
								detalle.setMensaje(mensajeTitulo(esTituCali,puCtrl));
							}
							else
							{
								detalle.setMensaje(rs.getString("tet_mensaje"));
							}
							/**2004/05/10 Kuma Fin**/
							
							date = rs.getDate("t_fec_venc");
							if(date != null)
								detalle.setVencimiento(FechaUtil.dateToString(date));
							else
								detalle.setVencimiento(" ");
							
							/*Obteniendo el nombre del Presentante*/
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
							/*Fin: Obteniendo el nombre del Presentante*/
							
							java.sql.Timestamp ts_present = rs.getTimestamp("t_ts_present");
							
							if(ts_present != null)
								detalle.setFecha(FechaUtil.dateTimeToString(ts_present));
							else
								detalle.setFecha(" ");
							
							detalle.setFecha_ult_sinc(FechaUtil.getCurrentDateTime());
							
							String estId = rs.getString("tet_estado_titulo_id");
							String est =  new StringBuffer(rs.getString("tet_estado")).append(mensaje_adicional).toString();
							String refNumTitu = rs.getString("t_refnum_titu");
							refNumTituPcm = rs.getString("t_refnum_titu"); //Inicio:mgarate: 26/10/2007
							
							
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
									ssql = "SELECT P.NUM_PARTIDA FROM PARTIDA P, ASIENTO A WHERE P.REFNUM_PART = A.REFNUM_PART AND P.REG_PUB_ID = ? AND P.OFIC_REG_ID = ? AND A.NUM_TITU = ? AND A.AA_TITU = ? AND P.AREA_REG_ID = ? ";
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
							//ExpressoHttpSessionBean.getRequest(request).setAttribute("areaReg", area_registral);
							detalle.setAreaRegistral(area_registral);
							primera_vez = false;
							/*
							 * Inicio:jascencio:12/07/07
							 * CC: REGMOBCON-2006
							 */
							if(Double.parseDouble(aux3) >= Double.parseDouble(Constantes.NUM_TITULO_RMC)){
								List listaPartidasBloq = listadopartidasBloqueadas(conn, stmt, rs, detalle);
								ExpressoHttpSessionBean.getRequest(request).setAttribute("listaPartidasBloqueadas", listaPartidasBloq);
							}
							/*
							 * Fin:jascencio
							 */
							ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", detalle);

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
							/* Inicio: mgarate :26/10/2007
							 * PCM 
							 */
							int count = 0;
							StringBuffer query = new StringBuffer();
							query.append("SELECT COUNT(REFNUM_TITU) "); 
							query.append("FROM USER1.MENSAJE MEN WHERE MEN.REFNUM_TITU='").append(refNumTituPcm).append("' AND ");
							query.append("MEN.CODESTADO = '").append(Constantes.ESTADO_TITULO_INSCRITO).append("' ");
							
							if (isTrace(this)) System.out.print("Quey PCM : " + query.toString());
							
							stmt1 = conn.createStatement();
							rs1 = stmt1.executeQuery(query.toString());
							
							if(rs1.next())
							{
								count = rs1.getInt(1);
							}
							
							if(count!=0)
							{
								ExpressoHttpSessionBean.getRequest(request).setAttribute("flagSolicitudInscripcion","S");
								ExpressoHttpSessionBean.getRequest(request).setAttribute("refnumtitu",refNumTituPcm);
							}
							/*Fin:mgarate
							 *PCM 
							 */
				
							//	Seteando Valores
								ExpressoHttpSessionBean.getRequest(request).setAttribute("listaRegistrales", listaActRegistrales);
								ExpressoHttpSessionBean.getRequest(request).setAttribute("listaEntidades", listaActEntidades);
								//ExpressoHttpSessionBean.getRequest(request).setAttribute("presentado", "Y");
								System.out.println(ExpressoHttpSessionBean.getRequest(request).getAttribute("presentado"));
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


	public void closeExtra(java.sql.Statement stmt1, java.sql.ResultSet rs1){
		if(rs1 != null)
			try{rs1.close();
			}catch(Exception ex){}
		if(stmt1 != null)
			try{stmt1.close();
			}catch(Exception ex){}
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
				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		PreparedStatement psm = null;
		ResultSet rs = null;
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		String ano_titu = "";
		String num_titu = "";
		String ruc = "";
		String fec_pre="";
		String zona= "";
		String oficina = "";
		String act_descripcion="";
		String num_partida="";
		String ns_asiento="";
		String ts_inscrip="";
		String num_recibo = "";
		String monto_total="";
			try
			{
				
				ArrayList result = new ArrayList();
				
				init(request);
				
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				
				
				String refnumTitulo = request.getParameter("params");		

				StringBuffer query = new StringBuffer();
				//query.append("SELECT T.ANO_TITU, T.NUM_TITU, M.INFO1, T.TS_PRESENT, REG.NOMBRE AS ZONA, OFI.NOMBRE AS OFICINA ");
				query.append("SELECT T.ANO_TITU, T.NUM_TITU, DECODE(M.INFO1,'-',M.INFO2,M.INFO1) INFO, T.TS_PRESENT, REG.NOMBRE AS ZONA, OFI.NOMBRE AS OFICINA ");
				query.append("FROM   MENSAJE M , TITULO T, ");
				query.append("REGIS_PUBLICO REG, OFIC_REGISTRAL OFI ");
				query.append("WHERE  M.REFNUM_TITU = T.REFNUM_TITU AND ");
				query.append("T.REG_PUB_ID = OFI.REG_PUB_ID AND ");
				query.append("T.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
				query.append("T.REG_PUB_ID = REG.REG_PUB_ID AND ");
				query.append("OFI.REG_PUB_ID = REG.REG_PUB_ID AND ");
				query.append("M.CODESTADO = '").append(Constantes.ESTADO_TITULO_INSCRITO).append("' AND ");
				query.append("M.REFNUM_TITU = '").append(refnumTitulo).append("' ");
				
				psm = conn.prepareStatement(query.toString());
				
				if (isTrace(this)) System.out.println("PCM :" + query.toString());
							
				rs = psm.executeQuery();
				
				if(rs.next())
				{
					ano_titu = rs.getString(1);
					num_titu = rs.getString(2);
					ruc = rs.getString(3);
					fec_pre = rs.getString(4);
					zona = rs.getString(5);
					oficina = rs.getString(6);
				}
				
				StringBuffer query2 = new StringBuffer();
				query2.append("SELECT ACT.DESCRIPCION, PART.NUM_PARTIDA, ASI.NS_ASIENTO, ASI.TS_INSCRIP ");
				query2.append("FROM   ASIENTO ASI, PARTIDA PART, TM_ACTO ACT ");
				query2.append("WHERE  ASI.REFNUM_PART = PART.REFNUM_PART AND ");
				query2.append("ASI.COD_ACTO = ACT.COD_ACTO AND ");
				query2.append("ASI.NUM_TITU = '").append(num_titu).append("' AND ");
				query2.append("ASI.AA_TITU =  '").append(ano_titu).append("' ");
				
				psm = conn.prepareStatement(query2.toString());
				
				if (isTrace(this)) System.out.println("PCM :" + query2.toString());
				
				rs = psm.executeQuery();
				
				while(rs.next())
				{
					AnotacionInscripcion anotacion = new AnotacionInscripcion();
					anotacion.setDescripcionActo(rs.getString(1));
					anotacion.setNumeroPartida(rs.getString(2));
					anotacion.setSecuenciaAsiento(rs.getString(3));
					anotacion.setFechaIncripcion(rs.getString(4));
					result.add(anotacion);
				}
				
				StringBuffer query3 = new StringBuffer();
				query3.append("SELECT REC.AN_RECIBO,REC.CO_CAJA,REC.NUM_TICK ");
				query3.append("FROM USER1.RECIBO_TITULO REC ");
				query3.append("WHERE  REC.REFNUM_TITU = '").append(refnumTitulo).append("' AND ");
				query3.append("REC.IND_EXTORNO != 'S'");
				
				psm = conn.prepareStatement(query3.toString());
				
				if (isTrace(this)) System.out.println("PCM :" + query3.toString());
				
				rs = psm.executeQuery();
				
				while(rs.next())
				{
					num_recibo = num_recibo +  rs.getString(1)+"-"+rs.getString(2)+"-"+rs.getString(3)+",";
				}
				
				
				StringBuffer query4 = new StringBuffer();
				query4.append("SELECT SUM(REC.MONTO_COBR) ");
				query4.append("FROM   USER1.RECIBO_TITULO REC ");
				query4.append("WHERE  REC.REFNUM_TITU = '").append(refnumTitulo).append("' AND ");
				query4.append("REC.IND_EXTORNO != 'S'");
				
				psm = conn.prepareStatement(query4.toString());
				
				if (isTrace(this)) System.out.println("PCM :" + query4.toString());
				
				rs = psm.executeQuery();
				
				if(rs.next())
				{
					monto_total = rs.getString(1);
				}
				ExpressoHttpSessionBean.getRequest(request).setAttribute("numTitulo",num_titu);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("anho",ano_titu);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("asientos",result);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaPresentacion",fec_pre);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("oficina",oficina);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("asiento",Constantes.ACTO_ANOTACION_INSCRIPCION);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("zonaRegistral",zona);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("recibo",num_recibo);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("monto",monto_total);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc",ruc);
				
				psm.close();
				
				response.setStyle("anotacionInscripcion");
				
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				end(request);
			}
			return response;
		}
	
	/** FIN **/
	
	protected ControllerResponse runBuscaOrdenState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
			
			try{
				init(request);
				
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
			
			sql.append( " select t.refnum_titu,t.refnum_orden,t.reg_pub_id,t.ofic_reg_id,t.ano_titu,t.num_titu,o.nombre " +
						" from titulo_orden t, ofic_registral o " +
						" where t.refnum_titu='"+refNumeroTitulo+"' "+
						" and o.reg_pub_id=t.reg_pub_id " +
						" and o.ofic_reg_id=t.ofic_reg_id ");
			
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

	
	
}

