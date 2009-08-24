package gob.pe.sunarp.extranet.common.logica;

public interface Errors extends gob.pe.sunarp.extranet.common.Errors {
// FRAMEWORK DE REPLICACION
public static final String EC_NO_EXTRA_PARTIDA_HANDLE_FOUND = "E90001";
public static final String EC_EXTRA_PARTIDA_CANNOT_CREATED = "E90002";
public static final String EC_REPLICADOR_INTERRUMPIDO = "E90003";
public static final String EC_ENTIDADES_NO_ENCONTRADAS_CON_TRANSMISION = "E90004";
// KEYFILE
public static final String EC_FALLO_LOGON_KEYFILE = "E91001";
public static final String EC_CANNOT_OPEN_DOCUMENT_KEYFILE = "E91002";
public static final String EC_CANNOT_OPEN_ELEMENT_KEYFILE = "E91003";
public static final String EC_CANNOT_READ_DATA = "E91004";
public static final String EC_CANNOT_EXECUTE_KEYFILE_PROGRAM = "E91005";
public static final String EC_CANNOT_READ_OUTFILE_DAT = "E91006";
public static final String EC_CANNOT_READ_IMAGE_CONTENTS = "E91007";

public static final String EC_FALLO_ENTENDER_CADENA_DESCRIPTIVA = "E91008";
public static final String EC_NO_TODAS_PAGINAS_ENCONTRADAS = "E91009";
public static final String EC_NO_ASEGURADO_TENER_TODAS_PAGINAS = "E91010";
public static final String EC_NO_PAGINAS_ENCONTRADAS = "E91011";
public static final String EC_SIGUIENTE_PAGINA_ERROR = "E91012";
public static final String EC_NUMERO_ASIENTOS_DIFERENTE = "E91013";
// ENTENDER CADENAS IMAGENES KEYFILE
public static final String EC_OBJETO_KF_PRIMERA_PAGINA_NO_ES_UNO = "E91101";
public static final String EC_OBJETO_KF_PAGINAS_NO_CORRELATIVAS = "E91102";
public static final String WC_CADENA_KF_PAG_ASIENTO_NO_ENTENDIDA = "W91103";
public static final String WC_CADENA_KF_MAS_DE_4_CAMPOS = "W91104";
public static final String WC_TITULO_NULO_ANTES_1999 = "W91105";
public static final String EC_ALGUN_ASIENTO_SIN_IMAGEN = "E91106";
public static final String EC_NO_COINCIDE_ASIENTOS_KF_CON_ORACLE = "E91107";
public static final String EC_FOLIO_CON_MAS_DE_UNA_PAGINA = "E91108";
public static final String EC_PAG_FICHA_MINERIA_CON_MAS_DE_UNA_IMAGEN = "E91109";
public static final String EC_ARCHIVO_TIFF_NO_ENCONTRADO = "E91110";
public static final String EC_OBJETO_KF_PAGINAS_NO_ASCENDENTES_UN_ASIENTO = "E91111";


// MQE
public static final String EC_FALLO_ARMAR_MENSAJE_MQE = "E92001";
public static final String EC_FALLO_ENVIAR_MENSAJE_MQE = "E92002";
public static final String EC_CANNOT_CREATE_QUEUE = "E92003";
public static final String EC_CANNOT_RECUPERAR_MENSAJE_MQE = "E92004";
public static final String EC_CANNOT_LEER_MENSAJE_MQE = "E92005";
public static final String EC_FALLO_ENVIO_ACUSE_MQE = "E92006";
public static final String EC_TRANSMISION_NO_ENCONTRADA = "E92007";
// CM
public static final String EC_FALLO_CREAR_CONEXION_CM = "E93001";
public static final String EC_FALLO_ARMAR_OBJETO_CM = "E93002";
public static final String EC_FALLO_COMENZAR_TRANSACCION_CM = "E93003";
public static final String EC_FALLO_INSERTAR_IMAGEN = "E93004";
public static final String EC_FALLO_RECUPERAR_IMAGEN = "E93005";
public static final String EC_INDEXSUBCLASS_NO_ENCONTRADO = "E93006";
// LOGICA
public static final String EC_TIPO_ENTIDAD_NO_DEFINIDA = "E94001";
public static final String EC_AREA_REGISTRAL_NO_DEFINIDA = "E94002";
public static final String EC_PARTIDA_NO_ENCONTRADA = "E94003";
public static final String EC_ACTO_NO_ENCONTRADO = "E94004";
public static final String EC_RUBRO_NO_ENCONTRADO = "E94005";
public static final String EC_TIPO_PART_LIBRO_NO_ENCONTRADO = "E94006";
public static final String EC_PARTIDA_NO_TIENE_OID = "E94007";
public static final String EC_CUR_NO_CLASIFICABLE = "E94008";
public static final String EC_CODIGO_UBIGEO_INVALIDO = "E94009";
public static final String EC_ASIENTO_NO_ENCONTRADO = "E94010";
public static final String EC_TITULO_NO_ENCONTRADO = "E94011";
public static final String EC_SEGUIMIENTO_TITULO_NO_ENCONTRADO = "E94012";
public static final String EC_ESQUELA_NO_ENCONTRADA = "E94013";
public static final String EC_AREA_REGISTRAL_NO_CORRESPONDE = "E94014";
public static final String EC_HOJA_PRESENTACION_NO_ENCONTRADA = "E94015";
public static final String EC_ESTADO_TITULO_NO_CONTEMPLADO = "E94016";
public static final String EC_CANNOT_READ_ESQUELA_CONTENTS = "E94017";
public static final String EC_CANNOT_WRITE_ESQUELA_CONTENTS = "E94018";
public static final String EC_NO_COINCIDE_NUMERO_ASIENTOS = "E94019";
public static final String EC_FICHA_NO_ENCONTRADA = "E94020";//cjvc77
public static final String EC_NO_COINCIDEN_CANTIDAD_IMAGENES = "E94021";//cjvc77

public static final String EC_OID_FICHA_MINERIA_NULL = "E94022";
public static final String EC_ASIENTO_NO_TIENE_IMAGEN = "E94023";
public static final String EC_FICHA_NO_TIENE_IMAGEN = "E94024";
public static final String EC_FOLIO_NO_TIENE_IMAGEN = "E94025";
public static final String EC_NUM_FICHA_NO_NUMERICO = "E94026";
public static final String EC_NUM_PARTIDA_NO_NUMERICO = "E94027";
public static final String EC_NUM_TOMO_NULL = "E94028";
public static final String EC_NUM_FOLIO_NULL = "E94029";
public static final String EC_CADENA_ROTA = "E94030";
public static final String EC_NUMERO_PARTIDA_INVALIDO = "E94031";
public static final String WC_PARTIDA_INACTIVA = "W94032";
public static final String WC_PARTIDA_RPU = "W94033";
public static final String EC_CO_REGI_NULL = "E94034";
public static final String EC_CO_OFIC_REG_NULL = "E94035";
public static final String EC_CO_AREA_NULL = "E94036";
public static final String WC_IN_ESTD_PART_VALOR_NO_PERMITIDO = "W94037";
public static final String EC_NO_EXISTE_CUR_PRTC = "E94038";//cjvc77
public static final String EC_NO_EXISTE_TI_PERS = "E94039";//cjvc77
public static final String EC_NO_HAY_IMAGENES_EN_OID = "E94040";

public static final String EC_FICHA_EXISTE_MAS_DE_UNO_EN_SUNARP = "E95001";
public static final String EC_PARTIDA_NO_EXISTE_EN_OFICINA = "E95002";
public static final String EC_PARTIDA_EXISTE_MAS_DE_UNO_EN_OFICINA = "E95003";
public static final String EC_PARTIDA_OID_INVALIDO = "E95004";
public static final String EC_FOLIO_NO_EXISTE_EN_OFICINA = "E95005";
public static final String EC_FOLIO_EXISTE_MAS_DE_UNO_EN_OFICINA = "E95006";
public static final String EC_FOLIO_OID_INVALIDO = "E95007";
public static final String EC_ASIENTO_MIN_NO_EXISTE_EN_OFICINA = "E95008";
public static final String EC_ASIENTO_MIN_EXISTE_MAS_DE_UNO_EN_OFICINA = "E95009";
public static final String EC_ASIENTO_MIN_OID_INVALIDO = "E95010";
public static final String EC_FICHA_MIN_OID_INVALIDO = "E95011";
public static final String EC_FICHA_MIN_SIN_OIDs = "E95012";

}

