/*
* DboPeNatu.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPeNatu extends DBObject {

	public static final String CAMPO_PE_NATU_ID = "PE_NATU_ID";
	public static final String CAMPO_NOMBRES = "NOMBRES";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_PE_JURI_ID = "PE_JURI_ID";
	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";

	public DboPeNatu() throws DBException {
		super();
	} /* DboPeNatu() */


	public DboPeNatu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPeNatu(DBConnection) */


	public DboPeNatu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PE_NATU(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PE_NATU");

		setDescription("Object Description Goes Here");

		addField("PE_NATU_ID","auto-inc", 0, false, "null");
		addField("NOMBRES","VARCHAR", 40, false, "null");
		addField("APE_PAT","VARCHAR", 30, false, "null");
		addField("APE_MAT","VARCHAR", 30, true, "null");
		addField("PE_JURI_ID","NUMBER", 22, true, "null");
		addField("PERSONA_ID","NUMBER", 22, false, "null");

		addKey("PE_NATU_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPeNatu();
	} /* getThisDBObj() */
} /* DboPeNatu */

