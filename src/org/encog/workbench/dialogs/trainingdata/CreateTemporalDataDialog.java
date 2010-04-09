package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class CreateTemporalDataDialog  extends EncogPropertiesDialog {

	private final IntegerField inputWindow;
	private final IntegerField outputWindow;

	
	public CreateTemporalDataDialog(Frame owner) {
		super(owner);
		
		GregorianCalendar gc = new GregorianCalendar();
		int year = gc.get(Calendar.YEAR);
		
		setTitle("Temporal Training Data");
		setSize(400,200);
		addProperty(this.inputWindow = new IntegerField("input window","Input Window",true,0,1000));
		addProperty(this.outputWindow = new IntegerField("output window","Output Window",true, 0, 1000));

		
		render();
	}




	public IntegerField getInputWindow() {
		return inputWindow;
	}


	public IntegerField getOutputWindow() {
		return outputWindow;
	}

	
}
