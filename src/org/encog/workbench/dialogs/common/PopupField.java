package org.encog.workbench.dialogs.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PopupField extends PropertiesField implements ActionListener {

	private String value;
	private JButton button;
	private JLabel label;
	
	public PopupField(String name, String label, boolean required) {
		super(name, label, required);
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JPanel p = new JPanel();
		this.setField(p);
		p.setLocation(x, y);

		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		
		p.setSize(width,label.getHeight());
		
		p.setLayout(new BorderLayout());
		p.add(this.label = new JLabel("test"),BorderLayout.CENTER);
		p.add(this.button = new JButton("..."),BorderLayout.EAST);
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		panel.add(label);
		panel.add(p);
		
		this.button.addActionListener(this);
		
		return y+p.getHeight();
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.button)
		{
			String str = ((PopupListener)this.getOwner()).popup(this);
			if( str!=null )
				this.value = str;
			this.label.setText(this.value);
		}
		
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.label.setText(value);
		this.value = value;
	}
	
	

}
