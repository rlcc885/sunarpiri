/*
* DboTmTipoEsquela.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmTipoEsquela extends DBObject {

	public static final String CAMPO_TIPO_ESQ = "TIPO_ESQ";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_ESTADO = "ESTADO";

	public DboTmTipoEsquela() throws DBException {
		super();
	} /* DboTmTipoEsquela() */


	public DboTmTipoEsquela(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmTipoEsquela(DBConnection) */


	public DboTmTipoEsquela(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_TIPO_ESQUELA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_ESQUELA");

		setDescription("Object Description Goes Here");

		addField("TIPO_ESQ","CHAR", 1, false, "null");
		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("DESCRIPCION","VARCHAR", 30, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");

		addKey("AREA_REG_ID");
		addKey("TIPO_ESQ");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmTipoEsquela();
	} /* getThisDBObj() */
} /* DboTmTipoEsquela */

