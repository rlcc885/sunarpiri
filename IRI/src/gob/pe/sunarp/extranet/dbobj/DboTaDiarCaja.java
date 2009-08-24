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
public class DboTaDiarCaja extends DBObject{

	public static final String CAMPO_ID_DIAR           = "ID_DIAR";
	public static final String CAMPO_CO_CAJA           = "CO_CAJA";
	public static final String CAMPO_CO_EMPL_CAJE      = "CO_EMPL_CAJE";
	public static final String CAMPO_CO_EMPL_TESO      = "CO_EMPL_TESO";
	public static final String CAMPO_FH_APER           = "FH_APER";
	public static final String CAMPO_FH_APER_CAJE      = "FH_APER_CAJE";
	public static final String CAMPO_FH_CIER_CAJE      = "FH_CIER_CAJE";
	public static final String CAMPO_CO_REGI           = "CO_REGI";
	public static final String CAMPO_CO_SEDE           = "CO_SEDE";
	public static final String CAMPO_CO_ZONA           = "CO_ZONA";
	public static final String CAMPO_DE_CAJA           = "DE_CAJA";
	
	public DboTaDiarCaja() throws DBException {
						super();
					} 


		public DboTaDiarCaja(DBConnection theConnection) throws DBException {
						super(theConnection);
					} 


		public DboTaDiarCaja(DBConnection theConnection, String theUser) throws DBException {
						super(theConnection);
					} 


		protected synchronized void setupFields() throws DBException {
						setTargetTable("TA_DIAR_CAJA");

						setDescription("Object Description Goes Here");

						addField("ID_DIAR","NUMBER", 16 ,false, "null");
						addField("CO_CAJA","CHAR", 2, false, "null");
						addField("CO_EMPL_CAJE","NUMBER", 16, true, "null");
						addField("CO_EMPL_TESO","NUMBER", 16, true, "null");
						addField("FH_APER","NUMBER", 22, false, "null");
						addField("FH_APER_CAJE","NUMBER",22 , true, "null");
						addField("FH_CIER_CAJE","NUMBER", 22, true, "null");
						addField("CO_REGI","CHAR",2 , true, "null");
						addField("CO_SEDE","CHAR",2 , true, "null");
						addField("CO_ZONA","CHAR",2 , true, "null");
						addField("DE_CAJA","VARCHAR",30 , true, "null");
					
						addKey("ID_DIAR");
					} /* setupFields() */


		public DBObject getThisDBObj() throws DBException {
						return new DboTaDiarCaja();
					} /* getThisDBObj() */
				} 

