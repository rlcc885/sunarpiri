/*
* DboSolicitud.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboSolicitud extends DBObject {

	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_TOTAL = "TOTAL";
	public static final String CAMPO_USR_MODI = "USR_MODI";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_TS_MODI = "TS_MODI";
	public static final String CAMPO_GASTO_ENVIO = "GASTO_ENVIO";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_SUBTOTAL = "SUBTOTAL";

	public DboSolicitud() throws DBException {
		super();
	} /* DboSolicitud() */


	public DboSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboSolicitud(DBConnection) */


	public DboSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* SOLICITUD(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("SOLICITUD_ID","auto-inc", 0, false, "CAMPO_SOLICITUD_ID");
		addField("ESTADO","CHAR", 1, false, "CAMPO_ESTADO");
		addField("CUENTA_ID","NUMBER", 22, false, "CAMPO_CUENTA_ID");
		addField("TOTAL","NUMBER", 12, false, "CAMPO_TOTAL");
		addField("USR_MODI","VARCHAR", 15, false, "CAMPO_USR_MODI");
		addField("USR_CREA","VARCHAR", 15, false, "CAMPO_USR_CREA");
		addField("TS_MODI","NUMBER", 22, false, "CAMPO_TS_MODI");
		addField("GASTO_ENVIO","NUMBER", 12, false, "CAMPO_GASTO_ENVIO");
		addField("TS_CREA","NUMBER", 22, false, "CAMPO_TS_CREA");
		addField("SUBTOTAL","NUMBER", 12, false, "CAMPO_SUBTOTAL");

		addKey("SOLICITUD_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboSolicitud();
	} /* getThisDBObj() */
} /* DboSolicitud */

