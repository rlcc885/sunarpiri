/*
* DboTmPregSecretas.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmPregSecretas extends DBObject {

	public static final String CAMPO_PREG_SEC_ID = "PREG_SEC_ID";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";

	public DboTmPregSecretas() throws DBException {
		super();
	} /* DboTmPregSecretas() */


	public DboTmPregSecretas(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmPregSecretas(DBConnection) */


	public DboTmPregSecretas(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_PREG_SECRETAS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_PREG_SECRETAS");

		setDescription("Object Description Goes Here");

		addField("PREG_SEC_ID","auto-inc", 0, false, "null");
		addField("DESCRIPCION","VARCHAR", 40, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, false, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");

		addKey("PREG_SEC_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmPregSecretas();
	} /* getThisDBObj() */
} /* DboTmPregSecretas */

