package gob.pe.sunarp.extranet.common;
import java.util.*;
import java.io.*;
import java.text.*;

public class EventsRepository extends SunarpBean{

	private int principal;
	private boolean hayPrincipal; //índice del evento principal dentro del vector "events"
	private int levelPrincipal;

	private int minLevel;
	private String request; //: url y queryString que originó un ControllerCommand o mensaje MQ que originó la transacción 
	private String userIdentifyingString;
	private Vector events = new Vector(); //Vector de EventMessage donde se describen los eventos

	/**
	 * EventsRepository constructor comment.
	 */
	public EventsRepository() {
		super();
	}

	public void addEvent(Event event) {
		if (minLevel < 0) {
			System.err.print(EventsProcessor.getInstance().getLogString(event));
		}
		events.addElement(event);
	}

	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	public void setPrincipalEvent() {

		hayPrincipal = true;
		this.principal = events.size() - 1;
		levelPrincipal = ((Event) events.elementAt(principal)).getLevel();

	}

	/**
	 * Devuelve el evento principal..
	 * Creation date: (18-Sep-01 3:00:13 PM)
	 */
	public Event getPrincipalEvent() {

		if (events.size() > 0)
			return (Event) events.elementAt(this.principal);
		return null;
	}


	/**
	 * Gets the principal
	 * @return Returns a int
	 */
	public int getPrincipal() {
		return principal;
	}
	/**
	 * Sets the principal
	 * @param principal The principal to set
	 */
	public void setPrincipal(int principal) {
		this.principal = principal;
	}

	/**
	 * Gets the hayPrincipal
	 * @return Returns a boolean
	 */
	public boolean getHayPrincipal() {
		return hayPrincipal;
	}
	/**
	 * Sets the hayPrincipal
	 * @param hayPrincipal The hayPrincipal to set
	 */
	public void setHayPrincipal(boolean hayPrincipal) {
		this.hayPrincipal = hayPrincipal;
	}

	/**
	 * Gets the levelPrincipal
	 * @return Returns a int
	 */
	public int getLevelPrincipal() {
		return levelPrincipal;
	}
	/**
	 * Sets the levelPrincipal
	 * @param levelPrincipal The levelPrincipal to set
	 */
	public void setLevelPrincipal(int levelPrincipal) {
		this.levelPrincipal = levelPrincipal;
	}

	/**
	 * Gets the minLevel
	 * @return Returns a int
	 */
	public int getMinLevel() {
		return minLevel;
	}
	/**
	 * Sets the minLevel
	 * @param minLevel The minLevel to set
	 */
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	/**
	 * Gets the request
	 * @return Returns a String
	 */
	public String getRequest() {
		return request;
	}
	/**
	 * Sets the request
	 * @param request The request to set
	 */
	public void setRequest(String request) {
		if (minLevel < 0) {
			System.err.println("Request: " + request);
		}
		this.request = request;
	}

	/**
	 * Gets the events
	 * @return Returns a Vector
	 */
	public Vector getEvents() {
		return events;
	}
	/**
	 * Sets the events
	 * @param events The events to set
	 */
	public void setEvents(Vector events) {
		this.events = events;
	}

	/**
	 * Gets the userIdentifyingString
	 * @return Returns a String
	 */
	public String getUserIdentifyingString() {
		return userIdentifyingString;
	}
	/**
	 * Sets the userIdentifyingString
	 * @param userIdentifyingString The userIdentifyingString to set
	 */
	public void setUserIdentifyingString(String userIdentifyingString) {
		if (minLevel < 0) {
			System.err.println("User: " + userIdentifyingString);
		}
		this.userIdentifyingString = userIdentifyingString;
	}

	public String getLogString() {
		return EventsProcessor.getInstance().getLogString(this);
	}
	public String getCompleteMessage() {
		return EventsProcessor.getInstance().getCompleteMessage(this);
	}
	public String getOtherMessage() {
		return EventsProcessor.getInstance().getOtherMessage(this);
	}
	public String getNiceMessage() {
		return EventsProcessor.getInstance().getNiceMessage(this);
	}
}

