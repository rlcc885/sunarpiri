/*
* DboPermisoPerfil.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPermisoPerfil extends DBObject {

	public static final String CAMPO_PERFIL_ID = "PERFIL_ID";
	public static final String CAMPO_PERMISO_ID = "PERMISO_ID";

	public DboPermisoPerfil() throws DBException {
		super();
	} /* DboPermisoPerfil() */


	public DboPermisoPerfil(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPermisoPerfil(DBConnection) */


	public DboPermisoPerfil(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PERMISO_PERFIL(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PERMISO_PERFIL");

		setDescription("Object Description Goes Here");

		addField("PERFIL_ID","NUMBER", 22, false, "null");
		addField("PERMISO_ID","NUMBER", 22, false, "null");

		addKey("PERFIL_ID");
		addKey("PERMISO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPermisoPerfil();
	} /* getThisDBObj() */
} /* DboPermisoPerfil */

