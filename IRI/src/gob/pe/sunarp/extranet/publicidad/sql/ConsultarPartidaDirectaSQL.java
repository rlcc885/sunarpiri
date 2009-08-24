package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqDirectaBean;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusquedaBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;

import com.jcorporate.expresso.core.db.DBException;


public interface ConsultarPartidaDirectaSQL {
	
	public int numeroPaginas(String refNumPart) throws SQLException;
	public String obtenerRefNumParNuevo(String refNumParAntiguo) throws SQLException;
	public String obtenerRefNumParAntiguo(String refNumParNuevo) throws SQLException;
	public FormOutputBuscarPartida busquedaDirectaPorFichaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException, DBException;
	public FormOutputBuscarPartida busquedaDirectaPorPartidaRMC(int medioDeAcceso,InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException, DBException;
	public FormOutputBuscarPartida busquedaDirectaPorTomoFolioRMC(int medioDeAcceso,InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException, DBException;
	public PartidaBean busquedaDirectaPorRefNumPartRMC(String refNumPart)throws SQLException, DBException;
	public String buscarActosTitulo(String refNumTitulo) throws SQLException, DBException;
	public FormOutputBuscarPartida busquedaDetallePartidaRMC(InputBusqDirectaBean inputBusquedaBean) throws CustomException, ValidacionException, Throwable;
}
