/*
* DboTmGrupo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmGrupo extends DBObject {

	public static final String CAMPO_GRUPO_ID = "GRUPO_ID";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmGrupo() throws DBException {
		super();
	} /* DboTmGrupo() */


	public DboTmGrupo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmGrupo(DBConnection) */


	public DboTmGrupo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_GRUPO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_GRUPO");

		setDescription("Object Description Goes Here");

		addField("GRUPO_ID","auto-inc", 0, false, "CAMPO_GRUPO_ID");
		addField("DESCRIPCION","VARCHAR", 30, false, "CAMPO_DESCRIPCION");

		addKey("GRUPO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmGrupo();
	} /* getThisDBObj() */
} /* DboTmGrupo */

