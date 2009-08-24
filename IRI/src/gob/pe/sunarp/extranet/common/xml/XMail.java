package gob.pe.sunarp.extranet.common.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.io.*;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (3/7/2002 11:11:12 AM)
 * @author: Iván Peralta
 */
public class XMail extends SunarpBean{
	private XObject xobject;

	public static final String SMTP = "smtp";
	public static final String EMAIL_FROM = "emailFrom";
	public static final String EMAIL_TO_DEFAULT = "emailToDefault";
	public static final String SECONDS = "seconds";
	public static final String TEMPLATES_DIRECTORY = "templatesDirectory";

	public XMail(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public String getSMTP() throws XObjectException {
		return xobject.getElementTextValue(XMail.SMTP);
	}

	public String getEmailFrom() throws XObjectException {
		return xobject.getElementTextValue(XMail.EMAIL_FROM);
	}

	public String getEmailToDefault() throws XObjectException {
		return xobject.getElementTextValue(XMail.EMAIL_TO_DEFAULT);
	}

	public int getSeconds() throws XObjectException {
		String valor = xobject.getElementTextValue(XMail.SECONDS);
		if (valor == null) return 5*60; // 5 minutos
		return Integer.parseInt(valor);
	}

	public String getTemplatesDirectory() throws XObjectException {
		return xobject.getElementTextValue(XMail.TEMPLATES_DIRECTORY);
	}

	public boolean getSubjectIncludeUsr() throws XObjectException {
		return xobject.getElementTextValue("subjectIncludeUsr").equals("true");
	}

	public boolean getSubjectIncludeReq() throws XObjectException {
		return xobject.getElementTextValue("subjectIncludeReq").equals("true");
	}

	public boolean getSubjectIncludeErr() throws XObjectException {
		return xobject.getElementTextValue("subjectIncludeErr").equals("true");
	}

}