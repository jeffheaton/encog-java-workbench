/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

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
	private ComboBoxField errorCalculation;
	
	public EncogConfigDialog(Frame owner) {
		super(owner);
		List<String> servers = new ArrayList<String>();
		List<String> errorMethods = new ArrayList<String>();
		
		servers.add("cloud.encog.com");
		servers.add("devcloud.encog.com");
		errorMethods.add("Root Mean Square");
		errorMethods.add("Mean Square Error");
		errorMethods.add("Arc-Tan Error");
		setTitle("Encog Configuration");
		setSize(500,300);
		beginTab("Training");
		addProperty(this.defaultError = new DoubleField("default error","Default Error Percent",true,0,1));
		addProperty(this.errorCalculation = new ComboBoxField("error calculation", "Error Calculation", true, errorMethods));
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

	public ComboBoxField getErrorCalculation() {
		return errorCalculation;
	}
	
	

}
