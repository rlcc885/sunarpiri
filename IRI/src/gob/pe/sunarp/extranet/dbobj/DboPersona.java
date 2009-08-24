/*
* DboPersona.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPersona extends DBObject {

	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_NUM_DOC_IDEN = "NUM_DOC_IDEN";
	public static final String CAMPO_TPO_PERS = "TPO_PERS";
	public static final String CAMPO_FAX = "FAX";
	public static final String CAMPO_EMAIL = "EMAIL";
	public static final String CAMPO_TELEF = "TELEF";
	public static final String CAMPO_ANEXO = "ANEXO";
	public static final String CAMPO_TIPO_DOC_ID = "TIPO_DOC_ID";
	public static final String CAMPO_JURIS_ID = "JURIS_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";

	public DboPersona() throws DBException {
		super();
	} /* DboPersona() */


	public DboPersona(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPersona(DBConnection) */


	public DboPersona(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PERSONA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PERSONA");

		setDescription("Object Description Goes Here");

		addField("PERSONA_ID","auto-inc", 0, false, "c1");
		addField("NUM_DOC_IDEN","VARCHAR", 15, false, "c2");
		addField("TPO_PERS","CHAR", 1, false, "c2");
		addField("FAX","VARCHAR", 32, true, "c4");
		addField("EMAIL","VARCHAR", 40, false, "c5");
		addField("TELEF","VARCHAR", 32, true, "c6");
		addField("ANEXO","VARCHAR", 10, true, "c7");
		addField("TIPO_DOC_ID","CHAR", 2, false, "c8");
		addField("JURIS_ID","NUMBER", 22, false, "c9");
		addField("REG_PUB_ID","CHAR", 2, true, "c10");

		addKey("PERSONA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPersona();
	} /* getThisDBObj() */
} /* DboPersona */

