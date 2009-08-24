package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTATituDocu extends DBObject {
	
	public static final String CAMPO_AN_TITU        	= "AN_TITU";
	public static final String CAMPO_NO_TITU        	= "NO_TITU";
	public static final String CAMPO_NS_DOCU        	= "NS_DOCU";
	public static final String CAMPO_TI_FUNC        	= "TI_FUNC" ;
	public static final String CAMPO_CO_TIPO_DOCU_LEGA  = "CO_TIPO_DOCU_LEGA";
	public static final String CAMPO_EMIS               = "EMIS";
	public static final String CAMPO_FE_DOCU_LEGA       = "FE_DOCU_LEGA";
	public static final String CAMPO_CO_ACTO            = "CO_ACTO";
	public static final String CAMPO_NS_ACTO            = "NS_ACTO";
	public static final String CAMPO_NO_HOJA            = "NO_HOJA";
	public static final String CAMPO_CO_ZONA            = "CO_ZONA";
	public static final String CAMPO_CO_SEDE            = "CO_SEDE";
	public static final String CAMPO_DE_TIPO_DOCU_LEGA  = "DE_TIPO_DOCU_LEGA";
	public static final String CAMPO_UB_GEOG            = "UB_GEOG";
	public static final String CAMPO_DE_OBSE            = "DE_OBSE";
	public static final String CAMPO_DE_ACTO            = "DE_ACTO";
	/**
	 * Constructor for DboTATituDocu
	 */
	public DboTATituDocu() throws DBException {
		super();
	}
	
	public DboTATituDocu(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_TITU_DOCU");

		setDescription("Object Description Goes Here");

		addField("AN_TITU","CHAR", 4, false, "AN_TITU");
		addField("NO_HOJA","CHAR", 8, false, "NO_HOJA");
		addField("CO_ZONA","CHAR", 2, false, "CO_ZONA");
		addField("CO_SEDE","CHAR", 2, false, "CO_SEDE");
		addField("NS_DOCU","NUMBER", 3, false, "NS_DOCU");
		addField("NO_TITU","NUMBER", 8,true, "NO_TITU");
		addField("TI_FUNC","VARCHAR", 8, true, "TI_FUNC");
		addField("CO_TIPO_DOCU_LEGA","CHAR", 2,true, "CO_TIPO_DOCU_LEGA");
		addField("EMIS","VARCHAR", 80,true, "EMIS");
		addField("FE_DOCU_LEGA","NUMBER", 22, true, "FE_DOCU_LEGA");
		addField("CO_ACTO","CHAR", 2, true, "CO_ACTO");
		addField("NS_ACTO","NUMBER", 3, true, "NS_ACTO");
		addField("DE_TIPO_DOCU_LEGA","VARCHAR", 45, true, "DE_TIPO_DOCU_LEGA");
		addField("UB_GEOG","VARCHAR", 30, true, "UB_GEOG");
		addField("DE_OBSE","VARCHAR", 500, true, "DE_OBSE");
		addField("DE_ACTO","VARCHAR", 80, true, "DE_ACTO");
		
		//KEYS
		addKey("AN_TITU");
		addKey("NO_HOJA");
		addKey("CO_ZONA");
		addKey("CO_SEDE");
		addKey("NS_DOCU");
		
		
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTATituDocu();
	} /* getThisDBObj() */
	

}

