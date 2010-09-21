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

package org.encog.workbench.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.encog.engine.StatusReportable;
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

}
