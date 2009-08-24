package gob.pe.sunarp.extranet.publicidad.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.service.VerificaPositivoRmcService;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaCremCondicionadoSQL;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaPositivoRmcSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaCremCondicionadoSQLImpl;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaPositivoRmcSQLImpl;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaPositivoRmcServiceImpl extends ServiceImpl implements VerificaPositivoRmcService {
	private Connection conn;
	private DBConnection dbConn;
	
	public VerificaPositivoRmcServiceImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
public ArrayList constanciaTituloCertificadoPositivoRmc(ObjetoSolicitudBean objetoSolicitudBean) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		ArrayList listaBien = new ArrayList();
		try{
			
			VerificaPositivoRmcSQL verificaPositivoRmcSQL = new VerificaPositivoRmcSQLImpl(this.conn, this.dbConn);
			
			if(objetoSolicitudBean.getPlaca()!=null&&objetoSolicitudBean.getPlaca().trim().length()>0){
				listaBien = verificaPositivoRmcSQL.busquedaNumPlacaRMC(objetoSolicitudBean.getPlaca());
			}else
				if((objetoSolicitudBean.getNumeroMatricula()!=null && objetoSolicitudBean.getNumeroMatricula().trim().length()>0) && (objetoSolicitudBean.getNombreBien()!=null&& objetoSolicitudBean.getNombreBien().trim().length()>0)){
					listaBien = verificaPositivoRmcSQL.busquedaNumMatriculaNombreRMC(objetoSolicitudBean.getNumeroMatricula(),objetoSolicitudBean.getNombreBien());
				}else
					if(objetoSolicitudBean.getNumeroMatricula()!=null && objetoSolicitudBean.getNumeroMatricula().trim().length()>0){
						listaBien = verificaPositivoRmcSQL.busquedaNumSerieMatriculaRMC(objetoSolicitudBean.getNumeroMatricula());
					}else
						if(objetoSolicitudBean.getNumeroSerie()!=null && objetoSolicitudBean.getNumeroSerie().trim().length()>0){
							listaBien = verificaPositivoRmcSQL.busquedaNumSerieMatriculaRMC(objetoSolicitudBean.getNumeroSerie());
						}
				
		}catch (CustomException e){
			throw e;
		} catch (DBException dbe) {
			throw dbe;
		} catch (Throwable ex) {
			throw ex;
		} 
		
		return listaBien;
	}

}
