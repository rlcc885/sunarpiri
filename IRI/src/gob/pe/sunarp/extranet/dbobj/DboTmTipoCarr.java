/*
* DboTmTipoCarr.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmTipoCarr extends DBObject {

	public static final String CAMPO_COD_TIPO_CARR = "COD_TIPO_CARR";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmTipoCarr() throws DBException {
		super();
	} /* DboTmTipoCarr() */


	public DboTmTipoCarr(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmTipoCarr(DBConnection) */


	public DboTmTipoCarr(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_TIPO_CARR(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_CARR");

		setDescription("Object Description Goes Here");

		addField("COD_TIPO_CARR","CHAR", 5, false, "CAMPO_COD_TIPO_CARR");
		addField("DESCRIPCION","VARCHAR", 30, true, "CAMPO_DESCRIPCION");

		addKey("COD_TIPO_CARR");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmTipoCarr();
	} /* getThisDBObj() */
} /* DboTmTipoCarr */

