/*
* DboTitulo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTitulo extends DBObject {

	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_NUM_TITU = "NUM_TITU";
	public static final String CAMPO_ANO_TITU = "ANO_TITU";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_TS_PRESENT = "TS_PRESENT";
	public static final String CAMPO_FEC_VENC = "FEC_VENC";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_NUM_HOJA_PRES = "NUM_HOJA_PRES";
	public static final String CAMPO_AA_HOJA_PRES = "AA_HOJA_PRES";
	public static final String CAMPO_PRES_OFIC_REG_ID = "PRES_OFIC_REG_ID";
	public static final String CAMPO_PRES_REG_PUB_ID = "PRES_REG_PUB_ID";
	public static final String CAMPO_SISTEMA_ID = "SISTEMA_ID";

	public DboTitulo() throws DBException {
		super();
	} /* DboTitulo() */


	public DboTitulo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTitulo(DBConnection) */


	public DboTitulo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TITULO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TITULO");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","auto-inc", 0, false, "null");
		addField("NUM_TITU","CHAR", 8, false, "null");
		addField("ANO_TITU","CHAR", 4, false, "null");
		addField("AREA_REG_ID","CHAR", 5, true, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("TS_PRESENT","NUMBER", 22, false, "null");
		addField("FEC_VENC","NUMBER", 22, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("NUM_HOJA_PRES","CHAR", 8, false, "null");
		addField("AA_HOJA_PRES","CHAR", 4, false, "null");
		addField("PRES_OFIC_REG_ID","CHAR", 2, false, "null");
		addField("PRES_REG_PUB_ID","CHAR", 2, false, "null");
		addField("SISTEMA_ID","CHAR", 3, false, "null");

		addKey("REFNUM_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTitulo();
	} /* getThisDBObj() */
} /* DboTitulo */

