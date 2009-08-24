/*
 * Created on 15-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author jbugarin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SecuencialesCajas extends SunarpBean{

	private static SecuencialesCajas secuenciales;
	public static SecuencialesCajas getInstance() {
		if (secuenciales == null) {
			secuenciales = new SecuencialesCajas();
		}
		return secuenciales;
	}
	private SecuencialesCajas() {
		super();
	}

	private String idDiarCajaSequence = "select ID_DIAR_SEQ.nextval from dual";
	
	public Long getIdDiarCaja(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(idDiarCajaSequence);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return new Long(resultado);
		} finally {
			stmt.close();
		}
	}


}
