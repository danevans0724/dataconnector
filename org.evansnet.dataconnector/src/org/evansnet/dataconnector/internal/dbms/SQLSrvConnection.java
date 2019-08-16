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

	/** 
	 * Represents a Microsoft SQL Server database server.
	 * @author pmidce0
	 *
	 */
	public class SQLSrvConnection extends DBMS {
		
		Logger javaLogger = Logger.getLogger("SQL Server Host logger");
		
		String connStr;
		Connection conn;
		Properties parmList;

		public SQLSrvConnection() throws ClassNotFoundException, SQLException {
			super();
			getHost().setPort(1433);
			connStr = "";
			parmList = new Properties();
			setDBMS(DBType.MS_SQLSrv);
		}
		
		public SQLSrvConnection(IHost h) throws ClassNotFoundException, SQLException {
			this();
			super.setHost(h);
		}
			
		/**
		 * Connection string for SQL Server JDBC driver:
		 *          "jdbc:sqlserver://yourserver.database.windows.net:1433;"  
                    + "database=AdventureWorks;"  
                    + "user=yourusername@yourserver;"  
                    + "password=yourpassword;"  
                    + "encrypt=true;"  
                    + "trustServerCertificate=false;"  
                    + "hostNameInCertificate=*.database.windows.net;"  
                    + "loginTimeout=30;";  
		 */
		@Override
		public String buildConnectionString(DBType dbt) throws SQLException {
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
			} catch (Exception e) {
				javaLogger.log(Level.INFO, "Failed to connect to " + getDatabaseName());
				e.printStackTrace();
				return null;
			}		
		}
		
		public Connection getConnection() {
			return conn;
		}
		
		protected void setConnection(Connection c) {
			conn = c;
		}
		
		public String getConnectionString() throws SQLException {
			if (connStr.isEmpty()) {
				connStr = buildConnectionString(DBType.MS_SQLSrv);
			}
			return connStr;
		}

		@Override
		public Object addParms(String p, char[] v) {
			return parmList.put(p, v);
		}

		@Override
		public String getDatabaseName() {
			return super.getDatabaseName();
		}
		
		@Override
		public void setConnectionString(String s) {
			connStr = s;
		}

}
