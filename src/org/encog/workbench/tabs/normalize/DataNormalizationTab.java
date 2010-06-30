package org.encog.workbench.tabs.normalize;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.encog.normalize.DataNormalization;
import org.encog.normalize.target.NormalizationStorage;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.tabs.EncogCommonTab;

public class DataNormalizationTab extends EncogCommonTab implements ActionListener {

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
		this.norm = (DataNormalization)encogObject;
		
		// input fields
		JPanel inputFieldPanel = new JPanel();
		JScrollPane inputFieldsScroll;
		JPanel inputFieldsButtons;
		
		inputFieldsList = new JList();
		inputFieldsScroll = new JScrollPane(inputFieldsList);
		inputFieldsButtons = new JPanel();
		
		inputFieldPanel.setLayout(new BorderLayout());
		inputFieldPanel.add(new JLabel("Input Fields"),BorderLayout.NORTH);
		inputFieldPanel.add(inputFieldsScroll,BorderLayout.CENTER);
		inputFieldPanel.add(inputFieldsButtons,BorderLayout.SOUTH);
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
		outputFieldPanel.add(new JLabel("Output Fields"),BorderLayout.NORTH);
		outputFieldPanel.add(outputFieldsScroll,BorderLayout.CENTER);
		outputFieldPanel.add(outputFieldsButtons,BorderLayout.SOUTH);
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
		segregatorPanel.add(new JLabel("Segregators"),BorderLayout.NORTH);
		segregatorPanel.add(segregatorScroll,BorderLayout.CENTER);
		segregatorPanel.add(segregatorButtons,BorderLayout.SOUTH);
		segregatorButtons.add(this.segAddButton = new JButton("Add"));
		segregatorButtons.add(this.segRemoveButton = new JButton("Remove"));
		segregatorButtons.add(this.segEditButton = new JButton("Edit"));
		
		// run panel
		JPanel bottomPanel = new JPanel();
		this.runButton = new JButton("Run Normalization");
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(runButton,BorderLayout.CENTER);
		
		// top panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(new JLabel("Target:  "),BorderLayout.WEST);
		topPanel.add(this.comboTarget = new JComboBox(),BorderLayout.CENTER);
		
		
		// splits
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				inputFieldPanel, outputFieldPanel);
			
		
		JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				split, segregatorPanel);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(split2,BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
		
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
	}
	
	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_LINK) ) {
				this.comboTarget.addItem(obj.getName());
			} else if (obj.getType().equals(EncogPersistedCollection.TYPE_TRAINING) ) {
				this.comboTarget.addItem(obj.getName());
			} 
		}
	}
	
	private void setTarget()
	{
		NormalizationStorage storage = norm.getStorage();
		
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.inAddButton) {
			performInAddButton();	
		}
		else if( e.getSource()==this.inRemoveButton) {
			performInRemoveButton();	
		}
		else if( e.getSource()==this.inEditButton) {
			performInEditButton();	
		}
		else if( e.getSource()==this.outAddButton) {
			performOutAddButton();	
		}
		else if( e.getSource()==this.outRemoveButton) {
			performOutRemoveButton();	
		}
		else if( e.getSource()==this.outEditButton) {
			performOutEditButton();	
		}
		else if( e.getSource()==this.segAddButton) {
			performSegAddButton();	
		}
		else if( e.getSource()==this.segRemoveButton) {
			performSegRemoveButton();	
		}
		else if( e.getSource()==this.segEditButton) {
			performSegEditButton();	
		}
		else if( e.getSource()==this.runButton ) {
			performRun();
		}
	}
	
	public void performInAddButton()
	{
		
	}
	
	public void performInRemoveButton()
	{
		
	}
	
	public void performInEditButton()
	{
		
	}
	
	public void performOutAddButton()
	{
		
	}
	
	public void performOutRemoveButton()
	{
		
	}
	
	public void performOutEditButton()
	{
		
	}
	
	public void performSegAddButton()
	{
		
	}
	
	public void performSegRemoveButton()
	{
		
	}
	
	public void performSegEditButton()
	{
		
	}
	
	public void performRun()
	{
		
	}

}
