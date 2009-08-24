/*
* DboOficRegistral.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboOficRegistral extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_JURIS_ID = "JURIS_ID";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";

	public DboOficRegistral() throws DBException {
		super();
	} /* DboOficRegistral() */


	public DboOficRegistral(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboOficRegistral(DBConnection) */


	public DboOficRegistral(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* OFIC_REGISTRAL(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("OFIC_REGISTRAL");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("NOMBRE","VARCHAR", 30, false, "null");
		addField("JURIS_ID","NUMBER", 22, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, true, "null");

		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboOficRegistral();
	} /* getThisDBObj() */
} /* DboOficRegistral */

