package gob.pe.sunarp.extranet.administracion.job;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.jcorporate.expresso.core.job.*;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.SecuredDBObject;
import com.jcorporate.expresso.core.security.User;
import com.jcorporate.expresso.services.dbobj.Event;
import com.jcorporate.expresso.core.misc.EventHandler;
import com.jcorporate.expresso.services.dbobj.JobQueue;
import com.jcorporate.expresso.services.dbobj.JobQueueParam;
import com.jcorporate.expresso.services.html.*;
import com.jcorporate.expresso.core.misc.StringUtil;
import org.apache.log4j.Category;

import com.jcorporate.expresso.core.logging.LogManager;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.job.Job;
import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.EventsRepository;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboParametros;
import gob.pe.sunarp.extranet.framework.JobLog;
import gob.pe.sunarp.extranet.framework.Logger;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.util.FechaUtil;
import java.util.ArrayList;


public class DesactivacionUsuarioJob extends Job{

	private String thisClass = new String(this.getClass().getName() + ".");

/*    public Job1() {
        setSchema("com.jcorporate.expresso.core.ExpressoSchema");
    } 
*/

	public String getTitle() {
		return getString("Test_Job"); 
	}

 public void run() {
		super.run();
		
		DBConnectionPool myPool = null;
		DBConnection myConn = null;

		

		EventsRepository events = JobLog.createEvents("Desactivacion Usuario", "SYSTEM");
		JobLog.trace(events, this, "Iniciando desactivacion usuario");
		
		try{
			myPool = DBConnectionPool.getInstance(getDBName());
			myConn = myPool.getConnection();
			myConn.setAutoCommit(false);
			
			DboParametros param = new DboParametros(myConn);
	
			// Se encuentra valor de parametro
			param.setFieldsToRetrieve(DboParametros.CAMPO_VALOR);
			param.setField(DboParametros.CAMPO_COD_PRM, "MDI");
			param.find();
	
			int dias = Integer.parseInt(param.getField(DboParametros.CAMPO_VALOR));
			
			// Se obtiene la fecha a buscar
			String fecha_limite = FechaUtil.add(FechaUtil.dateToString(new java.util.Date()), -dias);
			String fecha = " < " + FechaUtil.stringTimeToOracleString(fecha_limite + " 00:00:00");
			
			DboCuenta cuenta = new DboCuenta(myConn);
			cuenta.setField(DboCuenta.CAMPO_TS_ULT_ACC, fecha);
	
			ArrayList listaCuentas = cuenta.searchAndRetrieveList();
			for(int i = 0; i < listaCuentas.size(); i++)
			{
				DboCuenta cuentas = (DboCuenta) listaCuentas.get(i);
				cuentas.setConnection(myConn);
				cuentas.setFieldsToUpdate(DboCuenta.CAMPO_ESTADO);
				cuentas.setField(DboCuenta.CAMPO_ESTADO, "0");
				cuentas.update();
			}

			myConn.commit();
		} //try
		catch (DBException dbe) 
		{
			JobLog.log(events, this, Errors.EC_GENERIC_DB_ERROR, "", dbe);
			JobLog.rollback(myConn, events);
		} catch (Throwable ex) 
		{
			JobLog.log(events, this, Errors.EC_GENERIC_ERROR, "", ex);
			JobLog.rollback(myConn, events);
		} finally {
			if (myPool != null)
				myPool.release(myConn);
			JobLog.end(events);
		}
	}
}