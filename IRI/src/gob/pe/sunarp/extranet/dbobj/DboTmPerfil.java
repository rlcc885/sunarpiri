/*
* DboTmPerfil.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmPerfil extends DBObject {

	public static final String CAMPO_PERFIL_ID = "PERFIL_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_ABREV = "ABREV";
	public static final String CAMPO_FG_INTERNO = "FG_INTERNO";
	public static final String CAMPO_NOMBRE_TAM = "NOMBRE_TAM";

	public DboTmPerfil() throws DBException {
		super();
	} /* DboTmPerfil() */


	public DboTmPerfil(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmPerfil(DBConnection) */


	public DboTmPerfil(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_PERFIL(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_PERFIL");

		setDescription("Object Description Goes Here");

		addField("PERFIL_ID","auto-inc", 0, false, "null");
		addField("NOMBRE","VARCHAR", 35, false, "null");
		addField("DESCRIPCION","VARCHAR", 50, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("ABREV","CHAR", 2, false, "null");
		addField("FG_INTERNO","CHAR", 1, false, "null");
		addField("NOMBRE_TAM","VARCHAR", 15, true, "null");

		addKey("PERFIL_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmPerfil();
	} /* getThisDBObj() */
} /* DboTmPerfil */

