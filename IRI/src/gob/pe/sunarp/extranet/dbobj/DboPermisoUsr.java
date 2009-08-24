/*
* DboPermisoUsr.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPermisoUsr extends DBObject {

	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_PERMISO_ID = "PERMISO_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_FG_SYNC_TAM = "FG_SYNC_TAM";

	public DboPermisoUsr() throws DBException {
		super();
	} /* DboPermisoUsr() */


	public DboPermisoUsr(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPermisoUsr(DBConnection) */


	public DboPermisoUsr(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PERMISO_USR(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PERMISO_USR");

		setDescription("Object Description Goes Here");

		addField("CUENTA_ID","NUMBER", 22, false, "null");
		addField("PERMISO_ID","NUMBER", 22, false, "null");
		addField("ESTADO","VARCHAR", 20, false, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, false, "null");
		addField("USR_CREA","VARCHAR", 15, false, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, false, "null");
		addField("TS_CREA","NUMBER", 22, false, "null");
		addField("FG_SYNC_TAM","CHAR", 1, false, "null");

		addKey("CUENTA_ID");
		addKey("PERMISO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPermisoUsr();
	} /* getThisDBObj() */
} /* DboPermisoUsr */

