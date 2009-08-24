package gob.pe.sunarp.extranet.publicidad.sql;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.DetalleRjbBean;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.jcorporate.expresso.core.db.DBException;

public interface VerificaRjbSQL 
{
	public ArrayList recuperarDocumento(String numeroTitulo, String anoTitulo) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap recuperarPersonaNatural(String curPrtc, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException;
	public HashMap recuperarRazonSocial(String curPrtc, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarTipoPersonaGravamen(String refNumPart,String codLibro , String regPubId, String ofiRegId ) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarTipoPersonaDominial(String refNumPart, String codLibro, String numeroTitulo, String anoTitulo, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarTipoPersonaDominialPro(String refNumPart,String codLibro , String numeroTitulo, String anoTitulo, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarGravamenVehicularRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarGravamenEmbPesqueraRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarGravamenBusqueRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarGravamenAeronaveRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialVehicularRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialEmbPesqueraRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialBuqueRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public ArrayList recuperarDominialAeronaveRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleVehicularRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleEmbPesqueraRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleBuqueRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
	public DetalleRjbBean recuperarDetalleAeronaveRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException;
}