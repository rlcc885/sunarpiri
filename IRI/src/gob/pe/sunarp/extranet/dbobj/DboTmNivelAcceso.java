/*
* DboTmNivelAcceso.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmNivelAcceso extends DBObject {

	public static final String CAMPO_NIVEL_ACCESO_ID = "NIVEL_ACCESO_ID";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmNivelAcceso() throws DBException {
		super();
	} /* DboTmNivelAcceso() */


	public DboTmNivelAcceso(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmNivelAcceso(DBConnection) */


	public DboTmNivelAcceso(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_NIVEL_ACCESO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_NIVEL_ACCESO");

		setDescription("Object Description Goes Here");

		addField("NIVEL_ACCESO_ID","auto-inc", 0, false, "null");
		addField("DESCRIPCION","VARCHAR", 20, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");

		addKey("NIVEL_ACCESO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmNivelAcceso();
	} /* getThisDBObj() */
} /* DboTmNivelAcceso */

