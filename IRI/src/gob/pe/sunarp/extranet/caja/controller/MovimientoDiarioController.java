package gob.pe.sunarp.extranet.caja.controller;

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
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.prepago.bean.*;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.util.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class MovimientoDiarioController extends ControllerExtension implements Constantes {
	private String thisClass = MovimientoDiarioController.class.getName() + ".";

	public MovimientoDiarioController() 
	{
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("buscaRecibo", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("buscaReciboFecha", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("asociaRecibo", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("solicitaExtornarAbono", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("extornaAbono", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("consultaAbono", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("exporta", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("reimprimirRecibo", "Muestra el recibo pra imprimirlo"));
		setInitialState("muestra");
	}

	public String getTitle() 
	{
		return new String("MovimientoDiarioController");
	}

	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		
		try{
			init(request);
			validarSesion(request);
			req = ExpressoHttpSessionBean.getRequest(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
					
		    //Capturo variables de INPUT
			String ano = request.getParameter("ano");
			String mes = request.getParameter("mes");
			String dia = request.getParameter("dia");
			//String recibo = request.getParameter("recibo");
			//String rec = request.getParameter("rec");
			String caja = request.getParameter("cboCajas");
			String fecha_aux = null;
			String fecha_ = null;
			String fecha = null;
			int _d ;
			int _m ;
			int _a ;
			System.out.println("CAJA:::JLBP:::"+caja);
			//Recuperando la lista de cajas
			List listaCajas = Tarea.getComboCajas(dconn,user.getRegPublicoId());
			req.setAttribute("arrRepr", listaCajas);
			
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
			DiarioRecaudaDetalleBean bean = null;
			List lista = new ArrayList();
			String userCaja = null;
			DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);

			//Armamos el where
			String wherePorCajas = "";
			if(caja == null || caja.equals("T")){
				if(listaCajas.size() > 0){
					wherePorCajas = " AND " + DboVwDiariorecauda.CAMPO_USR_CAJA + " IN ('";
				}
				for(int x = 0; x < listaCajas.size(); x++){
					if(x == (listaCajas.size() - 1)){
						wherePorCajas = wherePorCajas +  ((ComboBean)listaCajas.get(x)).getCodigo() + "'";
					} else {
						wherePorCajas = wherePorCajas +  ((ComboBean)listaCajas.get(x)).getCodigo() + "', '";
					}
				}
				if(listaCajas.size() > 0){
					wherePorCajas = wherePorCajas + ")";
				}
			} else {
				vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, caja);
			}
			
			vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_ + wherePorCajas);


			//*** MANEJO DE LA PAGINACION
			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			vista.setMaxRecords(num_pagina);
			int paginacion = 1;
			if(request.getParameter("pagina") != null)
				paginacion = Integer.parseInt(request.getParameter("pagina"));
				
			//*** FIN DE MANEJO DE PAGINACION						
			boolean hayNext = false;
			boolean encontro = false;

			List ve = vista.searchAndRetrieveListPaginado(paginacion);
			
			if(vista.getHaySiguiente())
				hayNext = true;
			
			for(Iterator i = ve.iterator(); i.hasNext();){
				encontro = true;

				DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();
				bean = new DiarioRecaudaDetalleBean();
				
				bean.setIdCajero(x.getField(DboVwDiariorecauda.CAMPO_USR_CAJA));
				
				String aux = null;
				if(x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC) == null || x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC).trim().length() <= 0)
					aux = "SIN ASIGNAR";
				else
					aux = x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
					
				bean.setNroRecibo(aux);
				//bean.setNroAbonoExtranet(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
				bean.setHora(FechaUtil.toPaginado(x.getField(DboVwDiariorecauda.CAMPO_TS_CREA)).substring(11, 19));
				bean.setIdAbono(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
				bean.setTipoPersona((x.getField(DboVwDiariorecauda.CAMPO_TIPO_USR).trim().equalsIgnoreCase("O"))?"PJ":"PN");
				bean.setNombre(x.getField(DboVwDiariorecauda.CAMPO_APE_PAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_APE_MAT) + " " + x.getField(DboVwDiariorecauda.CAMPO_NOMBRES));
				bean.setMonto(x.getField(DboVwDiariorecauda.CAMPO_MONTO));
				bean.setTipoPago((x.getField(DboVwDiariorecauda.CAMPO_TPO_PAG_VENT).trim().equalsIgnoreCase("E"))?"Efectivo":"Cheque");
				bean.setEsAnulado(x.getField(DboVwDiariorecauda.CAMPO_ESTADO));
				bean.setTipoAbono(x.getField(DboVwDiariorecauda.CAMPO_TIPO_VENT));
				//bean.setNumComprobante(x.getField(DboVwDiariorecauda.CAMPO_COMPR));
				bean.setNumComprobante("");
				lista.add(bean);
			}
			
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("lista", lista);
			
			vista.clearAll();

			double abonos =0;
			double extornos =0;
			double recaudado=0;
			
			if(caja == null || caja.equals("T")){
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_   +  wherePorCajas);//cb
				//vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "1");
				abonos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				vista.clearAll();
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_   + wherePorCajas);//cb
				vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "0");
				extornos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				recaudado = abonos - extornos;
				caja=null;
			}
			if(caja!=null) {
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_ +" AND "+DboVwDiariorecauda.CAMPO_USR_CAJA+"='"+caja+"'");//cb
				//vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "1");
				abonos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				vista.clearAll();
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_   + " AND "+DboVwDiariorecauda.CAMPO_USR_CAJA+"='"+caja+"'");//cb
				vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "0");
				extornos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				recaudado = abonos - extornos;
			}
						
			DiarioReacudaBean b = new DiarioReacudaBean();
			b.setAno(ano);
			b.setMes(mes);
			b.setDia(dia);
			b.setTotalAbonos(Double.toString(abonos));
			b.setTotalExtornos(Double.toString(extornos));
			b.setTotalRecaudado(Double.toString(recaudado)); 
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("perfect", b);
			
			 req.setAttribute("codigo",caja);
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

	protected ControllerResponse runAsociaReciboState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		boolean ok = false;
				
		try{
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
			
		//Capturo variables de INPUT
			String rec = request.getParameter("rec");

			if(rec != null && rec.equals("X"))
				throw new CustomException(Errors.EC_GENERIC_ERROR, "No existen abonos para asociar a un Numero de Recibo, o todos los abonos mostrados ya han sido asociados a otro Numero de Recibo", "errorPrepago");

			String ano = request.getParameter("ano");
			String mes = request.getParameter("mes");
			String dia = request.getParameter("dia");

			String fecha_ = null;
			String fecha = null;
			String recibo = request.getParameter("reciboAsoc");

			if(recibo == null || recibo.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Se olvido de ingresar un Numero de Recibo.", "errorPrepago");
			
			if(recibo.equalsIgnoreCase("SIN ASIGNAR"))
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "No se puede asignar abonos al recibo: " + recibo, "errorPrepago");

			String fecha_aux = null;
			int _d;
			int _m;
			int _a;
				
			_d = Integer.parseInt(dia);
			_m = Integer.parseInt(mes);
			_a = Integer.parseInt(ano);
	
			fecha = FechaUtil.stringTimeToOracleString(_d, _m, _a, 0, 0, 0);
			fecha_ = FechaUtil.stringTimeToOracleString(_d, _m, _a, 23, 59, 59);

			ExpressoHttpSessionBean.getRequest(request).setAttribute("ano", ano);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mes", mes);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("dia", dia);

		//Empieza el QUERY
			DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);
			
			vista.setFieldsToRetrieve(DboVwDiariorecauda.CAMPO_ABONO_ID + "|" + DboVwDiariorecauda.CAMPO_RCBO_ASOC + "|" + DboVwDiariorecauda.CAMPO_ESTADO);
			
			vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, user.getUserId());
			//vista.setField(DboVwDiariorecauda.CAMPO_TS_CREA, " BETWEEN " + fecha + " AND " + fecha_);cb
			vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_);//cb
			
			DiarioRecaudaDetalleBean bean = null;
			java.util.List lista = vista.searchAndRetrieveList(DboVwDiariorecauda.CAMPO_TS_CREA);
			
			if(lista.size() <= 0)
				throw new CustomException(Errors.EC_GENERIC_ERROR, "No existen abonos para asociar a un Numero de Recibo, o todos los abonos mostrados ya han sido asociados a otro Numero de Recibo", "errorPrepago");

			java.util.List liAbonoIds = new java.util.ArrayList();
			
			boolean hayAsociados = false;
			String numAsoc = null;
			
			for(Iterator i = lista.iterator(); i.hasNext();)
			{
				DboVwDiariorecauda x = (DboVwDiariorecauda) i.next();

				if(x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC) == null || x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC).trim().length() <= 0)
					//if(x.getField(DboVwDiariorecauda.CAMPO_ESTADO).equals("1"))	// Abono no extornado (anulado)
						liAbonoIds.add(x.getField(DboVwDiariorecauda.CAMPO_ABONO_ID));
				else{
					if(!hayAsociados)
						numAsoc = x.getField(DboVwDiariorecauda.CAMPO_RCBO_ASOC);
					hayAsociados = true;
				}
			}
			
			if(hayAsociados && !recibo.equals(numAsoc))
				throw new CustomException(Errors.EC_GENERIC_ERROR, "Usted ya asocio ciertos abonos al Numero de Recibo: " + numAsoc + ". Solo puede asociar recibos de este dia al Numero de Recibo: " + numAsoc, "errorPrepago");
				
			if(liAbonoIds.size() <= 0)
				throw new CustomException(Errors.EC_GENERIC_ERROR, "No existen abonos para asociar a un Numero de Recibo, o todos los abonos mostrados ya han sido asociados a otro Numero de Recibo", "errorPrepago");
			

			DboAbono abono = new DboAbono(dconn);
			
			if (isTrace(this)) trace("Se inicia la Asociacion de Recibos", request);
			
			for(java.util.Iterator it = liAbonoIds.iterator(); it.hasNext();){
				String abonitoId = (String) it.next();
				abono.clearAll();
				abono.setFieldsToUpdate(DboAbono.CAMPO_RCBO_ASOC + "|" + DboAbono.CAMPO_FG_CIERRE + "|" + DboAbono.CAMPO_TS_MODI);
				abono.setField(DboAbono.CAMPO_ABONO_ID, abonitoId);
				
				if(abono.find()){
					String aux_abono = abono.getField(DboAbono.CAMPO_RCBO_ASOC);
					
					if(aux_abono == null || aux_abono.trim().length() <= 0){
						abono.setField(DboAbono.CAMPO_RCBO_ASOC, recibo);
						abono.setField(DboAbono.CAMPO_FG_CIERRE, "1");
						abono.setField(DboAbono.CAMPO_TS_MODI, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
						abono.update();
						if (isTrace(this)) trace("Se asociará al recibo # " + recibo + "el siguiente abonoId" + abonitoId, request);
					}
				}
			}
			conn.commit();
			ok = true;
			if (isTrace(this)) trace("Se asociaron al recibo # " + recibo + "los abonos mencianados", request);
			transition("muestra", request, response);
		} catch (CustomException ce) {
			if(!ok)
				if (isTrace(this)) trace("No se pudo asociar ningún recibo", request);

			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
		} catch (DBException dbe) {
			if(!ok)
				if (isTrace(this)) trace("No se pudo asociar ningún recibo", request);

			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			if(!ok)
				if (isTrace(this)) trace("No se pudo asociar ningún recibo", request);

			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
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
			
			if(!vista.getField(DboVwDiariorecauda.CAMPO_TS_CREA).startsWith(fecfor)){
				if (isTrace(this)) trace(problemas.append(user.getUserId()).append(" trato de extornar abono # ").append(abonoId).append(" el dia ").append(fecfor).append(". Accion denegada: Fecha no permitida para extornar dicho abono.") .toString(), request);
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "Abono no disponible para ser extornado en la fecha: " + fecfor, "errorPrepago");
			}

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
		throws ControllerException {
		
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
				
			String tipo_vent = abono.getField(abono.CAMPO_TIPO_VENT);
			
			abono.setFieldsToUpdate(DboAbono.CAMPO_ESTADO);
			abono.setField(DboAbono.CAMPO_ESTADO, "0");
			abono.update();
			
			if(tipo_vent.equals(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA))
			{
				DboPagoSolicitud dboPagSol = new DboPagoSolicitud(dconn);
				dboPagSol.setField(dboPagSol.CAMPO_ABONO_ID, abono.getField(abono.CAMPO_ABONO_ID));
				if(!dboPagSol.find())
					throw new CustomException(Errors.EC_GENERIC_ERROR, "No se encontro Pago Solicitud", "errorPrepago");
				/*DboSolicitud dboSol = new DboSolicitud(dconn);
				dboSol.setFieldsToUpdate(dboSol.CAMPO_ESTADO,Constantes.ESTADO_SOL_CANCELADA);
				dboSol.setField(dboSol.CAMPO_SOLICITUD_ID,dboPagSol.getField(dboPagSol.CAMPO_SOLICITUD_ID));
				dboSol.update();
				*/
				Solicitud sol = new Solicitud(dboPagSol.getField(dboPagSol.CAMPO_SOLICITUD_ID),conn);
				if(sol.getEstado().equals(Constantes.ESTADO_SOL_POR_VERIFICAR))
				{
					sol.setUsr_modi(user.getUserId());
					sol.actualizaEstadoSol(Constantes.ESTADO_SOL_CANCELADA);
				}
				else
				{
					throw new CustomException(Errors.EC_GENERIC_ERROR, "No se puede extornar la Solicitud", "errorPrepago");
				}
			}
			
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
			else
				prepagoBean.setEsFgDeposito(false);
				
			//Tarifario
			if(!prepagoBean.getLineaPrepagoId().equals(""+Comodin.getInstance().getLineaPrePago()))
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				lineaCmd.reduceSaldo(user, prepagoBean, dconn);
			}
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
		HttpServletRequest req = null;
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		
		try{
			init(request);
			validarSesion(request);
			req = ExpressoHttpSessionBean.getRequest(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
		
			UsuarioBean user = ExpressoHttpSessionBean.getUsuarioBean(request);
					
			//Capturo variables de INPUT
			String ano = request.getParameter("ano");
			String mes = request.getParameter("mes");
			String dia = request.getParameter("dia");
			//String recibo = request.getParameter("recibo");
			//String rec = request.getParameter("rec");
			String caja = request.getParameter("cboCajas");
			String fecha_aux = null;
			String fecha_ = null;
			String fecha = null;
			int _d ;
			int _m ;
			int _a ;
			System.out.println("CAJA:::JLBP:::"+caja);
			//Recuperando la lista de cajas
			List listaCajas = Tarea.getComboCajas(dconn,user.getRegPublicoId());
			req.setAttribute("arrRepr", listaCajas);
			
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
			DiarioRecaudaDetalleBean bean = null;
			List lista = new ArrayList();
			String userCaja = null;
			DboVwDiariorecauda vista = new DboVwDiariorecauda(dconn);

			//Armamos el where
			String wherePorCajas = "";
			if(caja == null || caja.equals("T")){
				if(listaCajas.size() > 0){
					wherePorCajas = " AND " + DboVwDiariorecauda.CAMPO_USR_CAJA + " IN ('";
				}
				for(int x = 0; x < listaCajas.size(); x++){
					if(x == (listaCajas.size() - 1)){
						wherePorCajas = wherePorCajas +  ((ComboBean)listaCajas.get(x)).getCodigo() + "'";
					} else {
						wherePorCajas = wherePorCajas +  ((ComboBean)listaCajas.get(x)).getCodigo() + "', '";
					}
				}
				if(listaCajas.size() > 0){
					wherePorCajas = wherePorCajas + ")";
				}
			} else {
				vista.setField(DboVwDiariorecauda.CAMPO_USR_CAJA, caja);
			}
			
			vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_ + wherePorCajas);
				
			lista = vista.searchAndRetrieveList();

			vista.clearAll();

			double abonos =0;
			double extornos =0;
			double recaudado=0;
			
			if(caja == null || caja.equals("T")){
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_   +  wherePorCajas);//cb
				//vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "1");
				abonos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				vista.clearAll();
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_   + wherePorCajas);//cb
				vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "0");
				extornos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				recaudado = abonos - extornos;
				caja=null;
			}
			if(caja!=null) {
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_ +" AND "+DboVwDiariorecauda.CAMPO_USR_CAJA+"='"+caja+"'");//cb
				//vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "1");
				abonos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				vista.clearAll();
				vista.setAppendWhereClause(DboVwDiariorecauda.CAMPO_TS_CREA + " BETWEEN " + fecha + " AND " + fecha_   + " AND "+DboVwDiariorecauda.CAMPO_USR_CAJA+"='"+caja+"'");//cb
				vista.setField(DboVwDiariorecauda.CAMPO_ESTADO, "0");
				extornos = vista.sum(DboVwDiariorecauda.CAMPO_MONTO);
				recaudado = abonos - extornos;
			}

			//Generamos el archivo
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
