/*
* DboCertificado.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;

import java.sql.Clob;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboCertificado extends DBObject {

	public static final String CAMPO_CERTIFICADO_ID = "CERTIFICADO_ID";
	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_OBJETO_SOL_ID = "OBJETO_SOL_ID";
	public static final String CAMPO_TPO_CERTIFICADO = "TPO_CERTIFICADO";
	public static final String CAMPO_COMENTARIO = "COMENTARIO";
	public static final String CAMPO_CONSTANCIA = "CONSTANCIA";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID_VERIF = "OFIC_REG_ID_VERIF";
	public static final String CAMPO_OFIC_REG_ID_EXP = "OFIC_REG_ID_EXP";
	public static final String CAMPO_TS_VERIFICACION = "TS_VERIFICACION";
	public static final String CAMPO_TS_EXPEDICION = "TS_EXPEDICION";
	public static final String CAMPO_TS_CREA = "TS_CREA";
	public static final String CAMPO_TS_MODI = "TS_MODI";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_MODI = "USR_MODI";
	
	//Inicio:jascencio:29/05/2007
	//SUNARP-REGMOBCOM: se agregaran algunas constantes
    public static final String CAMPO_TITULO = "TITULO";
	//Fin:jascencio:29/05/2007

    //inicio:dbravo:15/08/2007
    public static final String CAMPO_FLAG_PAGO_CREM = "FLAG_PAGO_CREM";
    public static final String CAMPO_PAGINAS_CREM   = "PAGINAS_CREM";
    public static final String CAMPO_PAGO_CREM      = "PAGO_CREM";
    //fin:dbravo:15/08/2007
    
	public DboCertificado() throws DBException {
		super();
	} /* DboCertificado() */


	public DboCertificado(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboCertificado(DBConnection) */


	public DboCertificado(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* CERTIFICADO(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("CERTIFICADO");

		setDescription("Object Description Goes Here");

		addField("CERTIFICADO_ID","auto-inc", 0, false, "CAMPO_CERTIFICADO_ID");
		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		addField("OBJETO_SOL_ID","NUMBER", 22, false, "CAMPO_OBJETO_SOL_ID");
		addField("TPO_CERTIFICADO","CHAR", 1, false, "CAMPO_TPO_CERTIFICADO");
		addField("COMENTARIO","VARCHAR", 1024, true, "CAMPO_COMENTARIO");
		addField("CONSTANCIA","VARCHAR", 2048, true, "CAMPO_CONSTANCIA");
		addField("REG_PUB_ID","CHAR", 2, true, "CAMPO_REG_PUB_ID");
		addField("OFIC_REG_ID_VERIF","CHAR", 2, true, "CAMPO_OFIC_REG_ID_VERIF");
		addField("OFIC_REG_ID_EXP","CHAR", 2, true, "CAMPO_OFIC_REG_ID_EXP");
		addField("TS_VERIFICACION","NUMBER", 22, true, "CAMPO_TS_VERIFICACION");
		addField("TS_EXPEDICION","NUMBER", 22, true, "CAMPO_TS_EXPEDICION");
		addField("TS_CREA","NUMBER", 22, false, "CAMPO_TS_CREA");
		addField("TS_MODI","NUMBER", 22, true, "CAMPO_TS_MODI");
		addField("USR_CREA","VARCHAR", 15, false, "CAMPO_USR_CREA");
		addField("USR_MODI","VARCHAR", 15, true, "CAMPO_USR_MODI");
		
		//Inicio:jascencio:29/05/02007
		//SUNARP-REGMOBCOM: se agregaran fields
		// TODO: VALIDAR LA LONGITUD DEL CAMPO CONSTANCIA2 
		addField("TITULO","VARCHAR", 1024, true, "CAMPO_TITULO");
		//Fin:jascencio:29/05/02007
		
		//inicio:dbravo:15/08/2007
		addField("FLAG_PAGO_CREM","CHAR", 1, true, "CAMPO_FLAG_PAGO_CREM");
		addField("PAGINAS_CREM","NUMBER", 15, true, "CAMPO_PAGINAS_CREM");
		addField("PAGO_CREM","NUMBER"   , 12, true, "CAMPO_PAGO_CREM");
		//fin:dbravo:15/08/2007
		
		addKey("CERTIFICADO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboCertificado();
	} /* getThisDBObj() */
} /* DboCertificado */

