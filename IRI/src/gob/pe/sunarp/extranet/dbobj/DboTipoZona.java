/*
* DboTipoZona.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTipoZona extends DBObject {

	public static final String CAMPO_TIPO_ZONA = "TIPO_ZONA";
	public static final String CAMPO_DESC_CORTA = "DESC_CORTA";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_DESC_LARGA = "DESC_LARGA";

	public DboTipoZona() throws DBException {
		super();
	} /* DboTipoZona() */


	public DboTipoZona(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTipoZona(DBConnection) */


	public DboTipoZona(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TIPO_ZONA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TIPO_ZONA");

		setDescription("Object Description Goes Here");

		addField("TIPO_ZONA","CHAR", 2, false, "null");
		addField("DESC_CORTA","CHAR", 15, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("DESC_LARGA","CHAR", 45, true, "null");

		addKey("TIPO_ZONA");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTipoZona();
	} /* getThisDBObj() */
} /* DboTipoZona */

