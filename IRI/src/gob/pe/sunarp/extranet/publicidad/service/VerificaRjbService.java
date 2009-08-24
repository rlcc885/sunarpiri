package gob.pe.sunarp.extranet.publicidad.service;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.DetalleRjbBean;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBException;

public interface VerificaRjbService 
{
	public ArrayList recuperarGravamenVehicularRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarGravamenEmbPesqueraRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarGravamenBusqueRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarGravamenAeronaveRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialVehicularRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialEmbPesqueraRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialBuqueRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialAeronaveRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleVehicularRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleEmbPesqueraRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleBuqueRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleAeronaveRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
}
