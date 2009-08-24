/*
* DboVwMovimiento.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVwMovimiento extends DBObject {

	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_ABONO_ID = "ABONO_ID";
	public static final String CAMPO_FEC_HOR = "FEC_HOR";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_TPO_PAG_VENT = "TPO_PAG_VENT";
	public static final String CAMPO_LINEA_PREPAGO_ID = "LINEA_PREPAGO_ID";
	public static final String CAMPO_TIPO_VENT = "TIPO_VENT";
	public static final String CAMPO_TIPO_ABONO = "TIPO_ABONO";
	public static final String CAMPO_USR_CAJA = "USR_CAJA";
	

	public DboVwMovimiento() throws DBException {
		super();
	} /* DboVwMovimiento() */


	public DboVwMovimiento(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVwMovimiento(DBConnection) */


	public DboVwMovimiento(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VW_MOVIMIENTO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VW_MOVIMIENTO");

		setDescription("Object Description Goes Here");

		addField("PERSONA_ID","NUMBER", 22, true, "null");
		addField("ABONO_ID","NUMBER", 22, true, "null");
		addField("FEC_HOR","NUMBER", 22, true, "null");
		addField("MONTO","NUMBER", 22, true, "null");
		addField("REG_PUB_ID","CHAR", 2, true, "null");
		addField("OFIC_REG_ID","CHAR", 2, true, "null");
		addField("NOMBRE","VARCHAR", 30, true, "null");
		addField("TPO_PAG_VENT","VARCHAR", 18, true, "null");
		addField("LINEA_PREPAGO_ID","NUMBER", 22, true, "null");
		addField("TIPO_VENT","CHAR", 1, true, "null");
		addField("TIPO_ABONO","CHAR", 1, true, "null");
		addField("USR_CAJA","VARCHAR", 15, true, "null");

	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVwMovimiento();
	} /* getThisDBObj() */
} /* DboVwMovimiento */