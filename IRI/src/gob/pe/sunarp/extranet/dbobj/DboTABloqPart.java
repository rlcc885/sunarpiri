
package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTABloqPart extends DBObject {

public static final String CAMPO_AA_HOJA_PRES        = "AA_HOJA_PRES";
public static final String CAMPO_CO_REGI     	     = "CO_REGI";
public static final String CAMPO_CO_OFIC_RGST        = "CO_OFIC_RGST";
public static final String CAMPO_AA_TITU     	     = "AA_TITU";
public static final String CAMPO_NU_TITU     	     = "NU_TITU";
public static final String CAMPO_CO_LIBR    	     = "CO_LIBR";
public static final String CAMPO_NU_PART     	     = "NU_PART";
public static final String CAMPO_NU_ORIG_PART     	 = "NU_ORIG_PART";
public static final String CAMPO_CO_ACTO_RGST     	 = "CO_ACTO_RGST";
public static final String CAMPO_NU_TOMO         	 = "NU_TOMO";
public static final String CAMPO_NU_FOJA     	     = "NU_FOJA";
public static final String CAMPO_CO_SECC         	 = "CO_SECC";
public static final String CAMPO_IN_BLOQ_ACTI     	 = "IN_BLOQ_ACTI";
public static final String CAMPO_IN_BLOQ_PASI     	 = "IN_BLOQ_PASI";
public static final String CAMPO_TS_USUA_CREA     	 = "TS_USUA_CREA";
public static final String CAMPO_ID_USUA_CREA     	 = "ID_USUA_CREA";
public static final String CAMPO_TS_USUA_MODI     	 = "TS_USUA_MODI";
public static final String CAMPO_ID_USUA_MODI     	 = "ID_USUA_MODI";
public static final String CAMPO_TS_USUA_ELIM     	 = "TS_USUA_ELIM";
public static final String CAMPO_ID_USUA_ELIM     	 = "ID_USUA_ELIM";
public static final String CAMPO_FLAG            	 = "FLAG";
public static final String CAMPO_ID_AUX     	     = "ID_AUX";
public static final String CAMPO_NU_FOJA_ID     	 = "NU_FOJA_ID";
public static final String CAMPO_TS_USUA_CHAR     	 = "TS_USUA_CHAR";
//MODIFICADO JBUGARIN 25/09/06
public static final String CAMPO_CO_TI_SIST     	 = "CO_TI_SIST";
public static final String CAMPO_DE_TI_SIST     	 = "DE_TI_SIST";
public static final String CAMPO_CO_REGI_PRES     	 = "CO_REGI_PRES";
public static final String CAMPO_CO_OFIC_RGST_PRES   = "CO_OFIC_RGST_PRES";
public static final String CAMPO_NU_HOJA_PRES        = "NU_HOJA_PRES";

//
	/**
	 * Constructor for DboTaBloqPart
	 */
	public DboTABloqPart() throws DBException {
		super();
	} /* DboTaBloqPart() */
	
	public DboTABloqPart(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTaBloqPart(DBConnection) */


	public DboTABloqPart(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* DboTaBloqPart(DBConnection, String) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("TA_BLOQ_PART");

		setDescription("Object Description Goes Here");
		addField("CO_ACTO_RGST","CHAR", 5, false, "CO_ACTO_RGST");
		addField("CO_REGI","CHAR", 2, true, "CAMPO_CO_REGI");
		addField("CO_OFIC_RGST","CHAR", 2, true, "CO_OFIC_RGST");
		addField("AA_TITU","CHAR", 4, true, "AA_TITU");
		addField("NU_TITU","CHAR", 8, true, "NU_TITU");
		addField("CO_LIBR","CHAR", 3, true, "CO_LIBR");
		addField("NU_PART","CHAR", 8, true, "NU_PART");
		addField("NU_ORIG_PART","CHAR", 10, true, "NU_ORIG_PART");
		addField("CO_ACTO_RGST","CHAR", 5, true, "CO_ACTO_RGST");
		addField("NU_TOMO","CHAR", 6, true, "NU_TOMO");
		addField("NU_FOJA","CHAR", 6, true, "NU_FOJA");
		addField("CO_SECC","CHAR", 5, true, "CO_SECC");
		addField("IN_BLOQ_ACTI","CHAR", 1, true, "IN_BLOQ_ACTI");
		addField("IN_BLOQ_PASI","CHAR", 1, true, "IN_BLOQ_PASI");
		addField("TS_USUA_CREA","NUMBER", 22, true, "TS_USUA_CREA");
		addField("ID_USUA_CREA","CHAR", 5, true, "ID_USUA_CREA");
		addField("TS_USUA_MODI","NUMBER", 22, true, "TS_USUA_MODI");
		addField("ID_USUA_MODI","CHAR", 5, true, "ID_USUA_MODI");
		addField("TS_USUA_ELIM","NUMBER", 22, true, "TS_USUA_ELIM");
		addField("ID_USUA_ELIM","CHAR", 5, true, "ID_USUA_ELIM");
		addField("FLAG","CHAR", 1, true, "FLAG");
		addField("ID_AUX","CHAR", 3, true, "ID_AUX");
		addField("NU_FOJA_ID","CHAR", 3, true, "NU_FOJA_ID");
		addField("TS_USUA_CHAR","CHAR", 8, true, "TS_USUA_CHAR");
		//jbugarin 25/09/06
		addField("CO_TI_SIST","CHAR", 1, true, "CO_TI_SIST");
		addField("DE_TI_SIST","VARCHAR", 50, true, "DE_TI_SIST");
		
		addField("CO_REGI_PRES","CHAR", 2, false, "CO_REGI_PRES");
		addField("CO_OFIC_RGST_PRES","CHAR", 2, false, "CO_OFIC_RGST_PRES");
		
		addField("NU_HOJA_PRES","CHAR", 8, false, "NU_HOJA_PRES");
		addField("AA_HOJA_PRES","CHAR", 4, false, "AA_HOJA_PRES");
		//Faltan agregar las primary keys

	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */

}

