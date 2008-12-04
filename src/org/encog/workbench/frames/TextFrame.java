package org.encog.workbench.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.workbench.frames.manager.EncogCommonFrame;

public class TextFrame extends EncogCommonFrame {
	
	private JTextArea text;
	private JScrollPane scroll;
	
	public TextFrame(String title,boolean readOnly)
	{
		this.setTitle(title);
		this.setSize(640,480);
		this.text = new JTextArea();
		this.text.setFont(new Font("monospaced",0,12));
		this.text.setEditable(!readOnly);
		this.scroll = new JScrollPane(this.text);
		this.getContentPane().add(this.scroll);
	}
	
	public void setText(String t)
	{
		this.text.setText(t);
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
