package org.encog.workbench.dialogs.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckField extends PropertiesField implements ActionListener {

	private boolean value;
	private CheckListener listener;
	
	public CheckField(String name, String label) {
		super(name, label, true);
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.setField(new JCheckBox());
		this.getField().setLocation(x, y);
		this.getField().setSize(this.getField().getPreferredSize());
		this.getField().setSize(width,this.getField().getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.getField());
		((JCheckBox)this.getField()).addActionListener(this);
		
		return y+this.getField().getHeight();
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == getField() )
			if( this.listener!=null )
		this.listener.check(this);
		
	}

	public CheckListener getListener() {
		return listener;
	}

	public void setListener(CheckListener listener) {
		
			this.listener = listener;
	}

	public boolean getValue() {
		return ((JCheckBox)this.getField()).isSelected();
	}

	public void setValue(boolean b) {
		((JCheckBox)this.getField()).setSelected(b);
		
	}
	
	
	
	

}
