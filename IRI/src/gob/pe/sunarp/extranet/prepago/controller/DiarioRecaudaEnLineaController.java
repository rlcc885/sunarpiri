package gob.pe.sunarp.extranet.prepago.controller;


import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.dbobj.DboVwRecaudaenlinea;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.prepago.bean.*;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.PagoBean;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaCertificadoBean;
import gob.pe.sunarp.extranet.util.*;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;


public class DiarioRecaudaEnLineaController extends ControllerExtension implements Constantes
{
	private String thisClass = DiarioRecaudaEnLineaController.class.getName() + ".";
	
	
	public DiarioRecaudaEnLineaController() 
	{
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("solicitaExtornarAbono", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("extornaAbono", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("consultaAbono", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("exporta", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("preparaCompletaPagoEnLinea", "Prepara Completa el Pago En Linea"));
		addState(new State("completaPagoEnLinea", "Completa el Pago En Linea"));
		addState(new State("reimprimirRecibo", "Muestra el recibo pra imprimirlo"));
		setInitialState("muestra");
	}
	
	
	public String getTitle() 
	{
		return new String("DiarioRecaudaEnLineaController");
	}
	
	
	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try
		{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
		//Capturo variables de INPUT
		
			String ano = request.getParameter("ano");
			String mes = request.getParameter("mes");
			String dia = request.getParameter("dia");
			String estado = request.getParameter("estado");
			//String recibo = request.getParameter("recibo");
			//String rec = request.getParameter("rec");
			
			String fecha_ = null;
			String fecha = null;
			//boolean busca_recibo = false;


			//if(rec != null && rec.equals("X"))
				//busca_recibo = true;


			//if(!busca_recibo){
				String fecha_aux = null;
				int _d;
				int _m;
				int _a;
				
			//PRIMERA VEZ QUE SE ENTRA A LA VENTANA - FECHA DE HOY
				if(ano == null){
					fecha_aux = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
					dia = fecha_aux.substring(0,2);
					mes = fecha_aux.substring(3,5);
					ano = fecha_aux.substring(6,10);
				}
				
				_d = Integer.parseInt(dia);
				_m = Integer.parseInt(mes);
				_a = Integer.parseInt(ano);
	
				fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
				fecha_ = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);
				if (isTrace(this)) trace("Buscando movimientos del dia " + fecha.substring(0, 10), request);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", ano);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mes", mes);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("dia", dia);

		//Empieza el QUERY


			DiarioRecaudaEnLineaDetalleBean bean = null;
			java.util.List lista = new java.util.ArrayList();


			DboVwRecaudaenlinea vista = new DboVwRecaudaenlinea(dconn);
			
			if(estado == null)
			{
				vista.setAppendWhereClause(DboVwRecaudaenlinea.CAMPO_TS_SOL_PAGO_VISA + " BETWEEN " + fecha + " AND " + fecha_ + " AND " + DboVwRecaudaenlinea.CAMPO_ESTADO_PAGO_VISA + " = 'C'");//cb
				ExpressoHttpSessionBean.getRequest(request).setAttribute("estado", "C");

			} else {
				if(estado.equals("T"))
					vista.setAppendWhereClause(DboVwRecaudaenlinea.CAMPO_TS_SOL_PAGO_VISA + " BETWEEN " + fecha + " AND " + fecha_);
				else
					vista.setAppendWhereClause(DboVwRecaudaenlinea.CAMPO_TS_SOL_PAGO_VISA + " BETWEEN " + fecha + " AND " + fecha_ + " AND " + DboVwRecaudaenlinea.CAMPO_ESTADO_PAGO_VISA + " = '" + estado + "'");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("estado", estado);
			}


			//*** MANEJO DE LA PAGINACION
			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			vista.setMaxRecords(num_pagina);
			int paginacion = 1;
			if(request.getParameter("pagina") != null)
				paginacion = Integer.parseInt(request.getParameter("pagina"));
				
			//*** FIN DE MANEJO DE PAGINACION						
			boolean hayNext = false;
			boolean encontro = false;


			java.util.List ve = vista.searchAndRetrieveListPaginado(paginacion);
			
			if(vista.getHaySiguiente())
				hayNext = true;
			
			for(Iterator i = ve.iterator(); i.hasNext();){
				encontro = true;


				DboVwRecaudaenlinea diario = (DboVwRecaudaenlinea) i.next();
				bean = new DiarioRecaudaEnLineaDetalleBean();
				bean.setNroAbonoExtranet(diario.getField(DboVwRecaudaenlinea.CAMPO_NRO_ABONO_EXTRANET));
				bean.setTsAbono(diario.getField(DboVwRecaudaenlinea.CAMPO_TS_ABONO));
				bean.setEstadoAbono(diario.getField(DboVwRecaudaenlinea.CAMPO_ESTADO_ABONO));
				bean.setConceptoAbono(diario.getField(DboVwRecaudaenlinea.CAMPO_CONCEPTO_ABONO));
				bean.setMontoAbono(diario.getField(DboVwRecaudaenlinea.CAMPO_MONTO_ABONO));
				bean.setNroComprobanteExtranet(diario.getField(DboVwRecaudaenlinea.CAMPO_NRO_COMPROBANTE_EXTRANET));
				bean.setApePatPagador(diario.getField(DboVwRecaudaenlinea.CAMPO_APE_PAT_PAGADOR));
				bean.setApeMatPagador(diario.getField(DboVwRecaudaenlinea.CAMPO_APE_MAT_PAGADOR));
				bean.setNombresPagador(diario.getField(DboVwRecaudaenlinea.CAMPO_NOMBRES_PAGADOR));
				bean.setRazsocPagador(diario.getField(DboVwRecaudaenlinea.CAMPO_RAZSOC_PAGADOR));
				bean.setPersonaIdPagador(diario.getField(DboVwRecaudaenlinea.CAMPO_PERSONA_ID_PAGADOR));
				bean.setNroPagoVisa(diario.getField(DboVwRecaudaenlinea.CAMPO_NRO_PAGO_VISA));
				bean.setUsrIdPago(diario.getField(DboVwRecaudaenlinea.CAMPO_USR_ID_PAGO));
				bean.setTsSolPagoVisa(FechaUtil.toPaginado(diario.getField(DboVwRecaudaenlinea.CAMPO_TS_SOL_PAGO_VISA)).substring(11,19));
				bean.setTsResPagoVisa(diario.getField(DboVwRecaudaenlinea.CAMPO_TS_RES_PAGO_VISA));
				bean.setRcPagoVisa(diario.getField(DboVwRecaudaenlinea.CAMPO_RC_PAGO_VISA));
				bean.setEcPagoVisa(diario.getField(DboVwRecaudaenlinea.CAMPO_EC_PAGO_VISA));
				bean.setEstadoPagoVisa(diario.getField(DboVwRecaudaenlinea.CAMPO_ESTADO_PAGO_VISA));
				bean.setSolicitudId(diario.getField(DboVwRecaudaenlinea.CAMPO_SOLICITUD_ID));
				lista.add(bean);
			}
			
//*PAGINACION EN EL JSP*//			
			if(paginacion == 1){
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
			}else{
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
			}
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
//*PAGINACION EN EL JSP*//
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista);
			
			vista.clearAll();
			vista.setAppendWhereClause(DboVwRecaudaenlinea.CAMPO_TS_SOL_PAGO_VISA + " BETWEEN " + fecha + " AND " + fecha_  + " AND " + DboVwRecaudaenlinea.CAMPO_ESTADO_PAGO_VISA + " = 'C'");//cb
			double abonos = vista.sum(DboVwRecaudaenlinea.CAMPO_MONTO_ABONO);
			vista.clearAll();
			vista.setAppendWhereClause(DboVwRecaudaenlinea.CAMPO_TS_SOL_PAGO_VISA + " BETWEEN " + fecha + " AND " + fecha_ );
			vista.setField(DboVwRecaudaenlinea.CAMPO_ESTADO_ABONO, "0");
			double extornos = vista.sum(DboVwRecaudaenlinea.CAMPO_MONTO_ABONO);
			double recaudado = abonos - extornos;
			DiarioReacudaBean b = new DiarioReacudaBean();
			b.setAno(ano);
			b.setMes(mes);
			b.setDia(dia);
			b.setTotalAbonos(Double.toString(abonos));
			b.setTotalExtornos(Double.toString(extornos));
			b.setTotalRecaudado(Double.toString(recaudado)); 
			ExpressoHttpSessionBean.getRequest(request).setAttribute("perfect", b);
			response.setStyle("siguiente");
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
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


	protected ControllerResponse runSolicitaExtornarAbonoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try
		{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


			String abonoId = request.getParameter("nroAbono");
			String tpoAbono = request.getParameter("tpoAbono").substring(0,1);
			String monto = request.getParameter("monto");
			
		//Paso 1
			DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
			vista.setField(DboVwDiariorecauda.CAMPO_ABONO_ID, abonoId);
			
			if(!vista.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono no existe o ya ha sido extornado", "errorPrepago");
			
			//14 nov
			//pintar en pantalla el monto FORMATEADO
			req.setAttribute("monto_formateado",Tarea.formatoNumero(monto));
			
			if (isTrace(this)) trace("Se procederá a extornar el abono # " + abonoId, request);
			
			StringBuffer problemas = new StringBuffer("Usuario");
				
		//Paso 2
			if(!vista.getField(DboVwDiariorecauda.CAMPO_USR_CAJA).equalsIgnoreCase(user.getUserId()))
			{
				if (isTrace(this)) trace(problemas.append(user.getUserId()).append(" trato de extornar abono # ").append(abonoId).append(". Accion denegada: Usuario no autorizado.").toString(), request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Usuario no autorizado", "errorPrepago");
			}
			
		//Paso 3
			String fecfor = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
			fecfor = fecfor.substring(6,10) + "-" + fecfor.substring(3,5) + "-" + fecfor.substring(0,2);
			
		/** No valido a partir de 08/2003
			if(!vista.getField(DboVwDiariorecauda.CAMPO_TS_CREA).startsWith(fecfor)){
				if (isTrace(this)) trace(problemas.append(user.getUserId()).append(" trato de extornar abono # ").append(abonoId).append(" el dia ").append(fecfor).append(". Accion denegada: Fecha no permitida para extornar dicho abono.") .toString(), request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono no disponible para ser extornado en la fecha: " + fecfor, "errorPrepago");
			}
		**/	

		// Paso X : Verifica que el abono no haya sido asociado anteriormente			
			String reciboAsociado = vista.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
			
			if(reciboAsociado != null & reciboAsociado.trim().length() > 0){
				if (isTrace(this)) trace(problemas.append(user.getUserId()).append(" trato de extornar abono # ").append(abonoId).append(",pero ya esta asociado al recibo # ").append(reciboAsociado).append(". Accion denegada.") .toString(), request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono ya está asociado a un recibo", "errorPrepago");
			}


		//Se anadio para continiar con los siguientes pasos (4)
			DboAbono abo = new DboAbono(dconn);
			abo.setFieldsToRetrieve(DboAbono.CAMPO_FG_CIERRE);
			abo.setField(DboAbono.CAMPO_ABONO_ID, abonoId);
			
			if(!abo.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No existe abono: " + abonoId, "errorPrepago");
		
		//Paso 4	
			if(abo.getField(DboAbono.CAMPO_FG_CIERRE).equalsIgnoreCase("1"))
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono no disponible para ser extornado", "errorPrepago");
		
		//Paso 5
			if(vista.getField(DboVwDiariorecauda.CAMPO_ESTADO).equalsIgnoreCase("0"))
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono ya ha sido extornado", "errorPrepago");
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("nroAbono", abonoId);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tpoAbono", tpoAbono);
			
		//Formando el QUERY
			MultiDBObject multi = new MultiDBObject(dconn);
			
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuentaJuris", "a");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPerfilCuenta", "b");
				
			multi.setForeignKey("a", DboCuentaJuris.CAMPO_CUENTA_ID, "b", DboPerfilCuenta.CAMPO_CUENTA_ID);
				
			multi.setField("a", DboCuentaJuris.CAMPO_REG_PUB_ID, user.getRegPublicoId());
			multi.setField("a", DboCuentaJuris.CAMPO_OFIC_REG_ID, user.getOficRegistralId());
			multi.setField("b", DboPerfilCuenta.CAMPO_PERFIL_ID, "50");
			multi.setField("b", DboPerfilCuenta.CAMPO_ESTADO, "1");
			
			java.util.Vector v = new java.util.Vector();
			
			for(java.util.Iterator list = multi.searchAndRetrieve().iterator();list.hasNext();)
			{
				MultiDBObject oneMulti = (MultiDBObject) list.next();
				v.add(oneMulti.getField("b", DboPerfilCuenta.CAMPO_CUENTA_ID));
			}
			
			if (v.size()==0)
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No existe usuario TESORERO", "errorPrepago");
			
			StringBuffer a = new StringBuffer();
			
			if(v.size() > 1)
			{
				a.append(" IN (").append((String)v.get(0));
				
				for(int x = 1; x < v.size(); x++)
				{
					a.append(",").append((String)v.get(x));
//					if (x< (v.size()-1))
//						a.append(",");
				}
				a.append(") ");
			}
			else
				a.append(" = ").append(  ((String) v.get(0))  );
				
			
			multi = new MultiDBObject(dconn);
			
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "a");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "b");
				
			multi.setForeignKey("a", DboPeNatu.CAMPO_PE_NATU_ID, "b", DboCuenta.CAMPO_PE_NATU_ID);
			multi.setAppendWhereClause(DboCuenta.CAMPO_CUENTA_ID + a.toString());
			
			TesoreroBean bean = null;
			java.util.List listas = new java.util.ArrayList();
						
			for(java.util.Iterator list = multi.searchAndRetrieve().iterator();list.hasNext();)
			{
				MultiDBObject oneMulti = (MultiDBObject) list.next();
				bean = new TesoreroBean();
				bean.setCuenta_id(oneMulti.getField("b", DboCuenta.CAMPO_CUENTA_ID));
				bean.setUsr_id(oneMulti.getField("b", DboCuenta.CAMPO_USR_ID));
				bean.setApe_pat(oneMulti.getField("a", DboPeNatu.CAMPO_APE_PAT));
				bean.setApe_mat(oneMulti.getField("a", DboPeNatu.CAMPO_APE_MAT));
				bean.setNombres(oneMulti.getField("a", DboPeNatu.CAMPO_NOMBRES));
				listas.add(bean);
			}
			ExpressoHttpSessionBean.getRequest(request).setAttribute("listas", listas);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("monto", monto);
			response.setStyle("extorno");
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}



	protected ControllerResponse runExtornaAbonoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			if (isTrace(this)) trace("Iniciando el proceso de extorno", request);
				
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);


			String abonoId = request.getParameter("nroAbono");
			String tpoAbono = request.getParameter("tpoAbono").substring(0,1);
			String glosa = request.getParameter("glosa");
			String pwCajero = request.getParameter("pwCajero");
			String idTesorero = request.getParameter("idTesorero");
			String pwTesorero = request.getParameter("pwTesorero");
			String monto = request.getParameter("monto");
			
			if(pwCajero == null || pwCajero.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Se olvidó ingresar el password del usuario Cajero", "errorPrepago");
			
			if(pwTesorero == null || pwTesorero.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Se olvidó ingresar el password del usuario Tesorero", "errorPrepago");


				SecAdmin secAdmin = SecAdmin.getInstance();
				boolean cajero = true;
				if (isTrace(this)) trace("Validando el password del cajero y del tesorero", request);
				try {
					String usuario = user.getUserId();
					String password = request.getParameter("pwCajero");
					/**01/10/2003 Para rastrear problema de extorno y TAM**/
					if (isTrace(this)) trace("SECADMIN valida cajero " + usuario, request);
					secAdmin.validaUsuario(usuario, password);
					/**01/10/2003 Para rastrear problema de extorno y TAM**/
					if (isTrace(this)) trace("SECADMIN cajero OK", request);
					
					cajero = false;
					usuario = request.getParameter("idTesorero");
					password = request.getParameter("pwTesorero");
					/**01/10/2003 Para rastrear problema de extorno y TAM**/
					if (isTrace(this)) trace("SECADMIN valida tesorero " + usuario, request);
					secAdmin.validaUsuario(usuario, password);
					/**01/10/2003 Para rastrear problema de extorno y TAM**/
					if (isTrace(this)) trace("SECADMIN tesorero OK", request);
					
				} catch (CustomException ce) {
					/**01/10/2003 Para rastrear problema de extorno y TAM**/
					if (isTrace(this)) trace("SECADMIN KO", request);
					/**01/10/2003 Para rastrear problema de extorno y TAM**/
					if (isTrace(this)) System.out.println(ce.toString());
					if(cajero)
						throw new CustomException(ce.getCodigoError(), "Password de Cajero es incorrecto", "errorPrepago");
					else
						throw new CustomException(ce.getCodigoError(), "Password de Tesorero es incorrecto", "errorPrepago");
				}
			
			DboAbono abono = new DboAbono(dconn);
			abono.setField(DboAbono.CAMPO_ABONO_ID, abonoId);
			
			if(!abono.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se encontro abono", "errorPrepago");
				
			abono.setFieldsToUpdate(DboAbono.CAMPO_ESTADO);
			abono.setField(DboAbono.CAMPO_ESTADO, "0");
			abono.update();
			
			DboComprobante compro = new DboComprobante(dconn);
			compro.setField(DboComprobante.CAMPO_ABONO_ID, abonoId);
			
			if(!compro.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se encontro comprobante", "errorPrepago");
			
			compro.setFieldsToUpdate(DboComprobante.CAMPO_ESTADO);
			compro.setField(DboComprobante.CAMPO_ESTADO, "1");
			compro.update();
			
			DboExtorno extorno = new DboExtorno(dconn);
			
			extorno.setField(DboExtorno.CAMPO_USR_CAJA, user.getUserId());
			extorno.setField(DboExtorno.CAMPO_USR_TESO, idTesorero);
			extorno.setField(DboExtorno.CAMPO_MONTO, monto);
			extorno.setField(DboExtorno.CAMPO_GLOSA, glosa);
			extorno.setField(DboExtorno.CAMPO_ABONO_ID, abonoId);
			extorno.setField(DboExtorno.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			extorno.add();
			
			MultiDBObject m = new MultiDBObject(dconn);


			m.addDBObj("gob.pe.sunarp.extranet.dbobj.DboAbono", "a");
			m.addDBObj("gob.pe.sunarp.extranet.dbobj.DboMovimiento", "b");
					
			m.setForeignKey("a", DboAbono.CAMPO_MOVIMIENTO_ID, "b", DboMovimiento.CAMPO_MOVIMIENTO_ID);
					
			m.setField("a", DboAbono.CAMPO_ABONO_ID, abonoId);
				
			java.util.Vector vectorcillo =  m.searchAndRetrieve();
				
			if(vectorcillo.size() <= 0)
			{
				if (isTrace(this)) trace("No se pudo obtener Linea Prepago del match de las tablas Movimiento y Abono con AbonoId # " + abonoId + " - Registro no existe.", request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se pudo obtener Linea Prepago", "errorPrepago");
			}
				
			if(vectorcillo.size() > 1)
			{
				if (isTrace(this)) trace("No se pudo obtener Linea Prepago del match de las tablas Movimiento y Abono con AbonoId # " + abonoId + " - Existe mas de un registro y solo debe haber uno.", request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se pudo obtener Linea Prepago. Existe mas de una Linea Prepago", "errorPrepago");
			}
			
			m = (MultiDBObject) vectorcillo.get(0);
				
			String lineacita = m.getField("b", DboMovimiento.CAMPO_LINEA_PREPAGO_ID);
				
			PrepagoBean prepagoBean = new PrepagoBean();
			prepagoBean.setTransacId(-1);
			prepagoBean.setEsExtorno(true);
			prepagoBean.setMontoBruto(Double.parseDouble(monto));
			prepagoBean.setLineaPrepagoId(lineacita);
				
			if(tpoAbono.equalsIgnoreCase("D"))
				prepagoBean.setEsFgDeposito(true);
			else if(tpoAbono.equalsIgnoreCase("A"))
				prepagoBean.setEsFgDeposito(false);
				
			//añadido por publicidad certificada
			prepagoBean.setTipoConsAbono(tpoAbono);
				
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(user, prepagoBean, dconn);


			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "El abono # " + abonoId + " ha sido extornado satisfactoriamente");
			conn.commit();
			transition("muestra", request, response);
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runCompletaPagoEnLineaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		boolean tarj = false;
		
		try
		{
			init(request);
			validarSesion(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			if (isTrace(this)) trace("Iniciando el proceso de completado de pago en linea ", request);
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			//cambia el estado de pago en linea a Completado
			String numOrden = request.getParameter("nroPago");
			String sGlosa = request.getParameter("glosa");
			
			String sYear = request.getParameter("year");
			String sMonth = request.getParameter("month");
			String sDay = request.getParameter("day");
			String sHora = request.getParameter("hora");
			String sMinutos = request.getParameter("minutos");
			String sSegundos = request.getParameter("segundos");
			String sMm = request.getParameter("mm");
			
			java.text.DateFormat dateF = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
			java.util.Date da = dateF.parse(sYear + "-" + sMonth + "-" + sDay + " " + sHora + ":" + sMinutos + ":" + sSegundos + " " + sMm);
			long dateTime = da.getTime();
			//validando password
			SecAdmin secAdmin = SecAdmin.getInstance();		
			if (isTrace(this)) trace("Validando el password del tesorero", request);
			try 
			{
				String usuarioId = user.getUserId();
				String password = request.getParameter("pwTesorero");
				secAdmin.validaUsuario(usuarioId, password);
									
			} 
			catch (CustomException ce) 
			{
				throw new CustomException(ce.getCodigoError(), "Password incorrecto", "errorPass");
			}
			/****/
			
			// PASO 4: Proceso la respuesta de VISA
			
			//if(ExpressoHttpSessionBean.getSession(request).getAttribute("comprobante") == null)
			//{
							
				String respuesta = "1";
				if (isTrace(this)) System.out.println("Validando orden ");
				
				// Validacion extra
				if((numOrden==null)||(numOrden.equals("0")))
					throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Número de orden recibido no válido.");
				if (isTrace(this)) 
					System.out.println("Orden " + numOrden);
				//if(respuesta.equals("1"))
				//{
					//if(!numOrden.equals("0"))
					//{
						PrepagoBean prepagoBean = new PrepagoBean();
						// Obtengo el MONTO BRUTO y el MEDIO ID de Tabla: Linea_Prepago con el Numero de Orden
						StringBuffer cadena = new StringBuffer(DboPagoEnLinea.CAMPO_MONTO);
						cadena.append("|").append(DboPagoEnLinea.CAMPO_MEDIO_ID);
						cadena.append("|").append(DboPagoEnLinea.CAMPO_SOLICITUD_ID);
						cadena.append("|").append(DboPagoEnLinea.CAMPO_USR_CREA);
						DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
						pagoLineaI.setFieldsToRetrieve(cadena.toString());
						pagoLineaI.setField(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID, numOrden);
						pagoLineaI.find();
						String b = pagoLineaI.getField(pagoLineaI.CAMPO_SOLICITUD_ID);
						String userCrea = pagoLineaI.getField(pagoLineaI.CAMPO_USR_CREA);
				
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
						UsuarioBean usuario = Tarea.getUserBean(dconn,userCrea);
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
							
						//Hard-codeado porque sabemos que es VISA.
						beancomp.setTipoPago("VISA");
						double nuevo_saldo = lineaCmd.getSaldoActual(lineaPrePago,dconn);
						
						cadena = new StringBuffer(DboPagoEnLinea.CAMPO_ESTADO);
						cadena.append("|").append(DboPagoEnLinea.CAMPO_TS_TRANSAC)
							.append("|").append(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID)
							.append("|").append(DboPagoEnLinea.CAMPO_COD_RETORNO);
						pagoLineaI.setFieldsToUpdate(cadena.toString());
						pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "C");
						pagoLineaI.setField(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID, Long.toString(beancomp.getMov_id()));
						pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(dateTime)));
						pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_RETORNO, respuesta);
						pagoLineaI.update();
						
						DboPagoEnLineaComp dboPagoComp = new DboPagoEnLineaComp(dconn);
						dboPagoComp.setField(dboPagoComp.CAMPO_PAGO_EN_LINEA_ID, numOrden);
						dboPagoComp.setField(dboPagoComp.CAMPO_COMENTARIO, sGlosa);
						dboPagoComp.setField(dboPagoComp.CAMPO_USR_MODI, user.getUserId());
						dboPagoComp.add();
						
						if(tarj)
						{
							//Solicitud sol = (Solicitud) ExpressoHttpSessionBean.getSession(request).getAttribute("solCertNeg");
							//sol.setConn(conn);
							Solicitud sol = new Solicitud(b,conn);
							sol.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_VERIFICAR);
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
							//Tarifa 2003/12/11
							bt.setCodigoGLA(Integer.parseInt(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCod_GLA()));
							
							bt.setUsuarioSession(usuario);
                                                        //Modificado por: Proyecto Filtros de Acceso
                                                        //Fecha: 02/10/2006
                                                        //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
                                                        bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
                                                        bt.setIdSesion(ExpressoHttpSessionBean.getSession(request).getId());
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
								Transaction.getInstance().registraTransaccion(bt,conn);
							/*				
							if (isTrace(this)) trace("Actualizamos el saldo en sesion", request);
							if (isTrace(this)) trace("Registrando uso de servicio", request);
							*/
								sol.grabarConsumoSol(bt.getConsumoId(),false);
							}
							/*
							UsuarioBean cliente = new UsuarioBean();
											
							if(bt.getUsuarioSession().getPerfilId()==Constantes.PERFIL_CAJERO)
							{
								cliente.setFgIndividual(true);
								cliente.setFgInterno(false);
								cliente.setRegPublicoId(bt.getRegPubId());
							}
							*/
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
							
						}		
						
						//ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", beancomp);
					//}
				//}
			//}
			
			
			conn.commit();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "La operación ha sido completada satisfactoriamente");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "DiarioRecaudaEnLinea.do");
			//session.setAttribute("Usuario", usuario);
			response.setStyle("completa");
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
	
	protected ControllerResponse runPreparaCompletaPagoEnLineaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			if (isTrace(this)) trace("Iniciando el proceso de completado de pago en linea ", request);
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			//cambia el estado de pago en linea a Completado
			String nroPago = request.getParameter("nroPago");
			String monto = request.getParameter("monto");
			String solicitudId = request.getParameter("solicitudId");
			if(solicitudId==null) solicitudId="";
			
			//llama al metodo completar pago de la solicitud
			
/*
			String abonoId = request.getParameter("nroAbono");
			String tpoAbono = request.getParameter("tpoAbono").substring(0,1);
			String glosa = request.getParameter("glosa");
			String pwCajero = request.getParameter("pwCajero");
			String idTesorero = request.getParameter("idTesorero");
			String pwTesorero = request.getParameter("pwTesorero");
			String monto = request.getParameter("monto");
			
			if(pwCajero == null || pwCajero.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Se olvidó ingresar el password del usuario Cajero", "errorPrepago");
			
			if(pwTesorero == null || pwTesorero.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Se olvidó ingresar el password del usuario Tesorero", "errorPrepago");


				SecAdmin secAdmin = SecAdmin.getInstance();
				boolean cajero = true;
				if (isTrace(this)) trace("Validando el password del cajero y del tesorero", request);
				try {
					String usuario = user.getUserId();
					String password = request.getParameter("pwCajero");
					secAdmin.validaUsuario(usuario, password);
					
					cajero = false;
					usuario = request.getParameter("idTesorero");
					password = request.getParameter("pwTesorero");
					secAdmin.validaUsuario(usuario, password);
				} catch (CustomException ce) {
					if(cajero)
						throw new CustomException(ce.getCodigoError(), "Password de Cajero es incorrecto", "errorPrepago");
					else
						throw new CustomException(ce.getCodigoError(), "Password de Tesorero es incorrecto", "errorPrepago");
				}
			
			DboAbono abono = new DboAbono(dconn);
			abono.setField(DboAbono.CAMPO_ABONO_ID, abonoId);
			
			if(!abono.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se encontro abono", "errorPrepago");
				
			abono.setFieldsToUpdate(DboAbono.CAMPO_ESTADO);
			abono.setField(DboAbono.CAMPO_ESTADO, "0");
			abono.update();
			
			DboComprobante compro = new DboComprobante(dconn);
			compro.setField(DboComprobante.CAMPO_ABONO_ID, abonoId);
			
			if(!compro.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se encontro comprobante", "errorPrepago");
			
			compro.setFieldsToUpdate(DboComprobante.CAMPO_ESTADO);
			compro.setField(DboComprobante.CAMPO_ESTADO, "1");
			compro.update();
			
			DboExtorno extorno = new DboExtorno(dconn);
			
			extorno.setField(DboExtorno.CAMPO_USR_CAJA, user.getUserId());
			extorno.setField(DboExtorno.CAMPO_USR_TESO, idTesorero);
			extorno.setField(DboExtorno.CAMPO_MONTO, monto);
			extorno.setField(DboExtorno.CAMPO_GLOSA, glosa);
			extorno.setField(DboExtorno.CAMPO_ABONO_ID, abonoId);
			extorno.setField(DboExtorno.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			extorno.add();
			
			MultiDBObject m = new MultiDBObject(dconn);


			m.addDBObj("gob.pe.sunarp.extranet.dbobj.DboAbono", "a");
			m.addDBObj("gob.pe.sunarp.extranet.dbobj.DboMovimiento", "b");
					
			m.setForeignKey("a", DboAbono.CAMPO_MOVIMIENTO_ID, "b", DboMovimiento.CAMPO_MOVIMIENTO_ID);
					
			m.setField("a", DboAbono.CAMPO_ABONO_ID, abonoId);
				
			java.util.Vector vectorcillo =  m.searchAndRetrieve();
				
			if(vectorcillo.size() <= 0)
			{
				if (isTrace(this)) trace("No se pudo obtener Linea Prepago del match de las tablas Movimiento y Abono con AbonoId # " + abonoId + " - Registro no existe.", request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se pudo obtener Linea Prepago", "errorPrepago");
			}
				
			if(vectorcillo.size() > 1)
			{
				if (isTrace(this)) trace("No se pudo obtener Linea Prepago del match de las tablas Movimiento y Abono con AbonoId # " + abonoId + " - Existe mas de un registro y solo debe haber uno.", request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "No se pudo obtener Linea Prepago. Existe mas de una Linea Prepago", "errorPrepago");
			}
			
			m = (MultiDBObject) vectorcillo.get(0);
				
			String lineacita = m.getField("b", DboMovimiento.CAMPO_LINEA_PREPAGO_ID);
				
			PrepagoBean prepagoBean = new PrepagoBean();
			prepagoBean.setTransacId(-1);
			prepagoBean.setEsExtorno(true);
			prepagoBean.setMontoBruto(Double.parseDouble(monto));
			prepagoBean.setLineaPrepagoId(lineacita);
				
			if(tpoAbono.equalsIgnoreCase("D"))
				prepagoBean.setEsFgDeposito(true);
			else if(tpoAbono.equalsIgnoreCase("A"))
				prepagoBean.setEsFgDeposito(false);
				
			//añadido por publicidad certificada
			prepagoBean.setTipoConsAbono(tpoAbono);
				
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(user, prepagoBean, dconn);
			
*/
			//ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje", "El pago # " + nroPago + " ha sido completado satisfactoriamente");
			String date = (new Date(System.currentTimeMillis())).toString();
			ExpressoHttpSessionBean.getRequest(request).setAttribute("year", date.substring(0,4));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("month", date.substring(5,7));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("day", date.substring(8,10));
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("monto_formateado",Tarea.formatoNumero(monto));
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("nroPago", nroPago);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("monto", monto);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("solicitudId", solicitudId);
			conn.commit();
			response.setStyle("prepara");
			//transition("prepara", request, response);
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}


	protected ControllerResponse runConsultaAbonoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);


			String abonoId = request.getParameter("nroAbono");
			String tpoAbono = request.getParameter("tpoAbono").substring(0,1);


		//Paso 1
			DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
			
			vista.setFieldsToRetrieve(DboVwDiariorecauda.CAMPO_ESTADO);
			vista.setField(DboVwDiariorecauda.CAMPO_ABONO_ID, abonoId);
			
			if(!vista.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No se tiene registrado el abono # " + abonoId, "errorPrepago");


		//Paso 2
			if(vista.getField(DboVwDiariorecauda.CAMPO_ESTADO).equalsIgnoreCase("1"))
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono # " + abonoId + " no ha sido extornado", "errorPrepago");


			ExpressoHttpSessionBean.getRequest(request).setAttribute("nroAbono", abonoId);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tpoAbono", tpoAbono);


		//Paso 3
			DboExtorno extorno = new DboExtorno(dconn);
			
			extorno.setField(DboExtorno.CAMPO_ABONO_ID, abonoId);
			
			if(!extorno.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono # " + abonoId + " no se encuentra en la tabla Abonos, posiblemente no ha sido extornado.", "errorPrepago");


String monto = extorno.getField(extorno.CAMPO_MONTO);
ExpressoHttpSessionBean.getRequest(request).setAttribute("monto_formateado",Tarea.formatoNumero(monto));
			
			MultiDBObject multi = new MultiDBObject(dconn);
			
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "a");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "b");
				
			multi.setForeignKey("a", DboPeNatu.CAMPO_PE_NATU_ID, "b", DboCuenta.CAMPO_PE_NATU_ID);
			multi.setField("b", DboCuenta.CAMPO_USR_ID, extorno.getField(DboExtorno.CAMPO_USR_TESO));
			
			
			java.util.Iterator i = multi.searchAndRetrieve().iterator();
			
			if(!i.hasNext())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No existen datos para el usuario " + extorno.getField(DboExtorno.CAMPO_USR_TESO), "errorPrepago");
				
			MultiDBObject m = (MultiDBObject) i.next();
			
			ExtornoBean bean = null;
			bean = new ExtornoBean();
			bean.setExtorno_id(extorno.getField(DboExtorno.CAMPO_EXTORNO_ID));
			bean.setGlosa(extorno.getField(DboExtorno.CAMPO_GLOSA));
			bean.setCuentaTesorero_id(extorno.getField(DboExtorno.CAMPO_USR_TESO));
			bean.setApe_pat(m.getField("a", DboPeNatu.CAMPO_APE_PAT));
			bean.setApe_mat(m.getField("a", DboPeNatu.CAMPO_APE_MAT));
			bean.setNombres(m.getField("a", DboPeNatu.CAMPO_NOMBRES));
			bean.setAbono_id(abonoId);
			
			ExpressoHttpSessionBean.getSession(request).setAttribute("list1", bean);
			response.setStyle("extornodisabled");
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}



	protected ControllerResponse runExportaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		


DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
		//Capturo variables de INPUT
		
			String ano = request.getParameter("ano");
			String mes = request.getParameter("mes");
			String dia = request.getParameter("dia");
			String recibo = request.getParameter("recibo");
			String rec = request.getParameter("rec");
			
			String fecha_ = null;
			String fecha = null;
			boolean busca_recibo = false;


			if(rec != null && rec.equals("X"))
				busca_recibo = true;


			if(!busca_recibo){
				String fecha_aux = null;
				int _d;
				int _m;
				int _a;
				
			//PRIMERA VEZ QUE SE ENTRA A LA VENTANA - FECHA DE HOY
				if(ano == null){
					fecha_aux = FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis()));
					dia = fecha_aux.substring(0,2);
					mes = fecha_aux.substring(3,5);
					ano = fecha_aux.substring(6,10);
				}
				
				_d = Integer.parseInt(dia);
				_m = Integer.parseInt(mes);
				_a = Integer.parseInt(ano);
	
				fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
				fecha_ = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);
				if (isTrace(this)) trace("Iniciando proceso de Exportacion...", request);
				if (isTrace(this)) trace("Buscando movimientos del dia " + fecha.substring(0, 10), request);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", ano);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mes", mes);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("dia", dia);


			}else{
				if(recibo == null || recibo.trim().length() <= 0)
					throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar un número de recibo", "errorPrepago");
				
				if (isTrace(this)) trace("Buscando movimientos para el recibo # " + recibo, request);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("recibo", recibo);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("rec", rec);
			}


		//Empieza el QUERY
			DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
			
			vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, user.getUserId());


			if(!busca_recibo)
				//vista.setField(DboVwDiariorecauda.CAMPO_TS_CREA, " BETWEEN " + fecha + " AND " + fecha_);cb
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_);//cb
			else
				vista.setField(DboVwDiariorecauda.CAMPO_RCBO_ASOC, recibo);
				
			DiarioRecaudaDetalleBean bean = null;
			java.util.List lista = vista.searchAndRetrieveList(DboVwDiariorecauda.CAMPO_TS_CREA);
				


			vista.clearAll();
			vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, user.getUserId());


			if(!busca_recibo)
				//vista.setField(DboVwDiariorecauda.CAMPO_TS_CREA, " BETWEEN " + fecha + " AND " + fecha_);cb
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_);//cb
			else
				vista.setField(DboVwDiariorecauda.CAMPO_RCBO_ASOC, recibo);
				
			vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "1");
			double abonos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
			
			vista.clearAll();
			vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, user.getUserId());


			if(!busca_recibo)
				//vista.setField(DboVwDiariorecauda.CAMPO_TS_CREA, " BETWEEN " + fecha + " AND " + fecha_);cb
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_);//cb
			else
				vista.setField(DboVwDiariorecauda.CAMPO_RCBO_ASOC, recibo);


			vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "0");
			double extornos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);


			double recaudado = abonos - extornos;


			if(lista.size() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "No existe data para exportar.", "errorPrepago");
		
			String fname = "abono.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;
					
			StringBuffer stb = null;


			stb = new StringBuffer();
	
			stb.append("Cajero ID").append(", ");
			stb.append("Recibo").append(", ");
			stb.append("Hora").append(", ");
			stb.append("Abono").append(", ");
			stb.append("Tipo Persona").append(", ");
			stb.append("Razón Social / Apellidos Nombres").append(", ");
			stb.append("Monto").append(", ");
			stb.append("Tipo").append(", ");
			stb.append("Estado");
				
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());


			for(Iterator i = lista.iterator();i.hasNext();)
			{
				DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();
				
				stb = new StringBuffer();
	
				stb.append(x.getField(DboVwDiariorecauda.CAMPO_USR_CAJA)).append(", ");
				
				String aux = null;
				if(x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC) == null || x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC).trim().length() <= 0)
					aux = "SIN ASIGNAR";
				else
					aux = x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
					
				stb.append(aux).append(", ");
				
				stb.append(x.getField(DboVwDiariorecauda.CAMPO_TS_CREA).substring(11, 19)).append(", ");
				
				stb.append(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID)).append(", ");
				stb.append((x.getField(DboVwDiariorecauda.CAMPO_TIPO_USR).trim().equalsIgnoreCase("O"))?"PJ":"PN").append(", ");
				stb.append(x.getField(DboVwDiariorecauda.CAMPO_APE_PAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_APE_MAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_NOMBRES)).append(", ");
				stb.append(x.getField(DboVwDiariorecauda.CAMPO_MONTO)).append(", ");
				stb.append((x.getField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT).trim().equalsIgnoreCase("E"))?"Efectivo":"Cheque").append(", ");
				stb.append(x.getField(DboVwDiariorecauda.CAMPO_ESTADO).equals("1")?"Anular":"Anulada");


				//out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
			}
	
			//out_cliente = res.getWriter();
			out_cliente.println("");
			//out_cliente = res.getWriter();
			out_cliente.println("TOTAL ABONOS     " + abonos);
			//out_cliente = res.getWriter();
			out_cliente.println("TOTAL EXTORNOS   " + extornos);
			
			//out_cliente = res.getWriter();
			out_cliente.println("TOTAL RECAUDADO  " + recaudado);
			
			out_cliente.flush();
			out_cliente.close();
			response.setCustomResponse(true);
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
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
	
	protected ControllerResponse runReimprimirReciboState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);

			String abonoId = request.getParameter("nroAbono");
		//	String tpoAbono = request.getParameter("tpoAbono").substring(0,1);
			ComprobanteBean comprobanteBean = new ComprobanteBean();
			comprobanteBean.setAbono_id(abonoId+"");
			
			DboAbono abono = new DboAbono();
			abono.setConnection(dconn);
			abono.setFieldsToRetrieve(DboAbono.CAMPO_TS_CREA+"|"+DboAbono.CAMPO_OFIC_REG_ID+"|"+DboAbono.CAMPO_TIPO_USR+"|"+DboAbono.CAMPO_PERSONA_ID+"|"+DboAbono.CAMPO_USR_CAJA+"|"+DboAbono.CAMPO_TIPO_VENT+"|"+DboAbono.CAMPO_TPO_PAG_VENT);
			abono.setField(DboAbono.CAMPO_ABONO_ID,abonoId);
			abono.find();
			
			comprobanteBean.setCajero(abono.getField( DboAbono.CAMPO_USR_CAJA));
			comprobanteBean.setFecha_hora(abono.getField(DboAbono.CAMPO_TS_CREA));
			
			DboComprobante comprobante= new DboComprobante();
			comprobante.setConnection(dconn);
			comprobante.setFieldsToRetrieve(DboComprobante.CAMPO_COMPROBANTE_ID+"|"+DboComprobante.CAMPO_MONTO);
			comprobante.setField(DboComprobante.CAMPO_ABONO_ID,abonoId);
			comprobante.find();
						
			comprobanteBean.setComprobanteId(comprobante.getField( DboComprobante.CAMPO_COMPROBANTE_ID));
			
			double monto=Double.parseDouble(comprobante.getField( DboComprobante.CAMPO_MONTO));
			System.out.println("Monto "+monto);
			comprobanteBean.setMonto(String.valueOf(monto));
				
						
			DboOficRegistral dboOfic= new DboOficRegistral();
			dboOfic.setConnection(dconn);
			dboOfic.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,abono.getField(DboAbono.CAMPO_OFIC_REG_ID));
			dboOfic.setFieldsToRetrieve(DboOficRegistral.CAMPO_NOMBRE);
			dboOfic.find();
			
			comprobanteBean.setOficina(dboOfic.getField(DboOficRegistral.CAMPO_NOMBRE));
			String strTipoUsr=abono.getField( DboAbono.CAMPO_TIPO_USR);
			String aux=null;
			String numdoc=null;
			String doc=null;
			String contratoId=null;
			
			if(strTipoUsr.equalsIgnoreCase(TIPO_USR_INDIVIDUAL)){
				DboPersona persona = new DboPersona();
				persona.setConnection(dconn);
				persona.setField(DboPersona.CAMPO_PERSONA_ID,abono.getField(DboAbono.CAMPO_PERSONA_ID));
				persona.setFieldsToRetrieve(DboPersona.CAMPO_NUM_DOC_IDEN+"|"+DboPersona.CAMPO_TIPO_DOC_ID);
				persona.find();
					
				DboPeNatu penatu= new DboPeNatu();
				penatu.setConnection(dconn);
				penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_APE_MAT+"|"+DboPeNatu.CAMPO_APE_PAT+"|"+DboPeNatu.CAMPO_NOMBRES+"|"+DboPeNatu.CAMPO_PE_NATU_ID);
				penatu.setField(DboPeNatu.CAMPO_PERSONA_ID,abono.getField(DboAbono.CAMPO_PERSONA_ID));
				penatu.find();
				
				
				DboCuenta cuenta = new DboCuenta();
				cuenta.setConnection(dconn);
				cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID+"|"+DboCuenta.CAMPO_USR_ID);
				cuenta.setField(DboCuenta.CAMPO_PE_NATU_ID,penatu.getField(DboPeNatu.CAMPO_PE_NATU_ID));
				cuenta.find();
				comprobanteBean.setUserId(cuenta.getField(DboCuenta.CAMPO_USR_ID));
	
				DboContrato contrato = new DboContrato();
				contrato.setConnection(dconn);
				contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
				contrato.setField(DboContrato.CAMPO_CUENTA_ID,cuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
				contrato.find();
				contratoId=contrato.getField(DboContrato.CAMPO_CONTRATO_ID);
				aux = penatu.getField(DboPeNatu.CAMPO_APE_PAT) + " " + penatu.getField(DboPeNatu.CAMPO_APE_MAT) + " " + penatu.getField(DboPeNatu.CAMPO_NOMBRES);
				
				numdoc=persona.getField(DboPersona.CAMPO_NUM_DOC_IDEN);
					
				DboTmDocIden docIden= new DboTmDocIden();
				docIden.setConnection(dconn);
				docIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV+"|"+DboTmDocIden.CAMPO_DESCRIPCION);
				docIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,persona.getField(DboPersona.CAMPO_TIPO_DOC_ID));
				docIden.find();
				doc=docIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);

			}else if(strTipoUsr.equalsIgnoreCase(TIPO_USR_ORGANIZACION)){
				DboPersona persona = new DboPersona();
				persona.setConnection(dconn);
				persona.setFieldsToRetrieve(DboPersona.CAMPO_NUM_DOC_IDEN+"|"+DboPersona.CAMPO_TIPO_DOC_ID);
				persona.setField(DboPersona.CAMPO_PERSONA_ID,abono.getField(DboAbono.CAMPO_PERSONA_ID));
				persona.find();
				
				DboPeJuri pej = new DboPeJuri();
				pej.setConnection(dconn);
				pej.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC+"|"+DboPeJuri.CAMPO_PE_JURI_ID);
				pej.setField(DboPeJuri.CAMPO_PERSONA_ID, abono.getField(DboAbono.CAMPO_PERSONA_ID));
				pej.find();
				aux = pej.getField(DboPeJuri.CAMPO_RAZ_SOC);
			
				
				numdoc=persona.getField(DboPersona.CAMPO_NUM_DOC_IDEN);

				DboTmDocIden docIden= new DboTmDocIden();
				docIden.setConnection(dconn);
				docIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV+"|"+DboTmDocIden.CAMPO_DESCRIPCION);
				docIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,persona.getField(DboPersona.CAMPO_TIPO_DOC_ID));
				docIden.find();
				doc=docIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);
			
				DboContrato contrato = new DboContrato();
				contrato.setConnection(dconn);
				contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID+"|"+DboContrato.CAMPO_CUENTA_ID);
				contrato.setField(DboContrato.CAMPO_PE_JURI_ID,pej.getField(DboPeJuri.CAMPO_PE_JURI_ID));
				contrato.find();
				contratoId=contrato.getField(DboContrato.CAMPO_CONTRATO_ID);
				DboCuenta cuenta= new DboCuenta();
				cuenta.setConnection(dconn);
				cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
				cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,contrato.getField(DboContrato.CAMPO_CUENTA_ID));
				cuenta.find();
				comprobanteBean.setUserId(cuenta.getField(DboCuenta.CAMPO_USR_ID));
				
			}
			 
			
				
			if(abono.getField(DboAbono.CAMPO_TPO_PAG_VENT).equalsIgnoreCase(PAGO_CHEQUE)){
						
				DboPagoCheque pagoCheque = new DboPagoCheque();
				pagoCheque.setConnection(dconn);
				pagoCheque.setField(DboPagoCheque.CAMPO_ABONO_ID, abonoId);
				pagoCheque.setFieldsToRetrieve(DboPagoCheque.CAMPO_BANCO_ID+"|"+DboPagoCheque.CAMPO_NUMERO+"|"+DboPagoCheque.CAMPO_TPO_CHEQ);
				pagoCheque.find();		
				comprobanteBean.setNumcheque(pagoCheque.getField(DboPagoCheque.CAMPO_NUMERO));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("cheq", "x");
				comprobanteBean.setTipoPago(NOMBRE_TIPO_PAGO_CHEQUE);
				}else{
				comprobanteBean.setNumcheque(null);
				comprobanteBean.setTipoCheque(null);
				comprobanteBean.setBanco(null);
				comprobanteBean.setTipoPago(nOMBRE_TIPO_PAGO_EFECTIVO);
				}
				comprobanteBean.setDocumento(doc);
				comprobanteBean.setNumeroDoc(numdoc);
				comprobanteBean.setNombreEntidad(aux);
				comprobanteBean.setContratoId(contratoId);
				
			ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", comprobanteBean);		
			ExpressoHttpSessionBean.getRequest(request).setAttribute("anterior", "reimprimir");	
		
//			
			
			
			response.setStyle("reimprimir");
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}

}
