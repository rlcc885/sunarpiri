package gob.pe.sunarp.extranet.dbobj;

import com.jcorporate.expresso.core.dbobj.*;
import com.jcorporate.expresso.core.db.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class DboVehiculo extends DBObject {
	
	public static final String CAMPO_NUM_PLACA                = "NUM_PLACA";
	public static final String CAMPO_REFNUM_PART              = "REFNUM_PART";
	public static final String CAMPO_TITU_NUM_POLIZA          = "TITU_NUM_POLIZA";
	public static final String CAMPO_COD_MODELO               = "COD_MODELO";
	public static final String CAMPO_COD_COND_VEHI            = "COD_COND_VEHI";
	public static final String CAMPO_COD_TIPO_VEHI            = "COD_TIPO_VEHI";
	public static final String CAMPO_COD_TIPO_COMB            = "COD_TIPO_COMB";
	public static final String CAMPO_COD_MARCA                = "COD_MARCA";
	public static final String CAMPO_DESC_COLOR_01            = "DESC_COLOR_01";
	public static final String CAMPO_DESC_COLOR_02            = "DESC_COLOR_02";
	public static final String CAMPO_DESC_COLOR_03            = "DESC_COLOR_03";
	public static final String CAMPO_COD_COLOR_01_ORIG        = "COD_COLOR_01_ORIG";
	public static final String CAMPO_COD_COLOR_02_ORIG        = "COD_COLOR_02_ORIG";
	public static final String CAMPO_COD_COLOR_03_ORIG        = "COD_COLOR_03_ORIG";
	public static final String CAMPO_NUM_TARJ_PROP            = "NUM_TARJ_PROP";
	public static final String CAMPO_FG_PROVINCIA             = "FG_PROVINCIA";
	public static final String CAMPO_ANO_FABRIC               = "ANO_FABRIC";
	public static final String CAMPO_NUM_SERIE                = "NUM_SERIE";
	public static final String CAMPO_NUM_MOTOR                = "NUM_MOTOR";
	public static final String CAMPO_NUM_CILINDROS            = "NUM_CILINDROS";
	public static final String CAMPO_PESO_SECO                = "PESO_SECO";
	public static final String CAMPO_PESO_BRUTO               = "PESO_BRUTO";
	public static final String CAMPO_NUM_PASAJEROS            = "NUM_PASAJEROS";
	public static final String CAMPO_NUM_ASIENTOS             = "NUM_ASIENTOS";
	public static final String CAMPO_NUM_EJES                 = "NUM_EJES";
	public static final String CAMPO_NUM_RUEDAS               = "NUM_RUEDAS";
	public static final String CAMPO_NUM_PUERTAS              = "NUM_PUERTAS";
	public static final String CAMPO_LONGITUD                 = "LONGITUD";
	public static final String CAMPO_ANCHO                    = "ANCHO";
	public static final String CAMPO_ALTURA                   = "ALTURA";
	public static final String CAMPO_NUM_POLIZA               = "NUM_POLIZA";
	public static final String CAMPO_TS_INSCRIP               = "TS_INSCRIP";
	public static final String CAMPO_FG_BAJA                  = "FG_BAJA";
	public static final String CAMPO_COD_IMPORTADOR           = "COD_IMPORTADOR";
	public static final String CAMPO_IMPORTADOR_DESC          = "IMPORTADOR_DESC";
	public static final String CAMPO_COD_TIPO_CARR            = "COD_TIPO_CARR";
	public static final String CAMPO_FG_IMPORTADO             = "FG_IMPORTADO";
	public static final String CAMPO_CETICO_DESC              = "CETICO_DESC";
	public static final String CAMPO_FG_ACTIVIDAD             = "FG_ACTIVIDAD";
	public static final String CAMPO_COD_DIRECTIV             = "COD_DIRECTIVA";
	public static final String CAMPO_TS_ULT_SYNC              = "TS_ULT_SYNC";
	public static final String CAMPO_AGNT_SYNC                = "AGNT_SYNC";
	public static final String CAMPO_COD_CETICO               = "COD_CETICO";
	
	
	public DboVehiculo() throws DBException{
		super();
	}

	public DboVehiculo(DBConnection theConnection) throws DBException {
		super(theConnection);
	} /* DboTPHojaPres(DBConnection) */
	
	protected synchronized void setupFields() throws DBException {
		setTargetTable("VEHICULO");

		setDescription("Object Description Goes Here");
	
		
		addField("NUM_PLACA","CHAR", 7, false, "NUM_PLACA");
		addField("REFNUM_PART","NUMBER", 22, false, "REFNUM_PART");
		addField("TITU_NUM_POLIZA","CHAR", 2, true, "TITU_NUM_POLIZA");
		addField("COD_MODELO","CHAR", 5, true, "COD_MODELO");
		addField("COD_COND_VEHI","CHAR", 2, false, "COD_COND_VEHI");
		addField("COD_TIPO_VEHI","CHAR", 2, false ,"COD_TIPO_VEHI");
		addField("COD_TIPO_COMB","CHAR", 2, false, "COD_TIPO_COMB");
		addField("COD_MARCA","CHAR", 4, false, "COD_MARCA");
		addField("DESC_COLOR_01","VARCHAR", 30, true, "DESC_COLOR_01");
		addField("DESC_COLOR_02","VARCHAR", 30, true, "DESC_COLOR_02");
		addField("DESC_COLOR_03","VARCHAR", 30, true, "DESC_COLOR_03");
		addField("COD_COLOR_01","VARCHAR", 30, true, "COD_COLOR_01");
		addField("COD_COLOR_02","VARCHAR", 30, true, "COD_COLOR_02");
		addField("COD_COLOR_03","VARCHAR", 30, true, "COD_COLOR_03");
		addField("NUM_TARJ_PROP","CHAR", 10, true, "NUM_TARJ_PROP");
		addField("FG_PROVINCIA","CHAR", 1, true, "FG_PROVINCIA");
		addField("ANO_FABRIC","CHAR", 4, true, "ANO_FABRIC");
		addField("NUM_SERIE","VARCHAR", 30, true, "NUM_SERIE");
		addField("NUM_MOTOR","VARCHAR", 30, true, "NUM_MOTOR");
		addField("NUM_CILINDROS","NUMBER", 2, true, "NUM_CILINDROS");
		addField("PESO_SECO","NUMBER", 5,3, true, "PESO_SECO");
		addField("PESO_BRUTO","NUMBER", 5,3, true, "PESO_BRUTO");
		addField("NUM_PASAJEROS","NUMBER", 3, true, "NUM_PASAJEROS");
		addField("NUM_ASIENTOS","NUMBER", 3, true, "NUM_ASIENTOS");
		addField("NUM_EJES","NUMBER", 3,true, "NUM_EJES");
		addField("NUM_RUEDAS","NUMBER", 2, true, "NUM_RUEDAS");
		addField("NUM_PUERTAS","NUMBER",2, true, "NUM_PUERTAS");
		addField("LONGITUD","NUMBER", 4,2, true, "LONGITUD");
		addField("ANCHO","NUMBER", 4,2, true, "ANCHO");
		addField("ALTURA","NUMBER", 4,2, true, "ALTURA");
		addField("NUM_POLIZA","VARCHAR", 15, true, "NUM_POLIZA");
		addField("TS_INSCRIP","NUMBER", 22, true, "TS_INSCRIP");
		addField("FG_BAJA","CHAR", 1, true, "FG_BAJA");
		addField("COD_IMPORTADOR","CHAR", 4, true, "COD_IMPORTADOR");
		addField("IMPORTADOR_DESC","VARCHAR", 40, true, "IMPORTADOR_DESC");
		addField("COD_TIPO_CARR","CHAR", 5, false, "COD_TIPO_CARR");
		addField("FG_IMPORTADO","CHAR", 1, true, "FG_IMPORTADO");
		addField("CETICO_DESC","VARCHAR", 30, true, "CETICO_DESC");
		addField("FG_ACTIVIDAD","CHAR", 1, true, "FG_ACTIVIDAD");
		addField("COD_DIRECTIVA","CHAR", 2, true, "COD_DIRECTIVA");
		addField("TS_ULT_SYNC","NUMBER", 22, true, "TS_ULT_SYNC");
		addField("AGNT_SYNC","CHAR", 4, true, "AGNT_SYNC");
		addField("COD_CETICO","CHAR", 1, true, "COD_CETICO");
		
		//KEYS
		addKey("NUM_PLACA");
		
		
		
	}
	
	public DBObject getThisDBObj() throws DBException {
        return new DboVehiculo();
	} /* getThisDBObj() */
	
			

}

