package gob.pe.sunarp.extranet.pool;

import java.sql.*;
import java.util.Enumeration;

import oracle.jdbc.driver.OracleConnection;
import gob.pe.sunarp.extranet.framework.Loggy;

public class DBConnectionFactory extends ObjectPool {

	private String driver;
	private String url;
	private String user;
	private String pass;

	private static DBConnectionFactory dbConnectionFactory;

	public static DBConnectionFactory getInstance() {
		if (dbConnectionFactory == null) {
			dbConnectionFactory = new DBConnectionFactory();
		}
		return dbConnectionFactory;
	}

	private DBConnectionFactory() {
	}

	public String getStatus() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("CONNECTION FACTORY:\r\n");
		buffer.append("\tDRIVER    -> ").append(driver).append("\r\n");
		buffer.append("\tURL       -> ").append(url).append("\r\n");
		buffer.append("\tUSER      -> ").append(user).append("\r\n");
		buffer.append("\tMAX SIZE  -> ").append(getMaxSize()).append("\r\n");
		buffer.append("\tSIZE      -> ").append(getSize()).append("\r\n");
		buffer.append("\tACT SIZE  -> ").append(getLockedSize()).append("\r\n");
		buffer.append("\tINAC SIZE -> ").append(getUnlockedSize()).append("\r\n");
		buffer.append("\tEXP TIME  -> ").append(getExpirationTime()).append("\r\n");
		buffer.append("\tTIME OUT  -> ").append(getTimeOut()).append("\r\n");
		buffer.append("\tTHREAD    -> ").append(this.currentThread).append("\r\n");
		buffer.append("\tTIMEOUTS  -> ").append(this.timeOutOcurred).append("\r\n");
		return buffer.toString();
	}

	public void init(String driver, String url, String user, String pass, int maxPoolSize, long expiryTime, long timeOut) 
	throws Exception {
		if ((driver == null) || (url == null) || (user == null) || (pass == null) || (maxPoolSize*expiryTime*timeOut == 0)) {
			throw new IllegalArgumentException("Ninguno de los parámetros puede ser null o 0");
		}
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pass = pass;
		
		setExpirationTime(expiryTime);
		setMaxSize(maxPoolSize);
		setTimeOut(timeOut);
		Class.forName(driver).newInstance();		
	}

	protected void expire(Object o) {
		try {
			((Connection)o).rollback();
			((Connection)o).close();
			o = null;
		} catch (SQLException e) {
			if (Loggy.isTrace(this)) 
				System.err.println("Error Expirando Objeto DB: DBConnectionFactory.expire: " + e.toString());
		}
	}

	protected boolean validate(Object o) {
		try {
			return !((Connection)o).isClosed();
		} catch (SQLException e) {
			if (Loggy.isTrace(this)) System.err.println("DBConnectionFactory.validate: " + e.toString());
			return false;
		}
	}

	protected Object create() throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, pass);
		return conn;
	}

	public Connection getConnection() throws Exception {
		checkStatusConecction();
		Connection conn = (Connection)checkOut();
		if (conn.getAutoCommit()) {
			conn.setAutoCommit(false);
		}
		if (conn.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED) {
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		}
		return conn;
	}

	public void release(Connection connection) {
		if (connection == null) return;
		try {
			connection.rollback();
			if (connection instanceof OracleConnection) {
				/**
				 * SAUL ACTUALIZA ESTE METODO DEL DRIVER DE ORACLE 10G
				 */
				//((oracle.jdbc.driver.OracleConnection)connection).close_statements();
				((OracleConnection)connection).close();
			} else {
				throw new IllegalStateException("Este pool de DB solo funciona con OracleConnection y no con " + connection.getClass().getName());
			}
		} catch (SQLException e) {
			if (Loggy.isTrace(this)) 
				System.err.println("SQLException DBConnectionFactory.release: " + e.toString());
		}
		checkIn(connection);
	}

	public static void main(String[] args) {
		
		try {
			//DBConnectionFactory.getInstance().init("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@172.18.1.45:1521:DBS05", "user1", "user12002", 100, 600000, 300000);
			DBConnectionFactory.getInstance().init("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@172.18.1.37:1521:DBSPDESA", "user1", "user1", 50, 60, 30);
			
			Connection[] conns = new Connection[100];
			
			for (int i = 0; i < conns.length; i++) {
				System.out.println("Contador de conexiones::"+i);
				Connection conn = DBConnectionFactory.getInstance().getConnection();
				DBConnectionFactory.getInstance().checkStatusConecction();
				for (int j = 0; j < i; j++) {
					if (conn == conns[j]) {
						System.out.println("CACHE!!!!!");
						//DBConnectionFactory.getInstance().getUnlockedSize();
					}
				}
				conns[i] = conn;
				//DBConnectionFactory.getInstance().release(conns[i]);				
			}
			System.out.println("Antes del close");
			DBConnectionFactory.getInstance().closePool();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
