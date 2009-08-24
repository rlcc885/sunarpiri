/*
* DboAsiento.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAsiento extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NS_ASIENTO = "NS_ASIENTO";
	public static final String CAMPO_COD_ACTO = "COD_ACTO";
	public static final String CAMPO_TS_INSCRIP = "TS_INSCRIP";
	public static final String CAMPO_COD_RUBRO = "COD_RUBRO";
	public static final String CAMPO_ES_ASIENTO = "ES_ASIENTO";
	public static final String CAMPO_NUM_TITU = "NUM_TITU";
	public static final String CAMPO_AA_TITU = "AA_TITU";
	public static final String CAMPO_ID_IMG_ASIENTO = "ID_IMG_ASIENTO";
	public static final String CAMPO_NUMPAG = "NUMPAG";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_CO_ACTO_RGST_ORIG = "CO_ACTO_RGST_ORIG";
	public static final String CAMPO_CO_RUBR_ORIG = "CO_RUBR_ORIG";
	public static final String CAMPO_LETRA_RUBRO = "LETRA_RUBRO";
	public static final String CAMPO_NUM_PLACA = "NUM_PLACA";
	public static final String CAMPO_NS_ASIE_PLACA = "NS_ASIE_PLACA";
	//SAUL - COMENTA LOS CAMPOS: 
	// - CAMPOS RECIBIDOS
	// - CAMPO_ID_USUA_CREA
	//modificado jbugarin anotacion de inscripcion 
	//public static final String CAMPO_RECIBOS = "RECIBOS";
	//public static final String CAMPO_ID_USUA_CREA = "ID_USUA_CREA";

	public DboAsiento() throws DBException {
		super();
	} /* DboAsiento() */


	public DboAsiento(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAsiento(DBConnection) */


	public DboAsiento(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ASIENTO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("ASIENTO");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "CAMPO_REFNUM_PART");
		addField("NS_ASIENTO","NUMBER", 5, false, "CAMPO_NS_ASIENTO");
		addField("COD_ACTO","CHAR", 5, false, "CAMPO_COD_ACTO");
		addField("TS_INSCRIP","DATE", 7, true, "CAMPO_TS_INSCRIP");
		addField("COD_RUBRO","CHAR", 3, false, "CAMPO_COD_RUBRO");
		addField("ES_ASIENTO","CHAR", 1, false, "CAMPO_ES_ASIENTO");
		addField("NUM_TITU","CHAR", 8, true, "CAMPO_NUM_TITU");
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("ID_IMG_ASIENTO","NUMBER", 22, true, "CAMPO_ID_IMG_ASIENTO");
		addField("NUMPAG","NUMBER", 3, true, "CAMPO_NUMPAG");
		addField("TS_ULT_SYNC","DATE", 7, true, "CAMPO_TS_ULT_SYNC");
		addField("AGNT_SYNC","CHAR", 4, true, "CAMPO_AGNT_SYNC");
		addField("CO_ACTO_RGST_ORIG","CHAR", 5, true, "CAMPO_CO_ACTO_RGST_ORIG");
		addField("CO_RUBR_ORIG","CHAR", 3, true, "CAMPO_CO_RUBR_ORIG");
		addField("LETRA_RUBRO","CHAR", 1, true, "CAMPO_LETRA_RUBRO");
		addField("NUM_PLACA","CHAR", 7, true, "CAMPO_CO_NUM_PLACA");
		addField("NS_ASIE_PLACA","NUMBER", 3, true, "CAMPO_NS_ASIE_PLACA");
		//SAUL COMENTA LOS CAMPOS:
		// - CAMPO_RECIBOS
		// - CAMPO_ID_USUA_CREA
		//MODIFICADO JBUGARIN ANOTACION INSCRIPCION
		//addField("RECIBOS","VARCHAR", 1000, true, "CAMPO_RECIBOS");
		//addField("ID_USUA_CREA","CHAR", 5, true, "CAMPO_ID_USUA_CREA");
		//
		addKey("COD_ACTO");
		addKey("NS_ASIENTO");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAsiento();
	} /* getThisDBObj() */
} /* DboAsiento */



