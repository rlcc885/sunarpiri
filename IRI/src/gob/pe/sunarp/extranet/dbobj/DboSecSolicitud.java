package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboSecSolicitud extends DBObject {
	
	public static final String CAMPO_ANHO      = "ANHO";
	public static final String CAMPO_SECUENCIA = "SECUENCIA";

	public DboSecSolicitud() throws DBException {
		super();
	} /* DboSecSoliciud() */


	public DboSecSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboSecSoliciud(DBConnection) */


	public DboSecSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboSecSoliciud(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("SEC_SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("ANHO","CHAR", 4, false, "CAMPO_ANHO");
		addField("SECUENCIA","NUMBER", 7, false, "CAMPO_SECUENCIA");

		addKey("ANHO");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboSolicitud();
	} /* getThisDBObj() */
} /* DboSecSoliciud */
