package gob.pe.sunarp.extranet.common;
public class MailDataBean extends SunarpBean{
	private String subject;
	private String body;
	private String to;
	private String bcc;
	private String from;
	private String senderAgent;
	/**
	 * Gets the subject
	 * @return Returns a String
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Sets the subject
	 * @param subject The subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the body
	 * @return Returns a String
	 */
	public String getBody() {
		return body;
	}
	/**
	 * Sets the body
	 * @param body The body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets the to
	 * @return Returns a String
	 */
	public String getTo() {
		return to;
	}
	/**
	 * Sets the to
	 * @param to The to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Gets the bcc
	 * @return Returns a String
	 */
	public String getBcc() {
		return bcc;
	}
	/**
	 * Sets the bcc
	 * @param bcc The bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
	 * Gets the from
	 * @return Returns a String
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * Sets the from
	 * @param from The from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Gets the senderAgent
	 * @return Returns a String
	 */
	public String getSenderAgent() {
		return senderAgent;
	}
	/**
	 * Sets the senderAgent
	 * @param senderAgent The senderAgent to set
	 */
	public void setSenderAgent(String senderAgent) {
		this.senderAgent = senderAgent;
	}

}

