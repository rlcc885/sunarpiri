package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTPago extends DBObject {

	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_MO_SERV           = "MO_SERV";
	public static final String CAMPO_CO_FR_PAGO        = "CO_FR_PAGO";
	public static final String CAMPO_DE_FR_PAGO        = "DE_FR_PAGO";
	public static final String CAMPO_NU_OPER           = "NU_OPER";
	public static final String CAMPO_FE_PAGO           = "FE_PAGO";
	//public static final String CAMPO_HO_PAGO           = "HO_PAGO";
	public static final String CAMPO_CO_TIPO_PAGO      = "CO_TIPO_PAGO";
	public static final String CAMPO_DE_TIPO_PAGO      = "DE_TIPO_PAGO";
	public static final String CAMPO_IN_SERV      	   = "IN_SERV";

	public DboTTPago() throws DBException {
		super();
	} /* DboTTPago() */


	public DboTTPago(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTPago(DBConnection) */


	public DboTTPago(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTTPago(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_PAGO");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");		
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("MO_SERV","NUMBER", 12, 2, false, "CAMPO_MO_SERV");
		addField("CO_FR_PAGO","CHAR", 2, false, "CAMPO_CO_FR_PAGO");
		addField("DE_FR_PAGO","VARCHAR", 50, false, "CAMPO_DE_FR_PAGO");
		addField("NU_OPER","CHAR", 20, true, "CAMPO_NU_OPER");
		addField("FE_PAGO","NUMBER", 22, false, "CAMPO_FE_PAGO");
		//addField("HO_PAGO","CHAR", 6, false, "CAMPO_HO_PAGO");
		addField("CO_TIPO_PAGO","CHAR", 2, false, "CAMPO_CO_TIPO_PAGO");
		addField("DE_TIPO_PAGO","VARCHAR", 50, false, "CAMPO_DE_TIPO_PAGO");
		addField("IN_SERV","CHAR", 2, false, "CAMPO_IN_SERV");
		
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */
} /* DboTTPago */
