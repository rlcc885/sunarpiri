package gob.pe.sunarp.extranet.framework.xml;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XTam extends SunarpBean{
	private XObject xobject;

	public XTam(XObject xobject) {
		super();
		this.xobject = xobject;
	}

	public String getUsuario() throws XObjectException {
		return xobject.getElementTextValue("usuario");
	}
	
	public String getPassword() throws XObjectException {
		return xobject.getElementTextValue("password");
	}				

	public String getUsuarioAutorizacion() throws XObjectException {
		return xobject.getElementTextValue("usuarioAutorizacion");
	}

	public String getPasswordAutorizacion() throws XObjectException {
		return xobject.getElementTextValue("passwordAutorizacion");
	}				
	
	public String getRuta() throws XObjectException {
		return xobject.getElementTextValue("ruta");
	}	
	
	public String getLocaleIdioma() throws XObjectException {
		return xobject.getElementTextValue("localeIdioma");
	}		
	
	public String getLocalePais() throws XObjectException {
		return xobject.getElementTextValue("localePais");
	}			
	
	public String getRutaConfigFile() throws XObjectException {
		return xobject.getElementTextValue("rutaConfigFile");
	}				
	
}

