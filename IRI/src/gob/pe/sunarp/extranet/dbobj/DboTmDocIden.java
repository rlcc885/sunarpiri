/*
* DboTmDocIden.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmDocIden extends DBObject {

	public static final String CAMPO_TIPO_DOC_ID = "TIPO_DOC_ID";
	public static final String CAMPO_NOMBRE_ABREV = "NOMBRE_ABREV";
	public static final String CAMPO_TIPO_PER = "TIPO_PER";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmDocIden() throws DBException {
		super();
	} /* DboTmDocIden() */


	public DboTmDocIden(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmDocIden(DBConnection) */


	public DboTmDocIden(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_DOC_IDEN(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_DOC_IDEN");

		setDescription("Object Description Goes Here");

		addField("TIPO_DOC_ID","CHAR", 2, false, "CAMPO_TIPO_DOC_ID");
		addField("NOMBRE_ABREV","VARCHAR", 10, false, "CAMPO_NOMBRE_ABREV");
		addField("TIPO_PER","CHAR", 1, false, "CAMPO_TIPO_PER");
		addField("ESTADO","CHAR", 1, true, "CAMPO_ESTADO");
		addField("DESCRIPCION","VARCHAR", 40, true, "CAMPO_DESCRIPCION");

		addKey("TIPO_DOC_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmDocIden();
	} /* getThisDBObj() */
} /* DboTmDocIden */

