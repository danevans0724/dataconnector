package org.evansnet.dataconnector.internal.core;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.crypto.Cipher;

/*
 * Algorithm:
 * Client side (not here).
 * 	Instantiates one of these objects
 * 	Passes a public key certificate for this class' from a client side keystore. 
 *    (The public key certificate was exported from dataconnector's keystore)
 * 	
 * This class:
 *  Securely unencrypt (how?) the keystore password and file name in the 
 *  security.properties file.
 *  Fetch the public key certificate from the dataconnector keystore; (fetchCert)
 *  Verify that the certificate passed from the client matches the dataconnector 
 *  public key; (isKeyEqual()).
 *  If trusted, 
 *  	Extract the private key from dataconnector's keystore and return it as a char[].
 * 		Get the private key.
 * 		Return the key as a char[]
 *  else
 *  	throw security exception and null out the values in the class.
 *  	return null
 */


public final class Credentials {
//	private final String SECURITY_PROPERTIES = "C:\\Users\\pmidce0\\git\\dataconnector\\org.evansnet.dataconnector\\security\\security.properties";
	private final String STORE = "C:\\Users\\pmidce0\\git\\dataconnector\\org.evansnet.dataconnector\\security\\credstore.keystore";
	private Certificate pub;
	private char[] uid;		//disguised only!
	private char[] pwd;		//disguised only!
	
	public Credentials() {
		pub = fetchCert();
	}
	
	public Credentials (Certificate certificate) {
		this();
	}

	// Encrypt with the public key. 
	private final char[] disguise(char[] pwd, Certificate c) throws Exception {
		byte[] bPwd = toBytes(pwd);
		PublicKey key = fetchKey(c);
		if (isKeyEqual(key)) {
			//Keys match so safe to encrypt here.
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encrypted = cipher.doFinal(bPwd);
			char[] safePwd = new char[encrypted.length];
			safePwd = toChar(encrypted);
			return safePwd;
		}
		pwd = null;		//Destroy the clear text password.
		return pwd;
	}
	
	private final byte[] toBytes(char[] c) {
		byte[] bPwd = new byte[c.length];
		for(int i = 0; i < c.length; i++) {
			bPwd[i] = (byte) c[i];
		}
		return bPwd;
	}
	
	private final char[] toChar(byte[] b) {
		char[] theChars = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			theChars[i] = (char)b[i];
		}
		return theChars;
	}
	
	private final PrivateKey fetchPrivate(KeyStore keystore) {
		try {
			KeyStore.ProtectionParameter prot = new KeyStore.PasswordProtection("BBv10let".toCharArray());
			KeyStore.PrivateKeyEntry privEntry = (KeyStore.PrivateKeyEntry)keystore.getEntry("credentials", prot);
			return privEntry.getPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private final char[] unDisguise(char[] p, Certificate c) throws Exception {
		byte[] bPwd = toBytes(p);
		PublicKey key = fetchKey(c);
		KeyStore keystore = getKeystore();
		if (isKeyEqual(key)) {
			//Keys match so safe to encrypt here.
			PrivateKey privKey = fetchPrivate(keystore);		//Fetch the private key from the keystore
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] decrypted = cipher.doFinal(bPwd);
			char[] thePwd = new char[decrypted.length];
			thePwd = toChar(decrypted);
			return thePwd;
		}
		pwd = null;
		return pwd;
	}
	
	private final KeyStore getKeystore() throws Exception {
		KeyStore keystore = KeyStore.getInstance("JKS");
		keystore.load(new FileInputStream(STORE), fetchStorePw());  
		return keystore;
	}
	
	//No javadoc because we don't want to expose the method that gets the key from the keystore
	//TODO: refactor this into the security plugin.
	private final Certificate fetchCert() {
		String pubCertFile = "C:\\Users\\pmidce0\\git\\dataconnector\\org.evansnet.dataconnector\\security\\credentials.cer";

		try {
			FileInputStream fis = new FileInputStream(pubCertFile);
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			Certificate pubCert = factory.generateCertificate(fis);
			pubCertFile = null;
			return pubCert;
		} catch (Exception e) {
			System.out.println("Failed to read certificate from file. Error; " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	// Test the public key passed for equality with the dataconnector's key.
	// If they are the same, we can trust the sender as another piece of our code.
	private final boolean isKeyEqual(Key keyTest ) throws Exception {
		PublicKey trustedKey = pub.getPublicKey();
		if (keyTest.equals(trustedKey) && Arrays.equals(keyTest.getEncoded(), trustedKey.getEncoded())) {
			return true;
		} 
		return false;
	}
	
	private final PublicKey fetchKey(Certificate cert) {
		if (pub == null) {
			fetchCert();
		}
		return pub.getPublicKey();
	}
	
	//Get the keystore pwd and unencrypt the password.
	private final char[] fetchStorePw() throws IOException {
		//TODO: Secure the keystore password. 
		final char[] pwd = {'B','B','v','1','0','l','e','t'};
		return pwd;
	}
	
	//For test only!
	public PublicKey fetchKeyTest(Certificate cert) throws Exception {
		fetchStorePw();
		pub = fetchCert();
		PublicKey key = pub.getPublicKey();
		return key;
	}

	public void setUserID(char[] txtUserID) throws Exception {
		uid = txtUserID;
		txtUserID = null;
	}

	public void setPassword(char[] txtPassword) throws Exception {
		pwd = disguise(txtPassword, pub);
		txtPassword = null;
	}
	
	public char[] testUnDisguise(char[] p, Certificate c) throws Exception {
		pub = c;
		return unDisguise(p, pub);
	}

	public char[] getUserID() {
		return uid;
	}
	
	public char[] getPassword(Certificate c) throws Exception {
		return unDisguise(pwd, c);			
	}
}
