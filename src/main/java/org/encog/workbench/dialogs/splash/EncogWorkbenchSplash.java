/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.dialogs.splash;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.EncogFonts;

public class EncogWorkbenchSplash extends JDialog implements ActionListener {
	
	private JButton buttonNew;
	private JButton buttonOpen;
	private JButton buttonQuit;
	
	public EncogWorkbenchSplash()
	{
		setLocation(100,100);
		setSize(500,200);
		setModal(true);
		setTitle("Welcome");
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(this.buttonNew = new JButton("New Project Folder..."));
		buttonPanel.add(this.buttonOpen = new JButton("Open Project Folder..."));
		buttonPanel.add(this.buttonQuit = new JButton("Quit"));
		content.add(buttonPanel,BorderLayout.SOUTH);
		this.buttonNew.addActionListener(this);
		this.buttonOpen.addActionListener(this);
		this.buttonQuit.addActionListener(this);
	}
	
	public void process()
	{
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.buttonNew ) {
			dispose();
			EncogWorkBench.getInstance().getMainWindow().getOperations().performFileNewProject();
		}
		else if( e.getSource()==this.buttonOpen ) {
			dispose();
			EncogWorkBench.getInstance().getMainWindow().getOperations().performFileChooseDirectory();
		}
		else if( e.getSource()==this.buttonQuit ) {
			dispose();
			EncogWorkBench.getInstance().getMainWindow().getOperations().performQuit();
		}
		
	}
	
	public void paint(final Graphics g) {
		super.paint(g);
		final FontMetrics fm = g.getFontMetrics();
		g.setFont(EncogFonts.getInstance().getTitleFont());
		int y = fm.getHeight()+30;
		g.setFont(EncogFonts.getInstance().getTitleFont());
		g.drawString("Encog Workbench v" + EncogWorkBench.VERSION, 10, y);
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString( EncogWorkBench.COPYRIGHT, 10, y);
		y += g.getFontMetrics().getHeight();
		g.drawString( "Released under the LGPL license", 10, y);
		y += g.getFontMetrics().getHeight();
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Java Version:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(System.getProperty("java.version"), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Java 64/32-Bit:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(System.getProperty("sun.arch.data.model"), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Processor Count:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(""+Runtime.getRuntime().availableProcessors(), 150, y);
		y += g.getFontMetrics().getHeight();
	}
}
