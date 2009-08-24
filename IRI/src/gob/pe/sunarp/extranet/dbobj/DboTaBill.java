/*
 * Created on 10-ene-07
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
public class DboTaBill extends DBObject {
	
	public static final String CAMPO_ID_DIAR      = "ID_DIAR";
	public static final String CAMPO_CO_OPER      = "CO_OPER";
	public static final String CAMPO_CO_BIMO      = "CO_BIMO";
	public static final String CAMPO_CANT 	      = "CANT";
	public static final String CAMPO_MO_TOTA      = "MO_TOTA";
	public static final String CAMPO_CO_TIPO_MONE = "CO_TIPO_MONE";
	
	
	public DboTaBill() throws DBException {
			super();
		} 


		public DboTaBill(DBConnection theConnection) throws DBException {
			super(theConnection);
		} 


		public DboTaBill(DBConnection theConnection, String theUser) throws DBException {
			super(theConnection);
		} 


		protected synchronized void setupFields() throws DBException {
			setTargetTable("TA_BILL");

			setDescription("Object Description Goes Here");

			addField("ID_DIAR","NUMBER", 16 ,false, "null");
			addField("CO_OPER","CHAR", 2, false, "null");
			addField("CO_BIMO","CHAR", 2, false, "null");
			addField("CANT","NUMBER", 5, false, "null");
			addField("MO_TOTA","NUMBER", 12,2, false, "null");
			addField("CO_TIPO_MONE","NUMBER", 1, true, "null");
			

			addKey("ID_DIAR");
			addKey("CO_BIMO");
		} /* setupFields() */


		public DBObject getThisDBObj() throws DBException {
			return new DboTaBill();
		} /* getThisDBObj() */
	} 
