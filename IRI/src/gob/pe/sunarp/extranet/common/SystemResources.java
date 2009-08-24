package gob.pe.sunarp.extranet.common;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class SystemResources implements OutputConstants, Errors {

	private static SystemResources systemResources;
	
	private PrintWriter log;
	private BufferedOutputStream logBuffer;
	private FileOutputStream logFile;
	private int sysMinLevel;

	private String smtp;
	private String emailFrom;
	private String emailToDefault;
	private int secondsEmail;
	private boolean subjectIncludeUsr;
	private boolean subjectIncludeReq;
	private boolean subjectIncludeErr;

	private Hashtable registeredErrors = new Hashtable();

	private Hashtable attributes = new Hashtable();
	private Hashtable objectLevel = new Hashtable();

	private String templatesDirectory;		//Carpeta en donde se guardan los templates de envio de e-mails

	private SimpleDateFormat formatter = new SimpleDateFormat("-yyyy.MM.dd-HH.mm.ss-SSS.'log'");
	private SimpleDateFormat formatterLog = new SimpleDateFormat(TSTMP_FORMAT_LOG);


	private SystemResources() {
	}
	public void addRegisteredError(RegisteredError rError) {
		this.registeredErrors.put(rError.getErrorCode().trim(), rError);
	}
	public void addObjectLevel(String className, int level) {
		this.objectLevel.put(className, new Integer(level));
	}
	public void clearAttributes() {
		this.attributes.clear();
	}
	public void clearRegisteredError() {
		this.registeredErrors.clear();
	}
	public void close() {
		log.close();
	}
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	public Object getAttributeNames() {
		return attributes.keys();
	}
	public java.lang.String getEmailToDefault() {
		return emailToDefault;
	}
	public static SystemResources getInstance() {
		if (systemResources == null) {
			systemResources = new SystemResources();
		}
		return systemResources;
	}
	public RegisteredError getRegisteredError(String errorCode) {
		return (RegisteredError) registeredErrors.get(errorCode);
	}
	public int getObjectLevel(String className) {
		try {
			return ((Integer) objectLevel.get(className)).intValue();
		} catch (Throwable t) {
			return getSysMinLevel();
		}
	}
	public int getSysMinLevel() {
		return sysMinLevel;
	}
	public java.lang.String getTemplatesDirectory() {
		return templatesDirectory;
	}
	public synchronized void hardLog(int level, String clase, String message) {
		
		if (level < sysMinLevel) return;
		
		java.util.Date fecha = new java.util.Date();
		
		StringBuffer buffer = new StringBuffer();
//		buffer.append(DELIMITER_LOG);
//		buffer.append(CRLF);

		buffer.append(formatterLog.format(fecha));
		buffer.append(B);
		buffer.append(clase);
		buffer.append(B);
		switch (level) {
			case LEVEL_TRACE: buffer.append(LABEL_TRACE); break;
			case LEVEL_INFORMATIONAL: buffer.append(LABEL_INFORMATIONAL); break;
			case LEVEL_WARNING: buffer.append(LABEL_WARNING); break;
			case LEVEL_ERROR: buffer.append(LABEL_ERROR); break;
			case LEVEL_FATAL_ERROR: buffer.append(LABEL_FATAL_ERROR); break;
		}

		buffer.append(CRLF);
		buffer.append(message);
		buffer.append(CRLF);
		buffer.append(CRLF);

		log.print(buffer.toString());
		log.flush();
		try {
			logBuffer.flush();
			logFile.flush();
		} catch (IOException e) {
			System.err.println("OCURRIO UN ERROR AL ESCRIBIR AL ARCHIVO LOG");
			e.printStackTrace();
		}
	}
	public void hardLog(int level, String clase, String message, Throwable e) {
		
		if (level < sysMinLevel) return;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(message);
		buffer.append(CRLF);
		
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

		buffer.append(sw.toString());

		hardLog(level, clase, buffer.toString());
	}
	public void removeAttribute(String name) {
		attributes.put(name, null);
	}
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}
	public void setLogFile(String name) throws FileNotFoundException {
		String nombre = name + formatter.format(new java.util.Date());
		logFile = new FileOutputStream(nombre);
		logBuffer = new BufferedOutputStream(logFile);
		log = new PrintWriter(logBuffer);
		System.out.println("Nombre archivo log: " + nombre);
	}
	public void setSysMinLevel(int level) {
		sysMinLevel = level;
	}
	public void setEmailToDefault(java.lang.String newEmailToDefault) {
		emailToDefault = newEmailToDefault;
	}
	public void setTemplatesDirectory(java.lang.String newTemplatesDirectory) {
		templatesDirectory = newTemplatesDirectory;
	}
	/**
	 * Gets the emailFrom
	 * @return Returns a String
	 */
	public String getEmailFrom() {
		return emailFrom;
	}
	/**
	 * Sets the emailFrom
	 * @param emailFrom The emailFrom to set
	 */
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	/**
	 * Gets the smtp
	 * @return Returns a String
	 */
	public String getSmtp() {
		return smtp;
	}
	/**
	 * Sets the smtp
	 * @param smtp The smtp to set
	 */
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	/**
	 * Gets the secondsEmail
	 * @return Returns a int
	 */
	public int getSecondsEmail() {
		return secondsEmail;
	}
	/**
	 * Sets the secondsEmail
	 * @param secondsEmail The secondsEmail to set
	 */
	public void setSecondsEmail(int secondsEmail) {
		this.secondsEmail = secondsEmail;
	}

	/**
	 * Gets the subjectIncludeUsr
	 * @return Returns a boolean
	 */
	public boolean getSubjectIncludeUsr() {
		return subjectIncludeUsr;
	}
	/**
	 * Sets the subjectIncludeUsr
	 * @param subjectIncludeUsr The subjectIncludeUsr to set
	 */
	public void setSubjectIncludeUsr(boolean subjectIncludeUsr) {
		this.subjectIncludeUsr = subjectIncludeUsr;
	}

	/**
	 * Gets the subjectIncludeReq
	 * @return Returns a boolean
	 */
	public boolean getSubjectIncludeReq() {
		return subjectIncludeReq;
	}
	/**
	 * Sets the subjectIncludeReq
	 * @param subjectIncludeReq The subjectIncludeReq to set
	 */
	public void setSubjectIncludeReq(boolean subjectIncludeReq) {
		this.subjectIncludeReq = subjectIncludeReq;
	}

	/**
	 * Gets the subjectIncludeErr
	 * @return Returns a boolean
	 */
	public boolean getSubjectIncludeErr() {
		return subjectIncludeErr;
	}
	/**
	 * Sets the subjectIncludeErr
	 * @param subjectIncludeErr The subjectIncludeErr to set
	 */
	public void setSubjectIncludeErr(boolean subjectIncludeErr) {
		this.subjectIncludeErr = subjectIncludeErr;
	}

}
