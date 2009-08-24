/*
* DboTmDepartamento.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmDepartamento extends DBObject {

	public static final String CAMPO_DPTO_ID = "DPTO_ID";
	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmDepartamento() throws DBException {
		super();
	} /* DboTmDepartamento() */


	public DboTmDepartamento(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmDepartamento(DBConnection) */


	public DboTmDepartamento(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_DEPARTAMENTO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_DEPARTAMENTO");

		setDescription("Object Description Goes Here");

		addField("DPTO_ID","CHAR", 2, false, "null");
		addField("PAIS_ID","CHAR", 2, false, "null");
		addField("NOMBRE","VARCHAR", 30, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");

		addKey("DPTO_ID");
		addKey("PAIS_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmDepartamento();
	} /* getThisDBObj() */
} /* DboTmDepartamento */

