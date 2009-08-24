/*
* DboTmBanco.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmBanco extends DBObject {

	public static final String CAMPO_BANCO_ID = "BANCO_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_ULT_MODI = "USR_ULT_MODI";
	public static final String CAMPO_TS_ULT_MODI = "TS_ULT_MODI";

	public DboTmBanco() throws DBException {
		super();
	} /* DboTmBanco() */


	public DboTmBanco(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmBanco(DBConnection) */


	public DboTmBanco(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_BANCO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_BANCO");

		setDescription("Object Description Goes Here");

		addField("BANCO_ID","auto-inc", 0, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("NOMBRE","VARCHAR", 100, false, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("USR_ULT_MODI","VARCHAR", 15, true, "null");
		addField("TS_ULT_MODI","NUMBER", 22, true, "null");

		addKey("BANCO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmBanco();
	} /* getThisDBObj() */
} /* DboTmBanco */

