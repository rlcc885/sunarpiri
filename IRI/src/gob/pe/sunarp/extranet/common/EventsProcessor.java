package gob.pe.sunarp.extranet.common;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.text.*;

public class EventsProcessor implements OutputConstants, Errors {

	private static EventsProcessor eventsProcessor;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
	/**
	 * EventsRepository constructor comment.
	 */
	
	public static EventsProcessor getInstance() {
		if (eventsProcessor == null) {
			eventsProcessor = new EventsProcessor();
		}
		return eventsProcessor;
	}
	
	private EventsProcessor() {
		super();
	}
	
	private void processPrincipal(EventsRepository repository, Event evtmsg) {
		if (repository.getPrincipal() == -1) {
			repository.setPrincipal(0);
		} else if ((!repository.getHayPrincipal()) && (evtmsg.getLevel() >= repository.getLevelPrincipal())) {
			repository.setPrincipal(repository.getEvents().indexOf(evtmsg));
			repository.setLevelPrincipal(evtmsg.getLevel());
		}
	}

	public Event addEvent(EventsRepository repository, int level, Object _this, String message) {

		if (level >= SystemResources.getInstance().getObjectLevel(_this.getClass().getName())) {

			Event evtmsg = new Event();
			evtmsg.setClassName(_this.getClass().getName());
			evtmsg.setLevel(level);
			evtmsg.setTimestamp(new Date());
			evtmsg.setMessage(message == null ? "" : message);
			evtmsg.setCode("");
			evtmsg.setSendMail(false);

			repository.addEvent(evtmsg);

			processPrincipal(repository, evtmsg);

			return evtmsg;
		}

		return null;
	}

	public Event addEvent(EventsRepository repository, int level, Object _this, String message, Throwable e) {

		if (level >= SystemResources.getInstance().getObjectLevel(_this.getClass().getName())) {

			Event evtmsg = new Event();
			evtmsg.setClassName(_this.getClass().getName());
			evtmsg.setLevel(level);
			evtmsg.setTimestamp(new Date());
			evtmsg.setMessage(message == null ? "" : message);
			evtmsg.setCode("");
			evtmsg.setSendMail(false);

			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			evtmsg.setTrace(sw.toString());

			repository.addEvent(evtmsg);

			processPrincipal(repository, evtmsg);

			return evtmsg;
		}

		return null;

	}

	public Event addEvent(EventsRepository repository, String event_code, Object _this, String message) {

		RegisteredError re = SystemResources.getInstance().getRegisteredError(event_code);

		if (re == null) {
			Event evtmsg = new Event();
			evtmsg.setMessage("No se encontró error de código: " + event_code);
			evtmsg.setOtherMessage(message);
			evtmsg.setClassName(_this.getClass().getName());
			evtmsg.setCode("");
			evtmsg.setLevel(4);
			evtmsg.setSendMail(false);
			evtmsg.setTimestamp(new Date());

			repository.addEvent(evtmsg);

			processPrincipal(repository, evtmsg);

			return evtmsg;
		}

		if (re.getLevel() >= SystemResources.getInstance().getObjectLevel(_this.getClass().getName())) {

			Event evtmsg = new Event();
			evtmsg.setMessage(re.getMessage() != null ? re.getMessage() : "");
			evtmsg.setOtherMessage(message != null ? message : "");
			evtmsg.setClassName(_this.getClass().getName());
			evtmsg.setCode(re.getErrorCode());
			evtmsg.setLevel(re.getLevel());
			evtmsg.setSendMail(re.getEmail());
			evtmsg.setTimestamp(new Date());

			repository.addEvent(evtmsg);

			processPrincipal(repository, evtmsg);

			return evtmsg;
		}

		return null;

	}

	public Event addEvent(EventsRepository repository, String event_code, Object _this, String message, Throwable e) {

		RegisteredError re = SystemResources.getInstance().getRegisteredError(event_code);

		if (re == null) {
			Event evtmsg = new Event();
			evtmsg.setMessage("No se encontró error de código: " + event_code);
			evtmsg.setOtherMessage(message);
			evtmsg.setClassName(_this.getClass().getName());
			evtmsg.setCode("");
			evtmsg.setLevel(4);
			evtmsg.setSendMail(false);
			evtmsg.setTimestamp(new Date());

			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			evtmsg.setTrace(sw.toString());

			repository.addEvent(evtmsg);

			processPrincipal(repository, evtmsg);

			return evtmsg;
		}

		if (re.getLevel() >= SystemResources.getInstance().getObjectLevel(_this.getClass().getName())) {

			Event evtmsg = new Event();
			StringBuffer messageComplete = new StringBuffer();
/*			messageComplete.append(re.getMessage() != null ? re.getMessage() : "");
			messageComplete.append(". ");
			messageComplete.append(message != null ? message : "");
*/
			evtmsg.setMessage(re.getMessage() != null ? re.getMessage() : "");
			evtmsg.setClassName(_this.getClass().getName());
			evtmsg.setCode(re.getErrorCode());
			evtmsg.setLevel(re.getLevel());
			evtmsg.setSendMail(re.getEmail());
			evtmsg.setTimestamp(new Date());
			evtmsg.setOtherMessage(message);

			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			evtmsg.setTrace(sw.toString());

			repository.addEvent(evtmsg);

			processPrincipal(repository, evtmsg);

			return evtmsg;
		}

		return null;

	}

	public String getCompleteMessage(EventsRepository repository) {

		StringBuffer buffer = new StringBuffer();
		Event evt = (Event) repository.getEvents().elementAt(repository.getPrincipal());

		switch (evt.getLevel()) {
			case LEVEL_TRACE :
				buffer.append(LABEL_TRACE);
				break;
			case LEVEL_INFORMATIONAL :
				buffer.append(LABEL_INFORMATIONAL);
				break;
			case LEVEL_WARNING :
				buffer.append(LABEL_WARNING);
				break;
			case LEVEL_ERROR :
				buffer.append(LABEL_ERROR);
				break;
			case LEVEL_FATAL_ERROR :
				buffer.append(LABEL_FATAL_ERROR);
				break;
		}

		buffer.append(B);

		if ((evt.getCode() != null) && (!evt.getCode().equals(""))) {
			buffer.append("(");
			buffer.append(evt.getCode());
			buffer.append(") ");
		}

		buffer.append(evt.getMessage());
		buffer.append(". ");
		buffer.append(evt.getOtherMessage());

		return buffer.toString();

	}
	
	public void fixSize(StringBuffer destino, String cadena, int size) {
		int sizeCadena = cadena.length();
		if (sizeCadena > size) {
			destino.append(cadena.substring(sizeCadena-size, sizeCadena));
		} else {
			for (int i = 0; i < size - sizeCadena; i++) {
				destino.append(' ');
			}
			destino.append(cadena);
		}
	}
	
	/**
	 * Devuelve el string tal cual se va a guardar en el archivo log con el método log() del objeto Resources..
	 * Creation date: (18-Sep-01 2:59:19 PM)
	 * @return java.lang.String
	 */
	public String getLogString(EventsRepository repository) {

		if (repository.getEvents().size() == 0)
			return null;

		StringBuffer buffer = new StringBuffer();

		// Título y separador
		//buffer.append(CRLF);
		//buffer.append(DELIMITER_LOG);
		buffer.append(LABEL_PRINCIPAL);
		buffer.append(CRLF);

		// datos del events repository
		buffer.append("USR: ");
		buffer.append(repository.getUserIdentifyingString());
		buffer.append(CRLF);

		buffer.append("REQ: ");
		buffer.append(repository.getRequest());
		buffer.append(CRLF);

		// datos del evento principal 
		Event evt = (Event) repository.getEvents().elementAt(repository.getPrincipal());
/*		switch (evt.getLevel()) {
			case LEVEL_TRACE :
				buffer.append(LABEL_TRACE);
				break;
			case LEVEL_INFORMATIONAL :
				buffer.append(LABEL_INFORMATIONAL);
				break;
			case LEVEL_WARNING :
				buffer.append(LABEL_WARNING);
				break;
			case LEVEL_ERROR :
				buffer.append(LABEL_ERROR);
				break;
			case LEVEL_FATAL_ERROR :
				buffer.append(LABEL_FATAL_ERROR);
				break;
		}
		buffer.append(B);
		buffer.append(formatter.format(evt.getTimeStamp()));
		buffer.append(B);
		buffer.append(evt.getClassName());
		buffer.append(B);
		if ((evt.getCode() != null) && (!evt.getCode().equals(""))) {
			buffer.append("(");
			buffer.append(evt.getCode());
			buffer.append(") ");
		}
		buffer.append(evt.getMessage());
		buffer.append(CRLF);

		if ((evt.getOtherMessage() != null) && (!evt.getOtherMessage().equals(""))) {
			buffer.append(TAB);
			buffer.append(evt.getOtherMessage());
			buffer.append(CRLF);
		}

		if ((evt.getTrace() != null) && (!evt.getTrace().equals(""))) {
			buffer.append(TAB);
			buffer.append(evt.getTrace());
			buffer.append(CRLF);
		}
*/
		// por cada uno de los eventos	
		Iterator elements = repository.getEvents().iterator();
		while (elements.hasNext()) {

			evt = (Event) elements.next();
//			buffer.append(TAB);

			buffer.append(formatter.format(evt.getTimeStamp()));
			buffer.append(B);
			fixSize(buffer, evt.getClassName(), 41);
			buffer.append(B);
			switch (evt.getLevel()) {
				case LEVEL_TRACE :
					buffer.append(LABEL_TRACE);
					break;
				case LEVEL_INFORMATIONAL :
					buffer.append(LABEL_INFORMATIONAL);
					break;
				case LEVEL_WARNING :
					buffer.append(LABEL_WARNING);
					break;
				case LEVEL_ERROR :
					buffer.append(LABEL_ERROR);
					break;
				case LEVEL_FATAL_ERROR :
					buffer.append(LABEL_FATAL_ERROR);
					break;
			}
			buffer.append(B);
			if ((evt.getCode() != null) && (!evt.getCode().equals(""))) {
				buffer.append("(");
				buffer.append(evt.getCode());
				buffer.append(") ");
			}
			buffer.append(evt.getMessage());
			buffer.append(CRLF);

			if ((evt.getOtherMessage() != null) && (!evt.getOtherMessage().equals(""))) {
				buffer.append(TAB);
				buffer.append(TAB);
				buffer.append(evt.getOtherMessage());
				buffer.append(CRLF);
			}

			if ((evt.getTrace() != null) && (!evt.getTrace().equals(""))) {
				buffer.append(TAB);
				buffer.append(TAB);
				buffer.append(evt.getTrace());
				buffer.append(CRLF);
			}

		}
		return buffer.toString();
	}

	/**
	 * Devuelve un String con el código y el mensaje del evento principal, listo para ser presentado al usuario en un JSP de error..
	 * Creation date: (18-Sep-01 3:00:13 PM)
	 */
	public String getNiceMessage(EventsRepository repository) {

		StringBuffer buffer = new StringBuffer();
		Event evt = (Event) repository.getEvents().elementAt(repository.getPrincipal());
		buffer.append(evt.getMessage());

		return buffer.toString();
	}
	/**
	 * Devuelve un String con el código y el mensaje del evento principal, listo para ser presentado al usuario en un JSP de error..
	 * Creation date: (18-Sep-01 3:00:13 PM)
	 */
	public String getOtherMessage(EventsRepository repository) {

		StringBuffer buffer = new StringBuffer();
		Event evt = (Event) repository.getEvents().elementAt(repository.getPrincipal());
		buffer.append(evt.getOtherMessage());

		String otherMessage = buffer.toString();
		if (otherMessage.equals(""))
			return getNiceMessage(repository);
		else
			return otherMessage;

	}
	/**
	 * Devuelve true si alguno de sus eventos está marcado para mandar e-mail.
	 * Creation date: (18-Sep-01 2:57:20 PM)
	 * @return boolean
	 */
	public boolean isSendMail(EventsRepository repository) {

		boolean sendMail = false;

		Iterator elements = repository.getEvents().iterator();

		while (elements.hasNext()) {

			Event evtmsg = (Event) elements.next();
			if (evtmsg.getSendMail()) {
				sendMail = true;
				break;
			}
		}
		return sendMail;
	}

	public String getLogString(Event evt) {

		if (evt == null)
			return "No event";

		StringBuffer buffer = new StringBuffer();

		buffer.append(formatter.format(evt.getTimeStamp()));
		buffer.append(B);
		fixSize(buffer, evt.getClassName(), 41);
		buffer.append(B);
		switch (evt.getLevel()) {
			case LEVEL_TRACE :
				buffer.append(LABEL_TRACE);
				break;
			case LEVEL_INFORMATIONAL :
				buffer.append(LABEL_INFORMATIONAL);
				break;
			case LEVEL_WARNING :
				buffer.append(LABEL_WARNING);
				break;
			case LEVEL_ERROR :
				buffer.append(LABEL_ERROR);
				break;
			case LEVEL_FATAL_ERROR :
				buffer.append(LABEL_FATAL_ERROR);
				break;
		}
		buffer.append(B);
		if ((evt.getCode() != null) && (!evt.getCode().equals(""))) {
			buffer.append("(");
			buffer.append(evt.getCode());
			buffer.append(") ");
		}
		buffer.append(evt.getMessage());
		buffer.append(CRLF);

		if ((evt.getOtherMessage() != null) && (!evt.getOtherMessage().equals(""))) {
			buffer.append(TAB);
			buffer.append(evt.getOtherMessage());
			buffer.append(CRLF);
		}

		if ((evt.getTrace() != null) && (!evt.getTrace().equals(""))) {
			buffer.append(TAB);
			buffer.append(evt.getTrace());
			buffer.append(CRLF);
		}

		return buffer.toString();
	}
	
	public void endRequestLog(EventsRepository events, Connection conn) {
		if (isSendMail(events)) {
			MailDataBean data = new MailDataBean();
			StringBuffer subject = new StringBuffer();
			if (SystemResources.getInstance().getSubjectIncludeUsr()) {
				subject.append(events.getUserIdentifyingString()).append("::");
			}
			if (SystemResources.getInstance().getSubjectIncludeReq()) {
				subject.append(events.getRequest()).append("::");
			}
			if (SystemResources.getInstance().getSubjectIncludeErr()) {
				subject.append(events.getNiceMessage()).append("::");
			}
			data.setSubject(subject.toString());
			data.setBody(getLogString(events));
			try {
				MailProcessor.getInstance().saveMail(data, conn);
			} catch (MailException e) {
				addEvent(events, Errors.EC_CANNOT_SEND_MAIL, this, e.toString(), e);
			}
		}
		writeDownLog(events);
	}

	public void writeDownLog(EventsRepository events) {
		if (events.getEvents().size() > 0) {
			SystemResources.getInstance().hardLog(
				events.getLevelPrincipal(),
				events.getPrincipalEvent().getClassName(),
				getLogString(events)
			);
		}
	}
}

