package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTPIntrTitu extends DBObject {
		
	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_CO_ACTO_RGST      = "CO_ACTO_RGST";
	public static final String CAMPO_DE_ACTO_RGST      = "DE_ACTO_RGST";
	public static final String CAMPO_NS_AFEC           = "NS_AFEC";
	public static final String CAMPO_NS_INTR_TITU      = "NS_INTR_TITU";
	public static final String CAMPO_TI_INTR           = "TI_INTR";
	public static final String CAMPO_DE_TI_INTR        = "DE_TI_INTR";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_FE_INTR_PUBL      = "FE_INTR_PUBL";
	public static final String CAMPO_TS_USUA_CREA      = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA      = "ID_USUA_CREA";
	public static final String CAMPO_LU_INTR_PUBL      = "LU_INTR_PUBL";	

	public DboTPIntrTitu() throws DBException {
		super();
	} /* DboTPIntrTitu() */


	public DboTPIntrTitu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPIntrTitu(DBConnection) */


	public DboTPIntrTitu(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTPIntrTitu(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TP_INTR_TITU");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");
		addField("CO_ACTO_RGST","CHAR", 5, false, "CAMPO_CO_ACTO_RGST");
		addField("DE_ACTO_RGST","VARCHAR", 80, true, "CAMPO_DE_ACTO_RGST");
		addField("NS_AFEC","NUMBER", 5, false, "CAMPO_NS_AFEC");
		//addField("NS_INTR_TITU","NUMBER", 5, false, "CAMPO_NS_INTR_TITU");
		addField("NS_INTR_TITU","CHAR", 3, false, "CAMPO_NS_INTR_TITU");
		addField("TI_INTR","CHAR", 2, false, "CAMPO_TI_INTR");
		addField("DE_TI_INTR","VARCHAR", 45, false, "CAMPO_DE_TI_INTR");
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("FE_INTR_PUBL","NUMBER", 22, true, "CAMPO_FE_INTR_PUBL");
		addField("TS_USUA_CREA","NUMBER", 22, true, "CAMPO_TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "CAMPO_ID_USUA_CREA");
		addField("LU_INTR_PUBL","VARCHAR", 30, true, "CAMPO_LU_INTR_PUBL");

		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");		
		addKey("CO_ACTO_RGST");
		addKey("NS_AFEC");
		addKey("NS_INTR_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */
} /* DboTPIntrTitu */
