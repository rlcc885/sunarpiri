package gob.pe.sunarp.extranet.framework.session;

import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.ServletControllerRequest;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.framework.Loggy;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class ExpressoHttpSessionBean extends SunarpBean{

	public static HttpServletRequest getRequest(ControllerRequest request){
		return (HttpServletRequest) ((ServletControllerRequest) request).getServletRequest();
	}

	public static HttpSession getSession(ControllerRequest request){
		//System.out.println("HttpSessionBean.antesDePedirSesion.")
		HttpSession session = ExpressoHttpSessionBean.getRequest(request).getSession();
		//System.out.println("HttpSessionBean.despuesDePedirSesion.")
		if (session == null) {
			Loggy.info("No existe sesion en sistema.", new ExpressoHttpSessionBean(), request);
			//Loggy.principal(request);
		}
		return session;
	}
	public static UsuarioBean getUsuarioBean(ControllerRequest request) {
		HttpSession session = ExpressoHttpSessionBean.getSession(request);
		if (session != null) {
			UsuarioBean bean = (UsuarioBean)session.getAttribute(Constantes.SESSION_DATA);
			if (bean == null) {
				Loggy.info("No existe datos de usuario en sesion.", new ExpressoHttpSessionBean(), request);
				//Loggy.principal(request);
			}
			return bean;
		}
		return null;
	}
	public static HttpServletResponse getResponse(ControllerRequest request){
		//return (HttpServletResponse)((ServletResponse) response);
		return (HttpServletResponse) ((ServletControllerRequest) request).getServletResponse();
	}

}

