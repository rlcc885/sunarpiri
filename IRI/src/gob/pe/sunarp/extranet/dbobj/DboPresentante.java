/*
* DboPresentante.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPresentante extends DBObject {

	public static final String CAMPO_NUM_HOJA_PRES = "NUM_HOJA_PRES";
	public static final String CAMPO_AA_HOJA_PRES = "AA_HOJA_PRES";
	public static final String CAMPO_PRES_OFIC_REG_ID = "PRES_OFIC_REG_ID";
	public static final String CAMPO_PRES_REG_PUB_ID = "PRES_REG_PUB_ID";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_CUR_PRES = "CUR_PRES";
	public static final String CAMPO_TIPO_PER = "TIPO_PER";
	public static final String CAMPO_NOMBRES = "NOMBRES";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_AA_TITU_ANTE = "AA_TITU_ANTE";
	public static final String CAMPO_NU_TITU_ANTE = "NU_TITU_ANTE";
	public static final String CAMPO_TI_DOCU = "TI_DOCU";
	public static final String CAMPO_NU_DOC = "NU_DOC";
	public static final String CAMPO_TS_PRES = "TS_PRES";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_NOMBRE_INST = "NOMBRE_INST";	
	public static final String CAMPO_SISTEMA_ID = "SISTEMA_ID";
	
	public DboPresentante() throws DBException {
		super();
	} /* DboPresentante() */


	public DboPresentante(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPresentante(DBConnection) */


	public DboPresentante(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PRESENTANTE(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PRESENTANTE");

		setDescription("Object Description Goes Here");

		addField("NUM_HOJA_PRES","CHAR", 8, false, "null");
		addField("AA_HOJA_PRES","CHAR", 4, false, "null");
		addField("PRES_OFIC_REG_ID","CHAR", 2, false, "null");
		addField("PRES_REG_PUB_ID","CHAR", 2, false, "null");
		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("CUR_PRES","CHAR", 14, true, "null");
		addField("TIPO_PER","CHAR", 1, false, "null");
		addField("NOMBRES","VARCHAR", 40, true, "null");
		addField("APE_MAT","VARCHAR", 30, true, "null");
		addField("APE_PAT","VARCHAR", 30, true, "null");
		addField("AA_TITU_ANTE","CHAR", 4, true, "null");
		addField("NU_TITU_ANTE","CHAR", 8, true, "null");
		addField("TI_DOCU","CHAR", 2, true, "null");
		addField("NU_DOC","CHAR", 10, true, "null");
		addField("TS_PRES","NUMBER", 22, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("NOMBRE_INST","VARCHAR", 100, true, "null");
		addField("SISTEMA_ID","CHAR", 3, true, "null");
		
		addKey("AA_HOJA_PRES");
		addKey("NUM_HOJA_PRES");
		addKey("PRES_OFIC_REG_ID");
		addKey("PRES_REG_PUB_ID");
		addKey("SISTEMA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPresentante();
	} /* getThisDBObj() */
} /* DboPresentante */

