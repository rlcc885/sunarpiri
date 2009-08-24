/*
 * Created on Jan 19, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.devolucion.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboConsumo;
import gob.pe.sunarp.extranet.dbobj.DboConsumoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboDireccion;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboObjetoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboRegisPublico;
import gob.pe.sunarp.extranet.dbobj.DboSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboTaSoliDevo;
import gob.pe.sunarp.extranet.dbobj.DboTmDocIden;
import gob.pe.sunarp.extranet.devolucion.SolicitudDevolucion;
import gob.pe.sunarp.extranet.devolucion.bean.SolicitudDevolucionBean;
import gob.pe.sunarp.extranet.devolucion.util.HelperAbonoDevolucion;
import gob.pe.sunarp.extranet.devolucion.util.HelperCuenta;
import gob.pe.sunarp.extranet.devolucion.util.HelperSolicitudDevolucion;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.publicidad.certificada.ObjetoSolicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.PagoBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.SolicitanteBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.DTEGeneratorHelper;
import gob.pe.sunarp.extranet.util.FechaUtil;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;


/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DevolucionController extends ControllerExtension implements Constantes{
	private String thisClass = DevolucionController.class.getName() + ".";

		public DevolucionController() {
			super();
			addState(new State("muestraModoDevolucion", "Muestra la ventana en la que se elige el modo de devolucion"));
			addState(new State("muestraSolicitudDevolucion", "Muestra la solicitud de devolucion"));
			addState(new State("devolucionEfectivo", "Devolucion en efectivo"));
			addState(new State("devolucionAbono", "Devolucion abonando a la cuenta del usuario"));
			addState(new State("reimprimeRecibo", "Reimpresion de recibo"));
			addState(new State("buscaSolicitudDev", "Busca Solicitud devolucion"));
			addState(new State("muestraFormularioBusquedaDev", "Busca Solicitud devolucion"));
			addState(new State("asociarTramite", "Asocia la solicitud de devolucion con el numero de tramite"));
			addState(new State("asociarInforme", "Asocia la solicitud de devolucion con el numero de informe"));
			addState(new State("asociarResolucion", "Asocia la solicitud de devolucion con el numero de informe"));
			addState(new State("muestraSolUsrDev", "Muestra la solicitud de mayor derecho"));
			addState(new State("muestraInformeDevolucion", "Muestra el informe de devolucion"));
			addState(new State("muestraResolucion", "Muestra la resolucion de devolucion"));
			addState(new State("muestraConstanciaSegEstado", "Muestra la resolucion de devolucion"));
			
			setInitialState("muestraFormularioBusquedaDev");
		}
	public String getTitle() {
			return new String("DevolucionController");
		}
	protected ControllerResponse runMuestraModoDevolucionState(ControllerRequest request,ControllerResponse response) throws ControllerException 
		{
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
		
			try{
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
				init(request);
				validarSesion(request);
						
				
				String solicitudId=req.getParameter("sol_id");
				
				req.setAttribute("solicitudId",solicitudId);
				response.setStyle("modoDevolucion");
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				
				response.setStyle(e.getForward());
			}catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				response.setStyle("error");
			}finally{
				//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
				pool.release(conn);								
				end(request);
			}
			return response;
		}
		
	protected ControllerResponse runMuestraSolicitudDevolucionState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
	
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			String solicitudId=req.getParameter("solicitudId");
			String tipoDev=req.getParameter("radTipDev");
			String oficinas=req.getParameter("cboOficinas");
			String regispubid="";
			String oficregid="";
			StringTokenizer st = null;
			if (oficinas!=null) {
				st = new StringTokenizer(oficinas,"|");
			
				if (st.hasMoreTokens()) {
					regispubid=st.nextToken();
				}
				if (st.hasMoreTokens()) {
					oficregid=st.nextToken();
				}
			}

			DboTaSoliDevo devolucion=new DboTaSoliDevo();
			devolucion.setConnection(dconn);
			devolucion.setField(DboTaSoliDevo.CAMPO_SOLICITUD_ID,solicitudId);
			devolucion.setField(DboTaSoliDevo.CAMPO_FE_SOLI,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			devolucion.setField(DboTaSoliDevo.CAMPO_ESTA,ESTADO_SOL_DEV_REGISTRADO);
			devolucion.setField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV,userBean.getCuentaId());
			devolucion.setField(DboTaSoliDevo.CAMPO_OFIC_REG_ID,oficregid);
			devolucion.setField(DboTaSoliDevo.CAMPO_REG_PUB_ID,regispubid);
			if(userBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT){
				devolucion.setField(DboTaSoliDevo.CAMPO_TIPO_USR,TIPO_USR_ORGANIZACION);
			}else
				devolucion.setField(DboTaSoliDevo.CAMPO_TIPO_USR,TIPO_USR_INDIVIDUAL);
			devolucion.add();
			
			String dptoFormat= HelperSolicitudDevolucion.obtenerDepartamento(regispubid,oficregid,dconn);
			dptoFormat.substring(0,1);
			String minus = dptoFormat.substring(1,dptoFormat.length());
			String dptoFinal = dptoFormat.substring(0,1) + minus.toLowerCase();
			
			req.setAttribute("zonReg",HelperSolicitudDevolucion.obtenerZonaRegistral(regispubid,dconn));
			req.setAttribute("departamento",dptoFinal);

			dconn.commit();
			switch(tipoDev.charAt(0))
			{
				case 'E': transition("devolucionEfectivo", request, response); break;
				case 'A': transition("devolucionAbono", request, response); break;
			}
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	protected ControllerResponse runDevolucionEfectivoState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
	
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);			
			String solicitudId=req.getParameter("solicitudId");
			Solicitud sol=new Solicitud(solicitudId,conn);
			SolicitanteBean solicitante = sol.getSolicitanteBean();
			
			if(userBean.getPerfilId()==PERFIL_ADMIN_ORG_EXT){
				solicitante.setApe_mat(userBean.getApeMat());
				solicitante.setApe_pat(userBean.getApePat());
				solicitante.setNombres(userBean.getNombres());
				DboPeNatu penatu= new DboPeNatu();
				penatu.setConnection(dconn);
				penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,userBean.getPeNatuId());
				penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PERSONA_ID+"|"+DboPeNatu.CAMPO_PE_JURI_ID);
				penatu.find();
				DboPeJuri pejuri = new DboPeJuri();
				pejuri.setConnection(dconn);
				pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
				pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
				pejuri.find();
				solicitante.setRaz_soc(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
				DboDireccion dboDireccion= new DboDireccion();
				dboDireccion.setConnection(dconn);
				dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, penatu.getField(DboPeNatu.CAMPO_PERSONA_ID));
				dboDireccion.find();
				solicitante.setDireccion(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
				if(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA)!=null)
					solicitante.setDireccion(solicitante.getDireccion()+" "+dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
				req.setAttribute("tipo",Constantes.TIPO_USR_ORGANIZACION);
				
			}else{
				DboDireccion dboDireccion= new DboDireccion();
				dboDireccion.setConnection(dconn);
				dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, userBean.getPersonaId());
				dboDireccion.find();
				solicitante.setDireccion(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
				if(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA)!=null)
					solicitante.setDireccion(solicitante.getDireccion()+" "+dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
				req.setAttribute("tipo",Constantes.TIPO_USR_INDIVIDUAL);
			}
			DboTaSoliDevo solDevo = new DboTaSoliDevo();
					
			sol.setTs_crea(FechaUtil.expressoDateTimeToUtilDateTime(sol.getTs_crea()).substring(0,10));
			req.setAttribute("solicitante",solicitante);
			req.setAttribute("solicitud",sol);
			req.setAttribute("fecHoy",FechaUtil.getCurrentDate());
			req.setAttribute("siguiente","S");
			
			
	
			response.setStyle("muestraSolicitudMayor");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}		
	protected ControllerResponse runReimprimeReciboState(ControllerRequest request,ControllerResponse response) throws ControllerException{
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			try{
				init(request);
				validarSesion(request);
	
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
				UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);			
				String solicitudId=req.getParameter("solicitudId");
				System.out.println("solicitudId"+solicitudId);

				//LSUAREZ - Usado para ocultar el boton siguiente
				String ocultaSiguiente = req.getParameter("hidOcultaSiguiente");
				if(ocultaSiguiente != null && ocultaSiguiente.equals("true")){
					req.setAttribute("ocultaSiguiente","true");
				}
				
				Solicitud sol=new Solicitud(solicitudId,conn);
				PagoBean pagoBean=sol.getPagoBean();
							
				SolicitanteBean solicitante = sol.getSolicitanteBean();
				ComprobanteBean comprobante = new ComprobanteBean();
				comprobante.setMonto(sol.getTotal());
				comprobante.setUserId(sol.getUsr_crea());
				comprobante.setSolicitudId(sol.getSolicitud_id());
				comprobante.setFecha_hora(FechaUtil.expressoDateTimeToUtilDateTime(sol.getTs_crea()).replace('/','.'));
				if(pagoBean.getTipo_abono()!=null)
				switch(pagoBean.getTipo_abono().charAt(0))
				{
					case 'E': comprobante.setTipoPago(nOMBRE_TIPO_PAGO_EFECTIVO); break;
					case 'P': comprobante.setTipoPago(NOMBRE_TIPO_PAGO_LINEA_PREGAGO); break;
					case 'T': comprobante.setTipoPago(nOMBRE_TIPO_PAGO_TARJETA); break;
					case 'C': comprobante.setTipoPago(NOMBRE_TIPO_PAGO_CHEQUE); break;
				}
				/***/
				StringBuffer descrip = new StringBuffer();
				ObjetoSolicitudBean objSol=(ObjetoSolicitudBean)sol.getObjetoSolicitudList(0);
				//if(objSol!=null)
				/************Inicio ifigueroa: 23/08/2007*************/ 
			
				if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_NEGATIVO))// habra tipo cert rmc 
				{
					descrip.append("CERTIFICADO POSITIVO/NEGATIVO DE " + ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_desc().toUpperCase());
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS)){
						
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca().equals("") ){
							descrip.append("<BR>");
							descrip.append("NRO PLACA: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca());
						}
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombreBien() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombreBien().equals("") ){
							descrip.append("<BR>");
							descrip.append("NOMBRE BIEN: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombreBien());
						}
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula().equals("") ){
							descrip.append("<BR>");
							descrip.append("NRO MATRICULA: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula());
						}
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie().equals("") ){
							descrip.append("<BR>");
							descrip.append("NRO SERIE: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie());
						}
					}else{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
						{
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
							if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals("")){
								descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
							}
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
						}
						else
						{
							if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("J")){
								descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
							}	
						}
					}	
				}
				
				// certificado de gravamen
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_RMC)){ //gravemn y vigencia=R
					descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_desc().toUpperCase());
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals(""))
						{
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
						}
						descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
					}
					else
					{
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("J")){
							descrip.append("<BR>");
							descrip.append("PARTICIPANTE: ");
							
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
						}	
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getSiglas() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getSiglas().equals("") ){
						descrip.append("<BR>");
						descrip.append("SIGLAS: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getSiglas());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equals("") ){
						descrip.append("<BR>");
						descrip.append("TIPO PARTICIPANTE: ");
						if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_ACREEDOR))
							descrip.append("Acreedor");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_DEUDOR))
								descrip.append("Deudor");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_OTROS))
							descrip.append("Otros");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE))
							descrip.append("Representante");
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante());
					}
					if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoDocumento() !=null &&((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoDocumento().length()>0){//(desNumDoc != null && !desNumDoc.equals("") ){
						descrip.append("<BR>");
						DboTmDocIden dboDoc= new DboTmDocIden();
						dboDoc.setConnection(dconn);
						dboDoc.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoDocumento());
						dboDoc.find();
						descrip.append(dboDoc.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
						descrip.append(" : ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroDocumento());//
						//desNumDoc=null;
					}
				}
				
				// certificado de Registro condicionado
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)){
					descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_desc().toUpperCase());
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals("")){
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
						}
						descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
					}
					else
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
					}
					
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equals("") ){
						descrip.append("<BR>");
						descrip.append("TIPO DE PARTICIPACIÓN: ");
						if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_ACREEDOR))
							descrip.append("Acreedor");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_DEUDOR))
								descrip.append("Deudor");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_OTROS))
							descrip.append("Otros");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE))
							descrip.append("Representante");
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoRegistro() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoRegistro().equals("") ){
						descrip.append("<BR>");
						descrip.append("REGISTROS: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getDesTipoRegistro());
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoRegistro());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFlagHistorico() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFlagHistorico().equals("") ){
						descrip.append("<BR>");
						descrip.append("HISTÓRICO: ");
						if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFlagHistorico().equals("1"))
							descrip.append("SÍ");
						else
							descrip.append("NO");
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoDesde() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoDesde().equals("") ){
						descrip.append("<BR>");
						descrip.append("FECHA DE INSCRIPCIÓN DE ASIENTO: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoDesde());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoHasta() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoHasta().equals("") ){
						descrip.append(" - ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoHasta());
					}
					
				}
				//certificado de Registro Actos Vigentes
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_ACTOS_VIGENTES)||
						((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO)){
					
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_desc().toUpperCase());
					
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals(""))
						{
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
						}
						descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
					}
					else
					{
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("J")){
							descrip.append("<BR>");
							descrip.append("PARTICIPANTE: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
						}	
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equals("") ){
						descrip.append("<BR>");
						descrip.append("TIPO DE PARTICIPACIÓN: ");
						if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_ACREEDOR))
							descrip.append("Acreedor");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_DEUDOR))
								descrip.append("Deudor");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_OTROS))
							descrip.append("Otros");
						else if( ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equalsIgnoreCase(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE))
							descrip.append("Representante");
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante());
					}	
				}
				// certificado de Registro Dominial RJB
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_DOMINIAL_RJB)||
						((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_GRAVAMEN_RJB)){
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_desc().toUpperCase());
					
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc().equals("")){
						descrip.append("<BR>");
						descrip.append("OFICINA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc());
					}
					// solo para certificado dominial se concatenara el tipo de informacion de dominio
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_DOMINIAL_RJB)){
						if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoInformacionDominio() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoInformacionDominio().equals("") ){
							descrip.append("<BR>");
							descrip.append("TIPO DE INFORMACION DE DOMINIO: ");
							if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoInformacionDominio().equals("C"))
							descrip.append("COMPLETA");
							else
								descrip.append("ULTIMO PROPIETARIO");
						}	
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO PLACA/PARTIDA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca());
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO MATRICULA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula());
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO PARTIDA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida());
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO SERIE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie());
					}
					
				}
				

				//
				
				/************Fin ifigueroa: 23/08/2007*************/
				else
				{
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumPartida() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumPartida().trim().equals(""))
						descrip.append(". PARTIDA: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumPartida());
					
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNs_asie() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNs_asie().trim().equals(""))
						descrip.append(". ASIENTO: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNs_asie());
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu().trim().equals(""))
						descrip.append(". TITULO: ");
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getAa_titu() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getAa_titu().trim().equals(""))
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getAa_titu()).append(" - ");
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu().trim().equals(""))
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu());
					
//					Incio:jascencio:09/08/2007
					//CC:REGMOBCON-2006
					String numeroPaginas = ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_pag();
					String numeroPaginasAnt = ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPaginasAnterior(); 
					if(numeroPaginasAnt != null && numeroPaginas != null ){
						Long numero = 0l;
						
						numero = Long.parseLong(numeroPaginas) + Long.parseLong(numeroPaginasAnt);
						numeroPaginas = String.valueOf(numero); 
						
					}
					descrip.append(". PAGINAS: ").append(numeroPaginas);
					//Fin:jascencio
				}
				if(!((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_BUSQUEDA))
				{
				 descrip.append("<BR>");
				 descrip.append("OFICINA: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc());
				 //descrip.append("OFICINA: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getDesc_regis());
				 sol.setDescripcion(descrip.toString());	
				 comprobante.setSolDesc(descrip.toString());
				}else
				{
					comprobante.setSolDesc(sol.getObjetoSolicitudList(0).getCriterioBusqueda());
				}
				//descrip.append(". OFICINA: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc());
				//comprobante.setSolDesc(descrip.toString());	
				/***/
				comprobante.setCajero("WEB");
				comprobante.setNombreEntidad(solicitante.getApe_pat()+" "+solicitante.getApe_mat()+" "+solicitante.getNombres());
			
				req.setAttribute("comprobante",comprobante);
			
	
				response.setStyle("muestraComprobante");
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe){
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
		return response;
		}	
		
	protected ControllerResponse runBuscaSolicitudDevState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
		
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			String estado= req.getParameter("estado");
			StringBuffer feci = new StringBuffer(request.getParameter("diainicio"));
			feci.append("/").append(request.getParameter("mesinicio")).append("/").append(request.getParameter("anoinicio"));
			String fechaInicio = FechaUtil.stringToOracleString(feci.toString());

			StringBuffer fecf = new StringBuffer(request.getParameter("diafin"));
			fecf.append("/").append(request.getParameter("mesfin")).append("/").append(request.getParameter("anofin")).append(" 23:59:59");
			String fechaFin = FechaUtil.stringTimeToOracleString(fecf.toString());		
			
			DboTaSoliDevo dboSolDev= new DboTaSoliDevo();
			dboSolDev.setConnection(dconn);
			if(estado!=null && estado.length()>0 && !estado.equalsIgnoreCase("T")){
				dboSolDev.setField(DboTaSoliDevo.CAMPO_ESTA,estado);
			}
			dboSolDev.setAppendWhereClause(DboTaSoliDevo.CAMPO_FE_SOLI + " BETWEEN " + fechaInicio + " AND " + fechaFin + " AND " + DboTaSoliDevo.CAMPO_REG_PUB_ID + "=" + userBean.getRegPublicoId());
			java.util.List l =dboSolDev.searchAndRetrieveList(DboTaSoliDevo.CAMPO_FE_SOLI);
			java.util.List arlSolicitud= new ArrayList();
			if(l.size() > 0)
			{
				for(Iterator i = l.iterator();i.hasNext();)
				{
					DboTaSoliDevo solDevolucion = (DboTaSoliDevo) i.next();
					SolicitudDevolucionBean solicitud= new SolicitudDevolucionBean();
					solicitud.setIdSolicitudDev(solDevolucion.getField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO));
					solicitud.setSolicitudId(solDevolucion.getField(DboTaSoliDevo.CAMPO_SOLICITUD_ID));
					String strEstado=solDevolucion.getField(DboTaSoliDevo.CAMPO_ESTA);
					if(strEstado!=null && strEstado.length()>0)
					switch(strEstado.charAt(0))
					{
						case '0': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_REGISTRADO); break;
						case '1': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_CON_INFORME); break;
						case '2': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_CON_RESOLUCION); break;
						case '3': solicitud.setEstado(NOMBRE_ESTADO_SOL_DEV_PAGADO); break;
					}
					solicitud.setFechaSolicitud(FechaUtil.expressoDateToUtilDate(solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_SOLI)));
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_AA_TRAM)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_AA_TRAM).length()>0)
						solicitud.setAnoHojaTramite(solDevolucion.getField(DboTaSoliDevo.CAMPO_AA_TRAM));
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_TRAM)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_TRAM).length()>0)
						solicitud.setNumHojaTramite(solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_TRAM));
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_TRAM)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_TRAM).length()>0)
						solicitud.setFechaTramite(FechaUtil.expressoDateToUtilDate(solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_TRAM)).replace('/','.'));
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_INFO)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_INFO).length()>0)
						solicitud.setNumInforme(solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_INFO));
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_INFO)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_INFO).length()>0)
						solicitud.setFechaInforme(FechaUtil.expressoDateToUtilDate(solDevolucion.getField(DboTaSoliDevo.CAMPO_FE_INFO)).replace('/','.'));
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_RESO)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_RESO).length()>0)
						solicitud.setNumResolucion(solDevolucion.getField(DboTaSoliDevo.CAMPO_NU_RESO));
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID).length()>0){
						solicitud.setCuentaId(solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID));
						DboCuenta cuenta = new DboCuenta();
						cuenta.setConnection(dconn);
						cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID));
						cuenta.find();
						DboPeNatu penatu= new DboPeNatu();
						penatu.setConnection(dconn);
						penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
						if(penatu.find())
							solicitud.setNombre(penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+ penatu.getField(DboPeNatu.CAMPO_APE_MAT)+" "+penatu.getField(DboPeNatu.CAMPO_NOMBRES));
					}
					if(solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV)!=null && solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV).length()>0){
						DboCuenta cuenta = new DboCuenta();
						cuenta.setConnection(dconn);
						cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,solDevolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV));
						cuenta.find();
						DboPeNatu penatu= new DboPeNatu();
						penatu.setConnection(dconn);
						penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
						if(penatu.find())
							solicitud.setNombre(penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+ penatu.getField(DboPeNatu.CAMPO_APE_MAT)+" "+penatu.getField(DboPeNatu.CAMPO_NOMBRES));
					}
					arlSolicitud.add(solicitud);
				}
			}
			if(arlSolicitud.size()<=0)
			req.setAttribute("contador0","0");
			req.setAttribute("lstSolDevolucion",arlSolicitud);
			req.setAttribute("estado",estado);
			if(arlSolicitud.size()>0)
			req.setAttribute("contador1","x");
			/** Mantener parametros de busqueda**/
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", req.getParameter("diainicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", req.getParameter("mesinicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", req.getParameter("anoinicio"));

			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1",  req.getParameter("diafin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1",  req.getParameter("mesfin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1",   req.getParameter("anofin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("estado",   req.getParameter("estado"));
			
			

			response.setStyle("buscaSolDevolucion");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}
	protected ControllerResponse runMuestraFormularioBusquedaDevState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		try{
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			init(request);
			validarSesion(request);
		
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);			
			Calendar cal=Calendar.getInstance();
	
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", String.valueOf(cal.get(Calendar.MONTH)+1));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1",  String.valueOf(cal.get(Calendar.YEAR)));

			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", String.valueOf(cal.get(Calendar.MONTH)+1));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", String.valueOf(cal.get(Calendar.YEAR)));
		
			
		
		
			response.setStyle("muestraBuscarSolDevolucion");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
		
			response.setStyle(e.getForward());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
			//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);						
			end(request);
		}
		return response;
	}
	protected ControllerResponse runAsociarTramiteState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	
		try{
			init(request);
			validarSesion(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			DboTaSoliDevo devolucion=new DboTaSoliDevo();
			String solicitudId= req.getParameter("hidNumSolDev");
			String fecTram= req.getParameter("hidFecTram");
			String numTram= req.getParameter("hidNumTram");
			
			devolucion.setConnection(dconn);
			devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solicitudId);
			devolucion.setField(DboTaSoliDevo.CAMPO_AA_TRAM,numTram.substring(0,4));
			devolucion.setField(DboTaSoliDevo.CAMPO_FE_TRAM,FechaUtil.stringToOracleString(fecTram));
			devolucion.setField(DboTaSoliDevo.CAMPO_NU_TRAM,numTram.substring(5,numTram.length()));
			devolucion.setFieldsToUpdate(DboTaSoliDevo.CAMPO_AA_TRAM+"|"+DboTaSoliDevo.CAMPO_NU_TRAM+"|"+DboTaSoliDevo.CAMPO_FE_TRAM);
			devolucion.update();
			dconn.commit();
			transition("buscaSolicitudDev", request, response);
					
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}	
	
	protected ControllerResponse runMuestraSolUsrDevState(ControllerRequest request,ControllerResponse response) throws ControllerException{
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			try{
				init(request);
				validarSesion(request);
	
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
				String solDevId= req.getParameter("hidNumSolDev");
				String solicitudId=req.getParameter("solicitudId");
				Solicitud sol=new Solicitud(solicitudId,conn);
				SolicitanteBean solicitante = sol.getSolicitanteBean();
				
				DboTaSoliDevo devolucion=new DboTaSoliDevo();
				devolucion.setConnection(dconn);
				devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solDevId);
				devolucion.find();
				String cuentaIdDev= devolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
				String tipoUsr= devolucion.getField(DboTaSoliDevo.CAMPO_TIPO_USR);
				String regPubId= devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID);
				String oficRegId= devolucion.getField(DboTaSoliDevo.CAMPO_OFIC_REG_ID);
				DboCuenta cuentaCreador=new DboCuenta();
				cuentaCreador.setConnection(dconn);
				cuentaCreador.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaIdDev);
				cuentaCreador.find();
				DboPeNatu penatu= new DboPeNatu();
				penatu.setConnection(dconn);
				penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuentaCreador.getField(DboPeNatu.CAMPO_PE_NATU_ID));
				penatu.find();
				solicitante.setApe_mat(penatu.getField(DboPeNatu.CAMPO_APE_MAT));
				solicitante.setApe_pat(penatu.getField(DboPeNatu.CAMPO_APE_PAT));
				solicitante.setNombres(penatu.getField(DboPeNatu.CAMPO_NOMBRES));
				if(tipoUsr.equalsIgnoreCase(TIPO_USR_ORGANIZACION)){
					
					DboPeJuri pejuri = new DboPeJuri();
					pejuri.setConnection(dconn);
					pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
					pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
					pejuri.find();
					solicitante.setRaz_soc(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
					req.setAttribute("tipo",Constantes.TIPO_USR_ORGANIZACION);
				}else{
					req.setAttribute("tipo",Constantes.TIPO_USR_INDIVIDUAL);
				}

				DboDireccion dboDireccion= new DboDireccion();
				dboDireccion.setConnection(dconn);
				dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, penatu.getField(DboPeNatu.CAMPO_PERSONA_ID));
				dboDireccion.find();
				solicitante.setDireccion(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
				if(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA)!=null)
					solicitante.setDireccion(solicitante.getDireccion()+" "+dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
				
				//dando formato a el departamento ejm Lima
				String dptoFormat = HelperSolicitudDevolucion.obtenerDepartamento(regPubId,oficRegId,dconn);
				dptoFormat.substring(0,1);
				String minus = dptoFormat.substring(1,dptoFormat.length());
				String dptoFinal = dptoFormat.substring(0,1) + minus.toLowerCase();
								
				sol.setTs_crea(FechaUtil.expressoDateTimeToUtilDateTime(sol.getTs_crea()).substring(0,10));
				req.setAttribute("solicitante",solicitante);
				req.setAttribute("solicitud",sol);
				req.setAttribute("fecHoy",FechaUtil.getCurrentDate());
				req.setAttribute("zonReg",HelperSolicitudDevolucion.obtenerZonaRegistral(regPubId,dconn));
				req.setAttribute("departamento",dptoFinal);
			
	
				response.setStyle("muestraSolicitudMayor");
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe){
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
		return response;
		}	
	protected ControllerResponse runAsociarInformeState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	
		try{
			init(request);
			validarSesion(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			DboTaSoliDevo devolucion=new DboTaSoliDevo();
			String solicitudId= req.getParameter("hidNumSolDev");
			String numInf= req.getParameter("hidNumInf");
			
			
			devolucion.setConnection(dconn);
			devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solicitudId);
			devolucion.setField(DboTaSoliDevo.CAMPO_NU_INFO,numInf);
			devolucion.setField(DboTaSoliDevo.CAMPO_ESTA,ESTADO_SOL_DEV_CON_INFORME);
			devolucion.setField(DboTaSoliDevo.CAMPO_FE_INFO,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			devolucion.setFieldsToUpdate(DboTaSoliDevo.CAMPO_NU_INFO+"|"+DboTaSoliDevo.CAMPO_FE_INFO+"|"+DboTaSoliDevo.CAMPO_ESTA);
			devolucion.update();
			dconn.commit();
			transition("buscaSolicitudDev", request, response);
					
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}	
	protected ControllerResponse runAsociarResolucionState(ControllerRequest request,ControllerResponse response) throws ControllerException{
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
	
			try{
				init(request);
				validarSesion(request);
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
				UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
				DboTaSoliDevo devolucion=new DboTaSoliDevo();
				String solicitudId= req.getParameter("hidNumSolDev");
				String numReso= req.getParameter("hidNumReso");
			
			
				devolucion.setConnection(dconn);
				devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solicitudId);
				devolucion.setField(DboTaSoliDevo.CAMPO_NU_RESO,numReso);
				devolucion.setField(DboTaSoliDevo.CAMPO_ESTA,ESTADO_SOL_DEV_CON_RESOLUCION);
				devolucion.setField(DboTaSoliDevo.CAMPO_FE_RESO,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				devolucion.setFieldsToUpdate(DboTaSoliDevo.CAMPO_NU_RESO+"|"+DboTaSoliDevo.CAMPO_FE_RESO+"|"+DboTaSoliDevo.CAMPO_ESTA);
				devolucion.update();
				dconn.commit();
				transition("buscaSolicitudDev", request, response);
					
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe){
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
		return response;
		}	
		
	protected ControllerResponse runMuestraInformeDevolucionState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			/** AGREGADO JBUGARIN OBSERVACIONES SUNARP 15/03/2007 **/
			String oficReg=null;
			String regPubId=null;
			/** AGREGADO FIN 15/03/2007**/
			DboTaSoliDevo devolucion=new DboTaSoliDevo();
			String solDevId= req.getParameter("hidNumSolDev");
			devolucion.setConnection(dconn);
			devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solDevId);
			devolucion.find();
			SolicitudDevolucionBean solDevBean=new SolicitudDevolucionBean();
			solDevBean.setNumInforme(devolucion.getField(DboTaSoliDevo.CAMPO_NU_INFO));
			solDevBean.setNumHojaTramite(devolucion.getField(DboTaSoliDevo.CAMPO_NU_TRAM));
			solDevBean.setFechaTramite(FechaUtil.expressoDateToUtilDate(devolucion.getField(DboTaSoliDevo.CAMPO_FE_TRAM)).replace('/','.'));
			solDevBean.setAnoHojaTramite(devolucion.getField(DboTaSoliDevo.CAMPO_AA_TRAM));
			/** AGREGADO JBUGARIN 15/03/2007 **/
			oficReg= devolucion.getField(DboTaSoliDevo.CAMPO_OFIC_REG_ID);
			regPubId= devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID);
			/** FIN AGREGADO JBUGARIN 15/03/2007 **/
			String solicitudId=req.getParameter("solicitudId");
			DboSolicitud solicitud = new DboSolicitud();
			solicitud.setConnection(dconn);
			solicitud.setField(DboSolicitud.CAMPO_SOLICITUD_ID,solicitudId);
			solicitud.find();
			DboObjetoSolicitud objSol= new DboObjetoSolicitud();
			objSol.setConnection(dconn);
			objSol.setField(DboObjetoSolicitud.CAMPO_SOLICITUD_ID, solicitudId);
			objSol.find();
			DboConsumoSolicitud conSol=new DboConsumoSolicitud();
			conSol.setConnection(dconn);
			conSol.setField(DboConsumoSolicitud.CAMPO_SOLICITUD_ID, solicitudId);
			conSol.setField(DboConsumoSolicitud.CAMPO_OBJETO_SOL_ID, objSol.getField(DboObjetoSolicitud.CAMPO_OBJETO_SOL_ID));
			conSol.find();
			DboConsumo consumo = new DboConsumo();
			consumo.setConnection(dconn);
			consumo.setField(DboConsumo.CAMPO_CONSUMO_ID,conSol.getField(DboConsumoSolicitud.CAMPO_CONSUMO_ID));
			consumo.find();
			String cuentaIdDev= devolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
			DboCuenta cuentaCreador=new DboCuenta();
			cuentaCreador.setConnection(dconn);
			cuentaCreador.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaIdDev);
			cuentaCreador.find();
			DboPeNatu penatu= new DboPeNatu();
			penatu.setConnection(dconn);
			penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuentaCreador.getField(DboPeNatu.CAMPO_PE_NATU_ID));
			penatu.find();
			ComprobanteBean comprobante=new ComprobanteBean();
			comprobante.setNumeroDoc(consumo.getField(DboConsumo.CAMPO_TRANS_ID));
			DecimalFormat df = new DecimalFormat("0.00");
			String strMonto=solicitud.getField(DboSolicitud.CAMPO_TOTAL);
			comprobante.setMonto(df.format(Double.parseDouble(strMonto)));
			comprobante.setNombreEntidad(penatu.getField(DboPeNatu.CAMPO_NOMBRES)+" "+penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+penatu.getField(DboPeNatu.CAMPO_APE_MAT));
			comprobante.setFecha_hora(FechaUtil.expressoDateToUtilDate(solicitud.getField(DboSolicitud.CAMPO_TS_CREA)).replace('/','.'));
			/****/
			Locale local= new Locale("es","ES");

			java.util.Date hoy= new java.util.Date();
			SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
			String fecha="";
			/*
			String oficReg=userBean.getOficRegistralId();
			String regPubId=userBean.getRegPublicoId();
			*/
			/** AGREGADO JBUGARIN 14/03/2007 **/
			fecha=HelperSolicitudDevolucion.obtenerDepartamento(regPubId,oficReg,dconn);
			String fechaAux = fecha.substring(0,1);
			String fechaAux2 = fecha.substring(1,fecha.length());
			fecha=  fechaAux + fechaAux2.toLowerCase()+", " + sd.format(hoy);
			/** FIN AGREGADO 14/03/2007 **/
			BigDecimal monto = new BigDecimal(comprobante.getMonto());
			String montoAsWord = DTEGeneratorHelper.montoAsWords(monto);
		
			ExpressoHttpSessionBean.getRequest(request).setAttribute("montoPalabra", montoAsWord.toUpperCase());//MODIFICADO JBUGARIN
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
			req.setAttribute("solDev",solDevBean);
			req.setAttribute("comprobante",comprobante);
		
			response.setStyle("muestraInformeDev");
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}	
		
	protected ControllerResponse runMuestraResolucionState(ControllerRequest request,ControllerResponse response) throws ControllerException{
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

			try{
				init(request);
				validarSesion(request);
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
				UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
				/** AGREGADO JBUGARIN OBSERVACIONES SUNARP 15/03/2007 **/
				String oficReg=null;
				String regPubId=null;
				/** FIN AGREGADO 15/03/2007**/
				DboTaSoliDevo devolucion=new DboTaSoliDevo();
				String solDevId= req.getParameter("hidNumSolDev");
				devolucion.setConnection(dconn);
				devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solDevId);
				devolucion.find();
				SolicitudDevolucionBean solDevBean=new SolicitudDevolucionBean();
				solDevBean.setNumInforme(devolucion.getField(DboTaSoliDevo.CAMPO_NU_INFO));
				solDevBean.setFechaInforme(FechaUtil.expressoDateToUtilDate(devolucion.getField(DboTaSoliDevo.CAMPO_FE_INFO)).replace('/','.'));
				solDevBean.setNumHojaTramite(devolucion.getField(DboTaSoliDevo.CAMPO_NU_TRAM));
				solDevBean.setAnoHojaTramite(devolucion.getField(DboTaSoliDevo.CAMPO_AA_TRAM));
				solDevBean.setFechaTramite(FechaUtil.expressoDateToUtilDate(devolucion.getField(DboTaSoliDevo.CAMPO_FE_TRAM)).replace('/','.'));
				/** AGREGADO JBUGARIN 15/03/2007 **/
				oficReg = devolucion.getField(DboTaSoliDevo.CAMPO_OFIC_REG_ID);
				regPubId = devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID);
				/** FIN AGREGADO 15/03/2007**/
				String solicitudId=req.getParameter("solicitudId");
				DboSolicitud solicitud = new DboSolicitud();
				solicitud.setConnection(dconn);
				solicitud.setField(DboSolicitud.CAMPO_SOLICITUD_ID,solicitudId);
				solicitud.find();
				DboObjetoSolicitud objSol= new DboObjetoSolicitud();
				objSol.setConnection(dconn);
				objSol.setField(DboObjetoSolicitud.CAMPO_SOLICITUD_ID, solicitudId);
				objSol.find();
				DboConsumoSolicitud conSol=new DboConsumoSolicitud();
				conSol.setConnection(dconn);
				conSol.setField(DboConsumoSolicitud.CAMPO_SOLICITUD_ID, solicitudId);
				conSol.setField(DboConsumoSolicitud.CAMPO_OBJETO_SOL_ID, objSol.getField(DboObjetoSolicitud.CAMPO_OBJETO_SOL_ID));
				conSol.find();
				DboConsumo consumo = new DboConsumo();
				consumo.setConnection(dconn);
				consumo.setField(DboConsumo.CAMPO_CONSUMO_ID,conSol.getField(DboConsumoSolicitud.CAMPO_CONSUMO_ID));
				consumo.find();
				String cuentaIdDev= devolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
				DboCuenta cuentaCreador=new DboCuenta();
				cuentaCreador.setConnection(dconn);
				cuentaCreador.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaIdDev);
				cuentaCreador.find();
				DboPeNatu penatu= new DboPeNatu();
				penatu.setConnection(dconn);
				penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuentaCreador.getField(DboPeNatu.CAMPO_PE_NATU_ID));
				penatu.find();
				ComprobanteBean comprobante=new ComprobanteBean();
				comprobante.setNumeroDoc(consumo.getField(DboConsumo.CAMPO_TRANS_ID));
				DecimalFormat df = new DecimalFormat("0.00");
				String strMonto=solicitud.getField(DboSolicitud.CAMPO_TOTAL);
				comprobante.setMonto(df.format(Double.parseDouble(strMonto)));
				comprobante.setNombreEntidad(penatu.getField(DboPeNatu.CAMPO_NOMBRES)+" "+penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+penatu.getField(DboPeNatu.CAMPO_APE_MAT));
				comprobante.setFecha_hora(FechaUtil.expressoDateToUtilDate(solicitud.getField(DboSolicitud.CAMPO_TS_CREA)).replace('/','.'));
				/****/
				Locale local= new Locale("es","ES");

				java.util.Date hoy= new java.util.Date();
				SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
				String fecha="";
				/*
				String oficReg=userBean.getOficRegistralId();
				String regPubId=userBean.getRegPublicoId();
				*/
				
				if(oficReg!=null&regPubId!=null&regPubId.length()>0&&oficReg.length()>0){
					DboOficRegistral oficRegistral= new DboOficRegistral();
					oficRegistral.setConnection(dconn);
					oficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficReg);
					oficRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,regPubId);
					oficRegistral.find();
					fecha= oficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE)+", ";

				}
//						
				fecha= fecha+sd.format(hoy);
				BigDecimal monto = new BigDecimal(comprobante.getMonto());

				String montoAsWord = DTEGeneratorHelper.montoAsWords(monto);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("montoPalabra", montoAsWord.toUpperCase());
				ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
				req.setAttribute("solDev",solDevBean);
				req.setAttribute("comprobante",comprobante);
				req.setAttribute("zona",HelperSolicitudDevolucion.obtenerZonaRegistral(devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID),dconn));
				
				/** MODIFICADO JBUGARIN OBSERVACIONES SUNARP 15/03/2007 **/
				if (oficReg.equals("01") && regPubId.equals("01")){
					req.setAttribute("resolucion"," y  la Resolución Jefatural No 1387-200-ORLC/JE");
				}
				/** FIN MODIFICADO 15/03/2007 **/
				response.setStyle("muestraResolucionDev");
			
			} catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe){
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex){
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
		return response;
		}	
	protected ControllerResponse runMuestraConstanciaSegEstadoState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
	
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);			
			String solicitudId=req.getParameter("solicitudId");
			Solicitud sol=new Solicitud(solicitudId,conn);
			SolicitanteBean solicitante = sol.getSolicitanteBean();
			req.setAttribute("Solicitud",sol);
			response.setStyle("muestraConstanciaSegEstado");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}		
	protected ControllerResponse runDevolucionAbonoState(ControllerRequest request,ControllerResponse response) throws ControllerException{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try{
			init(request);
			validarSesion(request);
	
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);			
			String solicitudId=req.getParameter("solicitudId");
			Solicitud sol=new Solicitud(solicitudId,conn);
			SolicitanteBean solicitante = sol.getSolicitanteBean();
			
			String idSession=req.getSession().getId();
			String ipRemota=req.getRemoteAddr();
			String entidad=HelperAbonoDevolucion.efectuarAbonoDevolucion(userBean,sol.getTotal(),ipRemota,idSession,sol.getSolicitud_id(),sol.getTs_crea(),dconn);
			
			req.setAttribute("entidad",entidad);
			req.setAttribute("monto",sol.getTotal());
			dconn.commit();
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			if (userBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO){
				UsuarioBean usuario = (UsuarioBean) session.getAttribute("Usuario");
				DboLineaPrepago linea = new DboLineaPrepago();
				linea.setConnection(dconn);
				linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID,userBean.getCuentaId());
				if(linea.find()){
					usuario.setSaldo(Double.parseDouble(linea.getField(DboLineaPrepago.CAMPO_SALDO)));
					session.setAttribute("Usuario", usuario);
				}
			}
			
			String fecha = FechaUtil.getCurrentDateTime();
			req.setAttribute("solicitud",solicitudId);
			req.setAttribute("fecha",fecha);
			response.setStyle("reciboDevolucionAbono");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
	return response;
	}		
}
