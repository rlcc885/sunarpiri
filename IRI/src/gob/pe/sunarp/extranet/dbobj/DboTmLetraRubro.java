/*
* DboTmLetraRubro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmLetraRubro extends DBObject {

	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_LETRA_RUBRO = "LETRA_RUBRO";
	public static final String CAMPO_DESC_RUBRO = "DESC_RUBRO";

	public DboTmLetraRubro() throws DBException {
		super();
	} /* DboTmLetraRubro() */


	public DboTmLetraRubro(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmLetraRubro(DBConnection) */


	public DboTmLetraRubro(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_LETRA_RUBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_LETRA_RUBRO");

		setDescription("Object Description Goes Here");

		addField("COD_LIBRO","CHAR", 3, false, "CAMPO_COD_LIBRO");
		addField("LETRA_RUBRO","CHAR", 1, false, "CAMPO_LETRA_RUBRO");
		addField("DESC_RUBRO","VARCHAR", 50, false, "CAMPO_DESC_RUBRO");

		addKey("COD_LIBRO");
		addKey("LETRA_RUBRO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmLetraRubro();
	} /* getThisDBObj() */
} /* DboTmLetraRubro */

