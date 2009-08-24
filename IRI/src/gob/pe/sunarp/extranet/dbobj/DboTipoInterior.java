/*
* DboTipoInterior.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTipoInterior extends DBObject {

	public static final String CAMPO_TIPO_INTER = "TIPO_INTER";
	public static final String CAMPO_DESC_CORTA = "DESC_CORTA";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_DESC_LARGA = "DESC_LARGA";

	public DboTipoInterior() throws DBException {
		super();
	} /* DboTipoInterior() */


	public DboTipoInterior(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTipoInterior(DBConnection) */


	public DboTipoInterior(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TIPO_INTERIOR(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TIPO_INTERIOR");

		setDescription("Object Description Goes Here");

		addField("TIPO_INTER","CHAR", 2, false, "null");
		addField("DESC_CORTA","CHAR", 15, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("DESC_LARGA","CHAR", 45, true, "null");

		addKey("TIPO_INTER");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTipoInterior();
	} /* getThisDBObj() */
} /* DboTipoInterior */

