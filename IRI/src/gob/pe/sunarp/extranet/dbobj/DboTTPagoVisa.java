package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTPagoVisa extends DBObject {

	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_COD_VISANET       = "COD_VISANET";
	public static final String CAMPO_REFERENCIA        = "REFERENCIA";
	public static final String CAMPO_NU_REFE           = "NU_REFE";
	public static final String CAMPO_TI_MENS           = "TI_MENS";
	public static final String CAMPO_TARJETA           = "TARJETA";
	public static final String CAMPO_TI_TARJ           = "TI_TARJ";
	public static final String CAMPO_FE_TRANS          = "FE_TRANS";
	public static final String CAMPO_FE_PROC           = "FE_PROC";
	public static final String CAMPO_FE_ABON           = "FE_ABON";
	public static final String CAMPO_IMPORTE           = "IMPORTE";
	public static final String CAMPO_CM_TOTA           = "CM_TOTA";
	public static final String CAMPO_CM_VISANET        = "CM_VISANET";
	public static final String CAMPO_IGV               = "IGV";
	public static final String CAMPO_IM_NETO           = "IM_NETO";
	public static final String CAMPO_TI_CAPT           = "TI_CAPT";
	public static final String CAMPO_ST_ABON           = "ST_ABON";
	public static final String CAMPO_CT_BANC           = "CT_BANC";

	public DboTTPagoVisa() throws DBException {
		super();
	} /* DboTTPagoVisa() */


	public DboTTPagoVisa(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTPagoVisa(DBConnection) */


	public DboTTPagoVisa(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTTPagoVisa(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_PAGO_VISA");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");		
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("COD_VISANET","CHAR", 9, false, "CAMPO_COD_VISANET");
		addField("REFERENCIA","CHAR", 12, false, "CAMPO_REFERENCIA");
		addField("NU_REFE","CHAR", 12, false, "CAMPO_NU_REFE");
		addField("TI_MENS","CHAR", 4, false, "CAMPO_TI_MENS");
		addField("TARJETA","CHAR", 19, false, "CAMPO_TARJETA");
		addField("TI_TARJ","CHAR", 3, false, "CAMPO_TI_TARJ");
		addField("FE_TRANS","NUMBER", 22, false, "CAMPO_FE_TRANS");
		addField("FE_PROC","NUMBER", 22, false, "CAMPO_FE_PROC");
		addField("FE_ABON","NUMBER", 22, false, "CAMPO_FE_ABON");
		addField("IMPORTE","NUMBER", 12, 2, false, "CAMPO_IMPORTE");
		addField("CM_TOTA","NUMBER", 12, 2, false, "CAMPO_CM_TOTA");
		addField("CM_VISANET","NUMBER", 12, 2, false, "CAMPO_CM_VISANET");
		addField("IGV","NUMBER", 12, 2, false, "CAMPO_IGV");
		addField("IM_NETO","NUMBER", 12, 2, false, "CAMPO_IM_NETO");
		addField("TI_CAPT","CHAR", 1, false, "CAMPO_TI_CAPT");
		addField("ST_ABON","CHAR", 1, false, "CAMPO_ST_ABON");
		addField("CT_BANC","CHAR", 20, false, "CAMPO_CT_BANC");
		
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */
} /* DboTTPagoVisa */
