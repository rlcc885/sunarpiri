package gob.pe.sunarp.extranet.publicidad.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.dbobj.DboTarifa;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqDirectaBean;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarPartidaDirectaService;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultarPartidaDirectaSQLImpl;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaConsultaPartidaBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Job004;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.Tarea;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class ConsultarPartidaDirectaServiceImpl extends ServiceImpl implements ConsultarPartidaDirectaService, Constantes{

	private Connection conn;
	private DBConnection dbConn;
	private DBConnectionFactory pool;
	
	public ConsultarPartidaDirectaServiceImpl() throws Exception{
		this.pool = DBConnectionFactory.getInstance();
		this.conn = pool.getConnection();
		this.dbConn = new DBConnection(conn);
	}
	
	public ConsultarPartidaDirectaServiceImpl(DBConnectionFactory pool, Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
		this.pool = pool;
	}
	
	/**
	 * @autor Daniel Bravo
	 * @param medio
	 * @param inputBusqDirectaBean
	 * @param usuario
	 * @param ipOrigen
	 * @return FormOutputBuscarPartida
	 * @throws SQLException
	 * @throws CustomException
	 * @throws ValidacionException
	 * @throws DBException
	 * @throws Throwable
	 */
	public FormOutputBuscarPartida busquedaDirectaPorFichaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		FormOutputBuscarPartida formOutputBuscarPartida = null;
		
		try{
			
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQL = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			formOutputBuscarPartida = consultarPartidaDirectaSQL.busquedaDirectaPorFichaRMC(medioDeAcceso, inputBusqDirectaBean);
			
			//Tarifa								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(this.dbConn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			
			//Se valida si el acceso es via webService o controller, para diferenciar la tarifa
			if(medioDeAcceso == MEDIO_WEB_SERVICE){
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
			}else{
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
			}
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, inputBusqDirectaBean.getCodGrupoLibroArea());
			
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}	
			
			formOutputBuscarPartida.setTarifa(tarifa);
			
			if (inputBusqDirectaBean.getFlagPagineo()==false)
			{
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				bt.setRemoteAddr(ipOrigen);             
				bt.setUsuarioSession(usuario);
				
				//Se valida si el acceso es via webService o controller, para diferenciar la auditoria
				if(medioDeAcceso == MEDIO_WEB_SERVICE){
					bt.setTipoBusq(""+Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
				}else{
					bt.setTipoBusq(""+Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
				}
				
				bt.setCodigoGLA(Integer.parseInt(inputBusqDirectaBean.getCodGrupoLibroArea()));
				//TODO: Validar
				bt.setTipoConsPartida("2");
				bt.setLibTomFol(null);
				bt.setNumPartFic(inputBusqDirectaBean.getNumeroFicha());
				bt.setOficRegId(inputBusqDirectaBean.getOficRegId());
				bt.setRegPubId(inputBusqDirectaBean.getRegPubId());
	
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						Job004 j = new Job004();
						j.setFormOutputBuscarPartida(formOutputBuscarPartida);
						j.setUsuario(usuario);
						//Se valida si el acceso es via webService o controller, para diferenciar la transacción
						if(medioDeAcceso == MEDIO_WEB_SERVICE){
							j.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
						}else{
							j.setCodigoServicio(Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
						}
						
						j.setCostoServicio(prepagoBean.getMontoBruto());
						
						Thread llamador1 = new Thread(j);
						llamador1.start();
					}
					
			  }
			}
			
			if (inputBusqDirectaBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{				
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),this.dbConn);
				usuario.setSaldo(nuevoSaldo);
			}
			
			conn.commit();
			
		}catch (ValidacionException e) {
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			throw e;
			
		} catch (CustomException e){
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			
			throw e;
		} catch (DBException dbe) {
			rollback(conn);
			throw dbe;
		} catch (Throwable ex) {
			rollback(conn);
			throw ex;
		} finally {
			pool.release(conn);
		}
		
		
		return formOutputBuscarPartida;
	}
	
	/**
	 * @autor Daniel Bravo
	 * @param medio
	 * @param inputBusqDirectaBean
	 * @param usuario
	 * @param ipOrigen
	 * @return FormOutputBuscarPartida
	 * @throws SQLException
	 * @throws CustomException
	 * @throws ValidacionException
	 * @throws DBException
	 * @throws Throwable
	 */
	public FormOutputBuscarPartida busquedaDirectaPorPartidaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		FormOutputBuscarPartida formOutputBuscarPartida = null;
		
		try{
			
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQL = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			formOutputBuscarPartida = consultarPartidaDirectaSQL.busquedaDirectaPorPartidaRMC( medioDeAcceso ,inputBusqDirectaBean);
			
			//Tarifa								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(this.dbConn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			
			//Se valida si el acceso es via webService o controller, para diferenciar la tarifa
			if(medioDeAcceso == MEDIO_WEB_SERVICE){
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
			}else{
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
			}
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, inputBusqDirectaBean.getCodGrupoLibroArea());
			
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}	
			
			formOutputBuscarPartida.setTarifa(tarifa);
			
			if (inputBusqDirectaBean.getFlagPagineo()==false)
			{
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				bt.setRemoteAddr(ipOrigen);             
				bt.setUsuarioSession(usuario);
				
				//Se valida si el acceso es via webService o controller, para diferenciar la auditoria
				if(medioDeAcceso == MEDIO_WEB_SERVICE){
					bt.setTipoBusq(""+Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
				}else{
					bt.setTipoBusq(""+Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
				}
				
				bt.setCodigoGLA(Integer.parseInt(inputBusqDirectaBean.getCodGrupoLibroArea()));
				//TODO: Validar
				bt.setTipoConsPartida("1");
				bt.setLibTomFol(null);
				bt.setNumPartFic(inputBusqDirectaBean.getNumeroPartida());
				bt.setOficRegId(inputBusqDirectaBean.getOficRegId());
				bt.setRegPubId(inputBusqDirectaBean.getRegPubId());
	
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						Job004 j = new Job004();
						j.setFormOutputBuscarPartida(formOutputBuscarPartida);
						j.setUsuario(usuario);
						//Se valida si el acceso es via webService o controller, para diferenciar la transacción
						if(medioDeAcceso == MEDIO_WEB_SERVICE){
							j.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
						}else{
							j.setCodigoServicio(Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
						}
						
						j.setCostoServicio(prepagoBean.getMontoBruto());
						
						Thread llamador1 = new Thread(j);
						llamador1.start();
					}
					
			  }
			}
			
			if (inputBusqDirectaBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{				
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),this.dbConn);
				usuario.setSaldo(nuevoSaldo);
			}
			
			conn.commit();
			
		}catch (ValidacionException e) {
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			throw e;
		} catch (CustomException e){
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			
			throw e;
		} catch (DBException dbe) {
			rollback(conn);
			throw dbe;
		} catch (Throwable ex) {
			rollback(conn);
			throw ex;
		} finally {
			pool.release(conn);
		}
		
		
		return formOutputBuscarPartida;
	}
	
	/**
	 * @autor Daniel Bravo
	 * @param medio
	 * @param inputBusqDirectaBean
	 * @param usuario
	 * @param ipOrigen
	 * @return FormOutputBuscarPartida
	 * @throws SQLException
	 * @throws CustomException
	 * @throws ValidacionException
	 * @throws DBException
	 * @throws Throwable
	 */
	public FormOutputBuscarPartida busquedaDirectaPorTomoFolioRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		FormOutputBuscarPartida formOutputBuscarPartida = null;
		
		try{
			
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQL = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			formOutputBuscarPartida = consultarPartidaDirectaSQL.busquedaDirectaPorTomoFolioRMC( medioDeAcceso ,inputBusqDirectaBean);
			
			//Tarifa								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(this.dbConn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			
			//Se valida si el acceso es via webService o controller, para diferenciar la tarifa
			if(medioDeAcceso == MEDIO_WEB_SERVICE){
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
			}else{
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
			}
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, inputBusqDirectaBean.getCodGrupoLibroArea());
			
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}	
			
			formOutputBuscarPartida.setTarifa(tarifa);
			
			if (inputBusqDirectaBean.getFlagPagineo()==false)
			{
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				bt.setRemoteAddr(ipOrigen);             
				bt.setUsuarioSession(usuario);
				
				//Se valida si el acceso es via webService o controller, para diferenciar la auditoria
				if(medioDeAcceso == MEDIO_WEB_SERVICE){
					bt.setTipoBusq(""+Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
				}else{
					bt.setTipoBusq(""+Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
				}
				
				bt.setCodigoGLA(Integer.parseInt(inputBusqDirectaBean.getCodGrupoLibroArea()));
				//TODO: Validar
				bt.setTipoConsPartida("3");
				bt.setLibTomFol(inputBusqDirectaBean.getLibro()+"|"+inputBusqDirectaBean.getTomo()+"|"+inputBusqDirectaBean.getFolio());
				bt.setOficRegId(inputBusqDirectaBean.getOficRegId());
				bt.setRegPubId(inputBusqDirectaBean.getRegPubId());
	
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						Job004 j = new Job004();
						j.setFormOutputBuscarPartida(formOutputBuscarPartida);
						j.setUsuario(usuario);
						//Se valida si el acceso es via webService o controller, para diferenciar la transacción
						if(medioDeAcceso == MEDIO_WEB_SERVICE){
							j.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_DIRECTA_PARTIDA_RMC);
						}else{
							j.setCodigoServicio(Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
						}
						
						j.setCostoServicio(prepagoBean.getMontoBruto());
						
						Thread llamador1 = new Thread(j);
						llamador1.start();
					}
			  }
			}
			
			if (inputBusqDirectaBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{				
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),this.dbConn);
				usuario.setSaldo(nuevoSaldo);
			}
			
			conn.commit();
			
		}catch (ValidacionException e) {
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			throw e;
		} catch (CustomException e){
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			
			throw e;
		} catch (DBException dbe) {
			rollback(conn);
			throw dbe;
		} catch (Throwable ex) {
			rollback(conn);
			throw ex;
		} finally {
			pool.release(conn);
		}
		
		
		return formOutputBuscarPartida;
	}
	
	public FormOutputBuscarPartida busquedaDetallePartidaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		if(medioDeAcceso==MEDIO_WEB_SERVICE)
		{
			String refnum_part = Tarea.recuperarRefnum_part(inputBusqDirectaBean.getNumeroPartida(), inputBusqDirectaBean.getRegPubId(), inputBusqDirectaBean.getOficRegId(), inputBusqDirectaBean.getArea_reg_id(), this.conn);
			if(refnum_part==null || refnum_part.equals(""))
			{
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}else
			{
				inputBusqDirectaBean.setRefNumPart(refnum_part);
			}
		}
		
		FormOutputBuscarPartida formOutputBuscarPartida = new FormOutputBuscarPartida();
		
		try{
			
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQL = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			formOutputBuscarPartida = consultarPartidaDirectaSQL.busquedaDetallePartidaRMC(inputBusqDirectaBean);
			
			//Tarifa								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(this.dbConn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			
			//Se valida si el acceso es via webService o controller, para diferenciar la tarifa
			if(medioDeAcceso == MEDIO_WEB_SERVICE){
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_WEBSERVICE_DETALLE_PARTIDA_RMC);
			}else{
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_DETALLE_PARTIDA_RMC);
			}
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC);
			
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}	
			
			formOutputBuscarPartida.setTarifa(tarifa);
			
			if (inputBusqDirectaBean.getFlagPagineo()==false)
			{
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				bt.setRemoteAddr(ipOrigen);             
				bt.setUsuarioSession(usuario);
				
				//Se valida si el acceso es via webService o controller, para diferenciar la auditoria
				if(medioDeAcceso == MEDIO_WEB_SERVICE){
					bt.setTipoBusq(""+Constantes.SERVICIO_WEBSERVICE_DETALLE_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_DETALLE_PARTIDA_RMC);
				}else{
					bt.setTipoBusq(""+Constantes.SERVICIO_DETALLE_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.SERVICIO_DETALLE_PARTIDA_RMC);
				}
				
				bt.setCodigoGLA(Integer.parseInt(Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC));
				//TODO: Validar
				bt.setTipoConsPartida("1");
				bt.setLibTomFol(null);
				bt.setNumPartFic(formOutputBuscarPartida.getPartidaBean().getNumPartida());
				bt.setOficRegId(formOutputBuscarPartida.getPartidaBean().getOficRegId());
				bt.setRegPubId(formOutputBuscarPartida.getPartidaBean().getRegPubId());
	
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						Job004 j = new Job004();
						j.setFormOutputBuscarPartida(formOutputBuscarPartida);
						j.setUsuario(usuario);
						//Se valida si el acceso es via webService o controller, para diferenciar la transacción
						if(medioDeAcceso == MEDIO_WEB_SERVICE){
							j.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_DETALLE_PARTIDA_RMC);
						}else{
							j.setCodigoServicio(Constantes.SERVICIO_DETALLE_PARTIDA_RMC);
						}
						
						j.setCostoServicio(prepagoBean.getMontoBruto());
						
						Thread llamador1 = new Thread(j);
						llamador1.start();
					}
					
			  }
			}
			
			if (inputBusqDirectaBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{				
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),this.dbConn);
				usuario.setSaldo(nuevoSaldo);
			}
			
			conn.commit();
			
		}catch (CustomException e){
			
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			
			throw e;
		} catch (DBException dbe) {
			rollback(conn);
			throw dbe;
		} catch (Throwable ex) {
			rollback(conn);
			throw ex;
		} finally {
			pool.release(conn);
		}
		
		
		return formOutputBuscarPartida;
	}
	
}
