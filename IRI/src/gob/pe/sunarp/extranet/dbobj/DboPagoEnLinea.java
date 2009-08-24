/*
* DboPagoEnLinea.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPagoEnLinea extends DBObject {

	public static final String CAMPO_PAGO_EN_LINEA_ID = "PAGO_EN_LINEA_ID";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_MOVIMIENTO_ID = "MOVIMIENTO_ID";
	public static final String CAMPO_TS_TRANSAC = "TS_TRANSAC";
	public static final String CAMPO_NUM_ITEMS = "NUM_ITEMS";
	public static final String CAMPO_MEDIO_ID = "MEDIO_ID";
	public static final String CAMPO_NRO_TERMINAL = "NRO_TERMINAL";
	public static final String CAMPO_NRO_TRANSAC = "NRO_TRANSAC";
	public static final String CAMPO_FEC_VENCIM = "FEC_VENCIM";
	public static final String CAMPO_NUM_TARJ = "NUM_TARJ";
	public static final String CAMPO_COD_VERIFIC = "COD_VERIFIC";
	public static final String CAMPO_FEC_HOR_RESP = "FEC_HOR_RESP";
	public static final String CAMPO_FEC_HOR_SOL = "FEC_HOR_SOL";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_COD_RETORNO = "COD_RETORNO";
	public static final String CAMPO_COD_ERROR = "COD_ERROR";
	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	//SAUL
	//SE COMENTA EL CAMPO GLOSA - NO SE ENCUENTRA EN LA BD DE LA MIGRACION
	//public static final String CAMPO_GLOSA = "GLOSA";

	public DboPagoEnLinea() throws DBException {
		super();
	} /* DboPagoEnLinea() */


	public DboPagoEnLinea(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPagoEnLinea(DBConnection) */


	public DboPagoEnLinea(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PAGO_EN_LINEA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PAGO_EN_LINEA");

		setDescription("Object Description Goes Here");

		addField("PAGO_EN_LINEA_ID","auto-inc", 0, false, "null");
		addField("MONTO","NUMBER", 12, true, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("MOVIMIENTO_ID","NUMBER", 22, true, "null");
		addField("TS_TRANSAC","NUMBER", 22, true, "null");
		addField("NUM_ITEMS","NUMBER", 22, true, "null");
		addField("MEDIO_ID","NUMBER", 22, false, "null");
		addField("NRO_TERMINAL","CHAR", 5, true, "null");
		addField("NRO_TRANSAC","CHAR", 5, true, "null");
		addField("FEC_VENCIM","CHAR", 6, true, "null");
		addField("NUM_TARJ","VARCHAR", 20, true, "null");
		addField("COD_VERIFIC","CHAR", 5, true, "null");
		addField("FEC_HOR_RESP","NUMBER", 22, true, "null");
		addField("FEC_HOR_SOL","NUMBER", 22, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("COD_RETORNO","CHAR", 1, true, "null");
		addField("COD_ERROR","CHAR", 3, true, "null");
		addField("PERSONA_ID","NUMBER", 22, true, "null");
		addField("SOLICITUD_ID","NUMBER", 22, true, "null");
		//addField("GLOSA","VARCHAR", 50, true, "null");

		addKey("PAGO_EN_LINEA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPagoEnLinea();
	} /* getThisDBObj() */
} /* DboPagoEnLinea */

