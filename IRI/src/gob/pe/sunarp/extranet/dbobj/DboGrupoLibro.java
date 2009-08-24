/*
* DboGrupoLibro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboGrupoLibro extends DBObject {

	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_GRUPO_ID = "GRUPO_ID";

	public DboGrupoLibro() throws DBException {
		super();
	} /* DboGrupoLibro() */


	public DboGrupoLibro(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboGrupoLibro(DBConnection) */


	public DboGrupoLibro(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* GRUPO_LIBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("GRUPO_LIBRO");

		setDescription("Object Description Goes Here");

		addField("COD_LIBRO","CHAR", 3, false, "CAMPO_COD_LIBRO");
		addField("GRUPO_ID","NUMBER", 22, false, "CAMPO_GRUPO_ID");

		addKey("COD_LIBRO");
		addKey("GRUPO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboGrupoLibro();
	} /* getThisDBObj() */
} /* DboGrupoLibro */

