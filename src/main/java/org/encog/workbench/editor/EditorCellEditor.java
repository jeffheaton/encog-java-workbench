/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.editor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class EditorCellEditor implements TableCellEditor {
	
	private List<CellEditorListener> listeners = new ArrayList<CellEditorListener>();
	private JTextField editorText;
	private JComboBox editorBoolean;
	private Class currentClass;
	private Object value;
	private int row;
	private int column;
	private JTable table;
	
	public static final String[] BOOLEAN_VALUES = {"true","false"};
	
	public EditorCellEditor()
	{
		this.editorText = new JTextField();
		this.editorBoolean = new JComboBox(BOOLEAN_VALUES);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		this.row = row;
		this.column = column;
		this.table = table;
		this.currentClass = value.getClass();
		this.value = value;
		if( this.currentClass == Boolean.class )
		{
			this.editorBoolean.setVisible(true);
			if( (Boolean)value == true )
				this.editorBoolean.setSelectedIndex(0);
			else
				this.editorBoolean.setSelectedIndex(1);
			
			return this.editorBoolean;	
		}
		else
		{
			this.editorText.setVisible(true);
			this.editorText.setText(value.toString());
			return this.editorText;
		}
	}

	public void addCellEditorListener(CellEditorListener l) {
		this.listeners.add(l);
		
	}

	public void cancelCellEditing() {
		if( this.currentClass == Boolean.class ) {
			this.editorBoolean.setVisible(false);
			this.editorBoolean.setSelectedIndex(0);
		}
		else
		{
			this.editorText.setVisible(false);
			this.editorText.setText("");
		}
		
	}

	public Object getCellEditorValue() {
		stopCellEditing();
		return this.value;
	}

	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	public void removeCellEditorListener(CellEditorListener l) {
		this.listeners.remove(l);
		
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	public boolean stopCellEditing() {
		if( this.currentClass == Boolean.class ) {
			this.value = this.editorBoolean.getSelectedIndex()==0;
			System.out.println(this.value);
			this.editorBoolean.setVisible(false);
		}
		else
		{
			this.editorText.setVisible(false);
			this.value = this.editorText.getText();
		}
		
		if( this.table!=null)
		{
			this.table.setValueAt(this.value, this.row, this.column);
		}
		return true;
	}
	
	public void clearSelection()
	{
		this.editorBoolean.setVisible(false);
		this.editorText.setVisible(false);
	}

}
