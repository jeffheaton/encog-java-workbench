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

package org.encog.workbench.process.cloud;

import org.encog.cloud.EncogCloud;
import org.encog.cloud.EncogCloudError;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.config.EncogWorkBenchConfig;
import org.encog.workbench.dialogs.cloud.LoginCloudDialog;

public class CloudProcess {
	public static void Login() {
		if (EncogWorkBench.getInstance().getCloud() == null) {

			LoginCloudDialog dialog = new LoginCloudDialog(EncogWorkBench
					.getInstance().getMainWindow());
			EncogWorkBenchConfig config = EncogWorkBench.getInstance()
					.getConfig();
			dialog.getNetwork().setValue(config.getEncogCloudNetwork());
			dialog.getUserID().setValue(config.getEncogCloudUserID());
			dialog.getPassword().setValue(config.getEncogCloudPassword());
			if (dialog.process()) {
				String uid = dialog.getUserID().getValue();
				String pwd = dialog.getPassword().getValue();
				String server = dialog.getNetwork().getValue();

				try {
					EncogCloud cloud = new EncogCloud(server);
					cloud.connect(uid, pwd);
					EncogWorkBench.getInstance().setCloud(cloud);
					EncogWorkBench.displayMessage("Login",
							"You are now connected to the Encog cloud.");
				} catch (EncogCloudError error) {
					EncogWorkBench.displayError("Can't Connect", error
							.getMessage());
				}
			}
		} else {
			if (EncogWorkBench
					.askQuestion("Encog Cloud",
							"You are already connected to the cloud.\nWould you like to logout?")) {
				try {
					EncogWorkBench.getInstance().getCloud().logout();
					EncogWorkBench.getInstance().setCloud(null);
					EncogWorkBench.displayMessage("Encog Cloud",
							"You have been logged out.");
				} catch (Throwable t) {
					EncogWorkBench.displayError("Encog Cloud", t, null, null);
				}
			}
		}

	}

	public static void performAutoConnect() {
		try {
			String uid = EncogWorkBench.getInstance().getConfig()
					.getEncogCloudUserID();
			String pwd = EncogWorkBench.getInstance().getConfig()
					.getEncogCloudPassword();
			String server = EncogWorkBench.getInstance().getConfig()
					.getEncogCloudPassword();
			
			EncogCloud cloud = new EncogCloud(server);
			cloud.connect(uid, pwd);
			EncogWorkBench.getInstance().setCloud(cloud);
		} catch (EncogCloudError error) {
			EncogWorkBench.displayError("Can't Connect", error.getMessage());
		}
	}
}
