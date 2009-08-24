package gob.pe.sunarp.extranet.reniec.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class WebServiceBean extends SunarpBean{


	private String Url;
	private String NameSpaceUri;
	private String ServiceName;
	private String PortName;
	
	public String getNameSpaceUri() {
		return NameSpaceUri;
	}
	public void setNameSpaceUri(String nameSpaceUri) {
		NameSpaceUri = nameSpaceUri;
	}
	public String getPortName() {
		return PortName;
	}
	public void setPortName(String portName) {
		PortName = portName;
	}
	public String getServiceName() {
		return ServiceName;
	}
	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
}
