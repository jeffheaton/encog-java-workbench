package org.encog.workbench.frames.document;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.neural.data.TextData;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.logging.Logging;
import org.encog.workbench.util.EncogFonts;
import org.encog.workbench.util.WorkbenchLogHandler;

public class EncogOutputPanel extends JPanel implements ActionListener {
	
	public static final String[] LEVELS = {"OFF","SEVERE","INFO","WARNING"};
	
	private final JTextArea text;
	private final JScrollPane scroll;
	private final JButton buttonClear;
	private final JComboBox comboLogLevel;
	
	public EncogOutputPanel() {
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(true);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.text);
		add(this.scroll, BorderLayout.CENTER);
		this.text.setEditable(false);		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		add(buttonPanel,BorderLayout.NORTH);
		
		this.comboLogLevel = new JComboBox(LEVELS);
		buttonPanel.add(new JLabel("Log Level:"));
		buttonPanel.add(this.comboLogLevel);
		buttonPanel.add(buttonClear = new JButton("Clear"));
		
		this.buttonClear.addActionListener(this);
		this.comboLogLevel.addActionListener(this);
	}
	
	public void output(String output)
	{
		this.text.append(output);
		
		int l = this.text.getText().length();		
		this.text.setSelectionStart(l);
		this.text.setSelectionEnd(l);
	}
	
	public void outputLine(String output)
	{
		output(output+"\n");
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.buttonClear ) {
			this.text.setText("");
		}
		else if( e.getSource()==this.comboLogLevel ) {
			String level = (String)this.comboLogLevel.getSelectedItem();
			output("Logging level set to: " + level);
			
			Level l = Level.OFF;
			
			if( level.equals("OFF") )
			{
				l = Level.OFF;
			}
			else if( level.equals("SEVERE") )
			{
				l = Level.SEVERE;
			}
			else if( level.equals("INFO") )
			{
				l = Level.INFO;
			}
			else if( level.equals("WARNING") )
			{
				l = Level.WARNING;	
			}
			Logging.getRootLogger().setLevel(l);
		} 
		
	}
}
