package gob.pe.sunarp.extranet.common;

public interface Errors {

	public final static String ATTRIBUTE_LABEL_EVENT = "events";
	public final static String ATTRIBUTE_LABEL_ERROR_MESSAGE = "errorMessage";

	public static final String LABEL_TRACE = "TR";
	public static final String LABEL_INFORMATIONAL = "IN";
	public static final String LABEL_WARNING = "WA";
	public static final String LABEL_ERROR = "ER";
	public static final String LABEL_FATAL_ERROR = "FA";

	public static final int LEVEL_TRACE = 0;
	public static final int LEVEL_INFORMATIONAL = 1;
	public static final int LEVEL_WARNING = 2;
	public static final int LEVEL_ERROR = 3;
	public static final int LEVEL_FATAL_ERROR = 4;

	public static final String NO_ERROR = "000000";

	public static final String EC_GENERIC_ERROR = "E09999";
	public static final String EC_RESOURCES_INTEGRITY = "E09001";
	public static final String EC_CANNOT_SEND_MAIL = "E09002";
	public static final String EC_GENERIC_DB_ERROR = "E09003";
	public static final String EC_GENERIC_DB_ERROR_DATA = "E09004";
	public static final String EC_GENERIC_DB_ERROR_INTEGRIDAD = "E09005";
	public static final String EC_DB_ERROR_AUTOCOMMIT_DEBE_SER_FALSE = "E09006";

	public static final String EC_MISSING_PARAM = "E00001";
	public static final String EC_PARAM_MISSFORMED = "E00002";
	public static final String EC_NOT_EXIST_VALID_SESSION = "E00003";
	public static final String EC_SESSION_INCOMPLETE = "E00004";
	
	public static final String EC_NO_REGISTRATOR = "E50000";
	public static final String EC_USER_NOT_ALLOWED = "E50001";	
	public static final String EC_NOT_FOUND_DATA = "E50002";
	
	/**
	 * SVASQUEZ - AVATAR GLOBAL
	 * CONSTANTES DE ERROR - WEBSERVICES
	 */
	public static final int WS_EXITO = 0;
	public static final int WS_PARSER_ERROR = 1;
	public static final int WS_USER_NOT_ALLOWED = 2;
	public static final int WS_INSUFFICENT_COST = 3;
	public static final int WS_TRANSACTION_ALREADY_PROCESSED = 4;
	public static final int WS_INSUFFICENT_FOUNDS = 6;
	public static final int WS_GENERAL_ERROR = 7;
	public static final int WS_BD_EXTRANET_ERROR = 8;
	public static final int WS_BD_SIR_ERROR = 9;
	public static final int WS_TYPE_OF_PAYMENT_NOT_AVAILABLE = 10;	
	public static final int WS_DIFFERENT_HASH = 11;	
	// PROYECTO DE INTEGRACION SUNARP RENIEC
	// 07/02/2007 JOSE LLANOS
	// llamada de web service para validacion de datos
	// ------inicio---------
	public static final int WS_VALIDACION_RENIEC_ERROR = 12;	
	// ------fin---------
	
	
}