package org.encog.workbench.dialogs.cloud;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.PasswordField;
import org.encog.workbench.dialogs.common.TextField;

public class LoginCloudDialog extends EncogPropertiesDialog {

	private TextField network;
	private TextField userID;
	private PasswordField password;
	
	public LoginCloudDialog(Frame owner) {
		super(owner);
		setTitle("Login to Encog Cloud");
		setSize(400,200);
		addProperty(this.network = new TextField("network","Network",true));
		addProperty(this.userID = new TextField("user id","User ID",true));
		addProperty(this.password = new PasswordField("password","Password",true));
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
	
	

}
