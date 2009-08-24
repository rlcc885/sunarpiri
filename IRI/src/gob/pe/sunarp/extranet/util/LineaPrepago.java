package gob.pe.sunarp.extranet.util;

import java.sql.SQLException;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.dbobj.DboAbono;
import gob.pe.sunarp.extranet.dbobj.DboComprobante;
import gob.pe.sunarp.extranet.dbobj.DboConsumo;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboMovimiento;
import gob.pe.sunarp.extranet.dbobj.DboPagoCheque;
import gob.pe.sunarp.extranet.dbobj.DboParametros;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboPersona;
import gob.pe.sunarp.extranet.dbobj.DboTmDocIden;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;

public class LineaPrepago extends SunarpBean{

	public double getSaldoActual(String lineaPrepagoId, DBConnection conn) throws DBException {
		DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(conn);
		dboLineaPrepago.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, lineaPrepagoId);
		dboLineaPrepago.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO);
		dboLineaPrepago.find();
		return Double.parseDouble(dboLineaPrepago.getField(DboLineaPrepago.CAMPO_SALDO));
	}
	
	private long actualizaSaldo(PrepagoBean prepagoBean, String usuarioSesion, boolean primer_abono, DBConnection conn) throws CustomException, DBException, SQLException {		
		double desc = 0;
		double nuevo_saldo;

		StringBuffer cadena = new StringBuffer();
		cadena.append(DboLineaPrepago.CAMPO_SALDO).append("|").append(DboLineaPrepago.CAMPO_TS_ULT_MODIF).append("|").append(
			DboLineaPrepago.CAMPO_USR_ULT_MODIF);
			
		if(prepagoBean.getEsExtorno() && prepagoBean.getEsFgDeposito())
			cadena.append("|").append(DboLineaPrepago.CAMPO_FG_DEPOSITO);
		
	//	*** PASO 3: Actualizando el saldo en Linea Prepago - LINEA_PREPAGO
		DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(conn);
		dboLineaPrepago.setConnection(conn);
		dboLineaPrepago.clearAll();

		dboLineaPrepago.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO);
		dboLineaPrepago.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, prepagoBean.getLineaPrepagoId());
		dboLineaPrepago.find();

			// Obtengo saldo actual total del cliente obtenido de la BD
		double saldo_actual = Double.parseDouble(dboLineaPrepago.getField(DboLineaPrepago.CAMPO_SALDO));
		
	//	ES ABONO O DEBITO?
		if(prepagoBean.getAbono()){
			if(prepagoBean.getFlag_transferencia())
			//Si es Transaferencia
				nuevo_saldo = saldo_actual + prepagoBean.getMontoBruto();
			else if(!prepagoBean.getFlag_ventan())
			//	Si es en Linea
				nuevo_saldo = saldo_actual + prepagoBean.getMontoNeto();
			else
			// Si es Ventanilla
				nuevo_saldo = saldo_actual + prepagoBean.getMontoBruto();
		}
		else
		// Si solo es Debito
			nuevo_saldo = saldo_actual - prepagoBean.getMontoBruto();
		
		// Si no es abono
		
		if (!prepagoBean.getAbono())
			if (nuevo_saldo < 0){
				String mensaje = "Usted no tiene el saldo suficiente para realizar dicha transaccion";
				
				if(prepagoBean.getEsExtorno())
					mensaje = "No puede extornar dicho abono, debido a que el usuario ya lo utilizo";
			
				throw new CustomException(Constantes.E70001_SALDO_INSUFICIENTE, mensaje, "errorPrepago");
			}
			
		dboLineaPrepago.clearFieldsToRetrieve();
		dboLineaPrepago.setFieldsToUpdate(cadena.toString());
		dboLineaPrepago.setField(DboLineaPrepago.CAMPO_SALDO, Double.toString(nuevo_saldo));
		dboLineaPrepago.setField(DboLineaPrepago.CAMPO_TS_ULT_MODIF, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		dboLineaPrepago.setField(DboLineaPrepago.CAMPO_USR_ULT_MODIF, usuarioSesion);

		if(prepagoBean.getEsExtorno() && prepagoBean.getEsFgDeposito())
			dboLineaPrepago.setField(DboLineaPrepago.CAMPO_FG_DEPOSITO, "0");
			
		dboLineaPrepago.update();
	//	*** FIN PASO 3
		
	//	*** PASO 4 Insertar un registro en Tabla Movimiento
		DboMovimiento movimiento = new DboMovimiento();
		movimiento.setConnection(conn);

		movimiento.clear();
		movimiento.setField(DboMovimiento.CAMPO_MOVIMIENTO_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDMovimiento(conn))));		
		movimiento.setField(DboMovimiento.CAMPO_FEC_HOR,
			FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			
	//	ES ABONO O DEBITO?
		movimiento.setField(DboMovimiento.CAMPO_TPO_MOV, prepagoBean.getAbono()?"0":"1");
		
		movimiento.setField(DboMovimiento.CAMPO_LINEA_PREPAGO_ID, prepagoBean.getLineaPrepagoId());
		movimiento.setField(DboMovimiento.CAMPO_MONTO_FIN, Double.toString(nuevo_saldo));
		
		if(prepagoBean.getFlag_transferencia())
			movimiento.setField(DboMovimiento.CAMPO_FG_ASIG, "1");
		else
			movimiento.setField(DboMovimiento.CAMPO_FG_ASIG, "0");
			
		movimiento.add();
	//	*** FIN PASO 4
		movimiento.clearAll();
	//	DEVUELVE EL NUMERO DE MOVIMIENTO
		return (long) movimiento.max(DboMovimiento.CAMPO_MOVIMIENTO_ID);
	}


	public ComprobanteBean incrementaSaldo(UsuarioBean userBean, PrepagoBean prepagoBean, DBConnection conn) throws Exception, CustomException {
		boolean esNatural = false;
		
		if (prepagoBean.getMontoBruto() < 0)
			throw new CustomException(Errors.EC_PARAM_MISSFORMED, "El monto a abonar no puede ser negativo","errorPrepago");
		
//		if(prepagoBean.getFlag_ventan()){
		if(!prepagoBean.getFlag_transferencia()){
			DboParametros param = new DboParametros();
			param.setConnection(conn);
				
			param.setFieldsToRetrieve(DboParametros.CAMPO_VALOR);
			param.setField(DboParametros.CAMPO_COD_PRM, "MMA");
			param.find();
			if(!prepagoBean.getTipoConsAbono().equals(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA))
				if(prepagoBean.getMontoBruto() < Double.parseDouble(param.getField(DboParametros.CAMPO_VALOR)))
					throw new CustomException(Errors.EC_PARAM_MISSFORMED, "El monto a abonar debe ser mayor a " + param.getField(DboParametros.CAMPO_VALOR),"errorPrepago");
		} 
		
		prepagoBean.setAbono(true);
		
	//	*** PASO 2: Verificar si es primer abono o ampliacion de cuenta - LINEA_PREPAGO
		boolean primer_abono = false;
		
		DboLineaPrepago dboLineaPrepago = new DboLineaPrepago();
		dboLineaPrepago.setConnection(conn);
		dboLineaPrepago.setFieldsToRetrieve(DboLineaPrepago.CAMPO_FG_DEPOSITO);
		dboLineaPrepago.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, prepagoBean.getLineaPrepagoId());
		dboLineaPrepago.find();
		
		//validacion: NO se puede aumentar saldo a una linea prepago
		//            cuyo estado sea invalido
		String estadoLinea = dboLineaPrepago.getField(DboLineaPrepago.CAMPO_ESTADO);
		if (estadoLinea.equals("0")==true)
			throw new CustomException(Errors.EC_PARAM_MISSFORMED, "Saldo no puede incrementarse porque la linea se encuentra inactiva","errorPrepago");
			//throw new IllegalArgumentException("Saldo no puede incrementarse porque la linea se encuentra inactiva");
		
		if(dboLineaPrepago.getField(DboLineaPrepago.CAMPO_FG_DEPOSITO).equals("0")){
			dboLineaPrepago.setFieldsToUpdate(DboLineaPrepago.CAMPO_FG_DEPOSITO);
			dboLineaPrepago.setField(DboLineaPrepago.CAMPO_FG_DEPOSITO, "1");
			dboLineaPrepago.update();
			primer_abono = true;
		}
	//	*** FIN PASO 2
		
	// ACTUALIZAMOS SALDO
		long movId = actualizaSaldo(prepagoBean, userBean.getUserId(), primer_abono, conn);
		
	//	*** PASO 5: Insertar un registro en tabla Abono
		DboAbono abono = new DboAbono();
		abono.setConnection(conn);
		
	//	ES EN VENTANILLA O EN LINEA  - Si es linea entra como transferencia tambien
		if(prepagoBean.getFlag_ventan()){
		//	Abono en Ventanilla
			abono.setField(DboAbono.CAMPO_TIPO_ABONO,"V");
			abono.setField(DboAbono.CAMPO_MEDIO_ID, null);
			abono.setField(DboAbono.CAMPO_OFIC_REG_ID, userBean.getOficRegistralId());
			abono.setField(DboAbono.CAMPO_REG_PUB_ID, userBean.getRegPublicoId());
			abono.setField(DboAbono.CAMPO_FG_CIERRE, "0");
			
	//	ES EN EFECTIVO O CHEQUE

			if(prepagoBean.getFlag_efectivo())
				abono.setField(DboAbono.CAMPO_TPO_PAG_VENT, "E");
			else
				abono.setField(DboAbono.CAMPO_TPO_PAG_VENT, "C");

			abono.setField(DboAbono.CAMPO_USR_CAJA, userBean.getUserId());
		}else{
		//	Abono en Linea o Transferencia
			// MC: verifico si es linea o transferencia
			if(prepagoBean.getFlag_transferencia())
				abono.setField(DboAbono.CAMPO_TIPO_ABONO, "A");
			else
				abono.setField(DboAbono.CAMPO_TIPO_ABONO, "L");
			abono.setField(DboAbono.CAMPO_OFIC_REG_ID, "00");
			abono.setField(DboAbono.CAMPO_REG_PUB_ID, "00");
			abono.setField(DboAbono.CAMPO_TPO_PAG_VENT, null);
			abono.setField(DboAbono.CAMPO_USR_CAJA, userBean.getUserId());
			abono.setField(DboAbono.CAMPO_MEDIO_ID, prepagoBean.getMedioId()); // Si es Transferencia = null
			abono.setField(DboAbono.CAMPO_FG_CIERRE, null);
		}
		
	//	ES PRIMER ABONO
		if(primer_abono)
			abono.setField(DboAbono.CAMPO_TIPO_VENT, Constantes.ABONO_CONCEPTO_DEPOSITO_APERTURA);
		else
		{
			if(prepagoBean.getTipoConsAbono().equals(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA))
			{
				abono.setField(DboAbono.CAMPO_TIPO_VENT, Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA);
			}
			else
			{
				abono.setField(DboAbono.CAMPO_TIPO_VENT, Constantes.ABONO_CONCEPTO_AMPLIACION_CUENTA);
			}
		}
		
		abono.setField(DboAbono.CAMPO_ESTADO, "1");
		abono.setField(DboAbono.CAMPO_TS_MODI, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		abono.setField(DboAbono.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		abono.setField(DboAbono.CAMPO_RCBO_ASOC, null);
		
		if(prepagoBean.getFlag_transferencia())
			abono.setField(DboAbono.CAMPO_MONTO, Double.toString(prepagoBean.getMontoBruto()));
		else if(!prepagoBean.getFlag_ventan())
			abono.setField(DboAbono.CAMPO_MONTO, Double.toString(prepagoBean.getMontoNeto()));
		else
			abono.setField(DboAbono.CAMPO_MONTO, Double.toString(prepagoBean.getMontoBruto()));
			
		abono.setField(DboAbono.CAMPO_MOVIMIENTO_ID, Long.toString(movId));
		
//*******************************
			DboLineaPrepago linea = new DboLineaPrepago();
			linea.setConnection(conn);
			linea.setFieldsToRetrieve(DboLineaPrepago.CAMPO_CUENTA_ID + "|" + DboLineaPrepago.CAMPO_PE_JURI_ID + "|" + DboLineaPrepago.CAMPO_ESTADO);
			linea.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, prepagoBean.getLineaPrepagoId());
			linea.find();
			
			/** MODIFICADO JBUGARIN POR CAJAS**/
			//if(linea.getField(DboLineaPrepago.CAMPO_ESTADO).equals("0"))
			//	throw new DBException();
			/** FIN **/
			String cuentaId = linea.getField(DboLineaPrepago.CAMPO_CUENTA_ID);
			String pejuriId = linea.getField(DboLineaPrepago.CAMPO_PE_JURI_ID);
			String penatuId = null;
			
			// Para ORGANIZACION
			if(cuentaId == null || cuentaId.trim().length() <= 0){
				DboPeJuri peJuri = new DboPeJuri();
				peJuri.setConnection(conn);
				peJuri.setFieldsToRetrieve(DboPeJuri.CAMPO_PERSONA_ID);
				peJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, pejuriId);
				peJuri.find();
				abono.setField(DboAbono.CAMPO_PERSONA_ID, peJuri.getField(DboPeJuri.CAMPO_PERSONA_ID));
				abono.setField(DboAbono.CAMPO_TIPO_USR, "O");
				esNatural = false;
			} else if(pejuriId == null | pejuriId.trim().length() <= 0){
			// Para USUARIO
				DboCuenta cuentita = new DboCuenta();
				cuentita.setConnection(conn);
				cuentita.setFieldsToRetrieve(DboCuenta.CAMPO_PE_NATU_ID);
				
				DboPeNatu peNatu = new DboPeNatu();
				peNatu.setConnection(conn);
				peNatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PERSONA_ID);
				
								
				MultiDBObject multi = new MultiDBObject(conn);
				multi.setDBName("default");
				multi.addDBObj(cuentita, "cuenta");
				multi.addDBObj(peNatu, "penatu");
				multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
				multi.setField("cuenta", DboCuenta.CAMPO_CUENTA_ID, cuentaId);
				
				java.util.Vector elementos = multi.searchAndRetrieve();
			
				if(elementos.size() != 1){
					throw new DBException();
				}
				
				MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
				String persona_ID = oneMulti.getField("penatu", DboPeNatu.CAMPO_PERSONA_ID);
				
				//	usado mas abajo
				penatuId = oneMulti.getField("penatu", DboPeNatu.CAMPO_PE_NATU_ID);
						
				abono.setField(DboAbono.CAMPO_PERSONA_ID, persona_ID);
				abono.setField(DboAbono.CAMPO_TIPO_USR, "I");
				esNatural = true;
			} else if(pejuriId != null && pejuriId.trim().length() > 0 && cuentaId != null && cuentaId.trim().length() > 0){
			//	PARA USUARIO DENTRO DE UNA ORGANIZACION (15 Set.)
				DboPeJuri peJuri = new DboPeJuri();
				peJuri.setConnection(conn);
				peJuri.setFieldsToRetrieve(DboPeJuri.CAMPO_PERSONA_ID);
				peJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, pejuriId);
				peJuri.find();
				abono.setField(DboAbono.CAMPO_PERSONA_ID, peJuri.getField(DboPeJuri.CAMPO_PERSONA_ID));
				abono.setField(DboAbono.CAMPO_TIPO_USR, "O");
				esNatural = false;
			} else
				throw new DBException();

//******************************
		abono.add();
		abono.clearAll();
	//	*** FIN PASO 5
		
		// Obtengo ABONO ID
		long abonoId = (long) abono.max(DboAbono.CAMPO_ABONO_ID);
		

	//	SI FUE EL ABONO EN VENTANILLA Y EN CHEQUE
		if(prepagoBean.getFlag_ventan() && !prepagoBean.getFlag_efectivo()){
			DboPagoCheque pagoCheque = new DboPagoCheque();
			pagoCheque.setConnection(conn);
			pagoCheque.setField(DboPagoCheque.CAMPO_ABONO_ID, Long.toString(abonoId));
			pagoCheque.setField(DboPagoCheque.CAMPO_BANCO_ID, prepagoBean.getBancoId());
			pagoCheque.setField(DboPagoCheque.CAMPO_NUMERO, prepagoBean.getNumCheuqe());
			pagoCheque.setField(DboPagoCheque.CAMPO_TPO_CHEQ, prepagoBean.getTipoCheque());
			pagoCheque.add();
		}
		
	//	*** PASO 6: Insertar un registro en tabla Comprobante
		if(!prepagoBean.getFlag_transferencia()){
			DboComprobante comprobante = new DboComprobante();
			comprobante.setConnection(conn);
			comprobante.setField(DboComprobante.CAMPO_ABONO_ID, Long.toString(abonoId));
			comprobante.setField(DboComprobante.CAMPO_MONTO, Double.toString(prepagoBean.getMontoBruto()));
			comprobante.setField(DboComprobante.CAMPO_ESTADO, "1");
			comprobante.add();
		
		//	*** FIN PASO 6
	
			// Obtengo COMPROBANTE ID
			long comprobId = (long) comprobante.max(comprobante.getField(DboComprobante.CAMPO_COMPROBANTE_ID));
	
			ComprobanteBean bean = new ComprobanteBean();
			bean.setAbono_id(abonoId+"");
		//	ABONO ES POR LINEA
	//A		if(!prepagoBean.getFlag_ventan()){
				
	/*Comprobante Id*/  bean.setComprobanteId(Long.toString(comprobId));
	
						if(!prepagoBean.getFlag_ventan())
	/*Num. contrato*/		bean.setContratoId(userBean.getNum_contrato());
	
	/*Fecha Hoy*/		bean.setFecha_hora(FechaUtil.dateTimeToString(new java.sql.Timestamp(System.currentTimeMillis())));
	/*Monto*/			bean.setMonto(Double.toString(prepagoBean.getMontoBruto()));
				
				String aux = null;
				String numdoc=null;
				String doc=null;
		//	ES ORGANIZACION O NATURAL
				if(esNatural){
				// Persona Natural
					DboPeNatu pe = new DboPeNatu();
					pe.setConnection(conn);
					pe.setFieldsToRetrieve(DboPeNatu.CAMPO_NOMBRES + "|" + DboPeNatu.CAMPO_APE_PAT + "|" + DboPeNatu.CAMPO_APE_MAT+"|"+DboPeNatu.CAMPO_PERSONA_ID);
					pe.setField(DboPeNatu.CAMPO_PE_NATU_ID, penatuId);
					pe.find();
					aux = pe.getField(DboPeNatu.CAMPO_APE_PAT) + " " + pe.getField(DboPeNatu.CAMPO_APE_MAT) + " " + pe.getField(DboPeNatu.CAMPO_NOMBRES);
					/**DESCAJ IFIGUEROA 05/01/2006 INICIO**/
					DboPersona persona = new DboPersona();
					persona.setConnection(conn);
					persona.setFieldsToRetrieve(DboPersona.CAMPO_NUM_DOC_IDEN+"|"+DboPersona.CAMPO_TIPO_DOC_ID);
					persona.setField(DboPersona.CAMPO_PERSONA_ID,pe.getField(DboPersona.CAMPO_PERSONA_ID));
					persona.find();
					numdoc=persona.getField(DboPersona.CAMPO_NUM_DOC_IDEN);
					
					DboTmDocIden docIden= new DboTmDocIden();
					docIden.setConnection(conn);
					docIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV+"|"+DboTmDocIden.CAMPO_DESCRIPCION);
					docIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,persona.getField(DboPersona.CAMPO_TIPO_DOC_ID));
					docIden.find();
					doc=docIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					/**DESCAJ IFIGUEROA 05/01/2006 FIN**/
				}else{
				// Persona Juridica
					DboPeJuri pej = new DboPeJuri();
					pej.setConnection(conn);
					pej.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC+"|"+DboPeJuri.CAMPO_PERSONA_ID);
					pej.setField(DboPeJuri.CAMPO_PE_JURI_ID, pejuriId);
					pej.find();
					aux = pej.getField(DboPeJuri.CAMPO_RAZ_SOC);
					/**DESCAJ IFIGUEROA 05/01/2006 INICIO**/
					DboPersona persona = new DboPersona();
					persona.setConnection(conn);
					persona.setFieldsToRetrieve(DboPersona.CAMPO_NUM_DOC_IDEN+"|"+DboPersona.CAMPO_TIPO_DOC_ID);
					persona.setField(DboPersona.CAMPO_PERSONA_ID,pej.getField(DboPersona.CAMPO_PERSONA_ID));
					persona.find();
					numdoc=persona.getField(DboPersona.CAMPO_NUM_DOC_IDEN);

					DboTmDocIden docIden= new DboTmDocIden();
					docIden.setConnection(conn);
					docIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV+"|"+DboTmDocIden.CAMPO_DESCRIPCION);
					docIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID,persona.getField(DboPersona.CAMPO_TIPO_DOC_ID));
					docIden.find();
					doc=docIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					/**DESCAJ IFIGUEROA 05/01/2006 FIN**/
				}
	/*Nom. Entidad*/bean.setNombreEntidad(aux);
	/*User*/		bean.setUserId(prepagoBean.getUsuario());
			
			if(!prepagoBean.getFlag_ventan() || prepagoBean.getFlag_efectivo()){
				bean.setNumcheque(null);
				bean.setTipoCheque(null);
				bean.setBanco(null);
		//	ABONO ES EN VENTANILLA
			}else{
				bean.setNumcheque(prepagoBean.getNumCheuqe());
				//bean.setTipoCheque(prepagoBean.getTipoCheque());
				//bean.setBanco(prepagoBean.getBancoId());
			}
			bean.setMov_id(movId);
			/**DESCAJ IFIGUEROA 05/01/2006 INICIO**/
			bean.setDocumento(doc);
			bean.setNumeroDoc(numdoc);
			/**DESCAJ IFIGUEROA 05/01/2006 FIN**/
			return bean;
		}
		return null;
	}

	public void reduceSaldo(UsuarioBean userBean, PrepagoBean prepagoBean, DBConnection conn) throws CustomException, DBException, SQLException {		
		prepagoBean.setAbono(false);
		long movId;
		
		/*
		if (!userBean.getExonPago()){
			*/
			movId = actualizaSaldo(prepagoBean, userBean.getUserId(), false, conn);
			/*
		}
		else
		{
			return;
		}
		*/
		long transaccionId = prepagoBean.getTransacId();
		String transacId;
		
		if(transaccionId == -1)
			transacId = null;
		else
			transacId = Long.toString(transaccionId);
		
		if(!prepagoBean.getEsExtorno()){
			DboConsumo consumo = new DboConsumo();
			consumo.setConnection(conn);
			
			//Inicio mgarate cambio a manejo de secuenciales por la base de datos error generico de base de datos de la aplicacion
			consumo.setField(DboConsumo.CAMPO_CONSUMO_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDConsumo(conn))));
			//Fin mgarate
			consumo.setField(DboConsumo.CAMPO_MONTO, Double.toString(prepagoBean.getMontoBruto()));
			consumo.setField(DboConsumo.CAMPO_MOVIMIENTO_ID, Long.toString(movId));
			consumo.setField(DboConsumo.CAMPO_TRANS_ID, transacId);
			if(prepagoBean.getFlag_transferencia())
				consumo.setField(DboConsumo.CAMPO_TPO_CONSUMO, "A");
			else
				consumo.setField(DboConsumo.CAMPO_TPO_CONSUMO, "U");
			consumo.setField(DboConsumo.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			consumo.setField(DboConsumo.CAMPO_TS_MODI, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			consumo.add();
			prepagoBean.setConsumoId(consumo.getField(consumo.CAMPO_CONSUMO_ID));
		}
		if(userBean.getPerfilId()!=Constantes.PERFIL_CAJERO)
			userBean.setSaldo(getSaldoActual(prepagoBean.getLineaPrepagoId(), conn));
	}
}