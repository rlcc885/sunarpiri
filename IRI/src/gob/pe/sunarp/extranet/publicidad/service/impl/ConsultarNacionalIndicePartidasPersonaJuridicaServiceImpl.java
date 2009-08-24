package gob.pe.sunarp.extranet.publicidad.service.impl;

import gob.pe.sunarp.extranet.dbobj.DboTarifa;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasPersonaJuridicaService;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarNacionalIndicePartidasPersonaJuridicaSQL;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarNacionalIndicePartidasPersonaNaturalSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultarNacionalIndicePartidasPersonaJuridicaSQLImpl;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultarNacionalIndicePartidasPersonaNaturalSQLImpl;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaBusqPartidaBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Job004;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.sql.Connection;
import java.sql.SQLException;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class ConsultarNacionalIndicePartidasPersonaJuridicaServiceImpl 
extends ServiceImpl implements ConsultarNacionalIndicePartidasPersonaJuridicaService {
	
	private Connection conn;
	private DBConnection dbConn;
	private DBConnectionFactory pool;
	
	
	public ConsultarNacionalIndicePartidasPersonaJuridicaServiceImpl() throws Exception {
		this.pool = DBConnectionFactory.getInstance();
		this.conn = pool.getConnection();
		this.dbConn = new DBConnection(conn);
	}


	public ConsultarNacionalIndicePartidasPersonaJuridicaServiceImpl(Connection conn, DBConnection dbConn, DBConnectionFactory pool) {
		
		this.conn = conn;
		this.dbConn = dbConn;
		this.pool = pool;
	}


	public FormOutputBuscarPartida busquedaIndicePersonaJuridicaSIGC (int medioDeAcceso, InputBusqIndirectaBean inputBusqIndirectaBean, UsuarioBean usuario, String ipOrigen, String idSession)
	throws SQLException, CustomException, ValidacionException, DBException,Throwable {

		FormOutputBuscarPartida output=null;
		FormOutputBuscarPartida outputInterno=null;
		try{
			int conteo=0;
			int conteoInterno=0;
			double tarifa=0;
			ConsultarNacionalIndicePartidasPersonaJuridicaSQL consultarNacionalIndicePartidasPersonaJuridicaSQL;
			consultarNacionalIndicePartidasPersonaJuridicaSQL= new ConsultarNacionalIndicePartidasPersonaJuridicaSQLImpl(this.conn, this.dbConn);
			Propiedades propiedades=Propiedades.getInstance();
			
			//Inicio:mgarate:27/08/2007
			output=consultarNacionalIndicePartidasPersonaJuridicaSQL.busquedaIndicePersonaJuridicaSIGC(medioDeAcceso,inputBusqIndirectaBean,usuario.getFgInterno(), idSession);
			//Fin:mgarate
			if(usuario.getFgInterno() == true){
				outputInterno=consultarNacionalIndicePartidasPersonaJuridicaSQL.busquedaIndicePersonaJuridicaSIGCInterno(inputBusqIndirectaBean, idSession);
				output.setOutputInterno(outputInterno);
			}
			
			DboTarifa dboTarifa = new DboTarifa(dbConn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			if(medioDeAcceso == MEDIO_WEB_SERVICE){
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID,Constantes.SERVICIO_WEBSERVICE_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC);
			}else{
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID,Constantes.SERVICIO_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC);
			}
			dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA, inputBusqIndirectaBean.getCodGrupoLibroArea());

			if (dboTarifa.find())
			{
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}
			output.setTarifa(tarifa);
			if (inputBusqIndirectaBean.getFlagPagineo()==false)
			{
				
				LogAuditoriaBusqPartidaBean bt = new LogAuditoriaBusqPartidaBean();
				bt.setRemoteAddr(ipOrigen);
				bt.setIdSesion(idSession);
				bt.setUsuarioSession(usuario);
				
				if(medioDeAcceso == MEDIO_WEB_SERVICE){
					bt.setCodigoServicio(Constantes.SERVICIO_WEBSERVICE_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC);
				}else{
					bt.setCodigoServicio(Constantes.SERVICIO_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC);
				}
				
				bt.setCodigoGLA(Integer.parseInt(inputBusqIndirectaBean.getCodGrupoLibroArea()));
				bt.setPartSeleccionado("PJ");
				bt.setNomApeRazSocPart(inputBusqIndirectaBean.getArea2Razon1()+ " " + inputBusqIndirectaBean.getArea2Siglas()+ " ");
				bt.setCodAreaReg(inputBusqIndirectaBean.getComboArea());
				bt.setTipoPersPart("J");
				
				/**
				 * inicio:dbravo:27/07/2007
				 * cc:regmobcon-2006
				 * descripcion: la auditoria para RMC va ser para todas las sedes
				 */
				bt.setNumSedes(Constantes.SEDES_PORDEFECTO_BUSQUEDA_RMC);
				bt.setTipoParticipacion("**");
				/**
				 * fin:dbravo:27/07/2007
				 * cc:regmobcon-2006
				 */
				
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						Job004 j = new Job004();
						j.setFormOutputBuscarPartida(output);
						j.setUsuario(usuario);
						j.setCostoServicio(prepagoBean.getMontoBruto());
						j.setCodigoServicio(Constantes.SERVICIO_BUSQ_NACIONAL_INDICE_PARTIDA_SIGC);
						
						Thread llamador1 = new Thread(j);
						llamador1.start();
					
					}
					
					
				}
				conn.commit();
				if (usuario.getFgInterno()==false)
				{
					LineaPrepago lineaCmd = new LineaPrepago();
					double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dbConn);
					usuario.setSaldo(nuevoSaldo);
				}
			}
		}catch (ValidacionException e){
			try{
				rollback(conn);
			} catch (Throwable ex) {
				rollback(conn);	
			}
			throw e;
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
		
		return output;
	
	}
}
