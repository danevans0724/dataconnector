package org.evansnet.dataconnector.internal.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

//import javax.crypto.Cipher;

import org.evansnet.dataconnector.internal.core.Credentials;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CredentialsTest {
	
	private Path currentPath = Paths.get("");
	private String testPathString = currentPath.toAbsolutePath().toString();
	private final String pubCertFile = testPathString + System.getProperty("file.separator") 
		+  "security" + System.getProperty("file.separator") + "credentials.cer";
	Credentials credential;
	Certificate certificate;
	char[] mockPwd = {'H','e','l','l','o'};
	byte[] safeBytes = {117, -78, 5, -105, 22, -55, -108, 31, -122, 38, -117, 77, 74, -17, 126, 117, -50, -104, 53, 5, -76, -64, 57, -24, 53, -104, 114, 38, -73, 97, 119, 102, -36, 7, -92, 107, 68, -30, -7, 74, -5, 66, -76, -94, -61, -11, 69, 82, -47, 111, -45, -99, 13, -126, 1, -103, 88, 19, -7, -22, 38, 43, -86, 25, 106, 24, 52, 81, -64, 16, 59, 30, 82, 108, -108, 18, 120, -46, -127, 114, 60, 74, -66, -40, 93, -35, 57, 116, -125, 90, 52, -14, -47, -61, 30, 9, 27, -121, -52, 90, 27, -101, 115, -53, 12, -12, 27, -92, -113, 90, 47, 83, -58, -61, -45, 39, 127, 6, 112, -28, -28, 48, -73, 117, 20, 64, 121, 73, -110, -102, -74, -61, -59, 25, -62, -70, 31, -17, -28, -72, -81, 72, -106, -52, 51, 108, 91, 102, 67, 118, 101, -116, -44, 81, -71, -79, -123, 96, -53, 124, 63, 99, 102, 104, 49, -83, -16, 57, -82, 15, 71, -22, 96, 17, -69, -23, 8, -34, 32, 26, 68, 24, 86, -101, 37, -15, -45, -33, -127, 52, 22, -11, -82, -25, 45, 79, 118, 99, 89, -67, 92, 4, -62, 29, -109, -97, 20, -15, 33, 78, 120, 81, 20, 80, -98, -49, -126, -20, 97, 79, -62, 82, -127, -18, -15, 118, 73, 67, -8, -12, -65, 28, 93, -57, -119, -86, -43, -55, -75, 65, -74, 62, -57, 46, -37, -38, 48, 100, 65, 58, 70, 124, -84, -82, 111, -22};
	char[] safeMockPwd;
	char[] disguised;
			
	@Before
	public void setUp() throws Exception {
		try {
			readCertFromFile();

			// Get the decrypted version of the password for comparison.
			safeMockPwd = new char[safeBytes.length];
			for (int count = 0; count < safeBytes.length; ++count) {
				safeMockPwd[count] = (char)safeBytes[count];
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception was thrown during tests");
		}
	}

	@After
	public void tearDown() throws Exception {
		credential = null;
		certificate = null;
	}

//	@Test
//	public void testFetchKey() throws Exception {
//		setUp();
//		PublicKey sourceKey = certificate.getPublicKey();
//		credential = new Credentials(certificate);
//		PublicKey testKey = credential.fetchKeyTest(certificate);
//		boolean testResult = testKey.equals(sourceKey);
//		if (testResult) {
//			System.out.println("Public keys match!");
//			tearDown();
//			return;
//		}
//		fail("Failed key check. Method returned false.");
//	}
//	
//	@Test
//	public void testDisguise() {
//		try {
//			Credentials cred = new Credentials(certificate);
//			cred.setPassword(mockPwd);
//			disguised = cred.getPassword(certificate);
//			if (disguised != safeMockPwd) {
//				System.out.println("Yay! it works");
//				return;
//			}
//			fail("Credential was not disguised.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Exception thrown");
//		}
//	}
//	
//	@Test public void testUnDisguise() throws Exception {
//		Credentials cred = new Credentials(certificate);
//		char[] undisguised = cred.testUnDisguise(safeMockPwd, certificate);
//		if(Arrays.equals(mockPwd,undisguised)) {
//			System.out.println("Password decrypted successfully!");
//			return;
//		}
//		fail("The unencrypted password does not match the original");
//	}
	

//	private void makeSafeStorePwd() throws Exception {
//		final String propFile = "C:\\Users\\pmidce0\\git\\dataconnector\\org.evansnet.dataconnector\\security\\security.properties";
//		FileInputStream fis = new FileInputStream(propFile);
//		Properties prop = new Properties();
//		prop.load(fis);
//		setUp();
//		
//		//Now use the certificate to encrypt the store password and put it back into the security.properties file.
//		PublicKey key = certificate.getPublicKey();
//		char[] pwd = {'B','B','v','1','0','l','e','t'};
//		byte[] toBytes = new byte[pwd.length];
//		for (int i = 0; i < pwd.length; i++) {
//			toBytes[i] = (byte)pwd[i];
//		}
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.ENCRYPT_MODE, key);
//		byte[] encrypted = cipher.doFinal(toBytes);
//		char[] safe = new char[encrypted.length];
//		for (int i = 0; i < encrypted.length; i++) {
//			safe[i] = (char)encrypted[i];
//		}
//		prop.setProperty("password", new String(safe));
//		
//		//Finally, write the properties to the security.properties file
//		FileOutputStream fos = new FileOutputStream(propFile);
//		prop.store(fos, "No comment");
//	}
	
	@Test
	public void readCertFromFile() {
		// Get the certificate with the public key from the security folder.
		try {
			FileInputStream fis = new FileInputStream(pubCertFile);
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			Certificate pubCert = factory.generateCertificate(fis);
			certificate = pubCert;
		} catch (Exception e) {
			System.out.println("Failed to read certificate from file. Error; " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
//	public static void main(String[] args) {
//		CredentialsTest ct = new CredentialsTest();
//		try {
//			System.out.println("Encrypting and storing keystore password in security.properties");
//			ct.makeSafeStorePwd();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
