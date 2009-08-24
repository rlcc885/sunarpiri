/*
* DboSociMine.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboSociMine extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NS_SOCI = "NS_SOCI";
	public static final String CAMPO_TI_SOCI = "TI_SOCI";
	public static final String CAMPO_NO_SOCI = "NO_SOCI";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboSociMine() throws DBException {
		super();
	} /* DboSociMine() */


	public DboSociMine(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboSociMine(DBConnection) */


	public DboSociMine(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* SOCI_MINE(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("SOCI_MINE");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("NS_SOCI","NUMBER", 5, false, "null");
		addField("TI_SOCI","CHAR", 1, false, "null");
		addField("NO_SOCI","VARCHAR", 150, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("NS_SOCI");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboSociMine();
	} /* getThisDBObj() */
} /* DboSociMine */

