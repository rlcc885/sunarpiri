/*
* DboTmJurisdiccion.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmJurisdiccion extends DBObject {

	public static final String CAMPO_JURIS_ID = "JURIS_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmJurisdiccion() throws DBException {
		super();
	} /* DboTmJurisdiccion() */


	public DboTmJurisdiccion(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmJurisdiccion(DBConnection) */


	public DboTmJurisdiccion(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_JURISDICCION(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_JURISDICCION");

		setDescription("Object Description Goes Here");

		addField("JURIS_ID","auto-inc", 0, false, "null");
		addField("NOMBRE","VARCHAR", 50, false, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");

		addKey("JURIS_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmJurisdiccion();
	} /* getThisDBObj() */
} /* DboTmJurisdiccion */

