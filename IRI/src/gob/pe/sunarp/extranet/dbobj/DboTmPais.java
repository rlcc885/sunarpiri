/*
* DboTmPais.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmPais extends DBObject {

	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_GENTILICIO = "GENTILICIO";
	

	public DboTmPais() throws DBException {
		super();
	} /* DboTmPais() */


	public DboTmPais(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmPais(DBConnection) */


	public DboTmPais(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_PAIS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_PAIS");

		setDescription("Object Description Goes Here");

		addField("PAIS_ID","CHAR", 2, false, "null");
		addField("NOMBRE","VARCHAR", 30, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("GENTILICIO","VARCHAR", 50, false, "null");


		addKey("PAIS_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmPais();
	} /* getThisDBObj() */
} /* DboTmPais */

