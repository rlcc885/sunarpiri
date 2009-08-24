/*
* DboMail.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboMail extends DBObject {

	public static final String CAMPO_MAIL_REFNUM = "MAIL_REFNUM";
	public static final String CAMPO_MAIL_MFROM = "MAIL_MFROM";
	public static final String CAMPO_MAIL_MTO = "MAIL_MTO";
	public static final String CAMPO_MAIL_MCOPY = "MAIL_MCOPY";
	public static final String CAMPO_MAIL_MBCC = "MAIL_MBCC";
	public static final String CAMPO_MAIL_SUBJ = "MAIL_SUBJ";
	public static final String CAMPO_MAIL_BODY = "MAIL_BODY";
	public static final String CAMPO_MAIL_ESTADO = "MAIL_ESTADO";
	public static final String CAMPO_MAIL_SENDER_AGENT = "MAIL_SENDER_AGENT";
	public static final String CAMPO_MAIL_STORE_DATE = "MAIL_STORE_DATE";
	public static final String CAMPO_MAIL_SEND_DATE = "MAIL_SEND_DATE";
	public static final String CAMPO_MAIL_FIELD1 = "MAIL_FIELD1";
	public static final String CAMPO_MAIL_FIELD_2 = "MAIL_FIELD_2";

	public DboMail() throws DBException {
		super();
	} /* DboMail() */


	public DboMail(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboMail(DBConnection) */


	public DboMail(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* MAIL(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("MAIL");

		setDescription("Object Description Goes Here");

		addField("MAIL_REFNUM","auto-inc", 0, false, "null");
		addField("MAIL_MFROM","VARCHAR", 254, false, "null");
		addField("MAIL_MTO","VARCHAR", 254, false, "null");
		addField("MAIL_MCOPY","VARCHAR", 254, true, "null");
		addField("MAIL_MBCC","VARCHAR", 254, true, "null");
		addField("MAIL_SUBJ","VARCHAR", 255, false, "null");
		addField("MAIL_BODY","VARCHAR", 2279, false, "null");
		addField("MAIL_ESTADO","NUMBER", 22, false, "null");
		addField("MAIL_SENDER_AGENT","VARCHAR", 126, true, "null");
		addField("MAIL_STORE_DATE","NUMBER", 22, false, "null");
		addField("MAIL_SEND_DATE","NUMBER", 22, true, "null");
		addField("MAIL_FIELD1","VARCHAR", 254, true, "null");
		addField("MAIL_FIELD_2","NUMBER", 22, true, "null");

		addKey("MAIL_REFNUM");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboMail();
	} /* getThisDBObj() */
} /* DboMail */

