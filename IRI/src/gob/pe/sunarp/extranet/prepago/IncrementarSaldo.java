package gob.pe.sunarp.extranet.prepago;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboParametros;
import gob.pe.sunarp.extranet.dbobj.DboTarifa;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.IncrementarSaldoBean;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.PagoTarjeta;
import gob.pe.sunarp.extranet.util.Constantes;
import java.sql.Connection;

import com.jcorporate.expresso.core.controller.Output;
import com.jcorporate.expresso.core.db.DBConnection;

// Creado por CCS - 15/06/06

public abstract class IncrementarSaldo {

	public static IncrementarSaldoBean solicitarDatos(String sMonto, String r1, String glosa, int codServicio) throws CustomException, Throwable {
		IncrementarSaldoBean isb = new IncrementarSaldoBean();
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		// - Debe validar un monto de pago mínimo
 		Output salidaMensaje = null;
		long perfilId = -1;
		long nivelAccId = -1;
		double montoMinimo = 4.00;

		try {
			if(sMonto == null || sMonto.trim().length() <= 0)
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Ingrese un monto por favor.");

			// recupera una conexión a base de datos
			conn = pool.getConnection();
			conn.setAutoCommit(true);
			DBConnection dconn = new DBConnection(conn);

			if (codServicio == 0) {
				DboParametros param = new DboParametros();
	
				param.setConnection(dconn);
				param.setFieldsToRetrieve(DboParametros.CAMPO_VALOR);
				
				param.setField(DboParametros.CAMPO_COD_PRM, "MMA");
	
				param.find();
				montoMinimo = Double.parseDouble(param.getField(DboParametros.CAMPO_VALOR));
			}
			else if (codServicio == Constantes.COD_SERVICIO_SOLINSCR) { //revisar codigos
				DboTarifa tarifa = new DboTarifa(dconn);
				tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
				tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.COD_SERVICIO_SOLINSCR);
				//Tarifario
				tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, Constantes.COD_GLA_SOLINSCR);
			
				if(!tarifa.find())
					throw new CustomException("No existe servicio con codigo '"+ Constantes.COD_SERVICIO_SOLINSCR + "' y GLA '"+ Constantes.COD_GLA_SOLINSCR + "' en tabla TARIFA");
			
				montoMinimo = Double.parseDouble(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
			}
			else {
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "El servicio invocado no esta disponible."); //revisar codigos
			}

			try {
				double monto = Double.parseDouble(sMonto);

				if(monto<montoMinimo)
					throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Ingrese un monto mayor o igual a S/. " + montoMinimo + ".");
			}
			catch(NumberFormatException nfe) {
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Monto ingresado debe ser numérico.");
			}
			
			
			String tipoTarjeta = r1.substring(0,1);
			
			// Tarjeta Credito
			if(tipoTarjeta.equals("1")){
				Output medioId = new Output("medioId", r1.substring(1));
				isb.setMedioId(medioId);
			
				if(sMonto.indexOf(".")==-1)
					sMonto = sMonto + ".00";
				else {
					String temp = sMonto.substring(sMonto.indexOf("."),sMonto.length());
					if(temp.length()==1)
						sMonto = sMonto + "0";
					else if (temp.length()>2)
					    sMonto = sMonto.substring(0,sMonto.indexOf(".")+2);
				}
				
				Output monto = new Output("monto", sMonto);
				Output fec = new Output("fecha", FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis())));
				//Output _glosa = new Output("glosa", glosa);
			
				isb.setMonto(monto);
				isb.setFecha(fec);
				//isb.setGlosa(_glosa);
			}
			else
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Tipo de pago no soportado.");
		}
		finally{
			pool.release(conn);
		}

		return isb;
	}

	public static PagoTarjeta iniciaCredito (UsuarioBean usuarioBean, String sMonto, String medioId, String numero, String ano, String mes, String glosa) throws CustomException, Throwable {
		// PASO 3: Iniciar la transacción
 		Output salidaMensaje = null;
 		PagoTarjeta pagoT = null;
 		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			double montoMinimo = 4.00;
			// valida que se haya ingresado un monto
			if(sMonto == null || sMonto.trim().length() <= 0)
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Ingrese un monto por favor.");
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			// valida que el monto sea válido

			// Se comenta por redundante
			/*
			DboParametros param = new DboParametros();
			param.setConnection(dconn);
			param.setFieldsToRetrieve(DboParametros.CAMPO_VALOR);
			param.setField(DboParametros.CAMPO_COD_PRM, "MMA");
			param.find();
			montoMinimo = Double.parseDouble(param.getField(DboParametros.CAMPO_VALOR));

			try {
				double monto = Double.parseDouble(sMonto);
				if(monto<montoMinimo)
					throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Ingrese un monto mayor o igual a S/. " + montoMinimo + ".");
			} 
			catch(NumberFormatException nfe){
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Monto ingresado debe ser numérico.");
			}*/
			
			pagoT = new PagoTarjeta();
			pagoT.getPtbean().setMedioId(medioId);
			pagoT.getPtbean().setMonto(sMonto);
			pagoT.getPtbean().setNumItems("1");
			pagoT.getPtbean().setUserId(usuarioBean.getUserId());
			pagoT.getPtbean().setPersonaId(usuarioBean.getPersonaId());
			pagoT.getPtbean().setNumero(numero);
			pagoT.getPtbean().setAno(ano);
			pagoT.getPtbean().setMes(mes);
			//pagoT.getPtbean().setGlosa(glosa);
			pagoT.efectuaAbono(conn);
			
			// recupera los parámetros del medio de pago
			/**DboMediosPago medioPagoI = new DboMediosPago(dconn);
			medioPagoI.setField(DboMediosPago.CAMPO_MEDIO_ID, request.getParameter("medioId"));
			medioPagoI.find();
			
			DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
			long numOrden = 0;
			**/
			// Verificamos si ya se tiene un Numero de Orden en Sesion
			// Es decir si esta reintentando de nuevo realizar el pago en Linea
			/**
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
				
				if (isTrace(this)) System.out.println("Agregando nueva transacción de pago en linea.");
				pagoLineaI.clear();
				pagoLineaI.clearFieldsToRetrieve();
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_VERIFIC, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "P");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_RESP, null);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_SOL, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, null);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_VENCIM, "*");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MEDIO_ID, request.getParameter("medioId"));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MONTO, request.getParameter("monto"));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID, null);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TERMINAL, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TRANSAC, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NUM_ITEMS, "1");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NUM_TARJ, "*");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_USR_CREA, usuarioBean.getUserId());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_PERSONA_ID, usuarioBean.getPersonaId());
				pagoLineaI.add();
				numOrden = (long) pagoLineaI.max(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID);
				
				session.setAttribute("numOrden", Long.toString(numOrden));
				if (isTrace(this)) System.out.println("Generando orden: " + numOrden);
				
			StringBuffer url = new StringBuffer();
			if(gob.pe.sunarp.extranet.util.Propiedades.getInstance().getFlagProduccion())
				url.append(gob.pe.sunarp.extranet.util.DirVisa.getInstance().getDir1Prod());
			else
				url.append(gob.pe.sunarp.extranet.util.DirVisa.getInstance().getDir1Desa());
			url.append("?CODTIENDA=").append(medioPagoI.getField(DboMediosPago.CAMPO_COD_TIENDA))
				.append("&MOUNT=").append(request.getParameter("monto"))
				.append("&PAN=").append(request.getParameter("numero"));
			url.append("&EXPIRYYEAR=").append(request.getParameter("ano"))
				.append("&EXPIRYMONTH=").append(request.getParameter("mes"))
				.append("&CURRENCY=").append(medioPagoI.getField(DboMediosPago.CAMPO_MONEDA));
			url.append("&NUMORDEN=").append(numOrden)
				.append("&TIPORESP=").append(medioPagoI.getField(DboMediosPago.CAMPO_TPO_RESP));
			Output urlParams = new Output("urlParams", url.toString());
			
			**/
				
			conn.commit();
		} 
		catch(CustomException ce) {
			conn.rollback();
			throw ce;
		}
		catch(Throwable ex) {
			conn.rollback();
			throw ex;
		}
		finally {
			pool.release(conn);
		}

		return pagoT;
	}
}

