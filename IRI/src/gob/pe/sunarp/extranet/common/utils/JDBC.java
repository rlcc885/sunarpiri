package gob.pe.sunarp.extranet.common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
	private static JDBC single;
	public synchronized static JDBC getInstance() {
		if (single == null) {
			single = new JDBC();
		}
		return single;
	}
	private JDBC() {
	}
	
	public void closeStatement(Statement stmt){
		if(stmt != null)
			try{stmt.close();
			}catch(SQLException sqle){}
	}

	public void closeResultSet(ResultSet rs){
		if(rs != null)
			try{rs.close();
			}catch(SQLException sqle){}
	}
}

