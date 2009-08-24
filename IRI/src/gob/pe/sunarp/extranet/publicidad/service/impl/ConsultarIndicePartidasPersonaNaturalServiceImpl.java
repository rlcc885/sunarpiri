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
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.publicidad.service.ConsultaIndicePartidasPersonaNaturalService;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultaIndicePartidasPersonaNaturalSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultaIndicePartidasPersonaNaturalSQLImpl;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaBusqPartidaBean;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaConsultaPartidaBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Job004;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class ConsultarIndicePartidasPersonaNaturalServiceImpl extends ServiceImpl implements ConsultaIndicePartidasPersonaNaturalService, Constantes{
	private Connection conn;
	private DBConnection dbConn;
	private DBConnectionFactory pool;
	
	public ConsultarIndicePartidasPersonaNaturalServiceImpl() throws Exception{
		this.pool = DBConnectionFactory.getInstance();
		this.conn = pool.getConnection();
		this.dbConn = new DBConnection(conn);
	}
	
	public ConsultarIndicePartidasPersonaNaturalServiceImpl(DBConnectionFactory pool, Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
		this.pool = pool;
	}
	
	public FormOutputBuscarPartida busquedaIndicePersonaNaturalRMC(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean ,UsuarioBean usuario ,String ipOrigen, String session_id , String amid_session) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
         FormOutputBuscarPartida formOutputBuscarPartida = null;
		try{
			
			ConsultaIndicePartidasPersonaNaturalSQL consultaIndicePartidasPersonaNaturalSQL = new ConsultaIndicePartidasPersonaNaturalSQLImpl(this.conn, this.dbConn);
			//Inicio:mgarate:27/08/2007
			formOutputBuscarPartida = consultaIndicePartidasPersonaNaturalSQL.busquedaIndicePersonaNaturalRmc(medioDeAcceso,inputBusqIndirectaBean, session_id);
			//Fin:mgarate
			//Tarifa								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(this.dbConn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			
			//Se valida si el acceso es via webService o controller, para diferenciar la tarifa
			if(medioDeAcceso == MEDIO_WEB_SERVICE){
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_INDICE_PARTIDA_RMC);
			}else{
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_BUSQ_INDICE_PARTIDA_RMC);
				dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, inputBusqIndirectaBean.getCodGrupoLibroArea());
			}
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, inputBusqIndirectaBean.getCodGrupoLibroArea());
			
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}	
			
			formOutputBuscarPartida.setTarifa(tarifa);
			int busqpartida=-1;
			if (inputBusqIndirectaBean.getFlagPagineo()==false)
			{
				LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();
				bt.setRemoteAddr(ipOrigen);            
				bt.setIdSesion(amid_session);
				bt.setUsuarioSession(usuario);
				
				//Se valida si el acceso es via webService o controller, para diferenciar la auditoria
				if(medioDeAcceso == MEDIO_WEB_SERVICE){
					bt.setTipoBusq(""+Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_INDICE_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_INDICE_PARTIDA_RMC);
				}else{
					bt.setTipoBusq(""+Constantes.SERVICIO_BUSQ_INDICE_PARTIDA_RMC);
					bt.setCodigoServicio(Constantes.SERVICIO_BUSQ_INDICE_PARTIDA_RMC);
				}
				
				bt.setCodigoGLA(Integer.parseInt(inputBusqIndirectaBean.getCodGrupoLibroArea()));
				bt.setPartSeleccionado("PN");
				bt.setTipoBusqPartida(busqpartida);
				bt.setNomApeRazSocPart(inputBusqIndirectaBean.getArea3ParticipanteApePat()+ " " + inputBusqIndirectaBean.getArea3ParticipanteApeMat()+ " " + inputBusqIndirectaBean.getArea3ParticipanteNombre());
				bt.setCodAreaReg(inputBusqIndirectaBean.getComboArea());
					
				/**
				 * inicio:dbravo:27/07/2007
				 * cc:regmobcon-2006
				 * descripcion: la auditoria para RMC va ser para todas las sedes
				 */
				bt.setTipoPersPart("N");
				bt.setNumSedes(Constantes.SEDES_PORDEFECTO_BUSQUEDA_RMC);
				bt.setTipoParticipacion("**");
				/**
				 * fin:dbravo:27/07/2007
				 * cc:regmobcon-2006
				 */
				
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					/**
					 *  inicio, dbravo: 15/06/2007
					 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					 */
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
                        //estamos en la primera llamada
						//enviamos TODOS los registros encontrados
						//a otro Thread para que registre el UsoServicio
						Job004 j = new Job004();
						j.setFormOutputBuscarPartida(formOutputBuscarPartida);
						j.setUsuario(usuario);
						//Se valida si el acceso es via webService o controller, para diferenciar la transacción
						if(medioDeAcceso == MEDIO_WEB_SERVICE){
							j.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQUEDA_INDICE_PARTIDA_RMC);
						}else{
							j.setCodigoServicio(Constantes.SERVICIO_BUSQ_INDICE_PARTIDA_RMC);
						}
						
						j.setCostoServicio(prepagoBean.getMontoBruto());
						
						Thread llamador1 = new Thread(j);
						llamador1.start();
					}
					
			  }
			}
			
			conn.commit();
			if (inputBusqIndirectaBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{				
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),this.dbConn);
				usuario.setSaldo(nuevoSaldo);
			}
		
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
}
