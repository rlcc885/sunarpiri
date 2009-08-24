
package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;


public class DboAudBusqMasRelacional extends DBObject {

	private static final long serialVersionUID = -7290977790161826535L;
	
	public static final String CAMPO_AUD_BUSQ_MAS_RELACIONAL_ID = "AUD_BUSQ_MAS_RELACIONAL_ID";
	public static final String CAMPO_TS_BUSQUEDA = "TS_BUSQUEDA";
	public static final String CAMPO_CRI_BUSQUEDA = "CRI_BUSQUEDA";
	public static final String CAMPO_DES_REG_PUBLICO = "DES_REG_PUBLICO";
	public static final String CAMPO_TRANS_ID = "TRANS_ID";

	public DboAudBusqMasRelacional() throws DBException {
		super();
	}

	public DboAudBusqMasRelacional(DBConnection theConnection) throws DBException {
		super(theConnection);
	} 


	public DboAudBusqMasRelacional(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	}


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_MAS_RELACIONAL");

		setDescription("Object Description Goes Here");

		addField("AUD_BUSQ_MAS_RELACIONAL_ID","auto-inc", 0, false, "CAMPO_AUD_BUSQ_MAS_RELACIONAL_ID");
		addField("TS_BUSQUEDA","NUMBER", 22, false, "CAMPO_TS_BUSQUEDA");
		addField("CRI_BUSQUEDA","VARCHAR", 600, true, "CAMPO_CRI_BUSQUEDA");
		addField("DES_REG_PUBLICO","VARCHAR", 600, true, "CAMPO_DES_REG_PUBLICO");
		addField("TRANS_ID","NUMBER", 22, false, "CAMPO_TRANS_ID");

		addKey("AUD_BUSQ_MAS_RELACIONAL_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqMasRelacional();
	} /* getThisDBObj() */
} 

