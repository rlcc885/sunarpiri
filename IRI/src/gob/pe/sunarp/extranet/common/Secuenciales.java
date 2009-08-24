package gob.pe.sunarp.extranet.common;

import gob.pe.sunarp.extranet.pool.DBConnectionFactory;

import java.sql.*;

public class Secuenciales extends SunarpBean{
	private static Secuenciales secuenciales;
	public static Secuenciales getInstance() {
		if (secuenciales == null) {
			secuenciales = new Secuenciales();
		}
		return secuenciales;
	}
	private Secuenciales() {
		super();
	}

	private String asientoNextVal = "select IMG_ASIENTO_SEQ.nextval from dual";
	private String otroNextVal = "select IMG_OTRO_SEQ.nextval from dual";
	private String fichaNextVal = "select IMG_FICHA_SEQ.nextval from dual";
	private String tomoNextVal = "select IMG_TOMO_SEQ.nextval from dual";
	
	private String partidaNextVal = "select PARTIDA_SEQ.nextval from dual";
	private String tituloNextVal = "select TITULO_SEQ.nextval from dual";

	/**
	 * @SVASQUEZ - AVATAR GLOBAL
	 * @DESCRIPCION: SE AÑADE LA VARIABLE: transaccionNextVal, QUE ES EL SECUENCIADOR DE LA TABLA TRANSACCION
	 */
	private String transaccionNextVal = "select TRANSACCION_SEQ.nextval from dual";

	/**
	 * @SVASQUEZ - AVATAR GLOBAL
	 * @DESCRIPCION: SE AÑADE LA VARIABLE: movimientoNextVaQ, QUE ES EL SECUENCIADOR DE LA TABLA MOVIMIENTO
	 */
	private String movimientoNextVal = "select MOVIMIENTO_SEQ.nextval from dual";

	/**
	 * @SVASQUEZ - AVATAR GLOBAL
	 * @DESCRIPCION: SE AÑADE LA VARIABLE: aud_busq_partidaNextVal, QUE ES EL SECUENCIADOR DE LA TABLA AUD_BUSQ_PARTIDA
	 */
	private String aud_busq_partidaNextVal = "select AUD_BUSQ_PARTIDA_SEQ.nextval from dual";
	//	AUD_VISUALIZ_PARTIDA
	
	//Inicio mgarate:aumento de secuenciales para el problema de error generico de base de datos
	//TABLA AUD_VISUA_PARTIDA
	private String aud_visua_partidaNextVal = "select AUD_VISUA_PARTIDA_SEQ.nextval from dual"; 
	//TABLA CONSUMO
	private String consumoNextVal = "select CONSUMO_SEQ.nextval from dual";
    
	//Fin mgarate
	
	private String exEntidadDistSeq = "select EX_ENTIDAD_DIST_SEQ.nextval from dual";
	
	public long getPartidaRefnum(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(partidaNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}

	public long getTituloRefnum(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(tituloNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}

	public long getIDAsiento(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(asientoNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}

	public long getIDOtro(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(otroNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}

	public long getIDFicha(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(fichaNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}

	public long getIDFolio(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(tomoNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long getIDTransaccion(com.jcorporate.expresso.core.db.DBConnection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(transaccionNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long getIDMovimiento(com.jcorporate.expresso.core.db.DBConnection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(movimientoNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}	

	/**
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long getIDAud_Busq_Partida(com.jcorporate.expresso.core.db.DBConnection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(aud_busq_partidaNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}	
	public long getIDAud_Visua_Partida(com.jcorporate.expresso.core.db.DBConnection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(aud_visua_partidaNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}
	public long getIDConsumo(com.jcorporate.expresso.core.db.DBConnection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(consumoNextVal);
			rs.next();
			long resultado = rs.getLong(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}
	
	public int getExEntDistRefNum(com.jcorporate.expresso.core.db.DBConnection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(exEntidadDistSeq);
			rs.next();
			int resultado = rs.getInt(1);
			rs.close();
			return resultado;
		} finally {
			stmt.close();
		}
	}
}

