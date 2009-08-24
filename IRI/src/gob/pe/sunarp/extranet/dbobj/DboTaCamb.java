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
public class DboTaCamb extends DBObject{

	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_FECHA             = "FECHA";
	public static final String CAMPO_CO_TIPO_MONE      = "CO_TIPO_MONE";
	public static final String CAMPO_MONTO             = "MONTO";

	public DboTaCamb() throws DBException {
				super();
			} 


			public DboTaCamb(DBConnection theConnection) throws DBException {
				super(theConnection);
			} 


			public DboTaCamb(DBConnection theConnection, String theUser) throws DBException {
				super(theConnection);
			} 


			protected synchronized void setupFields() throws DBException {
				setTargetTable("TA_CAMB");

				setDescription("Object Description Goes Here");

				addField("CO_REGI","CHAR", 2 ,false, "null");
				addField("FECHA","NUMBER", 22, false, "null");
				addField("CO_TIPO_MONE","NUMBER", 1, true, "null");
				addField("MONTO","NUMBER", 12,2, true, "null");
				
				addKey("CO_REGI");
				addKey("FECHA");
			} /* setupFields() */


			public DBObject getThisDBObj() throws DBException {
				return new DboTaCamb();
			} /* getThisDBObj() */
		} 

