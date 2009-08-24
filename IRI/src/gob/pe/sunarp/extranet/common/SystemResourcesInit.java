package gob.pe.sunarp.extranet.common;

import gob.pe.sunarp.extranet.common.xml.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SystemResourcesInit {
	
	private static SystemResourcesInit systemResourcesInit;
	
	private String selectRegErrorsQuery;
	
	private SystemResourcesInit() {
		StringBuffer selectRegErrorsQuery = new StringBuffer();
		selectRegErrorsQuery.append("select error_codigo, err_descripcion, err_level, err_mail");
		selectRegErrorsQuery.append("	from TM_ERROR");
		this.selectRegErrorsQuery = selectRegErrorsQuery.toString();
	}
	
	public static SystemResourcesInit getInstance() {
		if (systemResourcesInit == null) {
			systemResourcesInit = new SystemResourcesInit();
		}
		return systemResourcesInit;
	}
	
	public void init(String configFile, String root, Connection conn) throws XObjectException, FileNotFoundException, SQLException{
		XSystemResources xsr = new XSystemResources(configFile);
		
		SystemResources sr = SystemResources.getInstance();

		XMail xmail = xsr.getMail();
		sr.setEmailFrom(xmail.getEmailFrom());
		sr.setEmailToDefault(xmail.getEmailToDefault());
		File templatesDir = new File(root, xmail.getTemplatesDirectory());
		if (!templatesDir.exists()) templatesDir.mkdirs();
		sr.setSecondsEmail(xmail.getSeconds());
		sr.setTemplatesDirectory(templatesDir.getAbsolutePath());
		sr.setSmtp(xmail.getSMTP());
		sr.setSubjectIncludeUsr(xmail.getSubjectIncludeUsr());
		sr.setSubjectIncludeReq(xmail.getSubjectIncludeReq());
		sr.setSubjectIncludeErr(xmail.getSubjectIncludeErr());
		
		XLog xlog = xsr.getLog();
		File logFile = new File(root, xlog.getLogFile());
		File logDir = logFile.getParentFile();
		if (!logDir.exists()) logDir.mkdirs();
		sr.setLogFile(logFile.getAbsolutePath());
		sr.setSysMinLevel(xlog.getSysMinLevel());
		
		XObjectLevel xae = xlog.getObjectLevel();
		XMap[] xmaps = xae.getMaps();
		for (int i = 0; i < xmaps.length; i++) {
			sr.addObjectLevel(xmaps[i].getActionClass(), xmaps[i].getLevel());
		}

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectRegErrorsQuery);
		
		while (rs.next()) {
			RegisteredError re = new RegisteredError();
			re.setErrorCode(rs.getString(1));
			re.setMessage(rs.getString(2));
			re.setLevel((int)rs.getLong(3));
			re.setEmail(rs.getLong(4) == 1);
			
			sr.addRegisteredError(re);
		}
		rs.close();
		stmt.close();
		
	}
	
}

