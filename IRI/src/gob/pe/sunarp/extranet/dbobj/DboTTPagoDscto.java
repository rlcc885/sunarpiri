package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTPagoDscto extends DBObject {

	public static final String CAMPO_CO_REGI_PRES      = "CO_REGI_PRES";
	public static final String CAMPO_CO_OFIC_RGST_PRES = "CO_OFIC_RGST_PRES";
	public static final String CAMPO_AA_HOJA_PRES      = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES      = "NU_HOJA_PRES";
	public static final String CAMPO_AA_TITU           = "AA_TITU";
	public static final String CAMPO_NU_TITU           = "NU_TITU";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_OFIC_RGST      = "CO_OFIC_RGST";
	public static final String CAMPO_MOVIMIENTO_ID     = "MOVIMIENTO_ID";
	public static final String CAMPO_FEC_HOR     	   = "FEC_HOR";
	public static final String CAMPO_LINEA_PREPAGO_ID  = "LINEA_PREPAGO_ID";
	public static final String CAMPO_CUENTA_ID  	   = "CUENTA_ID";
	public static final String CAMPO_CONSUMO_ID        = "CONSUMO_ID";
	public static final String CAMPO_MONTO             = "MONTO";

	public DboTTPagoDscto() throws DBException {
		super();
	} /* DboTTPagoDscto() */


	public DboTTPagoDscto(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTPagoDscto(DBConnection) */


	public DboTTPagoDscto(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTTPagoDscto(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_PAGO_DSCTO");

		setDescription("Object Description Goes Here");

		addField("CO_REGI_PRES","CHAR", 2, false, "CAMPO_CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CAMPO_CO_OFIC_RGST_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES","CHAR", 8, false, "CAMPO_NU_HOJA_PRES");		
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "CAMPO_NU_TITU");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CAMPO_CO_OFIC_RGST");
		addField("MOVIMIENTO_ID","NUMBER", 12, false, "CAMPO_MOVIMIENTO_ID");
		addField("FEC_HOR","NUMBER", 80, false, "CAMPO_FEC_HOR");
		addField("LINEA_PREPAGO_ID","NUMBER", 12, false, "CAMPO_LINEA_PREPAGO_ID");
		addField("CUENTA_ID","NUMBER", 12, false, "CAMPO_CUENTA_ID");
		addField("CONSUMO_ID","NUMBER", 12, false, "CAMPO_CONSUMO_ID");
		addField("MONTO","NUMBER", 12, 2, false, "CAMPO_MONTO");
		
		addKey("CO_REGI_PRES");
		addKey("CO_OFIC_RGST_PRES");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */
} /* DboTTPagoDscto */

