/*
* DboVerificaTituPend.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVerificaTituPend extends DBObject {

	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_CO_ACTO = "CO_ACTO";
	public static final String CAMPO_NU_FOJA = "NU_FOJA";
	public static final String CAMPO_NU_TOMO = "NU_TOMO";
	public static final String CAMPO_FICHA = "FICHA";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_SISTEMA_ID = "SISTEMA_ID";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_NUM_TITU = "NUM_TITU";
	public static final String CAMPO_ANO_TITU = "ANO_TITU";
	public static final String CAMPO_NUM_PARTIDA = "NUM_PARTIDA";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OBJETO_SOL_ID = "OBJETO_SOL_ID";

	public DboVerificaTituPend() throws DBException {
		super();
	} /* DboVerificaTituPend() */


	public DboVerificaTituPend(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVerificaTituPend(DBConnection) */


	public DboVerificaTituPend(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VERIFICA_TITU_PEND(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VERIFICA_TITU_PEND");

		setDescription("Object Description Goes Here");

		addField("AGNT_SYNC","CHAR", 4, true, "CAMPO_AGNT_SYNC");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "CAMPO_TS_ULT_SYNC");
		addField("ESTADO","CHAR", 1, true, "CAMPO_ESTADO");
		addField("CO_ACTO","CHAR", 5, true, "CAMPO_CO_ACTO");
		addField("NU_FOJA","CHAR", 6, true, "CAMPO_NU_FOJA");
		addField("NU_TOMO","CHAR", 6, true, "CAMPO_NU_TOMO");
		addField("FICHA","CHAR", 10, true, "CAMPO_FICHA");
		addField("COD_LIBRO","CHAR", 3, true, "CAMPO_COD_LIBRO");
		addField("SISTEMA_ID","CHAR", 3, false, "CAMPO_SISTEMA_ID");
		addField("AREA_REG_ID","CHAR", 5, false, "CAMPO_AREA_REG_ID");
		addField("NUM_TITU","CHAR", 8, false, "CAMPO_NUM_TITU");
		addField("ANO_TITU","CHAR", 4, false, "CAMPO_ANO_TITU");
		addField("NUM_PARTIDA","CHAR", 8, false, "CAMPO_NUM_PARTIDA");
		addField("OFIC_REG_ID","CHAR", 2, false, "CAMPO_OFIC_REG_ID");
		addField("REG_PUB_ID","CHAR", 2, false, "CAMPO_REG_PUB_ID");
		addField("OBJETO_SOL_ID","NUMBER", 22, false, "CAMPO_OBJETO_SOL_ID");

		addKey("ANO_TITU");
		addKey("AREA_REG_ID");
		addKey("NUM_PARTIDA");
		addKey("NUM_TITU");
		addKey("OBJETO_SOL_ID");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
		addKey("SISTEMA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVerificaTituPend();
	} /* getThisDBObj() */
} /* DboVerificaTituPend */

