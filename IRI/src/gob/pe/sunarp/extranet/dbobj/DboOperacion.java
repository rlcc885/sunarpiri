package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboOperacion extends DBObject{

	public static final String CAMPO_SECUENCIA             = "SECUENCIA";
	public static final String CAMPO_CUO                   = "CUO";
	public static final String CAMPO_SERVICIO_ID           = "SERVICIO_ID";
	public static final String CAMPO_ESTADO                = "ESTADO";
	public static final String CAMPO_ID_USUARIO            = "ID_USUARIO";
	public static final String CAMPO_CUR                   = "CUR";
	public static final String CAMPO_NO_CUR                = "NO_CUR";
	public static final String CAMPO_FECHA                 = "FECHA";
	public static final String CAMPO_RUTA_XML              = "RUTA_XML";
	public static final String CAMPO_RUTA_RTF              = "RUTA_RTF";
	/**
	 * Constructor for DboOperacion
	 */
	
	public DboOperacion() throws DBException {
		super();
	}

	public DboOperacion(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection) */


	public DboOperacion(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection, String) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("MENSAJE");

		setDescription("Object Description Goes Here");
	
		addField("SECUENCIA","NUMBER", 12, false, "SECUENCIA");
		addField("CUO","CHAR", 10, false, "CUO");
		addField("SERVICIO_ID","NUMBER", 10, false, "SERVICIO_ID");
		
	}

	public DBObject getThisDBObj() throws DBException {
        return new DboOperacion();
	} /* getThisDBObj() */
	
}

