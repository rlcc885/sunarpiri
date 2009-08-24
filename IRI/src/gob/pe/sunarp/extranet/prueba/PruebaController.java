/**
 * 
 */
package gob.pe.sunarp.extranet.prueba;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.dbobj.DboTransaccion;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Tarea;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

/**
 * @author jbugarin
 *
 */
public class PruebaController extends ControllerExtension {
	
	public PruebaController() {
		// TODO Auto-generated constructor stub
		super();
		addState(new State("ok", "Muestra el form inicial para el ingreso de datos"));
		
		
		setInitialState("ok");
	}
	
	protected ControllerResponse runOkState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		DBConnection myConn = null;
		
		try{
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			init(request);
			validarSesion(request);
			conn = pool.getConnection();
			myConn = new DBConnection(conn);
		   	myConn.setAutoCommit(false);
		   	String cadBusqueda="";
		   	PrepagoBean prep = new PrepagoBean();
		   	//descuentoSaldo(myConn, prep, deno);	
			response.setStyle("ok");
		}
		/*catch (CustomException e) {
			//log(e.getCodigoError(), e.getMessage(), request);
			//principal(request);
			//rollback(conn, request);
			//response.setStyle("error");
		} */
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			//rollback(conn, request);
			response.setStyle("error");
		}finally{
			//JDBC.getInstance().closeResultSet(rsetGla);
			//JDBC.getInstance().closeStatement(pstmt);
			//pool.release(conn);
			end(request);
		}
		return response;
		
	}
	
	private  void registrTransaccionDsctoSaldo(DBConnection myConn,
			UsuarioBean user, PrepagoBean prep, String cadBusqueda) throws DBException,
			CustomException {

		try {
			int transId = 0;
			transId = Integer.valueOf(String.valueOf(Secuenciales.getInstance()
					.getIDTransaccion(myConn)));

			DboTransaccion transac = new DboTransaccion();
			transac.setConnection(myConn);
			transac.setField(transac.CAMPO_TRANS_ID, transId);
			transac.setField(transac.CAMPO_SERVICIO_ID, 198);
			transac.setField(transac.CAMPO_COD_GRUPO_LIBRO_AREA, 10);
			transac.setField(transac.CAMPO_CUENTA_ID, user.getCuentaId());
			transac.setField(transac.CAMPO_FEC_HOR, FechaUtil
					.dateTimeToStringToOracle(new Timestamp(System
							.currentTimeMillis())));
			// Double costo_servicio =
			// Double.parseDouble(deno.getMonto().toString());
			Double costo_servicio = Double.parseDouble(Tarea.getTarifa(myConn,
					198).toString());
			transac.setField(transac.CAMPO_COSTO, Double
					.toString(costo_servicio));
			transac.setField(transac.CAMPO_IP, "LOCALHOST");
			transac.setField(transac.CAMPO_SESION_ID, "");
			transac.setField(transac.CAMPO_TIPO_USR, "1");
			transac.setField(transac.CAMPO_STR_BUSQ, cadBusqueda);
			transac.setField(transac.CAMPO_REG_PUB_ID, user.getRegPublicoId());
			transac.setField(transac.CAMPO_OFIC_REG_ID, user
					.getOficRegistralId());
			transac.add();
			prep.setTransacId(transId);
			prep.setMontoBruto(costo_servicio);
			prep.setUsuario(user.getUserId());
			prep.setLineaPrepagoId(user.getLinPrePago());
			LineaPrepago lineaCmd = new LineaPrepago();
			lineaCmd.reduceSaldo(user, prep, myConn);
			myConn.commit();
			
		} catch (NumberFormatException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		
	}

	
/*private String  descuentoSaldo(DBConnection myConn, PrepagoBean pre) throws DBException, NumberFormatException, SQLException, CustomException{
		
		int transId = Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(myConn)));
		try{ 
		DboTransaccion transac = new DboTransaccion();
		transac.setConnection(myConn);
		
		//transac.setField(transac.CAMPO_TRANS_ID, Integer.valueOf(String.valueOf(Secuenciales.getInstance().getIDTransaccion(myConn))));		
		transac.setField(transac.CAMPO_TRANS_ID, transId);
		transac.setField(transac.CAMPO_SERVICIO_ID, deno.getServicio());
		transac.setField(transac.CAMPO_COD_GRUPO_LIBRO_AREA, deno.getCodigoLibro());
		transac.setField(transac.CAMPO_CUENTA_ID, deno.getUsuario().getCuentaId());
		transac.setField(transac.CAMPO_FEC_HOR, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		Double costo_servicio = Double.parseDouble(deno.getMonto().toString());
		transac.setField(transac.CAMPO_COSTO, Double.toString(costo_servicio));
		transac.setField(transac.CAMPO_IP, "LOCALHOST");
		transac.setField(transac.CAMPO_SESION_ID, "");
		transac.setField(transac.CAMPO_TIPO_USR, "1");
		transac.setField(transac.CAMPO_STR_BUSQ, "RESERVA DENOMINACION");
		transac.setField(transac.CAMPO_REG_PUB_ID, deno.getUsuario().getRegPublicoId());
		transac.setField(transac.CAMPO_OFIC_REG_ID, deno.getUsuario().getOficRegistralId());
		transac.add();
		
		PrepagoBean prep = new PrepagoBean();
		prep.setTransacId(transId);
		prep.setUsuario(deno.getUsuario().getUserId());
		prep.setLineaPrepagoId(deno.getUsuario().getLinPrePago());
		prep.setMontoBruto(costo_servicio);
		
		LineaPrepago lineaCmd = new LineaPrepago();
		
		lineaCmd.reduceSaldo(deno.getUsuario(), prep, myConn);
		
		myConn.commit();
		}catch (DBException dbe) {
			dbe.printStackTrace();
			throw dbe;

		}finally{
			myConn.rollback();
		}
		return String.valueOf(transId);
		}*/
}
