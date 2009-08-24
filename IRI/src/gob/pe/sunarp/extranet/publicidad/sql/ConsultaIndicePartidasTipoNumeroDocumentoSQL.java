package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultaIndicePartidasTipoNumeroDocumentoSQL 
{
	//Inicio:mgarate:27/08/2007
	//se agrega el medio de acceso para evitar el pagineo cuando se accede por web service
	public FormOutputBuscarPartida busquedaIndiceTipoNumeroDocumentoRmc(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, ValidacionException, DBException,IOException,ClassNotFoundException;
	//Fin:mgarate
	public int ContarBusquedaIndiceTipoNumeroDocumentoRMC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
}
