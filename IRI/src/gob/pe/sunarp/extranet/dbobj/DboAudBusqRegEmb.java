/*
* DboAudBusqRegEmb.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudBusqRegEmb extends DBObject {

	public static final String CAMPO_AUD_BUSQ_REG_EMB_ID = "AUD_BUSQ_REG_EMB_ID";
	public static final String CAMPO_TIPO_PARAM = "TIPO_PARAM";
	public static final String CAMPO_VALOR = "VALOR";
	public static final String CAMPO_AUD_BUSQ_PARTIDA_ID = "AUD_BUSQ_PARTIDA_ID";
	public static final String CAMPO_TIPO_EMB = "TIPO_EMB";

	public DboAudBusqRegEmb() throws DBException {
		super();
	} /* DboAudBusqRegEmb() */


	public DboAudBusqRegEmb(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudBusqRegEmb(DBConnection) */


	public DboAudBusqRegEmb(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_BUSQ_REG_EMB(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_REG_EMB");

		setDescription("Object Description Goes Here");

		addField("AUD_BUSQ_REG_EMB_ID","auto-inc", 0, false, "null");
		addField("TIPO_PARAM","CHAR", 1, false, "null");
		addField("VALOR","VARCHAR", 100, false, "null");
		addField("AUD_BUSQ_PARTIDA_ID","NUMBER", 22, false, "null");
		addField("TIPO_EMB","CHAR", 1, false, "null");

		addKey("AUD_BUSQ_REG_EMB_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqRegEmb();
	} /* getThisDBObj() */
} /* DboAudBusqRegEmb */

