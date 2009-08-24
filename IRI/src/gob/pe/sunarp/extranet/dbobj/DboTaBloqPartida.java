/*
* DboTaBloqPartida.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTaBloqPartida extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_NUM_PARTIDA = "NUM_PARTIDA";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_ANO_TITU = "ANO_TITU";
	public static final String CAMPO_NUM_TITU = "NUM_TITU";
	public static final String CAMPO_NU_FOJA = "NU_FOJA";
	public static final String CAMPO_NU_TOMO = "NU_TOMO";
	public static final String CAMPO_FICHA = "FICHA";
	public static final String CAMPO_CO_ACTO = "CO_ACTO";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_SISTEMA_ID = "SISTEMA_ID";

	public DboTaBloqPartida() throws DBException {
		super();
	} /* DboTaBloqPartida() */


	public DboTaBloqPartida(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTaBloqPartida(DBConnection) */


	public DboTaBloqPartida(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TA_BLOQ_PARTIDA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_BLOQ_PARTIDA");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("NUM_PARTIDA","CHAR", 8, false, "null");
		addField("COD_LIBRO","CHAR", 3, false, "null");
		addField("ANO_TITU","CHAR", 4, false, "null");
		addField("NUM_TITU","CHAR", 8, false, "null");
		addField("NU_FOJA","CHAR", 6, true, "null");
		addField("NU_TOMO","CHAR", 6, true, "null");
		addField("FICHA","CHAR", 10, true, "null");
		addField("CO_ACTO","CHAR", 5, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("SISTEMA_ID","CHAR", 3, false, "null");

		addKey("ANO_TITU");
		addKey("NUM_PARTIDA");
		addKey("NUM_TITU");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
		addKey("AREA_REG_ID");
		addKey("SISTEMA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTaBloqPartida();
	} /* getThisDBObj() */
} /* DboTaBloqPartida */

