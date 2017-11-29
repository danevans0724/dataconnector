package org.evansnet.dataconnector.internal.dbms;

import java.util.Properties;
import java.util.Set;

import org.evansnet.dataconnector.internal.core.DBType;
import org.evansnet.dataconnector.internal.core.IDatabase;
import org.evansnet.dataconnector.internal.core.IHost;

public class ConnectionStrFactory {
	
	DBType dbtype;
	IHost  host;
	IDatabase db;
	String connString;
	Properties parmList;
	
//	public String ConnectionStringFactory() {return null;}
	
	public ConnectionStrFactory(IHost h, IDatabase d) {
		dbtype = d.getDBMS();
		host   = h;
		db = d;
		buildConnString();
	}
	
	/**
	 * Creates the connection string given the host information and 
	 * the DBMS type.
	 * 
	 * @return String 
	 */

	private void buildConnString() {
		
		switch (dbtype) {
		case DB2: 
			buildDB2();
			break;
		case Derby:
			buildDerby();
			break;
		case MS_SQLSrv:
			buildMSSQL_Server();
			break;
		case MySQL:
			buildMySQL();
			break;
		case Oracle:
			buildOracle();
			break;
		case SQL_Lite:
			buildSQLLite();
			break;
		case Sybase:
			buildSybase();
			break;
		default:
			connString = null;
		}
	}

	private void buildSybase() {
		// jdbc:jtds:sybase://<host>:port/<database>/
		//TODO: Finish the connection string builder for Sybase.
		connString = null;
	}

	/**
	 * jdbc:sqlite:sqlite_database_file_path
	 * Example: jdbc:sqlite:C:/sqlite/db/chinook.db
	 */
	private void buildSQLLite() {
		// TODO Finish the connection string builder for SQL Lite
		connString = null;
	}
	
	/**
	 * SID format: jdbc:oracle:thin:@<HOST>:<PORT>:<SID>
	 * Example: jdbc:oracle:thin:@localhost:1521:orcl11
	 * Service name format: jdbc:oracle:thin:@//<<HOST>>:<<PORT>>/<<SERVICE NAME>>
	 * 
	 * To include credentials in the jdbc string:
	 * jdbc:oracle:thin:<<USER ID>>/<<PWD>>@...
	 * 
	 * For Oracle pluggable databases, the format is the same as for SID, but it requires,
	 * the service name instead of the SID.
	 * Example: jdbc:oracle:thin:@DCEOrclSrv1.nasa.cpwr.corp:1521/dceoraplug1.nasa.cpwr.corp
	 */
	private void buildOracle() {
		// TODO Finish the connection string builder for Oracle. Remember SID or Service name.
		connString = null;
	}

	/**
	 * MySQL connection string factory.
	 * MySQL connection string format: 
	 * jdbc:mysql://<<Host>>:3306/<<database>>
	 * Example: jdbc:mysql://10.100.12.64:3306/DCEDB01
	 */
	private void buildMySQL() {
		//TODO: Modify this to handle various parameters such as instance name etc.
		setUIDPWD();
		StringBuilder s = new StringBuilder();
		s.append("jdbc:mysql://");
		s.append(host.getHostName() + ":" + host.getPort() + "/");
		s.append(db.getDatabaseName());
		
		if (!parmList.isEmpty()) {
			Set<Object> keyset = parmList.keySet();
			s.append("?");
			int count = 0;
			for (Object key : keyset) {
				if (((String) key).isEmpty())
					continue;
				else if(count > 0) {
					s.append("&");
				}
				String k = (String)key;
				s.append( k + "=" + parmList.getProperty(k).toString());
				++count;
			}
		}
		connString = s.toString();
	}

	private void buildMSSQL_Server() {
		//TODO: Modify this to handle various parameters such as instance name etc.
		setUIDPWD();
		StringBuilder s = new StringBuilder();
		s.append("jdbc:sqlserver://");
		s.append(host.getHostName() + ":" + host.getPort() + ";");
		s.append("database=" + db.getDatabaseName());
		
		if (!parmList.isEmpty()) {
			Set<Object> keyset = parmList.keySet();
			for (Object key : keyset) {
				if (((String) key).isEmpty() )
					continue;
				String k = (String)key;
				s.append(";" + k + "=" + parmList.getProperty(k).toString());
			}
		}
		connString = s.toString();
	}

	/**
	 * Embedded:
	 * jdbc:derby:<<Path to datbase folder>>
	 * jdbc:derby:C:\SomeFolder\DatabaseName
	 * 
	 * Client/Server model:
	 * jdbc:derby://<<server>>[:<<port>>]/<<databaseName>>[;<URL attribute>=<value>]
	 */
	private void buildDerby() {
		// TODO Finish the connection string builder for Derby.
		setUIDPWD();
		
	}

	/**
	 * jdbc:db2://<<HOST>>:<<PORT>>/<<DATABASE>>
	 * Ex: jdbc:db2://DCEDB2Srv1:50000/DCEDB2
	 */
	private void buildDB2() {
		// TODO Finish the connection string builder for DB2.
		setUIDPWD();
		
	}

	/**
	 * Puts the user name and password into the Properties object, parmList.
	 * These are the first two items in the properties object and are used
	 * to set the user ID and password in the connection string
	 * 
	 */
	private void setUIDPWD() {
		//TODO: if no credentials then pop a dialog and get them.
		
		parmList = new Properties();		
		parmList.put("user", db.getCredentials().getUserID());
		parmList.put("password", db.getCredentials().getPassword());
	}
	
	/**
	 * @return the dbtype
	 */
	public DBType getDbtype() {
		return dbtype;
	}

	/**
	 * @param dbtype the dbtype to set
	 */
	public void setDbtype(DBType dbtype) {
		this.dbtype = dbtype;
	}


	/**
	 * @return the connString
	 */
	public String getConnString() {
		if (connString.isEmpty() || connString == null) {
			buildConnString();
		}
		return connString;
	}
	

}
