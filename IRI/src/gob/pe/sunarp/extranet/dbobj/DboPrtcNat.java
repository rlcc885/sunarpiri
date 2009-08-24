/*
* DboPrtcNat.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPrtcNat extends DBObject {

	public static final String CAMPO_CUR_PRTC = "CUR_PRTC";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_NOMBRES = "NOMBRES";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_NU_DOC_IDEN = "NU_DOC_IDEN";
	public static final String CAMPO_TI_DOC_IDEN = "TI_DOC_IDEN";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboPrtcNat() throws DBException {
		super();
	} /* DboPrtcNat() */


	public DboPrtcNat(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPrtcNat(DBConnection) */


	public DboPrtcNat(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PRTC_NAT(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PRTC_NAT");

		setDescription("Object Description Goes Here");

		addField("CUR_PRTC","CHAR", 14, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("NOMBRES","VARCHAR", 40, true, "null");
		addField("APE_MAT","VARCHAR", 30, true, "null");
		addField("APE_PAT","VARCHAR", 30, false, "null");
		addField("NU_DOC_IDEN","CHAR", 11, true, "null");
		addField("TI_DOC_IDEN","CHAR", 2, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("CUR_PRTC");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPrtcNat();
	} /* getThisDBObj() */
} /* DboPrtcNat */

