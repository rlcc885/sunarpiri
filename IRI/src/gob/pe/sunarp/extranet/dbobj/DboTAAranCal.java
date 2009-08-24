/*
* DboTAAranCal.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTAAranCal extends DBObject {

	public static final String CAMPO_CO_REGI      = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST = "CO_OFIC_RGST";
	public static final String CAMPO_CO_TASA      = "CO_TASA";
	public static final String CAMPO_CO_CPTO      = "CO_CPTO";
	public static final String CAMPO_ID_ARAN      = "ID_ARAN";
	public static final String CAMPO_NS_ARAN      = "NS_ARAN";
	public static final String CAMPO_PO_UIT       = "PO_UIT";
	public static final String CAMPO_MO_CPTO      = "MO_CPTO";
	public static final String CAMPO_IN_FRML      = "IN_FRML";
	public static final String CAMPO_RANGO_MIN    = "RANGO_MIN";
	public static final String CAMPO_RANGO_MAX    = "RANGO_MAX";
	public static final String CAMPO_VALOR_MIN    = "VALOR_MIN";
	public static final String CAMPO_VALOR_MAX    = "VALOR_MAX";
	public static final String CAMPO_IN_ESTD      = "IN_ESTD";
	public static final String CAMPO_FRML         = "FRML";
	

	public DboTAAranCal() throws DBException {
		super();
	} /* DboTAAranCal() */


	public DboTAAranCal(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTAAranCal(DBConnection) */


	public DboTAAranCal(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} 


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_ARAN_CAL");

		setDescription("Object Description Goes Here");

		addField("CO_REGI","CHAR", 2, false, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, false, "CAMPO_CO_OFIC_RGST");
		addField("CO_TASA","CHAR", 4, false, "CAMPO_CO_TASA");
		addField("CO_CPTO","CHAR", 4, false, "CAMPO_CO_CPTO");
		addField("ID_ARAN","CHAR", 6, false, "CAMPO_ID_ARAN");

		addField("NS_ARAN","NUMBER", 22, true, "CAMPO_NS_ARAN");
		addField("PO_UIT","NUMBER", 22, true, "CAMPO_PO_UIT");
		addField("MO_CPTO","NUMBER", 22, true, "CAMPO_MO_CPTO");
		addField("IN_FRML","CHAR", 1, true, "CAMPO_IN_FRML");
		addField("RANGO_MIN","NUMBER", 22, true, "CAMPO_RANGO_MIN");
		addField("RANGO_MAX","NUMBER", 22, true, "CAMPO_RANGO_MAX");
		addField("VALOR_MIN","NUMBER", 22, true, "CAMPO_VALOR_MIN");
		addField("VALOR_MAX","NUMBER", 22, true, "CAMPO_VALOR_MAX");
		addField("IN_ESTD","CHAR", 1, true, "CAMPO_IN_ESTD");
		addField("FRML","VARCHAR2", 250, true, "CAMPO_FRML");

		addKey("CO_REGI");
		addKey("CO_OFIC_RGST");
		addKey("CO_TASA");
		addKey("CO_CPTO");
		addKey("ID_ARAN");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTAAranCal();
	} /* getThisDBObj() */
} /* DboTAAranCal */



