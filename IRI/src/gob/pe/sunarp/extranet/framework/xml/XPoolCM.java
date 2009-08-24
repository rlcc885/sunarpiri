package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XPoolCM extends SunarpBean{
	private XObject xobject;


	public static final String LIBSERVER = "libserver";
	public static final String USER = "user";
	public static final String PASS = "pass";
	public static final String POOL_SIZE = "poolSize";

	public XPoolCM(XObject xobject) {
		super();
		this.xobject = xobject;
	}


	public String getLibserver() throws XObjectException {
		return xobject.getAttribute(LIBSERVER);
	}

	public String getUser() throws XObjectException {
		return xobject.getAttribute(USER);
	}

	public String getPass() throws XObjectException {
		return xobject.getAttribute(PASS);
	}

	public int getPoolSize() throws XObjectException {
		return Integer.parseInt(xobject.getAttribute(POOL_SIZE));
	}
	
	public long getExpiryTime() throws XObjectException {
		return Long.parseLong(xobject.getAttribute("expiryTime"));
	}

	public long getTimeOut() throws XObjectException {
		return Long.parseLong(xobject.getAttribute("timeOut"));
	}		

	public boolean getEnabled() throws XObjectException {
		return xobject.getAttribute("enabled").equals("true");
	}		
}

