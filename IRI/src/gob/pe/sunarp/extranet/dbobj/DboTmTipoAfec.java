/*
* DboTmTipoAfec.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmTipoAfec extends DBObject {

	public static final String CAMPO_COD_TIPO_AFEC = "COD_TIPO_AFEC";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_FG_INSCRIP = "FG_INSCRIP";

	public DboTmTipoAfec() throws DBException {
		super();
	} /* DboTmTipoAfec() */


	public DboTmTipoAfec(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmTipoAfec(DBConnection) */


	public DboTmTipoAfec(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_TIPO_AFEC(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_AFEC");

		setDescription("Object Description Goes Here");

		addField("COD_TIPO_AFEC","CHAR", 2, false, "CAMPO_COD_TIPO_AFEC");
		addField("DESCRIPCION","VARCHAR", 30, true, "CAMPO_DESCRIPCION");
		addField("FG_INSCRIP","CHAR", 1, true, "CAMPO_FG_INSCRIP");

		addKey("COD_TIPO_AFEC");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmTipoAfec();
	} /* getThisDBObj() */
} /* DboTmTipoAfec */

