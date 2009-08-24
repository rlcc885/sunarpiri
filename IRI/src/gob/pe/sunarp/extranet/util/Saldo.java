package gob.pe.sunarp.extranet.util;


//paquetes del sistema
import java.sql.SQLException;
import java.sql.Timestamp;

import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.misc.*;

//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.framework.session.*;


public class Saldo extends SunarpBean{
	
	
		public boolean verificaSaldo(DBConnection conn, int tipoTransa, UsuarioBean usuario) throws Throwable {

		DboTarifa dbo2 = new DboTarifa(conn);
		dbo2.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dbo2.setField(dbo2.CAMPO_SERVICIO_ID, "" + tipoTransa);

		boolean b = dbo2.find();

		double costo_servicio = 0;
		if (b == true)
			costo_servicio = Double.parseDouble(dbo2.getField(dbo2.CAMPO_PREC_OFIC));

		if ((costo_servicio > 0) && (!usuario.getExonPago()) && (usuario.getSaldo() < costo_servicio))
			return false;
		else
			return true;
		}
		
		public PrepagoBean registraTransaccion(DBConnection myConn, UsuarioBean user) throws DBException, CustomException{
			
			PrepagoBean prep=new PrepagoBean();
			try {
				int transId = 0;
				transId = Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(myConn)));
				
				DboTransaccion transac = new DboTransaccion();
				transac.setConnection(myConn);
				transac.setField(transac.CAMPO_TRANS_ID, transId);
				transac.setField(transac.CAMPO_SERVICIO_ID,198);
				transac.setField(transac.CAMPO_COD_GRUPO_LIBRO_AREA, 10);
				transac.setField(transac.CAMPO_CUENTA_ID, user.getCuentaId());
				transac.setField(transac.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
				//Double costo_servicio = Double.parseDouble(deno.getMonto().toString());
				Double costo_servicio = Double.parseDouble(Tarea.getTarifa(myConn, 198).toString());
				transac.setField(transac.CAMPO_COSTO, Double.toString(costo_servicio));
				transac.setField(transac.CAMPO_IP, "LOCALHOST");
				transac.setField(transac.CAMPO_SESION_ID, "");
				transac.setField(transac.CAMPO_TIPO_USR, "1");
				transac.setField(transac.CAMPO_STR_BUSQ, "BUSQUEDA PJ NACIONAL");
				transac.setField(transac.CAMPO_REG_PUB_ID, user.getRegPublicoId());
				transac.setField(transac.CAMPO_OFIC_REG_ID, user.getOficRegistralId());
				transac.add();
				prep.setTransacId(transId);
				prep.setMontoBruto(costo_servicio);
				prep.setUsuario(user.getUserId());
				prep.setLineaPrepagoId(user.getLinPrePago());
				LineaPrepago lineaCmd = new LineaPrepago();
				lineaCmd.reduceSaldo(user, prep, myConn);
				myConn.commit();
				/*
				PrepagoBean prep = new PrepagoBean();
		prep.setTransacId(transId);
		prep.setUsuario(deno.getUsuario().getUserId());
		prep.setLineaPrepagoId(deno.getUsuario().getLinPrePago());
		prep.setMontoBruto(costo_servicio);
		
		LineaPrepago lineaCmd = new LineaPrepago();
		
		lineaCmd.reduceSaldo(deno.getUsuario(), prep, myConn);
				*/
			} catch (NumberFormatException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			return prep;
			
			}

}

