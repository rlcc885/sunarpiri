package gob.pe.sunarp.extranet.common.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class XMap extends SunarpBean{
	private XObject xobject;

	public static final String CLASS = "class";
	public static final String LEVEL = "level";

	public XMap(XObject xobject) {
		super();
		this.xobject = xobject;
	}


	public String getActionClass() throws XObjectException {
		return xobject.getAttribute(XMap.CLASS);
	}

	public int getLevel() throws XObjectException {
		return Integer.parseInt(xobject.getAttribute(XMap.LEVEL));
	}

}

