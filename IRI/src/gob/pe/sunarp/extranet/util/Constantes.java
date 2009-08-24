package gob.pe.sunarp.extranet.util;

/*
* interface de constantes
* @version 1
*/

public interface Constantes extends gob.pe.sunarp.extranet.common.Errors
{
//acceso
public static final String NO_PERFILCUENTA_USUARIO = "E20001";
public static final String NO_SALDO_DE_LINEA_PREPAGO = "E20002";
public static final String NO_REG_PE_NATU = "E20003";
public static final String CUENTA_DESHABILITADA = "E20004";
public static final String USER_PWD_NO_COINCIDEN = "E20005";
public static final String NO_COINCIDEN_PWDS = "E20006";
public static final String USER_NO_EXISTE = "E20007";
public static final String NO_REG_PE_JURI = "E20008";
public static final String NO_REG_CONTRATO = "E20009";
public static final String NO_REG_CUENTA = "E20010";
public static final String NO_REG_CUENTA_JURIS = "E20011";
public static final String NO_LINEA_PREPAGO_ORG = "E20012";

public static final String E09999_ERROR_GENERICO = "E09999";	
//afiliación
public static final String E40000_USUARIO_YA_EXISTE = "E40000";
//publicidad
public static final String E70000_NO_EXISTE_SESION = "E70000";
public static final String E70001_SALDO_INSUFICIENTE = "E70001";
public static final String E70002_NO_ENCONTRO_PARTIDA = "E70002";
public static final String E70003_ERROR_EN_DATOS_A_BUSCAR = "E70003";

//TAM
public static final String E08000_ERROR_ABRIENDO_SESION = "E08000";
public static final String E08001_ERROR_CERRANDO_SESION = "E08001";
public static final String E08002_ERROR_TAM = "E08002";
public static final String E08003_PERFIL_DESCONOCIDO = "E08003";
public static final String E08004_USUARIO_NO_VALIDADO_EN_TAM = "E08004";
public static final String E08005_USUARIO_SIN_GRUPO = "E08005";

//Modificado por: Proyecto Filtros de Acceso
//Fecha: 02/10/2006
public static final String E08006_ERROR_POLITICA_TOD_ACCESS_TAM_ = "E08006";
public static final String E08007_TIENE_SESION_ACTIVA = "E08007";
public static final String E08008_DIRECCION_IP_NO_TIENE_ACCESO = "E08008";
public static final String E08009_ES_FERIADO = "E08009";
//Fecha: 07/10/2006
public static final String E08010_NO_EXISTE_SESION_PD = "E08010";
//Fin Modificación
  
//TAM errores especificos que pueden ocurrir durante autenticación
public static final String E08050_CUENTA_EXPIRADA = "E08050";
public static final String E08051_CREDENCIAL_EXPIRADA = "E08051";
public static final String E08052_NO_AUTENTICADO = "E08052";
public static final String E08053_LOGIN_ERROR = "E08053";
public static final String E08054_USUARIO_INCORRECTO = "E08054";
public static final String E08055_CLAVE_INCORRECTA = "E08055";
//perfiles
public static final long PERFIL_ADMIN_GENERAL      = 10;
public static final long PERFIL_INDIVIDUAL_EXTERNO = 20;
public static final long PERFIL_AFILIADO_EXTERNO   = 30;
public static final long PERFIL_ADMIN_ORG_EXT      = 40;
public static final long PERFIL_TESORERO           = 50;
public static final long PERFIL_CAJERO             = 60;
public static final long PERFIL_ADMIN_JURISDICCION = 70;
public static final long PERFIL_INTERNO            = 80;
/**DESCAJ lsuarez  09/02/2007**/
public static final long PERFIL_DEVOLUCIONES       = 90;


//códigos
public static final String ID_JURISDICCION_FUERA_DEL_PERU ="00";
public static final String ID_REGISTRO_PUBLICO_FUERA_DEL_PERU ="00";
public static final String ID_OFICINA_FUERA_DEL_PERU ="00";
public static final String ID_ZONA_REGISTRAL_WEB="00";

//tipos de documentos
public static final String TIPO_DOCUMENTO_RUC = "05";

//tipos de busquedas de partidas
//busquedas directas
public final static int BUSQUEDA_DIRECTA_POR_PARTIDA=10;
public final static int BUSQUEDA_DIRECTA_POR_FICHA=20;
public final static int BUSQUEDA_DIRECTA_POR_TOMOFOLIO=30;
//busquedas indirectas
public final static int BUSQUEDA_INDIRECTA_PERSONA_NATURAL = 510;
public final static int BUSQUEDA_INDIRECTA_PERSONA_JURIDICA = 520;
public final static int BUSQUEDA_INDIRECTA_RAZON_SOCIAL = 530;
public final static int BUSQUEDA_INDIRECTA_PREDIO = 540;
//___16oct2002 - HT
//___public final static int BUSQUEDA_INDIRECTA_MINERIA = 550;
public final static int BUSQUEDA_INDIRECTA_MINERIAxDERECHO = 551;
public final static int BUSQUEDA_INDIRECTA_MINERIAxSOCIEDAD = 552;
public final static int BUSQUEDA_INDIRECTA_EMBARCACION = 560;
public final static int BUSQUEDA_INDIRECTA_BUQUE = 570;
public final static int BUSQUEDA_INDIRECTA_AERONAVEXMATRICULA = 580;
public final static int BUSQUEDA_INDIRECTA_AERONAVEXNOMBRE = 590;
public final static int BUSQUEDA_INDIRECTA_AERONAVEXRAZONSOCIAL = 600;

//codigos de tablas para MantenimientoController
public final static int TABLA_TM_ACTO = 1;
public final static int TABLA_TM_AREA_REGISTRAL = 2;
public final static int TABLA_TM_LIBRO = 3;
public final static int TABLA_TM_RUBRO = 4;
public final static int TABLA_TM_DEPARTAMENTO = 5;
public final static int TABLA_TM_PROVINCIA = 6;
public final static int TABLA_TM_DISTRITO  = 7;
public final static int TABLA_TM_DOC_IDEN  = 8;
public final static int TABLA_TM_GIRO  = 9;
public final static int TABLA_NOTARIA = 10;
public final static int TABLA_PARTIC_LIBRO = 11;
public final static int TABLA_TM_SERVICIO = 12;

public final static int TABLA_TM_JURISDICCION = 13;
public final static int TABLA_REGIS_PUBLICO = 14;
public final static int TABLA_OFIC_REGISTRAL = 15;
public final static int TABLA_TM_BANCO = 16;

// Agregado por JACR - Inicio

public final static int TABLA_TM_MODELO_VEHI = 17;
public final static int TABLA_TM_MARCA_VEHI = 18;
public final static int TABLA_TM_COND_VEHI = 19;
public final static int TABLA_TM_TIPO_VEHI = 20;
public final static int TABLA_TM_TIPO_COMB = 21;
public final static int TABLA_TM_TIPO_CARR = 22;
public final static int TABLA_TIPO_AFEC = 23;

// Agregado por JACR - Fin

//servicios
public final static int SERVICIO_CONSULTAR_PARTIDA = 10;


	public static final String ESTADO_POR_TRANSMITIR = "PT";
	public static final String ESTADO_TRANSMITIDO = "TX";
	public static final String ESTADO_RECIBIDO_ACUSE1 = "A1";
	public static final String ESTADO_RECIBIDO_ACUSE2 = "A2";

	public static final String TIPO_PARTIDA_COMPLETA = "PACO";
	public static final String TIPO_PARTIDA = "PART";
	public static final String TIPO_ASIENTO = "ASIE";
	public static final String TIPO_TITULO = "TITU";

	public static final String FIELD_DATA = "DATA";
	public static final String FIELD_TX_REFNUM = "TXREFNUM";
	public static final String FIELD_REG_PUBLICO = "REGPUB";
	public static final String FIELD_OFICINA_REG = "OFICREG";
	public static final String FIELD_TX_ESTADO = "TXESTADO";
	public static final String FIELD_TMSTMP_TX = "TMSTMPTX";
	public static final String FIELD_ERROR_CODE = "ERRCODE";
	public static final String FIELD_FECHA_INICIO = "FECHAINI";
	public static final String FIELD_TIPO_ENTIDAD = "ENTTIPO";

	public static final String AREA_PERSONA_NATURAL = "23000";
	public static final String AREA_PERSONA_JURIDICA = "22000";
	public static final String AREA_PROPIEDAD_INMUEBLE = "21000";

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
	


	public static final int REG_AEREO = 1;
	public static final int REG_PREDIO = 2;
	public static final int REG_MINERO = 3;
	public static final int REG_EMB= 4;
	public static final int REG_RAZ_SOC_PJ = 5;	
	public static final int REG_VEH_MOTOR = 6;	
	public static final int REG_VEH_SERIE = 7;	
	
	
	


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

public static final String EC_NO_TODAS_IMAGENES_DISPONIBLES = "E95103";
	










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

	public static final String FIELD_AREA_REG = "AREAREG";
	public static final String FIELD_FECHA_FIN = "FECHAFIN";
	public static final String FIELD_NUMERO_PARTE = "NUMEROPARTE";
	public static final String FIELD_TOTAL_PARTES = "TOTALPARTES";

//	public static final String INDICE_ESTADO_ACTIVO = "A";
//	se supone que era solo 'A' pero encontramos 'A' solo cuando es indice de un participante de una enmienda.(?)
//	se decidio traerse todos los participantes

	public static final String ESTADO_TITULO_DECRETADO = "150";



	public static final int CONSULTA_PARTIDA = 10;
	
	public static final int BUSQ_SEDE_20 = 20;
	public static final int BUSQ_SEDE_21 = 21;
	public static final int BUSQ_SEDE_22 = 22;
	public static final int BUSQ_SEDE_23= 23;
	public static final int BUSQ_SEDE_24 = 24;
	public static final int BUSQ_SEDE_25 = 25;
	public static final int BUSQ_SEDE_26 = 26;
	public static final int BUSQ_SEDE_27 = 27;
	public static final int BUSQ_SEDE_28 = 28;
	public static final int BUSQ_SEDE_29 = 29;
	public static final int BUSQ_SEDE_30 = 30;
	public static final int BUSQ_SEDE_31 = 31;
	public static final int BUSQ_SEDE_32 = 32;
	
	public static final int CONSULTA_TITULOS = 40;	// anadida el 07/09/2002 11:30 pm - externos
	public static final int BUSQUEDA_TITULOS = 50; //internos
	public static final int AFILIACION = 60;
	/*
	public static final int PREDIOS = 70;
	public static final int EMBARCACIONES = 80;
	public static final int AERONAVES = 90;
	public static final int MINERIA = 100;
	*/
	public static final int VISUALIZACION = 70;
	public static final int TITULOS_RPV = 80;
	public static final int PARTIDAS_RPV = 90;
	public static final int BUSQUEDA_RPV = 100;
	
	
	// en request
	public static final String DATOS_URL = "datosUrl";
	public static final String VIEW_URI = "viewURI";
	public static final String EVENTS = "events";

	// en sesion
	public static final String SESSION_DATA = "Usuario";	
	
	
	public static final int SERVICIO_CONSULTA_TITULO_RPV = 80;	
	public static final int SERVICIO_CONSULTA_PARTIDA_RPV = 90;	
	public static final int SERVICIO_BUSQUEDA_PARTIDA_RPV = 100;	
	/**
	 * @autor Daniel Bravo
	 * @fecha 18/06/2007
	 * @descripcion Agregar las constantes de los servicios creados para el proyecto RMC
	 */
	public static final int SERVICIO_CERTIFICADO_RMC_POSITIVO_NEGATIVO = 131;
	public static final int SERVICIO_CERTIFICADO_RMC_GRAVAMEN = 132;
	public static final int SERVICIO_CERTIFICADO_RMC_VIGENCIA = 133;
	
	public static final int SERVICIO_CERTIFICADO_RJB_DOMINALES_VEHICULAR = 129;
	public static final int SERVICIO_CERTIFICADO_RJB_DOMINALES_EMB_PESQ = 136;
	public static final int SERVICIO_CERTIFICADO_RJB_DOMINALES_BUQUES = 130;
	public static final int SERVICIO_CERTIFICADO_RJB_DOMINALES_AERONAVES = 135;
	
	public static final int SERVICIO_CERTIFICADO_RJB_GRAVAMEN_VEHICULAR = 137;
	public static final int SERVICIO_CERTIFICADO_RJB_GRAVAMEN_EMB_PESQ = 141;
	public static final int SERVICIO_CERTIFICADO_RJB_GRAVAMEN_BUQUES = 138;
	public static final int SERVICIO_CERTIFICADO_RJB_GRAVAMEN_AERONAVES = 139;
	/**** inicio:jrosas 13-08-07***/
	public static final int SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC = 34;
	public static final int BUSQUEDA_DIRECTA_PARTIDA_RMC = 41;
	public static final int SERVICIO_WEBSERVICE_BUSQUEDA_INDICE_PARTIDA_RMC = 37;
	/**** fin:jrosas 13-08-07***/
	//inicio servicios para publicidad masiva relacional
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_0_19 = 180;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_20_100 = 181;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_101_500 = 182;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_501_1000 = 183;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_1001_10000 = 184;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_10001_50000 = 185;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_50001_100000 = 186;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_100001_MAS = 187;

	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19 = 188;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_20_100 = 189;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_101_500 = 190;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_501_1000 = 191;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_1001_10000 = 192;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_10001_50000 = 193;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_50001_100000 = 194;
	public static final int SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_100001_MAS = 195;
	
	public static final int SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE = 150;
	public static final int SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_POR_PAGINA = 151;
	//	fin servicios para publicidad masiva relacional
	/**
	 * fin dbravo
	 */
	
	//Inicio:mgarate:19/06/2007
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_VEHICULAR = 134;
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_EMB_PESQ = 142;
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_BUQUES = 144;
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_AERONAVES = 143;
	
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_VEHICULAR = 145;
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_EMB_PESQ = 146;
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_BUQUES = 148;
	public static final int SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_AERONAVES = 147;
	
	public static final int SERVICIO_CERTIFICADO_CREM_ACTOS_VIGENTES = 126;
	public static final int SERVICIO_CERTIFICADO_CREM_HISTORICOS = 127;
	public static final int SERVICIO_CERTIFICADO_CREM_CONDICIONADO = 128;
	//Fin:mgarate:19/06/2007
	
	//inicio:dbravo:10/08/2007
	public static final int SERVICIO_CERTIFICADO_CREM_ACTOS_VIGENTE_PAGINA = 152;
	public static final int SERVICIO_CERTIFICADO_CREM_HISTORICO_PAGINA = 153;
	public static final int SERVICIO_CERTIFICADO_CREM_CONDICIONADO_PAGINA = 154;
	//inicio:dbravo:10/08/2007
	

	// ID del PoolDeConexiones de CM para WEB.
	public static final String CM_ID_WEB = "DEFAULT";
	
	//*constantes de vehicular*//
	public static final String AREA_PROPIEDAD_VEHICULAR = "24000";
	//Inicio:mgarate:24/08/2007
	public static final String AREA_PROPIEDAD_RMC = "24000";
	//Fin:mgarate
	public static final String PROPIETARIO_VEHI = "038";
	public static final String LIBRO_VEHI = "088";
	
	
	public final static int BUSQUEDA_INDIRECTA_NUMERO_MOTOR = 710;
	public final static int BUSQUEDA_INDIRECTA_NUMERO_CHASIS = 720;
	//**//
	
	//*constantes de Publicidad Certificada *//
	public static final String CERTIFICADO_NEGATIVO = "N";
	public static final String CERTIFICADO_POSITIVO = "P";
	
	public static final String COPIA_LITERAL = "L";
	
	public static final String REGIS_VERIFICADOR = "VE";
	public static final String REGIS_EMISOR = "EM";

	public static final String PAGO_CHEQUE = "C";
	public static final String PAGO_EFECTIVO = "E";	
	public static final String PAGO_LINEA_PREPAGO = "P";
	public static final String PAGO_TARJETA_DE_CREDITO = "T";
	
	public static final String ESTADO_SOL_POR_PAGAR = "P";
	public static final String ESTADO_SOL_POR_VERIFICAR = "C";
	public static final String ESTADO_SOL_POR_EXPEDIR = "V";
	public static final String ESTADO_SOL_POR_DESPACHAR = "E";
	public static final String ESTADO_SOL_DESPACHADA = "D";
	public static final String ESTADO_SOL_CANCELADA = "X";
	public static final String ESTADO_SOL_IMPROCEDENTE = "I";	
	
	public static final String ESTADO_ATEN_PENDIENTE = "P";
	public static final String ESTADO_ATEN_ATENDIDA = "C";
	
	public static final String PERSONA_NATURAL = "N";
	public static final String PERSONA_JURIDICA = "J";
	public static final String PERSONA_AMBAS = "A";// para indicar que puede ser de cualquiera de los dos tipos natural o juridica
	
	
	public static final String ABONO_CONCEPTO_AMPLIACION_CUENTA  = "A";
	public static final String ABONO_CONCEPTO_DEPOSITO_APERTURA  = "D";
	public static final String ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA  = "P";
	
	/*
	SELECT co.contrato_id, li.linea_prepago_id 
	FROM cuenta cu, contrato co, linea_prepago li 
	WHERE cu.cuenta_id = co.cuenta_id 
	AND cu.cuenta_id = li.cuenta_id 
	AND cu.usr_id= 'UPUBCERT'
	*/
	public static final String COMODIN_USUARIO  = "PRUEBAAFI"; // "UPUBCERT";
	public static final int COMODIN_LINEA_PREPAGO  = 1; //15463;
	public static final int COMODIN_CONTRATO_ABONAR  = 1; //14090;

	public static final String TIPO_VENTANILLA  = "V";
	public static final String TIPO_DOMICILIO  = "D";
	
	public static final String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"}; 
	public static final String ACCION_VISUALIZAR_PARTIDA_VERIFICA = "VERIFICA";
	public static final String ACCION_VISUALIZAR_PARTIDA_EXPIDE = "EXPIDE";
	
	public static final String IMPRESION_COPIA_SIMPLE = "SIMPLE";
	public static final String IMPRESION_COPIA_CERTIFICADA = "CERTIFICADA";
	
	public static final int PARAM_REFRESH = 107;
	public static final int PARAM_GLOSA = 108;
	
	public static final int SERVICIO_DELIVERY = 140;	
	//**//
	
	
	/****************** AGREGADO GIANCARLO OCHOA CONSTANTES PARA WEB SERVICE ESCRITURA PUBLICA **************/
	//public static final String NUMEROHOJA = "numeroHoja";
	//public static final String ANHO = "anho";
	public static final String CODIGOAREA = "codigoArea";
	public static final String DESCRIPCIONAREA = "descripcionArea";
	public static final String CODIGOACTO = "codigoActo";
	public static final String DESCRIPCIONACTO = "descripcionActo";	
	public static final String CODIGOLIBRO = "codigoLibro";
	public static final String DESCRIPCIONLIBRO = "descripcionLibro";	
	public static final String CODIGOZONAREGISTRAL = "codigoZonaRegistral";
	//public static final String DESCRIPCIONZONAREGISTRAL = "descripcionZonaRegistral";
	public static final String CODIGOOFICINAREGISTRAL = "codigoOficinaRegistral";
	//public static final String DESCRIPCIONOFICINAREGISTRAL = "descripcionOficinaRegistral";
	public static final String CUO = "cuo";
	public static final String CODIGOSERVICIO = "codigoServicio";
	public static final String DESCRIPCIONSERVICIO = "descripcionServicio";
	public static final String INDICADORRUC = "indicadorRUC";
	public static final String DOCUMENTOS = "documentos";
	public static final String PRESENTANTE = "presentante";
	public static final String PERSONAJURIDICA = "personaJuridica";
	public static final String ACCION = "accion";
	public static final String RESERVAMERCANTIL = "reservaMercantil";
	public static final String INTERESADO = "interesado";
	public static final String PARTICIPANTESPERSONANATURAL = "participantesPersonaNatural";
	public static final String PARTICIPANTESPERSONAJURIDICA = "participantesPersonaJuridica";
	public static final String INSTRUMENTOSPUBLICO = "instrumentosPublico";
	public static final String DATOSPAGO = "datosPago";
	
	public static final String CODIGOUSUARIO = "codigoUsuario";
	public static final String CODIGOPRESENTANTE = "codigoPresentante";
	public static final String APELLIDOPATERNOPRESENTANTE = "apellidoPaternoPresentante";
	public static final String APELLIDOMATERNOPRESENTANTE = "apellidoMaternoPresentante";
	public static final String NOMBREPRESENTANTE = "nombrePresentante";	
	public static final String CODIGOINSTITUCION = "codigoInstitucion";
	public static final String DESCRIPCIONINSTITUCION = "descripcionInstitucion";
	public static final String CODIGOTIPODOCPRESENTANTE = "codigoTipoDocPresentante";
	public static final String DESCRIPCIONTIPODOCPRESENTANTE = "descripcionTipoDocPresentante";
	public static final String NUMERODOCPRESENTANTE = "numeroDocPresentante";
	
	/***** MODIFICADO JBUGARIN 12/09/06******/
	public static final String UBIGEOPRESENTANTE = "ubigeoPresentante";
	public static final String DESCRIPCIONDEPARTAMENTOPRESENTANTE = "descripcionDepartamentoPresentante";
	public static final String DESCRIPCIONPROVINCIAPRESENTANTE = "descripcionProvinciaPresentante";
	public static final String DESCRIPCIONDISTRITOPRESENTANTE = "descripcionDistritoPresentante";
	public static final String DESCRIPCIONTIPOVIA = "descripcionTipoVia";
	public static final String CODIGOTIPOVIA = "codigoTipoVia";
	
	public static final String DESCRIPCIONTIPOVIAPRESENTANTE = "descripcionTipoViaPresentante";
	public static final String CODIGOTIPOVIAPRESENTANTE = "codigoTipoViaPresentante";
	public static final String DIRECCIONPRESENTANTE = "direccionPresentante";
	public static final String CODIGOPOSTALPRESENTANTE = "codigoPostalPresentante";
	public static final String CORREOELECTRONICOPRESENTANTE = "correoElectronicoPresentante";
	//public static final String INDICADORRUC = "indicadorRuc";
	public static final String OTROS = "otros";
	
	public static final String UBIGEOPN = "ubigeoPN";
	public static final String DESCRIPCIONDEPARTAMENTOPN = "descripcionDepartamentoPN";
	public static final String DESCRIPCIONPROVINCIAPN = "descripcionProvinciaPN";
	public static final String DESCRIPCIONDISTRITOPN = "descripcionDistritoPN";
	public static final String CODIGOTIPOVIAPN = "codigoTipoViaPN";
	public static final String DESCRIPCIONTIPOVIAPN = "descripcionTipoViaPN";
	public static final String DIRECCIONPN = "direccionPN";
	public static final String CODIGOPOSTALPN = "codigoPostalPN";
	public static final String CORREOELECTRONICOPN = "correoElectronicoPN";
	
	public static final String CODIGOZONAREGISTRALPJ = "codigoZonaRegistralPJ";
	public static final String CODIGOOFICINAREGISTRALPJ = "codigoOficinaRegistralPJ";
	public static final String NUMEROPARTIDAPJ = "numeroPartidaPJ";
	public static final String UBIGEOPJ = "ubigeoPJ";
	public static final String DESCRIPCIONDEPARTAMENTOPJ = "descripcionDepartamentoPJ";
	public static final String DESCRIPCIONPROVINCIAPJ = "descripcionProvinciaPJ";
	public static final String DESCRIPCIONDISTRITOPJ = "descripcionDistritoPJ";
	public static final String CODIGOTIPOVIAPJ = "codigoTipoViaPJ";
	public static final String DESCRIPCIONTIPOVIAPJ = "descripcionTipoViaPJ";
	public static final String DIRECCIONPJ = "direccionPJ";
	public static final String CODIGOPOSTALPJ = "codigoPostalPJ";
	public static final String CORREOELECTRONICOPJ = "correoElectronicoPJ";
	
	public static final String DIRECCION = "direccion"; //por reserva mercantil
	
	/***** MODIFICADO JBUGARIN 12/09/06******/	
	
	public static final String RAZONSOCIALPERSONAJURIDICA = "razonSocialPersonaJuridica";
	public static final String SIGLASPERSONAJURIDICA = "siglasPersonaJuridica";
	public static final String CODIGOTIPOSOCIEDAD = "codigoTipoSociedad";
	public static final String DESCRIPCIONTIPOSOCIEDAD = "descripcionTipoSociedad";
	public static final String CODIGOTIPOSOCIEDADANONIMA = "codigoTipoSociedadAnonima";
	public static final String DESCRIPCIONTIPOSOCIEDADANONIMA = "descripcionTipoSociedadAnonima";
	public static final String CODIGOTIPOEMPRESA = "codigoTipoEmpresa";
	public static final String DESCRIPCIONTIPOEMPRESA = "descripcionTipoEmpresa";
	
	public static final String CODIGOMONEDA = "codigoMoneda";	
	public static final String DESCRIPCIONMONEDA = "descripcionMoneda";
	public static final String MONTOCAPITAL = "montoCapital";
	public static final String VALOR = "valor";	
	public static final String CODIGOCANCELACIONCAPITAL = "codigoCancelacionCapital";
	public static final String DESCRIPCIONCANCELACIONCAPITAL = "descripcionCancelacionCapital";
	public static final String PORCENTAJECANCELADO = "porcentajeCancelado";
	
	
	public static final String ANHOTITULO = "anhoTitulo";
	public static final String NUMEROTITULO = "numeroTitulo";	


	public static final String APELLIDOPATERNOINTERESADO = "apellidoPaternoInteresado";	
	public static final String APELLIDOMATERNOINTERESADO = "apellidoMaternoInteresado";
	public static final String NOMBREINTERESADO = "nombreInteresado";
	public static final String CODIGOTIPODOCINTERESADO = "codigoTipoDocInteresado";
	public static final String DESCRIPCIONTIPODOCINTERESADO = "descripcionTipoDocInteresado";	
	public static final String NUMERODOCINTERESADO = "numeroDocInteresado";
	public static final String CORREOELECTRONICO = "correoElectronico";

	
	public static final String PARTICIPANTEPERSONANATURAL = "participantePersonaNatural";
	public static final String CODIGOTIPODOCPN = "codigoTipoDocPN";
	public static final String DESCRIPCIONTIPODOCPN = "descripcionTipoDocPN";
	public static final String NUMERODOCPN = "numeroDocPN";
	public static final String CODIGOESTADOCIVIL = "codigoEstadoCivil";
	public static final String DESCRIPCIONESTADOCIVIL = "descripcionEstadoCivil";
	public static final String APELLIDOPATERNOPN = "apellidoPaternoPN";
	public static final String APELLIDOMATERNOPN = "apellidoMaternoPN";
	public static final String NOMBREPN = "nombrePN";
	public static final String FECHANACIMIENTO = "fechaNacimiento";	
	public static final String CODIGOTIPOPARTICIPANTEPN = "codigoTipoParticipantePN";
	public static final String DESCRIPCIONTIPOPARTICIPANTEPN = "descripcionTipoParticipantePN";
	public static final String CODIGONACIONALIDADPN = "codigoNacionalidadPN";
	public static final String DESCRIPCIONNACIONALIDADPN = "descripcionNacionalidadPN";
	public static final String CODIGOCARGOOCUPACION = "codigoCargoOcupacion";
	public static final String DESCRIPCIONCARGOOCUPACION = "descripcionCargoOcupacion";
	public static final String FECHACARGO = "fechaCargo";
		
	public static final String PARTICIPANTEPERSONAJURIDICA = "participantePersonaJuridica";
	public static final String CODIGOTIPODOCPJ = "codigoTipoDocPJ";
	public static final String DESCRIPCIONTIPODOCPJ = "descripcionTipoDocPJ";
	public static final String NUMERODOCPJ = "numeroDocPJ";
	public static final String CODIGOTIPOPARTICIPANTEPJ = "codigoTipoParticipantePJ";
	public static final String DESCRIPCIONTIPOPARTICIPANTEPJ = "descripcionTipoParticipantePJ";
	public static final String CODIGONACIONALIDADPJ = "codigoNacionalidadPJ";
	public static final String DESCRIPCIONNACIONALIDADPJ = "descripcionNacionalidadPJ";
	public static final String RAZONSOCIALPJ = "razonSocialPJ";
	public static final String SIGLAS = "siglas";
	public static final String SIGLASPJ = "siglasPJ";
	
	
	public static final String INSTRUMENTOPUBLICO = "instrumentoPublico";
	public static final String SECUENCIA = "secuencia";
	public static final String CODIGOTIPOINSTRUMENTO = "codigoTipoInstrumento";
	public static final String DESCRIPCIONTIPOINSTRUMENTO = "descripcionTipoInstrumento";
	public static final String LUGAR = "lugar";
	public static final String FECHA = "fecha";


	public static final String COSTOTOTALSERVICIO = "costoTotalServicio";
	public static final String CODIGOFORMAPAGO = "codigoFormaPago";
	public static final String DESCRIPCIONFORMAPAGO = "descripcionFormaPago";
	public static final String NUMEROOPERACION = "numeroOperacion";
	public static final String FECHAPAGO = "fechaPago";
	public static final String HORAPAGO = "horaPago";
	public static final String CODIGOTIPOPAGO = "codigoTipoPago";
	public static final String DESCRIPCIONTIPOPAGO = "descripcionTipoPago";

	public static final String CODIGOZONAREGISTRALPARTIDA = "codigoZonaRegistralPartida";	
	public static final String CODIGOOFICINAREGISTRALPARTIDA = "codigoZonaRegistralPartida";	
	public static final String NUMEROPARTIDA = "numeroPartida";	
	public static final String CODIGOTIPODESISTEMA = "codigoTipoSistema";	
	public static final String DESCRIPCIONTIPODESISTEMA = "descripcionTipoSistema";	
	
	public static final String PARTIDAS = "partidas";	
	public static final String PARTIDA = "partida";	
	
	//transferencia vehicular
	public static final String VEHICULO = "vehiculo";	
	public static final String PLACA = "placa";	
	public static final String SERIE = "serie";	
	public static final String MOTOR = "motor";	
	public static final String CODIGOSUBACTO = "codigoSubActo";	
	public static final String DESCRIPCIONSUBACTO = "descripcionSubActo";	
	public static final String MONTO = "monto";	
	public static final String PAGADO = "pagado";	
	public static final String SALDO = "saldo";	
	public static final String OBSERVACIONES = "observaciones";	
	public static final String CODIGOVENDEDOR = "codigoVendedor";	
	public static final String DESCRIPCIONVENDEDOR = "descripcionVendedor";	
	public static final String CODIGOCOMPRADOR = "codigoComprador";	
	public static final String DESCRIPCIONCOMPRADOR = "descripcionComprador";	
	
	// Para el servicio de busqueda
	public static final String CRITERIOBUSQUEDA = "criterioBusqueda";	
	public static final String SOLICITUDBUSQUEDA = "solicitudBusqueda";	
	public static final String RAZONSOCIAL = "razonSocial";	
	/********************************************************************************************************/
	public static final String SOLINSCRP_PERMISO_ID = "60";
	public static final int COD_SERVICIO_SOLINSCR = 160;
	public static final int COD_GLA_SOLINSCR = 3;
	public static final int COD_GLA_BLOQUEOINMUEBLE = 1;
	public static final int COD_GLA_TRANSFERENCIAVEHICULAR = 6;
	
	public static final int COD_SERVICIO_RESERVANOMBRE = 170; //JBUGARIN 20/09/06
	public static final int COD_SERVICIO_BLOQUEOINMUEBLE = 161; //JBUGARIN 21/09/06
	public static final int COD_SERVICIO_TRANSFVEHICULAR = 162; //JBUGARIN 02/10/06
	public static final int COD_SERVICIO_BUSQUEDANACIONAL = 180; //JBUGARIN 02/10/06
	
	
	public static final String COD_SERVICIO_BUSQUEDA_X_PERSONA_JURIDICA = "180";
	public static final String COD_SERVICIO_BUSQUEDA_X_PERSONA_NATURAL = "181";
	public static final String COD_SERVICIO_BUSQUEDA_X_PROPIEDAD_INMUEBLE = "182";
	public static final String COD_SERVICIO_BUSQUEDA_X_PROPIEDAD_VEHICULAR = "183";
	/**************************************************************************************************/
	public static final String CAMPO_NU_SEQU  = "0003";
	public static final String CAMPO_ES_TITU  = "01";
	public static final String CAMPO_PU_CTRL  = "02";
	public static final String CAMPO_IN_MOST  = "N";
	
	public static final String CAMPO_IN_ESTD  = "A";
	public static final String CAMPO_NU_PART  = "N1";
	
	public static final String CAMPO_ES_PRTC  = "A";
	public static final String CAMPO_NU_PART_ASOC  = "N1";
	
	public static final int CANTIDAD_REGISTROS_POR_PAGINA = 10;
	public static final String ESPACIO = " ";
	public static final String COMA = ",";
	
	/**DESCAJ ifigueroa  09/01/2007**/
	public static final String TIPO_USR_INDIVIDUAL = "I";
	public static final String TIPO_USR_ORGANIZACION = "O";

	public static final String NOMBRE_TIPO_PAGO_CHEQUE = "Cheque";
	public static final String nOMBRE_TIPO_PAGO_EFECTIVO = "Efectivo";
	public static final String NOMBRE_TIPO_PAGO_LINEA_PREGAGO = "Linea Prepago";
	public static final String nOMBRE_TIPO_PAGO_TARJETA = "Tarjeta de credito";
	
	public static final String INDICA_SELECCIONADO = "S";
	public static final String INDICA_NO_SELECCIONADO = "N";	
	
	public static final String NOMBRE_ESTADO_SOL_DEV_REGISTRADO = "REGISTRADO";
	public static final String NOMBRE_ESTADO_SOL_DEV_CON_INFORME = "CON INFORME";
	public static final String NOMBRE_ESTADO_SOL_DEV_CON_RESOLUCION= "CON RESOLUCION";
	public static final String NOMBRE_ESTADO_SOL_DEV_PAGADO= "PAGADO";
	public static final String ESTADO_SOL_DEV_REGISTRADO = "0";
	public static final String ESTADO_SOL_DEV_CON_INFORME = "1";
	public static final String ESTADO_SOL_DEV_CON_RESOLUCION= "2";
	public static final String ESTADO_SOL_DEV_PAGADO= "3";
	
	public static final String INDICA_AFIRMACION = "SI";
	public static final String TIPO_ASIGNACION_MOVIMIENTO_NORMAL = "0";//NORMAL
	public static final String TIPO_MOVIMIENTO_ABONO = "0";	//
	public static final String ESTADO_ABONO_NORMAL = "0";	//
	public static final int COD_SERVICIO_PAGO_DEVOLUCION = 200;
	public static final String STR_BUSQUEDA_PAGO_DEVOLUCION= "Devolucion por Abono a Cuenta: ";	//
	public static final String STR_BUSQUEDA_PAGO_DEVOLUCION_EN_EFECTIVO= "Devolucion en efectivo: ";	//
	public static final String TIPO_CONSUMO_PAGO_DEVOLUCION= "A";	//
	
    /** PRUEBAS PCM JBUGARIN 09/02/2007 **/
	public static final String INDICADORRUCXML = "1";	
	/** FIN**/
	
	/** ANOTACION DE INSCRIPCION JBUGARIN 23/02/2007**/
	public static final String ACTO_ANOTACION_INSCRIPCION = "A0001";
	/** **/
	
	public static final int CONTADORMENSAJES = 20;
	public static final long TIEMPOESPERAENVIOMENSAJES = 500; //500 MILISEGUNDOS ESPERA PARA LUEGO SEGUIR ENVIANDO MENSAJES
	
//	Inicio: jascencio:29/05/2007
	//SUNARP-REGMOBCOM:se esta agregando constantes 
	public static final String CERTIFICADO_RMC = "R";
	public static final String CERTIFICADO_RMC_GRAVAMEN = "RG";
	public static final String CERTIFICADO_RMC_VIGENCIA = "RV";
    public static final String CERTIFICADO_ACTOS_VIGENTES="A";
    public static final String PERSONA_TIPO_DOCUMENTO = "T";
    public static final String CERTIFICADO_MOBILIARIO_HISTORICO="H";
    public static final String CERTIFICADO_MOBILIARIO_CONDICIONADO="C";
    public static final String CERTIFICADO_DOMINIAL_RJB="D";
    public static final String CERTIFICADO_GRAVAMEN_RJB="G";
    
    public static final String COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS="18";
    public static final String COD_CERTIFICADO_REGISTRO_VIGENCIA="19";
    public static final String COD_CERTIFICADO_REGISTRO_GRAVAMEN="20";
    
    public static final String COD_CERTIFICADO_REGISTRO_VEHICULAR_D="25";
    public static final String COD_CERTIFICADO_REGISTRO_BUQUES_D="26";
    public static final String COD_CERTIFICADO_REGISTRO_AERONAVES_D="27";
    public static final String COD_CERTIFICADO_REGISTRO_EMBARCACIONES_D="28";
    
    public static final String COD_CERTIFICADO_REGISTRO_VEHICULAR_G="29";
    public static final String COD_CERTIFICADO_REGISTRO_BUQUES_G="30";
    public static final String COD_CERTIFICADO_REGISTRO_AERONAVES_G="31";
    public static final String COD_CERTIFICADO_REGISTRO_EMBARCACIONES_G="32";
    
    public static final String COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA="21";
    public static final String COD_CERTIFICADO_REGISTRO_CREM_HISTORICO="22";
    public static final String COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO="23";
    
    public static final String COD_CERTIFICADO_COPIA_LITERAL_RMC="41";
    
    public static final String OFICINA_WEB="00|00";
    public static final String COD_CERTIFICADO_REGISTRO_RJB = "RJB";
    public static final String COD_CERTIFICADO_REGISTRO_CREM = "CREM";
    
	
	//Fin: jascencio:29/05/2007
    	
    //  Inicio:mgarate:31/05/2007
    public static final String CERTIFICADO_BUSQUEDA = "B";
    
    //  Fin:mgarate:31/05/2007
    
    //Inicio:jascencio:25/06/2007
    //para los diferentes tipos de registro---solo caso CREM condicionado
    public static final String TIPO_REGISTRO_MOBILIARIO_CONTRATOS="RMC";
    public static final String TIPO_PROPIEDAD_VEHICULAR="VEH";
    public static final String TIPO_PROPIEDAD_EMBARCACION="EMB";
    public static final String TIPO_PROPIEDAD_BUQUES="BUQ";
    public static final String TIPO_PROPIEDAD_AERONAVES="AER";
    public static final String TIPO_PERSONAS_JURIDICAS="PEJ";
    //Fin:jascencio
    
    //Inicio:jascencio:26/06/2007
    //los diferentes tipos de documentos
    public static final String TIPO_DOCUMENTO_LE="01";
    public static final String TIPO_DOCUMENTO_LM="02";
    public static final String TIPO_DOCUMENTO_CE="03";
    public static final String TIPO_DOCUMENTO_CI="04";
    //public static final String TIPO_DOCUMENTO_RUC="05";//YA ESTA ARRIBA
    public static final String TIPO_DOCUMENTO_PS="08";
    public static final String TIPO_DOCUMENTO_PS2="24";
    public static final String TIPO_DOCUMENTO_DNI="09";
    public static final String TIPO_DOCUMENTO_LEM="10";
    public static final String TIPO_DOCUMENTO_CIP="25";
    public static final String TIPO_DOCUMENTO_CM="27";
    
    public static final String TIPO_PARTICIPANTE_DEUDOR			="1";
    public static final String TIPO_PARTICIPANTE_ACREEDOR		="2";
    public static final String TIPO_PARTICIPANTE_REPRESENTANTE	="3";
    public static final String TIPO_PARTICIPANTE_OTROS			="4";
    
    //Fin:jascencio
    //Inicio:mgarate:11/07/2007

	public static final int SERVICIO_BUSQ_INDICE_PARTIDA_RMC = 33;
	
	public static final int SERVICIO_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC = 35;
	public static final int SERVICIO_WEBSERVICE_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC = 36;
	
	public static final String CONSULTA_MASIVA_CONSOLIDADO = "C";
	public static final String CONSULTA_MASIVA_DETALLADO = "D";

	public static final String NUM_TITULO_RMC = "77000000";
	/** inicio: jrosas 13-08-07***/
	public static final int SERVICIO_DETALLE_PARTIDA_RMC = 38;
	public static final int SERVICIO_WEBSERVICE_DETALLE_PARTIDA_RMC = 39;
	/** fin: jrosas 13-08-07***/
    //Fin:mgarate
	/*
	 * Inicio:jascencio:19/07/07
	 * CC:REGMOBCON-2006
	 */
	public static final String CODIGO_SISTEMA_INTEGRADO_GARANTIAS_CONTRATOS="28";
	/*
	 *Fin:jascenio 
	 */
	//Inicio:mgarate:30/08/2007
    public static final String CODIGO_LIBRO_AREA_MACIONAL_RMC="28";
    //Fin:mgarate
	public static final String GRUPO_LIBRO_AREA_BUSQUEDA_RMC = "21";
	
	/**
	 * inicio:dbravo:26/07/2007
	 * cc:regmobcon-2006
	 */
	public static final String[] SEDES_PORDEFECTO_BUSQUEDA_RMC = {"05","11","12","09","08","13","04","02","01","06","10","03","07"};
	/**
	 * fin:dbravo:26/07/2007
	 * cc:regmobcon-2006
	 */
	
	public static final String COPIA_LITERAL_RMC = "LR";
	//Inicio:mgarate:10-08-2007
	public static final String REGISTRO_VEHICULAR = "V";
	public static final String REGISTRO_BUQUES = "B";
	public static final String REGISTRO_EMBARCACIONES_PESQUERAS = "E";
	public static final String REGISTRO_AERONAVES = "A";
	//Fin:mgarate
	//Inicio: ifigueroa 13/08/20007
	// se define la fecha a partir de la cual se buscan los asientos para el certificado de vigencia en RMC
	public static final String FECHA_INICIO_VIGENCIA_RMC = "2006-05-30 00:00:00','yyyy-mm-dd HH24:MI:SS";
	//Fin: ifigueroa 13/08/2007
	
	// Inicio:jascencio:23/08/2007
	// CC: SUNARP-REGMOBCON-2006
	public static final String SERVICIO_VISUALIZA_PARTIDA_RMC="196";
	public static final String CODIGO_LIBRO_RMC="099";
	public static final String CODIGO_LIBRO_VEHICULAR="088";
	//Fin:jascencio	
	//Inicio:jascencio:23/08/2007
	//CC: SUNARP-REGMOBCON-2006
	public static final String COPIA_LITERAL_ASIENtO_RMC = "LAR";
	public static final String COD_CERTIFICADO_COPIA_LITERAL_ASIENTO_RMC="42";
	public static final String NUMERO_TITULO_SIN_PARTIDAS="00000000";
	//CC: SUNARP-REGMOBCON-2006
	
	public static final String CODIGO_LIBRO_EMBARCACION_PESQUERA = "038";
	public static final String CODIGO_LIBRO_AERONAVES = "040";
	public static final String CODIGO_LIBRO_BUQUES = "053";
	
	
	/**
	 * VARIABLES DE LA WEB SERVICES DE PCM
	 * 
	 */
	public static final String CODIGOTIPOPARTICIPANTEPJSUNAT = "codigoTipoParticipantePJSUNAT";	
	public static final String CODIGOTIPOPARTICIPANTEPNSUNAT = "codigoTipoParticipantePNSUNAT";		

	//SE ADICIONAN CONSTANTES DEL ESQUEMA DE PERSONA NATURAL	
	public static final String INDICADORREPRESENTANTEPN = "indicadorRepresentantePN";
	public static final String NOMBRECONYUGEPN = "nombreConyugePN";
	public static final String VALORPARTICIPACIONPN = "valorParticipacionPN";
	public static final String PORCENTAJEPARTICIPACIONPN = "porcentajeParticipacionPN";
	public static final String NUMEROPARTIDAEMPRESAPN = "numeroPartidaEmpresaPN";	
	public static final String DESCRIPCIONPROFESIONPN = "descripcionProfesionPN";	
			
	//SE ADICIONAN CONSTANTES DEL ESQUEMA DE PERSONA JURIDICA
	public static final String INDICADORREPRESENTANTEPJ = "indicadorRepresentantePJ";	
	
	/** DENOMINACIONES**/
	public static final String SERVICIORESERVADENOMINACION = "197";
	public static final String CODIGOTIPOPAGODENOMINACION = "01";
	public static final String DESCTIPOPAGODENOMINACION = "PRESENTACION";
	public static final String FRMPAGOUSUARIO = "03";
	public static final String DESCFRMPAGOUSUARIO = "A cuenta de saldo";
	public static final String FRMPAGOANONIMO = "04";
	public static final String DESCFRMPAGOANONIMO = "Sin pago";
	public static final String PERZAUTZPRES = "001";
	public static final String PAGOWEB = "WEB";
	
	/** DENOMINACIONES**/
}

