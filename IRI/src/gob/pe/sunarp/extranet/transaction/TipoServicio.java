package gob.pe.sunarp.extranet.transaction;
public interface TipoServicio {
	public static final int CONSULTA_PARTIDA = 10;
	
	public static final int BUSQ_SEDE_1 = 20;
	public static final int BUSQ_SEDE_2 = 21;
	public static final int BUSQ_SEDE_3 = 22;
	public static final int BUSQ_SEDE_4= 23;
	public static final int BUSQ_SEDE_5 = 24;
	public static final int BUSQ_SEDE_6 = 25;
	public static final int BUSQ_SEDE_7 = 26;
	public static final int BUSQ_SEDE_8 = 27;
	public static final int BUSQ_SEDE_9 = 28;
	public static final int BUSQ_SEDE_10 = 29;
	public static final int BUSQ_SEDE_11 = 30;
	public static final int BUSQ_SEDE_12 = 31;
	public static final int BUSQ_SEDE_13 = 32;

	public static final int CONSULTA_TITULOS = 40;	// anadida el 07/09/2002 11:30 pm - externos  AUDCONSULTATITULO
	public static final int AFILIACION_EXTRANET = 60;//AUDAFILICIACION
	public static final int VISUALIZACION_PARTIDA = 70;//AUDVIS
	public static final int CONSULTA_TITULOS_VEHICULAR = 80;//
	public static final int CONSULTA_PLACA = 90;//
	public static final int BUSQ_PLACA = 100;//
	public static final int COPIA_LITERAL_CERTIFICADA = 110;//Creados el 08/07/2003 para Publicidad Certificada
	public static final int COPIA_LITERAL_CERTIFICADA_1 = 111;//Creados el 08/07/2003 para Publicidad Certificada
	public static final int COPIA_LITERAL_CERTIFICADA_2 = 112;//Creados el 08/07/2003 para Publicidad Certificada
	public static final int CERT_NEG_TESTAMENTO = 120;//
	public static final int CERT_NEG_SUC_INTEST = 121;//
	public static final int CERT_NEG_REGIS_PERS = 122;//
	public static final int CERT_NEG_PERS_JURID = 123;//
	public static final int CERT_NEG_PROP_INMUE = 124;//
	public static final int CERT_NEG_PROP_VEHIC = 125;//
	public static final int DELIVERY_CERT = 140;//
	
	//inicio:dbravo:08/06/07
	public static final int CERTIFICADO_RMC_CREM_ACTOS_VIGENTES = 126;
	public static final int CERTIFICADO_RMC_CREM_HISTORICO = 127;
	public static final int CERTIFICADO_RMC_CREM_CONDICIONADO = 128;
	//Fin:mgarate:19/06/2007
	
	//inicio:dbravo:10/08/2007
	public static final int CERTIFICADO_RMC_CREM_ACTOS_VIGENTES_PAGINA = 152;
	public static final int CERTIFICADO_RMC_CREM_HISTORICO_PAGINA = 153;
	public static final int CERTIFICADO_RMC_CREM_CONDICIONADO_PAGINA = 154;
	//inicio:dbravo:10/08/2007
	
	public static final int CERTIFICADO_RMC_RJB_DOMINEAL_VEHICULAR = 129;
	public static final int CERTIFICADO_RMC_RJB_DOMINEAL_BUQUES = 130;
	public static final int CERTIFICADO_RMC_RJB_DOMINEAL_AERONAVES = 135;
	public static final int CERTIFICADO_RMC_RJB_DOMINEAL_EMBARCACION_PESQUERA = 136;
	public static final int CERTIFICADO_RMC_RJB_GRAVAMEN_VEHICULAR = 137;
	public static final int CERTIFICADO_RMC_RJB_GRAVAMEN_BUQUES = 138;
	public static final int CERTIFICADO_RMC_RJB_GRAVAMEN_AERONAVES = 139;
	public static final int CERTIFICADO_RMC_RJB_GRAVAMEN_EMBARCACION_PESQUERA = 141;

	public static final int CERTIFICADO_RMC_RMC_POSITIVO_NEGATIVO = 131;
	public static final int CERTIFICADO_RMC_RMC_GRAVAMEN = 132;
	public static final int CERTIFICADO_RMC_RMC_VIGENCIA = 133;

	public static final int CERTIFICADO_RMC_BUSQUEDA_DIRECTA_VEHICULAR = 134;
	public static final int CERTIFICADO_RMC_BUSQUEDA_DIRECTA_EMBARCACION_PESQUERA = 142;
	public static final int CERTIFICADO_RMC_BUSQUEDA_DIRECTA_AERONAVES = 143;
	public static final int CERTIFICADO_RMC_BUSQUEDA_DIRECTA_BUQUES = 144;
	public static final int CERTIFICADO_RMC_BUSQUEDA_INDICE_VEHICULAR = 145;
	public static final int CERTIFICADO_RMC_BUSQUEDA_INDICE_EMBARCACION_PESQUERA = 146;
	public static final int CERTIFICADO_RMC_BUSQUEDA_INDICE_AERONAVES = 147;
	public static final int CERTIFICADO_RMC_BUSQUEDA_INDICE_BUQUES = 148;
	
	public static final int BUSQUEDA_INDICE_PARTIDA_RMC = 33;
	public static final int BUSQUEDA_WEBSERVICE_INDICE_PARTIDA_RMC = 37;
	
	public static final int BUSQUEDA_NACIONAL_INDICE_PARTIDA_SIGC = 35;
	public static final int BUSQUEDA_WEBSERVICE_NACIONAL_INDICE_PARTIDA_SIGC = 36;
	/***inicio: jrosas 13-08-07 **/
	public static final int BUSQUEDA_DIRECTA_PARTIDA_RMC = 41;
	public static final int BUSQUEDA_WEBSERVICE_DIRECTA_PARTIDA_RMC = 34;
	
	public static final int DETALLE_DIRECTA_PARTIDA_RMC = 38;
	public static final int DETALLE_WEBSERVICE_DIRECTA_PARTIDA_RMC = 39;
	
	/***fin: jrosas 13-08-07 **/
	public static final int BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19 = 188;
	//fin:dbravo:08/06/07
	/*
	 * Incio:jascencio:03/08/2007
	 * CC: REGMOBCON-2006
	 */
	public static final int CERTIFICADO_COPIA_LITERAL_RMC = 150;
	/*
	 * Fin:jascencio
	 * CC: REGMOBCON-2006
	 */
	
	public static final int SOLICITUD_INSCRIPCION_EMPRESA = 160;//AGREGADA 07/2006 GIANCARLO OCHOA
	//Inicio:jascencio:23/08/2007
	//CC: CUNARP-REGMOBCON-2006
	public static final int VISUALIZA_PARTIDA_RMC = 196;
	//Fin:jascencio
}

