package gob.pe.sunarp.extranet.publicidad.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.service.VerificaGravamenRmcService;
import gob.pe.sunarp.extranet.publicidad.service.VerificaPositivoRmcService;
import gob.pe.sunarp.extranet.publicidad.service.VerificaVigenciaRmcService;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaCremCondicionadoSQL;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaGravamenRmcSQL;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaPositivoRmcSQL;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaVigenciaRmcSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaCremCondicionadoSQLImpl;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaGravamenRmcSQLImpl;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaPositivoRmcSQLImpl;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaVigenciaRmcSQLImpl;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaGravamenRmcServiceImpl extends ServiceImpl implements VerificaGravamenRmcService {
	private Connection conn;
	private DBConnection dbConn;
	
	public VerificaGravamenRmcServiceImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
public ArrayList constanciaTituloCertificadoGravamenRmc(ObjetoSolicitudBean objetoSolicitudBean) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		ArrayList listaPartidas = new ArrayList();
		try{
			
			VerificaGravamenRmcSQL verificaGravamenRmcSQL = new VerificaGravamenRmcSQLImpl(this.conn, this.dbConn);
			if(objetoSolicitudBean.getTpo_pers()!=null&&objetoSolicitudBean.getTpo_pers().trim().equalsIgnoreCase(Constantes.PERSONA_NATURAL)){
				listaPartidas = verificaGravamenRmcSQL.busquedaPersonaNaturalGravamenRMC(objetoSolicitudBean.getNombres(), objetoSolicitudBean.getApe_pat(),objetoSolicitudBean.getApe_mat());
			}else
				if(objetoSolicitudBean.getTpo_pers()!=null && objetoSolicitudBean.getTpo_pers().trim().equalsIgnoreCase(Constantes.PERSONA_JURIDICA)){
					listaPartidas = verificaGravamenRmcSQL.busquedaPersonaJuridicaGravamenRMC(objetoSolicitudBean.getRaz_soc(), objetoSolicitudBean.getSiglas());
				}else
					if(objetoSolicitudBean.getTpo_pers()!=null && objetoSolicitudBean.getTpo_pers().trim().equalsIgnoreCase(Constantes.PERSONA_TIPO_DOCUMENTO)){
						listaPartidas = verificaGravamenRmcSQL.busquedaTipoNumeroDocGravamenRMC(objetoSolicitudBean.getTipoDocumento(),objetoSolicitudBean.getNumeroDocumento());
					}
				
		}catch (CustomException e){
			throw e;
		} catch (DBException dbe) {
			throw dbe;
		} catch (Throwable ex) {
			throw ex;
		} 
		
		return listaPartidas;
	}

}
