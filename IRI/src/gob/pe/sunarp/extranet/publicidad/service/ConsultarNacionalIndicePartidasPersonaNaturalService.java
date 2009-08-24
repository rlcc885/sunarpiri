package gob.pe.sunarp.extranet.publicidad.service;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultarNacionalIndicePartidasPersonaNaturalService 
{
	public static final int MEDIO_WEB_SERVICE = 1;
	public static final int MEDIO_CONTROLLER = 0;
	
	public FormOutputBuscarPartida busquedaIndicePersonaNaturalSIGC(int medioDeAcceso, InputBusqIndirectaBean inputBusqIndirectaBean , UsuarioBean usuario, String ipOrigen , String session_id) throws SQLException, CustomException, ValidacionException, DBException,Throwable;
}
