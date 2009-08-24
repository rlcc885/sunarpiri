/*
* DboTmPermisoAsig.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmPermisoAsig extends DBObject {

	public static final String CAMPO_PERFIL_ID_1 = "PERFIL_ID_1";
	public static final String CAMPO_PERMISO_ID = "PERMISO_ID";
	public static final String CAMPO_PERFIL_ID_2 = "PERFIL_ID_2";

	public DboTmPermisoAsig() throws DBException {
		super();
	} /* DboTmPermisoAsig() */


	public DboTmPermisoAsig(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmPermisoAsig(DBConnection) */


	public DboTmPermisoAsig(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_PERMISO_ASIG(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_PERMISO_ASIG");

		setDescription("Object Description Goes Here");

		addField("PERFIL_ID_1","CHAR", 2, false, "null");
		addField("PERMISO_ID","CHAR", 3, false, "null");
		addField("PERFIL_ID_2","CHAR", 2, false, "null");

		addKey("PERFIL_ID_1");
		addKey("PERFIL_ID_2");
		addKey("PERMISO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmPermisoAsig();
	} /* getThisDBObj() */
} /* DboTmPermisoAsig */

