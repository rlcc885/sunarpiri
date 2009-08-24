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
public class DboTaCaja extends DBObject{

	public static final String CAMPO_CO_CAJA           = "CO_CAJA";
	public static final String CAMPO_ID_DIAR           = "ID_DIAR";
	public static final String CAMPO_CO_EMPL           = "CO_EMPL";
	public static final String CAMPO_DE_CAJA           = "DE_CAJA";
	public static final String CAMPO_ESTA              = "ESTA";
	public static final String CAMPO_CO_ZONA           = "CO_ZONA";
	public static final String CAMPO_CO_SEDE           = "CO_SEDE";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public DboTaCaja() throws DBException {
					super();
				} 


	public DboTaCaja(DBConnection theConnection) throws DBException {
					super(theConnection);
				} 


	public DboTaCaja(DBConnection theConnection, String theUser) throws DBException {
					super(theConnection);
				} 


	protected synchronized void setupFields() throws DBException {
					setTargetTable("TA_CAJA");

					setDescription("Object Description Goes Here");

					addField("CO_CAJA","CHAR", 2 ,false, "null");
					addField("ID_DIAR","NUMBER", 16, true, "null");
					addField("CO_EMPL","NUMBER", 16, true, "null");
					addField("DE_CAJA","VARCHAR", 30, true, "null");
					addField("ESTA","CHAR", 1, true, "null");
					addField("CO_ZONA","CHAR", 2, true, "null");
					addField("CO_SEDE","CHAR",2 , true, "null");
					addField("CO_REGI","CHAR",2 , true, "null");
					
					addKey("CO_CAJA");
				} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
					return new DboTaCaja();
				} /* getThisDBObj() */
			} 

