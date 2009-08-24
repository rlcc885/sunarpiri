/*
 * Created on 29/01/2007
 *
 */
package gob.pe.sunarp.extranet.devolucion.util;

import com.jcorporate.expresso.core.db.DBConnection;

import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.dbobj.DboAbono;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboMovimiento;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboTaSoliDevo;
import gob.pe.sunarp.extranet.dbobj.DboTransaccion;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;

/**
 * @author lsuarez
 *
 */
public class HelperPagarDevolucion {

	public static void pagarDevolucion(String idSolicitudDevolucion, 
											String ipRemota,
											String sessionId,
											String montoAbonoAux,
											String numSol,
											String fechaSol,
											DBConnection dconn) throws Exception {

		try{

			//Caso 1: Se ha dado de baja y es organizacion (No tiene SOLICITUD_ID y en CUENTA_ID esta la cuenta
			//del usuario que se ha dado de baja, TIPO_USR es 'O') El unico que puede hacer esto es el administrador
			//de la organizacion
			//Caso 2: Se ha dado de baja y es un usuario individual externo (No tiene SOLICITUD_ID y en CUENTA_ID esta la cuenta
			//del usuario que se ha dado de baja, TIPO_USR es 'I')
			//Caso 3: Solicitud improcedente, Organizacion (Tiene SOLICITUD_ID, y el usuario que solicito la devolucion esta en CUENTA_ID_DEV, TIPO_USR es 'O')
			//Caso 4: Solicitud improcedente, Usuario Individual (Tiene SOLICITUD_ID, y el usuario que solicito la devolucion esta en CUENTA_ID_DEV, TIPO_USR es 'I')

			String caso = null;
			String cuentaId = null;
			String idSolicitud = null;
			String montoAbono = null;
			String tipoUsuario = null;
			String oficinaRegistral = null;
			String zonaRegistral = null;
			DboTaSoliDevo dboTaSoliDevoAux = new DboTaSoliDevo(dconn);
			dboTaSoliDevoAux.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO, idSolicitudDevolucion);
			if(dboTaSoliDevoAux.find()){
				String idSolicitudAux = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_SOLICITUD_ID);
				tipoUsuario = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_TIPO_USR);
				oficinaRegistral = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_OFIC_REG_ID);
				zonaRegistral = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_REG_PUB_ID);
				if(idSolicitudAux.equals("") && tipoUsuario.equals("O")){
					caso = "1";
					cuentaId = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_CUENTA_ID);
				} else if(idSolicitudAux.equals("") && tipoUsuario.equals("I")){
					caso = "2";
					cuentaId = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_CUENTA_ID);
				} else if(!idSolicitudAux.equals("") && tipoUsuario.equals("O")){
					caso = "3";
					cuentaId = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
					idSolicitud = idSolicitudAux;
					montoAbono = montoAbonoAux;
				} else if(!idSolicitudAux.equals("") && tipoUsuario.equals("I")){
					caso = "4";
					cuentaId = dboTaSoliDevoAux.getField(DboTaSoliDevo.CAMPO_CUENTA_ID_DEV);
					idSolicitud = idSolicitudAux;
					montoAbono = montoAbonoAux;
				} else {
					throw new CustomException(Constantes.EC_GENERIC_DB_ERROR_DATA, "La data en TA_SOLI_DEV es inconsistente");
				} 
			} else {
				throw new CustomException(Constantes.EC_GENERIC_ERROR, "La solicitud de devolucion ha sido borrada");
			}

	
			//Obtenemos el usario de devolucion
			UsuarioBean user = HelperCuenta.obtenerUsuarioBean(cuentaId, idSolicitudDevolucion, dconn);

			//obtenemos el montoAbono para el caso 3 y 4
			if(caso.equals("1")){
				montoAbono = HelperCuenta.obtenerTotalOrganizacion(idSolicitudDevolucion, dconn);
			} else if(caso.equals("2")) {
				montoAbono = HelperCuenta.obtenerTotalUsuario(idSolicitudDevolucion, dconn);
			}
	
			//Actualizar la tabla TA_SOLI_DEVO
			DboTaSoliDevo dboTaSoliDevo = new DboTaSoliDevo(dconn);
			dboTaSoliDevo.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO, idSolicitudDevolucion);
			if(dboTaSoliDevo.find()){
				dboTaSoliDevo.setFieldsToUpdate(DboTaSoliDevo.CAMPO_ESTA);
				dboTaSoliDevo.setField(DboTaSoliDevo.CAMPO_ESTA, Constantes.ESTADO_SOL_DEV_PAGADO);
				dboTaSoliDevo.update();
			}
			
			//Insertar en TRANSACCION - SERVICIO_ID: Devoluciones de Pago, Grabar monto 0
			DboTransaccion tran= new DboTransaccion();
			tran.setConnection(dconn);
			/**
			 * SVASQUEZ - AVATAR GLOBAL
			 * SE GENERA UN SECUECIAL PARA LA TABLA DE TRANSACCIONES
			 * DboTransaccion
			 */
			tran.setField(tran.CAMPO_TRANS_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(dconn))));		
			
			tran.setField(DboTransaccion.CAMPO_COSTO, "0");
			tran.setField(DboTransaccion.CAMPO_CUENTA_ID, user.getCuentaId());
			tran.setField(DboTransaccion.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			tran.setField(DboTransaccion.CAMPO_IP, ipRemota);
			tran.setField(DboTransaccion.CAMPO_OFIC_REG_ID, Constantes.ID_OFICINA_FUERA_DEL_PERU);
			tran.setField(DboTransaccion.CAMPO_REG_PUB_ID, Constantes.ID_ZONA_REGISTRAL_WEB);
			tran.setField(DboTransaccion.CAMPO_SERVICIO_ID, Constantes.COD_SERVICIO_PAGO_DEVOLUCION);
			tran.setField(DboTransaccion.CAMPO_SESION_ID, sessionId);
			tran.setField(DboTransaccion.CAMPO_STR_BUSQ, Constantes.STR_BUSQUEDA_PAGO_DEVOLUCION_EN_EFECTIVO + " Nro Sol: " + numSol + " Fecha: " + fechaSol + " Monto 0.00");
			tran.setField(DboTransaccion.CAMPO_TIPO_USR, Constantes.TIPO_USR_INDIVIDUAL);
			tran.setField(DboTransaccion.CAMPO_COD_GRUPO_LIBRO_AREA, "0");
			tran.add();
	
			//Insertar en MOVIMIENTO - MONTO_FIN: monto actualizado en LINEA_PREPAGO
			String movimientoId = null;
			if(user.getPerfilId() == Constantes.PERFIL_ADMIN_ORG_EXT){
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
				DboLineaPrepago linea= new DboLineaPrepago();
				linea.setConnection(dconn);
				linea.setField(DboLineaPrepago.CAMPO_PE_JURI_ID,penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
				linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID, "is null");
				if(linea.find()){
					DboMovimiento mov= new DboMovimiento();
					mov.setConnection(dconn);
					/**
					 * SVASQUEZ - AVATAR GLOBAL 
					 * TABLA-MOVIENTO FALTA ACTUALIZA EL SECUENCIAL DE ESTA TABLA
					 */					
					mov.setField(DboMovimiento.CAMPO_MOVIMIENTO_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDMovimiento(dconn))));					
					mov.setField(DboMovimiento.CAMPO_FG_ASIG, Constantes.TIPO_ASIGNACION_MOVIMIENTO_NORMAL);//igual al caso de abono
					mov.setField(DboMovimiento.CAMPO_TPO_MOV, Constantes.TIPO_MOVIMIENTO_ABONO);
					mov.setField(DboMovimiento.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					mov.setField(DboMovimiento.CAMPO_LINEA_PREPAGO_ID, linea.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
					mov.setField(DboMovimiento.CAMPO_MONTO_FIN, linea.getField(DboLineaPrepago.CAMPO_SALDO));
					mov.add();
					movimientoId = mov.getField(DboMovimiento.CAMPO_MOVIMIENTO_ID);
				}
			} else {
				DboLineaPrepago linea= new DboLineaPrepago();
				linea.setConnection(dconn);
				linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID,user.getCuentaId());
				if(linea.find()){
					DboMovimiento mov= new DboMovimiento();
					mov.setConnection(dconn);
					/**
					 * SVASQUEZ - AVATAR GLOBAL 
					 * TABLA-MOVIENTO FALTA ACTUALIZA EL SECUENCIAL DE ESTA TABLA
					 */						
					mov.setField(DboMovimiento.CAMPO_MOVIMIENTO_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDMovimiento(dconn))));					
					mov.setField(DboMovimiento.CAMPO_FG_ASIG, Constantes.TIPO_ASIGNACION_MOVIMIENTO_NORMAL);//igual al caso de abono
					mov.setField(DboMovimiento.CAMPO_TPO_MOV, Constantes.TIPO_MOVIMIENTO_ABONO);
					mov.setField(DboMovimiento.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					mov.setField(DboMovimiento.CAMPO_LINEA_PREPAGO_ID, linea.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
					mov.setField(DboMovimiento.CAMPO_MONTO_FIN, linea.getField(DboLineaPrepago.CAMPO_SALDO));
					mov.add();
					movimientoId = mov.getField(DboMovimiento.CAMPO_MOVIMIENTO_ID);
				}
			}
	
			//Insertar en ABONO un registro con el monto negativo asociado al cajero 
			//utilizado para pagos con tarjeta de credito(validar que es el mismo usuario)
			DboAbono dboAbono = new DboAbono(dconn);
			dboAbono.setField(DboAbono.CAMPO_TIPO_ABONO, "L");
			dboAbono.setField(DboAbono.CAMPO_USR_CAJA, user.getUserId());
			dboAbono.setField(DboAbono.CAMPO_TIPO_USR, tipoUsuario);
			dboAbono.setField(DboAbono.CAMPO_MONTO, "-"+montoAbono);
			dboAbono.setField(DboAbono.CAMPO_MOVIMIENTO_ID, movimientoId);
			dboAbono.setField(DboAbono.CAMPO_OFIC_REG_ID, oficinaRegistral);
			dboAbono.setField(DboAbono.CAMPO_REG_PUB_ID, zonaRegistral);
			dboAbono.setField(DboAbono.CAMPO_PERSONA_ID, cuentaId);
			dboAbono.setField(DboAbono.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			dboAbono.setField(DboAbono.CAMPO_TS_MODI, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			dboAbono.setField(DboAbono.CAMPO_ESTADO, "1");
			dboAbono.add();
	
			System.out.println("LSD " + dboAbono.getField(DboAbono.CAMPO_ABONO_ID));
	
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(Constantes.EC_GENERIC_ERROR, "Ocurrio un error al intentar pagar la devolucion");
		}
		
	}

}
