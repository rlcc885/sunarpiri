/*
* DboTmCondVehi.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmCondVehi extends DBObject {

	public static final String CAMPO_COD_COND_VEHI = "COD_COND_VEHI";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmCondVehi() throws DBException {
		super();
	} /* DboTmCondVehi() */


	public DboTmCondVehi(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmCondVehi(DBConnection) */


	public DboTmCondVehi(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_COND_VEHI(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_COND_VEHI");

		setDescription("Object Description Goes Here");

		addField("COD_COND_VEHI","CHAR", 2, false, "CAMPO_COD_COND_VEHI");
		addField("DESCRIPCION","VARCHAR", 30, true, "CAMPO_DESCRIPCION");

		addKey("COD_COND_VEHI");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmCondVehi();
	} /* getThisDBObj() */
} /* DboTmCondVehi */

