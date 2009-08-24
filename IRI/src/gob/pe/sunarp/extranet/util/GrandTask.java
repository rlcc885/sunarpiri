package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.util.*;
import gob.pe.sunarp.extranet.dbobj.*;

import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import java.util.*;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

public abstract class GrandTask
{
public String DBName="DEFAULT";
public DBConnection conn=null;

public int nKeys = 1;  //numero de campos llave
public String[] cabezas = null;
public String nombreTabla ="";
public String ruta ="";
public UsuarioBean usuario=null;

public abstract FormOutputListado getList(int pag) throws Throwable;
public abstract void read(GenericBean bean) throws Throwable;
public abstract void insert(GenericBean bean) throws Throwable;
public abstract void update(GenericBean bean) throws Throwable;
public abstract void delete(GenericBean bean) throws Throwable;

	/**
	 * Sets the dBName
	 * @param dBName The dBName to set
	 */
	public void setDBName(String dBName) {
		DBName = dBName;
	}

	/**
	 * Sets the conn
	 * @param conn The conn to set
	 */
	public void setConn(DBConnection conn) {
		this.conn = conn;
	}

}//fin clase

