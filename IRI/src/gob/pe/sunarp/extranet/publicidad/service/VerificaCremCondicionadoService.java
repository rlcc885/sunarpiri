package gob.pe.sunarp.extranet.publicidad.service;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;

import com.jcorporate.expresso.core.db.DBException;

public interface VerificaCremCondicionadoService {

	public ConstanciaCremBean comentarioCertificadoCREMCondicionado(ObjetoSolicitudBean objetoSolicitudBean) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	
}
