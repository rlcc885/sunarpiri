package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultaIndicePartidasBienesSQL 
{
	//Inicio:mgarate:27/08/2007
	//se agrega el argumento de medio de acceso para evitar la paginacion por web service
	public FormOutputBuscarPartida busquedaIndiceBienesRmc(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, ValidacionException, DBException,IOException,ClassNotFoundException;
	//Fin:mgarate:27/08/2007
	public int contarBusquedaIndiceBienesRMC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
}
