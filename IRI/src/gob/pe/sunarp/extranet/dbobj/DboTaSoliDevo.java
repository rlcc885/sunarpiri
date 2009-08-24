/*
 * Created on Jan 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;

/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DboTaSoliDevo extends DBObject{
	
	public static final String CAMPO_ID_SOLI_DEVO = "ID_SOLI_DEVO";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_AA_TRAM = "AA_TRAM";
	public static final String CAMPO_NU_TRAM = "NU_TRAM";
	public static final String CAMPO_FE_TRAM = "FE_TRAM";
	public static final String CAMPO_NU_INFO = "NU_INFO";
	public static final String CAMPO_FE_INFO = "FE_INFO";
	public static final String CAMPO_NU_RESO = "NU_RESO";
	public static final String CAMPO_FE_RESO = "FE_RESO";
	public static final String CAMPO_ESTA = "ESTA";
	public static final String CAMPO_FE_SOLI = "FE_SOLI";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_CUENTA_ID_DEV = "CUENTA_ID_DEV";
	public static final String CAMPO_TIPO_USR = "TIPO_USR";
	public static final String CAMPO_MONTO = "MONTO";
	
	public DboTaSoliDevo() throws DBException {
		super();
	}

	public DboTaSoliDevo(DBConnection theConnection) throws DBException {
		super(theConnection);
	}


	public DboTaSoliDevo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} 


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_SOLI_DEVO");

		setDescription("Object Description Goes Here");
		addField("ID_SOLI_DEVO","auto-inc", 0, false, "null");
		addField("REG_PUB_ID","CHAR", 2, true, "null");
		addField("OFIC_REG_ID","CHAR", 2, true, "null");
		addField("SOLICITUD_ID","NUMBER", 22, true, "null");
		addField("CUENTA_ID","NUMBER", 22, true, "null");
		addField("AA_TRAM","CHAR", 4, true, "null");
		addField("NU_TRAM","CHAR", 6, true, "null");
		addField("FE_TRAM","NUMBER", 22, true, "null");
		addField("NU_INFO","VARCHAR", 50, true, "null");
		addField("FE_INFO","NUMBER", 22, true, "null");
		addField("NU_RESO","VARCHAR", 50, true, "null");
		addField("FE_RESO","NUMBER", 22, true, "null");
		addField("ESTA","CHAR", 1, true, "null");
		addField("FE_SOLI","NUMBER", 22, true, "null");
		addField("CUENTA_ID_DEV","NUMBER", 22, true, "null");
		addField("TIPO_USR","CHAR", 1, true, "null");
		addField("MONTO","NUMBER", 22, true, "null");
		addKey("ID_SOLI_DEVO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
		return new DboTaSoliDevo();
	} /* getThisDBObj() */

}
