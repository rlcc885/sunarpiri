package gob.pe.sunarp.extranet.publicidad.service;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.InputPMasivaRelacionalBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jcorporate.expresso.core.db.DBException;

public interface ConsultarPublicidadMasivaRelacionalService 
{
	public ArrayList recuperarAreaRegistral(String servicio) throws SQLException , Throwable;
	public List recuperarTipoEmbPesquera() throws SQLException, Throwable;
	public List recuperarCapitania() throws SQLException, Throwable;
	public List recuperarTipoVehiculo() throws SQLException, Throwable;
	public List recuperarTipoCombustible() throws SQLException, Throwable;
	public List recuperarTipoAeronave() throws SQLException, Throwable;
	public HashMap consultarVehiculoConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarVehiculoDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarEmbarcacionConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarEmbarcacionDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarBuqueConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarBuqueDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarAeronaveConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarAeronaveDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarRMCConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	public HashMap consultarRMCDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable;
	
}
