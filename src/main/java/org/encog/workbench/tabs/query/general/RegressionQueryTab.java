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
package org.encog.workbench.tabs.query.general;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.EncogError;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.error.ErrorDialog;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.models.NetworkQueryModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class RegressionQueryTab extends EncogCommonTab implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable inputTable;
	private JTable outputTable;
	private int inputCount;
	private int outputCount;
	private JButton calculateButton;
	private MLRegression method;

	public RegressionQueryTab(final MLRegression theMethod) {
		super(null);
		this.method = theMethod;
		
		this.inputCount = theMethod.getInputCount();
		this.outputCount = theMethod.getOutputCount();

		// create the graphic objects
		this.setSize(640, 480);

		this.setLayout(new BorderLayout());
		final JPanel body = new JPanel();
		body.setLayout(new GridLayout(1, 2, 10, 10));
		this.add(body, BorderLayout.CENTER);
		final JPanel left = new JPanel();
		final JPanel right = new JPanel();
		body.add(left);
		body.add(right);
		left.setLayout(new BorderLayout());
		right.setLayout(new BorderLayout());
		left.add(new JLabel("Input"), BorderLayout.NORTH);
		right.add(new JLabel("Output"), BorderLayout.NORTH);
		
		this.inputTable = new JTable(new NetworkQueryModel(
				this.inputCount, 2));
		this.outputTable = new JTable(new NetworkQueryModel(
				this.outputCount, 2));
		
		JScrollPane inputScroll = new JScrollPane(this.inputTable);
		JScrollPane outputScroll = new JScrollPane(this.outputTable);
		
		
		left.add(inputScroll, BorderLayout.CENTER);
		right.add(outputScroll, BorderLayout.CENTER);
		this.add(this.calculateButton = new JButton("Calculate"),
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

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.calculateButton) {
			try {
				setDirty(true);
				final BasicMLData input = new BasicMLData(
						this.inputCount);
				for (int i = 0; i < this.inputCount; i++) {
					double value = 0;
					final String str = (String) this.inputTable
							.getValueAt(i, 1);
					try {
						value = Double.parseDouble(str);
					} catch (final NumberFormatException e2) {
						EncogWorkBench.displayError("Data Error",
								"Please enter a valid input number.");
					}
					input.setData(i, value);
				}

				final MLData output = this.method.compute(input);

				for (int i = 0; i < this.outputCount; i++) {
					this.outputTable.setValueAt(output.getData(i), i, 1);
				}
			} catch (EncogError ex) {
				EncogWorkBench.displayError("Query Error", ex.getMessage());
			} catch (Throwable t) {
				ErrorDialog.handleError(t,this.getEncogObject(), null);
			}
		}

	}

	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getName() {
		return "Query Encog Program";
	}

}
