package gob.pe.sunarp.extranet.reportegeneral.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.framework.xml.XErrorManejado;
import gob.pe.sunarp.extranet.framework.xml.XOficinaDB;

public class ConsolaCentral extends SunarpBean{


	private static ConsolaCentral single = null;
	private static java.util.Map erroresManejados = null;
	private static java.util.Map dbOficinas = null;
	
	private ConsolaCentral(){
		erroresManejados = java.util.Collections.synchronizedMap(new java.util.HashMap());
		dbOficinas = java.util.Collections.synchronizedMap(new java.util.HashMap());
	}
	
	public static synchronized ConsolaCentral getInstance(){
		if(single == null)
			single = new ConsolaCentral();
		return single;
	}
	
	public static synchronized void init(XErrorManejado[] errores, XOficinaDB[] oficinasDB) throws Throwable{
		for(int i = 0; i < errores.length; i++)
			erroresManejados.put(errores[i].getCodigo(), errores[i].getDescripcion());

		OficinaConection oficinaCon = null;
		for(int j = 0; j < oficinasDB.length; j++){
			oficinaCon = new OficinaConection();
			oficinaCon.setUrl(oficinasDB[j].getUrlDB());
			oficinaCon.setUser(oficinasDB[j].getUserDB());
			oficinaCon.setPassword(oficinasDB[j].getPwdDB());
			
			dbOficinas.put(oficinasDB[j].getCodigo(), oficinaCon);
		}
	}
	/**
	 * Gets the erroresManejados
	 * @return Returns a java.util.HashMap
	 */
	public static java.util.Map getErroresManejados() {
		return erroresManejados;
	}

	/**
	 * Gets the dbOficinas
	 * @return Returns a java.util.HashMap
	 */
	public static java.util.Map getDbOficinas() {
		return dbOficinas;
	}
}

