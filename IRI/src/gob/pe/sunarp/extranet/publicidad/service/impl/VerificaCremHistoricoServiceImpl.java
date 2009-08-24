package gob.pe.sunarp.extranet.publicidad.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.service.VerificaCremHistoricoService;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaCremSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaCremSQLImpl;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaCremHistoricoServiceImpl extends ServiceImpl implements VerificaCremHistoricoService{
	private Connection conn;
	private DBConnection dbConn;
	
	public VerificaCremHistoricoServiceImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
	
	/**
	 * @autor Daniel Bravo
	 * @param medio
	 * @return FormOutputBuscarPartida
	 * @throws SQLException
	 * @throws CustomException
	 * @throws ValidacionException
	 * @throws DBException
	 * @throws Throwable
	 */
	public ConstanciaCremBean comentarioCertificadoCREMHistorico(ObjetoSolicitudBean objetoSolicitudBean) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		ConstanciaCremBean constanciaCremBean = new ConstanciaCremBean();
		try{
			
			VerificaCremSQL objVerificaCremHistorico = new VerificaCremSQLImpl(this.conn, this.dbConn);
			String apellidoPaterno="";
			String apellidoMaterno="";
			String nombres="";
			String razonSocial="";
			String id_cert="";
			
			ArrayList ListadoPartidasRMC = new ArrayList();
			ArrayList ListadoTitulosPendientesRMC = new ArrayList();
			ArrayList ListadoAsientosVehicular = new ArrayList() ;
			ArrayList ListadoTitulosPendientesVehicular = new ArrayList();
			ArrayList ListadoAsientosEmbarcacionPesquera = new ArrayList();
			ArrayList ListadoTitulosPendientesEmbarcacionPesquera  = new ArrayList();
			ArrayList ListadoAsientosBuques = new ArrayList();
			ArrayList ListadoTitulosPendientesBuques = new ArrayList(); 
			ArrayList ListadoAsientosAeronaves = new ArrayList();
			ArrayList ListadoTitulosPendientesAeronaves = new ArrayList(); 
			ArrayList ListadoAsientosPersonasJuridicas = new ArrayList(); 
			ArrayList ListadoTitulosPendientesPersonasJuridicas = new ArrayList();
			
			
			String certificado_id = objetoSolicitudBean.getCertificado_id();
			String tipoPersona="";
			//if (certificado_id.equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO)){ 							// Crem - Historico
				tipoPersona = objetoSolicitudBean.getTpo_pers();
				if (tipoPersona.equals("N")){ 							
					apellidoPaterno= objetoSolicitudBean.getApe_pat();
					apellidoMaterno= objetoSolicitudBean.getApe_mat();
					nombres = objetoSolicitudBean.getNombres();
					id_cert = objetoSolicitudBean.getCertificado_id();
					
					ListadoPartidasRMC = objVerificaCremHistorico.busquedaPersonaNaturalRMC(apellidoPaterno, apellidoMaterno, nombres);
					ListadoAsientosVehicular = objVerificaCremHistorico.busquedaPersonaNaturalVehicularRJB(apellidoPaterno, apellidoMaterno, nombres,id_cert);
					ListadoAsientosEmbarcacionPesquera = objVerificaCremHistorico.busquedaPersonaNaturalEmbarcacionesPesquerasRJB(apellidoPaterno, apellidoMaterno, nombres);
					ListadoAsientosBuques = objVerificaCremHistorico.busquedaPersonaNaturalBuquesRJB(apellidoPaterno, apellidoMaterno, nombres);
					ListadoAsientosAeronaves = objVerificaCremHistorico.busquedaPersonaNaturalAeronaveRJB(apellidoPaterno, apellidoMaterno, nombres);
					ListadoAsientosPersonasJuridicas = objVerificaCremHistorico.busquedaPersonaJuridicaNatural(apellidoPaterno, apellidoMaterno, nombres);
 
				}else{
					if (tipoPersona.equals("J")){
						razonSocial= objetoSolicitudBean.getRaz_soc();
						
						ListadoPartidasRMC = objVerificaCremHistorico.busquedaPersonaJuridicaRMC(razonSocial);
						ListadoAsientosVehicular = objVerificaCremHistorico.busquedaPersonaJuridicaVehicularRJB(razonSocial,id_cert);
						ListadoAsientosEmbarcacionPesquera = objVerificaCremHistorico.busquedaPersonaJuridicaEmbarcacionesPesquerasRJB(razonSocial);
						ListadoAsientosBuques = objVerificaCremHistorico.busquedaPersonaJuridicaBuquesRJB(razonSocial);
						ListadoAsientosAeronaves = objVerificaCremHistorico.busquedaPersonaJuridicaAeronaveRJB(razonSocial);
						ListadoAsientosPersonasJuridicas = objVerificaCremHistorico.busquedaPersonaJuridicaRJB(razonSocial);
					}
				}
			//}// Fin de Certificado Historico
			
			if (ListadoPartidasRMC.size()>0){
				ListadoTitulosPendientesRMC = objVerificaCremHistorico.busquedaTitulosPendientes(ListadoPartidasRMC, "R");
			}	
			if (ListadoAsientosVehicular.size()>0){
				ListadoTitulosPendientesVehicular = objVerificaCremHistorico.busquedaTitulosPendientes(ListadoAsientosVehicular, "V");
			}	
			if (ListadoAsientosEmbarcacionPesquera.size()>0){
				ListadoTitulosPendientesEmbarcacionPesquera = objVerificaCremHistorico.busquedaTitulosPendientes(ListadoAsientosEmbarcacionPesquera, "E");
			}	
			if (ListadoAsientosBuques.size()>0){
				ListadoTitulosPendientesBuques = objVerificaCremHistorico.busquedaTitulosPendientes(ListadoAsientosBuques, "B");
			}	
			if (ListadoAsientosAeronaves.size()>0){
				ListadoTitulosPendientesAeronaves = objVerificaCremHistorico.busquedaTitulosPendientes(ListadoAsientosAeronaves, "A");
			}	
			if (ListadoAsientosPersonasJuridicas.size()>0){
				ListadoTitulosPendientesPersonasJuridicas = objVerificaCremHistorico.busquedaTitulosPendientes(ListadoAsientosPersonasJuridicas, "J");
			}	
			
			constanciaCremBean.setListadoPartidasRMC(ListadoPartidasRMC);
			constanciaCremBean.setListadoAsientosVehicular(ListadoAsientosVehicular);
			constanciaCremBean.setListadoAsientosEmbarcacionPesquera(ListadoAsientosEmbarcacionPesquera);
			constanciaCremBean.setListadoAsientosBuques(ListadoAsientosBuques);
			constanciaCremBean.setListadoAsientosAeronaves(ListadoAsientosAeronaves);
			constanciaCremBean.setListadoAsientosPersonasJuridicas(ListadoAsientosPersonasJuridicas);
			
			constanciaCremBean.setListadoTitulosPendientesRMC(ListadoTitulosPendientesRMC);
			constanciaCremBean.setListadoTitulosPendientesVehicular(ListadoTitulosPendientesVehicular);
			constanciaCremBean.setListadoTitulosPendientesEmbarcacionPesquera(ListadoTitulosPendientesEmbarcacionPesquera);
			constanciaCremBean.setListadoTitulosPendientesBuques(ListadoTitulosPendientesBuques);
			constanciaCremBean.setListadoTitulosPendientesAeronaves(ListadoTitulosPendientesAeronaves);
			constanciaCremBean.setListadoTitulosPendientesPersonasJuridicas(ListadoTitulosPendientesPersonasJuridicas);
			
		}catch (CustomException e){
			throw e;
		} catch (DBException dbe) {
			throw dbe;
		} catch (Throwable ex) {
			throw ex;
		} 
		
		return constanciaCremBean;
	}
}
