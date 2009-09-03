package gob.pe.sunarp.iri.acceso.controller;

import com.jcorporate.expresso.core.controller.*;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.util.*;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.Controller;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.Output;
import com.jcorporate.expresso.core.controller.State;
import gob.pe.sunarp.extranet.pool.*;

import java.sql.Connection;

public class SalirController extends ControllerExtension {
	
	private String thisClass = SalirController.class.getName() + ".";
		
    public SalirController(){
		super();
		addState(new State("salir", "Log Off del Sistema"));
		setInitialState("salir");
	}

	public String getTitle() {
		return new String("Salir Controller");
	}
    
    public ControllerResponse runSalirState(ControllerRequest request, ControllerResponse response) 
		throws ControllerException {    

 		Output salidaMensaje = null;
 		HttpSession session = null;
 		UsuarioBean usuario = null;

                //Modificado por: Proyecto Filtros de Acceso
                //Fecha: 03/10/2006
                DBConnectionFactory pool = DBConnectionFactory.getInstance();
                Connection conn = null;
                //Fin Modificación
      
		try{
			init(request);
			/*
			Si existe el iv_user, significa que todavía existe la sesión TAM
			y por lo tanto se debe llamar a la página de logoff de TAM,
			de lo contrario, simplemente llamar a la pantalla de inicio nuevamente.
			*/
			
			String iv_user = null;			
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			Enumeration enu = req.getHeaderNames();
				if (enu.hasMoreElements()) {
					while (enu.hasMoreElements()) {
						String parametroRequest = (String) enu.nextElement();
						
						if (parametroRequest.equals("iv-user"))
							iv_user = req.getHeader(parametroRequest);
					}
				}
				
			session = ExpressoHttpSessionBean.getSession(request);
			
			try
			{
                            //Modificado por: Proyecto Filtros de Acceso
                            //Fecha: 03/10/2006
                            conn = pool.getConnection();
                            conn.setAutoCommit(true);      
                            //Fecha: 08/10/2006
                            String sesionId = ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request));   
                            if (sesionId != null)
                            {       
                                ControlAccesoSesion.registrarFinSesion(sesionId, conn);
                            }
                            //Fin Modificación                        
			session.invalidate();
			}
			catch (Throwable tt)
			{
			}                        
                        //Modificado por: Proyecto Filtros de Acceso
                        //Fecha: 03/10/2006                        
			finally
                    {
					//SE ACTUALIZA ESTE FRAGMENTO DE CODIGO PRIMERO HACIENDO UN RELEASE Y LUEGO CLOSE A LA CONEXION. 
					pool.release(conn);
	                conn.close();                                                        
                    }
                    //Fin Modificación
                  
			//  "Unauthenticated" significa que ya expiró su
			//  sesión en el TAM
			
			if (isTrace(this)) System.out.println("iv_user="+iv_user);
			if (iv_user!=null && iv_user.equals("Unauthenticated"))
				iv_user=null;
				
			if (iv_user==null)
				response.setStyle("menuInicial");
			else
				response.setStyle("salidaTAM");      
		}
		
		catch(Throwable te)
		{
			response.setStyle("menuInicial");
			te.printStackTrace();
		}
		return response;
    }
}
