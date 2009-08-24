/*
* DboIndPjParticTitu.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboIndPjParticTitu extends DBObject {

	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_NU_TITU = "NU_TITU";
	public static final String CAMPO_AA_TITU = "AA_TITU";
	public static final String CAMPO_NS_AFEC = "NS_AFEC";
	public static final String CAMPO_NS_PERS_JURI = "NS_PERS_JURI";
	public static final String CAMPO_COD_ACTO_RGST = "COD_ACTO_RGST";
	public static final String CAMPO_RAZ_SOC = "RAZ_SOC";
	public static final String CAMPO_NU_DOC_IDEN = "NU_DOC_IDEN";
	public static final String CAMPO_TI_DOC_IDEN = "TI_DOC_IDEN";
	public static final String CAMPO_SIGLAS = "SIGLAS";
	public static final String CAMPO_CUR = "CUR";
	public static final String CAMPO_TI_PERS_JURI = "TI_PERS_JURI";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_SISTEMA_ID = "SISTEMA_ID";
	
	public DboIndPjParticTitu() throws DBException {
		super();
	} /* DboIndPjParticTitu() */


	public DboIndPjParticTitu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboIndPjParticTitu(DBConnection) */


	public DboIndPjParticTitu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* IND_PJ_PARTIC_TITU(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("IND_PJ_PARTIC_TITU");

		setDescription("Object Description Goes Here");

		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("NU_TITU","CHAR", 8, false, "null");
		addField("AA_TITU","CHAR", 4, false, "null");
		addField("NS_AFEC","NUMBER", 22, false, "null");
		addField("NS_PERS_JURI","NUMBER", 22, false, "null");
		addField("COD_ACTO_RGST","CHAR", 5, false, "null");
		addField("RAZ_SOC","VARCHAR", 250, true, "null");
		addField("NU_DOC_IDEN","CHAR", 11, true, "null");
		addField("TI_DOC_IDEN","CHAR", 2, true, "null");
		addField("SIGLAS","VARCHAR", 60, true, "null");
		addField("CUR","CHAR", 14, true, "null");
		addField("TI_PERS_JURI","CHAR", 2, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("AREA_REG_ID","CHAR",5,true,"null");
		addField("SISTEMA_ID","CHAR",3,false,"null");

		addKey("AA_TITU");
		addKey("COD_ACTO_RGST");
		addKey("NS_AFEC");
		addKey("NS_PERS_JURI");
		addKey("NU_TITU");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
		addKey("SISTEMA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboIndPjParticTitu();
	} /* getThisDBObj() */
} /* DboIndPjParticTitu */

