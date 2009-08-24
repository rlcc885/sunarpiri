/*
* DboTmError.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmError extends DBObject {

	public static final String CAMPO_ERROR_CODIGO = "ERROR_CODIGO";
	public static final String CAMPO_ERR_DESCRIPCION = "ERR_DESCRIPCION";
	public static final String CAMPO_ERR_LEVEL = "ERR_LEVEL";
	public static final String CAMPO_ERR_MAIL = "ERR_MAIL";
	public static final String CAMPO_ERR_FIELD_1 = "ERR_FIELD_1";
	public static final String CAMPO_ERR_FIELD_2 = "ERR_FIELD_2";

	public DboTmError() throws DBException {
		super();
	} /* DboTmError() */


	public DboTmError(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmError(DBConnection) */


	public DboTmError(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_ERROR(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_ERROR");

		setDescription("Object Description Goes Here");

		addField("ERROR_CODIGO","CHAR", 6, false, "null");
		addField("ERR_DESCRIPCION","VARCHAR", 126, false, "null");
		addField("ERR_LEVEL","NUMBER", 22, true, "null");
		addField("ERR_MAIL","NUMBER", 22, true, "null");
		addField("ERR_FIELD_1","VARCHAR", 254, true, "null");
		addField("ERR_FIELD_2","NUMBER", 22, true, "null");

		addKey("ERROR_CODIGO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmError();
	} /* getThisDBObj() */
} /* DboTmError */

