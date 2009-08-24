package gob.pe.sunarp.extranet.prepago.pruebas;

import org.apache.struts.action.ActionMapping;
import com.jcorporate.expresso.core.controller.Controller;
import com.jcorporate.expresso.core.controller.ControllerException;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.controller.ControllerResponse;
import com.jcorporate.expresso.core.controller.State;
import com.jcorporate.expresso.core.controller.session.HTTPPersistentSession;
import com.jcorporate.expresso.core.controller.session.PersistentSession;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import com.jcorporate.expresso.core.misc.ConfigManager;
import com.jcorporate.expresso.core.misc.DateTime;
import com.jcorporate.expresso.services.dbobj.JobQueue;
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.util.FechaUtil;
import com.jcorporate.expresso.core.controller.*;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboPerfilCuenta;
import gob.pe.sunarp.extranet.dbobj.DboPermisoUsr;
import gob.pe.sunarp.extranet.dbobj.DboTmDepartamento;
import gob.pe.sunarp.extranet.dbobj.DboTmPregSecretas;
import gob.pe.sunarp.extranet.dbobj.DboTmProvincia;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Ingreso1 extends ControllerExtension {

	private String thisClass = Ingreso1.class.getName() + ".";

	public Ingreso1() {
		super();
		addState(
			new State("validaIngreso", "Valida el usuario y password ingresados (dummy, en realidad lo va a hacer el WebSeal)"));
		setInitialState("validaIngreso");
	}

	public String getTitle() {
		return new String("Ingreso Controller");
	}

	protected ControllerResponse runValidaIngresoState(ControllerRequest request, ControllerResponse response)
		throws ControllerException {
		try {

			//String a = null;
			/*Jobs
			JobQueue oneJob = new JobQueue();

			//oneJob.setDBName(request.getDBName());

			oneJob.setJobCode("gob.pe.sunarp.extranet.administracion.job.DesactivacionUsuarioJob");

			oneJob.setUid("3");
			
			oneJob.setCronParams(-1, -1, -1, -1, -1, -1);
			oneJob.setJobStatus(JobQueue.JOB_STATUS_AVAILABLE);
			oneJob.add();
			*/
			
			//Para Credito
			Output urlParams = null;
			if(request.getParameter("CODTIENDA")!=null){
				String p1 = request.getParameter("CODTIENDA");
				String p2 = request.getParameter("NUMORDEN");
				String parametros = gob.pe.sunarp.extranet.util.DirVisa.getInstance().getDirDesa()+"IncrementarSaldo.do?state=resultadoAbonoCredito&cod_tienda=" + p1 + "&nordent=" + p2 + "&respuesta=1";
				urlParams = new Output("urlParams", parametros);
			}else{
			//Para Debito
				String d0 = request.getParameter("D0");
				String r1 = "20020821";
				String r2 = "095000";
				String r3 = "1001";
				String r4 = "00991";
				String r5 = "00000";
				String parametros = gob.pe.sunarp.extranet.util.DirVisa.getInstance().getDirDesa()+"IncrementarSaldo.do?state=resultadoAbonoDebito&D0=" + d0 + "&R1=" + r1 + "&R2=" + r2 + "&R3=" + r3 + "&R4=" + r4 + "&R5=" + r5;
				urlParams = new Output("urlParams", parametros);
			}
			
			
			/* NO BORRAR
			DboTmProvincia prov = new DboTmProvincia();
			prov.setDBName(request.getDBName());
			prov.clear();
			prov.clearFieldsToRetrieve();
			prov.setFieldsToRetrieve(DboTmProvincia.CAMPO_NOMBRE);
			
			DboTmDepartamento dept = new DboTmDepartamento();
			dept.setDBName(request.getDBName());
			dept.clear();
			dept.clearFieldsToRetrieve();
			//dept.setFieldsToRetrieve(DboTmDepartamento.CAMPO_ESTADO);
			*/
			/* Pruebas para MULTIDBOBJECTS
			MultiDBObject multi = new MultiDBObject();
			multi.setDBName(request.getDBName());
			
			multi.addDBObj(prov, "prov");
			multi.addDBObj(dept, "dept");
			multi.setForeignKey("dept", DboTmDepartamento.CAMPO_DPTO_ID, "prov", DboTmProvincia.CAMPO_DPTO_ID);
			MultiDBObject m = null;
			int i = 0;
			multi.setMaxRecords(10);
			
			for(java.util.Enumeration e = multi.searchAndRetrievePaginado(2).elements(); e.hasMoreElements();){
				m = (MultiDBObject) e.nextElement();
				if (isTrace(this)) System.out.println("Campo: " + i++);
				if (isTrace(this)) System.out.println(m.getField("prov", DboTmProvincia.CAMPO_NOMBRE));
			}
			*/
			/* Pruebas para DBOBJECT
			int i = 0;
			DboTmDepartamento m = null;
			dept.setMaxRecords(10);
			for(java.util.Enumeration e = dept.searchAndRetrievePaginado(1).elements(); e.hasMoreElements();){
				m = (DboTmDepartamento) e.nextElement();
				if (isTrace(this)) System.out.println("Campo: " + i++);
				if (isTrace(this)) System.out.println(m.getField(DboTmDepartamento.CAMPO_NOMBRE));
			}
			*/
			response.addOutput(urlParams);
		} catch (Throwable thr) {
			if (isTrace(this)) System.out.println("ERROR" + thr.getMessage());
		}
		response.setStyle("visa");
		return response;
	}
}