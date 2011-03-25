package org.encog.workbench.tabs.visualize.ThermalGrid;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.neural.thermal.ThermalNetwork;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.models.ThermalModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class ThermalGridTab extends EncogCommonTab {

	private JTable table;
	private ThermalModel model;
	
	public ThermalGridTab(ProjectEGFile file) {
		super(file);
		
		setLayout(new BorderLayout());
		this.model = new ThermalModel((ThermalNetwork) file.getObject());
		this.table = new JTable(this.model);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add(new JScrollPane(this.table), BorderLayout.CENTER);		
	}
	
	@Override
	public String getName() {
		return "Visualize :" + this.getEncogObject().getName();
	}

}
