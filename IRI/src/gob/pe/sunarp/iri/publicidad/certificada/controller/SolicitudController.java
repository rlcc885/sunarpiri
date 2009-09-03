package gob.pe.sunarp.iri.publicidad.certificada.controller;

import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.administracion.bean.DatosUsuarioBean;
import gob.pe.sunarp.extranet.administracion.bean.DocIdenBean;
import gob.pe.sunarp.extranet.administracion.bean.MantenUsuarioBean;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.prepago.bean.AbonoBean;
import gob.pe.sunarp.extranet.prepago.bean.AbonoVentanillaBean;
import gob.pe.sunarp.extranet.prepago.bean.BancoBean;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaCertificadoBean;
import gob.pe.sunarp.extranet.util.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.publicidad.certificada.*;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.*;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarPartidaDirectaService;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarPartidaDirectaServiceImpl;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultarPartidaDirectaSQLImpl;

import java.sql.*;

public class SolicitudController extends ControllerExtension implements Constantes
{
	private String thisClass = SolicitudController.class.getName() + ".";
	//Inicio:jascencio:14/06/2007
	//SUNARP-REGMOBCOM
	private String desNumDoc=null;
	private String desTipoParticipante=null;
	private String desTipoRegistro=null;
	//Fin:jascencio


	public SolicitudController() 
	{
		super();
		addState(new State("cargaInicial", "Muestra la ventana de carga inicial de certificados"));
		addState(new State("datosBasicos", "Muestra la ventana de Datos Basicos"));
		addState(new State("guardarDatosBasicos", "Pasa los datos ingresados en formulario datos basicos"));
		addState(new State("guardarDatosComplementarios", "Guarda los datos ingresados en formulario datos comp"));
		addState(new State("procesarPagoSolicitud", "Procesa el pago de la solicutud"));
		addState(new State("enviarEmail", "Guarda en tabla de emails"));
		
		setInitialState("cargaInicial");
	}


	public String getTitle() {
		return new String("SolicitudController");
	}
	
	private void muestraDocsId(ControllerRequest request,DBConnection dconn) throws DBException
	{
		
		DboTmDocIden doc_iden = new DboTmDocIden(dconn);
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_TIPO_DOC_ID);
		doc_iden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
		
		doc_iden.setField(DboTmDocIden.CAMPO_ESTADO,"1");
			
		ArrayList listaDocsIds = doc_iden.searchAndRetrieveList();
				
		java.util.List listaDocsId = new ArrayList();
				
		for(int i = 0; i < listaDocsIds.size(); i++)
		{
			DboTmDocIden docIdent = (DboTmDocIden) listaDocsIds.get(i);
				
			DocIdenBean bean = new DocIdenBean();
			bean.setTipoDoc(docIdent.getField(DboTmDocIden.CAMPO_TIPO_DOC_ID));
			bean.setNomAbre(docIdent.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
			listaDocsId.add(bean);
		}
			
		ExpressoHttpSessionBean.getRequest(request).setAttribute("listaDocsId", listaDocsId);

	}
	/**
	 * Solicitar Certificado IRI - Pagina Inicio
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	protected ControllerResponse runCargaInicialState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException 
	{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
		    response.setStyle("muestraCertificados");
		   
		}catch (CustomException e) {
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
	
	/**
	 *  Consulta de Tipo de Certificado
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	protected ControllerResponse runDatosBasicosState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		String tipoCertificado="";
		String hidTipoGravVig="";;
		try{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			//obtener usuario de la sesion				
		    UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		    String cuentaId = usuario.getCuentaId();
		    tipoCertificado = request.getParameter("cboTipoCert"); // recoge el tipo de certificado pocedente del combo Certificados
		    hidTipoGravVig = request.getParameter("hidTipoGravVig"); // tipoGravamenVigenciaRMC
		   
		    String estInicialCaja = "";
		    String caja = usuario.getUserId();
		    DboTaCaja dboCaja = new DboTaCaja (dconn);
		    dboCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_ESTA);
		    dboCaja.setField(DboTaCaja.CAMPO_CO_EMPL,cuentaId);
		   
			if(dboCaja.find()==true){
				estInicialCaja=dboCaja.getField(DboTaCaja.CAMPO_ESTA);
			}
			if(estInicialCaja.equals("1") && usuario.getPerfilId()==Constantes.PERFIL_CAJERO){
					req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. Debe aperturar su caja para poder ingresar a la opción.");
					response.setStyle("ok");
			}
			else if(estInicialCaja.equals("0") && usuario.getPerfilId()==Constantes.PERFIL_CAJERO){
					req.setAttribute("mensaje1","La caja "+ caja +" no ha sido aperturada. El tesorero no ha realizado su apertura general.");
					response.setStyle("ok");
			}
			else{
				//Inicio:jascencio:06/06/2007
				//SUNARP-REGMOBCOM:switch para el tipo de certificado
					req.setAttribute("arrCertificados", Tarea.getComboCertificados(tipoCertificado, conn));
					if(tipoCertificado.equals(Constantes.CERTIFICADO_RMC)){
						req.setAttribute("arrDocu",Tarea.getComboTiposDocumento(dconn));
						req.setAttribute("tipoGravVigRMC", hidTipoGravVig);
						System.err.println(hidTipoGravVig);
						System.err.println(Constantes.CERTIFICADO_RMC);
						response.setStyle("muestraRMC");
					}
					else{
						if(tipoCertificado.equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)){
							response.setStyle("muestraCREM");
						}
						else{
							if(tipoCertificado.equals(Constantes.CERTIFICADO_GRAVAMEN_RJB) || tipoCertificado.equals(Constantes.CERTIFICADO_DOMINIAL_RJB)){
								req.setAttribute("tipoCertificado", tipoCertificado);
								response.setStyle("muestraRJB");
							}
							else{
								if(tipoCertificado.equals(Constantes.CERTIFICADO_NEGATIVO)){
									response.setStyle("muestra");
								}
							}
						}
					}
				}
			System.out.println("response.getStyle()-->" + response.getStyle());
				//Fin:jascencio
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
	
	protected ControllerResponse runGuardarDatosBasicosState(ControllerRequest request, ControllerResponse response) throws ControllerException 
	{
			
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		ExpressoHttpSessionBean.getSession(request).removeAttribute("comprobante");
		
		PreparedStatement pstmt = null;
		ResultSet rsetGla = null;
		int servicio_id = 0;
		//int no_paginas = 0;

		try{
			init(request);
			validarSesion(request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
			if(bean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((bean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && bean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && bean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO) || bean.getExonPago()))
			{
				throw new CustomException(Errors.EC_USER_NOT_ALLOWED, "Usuario no permitido");
			}
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			ObjetoSolicitudBean bObjSol = new ObjetoSolicitudBean();
			bObjSol.setDesc_regis(request.getParameter("hidOfic"));
			bObjSol.setOfic_reg_desc(request.getParameter("hidOfic"));
			
//			Inicio:jascencio:08/06/2007
			
			StringBuffer descripcion= null;
			String var=null;
			
			System.out.println("request.getParameter(hidTipo) " + request.getParameter("hidTipo"));
			
			if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_NEGATIVO)){
				bObjSol.setCertificado_id(request.getParameter("cboTipoCert"));
				bObjSol.setCod_GLA(request.getParameter("hidGLA"));
				bObjSol.setTpo_pers(request.getParameter("radTipoPers"));
				bObjSol.setApe_pat(request.getParameter("txtApPa"));
				bObjSol.setApe_mat(request.getParameter("txtApMa"));
				bObjSol.setNombres(request.getParameter("txtNom"));
				bObjSol.setRaz_soc(request.getParameter("txtRazSoc"));
				bObjSol.setPlaca(request.getParameter("txtNumPlaca"));//preguntar acerca de la placa
				bObjSol.setNumeroMatricula(request.getParameter("txtNumMatricula"));
				bObjSol.setNombreBien(request.getParameter("txtNombreBien"));
				bObjSol.setNumeroSerie(request.getParameter("txtNumSerie"));
				String oficina = request.getParameter("hidCboOficinas");
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
				bObjSol.setTpo_cert(Constantes.CERTIFICADO_NEGATIVO);
				bObjSol.setDesc_tipop(request.getParameter("hidTipPers"));
				
				/**
				 * @autor Daniel Bravo
				 * @fecha 18/06/2007
				 * @descripcion Se recupera el id del servicio al que pertenece el certificado
				 */
				Solicitud solicitud = new Solicitud();
				servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn, bObjSol.getCertificado_id()));
				/** fin dbravo 18/06/2007 **/
				
				if(request.getParameter("cboTipoCert").equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS)){
					descripcion= new StringBuffer();
					
					bObjSol.setDesc_certi("CERTIFICADO POSITIVO/NEGATIVO DE " + request.getParameter("hidCert").toUpperCase());
					var=request.getParameter("txtNumMatricula");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" DE NRO. DE MATRICULA ").append(var.toUpperCase());
					}
					var=request.getParameter("txtNombreBien");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" CON NOMBRE ").append(var.toUpperCase());
					}
					var=request.getParameter("txtNumPlaca");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" DE NUMERO DE PLACA ").append(var.toUpperCase());
					}
					var=request.getParameter("txtNumSerie");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" DE SERIE ").append(var.toUpperCase());
					}
					req.setAttribute("des1",descripcion.toString());
					req.setAttribute("codigoCertificado",Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS);
					
				}
				else{
					bObjSol.setDesc_certi("CERTIFICADO POSITIVO/NEGATIVO DE " + request.getParameter("hidCert").toUpperCase());
					req.setAttribute("des1", "");
					req.setAttribute("codigoCertificado","");
				}
				req.setAttribute("hidTipo", Constantes.CERTIFICADO_NEGATIVO);

			}
			else if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_RMC)){
					desTipoParticipante=request.getParameter("hidDesTipoPar");
					bObjSol.setCertificado_id(request.getParameter("cboTipoCert"));
				    bObjSol.setCod_GLA(request.getParameter("hidGLA"));
				    bObjSol.setTpo_pers(request.getParameter("radTipoPers"));
				    bObjSol.setApe_pat(request.getParameter("txtApPa"));
				    bObjSol.setApe_mat(request.getParameter("txtApMa"));
				    bObjSol.setNombres(request.getParameter("txtNom"));
				    bObjSol.setRaz_soc(request.getParameter("txtRazSoc"));
				    bObjSol.setTpo_cert(Constantes.CERTIFICADO_RMC);
				    bObjSol.setDesc_tipop(request.getParameter("hidTipPers"));
				    bObjSol.setSiglas(request.getParameter("txtSiglas"));
			        bObjSol.setTipoParticipante(request.getParameter("cboTipoParticipante"));
			        bObjSol.setTipoDocumento(request.getParameter("cboTipoDocumento"));
			        bObjSol.setNumeroDocumento(request.getParameter("txtNumeroDocumento"));
			        String oficina = request.getParameter("cboOficinas");
					bObjSol.setReg_pub_id(oficina.substring(0,2));
					bObjSol.setOfic_reg_id(oficina.substring(3,5));
					
					/**
					 * @autor Daniel Bravo
					 * @fecha 18/06/2007
					 * @descripcion Se recupera el id del servicio al que pertenece el certificado
					 */
					Solicitud solicitud = new Solicitud();
					servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn, bObjSol.getCertificado_id()));
					/** fin dbravo 18/06/2007 **/
					
					descripcion=new StringBuffer();
					
					var=request.getParameter("txtApPa");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" de la Persona Natural ").append(var.toUpperCase());
					}
					var=request.getParameter("txtApMa");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" ").append(var.toUpperCase());
					}
					var=request.getParameter("txtNom");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" ").append(var.toUpperCase());
					}
					var=request.getParameter("txtRazSoc");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" de la Persona Jurídica ").append(var.toUpperCase());
					}
					else{
						var=request.getParameter("txtSiglas");
						if(var!=null && var.trim().length()>0){
							descripcion.append(" de la Persona Jurídica ");
						}
					}
					
					var=request.getParameter("txtSiglas");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" con siglas: ").append(var.toUpperCase());
					}
					var=request.getParameter("hidDesTipoDoc");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" del documento ").append(var.toUpperCase());
						desNumDoc=request.getParameter("hidDesTipoDoc");
					}
					var=request.getParameter("txtNumeroDocumento");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" ").append(var.toUpperCase());
					}
					var = bObjSol.getTipoParticipante();
					if(var!=null && var.trim().length()>0)
					{
						if(bObjSol.getTipoParticipante().equals("1"))
						{
							descripcion.append(" con tipo de participación ").append("DEUDOR");
						}
						if(bObjSol.getTipoParticipante().equals("2"))
						{
							descripcion.append(" con tipo de participación ").append("ACREEDOR");
						}
						if(bObjSol.getTipoParticipante().equals("3"))
						{
							descripcion.append(" con tipo de participación ").append("REPRESENTANTE");
						}
						if(bObjSol.getTipoParticipante().equals("4"))
						{
							descripcion.append(" con tipo de participación ").append("OTROS");
						}
					}
					
			        if(request.getParameter("cboTipoCert").equals(Constantes.COD_CERTIFICADO_REGISTRO_VIGENCIA)){
			        	
			        	bObjSol.setDesc_certi("CERTIFICADO DE VIGENCIA");
			        	req.setAttribute("codigoCertificado", Constantes.COD_CERTIFICADO_REGISTRO_VIGENCIA);
			        }
			        else{
			        	if(request.getParameter("cboTipoCert").equals(Constantes.COD_CERTIFICADO_REGISTRO_GRAVAMEN)){
			        		bObjSol.setDesc_certi("CERTIFICADO COMPENDIOSO DE GRAVAMEN DE REGISTRO MOBILIARIO DE CONTRATOS");
			        		req.setAttribute("codigoCertificado", Constantes.COD_CERTIFICADO_REGISTRO_GRAVAMEN);
			        	}
			        }
			        req.setAttribute("des1", descripcion);
			        req.setAttribute("hidTipo", Constantes.CERTIFICADO_RMC);
			       

			}
			else if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)){
				bObjSol.setCertificado_id(request.getParameter("cboTipoCert"));
			    bObjSol.setCod_GLA(request.getParameter("hidGLA"));
			    bObjSol.setTpo_pers(request.getParameter("radTipoPers"));
			    bObjSol.setApe_pat(request.getParameter("txtApPa"));
			    bObjSol.setApe_mat(request.getParameter("txtApMa"));
			    bObjSol.setNombres(request.getParameter("txtNom"));
			    bObjSol.setRaz_soc(request.getParameter("txtRazSoc"));
			    bObjSol.setTpo_cert(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO);
			    bObjSol.setDesc_tipop(request.getParameter("hidTipPers"));
			    bObjSol.setTipoParticipante(request.getParameter("cboTipoParticipante"));
		        bObjSol.setDesc_certi("CERTIFICADO REGISTRAL MOBILIARIO CONDICIONADO");
		        
		        
		        bObjSol.setFechaInscripcionASientoDesde(request.getParameter("txtFechaInscripAsientoDesde"));
		        bObjSol.setFechaInscripcionASientoHasta(request.getParameter("txtFechaInscripAsientoHasta"));
		        
		        bObjSol.setFlagHistorico(request.getParameter("rdbHistorico"));
		        bObjSol.setTipoRegistro(request.getParameter("hidTipoRegistro"));
		        String oficina = request.getParameter("cboOficinas");;
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
		        /**
				 * @autor Daniel Bravo
				 * @fecha 18/06/2007
				 * @descripcion Se recupera el id del servicio al que pertenece el certificado
				 */
				Solicitud solicitud = new Solicitud();
				servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn, bObjSol.getCertificado_id()));
				/** fin dbravo 18/06/2007 **/
		        
		        descripcion=new StringBuffer();
		        desTipoParticipante=request.getParameter("hidDesTipoPar");
		        
		        var=request.getParameter("hidTipoRegistro");
				if(var!=null && var.trim().length()>0){
					desTipoRegistro=request.getParameter("hidDesTipoRegistro");
					descripcion.append(" en los Registros RMC ").append(desTipoRegistro.toUpperCase());
				}
				var=request.getParameter("rdbHistorico");
				if(var!=null && var.trim().length()>0){
					if(var.equals("1"))
					descripcion.append("; incluida información histórica");
				}
				var=request.getParameter("txtFechaInscripAsientoDesde");
				if(var!=null && var.trim().length()>0){
					descripcion.append("; con año de inscripción ").append(var.toUpperCase());
				}
				var=request.getParameter("txtFechaInscripAsientoHasta");
				if(var!=null && var.trim().length()>0){
					descripcion.append(" - ").append(var.toUpperCase());
				}
				if(!(bObjSol.getTipoParticipante()==null || bObjSol.getTipoParticipante().equals("")))
				{
					if(bObjSol.getTipoParticipante().equals("1"))
					{
						descripcion.append(" con tipo de participación ").append("DEUDOR");
					}
					if(bObjSol.getTipoParticipante().equals("2"))
					{
						descripcion.append(" con tipo de participación ").append("ACREEDOR");
					}
					if(bObjSol.getTipoParticipante().equals("3"))
					{
						descripcion.append(" con tipo de participación ").append("REPRESENTANTE");
					}
					if(bObjSol.getTipoParticipante().equals("4"))
					{
						descripcion.append(" con tipo de participación ").append("OTROS TIPOS DE PARTICIPACIÓN");
					}
				}
		        req.setAttribute("des1", descripcion);
		        req.setAttribute("codigoCertificado",Constantes.COD_CERTIFICADO_REGISTRO_CREM);
		        req.setAttribute("hidTipo",Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO);
		        
			}
			else if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_ACTOS_VIGENTES)){
				bObjSol.setCertificado_id(request.getParameter("cboTipoCert"));
			    bObjSol.setCod_GLA(request.getParameter("hidGLA"));
			    bObjSol.setTpo_pers(request.getParameter("radTipoPers"));
			    bObjSol.setApe_pat(request.getParameter("txtApPa"));
			    bObjSol.setApe_mat(request.getParameter("txtApMa"));
			    bObjSol.setNombres(request.getParameter("txtNom"));
			    bObjSol.setRaz_soc(request.getParameter("txtRazSoc"));
			    /**inicio: ifigueroa 27/08/2007 ****/
			    /** Ojo: Es necesario la variable CERTIFICADO_MOBILIARIO_CONDICIONADO para que setee a los tres tipos Cert Crem de tipo "C" **/
			    bObjSol.setTpo_cert(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO);
			    //bObjSol.setTpo_cert(Constantes.CERTIFICADO_ACTOS_VIGENTES);
			    /**fin: ifigueroa 27/08/2007 ****/
			    bObjSol.setDesc_tipop(request.getParameter("hidTipPers"));
			    bObjSol.setTipoParticipante(request.getParameter("cboTipoParticipante"));
		        bObjSol.setDesc_certi("CERTIFICADO REGISTRAL MOBILIARIO");
		        String oficina = request.getParameter("cboOficinas");;
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
		        /**
				 * @autor Daniel Bravo
				 * @fecha 18/06/2007
				 * @descripcion Se recupera el id del servicio al que pertenece el certificado
				 */
				Solicitud solicitud = new Solicitud();
				servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn, bObjSol.getCertificado_id()));
				/** fin dbravo 18/06/2007 **/
		        
		        descripcion=new StringBuffer();
		        desTipoParticipante=request.getParameter("hidDesTipoPar");
		        
		        var=request.getParameter("cboTipoParticipante");
				if(var!=null && var.trim().length()>0){
					descripcion.append(" Tipo de Participacion ").append(desTipoParticipante.toUpperCase());
					//descripcion.append(" Tipo de Participacion ").append(var.toUpperCase());
				}
		        
		        req.setAttribute("des1", descripcion);
		        req.setAttribute("codigoCertificado",Constantes.COD_CERTIFICADO_REGISTRO_CREM);
		        req.setAttribute("hidTipo",Constantes.CERTIFICADO_ACTOS_VIGENTES);
		        
			}
			else if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO)){
				bObjSol.setCertificado_id(request.getParameter("cboTipoCert"));
			    bObjSol.setCod_GLA(request.getParameter("hidGLA"));
			    bObjSol.setTpo_pers(request.getParameter("radTipoPers"));
			    bObjSol.setApe_pat(request.getParameter("txtApPa"));
			    bObjSol.setApe_mat(request.getParameter("txtApMa"));
			    bObjSol.setNombres(request.getParameter("txtNom"));
			    bObjSol.setRaz_soc(request.getParameter("txtRazSoc"));
			    /**inicio: ifigueroa 27/08/2007 ****/
			    /** Ojo: Es necesario la variable CERTIFICADO_MOBILIARIO_CONDICIONADO para que setee a los tres tipos Cert Crem de tipo "C" **/
			    //bObjSol.setTpo_cert(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO);
			    bObjSol.setTpo_cert(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO);
			    /**fin: ifigueroa 27/08/2007 ****/
			    
			    bObjSol.setDesc_tipop(request.getParameter("hidTipPers"));
			    bObjSol.setTipoParticipante(request.getParameter("cboTipoParticipante"));
		        bObjSol.setDesc_certi("CERTIFICADO REGISTRAL MOBILIARIO HISTORICO");
		        String oficina = request.getParameter("cboOficinas");;
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
		        /**
				 * @autor Daniel Bravo
				 * @fecha 18/06/2007
				 * @descripcion Se recupera el id del servicio al que pertenece el certificado
				 */
				Solicitud solicitud = new Solicitud();
				servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn, bObjSol.getCertificado_id()));
				/** fin dbravo 18/06/2007 **/
		        
		        descripcion=new StringBuffer();
		        desTipoParticipante=request.getParameter("hidDesTipoPar");
		        var=request.getParameter("cboTipoParticipante");
				if(var!=null && var.trim().length()>0){
					descripcion.append(" Tipo de Participacion ").append(desTipoParticipante.toUpperCase());
					
					//descripcion.append(" Tipo de Participacion ").append(var.toUpperCase());
				}
		        
		        req.setAttribute("des1", descripcion);
		        req.setAttribute("codigoCertificado",COD_CERTIFICADO_REGISTRO_CREM);
		        req.setAttribute("hidTipo",Constantes.CERTIFICADO_MOBILIARIO_HISTORICO);
		        
			}
			else if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_GRAVAMEN_RJB) || request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_DOMINIAL_RJB)){
				bObjSol.setCertificado_id(request.getParameter("cboArea"));
				bObjSol.setCod_GLA(request.getParameter("hidGLA"));
				bObjSol.setTpo_cert(request.getParameter("hidTipo"));
				String oficina = request.getParameter("cboOficinas");
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
				if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_DOMINIAL_RJB)){
					bObjSol.setDesc_certi("CERTIFICADO COMPEDIOSO DE  DOMINIO");
					bObjSol.setTipoInformacionDominio(request.getParameter("radTipInfoDominio"));
				}
				else{
					bObjSol.setDesc_certi("CERTIFICADO COMPEDIOSO DE GRAVAMEN");
				}
				
				/**
				 * @autor Daniel Bravo
				 * @fecha 18/06/2007
				 * @descripcion Se recupera el id del servicio al que pertenece el certificado
				 */
				Solicitud solicitud = new Solicitud();
				servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn, bObjSol.getCertificado_id()));
				/** fin dbravo 18/06/2007 **/
				
				descripcion=new StringBuffer();	
				if(request.getParameter("cboArea").equals(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_D) ||request.getParameter("cboArea").equals(Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_G)){
					if(request.getParameter("radBusca")!=null){
					
						var=request.getParameter("txtNumero");
						if(request.getParameter("radBusca").equals("Placa")){
							if(var!=null && var.trim().length()>0){
								descripcion.append(" de Nro. de placa ").append(var.toUpperCase());
								bObjSol.setPlaca(var);
							}
						}
						else{
							if(var!=null && var.trim().length()>0){
								descripcion.append(" de Nro. de Partida ").append(var.toUpperCase());
								/*** inicio: jrosas 03-09-07 **/
								bObjSol.setNumeroPartida(var);
								/*** fin: jrosas 03-09-07 **/
							}
							
						}
						
					}
				}
				else{
					var=request.getParameter("txtSerie");
					if(var!=null && var.trim().length()>0){
						descripcion.append(" de Nro. de Serie ").append(var.toUpperCase());
						bObjSol.setNumeroSerie(request.getParameter("txtSerie"));
					}
					var=request.getParameter("txtNumMat");
					if(var!=null && var.trim().length()>0){
						descripcion.append(", de Nro. de Matrícula ").append(var.toUpperCase());
						bObjSol.setNumeroMatricula(request.getParameter("txtNumMat"));
					}
					var=request.getParameter("txtPartida");
					if(var!=null && var.trim().length()>0){
						descripcion.append(", de Nro. de Partida ").append(var.toUpperCase());
						bObjSol.setNumeroPartida(request.getParameter("txtPartida"));
					}
				}
				
				//bObjSol.setNumeroMatricula(request.getParameter("txtNumMat"));
				//bObjSol.setNumeroPartida(request.getParameter("txtPartida"));
				//bObjSol.setNumeroSerie(request.getParameter("txtSerie"));
				
				
				req.setAttribute("des1",descripcion);
				req.setAttribute("codigoCertificado",Constantes.COD_CERTIFICADO_REGISTRO_VEHICULAR_D);
				req.setAttribute("hidTipo", request.getParameter("hidTipo"));
			}
			//Fin:jascencio

			// Inicio:mgarate:31/05/2007
			else if(req.getParameter("hidTipo").equals(Constantes.CERTIFICADO_BUSQUEDA))
			{
				String criterio = request.getParameter("criterio");
				String cadenaMuestra = Tarea.generarCriterio(criterio);
				Solicitud solicitud = new Solicitud();
				
				
				bObjSol.setUrlBusqueda(criterio);
				bObjSol.setCriterioBusqueda(cadenaMuestra);
				if(request.getParameter("flagCertBusq").equals("6"))
				{
					if(request.getParameter("flagBusq").equals("D"))
					{
						bObjSol.setCertificado_id("33");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"33"));
					}else if(request.getParameter("flagBusq").equals("I"))
					{
						bObjSol.setCertificado_id("37");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"37"));
					}
					bObjSol.setDesc_certi("CERTIFICADO DE BUSQUEDA DEL REGISTRO DE PROPIEDAD VEHICULAR");
					bObjSol.setCod_GLA("24");
				}else if(request.getParameter("flagCertBusq").equals("11"))
				{
					if(request.getParameter("flagBusq").equals("D"))
					{
						bObjSol.setCertificado_id("34");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"34"));
					}else if(request.getParameter("flagBusq").equals("I"))
					{
						bObjSol.setCertificado_id("38");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"38"));
					}
					bObjSol.setDesc_certi("CERTIFICADO DE BUSQUEDA DEL REGISTRO DE EMBARCACIONES PESQUERAS");
					bObjSol.setCod_GLA("25");
				}else if(request.getParameter("flagCertBusq").equals("12"))
				{
					if(request.getParameter("flagBusq").equals("D"))
					{
						bObjSol.setCertificado_id("35");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"35"));
					}else if(request.getParameter("flagBusq").equals("I"))
					{
						bObjSol.setCertificado_id("39");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"39"));
					}
					bObjSol.setDesc_certi("CERTIFICADO DE BUSQUEDA DEL REGISTRO DE AERONAVES");
					bObjSol.setCod_GLA("26");
				}else if(request.getParameter("flagCertBusq").equals("2"))
				{
					if(request.getParameter("flagBusq").equals("D"))
					{
						bObjSol.setCertificado_id("36");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"36"));
					}else if(request.getParameter("flagBusq").equals("I"))
					{
						bObjSol.setCertificado_id("40");
						servicio_id = Integer.valueOf(solicitud.recuperaServicio(conn,"40"));
					}
					bObjSol.setDesc_certi("CERTIFICADO DE BUSQUEDA DEL REGISTRO DE BUQUES");
					bObjSol.setCod_GLA("27");
				}
				
				bObjSol.setReg_pub_id("00");
				bObjSol.setOfic_reg_id("00");
				bObjSol.setTpo_cert(Constantes.CERTIFICADO_BUSQUEDA);
				req.setAttribute("hidTipo", Constantes.CERTIFICADO_BUSQUEDA);
			}
			// Fin:mgarate:31/05/2007
			/*
			 * Inicio:jascencio:03/08/2007
			 * CC:REGMOBCON-2006
			 */
			else if(request.getParameter("hidTipo").equals(Constantes.COPIA_LITERAL_RMC)){

				String refNumPartidaAnterior = null;
				String refNumPartidaNuevo 	 = null;
				String estado				 = "";
				String estadoAnterior		 = "";
				ConsultarPartidaDirectaSQL consultarPartidaDirectaSQL; 
				consultarPartidaDirectaSQL = new ConsultarPartidaDirectaSQLImpl(conn,dconn); 
				
				refNumPartidaNuevo=request.getParameter("refnum_part");
				refNumPartidaAnterior = consultarPartidaDirectaSQL.obtenerRefNumParAntiguo(refNumPartidaNuevo);
				bObjSol.setTpo_cert(Constantes.COPIA_LITERAL_RMC);
				bObjSol.setDesc_certi("COPIA LITERAL DE PARTIDA COMPLETA RMC");
				bObjSol.setRefnum_part(refNumPartidaNuevo);
				//Tarifario
				DboPartida dboPartida			= new DboPartida(dconn);
				DboPartida dboPartidaAntigua	= null;
				
				dboPartida.setField(DboPartida.CAMPO_REFNUM_PART, bObjSol.getRefnum_part());
				if(refNumPartidaAnterior != null && refNumPartidaAnterior.length() > 0){
					dboPartidaAntigua = new DboPartida(dconn);
					dboPartidaAntigua.setField(DboPartida.CAMPO_REFNUM_PART, refNumPartidaAnterior);
					if(dboPartidaAntigua.find() == true){
						estadoAnterior=dboPartidaAntigua.getField(DboPartida.CAMPO_ESTADO);
						bObjSol.setNumeroPartidaAnterior(dboPartidaAntigua.getField(DboPartida.CAMPO_NUM_PARTIDA));
						bObjSol.setRefNumParAnterior(refNumPartidaAnterior);
					}
				}
				
				if(dboPartida.find() == true){
					estado = dboPartida.getField(DboPartida.CAMPO_ESTADO);
				}
				req.setAttribute("estadoActivo",estado);
				req.setAttribute("estadoAnteriorActivo",estadoAnterior);
				bObjSol.setLibro(dboPartida.getField(DboPartida.CAMPO_COD_LIBRO));	
				bObjSol.setArea_reg_id(request.getParameter("area"));
				
				int numPagAnt = 0;
				if(request.getParameter("noPgnaTotal") == null){
					if (isTrace(this))
						System.out.println("escenario 1");
					
					bObjSol.setNumeroPartida(dboPartida.getField(dboPartida.CAMPO_NUM_PARTIDA));
					bObjSol.setReg_pub_id(dboPartida.getField(dboPartida.CAMPO_REG_PUB_ID));
					bObjSol.setOfic_reg_id(dboPartida.getField(dboPartida.CAMPO_OFIC_REG_ID));
					int numPagNue = consultarPartidaDirectaSQL.numeroPaginas(bObjSol.getRefnum_part());
					bObjSol.setNum_pag(String.valueOf(numPagNue));
				}
				else
				{
					bObjSol.setNumeroPartida(request.getParameter("noPartida"));
					try
					{
						Integer.parseInt(request.getParameter("nroTitulo"));
						bObjSol.setNum_titu(request.getParameter("nroTitulo"));
					}
					catch (NumberFormatException ne1){}
					
					bObjSol.setNum_pag(request.getParameter("noPgnaTotal"));
					String oficina = request.getParameter("cboOficinas");
					bObjSol.setReg_pub_id(oficina.substring(0,2));
					bObjSol.setOfic_reg_id(oficina.substring(3,5));
				}
				if(refNumPartidaAnterior != null && refNumPartidaAnterior.length() > 0){
					numPagAnt = consultarPartidaDirectaSQL.numeroPaginas(refNumPartidaAnterior);
				}
				bObjSol.setNumeroPaginasAnterior(String.valueOf(numPagAnt));
				
				StringBuffer quebusq = new StringBuffer();
				servicio_id=Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE;
				bObjSol.setCertificado_id(Constantes.COD_CERTIFICADO_COPIA_LITERAL_RMC);
				
				quebusq.append("SELECT gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
				quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
				quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,bObjSol.getLibro());
				pstmt.setInt(2,servicio_id);
				
				rsetGla = pstmt.executeQuery();
				if(!rsetGla.next())
					throw new CustomException("Criterio no disponible");
				
				bObjSol.setCod_GLA(rsetGla.getString("cod_grupo_libro_area"));
				//
				req.setAttribute("hidTipo", Constantes.COPIA_LITERAL_RMC);
			}
			/*
			 * Fin:jascencio
			 */
			else if(request.getParameter("hidTipo").equals(Constantes.COPIA_LITERAL))
			{
				System.out.println("COPIA LITERAL--------------------->1");
				// 30/11/05 - HP - Inicio
				// antes: el tipo de certificado es seteado a 7
				// ahora: el tipo de certificado es seteado en función al tipo de libro
				// 
				/*
				bObjSol.setCertificado_id("7");
				*/
				String estado = "";
				bObjSol.setTpo_cert(Constantes.COPIA_LITERAL);
				bObjSol.setDesc_certi("COPIA LITERAL DE PARTIDA COMPLETA");
				bObjSol.setRefnum_part(request.getParameter("refnum_part"));
				//Tarifario
				DboPartida parti2 = new DboPartida(dconn);
				/**** JBUGARIN INICIO DESCAJ 09/01/07 ****/
				parti2.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO +"|"+ DboPartida.CAMPO_ESTADO); 
				parti2.setField(DboPartida.CAMPO_REFNUM_PART, bObjSol.getRefnum_part());
				if(parti2.find()==true){
				estado = parti2.getField(DboPartida.CAMPO_ESTADO);
				}
				req.setAttribute("estadoActivo",estado);
				bObjSol.setLibro(parti2.getField(DboPartida.CAMPO_COD_LIBRO));	
				bObjSol.setArea_reg_id(request.getParameter("area"));
				DboPartida parti = new DboPartida(dconn);
				parti.setField(DboPartida.CAMPO_REFNUM_PART, bObjSol.getRefnum_part());
				if(parti.find()==true)
				/**** JBUGARIN FIN DESCAJ 09/01/07 ****/
				bObjSol.setLibro(parti.getField(DboPartida.CAMPO_COD_LIBRO));
				
				System.out.println("COPIA LITERAL--------------------->2");
				
				// antes: primero se llamaba al tarifario y después se calculaba el número de página
				// ahora: primero se calcula el número de página, se calcula el servicio_id y después se llama al tarifario
				// calcular número de páginas
				if(request.getParameter("noPgnaTotal")==null)
				{
					if (isTrace(this))
						System.out.println("escenario 1");
					/**DboPartida parti = new DboPartida(dconn);
					parti.setField(DboPartida.CAMPO_REFNUM_PART, bObjSol.getRefnum_part());
					parti.find();
					**/
					bObjSol.setNumPartida(parti.getField(parti.CAMPO_NUM_PARTIDA));
					//bObjSol.setNum_pag()
					bObjSol.setReg_pub_id(parti.getField(parti.CAMPO_REG_PUB_ID));
					bObjSol.setOfic_reg_id(parti.getField(parti.CAMPO_OFIC_REG_ID));
					StringBuffer q = new StringBuffer();
					q.append("select sum(sub) AS paginas ")
						.append("from( ")
						.append("select sum(b.numpag) AS sub ").append("from user1.partida a, user1.asiento b ")
						.append("where a.refnum_part=b.refnum_part and  ").append("a.refnum_part='").append(bObjSol.getRefnum_part()).append("' ")
						.append("UNION ALL ").append("select sum(c.numpag) AS sub ")
						.append("from user1.partida a, user1.ficha c ").append("where a.refnum_part=c.refnum_part and  ")
						.append("a.refnum_part='").append(bObjSol.getRefnum_part()).append("' ")
						.append("UNION ALL ")
						.append("select count(*) AS sub ").append("from user1.partida a, user1.tomo_folio d ")
						.append("where a.refnum_part=d.refnum_part and  ")
						.append("a.refnum_part='").append(bObjSol.getRefnum_part()).append("' ")
						.append(") ");
						
					Statement stmt = conn.createStatement();
					ResultSet rset = null;
					try
					{
						rset = stmt.executeQuery(q.toString());
						// se descomentó el if
						if (isTrace(this)) 
							System.out.println("Paginas Totales QUERY ---> "+q.toString());
						rset.next();
						// No Paginas
						bObjSol.setNum_pag(rset.getString("paginas"));
					}
					finally
					{
						rset.close();
						stmt.close();
					}
				}
				else
				{
					bObjSol.setNumPartida(request.getParameter("noPartida"));
					try
					{
						Integer.parseInt(request.getParameter("nroTitulo"));
						bObjSol.setNum_titu(request.getParameter("nroTitulo"));
					}
					catch (NumberFormatException ne1){}
					// No Paginas
					bObjSol.setNum_pag(request.getParameter("noPgnaTotal"));
					String oficina = request.getParameter("cboOficinas");
					bObjSol.setReg_pub_id(oficina.substring(0,2));
					bObjSol.setOfic_reg_id(oficina.substring(3,5));
				}
				// Servicio
				// --------------------------------------------------------------------------------
				// estamos en copia literal certificada de partida completa
				// en función del libro (y del número de páginas) se determina el servicio_id
				// [si pertenece al cod_grupo_libro_area = 1 (Propiedad Inmueble Predial)
				//  ó
				//  si pertenece al cod_grupo_libro_area = 1 (Prenda Agrícola)]
				// 		si [Número de Páginas > 2]
				// 		entonces el servicio_id es 112
				//		sino el servicio_id es 111
				// sino el servicio_id es 110
				// --------------------------------------------------------------------------------`
				servicio_id = 0;
				StringBuffer quebusq = new StringBuffer();
				quebusq.append("select * from grupo_libro_area_det where cod_grupo_libro_area in (1,5) and cod_libro=?");
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,bObjSol.getLibro());
				rsetGla = pstmt.executeQuery();
				if(!rsetGla.next()) {
					servicio_id = 110;
					bObjSol.setCertificado_id("7");
				} else {
					if (Integer.parseInt(bObjSol.getNum_pag())>2) {
						servicio_id = 112;
						bObjSol.setCertificado_id("15");
					} else {
						servicio_id = 111;
						bObjSol.setCertificado_id("14");
					}
				}
				// --------------------------------------------------------------------------------
				
				System.out.println("COPIA LITERAL--------------------->3");
				
				// Tarifario
				quebusq = new StringBuffer();
				quebusq.append("SELECT gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
				quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
				quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,bObjSol.getLibro());
				pstmt.setInt(2,servicio_id);
				/*
				pstmt.setInt(2,110);
				*/
				// 30/11/05 - HP - Fin
				rsetGla = pstmt.executeQuery();
				if(!rsetGla.next())
					throw new CustomException("Criterio no disponible");
				bObjSol.setCod_GLA(rsetGla.getString("cod_grupo_libro_area"));
				//
				
				System.out.println("COPIA LITERAL--------------------->4");
			}
			/*
			 * Inicio:jascencio:27/08/2007
			 * CC: SUNARP-REGMOBCON-2006
			 */
			else if(request.getParameter("hidTipo").equals(Constantes.COPIA_LITERAL_ASIENtO_RMC)){

				bObjSol.setTpo_cert(Constantes.COPIA_LITERAL_RMC);
				bObjSol.setDesc_certi("COPIA LITERAL DE ASIENTO COMPLETO RMC");
				bObjSol.setRefnum_part(request.getParameter("refnum_part"));
				DboPartida parti2 = new DboPartida(dconn);
				parti2.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
				parti2.setField(DboPartida.CAMPO_REFNUM_PART, bObjSol.getRefnum_part());
				parti2.find();
				
				bObjSol.setLibro(parti2.getField(parti2.CAMPO_COD_LIBRO));	
				bObjSol.setNumeroPartida(request.getParameter("noPartida"));
				bObjSol.setNum_pag(request.getParameter("nroPags"));
				bObjSol.setNumeroPaginasAnterior("0");
				String oficina = request.getParameter("cboOficinas");
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
				bObjSol.setArea_reg_id(request.getParameter("area"));

				servicio_id=Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE;
				bObjSol.setCertificado_id(Constantes.COD_CERTIFICADO_COPIA_LITERAL_ASIENTO_RMC);
				StringBuffer quebusq = new StringBuffer();
				quebusq.append("SELECT gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
				quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
				quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
				pstmt = null;
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,bObjSol.getLibro());
				pstmt.setInt(2,servicio_id);
				rsetGla = pstmt.executeQuery();
				
				if(!rsetGla.next())
					throw new CustomException("Criterio no disponible");
				bObjSol.setCod_GLA(rsetGla.getString("cod_grupo_libro_area"));
				
				try
				{
					Integer.parseInt(request.getParameter("ano"));
					bObjSol.setAa_titu(request.getParameter("ano"));
				}
				catch (NumberFormatException ne2){}
				
				bObjSol.setNs_asie(request.getParameter("nsasie"));
				try
				{
					Integer.parseInt(request.getParameter("nroTitulo"));
					bObjSol.setNum_titu(request.getParameter("nroTitulo"));
				}catch (NumberFormatException ne3){}
				bObjSol.setCod_acto(request.getParameter("codActo"));
				bObjSol.setDesc_acto(request.getParameter("actoDesc"));
				bObjSol.setPlaca(request.getParameter("placa"));
				try
				{
					Integer.parseInt(request.getParameter("nsasieplaca"));
					bObjSol.setNs_asie_placa(request.getParameter("nsasieplaca"));
				}
				catch (NumberFormatException ne4){}
				req.setAttribute("hidTipo", Constantes.COPIA_LITERAL_ASIENtO_RMC);
			}
			/*
			 * Fin:jascencio
			 */
			else
			{
				// 30/11/05 - HP - Inicio
				// antes: el tipo de certificado es seteado a 8
				// ahora: el tipo de certificado es seteado en función al tipo de libro
				// 
				/*
				bObjSol.setCertificado_id("8");
				*/
				bObjSol.setTpo_cert(Constantes.COPIA_LITERAL);
				bObjSol.setDesc_certi("COPIA CERTIFICADA DE ASIENTO COMPLETO");
				bObjSol.setRefnum_part(request.getParameter("refnum_part"));
				DboPartida parti2 = new DboPartida(dconn);
				parti2.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
				parti2.setField(DboPartida.CAMPO_REFNUM_PART, bObjSol.getRefnum_part());
				parti2.find();
				
				bObjSol.setLibro(parti2.getField(parti2.CAMPO_COD_LIBRO));	
				// antes: primero se llamaba al tarifario y después se calculaba el número de página
				// ahora: primero se calcula el número de página, se calcula el servicio_id y después se llama al tarifario
				// recuperar parametros (entre ellos el numero de pagina)
				bObjSol.setNumPartida(request.getParameter("noPartida"));
				bObjSol.setNum_pag(request.getParameter("nroPags"));
				String oficina = request.getParameter("cboOficinas");
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
				bObjSol.setArea_reg_id(request.getParameter("area"));

				// Servicio
				// --------------------------------------------------------------------------------
				// estamos en copia literal certificada de partida completa
				// en función del libro (y del número de páginas) se determina el servicio_id
				// [si pertenece al cod_grupo_libro_area = 1 (Propiedad Inmueble Predial)
				//  ó
				//  si pertenece al cod_grupo_libro_area = 1 (Prenda Agrícola)]
				// 		si [Número de Páginas > 2]
				// 		entonces el servicio_id es 112
				//		sino el servicio_id es 111
				// sino el servicio_id es 110
				// --------------------------------------------------------------------------------`
				servicio_id = 0;
				StringBuffer quebusq = new StringBuffer();
				quebusq.append("select * from grupo_libro_area_det where cod_grupo_libro_area in (1,5) and cod_libro=?");
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,bObjSol.getLibro());
				rsetGla = pstmt.executeQuery();
				if(!rsetGla.next()) {
					servicio_id = 110;
					bObjSol.setCertificado_id("8");
				} else {
					if (Integer.parseInt(bObjSol.getNum_pag())>2) {
						servicio_id = 112;
						bObjSol.setCertificado_id("17");
					} else {
						servicio_id = 111;
						bObjSol.setCertificado_id("16");
					}
				}
				// --------------------------------------------------------------------------------


				//Tarifario
				quebusq = new StringBuffer();
				
				quebusq.append("SELECT gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
				quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
				quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
				pstmt = null;
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,bObjSol.getLibro());
				//pstmt.setInt(2,110);
				pstmt.setInt(2,servicio_id);
				rsetGla = pstmt.executeQuery();
				
				if(!rsetGla.next())
					throw new CustomException("Criterio no disponible");
				bObjSol.setCod_GLA(rsetGla.getString("cod_grupo_libro_area"));
				/*
				bObjSol.setNumPartida(request.getParameter("noPartida"));
				bObjSol.setNum_pag(request.getParameter("nroPags"));
				String oficina = request.getParameter("cboOficinas");
				bObjSol.setReg_pub_id(oficina.substring(0,2));
				bObjSol.setOfic_reg_id(oficina.substring(3,5));
				bObjSol.setArea_reg_id(request.getParameter("area"));
				*/
				try
				{
					Integer.parseInt(request.getParameter("ano"));
					bObjSol.setAa_titu(request.getParameter("ano"));
				}
				catch (NumberFormatException ne2){}
				
				bObjSol.setNs_asie(request.getParameter("nsasie"));
				try
				{
					Integer.parseInt(request.getParameter("nroTitulo"));
					bObjSol.setNum_titu(request.getParameter("nroTitulo"));
				}catch (NumberFormatException ne3){}
				bObjSol.setCod_acto(request.getParameter("codActo"));
				bObjSol.setDesc_acto(request.getParameter("actoDesc"));
				bObjSol.setPlaca(request.getParameter("placa"));
				try
				{
					Integer.parseInt(request.getParameter("nsasieplaca"));
					bObjSol.setNs_asie_placa(request.getParameter("nsasieplaca"));
				}
				catch (NumberFormatException ne4){}
			}
			
			// lógica tanto para partidas como asientos
			/*
			 * Inicio:jascencio:10/09/2007
			 * CC: SUNARP-REGMOBCON-2006
			 */
			boolean variable = false;
			if(bObjSol.getNumeroPaginasAnterior() == null){
				variable = true;
			}else{
				if(bObjSol.getNumeroPaginasAnterior().equals("0")){
					variable = true;
				}
			}
			
			/*
			 * Fin:jascencio
			 */
			
			//java.util.ArrayList objetoSolicitudList = new ArrayList();
			//objetoSolicitudList.add(bObjSol);
			
			System.out.println("COPIA LITERAL--------------------->5");
			
				SolicitudCertificadoNegativo solCertNeg = new SolicitudCertificadoNegativo();
				solCertNeg.setConn(conn);
				//	Inicio:jhenifer:07/06/2007
				if(!bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_NEGATIVO) && !bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_BUSQUEDA) &&
				   !bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_ACTOS_VIGENTES) && !bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO) &&
				   !bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO) && !bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_DOMINIAL_RJB) &&
				   !bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_GRAVAMEN_RJB) && !bObjSol.getTpo_cert().equals(Constantes.CERTIFICADO_RMC) &&  
					bObjSol.getNum_pag().equals("0") && variable)
				{
					String mensajeErrorPartidaIncompleta = (new StringBuffer("Atención: Administrador de Jurisdicción "))
													.append("La Partida ")
													.append(bObjSol.getNumPartida())
													.append(" del Area ")
													.append(bObjSol.getArea_reg_id())
													.append(" de la Oficina ")
													.append(bObjSol.getOfic_reg_desc())
													.append(" requiere ser corregida y sincronizada a la Bodega Central,")
													.append(" un usuario de la Extranet recibió un mensaje de error al momento")
													.append(" de accederla.")
													.toString();
					throw new CustomException(Constantes.EC_NO_TODAS_IMAGENES_DISPONIBLES, mensajeErrorPartidaIncompleta, "errorMotivosTecnicos");
				}
			// Fin:jrosas:07/06/2007
			
			//Tarifario
			// 30/11/2055 - HP - Inicio
			/*
			if(bObjSol.getNum_pag().equals("1"))
				bObjSol.setSubTotal(""+(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))*2.00));
			else
				bObjSol.setSubTotal(""+(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))*Long.parseLong(bObjSol.getNum_pag())));
			*/
			if (isTrace(this)) 
				System.out.println("Calculando tarifario para el servicio " + servicio_id);
			if(request.getParameter("hidTipo").equals(Constantes.CERTIFICADO_NEGATIVO))
			{
				bObjSol.setSubTotal(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()));


			}else if(servicio_id==111){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
			}
			else if (servicio_id==112)
			{
				bObjSol.setSubTotal(
				"" +
				(Long.parseLong(solCertNeg.obtenTarifa("14",bObjSol.getCod_GLA()))
				 +
				 (Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))
				  *
				  (Long.parseLong(bObjSol.getNum_pag()) - 2)
				 )
				)
				);
			}else if(servicio_id == Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE){
				double precioBase 	= 0; 
				double precioPagina = 0;
				long numeroPaginas	= 0;
				double precioFinal  = 0;
				String cadenaAux	= null;
				
				cadenaAux = solCertNeg.obtenTarifaServicio(Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE, bObjSol.getCod_GLA());
				if(cadenaAux != null ||cadenaAux.length() > 0){
					precioBase = Double.parseDouble(cadenaAux);
				}else{
					throw new CustomException("No existe una tarifa base para COPIA LITERAL RMC");
				}
				cadenaAux = solCertNeg.obtenTarifaServicio(Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_POR_PAGINA, bObjSol.getCod_GLA());
				if(cadenaAux != null ||cadenaAux.length() > 0){
					precioPagina = Double.parseDouble(cadenaAux);
				}else{
					throw new CustomException("No existe una tarifa para COPIA LITERAL RMC");
				}
				numeroPaginas 	= (Long.parseLong(bObjSol.getNum_pag()) + Long.parseLong(bObjSol.getNumeroPaginasAnterior()) - 2);
				if(numeroPaginas < 0){
					numeroPaginas = 0;
				}
				precioFinal = precioBase + (precioPagina*numeroPaginas);
				bObjSol.setSubTotal( "" + precioFinal);
				req.setAttribute("hidTipo", Constantes.COPIA_LITERAL_RMC);
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RMC_POSITIVO_NEGATIVO){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RMC_GRAVAMEN){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RMC_VIGENCIA){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_VEHICULAR){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_AERONAVES){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_BUQUES){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_DOMINALES_EMB_PESQ){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_VEHICULAR){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_AERONAVES){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_EMB_PESQ){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_GRAVAMEN_BUQUES){
				
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}
			//Inicio:mgarate:18/06/2007
			else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_VEHICULAR)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_EMB_PESQ)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_AERONAVES)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_DIRECTA_BUQUES)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_VEHICULAR)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_EMB_PESQ)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_AERONAVES)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_RJB_BUSQUEDA_INDICE_BUQUES)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_CREM_ACTOS_VIGENTES)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_CREM_HISTORICOS)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}else if(servicio_id==Constantes.SERVICIO_CERTIFICADO_CREM_CONDICIONADO)
			{
				bObjSol.setSubTotal("" +(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))));
				
			}
			//Fin:mgarate:18/06/2007
			else
			{
				bObjSol.setSubTotal(
				"" +
				(Long.parseLong(solCertNeg.obtenTarifa(bObjSol.getCertificado_id(),bObjSol.getCod_GLA()))
				*
				Long.parseLong(bObjSol.getNum_pag()))
				);
			if (isTrace(this)) 
				System.out.println("Calculando tarifario para el servicio " + servicio_id + " = " + bObjSol.getSubTotal());
			// 30/11/2055 - HP - Fin
			req.setAttribute("hidTipo", Constantes.COPIA_LITERAL);
			}
			
			System.out.println("COPIA LITERAL--------------------->7");
			
			DboTarifa tar = new DboTarifa(dconn);
			tar.setField(tar.CAMPO_SERVICIO_ID,Constantes.SERVICIO_DELIVERY);
			tar.find();
			req.setAttribute("delivery", tar.getField(tar.CAMPO_PREC_OFIC));
			solCertNeg.addObjetoSolicitudList(bObjSol);
			session.setAttribute("beanObjSol", bObjSol);
			session.setAttribute("solCertNeg", solCertNeg);
			
			//Tipos de Documento
			req.setAttribute("arrDocu", Tarea.getComboTiposDocumento(dconn));
			//---ComboBox
			java.util.ArrayList arreglo3 = new java.util.ArrayList(); //departamento
			
			DboTmProvincia dboProvincia = new DboTmProvincia(dconn);
			
			DboTmDepartamento dbo3 = new DboTmDepartamento(dconn);
			dbo3.setField(DboTmDepartamento.CAMPO_PAIS_ID, "01");
			dbo3.setField(DboTmDepartamento.CAMPO_ESTADO, "1");
			java.util.ArrayList arr3 =	dbo3.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
			java.util.ArrayList arrpro = new java.util.ArrayList();
			
			System.out.println("COPIA LITERAL--------------------->8");
			
			for (int i = 0; i < arr3.size(); i++) 
			{
				ComboBean b = new ComboBean();
				DboTmDepartamento d = (DboTmDepartamento) arr3.get(i);
				b.setCodigo(d.getField(DboTmDepartamento.CAMPO_DPTO_ID));
				b.setDescripcion(d.getField(DboTmDepartamento.CAMPO_NOMBRE));
				arreglo3.add(b);
				
				//buscar las provincias del departamento
				dboProvincia.clearAll();
				dboProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, b.getCodigo());
				dboProvincia.setField(DboTmProvincia.CAMPO_ESTADO, "1");
				java.util.ArrayList arrpv =
					dboProvincia.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
				for (int w = 0; w < arrpv.size(); w++) {
					DboTmProvincia dp = (DboTmProvincia) arrpv.get(w);
					Value05Bean b5 = new Value05Bean();
					b5.setValue01(dp.getField(DboTmProvincia.CAMPO_PROV_ID));
					b5.setValue02(dp.getField(DboTmProvincia.CAMPO_NOMBRE));
					b5.setValue03(b.getCodigo());
					arrpro.add(b5);
				}
			}
			
			System.out.println("COPIA LITERAL--------------------->9");
			
			req.setAttribute("arr2", Tarea.getComboPaises(dconn));
			req.setAttribute("arr3", arreglo3);
			//arreglo hijo provincia
			req.setAttribute("arr_hijo1", arrpro);
			
			//Recuperar bancos
			DboTmBanco bancoI = new DboTmBanco(dconn);
			bancoI.setField(DboTmBanco.CAMPO_ESTADO, "1");
			bancoI.setFieldsToRetrieve(DboTmBanco.CAMPO_BANCO_ID + "|" + DboTmBanco.CAMPO_NOMBRE);
			ArrayList listaBancos = bancoI.searchAndRetrieveList();
			bancoI.clearFieldsToRetrieve();
			
			java.util.List listaBanco = new ArrayList();
					
			for(int i = 0; i < listaBancos.size(); i++)
			{
				DboTmBanco banco = (DboTmBanco) listaBancos.get(i);
					
				BancoBean beanbanco = new BancoBean();
				beanbanco.setId(banco.getField(DboTmBanco.CAMPO_BANCO_ID));
				beanbanco.setDescripcion(banco.getField(DboTmBanco.CAMPO_NOMBRE));
				listaBanco.add(beanbanco);
			}
				
			System.out.println("COPIA LITERAL--------------------->10"+bean.getUserId());
			
			req.setAttribute("listaBancos", listaBanco);
			if(bean.getPerfilId()!=Constantes.PERFIL_CAJERO)
			{
				DatosUsuarioBean datosUsuarioBean = Tarea.getDatosUsuario(dconn,bean.getUserId());
				req.setAttribute("DATOS_FORMULARIO", datosUsuarioBean);
				Zona zona = new Zona();
				zona.setConn(dconn);
				//zona.setUsuario(usuario);
				zona.setPaisId(datosUsuarioBean.getPais());
				zona.setDepartamentoId(datosUsuarioBean.getDepartamento());
				zona.setProvinciaId(datosUsuarioBean.getProvincia());
				zona.calculaZona();
				req.setAttribute("oficRegId", zona.getOficRegId());
				req.setAttribute("regPubId", zona.getRegPubId());
				
			}
			
			System.out.println("COPIA LITERAL--------------------->11");
			
			response.setStyle("guardaBasico");
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
			JDBC.getInstance().closeResultSet(rsetGla);
			JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		return response;
	}


	protected ControllerResponse runGuardarDatosComplementariosState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
		try{
			
			init(request);
			validarSesion(request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean bean = ExpressoHttpSessionBean.getUsuarioBean(request);
			if(bean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((bean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && bean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && bean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
			 || bean.getExonPago()))
			{
				throw new CustomException(Errors.EC_USER_NOT_ALLOWED, "Usuario no permitido");
			}
			conn = pool.getConnection();
			conn.setAutoCommit(false);

			System.out.println("SolicitudController.runGuardarDatosComplementariosState 1 --> comprobante " + ExpressoHttpSessionBean.getSession(request).getAttribute("comprobante"));
			
			SolicitudCertificadoNegativo sol = null;
			boolean tarj = false;
			if(ExpressoHttpSessionBean.getSession(request).getAttribute("comprobante") == null)
			{
				sol = (SolicitudCertificadoNegativo)session.getAttribute("solCertNeg");
				
				SolicitanteBean bSolicit = new SolicitanteBean();
				DestinatarioBean bDesti = new DestinatarioBean();
				AbonoBean bAbo = new AbonoBean();
				//PagoBean bPago = new PagoBean();
				
				bSolicit.setTpo_pers(request.getParameter("radSolTipPers"));
				bSolicit.setApe_pat(request.getParameter("txtSolApPa"));
				bSolicit.setApe_mat(request.getParameter("txtSolApMa"));
				bSolicit.setNombres(request.getParameter("txtSolNom"));
				bSolicit.setRaz_soc(request.getParameter("txtSolRazSoc"));
				bSolicit.setTipo_doc_id(request.getParameter("cboSolTipDoc"));
				bSolicit.setNum_doc_iden(request.getParameter("txtSolNumDoc"));
				
				bDesti.setTpo_env(request.getParameter("radEnvTipo"));
				bDesti.setPais_id(request.getParameter("hidEnvPais"));
				bDesti.setDpto_id(request.getParameter("cboEnvDpto"));
				bDesti.setDpto_otro(request.getParameter("hidEnvOtro"));
				bDesti.setProv_id(request.getParameter("cboEnvProv"));
				bDesti.setDistrito(request.getParameter("txtEnvDist"));
				bDesti.setDirecc(request.getParameter("txtEnvDire"));
				bDesti.setCod_post(request.getParameter("txtEnvCodPost"));
				String oficina = request.getParameter("cboOficinas");
				bDesti.setReg_pub_id(oficina.substring(0,2));
				bDesti.setOfic_reg_id(oficina.substring(3,5));
				bDesti.setTpo_pers(request.getParameter("radEnvTipoPer"));
				bDesti.setApe_pat(request.getParameter("txtEnvApPa"));
				bDesti.setApe_mat(request.getParameter("txtEnvApMa"));
				bDesti.setNombres(request.getParameter("txtEnvNom"));
				bDesti.setRaz_soc(request.getParameter("txtEnvRazSoc"));
				bDesti.setOfic_reg_desc(request.getParameter("hidDestiOficDesc"));
				
				bAbo.setUsuario(request.getParameter("usuario"));
				bAbo.setNombre(request.getParameter("nombre"));
				bAbo.setContratoId(request.getParameter("contratoId"));
				//bAbo.setMonto_bruto(sol.getTotal());
				bAbo.setLineaPrePago(request.getParameter("linea"));
				bAbo.setTipoPago(request.getParameter("tipopago"));
				bAbo.setBancoId(request.getParameter("bancoId"));
				bAbo.setTipoCheque(request.getParameter("tipocheque"));
				bAbo.setNumCheque(request.getParameter("numcheque"));
				bAbo.setConcAbono(Constantes.ABONO_CONCEPTO_PUBLICIDAD_CERTIFICADA);
				sol.setAboBean(bAbo);
				
				sol.setConn(conn);
				//sol.addObjetoSolicitudList(bObjSol);
				sol.setSolicitanteBean(bSolicit);
				sol.setDestinatarioBean(bDesti);
				sol.setCuenta_id(bean.getCuentaId());
				sol.setUsr_crea(bean.getUserId());
				sol.setUsr_modi(bean.getUserId());
				sol.setEstado(Constantes.ESTADO_ATEN_PENDIENTE);
				sol.setGasto_envio(request.getParameter("txtDisEnv"));
				sol.setTipoPago(request.getParameter("tipopago"));
				
				/**
				 * inicio:dbravo:10/08/2007
				 * descripcion: Se va registrar el despacho a domicilio en el objeto solicitud, para ser manejado por el certificado CREM
				 */
				if(request.getParameter("flagAceptaCondicion")!=null && request.getParameter("flagAceptaCondicion").trim().length()>0){
					sol.getObjetoSolicitudList(0).setFlagAceptaCondicion(request.getParameter("flagAceptaCondicion"));
				}else{
					sol.getObjetoSolicitudList(0).setFlagAceptaCondicion("0");
				}
				
				if(bDesti.getTpo_env().equals(Constantes.TIPO_DOMICILIO)){
					sol.getObjetoSolicitudList(0).setFlagEnvioDomicilio("1");
				}else{
					sol.getObjetoSolicitudList(0).setFlagEnvioDomicilio("0");
				}
				/**
				 * fin:dbravo:10/08/2007
				 */
				
				ComprobanteBean beancomp = sol.grabarSolicitud(bean);
				
				if(beancomp!=null)
				{
					if(beancomp.getTipoPago().equals(Constantes.PAGO_EFECTIVO))
					{
						ExpressoHttpSessionBean.getRequest(request).setAttribute("cheq", null);
					}
					else if(beancomp.getTipoPago().equals(Constantes.PAGO_CHEQUE))
					{
						ExpressoHttpSessionBean.getRequest(request).setAttribute("cheq", "x");
					}
				}
				
				PagoBean pbean = new PagoBean();
				pbean.setTpo_pago(request.getParameter("tipopago").substring(0,1).toUpperCase());
				if(beancomp!=null)
				{
					pbean.setAbono_id(beancomp.getAbono_id());
					if(pbean.getTpo_pago().equals(Constantes.PAGO_CHEQUE)||pbean.getTpo_pago().equals(Constantes.PAGO_EFECTIVO))
					{
						beancomp.setContratoId(null);
						beancomp.setUserId(null);
						if(sol.getSolicitanteBean().getTpo_pers().equals(Constantes.PERSONA_NATURAL))
						{
							beancomp.setNombreEntidad(sol.getSolicitanteBean().getApe_pat()+ " " +sol.getSolicitanteBean().getApe_mat()+ " " + sol.getSolicitanteBean().getNombres());
						}
						else
						{
							beancomp.setNombreEntidad(sol.getSolicitanteBean().getRaz_soc());
						}
						
					}
				}
				else
				{
					beancomp = new ComprobanteBean();
					beancomp.setTipoPago("Linea prepago");
					beancomp.setOficina("WEB");
					beancomp.setCajero("WEB");
					beancomp.setFecha_hora(FechaUtil.getCurrentDateTime());
					beancomp.setMonto(sol.getTotal());
					beancomp.setUserId(bean.getUserId());
					beancomp.setNombreEntidad(bean.getApePat()+ " " + bean.getApeMat() + " " +bean.getNombres());
					
				}
				beancomp.setSolicitudId(sol.getSolicitud_id());
				beancomp.setTipoPub("C");
				pbean.setMonto(sol.getTotal());
				pbean.setSolicitud_id(sol.getSolicitud_id());
				pbean.setUsr_crea(bean.getUserId());
				pbean.setUsr_modi(bean.getUserId());
				sol.setPagoBean(pbean);
				
				System.out.println("SolicitudController.runGuardarDatosComplementariosState 2");
				if(!sol.getPagoBean().getTpo_pago().equals(Constantes.PAGO_TARJETA_DE_CREDITO))
				{
					sol.grabarPago();
				
					
					LogAuditoriaCertificadoBean bt = new LogAuditoriaCertificadoBean();
							
					bt.setOficRegId(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_id());
					bt.setRegPubId(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getReg_pub_id());
					bt.setSolicitud_id(sol.getSolicitud_id());
					bt.setTipoCertificado(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_id());
					sol.recuperaServicio();
					bt.setCodigoServicio(Integer.parseInt(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getServicio_id()));
					bt.setCodigoGLA(Integer.parseInt(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCod_GLA()));
					bt.setUsuarioSession(bean);
                    bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));             
                    bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
                    
                    System.out.println("SolicitudController.runGuardarDatosComplementariosState 3");

					int numpag = 1;
					try
					{
						numpag = Integer.parseInt(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_pag());
						String numeroPaginas = ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPaginasAnterior();
						if(numeroPaginas != null){
							numpag = numpag + Integer.parseInt(numeroPaginas);
						}
					}
					catch (NumberFormatException nfe)
					{
						
					}
					PrepagoBean beanPrepago=null;
					if(numpag>1)
						bt.setCantidad(numpag+"");
					if (Propiedades.getInstance().getFlagTransaccion()==true){
						beanPrepago=Transaction.getInstance().registraTransaccion(bt,conn);						
						sol.grabarConsumoSol(bt.getConsumoId(),false);
					}
					
					UsuarioBean cliente = new UsuarioBean();
									
					if(bt.getUsuarioSession().getPerfilId()==Constantes.PERFIL_CAJERO)
					{
						cliente.setFgIndividual(true);
						cliente.setFgInterno(false);
						cliente.setRegPublicoId(bt.getRegPubId());
					}
					
					Job004 j = new Job004();
					if(bt.getUsuarioSession().getPerfilId()==Constantes.PERFIL_CAJERO)
					{
						j.setUsuario(cliente);
					}
					else
					{
						j.setUsuario(bean);
					}
					j.setCodigoServicio(bt.getCodigoServicio());
					j.setRegPubId(bt.getRegPubId());
					j.setOficRegId(bt.getOficRegId());
					j.setArea(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getArea_reg_id());
					try{
						//j.setCostoServicio(Double.parseDouble(bt.getConsumoId()));
						j.setCostoServicio(beanPrepago.getMontoBruto());
					}catch(Exception e){
						e.printStackTrace();
					}
					
					Thread llamador1 = new Thread(j);
					llamador1.start();
					
					if(bDesti.getTpo_env().equals(Constantes.TIPO_DOMICILIO))
					{
						bt.setCodigoServicio(TipoServicio.DELIVERY_CERT);
						bt.setCodigoGLA(0);
						bt.setCantidad("1");
						if (Propiedades.getInstance().getFlagTransaccion()==true)
						{
							beanPrepago=Transaction.getInstance().registraTransaccion(bt,conn);
							sol.grabarConsumoSol(bt.getConsumoId(),true);
						}
						
						j = new Job004();
						if(bt.getUsuarioSession().getPerfilId()==Constantes.PERFIL_CAJERO)
						{
							j.setUsuario(cliente);
						}
						else
						{
							j.setUsuario(bean);
						}
						j.setCodigoServicio(bt.getCodigoServicio());
						j.setRegPubId(bt.getRegPubId());
						j.setOficRegId(bt.getOficRegId());
						j.setArea(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getArea_reg_id());
						try{
							j.setCostoServicio(beanPrepago.getMontoBruto());
						}catch(Exception e){
							e.printStackTrace();
						}
						Thread llamador2 = new Thread(j);
						llamador2.start();
						
					}
					
					sol.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_VERIFICAR);
					System.out.println("SolicitudController.runGuardarDatosComplementariosState 4");
				}
				else
				{
					System.out.println("SolicitudController.runGuardarDatosComplementariosState 3");
					sol.actualizaEstadoSol(Constantes.ESTADO_SOL_POR_PAGAR);
					PagoTarjeta pagoT = new PagoTarjeta();
					pagoT.getPtbean().setMedioId("1");
					pagoT.getPtbean().setMonto(sol.getTotal());
					pagoT.getPtbean().setNumItems("1");
					pagoT.getPtbean().setUserId(bean.getUserId());
					pagoT.getPtbean().setPersonaId(bean.getPersonaId());
					pagoT.getPtbean().setNumero(request.getParameter("txtPan"));
					pagoT.getPtbean().setAno(request.getParameter("cboYear"));
					pagoT.getPtbean().setMes(request.getParameter("cboMonth"));
					pagoT.getPtbean().setSolicitudId(sol.getSolicitud_id());
					//HttpSession session = ExpressoHttpSessionBean.getSession(request);
					pagoT.efectuaAbono(conn);
					System.out.println("SolicitudController.runGuardarDatosComplementariosState 4");
					
					session.setAttribute("numOrden", pagoT.getPtbean().getNumOrden());
					//if (isTrace(this)) System.out.println("Generando orden: " + numOrden);
						
					Output urlParams = new Output("urlParams", pagoT.getPtbean().getUrl());
					
					response.addOutput(urlParams);
					ExpressoHttpSessionBean.getSession(request).setAttribute("solicitudId", sol.getSolicitud_id());
					tarj = true;
				}
				//conn.commit();
				
				/****** Inicio:jrosas:04/06/2007 **/
				System.out.println("SolicitudController.runGuardarDatosComplementariosState 5 " + ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert());
				
				StringBuffer descrip = new StringBuffer();
				descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getDesc_certi());
				if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_NEGATIVO))// habra tipo cert rmc 
				{
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getCertificado_id().equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS)){
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca().equals("") ){
							descrip.append("<BR>");
							descrip.append("NRO PLACA: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca());
						}
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombreBien() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombreBien().equals("") ){
							descrip.append("<BR>");
							descrip.append("NOMBRE BIEN: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombreBien());
						}
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula().equals("") ){
							descrip.append("<BR>");
							descrip.append("NRO MATRICULA: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula());
						}
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie().equals("") ){
							descrip.append("<BR>");
							descrip.append("NRO SERIE: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie());
						}
					}else{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
						{
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
							if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals("")){
								descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
							}
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
						}
						else
						{
							if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("J")){
								descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
							}	
						}
					}	
				}
				
				// certificado de gravamen
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_RMC)){ //gravemn y vigencia=R
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals(""))
						{
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
						}
						descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
					}
					else
					{
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("J")){
							descrip.append("<BR>");
							descrip.append("PARTICIPANTE: ");
							
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
						}	
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getSiglas() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getSiglas().equals("") ){
						descrip.append("<BR>");
						descrip.append("SIGLAS: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getSiglas());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equals("") ){
						descrip.append("<BR>");
						descrip.append("TIPO PARTICIPANTE: ");
						descrip.append(desTipoParticipante);
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante());
					}
					if(desNumDoc != null && !desNumDoc.equals("") ){
						descrip.append("<BR>");
						descrip.append(desNumDoc);
						descrip.append(" : ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroDocumento());//
						desNumDoc=null;
					}
				}
				
				// certificado de Registro condicionado
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_MOBILIARIO_CONDICIONADO)){
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals("")){
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
						}
						descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
					}
					else
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
					}
					
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equals("") ){
						descrip.append("<BR>");
						descrip.append("TIPO DE PARTICIPACIÓN: ");
						descrip.append(desTipoParticipante);
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoRegistro() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoRegistro().equals("") ){
						descrip.append("<BR>");
						descrip.append("REGISTROS: ");
						descrip.append(desTipoRegistro);
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoRegistro());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFlagHistorico() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFlagHistorico().equals("") ){
						descrip.append("<BR>");
						descrip.append("HISTÓRICO: ");
						if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFlagHistorico().equals("1"))
							descrip.append("SÍ");
						else
							descrip.append("NO");
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoDesde() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoDesde().equals("") ){
						descrip.append("<BR>");
						descrip.append("FECHA DE INSCRIPCIÓN DE ASIENTO: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoDesde());
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoHasta() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoHasta().equals("") ){
						descrip.append(" - ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getFechaInscripcionASientoHasta());
					}
					
				}
				//certificado de Registro Actos Vigentes
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_ACTOS_VIGENTES)||
						((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_MOBILIARIO_HISTORICO)){
					
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("N"))
					{
						descrip.append("<BR>");
						descrip.append("PARTICIPANTE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_pat());
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat()!=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat().trim().equals(""))
						{
							descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getApe_mat());
						}
						descrip.append(" ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNombres());
					}
					else
					{
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_pers().equals("J")){
							descrip.append("<BR>");
							descrip.append("PARTICIPANTE: ");
							descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getRaz_soc());
						}	
					}
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante() !=null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante().equals("") ){
						descrip.append("<BR>");
						descrip.append("TIPO DE PARTICIPACIÓN: ");
						descrip.append(desTipoParticipante);
						//descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoParticipante());
					}	
				}
				// certificado de Registro Dominial RJB
				else if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_DOMINIAL_RJB)||
						((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_GRAVAMEN_RJB)){
					// repetedio mas abajo
					/*if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc().equals("")){
						descrip.append("<BR>");
						descrip.append("OFICINA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getOfic_reg_desc());
					}*/
					// solo para certificado dominial se concatenara el tipo de informacion de dominio
					if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_DOMINIAL_RJB)){
						if (((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoInformacionDominio() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoInformacionDominio().equals("") ){
							descrip.append("<BR>");
							descrip.append("TIPO DE INFORMACION DE DOMINIO: ");
							if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTipoInformacionDominio().equals("C"))
							descrip.append("COMPLETA");
							else
								descrip.append("ULTIMO PROPIETARIO");
						}	
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO PLACA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getPlaca());
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO MATRICULA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroMatricula());
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO PARTIDA: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida());
					}
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie().equals("") ){
						descrip.append("<BR>");
						descrip.append("NRO SERIE: ");
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroSerie());
					}
					
				}
				
				/***** Fin:jrosas:04/06/2007 *****/
				// copia literal
				else
				{
					System.out.println("SolicitudController.runGuardarDatosComplementariosState 6");
					 
					
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumPartida() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumPartida().trim().equals("")){
						
						descrip.append(". PARTIDA: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumPartida());
					}else{
						if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida().trim().equals("")){
							descrip.append(". PARTIDA: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPartida());
						}
					}
					
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNs_asie() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNs_asie().trim().equals(""))
						descrip.append(". ASIENTO: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNs_asie());
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu().trim().equals(""))
						descrip.append(". TITULO: ");
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getAa_titu() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getAa_titu().trim().equals(""))
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getAa_titu()).append(" - ");
					if(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu() != null && !((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu().trim().equals(""))
						descrip.append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_titu());
					//Incio:jascencio:09/08/2007
					//CC:REGMOBCON-2006
					String numeroPaginas = ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNum_pag();
					String numeroPaginasAnt = ((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getNumeroPaginasAnterior(); 
					if(numeroPaginasAnt != null && numeroPaginas != null ){
						Long numero = 0l;
						
						numero = Long.parseLong(numeroPaginas) + Long.parseLong(numeroPaginasAnt);
						numeroPaginas = String.valueOf(numero); 
						
					}
					System.out.println("SolicitudController.runGuardarDatosComplementariosState 7");
					descrip.append(". PAGINAS: ").append(numeroPaginas);
					//Fin:jascencio
				}
				// Inicio:mgarate:05/06/2007
				if(!((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getTpo_cert().equals(Constantes.CERTIFICADO_BUSQUEDA))
				{
					
				 descrip.append("<BR>");
				 descrip.append("OFICINA: ").append(((ObjetoSolicitudBean)sol.getObjetoSolicitudList(0)).getDesc_regis());
				 sol.setDescripcion(descrip.toString());	
				}else
				{
					CriterioBean crit = new CriterioBean();
					descrip.delete(0, descrip.length());
					descrip.append("CERTIFICADO DE BUSQUEDA ");
					crit = Tarea.recuperarCriterio(sol.getObjetoSolicitudList(0).getUrlBusqueda());

					if(!(crit.getApellidoParterno()==null || crit.getApellidoParterno().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Apellido Paterno : "+crit.getApellidoParterno());
					}
					if(!(crit.getApellidoMaterno()==null || crit.getApellidoMaterno().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Apellido Materno : "+crit.getApellidoMaterno());
					}
					if(!(crit.getNombre()==null || crit.getNombre().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Nombre : "+crit.getNombre());
					}
					if(!(crit.getChasis()==null || crit.getChasis().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Chasis : "+crit.getChasis());
					}
					if(!(crit.getFicha()==null || crit.getFicha().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Ficha : "+crit.getFicha());
					}
					if(!(crit.getTomo()==null || crit.getTomo().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Tomo : "+crit.getTomo());
					}
					if(!(crit.getFolio()==null || crit.getFolio().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Folio : "+crit.getFolio());
					}
					if(!(crit.getNombreBuque()==null || crit.getNombreBuque().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Nombre del Buque : "+crit.getNombreBuque());
					}
					if(!(crit.getNombreEmbarcacion()==null || crit.getNombreEmbarcacion().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Nombre de Embarcación : "+crit.getNombreEmbarcacion());
					}
					if(!(crit.getNumeroMatricula()==null || crit.getNumeroMatricula().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Numero de Matricula : "+crit.getNumeroMatricula());
					}
					if(!(crit.getNumeroMotor()==null || crit.getNumeroMotor().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Numero de Motor : "+crit.getNumeroMotor());
					}
					if(!(crit.getPartida()==null || crit.getPartida().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Numero de Partida : "+crit.getPartida());
					}
					if(!(crit.getPlaca()==null || crit.getPlaca().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Placa : "+crit.getPlaca());
					}
					if(!(crit.getRazonSocial()==null || crit.getRazonSocial().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Razon Social : "+crit.getRazonSocial());
					}if(!(crit.getSigla()==null || crit.getSigla().equals("")))
					{
						descrip.append("<BR>");
						descrip.append("Sigla : "+crit.getSigla());
					}
					if(!(crit.getRegistro()==null || crit.getRegistro().equals("")))
					{
						descrip.append("<BR>");
						String tipoRegistro = "";
						if(crit.getRegistro().equals("053"))
						{
							tipoRegistro = "LIBRO DE BUQUES";
						}else if(crit.getRegistro().equals("054"))
						{
							tipoRegistro = "REGISTRO DE BUQUES";
						}else if(crit.getRegistro().equals("038"))
						{
							tipoRegistro = "REGISTRO DE PROPIEDAD DE EMBARCACIONES PESQUERAS";
						}else if(crit.getRegistro().equals("088"))
						{
							tipoRegistro = "REGISTRO DE PROPIEDAD VEHICULAR";
						}else if(crit.getRegistro().equals("040"))
						{
							tipoRegistro = "REGISTRO DE AERONAVES";
						}else if(crit.getRegistro().equals("092"))
						{
							tipoRegistro = "LIBRO DE MOTORES DEL REGISTRO DE AERONAVES";	
						}
						descrip.append("Registro de : "+tipoRegistro);
					}
					sol.setDescripcion(descrip.toString());
					beancomp.setSolDesc(sol.getObjetoSolicitudList(0).getCriterioBusqueda());
				}
				// Fin:mgarate:05/06/2007
				if(!tarj)
				{  
					beancomp.setSolDesc(sol.getDescripcion());
					ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", beancomp);
				}
				else
				{
					ExpressoHttpSessionBean.getSession(request).setAttribute("solCertNeg", sol);
				}
				
			}
			if(tarj)
			{
				
				response.setStyle("pago");
			}
			else
			{
				ExpressoHttpSessionBean.getSession(request).removeAttribute("solCertNeg");
				req.setAttribute("arrCertificados", Tarea.getComboCertificados(conn));
				response.setStyle("compro");
			}
			System.out.println("SolicitudController.runGuardarDatosComplementariosState 8");
			conn.commit();
			System.out.println("SolicitudController.runGuardarDatosComplementariosState 9-->" + response.getStyle());
			
			// le decimos al singleton que la carga laboral del usuario se ha modificado
			try{
				MonitorCargaLaboral.getInstance().setEstadoCargaRegistrador(sol.getDatosRegisVerificadorBean().getCuentaId(), true);
				MonitorCargaLaboral.getInstance().setEstadoCargaRegistrador(sol.getDatosRegisEmisorBean().getCuentaId(), true);
			}catch (NullPointerException n) {
				if(isTrace(this)) System.out.println("WARNING. No se pudo recuperar el cuenta_id del registrador");
			}
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


	protected ControllerResponse runProcesarPagoSolicitudState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;
		
		try{
			init(request);
			validarSesion(request);
			
			String numopt = request.getParameter("numopt");
			String numdoc = request.getParameter("numdoc");

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);

			muestraDocsId(request, dconn);
			
			if (numdoc == null || numdoc.trim().length() <= 0)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Debe ingresar un número de Documento.", "errorPrepago");
				
			MultiDBObject multi = new MultiDBObject(dconn);

			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboCuenta", "cuenta");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeNatu", "penatu");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");

			multi.setForeignKey("cuenta", DboCuenta.CAMPO_PE_NATU_ID, "penatu", DboPeNatu.CAMPO_PE_NATU_ID);
			multi.setForeignKey("penatu", DboPeNatu.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "cuenta", DboCuenta.CAMPO_CUENTA_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			
			multi.setField("cuenta", DboCuenta.CAMPO_TIPO_USR, "11%");
			multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, numdoc);
			multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, numopt);//chequear
			multi.setField("persona", DboPersona.CAMPO_TPO_PERS, "N");
			multi.setField("cuenta", DboCuenta.CAMPO_ESTADO, "1"); //18Sept
			multi.setField("linprepag", DboLineaPrepago.CAMPO_ESTADO, "1");//19Sept
			multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO,"1");
				

			java.util.List lista = new java.util.ArrayList();
			
			AbonoVentanillaBean bean = null;
			
			boolean encontro = false;
			
			for(Iterator i = multi.searchAndRetrieve().iterator(); i.hasNext();)
			{
				MultiDBObject oneMulti = (MultiDBObject) i.next();
				
				bean = new AbonoVentanillaBean();

				bean.setUsuarioId(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
				bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
				bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
				bean.setNombre(oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_PAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_APE_MAT) + " " + oneMulti.getField("penatu", DboPeNatu.CAMPO_NOMBRES));
				bean.setAfil_organiz(null);
				bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
				lista.add(bean);
				encontro = true;
			}
			
			if(!encontro){//propio
			multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");

			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("linprepag", DboLineaPrepago.CAMPO_PE_JURI_ID, "pejuri", DboPeJuri.CAMPO_PE_JURI_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			
			//multi.setField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV, "RUC");
			
			multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, numdoc);
			multi.setField("persona", DboPersona.CAMPO_TIPO_DOC_ID, numopt);

			multi.setField("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "is null");//cb
			multi.setField("pejuri", DboPeJuri.CAMPO_TIPO_ORG, "0");
			multi.setField("persona", DboPersona.CAMPO_TPO_PERS, "J");
			multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO, "1");


			
			for(Iterator i = multi.searchAndRetrieve().iterator(); i.hasNext();)
			{
				MultiDBObject oneMulti = (MultiDBObject) i.next();
				
				bean = new AbonoVentanillaBean();

				//bean.setUsuarioId(oneMulti.getField("cuenta", DboCuenta.CAMPO_USR_ID));
				bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
				bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
				bean.setNombre(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
				bean.setAfil_organiz(null);
				bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
				lista.add(bean);
				encontro = true;
				ExpressoHttpSessionBean.getRequest(request).setAttribute("organi", "X");
			}
			}			
			if(encontro)
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", lista);
			else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", null);
				
			response.setStyle("muestra");
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


	protected ControllerResponse runEnviarEmailState(
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
			
			String numopt = request.getParameter("numopt");
			String numdoc = request.getParameter("numdoc");
			String razsoc = request.getParameter("razsoc");
			String ruc = request.getParameter("ruc");
			String nombreRuc = request.getParameter("nombreRUC"); //esto es para que el como se llama el campo del RUC sea igual a la base de datos

			muestraDocsId(request, dconn);
			
			boolean paseRuc = true;
			boolean paseRS = true;
			
			if (razsoc == null || razsoc.trim().length() <= 0)
				paseRS = false;

			if(ruc == null || ruc.trim().length() <= 0)
				paseRuc = false;
			
			if (!paseRuc && !paseRS)
				throw new CustomException(Constantes.EC_MISSING_PARAM, "Debe ingrear algun valor ya sea en el campo Razon Social o RUC.", "errorPrepago");


			MultiDBObject multi = new MultiDBObject(dconn);
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPeJuri", "pejuri");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPersona", "persona");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmDocIden", "tipodoc");
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboLineaPrepago", "linprepag");
			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PERSONA_ID, "persona", DboPersona.CAMPO_PERSONA_ID);
			multi.setForeignKey("persona", DboPersona.CAMPO_TIPO_DOC_ID, "tipodoc", DboTmDocIden.CAMPO_TIPO_DOC_ID);
			multi.setForeignKey("pejuri", DboPeJuri.CAMPO_PE_JURI_ID, "linprepag", DboLineaPrepago.CAMPO_PE_JURI_ID);
			multi.setField("pejuri", DboPeJuri.CAMPO_TIPO_ORG, "0");
			multi.setField("linprepag", DboLineaPrepago.CAMPO_CUENTA_ID, "is null");//cb
			multi.setField("linprepag", DboLineaPrepago.CAMPO_ESTADO, "1");//19Sept
			multi.setField("tipodoc", DboTmDocIden.CAMPO_ESTADO, "1");

			if(paseRuc){
				multi.setField("persona", DboPersona.CAMPO_NUM_DOC_IDEN, ruc);
				multi.setField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV, nombreRuc);
			}
			
			if(paseRS)
				multi.setAppendWhereClause(" PE_JURI." + DboPeJuri.CAMPO_RAZ_SOC + " LIKE '" + razsoc + "%'");
				//multi.setField("pejuri", DboPeJuri.CAMPO_RAZ_SOC, razsoc);

			java.util.List lista = new java.util.ArrayList();
			AbonoVentanillaBean bean = null;
				
			java.util.List l = multi.searchAndRetrieve();
				
			if(l.size() > 0)
				{
				for(Iterator i = l.iterator(); i.hasNext();){
					MultiDBObject oneMulti = (MultiDBObject) i.next();
						
					bean = new AbonoVentanillaBean();
					bean.setUsuarioId(null);
					bean.setTipo_doc(oneMulti.getField("tipodoc", DboTmDocIden.CAMPO_NOMBRE_ABREV));
					bean.setNum_doc(oneMulti.getField("persona", DboPersona.CAMPO_NUM_DOC_IDEN));
					bean.setNombre(oneMulti.getField("pejuri", DboPeJuri.CAMPO_RAZ_SOC));
					bean.setAfil_organiz(null);
					bean.setLineaPrepago(oneMulti.getField("linprepag", DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
					lista.add(bean);
				}
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", lista);
			}else
				ExpressoHttpSessionBean.getRequest(request).setAttribute("listaUsuarios", null);

			ExpressoHttpSessionBean.getRequest(request).setAttribute("organi", "X");
			response.setStyle("muestra");
		} catch (CustomException ce) {
			log(ce.getCodigoError(), ce.getMessage(), ce, request);
			principal(request);
			rollback(conn, request);
			response.setStyle(ce.getForward());
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

