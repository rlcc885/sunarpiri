package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTARepr extends DBObject{

	public static final String CAMPO_CO_NTIA      		= "CO_NTIA";
	public static final String CAMPO_CO_REPR 	  		= "CO_REPR";
	public static final String CAMPO_AP_PATE_REPR 	  	= "AP_PATE_REPR";
	public static final String CAMPO_AP_MATE_REPR       = "AP_MATE_REPR";
	public static final String CAMPO_NO_REPR      		= "NO_REPR";
	public static final String CAMPO_CO_PAIS            = "CO_PAIS";
	public static final String CAMPO_CO_DEPA      	    = "CO_DEPA";
	public static final String CAMPO_CO_PROV      		= "CO_PROV";
	public static final String CAMPO_CO_DIST     		= "CO_DIST";
	public static final String CAMPO_NO_DIRE            = "NO_DIRE";
	public static final String CAMPO_CO_TIPO      		= "CO_TIPO";
	public static final String CAMPO_NU_DOCU 			= "NU_DOCU";
	public static final String CAMPO_CO_TELF       		= "CO_TELF ";
	public static final String CAMPO_NU_TELF            = "NU_TELF";
	public static final String CAMPO_NU_CELU            = "NU_CELU";
	public static final String CAMPO_NO_MAIL            = "NO_MAIL";
	public static final String CAMPO_DE_MAIL            = "DE_MAIL";
	public static final String CAMPO_TI_ALTA            = "TI_ALTA";
	public static final String CAMPO_FE_ALTA 			= "FE_ALTA";
	public static final String CAMPO_FE_BAJA  		    = "FE_BAJA";
	public static final String CAMPO_DE_OBSV            = "DE_OBSV";
	public static final String CAMPO_IN_ESTD            = "IN_ESTD";
	public static final String CAMPO_ID_USUA_CREA       = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_CREA       = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_MODI       = "ID_USUA_MODI";
	public static final String CAMPO_TS_USUA_MODI      	= "TS_USUA_MODI";
	public static final String CAMPO_FL_ALTA     	    = "FL_ALTA";
	public static final String CAMPO_CO_TIPO_DOCU 	  	= "CO_TIPO_DOCU";
	public static final String CAMPO_NU_HOJA_TRAM       = "NU_HOJA_TRAM";
	public static final String CAMPO_FE_HOJA_TRAM     	= "FE_HOJA_TRAM";
	public static final String CAMPO_CO_PRES_SIR        = "CO_PRES_SIR";
	public static final String CAMPO_CO_PRES_RPV        = "CO_PRES_RPV";
	public static final String CAMPO_CO_SEDE_ACRE       = "CO_SEDE_ACRE";
	

	/**
	 * Constructor for DboTARepr
	 */
	public DboTARepr() throws DBException {
		super();
	}
	
	public DboTARepr(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection) */


	public DboTARepr(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection, String) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_REPR");

		setDescription("Object Description Goes Here");
		
		addField("CO_NTIA","CHAR", 5, false, "CO_NTIA");
		addField("CO_REPR","CHAR", 5, false, "CO_REPR");
		addField("AP_PATE_REPR","VARCHAR", 50, true, "AP_PATE_REPR");
		addField("AP_MATE_REPR","VARCHAR", 50, true, "AP_MATE_REPR");
		addField("NO_REPR","VARCHAR", 50, true, "NO_REPR");
		addField("CO_PAIS","CHAR", 2, true, "CO_PAIS");
		addField("CO_DEPA","CHAR", 2, true, "CO_DEPA");
		addField("CO_PROV","CHAR", 2, true, "CO_PROV");
		addField("CO_DIST","CHAR", 2, true, "CO_DIST");
		addField("NO_DIRE","VARCHAR", 100, true, "NO_DIRE");
		addField("CO_TIPO","CHAR", 3, true, "CO_TIPO");
		addField("NU_DOCU","CHAR", 20, true, "NU_DOCU");
		addField("CO_TELF ","CHAR", 3, true, "CO_TELF");
		addField("NU_TELF","NUMBER", 7, true, "NU_TELF");
		addField("NU_CELU","NUMBER", 7, true, "NU_CELU");
		addField("NO_MAIL","VARCHAR", 50, true, "NO_MAIL");
		addField("DE_MAIL","VARCHAR", 50, true, "DE_MAIL");
		addField("TI_ALTA","NUMBER", 22, true, "TI_ALTA");
		addField("FE_ALTA","NUMBER", 22, true, "FE_ALTA");
		addField("FE_BAJA","NUMBER", 22, true, "FE_BAJA");
		addField("DE_OBSV","VARCHAR", 100, true, "DE_OBSV");
		addField("IN_ESTD","CHAR", 1, true, "IN_ESTD");
		addField("ID_USUA_CREA","CHAR", 5, true, "ID_USUA_CREA");
		addField("TS_USUA_CREA","NUMBER", 22, true, "TS_USUA_CREA");
		addField("ID_USUA_MODI","CHAR", 5, true, "ID_USUA_MODI");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("FL_ALTA","CHAR", 1, true, "FL_ALTA");
		addField("CO_TIPO_DOCU","CHAR", 2, true, "CO_TIPO_DOCU");
		addField("NU_HOJA_TRAM","VARCHAR", 6, true, "NU_HOJA_TRAM");
		addField("FE_HOJA_TRAM","DATE", 22, true, "FE_HOJA_TRAM");
		addField("CO_PRES_SIR","CHAR", 3, true, "CO_PRES_SIR");
		addField("CO_PRES_RPV","CHAR", 5, true, "CO_PRES_RPV");
		addField("CO_SEDE_ACRE","CHAR", 4, true, "CO_SEDE_ACRE");
		
		addKey("CO_NTIA");
		addKey("CO_REPR");
		
		
	}

	public DBObject getThisDBObj() throws DBException {
        return new DboTARepr();
	} /* getThisDBObj() */
}

