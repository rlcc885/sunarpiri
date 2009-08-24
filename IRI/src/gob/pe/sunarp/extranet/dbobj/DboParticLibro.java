/*
* DboParticLibro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboParticLibro extends DBObject {

	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_COD_PARTIC = "COD_PARTIC";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_NOMBRE = "NOMBRE";

	public DboParticLibro() throws DBException {
		super();
	} /* DboParticLibro() */


	public DboParticLibro(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboParticLibro(DBConnection) */


	public DboParticLibro(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PARTIC_LIBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PARTIC_LIBRO");

		setDescription("Object Description Goes Here");

		addField("COD_LIBRO","CHAR", 3, false, "null");
		addField("COD_PARTIC","CHAR", 3, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("NOMBRE","VARCHAR", 50, false, "null");

		addKey("COD_LIBRO");
		addKey("COD_PARTIC");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboParticLibro();
	} /* getThisDBObj() */
} /* DboParticLibro */

