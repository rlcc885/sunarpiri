package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTATabl extends DBObject {

	public static final String CAMPO_VA_COLU      		= "VA_COLU";
	public static final String CAMPO_CO_COLU 	  		= "CO_COLU";
	public static final String CAMPO_DE_VALO       		= "DE_VALO";
	public static final String CAMPO_DE_CORT_VALO       = "DE_CORT_VALO";
	public static final String CAMPO_IN_ESTD            = "IN_ESTD";
	

	/**
	 * Constructor for DboTATabl
	 */
	public DboTATabl() throws DBException {
		super();
	}
	
	public DboTATabl(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection) */


	public DboTATabl(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection, String) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_TABL");

		setDescription("Object Description Goes Here");
		
		addField("VA_COLU","CHAR", 2, false, "VA_COLU");
		addField("CO_COLU","VARCHAR", 17, false, "CO_COLU");
		addField("DE_VALO","VARCHAR", 45, true, "DE_VALO");
		addField("DE_CORT_VALO","VARCHAR", 15, true, "DE_CORT_VALO");
		addField("IN_ESTD","CHAR", 1, true, "IN_ESTD");
		
		addKey("VA_COLU");
		addKey("CO_COLU");
		
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTATabl();
	} /* getThisDBObj() */

}

