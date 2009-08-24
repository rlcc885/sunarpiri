/*
* DboRegAeronaves.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboRegAeronaves extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NUM_MATRICULA = "NUM_MATRICULA";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_NS_AERO = "NS_AERO";

	public DboRegAeronaves() throws DBException {
		super();
	} /* DboRegAeronaves() */


	public DboRegAeronaves(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboRegAeronaves(DBConnection) */


	public DboRegAeronaves(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* REG_AERONAVES(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("REG_AERONAVES");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("NUM_MATRICULA","VARCHAR", 15, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("NS_AERO","NUMBER", 3, false, "null");

		addKey("NS_AERO");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboRegAeronaves();
	} /* getThisDBObj() */
} /* DboRegAeronaves */

