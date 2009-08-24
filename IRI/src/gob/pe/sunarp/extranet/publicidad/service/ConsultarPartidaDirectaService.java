
package gob.pe.sunarp.extranet.publicidad.service;

import java.sql.SQLException;

import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqDirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

public interface ConsultarPartidaDirectaService {
	
	public static final int MEDIO_WEB_SERVICE = 1;
	public static final int MEDIO_CONTROLLER = 0;
	
	public FormOutputBuscarPartida busquedaDirectaPorFichaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public FormOutputBuscarPartida busquedaDirectaPorPartidaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public FormOutputBuscarPartida busquedaDirectaPorTomoFolioRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public FormOutputBuscarPartida busquedaDetallePartidaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	
}
