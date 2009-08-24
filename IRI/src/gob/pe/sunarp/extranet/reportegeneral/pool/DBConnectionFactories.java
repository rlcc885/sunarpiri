package gob.pe.sunarp.extranet.reportegeneral.pool;
import gob.pe.sunarp.extranet.reportegeneral.bean.ConsolaCentral;
import gob.pe.sunarp.extranet.reportegeneral.bean.OficinaConection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import oracle.jdbc.pool.OracleConnectionCacheImpl;
import gob.pe.sunarp.extranet.framework.Loggy;

public class DBConnectionFactories {
	private static HashMap singles = new HashMap();
	private OracleConnectionCacheImpl connCache = null;

	public static DBConnectionFactories getInstance(String codOficina) {
		return (DBConnectionFactories) singles.get(codOficina);
	}

	private DBConnectionFactories() {
	}

	public void close() {
		try {
			connCache.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init(int poolSize) throws Exception {
		Iterator ite = ConsolaCentral.getInstance().getDbOficinas().keySet().iterator();
		while(ite.hasNext()){
			String codOficina = ite.next().toString();
			OficinaConection ofic = (OficinaConection) ConsolaCentral.getInstance().getDbOficinas().get(codOficina);
			DBConnectionFactories single = new DBConnectionFactories();
			OracleConnectionCacheImpl connCache = new OracleConnectionCacheImpl();
			connCache.setURL(ofic.getUrl());
			connCache.setUser(ofic.getUser());
			connCache.setPassword(ofic.getPassword());
			connCache.setMaxLimit(poolSize);
			connCache.setMinLimit(0);
			single.connCache = connCache;
			singles.put(codOficina, single);
		}
	}

	public Connection getConnection() throws SQLException {
		while (true) {
			try {
				Connection conn = connCache.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				return conn;
			} catch (java.util.EmptyStackException e) {
				if (Loggy.isTrace(this)) System.out.println("Conexiones a base de datos agotadas. Esperando nueva: " + e.toString());
				try {
					Thread.sleep(250);
				} catch (InterruptedException e2) {
				}
			}
		}
	}

	public synchronized void release(Connection connection) {
		if (connection == null)
			return;

		try {
			connection.rollback();
		} catch (SQLException e) {
		}

		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
}
