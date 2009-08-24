package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTPNtia extends DBObject {

	public static final String CAMPO_CO_NTIA      		= "CO_NTIA";
	public static final String CAMPO_DE_NTIA 	  		= "DE_NTIA";
	public static final String CAMPO_CO_PAIS 	  		= "CO_PAIS";
	public static final String CAMPO_CO_DEPA       		= "CO_DEPA";
	public static final String CAMPO_CO_PROV      		= "CO_PROV";
	public static final String CAMPO_CO_DIST            = "CO_DIST";
	public static final String CAMPO_NO_DIRE      	    = "NO_DIRE";
	public static final String CAMPO_CO_TELF      		= "CO_TELF";
	public static final String CAMPO_NU_TEL1     		= "NU_TEL1";
	public static final String CAMPO_NU_ANX1            = "NU_ANX1";
	public static final String CAMPO_NU_TEL2       		= "NU_TEL2";
	public static final String CAMPO_NU_ANX2 			= "NU_ANX2";
	public static final String CAMPO_NU_FAX       		= "NU_FAX ";
	public static final String CAMPO_NU_ANXF            = "NU_ANXF";
	public static final String CAMPO_NO_WEB             = "NO_WEB";
	public static final String CAMPO_DE_OBSV            = "DE_OBSV";
	public static final String CAMPO_CO_ORLC            = "CO_ORLC";
	public static final String CAMPO_CO_VEHI            = "CO_VEHI";
	public static final String CAMPO_IN_ESTD 			= "IN_ESTD";
	public static final String CAMPO_TS_USUA_CREA  		= "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA       = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_MODI       = "TS_USUA_MODI";
	public static final String CAMPO_ID_USUA_MODI       = "ID_USUA_MODI";
	public static final String CAMPO_AP_PATE            = "AP_PATE";
	public static final String CAMPO_AP_MATE            = "AP_MATE";
	public static final String CAMPO_DE_NOMB      	    = "DE_NOMB";
	public static final String CAMPO_FE_NOMB_NOTA     	= "FE_NOMB_NOTA";
	public static final String CAMPO_NU_RESO_NOTA 	  	= "NU_RESO_NOTA";
	public static final String CAMPO_NU_COLE_NOTA       = "NU_COLE_NOTA";
	public static final String CAMPO_NU_COLE      		= "NU_COLE";
	public static final String CAMPO_NO_MAIL            = "NO_MAIL";
	public static final String CAMPO_CO_DECA      	    = "CO_DECA";
	public static final String CAMPO_FE_ALTA            = "FE_ALTA";
	public static final String CAMPO_FE_BAJA            = "FE_BAJA";
	public static final String CAMPO_CUR                = "CUR";
	public static final String CAMPO_PERSONA_ID         = "PERSONA_ID";
	
	
	/**
	 * Constructor for DboTPNtia
	 */
	public DboTPNtia() throws DBException {
		super();
	}

	public DboTPNtia(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection) */


	public DboTPNtia(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection, String) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("TP_NTIA");

		setDescription("Object Description Goes Here");
		
		addField("CO_NTIA","CHAR", 5, false, "CO_NTIA");
		addField("DE_NTIA","VARCHAR", 50, true, "DE_NTIA");
		addField("CO_PAIS","CHAR", 2, true, "CO_PAIS");
		addField("CO_DEPA","CHAR", 2, true, "CO_DEPA");
		addField("CO_PROV","CHAR", 2, true, "CO_PROV");
		addField("CO_DIST","CHAR", 2, true, "CO_DIST");
		addField("NO_DIRE","VARCHAR", 100, true, "NO_DIRE");
		addField("CO_TELF","CHAR", 3, true, "CO_TELF");
		addField("NU_TEL1","NUMBER", 7, true, "NU_TEL1");
		addField("NU_ANX1","NUMBER", 5, true, "NU_ANX1");
		addField("NU_TEL2","NUMBER", 7, true, "NU_TEL2");
		addField("NU_ANX2","NUMBER", 5, true, "NU_ANX2");
		addField("NO_WEB","NUMBER", 7, true, "NO_WEB");
		addField("NU_ANXF","NUMBER", 5, true, "NU_ANXF");
		addField("NO_WEB","VARCHAR", 50, true, "NO_WEB");
		addField("DE_OBSV","VARCHAR", 500, true, "DE_OBSV");
		addField("CO_ORLC","CHAR", 3, true, "CO_ORLC");
		addField("CO_VEHI","CHAR", 5, true, "CO_VEHI");
		addField("IN_ESTD","CHAR", 1, true, "IN_ESTD");
		addField("TS_USUA_CREA","NUMBER", 22, true, "TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "ID_USUA_CREA");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("ID_USUA_MODI","CHAR", 5, true, "ID_USUA_MODI");
		addField("AP_PATE","VARCHAR", 50, true, "AP_PATE");
		addField("AP_MATE","VARCHAR", 50, true, "AP_MATE");
		addField("DE_NOMB","VARCHAR", 50, true, "DE_NOMB");
		addField("FE_NOMB_NOTA","DATE", 22, true, "FE_NOMB_NOTA");
		addField("NU_RESO_NOTA","VARCHAR", 50, true, "NU_RESO_NOTA");
		addField("NU_COLE_NOTA","CHAR", 20, true, "NU_COLE_NOTA");
		addField("NU_COLE","CHAR", 2, true, "NU_COLE");
		addField("NO_MAIL","VARCHAR", 50, true, "NO_MAIL");
		addField("CO_DECA","CHAR", 1, true, "CO_DECA");
		addField("FE_ALTA","NUMBER", 30, true, "FE_ALTA");
		addField("FE_BAJA","NUMBER", 30, true, "FE_BAJA");
		addField("CUR","CHAR", 14, true, "CUR");
		addField("PERSONA_ID","NUMBER", 10, true, "PERSONA_ID"); //vino del modelo sin longitud
		
		addKey("CO_NTIA");
		
		
		
	
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTPNtia();
	} /* getThisDBObj() */

}

