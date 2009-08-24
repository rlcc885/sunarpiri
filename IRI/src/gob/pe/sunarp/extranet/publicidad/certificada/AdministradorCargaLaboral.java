package gob.pe.sunarp.extranet.publicidad.certificada;

import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.dbobj.DboSolicitudXCarga;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.BuscaCargaLaboralBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.SolicitudXCargaBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import java.sql.Connection;
import java.sql.Timestamp;
import gob.pe.sunarp.extranet.framework.session.*;

public class AdministradorCargaLaboral {

private SolicitudXCargaBean solxcargaBean;
private BuscaCargaLaboralBean buscaCargaLabBean;
private Connection conn;
public void asignarRegistradores(){
}
	
public void recuperarRegistradoresDisponibles(){
}	

public void actualizarSolxCargaImprocendI(String solicitud_id, UsuarioBean user, Connection conn) throws Throwable{
	setConn(conn);
	SolicitudXCargaBean solxcargabean = new SolicitudXCargaBean();	
	solxcargabean.setEstado(Constantes.ESTADO_ATEN_ATENDIDA);
	solxcargabean.setRol(Constantes.REGIS_VERIFICADOR);
	solxcargabean.setSolicitud_id(solicitud_id);
	solxcargabean.setTs_modi(FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));
	//hphp:12/11/2003
	//solxcargabean.setUsr_modi(cuenta_id);
	//solxcargabean.setCta_id_reg(cuenta_id);
	solxcargabean.setCta_id_reg(user.getCuentaId());
	solxcargabean.setUsr_modi(user.getUserId());	
	//solxcargabean.setCuenta_id(cuenta_id);	//falta verificar el campo
	setSolxcargaBean(solxcargabean);
	actualizarDBSolicitudXCarga();	
	
}

public void actualizarSolxCargaImprocendII(String solicitud_id, UsuarioBean user, Connection conn) throws Throwable{
	setConn(conn);
	SolicitudXCargaBean solxcargabean = new SolicitudXCargaBean();	
	solxcargabean.setEstado(Constantes.ESTADO_ATEN_ATENDIDA);
	solxcargabean.setRol(Constantes.REGIS_EMISOR);
	solxcargabean.setSolicitud_id(solicitud_id);
	solxcargabean.setTs_modi(FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));
	//hphp:12/11/2003
	//solxcargabean.setUsr_modi(cuenta_id);
	//solxcargabean.setCta_id_reg(cuenta_id);
	solxcargabean.setCta_id_reg(user.getCuentaId());
	solxcargabean.setUsr_modi(user.getUserId());	
	//solxcargabean.setCuenta_id(cuenta_id);	//falta verificar el campo
	setSolxcargaBean(solxcargabean);
	actualizarDBSolicitudXCarga();	
	
}

public void actualizarSolxCargaVerficado(String solicitud_id, UsuarioBean user, Connection conn) throws Throwable{
	setConn(conn);
	SolicitudXCargaBean solxcargabean = new SolicitudXCargaBean();	
	solxcargabean.setEstado(Constantes.ESTADO_ATEN_ATENDIDA);
	solxcargabean.setRol(Constantes.REGIS_VERIFICADOR);
	solxcargabean.setSolicitud_id(solicitud_id);	
	solxcargabean.setTs_modi(FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));		
	//hphp:12/11/2003
	//solxcargabean.setUsr_modi(cuenta_id);
	//solxcargabean.setCta_id_reg(cuenta_id);
	solxcargabean.setCta_id_reg(user.getCuentaId());
	solxcargabean.setUsr_modi(user.getUserId());	
	//solxcargabean.setCuenta_id(cuenta_id);	//falta verificar el campo
	setSolxcargaBean(solxcargabean);
	actualizarDBSolicitudXCarga();
}

public void actualizarSolxCargaExpedido(String solicitud_id, UsuarioBean user, Connection conn) throws Throwable{
	setConn(conn);
	SolicitudXCargaBean solxcargabean = new SolicitudXCargaBean();	
	solxcargabean.setEstado(Constantes.ESTADO_ATEN_ATENDIDA);
	solxcargabean.setRol(Constantes.REGIS_EMISOR);
	solxcargabean.setSolicitud_id(solicitud_id);	
	solxcargabean.setTs_modi(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));		
	//hphp:12/11/2003
	//solxcargabean.setUsr_modi(cuenta_id);
	//solxcargabean.setCta_id_reg(cuenta_id);
	solxcargabean.setCta_id_reg(user.getCuentaId());
	solxcargabean.setUsr_modi(user.getUserId());	
	//solxcargabean.setCuenta_id(cuenta_id);	//falta verificar el campo
	setSolxcargaBean(solxcargabean);
	actualizarDBSolicitudXCarga();
}

protected void grabarDBSolicitudXCarga() throws Throwable{
	
		solxcargaBean.setTs_crea(FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));
		solxcargaBean.setTs_modi(FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));	
	
		//Insert SOLICITUD X CARGA
		DBConnection dconn = new DBConnection(conn);		
		DboSolicitudXCarga dboSolXCarga = new DboSolicitudXCarga(dconn);				
		dboSolXCarga.setField(dboSolXCarga.CAMPO_SOLICITUD_ID, solxcargaBean.getSolicitud_id());
		dboSolXCarga.setField(dboSolXCarga.CAMPO_CUENTA_ID, solxcargaBean.getCuenta_id());
		dboSolXCarga.setField(dboSolXCarga.CAMPO_ROL,solxcargaBean.getRol());
		dboSolXCarga.setField(dboSolXCarga.CAMPO_CTA_ID_REG,solxcargaBean.getCta_id_reg());
		dboSolXCarga.setField(dboSolXCarga.CAMPO_PRIORIDAD,solxcargaBean.getPrioridad());
		dboSolXCarga.setField(dboSolXCarga.CAMPO_TS_CREA,solxcargaBean.getTs_crea());		
		dboSolXCarga.setField(dboSolXCarga.CAMPO_TS_MODI,solxcargaBean.getTs_modi() );		
		dboSolXCarga.setField(dboSolXCarga.CAMPO_USR_CREA,solxcargaBean.getUsr_crea());		
		dboSolXCarga.setField(dboSolXCarga.CAMPO_USR_MODI,solxcargaBean.getUsr_modi());				
		dboSolXCarga.add();
				
		solxcargaBean.setSolicitud_id(dboSolXCarga.getField(dboSolXCarga.CAMPO_SOLICITUD_ID));
		solxcargaBean.setCuenta_id(dboSolXCarga.getField(dboSolXCarga.CAMPO_CUENTA_ID));
		solxcargaBean.setRol(dboSolXCarga.getField(dboSolXCarga.CAMPO_ROL));
		
}

protected void actualizarDBSolicitudXCarga() throws Throwable{
		
		DBConnection dconn= new DBConnection(getConn());
		DboSolicitudXCarga dboSolXCarga = new DboSolicitudXCarga(dconn);						
		
		dboSolXCarga.setFieldsToUpdate(dboSolXCarga.CAMPO_ESTADO+"|"+ dboSolXCarga.CAMPO_TS_MODI+"|"+ dboSolXCarga.CAMPO_USR_MODI);	
		dboSolXCarga.setField(dboSolXCarga.CAMPO_ESTADO, solxcargaBean.getEstado());		
		dboSolXCarga.setField(dboSolXCarga.CAMPO_TS_MODI, solxcargaBean.getTs_modi());				
		dboSolXCarga.setField(dboSolXCarga.CAMPO_USR_MODI, solxcargaBean.getUsr_modi());	
		dboSolXCarga.setField(dboSolXCarga.CAMPO_SOLICITUD_ID,solxcargaBean.getSolicitud_id());//where			
		dboSolXCarga.setField(dboSolXCarga.CAMPO_ROL,solxcargaBean.getRol());
		dboSolXCarga.setField(dboSolXCarga.CAMPO_CTA_ID_REG, solxcargaBean.getCta_id_reg());	
		//dboSolXCarga.setField(dboSolXCarga.CAMPO_CUENTA_ID,solxcargaBean.getCuenta_id());			
		dboSolXCarga.update();
		
}
public void recuperarSolicitudes(){
}

public void recuperarSolicitud(){
}

public void guardarVerificacion(){
}

public void verificarSolicitud(){
}


public void verificarSolicitudEnLinea(){
}


public void guardarEmision(){	
}


public void despacharSolicitudes(){
}

	/**
	 * Gets the solxcargaBean
	 * @return Returns a SolicitudXCargaBean
	 */
	public SolicitudXCargaBean getSolxcargaBean() {
		return solxcargaBean;
	}
	/**
	 * Sets the solxcargaBean
	 * @param solxcargaBean The solxcargaBean to set
	 */
	public void setSolxcargaBean(SolicitudXCargaBean solxcargaBean) {
		this.solxcargaBean = solxcargaBean;
	}

	/**
	 * Gets the buscaCargaLabBean
	 * @return Returns a BuscaCargaLaboralBean
	 */
	public BuscaCargaLaboralBean getBuscaCargaLabBean() {
		return buscaCargaLabBean;
	}
	/**
	 * Sets the buscaCargaLabBean
	 * @param buscaCargaLabBean The buscaCargaLabBean to set
	 */
	public void setBuscaCargaLabBean(BuscaCargaLaboralBean buscaCargaLabBean) {
		this.buscaCargaLabBean = buscaCargaLabBean;
	}

	/**
	 * Gets the conn
	 * @return Returns a Connection
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * Sets the conn
	 * @param conn The conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

}

