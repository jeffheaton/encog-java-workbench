package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.neural.data.PropertyData;

public class PropertyDataModel  implements TableModel {
	
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private PropertyData data;
	private String[] properties;
	
	public PropertyDataModel(PropertyData data)
	{
		this.data = data;
		updateProperties();
	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
		
	}

	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int c) {
		if(c==1)
			return "Name";
		else
			return "Value";
	}

	public int getRowCount() {
		return this.properties.length;
	}

	public Object getValueAt(int row, int col) {
		
		String key = this.properties[row]; 
		
		if( col==0 )
			return key;
		else
			return this.data.get(key);
	}

	public boolean isCellEditable(int row, int col) {
		if(col==0)
			return false;
		else
			return true;
	}

	public void removeTableModelListener(TableModelListener listener) {
		this.listeners.remove(listener);
		
	}

	public void setValueAt(Object obj, int row, int col) {

		String key = this.properties[row]; 
		String value = (String)obj;
		
		if( col==1 )
			this.data.set(key,value);
		
	}
	
	public void updateProperties()
	{
		this.properties = new String[data.size()];
		Map<String,String> map = this.data.getData();
		int i=0;
		for(String key: map.keySet() )
		{
			this.properties[i++]=key;
		}
		
		updateListeners();
	}
	
	public void updateListeners()
	{
		TableModelEvent event = new TableModelEvent(this);
		
		for(TableModelListener listener: this.listeners)
		{
			listener.tableChanged(event);
		}
	}

	public void addProperty(String input) {
		this.data.set(input, "");
		updateProperties();
		
	}

	public void deleteRow(int row) {
		String key = this.properties[row];
		this.data.remove(key);
		updateProperties();
		
	}

}
