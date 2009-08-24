/*
* DboTmEstadoTitulo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmEstadoTitulo extends DBObject {

	public static final String CAMPO_ESTADO_TITULO_ID = "ESTADO_TITULO_ID";
	public static final String CAMPO_MENSAJE = "MENSAJE";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmEstadoTitulo() throws DBException {
		super();
	} /* DboTmEstadoTitulo() */


	public DboTmEstadoTitulo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmEstadoTitulo(DBConnection) */


	public DboTmEstadoTitulo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_ESTADO_TITULO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_ESTADO_TITULO");

		setDescription("Object Description Goes Here");

		addField("ESTADO_TITULO_ID","auto-inc", 0, false, "null");
		addField("MENSAJE","VARCHAR", 80, false, "null");
		addField("ESTADO","VARCHAR", 20, false, "null");

		addKey("ESTADO_TITULO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmEstadoTitulo();
	} /* getThisDBObj() */
} /* DboTmEstadoTitulo */

