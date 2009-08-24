package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;

public class DboTmTipoAeronave extends DBObject{
	
	private static final long serialVersionUID = 125090547988165730L;
	
	public static final String CAMPO_COD_TIPO_AERONAVE = "COD_TIPO_AERONAVE";
	public static final String CAMPO_DESCRIPCION  = "DESCRIPCION";
	
	public DboTmTipoAeronave() throws DBException {
		super();
	} /* DboTmServicio() */


	public DboTmTipoAeronave(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmServicio(DBConnection) */


	public DboTmTipoAeronave(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_SERVICIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_AERONAVE");

		setDescription("Object Description Goes Here");
		
		addField("COD_TIPO_AERONAVE","CHAR", 5, false, "null");
		addField("DESCRIPCION","VARCHAR", 40, false, "null");

		addKey("COD_TIPO_AERONAVE");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmTipoAeronave();
	}
}
