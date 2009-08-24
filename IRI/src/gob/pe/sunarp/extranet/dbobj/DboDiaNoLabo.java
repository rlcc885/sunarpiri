/*
* DboAbono.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboDiaNoLabo extends DBObject {

	public static final String CAMPO_ID_DIA = "ID_DIA";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_DIA = "DIA";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	public static final String CAMPO_DOCU_SUST = "DOCU_SUST";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_ID_USUA_CREA = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_CREA = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_MODI = "ID_USUA_MODI";
	public static final String CAMPO_TS_USUA_MODI = "TS_USUA_MODI";
	//public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";

	public DboDiaNoLabo() throws DBException {
		super();
	} /* DboAbono() */


	public DboDiaNoLabo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAbono(DBConnection) */


	public DboDiaNoLabo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ABONO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_DIA_NO_LABO_NACIONAL");

		setDescription("Descripcion de Objetos de la tabla TM_DIA_NO_LABO_NACIONAL");

		addField("ID_DIA","auto-inc", 0, false, "null");
		addField("REG_PUB_ID","CHAR", 0, true, "null");
		addField("OFIC_REG_ID","CHAR", 0, true, "null");
		addField("DIA","NUMBER", 22, true, "null");
		addField("DESCRIPCION","VARCHAR", 4000, true, "null");
		addField("DOCU_SUST","VARCHAR", 4000, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("ID_USUA_CREA","VARCHAR", 15, true, "null");
		addField("TS_USUA_CREA","NUMBER", 22, true, "null");
		addField("ID_USUA_MODI","VARCHAR", 15, true, "null");
		addField("TS_USUA_MODI","NUMBER", 22, true, "null");
		//addField("ID_DIA","auto-inc", 0, false, "null");
		
		addKey("ID_DIA");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboDiaNoLabo();
	} /* getThisDBObj() */
} /* DboAbono */

