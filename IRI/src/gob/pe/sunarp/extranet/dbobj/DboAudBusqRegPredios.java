/*
* DboAudBusqRegPredios.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboAudBusqRegPredios extends DBObject {

	public static final String CAMPO_AUD_BUSQ_REG_PREDIOS_ID = "AUD_BUSQ_REG_PREDIOS_ID";
	public static final String CAMPO_PROV_ID = "PROV_ID";
	public static final String CAMPO_NOMBRE_VIA = "NOMBRE_VIA";
	public static final String CAMPO_AUD_BUSQ_PARTIDA_ID = "AUD_BUSQ_PARTIDA_ID";
	public static final String CAMPO_NOMBRE_ZONA = "NOMBRE_ZONA";
	public static final String CAMPO_TIPO_INTER = "TIPO_INTER";
	public static final String CAMPO_TIPO_NUMER = "TIPO_NUMER";
	public static final String CAMPO_TIPO_VIA = "TIPO_VIA";
	public static final String CAMPO_TIPO_ZONA = "TIPO_ZONA";
	public static final String CAMPO_NUM_INMB = "NUM_INMB";
	public static final String CAMPO_NUM_INTERIOR = "NUM_INTERIOR";
	public static final String CAMPO_PAIS_ID = "PAIS_ID";
	public static final String CAMPO_DPTO_ID = "DPTO_ID";
	public static final String CAMPO_DIST_ID = "DIST_ID";

	public DboAudBusqRegPredios() throws DBException {
		super();
	} /* DboAudBusqRegPredios() */


	public DboAudBusqRegPredios(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboAudBusqRegPredios(DBConnection) */


	public DboAudBusqRegPredios(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* AUD_BUSQ_REG_PREDIOS(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("AUD_BUSQ_REG_PREDIOS");

		setDescription("Object Description Goes Here");

		addField("AUD_BUSQ_REG_PREDIOS_ID","auto-inc", 0, false, "CAMPO_AUD_BUSQ_REG_PREDIOS_ID");
		addField("PROV_ID","CHAR", 2, false, "CAMPO_PROV_ID");
		addField("NOMBRE_VIA","VARCHAR", 100, false, "CAMPO_NOMBRE_VIA");
		addField("AUD_BUSQ_PARTIDA_ID","NUMBER", 22, false, "CAMPO_AUD_BUSQ_PARTIDA_ID");
		addField("NOMBRE_ZONA","VARCHAR", 100, false, "CAMPO_NOMBRE_ZONA");
		addField("TIPO_INTER","CHAR", 2, true, "CAMPO_TIPO_INTER");
		addField("TIPO_NUMER","CHAR", 2, true, "CAMPO_TIPO_NUMER");
		addField("TIPO_VIA","CHAR", 2, false, "CAMPO_TIPO_VIA");
		addField("TIPO_ZONA","CHAR", 2, false, "CAMPO_TIPO_ZONA");
		addField("NUM_INMB","VARCHAR", 10, true, "CAMPO_NUM_INMB");
		addField("NUM_INTERIOR","VARCHAR", 10, true, "CAMPO_NUM_INTERIOR");
		addField("PAIS_ID","CHAR", 2, false, "CAMPO_PAIS_ID");
		addField("DPTO_ID","CHAR", 2, false, "CAMPO_DPTO_ID");
		addField("DIST_ID","CHAR", 2, false, "CAMPO_DIST_ID");

		addKey("AUD_BUSQ_REG_PREDIOS_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboAudBusqRegPredios();
	} /* getThisDBObj() */
} /* DboAudBusqRegPredios */