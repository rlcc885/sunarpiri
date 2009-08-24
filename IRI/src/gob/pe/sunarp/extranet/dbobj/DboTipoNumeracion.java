/*
* DboTipoNumeracion.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTipoNumeracion extends DBObject {

	public static final String CAMPO_TIPO_NUMER = "TIPO_NUMER";
	public static final String CAMPO_DESC_CORTA = "DESC_CORTA";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_DESC_LARGA = "DESC_LARGA";

	public DboTipoNumeracion() throws DBException {
		super();
	} /* DboTipoNumeracion() */


	public DboTipoNumeracion(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTipoNumeracion(DBConnection) */


	public DboTipoNumeracion(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TIPO_NUMERACION(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TIPO_NUMERACION");

		setDescription("Object Description Goes Here");

		addField("TIPO_NUMER","CHAR", 2, false, "null");
		addField("DESC_CORTA","CHAR", 15, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("DESC_LARGA","CHAR", 45, true, "null");

		addKey("TIPO_NUMER");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTipoNumeracion();
	} /* getThisDBObj() */
} /* DboTipoNumeracion */

