package org.evansnet.dataconnector.internal.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.evansnet.dataconnector.internal.core.DBMS;
import org.evansnet.dataconnector.internal.core.DBType;
import org.evansnet.dataconnector.internal.core.IHost;

	/** 
	 * Represents a Microsoft SQL Server database server.
	 * @author Dan Evans (c) 2020
	 *
	 */
	public class SQLSrvConnection extends DBMS {
		
		Logger javaLogger = Logger.getLogger("SQL Server Host logger");
		
		String connStr;
		Connection conn;

		public SQLSrvConnection() throws ClassNotFoundException, SQLException {
			super();
			getHost().setPort(1433);	// MS SQL Server default port.
			connStr = "";
			setDBMS(DBType.MS_SQLSrv);
		}
		
		public SQLSrvConnection(IHost h) throws ClassNotFoundException, SQLException {
			this();
			super.setHost(h);
		}
			
		/**
		 * Connection string for Microsoft SQL Server JDBC driver:
		 *          "jdbc:sqlserver://yourserver.database.windows.net:1433;"  
                    + "database=AdventureWorks;"  
                    + "user=yourusername@yourserver;"  
                    + "password=yourpassword;"  
                    + "encrypt=true;"  
                    + "trustServerCertificate=false;"  
                    + "hostNameInCertificate=*.database.windows.net;"  
                    + "loginTimeout=30;";  
		 * @throws Exception 
		 */
		@Override
		public String buildConnectionString(DBType dbt) throws Exception {
			javaLogger.log(Level.INFO, "Building SQL Server Connection String.");
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
			} catch (SQLTimeoutException e) {
				throw new SQLException("/nThe connection attempt timed out.");
			} catch (SQLException e) {
				javaLogger.log(Level.INFO, "Failed to connect to " + getDatabaseName());
					throw new SQLException("/nThe connection string is null or empty.");
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
			if (connStr.isEmpty()) {
				try {
					connStr = buildConnectionString(DBType.MS_SQLSrv);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return connStr;
		}
		
		@Override
		public void setConnectionString(String s) {
			connStr = s;
		}

}
