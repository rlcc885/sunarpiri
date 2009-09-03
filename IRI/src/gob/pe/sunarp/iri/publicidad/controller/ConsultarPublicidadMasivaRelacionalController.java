package gob.pe.sunarp.iri.publicidad.controller;

import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;

import javax.servlet.http.HttpServletRequest;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;

import java.util.*;
import gob.pe.sunarp.extranet.publicidad.bean.InputPMasivaRelacionalBean;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarPublicidadMasivaRelacionalService;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarPublicidadMasivaRelacionalServiceImp;

public class ConsultarPublicidadMasivaRelacionalController extends ControllerExtension implements Constantes 
{
	public ConsultarPublicidadMasivaRelacionalController()
	{
		super();
		addState(new State("formularioInicial", "muestra formulario inicial"));
		addState(new State("busquedaVehicular", "busqueda de vehiculos"));
		addState(new State("busquedaEmbarcacionPesquera", "busqueda de Emb."));
		addState(new State("busquedaBuques", "busqueda de busques"));
		addState(new State("busquedaAeroNave", "busqueda de aeronaves"));
		addState(new State("busquedaRMC", "busqueda RMC"));
		setInitialState("formularioInicial");

	}
	
	public ControllerResponse runFormularioInicialState(ControllerRequest request, ControllerResponse response) throws ControllerException 
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		//UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		
		try
		{
			System.out.println("---runFormularioInicialState--");
			init(request);
			validarSesion(request);
		
			ArrayList areaRegistral;
			List tipoEmbPesquera;
			List capitania;
			List tipoVehiculo;
			List tipoCombustible;
			List tipoAeronave;
			
			ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
			areaRegistral = consultar.recuperarAreaRegistral(String.valueOf(Constantes.SERVICIO_BUSQUEDA_PUBLICIDAD_MASIVA_RELACIONA_BASE_0_19));
			tipoEmbPesquera = consultar.recuperarTipoEmbPesquera();
			capitania = consultar.recuperarCapitania();
			tipoVehiculo = consultar.recuperarTipoVehiculo();
			tipoCombustible = consultar.recuperarTipoCombustible();
			tipoAeronave = consultar.recuperarTipoAeronave();
			
			req.setAttribute("arrAreaLibro", areaRegistral);
			req.setAttribute("tipoEmbPesquera", tipoEmbPesquera );
			req.setAttribute("capitania", capitania);
			req.setAttribute("tipoVehiculo", tipoVehiculo);
			req.setAttribute("tipoCombustible",tipoCombustible);
			req.setAttribute("tipoAeronave", tipoAeronave);
			response.setStyle("consultaPMR");
			
		}catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
						
			end(request);
		}
		
		return response;
	
	}
	public ControllerResponse runBusquedaVehicularState(ControllerRequest request, ControllerResponse response) throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		try
		{
			System.out.println("---runBusquedaVehicularState--");
			init(request);
			validarSesion(request);
			
			HashMap listaResultado = null;
			ArrayList listaResult = null;
			//String cadenaZona;
			String flagRespuesta;
			//String resultado;
			String precio;
			String cantidad;
			String flagPagineo;
			flagRespuesta = req.getParameter("flagRespuesta"); 
			flagPagineo = req.getParameter("flagPagineo2");
			InputPMasivaRelacionalBean inputBean;
			
			//inicio:dbravo:27/07/2007
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			//fin:dbravo:27/07/2007
			
			if(flagPagineo == null & flagRespuesta==null)
			{
				inputBean = new InputPMasivaRelacionalBean();
				inputBean.setFechaInscripcionDesde(req.getParameter("fechaInscripcionDesdeVeh"));
				inputBean.setFechaInscripcionHasta(req.getParameter("fechaInscripcionHastaVeh"));
				inputBean.setMarca(req.getParameter("marca"));
				inputBean.setModelo(req.getParameter("modeloVehiculo"));
				inputBean.setAnoFabricacionDesde(req.getParameter("anoFabriDesde"));
				inputBean.setAnoFabricacionHasta(req.getParameter("anoFabriHasta"));
				inputBean.setCodTipoVehiculo(req.getParameter("tipoVehiculo"));
				inputBean.setColorVeh(req.getParameter("color"));
				inputBean.setCodTipoCombustible(req.getParameter("tipoCombustibleVeh"));
				inputBean.setCodTipoActoCausal(req.getParameter("tipoActoVeh"));
				inputBean.setCadenaZona(req.getParameter("hid1"));
				inputBean.setRegistro(req.getParameter("comboAreaLibro"));
				inputBean.setResultado(req.getParameter("radio"));
				inputBean.setAgrupación(req.getParameter("agrupacion"));
				/** incio: jrosas 11-10-2007 **/
				int num_agrupacion = Integer.parseInt(req.getParameter("agrupacion"));
				switch (num_agrupacion){
					case 1: inputBean.setDescripcionAgrupacion("Sin sub-agrupación"); break;
					case 2: inputBean.setDescripcionAgrupacion("Marca"); break;
					case 3: inputBean.setDescripcionAgrupacion("Modelo"); break;
					case 4: inputBean.setDescripcionAgrupacion("Año de Fabricación"); break;
					case 5: inputBean.setDescripcionAgrupacion("Fecha de Inscripción"); break;
					case 6: inputBean.setDescripcionAgrupacion("Tipo de Vehiculo"); break;
					case 7: inputBean.setDescripcionAgrupacion("Color"); break;
					case 8: inputBean.setDescripcionAgrupacion("Tipo de Combustible"); break;
					default : inputBean.setDescripcionAgrupacion(""); break;
				}		
				/** fin: jrosas 11-10-2007 **/
				
				inputBean.setTipActo(req.getParameter("tipoActoVeh"));
				inputBean.setTipLibro("VEH");
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarVehiculoDetallado(inputBean,usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						listaResultado = consultar.consultarVehiculoDetallado(inputBean,usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("registro","Registro Vehicular");
						req.setAttribute("listaResult",listaResult);
						req.setAttribute("criterio",req.getParameter("criterio"));
						req.setAttribute("bean", inputBean);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarVehiculoConsolidado(inputBean, usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						consultar.consultarVehiculoConsolidado(inputBean, usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("listaResult", listaResult);
						req.setAttribute("bean", inputBean);
						req.setAttribute("registro","Registro Vehicular");
						req.setAttribute("cantidad",inputBean.getCantidadRegistros());
					}
				}else
				{
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarVehiculoDetallado(inputBean,usuario, ipOrigen);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarVehiculoConsolidado(inputBean, usuario, ipOrigen);
					}
					precio = ""+listaResultado.get("precio");
					listaResult = (ArrayList)listaResultado.get("resultado");
					cantidad = ""+listaResultado.get("cantidad");
					inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
					inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
					req.setAttribute("precio",precio);
					req.setAttribute("cantidad", cantidad);
					req.getSession().setAttribute("listaResult", listaResult);
				}
		
				req.getSession().setAttribute("bean", inputBean );
				req.setAttribute("regis","V");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					if(inputBean.getResultado().equals("D"))
					{
						response.setStyle("resultadoDetallado");
					}else if(inputBean.getResultado().equals("C"))
					{
						response.setStyle("resultadoConsolidado");
					}
				}else
				{
					response.setStyle("FrmAceptarPago");
				}
			}else
			{
				inputBean = (InputPMasivaRelacionalBean)req.getSession().getAttribute("bean");
				if(inputBean.getResultado().equals("C"))
				{
					listaResult = (ArrayList) req.getSession().getAttribute("listaResult");
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta"));
					consultar.consultarVehiculoConsolidado(inputBean, usuario, ipOrigen);
					req.setAttribute("listaResult", listaResult);
					req.setAttribute("bean", inputBean);
					req.setAttribute("registro","Registro Vehicular");
					req.setAttribute("cantidad",req.getParameter("cantidad"));
					response.setStyle("resultadoConsolidado");
				}else if(inputBean.getResultado().equals("D"))
				{
					inputBean.setFlagPagineo(req.getParameter("flagPagineo2"));
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta"));
					inputBean.setOrdenamiento(req.getParameter("criterio"));
					if(!(req.getParameter("salto")==null))
					{
						inputBean.setSalto(Integer.parseInt(req.getParameter("salto")));
					}
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					listaResultado = consultar.consultarVehiculoDetallado(inputBean,usuario, ipOrigen);
					listaResult = (ArrayList)listaResultado.get("resultado");
					req.setAttribute("regis","V");
					req.setAttribute("registro","Registro Vehicular");
					req.setAttribute("listaResult",listaResult);
					req.setAttribute("criterio",req.getParameter("criterio"));
					req.getSession().setAttribute("bean", inputBean );
					req.setAttribute("bean", inputBean);
					response.setStyle("resultadoDetallado");
				}
			}
		}catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		}catch (ValidacionException e)
		{	response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
						
			end(request);
		}
		
		return response;
	}
	public ControllerResponse runBusquedaEmbarcacionPesqueraState(ControllerRequest request, ControllerResponse response) throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		try
		{
			System.out.println("---runBusquedaEmbarcacionPesqueraState--");
			init(request);
			validarSesion(request);
			
			HashMap listaResultado = null;
			ArrayList listaResult = null;
			//String cadenaZona;
			String flagRespuesta;
			//String resultado;
			String precio;
			String cantidad;
			String flagPagineo;
			flagRespuesta = req.getParameter("flagRespuesta"); 
			flagPagineo = req.getParameter("flagPagineo2");
			InputPMasivaRelacionalBean inputBean;
			
			//inicio:dbravo:27/07/2007
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			//fin:dbravo:27/07/2007
			
			if(flagPagineo == null & flagRespuesta==null)
			{
				inputBean = new InputPMasivaRelacionalBean();
				inputBean.setFechaInscripcionDesde(req.getParameter("fechaInscripcionDesdeEmb"));
				inputBean.setFechaInscripcionHasta(req.getParameter("fechaInscripcionHastaEmb"));
				inputBean.setCodTipoEmbarcacion(req.getParameter("tipoEmbarcacion"));
				inputBean.setNombreEmbarcacionPesquera(req.getParameter("nombreEmbarcacion"));
				inputBean.setCodCapitania(req.getParameter("capitaniaEmbarcacion"));
				inputBean.setCodTipoActoCausal(req.getParameter("tipoActoEmbarcacion"));
				inputBean.setCadenaZona(req.getParameter("hid1"));
				inputBean.setRegistro(req.getParameter("comboAreaLibro"));
				inputBean.setResultado(req.getParameter("radio"));
				inputBean.setTipActo(req.getParameter("tipoActoEmbarcacion"));
				inputBean.setTipLibro("EMB");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarEmbarcacionDetallado(inputBean,usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						listaResultado = consultar.consultarEmbarcacionDetallado(inputBean,usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("registro","Registro de Embarcaciones Pesqueras");
						req.setAttribute("listaResult",listaResult);
						req.setAttribute("criterio",req.getParameter("criterio"));
						req.setAttribute("bean", inputBean);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarEmbarcacionConsolidado(inputBean, usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						consultar.consultarEmbarcacionConsolidado(inputBean, usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("listaResult", listaResult);
						req.setAttribute("bean", inputBean);
						req.setAttribute("registro","Registro de Embarcaciones Pesqueras");
						req.setAttribute("cantidad",inputBean.getCantidadRegistros());
					}
					
				}else
				{
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarEmbarcacionDetallado(inputBean,usuario, ipOrigen);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarEmbarcacionConsolidado(inputBean, usuario, ipOrigen);
					}
					precio = ""+listaResultado.get("precio");
					listaResult = (ArrayList)listaResultado.get("resultado");
					cantidad = ""+listaResultado.get("cantidad");
					inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
					inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
					req.setAttribute("precio",precio);
					req.setAttribute("cantidad", cantidad);
					req.getSession().setAttribute("listaResult", listaResult);
				}
				req.getSession().setAttribute("bean", inputBean );
				req.setAttribute("regis","E");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					if(inputBean.getResultado().equals("D"))
					{
						response.setStyle("resultadoDetallado");
					}else if(inputBean.getResultado().equals("C"))
					{
						response.setStyle("resultadoConsolidado");
					}
				}else
				{
					response.setStyle("FrmAceptarPago");
				}
				
			}else
			{
				inputBean = (InputPMasivaRelacionalBean)req.getSession().getAttribute("bean");
				if(inputBean.getResultado().equals("C"))
				{
					listaResult = (ArrayList) req.getSession().getAttribute("listaResult");
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta"));
					consultar.consultarEmbarcacionConsolidado(inputBean, usuario, ipOrigen);
					req.setAttribute("listaResult", listaResult);
					req.setAttribute("bean", inputBean);
					req.setAttribute("registro","Registro de Embarcaciones Pesqueras");
					req.setAttribute("cantidad",req.getParameter("cantidad"));
					response.setStyle("resultadoConsolidado");
				}else if(inputBean.getResultado().equals("D"))
				{
					inputBean.setFlagPagineo(req.getParameter("flagPagineo2"));
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta"));
					inputBean.setOrdenamiento(req.getParameter("criterio"));
					if(!(req.getParameter("salto")==null))
					{
						inputBean.setSalto(Integer.parseInt(req.getParameter("salto")));
					}
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					listaResultado = consultar.consultarEmbarcacionDetallado(inputBean,usuario, ipOrigen);
					listaResult = (ArrayList)listaResultado.get("resultado");
					req.setAttribute("regis","E");
					req.setAttribute("registro","Registro de Embarcaciones Pesqueras");
					req.setAttribute("listaResult",listaResult);
					req.setAttribute("criterio",req.getParameter("criterio"));
					req.getSession().setAttribute("bean", inputBean );
					req.setAttribute("bean", inputBean);
					response.setStyle("resultadoDetallado");
				}
			}
			
		}catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		}catch (ValidacionException e)
		{	response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
						
			end(request);
		}	
		return response;
	}
	public ControllerResponse runBusquedaBuquesState(ControllerRequest request, ControllerResponse response) throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		try
		{
			System.out.println("---runBusquedaBuquesState--");
			init(request);
			validarSesion(request);
			
			HashMap listaResultado = null;
			ArrayList listaResult = null;
			//String cadenaZona;
			String flagRespuesta;
			//String resultado;
			String precio;
			String cantidad;
			String flagPagineo;
			
			flagRespuesta = req.getParameter("flagRespuesta"); 
			flagPagineo = req.getParameter("flagPagineo2");
			InputPMasivaRelacionalBean inputBean;

			//inicio:dbravo:27/07/2007
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			//fin:dbravo:27/07/2007
			
			if(flagPagineo == null & flagRespuesta==null)
			{
				
			
				inputBean = new InputPMasivaRelacionalBean();
				inputBean.setFechaInscripcionDesde(req.getParameter("fechaInscripDesdeBuque"));
				inputBean.setFechaInscripcionHasta(req.getParameter("fechaInscripHastaBuque"));
				inputBean.setNombreBuque(req.getParameter("nombreBuque"));
				inputBean.setCodCapitania(req.getParameter("capitaniaBuque"));
				inputBean.setCodTipoActoCausal(req.getParameter("tipoActoBuque"));
				inputBean.setCadenaZona(req.getParameter("hid1"));
				inputBean.setRegistro(req.getParameter("comboAreaLibro"));
				inputBean.setResultado(req.getParameter("radio"));
				inputBean.setTipActo(req.getParameter("tipoActoBuque"));
				inputBean.setTipLibro("BUQ");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarBuqueDetallado(inputBean,usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						listaResultado = consultar.consultarBuqueDetallado(inputBean,usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("registro","Registro de Buques");
						req.setAttribute("listaResult",listaResult);
						req.setAttribute("criterio",req.getParameter("criterio"));
						req.setAttribute("bean", inputBean);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarBuqueConsolidado(inputBean, usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						consultar.consultarBuqueConsolidado(inputBean, usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("listaResult", listaResult);
						req.setAttribute("bean", inputBean);
						req.setAttribute("registro","Registro de Buques");
						req.setAttribute("cantidad",inputBean.getCantidadRegistros());
					}
					
				}else
				{
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarBuqueDetallado(inputBean,usuario, ipOrigen);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarBuqueConsolidado(inputBean, usuario, ipOrigen);
					}
					precio = ""+listaResultado.get("precio");
					listaResult = (ArrayList)listaResultado.get("resultado");
					cantidad = ""+listaResultado.get("cantidad");
					inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
					inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
					req.setAttribute("precio",precio);
					req.setAttribute("cantidad", cantidad);
					req.getSession().setAttribute("listaResult", listaResult);
				}
				
				req.getSession().setAttribute("bean", inputBean );
				req.setAttribute("regis","B");
				
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					if(inputBean.getResultado().equals("D"))
					{
						response.setStyle("resultadoDetallado");
					}else if(inputBean.getResultado().equals("C"))
					{
						response.setStyle("resultadoConsolidado");
					}
				}else
				{
					response.setStyle("FrmAceptarPago");
				}
				
			}else
			{
				inputBean = (InputPMasivaRelacionalBean)req.getSession().getAttribute("bean");
				if(inputBean.getResultado().equals("C"))
				{
					listaResult = (ArrayList) req.getSession().getAttribute("listaResult");
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta"));
					consultar.consultarBuqueConsolidado(inputBean, usuario, ipOrigen);
					req.setAttribute("listaResult", listaResult);
					req.setAttribute("bean", inputBean);
					req.setAttribute("registro","Registro de Buques");
					req.setAttribute("cantidad",req.getParameter("cantidad"));
					response.setStyle("resultadoConsolidado");
				}else if(inputBean.getResultado().equals("D"))
				{
					inputBean.setFlagPagineo(req.getParameter("flagPagineo2"));
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta")); 
					inputBean.setOrdenamiento(req.getParameter("criterio"));
					if(!(req.getParameter("salto")==null))
					{
						inputBean.setSalto(Integer.parseInt(req.getParameter("salto")));
					}
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					listaResultado = consultar.consultarBuqueDetallado(inputBean,usuario, ipOrigen);
					listaResult = (ArrayList)listaResultado.get("resultado");
					req.setAttribute("regis","B");
					req.setAttribute("registro","Registro de Buques");
					req.setAttribute("listaResult",listaResult);
					req.setAttribute("criterio",req.getParameter("criterio"));
					req.getSession().setAttribute("bean", inputBean );
					req.setAttribute("bean", inputBean);
					response.setStyle("resultadoDetallado");
				}
			}
		}catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		}catch (ValidacionException e)
		{	response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
						
			end(request);
		}	
		return response;
	}
	public ControllerResponse runBusquedaAeroNaveState(ControllerRequest request, ControllerResponse response) throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		try
		{
			System.out.println("---runBusquedaAeroNaveState--");
			init(request);
			validarSesion(request);

			//inicio:dbravo:27/07/2007
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			//fin:dbravo:27/07/2007
			
			HashMap listaResultado = null;
			ArrayList listaResult = null;
			//String cadenaZona;
			String flagRespuesta;
			//String resultado;
			String precio;
			String cantidad;
			String flagPagineo;
			flagRespuesta = req.getParameter("flagRespuesta"); 
			flagPagineo = req.getParameter("flagPagineo2"); 
			InputPMasivaRelacionalBean inputBean;
			
			if(flagPagineo == null & flagRespuesta==null)
			{
				inputBean = new InputPMasivaRelacionalBean();
			
				inputBean.setFechaInscripcionDesde(req.getParameter("fechaInscripcionDesdeAero"));
				inputBean.setFechaInscripcionHasta(req.getParameter("fechaInscripcionHastaAero"));
				inputBean.setCodTipoAeronave(req.getParameter("tipoAero"));
				inputBean.setModelo(req.getParameter("modelo"));
				inputBean.setCodTipoActoCausal(req.getParameter("tipoActoAero"));
				inputBean.setCadenaZona(req.getParameter("hid1"));
				inputBean.setRegistro(req.getParameter("comboAreaLibro"));
				inputBean.setFlagRespuesta(flagRespuesta);
				inputBean.setResultado(req.getParameter("radio"));
				inputBean.setTipActo(req.getParameter("tipoActoAero"));
				inputBean.setTipLibro("AER");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarAeronaveDetallado(inputBean,usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						listaResultado = consultar.consultarAeronaveDetallado(inputBean,usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("registro","Registro de Aeronaves");
						req.setAttribute("listaResult",listaResult);
						req.setAttribute("criterio",req.getParameter("criterio"));
						req.setAttribute("bean", inputBean);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarAeronaveConsolidado(inputBean, usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						consultar.consultarAeronaveConsolidado(inputBean, usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("listaResult", listaResult);
						req.setAttribute("bean", inputBean);
						req.setAttribute("registro","Registro de Aeronaves");
						req.setAttribute("cantidad",inputBean.getCantidadRegistros());
					}
					
				}else
				{
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarAeronaveDetallado(inputBean,usuario, ipOrigen);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarAeronaveConsolidado(inputBean, usuario, ipOrigen);
					}
					precio = ""+listaResultado.get("precio");
					listaResult = (ArrayList)listaResultado.get("resultado");
					cantidad = ""+listaResultado.get("cantidad");
					inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
					inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
					req.setAttribute("precio",precio);
					req.setAttribute("cantidad", cantidad);
					req.getSession().setAttribute("listaResult", listaResult);
		
				}
				req.getSession().setAttribute("bean", inputBean );
				req.setAttribute("regis","A");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					if(inputBean.getResultado().equals("D"))
					{
						response.setStyle("resultadoDetallado");
					}else if(inputBean.getResultado().equals("C"))
					{
						response.setStyle("resultadoConsolidado");
					}
				}else
				{
					response.setStyle("FrmAceptarPago");
				}
			}else
			{
				ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
				inputBean = (InputPMasivaRelacionalBean)req.getSession().getAttribute("bean");
				inputBean.setFlagRespuesta(req.getParameter("flagRespuesta"));
				if(inputBean.getResultado().equals("C"))
				{
					/**
					 * inicio:dbravo:27/07/2007
					 * descripción: se invoca para el registro de auditoria transacción y pago
					 */
					consultar.consultarAeronaveConsolidado(inputBean, usuario, ipOrigen);
					/**
					 * fin:dbravo:27/07/2007
					 * descripción: se invoca para el registro de auditoria transacción y pago
					 */
					listaResult = (ArrayList) req.getSession().getAttribute("listaResult");
					req.setAttribute("listaResult", listaResult);
					req.setAttribute("bean", inputBean);
					req.setAttribute("registro","Registro de Aeronaves");
					req.setAttribute("cantidad",req.getParameter("cantidad"));
					response.setStyle("resultadoConsolidado");
					
				}else if(inputBean.getResultado().equals("D"))
				{
					inputBean.setFlagPagineo(req.getParameter("flagPagineo2"));
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta")); 
					inputBean.setOrdenamiento(req.getParameter("criterio"));
					if(!(req.getParameter("salto")==null))
					{
						inputBean.setSalto(Integer.parseInt(req.getParameter("salto")));
					}
					
					listaResultado = consultar.consultarAeronaveDetallado(inputBean,usuario, ipOrigen);
					listaResult = (ArrayList)listaResultado.get("resultado");
					req.setAttribute("regis","A");
					req.setAttribute("registro","Registro de Aeronaves");
					req.setAttribute("listaResult",listaResult);
					req.setAttribute("criterio",req.getParameter("criterio"));
					req.getSession().setAttribute("bean", inputBean );
					req.setAttribute("bean", inputBean);
					response.setStyle("resultadoDetallado");
				}
				
			}
		}catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		}catch (ValidacionException e)
		{	response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
						
			end(request);
		}	
		return response;
	}
	public ControllerResponse runBusquedaRMCState(ControllerRequest request, ControllerResponse response) throws ControllerException
	{
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		try
		{
			System.out.println("---runBusquedaRMCState--");
			init(request);
			validarSesion(request);
		
			//inicio:dbravo:27/07/2007
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			//fin:dbravo:27/07/2007
			
			HashMap listaResultado = null;
			ArrayList listaResult = null;
			//String cadenaZona;
			String flagRespuesta;
			//String resultado;
			String precio;
			String cantidad;
			String flagPagineo;
			flagRespuesta = req.getParameter("flagRespuesta"); 
			flagPagineo = req.getParameter("flagPagineo2"); 
			InputPMasivaRelacionalBean inputBean;
			if(flagPagineo == null & flagRespuesta==null)
			{	
				inputBean = new InputPMasivaRelacionalBean();
				inputBean.setFechaInscripcionDesde(req.getParameter("fechaInscripcionDesdeRMC"));
				inputBean.setFechaInscripcionHasta(req.getParameter("fechaInscripcionHastaRMC"));
				inputBean.setCodTipoActoCausal(req.getParameter("tipoActoRMC"));
				inputBean.setMontoGarantiaDesde(req.getParameter("montoDesdeRMC"));
				inputBean.setMontoGarantiaHasta(req.getParameter("montoHastaRMC"));	
				inputBean.setCadenaZona(req.getParameter("hid1"));
				inputBean.setRegistro(req.getParameter("comboAreaLibro"));
				inputBean.setResultado(req.getParameter("radio"));
				inputBean.setTipActo(req.getParameter("tipoActoRMC"));
				inputBean.setTipLibro("RMC");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarRMCDetallado(inputBean,usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						listaResultado = consultar.consultarRMCDetallado(inputBean,usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("registro","Registro Mobiliario de Contrato");
						req.setAttribute("listaResult",listaResult);
						req.setAttribute("criterio",req.getParameter("criterio"));
						req.setAttribute("bean", inputBean);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarRMCConsolidado(inputBean, usuario, ipOrigen);
						inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
						inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
						inputBean.setFlagRespuesta("1");
						consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
						consultar.consultarRMCConsolidado(inputBean, usuario, ipOrigen);
						listaResult = (ArrayList)listaResultado.get("resultado");
						req.setAttribute("listaResult", listaResult);
						req.setAttribute("bean", inputBean);
						req.setAttribute("registro","Registro Mobiliario de Contrato");
						req.setAttribute("cantidad",inputBean.getCantidadRegistros());
					}
					
				}else
				{
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					if(inputBean.getResultado().equals("D"))
					{
						listaResultado = consultar.consultarRMCDetallado(inputBean,usuario, ipOrigen);
					}else if(inputBean.getResultado().equals("C"))
					{
						listaResultado = consultar.consultarRMCConsolidado(inputBean, usuario, ipOrigen);
					}
					precio = ""+listaResultado.get("precio");
					listaResult = (ArrayList)listaResultado.get("resultado");
					cantidad = ""+listaResultado.get("cantidad");
					inputBean.setCantidadRegistros(String.valueOf((Long)listaResultado.get("cantidad")));
					inputBean.setPrecio(String.valueOf(listaResultado.get("precio")));
					req.setAttribute("precio",precio);
					req.setAttribute("cantidad", cantidad);
					req.getSession().setAttribute("listaResult", listaResult);
				}
				req.getSession().setAttribute("bean", inputBean );
				req.setAttribute("regis","R");
				
				if(usuario.getPerfilId()==Constantes.PERFIL_TESORERO || usuario.getPerfilId()==Constantes.PERFIL_CAJERO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_JURISDICCION || usuario.getPerfilId()==Constantes.PERFIL_INTERNO ||
				   usuario.getPerfilId()==Constantes.PERFIL_ADMIN_GENERAL)
				{
					if(inputBean.getResultado().equals("D"))
					{
						response.setStyle("resultadoDetallado");
					}else if(inputBean.getResultado().equals("C"))
					{
						response.setStyle("resultadoConsolidado");
					}
				}else
				{
					response.setStyle("FrmAceptarPago");
				}
				
			}else
			{
				inputBean = (InputPMasivaRelacionalBean)req.getSession().getAttribute("bean");
				if(inputBean.getResultado().equals("C"))
				{
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta"));
					listaResult = (ArrayList) req.getSession().getAttribute("listaResult");
					consultar.consultarRMCConsolidado(inputBean, usuario, ipOrigen);
					req.setAttribute("listaResult", listaResult);
					req.setAttribute("bean", inputBean);
					req.setAttribute("registro","Registro Mobiliario de Contrato");
					req.setAttribute("cantidad",req.getParameter("cantidad"));
					response.setStyle("resultadoConsolidado");
				}else if(inputBean.getResultado().equals("D"))
				{
					inputBean.setFlagPagineo(req.getParameter("flagPagineo2"));
					inputBean.setFlagRespuesta(req.getParameter("flagRespuesta")); 
					inputBean.setOrdenamiento(req.getParameter("criterio"));
					if(!(req.getParameter("salto")==null))
					{
						inputBean.setSalto(Integer.parseInt(req.getParameter("salto")));
					}
					ConsultarPublicidadMasivaRelacionalService consultar = new ConsultarPublicidadMasivaRelacionalServiceImp();
					listaResultado = consultar.consultarRMCDetallado(inputBean,usuario, ipOrigen);
					listaResult = (ArrayList)listaResultado.get("resultado");
					req.setAttribute("regis","R");
					req.setAttribute("registro","Registro Mobiliario de Contrato");
					req.setAttribute("listaResult",listaResult);
					req.setAttribute("criterio",inputBean.getOrdenamiento());
					req.getSession().setAttribute("bean", inputBean );
					req.setAttribute("bean", inputBean);
					response.setStyle("resultadoDetallado");
				}
			}
		}catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		}catch (ValidacionException e)
		{	response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
						
			end(request);
		}	
		return response;
	}
}
