package org.evansnet.dataconnector.internal.core;

import java.util.Properties;


/**
 * Concrete base class for a connectable host machine. 
 *  
 * @author pmidce0
 *
 */
public class Host implements IHost {
	
	String hostName;
	String ipAddress;
	Properties hostProperties;
	int port;		// TODO: Modify to a port list. We can connect to more than one port on a host.
	Credentials credentials;
	String shareName;
	
	public Host() {
		hostName = new String("");
		ipAddress = new String("");
		port = -1;
		hostProperties = new Properties();
//		credentials = new Credentials("","");
		shareName = new String("");
	}
	

	@Override
	public String getHostName() {
		return hostName;
	}

	@Override
	public String getIPaddress() {
		return ipAddress;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getShareName() {
		return shareName;
	}

	@Override
	public void setHostName(String h) {
		hostName = h;
	}

	@Override
	public void setIPaddress(String ip) {
		// TODO: Check to make sure the ip address passed is valid.
		ipAddress = ip;
	}

	@Override
	public void setPort(int p) {
		port = p;
	}

	@Override
	public void setShareName(String s) {
		shareName = s;
	}

	@Override
	public boolean isConnectable() {
		// TODO Implement the isConnectable method in class Host. Test the connection to the host.
		return false;
	}
	
	public void hostConnect() {
		//TODO: Implement the hostConnect method.
	}
}
