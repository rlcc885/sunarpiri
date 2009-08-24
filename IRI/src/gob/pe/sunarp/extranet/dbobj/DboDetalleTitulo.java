/*
* DboDetalleTitulo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboDetalleTitulo extends DBObject {

	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_NS_DETALLE = "NS_DETALLE";
	public static final String CAMPO_FG_ACTIVO = "FG_ACTIVO";
	public static final String CAMPO_MONTO_LIQ = "MONTO_LIQ";
	public static final String CAMPO_ESTADO_TITULO_ID = "ESTADO_TITULO_ID";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_PU_CTRL = "PU_CTRL";
	public static final String CAMPO_ES_TITU_CALI = "ES_TITU_CALI";

	public DboDetalleTitulo() throws DBException {
		super();
	} /* DboDetalleTitulo() */


	public DboDetalleTitulo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboDetalleTitulo(DBConnection) */


	public DboDetalleTitulo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DETALLE_TITULO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("DETALLE_TITULO");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","NUMBER", 22, false, "null");
		addField("NS_DETALLE","NUMBER", 5, false, "null");
		addField("FG_ACTIVO","CHAR", 1, false, "null");
		addField("MONTO_LIQ","NUMBER", 12, false, "null");
		addField("ESTADO_TITULO_ID","NUMBER", 22, false, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("TS_CREA","NUMBER", 22, true, "null");
		addField("PU_CTRL","CHAR", 2, true, "null");
		addField("ES_TITU_CALI","CHAR", 2, true, "null");

		addKey("NS_DETALLE");
		addKey("REFNUM_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboDetalleTitulo();
	} /* getThisDBObj() */
} /* DboDetalleTitulo */

