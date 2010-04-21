/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
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
import org.encog.neural.networks.synapse.Synapse;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.TableFieldModel;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.util.MouseUtil;

public class MatrixTableField extends PropertiesField implements MouseListener,
		ActionListener {

	private final MatrixTableModel model;
	private final int height;
	private final Synapse synapse;
	private final Matrix matrix;
	private JTable table;

	private JPopupMenu popupMatrix;
	private JMenuItem popupMatrixEnable;
	private JMenuItem popupMatrixDisable;

	public MatrixTableField(String name, String label, Synapse synapse) {
		super(name, label, true);
		this.synapse = synapse;
		this.matrix = synapse.getMatrix().clone();
		this.model = new MatrixTableModel(matrix);
		this.height = 300;

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
		table.setDefaultRenderer(Object.class, new MatrixCellRender());

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
		} else if (e.getSource() == this.popupMatrixEnable) {
			this.table.setValueAt(RangeRandomizer.randomize(-1, 1), row, col);
		}

	}

}
