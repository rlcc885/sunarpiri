package gob.pe.sunarp.extranet.publicidad.service.impl;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboRegisPublico;
import gob.pe.sunarp.extranet.dbobj.DboTmCapitania;
import gob.pe.sunarp.extranet.dbobj.DboTmDocIden;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoAeronave;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoComb;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoEmbPesquera;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoVehi;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.bean.InputPMasivaRelacionalBean;
import gob.pe.sunarp.extranet.publicidad.bean.OutputPMasivaRelacionalBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.CriterioBean;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarPublicidadMasivaRelacionalService;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultaPublicidadMasivaRelacionalSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultaPublicidadMasivaRelacionalSQLImpl;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaBusquedaMasivaRelacional;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaFormatter;
import gob.pe.sunarp.extranet.util.Job004;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.Tarea;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class ConsultarPublicidadMasivaRelacionalServiceImp extends ServiceImpl implements ConsultarPublicidadMasivaRelacionalService 
{
	private Connection conn;
	private DBConnection dbConn;
	private DBConnectionFactory pool;
	
	private final int BUSQUEDA_VEHICULAR = 0; 
	private final int BUSQUEDA_BUSQUE = 1;
	private final int BUSQUEDA_AERONAVES = 2;
	private final int BUSQUEDA_EMB_PESQUERAS = 3;
	private final int BUSQUEDA_RMC = 4;
	
	public ConsultarPublicidadMasivaRelacionalServiceImp() throws Exception
	{
		this.pool = DBConnectionFactory.getInstance();
		this.conn = pool.getConnection();
		this.dbConn = new DBConnection(conn);
	}
	
	public HashMap consultarAeronaveConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		
		HashMap map = new HashMap();
		
		try{
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			double precio;
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			System.out.println("inputPMasivaRelacionalBean.getFlagRespuesta(): "+inputPMasivaRelacionalBean.getFlagRespuesta());
			if(inputPMasivaRelacionalBean.getFlagRespuesta() == null)
			{
				ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
				beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
				inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
				inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
				resultado = consultar.consultarAeronaveConsolidadoPMR(inputPMasivaRelacionalBean);
				resultado1 = (ArrayList)resultado.get("resultado");
				map.put("resultado",resultado1);
				for(int i =0;i<resultado1.size();i++)
				{
					cantidad+=Integer.parseInt(((OutputPMasivaRelacionalBean)resultado1.get(i)).getCantidadRegistros());
				}
				if(cantidad == 0)
				{
					throw new ValidacionException("No se encontraron resultados para su búsqueda");
				}
				
				PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
				precio = prepagoBean.getMontoBruto();
				NumberFormat formateo = new DecimalFormat(".00");
				precio = Double.parseDouble(formateo.format(precio));
				//Verificar Si el Usuario Tiene Saldo 
				// 0 No posee Saldo Suficiente
				// 1 Posee Saldo Suficiente
				if(usuario.getSaldo()<precio)
				{
					inputPMasivaRelacionalBean.setSaldo("0");
				}else
				{
					inputPMasivaRelacionalBean.setSaldo("1");
				}
				
				map.put("precio",precio);
				map.put("cantidad",cantidad);
				
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(cantidad));
				inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
				inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			}else
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					transaccion(this.BUSQUEDA_AERONAVES, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		return map;
	}
	public HashMap consultarAeronaveDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable 
	{
		HashMap map = new HashMap();
		
		try{
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			String cantidadRegistroPago = "";
			double precio;
			
			if(!(inputPMasivaRelacionalBean.getCantidadRegistros()==null) && !(inputPMasivaRelacionalBean.getCantidadRegistros().equals("")) )
			{
				cantidadRegistroPago = inputPMasivaRelacionalBean.getCantidadRegistros();
			}
			
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
			beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
			inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
			inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
			resultado = consultar.consultarAeronaveDetalladoPMR(inputPMasivaRelacionalBean);
			resultado1 = (ArrayList)resultado.get("resultado");
			map.put("resultado",resultado1);
			if(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros()!=null)
			{
				cantidad = Long.parseLong(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros());
			}else
			{
				cantidad=resultado1.size();
			}
			if(cantidad == 0)
			{
				throw new ValidacionException("No se encontraron resultados para su búsqueda");
			}
			
			PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
			precio = prepagoBean.getMontoBruto();
			NumberFormat formateo = new DecimalFormat(".00");
			precio = Double.parseDouble(formateo.format(precio));
			//Verificar Si el Usuario Tiene Saldo 
			// 0 No posee Saldo Suficiente
			// 1 Posee Saldo Suficiente
			if(usuario.getSaldo()<precio)
			{
				inputPMasivaRelacionalBean.setSaldo("0");
			}else
			{
				inputPMasivaRelacionalBean.setSaldo("1");
			}
			
			map.put("precio",precio);
			map.put("cantidad",cantidad);
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta() != null)
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					inputPMasivaRelacionalBean.setCantidadRegistros(cantidadRegistroPago);
					//inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
					inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
					transaccion(this.BUSQUEDA_AERONAVES, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		return map;
	}
	public HashMap consultarBuqueConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable 
	{
		HashMap map = new HashMap();
	
		try{
			
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			double precio;
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta() == null)
			{
				ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
				beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
				inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
				inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
				resultado = consultar.consultarBuqueConsolidadoPMR(inputPMasivaRelacionalBean);
				resultado1 = (ArrayList)resultado.get("resultado");
				map.put("resultado",resultado1);
				for(int i =0;i<resultado1.size();i++)
				{
					cantidad+=Integer.parseInt(((OutputPMasivaRelacionalBean)resultado1.get(i)).getCantidadRegistros());
				}
				if(cantidad == 0)
				{
					throw new ValidacionException("No se encontraron resultados para su búsqueda");
				}
				
				PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
				precio = prepagoBean.getMontoBruto();
				NumberFormat formateo = new DecimalFormat(".00");
				precio = Double.parseDouble(formateo.format(precio));
				//Verificar Si el Usuario Tiene Saldo 
				// 0 No posee Saldo Suficiente
				// 1 Posee Saldo Suficiente
				if(usuario.getSaldo()<precio)
				{
					inputPMasivaRelacionalBean.setSaldo("0");
				}else
				{
					inputPMasivaRelacionalBean.setSaldo("1");
				}
				
				map.put("precio",precio);
				map.put("cantidad",cantidad);
				
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(cantidad));
				inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
				inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
				
			}else
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					transaccion(this.BUSQUEDA_BUSQUE, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		return map;
	}
	public HashMap consultarBuqueDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable
	{
		HashMap map = new HashMap();	
		
		try{
			
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			String cantidadRegistroPago = "";
			double precio;
			
			if(!(inputPMasivaRelacionalBean.getCantidadRegistros()==null) && !(inputPMasivaRelacionalBean.getCantidadRegistros().equals("")) )
			{
				cantidadRegistroPago = inputPMasivaRelacionalBean.getCantidadRegistros();
			}
			
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
			beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
			inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
			inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
			resultado = consultar.consultarBuqueDetalladoPMR(inputPMasivaRelacionalBean);
			resultado1 = (ArrayList)resultado.get("resultado");
			map.put("resultado",resultado1);
			if(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros()!=null)
			{
				cantidad = Long.parseLong(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros());
			}else
			{
				cantidad=resultado1.size();
			}
			if(cantidad == 0)
			{
				throw new ValidacionException("No se encontraron resultados para su búsqueda");
			}
			
			PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
			precio = prepagoBean.getMontoBruto();
			NumberFormat formateo = new DecimalFormat(".00");
			precio = Double.parseDouble(formateo.format(precio));
			//Verificar Si el Usuario Tiene Saldo 
			// 0 No posee Saldo Suficiente
			// 1 Posee Saldo Suficiente
			if(usuario.getSaldo()<precio)
			{
				inputPMasivaRelacionalBean.setSaldo("0");
			}else
			{
				inputPMasivaRelacionalBean.setSaldo("1");
			}
			
			map.put("precio",precio);
			map.put("cantidad",cantidad);
				
			if(inputPMasivaRelacionalBean.getFlagRespuesta() != null)
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					inputPMasivaRelacionalBean.setCantidadRegistros(cantidadRegistroPago);
					//inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
					inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
					transaccion(this.BUSQUEDA_BUSQUE, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		return map;
	}
	public HashMap consultarEmbarcacionConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		HashMap map = new HashMap();
		
		try{
		
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			double precio;
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta() == null)
			{
				ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
				beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
				inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
				inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
				resultado = consultar.consultarEmbarcacionConsolidadoPMR(inputPMasivaRelacionalBean);
				resultado1 = (ArrayList)resultado.get("resultado");
				map.put("resultado",resultado1);
				for(int i =0;i<resultado1.size();i++)
				{
					cantidad+=Integer.parseInt(((OutputPMasivaRelacionalBean)resultado1.get(i)).getCantidadRegistros());
				}
				if(cantidad == 0)
				{
					throw new ValidacionException("No se encontraron resultados para su búsqueda");
				}
				
				PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
				precio = prepagoBean.getMontoBruto();
				NumberFormat formateo = new DecimalFormat(".00");
				precio = Double.parseDouble(formateo.format(precio));
				//Verificar Si el Usuario Tiene Saldo 
				// 0 No posee Saldo Suficiente
				// 1 Posee Saldo Suficiente
				if(usuario.getSaldo()<precio)
				{
					inputPMasivaRelacionalBean.setSaldo("0");
				}else
				{
					inputPMasivaRelacionalBean.setSaldo("1");
				}
				
				map.put("precio",precio);
				map.put("cantidad",cantidad);
				
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(cantidad));
				inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
				inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
				
			}else
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					transaccion(this.BUSQUEDA_EMB_PESQUERAS, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		return map;
	}
	public HashMap consultarEmbarcacionDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		HashMap map = new HashMap();
		
		try{
			
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			String cantidadRegistroPago="";
			double precio;
			
			if(!(inputPMasivaRelacionalBean.getCantidadRegistros()==null) && !(inputPMasivaRelacionalBean.getCantidadRegistros().equals("")) )
			{
				cantidadRegistroPago = inputPMasivaRelacionalBean.getCantidadRegistros();
			}
			
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
			beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
			inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
			inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
			resultado = consultar.consultarEmbarcacionDetalladoPMR(inputPMasivaRelacionalBean);
			resultado1 = (ArrayList)resultado.get("resultado");
			map.put("resultado",resultado1);
			if(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros()!=null)
			{
				cantidad = Long.parseLong(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros());
			}else
			{
				cantidad=resultado1.size();
			}
			if(cantidad == 0)
			{
				throw new ValidacionException("No se encontraron resultados para su búsqueda");
			}

			PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
			precio = prepagoBean.getMontoBruto();
			NumberFormat formateo = new DecimalFormat(".00");
			precio = Double.parseDouble(formateo.format(precio));
			//Verificar Si el Usuario Tiene Saldo 
			// 0 No posee Saldo Suficiente
			// 1 Posee Saldo Suficiente
			if(usuario.getSaldo()<precio)
			{
				inputPMasivaRelacionalBean.setSaldo("0");
			}else
			{
				inputPMasivaRelacionalBean.setSaldo("1");
			}
			
			map.put("precio",precio);
			map.put("cantidad",cantidad);
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta() != null)
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					inputPMasivaRelacionalBean.setCantidadRegistros(cantidadRegistroPago);
					//inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
					inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
					transaccion(this.BUSQUEDA_EMB_PESQUERAS, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		
		return map;
	}
	public HashMap consultarRMCConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		HashMap map = new HashMap();

		try{
		
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			double precio;
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta() == null)
			{
				ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
				beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
				inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
				inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
				resultado = consultar.consultarRMCConsolidadoPMR(inputPMasivaRelacionalBean);
				resultado1 = (ArrayList)resultado.get("resultado");
				map.put("resultado",resultado1);
				for(int i =0;i<resultado1.size();i++)
				{
					cantidad+=Integer.parseInt(((OutputPMasivaRelacionalBean)resultado1.get(i)).getCantidadRegistros());
				}
				if(cantidad == 0)
				{
					throw new ValidacionException("No se encontraron resultados para su búsqueda");
				}
				
				PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
				precio = prepagoBean.getMontoBruto();
				NumberFormat formateo = new DecimalFormat(".00");
				precio = Double.parseDouble(formateo.format(precio));
				//Verificar Si el Usuario Tiene Saldo 
				// 0 No posee Saldo Suficiente
				// 1 Posee Saldo Suficiente
				if(usuario.getSaldo()<precio)
				{
					inputPMasivaRelacionalBean.setSaldo("0");
				}else
				{
					inputPMasivaRelacionalBean.setSaldo("1");
				}
				
				map.put("precio",precio);
				map.put("cantidad",cantidad);
				
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(cantidad));
				inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
				inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
				
			}else
			{
				
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					transaccion(this.BUSQUEDA_RMC, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
				
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
		return map;
	}
	public HashMap consultarRMCDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		HashMap map = new HashMap();

		try{
		
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			String cantidadRegistroPago = "";
			double precio;
			
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			if(!(inputPMasivaRelacionalBean.getCantidadRegistros()==null) && !(inputPMasivaRelacionalBean.getCantidadRegistros().equals("")) )
			{
				cantidadRegistroPago = inputPMasivaRelacionalBean.getCantidadRegistros();
			}
			
			ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
			beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
			inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
			inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
			resultado = consultar.consultarRMCDetalladoPMR(inputPMasivaRelacionalBean);
			resultado1 = (ArrayList)resultado.get("resultado");
			map.put("resultado",resultado1);
			if(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros()!=null)
			{
				cantidad = Long.parseLong(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros());
			}else
			{
				cantidad=resultado1.size();
			}
			if(cantidad == 0)
			{
				throw new ValidacionException("No se encontraron resultados para su búsqueda");
			}
			
			PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
			precio = prepagoBean.getMontoBruto();
			NumberFormat formateo = new DecimalFormat(".00");
			precio = Double.parseDouble(formateo.format(precio));
			//Verificar Si el Usuario Tiene Saldo 
			// 0 No posee Saldo Suficiente
			// 1 Posee Saldo Suficiente
			if(usuario.getSaldo()<precio)
			{
				inputPMasivaRelacionalBean.setSaldo("0");
			}else
			{
				inputPMasivaRelacionalBean.setSaldo("1");
			}
			
			map.put("precio",precio);
			map.put("cantidad",cantidad);	
				
			if(inputPMasivaRelacionalBean.getFlagRespuesta() != null)
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null)
				{
					inputPMasivaRelacionalBean.setCantidadRegistros(cantidadRegistroPago);
					//inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
					inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
					transaccion(this.BUSQUEDA_RMC, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
				
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
		
		return map;
	}
	public HashMap consultarVehiculoConsolidado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		HashMap map = new HashMap();

		try{
		
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			double precio;
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta() == null)
			{
				ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
				beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
				inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
				inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
				resultado = consultar.consultarVehiculoConsolidadoPMR(inputPMasivaRelacionalBean);
				resultado1 = (ArrayList)resultado.get("resultado");
				map.put("resultado",resultado1);
				for(int i =0;i<resultado1.size();i++)
				{
					cantidad+=Integer.parseInt(((OutputPMasivaRelacionalBean)resultado1.get(i)).getCantidadRegistros());
				}
				if(cantidad == 0)
				{
					throw new ValidacionException("No se encontraron resultados para su búsqueda");
				}

				PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
				precio = prepagoBean.getMontoBruto();
				NumberFormat formateo = new DecimalFormat(".00");
				precio = Double.parseDouble(formateo.format(precio));
				//Verificar Si el Usuario Tiene Saldo 
				// 0 No posee Saldo Suficiente
				// 1 Posee Saldo Suficiente
				if(usuario.getSaldo()<precio)
				{
					inputPMasivaRelacionalBean.setSaldo("0");
				}else
				{
					inputPMasivaRelacionalBean.setSaldo("1");
				}
				
				map.put("precio",precio);
				map.put("cantidad",cantidad);
				
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(cantidad));
				inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
				inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
				
			}else
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null){
					transaccion(this.BUSQUEDA_VEHICULAR, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		
		return map;
	}
	public HashMap consultarVehiculoDetallado(InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen) throws SQLException, CustomException, ValidacionException, DBException, Throwable
	{
		HashMap map = new HashMap();
		
		try{
		
			HashMap resultado = null;
			ArrayList resultado1 = null;
			long cantidad = 0;
			double precio;
			String cantidadRegistroPago = "";
			OutputPMasivaRelacionalBean bean = new OutputPMasivaRelacionalBean();
			CriterioBean beanCriterio = new CriterioBean();
			
			if(!(inputPMasivaRelacionalBean.getCantidadRegistros()==null) && !(inputPMasivaRelacionalBean.getCantidadRegistros().equals("")) )
			{
				cantidadRegistroPago = inputPMasivaRelacionalBean.getCantidadRegistros();
			}
			
			ConsultaPublicidadMasivaRelacionalSQL consultar = new ConsultaPublicidadMasivaRelacionalSQLImpl(this.conn, this.dbConn);
			beanCriterio = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
			inputPMasivaRelacionalBean.setSedesElegidas(beanCriterio.getSedesElegidas());
			inputPMasivaRelacionalBean.setSedesSQLString(beanCriterio.getSedesSQLString());
			resultado = consultar.consultarVehiculoDetalladoPMR(inputPMasivaRelacionalBean);
			resultado1 = (ArrayList)resultado.get("resultado");
			map.put("resultado",resultado1);
			if(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros()!=null)
			{
				cantidad = Long.parseLong(((OutputPMasivaRelacionalBean)resultado1.get(0)).getCantidadRegistros());
			}else
			{
				cantidad=resultado1.size();
			}
			if(cantidad == 0)
			{
				throw new ValidacionException("No se encontraron resultados para su búsqueda");
			}
			
			PrepagoBean prepagoBean = Transaction.getInstance().calcularPrecioMasivo(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19, Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()), cantidad, this.dbConn);
			precio = prepagoBean.getMontoBruto();
			NumberFormat formateo = new DecimalFormat(".00");
			precio = Double.parseDouble(formateo.format(precio));
			//Verificar Si el Usuario Tiene Saldo 
			// 0 No posee Saldo Suficiente
			// 1 Posee Saldo Suficiente
			if(usuario.getSaldo()<precio)
			{
				inputPMasivaRelacionalBean.setSaldo("0");
			}else
			{
				inputPMasivaRelacionalBean.setSaldo("1");
			}
			
			map.put("precio",precio);
			map.put("cantidad",cantidad);	
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta() != null)
			{
				if(inputPMasivaRelacionalBean.getFlagPagineo()==null)
				{
					inputPMasivaRelacionalBean.setCantidadRegistros(cantidadRegistroPago);
					//inputPMasivaRelacionalBean.setPrecio(String.valueOf(precio));
					inputPMasivaRelacionalBean.setFechaAct(FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
					transaccion(this.BUSQUEDA_VEHICULAR, inputPMasivaRelacionalBean, usuario, ipOrigen, dbConn, conn);
				}
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
		return map;
	}
	
	public ArrayList recuperarAreaRegistral(String servicio) throws SQLException ,Throwable
	{
		int serId;
		serId = Integer.parseInt(servicio);
		ArrayList areaRegistral = Tarea.getComboAreaLibro(conn, serId);
		
		return areaRegistral;
	}
	public List recuperarTipoEmbPesquera() throws SQLException, Throwable {
		List tipoEmbPesquera;
		tipoEmbPesquera = Tarea.recuperarTipoEmbPesquera(conn);
		return tipoEmbPesquera;
	}
	public List recuperarCapitania() throws SQLException, Throwable {
		List capitania;
		capitania = Tarea.recuperarCapitania(conn);
		return capitania;
	}
	public List recuperarTipoVehiculo() throws SQLException, Throwable {
		List tipoVehiculo;
		tipoVehiculo = Tarea.recuperarTipoVehiculo(conn);
		return tipoVehiculo;
	}
	public List recuperarTipoCombustible() throws SQLException, Throwable {
		List tipoCombustible;
		tipoCombustible = Tarea.recuperarTipoCombustible(conn);
		return tipoCombustible;
	}
	public List recuperarTipoAeronave() throws SQLException, Throwable {
		List tipoAeronave;
		tipoAeronave = Tarea.recuperarTipoAeronave(conn);
		return tipoAeronave;
	}
	
	private void transaccion(int tipoBusqueda, InputPMasivaRelacionalBean inputPMasivaRelacionalBean, UsuarioBean usuario, String ipOrigen, DBConnection myConn, Connection conn) throws DBException, CustomException, Throwable{
		
		LogAuditoriaBusquedaMasivaRelacional bt = new LogAuditoriaBusquedaMasivaRelacional();
		
		if (inputPMasivaRelacionalBean.getFlagPagineo()==null)
		{
			bt.setRemoteAddr(ipOrigen);             
			bt.setUsuarioSession(usuario);
			bt.setCodigoGLA(Integer.parseInt(inputPMasivaRelacionalBean.getRegistro()));
			bt.setCodigoServicio(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19);
			
			bt.setCantidadRegistros(Long.parseLong(inputPMasivaRelacionalBean.getCantidadRegistros()));
			
			StringBuffer factorBusqueda = new StringBuffer();
			CriterioBean criterioBean = Tarea.recuperarCriterio("oficinaregistral="+inputPMasivaRelacionalBean.getCadenaZona());
			String[] sedes = criterioBean.getSedesElegidas();
			
			String descripcionSedes = "";
			if(sedes.length <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe seleccionar al menos una sede", "errorPrepago");
			else if(sedes.length == 13)
				descripcionSedes = " TODAS LAS SEDES";
			else{
				descripcionSedes = sedes.length+" SEDES: "+nombreSede(sedes[0], myConn);
				
				for(int i = 1; i < sedes.length; i++)
					descripcionSedes += nombreSede(sedes[i], myConn);
			}
			bt.setDescripcionRegistroPublico(descripcionSedes);
			factorBusqueda.append(descripcionSedes);
			
			if(inputPMasivaRelacionalBean.getFechaInscripcionDesde()!=null && inputPMasivaRelacionalBean.getFechaInscripcionDesde().length()>0){
				factorBusqueda.append(", INSC. DESDE: "+inputPMasivaRelacionalBean.getFechaInscripcionDesde());
			}
			if(inputPMasivaRelacionalBean.getFechaInscripcionHasta()!=null && inputPMasivaRelacionalBean.getFechaInscripcionHasta().length()>0){
				factorBusqueda.append(", INSC. HASTA: "+inputPMasivaRelacionalBean.getFechaInscripcionHasta());
			}
			
			if(inputPMasivaRelacionalBean.getCodTipoActoCausal()!=null && inputPMasivaRelacionalBean.getCodTipoActoCausal().length()>0){

				if(inputPMasivaRelacionalBean.getCodTipoActoCausal().endsWith("T")){
					factorBusqueda.append(", TIPO ACTO: TODOS ");
				}else if(inputPMasivaRelacionalBean.getCodTipoActoCausal().endsWith("GRA")){
					factorBusqueda.append(", TIPO ACTO: GRAVÁMENES ");
				}else if(inputPMasivaRelacionalBean.getCodTipoActoCausal().endsWith("CAN")){
					factorBusqueda.append(", TIPO ACTO: CANCELACIONES ");
				}
			}
				
			if(tipoBusqueda == BUSQUEDA_VEHICULAR){
				
				if(inputPMasivaRelacionalBean.getMarca()!=null && inputPMasivaRelacionalBean.getMarca().length()>0){
					factorBusqueda.append(", MARCA: "+inputPMasivaRelacionalBean.getMarca());
				}
				if(inputPMasivaRelacionalBean.getModelo()!=null && inputPMasivaRelacionalBean.getModelo().length()>0){
					factorBusqueda.append(", MODELO: "+inputPMasivaRelacionalBean.getModelo());
				}
				if(inputPMasivaRelacionalBean.getAnoFabricacionDesde()!=null && inputPMasivaRelacionalBean.getAnoFabricacionDesde().length()>0){
					factorBusqueda.append(", AÑO FABRICACIÓN: "+inputPMasivaRelacionalBean.getAnoFabricacionDesde());
					if(inputPMasivaRelacionalBean.getAnoFabricacionHasta()!=null && inputPMasivaRelacionalBean.getAnoFabricacionHasta().length()>0){
						factorBusqueda.append("-"+inputPMasivaRelacionalBean.getAnoFabricacionHasta());
					}
				}
				
				if(inputPMasivaRelacionalBean.getCodTipoVehiculo()!=null && inputPMasivaRelacionalBean.getCodTipoVehiculo().length()>0){
					
					DboTmTipoVehi dboTmTipoVehi = new DboTmTipoVehi();
					dboTmTipoVehi.clearAll();
					dboTmTipoVehi.setFieldsToRetrieve(DboTmDocIden.CAMPO_DESCRIPCION);
					dboTmTipoVehi.setField(DboTmTipoVehi.CAMPO_COD_TIPO_VEHI, inputPMasivaRelacionalBean.getCodTipoVehiculo());
					
					if(dboTmTipoVehi.find()){
						factorBusqueda.append(", TIPO VEHICULO: "+dboTmTipoVehi.getField(DboTmTipoVehi.CAMPO_DESCRIPCION));
					}
				
				}
				
				if(inputPMasivaRelacionalBean.getColorVeh()!=null && inputPMasivaRelacionalBean.getColorVeh().length()>0){
					factorBusqueda.append(" COLOR: "+inputPMasivaRelacionalBean.getColorVeh());
				}
				
				if(inputPMasivaRelacionalBean.getCodTipoCombustible()!=null && inputPMasivaRelacionalBean.getCodTipoCombustible().length()>0){
					
					DboTmTipoComb dboTmTipoComb = new DboTmTipoComb();
					dboTmTipoComb.clearAll();
					dboTmTipoComb.setFieldsToRetrieve(DboTmTipoComb.CAMPO_DESCRIPCION);
					dboTmTipoComb.setField(DboTmTipoComb.CAMPO_COD_TIPO_COMB, inputPMasivaRelacionalBean.getCodTipoCombustible());
					
					if(dboTmTipoComb.find()){
						factorBusqueda.append(", TIPO COMBUSTIBLE: "+dboTmTipoComb.getField(DboTmTipoComb.CAMPO_DESCRIPCION));
					}
				}
				
				if(inputPMasivaRelacionalBean.getAgrupación().equals("1")){
					factorBusqueda.append(", SIN AGRUPACIÓN ");
				}else if(inputPMasivaRelacionalBean.getAgrupación().equals("2")){
					factorBusqueda.append(", AGRUPADO POR MARCA ");
				}else if(inputPMasivaRelacionalBean.getAgrupación().equals("3")){
					factorBusqueda.append(", AGRUPADO POR MODELO ");
				}else if(inputPMasivaRelacionalBean.getAgrupación().equals("4")){
					factorBusqueda.append(", AGRUPADO POR AÑO DE FABRICACIÓN ");
				}else if(inputPMasivaRelacionalBean.getAgrupación().equals("5")){
					factorBusqueda.append(", AGRUPADO POR FECHA DE INSCRIPCIÓN ");
				}else if(inputPMasivaRelacionalBean.getAgrupación().equals("6")){
					factorBusqueda.append(", AGRUPADO POR TIPO DE VEHICULO ");
				}else if(inputPMasivaRelacionalBean.getAgrupación().equals("7")){
					factorBusqueda.append(", AGRUPADO POR COLOR ");
				}else if(inputPMasivaRelacionalBean.getAgrupación().equals("8")){
					factorBusqueda.append(", AGRUPADO POR TIPO DE COMBUSTIBLE ");
				}
				
			}else if(tipoBusqueda == BUSQUEDA_EMB_PESQUERAS){
				
				if(inputPMasivaRelacionalBean.getCodTipoEmbarcacion()!=null && inputPMasivaRelacionalBean.getCodTipoEmbarcacion().length()>0){
					
					DboTmTipoEmbPesquera dboTmTipoEmbPesquera = new DboTmTipoEmbPesquera();
					dboTmTipoEmbPesquera.clearAll();
					dboTmTipoEmbPesquera.setFieldsToRetrieve(DboTmTipoEmbPesquera.CAMPO_DESCRIPCION);
					dboTmTipoEmbPesquera.setField(DboTmTipoEmbPesquera.CAMPO_COD_TIPO_EMB_PESQ, inputPMasivaRelacionalBean.getCodTipoEmbarcacion());
					
					if(dboTmTipoEmbPesquera.find()){
						factorBusqueda.append(", TIPO EMB.PESQ: "+dboTmTipoEmbPesquera.getField(DboTmTipoEmbPesquera.CAMPO_DESCRIPCION));
					}
				
				}
				
				if(inputPMasivaRelacionalBean.getCodCapitania()!=null && inputPMasivaRelacionalBean.getCodCapitania().length()>0){
					
					DboTmCapitania dboTmCapitania = new DboTmCapitania();
					dboTmCapitania.clearAll();
					dboTmCapitania.setFieldsToRetrieve(DboTmTipoEmbPesquera.CAMPO_DESCRIPCION);
					dboTmCapitania.setField(DboTmCapitania.CAMPO_CAPITANIA_ID, inputPMasivaRelacionalBean.getCodCapitania());
					
					if(dboTmCapitania.find()){
						factorBusqueda.append(", CAPITANIA: "+dboTmCapitania.getField(DboTmCapitania.CAMPO_DESCRIPCION));
					}
				
				}
				
				if(inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera()!=null && inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera().length()>0){
					factorBusqueda.append(", NOMBRE EMB.PESQ: "+inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera());
				}
				
			}else if(tipoBusqueda == BUSQUEDA_BUSQUE){
				
				if(inputPMasivaRelacionalBean.getCodCapitania()!=null && inputPMasivaRelacionalBean.getCodCapitania().length()>0){
					
					DboTmCapitania dboTmCapitania = new DboTmCapitania();
					dboTmCapitania.clearAll();
					dboTmCapitania.setFieldsToRetrieve(DboTmTipoEmbPesquera.CAMPO_DESCRIPCION);
					dboTmCapitania.setField(DboTmCapitania.CAMPO_CAPITANIA_ID, inputPMasivaRelacionalBean.getCodCapitania());
					
					if(dboTmCapitania.find()){
						factorBusqueda.append(", CAPITANIA: "+dboTmCapitania.getField(DboTmCapitania.CAMPO_DESCRIPCION));
					}
				
				}
				
				if(inputPMasivaRelacionalBean.getNombreBuque()!=null && inputPMasivaRelacionalBean.getNombreBuque().length()>0){
					factorBusqueda.append(", NOMBRE BUQUES: "+inputPMasivaRelacionalBean.getNombreBuque());
				}
				
			}else if(tipoBusqueda == BUSQUEDA_AERONAVES){
				
				if(inputPMasivaRelacionalBean.getCodTipoAeronave()!=null && inputPMasivaRelacionalBean.getCodTipoAeronave().length()>0){
					
					DboTmTipoAeronave dboTmTipoAeronave = new DboTmTipoAeronave();
					dboTmTipoAeronave.clearAll();
					dboTmTipoAeronave.setFieldsToRetrieve(DboTmTipoAeronave.CAMPO_DESCRIPCION);
					dboTmTipoAeronave.setField(DboTmTipoAeronave.CAMPO_COD_TIPO_AERONAVE, inputPMasivaRelacionalBean.getCodTipoAeronave());
					
					if(dboTmTipoAeronave.find()){
						factorBusqueda.append(", CAPITANIA: "+dboTmTipoAeronave.getField(DboTmTipoAeronave.CAMPO_DESCRIPCION));
					}
				
				}
				
				if(inputPMasivaRelacionalBean.getModelo()!=null && inputPMasivaRelacionalBean.getModelo().length()>0){
					factorBusqueda.append(", MODELO: "+inputPMasivaRelacionalBean.getModelo());
				}
				
			}else if(tipoBusqueda == BUSQUEDA_RMC){
				
				inputPMasivaRelacionalBean.getMontoGarantiaDesde();
				inputPMasivaRelacionalBean.getMontoGarantiaHasta();
				
				if(inputPMasivaRelacionalBean.getMontoGarantiaDesde()!=null && inputPMasivaRelacionalBean.getMontoGarantiaDesde().length()>0){
					factorBusqueda.append(", MONT.GAR.DESDE: "+inputPMasivaRelacionalBean.getMontoGarantiaDesde());
				}
				if(inputPMasivaRelacionalBean.getMontoGarantiaHasta()!=null && inputPMasivaRelacionalBean.getMontoGarantiaHasta().length()>0){
					factorBusqueda.append(", MONT.GAR.HASTA: "+inputPMasivaRelacionalBean.getMontoGarantiaHasta());
				}
				
			}
			
				bt.setCriterioBusqueda(factorBusqueda.toString());
				
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					  
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					prepagoBean.setCodigoServicio(bt.getCodigoServicio());
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						System.out.println("inputPMasivaRelacionalBean.getRegistro(): "+inputPMasivaRelacionalBean.getRegistro());
						String areaRegistral = Tarea.recuperaAreaRegistral(inputPMasivaRelacionalBean.getRegistro(), conn);
						Job004 j = new Job004();
						//j.setQuery("select o.reg_pub_id, o.ofic_reg_id, '"+areaRegistral+"' as area_reg_id from ofic_registral o");
						j.setQuery(inputPMasivaRelacionalBean.getCadenaQuery());
						j.setUsuario(usuario);
						//Se valida si el acceso es via webService o controller, para diferenciar la transacción
						j.setCodigoServicio(prepagoBean.getCodigoServicio());
						
						double monto = prepagoBean.getMontoBruto()/Long.parseLong(inputPMasivaRelacionalBean.getCantidadRegistros());
						BigDecimal bigDecimal = new BigDecimal(monto);
						bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
						monto = bigDecimal.doubleValue();
						
						j.setCostoServicio(monto);
						
						Thread llamador1 = new Thread(j);
						llamador1.start();
					}
					
				}
				
			}
			
		
		}
		
		private String nombreSede(String regPubId, DBConnection myConn) throws DBException, Throwable{
			DboRegisPublico sede = new DboRegisPublico();
			sede.setConnection(myConn);
		
			sede.clearAll();
			sede.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
			sede.setField(DboRegisPublico.CAMPO_REG_PUB_ID, regPubId);
			if(sede.find())
				return sede.getField(DboRegisPublico.CAMPO_NOMBRE);
			else
				return "No disponible";
		}
}
