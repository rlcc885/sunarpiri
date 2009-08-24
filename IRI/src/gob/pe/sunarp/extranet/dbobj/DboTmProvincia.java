/*
* DboTmProvincia.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmProvincia extends DBObject {

	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_DPTO_ID = "DPTO_ID";
	public static final String CAMPO_PROV_ID = "PROV_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";

	public DboTmProvincia() throws DBException {
		super();
	} /* DboTmProvincia() */


	public DboTmProvincia(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmProvincia(DBConnection) */


	public DboTmProvincia(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_PROVINCIA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_PROVINCIA");

		setDescription("Object Description Goes Here");

		addField("PAIS_ID","CHAR", 2, false, "null");
		addField("DPTO_ID","CHAR", 2, false, "null");
		addField("PROV_ID","CHAR", 2, false, "null");
		addField("NOMBRE","VARCHAR", 40, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");

		addKey("DPTO_ID");
		addKey("PAIS_ID");
		addKey("PROV_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmProvincia();
	} /* getThisDBObj() */
} /* DboTmProvincia */

