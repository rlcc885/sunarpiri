package gob.pe.sunarp.extranet.transaction;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.reportes.beans.MovimientosBean;
import gob.pe.sunarp.extranet.reportes.beans.MovimientosTotalBean;
import gob.pe.sunarp.extranet.reportes.beans.OrganizacionBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.transaction.bean.*;
import gob.pe.sunarp.extranet.util.Comodin;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
//import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
	
public class Transaction{

	private static Transaction transaction = null;
		
	public int numSessionAnterior = 0;
		
	private Transaction(){
	}

	public static Transaction getInstance(){
		if(transaction == null){
			transaction = new Transaction();
		}
		return transaction;
	}

//Metodo Principal
	public synchronized PrepagoBean registraTransaccion(TransactionBean bean, Connection conn) throws CustomException, DBException, Throwable
	{
		PrepagoBean prepagoBean = new PrepagoBean();
		/*
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try
		{
			
		conn = pool.getConnection();
		conn.setAutoCommit(false);
		*/ 
		DBConnection dconn = new DBConnection(conn);
		
		int tipo = bean.getCodigoServicio();
		
		switch (tipo)
		{
			
			case TipoServicio.CONSULTA_PARTIDA:	
					prepagoBean = registraLogAuditoriaConsultaPartida((LogAuditoriaConsultaPartidaBean) bean, dconn);
					break;

			case TipoServicio.CONSULTA_TITULOS:
					registraLogAuditoriaConsulTitulo((LogAuditoriaConsulTituloBean) bean, dconn);
					break;

			case TipoServicio.AFILIACION_EXTRANET:
					registraLogAuditoriaAfiliacion((LogAuditoriaAfiliacionBean) bean, dconn);
					break;
			case TipoServicio.CONSULTA_TITULOS_VEHICULAR:
					registraLogAuditoriaConsulTituloVehi((LogAuditoriaConsulTituloBean) bean, dconn);
					break;
						
			case TipoServicio.BUSQ_SEDE_1: 
			case TipoServicio.BUSQ_SEDE_2: 
			case TipoServicio.BUSQ_SEDE_3: 
			case TipoServicio.BUSQ_SEDE_4: 
			case TipoServicio.BUSQ_SEDE_5: 
			case TipoServicio.BUSQ_SEDE_6: 
			case TipoServicio.BUSQ_SEDE_7: 
			case TipoServicio.BUSQ_SEDE_8: 
			case TipoServicio.BUSQ_SEDE_9: 
			case TipoServicio.BUSQ_SEDE_10: 
			case TipoServicio.BUSQ_SEDE_11: 
			case TipoServicio.BUSQ_SEDE_12: 
			case TipoServicio.BUSQ_SEDE_13: 
					prepagoBean = registraLogAuditoriaBusqPartida((LogAuditoriaBusqPartidaBean) bean, dconn);
					break;
			
			case TipoServicio.VISUALIZACION_PARTIDA:
					prepagoBean = registraLogAuditoriaVisualizaPartida((LogAuditoriaVisualizaPartidaBean) bean, dconn);
					break;

			case TipoServicio.CONSULTA_PLACA:	
					
					prepagoBean = registraLogAuditoriaConsultaPlaca_Partida_Vehicular((LogAuditoriaConsultaPlacaBean) bean, dconn);
					break;
			case TipoServicio.BUSQ_PLACA:	
					LogAuditoriaConsultaPlacaBean bean1 = (LogAuditoriaConsultaPlacaBean) bean;
					if(bean1.getTipoBusq() == 1)
						prepagoBean = registraLogAuditoriaConsultaPlaca_Partida_Vehicular(bean1, dconn);
					else
						prepagoBean = registraLogAuditoriaBusqPlacaVehiNomRaz(bean1, dconn);
					break;
			case TipoServicio.COPIA_LITERAL_CERTIFICADA:
			case TipoServicio.COPIA_LITERAL_CERTIFICADA_1:
			case TipoServicio.COPIA_LITERAL_CERTIFICADA_2:
			case TipoServicio.CERT_NEG_TESTAMENTO:
			case TipoServicio.CERT_NEG_SUC_INTEST:
			case TipoServicio.CERT_NEG_REGIS_PERS:
			case TipoServicio.CERT_NEG_PERS_JURID:
			case TipoServicio.CERT_NEG_PROP_INMUE:
			case TipoServicio.CERT_NEG_PROP_VEHIC:
			case TipoServicio.DELIVERY_CERT:
				registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);

                break;

			//Inicio:dbravo:08/06/2007	
			case TipoServicio.CERTIFICADO_RMC_CREM_ACTOS_VIGENTES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_CREM_HISTORICO:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_CREM_CONDICIONADO:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_VEHICULAR:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_BUQUES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_AERONAVES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_EMBARCACION_PESQUERA:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;		
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_VEHICULAR:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_BUQUES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_AERONAVES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_EMBARCACION_PESQUERA:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_RMC_POSITIVO_NEGATIVO:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_RMC_GRAVAMEN:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;		
			case TipoServicio.CERTIFICADO_RMC_RMC_VIGENCIA:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			//Inicio:mgarate:19/06/2007	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_VEHICULAR:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_EMBARCACION_PESQUERA:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_AERONAVES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_BUQUES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_VEHICULAR:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_EMBARCACION_PESQUERA:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_AERONAVES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_BUQUES:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			//Fin:mgarate:19/06/2007
			//Fin:dbravo:08/06/2007
					
			/********************** AGREGADO GIANCARLO OCHOA 07/2006 ********************/
			/** Inicio:mgarate:20/11/2007 - PCM
			 *  Caso que sea constitución de empresa
			 *   
			**/
			case TipoServicio.SOLICITUD_INSCRIPCION_EMPRESA:
					registraLogAuditoriaSolicitudInscripcion((LogAuditoriaSolicitudInscripcionBean)bean, dconn);
					break;
			//Fin:mgarate - PCM
			
			case TipoServicio.BUSQUEDA_INDICE_PARTIDA_RMC:
				prepagoBean = registraLogAuditoriaBusqPartida((LogAuditoriaBusqPartidaBean) bean, dconn);
				break;
			case TipoServicio.BUSQUEDA_WEBSERVICE_INDICE_PARTIDA_RMC:
				prepagoBean = registraLogAuditoriaBusqPartida((LogAuditoriaBusqPartidaBean) bean, dconn);
				break;
					
			/**
			 * inicio:dbravo:27/07/2007
			 * cc:regmobcon-2006
			 */		
			case TipoServicio.BUSQUEDA_NACIONAL_INDICE_PARTIDA_SIGC:
				prepagoBean = registraLogAuditoriaBusqPartida((LogAuditoriaBusqPartidaBean) bean, dconn);
				break;
			case TipoServicio.BUSQUEDA_WEBSERVICE_NACIONAL_INDICE_PARTIDA_SIGC:
				prepagoBean = registraLogAuditoriaBusqPartida((LogAuditoriaBusqPartidaBean) bean, dconn);
				break;
			
			case TipoServicio.BUSQUEDA_DIRECTA_PARTIDA_RMC:	
				prepagoBean = registraLogAuditoriaConsultaPartida((LogAuditoriaConsultaPartidaBean) bean, dconn);
				break;	
				
			case TipoServicio.BUSQUEDA_WEBSERVICE_DIRECTA_PARTIDA_RMC:
				prepagoBean = registraLogAuditoriaConsultaPartida((LogAuditoriaConsultaPartidaBean) bean, dconn);
				break;
				
			case TipoServicio.DETALLE_DIRECTA_PARTIDA_RMC:
				prepagoBean = registraLogAuditoriaConsultaPartida((LogAuditoriaConsultaPartidaBean) bean, dconn);
				break;
				
			case TipoServicio.DETALLE_WEBSERVICE_DIRECTA_PARTIDA_RMC:
				prepagoBean = registraLogAuditoriaConsultaPartida((LogAuditoriaConsultaPartidaBean) bean, dconn);
				break;
				
			case TipoServicio.BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19:
				prepagoBean = registraLogAuditoriaBusqMasivaRelacional((LogAuditoriaBusquedaMasivaRelacional) bean, dconn);
				break;	
				
			case TipoServicio.CERTIFICADO_RMC_CREM_ACTOS_VIGENTES_PAGINA:
				prepagoBean = registraLogAuditoriaVerificaCrem((LogAuditoriaVerificaCremBean) bean, dconn);
				break;		
			case TipoServicio.CERTIFICADO_RMC_CREM_CONDICIONADO_PAGINA:
				prepagoBean = registraLogAuditoriaVerificaCrem((LogAuditoriaVerificaCremBean) bean, dconn);
				break;
			case TipoServicio.CERTIFICADO_RMC_CREM_HISTORICO_PAGINA:	
				prepagoBean = registraLogAuditoriaVerificaCrem((LogAuditoriaVerificaCremBean) bean, dconn);
				break;
			/**
			 * fin:dbravo:27/07/2007
			 * cc:regmobcon-2006
			 */	
			/*
			 * Inicio:jascencio:03/08/2007
			 * CC:REGMOBCON-2006
			 */
			case TipoServicio.CERTIFICADO_COPIA_LITERAL_RMC:
				prepagoBean=registraLogAuditoriaPubliCerti((LogAuditoriaCertificadoBean)bean, dconn);
				break;
			/*
			 * Fin:jascencio
			 * CC:REGMOBCON-2006
			 */
			//Inicio:jascencio:23/08/2007
			//CC: SUNARP-REGMOBCON-2006
			case TipoServicio.VISUALIZA_PARTIDA_RMC:
				prepagoBean = registraLogAuditoriaVisualizaPartida((LogAuditoriaVisualizaPartidaBean) bean, dconn);
				break;	
			//Fin:jascencio
		}
				
		return prepagoBean;
	}
	/**
	 * @param bean
	 * @param myConn
	 * @return
	 * @throws CustomException
	 * @throws DBException
	 * @throws Throwable
	 */
	//Auditoria Busqueda Partida
	private PrepagoBean registraLogAuditoriaBusqPartida (LogAuditoriaBusqPartidaBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
		
		PrepagoBean prepagoBean = new PrepagoBean();
		String[] sedes = bean.getNumSedes();
		
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer("BUSQUEDA DE PARTIDA: ");
		
		if(sedes.length <= 0)
			throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar al menos una sede", "errorPrepago");
		else if(sedes.length == 13)
			strBusq.append(" TODAS LAS SEDES.");
		else{
			strBusq.append(sedes.length).append(" SEDES: ").append(nombreSede(sedes[0], myConn));
			
			for(int i = 1; i < sedes.length; i++)
				strBusq.append(nombreSede(sedes[i], myConn));
		}
					
		if(bean.getPartSeleccionado().equalsIgnoreCase("PN")){
			
			strBusq.append(" TIPO PARTICIPANTE PN - PARTICIPANTE: ");
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("PJ")){
		
			strBusq.append(" TIPO PARTICIPANTE PJ - PARTICIPANTE: ");
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("NM")){
		
			strBusq.append(" NUMERO MOTOR: ");
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("NS")){
		
			strBusq.append(" NUMERO SERIE: ");
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("BI-PLACA")){
		
			strBusq.append(" PLACA: ");
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("BI-OTROS")){
		
			strBusq.append(" OTROS: ");
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("BI-PLACA-OTROS")){
		
			strBusq.append(" PLACA, OTROS: ");
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("BI")){
			strBusq.append("");
			if(bean.getNumeroPlaca()!=null && bean.getNumeroPlaca().trim().length()>0){
				strBusq.append("PLACA: ");
			}
			if(bean.getNumeroMatricula()!=null && bean.getNumeroMatricula().trim().length()>0){
				if(strBusq.length()>0){
					strBusq.append(", ");
				}
				strBusq.append("MATRICULA: ");
			}
			if(bean.getNombreBien()!=null && bean.getNombreBien().trim().length()>0){
				if(strBusq.length()>0){
					strBusq.append(", ");
				}
				strBusq.append("NOMBRE: ");
			}
			if(bean.getNumeroSerie()!=null && bean.getNumeroSerie().trim().length()>0){
				if(strBusq.length()>0){
					strBusq.append(", ");
				}
				strBusq.append("SERIE: ");
			}
			
		}else if (bean.getPartSeleccionado().equalsIgnoreCase("TND")){
			
			strBusq.append(" DOCUMENTO: ");
			
		}else{ 
		
			strBusq.append(" : ");
			
		}
		strBusq.append(bean.getNomApeRazSocPart());

		prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);

		DboAudBusqPartida tablaAudit = new DboAudBusqPartida(myConn);
		
		//Inicio mgarate secuenciales por base de datos problema de error generico de base de datos de la aplicacion
		tablaAudit.setField(DboAudBusqPartida.CAMPO_AUD_BUSQ_PARTIDA_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDAud_Busq_Partida(myConn))));
		//Fin mgarate
		tablaAudit.setField(DboAudBusqPartida.CAMPO_COD_AREA_REG, bean.getCodAreaReg());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_NOMAPE_RAZSOC_PART, bean.getNomApeRazSocPart());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_NUM_SEDES, sedes.length);
		tablaAudit.setField(DboAudBusqPartida.CAMPO_TIPO_PARTICIPACION, bean.getTipoParticipacion());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_TIPO_PERS_PART, bean.getTipoPersPart());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_TRANS_ID, Long.toString(prepagoBean.getTransacId()));
		tablaAudit.add();
		
		String busqPartId = tablaAudit.getField(DboAudBusqPartida.CAMPO_AUD_BUSQ_PARTIDA_ID);
		for(int j = 0; j < sedes.length; j++)
			registraLogAuditoriaBusqMultSedes(busqPartId, sedes[j], myConn);

		int tipo = bean.getTipoBusqPartida();
		
        if (tipo != -1){ // no entra para el caso de busqueda de indice
			LogAuditoriaBuscaPartidaRegAereoBean beanA = null;
			LogAuditoriaBuscaPartidaRegEmb beanE = null;
			LogAuditoriaBuscaPartidaRegMinBean beanM = null;
			LogAuditoriaBuscaPartidaRegPrediosBean beanP = null;
			LogAuditoriaBusqRazSocPJBean beanRSPJ = null;
			
			switch(tipo){
				case Constantes.REG_AEREO:
					/* Formando el String de Búsqueda */
						beanA = (LogAuditoriaBuscaPartidaRegAereoBean) bean;
		
						strBusq.append(". REGISTRO DE AERONAVES - ");
						
						if(beanA.getTipoParam().equalsIgnoreCase("T")){
							strBusq.append(" POR TITULAR ");
							
							if(beanA.getTipoTitular().equalsIgnoreCase("N"))
								strBusq.append(" PERSONA NATURAL");
							else if(beanA.getTipoTitular().equalsIgnoreCase("J"))
								strBusq.append(" PERSONA JURIDICA");
						}else if(beanA.getTipoParam().equalsIgnoreCase("M"))
							strBusq.append(" REGISTRO POR MATRICULA");
							
						strBusq.append(beanA.getValor());
					/* Fin de String de Búsqueda */
					break;
				case Constantes.REG_EMB:
					/* Formando el String de Búsqueda */
						beanE = (LogAuditoriaBuscaPartidaRegEmb) bean;
						
						if(beanE.getTipoEmb().equalsIgnoreCase("P")){
							strBusq.append(" .REGISTRO EMBARCACIONES PESQUERAS - ");
							
							if(beanE.getTipoParam().equalsIgnoreCase(" M"))
								strBusq.append(" Num Matricula:").append(beanE.getValor());
							else
								strBusq.append(" Nombre:").append(beanE.getValor());
						}else if(beanE.getTipoEmb().equalsIgnoreCase(" B")){
							strBusq.append(" REGISTRO EMBARCACIONES BUQUES - ");
							if(beanE.getTipoParam().equalsIgnoreCase(" M"))
								strBusq.append(" Num Matricula:").append(beanE.getValor());
							else
								strBusq.append(" Nombre:").append(beanE.getValor());
						}
					/* Fin de String de Búsqueda */
					break;
				case Constantes.REG_MINERO:
					/* Formando el String de Búsqueda */
						beanM = (LogAuditoriaBuscaPartidaRegMinBean) bean;
						
						strBusq.append(" .REGISTRO DE MINERIAS - ");
						if(beanM.getTipoParam().equalsIgnoreCase("D")){
							strBusq.append(" POR DERECHO MINERO ");
						}else if(beanM.getTipoParam().equalsIgnoreCase("R"))
							strBusq.append(" POR NOMBRE SOCIAL");
							
						strBusq.append(beanM.getValor());
					/* Fin de String de Búsqueda */
					break;
				case Constantes.REG_PREDIO:
					/* Formando el String de Búsqueda */
						beanP = (LogAuditoriaBuscaPartidaRegPrediosBean) bean;
	
						strBusq.append(" .REGISTRO PREDIOS - ").append("Dpto:").append(beanP.getDpto());
						strBusq.append(" Prov:").append(beanP.getProv()).append(" Dist:").append(beanP.getDist());
						strBusq.append(" TpoZona:").append(beanP.getTpoZona()).append(" Nombre:").append(beanP.getNomZona());
						strBusq.append(" TpoVia:").append(beanP.getTpoVia()).append(" Nombre:").append(beanP.getNomVia());
						strBusq.append(" TpoNum:").append(beanP.getTpoNum()).append(" Numero:").append(beanP.getNumInmb());
						strBusq.append(" tpoInt:").append(beanP.getTpoInt()).append(" Numero:").append(beanP.getNumInt());
					/* Fin de String de Búsqueda */
					break;
				case Constantes.REG_RAZ_SOC_PJ:
					/* Formando el String de Búsqueda */
						beanRSPJ = (LogAuditoriaBusqRazSocPJBean) bean;				
						strBusq.append(" .REGISTRO RAZ SOC PJ - ").append(beanRSPJ.getRazSocPJ());				
					/* Fin de String de Búsqueda */
					break;
				case Constantes.REG_VEH_MOTOR:
					/* Formando el String de Búsqueda */
						//beanRSPJ = (LogAuditoriaBusqRazSocPJBean) bean;
					
						//strBusq.append(bean.getNomApeRazSocPart());
					/* Fin de String de Búsqueda */
					prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);
					return prepagoBean;
					//break;
				case Constantes.REG_VEH_SERIE:
					/* Formando el String de Búsqueda */
						//beanRSPJ = (LogAuditoriaBusqRazSocPJBean) bean;
					
						//strBusq.append(bean.getNomApeRazSocPart());
					/* Fin de String de Búsqueda */
					prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);
					return prepagoBean;
					//break;		
					}
			
			switch(tipo){
				case Constantes.REG_AEREO:
					registraLogAuditoriaBuscaPartidaRegAereo(beanA, busqPartId, myConn);
					break;
				
				case Constantes.REG_EMB:
					registraLogAuditoriaBuscaPartidaRegEmb(beanE, busqPartId, myConn);
					break;
				
				case Constantes.REG_MINERO:
					registraLogAuditoriaBuscaPartidaRegMin (beanM, busqPartId, myConn);
					break;
				
				case Constantes.REG_PREDIO:
					registraLogAuditoriaBuscaPartidaRegPredios (beanP, busqPartId, myConn);
					break;
				
				case Constantes.REG_RAZ_SOC_PJ:
					registraLogAuditoriaBusqRaz_Soc_PJ(beanRSPJ, busqPartId, myConn);
					break;
					
				case Constantes.REG_VEH_MOTOR:
					registraLogAuditoriaBusqRaz_Soc_PJ(beanRSPJ, busqPartId, myConn);
					break;
	
				case Constantes.REG_VEH_SERIE:
					registraLogAuditoriaBusqRaz_Soc_PJ(beanRSPJ, busqPartId, myConn);
					break;
				
				
			}
        }	

		
		return prepagoBean;
	}

//Auditoria Busqueda Placa por nom o razSoc
	private PrepagoBean registraLogAuditoriaBusqPlacaVehiNomRaz (LogAuditoriaConsultaPlacaBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
		//String[] sedes = bean.getNumSedes();
		
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer();
		if(bean.getTipoBusq() == 2)
			strBusq.append("BUSQUEDA DE PLACA POR NOMBRES: ");
		else
			strBusq.append("BUSQUEDA DE PLACA POR RAZON SOCIAL: ");
		/*
		if(sedes.length <= 0)
			throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar al menos una sede", "errorPrepago");
		else if(sedes.length == 13)
			strBusq.append(" TODAS LAS SEDES.");
		else{
			strBusq.append(sedes.length).append(" SEDES: ").append(nombreSede(sedes[0], myConn));
			
			for(int i = 1; i < sedes.length; i++)
				strBusq.append(nombreSede(sedes[i], myConn));
		}
		*/
		strBusq.append(" Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		if(bean.getTipoBusq() == 2)
			strBusq.append(" TIPO PARTICIPANTE PN - PARTICIPANTE: ");
		else
			strBusq.append(" TIPO PARTICIPANTE PJ - PARTICIPANTE: ");
		
		strBusq.append(bean.getParamBusqueda());
		
		
		PrepagoBean prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);
		
		DboAudBusqPartida tablaAudit = new DboAudBusqPartida(myConn);
		
		//Inicio mgarate secuenciales por base de datos problema de error generico de base de datos de la aplicacion
		tablaAudit.setField(DboAudBusqPartida.CAMPO_AUD_BUSQ_PARTIDA_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDAud_Busq_Partida(myConn))));
		//Fin mgarate
		tablaAudit.setField(DboAudBusqPartida.CAMPO_COD_AREA_REG, bean.getCodAreaReg());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_NOMAPE_RAZSOC_PART, bean.getParamBusqueda());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_NUM_SEDES, bean.getNumSedes());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_TIPO_PARTICIPACION, bean.getTipoParticipacion());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_TIPO_PERS_PART, bean.getTipoPersPart());
		tablaAudit.setField(DboAudBusqPartida.CAMPO_TRANS_ID, Long.toString(prepagoBean.getTransacId()));
		tablaAudit.add();
		String busqPartId = tablaAudit.getField(DboAudBusqPartida.CAMPO_AUD_BUSQ_PARTIDA_ID);
		
		return prepagoBean;
	}

//Auditoria Visualiza Partida
	private PrepagoBean registraLogAuditoriaVisualizaPartida (LogAuditoriaVisualizaPartidaBean bean, DBConnection myConn) throws CustomException, DBException, Throwable
	{
	//Se prepara el String de Búsqueda
		
		StringBuffer strBusq = new StringBuffer();
		//Inicio:jascencio:23/08/2007
		//CC: SUNARP-REGMOBCON-2006
		if(bean.getCodigoServicio() == TipoServicio.VISUALIZA_PARTIDA_RMC){
			strBusq.append("VISUALIZACION DE PARTIDA RMC-");
		}else{
			strBusq.append("VISUALIZACION DE PARTIDA-");
		}
		//Fin:jascencio
		
		strBusq.append("Num Partida: ").append(bean.getNumPartida()).append(" Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		
		//cjvc77 20021218
			strBusq.append(" ").append(bean.getNumDocViasualiz());		
		
/*		if(Integer.parseInt(bean.getTipoImgVisualiz()) == 1)
			strBusq.append(" Asiento ").append(bean.getNumDocViasualiz());
		
		if(Integer.parseInt(bean.getTipoImgVisualiz()) == 2)
			strBusq.append(" Ficha ").append(bean.getNumDocViasualiz());

		if(Integer.parseInt(bean.getTipoImgVisualiz()) == 3){
			StringTokenizer t = new StringTokenizer(bean.getNumDocViasualiz(), "|");
			strBusq.append(" Tomo ").append(t.nextToken()).append(" Folio ").append(t.nextToken());
			
		}
*/
		PrepagoBean prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);

		DboAudVisualizPartida tablaAudit = new DboAudVisualizPartida();
		tablaAudit.setConnection(myConn);
		//Inicio mgarate error generico de base de datos de la aplicacion , ahora las secuencias se manejan por base de datos
		tablaAudit.setField(DboAudVisualizPartida.CAMPO_AUD_VISUALIZ_PARTIDA_ID,Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDAud_Visua_Partida(myConn))));
		//Fin mgarate
		tablaAudit.setField(DboAudVisualizPartida.CAMPO_NUMERO_DOC_VISUALIZ, bean.getNumDocViasualiz());
		tablaAudit.setField(DboAudVisualizPartida.CAMPO_NUM_PARTIDA, bean.getNumPartida());
		tablaAudit.setField(DboAudVisualizPartida.CAMPO_OFIC_REG_ID, bean.getOficRegId());
		tablaAudit.setField(DboAudVisualizPartida.CAMPO_REG_PUB_ID, bean.getRegPubId());
		tablaAudit.setField(DboAudVisualizPartida.CAMPO_TIPO_IMG_VISUALIZ, bean.getTipoImgVisualiz());
		tablaAudit.setField(DboAudVisualizPartida.CAMPO_TRANS_ID, Long.toString(prepagoBean.getTransacId()));
		try{
		tablaAudit.add();
		}catch(Throwable t)
		{
			t.printStackTrace();
		}
		
		return prepagoBean;
	}

//Auditoria REG EMB
	private void registraLogAuditoriaBuscaPartidaRegEmb (LogAuditoriaBuscaPartidaRegEmb bean, String busqPartId, DBConnection myConn) throws CustomException, DBException, Throwable{
			DboAudBusqRegEmb tablaAudit = new DboAudBusqRegEmb();
			tablaAudit.setConnection(myConn);
			
			tablaAudit.setField(DboAudBusqRegEmb.CAMPO_TIPO_PARAM, bean.getTipoParam());
			tablaAudit.setField(DboAudBusqRegEmb.CAMPO_VALOR, bean.getValor());
			tablaAudit.setField(DboAudBusqRegEmb.CAMPO_AUD_BUSQ_PARTIDA_ID, busqPartId);
			tablaAudit.setField(DboAudBusqRegEmb.CAMPO_TIPO_EMB, bean.getTipoEmb());
			tablaAudit.add();
	}

//Auditoria REG MIN
	private void registraLogAuditoriaBuscaPartidaRegMin (LogAuditoriaBuscaPartidaRegMinBean bean, String busqPartId, DBConnection myConn) throws CustomException, DBException, Throwable{
			DboAudBusqRegMin tablaAudit = new DboAudBusqRegMin();
			tablaAudit.setConnection(myConn);
			
			tablaAudit.setField(DboAudBusqRegMin.CAMPO_TIPO_PARAM, bean.getTipoParam());
			tablaAudit.setField(DboAudBusqRegMin.CAMPO_VALOR, bean.getValor());
			tablaAudit.setField(DboAudBusqRegMin.CAMPO_AUD_BUSQ_PARTIDA_ID, busqPartId);
			tablaAudit.add();
	}

//Auditoria RAZ SOC PJ
	private void registraLogAuditoriaBusqRaz_Soc_PJ (LogAuditoriaBusqRazSocPJBean bean, String busqPartId, DBConnection myConn) throws CustomException, DBException, Throwable{
			DboAudBusqRazSocPj tablaAudit = new DboAudBusqRazSocPj();
			tablaAudit.setConnection(myConn);
			
			tablaAudit.setField(DboAudBusqRazSocPj.CAMPO_AUD_BUSQ_PARTIDA_ID, busqPartId);
			tablaAudit.setField(DboAudBusqRazSocPj.CAMPO_RAZ_SOC_PJ, bean.getRazSocPJ());
			tablaAudit.add();
	}

//Auditoria REG PREDIOS
	private void registraLogAuditoriaBuscaPartidaRegPredios (LogAuditoriaBuscaPartidaRegPrediosBean bean, String busqPartId, DBConnection myConn) throws CustomException, DBException, Throwable{
			DboAudBusqRegPredios tablaAudit = new DboAudBusqRegPredios();
			tablaAudit.setConnection(myConn);
			
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_AUD_BUSQ_PARTIDA_ID, busqPartId);
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_PROV_ID, bean.getProv());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_PAIS_ID, bean.getPais());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_DPTO_ID, bean.getDpto());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_NOMBRE_VIA, bean.getNomVia());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_DIST_ID, bean.getDist());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_NOMBRE_ZONA, bean.getNomZona());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_TIPO_INTER, bean.getTpoInt());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_TIPO_NUMER, bean.getTpoNum());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_TIPO_VIA, bean.getTpoVia());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_NUM_INMB, bean.getNumInmb());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_NUM_INTERIOR, bean.getNumInt());
			tablaAudit.setField(DboAudBusqRegPredios.CAMPO_TIPO_ZONA, bean.getTpoZona());
			tablaAudit.add();
	}

//Auditoria REG AEREO
	private void registraLogAuditoriaBuscaPartidaRegAereo (LogAuditoriaBuscaPartidaRegAereoBean bean, String busqPartId, DBConnection myConn) throws CustomException, DBException, Throwable{
		DboAudBusqRegAero tablaAudit = new DboAudBusqRegAero();
		tablaAudit.setConnection(myConn);
			
		tablaAudit.setField(DboAudBusqRegAero.CAMPO_TIPO_PARAM, bean.getTipoParam());
		tablaAudit.setField(DboAudBusqRegAero.CAMPO_VALOR, bean.getValor());
		tablaAudit.setField(DboAudBusqRegAero.CAMPO_TIPO_TITULAR, bean.getTipoTitular());
		tablaAudit.setField(DboAudBusqRegAero.CAMPO_AUD_BUSQ_PARTIDA_ID, busqPartId);
		tablaAudit.add();
	}

//Auditoria: Múltiple Sedes
	private void registraLogAuditoriaBusqMultSedes(String busqPartId, String codSede, DBConnection myConn) throws CustomException, DBException, Throwable{
			DboAudBusqMultSedes tablaAudit = new DboAudBusqMultSedes();
			tablaAudit.setConnection(myConn);
			
			tablaAudit.setField(DboAudBusqMultSedes.CAMPO_AUD_BUSQ_PARTIDA_ID, busqPartId);
			tablaAudit.setField(DboAudBusqMultSedes.CAMPO_COD_SEDE, codSede);
			tablaAudit.setField(DboAudBusqMultSedes.CAMPO_TIPO_DOC_BUSQ, "1");
			tablaAudit.add();
	}

//Auditoria: Afiliciacion
	private void registraLogAuditoriaAfiliacion (LogAuditoriaAfiliacionBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer("AFILIACION: ").append(bean.getUserId());
		long idTran = grabaTransaccion(strBusq.toString(), (TransactionBean) bean, myConn);
		
		DboAudAfiliacion tablaAudit = new DboAudAfiliacion();
		tablaAudit.setConnection(myConn);
			
		tablaAudit.setField(DboAudAfiliacion.CAMPO_FG_WEB, bean.getFgWeb());
		tablaAudit.setField(DboAudAfiliacion.CAMPO_OFIC_REG_ID, bean.getOficRegId());//puede ser null
		tablaAudit.setField(DboAudAfiliacion.CAMPO_REG_PUB_ID, bean.getRegPubId());// puede ser null
		tablaAudit.setField(DboAudAfiliacion.CAMPO_TIPO_AFIL, bean.getTipoAfil());
		tablaAudit.setField(DboAudAfiliacion.CAMPO_NUM_CONT, bean.getNumCont());
		tablaAudit.setField(DboAudAfiliacion.CAMPO_TRANS_ID, Long.toString((long) idTran));
		tablaAudit.setField(DboAudAfiliacion.CAMPO_PERSONA_ID, bean.getPersonaId());
		tablaAudit.add();
	}

//Auditoria: Consulta Titulo
	private void registraLogAuditoriaConsulTitulo (LogAuditoriaConsulTituloBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer("CONSULTA DE TITULO: ");
		strBusq.append(bean.getNumTitulo()).append(" ANO: ").append(bean.getAnoTitulo()).append(" Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		long idTran = grabaTransaccion(strBusq.toString(), (TransactionBean) bean, myConn);

		DboAudConsultaTitulo tablaAudit = new DboAudConsultaTitulo();
		tablaAudit.setConnection(myConn);
			
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_AA_TITULO, bean.getAnoTitulo());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_NUM_TITULO, bean.getNumTitulo());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_OFIC_REG_ID, bean.getOficRegId());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_REG_PUB_ID, bean.getRegPubId());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_TRANS_ID, Long.toString(idTran));
		tablaAudit.add();
	}

//Auditoria: Consulta Partida
	private PrepagoBean registraLogAuditoriaConsultaPartida (LogAuditoriaConsultaPartidaBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer("CONSULTA DE PARTIDA-");
		
		strBusq.append("Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		
		if(bean.getTipoConsPartida().equals("1"))
						strBusq.append(" NUMERO DE PARTIDA ").append(bean.getNumPartFic());

		if(bean.getTipoConsPartida().equals("2"))
						strBusq.append(" NUMERO DE FICHA ").append(bean.getNumPartFic());

		if(bean.getTipoConsPartida().equals("3")){
			StringTokenizer stk = new StringTokenizer(bean.getLibTomFol(), "|");
			String aux[] = new String[3];
			int i = 0;
				  
			while (stk.hasMoreTokens()) {
				aux[i++] = stk.nextToken();
			}
			
			strBusq.append(" LIBRO ").append(aux[0]).append(" TOMO: ").append(aux[1]).append(" FOLIO: ").append(aux[2]);
		}
		//hp
		if(bean.getTipoConsPartida().equals("4"))
						strBusq.append(" NUMERO DE PLACA ").append(bean.getNumPartFic());
						
		PrepagoBean prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);
		
		DboAudConsultaPartida tablaAudit = new DboAudConsultaPartida(myConn);
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_LIB_TOM_FOL, bean.getLibTomFol());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_NUM_PART_FICHA, bean.getNumPartFic());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_OFIC_REG_ID, bean.getOficRegId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_REG_PUB_ID, bean.getRegPubId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TIPO_BUSQ, bean.getTipoBusq());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TRANS_ID, Long.toString(prepagoBean.getTransacId()));
		tablaAudit.add();
		
		return prepagoBean;
	}
	
//Auditoria: Consulta Vehicular Placa - Partida
	private PrepagoBean registraLogAuditoriaConsultaPlaca_Partida_Vehicular (LogAuditoriaConsultaPlacaBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
	//Se prepara el String de Búsqueda
		
		StringBuffer strBusq = new StringBuffer();
		
		if(bean.getTipoBusq() == 0)
			strBusq.append("CONSULTA VEHICULAR POR PLACA").append(" Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn)).append(" NUMERO DE PLACA ").append(bean.getParamBusqueda());
		else
			strBusq.append("CONSULTA VEHICULAR POR PARTIDA").append(" Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn)).append(" NUMERO DE PARTIDA ").append(bean.getParamBusqueda());
		
		PrepagoBean prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);
		
		DboAudConsultaPartida tablaAudit = new DboAudConsultaPartida(myConn);
		//tablaAudit.setField(DboAudConsultaPartida.CAMPO_LIB_TOM_FOL, "");
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_NUM_PART_FICHA, bean.getParamBusqueda());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_OFIC_REG_ID, bean.getOficRegId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_REG_PUB_ID, bean.getRegPubId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TIPO_BUSQ, bean.getTipoParticipacion());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TRANS_ID, Long.toString(prepagoBean.getTransacId()));
		tablaAudit.add();
		
		return prepagoBean;
	}	
	
	private long grabaTransaccion(String descripcion, TransactionBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
		PrepagoBean prepagoBean = grabaTransaccionPrepago(descripcion, bean, myConn);
		
		return prepagoBean.getTransacId();
	}
	
	/**
	 * @autor Daniel Bravo
	 * @param descripcion
	 * @param bean
	 * @param myConn
	 * @return
	 * @throws CustomException
	 * @throws DBException
	 * @throws Throwable
	 */
	private PrepagoBean grabaTransaccionPrepago(String descripcion, TransactionBean bean, DBConnection myConn) throws CustomException, DBException, Throwable{
		
		UsuarioBean usuarioSession = bean.getUsuarioSession();
		double costo_servicio = 0.0;
		/**
		 * inicio:dbravo:13/08/2007
		 * descripcion: Este booleano se activa cuando el tipo de certificado es crem, y permite realizar el pago diferenciado, 
		 * 				considerar que un verficador se va encargar de descontar la linea de credito del solicitante.
		 */
		boolean flagSegundoPagoCertificadoCREM = false;
		boolean flagTransaccion = false;
		/**
		 * fin:dbravo:13/08/2007
		 */
		
		/******************** MODIFICADO POR GIANCARLO OCHOA V. 20/07/2006 *********************/
		//if((bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140 && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)||
		   //(usuarioSession.getFgInterno()==false && 
		   //usuarioSession.getExonPago()==false))
		System.out.println("grabaTransaccion....");
		
		if((bean.getCodigoServicio()==TipoServicio.BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19) 
			&& (usuarioSession.getFgInterno()==false && usuarioSession.getExonPago()==false)){

			/**
			 * @autor Daniel L. Bravo Falcón
			 * @descripcion Se encarga de calcular el costo de la busqueda de publisidad masiva relacional
			 */
			flagTransaccion = true;
			PrepagoBean prepagoBean = calcularPrecioMasivo(bean.getCodigoServicio(), bean.getCodigoGLA(), bean.getCantidadRegistros(), myConn);
			int codServicio = prepagoBean.getCodigoServicio();
			costo_servicio  = prepagoBean.getMontoBruto(); 
			
			bean.setCodigoServicio(codServicio);
			
		}else if (bean.getCodigoServicio()==TipoServicio.BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19)
		{
			flagTransaccion = true;
		}
		else if(bean.getCodigoServicio()==TipoServicio.CERTIFICADO_RMC_CREM_ACTOS_VIGENTES ||
				 bean.getCodigoServicio()==TipoServicio.CERTIFICADO_RMC_CREM_CONDICIONADO ||
				 bean.getCodigoServicio()==TipoServicio.CERTIFICADO_RMC_CREM_HISTORICO 
				){
			/**
			 * @autor Daniel L. Bravo Falcón
			 * @descripcion Se encarga de calcular el costo inicial para CREM.
			 */
			costo_servicio = costoTarifa(bean, myConn);
			
		}else if(bean.getCodigoServicio()==TipoServicio.CERTIFICADO_RMC_CREM_ACTOS_VIGENTES_PAGINA ||
				 bean.getCodigoServicio()==TipoServicio.CERTIFICADO_RMC_CREM_CONDICIONADO_PAGINA ||
				 bean.getCodigoServicio()==TipoServicio.CERTIFICADO_RMC_CREM_HISTORICO_PAGINA 
				){
			/**
			 * @autor Daniel L. Bravo Falcón
			 * @descripcion Se encarga de calcular el costo faltante para CREM.
			 */
			flagSegundoPagoCertificadoCREM = true;
			PrepagoBean prepagoBean = calcularPrecioCREM(bean.getCodigoServicio(), bean.getCodigoGLA(), bean.getCantidadRegistros(), myConn);
			int codServicio = prepagoBean.getCodigoServicio();
			costo_servicio  = prepagoBean.getMontoBruto(); 
			
			bean.setCodigoServicio(codServicio);
			
		}else if((bean.getCodigoServicio() == Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE) 
				&& (usuarioSession.getFgInterno()==false && usuarioSession.getExonPago()==false)){
			double precioBase 	= 0; 
			double precioPagina = 0;
			long numeroPaginas	= 0;
			
			precioBase=costoTarifa(bean.getCodigoGLA(), Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE, myConn);
			precioPagina=costoTarifa(bean.getCodigoGLA(), Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_POR_PAGINA, myConn);
			numeroPaginas 	= Long.parseLong(((LogAuditoriaCertificadoBean)bean).getCantidad())-2;
			if(numeroPaginas < 0){
				numeroPaginas = 0;
			}
			costo_servicio  = precioBase + (precioPagina*numeroPaginas);
			System.out.println("Servicio: " + bean.getCodigoServicio() + "; " + "Costo: " + costo_servicio );
		}else if(bean.getCodigoServicio() == Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC){
			
			flagTransaccion = true;
			costo_servicio = costoTarifa(bean, myConn);
			
		}else if(bean.getCodigoServicio() == Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC){
			
			flagTransaccion = true;
			costo_servicio = costoTarifa(bean, myConn);
			
		}else if(bean.getCodigoServicio() == Constantes.SERVICIO_BUSQ_INDICE_PARTIDA_RMC){
			
			flagTransaccion = true;
			costo_servicio = costoTarifa(bean, myConn);
			System.out.println("[JROSAS] Constantes.SERVICIO_BUSQ_INDICE_PARTIDA_RMC, costo: "+costo_servicio);
			
		}else if(bean.getCodigoServicio() == Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_INDICE_PARTIDA_RMC){
			
			flagTransaccion = true;
			costo_servicio = costoTarifa(bean, myConn);
			System.out.println("[JROSAS] Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_INDICE_PARTIDA_RMC, costo: "+costo_servicio);
			
		}else if((bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=160 && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO) ||
		   (usuarioSession.getFgInterno()==false && usuarioSession.getExonPago()==false)){
			
			if(bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140)
			{
				
				if(bean.getCodigoServicio()==111){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==112){
					
					costo_servicio = (Double.parseDouble(((LogAuditoriaCertificadoBean)bean).getCantidad()) - 2) * costoTarifa(bean, myConn);
					bean.setCodigoServicio(111);
					costo_servicio = costo_servicio + costoTarifa(bean, myConn);
					bean.setCodigoServicio(112);
				
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RMC_POSITIVO_NEGATIVO){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RMC_GRAVAMEN){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RMC_VIGENCIA){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_VEHICULAR){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_AERONAVES){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_BUQUES){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_EMB_PESQ){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_VEHICULAR){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_AERONAVES){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_EMB_PESQ){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_BUQUES){
					
					costo_servicio = costoTarifa(bean, myConn);
					
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_VEHICULAR){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_AERONAVES){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_BUQUES){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_EMB_PESQ){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_VEHICULAR){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_EMB_PESQ){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_AERONAVES){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else if(bean.getCodigoServicio()==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_BUQUES){
					
					costo_servicio = costoTarifa(bean, myConn);
						
				}else 
				{
					costo_servicio = Double.parseDouble(((LogAuditoriaCertificadoBean)bean).getCantidad()) * costoTarifa(bean, myConn);
				}
				
			}
			else
			{
				costo_servicio = costoTarifa(bean, myConn);
				
			}
		}else if(bean.getCodigoServicio() == TipoServicio.VISUALIZA_PARTIDA_RMC){
			costo_servicio = costoTarifa(bean, myConn);
		}
		/**
		 * Inicio:mgarate:20/11/2007 - PCM
		 * 01 - cobro por POS costo 0.0
		 * 03 - cobro a cuenta a saldo se cobra sobre el tag que envia el colegio de notarios 
		 * 		en el tag de cobro total del servicio 
		**/
		if(bean.getCodigoServicio() == 160)
		{
			if(bean.getCodigoTipoPago().equals("03"))
			{
				costo_servicio = bean.getCostoTotal();
			}else if(bean.getCodigoTipoPago().equals("01"))
			{
				costo_servicio =  0.0;
			}
		}
		//Fin:mgarate
		
		DboTransaccion transac = new DboTransaccion();
		transac.setConnection(myConn);
		transac.setField(transac.CAMPO_TRANS_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(myConn))));
		transac.setField(transac.CAMPO_SERVICIO_ID, bean.getCodigoServicio());
		//Tarifario
		transac.setField(transac.CAMPO_COD_GRUPO_LIBRO_AREA, bean.getCodigoGLA());
		transac.setField(transac.CAMPO_CUENTA_ID, usuarioSession.getCuentaId());
		transac.setField(transac.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		//Inicio:mgarate:20/06/2007
		if(usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)
		{
			transac.setField(transac.CAMPO_COSTO, "0");
		}else
		{
			transac.setField(transac.CAMPO_COSTO, Double.toString(costo_servicio));
		}
		//Fin:mgarate:20/06/2007
		transac.setField(transac.CAMPO_IP, bean.getRemoteAddr());
                
        //Modificado por: Proyecto Filtros de Acceso
        //Fecha: 03/10/2006
        transac.setField(transac.CAMPO_SESION_ID, bean.getIdSesion());
        //Fin Modificación                
        
        /**
         * @autor Daniel L. Bravo Falcón
         * @fecha 14/08/207
         * @descripcion Para un realizar el segundo pago de un certificado CREM, se debe registrar en la transaccion:
         *               - tipo_usr    = Usuario externo(1).
         *               - cuenda_id   = El id de la cuenta del solicitante.
         *               - reg_pub_id  = El id del registro publico del solicitante.
         *               - ofic_reg_id = El id de la oficina publica del solicitante.
         *              Caso contrario se realizara lo ya existente. 
         *              
         */
        if(costo_servicio >= 0 && flagSegundoPagoCertificadoCREM==true){
        	
        	StringBuffer cadenacj = new StringBuffer(DboCuentaJuris.CAMPO_PERSONA_ID);
			cadenacj.append("|").append(DboCuentaJuris.CAMPO_OFIC_REG_ID);
			cadenacj.append("|").append(DboCuentaJuris.CAMPO_REG_PUB_ID);
			cadenacj.append("|").append(DboCuentaJuris.CAMPO_JURIS_ID);

			DboCuentaJuris cuentajuri = new DboCuentaJuris(myConn);
			cuentajuri.setFieldsToRetrieve(cadenacj.toString());
			cuentajuri.setField(DboCuentaJuris.CAMPO_CUENTA_ID, bean.getCuentaIdSolicitante());

			if (!cuentajuri.find())
				throw new CustomException(Constantes.NO_REG_CUENTA_JURIS);
        	
	    	transac.setField(transac.CAMPO_TIPO_USR, "1");
			transac.setField(transac.CAMPO_STR_BUSQ, descripcion);
			transac.setField(transac.CAMPO_REG_PUB_ID, cuentajuri.getField(DboCuentaJuris.CAMPO_REG_PUB_ID));
			transac.setField(transac.CAMPO_OFIC_REG_ID, cuentajuri.getField(DboCuentaJuris.CAMPO_OFIC_REG_ID));
			
        }else{
			//25octHT
			if (bean.getCodigoServicio()==TipoServicio.AFILIACION_EXTRANET &&
			    usuarioSession.getPerfilId()==Constantes.PERFIL_CAJERO)
			    	transac.setField(transac.CAMPO_TIPO_USR, "1");
			else
					transac.setField(transac.CAMPO_TIPO_USR, usuarioSession.getFgInterno() ? "0" : "1");
			transac.setField(transac.CAMPO_STR_BUSQ, descripcion);
			transac.setField(transac.CAMPO_REG_PUB_ID, usuarioSession.getRegPublicoId());
			transac.setField(transac.CAMPO_OFIC_REG_ID, usuarioSession.getOficRegistralId());
        }
        
		transac.add();

		long transacID = Long.parseLong(transac.getField(DboTransaccion.CAMPO_TRANS_ID));
		
		PrepagoBean prep = new PrepagoBean();
		prep.setTransacId(transacID);
		if(costo_servicio >= 0 && !usuarioSession.getExonPago() && flagSegundoPagoCertificadoCREM==false)
		{
			prep.setUsuario(usuarioSession.getUserId());
			prep.setLineaPrepagoId(usuarioSession.getLinPrePago());
			prep.setMontoBruto(costo_servicio);
			//prep.setTransacId(transacID);
			
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(usuarioSession, prep, myConn);
			/**Comentado el 25/08/2003**/
			//Inicio:mgarate:05/06/2007
			if(bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=148)
				((LogAuditoriaCertificadoBean)bean).setConsumoId(prep.getConsumoId());
			//Fin:mgarate:05/06/2007
			/****/
			//Inicio:jascencio:07/08/2007
			if(bean.getCodigoServicio() == Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE)
				((LogAuditoriaCertificadoBean)bean).setConsumoId(prep.getConsumoId());
			//Fin:jascencio
			/*********** MODIFICADO GIANCARLO OCHOA 07/2006 *********************/
			System.out.println("seteando consumo id...");
			if(bean.getCodigoServicio()==Constantes.COD_SERVICIO_SOLINSCR)
				((LogAuditoriaSolicitudInscripcionBean)bean).setConsumoId(prep.getConsumoId());				
			/*****************/
		}else if(costo_servicio >= 0 && flagSegundoPagoCertificadoCREM==true){
			/**
			 * dbravo:13/08/2007
			 * descripcion: 
			 */
			DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(myConn);
			dboLineaPrepago.clearAll();
			dboLineaPrepago.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
			dboLineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID, bean.getCuentaIdSolicitante());
			
			if (dboLineaPrepago.find() == true) {
				prep.setLineaPrepagoId(dboLineaPrepago.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
			}	
			usuarioSession.getUserId();
			
			prep.setUsuario(bean.getCuentaIdSolicitante());

			prep.setMontoBruto(costo_servicio);
			//prep.setTransacId(transacID);
			
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(usuarioSession, prep, myConn);
			
		}
		//if(costo_servicio > 0 &&  (bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140) && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)
		/******************** MODIFICADO POR GIANCARLO OCHOA V. 20/07/2006 *********************/
		//if(costo_servicio >= 0 &&  (bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140) && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)
		if(costo_servicio >= 0 &&  ((bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=190) || flagTransaccion==true) && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)
		/*************************************************************************************/
		{
			//PrepagoBean prep = new PrepagoBean();
				
			//prep.setUsuario(Constantes.COMODIN_USUARIO);
			prep.setUsuario(Comodin.getInstance().getUsuario());
			//prep.setLineaPrepagoId(""+Constantes.COMODIN_LINEA_PREPAGO);
			prep.setLineaPrepagoId(""+Comodin.getInstance().getLineaPrePago());
			prep.setMontoBruto(costo_servicio);
			//prep.setTransacId(transacID);
			
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(usuarioSession, prep, myConn);
			if (flagTransaccion==false){ // solo para certificados 
				((LogAuditoriaCertificadoBean)bean).setConsumoId(prep.getConsumoId());
			}	
		}
		
		prep.setMontoBruto(costo_servicio);
		return prep;
	}
	
	//Metodo que graba la Transaccion
	private long grabaTransaccionAntes(String descripcion, TransactionBean bean, DBConnection myConn) 
		throws CustomException, DBException, Throwable{
		
		UsuarioBean usuarioSession = bean.getUsuarioSession();
		double costo_servicio = 0.0;
		
		/******************** MODIFICADO POR GIANCARLO OCHOA V. 20/07/2006 *********************/
		//if((bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140 && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)||
		   //(usuarioSession.getFgInterno()==false && 
		   //usuarioSession.getExonPago()==false))
		System.out.println("grabaTransaccion....");
		if((bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=160 && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)||
		   (usuarioSession.getFgInterno()==false && 
		   usuarioSession.getExonPago()==false))
		/*****************************************************************************************/   
		{
			if(bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140)
			{
				// 30/11/05 - HP - Inicio
				// reemplazar la forma como recuperar la tarifa
				// antes: multiplica por 2 el costo en caso la página sea igual a 1
				// ahora: cobra una tarifa por las 2 primeras imágenes y le suma otra tarifa por c/imagen adicional
				/*
				if(((LogAuditoriaCertificadoBean)bean).getCantidad().equals("1"))
				{
					if(bean.getCodigoServicio()==110)
						costo_servicio = 2.00 * costoTarifa(bean, myConn);
					else
						costo_servicio = costoTarifa(bean, myConn);
				}
				else
				{
					costo_servicio = Double.parseDouble(((LogAuditoriaCertificadoBean)bean).getCantidad()) * costoTarifa(bean, myConn);
				}
				*/
				if(bean.getCodigoServicio()==111)
					costo_servicio = costoTarifa(bean, myConn);
				else if(bean.getCodigoServicio()==112)
				{
					costo_servicio = (Double.parseDouble(((LogAuditoriaCertificadoBean)bean).getCantidad()) - 2) * costoTarifa(bean, myConn);
					bean.setCodigoServicio(111);
					costo_servicio = costo_servicio + costoTarifa(bean, myConn);
					bean.setCodigoServicio(112);
				}
				else
				{
					costo_servicio = Double.parseDouble(((LogAuditoriaCertificadoBean)bean).getCantidad()) * costoTarifa(bean, myConn);
				}
				System.out.println("Servicio: " + bean.getCodigoServicio() + "; " + "Costo: " + costo_servicio );
				// 30/11/05 - HP - Fin
			}
			else
			{
				costo_servicio = costoTarifa(bean, myConn);
			}
		}
		
		DboTransaccion transac = new DboTransaccion();
		transac.setConnection(myConn);
		/**
		 * SVASQUEZ - AVATAR GLOBAL
		 * SE GENERA UN SECUECIAL PARA LA TABLA DE TRANSACCIONES
		 * DboTransaccion
		 */
		transac.setField(transac.CAMPO_TRANS_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(myConn))));		
		transac.setField(transac.CAMPO_SERVICIO_ID, bean.getCodigoServicio());
		//Tarifario
		transac.setField(transac.CAMPO_COD_GRUPO_LIBRO_AREA, bean.getCodigoGLA());
		transac.setField(transac.CAMPO_CUENTA_ID, usuarioSession.getCuentaId());
		transac.setField(transac.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		transac.setField(transac.CAMPO_COSTO, Double.toString(costo_servicio));
		transac.setField(transac.CAMPO_IP, bean.getRemoteAddr());
                
                //Modificado por: Proyecto Filtros de Acceso
                //Fecha: 03/10/2006
                transac.setField(transac.CAMPO_SESION_ID, bean.getIdSesion());
                //Fin Modificación                
                
		//25octHT
		if (bean.getCodigoServicio()==TipoServicio.AFILIACION_EXTRANET &&
		    usuarioSession.getPerfilId()==Constantes.PERFIL_CAJERO)
		    	transac.setField(transac.CAMPO_TIPO_USR, "1");
		else
				transac.setField(transac.CAMPO_TIPO_USR, usuarioSession.getFgInterno() ? "0" : "1");
		transac.setField(transac.CAMPO_STR_BUSQ, descripcion);
		transac.setField(transac.CAMPO_REG_PUB_ID, usuarioSession.getRegPublicoId());
		transac.setField(transac.CAMPO_OFIC_REG_ID, usuarioSession.getOficRegistralId());

		//SE AGREGA PARA VALIDAR LA SESION - INICIO
		/*DboTransaccion transacAnterior = new DboTransaccion();
		transacAnterior.setConnection(myConn);
		transacAnterior.setField(transacAnterior.CAMPO_TRANS_ID, numSessionAnterior);
		transacAnterior.find();
		int transacIDActual = Integer.valueOf(transacAnterior.getField(DboTransaccion.CAMPO_TRANS_ID));
		//SE AGREGA PARA VALIDAR LA SESION - FIN
		if(transacIDActual != numSessionAnterior)
		transac.add();

		//SE AGREGA PARA VALIDAR LA SESION
		numSessionAnterior = Integer.valueOf(transac.getField(DboTransaccion.CAMPO_TRANS_ID));
		*/
		transac.add();
		long transacID = Long.parseLong(transac.getField(DboTransaccion.CAMPO_TRANS_ID));
		
		if(costo_servicio >= 0 && !usuarioSession.getExonPago())
		{
			PrepagoBean prep = new PrepagoBean();
				
			prep.setUsuario(usuarioSession.getUserId());
			prep.setLineaPrepagoId(usuarioSession.getLinPrePago());
			prep.setMontoBruto(costo_servicio);
			prep.setTransacId(transacID);
			
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(usuarioSession, prep, myConn);
			/**Comentado el 25/08/2003**/
			//Inicio:mgarate:05/06/2007
			if(bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=144)
				((LogAuditoriaCertificadoBean)bean).setConsumoId(prep.getConsumoId());
			//Fin:mgarate:05/06/2007
			/****/
			/*********** MODIFICADO GIANCARLO OCHOA 07/2006 *********************/
			System.out.println("seteando consumo id...");
			if(bean.getCodigoServicio()==Constantes.COD_SERVICIO_SOLINSCR)
				((LogAuditoriaSolicitudInscripcionBean)bean).setConsumoId(prep.getConsumoId());				
			/*****************/
		}
		//if(costo_servicio > 0 &&  (bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140) && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)
		/******************** MODIFICADO POR GIANCARLO OCHOA V. 20/07/2006 *********************/
		//if(costo_servicio >= 0 &&  (bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=140) && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)
		if(costo_servicio >= 0 &&  (bean.getCodigoServicio()>109 && bean.getCodigoServicio()<=160) && usuarioSession.getPerfilId() == Constantes.PERFIL_CAJERO)
		/*************************************************************************************/
		{
			PrepagoBean prep = new PrepagoBean();
				
			//prep.setUsuario(Constantes.COMODIN_USUARIO);
			prep.setUsuario(Comodin.getInstance().getUsuario());
			//prep.setLineaPrepagoId(""+Constantes.COMODIN_LINEA_PREPAGO);
			prep.setLineaPrepagoId(""+Comodin.getInstance().getLineaPrePago());
			prep.setMontoBruto(costo_servicio);
			prep.setTransacId(transacID);
			
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(usuarioSession, prep, myConn);
			((LogAuditoriaCertificadoBean)bean).setConsumoId(prep.getConsumoId());
		}
		
		return transacID;
	}

//Metodos de Funcionalidad Generica
	private String nombreOficina(String regPubId, String oficRegId, DBConnection myConn) throws DBException, Throwable{
		DboOficRegistral oficina = new DboOficRegistral();
		oficina.setConnection(myConn);

		oficina.clearAll();
		oficina.setFieldsToRetrieve(DboOficRegistral.CAMPO_NOMBRE);
		oficina.setField(DboOficRegistral.CAMPO_REG_PUB_ID, regPubId);
		oficina.setField(DboOficRegistral.CAMPO_OFIC_REG_ID, oficRegId);
		if(oficina.find())
			return oficina.getField(DboOficRegistral.CAMPO_NOMBRE);
		else
			return "No disponible";
	}

	private String nombreSede(String regPubId, DBConnection myConn) throws DBException, Throwable{
		DboRegisPublico sede = new DboRegisPublico();
		sede.setConnection(myConn);

		sede.clearAll();
		sede.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
		sede.setField(DboRegisPublico.CAMPO_REG_PUB_ID, regPubId);
		if(sede.find())
			return sede.getField(DboRegisPublico.CAMPO_NOMBRE);
		else
			return "No disponible";
	}

	private double costoTarifa(TransactionBean bean, DBConnection myConn) throws CustomException, DBException, Throwable
	{
		// Fecha: 2003-07-31
		// Autor: HP
		// Descripción: Se agregó la funcionalidad para que cobre los servicios de búsquedas por índices en Lima
		int codServicio= bean.getCodigoServicio();
		//Tarifario
		int codGLA= bean.getCodigoGLA();
		
		DboTarifa tarifa = new DboTarifa(myConn);
		tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		
		if (codServicio>=20 && codServicio<=32)
		{
			LogAuditoriaBusqPartidaBean lb = (LogAuditoriaBusqPartidaBean) bean;						
			String[] sedes = lb.getNumSedes();
			boolean blima = false;
			String areaReg = lb.getCodAreaReg()==null?"":lb.getCodAreaReg(); 
		}
		else
		{
			if(codServicio == 90)
			{		
				LogAuditoriaConsultaPlacaBean lb2 = (LogAuditoriaConsultaPlacaBean) bean;
				String flCobro = lb2.getFlagCobro()==null?"":lb2.getFlagCobro(); 	
				if(flCobro.equals("1"))
					return 0;					
			}	
			
		}
		
		tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicio);
		//Tarifario
		tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codGLA);
		
		if(!tarifa.find())
			throw new DBException("No existe servicio con codigo '"+ codServicio + "' y GLA '"+ codGLA + "' en tabla TARIFA");
				
		return Double.parseDouble(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
	}
	/*
	 * Inicio:jascencio:07/08/2007
	 */
	private double costoTarifa(int codigoGLA,int codigoServicio, DBConnection myConn) throws CustomException, DBException, Throwable
	{
		
		DboTarifa tarifa = new DboTarifa(myConn);
		tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		
		tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codigoServicio);
		//Tarifario
		tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codigoGLA);
		
		if(!tarifa.find())
			throw new DBException("No existe servicio con codigo '"+ codigoServicio + "' y GLA '"+ codigoGLA + "' en tabla TARIFA");
				
		return Double.parseDouble(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
	}
	/*
	 * Fin:jascencio
	 */
/*
	private double costoTarifa(TransactionBean bean, DBConnection myConn) throws CustomException, DBException, Throwable
	{
		int codServicio= bean.getCodigoServicio();
		DboTarifa tarifa = new DboTarifa(myConn);
		tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		
		//3ene2003, buscar en Lima a través de Búsqueda 
		//          Indirecta, no debe costar

		if (codServicio>=20 && codServicio<=32)
		{
			LogAuditoriaBusqPartidaBean lb = (LogAuditoriaBusqPartidaBean) bean;						
			String[] sedes = lb.getNumSedes();
			boolean blima = false;
			for (int w =0; w < sedes.length; w++)
				{
					if (sedes[w].equals("01"))
						blima=true;
				}
			
							
			String areaReg = lb.getCodAreaReg()==null?"":lb.getCodAreaReg(); 
			if (blima==true && !areaReg.equals("24000"))///lima = true y no es RPV	
				codServicio--;
				
			if (codServicio<20)
				codServicio=20;
				
			if (codServicio==20 && blima==true && !areaReg.equals("24000"))///codServ = 20 y no es RPV
				return 0;
				
			//if bean partida incompleta ok -> retrun 0	
				
		}
		else
		{
			if(codServicio == 90)
			{		
				LogAuditoriaConsultaPlacaBean lb2 = (LogAuditoriaConsultaPlacaBean) bean;
				String flCobro = lb2.getFlagCobro()==null?"":lb2.getFlagCobro(); 	
				if(flCobro.equals("1"))
					return 0;					
			}	
			
		}
		
		
		tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicio);
		
		if(!tarifa.find())
			throw new DBException("No existe servicio con codigo '"+ codServicio + "' en tabla TARIFA");
				
		return Double.parseDouble(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
	}
*/	
	//Auditoria: Consulta Titulo Vehicular
	/** Kuma **/
	private void registraLogAuditoriaConsulTituloVehi (LogAuditoriaConsulTituloBean bean, DBConnection myConn) throws DBException, Throwable{
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer("CONSULTA DE TITULO VEHICULAR: ");
		strBusq.append(bean.getNumTitulo()).append(" ANO: ").append(bean.getAnoTitulo()).append(" Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		long idTran = grabaTransaccion(strBusq.toString(), (TransactionBean) bean, myConn);

		DboAudConsultaTitulo tablaAudit = new DboAudConsultaTitulo();
		tablaAudit.setConnection(myConn);
			
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_AA_TITULO, bean.getAnoTitulo());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_NUM_TITULO, bean.getNumTitulo());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_OFIC_REG_ID, bean.getOficRegId());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_REG_PUB_ID, bean.getRegPubId());
		tablaAudit.setField(DboAudConsultaTitulo.CAMPO_TRANS_ID, Long.toString(idTran));
		tablaAudit.add();
	}
	
	//Auditoria: Consulta Placa
	/** Kuma **/
	private PrepagoBean registraLogAuditoriaConsultaPlaca (LogAuditoriaConsultaPartidaBean bean, DBConnection myConn) throws DBException, Throwable{
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer("CONSULTA DE PARTIDA-");
		
		strBusq.append("Ofic Reg: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		
		if(bean.getTipoConsPartida().equals("1"))
						strBusq.append(" NUMERO DE PARTIDA ").append(bean.getNumPartFic());

		if(bean.getTipoConsPartida().equals("2"))
						strBusq.append(" NUMERO DE FICHA ").append(bean.getNumPartFic());

		if(bean.getTipoConsPartida().equals("3")){
			StringTokenizer stk = new StringTokenizer(bean.getLibTomFol(), "|");
			String aux[] = new String[3];
			int i = 0;
				  
			while (stk.hasMoreTokens()) {
				aux[i++] = stk.nextToken();
			}
			
			strBusq.append(" LIBRO ").append(aux[0]).append(" TOMO: ").append(aux[1]).append(" FOLIO: ").append(aux[2]);
		}
		
		PrepagoBean prepagoBean = grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);
		
		DboAudConsultaPartida tablaAudit = new DboAudConsultaPartida(myConn);
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_LIB_TOM_FOL, bean.getLibTomFol());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_NUM_PART_FICHA, bean.getNumPartFic());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_OFIC_REG_ID, bean.getOficRegId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_REG_PUB_ID, bean.getRegPubId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TIPO_BUSQ, bean.getTipoBusq());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TRANS_ID, Long.toString(prepagoBean.getTransacId()));
		tablaAudit.add();
		
		return prepagoBean;
		
	}
	
	//Auditoria: Publicidad Certificada
	/** Kuma 08/07/2003**/
	private PrepagoBean registraLogAuditoriaPubliCerti (LogAuditoriaCertificadoBean bean, DBConnection myConn) throws DBException, Throwable{
	//Se prepara el String de Búsqueda
		StringBuffer strBusq = new StringBuffer("PUBLICIDAD CERTIFICADA - ");
		
		strBusq.append("OFICINA: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		strBusq.append(" SOLICITUD: ").append(bean.getSolicitud_id());
		
		int tipo = bean.getCodigoServicio();
		switch (tipo)
		{
			case TipoServicio.COPIA_LITERAL_CERTIFICADA:
				strBusq.append(" SERVICIO: COPIA LITERAL CERTIFICADA");
				break;
			case TipoServicio.CERT_NEG_TESTAMENTO:
				strBusq.append(" SERVICIO: CERT. NEG. DE TESTAMENTO");
				break;
			case TipoServicio.CERT_NEG_SUC_INTEST:
				strBusq.append(" SERVICIO: CERT. NEG. DE SUCECION INTESTADA");
				break;
			case TipoServicio.CERT_NEG_REGIS_PERS:
				strBusq.append(" SERVICIO: CERT. NEG. DE REGISTRO DE PERSONAL");
				break;
			case TipoServicio.CERT_NEG_PERS_JURID:
				strBusq.append(" SERVICIO: CERT. NEG. DE PERSONA JURIDICA");
				break;
			case TipoServicio.CERT_NEG_PROP_INMUE:
				strBusq.append(" SERVICIO: CERT. NEG. DE PROPIEDAD INMUEBLE");
				break;
			case TipoServicio.CERT_NEG_PROP_VEHIC:
				strBusq.append(" SERVICIO: CERT. NEG. DE PROPIEDAD VEHICULAR");
				break;
			case TipoServicio.DELIVERY_CERT:
				strBusq.append(" SERVICIO: ENVIO DE CERTIFICADOS");
				break;
			
			//Inicio:dbravo:08/06/2007
			//TODO: dbravo, definir: EL NOMBRE PARA CADA CERTIFICADO, Y SI VA PODER INGRESARSE EN UN PROPERTIES
			case TipoServicio.CERTIFICADO_RMC_CREM_ACTOS_VIGENTES:
				strBusq.append(" SERVICIO: CERT. DE ACTOS VIGENTES");
				break;
			case TipoServicio.CERTIFICADO_RMC_CREM_HISTORICO:
				strBusq.append(" SERVICIO: CERT. CREM HISTORICO");
				break;
			case TipoServicio.CERTIFICADO_RMC_CREM_CONDICIONADO:
				strBusq.append(" SERVICIO: CERT. CREM CONDICIONADO");
				break;
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_VEHICULAR:
				strBusq.append(" SERVICIO: CERT. RJB DOMINEAL VEHICULAR");
				break;
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_BUQUES:
				strBusq.append(" SERVICIO: CERT. RJB DOMINEAL BUQUES");
				break;
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_AERONAVES:
				strBusq.append(" SERVICIO: CERT. RJB DOMINEAL AERONOVES");
				break;
			case TipoServicio.CERTIFICADO_RMC_RJB_DOMINEAL_EMBARCACION_PESQUERA:
				strBusq.append(" SERVICIO: CERT. RJB DOMINEAL EMBARCACION PESQUERA");
				break;		
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_VEHICULAR:
				strBusq.append(" SERVICIO: CERT. RJB GRAVAMEN VEHICULAR");
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_BUQUES:
				strBusq.append(" SERVICIO: CERT. RJB GRAVAMEN BUQUES");
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_AERONAVES:
				strBusq.append(" SERVICIO: CERT. RJB GRAVAMEN AERONAVES");
				break;	
			case TipoServicio.CERTIFICADO_RMC_RJB_GRAVAMEN_EMBARCACION_PESQUERA:
				strBusq.append(" SERVICIO: CERT. RJB GRAVAMEN EMBARCACION PESQUERA");
				break;
			//Inicio:garate:19/06/2007	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_VEHICULAR:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA DIRECTA VEHICULAR");
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_EMBARCACION_PESQUERA:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA DIRECTA EMBARCACION PESQUERA");
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_AERONAVES:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA DIRECTA AERONAVES");
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_DIRECTA_BUQUES:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA DIRECTA BUQUES");
				break;
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_VEHICULAR:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA INDICE VEHICULAR");
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_EMBARCACION_PESQUERA:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA INDICE EMBARCACION PESQUERA");
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_AERONAVES:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA INDICE AERONAVES");
				break;	
			case TipoServicio.CERTIFICADO_RMC_BUSQUEDA_INDICE_BUQUES:
				strBusq.append(" SERVICIO: CERT. RMC BUSQUEDA INDICE BUQUES");
				break;
			//Fin:mgarate:19/06/2007	
			case TipoServicio.CERTIFICADO_RMC_RMC_POSITIVO_NEGATIVO:
				strBusq.append(" SERVICIO: CERT. RMC POSITIVO NEGATIVO");
				break;	
			case TipoServicio.CERTIFICADO_RMC_RMC_GRAVAMEN:
				strBusq.append(" SERVICIO: CERT. RMC GRAVAMEN");
				break;		
			case TipoServicio.CERTIFICADO_RMC_RMC_VIGENCIA:
				strBusq.append(" SERVICIO: CERT. RMC VIGENCIA");
				break;	
			//Fin:dbravo:08/06/2007
			//Inicio:jascencio:07/08/2007
			case TipoServicio.CERTIFICADO_COPIA_LITERAL_RMC:
				strBusq.append(" SERVICIO: CERT. COPIA LITERAL RMC CERTIFICADA");
				break;
			//Fin:jascencio
			
		}
		
		return grabaTransaccionPrepago(strBusq.toString(), (TransactionBean) bean, myConn);
		//FALTA QUE HARLIN LO CREE
		/*
		DboAudConsultaPartida tablaAudit = new DboAudConsultaPartida(myConn);
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_LIB_TOM_FOL, bean.getLibTomFol());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_NUM_PART_FICHA, bean.getNumPartFic());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_OFIC_REG_ID, bean.getOficRegId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_REG_PUB_ID, bean.getRegPubId());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TIPO_BUSQ, bean.getTipoBusq());
		tablaAudit.setField(DboAudConsultaPartida.CAMPO_TRANS_ID, Long.toString(idTran));
		tablaAudit.add();
		*/
	}
	
	/***************************** AGREGADO GIANCARLO OCHOA V.*****************************/
	private void registraLogAuditoriaSolicitudInscripcion (LogAuditoriaSolicitudInscripcionBean bean, DBConnection myConn) throws DBException, Throwable{
		//Se prepara el String de Búsqueda
		
		System.out.println("registraLogAuditoriaSolicitudInscripcion.....");
		StringBuffer strBusq = new StringBuffer(" SOLICITUD DE INSCRIPCION - CONSTITUCION DE EMPRESA");
		
		/*strBusq.append("OFICINA: ").append(nombreOficina(bean.getRegPubId(), bean.getOficRegId(), myConn));
		
		strBusq.append(" SOLICITUD: ").append(bean.getSolicitud_id());
		
		int tipo = bean.getCodigoServicio();
		switch (tipo)
		{
			case TipoServicio.COPIA_LITERAL_CERTIFICADA:
				strBusq.append(" SERVICIO: COPIA LITERAL CERTIFICADA");
				break;
			case TipoServicio.CERT_NEG_TESTAMENTO:
				strBusq.append(" SERVICIO: CERT. NEG. DE TESTAMENTO");
				break;
			case TipoServicio.CERT_NEG_SUC_INTEST:
				strBusq.append(" SERVICIO: CERT. NEG. DE SUCECION INTESTADA");
				break;
			case TipoServicio.CERT_NEG_REGIS_PERS:
				strBusq.append(" SERVICIO: CERT. NEG. DE REGISTRO DE PERSONAL");
				break;
			case TipoServicio.CERT_NEG_PERS_JURID:
				strBusq.append(" SERVICIO: CERT. NEG. DE PERSONA JURIDICA");
				break;
			case TipoServicio.CERT_NEG_PROP_INMUE:
				strBusq.append(" SERVICIO: CERT. NEG. DE PROPIEDAD INMUEBLE");
				break;
			case TipoServicio.CERT_NEG_PROP_VEHIC:
				strBusq.append(" SERVICIO: CERT. NEG. DE PROPIEDAD VEHICULAR");
				break;
			case TipoServicio.DELIVERY_CERT:
				strBusq.append(" SERVICIO: ENVIO DE CERTIFICADOS");
				break;
							
		}*/
		System.out.println("antes de grabaTransaccion.....");
		long idTran = grabaTransaccion(strBusq.toString(), (TransactionBean) bean, myConn);
		/**
		 * Inicio:mgarate:20/11/2007 - PCM
		**/
		bean.setNumeroOperacion(idTran);	
		//Fin:mgarate - PCM
		System.out.println("despues de grabaTransaccion.....idTran::"+idTran);
	}
	/*****************************************************************************************/

	public void registraLogAuditoriaAtencionSolicitud (LogAuditoriaAtencionSolicitudBean bean, DBConnection myConn) throws DBException, Throwable{
	
		DboAudAtencionSolicitud atencionSolicitud = new DboAudAtencionSolicitud(myConn);
				
		atencionSolicitud.setField(DboAudAtencionSolicitud.CAMPO_TS_ATENCION, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		atencionSolicitud.setField(DboAudAtencionSolicitud.CAMPO_COMENTARIO, bean.getComentario());
		atencionSolicitud.setField(DboAudAtencionSolicitud.CAMPO_CUENTA_ID, bean.getCuentaId());
		atencionSolicitud.setField(DboAudAtencionSolicitud.CAMPO_SOLICITUD_ID, bean.getSolicitudId());
		atencionSolicitud.setField(DboAudAtencionSolicitud.CAMPO_ESTADO, bean.getEstado());
		atencionSolicitud.add();
		
	}
	
	public PrepagoBean registraLogAuditoriaBusqMasivaRelacional (LogAuditoriaBusquedaMasivaRelacional bean, DBConnection myConn) throws DBException, Throwable{
		
		PrepagoBean prepagoBean = grabaTransaccionPrepago(bean.getCriterioBusqueda(), (TransactionBean) bean, myConn);
		
		DboAudBusqMasRelacional audBusqMasRelacional = new DboAudBusqMasRelacional(myConn);
		audBusqMasRelacional.setField(DboAudBusqMasRelacional.CAMPO_CRI_BUSQUEDA, bean.getCriterioBusqueda());
		audBusqMasRelacional.setField(DboAudBusqMasRelacional.CAMPO_DES_REG_PUBLICO, bean.getDescripcionRegistroPublico());
		audBusqMasRelacional.setField(DboAudBusqMasRelacional.CAMPO_TRANS_ID, String.valueOf(prepagoBean.getTransacId()));
		audBusqMasRelacional.setField(DboAudBusqMasRelacional.CAMPO_TS_BUSQUEDA, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));

		audBusqMasRelacional.add();
		
		return prepagoBean;
	}
	/**
	 * @autor Daniel L. Bravo Falcón
	 * @descripcion Este metodo se encarga de calcular el precio para la publicidad masiva relacional, según la cantidad de registros, se recupera el 
	 *              servicio adecuado, y se calcula por la tarifa definida.
	 * @param codServicio
	 * @param codGLA
	 * @param cantidadRegistros
	 * @param myConn
	 * @return
	 * @throws CustomException
	 * @throws DBException
	 * @throws Throwable
	 */
	public PrepagoBean calcularPrecioMasivo(int codServicio, int codGLA, long cantidadRegistros,DBConnection myConn)throws CustomException, DBException, Throwable{
		
		//int codServicio     = bean.getCodigoServicio();
		int codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19;
		//int codGLA= bean.getCodigoGLA();
		//long cantidadRegistros = bean.getCantidadRegistros();
		
		DboTarifa tarifa = new DboTarifa(myConn);

		if(cantidadRegistros>=0&&cantidadRegistros<20){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_0_19;
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19;
		}else if(cantidadRegistros>=20&&cantidadRegistros<101){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_20_100;
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_20_100;
		}else if(cantidadRegistros>=101&&cantidadRegistros<501){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_101_500;
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_101_500;
		}else if(cantidadRegistros>=501&&cantidadRegistros<1001){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_501_1000;
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_501_1000;
		}else if(cantidadRegistros>=1001&&cantidadRegistros<10001){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_1001_10000;
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_1001_10000;
		}else if(cantidadRegistros>=10001&&cantidadRegistros<50001){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_10001_50000;
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_10001_50000;
		}else if(cantidadRegistros>=50001&&cantidadRegistros<100001){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_50001_100000;
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_50001_100000;
		}else if(cantidadRegistros>=100001){
			codServicio = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_100001_MAS;	
			codServicioBase = Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_100001_MAS;
		}
		
		tarifa.clearAll();
		tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicioBase);
		tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codGLA);
		
		if(!tarifa.find())
			throw new DBException("No existe servicio con codigo '"+ codServicio + "' y GLA '"+ codGLA + "' en tabla TARIFA");
		
		double costoBase = Double.parseDouble(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
		
		tarifa.clearAll();
		tarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		tarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicio);
		tarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codGLA);
		
		if(!tarifa.find())
			throw new DBException("No existe servicio con codigo '"+ codServicio + "' y GLA '"+ codGLA + "' en tabla TARIFA");
		
		double costoVariable = Double.parseDouble(tarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
		
		double costo_servicio = costoBase + (costoVariable*cantidadRegistros);
		
		PrepagoBean prepagoBean = new PrepagoBean();
		prepagoBean.setCodigoServicio(codServicio);
		prepagoBean.setMontoBruto(costo_servicio);
		
		return prepagoBean;
		
	}
	
	/**
	 * @autor Daniel L. Bravo Falcón
	 * @descripcion Se encarga de calcular el precio para los certifiacdos CREM, el calculo se realiza si la cantidad de paginas es mayor a 2
	 * 			    paginas, considerar que se pasa la cantidad de saltos de lineas editadas (con el cual se determina la cantidad de paginas)
	 * 				Cada certificado CREM maneja una tarifa por pagina diferenciado.
	 * @param codServicio
	 * @param codGLA
	 * @param cantidadLineas
	 * @param dbConn
	 * @return
	 * @throws DBException
	 */
	public PrepagoBean calcularPrecioCREM(int codServicio, int codGLA, long cantidadLineas, DBConnection dbConn) throws DBException{
		
		PrepagoBean prepagoBean = new PrepagoBean();
		prepagoBean.setMontoBruto(0);
		
		ResourceBundle bundle = ResourceBundle.getBundle("gob.pe.sunarp.extranet.publicidad.properties.Publicidad");
		int lineasPorPagina = 0;
		
		if(codServicio==Constantes.SERVICIO_CERTIFICADO_CREM_CONDICIONADO_PAGINA){
			lineasPorPagina = Integer.parseInt(bundle.getString("constante.certificado.crem.condicionado.lineas.pagina"));
		}else if(codServicio==Constantes.SERVICIO_CERTIFICADO_CREM_HISTORICO_PAGINA){
			lineasPorPagina = Integer.parseInt(bundle.getString("constante.certificado.crem.historico.lineas.pagina"));
		}else if(codServicio==Constantes.SERVICIO_CERTIFICADO_CREM_ACTOS_VIGENTE_PAGINA){
			lineasPorPagina = Integer.parseInt(bundle.getString("constante.certificado.crem.vigente.lineas.pagina"));
		}else{
			throw new NullPointerException("No se ingreso el codigo del certificado CREM.");
		}
		
		long cantidadPaginas = cantidadLineas/lineasPorPagina;
		long restante        = cantidadLineas%lineasPorPagina;
		if(restante>0){
			cantidadPaginas = cantidadPaginas+1;
		}
		
		double tarifa = 0;
		
		if(cantidadPaginas>1){
			
			DboTarifa dboTarifa = new DboTarifa(dbConn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, codServicio);
			prepagoBean.setCodigoServicio(codServicio);
			
			
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, codGLA);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}
			
			double costoDiferenciado = (tarifa * (cantidadPaginas-1));
			prepagoBean.setMontoBruto(costoDiferenciado);
			
		}
		
		return prepagoBean;
		
	}
	
	/**
	 * @autor Se encarga de grabar la transaccion para cancelar la diferencia del pago CREM
	 * @param bean
	 * @param myConn
	 * @return PrepagoBean
	 * @throws DBException
	 * @throws Throwable
	 */
	public PrepagoBean registraLogAuditoriaVerificaCrem(LogAuditoriaVerificaCremBean bean, DBConnection myConn) throws DBException, Throwable{
		
		PrepagoBean prepagoBean = grabaTransaccionPrepago(null, (TransactionBean) bean, myConn);
		
		return prepagoBean;
	}
	/*****************************************************************************************/

}
