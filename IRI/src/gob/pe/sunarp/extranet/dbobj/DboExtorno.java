/*
* DboExtorno.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboExtorno extends DBObject {

	public static final String CAMPO_EXTORNO_ID = "EXTORNO_ID";
	public static final String CAMPO_USR_CAJA = "USR_CAJA";
	public static final String CAMPO_USR_TESO = "USR_TESO";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_GLOSA = "GLOSA";
	public static final String CAMPO_ABONO_ID = "ABONO_ID";
	public static final String CAMPO_TS_CREA = "TS_CREA";

	public DboExtorno() throws DBException {
		super();
	} /* DboExtorno() */


	public DboExtorno(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboExtorno(DBConnection) */


	public DboExtorno(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* EXTORNO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("EXTORNO");

		setDescription("Object Description Goes Here");

		addField("EXTORNO_ID","auto-inc", 0, false, "null");
		addField("USR_CAJA","VARCHAR", 15, false, "null");
		addField("USR_TESO","VARCHAR", 15, false, "null");
		addField("MONTO","NUMBER", 12, false, "null");
		addField("GLOSA","VARCHAR", 255, false, "null");
		addField("ABONO_ID","NUMBER", 22, false, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");

		addKey("EXTORNO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboExtorno();
	} /* getThisDBObj() */
} /* DboExtorno */

