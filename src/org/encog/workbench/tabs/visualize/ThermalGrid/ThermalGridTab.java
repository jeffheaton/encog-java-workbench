package org.encog.workbench.tabs.visualize.ThermalGrid;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.neural.thermal.ThermalNetwork;
import org.encog.workbench.models.ThermalModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class ThermalGridTab extends EncogCommonTab {

	private JTable table;
	private ThermalModel model;
	
	public ThermalGridTab(ThermalNetwork encogObject) {
		super(encogObject);
		
		setLayout(new BorderLayout());
		this.model = new ThermalModel(encogObject);
		this.table = new JTable(this.model);
		add(new JScrollPane(this.table), BorderLayout.CENTER);		
	}

}
