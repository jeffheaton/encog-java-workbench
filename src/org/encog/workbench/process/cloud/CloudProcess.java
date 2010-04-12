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
			EncogWorkBenchConfig config = EncogWorkBench.getInstance().getConfig();
			dialog.getNetwork().setValue(config.getEncogCloudNetwork());
			dialog.getUserID().setValue(config.getEncogCloudUserID());
			dialog.getPassword().setValue(config.getEncogCloudPassword());
			if (dialog.process()) {
				String uid = dialog.getUserID().getValue();
				String pwd = dialog.getPassword().getValue();

				try {
					EncogCloud cloud = new EncogCloud();
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
					EncogWorkBench.displayMessage("Encog Cloud",
							"You have been logged out.");
				} catch (Throwable t) {
					EncogWorkBench.displayError("Encog Cloud", t, null, null);
				}
			}
		}

	}
}
