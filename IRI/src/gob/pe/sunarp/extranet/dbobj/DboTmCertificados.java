/*
* DboTmCertificados.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTmCertificados extends DBObject {

	public static final String CAMPO_CERTIFICADO_ID = "CERTIFICADO_ID";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_TPO_CERTIFICADO = "TPO_CERTIFICADO";
	public static final String CAMPO_SERVICIO_ID = "SERVICIO_ID";
	public static final String CAMPO_COD_GRUPO_LIBRO_AREA = "COD_GRUPO_LIBRO_AREA";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_TPO_PERSONA = "TPO_PERSONA";
	public static final String CAMPO_ESTADO = "ESTADO";
	
	public DboTmCertificados() throws DBException {
		super();
	} /* DboTmCertificados() */


	public DboTmCertificados(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTmCertificados(DBConnection) */


	public DboTmCertificados(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TM_CERTIFICADOS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TM_CERTIFICADOS");

		setDescription("Object Description Goes Here");

		addField("CERTIFICADO_ID","auto-inc", 0, false, "CAMPO_CERTIFICADO_ID");
		addField("AREA_REG_ID","NUMBER", 22, false, "CAMPO_AREA_REG_ID");
		addField("TPO_CERTIFICADO","CHAR", 1, false, "CAMPO_TPO_CERTIFICADO");
		addField("SERVICIO_ID","NUMBER", 22, false, "CAMPO_SERVICIO_ID");
		addField("COD_GRUPO_LIBRO_AREA","NUMBER", 1, false, "COD_GRUPO_LIBRO_AREA");
		addField("NOMBRE","VARCHAR", 100, false, "CAMPO_NOMBRE");
		addField("TPO_PERSONA","CHAR", 1, false, "CAMPO_TPO_PERSONA");
		addField("ESTADO","CHAR", 1, false, "CAMPO_ESTADO");
		
		addKey("CERTIFICADO_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTmCertificados();
	} /* getThisDBObj() */
} /* DboTmCertificados */

