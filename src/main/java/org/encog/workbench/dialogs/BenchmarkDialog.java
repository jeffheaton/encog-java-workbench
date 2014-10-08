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
package org.encog.workbench.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.encog.StatusReportable;
import org.encog.util.benchmark.EncogBenchmark;
import org.encog.workbench.EncogWorkBench;



public class BenchmarkDialog extends JDialog implements Runnable, StatusReportable {

	private JProgressBar progress;
	private JLabel status;
	
	public BenchmarkDialog()
	{
		progress = new JProgressBar(0,4);
		setTitle("Please wait, benchmarking Encog.");
		setSize(640,75);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.status = new JLabel("");
		content.add(this.status, BorderLayout.CENTER);
		content.add(progress, BorderLayout.SOUTH);
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		EncogBenchmark benchmark = new EncogBenchmark(this);
		String s = benchmark.process();
		dispose();
		EncogWorkBench.displayMessage("Benchmark Complete", s + "\n(higher is better)");
	}

	public void report(int total, int current, String status) {
		this.status.setText(status);
		this.progress.setValue(current);
		
	}

	public void reportPhase(int arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

}
