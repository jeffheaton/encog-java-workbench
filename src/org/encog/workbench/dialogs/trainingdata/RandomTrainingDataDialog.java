package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;


public class RandomTrainingDataDialog extends EncogPropertiesDialog {
	
	private IntegerField elements;
	private IntegerField input;
	private IntegerField ideal;
	private DoubleField low;
	private DoubleField high;
	
	public RandomTrainingDataDialog(Frame owner) {
		super(owner);
		setTitle("Random Training Data");
		setSize(400,200);
		addProperty(this.elements = new IntegerField("elements","Training Set Elements",true, 0, 10000));
		addProperty(this.input = new IntegerField("input","Input Field Count",true, 1, 1000));
		addProperty(this.ideal = new IntegerField("ideal","Output Field Count",true, 0, 1000));
		addProperty(this.low = new DoubleField("low","Random Low Value",true, 0, -1));
		addProperty(this.high = new DoubleField("high","Random High Value",true, 0, -1));
		render();
	}

	public IntegerField getElements() {
		return elements;
	}

	public IntegerField getInput() {
		return input;
	}

	public IntegerField getIdeal() {
		return ideal;
	}

	public DoubleField getLow() {
		return low;
	}

	public DoubleField getHigh() {
		return high;
	}
	
	

}
