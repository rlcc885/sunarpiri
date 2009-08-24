/*
* DboGrupoLibro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboGrupoLibroAreaDet extends DBObject {

	public static final String CAMPO_COD_GRUPO_LIBRO_AREA_DET = "COD_GRUPO_LIBRO_AREA_DET";
	public static final String CAMPO_COD_GRUPO_LIBRO_AREA = "COD_GRUPO_LIBRO_AREA";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";

	public DboGrupoLibroAreaDet() throws DBException {
		super();
	} /* DboGrupoLibro() */


	public DboGrupoLibroAreaDet(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboGrupoLibro(DBConnection) */


	public DboGrupoLibroAreaDet(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* GRUPO_LIBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("GRUPO_LIBRO_AREA_DET");

		setDescription("Object Description Goes Here");

		addField("COD_GRUPO_LIBRO_AREA_DET","NUMBER", 22, false, "CAMPO_COD_GRUPO_LIBRO_AREA_DET");
		addField("COD_GRUPO_LIBRO_AREA","NUMBER", 22, false, "CAMPO_COD_GRUPO_LIBRO_AREA");
		addField("COD_LIBRO","CHAR", 3, false, "CAMPO_COD_LIBRO");

		addKey("COD_GRUPO_LIBRO_AREA_DET");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboGrupoLibro();
	} /* getThisDBObj() */
} /* DboGrupoLibro */

