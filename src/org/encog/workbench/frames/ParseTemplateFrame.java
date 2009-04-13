package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.encog.parse.Parse;
import org.encog.parse.ParseTemplate;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.editor.ObjectEditorFrame;

public class ParseTemplateFrame extends ObjectEditorFrame {

	private JPanel buttons;
	private JButton btnLoadDefault;

	
	public ParseTemplateFrame(ParseTemplate data) {
		super(data);
		
		Container content = this.getContentPane();
		
		this.buttons = new JPanel();		
		content.add(this.buttons, BorderLayout.NORTH);
		this.btnLoadDefault = new JButton("Reset to Default Template");
		this.buttons.add(this.btnLoadDefault);
		this.buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.btnLoadDefault.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.btnLoadDefault) {
			if (EncogWorkBench
					.askQuestion(
							"Are you sure?",
							"Would you like to reset this parse template to the\ndefault Encog template? This will discard all changes.")) {
				//EncogPersistedCollection encog = new EncogPersistedCollection();
				//encog.loadResource(Parse.RESOURCE_NAME);
				//this.setData( (ParseTemplate) encog
				//		.find(Parse.RESOURCE_ITEM_NAME) );
				generateTree();
			}
		} 

	}

	
}
