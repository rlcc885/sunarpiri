package gob.pe.sunarp.extranet.mantenimiento.controller;


//paquetes del sistema
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import com.jcorporate.expresso.core.misc.*;

//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.framework.ControllerExtension;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class RecalcularTarifasController extends ControllerExtension {

	private String VALOR_ACTUAL_UIT = "UIT";
	private String C_ADM_PAG_CRED   = "CTC";
	private String C_ADM_PAG_DEB = "CTD";
	
	public RecalcularTarifasController() {
		super(); 
		addState(new State("mostrarFormulario", "muestra formulario de servicios y tarifas"));
		addState(new State("recalcularTarifas", "recalcular tarifas de formularios"));
		setInitialState("mostrarFormulario");
	}
	
	public ControllerResponse runMostrarFormularioState(
		ControllerRequest request,
		ControllerResponse response)
		throws ControllerException
	{
		
		HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
		
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;		
		
		try{
			//carga de datos extra
			//Datos de la tabla parametros
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			
			List paramArray = null;
			DboParametros parametros = new DboParametros(dconn);
			paramArray = (ArrayList)parametros.searchAndRetrieveList();
			double valorActualIUIT = 0.0;
			
			for(int i = 0 ; i < paramArray.size(); i++){ 
			
				DboParametros paramDB = (DboParametros)paramArray.get(i);
				if(paramDB.getField(paramDB.CAMPO_COD_PRM).equals(this.VALOR_ACTUAL_UIT)){
					req.setAttribute("V_A_UIT",paramDB.getField(paramDB.CAMPO_VALOR));
					valorActualIUIT = Double.parseDouble(paramDB.getField(paramDB.CAMPO_VALOR));
				}else if(paramDB.getField(paramDB.CAMPO_COD_PRM).equals(this.C_ADM_PAG_CRED)){
					req.setAttribute("C_CRED",paramDB.getField(paramDB.CAMPO_VALOR));
				}else if(paramDB.getField(paramDB.CAMPO_COD_PRM).equals(this.C_ADM_PAG_DEB)){
					req.setAttribute("C_DEBIT",paramDB.getField(paramDB.CAMPO_VALOR));
				}
			}	

			//Tarifario
			//Creamos un MultiDBObject para Servicio y Tarifa
			/*
			MultiDBObject servicioTarifa = new MultiDBObject(dconn);
			servicioTarifa.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmServicio","servicio");
			servicioTarifa.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTarifa","tarifa");
			servicioTarifa.setForeignKey("tarifa","SERVICIO_ID","servicio","SERVICIO_ID");
			servicioTarifa.clear();
			servicioTarifa.setField("servicio","ESTADO","1");
			
			Vector resultados = new Vector();
			
			resultados = servicioTarifa.searchAndRetrieve();
			//cargar en un output el resultado de la búsqueda
			
			
			for(int i = 0 ; i < resultados.size(); i++){
				MultiDBObject multi = (MultiDBObject)resultados.get(i);
				double precioCalculado = 0.0;
				
				Output output = new Output();
				output.setName("servTarifas"+i);
				//Tarifario
				output.setAttribute("ID_SERVICIO", multi.getField("servicio","SERVICIO_ID"));
				output.setAttribute("NOMBRE", multi.getField("servicio","NOMBRE"));
				output.setAttribute("PORC_UIT", multi.getField("tarifa","PORC_UIT"));
				precioCalculado = (Double.parseDouble(multi.getField("tarifa","PORC_UIT"))/100)*valorActualIUIT;
				BigDecimal precioCalcRed = new BigDecimal(precioCalculado).setScale(2,BigDecimal.ROUND_HALF_UP);
				output.setAttribute("PRECIO_CALCULADO",String.valueOf(precioCalcRed));
				output.setAttribute("PRECIO_OFICIAL",multi.getField("tarifa","PREC_OFIC"));
				//Tarifario
				output.setAttribute("ID_TARIFA", multi.getField("tarifa","TARIFA_ID"));
				response.addOutput(output);
			}
			*/
			StringBuffer quebusq = new StringBuffer();
			
			quebusq.append("SELECT TM_SERVICIO.SERVICIO_ID, TM_SERVICIO.NOMBRE ||' '|| gla.DESC_GRUPO_LIBRO_AREA AS NOMBRE, TM_SERVICIO.ESTADO, TM_SERVICIO.FG_MULT, TARIFA.TARIFA_ID, TARIFA.PORC_UIT, ");
			quebusq.append("TARIFA.PREC_OFIC, TARIFA.SERVICIO_ID, TARIFA.USR_ULT_MODIF, TARIFA.USR_CREA, TARIFA.TS_ULT_MODIF, TARIFA.TS_CREA, TARIFA.COD_GRUPO_LIBRO_AREA ");
			quebusq.append("FROM TM_SERVICIO,TARIFA, grupo_libro_area gla WHERE TM_SERVICIO.ESTADO = '1' AND TARIFA.SERVICIO_ID = TM_SERVICIO.SERVICIO_ID AND tarifa.cod_grupo_libro_area = gla.cod_grupo_libro_area(+)  ");
			System.out.println(quebusq.toString());
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(quebusq.toString());
			ResultSet rset = pstmt.executeQuery();
			int i = 0;
			while (rset.next())
			{
				double precioCalculado = 0.0;
				
				Output output = new Output();
				output.setName("servTarifas"+i);
				//Tarifario
				output.setAttribute("ID_SERVICIO", rset.getString("SERVICIO_ID"));
				output.setAttribute("NOMBRE", rset.getString("NOMBRE"));
				output.setAttribute("PORC_UIT", rset.getString("PORC_UIT"));
				precioCalculado = (Double.parseDouble(rset.getString("PORC_UIT"))/100)*valorActualIUIT;
				BigDecimal precioCalcRed = new BigDecimal(precioCalculado).setScale(2,BigDecimal.ROUND_HALF_UP);
				output.setAttribute("PRECIO_CALCULADO",String.valueOf(precioCalcRed));
				output.setAttribute("PRECIO_OFICIAL", rset.getString("PREC_OFIC"));
				//Tarifario
				output.setAttribute("ID_TARIFA", rset.getString("TARIFA_ID"));
				response.addOutput(output);
				i++;
			}
			
			
		}catch(Throwable ex)	{
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");		
		}
		finally
		{
			pool.release(conn);
			end(request);
		}
		
		response.setStyle("servicios");
		
		return response;
	}

	public ControllerResponse runRecalcularTarifasState(
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
DBConnection dconn = new DBConnection(conn);
			
			Loggy.info("METODO: RecalcularTarifas COMANDO: " + this.getClass().getName(), this, request);
		 
			HttpSession session = ExpressoHttpSessionBean.getSession(request);
			UsuarioBean datosUsuario = ExpressoHttpSessionBean.getUsuarioBean(request);
		
			//grabar datos recalculados de tarifas
			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			//Tarifario
			String [] serviciosID = req.getParameterValues("id_servicio");
			String [] tarifasID = req.getParameterValues("id_tarifa");
			String [] nuevosPorcUIT = req.getParameterValues("porc_uit");
			String [] nuevosPreciosOficial = req.getParameterValues("pre_oficial");
			String valorActualUIT = req.getParameter("valorUIT");
			String cCredito = req.getParameter("c_cre");
			String cDebito = req.getParameter("c_debit");
			
			//actualizar la tabla parametros
			DboParametros parametros = new DboParametros(dconn);
			DboParametros parametros1 = new DboParametros(dconn);
			DboParametros parametros2 = new DboParametros(dconn);

			parametros.setField(parametros.CAMPO_COD_PRM,this.VALOR_ACTUAL_UIT);
			parametros.find();
			parametros.setFieldsToUpdate(parametros.CAMPO_VALOR+"|"+parametros.CAMPO_USUA_MODI+"|"+parametros.CAMPO_TS_MODI);
			parametros.setField(parametros.CAMPO_VALOR,valorActualUIT);
			parametros.setField(parametros.CAMPO_USUA_MODI,datosUsuario.getUserId());
			parametros.setField(parametros.CAMPO_TS_MODI,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			parametros.update();
		
			//segundo update
			parametros1.setField(parametros.CAMPO_COD_PRM,this.C_ADM_PAG_CRED);
			parametros1.find();
			parametros1.setFieldsToUpdate(parametros.CAMPO_VALOR+"|"+parametros.CAMPO_USUA_MODI+"|"+parametros.CAMPO_TS_MODI);
			parametros1.setField(parametros.CAMPO_VALOR,cCredito);
			parametros1.setField(parametros.CAMPO_USUA_MODI,datosUsuario.getUserId());
			parametros1.setField(parametros.CAMPO_TS_MODI,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			parametros1.update();
		
			//tercer update
		
			parametros2.setField(parametros.CAMPO_COD_PRM,this.C_ADM_PAG_DEB);
			parametros2.find();
			parametros2.setFieldsToUpdate(parametros.CAMPO_VALOR+"|"+parametros.CAMPO_USUA_MODI+"|"+parametros.CAMPO_TS_MODI);
			parametros2.setField(parametros.CAMPO_VALOR,cDebito);
			parametros2.setField(parametros.CAMPO_USUA_MODI,datosUsuario.getUserId());
			parametros2.setField(parametros.CAMPO_TS_MODI,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
			parametros2.update();
		
			//update de la tabla tarifas
			//Tarifario
			//for(int i = 0 ; i < serviciosID.length; i++)
			for(int i = 0 ; i < tarifasID.length; i++)
			{
				DboTarifa tarifadbo = new DboTarifa(dconn);
				//Tarifario
				//tarifadbo.setField(tarifadbo.CAMPO_SERVICIO_ID, serviciosID[i]);
				tarifadbo.setField(tarifadbo.CAMPO_TARIFA_ID, tarifasID[i]);
				tarifadbo.find();
				tarifadbo.setFieldsToUpdate(tarifadbo.CAMPO_PORC_UIT+"|"+tarifadbo.CAMPO_PREC_OFIC);
				tarifadbo.setField(tarifadbo.CAMPO_PORC_UIT,nuevosPorcUIT[i]);
				tarifadbo.setField(tarifadbo.CAMPO_PREC_OFIC, nuevosPreciosOficial[i]);
				tarifadbo.setField(tarifadbo.CAMPO_TS_ULT_MODIF,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				tarifadbo.setField(tarifadbo.CAMPO_USR_ULT_MODIF, datosUsuario.getUserId());
				tarifadbo.update();
			}
		
			conn.commit();
			this.transition("mostrarFormulario",request,response);
		
		
		/* TERMINAMOS TRANSACCION CON EXITO */
			if (isTrace(this)) trace("Realizando commit a la base de datos", request);
			conn.commit();
		/*} catch (CustomException e) {
			log(e.getCodigoError(), e.getMessage(), request);
			principal(request);
			rollback(conn, request);
			response.setStyle(findForward(e.getForward(), request));
		*/}catch(DBException dbe){
			log(Errors.EC_GENERIC_DB_ERROR, "", dbe, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}catch(Throwable ex){
			log(Errors.EC_GENERIC_ERROR, "", ex, request);
			principal(request);
			rollback(conn, request);
			response.setStyle("error");
		}finally{
			pool.release(conn);
			end(request);
		}
		
		return response;
	}

}

