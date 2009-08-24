package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultaIndicePartidasPersonaJurídicaSQL 
{
	//Inicio:mgarate:27/08/2007
	//se agrego el medio de acceso como argumento para evitar la paginacion cuando la llamada sea por webservice
	public FormOutputBuscarPartida busquedaIndicePersonaJurídicaRmc(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, ValidacionException, DBException,IOException,ClassNotFoundException;
	//Fin:mgarate
	public int ContarBusquedaIndicePersonaJurídicaRMC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
}
