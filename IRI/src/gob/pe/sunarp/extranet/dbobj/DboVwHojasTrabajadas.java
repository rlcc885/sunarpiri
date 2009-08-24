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
public class DboVwHojasTrabajadas extends DBObject{
	
	public static final String CAMPO_ANO_TITU = "ANO_TITU";
	public static final String CAMPO_NU_TITU = "NU_TITU";
	public static final String CAMPO_AA_HOJA_PRES = "AA_HOJA_PRES";
	public static final String CAMPO_NUM_HOJA_PRES = "NUM_HOJA_PRES";
	public static final String CAMPO_OFICINA = "OFICINA";
	public static final String CAMPO_NS_DETALLE = "NS_DETALLE";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_PLAZO = "PLAZO";
	public static final String CAMPO_TS_PRESENTACION = "TS_PRESENTACION";
	public static final String CAMPO_SERVICIO = "SERVICIO";
	

	public DboVwHojasTrabajadas() throws DBException {
		super();
	} /* DboTTPago() */

	public DboVwHojasTrabajadas(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTPago(DBConnection) */

	public DboVwHojasTrabajadas(DBConnection theConnection, String theUser)
			throws DBException {
		super(theConnection);
	} /* DboTTPago(DBConnection, String) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("v_hojas_trabajadas");
		setDescription("Object Description Goes Here");
		addField("ANO_TITU", "VARCHAR", 4, true, "CAMPO_ANO_TITU");
		addField("NU_TITU", "VARCHAR", 8, false,"CAMPO_NU_TITU");
		addField("AA_HOJA_PRES", "CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NUM_HOJA_PRES", "CHAR", 10, false, "CAMPO_NUM_HOJA_PRES");
		addField("OFICINA", "VARCHAR", 4000, true, "CAMPO_OFICINA");
		addField("NS_DETALLE", "NUMBER", 5, true, "CAMPO_NS_DETALLE");
		addField("ESTADO", "VARCHAR", 50, false, "CAMPO_ESTADO");
		addField("PLAZO", "NUMBER", 5, true, "CAMPO_PLAZO");
		addField("TS_PRESENTACION", "NUMBER", 22, true, "CAMPO_TS_PRESENTACION");
		addField("SERVICIO", "VARCHAR", 4000, true, "CAMPO_SERVICIO");
		
	} /* setupFields() */

	public DBObject getThisDBObj() throws DBException {
		return new DboVwHojasTrabajadas();
	} /* getThisDBObj() */
	
}
