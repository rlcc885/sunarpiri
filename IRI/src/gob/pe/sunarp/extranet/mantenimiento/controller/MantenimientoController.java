package gob.pe.sunarp.extranet.mantenimiento.controller;

/*
23Oct2002 HT
Mantenimiento GENERICO para tablas
*/

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FormBean;
import gob.pe.sunarp.extranet.util.FormOutputListado;
import gob.pe.sunarp.extranet.util.GenericBean;
import gob.pe.sunarp.extranet.util.GrandTask;
import gob.pe.sunarp.extranet.util.Tarea;
import gob.pe.sunarp.extranet.util.ValidacionException;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class MantenimientoController extends ControllerExtension implements Constantes {
	
	public MantenimientoController() {
		super();
		addState(new State("listado", "ver listado de registros"));
		addState(new State("inicioProceso", "empezar Proceso"));
		addState(new State("finProceso", "terminar Proceso"));
		setInitialState("listado");	
	}

	public String getTitle() {
		return new String("MantenimientoController");
	}
	
	protected ControllerResponse runListadoState(ControllerRequest request, ControllerResponse response) throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			validarSesion(request);
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			String x = req.getParameter("P0");
			
			int P0 = Integer.parseInt(x);
			x = req.getParameter("PP");
			int PP = 1;
			
			if (x!=null)
				PP = Integer.parseInt(x);
			
			String nombreClase = Tarea.getNombreClase(P0);
			GrandTask gt = (GrandTask) Class.forName(nombreClase).newInstance();
			
			gt.setDBName(request.getDBName());
			gt.setConn(dconn);
			
			FormOutputListado output = gt.getList(PP);
			
			output.setCodTabla(P0);
			req.setAttribute("output",output);
			
			response.setStyle("listado");
								
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
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
					pool.release(conn);
			end(request);
		}

		return response;
	}
protected ControllerResponse runInicioProcesoState(ControllerRequest request, ControllerResponse response) throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
						
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);			
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			
			try {
				init(request);
				validarSesion(request);

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
				
				String x;
				
				x = req.getParameter("P0");
				int P0 = Integer.parseInt(x);
				x = req.getParameter("P1");
				int P1 = Integer.parseInt(x);
				
				GenericBean bean = new GenericBean();
				bean.setLlave01(req.getParameter("PK1"));
				bean.setLlave02(req.getParameter("PK2"));
				bean.setLlave03(req.getParameter("PK3"));
				bean.setLlave04(req.getParameter("PK4"));
				bean.setLlave05(req.getParameter("PK5"));
				bean.setLlave06(req.getParameter("PK6"));
				bean.setLlave07(req.getParameter("PK7"));
				bean.setLlave08(req.getParameter("PK8"));
				bean.setLlave09(req.getParameter("PK9"));
				bean.setLlave10(req.getParameter("PK10"));
				
				String nombreClase = Tarea.getNombreClase(P0);
				GrandTask gt = (GrandTask) Class.forName(nombreClase).newInstance();
				gt.setConn(dconn);
				
				if (P0==Constantes.TABLA_TM_ACTO)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboRubros(dconn));
								break;
						}//switch (P1)
				}

				if (P0==Constantes.TABLA_TM_LIBRO)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboAreasRegistrales(dconn));
								break;
						}//switch (P1)
				}				
				
				if (P0==Constantes.TABLA_TM_DEPARTAMENTO)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboPaises(dconn));
								break;
						}//switch (P1)
				}				
				
				if (P0==Constantes.TABLA_TM_PROVINCIA)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboPaises(dconn));
								FormBean formBean = Tarea.getDepartamento_Provincia(dconn);
								session.setAttribute("ARR2", formBean.getArray1());
								//req.setAttribute("arrProvincias", formBean.getArray2());	
								session.setAttribute("ARR3",Tarea.getComboOficinasRegistrales(dconn));
								break;
						}//switch (P1)
				}
				
				if (P0==Constantes.TABLA_TM_DISTRITO)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboPaises(dconn));
								FormBean formBean = Tarea.getDepartamento_Provincia(dconn);
								session.setAttribute("ARR2", formBean.getArray1());
								session.setAttribute("ARR3", formBean.getArray2());
								break;
						}//switch (P1)
				}	
				if (P0==Constantes.TABLA_NOTARIA)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboOficinasRegistrales(dconn));
								break;
						}//switch (P1)
				}	
				if (P0==Constantes.TABLA_PARTIC_LIBRO)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboLibros(dconn));
								break;
						}//switch (P1)
				}
				if (P0==Constantes.TABLA_OFIC_REGISTRAL)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboRegisPublico(dconn));
								session.setAttribute("ARR2",Tarea.getComboJurisdicciones(dconn));
								break;
						}//switch (P1)
				}	
				// Agregado por JACR - Inicio
				if (P0==Constantes.TABLA_TM_MODELO_VEHI)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboMarca(dconn));								
								break;
						}//switch (P1)
				}
				if (P0==Constantes.TABLA_TM_MARCA_VEHI)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboMarca(dconn));//Borrar estos datos q no son necesarios								
								break;
						}//switch (P1)
				}
				if (P0==Constantes.TABLA_TM_COND_VEHI)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboMarca(dconn));//Borrar estos datos q no son necesarios								
								break;
						}//switch (P1)
				}
				if (P0==Constantes.TABLA_TM_TIPO_VEHI)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboMarca(dconn));//Borrar estos datos q no son necesarios								
								break;
						}//switch (P1)
				}
				if (P0==Constantes.TABLA_TM_TIPO_COMB)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboMarca(dconn));//Borrar estos datos q no son necesarios								
								break;
						}//switch (P1)
				}																				
				if (P0==Constantes.TABLA_TM_TIPO_CARR)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboMarca(dconn));//Borrar estos datos q no son necesarios								
								break;
						}//switch (P1)
				}
				if (P0==Constantes.TABLA_TIPO_AFEC)
				{
					switch (P1)
						{
							case 1 : 
							case 2 : 
								gt.read(bean);
							case 4 :
								session.setAttribute("ARR1",Tarea.getComboMarca(dconn));//Borrar estos datos q no son necesarios								
								break;
						}//switch (P1)
				}
				// Agregado por JACR - Fin
				switch (P1)
				{
					case 1 : //view
					case 2 : //update
						gt.read(bean);
						break;
					case 3 : //delete
						gt.delete(bean);
						conn.commit();
						break;
					case 4 : // nuevo
						bean = new GenericBean();
						break;
				}//switch (P1)
				
				req.setAttribute("nombreTabla",gt.nombreTabla);	
				String ruta = gt.ruta;			

				if (P1==3)
				{
					this.transition("listado", request, response);
				}
				else
				{
					response.setStyle(ruta);
					req.setAttribute("codTabla",String.valueOf(P0));
					req.setAttribute("genericBean",bean);
					req.setAttribute("modo",""+P1);
					
					//llaves
					req.setAttribute("PK1",bean.getLlave01());
					req.setAttribute("PK2",bean.getLlave02());
					req.setAttribute("PK3",bean.getLlave03());
					req.setAttribute("PK4",bean.getLlave04());
					req.setAttribute("PK5",bean.getLlave05());
					req.setAttribute("PK6",bean.getLlave06());
					req.setAttribute("PK7",bean.getLlave07());
					req.setAttribute("PK8",bean.getLlave08());
					req.setAttribute("PK9",bean.getLlave09());
					req.setAttribute("PK10",bean.getLlave10());
				}
								
			} //try
			
			catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
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
							pool.release(conn);
				end(request);
			}

			return response;
		}
		
		



		protected ControllerResponse runFinProcesoState(ControllerRequest request, ControllerResponse response) throws ControllerException 
		{

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
			
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);			
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			
			String xP0="";
			String xP1="";
			String nombreTabla ="";
			String pantalla="";
			
			GenericBean bean = null;
			
			try {
				init(request);
				validarSesion(request);

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
				
				String x;
				
				xP0 = req.getParameter("P0");
				int P0 = Integer.parseInt(xP0);
				xP1 = req.getParameter("P1");
				int P1 = Integer.parseInt(xP1);
				
				bean = new GenericBean();
				
				bean.setValor01(req.getParameter("val01"));
				bean.setValor02(req.getParameter("val02"));
				bean.setValor03(req.getParameter("val03"));
				bean.setValor04(req.getParameter("val04"));
				bean.setValor05(req.getParameter("val05"));
				bean.setValor06(req.getParameter("val06"));
				bean.setValor07(req.getParameter("val07"));
				bean.setValor08(req.getParameter("val08"));
				bean.setValor09(req.getParameter("val09"));
				bean.setValor10(req.getParameter("val10"));
								
				bean.setLlave01(req.getParameter("PK1"));
				bean.setLlave02(req.getParameter("PK2"));
				bean.setLlave03(req.getParameter("PK3"));
				bean.setLlave04(req.getParameter("PK4"));
				bean.setLlave05(req.getParameter("PK5"));
				bean.setLlave06(req.getParameter("PK6"));
				bean.setLlave07(req.getParameter("PK7"));
				bean.setLlave08(req.getParameter("PK8"));
				bean.setLlave09(req.getParameter("PK9"));
				bean.setLlave10(req.getParameter("PK10"));
				
				String nombreClase = Tarea.getNombreClase(P0);
				
				GrandTask gt = (GrandTask) Class.forName(nombreClase).newInstance();				
				gt.setConn(dconn);
				nombreTabla = gt.nombreTabla;
				pantalla    = gt.ruta;
				
				gt.usuario = usuario;
				switch (P1)
				{
					case 1 : //view
						break;
					case 2 : //update
						bean.setValor01(bean.getLlave01());
						if (gt.nKeys>=2)
							bean.setValor02(bean.getLlave02());
						if (gt.nKeys>=3)
							bean.setValor03(bean.getLlave03());		
						if (gt.nKeys>=4)
							bean.setValor04(bean.getLlave04());		
						if (gt.nKeys>=5)
							bean.setValor05(bean.getLlave05());		
						if (gt.nKeys>=6)
							bean.setValor06(bean.getLlave06());		
						if (gt.nKeys>=7)
							bean.setValor07(bean.getLlave07());		
						if (gt.nKeys>=8)
							bean.setValor08(bean.getLlave08());		
						if (gt.nKeys>=9)
							bean.setValor09(bean.getLlave09());
						if (gt.nKeys>=10)
							bean.setValor09(bean.getLlave10());			
							
						req.setAttribute("PK1",bean.getLlave01());
						req.setAttribute("PK2",bean.getLlave02());
						req.setAttribute("PK3",bean.getLlave03());
						req.setAttribute("PK4",bean.getLlave04());
						req.setAttribute("PK5",bean.getLlave05());
						req.setAttribute("PK6",bean.getLlave06());
						req.setAttribute("PK7",bean.getLlave07());
						req.setAttribute("PK8",bean.getLlave08());
						req.setAttribute("PK9",bean.getLlave09());
						req.setAttribute("PK10",bean.getLlave10());
						
						gt.update(bean);
						conn.commit();
						break;
					case 3 : //delete
						gt.delete(bean);
						conn.commit();
						break;
					case 4 : // nuevo
						gt.insert(bean);
						conn.commit();
						break;
				}//switch (P1)
				
				session.removeAttribute("ARR1");
				session.removeAttribute("ARR2");
				session.removeAttribute("ARR3");
				session.removeAttribute("ARR4");
				session.removeAttribute("ARR5");
				session.removeAttribute("ARR6");
				session.removeAttribute("ARR7");
				
				this.transition("listado", request, response);
								
			} //try
			

			catch (ValidacionException e) {
				rollback(conn, request);
				req.setAttribute("VALIDACION_EXCEPTION",e);
				req.setAttribute("genericBean", bean);
				req.setAttribute("modo",xP1);
				req.setAttribute("codTabla",xP0);
				req.setAttribute("nombreTabla",nombreTabla);
				
				response.setStyle(pantalla);
			} 
			
			catch (CustomException e) {
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
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
							pool.release(conn);
				end(request);
			}

			return response;
		}		
		

}