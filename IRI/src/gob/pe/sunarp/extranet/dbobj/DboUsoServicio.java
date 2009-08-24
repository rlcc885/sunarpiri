/*
* DboUsoServicio.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboUsoServicio extends DBObject {

	public static final String CAMPO_SERVICIO_ID = "SERVICIO_ID";
	public static final String CAMPO_COD_REG_PUB = "COD_REG_PUB";
	public static final String CAMPO_COD_OFIC_REG = "COD_OFIC_REG";
	public static final String CAMPO_AAAAMMDD = "AAAAMMDD";
	public static final String CAMPO_TIPO_USR = "TIPO_USR";
	public static final String CAMPO_FG_INTERNO = "FG_INTERNO";
	public static final String CAMPO_REG_PUB_ORIG = "REG_PUB_ORIG";
	public static final String CAMPO_RPN = "RPN";
	public static final String CAMPO_RPJ = "RPJ";
	public static final String CAMPO_RPI = "RPI";
	public static final String CAMPO_RBM = "RBM";
	public static final String CAMPO_VAL_TOTAL = "VAL_TOTAL";

	public DboUsoServicio() throws DBException {
		super();
	} /* DboUsoServicio() */


	public DboUsoServicio(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboUsoServicio(DBConnection) */


	public DboUsoServicio(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* USO_SERVICIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("USO_SERVICIO");

		setDescription("Object Description Goes Here");

		addField("SERVICIO_ID","NUMBER", 22, false, "null");
		addField("COD_REG_PUB","CHAR", 2, false, "null");
		addField("COD_OFIC_REG","CHAR", 2, false, "null");
		addField("AAAAMMDD","CHAR", 8, false, "null");
		addField("TIPO_USR","CHAR", 1, false, "null");
		addField("FG_INTERNO","CHAR", 1, false, "null");
		addField("REG_PUB_ORIG","CHAR", 2, false, "null");
		addField("RPN","NUMBER", 5, false, "null");
		addField("RPJ","NUMBER", 5, false, "null");
		addField("RPI","NUMBER", 5, false, "null");
		addField("RBM","NUMBER", 5, false, "null");
		addField("VAL_TOTAL","NUMBER", 22, false, "null");

		addKey("AAAAMMDD");
		addKey("COD_OFIC_REG");
		addKey("COD_REG_PUB");
		addKey("FG_INTERNO");
		addKey("REG_PUB_ORIG");
		addKey("SERVICIO_ID");
		addKey("TIPO_USR");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboUsoServicio();
	} /* getThisDBObj() */
} /* DboUsoServicio */

