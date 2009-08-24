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
public class DboVwHojasPresentadas extends DBObject {

	public static final String CAMPO_PRESENTANTE = "PRESENTANTE";
	public static final String CAMPO_ID_USUARIO = "ID_USUARIO";
	public static final String CAMPO_AA_HOJA_PRES = "AA_HOJA_PRES";
	public static final String CAMPO_NU_HOJA_PRES = "NU_HOJA_PRES";
	public static final String CAMPO_MAIL_PRES = "MAIL_PRES";
	public static final String CAMPO_TS_PRESENTACION = "TS_PRESENTACION";
	public static final String CAMPO_SERVICIO = "SERVICIO";
	public static final String CAMPO_OFICINA = "OFICINA";

	public DboVwHojasPresentadas() throws DBException {
		super();
	} /* DboTTPago() */

	public DboVwHojasPresentadas(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTTPago(DBConnection) */

	public DboVwHojasPresentadas(DBConnection theConnection, String theUser)
			throws DBException {
		super(theConnection);
	} /* DboTTPago(DBConnection, String) */

	protected synchronized void setupFields() throws DBException {
		setTargetTable("v_hojas_presentadas");
		setDescription("Object Description Goes Here");
		addField("PRESENTANTE", "VARCHAR", 152, true, "CAMPO_PRESENTANTE");
		addField("ID_USUARIO", "VARCHAR", 15, false,"CAMPO_ID_USUARIO");
		addField("AA_HOJA_PRES", "CHAR", 4, false, "CAMPO_AA_HOJA_PRES");
		addField("NU_HOJA_PRES", "CHAR", 8, false, "CAMPO_NU_HOJA_PRES");
		addField("MAIL_PRES", "VARCHAR", 100, true, "CAMPO_MAIL_PRES");
		addField("TS_PRESENTACION", "NUMBER", 22, true, "CAMPO_TS_PRESENTACION");
		addField("SERVICIO", "VARCHAR", 50, false, "CAMPO_SERVICIO");
		addField("OFICINA", "VARCHAR", 4000, true, "CAMPO_OFICINA");
		
	} /* setupFields() */

	public DBObject getThisDBObj() throws DBException {
		return new DboVwHojasPresentadas();
	} /* getThisDBObj() */
}
