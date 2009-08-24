package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultarNacionalIndicePartidasTipoNumeroDocumentoSQL  
{
	//Inicio:mgarate:27/08/2007
	public FormOutputBuscarPartida busquedaIndiceTipoNumeroDocumentoSIGC(int medioDeAcceso ,InputBusqIndirectaBean inputBusqIndirectaBean, boolean flagUsuarioInterno, String idSession ) throws SQLException, CustomException, ValidacionException, DBException,Throwable;
	//Fin:mgarate
	public int contarBusquedaIndiceTipoNumeroDocumentoSIGC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, ValidacionException, DBException;

	public FormOutputBuscarPartida busquedaIndiceTipoNumeroDocumentoSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean , String idSession ) throws SQLException, CustomException, ValidacionException, DBException,Throwable;

	public int contarBusquedaIndiceTipoNumeroDocumentoSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, ValidacionException, DBException;

}

