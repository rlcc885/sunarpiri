/**
 * 
 */
package gob.pe.sunarp.extranet.publicidad.service;

import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;

/**
 * @author dbravo
 *
 */
public interface AccesoService 
{	
	public UsuarioBean validarIngreso(String usuario, String clave, String ipRemota, String session_id) throws CustomException, DBException, Throwable;
}
