/*
* DboTaBloqPartida.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTBloqPartida extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_NUM_PARTIDA = "NUM_PARTIDA";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_AA_HOJA_PRES = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES = "NU_HOJA_PRES";
	public static final String CAMPO_NU_FOJA = "NU_FOJA";
	public static final String CAMPO_NU_TOMO = "NU_TOMO";
	public static final String CAMPO_FICHA = "FICHA";
	public static final String CAMPO_RZ_SOC_MODI = "RZ_SOC_MODI";
	public static final String CAMPO_CO_ACTO = "CO_ACTO";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TS_USUA_CREA = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA = "ID_USUA_CREA";
	

	public DboTTBloqPartida() throws DBException {
		super();
	} /* DboTTBloqPartida() */


	public DboTTBloqPartida(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTBloqPartida(DBConnection) */


	public DboTTBloqPartida(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TT_BLOQ_PARTIDA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_BLOQ_PARTIDA");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("NUM_PARTIDA","CHAR", 8, false, "null");
		addField("COD_LIBRO","CHAR", 3, false, "null");
		addField("AA_HOJA_PRES","CHAR", 4, false, "null");
		addField("NU_HOJA_PRES","CHAR", 8, false, "null");
		addField("NU_FOJA","CHAR", 6, true, "null");
		addField("NU_TOMO","CHAR", 6, true, "null");
		addField("FICHA","CHAR", 10, true, "null");
		addField("CO_ACTO","CHAR", 5, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("TS_USUA_CREA","NUMBER", 22, true, "null");
		addField("ID_USUA_CREA","CHAR", 15, true, "null");
		addField("RZ_SOC_MODI","VARCHAR", 250, true, "null");
		

		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
		addKey("NUM_PARTIDA");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
		//addKey("AREA_REG_ID");
		//addKey("SISTEMA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTTBloqPartida();
	} /* getThisDBObj() */
} /* DboTaBloqPartida */

