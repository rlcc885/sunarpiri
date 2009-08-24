package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XErrorManejado extends SunarpBean{
	private XObject xobject;

	public XErrorManejado(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public String getCodigo() throws XObjectException {
		return xobject.getAttribute("codigo");
	}
	
	public String getDescripcion() throws XObjectException {
		return xobject.getAttribute("descripcion");
	}	
}

