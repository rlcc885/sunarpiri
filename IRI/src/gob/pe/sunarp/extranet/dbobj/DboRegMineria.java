/*
* DboRegMineria.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboRegMineria extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NOM_DER_MINE = "NOM_DER_MINE";
	public static final String CAMPO_NOM_SOCIEDAD = "NOM_SOCIEDAD";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboRegMineria() throws DBException {
		super();
	} /* DboRegMineria() */


	public DboRegMineria(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboRegMineria(DBConnection) */


	public DboRegMineria(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* REG_MINERIA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("REG_MINERIA");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("NOM_DER_MINE","VARCHAR", 100, false, "null");
		addField("NOM_SOCIEDAD","VARCHAR", 100, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboRegMineria();
	} /* getThisDBObj() */
} /* DboRegMineria */

