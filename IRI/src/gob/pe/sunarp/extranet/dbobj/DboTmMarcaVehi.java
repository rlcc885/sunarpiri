/*
* DboTmMarcaVehi.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmMarcaVehi extends DBObject {

	public static final String CAMPO_COD_MARCA = "COD_MARCA";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";

	public DboTmMarcaVehi() throws DBException {
		super();
	} /* DboTmMarcaVehi() */


	public DboTmMarcaVehi(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmMarcaVehi(DBConnection) */


	public DboTmMarcaVehi(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_MARCA_VEHI(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_MARCA_VEHI");

		setDescription("Object Description Goes Here");

		addField("COD_MARCA","CHAR", 4, false, "CAMPO_COD_MARCA");
		addField("DESCRIPCION","VARCHAR", 35, true, "CAMPO_DESCRIPCION");

		addKey("COD_MARCA");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmMarcaVehi();
	} /* getThisDBObj() */
} /* DboTmMarcaVehi */

