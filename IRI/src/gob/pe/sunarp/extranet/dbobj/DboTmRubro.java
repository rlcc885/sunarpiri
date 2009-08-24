/*
* DboTmRubro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmRubro extends DBObject {

	public static final String CAMPO_COD_RUBRO = "COD_RUBRO";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_LETRA = "LETRA";

	public DboTmRubro() throws DBException {
		super();
	} /* DboTmRubro() */


	public DboTmRubro(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmRubro(DBConnection) */


	public DboTmRubro(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_RUBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_RUBRO");

		setDescription("Object Description Goes Here");

		addField("COD_RUBRO","CHAR", 3, false, "null");
		addField("NOMBRE","VARCHAR", 50, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("LETRA","CHAR", 1, false, "null");

		addKey("COD_RUBRO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmRubro();
	} /* getThisDBObj() */
} /* DboTmRubro */

