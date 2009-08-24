/*
* DboSolicitudXCarga.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboSolicitudXCarga extends DBObject {

	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_ROL = "ROL";
	public static final String CAMPO_CTA_ID_REG = "CTA_ID_REG";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_PRIORIDAD = "PRIORIDAD";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_MODI = "TS_MODI";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_MODI = "USR_MODI";

	public DboSolicitudXCarga() throws DBException {
		super();
	} /* DboSolicitudXCarga() */


	public DboSolicitudXCarga(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboSolicitudXCarga(DBConnection) */


	public DboSolicitudXCarga(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* SOLICITUD_X_CARGA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("SOLICITUD_X_CARGA");

		setDescription("Object Description Goes Here");

		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		addField("CUENTA_ID","NUMBER", 22, false, "CAMPO_CUENTA_ID");
		addField("ROL","CHAR", 2, false, "CAMPO_ROL");
		addField("CTA_ID_REG","NUMBER", 22, false, "CAMPO_CTA_ID_REG");
		addField("ESTADO","CHAR", 1, false, "CAMPO_ESTADO");
		addField("PRIORIDAD","NUMBER", 22, false, "CAMPO_PRIORIDAD");
		addField("TS_CREA","NUMBER", 22, false, "CAMPO_TS_CREA");
		addField("TS_MODI","NUMBER", 22, true, "CAMPO_TS_MODI");
		addField("USR_CREA","VARCHAR", 15, false, "CAMPO_USR_CREA");
		addField("USR_MODI","VARCHAR", 15, true, "CAMPO_USR_MODI");
		
		addKey("ROL");
		addKey("SOLICITUD_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboSolicitudXCarga();
	} /* getThisDBObj() */
} /* DboSolicitudXCarga */

