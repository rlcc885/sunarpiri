/*
* DboTmServicio.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmServicio extends DBObject {

	public static final String CAMPO_SERVICIO_ID = "SERVICIO_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_FG_MULT = "FG_MULT";

	public DboTmServicio() throws DBException {
		super();
	} /* DboTmServicio() */


	public DboTmServicio(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmServicio(DBConnection) */


	public DboTmServicio(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_SERVICIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_SERVICIO");

		setDescription("Object Description Goes Here");

		addField("SERVICIO_ID","auto-inc", 0, false, "null");
		addField("NOMBRE","VARCHAR", 50, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("FG_MULT","CHAR", 1, true, "null");

		addKey("SERVICIO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmServicio();
	} /* getThisDBObj() */
} /* DboTmServicio */

