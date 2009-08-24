/*
* DboTarifa.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTarifa extends DBObject {

	public static final String CAMPO_TARIFA_ID = "TARIFA_ID";
	public static final String CAMPO_PORC_UIT = "PORC_UIT";
	public static final String CAMPO_PREC_OFIC = "PREC_OFIC";
	public static final String CAMPO_SERVICIO_ID = "SERVICIO_ID";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	//Tarifario
	public static final String CAMPO_COD_GRUPO_LIBRO_AREA = "COD_GRUPO_LIBRO_AREA";
	public DboTarifa() throws DBException {
		super();
	} /* DboTarifa() */


	public DboTarifa(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTarifa(DBConnection) */


	public DboTarifa(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TARIFA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TARIFA");

		setDescription("Object Description Goes Here");

		addField("TARIFA_ID","auto-inc", 0, false, "null");
		addField("PORC_UIT","NUMBER", 5, false, "null");
		addField("PREC_OFIC","NUMBER", 12, false, "null");
		addField("SERVICIO_ID","NUMBER", 22, false, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("COD_GRUPO_LIBRO_AREA","NUMBER", 22, false, "null");
		
		addKey("TARIFA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTarifa();
	} /* getThisDBObj() */
} /* DboTarifa */

