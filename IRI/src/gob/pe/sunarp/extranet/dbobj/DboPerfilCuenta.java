/*
* DboPerfilCuenta.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPerfilCuenta extends DBObject {

	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_PERFIL_ID = "PERFIL_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_TS_ULT_MODIF = "TS_ULT_MODIF";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_NIVEL_ACCESO_ID = "NIVEL_ACCESO_ID";

	public DboPerfilCuenta() throws DBException {
		super();
	} /* DboPerfilCuenta() */


	public DboPerfilCuenta(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPerfilCuenta(DBConnection) */


	public DboPerfilCuenta(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PERFIL_CUENTA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PERFIL_CUENTA");

		setDescription("Object Description Goes Here");

		addField("CUENTA_ID","NUMBER", 22, false, "null");
		addField("PERFIL_ID","NUMBER", 22, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, false, "null");
		addField("USR_CREA","CHAR", 15, false, "null");
		addField("TS_ULT_MODIF","NUMBER", 22, false, "null");
		addField("TS_CREA","NUMBER", 22, false, "null");
		addField("NIVEL_ACCESO_ID","NUMBER", 22, false, "null");

		addKey("CUENTA_ID");
		addKey("PERFIL_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPerfilCuenta();
	} /* getThisDBObj() */
} /* DboPerfilCuenta */

