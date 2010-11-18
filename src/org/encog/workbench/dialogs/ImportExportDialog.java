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
package org.encog.workbench.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.encog.engine.StatusReportable;
import org.encog.engine.util.Format;
import org.encog.neural.data.buffer.BinaryDataLoader;
import org.encog.util.benchmark.EncogBenchmark;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.TaskComplete;

public class ImportExportDialog extends JDialog implements Runnable,
		StatusReportable {

	private JProgressBar progress;
	private JLabel status;
	private BinaryDataLoader loader;
	private File binaryFile;
	private boolean performImport;
	private TaskComplete done;

	public ImportExportDialog(BinaryDataLoader loader, File binaryFile,
			boolean performImport) {
		this.loader = loader;
		this.binaryFile = binaryFile;
		this.performImport = performImport;
		progress = new JProgressBar(0, 100);

		if (performImport)
			setTitle("Please wait...importing...");
		else
			setTitle("Please wait...exporting...");

		setSize(640, 75);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.status = new JLabel("");
		content.add(this.status, BorderLayout.CENTER);
		content.add(progress, BorderLayout.SOUTH);
	}

	public void process(TaskComplete done) {
		this.done = done;
		setVisible(true);
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
		this.loader.setStatus(this);

		if (performImport)
			this.loader.external2Binary(binaryFile);
		else
			this.loader.binary2External(binaryFile);

		dispose();

		if (performImport)
			EncogWorkBench.displayMessage("Done", "Import Complete");
		else
			EncogWorkBench.displayMessage("Done", "Export Complete");
		
		if( this.done!=null ) {
			done.complete();
		}
		}
		catch(Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	public void report(int total, int current, String status) {
		StringBuilder result = new StringBuilder();

		if (total > 0) {
			double percent = (double) current / (double) total;
			int value = (int) (100.0 * percent);
			this.progress.setValue(value);
			result.append(Format.formatInteger(current));
			result.append(" / ");
			result.append(Format.formatInteger(total));
			result.append(": ");
		} else {
			result.append(Format.formatInteger(current));
			result.append(": ");
			this.progress.setValue(0);
		}

		result.append(status);
		this.status.setText(result.toString());
	}

}
