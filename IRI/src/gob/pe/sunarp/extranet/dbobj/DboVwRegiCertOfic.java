/*
* DboVwRegiCertOfic.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVwRegiCertOfic extends DBObject {

	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_OFIC_NOMBRE = "OFIC_NOMBRE";
	public static final String CAMPO_CERTIFICADO_NOMBRE = "CERTIFICADO_NOMBRE";
	public static final String CAMPO_CANTIDAD = "CANTIDAD";
	public static final String CAMPO_TIPO_CERTIFICADO = "TIPO_CERTIFICADO";

	public DboVwRegiCertOfic() throws DBException {
		super();
	} /* DboVwRegiCertOfic() */


	public DboVwRegiCertOfic(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVwRegiCertOfic(DBConnection) */


	public DboVwRegiCertOfic(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VW_REGI_CERT_OFIC(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VW_REGI_CERT_OFIC");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR", 2, false, "CAMPO_REG_PUB_ID");
		addField("OFIC_REG_ID","CHAR", 2, false, "CAMPO_OFIC_REG_ID");
		addField("OFIC_NOMBRE","VARCHAR", 30, false, "CAMPO_OFIC_NOMBRE");
		addField("CERTIFICADO_NOMBRE","VARCHAR", 100, true, "CAMPO_CERTIFICADO_NOMBRE");
		addField("CANTIDAD","NUMBER", 22, true, "CAMPO_CANTIDAD");
		addField("TIPO_CERTIFICADO","CHAR", 1, true, "CAMPO_TIPO_CERTIFICADO");

	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVwRegiCertOfic();
	} /* getThisDBObj() */
} /* DboVwRegiCertOfic */

