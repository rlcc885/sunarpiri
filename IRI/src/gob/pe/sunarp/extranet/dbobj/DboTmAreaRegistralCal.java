/*
* DboTmAreaRegistral.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmAreaRegistralCal extends DBObject {

	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_PREFIJO = "PREFIJO";

	public DboTmAreaRegistralCal() throws DBException {
		super();
	} /* DboTmAreaRegistral() */





	public DboTmAreaRegistralCal(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmAreaRegistral(DBConnection) */




	public DboTmAreaRegistralCal(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_AREA_REGISTRAL(DBConnection, String) */




	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_AREA_REGISTRAL_CAL");

		setDescription("Object Description Goes Here");

		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("NOMBRE","VARCHAR", 30, false, "null");
		addField("DESCRIPCION","VARCHAR", 80, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("PREFIJO","CHAR", 3, false, "null");

		addKey("AREA_REG_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmAreaRegistralCal();
	} /* getThisDBObj() */
} /* DboTmAreaRegistral */

