package gob.pe.sunarp.extranet.framework;

import com.jcorporate.expresso.core.controller.Controller;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.Constantes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;

public abstract class ControllerExtension extends Controller {
	
	protected String className = this.getClass().getName();
 


	public String findForward(String forwardName, ControllerRequest request) 
	{
		//-
		return forwardName;
	}

	public boolean isTrace(Object _this) {
		return Loggy.isTrace(_this);
	}
	
	public void trace(String traceMessage, ControllerRequest request){
		Loggy.trace(traceMessage, this, request);
	}

	public void info(String infoMessage, ControllerRequest request){
		Loggy.info(infoMessage, this, request);
	}
	
	public void log(String errorCode, String logMessage, ControllerRequest request){
		Loggy.log(errorCode, logMessage, this, request);
	}

	public void log(String errorCode, String logMessage, Throwable t, ControllerRequest request){
		t.printStackTrace();
		Loggy.log(errorCode, logMessage, this, t, request);
	}

	public void log(int errorLevel, String logMessage, ControllerRequest request){
		Loggy.log(errorLevel, logMessage, this, request);
	}

	public void log(int errorLevel, String logMessage, Throwable t, ControllerRequest request)
	{
		t.printStackTrace();
		Loggy.log(errorLevel, logMessage, this, t, request);
	}

	public void principal(ControllerRequest crequest) {
		Loggy.principal(crequest);
	}

	public void end(ControllerRequest request) {
		Loggy.end(request);
	}

	public void init(ControllerRequest request) 
	{
		String state = ExpressoHttpSessionBean.getRequest(request).getParameter("state");
		UsuarioBean  ub = ExpressoHttpSessionBean.getUsuarioBean(request);
		String userId = "[NO_HAY_USUARIOBEAN_EN_SESION]";
		if (ub!=null)
			userId = ub.getUserId();
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("__Procesando CONTROLLER: ").append(className);
		buffer.append(" STATE: ");
		if (state==null)
			buffer.append(getInitialState());
		else	
			buffer.append(state);
		buffer.append(" USERID: ").append(userId);
		info(buffer.toString(), request);
		if (isTrace(this)) System.out.println(buffer.toString());
	}

	public void rollback(Connection conn, ControllerRequest request) {
		if(conn != null) {
			try{
				if (isTrace(this)) trace("Realizando rollback a la base de datos", request);
				conn.rollback();
			}catch(Throwable dbe1){
				log(Errors.EC_GENERIC_DB_ERROR, "No se pudo realizar rollback", dbe1, request);
			}
		}
	}
	
	public void validarSesion(ControllerRequest request) throws CustomException {
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		if (session == null) {
			throw new CustomException(Errors.EC_NOT_EXIST_VALID_SESSION, "", "errorSesion");
		}
		UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
		if (bean == null) {
			throw new CustomException(Errors.EC_SESSION_INCOMPLETE, "", "errorSesion");
		}
	}
	
	
}

