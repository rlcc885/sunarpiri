/*
* DboCuenta.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboCuenta extends DBObject {

	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_USR_ID = "USR_ID";
	public static final String CAMPO_TIPO_USR = "TIPO_USR";
	public static final String CAMPO_EXON_PAGO = "EXON_PAGO";
	public static final String CAMPO_FG_NEW_USR_VENT = "FG_NEW_USR_VENT";
	public static final String CAMPO_TS_ULT_ACC = "TS_ULT_ACC";
	public static final String CAMPO_FG_REC_MAIL = "FG_REC_MAIL";
	public static final String CAMPO_RESP_SECRETA = "RESP_SECRETA";
	public static final String CAMPO_CLAVE = "CLAVE";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_PREG_SEC_ID = "PREG_SEC_ID";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_PE_NATU_ID = "PE_NATU_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_FG_SYNC_TAM = "FG_SYNC_TAM";
	/*DESCAJ 29/12/2006  INICIO*/
	public static final String CAMPO_TS_CAD_CLAVE = "TS_CAD_CLAVE";
	/*DESCAJ 29/12/2006  FIN*/

	public DboCuenta() throws DBException {
		super();
	} /* DboCuenta() */


	public DboCuenta(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboCuenta(DBConnection) */


	public DboCuenta(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CUENTA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CUENTA");

		setDescription("Object Description Goes Here");

		addField("CUENTA_ID","auto-inc", 0, false, "null");
		addField("USR_ID","VARCHAR", 15, false, "null");
		addField("TIPO_USR","CHAR", 4, false, "null");
		addField("EXON_PAGO","CHAR", 1, false, "null");
		addField("FG_NEW_USR_VENT","CHAR", 1, false, "null");
		addField("TS_ULT_ACC","NUMBER", 22, false, "null");
		addField("FG_REC_MAIL","CHAR", 1, false, "null");
		addField("RESP_SECRETA","VARCHAR", 30, true, "null");
		addField("CLAVE","VARCHAR", 10, false, "null");
		addField("TS_CREA","NUMBER", 22, false, "null");
		addField("PREG_SEC_ID","NUMBER", 22, false, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("PE_NATU_ID","NUMBER", 22, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("FG_SYNC_TAM","CHAR", 1, true, "null");
		/*DESCAJ 29/12/2006  INICIO*/
		addField("TS_CAD_CLAVE","NUMBER",22, true, "null");
		/*DESCAJ 29/12/2006  FIN*/

		addKey("CUENTA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboCuenta();
	} /* getThisDBObj() */
} /* DboCuenta */

