package org.encog.workbench.tabs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.neural.data.TextData;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.util.EncogFonts;

public class TextDataTab extends EncogCommonTab {

	private final JTextArea text;
	private final JScrollPane scroll;
	
	public TextDataTab(EncogPersistedObject encogObject) {
		super(encogObject);
		
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(true);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.text);
		add(this.scroll, BorderLayout.CENTER);
		this.text.setText(((TextData)getEncogObject()).getText());
	}
	


	public void setText(final String t) {
		this.text.setText(t);
	}
	
	public String getText()
	{
		return this.text.getText();
	}
	
	public void close()
	{
		((TextData)getEncogObject()).setText(this.getText());
	}

}
