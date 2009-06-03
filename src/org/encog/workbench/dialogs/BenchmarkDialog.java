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
