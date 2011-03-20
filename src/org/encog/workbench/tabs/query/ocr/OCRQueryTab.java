package org.encog.workbench.tabs.query.ocr;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.encog.ml.BasicML;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.tabs.EncogCommonTab;

public class OCRQueryTab extends EncogCommonTab implements ActionListener {

	private BasicML method;
	private OCRGridPanel panel;
	private JPanel buttonPanel;
	private JButton buttonQuery;
	private JButton buttonDownsample;
	private JButton buttonClear;
	private DrawingEntry entry;

	
	public OCRQueryTab(BasicML method) {
		super((EncogPersistedObject)method);
		
		this.method = method;
		
		this.setLayout(new BorderLayout());
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(1,2));
		this.add(body,BorderLayout.CENTER);
		
		this.buttonQuery = new JButton("Query");
		this.buttonDownsample = new JButton("Downsample");
		this.buttonClear = new JButton("Clear");		
		this.buttonPanel = new JPanel();
		this.buttonPanel.add(this.buttonQuery);
		this.buttonPanel.add(this.buttonDownsample);
		this.buttonPanel.add(this.buttonClear);
		this.add(this.buttonPanel, BorderLayout.NORTH);
		
		this.buttonQuery.addActionListener(this);
		this.buttonDownsample.addActionListener(this);
		this.buttonClear.addActionListener(this);
		
		this.panel = new OCRGridPanel(this.method);
		this.entry = new DrawingEntry();
		
		JPanel panelRight = new JPanel();
		panelRight.setLayout(new GridLayout(2,1));
		panelRight.add(this.panel);
		panelRight.add(this.entry);
				
		body.add(panelRight);
		body.add(new JPanel());
		
	}


	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
