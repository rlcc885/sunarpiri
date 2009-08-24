/*
* DboAbono.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboReciboTitulo extends DBObject {

	/**
	 * 
	 */
	
	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_NS_RECIBO = "NS_RECIBO";
	public static final String CAMPO_AN_RECIBO = "AN_RECIBO";
	public static final String CAMPO_CO_CAJA = "CO_CAJA";
	public static final String CAMPO_NUM_TICK = "NUM_TICK";
	public static final String CAMPO_MONTO_COBR = "MONTO_COBR";
	public static final String CAMPO_FEC_RECIBO = "FEC_RECIBO";
	public static final String CAMPO_CO_REGI_ORIG = "CO_REGI_ORIG";
	public static final String CAMPO_CO_SEDE_ORIG = "CO_SEDE_ORIG";
	public static final String CAMPO_CO_ZONA_ORIG = "CO_ZONA_ORIG";
	public static final String CAMPO_IND_EXTORNO = "IND_EXTORNO";
	public static final String CAMPO_TS_ULT_SYNC = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	

	public DboReciboTitulo() throws DBException {
		super();
	} 


	public DboReciboTitulo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} 


	public DboReciboTitulo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} 


	protected synchronized void setupFields() throws DBException {
		setTargetTable("RECIBO_TITULO");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","NUMBER", 38, false, "null");
		addField("NS_RECIBO","NUMBER", 5, false, "null");
		addField("AN_RECIBO","CHAR", 4, true, "null");
		addField("CO_CAJA","CHAR", 2, true, "null");
		addField("NUM_TICK","CHAR", 8, true, "null");
		addField("MONTO_COBR","NUMBER", 12, true, "null");
		addField("FEC_RECIBO","NUMBER", 22, true, "null");
		addField("CO_REGI_ORIG","CHAR", 2, true, "null");
		addField("CO_SEDE_ORIG","CHAR", 2, true, "null");
		addField("CO_ZONA_ORIG","CHAR", 2, true, "null");
		addField("IND_EXTORNO","CHAR", 1, true, "null");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "null");
		addField("AGNT_SYNC","CHAR", 4, true, "null");
		

		addKey("REFNUM_TITU");
		//addKey("COD_EST_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboReciboTitulo();
	} /* getThisDBObj() */
} 

