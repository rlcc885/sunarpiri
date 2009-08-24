package gob.pe.sunarp.extranet.publicidad.service.impl;

import java.sql.Connection;

import com.jcorporate.expresso.core.controller.ControllerRequest;

import gob.pe.sunarp.extranet.framework.Loggy;

public class ServiceImpl extends Loggy{
	
	protected String className = this.getClass().getName();
	
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
	
	public void rollback(Connection conn) {
		if(conn != null) {
			try{
				conn.rollback();
			}catch(Throwable dbe1){
				//log(Errors.EC_GENERIC_DB_ERROR, "No se pudo realizar rollback", dbe1, request);
			}
		}
	}
	
}
