package gob.pe.sunarp.extranet.publicidad.service.impl;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.publicidad.bean.DetalleRjbBean;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.service.VerificaRjbService;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaRjbSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.VerificaRjbSQLImpl;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class VerificaRjbServiceImpl extends ServiceImpl implements VerificaRjbService
{
	private Connection conn;
	private DBConnection dbConn;
	
	public VerificaRjbServiceImpl(Connection conn, DBConnection dbConn) 
	{
		this.conn = conn;
		this.dbConn = dbConn;
	}
	
	public ArrayList recuperarDominialAeronaveRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarDominialAeronaveRJB(objSol);
		
		return resultado;
	}
	public ArrayList recuperarDominialBuqueRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarDominialBuqueRJB(objSol);
		
		return resultado;
	}
	public ArrayList recuperarDominialEmbPesqueraRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarDominialEmbPesqueraRJB(objSol);
		
		return resultado;
	}
	public ArrayList recuperarDominialVehicularRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarDominialVehicularRJB(objSol);
		
		return resultado;
	}
	public ArrayList recuperarGravamenAeronaveRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarGravamenAeronaveRJB(objSol);
		
		return resultado;
	}
	public ArrayList recuperarGravamenBusqueRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarGravamenBusqueRJB(objSol);
		
		return resultado;
	}
	public ArrayList recuperarGravamenEmbPesqueraRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarGravamenEmbPesqueraRJB(objSol);
		
		return resultado;
	}
	public ArrayList recuperarGravamenVehicularRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		ArrayList resultado  = new ArrayList();
		
		VerificaRjbSQL verifica = new VerificaRjbSQLImpl(conn , dbConn);
		resultado = verifica.recuperarGravamenVehicularRJB(objSol);
		
		return resultado;
	}

	public DetalleRjbBean recuperarDetalleAeronaveRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		DetalleRjbBean  detalle = new DetalleRjbBean();
		
		VerificaRjbSQL resultado = new VerificaRjbSQLImpl(conn , dbConn);
		detalle = resultado.recuperarDetalleAeronaveRJB(sol);
		
		return detalle;
	}

	public DetalleRjbBean recuperarDetalleBuqueRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		DetalleRjbBean  detalle = new DetalleRjbBean();
		
		VerificaRjbSQL resultado = new VerificaRjbSQLImpl(conn , dbConn);
		detalle = resultado.recuperarDetalleBuqueRJB(sol);
		
		return detalle;
	}

	public DetalleRjbBean recuperarDetalleEmbPesqueraRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		DetalleRjbBean  detalle = new DetalleRjbBean();
		
		VerificaRjbSQL resultado = new VerificaRjbSQLImpl(conn , dbConn);
		detalle = resultado.recuperarDetalleEmbPesqueraRJB(sol);
		
		return detalle;
	}

	public DetalleRjbBean recuperarDetalleVehicularRJB(ObjetoSolicitudBean sol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		DetalleRjbBean  detalle = new DetalleRjbBean();
		
		VerificaRjbSQL resultado = new VerificaRjbSQLImpl(conn , dbConn);
		detalle = resultado.recuperarDetalleVehicularRJB(sol);
		
		return detalle;
	}	
}
