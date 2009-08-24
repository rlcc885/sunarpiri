/*
* DboCertificadoLibro.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboCertificadoLibro extends DBObject {

	public static final String CAMPO_CERTIFICADO_ID = "CERTIFICADO_ID";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";

	public DboCertificadoLibro() throws DBException {
		super();
	} /* DboCertificadoLibro() */


	public DboCertificadoLibro(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboCertificadoLibro(DBConnection) */


	public DboCertificadoLibro(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CERTIFICADO_LIBRO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CERTIFICADO_LIBRO");

		setDescription("Object Description Goes Here");

		addField("CERTIFICADO_ID","NUMBER", 22, false, "CAMPO_CERTIFICADO_ID");
		addField("COD_LIBRO","CHAR", 3, false, "CAMPO_COD_LIBRO");

		addKey("CERTIFICADO_ID");
		addKey("COD_LIBRO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboCertificadoLibro();
	} /* getThisDBObj() */
} /* DboCertificadoLibro */

