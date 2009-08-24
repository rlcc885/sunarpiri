/*
 * Created on Jan 25, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.devolucion.controller;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;





import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.administracion.bean.MantenOrganizacionBean;
import gob.pe.sunarp.extranet.administracion.bean.MantenUsuarioBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboTaSoliDevo;
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
import gob.pe.sunarp.extranet.publicidad.certificada.bean.SolicitanteBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.DTEGeneratorHelper;
import gob.pe.sunarp.extranet.util.FechaUtil;

/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BajaController extends ControllerExtension implements Constantes{
	private String thisClass = BajaController.class.getName() + ".";
	public BajaController() {
		super();
		addState(new State("elegirZona", "Muestra formulario en el que se elige en que oficina se presentaran los documentos"));
		addState(new State("muestraSolicitud", "Muestra solicitud de devolucion de mayor derecho"));
		addState(new State("muestraReporteSaldo", "Muestra solicitud de devolucion de mayor derecho"));
		addState(new State("muestraSolBaja", "Muestra solicitud de baja al usuario devoluciones "));
		addState(new State("reporteSaldoUsrDevolucion", "Muestra reporte de saldo al usuario devoluciones "));
		addState(new State("muestraResolucionBaja", "Muestra resolucion"));
		addState(new State("muestraInformeBaja", "Muestra informe"));
		addState(new State("generaSolicitudDevolucion", "Muestra mensaje de aviso de bloqueo de cuenta"));
		setInitialState("elegirZona");
		
	}
	public String getTitle() {
				return new String("DevolucionController");
			}
	protected ControllerResponse runElegirZonaState(ControllerRequest request,ControllerResponse response) throws ControllerException 
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
			DboCuenta cuenta= new DboCuenta();
		    cuenta.setConnection(dconn);
		    cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,userBean.getCuentaId());
		    cuenta.find();
		    DboPeNatu penatu= new DboPeNatu();
		    penatu.setConnection(dconn);
		    penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
		    penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
		    penatu.find();
		    double monto=0;
		    if(userBean.getPerfilId()==PERFIL_ADMIN_ORG_EXT){
				String strMonto=HelperCuenta.calcularTotalOrganizacion(penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID),dconn);
				monto=Double.parseDouble(strMonto);
		    }else{
		    	String strMonto=HelperCuenta.calcularTotalUsuario(userBean.getCuentaId(),dconn);
				monto=Double.parseDouble(strMonto);
		    }
		    if(monto>0)			
				response.setStyle("elegirZona");
			else
				transition("generaSolicitudDevolucion", request, response);
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
	protected ControllerResponse runMuestraSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
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
			String oficinas=req.getParameter("cboOficinas");
			String regispubid="";
			String oficregid="";
			StringTokenizer st = new StringTokenizer(oficinas,"|");
			if (st.hasMoreTokens()) {
				regispubid=st.nextToken();
			}
			if (st.hasMoreTokens()) {
				oficregid=st.nextToken();
			}

			
			if(userBean.getPerfilId()==PERFIL_ADMIN_ORG_EXT){
			//	HelperCuenta.bloquearOrganizacion(userBean.getCuentaId(),dconn);
			//	HelperSolicitudDevolucion.crearSolicitudBaja(userBean.getCuentaId(),regispubid,oficregid,Constantes.TIPO_USR_ORGANIZACION,dconn);
				req.setAttribute("tipo",Constantes.TIPO_USR_ORGANIZACION);
			}else{
			//	HelperCuenta.bloquearCuenta(userBean.getCuentaId(),dconn);
			//	HelperSolicitudDevolucion.crearSolicitudBaja(userBean.getCuentaId(),regispubid,oficregid,Constantes.TIPO_USR_ORGANIZACION,dconn);
				req.setAttribute("tipo",Constantes.TIPO_USR_INDIVIDUAL);
			}
			SolicitanteBean solicitante=HelperSolicitudDevolucion.obtenerSolicitanteBean(userBean,dconn);
			req.setAttribute("solicitante",solicitante);
			
			String dptoFormat= HelperSolicitudDevolucion.obtenerDepartamento(regispubid,oficregid,dconn);
			dptoFormat.substring(0,1);
			String minus = dptoFormat.substring(1,dptoFormat.length());
			
			String dptoFinal = dptoFormat.substring(0,1) + minus.toLowerCase();
			
			req.setAttribute("fecHoy",FechaUtil.getCurrentDate());
			req.setAttribute("zonReg",HelperSolicitudDevolucion.obtenerZonaRegistral(regispubid,dconn));
			req.setAttribute("departamento",dptoFinal);
			req.setAttribute("hidRegisPubId",regispubid);
			req.setAttribute("hidOficRegId",oficregid);	
			req.setAttribute("siguiente","S");	
			dconn.commit();
			response.setStyle("mayorDerechoBaja");
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
	protected ControllerResponse runMuestraReporteSaldoState(ControllerRequest request,ControllerResponse response) throws ControllerException 
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
			
			
		
			if(userBean.getPerfilId()==PERFIL_ADMIN_ORG_EXT){
				MantenUsuarioBean usuarioBean= HelperCuenta.obtenerOrganizacion(userBean,dconn);
				req.setAttribute("usuario",usuarioBean);
			}else{
				MantenUsuarioBean usuarioBean= HelperCuenta.obtenerUsuario(userBean,dconn);
				req.setAttribute("usuario",usuarioBean);
			
			}
			req.setAttribute("hidRegisPubId",req.getParameter("hidRegisPubId"));
			req.setAttribute("hidOficRegId",req.getParameter("hidOficRegId"));	
			req.setAttribute("generarSolicitud","S");
			response.setStyle("reporteSaldo");
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
	protected ControllerResponse runMuestraSolBajaState(ControllerRequest request,ControllerResponse response) throws ControllerException 
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
				String cuentaId=req.getParameter("hidCuentaId");
				String solDevId=req.getParameter("hidNumSolDev");
				UsuarioBean userBean =HelperCuenta.obtenerUsuarioBean(cuentaId,solDevId,dconn);// ExpressoHttpSessionBean.getUsuarioBean(request);
				SolicitanteBean solicitante=HelperSolicitudDevolucion.obtenerSolicitanteBeanUsrDev(userBean,solDevId,dconn);
				DboTaSoliDevo devolucion=new DboTaSoliDevo();
				devolucion.setConnection(dconn);
				devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solDevId);
				devolucion.find();
				req.setAttribute("solicitante",solicitante);
				//dando formato al departamento ejm Lima
				String dptoFormat= HelperSolicitudDevolucion.obtenerDepartamento(devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID),devolucion.getField(DboTaSoliDevo.CAMPO_OFIC_REG_ID),dconn);
				dptoFormat.substring(0,1);
				String minus = dptoFormat.substring(1,dptoFormat.length());
				String dptoFinal = dptoFormat.substring(0,1) + minus.toLowerCase();
								
				req.setAttribute("fecHoy",FechaUtil.getCurrentDate());
				req.setAttribute("zonReg",HelperSolicitudDevolucion.obtenerZonaRegistral(devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID),dconn));
				req.setAttribute("departamento",dptoFinal);
				response.setStyle("mayorDerechoBaja");
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
	protected ControllerResponse runReporteSaldoUsrDevolucionState(ControllerRequest request,ControllerResponse response) throws ControllerException 
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

				//LSUAREZ - Usado para mostrar el boton regresar
				String muestraRegresar = req.getParameter("hidMuestraRegresar");
				if(muestraRegresar != null && muestraRegresar.equals("true")){
					req.setAttribute("muestraRegresar","true");
				}


				String cuentaId=req.getParameter("hidCuentaId");
				String solDevId=req.getParameter("hidNumSolDev");
				UsuarioBean userBean =HelperCuenta.obtenerUsuarioBean(cuentaId,solDevId,dconn);// ExpressoHttpSessionBean.getUsuarioBean(request);		
					if(userBean.getPerfilId()==PERFIL_ADMIN_ORG_EXT){
					MantenUsuarioBean usuarioBean= HelperCuenta.obtenerOrganizacionUsrDev(userBean,solDevId,dconn);
					req.setAttribute("usuario",usuarioBean);
				}else{
					MantenUsuarioBean usuarioBean= HelperCuenta.obtenerUsuarioUsrDev(userBean,solDevId,dconn);
					req.setAttribute("usuario",usuarioBean);
				}
				response.setStyle("reporteSaldo");
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
	protected ControllerResponse runMuestraResolucionBajaState(ControllerRequest request,ControllerResponse response) throws ControllerException{
				DBConnectionFactory pool = DBConnectionFactory.getInstance();
				Connection conn = null;
				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

				try{
					init(request);
					validarSesion(request);
					conn = pool.getConnection();
					conn.setAutoCommit(false);
					DBConnection dconn = new DBConnection(conn);
					//UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
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
					
					String cuentaId= devolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID);
					DboCuenta cuentaCreador=new DboCuenta();
					cuentaCreador.setConnection(dconn);
					cuentaCreador.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaId);
					cuentaCreador.find();
					DboPeNatu penatu= new DboPeNatu();
					penatu.setConnection(dconn);
					penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuentaCreador.getField(DboPeNatu.CAMPO_PE_NATU_ID));
					penatu.find();
					ComprobanteBean comprobante=new ComprobanteBean();
					if(devolucion.getField(DboTaSoliDevo.CAMPO_TIPO_USR)!=null&&devolucion.getField(DboTaSoliDevo.CAMPO_TIPO_USR).equalsIgnoreCase(Constantes.TIPO_USR_ORGANIZACION)){
						DecimalFormat df = new DecimalFormat("0.00");
						String strMonto=HelperCuenta.obtenerTotalOrganizacion(solDevId,dconn);
						comprobante.setMonto(df.format(Double.parseDouble(strMonto)));
						
					}
					else{
						DecimalFormat df = new DecimalFormat("0.00");
						String strMonto=HelperCuenta.obtenerTotalUsuario(solDevId,dconn);
						comprobante.setMonto(df.format(Double.parseDouble(strMonto)));
					}
					comprobante.setNombreEntidad(penatu.getField(DboPeNatu.CAMPO_NOMBRES)+" "+penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+penatu.getField(DboPeNatu.CAMPO_APE_MAT));
				
					/****/
					Locale local= new Locale("es","ES");

					java.util.Date hoy= new java.util.Date();
					SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
					String fecha="";
					String oficReg=devolucion.getField(DboTaSoliDevo.CAMPO_OFIC_REG_ID);
					String regPubId=devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID);
					/** MODIFICADO JBUGARIN 15/03/2007**/
					fecha=HelperSolicitudDevolucion.obtenerDepartamento(regPubId,oficReg,dconn);
					
					fecha= fecha.substring(0,1)+fecha.substring(1,fecha.length()).toLowerCase() +sd.format(hoy);
					/** FIN MODIFICADO 15/03/2007**/
					BigDecimal monto = new BigDecimal(comprobante.getMonto());

					String montoAsWord = DTEGeneratorHelper.montoAsWords(monto);

					ExpressoHttpSessionBean.getRequest(request).setAttribute("montoPalabra", montoAsWord.toUpperCase());
					ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
					req.setAttribute("solDev",solDevBean);
					req.setAttribute("comprobante",comprobante);
					req.setAttribute("zona",HelperSolicitudDevolucion.obtenerZonaRegistral(devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID),dconn));
					
					/** MODIFICADO JBUGARIN OBSERVACIONES SUNARP 15/03/2007**/
					if (oficReg.equals("01") && regPubId.equals("01")){
					req.setAttribute("resolucion"," y  la Resolución Jefatural No 1387-200-ORLC/JE");
					}
					/** FIN MODIFICACIONES **/
					response.setStyle("muestraResolucionBaja");
			
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
	protected ControllerResponse runMuestraInformeBajaState(ControllerRequest request,ControllerResponse response) throws ControllerException{
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
				/** AGREGADO JBUGARIN 15/03/2007	 **/
				String oficReg=null;
				String regPubId=null;
				/** FIN AGREGADO JBUGARIN 15/03/2007 **/
				DboTaSoliDevo devolucion=new DboTaSoliDevo();
				String solDevId= req.getParameter("hidNumSolDev");
				devolucion.setConnection(dconn);
				devolucion.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solDevId);
				devolucion.find();
				
				SolicitudDevolucionBean solDevBean=new SolicitudDevolucionBean();
				solDevBean.setNumInforme(devolucion.getField(DboTaSoliDevo.CAMPO_NU_INFO));
				solDevBean.setNumHojaTramite(devolucion.getField(DboTaSoliDevo.CAMPO_NU_TRAM));
				solDevBean.setFechaTramite(FechaUtil.expressoDateToUtilDate(devolucion.getField(DboTaSoliDevo.CAMPO_FE_TRAM)).replace('/','.'));
				String cuentaId= devolucion.getField(DboTaSoliDevo.CAMPO_CUENTA_ID);
				/** AGREGADO JBUGARIN 15/03/2007 **/
				oficReg=devolucion.getField(DboTaSoliDevo.CAMPO_OFIC_REG_ID);
				regPubId=devolucion.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID);
				/** FIN AGREGADO JBUGARIN 15/03/2007 **/
				DboCuenta cuentaCreador=new DboCuenta();
				cuentaCreador.setConnection(dconn);
				cuentaCreador.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaId);
				cuentaCreador.find();
				DboPeNatu penatu= new DboPeNatu();
				penatu.setConnection(dconn);
				penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuentaCreador.getField(DboPeNatu.CAMPO_PE_NATU_ID));
				penatu.find();
				ComprobanteBean comprobante=new ComprobanteBean();
				if(devolucion.getField(DboTaSoliDevo.CAMPO_TIPO_USR)!=null&&devolucion.getField(DboTaSoliDevo.CAMPO_TIPO_USR).equalsIgnoreCase(Constantes.TIPO_USR_ORGANIZACION)){
					DecimalFormat df = new DecimalFormat("0.00");
					String strMonto=HelperCuenta.obtenerTotalOrganizacion(solDevId,dconn);
					comprobante.setMonto(df.format(Double.parseDouble(strMonto)));
				}else{
					DecimalFormat df = new DecimalFormat("0.00");
					String strMonto=HelperCuenta.obtenerTotalUsuario(solDevId,dconn);
					comprobante.setMonto(df.format(Double.parseDouble(strMonto)));	
				}
				comprobante.setNombreEntidad(penatu.getField(DboPeNatu.CAMPO_NOMBRES)+" "+penatu.getField(DboPeNatu.CAMPO_APE_PAT)+" "+penatu.getField(DboPeNatu.CAMPO_APE_MAT));
				/****/
				Locale local= new Locale("es","ES");

				java.util.Date hoy= new java.util.Date();
				SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
				String fecha="";
				/*
				String oficReg=userBean.getOficRegistralId();
				String regPubId=userBean.getRegPublicoId();
				*/
				//fecha=HelperSolicitudDevolucion.obtenerDepartamento(regPubId,oficReg,dconn)+", ";
		
				//fecha= fecha+sd.format(hoy);
				/** MODIFICADO JBUGARIN 15/03/2007 **/
				fecha=HelperSolicitudDevolucion.obtenerDepartamento(regPubId,oficReg,dconn)+", ";
				fecha= fecha.substring(0,1)+fecha.substring(1,fecha.length()).toLowerCase() +sd.format(hoy);
				/** FIN MODIFICADO JBUGARIN 15/03/2007 **/
				BigDecimal monto = new BigDecimal(comprobante.getMonto());

				String montoAsWord = DTEGeneratorHelper.montoAsWords(monto);

				ExpressoHttpSessionBean.getRequest(request).setAttribute("montoPalabra", montoAsWord.toUpperCase());
				ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
				req.setAttribute("solDev",solDevBean);
				req.setAttribute("comprobante",comprobante);
		
				response.setStyle("verInformeBaja");
			
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
	protected ControllerResponse runGeneraSolicitudDevolucionState(ControllerRequest request,ControllerResponse response) throws ControllerException 
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
				String oficinas=req.getParameter("cboOficinas");
				
				String regispubid=req.getParameter("hidRegisPubId");
				String oficregid=req.getParameter("hidOficRegId");
				
				if(userBean.getPerfilId()==PERFIL_ADMIN_ORG_EXT){
					HelperSolicitudDevolucion.crearSolicitudBaja(userBean.getCuentaId(),regispubid,oficregid,Constantes.TIPO_USR_ORGANIZACION,dconn);
					HelperCuenta.bloquearOrganizacion(userBean.getCuentaId(),dconn);					
					req.setAttribute("tipo",Constantes.TIPO_USR_ORGANIZACION);
				}else{
					HelperSolicitudDevolucion.crearSolicitudBaja(userBean.getCuentaId(),regispubid,oficregid,Constantes.TIPO_USR_INDIVIDUAL,dconn);
					HelperCuenta.bloquearCuenta(userBean.getCuentaId(),dconn);
					req.setAttribute("tipo",Constantes.TIPO_USR_INDIVIDUAL);
				}
				dconn.commit();
				response.setStyle("mensajeBloqueoCuenta");
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
