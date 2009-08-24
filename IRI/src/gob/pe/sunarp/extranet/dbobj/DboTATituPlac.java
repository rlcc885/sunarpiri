package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTATituPlac extends DBObject{

	public static final String CAMPO_AN_TITU        = "AN_TITU";
	public static final String CAMPO_NO_HOJA        = "NO_HOJA";
	public static final String CAMPO_CO_SEDE        = "CO_SEDE";
	public static final String CAMPO_CO_ZONA        = "CO_ZONA";
	public static final String CAMPO_NS_TITU_PLAC   = "NS_TITU_PLAC";
	public static final String CAMPO_NO_TITU        = "NO_TITU";
	public static final String CAMPO_NO_SECU_PLAC   = "NO_SECU_PLAC";
	public static final String CAMPO_NO_PLAC        = "NO_PLAC" ;
	public static final String CAMPO_NO_SERI        = "NO_SERI";
	public static final String CAMPO_FG_PROV        = "FG_PROV";
	public static final String CAMPO_NO_PTDA        = "NO_PTDA";
	public static final String CAMPO_NO_MOTR        = "NO_MOTR";
	public static final String CAMPO_TS_USUA_MODI   = "TS_USUA_MODI";
	public static final String CAMPO_TI_SIST        = "TI_SIST";
	public static final String CAMPO_CO_REGI_DEST   = "CO_REGI_DEST";
	public static final String CAMPO_CO_SEDE_DEST   = "CO_SEDE_DEST";
	public static final String CAMPO_IN_GENE        = "IN_GENE";
	
	
	/**
	 * Constructor for DboTATituPlac
	 */
	public DboTATituPlac() throws DBException {
		super();
	}

	
	public DboTATituPlac(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_TITU_PLAC");

		setDescription("Object Description Goes Here");
		
		addField("AN_TITU","CHAR", 4, false, "AN_TITU");
		addField("NO_HOJA","CHAR", 4, false, "NO_HOJA");
		addField("CO_SEDE","CHAR", 4, false, "CO_SEDE");
		addField("CO_ZONA","CHAR", 4, false, "CO_ZONA");
		addField("NS_TITU_PLAC","NUMBER", 38, false, "NS_TITU_PLAC");
		addField("NO_TITU","NUMBER", 8, true, "NO_TITU");
		addField("NO_SECU_PLAC","NUMBER", 3, true, "NO_SECU_PLAC");
		addField("NO_PLAC","CHAR", 7, true, "NO_PLAC");
		addField("NO_SERI","VARCHAR", 30, true, "NO_SERI");
		addField("FG_PROV","CHAR", 1, true, "FG_PROV");
		addField("NO_PTDA","CHAR", 8, true, "NO_PTDA");
		addField("NO_MOTR","VARCHAR", 30, true, "NO_MOTR");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("TI_SIST","CHAR", 1, true, "TI_SIST");
		addField("CO_REGI_DEST","CHAR", 2, true, "CO_REGI_DEST");
		addField("CO_SEDE_DEST","CHAR", 2, true, "CO_SEDE_DEST");
		addField("IN_GENE","CHAR", 1, true, "IN_GENE");
		
		
		//KEYS
		addKey("AN_TITU");
		addKey("NO_HOJA");
		addKey("CO_SEDE");
		addKey("CO_ZONA");
		addKey("NS_TITU_PLAC");
		
	}

	public DBObject getThisDBObj() throws DBException {
        return new DboTATituPlac();
	} /* getThisDBObj() */
	
}

