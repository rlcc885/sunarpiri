package gob.pe.sunarp.extranet.prepago.controller;

import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.NonHandleableException;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

import gob.pe.sunarp.extranet.administracion.bean.DatosUsuarioBean;
import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.prepago.bean.AbonoVentanillaBean;
import gob.pe.sunarp.extranet.prepago.bean.ConstanciaPagBean;
import gob.pe.sunarp.extranet.prepago.bean.ConstanciaPagoException;
import gob.pe.sunarp.extranet.prepago.bean.MovimientoBean;
import gob.pe.sunarp.extranet.publicidad.bean.OficRegistralesBean;
import gob.pe.sunarp.extranet.util.*;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;
//-
public class ConstanciaPagoController extends ControllerExtension implements Constantes{

	private String thisClass = ConstanciaPagoController.class.getName() + ".";

	public ConstanciaPagoController() {
		super();
		addState(new State("muestra", "Muestra la ventana de Busqueda y Resultados"));
		addState(new State("decide", "Ventana de Busq. x Usuario-Directa."));
		addState(new State("usuarioOrg", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("usuarioDirecta", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("muestraMovimientos", "Ventana de Busq. x Apellidos y Nombres."));
		addState(new State("exporta", "Exporta los datos."));
		addState(new State("certificadoGlobal", "Genera el certificado global de pago."));
		addState(new State("certificadoIndividual", "Genera el certificado individual de pago."));
		addState(new State("certificadoGlobalOrg", "Genera el certificado global de pago."));
		addState(new State("certificadoIndividualOrg", "Genera el certificado individual de pago."));
		addState(new State("regresar", "Regresa a la busqueda."));
		setInitialState("muestra");
	}

	public String getTitle() {
		return new String("ConstanciaPagoController");
	}

	private void muestraOficinas(ControllerRequest request, DBConnection dconn) throws DBException, Exception
	{
		
			DboOficRegistral oficreg = new DboOficRegistral(dconn);
			oficreg.setFieldsToRetrieve(DboOficRegistral.CAMPO_REG_PUB_ID + "|" + DboOficRegistral.CAMPO_OFIC_REG_ID + "|" + DboOficRegistral.CAMPO_NOMBRE);
			
			ArrayList lista = oficreg.searchAndRetrieveList(DboOficRegistral.CAMPO_NOMBRE);
			DboOficRegistral oficregis = null;
			OficRegistralesBean bean = null;
			List listaOfic = new ArrayList();
			
			for(int i = 0; i < lista.size(); i++)
			{
				oficregis = (DboOficRegistral) lista.get(i);
				bean = new OficRegistralesBean();
				bean.setRegPubId(oficregis.getField(DboOficRegistral.CAMPO_REG_PUB_ID));
				bean.setOficRegId(oficregis.getField(DboOficRegistral.CAMPO_OFIC_REG_ID));
				bean.setNombre(oficregis.getField(DboOficRegistral.CAMPO_NOMBRE));
				listaOfic.add(bean);
			}
			ExpressoHttpSessionBean.getRequest(request).setAttribute("listaOficinas", listaOfic);
		
	}
	protected ControllerResponse runMuestraState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			muestraOficinas(request, dconn);
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", null);
			
			response.setStyle("muestra");
			
		}catch(Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		return response;
	}

	protected ControllerResponse runDecideState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		try{
			int opcion = 0;
			try{
				opcion = Integer.parseInt(request.getParameter("radio"));
			}catch(NumberFormatException nfe){
				throw new CustomException(Errors.EC_GENERIC_ERROR, "Debe seleccionar una opcion", "errorPrepago");
			}
			switch(opcion)
			{
				case 1: transition("usuarioDirecta", request, response); break;
				case 2: transition("usuarioOrg", request, response); break;
				default: throw new CustomException(Errors.EC_GENERIC_ERROR, "Debe seleccionar una opcion", "errorPrepago");
			}
		}catch(CustomException ce){
			log(Errors.EC_GENERIC_ERROR, ce.getMessage(), ce, request);
			response.setStyle(ce.getForward());
		}catch(NonHandleableException nhe){
			log(Errors.EC_GENERIC_ERROR, "", nhe, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			response.setStyle("error");
		}finally{
			end(request);
		}
		return response;
	}

	protected ControllerResponse runUsuarioOrgState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		 
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		 
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			muestraOficinas(request, dconn);
			String razsoc = request.getParameter("razsoc");
			String ruc = request.getParameter("ruc");

			boolean paseRuc = true;
			boolean paseRS = true;
			
			if (razsoc == null || razsoc.trim().length() <= 0)
				paseRS = false;

			if(ruc == null || ruc.trim().length() <= 0)
				paseRuc = false;
			
			if (!paseRuc && !paseRS)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Debe ingresar un Numero de RUC o la Razón social", "errorPrepago");
				
			StringBuffer feci = new StringBuffer(request.getParameter("diainicio"));
			/** modificado jbugarin para que permita consulta en el mismo dia**/
			feci.append("/").append(request.getParameter("mesinicio")).append("/").append(request.getParameter("anoinicio")).append(" 00:00:00 ");
			String fechaInicio = FechaUtil.stringTimeToOracleString(feci.toString());
			/** fin modificado**/
			StringBuffer fecf = new StringBuffer(request.getParameter("diafin"));
			fecf.append("/").append(request.getParameter("mesfin")).append("/").append(request.getParameter("anofin")).append(" 23:59:00 ");
			/***DESCAJ ifigueroa 11/01/2007 cambio para que funcione la busqueda***/
			//String fechaFin = FechaUtil.stringToOracleString23(fecf.toString());
			/** jbugarin modificado **/
			String fechaFin = FechaUtil.stringTimeToOracleString(fecf.toString());
			/** fin jbugarin **/
			String tipopago = request.getParameter("tipopago");
			
			java.util.StringTokenizer tok = new java.util.StringTokenizer(request.getParameter("agencia"), "|");
			
			String regpubid = tok.nextToken();
			
			String oficregid = null;
			if(!regpubid.trim().equalsIgnoreCase("T"))
				oficregid = tok.nextToken();
			else oficregid = "T";

			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
				
			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PE_JURI_ID, "linprepag", DboLineaPrepago.CAMPO_PE_JURI_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			multi.setField("pejuri", DboPeJuri.CAMPO_TIPO_ORG, "0");
			multi.setField("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, null);//cb
			/***DESCAJ ifigueroa 11/01/2007 **/
			String accion = request.getParameter("accion");
			String linea = request.getParameter("lineaPrepago");
			if(linea!=null && linea.length()>0&&accion.equalsIgnoreCase("paginar")){
				multi.setField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, linea);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("lineaPrepago",linea);
			}
			/***DESCAJ ifigueroa 16/01/2007 **/
			
			//multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO, "1");

			if(paseRuc)
			{
				multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, ruc);
				multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, Constantes.TIPO_DOCUMENTO_RUC);
				req.setAttribute("origenConstancia","De la organización con Ruc " + ruc);
			}
			
			if(paseRS)
			{
				multi.setField("pejuri", DboPeJuri.CAMPO_RAZ_SOC, razsoc);
				req.setAttribute("origenConstancia","De la organización con Razón Social " + razsoc);
			}

			java.util.List lista = new java.util.ArrayList();
				
			AbonoVentanillaBean bean = null;
			java.util.Hashtable tabla = new java.util.Hashtable();

			//int a = multi.searchAndRetrieve().size();
			
			//	Realizando Primer Query				
			for(Iterator i = multi.searchAndRetrieve().iterator(); i.hasNext();)
			{
				MultiDBObject oneMulti = (MultiDBObject) i.next();
					
				bean = new AbonoVentanillaBean();
				bean.setUsuarioId(null);
				bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
				bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
				bean.setNombre(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
				bean.setAfil_organiz(null);
				bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
				tabla.put(bean.getLineaPrepago(), bean);
				lista.add(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
			}
			
			/*	NO HUBO RESULTADOS	*/
			if(lista.size() <= 0)
				throw new ConstanciaPagoException("muestra");

			/*	HUBO RESULTADOS	*/
			StringBuffer cadena = new StringBuffer(" IN ('");
			cadena.append((String) lista.get(0)).append("'");
					
			for(int j = 1; j < lista.size(); j++)
				cadena.append(", '").append((String) lista.get(j)).append("'");

			cadena.append(") ");

			DboVwMovimiento vista = new DboVwMovimiento(dconn);
			vista.setFieldDistinct(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID, true);
			vista.setFieldsToRetrieve(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID);
			
			if(!regpubid.equalsIgnoreCase("T"))
			{
				vista.setField(DboVwMovimiento.CAMPO_REG_PUB_ID, regpubid);
				vista.setField(DboVwMovimiento.CAMPO_OFIC_REG_ID, oficregid);
			}
						
			if(!tipopago.equals("X"))
				vista.setField(DboVwMovimiento.CAMPO_TIPO_VENT, tipopago);

			//vista.setField(DboVwMovimiento.CAMPO_FEC_HOR, " BETWEEN " + fechaInicio + " AND " + fechaFin);cb
			vista.setAppendWhereClause(DboVwMovimiento.CAMPO_FEC_HOR + " BETWEEN " + fechaInicio + " AND " + fechaFin);//cb
			
			//vista.setField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID, cadena.toString());cb
			vista.setAppendWhereClause(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID + cadena.toString());//cb

			DboVwMovimiento mov = null;
			AbonoVentanillaBean nuevobean = new AbonoVentanillaBean();

			//	2do Query - Busca la linea Prepago y 
			//	verificara cuantos resultados existen (0,1 o mas)
			java.util.List listatemp = vista.searchAndRetrieveList();

			/*	NO HUBO RESULTADOS	*/
			if(listatemp.size() <= 0)
				throw new ConstanciaPagoException("muestra");

			/*	SE ENCONTRO UN RESULTADO	*/
			if(listatemp.size() == 1)
			{
				Iterator i = listatemp.iterator();
				mov = (DboVwMovimiento) i.next();
				nuevobean = (AbonoVentanillaBean) tabla.get(mov.getField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID));

				vista = new DboVwMovimiento(dconn);
				//vista.setField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID, cadena.toString());
				vista.setField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID, nuevobean.getLineaPrepago());
						 
				if(!regpubid.equalsIgnoreCase("T"))
				{
					vista.setField(DboVwMovimiento.CAMPO_REG_PUB_ID, regpubid);
					vista.setField(DboVwMovimiento.CAMPO_OFIC_REG_ID, oficregid);
				}
						
				if(!tipopago.equals("X"))
					vista.setField(DboVwMovimiento.CAMPO_TIPO_VENT, tipopago);

				//vista.setField(DboVwMovimiento.CAMPO_FEC_HOR, " BETWEEN " + fechaInicio + " AND " + fechaFin);cb
				vista.setAppendWhereClause(DboVwMovimiento.CAMPO_FEC_HOR + " BETWEEN " + fechaInicio + " AND " + fechaFin);//cb

					//*** MANEJO DE LA PAGINACION
					vista.setMaxRecords(Propiedades.getInstance().getLineasPorPag());
					
					int paginacion = Integer.parseInt(request.getParameter("pagina"));
					if(paginacion == 1)
					{
						ExpressoHttpSessionBean.getRequest(request).setAttribute("next", "2");
						ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", null);
					}
					else
					{
						ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(paginacion + 1));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(paginacion - 1));
					}
					ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
					//*** FIN DE MANEJO DE PAGINACION						

					java.util.List listilla = new java.util.ArrayList();
						
					DboVwMovimiento movx = null;
					MovimientoBean movbean = null;
					//String a1 = movx.getField(DboVwMovimiento.CAMPO_FEC_HOR);
					/****DESCAJ 12/01/2007 ifigueroa****/
				
					HashMap hmAbono=null;
					if(accion.equalsIgnoreCase("paginar"))
						hmAbono= (HashMap)ExpressoHttpSessionBean.getSession(request).getAttribute("hmAbonoSel");

					//for(Iterator w = vista.searchAndRetrieveList().iterator();w.hasNext();)
					vista.setMaxRecords(10);
					for(Iterator w = vista.searchAndRetrieveListPaginado(paginacion).iterator();w.hasNext();)
					{
						movx = (DboVwMovimiento) w.next();
						movbean = new MovimientoBean();
						movbean.setAgencia(movx.getField(DboVwMovimiento.CAMPO_NOMBRE));
						/****DESCAJ 12/*01/2007 ifigueroa inicio****/
					/*	movbean.setFecha(movx.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(8, 10) + "/" +
											movx.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(5, 7) + "/" +
											movx.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(0, 4));
					*/						
						movbean.setFecha(FechaUtil.toPaginado(movx.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(0, 10));
//						movi.setHora(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(11, 19));
						movbean.setHora(FechaUtil.toPaginado(movx.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(11, 19));
									
										
							//movbean.setFormaPago(movx.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT));
						//	System.out.println(movx.getField(DboVwMovimiento.CAMPO_FEC_HOR));
						//movbean.setHora(movx.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(11, 19));
						/****DESCAJ 12/*01/2007 ifigueroa fin****/	
						String monto_formateado = Tarea.formatoNumero(movx.getField(DboVwMovimiento.CAMPO_MONTO));
						movbean.setMonto(monto_formateado);
						
						movbean.setNumAbono(movx.getField(DboVwMovimiento.CAMPO_ABONO_ID));
						
						//movbean.setTipo(movx.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase("A")?"Ampliacion":"Deposito");
						if(movx.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase(Constantes.ABONO_CONCEPTO_AMPLIACION_CUENTA))
						{
							movbean.setTipo("Ampliacion");
						}
						else if(movx.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase(Constantes.ABONO_CONCEPTO_DEPOSITO_APERTURA))
						{
							movbean.setTipo("Deposito");
						}
						else
						{
							movbean.setTipo("Publicidad Certificada");
						}
							
						if(movx.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).trim().length() == 1)
							movbean.setFormaPago(movx.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).equalsIgnoreCase("E")?"EFECTIVO":"CHEQUE");
						else
							movbean.setFormaPago(movx.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT));
						/****DESCAJ 12/*01/2007 ifigueroa inicio****/		
						movbean.setCajeroId(movx.getField(DboVwMovimiento.CAMPO_USR_CAJA));

						if(hmAbono!=null && hmAbono.containsKey(movbean.getNumAbono()))
							movbean.setSeleccionado(INDICA_SELECCIONADO);
						else
							movbean.setSeleccionado(INDICA_NO_SELECCIONADO);
						movbean.setNumZona(movx.getField(DboVwMovimiento.CAMPO_REG_PUB_ID));	
						/****DESCAJ 12/*01/2007 ifigueroa fin****/	
						listilla.add(movbean);
					}
				/**DESCAJ ifigueroa  09/01/2007**/
					List listaTemporal=null;
	
					if (accion.equalsIgnoreCase("buscar")){
						ExpressoHttpSessionBean.getSession(request).removeAttribute("hmAbonoSel");
					}else if(accion.equalsIgnoreCase("paginar")){
						String[] abono = ExpressoHttpSessionBean.getRequest(request).getParameterValues("chkAbono");
						listaTemporal=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("listaAbono");
	
						if(listaTemporal!=null&&hmAbono!=null&&!hmAbono.isEmpty()){
							for(int k=0;k<listaTemporal.size();k++){
								MovimientoBean movTemp=(MovimientoBean)listaTemporal.get(k);
								if(hmAbono.containsKey(movTemp.getNumAbono()))
									hmAbono.remove(movTemp.getNumAbono());
							}
						}
						if(abono!=null&&abono.length>0){
							if (listaTemporal!=null){ 
								if(hmAbono==null){						
									hmAbono = new HashMap();						
								}
								for(int k=0;k<abono.length;k++){
									MovimientoBean movBean=(MovimientoBean)listaTemporal.get(Integer.parseInt(abono[k]));
									if(!hmAbono.containsKey(movBean.getNumAbono()))
										hmAbono.put(movBean.getNumAbono(),movBean);
								}
							}
						}
					}
					if(hmAbono!=null)
					ExpressoHttpSessionBean.getSession(request).setAttribute("hmAbonoSel",hmAbono);
					ExpressoHttpSessionBean.getSession(request).setAttribute("listaAbono",listilla);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_ORGANIZACION);
					/**DESCAJ ifigueroa  09/01/2007**/

					ExpressoHttpSessionBean.getRequest(request).setAttribute("fecini", feci.toString());
					ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfin", fecf.toString());

					if(paseRuc)
						ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", "RUC: " + ruc);
								
					if(paseRS)
						ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", "Razón Social: " + razsoc);

					req.setAttribute("contador0", null);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", "X");
					ExpressoHttpSessionBean.getRequest(request).setAttribute("contador2", null);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMovimiento", listilla);
					
			}
			else
			{
			/*	SE ENCONTRO 2 O MAS RESULTADOS	*/
				java.util.List listu = new java.util.ArrayList();
						
				for(Iterator i = listatemp.iterator();i.hasNext();)
				{
					mov = (DboVwMovimiento) i.next();
					AbonoVentanillaBean nuevo = new AbonoVentanillaBean();
					nuevobean = (AbonoVentanillaBean) tabla.get(mov.getField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID));
					listu.add(nuevobean);
				}

				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", null);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", null);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador2", "X");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", listu);
							
				ExpressoHttpSessionBean.getRequest(request).setAttribute("fecinig", feci.toString());
				ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfing", fecf.toString());
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tipog", tipopago);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("regId", regpubid);

				ExpressoHttpSessionBean.getRequest(request).setAttribute("oficId", oficregid);
				//ExpressoHttpSessionBean.getRequest(request).setAttribute("oficId", regpubid.equalsIgnoreCase("T")?" ":oficregid);
				
				if(paseRuc)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("FF", ruc);

				if(paseRS)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("RR", razsoc);
			}
				
						
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("rs1", razsoc);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc1", ruc);
				
			response.setStyle("muestra");
		} catch (ConstanciaPagoException cpe) {
			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", null);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador2", null);

			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("rs1", request.getParameter("razsoc"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc1", request.getParameter("ruc"));

			response.setStyle(cpe.getForward());
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
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


	protected ControllerResponse runUsuarioDirectaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			muestraOficinas(request, dconn);
			
			String usuarioId = request.getParameter("userId");
			
			UsuarioBean userBean = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			
			if (usuarioId == null || usuarioId.trim().length() <= 0)
				throw new CustomException(Errors.EC_MISSING_PARAM, "Debe ingresar una cuenta de usuario", "errorPrepago");
			
			DboCuenta cuenta = new DboCuenta(dconn);
			cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
			cuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);

			if (!cuenta.find())
				throw new ConstanciaPagoException("muestra");
				
			req.setAttribute("origenConstancia","Del usuario " + usuarioId);
				
			String cuentaId = cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
				
			AbonoVentanillaBean bean = new AbonoVentanillaBean();
				
			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
	
			multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
			multi.setField("cuenta", DboCuenta.CAMPO_CUENTA_ID, cuentaId);
			multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");
			multi.setField("tipodoc",DboTmDocIden.CAMPO_ESTADO,"1");
				
			java.util.Vector elementos = multi.searchAndRetrieve();
				
			if(elementos.size() <= 0)
				throw new ConstanciaPagoException("muestra");
				
			if(elementos.size() > 1)
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD, "Error de Integridad en la Base de Datos: Existen 2 registros con la misma cuenta.", "errorPrepago");
		
			MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
			bean.setAfil_organiz(null);
		
			// La LineaPrepago			
			String linea = oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
					
			//**************DEBERIA UTILIZAR EL OTRO ESTADO*******************************
					
			StringBuffer feci = new StringBuffer(request.getParameter("diainicio"));
			feci.append("/").append(request.getParameter("mesinicio")).append("/").append(request.getParameter("anoinicio")).append(" 00:00:00 ");
			String fechaInicio = FechaUtil.stringTimeToOracleString(feci.toString());//modificado jbugarin
					
			StringBuffer fecf = new StringBuffer(request.getParameter("diafin"));
			fecf.append("/").append(request.getParameter("mesfin")).append("/").append(request.getParameter("anofin")).append(" 23:59:00 ");//modificado jbugarin
			String fechaFin = FechaUtil.stringTimeToOracleString(fecf.toString());
		
			String tipopago = request.getParameter("tipopago");

			java.util.StringTokenizer tok = new java.util.StringTokenizer(request.getParameter("agencia"), "|");
					
			String regpubid = tok.nextToken();
			String oficregid = null;
					
			if(!regpubid.equalsIgnoreCase("T"))
				oficregid = tok.nextToken();
					
		
			DboVwMovimiento vista = new DboVwMovimiento(dconn);
			vista.setField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID, linea);
							
			if(!regpubid.equalsIgnoreCase("T"))
			{
				vista.setField(DboVwMovimiento.CAMPO_REG_PUB_ID, regpubid);
				vista.setField(DboVwMovimiento.CAMPO_OFIC_REG_ID, oficregid);
			}
						
			if(!tipopago.equals("X"))
				vista.setField(DboVwMovimiento.CAMPO_TIPO_VENT, tipopago);
		
			//vista.setField(DboVwMovimiento.CAMPO_FEC_HOR, " BETWEEN " + fechaInicio + " AND " + fechaFin + " ");cb
			vista.setAppendWhereClause(DboVwMovimiento.CAMPO_FEC_HOR + " BETWEEN " + fechaInicio + " AND " + fechaFin + " ");//cb
						
			DboVwMovimiento mov = null;
			AbonoVentanillaBean nuevobean = new AbonoVentanillaBean();
							
			MovimientoBean movi = null;
							
			java.util.List lista = new java.util.ArrayList();
					
			//*** MANEJO DE LA PAGINACION
			int paginacion = Integer.parseInt(request.getParameter("pagina"));
					
			vista.setMaxRecords(10);
					
			if(paginacion == 1)
			{
				req.setAttribute("next", "2");
				req.setAttribute("previous", null);
			}else{
				req.setAttribute("next", String.valueOf(paginacion + 1));
				req.setAttribute("previous", String.valueOf(paginacion - 1));
			}

			ExpressoHttpSessionBean.getRequest(request).setAttribute("pagina", String.valueOf(paginacion));
//*** FIN DE MANEJO DE PAGINACION						
			java.util.List l = vista.searchAndRetrieveListPaginado(paginacion);
		

			//java.util.List l = vista.searchAndRetrieveListPaginado(1);
					
//** OBTIENE LOS MOVIMIENTOS
			if(l.size() <= 0)
				throw new ConstanciaPagoException("muestra");
			/**DESCAJ ifigueroa  09/01/2007**/
			String accion = request.getParameter("accion");
			HashMap hmAbono=null;
				
			if(accion.equalsIgnoreCase("paginar"))
				hmAbono= (HashMap)ExpressoHttpSessionBean.getSession(request).getAttribute("hmAbonoSel");

			for(Iterator i = l.iterator();i.hasNext();)
			{
				mov = (DboVwMovimiento) i.next();
				movi = new MovimientoBean();
				movi.setAgencia(mov.getField(DboVwMovimiento.CAMPO_NOMBRE));
/*
							movi.setFecha(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(8, 10) + "/" +
												mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(5, 7) + "/" +
												mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(0, 4));
*/							
				/** JBUGARIN OBS SUNARP **/
				//ASI ESTABA
				//movi.setFecha(FechaUtil.toPaginado(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(0, 10));
				//MODIFICADO
				movi.setFecha(FechaUtil.stringmmddyyyytoddmmyyyy(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(0, 10));
				/** FIN **/
//							movi.setHora(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(11, 19));
				movi.setHora(FechaUtil.toPaginado(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(11, 19));
				
				String monto_formateado = Tarea.formatoNumero(mov.getField(DboVwMovimiento.CAMPO_MONTO));
				movi.setMonto(monto_formateado);
				movi.setNumAbono(mov.getField(DboVwMovimiento.CAMPO_ABONO_ID));
				
				
				
				
				//movi.setTipo(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase("A")?"Ampliacion":"Deposito");
				if(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase(Constantes.ABONO_CONCEPTO_AMPLIACION_CUENTA))
				{
					movi.setTipo("Ampliacion");
				}
				else if(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase(Constantes.ABONO_CONCEPTO_DEPOSITO_APERTURA))
				{
					movi.setTipo("Deposito");
				}
				else
				{
					movi.setTipo("Publicidad Certificada");
				}
			
				if(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).trim().length() == 1)
					movi.setFormaPago(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).equalsIgnoreCase("E")?"EFECTIVO":"CHEQUE");
				else
					movi.setFormaPago(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT));
				
				movi.setCajeroId(mov.getField(DboVwMovimiento.CAMPO_USR_CAJA));
				
				if(hmAbono!=null && hmAbono.containsKey(movi.getNumAbono()))
					movi.setSeleccionado(INDICA_SELECCIONADO);
				else
					movi.setSeleccionado(INDICA_NO_SELECCIONADO);
				movi.setNumZona(mov.getField(DboVwMovimiento.CAMPO_REG_PUB_ID));
					
				lista.add(movi);
			}
			/**DESCAJ ifigueroa  09/01/2007**/
			List listaTemporal=null;
			
			if (accion.equalsIgnoreCase("buscar")){
				ExpressoHttpSessionBean.getSession(request).removeAttribute("hmAbonoSel");
			}else if(accion.equalsIgnoreCase("paginar")){
				String[] abono = ExpressoHttpSessionBean.getRequest(request).getParameterValues("chkAbono");
				listaTemporal=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("listaAbono");
			
				if(listaTemporal!=null&&hmAbono!=null&&!hmAbono.isEmpty()){
					for(int i=0;i<listaTemporal.size();i++){
						MovimientoBean movTemp=(MovimientoBean)listaTemporal.get(i);
						if(hmAbono.containsKey(movTemp.getNumAbono()))
							hmAbono.remove(movTemp.getNumAbono());
					}
				}
				if(abono!=null&&abono.length>0){
					if (listaTemporal!=null){ 
						if(hmAbono==null){						
							hmAbono = new HashMap();						
						}
						for(int i=0;i<abono.length;i++){
							MovimientoBean movBean=(MovimientoBean)listaTemporal.get(Integer.parseInt(abono[i]));
							if(!hmAbono.containsKey(movBean.getNumAbono()))
								hmAbono.put(movBean.getNumAbono(),movBean);
						}
					}
				}
			}
			if(hmAbono!=null)
			ExpressoHttpSessionBean.getSession(request).setAttribute("hmAbonoSel",hmAbono);
			ExpressoHttpSessionBean.getSession(request).setAttribute("listaAbono",lista);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_INDIVIDUAL);
			/**DESCAJ ifigueroa  09/01/2007**/

			ExpressoHttpSessionBean.getRequest(request).setAttribute("fecini", feci.toString());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfin", fecf.toString());
			ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", "Usuario: " + usuarioId);

			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", null);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", "X");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador2", null);
			ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMovimiento", lista);
						
						
			// ****************** P A R A   L A    S E L E C C I O N    D E L    U S U A R I O *************//
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));

			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
						
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
						
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));

			response.setStyle("muestra");
		} catch (ConstanciaPagoException cpe) {
			ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", "X");			
			
			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));

			response.setStyle(cpe.getForward());
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

	protected ControllerResponse runMuestraMovimientosState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		
		try{
			//java.util.StringTokenizer param = new java.util.StringTokenizer(request.getParameter("token"), "|");
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			muestraOficinas(request,dconn);
			
			String linea = request.getParameter("lineaPrepago");
			String fechaInicio = FechaUtil.stringToOracleString(request.getParameter("fecinig"));
			String fechaFin = FechaUtil.stringToOracleString(request.getParameter("fecfing"));
			String tipopago = request.getParameter("tipog");
			String regpubid = request.getParameter("regId");
			String oficregid = request.getParameter("oficId");
			
			DboVwMovimiento vista = new DboVwMovimiento(dconn);
			vista.setField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID, linea);
					
			if(!regpubid.equalsIgnoreCase("T"))
			{
				vista.setField(DboVwMovimiento.CAMPO_REG_PUB_ID, regpubid);
				vista.setField(DboVwMovimiento.CAMPO_OFIC_REG_ID, oficregid);
			}
						
			if(!tipopago.equals("X"))
				vista.setField(DboVwMovimiento.CAMPO_TIPO_VENT, tipopago);

			//vista.setField(DboVwMovimiento.CAMPO_FEC_HOR, " BETWEEN " + fechaInicio + " AND " + fechaFin + " ");
			vista.setAppendWhereClause(DboVwMovimiento.CAMPO_FEC_HOR + " BETWEEN " + fechaInicio + " AND " + fechaFin + " ");//cb
			
			DboVwMovimiento mov = null;
			AbonoVentanillaBean nuevobean = new AbonoVentanillaBean();
					
			MovimientoBean movi = null;
			int pagActual=1;
			vista.setMaxRecords(10);		
			java.util.List lista = new java.util.ArrayList();
					
			java.util.List l = vista.searchAndRetrieveListPaginado(pagActual);
				
			if(l.size() > 0)
			{
				for(Iterator i = l.iterator();i.hasNext();)
				{
					mov = (DboVwMovimiento) i.next();
					movi = new MovimientoBean();
					movi.setAgencia(mov.getField(DboVwMovimiento.CAMPO_NOMBRE));
					
					/****DESCAJ 12/*01/2007 ifigueroa inicio****/
				//movi.setFecha(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(0, 10));
				/*			movi.setFecha(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(8, 10) + "/" +
												mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(5, 7) + "/" +
												mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(0, 4));
					
					movi.setHora(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(11, 19));
				*/						
						movi.setFecha(FechaUtil.toPaginado(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(0, 10));
//										movi.setHora(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR).substring(11, 19));
						movi.setHora(FechaUtil.toPaginado(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(11, 19));
					
						
						
						/****DESCAJ 12/*01/2007 ifigueroa fin****/	
					
				
					//formatear el monto a 2 decimales
					String monto_formateado = Tarea.formatoNumero(mov.getField(DboVwMovimiento.CAMPO_MONTO));
					movi.setMonto(monto_formateado);
					movi.setNumAbono(mov.getField(DboVwMovimiento.CAMPO_ABONO_ID));
					//movi.setTipo(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase("A")?"Ampliacion":"Deposito");
					if(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase("A"))
					{
						movi.setTipo("Ampliacion");
					}
					else if(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase("D"))
					{
						movi.setTipo("Deposito");
					}
					else
					{
						movi.setTipo("Publicidad Certificada");
					}
					
					if(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).trim().length() == 1)
						movi.setFormaPago(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).equalsIgnoreCase("E")?"EFECTIVO":"CHEQUE");
					else
						movi.setFormaPago(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT));
					/** DESCAJ IFIGUEROA 16/01/2006  inicio**/
					movi.setSeleccionado(INDICA_NO_SELECCIONADO);
					/** DESCAJ IFIGUEROA 16/01/2006  fin**/
					/** DESCAJ IFIGUEROA 04/04/2006  inicio**/
					movi.setNumZona(mov.getField(DboVwMovimiento.CAMPO_REG_PUB_ID));
					/** DESCAJ IFIGUEROA 04/04/2006  fin**/
					movi.setCajeroId(mov.getField(DboVwMovimiento.CAMPO_USR_CAJA));
					lista.add(movi);
				}
				if(lista.size()>=10){
					ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(pagActual + 1));
				}
				ExpressoHttpSessionBean.getSession(request).setAttribute("listaAbono",lista);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", null);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", "X");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador2", null);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMovimiento", lista);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("lineaPrepago", linea);
			
				
				if(request.getParameter("varios")!=null){
					ExpressoHttpSessionBean.getRequest(request).setAttribute("fecini", request.getParameter("fecinig"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfin", request.getParameter("fecfing"));
				}else{
					ExpressoHttpSessionBean.getRequest(request).setAttribute("fecini", request.getParameter("fecinig"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfin", request.getParameter("fecfing"));
				}
				
				if(request.getParameter("EE")!=null){
					ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", "RAZON SOCIAL: " + request.getParameter("EE"));
				}

				if(request.getParameter("LL")!=null){
					
					DboLineaPrepago pp = new DboLineaPrepago(dconn);
					pp.setFieldsToRetrieve(DboLineaPrepago.CAMPO_PE_JURI_ID);
					pp.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, linea);
					pp.find();
					
					DboPeJuri p = new DboPeJuri(dconn);
					p.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
					p.setField(DboPeJuri.CAMPO_PE_JURI_ID, pp.getField(DboLineaPrepago.CAMPO_PE_JURI_ID));
					p.find();
					
					ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", "RAZON SOCIAL: " + p.getField(DboPeJuri.CAMPO_RAZ_SOC));
				}
			}else{
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", "X");
			}

			ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("di2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mi2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("ai2"));
	
			ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("df2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mf2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("af2"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tp2"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("ag2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("user2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("rad2"));
							
			ExpressoHttpSessionBean.getRequest(request).setAttribute("rs1", request.getParameter("razsoc2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc1", request.getParameter("ruc2"));
			ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_ORGANIZACION);

			response.setStyle("muestra");
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
	
	protected ControllerResponse runExportaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		try{
			init(request);
			validarSesion(request);
		
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);			
			muestraOficinas(request,dconn);
			
			String usuarioId = request.getParameter("userId");
			String origenConstancia = request.getParameter("origenConstancia");
			
			UsuarioBean userBean = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
			
			if (usuarioId == null || usuarioId.trim().length() <= 0)
				return response;
			
			DboCuenta cuenta = new DboCuenta(dconn);
			cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
			cuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);

			if (cuenta.find())
			{

				String cuentaId = cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
				
				AbonoVentanillaBean bean = new AbonoVentanillaBean();
				
				MultiDBObject multi = new MultiDBObject(dconn);
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
	
				multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
				multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
				multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
				multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
				multi.setField("cuenta", DboCuenta.CAMPO_CUENTA_ID, cuentaId);
				multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");
				multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO,"1");
				
				java.util.Vector elementos = multi.searchAndRetrieve();
				
				if(elementos.size() == 1)
				{
					MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
					bean.setAfil_organiz(null);

					// La LineaPrepago			
					String linea = oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
					
					//**************DEBERIA UTILIZAR EL OTRO ESTADO*******************************
					
					StringBuffer feci = new StringBuffer(request.getParameter("diainicio"));
					feci.append("/").append(request.getParameter("mesinicio")).append("/").append(request.getParameter("anoinicio"));
					String fechaInicio = FechaUtil.stringToOracleString(feci.toString());
					
					StringBuffer fecf = new StringBuffer(request.getParameter("diafin"));
					fecf.append("/").append(request.getParameter("mesfin")).append("/").append(request.getParameter("anofin"));
					String fechaFin = FechaUtil.stringToOracleString(fecf.toString());
		
					String tipopago = request.getParameter("tipopago");

					java.util.StringTokenizer tok = new java.util.StringTokenizer(request.getParameter("agencia"), "|");
					
					String regpubid = tok.nextToken();
					String oficregid = null;
					
					if(!regpubid.equalsIgnoreCase("T"))
						oficregid = tok.nextToken();
		
					DboVwMovimiento vista = new DboVwMovimiento(dconn);
					vista.setField(DboVwMovimiento.CAMPO_LINEA_PREPAGO_ID, linea);
							
					if(!regpubid.equalsIgnoreCase("T"))
					{
						vista.setField(DboVwMovimiento.CAMPO_REG_PUB_ID, regpubid);
						vista.setField(DboVwMovimiento.CAMPO_OFIC_REG_ID, oficregid);
					}
						
					if(!tipopago.equals("X"))
						vista.setField(DboVwMovimiento.CAMPO_TIPO_VENT, tipopago);
					
					//vista.setField(DboVwMovimiento.CAMPO_FEC_HOR, " BETWEEN " + fechaInicio + " AND " + fechaFin + " ");cb
					vista.setAppendWhereClause(DboVwMovimiento.CAMPO_FEC_HOR + " BETWEEN " + fechaInicio + " AND " + fechaFin + " ");//cb
					
					DboVwMovimiento mov = null;
					AbonoVentanillaBean nuevobean = new AbonoVentanillaBean();
							
					MovimientoBean movi = null;
							
					java.util.List lista = new java.util.ArrayList();
					
					java.util.List l = vista.searchAndRetrieveList();
					
					//** OBTIENE LOS MOVIMIENTOS
					String fname = "constancia.csv";
					HttpServletResponse res = ExpressoHttpSessionBean.getResponse(request);
					res.setContentType("archivo/xxx");
					res.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");
					PrintWriter out_cliente = null;
					
					StringBuffer stb = null;
					
					if(l.size() > 0)
					{
						out_cliente = res.getWriter();
						
						stb = new StringBuffer();
						
						//3 líneas iniciales
						stb.delete(0,stb.length());
						stb.append("Reporte Constancia de Pago desde ").append(feci).append(" hasta ").append(fecf);
						out_cliente.println(stb.toString());

						stb.delete(0,stb.length());
						stb.append("Emitido por ").append(userBean.getUserId()).append(" el ").append(FechaUtil.getCurrentDateTime());
						out_cliente.println(stb.toString());						
						
						out_cliente.println(origenConstancia);
						
						
						for(Iterator i = l.iterator();i.hasNext();)
						{
							mov = (DboVwMovimiento) i.next();
							
							stb.delete(0,stb.length());
							
							stb.append(mov.getField(DboVwMovimiento.CAMPO_ABONO_ID)).append(",");
							stb.append(FechaUtil.toPaginado(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(0, 10)).append(",");
							stb.append(FechaUtil.toPaginado(mov.getField(DboVwMovimiento.CAMPO_FEC_HOR)).substring(11, 16)).append(",");
							//stb.append(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase("A")?"Ampliacion":"Deposito").append(",");
							if(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase(Constantes.ABONO_CONCEPTO_AMPLIACION_CUENTA))
							{
								stb.append("Ampliacion");
							}
							else if(mov.getField(DboVwMovimiento.CAMPO_TIPO_VENT).equalsIgnoreCase(Constantes.ABONO_CONCEPTO_DEPOSITO_APERTURA))
							{
								stb.append("Deposito");
							}
							else
							{
								stb.append("Publicidad Certificada");
							}
			
							stb.append(mov.getField(DboVwMovimiento.CAMPO_MONTO)).append(",");
							stb.append(mov.getField(DboVwMovimiento.CAMPO_NOMBRE)).append(",");
							
							if(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).trim().length() == 1)
								stb.append(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT).equalsIgnoreCase("E")?"EFECTIVO":"CHEQUE").append(",");
							else
								stb.append(mov.getField(DboVwMovimiento.CAMPO_TPO_PAG_VENT));
								
							
							out_cliente.println(stb.toString());
						}
						//transition("usuarioDirecta", request, response);
						out_cliente.flush();
						out_cliente.close();
						
						
						/*
						
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fecini", feci.toString());
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfin", fecf.toString());
						ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", "Usuario: " + usuarioId);

						ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", null);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", "X");
						ExpressoHttpSessionBean.getRequest(request).setAttribute("contador2", null);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMovimiento", lista);
						
						
						// ****************** P A R A   L A    S E L E C C I O N    D E L    U S U A R I O *************
						/*	ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
							ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));
						*/	
					}else{
						ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", "X");
						// ****************** P A R A   L A    S E L E C C I O N    D E L    U S U A R I O *************//
							ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
							ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));
							response.setStyle("muestra");
					}
				}else{
					ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", "X");
						// ****************** P A R A   L A    S E L E C C I O N    D E L    U S U A R I O *************//
							ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
							ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));
							response.setStyle("muestra");
				}
			}else{
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador0", "X");
						// ****************** P A R A   L A    S E L E C C I O N    D E L    U S U A R I O *************//
							ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
							ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
							
							ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
							ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));
							response.setStyle("muestra");
			}
			
			//**************DEBERIA UTILIZAR EL OTRO ESTADO*******************************
			
		} catch (CustomException e) 
		{
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		}catch(DBException dbe)
		{
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex)
		{
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			rollback(conn, request);
			response.setStyle("error");
		}finally
		{
			pool.release(conn);
			end(request);
		}
		response.setCustomResponse(true);
		return response;
	}
	
	/**DESCAJ ifigueroa  09/01/2007**/
	
	protected ControllerResponse runCertificadoGlobalState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;

			try{
				init(request);
				validarSesion(request);
		
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);			
				muestraOficinas(request,dconn);
			
				String usuarioId = ExpressoHttpSessionBean.getRequest(request).getParameter("userId");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("userId",usuarioId);
				String accion = request.getParameter("accion");
				
				String[] abono = ExpressoHttpSessionBean.getRequest(request).getParameterValues("chkAbono");
							
				UsuarioBean userBean = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
							
				if (usuarioId == null || usuarioId.trim().length() <= 0)
					return response;
			
				DboCuenta cuenta = new DboCuenta(dconn);
				cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
				cuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);

				if (cuenta.find())
				{

					String cuentaId = cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
				
					AbonoVentanillaBean bean = new AbonoVentanillaBean();
				
					MultiDBObject multi = new MultiDBObject(dconn);
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
	
					multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
					multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
					multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
					multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
					multi.setField("cuenta", DboCuenta.CAMPO_CUENTA_ID, cuentaId);
					multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");
					multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO,"1");
				
					java.util.Vector elementos = multi.searchAndRetrieve();
				
					if(elementos.size() == 1)
					{
						MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
						bean.setAfil_organiz(null);

						// La LineaPrepago			
						String linea = oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
						DatosUsuarioBean usuarioBean=new DatosUsuarioBean();
						usuarioBean.setNombres(oneMulti.getField("penatu",DboPeNatu.CAMPO_NOMBRES)+" "+oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_PAT)+" "+oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_MAT));
						usuarioBean.setApellidoPaterno(oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_PAT));
						usuarioBean.setApellidoMaterno(oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_MAT));
						usuarioBean.setNumDocumento(oneMulti.getField("persona",DboPersona.CAMPO_NUM_DOC_IDEN));
						usuarioBean.setTipoDocumento(oneMulti.getField("tipodoc",DboTmDocIden.CAMPO_NOMBRE_ABREV));
						usuarioBean.setTipoDocumento(oneMulti.getField("tipodoc",DboTmDocIden.CAMPO_NOMBRE_ABREV));
						
						ExpressoHttpSessionBean.getRequest(request).setAttribute("usuarioCer",usuarioBean);
						
						DboRegisPublico dboRegis= new DboRegisPublico();
						List listaTemporal=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("listaAbono");
						HashMap hmAbono= (HashMap)ExpressoHttpSessionBean.getSession(request).getAttribute("hmAbonoSel");
						
						List lstCertificados=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("lstCertificdo");
						List lstZonaMonto=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("movZonaMonto");
						/**copiar
						if(listaTemporal!=null&&hmAbono!=null&&!hmAbono.isEmpty()){
							for(int i=0;i<listaTemporal.size();i++){
								MovimientoBean movTemp=(MovimientoBean)listaTemporal.get(i);
								if(hmAbono.containsKey(movTemp.getNumAbono()))
									hmAbono.remove(movTemp.getNumAbono());
							}
						}
						/** * */
						if(lstCertificados==null&&lstZonaMonto==null){
						
							if(abono!=null&&abono.length>0){
								if (listaTemporal!=null){ 
									if(hmAbono==null){						
										hmAbono = new HashMap();						
									}
									for(int i=0;i<abono.length;i++){
										MovimientoBean movBean=(MovimientoBean)listaTemporal.get(Integer.parseInt(abono[i]));
										if(!hmAbono.containsKey(movBean.getNumAbono()))
											hmAbono.put(movBean.getNumAbono(),movBean);
									}
								}
							}
							if(hmAbono!=null)
								ExpressoHttpSessionBean.getSession(request).setAttribute("hmAbonoSel",hmAbono);

							Iterator i=hmAbono.keySet().iterator();
							double montoTotal=0;
							List lstTemporal= new ArrayList();
							HashMap hmCertGlobal=new HashMap();
							HashMap hmMovTemp=new HashMap();
							while(i.hasNext()){
								MovimientoBean mov=(MovimientoBean)hmAbono.get(i.next());
								DboComprobante dboComp= new DboComprobante();
								dboComp.setConnection(dconn);
								dboComp.setField(DboComprobante.CAMPO_ABONO_ID,mov.getNumAbono());
								dboComp.find();
								mov.setComprobanteId(dboComp.getField(DboComprobante.CAMPO_COMPROBANTE_ID));
														
								if(!hmCertGlobal.isEmpty()&&hmCertGlobal.containsKey(mov.getNumZona())){
									lstTemporal=(ArrayList)hmCertGlobal.get(mov.getNumZona());
									hmCertGlobal.remove(mov.getNumZona());
									lstTemporal.add(mov);
									hmCertGlobal.put(mov.getNumZona(),lstTemporal);
									MovimientoBean movTemp=(MovimientoBean)hmMovTemp.get(mov.getNumZona());
									hmMovTemp.remove(mov.getNumZona());
									double monto=dboComp.getFieldFloat(DboComprobante.CAMPO_MONTO);
									movTemp.setDmonto(movTemp.getDmonto()+monto);
									hmMovTemp.put(mov.getNumZona(),movTemp);
								}else{
									List lstabono= new ArrayList();
									lstabono.add(mov);
									hmCertGlobal.put(mov.getNumZona(),lstabono);
									MovimientoBean movTemp=new MovimientoBean();
									double monto=dboComp.getFieldFloat(DboComprobante.CAMPO_MONTO);
									movTemp.setDmonto(movTemp.getDmonto()+monto);
									dboRegis.setConnection(dconn);
									dboRegis.setField(DboRegisPublico.CAMPO_REG_PUB_ID,mov.getNumZona());
									dboRegis.find();
									movTemp.setNombreZona(dboRegis.getField(DboRegisPublico.CAMPO_NOMBRE));
									movTemp.setNumConstancia(SecuenciaZona.getSecuenciaZona(mov.getNumZona(),dconn));
									hmMovTemp.put(mov.getNumZona(),movTemp);
									dboRegis.clear();
								}
															
							}
							 lstCertificados=new ArrayList();
							 lstZonaMonto=new ArrayList();
							if(!hmCertGlobal.isEmpty()){
								Iterator it=hmCertGlobal.keySet().iterator();
								while(it.hasNext()){
									String striter=(String)it.next();
									List lst=(List)hmCertGlobal.get(striter);
									lstCertificados.add(lst);
									MovimientoBean movTempo=(MovimientoBean)hmMovTemp.get(striter);
									lstZonaMonto.add(movTempo);
								}
								
							}
						}
						/*****/
						String strPagina = ExpressoHttpSessionBean.getRequest(request).getParameter("pagina");
						int pagActual=0;
						if(strPagina != null&&strPagina.length()>0){
							pagActual = Integer.parseInt(strPagina);
						}
						
						
						if(pagActual>0 )
							ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(pagActual - 1));
						if(pagActual+1<lstCertificados.size()){
							ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(pagActual + 1));
						}
						MovimientoBean montoZona=(MovimientoBean)lstZonaMonto.get(pagActual);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("montoTotal",Tarea.formatoNumero(montoZona.getDmonto()));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("lstAbono",lstCertificados.get(pagActual));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("montoZona",montoZona);
						ExpressoHttpSessionBean.getSession(request).setAttribute("lstCertificdo",lstCertificados);
						ExpressoHttpSessionBean.getSession(request).setAttribute("movZonaMonto",lstZonaMonto);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_INDIVIDUAL);
						
						Locale local= new Locale("es","ES");
						java.util.Date hoy= new java.util.Date();
						SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
						String fecha="";
						String oficReg=userBean.getOficRegistralId();
						String regPubId=userBean.getRegPublicoId();
	
						if(oficReg!=null&regPubId!=null&regPubId.length()>0&&oficReg.length()>0){
							DboOficRegistral oficRegistral= new DboOficRegistral();
							oficRegistral.setConnection(dconn);
							oficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficReg);
							oficRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,regPubId);
							oficRegistral.find();
							fecha= oficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE)+", ";
	
						}
	//						
						fecha= fecha+sd.format(hoy);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
						/***  para regresar **/
						ExpressoHttpSessionBean.getRequest(request).setAttribute("nextRegresar", request.getParameter("nextRegresar"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("prevRegresar", request.getParameter("prevRegresar"));
	
						ExpressoHttpSessionBean.getRequest(request).setAttribute("diainicio", request.getParameter("diainicio"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("mesinicio", request.getParameter("mesinicio"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("anoinicio", request.getParameter("anoinicio"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("diafin", request.getParameter("diafin"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("mesfin", request.getParameter("mesfin"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("anofin", request.getParameter("anofin"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("tipopago", request.getParameter("tipopago"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("agencia", request.getParameter("agencia"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("userId", request.getParameter("userId"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("radio", request.getParameter("radio"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc", request.getParameter("ruc"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("razsoc", request.getParameter("razsoc"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("entidadRegresa", request.getParameter("entidadRegresa"));	


						/****/	
		
				}
				}
				conn.commit();		
				response.setStyle("global");
				
				
			}catch (CustomException e) 
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe)
			{
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex)
			{
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally
			{
				pool.release(conn);
				end(request);
			}
			
			return response;
		}
		
	
	protected ControllerResponse runCertificadoIndividualState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;

			try{
				init(request);
				validarSesion(request);
		
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);			
			//	muestraOficinas(request,dconn);
			
				String usuarioId = ExpressoHttpSessionBean.getRequest(request).getParameter("userId");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("userId",usuarioId);
				String strPagina = ExpressoHttpSessionBean.getRequest(request).getParameter("pagina");
				int pagActual=0;
				
				String[] abono = ExpressoHttpSessionBean.getRequest(request).getParameterValues("chkAbono");
							
				UsuarioBean userBean = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
				
							
				if (usuarioId == null || usuarioId.trim().length() <= 0)
					return response;
			
				DboCuenta cuenta = new DboCuenta(dconn);
				cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
				cuenta.setField(DboCuenta.CAMPO_USR_ID, usuarioId);

				if (cuenta.find())
				{

					String cuentaId = cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
				
					AbonoVentanillaBean bean = new AbonoVentanillaBean();
				
					MultiDBObject multi = new MultiDBObject(dconn);
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
	
					multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
					multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
					multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
					multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
					multi.setField("cuenta", DboCuenta.CAMPO_CUENTA_ID, cuentaId);
					multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");
					multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO,"1");
				
					java.util.Vector elementos = multi.searchAndRetrieve();
				
					if(elementos.size() == 1)
					{
						MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
						bean.setAfil_organiz(null);

						// La LineaPrepago			
						String linea = oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
						DatosUsuarioBean usuarioBean=new DatosUsuarioBean();
						usuarioBean.setNombres(oneMulti.getField("penatu",DboPeNatu.CAMPO_NOMBRES)+" "+oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_PAT)+" "+oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_MAT));
						usuarioBean.setApellidoPaterno(oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_PAT));
						usuarioBean.setApellidoMaterno(oneMulti.getField("penatu",DboPeNatu.CAMPO_APE_MAT));
						usuarioBean.setNumDocumento(oneMulti.getField("persona",DboPersona.CAMPO_NUM_DOC_IDEN));
						usuarioBean.setTipoDocumento(oneMulti.getField("tipodoc",DboTmDocIden.CAMPO_NOMBRE_ABREV));
						usuarioBean.setTipoDocumento(oneMulti.getField("tipodoc",DboTmDocIden.CAMPO_NOMBRE_ABREV));
						
						ExpressoHttpSessionBean.getRequest(request).setAttribute("usuarioCer",usuarioBean);
						
						DboRegisPublico dboRegis= new DboRegisPublico();
						List lstListaIndividual=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("lstAbonoIndividual");
						
						if(lstListaIndividual==null){
						
							List listaTemporal=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("listaAbono");
							HashMap hmAbono= (HashMap)ExpressoHttpSessionBean.getSession(request).getAttribute("hmAbonoSel");
							/**copiar*/
							if(listaTemporal!=null&&hmAbono!=null&&!hmAbono.isEmpty()){
								for(int i=0;i<listaTemporal.size();i++){
									MovimientoBean movTemp=(MovimientoBean)listaTemporal.get(i);
									if(hmAbono.containsKey(movTemp.getNumAbono())){
										MovimientoBean obj = (MovimientoBean)hmAbono.remove(movTemp.getNumAbono());
										System.out.println(obj);;
									}
								}
							}
							/** * */
							if(abono!=null&&abono.length>0){
								if (listaTemporal!=null){ 
									if(hmAbono==null){						
										hmAbono = new HashMap();						
									}
									for(int i=0;i<abono.length;i++){
										MovimientoBean movBean=(MovimientoBean)listaTemporal.get(Integer.parseInt(abono[i]));
										if(!hmAbono.containsKey(movBean.getNumAbono()))
											hmAbono.put(movBean.getNumAbono(),movBean);
									}
								}
							}
							Iterator i=hmAbono.keySet().iterator();
							
							lstListaIndividual= new ArrayList();
							while(i.hasNext()){
								MovimientoBean mov=(MovimientoBean)hmAbono.get(i.next());
								DboComprobante dboComp= new DboComprobante();
								dboComp.setConnection(dconn);
								dboComp.setField(DboComprobante.CAMPO_ABONO_ID,mov.getNumAbono());
								dboComp.find();
								mov.setComprobanteId(dboComp.getField(DboComprobante.CAMPO_COMPROBANTE_ID));
								mov.setNumConstancia(SecuenciaZona.getSecuenciaZona(mov.getNumZona(),dconn));							
								lstListaIndividual.add(mov);
								
							}
							if(hmAbono!=null)
								ExpressoHttpSessionBean.getSession(request).setAttribute("hmAbonoSel",hmAbono);

						}
						if(strPagina != null&&strPagina.length()>0){
							pagActual = Integer.parseInt(strPagina);
						}
						if(pagActual>0 )
							ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(pagActual - 1));
						if(pagActual+1<lstListaIndividual.size()){
							ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(pagActual + 1));
						}
						
									
						MovimientoBean movActual=(MovimientoBean)lstListaIndividual.get(pagActual);
						dboRegis.setConnection(dconn);
						dboRegis.setField(DboRegisPublico.CAMPO_REG_PUB_ID,movActual.getNumZona());
						dboRegis.find();
						String montoFormateado = movActual.getMonto();
						ExpressoHttpSessionBean.getRequest(request).setAttribute("zonaRegistral",dboRegis.getField(DboRegisPublico.CAMPO_NOMBRE));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("montoTotal",montoFormateado);
						ExpressoHttpSessionBean.getSession(request).setAttribute("lstAbonoIndividual",lstListaIndividual);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("abonoActual",movActual);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_INDIVIDUAL);
						
						Locale local= new Locale("es","ES");
						java.util.Date hoy= new java.util.Date();
						SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
						String fecha="";
						String oficReg=userBean.getOficRegistralId();
						String regPubId=userBean.getRegPublicoId();
	
						if(oficReg!=null&regPubId!=null&regPubId.length()>0&&oficReg.length()>0){
							DboOficRegistral oficRegistral= new DboOficRegistral();
							oficRegistral.setConnection(dconn);
							oficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficReg);
							oficRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,regPubId);
							oficRegistral.find();
							fecha= oficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE)+", ";
	
						}
//						
						fecha= fecha+sd.format(hoy);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
						/***  para regresar **/
						ExpressoHttpSessionBean.getRequest(request).setAttribute("nextRegresar", request.getParameter("nextRegresar"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("prevRegresar", request.getParameter("prevRegresar"));
	
						ExpressoHttpSessionBean.getRequest(request).setAttribute("diainicio", request.getParameter("diainicio"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("mesinicio", request.getParameter("mesinicio"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("anoinicio", request.getParameter("anoinicio"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("diafin", request.getParameter("diafin"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("mesfin", request.getParameter("mesfin"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("anofin", request.getParameter("anofin"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("tipopago", request.getParameter("tipopago"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("agencia", request.getParameter("agencia"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("userId", request.getParameter("userId"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("radio", request.getParameter("radio"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc", request.getParameter("ruc"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("razsoc", request.getParameter("razsoc"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("entidadRegresa", request.getParameter("entidadRegresa"));	


						/****/	
	
			
				}
				}
					conn.commit();	
					response.setStyle("individual");
				
			}catch (CustomException e) 
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe)
			{
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex)
			{
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally
			{
				pool.release(conn);
				end(request);
			}
			
			return response;
		}
		
	protected ControllerResponse runCertificadoGlobalOrgState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;

			try{
				init(request);
				validarSesion(request);
		
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);	
				String razsoc = request.getParameter("razsoc");
				String ruc = request.getParameter("ruc");
				boolean paseRuc = true;
				boolean paseRS = true;
				if (razsoc != null && razsoc.trim().length() >0)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("razsoc",razsoc);
				else
					paseRS = false;				

				if(ruc != null && ruc.trim().length() > 0)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc",ruc);
				else
					paseRuc = false;		
			
				MultiDBObject multi = new MultiDBObject(dconn);
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
				String linea=(String)ExpressoHttpSessionBean.getRequest(request).getParameter("lineaPrepago");
				if(linea!=null && linea.length()>0){
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
					multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_PE_JURI_ID, "pejuri", DboPeJuri.CAMPO_PE_JURI_ID);
					multi.setField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, linea);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("lineaPrepago",linea);
				}
				multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
				multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
				multi.setField("pejuri", DboPeJuri.CAMPO_TIPO_ORG, "0");
			

				if(paseRuc)
				{
					multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, ruc);
					multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, Constantes.TIPO_DOCUMENTO_RUC);
				}
	
				if(paseRS)
				{
					multi.setField("pejuri", DboPeJuri.CAMPO_RAZ_SOC, razsoc);
				}
				

				String strPagina = ExpressoHttpSessionBean.getRequest(request).getParameter("pagina");
				int pagActual=0;
				
				String[] abono = ExpressoHttpSessionBean.getRequest(request).getParameterValues("chkAbono");
							
				UsuarioBean userBean = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
				java.util.Vector elementos = multi.searchAndRetrieve();
				
				if(elementos.size() == 1)
				{
					MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
					// La LineaPrepago			
					DatosUsuarioBean usuarioBean=new DatosUsuarioBean();
					usuarioBean.setNombres(oneMulti.getField("pejuri",DboPeJuri.CAMPO_RAZ_SOC));
					usuarioBean.setNumDocumento(oneMulti.getField("persona",DboPersona.CAMPO_NUM_DOC_IDEN));
					usuarioBean.setTipoDocumento(oneMulti.getField("tipodoc",DboTmDocIden.CAMPO_NOMBRE_ABREV));
									
					ExpressoHttpSessionBean.getRequest(request).setAttribute("usuarioCer",usuarioBean);
					DboRegisPublico dboRegis= new DboRegisPublico();
					List listaTemporal=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("listaAbono");
					HashMap hmAbono= (HashMap)ExpressoHttpSessionBean.getSession(request).getAttribute("hmAbonoSel");
					/**copiar
					if(listaTemporal!=null&&hmAbono!=null&&!hmAbono.isEmpty()){
						for(int i=0;i<listaTemporal.size();i++){
							MovimientoBean movTemp=(MovimientoBean)listaTemporal.get(i);
							if(hmAbono.containsKey(movTemp.getNumAbono()))
								hmAbono.remove(movTemp.getNumAbono());
						}
					}
					/** * */	
					List lstCertificados=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("lstCertificdo");
					List lstZonaMonto=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("movZonaMonto");
						
						
						if(lstCertificados==null&&lstZonaMonto==null){
						
							if(abono!=null&&abono.length>0){
								if (listaTemporal!=null){ 
									if(hmAbono==null){						
										hmAbono = new HashMap();						
									}
									for(int i=0;i<abono.length;i++){
										MovimientoBean movBean=(MovimientoBean)listaTemporal.get(Integer.parseInt(abono[i]));
										if(!hmAbono.containsKey(movBean.getNumAbono()))
											hmAbono.put(movBean.getNumAbono(),movBean);
									}
								}
							}
							Iterator i=hmAbono.keySet().iterator();
							double montoTotal=0;
							List lstTemporal= new ArrayList();
							HashMap hmCertGlobal=new HashMap();
							HashMap hmMovTemp=new HashMap();
							while(i.hasNext()){
								MovimientoBean mov=(MovimientoBean)hmAbono.get(i.next());
								DboComprobante dboComp= new DboComprobante();
								dboComp.setConnection(dconn);
								dboComp.setField(DboComprobante.CAMPO_ABONO_ID,mov.getNumAbono());
								dboComp.find();
								mov.setComprobanteId(dboComp.getField(DboComprobante.CAMPO_COMPROBANTE_ID));
														
								if(!hmCertGlobal.isEmpty()&&hmCertGlobal.containsKey(mov.getNumZona())){
									lstTemporal=(ArrayList)hmCertGlobal.get(mov.getNumZona());
									hmCertGlobal.remove(mov.getNumZona());
									lstTemporal.add(mov);
									hmCertGlobal.put(mov.getNumZona(),lstTemporal);
									MovimientoBean movTemp=(MovimientoBean)hmMovTemp.get(mov.getNumZona());
									hmMovTemp.remove(mov.getNumZona());
									double monto=dboComp.getFieldFloat(DboComprobante.CAMPO_MONTO);
									movTemp.setDmonto(movTemp.getDmonto()+monto);
									hmMovTemp.put(mov.getNumZona(),movTemp);
								}else{
									List lstabono= new ArrayList();
									lstabono.add(mov);
									hmCertGlobal.put(mov.getNumZona(),lstabono);
									MovimientoBean movTemp=new MovimientoBean();
									double monto=dboComp.getFieldFloat(DboComprobante.CAMPO_MONTO);
									movTemp.setDmonto(movTemp.getDmonto()+monto);
									dboRegis.setConnection(dconn);
									dboRegis.setField(DboRegisPublico.CAMPO_REG_PUB_ID,mov.getNumZona());
									dboRegis.find();
									movTemp.setNombreZona(dboRegis.getField(DboRegisPublico.CAMPO_NOMBRE));
									movTemp.setNumConstancia(SecuenciaZona.getSecuenciaZona(mov.getNumZona(),dconn));
									hmMovTemp.put(mov.getNumZona(),movTemp);
									dboRegis.clear();
								}
															
							}
							 lstCertificados=new ArrayList();
							 lstZonaMonto=new ArrayList();
							if(!hmCertGlobal.isEmpty()){
								Iterator it=hmCertGlobal.keySet().iterator();
								while(it.hasNext()){
									String striter=(String)it.next();
									List lst=(List)hmCertGlobal.get(striter);
									lstCertificados.add(lst);
									MovimientoBean movTempo=(MovimientoBean)hmMovTemp.get(striter);
									lstZonaMonto.add(movTempo);
								}
								
							}
							if(hmAbono!=null)
								ExpressoHttpSessionBean.getSession(request).setAttribute("hmAbonoSel",hmAbono);

						}
						/*****/
						
						if(strPagina != null&&strPagina.length()>0){
							pagActual = Integer.parseInt(strPagina);
						}
						
						
						if(pagActual>0 )
							ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(pagActual - 1));
						if(pagActual+1<lstCertificados.size()){
							ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(pagActual + 1));
						}
						MovimientoBean montoZona=(MovimientoBean)lstZonaMonto.get(pagActual);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("montoTotal",Tarea.formatoNumero(montoZona.getDmonto()));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("lstAbono",lstCertificados.get(pagActual));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("montoZona",montoZona);
						ExpressoHttpSessionBean.getSession(request).setAttribute("lstCertificdo",lstCertificados);
						ExpressoHttpSessionBean.getSession(request).setAttribute("movZonaMonto",lstZonaMonto);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_ORGANIZACION);
						Locale local= new Locale("es","ES");
						java.util.Date hoy= new java.util.Date();
						SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
						String fecha="";
						String oficReg=userBean.getOficRegistralId();
						String regPubId=userBean.getRegPublicoId();
	
						if(oficReg!=null&regPubId!=null&regPubId.length()>0&&oficReg.length()>0){
							DboOficRegistral oficRegistral= new DboOficRegistral();
							oficRegistral.setConnection(dconn);
							oficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficReg);
							oficRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,regPubId);
							oficRegistral.find();
							fecha= oficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE)+", ";
	
						}
	//						
						fecha= fecha+sd.format(hoy);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
					/***  para regresar **/
						System.out.println("/***/"+request.getParameter("nextRegresar"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("nextRegresar", request.getParameter("nextRegresar"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("prevRegresar", request.getParameter("prevRegresar"));
	
					
						ExpressoHttpSessionBean.getRequest(request).setAttribute("diainicio", request.getParameter("diainicio"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("mesinicio", request.getParameter("mesinicio"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("anoinicio", request.getParameter("anoinicio"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("diafin", request.getParameter("diafin"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("mesfin", request.getParameter("mesfin"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("anofin", request.getParameter("anofin"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("tipopago", request.getParameter("tipopago"));

						ExpressoHttpSessionBean.getRequest(request).setAttribute("agencia", request.getParameter("agencia"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("userId", request.getParameter("userId"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("radio", request.getParameter("radio"));
	
						ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc", request.getParameter("ruc"));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("razsoc", request.getParameter("razsoc"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("entidadRegresa", request.getParameter("entidadRegresa"));	
	

						/****/	
			
				}
				
				conn.commit();		
				response.setStyle("global");
				
				
			}catch (CustomException e) 
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe)
			{
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex)
			{
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally
			{
				pool.release(conn);
				end(request);
			}
			
			return response;
		}
		
	
	protected ControllerResponse runCertificadoIndividualOrgState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;

			try{
				init(request);
				validarSesion(request);
		
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);	
				String razsoc = request.getParameter("razsoc");
				String ruc = request.getParameter("ruc");
				boolean paseRuc = true;
				boolean paseRS = true;
				if (razsoc != null && razsoc.trim().length() >0)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("razsoc",razsoc);
				else
					paseRS = false;				

				if(ruc != null && ruc.trim().length() > 0)
					ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc",ruc);
				else
					paseRuc = false;		
			
				MultiDBObject multi = new MultiDBObject(dconn);
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
				multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
				String linea=(String)ExpressoHttpSessionBean.getRequest(request).getParameter("lineaPrepago");
				if(linea!=null && linea.length()>0){
					multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
					multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_PE_JURI_ID, "pejuri", DboPeJuri.CAMPO_PE_JURI_ID);
					multi.setField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID, linea);
					ExpressoHttpSessionBean.getRequest(request).setAttribute("lineaPrepago",linea);
				}
		
				multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
				multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
				multi.setField("pejuri", DboPeJuri.CAMPO_TIPO_ORG, "0");
			

				if(paseRuc)
				{
					multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, ruc);
					multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, Constantes.TIPO_DOCUMENTO_RUC);
				}
	
				if(paseRS)
				{
					multi.setField("pejuri", DboPeJuri.CAMPO_RAZ_SOC, razsoc);
				}
				

				String strPagina = ExpressoHttpSessionBean.getRequest(request).getParameter("pagina");
				int pagActual=0;
				
				String[] abono = ExpressoHttpSessionBean.getRequest(request).getParameterValues("chkAbono");
							
				UsuarioBean userBean = (UsuarioBean) ExpressoHttpSessionBean.getSession(request).getAttribute("Usuario");
				java.util.Vector elementos = multi.searchAndRetrieve();
				
				if(elementos.size() == 1)
				{
					MultiDBObject oneMulti = (MultiDBObject) elementos.get(0);
					// La LineaPrepago			
					DatosUsuarioBean usuarioBean=new DatosUsuarioBean();
					usuarioBean.setNombres(oneMulti.getField("pejuri",DboPeJuri.CAMPO_RAZ_SOC));
					usuarioBean.setNumDocumento(oneMulti.getField("persona",DboPersona.CAMPO_NUM_DOC_IDEN));
					usuarioBean.setTipoDocumento(oneMulti.getField("tipodoc",DboTmDocIden.CAMPO_NOMBRE_ABREV));
									
					ExpressoHttpSessionBean.getRequest(request).setAttribute("usuarioCer",usuarioBean);
					
					DboRegisPublico dboRegis= new DboRegisPublico();
					List lstListaIndividual=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("lstAbonoIndividual");
						
					if(lstListaIndividual==null){
						
						List listaTemporal=(List)ExpressoHttpSessionBean.getSession(request).getAttribute("listaAbono");
						HashMap hmAbono= (HashMap)ExpressoHttpSessionBean.getSession(request).getAttribute("hmAbonoSel");
						/**copiar*/
						if(listaTemporal!=null&&hmAbono!=null&&!hmAbono.isEmpty()){
							for(int i=0;i<listaTemporal.size();i++){
								MovimientoBean movTemp=(MovimientoBean)listaTemporal.get(i);
								if(hmAbono.containsKey(movTemp.getNumAbono())){
									hmAbono.remove(movTemp.getNumAbono());
								}
							}
						}
						/** * */
						if(abono!=null&&abono.length>0){
							if (listaTemporal!=null){ 
								if(hmAbono==null){						
									hmAbono = new HashMap();						
								}
								for(int i=0;i<abono.length;i++){
									System.out.println("abono "+abono[i]);
									MovimientoBean movBean=(MovimientoBean)listaTemporal.get(Integer.parseInt(abono[i]));
									if(!hmAbono.containsKey(movBean.getNumAbono()))
										hmAbono.put(movBean.getNumAbono(),movBean);
								}
							}
						}
						Iterator i=hmAbono.keySet().iterator();
							
						lstListaIndividual= new ArrayList();
						while(i.hasNext()){
							MovimientoBean mov=(MovimientoBean)hmAbono.get(i.next());
							DboComprobante dboComp= new DboComprobante();
							dboComp.setConnection(dconn);
							dboComp.setField(DboComprobante.CAMPO_ABONO_ID,mov.getNumAbono());
							dboComp.find();
							mov.setComprobanteId(dboComp.getField(DboComprobante.CAMPO_COMPROBANTE_ID));
							mov.setNumConstancia(SecuenciaZona.getSecuenciaZona(mov.getNumZona(),dconn));							
							lstListaIndividual.add(mov);
						
						}
						if(hmAbono!=null)
							ExpressoHttpSessionBean.getSession(request).setAttribute("hmAbonoSel",hmAbono);

						}
						if(strPagina != null&&strPagina.length()>0){
							pagActual = Integer.parseInt(strPagina);
						}
						if(pagActual>0 )
							ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", String.valueOf(pagActual - 1));
						if(pagActual+1<lstListaIndividual.size()){
							ExpressoHttpSessionBean.getRequest(request).setAttribute("next", String.valueOf(pagActual + 1));
						}
						
									
						MovimientoBean movActual=(MovimientoBean)lstListaIndividual.get(pagActual);
						dboRegis.setConnection(dconn);
						dboRegis.setField(DboRegisPublico.CAMPO_REG_PUB_ID,movActual.getNumZona());
						dboRegis.find();
						String montoFormateado = movActual.getMonto();
						ExpressoHttpSessionBean.getRequest(request).setAttribute("zonaRegistral",dboRegis.getField(DboRegisPublico.CAMPO_NOMBRE));
						ExpressoHttpSessionBean.getRequest(request).setAttribute("montoTotal",montoFormateado);
						ExpressoHttpSessionBean.getSession(request).setAttribute("lstAbonoIndividual",lstListaIndividual);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("abonoActual",movActual);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_ORGANIZACION);
						TimeZone tz = TimeZone.getDefault(); 
						Locale locale= new Locale("es","ES");
						Calendar cal= Calendar.getInstance(tz);
						userBean.getOficRegistralId();
						userBean.getRegPublicoId();
						System.out.println("fecha----- "+cal.toString());
						//cal.get
						java.util.Date da= new java.util.Date();
						

					//	da.
						//String fechoy= cal.get(Calendar.DAY_OF_MONTH)+""+cal.get(Calendar.);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fecha", "");
						Locale local= new Locale("es","ES");
						java.util.Date hoy= new java.util.Date();
						SimpleDateFormat sd= new SimpleDateFormat("dd 'de' MMMMMMMMM 'del' yyyy",local);
						String fecha="";
						String oficReg=userBean.getOficRegistralId();
						String regPubId=userBean.getRegPublicoId();
	
						if(oficReg!=null&regPubId!=null&regPubId.length()>0&&oficReg.length()>0){
							DboOficRegistral oficRegistral= new DboOficRegistral();
							oficRegistral.setConnection(dconn);
							oficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficReg);
							oficRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID,regPubId);
							oficRegistral.find();
							fecha= oficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE)+", ";
	
						}
	//						
						fecha= fecha+sd.format(hoy);
						ExpressoHttpSessionBean.getRequest(request).setAttribute("fechaHoy", fecha);
					
					/***  para regresar **/
				
					ExpressoHttpSessionBean.getRequest(request).setAttribute("nextRegresar", request.getParameter("nextRegresar"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("prevRegresar", request.getParameter("prevRegresar"));
				
					ExpressoHttpSessionBean.getRequest(request).setAttribute("diainicio", request.getParameter("diainicio"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("mesinicio", request.getParameter("mesinicio"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("anoinicio", request.getParameter("anoinicio"));

					ExpressoHttpSessionBean.getRequest(request).setAttribute("diafin", request.getParameter("diafin"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("mesfin", request.getParameter("mesfin"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("anofin", request.getParameter("anofin"));

					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipopago", request.getParameter("tipopago"));

					ExpressoHttpSessionBean.getRequest(request).setAttribute("agencia", request.getParameter("agencia"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("userId", request.getParameter("userId"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("radio", request.getParameter("radio"));
					
					ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc", request.getParameter("ruc"));
					ExpressoHttpSessionBean.getRequest(request).setAttribute("razsoc", request.getParameter("razsoc"));	
					ExpressoHttpSessionBean.getRequest(request).setAttribute("entidadRegresa", request.getParameter("entidadRegresa"));	
					
				
					/****/	
						
			
				}		
					
				
				
					conn.commit();	
					response.setStyle("individual");
				
			}catch (CustomException e) 
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle(e.getForward());
			}catch(DBException dbe)
			{
				log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
				rollback(conn, request);
				response.setStyle("error");
			}catch(Throwable ex)
			{
				log(Errors.EC_GENERIC_ERROR, "", ex, request);
				rollback(conn, request);
				response.setStyle("error");
			}finally
			{
				pool.release(conn);
				end(request);
			}
			
			return response;
		}
		
	protected ControllerResponse runRegresarState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
		
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
		
			try{
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);
			
				muestraOficinas(request, dconn);
				StringBuffer feci = new StringBuffer(request.getParameter("diainicio"));
				
				feci.append("/").append(request.getParameter("mesinicio")).append("/").append(request.getParameter("anoinicio"));
				HashMap hmAbono= (HashMap)ExpressoHttpSessionBean.getSession(request).getAttribute("hmAbonoSel");
				List lista=(ArrayList)ExpressoHttpSessionBean.getSession(request).getAttribute("listaAbono");
				List listaMovimiento=new ArrayList();
				System.out.println("hash map"+hmAbono);
				for(int i=0;i<lista.size();i++){
					MovimientoBean mov=(MovimientoBean)lista.get(i);
					if(hmAbono!=null&&hmAbono.containsKey(mov.getNumAbono()))
						mov.setSeleccionado(INDICA_SELECCIONADO);
					else
						mov.setSeleccionado(INDICA_NO_SELECCIONADO);
					listaMovimiento.add(mov);
				}
			
		
				StringBuffer fecf = new StringBuffer(request.getParameter("diafin"));
				fecf.append("/").append(request.getParameter("mesfin")).append("/").append(request.getParameter("anofin"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("fecini", feci);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("fecfin", fecf);

			
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador1", "x");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("contador2", null);
				ExpressoHttpSessionBean.getRequest(request).setAttribute("di1", request.getParameter("diainicio"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mi1", request.getParameter("mesinicio"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ai1", request.getParameter("anoinicio"));
	
				ExpressoHttpSessionBean.getRequest(request).setAttribute("df1", request.getParameter("diafin"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("mf1", request.getParameter("mesfin"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("af1", request.getParameter("anofin"));
					
				ExpressoHttpSessionBean.getRequest(request).setAttribute("tp1", request.getParameter("tipopago"));
					
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ag1", request.getParameter("agencia"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("usr1", request.getParameter("userId"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("r1", request.getParameter("radio"));
					
				ExpressoHttpSessionBean.getRequest(request).setAttribute("rs1",request.getParameter("razsoc"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("ruc1", request.getParameter("ruc"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaMovimiento",listaMovimiento);
				ExpressoHttpSessionBean.getSession(request).removeAttribute("lstCertificdo");
				ExpressoHttpSessionBean.getSession(request).removeAttribute("movZonaMonto");
				ExpressoHttpSessionBean.getSession(request).removeAttribute("lstAbonoIndividual");
				ExpressoHttpSessionBean.getRequest(request).setAttribute("next", request.getParameter("nextRegresar"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("previous", request.getParameter("prevRegresar"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("entidad", request.getParameter("entidadRegresa"));
				ExpressoHttpSessionBean.getRequest(request).setAttribute("lineaPrepago", request.getParameter("lineaPrepago"));
				String radio=request.getParameter("radio");
				if(radio.equalsIgnoreCase("1"))
					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_INDIVIDUAL);
				else
					ExpressoHttpSessionBean.getRequest(request).setAttribute("tipoUsr",TIPO_USR_ORGANIZACION);
		
			
				response.setStyle("muestra");
			
			}catch(Throwable ex)
			{
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				response.setStyle("error");
			}finally{
				pool.release(conn);
				end(request);
			}
			return response;
		}
		
	
}

