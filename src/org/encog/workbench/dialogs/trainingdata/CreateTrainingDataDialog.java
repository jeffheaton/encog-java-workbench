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

package org.encog.workbench.dialogs.trainingdata;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.createnetwork.NeuralNetworkType;

public class CreateTrainingDataDialog extends EncogCommonDialog implements
	ListSelectionListener {
	
	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private TrainingDataType type;
	private JCheckBox checkLink;

	public CreateTrainingDataDialog(Frame owner) {
		super(owner);
		setTitle("Create or Link Training Data");

		this.setSize(500, 250);
		this.setLocation(50, 100);
		
		final Container bottom = new JPanel();
		
		
		final Container select = new JPanel();
		select.setLayout(new GridLayout(1, 2));

		select.add(this.scroll1);
		select.add(this.scroll2);

		this.model.addElement("Empty Training Set");
		this.model.addElement("Import Training Set from CSV");
		this.model.addElement("Market Data Training Set");
		this.model.addElement("Prediction Window from CSV");
		this.model.addElement("Random Training Set");		
		this.model.addElement("XOR Temporal Training Set");
		this.model.addElement("XOR Training Set");
		
		this.list.addListSelectionListener(this);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text.setEditable(false);
		scroll2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		final Container outer = getBodyPanel();
		
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.checkLink = new JCheckBox("Store in External File(linked)");
		bottom.add(this.checkLink);
		
		outer.setLayout(new BorderLayout());
		outer.add(select,BorderLayout.CENTER);
		outer.add(bottom,BorderLayout.SOUTH);
		this.checkLink.setSelected(EncogWorkBench.getInstance().getCurrentFileName()!=null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5882600361686632769L;
	

	@Override
	public void collectFields() throws ValidationException {
		switch (list.getSelectedIndex()) {
		case 0:
			this.type = TrainingDataType.Empty;
			break;
		case 1:
			this.type = TrainingDataType.ImportCSV;
			break;
		case 2:
			this.type = TrainingDataType.MarketWindow;
			break;
		case 3:
			this.type = TrainingDataType.PredictWindow;
			break;
		case 4:
			this.type = TrainingDataType.Random;
			break;
		case 5:
			this.type = TrainingDataType.XORTemp;
			break;
		case 6:
			this.type = TrainingDataType.XOR;
			break;		
		}
	}

	@Override
	public void setFields() {
		switch (type) {
		case Empty:
			this.list.setSelectedIndex(0);
			break;
		case ImportCSV:
			this.list.setSelectedIndex(1);
			break;
		case MarketWindow:
			this.list.setSelectedIndex(2);
			break;
		case PredictWindow:
			this.list.setSelectedIndex(3);
			break;
		case Random:
			this.list.setSelectedIndex(4);
			break;
		case XORTemp:
			this.list.setSelectedIndex(5);
			break;
		case XOR:
			this.list.setSelectedIndex(6);
			break;		
		}

	}

	public TrainingDataType getType() {
		return type;
	}

	public void setType(TrainingDataType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text
					.setText("An empty training set that you can add more data to.  Can be either supervised, or unsupervised.  To specify an unsupervised training set specify zero output/ideal neurons.");
			break;

		case 1:
			this.text
					.setText("Import training data from a CSV file.  A comma separated value (CSV) file is a common source of data for neural network training.  The contents of the CSV will be copied to your Encog EG file.  This may not be practical with large files.");
			break;
								
		case 2:
			this.text
					.setText("Download market data from Yahoo Finance.  You need to enter a ticker symbol and date range.  You must also specify the size of the input window used to predict the output/prediction window.");
			break;

		case 3:
			this.text
					.setText("Use a CSV file to input prediction data. You will be prompted for an input and output window size.  The data will be formatted according to these two window sizes.");
			break;

		case 4:
			this.text
					.setText("Create a training set of random numbers.  This is really only useful for some testing purposes.  ");
			break;

		case 5:
			this.text
					.setText("Create training data that uses the XOR function over time.  This is commonly used as a very simple test for Elman and Jordan style neural networks.  The XOR as temporal data is the sequence 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0.");
			break;

		case 6:
			this.text
					.setText("Creates the classic XOR input data used to test many different neural network types.  This consists of four training pairs, each with two input values and one ideal value.");
			break;
			
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);

	}

	public boolean shouldLink()
	{
		return this.checkLink.isSelected();
	}

}
