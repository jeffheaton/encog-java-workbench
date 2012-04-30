package org.encog.workbench.tabs.cloud;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;

import org.encog.cloud.indicator.basic.DownloadIndicator;
import org.encog.util.HTMLReport;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.tabs.HTMLTab;

public class IndicatorDownloadTab extends HTMLTab implements ActionListener, Runnable {

	private JButton button;
	private boolean done = false;
	private DownloadIndicator downloadInd; 

	public IndicatorDownloadTab(File theTargetFile, List<String> list) {
		super(null);
		add(button = new JButton("Stop Download"), BorderLayout.NORTH);
		this.button.addActionListener(this);
		downloadInd = EncogWorkBench.getInstance().getIndicatorFactory().prepareDownload(theTargetFile, list);
		generate();
		new Thread(this).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.button) {
			close();
			dispose();
		}

	}

	@Override
	public void dispose() {
		super.dispose();
		EncogWorkBench.getInstance().getIndicatorFactory().clear();
	}

	@Override
	public String getName() {
		return "Download";
	}

	public boolean close() {
		if (!this.done) {
			try {
				EncogWorkBench.getInstance().getMainWindow().beginWait();
				if (this.downloadInd.getRowsDownloaded() > 0) {
					this.downloadInd.save();
				}
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}
			EncogWorkBench.displayMessage("Download Complete",
					"Rows downloaded: " + this.downloadInd.getRowsDownloaded());
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();

		}
		this.done = true;
		return true;
	}

	public void generate() {
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		String title = "Download in Progress";
		report.title(title);
		report.beginBody();
		report.h1(title);
		report.para("A download is in progress.  To terminate the download select \"Stop Download\" above.  You may now begin using your indicator in another application.");

		report.beginTable();
		report.tablePair("Rows Downloaded", "" + this.downloadInd.getRowsDownloaded());
		report.endTable();

		report.endList();
		report.endBody();
		report.endHTML();

		this.display(report.toString());
	}

	@Override
	public void run() {
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			generate();
		}

	}

}
