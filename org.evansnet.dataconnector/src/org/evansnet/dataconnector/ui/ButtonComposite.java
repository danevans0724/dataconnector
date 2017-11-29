package org.evansnet.dataconnector.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class ButtonComposite extends Composite {
	
	public static final String BTN_CONNECT_DATA = "button connect";
	public static final String BTN_CANCEL_DATA = "button cancel";
	public static final String BTN_DISCONNECT_DATA = "button disconnect";
	public static final String BTN_SAVE_DATA = "button save";
	
	Button btnConnect;
	Button btnDisconnect;
	Button btnSave;
	Button btnCancel;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ButtonComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(9, false));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		btnConnect = new Button(this, SWT.NONE);
		btnConnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnConnect.setText("Connect");
		btnConnect.setData(BTN_CONNECT_DATA);
				
		btnDisconnect = new Button(this, SWT.CENTER);
		btnDisconnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDisconnect.setText("Disconnect");
		btnDisconnect.setData(BTN_DISCONNECT_DATA);
		
		btnSave = new Button(this, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Save");
		btnSave.setData(BTN_SAVE_DATA);
		
		btnCancel = new Button(this, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Canel");
		btnCancel.setData(BTN_CANCEL_DATA);
		
		addButtonListeners();
	}
	
	//TODO: Connect button listeners to parent actions for the connection.

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	
	private void addButtonListeners() {
		btnConnect.addSelectionListener(ConnectionDialog.connDlgBtnListener);
		btnDisconnect.addSelectionListener(ConnectionDialog.connDlgBtnListener);
		btnSave.addSelectionListener(ConnectionDialog.connDlgBtnListener);
		btnCancel.addSelectionListener(ConnectionDialog.connDlgBtnListener);	
	}


}
