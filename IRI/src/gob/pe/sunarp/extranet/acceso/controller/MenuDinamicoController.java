package gob.pe.sunarp.extranet.acceso.controller;
/*
Generar el menu de opciones del usuario
*/
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.acceso.bean.PermisoBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.*;
import java.util.*;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MenuDinamicoController extends ControllerExtension {


	private String thisClass = MenuDinamicoController.class.getName() + ".";

	public MenuDinamicoController() {
		super();
		addState(new State("muestraMenu", "Muestra menú dinámico"));
		setInitialState("muestraMenu");
	}

	public String getTitle() 
	{
		return new String("MenuDinamicoController");
	}

	protected ControllerResponse runMuestraMenuState(ControllerRequest request, ControllerResponse response)
		throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		try {
			
			init(request);
			validarSesion(request);

			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
						
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			/*
			GENERACION DE MENU DINAMICO:
			
			Primero se buscan los permisos normales que tiene el usuario 
			según su perfil. Y luego los permisos adicionales que se le hayan
			otorgado.
			
			TABLA PERMISO_PERFIL -> PERMISOS BASICOS
			TABLA PERMISO_USR    -> PERMISOS EXTENDIDOS
			*/
	
			StringBuffer sb = new StringBuffer();

sb.append(" select max(permiso_id) from tm_permiso_ext ");
Statement stmt = conn.createStatement();
ResultSet rset = stmt.executeQuery(sb.toString());
boolean b = rset.next();			
int maxx = rset.getInt(1); 
maxx++;
PermisoBean[] arrp = new PermisoBean[maxx];
			
//permisos básicos
sb.delete(0,sb.length());			
sb.append(" SELECT ");
sb.append(" PERMISO_PERFIL.PERMISO_ID as permisoId,");
sb.append(" TM_PERMISO_EXT.STRING_URL as stringUrl,");
sb.append(" TM_PERMISO_EXT.NOMBRE as nombre, ");
sb.append(" TM_PERMISO_EXT.METODO as metodo ");
sb.append(" FROM PERMISO_PERFIL,TM_PERMISO_EXT ");
sb.append(" WHERE PERMISO_PERFIL.PERFIL_ID = ").append(usuario.getPerfilId());
sb.append(" AND TM_PERMISO_EXT.ESTADO = '1' ");
sb.append(" AND PERMISO_PERFIL.PERMISO_ID = TM_PERMISO_EXT.PERMISO_ID ");

System.out.println("men1-->"+sb.toString());		
stmt = conn.createStatement();
rset = stmt.executeQuery(sb.toString());
b = rset.next();

if (b==false)
	throw new IllegalArgumentException("Opciones de menú no disponibles");

while (b==true)
{
	String url    = rset.getString("stringUrl");
	String metodo = rset.getString("metodo");
	if (metodo==null)
		metodo="";	
	int permisoId = rset.getInt("permisoId");
	String desc   = rset.getString("nombre");

	System.out.println("***********" + url + " *** " + metodo + " *** " + permisoId + " *** " + desc);//GCHANG
	
				sb.delete(0,sb.length());
				sb.append(url);
				if (metodo.length()>0)
					{
						sb.append("?state=");
						sb.append(metodo);
					}
				
				PermisoBean permisoBean = new PermisoBean();
				permisoBean.setDesc(desc);
				
				permisoBean.setUrl(sb.toString());
	
				//9nov2002
				//permiso 49 = Mis Titulos
				//tiene un tratamiento especial			
				if (permisoId!=49)
					arrp[permisoId]=permisoBean;
						
	b = rset.next();
}//while


			//9nov2002
			//permiso 49 = Mis Titulos
			//tiene un tratamiento especial				
			if (usuario.getCur()!=null && usuario.getCur().trim().length()>0)
			{
				PermisoBean pb = new PermisoBean();
				pb.setDesc("Mis Titulos");
				pb.setUrl("/iri/MisTitulos.do");
				arrp[49]=pb;
			}
			
//permisos extras			
sb.delete(0,sb.length());
sb.append(" SELECT ");
sb.append(" TM_PERMISO_EXT.PERMISO_ID as permisoId, ");
sb.append(" TM_PERMISO_EXT.STRING_URL as stringUrl,");
sb.append(" TM_PERMISO_EXT.NOMBRE as nombre,");
sb.append(" TM_PERMISO_EXT.METODO as metodo");
sb.append(" FROM TM_PERMISO_EXT,PERMISO_USR ");
sb.append(" WHERE TM_PERMISO_EXT.ESTADO = '1' ");
sb.append(" AND PERMISO_USR.CUENTA_ID = ").append(usuario.getCuentaId());
sb.append(" AND PERMISO_USR.PERMISO_ID = TM_PERMISO_EXT.PERMISO_ID	");

stmt = conn.createStatement();
rset = stmt.executeQuery(sb.toString());
b = rset.next();
System.out.println("men2-->"+sb.toString());	
while(b==true)
{
	String url    = rset.getString("stringUrl");
	String metodo = rset.getString("metodo");
	if (metodo==null)
		metodo="";
	int permisoId = rset.getInt("permisoId");
	String desc   = rset.getString("nombre");
	
	System.out.println("***********" + url + " *** " + metodo + " *** " + permisoId + " *** " + desc);//GCHANG
	
				sb.delete(0,sb.length());
				sb.append(url);
				if (metodo.length()>0)
					{
						sb.append("?state=");
						sb.append(metodo);
					}
				
				PermisoBean permisoBean = new PermisoBean();
				permisoBean.setDesc(desc);
				permisoBean.setUrl(sb.toString());
				arrp[permisoId]=permisoBean;
	b = rset.next();
}//while
				

					
			req.setAttribute("arregloPermisos", arrp);
			response.setStyle("menuConTAM");			
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			try{
				pool.release(conn);
			}
			catch (Throwable t)
			{}
			end(request);
		}
		return response;
	}
}