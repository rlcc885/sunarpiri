/*
* DboTransaccion.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboTransaccion extends DBObject {

	public static final String CAMPO_TRANS_ID = "TRANS_ID";
	public static final String CAMPO_SERVICIO_ID = "SERVICIO_ID";
	public static final String CAMPO_CUENTA_ID = "CUENTA_ID";
	public static final String CAMPO_FEC_HOR = "FEC_HOR";
	public static final String CAMPO_COSTO = "COSTO";
	public static final String CAMPO_IP = "IP";
	public static final String CAMPO_TIPO_USR = "TIPO_USR";
	public static final String CAMPO_STR_BUSQ = "STR_BUSQ";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	//Tarifario
	public static final String CAMPO_COD_GRUPO_LIBRO_AREA = "COD_GRUPO_LIBRO_AREA";

        //Modificado por: Proyecto Filtros de Acceso
        //Fecha: 03/10/2006
        public static final String CAMPO_SESION_ID = "SESION_ID";
        //Fin Modificación        
	
	public DboTransaccion() throws DBException {
		super();
	} /* DboTransaccion() */


	public DboTransaccion(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTransaccion(DBConnection) */


	public DboTransaccion(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* TRANSACCION(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("TRANSACCION");

		setDescription("Object Description Goes Here");

		/**
		 * SVASQUEZ - AVATAR GLOBAL
		 * SE COMENTA EL AUTO-INC DE LA TABLA TRANSACCION
		 */
		//addField("TRANS_ID","auto-inc", 0, false, "null");
		addField("TRANS_ID","NUMBER", 22, false, "null");
		
		addField("SERVICIO_ID","NUMBER", 22, false, "null");
		addField("CUENTA_ID","NUMBER", 22, false, "null");
		addField("FEC_HOR","NUMBER", 22, false, "null");
		addField("COSTO","NUMBER", 12, false, "null");
		addField("IP","VARCHAR", 15, false, "null");
		addField("TIPO_USR","CHAR", 1, false, "null");
		addField("STR_BUSQ","VARCHAR", 100, true, "null");
		addField("REG_PUB_ID","CHAR", 2, true, "null");
		addField("OFIC_REG_ID","CHAR", 2, true, "null");
		//Tarifario
		addField("COD_GRUPO_LIBRO_AREA","NUMBER", 22, false, "null");

                //Modificado por: Proyecto Filtros de Acceso
                addField("SESION_ID","VARCHAR", 200, true, "null");
                //Fin Modificación
          
		addKey("TRANS_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboTransaccion();
	} /* getThisDBObj() */
} /* DboTransaccion */

