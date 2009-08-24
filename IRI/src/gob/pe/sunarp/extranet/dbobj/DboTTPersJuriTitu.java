package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTPersJuriTitu extends DBObject {

	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_CO_ACTO_RGST      = "CO_ACTO_RGST";
	public static final String CAMPO_DE_ACTO_RGST      = "DE_ACTO_RGST";
	public static final String CAMPO_NS_AFEC           = "NS_AFEC";
	public static final String CAMPO_NS_PERS_JURI      = "NS_PERS_JURI";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_RZ_SOCL           = "RZ_SOCL";
	public static final String CAMPO_TI_PERS_JURI      = "TI_PERS_JURI";
	public static final String CAMPO_DE_TI_PERS_JURI   = "DE_TI_PERS_JURI";
	public static final String CAMPO_DE_SIGL           = "DE_SIGL";
	public static final String CAMPO_CO_CIIU           = "CO_CIIU";
	public static final String CAMPO_TI_PRTC           = "TI_PRTC";
	/**
	 * SE AGREGO EL CAMPO CAMPO_TI_PRTC_SUNARP - REQUERIMIENTO DE SUNAT
	 */	
	public static final String CAMPO_TI_PRTC_SUNAT     = "TI_PRTC_SUNAT";	
	public static final String CAMPO_DE_TI_PRTC        = "DE_TI_PRTC";
	public static final String CAMPO_TI_DOCU_IDEN      = "TI_DOCU_IDEN";
	public static final String CAMPO_DE_TI_DOCU_IDEN   = "DE_TI_DOCU_IDEN";
	public static final String CAMPO_NU_DOCU           = "NU_DOCU";
	public static final String CAMPO_NU_ACCI           = "NU_ACCI";
	public static final String CAMPO_TS_USUA_CREA      = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA      = "ID_USUA_CREA";
	public static final String CAMPO_CO_LIBR_PART      = "CO_LIBR_PART";
	public static final String CAMPO_DE_LIBR_PART      = "DE_LIBR_PART";
	public static final String CAMPO_NU_PART_ASOC      = "NU_PART_ASOC";
	public static final String CAMPO_TI_MONE           = "TI_MONE";
	public static final String CAMPO_DE_TI_MONE        = "DE_TI_MONE";
	public static final String CAMPO_MO_TOTA           = "MO_TOTA";
	public static final String CAMPO_VA_ACCN           = "VA_ACCN";
	public static final String CAMPO_SI_CAPI           = "SI_CAPI";
	public static final String CAMPO_DE_SI_CAPI        = "DE_SI_CAPI";
	public static final String CAMPO_PO_PAGO_CANC      = "PO_PAGO_CANC";
	public static final String CAMPO_TI_SOCI           = "TI_SOCI";
	public static final String CAMPO_DE_TI_SOCI        = "DE_TI_SOCI";
	public static final String CAMPO_ES_PRTC           = "ES_PRTC";
	public static final String CAMPO_TI_EMPRE          = "TI_EMPRE";
	public static final String CAMPO_AA_TITU_ASOC      = "AA_TITU_ASOC";
	public static final String CAMPO_NU_TITU_ASOC      = "NU_TITU_ASOC";
	public static final String CAMPO_ID_PAIS           = "ID_PAIS";
	public static final String CAMPO_DE_NCNL           = "DE_NCNL";		
	/****  JBUGARIN 13/09/06 ****////
	public static final String CAMPO_UB_GEOG           		= "UB_GEOG";
	public static final String CAMPO_TI_VIA           		= "TI_VIA";
	public static final String CAMPO_NO_VIA           		= "NO_VIA";
	public static final String CAMPO_MAIL           		= "MAIL";
	public static final String CAMPO_DE_DEPARTAMENTO_PRES  = "DE_DEPARTAMENTO_PRES";
	public static final String CAMPO_DE_PROVINCIA_PRES    = "DE_PROVINCIA_PRES";
	public static final String CAMPO_DE_DISTRITO_PRES     = "DE_DISTRITO_PRES";
	public static final String CAMPO_DE_VIA                 = "DE_VIA";
	public static final String CAMPO_CO_POSTAL          = "CO_POSTAL";
	public static final String CAMPO_NU_PART	      		= "NU_PART";
	/****  JBUGARIN 13/09/06 ****////	
		
	//SAUL
	public static final String CAMPO_IND_RRLL_PARTIC          	= "IND_RRLL_PARTIC";	

	public DboTTPersJuriTitu() throws DBException {
		super();
	} /* DboTTPersJuriTitu() */


	public DboTTPersJuriTitu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTPersJuriTitu(DBConnection) */


	public DboTTPersJuriTitu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTTPersJuriTitu(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_PERS_JURI_TITU");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");
		addField("CO_ACTO_RGST","CHAR", 5, false, "CAMPO_CO_ACTO_RGST");
		addField("DE_ACTO_RGST","VARCHAR", 80, true, "CAMPO_DE_ACTO_RGST");
		addField("NS_AFEC","NUMBER", 38, false, "CAMPO_NS_AFEC");
		addField("NS_PERS_JURI","NUMBER", 38, false, "CAMPO_NS_PERS_JURI");
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("RZ_SOCL","VARCHAR", 250, true, "CAMPO_RZ_SOCL");
		addField("TI_PERS_JURI","CHAR", 2, true, "CAMPO_TI_PERS_JURI");
		addField("DE_TI_PERS_JURI","VARCHAR", 120, true, "CAMPO_DE_TI_PERS_JURI");
		addField("DE_SIGL","VARCHAR", 60, true, "CAMPO_DE_SIGL");
		addField("CO_CIIU","CHAR", 5, true, "CAMPO_CO_CIIU");
		addField("TI_PRTC","CHAR", 3, true, "CAMPO_TI_PRTC");
		/**
		 * SE AGREGO EL CAMPO CAMPO_TI_PRTC - REQUERIMIENTO DE SUNAT
		 */		
		addField("TI_PRTC_SUNAT","CHAR", 2, true, "CAMPO_TI_PRTC_SUNAT");		
		addField("DE_TI_PRTC","VARCHAR", 50, true, "CAMPO_DE_TI_PRTC");
		addField("TI_DOCU_IDEN","CHAR", 2, true, "CAMPO_TI_DOCU_IDEN");
		addField("DE_TI_DOCU_IDEN","VARCHAR", 10, true, "CAMPO_DE_TI_DOCU_IDEN");
		addField("NU_DOCU","CHAR", 20, true, "CAMPO_NU_DOCU");
		addField("NU_ACCI","NUMBER", 12, true, "CAMPO_NU_ACCI");
		addField("TS_USUA_CREA","NUMBER", 22, true, "CAMPO_TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "CAMPO_ID_USUA_CREA");
		addField("CO_LIBR_PART","CHAR", 3, true, "CAMPO_CO_LIBR_PART");
		addField("DE_LIBR_PART","VARCHAR", 120, true, "CAMPO_DE_LIBR_PART");
		addField("NU_PART_ASOC","CHAR", 8, true, "CAMPO_NU_PART_ASOC");
		addField("TI_MONE","CHAR", 2, true, "CAMPO_TI_MONE");
		addField("DE_TI_MONE","VARCHAR", 45, true, "CAMPO_DE_TI_MONE");
		addField("MO_TOTA","NUMBER", 12, 2, true, "CAMPO_MO_TOTA");
		addField("VA_ACCN","NUMBER", 12, 2, true, "CAMPO_VA_ACCN");
		addField("SI_CAPI","VARCHAR", 2, true, "CAMPO_SI_CAPI");
		addField("DE_SI_CAPI","VARCHAR", 20, true, "CAMPO_DE_SI_CAPI");
		addField("PO_PAGO_CANC","NUMBER", 5, 2, true, "CAMPO_PO_PAGO_CANC");
		addField("TI_SOCI","CHAR", 1, true, "CAMPO_TI_SOCI");
		addField("DE_TI_SOCI","VARCHAR", 50, true, "CAMPO_DE_TI_SOCI");
		addField("ES_PRTC","CHAR", 1, true, "CAMPO_ES_PRTC");
		addField("TI_EMPRE","CHAR", 1, true, "CAMPO_TI_EMPRE");
		addField("AA_TITU_ASOC","CHAR", 4, true, "CAMPO_AA_TITU_ASOC");
		addField("NU_TITU_ASOC","CHAR", 4, true, "CAMPO_NU_TITU_ASOC");
		addField("ID_PAIS","CHAR", 2, true, "CAMPO_ID_PAIS");
		addField("DE_NCNL","VARCHAR", 30, true, "CAMPO_DE_NCNL");
		
		/** JBUGARIN 13/09/06 **/
		addField("UB_GEOG","CHAR", 8, true, "UB_GEOG");
		addField("TI_VIA","CHAR", 2, true, "TI_VIA");
		addField("NO_VIA","VARCHAR", 100, true, "NO_VIA");
		addField("MAIL","VARCHAR", 100, true, "MAIL");
		addField("DE_DEPARTAMENTO_PRES","VARCHAR", 30, true, "DE_DEPARTAMENTO_PRES");
		addField("DE_PROVINCIA_PRES","VARCHAR", 40, true, "DE_PROVINCIA_PRES");
		addField("DE_DISTRITO_PRES","VARCHAR", 40, true, "DE_DISTRITO_PRES");
		addField("DE_VIA","VARCHAR", 45, true, "DE_VIA");
		addField("CO_POSTAL","VARCHAR", 10, true, "CO_POSTAL");
		addField("NU_PART","CHAR", 8, true, "NU_PART");
		/** JBUGARIN 13/09/06 **/
				
		//SAUL
		addField("IND_RRLL_PARTIC","CHAR", 1, true, "IND_RRLL_PARTIC");
		
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
		addKey("CO_ACTO_RGST");
		addKey("NS_AFEC");
		addKey("NS_PERS_JURI");
		
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTTPersJuriTitu();
	} /* getThisDBObj() */
} /* DboTTPersJuriTitu */

