package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.cloud.CloudListener;
import org.encog.cloud.node.CloudNode;
import org.encog.cloud.node.HandleClient;
import org.encog.util.Format;
import org.encog.workbench.EncogWorkBench;

public class ConnectionsModel implements TableModel, CloudListener {
	
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private final CloudNode cloud;
	
	public ConnectionsModel() {
		this.cloud = EncogWorkBench.getInstance().getCloud();
		this.cloud.addListener(this);
	}

	private void notifyListeners(final TableModelEvent tce) {
		for (final TableModelListener listener : this.listeners) {
			listener.tableChanged(tce);
		}
	}
	
	@Override
	public int getRowCount() {
		return this.cloud.getConnections().size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex) {
			case 0:
				return "IP";
			case 1:
				return "Source";
			case 2:
				return "Packets";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HandleClient hc = this.cloud.getConnections().get(rowIndex);
		switch(columnIndex) {
			case 0:
				return hc.getLink().getSocket().getRemoteSocketAddress().toString();
			case 1:
				return hc.getRemoteType();
			case 2:
				return "" + Format.formatInteger(hc.getLink().getPackets());
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		this.listeners.add(l);		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		this.listeners.remove(l);		
	}

	@Override
	public void notifyPacket() {
		refresh();		
	}

	@Override
	public void notifyConnections() {
		refresh();
	}

	private void refresh() {
		final TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}

}
