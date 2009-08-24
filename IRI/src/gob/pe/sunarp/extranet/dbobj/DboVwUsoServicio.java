/*
* DboVwUsoServicio.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVwUsoServicio extends DBObject {

	public static final String CAMPO_REGPUB = "REGPUB";
	public static final String CAMPO_OFIREG = "OFIREG";
	public static final String CAMPO_RPNOM = "RPNOM";
	public static final String CAMPO_OFRNOM = "OFRNOM";
	public static final String CAMPO_CO_SRV = "CO_SRV";
	public static final String CAMPO_NOMB = "NOMB";
	public static final String CAMPO_TIPO_USR = "TIPO_USR";
	public static final String CAMPO_FG_INTERNO = "FG_INTERNO";
	public static final String CAMPO_AAAAMMDD = "AAAAMMDD";
	public static final String CAMPO_RPN = "RPN";
	public static final String CAMPO_RPJ = "RPJ";
	public static final String CAMPO_RPI = "RPI";
	public static final String CAMPO_TUSR = "TUSR";

	public DboVwUsoServicio() throws DBException {
		super();
	} /* DboVwUsoServicio() */


	public DboVwUsoServicio(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVwUsoServicio(DBConnection) */


	public DboVwUsoServicio(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VW_USO_SERVICIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VW_USO_SERVICIO");

		setDescription("Object Description Goes Here");

		addField("REGPUB","CHAR", 2, false, "null");
		addField("OFIREG","CHAR", 2, false, "null");
		addField("RPNOM","VARCHAR", 50, false, "null");
		addField("OFRNOM","VARCHAR", 30, false, "null");
		addField("CO_SRV","NUMBER", 22, false, "null");
		addField("NOMB","VARCHAR", 50, false, "null");
		addField("TIPO_USR","CHAR", 1, false, "null");
		addField("FG_INTERNO","CHAR", 1, false, "null");
		addField("AAAAMMDD","CHAR", 8, false, "null");
		addField("RPN","NUMBER", 22, true, "null");
		addField("RPJ","NUMBER", 22, true, "null");
		addField("RPI","NUMBER", 22, true, "null");
		addField("TUSR","CHAR", 1, false, "null");

	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVwUsoServicio();
	} /* getThisDBObj() */
} /* DboVwUsoServicio */

