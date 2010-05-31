package org.encog.workbench.dialogs.config;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PasswordField;
import org.encog.workbench.dialogs.common.TextField;

public class EncogConfigDialog extends EncogPropertiesDialog {

	private TextField network;
	private TextField userID;
	private PasswordField password;
	private DoubleField defaultError;
	private CheckField autoConnect;
	private IntegerField threadCount;
	private CheckField useOpenCL;
	
	public EncogConfigDialog(Frame owner) {
		super(owner);
		List<String> servers = new ArrayList<String>();
		servers.add("cloud.encog.com");
		servers.add("devcloud.encog.com");
		setTitle("Encog Configuration");
		setSize(500,300);
		beginTab("Training");
		addProperty(this.defaultError = new DoubleField("default error","Default Error Percent",true,0,1));
		addProperty(this.threadCount = new IntegerField("thread count","Thread Count (0=auto)",true,0,10000));
		addProperty(this.useOpenCL = new CheckField("use opencl","Use Graphics Card(GPU)"));
		beginTab("Encog Cloud");
		addProperty(this.network = new TextField("network","Network",false));
		addProperty(this.userID = new TextField("user id","User ID",false));
		addProperty(this.password = new PasswordField("password","Password",false));
		addProperty(this.autoConnect = new CheckField("autoconnect","Auto Connect to Encog Cloud"));
		render();
	}

	public TextField getNetwork() {
		return network;
	}

	public TextField getUserID() {
		return userID;
	}

	public PasswordField getPassword() {
		return password;
	}

	public DoubleField getDefaultError() {
		return defaultError;
	}

	public CheckField getAutoConnect() {
		return autoConnect;
	}

	public IntegerField getThreadCount() {
		return threadCount;
	}

	public CheckField getUseOpenCL() {
		return useOpenCL;
	}

}
