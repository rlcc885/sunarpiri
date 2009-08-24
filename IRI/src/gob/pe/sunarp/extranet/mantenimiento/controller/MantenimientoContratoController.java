package gob.pe.sunarp.extranet.mantenimiento.controller;

//paquetes del sistema
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.controller.session.*;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.misc.*;
//paquetes del proyecto
import gob.pe.sunarp.extranet.framework.session.*;
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.transaction.*;
import gob.pe.sunarp.extranet.mantenimiento.bean.*;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;

public class MantenimientoContratoController extends ControllerExtension {
	private String thisClass = MantenimientoContratoController.class.getName() + ".";
	public MantenimientoContratoController() {
		super();
		addState(new State("mantenimiento", "c"));
		setInitialState("mantenimiento");
	}
	public ControllerResponse runMantenimientoState(ControllerRequest request, ControllerResponse response)
		throws ControllerException {

DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

		String ruta = "error";

		try {
			init(request);
			validarSesion(request);

			HttpServletRequest req = ExpressoHttpSessionBean.getRequest(request);
			
			
conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
			//-
			int p1 = 0;
			String x = req.getParameter("P1");
			if (x != null)
				p1 = Integer.parseInt(x);

			String p2 = req.getParameter("P2");

			DboVerContrato dbo1 = new DboVerContrato(dconn);
			java.util.ArrayList arr1 = null;
			VerContratoBean bean1 = null;

			//inicio
			switch (p1) {
				case 1 :
					//ver
					break;
				case 2 :
					//mostrar fomulario de actualizacion
					dbo1.setField(DboVerContrato.CAMPO_VER_CONTRATO_ID, p2);
					dbo1.find();
					bean1 = new VerContratoBean();
					bean1.setVerContratoId(p2);
					bean1.setVerContrato(dbo1.getField(DboVerContrato.CAMPO_VER_CONTRATO));
					bean1.setFechaHoraCreacion(dbo1.getField(DboVerContrato.CAMPO_TS_CREA));
					req.setAttribute("bean1", bean1);
					req.setAttribute("modo", "20");
					ruta = "form";
					break;
				case 3 :
					//delete
					break;
				case 4 :
					//mostrar formulario de insercion
					bean1 = new VerContratoBean();
					req.setAttribute("bean1", bean1);
					req.setAttribute("modo", "40");
					ruta = "form";
					break;
			} //switch

			//continuacion
			switch (p1) {
				case 20 :
					//update!
					dbo1.setField(DboVerContrato.CAMPO_VER_CONTRATO_ID, req.getParameter("txt0"));
					dbo1.setFieldsToUpdate(DboVerContrato.CAMPO_VER_CONTRATO + "|" + DboVerContrato.CAMPO_TS_ULT_MODIF);
					dbo1.setField(DboVerContrato.CAMPO_VER_CONTRATO, req.getParameter("txt1"));
					dbo1.setField(DboVerContrato.CAMPO_TS_ULT_MODIF, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
					dbo1.update();
					conn.commit();
					p1 = 0;
					break;
				case 40 :
					//insert!
					dbo1.setField(DboVerContrato.CAMPO_USR_ULT_MODIF, "usr");
					dbo1.setField(DboVerContrato.CAMPO_USR_CREA, "usr");
					dbo1.setField(DboVerContrato.CAMPO_TS_ULT_MODIF, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
					dbo1.setField(DboVerContrato.CAMPO_TS_CREA, FechaUtil.stringToOracleString(FechaUtil.getCurrentDate()));
					dbo1.setField(DboVerContrato.CAMPO_VER_CONTRATO, req.getParameter("txt1"));
					dbo1.add();
					conn.commit();
					p1 = 0;
					break;
			} //switch

			if (p1 == 0) {
				//listar
				dbo1.clearAll();
				arr1 = dbo1.searchAndRetrieveList(DboVerContrato.CAMPO_VER_CONTRATO + " Desc");
				java.util.ArrayList arrx = new java.util.ArrayList();
				for (int i = 0; i < arr1.size(); i++) {
					DboVerContrato d = (DboVerContrato) arr1.get(i);
					bean1 = new VerContratoBean();
					bean1.setVerContratoId(d.getField(DboVerContrato.CAMPO_VER_CONTRATO_ID));
					bean1.setVerContrato(d.getField(DboVerContrato.CAMPO_VER_CONTRATO));
					bean1.setFechaHoraCreacion(d.getField(DboVerContrato.CAMPO_TS_CREA));
					arrx.add(bean1);
				}
				req.setAttribute("arr1", arrx);
				ruta = "lista";
			}
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
				pool.release(conn);
			end(request);
		}
		response.setStyle(ruta);
		return response;
	}
}