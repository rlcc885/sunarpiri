/*
* DboAudBusqRegMin.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudBusqRegMin extends DBObject {

	public static final String CAMPO_AUD_BUSQ_REG_MIN_ID = "AUD_BUSQ_REG_MIN_ID";
	public static final String CAMPO_TIPO_PARAM = "TIPO_PARAM";
	public static final String CAMPO_VALOR = "VALOR";
	public static final String CAMPO_AUD_BUSQ_PARTIDA_ID = "AUD_BUSQ_PARTIDA_ID";

	public DboAudBusqRegMin() throws DBException {
		super();
	} /* DboAudBusqRegMin() */


	public DboAudBusqRegMin(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudBusqRegMin(DBConnection) */


	public DboAudBusqRegMin(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_BUSQ_REG_MIN(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_REG_MIN");

		setDescription("Object Description Goes Here");

		addField("AUD_BUSQ_REG_MIN_ID","auto-inc", 0, false, "null");
		addField("TIPO_PARAM","CHAR", 1, false, "null");
		addField("VALOR","VARCHAR", 100, false, "null");
		addField("AUD_BUSQ_PARTIDA_ID","NUMBER", 22, false, "null");

		addKey("AUD_BUSQ_REG_MIN_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqRegMin();
	} /* getThisDBObj() */
} /* DboAudBusqRegMin */

