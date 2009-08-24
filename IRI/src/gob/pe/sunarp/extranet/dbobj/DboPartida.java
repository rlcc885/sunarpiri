/*
* DboPartida.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPartida extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NUM_PARTIDA = "NUM_PARTIDA";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_TS_INSCRIP = "TS_INSCRIP";
	public static final String CAMPO_NUM_ACUM_PAG = "NUM_ACUM_PAG";
	public static final String CAMPO_NUM_ACUM_ASIE = "NUM_ACUM_ASIE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_CO_LIBR_ORIG = "CO_LIBR_ORIG";

	public DboPartida() throws DBException {
		super();
	} /* DboPartida() */


	public DboPartida(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPartida(DBConnection) */


	public DboPartida(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PARTIDA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PARTIDA");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","auto-inc", 0, false, "null");
		addField("NUM_PARTIDA","CHAR", 8, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("COD_LIBRO","CHAR", 3, false, "null");
		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("TS_INSCRIP","NUMBER", 22, true, "null");
		addField("NUM_ACUM_PAG","NUMBER", 5, true, "null");
		addField("NUM_ACUM_ASIE","NUMBER", 5, true, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("CO_LIBR_ORIG","CHAR", 3, true, "null");

		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPartida();
	} /* getThisDBObj() */
} /* DboPartida */

