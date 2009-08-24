/*
* DboIndPrtc.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboIndPrtc extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_COD_PARTIC = "COD_PARTIC";
	public static final String CAMPO_CUR_PRTC = "CUR_PRTC";
	public static final String CAMPO_TIPO_PERS = "TIPO_PERS";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_AA_TITU = "AA_TITU";
	public static final String CAMPO_NU_TITU = "NU_TITU";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_TI_PRTC_ORIG = "TI_PRTC_ORIG";

	public DboIndPrtc() throws DBException {
		super();
	} /* DboIndPrtc() */


	public DboIndPrtc(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboIndPrtc(DBConnection) */


	public DboIndPrtc(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* IND_PRTC(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("IND_PRTC");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("COD_PARTIC","CHAR", 3, false, "null");
		addField("CUR_PRTC","CHAR", 14, false, "null");
		addField("TIPO_PERS","CHAR", 1, true, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("AA_TITU","CHAR", 4, true, "null");
		addField("NU_TITU","CHAR", 8, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("TI_PRTC_ORIG","CHAR", 4, true, "null");

		addKey("COD_PARTIC");
		addKey("CUR_PRTC");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboIndPrtc();
	} /* getThisDBObj() */
} /* DboIndPrtc */

