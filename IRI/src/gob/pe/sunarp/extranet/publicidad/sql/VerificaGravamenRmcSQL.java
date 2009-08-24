package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBException;

public interface VerificaGravamenRmcSQL {
	
	public ArrayList busquedaPersonaNaturalGravamenRMC(String nombre,String apePat, String apeMat)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaGravamenRMC(String rasonSocial,String siglas)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaTipoNumeroDocGravamenRMC(String tipoDoc,String numDoc)throws SQLException, CustomException, ValidacionException, DBException;


}
