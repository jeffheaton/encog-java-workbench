/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.frames.query;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.encog.EncogError;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.Layer;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.error.ErrorDialog;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.models.NetworkQueryModel;

public class NetworkQueryFrame extends EncogCommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable inputTable;
	private JTable outputTable;
	private int inputCount;
	private int outputCount;
	private JButton calculateButton;

	public NetworkQueryFrame(final BasicNetwork data) {
		super(true);
		setEncogObject(data);
		addWindowListener(this);
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.calculateButton) {
			try {
			final BasicNeuralData input = new BasicNeuralData(this.inputCount);
			for (int i = 0; i < this.inputCount; i++) {
				double value = 0;
				final String str = (String) this.inputTable.getValueAt(i, 1);
				try {
					value = Double.parseDouble(str);
				} catch (final NumberFormatException e2) {
					EncogWorkBench.displayError("Data Error",
							"Please enter a valid input number.");
				}
				input.setData(i, value);
			}

			final NeuralData output = getData().compute(input);

			for (int i = 0; i < this.outputCount; i++) {
				this.outputTable.setValueAt(output.getData(i), i, 1);
			}
		}
			catch(EncogError ex)
			{
				EncogWorkBench.displayError("Query Error", ex.getMessage());
			}
			catch(Throwable t)
			{
				ErrorDialog.handleError(t, (BasicNetwork)this.getEncogObject(), null);
			}
		}

	}

	public BasicNetwork getData() {
		return (BasicNetwork) getEncogObject();
	}

	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(final WindowEvent e) {
		
		Layer input = getData().getLayer(BasicNetwork.TAG_INPUT);
		Layer output = getData().getLayer(BasicNetwork.TAG_OUTPUT);
		
		//
		this.inputCount = input.getNeuronCount();
		this.outputCount = output.getNeuronCount();

		// create the graphic objects
		setTitle("Query Network: " + getData().getName());
		this.setSize(640, 480);
		final Container contents = getContentPane();
		contents.setLayout(new BorderLayout());
		final JPanel body = new JPanel();
		body.setLayout(new GridLayout(1, 2, 10, 10));
		contents.add(body, BorderLayout.CENTER);
		final JPanel left = new JPanel();
		final JPanel right = new JPanel();
		body.add(left);
		body.add(right);
		left.setLayout(new BorderLayout());
		right.setLayout(new BorderLayout());
		left.add(new JLabel("Input"), BorderLayout.NORTH);
		right.add(new JLabel("Output"), BorderLayout.NORTH);
		left.add(this.inputTable = new JTable(new NetworkQueryModel(
				this.inputCount, 2)), BorderLayout.CENTER);
		right.add(this.outputTable = new JTable(new NetworkQueryModel(
				this.outputCount, 2)), BorderLayout.CENTER);
		contents.add(this.calculateButton = new JButton("Calculate"),
				BorderLayout.SOUTH);
		this.outputTable.setEnabled(false);

		for (int i = 1; i <= this.inputCount; i++) {
			this.inputTable.setValueAt("Input " + i + ":", i - 1, 0);
			this.inputTable.setValueAt("0.0", i - 1, 1);
		}

		for (int i = 1; i <= this.outputCount; i++) {
			this.outputTable.setValueAt("Output " + i + ":", i - 1, 0);
			this.outputTable.setValueAt("0.0", i - 1, 1);
		}

		this.calculateButton.addActionListener(this);
	}

}
