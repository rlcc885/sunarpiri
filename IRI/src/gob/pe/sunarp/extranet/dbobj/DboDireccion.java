/*
* DboDireccion.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboDireccion extends DBObject {

	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_DPTO_ID = "DPTO_ID";
	public static final String CAMPO_PROV_ID = "PROV_ID";
	public static final String CAMPO_LUG_EXT = "LUG_EXT";
	public static final String CAMPO_NOM_NUM_VIA = "NOM_NUM_VIA";
	public static final String CAMPO_COD_POST = "COD_POST";
	public static final String CAMPO_NO_DIST = "NO_DIST";

	public DboDireccion() throws DBException {
		super();
	} /* DboDireccion() */


	public DboDireccion(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboDireccion(DBConnection) */


	public DboDireccion(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DIRECCION(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("DIRECCION");

		setDescription("Object Description Goes Here");

		addField("PERSONA_ID","NUMBER", 22, false, "null");
		addField("PAIS_ID","CHAR", 2, false, "null");
		addField("DPTO_ID","CHAR", 2, true, "null");
		addField("PROV_ID","CHAR", 2, true, "null");
		addField("LUG_EXT","VARCHAR", 30, true, "null");
		addField("NOM_NUM_VIA","VARCHAR", 40, false, "null");
		addField("COD_POST","CHAR", 12, true, "null");
		addField("NO_DIST","VARCHAR", 40, true, "null");

		addKey("PERSONA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboDireccion();
	} /* getThisDBObj() */
} /* DboDireccion */

