package org.evansnet.dataconnector.internal.core;


import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.evansnet.common.configuration.Global;
import org.evansnet.common.security.CommonSec;


public final class Credentials {
	public static Logger javaLogger = Logger.getLogger(Credentials.class.getName());
	private char[] uid;		//disguised only!
	private char[] pwd;		//disguised only!
	private CommonSec security;
	
	public Credentials() throws Exception {
		security = CommonSec.getInstance();
	}
	
	public Credentials (Certificate certificate) throws Exception {
		this();
	}

	public void setUserID(char[] txtUserID) throws Exception {
		uid = txtUserID;
		txtUserID = null;
	}

	public void setPassword(char[] txtPassword) throws Exception {
		pwd = security.disguise(txtPassword, fetchCert());
		txtPassword = null;
	}
	
	private Certificate fetchCert() {
		Global global = Global.getInstance();
		String certFile = global.getDataCert();
		try {
			FileInputStream fis = new FileInputStream(certFile);
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			Certificate pubCert = factory.generateCertificate(fis);
			certFile = null;
			return pubCert;
		} catch (Exception e) {
			javaLogger.log(Level.SEVERE, "Failed to read certificate from file. Error; " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public char[] getUserID() {
		return uid;
	}
	
	public char[] getPassword(Certificate c) throws Exception {
		return security.unDisguise(pwd, c);			
	}
}
