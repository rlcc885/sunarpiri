/*
* DboActosTitulo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboActosTitulo extends DBObject {

	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_COD_ACTO = "COD_ACTO";
	public static final String CAMPO_NS_ACTO_TITU = "NS_ACTO_TITU";
	public static final String CAMPO_NU_PAG_FIN = "NU_PAG_FIN";
	public static final String CAMPO_NU_PAG_INI = "NU_PAG_INI";
	public static final String CAMPO_COD_RUBRO = "COD_RUBRO";
	public static final String CAMPO_MONTO_TOT_ACTO = "MONTO_TOT_ACTO";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_NU_ASIENTO = "NU_ASIENTO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_CO_ACTO_RGST_ORIG = "CO_ACTO_RGST_ORIG";

	public DboActosTitulo() throws DBException {
		super();
	} /* DboActosTitulo() */


	public DboActosTitulo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboActosTitulo(DBConnection) */


	public DboActosTitulo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ACTOS_TITULO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("ACTOS_TITULO");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","NUMBER", 22, false, "null");
		addField("COD_ACTO","CHAR", 5, false, "null");
		addField("NS_ACTO_TITU","NUMBER", 5, false, "null");
		addField("NU_PAG_FIN","NUMBER", 22, true, "null");
		addField("NU_PAG_INI","NUMBER", 22, true, "null");
		addField("COD_RUBRO","CHAR", 3, true, "null");
		addField("MONTO_TOT_ACTO","NUMBER", 12, false, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("NU_ASIENTO","NUMBER", 5, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		addField("CO_ACTO_RGST_ORIG","CHAR", 5, true, "null");

		addKey("COD_ACTO");
		addKey("NS_ACTO_TITU");
		addKey("REFNUM_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboActosTitulo();
	} /* getThisDBObj() */
} /* DboActosTitulo */

