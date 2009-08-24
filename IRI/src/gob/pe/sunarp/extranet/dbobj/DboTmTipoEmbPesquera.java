package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;

public class DboTmTipoEmbPesquera extends DBObject{
	
	private static final long serialVersionUID = 1909942137337019948L;
	
	public static final String CAMPO_COD_TIPO_EMB_PESQ = "COD_TIPO_EMB_PESQ";
	public static final String CAMPO_DESCRIPCION  = "DESCRIPCION";
	
	public DboTmTipoEmbPesquera() throws DBException {
		super();
	} /* DboTmServicio() */


	public DboTmTipoEmbPesquera(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmServicio(DBConnection) */


	public DboTmTipoEmbPesquera(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_SERVICIO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_TIPO_EMB_PESQ");

		setDescription("Object Description Goes Here");
		
		addField("COD_TIPO_EMB_PESQ","CHAR", 5, false, "null");
		addField("DESCRIPCION","VARCHAR", 40, false, "null");

		addKey("COD_TIPO_EMB_PESQ");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmTipoEmbPesquera();
	} 
}
