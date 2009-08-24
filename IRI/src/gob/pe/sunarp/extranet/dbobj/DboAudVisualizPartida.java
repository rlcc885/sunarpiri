/*
* DboAudVisualizPartida.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudVisualizPartida extends DBObject {

	public static final String CAMPO_AUD_VISUALIZ_PARTIDA_ID = "AUD_VISUALIZ_PARTIDA_ID";
	public static final String CAMPO_NUM_PARTIDA = "NUM_PARTIDA";
	public static final String CAMPO_NUMERO_DOC_VISUALIZ = "NUMERO_DOC_VISUALIZ";
	public static final String CAMPO_TIPO_IMG_VISUALIZ = "TIPO_IMG_VISUALIZ";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_TRANS_ID = "TRANS_ID";

	public DboAudVisualizPartida() throws DBException {
		super();
	} /* DboAudVisualizPartida() */


	public DboAudVisualizPartida(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudVisualizPartida(DBConnection) */


	public DboAudVisualizPartida(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_VISUALIZ_PARTIDA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_VISUALIZ_PARTIDA");

		setDescription("Object Description Goes Here");

		//addField("AUD_VISUALIZ_PARTIDA_ID","auto-inc", 0, false, "null");
		addField("AUD_VISUALIZ_PARTIDA_ID","NUMBER", 0, false, "null");
		addField("NUM_PARTIDA","CHAR", 8, false, "null");
		addField("NUMERO_DOC_VISUALIZ","VARCHAR", 30, false, "null");
		addField("TIPO_IMG_VISUALIZ","CHAR", 1, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("TRANS_ID","NUMBER", 22, false, "null");

		addKey("AUD_VISUALIZ_PARTIDA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudVisualizPartida();
	} /* getThisDBObj() */
} /* DboAudVisualizPartida */

