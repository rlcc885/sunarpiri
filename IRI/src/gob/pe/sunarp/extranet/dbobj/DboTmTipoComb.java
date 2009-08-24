/*
* DboTmTipoComb.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmTipoComb extends DBObject {

	public static final String CAMPO_COD_TIPO_COMB = "COD_TIPO_COMB";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmTipoComb() throws DBException {
		super();
	} /* DboTmTipoComb() */


	public DboTmTipoComb(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmTipoComb(DBConnection) */


	public DboTmTipoComb(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_TIPO_COMB(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_COMB");

		setDescription("Object Description Goes Here");

		addField("COD_TIPO_COMB","CHAR", 2, false, "CAMPO_COD_TIPO_COMB");
		addField("DESCRIPCION","VARCHAR", 30, true, "CAMPO_DESCRIPCION");

		addKey("COD_TIPO_COMB");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmTipoComb();
	} /* getThisDBObj() */
} /* DboTmTipoComb */

