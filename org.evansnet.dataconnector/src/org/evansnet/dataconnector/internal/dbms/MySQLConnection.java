package org.evansnet.dataconnector.internal.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.evansnet.dataconnector.internal.core.DBMS;
import org.evansnet.dataconnector.internal.core.DBType;
import org.evansnet.dataconnector.internal.core.IHost;

public class MySQLConnection extends DBMS {

	Logger javaLogger = Logger.getLogger("SQL Server Host logger");
	
	String connStr;
	Connection conn;
	Properties parmList;

	public MySQLConnection() throws ClassNotFoundException, SQLException {
		super();
		getHost().setPort(3306);
		connStr = "";
		parmList = new Properties();
		setDBMS(DBType.MySQL);
	}
	
	public MySQLConnection(IHost h) throws ClassNotFoundException, SQLException {
		this();
		super.setHost(h);
	}
	@Override
	public String buildConnectionString(DBType dbt) throws Exception {
		javaLogger.log(Level.INFO, "Building MySQL Connection String.");
		ConnectionStrFactory csf = new ConnectionStrFactory(getHost(), this);
		return csf.getConnString();
	}

	@Override
	public Connection connect(String connStr) throws SQLException {
		try {
			Connection c = DriverManager.getConnection(connStr);
			setConnection(c);
			javaLogger.log(Level.INFO,"Successful connection to " + getDatabaseName());
		return c;
		} catch (Exception e) {
			javaLogger.log(Level.INFO, "Failed to connect to " + getDatabaseName() + "\n" +
					"Cause: " + e.getCause());
			return null;
		}		
	}
	
	@Override
	public Connection getConnection() {
		return conn;
	}
	
	protected void setConnection(Connection c) {
		conn = c;
	}
	
	@Override
	public String getConnectionString() {
		return connStr;
	}

	@Override
	public Object addParms(String p, char[] v) {
		return parmList.put(p, v);
	}
}
