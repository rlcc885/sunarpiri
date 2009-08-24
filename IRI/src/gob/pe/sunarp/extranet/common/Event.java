package gob.pe.sunarp.extranet.common;
public class Event extends SunarpBean{
	private String code; //Código del evento principal
	private String message; //mensaje del evento.
	private int level; //nivel del evento (0, 1, 2, 3, 4)
	private boolean sendMail; //Booleano que indica si se manda e-mail o no
	private String otherMessage; //Mensaje extra del evento principal
	private String trace; //Trace del error (de printStackTrace del evento)
	private java.util.Date timestamp; //Timestamp del evento 
	private String className; //Clase que generó el evento 

	/**
	 * EventMessage constructor comment.
	 */
	public Event() {
		super();
	}
	/**
	 * getter para la variable className de tipo String.
	 * Creation date: (18-Sep-01 2:41:18 PM)
	 * @return java.lang.String
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * getter para la variable code de tipo String.
	 * Creation date: (18-Sep-01 2:41:18 PM)
	 * @return java.lang.String
	 */
	public String getCode() {
		return code;
	}
	/**
	 * getter para la variable level de tipo int.
	 * Creation date: (18-Sep-01 2:41:18 PM)
	 * @return java.lang.String
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * getter para la variable message de tipo String.
	 * Creation date: (18-Sep-01 2:41:18 PM)
	 * @return java.lang.String
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * getter para la variable othermessage de tipo String.
	 * Creation date: (18-Sep-01 2:41:18 PM)
	 * @return java.lang.String
	 */
	public String getOtherMessage() {
		return (otherMessage == null) ? "" : otherMessage;
	}
	/**
	 * getter para la variable sendMail de tipo boolean.
	 * Creation date: (18-Sep-01 2:41:18 PM)
	 * @return java.lang.String
	 */
	public boolean getSendMail() {
		return sendMail;
	}
	public java.util.Date getTimeStamp() {
		return timestamp;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (11/22/2001 4:56:26 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getTrace() {
		return trace;
	}
	/**
	 * setter de la variable className
	 * Creation date: (18-Sep-01 2:40:20 PM)
	 * @return java.lang.String
	 * @param code java.lang.String
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * setter para la variable code
	 * Creation date: (18-Sep-01 2:40:20 PM)
	 * @return java.lang.String
	 * @param code java.lang.String
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * setter para la variable level
	 * Creation date: (18-Sep-01 2:40:20 PM)
	 * @return java.lang.String
	 * @param code java.lang.String
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * setter para la variable message.
	 * Creation date: (18-Sep-01 2:40:20 PM)
	 * @return java.lang.String
	 * @param code java.lang.String
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * setter para la variable othermessage.
	 * Creation date: (18-Sep-01 2:40:20 PM)
	 * @return java.lang.String
	 * @param code java.lang.String
	 */
	public void setOtherMessage(String otherMessage) {
		this.otherMessage = otherMessage;
	}
	/**
	 * setter para la variable sendmail.
	 * Creation date: (18-Sep-01 2:40:20 PM)
	 * @return java.lang.String
	 * @param code java.lang.String
	 */
	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}
	/**
	 * setter para la variable timeStamp.
	 * Creation date: (18-Sep-01 2:40:20 PM)
	 * @return java.lang.String
	 * @param code java.lang.String
	 */
	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (11/22/2001 4:56:26 PM)
	 * @param newTrace java.lang.String
	 */
	public void setTrace(java.lang.String newTrace) {
		trace = newTrace;
	}
	/**
	 * getter para la variable message de tipo String.
	 * Creation date: (18-Sep-01 2:41:18 PM)
	 * @return java.lang.String
	 */
	public String toString() {
		return message;
	}

}