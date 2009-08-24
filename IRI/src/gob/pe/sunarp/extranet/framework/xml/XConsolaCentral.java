package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XConsolaCentral extends SunarpBean{
	private XObject xobject;

	public XConsolaCentral(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public XOficinaDB[] getOficinasDB() throws XObjectException {
		XObject[] xobjects = xobject.getElements("oficina");
		XOficinaDB[] result = new XOficinaDB[xobjects.length];
		for (int i = 0; i < xobjects.length; i++) {
			result[i] = new XOficinaDB(xobjects[i]);
		}
		return result;
	}					

	public XErrorManejado[] getErrorManejados() throws XObjectException {
		XObject[] xobjects = xobject.getElements("errorManejado");
		XErrorManejado[] result = new XErrorManejado[xobjects.length];
		for (int i = 0; i < xobjects.length; i++) {
			result[i] = new XErrorManejado(xobjects[i]);
		}
		return result;
	}					
}

