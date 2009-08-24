package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTPActoRgstCal extends DBObject {

	public static final String CAMPO_CO_ACTO_RGST      	= "CO_ACTO_RGST";
	public static final String CAMPO_CO_TARF 	  		= "CO_TARF";
	public static final String CAMPO_CO_AREA 	  		= "CO_AREA";
	public static final String CAMPO_CO_LIBR      		= "CO_LIBR";
	public static final String CAMPO_CO_RUBR      		= "CO_RUBR";
	public static final String CAMPO_CO_RGTR            = "CO_RGTR";
	public static final String CAMPO_DE_ACTO_RGST  	    = "DE_ACTO_RGST";
	public static final String CAMPO_GR_COMP      		= "GR_COMP";
	public static final String CAMPO_IN_INIC     		= "IN_INIC";
	public static final String CAMPO_FE_CREA_ACTO       = "FE_CREA_ACTO";
	public static final String CAMPO_RZ_CREA_ACTO  		= "RZ_CREA_ACTO";
	public static final String CAMPO_TI_PROM_PRE_CALI	= "TI_PROM_PRE_CALI";
	public static final String CAMPO_TI_PROM_CALI  		= "TI_PROM_CALI";
	public static final String CAMPO_DV_PROM_PRE_CALI   = "DV_PROM_PRE_CALI";
	public static final String CAMPO_DV_PROM_CALI       = "DV_PROM_CALI";
	public static final String CAMPO_TI_EXON            = "TI_EXON";
	public static final String CAMPO_PO_EXON            = "PO_EXON";
	public static final String CAMPO_IN_AVIS            = "IN_AVIS";
	public static final String CAMPO_MO_AVIS 			= "MO_AVIS";
	public static final String CAMPO_TI_SITU_ACTO  		= "TI_SITU_ACTO";
	public static final String CAMPO_IN_CONE	        = "IN_CONE";
	public static final String CAMPO_IN_ESTD	        = "IN_ESTD";
	public static final String CAMPO_TS_USUA_CREA       = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA       = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_MODI       = "TS_USUA_MODI";
	public static final String CAMPO_ID_USUA_MODI 	    = "ID_USUA_MODI";
	public static final String CAMPO_NU_AVIS	    	= "NU_AVIS";
	public static final String CAMPO_ES_PART	 	  	= "ES_PART";
	public static final String CAMPO_CO_TIPO_SUMI       = "CO_TIPO_SUMI";
	public static final String CAMPO_CO_GRUP_ACTO  		= "CO_GRUP_ACTO";
	public static final String CAMPO_CO_ACTO_GRAL       = "CO_ACTO_GRAL";
	public static final String CAMPO_CO_TASA      	    = "CO_TASA";
	
	
	/**
	 * Constructor for DboTPActoRgstCal
	 */
	public DboTPActoRgstCal() throws DBException {
		super();
	}

	public DboTPActoRgstCal(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPActoRgstCal(DBConnection) */


	public DboTPActoRgstCal(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTPActoRgstCal(DBConnection, String) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("TP_ACTO_RGST_CAL");

		setDescription("Object Description Goes Here");

		addField("CO_ACTO_RGST","CHAR", 5, false, "CO_ACTO_RGST");
		addField("CO_TARF","CHAR", 3, true, "CO_TARF");
		addField("CO_AREA","CHAR", 5, true, "CO_AREA");
		addField("CO_LIBR","CHAR", 3, true, "CO_LIBR");
		addField("CO_RUBR","CHAR", 3, true, "CO_RUBR");
		addField("CO_RGTR","CHAR", 3, true, "CO_RGTR");
		addField("DE_ACTO_RGST","VARCHAR", 80, true, "DE_ACTO_RGST");
		addField("GR_COMP","NUMBER", 3, true, "GR_COMP");	
		addField("IN_INIC","CHAR", 1, true, "IN_INIC");
		addField("FE_CREA_ACTO","NUMBER", 22, true, "FE_CREA_ACTO");
		addField("RZ_CREA_ACTO","VARCHAR2", 250, true, "RZ_CREA_ACTO");
		addField("TI_PROM_PRE_CALI","NUMBER", 5, 2, true, "TI_PROM_PRE_CALI");
		addField("TI_PROM_CALI","NUMBER", 5, 2, true, "TI_PROM_CALI");
		addField("DV_PROM_PRE_CALI","NUMBER", 5, 2, true, "DV_PROM_PRE_CALI");
		addField("DV_PROM_CALI","NUMBER", 5, 2, true, "DV_PROM_CALI");
		addField("TI_EXON","CHAR", 1, true, "TI_EXON");
		addField("PO_EXON","NUMBER", 5, 2, true, "PO_EXON");
		addField("IN_AVIS","CHAR", 1, true, "IN_AVIS");
		addField("MO_AVIS","NUMBER", 12, 2, true, "MO_AVIS");
		addField("TI_SITU_ACTO","CHAR", 1, true, "TI_SITU_ACTO");
		addField("IN_CONE","CHAR", 1, true, "IN_CONE");
		addField("IN_ESTD","CHAR", 1, true, "IN_ESTD");
		addField("TS_USUA_CREA","NUMBER", 22, true, "TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "ID_USUA_CREA");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("ID_USUA_MODI","CHAR", 5, true, "ID_USUA_MODI");
		addField("NU_AVIS","NUMBER", 3, true, "NU_AVIS");
		addField("ES_PART","CHAR", 2, true, "ES_PART");
		addField("CO_TIPO_SUMI","CHAR", 2, true, "CO_TIPO_SUMI");
		addField("CO_GRUP_ACTO","CHAR", 2, true, "CO_GRUP_ACTO");
		addField("CO_ACTO_GRAL","CHAR", 5, true, "CO_ACTO_GRAL");
		addField("CO_TASA","CHAR", 4, true, "CO_TASA");			
	
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTPActoRgstCal();
	} /* getThisDBObj() */

}



