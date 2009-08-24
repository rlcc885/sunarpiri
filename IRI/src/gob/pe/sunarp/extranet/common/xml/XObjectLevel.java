package gob.pe.sunarp.extranet.common.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class XObjectLevel extends SunarpBean{
	private XObject xobject;

	public static final String MAP = "map";

	public XObjectLevel(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public XMap[] getMaps() throws XObjectException {
		XObject[] xobjects = xobject.getElements(XObjectLevel.MAP);
		XMap[] result = new XMap[xobjects.length];
		for (int i = 0; i < xobjects.length; i++) {
			result[i] = new XMap(xobjects[i]);
		}
		return result;
	}

}

