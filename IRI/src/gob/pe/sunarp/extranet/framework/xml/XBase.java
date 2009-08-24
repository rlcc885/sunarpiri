package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XBase extends SunarpBean{
	private XObject xobject;

	public XBase(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public int getCodigo() throws XObjectException {
		return Integer.parseInt(xobject.getAttribute("codigo"));
	}

	public String getDriver() throws XObjectException {
		return xobject.getAttribute("driver");
	}
	
	public String getUrl() throws XObjectException {
		return xobject.getAttribute("url");
	}	
	
	public String getUser() throws XObjectException {
		return xobject.getAttribute("user");
	}
	
	public String getPassword() throws XObjectException {
		return xobject.getAttribute("password");
	}	
	
	public int getMaxConnections() throws XObjectException {
		return Integer.parseInt(xobject.getAttribute("max"));
	}	
	
	public long getExpiryTime() throws XObjectException {
		return Long.parseLong(xobject.getAttribute("expiryTime"));
	}	
	
	public long getTimeOut() throws XObjectException {
		return Long.parseLong(xobject.getAttribute("timeOut"));
	}		
}

