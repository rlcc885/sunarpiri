/*
* DboCriteriosAsigna.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboCriteriosAsigna extends DBObject {

	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_CERTIFICADO_ID = "CERTIFICADO_ID";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_GRUPO_ID = "GRUPO_ID";

	public DboCriteriosAsigna() throws DBException {
		super();
	} /* DboCriteriosAsigna() */


	public DboCriteriosAsigna(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboCriteriosAsigna(DBConnection) */


	public DboCriteriosAsigna(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CRITERIOS_ASIGNA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CRITERIOS_ASIGNA");

		setDescription("Object Description Goes Here");

		addField("CUENTA_ID","NUMBER", 22, false, "CAMPO_CUENTA_ID");
		addField("CERTIFICADO_ID","NUMBER", 22, false, "CAMPO_CERTIFICADO_ID");
		addField("ESTADO","CHAR", 1, false, "CAMPO_ESTADO");
		addField("REG_PUB_ID","CHAR", 2, false, "CAMPO_REG_PUB_ID");
		addField("OFIC_REG_ID","CHAR", 2, false, "CAMPO_OFIC_REG_ID");
		addField("GRUPO_ID","NUMBER", 22, false, "CAMPO_GRUPO_ID");

		addKey("CERTIFICADO_ID");
		addKey("CUENTA_ID");
		addKey("GRUPO_ID");
		addKey("OFIC_REG_ID");
		addKey("REG_PUB_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboCriteriosAsigna();
	} /* getThisDBObj() */
} /* DboCriteriosAsigna */

