/*
* DboReservaRzSoc.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboReservaRzSoc extends DBObject {

	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_RAZ_SOC_RES = "RAZ_SOC_RES";
	public static final String CAMPO_ANO_TITU = "ANO_TITU";
	public static final String CAMPO_NUM_TITU = "NUM_TITU";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";

	public DboReservaRzSoc() throws DBException {
		super();
	} /* DboReservaRzSoc() */


	public DboReservaRzSoc(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboReservaRzSoc(DBConnection) */


	public DboReservaRzSoc(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* RESERVA_RZ_SOC(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("RESERVA_RZ_SOC");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","NUMBER", 22, false, "null");
		addField("RAZ_SOC_RES","VARCHAR", 250, true, "null");
		addField("ANO_TITU","CHAR", 4, true, "null");
		addField("NUM_TITU","CHAR", 8, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");

		addKey("REFNUM_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboReservaRzSoc();
	} /* getThisDBObj() */
} /* DboReservaRzSoc */

