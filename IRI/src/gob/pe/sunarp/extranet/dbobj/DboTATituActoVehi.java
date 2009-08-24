package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTATituActoVehi extends DBObject {

	public static final String CAMPO_AN_TITU      = "AN_TITU";
	public static final String CAMPO_NO_HOJA      = "NO_HOJA";
	public static final String CAMPO_CO_ZONA      = "CO_ZONA";
	public static final String CAMPO_CO_SEDE      = "CO_SEDE";
	public static final String CAMPO_NO_TITU 	  = "NO_TITU";
	public static final String CAMPO_NS_ACTO      = "NS_ACTO";
	public static final String CAMPO_CO_ACTO      = "CO_ACTO";
	public static final String CAMPO_CO_CONC      = "CO_CONC";
	public static final String CAMPO_MONT      	  = "MONT";
	public static final String CAMPO_MONT_PGDO    = "MONT_PGDO";
	public static final String CAMPO_FORM_PAGO    = "FORM_PAGO";
	public static final String CAMPO_SALD         = "SALD";
	public static final String CAMPO_OBSV         = "OBSV";
	public static final String CAMPO_CO_TIPO_MONE = "CO_TIPO_MONE";
	public static final String CAMPO_NO_PLAC      = "NO_PLAC";
	public static final String CAMPO_NO_SECU_PLAC = "NO_SECU_PLAC";
	public static final String CAMPO_NO_PTDA      = "NO_PTDA";
	public static final String CAMPO_IN_OPC1      = "IN_OPC1";
	public static final String CAMPO_IN_OPC2      = "IN_OPC2";
	public static final String CAMPO_IN_OPC3      = "IN_OPC3";
	public static final String CAMPO_IN_PAGO      = "IN_PAGO";
	public static final String CAMPO_DE_ACTO      = "DE_ACTO";
	public static final String CAMPO_CO_FORM_PAGO = "CO_FORM_PAGO";
	public static final String CAMPO_DE_MONE      = "DE_MONE";
	
	/**
	 * Constructor for DboTATituActoVehi
	 */
	public DboTATituActoVehi() throws DBException {
		super();
	}

	public DboTATituActoVehi(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection) */


	public DboTATituActoVehi(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection, String) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_TITU_ACTO_VEHI");

		setDescription("Object Description Goes Here");
		
		addField("AN_TITU","CHAR", 4, false, "AN_TITU");
		addField("NO_HOJA","CHAR", 8, false, "NO_HOJA");
		addField("CO_SEDE","CHAR", 2, false, "CO_SEDE");
		addField("CO_ZONA","CHAR", 2, false, "CO_ZONA");
		addField("CO_ACTO","CHAR", 2, false, "CO_ACTO");
		addField("NS_ACTO","NUMBER", 3, false, "NS_ACTO");		
		addField("NO_TITU","NUMBER", 8, true, "NO_TITU");
        addField("CO_CONC","CHAR", 2, true, "CO_CONC");
		addField("MONT","NUMBER", 12,2, true, "MONT");
		addField("MONT_PGDO","NUMBER", 12,2, true, "MONT_PGDO");
		addField("FORM_PAGO","VARCHAR", 240, true, "FORM_PAGO");
		addField("SALD","NUMBER", 12,2, true, "SALD");
		addField("OBSV","VARCHAR", 250, true, "OBSV");
		addField("CO_TIPO_MONE","CHAR", 2, true, "CO_TIPO_MONE");
		addField("NO_PLAC","CHAR", 7, true, "NO_PLAC");
		addField("NO_SECU_PLAC","NUMBER", 3, true, "NO_SECU_PLAC");
		addField("NO_PTDA","CHAR", 8, true, "NO_PTDA");
		addField("IN_OPC1","CHAR", 1, true, "IN_OPC1");
		addField("IN_OPC2","CHAR", 1, true, "IN_OPC2");
		addField("IN_OPC3","CHAR", 1, true, "IN_OPC3");
		addField("IN_PAGO","CHAR", 1, true, "IN_PAGO");
		addField("DE_ACTO","VARCHAR", 80, true, "DE_ACTO");
		addField("CO_FORM_PAGO","CHAR", 2, true, "CO_FORM_PAGO");
		addField("DE_MONE","VARCHAR", 45, true, "DE_MONE");
		
		//keys
		addKey("AN_TITU");	
		addKey("NO_HOJA");	
		addKey("CO_ZONA");
		addKey("CO_SEDE");		
		addKey("CO_ACTO");	
		addKey("NS_ACTO");	
	
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTATituActoVehi();
	} /* getThisDBObj() */

}

