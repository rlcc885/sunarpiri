/*
* DboTmPerfilAsignac.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmPerfilAsignac extends DBObject {

	public static final String CAMPO_PERFIL_ID_1 = "PERFIL_ID_1";
	public static final String CAMPO_PERFIL_ID_2 = "PERFIL_ID_2";

	public DboTmPerfilAsignac() throws DBException {
		super();
	} /* DboTmPerfilAsignac() */


	public DboTmPerfilAsignac(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmPerfilAsignac(DBConnection) */


	public DboTmPerfilAsignac(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_PERFIL_ASIGNAC(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_PERFIL_ASIGNAC");

		setDescription("Object Description Goes Here");

		addField("PERFIL_ID_1","CHAR", 2, false, "null");
		addField("PERFIL_ID_2","CHAR", 2, false, "null");

		addKey("PERFIL_ID_1");
		addKey("PERFIL_ID_2");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmPerfilAsignac();
	} /* getThisDBObj() */
} /* DboTmPerfilAsignac */

