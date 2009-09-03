package gob.pe.sunarp.iri.publicidad.controller;

//paquetes del sistema
import javax.media.jai.TiledImage;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.misc.*;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import gob.pe.sunarp.extranet.pool.*;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;


//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.framework.session.*;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoIP;
import gob.pe.sunarp.extranet.acceso.util.ControlAccesoSesion;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.LoggeableException;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.common.SystemResources;
import gob.pe.sunarp.extranet.common.cm.CMProcessor;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.reportes.beans.TransaccionBean;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.publicidad.certificada.Certificado;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarPartidaDirectaService;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarPartidaDirectaServiceImpl;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.transaction.bean.*;


public class ConsultarPartidaController
	extends ControllerExtension
	implements Constantes {
	private String thisClass = ConsultarPartidaController.class.getName() + ".";

	public static final String ATRIBUTO_ASIENTOS_VISTOS = "ASIENTOSVISTOS";
	public static final String ATRIBUTO_PARTIDA_EN_VISUALIZACION = "PARTIDAENVISUALIZACION";
	public static final String ATRIBUTO_PARTIDA_RN_EN_VISUALIZACION = "PARTIDAREFNUMENVISUALIZACION";
	public static final String ATRIBUTO_OFIC_REG_ID = "OFICREG";
	public static final String ATRIBUTO_OFIC_REG_NOMBRE = "OFICREGNOMBRE";
	//Tarifario
	public static final String ATRIBUTO_COD_LIBRO = "CODLIBRO";
	public static final String ATRIBUTO_COD_GLA = "CODGLA";
	public static final String ATRIBUTO_TITULOS_PENDIENTES = "TITUPEND";
	public static final String ATRIBUTO_REG_PUB_ID = "REGPUB";
	public static final String ATRIBUTO_PARTIDA_TMSTMP_SYNCHRO = "TMSTMPSYNCHRO";
	public static final String ATRIBUTO_AREA_REGISTRAL = "AREAREGISTRAL";
	public static final String ATRIBUTO_ZOOM_DEFAULT = "ZOOMDEFAULT";


	public static final String SERVICIO_VISUALIZAR_PARTIDA = "70";
	public static final String SERVICIO_COPIA_CERTIFICADA = "110";
	public static final String SERVICIO_COPIA_CERTIFICADA_1 = "111";
	public static final String SERVICIO_COPIA_CERTIFICADA_2 = "112";
	public static final String EC_SALDO_INSUFICIENTE = "E95101";
	public static final String EC_TARIFA_NO_DISPONIBLE = "E95102";
	public static final String EC_NO_TODAS_IMAGENES_DISPONIBLES = "E95103";
	public static final String EC_NO_TIENE_IMAGENES = "E95104";
	public static final String EC_REPLICACION_PENDIENTE = "E95105";
	public static final String EC_INDICES_INCOMPLETOS = "E95106";
	public static final String IMAGEN_ERROR_GENERICO = "/images/vp_errorGenerico.gif";
	public static final String IMAGEN_ERROR_SALDO_INSUFICIENTE = "/images/vp_saldoInsuficiente.gif";


	public ConsultarPartidaController() 
	{
		super();
		addState(new State("solicitarFormulario", "muestra formulario de busqueda"));
		addState(new State("buscarXNroPartida", "c1"));
		addState(new State("buscarXFicha", "c2"));
		addState(new State("buscarXTomoFolio", "c3"));
		addState(new State("visualizaPartida", "c4"));
		addState(new State("muestraUsuarioMasSaldo", "c5"));
		addState(new State("muestraIndiceAsientos", "c6"));
		addState(new State("muestraImagen", "c7"));
		addState(new State("muestraTituloVisualizacion", "c8"));
		addState(new State("impresion", "c8"));
		addState(new State("buscarXPlaca", "c8"));		
		addState(new State("buscarXPlacaDet", "c8"));		
		addState(new State("muestraHistMotor", "Muestra historico de Motores"));		
		addState(new State("muestraHistPropi", "Muestra historico de Propietarios"));	
		addState(new State("buscarXFichaRMC", "Busqueda Directa RMC por Ficha"));
		addState(new State("buscarXNroPartidaRMC", "Busqueda Directa RMC por Partida"));
		addState(new State("buscarXTomoFolioRMC", "Busqueda Directa RMC por Tomo Folio"));
		addState(new State("detallePartidaRMC", "Detalle de la partida Rmc"));
		
		setInitialState("solicitarFormulario");
	}

	public ControllerResponse runMuestraHistMotorState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try
		{
			init(request);
			validarSesion(request);
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			HistMotorBean bmotor = new HistMotorBean();
			ArrayList lmotor = new ArrayList();
			
			//_empieza busqueda
			StringBuffer q  = new StringBuffer();
			q.append("select NUM_PLACA, NS_PLACA, NUM_SERIE, NUM_MOTOR from VEHICULO_HIST where REFNUM_PART=").append(req.getParameter("refnum"));
			if (isTrace(this))
				System.out.println("___verqueryCOUNT_A___"+q.toString());
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			//boolean b = rset.next();
			String aux = null;
			/**
			if (!b)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			**/
			while(rset.next())
			{
				bmotor = new HistMotorBean();
				aux = rset.getString("NUM_PLACA");
				if(aux!=null)
					bmotor.setPlaca(aux);
				aux = rset.getString("NS_PLACA");
				if(aux!=null)
					bmotor.setSecuencial(aux);
				aux = rset.getString("NUM_SERIE");
				if(aux!=null)
					bmotor.setSerie(aux);
				aux = rset.getString("NUM_MOTOR");
				if(aux!=null)
					bmotor.setMotor(aux);
				lmotor.add(bmotor);
				
			}
			if(lmotor.size()>0)
				req.setAttribute("bmotor",lmotor);
			response.setStyle("muestraHistMotor");
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		} catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
			pool.release(conn);			
			end(request);
		}
		
		return response;
	}

	public ControllerResponse runMuestraHistPropiState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try
		{
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			HistPropietarioBean bpropi = new HistPropietarioBean();
			
			ArrayList lpropi = new ArrayList();
			// correccion no se visualiza propietarios historicos
			//_empieza busqueda
			StringBuffer q  = new StringBuffer();
			q.append("SELECT /*+ORDERED*/ rownum sec, pn.ape_pat AS ape_pat, pn.ape_mat AS ape_mat, pn.nombres AS nombres, ")
				.append("pj.razon_social AS razon_social, ind.tipo_pers AS tipo_pers, ind.direccion AS direccion, ")
				//.append("doc1.nombre_abrev AS tipo_docu_nat, nu_doc_iden AS num_docu_nat, doc2.nombre_abrev AS tipo_docu_jur, nu_doc AS num_docu_jur, ti.ts_present AS fecha ")
				.append("doc1.nombre_abrev AS tipo_docu_nat, nu_doc_iden AS num_docu_nat, doc2.nombre_abrev AS tipo_docu_jur, nu_doc AS num_docu_jur ")
				//.append("FROM user1.ind_prtc ind, user1.prtc_nat pn , user1.prtc_jur pj, user1.titulo ti, user1.tm_doc_iden doc1, user1.tm_doc_iden doc2 ")
				.append("FROM user1.ind_prtc ind, user1.prtc_nat pn , user1.prtc_jur pj, user1.tm_doc_iden doc1, user1.tm_doc_iden doc2 ")
				.append("WHERE ind.refnum_part = '")
				.append(req.getParameter("refnum"))
				.append("' AND ind.cod_partic = '")
				.append(gob.pe.sunarp.extranet.util.Constantes.PROPIETARIO_VEHI)
				.append("' ") 
				.append("AND ind.estado != '1' ")
				.append("AND pn.cur_prtc(+)=ind.cur_prtc AND pn.reg_pub_id(+)='")
				.append(req.getParameter("regpub"))
				.append("' AND pn.ofic_reg_id(+)='")
				.append(req.getParameter("ofireg"))
				.append("' ")
				.append("AND pj.cur_prtc(+)=ind.cur_prtc AND pj.reg_pub_id(+)='")
				.append(req.getParameter("regpub"))
				.append("' AND pj.ofic_reg_id(+)='")
				.append(req.getParameter("ofireg"))
				.append("' ")
				//.append("AND ti.ano_titu=ind.aa_titu AND ti.num_titu = ind.nu_titu AND ti.reg_pub_id = '")
				//.append(req.getParameter("regpub"))
				//.append("' ")
				//.append("AND ti.ofic_reg_id='")
				//.append(req.getParameter("ofireg"))
				//.append("' AND ti.area_reg_id='")
				.append(gob.pe.sunarp.extranet.util.Constantes.AREA_PROPIEDAD_VEHICULAR)
				.append("' ")
				.append("AND pn.ti_doc_iden = doc1.tipo_doc_id(+) AND pj.ti_doc = doc2.tipo_doc_id(+)");
			
			if (isTrace(this))
				System.out.println("___verqueryCOUNT_A___"+q.toString());
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			//boolean b = rset.next();
			String aux = null;
			String tip = null;
			StringBuffer sb = new StringBuffer();
			
			/**
			if (!b)
				throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			**/
			while(rset.next())
			{
				bpropi = new HistPropietarioBean();
				tip = rset.getString("TIPO_PERS");
				if(tip.equals("J"))
				{
					aux = rset.getString("RAZON_SOCIAL");
					if(aux!=null)
						bpropi.setNombre(aux);
					
					sb.delete(0,sb.length());
					aux = rset.getString("TIPO_DOCU_JUR");
					if(aux!=null)
						sb.append(aux);
					aux = rset.getString("NUM_DOCU_JUR");
					if(aux!=null)
						sb.append(" ").append(aux);
					bpropi.setDocumentos(sb.toString());
					
				}
				else
				{
					sb.delete(0,sb.length());
					aux = rset.getString("APE_PAT");
					if(aux!=null)
						sb.append(aux);
					aux = rset.getString("APE_MAT");
					if(aux!=null)
						sb.append(" ").append(aux);
					aux = rset.getString("NOMBRES");
					if(aux!=null)
						sb.append(", ").append(aux);
					bpropi.setNombre(sb.toString());
					
					sb.delete(0,sb.length());
					aux = rset.getString("TIPO_DOCU_NAT");
					if(aux!=null)
						sb.append(aux);
					aux = rset.getString("NUM_DOCU_NAT");
					if(aux!=null)
						sb.append(" ").append(aux);
					bpropi.setDocumentos(sb.toString());
				}
				aux = rset.getString("DIRECCION");
				if(aux!=null)
					bpropi.setDirecciones(aux);
				aux = rset.getString("FECHA");
				if(aux!=null)
					bpropi.setFechaInsc(aux.substring(0,19));
				lpropi.add(bpropi);
				
			}
			if(lpropi.size()>0)
				req.setAttribute("bpropi",lpropi);
			
			response.setStyle("muestraHistPropi");
			
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		} catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
			pool.release(conn);			
			end(request);
		}
		
		return response;
	}


	public ControllerResponse runVisualizaPartidaState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException 
	{
		//Fecha: 2003-07-31
		//Autor: HP
		//Descripcion: Envia mensaje por email cuando la partida está incompleta
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
					
		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			if (isTrace(this)) trace("Limpiamos los asientos vistos", request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean ub = ExpressoHttpSessionBean.getUsuarioBean(request);
			
			AsientosVistos av = (AsientosVistos)session.getAttribute(ATRIBUTO_ASIENTOS_VISTOS);
			if (av != null) 
				av.limpiar();
		
			if (isTrace(this)) trace("Limpiamos el dato de oficina registral de sesion", request);
			session.removeAttribute(ATRIBUTO_OFIC_REG_NOMBRE);
			//Tarifario
			session.removeAttribute(ATRIBUTO_COD_LIBRO);
			session.removeAttribute(ATRIBUTO_COD_GLA);
			
			// publicidad certificada
			session.removeAttribute(ATRIBUTO_TITULOS_PENDIENTES);

			if (isTrace(this)) trace("Validamos que se haya pasado el parametro obligatorio", request);
			
			String sRefNum = request.getParameter("refnum_part");
			
			if (sRefNum == null) 
				throw new CustomException(Errors.EC_MISSING_PARAM, "refnum_part es obligatorio.");
				
			try {
				long refnumPart = Long.parseLong(sRefNum);
			} catch (Exception e) {
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "refnum_part no es número.");
			}
		
			//28diciembre2002		
			boolean partidaIncompleta=false;
			String mensajeErrorPartidaIncompleta="";

			if (isTrace(this)) trace("Validamos que la partida no tengo estado 3 (CARGA) o 4 (PAMA)", request);
			DboPartida dboPartida = new DboPartida(dconn);
			dboPartida.clearAll();
			dboPartida.setField(DboPartida.CAMPO_REFNUM_PART, sRefNum);
			//dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_ESTADO);
			if (!dboPartida.find()) {
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' no existe ", "errorMotivosTecnicos");
			}
			if ((Integer.parseInt(dboPartida.getField(DboPartida.CAMPO_ESTADO)) == 3)||(Integer.parseInt(dboPartida.getField(DboPartida.CAMPO_ESTADO)) == 4)) {
				DboOficRegistral dboOficina = new DboOficRegistral(dconn);
				dboOficina.setFieldsToRetrieve(dboOficina.CAMPO_NOMBRE);
				dboOficina.setField(dboOficina.CAMPO_REG_PUB_ID,dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID));
				dboOficina.setField(dboOficina.CAMPO_OFIC_REG_ID,dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID));
				if (!dboOficina.find())
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' sin oficina conocida", "errorMotivosTecnicos");
				mensajeErrorPartidaIncompleta = (new StringBuffer("Atención: Administrador de Jurisdicción "))
												.append("La Partida ")
												.append(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA))
												.append(" del Area ")
												.append(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID))
												.append(" de la Oficina ")
												.append(dboOficina.getField(dboOficina.CAMPO_NOMBRE))
												.append(" requiere ser corregida y sincronizada a la Bodega Central,")
												.append(" un usuario de la Extranet recibió un mensaje de error al momento")
												.append(" de accederla.")
												.toString();
				if (ub.getFgInterno()) {
					partidaIncompleta=true;
					// enviar mensaje por e-mail
					log((Integer.parseInt(dboPartida.getField(DboPartida.CAMPO_ESTADO)) == 3)?
						EC_INDICES_INCOMPLETOS:EC_REPLICACION_PENDIENTE,
						mensajeErrorPartidaIncompleta, 
						request);
				} else {
					throw new CustomException(
								(Integer.parseInt(dboPartida.getField(DboPartida.CAMPO_ESTADO)) == 3)?
									EC_INDICES_INCOMPLETOS:EC_REPLICACION_PENDIENTE,
								mensajeErrorPartidaIncompleta,
								"errorMotivosTecnicos");
				}
			}
			

			if (isTrace(this)) trace("Validamos que exista al menos una imagen que mostrar", request);
			DboAsiento asiento = new DboAsiento(dconn);
			DboFicha ficha = new DboFicha(dconn);
			DboTomoFolio folio = new DboTomoFolio(dconn);
			
			asiento.setField(DboAsiento.CAMPO_REFNUM_PART, sRefNum);
			if (asiento.count() == 0 ) {

				ficha.setField(DboFicha.CAMPO_REFNUM_PART, sRefNum);
				if (ficha.count() == 0) {

					folio.setField(DboTomoFolio.CAMPO_REFNUM_PART, sRefNum);
					if (folio.count() == 0) {

						if (ub.getFgInterno())
							partidaIncompleta=true;
						else
							throw new CustomException(EC_NO_TIENE_IMAGENES, "", "errorMotivosTecnicos");
					}
				}
			}


			trace("Validamos que existan todas las imagenes que se deben mostrar", request);
			asiento.clearAll();
			asiento.setFieldsToRetrieve(DboAsiento.CAMPO_ID_IMG_ASIENTO);
			asiento.setField(DboAsiento.CAMPO_REFNUM_PART, sRefNum);
			asiento.setField(DboAsiento.CAMPO_ID_IMG_ASIENTO, "0");
			ArrayList array = asiento.searchAndRetrieveList();
			if (array.size() != 0) 
				{
					if (ub.getFgInterno())
						partidaIncompleta=true;
					else
					{
						//Solo si ocurre error, recupero los datos de la Partida que deben ir en el Log de Eventos
						dboPartida.clearAll();
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_OFIC_REG_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_AREA_REG_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_CO_LIBR_ORIG);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
						dboPartida.setField(DboPartida.CAMPO_REFNUM_PART, sRefNum);
						if (!dboPartida.find())
							throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' no existe ", "errorMotivosTecnicos");
						DboOficRegistral dboOficina = new DboOficRegistral(dconn);
						dboOficina.setFieldsToRetrieve(dboOficina.CAMPO_NOMBRE);
						dboOficina.setField(dboOficina.CAMPO_REG_PUB_ID,dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID));
						dboOficina.setField(dboOficina.CAMPO_OFIC_REG_ID,dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID));
						if (!dboOficina.find())
							throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' sin oficina conocida", "errorMotivosTecnicos");

						mensajeErrorPartidaIncompleta = (new StringBuffer("Atención: Administrador de Jurisdicción "))
												.append("La Partida ")
												.append(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA))
												.append(" del Area ")
												.append(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID))
												.append(" de la Oficina ")
												.append(dboOficina.getField(dboOficina.CAMPO_NOMBRE))
												.append(" requiere ser corregida y sincronizada a la Bodega Central,")
												.append(" un usuario de la Extranet recibió un mensaje de error al momento")
												.append(" de accederla.")
												.toString();
						throw new CustomException(EC_NO_TODAS_IMAGENES_DISPONIBLES,
												mensajeErrorPartidaIncompleta,
												"errorMotivosTecnicos");
					}
				}


			ficha.clearAll();
			ficha.setFieldsToRetrieve(DboFicha.CAMPO_ID_IMG_FICHA);
			ficha.setField(DboFicha.CAMPO_REFNUM_PART, sRefNum);
			ficha.setField(DboFicha.CAMPO_ID_IMG_FICHA, "0");
			array = ficha.searchAndRetrieveList();
			System.out.println("array.size()---> " + array.size() + " sRefNum-->"+sRefNum);
			if (array.size() != 0) 
				{
					if (ub.getFgInterno())
						partidaIncompleta=true;
					else
					{
						//Solo si ocurre error, recupero los datos de la Partida que deben ir en el Log de Eventos
						dboPartida.clearAll();
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_OFIC_REG_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_AREA_REG_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_CO_LIBR_ORIG);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
						dboPartida.setField(DboPartida.CAMPO_REFNUM_PART, sRefNum);
						if (!dboPartida.find())
							throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' no existe ", "errorMotivosTecnicos");
						DboOficRegistral dboOficina = new DboOficRegistral(dconn);
						dboOficina.setFieldsToRetrieve(dboOficina.CAMPO_NOMBRE);
						dboOficina.setField(dboOficina.CAMPO_REG_PUB_ID,dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID));
						dboOficina.setField(dboOficina.CAMPO_OFIC_REG_ID,dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID));
						if (!dboOficina.find())
							throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' sin oficina conocida", "errorMotivosTecnicos");

						mensajeErrorPartidaIncompleta = (new StringBuffer("Atención: Administrador de Jurisdicción "))
												.append("La Partida ")
												.append(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA))
												.append(" del Area ")
												.append(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID))
												.append(" de la Oficina ")
												.append(dboOficina.getField(dboOficina.CAMPO_NOMBRE))
												.append(" requiere ser corregida y sincronizada a la Bodega Central,")
												.append(" un usuario de la Extranet recibió un mensaje de error al momento")
												.append(" de accederla.")
												.toString();
						throw new CustomException(EC_NO_TODAS_IMAGENES_DISPONIBLES,
												mensajeErrorPartidaIncompleta,
												"errorMotivosTecnicos");
					}
				}


				folio.clearAll();
				folio.setFieldsToRetrieve(DboTomoFolio.CAMPO_ID_IMG_FOLIO);
				folio.setField(DboTomoFolio.CAMPO_REFNUM_PART, sRefNum);
				folio.setField(DboTomoFolio.CAMPO_ID_IMG_FOLIO, "0");
				array = folio.searchAndRetrieveList();
				if (array.size() != 0) 
				{
					if (ub.getFgInterno())
						partidaIncompleta=true;
					else						
					{
						//Solo si ocurre error, recupero los datos de la Partida que deben ir en el Log de Eventos
						dboPartida.clearAll();
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_OFIC_REG_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_AREA_REG_ID);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_CO_LIBR_ORIG);
						dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
						dboPartida.setField(DboPartida.CAMPO_REFNUM_PART, sRefNum);
						if (!dboPartida.find())
							throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' no existe ", "errorMotivosTecnicos");
						DboOficRegistral dboOficina = new DboOficRegistral(dconn);
						dboOficina.setFieldsToRetrieve(dboOficina.CAMPO_NOMBRE);
						dboOficina.setField(dboOficina.CAMPO_REG_PUB_ID,dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID));
						dboOficina.setField(dboOficina.CAMPO_OFIC_REG_ID,dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID));
						if (!dboOficina.find())
							throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + sRefNum + "' sin oficina conocida", "errorMotivosTecnicos");

						mensajeErrorPartidaIncompleta = (new StringBuffer("Atención: Administrador de Jurisdicción "))
												.append("La Partida ")
												.append(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA))
												.append(" del Area ")
												.append(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID))
												.append(" de la Oficina ")
												.append(dboOficina.getField(dboOficina.CAMPO_NOMBRE))
												.append(" requiere ser corregida y sincronizada a la Bodega Central,")
												.append(" un usuario de la Extranet recibió un mensaje de error al momento")
												.append(" de accederla.")
												.toString();
						throw new CustomException(EC_NO_TODAS_IMAGENES_DISPONIBLES,
												mensajeErrorPartidaIncompleta,
												"errorMotivosTecnicos");
					}
				}


			if (partidaIncompleta==true)
			{
				if (isTrace(this)) trace("partidaIncompleta true",request);
				session.setAttribute("partidaIncompleta","1");
			}
			else
				session.setAttribute("partidaIncompleta","0");


			response.setStyle("visualizaPartida");


		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		} catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			pool.release(conn);			
			end(request);
		}
		
		return response;
	}


	public ControllerResponse runMuestraUsuarioMasSaldoState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		//muestra el id de usuario y el saldo actualizado
		
		try {
			init(request);
			validarSesion(request);
		
			if (isTrace(this)) trace("Buscamos los datos de usuario y saldo de sesion", request);
			
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


			req.setAttribute("usuario",usuario.getUserId());
			req.setAttribute("saldo",new Double(usuario.getSaldo()));
		
			response.setStyle("muestraUsuarioMasSaldo");


		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			response.setStyle(e.getForward());
		} catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}
		
		return response;
		
	}


	public ControllerResponse runMuestraIndiceAsientosState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		//muestra el indice de asientos de la partida
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		try {
			init(request);
			validarSesion(request);


			//recupera datos basicos de la partida
			String refNumPart = req.getParameter("refnum_part");
			
			String accion = req.getParameter("accion");
			String objetoSolID = req.getParameter("objetoId");
			//accion=verifica, entonces debe insertar/actualizar las imagenes en las tablas de verificacion, antes de visualizarlas
			//accion=expide, entonces debe visualizar las imagenes desde las tablas de verificacion
			//accion=null, entonces sigue el procedimiento normal de visualizacion

			//validaciones
			if ( (refNumPart==null) || (refNumPart.trim().equalsIgnoreCase("")) )
				throw new CustomException(Errors.EC_MISSING_PARAM, "El identificador de la partida es obligatorio.");
				
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			if((accion==null)
			||((!accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA))
			&&(!accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE))))
				recuperaIndiceAsientosSimple(request, response, dconn);
			else
				recuperaIndiceAsientosCertificados(request, response, dconn);
			
			conn.commit();
			response.setStyle("muestraIndiceAsientos");


		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			pool.release(conn);
			end(request);
		}
		return response;
		}
		
	private void recuperaIndiceAsientosSimple(
		ControllerRequest request,
		ControllerResponse response, DBConnection dconn)
		throws ControllerException, CustomException, DBException, Throwable {

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			//recupera datos basicos de la partida
			String refNumPart = req.getParameter("refnum_part");
			
			String accion = req.getParameter("accion");
			String objetoSolID = req.getParameter("objetoId");

			PartidaBean partida = new PartidaBean();
			List lAsientosE = new ArrayList();
			AsientoFichaBean asieFicha = null;
			List lAsientosT = new ArrayList();
			List lTitulosPen = new ArrayList();

			// inicia proceso normal
			if (isTrace(this)) trace("recuperamos la partida con ref num = " + refNumPart, request);
			DboPartida dboPartida = new DboPartida(dconn);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_OFIC_REG_ID);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_TS_ULT_SYNC);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_AREA_REG_ID);
			dboPartida.setField(DboPartida.CAMPO_REFNUM_PART,refNumPart);
			if (!dboPartida.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No se pudo encontrar la partida especificada");


			partida.setRefNumPart(refNumPart);
			partida.setNumPartida(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA));
			partida.setRegPubId(dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID));
			partida.setOficRegId(dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID));
			partida.setCodLibro(dboPartida.getField(DboPartida.CAMPO_COD_LIBRO));
			partida.setCodArea(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID));
			partida.setAreaRegistralId(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID));
			
			// ponemos datos en sesion
			if (isTrace(this)) trace("ponemos en sesion el NUM_PARTIDA", request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			
			session.setAttribute(ATRIBUTO_PARTIDA_EN_VISUALIZACION, partida.getNumPartida());
			session.setAttribute(ATRIBUTO_PARTIDA_RN_EN_VISUALIZACION, refNumPart);
			session.setAttribute(ATRIBUTO_PARTIDA_TMSTMP_SYNCHRO, dboPartida.getField(DboPartida.CAMPO_TS_ULT_SYNC));
			session.setAttribute(ATRIBUTO_OFIC_REG_ID, partida.getOficRegId());
			session.setAttribute(ATRIBUTO_REG_PUB_ID, partida.getRegPubId());
			session.setAttribute(ATRIBUTO_AREA_REGISTRAL, partida.getCodArea());
		
			//descripcion de registro
			if (isTrace(this)) trace("recuperamos los datos del registro publico", request);
			DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
			dboRegisPublico.setFieldsToRetrieve(dboRegisPublico.CAMPO_NOMBRE);
			dboRegisPublico.setField(dboRegisPublico.CAMPO_REG_PUB_ID,partida.getRegPubId());
			if (dboRegisPublico.find() == true)
				partida.setRegPubDescripcion(dboRegisPublico.getField(dboRegisPublico.CAMPO_NOMBRE));


			//descripcion de oficina
			if (isTrace(this)) trace("recuperamos los datos de la oficina registral", request);
			DboOficRegistral dboOficRegistral = new DboOficRegistral(dconn);
			dboOficRegistral.setFieldsToRetrieve(dboOficRegistral.CAMPO_NOMBRE);
			dboOficRegistral.setField(dboOficRegistral.CAMPO_OFIC_REG_ID,partida.getOficRegId());
			dboOficRegistral.setField(dboOficRegistral.CAMPO_REG_PUB_ID,partida.getRegPubId());
			if (dboOficRegistral.find() == true)
					partida.setOficRegDescripcion(dboOficRegistral.getField(dboOficRegistral.CAMPO_NOMBRE));
					
			//recupera los asientos electronicos
			if (isTrace(this)) trace("recuperamos los datos de los asientos, rubro, actos y titulos relacionados", request);
			MultiDBObject mDboAsientos = new MultiDBObject(dconn);
			
			mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboAsiento","as");
			
			mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmActo","ac");
			mDboAsientos.setForeignKey("as", DboAsiento.CAMPO_COD_ACTO, "ac", DboTmActo.CAMPO_COD_ACTO);
			mDboAsientos.setField("as", DboAsiento.CAMPO_REFNUM_PART, refNumPart);
			
			/*
			28 nov 2002 - 
			NO BORRAR ESTE COMENTARIO!, se cambia para usar tabla
			TM_LETRA_RUBRO en lugar de TM_rubro; pero el cambio no ha sido
			confirmado al 100%
			*/
			//_
			mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmRubro","rb");
			mDboAsientos.setForeignKey("as", DboAsiento.CAMPO_COD_RUBRO, "rb", DboTmRubro.CAMPO_COD_RUBRO);
			mDboAsientos.setField("rb", DboTmRubro.CAMPO_ESTADO, "1");
			
			//_HT
			/*
			DboPartida dbop = new DboPartida(conn);
			dbop.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
			dbop.setField(DboPartida.CAMPO_REFNUM_PART,refNumPart);
			dbop.find();
			String codigoLibro = dboPartida.getField(DboPartida.CAMPO_COD_LIBRO);
			mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmLetraRubro","letra");
			mDboAsientos.setForeignKey("as", DboAsiento.CAMPO_LETRA_RUBRO, "letra", DboTmLetraRubro.CAMPO_LETRA_RUBRO);
			mDboAsientos.setField("letra", DboTmLetraRubro.CAMPO_COD_LIBRO, codigoLibro);
			*/
			//_HT


			String orden = req.getParameter("orden");
			String ordenarPor;
			if (orden==null) 
			{
				// orden por defecto , default
				if (isTrace(this)) trace("se ordena por CAMPO_NS_ASIENTO DESC", request);
				
				//ANTES.......ordenarPor = DboAsiento.CAMPO_TS_INSCRIP + " desc";
				ordenarPor = DboAsiento.CAMPO_NS_ASIENTO + " asc";
			} 
			else 
			{
				if (isTrace(this)) trace("se ordena por codigo de rubro", request);
				// orden por rubro
				/*
				28 nov 2002 - 
				NO BORRAR ESTE COMENTARIO!, se cambia para usar tabla
				TM_LETRA_RUBRO en lugar de TM_rubro; pero el cambio no ha sido
				confirmado al 100%
				*/
				ordenarPor = DboTmRubro.CAMPO_LETRA + "|" + DboAsiento.CAMPO_TS_INSCRIP + " asc";
				/*
				ordenarPor = DboAsiento.CAMPO_LETRA_RUBRO + "|" + DboAsiento.CAMPO_TS_INSCRIP + " desc";
				*/
			}
			
			// Para recuperar la fecha de presentacion
			DboTitulo dboTitulo = new DboTitulo(dconn);
			
			int nEnmienda = 0;
			int nroPagRef = 0;
			
			//recupera los asientos en tomo foja
			if (isTrace(this)) trace("Buscamos asientos en folio", request);
			DboTomoFolio dboFoja = new DboTomoFolio(dconn);
			dboFoja.setFieldsToRetrieve(DboTomoFolio.CAMPO_ESTADO);
			dboFoja.setFieldsToRetrieve(DboTomoFolio.CAMPO_FOLIO_BIS);
			dboFoja.setFieldsToRetrieve(DboTomoFolio.CAMPO_ID_IMG_FOLIO);
			dboFoja.setFieldsToRetrieve(DboTomoFolio.CAMPO_NS_CADE);
			dboFoja.setFieldsToRetrieve(DboTomoFolio.CAMPO_NU_FOJA);
			dboFoja.setFieldsToRetrieve(DboTomoFolio.CAMPO_NU_TOMO);
			dboFoja.setField(DboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			//for(Iterator i = dboFoja.searchAndRetrieveList(DboTomoFolio.CAMPO_NS_CADE + " desc").iterator(); i.hasNext();){
			for(Iterator i = dboFoja.searchAndRetrieveList(DboTomoFolio.CAMPO_NS_CADE + " asc").iterator(); i.hasNext();){
				dboFoja = (DboTomoFolio) i.next();
				AsientoFojaBean asieFoja = new AsientoFojaBean();
				asieFoja.setNroFolio(dboFoja.getField(DboTomoFolio.CAMPO_NU_FOJA));
				//cjvc77 12Dic2002 *****
				String numTomo = dboFoja.getField(DboTomoFolio.CAMPO_NU_TOMO);
				asieFoja.setNroTomo(numTomo.startsWith("9")?new StringBuffer("0").append(numTomo.substring(1)).toString():numTomo);
				//asieFoja.setNroTomo(dboFoja.getField(DboTomoFolio.CAMPO_NU_TOMO));
				//cjvc77 12Dic2002 *****
				asieFoja.setNumRef(dboFoja.getField(DboTomoFolio.CAMPO_NS_CADE));
				asieFoja.setNumPagRef(String.valueOf(++nroPagRef));
				asieFoja.setObjectId(dboFoja.getField(DboTomoFolio.CAMPO_ID_IMG_FOLIO));
				lAsientosT.add(asieFoja);
			}
			
			if (lAsientosT.size()==0) {
				// no hay asientos electronicos
				if (isTrace(this)) trace("No hay fojas electronicos", request);
			} else 
				Collections.reverse(lAsientosT);

			//recupera los asientos en ficha
			if (isTrace(this)) trace("No hay asientos en ficha (solo puede haber 1)", request);
			DboFicha dboFicha = new DboFicha(dconn);
			dboFicha.setFieldsToRetrieve(DboFicha.CAMPO_FICHA);
			dboFicha.setFieldsToRetrieve(DboFicha.CAMPO_FICHA_BIS);
			dboFicha.setFieldsToRetrieve(DboFicha.CAMPO_ID_IMG_FICHA);
			dboFicha.setFieldsToRetrieve(DboFicha.CAMPO_NUMPAG);
			dboFicha.setField(DboFicha.CAMPO_REFNUM_PART,refNumPart);
			if (dboFicha.find()) {
				asieFicha = new AsientoFichaBean();
				asieFicha.setNroFicha(dboFicha.getField(DboFicha.CAMPO_FICHA));
				asieFicha.setNroPaginas(Integer.parseInt(dboFicha.getField(DboFicha.CAMPO_NUMPAG)));
				//asieFicha.setNumPagRef(String.valueOf(nroPagRef));
				nroPagRef = nroPagRef + asieFicha.getNroPaginas();
				asieFicha.setNumPagRef(String.valueOf(nroPagRef));
				asieFicha.setObjectId(dboFicha.getField(DboFicha.CAMPO_ID_IMG_FICHA));
			}

			//recupera los asientos electronicos
			if (isTrace(this)) trace("ejecutamos el select para los asientos", request);
			Vector vDboAsientos = mDboAsientos.searchAndRetrieve(ordenarPor);
			//int actual = vDboAsientos.size();
			int actual = 0;
			for(Iterator i = vDboAsientos.iterator(); i.hasNext();) {
				mDboAsientos = (MultiDBObject) i.next();
				AsientoElectronicoBean asiento = new AsientoElectronicoBean();
				//asiento.setNumRef(mDboAsientos.getField("as",DboAsiento.CAMPO_NS_ASIENTO));
				asiento.setNumRef(""+(++actual));
				//System.out.println("-" + asiento.getNumRef());
				asiento.setAaTitulo(mDboAsientos.getField("as",DboAsiento.CAMPO_AA_TITU));
				asiento.setActoDescripcion(mDboAsientos.getField("ac",DboTmActo.CAMPO_DESCRIPCION));
				asiento.setActoId(mDboAsientos.getField("as",DboAsiento.CAMPO_COD_ACTO));
				asiento.setNsAsie(mDboAsientos.getField("as",DboAsiento.CAMPO_NS_ASIENTO));
				asiento.setNsAsiePlaca(mDboAsientos.getField("as",DboAsiento.CAMPO_NS_ASIE_PLACA));
				asiento.setPlaca(mDboAsientos.getField("as",DboAsiento.CAMPO_NUM_PLACA));
				
				//si la fecha es vacia o null, mostrar "NO DISPONIBLE"
				String xts = FechaFormatter.deDBOaFechaHoraWeb(mDboAsientos.getField("as",DboAsiento.CAMPO_TS_INSCRIP));
				if (xts==null || xts.length()==0)				
					asiento.setFechaInscripcion("NO DISPONIBLE");
				else
					asiento.setFechaInscripcion(xts);
				
				asiento.setNroPaginas(Integer.parseInt(mDboAsientos.getField("as",DboAsiento.CAMPO_NUMPAG)));
				asiento.setNumPagRef(String.valueOf(nroPagRef));
				nroPagRef = nroPagRef + asiento.getNroPaginas();
				asiento.setNroTitulo(mDboAsientos.getField("as",DboAsiento.CAMPO_NUM_TITU));
				asiento.setObjectId(mDboAsientos.getField("as",DboAsiento.CAMPO_ID_IMG_ASIENTO));
				asiento.setRubroId(mDboAsientos.getField("as",DboAsiento.CAMPO_COD_RUBRO));
				
				//System.out.println(asiento.getAaTitulo() + "-" + asiento.getNroTitulo() + "-" + asiento.getNumPagRef());


				/*
				28 nov 2002 - 
				NO BORRAR ESTE COMENTARIO!, se cambia para usar tabla
				TM_LETRA_RUBRO en lugar de TM_rubro; pero el cambio no ha sido
				confirmado al 100%
				*/
				asiento.setRubroLetra(mDboAsientos.getField("rb",DboTmRubro.CAMPO_LETRA));
				asiento.setRubroDescripcion(mDboAsientos.getField("rb",DboTmRubro.CAMPO_NOMBRE));


				//_ht
				/*
				asiento.setRubroLetra(mDboAsientos.getField("as",DboAsiento.CAMPO_LETRA_RUBRO));
				asiento.setRubroDescripcion(mDboAsientos.getField("letra",DboTmLetraRubro.CAMPO_DESC_RUBRO));
				*/
				//_ht
				
				if ( (asiento.getNroTitulo() == null) || (asiento.getNroTitulo().trim().length() <= 0) 
				 || (asiento.getNroTitulo().equals("00000000")) || (asiento.getAaTitulo().equals("9999"))
				) {
					asiento.setAaTitulo("-");
					asiento.setNroTitulo("ENMIENDA " + ++nEnmienda);
					asiento.setParticipantesPJ("");
					asiento.setParticipantesPN("");
					asiento.setFechaPresentacion("");
				} else {
					// buscamos la fecha de presentacion de la tabla de titulos
					dboTitulo.clear();
					dboTitulo.setFieldsToRetrieve(DboTitulo.CAMPO_TS_PRESENT);
					dboTitulo.setField(DboTitulo.CAMPO_REG_PUB_ID, partida.getRegPubId());
					dboTitulo.setField(DboTitulo.CAMPO_OFIC_REG_ID, partida.getOficRegId());
					dboTitulo.setField(DboTitulo.CAMPO_ANO_TITU, asiento.getAaTitulo());
					dboTitulo.setField(DboTitulo.CAMPO_NUM_TITU, asiento.getNroTitulo());
					if (dboTitulo.find()) {
						asiento.setFechaPresentacion(FechaFormatter.deDBOaFechaHoraWeb(dboTitulo.getField(DboTitulo.CAMPO_TS_PRESENT)));
					} else {
						asiento.setFechaPresentacion("NO DISPONIBLE");
					}
					
						// inicio participantes
						// recupero relacion de participantes persona juridica
						if (isTrace(this)) trace("Recuperamos los participantes PJ y los concateno", request);
						StringBuffer partiPJ = new StringBuffer();
						// inicio cambio
						Statement stmt = dconn.createStatement();
						ResultSet prtcPJ = stmt.executeQuery(
						(new StringBuffer()).append("select pj.razon_social as razon_social from user1.ind_prtc ind, user1.prtc_jur pj where ")
											.append(" ind.aa_titu = '").append(asiento.getAaTitulo()).append("'")
											.append(" and ind.nu_titu = '").append(asiento.getNroTitulo()).append("'")
											.append(" and ind.refnum_part = ").append(refNumPart)
											.append(" and ind.tipo_pers = '").append(Constantes.CUR_EX_JURIDICO).append("'")
											.append(" and pj.cur_prtc = ind.cur_prtc")
											.append(" and pj.reg_pub_id = '").append(partida.getRegPubId()).append("'")
											.append(" and pj.ofic_reg_id = '").append(partida.getOficRegId()).append("'")
											.toString()
						);
						while (prtcPJ.next()) {
							if (partiPJ.length() != 0) partiPJ.append(" - ");
							partiPJ.append(prtcPJ.getString("razon_social"));
						}
						prtcPJ.close();
						stmt.close();
						// fin cambio
						asiento.setParticipantesPJ(partiPJ.toString());
						// recupero relacion de participantes persona natural
						if (isTrace(this)) trace("Recuperamos los participantes PN y los concateno", request);
						StringBuffer buf = new StringBuffer();
						// inicio cambio
						stmt = dconn.createStatement();
						ResultSet prtcPN = stmt.executeQuery(
						(new StringBuffer()).append("select pn.ape_pat as ape_pat, pn.ape_mat as ape_mat, pn.nombres as nombres ")
											.append(" from user1.ind_prtc ind, user1.prtc_nat pn where ")
											.append(" ind.aa_titu = '").append(asiento.getAaTitulo()).append("'")
											.append(" and ind.nu_titu = '").append(asiento.getNroTitulo()).append("'")
											.append(" and ind.refnum_part = ").append(refNumPart)
											.append(" and ind.tipo_pers = '").append(Constantes.CUR_EX_NATURAL).append("'")
											.append(" and pn.cur_prtc = ind.cur_prtc")
											.append(" and pn.reg_pub_id = '").append(partida.getRegPubId()).append("'")
											.append(" and pn.ofic_reg_id = '").append(partida.getOficRegId()).append("'")
											.toString()
						);
						while (prtcPN.next()) {
							if (buf.length() != 0) buf.append(" - ");					
							//armamos la cadena
							buf.append(prtcPN.getString("ape_pat"));
							String apMat = prtcPN.getString("ape_mat");
							if (apMat != null) buf.append(" ").append(apMat);
							String nombres = prtcPN.getString("nombres");
							if (nombres != null) buf.append(", ").append(nombres);
						}
						prtcPJ.close();
						stmt.close();
						// fin cambio
						asiento.setParticipantesPN(buf.toString());
						// fin participantes
				}
				
				lAsientosE.add(asiento);
				//Collections.reverse(lAsientosE);
				
			}
			
			if (lAsientosE.size()==0) {
				// no hay asientos electronicos
				if (isTrace(this)) trace("No hay asientos electronicos", request);
			} else 
				Collections.reverse(lAsientosE);


			if (isTrace(this)) trace("Recupera los bloqueos de partida", request);
			DboTaBloqPartida dboBloqPartida = new DboTaBloqPartida(dconn);
			dboBloqPartida.setFieldsToRetrieve(DboTaBloqPartida.CAMPO_ANO_TITU);
			dboBloqPartida.setFieldsToRetrieve(DboTaBloqPartida.CAMPO_NUM_TITU);
			dboBloqPartida.setField(DboTaBloqPartida.CAMPO_NUM_PARTIDA, partida.getNumPartida());
			dboBloqPartida.setField(DboTaBloqPartida.CAMPO_OFIC_REG_ID, partida.getOficRegId());
			dboBloqPartida.setField(DboTaBloqPartida.CAMPO_REG_PUB_ID, partida.getRegPubId());
			dboBloqPartida.setField(DboTaBloqPartida.CAMPO_COD_LIBRO, partida.getCodLibro());
			dboBloqPartida.setField(DboTaBloqPartida.CAMPO_ESTADO, "1");
			

			StringBuffer actosTitulo;
			
			for (Iterator i = dboBloqPartida.searchAndRetrieveList().iterator(); i.hasNext(); ) 
			{
				
				if (isTrace(this)) trace("Recupera los titulos que bloquean la partida", request);


				// llenamos los datos que tenemos de la tabla de lboqueo de partida
				DboTaBloqPartida bloqPart = (DboTaBloqPartida) i.next();
				TituloPendienteBean tituPen = new TituloPendienteBean();
				tituPen.setAaTitulo(bloqPart.getField(DboTaBloqPartida.CAMPO_ANO_TITU));
				tituPen.setNroTitulo(bloqPart.getField(DboTaBloqPartida.CAMPO_NUM_TITU));
				tituPen.setOficReg(partida.getOficRegDescripcion());
				tituPen.setZonaReg(partida.getRegPubDescripcion());
				
				//recupera actos del titulo
				MultiDBObject mDboActosTitu = new MultiDBObject(dconn);


				mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTitulo","ti");
				mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboActosTitulo","at");
				mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmActo","ac");
				
				mDboActosTitu.setForeignKey("ac",DboTmActo.CAMPO_COD_ACTO,"at",DboActosTitulo.CAMPO_COD_ACTO);
				
				mDboActosTitu.setField("ti", DboTitulo.CAMPO_ANO_TITU, tituPen.getAaTitulo());
				mDboActosTitu.setField("ti", DboTitulo.CAMPO_NUM_TITU, tituPen.getNroTitulo());
				mDboActosTitu.setField("ti", DboTitulo.CAMPO_AREA_REG_ID, partida.getAreaRegistralId());
				mDboActosTitu.setField("ti", DboTitulo.CAMPO_OFIC_REG_ID, partida.getOficRegId());
				mDboActosTitu.setField("ti", DboTitulo.CAMPO_REG_PUB_ID, partida.getRegPubId());
				
				mDboActosTitu.setForeignKey("ti", DboTitulo.CAMPO_REFNUM_TITU, "at", DboActosTitulo.CAMPO_REFNUM_TITU);
				mDboActosTitu.setForeignKey("at", DboActosTitulo.CAMPO_COD_ACTO, "ac", DboTmActo.CAMPO_COD_ACTO);
				
				actosTitulo = new StringBuffer();
				
				if (isTrace(this)) trace("Recupera los actos del titulo y concatena en un string", request);
				for(Iterator j = mDboActosTitu.searchAndRetrieve().iterator(); j.hasNext();){
					mDboActosTitu = (MultiDBObject) j.next();
					if (actosTitulo.length()!=0) actosTitulo.append(", ");
					actosTitulo.append(mDboActosTitu.getField("ac",DboTmActo.CAMPO_DESCRIPCION));
				}
				
				tituPen.setActo(actosTitulo.toString());
				if (tituPen.getActo().length() == 0) {
					tituPen.setActo("NO DISPONIBLE");
				}
				
				lTitulosPen.add(tituPen);
			}
			
			req.setAttribute("partida",partida);
			req.setAttribute("numPgnaTotal",String.valueOf(nroPagRef));
			if (lAsientosE.size() > 0) req.setAttribute("asientos",lAsientosE);
			if (asieFicha != null) req.setAttribute("ficha",asieFicha);
			if (lAsientosT.size() > 0) req.setAttribute("folios",lAsientosT);
			if (lTitulosPen.size() > 0) req.setAttribute("titulos",lTitulosPen);
			// termina proceso normal
			
			
		}

	private void recuperaIndiceAsientosCertificados(
		ControllerRequest request,
		ControllerResponse response, DBConnection dconn)
		throws ControllerException, CustomException, DBException, Throwable {

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			//control de acceso, se debe verificar que la accion sea coherente con la carga laboral (estado de la solicitud, usuario asignado)
			
			//recupera datos basicos de la partida
			String refNumPart = req.getParameter("refnum_part");
			
			String accion = req.getParameter("accion");
			String objetoSolID = req.getParameter("objetoId");

			PartidaBean partida = new PartidaBean();
			List lAsientosE = new ArrayList();
			AsientoFichaBean asieFicha = null;
			List lAsientosT = new ArrayList();
			List lTitulosPen = new ArrayList();

			// inicia proceso normal
			if (isTrace(this)) trace("recuperamos la partida con ref num = " + refNumPart, request);
			DboPartida dboPartida = new DboPartida(dconn);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_REG_PUB_ID);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_OFIC_REG_ID);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_TS_ULT_SYNC);
			dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_AREA_REG_ID);
			dboPartida.setField(DboPartida.CAMPO_REFNUM_PART,refNumPart);
			if (!dboPartida.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "No se pudo encontrar la partida especificada");


			partida.setRefNumPart(refNumPart);
			partida.setNumPartida(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA));
			partida.setRegPubId(dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID));
			partida.setOficRegId(dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID));
			partida.setCodLibro(dboPartida.getField(DboPartida.CAMPO_COD_LIBRO));
			partida.setCodArea(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID));
			partida.setAreaRegistralId(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID));
			
			// ponemos datos en sesion
			if (isTrace(this)) trace("ponemos en sesion el NUM_PARTIDA", request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			
			session.setAttribute(ATRIBUTO_PARTIDA_EN_VISUALIZACION, partida.getNumPartida());
			session.setAttribute(ATRIBUTO_PARTIDA_RN_EN_VISUALIZACION, refNumPart);
			session.setAttribute(ATRIBUTO_PARTIDA_TMSTMP_SYNCHRO, dboPartida.getField(DboPartida.CAMPO_TS_ULT_SYNC));
			session.setAttribute(ATRIBUTO_OFIC_REG_ID, partida.getOficRegId());
			session.setAttribute(ATRIBUTO_REG_PUB_ID, partida.getRegPubId());
			session.setAttribute(ATRIBUTO_AREA_REGISTRAL, partida.getCodArea());
		
			//descripcion de registro
			if (isTrace(this)) trace("recuperamos los datos del registro publico", request);
			DboRegisPublico dboRegisPublico = new DboRegisPublico(dconn);
			dboRegisPublico.setFieldsToRetrieve(dboRegisPublico.CAMPO_NOMBRE);
			dboRegisPublico.setField(dboRegisPublico.CAMPO_REG_PUB_ID,partida.getRegPubId());
			if (dboRegisPublico.find() == true)
				partida.setRegPubDescripcion(dboRegisPublico.getField(dboRegisPublico.CAMPO_NOMBRE));


			//descripcion de oficina
			if (isTrace(this)) trace("recuperamos los datos de la oficina registral", request);
			DboOficRegistral dboOficRegistral = new DboOficRegistral(dconn);
			dboOficRegistral.setFieldsToRetrieve(dboOficRegistral.CAMPO_NOMBRE);
			dboOficRegistral.setField(dboOficRegistral.CAMPO_OFIC_REG_ID,partida.getOficRegId());
			dboOficRegistral.setField(dboOficRegistral.CAMPO_REG_PUB_ID,partida.getRegPubId());
			if (dboOficRegistral.find() == true)
					partida.setOficRegDescripcion(dboOficRegistral.getField(dboOficRegistral.CAMPO_NOMBRE));
					
					
			int nroPagRef = 0;
			 
			// PUBLICIDAD CERTIFICADA
			// recuperar los registros existentes
			DboVerificaTomoFoja tomoFojaVerificada = new DboVerificaTomoFoja(dconn);
			if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA)){
				tomoFojaVerificada.clearAll();
				tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_OBJETO_SOL_ID, objetoSolID);
				for(Iterator i = tomoFojaVerificada.searchAndRetrieveList().iterator(); i.hasNext();) {
					// eliminar la data existente, asociada a este objeto solicitud
					tomoFojaVerificada = (DboVerificaTomoFoja) i.next();
					tomoFojaVerificada.delete();
				}
				//recupera los asientos en tomo foja
				if (isTrace(this)) trace("Buscamos asientos en folio", request);
				DboTomoFolio dboFoja = new DboTomoFolio(dconn);
				dboFoja.setField(DboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
	
				for(Iterator i = dboFoja.searchAndRetrieveList(DboTomoFolio.CAMPO_NS_CADE + " asc").iterator(); i.hasNext();){
					dboFoja = (DboTomoFolio) i.next();
					AsientoFojaBean asieFoja = new AsientoFojaBean();
					asieFoja.setNroFolio(dboFoja.getField(DboTomoFolio.CAMPO_NU_FOJA));
					String numTomo = dboFoja.getField(DboTomoFolio.CAMPO_NU_TOMO);
					asieFoja.setNroTomo(numTomo.startsWith("9")?new StringBuffer("0").append(numTomo.substring(1)).toString():numTomo);
					asieFoja.setNumRef(dboFoja.getField(DboTomoFolio.CAMPO_NS_CADE));
					asieFoja.setNumPagRef(String.valueOf(++nroPagRef));
					asieFoja.setObjectId(dboFoja.getField(DboTomoFolio.CAMPO_ID_IMG_FOLIO));
					// insertar en la tabla de verificaTomoFoja
					tomoFojaVerificada.clearAll();
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_AGNT_SYNC,dboFoja.getField(DboTomoFolio.CAMPO_AGNT_SYNC));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_ESTADO,dboFoja.getField(DboTomoFolio.CAMPO_ESTADO));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_FOLIO_BIS,dboFoja.getField(DboTomoFolio.CAMPO_FOLIO_BIS));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_ID_IMG_FOLIO,dboFoja.getField(DboTomoFolio.CAMPO_ID_IMG_FOLIO));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_NS_CADE,dboFoja.getField(DboTomoFolio.CAMPO_NS_CADE));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_NU_FOJA,dboFoja.getField(DboTomoFolio.CAMPO_NU_FOJA));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_NU_TOMO,dboFoja.getField(DboTomoFolio.CAMPO_NU_TOMO));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_OBJETO_SOL_ID,objetoSolID);
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_REFNUM_PART,dboFoja.getField(DboTomoFolio.CAMPO_REFNUM_PART));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_TOMO_BIS,dboFoja.getField(DboTomoFolio.CAMPO_TOMO_BIS));
					tomoFojaVerificada.setField(tomoFojaVerificada.CAMPO_TS_ULT_SYNC,FechaUtil.stringTimeToOracleString(FechaUtil.stringmmddyyyytoddmmyyyy(dboFoja.getField(DboTomoFolio.CAMPO_TS_ULT_SYNC))));
					tomoFojaVerificada.add();
					lAsientosT.add(asieFoja);
				}
			}else if (accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE)){
				//recupera los asientos en tomo foja
				if (isTrace(this)) trace("Buscamos asientos en folio", request);
				tomoFojaVerificada.clearAll();
				tomoFojaVerificada.setField(DboVerificaTomoFoja.CAMPO_REFNUM_PART, refNumPart);
				tomoFojaVerificada.setField(DboVerificaTomoFoja.CAMPO_OBJETO_SOL_ID, objetoSolID);
	
				for(Iterator i = tomoFojaVerificada.searchAndRetrieveList(DboVerificaTomoFoja.CAMPO_NS_CADE + " asc").iterator(); i.hasNext();){
					tomoFojaVerificada = (DboVerificaTomoFoja) i.next();
					AsientoFojaBean asieFoja = new AsientoFojaBean();
					asieFoja.setNroFolio(tomoFojaVerificada.getField(DboVerificaTomoFoja.CAMPO_NU_FOJA));
					String numTomo = tomoFojaVerificada.getField(DboVerificaTomoFoja.CAMPO_NU_TOMO);
					asieFoja.setNroTomo(numTomo.startsWith("9")?new StringBuffer("0").append(numTomo.substring(1)).toString():numTomo);
					asieFoja.setNumRef(tomoFojaVerificada.getField(DboVerificaTomoFoja.CAMPO_NS_CADE));
					asieFoja.setNumPagRef(String.valueOf(++nroPagRef));
					asieFoja.setObjectId(tomoFojaVerificada.getField(DboVerificaTomoFoja.CAMPO_ID_IMG_FOLIO));
					lAsientosT.add(asieFoja);
				}
			}
			// FIN PUBLICIDAD CERTIFICADA
			
			if (lAsientosT.size()==0) {
				// no hay asientos electronicos
				if (isTrace(this)) trace("No hay fojas electronicos", request);
			} else 
				Collections.reverse(lAsientosT);

			// PUBLICIDAD CERTIFICADA
			// recuperar los registros existentes
			DboVerificaFicha fichaVerificada = new DboVerificaFicha(dconn);
			if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA)){
				fichaVerificada.clearAll();
				fichaVerificada.setField(DboVerificaFicha.CAMPO_OBJETO_SOL_ID, objetoSolID);
				for(Iterator i = fichaVerificada.searchAndRetrieveList().iterator(); i.hasNext();) {
					// eliminar la data existente, asociada a este objeto solicitud
					fichaVerificada = (DboVerificaFicha) i.next();
					fichaVerificada.delete();
				}
	
				//recupera los asientos en ficha
				if (isTrace(this)) trace("No hay asientos en ficha (solo puede haber 1)", request);
				DboFicha dboFicha = new DboFicha(dconn);
				dboFicha.setField(DboFicha.CAMPO_REFNUM_PART,refNumPart);
				if (dboFicha.find()) {					
					asieFicha = new AsientoFichaBean();
					asieFicha.setNroFicha(dboFicha.getField(DboFicha.CAMPO_FICHA));
					asieFicha.setNroPaginas(Integer.parseInt(dboFicha.getField(DboFicha.CAMPO_NUMPAG)));
					//asieFicha.setNumPagRef(String.valueOf(nroPagRef));
					nroPagRef = nroPagRef + asieFicha.getNroPaginas();
					asieFicha.setNumPagRef(String.valueOf(nroPagRef));
					asieFicha.setObjectId(dboFicha.getField(DboFicha.CAMPO_ID_IMG_FICHA));
					// insertar en la tabla de verificaFicha
					fichaVerificada.clearAll();					
					fichaVerificada.setField(DboVerificaFicha.CAMPO_AGNT_SYNC,dboFicha.getField(DboFicha.CAMPO_AGNT_SYNC));
					fichaVerificada.setField(DboVerificaFicha.CAMPO_FICHA,dboFicha.getField(DboFicha.CAMPO_FICHA));
					//jacr
					if (dboFicha.getField(DboFicha.CAMPO_FICHA_BIS) == null || dboFicha.getField(DboFicha.CAMPO_FICHA_BIS).equals("")){
						fichaVerificada.setField(DboVerificaFicha.CAMPO_FICHA_BIS," ");						
					}else{
						fichaVerificada.setField(DboVerificaFicha.CAMPO_FICHA_BIS,dboFicha.getField(DboFicha.CAMPO_FICHA_BIS));
					}
					fichaVerificada.setField(DboVerificaFicha.CAMPO_ID_IMG_FICHA,dboFicha.getField(DboFicha.CAMPO_ID_IMG_FICHA));
					fichaVerificada.setField(DboVerificaFicha.CAMPO_NUMPAG,dboFicha.getField(DboFicha.CAMPO_NUMPAG));
					fichaVerificada.setField(DboVerificaFicha.CAMPO_OBJETO_SOL_ID,objetoSolID);
					fichaVerificada.setField(DboVerificaFicha.CAMPO_REFNUM_PART,dboFicha.getField(DboFicha.CAMPO_REFNUM_PART));
					fichaVerificada.setField(DboVerificaFicha.CAMPO_TS_ULT_SYNC,FechaUtil.stringTimeToOracleString(FechaUtil.stringmmddyyyytoddmmyyyy(dboFicha.getField(DboFicha.CAMPO_TS_ULT_SYNC))));
					fichaVerificada.add();
				}
			}else if (accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE)){
				//recupera los asientos en ficha
				if (isTrace(this)) trace("No hay asientos en ficha (solo puede haber 1)", request);
				fichaVerificada.clearAll();
				fichaVerificada.setField(DboVerificaFicha.CAMPO_REFNUM_PART,refNumPart);
				fichaVerificada.setField(DboVerificaFicha.CAMPO_OBJETO_SOL_ID,objetoSolID);
				if (fichaVerificada.find()) {
					asieFicha = new AsientoFichaBean();
					asieFicha.setNroFicha(fichaVerificada.getField(DboVerificaFicha.CAMPO_FICHA));
					asieFicha.setNroPaginas(Integer.parseInt(fichaVerificada.getField(DboVerificaFicha.CAMPO_NUMPAG)));
					nroPagRef = nroPagRef + asieFicha.getNroPaginas();
					asieFicha.setNumPagRef(String.valueOf(nroPagRef));
					asieFicha.setObjectId(fichaVerificada.getField(DboVerificaFicha.CAMPO_ID_IMG_FICHA));
				}
			}
			// FIN PUBLICIDAD CERTIFICADA
		
			// PUBLICIDAD CERTIFICADA
			// recuperar los registros existentes
			DboVerificaAsiento asientoVerificado = new DboVerificaAsiento(dconn);
			if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA)){
				asientoVerificado.clearAll();
				asientoVerificado.setField(DboVerificaAsiento.CAMPO_OBJETO_SOL_ID,objetoSolID);
				for(Iterator i = asientoVerificado.searchAndRetrieveList().iterator(); i.hasNext();) {
					// eliminar la data existente, asociada a este objeto solicitud
					asientoVerificado = (DboVerificaAsiento) i.next();
					asientoVerificado.delete();
				}
				
				//recupera los asientos electronicos
				if (isTrace(this)) trace("recuperamos los datos de los asientos, rubro, actos y titulos relacionados", request);
				MultiDBObject mDboAsientos = new MultiDBObject(dconn);
				mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboAsiento","as");
				mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmActo","ac");
				mDboAsientos.setForeignKey("as", DboAsiento.CAMPO_COD_ACTO, "ac", DboTmActo.CAMPO_COD_ACTO);
				mDboAsientos.setField("as", DboAsiento.CAMPO_REFNUM_PART, refNumPart);
				mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmRubro","rb");
				mDboAsientos.setForeignKey("as", DboAsiento.CAMPO_COD_RUBRO, "rb", DboTmRubro.CAMPO_COD_RUBRO);
				mDboAsientos.setField("rb", DboTmRubro.CAMPO_ESTADO, "1");
				String orden = req.getParameter("orden");
				String ordenarPor;
				if (orden==null) {
					if (isTrace(this)) trace("se ordena por CAMPO_NS_ASIENTO DESC", request);
					ordenarPor = DboAsiento.CAMPO_NS_ASIENTO + " asc";
				} else {
					if (isTrace(this)) trace("se ordena por codigo de rubro", request);
					ordenarPor = DboTmRubro.CAMPO_LETRA + "|" + DboAsiento.CAMPO_TS_INSCRIP + " asc";
				}
				
				// Para recuperar la fecha de presentacion
				DboTitulo dboTitulo = new DboTitulo(dconn);
				
				int nEnmienda = 0;
				
				//recupera los asientos electronicos
				if (isTrace(this)) trace("ejecutamos el select para los asientos", request);
				Vector vDboAsientos = mDboAsientos.searchAndRetrieve(ordenarPor);
				int actual = 0;
				for(Iterator i = vDboAsientos.iterator(); i.hasNext();) {
					mDboAsientos = (MultiDBObject) i.next();
					AsientoElectronicoBean asiento = new AsientoElectronicoBean();
					asiento.setNumRef(""+(++actual));
					asiento.setAaTitulo(mDboAsientos.getField("as",DboAsiento.CAMPO_AA_TITU));
					asiento.setActoDescripcion(mDboAsientos.getField("ac",DboTmActo.CAMPO_DESCRIPCION));
					asiento.setActoId(mDboAsientos.getField("as",DboAsiento.CAMPO_COD_ACTO));
					//si la fecha es vacia o null, mostrar "NO DISPONIBLE"
					String xts = FechaFormatter.deDBOaFechaHoraWeb(mDboAsientos.getField("as",DboAsiento.CAMPO_TS_INSCRIP));
					if (xts==null || xts.length()==0)				
						asiento.setFechaInscripcion("NO DISPONIBLE");
					else
						asiento.setFechaInscripcion(xts);
					asiento.setNroPaginas(Integer.parseInt(mDboAsientos.getField("as",DboAsiento.CAMPO_NUMPAG)));
					asiento.setNumPagRef(String.valueOf(nroPagRef));
					nroPagRef = nroPagRef + asiento.getNroPaginas();
					asiento.setNroTitulo(mDboAsientos.getField("as",DboAsiento.CAMPO_NUM_TITU));
					asiento.setObjectId(mDboAsientos.getField("as",DboAsiento.CAMPO_ID_IMG_ASIENTO));
					asiento.setRubroId(mDboAsientos.getField("as",DboAsiento.CAMPO_COD_RUBRO));
					asiento.setRubroLetra(mDboAsientos.getField("rb",DboTmRubro.CAMPO_LETRA));
					asiento.setRubroDescripcion(mDboAsientos.getField("rb",DboTmRubro.CAMPO_NOMBRE));
					if ( (asiento.getNroTitulo() == null) || (asiento.getNroTitulo().trim().length() <= 0) ) {
						asiento.setAaTitulo("-");
						asiento.setNroTitulo("ENMIENDA " + ++nEnmienda);
						asiento.setParticipantesPJ("");
						asiento.setParticipantesPN("");
						asiento.setFechaPresentacion("");
					} else {
						// buscamos la fecha de presentacion de la tabla de titulos
						dboTitulo.clear();
						dboTitulo.setFieldsToRetrieve(DboTitulo.CAMPO_TS_PRESENT);
						dboTitulo.setField(DboTitulo.CAMPO_REG_PUB_ID, partida.getRegPubId());
						dboTitulo.setField(DboTitulo.CAMPO_OFIC_REG_ID, partida.getOficRegId());
						dboTitulo.setField(DboTitulo.CAMPO_ANO_TITU, asiento.getAaTitulo());
						dboTitulo.setField(DboTitulo.CAMPO_NUM_TITU, asiento.getNroTitulo());
						if (dboTitulo.find()) {
							asiento.setFechaPresentacion(FechaFormatter.deDBOaFechaHoraWeb(dboTitulo.getField(DboTitulo.CAMPO_TS_PRESENT)));
						} else {
							asiento.setFechaPresentacion("NO DISPONIBLE");
						}

						// inicio participantes
						// recupero relacion de participantes persona juridica
						if (isTrace(this)) trace("Recuperamos los participantes PJ y los concateno", request);
						StringBuffer partiPJ = new StringBuffer();
						// inicio cambio
						Statement stmt = dconn.createStatement();
						ResultSet prtcPJ = stmt.executeQuery(
						(new StringBuffer()).append("select pj.razon_social as razon_social from user1.ind_prtc ind, user1.prtc_jur pj where ")
											.append(" ind.aa_titu = '").append(asiento.getAaTitulo()).append("'")
											.append(" and ind.nu_titu = '").append(asiento.getNroTitulo()).append("'")
											.append(" and ind.refnum_part = ").append(refNumPart)
											.append(" and ind.tipo_pers = '").append(Constantes.CUR_EX_JURIDICO).append("'")
											.append(" and pj.cur_prtc = ind.cur_prtc")
											.append(" and pj.reg_pub_id = '").append(partida.getRegPubId()).append("'")
											.append(" and pj.ofic_reg_id = '").append(partida.getOficRegId()).append("'")
											.toString()
						);
						while (prtcPJ.next()) {
							if (partiPJ.length() != 0) partiPJ.append(" - ");
							partiPJ.append(prtcPJ.getString("razon_social"));
						}
						prtcPJ.close();
						stmt.close();
						// fin cambio
						asiento.setParticipantesPJ(partiPJ.toString());
						// recupero relacion de participantes persona natural
						if (isTrace(this)) trace("Recuperamos los participantes PN y los concateno", request);
						StringBuffer buf = new StringBuffer();
						// inicio cambio
						stmt = dconn.createStatement();
						ResultSet prtcPN = stmt.executeQuery(
						(new StringBuffer()).append("select pn.ape_pat as ape_pat, pn.ape_mat as ape_mat, pn.nombres as nombres ")
											.append(" from user1.ind_prtc ind, user1.prtc_nat pn where ")
											.append(" ind.aa_titu = '").append(asiento.getAaTitulo()).append("'")
											.append(" and ind.nu_titu = '").append(asiento.getNroTitulo()).append("'")
											.append(" and ind.refnum_part = ").append(refNumPart)
											.append(" and ind.tipo_pers = '").append(Constantes.CUR_EX_NATURAL).append("'")
											.append(" and pn.cur_prtc = ind.cur_prtc")
											.append(" and pn.reg_pub_id = '").append(partida.getRegPubId()).append("'")
											.append(" and pn.ofic_reg_id = '").append(partida.getOficRegId()).append("'")
											.toString()
						);
						while (prtcPN.next()) {
							if (buf.length() != 0) buf.append(" - ");					
							//armamos la cadena
							buf.append(prtcPN.getString("ape_pat"));
							String apMat = prtcPN.getString("ape_mat");
							if (apMat != null) buf.append(" ").append(apMat);
							String nombres = prtcPN.getString("nombres");
							if (nombres != null) buf.append(", ").append(nombres);
						}
						prtcPJ.close();
						stmt.close();
						// fin cambio
						asiento.setParticipantesPN(buf.toString());
						// fin participantes
						
					}
					
					// insertar en la tabla de asientos verificados
					asientoVerificado.clearAll();
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_AA_TITU,mDboAsientos.getField("as",DboAsiento.CAMPO_AA_TITU));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_AGNT_SYNC,  mDboAsientos.getField("as",DboAsiento.CAMPO_AGNT_SYNC));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_COD_ACTO,mDboAsientos.getField("as",DboAsiento.CAMPO_COD_ACTO));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_COD_RUBRO,mDboAsientos.getField("as",DboAsiento.CAMPO_COD_RUBRO));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_CO_ACTO_RGST_ORIG,  mDboAsientos.getField("as",DboAsiento.CAMPO_CO_ACTO_RGST_ORIG));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_CO_RUBR_ORIG,   mDboAsientos.getField("as",DboAsiento.CAMPO_CO_RUBR_ORIG));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_ES_ASIENTO,mDboAsientos.getField("as",DboAsiento.CAMPO_ES_ASIENTO));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_ID_IMG_ASIENTO, mDboAsientos.getField("as",DboAsiento.CAMPO_ID_IMG_ASIENTO).equals("")?null:
																						mDboAsientos.getField("as",DboAsiento.CAMPO_ID_IMG_ASIENTO));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_LETRA_RUBRO,mDboAsientos.getField("as",DboAsiento.CAMPO_LETRA_RUBRO));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_NS_ASIENTO,mDboAsientos.getField("as",DboAsiento.CAMPO_NS_ASIENTO));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_NS_ASIE_PLACA,  mDboAsientos.getField("as",DboAsiento.CAMPO_NS_ASIE_PLACA).equals("")?null:
																						mDboAsientos.getField("as",DboAsiento.CAMPO_NS_ASIE_PLACA));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_NUMPAG, mDboAsientos.getField("as",DboAsiento.CAMPO_NUMPAG).equals("")?null:
																				mDboAsientos.getField("as",DboAsiento.CAMPO_NUMPAG));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_NUM_PLACA,  mDboAsientos.getField("as",DboAsiento.CAMPO_NUM_PLACA));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_NUM_TITU,   mDboAsientos.getField("as",DboAsiento.CAMPO_NUM_TITU));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_OBJETO_SOL_ID,objetoSolID);
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_REFNUM_PART,mDboAsientos.getField("as",DboAsiento.CAMPO_REFNUM_PART));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_TS_INSCRIP, mDboAsientos.getField("as",DboAsiento.CAMPO_TS_INSCRIP).equals("")?null:
																					FechaUtil.stringTimeToOracleString(FechaUtil.stringmmddyyyytoddmmyyyy(mDboAsientos.getField("as",DboAsiento.CAMPO_TS_INSCRIP))));
					asientoVerificado.setField(DboVerificaAsiento.CAMPO_TS_ULT_SYNC,mDboAsientos.getField("as",DboAsiento.CAMPO_TS_ULT_SYNC).equals("")?null:
																					FechaUtil.stringTimeToOracleString(FechaUtil.stringmmddyyyytoddmmyyyy(mDboAsientos.getField("as",DboAsiento.CAMPO_TS_ULT_SYNC))));
					asientoVerificado.add();
					lAsientosE.add(asiento);
				}
			}else if (accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE)){
				//recupera los asientos electronicos
				if (isTrace(this)) trace("recuperamos los datos de los asientos, rubro, actos y titulos relacionados", request);
				MultiDBObject mDboAsientos = new MultiDBObject(dconn);
				mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboVerificaAsiento","as");
				mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmActo","ac");
				mDboAsientos.setForeignKey("as", DboVerificaAsiento.CAMPO_COD_ACTO, "ac", DboTmActo.CAMPO_COD_ACTO);
				mDboAsientos.setField("as", DboVerificaAsiento.CAMPO_REFNUM_PART, refNumPart);
				mDboAsientos.setField("as", DboVerificaAsiento.CAMPO_OBJETO_SOL_ID, objetoSolID);
				mDboAsientos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmRubro","rb");
				mDboAsientos.setForeignKey("as", DboVerificaAsiento.CAMPO_COD_RUBRO, "rb", DboTmRubro.CAMPO_COD_RUBRO);
				mDboAsientos.setField("rb", DboTmRubro.CAMPO_ESTADO, "1");
				String orden = req.getParameter("orden");
				String ordenarPor;
				if (orden==null) {
					if (isTrace(this)) trace("se ordena por CAMPO_NS_ASIENTO DESC", request);
					ordenarPor = DboVerificaAsiento.CAMPO_NS_ASIENTO + " asc";
				} else {
					if (isTrace(this)) trace("se ordena por codigo de rubro", request);
					ordenarPor = DboTmRubro.CAMPO_LETRA + "|" + DboVerificaAsiento.CAMPO_TS_INSCRIP + " asc";
				}
				
				// Para recuperar la fecha de presentacion
				DboTitulo dboTitulo = new DboTitulo(dconn);
				
				int nEnmienda = 0;
				
				//recupera los asientos electronicos
				if (isTrace(this)) trace("ejecutamos el select para los asientos", request);
				Vector vDboAsientos = mDboAsientos.searchAndRetrieve(ordenarPor);
				int actual = 0;
				for(Iterator i = vDboAsientos.iterator(); i.hasNext();) {
					mDboAsientos = (MultiDBObject) i.next();
					AsientoElectronicoBean asiento = new AsientoElectronicoBean();
					asiento.setNumRef(""+(++actual));
					asiento.setAaTitulo(mDboAsientos.getField("as",DboVerificaAsiento.CAMPO_AA_TITU));
					asiento.setActoDescripcion(mDboAsientos.getField("ac",DboTmActo.CAMPO_DESCRIPCION));
					asiento.setActoId(mDboAsientos.getField("as",DboVerificaAsiento.CAMPO_COD_ACTO));
					//si la fecha es vacia o null, mostrar "NO DISPONIBLE"
					String xts = FechaFormatter.deDBOaFechaHoraWeb(mDboAsientos.getField("as",DboVerificaAsiento.CAMPO_TS_INSCRIP));
					if (xts==null || xts.length()==0)				
						asiento.setFechaInscripcion("NO DISPONIBLE");
					else
						asiento.setFechaInscripcion(xts);
					asiento.setNroPaginas(Integer.parseInt(mDboAsientos.getField("as",DboVerificaAsiento.CAMPO_NUMPAG)));
					asiento.setNumPagRef(String.valueOf(nroPagRef));
					nroPagRef = nroPagRef + asiento.getNroPaginas();
					asiento.setNroTitulo(mDboAsientos.getField("as",DboVerificaAsiento.CAMPO_NUM_TITU));
					asiento.setObjectId(mDboAsientos.getField("as",DboVerificaAsiento.CAMPO_ID_IMG_ASIENTO));
					asiento.setRubroId(mDboAsientos.getField("as",DboVerificaAsiento.CAMPO_COD_RUBRO));
					asiento.setRubroLetra(mDboAsientos.getField("rb",DboTmRubro.CAMPO_LETRA));
					asiento.setRubroDescripcion(mDboAsientos.getField("rb",DboTmRubro.CAMPO_NOMBRE));
					if ( (asiento.getNroTitulo() == null) || (asiento.getNroTitulo().trim().length() <= 0) ) {
						asiento.setAaTitulo("-");
						asiento.setNroTitulo("ENMIENDA " + ++nEnmienda);
						asiento.setParticipantesPJ("");
						asiento.setParticipantesPN("");
						asiento.setFechaPresentacion("");
					} else {
						// buscamos la fecha de presentacion de la tabla de titulos
						dboTitulo.clear();
						dboTitulo.setFieldsToRetrieve(DboTitulo.CAMPO_TS_PRESENT);
						dboTitulo.setField(DboTitulo.CAMPO_REG_PUB_ID, partida.getRegPubId());
						dboTitulo.setField(DboTitulo.CAMPO_OFIC_REG_ID, partida.getOficRegId());
						dboTitulo.setField(DboTitulo.CAMPO_ANO_TITU, asiento.getAaTitulo());
						dboTitulo.setField(DboTitulo.CAMPO_NUM_TITU, asiento.getNroTitulo());
						if (dboTitulo.find()) {
							asiento.setFechaPresentacion(FechaFormatter.deDBOaFechaHoraWeb(dboTitulo.getField(DboTitulo.CAMPO_TS_PRESENT)));
						} else {
							asiento.setFechaPresentacion("NO DISPONIBLE");
						}
						
						// inicio participantes
						// recupero relacion de participantes persona juridica
						if (isTrace(this)) trace("Recuperamos los participantes PJ y los concateno", request);
						StringBuffer partiPJ = new StringBuffer();
						// inicio cambio
						Statement stmt = dconn.createStatement();
						ResultSet prtcPJ = stmt.executeQuery(
						(new StringBuffer()).append("select pj.razon_social as razon_social from user1.ind_prtc ind, user1.prtc_jur pj where ")
											.append(" ind.aa_titu = '").append(asiento.getAaTitulo()).append("'")
											.append(" and ind.nu_titu = '").append(asiento.getNroTitulo()).append("'")
											.append(" and ind.refnum_part = ").append(refNumPart)
											.append(" and ind.tipo_pers = '").append(Constantes.CUR_EX_JURIDICO).append("'")
											.append(" and pj.cur_prtc = ind.cur_prtc")
											.append(" and pj.reg_pub_id = '").append(partida.getRegPubId()).append("'")
											.append(" and pj.ofic_reg_id = '").append(partida.getOficRegId()).append("'")
											.toString()
						);
						while (prtcPJ.next()) {
							if (partiPJ.length() != 0) partiPJ.append(" - ");
							partiPJ.append(prtcPJ.getString("razon_social"));
						}
						prtcPJ.close();
						stmt.close();
						// fin cambio
						asiento.setParticipantesPJ(partiPJ.toString());
						// recupero relacion de participantes persona natural
						if (isTrace(this)) trace("Recuperamos los participantes PN y los concateno", request);
						StringBuffer buf = new StringBuffer();
						// inicio cambio
						stmt = dconn.createStatement();
						ResultSet prtcPN = stmt.executeQuery(
						(new StringBuffer()).append("select pn.ape_pat as ape_pat, pn.ape_mat as ape_mat, pn.nombres as nombres ")
											.append(" from user1.ind_prtc ind, user1.prtc_nat pn where ")
											.append(" ind.aa_titu = '").append(asiento.getAaTitulo()).append("'")
											.append(" and ind.nu_titu = '").append(asiento.getNroTitulo()).append("'")
											.append(" and ind.refnum_part = ").append(refNumPart)
											.append(" and ind.tipo_pers = '").append(Constantes.CUR_EX_NATURAL).append("'")
											.append(" and pn.cur_prtc = ind.cur_prtc")
											.append(" and pn.reg_pub_id = '").append(partida.getRegPubId()).append("'")
											.append(" and pn.ofic_reg_id = '").append(partida.getOficRegId()).append("'")
											.toString()
						);
						while (prtcPN.next()) {
							if (buf.length() != 0) buf.append(" - ");					
							//armamos la cadena
							buf.append(prtcPN.getString("ape_pat"));
							String apMat = prtcPN.getString("ape_mat");
							if (apMat != null) buf.append(" ").append(apMat);
							String nombres = prtcPN.getString("nombres");
							if (nombres != null) buf.append(", ").append(nombres);
						}
						prtcPJ.close();
						stmt.close();
						// fin cambio
						asiento.setParticipantesPN(buf.toString());
						// fin participantes
					}
					
					lAsientosE.add(asiento);
				}
				
			}
			if (lAsientosE.size()==0) {
				// no hay asientos electronicos
				if (isTrace(this)) trace("No hay asientos electronicos", request);
			} else 
				Collections.reverse(lAsientosE);

			// PUBLICIDAD CERTIFICADA
			// recuperar los registros existentes
			DboVerificaTituPend tituPendVerificada = new DboVerificaTituPend(dconn);
			StringBuffer titulosPendientes = new StringBuffer();
			if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA)){
				tituPendVerificada.clearAll();
				tituPendVerificada.setField(DboVerificaTituPend.CAMPO_OBJETO_SOL_ID, objetoSolID);
				for(Iterator i = tituPendVerificada.searchAndRetrieveList().iterator(); i.hasNext();) {
					// eliminar la data existente, asociada a este objeto solicitud
					tituPendVerificada = (DboVerificaTituPend) i.next();
					tituPendVerificada.delete();
				}
	
				if (isTrace(this)) trace("Recupera los bloqueos de partida", request);
				DboTaBloqPartida dboBloqPartida = new DboTaBloqPartida(dconn);
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_NUM_PARTIDA, partida.getNumPartida());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_OFIC_REG_ID, partida.getOficRegId());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_REG_PUB_ID, partida.getRegPubId());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_COD_LIBRO, partida.getCodLibro());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_ESTADO, "1");
				
				StringBuffer actosTitulo;
				
				for (Iterator i = dboBloqPartida.searchAndRetrieveList().iterator(); i.hasNext(); ) 
				{
					
					if (isTrace(this)) trace("Recupera los titulos que bloquean la partida", request);
	
					// llenamos los datos que tenemos de la tabla de bloqueo de partida
					DboTaBloqPartida bloqPart = (DboTaBloqPartida) i.next();
					TituloPendienteBean tituPen = new TituloPendienteBean();
					tituPen.setAaTitulo(bloqPart.getField(DboTaBloqPartida.CAMPO_ANO_TITU));
					tituPen.setNroTitulo(bloqPart.getField(DboTaBloqPartida.CAMPO_NUM_TITU));
					tituPen.setOficReg(partida.getOficRegDescripcion());
					tituPen.setZonaReg(partida.getRegPubDescripcion());
					
					//recupera actos del titulo
					MultiDBObject mDboActosTitu = new MultiDBObject(dconn);
	
	
					mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTitulo","ti");
					mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboActosTitulo","at");
					mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmActo","ac");
					
					mDboActosTitu.setForeignKey("ac",DboTmActo.CAMPO_COD_ACTO,"at",DboActosTitulo.CAMPO_COD_ACTO);
					
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_ANO_TITU, tituPen.getAaTitulo());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_NUM_TITU, tituPen.getNroTitulo());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_AREA_REG_ID, partida.getAreaRegistralId());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_OFIC_REG_ID, partida.getOficRegId());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_REG_PUB_ID, partida.getRegPubId());
					
					mDboActosTitu.setForeignKey("ti", DboTitulo.CAMPO_REFNUM_TITU, "at", DboActosTitulo.CAMPO_REFNUM_TITU);
					mDboActosTitu.setForeignKey("at", DboActosTitulo.CAMPO_COD_ACTO, "ac", DboTmActo.CAMPO_COD_ACTO);
					
					actosTitulo = new StringBuffer();
					
					if (isTrace(this)) trace("Recupera los actos del titulo y concatena en un string", request);
					for(Iterator j = mDboActosTitu.searchAndRetrieve().iterator(); j.hasNext();){
						mDboActosTitu = (MultiDBObject) j.next();
						if (actosTitulo.length()!=0) actosTitulo.append(", ");
						actosTitulo.append(mDboActosTitu.getField("ac",DboTmActo.CAMPO_DESCRIPCION));
					}
					
					tituPen.setActo(actosTitulo.toString());
					if (tituPen.getActo().length() == 0) {
						tituPen.setActo("NO DISPONIBLE");
					}
					
					// insertar en la tabla de verificaTituPend
					tituPendVerificada.clearAll();
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_AGNT_SYNC,bloqPart.getField(DboTaBloqPartida.CAMPO_AGNT_SYNC));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_ANO_TITU,bloqPart.getField(DboTaBloqPartida.CAMPO_ANO_TITU));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_AREA_REG_ID,bloqPart.getField(DboTaBloqPartida.CAMPO_AREA_REG_ID));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_COD_LIBRO,bloqPart.getField(DboTaBloqPartida.CAMPO_COD_LIBRO));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_CO_ACTO,bloqPart.getField(DboTaBloqPartida.CAMPO_CO_ACTO));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_ESTADO,bloqPart.getField(DboTaBloqPartida.CAMPO_ESTADO));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_FICHA,bloqPart.getField(DboTaBloqPartida.CAMPO_FICHA));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_NUM_PARTIDA,bloqPart.getField(DboTaBloqPartida.CAMPO_NUM_PARTIDA));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_NUM_TITU,bloqPart.getField(DboTaBloqPartida.CAMPO_NUM_TITU));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_NU_FOJA,bloqPart.getField(DboTaBloqPartida.CAMPO_NU_FOJA));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_NU_TOMO,bloqPart.getField(DboTaBloqPartida.CAMPO_NU_TOMO));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_OBJETO_SOL_ID,objetoSolID);
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_OFIC_REG_ID,bloqPart.getField(DboTaBloqPartida.CAMPO_OFIC_REG_ID));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_REG_PUB_ID,bloqPart.getField(DboTaBloqPartida.CAMPO_REG_PUB_ID));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_SISTEMA_ID,bloqPart.getField(DboTaBloqPartida.CAMPO_SISTEMA_ID));
					tituPendVerificada.setField(DboVerificaTituPend.CAMPO_TS_ULT_SYNC,bloqPart.getField(DboTaBloqPartida.CAMPO_TS_ULT_SYNC).equals("")?null:FechaUtil.stringTimeToOracleString(FechaUtil.stringmmddyyyytoddmmyyyy(bloqPart.getField(DboTaBloqPartida.CAMPO_TS_ULT_SYNC))));
					tituPendVerificada.add();
					
					// concatenar la relacion de titulos pendientes
					if(titulosPendientes.length()>0)
						titulosPendientes.append(",");
					titulosPendientes.append(bloqPart.getField(DboTaBloqPartida.CAMPO_ANO_TITU)).append("-").append(bloqPart.getField(DboTaBloqPartida.CAMPO_NUM_TITU));
					
					lTitulosPen.add(tituPen);
				}
			}else if (accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE)){
				if (isTrace(this)) trace("Recupera los bloqueos de partida", request);
				tituPendVerificada.clearAll();
				tituPendVerificada.setField(DboVerificaTituPend.CAMPO_NUM_PARTIDA, partida.getNumPartida());
				tituPendVerificada.setField(DboVerificaTituPend.CAMPO_OFIC_REG_ID, partida.getOficRegId());
				tituPendVerificada.setField(DboVerificaTituPend.CAMPO_REG_PUB_ID, partida.getRegPubId());
				tituPendVerificada.setField(DboVerificaTituPend.CAMPO_COD_LIBRO, partida.getCodLibro());
				tituPendVerificada.setField(DboVerificaTituPend.CAMPO_ESTADO, "1");
				tituPendVerificada.setField(DboVerificaTituPend.CAMPO_OBJETO_SOL_ID, objetoSolID);
	
				StringBuffer actosTitulo;
				
				for (Iterator i = tituPendVerificada.searchAndRetrieveList().iterator(); i.hasNext(); ) 
				{
					
					if (isTrace(this)) trace("Recupera los titulos que bloquean la partida", request);
	
	
					// llenamos los datos que tenemos de la tabla de lboqueo de partida
					DboVerificaTituPend bloqPart = (DboVerificaTituPend) i.next();
					TituloPendienteBean tituPen = new TituloPendienteBean();
					tituPen.setAaTitulo(bloqPart.getField(DboVerificaTituPend.CAMPO_ANO_TITU));
					tituPen.setNroTitulo(bloqPart.getField(DboVerificaTituPend.CAMPO_NUM_TITU));
					tituPen.setOficReg(partida.getOficRegDescripcion());
					tituPen.setZonaReg(partida.getRegPubDescripcion());
					
					//recupera actos del titulo
					MultiDBObject mDboActosTitu = new MultiDBObject(dconn);
	
	
					mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTitulo","ti");
					mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboActosTitulo","at");
					mDboActosTitu.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmActo","ac");
					
					mDboActosTitu.setForeignKey("ac",DboTmActo.CAMPO_COD_ACTO,"at",DboActosTitulo.CAMPO_COD_ACTO);
					
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_ANO_TITU, tituPen.getAaTitulo());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_NUM_TITU, tituPen.getNroTitulo());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_AREA_REG_ID, partida.getAreaRegistralId());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_OFIC_REG_ID, partida.getOficRegId());
					mDboActosTitu.setField("ti", DboTitulo.CAMPO_REG_PUB_ID, partida.getRegPubId());
					
					mDboActosTitu.setForeignKey("ti", DboTitulo.CAMPO_REFNUM_TITU, "at", DboActosTitulo.CAMPO_REFNUM_TITU);
					mDboActosTitu.setForeignKey("at", DboActosTitulo.CAMPO_COD_ACTO, "ac", DboTmActo.CAMPO_COD_ACTO);
					
					actosTitulo = new StringBuffer();
					
					if (isTrace(this)) trace("Recupera los actos del titulo y concatena en un string", request);
					for(Iterator j = mDboActosTitu.searchAndRetrieve().iterator(); j.hasNext();){
						mDboActosTitu = (MultiDBObject) j.next();
						if (actosTitulo.length()!=0) actosTitulo.append(", ");
						actosTitulo.append(mDboActosTitu.getField("ac",DboTmActo.CAMPO_DESCRIPCION));
					}
					
					tituPen.setActo(actosTitulo.toString());
					if (tituPen.getActo().length() == 0) {
						tituPen.setActo("NO DISPONIBLE");
					}
					
					// concatenar la relacion de titulos pendientes
					if(titulosPendientes.length()>0)
						titulosPendientes.append(",");
					titulosPendientes.append(tituPen.getAaTitulo()).append("-").append(tituPen.getNroTitulo());
					
					lTitulosPen.add(tituPen);
				}
			}
			//req.getSession().setAttribute(ATRIBUTO_TITULOS_PENDIENTES,titulosPendientes);
			req.getSession().setAttribute(ATRIBUTO_TITULOS_PENDIENTES,titulosPendientes.toString());
			//hphp:12/11
			if (isTrace(this)) trace("ponemos en sesion los titulos pendientes" + titulosPendientes.toString(),request);
						
			req.setAttribute("partida",partida);
			req.setAttribute("numPgnaTotal",String.valueOf(nroPagRef));
			if (lAsientosE.size() > 0) req.setAttribute("asientos",lAsientosE);
			if (asieFicha != null) req.setAttribute("ficha",asieFicha);
			if (lAsientosT.size() > 0) req.setAttribute("folios",lAsientosT);
			if (lTitulosPen.size() > 0) req.setAttribute("titulos",lTitulosPen);
			// termina proceso normal
			
			
		}

	public ControllerResponse runMuestraTituloVisualizacionState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int nroPagTotal = 0;		
		String servicio_id = "";

		try {
			init(request);
			validarSesion(request);


			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
	
			String numRefPart = req.getParameter("refnum_part");
			
			String accion = req.getParameter("accion");

			if ( (numRefPart==null) || (numRefPart.trim().equalsIgnoreCase("")) )
				throw new CustomException(Errors.EC_MISSING_PARAM, "El identificador de la partida es obligatorio.");

			//recupera la oficina registral de la partida
			String nombreOficina = (String)session.getAttribute(ATRIBUTO_OFIC_REG_NOMBRE);
			String codLibro = (String)session.getAttribute(ATRIBUTO_COD_LIBRO);
			String codGLA = (String)session.getAttribute(ATRIBUTO_COD_GLA);
			if (nombreOficina == null) 
			{
				if (isTrace(this)) trace("Recupera la oficina registral de la partida", request);
				MultiDBObject ofiRegPart = new MultiDBObject(dconn);
			
				ofiRegPart.addDBObj("gob.pe.sunarp.extranet.dbobj.DboPartida","pa");
				ofiRegPart.addDBObj("gob.pe.sunarp.extranet.dbobj.DboOficRegistral","or");
			
				ofiRegPart.setForeignKey("pa",DboPartida.CAMPO_OFIC_REG_ID,"or",DboOficRegistral.CAMPO_OFIC_REG_ID);
				ofiRegPart.setForeignKey("pa",DboPartida.CAMPO_REG_PUB_ID,"or",DboOficRegistral.CAMPO_REG_PUB_ID);
			
				ofiRegPart.setField("pa", DboPartida.CAMPO_REFNUM_PART, numRefPart);
			
				Iterator i = ofiRegPart.searchAndRetrieve().iterator();
				if (i.hasNext()) 
				{
					ofiRegPart = (MultiDBObject) i.next();
				}

				nombreOficina = ofiRegPart.getField("or", DboOficRegistral.CAMPO_NOMBRE);
				codLibro = ofiRegPart.getField("pa", DboPartida.CAMPO_COD_LIBRO);
				session.setAttribute(ATRIBUTO_OFIC_REG_NOMBRE, nombreOficina.trim());
				session.setAttribute(ATRIBUTO_COD_LIBRO, codLibro.trim());
			}

			// 30/11/05 - HP - inicio
			if(req.getParameter("noPgnaTotal")==null)
				nroPagTotal = 0;
			else
				nroPagTotal = Integer.parseInt(req.getParameter("noPgnaTotal"));
			// 30/11/05 - HP - fin
			
			// recuperamos el costo
			//hphp:2003/09/06
			//valido que "accion" sea nula
			if((accion!=null)&&((accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA))
			 ||(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE)))){
				//recuperar el costo (preferentemente de la sesion)
				if (isTrace(this)) trace("Recuperando costo de visualizacion", request);
				// 30/11/05 - HP - inicio
				// Servicio
				// --------------------------------------------------------------------------------
				// en función del libro (y del número de páginas) se determina el servicio_id
				// [si pertenece al cod_grupo_libro_area = 1 (Propiedad Inmueble Predial)
				//  ó
				//  si pertenece al cod_grupo_libro_area = 1 (Prenda Agrícola)]
				// 		si [Número de Páginas > 2]
				// 		entonces el servicio_id es 112
				//		sino el servicio_id es 111
				// sino el servicio_id es 110
				// --------------------------------------------------------------------------------`
				servicio_id = "";
				StringBuffer quebusq = new StringBuffer();
				quebusq.append("select * from grupo_libro_area_det where cod_grupo_libro_area in (1,5) and cod_libro=?");
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,codLibro);
				rset = pstmt.executeQuery();
				if(!rset.next()) {
					servicio_id = SERVICIO_COPIA_CERTIFICADA;
				} else {
					if (nroPagTotal>2) {
						servicio_id = SERVICIO_COPIA_CERTIFICADA_2;
					} else {
						servicio_id = SERVICIO_COPIA_CERTIFICADA_1;
					}
				}
				// 30/11/05 - HP - fin
				//Tarifario
				/*DboTarifa dboTarifa = new DboTarifa(dconn);
				dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, SERVICIO_COPIA_CERTIFICADA);
				if (!dboTarifa.find()) 
					throw new CustomException(EC_TARIFA_NO_DISPONIBLE);
				req.setAttribute("costo", dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
				*/
				//Inicio:jascencio:28/08/2007
				//CC:SUNARP-REGMOBCON-2006
				if(codLibro.equals(Constantes.CODIGO_LIBRO_RMC)){
					servicio_id=String.valueOf(Constantes.SERVICIO_CERTIFICADO_COPIA_LITERAL_RMC_BASE);
				}
				//Fin:jascencio

				quebusq = new StringBuffer();
				
				quebusq.append("SELECT t.prec_ofic, gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
				quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
				quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
				
				pstmt = null;
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,codLibro);
				// 30/11/05 - HP
				//pstmt.setString(2,SERVICIO_COPIA_CERTIFICADA);
				pstmt.setString(2,servicio_id);
				rset = pstmt.executeQuery();
				
				if(!rset.next())
					throw new CustomException(EC_TARIFA_NO_DISPONIBLE);
				//req.setAttribute("costo", rset.getString("prec_ofic"));
				//para que no muestre información de costo
				req.setAttribute("costo", "0");
				if (codGLA == null)
					session.setAttribute(ATRIBUTO_COD_GLA, rset.getString("cod_grupo_libro_area").trim());
					
			} else {
				if (isTrace(this)) trace("Recuperando costo de visualizacion", request);
				//Tarifario
				/*DboTarifa dboTarifa = new DboTarifa(dconn);
				dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, SERVICIO_VISUALIZAR_PARTIDA);
				if (!dboTarifa.find()) 
					throw new CustomException(EC_TARIFA_NO_DISPONIBLE);
				req.setAttribute("costo", dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
				*/
				StringBuffer quebusq = new StringBuffer();
				
				quebusq.append("SELECT t.prec_ofic, gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
				quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
				quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
				
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,codLibro);
				if(codLibro != null && codLibro.equals(Constantes.CODIGO_LIBRO_RMC)){
					pstmt.setString(2,Constantes.SERVICIO_VISUALIZA_PARTIDA_RMC);
				}else{
					pstmt.setString(2,SERVICIO_VISUALIZAR_PARTIDA);
				}
				
				rset = pstmt.executeQuery();
				
				if(!rset.next())
					throw new CustomException(EC_TARIFA_NO_DISPONIBLE);
				req.setAttribute("costo", rset.getString("prec_ofic"));
				if (codGLA == null)
					session.setAttribute(ATRIBUTO_COD_GLA, rset.getString("cod_grupo_libro_area").trim());
				
			}

			
			//vemos si hay imagen o no
			String indexClass = req.getParameter("indexClass");
			if (indexClass != null) 
			{
				req.setAttribute("hayImagen", "1");
				if (indexClass.equals(Constantes.INDEX_SUBCLASS_ASIENTO)) 
				{
					req.setAttribute("width", new Integer(PreparaImagen.ASIENTO_WIDTH));
					req.setAttribute("height", new Integer(PreparaImagen.ASIENTO_HEIGHT));
					req.setAttribute(ATRIBUTO_ZOOM_DEFAULT, new Integer(PreparaImagen.ASIENTO_ZOOM));
				} else if (indexClass.equals(Constantes.INDEX_SUBCLASS_FICHA)) {
					req.setAttribute("width", new Integer(PreparaImagen.FICHA_WIDTH));
					req.setAttribute("height", new Integer(PreparaImagen.FICHA_HEIGHT));
					req.setAttribute(ATRIBUTO_ZOOM_DEFAULT, new Integer(PreparaImagen.FICHA_ZOOM));
				} else {
					req.setAttribute("width", new Integer(PreparaImagen.FOLIO_WIDTH));
					req.setAttribute("height", new Integer(PreparaImagen.FOLIO_HEIGHT));
					req.setAttribute(ATRIBUTO_ZOOM_DEFAULT, new Integer(PreparaImagen.FOLIO_ZOOM));
				}
				//System.out.println(req.getParameter("noPgna"));
				//System.out.println(req.getAttribute("noPgna"));
				req.setAttribute("noPgna", req.getParameter("noPgna"));
				req.setAttribute("noPgnaTotal", req.getParameter("noPgnaTotal"));
				req.setAttribute("noPartida", req.getParameter("noPartida"));
			}


			response.setStyle("muestraCabeceraVisualizacion");


		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		return response;
	}


	public ControllerResponse runMuestraImagenState(ControllerRequest request, ControllerResponse response) throws ControllerException {
		//muestra la imagen de una pagina del asiento

		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletResponse res = (HttpServletResponse) ((ServletControllerRequest) request).getServletResponse();
		res.setContentType("image/gif");
		java.io.BufferedOutputStream out = null;


		boolean exito = false;
		String imagenError = IMAGEN_ERROR_GENERICO;


		try {
			init(request);
			validarSesion(request);
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);


			if (isTrace(this)) trace("Recuperando parametros", request);
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			String sObjetoID = req.getParameter("objetoID");
			String sNumPagina = req.getParameter("numPagina");
			String indexClass = req.getParameter("indexClass");
			/* inicio */
			// copia certificada
			String accion = req.getParameter("accion");
			String noSolicitud = req.getParameter("noSolicitud");
			//String objetoSolId = req.getParameter("objetoSolId");
			/* fin */
			
			// validar si esta accion esta permitida al usuario conectado

			if ((indexClass == null) || (indexClass.length() == 0)) {
				throw new CustomException(Errors.EC_MISSING_PARAM, "indexClass es obligatorio");
			}
			if ((sObjetoID == null) || (sObjetoID.length() == 0)) {
				throw new CustomException(Errors.EC_MISSING_PARAM, "objetoID es obligatorio");
			}
			if ((sNumPagina == null) || (sNumPagina.length() == 0)) {
				throw new CustomException(Errors.EC_MISSING_PARAM, "numPagina es obligatorio");
			}

			long objetoID;
			int numPagina;
			try {
				objetoID = Long.parseLong(sObjetoID);
				numPagina = Integer.parseInt(sNumPagina);
			} catch (Exception e) {
				throw new CustomException(Errors.EC_PARAM_MISSFORMED, "No se pudo entender los parametros: " + sObjetoID + "/" + sNumPagina);
			}


			// recuperamos los parametros de sesion
			if (isTrace(this)) trace("Recuperando parametros de sesion", request);
			UsuarioBean ub = ExpressoHttpSessionBean.getUsuarioBean(request);
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			boolean exoneradoCobro = ub.getExonPago();
			double saldo = ub.getSaldo();
			String usuario = ub.getUserId();
			AsientosVistos av = (AsientosVistos) session.getAttribute(ATRIBUTO_ASIENTOS_VISTOS);
			String TMSTMP_SYNCHRO = (String) session.getAttribute(ATRIBUTO_PARTIDA_TMSTMP_SYNCHRO);
			String REG_PUB_ID = (String) session.getAttribute(ATRIBUTO_REG_PUB_ID);
			String OFIC_REG_ID = (String) session.getAttribute(ATRIBUTO_OFIC_REG_ID);
			String COD_AREA = (String) session.getAttribute(ATRIBUTO_AREA_REGISTRAL);
			//2003/11/04:hphp
			String codLibro = (String)session.getAttribute(ATRIBUTO_COD_LIBRO);
			
			if (av == null) {
				av = new AsientosVistos();
				session.setAttribute(ATRIBUTO_ASIENTOS_VISTOS, av);
			}
			
			/*cjvc77: Añadido 03-02-2003*/
				boolean esFicha = false;
				if(indexClass.equals(Constantes.INDEX_SUBCLASS_FICHA))
					esFicha = true;
			/**/
			//cjvc77: modificado 03-02-2003
			boolean yaVisto = av.fueVisto(indexClass + (esFicha?String.valueOf(numPagina):""), objetoID);
			// antes: boolean yaVisto = av.fueVisto(indexClass, objetoID);
			
			// recuperamos el costo de la visualizacion
			if (isTrace(this)) trace("Recuperando costo de visualizacion", request);
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			if((accion!=null)&&(
			   (accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA))
			 ||(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE  )))){
			 	//dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, SERVICIO_COPIA_CERTIFICADA);
			 	// 30-11-05 - HP
			 	//no es necesario recuperar la tarifa
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, SERVICIO_VISUALIZAR_PARTIDA);
			 }else{
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, SERVICIO_VISUALIZAR_PARTIDA);
			 }
			//2003/11/04:hphp
			//especificar el criterio de libro_area
			DboGrupoLibroAreaDet dboGrupoLibroAreaDet = new DboGrupoLibroAreaDet();
			dboGrupoLibroAreaDet.setFieldsToRetrieve(DboGrupoLibroAreaDet.CAMPO_COD_GRUPO_LIBRO_AREA);
			dboGrupoLibroAreaDet.setField(DboGrupoLibroAreaDet.CAMPO_COD_LIBRO,codLibro);
			if (!dboGrupoLibroAreaDet.find()) {
				throw new CustomException(EC_TARIFA_NO_DISPONIBLE);
			}
			
			if(codLibro != null && codLibro.equals(Constantes.CODIGO_LIBRO_RMC)){
				
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, Constantes.SERVICIO_VISUALIZA_PARTIDA_RMC);
				dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA,Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC);
			}else{
				dboTarifa.setField(DboTarifa.CAMPO_COD_GRUPO_LIBRO_AREA,dboGrupoLibroAreaDet.getField(DboGrupoLibroAreaDet.CAMPO_COD_GRUPO_LIBRO_AREA));
			}
		 	
			
			if (!dboTarifa.find()) {
				throw new CustomException(EC_TARIFA_NO_DISPONIBLE);
			}


			String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
			double tarifa = Double.parseDouble(sTarifa);


			if ((!exoneradoCobro) && (!yaVisto) && (saldo < tarifa)) {
				imagenError = IMAGEN_ERROR_SALDO_INSUFICIENTE;
				throw new CustomException(EC_SALDO_INSUFICIENTE);
			}

			// si ya fue visto, y se puede sacar la imagen del cache del browser, se hace
			boolean cache = false;
			String etag = new StringBuffer(indexClass).append(objetoID).append(":").append(numPagina).toString();
			if (yaVisto) {
				if (isTrace(this)) trace("Revisamos si se puede sacar de cache del browser", request);
/*				String modifiedSince = req.getHeader("If-Modified-Since");
				trace("Header If-Modified-Since: " + modifiedSince, request);*/
				String noneMatch = req.getHeader("If-None-Match");
				if (isTrace(this)) trace("Header If-None-Match: " + noneMatch, request);
				if ((noneMatch != null) && (noneMatch.equals(etag)))
						cache = true;
			}


			if (cache) {
				if (isTrace(this)) trace("Ya fue visto y se puede sacar de cache", request);
				res.setStatus(res.SC_NOT_MODIFIED);
				exito = true; // si bien es cierto, podria fallar en las siguientes lineas, ya mandamos la imagen,
				
			} else {
				
				res.setHeader("ETag", etag);
				if (isTrace(this)) trace("Recuperando la imagen del content manager", request);
				
				//Inicio:jascencio:05/09/2007
				//CC: SUNARP-REGMOBCON-2006
				if (true){

					
					
					/**
					 * jacaceres - CM8 - 22/02/07
					 */
					//byte[] contents = CMProcessor.getInstance().retrieveImage(Constantes.CM_ID_WEB, indexClass, objetoID, numPagina);
					byte[] contents = CMProcessor.getInstance().retrieveImage(indexClass, objetoID, numPagina);
					/*** jacaceres - CM8 ***/
					
					/*java.io.File fileError = new java.io.File("C:\\img\\ASIENTO_2526756.tif");
					java.io.BufferedInputStream in = new java.io.BufferedInputStream(new java.io.FileInputStream(fileError));
					byte[] contents = new byte[(int) fileError.length()];
					in.read(contents);
					in.close();
					if (out == null)
							out = new java.io.BufferedOutputStream(res.getOutputStream());
					out.write(contents);*/

	/* IVAN
					if (isTrace(this)) trace("Decodificando el formato Tiff", request);
					Tiff tiff = new Tiff();
					tiff.read(contents);
					Image image = tiff.getImage(0);
					int imageWidthOriginal = image.getWidth(null);
					int imageHeightOriginal = image.getHeight(null);
					if (isTrace(this)) trace("Imagen decodificada. Ancho y Alto original: " + imageWidthOriginal + " " + imageHeightOriginal, request);
	*/

					double scale = 1;
					
						if (indexClass.equals(Constantes.INDEX_SUBCLASS_ASIENTO)) 
							scale = Propiedades.getInstance().getImageScaleAsiento();
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_FICHA)) 
						scale = Propiedades.getInstance().getImageScaleFicha();
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_FOLIO)) 
						scale = Propiedades.getInstance().getImageScaleFolio();
					
				
	/* IVAN
					int imageWidth = (int) Math.round(imageWidthOriginal * scale);
					int imageHeight = (int) Math.round(imageHeightOriginal * scale);
					session.setAttribute("imageWidth", new Integer(imageWidth));
					session.setAttribute("imageHeight", new Integer(imageHeight));
					if (isTrace(this)) trace("Ancho y Alto escalado: " + imageWidth + " " + imageHeight, request);


					GifImage gifImage = new GifImage(imageWidth, imageHeight);
					Graphics2D gifGraphics = gifImage.getGraphics();
	*/

					ImagenBean imagenBean = new ImagenBean();
					imagenBean.setNumPagina(req.getParameter("noPgna"));
					imagenBean.setTotPaginas(req.getParameter("noPgnaTotal"));
					imagenBean.setNumPartida(req.getParameter("noPartida"));
					imagenBean.setNomOficina(String.valueOf(req.getSession().getAttribute(ATRIBUTO_OFIC_REG_NOMBRE)));
					
					/* inicio */
					//copia certificada
					//int hora = 0;
					java.util.Date fechaVerificacion = null;
					if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA)){
						// calcula la hora de titulos pendientes en funcion a la hora del sistema
						fechaVerificacion = new java.util.Date(java.lang.System.currentTimeMillis());
						/* java.sql.Timestamp d = new java.sql.Timestamp(java.lang.System.currentTimeMillis());
						java.util.Calendar cal = java.util.Calendar.getInstance();
						cal.setTime(d);
						hora = cal.get(java.util.Calendar.HOUR_OF_DAY);*/
					}else if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE)){
						// calcula la hora de titulos pendientes en funcion a la hora de verificacion
						if(noSolicitud==null)
							throw new CustomException(Errors.EC_MISSING_PARAM, "No. Solicitud es Obligatorio");
						Certificado certificado = new Certificado(noSolicitud,conn,Constantes.ESTADO_SOL_POR_EXPEDIR);
						//System.out.println(certificado.getTs_verificacion());
						fechaVerificacion = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(certificado.getTs_verificacion());
					}
					if((accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA))
					 ||(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE))){
						// calcula la hora de titulos pendientes en funcion a la hora de verificacion
						imagenBean.setHoraTitulosPendientes("A Horas : 8:00 AM");
					 	// recupera los titulos pendientes de la tabla de verificacion correspondiente
					 	imagenBean.setTitulosPendientes((String) req.getSession().getAttribute(ATRIBUTO_TITULOS_PENDIENTES));
					 	imagenBean.setTipoImpresion(Constantes.IMPRESION_COPIA_CERTIFICADA);
					 	imagenBean.setFechaVerificacion(fechaVerificacion);
					 } else {
					 	imagenBean.setTipoImpresion(Constantes.IMPRESION_COPIA_SIMPLE);
					 }
					/* fin */
					
					Object image = PreparaImagen.getInstance().leerTIFF(contents, (float) scale, 0);

					session.setAttribute("imageWidth", new Integer(PreparaImagen.getInstance().getWidth(image)));
					session.setAttribute("imageHeight", new Integer(PreparaImagen.getInstance().getHeight(image)));
					//2003/12/10 kuma
					session.setAttribute("imageArea", COD_AREA);
					
					if (isTrace(this)) trace("Armando la imagen con los textos y fechas.", request);
					
					//PreparaImagen.getInstance().preparar(tiledGraphics, tiledImage.getWidth(), tiledImage.getHeight(), tarifa, TMSTMP_SYNCHRO, usuario,imagenBean);
					//PreparaImagen.getInstance().preparar(gifGraphics, image, imageWidth, imageHeight, tarifa, TMSTMP_SYNCHRO, usuario,imagenBean);


					if (isTrace(this)) trace("Codificando y enviando al usuario", request);
					out = new java.io.BufferedOutputStream(res.getOutputStream());

					//IVAN gifImage.encode(out);
					//javax.media.jai.JAI.create("encode", tiledImage, out, "PNG");
					PreparaImagen.getInstance().ponerTextosYMandarImagenAStream(out, image, tarifa, TMSTMP_SYNCHRO, usuario, imagenBean);

					//out.close();
					if (isTrace(this)) trace("Terminado codificacion y envio al usuario", request);					
					
				}else{
					
					//byte[] contents = CMProcessor.getInstance().retrieveImage(indexClass, objetoID, numPagina);
					double scale = 1;
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_ASIENTO)) 
						scale = Propiedades.getInstance().getImageScaleAsiento();
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_FICHA)) 
						scale = Propiedades.getInstance().getImageScaleFicha();
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_FOLIO)) 
						scale = Propiedades.getInstance().getImageScaleFolio();

					ImagenBean imagenBean = new ImagenBean();
					imagenBean.setNumPagina(req.getParameter("noPgna"));
					imagenBean.setTotPaginas(req.getParameter("noPgnaTotal"));
					imagenBean.setNumPartida(req.getParameter("noPartida"));
					imagenBean.setNomOficina(String.valueOf(req.getSession().getAttribute(ATRIBUTO_OFIC_REG_NOMBRE)));
					
					java.util.Date fechaVerificacion = null;
					if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA)){
						
						fechaVerificacion = new java.util.Date(java.lang.System.currentTimeMillis());
						
					}else if(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE)){
						
						if(noSolicitud==null)
							throw new CustomException(Errors.EC_MISSING_PARAM, "No. Solicitud es Obligatorio");
						Certificado certificado = new Certificado(noSolicitud,conn,Constantes.ESTADO_SOL_POR_EXPEDIR);
						
						fechaVerificacion = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(certificado.getTs_verificacion());
					}
					if((accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_VERIFICA))
					 ||(accion.equalsIgnoreCase(Constantes.ACCION_VISUALIZAR_PARTIDA_EXPIDE))){
						
						imagenBean.setHoraTitulosPendientes("A Horas : 8:00 AM");
					 	imagenBean.setTitulosPendientes((String) req.getSession().getAttribute(ATRIBUTO_TITULOS_PENDIENTES));
					 	imagenBean.setTipoImpresion(Constantes.IMPRESION_COPIA_CERTIFICADA);
					 	imagenBean.setFechaVerificacion(fechaVerificacion);
					 } else {
					 	imagenBean.setTipoImpresion(Constantes.IMPRESION_COPIA_SIMPLE);
					 }
				
					session.setAttribute("imageWidth", new Integer(42));
					session.setAttribute("imageHeight", new Integer(56));
					session.setAttribute("imageArea", COD_AREA);
					if (isTrace(this)) trace("Armando la imagen con los textos y fechas.", request);
					if (isTrace(this)) trace("Codificando y enviando al usuario", request);
					out = new java.io.BufferedOutputStream(res.getOutputStream());
					Object image = new Object();
					//PreparaImagen.getInstance().ponerTextosYMandarImagenAStream(out, image, tarifa, TMSTMP_SYNCHRO, usuario, imagenBean);
					if (isTrace(this)) trace("Terminado codificacion y envio al usuario", request);					
					
				}
				//Fin:jascencio
				


				exito = true; // si bien es cierto, podria fallar en las siguientes lineas, ya mandamos la imagen,
				// y no podemos mandar la imagen de error
				
				/**** PARTE DE TRANSACCIONES Y AUDITORIA *****/
				//if ((!exoneradoCobro) && (!yaVisto)) //Cambiado el 10/03/2003 por la linea de abajo
				
				if (!yaVisto) 
				{
					//llamar a "Transaccion"
					LogAuditoriaVisualizaPartidaBean bt = new LogAuditoriaVisualizaPartidaBean();
					
					//Datos generales
					 //Modificado por: Proyecto Filtros de Acceso
					 //Fecha: 02/10/2006
					 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
					 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
					 //Fecha: 08/10/2006             
					 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
					 //Fin Modificación
					bt.setUsuarioSession(ub);
					//datos particulares de esta transaccion
					bt.setOficRegId((String) session.getAttribute(ATRIBUTO_OFIC_REG_ID));
					bt.setRegPubId((String) session.getAttribute(ATRIBUTO_REG_PUB_ID));
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_ASIENTO)) 
						bt.setTipoImgVisualiz("1");
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_FICHA)) 
						bt.setTipoImgVisualiz("2");
					if (indexClass.equals(Constantes.INDEX_SUBCLASS_FOLIO)) 
						bt.setTipoImgVisualiz("3");
					bt.setNumDocViasualiz(indexClass + "-" + objetoID);
					bt.setNumPartida((String) session.getAttribute(ATRIBUTO_PARTIDA_EN_VISUALIZACION));
					//Inicio:jascencio:23/08/2007
					//CC: SUNARP-REGMOBCON-2006
					if(codLibro != null && codLibro.equals(Constantes.CODIGO_LIBRO_RMC)){
						bt.setCodigoServicio(TipoServicio.VISUALIZA_PARTIDA_RMC);
					}else{
						bt.setCodigoServicio(TipoServicio.VISUALIZACION_PARTIDA);
					}
					//Fin:jascencio

					//Tarifario
					bt.setCodigoGLA(Integer.parseInt(((String) session.getAttribute(ATRIBUTO_COD_GLA)).trim()));
					
	
					//cjvc77 20021218
					/*
						Job004 j = new Job004();
						j.setBean(bt);
						Thread llamador1 = new Thread(j);
						llamador1.start();					
					*/
					
					// 18/11/2003
					// antes de cobrarle verificamos otra vez si la imagen ya fue vista
					yaVisto = av.fueVisto(indexClass + (esFicha?String.valueOf(numPagina):""), objetoID);
					
					/**
					 *  inicio, dbravo: 15/06/2007
					 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
					 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
					 *  			   inicialmente el Job003.
					 */
					PrepagoBean prepagoBean = new PrepagoBean();
			  		if ((!yaVisto)&&(Propiedades.getInstance().getFlagTransaccion()==true))
						 prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					// 18/11/2003
					// inmediatamente despues de cobrarle al cliente, agregamos el asiento como ya visto
					if (isTrace(this)) trace("Agregamos al asiento como ya visto", request);
					//cjvc77: Modificado 03-02-2003
					av.addVisto(indexClass +  (esFicha?String.valueOf(numPagina):""), objetoID);
					//antes: av.addVisto(indexClass, objetoID);
					
					if (isTrace(this)) trace("Actualizamos el saldo en sesion", request);
/*Cambiado el 10/03/2003 las siguientes tres lineas
				}
				if (!yaVisto)
				{
*/					
					if (isTrace(this)) trace("Registrando uso de servicio", request);
					
					Job004 j = new Job004();
					j.setUsuario(ub);
					j.setCodigoServicio(Integer.parseInt(SERVICIO_VISUALIZAR_PARTIDA));
					j.setRegPubId(REG_PUB_ID);
					j.setOficRegId(OFIC_REG_ID);
					j.setArea(COD_AREA);
					j.setCostoServicio(prepagoBean.getMontoBruto());
					/**
					 *  fin, dbravo: 15/06/2007
					 */
					
					Thread llamador1 = new Thread(j);
					llamador1.start();
				
					if (isTrace(this)) trace("Agregamos al asiento como ya visto", request);
					//cjvc77: Modificado 03-02-2003
					av.addVisto(indexClass +  (esFicha?String.valueOf(numPagina):""), objetoID);
					//antes: av.addVisto(indexClass, objetoID);
				}
			}
			conn.commit();
		} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
		} catch (LoggeableException e) {
			log(e.getErrorCode(), e.getOtroMensaje(), request);
			principal(request);
			rollback(conn, request);
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
		}


		try {
			if (!exito) {
				java.io.File fileError = new java.io.File(getServlet().getServletContext().getRealPath("/"), imagenError);
				java.io.BufferedInputStream in = new java.io.BufferedInputStream(new java.io.FileInputStream(fileError));
				byte[] content = new byte[(int) fileError.length()];
				in.read(content);
				in.close();
				if (out == null)
						out = new java.io.BufferedOutputStream(res.getOutputStream());
				out.write(content);
				//out.close();
			}
		} catch (Throwable t) {
			log(Constantes.EC_GENERIC_ERROR, "", t, request);
		} finally {
			try {
				if (out != null) out.close();
			} catch (Exception e) {}
			response.setCustomResponse(true);
			response.setStyle(null);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	public ControllerResponse runImpresionState(ControllerRequest request, ControllerResponse response) throws ControllerException 
	{
		response.setStyle("impresion");
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		init(request);
		try
		{
		validarSesion(request);
		}
		catch (Throwable t)
		{
			req.setAttribute("mensaje1","Su sesión ha finalizado. Reingrese nuevamente.");
			response.setStyle("pantallaFinal");
		}
		return response;
	}
	
	/**
	 * Búsqueda Directa de Partidas - Método de Inicio 
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runSolicitarFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
		
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		PreparedStatement pstmt = null;
		ResultSet rset = null;


		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			//Tarifario
			/*
			//obtener datos para comboBox
			DboTmLibro dbo3 = new DboTmLibro(dconn);
			StringBuffer sb = new StringBuffer();
			//-tomo libro
			java.util.ArrayList arreglo3 = new java.util.ArrayList();
			sb.delete(0,sb.length());
			sb.append(DboTmLibro.CAMPO_COD_LIBRO).append("|").append(DboTmLibro.CAMPO_DESCRIPCION).append("|");
			sb.append(DboTmLibro.CAMPO_AREA_REG_ID);
			dbo3.setFieldsToRetrieve(sb.toString());
			dbo3.setField(DboTmLibro.CAMPO_ESTADO, "1");
			java.util.ArrayList arr3 = dbo3.searchAndRetrieveList(DboTmLibro.CAMPO_DESCRIPCION);
			for (int i = 0; i < arr3.size(); i++) 
			{
				Value05Bean b = new Value05Bean();
				DboTmLibro d3 = (DboTmLibro) arr3.get(i);
				b.setValue01(d3.getField(DboTmLibro.CAMPO_COD_LIBRO));
				b.setValue02(d3.getField(DboTmLibro.CAMPO_DESCRIPCION));
				b.setValue03(d3.getField(DboTmLibro.CAMPO_AREA_REG_ID));
				arreglo3.add(b);
			}
			*/
			//fin Tarifario
			
			//Tarifario
			java.util.ArrayList arreglo3 = new java.util.ArrayList();
			
			StringBuffer quebusq = new StringBuffer();
			
			//Query busqueda de combo
			quebusq.append("SELECT l.cod_libro, l.descripcion, l.area_reg_id, glad.cod_grupo_libro_area ");
			quebusq.append("FROM tm_libro l, grupo_libro_area_det glad, tarifa t ");
			quebusq.append("WHERE l.cod_libro = glad.cod_libro AND glad.cod_grupo_libro_area = t.cod_grupo_libro_area  ");
			quebusq.append("AND ( t.servicio_id = ? or t.servicio_id = ? ) and glad.cod_grupo_libro_area!='5' AND  glad.cod_grupo_libro_area!='10'");// eliminamos libros de prenda agricola y de minera
			
			pstmt = conn.prepareStatement(quebusq.toString());
			pstmt.setInt(1,Constantes.SERVICIO_CONSULTAR_PARTIDA);
			pstmt.setInt(2,Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC);
			
			rset = pstmt.executeQuery();
			
			ArrayList resultado = new ArrayList();
			boolean encontro = false;
			while (rset.next())
			{
				Value05Bean b = new Value05Bean();
				b.setValue01(rset.getString("cod_libro"));
				b.setValue02(rset.getString("descripcion"));
				b.setValue03(rset.getString("area_reg_id"));
				b.setValue04(rset.getString("cod_grupo_libro_area"));
				arreglo3.add(b);
			}
			//fin Tarifario
			
			req.setAttribute("arr3", arreglo3);
			//Retirado por cambio de busqueda 10/10/2003
			//req.setAttribute("arrAreaRegistral", Tarea.getComboAreasRegistrales(dconn));
			//req.setAttribute("arrAreaLibro", Tarea.getComboAreaLibro(dconn,1));
			//Tarifario
			req.setAttribute("arrAreaLibro", Tarea.getComboAreaLibro(conn,Constantes.SERVICIO_CONSULTAR_PARTIDA, Constantes.BUSQUEDA_DIRECTA_PARTIDA_RMC));
			response.setStyle("formularioBusqueda");
		} //try
		catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(e.getForward());
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(pstmt);
			pool.release(conn);
			end(request);
		}
		return response;
	}
	
	
	/**
	 * Búsqueda Directa
	 * Buscar por Número de Partida
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarXNroPartidaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException 
	{
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		
		InputBusqDirectaBean inputBean=null;
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);

			// Inicio:mgarate:30/05/2007
			   String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=2";
			// Fin:mgarate:30/05/2007
			
			//recojer datos de entrada
			inputBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);

			req.setAttribute("outNumber", inputBean.getNumeroPartida());


			//_empieza busqueda
			StringBuffer q  = new StringBuffer();
			StringBuffer q2 = new StringBuffer();
			StringBuffer qc = new StringBuffer();
					
			q.append(" SELECT ");
			q2.append(" SELECT ");
			qc.append(" SELECT ");


			//2003-07-31 enviar el estado al JSP
			q.append(" PARTIDA.ESTADO as estado, ");
			q.append(" REGIS_PUBLICO.SIGLAS as siglas, ");
			q.append(" OFIC_REGISTRAL.NOMBRE as nombre, ");
			//2005-11-30 enviar el area_reg_id al JSP
			q.append(" PARTIDA.area_reg_id as area_reg_id, ");
			q.append(" PARTIDA.REFNUM_PART as refnum_part, ");
			q.append(" PARTIDA.NUM_PARTIDA as num_partida, ");
			q.append(" PARTIDA.COD_LIBRO as cod_libro ");
			
			q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
			qc.append(" count(partida.refnum_part) ");
			
			
			q.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad, TM_AREA_REGISTRAL area ");
			q2.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
			qc.append(" FROM REGIS_PUBLICO,OFIC_REGISTRAL,PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
			
			
			q.append(" WHERE ");
			q2.append(" WHERE ");
			qc.append(" WHERE ");
			
			
			q.append(" PARTIDA.NUM_PARTIDA = '").append(inputBean.getNumeroPartida()).append("'");
			q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q.append(" and PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("' ");
			q.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("' ");
			//Tarifario
			q.append(" and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
			q.append(" and partida.cod_libro = glad.cod_libro  ");
			q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(q);


			q2.append(" PARTIDA.NUM_PARTIDA = '").append(inputBean.getNumeroPartida()).append("'");
			q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q2.append(" and PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("' ");
			q2.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("' ");
			//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q2.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(q2);
			
			qc.append(" PARTIDA.NUM_PARTIDA = '").append(inputBean.getNumeroPartida()).append("'");
			qc.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			qc.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			qc.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			qc.append(" and PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("' ");
			qc.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("' ");
			//qc.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			qc.append(" and partida.cod_libro = glad.cod_libro  ");
			qc.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			qc.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(qc);
			
			
			q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");


			Propiedades propiedades = Propiedades.getInstance();
			int conteo=0;
			if (inputBean.getFlagPagineo()==false)
			{
			
				if (isTrace(this)) System.out.println("___verqueryCOUNT_A___"+qc.toString());
			
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qc.toString());
				boolean bc = rsetc.next();
				
				conteo = rsetc.getInt(1);
			
			
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
			
			
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}
			
			
			if (isTrace(this)) System.out.println("___verquery___"+q.toString());


			//UsoServicio_________________________________
			/**
			 *  inicio, dbravo: 15/06/2007
			 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
			 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
			 *  			   inicialmente el Job002.
			 *
			if (inputBean.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				*
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			/**
			 *  fin, dbravo: 15/06/2007
			 */

			//descripcion area registral
			/*DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,inputBean.getAreaRegistral());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
			*/

	
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			if (inputBean.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (inputBean.getSalto() - 1));
			String descripcionAreaRegistral = inputBean.getDescGrupoLibroArea();
			
			boolean b = rset.next();


			ArrayList resultado = new ArrayList();
			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);
			
			
			StringBuffer sb = new StringBuffer();
			
			
			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				// 2005-11-30
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
				
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				// 2003-07-31 enviar el estado al JSP
				partidaBean.setEstado(rset.getString("estado"));
				
				//ficha
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(dboFicha.CAMPO_FICHA).append("|");
				sb.append(dboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
				if (dboFicha.find() == true) 
						{
							partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
							String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
							int nbis = 0;
							try {
								nbis = Integer.parseInt(bis);
							}
							catch (NumberFormatException n)
							{
								nbis =0;
							}
							if (nbis>=1)
								partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
						}	
						
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
				if (dboTomoFolio.find() == true) 
						{
							partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
							partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
			
			
							String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
							String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);
			
			
							if (bist.trim().length() > 0)
									partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
			
			
							if (bisf.trim().length() > 0)
									partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
									
							//28dic2002
							//quitar el caracter "9" del inicio del tomoid
								if (partidaBean.getTomoId().length()>0)
								{
									if (partidaBean.getTomoId().startsWith("9"))
										{
											String ntomo = partidaBean.getTomoId().substring(1);
											partidaBean.setTomoId(ntomo);
										}
								}						
						}
			
			
				//descripcion libro
				dboTmLibro.clearAll();
				dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
				dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
				if (dboTmLibro.find() == true)
					partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));								
			
			
			
			
				resultado.add(partidaBean);
				conta++;
				b = rset.next();	
						if (conta==propiedades.getLineasPorPag())
					{
						if(b==true)	
							haySiguiente = true;
						
						break;
					}		
			}//while (b==true)

			FormOutputBuscarPartida output = new FormOutputBuscarPartida();
			output.setResultado(resultado);
			if (inputBean.getFlagPagineo()==false)
				output.setCantidadRegistros(String.valueOf(conteo));
			else
				output.setCantidadRegistros(inputBean.getCantidad());
			
			
			//calcular numero para boton "retroceder pagina"		
			if (inputBean.getSalto()==1)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(inputBean.getSalto()-1);
				
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(inputBean.getSalto()+1);
				
			//calcular regs del x al y
			int del = ((inputBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);


			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, 1);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			req.setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			req.setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			req.setAttribute("fechaAct",fechaAct);

			output.setAction(req.getContextPath() + "//Publicidad.do?state=buscarXNroPartida");			
			req.setAttribute("output", output);
			
			// Inicio:mgarate:30/05/2007
			   req.setAttribute("criterioBusqueda",criterioBusqueda);
			   req.setAttribute("flagCertBusq",inputBean.getCodGrupoLibroArea());			
			// Fin:mgarate:30/05/2007			
			
		    /*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", inputBean.getVerifica());
			/*fin:dbravo:14/09/2007*/   
			   
			response.setStyle("resultadoBusqueda1");
			
			
			if (inputBean.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				
				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Constantes.CONSULTA_PARTIDA);
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(inputBean.getCodGrupoLibroArea()));
				bt.setTipoBusq(""+Constantes.CONSULTA_PARTIDA);
				//bt.setTipoBusq();
				//datos particulares de esta transaccion
				bt.setTipoConsPartida("1");
				bt.setLibTomFol(null);
				bt.setNumPartFic(inputBean.getNumeroPartida());
				bt.setOficRegId(inputBean.getOficRegId());
				bt.setRegPubId(inputBean.getRegPubId());
				/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();						
				*/

				/**
				 *  inicio, dbravo: 15/06/2007
				 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				 *  			   inicialmente el Job003.
				 */
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
				  PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if (inputBean.getFlagPagineo()==false)
				{
					/*
					validar que el usuario NO sea de zona WEB
					*/
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						//estamos en la primera llamada
						//enviamos TODOS los registros encontrados
						//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							
							Thread llamador1 = new Thread(j);
							llamador1.start();
					}
				}
				
			  }
			  
			  /**
			   *  fin, dbravo: 15/06/2007
			   */
			  
			}
			
			conn.commit();


			if (inputBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}


		
		} //try


		catch (CustomException e) 
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					response.setStyle("pantallaFinal");
					req.setAttribute("destino","back");
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
					try{
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");				
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	}

	/**
	 * Búsqueda Directa de Partidas. Buscar por Ficha 
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarXFichaState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
			
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;


		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);


		//obtener usuario de la sesion				
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		
		InputBusqDirectaBean inputBean=null;		
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;

		try {
			init(request);
			validarSesion(request);

			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			// Inicio:mgarate:30/05/2007
			String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=3";
			// Fin:mgarate:30/05/2007
			
			//recojer datos de entrada
			inputBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);
			req.setAttribute("outNumber", inputBean.getNumeroFicha());
			
			//_empieza busqueda
			StringBuffer q  = new StringBuffer();
			StringBuffer q2 = new StringBuffer();
			StringBuffer qc = new StringBuffer();
			q.append(" SELECT /*+ordered */ ");
			q2.append(" SELECT /*+ordered */ ");
			qc.append(" SELECT /*+ordered */ ");

			// 2003-07-31 enviar el estado al JSP
			q.append(" PARTIDA.ESTADO as estado, ");
			q.append(" REGIS_PUBLICO.SIGLAS as siglas, ");
			q.append(" OFIC_REGISTRAL.NOMBRE as nombre, ");
			// 2005-11-30
			q.append(" PARTIDA.area_reg_id as area_reg_id, ");
			
			q.append(" PARTIDA.REFNUM_PART as refnum_part, ");
			q.append(" PARTIDA.NUM_PARTIDA as num_partida, ");
			q.append(" PARTIDA.COD_LIBRO as cod_libro, ");
			q.append(" AREA.NOMBRE as descripcion ");
			//Tarifario

			q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
			qc.append(" count(partida.refnum_part) ");

			q.append(" FROM FICHA,PARTIDA, REGIS_PUBLICO,OFIC_REGISTRAL,grupo_libro_area_det glad, grupo_libro_area gla, TM_AREA_REGISTRAL area ");
			q2.append(" FROM FICHA,PARTIDA, REGIS_PUBLICO,OFIC_REGISTRAL, grupo_libro_area_det glad, grupo_libro_area gla ");
			qc.append(" FROM FICHA,PARTIDA, REGIS_PUBLICO,OFIC_REGISTRAL, grupo_libro_area_det glad, grupo_libro_area gla ");

			q.append(" WHERE ");
			q2.append(" WHERE ");
			qc.append(" WHERE ");

			q.append(" FICHA.FICHA = '").append(inputBean.getNumeroFicha()).append("' ");
			q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q.append(" AND FICHA.REFNUM_PART = PARTIDA.REFNUM_PART ");
			q.append(" and PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("' ");
			q.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("' ");
			//Tarifario
			q.append(" and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
			q.append(" and partida.cod_libro = glad.cod_libro  ");
			q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(q);
			
			q2.append(" FICHA.FICHA = '").append(inputBean.getNumeroFicha()).append("' ");
			q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q2.append(" AND FICHA.REFNUM_PART = PARTIDA.REFNUM_PART ");
			q2.append(" and PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("' ");
			q2.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("' ");
			//q2.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q2.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(q2);
			
			qc.append(" FICHA.FICHA = '").append(inputBean.getNumeroFicha()).append("' ");
			qc.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			qc.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			qc.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			qc.append(" AND FICHA.REFNUM_PART = PARTIDA.REFNUM_PART ");
			qc.append(" and PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("' ");
			qc.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("' ");
			//qc.append(" and AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			qc.append(" and partida.cod_libro = glad.cod_libro  ");
			qc.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			qc.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(qc);
			
			q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");

			Propiedades propiedades = Propiedades.getInstance();

			int conteo=0;
			if (inputBean.getFlagPagineo()==false)
			{
				if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qc.toString());
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qc.toString());
				boolean bc = rsetc.next();
				conteo = rsetc.getInt(1);
				
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);	
			}
			if (isTrace(this)) System.out.println("___verquery___"+q.toString());

			//UsoServicio_________________________________
			/**
			 *  inicio, dbravo: 15/06/2007
			 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
			 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
			 *  			   inicialmente el Job003.
			 *
			if (inputBean.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				*
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			**/

			//descripcion area registral
			/*
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,inputBean.getAreaRegistral());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
			*/
			//String descripcionAreaRegistral = inputBean.getDescGrupoLibroArea();
	
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			if (inputBean.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (inputBean.getSalto() - 1));

			boolean b = rset.next();

			ArrayList resultado = new ArrayList();
			
			
			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);
			
			StringBuffer sb = new StringBuffer();

			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				//Tarifario
				String descripcionAreaRegistral = rset.getString("descripcion");
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				// 2005-11-30
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
				
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				// 2003-07-31 enviar el estado al JSP
				partidaBean.setEstado(rset.getString("estado"));
				
				//ficha
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(dboFicha.CAMPO_FICHA).append("|");
				sb.append(dboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
				if (dboFicha.find() == true) 
						{
							partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
							String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
							int nbis = 0;
							try {
								nbis = Integer.parseInt(bis);
							}
							catch (NumberFormatException n)
							{
								nbis =0;
							}
							if (nbis>=1)
								partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
						}	
						
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
				if (dboTomoFolio.find() == true) 
						{
							partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
							partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
			
			
							String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
							String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);
			
			
							if (bist.trim().length() > 0)
									partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
			
			
							if (bisf.trim().length() > 0)
									partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
									
							//28dic2002
							//quitar el caracter "9" del inicio del tomoid
								if (partidaBean.getTomoId().length()>0)
								{
									if (partidaBean.getTomoId().startsWith("9"))
										{
											String ntomo = partidaBean.getTomoId().substring(1);
											partidaBean.setTomoId(ntomo);
										}
								}						
						}
			
			
				//descripcion libro
				dboTmLibro.clearAll();
				dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
				dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
				if (dboTmLibro.find() == true)
					partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));								
			
			
				resultado.add(partidaBean);
				conta++;
				b = rset.next();
						if (conta==propiedades.getLineasPorPag())
					{
						if(b==true)	
							haySiguiente = true;
						
						break;
					}			
			}//while (b==true)

			FormOutputBuscarPartida output = new FormOutputBuscarPartida();
			output.setResultado(resultado);
			if (inputBean.getFlagPagineo()==false)
				output.setCantidadRegistros(String.valueOf(conteo));
			else
				output.setCantidadRegistros(inputBean.getCantidad());

			//calcular numero para boton "retroceder pagina"		
			if (inputBean.getSalto()==1)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(inputBean.getSalto()-1);
				
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(inputBean.getSalto()+1);


			//calcular regs del x al y
			int del = ((inputBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);


			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, 1);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			req.setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			req.setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			req.setAttribute("fechaAct",fechaAct);


			output.setAction("/webapp/extranet/Publicidad.do?state=buscarXFicha");
			req.setAttribute("output", output);
			
			// Inicio:mgarate:30/05/2007
			req.setAttribute("criterioBusqueda",criterioBusqueda);
			req.setAttribute("flagCertBusq",inputBean.getCodGrupoLibroArea());
			// Fin:mgarate:30/05/2007
			
		    /*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", inputBean.getVerifica());
			/*fin:dbravo:14/09/2007*/ 
			   
			response.setStyle("resultadoBusqueda1");
			
			if (inputBean.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				
				//Datos generales
				 //Modificado por: Proyecto Filtros de Acceso
				 //Fecha: 02/10/2006
				 //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				 bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				 //Fecha: 08/10/2006             
				 bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));
				 //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setTipoBusq(""+Constantes.CONSULTA_PARTIDA);
				bt.setCodigoServicio(Constantes.CONSULTA_PARTIDA);
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(inputBean.getCodGrupoLibroArea()));
				//bt.setTipoBusq();				
				//datos particulares de esta transaccion
				bt.setTipoConsPartida("2");
				bt.setLibTomFol(null);
				bt.setNumPartFic(inputBean.getNumeroFicha());
				bt.setOficRegId(inputBean.getOficRegId());
				bt.setRegPubId(inputBean.getRegPubId());
				/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();						
					*/
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (inputBean.getFlagPagineo()==false)
					{
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							//estamos en la primera llamada
							//enviamos TODOS los registros encontrados
							//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							
							Thread llamador1 = new Thread(j);
							llamador1.start();
						}
					}
			  }
			}
			
			conn.commit();
			
			if (inputBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{				
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);
			}


		
		} //try


		catch (CustomException e) 
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					response.setStyle("pantallaFinal");
					req.setAttribute("destino","back");
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
					try{
						rollback(conn, request);


					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	}

	private void appendCondicionEstadoPartida(StringBuffer q) {
		//q.append(" and PARTIDA.ESTADO in ('1', '3', '4') ");
		q.append(" and PARTIDA.ESTADO != '2' ");
	}

	/**
	 * Búsqueda Directa de Partidas. Buscar por Tomo Folio
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarXTomoFolioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
			
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;

		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);

		//obtener usuario de la sesion				
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		
		InputBusqDirectaBean inputBean=null;
		Statement stmtc   = null;
		ResultSet rsetc   = null;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try {
			init(request);
			validarSesion(request);


			conn = pool.getConnection();
			conn.setAutoCommit(false);
			DBConnection dconn = new DBConnection(conn);
			
			// Inicio:mgarate:29/05/2007
			String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=1";
			// Fin:mgarate:29/05/2007
			
			//recojer datos de entrada
			inputBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);
	
			//_empieza busqueda
			StringBuffer q  = new StringBuffer();
			StringBuffer q2 = new StringBuffer();
			StringBuffer qc = new StringBuffer();


			q.append(" SELECT /*+ORDERED INDEX(TOMO_FOLIO INDX_TOMO_FOJA) INDEX(PARTIDA INDX_PARTIDA_LIBRO) */");
			q2.append(" SELECT /*+ORDERED INDEX(TOMO_FOLIO INDX_TOMO_FOJA) INDEX(PARTIDA INDX_PARTIDA_LIBRO) */");
			qc.append(" SELECT /*+ORDERED INDEX(TOMO_FOLIO INDX_TOMO_FOJA) INDEX(PARTIDA INDX_PARTIDA_LIBRO) */");
			
			//2003-07-31 enviar el estado al JSP
			q.append(" PARTIDA.ESTADO as estado, ");
			q.append(" REGIS_PUBLICO.SIGLAS as siglas, ");
			q.append(" OFIC_REGISTRAL.NOMBRE as nombre, ");
			//2005-11-30
			q.append(" PARTIDA.area_reg_id as area_reg_id, ");
			
			q.append(" PARTIDA.REFNUM_PART as refnum_part, ");
			q.append(" PARTIDA.NUM_PARTIDA as num_partida, ");
			q.append(" PARTIDA.COD_LIBRO as cod_libro,");
			q.append(" PARTIDA.REG_PUB_ID as regPubId, ");
			q.append(" PARTIDA.OFIC_REG_ID as oficRegId ");
			
			q2.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
			qc.append(" count(partida.refnum_part) ");
			
			
			q.append("  FROM TOMO_FOLIO, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad, TM_AREA_REGISTRAL area  ");
			q2.append(" FROM TOMO_FOLIO, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad  ");
			qc.append(" FROM TOMO_FOLIO, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, grupo_libro_area gla, grupo_libro_area_det glad  ");
			
			
			q.append(" WHERE ");
			q2.append(" WHERE ");
			qc.append(" WHERE ");


			//___nt
			StringBuffer nt = new StringBuffer();
			if(inputBean.getTomo().startsWith("0"))
				{
					String otroNumTomo = new StringBuffer("9").append(inputBean.getTomo().substring(1)).toString();
					nt.append(" in ('").append(inputBean.getTomo()).append("','").append(otroNumTomo).append("')");
				}
				else
				{
					nt.append("='").append(inputBean.getTomo()).append("'");
				}
			
			
			q.append(" TOMO_FOLIO.REFNUM_PART = PARTIDA.REFNUM_PART  ");	
			q.append(" and  PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("'  ");
			q.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("'  ");
			//Tarifario
			q.append(" and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
			q.append(" and partida.cod_libro = glad.cod_libro  ");
			q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			q.append(" AND PARTIDA.COD_LIBRO = '").append(inputBean.getLibro()).append("'");
			q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID  ");
			q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID  ");
			q.append(" and TOMO_FOLIO.NU_TOMO ").append(nt.toString());
			q.append(" AND TOMO_FOLIO.NU_FOJA = '").append(inputBean.getFolio()).append("' "); 
			appendCondicionEstadoPartida(q);
			
			
			q2.append(" TOMO_FOLIO.REFNUM_PART = PARTIDA.REFNUM_PART  ");
			q2.append(" and  PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("'  ");
			q2.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("'  ");
			//q2.append(" and PARTIDA.AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and partida.cod_libro = glad.cod_libro  ");
			q2.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q2.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			q2.append(" AND PARTIDA.COD_LIBRO = '").append(inputBean.getLibro()).append("'");
			q2.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q2.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID  ");
			q2.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID  ");
			q2.append(" and TOMO_FOLIO.NU_TOMO ").append(nt.toString());
			q2.append(" AND TOMO_FOLIO.NU_FOJA = '").append(inputBean.getFolio()).append("' "); 
			appendCondicionEstadoPartida(q2);


			qc.append(" TOMO_FOLIO.REFNUM_PART = PARTIDA.REFNUM_PART  ");
			qc.append(" and  PARTIDA.REG_PUB_ID ='").append(inputBean.getRegPubId()).append("'  ");
			qc.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBean.getOficRegId()).append("'  ");
			//qc.append(" and PARTIDA.AREA_REG_ID = gla.cod_area and partida.cod_libro = glad.cod_libro  ");
			qc.append(" and partida.cod_libro = glad.cod_libro  ");
			qc.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			qc.append(" and gla.cod_grupo_libro_area ='").append(inputBean.getCodGrupoLibroArea()).append("' ");
			qc.append(" AND PARTIDA.COD_LIBRO = '").append(inputBean.getLibro()).append("'");
			qc.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			qc.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID  ");
			qc.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID  ");
			qc.append(" and TOMO_FOLIO.NU_TOMO ").append(nt.toString());
			qc.append(" and TOMO_FOLIO.NU_FOJA = '").append(inputBean.getFolio()).append("' "); 
			appendCondicionEstadoPartida(qc);


			q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");


			Propiedades propiedades = Propiedades.getInstance();
			int conteo=0;
			if (inputBean.getFlagPagineo()==false)
			{
				if (isTrace(this)) System.out.println("___verqueryCOUNT_A__"+qc.toString());
				stmtc   = conn.createStatement();
				rsetc   = stmtc.executeQuery(qc.toString());
				boolean bc = rsetc.next();
				conteo = rsetc.getInt(1);
				
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2) )
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}


			if (isTrace(this)) System.out.println("___verquery___"+q.toString());


			//UsoServicio_________________________________
			/**
			 *  inicio, dbravo: 15/06/2007
			 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
			 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
			 *  			   inicialmente el Job003.
			 *
			if (inputBean.getFlagPagineo()==false)
			{
				/*
				validar que el usuario NO sea de zona WEB
				*
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					//estamos en la primera llamada
					//enviamos TODOS los registros encontrados
					//a otro Thread para que registre el UsoServicio
						Job002 j = new Job002();
						j.setQuery(q2.toString());
						j.setUsuario(usuario);
						j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
						Thread llamador1 = new Thread(j);
						llamador1.start();
				}
			}
			**/


			//descripcion area registral
			/*
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,inputBean.getAreaRegistral());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			if (dboTmAreaRegistral.find() == true)
			*/
			String descripcionAreaRegistral = inputBean.getDescGrupoLibroArea();
				
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			if (inputBean.getSalto()>1)
				rset.absolute(propiedades.getLineasPorPag() * (inputBean.getSalto() - 1));

			boolean b = rset.next();

			ArrayList resultado = new ArrayList();

			DboFicha dboFicha = new DboFicha(dconn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
			DboTmLibro dboTmLibro = new DboTmLibro(dconn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
			DboParticLibro dboParticLibro = new DboParticLibro(dconn);
			DboIndPrtc dboIndPrtc = new DboIndPrtc(dconn);
			DboPrtcNat dboPrtcNat = new DboPrtcNat(dconn);
			DboPrtcJur dboPrtcJur = new DboPrtcJur(dconn);

			StringBuffer sb = new StringBuffer();

			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				//_completar los detalles de la partida encontrada
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				String regPubId   = rset.getString("regPubId");
				String oficRegId  = rset.getString("oficRegId");
				
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				// 2005-11-30
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
			
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				// 2003-07-31 enviar el estado al JSP
				partidaBean.setEstado(rset.getString("estado"));
				
				//ficha
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(dboFicha.CAMPO_FICHA).append("|");
				sb.append(dboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
				if (dboFicha.find() == true) 
						{
							partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
							String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
							int nbis = 0;
							try {
								nbis = Integer.parseInt(bis);
							}
							catch (NumberFormatException n)
							{
								nbis =0;
							}
							if (nbis>=1)
								partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
						}	
						
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
				if (dboTomoFolio.find() == true) 
						{
							partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
							partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
			
			
							String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
							String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);
			
			
							if (bist.trim().length() > 0)
									partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
			
			
							if (bisf.trim().length() > 0)
									partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
									
							//28dic2002
							//quitar el caracter "9" del inicio del tomoid
								if (partidaBean.getTomoId().length()>0)
								{
									if (partidaBean.getTomoId().startsWith("9"))
										{
											String ntomo = partidaBean.getTomoId().substring(1);
											partidaBean.setTomoId(ntomo);
										}
								}
									
						}
						
					
			
			
				//descripcion libro
				dboTmLibro.clearAll();
				dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
				dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
				if (dboTmLibro.find() == true)
					partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));								
			
			
				/*
					dboIndPrtc.clearAll();
					dboIndPrtc.setField(dboIndPrtc.CAMPO_REFNUM_PART,refNumPart);
					dboIndPrtc.find();
					String curPrtc     = dboIndPrtc.getField(dboIndPrtc.CAMPO_CUR_PRTC);
					String tipoPersona = dboIndPrtc.getField(dboIndPrtc.CAMPO_TIPO_PERS);
					String tipoParticipacion = dboIndPrtc.getField(dboIndPrtc.CAMPO_COD_PARTIC);
					if (tipoPersona.equals("N") == true) 
							{
								//persona natural
								dboPrtcNat.clearAll();
								sb.delete(0,sb.length());
								sb.append(DboPrtcNat.CAMPO_APE_PAT).append("|");
								sb.append(DboPrtcNat.CAMPO_APE_MAT).append("|");
								sb.append(DboPrtcNat.CAMPO_NOMBRES);
								dboPrtcNat.setFieldsToRetrieve(sb.toString());
			
			
								dboPrtcNat.setField(DboPrtcNat.CAMPO_CUR_PRTC, curPrtc);
								dboPrtcNat.setField(DboPrtcNat.CAMPO_REG_PUB_ID, regPubId);					
								dboPrtcNat.setField(DboPrtcNat.CAMPO_OFIC_REG_ID, oficRegId);					
								if (dboPrtcNat.find() == true) 
									{
											sb.delete(0, sb.length());
											sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_PAT));
											sb.append(" ");
											sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_MAT));
											sb.append(", ");
											sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_NOMBRES));
										partidaBean.setParticipanteDescripcion(sb.toString());
									}
							} 
						else 
							{
								//persona juridica
								dboPrtcJur.clearAll();
								dboPrtcJur.setFieldsToRetrieve(DboPrtcJur.CAMPO_RAZON_SOCIAL);
								dboPrtcJur.setField(dboPrtcJur.CAMPO_CUR_PRTC, curPrtc);
								dboPrtcJur.setField(DboPrtcJur.CAMPO_REG_PUB_ID, regPubId);					
								dboPrtcJur.setField(DboPrtcJur.CAMPO_OFIC_REG_ID, oficRegId);					
								if (dboPrtcJur.find() == true)
										partidaBean.setParticipanteDescripcion(dboPrtcJur.getField(DboPrtcJur.CAMPO_RAZON_SOCIAL));
							} //if (tipoPersona.equals("N")
				*/
				
				//descripcion de participacion
				/*
					dboParticLibro.clearAll();
					dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,tipoParticipacion);
					dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
					if (dboParticLibro.find()==true)
						partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));	
				*/
			
				resultado.add(partidaBean);
				conta++;
				b = rset.next();	
						if (conta==propiedades.getLineasPorPag())
					{
						if(b==true)	
							haySiguiente = true;
						
						break;
					}		
			}//while (b==true)

			FormOutputBuscarPartida output = new FormOutputBuscarPartida();
			output.setResultado(resultado);
			if (inputBean.getFlagPagineo()==false)
				output.setCantidadRegistros(String.valueOf(conteo));
			else
				output.setCantidadRegistros(inputBean.getCantidad());

			//calcular numero para boton "retroceder pagina"		
			if (inputBean.getSalto()==1)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(inputBean.getSalto()-1);
	
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(inputBean.getSalto()+1);


			//calcular regs del x al y
			int del = ((inputBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);

			//ETIQUETA		
							
			// recuperamos el costo de la visualizacion								
			double tarifa = 0;
			DboTarifa dboTarifa = new DboTarifa(dconn);
			dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
			dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, 1);
			if (dboTarifa.find())
			{ 		
				String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
				tarifa = Double.parseDouble(sTarifa);
			}				 												
			req.setAttribute("tarifa",""+tarifa);				
			// recuperamos el usuario			
			String usuaEtiq = usuario.getUserId();
			req.setAttribute("usuaEtiq",usuaEtiq);				
			// recuperamos la fecha Actual									
			String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
			req.setAttribute("fechaAct",fechaAct);

				
			output.setAction("/webapp/extranet/Publicidad.do?state=buscarXTomoFolio");				
			req.setAttribute("output", output);
			
			// Inicio:mgarate:30/05/2007
			   //if(inputBean.getCodGrupoLibroArea().equals("6") && inputBean.getCodGrupoLibroArea().equals("11") && inputBean.getCodGrupoLibroArea().equals("12"))
			   
			   req.setAttribute("flagCertBusq",inputBean.getCodGrupoLibroArea()); 
			   req.setAttribute("criterioBusqueda",criterioBusqueda);
			   req.setAttribute("flagLibro",inputBean.getLibro());
			// Fin:mgarate:30/05/2007
			   
		    /*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", inputBean.getVerifica());
			/*fin:dbravo:14/09/2007*/ 
			
			response.setStyle("resultadoBusqueda3");	
			
			if (inputBean.getFlagPagineo()==false)
			{
				//llamar a "Transaccion"
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				
				//Datos generales
                              //Modificado por: Proyecto Filtros de Acceso
                              //Fecha: 02/10/2006
                              //bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
                              bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
                              //Fecha: 08/10/2006             
                              bt.setIdSesion(ControlAccesoSesion.obtenerAMIdSesion(ExpressoHttpSessionBean.getRequest(request)));             
                              //Fin Modificación
				bt.setUsuarioSession(usuario);
				bt.setTipoBusq(""+Constantes.CONSULTA_PARTIDA);
				bt.setCodigoServicio(Constantes.CONSULTA_PARTIDA);
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(inputBean.getCodGrupoLibroArea()));
				//datos particulares de esta transaccion
				bt.setTipoConsPartida("3");
				bt.setLibTomFol(
						inputBean.getLibro()
						+ "|"
						+ inputBean.getTomo()
						+ "|"
						+ inputBean.getFolio()
						);
				bt.setNumPartFic(null);
				bt.setOficRegId(inputBean.getOficRegId());
				bt.setRegPubId(inputBean.getRegPubId());
				/*
					Job004 j = new Job004();
					j.setBean(bt);
					Thread llamador1 = new Thread(j);
					llamador1.start();						
				*/
				
				/**
				 *  inicio, dbravo: 15/06/2007
				 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				 *  			   inicialmente el Job003.
				 */
			  if (Propiedades.getInstance().getFlagTransaccion()==true){
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				if (inputBean.getFlagPagineo()==false)
				{
					/*
					validar que el usuario NO sea de zona WEB
					*/
					if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
					{
						//estamos en la primera llamada
						//enviamos TODOS los registros encontrados
						//a otro Thread para que registre el UsoServicio
							Job004 j = new Job004();
							j.setQuery(q2.toString());
							j.setUsuario(usuario);
							j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							
							Thread llamador1 = new Thread(j);
							llamador1.start();
					}
				}
				
			  }
			  /**
			   *  fin, dbravo: 15/06/2007
			   */

			}			

			conn.commit();
			
			if (inputBean.getFlagPagineo()==false && usuario.getFgInterno()==false)			
			{				
				LineaPrepago lineaCmd = new LineaPrepago();
				double nuevoSaldo = lineaCmd.getSaldoActual(usuario.getLinPrePago(),dconn);
				usuario.setSaldo(nuevoSaldo);			
			}
		} //try


		catch (CustomException e) 
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					try{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");						
						rollback(conn, request);
					} catch (Throwable ex) {
						log(Constantes.EC_GENERIC_ERROR, "", ex, request);
						principal(request);
						rollback(conn, request);
						response.setStyle("error");
						}
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmtc);
			pool.release(conn);
			end(request);
		}
		return response;
	}

	private boolean validaNulo(String cadena)
	{
		if (cadena == null)
		{
			return false;
		}
		else
		{
			
			if (cadena.trim().equals(""))
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
	}
	
	/**
	 *  Búsqueda Directa de Partidas. Buscar por Placa
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarXPlacaState
	(
		ControllerRequest request,
		ControllerResponse response
	)
	throws ControllerException
	{
		Connection conn = null;
		Statement stmt   = null;
		Statement stmt1   = null;
		ResultSet rset   =  null;
		ResultSet rs1   =  null;
		
		VehiDatGen datosV = null;
		VehiPropi propiV = null;
		VehiGrava gravaV = null;
		
		List lPropi = new ArrayList();
		List lGrava = new ArrayList();
		List lGrava2 = new ArrayList();
		
		//Inicio:mgarate:30/05/2007
		   String criterioBusqueda;
		// Fin:mgarate:30/05/2007 
		
		String sAux = new String();
		String param = new String();
		StringBuffer sqla = new StringBuffer();
		StringBuffer sqlb = new StringBuffer();
		StringBuffer sqlc = new StringBuffer();
		InputBusqDirectaBean inputBean=null;
		
		//Obteniendo el pool.
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
		//Obteniendo el Request
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

		Statement stmtc   = null;
		ResultSet rsetc   = null;
					
		try
		{
			init(request);
			validarSesion(request);
			
			//conexion a la base de datos
			conn = pool.getConnection();
			//conn.setAutoCommit(true);
			conn.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
					
			//recojer datos de entrada
			inputBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);
			
			Propiedades propiedades = Propiedades.getInstance();
			
			//Bean para el envio de correos
			//MailDataBean mailBean = new MailDataBean();
			//String param = request.getParameter("radBuscar2") == null?"":request.getParameter("radBuscar2");
			String numPlaca = inputBean.getNumeroPlaca();
			String numPartida = inputBean.getNumeroPartida();
			param = numPlaca.equals("")?"P":"";
			
			//numPlaca = request.getParameter("txt4");
			/*
			if(param.equals("P"))
			{		
			  //numPartida = request.getParameter("txt4");
			  //System.out.println("numero de partida :"+ numPartida);
			} 

			String reg_pub_id = "01";
			String ofic_reg_id = "01";			
			*/
			String area_registral = Constantes.AREA_PROPIEDAD_VEHICULAR;
			//String area_registral = inputBean.getAreaRegistral();
			boolean b = false;
			int conteo=0;
			if(param.equals("P"))
			{
				//Inicio:mgarate:30/05/2007
				   criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=4";
				// Fin:mgarate:30/05/2007 
				//verifica q la partida exista
				String fromAndWhere = (new StringBuffer()).append("FROM user1.partida pa, user1.VEHICULO vh, user1.REGIS_PUBLICO regp, user1.OFIC_REGISTRAL ofir, user1.TM_AREA_REGISTRAL area ")
						.append("WHERE pa.reg_pub_id = '").append(inputBean.getRegPubId()).append("' ")
						.append("AND pa.ofic_reg_id = '").append(inputBean.getOficRegId()).append("' ")
						.append("AND pa.area_reg_id = '").append(area_registral).append("' ")
						.append("AND pa.estado != '2'  ")
						.append("AND pa.num_partida = '").append(numPartida).append("' ")
						.append("AND pa.refnum_part = vh.refnum_part ")
						.append("AND vh.fg_baja = '0' ")
						.append("AND regp.REG_PUB_ID = ofir.REG_PUB_ID ")
						.append("AND ofir.OFIC_REG_ID = pa.OFIC_REG_ID ")
						.append("AND ofir.REG_PUB_ID = pa.REG_PUB_ID ")
						.append("AND area.area_reg_id = pa.area_reg_id ")
						.toString();
				if (inputBean.getFlagPagineo()==false)
				{
					sqlc.append("SELECT /*+ORDERED */ count(pa.REFNUM_PART) ")
						.append(fromAndWhere);
					if (isTrace(this)) System.out.println("___verqueryCOUNT_A___" + sqlc.toString());
					stmtc   = conn.createStatement();
					rsetc   = stmtc.executeQuery(sqlc.toString());
					boolean bc = rsetc.next();
					conteo = rsetc.getInt(1);
					if (conteo > (propiedades.getMaxResultadosBusqueda()) )
						throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
					if (conteo==0)
						throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
				}
				sqla.append("SELECT vh.num_placa, vh.fg_baja, pa.estado, pa.num_partida, pa.area_reg_id, pa.cod_libro ")
					.append(", regp.SIGLAS as siglas, ofir.NOMBRE as nombre, pa.REFNUM_PART as refnum_part, area.nombre as descripcionAreaRegistral ")
					.append(fromAndWhere);
														
				//Validando Existencia de la Placa
					if (isTrace(this)) System.out.println("QUERY 1 >> " + sqla.toString());
					if (isTrace(this)) System.out.println("QUERY 2 >> " + sqlb.toString());
					rset   = stmt.executeQuery(sqla.toString());
					b = rset.next();
					if(!b)
						throw new ValidacionException(E70002_NO_ENCONTRO_PARTIDA);
					/*
					numPlaca = rset.getString("num_placa");					
					if(numPlaca==null)
						throw new ValidacionException(E70002_NO_ENCONTRO_PARTIDA);
					*/
				sqlb.append("SELECT pa.reg_pub_id, pa.ofic_reg_id, pa.area_reg_id ")
					.append(fromAndWhere);
												
			}
			else
			{
				//Inicio:mgarate:30/05/2007
				   criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=41";
				// Fin:mgarate:30/05/2007 
				//verificar que la placa exista y recuperar el refnum_part
				String fromAndWhere = (new StringBuffer()).append("FROM user1.VEHICULO vh, user1.partida pa, user1.REGIS_PUBLICO regp, user1.OFIC_REGISTRAL ofir, user1.TM_AREA_REGISTRAL area ")
					.append("WHERE vh.num_placa = '").append(numPlaca).append("' ")
					.append("AND pa.refnum_part = vh.refnum_part ")
					.append("AND pa.reg_pub_id = '").append(inputBean.getRegPubId()).append("' ")
					.append("AND pa.ofic_reg_id= '").append(inputBean.getOficRegId()).append("' ")
					.append("AND pa.area_reg_id = '").append(area_registral).append("' ")
					.append("AND pa.estado != '2' ")
					.append("AND regp.REG_PUB_ID = ofir.REG_PUB_ID ")
					.append("AND ofir.OFIC_REG_ID = pa.OFIC_REG_ID ")
					.append("AND ofir.REG_PUB_ID = pa.REG_PUB_ID ")
					.append("AND area.area_reg_id = pa.area_reg_id ")
					.toString();
				if (inputBean.getFlagPagineo()==false)
				{
					sqlc.append("SELECT /*+ORDERED */ count(pa.REFNUM_PART) ")
						.append(fromAndWhere);
					if (isTrace(this)) System.out.println("___verqueryCOUNT_A___" + sqlc.toString());
					stmtc   = conn.createStatement();
					rsetc   = stmtc.executeQuery(sqlc.toString());
					boolean bc = rsetc.next();
					conteo = rsetc.getInt(1);
					if (conteo > (propiedades.getMaxResultadosBusqueda()) )
						throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
					if (conteo==0)
						throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
				}
				sqla.append("SELECT vh.num_placa, vh.fg_baja, pa.estado, pa.num_partida, pa.area_reg_id, pa.cod_libro ")
					.append(", regp.SIGLAS as siglas, ofir.NOMBRE as nombre, pa.REFNUM_PART as refnum_part, area.nombre as descripcionAreaRegistral ")
					.append(fromAndWhere);
					
					
				//Validando Existencia de la Placa
					if (isTrace(this)) System.out.println("QUERY 1 >> " + sqla.toString());
					if (isTrace(this)) System.out.println("QUERY 2 >> " + sqlb.toString());
					rset = stmt.executeQuery(sqla.toString());
					b = rset.next();
					if(!b)
						throw new ValidacionException("Número de Placa no Registrado.");
					
					sqlb.append("SELECT pa.reg_pub_id, pa.ofic_reg_id, pa.area_reg_id ")
						.append(fromAndWhere);
									
			}
			ArrayList resultado = new ArrayList();
			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				/*
				numPartida = rs.getString("num_partida");		
				req.setAttribute("placa", numPlaca);
				req.setAttribute("partida", numPartida);
				req.setAttribute("regpub", reg_pub_id);
				req.setAttribute("ofireg", ofic_reg_id);
				*/ 

					PartidaBean partida = new PartidaBean();
					partida.setRefNumPart(rset.getString("refnum_part"));
					partida.setAreaRegistralDescripcion("descripcionAreaRegistral");
					partida.setNumPartida(rset.getString("num_partida"));
					partida.setRegPubDescripcion(rset.getString("siglas"));
					partida.setOficRegDescripcion(rset.getString("nombre"));
					partida.setRegPubId(inputBean.getRegPubId());
					partida.setOficRegId(inputBean.getOficRegId());
					partida.setEstado(rset.getString("estado"));
					partida.setAreaRegistralId(rset.getString("area_reg_id"));
					partida.setCodLibro(rset.getString("cod_libro"));
					partida.setNumeroPlaca(rset.getString("num_placa"));
					
					if(rset.getString("fg_baja")==null)
						partida.setBaja("No Especificado");
					else if (rset.getString("fg_baja").equals("1"))
						partida.setBaja("Baja");
					else if (rset.getString("fg_baja").equals("0"))
						partida.setBaja("En Circulación");
					else
						partida.setBaja("No Especificado");
			
				//Ver en caso que flag baja sea 1:
				boolean otraPlaca = false;
				if(partida.getBaja().equals("1"))
				{
					StringBuffer sql12 = new StringBuffer();
					sql12.append("SELECT num_placa ")
						.append(" FROM user1.VEHICULO ")
						.append(" WHERE refnum_part = ").append(partida.getRefNumPart())
						.append(" AND fg_baja='0'");
					if (isTrace(this)) System.out.println("QUERY 2 >> " + sql12.toString());
					rset   = stmt.executeQuery(sql12.toString());
					if(rset.next())
					{
						req.setAttribute("nuevaPlaca", rset.getString("num_placa"));
						otraPlaca = true;
					}
					else
						req.setAttribute("nuevaPlaca", "No disponible");
				}
				//paginacion
				resultado.add(partida);
				conta++;
				b = rset.next();	
				if (conta==propiedades.getLineasPorPag())
				{
					if(b==true)	
						haySiguiente = true;
					break;
				}		
			}
			
			FormOutputBuscarPartida output = new FormOutputBuscarPartida();
			output.setResultado(resultado);
			if (inputBean.getFlagPagineo()==false)
				output.setCantidadRegistros(String.valueOf(conteo));
			else
				output.setCantidadRegistros(inputBean.getCantidad());
			
			//calcular numero para boton "retroceder pagina"		
			if (inputBean.getSalto()==1)
				output.setPagAnterior(-1);
			else
				output.setPagAnterior(inputBean.getSalto()-1);
				
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false)
				output.setPagSiguiente(-1);
			else
				output.setPagSiguiente(inputBean.getSalto()+1);
				
			//calcular regs del x al y
			int del = ((inputBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);
			
			/**
			 *  inicio, dbravo: 15/06/2007
			 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
			 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
			 *  			   inicialmente el Job003.
			 *
			if (inputBean.getFlagPagineo()==false)
			{
			//UsoServicio_________________________________
				/*
				validar que el usuario NO sea de zona WEB
				*
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					Job002 j = new Job002();
					j.setUsuario(usuario);
					j.setQuery(sqlb.toString());
					j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
					Thread llamador1 = new Thread(j);
					llamador1.start();
				}
			}
			**/	
			
				//ETIQUETA		
				// recuperamos el costo de la visualizacion
				double tarifa = 0;
				DboTarifa dboTarifa = new DboTarifa(dconn);
				dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, String.valueOf(Constantes.SERVICIO_CONSULTAR_PARTIDA));
				if (dboTarifa.find())
				{ 		
					String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
					tarifa = Double.parseDouble(sTarifa);
				}				 												
				req.setAttribute("tarifa",""+tarifa);
				
				// recuperamos el usuario			
				String usuaEtiq = usuario.getUserId();
				req.setAttribute("usuaEtiq",usuaEtiq);
				
				// recuperamos la fecha Actual									
				String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
				req.setAttribute("fechaAct",fechaAct);
				
				/*inicio:dbravo:14/09/2007*/
				req.setAttribute("flagVerifica", inputBean.getVerifica());
				/*fin:dbravo:14/09/2007*/
				
				// Inicio:mgarate:30/05/2007
				   req.setAttribute("flagCertBusq",inputBean.getCodGrupoLibroArea());
				   req.setAttribute("criterioBusqueda", criterioBusqueda);
				// Fin:mgarate:30/05/2007
				
			/*
			//llamar a "Transaccion"
				LogAuditoriaConsultaPlacaBean bt = new LogAuditoriaConsultaPlacaBean();
				//Datos generales
				bt.setRemoteAddr(ExpressoHttpSessionBean.getRequest(request).getRemoteAddr());
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(TipoServicio.CONSULTA_PLACA);
				bt.setTipoBusq(0);//0 = Placa
				bt.setTipoParticipacion("4");//4 = Placa
				bt.setCodAreaReg(area_registral);// = RPV
				bt.setParamBusqueda(numPlaca);
				bt.setOficRegId(ofic_reg_id);
				bt.setRegPubId(reg_pub_id);
				if(flagNocobrar)
					bt.setFlagCobro("1");
				else
					bt.setFlagCobro("0");
			if (Propiedades.getInstance().getFlagTransaccion()==true)
				Transaction.getInstance().registraTransaccion(bt,conn);
				conn.commit();
				response.setStyle("detallePlaca");
			*/

			//llamar a "Transaccion"
				LogAuditoriaConsultaPartidaBean bt = new LogAuditoriaConsultaPartidaBean();
				//Datos generales
				bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(Constantes.CONSULTA_PARTIDA);
				//Tarifario
				bt.setCodigoGLA(Integer.parseInt(inputBean.getCodGrupoLibroArea()));
				bt.setTipoBusq(""+Constantes.CONSULTA_PARTIDA);
				//bt.setTipoParticipacion("4");//4 = Placa
				//bt.setCodAreaReg(area_registral);// = RPV
				//bt.setParamBusqueda(numPlaca);
				//datos particulares de esta transaccion
				if(param.equals("P")){
					bt.setTipoConsPartida("1"); // x Partida
					bt.setNumPartFic(inputBean.getNumeroPartida());
				}else{
					bt.setTipoConsPartida("4"); // x Placa
					bt.setNumPartFic(inputBean.getNumeroPlaca());
				}
				bt.setLibTomFol(null);
				bt.setOficRegId(inputBean.getOficRegId());
				bt.setRegPubId(inputBean.getRegPubId());
				
				req.setAttribute("output",output);
				
				/*inicio:dbravo:14/09/2007*/
				req.setAttribute("flagVerifica", inputBean.getVerifica());
				/*fin:dbravo:14/09/2007*/ 
				/**
				 *  inicio, dbravo: 15/06/2007
				 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				 *  			   inicialmente el Job002.
				 */
				if (Propiedades.getInstance().getFlagTransaccion()==true){
					PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
					
					if (inputBean.getFlagPagineo()==false)
					{
					//UsoServicio_________________________________
						/*
						validar que el usuario NO sea de zona WEB
						*/
						if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
						{
							Job004 j = new Job004();
							j.setUsuario(usuario);
							j.setQuery(sqlb.toString());
							j.setCodigoServicio(Constantes.SERVICIO_CONSULTAR_PARTIDA);
							j.setCostoServicio(prepagoBean.getMontoBruto());
							Thread llamador1 = new Thread(j);
							llamador1.start();
						}
					}
				}
				/**
				 *  fin, dbravo: 15/06/2007
				 */
				conn.commit();
				response.setStyle("listaPlaca");

				
		}
		catch (ValidacionException ve)
		{
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1", ve.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
			{
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				if(param.equals("P"))
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
				else
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la placa buscada<br><br><br><br><br>");
				try
				{
					rollback(conn, request);
				}
				catch (Throwable ex)
				{
					log(Constantes.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					//rollback(connLocal, request);
					response.setStyle("error");
				}
			}
			else
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				rollback(conn, request);
				response.setStyle("error");
			}
		}
		catch (DBException dbe)
		{
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}
		catch (Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally
		{
			JDBC.getInstance().closeResultSet(rs1);
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rsetc);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmt1);
			JDBC.getInstance().closeStatement(stmtc);
			if (pool != null)
				pool.release(conn);
//			if (poolLocal != null)
//				poolLocal.release(connLocal);

			end(request);
		}
		
		
		return response;
	}
	
	/**
	 *  Búsqueda Directa de Partidas. Buscar por Placa . Detalle de la Placa
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarXPlacaDetState
	(
		ControllerRequest request,
		ControllerResponse response
	)
	throws ControllerException
	{
		Connection conn = null;
		//Connection connLocal = null;
		Statement stmt   = null;
		Statement stmt1   = null;
		ResultSet rs   =  null;
		ResultSet rs1   =  null;
		
		VehiDatGen datosV = null;
		VehiPropi propiV = null;
		VehiGrava gravaV = null;
		
		List lPropi = new ArrayList();
		List lGrava = new ArrayList();
		List lGrava2 = new ArrayList();
		
		String sAux = new String();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//Obteniendo el pool.
			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			//DBConnectionFactory poolLocal = DBConnectionFactory.getInstance();
		//Obteniendo el Request
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		//obtener usuario de la sesion				
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);
					
		try
		{
			init(request);
			validarSesion(request);
			
			//conexion a la base de datos
				conn = pool.getConnection();
				//conn.setAutoCommit(true);
				conn.setAutoCommit(false);
				//connLocal = poolLocal.getConnection();
				//connLocal.setAutoCommit(false);
			
			DBConnection dconn = new DBConnection(conn);
			//req.setAttribute("outNumber", inputBean.getNumeroFicha());
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			//Bean para el envio de correos
			MailDataBean mailBean = new MailDataBean();
			
			String param = request.getParameter("radBuscar2") == null?"":request.getParameter("radBuscar2");
			String numPlaca = "";
			String numPartida = "";
			
			numPlaca = request.getParameter("txt4");
			//System.out.println("numero de placa :"+ numPlaca);

			if(param.equals("P"))
			{		
			  numPartida = request.getParameter("txt4");
			  System.out.println("numero de partida :"+ numPartida);
			} 
			
			String reg_pub_id = "01";
			String ofic_reg_id = "01";			
			
			if(request.getParameter("CboOficinas")!=null)
			{
				System.out.println("request.getParameter(CboOficinas)-->"+request.getParameter("CboOficinas"));
				reg_pub_id = request.getParameter("CboOficinas").substring(0,2);
				ofic_reg_id = request.getParameter("CboOficinas").substring(3,5);
			}
			
			String area_registral = Constantes.AREA_PROPIEDAD_VEHICULAR;
						
			StringBuffer sql1 = new StringBuffer();
						
			System.out.println("runBuscarXPlacaDetState param actual--->"+param);
			if(param.equals("P"))
			{
				/*
				System.out.println("------------------------------------------------------------------------->");
				System.out.println("-->Búsqueda Detalle X Partida");
				//verifica q la partida exista
				sql1.append("SELECT vh.num_placa, vh.refnum_part, vh.fg_baja, pa.estado, pa.num_partida, pa.area_reg_id, pa.cod_libro ")
					.append("FROM user1.VEHICULO vh, user1.partida pa ")
					.append("WHERE pa.reg_pub_id = '").append(reg_pub_id).append("' ")
					.append("AND pa.ofic_reg_id = '").append(ofic_reg_id).append("' ")
					.append("AND pa.area_reg_id = '").append(area_registral).append("' ")
					.append("AND pa.estado != 2  ")
					.append("AND pa.num_partida = '").append(numPartida).append("' ")
					.append("AND pa.refnum_part = vh.refnum_part ")
					.append("AND vh.fg_baja = '0' ");
														
				//Validando Existencia de la Placa
					if (isTrace(this)) System.out.println("QUERY 1 >> " + sql1.toString());
					rs   = stmt.executeQuery(sql1.toString());
					if(!rs.next())
						throw new ValidacionException("Número de Partida no Registrado.");
										
					numPlaca = rs.getString("num_placa");					
					if(numPlaca==null)
						throw new ValidacionException("Partida no tiene asociada número de Placa.");
				*/
												
			}//end if(param == F)
			else
			{
				//verificar que la placa exista y recuperar el refnum_part
				sql1.append("SELECT vh.num_placa, vh.refnum_part, vh.fg_baja, pa.estado, pa.num_partida, pa.area_reg_id, pa.cod_libro ")
					.append("FROM user1.VEHICULO vh, user1.partida pa ")
					.append("WHERE vh.num_placa = '").append(numPlaca).append("' ")
					.append("AND pa.refnum_part = vh.refnum_part ")
					.append("AND pa.reg_pub_id = '").append(reg_pub_id).append("' ")
					.append("AND pa.ofic_reg_id= '").append(ofic_reg_id).append("' ")
					.append("AND pa.area_reg_id = '").append(area_registral).append("' ")
					.append("AND pa.estado != 2 ");
				
				//Validando Existencia de la Placa
					if (isTrace(this)) System.out.println("QUERY 1 >> " + sql1.toString());
					rs   = stmt.executeQuery(sql1.toString());
					if(!rs.next())
						throw new ValidacionException("Número de Placa no Registrado.");
									
			}
			
			numPartida = rs.getString("num_partida");		
			
			req.setAttribute("placa", numPlaca);
			req.setAttribute("partida", numPartida);
			req.setAttribute("regpub", reg_pub_id);
			req.setAttribute("ofireg", ofic_reg_id);
											
			PartidaBean partida = new PartidaBean();
			partida.setRefNumPart(rs.getString("refnum_part"));
			partida.setRegPubId(reg_pub_id);
			partida.setOficRegId(ofic_reg_id);
			partida.setEstado(rs.getString("estado"));
			partida.setNumPartida(numPartida);
			partida.setAreaRegistralId(rs.getString("area_reg_id"));
			//partida.setAreaRegistralDescripcion();
			partida.setCodLibro(rs.getString("cod_libro"));
			//partida.setLibroDescripcion();
			partida.setBaja(rs.getString("fg_baja") == null?"":rs.getString("fg_baja"));
				
			req.setAttribute("refnum_part", partida.getRefNumPart());
					
			boolean flagNocobrar = false;
					
			if(partida.getEstado().equals("3") || partida.getEstado().equals("4")) // 4 = PAMA
			{
				DboOficRegistral dboOficina = new DboOficRegistral(dconn);
				dboOficina.setFieldsToRetrieve(dboOficina.CAMPO_NOMBRE);
				dboOficina.setField(DboOficRegistral.CAMPO_REG_PUB_ID,partida.getRegPubId());
				dboOficina.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,partida.getOficRegId());
				if (!dboOficina.find())
					throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + partida.getRefNumPart() + "' sin oficina conocida", "errorMotivosTecnicos");
				partida.setOficRegDescripcion(dboOficina.getField(DboOficRegistral.CAMPO_NOMBRE));
				String mensajeErrorPartidaIncompleta = (new StringBuffer("Atención: Administrador de Jurisdicción "))
						.append("La Partida ")
						.append(partida.getNumPartida())
						.append(" del Area ")
						.append(partida.getAreaRegistralId())
						.append(" de la Oficina ")
						.append(partida.getOficRegDescripcion())
						.append(" requiere ser corregida y sincronizada a la Bodega Central,")
						.append(" un usuario de la Extranet recibió un mensaje de error al momento")
						.append(" de accederla.")
						.toString();
				if((usuario.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO) || (usuario.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT) || (usuario.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO))
				{
					//no cobro
					flagNocobrar = true;								
					throw new CustomException(
						partida.getEstado().equals("3")?EC_INDICES_INCOMPLETOS:EC_REPLICACION_PENDIENTE,
						mensajeErrorPartidaIncompleta,
						"errorMotivosTecnicos");
				} else {
					req.setAttribute("mensajeAdm", "Partida no disponible para usuarios externos via web");
					log(partida.getEstado().equals("3")?EC_INDICES_INCOMPLETOS:EC_REPLICACION_PENDIENTE,mensajeErrorPartidaIncompleta, request);
				}
			}
				
			//Ver en caso que flag baja sea 1:
			boolean otraPlaca = false;
			if(partida.getBaja().equals("1"))
			{
				StringBuffer sql12 = new StringBuffer();
				sql12.append("SELECT num_placa ")
					.append(" FROM user1.VEHICULO ")
					.append(" WHERE refnum_part = ").append(partida.getRefNumPart())
					.append(" AND fg_baja=0");
				if (isTrace(this)) System.out.println("QUERY 2 >> " + sql12.toString());
				rs   = stmt.executeQuery(sql12.toString());
				if(rs.next())
				{
					req.setAttribute("nuevaPlaca", rs.getString("num_placa"));
					otraPlaca = true;
				}
				else
					req.setAttribute("nuevaPlaca", "No disponible");
			}
			
			//UsoServicio_________________________________
			/*
			validar que el usuario NO sea de zona WEB
			*/
			/**
			 * @fecha: 18/06/2007
			 * @autor: dbravo 
			 * @descripcion: Se cambia la posición de Job al final, junto a la transaccion
			 *
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
				Job003 j = new Job003();sdsfsdf
				
				j.setUsuario(usuario);
				j.setCodigoServicio(Constantes.SERVICIO_CONSULTA_PARTIDA_RPV);
				j.setRegPubId(partida.getRegPubId());
				j.setOficRegId(partida.getOficRegId());
				j.setArea(area_registral);
				
							Thread llamador1 = new Thread(j);
				llamador1.start();
				}
			**/

			//recupera datos generales del vehiculo

			datosV = new VehiDatGen();
			
			StringBuffer sql8 = new StringBuffer();
			sql8.append("SELECT NOMBRE ")
				.append("FROM REGIS_PUBLICO ")
				.append("WHERE REG_PUB_ID = '")
				.append(reg_pub_id)
				.append("' ");
				rs = stmt.executeQuery(sql8.toString());
				if(rs.next()) {
					sAux = rs.getString("NOMBRE");
					if(validaNulo(sAux)) {
						datosV.setNomOfi(sAux);
					} else {
						datosV.setNomOfi("---");
					}
				} else {
					datosV.setNomOfi("---");
				}
			
			// 31/12/2005 - inicio
			DboOficRegistral dboOficina = new DboOficRegistral(dconn);
			dboOficina.setFieldsToRetrieve(dboOficina.CAMPO_NOMBRE);
			dboOficina.setField(DboOficRegistral.CAMPO_REG_PUB_ID,reg_pub_id);
			dboOficina.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,ofic_reg_id);
			if (!dboOficina.find())
				throw new CustomException(Errors.EC_GENERIC_DB_ERROR_DATA, "refnum_part '" + partida.getRefNumPart() + "' sin oficina conocida", "errorMotivosTecnicos");
			datosV.setNomOfi("<br>OFICINA " + dboOficina.getField(DboOficRegistral.CAMPO_NOMBRE));
			// 31/12/2005 - fin
			
			StringBuffer sql4 = new StringBuffer();
			sql4.append("SELECT /*+ rule */ vh.num_placa, vh.cod_modelo, mov.descripcion AS DE_MODE, vh.cod_marca, mav.descripcion AS DE_MARC, vh.cod_cond_vehi, cv.descripcion AS DE_COND_VEHI, ")
				.append("vh.cod_tipo_vehi, tv.descripcion AS DE_TIPO_VEHI, vh.cod_tipo_comb, tco.descripcion AS DE_TIPO_COMB, vh.cod_color_01_orig, ")
				.append("vh.desc_color_01 || ' ' || vh.desc_color_02 || ' ' || vh.desc_color_03 color, vh.ano_fabric AS AN_FABR, vh.num_serie AS NO_SERI, vh.num_motor AS NO_MOTR, ")
				.append("vh.num_cilindros AS NO_CILN, vh.peso_seco, vh.peso_bruto AS PESO_BRUT, vh.num_pasajeros AS NO_PASJ, vh.num_asientos AS NO_ASIE, vh.num_ejes AS NO_EJES, vh.num_ruedas AS NO_RUED, vh.num_puertas AS NO_PUER, ")
				.append("vh.longitud AS LGTD, vh.ancho AS ANCH, vh.altura AS ALTU, to_char(vh.ts_inscrip,'dd-mm-yyyy') fecha, vh.cod_tipo_carr, tca.descripcion AS DE_TIPO_CARR, vh.fg_baja AS FG_BAJA, vh.cetico_desc AS CETICO ")
				.append("FROM user1.vehiculo vh, user1.tm_modelo_vehi mov, user1.tm_marca_vehi mav, user1.tm_cond_vehi cv, user1.tm_tipo_vehi tv, ")
				.append("user1.tm_tipo_comb tco, user1.tm_tipo_carr tca ")
				.append("WHERE vh.num_placa = '").append(numPlaca).append("' AND vh.cod_modelo = mov.cod_modelo(+) AND vh.cod_marca = mav.cod_marca(+) ")
				.append("AND vh.cod_cond_vehi = cv.cod_cond_vehi(+) AND vh.cod_tipo_vehi = tv.cod_tipo_vehi(+) AND vh.cod_tipo_comb = tco.cod_tipo_comb(+) ")
				.append("AND vh.cod_tipo_carr = tca.cod_tipo_carr(+) ");
				
			//Obteniendo Detalle del Número de Placa
				if (isTrace(this)) System.out.println("QUERY 4 >> " + sql4.toString());
				rs = stmt.executeQuery(sql4.toString());
				rs.next();
				
				
			//Obtener los valores y ponerlos en Bean
				sAux = rs.getString("FECHA");
				if(validaNulo(sAux))
					datosV.setFecIns(sAux);	
				sAux = rs.getString("DE_MARC");
				if(validaNulo(sAux))
					datosV.setMarca(sAux);	
				sAux = rs.getString("DE_TIPO_VEHI");
				if(validaNulo(sAux))
					datosV.setClase(sAux);	
				sAux = rs.getString("DE_TIPO_COMB");
				if(validaNulo(sAux))
					datosV.setCombus(sAux);	
				sAux = rs.getString("NO_SERI");
				if(validaNulo(sAux))
					datosV.setNoSeri(sAux);	
				sAux = rs.getString("DE_COND_VEHI");
				if(validaNulo(sAux))
				{
					//datosV.setEstado(sAux);
					datosV.setCondic(sAux);
				}
				else
				{
					//datosV.setEstado("---");
					datosV.setCondic("---");
				}
				sAux = rs.getString("CETICO");
				if(validaNulo(sAux))
				{
					datosV.setCetico(sAux);
				}
				else
				{
					datosV.setCetico("");
				}

				sAux = rs.getString("DE_MODE");
				if(validaNulo(sAux))
					datosV.setModelo(sAux);	
				sAux = rs.getString("DE_TIPO_CARR");
				if(validaNulo(sAux))
					datosV.setCarroc(sAux);	
				sAux = rs.getString("COLOR");
				if(validaNulo(sAux))
					datosV.setColor(sAux);	
				sAux = rs.getString("NO_MOTR");
				if(validaNulo(sAux))
					datosV.setNoMoto(sAux);	
				sAux = rs.getString("AN_FABR");
				if(validaNulo(sAux))
					datosV.setYearFa(sAux);	
				sAux = rs.getString("ANCH");
				if(validaNulo(sAux))
					datosV.setAncho(sAux);	
				sAux = rs.getString("NO_ASIE");
				if(validaNulo(sAux))
					datosV.setAsient(sAux);	
				sAux = rs.getString("NO_RUED");
				if(validaNulo(sAux))
					datosV.setNoRued(sAux);	
				sAux = rs.getString("ALTU");
				if(validaNulo(sAux))
					datosV.setAltura(sAux);	
				sAux = rs.getString("NO_PUER");
				if(validaNulo(sAux))
					datosV.setPuerta(sAux);	
				sAux = rs.getString("NO_EJES");
				if(validaNulo(sAux))
					datosV.setNoEjes(sAux);	
				sAux = rs.getString("PESO_SECO");
				if(validaNulo(sAux))
					datosV.setPsSeco(sAux);	
				sAux = rs.getString("NO_PASJ");
				if(validaNulo(sAux))
					datosV.setPasaje(sAux);	
				sAux = rs.getString("LGTD");
				if(validaNulo(sAux))
					datosV.setLongit(sAux);	
				sAux = rs.getString("PESO_BRUT");
				if(validaNulo(sAux))
					datosV.setPsBrut(sAux);	
				sAux = rs.getString("NO_CILN");
				if(validaNulo(sAux))
					datosV.setCilind(sAux);	
				//agregado por hp
				sAux = rs.getString("FG_BAJA");
				if(validaNulo(sAux))
				{
					if(sAux.equals("0"))
						datosV.setEstado("EN CIRCULACION");	
					else if(sAux.equals("1"))
						datosV.setEstado("DE BAJA");
				}
				else
					datosV.setEstado("NO ESPECIFICADO");
				// fin agregado
				req.setAttribute("bDatos", datosV);
			
			if(!otraPlaca)
			{
								
				//recupera los propietarios
				StringBuffer sql3 = new StringBuffer();
				sql3.append("SELECT /*+ORDERED*/ rownum sec, pn.ape_pat AS ape_pat, pn.ape_mat AS ape_mat, pn.nombres AS nombres, null as razon_social, ind.direccion AS direccion, ind.aa_titu AS an_titu , ind.nu_titu AS nu_titu , to_char(ind.field1,'dd-mm-yyyy') fec_prop , ind.ubigeo AS ubigeo ")
					.append("FROM user1.ind_prtc ind, user1.prtc_nat pn ")
					.append("WHERE ind.refnum_part = '").append(partida.getRefNumPart()).append("' ")
					.append("AND ind.cod_partic = '")
					.append(gob.pe.sunarp.extranet.util.Constantes.PROPIETARIO_VEHI).append("' ")
					.append("AND ind.num_placa = '")
					.append(numPlaca).append("' ")
					.append("AND ind.estado = '1' ")
					.append("AND pn.cur_prtc = ind.cur_prtc ")
					.append("AND pn.reg_pub_id = '").append(reg_pub_id).append("' ")
					.append("AND pn.ofic_reg_id = '").append(ofic_reg_id).append("' ")
					.append("AND ind.tipo_pers='N' ")
					.append("UNION ALL ")
					.append("SELECT /*+ORDERED*/ rownum sec, null, null, null, pj.razon_social AS razon_social, ind.direccion AS direccion, ind.aa_titu AS an_titu , ind.nu_titu AS nu_titu, to_char(ind.field1,'dd-mm-yyyy') fec_prop , ind.ubigeo AS ubigeo " )
					.append("FROM user1.ind_prtc ind , user1.prtc_jur pj ")
					.append("WHERE ind.refnum_part = '").append(partida.getRefNumPart()).append("' ")
					.append("AND ind.cod_partic = '")
					.append(gob.pe.sunarp.extranet.util.Constantes.PROPIETARIO_VEHI).append("' ")
					.append("AND ind.num_placa = '")
					.append(numPlaca).append("' ")					
					.append("AND ind.estado = '1' ")
					.append("AND pj.cur_prtc = ind.cur_prtc ")
					.append("AND pj.reg_pub_id = '").append(reg_pub_id).append("' ")
					.append("AND pj.ofic_reg_id = '").append(ofic_reg_id).append("' ")
					.append("AND ind.tipo_pers='J' ");															
					
				//Obteniendo Datos del Propietario
					if (isTrace(this)) System.out.println("QUERY 3 >> " + sql3.toString());
					rs   = stmt.executeQuery(sql3.toString());
					int i = 0;
					int ccont = 0;
					boolean valResSet = rs.next();
					String fec_prop = "";
					
					if(!valResSet)
					{
						fec_prop = "NO ESPECIFICADO";
						if((usuario.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO) || (usuario.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT) || (usuario.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO))
						{
							req.setAttribute("mensajeAdm", "Esta partida en este momento se está actualizando. Por favor sirvase verificar en 2 horas.");	
								
							//Envio de Mail
							mailBean = new MailDataBean();
							mailBean.setTo(Propiedades.getInstance().getSendMailPersonasRPV());
							mailBean.setSubject("Partida Vehicular");
							mailBean.setBody(new StringBuffer("Atención: Administrador de Jurisdicción ")
								.append("La Partida ")
								.append(partida.getNumPartida())
								.append(" del Area ")
								.append(partida.getAreaRegistralId())
								.append(" de la Oficina ")
								.append(partida.getOficRegId())
								.append(" requiere ser corregida y sincronizada a la Bodega Central,")
								.append(" un usuario de la Extranet recibió un mensaje de error al momento")
								.append(" de accederla.")
								.toString());
							MailProcessor.getInstance().saveMail(mailBean, conn);
							
							//no cobro
							flagNocobrar = true;								
														
						}
						else
						{
							req.setAttribute("mensajeAdm", "Partida no disponible para usuarios externos via web");
						}
					}
					
					while(valResSet)
					{
						propiV = new VehiPropi();
						String aux = null;
						i++;
						StringBuffer prop = new StringBuffer();
						prop.append(String.valueOf(i)).append(". ");
						
						
						
						aux = rs.getString("ape_pat");
						prop.append(aux==null?"":(new StringBuffer(aux).append(" ").toString()));
						aux = rs.getString("ape_mat");
						prop.append(aux==null?"":(new StringBuffer(aux).append(" ").toString()));
						aux = rs.getString("nombres");
						prop.append(aux==null?"":(new StringBuffer(aux).append(" ").toString()));
							
						//Obtener los valores y ponerlos en Bean de nombres
						
						aux = rs.getString("razon_social");
						if(validaNulo(aux))
						{
							if(prop.toString().indexOf(".")==(prop.toString().length()-2))
							{
								prop.append("- ").append(aux);
							}
							else
							{
								prop.append(aux);
							}
						}
						
						propiV.setPropietario(prop.toString());
						
						//obtengo el ubigeo
						aux = rs.getString("ubigeo");
						if(validaNulo(aux))
							propiV.setUbigeo(aux);

						
						//obtengo la direccion
						aux = rs.getString("direccion");
						if(validaNulo(aux))
							propiV.setDireccion(aux);
						
						//agregando el ano de titulo
						aux = rs.getString("nu_titu");
																		
						if((validaNulo(aux)) || (aux.equals("00000000")))
						{							
							
							aux = aux + (((rs.getString("an_titu")==null)||(rs.getString("an_titu").equals("0000")))?"":(" - " + rs.getString("an_titu")));
							propiV.setExpediente(aux);
						}
						
						//obtengo la fecha de propiedad
						ccont++;
						if(ccont == 1)
							fec_prop = rs.getString("fec_prop")==null?"":rs.getString("fec_prop").toString();
						
						lPropi.add(propiV);
						valResSet = rs.next();
					}
					if(lPropi.size()>0)
					{
						req.setAttribute("lPropi", lPropi);						
					}	
					req.setAttribute("fec_prop", fec_prop);		
				
				
				//recupera gravamenes vigentes
				StringBuffer sql5 = new StringBuffer();
				sql5.append("SELECT g.ns_gravamen, g.ano_titu, g.num_titu, g.ti_afec, ta.descripcion, g.juzg, g.caus_afec, g.juez_afec, g.secr_afec, ")
					.append("g.juez_desc, g.secr_desc, ")
					.append("g.fg_estado, g.num_expe_afec, g.num_expe_desc, g.ts_afec, g.ts_expe_desc, g.ts_proc_desc, g.ts_expe_afec, g.fg_prenda, ")
					.append("g.ns_acto, g.ano_titu_desc, g.num_titu_desc, g.ns_acto_desc, g.ano_titu_modi, g.num_titu_modi, g.ns_acto_modi ")
					.append("FROM user1.gravamen g, user1.tm_tipo_afec ta ")
					.append("WHERE g.num_placa = '").append(numPlaca).append("' and g.ti_afec = ta.cod_tipo_afec(+) ORDER BY 2");
					
				//Obteniendo Gravámenes
					if (isTrace(this)) System.out.println("QUERY 5 Gravamenes Vigentes>> " + sql5.toString());
					rs = stmt.executeQuery(sql5.toString());
					int cont = 0;
					int j = 0;
					VehiParticipaGrava  beanPaGra = new VehiParticipaGrava();
					

						while(rs.next())
						{
							cont++;
							gravaV = new VehiGrava();
							
							//Obtener los valores y ponerlos en Bean de gravamenes
							sAux = rs.getString("FG_ESTADO");
							if(validaNulo(sAux))
							{
								if(sAux.trim().equals("1"))
								{
									gravaV.setEstado("VIGENTE");
								}
								else if(sAux.trim().equals("2"))
								{
									gravaV.setEstado("LEVANTADO");
								}

							}
							else
							{
								//gravamenens no disponibles
								gravaV.setEstado("---");
							}
							sAux = rs.getString("DESCRIPCION");
							if(validaNulo(sAux))
								gravaV.setAfecta(sAux);
							else
								gravaV.setAfecta("");		
							sAux = rs.getString("TS_AFEC");
							if(validaNulo(sAux))
								gravaV.setFecAfe(sAux);
							else
								gravaV.setFecAfe("");		
							sAux = rs.getString("NUM_EXPE_AFEC");
							if(validaNulo(sAux))
								gravaV.setNoDocu(sAux);
							else
								gravaV.setNoDocu("");		
							sAux = rs.getString("ANO_TITU")+" "+rs.getString("NUM_TITU") ;
							if(validaNulo(sAux))
								gravaV.setTitulo(sAux);	
							else
								gravaV.setTitulo("");
							sAux = rs.getString("JUZG");
							if(validaNulo(sAux))
								gravaV.setJuzgad(sAux);
							else
								gravaV.setJuzgad("");		
							sAux = rs.getString("CAUS_AFEC");
							if(validaNulo(sAux))
								gravaV.setCauAfe(sAux);
							else
								gravaV.setCauAfe("");	
							sAux = rs.getString("JUEZ_AFEC");
							if(validaNulo(sAux))
								gravaV.setJuez(sAux);
							else
								gravaV.setJuez("");		
							sAux = rs.getString("SECR_AFEC");
							if(validaNulo(sAux))
								gravaV.setSecre(sAux);
							else
								gravaV.setSecre("");		
							sAux = rs.getString("ANO_TITU_MODI")+" "+rs.getString("NUM_TITU_MODI");
							if(validaNulo(sAux))
								gravaV.setModifi(sAux);
							else
								gravaV.setModifi("");		
							sAux = rs.getString("JUEZ_DESC");
							if(validaNulo(sAux))
								gravaV.setJuezDs(sAux);	
							sAux = rs.getString("SECR_DESC");
							if(validaNulo(sAux))
								gravaV.setSecrDs(sAux);	
							sAux = rs.getString("TS_EXPE_DESC");
							if(validaNulo(sAux))
								gravaV.setFecDes(sAux);
							else
								gravaV.setFecDes("");		
							sAux = rs.getString("NUM_EXPE_DESC");
							if(validaNulo(sAux))
								gravaV.setNoXpDs(sAux);
							else		
								gravaV.setNoXpDs("");
							
							
							int nsGravamen = rs.getInt("NS_GRAVAMEN");
							
							//participantes en el gravamen							
							StringBuffer sql17 = new StringBuffer();
									
							sql17.append("SELECT /*+ORDERED*/ ").
								append(" rownum sec, pn.ape_pat AS ape_pat, pn.ape_mat AS ape_mat, pn.nombres AS nombres, NULL AS razon_social, pl.nombre AS des_part, ind.tipo_pers AS tipo_pers")
								.append(" FROM user1.gravamen g, user1.ind_prtc ind, user1.prtc_nat pn, user1.partic_libro pl ")
								.append(" WHERE g.num_placa = '").append(numPlaca).append("'")
								.append(" AND g.NS_GRAVAMEN = ").append(nsGravamen)
								.append(" AND ind.REFNUM_PART = g.REFNUM_PART ")
								.append(" AND ind.NS_GRAVAMEN = g.NS_GRAVAMEN ")
								.append(" AND ind.NUM_PLACA = g.NUM_PLACA ")
								.append(" AND ind.estado = '1' ")
								.append(" AND ind.tipo_pers = 'N' ")
								.append(" AND pn.cur_prtc = ind.cur_prtc ")
								.append(" AND pn.reg_pub_id ='").append(reg_pub_id).append("' ")
								.append(" AND pn.ofic_reg_id ='").append(ofic_reg_id).append("' ")
								.append(" AND ind.cod_partic = pl.cod_partic ")
								.append(" AND pl.cod_libro = '088' ")
								.append(" UNION ALL")
								.append(" SELECT /*+ORDERED*/ ")
								.append(" rownum sec, NULL AS ape_pat, NULL AS ape_mat, NULL AS nombres, pj.razon_social AS razon_social, pl.nombre AS des_part, ind.tipo_pers AS tipo_pers")
								.append(" FROM user1.gravamen g, user1.ind_prtc ind, user1.prtc_jur pj , user1.partic_libro pl ")
								.append(" WHERE g.num_placa = '").append(numPlaca).append("'")
								.append(" AND g.NS_GRAVAMEN = ").append(nsGravamen)
								.append(" AND ind.REFNUM_PART = g.REFNUM_PART ")
								.append(" AND ind.NS_GRAVAMEN = g.NS_GRAVAMEN ")
								.append(" AND ind.NUM_PLACA = g.NUM_PLACA ")
								.append(" AND ind.estado = '1' ")
								.append(" AND ind.tipo_pers = 'J' ")
								.append(" AND pj.cur_prtc = ind.cur_prtc")
								.append(" AND pj.reg_pub_id = '").append(reg_pub_id).append("' ")
								.append(" AND pj.ofic_reg_id = '").append(ofic_reg_id).append("' ")
								.append(" AND ind.cod_partic = pl.cod_partic ")
								.append(" AND pl.cod_libro = '088' ");
											
								if (isTrace(this)) System.out.println("QUERY 7 >> " + sql17.toString());
								rs1 = stmt1.executeQuery(sql17.toString());
																
								gravaV.getListbeanPartGrava().clear();
								j = 0;
								while(rs1.next())
								{
									beanPaGra = new VehiParticipaGrava();
									String aux = null;
									j++;
									StringBuffer prop = new StringBuffer();

									prop.append(String.valueOf(j)).append(". ");
									
									String tipoPersona = rs1.getString("tipo_pers") == null?"":rs1.getString("tipo_pers");														
									if(tipoPersona.equals("N"))
									{
										//persona natural					
										aux = rs1.getString("ape_pat");
										prop.append(aux==null?"":(new StringBuffer(aux).append(" ").toString()));
										aux = rs1.getString("ape_mat");
										prop.append(aux==null?"":(new StringBuffer(aux).append(" ").toString()));
										aux = rs1.getString("nombres");
										prop.append(aux==null?"":(new StringBuffer(aux).append(" ").toString()));
									}
									else
									{
										//persona juridica
										aux = rs1.getString("razon_social");
										if(validaNulo(aux))
										{						
											prop.append(aux);											
										}
									}
									
									String nomPartici = prop.toString();
									if(nomPartici.length() == 2)
										nomPartici = "---";
									 				
									beanPaGra.setNombres(nomPartici);
										
									sAux = rs1.getString("des_part");
									if(validaNulo(sAux))
										beanPaGra.setDescripcion(sAux);
									else
										beanPaGra.setDescripcion("---");										
										
									gravaV.getListbeanPartGrava().add(beanPaGra);																					
								}
								
							//copia el bean al Arraylist
							
							if(gravaV.getEstado().equals("LEVANTADO"))							
								lGrava2.add(gravaV);	
							else
								lGrava.add(gravaV);	
										
							
						}//fin del while

					if (cont == 0)
					{

							req.setAttribute("noGrav","0");
							req.setAttribute("noGrav2","0");	
					}
					else
					{
						//req.setAttribute("noGrav",String.valueOf(cont));
						
						if(lGrava.size() == 0)
						{
							req.setAttribute("noGrav","0");
							//lGrava.clear();
							
						}
						else
						{
							req.setAttribute("lGrava", lGrava);
						}	
								
						if(lGrava2.size() == 0)
						{
							req.setAttribute("noGrav2","0");
							//lGrava2.clear();
						
						}
						else
						{
							req.setAttribute("lGrava2", lGrava2);
						}	
					}

					
				//cjvc77
				/*StringBuffer sql6 = new StringBuffer();
				sql6.append("SELECT an_titu, no_titu from rpv.tt_titu_pend ")
					.append("WHERE no_plac ='").append(numPlaca).append("'");
				*/
				
				//recupera titulos que bloquean partidas
				if (isTrace(this)) trace("Recupera los bloqueos de partida", request);
				DboTaBloqPartida dboBloqPartida = new DboTaBloqPartida(dconn);
				/*
				dboBloqPartida.setFieldsToRetrieve(DboTaBloqPartida.CAMPO_ANO_TITU);
				dboBloqPartida.setFieldsToRetrieve(DboTaBloqPartida.CAMPO_NUM_TITU);
				*/
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_NUM_PARTIDA, partida.getNumPartida());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_OFIC_REG_ID, partida.getOficRegId());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_REG_PUB_ID, partida.getRegPubId());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_COD_LIBRO, partida.getCodLibro());
				dboBloqPartida.setField(DboTaBloqPartida.CAMPO_ESTADO, "1");
				
				//cjvc77
				//Obteniendo Titulos Pendientes
					//if (isTrace(this)) System.out.println("QUERY 6 >> " + sql6.toString());
					//rs = stmt.executeQuery(sql6.toString());
					cont = 0;
					AnoTituloBean tituPenBean = null;
					java.util.List listaTitulosPendientes = new ArrayList();
					String numero = new String();
					try
					{
						for(Iterator ite = dboBloqPartida.searchAndRetrieveList().iterator(); ite.hasNext();)
						{
							DboTaBloqPartida dbo = (DboTaBloqPartida) ite.next();
							cont++;
							tituPenBean = new AnoTituloBean();
							tituPenBean.setRegPubId(dbo.getField(dbo.CAMPO_REG_PUB_ID));
							tituPenBean.setOficRegId(dbo.getField(dbo.CAMPO_OFIC_REG_ID));
							tituPenBean.setAreaRegistralId(dbo.getField(dbo.CAMPO_AREA_REG_ID));
							tituPenBean.setAno(dbo.getField(dbo.CAMPO_ANO_TITU));
							numero = dbo.getField(dbo.CAMPO_NUM_TITU);
							for(int c = numero.length(); c < 8; c++)
								numero = "0" + numero;
							tituPenBean.setNumTitulo(numero);
							
							//verifica año y número de titulo
							if((tituPenBean.getAno().equals("9999")) || (tituPenBean.getNumTitulo().equals("99999999")))
								throw new CustomException("Titulos no disponibles");
							listaTitulosPendientes.add(tituPenBean);
						}
						if(listaTitulosPendientes.size() > 0)
							req.setAttribute("titupend", listaTitulosPendientes);
					}
					catch(CustomException e)
					{
						req.setAttribute("mensajetitupend", "Titulos no disponibles");
					}
					
				
				//recupero placas antiguas
				if(!((usuario.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO) || (usuario.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT) || (usuario.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO)))
				{
					
					//Motores y numeros de Serie anteriores					
					
					HistMotorBean bmotor = new HistMotorBean();
					ArrayList lmotor = new ArrayList();
					
					StringBuffer q1  = new StringBuffer();
					q1.append("select NUM_PLACA, NS_PLACA, NUM_SERIE, NUM_MOTOR from VEHICULO_HIST where REFNUM_PART=").append(Integer.parseInt(partida.getRefNumPart()));
					if (isTrace(this))
						System.out.println("Motores anteriores: "+q1.toString());					
					rs   = stmt.executeQuery(q1.toString());
					String aux = null;

					while(rs.next())
					{
						bmotor = new HistMotorBean();
						aux = rs.getString("NUM_PLACA");
						if(validaNulo(aux))
							bmotor.setPlaca(aux);
						else
							bmotor.setPlaca("---");	
						aux = rs.getString("NS_PLACA");
						if(validaNulo(aux))
							bmotor.setSecuencial(aux);
						else
							bmotor.setSecuencial("---");								
						aux = rs.getString("NUM_SERIE");
						if(validaNulo(aux))
							bmotor.setSerie(aux);
						else
							bmotor.setSerie("---");								
						aux = rs.getString("NUM_MOTOR");
						if(validaNulo(aux))
							bmotor.setMotor(aux);
						else
							bmotor.setMotor("---");	
							
						lmotor.add(bmotor);
						
					}
					if(lmotor.size()>0)
						req.setAttribute("bmotor",lmotor);
		

					//Propietarios anteriores
					HistPropietarioBean bpropi = new HistPropietarioBean();					
					ArrayList lpropi = new ArrayList();
					StringBuffer q2  = new StringBuffer();
					q2.append("SELECT /*+ORDERED*/ rownum sec, pn.ape_pat AS ape_pat, pn.ape_mat AS ape_mat, pn.nombres AS nombres, ")
						.append("pj.razon_social AS razon_social, ind.tipo_pers AS tipo_pers, ind.direccion AS direccion, ")
						.append("doc1.nombre_abrev AS tipo_docu_nat, nu_doc_iden AS num_docu_nat, doc2.nombre_abrev AS tipo_docu_jur, nu_doc AS num_docu_jur ")
						.append("FROM user1.ind_prtc ind, user1.prtc_nat pn , user1.prtc_jur pj, user1.tm_doc_iden doc1, user1.tm_doc_iden doc2 ")
						.append("WHERE ind.refnum_part = ")
						.append(Integer.parseInt(partida.getRefNumPart()))
						.append(" AND ind.cod_partic = '")
						.append(gob.pe.sunarp.extranet.util.Constantes.PROPIETARIO_VEHI)
						.append("' ") 
						.append("AND ind.estado != '1' ")
						.append("AND pn.cur_prtc(+)=ind.cur_prtc AND pn.reg_pub_id(+)='")
						.append(reg_pub_id)
						.append("' AND pn.ofic_reg_id(+)='")
						.append(ofic_reg_id)
						.append("' ")
						.append("AND pj.cur_prtc(+)=ind.cur_prtc AND pj.reg_pub_id(+)='")
						.append(reg_pub_id)
						.append("' AND pj.ofic_reg_id(+)='")
						.append(ofic_reg_id)
						.append("' ")
						.append("AND pn.ti_doc_iden = doc1.tipo_doc_id(+) AND pj.ti_doc = doc2.tipo_doc_id(+)");
					
					if (isTrace(this))
						System.out.println("Propietarios anteriores: "+q2.toString());
					
					rs   = stmt.executeQuery(q2.toString());			
					aux = null;
					String tip = null;
					StringBuffer sb = new StringBuffer();
					
					while(rs.next())
					{
						bpropi = new HistPropietarioBean();
						tip = rs.getString("TIPO_PERS");
						if(tip.equals("J"))
						{
							aux = rs.getString("RAZON_SOCIAL");
							if(aux!=null)
								bpropi.setNombre(aux);
							
							sb.delete(0,sb.length());
							aux = rs.getString("TIPO_DOCU_JUR");
							if(aux!=null)
								sb.append(aux);
							aux = rs.getString("NUM_DOCU_JUR");
							if(aux!=null)
								sb.append(" ").append(aux);
							bpropi.setDocumentos(sb.toString());
							
						}
						else
						{
							sb.delete(0,sb.length());
							aux = rs.getString("APE_PAT");
							if(aux!=null)
								sb.append(aux);
							aux = rs.getString("APE_MAT");
							if(aux!=null)
								sb.append(" ").append(aux);
							aux = rs.getString("NOMBRES");
							if(aux!=null)
								sb.append(", ").append(aux);
							bpropi.setNombre(sb.toString());
							
							sb.delete(0,sb.length());
							aux = rs.getString("TIPO_DOCU_NAT");
							if(aux!=null)
								sb.append(aux);
							aux = rs.getString("NUM_DOCU_NAT");
							if(aux!=null)
								sb.append(" ").append(aux);
							bpropi.setDocumentos(sb.toString());
						}
						aux = rs.getString("DIRECCION");
						if(aux!=null)
							bpropi.setDirecciones(aux);
						lpropi.add(bpropi);
						
					}
					if(lpropi.size()>0)
						req.setAttribute("bpropi",lpropi);


						
					//Placas anteriores
					StringBuffer sql6 = new StringBuffer();
					sql6.append("SELECT num_placa FROM vehiculo where refnum_part='").append(partida.getRefNumPart()).append("' and fg_baja = 1");
					if (isTrace(this)) System.out.println("QUERY 6 >> " + sql6.toString());
					rs = stmt.executeQuery(sql6.toString());
					String listaPlac = "NO SE ENCONTRARON PLACAS.";
					if(rs.next())
					{
						listaPlac = rs.getString("num_placa");
						while(rs.next())
						{
							listaPlac = listaPlac + "<br>" + rs.getString("num_placa");
						}
					}
					req.setAttribute("listaPlac", listaPlac);
				}
			}
			
							
				//ETIQUETA		
						
				// recuperamos el costo de la visualizacion
				
				
				double tarifa = 0;
				DboTarifa dboTarifa = new DboTarifa(dconn);
				dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
				dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, String.valueOf(Constantes.SERVICIO_CONSULTA_PARTIDA_RPV));
				if (dboTarifa.find())
				{ 		
					String sTarifa = dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC);
					tarifa = Double.parseDouble(sTarifa);
				}				 												
				req.setAttribute("tarifa",""+tarifa);
				
				// recuperamos el usuario			
				String usuaEtiq = usuario.getUserId();
				req.setAttribute("usuaEtiq",usuaEtiq);
				
				// recuperamos la fecha Actual									
				String fechaAct = FechaFormatter.deDateAFechaHoraWeb(new java.util.Date());
				req.setAttribute("fechaAct",fechaAct);
						

			
			//llamar a "Transaccion"
				LogAuditoriaConsultaPlacaBean bt = new LogAuditoriaConsultaPlacaBean();
				
				//Datos generales
				bt.setRemoteAddr(ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request)));
				bt.setUsuarioSession(usuario);
				bt.setCodigoServicio(TipoServicio.CONSULTA_PLACA);
				//Tarifario
				StringBuffer quebusq = new StringBuffer();
		
				quebusq.append("SELECT gla.cod_grupo_libro_area from grupo_libro_area gla, grupo_libro_area_det glad, tarifa t ");
				quebusq.append("where gla.cod_grupo_libro_area=glad.cod_grupo_libro_area AND t.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
				quebusq.append("AND glad.cod_libro=? AND t.servicio_id=? ");
				pstmt = null;
				pstmt = conn.prepareStatement(quebusq.toString());
				pstmt.setString(1,partida.getCodLibro());
				System.out.println(TipoServicio.CONSULTA_PLACA);
				pstmt.setInt(2,TipoServicio.CONSULTA_PLACA);
				rset = pstmt.executeQuery();
				
				if(!rset.next())
					throw new CustomException("Criterio no disponible");
				bt.setCodigoGLA(Integer.parseInt(rset.getString("cod_grupo_libro_area")));
				
				bt.setTipoBusq(0);//0 = Placa
				bt.setTipoParticipacion("4");//4 = Placa
				bt.setCodAreaReg(area_registral);// = RPV
				bt.setParamBusqueda(numPlaca);
				
				bt.setOficRegId(ofic_reg_id);
				bt.setRegPubId(reg_pub_id);
				
				if(flagNocobrar)
					bt.setFlagCobro("1");
				else
					bt.setFlagCobro("0");
				
				/*
					Job004 j = new Job004();
					j.setBean(bt);no
				*/
			if (Propiedades.getInstance().getFlagTransaccion()==true){
				PrepagoBean prepagoBean = Transaction.getInstance().registraTransaccion(bt,conn);
				
				/**
				 *  inicio, dbravo: 18/06/2007
				 *  descripcion: Se agrega el Job004, donde se ingresara el monto registrado en la tabla consumo,
				 *  			 - Se valida que q2 sea diferente de nula, si es nula, significa que no entro en la condición donde se ingresaba
				 *  			   inicialmente el Job003.
				 */
				if (usuario.getRegPublicoId().startsWith(Constantes.ID_ZONA_REGISTRAL_WEB)==false)
				{
					Job004 j = new Job004();
					
					j.setUsuario(usuario);
					j.setCodigoServicio(Constantes.SERVICIO_CONSULTA_PARTIDA_RPV);
					j.setRegPubId(partida.getRegPubId());
					j.setOficRegId(partida.getOficRegId());
					j.setArea(area_registral);
					j.setCostoServicio(prepagoBean.getMontoBruto());
					
					Thread llamador1 = new Thread(j);
					llamador1.start();
				}
				/**
				 *  fin, dbravo: 18/06/2007
				 */
			}
				
			conn.commit();
			response.setStyle("detallePlaca");
			
			System.out.println("detallePlaca------------>"+response.getStyle());
				
		}
		catch (ValidacionException ve)
		{
			principal(request);
			rollback(conn, request);
			response.setStyle("pantallaFinal");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("destino", "back");
			ExpressoHttpSessionBean.getRequest(request).setAttribute("mensaje1", ve.getMensaje());
		}
		catch (CustomException e)
		{
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
			{
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
				try
				{
					rollback(conn, request);
				}
				catch (Throwable ex)
				{
					log(Constantes.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					//rollback(connLocal, request);
					response.setStyle("error");
				}
			}
			else
			{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle(e.getForward());
				rollback(conn, request);
			}
		}
		catch (DBException dbe)
		{
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			rollback(conn, request);
			response.setStyle("error");
		}
		catch (Throwable ex)
		{
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}
		finally
		{
			JDBC.getInstance().closeResultSet(rs1);
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeResultSet(rs);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeStatement(stmt1);
			JDBC.getInstance().closeStatement(pstmt);
			if (pool != null)
				pool.release(conn);
			end(request);
		}
		
		
		return response;
	}
	
	/**
	 * Búsqueda Directa de Partidas. Registro Mobiliario de Contratos. Buscar por Ficha
	 * @param request
	 * @param response
	 * @return
	 * @throws ControllerException
	 */
	public ControllerResponse runBuscarXFichaRMCState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException {
			
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
		UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

		InputBusqDirectaBean inputBusqDirectaBean=null;		

		try {
			init(request);
			validarSesion(request);

			String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=3";

			inputBusqDirectaBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);

			ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
			String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
			
			FormOutputBuscarPartida output = consultarPartidaDirectaService.busquedaDirectaPorFichaRMC(ConsultarPartidaDirectaService.MEDIO_CONTROLLER, inputBusqDirectaBean, usuario, ipOrigen);
			
			output.setAction(req.getContextPath() +"/PublicidadIRI.do?state=buscarXFichaRMC");
			
			req.setAttribute("outNumber", inputBusqDirectaBean.getNumeroFicha());
			req.setAttribute("filtro", "f");
			req.setAttribute("rmc","rmc");		
			req.setAttribute("tarifa",""+output.getTarifa());		
			req.setAttribute("inputBusqDirectaBean", inputBusqDirectaBean);
			req.setAttribute("usuaEtiq",usuario.getUserId());				
			req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
			req.setAttribute("output", output);

		    // Inicio:mgarate:30/05/2007
		    req.setAttribute("criterioBusqueda",criterioBusqueda);
		    req.setAttribute("flagCertBusq",inputBusqDirectaBean.getCodGrupoLibroArea());
		    // Fin:mgarate:30/05/2007
		    
		    /*inicio:dbravo:14/09/2007*/
			req.setAttribute("flagVerifica", inputBusqDirectaBean.getVerifica());
			/*fin:dbravo:14/09/2007*/ 
			
		    response.setStyle("resultadoBusquedaDirectaRMC");
			
		} catch (ValidacionException e) {
			
			response.setStyle("pantallaFinal");
			req.setAttribute("destino","back");
			req.setAttribute("mensaje1",e.getMensaje());
			
		} catch (CustomException e) {
			
			if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
				{
					response.setStyle("pantallaFinal");
					req.setAttribute("destino","back");
					req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
				}
			else
				{
				log(e.getCodigoError(), e.getMessage(), request);
				principal(request);
				response.setStyle("error");
				}
		} catch (DBException dbe) {
			log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
			response.setStyle("error");
		} catch (Throwable ex) {
			log(Constantes.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			response.setStyle("error");
		} finally {
			end(request);
		}


		return response;
	}
	
	public ControllerResponse runBuscarXNroPartidaRMCState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
				
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			InputBusqDirectaBean inputBusqDirectaBean=null;		

			try {
				init(request);
				validarSesion(request);

				String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=2";

				inputBusqDirectaBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);

				ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
				String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
				
				FormOutputBuscarPartida output = consultarPartidaDirectaService.busquedaDirectaPorPartidaRMC(ConsultarPartidaDirectaService.MEDIO_CONTROLLER, inputBusqDirectaBean, usuario, ipOrigen);
				
				output.setAction("/webapp/extranet/Publicidad.do?state=buscarXNroPartidaRMC");
				
				req.setAttribute("outNumber", inputBusqDirectaBean.getNumeroPartida());
				req.setAttribute("filtro", "p");
				req.setAttribute("rmc","rmc");		
				req.setAttribute("tarifa",""+output.getTarifa());				
				req.setAttribute("usuaEtiq",usuario.getUserId());	
				req.setAttribute("inputBusqDirectaBean", inputBusqDirectaBean);
				req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
				req.setAttribute("output", output);

			    // Inicio:mgarate:30/05/2007
			    req.setAttribute("criterioBusqueda",criterioBusqueda);
			    req.setAttribute("flagCertBusq",inputBusqDirectaBean.getCodGrupoLibroArea());
			    // Fin:mgarate:30/05/2007
			    
			    /*inicio:dbravo:14/09/2007*/
				req.setAttribute("flagVerifica", inputBusqDirectaBean.getVerifica());
				/*fin:dbravo:14/09/2007*/ 
				
			    response.setStyle("resultadoBusquedaDirectaRMC");
				
			} catch (ValidacionException e) {
				
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1",e.getMensaje());
				
			} catch (CustomException e) {
				
				if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
					{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
					}
				else
					{
					log(e.getCodigoError(), e.getMessage(), request);
					principal(request);
					response.setStyle("error");
					}
			} catch (DBException dbe) {
				log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
				response.setStyle("error");
			} catch (Throwable ex) {
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				end(request);
			}


			return response;
		}
		
		public ControllerResponse runBuscarXTomoFolioRMCState(
			ControllerRequest request,
			ControllerResponse response)
			throws ControllerException {
				
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
			UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

			InputBusqDirectaBean inputBusqDirectaBean=null;		

			try {
				init(request);
				validarSesion(request);

				String criterioBusqueda = request.getParameter("criterio")+"/flagmetodo=1";

				inputBusqDirectaBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);

				ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
				String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
				
				FormOutputBuscarPartida output = consultarPartidaDirectaService.busquedaDirectaPorTomoFolioRMC(ConsultarPartidaDirectaService.MEDIO_CONTROLLER, inputBusqDirectaBean, usuario, ipOrigen);
				
				output.setAction("/webapp/extranet/Publicidad.do?state=buscarXTomoFolioRMC");
				
				req.setAttribute("outNumberTomo", inputBusqDirectaBean.getTomo());
				req.setAttribute("outNumberFolio", inputBusqDirectaBean.getFolio());
				req.setAttribute("outNumberLibro", inputBusqDirectaBean.getFolio());
				req.setAttribute("filtro", "tf");
				req.setAttribute("rmc","rmc");		
				req.setAttribute("tarifa",""+output.getTarifa());
				req.setAttribute("inputBusqDirectaBean", inputBusqDirectaBean);
				req.setAttribute("usuaEtiq",usuario.getUserId());				
				req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
				req.setAttribute("output", output);

			    // Inicio:mgarate:30/05/2007
			    req.setAttribute("criterioBusqueda",criterioBusqueda);
			    req.setAttribute("flagCertBusq",inputBusqDirectaBean.getCodGrupoLibroArea());
			    // Fin:mgarate:30/05/2007
			    
			    /*inicio:dbravo:14/09/2007*/
				req.setAttribute("flagVerifica", inputBusqDirectaBean.getVerifica());
				/*fin:dbravo:14/09/2007*/ 
				
			    response.setStyle("resultadoBusquedaDirectaRMC");
				
			} catch (ValidacionException e) {
				
				response.setStyle("pantallaFinal");
				req.setAttribute("destino","back");
				req.setAttribute("mensaje1",e.getMensaje());
				
			} catch (CustomException e) {
				
				if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
					{
						response.setStyle("pantallaFinal");
						req.setAttribute("destino","back");
						req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
					}
				else
					{
					log(e.getCodigoError(), e.getMessage(), request);
					principal(request);
					response.setStyle("error");
					}
			} catch (DBException dbe) {
				log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
				response.setStyle("error");
			} catch (Throwable ex) {
				log(Constantes.EC_GENERIC_ERROR, "", ex, request);
				principal(request);
				response.setStyle("error");
			} finally {
				end(request);
			}
			
			return response;
		}
		
		public ControllerResponse runDetallePartidaRMCState(
				ControllerRequest request,
				ControllerResponse response)
				throws ControllerException {
					
				HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);		
				UsuarioBean usuario = ExpressoHttpSessionBean.getUsuarioBean(request);

				InputBusqDirectaBean inputBusqDirectaBean=null;		

				try {
					init(request);
					validarSesion(request);

					inputBusqDirectaBean = Tarea.recojeDatosRequestBusqDirectaPartida(req);
					
					String refnumpart= request.getParameter("refnum_part");
					inputBusqDirectaBean.setRefNumPart(refnumpart);
					inputBusqDirectaBean.setCodGrupoLibroArea(Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC);
					
					ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
					String ipOrigen = ControlAccesoIP.obtenerIPRemota(ExpressoHttpSessionBean.getRequest(request));
					
					FormOutputBuscarPartida output = consultarPartidaDirectaService.busquedaDetallePartidaRMC(ConsultarPartidaDirectaService.MEDIO_CONTROLLER, inputBusqDirectaBean, usuario, ipOrigen);
					
					output.setAction("/webapp/extranet/Publicidad.do?state=detallePartidaRMC");
					
					req.setAttribute("outNumber", inputBusqDirectaBean.getNumeroFicha());
					req.setAttribute("rmc","rmc");		
					req.setAttribute("tarifa",""+output.getTarifa());				
					req.setAttribute("usuaEtiq",usuario.getUserId());				
					req.setAttribute("fechaAct",FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()));
					req.setAttribute("output", output);

				    response.setStyle("detallePartidaRMC");
					
				}catch (CustomException e) {
					
					if (e.getCodigoError().equals(E70002_NO_ENCONTRO_PARTIDA))
						{
							response.setStyle("pantallaFinal");
							req.setAttribute("destino","back");
							req.setAttribute("mensaje1","<br><br><br><br><br>Lo sentimos, no se encontro la partida buscada<br><br><br><br><br>");
						}
					else
						{
						log(e.getCodigoError(), e.getMessage(), request);
						principal(request);
						response.setStyle("error");
						}
				} catch (DBException dbe) {
					log(Constantes.EC_GENERIC_DB_ERROR, "", dbe, request);
					response.setStyle("error");
				} catch (Throwable ex) {
					log(Constantes.EC_GENERIC_ERROR, "", ex, request);
					principal(request);
					response.setStyle("error");
				} finally {
					end(request);
				}


				return response;
			}
		
}
