package org.evansnet.dataconnector.internal.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;

import org.evansnet.dataconnector.internal.core.Credentials;
import org.evansnet.dataconnector.internal.core.DBMS;
import org.evansnet.dataconnector.internal.dbms.SQLSrvConnection;

public class SQLConnectorTest {
	
	DBMS dbInst;
	Credentials credentials;
	
	public void setupForTests() throws Exception {
		dbInst = new SQLSrvConnection();
		dbInst.getHost().setHostName("localhost");
		dbInst.getHost().setPort(1433);
		credentials = new Credentials();
		credentials.setUserID("Dan".toCharArray());
		credentials.setPassword("3xnhlcup".toCharArray());
		dbInst.setInstanceName(dbInst.getHost().getHostName());
		dbInst.setDatabaseName("DCEDB01");
		dbInst.setCredentials(credentials);
		dbInst.addParms("user", credentials.getUserID());
		dbInst.addParms("password", credentials.getPassword(fetchCertificate()));
	}
	
	
	private Certificate fetchCertificate() throws Exception {
		String certFile = "C:\\Users\\pmidce0\\git\\dataconnector\\org.evansnet.dataconnector\\security\\credentials.cer";
		FileInputStream fis = new FileInputStream (certFile);
		return CertificateFactory.getInstance("X.509").generateCertificate(fis);
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
