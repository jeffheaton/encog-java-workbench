/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
