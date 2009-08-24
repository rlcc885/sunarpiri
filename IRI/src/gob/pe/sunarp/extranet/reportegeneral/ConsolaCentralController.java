package gob.pe.sunarp.extranet.reportegeneral;

import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.reportegeneral.bean.DatosOficina;
import gob.pe.sunarp.extranet.reportegeneral.bean.OficinasBean;
import gob.pe.sunarp.extranet.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBException;


public class ConsolaCentralController extends ControllerExtension {
	public ConsolaCentralController() {
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("busca", "Ventana de Busq. x Apellidos y Nombres."));
		setInitialState("muestra");
	}
	private void muestraOficinas(ControllerRequest request) throws DBException{
		DboOficRegistral oficina = new DboOficRegistral();
		oficina.setDBName(request.getDBName());
		
		StringBuffer fieldsToRetrieve = new StringBuffer(DboOficRegistral.CAMPO_REG_PUB_ID).append("|");
		fieldsToRetrieve.append(DboOficRegistral.CAMPO_OFIC_REG_ID).append("|");
		fieldsToRetrieve.append(DboOficRegistral.CAMPO_NOMBRE).append("|");
		oficina.setFieldsToRetrieve(fieldsToRetrieve.toString());
		
		oficina.setAppendWhereClause(DboOficRegistral.CAMPO_REG_PUB_ID + " != '00'");
		
		Iterator listaOficinas = oficina.searchAndRetrieveList(DboOficRegistral.CAMPO_NOMBRE).iterator();
		List listaOficinasL = new ArrayList();
				
		for(; listaOficinas.hasNext(); ){
			oficina = (DboOficRegistral) listaOficinas.next();
				
			OficinasBean bean = new OficinasBean();
			bean.setNombre(oficina.getField(DboOficRegistral.CAMPO_NOMBRE));
			bean.setRegPubId(oficina.getField(DboOficRegistral.CAMPO_REG_PUB_ID));
			bean.setOficRegId(oficina.getField(DboOficRegistral.CAMPO_OFIC_REG_ID));
			listaOficinasL.add(bean);
			DatosOficina.getInstance().getOficinas().put(bean.getRegPubId() + bean.getOficRegId(), bean.getNombre());
		}
			
		ExpressoHttpSessionBean.getRequest(request).setAttribute("listaOficinas", listaOficinasL);
	}

	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		try{
			init(request);
			validarSesion(request);
			UsuarioBean usuario = (UsuarioBean) ExpressoHttpSessionBean.getUsuarioBean(request);

			muestraOficinas(request);
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("E1", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("E2", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("E3", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("E4", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("E5", "X");
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("Error3", "X");

			ExpressoHttpSessionBean.getRequest(request).setAttribute("TE1", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("TE2", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("TE3", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("TE4", "X");
			
			String fecha = FechaUtil.getCurrentDate();

			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", fecha.substring(0,2));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", fecha.substring(3,5));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", fecha.substring(6,10));
			
			fecha = FechaUtil.add(fecha.substring(0, 10), -7);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", fecha.substring(0,2));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", fecha.substring(3,5));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", fecha.substring(6,10));
			
			
			response.setStyle("muestra");
		}
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		}		
		catch(DBException ex){
			log(Constantes.EC_GENERIC_DB_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runBuscaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		try{
			init(request);
			validarSesion(request);
			UsuarioBean usuario = (UsuarioBean) ExpressoHttpSessionBean.getUsuarioBean(request);
			
			muestraOficinas(request);
			
			if(request.getParameter("E1") != null && request.getParameter("E1").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("E1", "X");
			
			if(request.getParameter("E2") != null && request.getParameter("E2").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("E2", "X");
			
			if(request.getParameter("E3") != null && request.getParameter("E3").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("E3", "X");
			
			if(request.getParameter("E4") != null && request.getParameter("E4").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("E4", "X");
			
			if(request.getParameter("E5") != null && request.getParameter("E5").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("E5", "X");

			ExpressoHttpSessionBean.getRequest(request).setAttribute(request.getParameter("error"), "X");

			if(request.getParameter("TE1") != null && request.getParameter("TE1").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("TE1", "X");
			
			if(request.getParameter("TE2") != null && request.getParameter("TE2").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("TE2", "X");
			
			if(request.getParameter("TE3") != null && request.getParameter("TE3").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("TE3", "X");
			
			if(request.getParameter("TE4") != null && request.getParameter("TE4").equalsIgnoreCase("ON"))
				ExpressoHttpSessionBean.getRequest(request).setAttribute("TE4", "X");

			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));

			String arrayOficinas[] = ExpressoHttpSessionBean.getRequest(request).getParameterValues("oficinas");
			if(arrayOficinas ==null || arrayOficinas.length <= 0)
				throw new CustomException("");

			ExpressoHttpSessionBean.getRequest(request).setAttribute("servlet", "X");
			response.setStyle("muestra");
		}catch(CustomException cex){
			ExpressoHttpSessionBean.getRequest(request).setAttribute("faltaOficina", "X");
			response.setStyle("muestra");
		}catch(DBException ex){
			log(Constantes.EC_GENERIC_DB_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
		
	}
}

