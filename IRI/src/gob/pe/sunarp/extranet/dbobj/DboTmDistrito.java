/*
* DboTmDistrito.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmDistrito extends DBObject {

	public static final String CAMPO_DPTO_ID = "DPTO_ID";
	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_DIST_ID = "DIST_ID";
	public static final String CAMPO_PROV_ID = "PROV_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmDistrito() throws DBException {
		super();
	} /* DboTmDistrito() */


	public DboTmDistrito(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmDistrito(DBConnection) */


	public DboTmDistrito(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_DISTRITO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_DISTRITO");

		setDescription("Object Description Goes Here");

		addField("DPTO_ID","CHAR", 2, false, "null");
		addField("PAIS_ID","CHAR", 2, false, "null");
		addField("DIST_ID","CHAR", 2, false, "null");
		addField("PROV_ID","CHAR", 2, false, "null");
		addField("NOMBRE","VARCHAR", 30, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");

		addKey("DIST_ID");
		addKey("DPTO_ID");
		addKey("PAIS_ID");
		addKey("PROV_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmDistrito();
	} /* getThisDBObj() */
} /* DboTmDistrito */

