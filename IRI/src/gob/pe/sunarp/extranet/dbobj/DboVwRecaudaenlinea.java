/*
* DboVwRecaudaenlinea.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVwRecaudaenlinea extends DBObject {

	public static final String CAMPO_NRO_ABONO_EXTRANET = "NRO_ABONO_EXTRANET";
	public static final String CAMPO_TS_ABONO = "TS_ABONO";
	public static final String CAMPO_ESTADO_ABONO = "ESTADO_ABONO";
	public static final String CAMPO_CONCEPTO_ABONO = "CONCEPTO_ABONO";
	public static final String CAMPO_MONTO_ABONO = "MONTO_ABONO";
	public static final String CAMPO_NRO_COMPROBANTE_EXTRANET = "NRO_COMPROBANTE_EXTRANET";
	public static final String CAMPO_APE_PAT_PAGADOR = "APE_PAT_PAGADOR";
	public static final String CAMPO_APE_MAT_PAGADOR = "APE_MAT_PAGADOR";
	public static final String CAMPO_NOMBRES_PAGADOR = "NOMBRES_PAGADOR";
	public static final String CAMPO_RAZSOC_PAGADOR = "RAZSOC_PAGADOR";
	public static final String CAMPO_PERSONA_ID_PAGADOR = "PERSONA_ID_PAGADOR";
	public static final String CAMPO_NRO_PAGO_VISA = "NRO_PAGO_VISA";
	public static final String CAMPO_USR_ID_PAGO = "USR_ID_PAGO";
	public static final String CAMPO_TS_SOL_PAGO_VISA = "TS_SOL_PAGO_VISA";
	public static final String CAMPO_TS_RES_PAGO_VISA = "TS_RES_PAGO_VISA";
	public static final String CAMPO_RC_PAGO_VISA = "RC_PAGO_VISA";
	public static final String CAMPO_EC_PAGO_VISA = "EC_PAGO_VISA";
	public static final String CAMPO_ESTADO_PAGO_VISA = "ESTADO_PAGO_VISA";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";

	public DboVwRecaudaenlinea() throws DBException {
		super();
	} /* DboVwRecaudaenlinea() */


	public DboVwRecaudaenlinea(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVwRecaudaenlinea(DBConnection) */


	public DboVwRecaudaenlinea(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VW_RECAUDAENLINEA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VW_RECAUDAENLINEA");

		setDescription("Object Description Goes Here");

		addField("NRO_ABONO_EXTRANET","NUMBER", 22, true, "CAMPO_NRO_ABONO_EXTRANET");
		addField("TS_ABONO","NUMBER", 22, true, "CAMPO_TS_ABONO");
		addField("ESTADO_ABONO","CHAR", 1, true, "CAMPO_ESTADO_ABONO");
		addField("CONCEPTO_ABONO","CHAR", 1, true, "CAMPO_CONCEPTO_ABONO");
		addField("MONTO_ABONO","NUMBER", 12, true, "CAMPO_MONTO_ABONO");
		addField("NRO_COMPROBANTE_EXTRANET","NUMBER", 22, true, "CAMPO_NRO_COMPROBANTE_EXTRANET");
		addField("APE_PAT_PAGADOR","VARCHAR", 30, true, "CAMPO_APE_PAT_PAGADOR");
		addField("APE_MAT_PAGADOR","VARCHAR", 30, true, "CAMPO_APE_MAT_PAGADOR");
		addField("NOMBRES_PAGADOR","VARCHAR", 40, true, "CAMPO_NOMBRES_PAGADOR");
		addField("RAZSOC_PAGADOR","VARCHAR", 100, true, "CAMPO_RAZSOC_PAGADOR");
		addField("PERSONA_ID_PAGADOR","NUMBER", 22, true, "CAMPO_PERSONA_ID_PAGADOR");
		addField("NRO_PAGO_VISA","NUMBER", 22, false, "CAMPO_NRO_PAGO_VISA");
		addField("USR_ID_PAGO","VARCHAR", 15, true, "CAMPO_USR_ID_PAGO");
		addField("TS_SOL_PAGO_VISA","NUMBER", 22, true, "CAMPO_TS_SOL_PAGO_VISA");
		addField("TS_RES_PAGO_VISA","NUMBER", 22, true, "CAMPO_TS_RES_PAGO_VISA");
		addField("RC_PAGO_VISA","CHAR", 1, true, "CAMPO_RC_PAGO_VISA");
		addField("EC_PAGO_VISA","CHAR", 3, true, "CAMPO_EC_PAGO_VISA");
		addField("ESTADO_PAGO_VISA","CHAR", 1, false, "CAMPO_ESTADO_PAGO_VISA");
		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVwRecaudaenlinea();
	} /* getThisDBObj() */
} /* DboVwRecaudaenlinea */

