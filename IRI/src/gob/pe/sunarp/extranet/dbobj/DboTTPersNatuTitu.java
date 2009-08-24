package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTPersNatuTitu extends DBObject {

	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_CO_ACTO_RGST      = "CO_ACTO_RGST";
	public static final String CAMPO_DE_ACTO_RGST      = "DE_ACTO_RGST";
	public static final String CAMPO_NS_AFEC           = "NS_AFEC";
	public static final String CAMPO_NS_PERS_NATU      = "NS_PERS_NATU";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_CUR               = "CUR";
	public static final String CAMPO_TI_DOCU_IDEN      = "TI_DOCU_IDEN";
	public static final String CAMPO_DE_TI_DOCU_IDEN   = "DE_TI_DOCU_IDEN";
	public static final String CAMPO_NU_DOCU           = "NU_DOCU";
	public static final String CAMPO_AP_PATE_PERS_NATU = "AP_PATE_PERS_NATU";
	public static final String CAMPO_AP_MATE_PERS_NATU = "AP_MATE_PERS_NATU";
	public static final String CAMPO_NO_PERS_NATU      = "NO_PERS_NATU";
	public static final String CAMPO_TI_PRTC           = "TI_PRTC";
	/**
	 * SE AGREGO EL CAMPO CAMPO_TI_PRTC_SUNARP - REQUERIMIENTO DE SUNAT
	 */				
	public static final String CAMPO_TI_PRTC_SUNAT     = "TI_PRTC_SUNAT";	
	public static final String CAMPO_DE_TI_PRTC        = "DE_TI_PRTC";
	public static final String CAMPO_ES_CIVL           = "ES_CIVL";
	public static final String CAMPO_DE_ES_CIVL        = "DE_ES_CIVL";
	public static final String CAMPO_FE_NACI           = "FE_NACI";
	public static final String CAMPO_ID_PAIS           = "ID_PAIS";
	public static final String CAMPO_DE_NCNL           = "DE_NCNL";
	public static final String CAMPO_NU_PART_ASOC      = "NU_PART_ASOC";
	public static final String CAMPO_CO_OCUP           = "CO_OCUP";
	public static final String CAMPO_DE_OCUP           = "DE_OCUP";
	public static final String CAMPO_FE_OCUP           = "FE_OCUP";
	public static final String CAMPO_NU_ACCI           = "NU_ACCI";
	public static final String CAMPO_TS_USUA_CREA      = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA      = "ID_USUA_CREA";
	public static final String CAMPO_CO_LIBR_PART      = "CO_LIBR_PART";
	public static final String CAMPO_DE_LIBR_PART      = "DE_LIBR_PART";
	public static final String CAMPO_ES_PRTC           = "ES_PRTC";
	/** JBUGARIN 13/09/06 **/
	public static final String CAMPO_UB_GEOG           		= "UB_GEOG";
	public static final String CAMPO_TI_VIA           		= "TI_VIA";
	public static final String CAMPO_NO_VIA           		= "NO_VIA";
	public static final String CAMPO_MAIL           		= "MAIL";
	public static final String CAMPO_DE_DEPARTAMENTO_PRES  = "DE_DEPARTAMENTO_PRES";
	public static final String CAMPO_DE_PROVINCIA_PRES    = "DE_PROVINCIA_PRES";
	public static final String CAMPO_DE_DISTRITO_PRES     = "DE_DISTRITO_PRES";
	public static final String CAMPO_DE_VIA                 = "DE_VIA";
	public static final String CAMPO_CO_POSTAL          = "CO_POSTAL";
	/** JBUGARIN 13/09/06 **/	

	/**
	 * SE ADICIONAN LOS CAMPOS: 
	 * 
	 * - indicadorRepresentante, 
	 * - nombreConyuge, 
	 * - valorParticipacion,
	 * - porcentajeParticipacion,
	 * - numeroPartidaEmpresa
	 * - 
	 * 
	 * SAUL VASQUEZ
	 */	
	public static final String CAMPO_IND_RRLL_PARTIC   			= "IND_RRLL_PARTIC";
	public static final String CAMPO_NO_CONY			  		= "NO_CONY";
	public static final String CAMPO_VA_PART		    		= "VA_PART";
	public static final String CAMPO_PO_PART		     		= "PO_PART";
	public static final String CAMPO_NU_PART_ACCI          		= "NU_PART_ACCI";
	public static final String CAMPO_DE_PROF	          		= "DE_PROF";	 

	
	public DboTTPersNatuTitu() throws DBException {
		super();
	} /* DboTTPersNatuTitu() */


	public DboTTPersNatuTitu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTPersNatuTitu(DBConnection) */


	public DboTTPersNatuTitu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTTPersNatuTitu(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_PERS_NATU_TITU");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");
		addField("CO_ACTO_RGST","CHAR", 5, false, "CAMPO_CO_ACTO_RGST");
		addField("DE_ACTO_RGST","VARCHAR", 80, true, "CAMPO_DE_ACTO_RGST");
		addField("NS_AFEC","NUMBER", 38, false, "CAMPO_NS_AFEC");
		addField("NS_PERS_NATU","NUMBER", 38, false, "CAMPO_NS_PERS_NATU");
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("CUR","CHAR", 14, true, "CAMPO_CUR");
		addField("TI_DOCU_IDEN","CHAR", 2, true, "CAMPO_TI_DOCU_IDEN");
		addField("DE_TI_DOCU_IDEN","VARCHAR", 10, true, "CAMPO_DE_TI_DOCU_IDEN");
		addField("NU_DOCU","CHAR", 20, true, "CAMPO_NU_DOCU");
		addField("AP_PATE_PERS_NATU","VARCHAR", 50, true, "CAMPO_AP_PATE_PERS_NATU");
		addField("AP_MATE_PERS_NATU","VARCHAR", 50, true, "CAMPO_AP_MATE_PERS_NATU");
		addField("NO_PERS_NATU","VARCHAR", 50, true, "CAMPO_NO_PERS_NATU");
		addField("TI_PRTC","CHAR", 3, true, "CAMPO_TI_PRTC");
		/**
		 * SE AGREGO EL CAMPO CAMPO_TI_PRTC_SUNARP - REQUERIMIENTO DE SUNAT
		 */			
		addField("TI_PRTC_SUNAT","CHAR", 2, true, "CAMPO_TI_PRTC_SUNAT");		
		addField("DE_TI_PRTC","VARCHAR", 50, true, "CAMPO_DE_TI_PRTC");
		addField("ES_CIVL","CHAR", 2, true, "CAMPO_ES_CIVL");
		addField("DE_ES_CIVL","VARCHAR", 45, true, "CAMPO_DE_ES_CIVL");
		addField("FE_NACI","NUMBER", 22, true, "CAMPO_FE_NACI");
		addField("ID_PAIS","CHAR", 2, true, "CAMPO_ID_PAIS");
		addField("DE_NCNL","VARCHAR", 30, true, "CAMPO_DE_NCNL");
		addField("NU_PART_ASOC","CHAR", 8, true, "CAMPO_NU_PART_ASOC");
		addField("CO_OCUP","CHAR", 3, true, "CAMPO_CO_OCUP");
		addField("DE_OCUP","VARCHAR", 30, true, "CAMPO_DE_OCUP");
		addField("FE_OCUP","NUMBER", 22, true, "CAMPO_FE_OCUP");
		addField("NU_ACCI","NUMBER", 12, true, "CAMPO_NU_ACCI");
		addField("TS_USUA_CREA","NUMBER", 22, true, "CAMPO_TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "CAMPO_ID_USUA_CREA");
		addField("CO_LIBR_PART","CHAR", 3, true, "CAMPO_CO_LIBR_PART");
		addField("DE_LIBR_PART","VARCHAR", 120, true, "CAMPO_DE_LIBR_PART");
		addField("ES_PRTC","CHAR", 1, true, "CAMPO_ES_PRTC");

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
		/**JBUGARIN 13/09/06 **/

		//SAUL
		addField("IND_RRLL_PARTIC","CHAR", 1, true, "IND_RRLL_PARTIC");
		addField("NO_CONY","VARCHAR", 250, true, "NO_CONY");
		addField("VA_PART","NUMBER", 9,2, true, "VA_PART");
		addField("PO_PART","NUMBER", 5,2, true, "PO_PART");
		addField("NU_PART_ACCI","CHAR", 8, true, "NU_PART_ACCI");
		addField("DE_PROF","VARCHAR", 250, true, "DE_PROF");				

		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");		
		addKey("CO_ACTO_RGST");
		addKey("NS_AFEC");
		addKey("NS_PERS_NATU");

	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTTPersNatuTitu();
	} /* getThisDBObj() */
} /* DboTTPersNatuTitu */

