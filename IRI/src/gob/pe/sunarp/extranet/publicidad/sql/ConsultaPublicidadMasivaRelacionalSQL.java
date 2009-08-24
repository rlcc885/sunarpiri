package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.InputPMasivaRelacionalBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.jcorporate.expresso.core.db.DBException;

public interface ConsultaPublicidadMasivaRelacionalSQL 
{
	public HashMap consultarVehiculoConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarVehiculoDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarEmbarcacionConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarEmbarcacionDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarBuqueConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarBuqueDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarAeronaveConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarAeronaveDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarRMCConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap consultarRMCDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap recuperarPropietario(String registro, String refnumpart) throws SQLException, CustomException, ValidacionException, DBException;
}
