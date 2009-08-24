/*
* DboVerificaFicha.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVerificaFicha extends DBObject {

	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_NUMPAG = "NUMPAG";
	public static final String CAMPO_ID_IMG_FICHA = "ID_IMG_FICHA";
	public static final String CAMPO_FICHA_BIS = "FICHA_BIS";
	public static final String CAMPO_FICHA = "FICHA";
	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_OBJETO_SOL_ID = "OBJETO_SOL_ID";

	public DboVerificaFicha() throws DBException {
		super();
	} /* DboVerificaFicha() */


	public DboVerificaFicha(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVerificaFicha(DBConnection) */


	public DboVerificaFicha(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VERIFICA_FICHA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VERIFICA_FICHA");

		setDescription("Object Description Goes Here");

		addField("AGNT_SYNC","CHAR", 4, true, "CAMPO_AGNT_SYNC");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "CAMPO_TS_ULT_SYNC");
		addField("NUMPAG","NUMBER", 3, true, "CAMPO_NUMPAG");
		addField("ID_IMG_FICHA","NUMBER", 22, true, "CAMPO_ID_IMG_FICHA");
		addField("FICHA_BIS","CHAR", 1, false, "CAMPO_FICHA_BIS");
		addField("FICHA","CHAR", 10, false, "CAMPO_FICHA");
		addField("REFNUM_PART","NUMBER", 22, false, "CAMPO_REFNUM_PART");
		addField("OBJETO_SOL_ID","NUMBER", 22, false, "CAMPO_OBJETO_SOL_ID");

		addKey("OBJETO_SOL_ID");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVerificaFicha();
	} /* getThisDBObj() */
} /* DboVerificaFicha */

