package org.evansnet.dataconnector.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

/**
 * Contains the controls that make up the connection dialog. Provides an
 * interface for the user to enter connection information for a host and/or database.
 * 
 * @author pmidce0
 *
 */
public class ConnectionDlgComposite extends Composite {
	
	public static final String TXT_HOST_NAME = "txtHostName";
	public static final String TXT_IPADDR = "txtIPAddress";
	public static final String TXT_PORT = "txtPort";

	private Text txtHostName;
	private Text txtIPAddress;
	private Text txtPort;
	boolean dirty;


	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ConnectionDlgComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		dirty = false;
		
		Label lblHostConnection = new Label(this, SWT.NONE);
		lblHostConnection.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblHostConnection.setForeground(SWTResourceManager.getColor(70, 130, 180));
		lblHostConnection.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD | SWT.ITALIC));
		lblHostConnection.setText("Host Connection");
		
		Label lblHostName = new Label(this, SWT.NONE);
		GridData gd_lblHostName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblHostName.widthHint = 95;
		lblHostName.setLayoutData(gd_lblHostName);
		lblHostName.setText("Host Name:          ");
		
		txtHostName = new Text(this, SWT.BORDER);
		txtHostName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblIpAddress = new Label(this, SWT.NONE);
		lblIpAddress.setText("IP Address: ");
		
		txtIPAddress = new Text(this, SWT.BORDER);
		txtIPAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPort = new Label(this, SWT.NONE);
		lblPort.setText("Port:");
		
		txtPort = new Text(this, SWT.BORDER);
		txtPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean b) {
		dirty = b;
	}
	
	public String getTxtHostName() {
		return txtHostName.getText();
	}

	public String getTxtIPAddress() {
		return txtIPAddress.getText();
	}

	public String getTxtPort() {
		return txtPort.getText();
	}

}
