package org.encog.workbench.dialogs.training;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public abstract class BasicTrainingProgress extends JDialog implements Runnable,ActionListener {
	
	private JButton buttonStart;
	private JButton buttonStop;
	private JButton buttonClose;
	protected JPanel panelBody;
	private JPanel panelButtons;
	private Thread thread;
	private boolean cancel;
	
	public BasicTrainingProgress(Frame owner)
	{
		super(owner);
		this.setSize(320,200);
		Container content = this.getContentPane();
		this.buttonStart = new JButton("Start");
		this.buttonStop = new JButton("Stop");
		this.buttonClose = new JButton("Close");
		
		this.buttonStart.addActionListener(this);
		this.buttonStop.addActionListener(this);
		this.buttonClose.addActionListener(this);
		
		content.setLayout(new BorderLayout());
		this.panelBody = new JPanel();
		this.panelButtons = new JPanel();
		panelButtons.add(this.buttonStart);
		panelButtons.add(this.buttonStop);
		panelButtons.add(this.buttonClose);
		content.add(this.panelBody,BorderLayout.CENTER);
		content.add(this.panelButtons,BorderLayout.SOUTH);
		
		this.buttonStop.setEnabled(false);		
	}

	public void run() {
		startup();
		while(!cancel)
		{
			iteration();
		}		
		shutdown();
		stopped();
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.buttonClose )
			dispose();
		else if(e.getSource()==this.buttonStart)
			performStart();
		else if(e.getSource()==this.buttonStop)
			performStop();		
	}

	private void performStop() {
		this.cancel = true;
	}

	private void performStart() {
		this.buttonStart.setEnabled(false);
		this.buttonStop.setEnabled(true);
		this.cancel = false;
		this.thread = new Thread(this);
		this.thread.start();
	}	
	
	private void stopped()
	{
		this.buttonStart.setEnabled(true);
		this.buttonStop.setEnabled(false);		
		this.cancel = true;
	}
	
	public abstract void startup();
	public abstract void shutdown();
	public abstract void iteration();
}
