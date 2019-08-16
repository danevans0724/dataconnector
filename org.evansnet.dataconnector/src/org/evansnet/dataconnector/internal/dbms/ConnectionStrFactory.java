package org.evansnet.dataconnector.internal.dbms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.sql.SQLException;
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
	private Properties parmList;
	
//	public String ConnectionStringFactory() {return null;}
	
	public ConnectionStrFactory(IHost h, IDatabase d) throws SQLException {
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
	 * @throws Exception 
	 */

	private void buildConnString() throws SQLException {
		
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
	private void buildMySQL() throws SQLException {
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

	private void buildMSSQL_Server() throws SQLException {
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
	 * @throws Exception 
	 */
	private void buildDerby() throws SQLException {
		// TODO Finish the connection string builder for Derby.
		setUIDPWD();
		
	}

	/**
	 * jdbc:db2://<<HOST>>:<<PORT>>/<<DATABASE>>
	 * Ex: jdbc:db2://DCEDB2Srv1:50000/DCEDB2
	 * @throws Exception 
	 */
	private void buildDB2() throws SQLException {
		// TODO Finish the connection string builder for DB2.
		setUIDPWD();
		
	}

	/**
	 * Puts the user name and password into the Properties object, parmList.
	 * These are the first two items in the properties object and are used
	 * to set the user ID and password in the connection string
	 * @throws Exception 
	 * 
	 */
	private void setUIDPWD() throws SQLException {		
		try {
			parmList = new Properties();		
			parmList.put("user", new String(db.getCredentials().getUserID()));
			parmList.put("password", new String(db.getCredentials().getPassword(fetchCert())));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	//TODO: Refactor and generalize this class into a common plugin.
	private Certificate fetchCert() {
		String certFile = "C:\\Users\\pmidce0\\git\\dataconnector\\org.evansnet.dataconnector\\security\\credentials.cer";
		try {
			FileInputStream fis = new FileInputStream(certFile);
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			Certificate cert = factory.generateCertificate(fis);
			return cert;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (CertificateException ce) {
			ce.printStackTrace();
		}
		return null;
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
	public String getConnString() throws SQLException {
		if (connString.isEmpty() || connString == null) {
			buildConnString();
		}
		return connString;
	}
	

}
