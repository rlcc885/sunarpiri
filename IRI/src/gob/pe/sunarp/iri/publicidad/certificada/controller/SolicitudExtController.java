package gob.pe.sunarp.iri.publicidad.certificada.controller;

import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.DboTmDocIden;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.publicidad.certificada.AdministradorCargaLaboral;
//import gob.pe.sunarp.extranet.publicidad.certificada.Atencion;
import gob.pe.sunarp.extranet.publicidad.certificada.Certificado;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.AtencionBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Tarea;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class SolicitudExtController extends ControllerExtension implements Constantes{
	private String thisClass = CargaLaboralController.class.getName() + ".";



	public SolicitudExtController() {
		super();
		addState(new State("buscaSolicitudExt", "Muestra la busqueda de estado de Solicitudes para Externos"));
		addState(new State("muestraSolicitudExt", "Muestra los resultados de la busqueda por numero de solicitud"));		
		addState(new State("detalleSolicitudExt", "Muestra el detalle de la busqueda de Solicitudes para Externos"));
		setInitialState("buscaSolicitudExt");
	}


	public String getTitle() {
		return new String("SolicitudExtController");
	}
	
protected ControllerResponse runBuscaSolicitudExtState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		try{
			init(request);
			response.setStyle("buscaSolicitudExt");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}

protected ControllerResponse runMuestraSolicitudExtState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			String sNumSol;
			
			Solicitud solicitud = new Solicitud();			
			ArrayList arrbuscaList = new ArrayList();
			
			//Seteo la conexion
			solicitud.setConn(conn);
			sNumSol = request.getParameter("txtnumSol");
			if(sNumSol==null || sNumSol.equals(""))
			{
				
				//throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el Número de Solicitud a buscar");
				req.setAttribute("mensaje","Debe ingresar el numero de solicitud");
			}
			else
			{
				arrbuscaList =  solicitud.recuperarXNroSol(sNumSol,null);
				if(arrbuscaList != null)
					req.setAttribute("arrResultado", arrbuscaList);
				else
					req.setAttribute("mensaje","No se obtuvieron resultados");
				req.setAttribute("solicitud", sNumSol);
			}
			response.setStyle("muestraExt");
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
protected ControllerResponse runDetalleSolicitudExtState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			String sNumSol;					
			String sRegistrador;
			
			//Recupero numero de solicitud			
			sNumSol = request.getParameter("solicitud");
			//Recupero un valor si es registrador
			sRegistrador= request.getParameter("registrador");
			Solicitud solicitud = new Solicitud(sNumSol, conn);			
						
			/*** inicio: jrosas 05-09-07 **/
			if(solicitud.getEstado().equalsIgnoreCase(ESTADO_SOL_POR_DESPACHAR)){
				ObjetoSolicitudBean objetoSolicitudBean = (ObjetoSolicitudBean)solicitud.getObjetoSolicitudList().get(0);
				
				if( objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO) ||
					objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
					objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)){
					
					Certificado certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_EXPEDIR);
					req.setAttribute("certificado", certificado);
				}
			}else if(solicitud.getEstado().equalsIgnoreCase(ESTADO_SOL_DESPACHADA)){
				
				ObjetoSolicitudBean objetoSolicitudBean = (ObjetoSolicitudBean)solicitud.getObjetoSolicitudList().get(0);
				
				if(objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)||
					objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
					objetoSolicitudBean.getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA)){
					
					Certificado certificado = new Certificado(sNumSol, conn, Constantes.ESTADO_SOL_POR_EXPEDIR);
					req.setAttribute("certificado", certificado);
				}
			}
			/*** fin: jrosas 05-09-07 **/
			
			req.setAttribute("Solicitud", solicitud);
			req.setAttribute("Registrador", sRegistrador);
			response.setStyle("detalleSolicitudExt");
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}	
}

