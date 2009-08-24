/*
* DboComprobante.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboComprobante extends DBObject {

	public static final String CAMPO_COMPROBANTE_ID = "COMPROBANTE_ID";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_ABONO_ID = "ABONO_ID";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboComprobante() throws DBException {
		super();
	} /* DboComprobante() */


	public DboComprobante(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboComprobante(DBConnection) */


	public DboComprobante(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* COMPROBANTE(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("COMPROBANTE");

		setDescription("Object Description Goes Here");

		addField("COMPROBANTE_ID","auto-inc", 0, false, "null");
		addField("MONTO","NUMBER", 12, false, "null");
		addField("ABONO_ID","NUMBER", 22, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");

		addKey("COMPROBANTE_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboComprobante();
	} /* getThisDBObj() */
} /* DboComprobante */

