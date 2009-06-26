/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
		progress = new JProgressBar(0,7);
		setTitle("Please wait, benchmarking Encog.");
		setSize(640,75);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.status = new JLabel("Hello world");
		content.add(this.status, BorderLayout.CENTER);
		content.add(progress, BorderLayout.SOUTH);
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		EncogBenchmark benchmark = new EncogBenchmark(this);
		double d = benchmark.process();
		dispose();
		EncogWorkBench.displayMessage("Benchmark Complete", "This machine's rating is: " + d + "\n(lower is better)");
	}

	public void report(int total, int current, String status) {
		this.status.setText(status);
		this.progress.setValue(current);
		
	}

}
