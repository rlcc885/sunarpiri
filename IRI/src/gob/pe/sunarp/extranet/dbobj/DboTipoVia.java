/*
* DboTipoVia.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTipoVia extends DBObject {

	public static final String CAMPO_TIPO_VIA = "TIPO_VIA";
	public static final String CAMPO_DESC_CORTA = "DESC_CORTA";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_DESC_LARGA = "DESC_LARGA";

	public DboTipoVia() throws DBException {
		super();
	} /* DboTipoVia() */


	public DboTipoVia(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTipoVia(DBConnection) */


	public DboTipoVia(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TIPO_VIA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TIPO_VIA");

		setDescription("Object Description Goes Here");

		addField("TIPO_VIA","CHAR", 2, false, "null");
		addField("DESC_CORTA","CHAR", 15, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("DESC_LARGA","CHAR", 45, true, "null");

		addKey("TIPO_VIA");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTipoVia();
	} /* getThisDBObj() */
} /* DboTipoVia */

