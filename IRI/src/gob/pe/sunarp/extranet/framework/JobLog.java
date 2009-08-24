package gob.pe.sunarp.extranet.framework;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.EventsProcessor;
import gob.pe.sunarp.extranet.common.EventsRepository;
import gob.pe.sunarp.extranet.common.SystemResources;

import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import java.sql.Connection;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;

public class JobLog {
	private static JobLog log = new JobLog();

	public static void trace(
		EventsRepository events,
		Object _this,
		String mensaje) {
		EventsProcessor.getInstance().addEvent(
			events,
			Errors.LEVEL_TRACE,
			_this,
			mensaje);
	}
	public static void info(
		EventsRepository events,
		Object _this,
		String mensaje) {
		EventsProcessor.getInstance().addEvent(
			events,
			Errors.LEVEL_INFORMATIONAL,
			_this,
			mensaje);
	}
	public static void log(
		EventsRepository events,
		Object _this,
		String errorCode,
		String mensaje) {
		EventsProcessor.getInstance().addEvent(events, errorCode, _this, mensaje);
	}
	public static void log(
		EventsRepository events,
		Object _this,
		String errorCode,
		String mensaje,
		Throwable t) {
		EventsProcessor.getInstance().addEvent(events, errorCode, _this, mensaje, t);
	}
	public static void principal(EventsRepository events) {
		events.setPrincipalEvent();
	}
	public static EventsRepository createEvents(
		String request,
		String userIdentifyingString) {
		EventsRepository events = new EventsRepository();
		events.setRequest(request);
		events.setUserIdentifyingString(userIdentifyingString);
		events.setMinLevel(SystemResources.getInstance().getSysMinLevel());
		return events;
	}
	public static void end(EventsRepository events) {
		if (events == null)
			throw new IllegalArgumentException("Events no debe ser null");
		Connection myConn = null;

		try {
			myConn = DBConnectionFactory.getInstance().getConnection();
			myConn.setAutoCommit(true);
			EventsProcessor.getInstance().endRequestLog(events, myConn);
		} catch (Exception e) {
			JobLog.log(
				events,
				log,
				Errors.EC_GENERIC_DB_ERROR,
				"No se pudo llamar al JobLog.end() por error en DB",
				e);
			EventsProcessor.getInstance().writeDownLog(events);
		} finally {
			if (myConn != null) {
				DBConnectionFactory.getInstance().release(myConn);
			}
		}
	}
	
	public static void rollback(DBConnection conn, EventsRepository events) {
		if(conn != null) {
			try{
				JobLog.trace(events, log, "Realizando rollback a la base de datos");
				conn.rollback();
			}catch(DBException dbe1){
				JobLog.log(events, log, Errors.EC_GENERIC_DB_ERROR, "No se pudo realizar rollback", dbe1);
			}
		}
	}
	public static void endWithoutMail(EventsRepository events) {
		if (events == null)
			throw new IllegalArgumentException("Events no debe ser null");
		EventsProcessor.getInstance().writeDownLog(events);

		String logString = EventsProcessor.getInstance().getLogString(events);
		
	}

}