/*
* DboVerificaTomoFoja.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVerificaTomoFoja extends DBObject {

	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_ID_IMG_FOLIO = "ID_IMG_FOLIO";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_FOLIO_BIS = "FOLIO_BIS";
	public static final String CAMPO_TOMO_BIS = "TOMO_BIS";
	public static final String CAMPO_NU_FOJA = "NU_FOJA";
	public static final String CAMPO_NU_TOMO = "NU_TOMO";
	public static final String CAMPO_NS_CADE = "NS_CADE";
	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_OBJETO_SOL_ID = "OBJETO_SOL_ID";

	public DboVerificaTomoFoja() throws DBException {
		super();
	} /* DboVerificaTomoFoja() */


	public DboVerificaTomoFoja(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVerificaTomoFoja(DBConnection) */


	public DboVerificaTomoFoja(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VERIFICA_TOMO_FOJA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VERIFICA_TOMO_FOJA");

		setDescription("Object Description Goes Here");

		addField("AGNT_SYNC","CHAR", 4, true, "CAMPO_AGNT_SYNC");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "CAMPO_TS_ULT_SYNC");
		addField("ID_IMG_FOLIO","NUMBER", 22, true, "CAMPO_ID_IMG_FOLIO");
		addField("ESTADO","CHAR", 1, true, "CAMPO_ESTADO");
		addField("FOLIO_BIS","CHAR", 1, true, "CAMPO_FOLIO_BIS");
		addField("TOMO_BIS","CHAR", 1, true, "CAMPO_TOMO_BIS");
		addField("NU_FOJA","CHAR", 6, false, "CAMPO_NU_FOJA");
		addField("NU_TOMO","CHAR", 6, false, "CAMPO_NU_TOMO");
		addField("NS_CADE","NUMBER", 4, false, "CAMPO_NS_CADE");
		addField("REFNUM_PART","NUMBER", 22, false, "CAMPO_REFNUM_PART");
		addField("OBJETO_SOL_ID","NUMBER", 22, false, "CAMPO_OBJETO_SOL_ID");

		addKey("NS_CADE");
		addKey("OBJETO_SOL_ID");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVerificaTomoFoja();
	} /* getThisDBObj() */
} /* DboVerificaTomoFoja */

