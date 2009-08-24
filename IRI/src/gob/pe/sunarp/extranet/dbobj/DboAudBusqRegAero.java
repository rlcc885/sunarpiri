/*
* DboAudBusqRegAero.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudBusqRegAero extends DBObject {

	public static final String CAMPO_AUD_BUSQ_REG_AERO_ID = "AUD_BUSQ_REG_AERO_ID";
	public static final String CAMPO_TIPO_PARAM = "TIPO_PARAM";
	public static final String CAMPO_VALOR = "VALOR";
	public static final String CAMPO_TIPO_TITULAR = "TIPO_TITULAR";
	public static final String CAMPO_AUD_BUSQ_PARTIDA_ID = "AUD_BUSQ_PARTIDA_ID";

	public DboAudBusqRegAero() throws DBException {
		super();
	} /* DboAudBusqRegAero() */


	public DboAudBusqRegAero(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudBusqRegAero(DBConnection) */


	public DboAudBusqRegAero(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_BUSQ_REG_AERO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_REG_AERO");

		setDescription("Object Description Goes Here");

		addField("AUD_BUSQ_REG_AERO_ID","auto-inc", 0, false, "null");
		addField("TIPO_PARAM","CHAR", 1, false, "null");
		addField("VALOR","VARCHAR", 100, false, "null");
		addField("TIPO_TITULAR","CHAR", 1, true, "null");
		addField("AUD_BUSQ_PARTIDA_ID","NUMBER", 22, false, "null");

		addKey("AUD_BUSQ_REG_AERO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqRegAero();
	} /* getThisDBObj() */
} /* DboAudBusqRegAero */

