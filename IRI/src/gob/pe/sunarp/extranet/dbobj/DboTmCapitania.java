package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;

public class DboTmCapitania extends DBObject{
	
	private static final long serialVersionUID = -1066249358408286530L;
	
	public static final String CAMPO_CAPITANIA_ID = "COD_CAPITANIA";
	public static final String CAMPO_DESCRIPCION  = "DESCRIPCION";
	
	public DboTmCapitania() throws DBException {
		super();
	} /* DboTmServicio() */


	public DboTmCapitania(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmServicio(DBConnection) */


	public DboTmCapitania(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_SERVICIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_CAPITANIA");

		setDescription("Object Description Goes Here");
		
		addField("COD_CAPITANIA","CHAR", 5, false, "null");
		addField("DESCRIPCION","VARCHAR", 40, false, "null");

		addKey("COD_CAPITANIA");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmCapitania();
	} 
}
