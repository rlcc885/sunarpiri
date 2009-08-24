/*
* DboAudBusqPartida.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudBusqPartida extends DBObject {

	public static final String CAMPO_AUD_BUSQ_PARTIDA_ID = "AUD_BUSQ_PARTIDA_ID";
	public static final String CAMPO_NUM_SEDES = "NUM_SEDES";
	public static final String CAMPO_TIPO_PERS_PART = "TIPO_PERS_PART";
	public static final String CAMPO_NOMAPE_RAZSOC_PART = "NOMAPE_RAZSOC_PART";
	public static final String CAMPO_TIPO_PARTICIPACION = "TIPO_PARTICIPACION";
	public static final String CAMPO_COD_AREA_REG = "COD_AREA_REG";
	public static final String CAMPO_TRANS_ID = "TRANS_ID";

	public DboAudBusqPartida() throws DBException {
		super();
	} /* DboAudBusqPartida() */


	public DboAudBusqPartida(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudBusqPartida(DBConnection) */


	public DboAudBusqPartida(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_BUSQ_PARTIDA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_PARTIDA");

		setDescription("Object Description Goes Here");

		//addField("AUD_BUSQ_PARTIDA_ID","auto-inc", 0, false, "null");
		addField("AUD_BUSQ_PARTIDA_ID","NUMBER", 0, false, "null");
		addField("NUM_SEDES","NUMBER", 2, false, "null");
		addField("TIPO_PERS_PART","CHAR", 1, false, "null");
		addField("NOMAPE_RAZSOC_PART","VARCHAR", 100, false, "null");
		addField("TIPO_PARTICIPACION","CHAR", 4, true, "null");
		addField("COD_AREA_REG","CHAR", 5, false, "null");
		addField("TRANS_ID","NUMBER", 22, false, "null");

		addKey("AUD_BUSQ_PARTIDA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqPartida();
	} /* getThisDBObj() */
} /* DboAudBusqPartida */

