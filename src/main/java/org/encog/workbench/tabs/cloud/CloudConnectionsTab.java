package org.encog.workbench.tabs.cloud;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.cloud.CloudListener;
import org.encog.workbench.models.BufferedDataSetTableModel;
import org.encog.workbench.models.ConnectionsModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class CloudConnectionsTab extends EncogCommonTab implements ActionListener {

	private ConnectionsModel model;
	private JTable table;
	
	public CloudConnectionsTab() {
		super(null);
		setLayout(new BorderLayout());
		this.model = new ConnectionsModel();
		this.table = new JTable(this.model);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		this.table.getColumnModel().getColumn(0).setPreferredWidth(150);
		this.table.getColumnModel().getColumn(1).setPreferredWidth(300);
		this.table.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		add(new JScrollPane(this.table), BorderLayout.CENTER);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public String getName() {
		return "Cloud Connections";
	}

	public CloudListener getModel() {
		// TODO Auto-generated method stub
		return this.model;
	}

}
