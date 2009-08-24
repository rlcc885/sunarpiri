/*
* DboOrgCtas.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboOrgCtas extends DBObject {

	public static final String CAMPO_PE_JURI_ID = "PE_JURI_ID";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_FG_ADMIN = "FG_ADMIN";

	public DboOrgCtas() throws DBException {
		super();
	} /* DboOrgCtas() */


	public DboOrgCtas(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboOrgCtas(DBConnection) */


	public DboOrgCtas(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ORG_CTAS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("ORG_CTAS");

		setDescription("Object Description Goes Here");

		addField("PE_JURI_ID","NUMBER", 22, false, "null");
		addField("CUENTA_ID","NUMBER", 22, false, "null");
		addField("FG_ADMIN","CHAR", 1, true, "null");

		addKey("CUENTA_ID");
		addKey("PE_JURI_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboOrgCtas();
	} /* getThisDBObj() */
} /* DboOrgCtas */

