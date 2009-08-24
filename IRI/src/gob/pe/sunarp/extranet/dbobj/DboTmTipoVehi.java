/*
* DboTmTipoVehi.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmTipoVehi extends DBObject {

	public static final String CAMPO_COD_TIPO_VEHI = "COD_TIPO_VEHI";
	public static final String CAMPO_FG_MINUSVALIDO = "FG_MINUSVALIDO";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmTipoVehi() throws DBException {
		super();
	} /* DboTmTipoVehi() */


	public DboTmTipoVehi(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmTipoVehi(DBConnection) */


	public DboTmTipoVehi(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_TIPO_VEHI(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_VEHI");

		setDescription("Object Description Goes Here");

		addField("COD_TIPO_VEHI","CHAR", 1, false, "CAMPO_COD_TIPO_VEHI");
		addField("FG_MINUSVALIDO","CHAR", 1, true, "CAMPO_FG_MINUSVALIDO");
		addField("DESCRIPCION","VARCHAR", 30, true, "CAMPO_DESCRIPCION");

		addKey("COD_TIPO_VEHI");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmTipoVehi();
	} /* getThisDBObj() */
} /* DboTmTipoVehi */

