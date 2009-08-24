/*
* DboMediosPago.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboMediosPago extends DBObject {

	public static final String CAMPO_MEDIO_ID = "MEDIO_ID";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_TPO_TARJ = "TPO_TARJ";
	public static final String CAMPO_COD_TIENDA = "COD_TIENDA";
	public static final String CAMPO_TPO_RESP = "TPO_RESP";
	public static final String CAMPO_MONEDA = "MONEDA";
	public static final String CAMPO_TPO_TRANSAC = "TPO_TRANSAC";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboMediosPago() throws DBException {
		super();
	} /* DboMediosPago() */


	public DboMediosPago(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboMediosPago(DBConnection) */


	public DboMediosPago(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* MEDIOS_PAGO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("MEDIOS_PAGO");

		setDescription("Object Description Goes Here");

		addField("MEDIO_ID","auto-inc", 0, false, "null");
		addField("DESCRIPCION","CHAR", 18, true, "null");
		addField("TPO_TARJ","CHAR", 1, true, "null");
		addField("COD_TIENDA","CHAR", 15, true, "null");
		addField("TPO_RESP","CHAR", 1, true, "null");
		addField("MONEDA","CHAR", 5, true, "null");
		addField("TPO_TRANSAC","CHAR", 1, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");

		addKey("MEDIO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboMediosPago();
	} /* getThisDBObj() */
} /* DboMediosPago */

