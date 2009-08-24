package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XConexionesBD extends SunarpBean{
	private XObject xobject;

	public XConexionesBD(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public XBase[] getBases() throws XObjectException 
	{
		XObject[] xobjects = xobject.getElements("base");
		
		XBase[] result = new XBase[xobjects.length];
		
		for (int i = 0; i < xobjects.length; i++) 
		{
			result[i] = new XBase(xobjects[i]);
		}
		return result;
	}					
}

