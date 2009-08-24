package gob.pe.sunarp.extranet.administracion.util;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.util.Propiedades;
import java.util.ArrayList;

import com.tivoli.pd.jadmin.PDPolicy;
import com.tivoli.pd.jutil.PDException;
import com.tivoli.pd.jutil.PDMessages;

public class ControlDiaHoraAcceso
{
	public static void configurarFechaHora(String usuario) throws Throwable 
	{
		try 
		{
			SecAdmin secAdmin = SecAdmin.getInstance();
			secAdmin.configurarFechaHoraAcceso(usuario);	
		} 
		catch (Exception e) 
		{
			throw e;
		} 
		catch (Throwable t) 
		{
			throw t;
		}
	} 
}
