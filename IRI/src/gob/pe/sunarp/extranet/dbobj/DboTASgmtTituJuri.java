package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTASgmtTituJuri extends DBObject {

	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_NU_SEQU           = "NU_SEQU";
	public static final String CAMPO_CO_EMPL           = "CO_EMPL";
	public static final String CAMPO_ES_TITU           = "ES_TITU";
	public static final String CAMPO_TI_REIN           = "TI_REIN";
	public static final String CAMPO_TS_OPER           = "TS_OPER";
	public static final String CAMPO_PU_CTRL           = "PU_CTRL";
	public static final String CAMPO_IN_MOST           = "IN_MOST";
	public static final String CAMPO_DE_OBSE           = "DE_OBSE";
	public static final String CAMPO_ES_TITU_CALI      = "ES_TITU_CALI";
	public static final String CAMPO_TS_USUA_CREA      = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA      = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_MODI      = "TS_USUA_MODI";
	public static final String CAMPO_ID_USUA_MODI      = "ID_USUA_MODI";
	public static final String CAMPO_TI_SITU_SGMT_TITU_JURI = "TI_SITU_SGMT_TITU_JURI";
	public static final String CAMPO_TS_OPER_CHAR           = "TS_OPER_CHAR";
	public static final String CAMPO_CO_SECC           = "CO_SECC";
    public static final String CAMPO_CO_ZONA           = "CO_ZONA";

	public DboTASgmtTituJuri() throws DBException {
		super();
	} /* DboTASgmtTituJuri() */


	public DboTASgmtTituJuri(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTASgmtTituJuri(DBConnection) */


	public DboTASgmtTituJuri(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTASgmtTituJuri(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_SGMT_TITU_JURI");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("NU_SEQU","CHAR", 4, true, "CAMPO_NU_SEQU");
		addField("CO_EMPL","CHAR", 5, true, "CAMPO_CO_EMPL");
		addField("ES_TITU","CHAR", 2, true, "CAMPO_ES_TITU");
		addField("TI_REIN","CHAR", 2, true, "CAMPO_TI_REIN");
		addField("TS_OPER","NUMBER", 22, true, "CAMPO_TS_OPER");
		addField("PU_CTRL","CHAR", 2, true, "CAMPO_PU_CTRL");
		addField("IN_MOST","CHAR", 1, true, "CAMPO_IN_MOST");
		addField("DE_OBSE","VARCHAR", 500, true, "CAMPO_DE_OBSE");
		addField("ES_TITU_CALI","CHAR", 2, true, "CAMPO_ES_TITU_CALI");
		addField("TS_USUA_CREA","NUMBER", 22, true, "CAMPO_TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "CAMPO_ID_USUA_CREA");
		addField("TS_USUA_MODI","NUMBER", 22, true, "CAMPO_TS_USUA_MODI");
		addField("ID_USUA_MODI","CHAR", 5, true, "CAMPO_ID_USUA_MODI");
		addField("TI_SITU_SGMT_TITU_JURI","CHAR", 1, true, "CAMPO_TI_SITU_SGMT_TITU_JURI");
		addField("TS_OPER_CHAR","CHAR", 8, true, "CAMPO_TS_OPER_CHAR");
		addField("CO_SECC","CHAR", 5, true, "CAMPO_CO_SECC");
		addField("CO_ZONA","CHAR", 2, true, "CAMPO_CO_ZONA");
		
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */
} /* DboTASgmtTituJuri */

