package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class CreateMarketTrainingDialog  extends EncogPropertiesDialog {
	
	private final TextField ticker;
	private final IntegerField fromMonth;
	private final IntegerField fromDay;
	private final IntegerField fromYear;
	private final IntegerField toMonth;
	private final IntegerField toDay;
	private final IntegerField toYear;
	private final IntegerField inputWindow;
	private final IntegerField outputWindow;

	
	public CreateMarketTrainingDialog(Frame owner) {
		super(owner);
		
		GregorianCalendar gc = new GregorianCalendar();
		int year = gc.get(Calendar.YEAR);
		
		setTitle("Market Training Data");
		setSize(400,200);
		addProperty(this.ticker = new TextField("ticker","Ticker Symbol",true));
		addProperty(this.fromMonth = new IntegerField("begin month","Beginning Month(1-12)",true, 1, 12));
		addProperty(this.fromDay = new IntegerField("begin day","Beginning Day(1-12)",true, 1, 31));
		addProperty(this.fromYear = new IntegerField("begin year","Beginning Year(1-12)",true, 1900, year));
		
		addProperty(this.toMonth = new IntegerField("end month","Ending Month(1-12)",true, 1, 12));
		addProperty(this.toDay = new IntegerField("end day","Ending Day(1-12)",true, 1, 31));
		addProperty(this.toYear = new IntegerField("end year","Ending Year(1-12)",true, 1900, year));
		
		addProperty(this.inputWindow = new IntegerField("input window","Input Window",true,0,1000));
		addProperty(this.outputWindow = new IntegerField("output window","Output Window",true, 0, 1000));

		
		render();
	}


	public TextField getTicker() {
		return ticker;
	}


	public IntegerField getFromMonth() {
		return fromMonth;
	}


	public IntegerField getFromDay() {
		return fromDay;
	}


	public IntegerField getFromYear() {
		return fromYear;
	}


	public IntegerField getToMonth() {
		return toMonth;
	}


	public IntegerField getToDay() {
		return toDay;
	}


	public IntegerField getToYear() {
		return toYear;
	}


	public IntegerField getInputWindow() {
		return inputWindow;
	}


	public IntegerField getOutputWindow() {
		return outputWindow;
	}

	
	
}
