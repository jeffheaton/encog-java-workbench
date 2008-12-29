package org.encog.workbench.dialogs;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

import sun.org.mozilla.javascript.internal.Context;

public class EditSpiderOptions extends EncogCommonDialog {

	private JTextField txtName;
	private JTextField txtDescription;
	private JTextField txtTimeout;		
	private JTextField txtMaxDepth;
	private JTextField txtUserAgent;
	private JTextField txtCorePoolSize;
	private JTextField txtPoolSize;
	private JTextField txtKeepAlive;
	private JTextField txtDBURL;
	private JTextField txtDBUID;
	private JTextField txtDBPWD;
	private JTextField txtDBdriver;
	private JComboBox cbWorkload;
	private JComboBox cbFilter;
	private JComboBox cbStartup;
	
	private String objectName;
	private String objectDescription;
	private int timeout;		
	private int maxDepth;
	private String userAgent;
	private int corePoolSize;
	private int poolSize;
	private long keepAlive;
	private String dbURL;
	private String dbUID;
	private String dbPWD;
	private String dbDriver;
	private String workload;
	private String filter;
	private String startup;
	
	/**
	 * @return the poolSize
	 */
	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * @param poolSize the poolSize to set
	 */
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}



	public EditSpiderOptions(final Frame owner)
	{
		super(owner);
		setTitle("Edit Spider Options");
		setSize(400,420);
		
		this.txtName = new JTextField();
		this.txtDescription = new JTextField();
		this.txtTimeout = new JTextField();		
		this.txtMaxDepth = new JTextField();
		this.txtUserAgent = new JTextField();
		this.txtCorePoolSize = new JTextField();
		this.txtPoolSize = new JTextField();
		this.txtKeepAlive = new JTextField();
		this.txtDBURL = new JTextField();
		this.txtDBUID = new JTextField();
		this.txtDBPWD = new JTextField();
		this.txtDBdriver = new JTextField();
		this.cbWorkload = new JComboBox();
		this.cbFilter = new JComboBox();
		this.cbStartup = new JComboBox();

		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(15, 2, 10, 10));


		final String[] filters = { "RobotsFilter" };
		final String[] workloadManagers = { "MemoryWorkloadManager", "SQLWorkloadManager" };
		final String[] startup = { "CLEAR", "RESUME" };

		content.add(new JLabel("Name"));
		content.add(this.txtName);
		
		content.add(new JLabel("Description"));
		content.add(this.txtDescription);
		
		content.add(new JLabel("Timeout"));
		content.add(this.txtTimeout);
		
		content.add(new JLabel("Max Depth"));
		content.add(this.txtMaxDepth);
		
		content.add(new JLabel("User Agent"));
		content.add(this.txtUserAgent);
		
		content.add(new JLabel("Core Pool Size"));
		content.add(this.txtCorePoolSize);
		
		content.add(new JLabel("Max Pool Size"));
		content.add(this.txtPoolSize);
		
		content.add(new JLabel("Thread Keep Alive"));
		content.add(this.txtKeepAlive);
		
		content.add(new JLabel("DB URL/Connection String"));
		content.add(this.txtDBURL);
		
		content.add(new JLabel("DB User ID"));
		content.add(this.txtDBUID);
		
		content.add(new JLabel("DB Password"));
		content.add(this.txtDBPWD);
		
		content.add(new JLabel("DB DriverClass"));
		content.add(this.txtDBdriver);
		
		content.add(new JLabel("Wordload Manager"));
		content.add(this.cbWorkload);
		
		content.add(new JLabel("Filter Manager"));
		content.add(this.cbFilter);
		
		content.add(new JLabel("Startup"));
		content.add(this.cbStartup);
		
		this.cbFilter.setModel(new DefaultComboBoxModel(filters));
		this.cbWorkload.setModel(new DefaultComboBoxModel(workloadManagers));
		this.cbStartup.setModel(new DefaultComboBoxModel(startup));

	}
	
	@Override
	public void collectFields() throws ValidationException {
		this.objectName = validateFieldString("Name", this.txtName, true);
		this.objectDescription = validateFieldString("Description", this.txtDescription, false);
		this.userAgent = this.validateFieldString("User Agent", this.txtUserAgent, false);
		this.timeout = (int) this.validateFieldNumeric("Timeout", this.txtTimeout, 0, 9999999);		
		this.maxDepth = (int) this.validateFieldNumeric("Max Depth", this.txtMaxDepth, -1, 9999999);
		this.userAgent = this.validateFieldString("User Agent", this.txtUserAgent, false);
		this.corePoolSize = (int) this.validateFieldNumeric("Core Pool Size", this.txtCorePoolSize, 0, 1000);
		this.poolSize = (int) this.validateFieldNumeric("Pool Size", this.txtPoolSize, 0, 1000);
		this.keepAlive  = (int) this.validateFieldNumeric("Keep Alive", this.txtKeepAlive, 0, 9999999);
		this.dbURL = this.validateFieldString("Connect string", this.txtUserAgent, false);
		this.dbUID = this.validateFieldString("User ID", this.txtDBUID, false);
		this.dbPWD = this.validateFieldString("Password", this.txtDBPWD, false);
		this.dbDriver = this.validateFieldString("Driver", this.txtDBdriver, false);
		this.workload = this.validateFieldString("Workload", this.cbWorkload, true);
		this.filter = this.validateFieldString("Filter", this.cbFilter, false);
		this.startup = this.validateFieldString("Startup", this.cbStartup, true);
		
	}

	@Override
	public void setFields() {
		this.txtName.setText(this.objectName);
		this.txtDescription.setText(this.objectDescription);
		this.txtTimeout.setText(""+this.timeout);		
		this.txtMaxDepth.setText(""+this.maxDepth);
		this.txtUserAgent.setText(this.userAgent);
		this.txtCorePoolSize.setText(""+this.corePoolSize);
		this.txtPoolSize.setText(""+this.poolSize);
		this.txtKeepAlive.setText(""+this.keepAlive);
		this.txtDBURL.setText(this.dbURL);
		this.txtDBUID.setText(this.dbUID);
		this.txtDBPWD.setText(this.dbPWD);
		this.txtDBdriver.setText(this.dbDriver);
		
		if( this.workload==null)
		{
			this.workload = "MemoryWorkloadManager";
		}
		
		if( this.startup==null)
		{
			this.startup = "CLEAR";
		}
		
		if( this.filter==null)
		{
			this.filter = "";
		}
		
		if(this.workload.endsWith("MemoryWorkloadManager"))
		{
			this.cbWorkload.setSelectedIndex(0);
		}
		else if(this.workload.endsWith("SQLWorkloadManager"))
		{
			this.cbWorkload.setSelectedIndex(1);
		}
		else
		{
			this.cbWorkload.setSelectedIndex(-1);
			this.cbWorkload.setSelectedItem(this.workload);
		}
			
		if(this.startup.toUpperCase().endsWith("CLEAR"))
		{
			this.cbStartup.setSelectedIndex(0);
		}
		else if(this.startup.toUpperCase().endsWith("RESUME"))
		{
			this.cbStartup.setSelectedIndex(1);
		}
		else
		{
			this.cbStartup.setSelectedIndex(-1);
			this.cbStartup.setSelectedItem(this.startup);
		}
			
		if(this.startup.toUpperCase().endsWith("RobotsFilter"))
		{
			this.cbFilter.setSelectedIndex(0);
		}
		else 
		{
			this.cbFilter.setSelectedIndex(-1);
			this.cbFilter.setSelectedItem(this.filter);
		}
		
		this.cbWorkload.setEditable(true);
		this.cbFilter.setEditable(true);
		this.cbStartup.setEditable(true);
		
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the maxDepth
	 */
	public int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * @param maxDepth the maxDepth to set
	 */
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	/**
	 * @return the corePoolSize
	 */
	public int getCorePoolSize() {
		return corePoolSize;
	}

	/**
	 * @param corePoolSize the corePoolSize to set
	 */
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	/**
	 * @return the keepAlive
	 */
	public long getKeepAlive() {
		return keepAlive;
	}

	/**
	 * @param keepAlive the keepAlive to set
	 */
	public void setKeepAlive(long keepAlive) {
		this.keepAlive = keepAlive;
	}

	/**
	 * @return the dbURL
	 */
	public String getDbURL() {
		return dbURL;
	}

	/**
	 * @param dbURL the dbURL to set
	 */
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	/**
	 * @return the dbUID
	 */
	public String getDbUID() {
		return dbUID;
	}

	/**
	 * @param dbUID the dbUID to set
	 */
	public void setDbUID(String dbUID) {
		this.dbUID = dbUID;
	}

	/**
	 * @return the dbPWD
	 */
	public String getDbPWD() {
		return dbPWD;
	}

	/**
	 * @param dbPWD the dbPWD to set
	 */
	public void setDbPWD(String dbPWD) {
		this.dbPWD = dbPWD;
	}

	/**
	 * @return the dbDriver
	 */
	public String getDbDriver() {
		return dbDriver;
	}

	/**
	 * @param dbDriver the dbDriver to set
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the startup
	 */
	public String getStartup() {
		return startup;
	}

	/**
	 * @param startup the startup to set
	 */
	public void setStartup(String startup) {
		this.startup = startup;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the workload
	 */
	public String getWorkload() {
		return workload;
	}

	/**
	 * @param workload the workload to set
	 */
	public void setWorkload(String workload) {
		this.workload = workload;
	}

	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * @return the objectDescription
	 */
	public String getObjectDescription() {
		return objectDescription;
	}

	/**
	 * @param objectDescription the objectDescription to set
	 */
	public void setObjectDescription(String objectDescription) {
		this.objectDescription = objectDescription;
	}
	
	

}
