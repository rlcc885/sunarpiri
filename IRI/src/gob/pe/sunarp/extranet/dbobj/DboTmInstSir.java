/*
* DboTmInstSir.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmInstSir extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_CUR_PRES = "CUR_PRES";
	public static final String CAMPO_NOMBRE_INST = "NOMBRE_INST";
	public static final String CAMPO_SIGLAS = "SIGLAS";
	public static final String CAMPO_PE_JURI_ID = "PE_JURI_ID";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboTmInstSir() throws DBException {
		super();
	} /* DboTmInstSir() */


	public DboTmInstSir(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmInstSir(DBConnection) */


	public DboTmInstSir(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_INST_SIR(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_INST_SIR");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("CUR_PRES","CHAR", 14, false, "null");
		addField("NOMBRE_INST","VARCHAR", 50, false, "null");
		addField("SIGLAS","CHAR", 20, true, "null");
		addField("PE_JURI_ID","NUMBER", 22, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("CUR_PRES");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmInstSir();
	} /* getThisDBObj() */
} /* DboTmInstSir */

