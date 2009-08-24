/*
* DboTomoFolio.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTomoFolio extends DBObject {

	public static final String CAMPO_NU_TOMO = "NU_TOMO";
	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NU_FOJA = "NU_FOJA";
	public static final String CAMPO_TOMO_BIS = "TOMO_BIS";
	public static final String CAMPO_FOLIO_BIS = "FOLIO_BIS";
	public static final String CAMPO_NS_CADE = "NS_CADE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_ID_IMG_FOLIO = "ID_IMG_FOLIO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboTomoFolio() throws DBException {
		super();
	} /* DboTomoFolio() */


	public DboTomoFolio(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTomoFolio(DBConnection) */


	public DboTomoFolio(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TOMO_FOLIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TOMO_FOLIO");

		setDescription("Object Description Goes Here");

		addField("NU_TOMO","CHAR", 6, false, "null");
		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("NU_FOJA","CHAR", 6, false, "null");
		addField("TOMO_BIS","CHAR", 1, true, "null");
		addField("FOLIO_BIS","CHAR", 1, true, "null");
		addField("NS_CADE","NUMBER", 4, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("ID_IMG_FOLIO","NUMBER", 22, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("NS_CADE");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTomoFolio();
	} /* getThisDBObj() */
} /* DboTomoFolio */

