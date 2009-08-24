/*
* DboContrato.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboContrato extends DBObject {

	public static final String CAMPO_CONTRATO_ID = "CONTRATO_ID";
	public static final String CAMPO_FEC_CREA = "FEC_CREA";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_VER_CONTRATO_ID = "VER_CONTRATO_ID";
	public static final String CAMPO_PE_JURI_ID = "PE_JURI_ID";

	public DboContrato() throws DBException {
		super();
	} /* DboContrato() */


	public DboContrato(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboContrato(DBConnection) */


	public DboContrato(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CONTRATO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CONTRATO");

		setDescription("Object Description Goes Here");

		addField("CONTRATO_ID","auto-inc", 0, false, "null");
		addField("FEC_CREA","NUMBER", 22, false, "null");
		addField("CUENTA_ID","NUMBER", 22, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, false, "null");
		addField("TS_CREA","NUMBER", 22, false, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("VER_CONTRATO_ID","NUMBER", 22, false, "null");
		addField("PE_JURI_ID","NUMBER", 22, true, "null");

		addKey("CONTRATO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboContrato();
	} /* getThisDBObj() */
} /* DboContrato */

