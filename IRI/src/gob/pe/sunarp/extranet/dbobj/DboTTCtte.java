package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTCtte extends DBObject{

	public static final String CAMPO_AN_TITU      		= "AN_TITU";
	public static final String CAMPO_NO_HOJA      		= "NO_HOJA";
	public static final String CAMPO_CO_SEDE      		= "CO_SEDE";
	public static final String CAMPO_CO_ZONA      		= "CO_ZONA";
	
	public static final String CAMPO_NO_TITU 	  		= "NO_TITU";
	public static final String CAMPO_FG_TITU_EXPE       = "FG_TITU_EXPE";
	public static final String CAMPO_NS_CTTE      		= "NS_CTTE";
	public static final String CAMPO_CO_RGTR            = "CO_RGTR";
	public static final String CAMPO_NO_PTDA      	    = "NO_PTDA";
	public static final String CAMPO_CO_TIPO_PART       = "CO_TIPO_PART";
	public static final String CAMPO_CO_TIPO_PROP       = "CO_TIPO_PROP";
	public static final String CAMPO_AP_PATE            = "AP_PATE";
	public static final String CAMPO_AP_MATE       		= "AP_MATE";
	public static final String CAMPO_NOMB 				= "NOMB";
	public static final String CAMPO_RZ_SOCL      		= "RZ_SOCL";
	public static final String CAMPO_CO_TIPO_DOCU       = "CO_TIPO_DOCU";
	public static final String CAMPO_NO_DOCU            = "NO_DOCU";
	public static final String CAMPO_ADDR               = "ADDR";
	public static final String CAMPO_CO_UBIC_GEOG       = "CO_UBIC_GEOG";
	public static final String CAMPO_FG_RTTE 			= "FG_RTTE";
	public static final String CAMPO_NS_ACTO  			= "NS_ACTO";
	public static final String CAMPO_CO_ESTA_CIVL       = "CO_ESTA_CIVL";
	public static final String CAMPO_CO_TIPO_PART_GRAL  = "CO_TIPO_PART_GRAL";
	public static final String CAMPO_CO_TIPO_DOC2       = "CO_TIPO_DOC2";
	public static final String CAMPO_NO_DOC2            = "NO_DOC2";
	public static final String CAMPO_NS_GRAV            = "NS_GRAV";
	public static final String CAMPO_NS_PART      	    = "NS_PART";
	public static final String CAMPO_UB_GEOG      	    = "UB_GEOG";
	public static final String CAMPO_DE_DEPARTAMENTO_PRES      	    = "DE_DEPARTAMENTO_PRES";
	public static final String CAMPO_DE_PROVINCIA_PRES      	    = "DE_PROVINCIA_PRES";
	public static final String CAMPO_DE_DISTRITO_PRES       	    = "DE_DISTRITO_PRES";
	
	/**
	 * Constructor for DboTTCtte
	 */
	public DboTTCtte() throws DBException {
		super();
	}
	
	public DboTTCtte(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection) */


	public DboTTCtte(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTATituActo(DBConnection, String) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_CTTE");

		setDescription("Object Description Goes Here");
		
		addField("AN_TITU","CHAR", 4, false, "AN_TITU");
		addField("NO_TITU","NUMBER", 8, false, "NO_TITU");
		addField("FG_TITU_EXPE","CHAR", 1, false, "FG_TITU_EXPE");
		addField("NS_CTTE","NUMBER", 3, false, "NS_CTTE");
		addField("CO_RGTR","CHAR", 2, true, "CO_RGTR");
		addField("NO_PTDA","CHAR", 8, true, "NO_PTDA");
		addField("CO_TIPO_PART","CHAR", 2, true, "CO_TIPO_PART");
		addField("CO_TIPO_PROP","CHAR", 1, true, "CO_TIPO_PROP");
		addField("AP_PATE","VARCHAR", 30, true, "AP_PATE");
		addField("AP_MATE","VARCHAR", 30, true, "AP_MATE");
		addField("NOMB","VARCHAR", 40, true, "NOMB");
		addField("RZ_SOCL","VARCHAR", 80, true, "RZ_SOCL");
		addField("CO_TIPO_DOCU","CHAR", 2, true, "CO_TIPO_DOCU");
		addField("NO_DOCU","CHAR", 12, true, "NO_DOCU");
		addField("ADDR","VARCHAR", 120, true, "ADDR");
		addField("CO_UBIC_GEOG","CHAR", 6, true, "CO_UBIC_GEOG");
		addField("FG_RTTE","CHAR", 1, true, "FG_RTTE");
		addField("NS_ACTO","NUMBER", 3, true, "NS_ACTO");
		addField("CO_ESTA_CIVL","CHAR", 1, true, "CO_ESTA_CIVL");
		addField("CO_TIPO_PART_GRAL","CHAR", 3, true, "CO_TIPO_PART_GRAL");
		addField("CO_TIPO_DOC2","CHAR", 2, true, "CO_TIPO_DOC2");
		addField("NO_DOC2","CHAR", 12, true, "NO_DOC2");
		addField("NS_GRAV","NUMBER", 3, true, "NS_GRAV");
		addField("NS_PART","NUMBER", 3, true, "NS_PART");
		addField("NO_HOJA","CHAR", 8, true, "NO_HOJA");
		addField("CO_SEDE","CHAR", 2, true, "CO_SEDE");
		addField("CO_ZONA","CHAR", 2, true, "CO_ZONA");
		//faltan las PK
		
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTTCtte();
	} /* getThisDBObj() */
	


}

