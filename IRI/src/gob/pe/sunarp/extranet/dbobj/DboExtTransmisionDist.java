/**
 * 
 */
package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;

/**
 * @author jbugarin
 *
 */
public class DboExtTransmisionDist extends DBObject {
	
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_TX_REFNUM = "TX_REFNUM";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TMSTMP_PT = "TMSTMP_PT";
	public static final String CAMPO_TMSTMP_TX = "TMSTMP_TX";
	public static final String CAMPO_TMSTMP_A1 = "TMSTMP_A1";
	public static final String CAMPO_TMSTMP_A2 = "TMSTMP_A2";
	public static final String CAMPO_ERROR_CODIGO = "ERROR_CODIGO";

	public DboExtTransmisionDist() throws DBException {
		super();
		// TODO Apéndice de constructor generado automáticamente
	}
	
	public DboExtTransmisionDist(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboExRecepcion(DBConnection) */


	public DboExtTransmisionDist(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* EX_RECEPCION(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("EX_TRANSMISION_DIST");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("TX_REFNUM","NUMBER", 22, false, "null");
		addField("ESTADO","CHAR", 2, false, "null");
		addField("TMSTMP_PT","NUMBER", 22, true, "null");
		addField("TMSTMP_TX","NUMBER", 22, true, "null");
		addField("TMSTMP_A1","NUMBER", 22, true, "null");
		addField("TMSTMP_A2","NUMBER", 22, true, "null");
		addField("ERROR_CODIGO","CHAR", 6, true, "null");

		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
		addKey("TX_REFNUM");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboExtTransmisionDist();
	} /* getThisDBObj() */
	
}
