/*
* DboAudConsultaPartida.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudConsultaPartida extends DBObject {

	public static final String CAMPO_AUD_CONSULTA_PARTIDA_ID = "AUD_CONSULTA_PARTIDA_ID";
	public static final String CAMPO_TIPO_BUSQ = "TIPO_BUSQ";
	public static final String CAMPO_NUM_PART_FICHA = "NUM_PART_FICHA";
	public static final String CAMPO_LIB_TOM_FOL = "LIB_TOM_FOL";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_TRANS_ID = "TRANS_ID";

	public DboAudConsultaPartida() throws DBException {
		super();
	} /* DboAudConsultaPartida() */


	public DboAudConsultaPartida(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudConsultaPartida(DBConnection) */


	public DboAudConsultaPartida(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_CONSULTA_PARTIDA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_CONSULTA_PARTIDA");

		setDescription("Object Description Goes Here");

		addField("AUD_CONSULTA_PARTIDA_ID","auto-inc", 0, false, "null");
		addField("TIPO_BUSQ","CHAR", 1, false, "null");
		addField("NUM_PART_FICHA","CHAR", 8, true, "null");
		addField("LIB_TOM_FOL","VARCHAR", 20, true, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("TRANS_ID","NUMBER", 22, false, "null");

		addKey("AUD_CONSULTA_PARTIDA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudConsultaPartida();
	} /* getThisDBObj() */
} /* DboAudConsultaPartida */

