/*
* DboTituSuspen.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTituSuspen extends DBObject {

	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_NS_SEQU = "NS_SEQU";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_ANO_TITU = "ANO_TITU";
	public static final String CAMPO_NUM_TITU = "NUM_TITU";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboTituSuspen() throws DBException {
		super();
	} /* DboTituSuspen() */


	public DboTituSuspen(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTituSuspen(DBConnection) */


	public DboTituSuspen(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TITU_SUSPEN(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TITU_SUSPEN");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","NUMBER", 22, false, "null");
		addField("NS_SEQU","NUMBER", 22, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("ANO_TITU","CHAR", 4, false, "null");
		addField("NUM_TITU","CHAR", 8, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("NS_SEQU");
		addKey("REFNUM_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTituSuspen();
	} /* getThisDBObj() */
} /* DboTituSuspen */

