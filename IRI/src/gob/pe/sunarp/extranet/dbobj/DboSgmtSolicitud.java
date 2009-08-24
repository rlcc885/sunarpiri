/*
* DboSgmtSolicitud.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboSgmtSolicitud extends DBObject {

	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_USR_MOVIMIENTO = "USR_MOVIMIENTO";
	public static final String CAMPO_TS_MOVIMIENTO = "TS_MOVIMIENTO";
	public static final String CAMPO_ESTADO_FINAL = "ESTADO_FINAL";
	public static final String CAMPO_ESTADO_INICIAL = "ESTADO_INICIAL";
	public static final String CAMPO_SGMT_SOLICITUD_ID = "SGMT_SOLICITUD_ID";

	public DboSgmtSolicitud() throws DBException {
		super();
	} /* DboSgmtSolicitud() */


	public DboSgmtSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboSgmtSolicitud(DBConnection) */


	public DboSgmtSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* SGMT_SOLICITUD(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("SGMT_SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		addField("USR_MOVIMIENTO","VARCHAR", 15, false, "CAMPO_USR_MOVIMIENTO");
		addField("TS_MOVIMIENTO","NUMBER", 22, false, "CAMPO_TS_MOVIMIENTO");
		addField("ESTADO_FINAL","CHAR", 1, false, "CAMPO_ESTADO_FINAL");
		addField("ESTADO_INICIAL","CHAR", 1, false, "CAMPO_ESTADO_INICIAL");
		addField("SGMT_SOLICITUD_ID","auto-inc", 0, false, "CAMPO_SGMT_SOLICITUD_ID");

		addKey("SGMT_SOLICITUD_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboSgmtSolicitud();
	} /* getThisDBObj() */
} /* DboSgmtSolicitud */

