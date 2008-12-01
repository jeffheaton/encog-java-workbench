package org.encog.workbench.frames;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextFrame extends JFrame {
	
	private JTextArea text;
	private JScrollPane scroll;
	
	public TextFrame(String title,boolean readOnly)
	{
		this.setTitle(title);
		this.setSize(640,480);
		this.text = new JTextArea();
		this.text.setEditable(!readOnly);
		this.scroll = new JScrollPane(this.text);
		this.getContentPane().add(this.scroll);
	}
	
	public void setText(String t)
	{
		this.text.setText(t);
	}

}
