/*
* DboIndPnParticTitu.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboIndPnParticTitu extends DBObject {

	public static final String CAMPO_COD_OFIC_REG = "COD_OFIC_REG";
	public static final String CAMPO_COD_REG = "COD_REG";
	public static final String CAMPO_AA_TITU = "AA_TITU";
	public static final String CAMPO_NU_TITU = "NU_TITU";
	public static final String CAMPO_NS_AFEC = "NS_AFEC";
	public static final String CAMPO_NS_PERS_NATU = "NS_PERS_NATU";
	public static final String CAMPO_COD_ACTO_RGST = "COD_ACTO_RGST";
	public static final String CAMPO_TI_DOC_IDEN = "TI_DOC_IDEN";
	public static final String CAMPO_NU_DOC_IDEN = "NU_DOC_IDEN";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_NOMBRES = "NOMBRES";
	public static final String CAMPO_CUR = "CUR";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_SISTEMA_ID = "SISTEMA_ID";

	public DboIndPnParticTitu() throws DBException {
		super();
	} /* DboIndPnParticTitu() */


	public DboIndPnParticTitu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboIndPnParticTitu(DBConnection) */


	public DboIndPnParticTitu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* IND_PN_PARTIC_TITU(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("IND_PN_PARTIC_TITU");

		setDescription("Object Description Goes Here");

		addField("COD_OFIC_REG","CHAR", 2, false, "null");
		addField("COD_REG","CHAR", 2, false, "null");
		addField("AA_TITU","CHAR", 4, false, "null");
		addField("NU_TITU","CHAR", 8, false, "null");
		addField("NS_AFEC","NUMBER", 22, false, "null");
		addField("NS_PERS_NATU","NUMBER", 22, false, "null");
		addField("COD_ACTO_RGST","CHAR", 5, false, "null");
		addField("TI_DOC_IDEN","CHAR", 2, true, "null");
		addField("NU_DOC_IDEN","CHAR", 11, true, "null");
		addField("APE_MAT","VARCHAR", 30, true, "null");
		addField("APE_PAT","VARCHAR", 30, true, "null");
		addField("NOMBRES","VARCHAR", 40, true, "null");
		addField("CUR","CHAR", 14, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("AREA_REG_ID","CHAR", 5, true, "null");
		addField("SISTEMA_ID","CHAR", 3, false, "null");

		addKey("AA_TITU");
		addKey("COD_ACTO_RGST");
		addKey("COD_OFIC_REG");
		addKey("COD_REG");
		addKey("NS_AFEC");
		addKey("NS_PERS_NATU");
		addKey("NU_TITU");
		addKey("SISTEMA_ID");
		
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboIndPnParticTitu();
	} /* getThisDBObj() */
} /* DboIndPnParticTitu */

