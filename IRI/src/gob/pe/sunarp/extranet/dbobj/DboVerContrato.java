/*
* DboVerContrato.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVerContrato extends DBObject {

	public static final String CAMPO_VER_CONTRATO_ID = "VER_CONTRATO_ID";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_VER_CONTRATO = "VER_CONTRATO";

	public DboVerContrato() throws DBException {
		super();
	} /* DboVerContrato() */


	public DboVerContrato(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVerContrato(DBConnection) */


	public DboVerContrato(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VER_CONTRATO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VER_CONTRATO");

		setDescription("Object Description Goes Here");

		addField("VER_CONTRATO_ID","auto-inc", 0, false, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("VER_CONTRATO","CHAR", 10, false, "null");

		addKey("VER_CONTRATO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVerContrato();
	} /* getThisDBObj() */
} /* DboVerContrato */

