package org.evansnet.dataconnector.internal.core;

/**
 * Interface IHost
 * Common contract for connectable entities. Defines the connection requirements and methods.
 * @author pmidce0
 *
 */

public interface IHost {
	

	
	public String getHostName();
	public String getIPaddress();
	public int getPort();
	public String getShareName();
	
	public void setHostName(String h);
	public void setIPaddress(String ip);
	public void setPort(int p);
	public void setShareName(String s);
	
	public boolean isConnectable();
	
}
