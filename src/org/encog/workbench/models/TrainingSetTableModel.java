package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralDataSet;

public class TrainingSetTableModel implements TableModel {

	private BasicNeuralDataSet data;
	private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	
	public TrainingSetTableModel(BasicNeuralDataSet data)
	{
		this.data = data;
	}
	
	@Override
	public void addTableModelListener(TableModelListener listner) {
		this.listeners.add(listner);		
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return Double.class;
	}

	@Override
	public int getColumnCount() {		
		return data.getIdealSize()+data.getInputSize();
	}

	@Override
	public String getColumnName(int columnIndex) {
		if( (columnIndex< data.getInputSize()) )
			return "Input " + (columnIndex+1);
		
		return "Ideal " + ((columnIndex+1)-data.getInputSize());
	}

	@Override
	public int getRowCount() {
		int i=0;
		for(@SuppressWarnings("unused")
		NeuralDataPair pair: this.data)
		{
			i++;
		}
		return i;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		for( NeuralDataPair pair: this.data)
		{
			if( rowIndex==0 )
			{
				if( columnIndex>=pair.getInput().size() )
					return pair.getIdeal().getData(columnIndex-pair.getInput().size());
				return pair.getInput().getData(columnIndex);
			}
			rowIndex--;			
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		this.listeners.remove(l);		
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		for( NeuralDataPair pair: this.data)
		{
			if( rowIndex==0 )
			{
				if( columnIndex>=pair.getInput().size() )
					pair.getIdeal().setData(columnIndex-pair.getInput().size(),((Double)value).doubleValue());
				pair.getInput().setData(columnIndex,((Double)value).doubleValue());
			}
			rowIndex--;			
		}
		
	}

	public void addInputColumn() {
		for( NeuralDataPair pair: this.data)
		{
			BasicNeuralData input = (BasicNeuralData)pair.getInput();
			double[] d = new double[input.size()+1];
			for(int i=0;i<input.size();i++)
			{
				d[i] = input.getData(i);
			}
			input.setData(d);
			
		}
		
		TableModelEvent tce = new TableModelEvent(this, TableModelEvent.HEADER_ROW);
		notifyListeners(tce);		
	}

	public void delColumn(int col) {
		int inputSize = this.data.getInputSize();
		
		// does it fall inside of input or ideal?
		if( col< inputSize )
		{
			for( NeuralDataPair pair: this.data )
			{
				NeuralData input = pair.getInput();
				double[] d = new double[input.size()-1];
				int t = 0;
				for(int i=0;i<input.size();i++)
				{
					if( i!=col )
					{
						d[t] = pair.getInput().getData(i);
						t++;
					}					
				}
				input.setData(d);
			}
		}
		else
		{
			for( NeuralDataPair pair: this.data )
			{
				NeuralData ideal = pair.getIdeal();
				double[] d = new double[ideal.size()-1];
				int t = 0;
				for(int i=0;i<ideal.size();i++)
				{
					if( i!=(col-inputSize) )
					{
						d[t] = pair.getInput().getData(i);
						t++;
					}
					
				}
				ideal.setData(d);
			}
		}
		
		TableModelEvent tce = new TableModelEvent(this, TableModelEvent.HEADER_ROW);
		notifyListeners(tce);
		
	}

	public void addIdealColumn() {
		for( NeuralDataPair pair: this.data)
		{
			BasicNeuralData ideal = (BasicNeuralData)pair.getIdeal();
			double[] d = new double[ideal.size()+1];
			for(int i=0;i<ideal.size();i++)
			{
				d[i] = ideal.getData(i);
			}
			ideal.setData(d);
			
		}
		
		TableModelEvent tce = new TableModelEvent(this, TableModelEvent.HEADER_ROW);
		notifyListeners(tce);	
		
	}

	public void addRow(int row) {
		int idealSize = this.data.getIdealSize();
		int inputSize = this.data.getInputSize();
		NeuralData idealData = new BasicNeuralData(idealSize);
		NeuralData inputData = new BasicNeuralData(inputSize);
		NeuralDataPair pair = new BasicNeuralDataPair(inputData,idealData);
		if( row==-1 )
			this.data.getData().add(pair);
		else
			this.data.getData().add(row,pair);
		
		TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}

	public void delRow(int row) {
		this.data.getData().remove(row);
		TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}
	
	private void notifyListeners(TableModelEvent tce)
	{
		for(TableModelListener listener:this.listeners)
		{
			listener.tableChanged(tce);
		}
	}


}
