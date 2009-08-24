/*
* DboVwBusqRazSoc.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVwBusqRazSoc extends DBObject {

	public static final String CAMPO_TIPO = "TIPO";
	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_NOMBRE = "NOMBRE";
	public static final String CAMPO_SIGLAS = "SIGLAS";
	public static final String CAMPO_USR_CREA = "USR_CREA";
	public static final String CAMPO_USR_ULT_MODIF = "USR_ULT_MODIF";
	public static final String CAMPO_OFRREGPUB = "OFRREGPUB";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_OFRNOMBRE = "OFRNOMBRE";
	public static final String CAMPO_OFRUSRCREA = "OFRUSRCREA";
	public static final String CAMPO_USRULTMOD = "USRULTMOD";
	public static final String CAMPO_REFNUPART = "REFNUPART";
	public static final String CAMPO_NS_NOMBRE = "NS_NOMBRE";
	public static final String CAMPO_RAZON_SOC = "RAZON_SOC";
	public static final String CAMPO_ESTADO = "ESTADO";
	public static final String CAMPO_RZSIGL = "RZSIGL";
	public static final String CAMPO_NU_FOJA = "NU_FOJA";
	public static final String CAMPO_NU_TOMO = "NU_TOMO";
	public static final String CAMPO_NU_ORIG_PART = "NU_ORIG_PART";
	public static final String CAMPO_AGNT_SYNC = "AGNT_SYNC";
	public static final String CAMPO_NUM_PARTIDA = "NUM_PARTIDA";
	public static final String CAMPO_PARTREGPUB = "PARTREGPUB";
	public static final String CAMPO_PARTOFRID = "PARTOFRID";
	public static final String CAMPO_COD_LIBRO = "COD_LIBRO";
	public static final String CAMPO_AREA_REG_ID = "AREA_REG_ID";
	public static final String CAMPO_PARTESTD = "PARTESTD";
	public static final String CAMPO_PARTAGNSYN = "PARTAGNSYN";
	public static final String CAMPO_CO_LIBR_ORIG = "CO_LIBR_ORIG";

	public DboVwBusqRazSoc() throws DBException {
		super();
	} /* DboVwBusqRazSoc() */


	public DboVwBusqRazSoc(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboVwBusqRazSoc(DBConnection) */


	public DboVwBusqRazSoc(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* VW_BUSQ_RAZ_SOC(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("VW_BUSQ_RAZ_SOC");

		setDescription("Object Description Goes Here");

		addField("TIPO","CHAR", 4, true, "null");
		addField("REFNUM_PART","NUMBER", 22, true, "null");
		addField("REG_PUB_ID","CHAR", 2, true, "null");
		addField("NOMBRE","VARCHAR", 50, true, "null");
		addField("SIGLAS","CHAR", 5, true, "null");
		addField("USR_CREA","VARCHAR", 15, true, "null");
		addField("USR_ULT_MODIF","VARCHAR", 15, true, "null");
		addField("OFRREGPUB","VARCHAR", 8, true, "null");
		addField("OFIC_REG_ID","VARCHAR", 2, true, "null");
		addField("OFRNOMBRE","VARCHAR", 30, true, "null");
		addField("OFRUSRCREA","VARCHAR", 15, true, "null");
		addField("USRULTMOD","VARCHAR", 15, true, "null");
		addField("REFNUPART","NUMBER", 22, true, "null");
		addField("NS_NOMBRE","NUMBER", 22, true, "null");
		addField("RAZON_SOC","VARCHAR", 250, true, "null");
		addField("ESTADO","CHAR", 1, true, "null");
		addField("RZSIGL","VARCHAR", 60, true, "null");
		addField("NU_FOJA","VARCHAR", 6, true, "null");
		addField("NU_TOMO","VARCHAR", 6, true, "null");
		addField("NU_ORIG_PART","VARCHAR", 10, true, "null");
		addField("AGNT_SYNC","VARCHAR", 4, true, "null");
		addField("NUM_PARTIDA","VARCHAR", 8, true, "null");
		addField("PARTREGPUB","CHAR", 2, true, "null");
		addField("PARTOFRID","VARCHAR", 2, true, "null");
		addField("COD_LIBRO","VARCHAR", 3, true, "null");
		addField("AREA_REG_ID","CHAR", 5, true, "null");
		addField("PARTESTD","VARCHAR", 1, true, "null");
		addField("PARTAGNSYN","VARCHAR", 4, true, "null");
		addField("CO_LIBR_ORIG","VARCHAR", 3, true, "null");

	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboVwBusqRazSoc();
	} /* getThisDBObj() */
} /* DboVwBusqRazSoc */

