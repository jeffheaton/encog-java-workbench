package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class CreateTemporalDataDialog  extends EncogPropertiesDialog {

	private final IntegerField inputWindow;
	private final IntegerField outputWindow;
	private final ComboBoxField normalizationType;

	
	public CreateTemporalDataDialog(Frame owner) {
		super(owner);
		
		setTitle("Temporal Training Data");
		setSize(400,200);
		addProperty(this.inputWindow = new IntegerField("input window","Input Window",true,0,1000));
		addProperty(this.outputWindow = new IntegerField("output window","Output Window",true, 0, 1000));

		List<String> list = new ArrayList<String>();
		list.add("Raw");
		list.add("Percent Change");
		list.add("Delta Change");
		addProperty(this.normalizationType = new ComboBoxField("normalization type", "Normalization Type",true,list) );
		
		
		render();
	}


	public IntegerField getInputWindow() {
		return inputWindow;
	}


	public IntegerField getOutputWindow() {
		return outputWindow;
	}


	public ComboBoxField getNormalizationType() {
		return normalizationType;
	}
	
	

	
}
