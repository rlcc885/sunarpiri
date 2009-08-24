package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBException;

public interface VerificaPositivoRmcSQL {
	public ArrayList busquedaNumPlacaRMC(String placa)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaNumSerieMatriculaRMC(String numero)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaNumMatriculaNombreRMC(String numMatricula, String nombre)throws SQLException, CustomException, ValidacionException, DBException;
}
