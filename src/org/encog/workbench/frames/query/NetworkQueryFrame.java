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
