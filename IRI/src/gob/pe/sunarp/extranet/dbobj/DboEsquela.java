/*
* DboEsquela.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboEsquela extends DBObject {

	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_TIPO_ESQ = "TIPO_ESQ";
	public static final String CAMPO_NU_ESQUELA = "NU_ESQUELA";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_FE_EMISION = "FE_EMISION";
	public static final String CAMPO_COD_ACTO = "COD_ACTO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_CO_ACTO_RGST_ORIG = "CO_ACTO_RGST_ORIG";
	public static final String CAMPO_DATA = "DATA";

	public DboEsquela() throws DBException {
		super();
	} /* DboEsquela() */


	public DboEsquela(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboEsquela(DBConnection) */


	public DboEsquela(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ESQUELA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("ESQUELA");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","NUMBER", 22, false, "null");
		addField("TIPO_ESQ","CHAR", 1, false, "null");
		addField("NU_ESQUELA","CHAR", 8, false, "null");
		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("FE_EMISION","NUMBER", 22, false, "null");
		addField("COD_ACTO","CHAR", 5, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("CO_ACTO_RGST_ORIG","CHAR", 5, true, "null");
		addField("DATA","BLOB", 4000, true, "null");

		addKey("AREA_REG_ID");
		addKey("NU_ESQUELA");
		addKey("REFNUM_TITU");
		addKey("TIPO_ESQ");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboEsquela();
	} /* getThisDBObj() */
} /* DboEsquela */

