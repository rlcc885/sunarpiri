/*
* DboPrtcJur.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPrtcJur extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_CUR_PRTC = "CUR_PRTC";
	public static final String CAMPO_SIGLAS = "SIGLAS";
	public static final String CAMPO_RAZON_SOCIAL = "RAZON_SOCIAL";
	public static final String CAMPO_TI_DOC = "TI_DOC";
	public static final String CAMPO_NU_DOC = "NU_DOC";
	public static final String CAMPO_TIPO_PER_JUR = "TIPO_PER_JUR";
	public static final String CAMPO_TIPO_INST = "TIPO_INST";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboPrtcJur() throws DBException {
		super();
	} /* DboPrtcJur() */


	public DboPrtcJur(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPrtcJur(DBConnection) */


	public DboPrtcJur(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PRTC_JUR(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PRTC_JUR");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("CUR_PRTC","CHAR", 14, false, "null");
		addField("SIGLAS","VARCHAR", 50, true, "null");
		addField("RAZON_SOCIAL","VARCHAR", 250, false, "null");
		addField("TI_DOC","CHAR", 2, true, "null");
		addField("NU_DOC","VARCHAR", 11, true, "null");
		addField("TIPO_PER_JUR","CHAR", 2, true, "null");
		addField("TIPO_INST","CHAR", 2, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("CUR_PRTC");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPrtcJur();
	} /* getThisDBObj() */
} /* DboPrtcJur */

