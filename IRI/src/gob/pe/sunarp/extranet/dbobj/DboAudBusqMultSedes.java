/*
* DboAudBusqMultSedes.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudBusqMultSedes extends DBObject {

	public static final String CAMPO_AUD_BUSQ_PARTIDA_ID = "AUD_BUSQ_PARTIDA_ID";
	public static final String CAMPO_COD_SEDE = "COD_SEDE";
	public static final String CAMPO_TIPO_DOC_BUSQ = "TIPO_DOC_BUSQ";

	public DboAudBusqMultSedes() throws DBException {
		super();
	} /* DboAudBusqMultSedes() */


	public DboAudBusqMultSedes(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudBusqMultSedes(DBConnection) */


	public DboAudBusqMultSedes(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_BUSQ_MULT_SEDES(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_MULT_SEDES");

		setDescription("Object Description Goes Here");

		addField("AUD_BUSQ_PARTIDA_ID","NUMBER", 22, false, "null");
		addField("COD_SEDE","CHAR", 2, false, "null");
		addField("TIPO_DOC_BUSQ","CHAR", 1, false, "null");

		addKey("AUD_BUSQ_PARTIDA_ID");
		addKey("COD_SEDE");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqMultSedes();
	} /* getThisDBObj() */
} /* DboAudBusqMultSedes */

