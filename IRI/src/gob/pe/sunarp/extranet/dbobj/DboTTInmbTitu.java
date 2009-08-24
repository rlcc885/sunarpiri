package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTInmbTitu extends DBObject{
	
	public static final String CAMPO_AA_TITU         		= "AA_TITU";
	public static final String CAMPO_NU_TITU        		= "NU_TITU";
	public static final String CAMPO_CO_ACTO_RGST           = "CO_ACTO_RGST";
	public static final String CAMPO_CO_REGI         	    = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST           = "CO_OFIC_RGST";
	public static final String CAMPO_NS_AFEC                = "NS_AFEC";
	public static final String CAMPO_GR_IMPO                = "GR_IMPO";
	public static final String CAMPO_UB_GEOG                = "UB_GEOG";
	public static final String CAMPO_TI_ZONA                = "TI_ZONA";
	public static final String CAMPO_NO_ZONA                = "NO_ZONA";
	public static final String CAMPO_NO_ZONA_1              = "NO_ZONA_1";
	public static final String CAMPO_TI_VIA                 = "TI_VIA";
	public static final String CAMPO_NO_VIA                 = "NO_VIA";
	public static final String CAMPO_NO_VIA_1               = "NO_VIA_1";
	public static final String CAMPO_TI_NMRC                = "TI_NMRC";
	public static final String CAMPO_NU_VIA                 = "NU_VIA";
	public static final String CAMPO_NU_VIA_1               = "NU_VIA_1";
	public static final String CAMPO_TI_INTE                = "TI_INTE";
	public static final String CAMPO_NU_INTE                = "NU_INTE";
	public static final String CAMPO_NU_INTE_1    			= "NU_INTE_1";
	public static final String CAMPO_DE_OTRA_REFE           = "DE_OTRA_REFE";
	public static final String CAMPO_DE_OTRA_REFE_1         = "DE_OTRA_REFE_1";
	public static final String CAMPO_CO_CATA                = "CO_CATA";
	public static final String CAMPO_IN_BLOQ                = "IN_BLOQ";
	public static final String CAMPO_NS_INMB       			= "NS_INMB";
	public static final String CAMPO_TS_USUA_CREA           = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA           = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_MODI           = "TS_USUA_MODI";
	public static final String CAMPO_ID_USUA_MODI           = "ID_USUA_MODI";
	public static final String CAMPO_TI_SITU_INMB_TITU      = "TI_SITU_INMB_TITU";
	public static final String CAMPO_CO_REGI_PART           = "CO_REGI_PART";
	public static final String CAMPO_CO_OFIC_RGST_PART      = "CO_OFIC_RGST_PART";
	public static final String CAMPO_CO_LIBR_PART           = "CO_LIBR_PART" ;
	public static final String CAMPO_NU_PART_ASOC           = "NU_PART_ASOC";
	public static final String CAMPO_NS_ASIE_ASOC           = "NS_ASIE_ASOC";
	public static final String CAMPO_IN_MXTO                = "IN_MXTO";
	public static final String CAMPO_DE_MXTO                = "DE_MXTO";
	public static final String CAMPO_TI_PROP                = "TI_PROP";
	public static final String CAMPO_DE_INMB                = "DE_INMB";
	//MODIFICADO JBUGARIN 25/09/06
	public static final String CAMPO_CO_REGI_PRES                = "CO_REGI_PRES";
	public static final String CAMPO_NU_HOJA_PRES                = "NU_HOJA_PRES";
	public static final String CAMPO_AA_HOJA_PRES                = "AA_HOJA_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES                = "CO_OFIC_RGST_PRES";
	//

	/**
	 * Constructor for DboTTInmbTitu
	 */
	public DboTTInmbTitu() throws DBException {
		super();
	}

	public DboTTInmbTitu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboMensaje(DBConnection) */


	public DboTTInmbTitu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboMensaje(DBConnection, String) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_INMB_TITU");

		setDescription("Object Description Goes Here");
		
		addField("AA_TITU","CHAR", 4, true, "AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "NU_TITU"); //verificar
		addField("CO_ACTO_RGST","CHAR", 5, false, "CO_ACTO_RGST");
		addField("CO_REGI","CHAR", 2, true, "CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CO_OFIC_RGST");
		addField("NS_AFEC","NUMBER", 38, true, "NS_AFEC");
		addField("GR_IMPO","CHAR", 2, true, "GR_IMPO");
		addField("UB_GEOG","CHAR", 8, true, "UB_GEOG");
		addField("TI_ZONA","CHAR", 2, true, "TI_ZONA");
		addField("NO_ZONA","VARCHAR", 100, true, "NO_ZONA");
		addField("NO_ZONA_1","VARCHAR", 100, true, "NO_ZONA_1");
		addField("TI_VIA","CHAR", 2, true, "TI_VIA");
		addField("NO_VIA","VARCHAR", 100, true, "NO_VIA");
		addField("NO_VIA_1","VARCHAR", 100, true, "NO_VIA_1");
		addField("TI_NMRC","CHAR", 2, true, "TI_NMRC");
		addField("NU_VIA","VARCHAR", 100, true, "NU_VIA");
		addField("NU_VIA_1","VARCHAR", 100, true, "NU_VIA_1");
		addField("TI_INTE","CHAR", 2, true, "TI_INTE");
		addField("NU_INTE","VARCHAR", 100, true, "NU_INTE");
		addField("NU_INTE_1","VARCHAR", 100, true, "NU_INTE_1");
		addField("DE_OTRA_REFE","VARCHAR", 50, true, "DE_OTRA_REFE");
		addField("DE_OTRA_REFE_1","VARCHAR", 50, true, "DE_OTRA_REFE_1");
		addField("CO_CATA","CHAR", 8, true, "CO_CATA");
		addField("IN_BLOQ","CHAR", 2, true, "IN_BLOQ");
		addField("NS_INMB","NUMBER", 5, false, "NS_INMB");
		addField("TS_USUA_CREA","NUMBER", 22, true, "TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "ID_USUA_CREA");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("ID_USUA_MODI","CHAR", 5, true, "ID_USUA_MODI");
		addField("TI_SITU_INMB_TITU","CHAR", 1, true, "TI_SITU_INMB_TITU");
		addField("CO_REGI_PART","CHAR", 2, true, "CO_REGI_PART");
		addField("CO_OFIC_RGST_PART","CHAR", 2, true, "CO_OFIC_RGST_PART");
		addField("CO_LIBR_PART","CHAR", 3, true, "CO_LIBR_PART");
		addField("NU_PART_ASOC","CHAR", 8, true, "NU_PART_ASOC");
		addField("NS_ASIE_ASOC","NUMBER", 5, true, "NS_ASIE_ASOC");
		addField("IN_MXTO","CHAR", 1, true, "IN_MXTO");
		addField("DE_MXTO","VARCHAR", 100, true, "DE_MXTO");
		addField("TI_PROP","CHAR", 2, true, "TI_PROP");
		addField("DE_INMB","VARCHAR", 50, true, "DE_INMB");
		//modificado 25/09/06
		addField("CO_REGI_PRES","CHAR", 2, false, "CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CO_OFIC_RGST_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "NU_HOJA_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "AA_HOJA_PRES");
		//FALTAN LAS PRIMARY KEYS
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");		
		addKey("CO_ACTO_RGST");
		addKey("NS_INMB");
		
	}	
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTTInmbTitu();
	} /* getThisDBObj() */


}

	
	