package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.encog.ml.data.buffer.BufferedNeuralDataSet;

public class TrainingListModel implements ListModel {

	private BufferedNeuralDataSet data;
	private final List<ListDataListener> listeners = new ArrayList<ListDataListener>();
	
	public TrainingListModel(BufferedNeuralDataSet data) {
		this.data = data;
	}
	
	public int getSize() {
		return (int)data.getRecordCount();
	}

	public Object getElementAt(int index) {
		return "Element #" + index;
	}

	public void addListDataListener(ListDataListener l) {
		listeners.add(l);		
	}

	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
