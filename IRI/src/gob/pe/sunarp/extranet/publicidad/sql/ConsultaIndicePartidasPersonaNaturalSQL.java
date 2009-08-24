package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.SQLException;
import com.jcorporate.expresso.core.db.DBException;

public interface ConsultaIndicePartidasPersonaNaturalSQL
{
	//Inicio:mgarate:27/08/2007
	//se agrega un argumento al metodo para reconocer el medio de acceso (EXTRANET/WEBSERVICE) y asi evitar el 
	//pagineo en las llamadas a los webservices
	public FormOutputBuscarPartida busquedaIndicePersonaNaturalRmc(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean , String session_id ) throws SQLException, CustomException, ValidacionException, DBException,IOException,ClassNotFoundException;
	//Fin:mgarate
	public int ContarBusquedaIndicePersonaNaturalRMC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException;
}
