package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XDirVisa extends SunarpBean{
	private XObject xobject;

	public static final String dir1Desa = "";
	public static final String dir2Desa = "";
	public static final String dir1Prod = "";
	public static final String dir2Prod = "";
	public static final String dirDesa = "";
	
	public XDirVisa(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public String getDir1Desa() throws XObjectException {
		return xobject.getElementTextValue("dir1Desa");
	}

	public String getDir2Desa() throws XObjectException {
		return xobject.getElementTextValue("dir2Desa");
	}

	public String getDir1Prod() throws XObjectException {
		return xobject.getElementTextValue("dir1Prod");
	}

	public String getDir2Prod() throws XObjectException {
		return xobject.getElementTextValue("dir2Prod");
	}
	
	public String getDirDesa() throws XObjectException 
	{
		return xobject.getElementTextValue("dirDesa");
	}
	
}

