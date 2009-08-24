/*
* DboCuentaJuris.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboCuentaJuris extends DBObject {

	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_JURIS_ID = "JURIS_ID";

	public DboCuentaJuris() throws DBException {
		super();
	} /* DboCuentaJuris() */


	public DboCuentaJuris(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboCuentaJuris(DBConnection) */


	public DboCuentaJuris(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CUENTA_JURIS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CUENTA_JURIS");

		setDescription("Object Description Goes Here");

		addField("CUENTA_ID","NUMBER", 22, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("PERSONA_ID","NUMBER", 22, false, "null");
		addField("JURIS_ID","NUMBER", 22, false, "null");

		addKey("CUENTA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboCuentaJuris();
	} /* getThisDBObj() */
} /* DboCuentaJuris */

