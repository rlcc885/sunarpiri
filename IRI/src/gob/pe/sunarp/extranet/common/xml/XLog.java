package gob.pe.sunarp.extranet.common.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class XLog extends SunarpBean{
	private XObject xobject;

	public static final String LOG_FILE = "logFile";
	public static final String SYS_MIN_LEVEL = "sysMinLevel";
	public static final String OBJECT_LEVEL = "object-level";

	public XLog(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public String getLogFile() throws XObjectException {
		return xobject.getElementTextValue(XLog.LOG_FILE);
	}

	public int getSysMinLevel() throws XObjectException {
		return Integer.parseInt(xobject.getElementTextValue(XLog.SYS_MIN_LEVEL));
	}

	public XObjectLevel getObjectLevel() throws XObjectException {
		return new XObjectLevel(xobject.getElement(XLog.OBJECT_LEVEL));
	}
}

