package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;
import java.io.FileNotFoundException;

public class XWebappResources extends SunarpBean{
	private XObject xobject;
	public static final String TAM = "tam";
	public static final String POOL_CM = "poolCM";
	public static final String CONSOLA_CENTRAL = "consolaCentral";//cjvc77

	public XWebappResources(String xmlFile) throws FileNotFoundException, XObjectException {
		this(new XObject());
		this.xobject.setXMLFile(xmlFile);
	}

	public XWebappResources(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public XTam getTam() throws XObjectException {
		return new XTam(xobject.getElement(TAM));
	}

	public XPoolCM getPoolCM() throws XObjectException {
		return new XPoolCM(xobject.getElement(POOL_CM));
	}
	
	public XPropiedades getPropiedades() throws XObjectException {
		return new XPropiedades(xobject.getElement("propiedades"));
	}	

	public XConsolaCentral getConsolaCentral() throws XObjectException {
		return new XConsolaCentral(xobject.getElement(CONSOLA_CENTRAL));
	}
	
	public XConexionesBD getConexionesBD() throws XObjectException {
		return new XConexionesBD(xobject.getElement("conexionesBD"));
	}
	
	public XDirVisa getDirVisa() throws XObjectException {
		return new XDirVisa(xobject.getElement("dirVisa"));
	}	
}

