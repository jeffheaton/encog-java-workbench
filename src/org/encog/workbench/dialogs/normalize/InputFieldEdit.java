package org.encog.workbench.dialogs.normalize;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class InputFieldEdit extends EncogPropertiesDialog {
	
	private ComboBoxField comboSourceData;
	private IntegerField inputFieldIndex;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;

	/**
	 * All available training sets to display in the combo box.
	 */
	private final List<String> trainingSets = new ArrayList<String>();
	

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public InputFieldEdit(final Frame owner) {
		
		super(owner);
		findData();
		setTitle("Normalization Input");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboSourceData = new ComboBoxField("source data","Source Data",true,this.trainingSets));
		addProperty(this.inputFieldIndex = new IntegerField("field index","Field Index(in source data)",true,0,10000));
		render();
	}



	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_BINARY) ) {
				this.trainingSets.add(obj.getName());
			} else if (obj.getType().equals(EncogPersistedCollection.TYPE_TRAINING) ||
					obj.getType().equals(EncogPersistedCollection.TYPE_BINARY)) {
				this.trainingSets.add(obj.getName());
			}
		}
	}



	/**
	 * @return the comboSourceData
	 */
	public ComboBoxField getComboSourceData() {
		return comboSourceData;
	}



	/**
	 * @return the inputFieldIndex
	 */
	public IntegerField getInputFieldIndex() {
		return inputFieldIndex;
	}

	

}
