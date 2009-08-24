package gob.pe.sunarp.extranet.publicidad.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;

import gob.pe.sunarp.extranet.publicidad.service.VerificaCremCondicionadoService;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaCremCondicionadoSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaCremCondicionadoSQLImpl;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaCremCondicionadoServiceImpl extends ServiceImpl implements VerificaCremCondicionadoService{
	
	private Connection conn;
	private DBConnection dbConn;
	
	public VerificaCremCondicionadoServiceImpl(Connection conn, DBConnection dbConn){
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
	public ConstanciaCremBean comentarioCertificadoCREMCondicionado(ObjetoSolicitudBean objetoSolicitudBean) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		ConstanciaCremBean constanciaCremBean = new ConstanciaCremBean();
		try{
			
			VerificaCremCondicionadoSQL verificaCremCondicionadoSQL = new VerificaCremCondicionadoSQLImpl(this.conn, this.dbConn);
			constanciaCremBean = verificaCremCondicionadoSQL.comentarioCertificadoCREMCondicionado(objetoSolicitudBean);
			
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
