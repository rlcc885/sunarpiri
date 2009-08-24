/*
* DboDereMine.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboDereMine extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NS_DERE = "NS_DERE";
	public static final String CAMPO_NO_DERE = "NO_DERE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboDereMine() throws DBException {
		super();
	} /* DboDereMine() */


	public DboDereMine(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboDereMine(DBConnection) */


	public DboDereMine(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DERE_MINE(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("DERE_MINE");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("NS_DERE","NUMBER", 5, false, "null");
		addField("NO_DERE","VARCHAR", 150, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("NS_DERE");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboDereMine();
	} /* getThisDBObj() */
} /* DboDereMine */

