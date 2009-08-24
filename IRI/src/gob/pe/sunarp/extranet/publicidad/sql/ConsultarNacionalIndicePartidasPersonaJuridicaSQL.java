package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;


public interface ConsultarNacionalIndicePartidasPersonaJuridicaSQL
{
	//Inicio:mgarate:27/08/2007
	public FormOutputBuscarPartida busquedaIndicePersonaJuridicaSIGC(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean, boolean flagUsuarioInterno, String idSession ) throws SQLException, CustomException, ValidacionException, DBException;
	//Fin:mgarate
	
	public int contarBusquedaIndicePersonaJuridicaSIGC(InputBusqIndirectaBean inputBusqIndirectaBean ) throws SQLException, CustomException, DBException;
	
	public FormOutputBuscarPartida busquedaIndicePersonaJuridicaSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean,String idSession) throws SQLException, CustomException, ValidacionException, DBException;
	
	public int contarBusquedaIndicePersonaJuridicaSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean ) throws SQLException, CustomException, DBException;
}
