package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.process.generate.Generate.GenerateLanguage;

public class RandomizeNetworkDialog extends EncogPropertiesDialog  {
	private final DoubleField high;
	private final DoubleField low;
	private final ComboBoxField type;
	private final DoubleField perturbPercent;
	private final IntegerField seedValue;
	private final DoubleField constantValue;
	private final DoubleField constHigh;
	private final DoubleField constLow;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;


	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public RandomizeNetworkDialog(final Frame owner) {
		
		super(owner);

		setTitle("Randomize Network");
		setSize(400,400);
		setLocation(200,200);
		
		List<String> types = new ArrayList<String>();
		types.add("Random");
		types.add("Gaussian");
		types.add("Nguyen-Widrow");
		
		this.setCollectCurrentTabOnly(true);
				
		this.beginTab("Randomize");
		addProperty(this.high = new DoubleField("high","High Range",true,0,-1));
		addProperty(this.low = new DoubleField("low","Low Range",true,0,-1));
		addProperty(this.type = new ComboBoxField("type","Low Range",true,types));
		this.beginTab("Perturb");
		addProperty(this.perturbPercent = new DoubleField("perturb percent","Perturb Percent",true,0,-1));
		this.beginTab("Consistent");
		addProperty(this.seedValue = new IntegerField("seed value","Seed Value",true,0,-1));
		addProperty(this.constHigh = new DoubleField("high","High Range",true,0,-1));
		addProperty(this.constLow = new DoubleField("low","Low Range",true,0,-1));		
		this.beginTab("Constant");
		addProperty(this.constantValue = new DoubleField("constant value","Constant Value",true,0,-1));
		render();
	}


	public DoubleField getHigh() {
		return high;
	}


	public DoubleField getLow() {
		return low;
	}


	public ComboBoxField getType() {
		return type;
	}


	public DoubleField getPerturbPercent() {
		return perturbPercent;
	}


	public IntegerField getSeedValue() {
		return seedValue;
	}


	public DoubleField getConstantValue() {
		return constantValue;
	}


	public DoubleField getConstHigh() {
		return constHigh;
	}


	public DoubleField getConstLow() {
		return constLow;
	}


	


}
