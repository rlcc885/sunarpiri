package gob.pe.sunarp.extranet.publicidad.sql.impl;

import gob.pe.sunarp.extranet.framework.Loggy;

public class SQLImpl {

	public static final int MEDIO_WEB_SERVICE = 1;
	public static final int MEDIO_CONTROLLER = 0;
	
	public boolean isTrace(Object _this) {
		return Loggy.isTrace(_this);
	}
	
}
