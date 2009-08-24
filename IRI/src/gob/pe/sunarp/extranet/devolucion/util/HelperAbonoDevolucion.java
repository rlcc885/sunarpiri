/*
 * Created on Jan 26, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.devolucion.util;

import com.jcorporate.expresso.core.db.DBConnection;

import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.dbobj.DboAbono;
import gob.pe.sunarp.extranet.dbobj.DboConsumo;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboMovimiento;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboTransaccion;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;

/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelperAbonoDevolucion {
	public static String efectuarAbonoDevolucion(UsuarioBean user,String strMonto,String ipRemota ,String sessionId,String numSol,String fechaSol,DBConnection dconn)throws Exception{
	try{
		double monto = Double.parseDouble(strMonto);
		boolean existeLinea=false;
		String lineaId="";
		double saldo=0;
		String entidad="";
		if(user.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT){
			DboPeNatu penatu= new DboPeNatu();
			penatu.setConnection(dconn);
			penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,user.getPeNatuId());
			penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
			penatu.find();
			DboPeJuri pejuri = new DboPeJuri();
			pejuri.setConnection(dconn);
			pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
			pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
			pejuri.find();
			entidad=pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC);
			DboLineaPrepago linea= new DboLineaPrepago();
			linea.setConnection(dconn);
			linea.setField(DboLineaPrepago.CAMPO_PE_JURI_ID,penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
			linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID, "is null");
			existeLinea=linea.find();
			if(existeLinea){
				String strSaldo=linea.getField(DboLineaPrepago.CAMPO_SALDO);
				saldo=Double.parseDouble(strSaldo)+monto;
				linea.setFieldsToUpdate(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_USR_ULT_MODIF+"|"+DboLineaPrepago.CAMPO_TS_ULT_MODIF);
				linea.setField(DboLineaPrepago.CAMPO_SALDO,String.valueOf(saldo));
				linea.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF,user.getUserId());
				linea.setField(DboLineaPrepago.CAMPO_TS_ULT_MODIF,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				linea.update();
				
			}
			lineaId=linea.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
					
		}else{
			DboLineaPrepago linea= new DboLineaPrepago();
			linea.setConnection(dconn);
			linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID,user.getCuentaId());
			existeLinea=linea.find();
			if(existeLinea){
				String strSaldo=linea.getField(DboLineaPrepago.CAMPO_SALDO);
				saldo=Double.parseDouble(strSaldo)+monto;
				linea.setFieldsToUpdate(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_USR_ULT_MODIF+"|"+DboLineaPrepago.CAMPO_TS_ULT_MODIF);
				linea.setField(DboLineaPrepago.CAMPO_SALDO,String.valueOf(saldo));
				linea.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF,user.getUserId());
				linea.setField(DboLineaPrepago.CAMPO_TS_ULT_MODIF,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				linea.update();
				lineaId=linea.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
				entidad=user.getUserId();
			}
		}
		if(existeLinea){
			DboMovimiento mov= new DboMovimiento();
			mov.setConnection(dconn);
			/**
			 * SVAQUEZ - AVATAR GLOBAL
			 * TABLA-MOVIENTO FALTA ACTUALIZA EL SECUENCIAL DE ESTA TABLA 
			 */
			mov.setField(DboMovimiento.CAMPO_MOVIMIENTO_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDMovimiento(dconn))));
			mov.setField(DboMovimiento.CAMPO_FG_ASIG,Constantes.TIPO_ASIGNACION_MOVIMIENTO_NORMAL);//igual al caso de abono
			mov.setField(DboMovimiento.CAMPO_TPO_MOV,Constantes.TIPO_MOVIMIENTO_ABONO);
			mov.setField(DboMovimiento.CAMPO_FEC_HOR,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			mov.setField(DboMovimiento.CAMPO_LINEA_PREPAGO_ID,lineaId);
			mov.setField(DboMovimiento.CAMPO_MONTO_FIN,String.valueOf(saldo));
			mov.add();
			//INSERT INTO ABONO (OFIC_REG_ID, ESTADO, TS_MODI, RCBO_ASOC, MONTO, USR_CAJA, MOVIMIENTO_ID, TIPO_ABONO, TS_CREA, MEDIO_ID, PERSONA_ID, TIPO_USR, TPO_PAG_VENT, TIPO_VENT, FG_CIERRE, REG_PUB_ID, ABONO_ID) VALUES ('01', '1', to_date('2007-01-26 17:46:23','yyyy-mm-dd HH24:MI:SS'), null, 100.0, 'ZRIXLIMA035', 3596442, 'V', to_date('2007-01-26 17:46:23','yyyy-mm-dd HH24:MI:SS'), null, 2587, 'I', 'E', 'A', '0', '01', 117683)
			DboTransaccion tran= new DboTransaccion();
						
			tran.setConnection(dconn);
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 * SE GENERA UN SECUECIAL PARA LA TABLA DE TRANSACCIONES
			 * DboTransaccion
			 */
			tran.setField(tran.CAMPO_TRANS_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(dconn))));		
			
			tran.setField(DboTransaccion.CAMPO_COSTO,"-"+strMonto);
			tran.setField(DboTransaccion.CAMPO_CUENTA_ID,user.getCuentaId());
			tran.setField(DboTransaccion.CAMPO_FEC_HOR,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			tran.setField(DboTransaccion.CAMPO_IP,ipRemota);
			tran.setField(DboTransaccion.CAMPO_OFIC_REG_ID,Constantes.ID_OFICINA_FUERA_DEL_PERU);
			tran.setField(DboTransaccion.CAMPO_REG_PUB_ID,Constantes.ID_ZONA_REGISTRAL_WEB);
			tran.setField(DboTransaccion.CAMPO_SERVICIO_ID,Constantes.COD_SERVICIO_PAGO_DEVOLUCION);
			tran.setField(DboTransaccion.CAMPO_SESION_ID,sessionId);
			tran.setField(DboTransaccion.CAMPO_STR_BUSQ,Constantes.STR_BUSQUEDA_PAGO_DEVOLUCION+" Nro Sol: "+numSol+" Fecha: "+fechaSol+" Monto "+strMonto );
			tran.setField(DboTransaccion.CAMPO_TIPO_USR,Constantes.TIPO_USR_INDIVIDUAL);
			tran.setField(DboTransaccion.CAMPO_COD_GRUPO_LIBRO_AREA,"0");
			tran.add();
			DboConsumo consumo= new DboConsumo();
			consumo.setConnection(dconn);
			//Inicio mgarate cambio a manejo de secuenciales por la base de datos error generico de base de datos de la aplicacion
			consumo.setField(DboConsumo.CAMPO_CONSUMO_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDConsumo(dconn))));
			//Fin mgarate
			consumo.setField(DboConsumo.CAMPO_MONTO,"-"+strMonto);
			consumo.setField(DboConsumo.CAMPO_MOVIMIENTO_ID,mov.getField(DboMovimiento.CAMPO_MOVIMIENTO_ID));
			consumo.setField(DboConsumo.CAMPO_TPO_CONSUMO,Constantes.TIPO_CONSUMO_PAGO_DEVOLUCION);
			consumo.setField(DboConsumo.CAMPO_TRANS_ID,tran.getField(DboTransaccion.CAMPO_TRANS_ID));
			consumo.setField(DboConsumo.CAMPO_TS_CREA,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			consumo.add();
				
		}
		return entidad;
	}
			
   catch (Exception e) 
   {
	   throw e;
   }
		
}

}
