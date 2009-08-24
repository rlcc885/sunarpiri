package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultarNacionalIndicePartidasBienesSQL 
{
	//Inicio:mgarate:27/08/2007
	public FormOutputBuscarPartida busquedaIndiceBienesSIGC(int medioDeAcceso, InputBusqIndirectaBean inputBusqIndirectaBean, boolean flagUsuarioInterno, String idSession) throws SQLException, CustomException, ValidacionException, DBException,Throwable;
	//Fin:mgarate
	public int contarBusquedaIndiceBienesSIGC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
	
	public FormOutputBuscarPartida busquedaIndiceBienesSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean ,String idSession) throws SQLException, CustomException, ValidacionException, DBException,Throwable;
	
	public int contarBusquedaIndiceBienesSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;

}
