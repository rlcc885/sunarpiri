package gob.pe.sunarp.extranet.prepago.controller;

import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.prepago.bean.MediosPagoBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.PagoBean;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaCertificadoBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.Job003;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.PagoTarjeta;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.Controller;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.Output;
import com.jcorporate.expresso.core.controller.State;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.prepago.IncrementarSaldo;
import gob.pe.sunarp.extranet.prepago.bean.IncrementarSaldoBean;

public class IncrementarSaldoController extends ControllerExtension {

	private String thisClass = IncrementarSaldoController.class.getName() + ".";
		
    public IncrementarSaldoController(){
		super();
		addState(new State("inicioAbono", "Muest"));
		addState(new State("solicitaDatos", "Ventana Credito"));
		addState(new State("iniciaCredito", "Inicio del pago del Credito"));
		addState(new State("iniciaDebito", "Inicio del pago del Credit"));
		addState(new State("resultadoAbonoCredito", "Respuesta de Crdito"));
		addState(new State("resultadoAbonoDebito", "Respuesta de Debito"));
		setInitialState("inicioAbono");
		//addState(new State("identificacionCuenta", "Ventana de Verificacion de Cuenta"));
		//addState(new State("reestablecePassword", "Ventana de Verificacion de Usuario"));
	}

	public String getTitle() {
		return new String("IncrementarSaldoController");
	}

    public ControllerResponse runInicioAbonoState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException {    
		// PASO 1: Selecciona la opción de prepago en línea
 		Output salidaMensaje = null;
		long perfilId = -1;
		long nivelAccId = -1;

		try{
			init(request);
			validarSesion(request);
			response.setStyle("prepagar");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}

	// Modificado por CCS - 15/06/06
    public ControllerResponse runSolicitaDatosState(ControllerRequest request, ControllerResponse response) throws ControllerException {    
    	String glosa = null;
    	int codServicio = 0;
    	
		try{
			init(request);
			validarSesion(request);

			glosa = request.getParameter("monto");
			
			if (request.getParameter("codigoservicio") != null)
				codServicio = Integer.parseInt(request.getParameter("codigoservicio"));
			
			if (glosa == null || glosa.length() == 0)
				glosa = "";

			IncrementarSaldoBean bean =	IncrementarSaldo.solicitarDatos(request.getParameter("monto"), request.getParameter("R1"), glosa, codServicio);

			response.addOutput(bean.getMedioId());
			response.addOutput(bean.getMonto());
			response.addOutput(bean.getFecha());
			//response.addOutput(bean.getGlosa());

			response.setStyle("ventCredito");
		}
		catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}
		catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			//rollback(conn, request);
			response.setStyle("error");
		}
		finally{
			end(request);
		}

		return response;
	}

	// Modificado por CCS - 15/06/06
    public ControllerResponse runIniciaCreditoState(ControllerRequest request, ControllerResponse response) throws ControllerException {    

		try {
			ExpressoHttpSessionBean.getSession(request).removeAttribute("comprobante");

			init(request);		
			validarSesion(request);

			PagoTarjeta pagoT = IncrementarSaldo.iniciaCredito((UsuarioBean)ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario"), request.getParameter("monto"), request.getParameter("medioId"), request.getParameter("numero"), request.getParameter("ano"), request.getParameter("mes"), request.getParameter("glosa"));
	
			HttpSession session = ExpressoHttpSessionBean.getSession(request);

			session.setAttribute("numOrden", pagoT.getPtbean().getNumOrden());
				
			Output urlParams = new Output("urlParams", pagoT.getPtbean().getUrl());
			
			response.addOutput(urlParams);
			response.setStyle("ventespera");
		}
		catch(CustomException ce){
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			response.setStyle("error");
		}
		catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}
		finally{
			end(request);
		}
		
		return response;
    }


    public ControllerResponse runIniciaDebitoState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException {    

 		Output salidaMensaje = null;
 		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		try{
			HttpSession session = ExpressoHttpSessionBean.getSession(request);

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			DboMediosPago medioPagoI = new DboMediosPago(dconn);
			
			//medioPagoI.setField(DboMediosPago.CAMPO_MEDIO_ID, request.getParameter("medioId"));
			medioPagoI.setField(DboMediosPago.CAMPO_MEDIO_ID, request.getParameter("R1").substring(1));
			medioPagoI.find();

			DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
			
			long numPedido = 0;
			
				pagoLineaI.clear();
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_VERIFIC, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "P");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_RESP, FechaUtil.stringTimeToOracleString(1,1,1900,0,0,0));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_SOL, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, FechaUtil.stringTimeToOracleString(1,1,1900,0,0,0));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_VENCIM, "011900");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MEDIO_ID, request.getParameter("R1").substring(1));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MONTO, request.getParameter("monto"));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID, null);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TERMINAL, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TRANSAC, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NUM_ITEMS, "1");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NUM_TARJ, "0");
				pagoLineaI.add();
				conn.commit();
				numPedido = (long) pagoLineaI.max(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID);
				conn.commit();
				session.setAttribute("numPedido", Long.toString(numPedido));
			
			StringBuffer url = new StringBuffer();
			url.append(gob.pe.sunarp.extranet.util.DirVisa.getInstance().getDirDesa());
			url.append("Prueba.do?");
			url.append("D0=").append(numPedido).append("&D1=").append("1").append("&D2=").append(request.getParameter("monto"));
			url.append("&D3=").append(medioPagoI.getField(DboMediosPago.CAMPO_MONEDA)).append("&D4=").append(medioPagoI.getField(DboMediosPago.CAMPO_COD_TIENDA));
			Output urlParams = new Output("urlParams", url.toString());
			response.addOutput(urlParams);
			
			response.setStyle("ventespera");
		}catch(DBException dbe){
			if(conn != null){
				//Loggy.warn("Error al acceder la BD.", "");
				try{
					conn.rollback();
				}catch(Throwable dbe1){
					//Loggy.warn("Error al hacer rollback a la BD.", "");
					throw new ControllerException("DBException (rollback): " + dbe1, dbe1);
				}
			}
			//Loggy.trace("Conexion cerrada.", "");
			throw new ControllerException("DBException: " + dbe, dbe);
		}catch(Exception ex){
			//Loggy.fatal("ERROR: " + ex.getMessage(), "");
			throw new ControllerException("Exception: " + ex, ex);
		}finally{
			//Loggy.trace("Se liberara la conexion del Pool.", "");
			pool.release(conn);
		}
		return response;
    }

    public ControllerResponse runResultadoAbonoCreditoState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException 
	{    
		// PASO 4: Proceso la respuesta de VISA
		Connection conn = null;
		DBConnectionFactory pool = null;
		boolean tarj = false;
		try
		{
			// recupero una conexión a base de datos
			pool = DBConnectionFactory.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			init(request);
			validarSesion(request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean usuario = (UsuarioBean) session.getAttribute("Usuario");
			if (isTrace(this)) System.out.println("Procesando Respuesta de VISA");
			if(ExpressoHttpSessionBean.getSession(request).getAttribute("comprobante") == null)
			{
							
				// Obtengo Numero de Orden de la sesion
				String numOrden = (String) session.getAttribute("numOrden");
				
				// Obtengo parametros de VISA
				String respuesta = request.getParameter("respuesta");
				String cod_tienda = request.getParameter("cod_tienda");
				String nordent = request.getParameter("nordent");
				String cod_acc = request.getParameter("cod_accion");
				if (isTrace(this)) System.out.println("Validando orden ");
				
				// Validacion extra
				if(((numOrden==null))||((!numOrden.equals("0")) && (!numOrden.equalsIgnoreCase(nordent))))
					throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Número de orden recibido no válido.");
				if (isTrace(this)) System.out.println("Orden " + numOrden);			
				if(respuesta.equals("2"))
				{
					if(!numOrden.equals("0"))
					{
					DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID, numOrden);
					pagoLineaI.find();
					StringBuffer cadena = new StringBuffer(DboPagoEnLinea.CAMPO_ESTADO);
					cadena.append("|").append(DboPagoEnLinea.CAMPO_TS_TRANSAC)
							.append("|").append(DboPagoEnLinea.CAMPO_FEC_HOR_RESP)
							.append("|").append(DboPagoEnLinea.CAMPO_COD_RETORNO)
							.append("|").append(DboPagoEnLinea.CAMPO_COD_ERROR);
					String a = pagoLineaI.getField(pagoLineaI.CAMPO_SOLICITUD_ID);
					
					if(a!=null && a!="")
						tarj = true;
					pagoLineaI.setFieldsToUpdate(cadena.toString());
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "X");
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_RESP, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_RETORNO, respuesta);
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_ERROR, cod_acc);
					pagoLineaI.update();
					
					//Para Pub Cert
					if(tarj)
					{
						Solicitud sol = (Solicitud) ExpressoHttpSessionBean.getSession(request).getAttribute("certNeg");
						sol.setConn(conn);
						sol.actualizaEstadoSol("X");
					}
					
					session.setAttribute("numOrden", "0");
					}
					String mensaje = "";
					if((cod_acc.equals("101")) || (cod_acc.equals("201")))
						mensaje = "Su Banco ha informado que su Tarjeta está vencida";
					else if(cod_acc.equals("102") || (cod_acc.equals("104")) || (cod_acc.equals("106")) || (cod_acc.equals("121")) || (cod_acc.equals("126")) || (cod_acc.equals("182")) || (cod_acc.equals("202")))
						mensaje = "Su Banco ha informado que su Tarjeta tiene algún tipo de Bloqueo";
					else if(cod_acc.equals("107") || (cod_acc.equals("117")) || (cod_acc.equals("118")) || (cod_acc.equals("180")) || (cod_acc.equals("192")) || (cod_acc.equals("306")) || (cod_acc.equals("807")) || (cod_acc.equals("904")) || (cod_acc.equals("909")) || (cod_acc.equals("912")) || (cod_acc.equals("943")) || (cod_acc.equals("965")))
						mensaje = "Su Banco ha informado que no pudo realizar la transacción. Nro. de Tarjeta no válido ó Fecha de Vencimiento incorrecta.";
					else if(cod_acc.equals("116"))
						mensaje = "Su Banco ha informado que los Fondos Insuficientes";
					else if(cod_acc.equals("208"))
						mensaje = "Su Banco ha informado que la Tarjeta utilizada está reportada como Perdida";
					else if(cod_acc.equals("209"))
						mensaje = "Su Banco ha informado que la Tarjeta utilizada está reportada como Robada";
					else if(cod_acc.equals("300"))
						mensaje = "Número de pedido duplicado. Favor no atender";
					else if(cod_acc.equals("666"))
						mensaje = "Problemas de comunicación. Intente más tarde";
					else
						mensaje = "Su Banco ha informado que no pudo realizar la transacción. Nro. de Tarjeta no válido ó Fecha de Vencimiento incorrecta.";
						
					conn.commit();
					throw new CustomException(Errors.EC_PARAM_MISSFORMED, mensaje);
				} else if(respuesta.equals("1"))
				{
					if(!numOrden.equals("0"))
					{
					PrepagoBean prepagoBean = new PrepagoBean();
					// Obtengo el MONTO BRUTO y el MEDIO ID de Tabla: Linea_Prepago con el Numero de Orden
					StringBuffer cadena = new StringBuffer(DboPagoEnLinea.CAMPO_MONTO);
					cadena.append("|").append(DboPagoEnLinea.CAMPO_MEDIO_ID);
					cadena.append("|").append(DboPagoEnLinea.CAMPO_SOLICITUD_ID);
					DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
					pagoLineaI.setFieldsToRetrieve(cadena.toString());
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID, numOrden);
					pagoLineaI.find();
					String b = pagoLineaI.getField(pagoLineaI.CAMPO_SOLICITUD_ID);
					
					if(b!=null && b!="")
						tarj = true;
					String monto_bruto = pagoLineaI.getField(DboPagoEnLinea.CAMPO_MONTO);
					String medioId = pagoLineaI.getField(DboPagoEnLinea.CAMPO_MEDIO_ID);
	/*Monto bruto*/	prepagoBean.setMontoBruto(Double.parseDouble(monto_bruto));
	/*Medio Id*/	prepagoBean.setMedioId(medioId);
					// Obteniendo los montos de descuento, comision, monto_neto
					DboParametros param = new DboParametros(dconn);
					param.setFieldsToRetrieve(DboParametros.CAMPO_VALOR);
					param.setField(DboParametros.CAMPO_COD_PRM, "CTC");
					param.find();
					double desc = Double.parseDouble(param.getField(DboParametros.CAMPO_VALOR));
					double comic = Double.parseDouble(monto_bruto) * (desc/100);
					double monto_neto = Double.parseDouble(monto_bruto) - comic;
	/*Descuento*/	prepagoBean.setDescuento(desc);
	/*Comision*/	prepagoBean.setComision(comic);			
	/*Monto Neto*/	prepagoBean.setMontoNeto(monto_neto);	// Monto al que se les descuenta la comision
					// Obtengo LineaPrepagoId
					String lineaPrePago;
					if(usuario.getFgIndividual()) 
					{
						lineaPrePago = usuario.getLinPrePago();
					}
					else
					{
						DboLineaPrepago linea = new DboLineaPrepago(dconn);
						linea.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
						linea.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, usuario.getCodOrg());
						linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID, null);
						linea.find();
						lineaPrePago = linea.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
					}
	/*Linea Prepago Id*/prepagoBean.setLineaPrepagoId(lineaPrePago);
	/*EFECTIVO*/	prepagoBean.setFlag_efectivo(false);
	/*Usuario*/		prepagoBean.setUsuario(usuario.getUserId());
	/*Ventanilla*/	prepagoBean.setFlag_ventan(false);
	/*NO VA*/		prepagoBean.setBancoId(null);
	/*NO VA*/		prepagoBean.setTipoCheque(null);
	/*NO VA*/		prepagoBean.setNumCheuqe(null);
					if(tarj)
						prepagoBean.setTipoConsAbono(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA);
					// Incrementa Saldo del cliente				
					LineaPrepago lineaCmd = new LineaPrepago();
					ComprobanteBean beancomp = lineaCmd.incrementaSaldo(usuario, prepagoBean, dconn);
					
					// AGREGADO CCS
					//SAUL
					//SE COMENTA EL CAMPO GLOSA - NO SE ENCUENTRA EN LA VERSION DE MIGRACION
					//beancomp.setGlosa(pagoLineaI.getField(pagoLineaI.CAMPO_GLOSA));
					
					//Hard-codeado porque sabemos que es VISA.
					beancomp.setTipoPago("VISA");
					double nuevo_saldo = lineaCmd.getSaldoActual(lineaPrePago,dconn);
									
					cadena = new StringBuffer(DboPagoEnLinea.CAMPO_ESTADO);
					cadena.append("|").append(DboPagoEnLinea.CAMPO_TS_TRANSAC)
							.append("|").append(DboPagoEnLinea.CAMPO_FEC_HOR_RESP)
							.append("|").append(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID)
							.append("|").append(DboPagoEnLinea.CAMPO_COD_RETORNO);
					pagoLineaI.setFieldsToUpdate(cadena.toString());
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "C");
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID, Long.toString(beancomp.getMov_id()));
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_RESP, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_RETORNO, respuesta);
					pagoLineaI.update();
					if(tarj)
					{
						Solicitud sol = (Solicitud) ExpressoHttpSessionBean.getSession(request).getAttribute("solCertNeg");
						sol.setConn(conn);
						sol.actualizaEstadoSol("C");
						PagoBean pbean = new PagoBean();
						pbean.setTpo_pago(Constantes.PAGO_TARJETA_DE_CREDITO);
						pbean.setAbono_id(beancomp.getAbono_id());
						pbean.setMonto(sol.getTotal());
						pbean.setSolicitud_id(sol.getSolicitud_id());
						pbean.setUsr_crea(usuario.getUserId());
						pbean.setUsr_modi(usuario.getUserId());
						sol.setPagoBean(pbean);
						sol.grabarPago();
						beancomp.setSolicitudId(sol.getSolicitud_id());
						beancomp.setTipoPub("C");
						
						if(usuario.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT || usuario.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO)
						{
							//String saldo_disponible = req.getParameter("saldo_disponible");//saldo orga
							//String saldo_inicial = req.getParameter("saldo_inicial");//saldo user
							//String nuevo_saldo = req.getParameter("nuevo_saldo");
							
							String linea_Organizacion = usuario.getLineaPrePagoOrganizacion();
							String linea_Usuario = usuario.getLinPrePago();
							
							PrepagoBean prepagoBean2 = new PrepagoBean();
							prepagoBean2.setFlag_transferencia(true);
							prepagoBean2.setLineaPrepagoId(linea_Usuario);
							prepagoBean2.setMontoBruto(Double.parseDouble(sol.getTotal()));
							
							LineaPrepago lineaCmd2 = new LineaPrepago();
							lineaCmd2.incrementaSaldo(usuario, prepagoBean2, dconn);
							
							prepagoBean2 = new PrepagoBean();
							prepagoBean2.setFlag_transferencia(true);
							prepagoBean2.setLineaPrepagoId(linea_Organizacion);
							prepagoBean2.setMontoBruto(Double.parseDouble(sol.getTotal()));
							prepagoBean2.setTransacId(-1);					// -1 : Significa que en Consumo se pondra nulo (null).
							
							lineaCmd2.reduceSaldo(usuario, prepagoBean2, dconn);
							
							//leer nuevo saldo
							if (usuario.getFgInterno()==false)			
							{				
								double nuevoSaldo = lineaCmd2.getSaldoActual(usuario.getLinPrePago(),dconn);
								usuario.setSaldo(nuevoSaldo);
							}
							
						}
						
						LogAuditoriaCertificadoBean bt = new LogAuditoriaCertificadoBean();
						
						//Datos generales
						//bt.setUsuarioSession();
						//datos particulares de esta transaccion
						bt.setOficRegId(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_id());
						bt.setRegPubId(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getReg_pub_id());
						bt.setSolicitud_id(sol.getSolicitud_id());
						bt.setTipoCertificado(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_id());
						sol.recuperaServicio();
						bt.setCodigoServicio(Integer.parseInt(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getServicio_id()));
						bt.setUsuarioSession(usuario);
                                                //Modificado por: Proyecto Filtros de Acceso
                                                //Fecha: 02/10/2006
                                                //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
                                                bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
                                                //Fecha: 08/10/2006             
                                                bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
                                                //Fin Modificación
						bt.setCantidad("1");
						try
						{
							if(Integer.parseInt(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_pag())>1)
								bt.setCantidad(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_pag());
						}
						catch (NumberFormatException ne)
						{
							
						}
						
						if (Propiedades.getInstance().getFlagTransaccion()==true)
						{
							//Tarifario
							/*StringBuffer quebusq = new StringBuffer();
							
							quebusq.append("SELECT gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
							quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
							quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
							
							pstmt = conn.prepareStatement(quebusq.toString());
							pstmt.setString(1,bObjSol.getLibro());
							pstmt.setInt(2,110);
							rsetGla = pstmt.executeQuery();
							
							if(!rsetGla.next())
							throw new CustomException("Criterio no disponible");
							*/
							bt.setCodigoGLA(Integer.parseInt(sol.getObjetoSolicitudList(0).getCod_GLA()));
							Transaction.getInstance().registraTransaccion(bt,conn);
						/*				
						if (isTrace(this)) trace("Actualizamos el saldo en sesion", request);
						if (isTrace(this)) trace("Registrando uso de servicio", request);
						*/
							sol.grabarConsumoSol(bt.getConsumoId(),false);
						}
						
						UsuarioBean cliente = new UsuarioBean();
										
						if(bt.getUsuarioSession().getPerfilId()==Constantes.PERFIL_CAJERO)
						{
							cliente.setFgIndividual(true);
							cliente.setFgInterno(false);
							cliente.setRegPublicoId(bt.getRegPubId());
						}
						
						Job003 j = new Job003();
						j.setUsuario(usuario);
						j.setCodigoServicio(bt.getCodigoServicio());
						j.setRegPubId(bt.getRegPubId());
						j.setOficRegId(bt.getOficRegId());
						j.setArea(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getArea_reg_id());
										
						Thread llamador1 = new Thread(j);
						llamador1.start();
						
						if(sol.getDestinatarioBean().getTpo_env().equals(Constantes.TIPO_DOMICILIO))
						{
							bt.setCodigoServicio(TipoServicio.DELIVERY_CERT);
							bt.setCantidad("1");
							if (Propiedades.getInstance().getFlagTransaccion()==true)
							{
								//Tarifario
								bt.setCodigoGLA(0);
								Transaction.getInstance().registraTransaccion(bt,conn);
								sol.grabarConsumoSol(bt.getConsumoId(),true);
							}
							j = new Job003();
							j.setUsuario(usuario);
							j.setCodigoServicio(bt.getCodigoServicio());
							j.setRegPubId(bt.getRegPubId());
							j.setOficRegId(bt.getOficRegId());
							j.setArea(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getArea_reg_id());
											
							Thread llamador2 = new Thread(j);
							llamador2.start();
						}
						//Tarifa
						MailDataBean mailBean = new MailDataBean();
						mailBean.setTo(sol.getDatosRegisVerificadorBean().getCorreo_electronico());
						mailBean.setSubject("Solicitud de Publicidad Certificada No. " + sol.getSolicitud_id());
						mailBean.setBody("Ud ha recibido una nueva solicitud. Ingrese a la extranet y revise la solicitud "+ sol.getSolicitud_id());
						MailProcessor.getInstance().saveMail(mailBean, conn);
						
						beancomp.setSolDesc(((Solicitud)session.getAttribute("solCertNeg")).getDescripcion());
					}
					if (usuario.getFgIndividual())
					{
						if(usuario.getPerfilId()!=Constantes.PERFIL_CAJERO)
						{
							nuevo_saldo = lineaCmd.getSaldoActual(lineaPrePago,dconn);
							usuario.setSaldo(nuevo_saldo);
						}
					}
					ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", beancomp);
					}
				}
			}
			conn.commit();
			session.setAttribute("Usuario", usuario);
			response.setStyle("comprobante");
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			//principal(request);
			rollback(conn, request);
			response.setStyle(findForward(e.getForward(), request));
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

    public ControllerResponse runResultadoAbonoDebitoState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException {    

 		Output salidaMensaje = null;
 		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		try{
			init(request);
			validarSesion(request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			String numPedido = (String) session.getAttribute("numPedido");
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			String d0 = request.getParameter("D0");
			String r1 = request.getParameter("R1");
			String r2 = request.getParameter("R2");
			String r3 = request.getParameter("R3");
			String r4 = request.getParameter("R4");
			String r5 = request.getParameter("R5");
			
			// Validacion extra
				if(!numPedido.equalsIgnoreCase(d0))
					if (isTrace(this)) System.out.println("ERROR PERO CONTINUARE...... <Verificar>");
			//
			if(!r5.equals("00000")){
				DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID, numPedido);
				pagoLineaI.find();
				
				StringBuffer cadena = new StringBuffer(DboPagoEnLinea.CAMPO_ESTADO);
				cadena.append("|").append(DboPagoEnLinea.CAMPO_TS_TRANSAC).append("|").append(DboPagoEnLinea.CAMPO_FEC_HOR_RESP);
				cadena.append("|").append(DboPagoEnLinea.CAMPO_NRO_TERMINAL).append("|").append(DboPagoEnLinea.CAMPO_NRO_TRANSAC).append("|").append(DboPagoEnLinea.CAMPO_COD_VERIFIC);
				pagoLineaI.setFieldsToUpdate(cadena.toString());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "D");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, FechaUtil.stringTimeToOracleString(
				Integer.parseInt(r1.substring(6,8)), Integer.parseInt(r1.substring(4,6)), Integer.parseInt(r1.substring(0,4)),
				Integer.parseInt(r2.substring(0,2)), Integer.parseInt(r2.substring(2,4)), Integer.parseInt(r2.substring(4,6))));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_RESP, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TERMINAL, r4);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TRANSAC, r3);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_VERIFIC, r5);
				pagoLineaI.update();
				//Flag 0 = Debito
				ExpressoHttpSessionBean.getRequest(request).setAttribute("flag", "0");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "XXXXXXXX");
				response.setStyle("paginaerror");
				conn.commit();
			}else{
				PrepagoBean prepagoBean = new PrepagoBean();
				// Obtengo el monto bruto y el medio ID
				StringBuffer cadena = new StringBuffer(DboPagoEnLinea.CAMPO_MONTO);
				cadena.append("|").append(DboPagoEnLinea.CAMPO_MEDIO_ID);
				
				DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
				pagoLineaI.setFieldsToRetrieve(cadena.toString());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID, d0);
				pagoLineaI.find();
				String monto_bruto = pagoLineaI.getField(DboPagoEnLinea.CAMPO_MONTO);
				String medioId = pagoLineaI.getField(DboPagoEnLinea.CAMPO_MEDIO_ID);
				
/*Monto bruto*/	prepagoBean.setMontoBruto(Double.parseDouble(monto_bruto)); // Monto ing. poir el cliente
/*Medio Id*/	prepagoBean.setMedioId(medioId);
				
				// Obteniendo los montos de descuento, comision, monto_neto
				DboParametros param = new DboParametros(dconn);
				
				param.setFieldsToRetrieve(DboParametros.CAMPO_VALOR);
				param.setField(DboParametros.CAMPO_COD_PRM, "CTD");
				param.find();
				double desc = Double.parseDouble(param.getField(DboParametros.CAMPO_VALOR));
				double comic = Double.parseDouble(monto_bruto) * (desc/100);
				double monto_neto = Double.parseDouble(monto_bruto) - comic;
				
/*Descuento*/	prepagoBean.setDescuento(desc);
/*Comision*/	prepagoBean.setComision(comic);			
/*Monto Neto*/	prepagoBean.setMontoNeto(monto_neto);	// Monto al que se les descuenta la comision
				
				
				// Obtengo LineaPrepagoId
				UsuarioBean usuario = (UsuarioBean) session.getAttribute("Usuario");
				String lineaPrePago = usuario.getLinPrePago();
				
/*Linea Prepago Id*/prepagoBean.setLineaPrepagoId(lineaPrePago);
/*EFECTIVO*/	prepagoBean.setFlag_efectivo(false);
/*Usuario*/		prepagoBean.setUsuario(usuario.getUserId());
/*Ventanilla*/	prepagoBean.setFlag_ventan(false);

/*NO VA*/		prepagoBean.setBancoId(null);
/*NO VA*/		prepagoBean.setTipoCheque(null);
/*NO VA*/		prepagoBean.setNumCheuqe(null);

				// Incrementa Saldo del cliente				
				LineaPrepago lineaCmd = new LineaPrepago();
				ComprobanteBean beancomp = lineaCmd.incrementaSaldo(usuario, prepagoBean, dconn);
				
				//Hard-codeado porque sabemos que es VISA.
				beancomp.setTipoPago("DEBITO");
				double nuevo_saldo = lineaCmd.getSaldoActual(lineaPrePago, dconn);
								
			//	*** PASO 1: Actualizando Tabla Pago en Linea
				cadena = new StringBuffer(DboPagoEnLinea.CAMPO_ESTADO);
				cadena.append("|").append(DboPagoEnLinea.CAMPO_TS_TRANSAC).append("|").append(DboPagoEnLinea.CAMPO_FEC_HOR_RESP).append("|").append(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID);
				pagoLineaI.setFieldsToUpdate(cadena.toString());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "C");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID, Long.toString(beancomp.getMov_id()));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_RESP, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				pagoLineaI.update();
			//	*** FIN PASO 1
				
				conn.commit();
				
				//Seteo la variable de Sesion
				usuario.setSaldo(nuevo_saldo);
				session.setAttribute("Usuario", usuario);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("comprobante", beancomp);
				response.setStyle("comprobante");				
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
}
