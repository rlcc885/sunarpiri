/*
* DboRegPredios.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboRegPredios extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_TIPO_VIA = "TIPO_VIA";
	public static final String CAMPO_PROV_ID = "PROV_ID";
	public static final String CAMPO_NO_ZONA = "NO_ZONA";
	public static final String CAMPO_NU_INMB = "NU_INMB";
	public static final String CAMPO_NUM_INTER = "NUM_INTER";
	public static final String CAMPO_NO_VIA = "NO_VIA";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TIPO_INTER = "TIPO_INTER";
	public static final String CAMPO_TIPO_NUMER = "TIPO_NUMER";
	public static final String CAMPO_TIPO_ZONA = "TIPO_ZONA";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_NS_PRED = "NS_PRED";
	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_DPTO_ID = "DPTO_ID";
	public static final String CAMPO_DIST_ID = "DIST_ID";

	public DboRegPredios() throws DBException {
		super();
	} /* DboRegPredios() */


	public DboRegPredios(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboRegPredios(DBConnection) */


	public DboRegPredios(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* REG_PREDIOS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("REG_PREDIOS");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("TIPO_VIA","CHAR", 2, false, "null");
		addField("PROV_ID","CHAR", 2, false, "null");
		addField("NO_ZONA","VARCHAR", 100, false, "null");
		addField("NU_INMB","VARCHAR", 100, false, "null");
		addField("NUM_INTER","VARCHAR", 100, true, "null");
		addField("NO_VIA","VARCHAR", 100, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("TIPO_INTER","CHAR", 2, false, "null");
		addField("TIPO_NUMER","CHAR", 2, false, "null");
		addField("TIPO_ZONA","CHAR", 2, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("NS_PRED","NUMBER", 5, false, "null");
		addField("PAIS_ID","CHAR", 2, false, "null");
		addField("DPTO_ID","CHAR", 2, false, "null");
		addField("DIST_ID","CHAR", 2, false, "null");

		addKey("NS_PRED");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboRegPredios();
	} /* getThisDBObj() */
} /* DboRegPredios */

