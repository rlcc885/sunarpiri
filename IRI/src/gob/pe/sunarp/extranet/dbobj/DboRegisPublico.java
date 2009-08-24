/*
* DboRegisPublico.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboRegisPublico extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_SIGLAS = "SIGLAS";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";

	public DboRegisPublico() throws DBException {
		super();
	} /* DboRegisPublico() */


	public DboRegisPublico(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboRegisPublico(DBConnection) */


	public DboRegisPublico(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* REGIS_PUBLICO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("REGIS_PUBLICO");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("NOMBRE","VARCHAR", 50, false, "null");
		addField("SIGLAS","CHAR", 5, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, true, "null");

		addKey("REG_PUB_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboRegisPublico();
	} /* getThisDBObj() */
} /* DboRegisPublico */

