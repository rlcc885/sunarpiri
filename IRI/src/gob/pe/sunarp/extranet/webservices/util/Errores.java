package gob.pe.sunarp.extranet.webservices.util;

import gob.pe.sunarp.extranet.common.Errors;

public interface Errores extends Errors{
	
	public static final String WS_PARAMETRO_INCORRECTO_USUARIO 			    = "ERROR_PI18";
	public static final String WS_PARAMETRO_INCORRECTO_CLAVE 				= "ERROR_PI19";
	public static final String WS_PARAMETRO_INCORRECTO_REGISTRO_PUBLICO_ID  = "ERROR_PI02";
	public static final String WS_PARAMETRO_INCORRECTO_OFICINA_REGISTRAL_ID = "ERROR_PI01";
	public static final String WS_PARAMETRO_INCORRECTO_FICHA                = "ERROR_PI03";
	public static final String WS_PARAMETRO_INCORRECTO_NUMERO_PARTIDA		= "ERROR_PI04";
	public static final String WS_PARAMETRO_INCORRECTO_TOMO					= "ERROR_PI05";
	public static final String WS_PARAMETRO_INCORRECTO_FOLIO 				= "ERROR_PI06";
	public static final String WS_PARAMETRO_INCORRECTO_APE_PAT 				= "ERROR_PI07";
	public static final String WS_PARAMETRO_INCORRECTO_APE_MAT 				= "ERROR_PI08";
	public static final String WS_PARAMETRO_INCORRECTO_NOMBRE 				= "ERROR_PI09";
	public static final String WS_PARAMETRO_INCORRECTO_RAZON_SOCIAL			= "ERROR_PI10";
	public static final String WS_PARAMETRO_INCORRECTO_SIGLA				= "ERROR_PI11";
	public static final String WS_PARAMETRO_INCORRECTO_TIPO_DOCUMENTO		= "ERROR_PI12";
	public static final String WS_PARAMETRO_INCORRECTO_NUMERO_DOCUMENTO		= "ERROR_PI13";
	public static final String WS_PARAMETRO_INCORRECTO_NUMERO_PLACA			= "ERROR_PI14";
	public static final String WS_PARAMETRO_INCORRECTO_OTROS				= "ERROR_PI15";
	public static final String WS_PARAMETRO_INCORRECTO_NUMERO_MATRICULA		= "ERROR_PI16";
	public static final String WS_PARAMETRO_INCORRECTO_NUMERO_SERIE			= "ERROR_PI17";
	public static final String WS_PARAMETRO_NOMBRE_APEMAT_NO_INGRESADO		= "ERROR_PI20";
	public static final String WS_PARAMETRO_SIGLA_RAZON_NO_INGRESADO		= "ERROR_PI21";
	public static final String WS_PARAMETRO_PLACA_OTRO_NO_INGRESADO			= "ERROR_PI22";
	public static final String WS_PARAMETRO_INCORRECTO_CODIGO_LIBRO			= "ERROR_PI23"; //mgarate:19/11/2007
	
	public static final String WS_ACCESO_USUARIO_CLAVE_INCORRECTO           = "ERROR_A01";
	public static final String WS_ACCESO_PERFIL_INCORRECTO					= "ERROR_A02";
	public static final String WS_ACCESO_ERROR_INTEGRIDAD					= "ERROR_A03";
	public static final String WS_ACCESO_CUENTA_DESHABILITADA				= "ERROR_A04";
	public static final String WS_ACCESO_CUENTA_SESSION_ACTIVA				= "ERROR_A05";
	public static final String WS_ACCESO_RANGO_IP_NO_PERMITIDA				= "ERROR_A06";
	public static final String WS_ACCESO_NO_EXISTE_SESSION					= "ERROR_A07";
	public static final String WS_ACCESO_USUARIO_PERFIL_INCORRECTO			= "ERROR_A08";
	public static final String WS_ACCESO_USUARIO_NO_TIENE_SALDO				= "ERROR_A09";
	public static final String WS_ACCESO_FERIADO_NO_PERMITIDO				= "ERROR_A10";
	public static final String WS_ACCESO_CUENTA_NO_REGISTRADA				= "ERROR_A11";
	public static final String WS_ACCESO_PN_NO_REGISTRADA					= "ERROR_A12";
	public static final String WS_ACCESO_PJ_NO_REGISTRADA					= "ERROR_A13";
	public static final String WS_ACCESO_NO_TIENE_CONTRATO					= "ERROR_A14";
	
	public static final String WS_GENERICO_SALDO_INSUFICIENTE				= "ERROR_G03";
	public static final String WS_GENERICO_LIMITE_EXCEDIDO					= "ERROR_G02";
	public static final String WS_GENERICO_APLICACION		                = "ERROR_G01";
	
	public static final String WS_BUSQUEDA_LIMITE_RESULTADO_EXCEDIDO		= "ERROR_B01";
	public static final String WS_BUSQUEDA_NO_TIENE_RESULTADOS				= "ERROR_B02";
	
}
