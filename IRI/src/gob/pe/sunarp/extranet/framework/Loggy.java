// Clase para simular los logs.
package gob.pe.sunarp.extranet.framework;

import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.SystemResources;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import java.sql.Connection;

public class Loggy {


	public static boolean isTrace(Object _this) {
		int objectLevel = SystemResources.getInstance().getObjectLevel(_this.getClass().getName());
		return objectLevel <= Errors.LEVEL_TRACE;
	}
	
	public static void log(String errorCode, String logMessage, Object _this, ControllerRequest request){
		Logger.getInstance().log(request, errorCode, _this, logMessage);
	}

	public static void log(String errorCode, String logMessage, Object _this, Throwable t, ControllerRequest request){
		Logger.getInstance().log(request, errorCode, _this, logMessage, t);
	}

	public static void log(int errorLevel, String logMessage, Object _this, ControllerRequest request){
		Logger.getInstance().log(request, errorLevel, _this, logMessage);
	}

	public static void log(int errorLevel, String logMessage, Object _this, Throwable t, ControllerRequest request){
		Logger.getInstance().log(request, errorLevel, _this, logMessage, t);
	}

	public static void trace(String traceMessage, Object _this, ControllerRequest request){
		Logger.getInstance().log(request, Errors.LEVEL_TRACE, _this, traceMessage);
	}

	public static void info(String infoMessage, Object _this, ControllerRequest request){
		Logger.getInstance().log(request, Errors.LEVEL_INFORMATIONAL, _this, infoMessage);
	}
	
	public static void end(ControllerRequest request) {
		if (request == null) throw new IllegalArgumentException("ControllerRequest no debe ser null");
		DBConnectionFactory myPool = null;
		Connection myConn = null;
		try {
			myPool = DBConnectionFactory.getInstance();
			myConn = myPool.getConnection();
			//myConn.setAutoCommit(true);
			Logger.getInstance().end(request, myConn);
		} catch (Exception e) {
			Loggy.log(Errors.EC_GENERIC_DB_ERROR, "No se pudo llamar al Loggy.end() por error en DB", new Loggy(), e, request);
			Logger.getInstance().writeDownLog(request);
		} finally {
			myPool.release(myConn);
		}
	}
	
	public static void principal(ControllerRequest crequest) {
		Logger.getInstance().principal(crequest);
	}
}

