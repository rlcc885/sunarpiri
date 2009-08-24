/*
* DboPagoSolicitud.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPagoSolicitud extends DBObject {

	public static final String CAMPO_PAGO_SOLICITUD_ID = "PAGO_SOLICITUD_ID";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_TPO_PAGO = "TPO_PAGO";
	public static final String CAMPO_ABONO_ID = "ABONO_ID";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_MODI = "TS_MODI";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_MODI = "USR_MODI";

	public DboPagoSolicitud() throws DBException {
		super();
	} /* DboPagoSolicitud() */


	public DboPagoSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPagoSolicitud(DBConnection) */


	public DboPagoSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PAGO_SOLICITUD(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PAGO_SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("PAGO_SOLICITUD_ID","auto-inc", 22, false, "CAMPO_PAGO_SOLICITUD_ID");
		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		addField("TPO_PAGO","CHAR", 1, false, "CAMPO_TPO_PAGO");
		addField("ABONO_ID","NUMBER", 22, true, "CAMPO_ABONO_ID");
		addField("MONTO","NUMBER", 12, false, "CAMPO_MONTO");
		addField("TS_CREA","NUMBER", 22, true, "CAMPO_TS_CREA");
		addField("TS_MODI","NUMBER", 22, true, "CAMPO_TS_MODI");
		addField("USR_CREA","VARCHAR", 15, true, "CAMPO_USR_CREA");
		addField("USR_MODI","VARCHAR", 15, true, "CAMPO_USR_MODI");

		addKey("PAGO_SOLICITUD_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPagoSolicitud();
	} /* getThisDBObj() */
} /* DboPagoSolicitud */

