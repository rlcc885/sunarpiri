/*
* DboTmEstadoSolicitud.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmEstadoSolicitud extends DBObject {

	public static final String CAMPO_ESTADO_SOLICITUD = "ESTADO_SOLICITUD";
	public static final String CAMPO_MENSAJE_REGISTRADOR = "MENSAJE_REGISTRADOR";
	public static final String CAMPO_MENSAJE_USUARIO = "MENSAJE_USUARIO";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmEstadoSolicitud() throws DBException {
		super();
	} /* DboTmEstadoSolicitud() */


	public DboTmEstadoSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmEstadoSolicitud(DBConnection) */


	public DboTmEstadoSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_ESTADO_SOLICITUD(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_ESTADO_SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("ESTADO_SOLICITUD","CHAR", 1, false, "CAMPO_ESTADO_SOLICITUD");
		addField("MENSAJE_REGISTRADOR","VARCHAR", 80, false, "CAMPO_MENSAJE_REGISTRADOR");
		addField("MENSAJE_USUARIO","VARCHAR", 80, false, "CAMPO_MENSAJE_USUARIO");
		addField("ESTADO","CHAR", 1, false, "CAMPO_ESTADO");

		addKey("ESTADO_SOLICITUD");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmEstadoSolicitud();
	} /* getThisDBObj() */
} /* DboTmEstadoSolicitud */

