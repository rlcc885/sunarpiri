package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTPHojaPres extends DBObject {
	
	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_CUO               = "CUO";
	public static final String CAMPO_CO_AREA           = "CO_AREA";
	public static final String CAMPO_DE_AREA           = "DE_AREA";
	public static final String CAMPO_ID_USUARIO        = "ID_USUARIO";
	public static final String CAMPO_PERS_AUTZ_PRES    = "PERS_AUTZ_PRES";
	public static final String CAMPO_AP_PATE_PRES      = "AP_PATE_PRES";
	public static final String CAMPO_AP_MATE_PRES      = "AP_MATE_PRES";
	public static final String CAMPO_NO_PRES           = "NO_PRES";
	public static final String CAMPO_CUR               = "CUR";
	public static final String CAMPO_NO_CUR            = "NO_CUR";
	public static final String CAMPO_TI_DOCU_IDEN      = "TI_DOCU_IDEN";
	public static final String CAMPO_DE_TI_DOCU_IDEN   = "DE_TI_DOCU_IDEN";
	public static final String CAMPO_NU_DOCU           = "NU_DOCU";
	public static final String CAMPO_TS_HOJA_PRES      = "TS_HOJA_PRES";
	public static final String CAMPO_TI_HOJA_PRES      = "TI_HOJA_PRES";
	public static final String CAMPO_AA_HOJA_DEFI      = "AA_HOJA_DEFI";			
	public static final String CAMPO_NU_HOJA_DEFI      = "NU_HOJA_DEFI";
	public static final String CAMPO_TS_USUA_CREA      = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA      = "ID_USUA_CREA";
	public static final String CAMPO_DE_OBSE           = "DE_OBSE";
	public static final String CAMPO_TI_SITU_HOJA_PRES = "TI_SITU_HOJA_PRES";
	public static final String CAMPO_NU_ANOT           = "NU_ANOT";
	public static final String CAMPO_TI_FORM_REG       = "TI_FORM_REG";
	public static final String CAMPO_AP_PATE_REPR      = "AP_PATE_REPR";
	public static final String CAMPO_AP_MATE_REPR      = "AP_MATE_REPR";
	public static final String CAMPO_NO_REPR           = "NO_REPR";
	public static final String CAMPO_TI_DOCU_IDEN_REPR = "TI_DOCU_IDEN_REPR";
	public static final String CAMPO_DE_TI_DOCU_IDEN_REPR = "DE_TI_DOCU_IDEN_REPR";
	public static final String CAMPO_NU_DOCU_REPR      = "NU_DOCU_REPR";
	public static final String CAMPO_RZ_SOCL_REPR      = "RZ_SOCL_REPR";	
	public static final String CAMPO_IN_RUC            = "IN_RUC";
	public static final String CAMPO_CO_ELECT_REPR     = "CO_ELECT_REPR";
	public static final String CAMPO_SERVICIO_ID       = "SERVICIO_ID";
	public static final String CAMPO_DE_SERVICIO       = "DE_SERVICIO";
	
	/****  JBUGARIN 13/09/06 ****////
	public static final String CAMPO_UB_GEOG_PRES           		= "UB_GEOG_PRES";
	public static final String CAMPO_TI_VIA_PRES           		    = "TI_VIA_PRES";
	public static final String CAMPO_NO_VIA_PRES           		    = "NO_VIA_PRES";
	public static final String CAMPO_MAIL_PRES           		    = "MAIL_PRES";
	public static final String CAMPO_DE_DEPARTAMENTO_PRES           = "DE_DEPARTAMENTO_PRES";
	public static final String CAMPO_DE_PROVINCIA_PRES              = "DE_PROVINCIA_PRES";
	public static final String CAMPO_DE_DISTRITO_PRES               = "DE_DISTRITO_PRES";
	public static final String CAMPO_DE_VIA_PRES                    = "DE_VIA_PRES";
	public static final String CAMPO_CO_POSTAL_PRES                 = "CO_POSTAL_PRES";
	/****  JBUGARIN 13/09/06 ****////	

	/***** MGARATE 25/04/08 ***********////
	
	public static final String  CAMPO_USR_VERIF  = "USR_VERIF";
	public static final String  CAMPO_TS_VERIF   = "TS_VERIF";
	public static final String  CAMPO_RPTA_VERIF = "RPTA_VERIF";
	
	/***** MGARATE 25/04/08 ***********////

	public DboTPHojaPres() throws DBException {
		super();
	} /* DboTPHojaPres() */


	public DboTPHojaPres(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection) */


	public DboTPHojaPres(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TP_HOJA_PRES");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");
		addField("CUO","CHAR", 10, true, "CAMPO_CUO");
		addField("CO_AREA","CHAR", 5, true, "CAMPO_CO_AREA");
		addField("DE_AREA","VARCHAR", 30, true, "CAMPO_DE_AREA");
		addField("ID_USUARIO","VARCHAR", 15, false, "CAMPO_ID_USUARIO");
		addField("PERS_AUTZ_PRES","CHAR", 3, true, "CAMPO_PERS_AUTZ_PRES");
		addField("AP_PATE_PRES","VARCHAR", 50, true, "CAMPO_AP_PATE_PRES");
		addField("AP_MATE_PRES","VARCHAR", 50, true, "CAMPO_AP_MATE_PRES");
		addField("NO_PRES","VARCHAR", 50, true, "CAMPO_NO_PRES");
		addField("CUR","CHAR", 14, true, "CAMPO_CUR");
		addField("NO_CUR","VARCHAR", 50, true, "CAMPO_NO_CUR");
		addField("TI_DOCU_IDEN","CHAR", 2, true, "CAMPO_TI_DOCU_IDEN");
		addField("DE_TI_DOCU_IDEN","VARCHAR", 10, true, "CAMPO_DE_TI_DOCU_IDEN");
		addField("NU_DOCU","CHAR", 20, true, "CAMPO_NU_DOCU");
		addField("TS_HOJA_PRES","NUMBER", 22, true, "CAMPO_TS_HOJA_PRES");
		addField("TI_HOJA_PRES","CHAR", 1, true, "CAMPO_TI_HOJA_PRES");
		addField("AA_HOJA_DEFI","CHAR", 4, true, "CAMPO_AA_HOJA_DEFI");
		addField("NU_HOJA_DEFI","CHAR", 8, true, "CAMPO_NU_HOJA_DEFI");
		addField("TS_USUA_CREA","NUMBER", 22, true, "CAMPO_TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "CAMPO_ID_USUA_CREA");
		addField("DE_OBSE","VARCHAR", 100, true, "CAMPO_DE_OBSE");
		addField("TI_SITU_HOJA_PRES","CHAR", 1, true, "CAMPO_TI_SITU_HOJA_PRES");
		addField("NU_ANOT","NUMBER", 2, true, "CAMPO_NU_ANOT");
		addField("TI_FORM_REG","CHAR", 2, true, "CAMPO_TI_FORM_REG");
		addField("AP_PATE_REPR","VARCHAR", 50, true, "CAMPO_AP_PATE_REPR");
		addField("AP_MATE_REPR","VARCHAR", 50, true, "CAMPO_AP_MATE_REPR");
		addField("NO_REPR","VARCHAR", 50, true, "CAMPO_NO_REPR");
		addField("TI_DOCU_IDEN_REPR","CHAR", 2, true, "CAMPO_TI_DOCU_IDEN_REPR");
		addField("DE_TI_DOCU_IDEN_REPR","VARCHAR", 10, true, "CAMPO_DE_TI_DOCU_IDEN_REPR");
		addField("NU_DOCU_REPR","CHAR", 20, true, "CAMPO_NU_DOCU_REPR");
		addField("RZ_SOCL_REPR","VARCHAR", 250, true, "CAMPO_RZ_SOCL_REPR");
		addField("IN_RUC","CHAR", 1, true, "CAMPO_IN_RUC");
		addField("CO_ELECT_REPR","VARCHAR", 50, true, "CAMPO_CO_ELECT_REPR");
		addField("SERVICIO_ID","NUMBER", 15, true, "CAMPO_SERVICIO_ID");
		addField("DE_SERVICIO","VARCHAR", 50, true, "CAMPO_DE_SERVICIO");
		
		/** JBUGARIN 13/09/06 **/
		addField("UB_GEOG_PRES","CHAR", 8, true, "UB_GEOG_PRES");
		addField("TI_VIA_PRES","CHAR", 2, true, "TI_VIA_PRES");
		addField("NO_VIA_PRES","VARCHAR", 100, true, "NO_VIA_PRES");
		addField("MAIL_PRES","VARCHAR", 100, true, "MAIL_PRES");
		addField("DE_DEPARTAMENTO_PRES","VARCHAR", 30, true, "DE_DEPARTAMENTO_PRES");
		addField("DE_PROVINCIA_PRES","VARCHAR", 40, true, "DE_PROVINCIA_PRES");
		addField("DE_DISTRITO_PRES","VARCHAR", 40, true, "DE_DISTRITO_PRES");
		addField("DE_VIA_PRES","VARCHAR", 45, true, "DE_VIA_PRES");
		addField("CO_POSTAL_PRES","VARCHAR", 10, true, "CO_POSTAL_PRES");
		/**JBUGARIN 13/09/06 **/
						
		/***** MGARATE 25/04/08 ***********////
		
		addField("USR_VERIF","VARCHAR", 30, true, "USR_VERIF");
		addField("TS_VERIF","NUMBER", 22, true, "TS_VERIF");
		addField("RPTA_VERIF","CHAR", 1, true, "RPTA_VERIF");
		
		/***** MGARATE 25/04/08 ***********////
		
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTPHojaPres();
	} /* getThisDBObj() */
} /* DboTPHojaPres */

