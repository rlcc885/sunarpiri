/*
* DboParametros.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboParametros extends DBObject {

	public static final String CAMPO_PARAM_ID = "PARAM_ID";
	public static final String CAMPO_COD_PRM = "COD_PRM";
	public static final String CAMPO_USUA_MODI = "USUA_MODI";
	public static final String CAMPO_USUA_CREA = "USUA_CREA";
	public static final String CAMPO_TS_MODI = "TS_MODI";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_VALOR = "VALOR";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboParametros() throws DBException {
		super();
	} /* DboParametros() */


	public DboParametros(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboParametros(DBConnection) */


	public DboParametros(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PARAMETROS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PARAMETROS");

		setDescription("Object Description Goes Here");

		addField("PARAM_ID","auto-inc", 0, false, "null");
		addField("COD_PRM","CHAR", 3, false, "null");
		addField("USUA_MODI","VARCHAR", 15, false, "null");
		addField("USUA_CREA","VARCHAR", 15, false, "null");
		addField("TS_MODI","NUMBER", 22, false, "null");
		addField("TS_CREA","NUMBER", 22, false, "null");
		addField("VALOR","VARCHAR", 80, false, "null");
		addField("DESCRIPCION","VARCHAR", 50, true, "null");

		addKey("PARAM_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboParametros();
	} /* getThisDBObj() */
} /* DboParametros */

