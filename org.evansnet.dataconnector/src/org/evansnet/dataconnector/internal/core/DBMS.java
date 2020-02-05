package org.evansnet.dataconnector.internal.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBMS implements IDatabase {
	
	IHost host;
	DBType dbType;
	String instance;	// The database instance name. 
	String dbName;
	String schema;
	Credentials dbCredentials;
	Properties parmList;

	
	public DBMS() {
		host = new Host();
		instance = "";
		dbName = "";
		schema = "";
		parmList = new Properties();
	}
	
	public DBMS(IHost h) {
		this();
		host = h;
	}
	
	public IHost getHost() {
		return host;
	}
	
	public void setHost(IHost h) {
		host = h;
	}
	
	public DBType getDBMS() {
		return dbType;
	}
	
	public void setDBMS(DBType d) {
		dbType = d;
	}

	public Connection connect(String connStr) throws SQLException{
		return null;
	}
	
	public String getInstanceName(){
		return instance;
	}
	
	public void   setInstanceName(String i){
		instance = i;
	}
	
	public void   setDatabaseName(String d){
		dbName = d;
	}
	
	public String getDatabaseName(){
		return dbName;
	}
	
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Credentials getCredentials() {
		return dbCredentials;
	}

	@Override
	public void setCredentials(Credentials dbCredentials) {
		this.dbCredentials = dbCredentials;
	}

	@Override
	public String buildConnectionString(DBType dbType) throws Exception {
		return null;
	}

	@Override
	public Object addParms(String p, char[] v) {
		return parmList.put(p, v);
	}

	@Override
	public Connection getConnection() {
		return null;
	}

	@Override
	public String getConnectionString() throws SQLException {
		return null;
	}

	@Override
	public void setConnectionString(String s) {
		//See concrete definition of this method in the inherited class(es).
	}

	@Override
	public Properties getParameters() {
		return parmList;
	}
}
