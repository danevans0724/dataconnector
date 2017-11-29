package org.evansnet.dataconnector.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;

/**
 * A base class for the various database connector composites. 
 * 
 * @author pmidce0
 *
 */
public class DBBaseConnectorComposite extends Composite {
	
	public static final String TXT_INSTANCE_NAME = "Instance name";
	public static final String TXT_DATABASE_NAME = "Database name";
	public static final String TXT_SCHEMA = "schema";
	public static final String CMB_DBMS = "DBMS";
	
	private Text txtInstanceName;
	private Text txtDatabaseName;
	private Text txtSchema;
	private Combo cmbDBMS;
	
//	DBMS dbms;
	String dbType;
	boolean dirty;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DBBaseConnectorComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		dirty = false;
		
		Label lblDatabaseSystem = new Label(this, SWT.NONE);
		GridData gd_lblDatabaseSystem = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblDatabaseSystem.widthHint = 95;
		lblDatabaseSystem.setLayoutData(gd_lblDatabaseSystem);
		lblDatabaseSystem.setText("Database System: ");
		
		cmbDBMS = new Combo(this, SWT.READ_ONLY);
		cmbDBMS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbDBMS.setData(CMB_DBMS);
		fillDBMSCombo();
		
		Label lblInstanceName = new Label(this, SWT.NONE);
		lblInstanceName.setText("Instance Name: ");
		
		txtInstanceName = new Text(this, SWT.BORDER);
		txtInstanceName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtInstanceName.setData(TXT_INSTANCE_NAME);
		
		Label lblDatabase = new Label(this, SWT.NONE);
		lblDatabase.setText("Database: ");
		
		txtDatabaseName = new Text(this, SWT.BORDER);
		txtDatabaseName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDatabaseName.setData(TXT_DATABASE_NAME);
		
		Label lblSchema = new Label(this, SWT.NONE);
		lblSchema.setText("Schema: ");
		
		txtSchema = new Text(this, SWT.BORDER);
		GridData gd_txtSchema = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSchema.widthHint = 123;
		txtSchema.setLayoutData(gd_txtSchema);
		txtSchema.setData(TXT_SCHEMA);
	}

	private void fillDBMSCombo() {
		cmbDBMS.add("MS SQL Server");
		cmbDBMS.add("MySQL");
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	
	// Getters & setters 
	
	public String getDbType() {
		return cmbDBMS.getText();
	} //TODO Need a selection listener for the combo box that sets dbType

	public void setDbType(String dbt) {
		this.dbType = dbt; 
	}
	
	public String getDatabaseName() {
		return txtDatabaseName.getText();
	}
	
	public void setDatabaseName(String dbName) {
		txtDatabaseName.setText(dbName);
	}
	
	public void setInstanceName(String i) {
		txtInstanceName.setText(i);
	}
	
	public String getInstanceName(){
		return txtInstanceName.getText();
	}
	
	public void setSchema(String s) {
		txtSchema.setText(s);
	}
	
	public String getSchema() {
		return txtSchema.getText();
	}
}
