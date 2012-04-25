package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.cloud.indicator.IndicatorListener;
import org.encog.cloud.indicator.server.HandleClient;
import org.encog.cloud.indicator.server.IndicatorLink;
import org.encog.cloud.indicator.server.IndicatorPacket;
import org.encog.cloud.indicator.server.IndicatorServer;
import org.encog.util.Format;
import org.encog.workbench.EncogWorkBench;

public class ConnectionsModel implements TableModel, IndicatorListener {
	
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	
	public ConnectionsModel() {
		
	}

	private void notifyListeners(final TableModelEvent tce) {
		for (final TableModelListener listener : this.listeners) {
			listener.tableChanged(tce);
		}
	}
	
	@Override
	public int getRowCount() {
		IndicatorServer cloud = EncogWorkBench.getInstance().getCloud();
		return cloud.getConnections().size();
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
		IndicatorServer cloud = EncogWorkBench.getInstance().getCloud();
		HandleClient hc = cloud.getConnections().get(rowIndex);
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
	public void notifyConnections(IndicatorLink link, boolean opened) {
		refresh();
	}

	private void refresh() {
		final TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}

	@Override
	public void notifyPacket(IndicatorPacket packet) {
		refresh();
	}

}
