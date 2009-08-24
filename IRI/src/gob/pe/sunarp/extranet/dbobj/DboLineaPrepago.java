/*
* DboLineaPrepago.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboLineaPrepago extends DBObject {

	public static final String CAMPO_LINEA_PREPAGO_ID = "LINEA_PREPAGO_ID";
	public static final String CAMPO_SALDO = "SALDO";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_PE_JURI_ID = "PE_JURI_ID";
	public static final String CAMPO_FG_DEPOSITO = "FG_DEPOSITO";

	public DboLineaPrepago() throws DBException {
		super();
	} /* DboLineaPrepago() */


	public DboLineaPrepago(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboLineaPrepago(DBConnection) */


	public DboLineaPrepago(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* LINEA_PREPAGO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("LINEA_PREPAGO");

		setDescription("Object Description Goes Here");

		addField("LINEA_PREPAGO_ID","auto-inc", 0, false, "null");
		addField("SALDO","NUMBER", 12, false, "null");
		addField("CUENTA_ID","NUMBER", 22, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, false, "null");
		addField("USR_CREA","VARCHAR", 15, false, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("PE_JURI_ID","NUMBER", 22, true, "null");
		addField("FG_DEPOSITO","CHAR", 1, false, "null");

		addKey("LINEA_PREPAGO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboLineaPrepago();
	} /* getThisDBObj() */
} /* DboLineaPrepago */

