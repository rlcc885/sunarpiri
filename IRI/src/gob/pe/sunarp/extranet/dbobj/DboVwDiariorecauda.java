/*
* DboVwDiariorecauda.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVwDiariorecauda extends DBObject {

	public static final String CAMPO_USR_CAJA = "USR_CAJA";
	public static final String CAMPO_RCBO_ASOC = "RCBO_ASOC";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_ABONO_ID = "ABONO_ID";
	public static final String CAMPO_TIPO_USR = "TIPO_USR";
	public static final String CAMPO_PERSONA_ID = "PERSONA_ID";
	public static final String CAMPO_MONTO = "MONTO";
	public static final String CAMPO_TIPO_VENT = "TIPO_VENT";
	public static final String CAMPO_TPO_PAG_VENT = "TPO_PAG_VENT";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_NOMBRES = "NOMBRES";

	public DboVwDiariorecauda() throws DBException {
		super();
	} /* DboVwDiariorecauda() */


	public DboVwDiariorecauda(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVwDiariorecauda(DBConnection) */


	public DboVwDiariorecauda(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VW_DIARIORECAUDA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VW_DIARIORECAUDA");

		setDescription("Object Description Goes Here");

		addField("USR_CAJA","VARCHAR", 15, true, "null");
		addField("RCBO_ASOC","VARCHAR", 15, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("ABONO_ID","NUMBER", 22, true, "null");
		addField("TIPO_USR","CHAR", 1, true, "null");
		addField("PERSONA_ID","NUMBER", 22, true, "null");
		addField("MONTO","NUMBER", 22, true, "null");
		addField("TIPO_VENT","VARCHAR", 40, true, "null");
		addField("TPO_PAG_VENT","CHAR", 2, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("APE_PAT","VARCHAR", 100, true, "null");
		addField("APE_MAT","VARCHAR", 30, true, "null");
		addField("NOMBRES","VARCHAR", 40, true, "null");

	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVwDiariorecauda();
	} /* getThisDBObj() */
} /* DboVwDiariorecauda */

