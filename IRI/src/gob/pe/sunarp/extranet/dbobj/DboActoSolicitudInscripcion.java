package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;


public class DboActoSolicitudInscripcion extends DBObject {

	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_COD_ACTO = "COD_ACTO";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_COD_RUBRO = "COD_RUBRO";

	public DboActoSolicitudInscripcion () throws DBException {
		super();
	}
 
	public DboActoSolicitudInscripcion (DBConnection theConnection) throws DBException {
		super(theConnection);
	}

	public DboActoSolicitudInscripcion (DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	}

	protected synchronized void setupFields () throws DBException {
		setTargetTable("ACTO_SOLICITUD_INSCRIPCION");

		setDescription("Object Description Goes Here");

		addField("AREA_REG_ID","CHAR", 5, false, "null");
		addField("COD_ACTO","CHAR", 5, false, "null");
		addField("COD_LIBRO","CHAR", 3, false, "null");
		addField("COD_RUBRO","CHAR", 3, false, "null");

		addKey("AREA_REG_ID");
		addKey("COD_ACTO");
		addKey("COD_LIBRO");
		addKey("COD_RUBRO");
	}

	public DBObject getThisDBObj() throws DBException {
        return new DboParametros();
	}
}

