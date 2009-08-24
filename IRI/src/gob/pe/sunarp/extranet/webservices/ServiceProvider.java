package gob.pe.sunarp.extranet.webservices;

import gob.pe.sunarp.extranet.util.Propiedades;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;
import javax.xml.rpc.handler.MessageContext;

import com.ibm.ws.webcontainer.srt.SRTServletRequest;

public class ServiceProvider implements ServiceLifecycle 
{
	
	private ServletEndpointContext jaxrpcContext;
	private SRTServletRequest servletRequest;
	
	public void destroy() 
	{
		
		jaxrpcContext = null;
	}

	public void init(Object context) throws ServiceException 
	{
		
		this.jaxrpcContext = (ServletEndpointContext)context;
		MessageContext messageContext = jaxrpcContext.getMessageContext();		   
		this.servletRequest = (SRTServletRequest)messageContext.getProperty("transport.http.servletRequest");
	}

	public String obtenerAMIdSesion(HttpServletRequest request) throws Exception
	{
		String pdUserSesionId = "";
		if (Propiedades.getInstance().getFlagProduccion() == false) 
		{
			//Valido solo para el entorno de desarrollo, para poder trabajar sin el 
			// Tivoli Access Manager for e-Business
			pdUserSesionId = request.getSession(true).getId();
			//Fin Desarrollo
		}
		else
		{
        	// Para el Pase a Produccion se debe descomentar este codigo para poder interactuar
        	// con el Tivoli Access Manager for e-Business y se pueda obtener el Id de Sesion Unico.
			try
        	{
	        	Enumeration headers = request.getHeaderNames();
	        	while (headers.hasMoreElements()) 
	        	{
	            	String header = (String) headers.nextElement();
	            	if ("user-session-id".equalsIgnoreCase(header)) 
	            	{
	               		pdUserSesionId = request.getHeader(header);
	               		break;
	            	}
	        	}	
	        
	        	if (pdUserSesionId.length() == 0)
	        	{		
					pdUserSesionId = (String) request.getSession().getAttribute("user-session-id");
	       		}
       		}
       		catch (Exception e)
       		{
       			pdUserSesionId = null;
        	}
			//Fin Produccion
		}
        return pdUserSesionId;
	}  
	
	public String obtenerIPRemota(HttpServletRequest request) throws Exception
    {
		String ivRemoteAddress = "";
		if (Propiedades.getInstance().getFlagProduccion() == false) 
		{
			//Valido solo para el entorno de desarrollo, para poder trabajar sin el 
			// Tivoli Access Manager for e-Business
			ivRemoteAddress = request.getRemoteAddr();
			//Fin Desarrollo
		}
		else
		{	
	        try
	        {
		        Enumeration headers = request.getHeaderNames();
		        while (headers.hasMoreElements()) 
		        {
		            String header = (String) headers.nextElement();
		            if ("IV-REMOTE-ADDRESS".equalsIgnoreCase(header)) 
		            {
		                ivRemoteAddress = request.getHeader(header);
		                System.out.println("IV-REMOTE-ADDRESS: " + ivRemoteAddress);
		                break;
		            }
		        }
	        }
	        catch (Exception e)
	        {
	        	ivRemoteAddress = null;
	        }        
		}   
        return ivRemoteAddress;
    }
	
	public ServletEndpointContext getJaxrpcContext() {
		return jaxrpcContext;
	}

	public void setJaxrpcContext(ServletEndpointContext jaxrpcContext) {
		this.jaxrpcContext = jaxrpcContext;
	}

	public SRTServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(SRTServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	
	
	
}
