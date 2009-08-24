/*
* DboGrupoLibro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboGrupoLibroArea extends DBObject {

	public static final String CAMPO_COD_GRUPO_LIBRO_AREA = "COD_GRUPO_LIBRO_AREA";
	public static final String CAMPO_COD_AREA = "COD_AREA";
	public static final String CAMPO_DESC_GRUPO_LIBRO_AREA = "DESC_GRUPO_LIBRO_AREA";
	public static final String CAMPO_FLAG_ACTIVO = "FLAG_ACTIVO";
	

	public DboGrupoLibroArea() throws DBException {
		super();
	} /* DboGrupoLibro() */


	public DboGrupoLibroArea(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboGrupoLibro(DBConnection) */


	public DboGrupoLibroArea(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* GRUPO_LIBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("GRUPO_LIBRO_AREA");

		setDescription("Object Description Goes Here");

		addField("COD_GRUPO_LIBRO_AREA","NUMBER", 22, false, "CAMPO_COD_GRUPO_LIBRO_AREA");
		addField("COD_AREA","CHAR", 5, false, "CAMPO_COD_AREA");
		addField("FLAG_ACTIVO","CHAR", 1, false, "CAMPO_FLAG_ACTIVO");
		addField("DESC_GRUPO_LIBRO_AREA","VARCHAR",50, false, "CAMPO_DESC_GRUPO_LIBRO_AREA");

		addKey("COD_GRUPO_LIBRO_AREA");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboGrupoLibroArea();
	} /* getThisDBObj() */
} /* DboGrupoLibro */

