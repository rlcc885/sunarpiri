/*
 * Created on 26-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.dbobj;
import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * @author jbugarin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DboTmTipoVenta extends DBObject {

	public static final String CAMPO_TIPO_VENT = "TIPO_VENT";
	public static final String CAMPO_DESCRIPCION = "DESCRIPCION";
	

	public DboTmTipoVenta() throws DBException {
		super();
	} /* DboAbono() */


	public DboTmTipoVenta(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAbono(DBConnection) */


	public DboTmTipoVenta(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ABONO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_VENT");

		setDescription("Object Description Goes Here");

		addField("TIPO_VENT","CHAR", 1, false, "null");
		addField("DESCRIPCION","VARCHAR", 40, false, "null");
		
		
		addKey("TIPO_VENT");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
		return new DboTmTipoVenta();
	} /* getThisDBObj() */
} /* DboAbono */


