package gob.pe.sunarp.extranet.framework;

import com.jcorporate.expresso.core.controller.ControllerRequest;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.common.*;
import java.sql.Connection;
import java.util.Enumeration;
import javax.servlet.http.*;
import gob.pe.sunarp.extranet.util.*;

public class Logger {
	
	private static Logger logger;
	
	public static Logger getInstance() {
		if (logger == null) {
			logger = new Logger();
		}
		return logger;
	}
	
	private Logger() {
	}
	
	private void initEventsRepository(EventsRepository events, HttpServletRequest req) {
		StringBuffer buffer = new StringBuffer(req.getRequestURI());
		buffer.append(" ");
		buffer.append(req.getMethod());
		if (req.getContentType() != null) {
			buffer.append(" ");
			buffer.append(req.getContentType());
		}
		Enumeration names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			buffer.append(" ");
			buffer.append(name);
			buffer.append("=");
			String[] values = req.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				buffer.append(values[i]);
				buffer.append("::");
			}
		}
		events.setRequest(buffer.toString());

		HttpSession sesion = req.getSession(false);
		if (sesion != null) {
			Object sessionData = sesion.getAttribute(Constantes.SESSION_DATA);
			if (sessionData != null) {
				events.setUserIdentifyingString(sessionData.toString());
			}
		}

	}
	
	private EventsRepository retrieveEventsRepository(ControllerRequest crequest, Object _this) {
		HttpServletRequest request = ExpressoHttpSessionBean.getRequest(crequest);
		EventsRepository events = (EventsRepository)request.getAttribute(Constantes.EVENTS);
		if (events == null) {
			events = new EventsRepository();
			events.setMinLevel(SystemResources.getInstance().getObjectLevel(_this.getClass().getName()));
		
			initEventsRepository(events, request);
			request.setAttribute(Constantes.EVENTS, events);
		} else {
			events.setMinLevel(SystemResources.getInstance().getObjectLevel(_this.getClass().getName()));
		}
		
		return events;
	}
	
	public Event log(ControllerRequest crequest, String errorCode, Object _this, String message) {
		EventsRepository events = retrieveEventsRepository(crequest, _this);
		return EventsProcessor.getInstance().addEvent(events, errorCode, _this, message);
	}

	public Event log(ControllerRequest crequest, String errorCode, Object _this, String message, Throwable t) {
		EventsRepository events = retrieveEventsRepository(crequest, _this);
		return EventsProcessor.getInstance().addEvent(events, errorCode, _this, message, t);
	}

	public Event log(ControllerRequest crequest, int level, Object _this, String message) {
		EventsRepository events = retrieveEventsRepository(crequest, _this);
		return EventsProcessor.getInstance().addEvent(events, level, _this, message);
	}

	public Event log(ControllerRequest crequest, int level, Object _this, String message, Throwable t) {
		EventsRepository events = retrieveEventsRepository(crequest, _this);
		return EventsProcessor.getInstance().addEvent(events, level, _this, message, t);
	}
	
	public void principal(ControllerRequest crequest) {
		HttpServletRequest request = ExpressoHttpSessionBean.getRequest(crequest);
		EventsRepository events = (EventsRepository)request.getAttribute(Constantes.EVENTS);
		if (events != null) {
			events.setPrincipalEvent();
		}
	}

	public void end(ControllerRequest crequest, Connection conn) {
		HttpServletRequest request = ExpressoHttpSessionBean.getRequest(crequest);
		EventsRepository events = (EventsRepository)request.getAttribute(Constantes.EVENTS);
		if (events != null) {
			EventsProcessor.getInstance().endRequestLog(events, conn);
		}
	}
	
	public void writeDownLog(ControllerRequest crequest) {
		HttpServletRequest request = ExpressoHttpSessionBean.getRequest(crequest);
		EventsRepository events = (EventsRepository)request.getAttribute(Constantes.EVENTS);
		if (events != null) {
			EventsProcessor.getInstance().writeDownLog(events);
		}
	}
}

