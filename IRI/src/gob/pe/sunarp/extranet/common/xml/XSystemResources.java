package gob.pe.sunarp.extranet.common.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.io.*;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (3/7/2002 11:11:12 AM)
 * @author: Iván Peralta
 */
public class XSystemResources extends SunarpBean{
	private XObject xobject;

	public static final String EMAIL = "email";
	public static final String LOG = "log";

	public XSystemResources(String xmlFile) throws FileNotFoundException, XObjectException {
		this(new XObject());
		this.xobject.setXMLFile(xmlFile);
	}

	public XSystemResources(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public XMail getMail() throws XObjectException {
		return new XMail(xobject.getElement(EMAIL));
	}

	public XLog getLog() throws XObjectException {
		return new XLog(xobject.getElement(LOG));
	}

}