package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboMensaje extends DBObject {

	public static final String CAMPO_REF_NUM_TITU          = "REFNUM_TITU";
	public static final String CAMPO_NS_DETALLE            = "NS_DETALLE";
	public static final String CAMPO_CUO                   = "CUO";
	public static final String CAMPO_CODINSTITUCION        = "CODINSTITUCION";
	public static final String CAMPO_CODESTADO             = "CODESTADO";
	public static final String CAMPO_DESCRIPCION           = "DESCRIPCION";
	public static final String CAMPO_INFO1                 = "INFO1";
	public static final String CAMPO_INFO2                 = "INFO2";
	public static final String CAMPO_FLAG                  = "FLAG";
	
	/**
	 * Constructor for DboMensaje
	 */
	public DboMensaje() throws DBException {
		super();
	}

	public DboMensaje(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboMensaje(DBConnection) */


	public DboMensaje(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboMensaje(DBConnection, String) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("MENSAJE");

		setDescription("Object Description Goes Here");
		
		addField("REFNUM_TITU","NUMBER", 38, false, "REFNUM_TITU");
		addField("NS_DETALLE","NUMBER", 5, false, "NS_DETALLE");
		addField("CUO","VARCHAR", 10, true, "CUO");
		addField("CODINSTITUCION","VARCHAR", 6, true, "CODINSTITUCION");
		addField("CODESTADO","VARCHAR", 20, true, "CODESTADO");
		addField("DESCRIPCION","VARCHAR", 200, true, "DESCRIPCION");
		addField("INFO1","VARCHAR", 1, true, "INFO1");
		addField("INFO2","VARCHAR", 1, true, "INFO2");
		addField("FLAG","VARCHAR", 1, true, "FLAG");
		
		addKey("REFNUM_TITU");
		addKey("NS_DETALLE");
		
		
	}	
	

	public DBObject getThisDBObj() throws DBException {
        return new DboMensaje();
	} /* getThisDBObj() */
}

