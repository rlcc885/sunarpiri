/*
* DboMovimiento.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboMovimiento extends DBObject {

	public static final String CAMPO_MOVIMIENTO_ID = "MOVIMIENTO_ID";
	public static final String CAMPO_FEC_HOR = "FEC_HOR";
	public static final String CAMPO_TPO_MOV = "TPO_MOV";
	public static final String CAMPO_MONTO_FIN = "MONTO_FIN";
	public static final String CAMPO_FG_ASIG = "FG_ASIG";
	public static final String CAMPO_LINEA_PREPAGO_ID = "LINEA_PREPAGO_ID";

	public DboMovimiento() throws DBException {
		super();
	} /* DboMovimiento() */


	public DboMovimiento(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboMovimiento(DBConnection) */


	public DboMovimiento(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* MOVIMIENTO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("MOVIMIENTO");

		setDescription("Object Description Goes Here");

		//addField("MOVIMIENTO_ID","auto-inc", 0, false, "null");
		/**
		 * SVASQUEZ - AVATAR GLOBAL 
		 * TABLA-MOVIENTO FALTA ACTUALIZA EL SECUENCIAL DE ESTA TABLA
		 */
		addField("MOVIMIENTO_ID","NUMBER", 22, false, "null");
		
		addField("FEC_HOR","NUMBER", 22, false, "null");
		addField("TPO_MOV","CHAR", 1, false, "null");
		addField("MONTO_FIN","NUMBER", 12, false, "null");
		addField("FG_ASIG","CHAR", 1, true, "null");
		addField("LINEA_PREPAGO_ID","NUMBER", 22, false, "null");

		addKey("MOVIMIENTO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboMovimiento();
	} /* getThisDBObj() */
} /* DboMovimiento */

