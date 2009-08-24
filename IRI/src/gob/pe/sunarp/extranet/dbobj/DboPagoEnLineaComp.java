/*
* DboPagoEnLineaComp.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPagoEnLineaComp extends DBObject {

	public static final String CAMPO_PAGO_COMP_ID = "PAGO_COMP_ID";
	public static final String CAMPO_PAGO_EN_LINEA_ID = "PAGO_EN_LINEA_ID";
	public static final String CAMPO_USR_MODI = "USR_MODI";
	public static final String CAMPO_COMENTARIO = "COMENTARIO";

	public DboPagoEnLineaComp() throws DBException {
		super();
	} /* DboPagoEnLineaComp() */


	public DboPagoEnLineaComp(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPagoEnLineaComp(DBConnection) */


	public DboPagoEnLineaComp(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PAGO_EN_LINEA_COMP(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PAGO_EN_LINEA_COMP");

		setDescription("Object Description Goes Here");

		addField("PAGO_COMP_ID","auto-inc", 0, false, "CAMPO_PAGO_COMP_ID");
		addField("PAGO_EN_LINEA_ID","NUMBER", 22, false, "CAMPO_PAGO_EN_LINEA_ID");
		addField("USR_MODI","VARCHAR", 15, false, "CAMPO_USR_MODI");
		addField("COMENTARIO","VARCHAR", 255, false, "CAMPO_COMENTARIO");

		addKey("PAGO_COMP_ID");
		addKey("PAGO_EN_LINEA_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPagoEnLineaComp();
	} /* getThisDBObj() */
} /* DboPagoEnLineaComp */

