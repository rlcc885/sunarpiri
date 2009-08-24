package gob.pe.sunarp.extranet.common;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

public class MailProcessor {

	private static MailProcessor mailProcessor;
	
	private SystemResources systemResources;
	private String insertarMailQuery;
	private Hashtable templates = new Hashtable();

	private MailProcessor() {
		StringBuffer insertarMailQuery = new StringBuffer();
		insertarMailQuery.append("insert into mail (");
		insertarMailQuery.append("	mail_refnum, mail_mfrom, mail_mto, mail_mbcc, mail_subj, ");
		insertarMailQuery.append("	mail_body, mail_estado, mail_sender_agent, mail_store_date) ");
		insertarMailQuery.append("values (mail_seq.nextval, ?, ?, ?, ?, ?, 0, ?, ? )");
		this.insertarMailQuery = insertarMailQuery.toString();
		
		systemResources = SystemResources.getInstance();
	}

	public static MailProcessor getInstance() {
		if (mailProcessor == null) {
			mailProcessor = new MailProcessor();
		}
		return mailProcessor;
	}

	public void saveMail(MailDataBean mailData, Connection conn) throws MailException {
		PreparedStatement pStmt = null;
		try {
			if ( (mailData.getSubject() == null) || (mailData.getBody() == null) ) {
				throw new IllegalArgumentException("MailDataBean debe tener al menos un subject y un body.");
			}

			if (mailData.getTo() == null) mailData.setTo(systemResources.getEmailToDefault());
			if (mailData.getFrom() == null) mailData.setFrom(systemResources.getEmailFrom());

			if (mailData.getSubject().length() > 254) mailData.setSubject(mailData.getSubject().substring(0,254));
			if (mailData.getBody().length() > 2279) mailData.setBody(mailData.getBody().substring(0,2279));
			
			pStmt = conn.prepareStatement(this.insertarMailQuery);
			pStmt.setString(1, mailData.getFrom());
			pStmt.setString(2, mailData.getTo());
			pStmt.setString(3, mailData.getBcc());
			pStmt.setString(4, mailData.getSubject());
			pStmt.setString(5, mailData.getBody());
			pStmt.setString(6, mailData.getSenderAgent()==null?"WEB":mailData.getSenderAgent());
			pStmt.setTimestamp(7, new java.sql.Timestamp(new java.util.Date().getTime()));
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
//			EventsProcessor.getInstance().addEvent(events, Errors.EC_CANNOT_SEND_MAIL, this, "", e);
			throw new MailException("Sucedió un error SQL al tratar de grabar e-mail: " + e.toString());
		}finally{
			JDBC.getInstance().closeStatement(pStmt);
		}
	}

	private MailTemplate getTemplate(String nombreTemplate) {
		MailTemplate template = (MailTemplate)templates.get(nombreTemplate);

		if (template == null) {
			template = new MailTemplate(new File(systemResources.getTemplatesDirectory(), nombreTemplate).getAbsolutePath());
			templates.put(nombreTemplate, template);
		}

		return template;
	}

	public void saveMailTemplate(String nombreTemplate, Hashtable tokenValues, Connection conn) throws MailException {

		String[] texto = getTemplate(nombreTemplate).getText(tokenValues);
		MailDataBean data = new MailDataBean();
		data.setSubject(texto[0]);
		data.setBody(texto[1]);
		saveMail(data, conn);
	}

	public void saveMailTemplate(String nombreTemplate, Hashtable tokenValues, String to, Connection conn) throws MailException {

		String[] texto = getTemplate(nombreTemplate).getText(tokenValues);
		MailDataBean data = new MailDataBean();
		data.setSubject(texto[0]);
		data.setBody(texto[1]);
		data.setTo(to);
		saveMail(data, conn);
	}
}

