/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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
