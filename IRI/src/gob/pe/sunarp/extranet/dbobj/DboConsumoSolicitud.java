/*
* DboConsumoSolicitud.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboConsumoSolicitud extends DBObject {

	public static final String CAMPO_CONSUMO_ID = "CONSUMO_ID";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_OBJETO_SOL_ID = "OBJETO_SOL_ID";

	public DboConsumoSolicitud() throws DBException {
		super();
	} /* DboConsumoSolicitud() */


	public DboConsumoSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboConsumoSolicitud(DBConnection) */


	public DboConsumoSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CONSUMO_SOLICITUD(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CONSUMO_SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("CONSUMO_ID","NUMBER", 22, false, "CAMPO_CONSUMO_ID");
		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		addField("OBJETO_SOL_ID","NUMBER", 22, true, "CAMPO_OBJETO_SOL_ID");

		addKey("CONSUMO_ID");
		addKey("SOLICITUD_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboConsumoSolicitud();
	} /* getThisDBObj() */
} /* DboConsumoSolicitud */

