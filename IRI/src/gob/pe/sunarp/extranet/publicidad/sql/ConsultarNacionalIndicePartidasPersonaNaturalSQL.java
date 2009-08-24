package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultarNacionalIndicePartidasPersonaNaturalSQL
{
	//Inicio:mgarate:27/08/2007
	public FormOutputBuscarPartida busquedaIndicePersonaNaturalSIGC(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean,boolean flagUsuarioInterno ,String idSession) throws SQLException, CustomException, ValidacionException, DBException,IOException, ClassNotFoundException;
	//Fin:mgarate
	public int contarBusquedaIndicePersonaNaturalSIGC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
	
	public FormOutputBuscarPartida busquedaIndicePersonaNaturalSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean , String idSession) throws SQLException, CustomException, ValidacionException, DBException,IOException, ClassNotFoundException;
	public int contarBusquedaIndicePersonaNaturalSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
}
