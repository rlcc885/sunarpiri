/*
* DboFicha.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboFicha extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_FICHA = "FICHA";
	public static final String CAMPO_FICHA_BIS = "FICHA_BIS";
	public static final String CAMPO_ID_IMG_FICHA = "ID_IMG_FICHA";
	public static final String CAMPO_NUMPAG = "NUMPAG";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboFicha() throws DBException {
		super();
	} /* DboFicha() */


	public DboFicha(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboFicha(DBConnection) */


	public DboFicha(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* FICHA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("FICHA");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("FICHA","CHAR", 10, false, "null");
		addField("FICHA_BIS","CHAR", 1, false, "null");
		addField("ID_IMG_FICHA","NUMBER", 22, true, "null");
		addField("NUMPAG","NUMBER", 3, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboFicha();
	} /* getThisDBObj() */
} /* DboFicha */

