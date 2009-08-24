package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTPTituInmb extends DBObject{

	public static final String CAMPO_CO_REGI_PRES         = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES    = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES         = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES         = "NU_HOJA_PRES";
	public static final String CAMPO_AA_TITU              = "AA_TITU";
	public static final String CAMPO_NU_TITU              = "NU_TITU";
	public static final String CAMPO_CO_AREA              = "CO_AREA";
	public static final String CAMPO_CO_EMPL              = "CO_EMPL";
	public static final String CAMPO_MO_TOTA              = "MO_TOTA";
	public static final String CAMPO_SA_ACTU              = "SA_ACTU";
	public static final String CAMPO_FE_VENC              = "FE_VENC";
	public static final String CAMPO_ES_TITU              = "ES_TITU";
	public static final String CAMPO_ET_TITU              = "ET_TITU";
	public static final String CAMPO_ES_TITU_CALI         = "ES_TITU_CALI";
	public static final String CAMPO_TI_REIN              = "TI_REIN";
	public static final String CAMPO_NU_TOMO_DIAR         = "NU_TOMO_DIAR";
	public static final String CAMPO_ID_USUA_MODI         = "ID_USUA_MODI";
	public static final String CAMPO_TS_USUA_CREA         = "TS_USUA_CREA";
	public static final String CAMPO_ID_USUA_CREA         = "ID_USUA_CREA";
	public static final String CAMPO_TS_USUA_MODI         = "TS_USUA_MODI";
	public static final String CAMPO_CO_SECC              = "CO_SECC";
	public static final String CAMPO_FE_REIN              = "FE_REIN";
	public static final String CAMPO_TS_CREA_CHAR         = "TS_CREA_CHAR";
	public static final String CAMPO_MO_DEVO              = "MO_DEVO";
	public static final String CAMPO_ES_DEVO              = "ES_DEVO";
	public static final String CAMPO_IN_SUSP              = "IN_SUSP";
	public static final String CAMPO_TS_PRES              = "TS_PRES";
	public static final String CAMPO_CO_ZONA              = "CO_ZONA";
	
	/**
	 * Constructor for DboTptituInmb
	 */

	public DboTPTituInmb() throws DBException {
		super();
	}

	public DboTPTituInmb(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("TP_TITU_INMB");

		setDescription("Object Description Goes Here");
		
		addField("CO_REGI_PRES","CHAR", 2, false, "CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","VARCHAR", 4, false, "AA_HOJA_PRES");
		addField("NU_HOJA_PRES","VARCHAR", 8, false, "NU_HOJA_PRES");
		addField("AA_TITU","VARCHAR", 4, true, "AA_TITU");
		addField("NU_TITU","VARCHAR", 8, true, "NU_TITU");
		addField("CO_AREA","VARCHAR", 5, true, "CO_AREA");
		addField("CO_EMPL","VARCHAR", 5, true, "CO_EMPL");
		addField("MO_TOTA","NUMBER", 12, 2, true, "MO_TOTA");
		addField("SA_ACTU","NUMBER", 12, 2, true, "SA_ACTU");
		addField("FE_VENC","NUMBER", 22, true, "FE_VENC");
		addField("ES_TITU","CHAR", 2, true, "ES_TITU");
		addField("ET_TITU","CHAR", 2, true, "ET_TITU");
		addField("ES_TITU_CALI","CHAR", 2, true, "ES_TITU_CALI");
		addField("TI_REIN","CHAR", 2, true, "TI_REIN");
		addField("NU_TOMO_DIAR","CHAR", 4, true, "NU_TOMO_DIAR");
		addField("ID_USUA_MODI","CHAR", 5, true, "ID_USUA_MODI");
		addField("TS_USUA_CREA","NUMBER", 22, true, "TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "ID_USUA_CREA");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("CO_SECC","CHAR", 5, true, "CO_SECC");
		addField("FE_REIN","NUMBER", 22, true, "FE_REIN");
		addField("TS_CREA_CHAR","CHAR", 8, true, "TS_CREA_CHAR");
		addField("MO_DEVO","NUMBER", 12, 2, true, "MO_DEVO");
		addField("ES_DEVO","CHAR", 1, true, "ES_DEVO");
		addField("IN_SUSP","CHAR", 1, true, "IN_SUSP");
		addField("TS_PRES","NUMBER", 22, true, "TS_PRES");
		addField("CO_ZONA","CHAR", 2, true, "CO_ZONA");
		
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
		
		
	}	

	public DboTPTituInmb(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTPTituInmb(DBConnection, String) */
	
}

