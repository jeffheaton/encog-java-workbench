package org.encog.workbench.process;

import java.io.File;
import java.io.IOException;

import org.encog.persist.EncogMemoryCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.createfile.CreateFileDialog;
import org.encog.workbench.dialogs.createfile.CreateFileType;
import org.encog.workbench.util.FileUtil;

public class CreateNewFile {
	public static void performCreateFile() throws IOException {
		CreateFileDialog dialog = new CreateFileDialog(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.setType(CreateFileType.EGFile);
		
		
		if (dialog.process()) {
			String name = dialog.getFilename();
			
			if( name==null || name.length()==0 ) {
				EncogWorkBench.displayError("Data Missing", "Must specify a filename.");
				return;
			}
			
			if (dialog.getType() == CreateFileType.EGFile) {
				
				name = FileUtil.forceExtension(new File(name).getName(), "eg");
				String basePath = EncogWorkBench.getInstance().getMainWindow()
						.getTree().getPath();
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					EncogMemoryCollection encog = new EncogMemoryCollection();
					encog.save(path.toString());
					EncogWorkBench.getInstance().getMainWindow().getTree()
							.refresh();
				}
			} else if (dialog.getType() == CreateFileType.TextFile) {
				
				name = FileUtil.forceExtension(new File(name).getName(), "txt");
				String basePath = EncogWorkBench.getInstance().getMainWindow()
						.getTree().getPath();
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					FileUtil.writeFileAsString(path, "");
					EncogWorkBench.getInstance().getMainWindow().getTree()
							.refresh();
				}
			} else if (dialog.getType() == CreateFileType.CSVFile) {

				name = FileUtil.forceExtension(new File(name).getName(), "csv");
				String basePath = EncogWorkBench.getInstance().getMainWindow()
						.getTree().getPath();
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					FileUtil.writeFileAsString(path, "");
					EncogWorkBench.getInstance().getMainWindow().getTree()
							.refresh();
				}
			}
		}
	}
}
