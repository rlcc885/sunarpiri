/*
* DboAudBusqRazSocPj.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudBusqRazSocPj extends DBObject {

	public static final String CAMPO_AUD_BUSQ_RAZ_SOC_PJ_ID = "AUD_BUSQ_RAZ_SOC_PJ_ID";
	public static final String CAMPO_RAZ_SOC_PJ = "RAZ_SOC_PJ";
	public static final String CAMPO_AUD_BUSQ_PARTIDA_ID = "AUD_BUSQ_PARTIDA_ID";

	public DboAudBusqRazSocPj() throws DBException {
		super();
	} /* DboAudBusqRazSocPj() */


	public DboAudBusqRazSocPj(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudBusqRazSocPj(DBConnection) */


	public DboAudBusqRazSocPj(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_BUSQ_RAZ_SOC_PJ(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_RAZ_SOC_PJ");

		setDescription("Object Description Goes Here");

		addField("AUD_BUSQ_RAZ_SOC_PJ_ID","auto-inc", 0, false, "null");
		addField("RAZ_SOC_PJ","VARCHAR", 100, false, "null");
		addField("AUD_BUSQ_PARTIDA_ID","NUMBER", 22, false, "null");

		addKey("AUD_BUSQ_RAZ_SOC_PJ_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqRazSocPj();
	} /* getThisDBObj() */
} /* DboAudBusqRazSocPj */

