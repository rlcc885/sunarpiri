/*
* DboTmPermisoExt.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmPermisoExt extends DBObject {

	public static final String CAMPO_PERMISO_ID = "PERMISO_ID";
	public static final String CAMPO_STRING_URL = "STRING_URL";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_ACL = "ACL";
	public static final String CAMPO_METODO = "METODO";

	public DboTmPermisoExt() throws DBException {
		super();
	} /* DboTmPermisoExt() */


	public DboTmPermisoExt(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmPermisoExt(DBConnection) */


	public DboTmPermisoExt(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_PERMISO_EXT(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_PERMISO_EXT");

		setDescription("Object Description Goes Here");

		addField("PERMISO_ID","NUMBER", 22, false, "null");
		addField("STRING_URL","VARCHAR", 100, false, "null");
		addField("NOMBRE","VARCHAR", 80, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("ACL","VARCHAR", 20, false, "null");
		addField("METODO","VARCHAR", 40, true, "null");

		addKey("PERMISO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmPermisoExt();
	} /* getThisDBObj() */
} /* DboTmPermisoExt */

