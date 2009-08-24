package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTTProp extends DBObject{
	
	public static final String CAMPO_NO_HOJA        = "NO_HOJA";
	public static final String CAMPO_AN_TITU        = "AN_TITU";
	public static final String CAMPO_CO_ZONA        = "CO_ZONA";
	public static final String CAMPO_CO_SEDE        = "CO_SEDE";
	public static final String CAMPO_NS_PROP        = "NS_PROP";
	public static final String CAMPO_NS_ACTO        = "NS_ACTO";
	public static final String CAMPO_NO_PLAC        = "NO_PLAC";
	public static final String CAMPO_NO_TITU        = "NO_TITU" ;
	public static final String CAMPO_TI_PROP        = "TI_PROP";
	public static final String CAMPO_IMPD           = "IMPD";
	public static final String CAMPO_UB_GEOG        = "UB_GEOG";
	public static final String CAMPO_TI_DOCU        = "TI_DOCU";
	public static final String CAMPO_NO_TRAN        = "NO_TRAN";
	public static final String CAMPO_NO_DOCU        = "NO_DOCU";
	public static final String CAMPO_AP_PATE        = "AP_PATE";
	public static final String CAMPO_AP_MATE        = "AP_MATE";
	public static final String CAMPO_NOMB           = "NOMB ";
	public static final String CAMPO_RZ_SOCL        = "RZ_SOCL";
	public static final String CAMPO_ADDR           = "ADDR";
	public static final String CAMPO_IN_PROP        = "IN_PROP ";
	public static final String CAMPO_CO_ESTA_CIVL   = "CO_ESTA_CIVL";
	public static final String CAMPO_NO_EXPE_ANTE   = "NO_EXPE_ANTE";
	public static final String CAMPO_FE_EXPE_ANTE   = "FE_EXPE_ANTE";
	public static final String CAMPO_NO_TARJ        = "NO_TARJ";
	public static final String CAMPO_FG_ACTI        = "FG_ACTI";
	public static final String CAMPO_DE_TI_DOCU              = "DE_TI_DOCU";
	public static final String CAMPO_DE_ESTA_CIVIL           = "DE_ESTA_CIVIL";
	public static final String CAMPO_PAIS_ID                 = "PAIS_ID";
	public static final String CAMPO_DE_DEPARTAMENTO_PRES  = "DE_DEPARTAMENTO_PRES";
	public static final String CAMPO_DE_PROVINCIA_PRES     = "DE_PROVINCIA_PRES";
	public static final String CAMPO_DE_DISTRITO_PRES      = "DE_DISTRITO_PRES";
	public static final String CAMPO_TI_VIA        			 = "TI_VIA";
	public static final String CAMPO_DE_VIA                  = "DE_VIA";
	public static final String CAMPO_CO_POSTAL               = "CO_POSTAL";
	public static final String CAMPO_DE_NCL                  = "DE_NCL";
	public static final String CAMPO_MAIL                    = "MAIL";
	public static final String CAMPO_NU_PART                 = "NU_PART";
	public static final String CAMPO_CO_OFIC_RGST            = "CO_OFIC_RGST";
	public static final String CAMPO_CO_REGI                 = "CO_REGI";
	
	/**
	 * Constructor for DboTTProp
	 */
	public DboTTProp() throws DBException{
		super();
	}

	public DboTTProp(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("TT_PROP");

		setDescription("Object Description Goes Here");
	
		
		addField("NO_HOJA","CHAR", 8, false, "NO_HOJA");
		addField("AN_TITU","CHAR", 4, false, "AN_TITU");
		addField("CO_ZONA","CHAR", 2, false, "CO_ZONA");
		addField("CO_SEDE","CHAR", 2, false, "CO_SEDE");
		addField("NS_PROP","NUMBER", 4, false, "NS_PROP");
		addField("NS_ACTO","NUMBER", 3, false ,"NS_ACTO");
		addField("NO_PLAC","CHAR", 7, false, "NO_PLAC");
		addField("NO_TITU","NUMBER", 8, false, "NO_TITU");
		addField("TI_PROP","CHAR", 1, true, "TI_PROP");
		addField("IMPD","CHAR", 4, true, "IMPD");
		addField("UB_GEOG","CHAR", 6, true, "UB_GEOG");
		addField("TI_DOCU","CHAR", 2, true, "TI_DOCU");
		addField("NO_TRAN","NUMBER", 4, true, "NO_TRAN");
		addField("NO_DOCU","CHAR", 12, true, "NO_DOCU");
		addField("AP_PATE","VARCHAR", 25, true, "AP_PATE");
		addField("AP_MATE","VARCHAR", 25, true, "AP_MATE");
		addField("NOMB","VARCHAR", 25, true, "NOMB");
		addField("RZ_SOCL","VARCHAR", 70, true, "RZ_SOCL");
		addField("ADDR","VARCHAR", 120, true, "ADDR");
		addField("IN_PROP","CHAR", 1, true, "IN_PROP");
		addField("CO_ESTA_CIVL","CHAR", 1, true, "CO_ESTA_CIVL");
		addField("NO_EXPE_ANTE","VARCHAR", 2,true, "NO_EXPE_ANTE");
		addField("FE_EXPE_ANTE","NUMBER", 22, true, "FE_EXPE_ANTE");
		addField("NO_TARJ","NUMBER",10, true, "NO_TARJ");
		addField("FG_ACTI","CHAR", 1, true, "FG_ACTI");
		addField("DE_TI_DOCU","VARCHAR", 2, true, "DE_TI_DOCU");
		addField("DE_ESTA_CIVIL","VARCHAR", 45, true, "DE_ESTA_CIVIL");
		addField("PAIS_ID","CHAR", 2, true, "PAIS_ID");
		addField("DE_DEPARTAMENTO","VARCHAR", 30, true, "DE_DEPARTAMENTO");
		addField("DE_PROVINCIA","VARCHAR", 40, true, "DE_PROVINCIA");
		addField("DE_DISTRITO","VARCHAR", 40, true, "DE_DISTRITO");
		addField("TI_VIA","VARCHAR", 40, true, "TI_VIA");
		addField("DE_VIA","VARCHAR", 40, true, "DE_VIA");
		addField("CO_POSTAL","VARCHAR", 10, true, "CO_POSTAL");
		addField("DE_NCL","VARCHAR", 50, true, "DE_NCL");
		addField("MAIL","VARCHAR", 100, true, "MAIL");
		addField("NU_PART","CHAR", 8, true, "NU_PART");
		addField("CO_REGI","CHAR", 2, true, "CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CO_OFIC_RGST");
		
		//KEYS
		addKey("NO_HOJA");
		addKey("AN_TITU");
		addKey("CO_ZONA");
		addKey("CO_SEDE");
		addKey("NS_PROP");
		
		
		
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTTProp();
	} /* getThisDBObj() */
}

