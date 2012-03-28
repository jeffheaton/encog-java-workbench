package org.encog.workbench.tabs.cloud;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import org.encog.EncogError;
import org.encog.cloud.CloudListener;
import org.encog.cloud.basic.CloudPacket;
import org.encog.cloud.basic.CommunicationLink;
import org.encog.util.HTMLReport;
import org.encog.util.logging.EncogLogging;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.tabs.HTMLTab;

public class IndicatorDownloadTab extends HTMLTab implements ActionListener,
		CloudListener, Runnable {

	private int rowsDownloaded;
	private JButton button;
	private boolean done = false;
	private Map<String, InstrumentHolder> data = new HashMap<String, InstrumentHolder>();
	private File targetFile;
	private List<String> dataSource;
	private CommunicationLink mylink;

	public IndicatorDownloadTab(File theTargetFile, List<String> list) {
		super(null);
		this.rowsDownloaded = 0;
		this.targetFile = theTargetFile;
		add(button = new JButton("Stop Download"), BorderLayout.NORTH);
		this.button.addActionListener(this);
		EncogWorkBench.getInstance().getCloud().addListener(this);
		generate();
		this.dataSource = list;
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
		EncogWorkBench.getInstance().getCloud().removeListener(this);
	}

	@Override
	public String getName() {
		return "Download";
	}

	public boolean close() {
		if (!this.done) {
			try {
				EncogWorkBench.getInstance().getMainWindow().beginWait();
				if (this.rowsDownloaded > 0) {
					saveFile();
				}
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}
			EncogWorkBench.displayMessage("Download Complete",
					"Rows downloaded: " + this.rowsDownloaded);

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
		report.tablePair("Rows Downloaded", "" + this.rowsDownloaded);
		report.endTable();

		report.endList();
		report.endBody();
		report.endHTML();

		this.display(report.toString());
	}

	@Override
	public void notifyPacket(CloudPacket packet) {
		if (packet.getCommand().equalsIgnoreCase("bar")) {
			try {
				String security = packet.getArgs()[1];
				long when = Long.parseLong(packet.getArgs()[0]);
				String key = security.toLowerCase();
				InstrumentHolder holder = null;

				if (this.data.containsKey(key)) {
					holder = this.data.get(key);
				} else {
					holder = new InstrumentHolder();
					this.data.put(key, holder);
				}

				if (holder.record(when, 2, packet.getArgs())) {
					this.rowsDownloaded++;
				}
			} catch (Exception ex) {
				EncogLogging.log(ex);
			}

		}
	}

	@Override
	public void notifyConnections(CommunicationLink link, boolean hasOpened) {
		if( hasOpened && mylink==null) {
			this.mylink = link;
			this.mylink.requestSignal(this.dataSource);
		} else if( !hasOpened && link==this.mylink ) {
			this.close();
			this.dispose();
		}

	}

	@Override
	public void run() {
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			generate();
		}

	}

	public void saveFile() {
		try {
			FileWriter outFile = new FileWriter(this.targetFile);
			PrintWriter out = new PrintWriter(outFile);

			for (String ins : this.data.keySet()) {
				InstrumentHolder holder = this.data.get(ins);
				for (Long key : holder.getSorted()) {
					String str = holder.getData().get(key);
					out.println("\"" + ins + "\"," + key + "," + str);
				}
			}

			out.close();
		} catch (IOException ex) {
			throw new EncogError(ex);
		}

	}

}
