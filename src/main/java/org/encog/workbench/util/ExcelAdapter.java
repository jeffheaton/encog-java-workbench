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
package org.encog.workbench.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.encog.workbench.models.TrainingSetTableModel;

import java.awt.datatransfer.*;
import java.util.*;

/**
 * ExcelAdapter enables Copy-Paste Clipboard functionality on JTables. The
 * clipboard data format used by the adapter is compatible with the clipboard
 * format used by Excel. This provides for clipboard interoperability between
 * enabled JTables and Excel.
 */
public class ExcelAdapter implements ActionListener {
	private Clipboard system;
	private JTable table;
	private TrainingSetTableModel model;

	/**
	 * The Excel Adapter is constructed with a JTable on which it enables
	 * Copy-Paste and acts as a Clipboard listener.
	 */
	public ExcelAdapter(JTable table) {
		if( table.getModel() instanceof TrainingSetTableModel ){
			model = (TrainingSetTableModel)table.getModel();
		}else{
			System.out.println( table.getModel() );
			throw new RuntimeException( "Table model not supported by " +
					"ExcelAdapter." );
		}
		
		this.table = table;
		KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK, false);
		// Identifying the copy KeyStroke user can modify this
		// to copy on some other Key combination.
		KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK, false);
		// Identifying the Paste KeyStroke user can modify this
		// to copy on some other Key combination.
		this.table.registerKeyboardAction(this, "Copy", copy,
				JComponent.WHEN_FOCUSED);
		this.table.registerKeyboardAction(this, "Paste", paste,
				JComponent.WHEN_FOCUSED);
		system = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	/**
	 * Public Accessor methods for the Table on which this adapter acts.
	 */
	public JTable getJTable() {
		return table;
	}

	public void setJTable(JTable jTable1) {
		this.table = jTable1;
	}

	/**
	 * This method is activated on the Keystrokes we are listening to in this
	 * implementation. Here it listens for Copy and Paste ActionCommands.
	 * Selections comprising non-adjacent cells result in invalid selection and
	 * then copy action cannot be performed. Paste is done by aligning the upper
	 * left corner of the selection with the 1st element in the current
	 * selection of the JTable.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().compareTo("Copy") == 0) {
			StringBuffer sbf = new StringBuffer();
			// Check to ensure we have selected only a contiguous block of
			// cells
			int numcols = table.getSelectedColumnCount();
			int numrows = table.getSelectedRowCount();
			int[] rowsselected = table.getSelectedRows();
			int[] colsselected = table.getSelectedColumns();
			if (!((numrows - 1 == rowsselected[rowsselected.length - 1]
					- rowsselected[0] && numrows == rowsselected.length) &&
					(numcols - 1 == colsselected[colsselected.length - 1]
					- colsselected[0] && numcols == colsselected.length))) {
				JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
						"Invalid Copy Selection", JOptionPane.ERROR_MESSAGE);
				return;
			}
			for (int i = 0; i < numrows; i++) {
				for (int j = 0; j < numcols; j++) {
					sbf.append(table.getValueAt(rowsselected[i],
							colsselected[j]));
					if (j < numcols - 1)
						sbf.append("\t");
				}
				sbf.append("\n");
			}
			StringSelection stsel = new StringSelection(sbf.toString());
			system = Toolkit.getDefaultToolkit().getSystemClipboard();
			system.setContents(stsel, stsel);
		}
		if (e.getActionCommand().compareTo("Paste") == 0) {
			int startRow = (table.getSelectedRows())[0];
			int startCol = (table.getSelectedColumns())[0];
			try {
				String trstring = (String) (system.getContents(this)
						.getTransferData(DataFlavor.stringFlavor));
				StringTokenizer st1 = new StringTokenizer(trstring, "\n");
				for (int i = 0; st1.hasMoreTokens(); i++) {
					String rowstring = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(rowstring, "\t");
					for (int j = 0; st2.hasMoreTokens(); j++) {
						String value = (String) st2.nextToken();
						setValue(value, startRow + i, startCol + j);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void setValue( String value, int row, int column ){
		if( table.getRowCount() <= row ){
			model.addRow(-1);
		}
		table.setValueAt( value, row, column );
	}
}
