package gob.pe.sunarp.extranet.administracion.controller;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.administracion.bean.*;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class MantenimientoOrganizacionController extends ControllerExtension implements Constantes
	{
	private String thisClass = MantenimientoOrganizacionController.class.getName() + ".";
	public MantenimientoOrganizacionController() {
		super();
		addState(new State("verFormulario", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("buscar", "Estado que se encarga de direccionar al estado respectivo segun criterio de seleccion"));

		addState(new State("orgRazSoc", "buscar x Razon Social."));
		addState(new State("orgRUC", "buscar x RUC."));
		addState(new State("orgTiempoInac", "buscar Tiempo de Inactividad."));
		
		//activacion-exoneracion de TODOS los usuarios de una Organizacion
		addState(new State("activacion", "activacion/desactivacion de usuarios"));
		addState(new State("exoneracion", "exoneracion de pago"));
		
		setInitialState("verFormulario");
	}

	public String getTitle() {
		return new String("MantenimientoOrganizacionController");
	}
	
	protected ControllerResponse runVerFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		try {
			init(request);
			validarSesion(request);

			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean datosUsuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			//validando el estado de la caja
			/** OBSERVACIONES SUNARP JBUGARIN 22/02/2007**/
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			String cuentaId = datosUsuario.getCuentaId();
			String estInicialCaja = "";
			String caja = datosUsuario.getUserId();
			DboTaCaja dboCaja = new DboTaCaja (dconn);
			dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_ESTA);
					dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL,cuentaId);
					if(dboCaja.find()==true){
						estInicialCaja=dboCaja.getField(DboTaCaja.CAMPO_ESTA);
			
					
					}
					if(estInicialCaja.equals("1") && datosUsuario.getPerfilId()==Constantes.PERFIL_CAJERO ){
							req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. Debe aperturar su caja para poder ingresar a la opción.");
							response.setStyle("ok");
					}
					else if(estInicialCaja.equals("0") && datosUsuario.getPerfilId()==Constantes.PERFIL_CAJERO ){
			
												req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. El tesorero no ha realizado su apertura general.");
												response.setStyle("ok");
					}
			else{
			/** FIN OBSERVACIONES SUNARP **/
			req.setAttribute("perfilId", "" + datosUsuario.getPerfilId());
			response.setStyle("verFormulario");
			//cjvc 30Sept
			ExpressoHttpSessionBean.getSession(request).setAttribute("destinoPrepago", "MantenimientoOrg.do");
			}
		
			
			
		} //try
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			//SE AGREGA EL CIERRE DE LA CONEXION A LA INSTANCIA A LA BASE DE DATOS
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	protected ControllerResponse runBuscarState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			validarSesion(request);
			
			String x = req.getParameter("rad1");
			
			
			
			if (x == null || x.trim().length() == 0)
					throw new ValidacionException("Error en datos de entrada", "");

			int opcion = Integer.parseInt(x);

			String tipoBusqueda = null;

			switch (opcion) {
				case 1 :
					tipoBusqueda = "orgRazSoc";
					break;
				case 2 :
					tipoBusqueda = "orgRUC";
					break;
				case 3 :
					tipoBusqueda = "orgTiempoInac";
					break;
			}

			if (tipoBusqueda == null)
					throw new ValidacionException("Tipo de busqueda no seleccionado", "");

			transition(tipoBusqueda, request, response);
		} //try

		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION", e);
			req.setAttribute("DATOS_FORMULARIO", new Value05Bean());
			try {
				this.transition("verFormulario", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				end(request);
			}
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle("error");

		} catch (Throwable ex) {
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		return response;
	}

	protected ControllerResponse runOrgRazSocState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		//bean para datos del formulario
		Value05Bean beanFormulario = new Value05Bean();
		beanFormulario.setValue01(req.getParameter("rad1"));
						
		try{
			init(request);
			validarSesion(request);
			String sRefrescaAuto ; //agregado jbugarin descaj
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			String razsoc = request.getParameter("razsoc");
			beanFormulario.setValue02(razsoc);

			if (razsoc == null || razsoc.trim().length() <= 0)
				throw new ValidacionException("Campo Razón Social errado","");
			//BUSCAR PERSONAS JURIDICAS QUE CUMPLAN LA CONDICION
			StringBuffer sb = new StringBuffer();
			sb.append(DboPeJuri.CAMPO_RAZ_SOC);
			sb.append(" like '").append(razsoc.trim()).append("%'");
			
			if (usuario.getPerfilId()==PERFIL_ADMIN_JURISDICCION)
				{
					sb.append(" and ").append(DboPeJuri.CAMPO_JURIS_ID);
					sb.append(" = ").append(usuario.getJurisdiccionId());
				}
			
			DboPeJuri dboPeJuri = new DboPeJuri(dconn);
			
			dboPeJuri.setCustomWhereClause(sb.toString());
			
			java.util.ArrayList arri = dboPeJuri.searchAndRetrieveList(dboPeJuri.CAMPO_RAZ_SOC);

			if (arri.size()==0)
				throw new ValidacionException("No se han encontrado resultados para su búsqueda","razsoc");

			java.util.ArrayList listaMantOrg = new java.util.ArrayList();

			//barrer resultados
			DboPersona dboPersona = new DboPersona(dconn);
			DboPeNatu  dboPeNatu  = new DboPeNatu(dconn);
			DboCuenta  dboCuenta  = new DboCuenta(dconn);
			DboLineaPrepago   lin = new DboLineaPrepago(dconn);
			
			for (int i= 0; i < arri.size(); i++)
			{
				MantenOrganizacionBean manten_org = new MantenOrganizacionBean();

				manten_org.setRazsoc(((DboPeJuri) arri.get(i)).getField(dboPeJuri.CAMPO_RAZ_SOC));
				manten_org.setSiglas(((DboPeJuri) arri.get(i)).getField(dboPeJuri.CAMPO_SIGLAS));	
				manten_org.setNum_usu(((DboPeJuri) arri.get(i)).getField(dboPeJuri.CAMPO_NU_USRS));
				manten_org.setCod_org(((DboPeJuri) arri.get(i)).getField(dboPeJuri.CAMPO_PE_JURI_ID));
				
				String personaId = ((DboPeJuri) arri.get(i)).getField(dboPeJuri.CAMPO_PERSONA_ID);
				
				//buscar detalles de la organizacion
				
				//persona
				dboPersona.clearAll();
				dboPersona.setFieldsToRetrieve(dboPersona.CAMPO_NUM_DOC_IDEN);
				dboPersona.setField(dboPersona.CAMPO_PERSONA_ID,personaId);
				dboPersona.find();
				manten_org.setRuc(dboPersona.getField(dboPersona.CAMPO_NUM_DOC_IDEN));
				manten_org.setDireccion(Tarea.getDireccion(dconn, personaId));
				
				//buscar el usuario administrador de la empresa
				dboPeNatu.clearAll();
				dboPeNatu.setFieldsToRetrieve(dboPeNatu.CAMPO_PE_NATU_ID);
				dboPeNatu.setField(dboPeNatu.CAMPO_PE_JURI_ID, manten_org.getCod_org());
				java.util.ArrayList arrj = dboPeNatu.searchAndRetrieveList();
				String tipoUsr="";
				for (int j = 0; j < arrj.size(); j++)
				{
					dboCuenta.clearAll();
					String peNatuId= ((DboPeNatu) arrj.get(j)).getField(dboPeNatu.CAMPO_PE_NATU_ID);
					dboCuenta.setField(dboCuenta.CAMPO_PE_NATU_ID, peNatuId);
					boolean b = dboCuenta.find();
					if (b==true)
					{
						tipoUsr  = dboCuenta.getField(dboCuenta.CAMPO_TIPO_USR);
						String xf = tipoUsr.substring(2,3);
						if (xf.equals("1"))
							break; //usuario administrador encontrado
					}
				}
				manten_org.setUsuario_adm(dboCuenta.getField(dboCuenta.CAMPO_USR_ID));
				manten_org.setUsuarioId(dboCuenta.getField(dboCuenta.CAMPO_USR_ID));
				manten_org.setFlagActivo(dboCuenta.getField(dboCuenta.CAMPO_ESTADO));
				manten_org.setFlagExonPago(dboCuenta.getField(dboCuenta.CAMPO_EXON_PAGO));	
				if (tipoUsr.startsWith("1"))
						manten_org.setTipoOrg("EXTERNO");
				else
						manten_org.setTipoOrg("INTERNO");
				
				//fechas
					String fechaCrea = dboCuenta.getField(dboCuenta.CAMPO_TS_CREA);
					fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
					String fechaUlt  = dboCuenta.getField(dboCuenta.CAMPO_TS_ULT_ACC);
					fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
					int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
					manten_org.setFechaAfiliacion(fechaCrea);
					manten_org.setFechaUltimoAcceso(fechaUlt);
					manten_org.setDiasDesdeUltimoAcceso(""+du);				    							
				
				//linea prepago
				lin.clearAll();
				//*cjvc				
					lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
					//lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, null);
					lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, "is null");
					lin.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, manten_org.getCod_org());
					//lin.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
						
					if(lin.find())
						{
						manten_org.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
						manten_org.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
						}
				//*cjvc	

					//Validacion: la cuenta es visible o no 
					//dependiendo del perfil del usuario logeado
					boolean cuentaVisible = false;
					
					if (usuario.getPerfilId() == PERFIL_CAJERO)	
					{
						if (   tipoUsr.startsWith("101")    )
								cuentaVisible=true;
					}
					
					if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
						usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)	
					{
						//no interesa el primer flag, pero
						//los dos siguientes deben ser "01" 
						String f = tipoUsr.substring(1,3);
						if (   f.equals("01")    )
								cuentaVisible=true;						
					}
				
					if (cuentaVisible==true)
						listaMantOrg.add(manten_org);
							

			}//for (int i= 0; i < arri.size(); i++)
             
             /** agregado jbugarin descaj **/
			    sRefrescaAuto = request.getParameter("chkRefrescaAuto");
				System.out.println("sRefrescaAuto:::"+sRefrescaAuto);
				req.setAttribute("sRefrescaAuto",sRefrescaAuto);
				req.setAttribute("valorRB",request.getParameter("rad1"));
				//para no perder el texto de busqueda del form manteOrg.jsp
				req.setAttribute("buscarRazonSocial",razsoc);
			/** fin **/	

			if (listaMantOrg.size()==0)
				throw new ValidacionException("No se han encontrado resultados para su busqueda","razsoc");
				
				req.setAttribute("perfilId",""+usuario.getPerfilId());
			   
				response.setStyle("verFormulario");
					
				req.setAttribute("listaMantOrg", listaMantOrg);
		}//try
				catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION",e);
			req.setAttribute("DATOS_FORMULARIO",beanFormulario);
			try {
				this.transition("verFormulario", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} finally {
				try{
					pool.release(conn);
				}catch(Throwable tt){}
				end(request);
			}
		}
		
		 catch (CustomException e) {
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
			if (pool != null)
				pool.release(conn);
			end(request);
		}
		return response;
	}

	protected ControllerResponse runOrgRUCState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		//bean para datos del formulario
		Value05Bean beanFormulario = new Value05Bean();
		beanFormulario.setValue01(req.getParameter("rad1"));				
		
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);			

			String ruc = request.getParameter("ruc");
			beanFormulario.setValue03(ruc);

			if(ruc == null || ruc.trim().length() <= 0)
				throw new ValidacionException("Campo RUC errado","");

			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);

				MultiDBObject multi = new MultiDBObject(dconn);
				
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboOrgCtas", "orgctas");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
				
				multi.setForeignKey("persona", DboPersona.CAMPO_PERSONA_ID, "pejuri", DboPeJuri.CAMPO_PERSONA_ID);
				multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PE_JURI_ID, "orgctas", DboOrgCtas.CAMPO_PE_JURI_ID);
				multi.setForeignKey("orgctas", DboOrgCtas.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
	
				multi.setField("persona", DboPersona.CAMPO_TPO_PERS, "J");
				multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, ruc);
				multi.setField("orgctas", DboOrgCtas.CAMPO_FG_ADMIN, "1");
				
				if (usuario.getPerfilId()==PERFIL_ADMIN_JURISDICCION)
					multi.setField("pejuri", DboPeJuri.CAMPO_JURIS_ID, usuario.getJurisdiccionId());				
				
				java.util.List listaMantOrg = new java.util.ArrayList();
				
				for(Iterator i = multi.searchAndRetrieve().iterator(); i.hasNext();){
					MultiDBObject oneMulti = (MultiDBObject) i.next();
					
					MantenOrganizacionBean manten_org = new MantenOrganizacionBean();
					manten_org.setRuc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
					manten_org.setUsuario_adm(oneMulti.getField("cuenta", DboCuenta.CAMPO_CUENTA_ID));
					manten_org.setRazsoc(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
					manten_org.setSiglas(oneMulti.getField("pejuri", DboPeJuri.CAMPO_SIGLAS));
					manten_org.setDireccion(Tarea.getDireccion(dconn, oneMulti.getField("persona", DboPersona.CAMPO_PERSONA_ID)));
					manten_org.setNum_usu(oneMulti.getField("pejuri", DboPeJuri.CAMPO_NU_USRS));
					manten_org.setCod_org(oneMulti.getField("pejuri", DboPeJuri.CAMPO_PE_JURI_ID));
					
					//*cjvc				
						DboLineaPrepago lin = new DboLineaPrepago(dconn);
						lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
						lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, "is null");
						lin.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, oneMulti.getField("pejuri", DboPeJuri.CAMPO_PE_JURI_ID));
						//lin.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
						
						if(lin.find())
							{
							manten_org.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
							manten_org.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
							}
					//*cjvc
					
					//8sep2002HT
					manten_org.setUsuarioId(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
					manten_org.setFlagActivo(oneMulti.getField("cuenta", DboCuenta.CAMPO_ESTADO));
					manten_org.setFlagExonPago(oneMulti.getField("cuenta", DboCuenta.CAMPO_EXON_PAGO));
					String tipoUsr = oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
					
					//22Oct2002HT
					String fechaCrea = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
					fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
					String fechaUlt  = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
					fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
					int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
					manten_org.setFechaAfiliacion(fechaCrea);
					manten_org.setFechaUltimoAcceso(fechaUlt);
					manten_org.setDiasDesdeUltimoAcceso(""+du);
										
					//8oct2002LSJ
					if (tipoUsr.startsWith("1"))
						manten_org.setTipoOrg("EXTERNO");
					else
						manten_org.setTipoOrg("INTERNO");

					//Validacion: la cuenta es visible o no 
					//dependiendo del perfil del usuario logeado
					boolean cuentaVisible = false;
					if (usuario.getPerfilId() == PERFIL_CAJERO)	
					{
						if (   tipoUsr.startsWith("101")    )
								cuentaVisible=true;
					}
					
					if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
						usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)	
					{
						//no interesa el primer flag, pero
						//los dos siguientes deben ser "01" 
						String f = tipoUsr.substring(1,3);
						if (   f.equals("01")    )
								cuentaVisible=true;						
					}
					
					if (cuentaVisible==true)				
						listaMantOrg.add(manten_org);
				}
				
				req.setAttribute("perfilId",""+usuario.getPerfilId());
			/**** AGREGADO JBUGARIN DESCAJ 05/01/07 ****/
							String sRefrescaAuto2 = request.getParameter("chkRefrescaAuto");
							req.setAttribute("sRefrescaAuto",sRefrescaAuto2);
							req.setAttribute("valorRB",request.getParameter("rad1"));
							//para no perder el texto de busqueda del form manteOrg.jsp
							req.setAttribute("buscarRuc",ruc);
							
			/**** FIN JBUGARIN DESCAJ 05/01/07 ****/	
				response.setStyle("verFormulario");
							
				if (listaMantOrg.size()==0)
					throw new ValidacionException("No se han encontrado resultados para su busqueda","ruc");

				req.setAttribute("listaMantOrg", listaMantOrg);				
		}//
				catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION",e);
			req.setAttribute("DATOS_FORMULARIO",beanFormulario);
			try {
				this.transition("verFormulario", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} finally {
					try{
					pool.release(conn);
					}catch(Throwable ttt){}
				end(request);
			}
		}
		
		 catch (CustomException e) {
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
					}catch(Throwable ttt){}
			end(request);
		}
		return response;
	}

	protected ControllerResponse runOrgTiempoInacState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		
		//bean para datos del formulario
		Value05Bean beanFormulario = new Value05Bean();
		beanFormulario.setValue01(req.getParameter("rad1"));
				
		try{
			init(request);
			validarSesion(request);
			
			//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);		

			String tiempo = request.getParameter("tiempo");
			beanFormulario.setValue04(tiempo);
		
			if (tiempo == null || tiempo.trim().length() <= 0)
				throw new ValidacionException("Campo tiempo errado", "");
				
			int dias;
			try{
				dias = Integer.parseInt(tiempo);
			}catch(NumberFormatException nfe){
				throw new CustomException(Constantes.EC_PARAM_MISSFORMED);
			}
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			DboOrgCtas dboOrgCtas = new DboOrgCtas(dconn);
				MultiDBObject multi = new MultiDBObject(dconn);
				
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboOrgCtas", "orgctas");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
				
				multi.setForeignKey("persona", DboPersona.CAMPO_PERSONA_ID, "pejuri", DboPeJuri.CAMPO_PERSONA_ID);
				multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PE_JURI_ID, "orgctas", DboOrgCtas.CAMPO_PE_JURI_ID);
				multi.setForeignKey("orgctas", DboOrgCtas.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
	
				multi.setField("persona", DboPersona.CAMPO_TPO_PERS, "J");

				String fecha_limite = FechaUtil.add(FechaUtil.dateToString(new java.util.Date()), -dias);
				
				String fecha = " < " + FechaUtil.stringTimeToOracleString(fecha_limite + " 00:00:00");
	
				multi.setField("orgctas", DboOrgCtas.CAMPO_FG_ADMIN, "1");

				if (usuario.getPerfilId()==PERFIL_ADMIN_JURISDICCION)
					multi.setField("pejuri", DboPeJuri.CAMPO_JURIS_ID, usuario.getJurisdiccionId());				
				
				//multi.setField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC, fecha);cb
				multi.setAppendWhereClause(" CUENTA." + DboCuenta.CAMPO_TS_ULT_ACC + fecha);//cb

				java.util.List listaMantOrg = new java.util.ArrayList();
				
				for(Iterator i = multi.searchAndRetrieve().iterator(); i.hasNext();){
					
					MultiDBObject oneMulti = (MultiDBObject) i.next();
					
					MantenOrganizacionBean manten_org = new MantenOrganizacionBean();
					manten_org.setRuc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
					manten_org.setUsuario_adm(oneMulti.getField("cuenta", DboCuenta.CAMPO_CUENTA_ID));
					manten_org.setRazsoc(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
					manten_org.setSiglas(oneMulti.getField("pejuri", DboPeJuri.CAMPO_SIGLAS));
					manten_org.setDireccion(Tarea.getDireccion(dconn, oneMulti.getField("persona", DboPersona.CAMPO_PERSONA_ID)));
					manten_org.setNum_usu(oneMulti.getField("pejuri", DboPeJuri.CAMPO_NU_USRS));
					manten_org.setCod_org(oneMulti.getField("pejuri", DboPeJuri.CAMPO_PE_JURI_ID));

					//*cjvc				
						DboLineaPrepago lin = new DboLineaPrepago(dconn);
						lin.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID+"|"+DboLineaPrepago.CAMPO_SALDO);
						lin.setField(DboLineaPrepago.CAMPO_CUENTA_ID, "is null");
						lin.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, oneMulti.getField("pejuri", DboPeJuri.CAMPO_PE_JURI_ID));
						//lin.setField(DboLineaPrepago.CAMPO_ESTADO, "1");
						
						if(lin.find())
							{
							manten_org.setLineaPrepago(lin.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
							manten_org.setSaldo(lin.getField(DboLineaPrepago.CAMPO_SALDO));
							}
					//*cjvc
					
					//8sep2002HT
					manten_org.setUsuarioId(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
					manten_org.setFlagActivo(oneMulti.getField("cuenta", DboCuenta.CAMPO_ESTADO));
					manten_org.setFlagExonPago(oneMulti.getField("cuenta", DboCuenta.CAMPO_EXON_PAGO));
					
					//22Oct2002HT
					String fechaCrea = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_CREA);
					fechaCrea = FechaUtil.expressoDateToUtilDate(fechaCrea);
					String fechaUlt  = oneMulti.getField("cuenta", DboCuenta.CAMPO_TS_ULT_ACC);
					fechaUlt = FechaUtil.expressoDateToUtilDate(fechaUlt);
					int du = FechaUtil.getDays(fechaUlt,FechaUtil.getCurrentDate());
					manten_org.setFechaAfiliacion(fechaCrea);
					manten_org.setFechaUltimoAcceso(fechaUlt);
					manten_org.setDiasDesdeUltimoAcceso(""+du);
					
					String tipoUsr = oneMulti.getField("cuenta", DboCuenta.CAMPO_TIPO_USR);
					//8oct2002LSJ
					if (tipoUsr.startsWith("1"))
						manten_org.setTipoOrg("EXTERNO");
					else
						manten_org.setTipoOrg("INTERNO");

				
					//Validacion: la cuenta es visible o no 
					//dependiendo del perfil del usuario logeado
					boolean cuentaVisible = false;
					if (usuario.getPerfilId() == PERFIL_CAJERO)	
					{
						if (   tipoUsr.startsWith("101")    )
								cuentaVisible=true;
					}
					
					if (usuario.getPerfilId() == PERFIL_ADMIN_GENERAL ||
						usuario.getPerfilId() == PERFIL_ADMIN_JURISDICCION)	
					{
						//no interesa el primer flag, pero
						//los dos siguientes deben ser "01" 
						String f = tipoUsr.substring(1,3);
						if (   f.equals("01")    )
								cuentaVisible=true;						
					}

					if (cuentaVisible==true)									
						listaMantOrg.add(manten_org);
				}

				req.setAttribute("perfilId",""+usuario.getPerfilId());
			/***** agregado jbugarin descaj *****/
							String sRefrescaAuto3 = request.getParameter("chkRefrescaAuto");
							req.setAttribute("sRefrescaAuto",sRefrescaAuto3);
							req.setAttribute("valorRB",request.getParameter("rad1"));
							//para no perder el texto de busqueda del form manteOrg.jsp
							req.setAttribute("buscarTiempo",tiempo);
			/***** fin *****/	
				
				response.setStyle("verFormulario");
				
				if (listaMantOrg.size()==0)
					throw new ValidacionException("No se han encontrado resultados para su busqueda","tiempo");
				req.setAttribute("listaMantOrg", listaMantOrg);					
		} //try

		catch (ValidacionException e) {
			req.setAttribute("VALIDACION_EXCEPTION",e);
			req.setAttribute("DATOS_FORMULARIO",beanFormulario);
			try {
				this.transition("verFormulario", request, response);
			} catch (Throwable ex) {
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			} finally {
					try{
					pool.release(conn);
					}catch(Throwable ttt){}
				end(request);
			}
		}		
		
		catch (CustomException e) {
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
					}catch(Throwable ttt){}
			end(request);
		}
		return response;
	}
	
			protected ControllerResponse runActivacionState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

			try {
				init(request);
				validarSesion(request);

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

				//activar o desactivar TODOS LOS USUARIOS
				// DE LA ORGANIZACION
				//dependiendo de lo solicitado en param1

				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

				String param1 = req.getParameter("param1"); //operacion
				String param2 = req.getParameter("param2"); //codigo empresa

				DboLineaPrepago dboLineaPrepago = new DboLineaPrepago(dconn);
				
				DboPeNatu dboPeNatu = new DboPeNatu(dconn);
				dboPeNatu.setFieldsToRetrieve(dboPeNatu.CAMPO_PE_NATU_ID);
				dboPeNatu.setField(dboPeNatu.CAMPO_PE_JURI_ID,param2);
				ArrayList arr1 = dboPeNatu.searchAndRetrieveList();
				
				int contador=0;
				
				String nuevoEstado;
				if (param1.equals("0"))
					nuevoEstado = "0"; //desactivado
				else
					nuevoEstado = "1"; //activado

				DboCuenta dboCuenta = new DboCuenta(dconn);				
				for (int i=0; i < arr1.size(); i++)
				{
					String peNatuId = ((DboPeNatu) arr1.get(i)).getField(DboPeNatu.CAMPO_PE_NATU_ID);
					dboCuenta.clearAll();
					dboCuenta.setField(DboCuenta.CAMPO_PE_NATU_ID,peNatuId);
					if (dboCuenta.find()==true)	
						{
							String tipoUsr = dboCuenta.getField(DboCuenta.CAMPO_TIPO_USR);
							boolean adm = false;
							String flag = tipoUsr.substring(2,3);
							if (flag.equals("1")==true)
								adm = true;
							
							dboCuenta.setFieldsToUpdate(dboCuenta.CAMPO_ESTADO);
							contador++;
							dboCuenta.setField(DboCuenta.CAMPO_ESTADO, nuevoEstado);
							dboCuenta.update();
							
							//se debe cambiar tambien el estado de la linea de cada usuario
							//Nota: solamente se activa la linea del Administrador
							if (nuevoEstado.equals("0")==true ||
								(nuevoEstado.equals("1")==true && adm==true)
								)
							{
								dboLineaPrepago.clearAll();
								dboLineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID,dboCuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
								if (dboLineaPrepago.find()==true)
								{
									dboLineaPrepago.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
									dboLineaPrepago.setField(DboLineaPrepago.CAMPO_ESTADO,nuevoEstado);
									dboLineaPrepago.update();
								}
							}//if (nuevoEstado.equ
						}//if (dboCuenta.find()==true)	
				}//for
				
				//ademas cambiar el estado de la linea de la organizacion
				dboLineaPrepago.clearAll();
				//dboLineaPrepago.setField(DboLineaPrepago.CAMPO_CUENTA_ID,"is null");
				dboLineaPrepago.setField(DboLineaPrepago.CAMPO_PE_JURI_ID,param2);
				
				if (dboLineaPrepago.find()==true)
					{
						dboLineaPrepago.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
						dboLineaPrepago.setField(DboLineaPrepago.CAMPO_ESTADO,nuevoEstado);
						dboLineaPrepago.update();
					}

				StringBuffer sb = new StringBuffer();
				sb.append("Operacion completada, registros afectados : ");
				sb.append(contador);

				if (contador>0)					
					conn.commit();

				req.setAttribute("mensaje1",sb.toString());
				req.setAttribute("destino","MantenimientoOrg.do");

				response.setStyle("ok");
				
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
					}catch(Throwable ttt){}
				end(request);
			}
			return response;
		}//fin metodo activacion
		
		
		protected ControllerResponse runExoneracionState(
	ControllerRequest request,
	ControllerResponse response)
	throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

	try {
		init(request);
		validarSesion(request);

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

				//exonerar o no a TODOS LOS USUARIOS
				// DE LA ORGANIZACION
				//dependiendo de lo solicitado en param1

				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

				String param1 = req.getParameter("param1"); //operacion
				String param2 = req.getParameter("param2"); //codigo empresa

				DboPeNatu dboPeNatu = new DboPeNatu(dconn);
				dboPeNatu.setFieldsToRetrieve(dboPeNatu.CAMPO_PE_NATU_ID);
				dboPeNatu.setField(dboPeNatu.CAMPO_PE_JURI_ID,param2);
				ArrayList arr1 = dboPeNatu.searchAndRetrieveList();
				
				int contador=0;
				
				String nuevoEstado;
				if (param1.equals("0"))
					nuevoEstado = "0"; //quitar exoneracion
				else
					nuevoEstado = "1"; //exoneracion

				DboCuenta dboCuenta = new DboCuenta(dconn);				
				for (int i=0; i < arr1.size(); i++)
				{
					String peNatuId = ((DboPeNatu) arr1.get(i)).getField(DboPeNatu.CAMPO_PE_NATU_ID);
					dboCuenta.clearAll();
					dboCuenta.setField(DboCuenta.CAMPO_PE_NATU_ID,peNatuId);
					if (dboCuenta.find()==true)	
						{
							dboCuenta.setFieldsToUpdate(dboCuenta.CAMPO_EXON_PAGO);
							contador++;
							dboCuenta.setField(DboCuenta.CAMPO_EXON_PAGO, nuevoEstado); 
							dboCuenta.update();
						}
				}
				

				StringBuffer sb = new StringBuffer();

				sb.append("Operacion completada, registros afectados : ");
				sb.append(contador);

				if (contador>0)					
					conn.commit();


				req.setAttribute("mensaje1",sb.toString());
				req.setAttribute("destino","MantenimientoOrg.do");

				response.setStyle("ok");
				
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
					}catch(Throwable ttt){}
		end(request);
	}
	return response;
}//fin metodo exoneracion
		
		
}//fin de clase
