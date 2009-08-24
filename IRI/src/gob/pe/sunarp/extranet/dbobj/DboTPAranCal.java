/*
* DboTPAranCal.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTPAranCal extends DBObject {

	public static final String CAMPO_ID_ARAN      = "ID_ARAN";
	public static final String CAMPO_FE_INIC      = "FE_INIC";
	public static final String CAMPO_FE_FINA      = "FE_FINA";
	public static final String CAMPO_IN_ESTD      = "IN_ESTD";
	public static final String CAMPO_VA_UIT       = "VA_UIT";
	public static final String CAMPO_PO_LEY       = "PO_LEY";

	public DboTPAranCal() throws DBException {
		super();
	} /* DboTPAranCal() */


	public DboTPAranCal(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPAranCal(DBConnection) */


	public DboTPAranCal(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} 


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TP_ARAN_CAL");

		setDescription("Object Description Goes Here");

		addField("ID_ARAN","CHAR", 6, false, "CAMPO_ID_ARAN");

		addField("FE_INIC","NUMBER", 22, true, "CAMPO_FE_INIC");
		addField("FE_FINA","NUMBER", 22, true, "CAMPO_FE_FINA");
		addField("IN_ESTD","CHAR", 1, true, "CAMPO_IN_ESTD");
		addField("VA_UIT","NUMBER", 22, true, "CAMPO_VA_UIT");
		addField("PO_LEY","NUMBER", 22, true, "CAMPO_PO_LEY");
		
		addKey("ID_ARAN");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTPAranCal();
	} /* getThisDBObj() */
} /* DboTPAranCal */





