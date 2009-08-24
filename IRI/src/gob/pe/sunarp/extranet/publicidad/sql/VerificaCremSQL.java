package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBException;

public interface VerificaCremSQL {
	public ArrayList busquedaPersonaNaturalRMC(String apellidoPaterno, String apellidoMaterno, String nombres)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaRMC(String razonSocial)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaNaturalVehicularRJB(String apellidoPaterno, String apellidoMaterno, String nombres,String id_cert)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaVehicularRJB(String razonSocial, String id_cert)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaNaturalEmbarcacionesPesquerasRJB(String apellidoPaterno, String apellidoMaterno, String nombres)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaEmbarcacionesPesquerasRJB(String razonSocial)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaNaturalBuquesRJB(String apellidoPaterno, String apellidoMaterno, String nombres)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaBuquesRJB(String razonSocial)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaNaturalAeronaveRJB(String apellidoPaterno, String apellidoMaterno, String nombres)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaAeronaveRJB(String razonSocial)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaNatural(String apellidoPaterno, String apellidoMaterno, String nombres)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaPersonaJuridicaRJB(String razonSocial)throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList busquedaTitulosPendientes(ArrayList listadoAsientos, String tipo)throws SQLException, CustomException, ValidacionException, DBException;
}
