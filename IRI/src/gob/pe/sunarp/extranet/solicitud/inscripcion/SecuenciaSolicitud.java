package gob.pe.sunarp.extranet.solicitud.inscripcion;

import com.jcorporate.expresso.core.db.*;
import gob.pe.sunarp.extranet.pool.*;

import gob.pe.sunarp.extranet.util.*;
//import gob.pe.sunarp.extranet.framework.*;
import java.sql.*;
import gob.pe.sunarp.extranet.dbobj.*;


public class SecuenciaSolicitud {

	public static synchronized String obtieneSecuencia() throws Exception {

		DBConnectionFactory pool = null;
		Connection conn = null;
		DBConnection myConn = null;
		DboSecSolicitud secuencia = null;
		int resp1;
		String resp2 = null;
		int sizeResp1 = 0;
		String cad = "";
		int dif = 0;
		
		try { 
		
			pool = DBConnectionFactory.getInstance();
			
			conn = pool.getConnection();
			conn.setAutoCommit(false);
		   	myConn = new DBConnection(conn);

			//OBTENEMOS LA SECUENCIA
			secuencia = new DboSecSolicitud(myConn);
			secuencia.setFieldsToRetrieve(DboSecSolicitud.CAMPO_SECUENCIA);
			secuencia.setField(DboSecSolicitud.CAMPO_ANHO, (FechaUtil.getCurrentDateYYYYMMDD()).substring(0,4));
		
			secuencia.find();
			
			resp1 = secuencia.getFieldInt(DboSecSolicitud.CAMPO_SECUENCIA);
			
			//FORMAMOS LA CADENA
			sizeResp1 = (String.valueOf(resp1)).length();
			
			if (sizeResp1<8) {
				dif = 7 - sizeResp1;
				for (int i=0; i< dif; i++) {
					cad = cad + "0";
				}
			}

			resp2 = "V" + cad + String.valueOf(resp1);

			// ACTUALIZAMOS EL SECUENCIAL		
			secuencia.setFieldsToUpdate(DboSecSolicitud.CAMPO_SECUENCIA);
			secuencia.setField(DboSecSolicitud.CAMPO_SECUENCIA, resp1+1);
			secuencia.update();

			myConn.commit();

		} catch (Exception e) {
			try {
				myConn.rollback();
			}
			catch(DBException dbe1){}

			throw e;

		} finally {
			try{
				pool.release(conn);
			}
			catch (Throwable t)	{}
		}		

		return resp2;
	
	}

}

