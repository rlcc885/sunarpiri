/*
* DboAbono.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAbono extends DBObject {

	public static final String CAMPO_ABONO_ID = "ABONO_ID";
	public static final String CAMPO_TIPO_ABONO = "TIPO_ABONO";
	public static final String CAMPO_MEDIO_ID = "MEDIO_ID";
	public static final String CAMPO_TIPO_VENT = "TIPO_VENT";
	public static final String CAMPO_TPO_PAG_VENT = "TPO_PAG_VENT";
	public static final String CAMPO_USR_CAJA = "USR_CAJA";
	public static final String CAMPO_TIPO_USR = "TIPO_USR";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_MOVIMIENTO_ID = "MOVIMIENTO_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_FG_CIERRE = "FG_CIERRE";
	public static final String CAMPO_RCBO_ASOC = "RCBO_ASOC";
	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_MODI = "TS_MODI";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboAbono() throws DBException {
		super();
	} /* DboAbono() */


	public DboAbono(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAbono(DBConnection) */


	public DboAbono(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ABONO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("ABONO");

		setDescription("Object Description Goes Here");

		addField("ABONO_ID","auto-inc", 0, false, "null");
		addField("TIPO_ABONO","CHAR", 1, false, "null");
		addField("MEDIO_ID","NUMBER", 22, true, "null");
		addField("TIPO_VENT","CHAR", 1, true, "null");
		addField("TPO_PAG_VENT","CHAR", 2, true, "null");
		addField("USR_CAJA","VARCHAR", 15, true, "null");
		addField("TIPO_USR","CHAR", 1, true, "null");
		addField("MONTO","NUMBER", 12, false, "null");
		addField("MOVIMIENTO_ID","NUMBER", 22, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("FG_CIERRE","CHAR", 1, true, "null");
		addField("RCBO_ASOC","VARCHAR", 15, true, "null");
		addField("PERSONA_ID","NUMBER", 22, true, "null");
		addField("TS_CREA","NUMBER", 22, false, "null");
		addField("TS_MODI","NUMBER", 22, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");

		addKey("ABONO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAbono();
	} /* getThisDBObj() */
} /* DboAbono */

