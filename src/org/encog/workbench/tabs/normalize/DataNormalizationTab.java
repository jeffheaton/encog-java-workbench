package org.encog.workbench.tabs.normalize;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.encog.normalize.DataNormalization;
import org.encog.normalize.input.InputField;
import org.encog.normalize.input.InputFieldEncogCollection;
import org.encog.normalize.output.OutputField;
import org.encog.normalize.output.OutputFieldDirect;
import org.encog.normalize.target.NormalizationStorage;
import org.encog.normalize.target.NormalizationStorageEncogCollection;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.normalize.InputFieldEdit;
import org.encog.workbench.dialogs.normalize.OutputFieldDirectEdit;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.tabs.EncogCommonTab;

public class DataNormalizationTab extends EncogCommonTab implements
		ActionListener {

	private JButton inAddButton;
	private JButton inRemoveButton;
	private JButton inEditButton;
	private JButton outAddButton;
	private JButton outRemoveButton;
	private JButton outEditButton;
	private JButton segAddButton;
	private JButton segRemoveButton;
	private JButton segEditButton;
	private JList inputFieldsList;
	private JList outputFieldsList;
	private JList segregatorList;
	private JComboBox comboTarget;
	private JButton runButton;
	private DataNormalization norm;

	public DataNormalizationTab(EncogPersistedObject encogObject) {
		super(encogObject);

		this.setLayout(new BorderLayout());
		this.norm = (DataNormalization) encogObject;

		// input fields
		JPanel inputFieldPanel = new JPanel();
		JScrollPane inputFieldsScroll;
		JPanel inputFieldsButtons;

		inputFieldsList = new JList();
		inputFieldsScroll = new JScrollPane(inputFieldsList);
		inputFieldsButtons = new JPanel();

		inputFieldPanel.setLayout(new BorderLayout());
		inputFieldPanel.add(new JLabel("Input Fields"), BorderLayout.NORTH);
		inputFieldPanel.add(inputFieldsScroll, BorderLayout.CENTER);
		inputFieldPanel.add(inputFieldsButtons, BorderLayout.SOUTH);
		inputFieldsButtons.add(this.inAddButton = new JButton("Add"));
		inputFieldsButtons.add(this.inRemoveButton = new JButton("Remove"));
		inputFieldsButtons.add(this.inEditButton = new JButton("Edit"));

		// output fields
		JPanel outputFieldPanel = new JPanel();
		JScrollPane outputFieldsScroll;
		JPanel outputFieldsButtons;

		outputFieldsList = new JList();
		outputFieldsScroll = new JScrollPane(outputFieldsList);
		outputFieldsButtons = new JPanel();

		outputFieldPanel.setLayout(new BorderLayout());
		outputFieldPanel.add(new JLabel("Output Fields"), BorderLayout.NORTH);
		outputFieldPanel.add(outputFieldsScroll, BorderLayout.CENTER);
		outputFieldPanel.add(outputFieldsButtons, BorderLayout.SOUTH);
		outputFieldsButtons.add(this.outAddButton = new JButton("Add"));
		outputFieldsButtons.add(this.outRemoveButton = new JButton("Remove"));
		outputFieldsButtons.add(this.outEditButton = new JButton("Edit"));

		// segregators
		JPanel segregatorPanel = new JPanel();
		JScrollPane segregatorScroll;
		JPanel segregatorButtons;

		segregatorList = new JList();
		segregatorScroll = new JScrollPane(segregatorList);
		segregatorButtons = new JPanel();

		segregatorPanel.setLayout(new BorderLayout());
		segregatorPanel.add(new JLabel("Segregators"), BorderLayout.NORTH);
		segregatorPanel.add(segregatorScroll, BorderLayout.CENTER);
		segregatorPanel.add(segregatorButtons, BorderLayout.SOUTH);
		segregatorButtons.add(this.segAddButton = new JButton("Add"));
		segregatorButtons.add(this.segRemoveButton = new JButton("Remove"));
		segregatorButtons.add(this.segEditButton = new JButton("Edit"));

		// run panel
		JPanel bottomPanel = new JPanel();
		this.runButton = new JButton("Run Normalization");
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(runButton, BorderLayout.CENTER);

		// top panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(new JLabel("Target:  "), BorderLayout.WEST);
		topPanel.add(this.comboTarget = new JComboBox(), BorderLayout.CENTER);

		// splits
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				inputFieldPanel, outputFieldPanel);

		JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split,
				segregatorPanel);

		this.add(topPanel, BorderLayout.NORTH);
		this.add(split2, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

		// action listeners

		this.inAddButton.addActionListener(this);
		this.inRemoveButton.addActionListener(this);
		this.inEditButton.addActionListener(this);
		this.outAddButton.addActionListener(this);
		this.outRemoveButton.addActionListener(this);
		this.outEditButton.addActionListener(this);
		this.segAddButton.addActionListener(this);
		this.segRemoveButton.addActionListener(this);
		this.segEditButton.addActionListener(this);

		findData();
		setTarget();
		setInput();
	}

	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_LINK)) {
				this.comboTarget.addItem(obj.getName());
			} else if (obj.getType().equals(
					EncogPersistedCollection.TYPE_TRAINING)) {
				this.comboTarget.addItem(obj.getName());
			}
		}
	}

	private void setTarget() {
		NormalizationStorageEncogCollection storage = (NormalizationStorageEncogCollection) norm
				.getStorage();

		if (storage != null) {
			for (int i = 0; i < this.comboTarget.getItemCount(); i++) {
				String item = (String) this.comboTarget.getItemAt(i);
				if (item.equals(storage.getResourceName())) {
					this.comboTarget.setSelectedIndex(i);
					return;
				}
			}
		}
		this.comboTarget.setSelectedIndex(-1);
	}
	
	private void setInput() {
		
		Collection<String> list = new TreeSet<String>();
				
		for(InputField field : this.norm.getInputFields() )
		{
			InputFieldEncogCollection field2 = (InputFieldEncogCollection)field;
			list.add(field2.toString());
		}
	
		this.inputFieldsList.removeAll();
		this.inputFieldsList.setListData(list.toArray());
			 
	}
	
	private void setOutput() {
		
		Collection<String> list = new TreeSet<String>();
				
		for(OutputField field : this.norm.getOutputFields() )
		{
			OutputField field2 = (OutputField)field;
			list.add(field2.toString());
		}
	
		this.outputFieldsList.removeAll();
		this.outputFieldsList.setListData(list.toArray());
			 
	}

	public void collectTarget() {
		NormalizationStorageEncogCollection target = new NormalizationStorageEncogCollection();
		target.setResourceName((String) this.comboTarget.getSelectedItem());
		this.norm.setTarget(target);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.inAddButton) {
			performInAddButton();
		} else if (e.getSource() == this.inRemoveButton) {
			performInRemoveButton();
		} else if (e.getSource() == this.inEditButton) {
			performInEditButton();
		} else if (e.getSource() == this.outAddButton) {
			performOutAddButton();
		} else if (e.getSource() == this.outRemoveButton) {
			performOutRemoveButton();
		} else if (e.getSource() == this.outEditButton) {
			performOutEditButton();
		} else if (e.getSource() == this.segAddButton) {
			performSegAddButton();
		} else if (e.getSource() == this.segRemoveButton) {
			performSegRemoveButton();
		} else if (e.getSource() == this.segEditButton) {
			performSegEditButton();
		} else if (e.getSource() == this.runButton) {
			performRun();
		}
	}

	public void performInAddButton() {
		InputFieldEdit dialog = new InputFieldEdit(EncogWorkBench.getInstance().getMainWindow());
		if( dialog.process() )
		{
			InputFieldEncogCollection field = new InputFieldEncogCollection();
			field.setOffset(dialog.getInputFieldIndex().getValue());
			field.setResourceName((String)dialog.getComboSourceData().getSelectedValue());
			this.norm.addInputField(field);
			this.setInput();
		}
		
	}

	public void performInRemoveButton() {

	}

	public void performInEditButton() {

	}

	public void performOutAddButton() {
		SelectItem itemDirect, itemRangeScaled, itemValueMapped, itemOneOf, itemEquilateral, itemMultiplicative, itemZAxis ;
		final List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(itemDirect = new SelectItem("Direct", "A direct copy of the input field, no normalization performed."));
		list.add(itemRangeScaled = new SelectItem("Range Scaled", "Scale one range, to another range.  Usually to -1,1 or 0,1.  This is a very common normalization type."));
		list.add(itemValueMapped = new SelectItem("Value Range Mapped", "Map a range of values to one specific value.  For example, 1-100 to 1, 101-200 to 2, etc."));
		list.add(itemOneOf = new SelectItem("One Of", "Nominal output values, used to specify membership in a class.  For example, a defined set of colors.  Requires one output field for each class."));
		list.add(itemEquilateral = new SelectItem("Equilateral", "More advanced field for nominal output values, used to specify membership in a class.  Only useful when there are 3 or more classes.  For fewer classes use \"one of\".  For example, a defined set of colors.  Requires one output field for each class."));
		list.add(itemMultiplicative = new SelectItem("Multiplicative", "Both the multiplicative and z-axis normalization types allow a group of outputs to be adjusted so that the \"vector length\" is 1.  The multiplicative normalization is more simple than Z-Axis normalization.  Almost always Z=Axis normalization is a better choice.  However, multiplicative can perform better than Z-Axis when all of the values are near zero most of the time."));
		list.add(itemZAxis = new SelectItem("Z-Axis", "Both the multiplicative and z-axis normalization types allow a group of outputs to be adjusted so that the \"vector length\" is 1.  The multiplicative normalization is more simple than Z-Axis normalization.  Almost always Z=Axis normalization is a better choice.  However, multiplicative can perform better than Z-Axis when all of the values are near zero most of the time."));
		final SelectDialog dialog = new SelectDialog(EncogWorkBench.getInstance().getMainWindow(), list);
		dialog.setTitle("Choose Output Field Type");
		if( !dialog.process() )
			return;
		SelectItem result = dialog.getSelected();
		
		if( result==itemDirect)
		{
			OutputFieldDirectEdit dialog2 = new OutputFieldDirectEdit(EncogWorkBench.getInstance().getMainWindow(),this.norm);
			if( dialog2.process() )
			{
				OutputFieldDirect field = new OutputFieldDirect(dialog2.getInputField());
				norm.getOutputFields().add(field);
			}
			
		}
		else if( result==itemRangeScaled)
		{
			
		}
		else if( result==itemValueMapped)
		{
			
		}
		else if( result==itemOneOf)
		{
			
		}
		else if( result==itemEquilateral)
		{
			
		}
		else if( result==itemMultiplicative) 
		{
			
		}
		else if( result==itemZAxis)
		{
			
		}
		setOutput();
		
		
	}

	public void performOutRemoveButton() {

	}

	public void performOutEditButton() {

	}

	public void performSegAddButton() {

	}

	public void performSegRemoveButton() {

	}

	public void performSegEditButton() {

	}

	public void performRun() {

	}

	public boolean close() {
		collectTarget();
		return true;
	}

}
