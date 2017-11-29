package org.evansnet.dataconnector.internal.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;

import org.evansnet.dataconnector.internal.core.Credentials;
import org.evansnet.dataconnector.internal.core.DBMS;
import org.evansnet.dataconnector.internal.dbms.SQLSrvConnection;

public class SQLConnectorTest {
	
	DBMS dbInst;
	Credentials credentials;
	
	public void setupForTests() throws ClassNotFoundException, SQLException {
		dbInst = new SQLSrvConnection();
		dbInst.getHost().setHostName("localhost");
		dbInst.getHost().setPort(1433);
		credentials = new Credentials("devans", "3xnhlcup");
		dbInst.setInstanceName(dbInst.getHost().getHostName());
		dbInst.setDatabaseName("DCEDB01");
//		dbInst.getHost().setIPaddress("127.0.0.1");
		dbInst.setCredentials(credentials);
		dbInst.addParms("user", credentials.getUserID());
		dbInst.addParms("password", credentials.getPassword());
	}
	

	@Test
	public void testGetSQLConnection() throws SQLException {
		try {
			setupForTests();
			String connString = dbInst.buildConnectionString(dbInst.getDBMS());
			Connection conn = dbInst.connect(connString);
			assertTrue(!conn.isClosed());
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Connection to database failed.");
		}
	}

}
