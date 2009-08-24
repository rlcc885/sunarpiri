package gob.pe.sunarp.extranet.framework.session;

import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import java.sql.*;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/** Made by KumaX **//** 19/03/2003 **/

public class MonitorDeSesion implements javax.servlet.http.HttpSessionBindingListener
{
	/** 
		Esta clase puede ser usada para realizar un conjunto de acciones al finalizar la sesion.
		Para ello debemos insertar una instancia de esta clase como atributo de la sesion, solo una vez.
		
		if(sesion.getAttribute("monitor")==null)
		{
			MonitorDeSesion monitor = new MonitorDeSesion();
			sesion.setAttribute("monitor",monitor);
		}
		
	 **/
	public boolean isTrace(Object _this) 
	{
		return Loggy.isTrace(_this);
	}
	
	public void valueBound(HttpSessionBindingEvent event)
	{
		/** Este evento se ejecuta cuando el objeto es agregado a la sesion **/
		
	}
	
	public void valueUnbound(HttpSessionBindingEvent event)
	{
		/** Este evento se ejecuta cuando el objeto es sacado de la sesion (o cuando termina la sesion) **/
		if(isTrace(this))
			System.out.println("Sesion " + event.getSession().getId() + " finalizada");
		DBConnectionFactory pool = DBConnectionFactory.getInstance();
		Connection conn = null;
		Statement stmt = null;
		try
		{
			conn = pool.getConnection();
			//conn.setAutoCommit(false);
			
			StringBuffer sb = new StringBuffer();
			
			sb.delete(0,sb.length());
			sb.append("DELETE FROM DATA_SESSION WHERE SESSION_ID = '").append(event.getSession().getId()).append("'");
			stmt   = conn.createStatement();
			int n = stmt.executeUpdate(sb.toString());
			//conn.commit();
			if(isTrace(this))
				System.out.println(sb.toString());
			if(isTrace(this))
				System.out.println("Se eliminaron " + n + " registros temporales");
		}
		catch (SQLException e)
		{
			if(isTrace(this))
				System.out.println("No se pudo eliminar de tabla temporal DATA_SESSION, session_id = " + event.getSession());
			e.printStackTrace();
		}
		catch (Exception ex)
		{
			if(isTrace(this))
				System.out.println("No se elimino de tabla temporal DATA_SESSION, session_id = " + event.getSession());
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt!=null)
					stmt.close();
			}
			catch (SQLException sqle)
			{
				if(isTrace(this))
					System.out.println("No se pudo cerrar Statement");
			}			
			pool.release(conn);
		}		
	}	
}

