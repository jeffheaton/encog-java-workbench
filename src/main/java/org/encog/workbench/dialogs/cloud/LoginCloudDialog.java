/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
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
