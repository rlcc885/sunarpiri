/*
* DboObjetoSolicitud.java - cjvc
*/

package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboObjetoSolicitud extends DBObject {

	public static final String CAMPO_SOLICITUD_ID = "SOLICITUD_ID";
	public static final String CAMPO_RAZ_SOC = "RAZ_SOC";
	public static final String CAMPO_NOMBRES = "NOMBRES";
	public static final String CAMPO_APE_MAT = "APE_MAT";
	public static final String CAMPO_APE_PAT = "APE_PAT";
	public static final String CAMPO_TPO_PERS = "TPO_PERS";
	public static final String CAMPO_SUBTOTAL = "SUBTOTAL";
	public static final String CAMPO_REFNUM_PART = "REFNUM_PART";
	public static final String CAMPO_NS_ASIENTO = "NS_ASIENTO";
	public static final String CAMPO_COD_ACTO = "COD_ACTO";
	public static final String CAMPO_NUM_TITU = "NUM_TITU";
	public static final String CAMPO_AA_TITU = "AA_TITU";
	public static final String CAMPO_CERTIFICADO_ID = "CERTIFICADO_ID";
	public static final String CAMPO_NUMPAG = "NUMPAG";
	public static final String CAMPO_REG_PUB_ID = "REG_PUB_ID";
	public static final String CAMPO_OFIC_REG_ID = "OFIC_REG_ID";
	public static final String CAMPO_OBJETO_SOL_ID = "OBJETO_SOL_ID";
	public static final String CAMPO_NUM_PLACA = "NUM_PLACA";
	public static final String CAMPO_NS_ASIE_PLACA = "NS_ASIE_PLACA";
	public static final String CAMPO_TS_VERI_MANU = "TS_VERI_MANU";
	//Inicio: jascencio:29/05/2007
	//SUNARP-REGMOBCOM: se esta agregando las siguientes constantes 
	
	public static final String CAMPO_NUMERO_PARTIDA	  = "NUM_PARTIDA";
	public static final String CAMPO_NOMBRE_BIEN      = "NOMBRE_BIEN";
	public static final String CAMPO_NUM_MATRICULA    = "NUM_MATRICULA";
	public static final String CAMPO_NUM_SERIE        = "NUM_SERIE";
	public static final String CAMPO_TIP_PARTICIPANTE = "TIP_PARTICIPANTE";
	public static final String CAMPO_SIGLAS           = "SIGLAS";
	public static final String CAMPO_TIP_DOCUMENTO    = "TIP_DOCUMENTO";
	public static final String CAMPO_NUM_DOCUMENTO    = "NUM_DOCUMENTO";
	public static final String CAMPO_TIP_INF_DOMINIO  = "TIP_INF_DOMINIO";
	public static final String CAMPO_TIP_REGISTRO     = "TIP_REGISTRO";
	public static final String CAMPO_INS_ASIENTO      = "INS_ASIENTO";
	public static final String CAMPO_FLAG_HISTORICO   = "FLAG_HISTORICO";
	public static final String CAMPO_URL_BUSQ         = "URL_BUSQ";
	// Inicio:mgarate:05/06/2007
	public static final String CAMPO_CRIT_URL         = "CRIT_BUSQ";
	// Fin:mgarate:05/06/2007
	public static final String CAMPO_FEC_INS_ASIENTO_DESDE = "FEC_INS_ASIENTO_DESDE";
	public static final String CAMPO_FEC_INS_ASIENTO_HASTA = "FEC_INS_ASIENTO_HASTA";

	//Fin:jascencio:29/05/2007
		
	//inicio:dbravo:10/08/2007
	public static final String CAMPO_FLAG_ACEPTA_CONDICION = "FLAG_ACEPTA_CONDICION";
	public static final String CAMPO_FLAG_ENVIO_DOMICILIO = "FLAG_ENVIO_DOMICILIO";
	//fin:dbravo:10/08/2007
	
	//Inicio:jascencio:10/08/2007
	//CC:REGMOBCON-2006
	public static final String CAMPO_REFNUM_PART_ANTIGUO = "REFNUM_PART_ANTIGUO";
	//Fin
	
	public DboObjetoSolicitud() throws DBException {
		super();
	} /* DboObjetoSolicitud() */


	public DboObjetoSolicitud(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboObjetoSolicitud(DBConnection) */


	public DboObjetoSolicitud(DBConnection theConnection, String theUser) throws DBException {
		super(theConnection);
	} /* OBJETO_SOLICITUD(DBConnection, String) */


	protected synchronized void setupFields() throws DBException {
		setTargetTable("OBJETO_SOLICITUD");

		setDescription("Object Description Goes Here");

		addField("SOLICITUD_ID","NUMBER", 22, false, "CAMPO_SOLICITUD_ID");
		addField("RAZ_SOC","VARCHAR", 100, true, "CAMPO_RAZ_SOC");
		addField("NOMBRES","VARCHAR", 40, true, "CAMPO_NOMBRES");
		addField("APE_MAT","VARCHAR", 30, true, "CAMPO_APE_MAT");
		addField("APE_PAT","VARCHAR", 30, true, "CAMPO_APE_PAT");
		addField("TPO_PERS","CHAR", 1, true, "CAMPO_TPO_PERS");
		addField("SUBTOTAL","NUMBER", 12, false, "CAMPO_SUBTOTAL");
		addField("REFNUM_PART","NUMBER", 22, true, "CAMPO_REFNUM_PART");
		addField("NS_ASIENTO","NUMBER", 5, true, "CAMPO_NS_ASIENTO");
		addField("COD_ACTO","CHAR", 5, true, "CAMPO_COD_ACTO");
		addField("NUM_TITU","CHAR", 8, true, "CAMPO_NUM_TITU");
		addField("AA_TITU","CHAR", 4, true, "CAMPO_AA_TITU");
		addField("CERTIFICADO_ID","NUMBER", 22, false, "CAMPO_CERTIFICADO_ID");
		addField("NUMPAG","NUMBER", 22, true, "CAMPO_NUMPAG");
		addField("REG_PUB_ID","CHAR", 2, false, "CAMPO_REG_PUB_ID");
		addField("OFIC_REG_ID","CHAR", 2, false, "CAMPO_OFIC_REG_ID");
		addField("OBJETO_SOL_ID","auto-inc", 0, false, "CAMPO_OBJETO_SOL_ID");
		addField("NUM_PLACA","CHAR", 7, true, "CAMPO_NUM_PLACA");
		addField("NS_ASIE_PLACA","NUMBER", 3, true, "CAMPO_NS_ASIE_PLACA");
		addField("TS_VERI_MANU","NUMBER", 22, true, "CAMPO_TS_VERI_MANU");
		
		//Inicio:jascencio:29/05/2007
		//SUNARP-REGMOBCOM: se agregara los siguientes fields 
		
		addField("NOMBRE_BIEN","VARCHAR", 254, true, "CAMPO_NOMBRE_BIEN");
		addField("NUM_MATRICULA","VARCHAR", 35, true, "CAMPO_NUM_MATRICULA");
		addField("NUM_SERIE","VARCHAR", 35, true, "CAMPO_NUM_SERIE");
		addField("TIP_PARTICIPANTE","VARCHAR", 50, true, "CAMPO_TIP_PARTICIPANTE");
		addField("SIGLAS", "VARCHAR", 15, true, "CAMPO_SIGLAS");
		addField("TIP_DOCUMENTO", "VARCHAR", 50, true, "CAMPO_TIP_DOCUMENTO");
		addField("NUM_DOCUMENTO","VARCHAR", 15, true, "CAMPO_NUM_DOCUMENTO");
		addField("TIP_INF_DOMINIO","CHAR", 1, true, "CAMPO_TIP_INF_DOMINIO");
		addField("TIP_REGISTRO","VARCHAR", 50, true, "CAMPO_TIP_REGISTRO");
		addField("INS_ASIENTO","NUMBER", 4, true, "CAMPO_INS_ASIENTO");
		addField("FLAG_HISTORICO","CHAR", 1, true, "CAMPO_FLAG_HISTORICO");
		addField("URL_BUSQ","VARCHAR", 500, true, "CAMPO_URL_BUSQ");
		// Inicio:mgarate:05/06/2007
		addField("CRIT_BUSQ","VARCHAR", 500, true, "CAMPO_CRIT_BUSQ");
		// Fin:mgarate:05/06/2007
		addField("FEC_INS_ASIENTO_DESDE","NUMBER", 7, true, "CAMPO_FEC_INS_ASIENTO_DESDE");
		addField("FEC_INS_ASIENTO_HASTA","NUMBER", 7, true, "CAMPO_FEC_INS_ASIENTO_HASTA");
		addField("NUM_PARTIDA","CHAR", 8, true, "CAMPO_NUMERO_PARTIDA");
		//Fin:jascencio:29/05/2007
		
		//inicio:dbravo:10/08/2007
		addField("FLAG_ACEPTA_CONDICION","CHAR", 1, true, "CAMPO_FLAG_ACEPTA_CONDICION");
		addField("FLAG_ENVIO_DOMICILIO","CHAR", 1, true, "CAMPO_FLAG_ENVIO_DOMICILIO");
		//fin:dbravo:10/08/2007
		//Inicio:jascencio:10/08/2007
		//CC:REGMOBCON-2006
		addField("REFNUM_PART_ANTIGUO","NUMBER", 22, true, "CAMPO_REFNUM_PART_ANTIGUO");
		//Fin
		
		addKey("OBJETO_SOL_ID");
	} /* setupFields() */


	public DBObject getThisDBObj() throws DBException {
        return new DboObjetoSolicitud();
	} /* getThisDBObj() */
} /* DboObjetoSolicitud */

