/*
* DboAudConsultaTitulo.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudConsultaTitulo extends DBObject {

	public static final String CAMPO_AUD_CONSULTA_TITULO_ID = "AUD_CONSULTA_TITULO_ID";
	public static final String CAMPO_NUM_TITULO = "NUM_TITULO";
	public static final String CAMPO_AA_TITULO = "AA_TITULO";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_TRANS_ID = "TRANS_ID";

	public DboAudConsultaTitulo() throws DBException {
		super();
	} /* DboAudConsultaTitulo() */


	public DboAudConsultaTitulo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudConsultaTitulo(DBConnection) */


	public DboAudConsultaTitulo(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_CONSULTA_TITULO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_CONSULTA_TITULO");

		setDescription("Object Description Goes Here");

		addField("AUD_CONSULTA_TITULO_ID","auto-inc", 0, false, "null");
		addField("NUM_TITULO","CHAR", 8, false, "null");
		addField("AA_TITULO","CHAR", 4, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("TRANS_ID","NUMBER", 22, false, "null");

		addKey("AUD_CONSULTA_TITULO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudConsultaTitulo();
	} /* getThisDBObj() */
} /* DboAudConsultaTitulo */

