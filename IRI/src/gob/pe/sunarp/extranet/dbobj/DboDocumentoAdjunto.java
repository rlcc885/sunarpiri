/*
* DboDocumentoAdjunto.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboDocumentoAdjunto extends DBObject {

	public static final String CAMPO_DOCUMENTO_ID = "DOCUMENTO_ID";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_ATENCION_ID = "ATENCION_ID";
	public static final String CAMPO_TAMANO = "TAMANO";
	public static final String CAMPO_NOMBRE = "NOMBRE";

	public DboDocumentoAdjunto() throws DBException {
		super();
	} /* DboDocumentoAdjunto() */


	public DboDocumentoAdjunto(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboDocumentoAdjunto(DBConnection) */


	public DboDocumentoAdjunto(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DOCUMENTO_ADJUNTO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("DOCUMENTO_ADJUNTO");

		setDescription("Object Description Goes Here");

		addField("DOCUMENTO_ID","NUMBER", 22, false, "CAMPO_DOCUMENTO_ID");
		addField("TS_CREA","NUMBER", 22, false, "CAMPO_TS_CREA");
		addField("ATENCION_ID","NUMBER", 22, false, "CAMPO_ATENCION_ID");
		addField("TAMANO","CHAR", 5, false, "CAMPO_TAMANO");
		addField("NOMBRE","VARCHAR", 30, false, "CAMPO_NOMBRE");

		addKey("DOCUMENTO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboDocumentoAdjunto();
	} /* getThisDBObj() */
} /* DboDocumentoAdjunto */

