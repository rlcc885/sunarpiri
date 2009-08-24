/*
* DboRubroLibro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboRubroLibro extends DBObject {

	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_COD_RUBRO = "COD_RUBRO";

	public DboRubroLibro() throws DBException {
		super();
	} /* DboRubroLibro() */


	public DboRubroLibro(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboRubroLibro(DBConnection) */


	public DboRubroLibro(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* RUBRO_LIBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("RUBRO_LIBRO");

		setDescription("Object Description Goes Here");

		addField("COD_LIBRO","CHAR", 3, false, "null");
		addField("COD_RUBRO","CHAR", 3, false, "null");

		addKey("COD_LIBRO");
		addKey("COD_RUBRO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboRubroLibro();
	} /* getThisDBObj() */
} /* DboRubroLibro */

