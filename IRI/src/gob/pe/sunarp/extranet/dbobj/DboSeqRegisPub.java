/*
 * Created on Jan 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;

/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DboSeqRegisPub extends DBObject{
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_SEQ_VALOR = "SEQ_VALOR";
	
	public DboSeqRegisPub() throws DBException {
			super();
	}
	public DboSeqRegisPub(DBConnection theConnection) throws DBException {
			super(theConnection);
		} 


	public DboSeqRegisPub(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} 


	protected synchronized void setupFields() throws DBException {
		setTargetTable("SEQ_REGIS_PUB");

		setDescription("Object Description Goes Here");

		addField("REG_PUB_ID","CHAR",2, false, "null");
		addField("SEQ_VALOR","NUMBER", 4, true, "null");
		addKey("REG_PUB_ID");
	} 


	public DBObject getThisDBObj() throws DBException {
		return new DboSeqRegisPub();
	}	

}
