package gob.pe.sunarp.extranet.reportes.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.misc.*;

import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.reportes.beans.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;

public class ReporteTransaccionesController extends ControllerExtension implements Constantes{

	public ReporteTransaccionesController() 
	{
		super();
		addState(new State("verFormulario", "muestra formulario de búsqueda de transacciones"));
		addState(new State("porOrganizacion", "muestra el resultado de la búsqueda"));
		addState(new State("porUsuario", "muestra el resultado de la búsqueda"));
		addState(new State("porOrganizacionDetalle", "muestra el resultado de la búsqueda de una sola Organizacion"));
		addState(new State("exportaUsu", "exporta los resultados de la búsqueda"));
		addState(new State("exportaOrg", "exporta los resultados de la búsqueda"));
		setInitialState("verFormulario");
	}

	public String getTitle() {
		return new String("ReporteTransaccionesController");
	}

	public ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
		{
			
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);	
		
		try 
		{
			init(request);
			validarSesion(request);

			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
		//combo para fechas
			String anoinicio = null;
			String mesinicio = null;
			String diainicio = null;
			String anofin = null;
			String mesfin = null;
			String diafin = null;

			boolean primeravez = (request.getParameter("primeravez") == null)?true:false;
			req.setAttribute("usuario_logeado",usuario.getUserId());
			req.setAttribute("arrDays", FechaUtil.getReportDays());
			req.setAttribute("arrMonths", FechaUtil.getReportMonths());
			req.setAttribute("arrYears", FechaUtil.getReportYears());			
			
			if(primeravez)
				{
					String hoy = FechaUtil.getCurrentDate();
					String hoy_dia = hoy.substring(0,2);
					String hoy_mes = hoy.substring(3,5);
					String hoy_ano = hoy.substring(6,10);
					
					anoinicio = hoy_ano;
					mesinicio = hoy_mes;
					diainicio = hoy_dia;
					anofin = hoy_ano;
					mesfin = hoy_mes;
					diafin = hoy_dia;					
				}
				else
				{
					anoinicio = request.getParameter("anoinicio");
					mesinicio = request.getParameter("mesinicio");
					diainicio = request.getParameter("diainicio");
					anofin = request.getParameter("anofin");
					mesfin = request.getParameter("mesfin");
					diafin = request.getParameter("diafin");
				}
				
			req.setAttribute("selectedIDay",diainicio);
			req.setAttribute("selectedIMonth",mesinicio);
			req.setAttribute("selectedIYear",anoinicio);
			req.setAttribute("selectedFDay",diafin);
			req.setAttribute("selectedFMonth",mesfin);
			req.setAttribute("selectedFYear",anofin);
				
			int _di = Integer.parseInt(diainicio);
			int _mi = Integer.parseInt(mesinicio);
			int _ai = Integer.parseInt(anoinicio);
			int _df = Integer.parseInt(diafin);
			int _mf = Integer.parseInt(mesfin);
			int _af = Integer.parseInt(anofin);

			//Construyendo la fecha de inicio y fecha de fin
				String fechaInicio = FechaUtil.stringTimeToOracleString(_di, _mi, _ai, 0, 0, 0);
				String fechaFin = FechaUtil.stringTimeToOracleString(_df, _mf, _af, 23, 59, 59);
				
				req.setAttribute("perfilId",String.valueOf(usuario.getPerfilId()));

				if(primeravez)
				{
					req.setAttribute("hayData", null);
					req.setAttribute("r1", "2");
				
					response.setStyle("muestra");
				}
				else
				{
					request.setParameter("fechaInicio", fechaInicio);
					request.setParameter("fechaFin", fechaFin);
				
					String exportar = request.getParameter("exportar");
					String tipob = request.getParameter("r1");
					
					if(tipob.equals("2"))
						{
								if(exportar == null){
									if(request.getParameter("detalle") == null)
										transition("porOrganizacion", request, response);
									else
										transition("porOrganizacionDetalle", request, response);
								}else
									transition("exportaOrg", request, response);
						}
					else
						{
								if(exportar == null)
									transition("porUsuario", request, response);
								else
									transition("exportaUsu", request, response);
						}

				}

		} 
		catch (CustomException ce) 
		{
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		} 
		catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}


	public ControllerResponse runPorOrganizacionState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = null;
		java.sql.Connection conne = null;
		java.sql.Statement stmt = null;
		java.sql.PreparedStatement pst = null;
		java.sql.ResultSet rs = null;
		java.sql.ResultSet rs1 = null;
		
		try {
			//Lo de Siempre
				init(request);
				validarSesion(request);
				req = ExpressoHttpSessionBean.getRequest(request);
				UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
				
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
				
			//Verificando si usuario tiene Perfil de Administrador de Jurisdiccion
				boolean esAdminJuris = false;
				if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					esAdminJuris = true;
			//capturtando parámetros y validando cosas
				String razsoc = request.getParameter("razsoc");
				String ruc = request.getParameter("ruc");
				String ai = request.getParameter("anoinicio");
				String mi = request.getParameter("mesinicio");
				String di = request.getParameter("diainicio");
				String af = request.getParameter("anofin");
				String mf = request.getParameter("mesfin");
				String df = request.getParameter("diafin");
				//Verificamos si es una nueva Búsqueda (paginar = false) o es la paginacion (paginar = true)
					boolean paginar = request.getParameter("paginar")==null?false:true;
				//Verificando opcion Costo Cero
					boolean costocero = request.getParameter("costocero")==null?false:true;
					if(costocero)
						req.setAttribute("costocero", "ON");
				//Verificando tipo de Búsqueda
					boolean porRazonSoc = false;
					boolean porRuc = false;
					if(razsoc != null && razsoc.trim().length() > 0)
						porRazonSoc = true;
					if(!porRazonSoc){
						if(ruc == null || ruc.trim().length() <= 0)
							throw new ValidacionException("Debe ingresar la Razón Social o el Número de RUC.");
						porRuc = true;
					}
			//Parametros de Salida		
				req.setAttribute("r1", request.getParameter("r1"));
				req.setAttribute("tipousuario", "Organización");
				//req.setAttribute("userId", razsoc + " " + ruc);
				req.setAttribute("razsoc", razsoc);
				req.setAttribute("ruc", ruc);
				//Retornar Fecha para mostrarse
					req.setAttribute("fecini", di + "/" + mi + "/" + ai);
					req.setAttribute("fecfin", df + "/" + mf + "/" + af);

			String peJuriId = null;
			java.util.List auxil = new ArrayList();
			RptTransaccionBean bean = null;
			String nombreEmpresa = null;
			
			StringBuffer SQL = new StringBuffer("");
			stmt = conn.createStatement();
			//Búsqueda por Razón Social
			if(porRazonSoc){
				SQL.append("SELECT PE_JURI_ID FROM PE_JURI WHERE RAZ_SOC LIKE '").append(razsoc).append("%'");
				if(esAdminJuris)
					SQL.append(" AND JURIS_ID = '").append(usuario.getJurisdiccionId()).append("'");
				if (isTrace(this)) System.out.println(new StringBuffer("SQL>> ").append(SQL.toString()));
				
				rs = stmt.executeQuery(SQL.toString());
				
				if(!rs.next()){
					if(esAdminJuris)	throw new ValidacionException("Organización: " + razsoc + " no existe en el Sistema o no pertenece a su Jurisdicción.");
					else	throw new ValidacionException("Organización: " + razsoc + " no existe en el Sistema.");
				}else{
					SQL = new StringBuffer();
					SQL.append("select p.num_doc_iden from persona p, pe_juri pj ");
					SQL.append("where p.persona_id = pj.persona_id ");
					SQL.append("and pj.pe_juri_id = ?");
					pst = conn.prepareStatement(SQL.toString());
					//pst = conne.prepareStatement(SQL.toString());

					do{
						peJuriId = rs.getString(1);
						nombreEmpresa = Generales.getRazonSocial(peJuriId, dconn);
						
						pst.setString(1, peJuriId);
						rs1 = pst.executeQuery();
						
						String ruc_ = "";
						if(rs1.next())
							ruc_ = rs1.getString(1);
							
						if(nombreEmpresa != null){
							bean = new RptTransaccionBean();
							bean.setPeJuriID(peJuriId);
							bean.setRazonSocial(nombreEmpresa);
							bean.setNumDoc(ruc_);
							auxil.add(bean);
						}
					}while(rs.next());
				}
			}
			
			if(porRuc){
				SQL.append("SELECT PERSONA_ID FROM PERSONA WHERE TIPO_DOC_ID = '05' AND NUM_DOC_IDEN = '").append(ruc).append("'");
				SQL.append(" AND TPO_PERS = 'J'");
				if (isTrace(this)) System.out.println(new StringBuffer("SQL>> ").append(SQL.toString()));
				rs = stmt.executeQuery(SQL.toString());
				
				SQL = new StringBuffer("SELECT PE_JURI_ID FROM PE_JURI WHERE PERSONA_ID = ?");
				if(esAdminJuris)
					SQL.append(" AND JURIS_ID = '").append(usuario.getJurisdiccionId()).append("'");
				
				//pst = conne.prepareStatement(SQL.toString());
				pst = conn.prepareStatement(SQL.toString());
				
				while(rs.next()){
					pst.setString(1, rs.getString(1));
					rs1 = pst.executeQuery();
					
					if(!rs1.next()){
						if(esAdminJuris)
							throw new ValidacionException("Organización con RUC: " + ruc + " no existe en el Sistema o no pertenece a su Jurisdicción.");
						else
							throw new ValidacionException("Organización con RUC: " + ruc + " no existe en el Sistema.");
					}else{
						do{
							peJuriId = rs1.getString(1);
							nombreEmpresa = Generales.getRazonSocial(peJuriId, dconn);
							if(nombreEmpresa != null){
								bean = new RptTransaccionBean();
								bean.setPeJuriID(peJuriId);
								bean.setRazonSocial(nombreEmpresa);
								bean.setNumDoc(ruc);
								auxil.add(bean);
							}
						}while(rs1.next());
					}
				}
			}

		//De aqui en adelante tanto por RUC como por Razon Social 
			if(auxil.size() <= 0)
				throw new ValidacionException("No existe Organización en el Sistema.");

			if(auxil.size() == 1){
				request.setParameter("razSoc", ((RptTransaccionBean)auxil.get(0)).getRazonSocial());
				request.setParameter("numDoc", ((RptTransaccionBean)auxil.get(0)).getNumDoc());
				request.setParameter("peJuriId", ((RptTransaccionBean)auxil.get(0)).getPeJuriID());
				transition("porOrganizacionDetalle", request, response);
				return null;
			}
			
			req.setAttribute("hayDetalle", "X");
			req.setAttribute("hayData", "S");
			req.setAttribute("listaOrganizaciones", auxil);
			response.setStyle("muestra");
		} catch (CustomException ce) 		{
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		}
		catch (ValidacionException ve) 		{		
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","VerReporteTransacciones.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} finally {
			if(pst != null)
				try{pst.close();}
				catch(Exception e){}
			if(stmt != null)
				try{stmt.close();}
				catch(Exception e){}
			if(rs != null)
				try{rs.close();}
				catch(Exception e){}
			if(rs1 != null)
				try{rs1.close();}
				catch(Exception e){}
			pool.release(conn);
			end(request);
		}
		return response;
	}

	public ControllerResponse runPorUsuarioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try {
			init(request);
			validarSesion(request);

			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			req.setAttribute("perfilId",String.valueOf(usuario.getPerfilId()));
			req.setAttribute("usuario_logeado",usuario.getUserId());
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			boolean esAdminJuris = false;
			boolean esAdminOrgExt = false;
			boolean esIndivExt = false;
			String userId = request.getParameter("userId");

				if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					esAdminJuris = true;
				if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					esAdminOrgExt = true;
				if (usuario.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO)
					esIndivExt = true;
			// NTH: El ADMIN_ORG_EXT podría ver un reporte consolidado de todos sus usuarios 
			boolean adm = false;
			if(userId == null || userId.trim().length() <= 0)
			{
				userId = usuario.getUserId();
				//throw new ValidacionException("Debe ingresar una Cuenta de Usuario","");
				adm = true;
			}	
			String cuentaID = Generales.getCuentaID(userId, dconn);
			
			if(cuentaID == null)
				throw new ValidacionException("Usuario: " + userId + " no tiene Cuenta en el Sistema","");

			req.setAttribute("userId", userId);
			req.setAttribute("r1", request.getParameter("r1"));
			req.setAttribute("tipousuario", "Usuario");

			//Verificamos si es una nueva Búsqueda (paginar = false) o es la paginacion (paginar = true)
				boolean paginar = request.getParameter("paginar")==null?false:true;

			//Verificando opcion Costo Cero	****************************************************
				String auxi = null;
				boolean costocero = false;
				
				auxi = request.getParameter("costocero");
					
				if(auxi == null)
					costocero = false;
				else
				{
					req.setAttribute("costocero", "ON");
					costocero = true;
				}
			//**********************************************************************************
				String ai = request.getParameter("anoinicio");
				String mi = request.getParameter("mesinicio");
				String di = request.getParameter("diainicio");
				String af = request.getParameter("anofin");
				String mf = request.getParameter("mesfin");
				String df = request.getParameter("diafin");
			//***********************************************************************

			//Retornar Fecha para mostrarse
				req.setAttribute("fecini", di + "/" + mi + "/" + ai);
				req.setAttribute("fecfin", df + "/" + mf + "/" + af);
			
			//Averiguamos si pertenece a alguna Organizacion
			boolean esPJ;
			if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
				esPJ = Generales.perteneceOrganizacion(cuentaID, usuario.getCodOrg(), dconn);
			else
				esPJ = Generales.perteneceOrganizacion(cuentaID, null, dconn);
	
			String nomEmpresa = null;
			if(esPJ)
				nomEmpresa = Generales.getNombreEmpresaXCuentaID(cuentaID, dconn);
			else
				req.setAttribute("noEmpresa", "X");
				
			if(esAdminJuris)
			{
				if( !Generales.perteneceJurisdiccion(cuentaID, usuario.getJurisdiccionId(), dconn) )
					throw new ValidacionException("Usuario: " + userId + " no pertenece a su Jurisdicción");
			}
			
			if(esAdminOrgExt)
			{
					// hphp: aqui esta fallando
				if( !Generales.perteneceOrganizacion(cuentaID, usuario.getCodOrg(), dconn) )
					throw new ValidacionException("Usuario: " + userId + " no pertenece a su Organización");
			}
			
			
			
			StringBuffer where = new StringBuffer(" TRANSACCION.").append(DboTransaccion.CAMPO_FEC_HOR);
			where.append(" BETWEEN ").append(request.getParameter("fechaInicio")).append(" AND ").append(request.getParameter("fechaFin"));
			if (!adm)
				where.append(" AND TRANSACCION.").append(DboTransaccion.CAMPO_CUENTA_ID).append(" = ").append(cuentaID);
			else
				where.append(" AND TRANSACCION.").append(DboTransaccion.CAMPO_CUENTA_ID).append(" IN (").append("SELECT cuenta_id FROM org_ctas WHERE pe_juri_id =").append(usuario.getCodOrg()).append(")");
			
			if(costocero)
				where.append(" AND TRANSACCION.").append(DboTransaccion.CAMPO_COSTO).append(" >= 0");
			else
				where.append(" AND TRANSACCION.").append(DboTransaccion.CAMPO_COSTO).append(" > 0");
	
			DboTransaccion dboTransaccion = new DboTransaccion(dconn);
			
			dboTransaccion.setAppendWhereClause(where.toString());
			
			//*** MANEJO DE LA PAGINACION
			int num_pagina = Propiedades.getInstance().getLineasPorPag();
			//dboTransaccion.setMaxRecords(num_pagina);
			//dboTransaccion.setMaxRecords(2);
			int paginacion = Integer.parseInt(request.getParameter("pagina"));
			//*** FIN DE MANEJO DE PAGINACION						

			boolean hayNext = false;
			boolean encontro = false;

			java.util.List listaTransaccion = dboTransaccion.searchAndRetrieveListPaginado(paginacion);
			java.util.List lista = new java.util.ArrayList();
			
			if(listaTransaccion.size() <= 0)
				throw new ReporteNoRegistroException("muestra");
			else
				encontro = true;

			//se anadio paginacion
			if(dboTransaccion.getHaySiguiente())
				hayNext = true;

			TransaccionBean TBean = null;
			
			for(java.util.Iterator it = listaTransaccion.iterator(); it.hasNext();){
				DboTransaccion aux = (DboTransaccion) it.next();
				
				TBean = new TransaccionBean();
				TBean.setRazonSocial(nomEmpresa);
				
				String auxiliar = Generales.getUserID(aux.getField(DboTransaccion.CAMPO_CUENTA_ID), dconn);
				TBean.setUserId(auxiliar==null?"":auxiliar);
				
				//cjvc77 20021218
				//	if (isTrace(this)) System.out.println(new StringBuffer("Reporte Transaccion: ").append(aux.getField(DboTransaccion.CAMPO_FEC_HOR)).toString());//Para verificar como lo trae el DBObject desde el AIX.
					String aux1 = FechaUtil.toPaginado(aux.getField(DboTransaccion.CAMPO_FEC_HOR)).substring(0, 10);
					TBean.setFecha(new StringBuffer(aux1.substring(3,6)).append(aux1.substring(0,3)).append(aux1.substring(6,10)).toString());
				//antes
				//TBean.setFecha(FechaUtil.toPaginado(aux.getField(DboTransaccion.CAMPO_FEC_HOR)).substring(0, 10));
				TBean.setHora(FechaUtil.toPaginado(aux.getField(DboTransaccion.CAMPO_FEC_HOR)).substring(11, 19));
				
				auxiliar = Generales.getNombreOficina(aux.getField(DboTransaccion.CAMPO_REG_PUB_ID), aux.getField(DboTransaccion.CAMPO_OFIC_REG_ID), dconn);
				TBean.setNomOficinaRegistral(auxiliar==null?"":auxiliar);
				
				auxiliar = Generales.getNombreServicio(aux.getField(DboTransaccion.CAMPO_SERVICIO_ID), dconn);
				TBean.setNomServicio(auxiliar==null?"":auxiliar);

				TBean.setIdDocumento(aux.getField(DboTransaccion.CAMPO_STR_BUSQ));
				TBean.setCosto(aux.getField(DboTransaccion.CAMPO_COSTO));
				
				lista.add(TBean);
			}
			double costo_total = dboTransaccion.sum(DboTransaccion.CAMPO_COSTO);
			
//*PAGINACION EN EL JSP*//			
			if(paginacion == 1){
				if(hayNext)
					req.setAttribute("next", "2");
				else
					req.setAttribute("next", null);
				
				req.setAttribute("previous", null);
			}else{
				if(hayNext)
					req.setAttribute("next", String.valueOf(paginacion + 1));
				else
					req.setAttribute("next", null);
				
				req.setAttribute("previous", String.valueOf(paginacion - 1));
			}
			req.setAttribute("numeropaginas", String.valueOf(num_pagina));
			req.setAttribute("pagina", String.valueOf(paginacion));
//*PAGINACION EN EL JSP*//

			req.setAttribute("listatransacciones", lista);
			req.setAttribute("costo_total", Double.toString(costo_total));
			req.setAttribute("hayData", "S");

			response.setStyle("muestra");
			
		} catch (ReporteNoRegistroException re) {
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "N");
			response.setStyle(re.getForward());
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		}catch (ValidacionException ve) {		
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","VerReporteTransacciones.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		return response;
	}




	public ControllerResponse runExportaOrgState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
			
		try {
			init(request);
			validarSesion(request);

			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			req.setAttribute("perfilId",String.valueOf(usuario.getPerfilId()));
			req.setAttribute("usuario_logeado",usuario.getUserId());
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			boolean esAdminJuris = false;

			if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					esAdminJuris = true;

			//**********************************************************************************************
			
				String peJuriId = request.getParameter("peJuri");
				String razSoc = request.getParameter("razS");
				String numDoc = request.getParameter("numDoc");
			
			String razsoc = request.getParameter("razsoc");
			String ruc = request.getParameter("ruc");
			String nombreEmpresa = null;
			
		//Parametros para la Salida
			req.setAttribute("r1", request.getParameter("r1"));
			req.setAttribute("tipousuario", "Organización");
			req.setAttribute("razsoc", razsoc);
			req.setAttribute("ruc", ruc);
			
			//Verificando opcion Costo Cero	****************************************************
				String auxi = null;
				boolean costocero = false;
				
				auxi = request.getParameter("costocero");
					
				if(auxi == null)
					costocero = false;
				else 
				{
					req.setAttribute("costocero", "ON");
					costocero = true;
				}
			//**********************************************************************************
				String ai = request.getParameter("anoinicio");
				String mi = request.getParameter("mesinicio");
				String di = request.getParameter("diainicio");
				String af = request.getParameter("anofin");
				String mf = request.getParameter("mesfin");
				String df = request.getParameter("diafin");
			//***********************************************************************

				req.setAttribute("fecini", di + "/" + mi + "/" + ai);
				req.setAttribute("fecfin", df + "/" + mf + "/" + af);
		//Fin Parametros de Salida		

			
		//De aqui en adelante tanto por RUC como por Razon Social 
		/* * * * * * * * * * * * * * * R E A L I Z A   L A   T R A N S A C C I O N * * * * * * * * * * * */
			StringBuffer SQL = new StringBuffer("");
			stmt = conn.createStatement();

			SQL.append("SELECT T.CUENTA_ID, T.FEC_HOR, T.REG_PUB_ID, T.OFIC_REG_ID, T.SERVICIO_ID, T.STR_BUSQ, T.COSTO  FROM TRANSACCION T WHERE T.FEC_HOR BETWEEN ");
			SQL.append(request.getParameter("fechaInicio")).append(" AND ").append(request.getParameter("fechaFin"));
			
			if(costocero)	SQL.append(" AND T.COSTO >= 0");
			else	SQL.append(" AND T.COSTO > 0");

			SQL.append(" AND T.CUENTA_ID IN(");
			SQL.append("SELECT ORG_CTAS.CUENTA_ID FROM ORG_CTAS WHERE ORG_CTAS.PE_JURI_ID = '").append(peJuriId).append("')");
	
			if (isTrace(this)) System.out.println(new StringBuffer("SQL>> ").append(SQL.toString()).toString());
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL.toString());

			//boolean hayNext = false;
			boolean encontro = false;

			if(!rs.next())
				throw new ReporteNoRegistroException("muestra");
			else
				encontro = true;


 			String fname = "constancia.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;
			StringBuffer stb = new StringBuffer();

			TransaccionBean TBean = null;

			stb.append("Organizacion").append(",");
			stb.append("Usuario").append(",");
			stb.append("Fecha").append(",");
			stb.append("Hora").append(",");
			stb.append("Ofic Registral").append(",");
			stb.append("Tipo Servicio").append(",");
			stb.append("Id. Documento").append(",");
			stb.append("Costo");

			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());
			double costo_total = 0.0;

			do{
				stb = new StringBuffer();
				stb.append(razSoc).append(",");
				
				String auxiliar = Generales.getUserID(String.valueOf(rs.getLong(1)), dconn);
				stb.append(auxiliar==null?"":auxiliar).append(",");
				
				//java.util.Date date = rs.getDate(2);
				//inicio:mgarate:observacion
				java.sql.Timestamp date = rs.getTimestamp(2);
				//fin:mgarate
				if(date != null){
					stb.append(FechaUtil.dateToString(date)).append(",");;
					stb.append(FechaUtil.dateTimeToString(new java.sql.Timestamp(date.getTime())).substring(11, 19));
				}else{
					stb.append("").append(",");;
					TBean.setHora("");
				}
				stb.append(",");
				
				auxiliar = Generales.getNombreOficina(rs.getString(3), rs.getString(4), dconn);
				stb.append(auxiliar==null?"":auxiliar).append(",");
				
				auxiliar = Generales.getNombreServicio(String.valueOf(rs.getLong(5)), dconn);
				stb.append(auxiliar==null?"":auxiliar).append(", ");

				stb.append(rs.getString(6)).append(", ");
				stb.append(String.valueOf(rs.getDouble(7)));

				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());

				costo_total = costo_total + rs.getDouble(7);
			}while(rs.next());

			stb = new StringBuffer();
			stb.append("TOTAL: ").append(costo_total);
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());

			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);

		} catch (ReporteNoRegistroException re) {
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "N");
			response.setStyle(re.getForward());
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		}catch (ValidacionException ve) {		
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","VerReporteTransacciones.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			
			
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} finally {
			if(stmt != null)
				try{stmt.close();
				}catch(Exception e){}
			if(rs != null)
				try{rs.close();
				}catch(Exception e){}
			pool.release(conn);
			end(request);
		}
		return response;
	}

	public ControllerResponse runExportaUsuState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);			
		
		try {
			init(request);
			validarSesion(request);

			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			req.setAttribute("perfilId",String.valueOf(usuario.getPerfilId()));
			req.setAttribute("usuario_logeado",usuario.getUserId());
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			boolean esAdminJuris = false;
			boolean esAdminOrgExt = false;
			boolean esIndivExt = false;
			
			//String userId = request.getParameter("hiduserId");	
			String userId = request.getParameter("userId");

				if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					esAdminJuris = true;
				if (usuario.getPerfilId() == PERFIL_ADMIN_ORG_EXT)
					esAdminOrgExt = true;
				if (usuario.getPerfilId() == PERFIL_INDIVIDUAL_EXTERNO)
				{
					userId = usuario.getUserId();
					esIndivExt = true;
				}

			if(userId == null || userId.trim().length() <= 0)
				throw new ValidacionException("Debe ingresar una Cuenta de Usuario");
				
			String cuentaID = Generales.getCuentaID(userId, dconn);
			if(cuentaID == null)
				throw new ValidacionException("Usuario: " + userId + " no tiene Cuenta en el Sistema");

			req.setAttribute("userId", userId);
			req.setAttribute("r1", request.getParameter("r1"));
			req.setAttribute("tipousuario", "Usuario");

			//Verificando opcion Costo Cero	****************************************************
				String auxi = null;
				boolean costocero = false;
				
				auxi = request.getParameter("costocero");
					
				if(auxi == null)
					costocero = false;
				else 
				{
					req.setAttribute("costocero", "ON");
					costocero = true;
				}
			//**********************************************************************************
				String ai = request.getParameter("anoinicio");
				String mi = request.getParameter("mesinicio");
				String di = request.getParameter("diainicio");
				String af = request.getParameter("anofin");
				String mf = request.getParameter("mesfin");
				String df = request.getParameter("diafin");
			//***********************************************************************

			//Retornar Fecha para mostrarse
				req.setAttribute("fecini", di + "/" + mi + "/" + ai);
				req.setAttribute("fecfin", df + "/" + mf + "/" + af);
			
			//Averiguamos si pertenece a alguna Organizacion
				boolean esPJ = Generales.perteneceOrganizacion(cuentaID, null, dconn);
	
			String nomEmpresa = null;
			if(esPJ)
				nomEmpresa = Generales.getNombreEmpresaXCuentaID(cuentaID, dconn);
			else
				req.setAttribute("noEmpresa", "X");
				
			if(esAdminJuris)
				if( !Generales.perteneceJurisdiccion(cuentaID, usuario.getJurisdiccionId(), dconn) )
					throw new ValidacionException("Usuario: " + userId + " no pertenece a su Jurisdicción");
			
			if(esAdminOrgExt)
				if( !Generales.perteneceOrganizacion(cuentaID, usuario.getCodOrg(), dconn) )
					throw new ValidacionException("Usuario: " + userId + " no pertenece a su Organización");
			

		/* * * * * * * * * * * * * * * R E A L I Z A   L A   T R A N S A C C I O N * * * * * * * * * * * */
			StringBuffer where = new StringBuffer(" TRANSACCION.").append(DboTransaccion.CAMPO_FEC_HOR);
			where.append(" BETWEEN ").append(request.getParameter("fechaInicio")).append(" AND ").append(request.getParameter("fechaFin"));
			where.append(" AND TRANSACCION.").append(DboTransaccion.CAMPO_CUENTA_ID).append(" = ").append(cuentaID);
						
			if(costocero)
				where.append(" AND TRANSACCION.").append(DboTransaccion.CAMPO_COSTO).append(" >= 0");
			else
				where.append(" AND TRANSACCION.").append(DboTransaccion.CAMPO_COSTO).append(" > 0");
	
			DboTransaccion dboTransaccion = new DboTransaccion(dconn);
			dboTransaccion.setAppendWhereClause(where.toString());
			
			java.util.List listaTransaccion = dboTransaccion.searchAndRetrieveList();
			java.util.List lista = new java.util.ArrayList();
			
			if(listaTransaccion.size() <= 0)
				throw new ReporteNoRegistroException("muestra");

 			String fname = "constancia.csv";
			HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
			res.setContentType("archivo/xxx");
			res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
			PrintWriter out_cliente = null;

			StringBuffer stb = new StringBuffer();
			
			if(esPJ)
				stb.append("Organizacion").append(",");
				
			stb.append("Usuario").append(",");
			stb.append("Fecha").append(",");
			stb.append("Hora").append(",");
			stb.append("Ofic Registral").append(",");
			stb.append("Tipo Servicio").append(",");
			stb.append("Id. Documento").append(",");
			stb.append("Costo");
			
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());

			for(java.util.Iterator it = listaTransaccion.iterator(); it.hasNext();){
				DboTransaccion aux = (DboTransaccion) it.next();
				
				stb = new StringBuffer();

				if(esPJ)
					stb.append(nomEmpresa).append(",");
					
				String auxiliar = Generales.getUserID(aux.getField(DboTransaccion.CAMPO_CUENTA_ID), dconn);
				stb.append(auxiliar==null?"":auxiliar).append(",");
				
				stb.append(FechaUtil.toPaginado(aux.getField(DboTransaccion.CAMPO_FEC_HOR)).substring(0, 10)).append(",");
				stb.append(FechaUtil.toPaginado(aux.getField(DboTransaccion.CAMPO_FEC_HOR)).substring(11, 19)).append(",");

				auxiliar = Generales.getNombreOficina(aux.getField(DboTransaccion.CAMPO_REG_PUB_ID), aux.getField(DboTransaccion.CAMPO_OFIC_REG_ID), dconn);
				stb.append(auxiliar==null?"":auxiliar).append(",");
				
				auxiliar = Generales.getNombreServicio(aux.getField(DboTransaccion.CAMPO_SERVICIO_ID), dconn);
				stb.append(auxiliar==null?"":auxiliar).append(",");

				stb.append(aux.getField(DboTransaccion.CAMPO_STR_BUSQ)).append(",");
				stb.append(aux.getField(DboTransaccion.CAMPO_COSTO));

				out_cliente = res.getWriter();
				out_cliente.println(stb.toString());
			}
			double costo_total = dboTransaccion.sum(DboTransaccion.CAMPO_COSTO);
			
			stb = new StringBuffer();
			stb.append("TOTAL: ").append(costo_total);
			out_cliente = res.getWriter();
			out_cliente.println(stb.toString());

			out_cliente.flush();
			out_cliente.close();

			response.setCustomResponse(true);

		} catch (ReporteNoRegistroException re) {
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "N");
			response.setStyle(re.getForward());
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);

			response.setStyle(ce.getForward());
		}catch (ValidacionException ve) {		
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","VerReporteTransacciones.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		return response;
	}

	public ControllerResponse runPorOrganizacionDetalleState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = null;

		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		
		try {
			//Lo de Siempre
				init(request);
				validarSesion(request);
				req = ExpressoHttpSessionBean.getRequest(request);
				UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
				
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
				
			//Verificando si usuario tiene Perfil de Administrador de Jurisdiccion
				boolean esAdminJuris = false;
				if (usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)
					esAdminJuris = true;

			//capturando parámetros y validando cosas
				String peJuriId = request.getParameter("peJuriId");
				//String nombreEmpresa = request.getParameter("nombreEmpresa");
				String razSoc = request.getParameter("razSoc");
				String numDoc = request.getParameter("numDoc");
				
				String razsoc = request.getParameter("razsoc");
				String ruc = request.getParameter("ruc");
				String ai = request.getParameter("anoinicio");
				String mi = request.getParameter("mesinicio");
				String di = request.getParameter("diainicio");
				String af = request.getParameter("anofin");
				String mf = request.getParameter("mesfin");
				String df = request.getParameter("diafin");
				//Verificamos si es una nueva Búsqueda (paginar = false) o es la paginacion (paginar = true)
					boolean paginar = request.getParameter("paginar")==null?false:true;
				//Verificando opcion Costo Cero
					boolean costocero = request.getParameter("costocero")==null?false:true;
					if(costocero)
						req.setAttribute("costocero", "ON");
			//Parametros de Salida
				req.setAttribute("peJuriId", peJuriId);	
				req.setAttribute("perfilId",String.valueOf(usuario.getPerfilId()));
				req.setAttribute("usuario_logeado",usuario.getUserId());
				req.setAttribute("r1", request.getParameter("r1"));
				req.setAttribute("tipousuario", "Organización");
				req.setAttribute("razsoc", razsoc);
				req.setAttribute("ruc", ruc);
				req.setAttribute("razSoc", razSoc);
				//Retornar Fecha para mostrarse
					req.setAttribute("fecini", di + "/" + mi + "/" + ai);
					req.setAttribute("fecfin", df + "/" + mf + "/" + af);


		/* * * * * * * * * * * * * * * R E A L I Z A   L A   T R A N S A C C I O N * * * * * * * * * * * */
			StringBuffer SQL = new StringBuffer("");
			stmt = conn.createStatement();

			SQL.append("SELECT T.CUENTA_ID, T.FEC_HOR, T.REG_PUB_ID, T.OFIC_REG_ID, T.SERVICIO_ID, T.STR_BUSQ, T.COSTO  FROM TRANSACCION T WHERE T.FEC_HOR BETWEEN ");
			SQL.append(request.getParameter("fechaInicio")).append(" AND ").append(request.getParameter("fechaFin"));
			
			if(costocero)	SQL.append(" AND T.COSTO >= 0");
			else	SQL.append(" AND T.COSTO > 0");

			SQL.append(" AND T.CUENTA_ID IN(");
			SQL.append("SELECT ORG_CTAS.CUENTA_ID FROM ORG_CTAS WHERE ORG_CTAS.PE_JURI_ID = '").append(peJuriId).append("')");
	
			if (isTrace(this)) System.out.println(new StringBuffer("SQL>> ").append(SQL.toString()).toString());
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL.toString());

			//boolean hayNext = false;
			boolean encontro = false;

			if(!rs.next())
				throw new ReporteNoRegistroException("muestra");
			else
				encontro = true;
			
			
			//*** MANEJO DE LA PAGINACION
			//int num_pagina = Propiedades.getInstance().getLineasPorPag();
			//int paginacion = Integer.parseInt(request.getParameter("pagina"));
			//*** FIN DE MANEJO DE PAGINACION						

			java.util.List lista = new java.util.ArrayList();

			TransaccionBean TBean = null;
			double costo_total = 0.0;
			do{
				TBean = new TransaccionBean();
				TBean.setRazonSocial(razSoc);
			
				String auxiliar = Generales.getUserID(String.valueOf(rs.getLong(1)), dconn);
				TBean.setUserId(auxiliar==null?"":auxiliar);
				
				java.sql.Timestamp date = rs.getTimestamp(2);
				if(date != null){
					TBean.setFecha(FechaUtil.dateToString(date));
					TBean.setHora(FechaUtil.dateTimeToString(new java.sql.Timestamp(date.getTime())).substring(11, 19));
				}else{
					TBean.setFecha("");
					TBean.setHora("");
				}
				
				auxiliar = Generales.getNombreOficina(rs.getString(3), rs.getString(4), dconn);
				TBean.setNomOficinaRegistral(auxiliar==null?"":auxiliar);
				
				auxiliar = Generales.getNombreServicio(String.valueOf(rs.getLong(5)), dconn);
				TBean.setNomServicio(auxiliar==null?"":auxiliar);

				TBean.setIdDocumento(rs.getString(6));
				TBean.setCosto(String.valueOf(rs.getDouble(7)));
				costo_total = costo_total + rs.getDouble(7);
				lista.add(TBean);
			}while(rs.next());
			
//*PAGINACION EN EL JSP*//			
/*			if(paginacion == 1){
				if(hayNext)
					req.setAttribute("next", "2");
				else
					req.setAttribute("next", null);
				
				req.setAttribute("previous", null);
			}else{
				if(hayNext)
					req.setAttribute("next", String.valueOf(paginacion + 1));
				else
					req.setAttribute("next", null);
				
				req.setAttribute("previous", String.valueOf(paginacion - 1));
			}
			req.setAttribute("numeropaginas", String.valueOf(num_pagina));
			req.setAttribute("pagina", String.valueOf(paginacion));
*/
//*PAGINACION EN EL JSP*//

			req.setAttribute("listatransacciones", lista);
			req.setAttribute("costo_total", Double.toString(costo_total));
			req.setAttribute("hayData", "S");

			response.setStyle("muestra");

		} catch (ReporteNoRegistroException re) 		{
			ExpressoHttpSessionBean.getRequest(request).setAttribute("hayData", "N");
			response.setStyle(re.getForward());
		} catch (CustomException ce) 		{
			log(ce.getCodigoError(), ce.getMessage(), request);
			principal(request);
			response.setStyle(ce.getForward());
		}
		catch (ValidacionException ve) 		{		
			response.setStyle("pantallaFinalReportes");
			req.setAttribute("destino","VerReporteTransacciones.do?state=verFormulario");
			req.setAttribute("mensaje1",ve.getMensaje());			
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		} finally {
			if(stmt != null)
				try{stmt.close();
				}catch(Exception e){}
			if(rs != null)
				try{rs.close();
				}catch(Exception e){}
			pool.release(conn);
			end(request);
		}
		return response;
	}	
}