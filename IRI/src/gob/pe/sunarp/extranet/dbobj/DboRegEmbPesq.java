/*
* DboRegEmbPesq.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboRegEmbPesq extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NOMBRE_EMB = "NOMBRE_EMB";
	public static final String CAMPO_NUM_MATRICULA = "NUM_MATRICULA";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_NS_EMB = "NS_EMB";

	public DboRegEmbPesq() throws DBException {
		super();
	} /* DboRegEmbPesq() */


	public DboRegEmbPesq(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboRegEmbPesq(DBConnection) */


	public DboRegEmbPesq(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* REG_EMB_PESQ(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("REG_EMB_PESQ");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("NOMBRE_EMB","VARCHAR", 100, false, "null");
		addField("NUM_MATRICULA","VARCHAR", 15, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("NS_EMB","NUMBER", 3, false, "null");

		addKey("NS_EMB");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboRegEmbPesq();
	} /* getThisDBObj() */
} /* DboRegEmbPesq */

