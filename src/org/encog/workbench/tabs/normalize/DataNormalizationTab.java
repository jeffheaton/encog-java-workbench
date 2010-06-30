package org.encog.workbench.tabs.normalize;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.tabs.EncogCommonTab;

public class DataNormalizationTab extends EncogCommonTab {

	public DataNormalizationTab(EncogPersistedObject encogObject) {
		super(encogObject);
		
		this.setLayout(new BorderLayout());
	
		
		// input fields
		JPanel inputFieldPanel = new JPanel();
		JScrollPane inputFieldsScroll;
		JList inputFieldsList;
		JPanel inputFieldsButtons;
		
		inputFieldsList = new JList();
		inputFieldsScroll = new JScrollPane(inputFieldsList);
		inputFieldsButtons = new JPanel();
		
		inputFieldPanel.setLayout(new BorderLayout());
		inputFieldPanel.add(new JLabel("Input Fields"),BorderLayout.NORTH);
		inputFieldPanel.add(inputFieldsScroll,BorderLayout.CENTER);
		inputFieldPanel.add(inputFieldsButtons,BorderLayout.SOUTH);
		inputFieldsButtons.add(new JButton("Add"));
		inputFieldsButtons.add(new JButton("Remove"));
		inputFieldsButtons.add(new JButton("Edit"));
		
		// output fields
		JPanel outputFieldPanel = new JPanel();
		JScrollPane outputFieldsScroll;
		JList outputFieldsList;
		JPanel outputFieldsButtons;
		
		outputFieldsList = new JList();
		outputFieldsScroll = new JScrollPane(outputFieldsList);
		outputFieldsButtons = new JPanel();
		
		outputFieldPanel.setLayout(new BorderLayout());
		outputFieldPanel.add(new JLabel("Output Fields"),BorderLayout.NORTH);
		outputFieldPanel.add(outputFieldsScroll,BorderLayout.CENTER);
		outputFieldPanel.add(outputFieldsButtons,BorderLayout.SOUTH);
		outputFieldsButtons.add(new JButton("Add"));
		outputFieldsButtons.add(new JButton("Remove"));
		outputFieldsButtons.add(new JButton("Edit"));
	
		// segregators
		JPanel segregatorPanel = new JPanel();
		JScrollPane segregatorScroll;
		JList segregatorList;
		JPanel segregatorButtons;
		
		segregatorList = new JList();
		segregatorScroll = new JScrollPane(segregatorList);
		segregatorButtons = new JPanel();
		
		segregatorPanel.setLayout(new BorderLayout());
		segregatorPanel.add(new JLabel("Segregators"),BorderLayout.NORTH);
		segregatorPanel.add(segregatorScroll,BorderLayout.CENTER);
		segregatorPanel.add(segregatorButtons,BorderLayout.SOUTH);
		segregatorButtons.add(new JButton("Add"));
		segregatorButtons.add(new JButton("Remove"));
		segregatorButtons.add(new JButton("Edit"));
		
		// run panel
		JPanel bottomPanel = new JPanel();
		JButton runButton = new JButton("Run Normalization");
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(runButton,BorderLayout.CENTER);
		
		// top panel
		JPanel topPanel = new JPanel();
		topPanel.setMinimumSize(new Dimension(100,100));
		topPanel.setSize(new Dimension(100,100));
		topPanel.setLayout(null);
		topPanel.add(new JLabel("Target:"));
		
		
		// splits
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				inputFieldPanel, outputFieldPanel);
			
		
		JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				split, segregatorPanel);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(split2,BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
		
	}

}
