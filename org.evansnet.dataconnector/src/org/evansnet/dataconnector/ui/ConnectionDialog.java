package org.evansnet.dataconnector.ui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.evansnet.dataconnector.internal.core.Credentials;
import org.evansnet.dataconnector.internal.core.DBMS;
import org.evansnet.dataconnector.internal.core.DBType;
import org.evansnet.dataconnector.internal.core.Host;
import org.evansnet.dataconnector.internal.dbms.MySQLConnection;
import org.evansnet.dataconnector.internal.dbms.SQLSrvConnection;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class ConnectionDialog extends Dialog {

	private Host host;
	private DBMS dbms;
	private Credentials credentials;
	private Connection conn;
	
	public Object result;
	protected Shell shlConn;
	
	protected ConnectionDlgComposite connHostComposite;
	protected DBBaseConnectorComposite dbComposite;
	protected CredentialsComposite credentialsComposite;
	protected ButtonComposite buttonComposite;
	
	protected static SelectionListener connDlgBtnListener;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 * @throws Exception 
	 */
	public ConnectionDialog(Shell parent, int style) throws Exception {
		super(parent, style);
		setText("Create Connection");
		shlConn = parent;
		host = new Host();
		credentials = new Credentials();
 		createContents();
	} 

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		shlConn.open();
		shlConn.layout();
		Display display = getParent().getDisplay();
		while (!shlConn.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		
		connDlgBtnListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button)e.widget;
				String selButton = (String)btn.getData();
				//TODO: ConnectionDialog: add code to handle the other buttons
				if(selButton == "button connect") {
					try {
						doConnect();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (selButton == "button Cancel") {
					return;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Handled above. Do nothing here.				
			}			
		};
		
		shlConn = new Shell(getParent(), getStyle());
		shlConn.setSize(350, 370);
		shlConn.setText("Data Connection");
		shlConn.setLayout(new GridLayout(1, false));
		
		connHostComposite = new ConnectionDlgComposite(shlConn, SWT.NONE);
		GridData gd_ConnHostComposite = new GridData(GridData.FILL, GridData.FILL,
				true, true, 1, 1);
		connHostComposite.setLayoutData(gd_ConnHostComposite);
		
		dbComposite = new DBBaseConnectorComposite(shlConn, SWT.NONE);
		GridData gd_dbComposite = new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1);
		dbComposite.setLayoutData(gd_dbComposite);
		
		credentialsComposite = new CredentialsComposite(shlConn, SWT.NONE);
		GridData gd_credentialsComposite = new GridData(GridData.FILL, GridData.FILL,
				true, true, 1, 1);
		credentialsComposite.setLayoutData(gd_credentialsComposite);
		
		buttonComposite = new ButtonComposite(shlConn, SWT.NONE);
		GridData gd_buttonComposite = new GridData(GridData.FILL, GridData.FILL, 
				true, true, 1, 1);
		buttonComposite.setLayoutData(gd_buttonComposite);
	 }
	
	private void doConnect() throws Exception {
		modelPopulate();
		String cStr = dbms.buildConnectionString(dbms.getDBMS());
		try {		
			conn = dbms.connect(cStr); 
			result = dbms;
			shlConn.dispose();
		} catch (SQLException e) {
			// TODO: Log a message and trap or fail gracefully
			e.printStackTrace();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			conn.close();
		} finally {
			shlConn.dispose();		//TODO: Post a message box to notify of error.
		}
	}
	
	/**
	 * Gets the values from the controls and populates the host, dbms,
	 * credentials model objects.
	 * @throws Exception 
	 */
	private void modelPopulate() throws Exception {
		DBType dbt = DBType.NONE;
		dbt = dbt.getType(dbComposite.getDbType());
		
		switch (dbt) {
		case MS_SQLSrv:
			dbms = new SQLSrvConnection(host);
			break;
		case MySQL:
			dbms = new MySQLConnection(host);
			break;
		default:
			// Temporary, replace once all connector classes are implemented.
			dbms = new SQLSrvConnection();
		}
		
		host.setHostName(connHostComposite.getTxtHostName());
		host.setIPaddress(connHostComposite.getTxtIPAddress());
		host.setPort(Integer.parseInt(connHostComposite.getTxtPort()));
		
		dbms.setDatabaseName(dbComposite.getDatabaseName());
		dbms.setDBMS(dbt);
		dbms.setInstanceName(dbComposite.getInstanceName());
		dbms.setSchema(dbComposite.getSchema());
		
		credentials.setPassword(credentialsComposite.getTxtPassword());
		credentials.setUserID(credentialsComposite.getTxtUserID().toCharArray());
		dbms.setCredentials(credentials);
	}
	
}
