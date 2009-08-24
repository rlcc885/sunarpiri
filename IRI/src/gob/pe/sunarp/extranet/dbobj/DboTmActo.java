/*
* DboTmActo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmActo extends DBObject {

	public static final String CAMPO_COD_ACTO = "COD_ACTO";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_COD_RUBRO = "COD_RUBRO";

	public DboTmActo() throws DBException {
		super();
	} /* DboTmActo() */


	public DboTmActo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmActo(DBConnection) */


	public DboTmActo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_ACTO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_ACTO");

		setDescription("Object Description Goes Here");

		addField("COD_ACTO","CHAR", 5, false, "null");
		addField("DESCRIPCION","VARCHAR", 80, false, "null");
		addField("COD_LIBRO","CHAR", 3, true, "null");
		addField("COD_RUBRO","CHAR", 3, true, "null");

		addKey("COD_ACTO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmActo();
	} /* getThisDBObj() */
} /* DboTmActo */

