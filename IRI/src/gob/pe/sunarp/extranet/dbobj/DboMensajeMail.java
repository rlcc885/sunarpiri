/*
* DboAbono.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboMensajeMail extends DBObject {

	/**
	 * 
	 */
	
	public static final String CAMPO_REFNUM_TITU = "REFNUM_TITU";
	public static final String CAMPO_NS_DETALLE = "NS_DETALLE";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_AA_HOJA_PRES = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES = "NU_HOJA_PRES";
	public static final String CAMPO_FAM_ACTO = "FAM_ACTO";
	public static final String CAMPO_COD_EST_TITU = "COD_EST_TITU";
	public static final String CAMPO_DESC_EST_TITU = "DESC_EST_TITU";
	public static final String CAMPO_FLAG_MAIL = "FLAG_MAIL";
	public static final String CAMPO_TS_USUA_CREA = "TS_USUA_CREA";
	

	public DboMensajeMail() throws DBException {
		super();
	} 


	public DboMensajeMail(DBConnection theConnection) throws DBException {
		super(theConnection);
	} 


	public DboMensajeMail(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} 


	protected synchronized void setupFields() throws DBException {
		setTargetTable("MENSAJE_MAIL");

		setDescription("Object Description Goes Here");

		addField("REFNUM_TITU","NUMBER", 38, false, "null");
		addField("NS_DETALLE","NUMBER", 5, false, "null");
		addField("REG_PUB_ID","CHAR", 2, true, "null");
		addField("OFIC_REG_ID","CHAR", 2, true, "null");
		addField("AA_HOJA_PRES","CHAR", 4, true, "null");
		addField("NU_HOJA_PRES","VARCHAR", 28, true, "null");
		addField("FAM_ACTO","CHAR", 3, true, "null");
		addField("COD_EST_TITU","CHAR", 2, true, "null");
		addField("DESC_EST_TITU","NUMBER", 200, true, "null");
		addField("FLAG_MAIL","VARCHAR", 2, true, "null");
		addField("TS_USUA_CREA","NUMBER", 22, true, "null");
		

		addKey("REFNUM_TITU");
		//addKey("COD_EST_TITU");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboMensajeMail();
	} /* getThisDBObj() */
} 

