package gob.pe.sunarp.extranet.framework.xml;

/*
Clase para "mapping" del xml de propiedades 
generales del sistema

Revisar el archivo WebappResources.xml para
mayor informacion
*/
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.xml.XObject;
import gob.pe.sunarp.extranet.common.xml.XObjectException;

public class XPropiedades extends SunarpBean{
	private XObject xobject;

	public XPropiedades(XObject xobject) {
		super();
		this.xobject = xobject;
	}

//___________flags________
	public String getFlag01() throws XObjectException {
		return xobject.getElementTextValue("flag01");
	}
	
	public String getFlag02() throws XObjectException {
		return xobject.getElementTextValue("flag02");
	}	
	
	public String getFlag03() throws XObjectException {
		return xobject.getElementTextValue("flag03");
	}		

	public String getFlag04() throws XObjectException {
		return xobject.getElementTextValue("flag04");
	}			
	public String getFlag05() throws XObjectException {
		return xobject.getElementTextValue("flag05");
	}		

//___________valores_______
	public String getValue01() throws XObjectException {
		return xobject.getElementTextValue("value01");
	}			
	
	public String getValue02() throws XObjectException {
		return xobject.getElementTextValue("value02");
	}				

	public String getValue03() throws XObjectException {
		return xobject.getElementTextValue("value03");
	}								

	public String getValue04() throws XObjectException {
		return xobject.getElementTextValue("value04");
	}									
	public String getValue05() throws XObjectException {
		return xobject.getElementTextValue("value05");
	}		
	public String getValue06() throws XObjectException {
		return xobject.getElementTextValue("value06");
	}
	public String getValue07() throws XObjectException {
		return xobject.getElementTextValue("value07");
	}
	public String getValue08() throws XObjectException {
		return xobject.getElementTextValue("value08");
	}
	public String getValue09() throws XObjectException {
		return xobject.getElementTextValue("value09");
	}
	public String getValue10() throws XObjectException {
		return xobject.getElementTextValue("value10");
	}					
	public String getValue11() throws XObjectException {
		return xobject.getElementTextValue("value11");
	}	
	public String getValue12() throws XObjectException {
		return xobject.getElementTextValue("value12");
	}	
	public String getValue13() throws XObjectException {
		return xobject.getElementTextValue("value13");
	}	
	public String getValue14() throws XObjectException {
		return xobject.getElementTextValue("value14");
	}	
	public String getValue15() throws XObjectException {
		return xobject.getElementTextValue("value15");
	}	
	
	
	public String getValue16() throws XObjectException {
		return xobject.getElementTextValue("value16");
	}	
	public String getValue17() throws XObjectException {
		return xobject.getElementTextValue("value17");
	}	
	public String getValue18() throws XObjectException {
		return xobject.getElementTextValue("value18");
	}	
	public String getValue19() throws XObjectException {
		return xobject.getElementTextValue("value19");
	}	
	public String getValue20() throws XObjectException {
		return xobject.getElementTextValue("value20");
	}	
	public String getValue21() throws XObjectException {
		return xobject.getElementTextValue("value21");
	}	
	public String getValue22() throws XObjectException {
		return xobject.getElementTextValue("value22");
	}	
	public String getValue23() throws XObjectException {
		return xobject.getElementTextValue("value23");
	}	
	public String getValue24() throws XObjectException {
		return xobject.getElementTextValue("value24");
	}	
	public String getValue25() throws XObjectException {
		return xobject.getElementTextValue("value25");
	}											


	public boolean getSubjectIncludeUsr() throws XObjectException {
		return xobject.getElementTextValue("subjectIncludeUsr").equals("true");
	}

	public boolean getSubjectIncludeReq() throws XObjectException {
		return xobject.getElementTextValue("subjectIncludeReq").equals("true");
	}

	public boolean getSubjectIncludeErr() throws XObjectException {
		return xobject.getElementTextValue("subjectIncludeErr").equals("true");
	}

}

