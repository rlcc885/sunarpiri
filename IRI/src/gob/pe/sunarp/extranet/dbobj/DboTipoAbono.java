/*
* DboTipoAbono.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTipoAbono extends DBObject {

	public static final String CAMPO_TIPO_ABONO = "TIPO_ABONO";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTipoAbono() throws DBException {
		super();
	} /* DboTipoAbono() */


	public DboTipoAbono(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTipoAbono(DBConnection) */


	public DboTipoAbono(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TIPO_ABONO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TIPO_ABONO");

		setDescription("Object Description Goes Here");

		addField("TIPO_ABONO","CHAR", 1, false, "CAMPO_TIPO_ABONO");
		addField("DESCRIPCION","VARCHAR", 40, true, "CAMPO_DESCRIPCION");

		addKey("TIPO_ABONO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTipoAbono();
	} /* getThisDBObj() */
} /* DboTipoAbono */

