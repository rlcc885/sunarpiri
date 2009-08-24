package gob.pe.sunarp.extranet.reportegeneral.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.framework.xml.XErrorManejado;
import gob.pe.sunarp.extranet.framework.xml.XOficinaDB;

public class DatosOficina extends SunarpBean{


	private static DatosOficina single = null;
	private static java.util.Map oficinas = null;
	
	private DatosOficina(){
		oficinas = java.util.Collections.synchronizedMap(new java.util.HashMap());
	}
	
	public static synchronized DatosOficina getInstance(){
		if(single == null)
			single = new DatosOficina();
		return single;
	}
	
	/**
	 * Gets the oficinas
	 * @return Returns a java.util.Map
	 */
	public static java.util.Map getOficinas() {
		return oficinas;
	}
}

