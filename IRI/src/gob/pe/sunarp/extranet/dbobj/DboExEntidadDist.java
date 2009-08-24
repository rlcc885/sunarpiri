/**
 * 
 */
package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.DBObject;

/**
 * @author jbugarin
 *
 */
public class DboExEntidadDist extends DBObject {
	public static final String CAMPO_ENT_REFNUM = "ENT_REFNUM";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_AA_HOJA_PRES = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES = "NU_HOJA_PRES";
	public static final String CAMPO_TBP_NUM_PARTIDA = "TBP_NUM_PARTIDA";
	public static final String CAMPO_TTA_CO_ACTO = "TTA_CO_ACTO";
	public static final String CAMPO_TTA_NS_AFEC = "TTA_NS_AFEC";
	public static final String CAMPO_TPNT_CO_ACTO_RGST = "TPNT_CO_ACTO_RGST";
	public static final String CAMPO_TPNT_NS_AFEC = "TPNT_NS_AFEC";
	public static final String CAMPO_TPNT_NS_PERS_NATU = "TPNT_NS_PERS_NATU";
	public static final String CAMPO_TPJT_CO_ACTO_RGST = "TPJT_CO_ACTO_RGST";
	public static final String CAMPO_TPJT_NS_AFEC = "TPJT_NS_AFEC";
	public static final String CAMPO_TPJT_NS_PERS_NATU = "TPJT_NS_PERS_NATU";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_TMSTMP_DIST = "TMSTMP_DIST";
	public static final String CAMPO_ERROR_CODIGO = "ERROR_CODIGO";

	public DboExEntidadDist() throws DBException {
		super();
	} /* DboEsquela() */


	public DboExEntidadDist(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboEsquela(DBConnection) */


	public DboExEntidadDist(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* ESQUELA(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("EX_ENTIDAD_DIST");

		setDescription("Object Description Goes Here");
		addField("ENT_REFNUM","NUMBER", 22, false, "null");
		addField("REG_PUB_ID","CHAR", 2, false, "null");
		addField("OFIC_REG_ID","CHAR", 2, false, "null");
		addField("AA_HOJA_PRES","CHAR", 4, false, "null");
		addField("NU_HOJA_PRES","CHAR", 8, false, "null");
		addField("TBP_NUM_PARTIDA","CHAR", 8, true, "null");
		addField("TTA_CO_ACTO","CHAR", 5, true, "null");
		addField("TTA_NS_AFEC","NUMBER", 5, true, "null");
		addField("TPNT_CO_ACTO_RGST","CHAR", 5, true, "null");
		addField("TPNT_NS_AFEC","CHAR", 38, true, "null");
		addField("TPNT_NS_PERS_NATU","CHAR", 5, true, "null");
		addField("TPJT_CO_ACTO_RGST","CHAR", 5, true, "null");
		addField("TPJT_NS_AFEC","NUMBER", 38, false, "null");
		addField("TPJT_NS_PERS_NATU","NUMBER", 22, true, "null");
		//addField("FIELD1","VARCHAR", 20, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("TMSTMP_DIST","NUMBER", 22, true, "null");
		addField("ERROR_CODIGO","CHAR", 6, true, "null");
		
		addKey("ENT_REFNUM");
		addKey("REG_PUB_ID");
		addKey("OFIC_REG_ID");
		addKey("AA_HOJA_PRES");
		addKey("NU_HOJA_PRES");
		
		
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboExEntidadDist();
	} /* getThisDBObj() */
} 