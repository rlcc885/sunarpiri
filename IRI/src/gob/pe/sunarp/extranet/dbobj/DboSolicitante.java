/*
* DboSolicitante.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboSolicitante extends DBObject {

	public static final String CAMPO_SOLICITANTE_ID = "SOLICITANTE_ID";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_TPO_PERS = "TPO_PERS";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_NOMBRES = "NOMBRES";
	public static final String CAMPO_RAZ_SOC = "RAZ_SOC";
	public static final String CAMPO_TIPO_DOC_ID = "TIPO_DOC_ID";
	public static final String CAMPO_NUM_DOC_IDEN = "NUM_DOC_IDEN";
	public static final String CAMPO_EMAIL = "EMAIL";
	public static final String CAMPO_TS_CREA = "TS_CREA";

	public DboSolicitante() throws DBException {
		super();
	} /* DboSolicitante() */


	public DboSolicitante(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboSolicitante(DBConnection) */


	public DboSolicitante(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* SOLICITANTE(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("SOLICITANTE");

		setDescription("Object Description Goes Here");

		addField("SOLICITANTE_ID","auto-inc", 0, false, "CAMPO_SOLICITANTE_ID");
		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		addField("TPO_PERS","CHAR", 1, false, "CAMPO_TPO_PERS");
		addField("APE_PAT","VARCHAR", 30, true, "CAMPO_APE_PAT");
		addField("APE_MAT","VARCHAR", 30, true, "CAMPO_APE_MAT");
		addField("NOMBRES","VARCHAR", 40, true, "CAMPO_NOMBRES");
		addField("RAZ_SOC","VARCHAR", 100, true, "CAMPO_RAZ_SOC");
		addField("TIPO_DOC_ID","CHAR", 2, true, "CAMPO_TIPO_DOC_ID");
		addField("NUM_DOC_IDEN","VARCHAR", 15, true, "CAMPO_NUM_DOC_IDEN");
		addField("EMAIL","VARCHAR", 40, true, "CAMPO_EMAIL");
		addField("TS_CREA","NUMBER", 22, false, "CAMPO_TS_CREA");

		addKey("SOLICITANTE_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboSolicitante();
	} /* getThisDBObj() */
} /* DboSolicitante */

