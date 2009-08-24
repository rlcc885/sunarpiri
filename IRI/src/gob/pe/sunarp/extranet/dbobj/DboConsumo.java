/*
* DboConsumo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboConsumo extends DBObject {

	public static final String CAMPO_CONSUMO_ID = "CONSUMO_ID";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_MOVIMIENTO_ID = "MOVIMIENTO_ID";
	public static final String CAMPO_TRANS_ID = "TRANS_ID";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_MODI = "TS_MODI";
	public static final String CAMPO_TPO_CONSUMO = "TPO_CONSUMO";

	public DboConsumo() throws DBException {
		super();
	} /* DboConsumo() */


	public DboConsumo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboConsumo(DBConnection) */


	public DboConsumo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CONSUMO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CONSUMO");

		setDescription("Object Description Goes Here");

		//addField("CONSUMO_ID","auto-inc", 0, false, "null");
		addField("CONSUMO_ID","NUMBER", 0, false, "null");
		addField("MONTO","NUMBER", 12, false, "null");
		addField("MOVIMIENTO_ID","NUMBER", 22, false, "null");
		addField("TRANS_ID","NUMBER", 22, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("TS_MODI","NUMBER", 22, true, "null");
		addField("TPO_CONSUMO","CHAR", 1, false, "null");

		addKey("CONSUMO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboConsumo();
	} /* getThisDBObj() */
} /* DboConsumo */

