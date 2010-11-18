/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.synapse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.encog.mathutil.matrices.Matrix;
import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.TableFieldModel;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.util.MouseUtil;

public class MatrixTableField extends PropertiesField implements MouseListener,
		ActionListener {

	private final MatrixTableModel model;
	private final int height;
	private final BasicNetwork network;
	private final Synapse synapse;
	private final Matrix matrix;
	private JTable table;

	private JPopupMenu popupMatrix;
	private JMenuItem popupMatrixEnable;
	private JMenuItem popupMatrixDisable;
	private boolean shouldLimit;
	private double limitValue;

	public MatrixTableField(String name, String label, BasicNetwork network, Synapse synapse) {
		super(name, label, true);
		this.network = network;
		this.synapse = synapse;
		this.matrix = synapse.getMatrix().clone();
		this.model = new MatrixTableModel(this, network,matrix);
		this.height = 300;
		this.shouldLimit = network.getStructure().isConnectionLimited();
		if( this.shouldLimit )
			limitValue = network.getStructure().getConnectionLimit();

		this.popupMatrix = new JPopupMenu();
		this.popupMatrixEnable = addItem(this.popupMatrix, "Enable Connection",
				'e');
		this.popupMatrixDisable = addItem(this.popupMatrix,
				"Disable Connection", 'e');

	}

	public JMenuItem addItem(final JPopupMenu m, final String s, final int key) {

		final JMenuItem mi = new JMenuItem(s, key);
		mi.addActionListener(this);
		m.add(mi);
		return mi;
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub

	}

	public int createField(JPanel panel, int x, int y, int width) {
		this.table = new JTable(this.model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.addMouseListener(this);
		table.setDefaultRenderer(Object.class, new MatrixCellRender(this));

		for (int i = 0; i < this.model.getColumnCount(); i++) {
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setPreferredWidth(125);
		}

		this.setField(new JScrollPane(this.table));
		this.getField().setLocation(x, y);
		this.getField().setSize(width, this.height);

		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.getField());

		return y + this.getField().getHeight();
	}

	public void setValue(int row, int col, String str) {
		this.model.setValueAt(str, row, col);
	}

	public String getValue(int row, int col) {
		return "" + this.model.getValueAt(row, col);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public MatrixTableModel getModel() {
		return model;
	}

	public int getHeight() {
		return height;
	}

	public Synapse getSynapse() {
		return synapse;
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void mouseClicked(MouseEvent e) {
		if (MouseUtil.isRightClick(e)) {
			int row = table.rowAtPoint(e.getPoint());
			int col = table.columnAtPoint(e.getPoint());

			Object value = this.table.getValueAt(row, col);

			if (value instanceof Double) {
				table.setCellSelectionEnabled(true);
				table.getSelectionModel().setSelectionInterval(row, row);
				table.getColumnModel().getSelectionModel()
						.setSelectionInterval(col, col);

				table.setColumnSelectionInterval(col, col);
				table.setRowSelectionInterval(row, row);

				this.popupMatrix.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {

		int row = this.table.getSelectedRow();
		int col = this.table.getSelectedColumn();

		if (e.getSource() == this.popupMatrixDisable) {
			this.table.setValueAt(0.0, row, col);
			if( !this.shouldLimit ) {
				this.shouldLimit = true;
				this.limitValue = Double.parseDouble(BasicNetwork.DEFAULT_CONNECTION_LIMIT);
			}
		} else if (e.getSource() == this.popupMatrixEnable) {
			this.table.setValueAt(RangeRandomizer.randomize(-1, 1), row, col);
		}

	}

	public boolean isShouldLimit() {
		return shouldLimit;
	}

	public void setShouldLimit(boolean shouldLimit) {
		this.shouldLimit = shouldLimit;
	}

	public double getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(double limitValue) {
		this.limitValue = limitValue;
	}
	
	
	

}
