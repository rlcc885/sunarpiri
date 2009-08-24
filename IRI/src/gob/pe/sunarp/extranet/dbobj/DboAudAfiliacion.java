/*
* DboAudAfiliacion.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudAfiliacion extends DBObject {

	public static final String CAMPO_AUD_AFIL_ID = "AUD_AFIL_ID";
	public static final String CAMPO_FG_WEB = "FG_WEB";
	public static final String CAMPO_TRANS_ID = "TRANS_ID";
	public static final String CAMPO_NUM_CONT = "NUM_CONT";
	public static final String CAMPO_TIPO_AFIL = "TIPO_AFIL";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";

	public DboAudAfiliacion() throws DBException {
		super();
	} /* DboAudAfiliacion() */


	public DboAudAfiliacion(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudAfiliacion(DBConnection) */


	public DboAudAfiliacion(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_AFILIACION(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_AFILIACION");

		setDescription("Object Description Goes Here");

		addField("AUD_AFIL_ID","auto-inc", 0, false, "null");
		addField("FG_WEB","CHAR", 1, true, "null");
		addField("TRANS_ID","NUMBER", 22, false, "null");
		addField("NUM_CONT","NUMBER", 22, true, "null");
		addField("TIPO_AFIL","CHAR", 1, false, "null");
		addField("REG_PUB_ID","CHAR", 2, true, "null");
		addField("OFIC_REG_ID","CHAR", 2, true, "null");
		addField("PERSONA_ID","NUMBER", 22, false, "null");

		addKey("AUD_AFIL_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudAfiliacion();
	} /* getThisDBObj() */
} /* DboAudAfiliacion */