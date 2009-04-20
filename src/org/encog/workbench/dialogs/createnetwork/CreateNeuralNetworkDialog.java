package org.encog.workbench.dialogs.createnetwork;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class CreateNeuralNetworkDialog extends EncogCommonDialog {

	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextField text = new JTextField();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private NeuralNetworkType type;
	
	public CreateNeuralNetworkDialog(Frame owner) {
		super(owner);
		setTitle("Create a Neural Network");

		this.setSize(300, 240);
		this.setLocation(200, 100);
		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(1,2));
		
		content.add(this.scroll1);
		content.add(this.scroll2);
		
		this.model.addElement("Empty Neural Network");
		this.model.addElement("Feedforward Neural Network");
		this.model.addElement("Self Organizing Map");
		this.model.addElement("Hopfield Neural Network");
		this.model.addElement("Recurrent - Elman");
		this.model.addElement("Recurrent - Jordan");
		this.model.addElement("Feedforward - Radial Basis");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5882600361686632769L;

	@Override
	public void collectFields() throws ValidationException {
		switch(list.getSelectedIndex())
		{
		case 0:
			this.type = NeuralNetworkType.Empty;
			break;
		case 1:
			this.type = NeuralNetworkType.Feedforward;
			break;
		case 2:
			this.type = NeuralNetworkType.SOM;
			break;
		case 3:
			this.type = NeuralNetworkType.Hopfield;
			break;
		case 4:
			this.type = NeuralNetworkType.Elman;
			break;
		case 5:
			this.type = NeuralNetworkType.Jordan;
			break;
		case 6:
			this.type = NeuralNetworkType.RBF;
			break;
		}
		
	}

	@Override
	public void setFields() {
		switch(type)
		{
		case Empty:
			this.list.setSelectedIndex(0);
			break;
		case Feedforward:
			this.list.setSelectedIndex(1);
			break;
		case SOM:
			this.list.setSelectedIndex(2);
			break;
		case Hopfield:
			this.list.setSelectedIndex(3);
			break;
		case Elman:
			this.list.setSelectedIndex(4);
			break;
		case Jordan:
			this.list.setSelectedIndex(5);
			break;
		case RBF:
			this.list.setSelectedIndex(6);
			break;
		}
		
	}

	public NeuralNetworkType getType() {
		return type;
	}

	public void setType(NeuralNetworkType type) {
		this.type = type;
	}
	
	

}
