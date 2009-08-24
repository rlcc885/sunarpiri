/*
* DboTmLibro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmLibroCal extends DBObject {

	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmLibroCal() throws DBException {
		super();
	} /* DboTmLibro() */




	public DboTmLibroCal(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmLibro(DBConnection) */




	public DboTmLibroCal(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_LIBRO(DBConnection, String) */




	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_LIBRO_CAL");

		setDescription("Object Description Goes Here");

		addField("COD_LIBRO","CHAR", 3, false, "null");
		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("DESCRIPCION","VARCHAR", 120, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");

		addKey("COD_LIBRO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmLibroCal();
	} /* getThisDBObj() */
} /* DboTmLibro */

