package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTATituActo extends DBObject {
	
	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_CO_ACTO_RGST      = "CO_ACTO_RGST";
	public static final String CAMPO_DE_ACTO_RGST      = "DE_ACTO_RGST";
	public static final String CAMPO_NS_AFEC           = "NS_AFEC";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_MO_TOTA_ACTO      = "MO_TOTA_ACTO";
	public static final String CAMPO_IN_EXON           = "IN_EXON";
	public static final String CAMPO_PO_EXON           = "PO_EXON";
	public static final String CAMPO_TS_USUA_CREA      = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA      = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_MODI      = "TS_USUA_MODI";
	public static final String CAMPO_ID_USUA_MODI      = "ID_USUA_MODI";
	public static final String CAMPO_IN_ESTD           = "IN_ESTD";
	public static final String CAMPO_CO_RUBR           = "CO_RUBR";
	public static final String CAMPO_IN_RESE           = "IN_RESE";
	public static final String CAMPO_IN_GENE_ASIE      = "IN_GENE_ASIE";
	public static final String CAMPO_NU_PART           = "NU_PART";
	public static final String CAMPO_CO_LIBR           = "CO_LIBR";
	public static final String CAMPO_DE_LIBR           = "DE_LIBR";	

	public DboTATituActo() throws DBException {
		super();
	} /* DboTATituActo() */


	public DboTATituActo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection) */


	public DboTATituActo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_TITU_ACTO");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");
		addField("CO_ACTO_RGST","CHAR", 5, false, "CAMPO_CO_ACTO_RGST");
		addField("DE_ACTO_RGST","VARCHAR", 80, true, "CAMPO_DE_ACTO_RGST");
		addField("NS_AFEC","NUMBER", 5, false, "CAMPO_NS_AFEC");
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("MO_TOTA_ACTO","NUMBER", 12, 2, true, "CAMPO_MO_TOTA_ACTO");
		addField("IN_EXON","CHAR", 1, true, "CAMPO_IN_EXON");
		addField("PO_EXON","NUMBER", 38, true, "CAMPO_PO_EXON");
		addField("TS_USUA_CREA","NUMBER", 22, true, "CAMPO_TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "CAMPO_ID_USUA_CREA");
		addField("TS_USUA_MODI","NUMBER", 22, true, "CAMPO_TS_USUA_MODI");
		addField("ID_USUA_MODI","CHAR", 5, true, "CAMPO_ID_USUA_MODI");
		addField("IN_ESTD","CHAR", 1, true, "CAMPO_IN_ESTD");
		addField("CO_RUBR","CHAR", 3, true, "CAMPO_CO_RUBR");
		addField("IN_RESE","CHAR", 1, true, "CAMPO_IN_RESE");
		addField("IN_GENE_ASIE","CHAR", 1, true, "CAMPO_IN_GENE_ASIE");
		addField("NU_PART","CHAR", 8, true, "CAMPO_NU_PART");
		addField("CO_LIBR","CHAR", 3, true, "CAMPO_CO_LIBR");
		addField("DE_LIBR","VARCHAR", 120, true, "CAMPO_DE_LIBR");

		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");		
		addKey("CO_ACTO_RGST");
		addKey("NS_AFEC");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */
} /* DboTATituActo */