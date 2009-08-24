/*
* DboTmGiro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmGiro extends DBObject {

	public static final String CAMPO_GIRO_ID = "GIRO_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmGiro() throws DBException {
		super();
	} /* DboTmGiro() */


	public DboTmGiro(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmGiro(DBConnection) */


	public DboTmGiro(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_GIRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_GIRO");

		setDescription("Object Description Goes Here");

		addField("GIRO_ID","auto-inc", 0, false, "null");
		addField("NOMBRE","CHAR", 50, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");

		addKey("GIRO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmGiro();
	} /* getThisDBObj() */
} /* DboTmGiro */

