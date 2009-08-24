/*
* DboTmModeloVehi.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmModeloVehi extends DBObject {

	public static final String CAMPO_COD_MODELO = "COD_MODELO";
	public static final String CAMPO_COD_MARCA = "COD_MARCA";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmModeloVehi() throws DBException {
		super();
	} /* DboTmModeloVehi() */


	public DboTmModeloVehi(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmModeloVehi(DBConnection) */


	public DboTmModeloVehi(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_MODELO_VEHI(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_MODELO_VEHI");

		setDescription("Object Description Goes Here");

		addField("COD_MODELO","CHAR", 5, false, "CAMPO_COD_MODELO");
		addField("COD_MARCA","CHAR", 4, true, "CAMPO_COD_MARCA");
		addField("DESCRIPCION","VARCHAR", 35, true, "CAMPO_DESCRIPCION");

		addKey("COD_MODELO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmModeloVehi();
	} /* getThisDBObj() */
} /* DboTmModeloVehi */

