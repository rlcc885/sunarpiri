package gob.pe.sunarp.extranet.publicidad.certificada.controller;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.FechaBusquedaBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.PaginacionBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Propiedades;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class BusquedaSolicitudController extends ControllerExtension implements Constantes{
	private String thisClass = BusquedaSolicitudController.class.getName() + ".";
	

	public BusquedaSolicitudController(){
		super();	
		addState(new State("buscaEstadoSolicitud", "Muestra la ventana de busqueda por Estado de la solicitud"));										
		addState(new State("muestraEstadoSolicitud", "Muestra los resultados de la busqueda por numero de solicitud"));				
		setInitialState("buscaEstadoSolicitud");	
	}
	
	public String getTitle() {
		return new String("CargaLaboralController");
	}
	
	
	protected ControllerResponse runBuscaEstadoSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{	
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);
			FechaBusquedaBean fechaBusqBean = new FechaBusquedaBean();
			fechaBusqBean.almacenaFechaHoy();		
			req.setAttribute("fechabusqBean",fechaBusqBean);
			response.setStyle("buscaEstadoSolicitud");
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runMuestraEstadoSolicitudState(ControllerRequest request,ControllerResponse response) throws ControllerException 
	{
		
	DBConnectionFactory pool = DBConnectionFactory.getInstance();
	Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		try{
			init(request);
			validarSesion(request);			
			UsuarioBean userBean = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			String stipobusq;
			String sNumSol;
			String sApePat;
			String sApeMat;
			String sNombre;
			String sRazonSoc;
			String sRangoFecha; 
			String sRefrescaAuto;				
			StringBuffer desde = new StringBuffer();		
			StringBuffer hasta = new StringBuffer();
			
			Solicitud solicitud = new Solicitud();			
			ArrayList arrbuscaList = new ArrayList();	
			FechaBusquedaBean fechaBusqBean = new FechaBusquedaBean();		
			PaginacionBean paginacionBean = new PaginacionBean();
			
			//seteo los datos de Paginacion
			paginacionBean.setNum_pagina(Propiedades.getInstance().getLineasPorPag());
			paginacionBean.setPaginacion(Integer.parseInt(request.getParameter("pagina")));
			paginacionBean.setTamano(Integer.parseInt(request.getParameter("tamano")));
			
			//Recupero el tipo de busqueda			
			stipobusq = request.getParameter("radTipBusq");		
			//Seteo la conexion
			solicitud.setConn(conn);
			solicitud.setPaginacBean(paginacionBean);
			//seteo la fecha de hoy
			fechaBusqBean.almacenaFechaHoy();
			if (stipobusq.equals("NU")){				
				//busqueda por numero de solicitud
				sNumSol = request.getParameter("txtnumSol");
				if(sNumSol==null || sNumSol.equals("")){
				//throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el Número de Solicitud a buscar");
				}else{
					 arrbuscaList =  solicitud.recuperarXNroSolBusqueda(sNumSol, userBean.getCuentaId());
					 req.setAttribute("solicitud",sNumSol);
				}
			}	
			if (stipobusq.equals("PN")){
				//busqueda por nombre de la persona natural
				sApePat = request.getParameter("txtApePat");
				sApeMat = request.getParameter("txtApeMat");
				sNombre = request.getParameter("txtNombre");
				
				
				if(sApePat==null ||	sApePat.equals("")|| sApePat.length() <2){
				   throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar el Apellido Paterno, minimo dos caracteres");		
				}else{
					if((sApeMat==null || sApeMat.equals("")||sApeMat.length()<2 )&&(sNombre==null ||sNombre.equals("")||sNombre.length()<2)){
					   throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar el Apellido Materno y/o el nombre, minimo dos caracteres");				
					}
				}
								
				//if((sApeMat==null || sApeMat.equals(""))||(sNombre==null ||sNombre.equals(""))){				    
				//}
				
				
					if((sApeMat!=null||!sApeMat.equals("")) && (sNombre!=null||!sApeMat.equals(""))){
				    //busqueda de la solicitud por ApePat, ApeMat y Nombres
				    arrbuscaList =  solicitud.recuperarXPersonaNat("3", sApePat, sApeMat, sNombre, userBean.getCuentaId());
					manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());
					}else{
					//busqueda de solicitud por apepat y (apemat o nombre)	
						if(sApeMat==null || sApeMat.equals("")){
						  //busqueda de la solicitud por ApePat y nombre 	
						  arrbuscaList =  solicitud.recuperarXPersonaNat("1", sApePat, sApeMat, sNombre, userBean.getCuentaId());
						  manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());
						} 
						if(sNombre==null ||sNombre.equals("")){
						  //busqueda de la solicitud por ApePat y ApeMat	
						  arrbuscaList =  solicitud.recuperarXPersonaNat("2", sApePat, sApeMat, sNombre, userBean.getCuentaId());
						  manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());
						} 												
					}	
				req.setAttribute("sApePat",sApePat);		
				req.setAttribute("sApeMat",sApeMat);
				req.setAttribute("sNombre",sNombre);
			}	 		 		
			if (stipobusq.equals("PJ")){
				//busqueda por nobre de la persona juridica
				sRazonSoc = request.getParameter("txtRazonSocial");											
				if(sRazonSoc==null || sRazonSoc.equals(""))
				throw new CustomException(Errors.EC_MISSING_PARAM, "Falta el nombre de la razon Social a buscar");
				arrbuscaList = solicitud.recuperarXPersonaJurid(sRazonSoc, userBean.getCuentaId());
				manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());				
				req.setAttribute("sRazonSoc",sRazonSoc);
			}	 		
			if (stipobusq.equals("RF")){
					fechaBusqBean.setDia_inicio(request.getParameter("cbodiainicio"));
					fechaBusqBean.setMes_inicio(request.getParameter("cbomesinicio"));
					fechaBusqBean.setAnno_inicio(request.getParameter("cboanoinicio"));
					fechaBusqBean.setDia_final(request.getParameter("cbodiafin"));
					fechaBusqBean.setMes_final(request.getParameter("cbomesfin"));
					fechaBusqBean.setAnno_final(request.getParameter("cboanofin"));
				//busqueda por rango de fechas
				desde.append(request.getParameter("cboanoinicio")).append("/").append(request.getParameter("cbomesinicio")).append("/").append(request.getParameter("cbodiainicio"));
				hasta.append(request.getParameter("cboanofin")).append("/").append(request.getParameter("cbomesfin")).append("/").append(request.getParameter("cbodiafin"));
				//busqueda de la solicitud por rango de fecha
				arrbuscaList = solicitud.recuperarSolxRangoFechas(userBean.getCuentaId(), desde.toString(),hasta.toString());								
				manejoPaginacion(paginacionBean.getPropiedades(), request, paginacionBean.getPaginacion(), paginacionBean.getHayNext(), paginacionBean.getTamano(), paginacionBean.getNum_pagina());				
				//req.setAttribute("stipobusq",stipobusq);
				req.setAttribute("fechabusqBean",fechaBusqBean);
			}	 		
			//envio el bean con los  campos rango de fecha 
			req.setAttribute("fechabusqBean",fechaBusqBean);
			//encontro registros q mostrar
			req.setAttribute("encontro", paginacionBean.getEncontro());
			if(paginacionBean.getEncontro()==null)
				req.setAttribute("mensaje", "No se encontraron resultados.");
			req.setAttribute("stipobusq",stipobusq);
			req.setAttribute("arrResultado", arrbuscaList);			
			response.setStyle("buscaEstadoSolicitud");

			
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

	private void manejoPaginacion(Propiedades propiedades, ControllerRequest request, int paginacion, boolean hayNext, long tamano, int num_pagina) throws Exception{
  		  int pagSiguiente = 0;
  		  int linXpag = propiedades.getLineasPorPag();

			if(paginacion == 1){
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
				
				pagSiguiente = (tamano >= linXpag)?linXpag:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Titulos del 1 al ").append(pagSiguiente));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));
			}else{
				
				if(hayNext)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", null);
				
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
				
				pagSiguiente = (tamano >= linXpag * paginacion)?linXpag * paginacion:(int)tamano;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("pagdetalle", new StringBuffer("Mostrando Titulos del ").append(((paginacion - 1) * linXpag) + 1).append(" al ").append(pagSiguiente));
			}
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeropaginas", String.valueOf(num_pagina));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("numeroderegistros", String.valueOf(tamano));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tamano", String.valueOf(tamano));		
	}



}

