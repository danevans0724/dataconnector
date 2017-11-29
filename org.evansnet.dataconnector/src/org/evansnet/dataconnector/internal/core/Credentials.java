package org.evansnet.dataconnector.internal.core;

public class Credentials {
	private String userID;
	private String password;
	private String key;
	
	public Credentials (String uid, String pwd) {
		userID = new String(uid);
		password = new String(disguisePWD(pwd, key));
	}

	private String disguisePWD(String pwd, String key) {
		// TODO Modify disguisePWD() method to encrypt password.
		//return this.password;
		return pwd;
	}
	
	private String unDisguisePWD(String p, String key) {
		// TODO modify unDisguisePWD() method to unencrypt the password.
		return this.password;
	}
	
	public void setUserID(String u) {
		userID = u;
	}
	
	public void setPassword(String p) {
		password = disguisePWD(p,this.key);
	}
	
	public void setKey(String k) {
		key = k;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public String getPassword() {
		String pwd = unDisguisePWD(password, key);
		return pwd; 
	} 
}
