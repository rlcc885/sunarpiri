package gob.pe.sunarp.extranet.framework;
import javax.mail.*;
import javax.mail.internet.*;
import gob.pe.sunarp.extranet.util.Propiedades;

public class MailSender {
	private static MailSender single;
	
	public synchronized static  MailSender getInstance() {
		if (single == null) {
			single = new MailSender();
		}
		return single;
	}
	
	private MailSender() {
	}

	public void sendMail(String from, String to, String cc, String bcc, String subject, String text) 
		throws MessagingException{
	    String status = null;
	
	        // Crear una sesion JAVAMAIL
	        java.util.Properties properties = System.getProperties();
	        properties.put("mail.smtp.host", Propiedades.getInstance().getSendMailSMTPServer());
			//LSJ 06/11/2002
	  	      properties.put("mail.smtp.class", "com.sun.mail.smtp");
			
			Session session = Session.getInstance(properties, null);
	        MimeMessage message = new MimeMessage(session);
	        Address fromAddress = new InternetAddress(from);
	        message.setFrom(fromAddress);
	        Address[] toAddresses = InternetAddress.parse(to);
	        message.setRecipients(Message.RecipientType.TO, toAddresses);
	        if(cc != null){
		        Address[] ccAddresses = InternetAddress.parse(cc);
		        message.setRecipients(Message.RecipientType.CC, ccAddresses);
	        }
	        if(bcc != null){
		        Address[] bccAddresses = InternetAddress.parse(bcc);
		        message.setRecipients(Message.RecipientType.BCC, bccAddresses);
	        }
	        message.setSubject(subject==null?"":subject);
	  		//modificado por LSJ
				message.setContent(text == null ? "" : text, "text/plain");
				message.setSentDate(new java.util.Date());
			Transport.send(message);
	}
}

