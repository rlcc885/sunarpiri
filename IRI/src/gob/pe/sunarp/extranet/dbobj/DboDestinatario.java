/*
* DboDestinatario.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboDestinatario extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_COD_POST = "COD_POST";
	public static final String CAMPO_DPTO_OTRO = "DPTO_OTRO";
	public static final String CAMPO_DISTRITO = "DISTRITO";
	public static final String CAMPO_PROV_ID = "PROV_ID";
	public static final String CAMPO_DPTO_ID = "DPTO_ID";
	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_TPO_ENV = "TPO_ENV";
	public static final String CAMPO_RAZ_SOC = "RAZ_SOC";
	public static final String CAMPO_NOMBRES = "NOMBRES";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_TPO_PERS = "TPO_PERS";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_DIRECC = "DIRECC";
	public static final String CAMPO_TIPO_DOC_ID = "TIPO_DOC_ID";
	public static final String CAMPO_EMAIL = "EMAIL";
	public static final String CAMPO_NUM_DOC_IDEN = "NUM_DOC_IDEN";
	public static final String CAMPO_DESTINATARIO_ID = "DESTINATARIO_ID";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";

	public DboDestinatario() throws DBException {
		super();
	} /* DboDestinatario() */


	public DboDestinatario(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboDestinatario(DBConnection) */


	public DboDestinatario(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DESTINATARIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("DESTINATARIO");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "CAMPO_REG_PUB_ID");
		addField("OFIC_REG_ID","CHAR", 2, false, "CAMPO_OFIC_REG_ID");
		addField("COD_POST","VARCHAR", 30, true, "CAMPO_COD_POST");
		addField("DPTO_OTRO","VARCHAR", 30, true, "CAMPO_DPTO_OTRO");
		addField("DISTRITO","CHAR", 30, true, "CAMPO_DISTRITO");
		addField("PROV_ID","CHAR", 2, true, "CAMPO_PROV_ID");
		addField("DPTO_ID","CHAR", 2, true, "CAMPO_DPTO_ID");
		addField("PAIS_ID","CHAR", 2, true, "CAMPO_PAIS_ID");
		addField("TPO_ENV","CHAR", 1, false, "CAMPO_TPO_ENV");
		addField("RAZ_SOC","VARCHAR", 100, true, "CAMPO_RAZ_SOC");
		addField("NOMBRES","VARCHAR", 40, true, "CAMPO_NOMBRES");
		addField("APE_MAT","VARCHAR", 30, true, "CAMPO_APE_MAT");
		addField("APE_PAT","VARCHAR", 30, true, "CAMPO_APE_PAT");
		addField("TPO_PERS","CHAR", 1, false, "CAMPO_TPO_PERS");
		addField("TS_CREA","NUMBER", 22, false, "CAMPO_TS_CREA");
		addField("DIRECC","VARCHAR", 90, true, "CAMPO_DIRECC");
		addField("TIPO_DOC_ID","CHAR", 2, true, "CAMPO_TIPO_DOC_ID");
		addField("EMAIL","VARCHAR", 40, true, "CAMPO_EMAIL");
		addField("NUM_DOC_IDEN","VARCHAR", 15, true, "CAMPO_NUM_DOC_IDEN");
		addField("DESTINATARIO_ID","auto-inc", 0, false, "CAMPO_DESTINATARIO_ID");
		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");

		addKey("DESTINATARIO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboDestinatario();
	} /* getThisDBObj() */
} /* DboDestinatario */

