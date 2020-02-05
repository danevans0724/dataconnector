package org.evansnet.dataconnector.internal.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public interface IDatabase {
	
	public String buildConnectionString(DBType dbType) throws Exception;
	public Connection connect(String connStr) throws SQLException;
	
	public String getInstanceName();
	public void   setInstanceName(String i);
	public void   setDatabaseName(String d);
	public String getDatabaseName();
	public DBType getDBMS();
	public Object addParms(String p, char[] v);
	public Properties getParameters();
	public Credentials getCredentials();
	public void setCredentials(Credentials c);
	public String getSchema();
	public Connection getConnection();
	public String getConnectionString() throws SQLException;
	public void setConnectionString(String s);
	public IHost getHost();
	public void setSchema(String string);
}
