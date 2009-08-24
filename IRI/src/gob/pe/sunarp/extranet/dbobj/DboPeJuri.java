/*
* DboPeJuri.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPeJuri extends DBObject {

	public static final String CAMPO_PE_JURI_ID = "PE_JURI_ID";
	public static final String CAMPO_RAZ_SOC = "RAZ_SOC";
	public static final String CAMPO_SIGLAS = "SIGLAS";
	public static final String CAMPO_PREF_CTA = "PREF_CTA";
	public static final String CAMPO_TIPO_ORG = "TIPO_ORG";
	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_NU_USRS = "NU_USRS";
	public static final String CAMPO_JURIS_ID = "JURIS_ID";
	public static final String CAMPO_GIRO_ID = "GIRO_ID";
	public static final String CAMPO_TS_ULT_ACC = "TS_ULT_ACC";
	public static final String CAMPO_REPRES_ID = "REPRES_ID";

	public DboPeJuri() throws DBException {
		super();
	} /* DboPeJuri() */


	public DboPeJuri(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPeJuri(DBConnection) */


	public DboPeJuri(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PE_JURI(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PE_JURI");

		setDescription("Object Description Goes Here");

		addField("PE_JURI_ID","auto-inc", 0, false, "null");
		addField("RAZ_SOC","VARCHAR", 100, false, "null");
		addField("SIGLAS","VARCHAR", 50, true, "null");
		addField("PREF_CTA","VARCHAR", 12, false, "null");
		addField("TIPO_ORG","CHAR", 1, false, "null");
		addField("PERSONA_ID","NUMBER", 22, false, "null");
		addField("NU_USRS","NUMBER", 3, true, "null");
		addField("JURIS_ID","NUMBER", 3, false, "null");
		addField("GIRO_ID","NUMBER", 22, true, "null");
		addField("TS_ULT_ACC","NUMBER", 22, true, "null");
		addField("REPRES_ID","NUMBER", 22, true, "null");

		addKey("PE_JURI_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPeJuri();
	} /* getThisDBObj() */
} /* DboPeJuri */

