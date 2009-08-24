/*
* DboPagoCheque.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboPagoCheque extends DBObject {

	public static final String CAMPO_ABONO_ID = "ABONO_ID";
	public static final String CAMPO_BANCO_ID = "BANCO_ID";
	public static final String CAMPO_NUMERO = "NUMERO";
	public static final String CAMPO_TPO_CHEQ = "TPO_CHEQ";

	public DboPagoCheque() throws DBException {
		super();
	} /* DboPagoCheque() */


	public DboPagoCheque(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboPagoCheque(DBConnection) */


	public DboPagoCheque(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* PAGO_CHEQUE(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("PAGO_CHEQUE");

		setDescription("Object Description Goes Here");

		addField("ABONO_ID","NUMBER", 22, false, "null");
		addField("BANCO_ID","NUMBER", 22, false, "null");
		addField("NUMERO","VARCHAR", 15, false, "null");
		addField("TPO_CHEQ","CHAR", 1, false, "null");

		addKey("ABONO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboPagoCheque();
	} /* getThisDBObj() */
} /* DboPagoCheque */

