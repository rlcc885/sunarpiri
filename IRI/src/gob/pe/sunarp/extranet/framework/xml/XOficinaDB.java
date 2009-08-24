package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XOficinaDB extends SunarpBean{
	private XObject xobject;

	public XOficinaDB(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public String getCodigo() throws XObjectException {
		return xobject.getAttribute("codigo");
	}

	public String getUrlDB() throws XObjectException {
		return xobject.getAttribute("urlDB");
	}
	
	public String getUserDB() throws XObjectException {
		return xobject.getAttribute("userDB");
	}	
	
	public String getPwdDB() throws XObjectException {
		return xobject.getAttribute("pwdDB");
	}		
}

