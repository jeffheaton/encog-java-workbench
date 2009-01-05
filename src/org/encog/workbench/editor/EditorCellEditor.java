package org.encog.workbench.editor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class EditorCellEditor implements TableCellEditor {
	
	private List<CellEditorListener> listeners = new ArrayList<CellEditorListener>();
	private JTextField editorText;
	private JCheckBox editorBoolean;
	private Class currentClass;
	
	public EditorCellEditor()
	{
		this.editorText = new JTextField();
		this.editorBoolean = new JCheckBox();
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		this.currentClass = value.getClass();
		if( this.currentClass == Boolean.class )
			return this.editorBoolean;
		else
			return this.editorText;
	}

	public void addCellEditorListener(CellEditorListener l) {
		this.listeners.add(l);
		
	}

	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	public void removeCellEditorListener(CellEditorListener l) {
		this.listeners.remove(l);
		
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return false;
	}

}
