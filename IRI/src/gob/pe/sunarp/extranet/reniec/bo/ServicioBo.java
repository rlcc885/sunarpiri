package gob.pe.sunarp.extranet.reniec.bo;

import java.net.URL;

import javax.xml.rpc.ParameterMode; 
import javax.xml.rpc.encoding.XMLType; 
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;

import gob.pe.sunarp.extranet.reniec.ws.*;
import gob.pe.sunarp.extranet.reniec.bean.*;

public class ServicioBo {

	public String dataVerificationServicio (IdentificacionBean iBean, ConsultaBean cBean) throws Exception
	{
		EditorDocXmlBo editorDocXmlBo = new EditorDocXmlBo(); 
				
		String inXml = editorDocXmlBo.escribirDocXml(iBean, cBean);
		System.out.println("IN-XML: " + inXml);
		
		String outXml = dataVerification(iBean, inXml);
		System.out.println("Respuesta de web service dataVerificationServicio: " + outXml);
		//outXml = "-2";
		
		if (outXml.trim().length()<= 3){
			cBean.setRes_val(outXml);
		} else {
			editorDocXmlBo.leerDocXml(outXml, cBean);
		}
		return cBean.getRes_val();
	}
	
	private String dataVerification(IdentificacionBean iBean, String xmlDoc) throws Exception {
		
		String UrlString = iBean.getWsDataVerification().getUrl();
		String nameSpaceUri = iBean.getWsDataVerification().getNameSpaceUri();
		String serviceName = iBean.getWsDataVerification().getServiceName();
		String portName = iBean.getWsDataVerification().getPortName();
		
		/*URL verificationwsdlUrl = new URL(UrlString);
		
		ServiceFactory serviceFactory = ServiceFactory.newInstance();
		Service verificationService = serviceFactory.createService(verificationwsdlUrl, new QName(nameSpaceUri, serviceName));
		DataVerificationIF oProxi = (DataVerificationIF)verificationService.getPort(new QName(nameSpaceUri, portName), DataVerificationIF.class);
		
		return oProxi.dataVerification(xmlDoc);*/
		
		QName service = new QName(nameSpaceUri,serviceName);
		QName operation = new QName(serviceName,"dataVerification");
		
		ServiceFactory factory1 = ServiceFactory.newInstance();
        Service service1 = factory1.createService(service); 
                    
        Call call1 = service1.createCall();                        
        // Parametros de entrada del metodo
        call1.setOperationName(operation);            
        call1.addParameter("xmlDocumento", XMLType.XSD_STRING, String.class, ParameterMode.IN);            
        call1.setReturnType(XMLType.XSD_STRING);
        
        // Tipo de operacion RPC y estilo de codificacion
        call1.setProperty(Call.OPERATION_STYLE_PROPERTY,"rpc");
        call1.setProperty(Call.ENCODINGSTYLE_URI_PROPERTY, "http://schemas.xmlsoap.org/soap/encoding/");   
                   
        // Configuracion del Endpoint    
        call1.setTargetEndpointAddress(UrlString);
        // Invocación del Metodo
        Object[] myArgs1 = (new Object[] {xmlDoc});                                    
        String XMLresult = (String)call1.invoke(myArgs1);
        
        return XMLresult;
	}
	
	public String AuthenticationServicio (IdentificacionBean iBean) throws Exception
	{
		String ticket = getTicket(iBean);
		return ticket;
	}
	
	private String getTicket(IdentificacionBean iBean) throws Exception 
	{
		
		String UrlString = iBean.getWsAuthentication().getUrl();
		String nameSpaceUri = iBean.getWsAuthentication().getNameSpaceUri();
		String serviceName = iBean.getWsAuthentication().getServiceName();
		String portName = iBean.getWsAuthentication().getPortName();
		
		/*
		URL verificationwsdlUrl = new URL(UrlString);
		ServiceFactory serviceFactory = ServiceFactory.newInstance();
		Service service = serviceFactory.createService(verificationwsdlUrl, new QName(nameSpaceUri, serviceName));
		//Service service = serviceFactory.createService(new QName(nameSpaceUri, serviceName));
		AuthenticationIF oProxi = (AuthenticationIF)service.getPort(new QName(nameSpaceUri, portName), AuthenticationIF.class);
		return oProxi.getTicket(iBean.getUser(), iBean.getPassword());*/
		
		QName service = new QName(nameSpaceUri,serviceName);
		QName operation = new QName(serviceName,"getTicket");
		
		ServiceFactory factory1 = ServiceFactory.newInstance();
        Service service1 = factory1.createService(service); 
                    
        Call call1 = service1.createCall();                        
        // Parametros de entrada del metodo
        call1.setOperationName(operation);            
        call1.addParameter("user", XMLType.XSD_STRING, String.class, ParameterMode.IN);            
        call1.addParameter("password", XMLType.XSD_STRING, String.class, ParameterMode.IN);
        call1.setReturnType(XMLType.XSD_STRING);
        
        // Tipo de operacion RPC y estilo de codificacion
        call1.setProperty(Call.OPERATION_STYLE_PROPERTY,"rpc");
        call1.setProperty(Call.ENCODINGSTYLE_URI_PROPERTY, "http://schemas.xmlsoap.org/soap/encoding/");   
                   
        // Configuracion del Endpoint    
        call1.setTargetEndpointAddress(UrlString);
        // Invocación del Metodo
        Object[] myArgs1 = (new Object[] {iBean.getUser(), iBean.getPassword()});                                    
        String XMLsesion = (String)call1.invoke(myArgs1);   
        
        return XMLsesion;
		
	}
	
	public String getErrorDataVerification (String codigo, ConsultaBean cBean) throws Exception
	{
		String mensaje = "Error desconocido";
		if (codigo.equals("-1")) mensaje = "Error en el servidor";
		if (codigo.equals("-2")) mensaje = "Sesion expirada";
		if (codigo.equals("-3")) mensaje = "numero de consultas excedido";
		if (codigo.equals("-4")) mensaje = "codigo de operacion no existe";
		if (codigo.equals("-5")) mensaje = "usuario invalido";
		if (codigo.equals("-6")) mensaje = "no puede acceder al servicio en este horario ni fecha";
		if (codigo.equals("-7")) mensaje = "DNI no valido";
		if (codigo.equals("-8")) mensaje = "No existe DNI en Base de datos";
		if (codigo.equals("-9")) mensaje = "data incompleta";
		if (codigo.equals("-10")) mensaje = "no es un documento XML";
		if (codigo.equals("-11")) mensaje = "Error en el servidor";
		if (codigo.equals("-12")) mensaje = "Error en el servidor";
		if (codigo.equals("-13")) mensaje = "Error en el servidor";
		if (codigo.equals("-20")) mensaje = "Error en el servidor";
		
		mensaje = "CODIGO DE ERROR: " + codigo + ": " + mensaje + " " + 
				  "PERSONA: " + cBean.getNo_pers() + " " + 
								cBean.getAp_pate() + " " +
								cBean.getAp_mate() + " " + 
				  "DNI: " + cBean.getNu_docu() + " // ";
		System.out.println("mensaje reniec::"+mensaje);
		return mensaje;
	}
	
	public String getErrorAuthentication (String codigo) throws Exception
	{
		String mensaje = "Error desconocido";
		
		if (codigo.equals("-1")) mensaje = "Error en el servidor";
		if (codigo.equals("-2")) mensaje = "el usuario o password es incorrecto";
		if (codigo.equals("-3")) mensaje = "usuario no esta activado";
		if (codigo.equals("-5")) mensaje = "su consulta esta fuera del horario permitido";
		if (codigo.equals("-6")) mensaje = "Error en el servidor";
		if (codigo.equals("-7")) mensaje = "Error en el servidor";
		if (codigo.equals("-8")) mensaje = "Error en el servidor";
				
		mensaje = "CODIGO DE ERROR EN SESION: " + codigo + ": " + mensaje;
		System.out.println("mensaje reniec::"+mensaje);
		return mensaje;
	}
}
