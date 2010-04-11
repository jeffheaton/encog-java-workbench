package org.encog.workbench.dialogs.config;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.PasswordField;
import org.encog.workbench.dialogs.common.TextField;

public class EncogConfigDialog extends EncogPropertiesDialog {

	private ComboBoxField network;
	private TextField userID;
	private PasswordField password;
	
	public EncogConfigDialog(Frame owner) {
		super(owner);
		List<String> servers = new ArrayList<String>();
		servers.add("cloud.encog.com");
		servers.add("devcloud.encog.com");
		setTitle("Encog Configuration");
		setSize(400,200);
		beginTab("Training");
		addProperty(new DoubleField("default error","Default Error Percent",true,0,100));
		beginTab("Encog Cloud");
		addProperty(this.network = new ComboBoxField("network","Network",true, servers));
		addProperty(this.userID = new TextField("user id","User ID",true));
		addProperty(this.password = new PasswordField("password","Password",true));
		render();
	}

	public ComboBoxField getNetwork() {
		return network;
	}

	public TextField getUserID() {
		return userID;
	}

	public PasswordField getPassword() {
		return password;
	}
	
}
