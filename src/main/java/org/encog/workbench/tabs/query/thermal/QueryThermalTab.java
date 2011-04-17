package org.encog.workbench.tabs.query.thermal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.encog.neural.thermal.ThermalNetwork;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.tabs.EncogCommonTab;

public class QueryThermalTab extends EncogCommonTab implements ActionListener {

	private ThermalNetwork network;
	
	public QueryThermalTab(ProjectEGFile network) {
		super(network);
		this.network = (ThermalNetwork) network.getObject();
		
		this.setLayout(new BorderLayout());
		
		this.buttonTrain = new JButton("Train");
		this.buttonGo = new JButton("Go");
		this.buttonClear = new JButton("Clear");
		this.buttonClearMatrix = new JButton("Clear Matrix");		
		this.buttonPanel = new JPanel();
		this.buttonPanel.add(this.buttonTrain);
		this.buttonPanel.add(this.buttonGo);
		this.buttonPanel.add(this.buttonClear);
		this.buttonPanel.add(this.buttonClearMatrix);
		this.add(this.buttonPanel, BorderLayout.SOUTH);

		this.buttonTrain.addActionListener(this);
		this.buttonGo.addActionListener(this);
		this.buttonClear.addActionListener(this);
		this.buttonClearMatrix.addActionListener(this);
		
		this.panel = new ThermalPanel(this.network);
		this.add(this.panel, BorderLayout.CENTER);
		
	}
	

	private ThermalPanel panel;
	private JPanel buttonPanel;
	private JButton buttonTrain;
	private JButton buttonGo;
	private JButton buttonClear;
	private JButton buttonClearMatrix;

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.buttonClear) {
			this.panel.clear();
		} else if (e.getSource() == this.buttonClearMatrix) {
			this.panel.clearMatrix();
		} else if (e.getSource() == this.buttonGo) {
			this.panel.go();
		} else if (e.getSource() == this.buttonTrain) {
			setDirty(true);
			this.panel.train();
		}
	}

	@Override
	public String getName() {
		return "Thrm :" + this.getEncogObject().getName();
	}

}
