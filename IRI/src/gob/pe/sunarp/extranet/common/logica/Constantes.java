package gob.pe.sunarp.extranet.common.logica;
public interface Constantes {
	public static final String ESTADO_POR_TRANSMITIR = "PT";
	public static final String ESTADO_TRANSMITIDO = "TX";
	public static final String ESTADO_RECIBIDO_ACUSE1 = "A1";
	public static final String ESTADO_RECIBIDO_ACUSE2 = "A2";

	public static final String TIPO_PARTIDA_COMPLETA = "PACO";
	public static final String TIPO_PARTIDA = "PART";
	public static final String TIPO_ASIENTO = "ASIE";
	public static final String TIPO_TITULO = "TITU";
	public static final String TIPO_INDICE = "PAIN";
	
	public static final String TRIGGER_PARTIDA_NATURAL = "PNAT";
	public static final String TRIGGER_PARTIDA_JURIDICO = "PJUR";
	public static final String TRIGGER_PARTIDA_INMUEBLE = "PINM";
	public static final String TRIGGER_TITULO_NATURAL = "TNAT";
	public static final String TRIGGER_TITULO_JURIDICO = "TJUR";
	public static final String TRIGGER_TITULO_INMUEBLE = "TINM";
	public static final String TRIGGER_TITULO_FECHA_NATURAL = "TFNA";
	public static final String TRIGGER_TITULO_FECHA_JURIDICO = "TFJU";
	public static final String TRIGGER_TITULO_FECHA_INMUEBLE = "TFIN";
	public static final String TRIGGER_ASIENTOS = "ASIE";

	public static final String FIELD_DATA = "DATA";
	public static final String FIELD_TX_REFNUM = "TXREFNUM";
	public static final String FIELD_REG_PUBLICO = "REGPUB";
	public static final String FIELD_OFICINA_REG = "OFICREG";
	public static final String FIELD_AREA_REG = "AREAREG";
	public static final String FIELD_TX_ESTADO = "TXESTADO";
	public static final String FIELD_TMSTMP_TX = "TMSTMPTX";
	public static final String FIELD_ERROR_CODE = "ERRCODE";
	public static final String FIELD_FECHA_INICIO = "FECHAINI";
	public static final String FIELD_FECHA_FIN = "FECHAFIN";
	public static final String FIELD_TIPO_ENTIDAD = "ENTTIPO";
	public static final String FIELD_NUMERO_PARTE = "NUMEROPARTE";
	public static final String FIELD_TOTAL_PARTES = "TOTALPARTES";

	public static final String AREA_PERSONA_NATURAL = "23000";
	public static final String AREA_PERSONA_JURIDICA = "22000";
	public static final String AREA_PROPIEDAD_INMUEBLE = "21000";
	public static final String ID_AREA_PI_MINERIA = "MINERIA";

//	public static final String INDICE_ESTADO_ACTIVO = "A";
//	se supone que era solo 'A' pero encontramos 'A' solo cuando es indice de un participante de una enmienda.(?)
//	se decidio traerse todos los participantes

	public static final String CUR_SIR_NATURAL = "TN";
	public static final String CUR_SIR_JURIDICO = "TJ";
	public static final String CUR_SIR_INSTITUCION = "TI";

	public static final String CUR_EX_NATURAL = "N";
	public static final String CUR_EX_JURIDICO = "J";
	public static final String CUR_EX_NODEFINIDO = "X";

	public static final String RUBRO_DEFAULT = "000";

	public static final String ESTADO_ACTIVO = "1";
	public static final String ESTADO_INACTIVO = "0";

	public static final long ID_IMAGEN_NO_IMAGEN = 0;

	public static final String INDEX_SUBCLASS_ASIENTO = "ASIENTO";
//	public static final String INDEX_SUBCLASS_OTRO = "OTRO";
	public static final String INDEX_SUBCLASS_FICHA = "FICHA";
	public static final String INDEX_SUBCLASS_FOLIO = "FOLIO";
	
	public static final String KEYFILE_TITULO_NULO = "99999999";
	public static final String KEYFILE_ANO_NULO = "9999";

	public static final String ESTADO_TITULO_OTRO = "00";
	public static final String ESTADO_TITULO_PRESENTADO = "10";
	public static final String ESTADO_TITULO_INSCRITO = "60";
	public static final String ESTADO_TITULO_TACHADO = "50";
	public static final String ESTADO_TITULO_OBSERVADO = "40";
	public static final String ESTADO_TITULO_LIQUIDADO = "30";
	public static final String ESTADO_TITULO_EN_CALIFICACION = "20";
	public static final String ESTADO_TITULO_DESPACHADO = "70";

	// nuevos estados	
	public static final String ESTADO_TITULO_PRORROGADO_EN_CALIFICACION = "80";
	public static final String ESTADO_TITULO_PRORROGADO_LIQUIDADO = "90";
	public static final String ESTADO_TITULO_PRORROGADO_OBSERVADO = "100";
	public static final String ESTADO_TITULO_RESERVADO = "130";
	public static final String ESTADO_TITULO_SUSPENDIDO = "120";
	public static final String ESTADO_TITULO_APELACION = "110";
	public static final String ESTADO_TITULO_TACHADO_VENCIMIENTO = "140";
	public static final String ESTADO_TITULO_DECRETADO = "150";

	// atributos pasados en el mensaje MQe de replciacion
	public static final String ATRIBUTO_REGISTRO_IMAGENES = "REGISTRO_IMAGENES";
	
	public static final String TIPO_PERSONA_NATURAL = "TPNATU";
	public static final String TIPO_PERSONA_JURIDICA = "TPJURI";
	
	
}

