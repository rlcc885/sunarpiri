package gob.pe.sunarp.extranet.pool;

import java.sql.*;
import gob.pe.sunarp.extranet.framework.Loggy;

public class DBConnectionFactoryV extends ObjectPool {

	private String driver;
	private String url;
	private String user;
	private String pass;

	private static DBConnectionFactoryV dbConnectionFactoryV;

	public static DBConnectionFactoryV getInstance() {
		if (dbConnectionFactoryV == null) {
			dbConnectionFactoryV = new DBConnectionFactoryV();
		}
		return dbConnectionFactoryV;
	}

	private DBConnectionFactoryV() {
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
			if (Loggy.isTrace(this)) System.out.println("DBConnectionFactory.expire: " + e.toString());
		}
	}

	protected boolean validate(Object o) {
		try {
			return !((Connection)o).isClosed();
		} catch (SQLException e) {
			if (Loggy.isTrace(this)) System.out.println("DBConnectionFactory.validate: " + e.toString());
			return false;
		}
	}

	protected Object create() throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, pass);
		return conn;
	}

	public Connection getConnection() throws Exception {
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
			if (connection instanceof oracle.jdbc.driver.OracleConnection) {
				//SAUL
				//((oracle.jdbc.driver.OracleConnection)connection).close_statements();
				((oracle.jdbc.driver.OracleConnection)connection).close();
			} else {
				throw new IllegalStateException("Este pool de DB solo funciona con OracleConnection y no con " + connection.getClass().getName());
			}
		} catch (SQLException e) {
			if (Loggy.isTrace(this)) System.out.println("SQLException DBConnectionFactory.release: " + e.toString());
		}
		checkIn(connection);
	}

}
