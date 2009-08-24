/*
* DboRazSocPj.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboRazSocPj extends DBObject {

	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NS_NOMBRE = "NS_NOMBRE";
	public static final String CAMPO_RAZON_SOC = "RAZON_SOC";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_SIGLAS = "SIGLAS";
	public static final String CAMPO_NU_FOJA = "NU_FOJA";
	public static final String CAMPO_NU_TOMO = "NU_TOMO";
	public static final String CAMPO_NU_ORIG_PART = "NU_ORIG_PART";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboRazSocPj() throws DBException {
		super();
	} /* DboRazSocPj() */


	public DboRazSocPj(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboRazSocPj(DBConnection) */


	public DboRazSocPj(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* RAZ_SOC_PJ(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("RAZ_SOC_PJ");

		setDescription("Object Description Goes Here");

		addField("REFNUM_PART","NUMBER", 22, false, "null");
		addField("NS_NOMBRE","NUMBER", 3, false, "null");
		addField("RAZON_SOC","VARCHAR", 250, false, "null");
		addField("ESTADO","CHAR", 1, false, "null");
		addField("SIGLAS","VARCHAR", 60, true, "null");
		addField("NU_FOJA","CHAR", 6, true, "null");
		addField("NU_TOMO","CHAR", 6, true, "null");
		addField("NU_ORIG_PART","CHAR", 10, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("NS_NOMBRE");
		addKey("REFNUM_PART");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboRazSocPj();
	} /* getThisDBObj() */
} /* DboRazSocPj */

