package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTATitu extends DBObject {

	public static final String CAMPO_NO_HOJA      		= "NO_HOJA";
	public static final String CAMPO_AN_TITU      		= "AN_TITU";
	public static final String CAMPO_CO_ZONA      		= "CO_ZONA";
	public static final String CAMPO_CO_SEDE      		= "CO_SEDE";
	public static final String CAMPO_CO_AREA     		= "CO_AREA";
	public static final String CAMPO_NO_TITU 	  		= "NO_TITU";
	public static final String CAMPO_RZ_SOCL      		= "RZ_SOCL";
	public static final String CAMPO_ES_TITU      		= "ES_TITU";
	public static final String CAMPO_ES_TITU_CALI       = "ES_TITU_CALI";
	public static final String CAMPO_FH_PRES      	    = "FH_PRES ";
	public static final String CAMPO_FH_DIGI    		= "FH_DIGI";
	public static final String CAMPO_CO_EMPL_DIGI       = "CO_EMPL_DIGI";
	public static final String CAMPO_LE_PRES            = "LE_PRES";
	public static final String CAMPO_CO_EMPL_TECN       = "CO_EMPL_TECN";
	public static final String CAMPO_CO_SECC 			= "CO_SECC";
	public static final String CAMPO_CO_PSTO_TRAB       = "CO_PSTO_TRAB";
	public static final String CAMPO_PU_CTRL            = "PU_CTRL";
	public static final String CAMPO_CO_EMPL_REGI       = "CO_EMPL_REGI";
	public static final String CAMPO_FE_VENC            = "FE_VENC";
	public static final String CAMPO_FE_DOCU_LEGA       = "FE_DOCU_LEGA";
	public static final String CAMPO_OBSV 				= "OBSV";
	public static final String CAMPO_CO_EMPL_DIGI_SECC  = "CO_EMPL_DIGI_SECC";
	public static final String CAMPO_FH_DIGI_SECC       = "FH_DIGI_SECC";
	public static final String CAMPO_FH_INSC 	        = "FH_INSC";
	public static final String CAMPO_TI_DOCU_PRES       = "TI_DOCU_PRES";
	public static final String CAMPO_TI_DOCU_LEGA       = "TI_DOCU_LEGA";
	public static final String CAMPO_CO_PACT_ESPE       = "CO_PACT_ESPE";
	public static final String CAMPO_FG_PROV      	    = "FG_PROV";
	public static final String CAMPO_NO_DIAS_PROR       = "NO_DIAS_PROR";
	public static final String CAMPO_FG_SOLI_TACH       = "FG_SOLI_TACH";
	public static final String CAMPO_CO_TIPO_RTTE       = "CO_TIPO_RTTE";
	public static final String CAMPO_AP_PATE            = "AP_PATE";
	public static final String CAMPO_AP_MATE            = "AP_MATE";
	public static final String CAMPO_NOMBRE             = "NOMBRE";
	public static final String CAMPO_TI_PROP 			= "TI_PROP";
	public static final String CAMPO_CUR     			= "CUR";
	public static final String CAMPO_FH_MICSEG          = "FH_MICSEG";
	public static final String CAMPO_CO_NOTA      		= "CO_NOTA";
	public static final String CAMPO_CO_PRES 			= "CO_PRES";
	public static final String CAMPO_TS_USUA_MODI       = "TS_USUA_MODI";
	public static final String CAMPO_NO_DIAS_EXTR 		= "NO_DIAS_EXTR";
	public static final String CAMPO_FG_CANC      		= "FG_CANC";
	public static final String CAMPO_TI_DIAR   		    = "TI_DIAR";
	public static final String CAMPO_IN_FORM     		= "IN_FORM";
	public static final String CAMPO_CO_LIBR    		= "CO_LIBR";
	public static final String CAMPO_CUO     		    = "CUO";
	public static final String CAMPO_SERVICIO_ID        = "SERVICIO_ID";
	public static final String CAMPO_DE_SERVICIO        = "DE_SERVICIO";
	public static final String CAMPO_ID_USUARIO     	= "ID_USUARIO";
	public static final String CAMPO_MAIL_PRES     		= "MAIL_PRES";
	public static final String CAMPO_CO_POSTAL_PRES    	= "CO_POSTAL_PRES";
	public static final String CAMPO_ADDR     		    = "ADDR";
	public static final String CAMPO_DE_VIA_PRES     	= "DE_VIA_PRES";
	public static final String CAMPO_TI_VIA_PRES     	= "TI_VIA_PRES";
	public static final String CAMPO_UB_GEOG_PRES     		= "UB_GEOG_PRES";
	public static final String CAMPO_DE_DEPARTAMENTO_PRES   	= "DE_DEPARTAMENTO_PRES";
	public static final String CAMPO_DE_PROVINCIA_PRES     		= "DE_PROVINCIA_PRES";
	public static final String CAMPO_DE_DISTRITO_PRES     		= "DE_DISTRITO_PRES";
	public static final String CAMPO_DE_TIPO_PRES     		    = "DE_TIPO_PRES";
	public static final String CAMPO_DE_AREA     		        = "DE_AREA";
	public static final String CAMPO_DE_LIBRO     		        = "DE_LIBRO";
	public static final String CAMPO_DES_INST     		        = "DES_INST";
//	public static final String CAMPO_CO_ACTO     		        = "CO_ACTO";
	
	/**
	 * Constructor for DboTATitu
	 */
	public DboTATitu() throws DBException{
		super();
	}

	public DboTATitu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection) */


	public DboTATitu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection, String) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_TITU");

		setDescription("Object Description Goes Here");
		
		addField("NO_HOJA","CHAR", 8, false, "NO_HOJA");
		addField("AN_TITU","CHAR", 4, false, "AN_TITU");
		addField("CO_ZONA","CHAR", 2, false, "CO_ZONA");
		addField("CO_SEDE","CHAR", 2, false, "CO_SEDE");
		addField("CO_AREA","CHAR", 5, false, "CO_AREA");
		addField("NO_TITU","NUMBER", 8, true, "NO_TITU");
		addField("RZ_SOCL","VARCHAR", 100, true, "RZ_SOCL");
		addField("ES_TITU","CHAR", 2, true, "ES_TITU");
		addField("ES_TITU_CALI","CHAR", 2, true, "ES_TITU_CALI");
		addField("FH_PRES ","NUMBER", 22, true, "FH_PRES");
		addField("FH_DIGI","NUMBER", 22, true, "FH_DIGI");
		addField("CO_EMPL_DIGI","CHAR", 4, true, "CO_EMPL_DIGI");
		addField("LE_PRES","CHAR", 12, true, "LE_PRES");
		addField("CO_EMPL_TECN","CHAR", 4, true, "CO_EMPL_TECN");
		addField("CO_SECC","CHAR", 2, true, "CO_SECC");
		addField("CO_PSTO_TRAB","CHAR", 4, true, "CO_PSTO_TRAB");
		addField("PU_CTRL","CHAR", 2, true, "PU_CTRL");
		addField("CO_EMPL_REGI","CHAR", 4, true, "CO_EMPL_REGI");
		addField("FE_VENC","NUMBER", 22, true, "FE_VENC");
		addField("FE_DOCU_LEGA","NUMBER", 22, true, "FE_DOCU_LEGA");
		addField("OBSV","VARCHAR", 250, true, "OBSV");
		addField("CO_EMPL_DIGI_SECC","CHAR", 4, true, "CO_EMPL_DIGI_SECC");
		addField("FH_DIGI_SECC","DATE", 22, true, "FH_DIGI_SECC");
		addField("FH_INSC","DATE", 22, true, "FH_INSC");
		addField("TI_DOCU_PRES","CHAR", 2, true, "TI_DOCU_PRES");
		addField("TI_DOCU_LEGA","CHAR", 2, true, "TI_DOCU_LEGA");
		addField("CO_PACT_ESPE","CHAR", 1, true, "CO_PACT_ESPE");
		addField("FG_PROV","CHAR", 1, true, "FG_PROV");
		addField("NO_DIAS_PROR","NUMBER", 3, true, "NO_DIAS_PROR");
		addField("FG_SOLI_TACH","CHAR", 1, true, "FG_SOLI_TACH");
		addField("CO_TIPO_RTTE","CHAR", 1, true, "CO_TIPO_RTTE");
		addField("AP_PATE","VARCHAR", 30, true, "AP_PATE");
		addField("AP_MATE","VARCHAR", 30, true, "AP_MATE");
		addField("NOMBRE","VARCHAR", 40, true, "NOMBRE");
		addField("TI_PROP","CHAR", 1, true, "TI_PROP");
		addField("CUR","CHAR", 14, true, "CUR");
		addField("FH_MICSEG","VARCHAR", 6, true, "FH_MICSEG");
		addField("CO_NOTA","CHAR", 5, true, "CO_NOTA");
		addField("CO_PRES","CHAR", 5, true, "CO_PRES");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("NO_DIAS_EXTR","NUMBER", 3, true, "NO_DIAS_EXTR");
		addField("FG_CANC","CHAR", 1, true, "FG_CANC");
		addField("TI_DIAR","CHAR", 1, true, "TI_DIAR");
		addField("IN_FORM","CHAR", 1, true, "IN_FORM");
		addField("CO_LIBR","CHAR", 3, true, "CO_LIBR");
		addField("CUO","CHAR", 10, true, "CUO");
		addField("SERVICIO_ID","NUMBER", 3, true, "SERVICIO_ID");
		addField("DE_SERVICIO","CHAR", 3, true, "DE_SERVICIO");
		addField("ID_USUARIO","VARCHAR", 15, true, "ID_USUARIO");
		addField("MAIL_PRES","VARCHAR", 100, true, "MAIL_PRES");
		addField("DE_VIA_PRES","CHAR", 45, true, "DE_VIA_PRES");
		addField("TI_VIA_PRES","CHAR", 3, true, "TI_VIA_PRES");
		addField("UB_GEOG_PRES","CHAR", 2, true, "UB_GEOG_PRES");
		addField("DE_DEPARTAMENTO_PRES","VARCHAR", 40, true, "DE_DEPARTAMENTO_PRES");
		addField("DE_PROVINCIA_PRES","VARCHAR", 40, true, "DE_PROVINCIA_PRES");
		addField("DE_DISTRITO_PRES","VARCHAR", 40, true, "DE_DISTRITO_PRES");
		addField("DE_TIPO_PRES","VARCHAR", 10, true, "DE_TIPO_PRES");
		addField("DE_AREA","VARCHAR", 30, true, "DE_AREA");
		addField("DE_LIBRO","VARCHAR", 120, true, "DE_LIBRO");
		addField("DES_INST","VARCHAR", 50, true, "DES_INST");
		addField("CO_POSTAL_PRES","VARCHAR", 10, true, "CO_POSTAL_PRES");
		addField("ADDR","VARCHAR", 100, true, "ADDR");
		// primary keys
		addKey("NO_HOJA");
		addKey("AN_TITU");
		addKey("CO_SEDE");
		addKey("CO_ZONA");
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTATitu();
	} /* getThisDBObj() */

}

