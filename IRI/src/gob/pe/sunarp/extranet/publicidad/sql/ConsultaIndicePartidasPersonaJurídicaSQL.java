package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultaIndicePartidasPersonaJur�dicaSQL 
{
	//Inicio:mgarate:27/08/2007
	//se agrego el medio de acceso como argumento para evitar la paginacion cuando la llamada sea por webservice
	public FormOutputBuscarPartida busquedaIndicePersonaJur�dicaRmc(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, ValidacionException, DBException,IOException,ClassNotFoundException;
	//Fin:mgarate
	public int ContarBusquedaIndicePersonaJur�dicaRMC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
}
