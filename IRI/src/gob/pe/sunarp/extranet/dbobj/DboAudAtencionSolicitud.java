/*
* DboAudAfiliacion.java - dbravo
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudAtencionSolicitud extends DBObject {

	public static final String CAMPO_AUD_ATEN_SOLICITUD_ID = "AUD_ATEN_SOLICITUD_ID";
	public static final String CAMPO_TS_ATENCION = "TS_ATENCION";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_COMENTARIO = "COMENTARIO";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";

	public DboAudAtencionSolicitud() throws DBException {
		super();
	} 


	public DboAudAtencionSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} 


	public DboAudAtencionSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	}


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_ATEN_SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("AUD_ATEN_SOLICITUD_ID","auto-inc", 0, false, "null");
		addField("TS_ATENCION","NUMBER", 0, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("COMENTARIO","VARCHAR", 1024, true, "null");
		addField("SOLICITUD_ID","NUMBER", 22, false, "null");
		addField("CUENTA_ID","NUMBER", 22, false, "null");

		addKey("AUD_ATEN_SOLICITUD_ID");
	} /* setupFields() */

	public DBObject getThisDBObj() throws DBException {
        return new DboAudAtencionSolicitud();
	} /* getThisDBObj() */
} /* DboAudAfiliacion */